package it.giustizia.smp.controller.cameralini;

import it.giustizia.smp.controller.MenuController;
import it.giustizia.smp.controller.procedimento.ApriController;
import it.giustizia.smp.dto.pg_procedimento.AssociaPersonaDto;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.form.cameralini.CauzioniForm;
import it.giustizia.smp.utility.Constants;

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
public class RicercaCauzioniController extends RicercaBaseController
{
	@Autowired
	private ApriController 	apriController;
	@RequestMapping( value = "/procedimento/nuovo/cauzioni/ricerca.htm", method = RequestMethod.GET )
	public ModelAndView ricerca ( Model model , HttpServletRequest request)
	{
		return ricercaBase(model,"cauzioni", Constants.TIPOPROC_CAUZIONI_CAM, request); 
	}
	
	@RequestMapping( value = "/procedimento/nuovo/cauzioni/indietroDaDettaglio.htm", method = RequestMethod.GET )
	public ModelAndView indietroDaDettaglio( @Valid @ModelAttribute( "ricerca" ) CauzioniForm form, BindingResult result, ModelMap modelMap , HttpServletRequest request) {
		//Long anno = form.getProcedPadre().setAnno().longValue();
		//Long numero = form.getProcedPadre().getNumero();
		form.setProcedPadre((ProcedimentoForm)request.getSession().getAttribute("ProcedimentoPadre"));
		return elencoSoggetti(form,result,modelMap, request);
	}

