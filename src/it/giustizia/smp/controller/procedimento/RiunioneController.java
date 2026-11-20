package it.giustizia.smp.controller.procedimento;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import it.giustizia.smp.controller.BreadcrumbsController;
import it.giustizia.smp.dto.KeyMessageDto;
import it.giustizia.smp.form.AllegatoForm;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.MagistratoForm;
import it.giustizia.smp.form.RiunioneForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.model.TAtto;
import it.giustizia.smp.model.TRelAtti;
import it.giustizia.smp.model.TTpCatAtto;
import it.giustizia.smp.service.AnbscService;
import it.giustizia.smp.service.AttoService;
import it.giustizia.smp.service.CommonService;
import it.giustizia.smp.service.ProcedimentoService;
import it.giustizia.smp.service.RiunioneService;
import it.giustizia.smp.service.msg.AnbscMsgService;
import it.giustizia.smp.service.msg.AnbscMsgService.EsitoFromResponse;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Message;
import it.giustizia.smp.utility.RequestUtil;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.utility.ValidationUtil;
import it.giustizia.smp.validation.AllegatoValidation;
import it.giustizia.smp.validation.AttoValidation;
import it.giustizia.smp.webservice.client.anbsc.RiunioneResponse;
import it.giustizia.smp.webservice.client.anbsc.StralcioResponse;

/**
 * RiunioneController
 * 
 * @author Almaviva S.p.A. Classe Controller per la gestione dello controller di un fascicolo
 */
@Controller
public class RiunioneController extends BreadcrumbsController {

	@Autowired
	private CommonService			commonService;
	@Autowired
	private ProcedimentoService		procedimentoService;
	@Autowired
	private RiunioneService 		riunioneService;
	@Autowired
	private AttoService				attoService;
	@Autowired
	private AttoValidation			attoValidation;
	@Autowired
	private AllegatoValidation		allegatoValidation;
	@Autowired
	private AnbscMsgService 		anbscMsgService;
	@Autowired
	private AnbscService 			anbscService;	
	
	public String copertinaGestioneRiunione(RiunioneForm riunioneForm, BindingResult result, ModelMap modelMap) {
	
		long idProcedimentoRiunito = recuperaIdProcedimento();
		
		Integer idUfficio = recuperaIdUfficioUtente();
		
		riunioneForm.setIdProcedimento(idProcedimentoRiunito);
		riunioneForm.setIdUfficio(idUfficio);
		modelMap.addAttribute("riunioneForm",riunioneForm);		
		
		ModelAndView mnv = new ModelAndView("procedimento/riunione/gestioneRiunione", "riunioneForm", riunioneForm);
		
		KeyMessageDto key = riunioneService.controlloRiunione(0, idProcedimentoRiunito, recuperaIdCodiceUtente(), idUfficio.intValue(), super.printMethodName());
		if(key.isError()){
			mnv.addObject("messaggioProcedura", key.getMessage());
			super.setMessage(Constants.MESSAGE, "messaggio.gestito.db", key.getMessage(), false);
			addBreadcrumbs();
			return "procedimento/riunione/gestioneRiunione";
		}
		
		List<SoggettoForm> listSoggInt = procedimentoService.soggettiDirettiInteressatiDelProcedimento(idProcedimentoRiunito, Constants.RUOLO_DIRETTO, Constants.Flag.N);
		List<SoggettoForm> listToViewSoggInt = new ArrayList<SoggettoForm>();
		
		for (Iterator iterator = listSoggInt.iterator(); iterator.hasNext();) {
			SoggettoForm soggettoForm = (SoggettoForm) iterator.next();
			if(soggettoForm.getDescrizioneProcedimentoDestinazione() != null){
				listToViewSoggInt.add(soggettoForm); 
			}
		}
		
		List<SoggettoForm> direttiInteressati = procedimentoService.soggettiDirettiInteressatiDelProcedimento(idProcedimentoRiunito, Constants.RUOLO_DIRETTO, Constants.Flag.S);
		mnv.addObject("direttiInteressati", direttiInteressati);
		mnv.addObject("direttiInteressatiNonValidi", listToViewSoggInt);		
		
		addBreadcrumbs();
		return "procedimento/riunione/gestioneRiunione";
		
	}
	
