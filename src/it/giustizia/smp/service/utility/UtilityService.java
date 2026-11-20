package it.giustizia.smp.service.utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import it.giustizia.smp.dto.KeyMessageDto;
import it.giustizia.smp.form.AllegatoForm;
import it.giustizia.smp.form.AmministratoreAlboForm;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.BeneForm;
import it.giustizia.smp.form.BeneSoggettoForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.MagistratoForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.model.TAnComuni;
import it.giustizia.smp.model.TAnStato;
import it.giustizia.smp.model.TAtto;
import it.giustizia.smp.model.TComunicazione;
import it.giustizia.smp.model.TRelAttoMisPers;
import it.giustizia.smp.service.MisuraService;
import it.giustizia.smp.service.webservices.CasellarioService;
import it.giustizia.smp.service.webservices.CasellarioServiceFC;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.utility.goback.Breadcrumbs;
import it.giustizia.smp.webservice.client.casellario.DATIANAGRAFICI;
import it.giustizia.smp.webservice.client.casellario.DATICHIAMATATRASFERIMENTO;
import it.giustizia.smp.webservice.client.casellario.DATIRISPOSTATRASFERIMENTO;
import it.giustizia.smp.webservice.client.casellario.ESITO;

@Service
public class UtilityService extends AttoUtilService {

	@Autowired
	private CasellarioService casellarioService;
	@Autowired
	private MisuraService misuraService;
	@Autowired
	private CasellarioServiceFC casellarioServiceFC;
	
	/***
	 * Nel caso in cui la scheda del casellario è inviato o annullata valorizzo i dati del soggetto con quelli presenti nell'xml di comunicazione 
	 * @param attoForm
	 */
	public void sovrascritturaDatiSoggettoDaXML(AttoForm attoForm) {
		try {
			TComunicazione tComunicazione = attoService.getTComunicazioneById(attoForm.getComunicazioneForm().getIdComunicazione());
			DATICHIAMATATRASFERIMENTO chiamataXML =  casellarioService.unmarhallerChiamataTrasferimento(tComunicazione.getXmlComunicazione());
			DATIANAGRAFICI anagrafica =  chiamataXML.getANAGRAFICA().getDATIANAGRAFICI();
			SoggettoForm soggetto = attoForm.getSoggetti().get(0);
			soggetto.setCodiceFiscale(anagrafica.getCODIFISCALE());
			soggetto.setCognome(anagrafica.getPERSCOGNOME());
			soggetto.setNome(anagrafica.getPERSNOME());
			//Data Nascita
			try {
				soggetto.setDataNascita( casellarioService.convertiData(anagrafica.getDATANASCITA()));
			} catch (ParseException e) {
				logger.error("Impossibile convertire la data di nascita ricevuta dal servizio in una data valida",e);
			}
			//Data Morte
			try {
				soggetto.setDataMorte(casellarioService.convertiData(anagrafica.getDATAMORTE()));
			} catch (ParseException e) {
				logger.error("Impossibile convertire la data di morte ricevuta dal servizio in una data valida",e);
			}
			//Stato Nascita
			if (soggetto.getStatoNascita()!=null) {
				TAnStato statoEstero =  commonService.getStatoByIdCasellario(anagrafica.getCODISTATOESTERONAS() );
				String descStato = soggetto.getStatoNascita().getDescrizione();
				if (statoEstero!=null)
					descStato = statoEstero.getDescStato();
				soggetto.getStatoNascita().setDescrizione(descStato);
			}
			//Comune Nascita
			if (anagrafica.getCODILUOGONASCITA()!=null && !anagrafica.getCODILUOGONASCITA().equals("")) {
				TAnComuni comuneCasellario =  commonService.getComuneByIdCasellario(anagrafica.getCODILUOGONASCITA() );
				String descCasellario = soggetto.getComuneNascita().getDescrizione();
				if (comuneCasellario!=null)
					descCasellario = comuneCasellario.getDescComune();
				soggetto.getComuneNascita().setDescrizione(descCasellario);
			} else
				soggetto.getComuneNascita().setDescrizione(anagrafica.getDESCCOMUNEESTERO());
			if (anagrafica.getSESSO()!=null)
				switch (anagrafica.getSESSO()) {
					case M: soggetto.setDescrSesso("MASCHIO");
					break;
					default: soggetto.setDescrSesso("FEMMINA");
					break;
				}
			soggetto.setNomePaternita(anagrafica.getPERSPATERNITA());
			soggetto.setNomeMaternita(anagrafica.getPERSNOMEMADRE());
			soggetto.setCognomeMaternita(anagrafica.getPERSCOGNOMEMADRE());
			
		} catch (JAXBException e0){
			logger.error("Impossibile eseguire unmarhallerRispostaTrasferimento su sovrascritturaDatiSoggettoDaXML",e0);
		} catch (IOException e1) {
			logger.error("Impossibile eseguire unmarhallerRispostaTrasferimento su sovrascritturaDatiSoggettoDaXML",e1);
		}
	}
	
