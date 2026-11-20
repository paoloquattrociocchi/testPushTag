package it.giustizia.smp.controller.cameralini;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
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

import it.giustizia.smp.controller.MenuController;
import it.giustizia.smp.controller.procedimento.ApriController;
import it.giustizia.smp.dto.pg_cameralini.IntestatarioDto;
import it.giustizia.smp.dto.pg_procedimento.AssociaPersonaDto;
import it.giustizia.smp.form.AmministratoreForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.NumeroRegistroForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.cameralini.IstanzaCompensiAmministratoreForm;
import it.giustizia.smp.service.BeneService;
import it.giustizia.smp.service.ProcedimentoService;
import it.giustizia.smp.utility.Constants;

@Controller
public class RicercaAmministratoreController extends RicercaBaseController
{
	@Autowired
	private BeneService		beneService;
	@Autowired
	private ApriController 	apriController;	
	@Autowired
	private ProcedimentoService		procedimentoService;
	
	@RequestMapping( value = "/procedimento/nuovo/amministratore/ricerca.htm", method = RequestMethod.GET )
	public ModelAndView ricerca ( Model model , HttpServletRequest request)
	{
		return ricercaBase(model,"amministratore", Constants.TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM, request);  
	}
	
	@RequestMapping( value = "/procedimento/nuovo/amministratore/ricerca.htm", method = RequestMethod.POST, params = { "Ricerca" } )
	public ModelAndView elencoSoggetti( @Valid @ModelAttribute( "ricerca" ) IstanzaCompensiAmministratoreForm form, BindingResult result, ModelMap modelMap, HttpServletRequest request )
		{
		/****************** MOCK ****************/
		//int idTipoUfficio = (Integer) request.getSession().getAttribute("idTipoUfficio");
		//if(idTipoUfficio == 6)
		//{	form.getCognomeRichiedente().getBytes();
		//	form.getTrasmissione().getDescrizione();
		//}
		/*************** END MOCK ***************/
		ModelAndView mnv = new ModelAndView("/cameralini/amministratore/istanzaCompensiAmministratore", "form", form);
		form.setFunzioneCameralini("amministratore");
		if (!result.hasErrors()) {
			// INIZIO CONTROLLI VALIDATE CAMERALINI
			ProcedimentoForm procedimento = form.getProcedPadre();
			cameraliniValidation.validateCriteriRicercaPuntuale(procedimento, result);

			if (result.hasErrors()) {
				return new ModelAndView("/cameralini/ricerca", "ricerca", form);
			} else {
			
				// recupero dati ufficio da sessione
				Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");				
						
				Short anno = form.getProcedPadre().getAnno();
				Long numero = form.getProcedPadre().getNumero();
				
				request.getSession().setAttribute("idUfficioUtente",idUfficioUtente);
						
				//verifica esistenza del procedimento con chiave: numero + anno + codiceUfficio
				//ProcedimentoForm p = getProcedimentoPadre(idUfficioUtente,numero,anno,Constants.TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM);
	
				int idTipoFascicolo = Constants.TIPO_FASCICOLO_MERITO;
				
				ProcedimentoForm p = null;
				List<Long> lista = procedimentoService.getIdProcByAnnoNumProc(idUfficioUtente, anno, numero, idTipoFascicolo);
				if(lista!=null && !lista.isEmpty()) {
					p = procedimentoService.getDettaglioProcedimento(lista.get(0));
				}
	
				String error = checkMisuraLiqAmmApplicata(p,idUfficioUtente,request);
				if(error != null){
					super.setMessage( Constants.MESSAGE, error, null, true );
					return	 mnv = new ModelAndView( "/cameralini/ricerca", "ricerca", form );
				}				
	
	
				form.setProcedPadre(p);
					
				// si caricano in sessione i dati del procedimento in ProcedimentoForm 
				request.getSession().setAttribute(Constants.ID_PROCEDIMENTO, form.getProcedPadre());
				List<IntestatarioDto> listaOmonimi = new ArrayList<IntestatarioDto>();
				modelMap.addAttribute("listaOmonimi", listaOmonimi);

				List<AmministratoreForm> listaAmministratori = new ArrayList<AmministratoreForm>();
				if ((Integer) request.getSession().getAttribute("idTipoUfficio") == Constants.TIPO_UFFICIO_CORTE_D_APPELLO) {
					List<NumeroRegistroForm> fascColl = procedimentoService.getNumeriRegistro(p.getIdProcedimento());
					if(fascColl!=null && !fascColl.isEmpty()) {
						for(int i=0;i<fascColl.size();i++) {
							NumeroRegistroForm fascCollRec = fascColl.get(i);
							if (fascCollRec.getIdTipoUfficio2() == Integer.valueOf(Constants.TIPO_UFFICIO_TRIBUNALE)) {
								List<AmministratoreForm> listaAmminTrib = beneService.getAmministratori(fascCollRec.getIdUfficio2(), fascCollRec.getIdProc2(), null, null, true);
								if(listaAmminTrib!=null && !listaAmminTrib.isEmpty()) {
									boolean aggiungiAmm = true;
									for(int j=0;j<listaAmminTrib.size();j++) {
										AmministratoreForm ammTmp = listaAmminTrib.get(j);
										for(int k=0;k<listaAmministratori.size();k++) {
											AmministratoreForm ammSel = listaAmministratori.get(k);
											if(ammTmp.getId().equals(ammSel.getId())) aggiungiAmm = false;
										}
										if(aggiungiAmm) listaAmministratori.add(ammTmp);
									}
								}								
							}
						}
					}
				} else {
					listaAmministratori = beneService.getAmministratori(idUfficioUtente, p.getIdProcedimento(), null, null, true);
				}
				modelMap.addAttribute("listaAmministratori", listaAmministratori);
			}
		} else {
			return mnv = new ModelAndView("/cameralini/ricerca", "ricerca",
					form);
		}
		cleanBreadcrumb(request.getSession());
		addBreadcrumbs();
		return mnv;
	}
	
	
	@RequestMapping( value = "/procedimento/nuovo/amministratore/ricerca.htm", method = RequestMethod.POST, params = { "Indietro" } )
	public ModelAndView indietro( IstanzaCompensiAmministratoreForm form, BindingResult result, ModelMap modelMap , HttpServletRequest request)
	{
		MenuController.caricaMenu( Constants.ID_K_MENU_PROCEDIMENTO, request );
		IstanzaCompensiAmministratoreForm amministratoreForm = new IstanzaCompensiAmministratoreForm();
		amministratoreForm.setFunzioneCameralini("amministratore");
				
		ModelAndView mnv = new ModelAndView( "/cameralini/ricerca", "ricerca", amministratoreForm );
		cleanBreadcrumb(request.getSession());
		addBreadcrumbs();
		return mnv;
	}	
	
