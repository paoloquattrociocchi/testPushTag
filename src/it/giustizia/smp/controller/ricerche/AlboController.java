package it.giustizia.smp.controller.ricerche;

import it.giustizia.smp.controller.BreadcrumbsController;
import it.giustizia.smp.form.AmministratoreAlboForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.service.CommonService;
import it.giustizia.smp.service.webservices.AlboService;
import it.giustizia.smp.utility.Constants;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * AlboController
 * 
 * @author Almaviva S.p.A.
 *         Classe Controller per la gestione delle ricerche
 *         sull'applicativo Albo degli Amministratori
 */
@Controller
public class AlboController extends BreadcrumbsController {

	
	@Autowired
	private AlboService				alboService;
	
	@Autowired 
	private CommonService			commonService;
	

	
	@RequestMapping(value = "/ricerche/albo/ricerca.htm", method = RequestMethod.GET)
	public ModelAndView ricercaAlbo(AmministratoreAlboForm amministratoreForm, BindingResult result, ModelMap modelMap, HttpServletRequest request) {
		
		cleanBreadcrumb(request.getSession());
		
		initFormRicercaAmministratore(amministratoreForm,modelMap);			
		return new ModelAndView("/ricerche/ricercaAlbo", "amministratoreForm", amministratoreForm);
	}	

	

	@RequestMapping(value = "/ricerche/albo/risultatiRicerca.htm", method = RequestMethod.POST)
	 public ModelAndView impostaFormRicerca(AmministratoreAlboForm amministratoreForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {

	    return ricercaAlbo(amministratoreForm,result,modelMap,request);
	}		
	
    /**	
     * initFormRicercaAmministratore
     * 
     * metodo private per caricamento combo form di ricerca albo amministratori
     * 
     * @param amministratoreForm
     * @param modelMap
     */
	private void initFormRicercaAmministratore(AmministratoreAlboForm amministratoreForm, ModelMap modelMap) {

		List<ComboForm> tipoOrdineList = commonService.getTipoOrdineAmministratori();
		modelMap.addAttribute("tipoOrdineList", tipoOrdineList);
		
	    if( amministratoreForm.getAlboProfessionale() != null && amministratoreForm.getAlboProfessionale().getId() != null ){
	        modelMap.addAttribute("sediOrdini", commonService.getSediOrdineAmministratori(amministratoreForm.getAlboProfessionale().getId())) ;
	    }else{
	       modelMap.addAttribute("sediOrdini", null) ;
	    }
	    
	    modelMap.addAttribute("provinceDomicilio", commonService.getAllProvincia());
	    
	    if( amministratoreForm.getProvinciaDomProf() != null 
	            && amministratoreForm.getProvinciaDomProf().getId() != null 
	            &&! amministratoreForm.getProvinciaDomProf().getId().equals(Constants.NO_ITEM_SELECTED) )
	        modelMap.addAttribute("comuniDomicilio", commonService.getAllComuneByProvincia(amministratoreForm.getProvinciaDomProf().getId()));
	    else
	        modelMap.addAttribute("comuniDomicilio", null); 
	            
	}	
	
	/**
	 * Esegue la ricerca degli amministratori dell'albo con i criteri valorizzati
	 * @param amministratoreForm
	 * @param result
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ricerche/albo/risultatiRicerca.htm", method = RequestMethod.POST, params = { "RICERCA" })
	public ModelAndView eseguiRicercaAlbo(@Valid @ModelAttribute("amministratore") AmministratoreAlboForm amministratoreForm, BindingResult result, HttpServletRequest request) {
		//@RequestMapping(value = "/ricerche/albo/ricerca.htm", method = RequestMethod.GET)
		addBreadcrumbs("GET","/ricerche/albo/ricerca.htm",null);
		
		List<AmministratoreAlboForm> listaAmministratori = alboService.elenco(amministratoreForm);
		ModelAndView mnv = new ModelAndView("/ricerche/elencoAmministratori", "amministratoreForm", amministratoreForm);
		mnv.addObject("risultatiRicerca", listaAmministratori);
		return mnv;
	}
	

}