	public void infoCauzeArt67Proposta(long idCategoriaAtto, SoggettoForm soggettoForm, HttpServletRequest request) {
		// ***** RECUPERO INFORMAZIONI CAUZIONE E ART.67 PROPOSTA *****
		if ((idCategoriaAtto == 4L) ||  (idCategoriaAtto == 22L) ||  (idCategoriaAtto == 355L) ||  (idCategoriaAtto == 359L) ||  (idCategoriaAtto == 373L)) {
			int idUffOwn=soggettoForm.getIdUfficio();
			long vidUffOwn = Long.valueOf(idUffOwn).longValue();
			long vidProc = soggettoForm.getIdProcedimento();
			
			long idSoggInt = soggettoForm.getIdSoggetto();
			long idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
			// *** CAUZIONE ***
			String carMisuraCauz = "C";
			List<TRelAttoMisPers> listaCauzioneDB = new ArrayList<TRelAttoMisPers>();
			listaCauzioneDB = misuraService.listaIdMisureSpecificheProposta(vidProc, vidUffOwn, idSoggInt, idAtto, carMisuraCauz);
			if (listaCauzioneDB.size() > 0)
				soggettoForm.getMisura().setCauzioneProp("S");
			else
				soggettoForm.getMisura().setCauzioneProp("N");
			// *** ART.67 ***
			String carMisuraArt = "A";
			List<TRelAttoMisPers> listaArtDB = new ArrayList<TRelAttoMisPers>();
			listaArtDB = misuraService.listaIdMisureSpecificheProposta(vidProc, vidUffOwn, idSoggInt, idAtto, carMisuraArt);
			if (listaArtDB.size() > 0)
				soggettoForm.getMisura().setArt67Prop("S");
			else
				soggettoForm.getMisura().setArt67Prop("N");
		}
		// ***** RECUPERO INFORMAZIONI CAUZIONE E ART.67 PROPOSTA *****
	}
	
