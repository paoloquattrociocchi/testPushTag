/**
 * 
 */
package it.giustizia.smp.service;

import it.giustizia.smp.dao.AttoDao;
import it.giustizia.smp.dao.CommonDao;
import it.giustizia.smp.dao.NotificheDao;
import it.giustizia.smp.dao.ProcedimentoDao;
import it.giustizia.smp.dto.pg_notifiche.DestinatarioDto;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.NotificaAttoForm;
import it.giustizia.smp.form.NotificaDettaglioDestinatarioForm;
import it.giustizia.smp.form.NotificaDettaglioForm;
import it.giustizia.smp.form.NotificheEComunicazioniForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.model.TAnUfficio;
import it.giustizia.smp.model.TAnUtenzePdoc;
import it.giustizia.smp.model.TAtto;
import it.giustizia.smp.model.TAttoId;
import it.giustizia.smp.model.TNotifAmm;
import it.giustizia.smp.model.TNotifDest;
import it.giustizia.smp.model.TNotifRicevute;
import it.giustizia.smp.model.TNotifRichieste;
import it.giustizia.smp.model.TNotifiche;
import it.giustizia.smp.model.TProcedimento;
import it.giustizia.smp.model.TTpRuoliDest;
import it.giustizia.smp.model.TTpTipoUfficio;
import it.giustizia.smp.model.VNotifiche;
import it.giustizia.smp.service.webservices.DocumentaleService;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.utility.notifiche.ServiceResult;
import it.giustizia.smp.utility.notifiche.ServiceResult.Result;
import it.giustizia.smp.webservice.client.glmp.servizitrasmissione.Attributo;
import it.giustizia.smp.webservice.client.glmp.servizitrasmissione.IdFascicolo;
import it.giustizia.smp.webservice.client.glmp.servizitrasmissione.InfoDestinatario;
import it.giustizia.smp.webservice.client.glmp.servizitrasmissione.InfoEccezione;
import it.giustizia.smp.webservice.client.glmp.servizitrasmissione.InviaComunicazione;
import it.giustizia.smp.webservice.client.glmp.servizitrasmissione.Notifica;
import it.giustizia.smp.webservice.client.glmp.servizitrasmissione.ObjectFactory;
import it.giustizia.smp.webservice.client.glmp.servizitrasmissione.QueryStatoTrasmissioneResponse;
import it.giustizia.smp.webservice.client.glmp.servizitrasmissione.RisultatoComunicazione;
import it.giustizia.smp.webservice.client.glmp.servizitrasmissione.ServiziTrasmissioneServiceClient;
import it.giustizia.smp.xml.TrasmissioneRelazioneAmm;

import java.math.BigInteger;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author massimo.demattia
 *
 */
@Transactional(rollbackFor = Exception.class)
public class NotificheServiceImpl implements NotificheService {

	private Logger logger = Logger.getLogger(getClass());
	@Autowired
	private Mapper mapperBean;
	
	@Autowired
	ServiziTrasmissioneServiceClient serviziTrasmissioneServiceClient;
	
	@Autowired
	private NotificheDao notificheDao;
	
	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private DocumentaleService documentaleService ;
	
	@Autowired
	private AttoDao attoDao;
	
	@Autowired
	private ProcedimentoDao procedimentoDao;
	
	@Autowired
	private TrasmissioneAmministratoriService trasmissioneAmministratoriService;

	
	public String isAttoInviabile(long idAtto, long  idProcedimento) {
		return notificheDao.isAttoInviabile(idAtto, idProcedimento);
	}

	public List<DestinatarioDto> caricaListaDestinatari(long idProc, long idAtto, int idUfficio) {
		return notificheDao.caricaListaDestinatari(idProc,idAtto, idUfficio);
	}

	public NotificheDao getNotificheDao() {
		return notificheDao;
	}

	public void setNotificheDao(NotificheDao notificheDao) {
		this.notificheDao = notificheDao;
	}