	@RequestMapping(value = "/procedimento/riunione/gestioneRiunione.htm", method = RequestMethod.GET)
	public ModelAndView gestioneRiunione(RiunioneForm riunioneForm, BindingResult result, ModelMap modelMap) {

		long idProcedimentoRiunito = recuperaIdProcedimento();
		
		Integer idUfficio = recuperaIdUfficioUtente();
		
		ModelAndView mnv = new ModelAndView("procedimento/riunione/gestioneRiunione", "riunioneForm", riunioneForm);
		
		KeyMessageDto key = riunioneService.controlloRiunione(0, idProcedimentoRiunito, recuperaIdCodiceUtente(), idUfficio.intValue(), super.printMethodName());
		if(key.isError()){
			mnv.addObject("messaggioProcedura", key.getMessage());
			super.setMessage(Constants.MESSAGE, "messaggio.gestito.db", key.getMessage(), false);
			return mnv;
		}
		
		List<SoggettoForm> listSoggInt = procedimentoService.soggettiDirettiInteressatiDelProcedimento(idProcedimentoRiunito, Constants.RUOLO_DIRETTO, Constants.Flag.N);
		List<SoggettoForm> listToViewSoggInt = new ArrayList<SoggettoForm>();
		
		for (Iterator iterator = listSoggInt.iterator(); iterator.hasNext();) {
			SoggettoForm soggettoForm = (SoggettoForm) iterator.next();
			if(soggettoForm.getDescrizioneProcedimentoDestinazione() != null){
				listToViewSoggInt.add(soggettoForm); 
			}
		}
		
		List<SoggettoForm> direttiInteressati = procedimentoService.soggettiDirettiInteressatiDelProcedimento(idProcedimentoRiunito, Constants.RUOLO_DIRETTO, Constants.Flag.S);
		mnv.addObject("direttiInteressati", direttiInteressati);
		mnv.addObject("direttiInteressatiNonValidi", listToViewSoggInt);
		
		return mnv;
	}
	