	public String caricaOrdinanza14CodiceAntimafia(AttoForm attoForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {

		int idUffOwn = recuperaIdUfficioUtente();
		String codUtente = recuperaIdCodiceUtente();
		ProcedimentoForm procedimento = recuperaProcedimento();

		long idAtto = 0l;
		if (attoForm != null && attoForm.getCodAtto() > 0)
			idAtto = attoForm.getCodAtto();
		else
			idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();

		attoForm = caricaAttoInSessione(idUffOwn, idAtto, codUtente, procedimento.getIdProcedimento(), request, attoForm);

		ComboForm combo = new ComboForm();
		SoggettoForm soggetto = attoService.getSogAntimafia(idUffOwn, procedimento.getIdProcedimento(), idAtto);
		
		combo.setDescrizione(soggetto.getDescrizioneSoggettoConVersione());
		combo.setId("" + soggetto.getIdSoggetto());
		attoForm.setSoggettiAntimafia(combo);	
		
		AttoForm tmpAtto = new AttoForm();
		tmpAtto = attoService.getTRelProcSogintAttoById(idAtto, idUffOwn, procedimento.getIdProcedimento());
		
		if(tmpAtto != null){
			attoForm.setDataRipresaMisura(tmpAtto.getDataRipresaMisura());
			attoForm.setDataCessazioneMisura(tmpAtto.getDataCessazioneMisura());
		}
		
		
		modelMap.addAttribute("attoForm", attoForm);
		
		addBreadcrumbs();

		
		int ruoloMagistrato=5;
	    MagistratoForm magistratoForm = attoService.getMagistratoPerAtto(idUffOwn, attoForm.getCodAtto(),ruoloMagistrato);
	    ComboForm magistrato = new ComboForm();
	    if(magistratoForm != null && magistratoForm.getNome()!= null){
	      magistrato.setDescrizione(magistratoForm.getNome() + " "+ magistratoForm.getCognome());
	      magistrato.setId(magistratoForm.getId()+"");
	    }
	    attoForm.setMagistrato(magistrato);	
	    
		if (attoForm != null && attoForm.getStatoAtto() != null
				&& Long.valueOf(attoForm.getStatoAtto().getId()).equals(Constants.STATOATTO_REGISTRATO))
			return "/procedimento/atto/copertinaOrdinanzaArt14Antimafia";
		
		List<ComboForm> magistrati = commonService.getMagistratiByUff(idUffOwn);
		
		modelMap.addAttribute("magistrati", magistrati);
		
		return "/procedimento/atto/confermaInviaOrdinanzaArt14Antimafia";
	}
	
	public File caricaDocumento(AttoForm atto, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws IOException {

		long idUfficio = recuperaIdUfficioUtente();
		long idAtto = atto.getCodAtto();
		AllegatoForm allegato = new AllegatoForm();
        // System.out.println("idAtto**********"+idAtto) ; 
        Breadcrumbs lastBreadcrumbs = getLastBreadCrumbs(getSession());
        //Controllo se si stanno visualizzando i documenti della sezione -Atti del tribunale-
        if(lastBreadcrumbs.getAction().contains("procedimento/atti/elencoAttiTribunale.htm") && atto.getIdUfficio() != 0)
        	idUfficio = atto.getIdUfficio();
    	
		TAtto tAtto = attoService.getTAttoById(idAtto, idUfficio);
		
		//P.Q. 05/07/2022 Ticket 20220627018 Inizio
		// Controllo prima se e' valorizzato NUME_COPIA_PDOC
		if (tAtto.getNumeCopiaPdoc() != null) {
			allegato = attoService.extractBlobFromDocumentazione(tAtto.getNumeCopiaPdoc(), tAtto.getTAnUfficio().getCodiKIdUfficio());
			// Se allegato = null (problema con PDOC , ad es. con le autorizzazioni) provo con NUME_DOCUMENTO_PDOC
			if (allegato.getDocumento() == null) {
				if (tAtto.getNumeDocumentoPdoc() != null) {
					allegato = attoService.extractBlobFromDocumentazione(tAtto.getNumeDocumentoPdoc(), tAtto.getTAnUfficio().getCodiKIdUfficio());
				}							
			}
		} else if (tAtto.getNumeDocumentoPdoc() != null) {
			// lo carico dalla PIATT DOC
			allegato = attoService.extractBlobFromDocumentazione(tAtto.getNumeDocumentoPdoc(), tAtto.getTAnUfficio().getCodiKIdUfficio());
		//P.Q. 05/07/2022 Ticket 20220627018 Fine			
		} else {
			// lo carico da T_ATTO
			allegato.setDocumento(tAtto.getFileDocumento());
		}
		
		//P.Q. 05/07/2022 Ticket 20220627018 Inizio		
		if(allegato.getDocumento() == null) {
			throw new IOException("Errore durante la lettura dal documentale (Allegato non presente / Ufficio non autorizzato)");
		}
		//P.Q. 05/07/2022 Ticket 20220627018 Fine
		
		response.setContentType(tAtto.getDescTipoDocumento());
		//System.out.println("tAtto.getDescTipoDocumento()"+tAtto.getDescTipoDocumento()) ; 
		response.setHeader("Content-Disposition", "attachment;filename=" + tAtto.getDescNomeDocumento());
		OutputStream out = response.getOutputStream();

		try {

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];

			InputStream in = allegato.getDocumento().getBinaryStream();

			int n = 0;
			while ((n = in.read(buf)) >= 0)
				baos.write(buf, 0, n);

			in.close();
			byte[] bytes = baos.toByteArray();

			out.write(bytes);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		out.flush();
		return null;

	}
	
	/***
	 * Implementazione del download della scheda casellario e/o modulo elettorale
	 * @param tipoFile, "C" per scehda  casellario, "S" per scheda  elettorale
	 * @param atto
	 * @param result
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public File downlaodPDF(String tipoFile,AttoForm atto, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		atto = recuperaAtto();
		TComunicazione tComunicazione = attoService.getTComunicazioneById(atto.getComunicazioneForm().getIdComunicazione());
		OutputStream out = response.getOutputStream();
		if (tipoFile.equals("S")) { // Se scheda elettorale
			// Scrittura del file sulla response
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			InputStream in = null;
			if(tComunicazione.getFileModuloElettorale()==null )
				throw new Exception("Errore: Il file del modulo elettorale non è stato trasmesso dal casellario.");
			in = tComunicazione.getFileModuloElettorale().getBinaryStream();	
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment;filename=SchedaElettorale.pdf");
			int n = 0;
			while ((n = in.read(buf)) >= 0)
				baos.write(buf, 0, n);
			in.close();
			byte[] bytes = baos.toByteArray();
			out.write(bytes);
		} else if (tipoFile.equals("FC")) { // Se foglio complementare
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment;filename=Casellario.pdf");
			out.write(casellarioServiceFC.unmarhallerRispostaTrasferimento(tComunicazione.getFileEstrattoCertif()).getESTRATTOPROVVEDIMENTO() );
				
		} else {
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment;filename=Casellario.pdf");
			
			out.write(casellarioService.unmarhallerRispostaTrasferimento(tComunicazione.getFileEstrattoCertif()).getESTRATTOPROVVEDIMENTO() );
		}
		out.flush();
		return null;

	}
	
	public void validateNumeroAttoDate(AttoForm attoForm, BindingResult result, HttpServletRequest request) {

		Date dataDeposito = attoForm.getDataDeposito();
		Date dataEmissione = attoForm.getDataEmissione();

		if ((dataDeposito != null && dataEmissione != null) && dataEmissione.after(dataDeposito)) {
			super.setMessage(Constants.MESSAGE,"error.dataEmiss.dataDep", null, false);
			result.reject(Constants.MESSAGE);
		}

		//controllo sulla data apertura fascicolo
		ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute("procedimento");
		Date dataApeFasc = Utility.removeTime(procedimento.getDataApertura());
		if ((dataEmissione != null) && dataEmissione.before(dataApeFasc))
			result.rejectValue("dataEmissione", "error.dataApeFasc");
		if ((dataDeposito != null) && dataDeposito.before(dataApeFasc))
			result.rejectValue("dataDeposito", "error.dataApeFasc");
	}
	
	/**
	 * initFormRicercaAmministratore
	 * 
	 * metodo private per caricamento combo form di ricerca albo amministratori
	 * 
	 * @param amministratoreForm
	 * @param modelMap
	 */
	public void initFormRicercaAmministratore(AmministratoreAlboForm amministratoreForm, ModelMap modelMap) {

		List<ComboForm> tipoOrdineList = commonService.getTipoOrdineAmministratori();
		modelMap.addAttribute("tipoOrdineList", tipoOrdineList);

		if (amministratoreForm.getAlboProfessionale() != null && amministratoreForm.getAlboProfessionale().getId() != null)
			modelMap.addAttribute("sediOrdini", commonService.getSediOrdineAmministratori(amministratoreForm.getAlboProfessionale().getId()));
		else
			modelMap.addAttribute("sediOrdini", null);

		modelMap.addAttribute("provinceDomicilio", commonService.getAllProvincia());

		if (amministratoreForm.getProvinciaDomProf() != null && amministratoreForm.getProvinciaDomProf().getId() != null
				&& !amministratoreForm.getProvinciaDomProf().getId().equals(Constants.NO_ITEM_SELECTED))
			modelMap.addAttribute("comuniDomicilio", commonService.getAllComuneByProvincia(amministratoreForm.getProvinciaDomProf().getId()));
		else
			modelMap.addAttribute("comuniDomicilio", null);

	}
	
	public void gestioneMagistratiAssociati(int idUffOwn, long idAtto, long idProcedimento, String codiceUtente){
		//GESTIONE GIUDICE DELEGATO (se è presente il giudice delegato associato al procedimento lo inserisco nella REL_MAG_ATTO)
		List<MagistratoForm> magistratiAssociatiAtto = attoService.listaMagistratiPerAtto(idUffOwn, idAtto, Integer.parseInt(Constants.ID_TIPO_RUOLO_GIU_DEL));
		List<MagistratoForm> magistratiAssociatiProc = procedimentoService.listaMagistratiPerProcedimento(idUffOwn, idProcedimento, Integer.parseInt(Constants.ID_TIPO_RUOLO_GIU_DEL), true);
		if((magistratiAssociatiAtto == null || (magistratiAssociatiAtto != null && magistratiAssociatiAtto.size() == 0))
			&& (magistratiAssociatiProc != null && magistratiAssociatiProc.size() > 0)){
			MagistratoForm magistratoForm = magistratiAssociatiProc.get(0);
			attoService.inserisciMagistratoPerAtto(idUffOwn, idAtto, magistratoForm.getId(), magistratoForm.getRuolo().getId(), magistratoForm.getRelatore(), codiceUtente);
		}
	}
	
	public KeyMessageDto controllaInsertDefinitivitaIGrado(long idProcedimento, AttoForm attoForm, BeneSoggettoForm beneSoggettoForm,
			List<BeneForm> misurePatrimoniali, List<SoggettoForm> misurePersonali, String codUtente, Integer idUfficioUtente) {

		KeyMessageDto key = new KeyMessageDto();

		if (misurePatrimoniali != null && !misurePatrimoniali.isEmpty())
			for (BeneForm bene : misurePatrimoniali)
				if (bene.getListaIdCheckboxString() != null)
					for (String idSoggettiTitolareETitolo : bene.getListaIdCheckboxString()) {
						long idSoggetto = 0;
						long idInteresse = 0;
						StringTokenizer st = new StringTokenizer(idSoggettiTitolareETitolo, ";");
						int numToken = st.countTokens();
						while (st.hasMoreTokens()) {
							idSoggetto = Long.valueOf(st.nextToken()).longValue();
							idInteresse = Long.valueOf(st.nextToken()).longValue();
							Long.valueOf(st.nextToken()).longValue();
							if (numToken == 4)
								Long.valueOf(st.nextToken()).longValue();
						}

						key = attoService.controllaInsertDefinitivita(idProcedimento, 0l, bene.getIdBene(), idInteresse, idSoggetto, idUfficioUtente,
								codUtente, "I_GRADO", super.printMethodName());
						if (key.isError())
							return key;
					}
		if (key.isError())
			return key;

		if (misurePersonali != null && !misurePersonali.isEmpty())
			for (SoggettoForm sogg : misurePersonali)
				if (sogg.getMisuraInfo() != null)
					for (String misuraInfo : sogg.getMisuraInfo()) {
						long idMisura = 0;
						StringTokenizer st = new StringTokenizer(misuraInfo, ";");
						while (st.hasMoreTokens()) {
							idMisura = Long.valueOf(st.nextToken()).longValue();
							Long.valueOf(st.nextToken()).longValue();
						}
						key = attoService.controllaInsertDefinitivita(idProcedimento, idMisura, 0l, 0l, 0l, idUfficioUtente, codUtente, "I_GRADO",
								super.printMethodName());
						if (key.isError())
							return key;
					}

		return key;

	}
	
	/***
	 * Inserisce nella response l'errore del casellario 
	 * @param datiRisposta
	 */
	public void addMessageErrorCasellario(DATIRISPOSTATRASFERIMENTO datiRisposta) {
		StringBuilder errore = new StringBuilder();
		for (ESITO esito :  datiRisposta.getArrayEsito().getESITO()) {
			if (esito.getCODICE()!=null)
				errore.append("[Codice:" + esito.getCODICE() + "]");
			if (esito.getDESCRIZIONE()!=null)
				errore.append("[Descrizione:" + esito.getDESCRIZIONE() + "]");
		}
		super.setMessage(Constants.MESSAGE, "redazioneScheda.erroreNonGestito", errore.toString(), false);
		logger.error(errore.toString());
	}
	
	/***
	 * Inserisce nella response l'errore del casellario per il fogio complementare
	 * @param datiRisposta
	 */
	public void addMessageErrorCasellario(it.giustizia.smp.webservice.client.casellario.esecuzione.DATIRISPOSTATRASFERIMENTO datiRisposta) {
		StringBuilder errore = new StringBuilder();
		for (it.giustizia.smp.webservice.client.casellario.esecuzione.ESITO esito :  datiRisposta.getArrayEsito().getESITO()) {
			if (esito.getCODICE()!=null)
				errore.append("[Codice:" + esito.getCODICE() + "]");
			if (esito.getDESCRIZIONE()!=null)
				errore.append("[Descrizione:" + esito.getDESCRIZIONE() + "]");
		}
		super.setMessage(Constants.MESSAGE, "redazioneScheda.erroreNonGestito", errore.toString(), false);
		logger.error(errore.toString());
	}
	
}