	public List<ComboForm> getTipoUfficiPerDesinatario() {
		logger.info("Caricamento e trasformazione dei destinatari");
		List<TTpTipoUfficio> organi = notificheDao.getTipoUfficiPerDesinatario();
		List<ComboForm> risultati = new ArrayList<ComboForm>();
		Iterator<TTpTipoUfficio> iter = organi.iterator();
		while (iter.hasNext()) {
			TTpTipoUfficio currentOrgano = iter.next();
			if (currentOrgano.getCodiFKIdRuoloDest()!=null) {
				ComboForm cForm = new ComboForm();
				mapperBean.map(currentOrgano, cForm);
				risultati.add(cForm);
			}
		}
		return risultati;
		
	}
	
	
	public List<ComboForm> getListaTipoRuoloDesinatario() {
		logger.info("Caricamento e trasformazione dei ruoli del destinatario");
		List<TTpRuoliDest> organi = notificheDao.getListaTipoRuoliDesinatario();
		List<ComboForm> risultati = new ArrayList<ComboForm>();
		Iterator<TTpRuoliDest> iter = organi.iterator();
		while (iter.hasNext()) {
			TTpRuoliDest current = iter.next();
			ComboForm cForm = new ComboForm();
			cForm.setId(current.getCodiKRuoloDest()+"");
			cForm.setDescrizione(current.getDescRuoloDestSitmp());
			risultati.add(cForm);
		}
		return risultati;
		
	}

	public DestinatarioDto getDetinatarioDTObyIdUfficio(int idUfficio) {
		return notificheDao.getDetinatarioDTObyIdUfficio(idUfficio);
	}

