package it.giustizia.smp.controller.procedimento;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import it.giustizia.smp.controller.BreadcrumbsController;
import it.giustizia.smp.dto.KeyMessageDto;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.MagistratoForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.form.SoggettoStralcioForm;
import it.giustizia.smp.form.StralcioForm;
import it.giustizia.smp.model.TTpCatAtto;
import it.giustizia.smp.service.AnbscService;
import it.giustizia.smp.service.CommonService;
import it.giustizia.smp.service.ProcedimentoService;
import it.giustizia.smp.service.StralcioService;
import it.giustizia.smp.service.msg.AnbscMsgService;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Message;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.validation.AllegatoValidation;
import it.giustizia.smp.validation.AttoValidation;
import it.giustizia.smp.webservice.client.anbsc.SequestroResponse;
import it.giustizia.smp.webservice.client.anbsc.StralcioResponse;

/**
 * StralcioController
 * 
 * @author Almaviva S.p.A. Classe Controller per la gestione dello stralcio di un fascicolo
 */
@Controller
public class StralcioController extends BreadcrumbsController {

	@Autowired
	private CommonService			commonService;
	@Autowired
	private ProcedimentoService		procedimentoService;
	@Autowired
	private StralcioService 		stralcioService;
	@Autowired
	private AttoValidation			attoValidation;
	@Autowired
	private AllegatoValidation		allegatoValidation;
	@Autowired
	private AnbscMsgService 		anbscMsgService;
	@Autowired
	private AnbscService 			anbscService;	
	
	@Autowired
	private Utility			utility;	
	
	@RequestMapping(value = "/procedimento/stralcio/gestioneStralcio.htm", method = RequestMethod.GET)
	public ModelAndView gestioneStralcio(StralcioForm stralcioForm, BindingResult result, ModelMap modelMap) {

		long idProcedimento = restituisciIdProcedimento(stralcioForm);
		int idUffOwner = restituisciIdUfficioOwner(stralcioForm);
		List<SoggettoStralcioForm> listaStralcio = null;
		KeyMessageDto obj = stralcioService.controlloStralcio(idProcedimento);
		
		// PQ 03/08/2020 Inizio controllo se PU e PG hanno acquisito gli scambi informativi
		if (!obj.isError()) {
			long idUfficio = recuperaIdUfficioUtente().longValue();
			String descUtente = recuperaIdCodiceUtente();
			obj = commonService.controllaInvioProcuraPG(idProcedimento, idUfficio, descUtente, "STRALCIO/RIUNIONE", super.printMethodName());			
		}
		// PQ 03/08/2020 Fine controllo se PU e PG hanno acquisito gli scambi informativi
				
		stralcioForm.setIdProcedimento(idProcedimento);
		stralcioForm.setIdUfficio(idUffOwner);
		ModelAndView mnv = new ModelAndView("procedimento/stralcio/gestioneStralcio", "stralcioForm", stralcioForm);
		
		if (obj.isError()){
			super.setMessage(Constants.MESSAGE, "messaggio.gestito.db", obj.getMessage(), false);
			mnv.addObject("messaggioProcedura", obj.getMessage());
		}else{
		
			List<ComboForm> tipoMisure = new ArrayList<ComboForm>();
			tipoMisure.add(new ComboForm(Constants.TipoMisura.PATRIMONIALE_ID, Constants.TipoMisura.PATRIMONIALE_DESCR));
			tipoMisure.add(new ComboForm(Constants.TipoMisura.PERSONALE_ID, Constants.TipoMisura.PERSONALE_DESCR));
			
			List<SoggettoForm> soggettiDirettiInteressati = procedimentoService.soggettiDirettiInteressatiDelProcedimento(idProcedimento, Constants.RUOLO_DIRETTO, Constants.Flag.S);
			List<SoggettoForm> soggettiTitolari = procedimentoService.soggettiTitolariDelProcedimento(idProcedimento);
			
			listaStralcio = stralcioService.ricercaStralcio(idProcedimento, idUffOwner, stralcioForm, null);
			
			modelMap.addAttribute("listaStralcio", listaStralcio);
			
			mnv.addObject("soggettiDiretti", utility.creaComboSoggetti(soggettiDirettiInteressati));
			mnv.addObject("soggettiTitolari", utility.creaComboSoggetti(soggettiTitolari));
			mnv.addObject("tipoMisure", tipoMisure);
		
		}
		addBreadcrumbs();
		return mnv;
	}
	


