package it.giustizia.smp.controller.conservatorie;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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

import it.giustizia.smp.controller.BreadcrumbsController;
import it.giustizia.smp.controller.procedimento.SoggettoController;
import it.giustizia.smp.dto.pg_conservatorie.SoggettoInteressatoDescDto;
import it.giustizia.smp.dto.pg_conservatorie.SoggettoInteressatoDto;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.form.conservatorie.ComunicazioneSoggettiForm;
import it.giustizia.smp.form.conservatorie.NotaDettaglioForm;
import it.giustizia.smp.service.CommonService;
import it.giustizia.smp.service.ConservatorieService;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;

@Controller
public class ElencoSoggettiComunicazioniController extends BreadcrumbsController
{
    @Autowired
    private CommonService        commonService;

    @Autowired
    private ConservatorieService conservatorieService;
    
    @Autowired
    private SoggettoController soggettoController;

    @ModelAttribute( "comboRegime" )
    public List<ComboForm> getTipoRegime( HttpServletRequest request )
    {
        return commonService.getTipoRegime();
    }

    @ModelAttribute( "tipoComunic" )
    public List<ComboForm> getTipoComunic( HttpServletRequest request )
    {
        return conservatorieService.getTipoComunic();
    }
    
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

    @ModelAttribute( "comboStatiIstat" )
    public List<ComboForm> getAllStatiIstat( HttpServletRequest request )
    {
        return commonService.getAllDescIstatStato();
    }

    @ModelAttribute( "comboStatiBelfiore" )
    public List<ComboForm> getAllStatiBelf( HttpServletRequest request )
    {
        return commonService.getAllDescBelfStato();
    }