	@RequestMapping( value = "/procedimento/nuovo/amministratore/ricerca.htm", method = RequestMethod.POST, params = {"Dettaglio"} )
    public ModelAndView soggettoSitMP(IstanzaCompensiAmministratoreForm form, BindingResult result, ModelMap modelMap , HttpServletRequest request )
	{
    	ModelAndView mnv = new ModelAndView( "/cameralini/amministratore/istanzaCompensiAmministratore", "form", form );
    	form.setFunzioneCameralini("amministratore");
    	Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
    	Long anno = form.getProcedPadre().getAnno().longValue();
		Long numero = form.getProcedPadre().getNumero();
		ProcedimentoForm p = getProcedimentoPadre(idUfficioUtente,numero,anno,Constants.TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM);
    	String idSel=form.getAmministratoreSelected();
    	if (idSel != null) {
    		AmministratoreForm amministratoreForm= new AmministratoreForm();
    		amministratoreForm.setId(new Long(idSel).longValue());
    		//mnv=amministratoreController.dettaglioAmministratore(amministratoreForm, result, request, modelMap, null);
    		List<AmministratoreForm> amministratoreSel = beneService.getAmministratoreById(amministratoreForm.getId());
    		amministratoreForm = amministratoreSel.get(0);
    		if(amministratoreForm.getComuneEsteroLegale() != null){
    			amministratoreForm.setComuneDomProf(new ComboForm(null,amministratoreForm.getComuneEsteroLegale()));
    		}
    		amministratoreForm.setDettaglio("DETTAGLIO");
    		modelMap.addAttribute("amministratoreForm", amministratoreForm);
    		addBreadcrumbs("POST","/procedimento/amministratore/elencoAssociatoProcedimento.htm",null);
    		
    		return new ModelAndView("/procedimento/bene/amministratore/amministratoreAnagrafica", "amministratoreForm", amministratoreForm);
    	
    	} else {
    		 super.setMessage( Constants.MESSAGE, "table.item.selection", null, false );
             result.reject( Constants.MESSAGE );
             //List<AmministratoreForm> listaAmministratori = beneService.getAmministratori(idUfficioUtente, p.getIdProcedimento(), null, null);
             List<AmministratoreForm> listaAmministratori = procedimentoServiceCam.getAmmMisurePatrPersProcedimento(idUfficioUtente, form.getProcedPadre().getIdProcedimento());
     		 modelMap.addAttribute("listaAmministratori", listaAmministratori);
             return	 mnv = new ModelAndView( "/cameralini/amministratore/istanzaCompensiAmministratore", "form", form );
    	}
    	
	}	
	