	public String copertinaGestioneStralcio(StralcioForm stralcioForm, BindingResult result, ModelMap modelMap) {
	
		
		long idProcedimento = restituisciIdProcedimento(stralcioForm);
		int idUffOwner = restituisciIdUfficioOwner(stralcioForm);
		List<SoggettoStralcioForm> listaStralcio = null;
		@SuppressWarnings("unused")
		KeyMessageDto obj = stralcioService.controlloStralcio(idProcedimento);
		
		stralcioForm.setIdProcedimento(idProcedimento);
		stralcioForm.setIdUfficio(idUffOwner);
		modelMap.addAttribute("stralcioForm",stralcioForm);
			
		List<ComboForm> tipoMisure = new ArrayList<ComboForm>();
		tipoMisure.add(new ComboForm(Constants.TipoMisura.PATRIMONIALE_ID, Constants.TipoMisura.PATRIMONIALE_DESCR));
		tipoMisure.add(new ComboForm(Constants.TipoMisura.PERSONALE_ID, Constants.TipoMisura.PERSONALE_DESCR));
		
		List<SoggettoForm> soggettiDirettiInteressati = procedimentoService.soggettiDirettiInteressatiDelProcedimento(idProcedimento, Constants.RUOLO_DIRETTO, Constants.Flag.S);
		List<SoggettoForm> soggettiTitolari = procedimentoService.soggettiTitolariDelProcedimento(idProcedimento);
		
		listaStralcio = stralcioService.ricercaStralcio(idProcedimento, idUffOwner, stralcioForm, null);
		
		modelMap.addAttribute("listaStralcio", listaStralcio);
		
		modelMap.addAttribute("soggettiDiretti", utility.creaComboSoggetti(soggettiDirettiInteressati));
		modelMap.addAttribute("soggettiTitolari", utility.creaComboSoggetti(soggettiTitolari));
		modelMap.addAttribute("tipoMisure", tipoMisure);
		
		
		addBreadcrumbs();
		return "procedimento/stralcio/gestioneStralcio";		
	}
	
	@RequestMapping(value = "/procedimento/stralcio/ricercaStralcioOnSelect", method = RequestMethod.GET)
	public ModelAndView ricercaStralcio(StralcioForm stralcioForm, BindingResult result,  ModelMap modelMap) {
		List<SoggettoStralcioForm> listaStralcio = null;
		
		long idProcedimento = restituisciIdProcedimento(stralcioForm);
		long idUffOwner = restituisciIdUfficioOwner(stralcioForm);
		
		if(stralcioForm.getTipologiaBene() != null){
			modelMap.addAttribute("categorie", utility.caricaComboCategoria(stralcioForm.getTipologiaBene().getId()));
		}
		if(stralcioForm.getCategoriaBene() != null){
			modelMap.addAttribute("sottoCategorieBene", utility.caricaComboSottoCategoria(stralcioForm.getCategoriaBene().getId()));
		}
		listaStralcio = stralcioService.ricercaStralcio(idProcedimento, idUffOwner, stralcioForm, null);
		
		modelMap.addAttribute("listaStralcio", listaStralcio);
		
		return gestioneStralcio(stralcioForm, result, modelMap);
	}
	
	
	@RequestMapping(value = "/procedimento/stralcio/gestioneStralcio", method = RequestMethod.POST, params ={ "ricercaStralcioOnSelect"})
	public ModelAndView ricercaStralcioOnSelect(StralcioForm stralcioForm, BindingResult result,  ModelMap modelMap) {
		List<SoggettoStralcioForm> listaStralcio = null;
		
		long idProcedimento = stralcioForm.getIdProcedimento();
		long idUffOwner = restituisciIdUfficioOwner(stralcioForm);

		
		if(stralcioForm.getTipologiaBene() != null){
			modelMap.addAttribute("categorie", utility.caricaComboCategoria(stralcioForm.getTipologiaBene().getId()));
		}
		if(stralcioForm.getCategoriaBene() != null){
			modelMap.addAttribute("sottoCategorieBene", utility.caricaComboSottoCategoria(stralcioForm.getCategoriaBene().getId()));
		}
		listaStralcio = stralcioService.ricercaStralcio(idProcedimento, idUffOwner, stralcioForm, null);
		
		modelMap.addAttribute("listaStralcio", listaStralcio);
		
		return gestioneStralcio(stralcioForm, result, modelMap);
	}
	
	
	