    private void completaForm( ComunicazioneSoggettiForm form, NotaDettaglioForm dettaglio )
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
        form.setCodiceFiscale( dettaglio.getCodiceFiscale() );
        form.setTitoloDescrizione( dettaglio.getTitoloDescrizione() );
        form.setData( dettaglio.getData() );
        form.setNumeroProcedimento(dettaglio.getNumeroProcedimento());
        form.setListaSoggetti( dettaglio.getListaSoggetti() );
    }
    
    /** @RequestMapping( value = "/conservatorie/soggettiComunicazione.htm", method = RequestMethod.POST, params = { "Associa" } ) */
    @RequestMapping( value = "/conservatorie/soggettiComunicazione.htm", method = RequestMethod.POST, params = { "Associa" } )
    public String associa( ComunicazioneSoggettiForm form, BindingResult result, ModelMap modelMap , HttpServletRequest request )
    {
        String soggettoSel = form.getSoggettoAssociabileSelected();
        if( soggettoSel == null )
        {
            super.setMessage( Constants.MESSAGE, "table.item.selection", null, false );
            result.reject( Constants.MESSAGE );
            return indietroElencoSoggetti( form, result, modelMap, request );
        }
        Long idNota = (Long)request.getSession().getAttribute( "idNota" );
        Integer idUffOwn = (Integer)request.getSession().getAttribute( "idUffOwner" );
        String descUtente = (String)request.getSession().getAttribute( "codUtente" );

        String[] campi = StringUtils.split( soggettoSel, "'^'" );
        Long idSoggetto = new Long( campi[0] );
        Character tipoSoggetto = campi[1].charAt( 0 );
        Integer versione = Integer.valueOf( campi[2] );
        if( !tipoSoggetto.equals( 'F' ) && !tipoSoggetto.equals( 'G' ) )
        {
            super.setMessage( Constants.MESSAGE, "conservatoria.error.tipoSoggetto", null, false );
            return indietroElencoSoggetti( form, result, modelMap, request );
        }
        SoggettoInteressatoDto dettaglioPers = null;
        if( tipoSoggetto.equals( 'F' ) )
        {
            dettaglioPers = conservatorieService.dettaglioPersonaFisica( idSoggetto, idUffOwn, versione );
        }else
        {
        	tipoSoggetto='N';
            dettaglioPers = conservatorieService.dettaglioPersonaGiurid( idSoggetto, idUffOwn, versione );
        }
        if( dettaglioPers == null )
        {
            super.setMessage( Constants.MESSAGE, "conservatoria.error.SoggettoInesistente", null, false ); 
            return indietroElencoSoggetti( form, result, modelMap , request);
        }
        boolean controlliOk = false;
        if( tipoSoggetto.equals( 'F' ) )
        {
            String cognome = dettaglioPers.getCOGNOME();
            String nome = dettaglioPers.getNOME();
            Character sesso = dettaglioPers.getFLGSESSO();
            String codFiscale = dettaglioPers.getCODICEFISCALEPF();
            Date dataNascita = dettaglioPers.getDATANASCITA();
            controlliOk = controlliAssociaPF( cognome, nome, sesso, dataNascita, codFiscale, result );
        } else if( tipoSoggetto.equals( 'N' ) )
        {
            String denominazione = dettaglioPers.getDENOMINAZIONE();
            String comuneSede = dettaglioPers.getCODICECOMUNESEDE();
            String provSede = dettaglioPers.getPROVSEDE();
            String codFiscale = dettaglioPers.getPARTITAIVA();
            controlliOk = controlliAssociaPNF( denominazione, comuneSede, provSede, codFiscale, result );
        }
        Character flgErrore = controlliOk ? '0' : '1';
        dettaglioPers.setVERSIONE( BigDecimal.valueOf(versione) );
        dettaglioPers.setIDSOGGETTO( BigDecimal.valueOf(idSoggetto) );
        dettaglioPers.setTIPOLOGIA( tipoSoggetto );
        dettaglioPers.setFLAGERRORE( flgErrore );

        boolean esitoOk = conservatorieService.associaSoggetto( dettaglioPers, idUffOwn, descUtente, idNota );
/*
        boolean esitoOk = conservatorieService.i( idSoggetto, idUffOwn, versione, tipoSoggetto, descUtente, idNota, flgErrore  ); 
*/
        if( esitoOk )
        {
            super.setMessage( Constants.SUCCESS_MESSAGE, "success.associazioneNotaSoggettoOK", " ", true );
        } else
        {
            super.setMessage( Constants.MESSAGE, "conservatoria.error.associazioneNotaSoggetto", " ", true );
        }
        return indietroElencoSoggetti( form, result, modelMap , request);
    }

    private boolean controlliAssociaPNF( String denominazione, String comuneSede, String provSede, String partitaIva, BindingResult result )
    {
        if( denominazione == null || comuneSede == null || provSede == null || partitaIva == null )
        {
            return false;
        }
        return true;
    }

    private boolean controlliAssociaPF( String cognome, String nome, Character sesso, Date dataNascita, String codFiscale, BindingResult result )
    {
        if( cognome == null || nome == null || sesso == null ||  dataNascita == null || dataNascita.after( new Date() ) || codFiscale == null )
        {
            return false;
        }
        return true;
    }

    /** RequestMapping( value = "/conservatorie/soggettiComunicazione.htm", method = RequestMethod.GET ) */
    @RequestMapping( value = "/conservatorie/soggettiComunicazione.htm", method = RequestMethod.GET )
    public ModelAndView elencoSoggettiComunicazione( Model model, HttpServletRequest request )
    {
        ComunicazioneSoggettiForm form = new ComunicazioneSoggettiForm();
        Long idNota = (Long)request.getSession().getAttribute( "idNota" );
        Integer idUfficioUtente = (Integer)request.getSession().getAttribute( "idUffOwner" );
        NotaDettaglioForm dettaglio = conservatorieService.dettaglioNota( idUfficioUtente, idNota ); // compresa lista soggetti
        if( dettaglio != null ) 
        {
            completaForm( form, dettaglio );
            List<SoggettoInteressatoDto> soggettiAssociabiliDto = new ArrayList<SoggettoInteressatoDto>();
            soggettiAssociabiliDto = conservatorieService.listaSoggettiAssociabili( idNota, idUfficioUtente, dettaglio );
            List<SoggettoInteressatoDescDto> soggettiAssociabili = new ArrayList<SoggettoInteressatoDescDto>();
            soggettiAssociabili = conservatorieService.setListaSoggettiDesc(soggettiAssociabiliDto);
            
            if(dettaglio.getListaSoggetti() != null) {
            	if( dettaglio.getListaSoggetti().isEmpty() ) {
		            if( soggettiAssociabili.isEmpty() )
		                super.setMessage( Constants.MESSAGE, "conservatoria.listaSoggettiAssociabiliVuota", " ", false );
		            }
            } else {
            	if( soggettiAssociabili.isEmpty() )
	                super.setMessage( Constants.MESSAGE, "conservatoria.listaSoggettiAssociabiliVuota", " ", false );
            }
            form.setListaSoggettiAssociabili( soggettiAssociabili );
        }
        ProcedimentoForm procedimento = new ProcedimentoForm();
        procedimento.setIdProcedimento(form.getNumeroProcedimento());
//		request.getSession().setAttribute(Constants.ID_PROCEDIMENTO, procedimento);
		super.updateProcedimentoIntoSession(procedimento.getIdProcedimento());


        ModelAndView mnv = new ModelAndView( "/conservatorie/elencoSoggettiComunicazione", "comunicazioneSoggettiForm", form );
		addBreadcrumbs();
        return mnv;
    }

    /** RequestMapping( value = "/conservatorie/soggettiComunicazione.htm", method = RequestMethod.POST, params = {"Indietro"} ) */
    @RequestMapping( value = "/conservatorie/soggettiComunicazione.htm", method = RequestMethod.POST, params = { "Indietro" } )
    public String indietroElencoSoggetti( ComunicazioneSoggettiForm form, BindingResult result, ModelMap modelMap , HttpServletRequest request)
    {
        Long idNota = (Long)request.getSession().getAttribute( "idNota" );
        Integer idUfficioUtente = (Integer)request.getSession().getAttribute( "idUffOwner" );
        NotaDettaglioForm dettaglio = conservatorieService.dettaglioNota( idUfficioUtente, idNota ); // compresa lista soggetti

        if( dettaglio != null )
        {
            completaForm( form, dettaglio );
            List<SoggettoInteressatoDto> soggettiAssociabiliDto = new ArrayList<SoggettoInteressatoDto>();
            soggettiAssociabiliDto = conservatorieService.listaSoggettiAssociabili( idNota, idUfficioUtente, dettaglio );
            List<SoggettoInteressatoDescDto> soggettiAssociabili = new ArrayList<SoggettoInteressatoDescDto>();
            soggettiAssociabili = conservatorieService.setListaSoggettiDesc(soggettiAssociabiliDto);
            form.setListaSoggettiAssociabili( soggettiAssociabili );
        }
        modelMap.addAttribute( "comunicazioneSoggettiForm", form );
        return "/conservatorie/elencoSoggettiComunicazione";
    }

    private ModelAndView caricaDettaglioSoggetto( 
        String jspName, ComunicazioneSoggettiForm form, BindingResult result, Model model, ModelMap modelMap, HttpServletRequest request )
    {
        Long idNota = (Long)request.getSession().getAttribute( "idNota" );
        Integer idUffOwn = (Integer)request.getSession().getAttribute( "idUffOwner" );
        String selezionato = form.getSoggettoSelected();
        if( selezionato == null )
        {
            super.setMessage( Constants.MESSAGE, "table.item.selection", null, false );
            result.reject( Constants.MESSAGE );
            return elencoSoggettiComunicazione( model, request );
        }
        String[] campi = StringUtils.split( selezionato, "'^'" );
        Long progrSogg = new Long( campi[0] );
        String tipoSoggetto = campi[1];

        List<ComboForm> listaPFinRegime = conservatorieService.listaPFinRegime( 
                (Long)request.getSession().getAttribute( "idNota" ), 
                (Integer)request.getSession().getAttribute( "idUffOwner" ), progrSogg );
        modelMap.addAttribute("listaPFinRegime", listaPFinRegime);
        
        form.setFlgTipoSoggetto( tipoSoggetto.charAt( 0 ) ); // deve essere 'F' o 'N' ( persona 'Fisica' o 'Non fisica' )
        conservatorieService.dettaglioSoggetto( form, idNota, idUffOwn, progrSogg ); // form.getComboComuniBelfiore();
        ModelAndView mnv = new ModelAndView( jspName, "comunicazioneSoggettiForm", form );
        return mnv;
    }

    /** @RequestMapping( value = "/conservatorie/soggettiComunicazione.htm", method = RequestMethod.POST, params = {"SoggettoSitMp"} ) */
    @RequestMapping( value = "/conservatorie/soggettiComunicazione.htm", method = RequestMethod.POST, params = {"SoggettoSitMp"} )
    public ModelAndView soggettoSitMP( ComunicazioneSoggettiForm form, BindingResult result,  ModelMap modelMap, HttpServletRequest request )
	{
        String soggettoSel = form.getSoggettoAssociabileSelected();
    	form.setTipoOperazione("Modifica");
    	ModelAndView ritorno = null;
        ritorno = new ModelAndView( "/conservatorie/elencoSoggettiComunicazione", "comunicazioneSoggettiForm", form );
		if (form.getSoggettoAssociabileSelected() == null) {
			super.setMessage( Constants.MESSAGE, "table.item.selection", null, false );
			result.reject( Constants.MESSAGE );
			completaFormRitorno(form, request);
			return ritorno;
		}
		
        String[] campi = StringUtils.split( soggettoSel, "'^'" );
        Long idSoggetto = new Long( campi[0] );
        Character tipoSoggetto = campi[1].charAt( 0 );
        Integer versione = Integer.valueOf( campi[2] );

		SoggettoForm soggettoForm = new SoggettoForm();
		
		soggettoForm.setIdSoggetto(idSoggetto);
		soggettoForm.setTipoSoggetto(tipoSoggetto.toString());
		soggettoForm.setIdVersione(versione.longValue());
		
		ritorno = soggettoController.direttiInteressatiDettaglio(soggettoForm, result);
		
		return ritorno;
	}
    
    private void completaFormRitorno(ComunicazioneSoggettiForm form, HttpServletRequest request) 
    {
        Long idNota = (Long)request.getSession().getAttribute( "idNota" );
        Integer idUfficioUtente = (Integer)request.getSession().getAttribute( "idUffOwner" );
        NotaDettaglioForm dettaglio = conservatorieService.dettaglioNota( idUfficioUtente, idNota ); // compresa lista soggetti
        completaForm( form, dettaglio );
        List<SoggettoInteressatoDto> soggettiAssociabiliDto = new ArrayList<SoggettoInteressatoDto>();
        soggettiAssociabiliDto = conservatorieService.listaSoggettiAssociabili( idNota, idUfficioUtente, dettaglio );
        List<SoggettoInteressatoDescDto> soggettiAssociabili = new ArrayList<SoggettoInteressatoDescDto>();
        soggettiAssociabili = conservatorieService.setListaSoggettiDesc(soggettiAssociabiliDto);

        form.setListaSoggettiAssociabili( soggettiAssociabili );
	}

	/** RequestMapping( value = "/conservatorie/soggettiComunicazione.htm", method = RequestMethod.POST, params = {"Dettaglio"} ) */
    @RequestMapping( value = "/conservatorie/soggettiComunicazione.htm", method = RequestMethod.POST, params = { "Dettaglio" } )
    public ModelAndView mostraDettaglioSoggetto( ComunicazioneSoggettiForm form, BindingResult result, Model model, ModelMap modelMap , HttpServletRequest request)
    {
        form.setTipoOperazione( "Dettaglio" );
        return caricaDettaglioSoggetto( "/conservatorie/dettaglioSoggettoComunicazione", form, result, model, modelMap , request);
    }

    /** RequestMapping( value = "/conservatorie/soggettiComunicazione.htm", method = RequestMethod.POST, params = {"Modifica"} ) */
    @RequestMapping( value = "/conservatorie/soggettiComunicazione.htm", method = RequestMethod.POST, params = { "Modifica" } )
    public ModelAndView preparaModificaSoggetto( ComunicazioneSoggettiForm form, BindingResult result, Model model, ModelMap modelMap , HttpServletRequest request)
    {
        Long idNota = (Long)request.getSession().getAttribute( "idNota" );
        Integer idUffOwner = (Integer)request.getSession().getAttribute( "idUffOwner" );
        String selezionato = form.getSoggettoSelected();
        if( selezionato == null )
        {
            super.setMessage( Constants.MESSAGE, "table.item.selection", null, false );
            result.reject( Constants.MESSAGE );
            return elencoSoggettiComunicazione( model, request );
        }
        form.setTipoOperazione( "Modifica" );
        return caricaDettaglioSoggetto( "/conservatorie/modificaSoggettoComunicazione", form, result, model, modelMap, request );
    }
    
    /** @RequestMapping( value = "/conservatorie/soggettiComunicazione.htm", method = RequestMethod.POST, params = {"Cancella"} ) */
    @RequestMapping( value = "/conservatorie/soggettiComunicazione.htm", method = RequestMethod.POST, params = {"Cancella"} )
	public String deleteSoggettoComunicazione(ComunicazioneSoggettiForm form, BindingResult result, ModelMap modelMap , HttpServletRequest request)
	{
    	form.setTipoOperazione("Cancella");
		Long idNota = (Long)request.getSession().getAttribute( "idNota" );
		Integer idUffOwn = (Integer)request.getSession().getAttribute( "idUffOwner" );
		String descUtente = (String)request.getSession().getAttribute( "codUtente" ) ;
		String ritorno = "/conservatorie/modificaSoggettoComunicazione";
		
//		NotaDettaglioForm dettaglio = conservatorieService.dettaglioNota( idUffOwn, idNota );
//		completaForm(form, dettaglio);
		Long progressivoSogg = form.getSoggettoDto().getProgressivo().longValue();
		Character tipoSoggetto= form.getFlgTipoSoggetto();
		Character stato=form.getStato();
		
		if ("OK".equals(conservatorieService.cancellaNotaSoggetto(idNota, idUffOwn, progressivoSogg, descUtente,
				tipoSoggetto, stato)))
		{
//			completaForm(form, dettaglio);
			super.setMessage(Constants.SUCCESS_MESSAGE, "success.cancellazioneNotaSoggettoOK", " ", true);
		} else
		{
			super.setMessage(Constants.MESSAGE, "conservatoria.error.cancellazioneNotaSoggetto", " ", true);

		}
		return ritorno;
	}
    
    /** RequestMapping( value = "/conservatorie/soggettiComunicazione.htm", method = RequestMethod.POST, params = {"Conferma"} ) */
    @RequestMapping( value = "/conservatorie/soggettiComunicazione.htm", method = RequestMethod.POST, params = { "Conferma" } )
    public String modificaSoggettiComunicazione( ComunicazioneSoggettiForm form, BindingResult result, ModelMap modelMap , HttpServletRequest request)
    {
        String ritorno = "/conservatorie/modificaSoggettoComunicazione";
        boolean ok = false;
        Integer idUffOwn = (Integer)request.getSession().getAttribute( "idUffOwner" );
        String descUtente = (String)request.getSession().getAttribute( "codUtente" );
        Long idNota = (Long)request.getSession().getAttribute( "idNota" );

        SoggettoInteressatoDescDto soggetto = form.getSoggettoDto();
        Long progressivo = soggetto.getProgressivo().longValue();
        if( form.getFlgTipoSoggetto() == 'N' )
        {
            if( form.getComboStatiIstat().getId() != null && !form.getComboStatiIstat().getId().equals( "1" ) )
            { // sede all'estero : in Codice Comune va il codice Istat dello Stato estero
                soggetto.setCodiceComuneSede( form.getComboStatiIstat().getId() );
                soggetto.setComuneSede( form.getComboComuniIstat().getDescrizione() );
                soggetto.setProvSede( null );
            } else
            { // sede in Italia : in Codice Comune va il codice Istat del comune
                soggetto.setCodiceComuneSede( form.getComboComuniIstat().getId() );
                soggetto.setComuneSede( form.getComboComuniIstat().getDescrizione() );
                soggetto.setProvSede( getProvinciaFromComune( soggetto.getComuneSede() ) );
                soggetto.setComuneSede( getComuneFromComune( soggetto.getComuneSede() ) );
            }
            String denom = soggetto.getDenominazione();
            String pIva = soggetto.getPartitaIva();
            String codComune = soggetto.getCodiceComuneSede();
            if( !controlliSoggN( denom, soggetto.getComuneSede(), pIva, codComune, result ) )
            {
                modelMap.addAttribute( "comunicazioneSoggettiForm", form );
                return ritorno;
            }
            ok = conservatorieService.modificaSoggettoGiurid(
                idNota, idUffOwn, descUtente, denom, soggetto.getComuneSede(), soggetto.getProvNascita(), pIva, codComune, progressivo );
        } else
        {
            if( form.getComboStatiBelfiore().getId() != null && !form.getComboStatiBelfiore().getId().equals( "1" ) )
            { // sede all'estero : in Codice Comune va il codice Belfiore dello Stato estero
                soggetto.setCodiceComuneNasc( form.getComboStatiBelfiore().getId() );
                soggetto.setComuneNascita( form.getComboComuniBelfiore().getDescrizione() );
                soggetto.setProvNascita( null );
            } else
            { // sede in Italia : in Codice Comune va il codice Belfiore del comune
                soggetto.setCodiceComuneNasc( form.getComboComuniBelfiore().getId() );
                soggetto.setComuneNascita( form.getComboComuniBelfiore().getDescrizione() );
                soggetto.setProvNascita( getProvinciaFromComune( soggetto.getComuneNascita() ) );
                soggetto.setComuneNascita( getComuneFromComune( soggetto.getComuneNascita() ) );
            }
            if( form.getComboRegime().getId().equals( "-1" ) )
                soggetto.setRegime( null );
            else 
                soggetto.setRegime( form.getComboRegime().getId().charAt( 0 ) );

            if( form.getComboPF().getId().equals( "-1" ) )
                soggetto.setCodProgrSoggC( null );
            else 
                soggetto.setCodProgrSoggC( new Integer(form.getComboPF().getId() ) );

            String cognome = soggetto.getCognome();
            String nome = soggetto.getNome();
            Character sesso = soggetto.getFlgSesso();
            Date dataNascita = soggetto.getDataNascita();
            String statoNascita = soggetto.getStatoNascita();
            String codiceComuneNasc = soggetto.getCodiceComuneNasc();
            String comuneNascita = soggetto.getComuneNascita();
            String provNascita = soggetto.getProvNascita();
            String codFiscale = soggetto.getCodiceFiscalePF();
            Character regime = soggetto.getRegime();
            if( !controlliSoggF( cognome, nome, sesso, dataNascita, statoNascita, comuneNascita, codFiscale, result ) )
            {
                modelMap.addAttribute( "comunicazioneSoggettiForm", form );
                return ritorno;
            }
            ok = conservatorieService.modificaSoggettoFisico( idNota, idUffOwn, descUtente, cognome, nome, sesso, 
                dataNascita, codiceComuneNasc, comuneNascita, provNascita, codFiscale, regime, soggetto.getCodProgrSoggC(), progressivo );
        }
        if( ok )
            super.setMessage( Constants.SUCCESS_MESSAGE, "success.modificaSoggetto", " ", true );
        else super.setMessage( Constants.MESSAGE, "error.modificaSoggetto", " ", true );

        List<ComboForm> listaPFinRegime = conservatorieService.listaPFinRegime( 
            (Long)request.getSession().getAttribute( "idNota" ), 
            (Integer)request.getSession().getAttribute( "idUffOwner" ), form.getSoggettoDto().getProgressivo() );
        modelMap.addAttribute("listaPFinRegime", listaPFinRegime);
        
        modelMap.addAttribute( "comunicazioneSoggettiForm", form );
        return ritorno;
    }

    private String getProvinciaFromComune( String comune )
    {
        if( comune == null )
            return null;
        Integer apertaPar = comune.indexOf( '(' );
        if( apertaPar > 1 )
            return comune.substring( apertaPar + 1, apertaPar + 3 );
        else return null;
    }

    private String getComuneFromComune( String comune )
    {
        if( comune == null )
            return null;
        Integer apertaPar = comune.indexOf( '(' );
        if( apertaPar > 1 )
            comune = comune.substring( 0, apertaPar ).trim();
        return comune;
    }

    private boolean controlliSoggF( String cognome, String nome, Character sesso, Date dataNascita,
        String statoNascita, String comuneNascita, String codFiscale, BindingResult result )
    {
        @SuppressWarnings( "unused" )
        int campiMancanti = 0;
        if( cognome == null )
        {
            campiMancanti += mancante( result, "Cognome" );
            return false;
        }
        if( nome == null )
        {
            campiMancanti += mancante( result, "Nome" );
            return false;
        }
        if( sesso == null )
        {
            campiMancanti += mancante( result, "Sesso" );
            return false;
        }
        if( dataNascita == null || dataNascita.after( new Date() ) )
        {
            campiMancanti += mancante( result, "Data di nascita" );
            return false;
        }

        if( dataNascita.after( new Date() ) )
        {
            super.setMessage( Constants.MESSAGE, "conservatoria.error.dataNascitaFutura", " ", false );
            return false;
        }

        if( statoNascita == null )
        {
            campiMancanti += mancante( result, "Stato di nascita" );
            return false;
        }
        if( comuneNascita == null )
        {
            campiMancanti += mancante( result, "Comune di nascita" );
            return false;
        }
        if( codFiscale == null )
        {
            campiMancanti += mancante( result, "Codice fiscale" );
            return false;
        } else {
        	String message = Utility.ControllaCodiceFiscale(codFiscale);
			if(StringUtils.isNotBlank(message)){
				super.setMessage(Constants.MESSAGE, message, null, false);
				result.reject( Constants.MESSAGE );
				return false;
			}
        }
        return true;
    }

    private boolean controlliSoggN( String denom, String comune, String pIva, String codComune, BindingResult result )
    {
        @SuppressWarnings( "unused" )
        int campiMancanti = 0;
        if( denom == null )
        {
            campiMancanti += mancante( result, "Denominazione" );
            return false;
        }
        if( comune == null )
        {
            campiMancanti += mancante( result, "Comune sede" );
            return false;
        }
        if( pIva == null )
        {
            campiMancanti += mancante( result, "Partita Iva" );
            return false;
        } else {
        	String message = Utility.controllaPIVA(pIva);
			if(StringUtils.isNotBlank(message)){
				super.setMessage(Constants.MESSAGE, message, null, false);
				result.reject( Constants.MESSAGE );
				return false;
			}
        }
        if( codComune == null || codComune.equals( "-1" ) )
        {
            campiMancanti += mancante( result, "Stato sede" );
            return false;
        }
        return true;
    }

    private int mancante( BindingResult result, String params )
    {
        super.setMessage( Constants.MESSAGE, "conservatoria.error.campoObbligatorio", params, false );
        return 1;
    }
}
