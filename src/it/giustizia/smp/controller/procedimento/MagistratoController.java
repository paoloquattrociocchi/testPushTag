package it.giustizia.smp.controller.procedimento;

import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import it.giustizia.smp.controller.BreadcrumbsController;
import it.giustizia.smp.dao.AttoDao;
import it.giustizia.smp.dto.KeyMessageDto;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.MagistratoForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.model.TTpCatAtto;
import it.giustizia.smp.service.AttoService;
import it.giustizia.smp.service.CommonService;
import it.giustizia.smp.service.ProcedimentoService;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.validation.MagistratoValidation;

/**
 * MagistratoController
 * 
 * @author Almaviva S.p.A.
 *         Classe Controller per la gestione dei magistrati
 *         inerenti al procedimento
 */
@Controller
public class MagistratoController extends BreadcrumbsController {

	@Autowired
	private AttoService				attoService;
	@Autowired
	private MagistratoValidation	magistratoValidation;
	@Autowired
	private ProcedimentoService		procedimentoService;
	@Autowired
	private AttoController			attoController;
	@Autowired
	private CommonService			commonService;
	@Autowired
	private AttoDao					attoDao;

	@Autowired
	private ReloadableResourceBundleMessageSource configParameterSource;	
	
	@RequestMapping(value = "/procedimento/collegio/elenco.htm", method = RequestMethod.GET)
	public ModelAndView collegioElenco(HttpServletRequest request) {

		//Link Collegio del Procedimento
		ProcedimentoForm proced = recuperaProcedimento();
		//Segnalazione EMS-67
		if(proced.getTipoFascicolo().getId().equals(Constants.TIPO_FASCICOLO_OPP_DIVIETI_QUESTORE_S))
			super.saveIntoSession(Constants.TIPO_FASCICOLO_OPP_DIVIETI_QUESTORE, Constants.TIPO_FASCICOLO, true);
		
		boolean abilitazione = magistratoValidation.validateAbilitazioneCollegioNuovo(proced, request);
		if (!abilitazione) {
			return new ModelAndView("redirect:/procedimento/apri/copertinaProcedimento.htm");
		}
		ModelAndView mnv = new ModelAndView("/procedimento/magistrato/collegioElenco", "procedimentoForm", new ProcedimentoForm());
		long idProcedimento = recuperaIdProcedimento();
		Integer idUfficioUtente = recuperaIdUfficioUtente();
		List<MagistratoForm> magistratiAssociati = procedimentoService.listaMagistratiPerProcedimento(idUfficioUtente, idProcedimento, 0, true);
		mnv.addObject("magistratiAssociati", magistratiAssociati);
		
		//Giacomo 25/07/2013
		addBreadcrumbs("GET","/procedimento/collegio/elenco.htm",null);
		return mnv;
	}
	
