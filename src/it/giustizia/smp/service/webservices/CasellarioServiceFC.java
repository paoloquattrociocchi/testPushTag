package it.giustizia.smp.service.webservices;

import it.giustizia.smp.dao.AttoDao;
import it.giustizia.smp.dao.CommonDao;
import it.giustizia.smp.dao.ProcedimentoDao;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.ComunicazioneForm;
import it.giustizia.smp.form.OmonimoForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.model.TAnComuni;
import it.giustizia.smp.model.TAnStato;
import it.giustizia.smp.model.TAnUfficio;
import it.giustizia.smp.model.TComunicazione;
import it.giustizia.smp.model.TRelProcProc;
import it.giustizia.smp.model.TTpCatAtto;
import it.giustizia.smp.model.TTpTipoMisura;
import it.giustizia.smp.model.TTpTipoUfficio;
import it.giustizia.smp.model.TUtente;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.webservice.client.casellario.esecuzione.CHIAVI;
import it.giustizia.smp.webservice.client.casellario.esecuzione.CasellarioServiceClientFC;
import it.giustizia.smp.webservice.client.casellario.esecuzione.DATA;
import it.giustizia.smp.webservice.client.casellario.esecuzione.DATIANAGRAFICAART130;
import it.giustizia.smp.webservice.client.casellario.esecuzione.DATIANAGRAFICAPRINCIPALE;
import it.giustizia.smp.webservice.client.casellario.esecuzione.DATICHIAMATATRASFERIMENTO;
import it.giustizia.smp.webservice.client.casellario.esecuzione.DATIOPERAZIONE;
import it.giustizia.smp.webservice.client.casellario.esecuzione.DATIPROVVEDIMENTO;
import it.giustizia.smp.webservice.client.casellario.esecuzione.DATIRISPOSTATRASFERIMENTO;
import it.giustizia.smp.webservice.client.casellario.esecuzione.DATIUFFICIO;
import it.giustizia.smp.webservice.client.casellario.esecuzione.DATIUTENTE;
import it.giustizia.smp.webservice.client.casellario.esecuzione.DURATA;
import it.giustizia.smp.webservice.client.casellario.esecuzione.ENTITA;
import it.giustizia.smp.webservice.client.casellario.esecuzione.MISURADIPREVENZIONE;
import it.giustizia.smp.webservice.client.casellario.esecuzione.OMONIMO;
import it.giustizia.smp.webservice.client.casellario.esecuzione.OPERAZIONE;
import it.giustizia.smp.webservice.client.casellario.esecuzione.PROCEDIMENTO;
import it.giustizia.smp.webservice.client.casellario.esecuzione.PROVVEDIMENTOESECUZIONEMP;
import it.giustizia.smp.webservice.client.casellario.esecuzione.SESSO;
import it.giustizia.smp.webservice.utility.JAXBUtil;

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

public class CasellarioServiceFC implements ICasellarioServiceFC {

	@Autowired
	private CasellarioServiceClientFC casellarioServiceClientFC;

	@Autowired
	private AttoDao attoDao;

