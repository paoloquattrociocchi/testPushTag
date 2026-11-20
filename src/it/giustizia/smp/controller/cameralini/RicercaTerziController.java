package it.giustizia.smp.controller.cameralini;

import it.giustizia.smp.controller.MenuController;
import it.giustizia.smp.controller.procedimento.ApriController;
import it.giustizia.smp.dao.StaticListLoader;
import it.giustizia.smp.dto.pg_cameralini.IntestatarioDto;
import it.giustizia.smp.dto.pg_cameralini.ProcedimentoDto;
import it.giustizia.smp.dto.pg_procedimento.AssociaPersonaDto;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.form.cameralini.TerzoIntervenienteForm;
import it.giustizia.smp.service.ProcedimentoService;
import it.giustizia.smp.service.webservices.AgenziaEntrateService;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.validation.SoggettoValidation;
import it.giustizia.smp.webservice.client.agenziaentrate.EsitoControlloCFePIWrapper;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class RicercaTerziController extends RicercaBaseController
{
	
	@Autowired
	private SoggettoValidation		soggettoValidation;
	
	@Autowired
	private ApriController 			apriController;	
    
    @Autowired
	private ProcedimentoService		procedimentoService;
    
	@Autowired
	private AgenziaEntrateService	agenziaEntrateService;

	@RequestMapping( value = "/procedimento/nuovo/terzi/ricerca.htm", method = RequestMethod.GET )
	public ModelAndView ricerca(Model model, HttpServletRequest request)
	{
		//return ricercaBase(model,"terzi",  Constants.TIPOPROC_TERZO_INTERVENIENTE_CAM); 
		MenuController.caricaMenu( Constants.ID_K_MENU_PROCEDIMENTO, request );
		TerzoIntervenienteForm form = new TerzoIntervenienteForm();
        form.setFunzioneCameralini("terzi");
        ModelAndView mnv = new ModelAndView( "/cameralini/ricerca", "ricerca", form );
        cleanBreadcrumb(request.getSession());
        addBreadcrumbs();
        return mnv;
	}
	
	@RequestMapping( value = "/procedimento/nuovo/terzi/paginazioneRicerca.htm", method = RequestMethod.GET )
    public ModelAndView paginazioneRicerca( TerzoIntervenienteForm form, BindingResult result, ModelMap modelMap, HttpServletRequest request )
	{
        if (form.getTipoSoggetto().equals("F"))
        	return ricercaSoggettoFisico( form, result, modelMap, request );
        else
        	return ricercaSoggettoGiuridico( form, result, modelMap, request );
	}
	
    // @RequestMapping( value = "/procedimento/nuovo/terzi/terzoInterveniente.htm", method = RequestMethod.POST, params = { "Ricerca" } )
	@RequestMapping( value = "/procedimento/nuovo/terzi/ricerca.htm", method = RequestMethod.POST, params = { "Ricerca" } )
    public ModelAndView istanzaDiTerzo( @Valid @ModelAttribute( "ricerca" ) TerzoIntervenienteForm form, BindingResult result, ModelMap modelMap, HttpServletRequest request )
    {
		/****************** MOCK ****************/
	//	int idTipoUfficio = (Integer) request.getSession().getAttribute("idTipoUfficio");
	//	if(idTipoUfficio == 6)
	//	{	form.getCognomeRichiedente().getBytes();
	//		form.getTrasmissione().getDescrizione();
	//	}
		/*************** END MOCK ***************/
		form.setFunzioneCameralini("terzi");
		ModelAndView mnv = new ModelAndView( "/cameralini/terzi/istanzaDiTerzo", "form", form );
		
		if(! result.hasErrors() ) {
			// INIZIO CONTROLLI VALIDATE CAMERALINI
			ProcedimentoForm procedimento = form.getProcedPadre();
			cameraliniValidation.validateCriteriRicercaPuntuale(procedimento, result );
			if( result.hasErrors() )
	        {
	           return new ModelAndView(  "/cameralini/ricerca", "ricerca", form );
	       
	        } else {
	        	Long anno = form.getProcedPadre().getAnno().longValue();
	    		Long numero = form.getProcedPadre().getNumero();
	    		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
	    		//form.setUfficioSelezionato(form.getUfficio().getDescrizione());
	            
	            ProcedimentoForm p = getProcedimentoPadre(idUfficioUtente, numero, anno, Constants.TIPOPROC_TERZO_INTERVENIENTE_CAM);
	            // ginesti se il risultato precedente  è null verifico solo se presente il procedimento
				// con chiave: numero + anno + codiceUfficio
				 ProcedimentoDto  solop = null;
				 if (p == null) { 
					  solop = procedimentoServiceCam.getProcedimentoByKeysProc(idUfficioUtente,numero,anno,Constants.TIPOPROC_MODIFICA_PATRIMONIALE_CAM) ; 
					
				 }
	            String error = checkMisuraPatrApplicata(p,idUfficioUtente,solop);
				if(error != null){
					super.setMessage( Constants.MESSAGE, error, null, true );
					return	 mnv = new ModelAndView( "/cameralini/ricerca", "ricerca", form );
				}
				
	    		
	    		form.setProcedPadre(p);
	    		
	    		// si caricano in sessione i dati del procedimento in ProcedimentoForm 
	    		request.getSession().setAttribute(Constants.ID_PROCEDIMENTO, form.getProcedPadre());
	    		
	    		
	        }
		} else {
			return mnv = new ModelAndView(  "/cameralini/ricerca", "ricerca", form );
		}

        cleanBreadcrumb(request.getSession());
        addBreadcrumbs();
        return mnv;
    }
	

	// @SuppressWarnings("null")
	@RequestMapping( value = "/procedimento/nuovo/terzi/ricerca.htm", method = RequestMethod.POST, params = { "SoggettoFisico" } )
	public ModelAndView soggettoFisico(TerzoIntervenienteForm form, BindingResult result, ModelMap modelMap, HttpServletRequest request )
	{
		form.setFunzioneCameralini("terzi");
		form.setTipoSoggetto("F");
		
		ModelAndView mnv = new ModelAndView( "/cameralini/terzi/ricercaSoggetto", "form", form );
		
		addBreadcrumbs("SoggettoFisico",request);
		return mnv;
	}
	
	
	// @SuppressWarnings("null")
	@RequestMapping( value = "/procedimento/nuovo/terzi/ricerca.htm", method = RequestMethod.POST, params = { "SoggettoGiuridico" } )
	public ModelAndView soggettoGiuridico(TerzoIntervenienteForm form, BindingResult result, ModelMap modelMap, HttpServletRequest request )
	{
		form.setFunzioneCameralini("terzi");
		form.setTipoSoggetto("G");
		
		ModelAndView mnv = new ModelAndView( "/cameralini/terzi/ricercaSoggetto", "form", form );
		
		cleanBreadcrumb(request.getSession());
		addBreadcrumbs();
		return mnv;
	}
	

	@RequestMapping(value = { "/procedimento/nuovo/terzi/ricerca.htm" }, method = RequestMethod.POST, params = { "RicercaSoggettoFisico" })
	public ModelAndView ricercaSoggettoFisico(@Valid @ModelAttribute("form") TerzoIntervenienteForm form, BindingResult result, ModelMap modelMap, HttpServletRequest request) {
		
		ModelAndView mnv = new ModelAndView("/cameralini/terzi/ricercaSoggetto", "form", form );
		form.setTipoSoggetto("F");
		SoggettoForm soggettoForm = caricaSoggForm(form);
		cameraliniValidation.validateRicercaSoggettoFisico(soggettoForm, result);
		
		if (result.hasErrors()) {
			return mnv;
		} 

		int idUffOwner = (Integer) request.getSession().getAttribute("idUffOwner");
		List<SoggettoForm> listaSoggettoForm = procedimentoService.listaSoggettiByCriteria(idUffOwner, soggettoForm.getNome(), soggettoForm.getCognome(), soggettoForm.getStatoNascita().getId().equals(String.valueOf(Constants.NO_ITEM_SELECTED)) ? null : soggettoForm.getStatoNascita().getId(), soggettoForm.getComuneNascita() == null ? null : soggettoForm.getComuneNascita().getId(), soggettoForm.getDescrizioneComuneEsteroNascita(), Utility.convertDateToString(soggettoForm.getDataNascita()), soggettoForm.getCodiceFiscale(), soggettoForm.getCui(), false, null);
		modelMap.addAttribute("risultatiRicerca", listaSoggettoForm);
		
		addBreadcrumbs("RicercaSoggettoFisico",request);
		
		return mnv = new ModelAndView( "/cameralini/terzi/ricercaSoggetto", "form", form);
	}
	

	@RequestMapping(value = { "/procedimento/nuovo/terzi/ricerca.htm" }, method = RequestMethod.POST, params = { "RicercaSoggettoGiuridico" })
	public ModelAndView ricercaSoggettoGiuridico(@Valid @ModelAttribute("form") TerzoIntervenienteForm form, BindingResult result, ModelMap modelMap, HttpServletRequest request) {
		
		ModelAndView mnv = new ModelAndView("/cameralini/terzi/ricercaSoggetto", "form", form );
		form.setTipoSoggetto("G");
		SoggettoForm soggettoForm = caricaSoggForm(form);
		cameraliniValidation.validateRicercaSoggettoGiuridico(soggettoForm, result);
		
		if (result.hasErrors()) {
			return mnv;
		} 	
		
		int idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
		String piva = StringUtils.isNotEmpty(soggettoForm.getPartitaIvaSiglaStato())?soggettoForm.getPartitaIvaSiglaStato():"";
		if(StringUtils.isNotEmpty(soggettoForm.getPartitaIva())){
			piva.concat(soggettoForm.getPartitaIva().trim());
		}
		List<SoggettoForm> listaSoggettoForm = procedimentoService.listaSoggettiGiurByCriteria(idUfficioUtente, soggettoForm.getRagioneSociale() != null ? soggettoForm.getRagioneSociale() : null, piva, soggettoForm.getStatoSedeLegale().getId().equals(String.valueOf(Constants.NO_ITEM_SELECTED)) ? null : Integer.parseInt(soggettoForm.getStatoSedeLegale().getId()), soggettoForm.getComuneSedeLegale().getId() == null ? null : Integer.parseInt(soggettoForm.getComuneSedeLegale().getId()), soggettoForm.getDescrizioneComuneEsteroSedeLegale(), false, null);
		modelMap.addAttribute("risultatiRicercaSoggettoGiuridico", listaSoggettoForm);
		
		addBreadcrumbs("RicercaSoggettoGiuridico",request);
		
		return mnv = new ModelAndView( "/cameralini/terzi/ricercaSoggetto", "form", form);
	}

	@RequestMapping( value = "/procedimento/nuovo/terzi/ricerca.htm", method = RequestMethod.POST, params = { "Registra" } )
	public ModelAndView registra(@Valid @ModelAttribute("form") TerzoIntervenienteForm form, BindingResult result, ModelMap modelMap, HttpServletRequest request) {
    	
    	Integer idUffOwn = (Integer) request.getSession().getAttribute("idUffOwner");
    	String codUtente = (String) request.getSession().getAttribute("codUtente");
    	
    	ProcedimentoForm procPadre = (ProcedimentoForm)request.getSession().getAttribute(Constants.ID_PROCEDIMENTO);
    	form.setProcedPadre(procPadre);
    	
    	List<AssociaPersonaDto> listaAssoc = null;
    	
    	Long idTipoProcedimento = Constants.TIPOPROC_TERZO_INTERVENIENTE_CAM;
    	Long idCatAtto = 1L;
    	
		if (!result.hasErrors()) {

			Long idTipoAtto = 5L;
			int idTipoProc = 18;
			
			List<SoggettoForm> listaSoggettiSel = new ArrayList<SoggettoForm>();
			if(form.getSoggettoForm()!=null && form.getSoggettoForm().getIdSoggetto()!=null) {
				listaSoggettiSel.add(form.getSoggettoForm());
			}
			
			String tipoSoggetto = form.getSoggettoForm().getTipoSoggetto();
			Long idVersione = form.getSoggettoForm().getIdVersione();
			
			ProcedimentoForm procForm =new ProcedimentoForm();
			if (listaSoggettiSel != null && !listaSoggettiSel.isEmpty()) {

				int idTipoFascicolo = 6;
				Long statoProcedimento = new Long(2);

				procForm = preparaNuovoProcedimento(form, idTipoProcedimento);
				
				listaAssoc = procedimentoServiceCam.inserisciProcedimentoCameraliniNew(idUffOwn.intValue(),
								codUtente, procForm, statoProcedimento.longValue(), listaSoggettiSel,
								idTipoFascicolo, idTipoAtto, idCatAtto, form.getCognomeRichiedente(), form.getNomeRichiedente(), 
								form.getDataSottoscrizione(), form.getDataDeposito(), new Long("0"), 
								form.getProcedPadre().getIdProcedimento(),idTipoProc, tipoSoggetto, idVersione);
				
			} else {
				super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
				result.reject(Constants.MESSAGE);
				return new ModelAndView("/cameralini/terzi/ricercaSoggetto", "form", form);
			}
			request.getSession().setAttribute("idProcPadre",  form.getProcedPadre().getIdProcedimento());
			request.getSession().setAttribute("idProcFiglio", listaAssoc.get(0).getIDPROC().longValue());
		
			
			procForm.setIdProcedimento(listaAssoc.get(0).getIDPROC().longValue());
			request.getSession().setAttribute(Constants.ID_PROCEDIMENTO, procForm);
			
			//richiamo la copertina 
			return apriController.copertinaProcedimento(procForm, result, request);
		
		} else {
			return new ModelAndView("/cameralini/terzi/ricercaSoggetto", "form", form);
		}
	}
	
	@RequestMapping(value = "/procedimento/nuovo/terzi/ricerca.htm", method = RequestMethod.POST, params =	{ "NuovoSoggettoFisico" })
	public ModelAndView nuovoSoggettoFisico(@ModelAttribute("form") TerzoIntervenienteForm form, BindingResult result, ModelMap modelMap, HttpServletRequest request)
	{
		SoggettoForm soggettoForm  = new SoggettoForm();
		soggettoForm.setCognome(form.getCognome());
		soggettoForm.setNome(form.getNome());
		soggettoForm.setDataNascita(form.getDataNascita());
		soggettoForm.setStatoNascita(form.getStatoNascita());
		soggettoForm.setComuneNascita(form.getComuneNascita());
		soggettoForm.setCodiceFiscale(form.getCodiceFiscale());
				
		form.setSoggettoForm(soggettoForm);
		ModelAndView mnv = new ModelAndView("/cameralini/terzi/nuovoSoggettoFisico", "form", form);
		addBreadcrumbs("NuovoSoggettoFisico",request);
		return mnv;
	}
	
	@RequestMapping(value = "/procedimento/nuovo/terzi/ricerca.htm", method = RequestMethod.POST, params =	{ "NuovoSoggettoGiuridico" })
	public ModelAndView nuovoSoggettoGiuridico(@ModelAttribute("form") TerzoIntervenienteForm form, BindingResult result, ModelMap modelMap, HttpServletRequest request)
	{
		SoggettoForm soggettoForm  = new SoggettoForm();
		soggettoForm.setRagioneSociale(form.getRagioneSociale());
		soggettoForm.setStatoSedeLegale(form.getStatoSedeLegale());
		soggettoForm.setComuneSedeLegale(form.getComuneSedeLegale());
		soggettoForm.setPartitaIva(form.getPartitaIva());
		
		form.setSoggettoForm(soggettoForm);
		ModelAndView mnv = new ModelAndView("/cameralini/terzi/nuovoSoggettoGiuridico", "form", form);
		addBreadcrumbs("NuovoSoggettoGiuridico",request);
		return mnv;
	}	

	@RequestMapping(value = "/procedimento/nuovo/terzi/ricerca.htm", method = RequestMethod.POST, params = { "verificaCF" })
	public String controllaCFSoggettoFisico(@Valid TerzoIntervenienteForm form, BindingResult result, ModelMap modelMap) {
		String msgErrore = "";
		
		if ( "".equals(form.getSoggettoForm().getNome()) || form.getSoggettoForm().getNome()==null || "".equals(form.getSoggettoForm().getCognome()) || form.getSoggettoForm().getCognome()==null ) {
			msgErrore = "errors.campi.controllo.codicefiscale.nomeocognome";
		} else {
			//Invoco il WS
			EsitoControlloCFePIWrapper  esito = agenziaEntrateService.controllaDatiAnagraficiPF(form.getSoggettoForm());
			if (esito.isInvocazioneFallita()) {
				super.setMessage(Constants.MESSAGE, "errors.campi.controllo.codicefiscale", esito.getErrore(), false);
			} else {
				if (form.getSoggettoForm().getCodiceFiscale()!=null && !form.getSoggettoForm().getCodiceFiscale().equals("")) {
					// Compara CF
					if (form.getSoggettoForm().getCodiceFiscale().equals(esito.getCodiceFiscale())) {
						super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.codicefiscale.corretto", " ", true);
					} else {
						super.setMessage(Constants.MESSAGE, "errors.campi.controllo.codicefiscale.diverso", esito.getCodiceFiscale(), false);
					}
				} else {
					form.getSoggettoForm().setCodiceFiscale(esito.getCodiceFiscale());	
				}
			}
		}
		
		if (!msgErrore.equals("")) {
			super.setMessage(Constants.MESSAGE, msgErrore, null, false);
		}
		modelMap.addAttribute("form", form);
		return "/cameralini/terzi/nuovoSoggettoFisico";
	}
	
	@RequestMapping(value = "/procedimento/nuovo/terzi/ricerca.htm", method = RequestMethod.POST, params = { "verificaPI" })
	public String controllaPISoggettoGiuridico(@ModelAttribute("form") TerzoIntervenienteForm form, BindingResult result, ModelMap modelMap) {
		String msgErrore = "";
		
		if ( "".equals(form.getSoggettoForm().getRagioneSociale()) || form.getSoggettoForm().getRagioneSociale()==null  ) {
			msgErrore = "errors.campi.controllo.pi.ragionesociale";
		} else {
			//Invoco il WS
			EsitoControlloCFePIWrapper esito = agenziaEntrateService.controllaDatiAnagraficiPI(form.getSoggettoForm());
			if (esito.isInvocazioneFallita()) {
				super.setMessage(Constants.MESSAGE, "errors.campi.controllo.pi", esito.getErrore(), false);
			} else {
				if (form.getSoggettoForm().getPartitaIva()!=null && !form.getSoggettoForm().getPartitaIva().equals("")) {
					// Compara CF
					if (form.getSoggettoForm().getPartitaIva().equals(esito.getPartitaIva())) {
						super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.pi.corretta", " ", true);
					} else {
						super.setMessage(Constants.MESSAGE, "errors.campi.controllo.pi.diverso", esito.getPartitaIva(), false);
					}
				} else {
					form.getSoggettoForm().setPartitaIva(esito.getPartitaIva());	
				}
			}
		}
		if (!msgErrore.equals("")) {
			super.setMessage(Constants.MESSAGE, msgErrore, null, false);
			result.reject(Constants.MESSAGE);
		}
		
		ModelAndView mnv = new ModelAndView("/cameralini/terzi/nuovoSoggettoGiuridico", "form", form);
		return "/cameralini/terzi/nuovoSoggettoGiuridico";
		
	}
	@RequestMapping(value = "/procedimento/nuovo/terzi/ricerca.htm", method = RequestMethod.POST, params =	{ "InserisciNuovoSoggettoFisico" })
	public ModelAndView inserisciSoggettoFisicoEAssocia(@ModelAttribute("form") TerzoIntervenienteForm form, BindingResult result, ModelMap modelMap, HttpServletRequest request) {
		form.getSoggettoForm().setIdRuolo(Constants.ID_RUOLO_INTERVENIENTE);
		soggettoValidation.validateNuovoSoggettoFisicoObbl(form.getSoggettoForm(), result);
		
		soggettoValidation.validateNuovoSoggettoNonObbl(form.getSoggettoForm(), result);
		
		if (result.hasErrors()) {
			super.setMessage(Constants.MESSAGE, "errors.campi.controllo", null, false);
			return new ModelAndView("/cameralini/terzi/nuovoSoggettoFisico", "form", form);
		}

		addBreadcrumbs("InserisciNuovoSoggettoFisico",request);

		if(form.getSoggettoForm().getStatoNascita() != null){
			String idStatoNasc = form.getSoggettoForm().getStatoNascita().getId();
			String label = Utility.findLabelByValue(StaticListLoader.getStati(), idStatoNasc);
			form.getSoggettoForm().getStatoNascita().setDescrizione(label);
		}
		
		form.getSoggettoForm().setTipoSoggetto(Constants.SOGGETTO_FISICO);

		int idUffOwner = (Integer) request.getSession().getAttribute("idUffOwner");
		String codUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);

		AssociaPersonaDto soggAssociato = procedimentoService.nuovaPersonaFisica(idUffOwner, codUtente, form.getSoggettoForm());
		
		SoggettoForm soggettoForm = new SoggettoForm(); 
		soggettoForm.setIdSoggetto(soggAssociato.getIDPERS().longValue());
		soggettoForm.setIdVersione(1L);
		form.setSoggettoForm(soggettoForm);
		
		return registra(form, result, modelMap, request);
	}	
	
	@RequestMapping(value = "/procedimento/nuovo/terzi/ricerca.htm", method = RequestMethod.POST, params =	{ "InserisciNuovoSoggettoGiuridico" })
	public ModelAndView inserisciSoggettoGiuridicoEAssocia(@ModelAttribute("form") TerzoIntervenienteForm form, BindingResult result, ModelMap modelMap, HttpServletRequest request) {
		form.getSoggettoForm().setIdRuolo(Constants.ID_RUOLO_INTERVENIENTE);
		soggettoValidation.validateNuovoSoggettoGiuridico(form.getSoggettoForm(), result);
		
		if (result.hasErrors()) {
			super.setMessage(Constants.MESSAGE, "errors.campi.controllo", null, false);
			return new ModelAndView("/cameralini/terzi/nuovoSoggettoGiuridico", "form", form);
		}

		form.getSoggettoForm().setTipoSoggetto(Constants.SOGGETTO_GIURIDICO);

		int idUffOwner = (Integer) request.getSession().getAttribute("idUffOwner");
		String codUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);

		AssociaPersonaDto soggAssociato = procedimentoService.nuovaPersonaGiuridica(idUffOwner, codUtente, form.getSoggettoForm());
		
		SoggettoForm soggettoForm = new SoggettoForm(); 
		soggettoForm.setIdSoggetto(soggAssociato.getIDPERS().longValue());
		soggettoForm.setIdVersione(1L);		
		form.setSoggettoForm(soggettoForm);
		
		return registra(form, result, modelMap, request);
	}		
	
	
	@RequestMapping(value = "/procedimento/nuovo/terzi/ricerca.htm", method = RequestMethod.POST, params =	{ "Associa" })
	public ModelAndView associa(@Valid @ModelAttribute("form") TerzoIntervenienteForm form, BindingResult result, ModelMap modelMap, HttpServletRequest request) {

		if(form.getSoggettoForm()!=null && form.getSoggettoForm().getIdSoggetto()!=null) { 
		
			SoggettoForm soggettoForm = new SoggettoForm(); 
			soggettoForm.setIdSoggetto(form.getSoggettoForm().getIdSoggetto());
			soggettoForm.setIdVersione(form.getSoggettoForm().getIdVersione());
			soggettoForm.setTipoSoggetto(form.getSoggettoForm().getTipoSoggetto());
			form.setSoggettoForm(soggettoForm);
			
			return registra(form, result, modelMap, request);
			
		} else {
			if(form.getTipoSoggetto().equals("F")) return ricercaSoggettoFisico(form, result, modelMap, request);
			else return ricercaSoggettoGiuridico(form, result, modelMap, request);
		}

	}

	
	
    @RequestMapping( value = "/procedimento/nuovo/terzi/ricerca.htm", method = RequestMethod.POST, params = { "Indietro" } )
	public ModelAndView indietro( TerzoIntervenienteForm form, BindingResult result, ModelMap modelMap, HttpServletRequest request )
	{
		MenuController.caricaMenu( Constants.ID_K_MENU_PROCEDIMENTO, request );
		TerzoIntervenienteForm terzoIntervenienteForm = new TerzoIntervenienteForm();
		terzoIntervenienteForm.setFunzioneCameralini("terzi");
		ModelAndView mnv = new ModelAndView( "/cameralini/ricerca", "ricerca", terzoIntervenienteForm );
		cleanBreadcrumb(request.getSession());
		addBreadcrumbs();
		return mnv;
	}
    
    @RequestMapping( value = "/procedimento/nuovo/terzi/ricerca.htm", method = RequestMethod.POST, params = { "IndietroRicerca" } )
	public ModelAndView indietroRicerca( TerzoIntervenienteForm form, BindingResult result, ModelMap modelMap, HttpServletRequest request )
	{
		MenuController.caricaMenu( Constants.ID_K_MENU_PROCEDIMENTO, request );
		form.setFunzioneCameralini("terzi");
		ModelAndView mnv = new ModelAndView( "/cameralini/terzi/istanzaDiTerzo", "form", form );
		cleanBreadcrumb(request.getSession());
		addBreadcrumbs();
		return mnv;
	}
    
    @RequestMapping( value = "/procedimento/nuovo/terzi/ricerca.htm", method = RequestMethod.POST, params = { "IndietroNuovo" } )
	public ModelAndView indietroNuovo( TerzoIntervenienteForm form, BindingResult result, ModelMap modelMap, HttpServletRequest request )
	{
		MenuController.caricaMenu( Constants.ID_K_MENU_PROCEDIMENTO, request );
		form.setFunzioneCameralini("terzi");
		ModelAndView mnv = new ModelAndView( "/cameralini/terzi/ricercaSoggetto", "form", form );
		
		@SuppressWarnings("unchecked")
		List<IntestatarioDto> listaIntestatari = (List<IntestatarioDto>) request.getSession().getAttribute("listaIntestatariTerzoInter");
		if (listaIntestatari != null) {
			modelMap.addAttribute("listaIntestatari", listaIntestatari);
		}
		cleanBreadcrumb(request.getSession());
		addBreadcrumbs();
		return mnv;
	}
    
    private SoggettoForm caricaSoggForm(TerzoIntervenienteForm form)
    {
		SoggettoForm soggettoForm = new SoggettoForm();
		soggettoForm.setIdProcedimento(form.getProcedPadre().getIdProcedimento());
		soggettoForm.setCognome(form.getCognome());
		soggettoForm.setNome(form.getNome());
		soggettoForm.setDataNascita(form.getDataNascita());
		soggettoForm.setCodiceFiscale(form.getCodiceFiscale());
		soggettoForm.setStatoNascita(form.getStatoNascita());
		soggettoForm.setSesso(form.getSesso());
		soggettoForm.setComuneNascita(form.getComuneNascita());
		soggettoForm.setDescrizioneComuneEsteroNascita(form.getDescrizioneComuneEsteroNascita());
		if (form.getComuneSedeLegale() != null) 
		{
			soggettoForm.setDescrizioneComuneEsteroNascita(form.getComuneSedeLegale().getDescrizione());
		}
		soggettoForm.setRagioneSociale(form.getRagioneSociale());
		if (form.getPartitaIvaSiglaStato() != null) {
			soggettoForm.setPartitaIvaSiglaStato(form.getPartitaIvaSiglaStato());
		}
		if (form.getPartitaIva() != null) {
			soggettoForm.setPartitaIva(form.getPartitaIva());
		}
		if (form.getPartitaIvaSiglaStato() != null && form.getPartitaIva() != null) {
			soggettoForm.setPartitaIvaCompleta(form.getPartitaIvaSiglaStato()+form.getPartitaIva());
		}
		soggettoForm.setStatoSedeLegale(form.getStatoSedeLegale());
		soggettoForm.setComuneSedeLegale(form.getComuneSedeLegale());
		soggettoForm.setTipoSoggetto(form.getTipoSoggetto());
		soggettoForm.setIdTipoSocieta(form.getIdTipoSocieta());
		soggettoForm.setNumeroCCIAA(form.getNumeroCCIAA());
		return soggettoForm;
	}
 
 	 
 	// @SuppressWarnings("null")
  	@RequestMapping( value = { "/procedimento/nuovo/terzi/ricerca.htm" }, method = RequestMethod.POST, params = { "DettaglioSoggettoFisico" } )
  	public ModelAndView dettaglioSoggettoFisico(@Valid @ModelAttribute("form")TerzoIntervenienteForm form, BindingResult result, ModelMap modelMap , HttpServletRequest request) {

  		form.setFunzioneCameralini("terzi");
  		form.setTipoSoggetto("F");
  		Long idSoggetto = form.getSoggettoForm().getIdSoggetto();
  		ModelAndView mnv;
  		if(idSoggetto!=null) {
	  		SoggettoForm soggettoForm = new SoggettoForm();
	  		soggettoForm.setIdSoggetto(form.getSoggettoForm().getIdSoggetto());
			soggettoForm.setIdVersione(form.getSoggettoForm().getIdVersione());
	  		soggettoForm.setIdUfficio((Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE));
			soggettoForm.setTipoSoggetto(Constants.SOGGETTO_FISICO);
	  		soggettoForm = procedimentoService.caricaValoriDettaglioSoggettoFisico(soggettoForm);
	  		form.setSoggettoForm(soggettoForm);
  			mnv = new ModelAndView("/cameralini/terzi/dettaglioSoggettoFisico", "form", form);
  	  		addBreadcrumbs("DettaglioSoggettoFisico",request);

  		} else {
  			
  			mnv = ricercaSoggettoFisico(form, result, modelMap, request);
		}
  		
  		return mnv;
  	}
  	
  	 @RequestMapping( value = "/procedimento/nuovo/terzi/ricerca.htm", method = RequestMethod.POST, params = { "IndietroDettaglioFisico" } )
  	public ModelAndView indietroDettaglioFisico( TerzoIntervenienteForm form, BindingResult result, ModelMap modelMap , HttpServletRequest request)
  	{
  		MenuController.caricaMenu( Constants.ID_K_MENU_PROCEDIMENTO, request );
  		form.setFunzioneCameralini("terzi");
  		ModelAndView mnv = new ModelAndView( "/cameralini/terzi/ricercaSoggetto", "form", form );
  		
		@SuppressWarnings("unchecked")
		List<IntestatarioDto> listaIntestatari = (List<IntestatarioDto>) request.getSession().getAttribute("listaIntestatariTerzoInter");
		if (listaIntestatari != null) {
			modelMap.addAttribute("listaIntestatari", listaIntestatari);
		}
  		cleanBreadcrumb(request.getSession());
  		addBreadcrumbs();
  		return mnv;
  	}

  	// @SuppressWarnings("null")
   	@RequestMapping( value = { "/procedimento/nuovo/terzi/ricerca.htm" }, method = RequestMethod.POST, params = { "DettaglioSoggettoGiuridico" } )
   	public ModelAndView dettaglioSoggettoGiuridico(@Valid @ModelAttribute("form")TerzoIntervenienteForm form, BindingResult result, ModelMap modelMap , HttpServletRequest request) {

  		form.setFunzioneCameralini("terzi");
  		form.setTipoSoggetto("G");
  		Long idSoggetto = form.getSoggettoForm().getIdSoggetto();
  		ModelAndView mnv;
  		if(idSoggetto!=null) {
	  		SoggettoForm soggettoForm = new SoggettoForm();
	  		soggettoForm.setIdSoggetto(form.getSoggettoForm().getIdSoggetto());
			soggettoForm.setIdVersione(form.getSoggettoForm().getIdVersione());
	  		soggettoForm.setIdUfficio((Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE));
			soggettoForm.setTipoSoggetto(Constants.SOGGETTO_GIURIDICO);
	  		soggettoForm = procedimentoService.caricaValoriDettaglioSoggettoGiuridico(soggettoForm);
	  		form.setSoggettoForm(soggettoForm);
  			mnv = new ModelAndView("/cameralini/terzi/dettaglioSoggettoGiuridico", "form", form);
  	  		addBreadcrumbs("DettaglioSoggettoGiuridico",request);

  		} else {
  			
  			mnv = ricercaSoggettoGiuridico(form, result, modelMap, request);
		}
  		
  		return mnv;
   	}
   	
   	 @RequestMapping( value = "/procedimento/nuovo/terzi/ricerca.htm", method = RequestMethod.POST, params = { "IndietroDettaglioGiuridico" } )
   	public ModelAndView indietroDettaglioGiuridico( TerzoIntervenienteForm form, BindingResult result, ModelMap modelMap, HttpServletRequest request )
   	{
   		MenuController.caricaMenu( Constants.ID_K_MENU_PROCEDIMENTO, request );
   		form.setFunzioneCameralini("terzi");
   		ModelAndView mnv = new ModelAndView( "/cameralini/terzi/ricercaSoggetto", "form", form );
		@SuppressWarnings("unchecked")
		List<IntestatarioDto> listaIntestatari = (List<IntestatarioDto>) request.getSession().getAttribute("listaIntestatariTerzoInter");
		if (listaIntestatari != null) {
			modelMap.addAttribute("listaIntestatari", listaIntestatari);
		}
   		cleanBreadcrumb(request.getSession());
   		addBreadcrumbs();
   		return mnv;
   	}
  

}