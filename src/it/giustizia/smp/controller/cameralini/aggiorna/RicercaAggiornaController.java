package it.giustizia.smp.controller.cameralini.aggiorna;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import it.giustizia.smp.controller.BreadcrumbsController;
import it.giustizia.smp.controller.MenuController;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.cameralini.AggiornaForm;
import it.giustizia.smp.service.cameralini.ProcedimentoServiceCam;
import it.giustizia.smp.utility.Constants;

@Controller
public class RicercaAggiornaController extends BreadcrumbsController
{
    @Autowired
    protected ProcedimentoServiceCam procedimentoServiceCam;

    protected ModelAndView ricercaBaseAggiorna( Model model, String funzione, HttpServletRequest request )
    {
        MenuController.caricaMenu( Constants.ID_K_MENU_PROCEDIMENTO, request );
        AggiornaForm form = new AggiornaForm();
        form.setProcedCamerale( new ProcedimentoForm() );
        form.setProcedPadre( new ProcedimentoForm() );

        form.setFunzioneCameralini( funzione );
        ModelAndView mnv = new ModelAndView( "/cameralini/ricercaPerAggiornamento", "ricerca", form );
        cleanBreadcrumb(request.getSession());
        addBreadcrumbs();
        return mnv;
    }

    // @RequestMapping( value = "/procedimento/aggiorna/correzione/ricerca.htm", method = RequestMethod.GET )
    @RequestMapping( value = "/procedimento/aggiorna/correzione/ricerca.htm", method = RequestMethod.GET )
    public ModelAndView ricercaCorrezioni( Model model, HttpServletRequest request )
    {
        return ricercaBaseAggiorna( model, " Correzione di errori materiali nei dati del soggetto", request );
    }

    // @RequestMapping( value = "/procedimento/aggiorna/misuraPersonale/ricerca.htm", method = RequestMethod.GET )
    @RequestMapping( value = "/procedimento/aggiorna/misuraPersonale/ricerca.htm", method = RequestMethod.GET )
    public ModelAndView ricercaMisurePersonali( Model model, HttpServletRequest request )
    {
        return ricercaBaseAggiorna( model, " Modifica/Revoca misure personali" , request);
    }

    // @RequestMapping( value = "/procedimento/aggiorna/cauzioni/ricerca.htm", method = RequestMethod.GET )
    @RequestMapping( value = "/procedimento/aggiorna/cauzioni/ricerca.htm", method = RequestMethod.GET )
    public ModelAndView ricercaCauzioni( Model model , HttpServletRequest request)
    {
        return ricercaBaseAggiorna( model, "Cauzioni" , request);
    }

    // @RequestMapping( value = "/procedimento/aggiorna/misuraPatrimoniale/ricerca.htm", method = RequestMethod.GET )
    @RequestMapping( value = "/procedimento/aggiorna/misuraPatrimoniale/ricerca.htm", method = RequestMethod.GET )
    public ModelAndView ricercaMisurePatrimoniali( Model model, HttpServletRequest request )
    {
        return ricercaBaseAggiorna( model, " Modifica/Revoca misure patrimoniali" , request);
    }

    // @RequestMapping( value = "/procedimento/aggiorna/permessi/ricerca.htm", method = RequestMethod.GET )
    @RequestMapping( value = "/procedimento/aggiorna/permessi/ricerca.htm", method = RequestMethod.GET )
    public ModelAndView ricercaPermessi( Model model , HttpServletRequest request)
    {
        return ricercaBaseAggiorna( model, "Permessi" , request);
    }

    // @RequestMapping( value = "/procedimento/aggiorna/terzi/ricerca.htm", method = RequestMethod.GET )
    @RequestMapping( value = "/procedimento/aggiorna/terzi/ricerca.htm", method = RequestMethod.GET )
    public ModelAndView ricercaTerzi( Model model , HttpServletRequest request)
    {
        return ricercaBaseAggiorna( model, " Terzo interveniente (666 cpp)" , request);
    }

