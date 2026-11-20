package it.giustizia.smp.controller.cameralini;

import it.giustizia.smp.controller.MenuController;
import it.giustizia.smp.controller.procedimento.ApriController;
import it.giustizia.smp.dto.pg_procedimento.AssociaPersonaDto;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.form.cameralini.CorrezioneErroriForm;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
public class RicercaCorrezioneErroriController extends RicercaBaseController
{
    @Autowired
	private ApriController 	apriController;	
    
	// @RequestMapping( value = "/procedimento/nuovo/correzione/ricerca.htm", method = RequestMethod.GET )
	@RequestMapping(value = "/procedimento/nuovo/correzione/ricerca.htm", method = RequestMethod.GET)
	public ModelAndView ricerca(Model model, HttpServletRequest request)
	{
		return ricercaBase(model, "correzione",  Constants.TIPOPROC_CORREZIONE_ERRORI_CAM, request); //TODO Correzione parametro è idTipoProcedimento ad oggi =1 da parametrizzare  in attesa di specifiche
	}

	// @SuppressWarnings("null")
	@RequestMapping(value = "/procedimento/nuovo/correzione/ricerca.htm", method = RequestMethod.POST, params = { "Ricerca" })
	public ModelAndView elencoSoggetti(@Valid @ModelAttribute( "ricerca" ) CorrezioneErroriForm form, BindingResult result, ModelMap modelMap, HttpServletRequest request)
	{
		int idTipoUfficio = (Integer) request.getSession().getAttribute("idTipoUfficio");

		
		form.setFunzioneCameralini("correzione");
		form.setTipoUfficio(idTipoUfficio);
		ModelAndView mnv = new ModelAndView("/cameralini/correzione/soggettiFascicoloCorrezione", "form", form);

		if (!result.hasErrors()) {
			// INIZIO CONTROLLI VALIDATE CAMERALINI
			ProcedimentoForm procedimento = form.getProcedPadre();
			cameraliniValidation.validateCriteriRicercaPuntuale(procedimento, result);

			if (result.hasErrors()) {
				return new ModelAndView("/cameralini/ricerca", "ricerca", form);

			} else {
	        	
			    // recupero dati ufficio da sessione
				Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
				
				Long anno = form.getProcedPadre().getAnno().longValue();
				Long numero = form.getProcedPadre().getNumero();
//				Integer ufficioSel = Integer.valueOf(form.getUfficio().getId());
				form.setFunzioneCameralini("correzione");
				request.getSession().setAttribute("idUfficioUtente",idUfficioUtente);
		
				// verifica esistenza del procedimento con chiave: numero + anno + codiceUfficio
				ProcedimentoForm p = getProcedimentoPadre(idUfficioUtente, numero, anno, Constants.TIPOPROC_CORREZIONE_ERRORI_CAM);
				if (p == null) {
					super.setMessage(Constants.MESSAGE,	"cameralini.no.fascicolo.prevenzione", null, true);
					return mnv = new ModelAndView("/cameralini/ricerca", "ricerca", form);
				}
				if(!("1".equals(p.getTipoFascicolo().getId()) )) {
					super.setMessage(Constants.MESSAGE,	"cameralini.no.fascicolo.prevenzione", null, true);
					return mnv = new ModelAndView("/cameralini/ricerca", "ricerca", form);
				}
				if (!p.getStatoProcedimento().getId().equals("22") ) {
					super.setMessage(Constants.MESSAGE,	"cameralini.fascicolo.no.definito", null, true);
					return mnv = new ModelAndView("/cameralini/ricerca", "ricerca", form);
				}
			
				
				form.setProcedPadre(p);


		// si caricano in sessione i dati del procedimento in ProcedimentoForm
		request.getSession().setAttribute(Constants.ID_PROCEDIMENTO, form.getProcedPadre());
				
		// una volta superati i controlli si recupera la lita dei soggetti  interessati
		List<SoggettoForm> listaRicerca = procedimentoServiceCam.soggettiDirettiInteressatiDelProcedimento(form.getProcedPadre().getIdProcedimento(), null, Constants.Flag.S);

		modelMap.addAttribute("listaSoggetti", listaRicerca);
		
			}
		} else {
			return mnv = new ModelAndView("/cameralini/ricerca", "ricerca", form);
		}

		cleanBreadcrumb(request.getSession());
		addBreadcrumbs();
		return mnv;
	}

