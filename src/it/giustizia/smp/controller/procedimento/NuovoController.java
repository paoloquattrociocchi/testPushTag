package it.giustizia.smp.controller.procedimento;

import it.giustizia.smp.controller.BreadcrumbsController;
import it.giustizia.smp.controller.MenuController;
import it.giustizia.smp.dao.StaticListLoader;
import it.giustizia.smp.dto.pg_procedimento.AssociaPersonaDto;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.model.TProcedimento;
import it.giustizia.smp.service.CommonService;
import it.giustizia.smp.service.MisuraService;
import it.giustizia.smp.service.ProcedimentoService;
import it.giustizia.smp.service.webservices.AgenziaEntrateService;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.validation.ProcedimentoValidation;
import it.giustizia.smp.validation.SoggettoValidation;
import it.giustizia.smp.webservice.client.agenziaentrate.EsitoControlloCFePIWrapper;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * NuovoController
 * 
 * @author Almaviva S.p.A.
 *         Classe Controller per la gestione del flusso
 *         inerente alla creazione di un nuovo procedimento
 */
@Controller
public class NuovoController extends BreadcrumbsController {
 
	@Autowired
	private CommonService			commonService;
	@Autowired
	private MisuraService			misuraService;
	@Autowired
	private ProcedimentoService		procedimentoService;
	@Autowired
	private ProcedimentoValidation	procedimentoValidation;
	@Autowired(required = true)
	private HttpServletRequest		request;
	@Autowired
	private SoggettoValidation		soggettoValidation;
	@Autowired
	private ApriController			apriController;
	
	@Autowired
	private AgenziaEntrateService	agenziaEntrateService;
	
	
	

	@RequestMapping(value = "/procedimento/nuovo/ricercaSoggettoFisico.htm", method = RequestMethod.POST, params = { "NuovoSoggetto" })
	public String aggiungiNuovoSoggettoFisico(SoggettoForm soggettoForm, BindingResult result, ModelMap modelMap) {

		//Giacomo 11/07/2013	BreadCrumbs
		addBreadcrumbs("NuovoSoggetto",request);
		//String idTipoProc = (String) request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO);
		int idTipoFasc = super.getIdTipoFascicolo(false);
		resettaChiaveSoggetto(soggettoForm);
		soggettoForm.setTipoSoggetto(Constants.SOGGETTO_FISICO);
		//setDefaultValues(soggettoForm);
		modelMap.addAttribute("soggettoForm", soggettoForm);
		if(Constants.TIPO_FASCICOLO_OPP_DIVIETI_QUESTORE==(idTipoFasc))
			return "/procedimento/nuovo/riesame/nuovoSoggettoFisicoRiesame";
		else
			return "/procedimento/nuovo/nuovoSoggettoFisico";
	}

	@RequestMapping(value = "/procedimento/nuovo/ricercaSoggettoGiuridico.htm", method = RequestMethod.POST, params = { "NuovoSoggetto" })
	public String aggiungiNuovoSoggettoGiuridico(SoggettoForm soggettoForm, BindingResult result, ModelMap modelMap) {

		//Giacomo 11/07/2013	BreadCrumbs
		addBreadcrumbs("NuovoSoggetto",request);
		
		resettaChiaveSoggetto(soggettoForm);
		soggettoForm.setTipoSoggetto(Constants.SOGGETTO_GIURIDICO);
		//setDefaultValues(soggettoForm);
		modelMap.addAttribute("soggettoForm", soggettoForm);
		return "/procedimento/nuovo/nuovoSoggettoGiuridico";
	}

	// invocata in sede di aggiunta di un nuovo soggetto fisico diretto al
	// procedimento
	@RequestMapping(value = "/procedimento/nuovo/gestioneAssociaSoggetto.htm", method = RequestMethod.POST, params = { "AggiungiSoggettoFisico" })
	public String aggiungiSoggettoFisico(SoggettoForm soggettoForm, BindingResult result, ModelMap modelMap) {
		int idTipoFascicolo = super.getIdTipoFascicolo(false);
		
		if(idTipoFascicolo == Constants.TIPO_FASCICOLO_MERITO){
			return visualizzaRicercaSoggettoFisicoDiretto(soggettoForm, result, modelMap);
		}else if (idTipoFascicolo == Constants.TIPO_FASCICOLO_ACCERTAMENTO){
			return inizializzaRicercaSoggettoFisico(soggettoForm, result, modelMap, Constants.RUOLO_DIRETTO, Constants.ID_RUOLO_DIRETTO);
		}
		
		return visualizzaRicercaSoggettoFisicoDiretto(soggettoForm, result, modelMap);
	}

	// invocata in sede di aggiunta di un nuovo soggetto giuridico diretto al
	// procedimento
	@RequestMapping(value = "/procedimento/nuovo/gestioneAssociaSoggetto.htm", method = RequestMethod.POST, params = { "AggiungiSoggettoGiuridico" })
	public String aggiungiSoggettoGiuridico(SoggettoForm soggettoForm, BindingResult result, ModelMap modelMap) {
		int idTipoFascicolo = super.getIdTipoFascicolo(false);
		
		if(idTipoFascicolo == Constants.TIPO_FASCICOLO_MERITO){
			return visualizzaRicercaSoggettoGiuridicoDiretto(soggettoForm, result, modelMap);
		}else if (idTipoFascicolo == Constants.TIPO_FASCICOLO_ACCERTAMENTO){
			return inizializzaRicercaSoggettoGiuridico(soggettoForm, result, modelMap, Constants.RUOLO_DIRETTO, Constants.ID_RUOLO_DIRETTO);
		}
		
		return visualizzaRicercaSoggettoGiuridicoDiretto(soggettoForm, result, modelMap);
	}

	@RequestMapping(value = "/procedimento/nuovo/aggravamento/ricerca.htm", method = RequestMethod.GET)
	public String aggravamentoNuovo(ModelMap model) {

		// metto in sessione il tipo procedimento 1 (vale anche se sto cercando
		// prevenzioni che hanno tipo_proc =2 o =4
		//request.getSession().setAttribute(Constants.ID_TIPO_PROCEDIMENTO, Constants.TIPOPROC_PERSONALE_E_PATRIMONIALE);
		super.saveIntoSession(Constants.TIPO_FASCICOLO_AGGRAVAMENTO, Constants.TIPO_FASCICOLO, true);
		ProcedimentoForm procedimento = new ProcedimentoForm();
		model.addAttribute("procedimento", procedimento);
		addBreadcrumbs("GET","/procedimento/nuovo/aggravamento/ricerca.htm",null);
		return "/procedimento/nuovo/aggravamentoRicerca";
	}

	@RequestMapping(value = "/procedimento/nuovo/aggravamento/dettaglioPadre.htm", method = RequestMethod.POST, params = { "ISCRIVI" })
	public String aggravamentoNuovoDettaglioPadreIscrivi(ProcedimentoForm form, BindingResult result, HttpServletRequest request, ModelMap model) {

		procedimentoValidation.validateRegistrazioneAggravamento(form, request, result);
		if (result.hasErrors()) {
			return aggravamentoNuovoDettaglioPadre(form, result, request, model, true);
		}
		Long idSoggetto = form.getIdSelezionato();
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		String codiceUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);
		ProcedimentoForm padre = (ProcedimentoForm) request.getSession().getAttribute(Constants.ID_PROCEDIMENTO);
		Long idPadre = padre.getIdProcedimento();
		String tipoUtente = (String) request.getSession().getAttribute(Constants.TIPO_UTENTE);
		int idTipoFascicolo = super.getIdTipoFascicolo(false);
		Long idProcedimentoAggravamento = procedimentoService.iscriviProcedimentoDiAggravamento(idUfficioUtente, codiceUtente, form, idSoggetto, idPadre, tipoUtente,idTipoFascicolo);
		ProcedimentoForm aggravamento = procedimentoService.getDettaglioProcedimento(idProcedimentoAggravamento);
		aggravamento.setIdPadre(idPadre);
		aggiornaProcedimentoInSessione(aggravamento, request);
		aggravamento.setIdSelezionato(idSoggetto);
		MenuController.caricaMenu(Constants.ID_PER_MENU_DETTAGLIO_AGGRAVAMENTO, request);
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation", "inserimento", false);
		
		super.saveIntoSession(Constants.TIPO_FASCICOLO_AGGRAVAMENTO, Constants.TIPO_FASCICOLO, true);
		
