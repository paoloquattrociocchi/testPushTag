package it.giustizia.smp.controller.conservatorie;

import it.giustizia.smp.controller.BreadcrumbsController;
import it.giustizia.smp.controller.MenuController;
import it.giustizia.smp.dto.pg_conservatorie.SoggettoInteressatoDescDto;
import it.giustizia.smp.dto.pg_conservatorie.SoggettoInteressatoDto;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.conservatorie.ComunicazioneRipropAnnotaForm;
import it.giustizia.smp.form.conservatorie.NotaDettaglioForm;
import it.giustizia.smp.service.CommonService;
import it.giustizia.smp.service.ConservatorieService;
import it.giustizia.smp.utility.Constants;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public class RipropAnnotaComunicazioniController extends BreadcrumbsController
{
    @Autowired
    private CommonService                    commonService;

    @Autowired
    private ConservatorieService             conservatorieService;

    @Autowired
    private PreparazioneArchiviConservatoria preparazioneArchiviConservatoria;

    @ModelAttribute( "tipoComunic" )
    public List<ComboForm> getTipoComunic( HttpServletRequest request )
    {
        return conservatorieService.getTipoComunic();
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

    @RequestMapping( value = "/conservatorie/annotazioneComunicazione.htm", method = RequestMethod.POST, params = { "Annota" } )
    public String annotazioneComunicazione( ComunicazioneRipropAnnotaForm form, BindingResult result, ModelMap modelMap, HttpServletRequest request )
    {
        Integer idUfficioUtente = (Integer)request.getSession().getAttribute( "idUffOwner" );
        String descUtente = (String)request.getSession().getAttribute( "codUtente" );

        Long idNota = (Long)request.getSession().getAttribute( "idNota" );
        NotaDettaglioForm dettaglio = conservatorieService.dettaglioNota( idUfficioUtente, idNota );
        String ritorno = "/conservatorie/ripropAnnotaComunicazione";

        // CHIAMATA AL SERVICE
        Long idAnnotazione = conservatorieService.generaAnnotazione( dettaglio, idUfficioUtente, descUtente );
        completaForm( form, idAnnotazione, request );
        super.setMessage( Constants.SUCCESS_MESSAGE, "success.annotazione", " ", true );
        modelMap.addAttribute( "comunicazioneRipropAnnotaForm", form );
        return ritorno;
    }

    @RequestMapping( value = "/conservatorie/riproposizioneComunicazione.htm", method = RequestMethod.POST, params = { "Riproponi" } )
    public String riproproposizioneComunicazione( ComunicazioneRipropAnnotaForm form, BindingResult result, ModelMap modelMap , HttpServletRequest request)
    {
        Integer idUfficioUtente = (Integer)request.getSession().getAttribute( "idUffOwner" );
        String descUtente = (String)request.getSession().getAttribute( "codUtente" );

        Long idNota = (Long)request.getSession().getAttribute( "idNota" );
        NotaDettaglioForm dettaglio = conservatorieService.dettaglioNota( idUfficioUtente, idNota );
        String ritorno = "/conservatorie/ripropAnnotaComunicazione";

        // CHIAMATA AL SERVICE
        Long idRiproposizione = conservatorieService.riproposizione( dettaglio, idUfficioUtente, descUtente );
        super.setMessage( Constants.SUCCESS_MESSAGE, "success.riproposizione", " ", true );
        completaForm( form, idRiproposizione , request);
        modelMap.addAttribute( "comunicazioneRipropAnnotaForm", form );
        return ritorno;
    }

    @RequestMapping( value = "/conservatorie/esportazioneComunicazione.htm", method = RequestMethod.POST, params = { "Esporta" } )
    public String esportazioneComunicazione(
        ComunicazioneRipropAnnotaForm form, BindingResult result, ModelMap modelMap, HttpServletResponse response, HttpServletRequest request ) throws IOException
    {
        Integer idUfficioUtente = (Integer)request.getSession().getAttribute( "idUffOwner" );
        String descUtente = (String)request.getSession().getAttribute( "codUtente" );
        Long idNota = (Long)request.getSession().getAttribute( "idNota" );
        NotaDettaglioForm dettaglio = conservatorieService.dettaglioNota( idUfficioUtente, idNota );

        //Recupero la lista dei soggetti legati alle unità negoziali del bene
        List<SoggettoInteressatoDto> listaSoggettiUnitaNegDto = conservatorieService.listaSoggettiUnitaNegoziali( idNota, idUfficioUtente );
        List<SoggettoInteressatoDescDto> listaSoggettiUnitaNeg = new ArrayList<SoggettoInteressatoDescDto>();
        listaSoggettiUnitaNeg = conservatorieService.setListaSoggettiDesc(listaSoggettiUnitaNegDto);

        // String ritorno = "/conservatorie/ripropAnnotaComunicazione";
        completaForm( form, idNota, request);
        String xmlString = preparazioneArchiviConservatoria.generaXml( dettaglio, listaSoggettiUnitaNeg, idUfficioUtente, idNota );

        // Aggiorno lo stato della Nota a "INVIATA" = 2
        conservatorieService.aggiornaStatoNota( idUfficioUtente, descUtente, idNota, new Character('2')); // stato passa da 0 a 2
        
        OutputStream os = response.getOutputStream();
        response.setContentType( "text/xml" );
        response.setHeader( "Content-Disposition", "attachment;filename= " + "nota.xml" );
        response.setHeader( "Cache-control", "" );
        response.setHeader( "Pragma", "" );
        os.write( xmlString.getBytes() );
        return null;
    }

    @RequestMapping( value = "/conservatorie/esportazioneComunicazione.htm", method = RequestMethod.GET )
    public ModelAndView esportaComunicazione( Model model, HttpServletRequest request )
    {
        ComunicazioneRipropAnnotaForm form = new ComunicazioneRipropAnnotaForm();
        // Parametro per la gestione dell'operazione Annotazione = A /
        // Riproposizione = R / Esportazione = E
        form.setOperazione( "E" );
        Long idNota = (Long)request.getSession().getAttribute( "idNota" );
        completaForm( form, idNota , request);
        if( form.getStato().equals( '0' ) )
        {
            super.setMessage( Constants.MESSAGE, "conservatoria.error.statoIncoerente", null, false );
        }
        MenuController.caricaMenu( Constants.ID_K_MENU_CONSERVATORIE_AGGIORNA_COMUNICAZIONE, request );
        ModelAndView mnv = new ModelAndView( "/conservatorie/ripropAnnotaComunicazione", "comunicazioneRipropAnnotaForm", form );
        return mnv;
    }

    @RequestMapping( value = "/conservatorie/riproposizioneComunicazione.htm", method = RequestMethod.GET )
    public ModelAndView riproponiComunicazione( Model model , HttpServletRequest request)
    {
        ComunicazioneRipropAnnotaForm form = new ComunicazioneRipropAnnotaForm();
        // Parametro per la gestione dell'operazione Annotazione = A /
        // Riproposizione = R
        form.setOperazione( "R" );
        Long idNota = (Long)request.getSession().getAttribute( "idNota" );
        completaForm( form, idNota, request );
        if( !form.getStato().equals( '2' ) )
        {
            super.setMessage( Constants.MESSAGE, "conservatoria.error.statoIncoerente", null, false );
        }
        MenuController.caricaMenu( Constants.ID_K_MENU_CONSERVATORIE_AGGIORNA_COMUNICAZIONE, request );
        ModelAndView mnv = new ModelAndView( "/conservatorie/ripropAnnotaComunicazione", "comunicazioneRipropAnnotaForm", form );
        return mnv;
    }

    @RequestMapping( value = "/conservatorie/annotazioneComunicazione.htm", method = RequestMethod.GET )
    public ModelAndView annotaComunicazione( Model model, HttpServletRequest request )
    {
        ComunicazioneRipropAnnotaForm form = new ComunicazioneRipropAnnotaForm();
        // Parametro per la gestione dell'operazione Annotazione = A /
        // Riproposizione = R
        form.setOperazione( "A" );

        Long idNota = (Long)request.getSession().getAttribute( "idNota" );
        completaForm( form, idNota, request );

        if( !form.getStato().equals( '2' ) )
        {
            super.setMessage( Constants.MESSAGE, "conservatoria.error.statoIncoerente", null, false );
        }
        MenuController.caricaMenu( Constants.ID_K_MENU_CONSERVATORIE_AGGIORNA_COMUNICAZIONE, request );
        ModelAndView mnv = new ModelAndView( "/conservatorie/ripropAnnotaComunicazione", "comunicazioneRipropAnnotaForm", form );
        return mnv;
    }

    private void completaForm( ComunicazioneRipropAnnotaForm form, Long idNota, HttpServletRequest request )
    {
        Integer idUfficioUtente = (Integer)request.getSession().getAttribute( "idUffOwner" );
        NotaDettaglioForm dettaglio = conservatorieService.dettaglioNota( idUfficioUtente, idNota );

        if( dettaglio != null )
        {
            form.setNumeroComunicazione( dettaglio.getNumero() );
            form.setDescStato( dettaglio.getDescStato() );
            form.setTipoNotaId( dettaglio.getTipoNotaId() );
            form.setDescTipoNota( dettaglio.getDescTipoNota() );
            form.setDescCatAtto( dettaglio.getDescCatAtto() );
            form.setRepertorioAnno( dettaglio.getRepertorioAnno() );
            form.setNumero( dettaglio.getNumeroRepertorio1() );
            form.setProgressivo( dettaglio.getProgressivo() );
            form.setConservatoria( dettaglio.getConservatoria() );
            form.setCodiceFiscale( dettaglio.getCodiceFiscale() );
            form.setTitoloDescrizione( dettaglio.getTitoloDescrizione() );
            form.setDataTitolo( dettaglio.getData() );
            form.setPubblicoUfficiale( dettaglio.getPubblicoUfficiale() );
            form.setDescTipoPubblUff( dettaglio.getDescTipoPubblUff() );
            form.setCategorieAtti( dettaglio.getCategoria().toString() );
            form.setCategoria( dettaglio.getCategoria() );
            form.setAttoSpecie( dettaglio.getAttoSpecie() );
            form.setDescAtto( dettaglio.getDescAtto() );
            form.setSede( dettaglio.getSede() );
            form.setProvincia( dettaglio.getProvincia() );
            form.setImmobili( dettaglio.getImmobili().longValue() );
            form.setSoggettiContro( dettaglio.getSoggettiContro().longValue() );
            form.setSoggettiFavore( dettaglio.getSoggettiFavore().longValue() );
            form.setUnitaNegoziali( dettaglio.getUnitaNegoziali().longValue() );
            form.setStato( dettaglio.getStato() );
            form.setListaBeni( dettaglio.getListaBeni() );
            form.setListaSoggetti( dettaglio.getListaSoggetti() );
            // Dati ANNOTAZIONE
            if( dettaglio.getTipoNotaId().equals( 'A' ) )
            {
                form.setTrascrizioneDel( dettaglio.getTrascrizioneDel() );
                form.setNumeroTrascrizione1( dettaglio.getNumeroTrascrizione1() );
                form.setNumeroTrascrizione2( dettaglio.getNumeroTrascrizione2() );
//              form.setValoreImmobLib( dettaglio.getValoreImmobLib() );
//              form.setValoreImmobLibParteDecimale( dettaglio.getValoreImmobLibParteDecimale() );
                if( dettaglio.getValoreImmobLib() != null )
                {
                    String centesimi = ( "00" + dettaglio.getValoreImmobLibParteDecimale() ); 
                    centesimi = centesimi.substring( centesimi.length() - 2 );
                    form.setValoreImmobLibStr( dettaglio.getValoreImmobLib() + "," + centesimi );
                } else
                    form.setValoreImmobLibStr( "" );
                form.setVolturaCatastale( dettaglio.isVolturaCatastale() );
            }
            // Dati TRASCRIZIONE
            if( dettaglio.getTipoNotaId().equals( 'T' ) )
            {
                form.setDataFormalita( dettaglio.getDataFormalita() );
                form.setNumeroFormalita1( dettaglio.getNumeroFormalita1() );
                form.setNumeroFormalita2( dettaglio.getNumeroFormalita2() );
                form.setDataDal( dettaglio.getDataDal() );
                // Check
                form.setAttoSoggVoltura( dettaglio.isAttoSoggVoltura() );
                form.setVolturaCatastale( dettaglio.isVolturaCatastale() );
                form.setPresenzaCondizione( dettaglio.isPresenzaCondizione() );
                form.setPresenzaTermini( dettaglio.isPresenzaTermini() );
                form.setDifferita( dettaglio.isDifferita() );
            }
            // recuperato il valore di attoSpecie occorre recuperarne la descrizione    //rm		
            if( dettaglio.isQuadroA() )
                form.setQuadroATrascrizione( "OK" );
            else form.setQuadroATrascrizione( "NO" );
            if( dettaglio.isQuadroB() )
                form.setQuadroBTrascrizione( "OK" );
            else form.setQuadroBTrascrizione( "NO" );
            if( dettaglio.isQuadroC() )
                form.setQuadroCTrascrizione( "OK" );
            else form.setQuadroCTrascrizione( "NO" );
            //			comunicazioneRipropAnnotaForm.setQuadroA( dettaglio.isQuadroA() );
            //			comunicazioneRipropAnnotaForm.setQuadroB( dettaglio.isQuadroB() );
            //			comunicazioneRipropAnnotaForm.setQuadroC( dettaglio.isQuadroC() );
            //rm
            if( form.getTipoNotaId().equals( 'T' ) )
                form.setLabelSezAnn( "Trascrizione del " );
            if( form.getTipoNotaId().equals( 'A' ) )
                form.setLabelSezAnn( "Annotazione del " );
        }
    }
}