	@RequestMapping(value = { "/procedimento/stralcio/caricaCombo/{comboDaCaricare}/{id}" }, method = RequestMethod.POST)
	public @ResponseBody List<ComboForm> caricaComboPost(StralcioForm stralcioForm, @PathVariable String comboDaCaricare, @PathVariable String id, ModelMap modelMap) {

		if("categoriaBene".equalsIgnoreCase(comboDaCaricare)){
			return utility.caricaComboCategoria(id);
		}else if("sottoCategoriaBene".equalsIgnoreCase(comboDaCaricare)){
			return utility.caricaComboSottoCategoria(id);
		}else{
			return null;
		}
	}
	
	

	@RequestMapping(value = { "/procedimento/stralcio/impostaStralcio" }, method = RequestMethod.POST)
	public ModelAndView impostaStralcio(@Valid StralcioForm stralcioForm, BindingResult result,
			@RequestParam(value = "tipoStralcio", required = true) String tipoStralcio,
			@RequestParam(value = "idProcedimento", required = true) long idProcedimento,
			@RequestParam(value = "idUffOwner", required = true) long idUffOwner,
			@RequestParam(value = "checked", required = true) boolean checked,
			@RequestParam(value = "idSoggInt", required = true) long idSoggInt,
			@RequestParam(value = "idBene", required = false) long idBene,
			@RequestParam(value = "filtroIdsSoggTit", required = false) String filtroIdsSoggTit,
			@RequestParam(value = "filtroIdsMisura", required = false) String filtroIdsMisura,
			@RequestParam(value = "filtroTipoBene", required = false) String filtroTipoBene,
			@RequestParam(value = "filtroCatBene", required = false) String filtroCatBene,
			@RequestParam(value = "filtroSottoCatBene", required = false) String filtroSottoCatBene,
			@RequestParam(value = "idSoggTit", required = false) String idSoggTit, ModelMap modelMap){

		if (Constants.TipoMisura.PATRIMONIALE_DESCR.equalsIgnoreCase(tipoStralcio)) {
			if("0".equalsIgnoreCase(idSoggTit)){
				idSoggTit = filtroIdsSoggTit;
			}
			
			stralcioService.impostaStralcioMisPatr(idProcedimento, idUffOwner, checked, idSoggInt, idBene, idSoggTit, Integer.parseInt(filtroTipoBene), Integer.parseInt(filtroCatBene), Integer.parseInt(filtroSottoCatBene), recuperaIdCodiceUtente());
			
		} else if (Constants.TipoMisura.PERSONALE_DESCR.equalsIgnoreCase(tipoStralcio)) {
			stralcioService.impostaStralcioMisPers(idProcedimento, idUffOwner, checked, idSoggInt,  recuperaIdCodiceUtente());
		}else{
			//per soggetto
			if(filtroTipoBene == null || filtroTipoBene.equalsIgnoreCase(Constants.UNDEFINED))
				filtroTipoBene = "0";
			if(filtroCatBene == null || filtroCatBene.equalsIgnoreCase(Constants.UNDEFINED))
				filtroCatBene = "0";
			if(filtroSottoCatBene == null || filtroSottoCatBene.equalsIgnoreCase(Constants.UNDEFINED))
				filtroSottoCatBene = "0";
			stralcioService.impostaStralcioSoggetto(idProcedimento ,idUffOwner, checked, idSoggInt, filtroIdsSoggTit, filtroIdsMisura, Integer.parseInt(filtroTipoBene), Integer.parseInt(filtroCatBene), Integer.parseInt(filtroSottoCatBene), recuperaIdCodiceUtente());
		}
		
		return ricercaStralcioOnSelect(stralcioForm, result, modelMap);
	}
	