		//mac richiesta da Stefania 31/10/2014
		return "redirect:/procedimento/apri/copertinaProcedimento.htm";
		//return aggravamentoDettaglio(aggravamento, model);
	}

	@RequestMapping(value = "/procedimento/nuovo/aggravamento/dettaglioPadre.htm", method = RequestMethod.GET)
	public String aggravamentoNuovoDettaglioPadreOrdinamento(ProcedimentoForm procedimento, BindingResult result, HttpServletRequest request, ModelMap model) {

		ProcedimentoForm form = (ProcedimentoForm) model.get("procedimento");
		model.addAttribute("procedimento", form);
		return aggravamentoNuovoDettaglioPadre(form, result, request, model, false);
	}

	@RequestMapping(value = "/procedimento/nuovo/aggravamento/ricerca.htm", method = RequestMethod.POST, params = { "RICERCA" })
	public String aggravamentoNuovoRicerca(@Valid @ModelAttribute("procedimento") ProcedimentoForm form, BindingResult result, HttpServletRequest request, ModelMap model) {

		procedimentoValidation.validateCriteriRicercaPuntuale(form, result);
		if (result.hasErrors()) {
			model.addAttribute("procedimento", form);
			MenuController.caricaMenu(Constants.ID_PADRE_MENU, request);
			return "/procedimento/nuovo/aggravamentoRicerca";
		}
		int idUfficioUtente = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		//int idTipoFascicolo = getIdTipoFascicolo(false);
		Long idProcedimento = null;
		List<Long> procedimentoList = procedimentoService.getIdProcByAnnoNumProc(idUfficioUtente, form.getAnno(), form.getNumero(), Constants.TIPO_FASCICOLO_MERITO);
		if(procedimentoList!=null && procedimentoList.size()>0) {
			idProcedimento = procedimentoList.get(0);
		}
		form.setIdProcedimento(idProcedimento);
		procedimentoValidation.validateRicercaProcedimento(form, result, request);
		
		addBreadcrumbs("POST","/procedimento/nuovo/aggravamento/ricerca.htm",null);
		
		if (result.hasErrors()) {
			model.addAttribute("procedimento", form);
			MenuController.caricaMenu(Constants.ID_PADRE_MENU, request);
			return "/procedimento/nuovo/aggravamentoRicerca";
		}
		return aggravamentoNuovoDettaglioPadre(form, result, request, model, false);
	}

	@RequestMapping(value = "/procedimento/nuovo/nuovoSoggettoGiuridico.htm", method = RequestMethod.POST, params = { "verificaPI" })
	public String controllaPISoggettoFisico(@Valid SoggettoForm soggettoForm, BindingResult result, ModelMap modelMap) {
		String msgErrore = "";
		
		if ( "".equals(soggettoForm.getRagioneSociale()) || soggettoForm.getRagioneSociale()==null  ) {
			msgErrore = "errors.campi.controllo.pi.ragionesociale";
		} else {
			//Invoco il WS
			EsitoControlloCFePIWrapper  esito = agenziaEntrateService.controllaDatiAnagraficiPI(soggettoForm);
			if (esito.isInvocazioneFallita()) {
				super.setMessage(Constants.MESSAGE, "errors.campi.controllo.pi", esito.getErrore(), false);
			} else {
				if (soggettoForm.getPartitaIva()!=null && !soggettoForm.getPartitaIva().equals("")) {
					// Compara CF
					if (soggettoForm.getPartitaIva().equals(esito.getPartitaIva())) {
						super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.pi.corretta", " ", true);
					} else {
						super.setMessage(Constants.MESSAGE, "errors.campi.controllo.pi.diverso", esito.getPartitaIva(), false);
					}
				} else {
					soggettoForm.setPartitaIva(esito.getPartitaIva());	
				}
			}
		}
		if (!msgErrore.equals("")) {
			super.setMessage(Constants.MESSAGE, msgErrore, null, false);
		}
		return "/procedimento/nuovo/nuovoSoggettoGiuridico";
		
	}
	
	
	/***
	 * Implementazione del controllo del codice fiscale per persona fisica
	 * @param soggettoForm
	 * @param result
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/procedimento/nuovo/nuovoSoggettoFisico.htm", method = RequestMethod.POST, params = { "verificaCF" })
	public String controllaCFSoggettoFisico(@Valid SoggettoForm soggettoForm, BindingResult result, ModelMap modelMap) {
		String msgErrore = "";
		
		if ( "".equals(soggettoForm.getNome()) || soggettoForm.getNome()==null || "".equals(soggettoForm.getCognome()) || soggettoForm.getCognome()==null ) {
			msgErrore = "errors.campi.controllo.codicefiscale.nomeocognome";
		} else {
			//Invoco il WS
			EsitoControlloCFePIWrapper  esito = agenziaEntrateService.controllaDatiAnagraficiPF(soggettoForm);
			if (esito.isInvocazioneFallita()) {
				super.setMessage(Constants.MESSAGE, "errors.campi.controllo.codicefiscale", esito.getErrore(), false);
			} else {
				if (soggettoForm.getCodiceFiscale()!=null && !soggettoForm.getCodiceFiscale().equals("")) {
					// Compara CF
					if (soggettoForm.getCodiceFiscale().equals(esito.getCodiceFiscale())) {
						super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.codicefiscale.corretto", " ", true);
					} else {
						super.setMessage(Constants.MESSAGE, "errors.campi.controllo.codicefiscale.diverso", esito.getCodiceFiscale(), false);
					}
				} else {
					soggettoForm.setCodiceFiscale(esito.getCodiceFiscale());	
				}
			}
		}
		
		if (!msgErrore.equals("")) {
			super.setMessage(Constants.MESSAGE, msgErrore, null, false);
		}
		return "/procedimento/nuovo/nuovoSoggettoFisico";
	}
	
	

	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/procedimento/nuovo/nuovoSoggettoFisico.htm", method = RequestMethod.POST, params = { "InserisciEAssocia" })
	public String associaNuovoSoggettoFisico(@Valid SoggettoForm soggettoForm, BindingResult result, ModelMap modelMap) {

		soggettoValidation.validateNuovoSoggettoFisicoObbl(soggettoForm, result);
		
		soggettoValidation.validateNuovoSoggettoNonObbl(soggettoForm, result);
		
		if (result.hasErrors()) {
			super.setMessage(Constants.MESSAGE, "errors.campi.controllo", null, false);
			return "/procedimento/nuovo/nuovoSoggettoFisico";
		}
		
		//Giacomo 11/07/2013	BreadCrumbs
		addBreadcrumbs("InserisciEAssocia",request);

		if(soggettoForm.getStatoNascita() != null){
			String idStatoNasc = soggettoForm.getStatoNascita().getId();
			String label = Utility.findLabelByValue(StaticListLoader.getStati(), idStatoNasc);
			soggettoForm.getStatoNascita().setDescrizione(label);
		}
		
		soggettoForm.setTipoSoggetto(Constants.SOGGETTO_FISICO);
		ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute("procedimento");
		if ((procedimento == null) || (procedimento.getIdProcedimento() == null)) {
			List<SoggettoForm> soggettiTemp = (List<SoggettoForm>) request.getSession().getAttribute("soggettiTemp");
			// aggiungo il soggetto inserito alla lista temporanea
			if (soggettiTemp == null) {
				soggettiTemp = new ArrayList<SoggettoForm>();
			}
			soggettiTemp.add(soggettoForm);
			request.getSession().setAttribute("soggettiTemp", soggettiTemp);
			return getMvRiepilogoProcedimento(modelMap, result);
		}
		int idUffOwner = (Integer) request.getSession().getAttribute("idUffOwner");
		String codUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);
		Long idProc = procedimento.getIdProcedimento();
		AssociaPersonaDto soggAssociato = procedimentoService.associaSoggettoProcedimento(idUffOwner, codUtente, idProc, soggettoForm);
		//simone
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", " ", true);
		//simone
		if (soggettoForm.getDescrRuolo().equals(Constants.RUOLO_DIRETTO)) {
			return "redirect:/procedimento/apri/copertinaProcedimento.htm";
		} else
			if (soggettoForm.getDescrRuolo().equals(Constants.RUOLO_TERZO)) {
				return "redirect:/procedimento/bene/visualizzaTitoliDiInteresse.htm";
			} else if (soggettoForm.getDescrRuolo().equals(Constants.RUOLO_INTERVENTIENTE)) {
					return "redirect:/procedimento/soggetto/visualizzaIntervenienti.htm";	
			} else {
				throw new RuntimeException("Ruolo non definito");
			}
	}

	@RequestMapping(value = "/procedimento/nuovo/nuovoSoggettoFisico.htm", method = RequestMethod.POST, params = { "IndietroRiesame" })
	public String associaNuovoSoggettoFisicoIndietro(SoggettoForm soggetto, ModelMap modelMap) {

		//SoggettoForm soggettoForm = (SoggettoForm) super.getIndietroForm();
		SoggettoForm soggettoForm = (SoggettoForm) request.getSession().getAttribute(Constants.Key.DATI_PER_INDIETRO);
		if(soggettoForm==null){
			return "/procedimento/nuovo/ricercaSoggettoFisico";
			}
		modelMap.remove("soggettoForm");
		modelMap.put("soggettoForm", soggettoForm);
		request.getSession().removeAttribute(Constants.Key.DATI_PER_INDIETRO);
		return modelAndViewRisultatiRicercaSoggettoFisico(modelMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/procedimento/nuovo/nuovoSoggettoGiuridico.htm", method = RequestMethod.POST, params = { "InserisciEAssocia" })
	public String associaNuovoSoggettoGiuridico(@Valid SoggettoForm soggettoForm, BindingResult result, ModelMap modelMap) {

		if(soggettoForm.getIdSoggetto()==null) soggettoValidation.validateNuovoSoggettoGiuridico(soggettoForm, result);
		if (result.hasErrors()) {
			soggettoForm.setFrom("associa");
			super.setMessage(Constants.MESSAGE, "errors.campi.controllo", null, false);
			return "/procedimento/nuovo/nuovoSoggettoGiuridico";
		}
		
		//Giacomo 11/07/2013	BreadCrumbs
		addBreadcrumbs("POST","/procedimento/nuovo/ricercaSoggettoGiuridico.htm","NuovoSoggetto");
		addBreadcrumbs("InserisciEAssocia",request);
		
		soggettoForm.setTipoSoggetto(Constants.SOGGETTO_GIURIDICO);
		ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute("procedimento");
		if ((procedimento == null) || (procedimento.getIdProcedimento() == null)) {
			List<SoggettoForm> soggettiTemp = (List<SoggettoForm>) request.getSession().getAttribute("soggettiTemp");
			// aggiungo il soggetto inserito alla lista temporanea
			if (soggettiTemp == null) {
				soggettiTemp = new ArrayList<SoggettoForm>();
			}
			soggettiTemp.add(soggettoForm);
			request.getSession().setAttribute("soggettiTemp", soggettiTemp);
			return getMvRiepilogoProcedimento(modelMap, result);
		}
		int idUffOwner = (Integer) request.getSession().getAttribute("idUffOwner");
		String codUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);
		Long idProc = procedimento.getIdProcedimento();
		AssociaPersonaDto soggAssociato = procedimentoService.associaSoggettoProcedimento(idUffOwner, codUtente, idProc, soggettoForm);
		if (soggettoForm.getDescrRuolo().equals(Constants.RUOLO_DIRETTO)) {
			return "redirect:/procedimento/apri/copertinaProcedimento.htm";
		} else
			if (soggettoForm.getDescrRuolo().equals(Constants.RUOLO_TERZO)) {
				return "redirect:/procedimento/bene/visualizzaTitoliDiInteresse.htm";
			} else if (soggettoForm.getDescrRuolo().equals(Constants.RUOLO_INTERVENTIENTE)) {
				return "redirect:/procedimento/soggetto/visualizzaIntervenienti.htm";	
			} else {
				throw new RuntimeException("Ruolo non definito");
			}
	}

	@RequestMapping(value = "/procedimento/nuovo/nuovoSoggettoGiuridico.htm", method = RequestMethod.POST, params = { "Salva" })
	public ModelAndView modificaSoggettoGiuridico(@Valid SoggettoForm soggettoForm, BindingResult result, ModelMap modelMap) {
		
		if(!"modifica".equalsIgnoreCase(soggettoForm.getFrom()))
			soggettoValidation.validateNuovoSoggettoGiuridico(soggettoForm, result);
		else
			soggettoValidation.validateModificaSoggettoGiuridico(soggettoForm, result);

		if (result.hasErrors()) {
			soggettoForm.setFrom("modifica");
			super.setMessage(Constants.MESSAGE, "errors.campi.controllo", null, false);
			ModelAndView mnv = new ModelAndView("/procedimento/nuovo/nuovoSoggettoGiuridico", "soggettoForm", soggettoForm);
			return mnv;
		}
		
		ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute("procedimento");
		int idUffOwner = (Integer) request.getSession().getAttribute("idUffOwner");
		String codUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);
		Long idProc = procedimento.getIdProcedimento();
		soggettoForm.setIdUfficio(idUffOwner);
		soggettoForm.setFrom("modifica");
		procedimentoService.aggiornaPersonaGiuridica(soggettoForm, idProc, codUtente);
		ModelAndView mnv = new ModelAndView("/procedimento/nuovo/nuovoSoggettoGiuridico", "soggettoForm", soggettoForm);
		super.setMessage(Constants.SUCCESS_MESSAGE, "success", null, false);
		return mnv;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/procedimento/nuovo/ricercaSoggettoFisico.htm", method = RequestMethod.POST, params = { "Associa" })
	public String associaSoggettoFisico(@Valid SoggettoForm soggettoForm, BindingResult result, ModelMap modelMap) {
		
		//String idTipoProc = (String) request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO);
		int idTipoFasc = super.getIdTipoFascicolo(false);
				
		soggettoValidation.validateGestioneSoggetto(soggettoForm, result, request);
		if (result.hasErrors()) {
			return modelAndViewRisultatiRicercaSoggettoFisico(modelMap);
		}
		
		addBreadcrumbs("Associa",request);
		
		List<SoggettoForm> soggettiTemp = (List<SoggettoForm>) request.getSession().getAttribute("soggettiTemp");
		if (soggettiTemp != null) {
			for (int i = 0; i < soggettiTemp.size(); i++) {
				SoggettoForm soggettoTemp = soggettiTemp.get(i);
				if ((soggettoTemp.getIdSoggetto() != null) && soggettoTemp.getIdSoggetto().equals(soggettoForm.getIdSoggetto())) {
					super.setMessage(Constants.MESSAGE, "soggettoFisico.giaAssociato", null, false);
					return modelAndViewRisultatiRicercaSoggettoFisico(modelMap);
				}
			}
		}
		ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute("procedimento");
		if ((procedimento != null) && (procedimento.getIdProcedimento() != null)) {
			Long idProc = procedimento.getIdProcedimento();
			List<SoggettoForm> soggettiAssociati = procedimentoService.soggettiDirettiInteressatiDelProcedimento(idProc);
			if (soggettiAssociati != null) {
				for (int i = 0; i < soggettiAssociati.size(); i++) {
					SoggettoForm soggettoAssociato = soggettiAssociati.get(i);
					if ((soggettoForm.getIdSoggetto() != null) && soggettoAssociato.getIdSoggetto().equals(soggettoForm.getIdSoggetto())) {
						super.setMessage(Constants.MESSAGE, "soggettoFisico.giaAssociato", null, false);
						return modelAndViewRisultatiRicercaSoggettoFisico(modelMap);
					}
				}
			}
		}
		soggettoForm.setIdUfficio((Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE));
		soggettoForm = procedimentoService.caricaValoriDettaglioSoggettoFisico(soggettoForm);
		soggettoForm.setTipoSoggetto(Constants.SOGGETTO_FISICO);
		//setDefaultValues(soggettoForm);
		modelMap.addAttribute("soggettoForm", soggettoForm);
		if(Constants.TIPO_FASCICOLO_OPP_DIVIETI_QUESTORE==(idTipoFasc))
			return "/procedimento/nuovo/riesame/nuovoSoggettoFisicoRiesame";
		else
			return "/procedimento/nuovo/nuovoSoggettoFisico";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/procedimento/nuovo/ricercaSoggettoGiuridico.htm", method = RequestMethod.POST, params = { "Associa" })
	public String associaSoggettoGiuridico(@Valid SoggettoForm soggettoForm, BindingResult result, ModelMap modelMap) {
		soggettoForm.setFrom("associa");
		soggettoValidation.validateGestioneSoggetto(soggettoForm, result, request);
		
		if (result.hasErrors()) {
			return modelAndViewRisultatiRicercaSoggettoGiuridico(modelMap);
		}
		
		addBreadcrumbs("Associa",request);
		
		List<SoggettoForm> soggettiTemp = (List<SoggettoForm>) request.getSession().getAttribute("soggettiTemp");
		if (soggettiTemp != null) {
			for (int i = 0; i < soggettiTemp.size(); i++) {
				SoggettoForm soggettoTemp = soggettiTemp.get(i);
				if ((soggettoTemp.getIdSoggetto() != null) && soggettoTemp.getIdSoggetto().equals(soggettoForm.getIdSoggetto())) {
					super.setMessage(Constants.MESSAGE, "soggettoFisico.giaAssociato", null, false);
					return modelAndViewRisultatiRicercaSoggettoGiuridico(modelMap);
				}
			}
		}
		ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute("procedimento");
		if ((procedimento != null) && (procedimento.getIdProcedimento() != null)) {
			Long idProc = procedimento.getIdProcedimento();
			List<SoggettoForm> soggettiAssociati = procedimentoService.soggettiDirettiInteressatiDelProcedimento(idProc);
			if (soggettiAssociati != null) {
				for (int i = 0; i < soggettiAssociati.size(); i++) {
					SoggettoForm soggettoAssociato = soggettiAssociati.get(i);
					if ((soggettoForm.getIdSoggetto() != null) && soggettoAssociato.getIdSoggetto().equals(soggettoForm.getIdSoggetto())) {
						super.setMessage(Constants.MESSAGE, "soggettoFisico.giaAssociato", null, false);
						return modelAndViewRisultatiRicercaSoggettoGiuridico(modelMap);
					}
				}
			}
		}
		soggettoForm.setIdUfficio((Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE));
		soggettoForm = procedimentoService.caricaValoriDettaglioSoggettoGiuridico(soggettoForm);
		soggettoForm.setTipoSoggetto(Constants.SOGGETTO_GIURIDICO);
		//setDefaultValues(soggettoForm);
		modelMap.addAttribute("soggettoForm", soggettoForm);
		return "/procedimento/nuovo/nuovoSoggettoGiuridico";
	}

	@RequestMapping(value = "/procedimento/nuovo/dettaglioProcedimento.htm", method = RequestMethod.POST, params = { "DettaglioProcedimento" })
	public ModelAndView dettaglioProcedimentiCollegatiSoggetto(@Valid SoggettoForm soggettoForm, BindingResult result, ModelMap modelMap) {

		soggettoValidation.validateDettaglioProcedimentiCollegati(soggettoForm, result, request);
		if (result.hasErrors()) {
			soggettoForm.setIdUfficio((Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE));
			if (soggettoForm.getTipoSoggetto().equals(Constants.SOGGETTO_FISICO)) {
				soggettoForm = procedimentoService.caricaValoriDettaglioSoggettoFisico(soggettoForm);
				soggettoForm.setTipoSoggetto(Constants.SOGGETTO_FISICO);
			} else
				if (soggettoForm.getTipoSoggetto().equals(Constants.SOGGETTO_GIURIDICO)) {
					soggettoForm = procedimentoService.caricaValoriDettaglioSoggettoGiuridico(soggettoForm);
					soggettoForm.setTipoSoggetto(Constants.SOGGETTO_GIURIDICO);
				}
			modelMap.addAttribute("soggettoForm", soggettoForm);
			int idUffOwner = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
			modelMap.addAttribute("procedimenti", caricaValoriProcedimentiCollegati(idUffOwner, soggettoForm.getIdSoggetto()));
			return new ModelAndView("/procedimento/nuovo/procedimentiCollegati");
		}
		
		addBreadcrumbs("DettaglioProcedimento",request);
		
		// setto in sessione l'oggetto procedimento
		/*
		Long idProcedimento = new Long(soggettoForm.getIdProcedimento());
		ProcedimentoForm procedimentoForm = new ProcedimentoForm();
		procedimentoForm.setIdProcedimento(idProcedimento);
		request.getSession().setAttribute(Constants.ID_PROCEDIMENTO, procedimentoForm);
		return "redirect:/procedimento/apri/copertinaProcedimento.htm";
		*/
		Long idProcedimento = new Long(soggettoForm.getIdProcedimento());
		ProcedimentoForm procedimento = new ProcedimentoForm();
		procedimento.setIdProcedimento(idProcedimento);
		
		//è il tipo fascicolo relativo al procedimento collegato selezionato 
		int tipoFascicolo = Integer.valueOf(soggettoForm.getTipoFascicolo().getId()).intValue();
		request.setAttribute(Constants.TIPO_FASCICOLO_COLLEGATO, tipoFascicolo);
		
		return apriController.caricaDettaglioProcedimento(procedimento, result, request, null);
	}

	@RequestMapping(value = "/procedimento/nuovo/ricercaSoggettoFisico.htm", method = RequestMethod.POST, params = { "Dettaglio" })
	public String dettaglioSoggettoFisico(@Valid SoggettoForm soggettoForm, BindingResult result, ModelMap modelMap) {

		//String idTipoProc = (String) request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO);
		int idTipoFasc = super.getIdTipoFascicolo(false);
		
		soggettoValidation.validateGestioneSoggetto(soggettoForm, result, request);
		if (result.hasErrors()) {
			return modelAndViewRisultatiRicercaSoggettoFisico(modelMap);
		}
		soggettoForm.setIdUfficio((Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE));
		soggettoForm = procedimentoService.caricaValoriDettaglioSoggettoFisico(soggettoForm);
		modelMap.addAttribute("soggettoForm", soggettoForm);
		// Imposto nel form IndietroForm la tipologia di Soggetto
//		SoggettoForm soggettoFormIndietro = (SoggettoForm) super.getIndietroForm();
//		soggettoFormIndietro.setTipoSoggetto(soggettoForm.getTipoSoggetto());
//		super.setIndietroForm(soggettoFormIndietro);
		
		addBreadcrumbs("Dettaglio",request);
		
		
		//modelMap.addAttribute("indietro", "/procedimento/nuovo/ricercaSoggettoFisico.htm ");
		modelMap.addAttribute("indietro", "/procedimento/nuovo/indietroNuovoSoggettoFisicoRiesame.htm");
		
		
		if(Constants.TIPO_FASCICOLO_OPP_DIVIETI_QUESTORE==(idTipoFasc))
			return "/procedimento/nuovo/riesame/dettaglioSoggettoFisicoProcedimentoRiesame";
		else
			return "procedimento/nuovo/dettaglioSoggettoFisico";
	}

	@RequestMapping(value = "/procedimento/nuovo/ricercaSoggettoGiuridico.htm", method = RequestMethod.POST, params = { "Dettaglio" })
	public String dettaglioSoggettoGiuridico(@Valid SoggettoForm soggettoForm, BindingResult result, ModelMap modelMap) {

		soggettoValidation.validateGestioneSoggetto(soggettoForm, result, request);
		if (result.hasErrors()) {
			return modelAndViewRisultatiRicercaSoggettoGiuridico(modelMap);
		}
		
		addBreadcrumbs("Dettaglio",request);
		
		procedimentoService.setIdSoggettoIdVersioneTipoSoggFromChiave(soggettoForm);
		soggettoForm.setIdUfficio((Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE));
		soggettoForm = procedimentoService.caricaValoriDettaglioSoggettoGiuridico(soggettoForm);
		modelMap.addAttribute("soggettoForm", soggettoForm);
		// Imposto nel form IndietroForm la tipologia di Soggetto
//        SoggettoForm soggettoFormIndietro = (SoggettoForm) super.getIndietroForm();
//        soggettoFormIndietro.setTipoSoggetto(soggettoForm.getTipoSoggetto());
//        super.setIndietroForm(soggettoFormIndietro);
		return "procedimento/nuovo/dettaglioSoggettoGiuridico";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/procedimento/nuovo/gestioneAssociaSoggetto.htm", method = RequestMethod.GET)
	public String gestioneListaSoggettiInteressati(ProcedimentoForm procedimentoForm, BindingResult result, ModelMap modelMap) {

		// id della lista da eliminare
		String elimID = request.getParameter("elimID");
		if ((elimID != null) && !"".equals(elimID)) {
			int id = Integer.valueOf(elimID) - 1;
			List<SoggettoForm> soggettiTemp = (List<SoggettoForm>) request.getSession().getAttribute("soggettiTemp");
			SoggettoForm sogg = soggettiTemp.get(id);
			soggettiTemp.remove(id);
			request.getSession().setAttribute("soggettiTemp", soggettiTemp);
		}
		return getMvRiepilogoProcedimento(modelMap, result);
	}

	@ModelAttribute("normative")
	public List<ComboForm> getNormative(@Valid SoggettoForm soggettoForm, BindingResult result) {

		//setDefaultValues(soggettoForm);
		if ((soggettoForm.getIpotesiMisura() != null) && (soggettoForm.getTipologiaPrevenzione() != null)) {
			if (!soggettoForm.getIpotesiMisura().getId().equals(Constants.NO_ITEM_SELECTED) && !soggettoForm.getTipologiaPrevenzione().getId().equals(Constants.NO_ITEM_SELECTED)) {
				return commonService.getNormativaByTipoProcedAndTipologiaPrevenzione(soggettoForm);
			}
			return new ArrayList<ComboForm>();
		}
		return new ArrayList<ComboForm>();
	}

	@ModelAttribute("tipiProcedimentoCondizionati")
	public List<ComboForm> getTipiProcedimento(@Valid SoggettoForm soggettoForm, BindingResult result) {

//		return commonService.listaTipiDiProcedimento(super.getIdTipoFascicolo(false));
		return commonService.listaTipoIpotesi(super.getIdTipoFascicolo(false), soggettoForm.getTipoSoggetto());
	}

//	@RequestMapping(value = { "/procedimento/nuovo/nuovoSoggettoFisico.htm", "/procedimento/nuovo/nuovoSoggettoGiuridico.htm" }, method = RequestMethod.POST, params = { "Indietro" })
//	public String indietro(SoggettoForm soggettoForm, BindingResult result, ModelMap modelMap) {
//
//		SoggettoForm soggettoFormIndietro = (SoggettoForm) super.getIndietroForm();
//		if (soggettoFormIndietro.getTipoSoggetto() != null) {
//			if (soggettoFormIndietro.getTipoSoggetto().equals(Constants.SOGGETTO_FISICO)) {
//				return modelAndViewRisultatiRicercaSoggettoFisico(modelMap.addAttribute("soggettoForm", soggettoFormIndietro));
//			}
//			if (soggettoFormIndietro.getTipoSoggetto().equals(Constants.SOGGETTO_GIURIDICO)) {
//				return modelAndViewRisultatiRicercaSoggettoGiuridico(modelMap.addAttribute("soggettoForm", soggettoFormIndietro));
//			}
//		}
//		return null;
//	}
	
//	@RequestMapping(value = { "/procedimento/nuovo/indietroNuovoSoggettoFisicoRiesame.htm"}, method = RequestMethod.GET)
//	public String indietroRiesame(SoggettoForm soggettoForm, BindingResult result, ModelMap modelMap) {
//		return indietro(soggettoForm, result, modelMap);
//	}

	@RequestMapping(value = "/procedimento/nuovo/nuovoRiesameProvvedimentiQuestore.htm", method = RequestMethod.GET)
	public String nuovoRiesameProvvedimentiQuestore(@Valid SoggettoForm soggettoForm, BindingResult result, ModelMap modelMap) {

//		if (request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO) != null) {
//			request.getSession().removeAttribute(Constants.ID_TIPO_PROCEDIMENTO);
//		}
		super.getIdTipoFascicolo(true);
		//request.getSession().setAttribute(Constants.ID_TIPO_PROCEDIMENTO, Constants.TIPOPROC_RIESAME_PROVV_QUESTURA);
		super.saveIntoSession(Constants.TIPO_FASCICOLO_OPP_DIVIETI_QUESTORE, Constants.TIPO_FASCICOLO, true);
		soggettoForm.setDescrRuolo(Constants.RUOLO_DIRETTO);
		soggettoForm.setIdRuolo(Constants.ID_RUOLO_DIRETTO);
		return "/procedimento/nuovo/ricercaSoggettoFisico";
	}

	// ****************** FINE SOGGETTO GIURIDICO ****************
	// Soggetto Giuridico
	// ****************** Inizio parti comuni SOGGETTO ****************
	// Inizio parti comuni SOGGETTO
	// metodo in get che serve per l' ordinamento
	@RequestMapping(value = "/procedimento/nuovo/dettaglioProcedimento.htm", method = RequestMethod.GET)
	public String ordinamentoProcedimentiCollegatiSoggetto(@Valid SoggettoForm soggettoForm, BindingResult result, ModelMap modelMap) {

		String ritorno = null;
		if (Constants.SOGGETTO_GIURIDICO.equals(request.getParameter("tipoSoggetto"))) {
			ritorno = procedimentiCollegatiSoggettoGiuridico(soggettoForm, result, modelMap);
		} else {
			if (Constants.SOGGETTO_FISICO.equals(request.getParameter("tipoSoggetto"))) {
				ritorno = procedimentiCollegatiSoggettoFisico(soggettoForm, result, modelMap);
			}
		}
		return ritorno;
	}

	// metodo per caricare la pagina successiva della ricerca
	@RequestMapping(value = "/procedimento/nuovo/ricercaSoggettoFisico.htm", method = RequestMethod.GET)
	public String ordinamentoSoggettoFisico(@Valid SoggettoForm soggettoForm, BindingResult result, ModelMap modelMap) {
		
		//soggettoForm = (SoggettoForm) request.getSession().getAttribute(Constants.Key.DATI_PER_INDIETRO);
		//modelMap.remove("soggettoForm");
		modelMap.put("soggettoForm", soggettoForm);
		return modelAndViewRisultatiRicercaSoggettoFisico(modelMap);
	}

	// metodo per l'ordinamento della ricerca
	@RequestMapping(value = "/procedimento/nuovo/ricercaSoggettoGiuridico.htm", method = RequestMethod.GET)
	public String ordinamentoSoggettoGiuridico(@Valid SoggettoForm soggettoForm, BindingResult result, ModelMap modelMap) {
		
		//soggettoForm = (SoggettoForm) request.getSession().getAttribute(Constants.Key.DATI_PER_INDIETRO);
		//modelMap.remove("soggettoForm");
		modelMap.put("soggettoForm", soggettoForm);
		return modelAndViewRisultatiRicercaSoggettoGiuridico(modelMap);
	}

	@RequestMapping(value = "/procedimento/nuovo/ricercaSoggettoFisico.htm", method = RequestMethod.POST, params = { "ProcedimentiCollegati" })
	public String procedimentiCollegatiSoggettoFisico(@Valid SoggettoForm soggettoForm, BindingResult result, ModelMap modelMap) {

		ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute("procedimento");
		
		if(procedimento != null && procedimento.getIdProcedimento() != null)
		  MenuController.caricaMenu(Constants.ID_PER_MENU_DETTAGLIO_PREVENZIONE, request);
		else
		  MenuController.caricaMenu(Constants.ID_PADRE_MENU, request);

		soggettoValidation.validateGestioneSoggetto(soggettoForm, result, request);
		if (result.hasErrors()) {
			return modelAndViewRisultatiRicercaSoggettoFisico(modelMap);
		}
		
		addBreadcrumbs("ProcedimentiCollegati",request);
		
		
		soggettoForm.setIdUfficio((Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE));
		soggettoForm = procedimentoService.caricaValoriDettaglioSoggettoFisico(soggettoForm);
		soggettoForm.setTipoSoggetto(Constants.SOGGETTO_FISICO);
		modelMap.addAttribute("soggettoForm", soggettoForm);
		int idUffOwner = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		modelMap.addAttribute("procedimenti", caricaValoriProcedimentiCollegati(idUffOwner, soggettoForm.getIdSoggetto()));
		return "/procedimento/nuovo/procedimentiCollegati";
	}

	@RequestMapping(value = "/procedimento/nuovo/ricercaSoggettoGiuridico.htm", method = RequestMethod.POST, params = { "ProcedimentiCollegati" })
	public String procedimentiCollegatiSoggettoGiuridico(@Valid SoggettoForm soggettoForm, BindingResult result, ModelMap modelMap) {

		ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute("procedimento");
		
		if(procedimento != null && procedimento.getIdProcedimento() != null)
		  MenuController.caricaMenu(Constants.ID_PER_MENU_DETTAGLIO_PREVENZIONE, request);
		else
		  MenuController.caricaMenu(Constants.ID_PADRE_MENU, request);
		
		soggettoValidation.validateGestioneSoggetto(soggettoForm, result, request);
		if (result.hasErrors()) {
			return modelAndViewRisultatiRicercaSoggettoGiuridico(modelMap);
		}
		
		addBreadcrumbs("ProcedimentiCollegati",request);
		
		soggettoForm.setIdUfficio((Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE));
		soggettoForm = procedimentoService.caricaValoriDettaglioSoggettoGiuridico(soggettoForm);
		soggettoForm.setTipoSoggetto(Constants.SOGGETTO_GIURIDICO);
		modelMap.addAttribute("soggettoForm", soggettoForm);
		int idUffOwner = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		modelMap.addAttribute("procedimenti", caricaValoriProcedimentiCollegati(idUffOwner, soggettoForm.getIdSoggetto()));
		return "/procedimento/nuovo/procedimentiCollegati";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/procedimento/nuovo/gestioneAssociaSoggetto.htm", "/procedimento/nuovo/gestioneAssociaSoggettoRiesame.htm" }, method = RequestMethod.POST, params = { "RegistraProcedimento" })
	public String registraProcedimento(ProcedimentoForm procedimentoForm, BindingResult result, ModelMap modelMap) {

		List<SoggettoForm> soggettiTemp = (List<SoggettoForm>) request.getSession().getAttribute("soggettiTemp");

		int idTipoFasc = super.getIdTipoFascicolo(false);
		if(idTipoFasc != Constants.TIPO_FASCICOLO_OPP_DIVIETI_QUESTORE){
	      	procedimentoValidation.validaAssociaSoggetto(procedimentoForm, result, request);
				if (result.hasErrors()) {
					super.setMessage(Constants.MESSAGE, "errors.campi.controllo", null, false);
					return getMvRiepilogoProcedimento(modelMap,result);
				}
		}
		if ((soggettiTemp == null) || (soggettiTemp.size() == 0)) {
			super.setMessage(Constants.MESSAGE, "procedimento.nuovo.noSoggetti", null, false);
			return getMvRiepilogoProcedimento(modelMap,result);
		}
		int idUffOwner = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		String codUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);
		String tipoUtente = (String) request.getSession().getAttribute(Constants.TIPO_UTENTE);
		long statoProc = Constants.STATOPROC_IN_LAVORAZIONE;
		if (tipoUtente.equals(Constants.TIPO_UTENTE_SMP_GIU)) {
			if ((procedimentoForm.getTipoProcedimento() != null) && Constants.TIPOPROC_RIESAME_PROVV_QUESTURA.equals(procedimentoForm.getTipoProcedimento().getId())) {
				statoProc = Constants.STATOPROC_IN_LAVORAZIONE;
			} else {
				statoProc = Long.valueOf(Constants.STATOPROC_IN_ACQUISIZ_PROPOSTA);
			}
		}
		int idTipoFascicolo = getIdTipoFascicolo(false);
		List<AssociaPersonaDto> ins = procedimentoService.inserisciProcedimento(idUffOwner, codUtente, procedimentoForm, statoProc, soggettiTemp, idTipoFascicolo);
		// rimuovo la lista temporanea
		request.getSession().removeAttribute("soggettiTemp");
		// setto in sessione l'oggetto procedimento
		Long idProcedimento = new Long(ins.get(0).getIDPROC().longValue());
		procedimentoForm.setIdProcedimento(idProcedimento);
		// se il procedimento è un riesame provvedimento questore inserisce la
		// misura personale del soggetto interessato(che si trova nella lista)
		if ((procedimentoForm.getTipoProcedimento() != null) && Constants.TIPOPROC_RIESAME_PROVV_QUESTURA.equals(procedimentoForm.getTipoProcedimento().getId())) {
			inserisciMisuraPersonaleSoggettoDiffidato(procedimentoForm, ins, idUffOwner, codUtente);
		}
		request.getSession().setAttribute(Constants.ID_PROCEDIMENTO, procedimentoForm);
		
		//Giacomo: 16/07/2013
		cleanBreadcrumb(request.getSession());
		
		
		//INIZIO GESTIONE DOCUMENTALE
		TProcedimento procedimento = procedimentoService.getProcedimentoById(idProcedimento);
		long numeProc = procedimento.getNumeProc();
		
		int tipoProcedimento;
		if (procedimento.getTTpTipoProc() == null)
			tipoProcedimento = procedimento.getTTpTipoFascicolo().getCodiKIdTipoFasc();
		else 
			tipoProcedimento = procedimento.getTTpTipoProc().getCodiKIdTipoProc();
		
		String descUfficioGiudiziario = procedimento.getTAnUfficioByCodiFkIdUffOwner().getDescUffGiudiz();
		
		String codiceFascicolo= Utility.creaCodiceFascicolo(Constants.PREFISSO_FASCICOLO, tipoProcedimento, String.valueOf(numeProc), descUfficioGiudiziario);
		procedimentoService.inserisciECreaFascicoloPDoc(idProcedimento, codiceFascicolo);
		
		//FINE GESTIONE DOCUMENTALE
		
		return "redirect:/procedimento/apri/copertinaProcedimento.htm";
	}


	@RequestMapping(value = { "/procedimento/nuovo/ricercaSoggettoFisico.htm" }, method = RequestMethod.POST, params = { "Ricerca" })
	public String ricercaSoggettoFisico(@Valid SoggettoForm soggettoForm, BindingResult result, ModelMap modelMap) {
		
		soggettoValidation.validateRicercaSoggettoFisico(soggettoForm, result, request);
		if (result.hasErrors()) {
			return "/procedimento/nuovo/ricercaSoggettoFisico";
		}
		
		request.getSession().setAttribute(Constants.Key.DATI_PER_INDIETRO, soggettoForm);
		addBreadcrumbs("Ricerca",request);
		
		//request.getSession().removeAttribute(Constants.Key.DATI_PER_INDIETRO);
		return modelAndViewRisultatiRicercaSoggettoFisico(modelMap);
	}

	// invocato quando viene premuto il tasto 'Ricerca'
	@RequestMapping(value = "/procedimento/nuovo/ricercaSoggettoGiuridico.htm", method = RequestMethod.POST, params = { "Ricerca" })
	public String ricercaSoggettoGiuridico(@Valid SoggettoForm soggettoForm, BindingResult result, ModelMap modelMap) {

		soggettoValidation.validateRicercaSoggettoGiuridico(soggettoForm, result, request);
		if (result.hasErrors()) {
			return "/procedimento/nuovo/ricercaSoggettoGiuridico";
		}
		
		addBreadcrumbs("Ricerca",request);
		
		return modelAndViewRisultatiRicercaSoggettoGiuridico(modelMap);
	}

	@RequestMapping(value = "/procedimento/nuovo/nuovoSoggettoFisico.htm", method = RequestMethod.POST)
	public String selezioneCombo(@Valid SoggettoForm soggettoForm, BindingResult result, ModelMap modelMap) {

		return "/procedimento/nuovo/nuovoSoggettoFisico";
	}

	@RequestMapping(value = "/procedimento/nuovo/nuovoSoggettoGiuridico.htm", method = RequestMethod.POST)
	public String selezioneComboPG(@Valid SoggettoForm soggettoForm, BindingResult result, ModelMap modelMap) {

		return "/procedimento/nuovo/nuovoSoggettoGiuridico";
	}

	// metodo per la selezione delle comboForm
	@RequestMapping(value = "/procedimento/nuovo/ricercaSoggettoFisico.htm", method = RequestMethod.POST)
	public String selezioneComboSoggettoFisico(@Valid SoggettoForm soggettoForm, BindingResult result, ModelMap modelMap) {

		return "/procedimento/nuovo/ricercaSoggettoFisico";
	}

	@RequestMapping(value = "/procedimento/nuovo/ricercaSoggettoGiuridico.htm", method = RequestMethod.POST)
	public String selezioneComboSoggettoGiuridico(@Valid SoggettoForm soggettoForm, BindingResult result, ModelMap modelMap) {

		return "/procedimento/nuovo/ricercaSoggettoGiuridico";
	}

	public void setProcedimentoValidation(ProcedimentoValidation procedimentoValidation) {

		this.procedimentoValidation = procedimentoValidation;
	}

	
	@RequestMapping(value = {"/procedimento/nuovo/accertamento/visualizzaRicercaSoggettoFisico.htm","/procedimento/nuovo/accertamento/visualizzaRicercaSoggettoGiuridico.htm"}, method = RequestMethod.GET)
	public String nuovoAccertamento(@Valid SoggettoForm soggettoForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {
		
//		if (request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO) != null) {
//			request.getSession().removeAttribute(Constants.ID_TIPO_PROCEDIMENTO);
//		}
		
		//Giacomo 11/07/2013
//		cleanBreadcrumb();
		
		super.getIdTipoFascicolo(true);
		//request.getSession().setAttribute(Constants.ID_TIPO_PROCEDIMENTO, Constants.TIPOPROC_ACCERTAMENTO);
		super.saveIntoSession(Constants.TIPO_FASCICOLO_ACCERTAMENTO, Constants.TIPO_FASCICOLO, true);
		if(request.getRequestURI().equals(request.getContextPath() + "/procedimento/nuovo/accertamento/visualizzaRicercaSoggettoFisico.htm"))
			return inizializzaRicercaSoggettoFisico(soggettoForm, result, modelMap, Constants.RUOLO_DIRETTO, Constants.ID_RUOLO_DIRETTO);
		else 
			return inizializzaRicercaSoggettoGiuridico(soggettoForm, result, modelMap, Constants.RUOLO_DIRETTO, Constants.ID_RUOLO_DIRETTO);
	}
	
	// ***************** SOGGETTO FISICO ****************//
	@RequestMapping(value = "/procedimento/nuovo/visualizzaRicercaSoggettoFisico.htm", method = RequestMethod.GET)
	public String visualizzaRicercaSoggettoFisicoDiretto(@Valid SoggettoForm soggettoForm, BindingResult result, ModelMap modelMap) {

		//request.getSession().removeAttribute(Constants.ID_TIPO_PROCEDIMENTO);
		super.getIdTipoFascicolo(true);
		super.saveIntoSession(Constants.TIPO_FASCICOLO_MERITO, Constants.TIPO_FASCICOLO, true);
//		cleanBreadcrumb();
		return inizializzaRicercaSoggettoFisico(soggettoForm, result, modelMap, Constants.RUOLO_DIRETTO, Constants.ID_RUOLO_DIRETTO);
	}

	@RequestMapping(value = "/procedimento/nuovo/visualizzaRicercaSoggettoFisicoTerzo.htm", method = RequestMethod.GET)
	public String visualizzaRicercaSoggettoFisicoTerzo(@Valid SoggettoForm soggettoForm, BindingResult result, ModelMap modelMap) {
		
		//Giacomo 11/07/2013
		addBreadcrumbs();

		return inizializzaRicercaSoggettoFisico(soggettoForm, result, modelMap, Constants.RUOLO_TERZO, Constants.ID_RUOLO_TERZO);
	}

	@RequestMapping(value = "/procedimento/nuovo/visualizzaRicercaIntervenienteFisico.htm", method = RequestMethod.GET)
	public String visualizzaRicercaIntervenienteFisico(@Valid SoggettoForm soggettoForm, BindingResult result, ModelMap modelMap) {

		super.getIdTipoFascicolo(true);
		super.saveIntoSession(Constants.TIPO_FASCICOLO_MERITO, Constants.TIPO_FASCICOLO, true);
		return inizializzaRicercaSoggettoFisico(soggettoForm, result, modelMap, Constants.RUOLO_INTERVENTIENTE, Constants.ID_RUOLO_INTERVENIENTE);
	}
	
	// ***************** FINE SOGGETTO FISICO ****************//
	// Soggetto Fisico
	// ****************** SOGGETTO GIURIDICO ****************//
	// Giuridico
	@RequestMapping(value = "/procedimento/nuovo/visualizzaRicercaSoggettoGiuridico.htm", method = RequestMethod.GET)
	public String visualizzaRicercaSoggettoGiuridicoDiretto(@Valid SoggettoForm soggettoForm, BindingResult result, ModelMap modelMap) {
//		Giacomo: viene anche richiamato dalla ricerca puntuale 
//		cleanBreadcrumb();
//		super.removeFromSession(Constants.ID_PROCEDIMENTO);
		
		super.saveIntoSession(Constants.TIPO_FASCICOLO_MERITO, Constants.TIPO_FASCICOLO, true);
		return inizializzaRicercaSoggettoGiuridico(soggettoForm, result, modelMap, Constants.RUOLO_DIRETTO, Constants.ID_RUOLO_DIRETTO);
	}

	@RequestMapping(value = "/procedimento/nuovo/visualizzaRicercaSoggettoGiuridicoTerzo.htm", method = RequestMethod.GET)
	public String visualizzaRicercaSoggettoGiuridicoTerzo(@Valid SoggettoForm soggettoForm, BindingResult result, ModelMap modelMap) {
		 //Giacomo 11/07/2013	BreadCrumbs
		 addBreadcrumbs();
		return inizializzaRicercaSoggettoGiuridico(soggettoForm, result, modelMap, Constants.RUOLO_TERZO, Constants.ID_RUOLO_TERZO);
	}

	@RequestMapping(value = "/procedimento/nuovo/visualizzaRicercaIntervenienteGiuridico.htm", method = RequestMethod.GET)
	public String visualizzaRicercaIntervenienteGiuridico(@Valid SoggettoForm soggettoForm, BindingResult result, ModelMap modelMap) {

		super.saveIntoSession(Constants.TIPO_FASCICOLO_MERITO, Constants.TIPO_FASCICOLO, true);
		return inizializzaRicercaSoggettoGiuridico(soggettoForm, result, modelMap, Constants.RUOLO_INTERVENTIENTE, Constants.ID_RUOLO_INTERVENIENTE);
	}
	
	private void aggiornaProcedimentoInSessione(ProcedimentoForm procedimento, HttpServletRequest request) {

		request.getSession().setAttribute(Constants.ID_PROCEDIMENTO, procedimento);
		request.getSession().removeAttribute(Constants.ATTO);
		MenuController.caricaMenu(Constants.ID_PER_MENU_DETTAGLIO_PREVENZIONE, request);
	}

	private String aggravamentoDettaglio(ProcedimentoForm form, ModelMap model) {

		Integer idUfficioUtente = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		Long idSoggetto = form.getIdSelezionato();
		SoggettoForm soggetto = procedimentoService.getSogIntById(idSoggetto, idUfficioUtente.longValue(), form.getIdProcedimento(), null);
		List<SoggettoForm> direttiInteressati = new ArrayList<SoggettoForm>();
		direttiInteressati.add(soggetto);
		model.clear();
		model.addAttribute("procedimento", form);
		model.addAttribute("direttiInteressati", direttiInteressati);
		return "procedimento/nuovo/aggravamentoDettaglio";
	}

	private String aggravamentoNuovoDettaglioPadre(ProcedimentoForm form, BindingResult result, HttpServletRequest request, ModelMap model, boolean validationDisabled) {

		int idUfficioUtente = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		ProcedimentoForm procedimento = procedimentoService.getDettaglioProcedimento(form.getIdProcedimento());
		aggiornaProcedimentoInSessione(procedimento, request);
		procedimento.setIdSelezionato(form.getIdSelezionato());
		List<SoggettoForm> soggettiDelProcedimento = procedimentoService.soggettiInteressatiDelProcedimentoPerRegistrazioneAggravamento(procedimento.getIdProcedimento(), idUfficioUtente);
		if (!validationDisabled) {
			procedimentoValidation.validateSoggettiInteressatiPerEligibilitaPerAggravamento(soggettiDelProcedimento, result);
			if (result.hasErrors()) {
				return aggravamentoNuovoRicerca(form, result, request, model);
			}
		}
		
		model.remove("procedenti");
		List<ComboForm> procedenti = getProcedenti(model);
		model.addAttribute("procedimento", procedimento);
		model.addAttribute("direttiInteressati", soggettiDelProcedimento);
		model.addAttribute("procedenti", procedenti);
		MenuController.caricaMenu(Constants.ID_PADRE_MENU, request);
		
		return "procedimento/nuovo/aggravamentoDettaglioPadre";
	}

	private List<ProcedimentoForm> caricaValoriProcedimentiCollegati(int idUffOwn, long idSoggetto) {

		return procedimentoService.listaProcedimentiSoggetto(idUffOwn, idSoggetto);
	}

//	private ProcedimentoForm creaProcedimentoMock() {
//
//		ProcedimentoForm procedimento = new ProcedimentoForm();
//		procedimento.setAnno((short) 2011);
//		procedimento.setNumero(105L);
//		procedimento.setProponente("Credenziali proponente");
//		procedimento.setProposta("Testo della proposta");
//		procedimento.setComAccertamento("Roma");
//		procedimento.setTipoProcedimento(new ComboForm(null, "Fascicolo di prevenzione"));
//		procedimento.setAntiterrorismo(true);
//		procedimento.setStatoProcedimento(new ComboForm(null, "In Istruttoria"));
//		try {
//			procedimento.setDataIscrizione(new SimpleDateFormat("dd/MM/yyyy").parse("19/09/2011"));
//			procedimento.setDataEmissioneProposta(new SimpleDateFormat("dd/MM/yyyy").parse("01/09/2011"));
//			procedimento.setDataRicezioneProposta(new SimpleDateFormat("dd/MM/yyyy").parse("05/09/2011"));
//			procedimento.setDataEmissioneAccertamento(new SimpleDateFormat("dd/MM/yyyy").parse("02/09/2011"));
//			procedimento.setDataRicezioneAccertamento(new SimpleDateFormat("dd/MM/yyyy").parse("06/09/2011"));
//			procedimento.setDataAttivazioneStato(new SimpleDateFormat("dd/MM/yyyy").parse("09/09/2011"));
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		return procedimento;
//	}

	@SuppressWarnings("unchecked")
	private String getMvRiepilogoProcedimento(ModelMap modelMap, BindingResult result) {

		String mv = "/procedimento/nuovo/eseguiAssociazioneSoggettoFisico";
		int idTipoFascicolo = super.getIdTipoFascicolo(false);
		ProcedimentoForm procedimentoForm = (ProcedimentoForm) modelMap.get("procedimentoForm");
		List<SoggettoForm> lista = (List<SoggettoForm>) request.getSession().getAttribute("soggettiTemp");
		procedimentoForm = Utility.getProcedimentoByListaSoggetti(procedimentoForm, lista, idTipoFascicolo);
		modelMap.addAttribute("procedimentoForm", procedimentoForm);
		modelMap.addAttribute("procedenti", getProcedenti(modelMap));
		if (Constants.TIPO_FASCICOLO_OPP_DIVIETI_QUESTORE==(idTipoFascicolo)) {
			ComboForm tipoProc = new ComboForm();
			tipoProc.setId(Constants.TIPOPROC_RIESAME_PROVV_QUESTURA);
			procedimentoForm.setTipoProcedimento(tipoProc);
			request.getSession().removeAttribute(Constants.ID_TIPO_PROCEDIMENTO);
			mv = registraProcedimento(procedimentoForm, result, modelMap);
		} else if (Constants.TIPO_FASCICOLO_ACCERTAMENTO==(idTipoFascicolo)){
			procedimentoForm = Utility.getProcedimentoByListaSoggetti(procedimentoForm, lista, idTipoFascicolo);
			modelMap.addAttribute("procedimentoForm", procedimentoForm);
		}
		return mv;
	}

	@ModelAttribute("procedenti")
	private List<ComboForm> getProcedenti(ModelMap modelMap) {
		
		int idUfficio = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);	
		ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute(Constants.ID_PROCEDIMENTO);
		Long idTipoPrev = null;
		if(procedimento==null) {
			if(modelMap.get("procedimentoForm")!=null) {
				procedimento = (ProcedimentoForm)modelMap.get("procedimentoForm");
			} else return null;
		}
		if(procedimento.getCalcoloTipologiaPrevenzione()!=null) {
			idTipoPrev = Long.valueOf(procedimento.getCalcoloTipologiaPrevenzione());
		}
		Long idTipoFasc = Long.valueOf(super.getIdTipoFascicolo(false));
		return procedimentoService.getListaProcedenti(idUfficio, idTipoPrev, idTipoFasc);
	}

	private String inizializzaRicercaSoggettoFisico(@Valid SoggettoForm soggettoForm, BindingResult result, ModelMap modelMap, String ruolo, int idRuolo) {

		soggettoForm = new SoggettoForm();
		soggettoForm.setDescrRuolo(ruolo);
		soggettoForm.setIdRuolo(idRuolo);
		modelMap.addAttribute("soggettoForm", soggettoForm);
		
		return "/procedimento/nuovo/ricercaSoggettoFisico";
	}

	private String inizializzaRicercaSoggettoGiuridico(@Valid SoggettoForm soggettoForm, BindingResult result, ModelMap modelMap, String ruolo, int idRuolo) {

		soggettoForm = new SoggettoForm();
		soggettoForm.setDescrRuolo(ruolo);
		soggettoForm.setIdRuolo(idRuolo);
		modelMap.addAttribute("soggettoForm", soggettoForm);
		return "/procedimento/nuovo/ricercaSoggettoGiuridico";
	}

	private void inserisciMisuraPersonaleSoggettoDiffidato(ProcedimentoForm procedimentoForm, List<AssociaPersonaDto> listaAssociaPers, int idUffOwn, String codUtente) {

		long idSoggettoInteressato = 0;
		if (listaAssociaPers.size() > 0) {
			AssociaPersonaDto dto = listaAssociaPers.get(0);
			idSoggettoInteressato = dto.getIDSOGGINT().longValue();
		}
		misuraService.inserisciMisuraSoggettoDiffidato(procedimentoForm.getIdProcedimento(), idUffOwn, idSoggettoInteressato, codUtente);
	}

	// ****************** Fine parti comuni SOGGETTO ****************//
	// Fine parti comuni SOGGETTO
	// ***************** METODI PRIVATI **********************//
	// Privati
	private String modelAndViewRisultatiRicercaSoggettoFisico(ModelMap modelMap) {

//		SoggettoForm soggettoForm = new SoggettoForm();
//		if(request.getSession().getAttribute(Constants.Key.DATI_PER_INDIETRO)==null){
//			soggettoForm = (SoggettoForm) modelMap.get("soggettoForm");
//			super.setIndietroForm(soggettoForm);
//		}
//		else{
//			soggettoForm = (SoggettoForm) request.getSession().getAttribute(Constants.Key.DATI_PER_INDIETRO);
//		}
		
		SoggettoForm soggettoForm = (SoggettoForm) modelMap.get("soggettoForm");
		
		int idUffOwner = (Integer) request.getSession().getAttribute("idUffOwner");
		List<SoggettoForm> listaSoggettoForm = procedimentoService.listaSoggettiByCriteria(idUffOwner, soggettoForm.getNome(), soggettoForm.getCognome(), soggettoForm.getStatoNascita().getId().equals(String.valueOf(Constants.NO_ITEM_SELECTED)) ? null : soggettoForm.getStatoNascita().getId(), soggettoForm.getComuneNascita() == null ? null : soggettoForm.getComuneNascita().getId(), soggettoForm.getDescrizioneComuneEsteroNascita(), Utility.convertDateToString(soggettoForm.getDataNascita()), soggettoForm.getCodiceFiscale(), soggettoForm.getCui(), true, null);
		modelMap.addAttribute("risultatiRicerca", listaSoggettoForm);
		return "/procedimento/nuovo/ricercaSoggettoFisico";
	}

	private String modelAndViewRisultatiRicercaSoggettoGiuridico(ModelMap modelMap) {

//		SoggettoForm soggettoForm = new SoggettoForm();
//		if(request.getSession().getAttribute(Constants.Key.DATI_PER_INDIETRO)==null){
//			soggettoForm = (SoggettoForm) modelMap.get("soggettoForm");
//			super.setIndietroForm(soggettoForm);
//		}
//		else{
//			soggettoForm = (SoggettoForm) request.getSession().getAttribute(Constants.Key.DATI_PER_INDIETRO);
//		}
		
		SoggettoForm soggettoForm = (SoggettoForm) modelMap.get("soggettoForm");
		
		int idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
		String piva =StringUtils.isNotEmpty(soggettoForm.getPartitaIvaSiglaStato())?soggettoForm.getPartitaIvaSiglaStato():"";
		if(StringUtils.isNotEmpty(soggettoForm.getPartitaIva())){
			piva.concat(soggettoForm.getPartitaIva().trim());
		}
		List<SoggettoForm> listaSoggettoForm = procedimentoService.listaSoggettiGiurByCriteria(idUfficioUtente, soggettoForm.getRagioneSociale() != null ? soggettoForm.getRagioneSociale() : null, piva, soggettoForm.getStatoSedeLegale().getId().equals(String.valueOf(Constants.NO_ITEM_SELECTED)) ? null : Integer.parseInt(soggettoForm.getStatoSedeLegale().getId()), soggettoForm.getComuneSedeLegale().getId() == null ? null : Integer.parseInt(soggettoForm.getComuneSedeLegale().getId()), soggettoForm.getDescrizioneComuneEsteroSedeLegale(), true, null);
		modelMap.addAttribute("risultatiRicercaSoggettoGiuridico", listaSoggettoForm);
		return "/procedimento/nuovo/ricercaSoggettoGiuridico";
	}

	private void resettaChiaveSoggetto(SoggettoForm soggettoForm) {

		soggettoForm.setIdSoggetto(null);
		soggettoForm.setIdVersione(null);
		soggettoForm.setTipoSoggetto(null);
	}

//	private void setDefaultValues(SoggettoForm soggettoForm) {
//
//		if (request.getSession().getAttribute(Constants.TIPO_UTENTE).equals(Constants.TIPO_UTENTE_SMP)) {
//			if ((soggettoForm.getIpotesiMisura() == null) && (soggettoForm.getTipologiaPrevenzione() == null)) {
//				ComboForm ipotesiMisura = new ComboForm();
//				ComboForm tipologiaPrevenzione = new ComboForm();
//				if ((soggettoForm.getTipoSoggetto() != null) && Constants.SOGGETTO_FISICO.equals(soggettoForm.getTipoSoggetto())) {
//					ipotesiMisura.setId(Constants.TIPOPROC_PERSONALE);
//					tipologiaPrevenzione.setId(Constants.TIPOPREV_ANTIMAFIA);
//					soggettoForm.setIpotesiMisura(ipotesiMisura);
//					soggettoForm.setTipologiaPrevenzione(tipologiaPrevenzione);
//				} else
//					if ((soggettoForm.getTipoSoggetto() != null) && Constants.SOGGETTO_GIURIDICO.equals(soggettoForm.getTipoSoggetto())) {
//						ipotesiMisura.setId(Constants.TIPOPROC_PATRIMONIALE);
//						tipologiaPrevenzione.setId(Constants.TIPOPREV_ANTIMAFIA);
//						soggettoForm.setIpotesiMisura(ipotesiMisura);
//						soggettoForm.setTipologiaPrevenzione(tipologiaPrevenzione);
//					}
//			}
//		}
//	}
}