	@Autowired
	private CommonDao commonDao;
	@Autowired
	private ProcedimentoDao procedimentoDao;
	/*
	 * (non-Javadoc)
	 * 
	 * @see it.giustizia.smp.service.webservices.ICasellarioService#inviaProvvedimento (it.giustizia.smp.form.AttoForm,
	 * it.giustizia.smp.form.ComunicazioneForm)
	 */
	/* (non-Javadoc)
	 * @see it.giustizia.smp.service.webservices.ICasellarioService#inviaProvvedimento(java.lang.String, it.giustizia.smp.model.TUtente, long, it.giustizia.smp.form.AttoForm, it.giustizia.smp.form.ComunicazioneForm, it.giustizia.smp.form.ProcedimentoForm, java.lang.String)
	 */
	public DATIRISPOSTATRASFERIMENTO inviaProvvedimento(final String descUtente, final TUtente tUtente, final long idUffOwn, AttoForm attoForm,
			ComunicazioneForm comunicazioneForm, ProcedimentoForm procedimentoForm,String idNSC) throws Exception {

		// Tipi procedimenti da  14 = ISTANZA MODIFICA/REVOCA MISURE PERSONALE
		// Tipi procedimenti da  19 = ISTANZA DI CORREZIONE DEGLI ERRORI MATERIALI
		// Tipi procedimenti da  8 = AGGRAVAMENTO
		
		boolean seIstanzaErroreMateriale = false; 
		if ((Constants.TIPOPROC_CORREZIONE_ERRORI_CAM+"").equals(procedimentoForm.getTipoProcedimento().getId())) {
			seIstanzaErroreMateriale = true;
		}
		SoggettoForm soggetto;
		if (seIstanzaErroreMateriale) {
			soggetto = attoForm.getSoggetti().get(1);
		} else {
			soggetto = attoForm.getSoggetti().get(0);
		}
		
		// Estraggo il tipo atto per leggere i valori del cassellario
		String idCatAtto = attoForm.getCategoriaAtto().getId();
		TTpCatAtto catAtto = commonDao.getCatAttoById(Integer.parseInt(idCatAtto)); 
		String nscTipoProvvedimento = "" + catAtto.getDescCodiNSC(); 
		
		// Estraggo l'ufficio per leggere i valori del casellario
		TAnUfficio ufficioCorrente = commonDao.getUfficioById((int)idUffOwn); 
		
		// Estraggo il tipo ufficio corrente
		TTpTipoUfficio tipoUfficio = commonDao.getTipoUfficioById(ufficioCorrente.getTTpTipoUfficio().getCodiKIdTipoUff());
		String nscUfficioCorrenteCodiAutorita = tipoUfficio.getDescCodiAutoritaNSC(); 
		String nscUfficioCorrenteCodiUfficio = tipoUfficio.getDescCodiUfficioNSC(); 

		// Estraggo l'ufficio per leggere i valori del casellario
		TAnComuni comuneUfficioCorrente = commonDao.getComuneById(ufficioCorrente.getTAnComuni().getCodiKIdComune());
		String nscComuneUfficioCorrente = comuneUfficioCorrente.getDescCodiNSC(); 

		String nscComuneNascita = null;
		// Estraggo l'ufficio per leggere i valori del casellario
		if (soggetto.getComuneNascita().getId()!=null) {
			TAnComuni comuneNascita = commonDao.getComuneById(Integer.parseInt(soggetto.getComuneNascita().getId()));
			nscComuneNascita = comuneNascita.getDescCodiNSC(); 
		}
		
		String nscComuneMorte = null;
		// Estraggo l'ufficio per leggere i valori del casellario
		if (soggetto.getComuneMorte().getId()!=null) {
			TAnComuni comuneMorte = commonDao.getComuneById(Integer.parseInt(soggetto.getComuneMorte().getId()));
			nscComuneMorte = comuneMorte.getDescCodiNSC(); 
		}
		
		String nscStatoNascita = null;
		if (soggetto.getStatoNascita().getId()!=null) {
			TAnStato statoNascita = commonDao.getStatoById(Integer.parseInt(soggetto.getStatoNascita().getId()));
			nscStatoNascita = statoNascita.getDescCodiNSC(); 
		}
		
		String nscCodiceStatoEsteroMorte = null;
		if (soggetto.getStatoMorte().getId()!=null) {
			TAnStato statoMorte = commonDao.getStatoById(Integer.parseInt(soggetto.getStatoMorte().getId()));
			nscCodiceStatoEsteroMorte = statoMorte.getDescCodiNSC();
		}
		String nscCodiceMisuraPrevenzione = null;
		if (!seIstanzaErroreMateriale) {
			TTpTipoMisura tipoMisura = commonDao.getTipoMisuraById(Integer.parseInt(soggetto.getMisura().getTipoMisura().getId()));
			nscCodiceMisuraPrevenzione = tipoMisura.getDescCodiNSC();
		}
		String esitoNSC = null;
		
		if (!seIstanzaErroreMateriale) {
			esitoNSC = attoDao.getCodiceEsitoNSCPerFoglioComplementare(procedimentoForm.getIdProcedimento(), (int)idUffOwn, comunicazioneForm.getIdAttoEsecutivo(), comunicazioneForm.getIdMisuraPersonale());
		} else {
			// TODO FC Errore Materiale chiedere a francesco come valorizzare
			esitoNSC = attoDao.getCodiceEsitoNSCPerFoglioComplementare(procedimentoForm.getIdProcedimento(), (int)idUffOwn, comunicazioneForm.getIdAttoEsecutivo(), 0l);
		}
		
		// Impostazione dei dati dell'ufficio
		DATIUFFICIO datiUfficio = creaDatiUfficio(nscComuneUfficioCorrente,nscUfficioCorrenteCodiUfficio);
		// Impostazione dei dati dell'utente
		DATIUTENTE datiUtente = creaDatiUtente(tUtente,datiUfficio);
		// Impostazione dei dati delle chiavi
		CHIAVI chiavi = creaChiavi( soggetto, comunicazioneForm, procedimentoForm, seIstanzaErroreMateriale,  tipoUfficio);
		
		// Lettura vecchi provvedimento
		// Implementazione della ricerca del procedimento che ha data la devinitività.
		// Viene letto dalla tabelle T_COMUNICAZIONE per della scheda padre.
		List<TRelProcProc> listaProcProc =  procedimentoDao.getTRelProcProcByIdProc1Padre(procedimentoForm.getIdProcedimento());
		TComunicazione com = null;
		if (listaProcProc.size()>0) {
			// Id del procedimento padre
			long idProc2 = listaProcProc.get(0).getId().getCodiKIdProc2();
			long idSoggettoQuery = soggetto.getIdSoggetto();
			com = procedimentoDao.getComunicazioneByIdProcESogIntMis(idProc2,idSoggettoQuery);
			if (com==null) {
				// se la scheda non è stata inviata dallo stesso ufficio
				int idTipoUff = tipoUfficio.getCodiKIdTipoUff();
				com = procedimentoDao.getComunicazioneByIdProc1ETipoUff(idSoggettoQuery,idProc2,idTipoUff);
			}
		}
		if (com==null) {
			throw new Exception("Non è possibile inviare il foglio complementare perchè per il soggetto non è presente una scheda valida.");
		} 
		// se la scheda è stata inviata dallo stesso ufficio
		chiavi.setKPPSIPPI(BigInteger.valueOf(com.getCodiFkIdAttoEs()));
		DATIPROVVEDIMENTO datiprovvedimento = null;
		try {
			it.giustizia.smp.webservice.client.casellario.DATICHIAMATATRASFERIMENTO chiamataRedazioneScheda =
			JAXBUtil.unmarshall(it.giustizia.smp.webservice.client.casellario.DATICHIAMATATRASFERIMENTO.class, com.getXmlComunicazione().getBinaryStream());
		
			
			String anno = chiamataRedazioneScheda.getANAGRAFICA().getPROCEDIMENTO().getDATIPROVVEDIMENTO().getDATAPROVVEDIMENTO().getANNO();
			String mese = chiamataRedazioneScheda.getANAGRAFICA().getPROCEDIMENTO().getDATIPROVVEDIMENTO().getDATAPROVVEDIMENTO().getMESE();
			String giorno = chiamataRedazioneScheda.getANAGRAFICA().getPROCEDIMENTO().getDATIPROVVEDIMENTO().getDATAPROVVEDIMENTO().getGIORNO();

			String codiAutorita = chiamataRedazioneScheda.getANAGRAFICA().getPROCEDIMENTO().getDATIPROVVEDIMENTO().getCODIAUTORITA();
			String codiSede  = chiamataRedazioneScheda.getANAGRAFICA().getPROCEDIMENTO().getDATIPROVVEDIMENTO().getCODISEDEAUTORITAPRINDIST();
			
			datiprovvedimento = new  DATIPROVVEDIMENTO();
			datiprovvedimento.setCODIAUTORITA(codiAutorita);
			datiprovvedimento.setCODISEDEAUTORITAPRINDIST(codiSede);
			datiprovvedimento.setDATAPROVVEDIMENTO(new DATA());
			datiprovvedimento.getDATAPROVVEDIMENTO().setANNO(anno);
			datiprovvedimento.getDATAPROVVEDIMENTO().setMESE(mese);
			datiprovvedimento.getDATAPROVVEDIMENTO().setGIORNO(giorno);
			
			
		} catch (Exception e) {
			throw new Exception("Non è possibile inviare il foglio complementare perchè l'xml della scheda originale non è leggibile.");
		}
		
		
		// Impostazione dei dati dell'anagrafica principale
		DATIANAGRAFICAPRINCIPALE datiAnagraficaPrincipale = creaDatiAnagraficaPrincipale(soggetto, nscComuneNascita, nscStatoNascita, nscComuneMorte, nscCodiceStatoEsteroMorte);
		// Impostazione delle informazioni relative al procedeimento/atto definitivo
		PROCEDIMENTO procedimento = creaDatiProcedimento(soggetto, attoForm, procedimentoForm, comunicazioneForm, nscTipoProvvedimento, nscCodiceMisuraPrevenzione, nscUfficioCorrenteCodiAutorita, nscComuneUfficioCorrente,esitoNSC);
		
		// Impostazione dati del provvedimento 
		/** Commentato perchè valorizzati on quelli della scheda 
		DATIPROVVEDIMENTO datiprovvedimento = new  DATIPROVVEDIMENTO();
		datiprovvedimento.setCODIAUTORITA(nscUfficioCorrenteCodiAutorita);
		datiprovvedimento.setCODISEDEAUTORITAPRINDIST(nscComuneUfficioCorrente);
		datiprovvedimento.setDATAPROVVEDIMENTO(convertiData(attoForm.getDataEmissione()));
		*/
		// Impostazione dei dati relativi all'operazione da effettuare
		DATIOPERAZIONE operazione = new DATIOPERAZIONE();
		// TODO MARCO Gestire il caso del soggetto ENTITA.S da chiedere come.
		operazione.setENTITA(ENTITA.P);// Nel caso di primo invio è P
		if (idNSC!=null && !idNSC.trim().equals("")) {
			// Se l'id del soggetto casellario è valorizzato (caso di risposta con omonimia) 
			// lo l'operazione deve essere impostata a SKIP_INSERT_S e  valorizzato l'idNSC
			operazione.setOPERAZIONE(OPERAZIONE.SKIP_INSERT_S);
			//chiavi.setKANSC(BigInteger.valueOf(Long.parseLong(idNSC)));
		} else {
			operazione.setOPERAZIONE(OPERAZIONE.INSERT);
		}

		// Impostazione della request
		DATICHIAMATATRASFERIMENTO request = new DATICHIAMATATRASFERIMENTO();
		
		// NEW
		request.setCHIAVI(chiavi);
		
		if (seIstanzaErroreMateriale) {
			
			SoggettoForm soggettoNuovo = attoForm.getSoggetti().get(0);
			
			String nscStatoNascitaNuovo = null;
			if (soggettoNuovo.getStatoNascita().getId()!=null) {
				TAnStato statoNascita = commonDao.getStatoById(Integer.parseInt(soggettoNuovo.getStatoNascita().getId()));
				nscStatoNascitaNuovo = statoNascita.getDescCodiNSC(); 
			}
			String nscComuneNascitaNuovo = null;
			// Estraggo l'ufficio per leggere i valori del casellario
			if (soggettoNuovo.getComuneNascita().getId()!=null) {
				TAnComuni comuneNascita = commonDao.getComuneById(Integer.parseInt(soggettoNuovo.getComuneNascita().getId()));
				nscComuneNascitaNuovo = comuneNascita.getDescCodiNSC(); 
			}
			//soggettoNuovo.setCodiceFiscale(chiavi.setKA130SIPPI(new BigInteger("0")););
			DATIANAGRAFICAART130 datianagraficaart130 =  creaDatiAnagraficaPrincipale130(soggettoNuovo, nscComuneNascitaNuovo, nscStatoNascitaNuovo, nscComuneMorte, nscCodiceStatoEsteroMorte);
			chiavi.setKA130SIPPI(datianagraficaart130.getPROGANAGRAFICA());
			request.setDATIANAGRAFICAART130(datianagraficaart130);
		}
		request.setDATIANAGRAFICAPRINCIPALE(datiAnagraficaPrincipale);
		request.setDATIOPERAZIONE(operazione);
		request.setDATIPROVVEDIMENTO(datiprovvedimento);
		request.setDATIUTENTE(datiUtente);
		request.setPROCEDIMENTO(procedimento);
		
		// Invocazione del servizio del casellario
		DATIRISPOSTATRASFERIMENTO response = casellarioServiceClientFC.inviaProvvedimento(request);

		if (response.getArrayEsito().getESITO().get(0).getCODICE().equals("000")) {
			// Se l'esito è ok

			// Nel caso di invio con omonimi valorizzo il campo numSoggettoCasellario
			Long numSoggettoCasellario = null;
			if (idNSC!=null && !idNSC.equals("")) numSoggettoCasellario = Long.parseLong(idNSC);
			
			//Valorizzare xmlComunicazione e fileEstrattoCertif con blob proveniente da chiamata
			Blob xmlComunicazione = serializeInBlob(marshaller(request));
			Blob fileEstrattoCertif = serializeInBlob(marshaller(response));
			Character flagStatoInvio = '2'; //INVIATO
			// CAMBIO STATO dell'atto a REGISTRATO e salvataggio dell'xml + certificato
			attoDao.invioCasellario(
					idUffOwn, 
					comunicazioneForm.getIdAtto(), 
					new Date(), 
					comunicazioneForm.getIdComunicazione(),
					attoForm.getIdProcedimento(), 
					numSoggettoCasellario, 
					descUtente, 
					xmlComunicazione, 
					fileEstrattoCertif,
					null,
					flagStatoInvio);
		} else 	if (response.getArrayEsito().getESITO().get(0).getCODICE().equals("999")) {
			// Se il casellario ha risposto con OMONIMI
			Long numSoggettoCasellario = null;
			Blob xmlComunicazione = serializeInBlob(marshaller(request));
			Blob fileEstrattoCertif = serializeInBlob(marshaller(response));
			Character flagStatoInvio = '1'; //INVIATO
			attoDao.invioCasellario(
					idUffOwn, 
					comunicazioneForm.getIdAtto(), 
					new Date(), 
					comunicazioneForm.getIdComunicazione(),
					attoForm.getIdProcedimento(), 
					numSoggettoCasellario, 
					descUtente, 
					xmlComunicazione, 
					fileEstrattoCertif,
					null,
					flagStatoInvio);
			
		} else {
			// Se il casellario ha risposto con codice non gestito
			Long numSoggettoCasellario = null;
			Blob xmlComunicazione = serializeInBlob(marshaller(request));
			Blob fileEstrattoCertif = serializeInBlob(marshaller(response));
			Character flagStatoInvio = '0'; //Da inviare
			attoDao.invioCasellario(
					idUffOwn, 
					comunicazioneForm.getIdAtto(), 
					new Date(), 
					comunicazioneForm.getIdComunicazione(),
					attoForm.getIdProcedimento(), 
					numSoggettoCasellario, 
					descUtente, 
					xmlComunicazione, 
					fileEstrattoCertif,
					null,
					flagStatoInvio);
		}
 		return response;
	}

	
	private PROCEDIMENTO creaDatiProcedimento(SoggettoForm soggetto,
			AttoForm attoForm,
			ProcedimentoForm procedimentoForm, 
			ComunicazioneForm comunicazioneForm, 
			String nscTipoProvvedimento,
			String nscCodiceMisuraPrevenzione,
			String nscUfficioCorrenteCodiAutorita,
			String nscComuneUfficioCorrente,
			String nscEsito) {
		// Impostazione delle informazioni relative al procedeimento/atto definitivo
		PROCEDIMENTO procedimento = new PROCEDIMENTO();
		
		boolean seIstanzaErroreMateriale = false; 
		if ((Constants.TIPOPROC_CORREZIONE_ERRORI_CAM+"").equals(procedimentoForm.getTipoProcedimento().getId())) {
			seIstanzaErroreMateriale = true;
		}
		// Impostazione della info della misura di prevenzione
		MISURADIPREVENZIONE misuradiprevenzione = new MISURADIPREVENZIONE();
		if (!seIstanzaErroreMateriale) {
			DURATA durataMisuta = new DURATA();
			durataMisuta.setANNIDURATA(BigInteger.valueOf((soggetto.getMisura().getAnni()!=null?soggetto.getMisura().getAnni():0)));
			durataMisuta.setMESIDURATA(BigInteger.valueOf((soggetto.getMisura().getMesi()!=null?soggetto.getMisura().getMesi():0)));
			durataMisuta.setGIORNIDURATA(BigInteger.valueOf((soggetto.getMisura().getGiorni()!=null?soggetto.getMisura().getGiorni():0)));
			misuradiprevenzione.setDURATAMISURA(durataMisuta);
			misuradiprevenzione.setIMPORTO(soggetto.getMisura().getImporto()); 
			misuradiprevenzione.setPROGMISPREVENZIONE(BigInteger.valueOf(1l)); // ID Della misura di prevenzione 
			misuradiprevenzione.setCODITIPOMP(nscCodiceMisuraPrevenzione);
			
		}
		// Impostazione dei dati del provvedimento
		PROVVEDIMENTOESECUZIONEMP datiProvvedimento = new PROVVEDIMENTOESECUZIONEMP();
		
		
		datiProvvedimento.setANNOREGISTROGENERALE(BigInteger.valueOf(procedimentoForm.getAnno()));
		datiProvvedimento.setCODIAUTORITA(nscUfficioCorrenteCodiAutorita);
		BigInteger nscEsitoNum = null;
		try {
			nscEsitoNum = new BigInteger(nscEsito);
		} catch (Exception e) {
			System.err.println("Esito non determinato");
		}
		datiProvvedimento.setCODINATURA(nscEsitoNum); 
		datiProvvedimento.setCODISEDEAUTORITAPRINDIST(nscComuneUfficioCorrente); 
		datiProvvedimento.setCODITIPOPROVV(nscTipoProvvedimento);
		datiProvvedimento.setDATAPROVVEDIMENTO(convertiData(attoForm.getDataEmissione()));
		datiProvvedimento.setMISURADIPREVENZIONE(misuradiprevenzione); 
		datiProvvedimento.setMOTIVAZIONEART130(null); //TODO FOGLIO COMPLEMENTARE Gestiore su errore materiale
		datiProvvedimento.setNUMEROREGISTROGENERALE("" + procedimentoForm.getNumero());
	
		procedimento.setPROVVEDIMENTOESECUZIONEMP(datiProvvedimento);
		return procedimento;
	}
	
	
	