	@RequestMapping(value = "/procedimento/riunione/gestioneRiunione", method = RequestMethod.POST, params={"ricercaSoggettiRiunente"})
	public ModelAndView ricercaSoggettiRiunente(RiunioneForm riunioneForm, BindingResult result,  ModelMap modelMap, HttpServletRequest request) {
		
		int idUfficio = recuperaIdUfficioUtente().intValue();
		ValidationUtil.required("riunioneAnno", result);
		ValidationUtil.required("riunioneNumero", result);
		long idProcedimentoRiunito = recuperaProcedimento().getIdProcedimento();
		String codiceUtente = recuperaIdCodiceUtente();
		
		if (result.hasErrors()) {
			return this.gestioneRiunione(riunioneForm, result, modelMap);
		}

		Long idProcedimentoRiunente = null; 

		List<Long> procedimentoList = procedimentoService.getIdProcByAnnoNumProc(idUfficio, Short.parseShort(riunioneForm.getRiunioneAnno()), Long.parseLong(riunioneForm.getRiunioneNumero()),  super.getIdTipoFascicolo(false));
		if(procedimentoList!=null && procedimentoList.size()>0) {
			idProcedimentoRiunente = procedimentoList.get(0);
		}
		
		if(idProcedimentoRiunente == null){
			result.reject(Constants.MESSAGE);
			setMessage(Constants.MESSAGE, "no.item.found", null, false);
		}
		riunioneForm.setIdProcedimentoRiunente(idProcedimentoRiunente);
		riunioneForm.setIdUfficio(idUfficio);
				
		if (result.hasErrors()) {
			return this.gestioneRiunione(riunioneForm, result, modelMap);
		}
		
		KeyMessageDto key = riunioneService.controlloRiunione(idProcedimentoRiunente.longValue(), idProcedimentoRiunito, recuperaIdCodiceUtente(), idUfficio, super.printMethodName());
		if(key.isError()){
			super.setMessage(Constants.MESSAGE, "messaggio.gestito.db", key.getMessage(), false);
			return gestioneRiunione(riunioneForm, result, modelMap);
		}
			
		//cerco l'atto di riunione
		List<AttoForm> listaAttiRiunione = attoService.getAttiByProcedimento(idProcedimentoRiunito, 13l, "13");
        AttoForm  attoForm = null;
		if(listaAttiRiunione != null && !listaAttiRiunione.isEmpty())
		{
			Iterator<AttoForm> iter = listaAttiRiunione.iterator();
			while (iter.hasNext()) {
				AttoForm attoTmp = iter.next();
				//solo se l'atto è in lavorazione
				if(attoTmp != null && attoTmp.getStatoAtto() != null && "1".equalsIgnoreCase(attoTmp.getStatoAtto().getId())){					
//				if(attoTmp != null && attoTmp.getStatoAtto() != null ){								
					attoForm = attoTmp;		
				}
			}	
			
			if(attoForm != null){			
		        riunioneForm.setCodAtto(attoForm.getCodAtto());
		        riunioneForm.setIdAtto(attoForm.getIdAtto());					
				String dataRiunione = Utility.formatDate(attoForm.getDataEmissione());
				riunioneForm.setDataRiunione(dataRiunione);
				int ruolo=5;	
				MagistratoForm magistratoForm = attoService.getMagistratoPerAtto(idUfficio, attoForm.getCodAtto(),ruolo);
				if(magistratoForm != null){
					ComboForm mag = new ComboForm();
					mag.setId(magistratoForm.getId()+"");
				    riunioneForm.setMagistrato(mag);
					
				}
				
				if(attoForm.getStatoAtto() != null){
					ComboForm statoAtto = new ComboForm();
					statoAtto.setId(attoForm.getStatoAtto().getId());
					riunioneForm.setStatoAtto(statoAtto);
				}

				AttoForm attoFromDB = this.attoService.getAttoById(riunioneForm.getCodAtto(), idUfficio);
				if (attoFromDB.getAllegato().getDocumento() != null) {
					riunioneForm.getAllegato().setDocumento(attoFromDB.getAllegato().getDocumento());
					riunioneForm.getAllegato().setFileName(attoFromDB.getAllegato().getFileName());
					riunioneForm.getAllegato().setContentType(attoFromDB.getAllegato().getContentType());
				}
				else
				{
					if (attoFromDB.getAllegato().getFileName() != null)
						riunioneForm.getAllegato().setFileName(attoFromDB.getAllegato().getFileName());
					if (attoFromDB.getAllegato().getContentType() != null)
						riunioneForm.getAllegato().setContentType(attoFromDB.getAllegato().getContentType());
				}
							
				attoForm = caricaAttoInSessione(idUfficio, attoForm.getCodAtto(), codiceUtente, idProcedimentoRiunito, request, riunioneForm);						
			}
			
		}		
				
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("riunioneAnno", riunioneForm.getRiunioneAnno());
		parameters.put("riunioneNumero", riunioneForm.getRiunioneNumero());
		
		addBreadcrumbs("POST", "/procedimento/riunione/gestioneRiunione", "ricercaSoggettiRiunente").addParameter(parameters);
		
		List<SoggettoForm> direttiIntRiunente = procedimentoService.soggettiDirettiInteressatiDelProcedimentoRiunente(idProcedimentoRiunente.longValue(), null, Constants.Flag.S, idProcedimentoRiunito);
		
		modelMap.addAttribute("direttiInteressatiRiunente", direttiIntRiunente);
		List<MagistratoForm> magistratiAssociati = procedimentoService.ricercaMagistrati(new MagistratoForm(), idUfficio);
		modelMap.addAttribute("listaMagistrati", creaComboMagistrati(magistratiAssociati));
			
		return gestioneRiunione(riunioneForm, result, modelMap);
	}
		