	 @RequestMapping( value = "/procedimento/nuovo/amministratore/ricerca.htm", method = RequestMethod.POST, params = {"Registra"} )
	    public ModelAndView registra( @Valid @ModelAttribute("form")IstanzaCompensiAmministratoreForm form, BindingResult result, ModelMap modelMap , HttpServletRequest request)
		{
	    	//si acquisiscono dati da sessione
	    	Integer idUffOwn = (Integer) request.getSession().getAttribute("idUffOwner");
	    	String codUtente = (String) request.getSession().getAttribute("codUtente");
	    	List<AssociaPersonaDto> listaAssoc = null;
	    	ModelAndView mnv = new ModelAndView( "/cameralini/amministratore/istanzaCompensiAmministratore", "form", form );
	    	Long idTipoProcedimento = Constants.TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM;
	    	form.setFunzioneCameralini("amministratore");
	    	Short anno = form.getProcedPadre().getAnno();
			Long numero = form.getProcedPadre().getNumero();
			//ProcedimentoForm p = getProcedimentoPadre(idUffOwn,numero,anno,Constants.TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM);
			
			Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
			int idTipoFascicolo = Constants.TIPO_FASCICOLO_MERITO;
			
			ProcedimentoForm p = null;
			List<Long> lista = procedimentoService.getIdProcByAnnoNumProc(idUfficioUtente, anno, numero, idTipoFascicolo);
			if(lista!=null && !lista.isEmpty()) {
				p = procedimentoService.getDettaglioProcedimento(lista.get(0));
			}			
	    	
	    	ProcedimentoForm procForm =new ProcedimentoForm();
	    	
	    	String sesso = null; 	
			String tipoSoggetto = "F";
			String comuneEsteroNasc = null;
			String codiceFiscalePG = null;
			String ragioneSociale = null;
			String indirizzoSede = null;
			String descNumeCcia = null;
			String descCivico = null;
			String descCap = null;
			Long codTipoStrada = null;
			Long idComuneNascita = null;
			Long idStatoNascita = null;
			Long idTipoSocieta = null;
			Long codSoggInt = null;
			Long idVer = null;
			Long codComSede = null;
			Long codStatoSede = null;
	    	
	    	String ammSel=form.getAmministratoreSelected();
	    	if (ammSel != null ) {
	    		
    			List<AmministratoreForm> listaAmministratoriSel = beneService.getAmministratoreById(new Long(ammSel).longValue());
    			AmministratoreForm amministratore = listaAmministratoriSel.get(0);
	 
	    		procForm = preparaNuovoProcedimento(form,idTipoProcedimento);

				codSoggInt = amministratore.getId();
				
	    		//Registrazione
    			listaAssoc  = procedimentoServiceCam.inserisciProcedimentoCameraliniInt( p.getIdProcedimento(), idUffOwn.intValue(),  idTipoProcedimento.intValue(), 
	    				null,tipoSoggetto,amministratore.getNome(),amministratore.getCognome(),sesso,amministratore.getDataNascita(),
	    				comuneEsteroNasc,amministratore.getCodiceFiscale(),codiceFiscalePG,
	    				ragioneSociale,amministratore.getComuneEsteroLegale(),indirizzoSede,amministratore.getPartitaIva(),idUffOwn.intValue(),
	    				descNumeCcia,descCivico,descCap,codTipoStrada, codComSede,idComuneNascita, codStatoSede,idStatoNascita,
	    				idTipoSocieta,codSoggInt,idVer,codUtente);  			
	    			
    			
    			request.getSession().setAttribute("idProcPadre",  form.getProcedPadre().getIdProcedimento());
    			request.getSession().setAttribute("idProcFiglio", listaAssoc.get(0).getIDPROC().longValue());
    		
    			procForm.setIdProcedimento(listaAssoc.get(0).getIDPROC().longValue());
    			request.getSession().setAttribute(Constants.ID_PROCEDIMENTO, procForm);
    			
    			//richiamo la copertina 
    			return apriController.copertinaProcedimento(procForm, result, request);
    			
	    	} else {
	    		 super.setMessage( Constants.MESSAGE, "table.item.selection", null, false );
	             result.reject( Constants.MESSAGE );
	             List<AmministratoreForm> listaAmministratori = beneService.getAmministratori(idUfficioUtente, p.getIdProcedimento(), null, null, true);
	             modelMap.addAttribute("listaAmministratori", listaAmministratori);
	             return	 mnv = new ModelAndView( "/cameralini/amministratore/istanzaCompensiAmministratore", "form", form );
	    	}

	    	
		}
	 