	private DATIANAGRAFICAPRINCIPALE creaDatiAnagraficaPrincipale(SoggettoForm soggetto,String nscComuneNascita,String nscStatoNascita,String nscComuneMorte,String nscCodiceStatoEsteroMorte) {
		// Impostazione delle informazioni anagrafiche del soggetto
		DATIANAGRAFICAPRINCIPALE datiAnagrafici = new DATIANAGRAFICAPRINCIPALE();
		datiAnagrafici.setPROGANAGRAFICA(BigInteger.valueOf(soggetto.getIdSoggetto()));
		datiAnagrafici.setPERSCOGNOME(soggetto.getCognome());
		datiAnagrafici.setPERSNOME(soggetto.getNome());
		datiAnagrafici.setCODILUOGONASCITA(nscComuneNascita); 
		datiAnagrafici.setCODISTATOESTERONAS(nscStatoNascita ); 
		datiAnagrafici.setDESCCOMUNEESTERO(soggetto.getDescrizioneComuneEsteroNascita() ); 
		datiAnagrafici.setDATANASCITA(convertiData(soggetto.getDataNascita()));
		datiAnagrafici.setCODIFISCALE(soggetto.getCodiceFiscale());
		datiAnagrafici.setCODIIMPRONTADIGITALE(null); // null da specifica
		datiAnagrafici.setPERSPATERNITA(soggetto.getNomePaternita());
		datiAnagrafici.setSESSO(convertiSesso(soggetto.getSesso()));
		return datiAnagrafici;
	}
	
