package it.giustizia.smp.service.webservices;

import it.giustizia.smp.dao.AttoDao;
import it.giustizia.smp.dao.CommonDao;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.OmonimoForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.model.TAnComuni;
import it.giustizia.smp.model.TAnStato;
import it.giustizia.smp.model.TAnUfficio;
import it.giustizia.smp.model.TTpTipoUfficio;
import it.giustizia.smp.model.TUtente;
import it.giustizia.smp.webservice.client.casellario.ricerca.ANAGRAFICA;
import it.giustizia.smp.webservice.client.casellario.ricerca.CasellarioRicercaServiceClient;
import it.giustizia.smp.webservice.client.casellario.ricerca.DATA;
import it.giustizia.smp.webservice.client.casellario.ricerca.DATIANAGRAFICI;
import it.giustizia.smp.webservice.client.casellario.ricerca.DATICERTIFICATO;
import it.giustizia.smp.webservice.client.casellario.ricerca.DATIRICHIESTACERTIFICATO;
import it.giustizia.smp.webservice.client.casellario.ricerca.DATIRISPOSTACERTIFICATO;
import it.giustizia.smp.webservice.client.casellario.ricerca.DATIUFFICIO;
import it.giustizia.smp.webservice.client.casellario.ricerca.DATIUTENTE;
import it.giustizia.smp.webservice.client.casellario.ricerca.OMONIMO;
import it.giustizia.smp.webservice.client.casellario.ricerca.SESSO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.axiom.attachments.ByteArrayDataSource;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;

public class CasellarioRicercaService implements ICasellarioRicercaService {

	@Autowired
	private CasellarioRicercaServiceClient casellarioRicercaServiceClient;

	@Autowired
	private AttoDao attoDao;

	@Autowired
	private CommonDao commonDao;

	public class RispostaRicerca {
		public DATIRISPOSTACERTIFICATO response;
		public Long idAttoInserito ;
		
		public RispostaRicerca(DATIRISPOSTACERTIFICATO response, Long idAttoInserito) {
			super();
			this.response = response;
			this.idAttoInserito = idAttoInserito;
		}
		
		
		public DATIRISPOSTACERTIFICATO getResponse() {
			return response;
		}
		public void setResponse(DATIRISPOSTACERTIFICATO response) {
			this.response = response;
		}
		public Long getIdAttoInserito() {
			return idAttoInserito;
		}
		public void setIdAttoInserito(Long idAttoInserito) {
			this.idAttoInserito = idAttoInserito;
		}
		
		
	}
	
