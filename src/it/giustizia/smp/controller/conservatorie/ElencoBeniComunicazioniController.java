package it.giustizia.smp.controller.conservatorie;

import it.giustizia.smp.controller.BreadcrumbsController;
import it.giustizia.smp.controller.MenuController;
import it.giustizia.smp.controller.procedimento.BeneController;
import it.giustizia.smp.dto.pg_conservatorie.ComuneProvinciaDto;
import it.giustizia.smp.form.BeneForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.conservatorie.ComunicazioneBeniForm;
import it.giustizia.smp.form.conservatorie.NotaDettaglioForm;
import it.giustizia.smp.service.CommonService;
import it.giustizia.smp.service.ConservatorieService;
import it.giustizia.smp.utility.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.AutoPopulatingList;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ElencoBeniComunicazioniController extends BreadcrumbsController
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
	
    @Autowired
    private TitoliDirittoComunicazioniController titoliDiritto;


    @Autowired
    private BeneController beneController;


	
	@RequestMapping( value = "/conservatorie/beniComunicazione.htm", method = RequestMethod.GET )
	public ModelAndView elencoBeniComunicazione( Model model, HttpServletRequest request )
	{
		ComunicazioneBeniForm comunicazioneBeniForm = new ComunicazioneBeniForm();
		
		Long idNota = (Long)request.getSession().getAttribute( "idNota" );
		completaForm(comunicazioneBeniForm, idNota, request);
		
		if (comunicazioneBeniForm.getListaBeni() != null ) {
			if (comunicazioneBeniForm.getListaBeni().isEmpty()) {
				if (comunicazioneBeniForm.getListaBeniAssociabili().isEmpty()) {
			           super.setMessage( Constants.MESSAGE, "conservatoria.listaBeniAssociabiliVuota", " ", false );
				}
			}
		} else {
			if (comunicazioneBeniForm.getListaBeniAssociabili().isEmpty()) {
		           super.setMessage( Constants.MESSAGE, "conservatoria.listaBeniAssociabiliVuota", " ", false );
			}
		}

		MenuController.caricaMenu( Constants.ID_K_MENU_CONSERVATORIE_AGGIORNA_COMUNICAZIONE, request );
		ModelAndView mnv = new ModelAndView( "/conservatorie/elencoBeniComunicazione", "comunicazioneBeniForm", comunicazioneBeniForm );
		addBreadcrumbs();
		return mnv;
	}
	
	
	@RequestMapping( value = "/conservatorie/beniComunicazione.htm", method = RequestMethod.POST, params = {"Dettaglio"} )
	public String dettaglioBeneComunicazione(ComunicazioneBeniForm comunicazioneBeniForm, BindingResult result, ModelMap modelMap , HttpServletRequest request)
	{
		comunicazioneBeniForm.setTipoOperazione("Dettaglio");
		String ritorno = "/conservatorie/dettaglioBeneComunicazione";
		if (comunicazioneBeniForm.getBeneSelected() == null) {
			super.setMessage( Constants.MESSAGE, "table.item.selection", null, false );
			result.reject( Constants.MESSAGE );
			completaForm(comunicazioneBeniForm, (Long)request.getSession().getAttribute( "idNota" ), request);
			return "/conservatorie/elencoBeniComunicazione";
		}
		// CHIAMATA AL SERVICE
		Long idNota = (Long)request.getSession().getAttribute( "idNota" );
		Integer idUffOwn = (Integer)request.getSession().getAttribute( "idUffOwner" );
		comunicazioneBeniForm = conservatorieService.dettaglioNotaBene(comunicazioneBeniForm, idNota, idUffOwn);
		
		// Gestione visualizzazione sezioni Identificazione Catastale o Denuncia di accatastamento
		if ((comunicazioneBeniForm.getDescSezione() != null || 
				comunicazioneBeniForm.getDescFoglio() != null 	||
					comunicazioneBeniForm.getDescParticella1() != null 	||
						comunicazioneBeniForm.getDescSubalterno1() != null ) 
			)
		{
			request.setAttribute("visualizzaIdentificazione", "si");
		}
		
		if ((comunicazioneBeniForm.getDescTipoDenuncia() != null || 
				comunicazioneBeniForm.getNumeDenuncia1() != null 	||
					comunicazioneBeniForm.getNumeAnnoDenuncia() != null 	 ) 
			)
		{
			request.setAttribute("visualizzaDenuncia", "si");
		}
		return ritorno;
	}
	
	@RequestMapping( value = "/conservatorie/beniComunicazione.htm", method = RequestMethod.POST, params = {"Modifica"} )
	public String modificaBeneComunicazione(ComunicazioneBeniForm comunicazioneBeniForm, BindingResult result, ModelMap modelMap , HttpServletRequest request)
	{
		comunicazioneBeniForm.setTipoOperazione("Modifica");
		String ritorno = "/conservatorie/dettaglioBeneComunicazione";
		if (comunicazioneBeniForm.getBeneSelected() == null) {
			super.setMessage( Constants.MESSAGE, "table.item.selection", null, false );
			result.reject( Constants.MESSAGE );
			completaForm(comunicazioneBeniForm, (Long)request.getSession().getAttribute( "idNota" ), request);
			return "/conservatorie/elencoBeniComunicazione";
		}
		// CHIAMATA AL SERVICE
		Long idNota = (Long)request.getSession().getAttribute( "idNota" );
		Integer idUffOwn = (Integer)request.getSession().getAttribute( "idUffOwner" );
		comunicazioneBeniForm = conservatorieService.dettaglioNotaBene(comunicazioneBeniForm, idNota, idUffOwn);
		comunicazioneBeniForm.setCodiceCatastale1POld(comunicazioneBeniForm.getCodiceCatastale1P());
		if( !comunicazioneBeniForm.getStato().equals( '0' ) && !comunicazioneBeniForm.getStato().equals( '1' ) )
		{
			super.setMessage( Constants.MESSAGE, "conservatoria.error.variazioneNonAmmessa", null, false );
		}


		//Genera lista comuni conservatoria
		generaListaComuniCanceleria(comunicazioneBeniForm, modelMap);
		
		gestisciAbilitazioneCampi(comunicazioneBeniForm);
		return ritorno;
	}
	
	@RequestMapping( value = "/conservatorie/beniComunicazione.htm", method = RequestMethod.POST, params = {"Conferma"} )
	public String updateBeneComunicazione(ComunicazioneBeniForm comunicazioneBeniForm, BindingResult result, ModelMap modelMap , HttpServletRequest request)
	{
		comunicazioneBeniForm.setTipoOperazione("Modifica");
		String ritorno = "/conservatorie/dettaglioBeneComunicazione";
		
		if(!controlli(comunicazioneBeniForm, result)) {
			//Genera lista comuni conservatoria
			generaListaComuniCanceleria(comunicazioneBeniForm, modelMap);
			gestisciAbilitazioneCampi(comunicazioneBeniForm);
			return ritorno;
		}
		
		// Gestione del codice catastale dei dati di origine
		if (comunicazioneBeniForm.getCodiceCatastale1P() == null) {
			if (comunicazioneBeniForm.getComuneCatastaleP().getDescrizione() != null) {
				if ("9999".equals(comunicazioneBeniForm.getComuneCatastaleP().getId()) || comunicazioneBeniForm.getComuneCatastaleP().getId() == null) {
					super.setMessage( Constants.MESSAGE, "conservatoria.error.catastoOrig", null, false );
					result.reject( Constants.MESSAGE );
					generaListaComuniCanceleria(comunicazioneBeniForm, modelMap);
					gestisciAbilitazioneCampi(comunicazioneBeniForm);
					return ritorno;
				} else {
					comunicazioneBeniForm.setCodiceCatastale1P(comunicazioneBeniForm.getComuneCatastaleP().getId());
					comunicazioneBeniForm.setCodiceCatastale1POld(comunicazioneBeniForm.getComuneCatastaleP().getId());
					comunicazioneBeniForm.setDescComuneBeneP(comunicazioneBeniForm.getComuneCatastaleP().getDescrizione());
				}
			} else {
				comunicazioneBeniForm.setCodiceCatastale1P(comunicazioneBeniForm.getCodiceCatastale1POld());
			}
		}
		
		
		Long idNota = (Long)request.getSession().getAttribute( "idNota" );
		Integer idUffOwn = (Integer)request.getSession().getAttribute( "idUffOwner" );
		String descUtente = (String)request.getSession().getAttribute( "codUtente" ) ;
		
		if ("OK".equals(conservatorieService.updateNotaBene(comunicazioneBeniForm, idNota, idUffOwn, descUtente))) {
			super.setMessage( Constants.SUCCESS_MESSAGE, "success.modificaNotaBeneOK", " ", true );
		} else {
			super.setMessage(Constants.MESSAGE,  "conservatoria.error.modificaNotabene", " ", true );
			
		}
		//Genera lista comuni conservatoria
		generaListaComuniCanceleria(comunicazioneBeniForm, modelMap);
		gestisciAbilitazioneCampi(comunicazioneBeniForm);
		return ritorno;
	}
	
	
	@RequestMapping( value = "/conservatorie/beniComunicazione.htm", method = RequestMethod.POST, params = {"Cancella"} )
	public String deleteBeneComunicazione(ComunicazioneBeniForm comunicazioneBeniForm, BindingResult result, ModelMap modelMap , HttpServletRequest request)
	{
		comunicazioneBeniForm.setTipoOperazione("Modifica");
		Long idNota = (Long)request.getSession().getAttribute( "idNota" );
		Integer idUffOwn = (Integer)request.getSession().getAttribute( "idUffOwner" );
		String descUtente = (String)request.getSession().getAttribute( "codUtente" ) ;
		String ritorno = "/conservatorie/elencoBeniComunicazione";
		completaForm(comunicazioneBeniForm, idNota, request);
		String beneSel = comunicazioneBeniForm.getBeneSelected();
		Long progrNotaBene = Long.valueOf(beneSel);
		
		if ("OK".equals(conservatorieService.cancellaNotaBene(idNota, idUffOwn, progrNotaBene, descUtente))){
			completaForm(comunicazioneBeniForm, idNota, request);
			super.setMessage( Constants.SUCCESS_MESSAGE, "success.cancellazioneNotaBeneOK", " ", true );
		} else {
			super.setMessage(Constants.MESSAGE,  "conservatoria.error.cancellazioneNotabene", " ", true );
			
		}
		return ritorno;
	}
	
	
	@RequestMapping( value = "/conservatorie/beniComunicazione.htm", method = RequestMethod.POST, params = {"BeneSitMP"} )
	public String beneSitMP(ComunicazioneBeniForm comunicazioneBeniForm, BindingResult result, ModelMap modelMap , HttpServletRequest request)
	{
		comunicazioneBeniForm.setTipoOperazione("Modifica");
		String ritorno = "/conservatorie/elencoBeniComunicazione";
		if (comunicazioneBeniForm.getBeneAssociabileSelected() == null) {
			super.setMessage( Constants.MESSAGE, "table.item.selection", null, false );
			result.reject( Constants.MESSAGE );
			completaForm(comunicazioneBeniForm, (Long)request.getSession().getAttribute( "idNota" ), request);
			return "/conservatorie/elencoBeniComunicazione";
		}

		BeneForm beneForm = new BeneForm();
		beneForm.setListaIdCheckbox(new AutoPopulatingList<Long>(Long.class));
		beneForm.getListaIdCheckbox().add(Long.valueOf(comunicazioneBeniForm.getBeneAssociabileSelected()));
		
		modelMap.addAttribute("comunicazioneBeniForm", comunicazioneBeniForm);

		ritorno = beneController.dettaglioBene(beneForm, result, request, modelMap);
		return ritorno;
	}
	
	
	@RequestMapping( value = "/conservatorie/beniComunicazione.htm", method = RequestMethod.POST, params = {"Associa"} )
	public String associa(ComunicazioneBeniForm comunicazioneBeniForm, BindingResult result, ModelMap modelMap, HttpServletRequest request )
	{
		comunicazioneBeniForm.setTipoOperazione("Modifica");
		Long idNota = (Long)request.getSession().getAttribute( "idNota" );
		Integer idUffOwn = (Integer)request.getSession().getAttribute( "idUffOwner" );
		String descUtente = (String)request.getSession().getAttribute( "codUtente" ) ;
		String ritorno = "/conservatorie/elencoBeniComunicazione";
		completaForm(comunicazioneBeniForm, idNota, request);
		String beneSel = comunicazioneBeniForm.getBeneAssociabileSelected();
		Long idBene = Long.valueOf(beneSel);
		
		if ("OK".equals(conservatorieService.associaNotaBene(idNota, idUffOwn, idBene, descUtente))){
			completaForm(comunicazioneBeniForm, idNota, request);
			super.setMessage( Constants.SUCCESS_MESSAGE, "success.associazioneNotaBeneOK", " ", true );
		} else {
			super.setMessage(Constants.MESSAGE,  "conservatoria.error.associazioneNotabene", " ", true );
			
		}
		return ritorno;
	}
	
	
	@RequestMapping( value = "/conservatorie/beniComunicazione.htm", method = RequestMethod.POST, params = {"TitoliDiritto"} )
	public String titoliDiritto(ComunicazioneBeniForm comunicazioneBeniForm, BindingResult result, ModelMap modelMap , HttpServletRequest request)
	{	
		if (comunicazioneBeniForm.getBeneSelected() == null) {
			super.setMessage( Constants.MESSAGE, "table.item.selection", null, false );
			result.reject( Constants.MESSAGE );
			completaForm(comunicazioneBeniForm, (Long)request.getSession().getAttribute( "idNota" ), request);
			return "/conservatorie/elencoBeniComunicazione";
		}
		Long idNota = (Long)request.getSession().getAttribute( "idNota" );
		Integer idUffOwn = (Integer)request.getSession().getAttribute( "idUffOwner" );
		comunicazioneBeniForm = conservatorieService.dettaglioNotaBene(comunicazioneBeniForm, idNota, idUffOwn);
		titoliDiritto.titoliDiritto(comunicazioneBeniForm, request);
		return "/conservatorie/titoliDirittoComunicazione";
	}
	
	
	@RequestMapping( value = "/conservatorie/beniComunicazione.htm", method = RequestMethod.POST, params = { "Indietro" } )
	public String indietroElencoBeni( ComunicazioneBeniForm comunicazioneBeniForm, BindingResult result, ModelMap modelMap, HttpServletRequest request )
	{
		Long idNota = (Long)request.getSession().getAttribute( "idNota" );
		completaForm(comunicazioneBeniForm, idNota, request);
		modelMap.addAttribute( "comunicazioneBeniForm", comunicazioneBeniForm );
		return "/conservatorie/elencoBeniComunicazione";
	}
	
	
	
	private void completaForm(ComunicazioneBeniForm comunicazioneBeniForm, Long idNota, HttpServletRequest request)
	{
		Integer idUfficioUtente = (Integer)request.getSession().getAttribute( "idUffOwner" );
		NotaDettaglioForm dettaglio = conservatorieService.dettaglioNota( idUfficioUtente, idNota );
		
		if( dettaglio != null )
		{
			comunicazioneBeniForm.setNumeroComunicazione(dettaglio.getNumero().longValue());
			comunicazioneBeniForm.setStato(dettaglio.getStato());
			comunicazioneBeniForm.setDescTipoNota(dettaglio.getDescTipoNota());
			comunicazioneBeniForm.setDescAtto(dettaglio.getDescAtto());
			comunicazioneBeniForm.setDescCatAtto(dettaglio.getDescCatAtto());
			comunicazioneBeniForm.setRepertorioAnno(dettaglio.getRepertorioAnno() );
			comunicazioneBeniForm.setNumeroRepertorio1(dettaglio.getNumeroRepertorio1().longValue());
			comunicazioneBeniForm.setProgressivoNota(dettaglio.getProgressivo());
			comunicazioneBeniForm.setConservatoria(dettaglio.getConservatoria());
			comunicazioneBeniForm.setConservatoriaId(dettaglio.getConservatoriaId());
			comunicazioneBeniForm.setCodiceFiscale(dettaglio.getCodiceFiscale());
			comunicazioneBeniForm.setTitoloDescrizione(dettaglio.getTitoloDescrizione());
			comunicazioneBeniForm.setData(dettaglio.getData());
			comunicazioneBeniForm.setListaBeni(dettaglio.getListaBeni());
			comunicazioneBeniForm.setListaBeniAssociabili(dettaglio.getListaBeniAssociabili());
		}
	}
	
	private void gestisciAbilitazioneCampi(ComunicazioneBeniForm comunicazioneBeniForm)
	{
		// Gestione sezione dati catastali
		if ((comunicazioneBeniForm.getTipoDenuncia() != null 	|| 
				comunicazioneBeniForm.getNumeDenuncia1() != null 	||
					comunicazioneBeniForm.getNumeDenuncia2() != null 	||
						comunicazioneBeniForm.getNumeAnnoDenuncia() != null ) 
			&& 
			(comunicazioneBeniForm.getDescSezione() == null 	&& 
				comunicazioneBeniForm.getDescFoglio() == null 		&&
					comunicazioneBeniForm.getDescParticella1() == null 	&&
						comunicazioneBeniForm.getDescParticella2() == null 	&&
							comunicazioneBeniForm.getDescSubalterno1() == null 	&&
								comunicazioneBeniForm.getDescSubalterno2() == null))
		{
			comunicazioneBeniForm.setDenunciaAccatastamento(true);
		}
		
		if ((comunicazioneBeniForm.getTipoDenuncia() == null 	&& 
				comunicazioneBeniForm.getNumeDenuncia1() == null 	&&
					comunicazioneBeniForm.getNumeDenuncia2() == null 	&&
						comunicazioneBeniForm.getNumeAnnoDenuncia() == null ) 
			&& 
			(comunicazioneBeniForm.getDescSezione() != null 	|| 
				comunicazioneBeniForm.getDescFoglio() != null 		||
					comunicazioneBeniForm.getDescParticella1() != null 	||
						comunicazioneBeniForm.getDescParticella2() != null 	||
							comunicazioneBeniForm.getDescSubalterno1() != null 	||
								comunicazioneBeniForm.getDescSubalterno1() != null))
		{
			comunicazioneBeniForm.setIdentificazioneCatastale(true);
		}
		
		// Abilitazione campi sezione dati catastali
		if (comunicazioneBeniForm.isDenunciaAccatastamento() && comunicazioneBeniForm.isIdentificazioneCatastale()) {
			comunicazioneBeniForm.setDisabledCampiIdentificazione(false);
			comunicazioneBeniForm.setDisabledCampiDenuncia(false);
		} else {
			if (comunicazioneBeniForm.isDenunciaAccatastamento()) {
				comunicazioneBeniForm.setDisabledCampiIdentificazione(true);
				comunicazioneBeniForm.setDenunciaAccatastamentoChecked(true);
			} else {
				comunicazioneBeniForm.setDenunciaAccatastamentoChecked(false);
			}
			
			if (comunicazioneBeniForm.isIdentificazioneCatastale()) {
				comunicazioneBeniForm.setDisabledCampiDenuncia(true);
				comunicazioneBeniForm.setIdentificazioneCatastaleChecked(true);
			} else {
				comunicazioneBeniForm.setIdentificazioneCatastaleChecked(false);
			}
		}
		
		// Gestione sezione dati catastali Origine
		if ((comunicazioneBeniForm.getTipoDenunciaP() != null 	|| 
				comunicazioneBeniForm.getNumeDenuncia1P() != null 	||
					comunicazioneBeniForm.getNumeDenuncia2P() != null 	||
						comunicazioneBeniForm.getNumeAnnoDenunciaP() != null ) 
			&& 
			(comunicazioneBeniForm.getDescSezioneP() == null 	&& 
				comunicazioneBeniForm.getDescFoglioP() == null 		&&
					comunicazioneBeniForm.getDescParticella1P() == null 	&&
						comunicazioneBeniForm.getDescParticella2P() == null 	&&
							comunicazioneBeniForm.getDescSubalterno1P() == null 	&&
								comunicazioneBeniForm.getDescSubalterno2P() == null))
		{
			comunicazioneBeniForm.setDenunciaAccatastamentoP(true);
		}
		
		if ((comunicazioneBeniForm.getTipoDenunciaP() == null 	&& 
				comunicazioneBeniForm.getNumeDenuncia1P() == null 	&&
					comunicazioneBeniForm.getNumeDenuncia2P() == null 	&&
						comunicazioneBeniForm.getNumeAnnoDenunciaP() == null ) 
			&& 
			(comunicazioneBeniForm.getDescSezioneP() != null 	|| 
				comunicazioneBeniForm.getDescFoglioP() != null 		||
					comunicazioneBeniForm.getDescParticella1P() != null 	||
						comunicazioneBeniForm.getDescParticella2P() != null 	||
							comunicazioneBeniForm.getDescSubalterno1P() != null 	||
								comunicazioneBeniForm.getDescSubalterno1P() != null))
		{
			comunicazioneBeniForm.setIdentificazioneCatastaleP(true);
		}
		
		
		// Abilitazione campi sezione dati catastali origine
		if (comunicazioneBeniForm.isDenunciaAccatastamentoP() && comunicazioneBeniForm.isIdentificazioneCatastaleP()) {
			comunicazioneBeniForm.setDisabledCampiIdentificazioneP(false);
			comunicazioneBeniForm.setDisabledCampiDenunciaP(false);
		} else {
			if (comunicazioneBeniForm.isDenunciaAccatastamentoP()) {
				comunicazioneBeniForm.setDisabledCampiIdentificazioneP(true);
			}
			
			if (comunicazioneBeniForm.isIdentificazioneCatastaleP()) {
				comunicazioneBeniForm.setDisabledCampiDenunciaP(true);
			}
		}
		
		// Gestione abilitazione campi input
		if (comunicazioneBeniForm.getTipoCatasto() != null) {
			if (comunicazioneBeniForm.getTipoCatasto().equals('U')) {
				comunicazioneBeniForm.setDisabledCampi(false);
			} else {
				comunicazioneBeniForm.setDisabledCampi(true);
			}
		}
		
		if (comunicazioneBeniForm.getTipoCatasto() != null) {
			if (comunicazioneBeniForm.getTipoCatasto().equals('U')) {
				comunicazioneBeniForm.setDisabledCampiT(true);
			} else {
				comunicazioneBeniForm.setDisabledCampiT(false);
			}
		}
	}
	
	private int mancante( BindingResult result, String params )
	{
		super.setMessage( Constants.MESSAGE, "conservatoria.error.campoObbligatorio", params, false );
		result.reject( Constants.MESSAGE );
		return 1;
	}
	
    private boolean controlli( ComunicazioneBeniForm comunicazioneBeniForm, BindingResult result )
	{
		@SuppressWarnings( "unused" )
        int campiMancanti = 0;
		
			if (comunicazioneBeniForm.getStato().equals('2')) {
				super.setMessage( Constants.MESSAGE, "conservatoria.error.statoIncoerente", null, false );
				result.reject( Constants.MESSAGE );
				return false;
			}
		
			// CAMPI OBBLIGATORI
			if(comunicazioneBeniForm.getComuneConservatoria() == null || comunicazioneBeniForm.getComuneConservatoria().equals("")) {
				campiMancanti += mancante( result, "Comune" );
				return false;
			}
			
			if(comunicazioneBeniForm.getCodiceCatastale1() == null || comunicazioneBeniForm.getCodiceCatastale1().equals("")) {
				campiMancanti += mancante( result, "Codice Catastale" );
				return false;
			}
			
			if(comunicazioneBeniForm.getTipoCatasto() == null || comunicazioneBeniForm.getTipoCatasto().equals("")) {
				campiMancanti += mancante( result, "Catasto" );
				return false;
			}
			
			if(comunicazioneBeniForm.getIdNaturaImmobile() == null || comunicazioneBeniForm.getIdNaturaImmobile().equals("")) {
				campiMancanti += mancante( result, "Natura immobile" );
				return false;
			}
			
			// Verifico Foglio e Particella solo se ho selezionato flag identificazioneCatastale 
			if (comunicazioneBeniForm.isIdentificazioneCatastaleChecked()) {
				
				if(comunicazioneBeniForm.getDescFoglio() == null || comunicazioneBeniForm.getDescFoglio().equals("")) {
						campiMancanti += mancante( result, "Foglio" );
						return false;
				}
				
				if(comunicazioneBeniForm.getDescParticella1() == null || comunicazioneBeniForm.getDescParticella1().equals("")) {
					campiMancanti += mancante( result, "Particella" );
					return false;
				}
			}
			
			// Verifico Tipo Numeri e Anno Denuncia solo se ho selezionato flag denunciaAccatastamento 
			if (comunicazioneBeniForm.isDenunciaAccatastamentoChecked()) {
				
				if(comunicazioneBeniForm.getTipoDenuncia() == null || comunicazioneBeniForm.getTipoDenuncia().equals("")) {
						campiMancanti += mancante( result, "Tipo denuncia" );
						return false;
				}
				
				if(comunicazioneBeniForm.getNumeDenuncia1() == null || comunicazioneBeniForm.getNumeDenuncia1().equals("")) {
					campiMancanti += mancante( result, "Numero" );
					return false;
				}
				
				if(comunicazioneBeniForm.getNumeAnnoDenuncia() == null || comunicazioneBeniForm.getNumeAnnoDenuncia().equals("")) {
					campiMancanti += mancante( result, " 'del' " );
					return false;
				}
				
				if(comunicazioneBeniForm.getNumeAnnoDenuncia() != null) {
					if((""+comunicazioneBeniForm.getNumeAnnoDenuncia()).length() != 4) {
						String params = " 'del' ";
						super.setMessage( Constants.MESSAGE, "conservatoria.error.verificaAnno", params, false );
						result.reject( Constants.MESSAGE );
						return false;
					}
				}
				
				
			}
			
			// Gestione sezione dati catastali
			if ((comunicazioneBeniForm.getTipoDenuncia() != null 	|| 
					comunicazioneBeniForm.getNumeDenuncia1() != null 	||
						comunicazioneBeniForm.getNumeDenuncia2() != null 	||
							comunicazioneBeniForm.getNumeAnnoDenuncia() != null ) 
				&& 
				(comunicazioneBeniForm.getDescSezione() != null 		|| 
					comunicazioneBeniForm.getDescFoglio() != null 		||
						comunicazioneBeniForm.getDescParticella1() != null 	||
							comunicazioneBeniForm.getDescParticella2() != null 	||
								comunicazioneBeniForm.getDescSubalterno1() != null 	||
									comunicazioneBeniForm.getDescSubalterno2() != null))
			{
				super.setMessage( Constants.MESSAGE, "conservatoria.error.catastaliIncoerenti", null, false );
				result.reject( Constants.MESSAGE );
				return false;
			}

			if ((comunicazioneBeniForm.getNumeVaniInt() != null 	||
					comunicazioneBeniForm.getNumeVaniDec() != null 		||
						comunicazioneBeniForm.getNumeMcInt() != null 		||
							comunicazioneBeniForm.getNumeMcDec() != null 		||
								comunicazioneBeniForm.getDescSezione() != null 		|| 
									comunicazioneBeniForm.getDescEdificio() != null 	|| 
										comunicazioneBeniForm.getDescScala() != null 		|| 
											comunicazioneBeniForm.getDescInterno() != null 		||
												comunicazioneBeniForm.getDescPiano() != null 		||
														comunicazioneBeniForm.getDescSezione() != null 	||
															comunicazioneBeniForm.getDescSubalterno1() != null 	||
																comunicazioneBeniForm.getDescSubalterno2() != null)
				&& (comunicazioneBeniForm.getTipoCatasto() != 'U'))
				
			{
				super.setMessage( Constants.MESSAGE, "conservatoria.error.tipoCatastali", null, false );
				result.reject( Constants.MESSAGE );
				return false;
			}
			
			String numVani =   null;
			String metriCubi = null;
			String metriQuad = null;
			
			if (comunicazioneBeniForm.getNumeVaniInt() != null || comunicazioneBeniForm.getNumeVaniDec()  != null){
				numVani =   "" + comunicazioneBeniForm.getNumeVaniInt() + comunicazioneBeniForm.getNumeVaniDec();
			}
			if (comunicazioneBeniForm.getNumeMcInt() != null  || comunicazioneBeniForm.getNumeMcDec() != null ) {
			    metriCubi = "" + comunicazioneBeniForm.getNumeMcInt() + comunicazioneBeniForm.getNumeMcDec();
			}
			if (comunicazioneBeniForm.getNumeSupMqInt() != null || comunicazioneBeniForm.getNumeSupMqDec() != null) {
			    metriQuad = "" + comunicazioneBeniForm.getNumeSupMqInt() + comunicazioneBeniForm.getNumeSupMqDec();
			}
			
			if (numVani != null && metriCubi != null || numVani != null && metriQuad != null || metriCubi != null && metriQuad != null ) 
			{
				super.setMessage( Constants.MESSAGE, "conservatoria.error.tipoCatastaliAlternativa", null, false );
				result.reject( Constants.MESSAGE );
				return false;
			}
			
		return true;
	}
    
    
	private void generaListaComuniCanceleria (ComunicazioneBeniForm comunicazioneBeniForm, ModelMap modelMap) {
		List<ComuneProvinciaDto> listaComuniConservatoria = conservatorieService.getComuniByConservatoria(comunicazioneBeniForm.getConservatoriaId());
		comunicazioneBeniForm.setComuneConservatoria(comunicazioneBeniForm.getCodiceCatastale1());
		modelMap.addAttribute("listaComuniConservatoria", creaComboComuni(listaComuniConservatoria));
	}
    
	private List<ComboForm> creaComboComuni(List<ComuneProvinciaDto> listaComuniConservatoria) {
		List<ComboForm> listaCbo = new ArrayList<ComboForm>();
		for (ComuneProvinciaDto item : listaComuniConservatoria) {
				listaCbo.add(new ComboForm(String.valueOf(item.getCodiceCatasto()), String.valueOf(item.getCOMUNE())));
		}
		return listaCbo;
	}
}