	/***
	 * Implementazione dell'invio delle notifiche con chiamata al service di glmp
	 */
	public ServiceResult inviaNotifiche(NotificaAttoForm attoForm,ProcedimentoForm procedimento, int idUfficioMitt,String nomeECongomeUtente, String descUtente) {
		
		// Ciclo su tutti i destinatari selezionati e estraggo il dto per ognuno nelle due liste separate
		List<DestinatarioDto> listaDestinatariSelezionatiConContenuto = new ArrayList<DestinatarioDto>(); 
		List<DestinatarioDto> listaDestinatariSelezionatiSenzaContenuto = new ArrayList<DestinatarioDto>();
		for (String destSelezionato : attoForm.getListaIdCheckboxDestinatari()) {
			for (DestinatarioDto destinatarioDto : attoForm.getListaDestinatari()) {
				if (destSelezionato.substring(4).equals(destinatarioDto.getCODI_DEST())) {
					if ("S".equals(destinatarioDto.getFLG_ALLEGA_CONT())) {
						listaDestinatariSelezionatiConContenuto.add(destinatarioDto);
					} else {
						listaDestinatariSelezionatiSenzaContenuto.add(destinatarioDto);
					}
				}
			}
		}
		TAtto tAtto = attoDao.getAttoById(new TAttoId(attoForm.getCodAtto(), idUfficioMitt));
		TProcedimento tProcedimento = procedimentoDao.getProcedimentoById(procedimento.getIdProcedimento());
		TAnUfficio ufficioMittente = commonDao.getUfficioById(idUfficioMitt);
		

		// Inizializzo le due request di chiamata ai WS
		InviaComunicazione requestSenzaContenuto = null;
		InviaComunicazione requestConContenutoConXML = null;
		InviaComunicazione requestConContenutoSenzaXML = null;
		
		RisultatoComunicazione responseSenzaContenuto = null; 
		RisultatoComunicazione responseConContenutoConXML = null;
		RisultatoComunicazione responseConContenutoSenzaXML = null;
		
		byte[] xmlDati = null;
		BigInteger idPDOCXml = null;
		try {
			// Copia del documento sul documentale solo se necessario
			creaCopiaDocumentoSuDocumentaleEPDOC(attoForm, tAtto, procedimento, idUfficioMitt, listaDestinatariSelezionatiSenzaContenuto);			
			
			// Invoco il WS la lista di destinatari senza contenuti (tipicamente Uffici giudiziari)  
			if (listaDestinatariSelezionatiSenzaContenuto.size() > 0) {
				
				// Referenza agli allegati per ID
				requestSenzaContenuto = createRequetNotifica(attoForm, tAtto, tProcedimento, ufficioMittente, nomeECongomeUtente);
				if (tAtto.getNumeCopiaPdoc()==null) 
					throw new Exception("In nume_pdoc originale è null nella tabelle dell'atto per l'atto:" + tAtto.getId().getCodiKIdAtto() + " I dati sul db non sono coerenti.");

				requestSenzaContenuto.getNotifica().getAllegati().add(tAtto.getNumeCopiaPdoc().toString());
				requestSenzaContenuto.getNotifica().setAllegaContenuti(false);
				responseSenzaContenuto = invocaServizioRichiestaPerDestinatariPassati(requestSenzaContenuto, listaDestinatariSelezionatiSenzaContenuto);
				
			}
			
			
			
			// Invoco il WS la lista di destinatari con  contenuti  
			if (listaDestinatariSelezionatiConContenuto.size() > 0) {
				
				// Instanzio altre due liste per gestire con XML per Tool e Senza XML 
				List<DestinatarioDto> listaDestinatariSelezionatiConContenutoConXML = new ArrayList<DestinatarioDto>();
				List<DestinatarioDto> listaDestinatariSelezionatiConContenutoSenzaXML = new ArrayList<DestinatarioDto>();
				// Valorizzo le due liste separate per i destinatari di tipo amministratore e non amministratore
				for (DestinatarioDto destinatarioDto : listaDestinatariSelezionatiConContenuto) {
					if (isAmministratore(destinatarioDto) && attoForm.getFlgAllegaDatiToolAdmin()!=null && attoForm.getFlgAllegaDatiToolAdmin()==true) {
						listaDestinatariSelezionatiConContenutoConXML.add(destinatarioDto);
					} else {
						listaDestinatariSelezionatiConContenutoSenzaXML.add(destinatarioDto);
					}
				}
				
				if (listaDestinatariSelezionatiConContenutoConXML.size()>0 ) {
					// Se sono nel caso degli amministratore e devo notificare anche l'xml separo le request e Invoco la prima chiamata
					requestConContenutoSenzaXML = createRequetNotificaConContenuto(attoForm,tAtto,tProcedimento,ufficioMittente,nomeECongomeUtente);
					responseConContenutoSenzaXML = invocaServizioRichiestaPerDestinatariPassati(requestConContenutoSenzaXML, listaDestinatariSelezionatiConContenutoSenzaXML);
					
					// Se ci sono degli amministratori ed è stata impostata la check allega  dati tool amministratori e invoco la seconda chiamata
					xmlDati =  preparaXmlDatiToolAdmin(attoForm, idUfficioMitt, procedimento.getIdProcedimento());
					Blob documentoXMLBlob = Hibernate.createBlob(xmlDati);
					idPDOCXml = documentaleService.inserimentoDocumento("XMLToolAmministratori",  "application/xml", "XMLToolAmministratori",  "Xml Tool Amministratori", documentoXMLBlob, ufficioMittente.getDescUffGiudiz(), idUfficioMitt);
					if (idPDOCXml==null) {
						throw new Exception("Impossibile inserire il file XMl del Tool Amministratori nel documentale. Il servizio del documentale non è attualmente funzionante. Riprovare più tardi.");
					}
					// Creo la requst con contenuto
					requestConContenutoConXML = createRequetNotificaConContenuto(attoForm,tAtto,tProcedimento,ufficioMittente,nomeECongomeUtente);
					requestConContenutoConXML.getNotifica().getAllegati().add(idPDOCXml.toString());
					responseConContenutoConXML = invocaServizioRichiestaPerDestinatariPassati(requestConContenutoConXML, listaDestinatariSelezionatiConContenutoConXML);
				} else {
					// Sto notificando senza XML (Vecchio giro di notifiche)
					requestConContenutoSenzaXML = createRequetNotificaConContenuto(attoForm,tAtto,tProcedimento,ufficioMittente,nomeECongomeUtente);
					responseConContenutoSenzaXML = invocaServizioRichiestaPerDestinatariPassati(requestConContenutoSenzaXML, listaDestinatariSelezionatiConContenuto);
				}
			}
			
			ServiceResult result = new ServiceResult(Result.OK);
			// Verifico se è stata effettuato almeno una trasmissione
			if (	(responseSenzaContenuto==null || responseSenzaContenuto.getTrasmissioni().size()==0) && 
					(responseConContenutoSenzaXML==null || responseConContenutoSenzaXML.getTrasmissioni().size()==0) && 
					(responseConContenutoConXML==null || responseConContenutoConXML.getTrasmissioni().size()==0) 
					) {
				String msgErrori = valorizzaMessaggioErroreDaEccezioni(responseSenzaContenuto,responseConContenutoSenzaXML,responseConContenutoConXML);
				return new ServiceResult(Result.KO,"Nessuna trasmissione inviata. Elenco Errori:<br/> " + msgErrori );
			
			} else {
				// Se è stata effettuato almeno una trasmissione scrivo sul db
				result = notificheDao.insertNotifica(
								attoForm,
								tAtto,
								procedimento,
								idUfficioMitt,
								responseSenzaContenuto,
								responseConContenutoSenzaXML,
								responseConContenutoConXML,
								requestSenzaContenuto,
								requestConContenutoSenzaXML,
								requestConContenutoConXML, 
								descUtente,
								listaDestinatariSelezionatiSenzaContenuto,
								listaDestinatariSelezionatiConContenuto,
								xmlDati,
								idPDOCXml);
				
			}
			
			return result;
		} catch (Exception e) {
			logger.error("Errore durante la chiama al WSTrasmissione: " + e.getMessage() );
			e.printStackTrace();
			return new ServiceResult(Result.KO,"Errore durante la chiama al WSTrasmissione: " + e.getMessage() );
		}
	}