	public RispostaRicerca ricerca(final String descUtente, final TUtente tUtente, final long idUffOwn, AttoForm attoForm,ProcedimentoForm procedimentoForm,String idNSC,String tipoCertificato,String authUsername,String authPassword) {
		
		SoggettoForm soggetto = attoForm.getSoggetti().get(0);
		
		// Estraggo l'ufficio per leggere i valori del casellario
		TAnUfficio ufficioCorrente = commonDao.getUfficioById((int)idUffOwn); 
				
		// Estraggo l'ufficio per leggere i valori del casellario
		TAnComuni comuneUfficioCorrente = commonDao.getComuneById(ufficioCorrente.getTAnComuni().getCodiKIdComune());
		String nscComuneUfficioCorrente = comuneUfficioCorrente.getDescCodiNSC(); 
		
		// Estraggo il tipo ufficio corrente
		TTpTipoUfficio tipoUfficio = commonDao.getTipoUfficioById(ufficioCorrente.getTTpTipoUfficio().getCodiKIdTipoUff());

		// String nscUfficioCorrenteCodiAutorita = tipoUfficio.getDescCodiAutoritaNSC(); 
		String nscUfficioCorrenteCodiUfficio = tipoUfficio.getDescCodiUfficioNSC(); 

		String nscComuneNascita = null;
		// Estraggo l'ufficio per leggere i valori del casellario
		if (soggetto.getComuneNascita().getId()!=null) {
			TAnComuni comuneNascita = commonDao.getComuneById(Integer.parseInt(soggetto.getComuneNascita().getId()));
			nscComuneNascita = comuneNascita.getDescCodiNSC(); 
		}
		
		String nscStatoNascita = null;
		if (soggetto.getStatoNascita().getId()!=null) {
			TAnStato statoNascita = commonDao.getStatoById(Integer.parseInt(soggetto.getStatoNascita().getId()));
			nscStatoNascita = statoNascita.getDescCodiNSC(); 
		}
		
		
		// Imposto i valori dell'utente e dell'ufficio da passare
		DATIUFFICIO datiUfficio = creaDatiUfficio(nscComuneUfficioCorrente,nscUfficioCorrenteCodiUfficio);
		DATIUTENTE datiUtente = creaDatiUtente(tUtente,datiUfficio);
		ANAGRAFICA anagrafica = new ANAGRAFICA();
		
		// Impostazione delle informazioni anagrafiche del soggetto
		DATIANAGRAFICI datiAnagrafici = new DATIANAGRAFICI();
		datiAnagrafici.setPROGANAGRAFICA(BigInteger.valueOf(soggetto.getIdSoggetto()));
		datiAnagrafici.setPERSCOGNOME(soggetto.getCognome());
		datiAnagrafici.setPERSNOME(soggetto.getNome());
		datiAnagrafici.setCODILUOGONASCITA(nscComuneNascita); 
		datiAnagrafici.setCODISTATOESTERONAS(nscStatoNascita ); 
		datiAnagrafici.setDESCCOMUNEESTERO(soggetto.getDescrizioneComuneEsteroNascita() ); 
		datiAnagrafici.setDATANASCITA(convertiData(soggetto.getDataNascita()));
		datiAnagrafici.setCODIFISCALE(soggetto.getCodiceFiscale());
		datiAnagrafici.setCODIIMPRONTADIGITALE(soggetto.getCui()); // null da specifica
		datiAnagrafici.setPERSPATERNITA(soggetto.getNomePaternita());
		datiAnagrafici.setPERSCOGNOMEMADRE(soggetto.getCognomeMaternita());
		datiAnagrafici.setPERSNOMEMADRE(soggetto.getNomeMaternita());
		datiAnagrafici.setSESSO(convertiSesso(soggetto.getSesso()));
		
		DATICERTIFICATO datiCertificato = new DATICERTIFICATO();
		datiCertificato.setANNOPROC(BigInteger.valueOf(procedimentoForm.getAnno()));
		datiCertificato.setNUMEROPROC(BigInteger.valueOf(procedimentoForm.getNumero()));		
		datiCertificato.setTIPOCERTIFICATO(tipoCertificato);
		
		anagrafica.setDATIANAGRAFICI(datiAnagrafici);
		anagrafica.setDATICERTIFICATO(datiCertificato);
		
		DATIRICHIESTACERTIFICATO request = new DATIRICHIESTACERTIFICATO();
		// Imposto tutti i setter della richiesta al casellario
		request.setANAGRAFICA(anagrafica);
		request.setDATIUTENTE(datiUtente);
		if (idNSC!=null && idNSC.length()>0) {
			request.setPROGANAGRAFICANSC(BigInteger.valueOf(Long.parseLong(idNSC)));
		}
		Long idAtto =null;
		// Invoco la chiamata al servizio
		DATIRISPOSTACERTIFICATO response = casellarioRicercaServiceClient.ricercaCertificato(request,authUsername,authPassword);
		if (response.getArrayEsito().getESITO().get(0).getCODICE().equals("000")) {
			// Se l'esito è ok procedo con l'inserimento
			// Nel caso di invio con omonimi valorizzo il campo numSoggettoCasellario
			Long numSoggettoCasellario = null;
			if (idNSC!=null && !idNSC.equals("")) numSoggettoCasellario = Long.parseLong(idNSC);
			
			//Valorizzare xmlComunicazione e fileEstrattoCertif con blob proveniente da chiamata
			Blob xmlRichiesta = serializeInBlob(marshaller(request));
			Blob xmlRisposta = serializeInBlob(marshaller(response));
			Character flagStatoInvio = 'S'; //INVIATO
			
			idAtto =  attoDao.inserisciCertificatoPenale(
					procedimentoForm.getIdProcedimento(), 
					idUffOwn, 
					descUtente, 
					attoForm.getDescSedeUfficio(), 
					attoForm.getDescTipoUfficio(), 
					soggetto.getIdSoggetto(), 
					flagStatoInvio, 
					xmlRichiesta, 
					xmlRisposta);
			
		}
		return new RispostaRicerca(response, idAtto);
	}
			
	
	private DATIUFFICIO creaDatiUfficio(String nscComuneUfficioCorrente, String nscUfficioCorrenteCodiUfficio) {
		DATIUFFICIO datiUfficio = new DATIUFFICIO();
		datiUfficio.setCODICESEDEUFFICIO(nscComuneUfficioCorrente);
		datiUfficio.setCODICETIPOUFFICIO(nscUfficioCorrenteCodiUfficio);
		return datiUfficio;
	}