	@RequestMapping(value = "/procedimento/stralcio/gestioneStralcio", method = RequestMethod.POST, params = { "ConfermaEsecuzioneStralcio" })
	public ModelAndView esecuzioneStralcio(StralcioForm stralcioForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) throws IOException, SQLException {
				
		addBreadcrumbs("ConfermaEsecuzioneStralcio",request);
//		/********************gestione documentale****************************************/
//		TODO DA SISTEMARE, per ora è cablato il catAtto
		TTpCatAtto tTpCatAtto = commonService.getCategoriaAtto(14);	
		allegatoValidation.validateAllegatoEFirma(stralcioForm.getAllegato(), result, request, tTpCatAtto);
//		
//		/********************gestione documentale****************************************/
		attoValidation.validateEsecuzioneStralcio(stralcioForm, result, request, "Conferma");
//		
		long idProcedimento = restituisciIdProcedimento(stralcioForm);
		long idAtto=0l;
		int idUffOwner = restituisciIdUfficioOwner(stralcioForm);
		stralcioForm.setIdProcedimento(idProcedimento);
		stralcioForm.setIdUfficio(idUffOwner);
		
		//cerco l'atto di stralcio
		List<AttoForm> listaStralcio = attoService.getAttiByProcedimento(idProcedimento, 13l, "14");

		if(listaStralcio != null && !listaStralcio.isEmpty()){
			Iterator<AttoForm> iter = listaStralcio.iterator();
			while (iter.hasNext()) {
				AttoForm attoTmp = iter.next();
				//solo se l'atto è in lavorazione
				if(attoTmp != null && attoTmp.getStatoAtto() != null && "1".equalsIgnoreCase(attoTmp.getStatoAtto().getId())){
					
					if(attoTmp.getIdAtto() != null){
					  idAtto = attoTmp.getIdAtto();
					}else{ 
					  idAtto = attoTmp.getCodAtto();
					}			
				}
			}			
		}
		
		//modifica maggio 2018 Ginesti
		
		   stralcioForm.setCodAtto(idAtto);		
				
		//	stralcioForm.setIdAtto(idAtto);		
				
		//
		
		if(result.hasErrors()){
			List<Message> customMessageList = new ArrayList<Message>();
			super.addMessageIntoList(customMessageList, Constants.MESSAGE, "errors.campi.controllo", null);
			super.setMessages(customMessageList, true);
			return riepilogoStralcio(stralcioForm, result, modelMap);
		}

		
		List<Message> listaMessaggiSuccesso = new ArrayList<Message>();
		List<Message> listaMessaggiErrore = new ArrayList<Message>();
		
		long idMagistrato = 0;
		if(stralcioForm.getMagistrato()!=null && stralcioForm.getMagistrato().getId()!=null) idMagistrato = Long.valueOf(stralcioForm.getMagistrato().getId());
		
		
		KeyMessageDto obj = stralcioService.esecuzioneStralcio("INVIA",idAtto,idProcedimento, recuperaIdCodiceUtente(), Utility.stringToDate(stralcioForm.getDataStralcio(),"dd/MM/yyyy"), idMagistrato, stralcioForm);
		
		if(obj.isError()){			
			super.addMessageIntoList(listaMessaggiErrore, Constants.MESSAGE , "messaggio.gestito.db", obj.getMessage());
		}else{
			super.addMessageIntoList(listaMessaggiSuccesso, Constants.SUCCESS_MESSAGE , "messaggio.gestito.db", obj.getMessage());
			stralcioService.documentaleStralcio(idProcedimento, obj.getId(), attoService.getDocumentiAttiByProcedimento(idProcedimento));			
		
			//INIZIA GESTIONE DOCUMENTALE
			gestionePiattaformaDocumentale(stralcioForm, request, idUffOwner, result);
			//FINE GESTIONE DOCUMENTALE	
			
			stralcioForm.setIdProcedimentoDestinatario(obj.getId());
			
			//INIZIO ANBSC - INVIO STRALCIO
			String codUtente = recuperaIdCodiceUtente();
			//Mev 2020/22
			//if(anbscMsgService.invioStralcio(idProcedimento, obj.getId(), idUffOwner, codUtente) == false){
			//	super.setMessage(Constants.MESSAGE, "anbsc.invio.error", null, false);
			//}
			if(anbscService.verificaInvioStralcioRiunione(idProcedimento) == true) {
				it.giustizia.smp.service.msg.AnbscMsgService.EsitoFromResponse esitoFromResponse = anbscMsgService.invioStralcio(idProcedimento, obj.getId(), idUffOwner, codUtente);

				switch (esitoFromResponse.getEsito()) {
				case 'C':
					super.addMessageIntoList(listaMessaggiSuccesso, Constants.SUCCESS_MESSAGE, esitoFromResponse.getMessaggio(), null);
					break;

				default:
					super.addMessageIntoList(listaMessaggiErrore, Constants.MESSAGE, "anbsc.invio.ko", esitoFromResponse.getMessaggio());
					break;
	
				}			
			}
			//FINE ANBSC - INVIO STRALCIO
		
		}
		
		super.setMessaggiErrori(listaMessaggiErrore, true);
		super.setMessagesSuccess(listaMessaggiSuccesso, true);
		
		return visualizzaPaginaAttiGenerici(stralcioForm, result, modelMap);
		
	}
	