	private String valorizzaMessaggioErroreDaEccezioni(RisultatoComunicazione responseSenzaContenuto,
			RisultatoComunicazione responseConContenutoSenzaXML, RisultatoComunicazione responseConContenutoConXML) {
		String msgErrori = "";
		if (responseSenzaContenuto!=null) {
			for (InfoEccezione eccezione : responseSenzaContenuto.getEccezioni()) {
				msgErrori += eccezione.getDestinatario().getRuolo() + "-" + eccezione.getDestinatario().getCodice() + "; Eccezione:" + eccezione.getMessaggioEccezione() + "<br/>";
			}
		}
		if (responseConContenutoSenzaXML!=null) {
			for (InfoEccezione eccezione : responseConContenutoSenzaXML.getEccezioni()) {
			msgErrori += eccezione.getDestinatario().getRuolo() + "-" + eccezione.getDestinatario().getCodice() + "; Eccezione:" + eccezione.getMessaggioEccezione() + "<br/>";
			}
		}
		if (responseConContenutoConXML!=null) {
			for (InfoEccezione eccezione : responseConContenutoConXML.getEccezioni()) {
				msgErrori += eccezione.getDestinatario().getRuolo() + "-" + eccezione.getDestinatario().getCodice() + "; Eccezione:" + eccezione.getMessaggioEccezione() + "<br/>";
			}
		}
		return msgErrori;
	}

	/***
	 * controlla se nella lista 
	 * @param listaDestinatari
	 * @return
	 */
	public Boolean controllaPresenzaAmministratori(List<DestinatarioDto> listaDestinatari) {
		for (DestinatarioDto destinatarioDto : listaDestinatari) {
			if (isAmministratore(destinatarioDto)) {
				return true;
			}
		}
		return false;
	}
	