	private DATIUTENTE creaDatiUtente(TUtente tUtente, DATIUFFICIO datiUfficio) {
		DATIUTENTE datiUtente = new DATIUTENTE();
		datiUtente.setCOGNOMEUTENTE(tUtente.getCognome());
		datiUtente.setNOMEUTENTE(tUtente.getNome());
		/*
		datiUtente.setUSERNAMESIPPI(tUtente.getUserid());
		System.out.println("Username usata:" + tUtente.getUserid());
		*/
		datiUtente.setUSERNAMESIPPI(tUtente.getCodiceFiscale());
		System.out.println("Username usata:" + tUtente.getCodiceFiscale());
		
		datiUtente.setDATIUFFICIO(datiUfficio);
		
		
		
		
		return datiUtente;
	}
	
	
	private Blob serializeInBlob(byte[] byteArray) {
		return Hibernate.createBlob(byteArray);
	}

	/**
	 * Esegue il marshaller per la response e la request del servizio
	**/
	private byte[] marshaller(Object request) {
		byte[] firmaBytes = new byte[1000];
		// ByteArrayInputStream bis = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			JAXBContext contextObj = JAXBContext.newInstance(request.getClass());
			Marshaller marshallerObj = contextObj.createMarshaller();
			marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshallerObj.marshal(request, bos);
			firmaBytes = bos.toByteArray();
			// bis = new ByteArrayInputStream(bos.toByteArray());
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return firmaBytes;
	}

	

	

	/**
	 * Converte un oggetto java date nella data accettata dal servizio del casellario
	 * 
	 * @param date
	 * @return
	 */
	private static SESSO convertiSesso(String sessoString) {
		if (sessoString.toUpperCase().equals("M")) {
			return SESSO.M;
		} else if (sessoString.toUpperCase().equals("F")) {
			return SESSO.M;
		}
		return null;
	}

	/**
	 * Converte un oggetto java date nella data accettata dal servizio del casellario
	 * 
	 * @param date
	 * @return
	 */
	private static DATA convertiData(Date date) {
		if (date == null)
			return null;
		DATA data = new DATA();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		data.setANNO("" + year);
		data.setMESE("" + month);
		data.setGIORNO("" + day);
		return data;
	}