	// @SuppressWarnings("null")
	@RequestMapping( value = "/procedimento/nuovo/cauzioni/ricerca.htm", method = RequestMethod.POST, params = { "Ricerca" } )
	public ModelAndView elencoSoggetti( @Valid @ModelAttribute( "ricerca" ) CauzioniForm form, BindingResult result, ModelMap modelMap, HttpServletRequest request )
		{
		/****************** MOCK ****************/
		//int idTipoUfficio = (Integer) request.getSession().getAttribute("idTipoUfficio");
		//if(idTipoUfficio == 6)
		//{	form.getCognomeRichiedente().getBytes();
		//	form.getTrasmissione().getDescrizione();
		//}
		/*************** END MOCK ***************/
			ModelAndView mnv = new ModelAndView( "/cameralini/soggettiCauzioniPersPerm", "form", form );
			form.setFunzioneCameralini("cauzioni");
			if (!result.hasErrors()) {
				// INIZIO CONTROLLI VALIDATE CAMERALINI
				ProcedimentoForm procedimento = form.getProcedPadre();
				cameraliniValidation.validateCriteriRicercaPuntuale(procedimento, result);

				if (result.hasErrors()) {
					return new ModelAndView("/cameralini/ricerca", "ricerca", form);

				} else {
					
					// recupero dati ufficio da sessione
					Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");				
					form.setFunzioneCameralini("cauzioni");
					Long anno = form.getProcedPadre().getAnno().longValue();
					Long numero = form.getProcedPadre().getNumero();
					request.getSession().setAttribute("idUfficioUtente",idUfficioUtente);
			
					// verifica esistenza del procedimento con chiave: numero + anno + codiceUfficio
					ProcedimentoForm p = getProcedimentoPadre(idUfficioUtente, numero, anno,Constants.TIPOPROC_CAUZIONI_CAM);
					
					String error = checkCauzApplicata(p,idUfficioUtente);
					if(error != null){
						super.setMessage( Constants.MESSAGE, error, null, true );
						return	 mnv = new ModelAndView( "/cameralini/ricerca", "ricerca", form );
					}				
					form.setProcedPadre(p);
			
					// si caricano in sessione i dati del procedimento in ProcedimentoForm 
					request.getSession().setAttribute(Constants.ID_PROCEDIMENTO,form.getProcedPadre());
		
					
					// una volta superati i controlli si recupera la lita dei soggetti interessati
					List<SoggettoForm> listaSoggetti = procedimentoServiceCam.getSoggettiMisurePersProcedimento(idUfficioUtente,form.getProcedPadre().getIdProcedimento(),"C");  
					  
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
	
	@RequestMapping( value = "/procedimento/nuovo/cauzioni/ricerca.htm", method = RequestMethod.POST, params = { "Indietro" } )
	public ModelAndView indietro( CauzioniForm form, BindingResult result, ModelMap modelMap, HttpServletRequest request )
	{
			MenuController.caricaMenu( Constants.ID_K_MENU_PROCEDIMENTO, request );
			CauzioniForm cauzioniForm = new CauzioniForm();
			cauzioniForm.setFunzioneCameralini("cauzioni");
					
			ModelAndView mnv = new ModelAndView( "/cameralini/ricerca", "ricerca", cauzioniForm );
			cleanBreadcrumb(request.getSession());
			addBreadcrumbs();
			return mnv;
		}	
	
	@RequestMapping(value = "/procedimento/nuovo/cauzioni/ricerca.htm", method = RequestMethod.POST, params = { "Dettaglio" })
	public ModelAndView soggettoSitMP(@Valid @ModelAttribute("form") CauzioniForm form, BindingResult result, ModelMap modelMap, HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView("/cameralini/soggettiCauzioniPersPerm", "form", form);
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
		// Integer ufficioSel =
		// (Integer)request.getSession().getAttribute("idUfficioSel");
		String soggettoSel = form.getSoggettoSelected();
		form.setFunzioneCameralini("cauzioni");
		if (soggettoSel != null) {
			// SoggettoForm so = procedimentoServiceCam.getSogIntById(new
			// Long(soggettoSel), ufficioSel.longValue(),
			// form.getProcedPadre().getIdProcedimento());

			SoggettoForm soggettoDettaglio = new SoggettoForm();
			List<SoggettoForm> lista = procedimentoServiceCam.getSoggettiMisurePersProcedimento(idUfficioUtente, form.getProcedPadre().getIdProcedimento(),
					"C");
			Iterator<SoggettoForm> iter = lista.iterator();
			while (iter.hasNext()) {
				SoggettoForm soggetto = iter.next();
				if (soggetto.getIdSoggetto().longValue() == new Long(soggettoSel).longValue()) {
					soggettoDettaglio.setIdSoggetto(soggetto.getIdSoggetto());
					soggettoDettaglio.setTipoSoggetto(soggetto.getTipoSoggetto());
					soggettoDettaglio.setIdVersione(soggetto.getIdVersione());

				}
			}
			//addBreadcrumbs("/procedimento/nuovo/cauzioni/ricerca.htm");
			request.getSession().setAttribute("SovrascitturaIndietro","/procedimento/nuovo/cauzioni/indietroDaDettaglio.htm");
			request.getSession().setAttribute("ProcedimentoPadre",form.getProcedPadre());
			
			mnv = soggettoController.direttiInteressatiDettaglio(soggettoDettaglio, result);
			return mnv;

		} else {
			super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
			result.reject(Constants.MESSAGE);

			List<SoggettoForm> lista = procedimentoServiceCam.getSoggettiMisurePersProcedimento(idUfficioUtente, form.getProcedPadre().getIdProcedimento(),
					"C");
			modelMap.addAttribute("listaSoggetti", lista);
			return mnv = new ModelAndView("/cameralini/soggettiCauzioniPersPerm", "form", form);
		}

	}
	
	@RequestMapping( value = "/procedimento/nuovo/cauzioni/ricerca.htm", method = RequestMethod.POST, params = {"Registra"} )
    public ModelAndView registra( @Valid @ModelAttribute( "form" )CauzioniForm form, BindingResult result, ModelMap modelMap , HttpServletRequest request)
	{
    	//si acquisiscono dati da sessione
    	Integer idUffOwn = (Integer) request.getSession().getAttribute("idUffOwner");
    	String codUtente = (String) request.getSession().getAttribute("codUtente");
    	//Integer ufficioSel = (Integer)request.getSession().getAttribute("idUfficioSel");
    	form.setFunzioneCameralini("cauzioni");
    	ModelAndView mnv = new ModelAndView( "/cameralini/soggettiCauzioniPersPerm", "form", form );
    	List<AssociaPersonaDto> listaAssoc = null;
//    	TODO  idTipoProcedimento  da modificare  in attesa di specifiche;KKK
    	Long idTipoProcedimento = Constants.TIPOPROC_CAUZIONI_CAM;
    	Long idCatAtto = 1L;
    	ProcedimentoForm procForm =new ProcedimentoForm();
    	
    	
  		Long idTipoAtto = 5L;
    	String soggettoSel=form.getSoggettoSelected();
     	if (soggettoSel != null) {
//        	SoggettoForm so = procedimentoServiceCam.getSogIntById(new Long(soggettoSel), ufficioSel.longValue(), form.getProcedPadre().getIdProcedimento());  		
     		List<SoggettoForm> listaSoggetti = procedimentoServiceCam.getSoggettiMisurePersProcedimento(idUffOwn,form.getProcedPadre().getIdProcedimento(),"C");
    			Iterator<SoggettoForm> iter = listaSoggetti.iterator();
    			List<SoggettoForm> listaSoggettiSel =new ArrayList<SoggettoForm>();
				while(iter.hasNext()) {
					SoggettoForm soggetto = iter.next();
					if (soggetto.getIdSoggetto().longValue() == new Long(soggettoSel).longValue()) {
						listaSoggettiSel.add(soggetto);
					}
				}						
				int idTipoFascicolo = 6;
//				Long statoProcedimento = new Long(2);

			
    		procForm = preparaNuovoProcedimento(form,idTipoProcedimento);
    		listaAssoc = procedimentoServiceCam.inserisciProcedimentoCameraliniNew(idUffOwn.intValue(),
					codUtente, procForm, Constants.STATOPROC_IN_LAVORAZIONE, listaSoggettiSel,
					idTipoFascicolo, idTipoAtto, idCatAtto, null, null, 
					null, null, null, 
					form.getProcedPadre().getIdProcedimento(),idTipoProcedimento.intValue());
    		
    	} else {
    		 super.setMessage( Constants.MESSAGE, "table.item.selection", null, false );
             result.reject( Constants.MESSAGE );
             
             List<SoggettoForm> lista = procedimentoServiceCam.getSoggettiMisurePersProcedimento(idUffOwn,form.getProcedPadre().getIdProcedimento(),"C");
     		 modelMap.addAttribute("listaSoggetti", lista);
             return	 mnv = new ModelAndView( "/cameralini/soggettiCauzioniPersPerm", "form", form );
    	}
			request.getSession().setAttribute("idProcPadre",  form.getProcedPadre().getIdProcedimento());
			request.getSession().setAttribute("idProcFiglio", listaAssoc.get(0).getIDPROC().longValue());
			procForm.setIdProcedimento(listaAssoc.get(0).getIDPROC().longValue());
			// si caricano in sessione i dati del procedimento FIGLIO in SESSIONE PER VISUALIZZARE IN COPERTINA IL NUOVO PROC
			request.getSession().setAttribute(Constants.ID_PROCEDIMENTO, procForm);
			//richiamo la copertina 
			return apriController.copertinaProcedimento(procForm, result, request);
     
		
	}	
}