	/***
	 * Restituisce true se il dto è relativo ad un ruolo amministratore
	 * @param destinatarioDto
	 * @return
	 */
	private Boolean isAmministratore(DestinatarioDto destinatarioDto) {
		if (destinatarioDto.getCODI_K_RUOLO_DEST()!=null && destinatarioDto.getCODI_K_RUOLO_DEST().longValue() == Constants.TIPO_RUOLO_AMMINISTRATORE) {
			return true;
		} else {
			return false;
		}
	}
		
	
	/***
	 * 
	 * @param attoForm
	 * @param idUfficio
	 * @param idProcedimento
	 * @return
	 */
	private byte[] preparaXmlDatiToolAdmin(final AttoForm attoForm, final int idUfficio, final long idProcedimento) {
		// usando il TrasmissioneAmministratoriService trasmissioneService				
		TrasmissioneRelazioneAmm relazione = new TrasmissioneRelazioneAmm();
		relazione.setProcedimento(trasmissioneAmministratoriService.getProcedimento(idProcedimento));
		relazione.setProvvedimento(trasmissioneAmministratoriService.getProvvedimento(attoForm.getCodAtto(), idUfficio));
				   		   		
		// carica lista beni		   
		relazione.setBeni(trasmissioneAmministratoriService.getTBeni(idProcedimento, attoForm.getCodAtto(), idUfficio));
			
		// carica misure		   
		relazione.setMisure(trasmissioneAmministratoriService.getMisure(idProcedimento, attoForm.getCodAtto(), idUfficio));
		
		// carica soggetti
		relazione.setSoggetti(trasmissioneAmministratoriService.getSoggetti(idUfficio, idProcedimento, attoForm.getCodAtto()));
		
		// carica amministrazioni		
		relazione.setAmministrazioni(trasmissioneAmministratoriService.getAmministrazioni(idProcedimento, attoForm.getCodAtto(), idUfficio, false));		
//		relazione.setAmministrazioni(trasmissioneAmministratoriService.getAmministrazioni(idProcedimento, attoForm.getCodAtto(), idUfficio, boolean escludiAgenzia));
		

		// crea XML
		return relazione.creaRelazione();
	}
	
	
	private InviaComunicazione createRequetNotificaConContenuto(
			NotificaAttoForm attoForm,
			TAtto tAtto,
			TProcedimento procedimento,
			TAnUfficio ufficioMittente, 
			String nomeECongomeUtente) throws Exception {
		// Creo la requst
		InviaComunicazione requestConContenuto = createRequetNotifica(attoForm,tAtto,procedimento,ufficioMittente,nomeECongomeUtente);
		requestConContenuto.getNotifica().getAllegati().clear();
		if (tAtto.getNumeDocumentoPdoc()==null) {
			throw new Exception("L'atto non è stato ancora acquisito dal documentale");
		}
		// Aggiungo l'allegato pdf che comunque deve essere aggiunto
		requestConContenuto.getNotifica().getAllegati().add(tAtto.getNumeDocumentoPdoc().toString());
		requestConContenuto.getNotifica().setAllegaContenuti(true);
		return requestConContenuto;
		
	}
	
