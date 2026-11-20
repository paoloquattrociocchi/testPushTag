package it.giustizia.smp.controller.conservatorie;

import it.giustizia.smp.controller.BreadcrumbsController;
import it.giustizia.smp.controller.MenuController;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.conservatorie.ComunicazioneListaForm;
import it.giustizia.smp.form.conservatorie.ComunicazioneModificaForm;
import it.giustizia.smp.form.conservatorie.ComunicazioneRicercaForm;
import it.giustizia.smp.form.conservatorie.NotaDettaglioForm;
import it.giustizia.smp.service.CommonService;
import it.giustizia.smp.service.ConservatorieService;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;
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

@Controller
public class ModificaComunicazioniController extends BreadcrumbsController
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
	
	@ModelAttribute( "tipoComunicAnn" )
	public List<ComboForm> getTipoComunicAnn( HttpServletRequest request )
	{
		return conservatorieService.getTipoComunicAnn();
	}

	@ModelAttribute( "tipoComunicTrasc" )
	public List<ComboForm> getTipoComunicTrasc( HttpServletRequest request )
	{
		return conservatorieService.getTipoComunicTrasc();
	}

	@ModelAttribute( "conservatoria" )
	public List<ComboForm> getAllConservatoria( HttpServletRequest request )
	{
		return commonService.getAllConservatoria();
	}

	@ModelAttribute( "categorieAtti" )
	public List<ComboForm> getCategorieAtti( HttpServletRequest request )
	{
		return conservatorieService.getCategorieAtti( "ricerca",(Integer) request.getSession().getAttribute("idTipoUfficio") );
	}

	@ModelAttribute( "tipoNota" )
	public List<ComboForm> getTipoNota( HttpServletRequest request )
	{
		return commonService.getTipoNota();
	}
	
	private int mancante( BindingResult result, String params )
	{
		super.setMessage( Constants.MESSAGE, "conservatoria.error.campoObbligatorio", params, false );
		result.reject( Constants.MESSAGE );
		return 1;
	}

	private boolean controlli( String conservatoria, Character tipoNotaId, String codiceFiscale, String titoloDescr,
		Date dataTitolo, String pubblicoUfficiale, String comune, String valore, String dataFormalita, String numeroFormalita1, 
		String dataTrascrizione, String numeroTrascrizione1, BindingResult result )
	{
		@SuppressWarnings( "unused" )
        int campiMancanti = 0;
		Date oggi = new Date();
		if( conservatoria == null || conservatoria.equals( "-1" ) )
		{
			campiMancanti += mancante( result, "Conservatoria" );
			return false;
		}
		if( codiceFiscale == null )
		{
			campiMancanti += mancante( result, "Codice fiscale" );
			return false;
		}
		if( titoloDescr == null || titoloDescr.equals( "-1" ) )
		{
			campiMancanti += mancante( result, "Titolo descrizione" );
			return false;
		}
		if( dataTitolo == null || dataTitolo.after( oggi ) )
		{
			super.setMessage( Constants.MESSAGE, "conservatoria.error.dataTitolo", null, false );
			result.reject( Constants.MESSAGE );
			campiMancanti++;
		}
		if( pubblicoUfficiale == null )
		{
			campiMancanti += mancante( result, "Pubblico ufficiale" );
			return false;
		}
		if( comune == null )
		{
			campiMancanti += mancante( result, "Sede" );
			return false;
		}
		
		if( tipoNotaId.equals( 'A' ))  
		{
		    if( dataTrascrizione == null  ) 
		    {
			super.setMessage( Constants.MESSAGE, "conservatoria.error.dataFormalitaModifica", null, false );
			result.reject( Constants.MESSAGE );
			return false;
		    }
		    if (numeroTrascrizione1 == null) 
		    {
				super.setMessage( Constants.MESSAGE, "conservatoria.error.numeroFormalita1", null, false );
				result.reject( Constants.MESSAGE );
				return false;
		    }
	    }
		
        if( valore != null )
        {
            if( !isFloat( valore ) )
            {
                super.setMessage( Constants.MESSAGE, "conservatoria.error.valoreImmobileIncoerente", null, false );
                result.reject( Constants.MESSAGE );
                return false;
            }
            String valoreImmInt = "";
            String valoreImmDec = "";
            int vInd = valore.indexOf( '.' );
            if( vInd < 0 )
                valoreImmInt = valore;
            else
                valoreImmInt = valore.substring( 0, vInd );
            if( valoreImmInt.length() > 11 )
            {
                super.setMessage( Constants.MESSAGE, "conservatoria.error.valoreImmobile", null, false );
                result.reject( Constants.MESSAGE );
                return false;
            }
            if( vInd >= 0 )
            {
                valoreImmDec = valore.substring( vInd + 1, valore.length() );
                if( valoreImmDec.length() > 2 )
                {
                    super.setMessage( Constants.MESSAGE, "conservatoria.error.valoreImmobile", null, false );
                    result.reject( Constants.MESSAGE );
                    return false;
                }
            }
		}
		return true;
	}

    private ComunicazioneModificaForm caricaNota(NotaDettaglioForm dettaglio )
    {
        ComunicazioneModificaForm form = new ComunicazioneModificaForm();
        if( dettaglio != null )
        {
            form.setNumeroComunicazione( dettaglio.getNumero() );
            form.setTipoNotaId(dettaglio.getTipoNotaId());
            form.setDescTipoNota(dettaglio.getDescTipoNota());
            form.setCategorieAtti( dettaglio.getDescCatAtto() );
            form.setRepertorioAnno( dettaglio.getRepertorioAnno() );
            form.setNumero( dettaglio.getNumeroRepertorio1() );
            form.setProgressivo( dettaglio.getProgressivo() );
            form.setCategoria( dettaglio.getCategoria() );
            form.setAttoSpecie( dettaglio.getAttoSpecie() );
            form.setConservatoriaSelez( dettaglio.getConservatoria() );
            
            if( form.getTipoNotaId().equals( 'T' ) )
                form.setTipoComunicSelez( dettaglio.getDescAtto() );
            if( form.getTipoNotaId().equals( 'A' ) )
                form.setTipoComunicSelez(dettaglio.getTipoComunic());
            
            form.setCodiceFiscale( dettaglio.getCodiceFiscale() );
            form.setDataTitolo( dettaglio.getData() );
            if( dettaglio.getTipoComunic() != null )
                form.setRettificaSelez( dettaglio.getTipoComunic().substring( 0, 1 ) );
            form.setImmobili( dettaglio.getImmobili().intValue() );
            form.setProvincia( dettaglio.getProvincia() );
            form.setPubblicoUfficiale( dettaglio.getPubblicoUfficiale() );
            
            if( dettaglio.getSede().indexOf('(') < 0 ) 
                form.setSede( dettaglio.getSede() + " (" + dettaglio.getProvincia() + ")" );
            else
                form.setSede( dettaglio.getSede() );

            form.setSoggettiContro( dettaglio.getSoggettiContro().shortValue() );
            form.setSoggettiFavore( dettaglio.getSoggettiFavore().shortValue() );
            form.setTitoloDescrizione( dettaglio.getTitoloDescrizione() );
            form.setUnitaNegoziali( dettaglio.getUnitaNegoziali().shortValue() );
            form.setStato( dettaglio.getStato() );
            form.setAttoSoggVoltura( dettaglio.isAttoSoggVoltura() );
            form.setVolturaCatastale( dettaglio.isVolturaCatastale() );
            form.setPresenzaCondizione( dettaglio.isPresenzaCondizione() );
            form.setPresenzaTermini( dettaglio.isPresenzaTermini() );
            form.setDifferita( dettaglio.isDifferita() );
            form.setDataDal( dettaglio.getDataDal() );
            form.setFormalita( dettaglio.getFormalita() );
            form.setDataFormalita( dettaglio.getDataFormalita() );
            form.setNumeroFormalita1( dettaglio.getNumeroFormalita1() );
            form.setNumeroFormalita2( dettaglio.getNumeroFormalita2() );
            form.setNumeroTrascrizione1( dettaglio.getNumeroTrascrizione1() );
            form.setNumeroTrascrizione2( dettaglio.getNumeroTrascrizione2() );
            form.setQuadroA( dettaglio.isQuadroA() );
            form.setQuadroB( dettaglio.isQuadroB() );
            form.setQuadroC( dettaglio.isQuadroC() );
            form.setTrascrizioneDel(dettaglio.getTrascrizioneDel());
            if( dettaglio.getValoreImmobLib() != null )
            {
                String centesimi = ( "00" + dettaglio.getValoreImmobLibParteDecimale() ); 
                centesimi = centesimi.substring( centesimi.length() - 2 );
                form.setValoreImmobLib( dettaglio.getValoreImmobLib() + "," + centesimi );
             // form.setValoreImmobLib( dettaglio.getValoreImmobLib() + "," + dettaglio.getValoreImmobLibParteDecimale() );
            } else
            {
                form.setValoreImmobLib( "" );
            }
            form.setDescAtto(dettaglio.getDescAtto());
        }
        //recuperato il valore di attoSpecie occorre recuperarne la descrizione 
        if( form.getTipoNotaId().equals( 'T' ) )
            form.setLabelSezAnn( "Trascrizione del " );
            
        if (form.getTipoNotaId().equals('A'))
            form.setLabelSezAnn("Annotazione del ");
        return form;
    }
    
	/** @RequestMapping( value = "/conservatorie/modificaComunicazione.htm", method = RequestMethod.GET ) --> da Copertina a Nota (per modifica) */
	@RequestMapping( value = "/conservatorie/modificaComunicazione.htm", method = RequestMethod.GET )
	public ModelAndView modificaComunicazione( Model model , HttpServletRequest request)
	{
        Long idNota = (Long)request.getSession().getAttribute( "idNota" );
        Integer idUfficioUtente = (Integer)request.getSession().getAttribute( "idUffOwner" );
        NotaDettaglioForm dettaglio = conservatorieService.dettaglioNota( idUfficioUtente, idNota );
        if( !dettaglio.getStato().equals( '0' ) && !dettaglio.getStato().equals( '1' ) )
        {
            super.setMessage( Constants.MESSAGE, "conservatoria.error.variazioneNonAmmessa", null, false );
        }
        ComunicazioneModificaForm form = caricaNota( dettaglio );
		ModelAndView mnv = new ModelAndView( "/conservatorie/modificaComunicazione", "comunicazioneModificaForm", form );
		cleanBreadcrumb(request.getSession());
		addBreadcrumbs();
		return mnv;
	}

    /** @RequestMapping( value = "/conservatorie/modificaComunicazione.htm", method = RequestMethod.POST, params = { "UnitaNegoziali" } ) */
    @RequestMapping( value = "/conservatorie/modificaComunicazione.htm", method = RequestMethod.POST, params = { "UnitaNegoziali" } )
    public String unitaNegoziali( ComunicazioneModificaForm form, BindingResult result, ModelMap modelMap, HttpServletRequest request )
    {
        String ritorno = "/conservatorie/modificaComunicazione";
        Integer idUff = (Integer)request.getSession().getAttribute( "idUffOwner" );
        Long idNota = form.getNumeroComunicazione();
        String descUtente = (String)request.getSession().getAttribute( "codUtente" );
        
        // INSERITI CONTROLLI
        String dataFormalita = null;
        String dataTrascrizione = null;
		String numeroFormalita1 = null;
		String numeroTrascrizione1 = null;
        
        
		if (form.getTipoNotaId().equals('A')) 
		{
			if (form.getTrascrizioneDel() != null) 
			{
				dataTrascrizione = form.getTrascrizioneDel().toString();
			}
			if (form.getNumeroTrascrizione1() != null) 
			{
				numeroTrascrizione1 = form.getNumeroTrascrizione1().toString();
			}
		}
		
		if (form.getTipoNotaId().equals('T')) 
		{
			if (form.getDataFormalita() != null) 
			{
				dataFormalita = form.getDataFormalita().toString();
			}
			if (form.getNumeroTrascrizione1() != null) 
			{
				numeroFormalita1 = form.getNumeroFormalita1().toString();
			}
		}

        String rettifica = null;
        if( form.getRettifica() != null && form.getRettifica().getId() != null )
        {
            if( !form.getRettifica().getId().equals( "-1" ) )
                rettifica = form.getRettifica().getId();
        }
        ComboForm tipoCom = new ComboForm();
        if( form.getTipoNotaId().equals('T' ) )
        {
            if( rettifica != null )
            {
                tipoCom.setId(rettifica + "600");
            }
        }
        form.setTipoComunic(tipoCom);
        
		String valore = null;
		if (form.getValoreImmobLib() != null) {
			valore = form.getValoreImmobLib();
			valore = valore.replace(',' , '.');
			if (isFloat(valore)) {
				form.setValoreImmobLib(valore);
			}
		}
       
		if (!controlli(form.getConservatoria().getId(), form.getTipoNotaId(), form.getCodiceFiscale(), form.getTitoloDescrizione(), 
					   form.getDataTitolo(), form.getPubblicoUfficiale(), form.getComune().getDescrizione(), 
					   valore, dataFormalita, numeroFormalita1, dataTrascrizione,numeroTrascrizione1, result)) {
			modelMap.addAttribute("comunicazioneModificaForm", form);
			return ritorno;
		}
		
		// Controllo coerenza DataFormalita (controllo che la stessa esista in archivio e che non sia stata semplicemente digitata nella form)
        NotaDettaglioForm dettaglioOld = conservatorieService.dettaglioNota( idUff, idNota );
        ComunicazioneModificaForm formOld = caricaNota( dettaglioOld );     

        // Valori old
        String dataFormalitaOld = null;
        String dataTrascrizioneOld = null;
		String numeroFormalita1Old = null;
		String numeroTrascrizione1Old = null;
		String valoreOld = null;

		if (formOld.getTipoNotaId().equals('A')) 
		{
			if (formOld.getTrascrizioneDel() != null) 
			{
				dataTrascrizioneOld = formOld.getTrascrizioneDel().toString();
			}
			if (formOld.getNumeroTrascrizione1() != null) 
			{
				numeroTrascrizione1Old = formOld.getNumeroTrascrizione1().toString();
			}
		}
		
		if (formOld.getTipoNotaId().equals('T')) 
		{
			if (formOld.getDataFormalita() != null) 
			{
				dataFormalitaOld = formOld.getDataFormalita().toString();
			}
			if (formOld.getNumeroTrascrizione1() != null) 
			{
				numeroFormalita1Old = formOld.getNumeroFormalita1().toString();
			}
		}
		if (formOld.getTipoNotaId().equals('A')) {
			if (formOld.getValoreImmobLib() != null && formOld.getValoreImmobLib().length() > 0) {
				valoreOld = formOld.getValoreImmobLib();
				valoreOld = valoreOld.replace(',' , '.');
			}
			
		}
		if (!controlli(form.getConservatoria().getId(), formOld.getTipoNotaId(), formOld.getCodiceFiscale(), formOld.getTitoloDescrizione(), 
				formOld.getDataTitolo(), formOld.getPubblicoUfficiale(), form.getComune().getDescrizione(), 
				valoreOld, dataFormalitaOld, numeroFormalita1Old, dataTrascrizioneOld,numeroTrascrizione1Old, result)) {
		modelMap.addAttribute("comunicazioneModificaForm", form);
		return ritorno;
	}
	

        String esito = conservatorieService.calcolaUnitaNegoziali( idNota, idUff, descUtente );
        
        if( esito.equals( "OK" ) )
        {
            super.setMessage( Constants.SUCCESS_MESSAGE, "success.fattoUN",        "Generazione unità negoziali", false ); // true ?
            NotaDettaglioForm dettaglio = conservatorieService.dettaglioNota( idUff, idNota );
            form = caricaNota( dettaglio );     // ricarico la nota, visto che è stata aggiornata
        }else
            super.setMessage( Constants.MESSAGE, "error.nonFatto", "Generazione unità negoziali;" + esito , false );
        
        request.getSession().setAttribute( "idNota", idNota );
        modelMap.addAttribute( "comunicazioneModificaForm", form );
        return ritorno;
    }

    /** @RequestMapping( value = "/conservatorie/modificaComunicazione.htm", method = RequestMethod.POST, params = { "Conferma" } ) */
    @RequestMapping( value = "/conservatorie/modificaComunicazione.htm", method = RequestMethod.POST, params = { "Conferma" } )
    public String modificaComunicazione( ComunicazioneModificaForm form, BindingResult result, ModelMap modelMap , HttpServletRequest request)
    {
        Integer idUfficioUtente = (Integer)request.getSession().getAttribute( "idUffOwner" );
        String descUtente = (String)request.getSession().getAttribute( "codUtente" );
        Character vecchioStato = form.getStato();
        form.setDescUtente( descUtente );
        Character tipoNotaId = form.getTipoNotaId();
        String conservatoria = form.getConservatoria().getId();
        String codiceFiscale = form.getCodiceFiscale();
        String titoloDescr = form.getTitoloDescrizione();
        Date dataTitolo = form.getDataTitolo();
        String pubblicoUfficiale = form.getPubblicoUfficiale();
        String comune = form.getComune().getDescrizione();
        
        String dataFormalita = null;
        String dataTrascrizione = null;
		String numeroFormalita1 = null;
		String numeroTrascrizione1 = null;
		
		if (form.getTipoNotaId().equals('A')) 
		{
			if (form.getTrascrizioneDel() != null) 
			{
				dataTrascrizione = form.getTrascrizioneDel().toString();
			}
			if (form.getNumeroTrascrizione1() != null) 
			{
				numeroTrascrizione1 = form.getNumeroTrascrizione1().toString();
			}
		}
		
		if (form.getTipoNotaId().equals('T')) 
		{
			if (form.getDataFormalita() != null) 
			{
				dataFormalita = form.getDataFormalita().toString();
			}
			if (form.getNumeroTrascrizione1() != null) 
			{
				numeroFormalita1 = form.getNumeroFormalita1().toString();
			}
		}


        String rettifica = null;
        if( form.getRettifica() != null && form.getRettifica().getId() != null )
        {
            if( !form.getRettifica().getId().equals( "-1" ) )
                rettifica = form.getRettifica().getId();
        }
        ComboForm tipoCom = new ComboForm();
        if( tipoNotaId.equals('T' ) )
        {
            if( rettifica != null )
            {
                tipoCom.setId(rettifica + "600");
            }
        }
        form.setTipoComunic(tipoCom);
        
		String valore = null;
		if (form.getValoreImmobLib() != null) {
			valore = form.getValoreImmobLib();
			valore = valore.replace(',' , '.');
			if (isFloat(valore)) {
				form.setValoreImmobLib(valore);
			}
		}
 
		String ritorno = "/conservatorie/modificaComunicazione";
       
		if (!controlli(conservatoria, tipoNotaId, codiceFiscale, titoloDescr, dataTitolo, pubblicoUfficiale, comune, 
				       valore, dataFormalita, numeroFormalita1, dataTrascrizione,numeroTrascrizione1, result)) {
			modelMap.addAttribute("comunicazioneModificaForm", form);
			return ritorno;
		}
		
		// Controllo coerenza DataFormalita 
		if (form.getTipoNotaId().equals('T')) 
		{
			if(form.getDataFormalita() != null ) { 
				if (form.getNumeroFormalita1() == null) {
					super.setMessage( Constants.MESSAGE, "conservatoria.error.dataTrascrizione", null, false );
					result.reject( Constants.MESSAGE );
					return ritorno;
				}
			}
		}
		
		try {
			form.setProvincia(comune.substring(comune.indexOf('(') + 1, comune.indexOf(')')));
			form.setSede(comune.substring(0, comune.indexOf('(')));
		} catch (Exception e) {
			super.setMessage(Constants.MESSAGE, "conservatoria.error.provincia", null, false);
			return ritorno;
		}
        // controllo codice fiscale pubblico ufficiale
        if(StringUtils.isNotBlank(form.getCodiceFiscale())){
            if (form.getCodiceFiscale().length() > 11) {
                String message = Utility.ControllaCodiceFiscale(form.getCodiceFiscale());
                if(StringUtils.isNotBlank(message)){
                    super.setMessage(Constants.MESSAGE, message, null, false);
                    result.reject( Constants.MESSAGE );
                    return ritorno;
                }
            } else {
                String message = Utility.controllaPIVA(form.getCodiceFiscale());
                if(StringUtils.isNotBlank(message)){
                    super.setMessage(Constants.MESSAGE, message, null, false);
                    result.reject( Constants.MESSAGE );
                    return ritorno;
                }
            }
        }
        Long idNota = conservatorieService.modificaNota( form, idUfficioUtente );
        form.setNumeroComunicazione( idNota );
        form.setSede( comune );
        
		if (form.getDescrConservatoria() == null) {
			form.setConservatoriaSelez(form.getConservatoriaSelez());
		} else {
			form.setConservatoriaSelez(form.getDescrConservatoria());
		}
        request.getSession().setAttribute( "idNota", idNota );
        if( vecchioStato != '0' )
            super.setMessage( Constants.SUCCESS_MESSAGE, "success.modificaNotaComunicazione", " ", true );
        else
            super.setMessage( Constants.SUCCESS_MESSAGE, "success.modificaNota", " ", true );
        modelMap.addAttribute( "comunicazioneModificaForm", form );
        return ritorno;
    }

    /** @RequestMapping( value = "/conservatorie/modificaComunicazione.htm", method = RequestMethod.POST, params = { "Cancella" } ) */
    @RequestMapping( value = "/conservatorie/modificaComunicazione.htm", method = RequestMethod.POST, params = { "Cancella" } )
	public String cancellaComunicazione( ComunicazioneModificaForm form, BindingResult result, ModelMap modelMap , HttpServletRequest request)
	{
		Integer idUffOwn = (Integer)request.getSession().getAttribute( "idUffOwner" );
		Long idNota = (Long)request.getSession().getAttribute( "idNota" );
		String ritorno = "/conservatorie/modificaComunicazione";
		if ("OK".equals(conservatorieService.eliminaNota(idNota, idUffOwn))){

			List<ComunicazioneListaForm> listaComunicazioniConservatoriaForm =  new ArrayList<ComunicazioneListaForm>();
			ComunicazioneRicercaForm conservatoriaForm = (ComunicazioneRicercaForm)request.getSession().getAttribute( "comunicazioneRicercaForm" );
			listaComunicazioniConservatoriaForm = ricaricaLista(listaComunicazioniConservatoriaForm, conservatoriaForm , request);
			
			MenuController.caricaMenu( Constants.ID_K_MENU_CONSERVATORIE, request );
			modelMap.addAttribute( "listaComunicazioniConservatoriaForm", listaComunicazioniConservatoriaForm );
			modelMap.addAttribute( "comunicazioneRicercaForm", conservatoriaForm );
			ritorno =  "/conservatorie/ricercaConservatoria";
			
			super.setMessage( Constants.SUCCESS_MESSAGE, "success.cancellazioneNotaOK", " ", true );
		} else {
			super.setMessage(Constants.MESSAGE,  "conservatoria.error.cancellazioneNota", " ", true );
			
		}
		return ritorno;
	}

	private List<ComunicazioneListaForm> ricaricaLista(	List<ComunicazioneListaForm> listaComunicazioniConservatoriaForm, ComunicazioneRicercaForm conservatoriaForm, HttpServletRequest request) {

		Integer idUfficioUtente = (Integer)request.getSession().getAttribute( "idUffOwner" );

		String tipo = conservatoriaForm.getTipoNota().getId();
		String categorieAtti = conservatoriaForm.getCategorieAtti().getId();
		String conservatoria = conservatoriaForm.getConservatoria().getId();
		Short anno = conservatoriaForm.getAnno();
		Long dalNumero = conservatoriaForm.getDalNumero();
		Long alNumero = conservatoriaForm.getAlNumero();
		String cognome = conservatoriaForm.getCognome();
		String nome = conservatoriaForm.getNome();
		String denominazione = conservatoriaForm.getDenominazione();
		if ("-1".equals(tipo)) {
			tipo = null;
		}
		if ("-1".equals(categorieAtti)) {
			categorieAtti = null;
		} else {
			categorieAtti = categorieAtti.substring(0, categorieAtti.indexOf(';'));
		}
		if ("-1".equals(conservatoria)) {
			conservatoria = null;
		}
		if (dalNumero == null) {
			dalNumero = (long) 0;
		}
		if (alNumero == null) {
			alNumero = (long) 999999;
		}

		listaComunicazioniConservatoriaForm = conservatorieService.listaComunicazioniConservatorie( tipo, categorieAtti, anno,
			        dalNumero, alNumero, conservatoria, cognome, nome, denominazione, idUfficioUtente );

		if( listaComunicazioniConservatoriaForm == null || listaComunicazioniConservatoriaForm.size() == 0 )
		{
			listaComunicazioniConservatoriaForm = new ArrayList<ComunicazioneListaForm>();
		}
		return listaComunicazioniConservatoriaForm;
	}
	
	
	private static boolean isFloat(String num) {
		try {
			Float.parseFloat(num);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}