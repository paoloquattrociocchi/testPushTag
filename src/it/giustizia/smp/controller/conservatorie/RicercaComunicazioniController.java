package it.giustizia.smp.controller.conservatorie;

import it.giustizia.smp.controller.BreadcrumbsController;
import it.giustizia.smp.controller.MenuController;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.conservatorie.ComunicazioneListaForm;
import it.giustizia.smp.form.conservatorie.ComunicazioneRicercaForm;
import it.giustizia.smp.form.conservatorie.NotaDettaglioForm;
import it.giustizia.smp.service.CommonService;
import it.giustizia.smp.service.ConservatorieService;
import it.giustizia.smp.utility.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
public class RicercaComunicazioniController extends BreadcrumbsController
{
	@Autowired
	private CommonService        commonService;

	@Autowired
	private ConservatorieService conservatorieService;

    @ModelAttribute( "categorieAtti" )
    public List<ComboForm> getCategorieAtti( HttpServletRequest request )
    {
        return conservatorieService.getCategorieAtti( "ricerca" ,(Integer) request.getSession().getAttribute("idTipoUfficio"));
    }

	@ModelAttribute( "tipoNota" )
	public List<ComboForm> getTipoNota( HttpServletRequest request )
	{
		return commonService.getTipoNota();
	}

	@ModelAttribute( "conservatoria" )
	public List<ComboForm> getAllConservatoria( HttpServletRequest request )
	{
		return commonService.getAllConservatoria();
	}

 // @RequestMapping( value = "/conservatorie/visualizzaComunicazioniConservatoria.htm", method = RequestMethod.POST, params = { "Ricerca" } )
	@RequestMapping( value = "/conservatorie/visualizzaComunicazioniConservatoria.htm", method = RequestMethod.POST, params = { "Ricerca" } )
	public String ricercaComunicazioniConservatoria( ComunicazioneRicercaForm comunicazioneRicercaForm, BindingResult result, ModelMap modelMap , HttpServletRequest request)
	{
		String tipo;
		String categorieAtti;
		String conservatoria;
		try
		{
			tipo = comunicazioneRicercaForm.getTipoNota().getId();
			categorieAtti = comunicazioneRicercaForm.getCategorieAtti().getId();
			conservatoria = comunicazioneRicercaForm.getConservatoria().getId();
		} catch( Exception e )
		{
			comunicazioneRicercaForm = (ComunicazioneRicercaForm)request.getSession().getAttribute( "comunicazioneRicercaForm" );
			tipo = comunicazioneRicercaForm.getTipoNota().getId();
			categorieAtti = comunicazioneRicercaForm.getCategorieAtti().getId();
			conservatoria = comunicazioneRicercaForm.getConservatoria().getId();
		}
		Short anno = comunicazioneRicercaForm.getAnno();
		Long dalNumero = comunicazioneRicercaForm.getDalNumero();
		Long alNumero = comunicazioneRicercaForm.getAlNumero();
		String cognome = comunicazioneRicercaForm.getCognome();
		String nome = comunicazioneRicercaForm.getNome();
		String denominazione = comunicazioneRicercaForm.getDenominazione();

		if( ("-1".equals( tipo ) || tipo.isEmpty()) && ("-1".equals( categorieAtti ) || categorieAtti.isEmpty()) &&
		    (anno == null) && (dalNumero == null) && (alNumero == null)
		    && ("-1".equals( conservatoria ) || conservatoria.isEmpty()) &&
		    (cognome == null || cognome.isEmpty()) && (nome == null || nome.isEmpty())
		    && (denominazione == null || denominazione.isEmpty()) )
		{
			super.setMessage( Constants.MESSAGE, "conservatoria.error.selezione", null, false );
			result.reject( Constants.MESSAGE );
			return "/conservatorie/ricercaConservatoria";
		}
		if( "-1".equals( tipo ) )
		{
			tipo = null;
		}
		if( "-1".equals( categorieAtti ) || categorieAtti.indexOf( ';' ) < 0 )
		{
			categorieAtti = null;
		}else
		{
			categorieAtti = categorieAtti.substring( 0, categorieAtti.indexOf( ';' ) );
		}
		if( "-1".equals( conservatoria ) )
		{
			conservatoria = null;
		}
		if( dalNumero == null )
		{
			dalNumero = (long)0;
		}
		if( alNumero == null )
		{
			alNumero = (long)999999;
		}
		Integer idUfficioUtente = (Integer)request.getSession().getAttribute( "idUffOwner" );

		List<ComunicazioneListaForm> listaComunicazioniConservatoriaForm =
		    conservatorieService.listaComunicazioniConservatorie( tipo, categorieAtti, anno,
		        dalNumero, alNumero, conservatoria, cognome, nome, denominazione, idUfficioUtente );

		if( listaComunicazioniConservatoriaForm == null || listaComunicazioniConservatoriaForm.size() == 0 )
		{
			listaComunicazioniConservatoriaForm = new ArrayList<ComunicazioneListaForm>();
		}
		request.getSession().setAttribute( "comunicazioneRicercaForm", comunicazioneRicercaForm );
		request.getSession().setAttribute( "listaComunicazioni", listaComunicazioniConservatoriaForm );
		modelMap.addAttribute( "listaComunicazioniConservatoriaForm", listaComunicazioniConservatoriaForm );
		return "/conservatorie/ricercaConservatoria";
	}

