package it.giustizia.smp.scheduler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.NodeList;

import it.giustizia.gestorelocale.accettazione.esiti.CercaRiepiloghi;
import it.giustizia.gestorelocale.accettazione.esiti.ExtraInfo;
import it.giustizia.gestorelocale.accettazione.esiti.RiepilogoContenuto;
import it.giustizia.gestorelocale.accettazione.esiti.RiepilogoEsiti;
import it.giustizia.gestorelocale.accettazione.servizi.DownloadContenutoDeposito;
import it.giustizia.gestorelocale.accettazione.servizi.ServiziAccettazioneServiceClient;
import it.giustizia.smp.dao.NotificheDao;
import it.giustizia.smp.model.TAmministratori;
import it.giustizia.smp.model.TAnUfficio;
import it.giustizia.smp.model.TDepositi;
import it.giustizia.smp.model.TProcedimento;
import it.giustizia.smp.model.TRelProcProc;
import it.giustizia.smp.multidb.UserRoutingDataSource;
import it.giustizia.smp.service.ProcedimentoService;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.webservice.client.glmp.serviziodepositi.ServiziDepositiServiceClient;

/***
 * Job per la ricezione dei depositi dal sistema GL
 * 
 * @author almaviva
 * 
 */
public class RicezioneDepositiJob {

	@Autowired
	private UserRoutingDataSource myDataSource;
	@Autowired
	private NotificheDao notificheDao;
	@Autowired
	ServiziDepositiServiceClient serviziDepositiServiceClient;
	@Autowired
	ServiziAccettazioneServiceClient serviziAccettazioneServiceClient;
	@Autowired
	ProcedimentoService procedimentoService;

	private Logger logger = Logger.getLogger(getClass());

	/***
	 * Mappa ed inizializzazione necessaria per simulare il l'associazione fra
	 * l'indirizzo pec e il codugne in attesa della risoluzione del problema di GL
	 * legato al campo signer
	 */
	private static Map<String, String> fakeMap = new LinkedHashMap<String, String>();
	static {

		fakeMap.put("SITMP.TRIBUNALE.PALERMO@TEST1.GIUSTIZIACERT.IT", "08205302205");
		fakeMap.put("SITMP.CA.PALERMO@TEST1.GIUSTIZIACERT.IT", "08205300605");
		fakeMap.put("SITMP.PG.PALERMO@TEST1.GIUSTIZIACERT.IT", "08205300706");
		fakeMap.put("SITMP.PROCURA.PALERMO@TEST1.GIUSTIZIACERT.IT", "08205302104");
	}

	private static boolean hasInstaceInRun = false;

	// @Scheduled(cron = "0 0/5 1-23 * * ?")// scheduler ogni giorno dalle 1 alle 23
	// ogni 5 minuti
	// @Scheduled(cron="0 0/10 1-23 * * ?")//scheduler ogni giorno dalle 1 alle 23
	// ogni 10 minuti
	// @Scheduled(fixedRate=600000) //scheduler ogni 600 secondi

	public void scheduledMethod() {
		if (!hasInstaceInRun) {
			hasInstaceInRun = true;
			elaboraJob("SMP_GIU");
			elaboraJob("SMP");
			myDataSource.setSchemaDocumentale("");
		}
		hasInstaceInRun = false;
	}

	/*** P.Q. 29/01/21 - TT#20210125011: Modificata la forma del log nei metodi di elaorazione ****/
	private void elaboraJob(String schema) {
		myDataSource.selectConnection(schema);
		try {
			logger.info("Inizio Job - Ricezione depositi GL su schema: " + schema);
			if (schema.equals("SMP_GIU")) {
				ricezioneDepositiPerUffici(
						System.getProperties().getProperty("smp.ws.servizi.depositi.codugne.smp_giu"));
			} else if (schema.equals("SMP")) {
				ricezioneDepositiPerUffici(System.getProperties().getProperty("smp.ws.servizi.depositi.codugne.smp"));
			}
			logger.info("Fine Job - Ricezione depositi GL su schema: " + schema);
		} catch (Exception e) {
			logger.error("Fine Job - Ricezione depositi GL con Errore:" + e.getMessage());

		}
	}