	@RequestMapping(value = "/procedimento/stralcio/gestioneStralcio", method = RequestMethod.POST, params = { "SalvaStralcio" })
	public ModelAndView salvaStralcio(StralcioForm stralcioForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) throws IOException, SQLException {
				
		
		addBreadcrumbs();
		attoValidation.validateEsecuzioneStralcio(stralcioForm, result, request, "Salva");
		
		long idProcedimento = restituisciIdProcedimento(stralcioForm);
		long idAtto=0l;
		int idUffOwner = restituisciIdUfficioOwner(stralcioForm);
		stralcioForm.setIdProcedimento(idProcedimento);
		stralcioForm.setIdUfficio(idUffOwner);

		//cerco l'atto di stralcio
		List<AttoForm> listaStralcio = attoService.getAttiByProcedimento(idProcedimento, 13l, "14");

		if(listaStralcio != null && !listaStralcio.isEmpty()){
			Iterator<AttoForm> iter = listaStralcio.iterator();
			while (iter.hasNext()) {
				AttoForm attoTmp = iter.next();
				//solo se l'atto è in lavorazione
				if(attoTmp != null && attoTmp.getStatoAtto() != null && "1".equalsIgnoreCase(attoTmp.getStatoAtto().getId())){
					
					if(attoTmp.getIdAtto() != null){
					  idAtto = attoTmp.getIdAtto();
					}else{ 
					  idAtto = attoTmp.getCodAtto();
					}			
				}
			}			
		}		
		//modifica maggio 2018 Ginesti
		
		stralcioForm.setCodAtto(idAtto);		
		
		//stralcioForm.setIdAtto(idAtto);		
		
		//
		
		if(result.hasErrors()){
			List<Message> customMessageList = new ArrayList<Message>();
			super.addMessageIntoList(customMessageList, Constants.MESSAGE, "errors.campi.controllo", null);
			super.setMessages(customMessageList, true);
			return riepilogoStralcio(stralcioForm, result, modelMap);
		}

		long idMagistrato = 0;
		if(stralcioForm.getMagistrato()!=null && stralcioForm.getMagistrato().getId()!=null) idMagistrato = Long.valueOf(stralcioForm.getMagistrato().getId());
		
		
		KeyMessageDto obj = stralcioService.esecuzioneStralcio("SALVA",idAtto,idProcedimento, recuperaIdCodiceUtente(), Utility.stringToDate(stralcioForm.getDataStralcio(),"dd/MM/yyyy"), idMagistrato, stralcioForm);
		
		if(obj.isError()){
			super.setMessage(Constants.MESSAGE , "messaggio.gestito.db", obj.getMessage(), true);
		}else{
			super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", obj.getMessage(), true);					
		
			//INIZIA GESTIONE DOCUMENTALE
			gestionePiattaformaDocumentale(stralcioForm, request, idUffOwner, result);
			//FINE GESTIONE DOCUMENTALE				

		}
		
		return visualizzaPaginaAttiGenerici(stralcioForm, result, modelMap);
		
	}	
	