	@RequestMapping(value = "/procedimento/riunione/gestioneRiunione", method = RequestMethod.POST, params = { "ConfermaEsecuzioneRiunione" })
	public ModelAndView esecuzioneRiunione(RiunioneForm riunioneForm, BindingResult result, ModelMap modelMap, HttpServletRequest request) {
		
		long idProcedimentoRiunente = riunioneForm.getIdProcedimentoRiunente();
		long idProcedimentoRiunito = recuperaIdProcedimento();
		int idUfficio = recuperaIdUfficioUtente().intValue();
		String codiceUtente = recuperaIdCodiceUtente();
		long idAtto=0l;	
		long idProcedimento = recuperaIdProcedimento();
		
		/********************gestione documentale****************************************/
//		TODO DA SISTEMARE, per ora è cablato il catAtto	

		TTpCatAtto tTpCatAtto = commonService.getCategoriaAtto(13);	
		allegatoValidation.validateAllegatoEFirma(riunioneForm.getAllegato(), result, request, tTpCatAtto);
		
		/********************gestione documentale****************************************/
		
		attoValidation.validateEsecuzioneRiunione(riunioneForm, result, request, "Conferma");

//---		
		//cerco l'atto di riunione
		List<AttoForm> listaRiunione = attoService.getAttiByProcedimento(idProcedimento, 13l, "13");

		if(listaRiunione != null && !listaRiunione.isEmpty()){
			Iterator<AttoForm> iter = listaRiunione.iterator();
			while (iter.hasNext()) {
				AttoForm attoTmp = iter.next();
				//solo se l'atto è in lavorazione
				if(attoTmp != null && attoTmp.getStatoAtto() != null && "1".equalsIgnoreCase(attoTmp.getStatoAtto().getId())){
//				if(attoTmp != null && attoTmp.getStatoAtto() != null ){
					
					if(attoTmp.getIdAtto() != null){
					  idAtto = attoTmp.getIdAtto();
					}else{ 
					  idAtto = attoTmp.getCodAtto();
					}			
				}
			}			
		}				
//--
		riunioneForm.setIdAtto(idAtto);
		riunioneForm.setCodAtto(idAtto);

		List<Message> listaMessaggiSuccesso = new ArrayList<Message>();
		List<Message> listaMessaggiErrore = new ArrayList<Message>();
		
		inizializzaDocumentoRiunione(riunioneForm, request);			

		if(!result.hasErrors()){
			KeyMessageDto key = riunioneService.esecuzioneRiunione("INVIA",idAtto,idProcedimentoRiunente, idProcedimentoRiunito, codiceUtente, idUfficio, 
					super.printMethodName(), Long.valueOf(riunioneForm.getMagistrato().getId()).longValue(), Utility.stringToDate(riunioneForm.getDataRiunione(),"dd/MM/yyyy"), riunioneForm);
			
			super.addMessageIntoList(listaMessaggiSuccesso, Constants.SUCCESS_MESSAGE , "messaggio.gestito.db", key.getMessage());
			riunioneForm.setIdProcedimento(idProcedimento);
			
			riunioneService.documentaleRiunione(idProcedimento, idProcedimentoRiunente, attoService.getIdsDocumentiAttiByProcedimento(idProcedimento));
		}
		
		if (result.hasErrors()) {
			List<SoggettoForm> direttiIntRiunente = procedimentoService.soggettiDirettiInteressatiDelProcedimentoRiunente(idProcedimentoRiunente, null, Constants.Flag.S, idProcedimentoRiunito);
			modelMap.addAttribute("direttiInteressatiRiunente", direttiIntRiunente);
			List<MagistratoForm> magistratiAssociati = procedimentoService.ricercaMagistrati(new MagistratoForm(), idUfficio);
			modelMap.addAttribute("listaMagistrati", creaComboMagistrati(magistratiAssociati));
			List<Message> customMessageList = new ArrayList<Message>();
			super.addMessageIntoList(customMessageList, Constants.MESSAGE, "errors.campi.controllo", null);
			super.setMessages(customMessageList, true);			
			return this.gestioneRiunione(riunioneForm, result, modelMap);
		}
			
		//INIZIA GESTIONE DOCUMENTALE
		 gestionePiattaformaDocumentale(riunioneForm, request, idUfficio, result);
		//FINE GESTIONE DOCUMENTALE	
		 
		//INIZIO ANBSC - INVIO RIUNIONE
		String codUtente = recuperaIdCodiceUtente();
		
		if(anbscService.verificaInvioStralcioRiunione(idProcedimentoRiunito) == true)  {
			it.giustizia.smp.service.msg.AnbscMsgService.EsitoFromResponse esitoFromResponse = anbscMsgService.invioRiunione(idProcedimentoRiunito, idProcedimentoRiunente, idUfficio, codUtente);

			switch (esitoFromResponse.getEsito()) {
			case 'C':
				super.addMessageIntoList(listaMessaggiSuccesso, Constants.SUCCESS_MESSAGE, esitoFromResponse.getMessaggio(), null);
				break;

			default:
				super.addMessageIntoList(listaMessaggiErrore, Constants.MESSAGE, esitoFromResponse.getMessaggio(), null);
				break;

			}			
		}
		super.setMessaggiErrori(listaMessaggiErrore, true);
		super.setMessagesSuccess(listaMessaggiSuccesso, true);

		//Mev 2020/22
		//if(anbscMsgService.invioRiunione(idProcedimentoRiunito, idProcedimentoRiunente, idUfficio, codUtente) == false){
		//	super.setMessage(Constants.MESSAGE, "anbsc.invio.error", null, false);
		//}
		//FINE ANBSC - INVIO RIUNIONE
		
		
		addBreadcrumbs("POST", "/procedimento/riunione/gestioneRiunione", "ConfermaEsecuzioneRiunione");
		
		return new ModelAndView("redirect:/procedimento/apri/copertinaProcedimento.htm");
	}
	