	/***
	 * Implementazione della ricerca dei depositi sui vari uffici codugne passati
	 * come parametro
	 * 
	 * @param property
	 */
	private void ricezioneDepositiPerUffici(String listaCodugne) throws Exception {
		logger.info("1. Chiamo il metodo ricezioneDepositiPerUffici");
		if (listaCodugne == null || listaCodugne.trim().equals("")) {
			logger.error(
					"ATTENZIONE: Manca la cofigurazione delle chiavi smp.ws.servizi.depositi.codugne.smp e smp.ws.servizi.depositi.codugne.smp_giu nel file properties-service.xml del nodo.");
		} else {
			String stati = System.getProperties().getProperty("smp.ws.servizi.depositi.stati.interrogazione");
			if (stati == null || stati.trim().equals("")) {
				logger.info(
						"ATTENZIONE: Manca la cofigurazione della chiave smp.ws.servizi.depositi.stati.interrogazione nel file properties-service.xml del nodo.");
				logger.info("L'interrogazione sarà effettuata per i soli stati INFO,WARM.");
				stati = "INFO,HALT";
			}

			/**
			 * VECCHIA GESTIONE - Prima della patch di GL for (String stato :
			 * stati.split(",")) { String[] codugneArray = listaCodugne.split(","); for
			 * (String codugne : codugneArray) { ricezioneDepositi(codugne, stato); } }
			 */

			String[] codugneArray = listaCodugne.split(",");
			for (String codugne : codugneArray) {
				ricezioneDepositi(codugne, stati);
			}
		}
	}