	@RequestMapping(value = "/procedimento/stralcio/gestioneStralcio", method = RequestMethod.POST, params = { "visualizzaPaginaAttiGenerici" })
	public ModelAndView visualizzaPaginaAttiGenerici(StralcioForm stralcioForm, BindingResult result, ModelMap modelMap) {
		
		addBreadcrumbs("POST","/procedimento/stralcio/gestioneStralcio","visualizzaPaginaAttiGenerici");
		long idProcedimento = restituisciIdProcedimento(stralcioForm);
		//QUERY ATTI GENERICI CON TIPO ATTO = 14 (per il momento)
		List<AttoForm> listaAttiGenerici = attoService.getAttiOrdineCronologico(idProcedimento, recuperaIdCodiceUtente(), super.printMethodName(), Constants.TipoAtto.TIPO_ATTO_GENERICO_S);
		if(listaAttiGenerici.size()> 0){
			modelMap.addAttribute("listaAtti", listaAttiGenerici);
			//Visualizza la maschera di Copia / Sposta ATTI GENERICI
			return new ModelAndView("procedimento/stralcio/gestioneAttiGenerici");
		}
		
		return new ModelAndView("redirect:/procedimento/apri/copertinaProcedimento.htm");
	}
	
	
	@RequestMapping(value = "/procedimento/stralcio/gestioneStralcio", method = RequestMethod.POST, params ={ "riepilogoStralcio"})
	public ModelAndView riepilogoStralcio(StralcioForm stralcioForm, BindingResult result, ModelMap modelMap) {
		
		ModelAndView mnv = new ModelAndView("procedimento/stralcio/gestioneStralcio", "stralcioForm", stralcioForm);
		long idProcedimento = restituisciIdProcedimento(stralcioForm);
		int idUffOwner = restituisciIdUfficioOwner(stralcioForm);
		StralcioForm sf = new StralcioForm();		
		sf.setIdProcedimento(idProcedimento);
		sf.setIdUfficio(idUffOwner);
		
		//cerco l'atto di stralcio
		List<AttoForm> listaAttiStralcio = attoService.getAttiByProcedimento(idProcedimento, 13l, "14");
        AttoForm  attoForm = null;
		if(listaAttiStralcio != null && !listaAttiStralcio.isEmpty())
		{
			Iterator<AttoForm> iter = listaAttiStralcio.iterator();
			while (iter.hasNext()) {
				AttoForm attoTmp = iter.next();
				//solo se l'atto è in lavorazione
				if(attoTmp != null && attoTmp.getStatoAtto() != null && "1".equalsIgnoreCase(attoTmp.getStatoAtto().getId())){					
					attoForm = attoTmp;		
				}
			}	
			
			if(attoForm != null){
				String dataStralcio = Utility.formatDate(attoForm.getDataEmissione());
				stralcioForm.setDataStralcio(dataStralcio);
				int ruolo=5;	
				MagistratoForm magistratoForm = attoService.getMagistratoPerAtto(idUffOwner, attoForm.getCodAtto(),ruolo);
				if(magistratoForm != null){
					ComboForm mag = new ComboForm();
					mag.setId(magistratoForm.getId()+"");
				    stralcioForm.setMagistrato(mag);
					
				}
				
				if(attoForm.getStatoAtto() != null){
					ComboForm statoAtto = new ComboForm();
					statoAtto.setId(attoForm.getStatoAtto().getId());
					stralcioForm.setStatoAtto(statoAtto);
				}
				
				if(attoForm.getAllegato() != null ){
				   stralcioForm.setAllegato(attoForm.getAllegato());
				}

			}
			
		}		
		
	
		List<SoggettoStralcioForm> listaStralcio = stralcioService.ricercaStralcio(idProcedimento, idUffOwner, sf, "O");	
		modelMap.addAttribute("listaStralcio", listaStralcio);
		
		List<MagistratoForm> magistratiAssociati = procedimentoService.ricercaMagistrati(new MagistratoForm(), idUffOwner);
		modelMap.addAttribute("listaMagistrati", creaComboMagistrati(magistratiAssociati));
		
	
		//attributo utilizzato per rendere disabled i checkbox nel riepilogo dello stralcio
		modelMap.addAttribute("riepilogoStralcio", "disabled");

			
		
		addBreadcrumbs();
		return mnv;
	}
	