	@RequestMapping(value = "/procedimento/riunione/gestioneRiunione", method = RequestMethod.POST, params = { "SalvaRiunione" })
	public ModelAndView salvaRiunione(RiunioneForm riunioneForm, BindingResult result, ModelMap modelMap, HttpServletRequest request) {
		
		long idProcedimentoRiunente = riunioneForm.getIdProcedimentoRiunente();
		long idProcedimentoRiunito = recuperaIdProcedimento();
		int idUfficio = recuperaIdUfficioUtente().intValue();
		String codiceUtente = recuperaIdCodiceUtente();
		
		long idAtto=0l;		
		long idProcedimento = recuperaIdProcedimento();
				
		attoValidation.validateEsecuzioneRiunione(riunioneForm, result, request, "Salva");
//---		
		//cerco l'atto di riunione
		List<AttoForm> listaRiunione = attoService.getAttiByProcedimento(idProcedimento, 13l, "13");

		if(listaRiunione != null && !listaRiunione.isEmpty()){
			Iterator<AttoForm> iter = listaRiunione.iterator();
			while (iter.hasNext()) {
				AttoForm attoTmp = iter.next();
				//solo se l'atto è in lavorazione
				if(attoTmp != null && attoTmp.getStatoAtto() != null  && "1".equalsIgnoreCase(attoTmp.getStatoAtto().getId())){
//				if(attoTmp != null && attoTmp.getStatoAtto() != null ){
					
					if(attoTmp.getIdAtto() != null){
					  idAtto = attoTmp.getIdAtto();
					}else{ 
					  idAtto = attoTmp.getCodAtto();
					}			
				}
			}			
		}				
//--
		riunioneForm.setIdAtto(idAtto);
		riunioneForm.setCodAtto(idAtto);

		inizializzaDocumentoRiunione(riunioneForm, request);	
		
		long idMagistrato = 0;
		if(riunioneForm.getMagistrato()!=null && riunioneForm.getMagistrato().getId()!=null) idMagistrato = Long.valueOf(riunioneForm.getMagistrato().getId());		
		
		if(!result.hasErrors()){
			KeyMessageDto key = riunioneService.esecuzioneRiunione("SALVA",idAtto,idProcedimentoRiunente, idProcedimentoRiunito, codiceUtente, idUfficio, 
					super.printMethodName(), idMagistrato, Utility.stringToDate(riunioneForm.getDataRiunione(),"dd/MM/yyyy"), riunioneForm);
			
			super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", key.getMessage(), true);
			riunioneForm.setIdProcedimento(idProcedimento);
			
		}
		
		if (result.hasErrors()) {
			List<SoggettoForm> direttiIntRiunente = procedimentoService.soggettiDirettiInteressatiDelProcedimentoRiunente(idProcedimentoRiunente, null, Constants.Flag.S, idProcedimentoRiunito);
			modelMap.addAttribute("direttiInteressatiRiunente", direttiIntRiunente);
			List<MagistratoForm> magistratiAssociati = procedimentoService.ricercaMagistrati(new MagistratoForm(), idUfficio);
			modelMap.addAttribute("listaMagistrati", creaComboMagistrati(magistratiAssociati));
			List<Message> customMessageList = new ArrayList<Message>();
			super.addMessageIntoList(customMessageList, Constants.MESSAGE, "errors.campi.controllo", null);
			return this.gestioneRiunione(riunioneForm, result, modelMap);
		}
			
			
		return this.ricercaSoggettiRiunente(riunioneForm, result, modelMap, request);

//		addBreadcrumbs("POST", "/procedimento/riunione/gestioneRiunione", "SalvaRiunione");		
//		return new ModelAndView("redirect:/procedimento/apri/copertinaProcedimento.htm");	
		
	}
	