	/***
	 * Implementazione della ricezione dei depositi per singolo ufficio
	 */
	private void ricezioneDepositi(String codugneUffCurr, String stati) throws Exception {
		logger.info("2. Interrogazione per codice codugna e stato: " + codugneUffCurr + " , " + stati);

		// Eseguo la lettura del dei depositi in stato INFO
		CercaRiepiloghi cercaRiepiloghi = new CercaRiepiloghi();
		// Non funziona perchè il tipo atto è valorizzato diversamente TODO CHIDERE ->
		// cercaRiepiloghi.getTipoAtto().add("http://penale.processotelematico.giustizia.it/mp/sistema/v1/DepositoDocumentale");
		cercaRiepiloghi.setStatus(stati);
		cercaRiepiloghi.setRegistro("MP");
		cercaRiepiloghi.setSoloUrgenti(false);

		List<RiepilogoEsiti> esiti = serviziDepositiServiceClient.cercaRiepiloghi(cercaRiepiloghi, codugneUffCurr);
		logger.info("3. serviziDepositiServiceClient.cercaRiepiloghi, numero di esiti : "
				+ (esiti != null ? esiti.size() : 0));
		/*** GC 23/02/2021 TT#20210222016 : Aggiunta gestione di esiti=null per non interrompere l'esecuzione del job e passare al codugna successivo  ***/
		if (esiti != null) {
			for (RiepilogoEsiti riepilogoEsiti : esiti) {

				// Per ogni deposito verifico se l'ho già inserito
				String idDepositoGl = riepilogoEsiti.getCatId(); // Id del deposito

				/*
				 * if (idDepositoGl.equals("8a834d4250ffe1ed0151871ee2e30038")) {
				 * System.out.println(riepilogoEsiti.getIdMsgGC()); }
				 */

				String idMittenteString = riepilogoEsiti.getSigner(); // Codugne destinatario del mittente oppure codice
																		// fiscale amministratore
				// Fake per risposta errata da parte di GL
				if (idMittenteString == null) {
					// Commentato perchè la patch rilasciata da GL ha risolto il bub
					// idMittenteString = fakeMap.get(riepilogoEsiti.getSender().toUpperCase());
				}
				String idDestinatariString = riepilogoEsiti.getDestination(); // Codugne destinatario
				String idDocumentoString = riepilogoEsiti.getMainCatId(); // Id del Documento
				Date dataRicezione = riepilogoEsiti.getReceiveTime().toGregorianCalendar().getTime(); // Data di
																										// ricezione

				logger.info("4. Dati valorizzati :  idDepositoGl " + idDepositoGl + " idMittenteString "
						+ idMittenteString + " idDestinatariString " + idDestinatariString + " idDocumentoString "
						+ idDocumentoString + " dataRicezione " + dataRicezione);

				Integer idUfficioMittente = null;
				Integer idUfficioDestinatario = null;
				long idAmministratore = 0;

				if (idMittenteString != null) {
					// controllo se è amministratore
					TAmministratori amministratore = notificheDao.getAmminByCodiceFiscale(idMittenteString);
					if (amministratore == null) {

						// se non è un amministratore va a vedere se è un ufficio giudiziario
						TAnUfficio ufficioMittente = notificheDao.getUfficioByCodugne(idMittenteString);
						if (ufficioMittente == null) {
							logger.info("Identificativo mittente non trovato per il deposito con id " + idDepositoGl
									+ "; codugne cercato = " + idMittenteString + ")");
						} else {
							idUfficioMittente = ufficioMittente.getCodiKIdUfficio();
							logger.info("5. idUfficioMittente " + idUfficioMittente);
						}
					} else {
						idAmministratore = amministratore.getCodiKIdAmmin();
						logger.info("Trovato amministratore per il deposito con id " + idDepositoGl
								+ "; codice fiscale cercato = " + idMittenteString + ")");
					}
				}

				if (idDestinatariString != null) {

					TAnUfficio ufficioDestinatario = notificheDao.getUfficioByCodugne(idDestinatariString);
					if (ufficioDestinatario == null) {
						logger.info("Identificativo destinatario non trovato per il deposito con id " + idDepositoGl
								+ "; codugne cercato = " + idDestinatariString + ")");

					} else {
						idUfficioDestinatario = ufficioDestinatario.getCodiKIdUfficio();
						logger.info("6. ufficioDestinatario " + ufficioDestinatario.getDescTipoUfficio() + " - " + ufficioDestinatario.getDescSedeUfficio() + " (" +  ufficioDestinatario.getDescUffGiudiz() + ")");
					}
				}

				if (idUfficioDestinatario != null && "".equals(idDocumentoString)) {
					// Se esiste l'ufficio destinatario ma non il documento... scrivo un warning
					logger.warn("Attezione: il deposito ");
				}

				if (idAmministratore > 0) {

					// controllo del contenuto del deposito (se esistono i documenti)

					List<RiepilogoContenuto> listaContenutoDeposito = riepilogoEsiti.getContentSummary();
					// List<ExtraInfo> attributiDeposito = riepilogoEsiti.getExtraInfo();

					if (listaContenutoDeposito != null) {
						logger.info("7. Chiamo gestioneDepositoAmm");
						gestioneDepositoAmm(idDepositoGl, idMittenteString, idDestinatariString, idDocumentoString,
								dataRicezione, idUfficioMittente, idUfficioDestinatario, listaContenutoDeposito);
					} else
						logger.error("Fine Job - Ricezione depositi GL interrotta per numero documenti non corretto");
				} else {
					// Utilizzare se ci sono le extra info estraggo l'id del documento da lì
					idDocumentoString = "";
					if (riepilogoEsiti.getExtraInfo() != null) {
						logger.info("18. Trovate EXTRA INFO su esito");
						idDocumentoString = getIdDocumento2(riepilogoEsiti);
					}
					if (idDocumentoString == null || (idDocumentoString != null && idDocumentoString.equals(""))) {
						// se nelle extra info non ho trovato l'id del documento provo con
						// con la lettura scaricando l'xml con l'informazione
						logger.info("19. PRIMA di getIdDocumento(riepilogoEsiti,codugneUffCurr) ");
						idDocumentoString = getIdDocumento(riepilogoEsiti, codugneUffCurr);
					}
					if (idUfficioDestinatario != null && !"".equals(idDocumentoString)) {
						// Se l'id dell'ufficio destinatari è stato decodificato dal codugne
						// verifico se il deposito è relativo ad un ufficio appartenente al polo
						// di tipo procura, tribunale, CDA o PG
						if (idUfficioMittente != null) {
							// Inserisco il deposito se non esiste ancora
							logger.info("23. PRIMA di notificheDao.inserisciDepositoSeNonEsiste() ");
							notificheDao.inserisciDepositoSeNonEsiste(idDepositoGl, idMittenteString,
									idDestinatariString, idUfficioMittente, idUfficioDestinatario, dataRicezione,
									idDocumentoString);
							logger.info("24. DOPO notificheDao.inserisciDepositoSeNonEsiste() ");
						}
					} else {
						logger.info("25. ERR: inserisciDepositoSeNonEsiste fallito! Parametri : idUfficioDestinatario " + idUfficioDestinatario + " idDocumentoString " + idDocumentoString + " idUfficioMittente " + idUfficioMittente);						
					}
				}
			}
		}
	}

