package it.giustizia.smp.controller.cameralini;

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

import it.giustizia.smp.controller.MenuController;
import it.giustizia.smp.controller.procedimento.ApriController;
import it.giustizia.smp.dto.pg_cameralini.ProcedimentoDto;
import it.giustizia.smp.dto.pg_procedimento.AssociaPersonaDto;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.form.cameralini.MisurePrevenzioniPatrimonialeForm;
import it.giustizia.smp.utility.Constants;

@Controller
public class RicercaMisurePrevenzionePatrimonialeController extends RicercaBaseController
{
	
    @Autowired
	private ApriController 	apriController;
	

	// @RequestMapping( value = "/procedimento/nuovo/misuraPatrimoniale/ricerca.htm", method = RequestMethod.GET )
	@RequestMapping( value = "/procedimento/nuovo/misuraPatrimoniale/ricerca.htm", method = RequestMethod.GET )
	public ModelAndView ricerca ( Model model , HttpServletRequest request )
	{
		return ricercaBase(model,"misuraPatrimoniale",  new Long(1), request); //TODO terzo parametro è idTipoProcedimento ad oggi =1 da parametrizzare  in attesa di specifiche
	}

	// @SuppressWarnings("null")
	@RequestMapping( value = "/procedimento/nuovo/misuraPatrimoniale/ricerca.htm", method = RequestMethod.POST, params = { "Ricerca" } )
	public ModelAndView elencoSoggetti( @Valid @ModelAttribute( "ricerca" ) MisurePrevenzioniPatrimonialeForm form, BindingResult result, ModelMap modelMap , HttpServletRequest request)
	{
		/****************** MOCK ****************/
	//	int idTipoUfficio = (Integer) request.getSession().getAttribute("idTipoUfficio");
	//	if(idTipoUfficio == 6)
	//	{	form.getCognomeRichiedente().getBytes();
	//		form.getTrasmissione().getDescrizione();
	//	} 
		/*************** END MOCK ***************/
		
		form.setFunzioneCameralini("misuraPatrimoniale");
		ModelAndView mnv = new ModelAndView( "/cameralini/misuraPatrimoniale/soggettiFascicoloMisuraPatrimoniale", "form", form );
		
		if (!result.hasErrors()) {
			// INIZIO CONTROLLI VALIDATE CAMERALINI
			ProcedimentoForm procedimento = form.getProcedPadre();
			cameraliniValidation.validateCriteriRicercaPuntuale(procedimento, result);

			if (result.hasErrors()) {
				return new ModelAndView("/cameralini/ricerca", "ricerca", form);

			} else {
				// recupero dati ufficio da sessione
				Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
				int idTipoUfficio = (Integer) request.getSession().getAttribute("idTipoUfficio");
				Long anno = form.getProcedPadre().getAnno().longValue();
				Long numero = form.getProcedPadre().getNumero();
				Integer ufficioSel = Integer.valueOf(form.getUfficio().getId());
				form.setUfficioSelezionato(form.getUfficio().getDescrizione());
				
				request.getSession().setAttribute("idUfficioSel",ufficioSel);
				
				//verifica esistenza del procedimento con chiave: numero + anno + codiceUfficio
				ProcedimentoForm p = getProcedimentoPadre(ufficioSel,numero,anno,Constants.TIPOPROC_MODIFICA_PATRIMONIALE_CAM);
				
				// ginesti se il risultato precedente  è null verifico solo se presente il procedimento
				// con chiave: numero + anno + codiceUfficio
				 ProcedimentoDto  solop = null;
				 if (p == null) { 
					  solop = procedimentoServiceCam.getProcedimentoByKeysProc(ufficioSel,numero,anno,Constants.TIPOPROC_MODIFICA_PATRIMONIALE_CAM) ; 
					
				 }
				String error = checkMisuraPatrApplicata(p,ufficioSel,solop);
				if(error != null){
					super.setMessage( Constants.MESSAGE, error, null, true );
					return	 mnv = new ModelAndView( "/cameralini/ricerca", "ricerca", form );
				}
				
				form.setProcedPadre(p);
				
				// si caricano in sessione i dati del procedimento in ProcedimentoForm 
				request.getSession().setAttribute(Constants.ID_PROCEDIMENTO, form.getProcedPadre());
				
				// una volta superati i controlli si recupera la lita dei soggetti interesati
				
				//	TODO  idTipoProcedimento  da modificare  in attesa di specifiche;KKK
		    	Long idTipoProcedimento = 1L;
				
				List<SoggettoForm> listaSoggetti = caricalista(idUfficioUtente,form.getProcedPadre().getIdProcedimento(),idTipoProcedimento);
				modelMap.addAttribute("listaSoggetti", listaSoggetti);
		
			}
		} else {
			return mnv = new ModelAndView("/cameralini/ricerca", "ricerca", form);
		}
		cleanBreadcrumb(request.getSession());
		addBreadcrumbs();
		return mnv;
	}
	