 // @RequestMapping( value = "/conservatorie/aggiorna/ricerca.htm", method = RequestMethod.GET )
	@RequestMapping( value = "/conservatorie/aggiorna/ricerca.htm", method = RequestMethod.GET )
	public ModelAndView ricercaConservatoria( Model model, HttpServletRequest request )
	{
		MenuController.caricaMenu( Constants.ID_K_MENU_CONSERVATORIE, request );
		ComunicazioneRicercaForm conservatoriaForm = new ComunicazioneRicercaForm();
		ModelAndView mnv = new ModelAndView( "/conservatorie/ricercaConservatoria", "comunicazioneRicercaForm", conservatoriaForm );
		cleanBreadcrumb(request.getSession());
		addBreadcrumbs();
		return mnv;
	}

 // @RequestMapping( value = "/conservatorie/copertinaComunicazioniConservatoria.htm", method = RequestMethod.GET )
	@RequestMapping( value = "/conservatorie/copertinaComunicazioniConservatoria.htm", method = RequestMethod.GET )
	public ModelAndView copertina( Model model, HttpServletRequest request )
	{
		Long idNota = (Long)request.getSession().getAttribute( "idNota" );
		Integer idUfficioUtente = (Integer)request.getSession().getAttribute( "idUffOwner" );
		NotaDettaglioForm dettaglioNota = conservatorieService.dettaglioNota( idUfficioUtente, idNota );
		MenuController.caricaMenu( Constants.ID_K_MENU_CONSERVATORIE_AGGIORNA_COMUNICAZIONE, request );
		ModelAndView mnv = new ModelAndView( "/conservatorie/dettaglioNota", "conservatoriaDettaglioNotaForm", dettaglioNota );
		return mnv;
	}

 // @RequestMapping( value = "/conservatorie/dettaglioNota.htm", method = RequestMethod.GET )
	@RequestMapping( value = "/conservatorie/dettaglioNota.htm", method = RequestMethod.GET )
    public ModelAndView paginazioneDettaglio( ComunicazioneRicercaForm conservatoriaForm, BindingResult result, ModelMap modelMap, HttpServletRequest request )
	{
        return dettaglioNota( conservatoriaForm, result, modelMap, request );
	}

 // @RequestMapping( value = "/conservatorie/visualizzaComunicazioniConservatoria.htm", method = RequestMethod.GET )
    @RequestMapping( value = "/conservatorie/visualizzaComunicazioniConservatoria.htm", method = RequestMethod.GET )
    public String paginazioneRicerca( ComunicazioneRicercaForm conservatoriaForm, BindingResult result, ModelMap modelMap, HttpServletRequest request )
    {
        return ricercaComunicazioniConservatoria( conservatoriaForm, result, modelMap , request);
    }

