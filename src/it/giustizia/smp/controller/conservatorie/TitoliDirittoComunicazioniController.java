package it.giustizia.smp.controller.conservatorie;

import it.giustizia.smp.controller.BreadcrumbsController;
import it.giustizia.smp.controller.MenuController;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.conservatorie.ComunicazioneBeniForm;
import it.giustizia.smp.form.conservatorie.NotaDettaglioForm;
import it.giustizia.smp.service.CommonService;
import it.giustizia.smp.service.ConservatorieService;
import it.giustizia.smp.utility.Constants;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TitoliDirittoComunicazioniController extends BreadcrumbsController
{
    @Autowired
    private CommonService        commonService;

    @Autowired
    private ConservatorieService conservatorieService;

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

    @ModelAttribute( "categoriaCatastale" )
    public List<ComboForm> getCategorieCatastali( HttpServletRequest request )
    {
        return commonService.getCategorieCatastali();
    }

    @ModelAttribute( "listaTipiInteresse" )
    public List<ComboForm> getListaTipiInteresse( HttpServletRequest request )
    {
        return commonService.listaTipiInteresse();
    }

    @RequestMapping( value = "/conservatorie/titoliDirittoComunicazione.htm", method = RequestMethod.POST, params = { "IndietroListaBeni" } )
    public String indietroListaBeni( ComunicazioneBeniForm form, BindingResult result, ModelMap modelMap, HttpServletRequest request )
    {
        Long idNota = (Long)request.getSession().getAttribute( "idNota" );
        //	Integer idUffOwn = (Integer)request.getSession().getAttribute( "idUffOwner" );
        completaForm( form, idNota , request);
        modelMap.addAttribute( "comunicazioneBeniForm", form );
        String ritorno = "/conservatorie/elencoBeniComunicazione";
        return ritorno;
    }

    @RequestMapping( value = "/conservatorie/titoliDirittoComunicazione.htm", method = RequestMethod.POST, params = { "IndietroListaTitoli" } )
    public String indietroListaTitoli( ComunicazioneBeniForm form, BindingResult result, ModelMap modelMap , HttpServletRequest request)
    {
        Long idNota = (Long)request.getSession().getAttribute( "idNota" );
        Integer idUffOwn = (Integer)request.getSession().getAttribute( "idUffOwner" );
        completaForm( form, idNota, request );
        modelMap.addAttribute( "comunicazioneBeniForm", form );
        String ritorno = "/conservatorie/titoliDirittoComunicazione";
        form.setListaTitoliDiritto( conservatorieService.listaTitoliDiritto( form, idNota, idUffOwn ) );
        return ritorno;
    }

    private String decimaleToString( Long  decimale )
    {
        String millesimi = "";
        if( decimale != null && decimale != 0 )
        {
            millesimi = ("000" + decimale);
            millesimi = millesimi.substring( millesimi.length() - 3 );
            if( millesimi.charAt( 2 ) == '0' )
            {    millesimi = millesimi.substring( 0, 2 );
                if( millesimi.charAt( 1 ) == '0' )
                    millesimi = millesimi.substring( 0, 1 );
            }
        }
        return millesimi;
    }

    @RequestMapping( value = "/conservatorie/titoliDirittoComunicazione.htm", method = RequestMethod.POST, params = { "Modifica" } )
    public String modificaTitoloDiritto( ComunicazioneBeniForm form, BindingResult result, ModelMap modelMap , HttpServletRequest request)
    {
        form.setTipoOperazione( "Modifica" );
        String ritorno = "/conservatorie/modificaTitoloDiritto";
        Long idNota = (Long)request.getSession().getAttribute( "idNota" );
        Integer idUffOwn = (Integer)request.getSession().getAttribute( "idUffOwner" );
        if( !controlliModifica( form, result, request ) )
        {
            form.setTipoOperazione( "NotaInviata" );
            return ritorno;
        }
        if( form.getTitoloDirittoSelected() == null )
        {
            super.setMessage( Constants.MESSAGE, "table.item.selection", null, false );
            result.reject( Constants.MESSAGE );
            form.setListaTitoliDiritto( conservatorieService.listaTitoliDiritto( form, idNota, idUffOwn ) );
            completaForm( form, (Long)request.getSession().getAttribute( "idNota" ), request );
            return "/conservatorie/titoliDirittoComunicazione";
        }
        // CHIAMATA AL SERVICE
        String soggettoSel = form.getTitoloDirittoSelected();
        String[] campi = StringUtils.split( soggettoSel, "'^'" );
        String titoloSel = campi[0];
        Integer idTipoInteresse = Integer.valueOf( campi[1] );
        form.setIdTipoInt( idTipoInteresse );
        form.setTitoloDirittoSelected( titoloSel );

        form = conservatorieService.titoloDiritto( form, idNota, idUffOwn );
        String millesimi = decimaleToString( form.getNumeQuotaDec() );
        if( millesimi.length() > 0 )
            form.setNumeQuota( "" + form.getNumeQuotaInt() + "," + millesimi );
        else
            form.setNumeQuota( "" + form.getNumeQuotaInt() );
            
        return ritorno;
    }

    @RequestMapping( value = "/conservatorie/titoliDirittoComunicazione.htm", method = RequestMethod.POST, params = { "ConfermaModifica" } )
    public String confermaModificaTitoloDiritto( ComunicazioneBeniForm form, BindingResult result, ModelMap modelMap , HttpServletRequest request)
    {
        Long idNota = (Long)request.getSession().getAttribute( "idNota" );//id nota in sessione
        form.setTipoOperazione( "Modifica" );
        completaForm( form, idNota, request );
        String ritorno = "/conservatorie/modificaTitoloDiritto";
        if( !controllaQuota( form ) )
        {
            super.setMessage( Constants.MESSAGE, "conservatoria.error.numeratoreQuotaErrato", null, false );
            result.reject( Constants.MESSAGE );
            return ritorno;
        }
        if( !controlliConferma( form, result, request ) )
        {
            return ritorno;
        }
        Integer idUffOwn = (Integer)request.getSession().getAttribute( "idUffOwner" );
        String descUtente = (String)request.getSession().getAttribute( "codUtente" );
        Integer esito = conservatorieService.modificaTitoloDiritto( form, idNota, idUffOwn, descUtente );

        if( esito != 0 )
        {
            request.getSession().setAttribute( "idNota", idNota );
            super.setMessage( Constants.SUCCESS_MESSAGE, "success.modificaTitolo", " ", true );
            modelMap.addAttribute( "comunicazioneBeniForm", form );
        } else
        {
            super.setMessage( Constants.MESSAGE, "conservatoria.titolo.error.modificaTitolo", " ", true );
        }
        return ritorno;
    }

    private int mancanteModifica( BindingResult result, String params )
    {
        super.setMessage( Constants.MESSAGE, "conservatoria.error.campoObbligatorioNumerico", params, false );
        result.reject( Constants.MESSAGE );
        return 1;
    }

    private boolean controllaInserimento( String selezionato, String tipoInteresse, BindingResult result )
    {
        @SuppressWarnings( "unused" )
        int campiMancanti = 0;

        if( (selezionato == null) || selezionato.equals( "-1" ) )
        {
            super.setMessage( Constants.MESSAGE, "table.item.selection", null, false );
            result.reject( Constants.MESSAGE );
            return false;
        }
        if( tipoInteresse == null || tipoInteresse.equals( "-1" ) )
        {
            campiMancanti += mancante( result, "Tipo Interesse" );
            return false;
        }
        return true;
    }

    private boolean controlliConferma( ComunicazioneBeniForm form, BindingResult result, HttpServletRequest request )
    {
        @SuppressWarnings( "unused" )
        int campiMancanti = 0;

        // CAMPI OBBLIGATORI
        if( form.getNumeQuotaInt() == null || form.getNumeQuotaInt().equals( "" ) )
        {
            campiMancanti += mancanteModifica( result, "Numeratore Quota" );
            return false;
        }
        if( form.getNumeQuotaDec() == null || form.getNumeQuotaDec().equals( "" ) )
        {
            form.setNumeQuotaDec( new Long( 0 ) );
        }
        if( form.getNumeQuota() == null || form.getNumeQuota().equals( "" ) )
        {
            campiMancanti += mancanteModifica( result, " Numeratore Quota" );
            return false;
        }
        if( form.getNumeQuotaDen() == null || form.getNumeQuotaDen().equals( "" ) )
        {
            campiMancanti += mancanteModifica( result, "Denominatore Quota" );
            return false;
        }
        if( form.getNumeQuotaInt().doubleValue() < 0 )
        {
            super.setMessage( Constants.MESSAGE, "conservatoria.error.inserimentoTitoloNumeratore.negativo", " ", true );
            return false;
        }
        if( form.getNumeQuotaDec().doubleValue() < 0 )
        {
            super.setMessage( Constants.MESSAGE, "conservatoria.error.inserimentoTitoloDecimale.negativo", " ", true );
            return false;
        }
        if( form.getNumeQuotaDen().doubleValue() <= 0 )
        {
            super.setMessage( Constants.MESSAGE, "conservatoria.error.inserimentoTitoloFrazione.zero", " ", true );
            return false;
        }
        if( form.getNumeQuotaDec() == 0 && form.getNumeQuotaInt() == 0 )
        {
            super.setMessage( Constants.MESSAGE, "conservatoria.error.inserimentoTitoloFrazioneSup.zero", " ", true );
            return false;
        }
//        String numeratoreQuotaStringa = form.getNumeQuotaInt() + "." + form.getNumeQuotaDec();
//        String numeratoreQuotaStringa;
//        String millesimi = decimaleToString( form.getNumeQuotaDec() );
//        if( millesimi.length() > 0 )
//        	numeratoreQuotaStringa = "" + form.getNumeQuotaInt() + "." + millesimi ;
//        else
//        	numeratoreQuotaStringa = "" + form.getNumeQuotaInt();
//        
//        Double numeratoreQuota = new Double( numeratoreQuotaStringa ).doubleValue(); 
        
        Double numeratoreQuota = (double) form.getNumeQuotaInt() + (double) form.getNumeQuotaDec() / 1000;
        if( numeratoreQuota > form.getNumeQuotaDen() )
        {
            super.setMessage( Constants.MESSAGE, "conservatoria.error.inserimentoTitoloNumeratoreMaggioreDenominatore", " ", true );
            return false;
        }
        Long idNota = (Long)request.getSession().getAttribute( "idNota" );//id nota in sessione
        Integer idUffOwn = (Integer)request.getSession().getAttribute( "idUffOwner" );
        String descUtente = (String)request.getSession().getAttribute( "codUtente" );
        Long progrNotaBene = Long.valueOf( form.getBeneSelected() );
        if( form.getTitoloDirittoSelected() != null )
        {
            Long titoloSel = Long.valueOf( form.getTitoloDirittoSelected() );
            Integer idTipoInt = form.getIdTipoInt();
            Double capienza = conservatorieService.controllaCapienza( idNota, progrNotaBene, titoloSel, idTipoInt, idUffOwn, descUtente );
            if( numeratoreQuota / form.getNumeQuotaDen() + capienza > 1 )
            {
                super.setMessage( Constants.MESSAGE, "conservatoria.titolo.error.inserimentoTitoloCapienza", " ", true );
                return false;
            }
        }
        return true;
    }

    private boolean controlliModifica( ComunicazioneBeniForm form, BindingResult result, HttpServletRequest request )
    {
        @SuppressWarnings( "unused" )
        Long idNota = (Long)request.getSession().getAttribute( "idNota" );//id nota in sessione
        Integer idUffOwn = (Integer)request.getSession().getAttribute( "idUffOwner" );
        form = conservatorieService.dettaglioNotaBene( form, idNota, idUffOwn );
        if( form.getStato().equals( '2' ) )
        {
            super.setMessage( Constants.MESSAGE, "conservatoria.error.variazioneNonAmmessa", null, false );
            result.reject( Constants.MESSAGE );
            return false;
        }
        return true;
    }

  
    @RequestMapping( value = "/conservatorie/titoliDirittoComunicazione.htm", method = RequestMethod.POST, params = { "Nuovo" } )
    public String inserisciTitoliDiritto( ComunicazioneBeniForm form,
        BindingResult result, ModelMap modelMap , HttpServletRequest request)
    {
        String descUtente = (String)request.getSession().getAttribute( "codUtente" );//

        Long idNota = (Long)request.getSession().getAttribute( "idNota" );//id nota in sessione

        completaForm( form, idNota, request );
        form.setDescUtente( descUtente );
        String ritorno = "/conservatorie/inserimentoTitoliDiritto";
        modelMap.addAttribute( "comunicazioneBeniForm", form );

        return ritorno;
    }

    @RequestMapping( value = "/conservatorie/titoliDirittoComunicazione.htm", method = RequestMethod.GET )
    public ModelAndView titoliDiritto( ComunicazioneBeniForm form , HttpServletRequest request)
    {
        Long idNota = (Long)request.getSession().getAttribute( "idNota" );
        Integer idUffOwn = (Integer)request.getSession().getAttribute( "idUffOwner" );
        form.setTipoOperazione( "Lista" );
        form.setListaTitoliDiritto( conservatorieService.listaTitoliDiritto( form, idNota, idUffOwn ) );
        MenuController.caricaMenu( Constants.ID_K_MENU_CONSERVATORIE_AGGIORNA_COMUNICAZIONE, request );
        ModelAndView mnv = new ModelAndView( "/conservatorie/titoliDirittoComunicazione", "comunicazioneBeniForm", form );
        return mnv;
    }

    private void completaForm( ComunicazioneBeniForm form, Long idNota, HttpServletRequest request )
    {
        Integer idUfficioUtente = (Integer)request.getSession().getAttribute( "idUffOwner" );
        NotaDettaglioForm dettaglio = conservatorieService.dettaglioNota( idUfficioUtente, idNota );

        if( dettaglio != null )
        {
            form.setNumeroComunicazione( dettaglio.getNumero().longValue() );
            form.setStato( dettaglio.getStato() );
            form.setDescTipoNota( dettaglio.getDescTipoNota() );
            form.setDescAtto( dettaglio.getDescAtto() );
            form.setDescCatAtto( dettaglio.getDescCatAtto() );
            form.setRepertorioAnno( dettaglio.getRepertorioAnno() );
            form.setNumeroRepertorio1( dettaglio.getNumeroRepertorio1().longValue() );
            form.setProgressivoNota( dettaglio.getProgressivo() );
            form.setConservatoria( dettaglio.getConservatoria() );
            form.setConservatoriaId( dettaglio.getConservatoriaId() );
            form.setCodiceFiscale( dettaglio.getCodiceFiscale() );
            form.setTitoloDescrizione( dettaglio.getTitoloDescrizione() );
            form.setData( dettaglio.getData() );
            form.setListaBeni( dettaglio.getListaBeni() );
            form.setListaBeniAssociabili( dettaglio.getListaBeniAssociabili() );
            form.setListaSoggetti( dettaglio.getListaSoggetti() );

        }
    }

    //rm
    @RequestMapping( value = "/conservatorie/inserimentoTitoliDiritto.htm", method = RequestMethod.POST, params = { "ConfermaInserimento" } )
    public String confermaInserisciTitoliDiritto( ComunicazioneBeniForm form,
        BindingResult result, ModelMap modelMap, HttpServletRequest request )
    {

        String ritorno = "/conservatorie/inserimentoTitoliDiritto";
        modelMap.addAttribute( "comunicazioneBeniForm", form );
        form.setTipoOperazione( "Conferma" );

        Long idNota = (Long)request.getSession().getAttribute( "idNota" );//id nota in sessione
        completaForm( form, idNota, request );
        
        if( !controllaQuota( form ) )
        {
            super.setMessage( Constants.MESSAGE, "conservatoria.error.numeratoreQuotaErrato", null, false );
            result.reject( Constants.MESSAGE );
            return ritorno;
        }
        if( !controllaInserimento( form.getSoggettoSelected(), form.getListaTipiInteresse().getId(), result ) )
        {
            return ritorno;
        }
        if( !controlliConferma( form, result , request) )
        {
            return ritorno;
        }

        Integer idUffOwner = (Integer)request.getSession().getAttribute( "idUffOwner" );
        String descUtente = (String)request.getSession().getAttribute( "codUtente" );
        Long numeroComunicazione = form.getNumeroComunicazione();//idnota
        String tipoInteresse = form.getListaTipiInteresse().getId();
        String beneSelected = form.getBeneSelected();
        Long beneSelezionato = new Long( beneSelected );
        
        String soggSelezionato = form.getSoggettoSelected();
        soggSelezionato = form.getSoggettoSelected();
        Long progrSogg = new Long( soggSelezionato );
        Integer interesse = new Integer( tipoInteresse );

        Long numeratoreQuotaIntero = form.getNumeQuotaInt();
        Long numeratoreQuotaDecimale = 0L;
        Long denominatoreQuota = form.getNumeQuotaDen();

        if( (form.getNumeQuotaDec() != null) && !form.getNumeQuotaDec().equals( "" ) )
            numeratoreQuotaDecimale = form.getNumeQuotaDec();

        if( (numeratoreQuotaIntero == 0) && (numeratoreQuotaDecimale == 0) )
        {
            super.setMessage( Constants.MESSAGE, "conservatoria.error.inserimentoTitoloNumeratore.zero", " ", true );
            return ritorno;
        }
        if( numeratoreQuotaIntero < 0 )
        {
            super.setMessage( Constants.MESSAGE, "conservatoria.error.inserimentoTitoloNumeratore.negativo", " ", true );
            return ritorno;
        }
        if( numeratoreQuotaDecimale < 0 )
        {
            super.setMessage( Constants.MESSAGE, "conservatoria.error.inserimentoTitoloDecimale.negativo", " ", true );
            return ritorno;
        }
        if( denominatoreQuota <= 0 )
        {
            super.setMessage( Constants.MESSAGE, "conservatoria.error.inserimentoTitoloFrazione.zero", " ", true );
            return ritorno;
        }

        if( numeratoreQuotaIntero / denominatoreQuota > 1 )
        {
            super.setMessage( Constants.MESSAGE, "conservatoria.error.inserimentoTitoloNumeratoreMaggioreDenominatore", " ", true );
            return ritorno;
        }
        if( (numeratoreQuotaIntero / denominatoreQuota == 1) && (numeratoreQuotaDecimale > 0) )
        {
            super.setMessage( Constants.MESSAGE, "conservatoria.error.inserimentoTitoloNumeratoreMaggioreDenominatore", " ", true );
            return ritorno;
        }
        Integer numeroOccorenze = conservatorieService.controllaDuplicato( numeroComunicazione, beneSelezionato, progrSogg,
            interesse, idUffOwner, descUtente );

        if( numeroOccorenze > 0 )
        {
            super.setMessage( Constants.MESSAGE, "conservatoria.titolo.error.inserimentoTitoloOccorrenzaPresente", " ", true );
            return ritorno;
        }

        Double capienza = conservatorieService.controllaCapienza( numeroComunicazione, beneSelezionato, progrSogg,
            interesse, idUffOwner, descUtente );
        //check parte decimale

//        String numeratoreQuotaStringa = numeratoreQuotaIntero + "." + numeratoreQuotaDecimale;
//        Double numeratoreQuota = new Double( numeratoreQuotaStringa ).doubleValue();
        //		 if (numeratoreFrazioneQuotaIntero/denominatoreFrazioneQuota + capienza > 1){

        Double numeratoreQuota = (double) form.getNumeQuotaInt() + (double) form.getNumeQuotaDec() / 1000;
        if( numeratoreQuota / denominatoreQuota + capienza > 1 )
        {
            super.setMessage( Constants.MESSAGE, "conservatoria.titolo.error.inserimentoTitoloCapienza", " ", true );
            return ritorno;
        }

        //		 if ((numeratoreFrazioneQuotaIntero/denominatoreFrazioneQuota + capienza==1) &&(numeratoreFrazioneQuotaDecimale > 0) ){
        //			 super.setMessage( Constants.MESSAGE, "conservatoria.titolo.error.inserimentoTitoloCapienza", " ", true );
        //			 return ritorno;
        //		 }

        String esito = conservatorieService.inserisciTitoliDiritto( numeroComunicazione, beneSelezionato, progrSogg,
            interesse, numeratoreQuotaIntero, numeratoreQuotaDecimale, denominatoreQuota, idUffOwner, descUtente );

        if( esito.equals( "OK" ) )
        {
            request.getSession().setAttribute( "idNota", idNota );
            form.setTipoOperazione( "hoInserito" );
            super.setMessage( Constants.SUCCESS_MESSAGE, "success.insersciTitolo", " ", true );
            modelMap.addAttribute( "comunicazioneBeniForm", form );
            return ritorno;

        } else
        {
            super.setMessage( Constants.MESSAGE, "conservatoria.titolo.error.inserimentoTitolo", " ", true );
        }

        return ritorno;
    }

    private int mancante( BindingResult result, String params )
    {
        super.setMessage( Constants.MESSAGE, "conservatoria.error.campoObbligatorio", params, false );
        result.reject( Constants.MESSAGE );
        return 1;
    }

    private boolean controllaQuota( ComunicazioneBeniForm form )
    {
        if( form.getNumeQuota() == null || form.getNumeQuota().equals( "" ) )
            return false;
        String[] stringArray = form.getNumeQuota().split( "," );
        if( stringArray.length < 1 || stringArray.length > 2 )
            return false;
        if( stringArray[0].length() > 6 )
            return false;
        Long parteIntera = 0L, parteDecimale = 0L;
        try
        {
            parteIntera = Long.parseLong( stringArray[0] );
        } catch( NumberFormatException e )
        {
            // e.printStackTrace();
            return false;
        }
        if( stringArray.length == 2 )
        {
            if( stringArray[1].length() > 3 )
                return false;
            try
            {
                parteDecimale = Long.parseLong( (stringArray[1] + "000").substring( 0, 3 ) );
            } catch( NumberFormatException e )
            {
                // e.printStackTrace();
                return false;
            }
        }
        if( parteIntera < 0 || parteDecimale < 0 )
            return false;
        form.setNumeQuotaInt( parteIntera );
        form.setNumeQuotaDec( parteDecimale );
        return true;
    }

    @RequestMapping( value = "/conservatorie/inserimentoTitoliDiritto.htm", method = RequestMethod.POST, params = { "IndietroRicerca" } )
    public String indietroRicerca( ComunicazioneBeniForm form, BindingResult result, ModelMap modelMap, HttpServletRequest request )
    {

        Long idNota = (Long)request.getSession().getAttribute( "idNota" );
        Integer idUffOwn = (Integer)request.getSession().getAttribute( "idUffOwner" );
        completaForm( form, idNota, request );
        modelMap.addAttribute( "comunicazioneBeniForm", form );
        String ritorno = "/conservatorie/titoliDirittoComunicazione";
        form.setListaTitoliDiritto( conservatorieService.listaTitoliDiritto( form, idNota, idUffOwn ) );
        return ritorno;

    }

    //rmcancella
    @RequestMapping( value = "/conservatorie/titoliDirittoComunicazione.htm", method = RequestMethod.POST, params = { "Cancella" } )
    public String cancellaTitoliDiritto( ComunicazioneBeniForm form,
        BindingResult result, ModelMap modelMap , HttpServletRequest request)
    {
        form.setTipoOperazione( "Modifica" );
        String ritorno = "/conservatorie/modificaTitoloDiritto";

        modelMap.addAttribute( "comunicazioneBeniForm", form );

        Long idNota = (Long)request.getSession().getAttribute( "idNota" );
        Integer idUffOwner = (Integer)request.getSession().getAttribute(
            "idUffOwner" );
        String descUtente = (String)request.getSession().getAttribute(
            "codUtente" );

        String beneSelected = form.getBeneSelected();
        Long beneSelezionato = new Long( beneSelected );
        Long numeroComunicazione = form.getNumeroComunicazione();// idnota
        String soggettoSelected = form.getTitoloDirittoSelected();
        Long progrSogg = new Long( soggettoSelected );
        Integer interesse = form.getIdTipoInt();

        String esito = conservatorieService.cancellaTitoliDiritto(
            numeroComunicazione, beneSelezionato, progrSogg, interesse,
            idUffOwner, descUtente );

        if( esito.equals( "OK!!!" ) )
        {
            request.getSession().setAttribute( "idNota", idNota );
            super.setMessage( Constants.SUCCESS_MESSAGE,
                "success.cancellaTitolo", " ", true );
            modelMap.addAttribute( "comunicazioneBeniForm", form );
            form.setTipoOperazione( "hoCancellato" );

            //			completaForm(form, idNota);
            //			modelMap.addAttribute( "comunicazioneBeniForm", form );			
            //			form.setListaTitoliDiritto(conservatorieService.listaTitoliDiritto(form, idNota, idUffOwner));	
            //			ritorno = "/conservatorie/titoliDirittoComunicazione";

            return ritorno;

        } else
        {
            super.setMessage( Constants.MESSAGE,
                "conservatoria.titolo.error.cancellaTitolo", " ", true );
            form.setTipoOperazione( "Cancella" );
        }

        return ritorno;
    }

}