	/**
	 * @RequestMapping( value = "/procedimento/nuovo/correzione/ricerca.htm", method = RequestMethod.POST, params = { "Indietro" } )
	 **/
	@RequestMapping(value = "/procedimento/nuovo/correzione/ricerca.htm", method = RequestMethod.POST, params = { "Indietro" })
	public ModelAndView indietro(CorrezioneErroriForm form, BindingResult result, ModelMap modelMap, HttpServletRequest request)
	{
		MenuController.caricaMenu(Constants.ID_K_MENU_PROCEDIMENTO, request);
		CorrezioneErroriForm formCorrezione = new CorrezioneErroriForm();
		formCorrezione.setFunzioneCameralini("correzione");

		ModelAndView mnv = new ModelAndView("/cameralini/ricerca", "ricerca", formCorrezione);
		cleanBreadcrumb(request.getSession());
		addBreadcrumbs();
		return mnv;
	}
	
	/**
	 * @RequestMapping( value = "/procedimento/nuovo/correzione/ricerca.htm", method = RequestMethod.POST, params = { "IndietroDettaglio" } )
	 **/
	@RequestMapping(value = "/procedimento/nuovo/correzione/ricerca.htm", method = RequestMethod.POST, params = { "IndietroDettaglio" })
	public ModelAndView indietroDettaglio(CorrezioneErroriForm form, BindingResult result, ModelMap modelMap, HttpServletRequest request)
	{
		
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
			
		ModelAndView mnv = new ModelAndView( "/cameralini/correzione/soggettiFascicoloCorrezione", "form", form);
		MenuController.caricaMenu(Constants.ID_K_MENU_PROCEDIMENTO, request);
		List<SoggettoForm> listaRicerca = procedimentoServiceCam.soggettiDirettiInteressatiDelProcedimento(form.getProcedPadre().getIdProcedimento(), null, Constants.Flag.S);
		modelMap.addAttribute("listaSoggetti", listaRicerca);
		cleanBreadcrumb(request.getSession());
		addBreadcrumbs();
		return mnv;

	}
	
	/**  @RequestMapping( value = "/procedimento/nuovo/correzione/ricerca.htm", method = RequestMethod.POST, params = {"Dettaglio"} ) **/
    @RequestMapping( value = "/procedimento/nuovo/correzione/ricerca.htm", method = RequestMethod.POST, params = {"Dettaglio"} )
    public ModelAndView soggettoSitMP( @Valid @ModelAttribute("form")CorrezioneErroriForm form, BindingResult result, ModelMap modelMap , HttpServletRequest request)
    {
    			
    	ModelAndView mnv = new ModelAndView( "/cameralini/correzione/soggettiFascicoloCorrezione", "form", form );
    	Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
    	Integer ufficioSel = (Integer)request.getSession().getAttribute("idUfficioSel");
//    	TODO  idTipoProcedimento  da modificare  in attesa di specifiche;KKK
    	Long idTipoProcedimento = 1L;
    	form.setFunzioneCameralini("correzione");
    	
    	String soggettoSel=form.getSoggettoSelected();
    	if (soggettoSel != null) {
    				SoggettoForm soggettoDettaglio = new SoggettoForm();
	    			List<SoggettoForm> lista = procedimentoServiceCam.soggettiDirettiInteressatiDelProcedimento
	    					(form.getProcedPadre().getIdProcedimento(), null, Constants.Flag.S);
	    			Iterator<SoggettoForm> iter = lista.iterator();
					while(iter.hasNext()) {
						SoggettoForm soggetto = iter.next();
						if (soggetto.getIdSoggetto().longValue() == new Long(soggettoSel).longValue()) {
							soggettoDettaglio.setIdSoggetto(soggetto.getIdSoggetto());
							soggettoDettaglio.setTipoSoggetto(soggetto.getTipoSoggetto());
							soggettoDettaglio.setIdVersione(soggetto.getIdVersione());
							
						}
					}
					// per poter utilizzare il controller Soggetto occorre che questo
					// lavori avendo in sessione come ufficioUtente quello del procedimento
					//request.getSession().setAttribute(Constants.ID_UFFICIO_UTENTE,ufficioSel);
					request.getSession().setAttribute(Constants.ID_UFFICIO_UTENTE,idUfficioUtente);
			    	mnv = soggettoController.direttiInteressatiDettaglio(soggettoDettaglio, result);
			    	
	    			return mnv;
			    		
    		}
    	 else {
    		 super.setMessage( Constants.MESSAGE, "table.item.selection", null, false );
             result.reject( Constants.MESSAGE );
             List<SoggettoForm> lista = procedimentoServiceCam.soggettiDirettiInteressatiDelProcedimento(form.getProcedPadre().getIdProcedimento(), null, Constants.Flag.S);
     		 modelMap.addAttribute("listaSoggetti", lista);
             return	 mnv = new ModelAndView( "/cameralini/correzione/soggettiFascicoloCorrezione", "form", form );
    	}
    	
	}	
    