	/***
	 * Valorizza i principali campi della chiamata al WS
	 * @param attoForm
	 * @param tAtto
	 * @param procedimento
	 * @param ufficioMittente
	 * @param nomeECongomeUtente
	 * @return
	 */
	private InviaComunicazione createRequetNotifica(
			NotificaAttoForm attoForm,
			TAtto tAtto,
			TProcedimento procedimento,
			TAnUfficio ufficioMittente, 
			String nomeECongomeUtente) {
		ObjectFactory factory = new ObjectFactory();
		// Valorizzo i dati generali da passare al WS
		InviaComunicazione request = factory.createInviaComunicazione();
		Notifica notifica = factory.createNotifica();
		IdFascicolo fascicolo = factory.createIdFascicolo();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String dataEvento = "";
		if (attoForm.getDataRegistrazione()!=null) {
			dataEvento = sdf.format(attoForm.getDataRegistrazione());
		} else if (attoForm.getDataEmissione()!=null) {
			dataEvento = sdf.format(attoForm.getDataEmissione());
		} else if (attoForm.getDataDeposito()!=null) {
			dataEvento = sdf.format(attoForm.getDataDeposito());
		}
		
		// Valorizzazione dell'oggetto notifica
		notifica.setAvvisoDownload(attoForm.getFlgDatiSensibili());
		notifica.setOggettoComunicazione(attoForm.getOggetto());
		String nomeCancelliere = nomeECongomeUtente;
		notifica.getAttributi().add(nuovoAttibuto("DENOMINAZIONE_CANCELLIERE",nomeCancelliere) );
		notifica.getAttributi().add(nuovoAttibuto("DESCRIZIONE_EVENTO",attoForm.getTesto()) );
		notifica.getAttributi().add(nuovoAttibuto("DATA_EVENTO",dataEvento) );
		
		notifica.getAttributi().add(nuovoAttibuto("TIPO_UFFICIO",ufficioMittente.getDescTipoUfficio()));
		notifica.getAttributi().add(nuovoAttibuto("SEDE_GIUDIZIARIA",ufficioMittente.getDescSedeUfficio()));
		String numeroRegistro = "N/D";
		if(Utility.isValid(attoForm.getNumeAnnoAtto()) && Utility.isValid(attoForm.getNumeAtto())) {
			numeroRegistro = attoForm.getNumeAnnoAtto().concat(Constants.FormattazioneAnnoNumeroAtto.DIVISORE_ANNO_NUMERO_ATTO).concat(attoForm.getNumeAtto());
		}
		notifica.getAttributi().add(nuovoAttibuto("NUMERO_REGISTRO",numeroRegistro));
		notifica.getAttributi().add(nuovoAttibuto("TIPOLOGIA_ATTO",commonDao.getCatAttoById(tAtto.getTTpCatAtto().getCodiKIdCatAtto()).getDescCatAtto()));
		
		// Valorizzazione info fascicolo
		fascicolo.setAnno(procedimento.getNumeAnnoProc());
		fascicolo.setNumero(procedimento.getNumeProc().intValue());
		
		// fascicolo.setSubProcedimento(null); // Non valorizzato
		fascicolo.setRegistro("MP"); // Registro delle misure di prevenzione
		fascicolo.setUfficio(ufficioMittente.getDescUffGiudiz()); 
		fascicolo.setIdSgr(procedimento.getCodiKIdProc()+"");
		fascicolo.setIdRepository(procedimento.getDescFascicoloPdoc());  
		
		request.setNotifica(notifica);
		request.setFascicolo(fascicolo);
		return request;
	}
	
	
	/***
	 * Crea la copia del documento dell'atto sul documentale per tutti i flussi di 
	 * destinazione e valorizza al campo NUME_COPIA_PDOC di T_ATTO con il documento copia
	 * L'operazione viene eseguita solo se il campo NUME_COPIA_PDOC = null (ovvero non è mai stata eseguita)
	 * @param attoForm
	 * @param tAtto
	 * @param procedimento
	 * @param idUfficio
	 */
	private void creaCopiaDocumentoSuDocumentaleEPDOC(NotificaAttoForm attoForm, TAtto tAtto, ProcedimentoForm procedimento, int idUfficio,List<DestinatarioDto> listaDestinatariDiTipoUfficio) throws Exception {
		if (tAtto.getNumeCopiaPdoc() == null && Utility.isValid(listaDestinatariDiTipoUfficio)) {
	
			// Estraggo la lista dei codugne da passare al documentale per la visibilita`
			String listCodugna = "";
			for (DestinatarioDto destinatarioDto : listaDestinatariDiTipoUfficio) {
				listCodugna += destinatarioDto.getCODI_DEST() + DocumentaleService.SEPARATORE_CAMPI_IN_STRINGA_CODUGNA;
			}
			// Rimozione dell'ultimo "DocumentaleService.SEPARATORE_CAMPI_IN_STRINGA_CODUGNA"
			listCodugna = StringUtils.substringBeforeLast(listCodugna, DocumentaleService.SEPARATORE_CAMPI_IN_STRINGA_CODUGNA);
			BigInteger numeCopiaPDOC = documentaleService.copiaDocumento(BigInteger.valueOf(tAtto.getNumeDocumentoPdoc()), tAtto.getDescNomeDocumento(), tAtto.getDescTipoDocumento(), tAtto.getDescNomeDocumento(), "Copia creata da ufficio mittente per notifica via PEC.", listCodugna, idUfficio);
			if (numeCopiaPDOC == null) {
				throw new Exception("Errore durante la chiamata al documentale per la copia del documento con attribuzione visibilita` su ufficio di destinazione");
			}
			notificheDao.modificaCopiaPDOC(numeCopiaPDOC.longValue(), tAtto.getId().getCodiKIdAtto(), tAtto.getId().getCodiKIdUffOwner());
			//tAtto.setNumeCopiaPdoc(tAtto.getNumeDocumentoPdoc()); // commentato perchè dovrebbe funzionare la copia.
			// IMPOSTO IL NUMERO COPIA CON QUELLO CORRENTE
			tAtto.setNumeCopiaPdoc(numeCopiaPDOC.longValue()); 
		} else { //F.C. Ticket#202202240113 SIT-MP - errore download pdf e registrazione scambio INIZIO
			// se esiste già devo aggiornare le security
			// Estraggo la lista dei codugne da passare al documentale per la visibilita`
			String listCodugna = "";
			for (DestinatarioDto destinatarioDto : listaDestinatariDiTipoUfficio) {
				listCodugna += destinatarioDto.getCODI_DEST() + DocumentaleService.SEPARATORE_CAMPI_IN_STRINGA_CODUGNA;
			}
			//F.C. MEV 2023-024 se la lista è vuota esco
			if(listCodugna==null | listCodugna.length()==0) {
				return;
			}
			// Rimozione dell'ultimo "DocumentaleService.SEPARATORE_CAMPI_IN_STRINGA_CODUGNA"
			listCodugna = StringUtils.substringBeforeLast(listCodugna, DocumentaleService.SEPARATORE_CAMPI_IN_STRINGA_CODUGNA);
			TAnUtenzePdoc utenzaPdoc = commonDao.getUtenzaPdoc(idUfficio);
			documentaleService.estendiSicurezza(BigInteger.valueOf(tAtto.getNumeCopiaPdoc()), listCodugna, utenzaPdoc);
		} //F.C. Ticket#202202240113 SIT-MP - errore download pdf e registrazione scambio INIZIO
	}