	private DATIANAGRAFICAART130 creaDatiAnagraficaPrincipale130(SoggettoForm soggetto,String nscComuneNascita,String nscStatoNascita,String nscComuneMorte,String nscCodiceStatoEsteroMorte) {
		// Impostazione delle informazioni anagrafiche del soggetto
		DATIANAGRAFICAART130 datiAnagrafici = new DATIANAGRAFICAART130();
		datiAnagrafici.setPROGANAGRAFICA(BigInteger.valueOf(soggetto.getIdSoggetto()));
		datiAnagrafici.setPERSCOGNOME(soggetto.getCognome());
		datiAnagrafici.setPERSNOME(soggetto.getNome());
		datiAnagrafici.setCODILUOGONASCITA(nscComuneNascita); 
		datiAnagrafici.setCODISTATOESTERONAS(nscStatoNascita ); 
		datiAnagrafici.setDESCCOMUNEESTERO(soggetto.getDescrizioneComuneEsteroNascita() ); 
		datiAnagrafici.setDATANASCITA(convertiData(soggetto.getDataNascita()));
		datiAnagrafici.setCODIFISCALE(soggetto.getCodiceFiscale());
		datiAnagrafici.setCODIIMPRONTADIGITALE(null); // null da specifica
		datiAnagrafici.setPERSPATERNITA(soggetto.getNomePaternita());
		datiAnagrafici.setSESSO(convertiSesso(soggetto.getSesso()));
		return datiAnagrafici;
	}
	