	/**
	 * @RequestMapping( value = "/procedimento/nuovo/correzione/ricerca.htm",
	 *                  method = RequestMethod.POST, params = {"Modifica"} )
	 **/
	@RequestMapping(value = "/procedimento/nuovo/correzione/ricerca.htm", method = RequestMethod.POST, params = { "Modifica" })
	public ModelAndView modificaSoggetto(CorrezioneErroriForm form, BindingResult result, ModelMap modelMap, HttpServletRequest request)
	{
		
		ModelAndView mnv = new ModelAndView("/cameralini/correzione/soggettiFascicoloCorrezione", "form", form);
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");

		String soggettoSel = form.getSoggettoSelected();
		if (form.getSoggettoSelected() != null)
		{
			List<SoggettoForm> lista = procedimentoServiceCam.soggettiDirettiInteressatiDelProcedimento(form.getProcedPadre().getIdProcedimento(), null, Constants.Flag.S);
			Iterator<SoggettoForm> iter = lista.iterator();
			while (iter.hasNext())
			{
				SoggettoForm soggetto = iter.next();
				if (soggetto.getIdSoggetto().longValue() == new Long(soggettoSel).longValue())
				{
					if (soggetto.getTipoSoggetto().equals("F"))
					{
						form.setTipoSoggetto("F");
						form.setCognome(soggetto.getCognome());
						form.setNome(soggetto.getNome());
						form.setSesso(soggetto.getSesso());
						form.setCodiceFiscale(soggetto.getCodiceFiscale());
						form.setDataNascita(soggetto.getDataNascita());
						form.setStatoNascita(soggetto.getStatoNascita());
						form.setComuneNascita(soggetto.getComuneNascita());
						if(soggetto.getDescrizioneComuneEsteroNascita() != null && !soggetto.getDescrizioneComuneEsteroNascita().equals("")){
							form.setDescrizioneComuneEsteroNascita(soggetto.getDescrizioneComuneEsteroNascita());
							form.getComuneNascita().setDescrizione(soggetto.getDescrizioneComuneEsteroNascita());
						}
						
						
					} else
					{
						form.setTipoSoggetto("G");
						form.setRagioneSociale(soggetto.getRagioneSociale());
						form.setPartitaIva(soggetto.getPartitaIva().substring( 2, soggetto.getPartitaIva().length() ) );
						form.setPartitaIvaSiglaStato(soggetto.getPartitaIva().substring( 0, 2 ) );

						form.setStatoSedeLegale(soggetto.getStatoSedeLegale());
						form.setComuneSedeLegale(soggetto.getComuneSedeLegale());
						
						if(soggetto.getDescrizioneComuneEsteroSedeLegale() != null && !soggetto.getDescrizioneComuneEsteroSedeLegale().equals("")){
							form.setDescrizioneComuneEsteroSedeLegale(soggetto.getDescrizioneComuneEsteroSedeLegale());
							form.getComuneSedeLegale().setDescrizione(soggetto.getDescrizioneComuneEsteroSedeLegale());
						}						
					}
					request.getSession().setAttribute("soggettoSel", soggetto);
					return mnv = new ModelAndView("/cameralini/correzione/modificaSoggetto", "form", form);
				}
			}

		}else {
		super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
		result.reject(Constants.MESSAGE);
		List<SoggettoForm> listaRicerca = procedimentoServiceCam.soggettiDirettiInteressatiDelProcedimento(form.getProcedPadre().getIdProcedimento(), null, Constants.Flag.S);
		modelMap.addAttribute("listaSoggetti", listaRicerca);
		return mnv;
	}
	return mnv;
		
	
	}