	// @RequestMapping( value = "/conservatorie/visualizzaComunicazioniConservatoria.htm", method = RequestMethod.POST, params = { "Dettaglio" } )	
	@RequestMapping( value = "/conservatorie/visualizzaComunicazioniConservatoria.htm", method = RequestMethod.POST, params = { "Dettaglio" } )
	public ModelAndView dettaglioNota( ComunicazioneRicercaForm conservatoriaForm, BindingResult result, ModelMap modelMap, HttpServletRequest request )
	{
		String notaSel = conservatoriaForm.getNotaSelezionata();

		if( notaSel == null )
		{
			conservatoriaForm = (ComunicazioneRicercaForm)request.getSession().getAttribute( "comunicazioneRicercaForm" );
			List listaRisultati = (ArrayList)request.getSession().getAttribute( "listaComunicazioni" );
			modelMap.addAttribute( "comunicazioneRicercaForm", conservatoriaForm );
			modelMap.addAttribute( "listaComunicazioniConservatoriaForm", listaRisultati );

			super.setMessage( Constants.MESSAGE, "table.item.selection", null, false );
			result.reject( Constants.MESSAGE );
			// MenuController.caricaMenu( Constants.ID_K_MENU_CONSERVATORIE_AGGIORNA_COMUNICAZIONE, request );
			ModelAndView mnv = new ModelAndView( "/conservatorie/ricercaConservatoria", "listaComunicazioniConservatoriaForm", listaRisultati );
			return mnv;
		}
		String[] campi = StringUtils.split( notaSel, "'^'" );
		String idNotaString = campi[0];
		String idUffOwnerString = campi[1];
		Long idNota = new Long( idNotaString );
		Integer idUfficioUtente = new Integer( idUffOwnerString );
		NotaDettaglioForm dettaglioNota = conservatorieService.dettaglioNota( idUfficioUtente, idNota );

		if( dettaglioNota == null )
		{
			dettaglioNota = new NotaDettaglioForm();
		}

		request.getSession().setAttribute( "idNota", idNota );
		request.getSession().setAttribute( "idUffOwner", idUfficioUtente );

		MenuController.caricaMenu( Constants.ID_K_MENU_CONSERVATORIE_AGGIORNA_COMUNICAZIONE, request );
		ModelAndView mnv = new ModelAndView( "/conservatorie/dettaglioNota", "conservatoriaDettaglioNotaForm", dettaglioNota );
		return mnv;

	}

 // @RequestMapping( value = "/conservatorie/visualizzaComunicazioniConservatoria.htm", method = RequestMethod.POST, params = { "Indietro" } )
	@RequestMapping( value = "/conservatorie/visualizzaComunicazioniConservatoria.htm", method = RequestMethod.POST, params = { "Indietro" } )
	public String indietroDettaglioComunicazione( NotaDettaglioForm conservatoriaDettaglioNotaForm,
	    BindingResult result, ModelMap modelMap, HttpServletRequest request )
	{

		ComunicazioneRicercaForm conservatoriaForm = (ComunicazioneRicercaForm)request.getSession().getAttribute( "comunicazioneRicercaForm" );

		String tipo = conservatoriaForm.getTipoNota().getId();
		String categorieAtti = conservatoriaForm.getCategorieAtti().getId();
		String conservatoria = conservatoriaForm.getConservatoria().getId();
		Short anno = conservatoriaForm.getAnno();
		Long dalNumero = conservatoriaForm.getDalNumero();
		Long alNumero = conservatoriaForm.getAlNumero();
		String cognome = conservatoriaForm.getCognome();
		String nome = conservatoriaForm.getNome();
		String denominazione = conservatoriaForm.getDenominazione();

		Integer idUfficioUtente = (Integer)request.getSession().getAttribute( "idUffOwner" );

		if( "-1".equals( tipo ) )
		{
			tipo = null;
		}
		if( "-1".equals( categorieAtti ) )
		{
			categorieAtti = null;
		}else
		{
			categorieAtti = categorieAtti.substring( 0, categorieAtti.indexOf( ';' ) );
		}
		if( "-1".equals( conservatoria ) )
		{
			conservatoria = null;
		}
		if( dalNumero == null )
		{
			dalNumero = (long)0;
		}
		if( alNumero == null )
		{
			alNumero = (long)999999;
		}

		List<ComunicazioneListaForm> listaComunicazioniConservatoriaForm =
			    conservatorieService.listaComunicazioniConservatorie( tipo, categorieAtti, anno,dalNumero, alNumero, conservatoria, cognome, nome, denominazione, idUfficioUtente );

		if( listaComunicazioniConservatoriaForm == null || listaComunicazioniConservatoriaForm.size() == 0 )
		{
			listaComunicazioniConservatoriaForm = new ArrayList<ComunicazioneListaForm>();
		}

//		List listaRisultati = (ArrayList)request.getSession().getAttribute( "listaComunicazioni" );
//		if( listaRisultati == null || listaRisultati.size() == 0 )
//		{
//			listaRisultati = new ArrayList<ComunicazioneListaForm>();
//		}

		MenuController.caricaMenu( Constants.ID_K_MENU_CONSERVATORIE, request );
		modelMap.addAttribute( "comunicazioneRicercaForm", conservatoriaForm );
		modelMap.addAttribute( "listaComunicazioniConservatoriaForm", listaComunicazioniConservatoriaForm );
		return "/conservatorie/ricercaConservatoria";
	}
}