	@RequestMapping(value = "/procedimento/riunione/gestioneRiunione", method = RequestMethod.POST, params= {"dettaglioSoggetto"})
	public ModelAndView dettaglioSoggetto(@Valid RiunioneForm riunioneForm, BindingResult result, ModelMap modelMap, HttpServletRequest request) {

		//String idTipoProc = (String) request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO);
		int idTipoFasc = super.getIdTipoFascicolo(false);
		
		if (riunioneForm.getIdSoggetto() == null){
			super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
//			return "procedimento/riunione/gestioneRiunione";
			return ricercaSoggettiRiunente(riunioneForm, result, modelMap, request);
		}
		
		SoggettoForm sf = new SoggettoForm();
		sf.setIdUfficio(recuperaIdUfficioUtente());
		sf.setIdSoggetto(riunioneForm.getIdSoggetto());
		sf.setIdVersione(riunioneForm.getIdVersioneSoggetto());
		
		if(Constants.SOGGETTO_FISICO.equalsIgnoreCase(riunioneForm.getTipoSoggetto())){
			sf = procedimentoService.caricaValoriDettaglioSoggettoFisico(sf);
		}else{
			sf = procedimentoService.caricaValoriDettaglioSoggettoGiuridico(sf);
		}
			
		modelMap.addAttribute("soggettoForm", sf);
		
		addBreadcrumbs("dettaglioSoggetto",request);
		
		if(Constants.SOGGETTO_FISICO.equalsIgnoreCase(riunioneForm.getTipoSoggetto())){
			modelMap.addAttribute("indietro", "/procedimento/nuovo/indietroNuovoSoggettoFisicoRiesame.htm");
			if(Constants.TIPO_FASCICOLO_OPP_DIVIETI_QUESTORE==(idTipoFasc))
//				return "/procedimento/nuovo/riesame/dettaglioSoggettoFisicoProcedimentoRiesame";
				return  new ModelAndView("/procedimento/nuovo/riesame/dettaglioSoggettoFisicoProcedimentoRiesame", "soggettoForm", sf);
			else
//				return "procedimento/nuovo/dettaglioSoggettoFisico";
			return  new ModelAndView("/procedimento/nuovo/dettaglioSoggettoFisico", "soggettoForm", sf);
		}else{
//			return "procedimento/nuovo/dettaglioSoggettoGiuridico";
			return  new ModelAndView("/procedimento/nuovo/dettaglioSoggettoFisico", "soggettoForm", sf);
		}
	}
	
	
	private List<ComboForm> creaComboMagistrati(List<MagistratoForm> magistratiForm) {
		List<ComboForm> listaCbo = new ArrayList<ComboForm>();
		for (MagistratoForm item : magistratiForm) {
				listaCbo.add(new ComboForm(String.valueOf(item.getId()), String.valueOf(item.getCognome() +" "+ item.getNome())));
		}
		return listaCbo;
	}
	
		@RequestMapping(value = { "/procedimento/riunione/rimuoviAllegatoDaMemoriaDocumentale.htm" }, method = RequestMethod.POST, params = { "rimuoviAllegatoDaMemoriaDocumentale" })
		public ModelAndView rimuoviAllegatoDaMemoriaDocumentaleRiunione(RiunioneForm riunioneForm, BindingResult result, HttpServletRequest request, ModelMap map)
				throws IOException, SQLException {
			
	        AttoForm attoInSessione = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
	        
	        riunioneForm.setCodAtto(attoInSessione.getCodAtto());
	        riunioneForm.setIdAtto(attoInSessione.getIdAtto());			

			riunioneForm.getAllegato().setAllegatoRimosso(true);
			riunioneForm.getAllegato().setDocumentoFirmato("N");
			riunioneForm.getAllegato().getFirmaRemota().setFirmaImmediata(false);
			riunioneForm.getAllegato().setFileName(null);

			if(riunioneForm.getAllegato().isAllegatoRimosso()){
				cancellaDocumentoRiunione(riunioneForm, request);
				riunioneForm.getAllegato().setAllegatoRimosso(false);
			}
			
			return this.ricercaSoggettiRiunente(riunioneForm, result, map, request);		

		}

//
		@RequestMapping(value = { "/procedimento/riunione/downloadDocumentale.htm" }, method = RequestMethod.POST, params = { "downloadDocumentale" })
		public File downloadDocumentaleRiunione(RiunioneForm riunioneForm, BindingResult result, HttpServletRequest request, ModelMap map,
				HttpServletResponse response) throws IOException, SQLException {

			return caricaDocumento(riunioneForm, result, request, response);
		}