	/***
	 * Crea l'oggetto con i dati anagrafici da passare al servizio
	 * @param soggetto
	 * @param nscComuneNascita
	 * @param nscStatoNascita
	 * @param nscComuneMorte
	 * @param nscCodiceStatoEsteroMorte
	 * @return
	
	private DATIANAGRAFICI creaDatiAnagrafici(SoggettoForm soggetto,String nscComuneNascita,String nscStatoNascita,String nscComuneMorte,String nscCodiceStatoEsteroMorte) {
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
		datiAnagrafici.setCODIIMPRONTADIGITALE(null); // null da specifica
		datiAnagrafici.setPERSPATERNITA(soggetto.getNomePaternita());
		datiAnagrafici.setPERSCOGNOMEMADRE(soggetto.getCognomeMaternita());
		datiAnagrafici.setPERSNOMEMADRE(soggetto.getNomeMaternita());
		// datiAnagrafici.setDESCANNOTAZIONE( ); //Francesco dice di lasciare a null
		datiAnagrafici.setFLAGDECEDUTOSN((soggetto.getDataMorte() != null ? FLAGDECESSO.S : FLAGDECESSO.N));
		datiAnagrafici.setCODILUOGOMORTE(nscComuneMorte ); 
		datiAnagrafici.setCODISTATOESTEROMORTE(nscCodiceStatoEsteroMorte ); 
		datiAnagrafici.setNUMEATTOMORTE(null); // null da specifica
		datiAnagrafici.setDATAMORTE(convertiData(soggetto.getDataMorte()));
		datiAnagrafici.setSESSO(convertiSesso(soggetto.getSesso()));
		return datiAnagrafici;
	} */
	
