package it.giustizia.smp.controller.cameralini;

import it.giustizia.smp.controller.MenuController;
import it.giustizia.smp.dto.pg_cameralini.ProcedimentoDto;
import it.giustizia.smp.dto.pg_procedimento.AssociaPersonaDto;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.form.cameralini.BaseForm;
import it.giustizia.smp.form.cameralini.MisurePrevenzioniPersonaleForm;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.controller.procedimento.ApriController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RicercaMisurePrevenzionePersonaleController extends RicercaBaseController
{
	@Autowired
	private ApriController 	apriController;
	@Autowired
	private ReloadableResourceBundleMessageSource configParameterSource;	
	
	@RequestMapping( value = "/procedimento/nuovo/misuraPersonale/ricerca.htm", method = RequestMethod.GET )
	public ModelAndView ricerca ( @Valid @ModelAttribute( "ricerca" ) BaseForm form,BindingResult result,Model model , HttpServletRequest request)
	{
//        String abilitaCamerilini = configParameterSource.getMessage("ABILITA_CAMERALINI", null, null);
//    	if(abilitaCamerilini!=null && abilitaCamerilini.equals("N")) { 
//			super.setMessage(Constants.MESSAGE, "error.funzione.in.fase.di.implementazione", null, false);
//			result.reject(Constants.MESSAGE);
//			ModelAndView mnv = new ModelAndView("/home");
//			return mnv;			
//		}
		
		return ricercaBase(model,"misuraPersonale",  new Long(1), request); //TODO terzo parametro è idTipoProcedimento ad oggi =1 da parametrizzare  in attesa di specifiche
	}
	
	// @SuppressWarnings("null")
	@RequestMapping( value = "/procedimento/nuovo/misuraPersonale/ricerca.htm", method = RequestMethod.POST, params = { "Ricerca" } )
	public ModelAndView elencoSoggetti( @Valid @ModelAttribute( "ricerca" ) MisurePrevenzioniPersonaleForm form, BindingResult result, ModelMap modelMap , HttpServletRequest request)
	{
		/****************** MOCK ****************/
		//int idTipoUfficio = (Integer) request.getSession().getAttribute("idTipoUfficio");
		//if(idTipoUfficio == 6)
		//{	form.getCognomeRichiedente().getBytes();
		//	form.getTrasmissione().getDescrizione();
		//}
		/*************** END MOCK ***************/
		
		ModelAndView mnv = new ModelAndView( "/cameralini/soggettiCauzioniPersPerm", "form", form );
		form.setFunzioneCameralini("misuraPersonale");
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
				Integer ufficioSel = Integer.valueOf(form.getUfficio().getId());
				form.setUfficioSelezionato(form.getUfficio().getDescrizione());
				request.getSession().setAttribute("idUfficioSel",ufficioSel);
		
				// verifica esistenza del procedimento con chiave: numero + anno + codiceUfficio
				ProcedimentoForm p = getProcedimentoPadre(ufficioSel, numero, anno,Constants.TIPOPROC_MODIFICA_PERSONALE_CAM);
				// ginesti se il risultato precedente  è null verifico solo se presente il procedimento
				// con chiave: numero + anno + codiceUfficio
				 ProcedimentoDto  solop = null;
				 if (p == null) { 
					  solop = procedimentoServiceCam.getProcedimentoByKeysProc(ufficioSel,numero,anno,Constants.TIPOPROC_MODIFICA_PERSONALE_CAM) ; 
					
				 }
				String error = checkMisuraPersApplicata(p,ufficioSel,solop);
				if(error != null){
					super.setMessage( Constants.MESSAGE, error, null, true );
					return	 mnv = new ModelAndView( "/cameralini/ricerca", "ricerca", form );
				}
				

				form.setProcedPadre(p);

				// si caricano in sessione i dati del procedimento in ProcedimentoForm 
				
				
				request.getSession().setAttribute(Constants.ID_PROCEDIMENTO,form.getProcedPadre());
	
				
				// una volta superati i controlli si recupera la lita dei soggetti interesati
				//List<SoggettoForm> listaSoggetti = caricalista(idUfficioUtente,form.getIdProcedimento(),new Long(idTipoProcedimento));
				List<SoggettoForm> listaSoggetti = procedimentoServiceCam.getSoggettiMisurePersProcedimento(ufficioSel,form.getProcedPadre().getIdProcedimento(),"P");
				  
				modelMap.addAttribute("listaSoggetti", listaSoggetti);
			}
		} else {
			return mnv = new ModelAndView("/cameralini/ricerca", "ricerca",
					form);
		}
				
		cleanBreadcrumb(request.getSession());
		addBreadcrumbs();
		return mnv;
		}
			
	@RequestMapping( value = "/procedimento/nuovo/misuraPersonale/ricerca.htm", method = RequestMethod.POST, params = { "Indietro" } )
	public ModelAndView indietro( MisurePrevenzioniPersonaleForm form, BindingResult result, ModelMap modelMap , HttpServletRequest request)
	{
			MenuController.caricaMenu( Constants.ID_K_MENU_PROCEDIMENTO, request );
			MisurePrevenzioniPersonaleForm misurePrevenzioniForm = new MisurePrevenzioniPersonaleForm();
			misurePrevenzioniForm.setFunzioneCameralini("misuraPersonale");
					
			ModelAndView mnv = new ModelAndView( "/cameralini/ricerca", "ricerca", misurePrevenzioniForm );
			cleanBreadcrumb(request.getSession());
			addBreadcrumbs();
			return mnv;
		}	

    
    @RequestMapping( value = "/procedimento/nuovo/misuraPersonale/ricerca.htm", method = RequestMethod.POST, params = {"Dettaglio"} )
    public ModelAndView soggettoSitMP( @Valid @ModelAttribute("form")MisurePrevenzioniPersonaleForm form, BindingResult result, ModelMap modelMap , HttpServletRequest request)
	{
    	addBreadcrumbs("Dettaglio", request);
    	
    	ModelAndView mnv = new ModelAndView( "/cameralini/soggettiCauzioniPersPerm", "form", form );
    	Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
    	Integer ufficioSel = (Integer)request.getSession().getAttribute("idUfficioSel");
    	String soggettoSel=form.getSoggettoSelected();
    	form.setFunzioneCameralini("misuraPersonale");
//    	TODO  idTipoProcedimento  da modificare  in attesa di specifiche;KKK
    	Long idTipoProcedimento = 1L;
    	if (soggettoSel != null) {
    	//SoggettoForm so = procedimentoServiceCam.getSogIntById(new Long(soggettoSel), ufficioSel.longValue(), form.getProcedPadre().getIdProcedimento());
    	
    				SoggettoForm soggettoDettaglio = new SoggettoForm();
	    			//List<SoggettoForm> lista = caricalista(idUfficioUtente,form.getProcedPadre().getIdProcedimento(),idTipoProcedimento);
    				List<SoggettoForm> lista = procedimentoServiceCam.soggettiFisiciDirettiInteressatiDelProcedimento(form.getProcedPadre().getIdProcedimento(), null, Constants.Flag.S);
	    			Iterator<SoggettoForm> iter = lista.iterator();
					while(iter.hasNext()) {
						SoggettoForm soggetto = iter.next();
						if (soggetto.getIdSoggetto().longValue() == new Long(soggettoSel).longValue()) {
							soggettoDettaglio.setIdSoggetto(soggetto.getIdSoggetto());
							soggettoDettaglio.setTipoSoggetto(soggetto.getTipoSoggetto());
							soggettoDettaglio.setIdVersione(soggetto.getIdVersione());
							
						}
					}		
					
			request.getSession().setAttribute(Constants.ID_UFFICIO_UTENTE,ufficioSel);
	    	mnv = soggettoController.direttiInteressatiDettaglio(soggettoDettaglio, result);
	    	request.getSession().setAttribute(Constants.ID_UFFICIO_UTENTE,idUfficioUtente);
	    	return mnv;
	    		
    		} else {
    		 super.setMessage( Constants.MESSAGE, "table.item.selection", null, false );
             result.reject( Constants.MESSAGE );
             
     		 //List<SoggettoForm> lista = caricalista(idUfficioUtente,form.getProcedPadre().getIdProcedimento(),idTipoProcedimento);
             List<SoggettoForm> lista = procedimentoServiceCam.soggettiFisiciDirettiInteressatiDelProcedimento(form.getProcedPadre().getIdProcedimento(), null, Constants.Flag.S);
     		 modelMap.addAttribute("listaSoggetti", lista);
             return	 mnv = new ModelAndView( "/cameralini/soggettiCauzioniPersPerm", "form", form );
    	}
    	
	}	
    
    @RequestMapping( value = "/procedimento/nuovo/misuraPersonale/ricerca.htm", method = RequestMethod.POST, params = {"Registra"} )
    public ModelAndView registra( @Valid @ModelAttribute( "form" )MisurePrevenzioniPersonaleForm form, BindingResult result, ModelMap modelMap , HttpServletRequest request)
	{
    	//si acquisiscono dati da sessione
    	Integer idUffOwn = (Integer) request.getSession().getAttribute("idUffOwner");
    	String codUtente = (String) request.getSession().getAttribute("codUtente");
    	Integer ufficioSel = (Integer)request.getSession().getAttribute("idUfficioSel");
    	ModelAndView mnv = new ModelAndView( "/cameralini/soggettiCauzioniPersPerm", "form", form );
    	form.setFunzioneCameralini("misuraPersonale");
    	List<AssociaPersonaDto> listaAssoc = null;
//    	TODO  idTipoProcedimento  da modificare  in attesa di specifiche;KKK
    	Long idTipoProcedimento = 1L;
    	Long idCatAtto = 1L;
    	
    	ProcedimentoForm procForm =new ProcedimentoForm();
    	if (!result.hasErrors()) {
			
  	//	  cameraliniValidation.validateDatiRichiedente(form, result);
  	//	  if (result.hasErrors()) {
  	//		  List<SoggettoForm> lista = procedimentoServiceCam.soggettiFisiciDirettiInteressatiDelProcedimento(form.getProcedPadre().getIdProcedimento(), null, Constants.Flag.S);
  	//			modelMap.addAttribute("listaSoggetti", lista);
  	//		  return mnv;
  	//	  }
  		Long idTipoAtto = 5L;
    	String soggettoSel=form.getSoggettoSelected();
     	if (soggettoSel != null) {
//        	SoggettoForm so = procedimentoServiceCam.getSogIntById(new Long(soggettoSel), ufficioSel.longValue(), form.getProcedPadre().getIdProcedimento());  		
    			//SoggettoForm soggettoDettaglio = new SoggettoForm();
    			//List<SoggettoForm> listaSoggetti = caricalista(idUffOwn,form.getProcedPadre().getIdProcedimento(),idTipoProcedimento);
    			List<SoggettoForm> listaSoggetti = procedimentoServiceCam.soggettiFisiciDirettiInteressatiDelProcedimento(form.getProcedPadre().getIdProcedimento(), null, Constants.Flag.S);
    			Iterator<SoggettoForm> iter = listaSoggetti.iterator();
    			List<SoggettoForm> listaSoggettiSel =new ArrayList<SoggettoForm>();
				while(iter.hasNext()) {
					SoggettoForm soggetto = iter.next();
					if (soggetto.getIdSoggetto().longValue() == new Long(soggettoSel).longValue()) {
//						soggettoDettaglio.setIdSoggetto(soggetto.getIdSoggetto());
//						soggettoDettaglio.setTipoSoggetto(soggetto.getTipoSoggetto());
//						soggettoDettaglio.setIdVersione(soggetto.getIdVersione());
						listaSoggettiSel.add(soggetto);
					}
				}						
			//List<SoggettoForm> listaSoggettiSel =new ArrayList<SoggettoForm>();
			//listaSoggettiSel.add(soggettoDettaglio);
				//imposto il ruolo ad 1 in attesa di sapere
			//listaSoggettiSel.get(0).setIdRuolo(1);
				
				int idTipoFascicolo = 6;
				Long statoProcedimento = new Long(2);
				int idTipoProc=14;

			
    		procForm = preparaNuovoProcedimento(form,idTipoProcedimento);
//    		List<AssociaPersonaDto> listaAssoc  = procedimentoServiceCam.inserisciProcedimentoCam(idUffOwn, codUtente, procForm, statoProcedimento, listaSoggettiSel, idTipoFascicolo,form.getProcedPadre().getIdProcedimento());
    		//commentato da gennaro
    		
    		/*listaAssoc = procedimentoServiceCam.inserisciProcedimentoCameralini(idUffOwn.intValue(),
					codUtente, procForm, statoProcedimento.longValue(), listaSoggettiSel,
					idTipoFascicolo, idTipoAtto, idCatAtto, form.getCognomeRichiedente(), form.getNomeRichiedente(), 
					form.getDataSottoscrizione(), form.getDataDeposito(), new Long(form.getTrasmissione().getId()), 
					form.getProcedPadre().getIdProcedimento());
					*/
    		listaAssoc = procedimentoServiceCam.inserisciProcedimentoCameraliniNew(idUffOwn.intValue(),
					codUtente, procForm, statoProcedimento.longValue(), listaSoggettiSel,
					idTipoFascicolo, idTipoAtto, idCatAtto, form.getCognomeRichiedente(), form.getNomeRichiedente(), 
					form.getDataSottoscrizione(), form.getDataDeposito(), new Long("0"), 
					form.getProcedPadre().getIdProcedimento(),idTipoProc);	
    		
    	} else {
    		 super.setMessage( Constants.MESSAGE, "table.item.selection", null, false );
             result.reject( Constants.MESSAGE );
             
     		 //List<SoggettoForm> lista = caricalista(idUffOwn,form.getProcedPadre().getIdProcedimento(),idTipoProcedimento);
             List<SoggettoForm> lista = procedimentoServiceCam.soggettiFisiciDirettiInteressatiDelProcedimento(form.getProcedPadre().getIdProcedimento(), null, Constants.Flag.S);
     		 modelMap.addAttribute("listaSoggetti", lista);
             return	 mnv = new ModelAndView( "/cameralini/soggettiCauzioniPersPerm", "form", form );
    	}
			request.getSession().setAttribute("idProcPadre",  form.getProcedPadre().getIdProcedimento());
			request.getSession().setAttribute("idProcFiglio", listaAssoc.get(0).getIDPROC().longValue());
			//commentato da gennaro
		    //	BaseForm base = (BaseForm) form;
	       //		return copertinaController.caricaDettaglioProcedimento(result, modelMap, base);
           //
			

			procForm.setIdProcedimento(listaAssoc.get(0).getIDPROC().longValue());
			request.getSession().setAttribute(Constants.ID_PROCEDIMENTO, procForm);
			//richiamo la copertina 
			return apriController.copertinaProcedimento(procForm, result, request);
			
			
		}else {
			List<SoggettoForm> lista = procedimentoServiceCam.soggettiFisiciDirettiInteressatiDelProcedimento(form.getProcedPadre().getIdProcedimento(), null, Constants.Flag.S);
			modelMap.addAttribute("listaSoggetti", lista);
			
			return mnv;
		}
		
	}	
}