    /** @RequestMapping( value = "/procedimento/nuovo/misuraPatrimoniale/ricerca.htm", method = RequestMethod.POST, params = {"Dettaglio"} ) */
    @RequestMapping( value = "/procedimento/nuovo/misuraPatrimoniale/ricerca.htm", method = RequestMethod.POST, params = {"Dettaglio"} )
    public ModelAndView soggettoSitMP(@Valid @ModelAttribute("form") MisurePrevenzioniPatrimonialeForm form, BindingResult result, ModelMap modelMap , HttpServletRequest request)
	{
    	ModelAndView mnv = new ModelAndView( "/cameralini/misuraPatrimoniale/soggettiFascicoloMisuraPatrimoniale", "form", form );
    	Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
    	
//    	TODO  idTipoProcedimento  da modificare  in attesa di specifiche;KKK
    	Long idTipoProcedimento = 1L;
    	
    	List<SoggettoForm> listaSoggetti = form.estrapolaListaSoggettiMisPersConIdCheckati();
    	if (listaSoggetti != null) {
    		if (!listaSoggetti.isEmpty()) {
	    		if (listaSoggetti.size() > 1) {
	                super.setMessage( Constants.MESSAGE, "table.item.selection.single", null, false );
	                result.reject( Constants.MESSAGE );
	                List<SoggettoForm> lista = caricalista(idUfficioUtente,form.getProcedPadre().getIdProcedimento(),idTipoProcedimento);
	        		modelMap.addAttribute("listaSoggetti", lista);
	                return	 mnv = new ModelAndView( "/cameralini/misuraPatrimoniale/soggettiFascicoloMisuraPatrimoniale", "form", form );
	    		} else {
	    			SoggettoForm soggettoSel = listaSoggetti.get(0);
	    			SoggettoForm soggettoDettaglio = new SoggettoForm();
	    			
	    			List<SoggettoForm> lista = caricalista(idUfficioUtente,form.getProcedPadre().getIdProcedimento(),idTipoProcedimento);
	    			
	    			Iterator<SoggettoForm> iter = lista.iterator();
					while(iter.hasNext()) {
						SoggettoForm soggetto = iter.next();
						if (soggetto.getIdSoggetto().longValue() == soggettoSel.getIdSoggetto().longValue()) {
							soggettoDettaglio.setIdSoggetto(soggetto.getIdSoggetto());
							soggettoDettaglio.setTipoSoggetto(soggetto.getTipoSoggetto());
							soggettoDettaglio.setIdVersione(soggetto.getIdVersione());
							
						}
					}
					// per poter utilizzare il controller Soggetto occorre che questo
					// lavori avendo in sessione come ufficioUtente quello del procedimento
					Integer ufficioSel = (Integer)request.getSession().getAttribute("idUfficioSel");
					
					request.getSession().setAttribute(Constants.ID_UFFICIO_UTENTE,ufficioSel);
					
	    			mnv = soggettoController.direttiInteressatiDettaglio(soggettoDettaglio, result);
	    			request.getSession().setAttribute(Constants.ID_UFFICIO_UTENTE,idUfficioUtente);
	    			return mnv;
	    		}
    		}
    	} else {
    		 super.setMessage( Constants.MESSAGE, "table.item.selection", null, false );
             result.reject( Constants.MESSAGE );
     		 List<SoggettoForm> lista = caricalista(idUfficioUtente,form.getProcedPadre().getIdProcedimento(),idTipoProcedimento);
     		 modelMap.addAttribute("listaSoggetti", lista);
             return	 mnv = new ModelAndView( "/cameralini/misuraPatrimoniale/soggettiFascicoloMisuraPatrimoniale", "form", form );
    	}
		return null;
	}
    