	/***
	 * Esegue il download del documento ed estrae l'id del documentale
	 * 
	 * @param catIdDowload
	 * @param codugneUffCurr
	 * @return
	 */
	private String getIdDocumento(RiepilogoEsiti riepilogoEsiti, String codugneUffCurr) throws Exception {
		String catIdDowload = "";
		for (RiepilogoContenuto riepilogoContenuto : riepilogoEsiti.getContentSummary()) {
			if ("DA".equals(riepilogoContenuto.getMimeType())) {
				catIdDowload = riepilogoContenuto.getCatId();
			}
		}
		if ("".equals(catIdDowload))
			return "";
		DownloadContenutoDeposito downloadContenutoXml = new DownloadContenutoDeposito();
		downloadContenutoXml.setContentId(catIdDowload);
		logger.info(
				"20. PRIMA di serviziAccettazioneServiceClient.downloadContenutoDeposito(downloadContenutoXml,codugneUffCurr) ");
		DataHandler docXml = serviziAccettazioneServiceClient.downloadContenutoDeposito(downloadContenutoXml,
				codugneUffCurr);
		logger.info(
				"21. DOPO serviziAccettazioneServiceClient.downloadContenutoDeposito(downloadContenutoXml,codugneUffCurr) ");
		return estraiIDDocumentoDaXML(deserializzaFile(docXml));

	}

	/***
	 * Restituisce l'id del documento come da specifiche nuove di GL utilizzando
	 * l'oggetto extra info
	 * 
	 * @param catIdDowload
	 * @param codugneUffCurr
	 * @return
	 */
	private String getIdDocumento2(RiepilogoEsiti riepilogoEsiti) throws Exception {
		for (ExtraInfo extraInfo : riepilogoEsiti.getExtraInfo()) {
			if (extraInfo.getName() != null && extraInfo.getName().equals("DOCUMENTID")) {
				return extraInfo.getValue();
			}
		}
		return "";
	}

	private String estraiIDDocumentoDaXML(byte[] bXml) throws Exception {
		// ricerca idProcedimento nel file XML
		org.w3c.dom.Document documentw3c = null;
		try {
			Blob blobXml = Hibernate.createBlob(bXml);
			documentw3c = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(blobXml.getBinaryStream());
		} catch (Exception e) {
			logger.error("RicezioneDepositiJob - Errore " + e);
			return "";
		}

		XPath xPath = XPathFactory.newInstance().newXPath();

		// leggo il procedimento di merito da XML
		String expression = "/DepositoDocumentale/Documento";
		NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(documentw3c, XPathConstants.NODESET);

		/*** GC 23/02/2021 TT#20210319011 : Alcuni tipi di atti non trovano l'iddoc e quindi si bloccano qui; aggiungo un controllo se esista la sezione ***/
		String str = "";
		if (nodeList != null && nodeList.item(0) != null && nodeList.item(0).getFirstChild() != null) {
			str = nodeList.item(0).getFirstChild().getNodeValue();
			logger.info("22. Valore della sez DepositoDocumentale/Documento: " + str);
		} else {
			logger.error("22. ERR: Sezione /DepositoDocumentale/Documento non presente o nulla");
		}
		return str;				
	}

