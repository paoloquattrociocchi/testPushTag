package it.giustizia.smp.controller.procedimento;

import it.giustizia.smp.controller.BreadcrumbsController;
import it.giustizia.smp.dao.BeneDao;
import it.giustizia.smp.form.AmministratoreForm;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.BeneForm;
import it.giustizia.smp.form.CoadiutoreForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.service.BeneService;
import it.giustizia.smp.service.CommonService;
import it.giustizia.smp.service.ProcedimentoService;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.validation.AmministratoreValidation;
import it.giustizia.smp.validation.BeneValidation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
 * AmministratoreController
 * 
 * @author Almaviva S.p.A.
 *         Classe Controller per la gestione degli amministratori 
 */
@Controller
public class AmministratoreController extends BreadcrumbsController {
	@Autowired
	private BeneService					beneService;
	@Autowired
	private AmministratoreValidation	amministratoreValidation;
	@Autowired 
	private CommonService				commonService;
	@Autowired
	private BeneValidation		    	beneValidation;
	@Autowired
	private BeneDao 					beneDao;
	@Autowired 
	private ProcedimentoService			procedimentoService;	
	
	
	/// J.C. SITMP 271 - ANOMALIA PRE-MEV: Errore visualizzazione msg ///	
	public HttpSession httpGetVarSession ( HttpServletRequest request ){ 
		return request.getSession(); 
	}

	public boolean httpVarSessionController ( HttpServletRequest request , String nameVarSession){ 
		if( httpGetVarSession( request ).getAttribute( nameVarSession ) == "VISITED" )  return true; return false;
	}

	public void httpVarSessionRemove ( HttpServletRequest request , String nameVarSession){ 
		httpGetVarSession( request ).removeAttribute( nameVarSession ); 
	}

	//Controlla se la variabile session è stata valorizzata nella JSP antecedente.
	//Se la variabile è stata valorizzata significa che la pagina JSP è stata visitata.
	//Quindi non visualizzo il messaggio di errore ed elimino la variabile in sessione.
	public void setErrorMessageOrRemoveVarSesssion( HttpServletRequest request , String nameVarSession ){ 
		if ( httpVarSessionController( request, nameVarSession ) == true ) 
			httpVarSessionRemove( request, nameVarSession );	
		else 
			super.setMessage(Constants.MESSAGE,"table.item.selection",null,false); 
	}
	
	public void setErrorMessageOrRemoveVarSesssion( HttpServletRequest request , String nameVarSession , String nameVarSession2){ 
		
		if ( httpVarSessionController( request, nameVarSession ) == true ) 
			httpVarSessionRemove( request, nameVarSession );		 
		else if ( httpVarSessionController( request, nameVarSession2 ) == true )
			httpVarSessionRemove( request, nameVarSession2 );
		else
			super.setMessage(Constants.MESSAGE,"table.item.selection",null,false);
			
	}
	/// FINE - SITMP-271 - ANOMALIA PRE-MEV: Errore visualizzazione msg ///
	
	
	//Link da MENU dell'Atto
	@RequestMapping(value = "/procedimento/amministratore/elencoAssociatoAtto.htm", method = RequestMethod.GET)
	public ModelAndView amministratoreElencoAssociatoAtto(HttpServletRequest request) {
	
		AmministratoreForm amministratoreForm = new AmministratoreForm();
		amministratoreForm.setJspProvenienza("ATTOELENCO");
		ModelAndView mnv = new ModelAndView("/procedimento/bene/amministratore/amministratoriElencoAtto", "amministratoreForm", amministratoreForm);
		Integer idUfficioUtente = recuperaIdUfficioUtente();
		Long idProcedimento = recuperaIdProcedimento();
		
		Long idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
		
		List<AmministratoreForm> amministratoriAssociati = beneService.getAmministratori(idUfficioUtente, idProcedimento, idAtto, null, false);
		
		mnv.addObject("amministratoriAssociati", amministratoriAssociati);
		mnv.addObject("statoAtto", recuperaAtto().getStatoAtto().getId());
		
		addBreadcrumbs("GET","/procedimento/amministratore/elencoAssociatoAtto.htm",null);
		
		return mnv;
	}
	
	//Link da MENU del Procedimento
	@RequestMapping(value = "/procedimento/amministratore/elencoAssociatoProcedimento.htm", method = RequestMethod.GET)
	public ModelAndView amministratoreElencoAssociatoProcedimento(HttpServletRequest request) {
	
		if (request.getSession().getAttribute(Constants.ATTO) != null) {
			request.getSession().removeAttribute(Constants.ATTO);
			
			//request.getSession().setAttribute(Constants.ATTO, new AttoForm());
		}
		AmministratoreForm amministratoreForm = new AmministratoreForm();
		amministratoreForm.setJspProvenienza("PROCEDIMENTO");
		ModelAndView mnv = new ModelAndView("/procedimento/bene/amministratore/amministratoriElencoProc", "amministratoreForm", amministratoreForm);
		Integer idUfficioUtente = recuperaIdUfficioUtente();
		Long idProcedimento = recuperaIdProcedimento();
		
		Integer idUfficioUtente2 = (Integer) request.getSession().getAttribute("idUffOwner");
		
		List<AmministratoreForm> amministratoriAssociati = beneService.getAmministratori(idUfficioUtente, idProcedimento, null, null, false);
		
		mnv.addObject("amministratoriAssociati", amministratoriAssociati);
		
		addBreadcrumbs("GET","/procedimento/amministratore/elencoAssociatoProcedimento.htm",null);
		
		return mnv;
	}
	
	@RequestMapping(value = "/procedimento/amministratore/ricercaAssocia.htm", method = RequestMethod.GET)
	public ModelAndView amministratoreRicerca(AmministratoreForm amministratoreForm, BindingResult result, ModelMap modelMap) {
		
		addBreadcrumbs("GET","/procedimento/amministratore/ricercaAssocia.htm",null);
		
		initFormRicercaAmministratore(amministratoreForm,modelMap);			
		return new ModelAndView("/procedimento/bene/amministratore/amministratoreRicercaAssocia", "amministratoreForm", amministratoreForm);
	}	
	