    /** @RequestMapping( value = "/procedimento/nuovo/misuraPatrimoniale/ricerca.htm", method = RequestMethod.POST, params = {"Registra"} ) */
    @RequestMapping( value = "/procedimento/nuovo/misuraPatrimoniale/ricerca.htm", method = RequestMethod.POST, params = {"Registra"} )
    public ModelAndView registra( @Valid @ModelAttribute( "form" )MisurePrevenzioniPatrimonialeForm form, BindingResult result, ModelMap modelMap , HttpServletRequest request)
	{
    	//si acquisiscono dati da sessione
    	Integer idUffOwn = (Integer) request.getSession().getAttribute("idUffOwner");
    	String codUtente = (String) request.getSession().getAttribute("codUtente");
    	
    	ModelAndView mnv = new ModelAndView( "/cameralini/misuraPatrimoniale/soggettiFascicoloMisuraPatrimoniale", "form", form );
    	List<AssociaPersonaDto> listaAssoc = null;
    	
    	// TODO idTipoProcedimento e idCatAtto da modificare in attesa di specifiche;KKK
    				Long idTipoProcedimento = 1L;
    				Long idCatAtto = 1L;
    	
		if (!result.hasErrors()) {
			
		//  cameraliniValidation.validateDatiRichiedente(form, result);
		//  if (result.hasErrors()) {
		//	  	List<SoggettoForm> lista = caricalista(idUffOwn, form.getProcedPadre().getIdProcedimento(),	idTipoProcedimento);
		//		modelMap.addAttribute("listaSoggetti", lista);
		//	  return mnv;
		//  }

			Long idTipoAtto = 5L;
			int idTipoProc=13;

			List<SoggettoForm> listaSelezioni = form.estrapolaListaSoggettiMisPersConIdCheckati();
			List<SoggettoForm> listaSoggetti = caricalista(idUffOwn, form.getProcedPadre().getIdProcedimento(), idTipoProcedimento);
			List<SoggettoForm> listaSoggettiSel = estraiSelezionati(listaSoggetti, listaSelezioni);
			ProcedimentoForm procForm =new ProcedimentoForm();
			if (listaSelezioni != null && !listaSelezioni.isEmpty()) {

				int idTipoFascicolo = 6;
				Long statoProcedimento = new Long(2);

				procForm= preparaNuovoProcedimento(form, idTipoProcedimento);
				
				//modifica Gennaro
				listaAssoc = procedimentoServiceCam.inserisciProcedimentoCameraliniNew(idUffOwn.intValue(),
								codUtente, procForm, statoProcedimento.longValue(), listaSoggettiSel,
								idTipoFascicolo, idTipoAtto, idCatAtto, form.getCognomeRichiedente(), form.getNomeRichiedente(), 
								form.getDataSottoscrizione(), form.getDataDeposito(), new Long("0"), 
								form.getProcedPadre().getIdProcedimento(),idTipoProc);

			} else {
				super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
				result.reject(Constants.MESSAGE);
				List<SoggettoForm> lista = caricalista(idUffOwn, form.getProcedPadre().getIdProcedimento(), idTipoProcedimento);
				modelMap.addAttribute("listaSoggetti", lista);
				return mnv = new ModelAndView("/cameralini/misuraPatrimoniale/soggettiFascicoloMisuraPatrimoniale", "form", form);
			}
			request.getSession().setAttribute("idProcPadre",  form.getProcedPadre().getIdProcedimento());
			request.getSession().setAttribute("idProcFiglio", listaAssoc.get(0).getIDPROC().longValue());
		
			
			procForm.setIdProcedimento(listaAssoc.get(0).getIDPROC().longValue());
			request.getSession().setAttribute(Constants.ID_PROCEDIMENTO, procForm);
			
			//richiamo la copertina 
			return apriController.copertinaProcedimento(procForm, result, request);
			
			
			
			// return indietro(form, result, modelMap);
			// Metodo di CopertinaController riportare i valori di input nel nuovo form
			//BaseForm base = (BaseForm) form;
			//return copertinaController.caricaDettaglioProcedimento(result, modelMap, base);
			
		}else {
			List<SoggettoForm> lista = caricalista(idUffOwn, form.getProcedPadre().getIdProcedimento(),	idTipoProcedimento);
			modelMap.addAttribute("listaSoggetti", lista);
			return mnv;
		}
	}
    
    @RequestMapping( value = "/procedimento/nuovo/misuraPatrimoniale/ricerca.htm", method = RequestMethod.POST, params = { "Indietro" } )
	public ModelAndView indietro( MisurePrevenzioniPatrimonialeForm form, BindingResult result, ModelMap modelMap , HttpServletRequest request)
	{
		MenuController.caricaMenu( Constants.ID_K_MENU_PROCEDIMENTO, request );
		MisurePrevenzioniPatrimonialeForm misurePrevenzioniForm = new MisurePrevenzioniPatrimonialeForm();
		misurePrevenzioniForm.setFunzioneCameralini("misuraPatrimoniale");
		
		ModelAndView mnv = new ModelAndView( "/cameralini/ricerca", "ricerca", misurePrevenzioniForm );
		cleanBreadcrumb(request.getSession());
		addBreadcrumbs();
		return mnv;
	}
 
	
    private List<SoggettoForm> estraiSelezionati(List<SoggettoForm> listaSoggetti, List<SoggettoForm> listaSelezionati){
    	List<SoggettoForm> ret = new ArrayList <SoggettoForm>();
    	if (listaSelezionati == null || listaSelezionati.isEmpty() || listaSoggetti == null || listaSoggetti.isEmpty()) {
    		return null;
    	}
    	for(int j = 0; j < listaSoggetti.size() ;j++){
    		SoggettoForm  soggetto1 = listaSoggetti.get(j);
    		Long idSogg1 = soggetto1.getIdSoggetto();
    		boolean trovato = false;
    		for (int i = 0; i < listaSelezionati.size() && !trovato;i++){
    			SoggettoForm soggetto2 = listaSelezionati.get(i);
    			if (idSogg1.compareTo(soggetto2.getIdSoggetto()) == 0) {
    				ret.add(soggetto1);
    				trovato = true;
    			}
    		}
    	}
    	return ret;
    }
}