	/***
	 * Cancellazione del Provvedimento inviato al casellario
	 * 
	 * @param idComunicazione
	 */
	public DATIRISPOSTATRASFERIMENTO cancellaProvvedimento(
			final String descUtente, 
			final TUtente tUtente, 
			final long idUffOwn, 
			AttoForm attoForm,
			ComunicazioneForm comunicazioneForm, 
			ProcedimentoForm procedimentoForm) throws Exception {
		boolean seIstanzaErroreMateriale = false; 
		if ((Constants.TIPOPROC_CORREZIONE_ERRORI_CAM+"").equals(procedimentoForm.getTipoProcedimento().getId())) {
			seIstanzaErroreMateriale = true;
		}
		SoggettoForm soggetto;
		if (seIstanzaErroreMateriale) {
			soggetto = attoForm.getSoggetti().get(1);
		} else {
			soggetto = attoForm.getSoggetti().get(0);
		}
		
		// Estraggo il tipo atto per leggere i valori del cassellario
		String idCatAtto = attoForm.getCategoriaAtto().getId();
		TTpCatAtto catAtto = commonDao.getCatAttoById(Integer.parseInt(idCatAtto)); 
		String nscTipoProvvedimento = "" + catAtto.getDescCodiNSC(); 
		
		// Estraggo l'ufficio per leggere i valori del casellario
		TAnUfficio ufficioCorrente = commonDao.getUfficioById((int)idUffOwn); 
		
		// Estraggo il tipo ufficio corrente
		TTpTipoUfficio tipoUfficio = commonDao.getTipoUfficioById(ufficioCorrente.getTTpTipoUfficio().getCodiKIdTipoUff());
		String nscUfficioCorrenteCodiAutorita = tipoUfficio.getDescCodiAutoritaNSC(); 
		String nscUfficioCorrenteCodiUfficio = tipoUfficio.getDescCodiUfficioNSC(); 

		// Estraggo l'ufficio per leggere i valori del casellario
		TAnComuni comuneUfficioCorrente = commonDao.getComuneById(ufficioCorrente.getTAnComuni().getCodiKIdComune());
		String nscComuneUfficioCorrente = comuneUfficioCorrente.getDescCodiNSC(); 

		String nscComuneNascita = null;
		// Estraggo l'ufficio per leggere i valori del casellario
		if (soggetto.getComuneNascita().getId()!=null) {
			TAnComuni comuneNascita = commonDao.getComuneById(Integer.parseInt(soggetto.getComuneNascita().getId()));
			nscComuneNascita = comuneNascita.getDescCodiNSC(); 
		}
		
		String nscComuneMorte = null;
		// Estraggo l'ufficio per leggere i valori del casellario
		if (soggetto.getComuneMorte().getId()!=null) {
			TAnComuni comuneMorte = commonDao.getComuneById(Integer.parseInt(soggetto.getComuneMorte().getId()));
			nscComuneMorte = comuneMorte.getDescCodiNSC(); 
		}
		
		String nscStatoNascita = null;
		if (soggetto.getStatoNascita().getId()!=null) {
			TAnStato statoNascita = commonDao.getStatoById(Integer.parseInt(soggetto.getStatoNascita().getId()));
			nscStatoNascita = statoNascita.getDescCodiNSC(); 
		}
		
		String nscCodiceStatoEsteroMorte = null;
		if (soggetto.getStatoMorte().getId()!=null) {
			TAnStato statoMorte = commonDao.getStatoById(Integer.parseInt(soggetto.getStatoMorte().getId()));
			nscCodiceStatoEsteroMorte = statoMorte.getDescCodiNSC();
		}
		String nscCodiceMisuraPrevenzione = null;
		if (!seIstanzaErroreMateriale) {
			TTpTipoMisura tipoMisura = commonDao.getTipoMisuraById(Integer.parseInt(soggetto.getMisura().getTipoMisura().getId()));
			nscCodiceMisuraPrevenzione = tipoMisura.getDescCodiNSC();
		}
		
		String esitoNSC = null;
		if (!seIstanzaErroreMateriale) {
			esitoNSC = attoDao.getCodiceEsitoNSCPerFoglioComplementare(procedimentoForm.getIdProcedimento(), (int)idUffOwn, comunicazioneForm.getIdAttoEsecutivo(), comunicazioneForm.getIdMisuraPersonale());
		} else {
			// TODO FC Errore Materiale chiedere a francesco come valorizzare
			esitoNSC = attoDao.getCodiceEsitoNSCPerFoglioComplementare(procedimentoForm.getIdProcedimento(), (int)idUffOwn, comunicazioneForm.getIdAttoEsecutivo(), 0l);
		}
		
		// Impostazione dei dati dell'ufficio
		DATIUFFICIO datiUfficio = creaDatiUfficio(nscComuneUfficioCorrente,nscUfficioCorrenteCodiUfficio);
		// Impostazione dei dati dell'utente
		DATIUTENTE datiUtente = creaDatiUtente(tUtente,datiUfficio);
		// Impostazione dei dati delle chiavi
		CHIAVI chiavi = creaChiavi( soggetto, comunicazioneForm, procedimentoForm, seIstanzaErroreMateriale,  tipoUfficio);
		
		// Lettura vecchi provvedimento
		// Implementazione della ricerca del procedimento che ha data la devinitività.
		// Viene letto dalla tabelle T_COMUNICAZIONE per della scheda padre.
		List<TRelProcProc> listaProcProc =  procedimentoDao.getTRelProcProcByIdProc1Padre(procedimentoForm.getIdProcedimento());
		TComunicazione com = null;
		if (listaProcProc.size()>0) {
			// Id del procedimento padre
			long idProc2 = listaProcProc.get(0).getId().getCodiKIdProc2();
			long idSoggettoQuery = soggetto.getIdSoggetto();
			com = procedimentoDao.getComunicazioneByIdProcESogIntMis(idProc2,idSoggettoQuery);
			if (com==null) {
				// se la scheda non è stata inviata dallo stesso ufficio
				int idTipoUff = tipoUfficio.getCodiKIdTipoUff();
				com = procedimentoDao.getComunicazioneByIdProc1ETipoUff(idSoggettoQuery,idProc2,idTipoUff);
			}
		}
		if (com==null) {
			throw new Exception("Non è possibile inviare il foglio complementare perchè per il soggetto non è presente una scheda valida.");
		} 
		// se la scheda è stata inviata dallo stesso ufficio
		chiavi.setKPPSIPPI(BigInteger.valueOf(com.getCodiFkIdAttoEs()));
		DATIPROVVEDIMENTO datiprovvedimento = null;
		try {
			it.giustizia.smp.webservice.client.casellario.DATICHIAMATATRASFERIMENTO chiamataRedazioneScheda =
			JAXBUtil.unmarshall(it.giustizia.smp.webservice.client.casellario.DATICHIAMATATRASFERIMENTO.class, com.getXmlComunicazione().getBinaryStream());
		
			
			String anno = chiamataRedazioneScheda.getANAGRAFICA().getPROCEDIMENTO().getDATIPROVVEDIMENTO().getDATAPROVVEDIMENTO().getANNO();
			String mese = chiamataRedazioneScheda.getANAGRAFICA().getPROCEDIMENTO().getDATIPROVVEDIMENTO().getDATAPROVVEDIMENTO().getMESE();
			String giorno = chiamataRedazioneScheda.getANAGRAFICA().getPROCEDIMENTO().getDATIPROVVEDIMENTO().getDATAPROVVEDIMENTO().getGIORNO();

			String codiAutorita = chiamataRedazioneScheda.getANAGRAFICA().getPROCEDIMENTO().getDATIPROVVEDIMENTO().getCODIAUTORITA();
			String codiSede  = chiamataRedazioneScheda.getANAGRAFICA().getPROCEDIMENTO().getDATIPROVVEDIMENTO().getCODISEDEAUTORITAPRINDIST();
			
			datiprovvedimento = new  DATIPROVVEDIMENTO();
			datiprovvedimento.setCODIAUTORITA(codiAutorita);
			datiprovvedimento.setCODISEDEAUTORITAPRINDIST(codiSede);
			datiprovvedimento.setDATAPROVVEDIMENTO(new DATA());
			datiprovvedimento.getDATAPROVVEDIMENTO().setANNO(anno);
			datiprovvedimento.getDATAPROVVEDIMENTO().setMESE(mese);
			datiprovvedimento.getDATAPROVVEDIMENTO().setGIORNO(giorno);
			
			
		} catch (Exception e) {
			throw new Exception("Non è possibile inviare il foglio complementare perchè l'xml della scheda originale non è leggibile.");
		}
		
		
		// Impostazione dei dati dell'anagrafica principale
		DATIANAGRAFICAPRINCIPALE datiAnagraficaPrincipale = creaDatiAnagraficaPrincipale(soggetto, nscComuneNascita, nscStatoNascita, nscComuneMorte, nscCodiceStatoEsteroMorte);
		// Impostazione delle informazioni relative al procedeimento/atto definitivo
		PROCEDIMENTO procedimento = creaDatiProcedimento(soggetto, attoForm, procedimentoForm, comunicazioneForm, nscTipoProvvedimento, nscCodiceMisuraPrevenzione, nscUfficioCorrenteCodiAutorita, nscComuneUfficioCorrente,esitoNSC);
		
		
		// Impostazione dei dati relativi all'operazione da effettuare
		DATIOPERAZIONE operazione = new DATIOPERAZIONE();
		operazione.setENTITA(ENTITA.P);// Nel caso di primo invio è P
		operazione.setOPERAZIONE(OPERAZIONE.DELETE);
		
		
		// Impostazione della request
		DATICHIAMATATRASFERIMENTO request = new DATICHIAMATATRASFERIMENTO();
		
		// NEW
		request.setCHIAVI(chiavi);
		// request.setDATIANAGRAFICAART130(value); //TODO FOGLIO COMPLEMENTARE Da gestiore su correzzione materiale
		request.setDATIANAGRAFICAPRINCIPALE(datiAnagraficaPrincipale);
		request.setDATIOPERAZIONE(operazione);
		request.setDATIPROVVEDIMENTO(datiprovvedimento);
		request.setDATIUTENTE(datiUtente);
		request.setPROCEDIMENTO(procedimento);
		
		// Invocazione del servizio del casellario
		return casellarioServiceClientFC.inviaProvvedimento(request);

	}