	private RisultatoComunicazione invocaServizioRichiestaPerDestinatariPassati(InviaComunicazione request, List<DestinatarioDto>  listaDestinatariSelezionati ) {
		// Valorizzazione dei destinatari
		Notifica notifica = request.getNotifica();
		notifica.getDestinatari().clear();
		for (DestinatarioDto destinatarioDto : listaDestinatariSelezionati) {
			InfoDestinatario infoDestinatario = new InfoDestinatario();
			infoDestinatario.setCodice(destinatarioDto.getCODI_DEST());
			infoDestinatario.setRuolo(destinatarioDto.getDESC_RUOLO_DEST());
			notifica.getDestinatari().add(infoDestinatario);	
		}
		//FC modificata la firma del metodo aggiungendo gli info destinatari
		return serviziTrasmissioneServiceClient.inviaComunicazione(request, notifica).getReturn();
	}
	
	
	
	
	private Attributo nuovoAttibuto(String nome,String valore) {
		Attributo attributo = new Attributo();
		attributo.setNome(nome);
		attributo.setValore(valore);
		return attributo;
	}
	
	
	/***
	 * Caricamento della lista delle notifiche da visualizzata sulla maschera lista notifiche
	 * @param idAtto
	 * @param idUfficio
	 * @return
	 */
	public ArrayList<NotificaDettaglioForm> caricaListaNotifiche(Long idAtto,int idUfficio) {
		ArrayList<NotificaDettaglioForm> listaNotifiche =  new ArrayList<NotificaDettaglioForm>();
		List<TNotifiche> lista = notificheDao.getListaNotifiche(idAtto,idUfficio);
		for (TNotifiche tNotifiche : lista) {
			NotificaDettaglioForm notifica = new NotificaDettaglioForm();
			notifica.setDataInvio(tNotifiche.getDataInvio());
			notifica.setDatiSensibili((tNotifiche.getFlgDatiSensibili()=='S'?true:false));
			notifica.setEvento(tNotifiche.getDescEvento());
			notifica.setOggetto(tNotifiche.getDescOggetto());
			for (TNotifRichieste tNotifRichieste : tNotifiche.getTNotifRichiestes()) {
				
				for (TNotifDest tNotifDest : tNotifRichieste.getTNotifDests()) {
					NotificaDettaglioDestinatarioForm dettaglioDestinatarioForm = new NotificaDettaglioDestinatarioForm();
					dettaglioDestinatarioForm.setDestinatario(tNotifDest.getDescDestinataro());
					
					for (TNotifRicevute ricevuta : tNotifDest.getTNotifRicevutes()) {
						long idAttoRicevuta = ricevuta.getCodiFkIdAttoRicevuta();
						int idUffRicevuta = ricevuta.getCodiFkIdUffOwnerRicevuta();
						TAtto attoRicevuta = attoDao.getAttoById(new TAttoId(idAttoRicevuta, idUffRicevuta));
						dettaglioDestinatarioForm.getMappaRicevute().put(attoRicevuta.getTTpCatAtto().getDescCatAtto(), attoRicevuta);
					}
					dettaglioDestinatarioForm.setRicevuta(""); // Non viene più utilizzato perche le ricevure sono diventate n
				
					TNotifAmm notifAmm = notificheDao.getTNotifAmmByid(tNotifDest.getCodiKIdNotifDest());  
					if (notifAmm!=null) {
						dettaglioDestinatarioForm.setHasAmministratori(true);
					}
					dettaglioDestinatarioForm.setCodiKIdNotifDest(tNotifDest.getCodiKIdNotifDest());
					// Invocazione del WS per determinare lo stato della trasmissione
					valorizzaStatoComunicazione(dettaglioDestinatarioForm, tNotifDest);
					dettaglioDestinatarioForm.setTipoDestinatario(tNotifDest.getTTpRuoliDest().getDescRuoloDestSitmp());
					notifica.getDettaglioDestinatari().add(dettaglioDestinatarioForm);		
				}
			}
			listaNotifiche.add(notifica);
		}
		return listaNotifiche;
	}