	 @RequestMapping( value = "/procedimento/nuovo/amministratore/ricerca.htm", method = RequestMethod.POST, params = {"Nuovo"} )
	    public ModelAndView nuovo( @Valid @ModelAttribute("form")IstanzaCompensiAmministratoreForm form, BindingResult result, ModelMap modelMap , HttpServletRequest request)
		{
		 	Integer idUffOwn = (Integer) request.getSession().getAttribute("idUffOwner");
	    	String codUtente = (String) request.getSession().getAttribute("codUtente");
	    	List<AssociaPersonaDto> listaAssoc = null;
	    	
	    	Long anno = form.getProcedPadre().getAnno().longValue();
			Long numero = form.getProcedPadre().getNumero();
	    	ModelAndView mnv = new ModelAndView( "/cameralini/amministratore/istanzaCompensiAmministratore", "form", form );
	    	Long idTipoProcedimento = Constants.TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM;
	    	
	    	form.setFunzioneCameralini("amministratore");
	    	
	    	ProcedimentoForm procForm =new ProcedimentoForm();
	    	Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
	    	
	    	String sesso = null; 	
			String tipoSoggetto = "F";
			String comuneEsteroNasc = null;
			String codiceFiscalePG = null;
			String ragioneSociale = null;
			String indirizzoSede = null;
			String descNumeCcia = null;
			String descCivico = null;
			String descCap = null;
			Long codTipoStrada = null;
			Long idComuneNascita = null;
			Long idStatoNascita = null;
			Long idTipoSocieta = null;
			Long codSoggInt = null;
			Long idVer = null;
			Long codComSede = null;
			Long codStatoSede = null;
			
	    	ProcedimentoForm p = getProcedimentoPadre(idUfficioUtente,numero,anno,Constants.TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM);
	    	
	    	String ammSel=form.getAmministratoreSelected();
	    	if (ammSel != null ) {
	    		
	    			List<AmministratoreForm> listaAmministratoriSel = beneService.getAmministratoreById(new Long(ammSel).longValue());
	    			AmministratoreForm amministratore= listaAmministratoriSel.get(0);
	    			
	    			//GP Test ricerca codice comune per amministratori
	    			List<Long> listaId = null;
	    			if (amministratore.getLuogoNascita() != null){
	    				String descComune = getComuneFromComune(amministratore.getLuogoNascita());
	    				listaId = procedimentoServiceCam.getIdComuneNascita(descComune);
	    			}
	    			if (listaId != null && listaId.size() ==1) {
	    				idComuneNascita = listaId.get(0).longValue();
	    				idStatoNascita = 1L;
	    			}

	    		procForm = preparaNuovoProcedimento(form,idTipoProcedimento);
	    		
	    		if(amministratore.getComuneDomProf()!= null){
	    			codComSede   = new Long(amministratore.getComuneDomProf().getId());
				}
	    		if(amministratore.getStatoDomProf()!= null){
	    			codStatoSede   = new Long(amministratore.getStatoDomProf().getId());
	    			
				}
	    		//Registrazione
	    		
//	    		listaAssoc  = procedimentoServiceCam.inserisciProcedimentoCameraliniInt( p.getIdProcedimento(), idUffOwn.intValue(),  idTipoProcedimento.intValue(), 
//	    				null,"F",amministratore.getNome() ,amministratore.getCognome(),null,amministratore.getDataNascita(),null,amministratore.getCodiceFiscale(),null,
//	    				null,amministratore.getComuneEsteroLegale(),null,amministratore.getPartitaIva(),idUffOwn.intValue(),
//	    				null,null,null,null, null, new Long(amministratore.getComuneDomProf().getId()), new Long(amministratore.getStatoDomProf().getId()),null,null,
//	    				null,null, codUtente);

	    		listaAssoc  = procedimentoServiceCam.inserisciProcedimentoCameraliniInt( p.getIdProcedimento(), idUffOwn.intValue(),  idTipoProcedimento.intValue(), 
	    				null,tipoSoggetto,amministratore.getNome(),amministratore.getCognome(),sesso,amministratore.getDataNascita(),
	    				comuneEsteroNasc,amministratore.getCodiceFiscale(),codiceFiscalePG,
	    				ragioneSociale,amministratore.getComuneEsteroLegale(),indirizzoSede,amministratore.getPartitaIva(),idUffOwn.intValue(),
	    				descNumeCcia,descCivico,descCap,codTipoStrada, codComSede,idComuneNascita, codStatoSede,idStatoNascita,
	    				idTipoSocieta,codSoggInt,idVer,codUtente);

	    		
				procForm.setIdProcedimento(listaAssoc.get(0).getIDPROC().longValue());
				request.getSession().setAttribute(Constants.ID_PROCEDIMENTO, procForm);

				request.getSession().setAttribute("idProcPadre",  form.getProcedPadre().getIdProcedimento());
				request.getSession().setAttribute("idProcFiglio", listaAssoc.get(0).getIDPROC().longValue());
			
				procForm.setIdProcedimento(listaAssoc.get(0).getIDPROC().longValue());
				request.getSession().setAttribute(Constants.ID_PROCEDIMENTO, procForm);
				
				//richiamo la copertina 
				return apriController.copertinaProcedimento(procForm, result, request);
			
	    	} else {
	    		 
	             List<AmministratoreForm> listaAmministratori = procedimentoServiceCam.getAmmMisurePatrPersProcedimento(idUfficioUtente, form.getProcedPadre().getIdProcedimento());
	             modelMap.addAttribute("listaAmministratori", listaAmministratori);
	             return	 mnv = new ModelAndView( "/cameralini/amministratore/istanzaCompensiAmministratore", "form", form );
	            
	    	}
	    	
			
		}		
	
