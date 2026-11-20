package it.giustizia.smp.controller.conservatorie;

import it.giustizia.smp.controller.BreadcrumbsController;
import it.giustizia.smp.controller.MenuController;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.ProfiloForm;
import it.giustizia.smp.form.conservatorie.ComunicazioneInsertForm;
import it.giustizia.smp.model.TAnUfficio;
import it.giustizia.smp.service.CommonService;
import it.giustizia.smp.service.ConservatorieService;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
public class InserimentoComunicazioniController extends BreadcrumbsController
{
	@Autowired
	private CommonService        commonService;

	@Autowired
	private ConservatorieService conservatorieService;

	@ModelAttribute( "categorieAtti" )
	public List<ComboForm> getCategorieAtti( HttpServletRequest request )
	{
		return conservatorieService.getCategorieAtti( "inserimento",(Integer) request.getSession().getAttribute("idTipoUfficio") );
	}

    @ModelAttribute( "categorieAttiNonSequestri" )
    public List<ComboForm> getCategorieAttiNonSequestri( HttpServletRequest request )
    {
        return conservatorieService.getCategorieAttiNonSequestri( (Integer) request.getSession().getAttribute("idTipoUfficio"));
    }

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

    private boolean controlli( ComunicazioneInsertForm form, String tipoNota, String catAtto, String tipoAtto, Integer annoRepertorio, 
		Integer numeroRepertorio, String conservatoria, String codiceFiscale, String titoloDescr,
		Date dataTitolo, String pubblicoUfficiale, String comune,  String specieAtto,
		String specieComunicazione,String descAtto, String valore, String dataFormalita, String numeroFormalita1, String decisorio, BindingResult result )
	{
		
		@SuppressWarnings( "unused" )
        int campiMancanti = 0;
		if( tipoNota == null || tipoNota.equals( "-1" ) )
		{
			campiMancanti += mancante( result, "Tipo" );
			return false;
		}
		if( catAtto == null || catAtto.equals( "-1" ) )
		{
			campiMancanti += mancante( result, "Tipo Atto" );
			return false;
		}
		
		// Verifico congruenza tra tipo nota e decisorio dell'atto
		if (tipoNota.equals("A") && decisorio.equals("S")) {
			super.setMessage( Constants.MESSAGE, "conservatoria.error.incongruenzaDecisorio", null, false );
			result.reject( Constants.MESSAGE );
			return false;
		}
		
		int anno = Calendar.getInstance().get( Calendar.YEAR );
		Date oggi = new Date();
		if( annoRepertorio == null  )
		{
			super.setMessage( Constants.MESSAGE, "conservatoria.error.annoRepertorioObb", null, false );
			result.reject( Constants.MESSAGE );
			return false;
		}
		if(  annoRepertorio > anno )
		{
			super.setMessage( Constants.MESSAGE, "conservatoria.error.annoRepertorio", null, false );
			result.reject( Constants.MESSAGE );
			return false;
		}
		if( numeroRepertorio == null )
		{
			campiMancanti += mancante( result, "Numero " );
			return false;
		}
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
		if (tipoNota.equals("A") ) 
		{
			if( specieAtto == null || specieAtto.equals( "-1" ) )
			{
				campiMancanti += mancante( result, "Atto specie" );
				return false;
			}
			
			if( specieComunicazione == null || specieComunicazione.equals( "-1" ) )
			{
				campiMancanti += mancante( result, "Descrizione Atto" );
				return false;
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
		}
		if(  tipoNota.equals( "T" ) && (descAtto==null) )
		{
			campiMancanti += mancante( result, "Descrizione Atto" );
			return false;
		}
		if(  tipoNota.equals( "A" ) && (dataFormalita == null) )
		{
			super.setMessage( Constants.MESSAGE, "conservatoria.error.dataFormalitaInserimento", null, false );
			result.reject( Constants.MESSAGE );
			return false;
		}
		if(  tipoNota.equals( "A" ) && (numeroFormalita1 == null) )
		{
			super.setMessage( Constants.MESSAGE, "conservatoria.error.numeroFormalita1", null, false );
			result.reject( Constants.MESSAGE );
			return false;
		}
		return true;
	}

	private int mancante( BindingResult result, String params )
	{
		super.setMessage( Constants.MESSAGE, "conservatoria.error.campoObbligatorio", params, false );
		result.reject( Constants.MESSAGE );
		return 1;
	}

	@RequestMapping( value = "/conservatorie/inserimentoComunicazione.htm", method = RequestMethod.POST, params = { "Conferma" } )
	public String inserisciComunicazione(@Valid ComunicazioneInsertForm form, BindingResult result, ModelMap modelMap, HttpServletRequest request )
	{
		Integer idUfficioUtente = (Integer)request.getSession().getAttribute( "idUffOwner" );
		String descUtente = (String)request.getSession().getAttribute( "codUtente" ) ;
		form.setDescUtente(descUtente);
		String tipoNota = form.getTipoNota().getId();
		String catAtto = null;
		String tipoAtto = null;
		String decisorio = null;
        // String catTipoAtto = form.getCategorieAtti().getId(); // sulla jsp : tipoAtto - ottengo: "categoriaAtto;tipoAtto;decisorio"
		String catTipoAtto = form.getCategoriaAtto();
		if( catTipoAtto != null )
		{
			int sepInd = catTipoAtto.indexOf( ';' );
			if( sepInd > 0 )
			{
				catAtto = catTipoAtto.substring( 0, sepInd );
				catTipoAtto = catTipoAtto.substring(catAtto.length()+1, catTipoAtto.length());
				sepInd = catTipoAtto.indexOf( ';' );
				tipoAtto = catTipoAtto.substring( 0, sepInd );
				catTipoAtto = catTipoAtto.substring(tipoAtto.length()+1, catTipoAtto.length());
				decisorio = catTipoAtto;
						
				form.setCatAtto( catAtto );
				form.setTipoAtto( tipoAtto );
			}
		}
		Integer annoRepertorio = form.getRepertorioAnno();
		Integer numeroRepertorio = form.getNumero();
		String conservatoria = form.getConservatoria().getId();
		conservatoria = form.getDescrConservatoria();
		String codiceFiscale = form.getCodiceFiscale();
		String titoloDescr = form.getTitoloDescrizione();
		Date dataTitolo = form.getDataTitolo();
		String pubblicoUfficiale = form.getPubblicoUfficiale();
		String comune = form.getComune().getDescrizione();
		Short progressivo = form.getProgressivo();
		String descAtto = form.getTipoComunicT();
		
		String dataFormalita = null;
		String numeroFormalita1 = null;
		if (form.getTrascrizioneDel() != null) {
			dataFormalita = form.getTrascrizioneDel().toString();
		}
		if (form.getNumeroTrascrizione1() != null) {
			numeroFormalita1 = form.getNumeroTrascrizione1().toString();
		}
		
		String valore = null;
		if (form.getValoreImmobLib() != null) {
			valore = form.getValoreImmobLib();
			valore = valore.replace(',' , '.');
			if (isFloat(valore)) {
				form.setValoreImmobLib(valore);
			}
		}
		
		String ritorno = "/conservatorie/inserimentoComunicazione";
		if (tipoNota.equals("A") ) { 
			form.setAttoSpecie(form.getAttoSpecieA());
		}
		if (tipoNota.equals("T") ) {
			form.setAttoSpecie(form.getAttoSpecieT());
			form.setDescAtto(form.getTipoComunicT());
		}
		String specieAtto = null;
		
		if( form.getAttoSpecie() != null && !form.getAttoSpecie().getId().equals("-1"))
			specieAtto = form.getAttoSpecie().getId();
		
		String specieComunicazione = null;
		
		ComboForm tipoCom1=new ComboForm();	
		if(specieAtto !=null){
			if (!form.getTipoComunicAnn().getId().equals("-1") ){
				specieComunicazione=form.getTipoComunicAnn().getId();
				tipoCom1.setId((form.getTipoComunicAnn().getId()));
				form.setTipoComunic(tipoCom1);
			}
			if (!form.getTipoComunicTrasc().getId().equals("-1") ){
				specieComunicazione=form.getTipoComunicTrasc().getId();
				tipoCom1.setId((form.getTipoComunicTrasc().getId()));
				form.setTipoComunic(tipoCom1);
			}
		}

		String rettifica = form.getRettifica().getId();
		if (rettifica.equals("-1" )) {
			rettifica = null;
		}

		ComboForm tipoCom=new ComboForm();		 
		if (tipoNota.equals( "T" )) {
			if (rettifica != null ) {
				tipoCom.setId(rettifica + "600");
				form.setTipoComunic(tipoCom);
			}else{
				form.setTipoComunic(null);
			}
		}
		modelMap.addAttribute( "comunicazioneInsertForm", form );
		
		if( !controlli( form, tipoNota, catAtto, tipoAtto, annoRepertorio, numeroRepertorio, conservatoria, codiceFiscale, 
				        titoloDescr,dataTitolo, pubblicoUfficiale, comune,  specieAtto, specieComunicazione, descAtto, valore, 
				        dataFormalita, numeroFormalita1, decisorio, result ) )
			return ritorno;
		
		// Controllo coerenza DataTrascrizione
		if(form.getTrascrizioneDel() != null ) { 
			if (form.getNumeroTrascrizione1() == null) {
				super.setMessage( Constants.MESSAGE, "conservatoria.error.dataTrascrizione", null, false );
				result.reject( Constants.MESSAGE );
				return ritorno;
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

		Long idProcedimento = conservatorieService.getIdProcedimento( idUfficioUtente, annoRepertorio, numeroRepertorio, tipoAtto, catAtto );
		if( idProcedimento == null )
		{   
		    ProfiloForm profilo = this.recuperaProfilo(); 
		    String descUfficio = profilo.getDescrizioneUfficio() + " di " + profilo.getDistretto();
            String params = "" + annoRepertorio + ";" + numeroRepertorio + ";" + descUfficio + ";" + form.getDescrCategoriaAtto() + "";
//          String params = "" + annoRepertorio + ";" + numeroRepertorio + ";" + idUfficioUtente + "";
			super.setMessage( Constants.MESSAGE, "conservatoria.error.procedimentoInesistente", params, false );
			result.reject( Constants.MESSAGE );
			return ritorno;
		}
		form.setIdProcedimento( idProcedimento );
		List<Short> listaProgressivi = conservatorieService.cercaProgressivoNota( idUfficioUtente, annoRepertorio,
		    numeroRepertorio );
		if( listaProgressivi == null || listaProgressivi.isEmpty() )
		{
			if( progressivo == null )
			    progressivo = 1;
		}   else {
				String progressivi = "";
			for( Short p : listaProgressivi )
			{
				if( progressivi.length() > 0 ) progressivi = progressivi + ", ";
				progressivi = progressivi + "" + p;
			}
			if( progressivo == null )
			{
				super.setMessage( Constants.MESSAGE, "conservatoria.error.inserireProgressivo", progressivi, false );
				result.reject( Constants.MESSAGE );
				return ritorno;
			} else {
				for( Short p : listaProgressivi )
				{
					if( progressivo.equals(p) )
					{
						super.setMessage( Constants.MESSAGE, "conservatoria.error.inserireProgressivo", progressivi, false );
						result.reject( Constants.MESSAGE );
						return ritorno;
					}
				}
			}
		}
		
		form.setProgressivo(progressivo);
		form.setflgStato('0');
		form.setflgNotaCollegata(null);
		form.setCategoria( '2' );

		Long idNota = conservatorieService.inserisciNota( form ,idUfficioUtente);
		form.setNumeroComunicazione( idNota );  
		request.getSession().setAttribute( "idNota", idNota );
		super.setMessage( Constants.SUCCESS_MESSAGE, "success.insersciNota", " ", true );
		modelMap.addAttribute( "comunicazioneInsertForm", form );
		return ritorno;
	}

	@RequestMapping( value = "/conservatorie/inserimentoComunicazione.htm", method = RequestMethod.GET )
	public ModelAndView inserisciComunicazione( Model model, HttpServletRequest request )
	{
		MenuController.caricaMenu( Constants.ID_K_MENU_CONSERVATORIE, request );
		ComunicazioneInsertForm inserimentoComunicazione = new ComunicazioneInsertForm();
		Integer idUffOwn = (Integer)request.getSession().getAttribute( "idUffOwner" );
        TAnUfficio ufficio = conservatorieService.getDettaglioUfficio(idUffOwn);
        if (ufficio.getDescCfPu() != null){
            inserimentoComunicazione.setCodiceFiscale(ufficio.getDescCfPu());
        }
        if (ufficio.getDescPubblUff() != null){
            inserimentoComunicazione.setPubblicoUfficiale(ufficio.getDescPubblUff());
        }
        ComboForm comboComune = conservatorieService.getComuneByUfficio( idUffOwn );
        
        inserimentoComunicazione.setComune( comboComune ); 
        inserimentoComunicazione.setDataTitolo( new Date() );

        // value="ATTO GIUDIZIARIO" SU ATTRIBUTO tipoComunicT
        inserimentoComunicazione.setTipoComunicT("ATTO GIUDIZIARIO");
        // value="ATTO GIUDIZIARIO" SU ATTRIBUTO tipoComunicT
        inserimentoComunicazione.setTitoloDescrizione("ATTO GIUDIZIARIO");
        
		ModelAndView mnv = new ModelAndView("/conservatorie/inserimentoComunicazione", "comunicazioneInsertForm", inserimentoComunicazione );
		cleanBreadcrumb(request.getSession());
		addBreadcrumbs();
		return mnv;
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