	private CHIAVI creaChiavi(
			SoggettoForm soggetto,
			ComunicazioneForm comunicazioneForm,
			ProcedimentoForm procedimentoForm,
			boolean seIstanzaErroreMateriale,
			TTpTipoUfficio tipoUfficio
			) {
		CHIAVI chiavi = new CHIAVI();
		
		chiavi.setKASIPPI(BigInteger.valueOf(soggetto.getIdSoggetto()));
		chiavi.setKPESIPPI(BigInteger.valueOf(comunicazioneForm.getIdAttoEsecutivo()));
		
	
		return chiavi;
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

	private DATIUFFICIO creaDatiUfficio(String nscComuneUfficioCorrente, String nscUfficioCorrenteCodiUfficio) {
		DATIUFFICIO datiUfficio = new DATIUFFICIO();
		datiUfficio.setCODICESEDEUFFICIO(nscComuneUfficioCorrente);
		datiUfficio.setCODICETIPOUFFICIO(nscUfficioCorrenteCodiUfficio);

		return datiUfficio;
	}

	/**
	 * Crea l'oggetto di invocazione per ogni operation
	 */
	private Blob serializeInBlob(byte[] byteArray) {
		return Hibernate.createBlob(byteArray);
	}

	/**
	 * Esegue il marshaller per la response e la request del servizio
	 */
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

	public DATIRISPOSTATRASFERIMENTO unmarhallerRispostaTrasferimento(Blob blobFile) throws JAXBException, IOException {
		DataHandler dh = serializzaFile(blobFile);
		DATIRISPOSTATRASFERIMENTO risposta = null;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		dh.writeTo(bout);
		JAXBContext jc = JAXBContext.newInstance(DATIRISPOSTATRASFERIMENTO.class);
		ByteArrayInputStream bis = new ByteArrayInputStream(bout.toByteArray());
		Unmarshaller u = jc.createUnmarshaller();
		risposta = (DATIRISPOSTATRASFERIMENTO) u.unmarshal(bis);
		return risposta;
	}

	public DATICHIAMATATRASFERIMENTO unmarhallerChiamataTrasferimento(Blob blobFile) throws JAXBException, IOException {
		DataHandler dh = serializzaFile(blobFile);
		DATICHIAMATATRASFERIMENTO chiamata = null;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		dh.writeTo(bout);
		JAXBContext jc = JAXBContext.newInstance(DATICHIAMATATRASFERIMENTO.class);
		ByteArrayInputStream bis = new ByteArrayInputStream(bout.toByteArray());
		Unmarshaller u = jc.createUnmarshaller();
		chiamata = (DATICHIAMATATRASFERIMENTO) u.unmarshal(bis);
		return chiamata;
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
			return SESSO.F;
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

	public List<OmonimoForm> getListaOmonimi(ComunicazioneForm comunicazioneForm) throws JAXBException, IOException {
		ArrayList<OmonimoForm> listOmonimoForms = new ArrayList<OmonimoForm>();
		TComunicazione tComunicazione = attoDao.getTComunicazioneById(comunicazioneForm.getIdComunicazione());
		DATIRISPOSTATRASFERIMENTO risposta = unmarhallerRispostaTrasferimento(tComunicazione.getFileEstrattoCertif());

		for (OMONIMO omonimo : risposta.getArrayOmonimi().getOMONIMO()) {
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

			String deceduto = "N/D";
			listOmonimoForms.add(new OmonimoForm(omonimo.getDATIANAGRAFICI().getPROGANAGRAFICA().toString(), soggetto, omonimo.getDATIANAGRAFICI()
					.getCODIFISCALE(), deceduto));
		}
		return listOmonimoForms;
	}

}