	/**
	 * Converte un oggetto DATA del servizio in una data date
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public Date convertiData(DATA data) throws ParseException {
		if (data == null)
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.parse(data.getGIORNO() + "/" + data.getMESE() + "/" + data.getANNO());
	}


	public List<OmonimoForm> extractListaOmonimi(RispostaRicerca rispostaRicerca) {
		ArrayList<OmonimoForm> listOmonimoForms = new ArrayList<OmonimoForm>();

		for (OMONIMO omonimo : rispostaRicerca.getResponse().getArrayOmonimi().getOMONIMO()) {
			TAnStato statoEstero = commonDao.getStatoByIdCasellario(omonimo.getDATIANAGRAFICI().getCODISTATOESTERONAS());
			String descStato = "";
			if (statoEstero != null) {
				descStato = statoEstero.getDescStato();
			}
			String nome = omonimo.getDATIANAGRAFICI().getPERSNOME() == null ? "" : omonimo.getDATIANAGRAFICI().getPERSNOME().toUpperCase();
			String cognome = omonimo.getDATIANAGRAFICI().getPERSCOGNOME() == null ? "" : omonimo.getDATIANAGRAFICI().getPERSCOGNOME().toUpperCase();
			String comuneNascita = "";
			TAnComuni anComuni = commonDao.getComuneByIdCasellario(omonimo.getDATIANAGRAFICI().getCODILUOGONASCITA());
			if (anComuni != null) {
				comuneNascita = anComuni.getDescComune();
			}
			if (omonimo.getDATIANAGRAFICI().getDESCCOMUNEESTERO() != null) {
				comuneNascita += omonimo.getDATIANAGRAFICI().getDESCCOMUNEESTERO();
			}
			String natoNata = "nato";
			if (omonimo.getDATIANAGRAFICI().getSESSO() != null && omonimo.getDATIANAGRAFICI().getSESSO().equals(SESSO.F)) {
				natoNata = "nata";
			}
			String soggetto = cognome + " " + nome + " " + natoNata + " in " + descStato + " ( " + comuneNascita + " ) " + " il "
					+ omonimo.getDATIANAGRAFICI().getDATANASCITA().getGIORNO() + "/" + omonimo.getDATIANAGRAFICI().getDATANASCITA().getMESE() + "/"
					+ omonimo.getDATIANAGRAFICI().getDATANASCITA().getANNO();

			
			OmonimoForm omonimoForm = new OmonimoForm(omonimo.getDATIANAGRAFICI().getPROGANAGRAFICA().toString(), soggetto, omonimo.getDATIANAGRAFICI().getCODIFISCALE(), "");
			
			String paternita = omonimo.getDATIANAGRAFICI().getPERSPATERNITA();
			String maternita = (omonimo.getDATIANAGRAFICI().getPERSCOGNOMEMADRE()!=null?omonimo.getDATIANAGRAFICI().getPERSCOGNOMEMADRE() + " ":"")+
							   (omonimo.getDATIANAGRAFICI().getPERSNOMEMADRE()!=null?omonimo.getDATIANAGRAFICI().getPERSNOMEMADRE():"");

			omonimoForm.setPaternita(paternita);
			omonimoForm.setMaternita(maternita);
			
			//omonimo.getDATIANAGRAFICI().getCODIIMPRONTADIGITALE();
			
			if (omonimo.getDATIANAGRAFICI().getFLAGOMONINOSINONIMO()!=null && omonimo.getDATIANAGRAFICI().getFLAGOMONINOSINONIMO().length()>0) {
				switch (omonimo.getDATIANAGRAFICI().getFLAGOMONINOSINONIMO().charAt(0) ) {
				case 'O':
					omonimoForm.setTipo("Omonimo");
					break;
				case 'S':
					omonimoForm.setTipo("Sinonimo");
					break;
				case 'T':
					omonimoForm.setTipo("Trovato");
					break;
				default:
					break;
				}
				
			}
			
			listOmonimoForms.add(omonimoForm);
			
			
			
		}
		return listOmonimoForms;
	}


	public byte[] unmarhallerRisposta(Blob xmlRisposta) throws JAXBException, IOException {
		DataHandler dh = serializzaFile(xmlRisposta);
		DATIRISPOSTACERTIFICATO risposta = null;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		dh.writeTo(bout);
		JAXBContext jc = JAXBContext.newInstance(DATIRISPOSTACERTIFICATO.class);
		ByteArrayInputStream bis = new ByteArrayInputStream(bout.toByteArray());
		Unmarshaller u = jc.createUnmarshaller();
		risposta = (DATIRISPOSTACERTIFICATO) u.unmarshal(bis);
		return risposta.getCERTIFICATOPENALE();
	}

	private DataHandler serializzaFile(Blob blobFile) {
		DataHandler dh = null;
		try {
			InputStream is = blobFile.getBinaryStream();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int next = is.read();
			while (next > -1) {
				bos.write(next);
				next = is.read();
			}
			bos.flush();
			byte[] fileContent = bos.toByteArray();
			ByteArrayDataSource rawData = new ByteArrayDataSource(fileContent);
			dh = new DataHandler(rawData);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return dh;
		} catch (IOException e) {
			e.printStackTrace();
			return dh;
		} catch (SQLException e) {
			e.printStackTrace();
			return dh;
		}
		return dh;
	}
}

