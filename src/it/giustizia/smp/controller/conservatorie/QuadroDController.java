package it.giustizia.smp.controller.conservatorie;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import it.giustizia.smp.controller.BreadcrumbsController;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.conservatorie.NotaDettaglioForm;
import it.giustizia.smp.service.CommonService;
import it.giustizia.smp.service.ConservatorieService;
import it.giustizia.smp.utility.Constants;

@Controller
public class QuadroDController extends BreadcrumbsController
{
	@Autowired
	private CommonService        commonService;

	@Autowired
	private ConservatorieService conservatorieService;

//	@ModelAttribute( "categorieAtti" )
//	public List<ComboForm> getCategorieAtti( HttpServletRequest request )
//	{
//		return conservatorieService.getCategorieAtti( "inserimento",(Integer) request.getSession().getAttribute("idTipoUfficio") );
//	}

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

	@RequestMapping( value = "/conservatorie/quadroDConservatorie.htm", method = RequestMethod.GET )
	public ModelAndView visualizzaConservatorie( Model model, HttpServletRequest request )
	{
		return mostraQuadroD( model, '0', request );
	}

	@RequestMapping( value = "/conservatorie/quadroDCatasto.htm", method = RequestMethod.GET )
	public ModelAndView visualizzaQuadroDCatasto( Model model, HttpServletRequest request )
	{
		return mostraQuadroD( model, '1', request  );
	}

	private ModelAndView mostraQuadroD( Model model, Character tipoInfo, HttpServletRequest request )
	{
		NotaDettaglioForm form;
		Long idNota = (Long)request.getSession().getAttribute( "idNota" );
		Integer idUfficioUtente = (Integer)request.getSession().getAttribute( "idUffOwner" );
		form = conservatorieService.dettaglioNota( idUfficioUtente, idNota );
		if( form != null )
		{
			form.setTipoInf( tipoInfo );
			List<String> osservazioniNota = conservatorieService.cercaOsservazioniNota( idUfficioUtente, idNota, tipoInfo );
			if( osservazioniNota != null && !osservazioniNota.isEmpty() )
			{
				StringBuffer sb = new StringBuffer();
				for( Iterator<String> iterator = osservazioniNota.iterator(); iterator.hasNext(); )
				{
					String osserv = (String)iterator.next();
					sb.append( osserv );
				}
				form.setQuadroD( sb.toString() );
				form.setQuadroDPresente( true );
				form.setOldQuadroD( form.getQuadroD() );
			} else
			{
				form.setQuadroDPresente( false );
				form.setOldQuadroD( null );
			}
			ModelAndView mnv = new ModelAndView( "/conservatorie/visualizzaQuadroD", "comunicazioneQuadroDForm", form );
			return mnv;
		}
		return null;
	}

	@RequestMapping( value = "/conservatorie/quadroDConservatorie.htm", method = RequestMethod.POST,
	    params = { "Conferma" } )
	public ModelAndView inserisciQuadroDConservatorie( NotaDettaglioForm form, BindingResult result, ModelMap modelMap, HttpServletRequest request )
	{
		return inserisciQuadroD( form, result, modelMap, '0' , request );
	}

	@RequestMapping( value = "/conservatorie/quadroDCatasto.htm", method = RequestMethod.POST,
	    params = { "Conferma" } )
	public ModelAndView inserisciQuadroDCatasto( NotaDettaglioForm form, BindingResult result, ModelMap modelMap, HttpServletRequest request )
	{
		return inserisciQuadroD( form, result, modelMap, '1', request  );
	}

	@RequestMapping( value = "/conservatorie/quadroDConservatorie.htm", method = RequestMethod.POST,
	    params = { "Cancella" } )
	public ModelAndView cancellaQuadroDConservatorie( NotaDettaglioForm form, BindingResult result, ModelMap modelMap, HttpServletRequest request )
	{
		return cancellaQuadroD( form, result, modelMap, '0', request  );
	}

	@RequestMapping( value = "/conservatorie/quadroDCatasto.htm", method = RequestMethod.POST,
	    params = { "Cancella" } )
	public ModelAndView cancellaQuadroDCatasto( NotaDettaglioForm form, BindingResult result, ModelMap modelMap , HttpServletRequest request)
	{
		return cancellaQuadroD( form, result, modelMap, '1', request  );
	}

	private ModelAndView cancellaQuadroD( NotaDettaglioForm form, BindingResult result, ModelMap modelMap, char c , HttpServletRequest request)
	{
		Long idNota = (Long)request.getSession().getAttribute( "idNota" );
		Integer idUfficioUtente = (Integer)request.getSession().getAttribute( "idUffOwner" );
		String descUtente = (String)request.getSession().getAttribute( "codUtente" );
		String ret = conservatorieService.cancellaQuadroD(
		    idUfficioUtente, descUtente, idNota, form.getTipoInf(), form.getStato() );
		super.setMessage( Constants.SUCCESS_MESSAGE, "success.cancellaQuadroD", " ", true );
		form.setQuadroDPresente( false );
		form.setOldQuadroD( null ); form.setQuadroD( null );
		ModelAndView mnv = new ModelAndView( "/conservatorie/visualizzaQuadroD", "comunicazioneQuadroDForm", form );
		return mnv;
	}

	private ModelAndView inserisciQuadroD( NotaDettaglioForm form, BindingResult result, ModelMap modelMap, Character tipoInfo, HttpServletRequest request )
	{
		Long idNota = (Long)request.getSession().getAttribute( "idNota" );
		Integer idUfficioUtente = (Integer)request.getSession().getAttribute( "idUffOwner" );
		String descUtente = (String)request.getSession().getAttribute( "codUtente" );
		String errore = "";
		if( !idNota.equals( form.getNumero() ) )
		    errore = "idNota errato";
		if( form.getQuadroD() == null || form.getQuadroD().length() == 0 )
		{
			form.setQuadroD( form.getOldQuadroD() ); 
			super.setMessage( Constants.MESSAGE, "conservatoria.error.QuadroD.vuoto", " ", true );
		} else
		{
			Long ret = conservatorieService.inserisciQuadroD(
			    idUfficioUtente, descUtente, idNota, form.getTipoInf(), form.getQuadroD(), form.getStato() );
			if( ret.equals( idNota ) )
			{
				super.setMessage( Constants.SUCCESS_MESSAGE, "success.modificaQuadroD", " ", true );
				form.setQuadroDPresente( true );
				form.setOldQuadroD( form.getQuadroD() );
			}
			else //  istruzioni da eliminare 
				super.setMessage( Constants.MESSAGE, "conservatoria.error.QuadroD", " ", true );
		}
		ModelAndView mnv = new ModelAndView( "/conservatorie/visualizzaQuadroD", "comunicazioneQuadroDForm", form );
		return mnv;
	}

}