	    private File caricaDocumento(RiunioneForm riunioneForm, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws IOException {

			long idUfficio = recuperaIdUfficioUtente();
			
	        AttoForm attoInSessione = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
	        riunioneForm.setCodAtto(attoInSessione.getCodAtto());
	        riunioneForm.setIdAtto(attoInSessione.getIdAtto());
	        riunioneForm.setTipoAtto(attoInSessione.getTipoAtto());
	        riunioneForm.setStatoAtto(attoInSessione.getStatoAtto());
			
			AllegatoForm allegato = new AllegatoForm();
			
			TAtto tAtto = attoService.getTAttoById(riunioneForm.getCodAtto(), idUfficio);
			if(tAtto.getNumeDocumentoPdoc() != null){
				//lo carico dalla PIATT DOC
				//P.Q. 05/07/2022 Ticket 20220627018 Inizio				
				allegato = attoService.extractBlobFromDocumentazione(tAtto.getNumeDocumentoPdoc(), tAtto.getTAnUfficio().getCodiKIdUfficio());
				//P.Q. 05/07/2022 Ticket 20220627018 Fine				
			} else {
				//lo carico da T_ATTO
				allegato.setDocumento(tAtto.getFileDocumento());
			}
			response.setContentType(tAtto.getDescTipoDocumento());
	        response.setHeader("Content-Disposition", "attachment;filename=" + tAtto.getDescNomeDocumento());
	        OutputStream out = response.getOutputStream();

	        try {
	        	
	        	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        	byte[] buf = new byte[1024];

	        	InputStream in = allegato.getDocumento().getBinaryStream();

	        	int n = 0;
	        	while ((n=in.read(buf))>=0)
	        	{
	        	   baos.write(buf, 0, n);

	        	}

	        	in.close();
	        	byte[] bytes = baos.toByteArray(); 
	        	
	        	
				out.write(bytes);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        out.flush();
	        return null;

		}
	
		@RequestMapping(value = { "/procedimento/riunione/firmaPosticipataDocumentale.htm" }, method = RequestMethod.POST, params = { "firmaPosticipataDocumentale" })
//		      public String firmaPosticipataDocumentaleRiunione(RiunioneForm riunioneForm, BindingResult result, HttpServletRequest request, ModelMap map,
		public ModelAndView firmaPosticipataDocumentaleRiunione(RiunioneForm riunioneForm, BindingResult result, HttpServletRequest request, ModelMap map,				
				HttpServletResponse response) throws IOException, SQLException {
			
			AttoForm attoForm = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO)));		
//			String url = copertinaAtto(attoForm, result, request, map);

			Integer idUfficio = recuperaIdUfficioUtente();
			BigInteger idAttoDocumentale = new BigInteger("-1");

			// INIZIA GESTIONE DOCUMENTALE
			validaAllegatoFirmaPosticipata(attoForm, result, request);
			// FINE GESTIONE DOCUMENTALE

			if (attoForm != null && attoForm.getCodAtto() > 0) {
				TAtto tAtto = attoService.getTAttoById(attoForm.getCodAtto(), idUfficio);
				if (tAtto.getNumeDocumentoPdoc() != null)
					idAttoDocumentale = Utility.longToBigInteger(tAtto.getNumeDocumentoPdoc());
			}
			// INIZIA GESTIONE DOCUMENTALE
			gestioneFirmaRemotaDocumento(attoForm, idUfficio, result, idAttoDocumentale);
			// FINE GESTIONE DOCUMENTALE

//			url = copertinaAtto(attoForm, result, request, map);
//			return url;
			