	/***
	 * Vaolorizza lo stato della comunicazione dell'oggetto NotificaDettaglioDestinatarioForm
	 * @param dettaglioDestinatarioForm
	 * @param tNotifDest
	 */
	private void valorizzaStatoComunicazione(NotificaDettaglioDestinatarioForm dettaglioDestinatarioForm, TNotifDest tNotifDest) {
		try {
		
			// Chiamata al WS per la lettura dello stato della trasmissione.
			logger.info("Inizio - Invocazione servize GL queryStatoTrasmissione per verifica dello stato della comunicazione...");
			QueryStatoTrasmissioneResponse queryStatoTrasmissioneResponse = serviziTrasmissioneServiceClient.queryStatoTrasmissione(tNotifDest.getDescCodiTrasmissione());
			logger.info("Fine - Invocazione servize GL queryStatoTrasmissione per verifica dello stato della comunicazione...");
			String stato = notificheDao.decodificaStatoTrasmissione(queryStatoTrasmissioneResponse .getReturn().getStato());
			dettaglioDestinatarioForm.setStatoComunicazione(stato);
		} catch (Exception e) {
			dettaglioDestinatarioForm.setStatoComunicazione("Sistema GL Non Disponibile");
		}
	}

	public List<VNotifiche> getListaNotifichePerData(int idUfficioMitt, int idTipoUff, NotificheEComunicazioniForm notificheEComunicazioniForm) {
		return notificheDao.getListaNotifichePerFiltro(idUfficioMitt, idTipoUff, notificheEComunicazioniForm);
	}
	
	

	public TNotifAmm getTNotifAmmById(Long idNotifAmm) {
		return notificheDao.getTNotifAmmByid(idNotifAmm);
	}

}