    // @RequestMapping( value = "/procedimento/aggiorna/amministratore/ricerca.htm", method = RequestMethod.GET )
    @RequestMapping( value = "/procedimento/aggiorna/amministratore/ricerca.htm", method = RequestMethod.GET )
    public ModelAndView ricercaAmministratore( Model model , HttpServletRequest request)
    {
        return ricercaBaseAggiorna( model, " Liquidazione compensi amministratore" , request);
    }

    public void validateCriteriRicercaPuntuale( ProcedimentoForm procedimento, Errors errors )
    {

        if( procedimento.getAnno() == null )
        {
            errors.rejectValue( "procedCamerale.anno", "NotNull" );
        }
        if( procedimento.getNumero() == null )
        {
            errors.rejectValue( "procedCamerale.numero", "NotNull" );
        }
    }
    
    // @RequestMapping(value = "/procedimento/aggiorna/*/ricerca.htm", method = RequestMethod.POST, params = { "Ricerca" })
    @RequestMapping( value = "/procedimento/aggiorna/*/ricerca.htm", method = RequestMethod.POST, params = { "Ricerca" } )
    public ModelAndView elencoSoggetti( @Valid @ModelAttribute( "ricerca" ) AggiornaForm form, BindingResult result, ModelMap modelMap, HttpServletRequest request )
    {
        String funzioneCameralini = form.getFunzioneCameralini();
        Integer idUfficioUtente = (Integer)request.getSession().getAttribute( "idUffOwner" );

        Short anno = form.getProcedCamerale().getAnno();
        Long numero = form.getProcedCamerale().getNumero();
        if( anno != null || numero != null )
        {
            if( result.hasErrors() )
            {
                return new ModelAndView( "/cameralini/ricercaPerAggiornamento", "ricerca", form );
            }
            validateCriteriRicercaPuntuale( form.getProcedCamerale(), result );
            if( result.hasErrors() )
            {
                return new ModelAndView( "/cameralini/ricercaPerAggiornamento", "ricerca", form );
            }
        }

        if( anno != null && numero != null )
        {
            procedimentoServiceCam.getProcedimentoByKeys( form, idUfficioUtente, numero, anno,Constants.TIPOPROC_MODIFICA_PERSONALE_CAM );
            if( form.getProcedCamerale().getIdProcedimento() == null )
            {
                super.setMessage( Constants.MESSAGE, "cameralini.fascicoloInesistente", null, true );
                return new ModelAndView( "/cameralini/ricercaPerAggiornamento", "ricerca", form );
            }
            // controlli superati

            List<ProcedimentoForm> lista = new  ArrayList<ProcedimentoForm>();
            lista.add( form.getProcedCamerale() );
            
            ModelAndView mnv = new ModelAndView( "/cameralini/ricercaPerAggiornamento", "ricerca", form );
            mnv.addObject( "risultati", lista );
            return mnv;
        } else
        {
            //          List<ProcedimentoForm> lista = procedimentoServiceCam.getProcedimentoByCriteria( form, idUfficioUtente );
            //
            //          mnv.addObject( "risultati", lista );
            //
            //          List<ProcedimentoForm> risultati = (List)mnv.getModelMap().get( "risultati" );
            //
            //          //se l'elenco dei risultati produce un solo procedimento visualizzo la pagina del dettaglio del fascicolo
            //          if( risultati != null && risultati.size() == 1 )
            //          {
            //              ProcedimentoForm procedimento = risultati.get( 0 );
            //              mnv = (ApriController.)caricaDettaglioProcedimento( procedimento, result, request, "/procedimento/apri/ricercaProcedimento" );
            //          }

            //          procedimento.setAnno( null ); procedimento.setNumero( null );
            //          if( procedimento.getAnno() != null && procedimento.getNumero() != null )
            //          {
            //              procedimento.setNumeroMin( null ); procedimento.setNumeroMax( null );
            //          } 
        }
        cleanBreadcrumb(request.getSession());
        addBreadcrumbs();
        return new ModelAndView( "/cameralini/ricercaPerAggiornamento", "ricerca", form );
        //        return ricercaBaseAggiorna( model, "misurePatrimoniale" );
    }

}