	@RequestMapping(value = "/procedimento/stralcio/gestioneStralcioAttoGenerici", method = RequestMethod.POST, params ={ "confermaCopiaSposta"})
	public ModelAndView confermaStralcioCopiaSposta(StralcioForm stralcioForm, BindingResult result, ModelMap modelMap, HttpServletRequest request) {
		
		String operazione = request.getParameter("copiaSposta");
				
		KeyMessageDto obj = stralcioService.copiaSpostaAttoGenerico(recuperaProcedimento().getIdProcedimento(), stralcioForm.getIdProcedimentoDestinatario(), stralcioForm.getCodAtto(), operazione, recuperaIdCodiceUtente(),recuperaIdTipoUfficio(), super.printMethodName());
		
		if (obj.isError()){
			super.setMessage(Constants.MESSAGE, "messaggio.gestito.db", obj.getMessage(), true);
		}else{
			super.setMessage(Constants.SUCCESS_MESSAGE, "success", null, true);
		}
		
		long idProcedimento = restituisciIdProcedimento(stralcioForm);
		//QUERY ATTI GENERICI CON TIPO ATTO = 14 (per il momento)
		List<AttoForm> listaAttiGenerici = attoService.getAttiOrdineCronologico(idProcedimento, recuperaIdCodiceUtente(), super.printMethodName(), Constants.TipoAtto.TIPO_ATTO_GENERICO_S);
		if(listaAttiGenerici.size()> 0){
			modelMap.addAttribute("listaAtti", listaAttiGenerici);
			//Visualizza la maschera di Copia / Sposta ATTI GENERICI
			return new ModelAndView("procedimento/stralcio/gestioneAttiGenerici");
		}
		
		return new ModelAndView("redirect:/procedimento/apri/copertinaProcedimento.htm");
	}


	
	private List<ComboForm> creaComboMagistrati(List<MagistratoForm> magistratiForm) {
		List<ComboForm> listaCbo = new ArrayList<ComboForm>();
		for (MagistratoForm item : magistratiForm) {
				listaCbo.add(new ComboForm(String.valueOf(item.getId()), String.valueOf(item.getCognome() +" "+ item.getNome())));
		}
		return listaCbo;
	}
	
	//utilizzo momentaneo
	private long restituisciIdProcedimento(StralcioForm sf){
		if(sf.getIdProcedimento() != null){
			return sf.getIdProcedimento();
		}else{
			return recuperaIdProcedimento();
		}
	}
	private int restituisciIdUfficioOwner(StralcioForm sf){
		if(sf.getIdUfficio() > 0){
			return sf.getIdUfficio();
		}else{
			return recuperaIdUfficioUtente();
		}
	}
	
}