	@RequestMapping(value = "/procedimento/nuovo/correzione/ricerca.htm", method = RequestMethod.POST, params =	{ "Registra" })
	public ModelAndView confermaModifiche(@Valid @ModelAttribute("form") CorrezioneErroriForm form, BindingResult result, ModelMap modelMap, HttpServletRequest request)
	{

		// Dati da sessione
		Integer idUffOwn = (Integer) request.getSession().getAttribute("idUffOwner");
		String codUtente = (String) request.getSession().getAttribute("codUtente");
	

		ModelAndView mnv = new ModelAndView("/cameralini/correzione/modificaSoggetto", "form", form);
		ProcedimentoForm procForm =new ProcedimentoForm();
		// TODO idTipoProcedimento e idCatAtto da modificare in attesa di
		Long idTipoProcedimento = Constants.TIPOPROC_CORREZIONE_ERRORI_CAM;
		List<AssociaPersonaDto> listaAssoc = null;
		
		//GP 
		String codiceFiscalePG = null; 	
		String indirizzoSede   = null;
		String descNumeCcia    = null;    
		String descCivico      = null;   
		String descCap         = null;
		Long codTipoStrada     = null;   
		Long codComSede        = null;
		Long statoSedeLegaleId = null;   
		Long idTipoSocieta     = null;
		Long idComuneNascita   = null;
		Long idStatoNascita    = null;
		Long pIva              = null;
		
		if (!result.hasErrors())
		{
			if (result.hasErrors())
			{
				List<SoggettoForm> lista = caricalista(idUffOwn, form.getProcedPadre().getIdProcedimento(),
						idTipoProcedimento);
				modelMap.addAttribute("listaSoggetti", lista);
				return mnv;
			}
			SoggettoForm soggetto = (SoggettoForm) request.getSession().getAttribute("soggettoSel");
			soggetto.setIdProcedimento(form.getProcedPadre().getIdProcedimento());
			if ("F".equals(soggetto.getTipoSoggetto())) {
				soggetto = procedimentoServiceCam.caricaValoriDettaglioSoggettoFisicoProcedimento(soggetto);
			} else {
				soggetto = procedimentoServiceCam.caricaValoriDettaglioSoggettoGiuridicoProcedimento(soggetto);
			}

			List<SoggettoForm> lista = new ArrayList<SoggettoForm>();

			// aggiornare soggetto con i campi modificati
			if (soggetto.getTipoSoggetto().equals("F"))
			{
				// dati soggetto fisico
				soggetto.setCognome(form.getCognome());
				soggetto.setNome(form.getNome());
				soggetto.setSesso(form.getSesso());
				soggetto.setCodiceFiscale(form.getCodiceFiscale());
				soggetto.setDataNascita(form.getDataNascita());
				soggetto.setStatoNascita(form.getStatoNascita());
				if (new Long(form.getStatoNascita().getId())!=-1){
					idStatoNascita = new Long(form.getStatoNascita().getId());
				}
				
				if(new Long(form.getStatoNascita().getId())==1)
				{
					soggetto.setComuneNascita(form.getComuneNascita());
					if (form.getComuneNascita().getDescrizione() != null && form.getComuneNascita().getId() != null){
						idComuneNascita = new Long(form.getComuneNascita().getId());
					}
				}else{
					if(form != null && form.getComuneNascita() != null && form.getComuneNascita().getDescrizione()!= null)
					 soggetto.setDescrizioneComuneEsteroNascita(form.getComuneNascita().getDescrizione());	
				}
				
				

			
			} else
			{
				// dati soggetto giuridico
				soggetto.setRagioneSociale(form.getRagioneSociale());
				if (form.getPartitaIvaSiglaStato() != null) {
					soggetto.setPartitaIvaSiglaStato(form.getPartitaIvaSiglaStato());
				}
				if (form.getPartitaIva() != null) {
					soggetto.setPartitaIva(form.getPartitaIva());
				}
				if (form.getPartitaIvaSiglaStato() != null && form.getPartitaIva() != null) {
					soggetto.setPartitaIvaCompleta(form.getPartitaIvaSiglaStato()+form.getPartitaIva());
				}

				if (new Long(form.getStatoSedeLegale().getId())!=-1){
					soggetto.setStatoSedeLegale(form.getStatoSedeLegale());
					statoSedeLegaleId = new Long(form.getStatoSedeLegale().getId());
				}
				if (statoSedeLegaleId == 1) 
				{
					// dati sede italia
					soggetto.setComuneSedeLegale(form.getComuneSedeLegale());
					codComSede      = new Long(form.getComuneSedeLegale().getId());
					indirizzoSede   = form.getComuneSedeLegale().getDescrizione();
				} else 
				{
					// dati sede estera
					soggetto.getComuneSedeLegale().setDescrizione(form.getComuneSedeLegale().getDescrizione());
					soggetto.setDescrizioneComuneEsteroSedeLegale(form.getComuneSedeLegale().getDescrizione());
					soggetto.getComuneSedeLegale().setId(null);
											
					codComSede      = null;
					indirizzoSede   = null;
				}
				if (!"-1".equals(form.getIndirizzoSedeLegale().getToponimo().getId()) )
				{
					codTipoStrada = new Long(form.getIndirizzoSedeLegale().getToponimo().getId());
				}

				if (form.getIndirizzoSedeLegale().getDenominazione() != null) 
				{
		    		indirizzoSede = form.getIndirizzoSedeLegale().getDenominazione(); 
				}
				if (form.getIndirizzoSedeLegale().getCivico() != null) 
				{
		    		descCivico = form.getIndirizzoSedeLegale().getCivico();
				}
				descNumeCcia    = soggetto.getNumeroCCIAA();    
				if (soggetto.getIdTipoSocieta() > 0L) 
				{
					idTipoSocieta = new Long(soggetto.getIdTipoSocieta()); 
				}
			}
			if (form.getPartitaIva() != null) 
			{
				String error = Utility.controllaPIVA(form.getPartitaIva());
				if (!"".equals(error))
				{
	                super.setMessage(Constants.MESSAGE, error, null, false);
	                result.reject( Constants.MESSAGE );
					return mnv;
				}
				pIva = new Long(form.getPartitaIva());
				if (pIva == 0) 
				{
					super.setMessage(Constants.MESSAGE,	"cameralini.error.pIvaZero", null, true);
					result.reject( Constants.MESSAGE );
					return mnv;
				}
			}
	     	 cameraliniValidation.validateDatiSoggetto(soggetto, result);
	        
			if (result.hasErrors())
			{
				lista = caricalista (idUffOwn, form.getProcedPadre().getIdProcedimento(),	idTipoProcedimento);
				modelMap.addAttribute("listaSoggetti", lista);
				return mnv;
			} else
			{
				lista.add(soggetto);

				if (lista != null && !lista.isEmpty())
				{

					procForm = preparaNuovoProcedimento(form, idTipoProcedimento);
					// inserisco il nuovo procedimento figlio

	    			listaAssoc  = procedimentoServiceCam.inserisciProcedimentoCameraliniInt( procForm.getIdProcedimento(), idUffOwn.intValue(),  idTipoProcedimento.intValue(), 
		    				null,soggetto.getTipoSoggetto(),soggetto.getNome() ,soggetto.getCognome(),soggetto.getSesso(),soggetto.getDataNascita(),
		    				soggetto.getDescrizioneComuneEsteroNascita(),soggetto.getCodiceFiscale(),codiceFiscalePG,
		    				soggetto.getRagioneSociale(),soggetto.getDescrizioneComuneEsteroSedeLegale(),indirizzoSede,soggetto.getPartitaIvaCompleta(),idUffOwn.intValue(),
		    				descNumeCcia,descCivico,descCap,codTipoStrada, codComSede,  idComuneNascita,statoSedeLegaleId,idStatoNascita,
		    				idTipoSocieta,new Long(soggetto.getIdSoggetto()),soggetto.getIdVersione(), codUtente);

				} else
				{
					super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
					result.reject(Constants.MESSAGE);
					lista = caricalista(idUffOwn, form.getProcedPadre().getIdProcedimento(), idTipoProcedimento);
					modelMap.addAttribute("listaSoggetti", lista);
					return mnv = new ModelAndView("/cameralini/correzione/soggettiFascicoloCorrezione", "form", form);
				}
				request.getSession().setAttribute("idProcPadre", form.getProcedPadre().getIdProcedimento());
				request.getSession().setAttribute("idProcFiglio", listaAssoc.get(0).getIDPROC().longValue());
				
				procForm.setIdProcedimento(listaAssoc.get(0).getIDPROC().longValue());
    			request.getSession().setAttribute(Constants.ID_PROCEDIMENTO, procForm);
				return apriController.copertinaProcedimento(procForm, result, request);
				//return copertinaController.caricaDettaglioProcedimento(result, modelMap, base);
			}
	     }else {
		List<SoggettoForm> lista = caricalista(idUffOwn, form.getProcedPadre().getIdProcedimento(),	idTipoProcedimento);
		modelMap.addAttribute("listaSoggetti", lista);
		return mnv;
	    }

	}

	
}