	private void gestioneDepositoAmm(String idDepositoGl, String idMittenteString, String idDestinatariString,
			String idDocumentoString, Date dataRicezione, Integer idUfficioMittente, Integer idUfficioDestinatario,
			List<RiepilogoContenuto> listaContenutoDeposito) throws Exception {

		Blob blobXml = null;
		Blob blobPdf = null;
		String descTipoDoc = null;
		String descNomeDoc = null;
		String contentId = null;

		// devono esserci solo e sempre 2 documenti (XML e PDF)
		if (listaContenutoDeposito.size() == 2) {
			for (RiepilogoContenuto contenutoDeposito : listaContenutoDeposito) {
				if (contenutoDeposito.getMimeType().contains("/xml")) {
					// downloadContenutoDeposito file XML MIME type = application/xml o text/xml
					contentId = contenutoDeposito.getCatId();
					DownloadContenutoDeposito downloadContenutoXml = new DownloadContenutoDeposito();
					downloadContenutoXml.setContentId(contentId);
					DataHandler docXml = serviziAccettazioneServiceClient
							.downloadContenutoDeposito(downloadContenutoXml, idDestinatariString);
					byte[] bXml = deserializzaFile(docXml);
					blobXml = Hibernate.createBlob(bXml);
					logger.info("8. Contenuto Deposito = XML");
				} else {
					// downloadContenutoDeposito file PDF o altro tipo file .... application/pdf
					contentId = contenutoDeposito.getCatId();
					DownloadContenutoDeposito downloadContenutoPdf = new DownloadContenutoDeposito();
					downloadContenutoPdf.setContentId(contentId);
					DataHandler docPdf = serviziAccettazioneServiceClient
							.downloadContenutoDeposito(downloadContenutoPdf, idDestinatariString);
					byte[] bPdf = deserializzaFile(docPdf);
					blobPdf = Hibernate.createBlob(bPdf);
					// su tipo e nome documento della T_DEPOSITI_AMM metto il tipo e il nome del
					// file PDF (che può essere anche di altro tipo es. DOCX, JPG,ecc.)
					descTipoDoc = contenutoDeposito.getMimeType();
					descNomeDoc = contenutoDeposito.getFilename();
					logger.info("8.1 Contenuto Deposito = PDF");
				}
			}
		} else {
			logger.error("Fine Job - Ricezione depositi GL interrotta per numero documenti non corretto");
			return;
		}

		if (blobXml != null) {

			// verifica xsd
			if (validateXMLSchema("schema.Amministratori.Relazione.xsd", blobXml)) {
				logger.info("9. XML Validato");
				TProcedimento tProcedimentoFg = new TProcedimento();
				TProcedimento tProcedimento = new TProcedimento();
				long idProcedimentoFg = 0;
				long idProcedimento = 0;

				// ricerca idProcedimento nel file XML
				org.w3c.dom.Document documentw3c = null;
				try {
					documentw3c = DocumentBuilderFactory.newInstance().newDocumentBuilder()
							.parse(blobXml.getBinaryStream());
				} catch (Exception e) {
					logger.error("RicezioneDepositiJob - Errore " + e);
					return;
				}

				XPath xPath = XPathFactory.newInstance().newXPath();

				// leggo il procedimento di merito da XML
				String expression = "/RicezioneRelazione/procedimento[1]/identificativoProcedimento";
				NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(documentw3c, XPathConstants.NODESET);
				String str = nodeList.item(0).getFirstChild().getNodeValue();
				idProcedimento = Long.parseLong(str);
				tProcedimento = procedimentoService.getProcedimentoById(idProcedimento);
				logger.info("10. Procedimento estratto dal file XML : " + idProcedimento);

				// leggo il fascicolo di gestione (tipo = 8) dall'id_procedimento
				if (idProcedimento > 0) {
					List<TRelProcProc> listaTRel = procedimentoService.getProc1ByIdProc2AndTipoFasc(idProcedimento,
							tProcedimento.getTAnUfficioByCodiFkIdPu().getCodiKIdUfficio(),
							Constants.TIPO_FASCICOLO_GESTIONE);
					if (listaTRel.size() > 0) {
						TRelProcProc tRel = listaTRel.get(0);
						idProcedimentoFg = tRel.getTProcedimento().getCodiKIdProc();
						tProcedimentoFg = procedimentoService.getProcedimentoById(idProcedimentoFg);
						logger.info("11. Trovato il fascicolo di gestione ");
					}
				}

				// scrittura su database

				if (idProcedimentoFg > 0 && idProcedimento > 0) {
					if (blobXml != null && blobPdf != null && descTipoDoc != null) {

						try {
							logger.info("12. PRIMA di notificheDao.inserisciDepositoSeNonEsiste ");
							notificheDao.inserisciDepositoSeNonEsiste(idDepositoGl, idMittenteString,
									idDestinatariString, idUfficioMittente, idUfficioDestinatario, dataRicezione,
									idDocumentoString);
							logger.info("13. DOPO notificheDao.inserisciDepositoSeNonEsiste ");

							logger.info("14. PRIMA di notificheDao.getDepositoByGl ");
							TDepositi tDeposito = notificheDao.getDepositoByGl(idDepositoGl);
							logger.info("15. DOPO notificheDao.getDepositoByGl ");

							logger.info("16. PRIMA di notificheDao.inserisciDepositoAmm ");
							notificheDao.inserisciDepositoAmm(tDeposito.getCodiKIdDeposito(), tProcedimentoFg,
									tProcedimento, tDeposito, blobXml, blobPdf, descTipoDoc, descNomeDoc);
							logger.info("17. DOPO notificheDao.inserisciDepositoAmm ");

							logger.info("Ricezione depositi memorizzato");
						} catch (Exception e) {
							logger.error("Fine Job - Ricezione depositi GL con Errore:" + e.getMessage());
						}
					} else {
						logger.error(
								"Fine Job - Ricezione depositi GL interrotta per assenza di riferimenti ai documenti XML e PDF");
					}
				} else {
					logger.error(
							"Fine Job - Ricezione depositi GL interrotta per assenza di riferimenti al procedimento di merito "
									+ idProcedimento + " o al procedimento di gestione " + idProcedimentoFg);
				}
			} else
				logger.error(
						"Fine Job - Ricezione depositi GL interrotta perchè il documento XML non è validato dallo schema XSD");
		} else {
			logger.error("Fine Job - Ricezione depositi GL interrotta per assenza del documento XML");
		}
	}

	/**
	 * validation file XML con schema XSD
	 * 
	 */
	public boolean validateXMLSchema(String xsdPath, Blob blobXml) {

		// TODO validare file XML con schema Relazione.xsd in
		// WebContent.schema.Amministratori
		return true;
	}

	/*
	 * private static String encode64(byte[] bytes) {
	 * 
	 * BASE64Encoder encoder = new BASE64Encoder(); return encoder.encode(bytes); }
	 * 
	 * private static byte[] decode64(String str) throws IOException {
	 * 
	 * BASE64Decoder decoder = new BASE64Decoder(); return
	 * decoder.decodeBuffer(str); }
	 */

	private byte[] deserializzaFile(DataHandler dh) {
		try {
			if (dh == null)
				return null;
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			dh.writeTo(output);
			byte[] byteArray = output.toByteArray();
			return byteArray;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}