	 @RequestMapping( value = "/procedimento/nuovo/amministratore/ricerca.htm", method = RequestMethod.POST, params = {"Associa"} )
	    public ModelAndView associa( @Valid @ModelAttribute("form")IstanzaCompensiAmministratoreForm form, BindingResult result, ModelMap modelMap, HttpServletRequest request )
		{
	    	String sesso = null; 	
			String tipoSoggetto = null;
			String comuneEsteroNasc = null;
			String codiceFiscale = null;
			String codiceFiscalePG = null;
			String ragioneSociale = null;
			String indirizzoSede = null;
			String descNumeCcia = null;
			String descCivico = null;
			String descCap = null;
			Long codTipoStrada = null;
			Long idComuneNascita = null;
			Long idStatoNascita = null;
			Long idTipoSocieta = null;
			Long codSoggInt = null;
			Long idVer = null;
			String nome = null;
			String cognome = null;
			Date dataNascita = null;
			String comuneEsteroSede = null;
			String pIva = null;
			Long codComSede = null;
			Long codStatoSede = null;
	    	
	    	//si acquisiscono dati da sessione
	    	
	    	Integer idUffOwn = (Integer) request.getSession().getAttribute("idUffOwner");
	    	String codUtente = (String) request.getSession().getAttribute("codUtente");
	    	List<AssociaPersonaDto> listaAssoc = null;
	    	ModelAndView mnv = new ModelAndView( "/cameralini/amministratore/istanzaCompensiAmministratore", "form", form );
	    	Long idTipoProcedimento = Constants.TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM;
	    	form.setFunzioneCameralini("amministratore");
	    	
	    	ProcedimentoForm procForm =new ProcedimentoForm();
	    	Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");

	    	Long anno = form.getProcedPadre().getAnno().longValue();
			Long numero = form.getProcedPadre().getNumero();
			String amminSel=form.getAmministratoreSelected();
			List<AmministratoreForm> listaAmminSel = beneService.getAmministratoreById(new Long(amminSel).longValue());
			AmministratoreForm amministratore = listaAmminSel.get(0);
	    	ProcedimentoForm p = getProcedimentoPadre(idUfficioUtente,numero,anno,Constants.TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM);
    		
			//GP Test ricerca codice comune per amministratori
//			List<Long> listaId = null;
//			if (amministratore.getLuogoNascita() != null){
//				String descComune = getComuneFromComune(amministratore.getLuogoNascita());
//				listaId = procedimentoServiceCam.getIdComuneNascita(descComune);
//			}
//			if (listaId != null && listaId.size() ==1) {
//				idComuneNascita = listaId.get(0).longValue();
//				idStatoNascita = 1L;
//			}

    		
	    	String ammOmonimoSel=form.getAmministratoreOmonimoSel();
	    	if (ammOmonimoSel != null ) {
	    		String[] campi = StringUtils.split( ammOmonimoSel, "'^'" );
	    		BigDecimal idSel =  new BigDecimal(campi[0]) ;
	    		Integer idUffIntest = Integer.valueOf( campi[1] );

	    		//List<AmministratoreForm> listaAmministratoriSel = beneService.getAmministratoreById(new Long(ammOmonimoSel).longValue());
	    			    			
	    		procForm = preparaNuovoProcedimento(form,idTipoProcedimento);
	    		
	    		//Registrazione
	    		
	    		listaAssoc  = procedimentoServiceCam.inserisciProcedimentoCameraliniInt( p.getIdProcedimento(), idUffOwn.intValue(),  idTipoProcedimento.intValue(), 
	    				idSel,tipoSoggetto,nome ,cognome,sesso,dataNascita,comuneEsteroNasc,codiceFiscale,codiceFiscalePG,
	    				ragioneSociale,comuneEsteroSede,indirizzoSede,pIva, idUffIntest, descNumeCcia,descCivico,descCap,codTipoStrada, codComSede, idComuneNascita, codStatoSede,idStatoNascita,
	    				idTipoSocieta,codSoggInt,idVer, codUtente);    		
	    		
	    		
	    	} else {
	    		 super.setMessage( Constants.MESSAGE, "table.item.selection", null, false );
	             result.reject( Constants.MESSAGE );
	             List<AmministratoreForm> listaAmministratori = procedimentoServiceCam.getAmmMisurePatrPersProcedimento(idUfficioUtente, form.getProcedPadre().getIdProcedimento());
	             List<IntestatarioDto> listaOmonimi = procedimentoServiceCam.getAmministratoriOmonimi(amministratore.getCognome(),amministratore.getNome(), amministratore.getRagioneSociale());
	             modelMap.addAttribute("listaOmonimi", listaOmonimi);
	             modelMap.addAttribute("listaAmministratori", listaAmministratori);
	             return	 mnv = new ModelAndView( "/cameralini/amministratore/istanzaCompensiAmministratore", "form", form );
	    	}
			request.getSession().setAttribute("idProcPadre",  form.getProcedPadre().getIdProcedimento());
			request.getSession().setAttribute("idProcFiglio", listaAssoc.get(0).getIDPROC().longValue());
		
			procForm.setIdProcedimento(listaAssoc.get(0).getIDPROC().longValue());
			request.getSession().setAttribute(Constants.ID_PROCEDIMENTO, procForm);
			
			//richiamo la copertina 
			return apriController.copertinaProcedimento(procForm, result, request);
	    	
		}
	 
	 
		@RequestMapping( value = "/procedimento/nuovo/amministratore/ricerca.htm", method = RequestMethod.POST, params = { "Annulla" } )
		public ModelAndView annulla (IstanzaCompensiAmministratoreForm form, BindingResult result, ModelMap modelMap, HttpServletRequest request )
		{
			ModelAndView mnv = new ModelAndView("/cameralini/amministratore/istanzaCompensiAmministratore", "form", form);
			form.setFunzioneCameralini("amministratore");
			
			Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
			//verifica esistenza del procedimento con chiave: numero + anno + codiceUfficio
			ProcedimentoForm p = (ProcedimentoForm) request.getSession().getAttribute(Constants.ID_PROCEDIMENTO);
			
			List<AmministratoreForm> listaAmministratori = procedimentoServiceCam.getAmmMisurePatrPersProcedimento(idUfficioUtente, p.getIdProcedimento());
			modelMap.addAttribute("listaAmministratori", listaAmministratori);
			form.setProcedPadre(p);
			MenuController.caricaMenu( Constants.ID_K_MENU_PROCEDIMENTO, request );
			cleanBreadcrumb(request.getSession());
			addBreadcrumbs();
			return mnv;
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


}