	@RequestMapping(value = "procedimento/atto/collegio/associati/gestione.htm", method = RequestMethod.POST, params = { "Elimina" })
	public String collegioElencoAssociatoAttoElimina(MagistratoForm magistratoForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {

		//"ELIMINA" Collegio dell'Atto
		magistratoValidation.validateSceltaRadioButtonIdString(magistratoForm, result, request);
		if (result.hasErrors()) {
			return elencoCollegioAssociatoAtto(magistratoForm, result, request, modelMap);
		}
		long IdAtto = recuperaAtto().getCodAtto();
		Integer idUffOwn = recuperaIdUfficioUtente();
		String separatore = ";";
		String idMagistrato = null;
		String descRuolo = null;
		StringTokenizer st = new StringTokenizer(magistratoForm.getIdMagString(), separatore);
		while (st.hasMoreTokens()) {
			idMagistrato = st.nextToken();
			descRuolo = st.nextToken();
		}
		//Modifica 01/2014
		MagistratoForm infoMagistrato = new MagistratoForm();
		infoMagistrato.setId(Long.parseLong(idMagistrato));
		infoMagistrato.setRuolo(new ComboForm(descRuolo,descRuolo));
		magistratoValidation.validateEliminazione(infoMagistrato, result, request);
		if (result.hasErrors()) {
			return elencoCollegioAssociatoAtto(magistratoForm, result, request, modelMap);
		}
		//Modifica 01/2014
		attoService.eliminaRelMagAtto(idUffOwn, IdAtto, Long.parseLong(idMagistrato), descRuolo);
		
		return elencoCollegioAssociatoAtto(magistratoForm, result, request, modelMap);
	}

	@RequestMapping(value = "procedimento/atto/collegio/associati/gestione.htm", method = RequestMethod.POST, params = { "Modifica" })
	public String collegioElencoAssociatoAttoModifica(MagistratoForm magistratoForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {

		//"MODIFICA" Collegio dell'Atto
		magistratoValidation.validateSceltaRadioButtonIdString(magistratoForm, result, request);
		if (result.hasErrors()) {
			return elencoCollegioAssociatoAtto(magistratoForm, result, request, modelMap);
		}
		long idAtto = recuperaAtto().getCodAtto();
		Integer idUffOwn = recuperaIdUfficioUtente();
		String separatore = ";";
		String idMagistrato = null;
		String descRuolo = null;
		StringTokenizer st = new StringTokenizer(magistratoForm.getIdMagString(), separatore);
		while (st.hasMoreTokens()) {
			idMagistrato = st.nextToken();
			descRuolo = st.nextToken();
		}
		magistratoForm = attoService.getDettaglioMagistratoAtto(idUffOwn, idAtto, Long.parseLong(idMagistrato), descRuolo);
		modelMap.addAttribute("magistratoForm", magistratoForm);
		return "/procedimento/magistrato/collegioAssociatoAttoModifica";
	}

	@RequestMapping(value = "/procedimento/atto/collegio/associati/modifica.htm", method = RequestMethod.POST, params = { "Conferma" })
	public String collegioElencoAssociatoAttoModificaConferma(MagistratoForm magistratoForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {

		//"CONFERMA" Collegio dell'Atto
		long idAtto = recuperaAtto().getCodAtto();
		Integer idUffOwn = recuperaIdUfficioUtente();
		magistratoValidation.validateMagistratoDelCollegioPerAtto(magistratoForm, result, request, idAtto, idUffOwn, false);
		if (result.hasErrors()) {
			return "/procedimento/magistrato/collegioAssociatoAttoModifica";
		}
		attoService.modificaMagistratoPerAtto(idUffOwn, idAtto, magistratoForm.getId(), magistratoForm.getRuoloAnteModifica(), magistratoForm.getRuolo().getId(), magistratoForm.getRelatore());
		return elencoCollegioAssociatoAtto(magistratoForm, result, request, modelMap);
	}

	@RequestMapping(value = "/procedimento/collegio/elenco.htm", method = RequestMethod.POST, params = { "Elimina" })
	public ModelAndView collegioElencoElimina(ProcedimentoForm procedimentoForm, BindingResult result, HttpServletRequest request) {

		//"ELIMINA" Collegio del Procedimento
		MagistratoForm magistratoForm = procedimentoForm.getMagistratoForm();
		magistratoValidation.validateSceltaRadioButton(magistratoForm, result, request);
		if (result.hasErrors()) {
			return collegioElenco(request);
		}
		long idProcedimento = recuperaProcedimento().getIdProcedimento();
		Integer idUfficioUtente = recuperaIdUfficioUtente();
		
		//Modifica 01/2014
		MagistratoForm infoMagistrato = procedimentoService.getDettaglioMagistratoProcedimento(magistratoForm.getId(), idProcedimento, idUfficioUtente);
		magistratoValidation.validateEliminazione(infoMagistrato, result, request);
		if (result.hasErrors()) {
			return collegioElenco(request);
		}
		//Modifica 01/2014
		procedimentoService.eliminaAssociazioneMagistratoProcedimento(magistratoForm, idProcedimento, idUfficioUtente);
		//simone
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", " ", true);
		//simone
		
		return collegioElenco(request);
	}

	@RequestMapping(value = "/procedimento/collegio/elenco.htm", method = RequestMethod.POST, params = { "Modifica" })
	public ModelAndView collegioElencoModifica(ProcedimentoForm procedimentoForm, BindingResult result, HttpServletRequest request) {

		//"MODIFICA" Collegio del Procedimento
		String idTipoProcString = "";
		String view = "/procedimento/magistrato/collegioModifica";
		ProcedimentoForm proced = recuperaProcedimento();
		if ((proced.getTipoProcedimento() != null) && (proced.getTipoProcedimento().getId() != null)) {
			idTipoProcString = proced.getTipoProcedimento().getId();
			if (idTipoProcString.equals(Constants.TIPOPROC_RIESAME_PROVV_QUESTURA)) {
				view = "/procedimento/magistrato/collegioModificaRiesame";
			}
		}
		magistratoValidation.validateSceltaRadioButton(procedimentoForm.getMagistratoForm(), result, request);
		if (result.hasErrors()) {
			if (idTipoProcString.equals(Constants.TIPOPROC_RIESAME_PROVV_QUESTURA)) {
				return collegioElenco(request);
			}
			return collegioElenco(request);
		}
		long idProcedimento = recuperaIdProcedimento();
		Integer idUfficioUtente = recuperaIdUfficioUtente();
		
		MagistratoForm magistratoForm = procedimentoService.getDettaglioMagistratoProcedimento(procedimentoForm.getMagistratoForm().getId(), idProcedimento, idUfficioUtente);
		
		//Modifica 01/2014
		magistratoValidation.validateDataFineAssegnazione(magistratoForm, result, request);
		if (result.hasErrors()) {
			return collegioElenco(request);
		}
		//Modifica 01/2014
		
		//Giacomo 25/07/2013
		addBreadcrumbs();
		
		return new ModelAndView(view, "magistratoForm", magistratoForm);
	}

	@RequestMapping(value = "/procedimento/collegio/modifica.htm", method = RequestMethod.POST)
	public ModelAndView sceltaMotivoSostituzioneTribunale(@Valid MagistratoForm magistratoForm, BindingResult result, HttpServletRequest request) {
	    
	    String thisView = "/procedimento/magistrato/collegioModifica";
        ProcedimentoForm proced = recuperaProcedimento();
        if ((proced.getTipoProcedimento() != null) && proced.getTipoProcedimento().getId().equals(Constants.TIPOPROC_RIESAME_PROVV_QUESTURA)) {
            thisView = "/procedimento/magistrato/collegioModificaRiesame";
        }
        return new ModelAndView(thisView, "magistratoForm", magistratoForm);
	}
	
	@RequestMapping(value = "/procedimento/collegio/modifica.htm", method = RequestMethod.POST, params = { "Conferma" })
	public ModelAndView collegioElencoModificaConferma(@Valid MagistratoForm magistratoForm, BindingResult result, HttpServletRequest request) {

		//"CONFERMA" Collegio del Procedimento
		String thisView = "/procedimento/magistrato/collegioModifica";
		ProcedimentoForm proced = recuperaProcedimento();
		long idProcedimento = proced.getIdProcedimento();
		Integer idUfficioUtente = recuperaIdUfficioUtente();
		if ((proced.getTipoProcedimento() != null) && proced.getTipoProcedimento().getId().equals(Constants.TIPOPROC_RIESAME_PROVV_QUESTURA)) {
			thisView = "/procedimento/magistrato/collegioModificaRiesame";
		}
		if (!result.hasErrors()) {
			magistratoValidation.validateAssociazioneMagistrato(magistratoForm, result, request);
		}
		if (result.hasErrors()) {
			return new ModelAndView(thisView, "magistratoForm", magistratoForm);
		}
		MagistratoForm presidenteAttivo = magistratoValidation.validateCorrettezzaInserimentoMagistratoDelCollegio(magistratoForm, result, request, idProcedimento, idUfficioUtente, false);
		if (result.hasErrors()) {
			return new ModelAndView(thisView, "magistratoForm", magistratoForm);
		}
		//Modifica 01/2014
		if(presidenteAttivo!=null){
			//magistrato contiene tutte le informazioni del presidente attivo, se presente
			//devo impostare la data fine assegnazione del presidente precedente con la data assegnazione del nuovo presidente
			presidenteAttivo.setDataFineAssegnazione(magistratoForm.getDataAssegnazioneFascicolo());
			procedimentoService.modificaDatiMagistratoSuProcedimento(presidenteAttivo, idProcedimento, idUfficioUtente);
		}
		//Modifica 01/2014
		procedimentoService.modificaDatiMagistratoSuProcedimento(magistratoForm, idProcedimento, idUfficioUtente);
		if ((proced.getStatoProcedimento() != null) && proced.getStatoProcedimento().getId().equals("2")) {
			List<MagistratoForm> magistratiAssociati = procedimentoService.listaMagistratiPerProcedimento(idUfficioUtente, idProcedimento, 0);
			if (isCollegioCorrettamenteFormato(magistratiAssociati, proced)) {
				String codUtente = recuperaIdCodiceUtente();
				String tipoUtente = (String) request.getSession().getAttribute(Constants.TIPO_UTENTE);
				procedimentoService.aggiornaStatoDelProcedimento(idProcedimento, idUfficioUtente, codUtente, tipoUtente, 0);
				
			}
		}
		//simone
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", " ", true);
		//simone
		super.updateProcedimentoIntoSession(idProcedimento);
		return collegioElenco(request);
	}

	@RequestMapping(value = "/procedimento/collegio/nuovo.htm", method = RequestMethod.GET)
	public ModelAndView collegioNuovo(@Valid MagistratoForm magistratoForm, BindingResult result, HttpServletRequest request) {

		//"NUOVO" Collegio del Procedimento
		ProcedimentoForm proced = recuperaProcedimento();
		boolean abilitazione = magistratoValidation.validateAbilitazioneCollegioNuovo(proced, request);
		if (!abilitazione) {
			return collegioElenco(request);
		}
		long idProcedimento = proced.getIdProcedimento();
		Integer idUfficioUtente = recuperaIdUfficioUtente();
		
		//Modifica 01/2014 - Commentato
		/*magistratoValidation.validateDisponibilitaPerMagistratoDelCollegio(magistratoForm, result, request, idProcedimento, idUfficioUtente);
		if (result.hasErrors()) {
			return collegioElenco(request);
		}*/
		//Modifica 01/2014
		
		ModelAndView mnv = new ModelAndView("/procedimento/magistrato/collegioAssocia", "procedimentoForm", new ProcedimentoForm());
		List<MagistratoForm> magistratiAssociati = procedimentoService.listaMagistratiPerProcedimento(idUfficioUtente, idProcedimento, 0, true);
		mnv.addObject("magistratiAssociati", magistratiAssociati);
		if ((proced.getTipoProcedimento() != null) && proced.getTipoProcedimento().getId().equals(Constants.TIPOPROC_RIESAME_PROVV_QUESTURA)) {
			magistratoValidation.validateNuovoRiesame(magistratoForm, result, request, magistratiAssociati);
			if (result.hasErrors()) {
				return collegioElenco(request);
			}
			mnv.setViewName("/procedimento/magistrato/collegioAssociaRiesame");
		}
		
		List<MagistratoForm> magistratiDelProcedimento = procedimentoService.ricercaMagistrati(magistratoForm, idUfficioUtente);
		List<MagistratoForm> risultati = procedimentoService.listaMagistratiNonGiaAssociati(magistratiDelProcedimento, magistratiAssociati);
		mnv.addObject("magistratiAssociati", magistratiAssociati);
		mnv.addObject("risultati", risultati);
		
		//Giacomo 25/07/2013
		addBreadcrumbs();
		return mnv;
	}

	@RequestMapping(value = "/procedimento/collegio/associa.htm", method = RequestMethod.POST, params = { "Ricerca" })
	public ModelAndView collegioNuovoRicerca(@Valid ProcedimentoForm procedimentoForm, BindingResult result, HttpServletRequest request) {

		//"RICERCA" Collegio del Procedimento
		MagistratoForm magistratoForm = procedimentoForm.getMagistratoForm();
		request.getSession().setAttribute("magistratoSA", magistratoForm);
		long idProcedimento = recuperaIdProcedimento();
		Integer idUfficioUtente = recuperaIdUfficioUtente();
		
		List<MagistratoForm> magistratiDelProcedimento = procedimentoService.ricercaMagistrati(magistratoForm, idUfficioUtente);
		ModelAndView mnv = new ModelAndView("/procedimento/magistrato/collegioAssocia", "procedimentoForm", procedimentoForm);
		List<MagistratoForm> magistratiAssociati = procedimentoService.listaMagistratiPerProcedimento(idUfficioUtente, idProcedimento, 0, true);
		// escludo i magistrati già associati al procedimento dalla lista dei
		// magistrati associabili
		List<MagistratoForm> risultati = procedimentoService.listaMagistratiNonGiaAssociati(magistratiDelProcedimento, magistratiAssociati);
		mnv.addObject("magistratiAssociati", magistratiAssociati);
		mnv.addObject("risultati", risultati);
		
		//Giacomo 25/07/2013
		addBreadcrumbs("POST","/procedimento/collegio/associa.htm","Ricerca");
		return mnv;
	}

	@RequestMapping(value = "/procedimento/collegio/associa.htm", method = RequestMethod.POST, params = { "Associa" })
	public ModelAndView collegioNuovoRicercaAssocia(@Valid ProcedimentoForm procedimentoForm, BindingResult result, HttpServletRequest request) {

		//"ASSOCIA" Collegio del Procedimento
		MagistratoForm magistratoForm = procedimentoForm.getMagistratoForm();
		ProcedimentoForm proced = recuperaProcedimento();
		long idProcedimento = proced.getIdProcedimento();
		Integer idUfficioUtente = recuperaIdUfficioUtente();
		magistratoValidation.validateSceltaRadioButton(magistratoForm, result, request);
		magistratoValidation.validateAssociazioneMagistratoProc(magistratoForm, result, request);
		MagistratoForm presidenteAttivo = null;
		if (!result.hasErrors()) {
			presidenteAttivo = magistratoValidation.validateCorrettezzaInserimentoMagistratoDelCollegio(magistratoForm, result, request, idProcedimento, idUfficioUtente, true);
		}
		if (result.hasErrors()) {
			if ((proced.getTipoProcedimento() != null) && Constants.TIPOPROC_RIESAME_PROVV_QUESTURA.equals(proced.getTipoProcedimento().getId())) {
				return collegioNuovoRicercaRiesame(procedimentoForm, result, request);
			}
			return collegioNuovoRicerca(procedimentoForm, result, request);
		}
		//Modifica 17/01/2014
		if(presidenteAttivo!=null){
			//magistrato contiene tutte le informazioni del presidente attivo, se presente
			//devo impostare la data fine assegnazione del presidente precedente con la data assegnazione del nuovo presidente
			presidenteAttivo.setDataFineAssegnazione(magistratoForm.getDataAssegnazioneFascicolo());
			procedimentoService.modificaDatiMagistratoSuProcedimento(presidenteAttivo, idProcedimento, idUfficioUtente);
		}
		//Modifica 17/01/2014
		procedimentoService.associaMagistratoAlProcedimento(magistratoForm, idProcedimento, idUfficioUtente);
		//simone
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", " ", true);
		//simone
		if ((proced.getStatoProcedimento() != null) && 
				(proced.getStatoProcedimento().getId().equals(Constants.STATOPROC_ISCRITTO) 
						|| proced.getStatoProcedimento().getId().equals(Constants.STATOPROC_ISCRITTO_PER_OPPOSIZIONE)
						|| proced.getStatoProcedimento().getId().equals(Constants.STATOPROC_ISCRITTO_PER_REVOCAZIONE)) ) {
			List<MagistratoForm> magistratiAssociati = procedimentoService.listaMagistratiPerProcedimento(idUfficioUtente, idProcedimento, 0);
			if (isCollegioCorrettamenteFormato(magistratiAssociati, proced)) {
				String codUtente = recuperaIdCodiceUtente();
				String tipoUtente = (String) request.getSession().getAttribute(Constants.TIPO_UTENTE);
				procedimentoService.aggiornaStatoDelProcedimento(idProcedimento, idUfficioUtente, codUtente, tipoUtente, 0);
			}
		}
		super.updateProcedimentoIntoSession(idProcedimento);
		return collegioElenco(request);
	}

	@RequestMapping(value = "/procedimento/collegio/associaRiesame.htm", method = RequestMethod.POST, params = { "Associa" })
	public ModelAndView collegioNuovoRicercaAssociaRiesame(@Valid ProcedimentoForm procedimentoForm, BindingResult result, HttpServletRequest request) {

		return collegioNuovoRicercaAssocia(procedimentoForm, result, request);
	}

	@RequestMapping(value = "/procedimento/collegio/associa.htm", method = RequestMethod.GET)
	public ModelAndView collegioNuovoRicercaOrdinamento(ProcedimentoForm procedimentoForm, BindingResult result, SessionStatus status, HttpServletRequest request) {

		MagistratoForm magistratoForm = (MagistratoForm) request.getSession().getAttribute("magistratoSA");
		ProcedimentoForm procedimento = new ProcedimentoForm();
		procedimento.setMagistratoForm(magistratoForm);
		request.getSession().removeAttribute("magistratoSA");
		return collegioNuovoRicerca(procedimento, result, request);
	}

	@RequestMapping(value = "/procedimento/collegio/associaRiesame.htm", method = RequestMethod.POST, params = { "Ricerca" })
	public ModelAndView collegioNuovoRicercaRiesame(@Valid ProcedimentoForm procedimentoForm, BindingResult result, HttpServletRequest request) {

		ModelAndView mnv = collegioNuovoRicerca(procedimentoForm, result, request);
		mnv.setViewName("/procedimento/magistrato/collegioAssociaRiesame");
		return mnv;
	}

	@RequestMapping(value = "/procedimento/collegio/associaRiesame.htm", method = RequestMethod.GET)
	public ModelAndView collegioNuovoRicercaOrdinamentoRiesame(ProcedimentoForm procedimentoForm, BindingResult result, SessionStatus status, HttpServletRequest request) {

		MagistratoForm magistratoForm = (MagistratoForm) request.getSession().getAttribute("magistratoSA");
		ProcedimentoForm procedimento = new ProcedimentoForm();
		procedimento.setMagistratoForm(magistratoForm);
		request.getSession().removeAttribute("magistratoSA");
		return collegioNuovoRicercaRiesame(procedimento, result, request);
	}

	/****************** Inizio Parte relativa al collegio gestito con l'atto *******************/
	/**
	 * elencoCollegioAssociatoAtto
	 * 
	 * @param magistratoForm
	 * @param result
	 * @param request
	 * @param modelMap
	 * @return String
	 */
	@RequestMapping(value = "/procedimento/atto/colleggio/elencoCollegioAssociatoAtto.htm", method = RequestMethod.GET)
	public String elencoCollegioAssociatoAtto(MagistratoForm magistratoForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {

		//Link Collegio dell'Atto
		Integer idUfficioUtente = recuperaIdUfficioUtente();
		long IdAtto = recuperaAtto().getCodAtto();
		TTpCatAtto tTpCatAtto = commonService.getCategoriaAtto(Long.valueOf(recuperaAtto().getCategoriaAtto().getId()));
		
		if (tTpCatAtto.getFlgCollegio()!=null && tTpCatAtto.getFlgCollegio()=='P')
		modelMap.addAttribute("presidenziale", "presidenziale");
		
		List<MagistratoForm> magistratiAssociati = attoService.listaMagistratiPerAtto(idUfficioUtente, IdAtto, 0);
		modelMap.addAttribute("magistratiAssociati", magistratiAssociati);
		return "/procedimento/magistrato/elencoCollegioAssociatoAtto";
	}

	@RequestMapping(value = "/procedimento/magistrato/associati/gestione.htm", method = RequestMethod.POST, params = { "Elimina" })
	public ModelAndView elencoElimina(ProcedimentoForm procedimentoForm, BindingResult result, HttpServletRequest request) {
		long idProcedimento = recuperaProcedimento().getIdProcedimento();
		Integer idUfficioUtente = recuperaIdUfficioUtente();
		List<MagistratoForm> magistratiAssociati = procedimentoService.listaMagistratiPerProcedimento(idUfficioUtente, idProcedimento, 0);

		magistratoValidation.validateSceltaRadioButton(procedimentoForm.getMagistratoForm(), result, request);
		if(!result.hasErrors()){
			magistratoValidation.validateUnicoMagistratoPresente(magistratiAssociati, result, request);
		}
		if (result.hasErrors()) {
			return elencoShow(request);
		}
		MagistratoForm magistratoForm = procedimentoForm.getMagistratoForm();
		
		procedimentoService.eliminaAssociazioneMagistratoProcedimento(magistratoForm, idProcedimento, idUfficioUtente);
		
		//simone
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", " ", true);
		//simone
				
		ModelAndView mnv = new ModelAndView("/procedimento/magistrato/elencoAssociati", "procedimentoForm", procedimentoForm);
		magistratiAssociati = procedimentoService.listaMagistratiPerProcedimento(idUfficioUtente, idProcedimento, 0);
		mnv.addObject("magistratiAssociati", magistratiAssociati);
		return mnv;
	}

	@RequestMapping(value = "/procedimento/magistrato/associati/gestione.htm", method = RequestMethod.POST, params = { "Modifica" })
	public ModelAndView elencoModifica(ProcedimentoForm procedimentoForm, BindingResult result, HttpServletRequest request) {

		magistratoValidation.validateSceltaRadioButton(procedimentoForm.getMagistratoForm(), result, request);
		if (result.hasErrors()) {
			return elencoShow(request);
		}
		long idProcedimento = recuperaProcedimento().getIdProcedimento();
		Integer idUfficioUtente = recuperaIdUfficioUtente();
		MagistratoForm magistratoForm = procedimentoService.getDettaglioMagistratoProcedimento(procedimentoForm.getMagistratoForm().getId(), idProcedimento, idUfficioUtente);
		ModelAndView mnv = new ModelAndView("/procedimento/magistrato/modifica", "magistratoForm", magistratoForm);
		
		//Giacomo 10/07/2013 Aggiunta breadcrumbs
		addBreadcrumbs("Modifica",request);
		return mnv;
	}

	@RequestMapping(value = "/procedimento/magistrato/modifica.htm", method = RequestMethod.POST)
	   public ModelAndView sceltaMotivoSostituzioneProcura(@Valid MagistratoForm magistratoForm, BindingResult result, HttpServletRequest request) {
	    
	    return new ModelAndView("/procedimento/magistrato/modifica", "magistratoForm", magistratoForm);	    
	}
	
	@RequestMapping(value = "/procedimento/magistrato/modifica.htm", method = RequestMethod.POST, params = { "Conferma" })
	public ModelAndView elencoModificaConferma(@Valid MagistratoForm magistratoForm, BindingResult result, HttpServletRequest request) {

		if (!result.hasErrors()) {
			magistratoValidation.validateAssociazioneMagistrato(magistratoForm, result, request);
		}
		if (result.hasErrors()) {
			return new ModelAndView("/procedimento/magistrato/modifica", "magistratoForm", magistratoForm);
		}
		long idProcedimento = recuperaProcedimento().getIdProcedimento();
		Integer idUfficioUtente = recuperaIdUfficioUtente();
		procedimentoService.modificaDatiMagistratoSuProcedimento(magistratoForm, idProcedimento, idUfficioUtente);
		String codUtente = recuperaIdCodiceUtente();
		String tipoUtente = (String) request.getSession().getAttribute(Constants.TIPO_UTENTE);
		procedimentoService.aggiornaStatoDelProcedimento(idProcedimento, idUfficioUtente, codUtente, tipoUtente, 0);
		super.updateProcedimentoIntoSession(idProcedimento);
		//simone
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", " ", true);
		//simone
		return elencoShow(request);
	}

	/**
	 * Visualizza l'elenco dei magistrati associati
	 * 
	 * @return ModelAndView: procedimento/magistrato/elencoAssociati.jsp
	 */
	@RequestMapping(value = "/procedimento/magistrato/elenco/visualizzaMagistratiAssociati.htm", method = RequestMethod.GET)
	public ModelAndView elencoShow(HttpServletRequest request) {

		ProcedimentoForm proced = recuperaProcedimento();
		boolean abilitazione = magistratoValidation.validateAbilitazioneNuovo(proced, request);
		if (!abilitazione) {
			return new ModelAndView("redirect:/procedimento/apri/copertinaProcedimento.htm");
		}
		ModelAndView mnv = new ModelAndView("/procedimento/magistrato/elencoAssociati", "procedimentoForm", new ProcedimentoForm());
		long idProcedimento = recuperaProcedimento().getIdProcedimento();
		Integer idUfficioUtente = recuperaIdUfficioUtente();
		List<MagistratoForm> magistratiAssociati = procedimentoService.listaMagistratiPerProcedimento(idUfficioUtente, idProcedimento, 0);
		mnv.addObject("magistratiAssociati", magistratiAssociati);
		
		//Giacomo 10/07/2013 Aggiunta Breadcrumb
		addBreadcrumbs();
		return mnv;
	}
	@RequestMapping(value = "/procedimento/magistrato/associati/visualizza.htm", method = RequestMethod.GET)
	public ModelAndView elencoShowPaginazione(HttpServletRequest request) {
		return elencoShow(request);
	}

	@RequestMapping(value = "procedimento/atto/collegio/associati/gestione.htm", method = RequestMethod.POST, params = { "Nuovo" })
	public String nuovoCollegioAssociatoAtto(MagistratoForm magistratoForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {

		//"RICERCA" Collegio dell'Atto
		long idAtto = recuperaAtto().getCodAtto();
		Integer idUffOwn = recuperaIdUfficioUtente();
		List<MagistratoForm> risultati = procedimentoService.ricercaMagistrati(magistratoForm, idUffOwn);
		List<MagistratoForm> magistratiAssociati = attoService.listaMagistratiPerAtto(idUffOwn, idAtto, 0);
		// escludo i magistrati già associati all'atto dalla lista dei
		// magistrati associabili
		for (int i = 0; (risultati != null) && (i < risultati.size()); i++) {
			MagistratoForm magDaAssociare = risultati.get(i);
			boolean trov = false;
			for (int j = 0; (magistratiAssociati != null) && (j < magistratiAssociati.size()) && !trov; j++) {
				MagistratoForm magAssociato = magistratiAssociati.get(j);
				if (magAssociato.getId().equals(magDaAssociare.getId())) {
					trov = true;
				}
			}
			if (trov) {
				risultati.remove(i);
				i--;
			}
		}
		modelMap.addAttribute("magistratiAssociati", magistratiAssociati);
		modelMap.addAttribute("risultati", risultati);
		
		return "/procedimento/magistrato/nuovoCollegioAssociatoAtto";
	}

	@RequestMapping(value = "/procedimento/atto/collegio/associaAdAtto.htm", method = RequestMethod.POST, params = { "Ricerca" })
	public String nuovoCollegioAssociatoAttoRicerca(MagistratoForm magistratoForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {

		//"RICERCA" Collegio dell'Atto
		long idAtto = recuperaAtto().getCodAtto();
		Integer idUffOwn = recuperaIdUfficioUtente();
		List<MagistratoForm> risultati = procedimentoService.ricercaMagistrati(magistratoForm, idUffOwn);
		List<MagistratoForm> magistratiAssociati = attoService.listaMagistratiPerAtto(idUffOwn, idAtto, 0);
		// escludo i magistrati già associati all'atto dalla lista dei
		// magistrati associabili
		for (int i = 0; (risultati != null) && (i < risultati.size()); i++) {
			MagistratoForm magDaAssociare = risultati.get(i);
			boolean trov = false;
			for (int j = 0; (magistratiAssociati != null) && (j < magistratiAssociati.size()) && !trov; j++) {
				MagistratoForm magAssociato = magistratiAssociati.get(j);
				if (magAssociato.getId().equals(magDaAssociare.getId())) {
					trov = true;
				}
			}
			if (trov) {
				risultati.remove(i);
				i--;
			}
		}
		modelMap.addAttribute("magistratiAssociati", magistratiAssociati);
		modelMap.addAttribute("risultati", risultati);
		return "/procedimento/magistrato/nuovoCollegioAssociatoAtto";
	}

	@RequestMapping(value = "/procedimento/atto/collegio/associaAdAtto.htm", method = RequestMethod.GET)
	public String nuovoCollegioAssociatoAttoRicercaGet(MagistratoForm magistratoForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {

		return nuovoCollegioAssociatoAttoRicerca(magistratoForm, result, request, modelMap);
	}

	@RequestMapping(value = "/procedimento/atto/collegio/associaAdAtto.htm", method = RequestMethod.POST, params = { "Associa" })
	public String nuovoCollegioAttoAssocia(MagistratoForm magistratoForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {

		//"ASSOCIA" Collegio dell'Atto
		long idAtto = recuperaAtto().getCodAtto();
		Integer idUffOwn = recuperaIdUfficioUtente();
		String codiUtente = recuperaIdCodiceUtente();
		magistratoValidation.validateSceltaRadioButton(magistratoForm, result, request);
		if (!result.hasErrors()) {
			//Modifica 01/2014
			magistratoValidation.validateMagistratoDelCollegioPerAtto(magistratoForm, result, request, idAtto, idUffOwn, /*true*/false);
			//Modifica 01/2014
		}
		if (result.hasErrors()) {
			return nuovoCollegioAssociatoAttoRicerca(magistratoForm, result, request, modelMap);
		}
		attoService.inserisciMagistratoPerAtto(idUffOwn, idAtto, magistratoForm.getId(), magistratoForm.getRuolo().getId(), magistratoForm.getRelatore(), codiUtente);
		return elencoCollegioAssociatoAtto(magistratoForm, result, request, modelMap);
	}

	/****************** Fine Parte relativa al collegio gestito con l'atto *******************/
	@RequestMapping(value = "/procedimento/magistrato/associa/ricerca.htm", method = RequestMethod.POST, params = { "Ricerca" })
	public ModelAndView nuovoRicerca(ProcedimentoForm procedimentoForm, BindingResult result, HttpServletRequest request) {

		ProcedimentoForm proced = recuperaProcedimento();
		long idProcedimento = proced.getIdProcedimento();
		MagistratoForm magistratoForm = procedimentoForm.getMagistratoForm();
		ModelAndView mnv = new ModelAndView("/procedimento/magistrato/associa", "procedimentoForm", procedimentoForm);
		Integer idUfficioUtente = recuperaIdUfficioUtente();
		List<MagistratoForm> magistratiAssociati = procedimentoService.listaMagistratiPerProcedimento(idUfficioUtente, idProcedimento, 0);
		mnv.addObject("magistratiAssociati", magistratiAssociati);
		List<MagistratoForm> magistratiDelProcedimento = procedimentoService.ricercaMagistrati(magistratoForm, idUfficioUtente);
		// escludo i magistrati già associati al procedimento dalla lista dei
		// magistrati associabili
		List<MagistratoForm> risultati = procedimentoService.listaMagistratiNonGiaAssociati(magistratiDelProcedimento, magistratiAssociati);
		mnv.addObject("risultati", risultati);
		
		//Giacomo 10/07/2013 Aggiunta Breadcrumb
		addBreadcrumbs("Ricerca",request);
		
		return mnv;
	}

	@RequestMapping(value = "/procedimento/magistrato/associa/ricerca.htm", method = RequestMethod.POST, params = { "Associa" })
	public ModelAndView nuovoRicercaAssocia(@Valid ProcedimentoForm procedimentoForm, BindingResult result, HttpServletRequest request) {

		MagistratoForm magistratoForm = procedimentoForm.getMagistratoForm();
		magistratoValidation.validateSceltaRadioButton(magistratoForm, result, request);
		if (!result.hasErrors()) {
			magistratoValidation.validateAssociazioneMagistratoProc(magistratoForm, result, request);
		}
		if (result.hasErrors()) {
			return nuovoRicerca(procedimentoForm, result, request);
		}
		ProcedimentoForm proced = recuperaProcedimento();
		long idProcedimento = proced.getIdProcedimento();
		Integer idUfficioUtente = recuperaIdUfficioUtente();
		
		magistratoForm.setRuolo(new ComboForm(Constants.ID_TIPO_RUOLO_PM, null));
		
		procedimentoService.associaMagistratoAlProcedimento(magistratoForm, idProcedimento, idUfficioUtente);
		String codUtente = recuperaIdCodiceUtente();
		String tipoUtente = (String) request.getSession().getAttribute(Constants.TIPO_UTENTE);
		procedimentoService.aggiornaStatoDelProcedimento(idProcedimento, idUfficioUtente, codUtente, tipoUtente, 0);
		super.updateProcedimentoIntoSession(idProcedimento);
		//simone
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", " ", true);
		//simone
		return elencoShow(request);
	}

	@RequestMapping(value = "/procedimento/magistrato/associa/ricerca.htm", method = RequestMethod.GET)
	public ModelAndView nuovoRicercaOrdinamento(ProcedimentoForm procedimentoForm, HttpServletRequest request) {

		ModelAndView mnv = new ModelAndView("/procedimento/magistrato/associa", "procedimentoForm", procedimentoForm);
		long idProcedimento = recuperaProcedimento().getIdProcedimento();
		Integer idUfficioUtente = recuperaIdUfficioUtente();
		List<MagistratoForm> magistratiAssociati = procedimentoService.listaMagistratiPerProcedimento(idUfficioUtente, idProcedimento, 0);
		mnv.addObject("magistratiAssociati", magistratiAssociati);
		List<MagistratoForm> risultati = null;
		if (procedimentoForm.getMagistratoForm() != null){
			risultati = procedimentoService.ricercaMagistrati(procedimentoForm.getMagistratoForm(), idUfficioUtente);
		}else{
			List<MagistratoForm> magistratiDelProcedimento = procedimentoService.ricercaMagistrati(new MagistratoForm(), idUfficioUtente);
			risultati = procedimentoService.listaMagistratiNonGiaAssociati(magistratiDelProcedimento, magistratiAssociati);
		}
		

		mnv.addObject("risultati", risultati);

		//Giacomo 10/07/2013 Aggiunta Breadcrumb
		addBreadcrumbs();
		return mnv;
	}

	@RequestMapping(value = "/procedimento/magistrato/associa/visualizza.htm", method = RequestMethod.GET)
	public ModelAndView nuovoShow(HttpServletRequest request) {

		ProcedimentoForm proced = recuperaProcedimento();
		boolean abilitazione = magistratoValidation.validateAbilitazioneNuovo(proced, request);
		if (!abilitazione) {
			return elencoShow(request);
		}
		request.getSession().removeAttribute("risultati");
		ModelAndView mnv = new ModelAndView("/procedimento/magistrato/associa", "procedimentoForm", new ProcedimentoForm());
		long idProcedimento = proced.getIdProcedimento();
		Integer idUfficioUtente = recuperaIdUfficioUtente();
		List<MagistratoForm> magistratiAssociati = procedimentoService.listaMagistratiPerProcedimento(idUfficioUtente, idProcedimento, 0);
		mnv.addObject("magistratiAssociati", magistratiAssociati);
		return mnv;
	}

	public void setMagistratoValidation(MagistratoValidation magistratoValidation) {

		this.magistratoValidation = magistratoValidation;
	}

	private boolean isCollegioCorrettamenteFormato(List<MagistratoForm> magistratiAssociati, ProcedimentoForm procedimentoForm) {

		String idTipoProc = null;
		if ((procedimentoForm != null) && (procedimentoForm.getTipoProcedimento() != null) && (procedimentoForm.getTipoProcedimento().getId() != null)) {
			idTipoProc = procedimentoForm.getTipoProcedimento().getId();
		}
		if ((idTipoProc != null) && idTipoProc.equals(Constants.TIPOPROC_RIESAME_PROVV_QUESTURA)) {
			return true;
		}
		if ((magistratiAssociati == null) || magistratiAssociati.isEmpty()) {
			return false;
		}
		int numeroPresidenti = 0;
		int numeroGiudiciALatere = 0;
		int numeroRelatori = 0;
		for (MagistratoForm tmp : magistratiAssociati) {
			if (tmp.getDataFineAssegnazione() == null) {
				if ((tmp.getRuolo() != null) && Constants.ID_TIPO_RUOLO_PRES.equalsIgnoreCase(tmp.getRuolo().getId())) {
//				if (tmp.getRuolo().equals("PRESIDENTE")) {
					numeroPresidenti++;
					return true;
				} else {
					numeroGiudiciALatere++;
				}
				if (tmp.getRelatore().equals("S")) {
					numeroRelatori++;
				}
			}
		}
		if ((idTipoProc != null) && idTipoProc.equals(Constants.TIPOPROC_AGGRAVAMENTO_PERSONALE)) {
			return ((numeroPresidenti == 1) && (numeroGiudiciALatere <= 2));
		}
		return ((numeroPresidenti == 1) && (numeroGiudiciALatere == 2) && (numeroRelatori == 1));
	}
	
	
	
	
//INIZIO giudice delegato 	
		
	
	@RequestMapping(value = "/procedimento/giudiceDelegato/elencoAssociatoAtto.htm", method = RequestMethod.GET)
	public ModelAndView giudiceDelegatoElencoAssociatoAtto(HttpServletRequest request) {

	//Link da MENU Atto
		ModelAndView mnv = new ModelAndView("/procedimento/magistrato/giudiceDelegatoElenco", "procedimentoForm", new ProcedimentoForm());
		Integer idUfficioUtente = recuperaIdUfficioUtente();
		long IdAtto = recuperaAtto().getCodAtto();
		long idProcedimento = recuperaIdProcedimento();
		String statoAtto = recuperaAtto().getStatoAtto().getId();
		mnv.addObject("statoAtto", statoAtto);
	 	
		List<MagistratoForm> magistratiAssociati = attoService.listaMagistratiPerAtto(idUfficioUtente, IdAtto, Integer.parseInt(Constants.ID_TIPO_RUOLO_GIU_DEL));
		mnv.addObject("Associazione", "ATTO");
		request.setAttribute("Associazione", "ATTO");
		
// GESTIONE GIUDICE DELEGATO -- se esiste già il GD a livello di procedimento vuol dire che c'è già un decreto di sequestro
//	registrato, quindi non devo fare inserire un altro GD   
		boolean esisteAmministratore = attoDao.controllaPresenzaGDPerProc(idUfficioUtente, idProcedimento);
		if (!esisteAmministratore) {
			mnv.addObject("Bottoni", "SI");
		} 
		
		mnv.addObject("magistratiAssociati", magistratiAssociati);
	
		addBreadcrumbs("GET","/procedimento/giudiceDelegato/elencoAssociatoAtto.htm",null);
	
		return mnv;
	}
	
	@RequestMapping(value = "/procedimento/giudiceDelegato/elencoAssociatoProcedimento.htm", method = RequestMethod.GET)
	public ModelAndView giudiceDelegatoElencoAssociatoProcedimento(HttpServletRequest request) {

	//link da MENU Copertina Procedimento	
		ModelAndView mnv = new ModelAndView("/procedimento/magistrato/giudiceDelegatoElenco", "procedimentoForm", new ProcedimentoForm());
		Integer idUfficioUtente = recuperaIdUfficioUtente();
		long idProcedimento = recuperaIdProcedimento();
		List<MagistratoForm> magistratiAssociati = procedimentoService.listaMagistratiPerProcedimento(idUfficioUtente, idProcedimento, Integer.parseInt(Constants.ID_TIPO_RUOLO_GIU_DEL), true);
		mnv.addObject("magistratiAssociati", magistratiAssociati);
		mnv.addObject("UffProprietario", "SI");
		
	//Bottone 'Sostituisci': la CdA deve poter sostituire il GD solo nei propri Procedimenti e non in quelli che provengono da Tribunale    
		if ((Integer) request.getSession().getAttribute("idTipoUfficio") == Constants.TIPO_UFFICIO_CORTE_D_APPELLO){
			if (magistratiAssociati != null){
				MagistratoForm magistratoForm = magistratiAssociati.get(0);
				KeyMessageDto messaggioRitorno = commonService.controllaRelMagistratoUfficio(idUfficioUtente, magistratoForm.getId());
				if (messaggioRitorno.isError()) {
					mnv.addObject("UffProprietario", "NO");
				}	
			}
		}
		mnv.addObject("Associazione", "PROCEDIMENTO");
		request.setAttribute("Associazione", "PROCEDIMENTO");
		
		addBreadcrumbs("GET","/procedimento/giudiceDelegato/elencoAssociatoProcedimento.htm",null);
		return mnv;
	}
	
	@RequestMapping(value = "/procedimento/giudiceDelegato/nuovo.htm", method = RequestMethod.GET)
	public ModelAndView giudiceDelegatoNuovo(@Valid MagistratoForm magistratoForm, BindingResult result, HttpServletRequest request) {

	//Solo da Atto
		ProcedimentoForm proced = recuperaProcedimento();
		boolean abilitazione = magistratoValidation.validateAbilitazioneCollegioNuovo(proced, request);
		if (!abilitazione) {
			return giudiceDelegatoElencoAssociatoAtto(request);
		}
		Integer idUfficioUtente = recuperaIdUfficioUtente();
		long IdAtto = recuperaAtto().getCodAtto();
		
		ModelAndView mnv = new ModelAndView("/procedimento/magistrato/giudiceDelegatoAssocia", "procedimentoForm", new ProcedimentoForm());
		List<MagistratoForm> magistratiAssociati = attoService.listaMagistratiPerAtto(idUfficioUtente, IdAtto, Integer.parseInt(Constants.ID_TIPO_RUOLO_GIU_DEL));
		mnv.addObject("magistratiAssociati", magistratiAssociati);
		List<MagistratoForm> magistratiDelProcedimento = procedimentoService.ricercaMagistrati(magistratoForm, idUfficioUtente);
		List<MagistratoForm> risultati = procedimentoService.listaMagistratiNonGiaAssociati(magistratiDelProcedimento, magistratiAssociati);
		mnv.addObject("risultati", risultati);
		

		addBreadcrumbs();
		return mnv;
	}
	
	@RequestMapping(value = "/procedimento/giudiceDelegato/associa.htm", method = RequestMethod.GET)
	public ModelAndView giudiceDelegatoPaginazione(ProcedimentoForm procedimentoForm, BindingResult result, SessionStatus status, HttpServletRequest request) {

		MagistratoForm magistratoForm = (MagistratoForm) request.getSession().getAttribute("magistratoSA");
		ProcedimentoForm procedimento = new ProcedimentoForm();
		procedimento.setMagistratoForm(magistratoForm);
		request.getSession().removeAttribute("magistratoSA");
		return giudiceDelegatoNuovoRicerca(procedimento, result, request);
	}
	
	@RequestMapping(value = "/procedimento/giudiceDelegato/associa.htm", method = RequestMethod.POST, params = { "Associa" })
	public ModelAndView giudiceDelegatoNuovoAssocia(@Valid ProcedimentoForm procedimentoForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {

		MagistratoForm magistratoForm = procedimentoForm.getMagistratoForm();
		ProcedimentoForm proced = recuperaProcedimento();
		long idProcedimento = proced.getIdProcedimento();
		Integer idUffOwn = recuperaIdUfficioUtente();
		String codiUtente = recuperaIdCodiceUtente();
		long IdAtto = recuperaAtto().getCodAtto();	
		
		magistratoValidation.validateSceltaRadioButton(magistratoForm, result, request);
		if (result.hasErrors()) {
			return giudiceDelegatoNuovoRicerca(procedimentoForm, result, request);
		}
		magistratoForm.setRuolo(new ComboForm(Constants.ID_TIPO_RUOLO_GIU_DEL,null));
		attoService.inserisciMagistratoPerAtto(idUffOwn, IdAtto, magistratoForm.getId(), magistratoForm.getRuolo().getId(), magistratoForm.getRelatore(), codiUtente);	
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", " ", true);
		
		super.updateProcedimentoIntoSession(idProcedimento);	
	
		AttoForm attoForm = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO)));
		modelMap.put("attoForm", attoForm);
		modelMap.addAttribute("attoForm", attoForm);
		
		String ritorno = attoController.copertinaAtto(attoForm, result, request, modelMap);

		return new ModelAndView(ritorno);
		
	}
	
	@RequestMapping(value = "/procedimento/giudiceDelegato/associa.htm", method = RequestMethod.POST, params = { "Ricerca" })
	public ModelAndView giudiceDelegatoNuovoRicerca(@Valid ProcedimentoForm procedimentoForm, BindingResult result, HttpServletRequest request) {

		MagistratoForm magistratoForm = procedimentoForm.getMagistratoForm();
		request.getSession().setAttribute("magistratoSA", magistratoForm);
		long idProcedimento = recuperaIdProcedimento();
		Integer idUfficioUtente = recuperaIdUfficioUtente();
		
		List<MagistratoForm> magistratiDelProcedimento = procedimentoService.ricercaMagistrati(magistratoForm, idUfficioUtente);
		ModelAndView mnv = new ModelAndView("/procedimento/magistrato/giudiceDelegatoAssocia", "procedimentoForm", procedimentoForm);
		
		List<MagistratoForm> magistratiAssociati;
	//vedo se provengo dall'Atto o dalla copertina Procedimento
		boolean AttoSi = false;
		long IdAtto = 0;
		try {
			IdAtto = recuperaAtto().getCodAtto();	
			AttoSi = true;
		}catch (Exception e){}	
		if(AttoSi)	{
			magistratiAssociati = attoService.listaMagistratiPerAtto(idUfficioUtente, IdAtto, Integer.parseInt(Constants.ID_TIPO_RUOLO_GIU_DEL));
		}else{
			magistratiAssociati = procedimentoService.listaMagistratiPerProcedimento(idUfficioUtente, idProcedimento, Integer.parseInt(Constants.ID_TIPO_RUOLO_GIU_DEL), true);	
		}
	// escludo i magistrati già associati al procedimento
		List<MagistratoForm> risultati = procedimentoService.listaMagistratiNonGiaAssociati(magistratiDelProcedimento, magistratiAssociati);
		mnv.addObject("magistratiAssociati", magistratiAssociati);
		mnv.addObject("risultati", risultati);
		
		addBreadcrumbs("POST","/procedimento/giudiceDelegato/associa.htm","Ricerca");
		return mnv;
	}

	@RequestMapping(value = "procedimento/giudiceDelegato/elenco.htm", method = RequestMethod.POST, params = { "Sostituisci" })
	public ModelAndView giudiceDelegatoSostituisciRicerca(@Valid ProcedimentoForm procedimentoForm, BindingResult result, HttpServletRequest request) {

		long idProcedimento = recuperaIdProcedimento();
		Integer idUfficioUtente = recuperaIdUfficioUtente();
		List<MagistratoForm> magistratiAssociati = procedimentoService.listaMagistratiPerProcedimento(idUfficioUtente, idProcedimento, Integer.parseInt(Constants.ID_TIPO_RUOLO_GIU_DEL), true);
		MagistratoForm magistratoForm = magistratiAssociati.get(0);
		List<MagistratoForm> magistratiDelProcedimento = procedimentoService.ricercaMagistrati(magistratoForm, idUfficioUtente);
		ModelAndView mnv = new ModelAndView("/procedimento/magistrato/giudiceDelegatoSostituisci", "procedimentoForm", procedimentoForm);
		// escludo i magistrati già associati al procedimento dalla lista
		List<MagistratoForm> risultati = procedimentoService.listaMagistratiNonGiaAssociati(magistratiDelProcedimento, magistratiAssociati);
		mnv.addObject("magistratiAssociati", magistratiAssociati);
		mnv.addObject("risultati", risultati);
		
		addBreadcrumbs("POST","/procedimento/giudiceDelegato/elenco.htm","Ricerca");
		return mnv;
	}
	
	
	@RequestMapping(value = "/procedimento/giudiceDelegato/sostituisci.htm", method = RequestMethod.POST, params = { "Ricerca" })
	public ModelAndView giudiceDelegatoNuovoRicercaSostituisci(@Valid ProcedimentoForm procedimentoForm, BindingResult result, HttpServletRequest request) {

		MagistratoForm magistratoForm = procedimentoForm.getMagistratoForm();
		long idProcedimento = recuperaIdProcedimento();
		Integer idUfficioUtente = recuperaIdUfficioUtente();
		
		List<MagistratoForm> magistratiDelProcedimento = procedimentoService.ricercaMagistrati(magistratoForm, idUfficioUtente);
		ModelAndView mnv = new ModelAndView("/procedimento/magistrato/giudiceDelegatoSostituisci", "procedimentoForm", procedimentoForm);
		
		List<MagistratoForm> magistratiAssociati;
	
		magistratiAssociati = procedimentoService.listaMagistratiPerProcedimento(idUfficioUtente, idProcedimento, Integer.parseInt(Constants.ID_TIPO_RUOLO_GIU_DEL), true);	
	// escludo i magistrati già associati al procedimento dalla lista 
		List<MagistratoForm> risultati = procedimentoService.listaMagistratiNonGiaAssociati(magistratiDelProcedimento, magistratiAssociati);
		mnv.addObject("magistratiAssociati", magistratiAssociati);
		mnv.addObject("risultati", risultati);
		
		//addBreadcrumbs("POST","/procedimento/giudiceDelegato/sostituisci.htm","Ricerca");
		return mnv;
	}
	
	@RequestMapping(value = "/procedimento/giudiceDelegato/sostituisci.htm", method = RequestMethod.GET)
	public ModelAndView giudiceDelegatoNuovoRicercaSostituisciPaginazione(@Valid ProcedimentoForm procedimentoForm, BindingResult result, HttpServletRequest request) {

		MagistratoForm magistratoForm = procedimentoForm.getMagistratoForm();
		long idProcedimento = recuperaIdProcedimento();
		Integer idUfficioUtente = recuperaIdUfficioUtente();
		
		List<MagistratoForm> magistratiDelProcedimento = procedimentoService.ricercaMagistrati(magistratoForm, idUfficioUtente);
		ModelAndView mnv = new ModelAndView("/procedimento/magistrato/giudiceDelegatoSostituisci", "procedimentoForm", procedimentoForm);
		
		List<MagistratoForm> magistratiAssociati;
	
		magistratiAssociati = procedimentoService.listaMagistratiPerProcedimento(idUfficioUtente, idProcedimento, Integer.parseInt(Constants.ID_TIPO_RUOLO_GIU_DEL), true);	
	// escludo i magistrati già associati al procedimento dalla lista 
		List<MagistratoForm> risultati = procedimentoService.listaMagistratiNonGiaAssociati(magistratiDelProcedimento, magistratiAssociati);
		mnv.addObject("magistratiAssociati", magistratiAssociati);
		mnv.addObject("risultati", risultati);
		
		//addBreadcrumbs("POST","/procedimento/giudiceDelegato/sostituisci.htm","Ricerca");
		return mnv;
	}	
	
	@RequestMapping(value = "/procedimento/giudiceDelegato/sostituisci.htm", method = RequestMethod.POST)
	public ModelAndView giudiceDelegatoSostituisci(@Valid ProcedimentoForm procedimentoForm, BindingResult result, HttpServletRequest request) {
		return giudiceDelegatoNuovoRicercaSostituisci(procedimentoForm, result, request);
	}
	
	
	@RequestMapping(value = "/procedimento/giudiceDelegato/sostituisci.htm", method = RequestMethod.POST, params = { "Associa" })
	public ModelAndView giudiceDelegatoNuovoAssociaSostituisci(@Valid ProcedimentoForm procedimentoForm, BindingResult result, HttpServletRequest request) {

		ProcedimentoForm proced = recuperaProcedimento();
		long idProcedimento = proced.getIdProcedimento();
		Integer idUffOwn = recuperaIdUfficioUtente();
		String codiUtente = recuperaIdCodiceUtente();
		
		MagistratoForm magistratoFormIn = procedimentoForm.getMagistratoForm();
		
		magistratoValidation.validateSceltaRadioButton(magistratoFormIn, result, request);
		if (result.hasErrors()) {
			return giudiceDelegatoNuovoRicercaSostituisci(procedimentoForm, result, request);
		}
		
		if(magistratoFormIn.getTipoSostituzione() == null) {
			super.setMessage(Constants.MESSAGE, "magistrato.error.tipoSostituzioneObbligatorioGD", null, false);
			return giudiceDelegatoNuovoRicercaSostituisci(procedimentoForm, result, request);
		}
		
		List<MagistratoForm> magistratiAssociati = procedimentoService.listaMagistratiPerProcedimento(idUffOwn, idProcedimento, Integer.parseInt(Constants.ID_TIPO_RUOLO_GIU_DEL), true);
		MagistratoForm magistratoFormOut = magistratiAssociati.get(0);
	
		procedimentoService.sostituisciGiudiceDelegato(idUffOwn, magistratoFormOut.getId(), magistratoFormIn.getId(), idProcedimento, magistratoFormIn.getTipoSostituzione(), codiUtente);	
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", " ", true);
		
//		String abilFascGest = configParameterSource.getMessage("ABIL_FASC_GEST", null, null);
//		if(abilFascGest!=null && abilFascGest.equals("S")) { 		
			//verifica fascicolo gestione -------
			HashMap fascGestione = commonService.getFascicoloGestione(idProcedimento, idUffOwn, codiUtente);
			if(fascGestione!=null) {
				Long idProcGest = (Long) fascGestione.get("idProcGest");
				Long idUffRic = (Long) fascGestione.get("idUffRic");
				if(idProcGest!=null && idUffRic!=null && idProcGest>0 && idUffRic>0) {
					procedimentoService.sostituisciGiudiceDelegato(idUffRic.intValue(), magistratoFormOut.getId(), magistratoFormIn.getId(), idProcGest, magistratoFormIn.getTipoSostituzione(), codiUtente);
				}
			}
			//fine verifica fascicolo gestione -------
//		}
		super.updateProcedimentoIntoSession(idProcedimento);	
		return giudiceDelegatoElencoAssociatoProcedimento(request);
		
	}
	
	@RequestMapping(value = "/procedimento/giudiceDelegato/elenco.htm", method = RequestMethod.POST, params = { "Elimina" })
	public ModelAndView giudiceDelegatoElencoAssociatoAttoElimina(ProcedimentoForm procedimentoForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {
		
		MagistratoForm magistratoForm = procedimentoForm.getMagistratoForm();
		magistratoValidation.validateSceltaRadioButton(magistratoForm, result, request);
		if (result.hasErrors()) {
			return giudiceDelegatoElencoAssociatoAtto(request);
		}
		long IdAtto = recuperaAtto().getCodAtto();
		Integer idUffOwn = recuperaIdUfficioUtente();
		
		attoService.eliminaRelMagAtto(idUffOwn, IdAtto, magistratoForm.getId(), Constants.ID_TIPO_RUOLO_GIU_DEL);
		
		return giudiceDelegatoElencoAssociatoAtto(request);
	}

		
}