	private void initFormRicercaAmministratore(AmministratoreForm amministratoreForm, ModelMap modelMap) {

		List<ComboForm> tipoOrdineList = commonService.getTipoOrdineAmministratori();
		modelMap.addAttribute("tipoOrdineList", tipoOrdineList);
		
	    if( amministratoreForm.getAlboProfessionale() != null && amministratoreForm.getAlboProfessionale().getId() != null ){
	        modelMap.addAttribute("sediOrdini", commonService.getSediOrdineAmministratori(amministratoreForm.getAlboProfessionale().getId())) ;
	    }else{
	        modelMap.addAttribute("sediOrdini", null);
	    }
	    
	    modelMap.addAttribute("provinceDomicilio", commonService.getAllProvincia());
	    
	    if( amministratoreForm.getProvinciaDomProf() != null 
	            && amministratoreForm.getProvinciaDomProf().getId() != null 
	            &&! amministratoreForm.getProvinciaDomProf().getId().equals(Constants.NO_ITEM_SELECTED) )
	        modelMap.addAttribute("comuniDomicilio", commonService.getAllComuneByProvincia(amministratoreForm.getProvinciaDomProf().getId()));
	    else
	        modelMap.addAttribute("comuniDomicilio", null); 
	            
	}	
	
	@RequestMapping(value = "/procedimento/amministratore/ricercaAssocia.htm", method = RequestMethod.POST)
	 public ModelAndView impostaFormRicerca(AmministratoreForm amministratoreForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {

	    return amministratoreRicerca(amministratoreForm,result,modelMap);
	}	
		
	@RequestMapping(value = "/procedimento/amministratore/ricercaAssocia.htm", method = RequestMethod.POST, params = { "Ricerca" })
	public ModelAndView eseguiRicerca(AmministratoreForm amministratoreForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {
		
		ProcedimentoForm proced = recuperaProcedimento();
		int idUffOwn = recuperaIdUfficioUtente();
		Long idAtto = recuperaAtto().getCodAtto();
		
		List<AmministratoreForm> amministratoriDaAssociare = beneService.getAmministratoriDaAssociare(idUffOwn, proced.getIdProcedimento(), idAtto, amministratoreForm);
		
		//J.C. SITMP 296 - J.C. SITMP 297: Rimuovere l'agenzia di beni confiscati dalla lista amministratori se si tratta di un sequestro di primo grado e di secondo grado
		AttoForm attoForm = (AttoForm) (request.getSession().getAttribute(Constants.ATTO));			
		if (attoForm.getCategoriaAtto().getId().equals(Constants.DECRETO_SEQUESTRO_URGENTE) ||
				attoForm.getCategoriaAtto().getId().equals(Constants.DECRETO_CONVALIDA_SEQUESTRO_URGENTE) ||
				attoForm.getCategoriaAtto().getId().equals(Constants.DECRETO_SEQUESTRO_ANTICIPATO) ||
				attoForm.getCategoriaAtto().getId().equals(Constants.DECRETO_CONVALIDA_SEQUESTRO_ANTICIPATO) ||
				attoForm.getCategoriaAtto().getId().equals(Constants.DECRETO_SEQUESTRO_ORDINARIO)){
			
			AmministratoreForm agenziaBeniConfiscati = new AmministratoreForm();
			for (AmministratoreForm elementList : amministratoriDaAssociare) {
				String idString = Long.toString(elementList.getId());
			    if(idString.equals(Constants.AGENZIA_BENI_CONFISCATI)){
			    	agenziaBeniConfiscati=elementList; 	
			    }	    
			}
			amministratoriDaAssociare.remove(agenziaBeniConfiscati);
		}
		
		ModelAndView mnv = new ModelAndView("/procedimento/bene/amministratore/amministratoreRicercaAssocia", "amministratoreForm", amministratoreForm);
		mnv.addObject("amministratoriDaAssociare", amministratoriDaAssociare);
		
		initFormRicercaAmministratore(amministratoreForm,modelMap);	
		amministratoreForm.setJspProvenienza("ATTO");
		
		addBreadcrumbs("POST","/procedimento/amministratore/ricercaAssocia.htm",null);
		return mnv;
	}
	
	@RequestMapping(value = "/procedimento/amministratore/ricercaAssocia.htm", method = RequestMethod.POST, params = { "Nuovo" })
	public String nuovoAmministratore(AmministratoreForm amministratoreForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {
	
		addBreadcrumbs("GET","/procedimento/amministratore/elencoAssociatoAtto.htm",null);
		
		amministratoreForm = new AmministratoreForm();
		
		modelMap.addAttribute("amministratoreForm", amministratoreForm);
		
		return "/procedimento/bene/amministratore/amministratoreAnagrafica";
	}	
	
	//Modifica dati anagrafici	
	@RequestMapping(value = {"/procedimento/amministratore/elencoAssociatoProcedimento.htm","/procedimento/amministratore/elencoAssociatoAtto.htm","/procedimento/amministratore/ricercaAssocia.htm"}, method = RequestMethod.POST, params = { "Modifica" })
	public ModelAndView modificaAmministratore(AmministratoreForm amministratoreForm, BindingResult result, HttpServletRequest request, ModelMap modelMap, boolean isInsert) {
	
		if (amministratoreForm.getId()== null){
			setErrorMessageOrRemoveVarSesssion( request , "amministratoreAnagraficaJspVisited" ); //J.C. SITMP 271 - Variabile di sessione valorizzata nella JSP: amministratoreAnagrafica.jsp;
			if(amministratoreForm.getJspProvenienza().equals("ATTO")){
				return eseguiRicerca(amministratoreForm,result,request,modelMap);	
			}else if(amministratoreForm.getJspProvenienza().equals("ATTOELENCO")){
				return amministratoreElencoAssociatoAtto(request);	
			}else{
				return amministratoreElencoAssociatoProcedimento(request);
			}
		}	
		
		if (request.getSession().getAttribute(Constants.SUCCESS_MESSAGE) != null) {
			modelMap.addAttribute(Constants.SUCCESS_MESSAGE, request.getSession().getAttribute(Constants.SUCCESS_MESSAGE));
			request.getSession().removeAttribute(Constants.SUCCESS_MESSAGE);
		}
		if (request.getSession().getAttribute(Constants.SUCCESS_MESSAGE_LIST) != null) {
			modelMap.addAttribute(Constants.SUCCESS_MESSAGE_LIST, request.getSession().getAttribute(Constants.SUCCESS_MESSAGE_LIST));
			request.getSession().removeAttribute(Constants.SUCCESS_MESSAGE_LIST);
		}
		
		long idAmministratore = amministratoreForm.getId();
		String jspProvenienza = amministratoreForm.getJspProvenienza();
		String dettaglio = amministratoreForm.getDettaglio();

		String jspP = amministratoreForm.getJspProvenienza();
		
		if(amministratoreForm.getJspProvenienza().equals("PROCEDIMENTO")){
			addBreadcrumbs("POST","/procedimento/amministratore/elencoAssociatoProcedimento.htm",null);
		}else {
			/// P.Q. - SITMP-272 - ANOMALIA PRE-MEV: Amministratori - Errore bottone indietro
			if(!isInsert) {
				addBreadcrumbs("POST","/procedimento/amministratore/ricercaAssocia.htm","Modifica");				
			}				
		}
		
		if (!result.hasErrors()) {
			if (amministratoreForm.getId() == null){
				setErrorMessageOrRemoveVarSesssion( request , "amministratoreAnagraficaJspVisited" ); //J.C. SITMP 271 - Variabile di sessione valorizzata nella JSP: amministratoreAnagrafica.jsp;
				if(amministratoreForm.getJspProvenienza().equals("ATTO")){
					return amministratoreElencoAssociatoAtto(request);	
				}else{
					return amministratoreElencoAssociatoProcedimento(request);
				}
			}	
		}
		
		List<AmministratoreForm> amministratoreSel = beneService.getAmministratoreById(amministratoreForm.getId());
		amministratoreForm = amministratoreSel.get(0);
		amministratoreForm.setId(idAmministratore);
		amministratoreForm.setJspProvenienza(jspProvenienza);
		amministratoreForm.setDettaglio(dettaglio);
		if(amministratoreForm.getComuneEsteroLegale() != null){
			amministratoreForm.setComuneDomProf(new ComboForm(null,amministratoreForm.getComuneEsteroLegale()));
		}
		
		amministratoreForm.setJspProvenienza(jspP);
		
		modelMap.addAttribute("amministratoreForm", amministratoreForm);
		
		return new ModelAndView("/procedimento/bene/amministratore/amministratoreAnagrafica", "amministratoreForm", amministratoreForm);
	}	
	
	@RequestMapping(value = "/procedimento/amministratore/gestioneAnagrafica.htm", method = RequestMethod.POST, params = { "Inserisci" })
	public ModelAndView inserisciAmministratore(AmministratoreForm amministratoreForm, BindingResult result, HttpServletRequest request, ModelMap modelMap, String nomeMetodo) {
		
		int idUffOwn = recuperaIdUfficioUtente();
		amministratoreForm.setId(null);
		
		//Lo stato non è Italia passo la descizione nel comune estero  
		if(Integer.parseInt(amministratoreForm.getStatoDomProf().getId()) > 1){
			amministratoreForm.setComuneEsteroLegale(amministratoreForm.getComuneDomProf().getDescrizione());
			amministratoreForm.setComuneDomProf(new ComboForm(null,null));
		}
		
		amministratoreValidation.validateDatiAmministratore(amministratoreForm, result);
		if (result.hasErrors()) {
			super.setMessage(Constants.MESSAGE, "errors.campi.controllo", null, false);
			return new ModelAndView("/procedimento/bene/amministratore/amministratoreAnagrafica", "amministratoreForm", amministratoreForm);
		}
		
		
		Long id = beneService.inserisciAmministratore(idUffOwn, amministratoreForm, (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE), nomeMetodo);
	
		List<AmministratoreForm> amministratoreSel = beneService.getAmministratoreById(id);
		amministratoreForm = amministratoreSel.get(0);
		amministratoreForm.setId(id);
		amministratoreForm.setJspProvenienza("ATTO");
		amministratoreForm.setDettaglio("MODIFICA");
		
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", " ", true);
		
		return modificaAmministratore(amministratoreForm,result,request,modelMap, true);
	}
	
	@RequestMapping(value = "/procedimento/amministratore/gestioneAnagrafica.htm", method = RequestMethod.POST, params = { "Salva" })
	public ModelAndView aggiornaAmministratore(AmministratoreForm amministratoreForm, BindingResult result, HttpServletRequest request, ModelMap modelMap, String nomeMetodo) {
		
		int idUffOwn = recuperaIdUfficioUtente();
	    long idAmministratore =  amministratoreForm.getId();
	    String jspProvenienza =  amministratoreForm.getJspProvenienza();
		
	  //Lo stato non è Italia passo la descizione nel comune estero  
  		if(Integer.parseInt(amministratoreForm.getStatoDomProf().getId()) > 1){
  			amministratoreForm.setComuneEsteroLegale(amministratoreForm.getComuneDomProf().getDescrizione());
  			amministratoreForm.setComuneDomProf(new ComboForm(null,null));
  		}
	    
	    amministratoreValidation.validateDatiAmministratore(amministratoreForm, result);
		
		if (result.hasErrors()) {
			super.setMessage(Constants.MESSAGE, "errors.campi.controllo", null, false);
			return new ModelAndView("/procedimento/bene/amministratore/amministratoreAnagrafica", "amministratoreForm", amministratoreForm);
		}
		
		beneDao.updateAmministratore(idUffOwn, amministratoreForm, (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE));
		
		List<AmministratoreForm> amministratoreSel = beneService.getAmministratoreById(idAmministratore);
		amministratoreForm = amministratoreSel.get(0);
		amministratoreForm.setId(idAmministratore);
		amministratoreForm.setJspProvenienza(jspProvenienza);
		
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", " ", true);
		
		return modificaAmministratore(amministratoreForm,result,request,modelMap, false);
	}
	
	@RequestMapping(value = {"/procedimento/amministratore/ricercaAssocia.htm", "/procedimento/amministratore/gestioneAnagrafica.htm"}, method = RequestMethod.POST, params = { "Associa" })
	public ModelAndView associaAmministratore(AmministratoreForm amministratoreForm, BindingResult result, HttpServletRequest request, ModelMap modelMap, String nomeMetodo) {
		
		if (!result.hasErrors()) {
			if (amministratoreForm.getId()== null){
				setErrorMessageOrRemoveVarSesssion( request , "associaAmministratoreBeniJspVisited" , "amministratoreAnagraficaJspVisited" ); //J.C. SITMP 271 - Variabile di sessione valorizzata nelle JSP: associaAmministratoreBeni.jsp; amministratoreAnagrafica.jsp
				return eseguiRicerca(amministratoreForm,result,request,modelMap);
			}	
		}
		
		
		addBreadcrumbs("GET","/procedimento/amministratore/elencoAssociatoAtto.htm",null);
		
		
		BeneForm beneForm = new BeneForm();
		beneForm.setIdAmministratore(amministratoreForm.getId());
		List<AmministratoreForm> amministratoreSel = beneService.getAmministratoreById(amministratoreForm.getId());
		AmministratoreForm ammSel = amministratoreSel.get(0);
		if(ammSel.getNome() != null)
		{
			beneForm.setNominativoAmministratore(ammSel.getCognome()+" "+ammSel.getNome());
		}else{
			beneForm.setNominativoAmministratore(ammSel.getCognome());
		}		
		return visualizzaRicercaDelBeneAtto(beneForm, result, request, modelMap);
	}
	
	@RequestMapping(value = "/procedimento/amministratore/elencoAssociatoAtto.htm", method = RequestMethod.POST, params = { "Associa" })
	public ModelAndView modificaAssociazioneBeniAmministratore(AmministratoreForm amministratoreForm, BindingResult result, HttpServletRequest request, ModelMap modelMap, String nomeMetodo) {
		
		if (amministratoreForm.getId()== null){
			setErrorMessageOrRemoveVarSesssion( request , "associaAmministratoreBeniJspVisited" ); //J.C. SITMP 271 - Variabile di sessione valorizzata nella JSP: associaAmministratoreBeni.jsp;
			return amministratoreElencoAssociatoAtto(request);
		}	
		
    	addBreadcrumbs("GET","/procedimento/amministratore/gestioneAnagrafica.htm",null);
		
		BeneForm beneForm = new BeneForm();
		beneForm.setIdAmministratore(amministratoreForm.getId());
		List<AmministratoreForm> amministratoreSel = beneService.getAmministratoreById(amministratoreForm.getId());
		AmministratoreForm ammSel = amministratoreSel.get(0);

		if(ammSel.getNome() != null)
		{
			beneForm.setNominativoAmministratore(ammSel.getCognome()+" "+ammSel.getNome());
		}else{
			beneForm.setNominativoAmministratore(ammSel.getCognome());
		}		
		return visualizzaRicercaDelBeneAtto(beneForm, result, request, modelMap);
	}
	
	@RequestMapping(value = {"/procedimento/amministratore/elencoAssociatoAtto.htm","/procedimento/amministratore/elencoAssociatoProcedimento.htm","/procedimento/amministratore/ricercaAssocia.htm"}, method = RequestMethod.POST, params = { "Dettaglio" })
	public ModelAndView dettaglioAmministratore(AmministratoreForm amministratoreForm, BindingResult result, HttpServletRequest request, ModelMap modelMap, String nomeMetodo) {
		
		if (amministratoreForm.getId()== null){
			setErrorMessageOrRemoveVarSesssion( request , "amministratoreAnagraficaJspVisited" ); //J.C. SITMP 271 - Variabile di sessione valorizzata nella JSP: amministratoreAnagrafica.jsp;
			if(amministratoreForm.getJspProvenienza().equals("ATTO")){
				return eseguiRicerca(amministratoreForm,result,request,modelMap);	
			}else if(amministratoreForm.getJspProvenienza().equals("ATTOELENCO")){
				return amministratoreElencoAssociatoAtto(request);	
			}else{
				return amministratoreElencoAssociatoProcedimento(request);
			}
		}	
		
		long idAmministratore = amministratoreForm.getId();
		String jspProvenienza = amministratoreForm.getJspProvenienza();
		
		List<AmministratoreForm> amministratoreSel = beneService.getAmministratoreById(amministratoreForm.getId());
		amministratoreForm = amministratoreSel.get(0);
		amministratoreForm.setId(idAmministratore);
		amministratoreForm.setJspProvenienza(jspProvenienza);
		if(amministratoreForm.getComuneEsteroLegale() != null){
			amministratoreForm.setComuneDomProf(new ComboForm(null,amministratoreForm.getComuneEsteroLegale()));
		}
		amministratoreForm.setDettaglio("DETTAGLIO");
		
		modelMap.addAttribute("amministratoreForm", amministratoreForm);
		
		if(amministratoreForm.getJspProvenienza().equals("ATTO")){
			addBreadcrumbs("GET","/procedimento/amministratore/elencoAssociatoAtto.htm",null);	
		}else{
			addBreadcrumbs("POST","/procedimento/amministratore/elencoAssociatoProcedimento.htm",null);
		}
		
		return new ModelAndView("/procedimento/bene/amministratore/amministratoreAnagrafica", "amministratoreForm", amministratoreForm);
	}
	
//Prospetta la pagina con le liste dei beni associati e dei beni da associare	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/procedimento/amministratore/associaBeni.htm", method = RequestMethod.GET)
	public ModelAndView visualizzaRicercaDelBeneAtto(BeneForm beneForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {
		
		if (beneForm.getIdAmministratore()== null){
			super.setMessage(Constants.MESSAGE,"table.item.selection",null,false);
			return amministratoreElencoAssociatoAtto(request);
		}	
		if (request.getSession().getAttribute(Constants.SUCCESS_MESSAGE) != null) {
			modelMap.addAttribute(Constants.SUCCESS_MESSAGE, request.getSession().getAttribute(Constants.SUCCESS_MESSAGE));
			request.getSession().removeAttribute(Constants.SUCCESS_MESSAGE);
		}
		if (request.getSession().getAttribute(Constants.SUCCESS_MESSAGE_LIST) != null) {
			modelMap.addAttribute(Constants.SUCCESS_MESSAGE_LIST, request.getSession().getAttribute(Constants.SUCCESS_MESSAGE_LIST));
			request.getSession().removeAttribute(Constants.SUCCESS_MESSAGE_LIST);
		}

		if (!beneForm.isVisualizzaFiltro()) {
			beneForm.setIdBene(null);
			beneForm.setTipologia(null);
			beneForm.setCategoria(null);
			beneForm.setSottocategoria(null);
		}else{
			modelMap.addAttribute("categorie", getCategorie(beneForm, result));
			modelMap.addAttribute("sottocategorie", getSottocategorie(beneForm, result));
		}
		
		long idAmministratore = beneForm.getIdAmministratore();
		List<BeneForm> beniDaAssociare = new ArrayList<BeneForm>();
		List<BeneForm> beniAssociati = new ArrayList<BeneForm>();
		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
		AttoForm attoForm = (AttoForm) (request.getSession().getAttribute(Constants.ATTO));
		long vidDecreto = attoForm.getCodAtto();
		String idDecretoSequestroEProposta = null;
		long vidProposta = beneService.getIdAttoByUfficioEProcedimento(idUfficioUtente, idProcedimento);
		idDecretoSequestroEProposta = vidDecreto + ";" + vidProposta;
	
		
		
		if(attoForm!=null && attoForm.getCategoriaAtto()!=null && attoForm.getCategoriaAtto().getId().equals(Constants.PROVVEDIMENTO_NOMINA_AMMINISTRATORI)) { 
			beniAssociati = beneService.listaBeniNominaAmministratore(0, idUfficioUtente, idProcedimento, new BeneForm(), vidDecreto, idAmministratore," > ");
			beniDaAssociare = beneService.listaBeniNominaAmministratore(0, idUfficioUtente, idProcedimento, beneForm, vidDecreto, idAmministratore, " = ");
		} else {
			beniAssociati = beneService.listaBeniProcDecretoFiltro(0, idUfficioUtente, idProcedimento, new BeneForm(), vidDecreto, vidProposta, idAmministratore," > ");
			beniDaAssociare = beneService.listaBeniProcDecretoFiltro(0, idUfficioUtente, idProcedimento, beneForm, vidDecreto, vidProposta, idAmministratore, " = ");
		}
		
		List<AmministratoreForm> amministratoreSel = beneService.getAmministratoreById(idAmministratore);
		AmministratoreForm ammSel = amministratoreSel.get(0);
		
		if(ammSel.getNome() != null)
		{
			beneForm.setNominativoAmministratore(ammSel.getCognome()+" "+ammSel.getNome());
		}else{
			beneForm.setNominativoAmministratore(ammSel.getCognome());
		}
		
		String statoAtto = recuperaAtto().getStatoAtto().getId();
		modelMap.addAttribute("statoAtto", statoAtto);
		
		modelMap.addAttribute("idDecretoSequestroEProposta", idDecretoSequestroEProposta);
		modelMap.addAttribute("risultatoRicerca", beniAssociati);
		modelMap.addAttribute("beni", beniDaAssociare);
		modelMap.addAttribute("beneForm", beneForm);
		
		return new ModelAndView("/procedimento/bene/amministratore/associaAmministratoreBeni", "beneForm", beneForm);
	}
	
//Associa i beni all'amministratore
	@SuppressWarnings("unused")
	@RequestMapping(value = "/procedimento/amministratore/associaBeni.htm", method = RequestMethod.POST, params = { "Associa" })
	private ModelAndView associaAmministratoreBeneAtto(BeneForm beneForm, BindingResult result, HttpServletRequest request, ModelMap modelMap, String nomeMetodo) {

		beneValidation.validateSceltaListaBeni(beneForm.getListaIdCheckbox(), result, request);
		if (result.hasErrors()) {
			return visualizzaRicercaDelBeneAtto(beneForm, result, request, modelMap);
		}
		int idUffOwn = recuperaIdUfficioUtente();
		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		String codiceUtente = recuperaIdCodiceUtente();
		long idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
		
		long idAmministratore = beneForm.getIdAmministratore();
		Iterator it = beneForm.getListaIdCheckbox().iterator();
		
		AttoForm attoForm = (AttoForm) (request.getSession().getAttribute(Constants.ATTO));
		attoForm.setIdProcedimento(idProcedimento);
		AmministratoreForm amministratoreForm = new AmministratoreForm();
		amministratoreForm.setId(idAmministratore);
		
	//Inserisce la relazione Atto/Amministratore (nella procedura SQL c'è il controllo: se c'è già non l'inserisce)	
		beneService.inserisciRelAmministratoreAtto(idUffOwn, amministratoreForm, attoForm, (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE), nomeMetodo);
	//per ogni bene inserisce la relazione REL_PROC_BENE_ATTO_AMM
		while (it.hasNext()) {
			long idBene = (Long) it.next();
			beneService.inserisciRelAmministratoreBeneAtto(idUffOwn, idAmministratore, attoForm, idBene, (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE), nomeMetodo);
		}
		
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", " ", true);
		
		return visualizzaRicercaDelBeneAtto(beneForm, result, request, modelMap);
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/procedimento/amministratore/dissociaBeni.htm", method = RequestMethod.POST, params = { "Elimina" })
	private ModelAndView dissociaAmministratoreBeneAtto(BeneForm beneForm, BindingResult result, HttpServletRequest request, ModelMap modelMap, String nomeMetodo) {

		beneValidation.validateSceltaListaBeni(beneForm.getListaIdCheckbox(), result, request);
		if (result.hasErrors()) {
			return visualizzaRicercaDelBeneAtto(beneForm, result, request, modelMap);
		}
		int idUffOwn = recuperaIdUfficioUtente();
		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		String codiceUtente = recuperaIdCodiceUtente();
		
		long idAmministratore = beneForm.getIdAmministratore();
		
		AttoForm attoForm = (AttoForm) (request.getSession().getAttribute(Constants.ATTO));
		attoForm.setIdProcedimento(idProcedimento);
		AmministratoreForm amministratoreForm = new AmministratoreForm();
		amministratoreForm.setId(idAmministratore);
		Iterator it = beneForm.getListaIdCheckbox().iterator();
		
	//per ogni bene viene eliminata la relazione REL_PROC_BENE_ATTO_AMM
		while (it.hasNext()) {
			long idBene = (Long) it.next();
			beneService.eliminaRelAmministratoreBeneAtto(idUffOwn, idAmministratore, attoForm, idBene);
		}	
	
		return visualizzaRicercaDelBeneAtto(beneForm, result, request, modelMap);
	}
		
	@SuppressWarnings("unused")
	@RequestMapping(value = "/procedimento/amministratore/elencoAssociatoAtto.htm", method = RequestMethod.POST, params = { "Elimina" })
	private ModelAndView dissociaAmministratoreAtto(AmministratoreForm amministratoreForm, BindingResult result, HttpServletRequest request, ModelMap modelMap, String nomeMetodo) {

		if (amministratoreForm.getId()== null){
			super.setMessage(Constants.MESSAGE,"table.item.selection",null,false);
			return amministratoreElencoAssociatoAtto(request);
		}	
		
		addBreadcrumbs("GET","/procedimento/amministratore/elencoAssociatoAtto.htm",null);
		int idUffOwn = recuperaIdUfficioUtente();
		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		String codiceUtente = recuperaIdCodiceUtente();
		AttoForm attoForm = (AttoForm) (request.getSession().getAttribute(Constants.ATTO));
		attoForm.setIdProcedimento(idProcedimento);
		
	//prima di cancellare controlla che non ci siano beni associati all'Atto/Amministratore  
		boolean esistonoBeniAssociati = beneDao.esisteRelAmministratoreAttoBeni(idUffOwn, idProcedimento, attoForm.getCodAtto(), amministratoreForm.getId());
		if (esistonoBeniAssociati) {
			 super.setMessage(Constants.MESSAGE, "amministratore.beni.cancella.error", null, false);
		}else{
			 beneService.eliminaRelAmministratoreAtto(idUffOwn, amministratoreForm.getId(), attoForm);
		 //elimina anche dall'anagrafica T_AMMINIATRATORI se l'amministratore non è associato ad altri atti
			 boolean esistonoAltriAttiAssociati = beneDao.esisteRelAmministratoreAtti(amministratoreForm.getId());
			 //escludo l'agenzia dagli amministratori dei beni che verrebbero eliminati in assenza di associazioni
			 if (!esistonoAltriAttiAssociati && amministratoreForm.getId().toString() != Constants.AGENZIA_BENI_CONFISCATI) {
				 beneDao.eliminaTAmministratori(amministratoreForm.getId());
		 	}
		}
		
		return amministratoreElencoAssociatoAtto(request);
	}
	
	@RequestMapping(value = {"/procedimento/amministratore/elencoAssociatoProcedimento.htm","/procedimento/amministratore/elencoAssociatoAtto.htm","/procedimento/amministratore/gestioneAnagrafica.htm"}, method = RequestMethod.POST, params = { "BeniInAmministrazione" })
	public ModelAndView beniInAmministrazione(AmministratoreForm amministratoreForm, HttpServletRequest request, ModelMap modelMap) {

		if (amministratoreForm.getId()== null){
			super.setMessage(Constants.MESSAGE,"table.item.selection",null,false);
			if(amministratoreForm.getJspProvenienza().equals("ATTOELENCO")){
				return amministratoreElencoAssociatoAtto(request);	
			}else{
				return amministratoreElencoAssociatoProcedimento(request);
			}
		}
		
		long idAmministratore = amministratoreForm.getId();
		List<BeneForm> beniInAmministrazione = new ArrayList<BeneForm>();
		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
		
		BeneForm beneForm = new BeneForm();
		
		Long idAtto = request.getSession().getAttribute(Constants.ATTO) != null? ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto(): null;
		
		// P.Q. MEV 2020/48 Inizio
		// Se ATTO = null sono in amministratori della copertina procedimento (devo togliere eventuali coppie amministratore/bene duplicate)
		// Il recordset e' ordinato in base alla descrizione del bene!
		beniInAmministrazione = beneService.listaBeniInAmministrazione(idUfficioUtente, idProcedimento, idAtto, idAmministratore);
		if (beniInAmministrazione != null) {
			if(idAtto == null) {
				boolean scrivoilRecord = false;
				
				List<BeneForm> beniInAmministrazioneDistinct = new ArrayList<BeneForm>();
				
				for(BeneForm bf : beniInAmministrazione) {
					scrivoilRecord = true;
					for(BeneForm bfd : beniInAmministrazioneDistinct) {
						if (bfd.getIdBene().longValue() == bf.getIdBene().longValue()) { // il bene e' gia' presente nella lista di appoggio.
							scrivoilRecord = false;
							break;
						}
					}
					
					if (scrivoilRecord) beniInAmministrazioneDistinct.add(bf);
				}
				
				beniInAmministrazione.clear();
				beniInAmministrazione.addAll(beniInAmministrazioneDistinct);
			}			
		}
		// P.Q. MEV 2020/48 Fine
		
		List<AmministratoreForm> amministratoreSel = beneService.getAmministratoreById(amministratoreForm.getId());
		AmministratoreForm ammSel = amministratoreSel.get(0);
		if(ammSel.getNome() != null){
		  beneForm.setNominativoAmministratore(ammSel.getCognome()+" "+ammSel.getNome());
		}else{
			beneForm.setNominativoAmministratore(ammSel.getCognome());
		}
		
		
		modelMap.addAttribute("risultatiRicerca", beniInAmministrazione);
				
		//addBreadcrumbs("POST","/procedimento/amministratore/elencoAssociatoProcedimento.htm",null);	
		addBreadcrumbs("BeniInAmministrazione",request);
		
		beneForm.setIdAmministratore(amministratoreForm.getId());
		//Segnalazione CFTRI04
		if(beniInAmministrazione.size() > 10)
			request.getSession().setAttribute("idAmministratore", amministratoreForm.getId());
		
		return new ModelAndView("/procedimento/bene/amministratore/beniInAmministrazione", "beneForm", beneForm);
	}			
	
	@RequestMapping(value = "/procedimento/amministratore/associaBeni.htm", method = RequestMethod.POST)
	public ModelAndView postAssociaAmministratoreBeni(BeneForm beneForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {

		if (!beneForm.isVisualizzaFiltro()) {
			beneForm.setIdBene(null);
			beneForm.setTipologia(null);
			beneForm.setCategoria(null);
			beneForm.setSottocategoria(null);
		}
		
		return visualizzaRicercaDelBeneAtto(beneForm, result, request, modelMap);
	}
	
	@RequestMapping(value = "/procedimento/amministratore/paginazioneRicercaAssocia.htm", method = RequestMethod.GET)
	public ModelAndView paginazioneRicercaAssocia(AmministratoreForm amministratoreForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {
	
		return eseguiRicerca(amministratoreForm, result, request, modelMap);
	}
	
	@RequestMapping(value = "/procedimento/amministratore/paginazioneElencoAssociatoAtto.htm", method = RequestMethod.GET)
	public ModelAndView paginazioneElencoAssociatoAtto(AmministratoreForm amministratoreForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {
	
		return amministratoreElencoAssociatoAtto(request);
	}
	
	@RequestMapping(value = "/procedimento/amministratore/paginazioneElencoAssociatoProcedimento.htm", method = RequestMethod.GET)
	public ModelAndView paginazioneElencoAssociatoProcedimento(AmministratoreForm amministratoreForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {
	
		return amministratoreElencoAssociatoProcedimento(request);
	}
	
	@RequestMapping(value = "/procedimento/bene/amministratore/beniInAmministrazione.htm", method = RequestMethod.GET)
	public ModelAndView paginazioneBeniInAmministrazione(BeneForm beneForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {
		
		AmministratoreForm amministratoreForm = new AmministratoreForm();
		//Segnalazione CFTRI04
		if(request.getSession().getAttribute("idAmministratore") != null)
		{
			amministratoreForm.setId(new Long(request.getSession().getAttribute("idAmministratore").toString()));
			request.getSession().removeAttribute("idAmministratore");
		}
//		amministratoreForm.setId(beneForm.getIdAmministratore());
		
		return beniInAmministrazione(amministratoreForm, request, modelMap);
	}
	
	@RequestMapping(value = "/procedimento/amministratore/dissociaBeni.htm", method = RequestMethod.GET)
	public ModelAndView paginazioneAssociaBeni(BeneForm beneForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {
	
		return visualizzaRicercaDelBeneAtto(beneForm, result, request, modelMap);
	}

	@RequestMapping(value = "/procedimento/amministratore/elencoAssociatoProcedimento.htm", method = RequestMethod.POST, params = { "Coadiutori" })
	public String gestioneCoadiutori(AmministratoreForm amministratoreForm, CoadiutoreForm coadiutoreForm, BindingResult result, ModelMap modelMap, HttpServletRequest request) {
	
		Long idProcedimento = recuperaIdProcedimento();
		Integer idUfficioUtente = recuperaIdUfficioUtente();
		
  		if (amministratoreForm.getId()== null){
  			super.setMessage(Constants.MESSAGE,"table.item.selection",null,false);
  			List<AmministratoreForm> amministratoriAssociati = beneService.getAmministratori(idUfficioUtente, idProcedimento, null, null, false);
  			modelMap.addAttribute("amministratoriAssociati", amministratoriAssociati);
  			
  			return "/procedimento/bene/amministratore/amministratoriElencoProc";
  		}
  			
		Long idAmm = amministratoreForm.getId();
		if(idAmm==null) {
			idAmm = coadiutoreForm.getIdAmministratore();
		}
		coadiutoreForm.setIdAmministratore(idAmm);
		
		List<CoadiutoreForm> listaCoadiutori = procedimentoService.listaCoadiutori(idProcedimento, idUfficioUtente, idAmm);
		modelMap.addAttribute("listaCoadiutori", listaCoadiutori);
		modelMap.addAttribute("coadiutoreForm", coadiutoreForm);
		
		addBreadcrumbs("Coadiutori",request);
		
		return "/procedimento/bene/amministratore/gestioneCoadiutori";
	}
	
	@RequestMapping(value = "/procedimento/amministratore/elencoAssociatoProcedimento.htm", method = RequestMethod.POST, params = { "NuovoCoadiutore" })
	public String nuovoCoadiutore(@Valid CoadiutoreForm coadiutoreForm, BindingResult result, ModelMap modelMap, HttpServletRequest request) {
	
		Long idAmm = coadiutoreForm.getIdAmministratore();
		Long idProcedimento = recuperaIdProcedimento();
		Integer idUfficioUtente = recuperaIdUfficioUtente();
		coadiutoreForm.setIdProcedimento(idProcedimento);
		coadiutoreForm.setIdUfficio(idUfficioUtente);
		coadiutoreForm.setIdAmministratore(idAmm);
		coadiutoreForm.setIdProgrCoad(null);
		
		addBreadcrumbs("Nuovo",request);
		modelMap.addAttribute("coadiutoreForm", coadiutoreForm);
		
		return "/procedimento/bene/amministratore/dettaglioCoadiutore";
	}
	
	@RequestMapping(value = "/procedimento/amministratore/elencoAssociatoProcedimento.htm", method = RequestMethod.POST, params = { "ModificaCoadiutore" })
	public String modificaCoadiutore(@Valid CoadiutoreForm coadiutoreForm, BindingResult result, ModelMap modelMap, HttpServletRequest request) {
	
		if (!result.hasErrors()) {
			if (coadiutoreForm.getIdProgrCoad() == null) {
				super.setMessage(Constants.MESSAGE,"table.item.selection",null,false);
				AmministratoreForm amministratoreForm = new AmministratoreForm();
				amministratoreForm.setId(coadiutoreForm.getIdAmministratore());
				return gestioneCoadiutori(amministratoreForm, coadiutoreForm, result, modelMap, request);				
			}	
		}

		Long idProcedimento = recuperaIdProcedimento();
		Integer idUfficioUtente = recuperaIdUfficioUtente();
		
		coadiutoreForm = procedimentoService.dettaglioCoadiutore(idProcedimento, idUfficioUtente, coadiutoreForm.getIdAmministratore(), coadiutoreForm.getIdProgrCoad());
		
		addBreadcrumbs("Modifica",request);
		modelMap.addAttribute("coadiutoreForm", coadiutoreForm);
		
		return "/procedimento/bene/amministratore/dettaglioCoadiutore";
	}	
	
	@RequestMapping(value = "/procedimento/amministratore/elencoAssociatoProcedimento.htm", method = RequestMethod.POST, params = { "DettaglioCoadiutore" })
	public String dettaglioCoadiutore(CoadiutoreForm coadiutoreForm, BindingResult result, ModelMap modelMap, HttpServletRequest request) {
	
		if (!result.hasErrors()) {
			if (coadiutoreForm.getIdProgrCoad() == null) {
				super.setMessage(Constants.MESSAGE,"table.item.selection",null,false);
				AmministratoreForm amministratoreForm = new AmministratoreForm();
				amministratoreForm.setId(coadiutoreForm.getIdAmministratore());
				return gestioneCoadiutori(amministratoreForm, coadiutoreForm, result, modelMap, request);				
			}	
		}

		Long idProcedimento = recuperaIdProcedimento();
		Integer idUfficioUtente = recuperaIdUfficioUtente();
		
		coadiutoreForm = procedimentoService.dettaglioCoadiutore(idProcedimento, idUfficioUtente, coadiutoreForm.getIdAmministratore(), coadiutoreForm.getIdProgrCoad());
		
		addBreadcrumbs("Dettaglio",request);
		modelMap.addAttribute("coadiutoreForm", coadiutoreForm);
		modelMap.addAttribute("disabled", "1");
		
		return "/procedimento/bene/amministratore/dettaglioCoadiutore";
	}		

	@RequestMapping(value = "/procedimento/amministratore/elencoAssociatoProcedimento.htm", method = RequestMethod.POST, params = { "SalvaCoadiutore" })
	public String salvaCoadiutore(@Valid CoadiutoreForm coadiutoreForm, BindingResult result, ModelMap modelMap, HttpServletRequest request) {
	
		amministratoreValidation.validateDatiCoadiutore(coadiutoreForm, result);
		if (result.hasErrors()) {
			return "/procedimento/bene/amministratore/dettaglioCoadiutore";
		} else {
			Long idProcedimento = recuperaIdProcedimento();
			Integer idUfficioUtente = recuperaIdUfficioUtente();
	
			String descUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);
			coadiutoreForm.setDescUtente(descUtente);
	
			int idProgrCoad = procedimentoService.salvaCoadiutore(coadiutoreForm);
			coadiutoreForm.setIdProgrCoad(idProgrCoad);
		}			
		
		modelMap.addAttribute("coadiutoreForm", coadiutoreForm);
		
		return "/procedimento/bene/amministratore/dettaglioCoadiutore";
	}
	
	@RequestMapping(value = "/procedimento/amministratore/elencoAssociatoProcedimento.htm", method = RequestMethod.POST, params = { "EliminaCoadiutore" })
	public String eliminaCoadiutori(CoadiutoreForm coadiutoreForm, BindingResult result, ModelMap modelMap, HttpServletRequest request) {
	
		if (!result.hasErrors()) {
			if (coadiutoreForm.getIdProgrCoad() == null) {
				super.setMessage(Constants.MESSAGE,"table.item.selection",null,false);
				AmministratoreForm amministratoreForm = new AmministratoreForm();
				amministratoreForm.setId(coadiutoreForm.getIdAmministratore());
				return gestioneCoadiutori(amministratoreForm, coadiutoreForm, result, modelMap, request);				
			}	
		}
		
		Long idProcedimento = recuperaIdProcedimento();
		Integer idUfficioUtente = recuperaIdUfficioUtente();
		
		coadiutoreForm.setIdProcedimento(idProcedimento);
		coadiutoreForm.setIdUfficio(idUfficioUtente);
		procedimentoService.eliminaCoadiutore(coadiutoreForm);
				
		AmministratoreForm amministratoreForm = new AmministratoreForm();
		amministratoreForm.setId(coadiutoreForm.getIdAmministratore());
		return gestioneCoadiutori(amministratoreForm, coadiutoreForm, result, modelMap, request);
	}	
	
	@ModelAttribute("sottocategorie")
	public List<ComboForm> getSottocategorie(@Valid BeneForm beneForm, BindingResult result) {

		if ((beneForm == null) || (beneForm.getCategoria() == null) || (beneForm.getCategoria().getId() == null)) {
			return null;
		}
		return commonService.listaSottocategorieDelBene(beneForm);
	}	

	@ModelAttribute("categorie")
	public List<ComboForm> getCategorie(@Valid BeneForm beneForm, BindingResult result) {

		if ((beneForm == null) || (beneForm.getTipologia() == null) || (beneForm.getTipologia().getId() == null)) {
			return null;
		}
		return commonService.listaCategorieDelBene(beneForm);
	}
	
}