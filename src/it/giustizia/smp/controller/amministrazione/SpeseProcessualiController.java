package it.giustizia.smp.controller.amministrazione;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import it.giustizia.smp.controller.BreadcrumbsController;
import it.giustizia.smp.form.SpeseProcessualiForm;
import it.giustizia.smp.service.AmministrazioneService;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.validation.SpeseProcessualiValidation;

@Controller
public class SpeseProcessualiController extends BreadcrumbsController {

	@Autowired
	private AmministrazioneService amministrazioneService;
	
	@Autowired
	private SpeseProcessualiValidation speseProcessualiValidation;
	
/******* Inserimento spese processuali  in base dati *********/
	
	@RequestMapping(value = "/funzioniAmministrazione/ricercaSpeseProcessuali.htm", method = RequestMethod.GET)
	public ModelAndView amministrazioneSpeseProcessuali(ModelMap modelMap,HttpSession session) {
		cleanBreadcrumb(session);
		SpeseProcessualiForm speseProcessualiForm = new SpeseProcessualiForm();
		ModelAndView mnv = new ModelAndView("/amministrazione/ricercaSpeseProcessuali", "speseProcessualiForm", speseProcessualiForm);
		
		return mnv;
	}

	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaSpeseProcessuali.htm" , method = RequestMethod.POST, params = { "Ricerca" })
	public String ricercaSpeseProcessuali(SpeseProcessualiForm speseProcessualiForm, BindingResult result,  HttpServletRequest request, ModelMap modelMap) {
		  
		BigDecimal importoSpese = speseProcessualiForm.getImportoSpese() ==null ? null : speseProcessualiForm.getImportoSpese();
		List<SpeseProcessualiForm> listaSpeseProcessualiForm = amministrazioneService.listaSpeseProcessuali(importoSpese);
		if(listaSpeseProcessualiForm == null)
			listaSpeseProcessualiForm = new ArrayList<SpeseProcessualiForm>();
		modelMap.addAttribute("risultatiRicerca", listaSpeseProcessualiForm);
		return "/amministrazione/ricercaSpeseProcessuali";
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaSpeseProcessuali.htm" , method = RequestMethod.POST)
	public String speseProcessuali(SpeseProcessualiForm speseProcessualiForm, BindingResult result,  HttpServletRequest request, ModelMap modelMap) {
		return ricercaSpeseProcessuali(speseProcessualiForm, result, request, modelMap);
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaSpeseProcessuali.htm" , method = RequestMethod.GET)
	public String visualizzaSpeseProcessuali(SpeseProcessualiForm speseProcessualiForm, BindingResult result,  HttpServletRequest request, ModelMap modelMap) {
		return ricercaSpeseProcessuali(speseProcessualiForm, result, request, modelMap);
	}
	
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaSpeseProcessuali.htm" , method = RequestMethod.POST, params = { "NuovoImportoSpese" })
	public String nuovoImportoSpese(SpeseProcessualiForm speseProcessualiForm, BindingResult result,  HttpServletRequest request, ModelMap modelMap) {
		modelMap.addAttribute("speseProcessualiForm", new SpeseProcessualiForm());
		return "/amministrazione/nuovoSpeseProcessuali";
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/nuovoSpeseProcessuali.htm" , method = RequestMethod.POST, params = { "Inserisci" })
	public String inserisciSpeseProcessuali(@Valid SpeseProcessualiForm speseProcessualiForm, BindingResult result,  HttpServletRequest request, ModelMap modelMap) {
		speseProcessualiValidation.validateInserimento(speseProcessualiForm, result);
		if (result.hasErrors()) {
			return "/amministrazione/nuovoSpeseProcessuali";
		}
		String descUtente = (recuperaIdCodiceUtente().toString());
		amministrazioneService.inserisciSpeseProcessuali(speseProcessualiForm.getImportoSpese(), speseProcessualiForm.getDataInizioValidita() , speseProcessualiForm.getDescImportoSpese() , descUtente);
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", " ", true);
		modelMap.addAttribute("speseProcessualiForm", new SpeseProcessualiForm());
		return ricercaSpeseProcessuali(new SpeseProcessualiForm(), result, request, modelMap);
	}
	
	
	@RequestMapping(value = "/funzioniAmministrazione/nuovoSpeseProcessuali.htm" , method = RequestMethod.POST, params = { "Indietro" })
	public String indietroSpeseProcessuali(SpeseProcessualiForm speseProcessualiForm, BindingResult result,  HttpServletRequest request, ModelMap modelMap) {
		modelMap.addAttribute("speseProcessualiForm", new SpeseProcessualiForm());
		return ricercaSpeseProcessuali(new SpeseProcessualiForm(), result,  request, modelMap);
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/modificaSpeseProcessuali.htm" , method = RequestMethod.POST, params = { "Indietro" })
	public String indietroSpeseProcessualiModifica(SpeseProcessualiForm speseProcessualiForm, BindingResult result,  HttpServletRequest request, ModelMap modelMap) {
		modelMap.addAttribute("speseProcessualiForm", new SpeseProcessualiForm());
		return ricercaSpeseProcessuali(new SpeseProcessualiForm(), result,  request, modelMap);
	}
	
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaSpeseProcessuali.htm" , method = RequestMethod.POST, params = { "ModificaImportoSpese" })
	public String modificaImportoSpese(SpeseProcessualiForm speseProcessualiForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {
		if(speseProcessualiForm.getCodiKIdSpeseProcessuali() == 0)
		{
			super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
			return ricercaSpeseProcessuali(speseProcessualiForm, result,  request, modelMap);
		}
		modelMap.addAttribute("importoSpeseSearched", speseProcessualiForm.getImportoSpese());
		speseProcessualiForm = amministrazioneService.getSpeseProcessuali(speseProcessualiForm.getCodiKIdSpeseProcessuali());
		request.getSession().setAttribute("spesaProcessuale", speseProcessualiForm);
		modelMap.addAttribute("speseProcessualiForm", speseProcessualiForm);
		return "/amministrazione/modificaSpeseProcessuali";
	}
	
	
	
	@RequestMapping(value = "/funzioniAmministrazione/modificaSpeseProcessuali.htm" , method = RequestMethod.POST, params = { "Salva" })
	public String salvaModificaSpeseProcessuali(SpeseProcessualiForm speseProcessualiForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {
		String descUtente = (recuperaIdCodiceUtente().toString());
		SpeseProcessualiForm spesa = (SpeseProcessualiForm) request.getSession().getAttribute("spesaProcessuale");
		speseProcessualiValidation.validateModifica(speseProcessualiForm.getDataFineValidita(), spesa, result);
		if (result.hasErrors()) {
			//modelMap.addAttribute("speseProcessualiForm", spesa);
			speseProcessualiForm.setImportoSpese(spesa.getImportoSpese());
			speseProcessualiForm.setDataInizioValidita(spesa.getDataInizioValidita());
			return "/amministrazione/modificaSpeseProcessuali";
		}
		amministrazioneService.modificaSpeseProcessuali(speseProcessualiForm.getCodiKIdSpeseProcessuali(), spesa.getImportoSpese(), spesa.getDataInizioValidita(), speseProcessualiForm.getDataFineValidita() , speseProcessualiForm.getDescImportoSpese() , descUtente);
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", " ", true);
		modelMap.addAttribute("speseProcessualiForm", new SpeseProcessualiForm());
		return ricercaSpeseProcessuali(speseProcessualiForm, result, request,  modelMap);
		
		
	}

	/******* END Inserimento spese processuali in base dati *********/
}