			return this.ricercaSoggettiRiunente(riunioneForm, result, map, request);		
			
//			addBreadcrumbs("POST", "/procedimento/riunione/gestioneRiunione", "SalvaRiunione");		
//			return new ModelAndView("redirect:/procedimento/apri/copertinaProcedimento.htm");	

		}	

	//------------------------	
	
	private AttoForm caricaAttoInSessione(int idUffOwn, long idAtto, String codiceUtente, long idProcedimento, HttpServletRequest request,
			RiunioneForm riunioneForm) {
	     
		AttoForm attoForm = null;
		
		attoForm = attoService.dettaglioAtto(idUffOwn, idAtto, idProcedimento, codiceUtente);
	
		
		// nel caso di decreto di Revoca o Convalida va popolato anche l'atto di
		// origine
		String numeroAttoOrigine = attoService.numeroAttoOrigine(idProcedimento, idAtto, idUffOwn, codiceUtente, super.printMethodName());
		if (numeroAttoOrigine != null)
			attoForm.setNumeroAttoOrigine(numeroAttoOrigine);
		else
			attoForm.setNumeroAttoOrigine("");
		if (attoForm.getCodAttoOrigine() == 0) { //cerco l'id atto origine solo se sono in modifica del decreto
			long idAttoOrigine = attoService.idAttoOrigine(idProcedimento, idAtto, idUffOwn, codiceUtente, super.printMethodName());
			attoForm.setCodAttoOrigine(idAttoOrigine);
		}
	
		String numProcedOrigine = attoService.getNumeroProcedimentoOrigine(recuperaIdProcedimento(), idAtto, idUffOwn, recuperaIdCodiceUtente(),
				super.printMethodName());
		attoForm.setNumeroProcedimentoOrigine(numProcedOrigine);
	
		//provvedimenti fascicolo gestione
		if(attoForm!=null && attoForm.getTipoAtto()!=null && attoForm.getTipoAtto().getId().equals(Constants.TIPO_ATTO_PROVVEDIMENTO)) {
			
			List listaAtti = attoService.getRelAtti(0, 0, idAtto, idUffOwn, recuperaIdCodiceUtente(), super.printMethodName());
			if(listaAtti!=null && !listaAtti.isEmpty()) {
				Map<String, Object> row = (Map<String, Object>) listaAtti.get(0);
				TRelAtti relAtti = (TRelAtti) row.get("relAtti");
				int idUffOwnOrigine = relAtti.getId().getCodiKIdUffOwnerOr();
				long idAttoOrigine = relAtti.getId().getCodiKIdAttoOr();
				AttoForm dettaglioAttoOrigine = attoService.dettaglioAtto(idUffOwnOrigine, idAttoOrigine, idProcedimento, recuperaIdCodiceUtente());
				if(dettaglioAttoOrigine!=null) {
					attoForm.setDataRicezioneAttoOrigine(dettaglioAttoOrigine.getDataRicezione());
					if(dettaglioAttoOrigine.getCategoriaAtto()!=null) {
						attoForm.setDescrizioneCategoriaAttoOrigine(dettaglioAttoOrigine.getCategoriaAtto().getDescrizione());
					}
				}
			}
		}
		//
	
		if (request.getSession().getAttribute(Constants.ATTO) != null)
			request.getSession().removeAttribute(Constants.ATTO);
		request.getSession().setAttribute(Constants.ATTO, attoForm);
	
		return attoForm;
	}
		
	private void validaAllegatoFirmaPosticipata(AttoForm attoForm, BindingResult result, HttpServletRequest request) {

		long idCatAtto = RequestUtil.recuperaIdCategoriaAtto(request);
		if (idCatAtto <= 0)
			idCatAtto = attoForm.getCategoriaAtto() != null ? Long.valueOf(attoForm.getCategoriaAtto().getId()).longValue() : 0;

		TTpCatAtto tTpCatAtto = commonService.getCategoriaAtto(idCatAtto);

		allegatoValidation.validateAllegatoFirmaPosticipata(attoForm.getAllegato(), result, request, tTpCatAtto);

	}
	
}
