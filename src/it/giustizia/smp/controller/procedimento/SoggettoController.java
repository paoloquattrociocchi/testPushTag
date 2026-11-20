package it.giustizia.smp.controller.procedimento;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import it.giustizia.smp.controller.BreadcrumbsController;
import it.giustizia.smp.controller.MenuController;
import it.giustizia.smp.dto.KeyMessageDto;
import it.giustizia.smp.dto.pg_cameralini.IntestatarioDto;
import it.giustizia.smp.dto.pg_procedimento.MisurePatrSoggDto;
import it.giustizia.smp.dto.pg_procedimento.MisurePersSoggDto;
import it.giustizia.smp.dto.pg_soggetto.BeniCollegatiTerzoDto;
import it.giustizia.smp.dto.pg_soggetto.DettaglioSoggettoDto;
import it.giustizia.smp.dto.pg_soggetto.RiepilogoSoggettoDto;
import it.giustizia.smp.form.AmministratoreForm;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.BeneForm;
import it.giustizia.smp.form.BeneSoggettoForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.DifensoreSoggettoForm;
import it.giustizia.smp.form.EredeConviventeForm;
import it.giustizia.smp.form.MisuraForm;
import it.giustizia.smp.form.NumeroRegistroForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.ProfiloForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.form.SoggettoStralcioForm;
import it.giustizia.smp.model.TProcedimento;
import it.giustizia.smp.model.TRelAttoIstGen;
import it.giustizia.smp.model.TRelAttoIstGenId;
import it.giustizia.smp.model.TRelBeneAtto;
import it.giustizia.smp.model.TRelProcProc;
import it.giustizia.smp.model.TRelProcSogint;
import it.giustizia.smp.model.TRelSoginvBene;
import it.giustizia.smp.model.TRelSoginvBeneId;
import it.giustizia.smp.model.TRelSoginvSogint;
import it.giustizia.smp.model.TRelSoginvSogintId;
import it.giustizia.smp.service.AnnullamentoDuplicatoService;
import it.giustizia.smp.service.AttoService;
import it.giustizia.smp.service.BeneService;
import it.giustizia.smp.service.CommonService;
import it.giustizia.smp.service.MisuraService;
import it.giustizia.smp.service.ProcedimentoService;
import it.giustizia.smp.service.SoggettoService;
import it.giustizia.smp.service.cameralini.ProcedimentoServiceCam;
import it.giustizia.smp.service.webservices.AgenziaEntrateService;
import it.giustizia.smp.utility.ComparatorUtil;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.RequestUtil;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.validation.BeneValidation;
import it.giustizia.smp.validation.ProcedimentoValidation;
import it.giustizia.smp.validation.SoggettoValidation;
import it.giustizia.smp.webservice.client.agenziaentrate.EsitoControlloCFePIWrapper;
import it.giustizia.smp.webservice.client.documentale.stub.Configuration;

/**
 * SoggettoController
 * 
 * @author Almaviva S.p.A. Classe Controller per la gestione di soggetti del procedimento
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Controller
public class SoggettoController extends BreadcrumbsController {

	@Autowired
	private AttoController attoController;
	@Autowired
	private BeneService beneService;
	@Autowired
	private BeneValidation beneValidation;
	@Autowired
	private CommonService commonService;
	@Autowired
	private MisuraService misuraService;
	@Autowired
	private ProcedimentoService procedimentoService;
	@Autowired
	private AttoService attoService;
	@Autowired
	private SoggettoService soggettoService;
	@Autowired
	private AnnullamentoDuplicatoService annullamentoDuplicatoService;
	@Autowired
	private ProcedimentoValidation procedimentoValidation;
	@Autowired(required = true)
	private HttpServletRequest request;
	@Autowired
	private SoggettoValidation soggettoValidation;
	@Autowired
	private Utility utility;
	@Autowired
	private ProcedimentoServiceCam procedimentoServiceCam;

	@Autowired
	private AgenziaEntrateService agenziaEntrateService;

	/**
	 * Procedura per convertire le lettere accentate con apice semplice
	 * 
	 * @param marc
	 * @return
	 */
	private String convertiAccenti(String marc) {

		if (marc != null) {
			marc = marc.toLowerCase();
			marc = marc.replace("à", "a'");
			marc = marc.replace("è", "e'");
			marc = marc.replace("é", "e'");
			marc = marc.replace("ì", "i'");
			marc = marc.replace("ò", "o'");
			marc = marc.replace("ù", "u'");
			marc = marc.toUpperCase();
		}
		return marc;
	}
	

	/**
	 * funzione per estrapolare i dati dai checkbox paginati
	 * 
	 * @param request
	 * @return
	 */
	private List<BeneForm> estrapolaListaBeniCheckBoxPaged(HttpServletRequest request) {

		List<BeneForm> risultati = null;
		Set<String> procList = getCheckBoxPaged(request, "proc_chk");
		Set<String> subprocList = getCheckBoxPaged(request, "subproc_chk");
		if (procList != null && subprocList != null) {
			risultati = new ArrayList<BeneForm>();
			// scorre i proc checkbox
			Iterator<String> procIter = procList.iterator();
			while (procIter.hasNext()) {
				String sBene = procIter.next();
				Long lBene = Long.parseLong(sBene);
				BeneForm elem = new BeneForm();
				// definisco [idBene]
				elem.setIdBene(lBene);
				List<String> subCheckboxString = new ArrayList<String>();
				// scorre i sub-proc checkbox
				Iterator<String> subprocIter = subprocList.iterator();
				while (subprocIter.hasNext()) {
					String soggRec = subprocIter.next();
					String[] soggList = soggRec.split(";");
					if (sBene.equals(soggList[0])) {
						// sono nel subcheck relativo
						String tmp = soggList[1] + ";" + soggList[2];
						if (!subCheckboxString.contains(tmp)) {
							subCheckboxString.add(tmp);
						}
					}
				}
				// definisco [listaIdCheckboxString]
				elem.setListaIdCheckboxString(subCheckboxString);
				// aggiungo dati bene selezionato
				risultati.add(elem);
			}
		}
		return risultati;
	}

	/**
	 * funzione per prelevare i checkbox paginati
	 * 
	 * @param beneForm
	 * @param request
	 */
	private Set<String> getCheckBoxPaged(HttpServletRequest request, String name) {

		Set<String> listaIdCheckbox = new HashSet<String>();
		String[] checkArray = request.getParameterValues(name);
		if (checkArray != null && checkArray.length > 0) {
			for (int ic = 0; ic < checkArray.length; ic++) {
				String xxx = checkArray[ic];
				listaIdCheckbox.add(xxx);
			}
		}
		return listaIdCheckbox;
	}

	/**
	 * funzione per rimappare i value sui sub-checkbox contenete anche l'id della riga
	 * 
	 * @param bsfList
	 */
	private void mapSubCheckBox(List<BeneSoggettoForm> bsfList) {

		if (bsfList != null) {
			for (BeneSoggettoForm bsf : bsfList) {
				String bnd = bsf.getBene().getIdBene().toString();
				List<SoggettoForm> sfList = bsf.getSoggetti();
				for (SoggettoForm sf : sfList) {
					String sub = bnd + ";" + sf.getIdSoggetto() + ";" + sf.getTipoInteresse().getId();
					sf.setSubCheck(sub);
				}
			}
		}
	}

	@RequestMapping(value = "/procedimento/soggetto/gestioneDifensori.htm", method = RequestMethod.POST, params = {
			"AbilitaRicerca" })
	public ModelAndView AbilitaRicercaDifensori(DifensoreSoggettoForm difensoreSoggettoForm,
			BindingResult result) {

		// altrimenti cancella il difensore anche senza selezionarlo
		difensoreSoggettoForm.getDifensoreForm().setIdDifensore(null);
		caricaValoriDettaglioSoggetto(difensoreSoggettoForm.getSoggettoForm());
		ModelAndView mnv = difensoriAssociatiJsp(difensoreSoggettoForm, result);
		mnv.addObject("abilitaRicerca", true);

		addBreadcrumbs("AbilitaRicerca", request);

		return mnv;
	}

	@RequestMapping(value = "/procedimento/soggetto/difensoriAssociatiModifica.htm", method = RequestMethod.POST, params = {
			"Aggiorna" })
	public ModelAndView aggiornaDifensoreDaReginde(DifensoreSoggettoForm difensoreSoggettoForm,
			BindingResult result) {

		caricaValoriDettaglioSoggetto(difensoreSoggettoForm.getSoggettoForm());
		difensoreSoggettoForm.setSoggettoForm(procedimentoService
				.caricaValoriGratuitoPatrocinioDelSoggetto(difensoreSoggettoForm.getSoggettoForm()));
		if (difensoreSoggettoForm.getSoggettoForm().getGratuitoPatrocinio() == true) {
			difensoreSoggettoForm.setGratuitoPatrocinioCaricato(true);
		} else {
			difensoreSoggettoForm.setGratuitoPatrocinioCaricato(false);
		}
		return difensoriAssociatiJsp(difensoreSoggettoForm, result);
	}

	@RequestMapping(value = "/procedimento/soggetto/direttiInteressati.htm", method = RequestMethod.POST, params = {
			"AggiungiSoggettoFisico" })
	public ModelAndView aggiungiDirettoInteressatoFisico(SoggettoForm soggettoForm) {

		return new ModelAndView("procedimento/nuovo/ricercaSoggettoFisico", "soggettoForm", soggettoForm);
	}

	@RequestMapping(value = "/procedimento/soggetto/direttiInteressati.htm", method = RequestMethod.POST, params = {
			"AggiungiSoggettoGiuridico" })
	public ModelAndView aggiungiDirettoInteressatoGiuridico(SoggettoForm soggettoForm) {

		return new ModelAndView("procedimento/nuovo/ricercaSoggettoGiuridico", "soggettoForm", soggettoForm);
	}

	public ModelAndView aggravamentoMisuraPersonale(SoggettoForm soggettoForm, BindingResult result) {

		soggettoValidation.validateGestioneSoggetto(soggettoForm, result, request);
		if (result.hasErrors()) {
			return visualizzaSoggettiDirettiInteressati(soggettoForm);
		}
		procedimentoService.setIdSoggettoIdVersioneTipoSoggFromChiave(soggettoForm);
		soggettoValidation.validateBottoneMisuraPersonale(soggettoForm.getTipoSoggetto(),
				soggettoForm.getMisura().getDescrizioneMisura(), null, result, request);
		if (result.hasErrors()) {
			return visualizzaSoggettiDirettiInteressati(soggettoForm);
		}
		soggettoForm.setIdUfficio((Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE));
		soggettoForm.setIdProcedimento(
				((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO)))
						.getIdProcedimento());
		soggettoForm.setIdAtto(0L);
		soggettoForm = misuraService.caricaMisuraPersonale(soggettoForm);

		if ((soggettoForm.getMisura() != null) && (soggettoForm.getMisura().getCauzione() != null)
				&& ("S".equals(soggettoForm.getMisura().getCauzione())))
			request.setAttribute("importoCauzione", "true");

		// Carico le informazioni da T_REL_PROC_SOGINT
		soggettoForm = procedimentoService.caricaValoriGratuitoPatrocinioDelSoggetto(soggettoForm);
		String tipologiaPrev = soggettoForm.getTipologiaPrevenzione().getDescrizione();
		if ("F".equals(soggettoForm.getTipoSoggetto())) {
			soggettoForm = procedimentoService.caricaValoriDettaglioSoggettoFisicoProcedimento(soggettoForm);
			soggettoForm.setTipoSoggetto(Constants.SOGGETTO_FISICO);
			if (soggettoForm.getTipologiaPrevenzione() != null
					&& soggettoForm.getTipologiaPrevenzione().getDescrizione() == null) {
				soggettoForm.getTipologiaPrevenzione().setDescrizione(tipologiaPrev);
			}
		} else {
			soggettoForm = procedimentoService
					.caricaValoriDettaglioSoggettoGiuridicoProcedimento(soggettoForm);
			soggettoForm.setTipoSoggetto(Constants.SOGGETTO_GIURIDICO);
			if (soggettoForm.getTipologiaPrevenzione() != null
					&& soggettoForm.getTipologiaPrevenzione().getDescrizione() == null) {
				soggettoForm.getTipologiaPrevenzione().setDescrizione(tipologiaPrev);
			}
		}
		ModelAndView mnv = new ModelAndView("procedimento/soggetto/misuraPersonaleReadOnly", "soggettoForm",
				soggettoForm);
		List<ComboForm> list = commonService.listaPrescrizioniMisuraPersonale();
		mnv.addObject("listaPrescrizioni", list);
		if (soggettoForm.getMisura().getVecchiePrescrizioni() != null)
			mnv.addObject("listaVecchiePrescrizioni", soggettoForm.getMisura().getVecchiePrescrizioni());
		// Carico nel ModelAndView le Normative
		caricaNormative(soggettoForm, mnv, null);
		return mnv;
	}

	@RequestMapping(value = "/procedimento/soggetto/gestioneDifensori.htm", method = RequestMethod.POST, params = {
			"AssociaDifensoreReginde" })
	public ModelAndView associaDifensoreReginde(DifensoreSoggettoForm difensoreSoggettoForm,
			BindingResult result) {

		soggettoValidation.validateTabellaDifensori(difensoreSoggettoForm, result, request);
		if (result.hasErrors())
			return ricercaDifensoriReginde(difensoreSoggettoForm, result);
		if (difensoreSoggettoForm.getDifensoreForm() != null
				&& difensoreSoggettoForm.getDifensoreForm().getIdReginde() != null) {
			List<DifensoreSoggettoForm> listaDifensoriReginde = (List<DifensoreSoggettoForm>) super.getIntoSession(
					"listaReginde", false);
			Iterator iter = listaDifensoriReginde.iterator();
			while (iter.hasNext()) {
				DifensoreSoggettoForm difensore = (DifensoreSoggettoForm) iter.next();
				if (difensore.getDifensoreForm().getCodiceFiscale().equalsIgnoreCase(
						difensoreSoggettoForm.getDifensoreForm().getIdReginde().replaceAll(",", ""))) {
					difensoreSoggettoForm.setDifensoreForm(difensore.getDifensoreForm());
					break;
				}
			}
		}

		difensoreSoggettoForm.getDifensoreForm().setIdDifensore(null);
		caricaValoriDettaglioSoggetto(difensoreSoggettoForm.getSoggettoForm());

		// addBreadcrumbs("AssociaDifensore");
		// return new ModelAndView("procedimento/soggetto/difensoriAssociatiModifica",
		// "difensoreSoggettoForm", difensoreSoggettoForm);
		return new ModelAndView("procedimento/soggetto/difensoriAssociaNuovo", "difensoreSoggettoForm",
				difensoreSoggettoForm);
	}

	@RequestMapping(value = "/procedimento/soggetto/gestioneDifensori.htm", method = RequestMethod.POST, params = {
			"AssociaDifensoreBaseDati" })
	public ModelAndView associaDifensoreBaseDati(DifensoreSoggettoForm difensoreSoggettoForm,
			BindingResult result) {

		soggettoValidation.validateTabellaDifensori(difensoreSoggettoForm, result, request);
		if (result.hasErrors()) {
			return ricercaDifensoriReginde(difensoreSoggettoForm, result);
		}
		if (difensoreSoggettoForm.getDifensoreForm() != null
				&& difensoreSoggettoForm.getDifensoreForm().getIdDifensore() != null) {
			super.removeFromSession("listaReginde");
			difensoreSoggettoForm.setDifensoreForm(procedimentoService
					.dettaglioDifensore(difensoreSoggettoForm.getDifensoreForm().getIdDifensore())
					.getDifensoreForm());
		}

		caricaValoriDettaglioSoggetto(difensoreSoggettoForm.getSoggettoForm());

		// addBreadcrumbs("AssociaDifensore");
		// return new ModelAndView("procedimento/soggetto/difensoriAssociatiModifica",
		// "difensoreSoggettoForm", difensoreSoggettoForm);
		return new ModelAndView("procedimento/soggetto/difensoriAssociaNuovo", "difensoreSoggettoForm",
				difensoreSoggettoForm);
	}

	// metodo per cambiare pagina ai soggetti della tabella
	@RequestMapping(value = "/procedimento/soggetto/misuraPersonale.htm", method = RequestMethod.GET)
	public ModelAndView cambioPaginaMisuraPersonale(SoggettoForm soggettoForm, ModelMap modelMap) {

		return new ModelAndView("/procedimento/soggetto/misuraPersonale", "soggettoForm", soggettoForm);
	}

	@RequestMapping(value = "/procedimento/soggetto/gestioneDifensori.htm", method = RequestMethod.POST, params = {
			"CancellaDifensore" })
	public ModelAndView cancellaDifensoreAssociato(DifensoreSoggettoForm difensoreSoggettoForm,
			BindingResult result) {

		soggettoValidation.validateTabellaDifensoriAssociati(difensoreSoggettoForm, result, request);
		caricaValoriDettaglioSoggetto(difensoreSoggettoForm.getSoggettoForm());
		if (!result.hasErrors()) {
			boolean errore = procedimentoService.verificaCancellaDifensoreAssociato(difensoreSoggettoForm);
			if (!errore) {
				ProcedimentoForm procedimentoForm = (ProcedimentoForm) (request.getSession()
						.getAttribute(Constants.ID_PROCEDIMENTO));
				if ((procedimentoForm.getTipoProcedimento().getId()
						.equals("" + Constants.TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM))
						|| (procedimentoForm.getTipoProcedimento().getId()
								.equals("" + Constants.TIPOPROC_CORREZIONE_ERRORI_CAM)))
					procedimentoService.cancellaDifensoreAssociatoIntestatario(difensoreSoggettoForm);
				else
					procedimentoService.cancellaDifensoreAssociato(difensoreSoggettoForm);
			} else {
				// Presente un atto con il Difensore corrente
				super.setMessage(Constants.MESSAGE, "soggetto.difensori.cancella.error", null, false);
			}
		}
		return difensoriAssociatiJsp(difensoreSoggettoForm, result);
	}

	@RequestMapping(value = "/procedimento/soggetto/modificaSoggettoFisico.htm", method = RequestMethod.POST, params = {
			"CancellaErede" })
	public ModelAndView cancellaErede(@Valid SoggettoForm soggettoForm, BindingResult result) {

		ModelAndView mnv = new ModelAndView("procedimento/soggetto/modificaSoggettoFisico", "soggettoForm",
				soggettoForm);
		soggettoValidation.validateTabellaErediAssociati(soggettoForm, result, request);
		if (!result.hasErrors()) {
			procedimentoService.cancellaEredeDelDefunto(soggettoForm);
		}
		if (soggettoForm.getGestioneEredi() == true) {
			List<EredeConviventeForm> listaEredi = procedimentoService.listaErediDelDefunto(soggettoForm);
			mnv.addObject("erediAssociati", listaEredi);
		}
		return mnv;
	}

	@RequestMapping(value = "/procedimento/soggetto/visualizzaDirettiInteressatiAtto.htm", method = RequestMethod.POST, params = {
			"Dettaglio" })
	public ModelAndView dettaglioSoggettiDirettiInteressatiAtto(SoggettoForm soggettoForm,
			BindingResult result) {

		addBreadcrumbs("procedimento/atto/copertinaAtto.htm", request);
		request.setAttribute("contesto", "atto");
		soggettoValidation.validateGestioneSoggetto(soggettoForm, result, request);
		if (result.hasErrors()) {
			return visualizzaSoggettiDirettiInteressatiAtto(soggettoForm);
		}
		return includedettaglioSoggettiDirettiInteressati(soggettoForm);
	}

	@RequestMapping(value = "/procedimento/soggetto/visualizzaDirettiInteressatiRiesame.htm", method = RequestMethod.POST, params = {
			"Dettaglio" })
	public ModelAndView dettaglioSoggettiDirettiInteressatiRiesame(SoggettoForm soggettoForm,
			BindingResult result) {

		addBreadcrumbs("POST", "/procedimento/soggetto/visualizzaDirettiInteressatiRiesame.htm", "Dettaglio");
		soggettoValidation.validateGestioneSoggetto(soggettoForm, result, request);
		if (result.hasErrors()) {
			return visualizzaSoggettiDirettiInteressatiRiesame(soggettoForm);
		}
		procedimentoService.setIdSoggettoIdVersioneTipoSoggFromChiave(soggettoForm);
		soggettoForm.setIdUfficio((Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE));
		soggettoForm.setIdProcedimento(
				((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO)))
						.getIdProcedimento());
		procedimentoService.caricaValoriDettaglioSoggettoFisicoProcedimento(soggettoForm);
		return (new ModelAndView("procedimento/nuovo/riesame/dettaglioSoggettoFisicoProcedimentoRiesame",
				"soggettoForm", soggettoForm)).addObject("indietro",
						"/procedimento/soggetto/visualizzaDirettiInteressatiRiesame.htm");
	}

	@RequestMapping(value = { "/procedimento/soggetto/visualizzaDirettiInteressati.htm",
			"/procedimento/soggetto/visualizzaDirettiInteressatiTerzi.htm",
			"/procedimento/soggetto/visualizzaDirettiInteressatiRiesame.htm" }, method = RequestMethod.POST, params = {
					"Difensori" })
	public ModelAndView difensoriAssociati(SoggettoForm soggettoForm, BindingResult result) {

		ProcedimentoForm procedimentoForm = (ProcedimentoForm) request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO);
		DifensoreSoggettoForm difensoreSoggettoForm = new DifensoreSoggettoForm();
		if (procedimentoForm.getTipoProcedimento() != null
				&& (Long.toString(Constants.TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM)
						.equals(procedimentoForm.getTipoProcedimento().getId())
						|| Long.toString(Constants.TIPOPROC_CORREZIONE_ERRORI_CAM)
								.equals(procedimentoForm.getTipoProcedimento().getId()))) {
			if (soggettoForm.getIdSoggetto() == null) {
				result.reject(Constants.MESSAGE);
				super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
				if (request.getSession().getAttribute("RuoloSession") != null
						&& request.getSession().getAttribute("RuoloSession").equals(Constants.RUOLO_TERZO)) {
					request.getSession().removeAttribute("RuoloSession");
					return visualizzaSoggettiDirettiInteressatiTerzi(soggettoForm);
				} else
					return visualizzaSoggettiDirettiInteressati(soggettoForm);
			} else {
				soggettoForm.setIdVersione(0L);
				difensoreSoggettoForm.setSoggettoForm(soggettoForm);
				caricaValoriDettaglioSoggetto(difensoreSoggettoForm.getSoggettoForm());
			}
		} else {
			if (isInterveniente(soggettoForm)) {
				soggettoValidation.validateGestioneSoggettoInterveniente(soggettoForm, result, request);
			} else {
				soggettoValidation.validateGestioneSoggetto(soggettoForm, result, request);
			}

			if (result.hasErrors()) {
				if (request.getSession().getAttribute("RuoloSession") != null
						&& request.getSession().getAttribute("RuoloSession").equals(Constants.RUOLO_TERZO)) {
					request.getSession().removeAttribute("RuoloSession");
					return visualizzaSoggettiDirettiInteressatiTerzi(soggettoForm);
				} else {
					if (isInterveniente(soggettoForm)) {
						return visualizzaIntervenienti(soggettoForm, request);
					} else {
						return visualizzaSoggettiDirettiInteressati(soggettoForm);
					}

				}
			}
			procedimentoService.setIdSoggettoIdVersioneTipoSoggFromChiave(soggettoForm);
			difensoreSoggettoForm.setSoggettoForm(soggettoForm);
			caricaValoriDettaglioSoggetto(difensoreSoggettoForm.getSoggettoForm());
			difensoreSoggettoForm.setSoggettoForm(procedimentoService
					.caricaValoriGratuitoPatrocinioDelSoggetto(difensoreSoggettoForm.getSoggettoForm()));
			if (difensoreSoggettoForm.getSoggettoForm().getGratuitoPatrocinio() == true) {
				difensoreSoggettoForm.setGratuitoPatrocinioCaricato(true);
			} else {
				difensoreSoggettoForm.setGratuitoPatrocinioCaricato(false);
			}
		}
		request.getSession().removeAttribute("RuoloSession");

		// Giacomo 22/07/2013
		addBreadcrumbs("Difensori", request);
		return difensoriAssociatiJsp(difensoreSoggettoForm, result);
	}

	@RequestMapping(value = "/procedimento/soggettiInteressati/dettaglio.htm", method = RequestMethod.POST, params = {
			"CONFERMA" })
	public ModelAndView direttiInteressatiCancellaConferma(SoggettoForm soggettoForm, BindingResult result) {

		addBreadcrumbs("CancellaConferma", request);
		boolean ruoloTerzo = false;
		String ruolo = (String) request.getSession().getAttribute("RuoloSession");
		if (ruolo != null && ruolo.equals("TERZO"))
			ruoloTerzo = true;
		soggettoValidation.validateGestioneSoggetto(soggettoForm, result, request);
		if (result.hasErrors()) {
			return includedettaglioSoggettiDirettiInteressati(soggettoForm);
		}
		ProcedimentoForm procedimentoForm = (ProcedimentoForm) request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO);
		long idProcedimento = procedimentoForm.getIdProcedimento();
		int idUfficioUtente = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		long idSoggetto = soggettoForm.getIdSoggetto();
		procedimentoValidation.validateCancellazioneSoggettoInteressato(idProcedimento, idSoggetto,
				idUfficioUtente, result);
		if (result.hasErrors()) {
			return includedettaglioSoggettiDirettiInteressati(soggettoForm);
		}

		procedimentoService.cancellaDirettoInteressato(idSoggetto, idProcedimento, idUfficioUtente,
				soggettoForm.getIdVersione(), soggettoForm.getTipoSoggetto(), result, ruoloTerzo);
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation", "cancellazione", false);
		if (ruoloTerzo == true)
			return visualizzaSoggettiDirettiInteressatiTerzi(soggettoForm);
		else
			return visualizzaSoggettiDirettiInteressati(soggettoForm);
	}

	@RequestMapping(value = { "/procedimento/soggetto/visualizzaDirettiInteressati.htm",
			"/procedimento/soggetto/visualizzaDirettiInteressatiTerzi.htm" }, method = RequestMethod.POST, params = {
					"CANCELLA" })
	public ModelAndView direttiInteressatiCancellaDettaglio(SoggettoForm soggettoForm, BindingResult result) {

		String ruolo = (String) request.getSession().getAttribute("RuoloSession");
		soggettoValidation.validateGestioneSoggetto(soggettoForm, result, request);
		if (result.hasErrors()) {
			if (ruolo != null && ruolo.equals("TERZO"))
				return visualizzaSoggettiDirettiInteressatiTerzi(soggettoForm);
			else
				return visualizzaSoggettiDirettiInteressati(soggettoForm);
		}
		ProcedimentoForm procedimentoForm = (ProcedimentoForm) request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO);
		long idProcedimento = procedimentoForm.getIdProcedimento();
		int idUfficioUtente = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		long idSoggetto = soggettoForm.getIdSoggetto();
		procedimentoValidation.validateCancellazioneSoggettoInteressato(idProcedimento, idSoggetto,
				idUfficioUtente, result);
		if (result.hasErrors()) {
			if (ruolo != null && ruolo.equals("TERZO"))
				return visualizzaSoggettiDirettiInteressatiTerzi(soggettoForm);
			else
				return visualizzaSoggettiDirettiInteressati(soggettoForm);
		}

		// Giacomo 19/07/2013
		addBreadcrumbs("CANCELLA", request);
		request.setAttribute("provenienza", "cancellazione");
		return includedettaglioSoggettiDirettiInteressati(soggettoForm);
	}

	@RequestMapping(value = { "/procedimento/soggetto/visualizzaDirettiInteressati.htm",
			"/procedimento/soggetto/visualizzaDirettiInteressatiTerzi.htm" }, method = RequestMethod.POST, params = {
					"Dettaglio" })
	public ModelAndView direttiInteressatiDettaglio(SoggettoForm soggettoForm, BindingResult result) {

		addBreadcrumbs("Dettaglio", request);

		if (isInterveniente(soggettoForm)) {
			soggettoValidation.validateGestioneSoggettoInterveniente(soggettoForm, result, request);
		} else {
			soggettoValidation.validateGestioneSoggetto(soggettoForm, result, request);
		}

		if (result.hasErrors()) {
			if (request.getSession().getAttribute("RuoloSession") != null
					&& request.getSession().getAttribute("RuoloSession").equals(Constants.RUOLO_TERZO)) {
				request.getSession().removeAttribute("RuoloSession");
				return visualizzaSoggettiDirettiInteressatiTerzi(soggettoForm);
			} else {
				if (isInterveniente(soggettoForm)) {
					return visualizzaIntervenienti(soggettoForm, request);
				} else {
					return visualizzaSoggettiDirettiInteressati(soggettoForm);
				}

			}
		}
		// Giacomo 12/07/2013 Aggiunta Breadcrumbs
		/*
		 * addBreadcrumbs("Dettaglio");
		 */
		request.getSession().removeAttribute("RuoloSession");
		return includedettaglioSoggettiDirettiInteressati(soggettoForm);
	}

	// Segnalazione EMS-114
	@RequestMapping(value = {
			"/procedimento/soggetto/visualizzaDirettiInteressati.htm" }, method = RequestMethod.POST, params = {
					"DettaglioCorrezioneErrMat" })
	public ModelAndView direttiInteressatiCorrezineErroreMaterialeDettaglio(SoggettoForm soggettoForm,
			BindingResult result) {

		addBreadcrumbs("Detaglio", request);

		if (soggettoForm.getChiave() == null) {
			super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
			return visualizzaSoggettiDirettiInteressati(soggettoForm);
		}
		request.getSession().removeAttribute("RuoloSession");
		return includeDettaglioSoggettiDirettiInteressatiCorrezioneErrMat(soggettoForm);
	}

	private boolean isInterveniente(SoggettoForm soggettoForm) {

		return soggettoForm.getIdRuolo() == Constants.ID_RUOLO_INTERVENIENTE;
	}

	@RequestMapping(value = { "/procedimento/soggetto/visualizzaDirettiInteressati.htm",
			"/procedimento/soggetto/visualizzaDirettiInteressatiTerzi.htm" }, method = RequestMethod.POST, params = {
					"BeniCollegati" })
	public ModelAndView direttiInteressatiBeniCollegati(SoggettoForm soggettoForm, BindingResult result) {

		soggettoValidation.validateGestioneSoggetto(soggettoForm, result, request);
		if (result.hasErrors()) {
			if (request.getSession().getAttribute("RuoloSession") != null
					&& request.getSession().getAttribute("RuoloSession").equals(Constants.RUOLO_TERZO)) {
				request.getSession().removeAttribute("RuoloSession");
				return visualizzaSoggettiDirettiInteressatiTerzi(soggettoForm);
			} else
				return visualizzaSoggettiDirettiInteressati(soggettoForm);
		}
		addBreadcrumbs("BeniCollegati", request);

		ProcedimentoForm procedimentoForm = (ProcedimentoForm) request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO);
		long idProcedimento = procedimentoForm.getIdProcedimento();
		int idUfficioUtente = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		long idSoggetto = soggettoForm.getIdSoggetto();
		List<BeniCollegatiTerzoDto> beniCollegati = soggettoService.beniCollegatiTerzo(idUfficioUtente,
				idProcedimento, idSoggetto);
		request.setAttribute("beniCollegati", beniCollegati);

		procedimentoService.setIdSoggettoIdVersioneTipoSoggFromChiave(soggettoForm);
		Integer idUffOwn = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		long idProc = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO)))
				.getIdProcedimento();
		soggettoForm.setIdUfficio(idUffOwn);
		soggettoForm.setIdProcedimento(idProc);

		// long idSogg = soggettoForm.getIdSoggetto();

		if ("F".equals(soggettoForm.getTipoSoggetto())) {
			soggettoForm = procedimentoService.caricaValoriDettaglioSoggettoFisicoProcedimento(soggettoForm);
			if ((soggettoForm.getPressoIndirizzoDomicilioEletto() != null)
					&& "1".equals(soggettoForm.getPressoIndirizzoDomicilioEletto())) {
				soggettoForm.setPressoIndirizzoDomicilioEletto("AVVOCATO");
			} else if ((soggettoForm.getPressoIndirizzoDomicilioEletto() != null)
					&& "2".equals(soggettoForm.getPressoIndirizzoDomicilioEletto())) {
				soggettoForm.setPressoIndirizzoDomicilioEletto("ALTRO");
			}
		} else {
			soggettoForm = procedimentoService
					.caricaValoriDettaglioSoggettoGiuridicoProcedimento(soggettoForm);
		}

		return new ModelAndView("procedimento/soggetto/beniCollegatiTerzoInteressato", "soggettoForm",
				soggettoForm);
	}

	public List<ComboForm> getNormativeMisuraPersonaleNew(@Valid SoggettoForm soggetto,
			BindingResult result) {

		if (soggetto.getMisura().getTipoMisura() != null) {
			ComboForm tipoMisura = soggetto.getMisura().getTipoMisura();
			if (Utility.isDefinito(tipoMisura)) {
				return commonService.getNormativaByTipoPrevenzioneAndTipoMisura(soggetto);
			}
		}
		return null;
	}

	public ModelAndView includedettaglioSoggettiDirettiInteressati(SoggettoForm soggettoForm) {

		procedimentoService.setIdSoggettoIdVersioneTipoSoggFromChiave(soggettoForm);

		Integer idUffOwn = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		long idProc = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO)))
				.getIdProcedimento();
		soggettoForm.setIdUfficio(idUffOwn);
		soggettoForm.setIdProcedimento(idProc);

		long idSogg;
		if (!isInterveniente(soggettoForm)) {
			idSogg = soggettoForm.getIdSoggetto();
		} else {
			idSogg = soggettoForm.getIdSoggettoInterveniente();
		}

		ModelAndView mv = null;

		if ("F".equals(soggettoForm.getTipoSoggetto())) {
			soggettoForm = procedimentoService.caricaValoriDettaglioSoggettoFisicoProcedimento(soggettoForm);
			if ((soggettoForm.getPressoIndirizzoDomicilioEletto() != null)
					&& "1".equals(soggettoForm.getPressoIndirizzoDomicilioEletto())) {
				soggettoForm.setPressoIndirizzoDomicilioEletto("AVVOCATO");
			} else if ((soggettoForm.getPressoIndirizzoDomicilioEletto() != null)
					&& "2".equals(soggettoForm.getPressoIndirizzoDomicilioEletto())) {
				soggettoForm.setPressoIndirizzoDomicilioEletto("ALTRO");
			}
			mv = new ModelAndView("procedimento/nuovo/dettaglioSoggettoFisicoProcedimento", "soggettoForm",
					soggettoForm);
		} else {
			soggettoForm = procedimentoService
					.caricaValoriDettaglioSoggettoGiuridicoProcedimento(soggettoForm);
			mv = new ModelAndView("procedimento/nuovo/dettaglioSoggettoGiuridicoProcedimento", "soggettoForm",
					soggettoForm);
		}
		// storico versioni
		List<SoggettoForm> versioniSoggetto = null;
		if (soggettoForm.getDescrRuolo() != null && "TERZO".equals(soggettoForm.getDescrRuolo())) {
			versioniSoggetto = procedimentoService.soggettiDirettiInteressatiDelProcedimentoVer(idProc,
					Constants.RUOLO_TERZO, Constants.Flag.S, idSogg, idUffOwn, null);
		} else {
			versioniSoggetto = procedimentoService.soggettiDirettiInteressatiDelProcedimentoVer(idProc,
					Constants.RUOLO_DIRETTO, Constants.Flag.S, idSogg, idUffOwn, null);
		}
		mv.addObject("versioniSoggetto", versioniSoggetto);
		//
		return mv;
	}

	// Segnalazione EMS-114
	public ModelAndView includeDettaglioSoggettiDirettiInteressatiCorrezioneErrMat(
			SoggettoForm soggettoForm) {

		Integer idUffOwner = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		soggettoForm = soggettoService.getSoggettoIntestatario(soggettoForm.getIdSoggetto(), idUffOwner);

		ModelAndView mv = null;

		if ("F".equals(soggettoForm.getTipoSoggetto())) {
			mv = new ModelAndView("procedimento/nuovo/dettaglioSoggettoFisicoProcedimento", "soggettoForm",
					soggettoForm);
		} else {
			mv = new ModelAndView("procedimento/nuovo/dettaglioSoggettoGiuridicoProcedimento", "soggettoForm",
					soggettoForm);
		}
		return mv;
	}

	@RequestMapping(value = "/procedimento/soggetto/visualizzaDirettiInteressati.htm", method = RequestMethod.POST, params = {
			"MisuraPatrimoniale" })
	public ModelAndView misuraPatrimoniale(SoggettoForm soggettoForm, BindingResult result,
			boolean validationDisabled) {

		if (!validationDisabled) {
			soggettoValidation.validateGestioneSoggetto(soggettoForm, result, request);
			if (result.hasErrors()) {
				return visualizzaSoggettiDirettiInteressati(soggettoForm);
			}

			procedimentoService.setIdSoggettoIdVersioneTipoSoggFromChiave(soggettoForm);
			if (soggettoForm.getMisura() != null) {
				soggettoValidation.validateBottoneMisuraPatrimoniale(
						soggettoForm.getMisura().getDescrizioneMisura(), null, result, request);
				if (result.hasErrors()) {
					return visualizzaSoggettiDirettiInteressati(soggettoForm);
				}
			}
		}

		String cambiopagina = request.getParameter("CambioPagina");
		if (cambiopagina == null || "".equals(cambiopagina)) {
			// Giacomo 12/07/2013 Aggiunta Breadcrumbs
			addBreadcrumbs("POST", "/procedimento/soggetto/visualizzaDirettiInteressati.htm",
					"MisuraPatrimoniale");
		}

		ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO);
		long idProcedimento = ((ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();

		if (soggettoForm.getIdProcedimentoCda() == null || soggettoForm.getIdProcedimentoCda() == 0L) {
			Integer idUfficio = recuperaIdUfficioUtente();

			soggettoForm.setIdUfficio(idUfficio);

			soggettoForm.setIdProcedimento(procedimento.getIdProcedimento());

		} else {
			AttoForm attoForm = recuperaAtto();
			attoForm.setIdProcedimentoCda(soggettoForm.getIdProcedimentoCda());
			request.getSession().setAttribute("atto", attoForm);
			idProcedimento = soggettoForm.getIdProcedimentoCda();

		}

		// soggettoForm.setIdUfficio((Integer)
		// request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE));
		if ("F".equals(soggettoForm.getTipoSoggetto())) {
			soggettoForm = procedimentoService.caricaValoriDettaglioSoggettoFisico(soggettoForm);
		} else {
			soggettoForm = procedimentoService.caricaValoriDettaglioSoggettoGiuridico(soggettoForm);
		}
		BeneSoggettoForm beneSoggettoForm = new BeneSoggettoForm();
		beneSoggettoForm.setSoggetto(soggettoForm);
		Integer idUfficioUtente = soggettoForm.getIdUfficio();

		long idAtto = 0L;
		AttoForm atto = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
		if (atto != null && atto.getCodAtto() > 0) {
			idAtto = atto.getCodAtto();
		} else {
			idAtto = beneService.getIdAttoByUfficioEProcedimento(idUfficioUtente, idProcedimento);
		}
		long idSoggetto = soggettoForm.getIdSoggetto();
		if (!soggettoForm.isTipologiaPrevenzioneNew()) {
			// Carico le informazioni da T_REL_PROC_SOGINT
			soggettoForm.setIdProcedimento(idProcedimento);
			soggettoForm = procedimentoService.caricaValoriGratuitoPatrocinioDelSoggetto(soggettoForm);
			soggettoForm.setTipologiaPrevenzioneDb(soggettoForm.getTipologiaPrevenzione());
		}
		List<ComboForm> normative = new ArrayList<ComboForm>();
		if ((soggettoForm.getTipologiaPrevenzione() != null)) {
			if (soggettoForm.getTipologiaPrevenzione().getId() != null
					&& !Constants.NO_ITEM_SELECTED.equals(soggettoForm.getTipologiaPrevenzione().getId())) {
				if (soggettoForm.getIpotesiMisura() != null) {
					// Imposto manualmente ad 1 il Tipo di Procedimento
					soggettoForm.getIpotesiMisura().setId("1");
				} else {
					// Imposto manualmente ad 1 il Tipo di Procedimento
					soggettoForm.setIpotesiMisura(new ComboForm("1", ""));
				}

				normative = commonService.getNormativaByTipoProcedAndTipologiaPrevenzione(soggettoForm);
				boolean normativaPresente = true;
				for (int i = 0; i < normative.size(); i++) {
					if (!normative.get(i).getId().contains(soggettoForm.getNormativa().getId())) {
						normativaPresente = false;
					} else {
						normativaPresente = true;
						break;
					}
				}

				if (normativaPresente == false)
					soggettoForm.getNormativa().setId("-1");
			}
		}
		List<BeneSoggettoForm> beniDellaMisuraPatrimoniale = procedimentoService
				.listaBeniConSoggettiAssociatiAllaMisuraPatrimoniale(idSoggetto, idProcedimento, idAtto,
						idUfficioUtente);
		beneSoggettoForm.setSoggettiTitolari(null);
		beneSoggettoForm.setTipologia(null);
		beneSoggettoForm.setCategoria(null);
		beneSoggettoForm.setSottocategoria(null);

		List<BeneSoggettoForm> beniNonAssociati = procedimentoService.listaBeniAttoNoMisPatr(idUfficioUtente,
				idProcedimento, idAtto, idSoggetto, beneSoggettoForm.getIdSoggettiTitolari(),
				beneSoggettoForm.getIdTipologia(), beneSoggettoForm.getIdCategoria(),
				beneSoggettoForm.getIdSottocategoria());

		ModelAndView mnv = new ModelAndView("/procedimento/soggetto/misuraPatrimoniale", "beneSoggetto",
				beneSoggettoForm);

		mapSubCheckBox(beniNonAssociati);
		mnv.addObject("risultatoRicerca", beniDellaMisuraPatrimoniale);
		mnv.addObject("risultatiRicerca", beniNonAssociati);
		mnv.addObject("normativeMisPatr", normative);
		mnv.addObject("storeCheck", cambiopagina);
		request.getSession().setAttribute("soggettoForm", soggettoForm);
		// request.getSession().setAttribute("disabledViewProc", "true");

		return mnv;
	}

	@RequestMapping(value = "/procedimento/soggetto/soggettiDirettiInteressatiDelProcedimentoAssocia.htm", method = RequestMethod.POST, params = {
			"Associa" })
	public ModelAndView misuraPatrimonialeAssocia(BeneSoggettoForm beneSoggettoForm, BindingResult result) {

		// List<BeneForm> beniSelezionati = beneSoggettoForm.estrapolaListaBeniConIdSoggettiCheckati();
		List<BeneForm> beniSelezionati = estrapolaListaBeniCheckBoxPaged(request);

		SoggettoForm soggetto = (SoggettoForm) request.getSession().getAttribute("soggettoForm");
		beneValidation.validateSceltaListaStringBeneForm(beniSelezionati, result, request);
		soggettoValidation.validaSceltaTipoPrevenzioneNormativa(beneSoggettoForm.getSoggetto(), result);

		/*
		 * if(beneSoggettoForm.getSoggetto()!= null && beneSoggettoForm.getSoggetto().getNormativa()!= null &&
		 * beneSoggettoForm.getSoggetto().getNormativa().getId().equals("-1")){
		 * 
		 * result.reject("errore.NormativaObbligatoria"); }
		 */
		if (result.hasErrors()) {
			return misuraPatrimoniale(soggetto, result, true);
		}
		Integer idUfficioUtente = recuperaIdUfficioUtente();
		long idProcedimento = recuperaIdProcedimento();
		String codUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);
		long idAtto = beneService.getIdAttoByUfficioEProcedimento(idUfficioUtente, idProcedimento);
		long idSoggettoInteressato = soggetto.getIdSoggetto();
		long idTipoPrev = Long.parseLong(beneSoggettoForm.getSoggetto().getTipologiaPrevenzione().getId());
		long idTipoNorm = Long.parseLong(beneSoggettoForm.getSoggetto().getNormativa().getId());
		procedimentoService.associaSoggettiTitolariPerBeneMisuraPatrimoniale(beniSelezionati, idProcedimento,
				idUfficioUtente, idAtto, idSoggettoInteressato, codUtente);
		procedimentoService.aggiornaTipoPrevenzioneNormativaSoggetto(idProcedimento, idUfficioUtente,
				idSoggettoInteressato, idTipoPrev, idTipoNorm);
		// if(soggetto.getTipologiaPrevenzioneDb()!=null && soggetto.getTipologiaPrevenzioneDb().getId() !=
		// null){
		// if(!soggetto.getTipologiaPrevenzioneDb().getId().equals(soggetto.getTipologiaPrevenzione().getId())){
		procedimentoService.aggiornaFlgTipoPrevenzioneProcedimento(idProcedimento, idUfficioUtente);
		// }
		// }
		super.updateProcedimentoIntoSession(idProcedimento);
		return misuraPatrimoniale(soggetto, result, false);
	}

	@RequestMapping(value = "/procedimento/soggetto/visualizzaDirettiInteressatiAtto.htm", method = RequestMethod.POST, params = {
			"MisuraPatrimonialeAtto" })
	public ModelAndView misuraPatrimonialeAtto(SoggettoForm soggettoForm, BindingResult result) {

		soggettoValidation.validateGestioneSoggetto(soggettoForm, result, request);
		if (result.hasErrors()) {
			return visualizzaSoggettiDirettiInteressatiAtto(soggettoForm);
		}
		soggettoValidation.validateBottoneMisuraPatrimoniale(soggettoForm.getMisura().getDescrizioneMisura(),
				null, result, request);
		if (result.hasErrors()) {
			return visualizzaSoggettiDirettiInteressatiAtto(soggettoForm);
		}
		return includeElencoMisuraPatrimonialeAtto(soggettoForm, result);
	}

	@RequestMapping(value = "/procedimento/soggetto/soggettiDirettiInteressatiAttoDelProcedimentoAssocia.htm", method = RequestMethod.POST, params = {
			"Associa" })
	public ModelAndView misuraPatrimonialeAttoAssocia(BeneSoggettoForm beneSoggettoForm,
			BindingResult result) {

		List<BeneForm> beniSelezionati = beneSoggettoForm.estrapolaListaBeniConIdSoggettiCheckati();
		SoggettoForm soggetto = (SoggettoForm) request.getSession().getAttribute("soggettoForm");
		beneValidation.validateSceltaListaStringBeneForm(beniSelezionati, result, request);
		soggettoValidation.validaSceltaTipoPrevenzioneNormativa(beneSoggettoForm.getSoggetto(), result);
		if (result.hasErrors()) {
			return includeElencoMisuraPatrimonialeAtto(soggetto, result);
		}
		Integer idUfficioUtente = recuperaIdUfficioUtente();
		long idProcedimento = ((ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		String codUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);
		long idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
		long idSoggettoInteressato = soggetto.getIdSoggetto();
		long idTipoPrev = Long.parseLong(beneSoggettoForm.getSoggetto().getTipologiaPrevenzione().getId());
		long idTipoNorm = Long.parseLong(beneSoggettoForm.getSoggetto().getNormativa().getId());
		procedimentoService.associaSoggettiTitolariPerBeneMisuraPatrimoniale(beniSelezionati, idProcedimento,
				idUfficioUtente, idAtto, idSoggettoInteressato, codUtente);
		procedimentoService.aggiornaTipoPrevenzioneNormativaSoggetto(idProcedimento, idUfficioUtente,
				idSoggettoInteressato, idTipoPrev, idTipoNorm);
		if (soggetto.getTipologiaPrevenzioneDb() != null) {
			if (!soggetto.getTipologiaPrevenzioneDb().getId()
					.equals(soggetto.getTipologiaPrevenzione().getId())) {
				procedimentoService.aggiornaFlgTipoPrevenzioneProcedimento(idProcedimento, idUfficioUtente);
			}
		}
		super.updateProcedimentoIntoSession(idProcedimento);
		MenuController.caricaMenu("15", request);
		return includeElencoMisuraPatrimonialeAtto(soggetto, result);
	}

	@RequestMapping(value = "/procedimento/soggetto/soggettiDirettiInteressatiAttoDelProcedimentoElimina.htm", method = RequestMethod.POST, params = {
			"Elimina" })
	public ModelAndView misuraPatrimonialeAttoElimina(BeneSoggettoForm beneSoggettoForm,
			BindingResult result) {

		List<Long> listaIdCheckati = beneSoggettoForm.getListaIdCheckati();
		SoggettoForm soggetto = (SoggettoForm) request.getSession().getAttribute("soggettoForm");
		beneValidation.validateSceltaListaBeni(listaIdCheckati, result, request);
		if (result.hasErrors()) {
			return includeElencoMisuraPatrimonialeAtto(soggetto, result);
		}
		Integer idUfficioUtente = recuperaIdUfficioUtente();
		long idProcedimento = ((ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		String codUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);
		long idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
		long idSoggettoInteressato = soggetto.getIdSoggetto();
		procedimentoService.eliminaRelazioniAttoMisuraPatrimoniale(listaIdCheckati, idProcedimento,
				idUfficioUtente, idAtto, idSoggettoInteressato, codUtente);

		super.updateProcedimentoIntoSession(idProcedimento);
		MenuController.caricaMenu("15", request);
		return includeElencoMisuraPatrimonialeAtto(soggetto, result);
	}

	@RequestMapping(value = "/procedimento/soggetto/soggettiDirettiInteressatiAttoDelProcedimentoAssocia.htm", method = RequestMethod.POST)
	public ModelAndView misuraPatrimonialeAttoTipoPrevNormativa(@Valid BeneSoggettoForm beneSoggettoForm,
			BindingResult result) {

		beneSoggettoForm.getSoggetto().setTipologiaPrevenzioneNew(true);
		return includeElencoMisuraPatrimonialeAtto(beneSoggettoForm.getSoggetto(), result);
	}

	@RequestMapping(value = "/procedimento/soggetto/soggettiDirettiInteressatiDelProcedimentoElimina.htm", method = RequestMethod.POST, params = {
			"Elimina" })
	public ModelAndView misuraPatrimonialeElimina(BeneSoggettoForm beneSoggettoForm, BindingResult result) {

		// beneSoggettoForm.setListaIdCheckati(getCheckBoxPaged(request, "_chk"));
		List<Long> listaIdCheckati = beneSoggettoForm.getListaIdCheckati();
		SoggettoForm soggetto = (SoggettoForm) request.getSession().getAttribute("soggettoForm");
		beneValidation.validateSceltaListaBeni(listaIdCheckati, result, request);
		if (result.hasErrors()) {
			return misuraPatrimoniale(soggetto, result, true);
		}
		Integer idUfficioUtente = recuperaIdUfficioUtente();
		long idProcedimento = ((ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		String codUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);
		long idAtto = beneService.getIdAttoByUfficioEProcedimento(idUfficioUtente, idProcedimento);
		long idSoggettoInteressato = soggetto.getIdSoggetto();
		procedimentoService.eliminaRelazioniAttoMisuraPatrimoniale(listaIdCheckati, idProcedimento,
				idUfficioUtente, idAtto, idSoggettoInteressato, codUtente);
		procedimentoService.aggiornaTipoPrevenzioneNormativaSoggetto(idProcedimento, idUfficioUtente,
				idSoggettoInteressato, 0L, 0L);
		super.updateProcedimentoIntoSession(idProcedimento);
		return misuraPatrimoniale(soggetto, result, false);
	}

	// @RequestMapping(value = "/procedimento/soggetto/soggettiDirettiInteressatiDelProcedimentoAssocia.htm",
	// method = RequestMethod.POST, params = { "INDIETRO_PROPOSTA" })
	// public ModelAndView misuraPatrimonialeIndietroProposta(@Valid AttoForm attoForm, BindingResult result,
	// ModelMap modelMap, HttpServletRequest request) {
	//
	// AttoForm vecchiDati = (AttoForm) super.getIndietroForm();
	// return attoController.proposta(vecchiDati, result, request, modelMap);
	// }

	@RequestMapping(value = { "/procedimento/soggetto/soggettiDirettiInteressatiDelProcedimentoElimina.htm",
			"/procedimento/soggetto/soggettiDirettiInteressatiDelProcedimentoAssocia.htm" }, method = RequestMethod.GET)
	public ModelAndView misuraPatrimonialeOrdinaAssociati(BeneSoggettoForm form, BindingResult result) {

		SoggettoForm soggetto = (SoggettoForm) request.getSession().getAttribute("soggettoForm");
		return misuraPatrimoniale(soggetto, result, true);
	}

	@RequestMapping(value = { "/procedimento/soggetto/soggettiDirettiInteressatiDelProcedimentoElimina.htm",
			"/procedimento/soggetto/soggettiDirettiInteressatiDelProcedimentoAssocia.htm" }, method = RequestMethod.POST, params = {
					"CambioPagina" })
	public ModelAndView misuraPatrimonialeCambioPagina(BeneSoggettoForm beneSoggettoForm,
			BindingResult result, ModelMap modelMap) {

		// cambio pagina tabella "Beni riconducibili al soggetto"
		// Set<String> listaIdCheckati = getCheckBoxPaged(request, "listaIdCheckati");
		// cambio pagina tabella "Beni del procedimento"
		// Set<String> procList = getCheckBoxPaged(request, "proc_chk");
		// Set<String> subprocList = getCheckBoxPaged(request, "subproc_chk");
		SoggettoForm soggetto = (SoggettoForm) request.getSession().getAttribute("soggettoForm");
		return misuraPatrimoniale(soggetto, result, true);
	}

	@RequestMapping(value = "/procedimento/soggetto/soggettiDirettiInteressatiDelProcedimentoAssocia.htm", method = RequestMethod.POST)
	public ModelAndView postMisuraPatrimonialeTipoPrevenzione(BeneSoggettoForm beneSoggettoForm,
			BindingResult result, ModelMap modelMap) {

		// SoggettoForm soggetto = (SoggettoForm) request.getSession().getAttribute("soggettoForm");
		// soggetto.setTipologiaPrevenzione(beneSoggettoForm.getSoggetto().getTipologiaPrevenzione());
		SoggettoForm soggetto = (SoggettoForm) request.getSession().getAttribute("soggettoForm");
		ComboForm tipologiaPrevenzione = beneSoggettoForm.getSoggetto().getTipologiaPrevenzione();
		ComboForm normativa = beneSoggettoForm.getSoggetto().getNormativa();
		soggetto.setTipologiaPrevenzioneNew(true);

		List<ComboForm> normative = new ArrayList<ComboForm>();
		if ((tipologiaPrevenzione != null)) {
			if (tipologiaPrevenzione.getId() != null
					&& !Constants.NO_ITEM_SELECTED.equals(tipologiaPrevenzione.getId())) {
				soggetto.setTipologiaPrevenzione(tipologiaPrevenzione);
				// if (soggetto.getNormativa().getId() == null ) {
				// caricaNormative(soggetto, mnv, null);
				// }
				ComboForm ipotesiMisura = soggetto.getIpotesiMisura();
				if (ipotesiMisura != null) {
					// Imposto manualmente ad 1 il Tipo di Procedimento
					soggetto.getIpotesiMisura().setId("1");
				} else {
					// Imposto manualmente ad 1 il Tipo di Procedimento
					soggetto.setIpotesiMisura(new ComboForm("1", ""));
				}
				normative = commonService.getNormativaByTipoProcedAndTipologiaPrevenzione(soggetto);
				// for(int i=0; i<normative.size(); i++){
				// if (!normative.get(i).getId().contains(soggetto.getNormativa().getId()))
				// {
				// normativaPresente=false;
				// }
				// else
				// {
				// normativaPresente=true;
				// break;
				// }
				// }
				// if(normativaPresente==false){
				// soggetto.getNormativa().setId("-1");
				// }
			}
		}
		soggetto.setNormativa(normativa);
		request.getSession().setAttribute("normativeMisPatr", normative);
		beneSoggettoForm.setSoggetto(soggetto);
		return postMisuraPatrimoniale(beneSoggettoForm, result, modelMap);
	}

	@RequestMapping(value = {
			"/procedimento/soggetto/soggettiDirettiInteressatiAttoDelProcedimentoElimina.htm",
			"/procedimento/soggetto/soggettiDirettiInteressatiAttoDelProcedimentoAssocia.htm" }, method = RequestMethod.GET)
	public ModelAndView misuraPatrimonialeOrdinaAssociatiAtti(BeneSoggettoForm form, BindingResult result) {

		SoggettoForm soggettoForm = (SoggettoForm) request.getSession().getAttribute("soggettoForm");
		return includeElencoMisuraPatrimonialeAtto(soggettoForm, result);
	}

	@RequestMapping(value = "/procedimento/soggetto/soggettiDirettiInteressatiDelProcedimentoFiltro.htm", method = RequestMethod.POST)
	public ModelAndView postMisuraPatrimoniale(@Valid BeneSoggettoForm beneSoggettoForm, BindingResult result,
			ModelMap modelMap) {

		SoggettoForm soggetto = (SoggettoForm) request.getSession().getAttribute("soggettoForm");
		// ComboForm tipologiaPrevenzione = soggetto.getTipologiaPrevenzione();
		// ComboForm normativa = soggetto.getNormativa();
		beneSoggettoForm.setSoggetto(soggetto);
		// soggetto.setTipologiaPrevenzione(tipologiaPrevenzione);
		// soggetto.setNormativa(normativa);

		long idProcedimento = ((ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
		long idAtto = 0L;
		AttoForm atto = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
		if (atto != null && atto.getCodAtto() > 0) {
			idAtto = atto.getCodAtto();
		} else {
			idAtto = beneService.getIdAttoByUfficioEProcedimento(idUfficioUtente, idProcedimento);
		}
		long idSoggetto = soggetto.getIdSoggetto();
		List<BeneSoggettoForm> beniDellaMisuraPatrimoniale = procedimentoService
				.listaBeniConSoggettiAssociatiAllaMisuraPatrimoniale(idSoggetto, idProcedimento, idAtto,
						idUfficioUtente);
		List<SoggettoForm> soggettiTitolari = null;
		if (!beneSoggettoForm.isVisualizzaFiltro()) {
			beneSoggettoForm.setSoggettiTitolari(null);
			beneSoggettoForm.setTipologia(null);
			beneSoggettoForm.setCategoria(null);
			beneSoggettoForm.setSottocategoria(null);
		}

		List<BeneSoggettoForm> beniNonAssociati = procedimentoService.listaBeniAttoNoMisPatr(idUfficioUtente,
				idProcedimento, idAtto, idSoggetto, beneSoggettoForm.getIdSoggettiTitolari(),
				beneSoggettoForm.getIdTipologia(), beneSoggettoForm.getIdCategoria(),
				beneSoggettoForm.getIdSottocategoria());

		ModelAndView mnv = new ModelAndView("/procedimento/soggetto/misuraPatrimoniale", "beneSoggetto",
				beneSoggettoForm);

		mapSubCheckBox(beniNonAssociati);
		mnv.addObject("risultatoRicerca", beniDellaMisuraPatrimoniale);
		mnv.addObject("risultatiRicerca", beniNonAssociati);
		// mnv.addObject("normativeMisPatr", request.getSession().getAttribute("normativeMisPatr"));
		modelMap.addAttribute("categorie", getCategorie(beneSoggettoForm, result));
		modelMap.addAttribute("sottocategorie", getSottocategorie(beneSoggettoForm, result));
		if (beneSoggettoForm.isVisualizzaFiltro()) {
			// carica combo soggetti titolari se filtro = true
			soggettiTitolari = procedimentoService.soggettiTitolariDelProcedimentoCombo(idProcedimento);
			if (soggettiTitolari != null) {
				mnv.addObject("soggettiTitolari", utility.creaComboSoggetti(soggettiTitolari));
			}
		}
		request.getSession().setAttribute("soggettoForm", soggetto);

		return mnv;

	}

	@RequestMapping(value = "/procedimento/soggetto/soggettiTitolariInteressatiDelProcedimentoFiltro.htm", method = RequestMethod.POST)
	public ModelAndView postMisuraPatrimonialeAtto(@Valid BeneSoggettoForm beneSoggettoForm,
			BindingResult result, ModelMap modelMap) {

		SoggettoForm soggetto = (SoggettoForm) request.getSession().getAttribute("soggettoForm");
		beneSoggettoForm.setSoggetto(soggetto);

		long idProcedimento = ((ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
		long idAtto = 0L;
		AttoForm atto = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
		if (atto != null && atto.getCodAtto() > 0) {
			idAtto = atto.getCodAtto();
		} else {
			idAtto = beneService.getIdAttoByUfficioEProcedimento(idUfficioUtente, idProcedimento);
		}
		long idSoggetto = soggetto.getIdSoggetto();
		List<BeneSoggettoForm> beniDellaMisuraPatrimoniale = procedimentoService
				.listaBeniConSoggettiAssociatiAllaMisuraPatrimonialeAtto(idSoggetto, idProcedimento, idAtto,
						idUfficioUtente);
		List<SoggettoForm> soggettiTitolari = null;
		if (!beneSoggettoForm.isVisualizzaFiltro()) {
			beneSoggettoForm.setSoggettiTitolari(null);
			beneSoggettoForm.setTipologia(null);
			beneSoggettoForm.setCategoria(null);
			beneSoggettoForm.setSottocategoria(null);
		}

		List<BeneSoggettoForm> beniNonAssociati = procedimentoService.listaBeniAttoNoMisPatr(idUfficioUtente,
				idProcedimento, idAtto, idSoggetto, beneSoggettoForm.getIdSoggettiTitolari(),
				beneSoggettoForm.getIdTipologia(), beneSoggettoForm.getIdCategoria(),
				beneSoggettoForm.getIdSottocategoria());

		ModelAndView mnv = new ModelAndView("/procedimento/soggetto/misuraPatrimonialeAtto", "beneSoggetto",
				beneSoggettoForm);

		mnv.addObject("risultatoRicerca", beniDellaMisuraPatrimoniale);
		mnv.addObject("risultatiRicerca", beniNonAssociati);
		modelMap.addAttribute("categorie", getCategorie(beneSoggettoForm, result));
		modelMap.addAttribute("sottocategorie", getSottocategorie(beneSoggettoForm, result));
		if (beneSoggettoForm.isVisualizzaFiltro()) {
			// carica combo soggetti titolari se filtro = true
			soggettiTitolari = procedimentoService.soggettiTitolariDelProcedimentoAttoCombo(idProcedimento,
					idAtto);
			if (soggettiTitolari != null) {
				List<ComboForm> listaCbo = new ArrayList<ComboForm>();
				for (SoggettoForm soggettoForm : soggettiTitolari) {
					if ("F".equalsIgnoreCase(soggettoForm.getTipoSoggetto())) {
						listaCbo.add(new ComboForm(String.valueOf(soggettoForm.getIdSoggetto()),
								String.valueOf(soggettoForm.getCognome() + " " + soggettoForm.getNome())));
					} else { // la ragione sociale è appoggiata sul cognome
						listaCbo.add(new ComboForm(String.valueOf(soggettoForm.getIdSoggetto()),
								String.valueOf(soggettoForm.getCognome())));
					}
				}
				mnv.addObject("soggettiTitolari", listaCbo);
			}
		}
		request.getSession().setAttribute("soggettoForm", soggetto);

		return mnv;
	}

	@ModelAttribute("categorie")
	public List<ComboForm> getCategorie(@Valid BeneSoggettoForm beneSoggettoForm, BindingResult result) {

		if ((beneSoggettoForm == null) || (beneSoggettoForm.getTipologia() == null)
				|| (beneSoggettoForm.getTipologia().getId() == null)) {
			return null;
		}
		BeneForm bene = new BeneForm();
		ComboForm tipologia = beneSoggettoForm.getTipologia();
		bene.setTipologia(tipologia);
		return commonService.listaCategorieDelBene(bene);
	}

	@ModelAttribute("sottocategorie")
	public List<ComboForm> getSottocategorie(@Valid BeneSoggettoForm beneSoggettoForm, BindingResult result) {

		if ((beneSoggettoForm == null) || (beneSoggettoForm.getCategoria() == null)
				|| (beneSoggettoForm.getCategoria().getId() == null)) {
			return null;
		}
		BeneForm bene = new BeneForm();
		ComboForm categoria = beneSoggettoForm.getCategoria();
		bene.setCategoria(categoria);
		return commonService.listaSottocategorieDelBene(bene);
	}

	@RequestMapping(value = "/procedimento/soggetto/visualizzaDirettiInteressati.htm", method = RequestMethod.POST, params = {
			"MisuraPersonale" })
	public ModelAndView misuraPersonale(SoggettoForm soggettoForm, BindingResult result) {

		soggettoValidation.validateGestioneSoggetto(soggettoForm, result, request);
		if (result.hasErrors()) {
			return visualizzaSoggettiDirettiInteressati(soggettoForm);
		}
		procedimentoService.setIdSoggettoIdVersioneTipoSoggFromChiave(soggettoForm);
		soggettoValidation.validateBottoneMisuraPersonale(soggettoForm.getTipoSoggetto(),
				soggettoForm.getMisura().getDescrizioneMisura(), null, result, request);
		if (result.hasErrors()) {
			return visualizzaSoggettiDirettiInteressati(soggettoForm);
		}

		// Giacomo 15/07/2013 Aggiunta Breadcrumbs
		addBreadcrumbs("MisuraPersonale", request);
		ProcedimentoForm procedimento = recuperaProcedimento();

		if (soggettoForm.getIdProcedimentoCda() == null || soggettoForm.getIdProcedimentoCda() == 0L) {
			Integer idUfficio = recuperaIdUfficioUtente();
			soggettoForm.setIdUfficio(idUfficio);
			soggettoForm.setIdProcedimento(procedimento.getIdProcedimento());
		} else {
			AttoForm attoForm = recuperaAtto();
			attoForm.setIdProcedimentoCda(soggettoForm.getIdProcedimentoCda());
			request.getSession().setAttribute("atto", attoForm);
		}

		soggettoForm.setIdAtto(0L);
		soggettoForm = misuraService.caricaMisuraPersonale(soggettoForm);
		if (Constants.SOGGETTO_FISICO.equals(soggettoForm.getTipoSoggetto())) {
			soggettoForm = procedimentoService.caricaValoriDettaglioSoggettoFisicoProcedimento(soggettoForm);
			soggettoForm.setTipoSoggetto(Constants.SOGGETTO_FISICO);
		} else {
			soggettoForm = procedimentoService
					.caricaValoriDettaglioSoggettoGiuridicoProcedimento(soggettoForm);
			soggettoForm.setTipoSoggetto(Constants.SOGGETTO_GIURIDICO);
		}
		// Se valorizzato provengo dalla proposta
		if (soggettoForm.getSchermataDiProvenienza() == null) {
			// Chiamata al metodo diretta dall'elenco dei Soggetti Interessati
			soggettoForm.setSchermataDiProvenienza("soggetti");
		}
		// Carico le informazioni da T_REL_PROC_SOGINT
		soggettoForm = procedimentoService.caricaValoriGratuitoPatrocinioDelSoggetto(soggettoForm);
		soggettoForm.setTipologiaPrevenzioneDb(soggettoForm.getTipologiaPrevenzione());

		ModelAndView mnv = null;
		if (procedimento.getTipoProcedimento() != null && (Constants.TIPOPROC_AGGRAVAMENTO_PERSONALE
				.equals(procedimento.getTipoProcedimento().getId())
				|| Long.toString(Constants.TIPOPROC_CAUZIONI_CAM)
						.equals(procedimento.getTipoProcedimento().getId()))) {
			mnv = new ModelAndView("procedimento/soggetto/misuraPersonaleReadOnly", "soggettoForm",
					soggettoForm);
			if (soggettoForm != null && soggettoForm.getMisura() != null
					&& ("S").equalsIgnoreCase(soggettoForm.getMisura().getCauzione())) {
				mnv.addObject("importoCauzione", true);
			}
			mnv.addObject("indietroSogInt", true);
		} else
			mnv = new ModelAndView("procedimento/soggetto/misuraPersonale", "soggettoForm", soggettoForm);
		// mnv.addObject("provenienza", soggettoForm.getSchermataDiProvenienza().toLowerCase());

		// Carico nel ModelAndView le Normative
		caricaNormative(soggettoForm, mnv, null);
		List<ComboForm> list = commonService.listaPrescrizioniMisuraPersonale();
		mnv.addObject("listaPrescrizioni", list);
		if (soggettoForm.getMisura().getVecchiePrescrizioni() != null) {
			mnv.addObject("listaVecchiePrescrizioni", soggettoForm.getMisura().getVecchiePrescrizioni());
		}

		return mnv;
	}

	public ModelAndView misuraPersonaleDettaglio(SoggettoForm soggettoForm, BindingResult result) {

		return misuraPersonaleDettaglio(soggettoForm, result, null);
	}

	public ModelAndView misuraPersonaleDettaglio(SoggettoForm soggettoForm, BindingResult result,
			Long idProcedimento) {

		soggettoValidation.validateGestioneSoggetto(soggettoForm, result, request);
		if (result.hasErrors()) {
			return visualizzaSoggettiDirettiInteressati(soggettoForm);
		}
		procedimentoService.setIdSoggettoIdVersioneTipoSoggFromChiave(soggettoForm);
		soggettoValidation.validateBottoneMisuraPersonale(soggettoForm.getTipoSoggetto(),
				soggettoForm.getMisura().getDescrizioneMisura(), null, result, request);
		if (result.hasErrors()) {
			return visualizzaSoggettiDirettiInteressati(soggettoForm);
		}
		soggettoForm.setIdUfficio((Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE));
		if (idProcedimento == null) {
			soggettoForm.setIdProcedimento(
					((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO)))
							.getIdProcedimento());
		} else {
			soggettoForm.setIdProcedimento(idProcedimento);
		}
		soggettoForm.setIdAtto(0L);
		soggettoForm = misuraService.caricaMisuraPersonale(soggettoForm);
		if ("F".equals(soggettoForm.getTipoSoggetto())) {
			soggettoForm = procedimentoService.caricaValoriDettaglioSoggettoFisicoProcedimento(soggettoForm);
			soggettoForm.setTipoSoggetto(Constants.SOGGETTO_FISICO);
		} else {
			soggettoForm = procedimentoService
					.caricaValoriDettaglioSoggettoGiuridicoProcedimento(soggettoForm);
			soggettoForm.setTipoSoggetto(Constants.SOGGETTO_GIURIDICO);
		}
		ModelAndView mnv = new ModelAndView("procedimento/soggetto/misuraPersonaleDettaglio", "soggettoForm",
				soggettoForm);
		List<ComboForm> list = commonService.listaPrescrizioniMisuraPersonale();
		mnv.addObject("listaPrescrizioni", list);
		if (soggettoForm.getMisura().getVecchiePrescrizioni() != null) {
			mnv.addObject("listaVecchiePrescrizioni", soggettoForm.getMisura().getVecchiePrescrizioni());
		}
		// Carico nel ModelAndView le Normative
		caricaNormative(soggettoForm, mnv, null);
		return mnv;
	}

	@RequestMapping(value = "/procedimento/soggetto/misuraPersonale.htm", method = RequestMethod.POST, params = {
			"ELIMINA" })
	public ModelAndView misuraPersonaleElimina(@Valid SoggettoForm soggettoForm, BindingResult result,
			ModelMap modelMap) {

		Long idSoggetto = soggettoForm.getIdSoggetto();
		soggettoValidation.validateAbilitazioneEliminazioneMisuraPersonale(result);
		if (result.hasErrors()) {
			soggettoForm
					.setIdUfficio((Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE));
			if ("F".equals(soggettoForm.getTipoSoggetto())) {
				soggettoForm = procedimentoService.caricaValoriDettaglioSoggettoFisico(soggettoForm);
				soggettoForm.setTipoSoggetto(Constants.SOGGETTO_FISICO);
			} else {
				soggettoForm = procedimentoService.caricaValoriDettaglioSoggettoGiuridico(soggettoForm);
				soggettoForm.setTipoSoggetto(Constants.SOGGETTO_GIURIDICO);
			}
			ModelAndView mnv = new ModelAndView("procedimento/soggetto/misuraPersonale", "soggettoForm",
					soggettoForm);
			List<ComboForm> list = commonService.listaPrescrizioniMisuraPersonale();
			mnv.addObject("listaPrescrizioni", list);
			return mnv;
		}

		long vidProc = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO)))
				.getIdProcedimento();
		Integer vidUffOwn = recuperaIdUfficioUtente();
		long idSoggInt = soggettoForm.getIdSoggetto();
		// long idTipoPrev = Long.parseLong(soggettoForm.getTipologiaPrevenzione().getId());
		// long idTipoNorm = Long.parseLong(soggettoForm.getNormativa().getId());
		procedimentoService.aggiornaTipoPrevenzioneNormativaSoggetto(vidProc, vidUffOwn, idSoggInt, 0L, 0L);
		misuraService.eliminaMisuraPersonaleDelSoggetto(idSoggetto);

		// procedimentoService.aggiornaTipoPrevenzioneNormativaSoggetto(idProcedimento, idUfficioUtente,
		// idSoggettoInteressato, 0L, 0L);
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation", "cancellazione", false);
		return visualizzaSoggettiDirettiInteressati(soggettoForm);
	}

	@RequestMapping(value = "/procedimento/soggetto/gestioneDifensori.htm", method = RequestMethod.POST, params = {
			"ModificaDifensore" })
	public ModelAndView ModificaDifensoreAssociato(DifensoreSoggettoForm difensoreSoggettoForm,
			BindingResult result) {

		soggettoValidation.validateTabellaDifensoriAssociati(difensoreSoggettoForm, result, request);
		caricaValoriDettaglioSoggetto(difensoreSoggettoForm.getSoggettoForm());
		if (result.hasErrors()) {
			return difensoriAssociatiJsp(difensoreSoggettoForm, result);
		}

		// Giacomo 22/07/2013
		addBreadcrumbs("ModificaDifensore", request);
		ProcedimentoForm procedimentoForm = (ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO));
		if (Long.toString(Constants.TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM)
				.equals(procedimentoForm.getTipoProcedimento().getId())
				|| Long.toString(Constants.TIPOPROC_CORREZIONE_ERRORI_CAM)
						.equals(procedimentoForm.getTipoProcedimento().getId()))
			difensoreSoggettoForm = procedimentoService
					.caricaDifensoreAssociatoIntestatario(difensoreSoggettoForm);
		else
			difensoreSoggettoForm = procedimentoService
					.caricaDifensoreAssociatoSoggetto(difensoreSoggettoForm);
		return new ModelAndView("procedimento/soggetto/difensoriAssociatiModifica", "difensoreSoggettoForm",
				difensoreSoggettoForm);
	}

	@RequestMapping(value = "/procedimento/soggetto/gestioneDifensori.htm", method = RequestMethod.POST, params = {
			"DettaglioDifensore" })
	public ModelAndView DettaglioDifensoreAssociato(DifensoreSoggettoForm difensoreSoggettoForm,
			BindingResult result) {

		soggettoValidation.validateTabellaDifensoriAssociati(difensoreSoggettoForm, result, request);
		caricaValoriDettaglioSoggetto(difensoreSoggettoForm.getSoggettoForm());
		if (result.hasErrors()) {
			return difensoriAssociatiJsp(difensoreSoggettoForm, result);
		}

		addBreadcrumbs("POST", "/procedimento/soggetto/gestioneDifensori.htm", "DettaglioDifensore");
		ProcedimentoForm procedimentoForm = (ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO));
		if (Long.toString(Constants.TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM)
				.equals(procedimentoForm.getTipoProcedimento().getId())
				|| (Long.toString(Constants.TIPOPROC_CORREZIONE_ERRORI_CAM)
						.equals(procedimentoForm.getTipoProcedimento().getId())))
			difensoreSoggettoForm = procedimentoService
					.caricaDifensoreAssociatoIntestatario(difensoreSoggettoForm);
		else
			difensoreSoggettoForm = procedimentoService
					.caricaDifensoreAssociatoSoggetto(difensoreSoggettoForm);
		difensoreSoggettoForm.setTipoNomina(new ComboForm("", difensoreSoggettoForm.getDescTipoNomina()));

		return new ModelAndView("procedimento/soggetto/difensoriAssociatiDettaglio", "difensoreSoggettoForm",
				difensoreSoggettoForm);
	}

	@RequestMapping(value = "/procedimento/soggetto/modificaSoggettoFisico.htm", method = RequestMethod.POST, params = {
			"ModificaErede" })
	public ModelAndView modificaErede(@Valid SoggettoForm soggettoForm, BindingResult result) {

		ModelAndView mnv = new ModelAndView("procedimento/soggetto/modificaSoggettoFisico", "soggettoForm",
				soggettoForm);
		soggettoValidation.validateTabellaErediAssociati(soggettoForm, result, request);
		if (!result.hasErrors()) {
			soggettoForm.setEredeConviventeForm(procedimentoService.caricaEredeDelDefunto(soggettoForm));
			if (soggettoForm.getEredeConviventeForm().getComuneNascitaItaliano() != null) {
				soggettoForm.getEredeConviventeForm().getComuneNascitaErCon()
						.setDescrizione(soggettoForm.getEredeConviventeForm().getComuneNascitaItaliano());
			} else if (soggettoForm.getEredeConviventeForm().getComuneNascitaEstero() != null) {
				soggettoForm.getEredeConviventeForm().getComuneNascitaErCon()
						.setDescrizione(soggettoForm.getEredeConviventeForm().getComuneNascitaEstero());
			}
			soggettoForm.setGestioneInserimentoModificaErede(true);
		}
		if (soggettoForm.getGestioneEredi() == true) {
			List<EredeConviventeForm> listaEredi = procedimentoService.listaErediDelDefunto(soggettoForm);
			mnv.addObject("erediAssociati", listaEredi);
		}
		return mnv;
	}

	@RequestMapping(value = { "/procedimento/soggetto/visualizzaDirettiInteressati.htm",
			"/procedimento/soggetto/visualizzaDirettiInteressatiTerzi.htm" }, method = RequestMethod.POST, params = {
					"Modifica" })
	public ModelAndView modificaSoggettiDirettiInteressati(SoggettoForm soggettoForm, BindingResult result) {

		soggettoValidation.validateGestioneSoggetto(soggettoForm, result, request);
		if (result.hasErrors()) {
			if (request.getSession().getAttribute("RuoloSession") != null
					&& Constants.RUOLO_TERZO.equals(request.getSession().getAttribute("RuoloSession"))) {
				request.getSession().removeAttribute("RuoloSession");
				return visualizzaSoggettiDirettiInteressatiTerzi(soggettoForm);
			} else
				return visualizzaSoggettiDirettiInteressati(soggettoForm);
		}

		// Giacomo 12/07/2013 Aggiunta Breadcrumbs
		addBreadcrumbs("Modifica", request);

		procedimentoService.setIdSoggettoIdVersioneTipoSoggFromChiave(soggettoForm);
		soggettoForm.setIdUfficio((Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE));
		soggettoForm.setIdProcedimento(
				((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO)))
						.getIdProcedimento());
		if ("F".equals(soggettoForm.getTipoSoggetto())) {
			soggettoForm = procedimentoService.caricaValoriDettaglioSoggettoFisicoProcedimento(soggettoForm);
			// per il modifica nn posso cambiare il mapping e sono costretta a
			// farlo qui.
			if (soggettoForm.getDescrizioneComuneEsteroNascita() != null) {
				soggettoForm.setComuneNascita(new ComboForm());
				soggettoForm.getComuneNascita()
						.setDescrizione(soggettoForm.getDescrizioneComuneEsteroNascita());
			}
			if (soggettoForm.getDescComuneEsteroResidenza() != null) {
				soggettoForm.setComuneResidenza(new ComboForm());
				soggettoForm.getComuneResidenza().setDescrizione(soggettoForm.getDescComuneEsteroResidenza());
			}
			if (soggettoForm.getDescComuneEsteroDomicilio() != null) {
				soggettoForm.setComuneDomicilio(new ComboForm());
				soggettoForm.getComuneDomicilio().setDescrizione(soggettoForm.getDescComuneEsteroDomicilio());
			}
			if (soggettoForm.getDescComuneEsteroDimora() != null) {
				soggettoForm.setComuneDimora(new ComboForm());
				soggettoForm.getComuneDimora().setDescrizione(soggettoForm.getDescComuneEsteroDimora());
			}
			if (soggettoForm.getDescComuneEsteroDomicilioEletto() != null) {
				soggettoForm.setComuneDomicilioEletto(new ComboForm());
				soggettoForm.getComuneDomicilioEletto()
						.setDescrizione(soggettoForm.getDescComuneEsteroDomicilioEletto());
			}
			if (soggettoForm.getDescrizioneComuneEsteroMorte() != null) {
				soggettoForm.setComuneMorte(new ComboForm());
				soggettoForm.getComuneMorte().setDescrizione(soggettoForm.getDescrizioneComuneEsteroMorte());
			}
			String flg = soggettoService.flagDecretoSequestro(soggettoForm);
			soggettoForm.setFlgDecretoSequestro(flg);
			request.getSession().removeAttribute("RuoloSession");
			return new ModelAndView("procedimento/soggetto/modificaSoggettoFisico", "soggettoForm",
					soggettoForm);
		}
		soggettoForm = procedimentoService.caricaValoriDettaglioSoggettoGiuridicoProcedimento(soggettoForm);
		if (soggettoForm.getDescComuneEsteroDomicilioEletto() != null) {
			soggettoForm.setComuneDomicilioEletto(new ComboForm());
			soggettoForm.getComuneDomicilioEletto()
					.setDescrizione(soggettoForm.getDescComuneEsteroDomicilioEletto());
		}
		soggettoForm.setFrom("modifica");
		// super.setIndietroForm(soggettoForm);
		request.getSession().removeAttribute("RuoloSession");
		return new ModelAndView("procedimento/nuovo/nuovoSoggettoGiuridico", "soggettoForm", soggettoForm);
	}

	@RequestMapping(value = "/procedimento/soggetto/visualizzaDirettiInteressatiRiesame.htm", method = RequestMethod.POST, params = {
			"Modifica" })
	public ModelAndView modificaSoggettiDirettiInteressatiRiesame(SoggettoForm soggettoForm,
			BindingResult result) {

		soggettoValidation.validateGestioneSoggetto(soggettoForm, result, request);
		if (result.hasErrors()) {
			return visualizzaSoggettiDirettiInteressatiRiesame(soggettoForm);
		}
		procedimentoService.setIdSoggettoIdVersioneTipoSoggFromChiave(soggettoForm);
		soggettoForm.setIdUfficio((Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE));
		soggettoForm.setIdProcedimento(
				((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO)))
						.getIdProcedimento());
		soggettoForm = procedimentoService.caricaValoriDettaglioSoggettoFisicoProcedimento(soggettoForm);
		// per il modifica nn posso cambiare il mapping e sono costretta a farlo qui.
		if (soggettoForm.getDescrizioneComuneEsteroNascita() != null) {
			soggettoForm.setComuneNascita(new ComboForm());
			soggettoForm.getComuneNascita().setDescrizione(soggettoForm.getDescrizioneComuneEsteroNascita());
		}
		if (soggettoForm.getDescComuneEsteroResidenza() != null) {
			soggettoForm.setComuneResidenza(new ComboForm());
			soggettoForm.getComuneResidenza().setDescrizione(soggettoForm.getDescComuneEsteroResidenza());
		}
		if (soggettoForm.getDescComuneEsteroDomicilio() != null) {
			soggettoForm.setComuneDomicilio(new ComboForm());
			soggettoForm.getComuneDomicilio().setDescrizione(soggettoForm.getDescComuneEsteroDomicilio());
		}
		return new ModelAndView("procedimento/soggetto/modificaSoggettoFisicoRiesame", "soggettoForm",
				soggettoForm);
	}

	@RequestMapping(value = "/procedimento/soggetto/modificaSoggettoFisico.htm", method = RequestMethod.POST, params = {
			"Salva" })
	public ModelAndView modificaSoggettoFisico(@Valid SoggettoForm soggettoForm, BindingResult result) {

		ModelAndView mnv = new ModelAndView("procedimento/soggetto/modificaSoggettoFisico", "soggettoForm",
				soggettoForm);
		String codiceUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);
		long idProcedimento = ((ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		if (!result.hasErrors())
			procedimentoService.aggiornaPersonaFisica(soggettoForm, idProcedimento, codiceUtente);
		else
			return mnv;
		// se la data di morte del soggetto è cancellata
		// occorre rendere 'false' gestioneEredi in modo
		// che le funzionalità di gestione (degli eredi) siano inibite
		if (soggettoForm.getDataMorte() == null) {
			soggettoForm.setGestioneEredi(false);
		}
		if ((soggettoForm.getGestioneEredi() != null) && (soggettoForm.getGestioneEredi() == true)) {
			List<EredeConviventeForm> listaEredi = procedimentoService.listaErediDelDefunto(soggettoForm);
			mnv.addObject("erediAssociati", listaEredi);
		}
		super.setMessage(Constants.SUCCESS_MESSAGE, "success", null, false);
		return mnv;
	}

	// @RequestMapping(value = "/procedimento/soggetto/modificaSoggettoFisico.htm", method =
	// RequestMethod.POST, params = { "Indietro" })
	// public String modificaSoggettoFisicoIndietro(SoggettoForm soggettoForm) {
	//
	// return "redirect:/procedimento/soggetto/visualizzaDirettiInteressati.htm";
	// }

	@RequestMapping(value = "/procedimento/soggetto/modificaSoggettoFisico.htm", method = RequestMethod.POST, params = {
			"NuovoErede" })
	public ModelAndView modificaSoggettoFisicoInserimentoNuovoErede(@Valid SoggettoForm soggettoForm,
			BindingResult result) {

		ModelAndView mnv = new ModelAndView("procedimento/soggetto/modificaSoggettoFisico", "soggettoForm",
				soggettoForm);
		if (soggettoForm.getGestioneEredi() == true) {
			List<EredeConviventeForm> listaEredi = procedimentoService.listaErediDelDefunto(soggettoForm);
			mnv.addObject("erediAssociati", listaEredi);
		}
		soggettoForm.setEredeConviventeForm(new EredeConviventeForm());
		soggettoForm.setGestioneInserimentoModificaErede(true);
		return mnv;
	}

	@RequestMapping(value = "/procedimento/soggetto/modificaSoggettoFisico.htm", method = RequestMethod.POST)
	public ModelAndView modificaSoggettoFisicoPost(@Valid SoggettoForm soggettoForm, BindingResult result) {

		ModelAndView mnv = new ModelAndView("procedimento/soggetto/modificaSoggettoFisico", "soggettoForm",
				soggettoForm);
		if ((soggettoForm.getGestioneEredi() != null) && (soggettoForm.getGestioneEredi() == true)) {
			List<EredeConviventeForm> listaEredi = procedimentoService.listaErediDelDefunto(soggettoForm);
			mnv.addObject("erediAssociati", listaEredi);
		}
		return mnv;
	}

	@RequestMapping(value = "/procedimento/soggetto/modificaSoggettoFisico.htm", method = RequestMethod.POST, params = {
			"verificaCF" })
	public ModelAndView controllaCFSoggettoFisico(@Valid SoggettoForm soggettoForm, BindingResult result) {

		ModelAndView mnv = new ModelAndView("procedimento/soggetto/modificaSoggettoFisico", "soggettoForm",
				soggettoForm);
		String msgErrore = "";
		if ("".equals(soggettoForm.getNome()) || soggettoForm.getNome() == null
				|| "".equals(soggettoForm.getCognome()) || soggettoForm.getCognome() == null) {
			msgErrore = "errors.campi.controllo.codicefiscale.nomeocognome";
		} else {
			// Invoco il WS
			EsitoControlloCFePIWrapper esito = agenziaEntrateService.controllaDatiAnagraficiPF(soggettoForm);
			if (esito.isInvocazioneFallita()) {
				super.setMessage(Constants.MESSAGE, "errors.campi.controllo.codicefiscale", esito.getErrore(),
						false);
			} else {
				if (soggettoForm.getCodiceFiscale() != null && !"".equals(soggettoForm.getCodiceFiscale())) {
					// Compara CF
					if (soggettoForm.getCodiceFiscale().equals(esito.getCodiceFiscale())) {
						super.setMessage(Constants.SUCCESS_MESSAGE,
								"success.operation.codicefiscale.corretto", " ", true);
					} else {
						super.setMessage(Constants.MESSAGE, "errors.campi.controllo.codicefiscale.diverso",
								esito.getCodiceFiscale(), false);
					}
				} else {
					soggettoForm.setCodiceFiscale(esito.getCodiceFiscale());
				}
			}
		}

		if (!"".equals(msgErrore)) {
			super.setMessage(Constants.MESSAGE, msgErrore, null, false);
		}

		if ((soggettoForm.getGestioneEredi() != null) && (soggettoForm.getGestioneEredi() == true)) {
			List<EredeConviventeForm> listaEredi = procedimentoService.listaErediDelDefunto(soggettoForm);
			mnv.addObject("erediAssociati", listaEredi);
		}
		return mnv;
	}

	@RequestMapping(value = "/procedimento/soggetto/modificaSoggettoFisicoRiesame.htm", method = RequestMethod.POST, params = {
			"Salva" })
	public ModelAndView modificaSoggettoFisicoRiesame(@Valid SoggettoForm soggettoForm,
			BindingResult result) {

		ModelAndView mnv = new ModelAndView("procedimento/soggetto/modificaSoggettoFisicoRiesame",
				"soggettoForm", soggettoForm);
		String codiceUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);
		long idProcedimento = ((ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		procedimentoService.aggiornaPersonaFisica(soggettoForm, idProcedimento, codiceUtente);
		return mnv;
	}

	@RequestMapping(value = "/procedimento/soggetto/gestioneDifensori.htm", method = RequestMethod.POST, params = {
			"NuovoDifensore" })
	public ModelAndView nuovoDifensore(DifensoreSoggettoForm difensoreSoggettoForm, BindingResult result) {

		caricaValoriDettaglioSoggetto(difensoreSoggettoForm.getSoggettoForm());
		return new ModelAndView("procedimento/soggetto/difensoriNuovo", "difensoreSoggettoForm",
				difensoreSoggettoForm);
	}

	@RequestMapping(value = "/procedimento/soggetto/gestioneDifensori.htm", method = RequestMethod.POST)
	public ModelAndView postDifensori(@Valid DifensoreSoggettoForm difensoreSoggettoForm,
			BindingResult result) {

		caricaValoriDettaglioSoggetto(difensoreSoggettoForm.getSoggettoForm());
		if (difensoreSoggettoForm.getSoggettoForm().getGratuitoPatrocinio() != null) {
			if (difensoreSoggettoForm.getSoggettoForm().getGratuitoPatrocinio() == false) {
				difensoreSoggettoForm.getSoggettoForm().setAnnoRegistroGratuitoPatrocinio(null);
				difensoreSoggettoForm.getSoggettoForm().setNumeroRegistroGratuitoPatrocinio(null);
				difensoreSoggettoForm.getSoggettoForm().setDataIstanzaGratuitoPatrocinio(null);
				difensoreSoggettoForm.getSoggettoForm().setDataProvvedimentoGratuitoPatrocinio(null);
				difensoreSoggettoForm.getSoggettoForm().getTenoreProvvedimentoGratuitoPatrocinio()
						.setId("-1");
				difensoreSoggettoForm.getSoggettoForm().setDataRevocaGratuitoPatrocinio(null);
			}
			if ((difensoreSoggettoForm.getSoggettoForm().getGratuitoPatrocinio() == true)
					&& (difensoreSoggettoForm.getGratuitoPatrocinioCaricato() == true)) {
				difensoreSoggettoForm.setSoggettoForm(procedimentoService
						.caricaValoriGratuitoPatrocinioDelSoggetto(difensoreSoggettoForm.getSoggettoForm()));
				difensoreSoggettoForm.setGratuitoPatrocinioCaricato(true);
			} else if ((difensoreSoggettoForm.getSoggettoForm().getGratuitoPatrocinio()) == false
					&& (difensoreSoggettoForm.getGratuitoPatrocinioCaricato() == true)) {
				procedimentoService.cancellaGratuitoPatrocinioSoggProc(
						difensoreSoggettoForm.getSoggettoForm(), RequestUtil.recuperaIdProcedimento(request),
						RequestUtil.recuperaIdUfficioUtente(request));
				difensoreSoggettoForm.setGratuitoPatrocinioCaricato(false);
			}
		}
		return difensoriAssociatiJsp(difensoreSoggettoForm, result);
	}

	@RequestMapping(value = "/procedimento/soggetto/gestioneDifensori.htm", method = RequestMethod.POST, params = {
			"RicercaDifensoreReginde" })
	public ModelAndView ricercaDifensoriReginde(DifensoreSoggettoForm difensoreSoggettoForm,
			BindingResult result) {

		String difensoreInput = difensoreSoggettoForm.getDifensoreForm().getCognome();
		String difensoreSearch = convertiAccenti(difensoreInput);
		difensoreSoggettoForm.getDifensoreForm().setCognome(difensoreSearch);
		caricaValoriDettaglioSoggetto(difensoreSoggettoForm.getSoggettoForm());
		ModelAndView mnv = difensoriAssociatiJsp(difensoreSoggettoForm, result);

		if (Configuration.isConnessioneInterna() == false) {
			// Segnalazione SMP-19
			super.getIntoSession("listaReginde", true);
			soggettoValidation.validateCognomeReginde(difensoreSoggettoForm, result);
			if (result.hasErrors()) {
				super.setMessage(Constants.MESSAGE, "soggetto.difensori.ricerca.error.reginde", null, false);
			} else {
				// richiamato da paginazione
				if (super.getIntoSession("listaReginde", false) == null) {
					List<DifensoreSoggettoForm> listaDifensoriReginde = procedimentoService
							.listaDifensoriReginde(difensoreSoggettoForm.getDifensoreForm().getCognome());
					if (listaDifensoriReginde == null) // Il sistema REGINDE non risponde o non funziona
						super.setMessage(Constants.MESSAGE, "soggetto.difensori.error.reginde", null, false);
					else {
						mnv.addObject("difensoriWebService", listaDifensoriReginde);
						super.saveIntoSession(listaDifensoriReginde, "listaReginde", true);
					}
				}
			}
		} else
			super.setMessage(Constants.MESSAGE, "error.fuoriReteGiustizia", null, false);
		mnv.addObject("abilitaRicerca", true);
		mnv.addObject("visualizzaPulsanteRicerca", "Ricerca");
		// addBreadcrumbs("RicercaDifensoreReginde");
		return mnv;
	}

	@RequestMapping(value = "/procedimento/soggetto/gestioneDifensori.htm", method = RequestMethod.GET)
	public ModelAndView difensoriBaseDatiPaginazione(DifensoreSoggettoForm difensoreSoggettoForm,
			BindingResult result, ModelMap map) {

		return ricercaDifensoriBaseDati(difensoreSoggettoForm, result, map);
	}

	@RequestMapping(value = "/procedimento/soggetto/gestioneDifensori.htm", method = RequestMethod.POST, params = {
			"RicercaDifensore" })
	public ModelAndView ricercaDifensoriBaseDati(DifensoreSoggettoForm difensoreSoggettoForm,
			BindingResult result, ModelMap map) {

		caricaValoriDettaglioSoggetto(difensoreSoggettoForm.getSoggettoForm());
		Integer vidUffOwn = recuperaIdUfficioUtente();
		ModelAndView mnv = difensoriAssociatiJsp(difensoreSoggettoForm, result);
		long idProcedimento = ((ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		List<DifensoreSoggettoForm> listaDifensoriBaseDati = procedimentoService.listaDifensoriNoReginde(
				difensoreSoggettoForm.getDifensoreForm().getCognome(),
				difensoreSoggettoForm.getSoggettoForm().getIdSoggetto(), idProcedimento, vidUffOwn);
		mnv.addObject("difensoriBaseDati", listaDifensoriBaseDati);
		mnv.addObject("abilitaRicerca", true);
		mnv.addObject("visualizzaPulsanteNuovo", "NuovoDifensore");
		mnv.addObject("visualizzaPulsanteRicerca", "Ricerca");
		if (super.getIntoSession("listaReginde", false) != null) {
			mnv.addObject("difensoriWebService", super.getIntoSession("listaReginde", false));
		}
		// addBreadcrumbs("RicercaDifensore");
		return mnv;
	}

	@RequestMapping(value = { "/procedimento/soggetto/difensoriAssociatiModifica.htm",
			"/procedimento/soggetto/difensoriNuovo.htm" }, method = RequestMethod.POST, params = {
					"SalvaDifensore" })
	public ModelAndView salvaDifensore(@Valid DifensoreSoggettoForm difensoreSoggettoForm,
			BindingResult result) {

		caricaValoriDettaglioSoggetto(difensoreSoggettoForm.getSoggettoForm());
		if (result.hasErrors()) {
			if (difensoreSoggettoForm.getDifensoreForm().getIdDifensore() != null) {
				return new ModelAndView("procedimento/soggetto/difensoriAssociatiModifica",
						"difensoreSoggettoForm", difensoreSoggettoForm);
			} else {
				return new ModelAndView("procedimento/soggetto/difensoriNuovo", "difensoreSoggettoForm",
						difensoreSoggettoForm);
			}
		}
		soggettoValidation.validateSalvaDifensore(difensoreSoggettoForm, result);
		if (result.hasErrors()) {
			if (difensoreSoggettoForm.getDifensoreForm().getIdDifensore() != null) {
				return new ModelAndView("procedimento/soggetto/difensoriAssociatiModifica",
						"difensoreSoggettoForm", difensoreSoggettoForm);
			} else {
				return new ModelAndView("procedimento/soggetto/difensoriNuovo", "difensoreSoggettoForm",
						difensoreSoggettoForm);
			}
		}
		boolean erroreDataRevoca = false;
		if (difensoreSoggettoForm.getDataRevoca() != null) {
			erroreDataRevoca = procedimentoService
					.verificaDataRevocaDifensoreAssociato(difensoreSoggettoForm);
			if (erroreDataRevoca) {
				// Presente un'Udienza con data successiva alla Data Revoca inserita
				super.setMessage(Constants.MESSAGE, "soggetto.difensore.error.dataRevoca.atto", null, false);
				if (difensoreSoggettoForm.getDifensoreForm().getIdDifensore() != null) {
					return new ModelAndView("procedimento/soggetto/difensoriAssociatiModifica",
							"difensoreSoggettoForm", difensoreSoggettoForm);
				} else {
					return new ModelAndView("procedimento/soggetto/difensoriNuovo", "difensoreSoggettoForm",
							difensoreSoggettoForm);
				}
			}
		}
		String codiceUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);
		if (Long.toString(Constants.TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM)
				.equals(((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO)))
						.getTipoProcedimento().getId())
				|| Long.toString(Constants.TIPOPROC_CORREZIONE_ERRORI_CAM).equals(
						((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO)))
								.getTipoProcedimento().getId()))
			procedimentoService.inserisciModificaDifensoreAssociatoIntestatario(difensoreSoggettoForm,
					codiceUtente);
		else
			procedimentoService.inserisciModificaDifensoreAssociatoSoggetto(difensoreSoggettoForm,
					codiceUtente);
		difensoreSoggettoForm.setSoggettoForm(procedimentoService
				.caricaValoriGratuitoPatrocinioDelSoggetto(difensoreSoggettoForm.getSoggettoForm()));
		if (difensoreSoggettoForm.getSoggettoForm().getGratuitoPatrocinio() == true) {
			difensoreSoggettoForm.setGratuitoPatrocinioCaricato(true);
		} else {
			difensoreSoggettoForm.setGratuitoPatrocinioCaricato(false);
		}
		// altrimenti cancella il difensore anche senza selezionarlo
		difensoreSoggettoForm.getDifensoreForm().setIdDifensore(null);

		removeLast("POST", "/SMP2/procedimento/soggetto/gestioneDifensori.htm", "AbilitaRicerca", "",
				request.getSession());
		addBreadcrumbs("POST", "/SMP2/procedimento/soggetto/visualizzaDirettiInteressati.htm", "Difensori");

		return difensoriAssociatiJsp(difensoreSoggettoForm, result);
	}

	@RequestMapping(value = "/procedimento/soggetto/gestioneDifensori.htm", method = RequestMethod.POST, params = {
			"SalvaGratuitoPatrocinio" })
	public ModelAndView salvaGratuitoPatrocinio(@Valid DifensoreSoggettoForm difensoreSoggettoForm,
			BindingResult result) {

		if (difensoreSoggettoForm.getSoggettoForm().getGratuitoPatrocinio() == true) {
			soggettoValidation.validateGratuitoPatrocinio(difensoreSoggettoForm, result);
		}

		if (result.hasErrors()) {
			return difensoriAssociatiJsp(difensoreSoggettoForm, result);
		}

		procedimentoService.modificaGratuitoPatrocinioSoggProc(difensoreSoggettoForm.getSoggettoForm(),
				RequestUtil.recuperaIdProcedimento(request), RequestUtil.recuperaIdUfficioUtente(request));

		caricaValoriDettaglioSoggetto(difensoreSoggettoForm.getSoggettoForm());
		difensoreSoggettoForm.setSoggettoForm(procedimentoService
				.caricaValoriGratuitoPatrocinioDelSoggetto(difensoreSoggettoForm.getSoggettoForm()));

		if (difensoreSoggettoForm.getSoggettoForm().getGratuitoPatrocinio() == true) {
			difensoreSoggettoForm.setGratuitoPatrocinioCaricato(true);
		} else {
			difensoreSoggettoForm.setGratuitoPatrocinioCaricato(false);
		}

		return difensoriAssociatiJsp(difensoreSoggettoForm, result);
	}

	@RequestMapping(value = "/procedimento/soggetto/misuraPersonale.htm", method = RequestMethod.POST, params = {
			"Salva" })
	public ModelAndView salvaMisuraPersonale(@Valid SoggettoForm soggettoForm, BindingResult result,
			ModelMap modelMap) {

		soggettoValidation.validateSalvaMisuraPersonale(soggettoForm, false, result, request);
		if (result.hasErrors()) {
			soggettoForm
					.setIdUfficio((Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE));
			if ("F".equals(soggettoForm.getTipoSoggetto())) {
				soggettoForm = procedimentoService.caricaValoriDettaglioSoggettoFisico(soggettoForm);
				soggettoForm.setTipoSoggetto(Constants.SOGGETTO_FISICO);
			} else {
				soggettoForm = procedimentoService.caricaValoriDettaglioSoggettoGiuridico(soggettoForm);
				soggettoForm.setTipoSoggetto(Constants.SOGGETTO_GIURIDICO);
			}
			ModelAndView mnv = new ModelAndView("procedimento/soggetto/misuraPersonale", "soggettoForm",
					soggettoForm);
			// Carico nel ModelAndView le Normative
			caricaNormative(soggettoForm, mnv, null);
			List<ComboForm> list = commonService.listaPrescrizioniMisuraPersonale();
			mnv.addObject("listaPrescrizioni", list);
			return mnv;
		}
		long vidProc = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO)))
				.getIdProcedimento();
		ProcedimentoForm procedimento = (ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO));

		Integer vidUffOwn = recuperaIdUfficioUtente();
		String vcodUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);
		long idSoggInt = soggettoForm.getIdSoggetto();
		long idTipoPrev = Long.parseLong(soggettoForm.getTipologiaPrevenzione().getId());
		long idTipoNorm = Long.parseLong(soggettoForm.getNormativa().getId());
		misuraService.inserisciMisuraPersonale(vidProc, vidUffOwn, idSoggInt, vcodUtente, soggettoForm);
		if (procedimento.getTipoProcedimento() == null || procedimento.getTipoProcedimento().getId() == null
				|| (procedimento.getTipoProcedimento() != null
						&& procedimento.getTipoProcedimento().getId() != null
						&& (!Long.toString(Constants.TIPOPROC_PERMESSI_CAM)
								.equals(procedimento.getTipoProcedimento().getId()))
						&& (!Long.toString(Constants.TIPOPROC_MODIFICA_PERSONALE_CAM)
								.equals(procedimento.getTipoProcedimento().getId()))
						&& (!Long.toString(Constants.TIPOPROC_CAUZIONI_CAM)
								.equals(procedimento.getTipoProcedimento().getId()))))
			procedimentoService.aggiornaTipoPrevenzioneNormativaSoggetto(vidProc, vidUffOwn, idSoggInt,
					idTipoPrev, idTipoNorm);
		if (soggettoForm.getTipologiaPrevenzioneDb() != null
				&& soggettoForm.getTipologiaPrevenzioneDb().getId() != null) {
			if (!soggettoForm.getTipologiaPrevenzioneDb().getId()
					.equals(soggettoForm.getTipologiaPrevenzione().getId())) {
				procedimentoService.aggiornaFlgTipoPrevenzioneProcedimento(vidProc, vidUffOwn);
			}
		} else if (soggettoForm.getTipologiaPrevenzione() != null
				&& soggettoForm.getTipologiaPrevenzione().getId() != null) {
			procedimentoService.aggiornaFlgTipoPrevenzioneProcedimento(vidProc, vidUffOwn);
		}
		if (soggettoForm.getSchermataDiProvenienza() != null
				&& "proposta".equalsIgnoreCase(soggettoForm.getSchermataDiProvenienza())) {
			AttoForm attoForm = new AttoForm();
			attoForm.setCodAtto(0L);
			return attoController.proposta(attoForm, result, request, modelMap);
		}
		updateProcedimentoIntoSession(vidProc);
		return visualizzaSoggettiDirettiInteressati(soggettoForm);
	}

	@RequestMapping(value = "/procedimento/soggetto/misuraPersonale.htm", method = RequestMethod.POST, params = {
			"SalvaMisuraDecisorioIGrado" })
	public String salvaMisuraPersonaleDecisorioIGrado(
			@ModelAttribute("soggettoForm") SoggettoForm soggettoForm, BindingResult result, ModelMap map) {

		ProfiloForm profilo = (ProfiloForm) request.getSession().getAttribute("profilo");
		soggettoValidation.validateSalvaMisuraPersonale(soggettoForm, true, result, request);
		// SoggettoForm datiVecchi = null;
		if (!result.hasErrors()) {
			ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession()
					.getAttribute(Constants.ID_PROCEDIMENTO);
			if (Utility.isDefinito(procedimento.getTipoProcedimento())) {
				if (Constants.TIPOPROC_AGGRAVAMENTO_PERSONALE
						.equals(procedimento.getTipoProcedimento().getId())
						|| Long.toString(Constants.TIPOPROC_MODIFICA_PERSONALE_CAM)
								.equals(procedimento.getTipoProcedimento().getId())
						|| Long.toString(Constants.TIPOPROC_PERMESSI_CAM)
								.equals(procedimento.getTipoProcedimento().getId())
						|| Long.toString(Constants.TIPOPROC_CAUZIONI_CAM)
								.equals(procedimento.getTipoProcedimento().getId())) {
					int idUff = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
					long idProc = ((ProcedimentoForm) (request.getSession()
							.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
					// Recupero informazioni riguardanti la Misura Personale della Proposta relativa
					// all'Aggravamento
					// long attoProposta = 0;
					if (Long.toString(Constants.TIPOPROC_MODIFICA_PERSONALE_CAM)
							.equals(procedimento.getTipoProcedimento().getId())
							|| Long.toString(Constants.TIPOPROC_PERMESSI_CAM)
									.equals(procedimento.getTipoProcedimento().getId())
							|| Long.toString(Constants.TIPOPROC_CAUZIONI_CAM)
									.equals(procedimento.getTipoProcedimento().getId()))
						/* attoProposta = */beneService.getIdAttoByUfficioEProcedimentoCameralini(idUff,
								idProc);
					else
						/* attoProposta = */beneService.getIdAttoByUfficioEProcedimento(idUff, idProc);
					/*
					 * datiVecchi = new SoggettoForm();
					 * datiVecchi.setIdSoggetto(soggettoForm.getIdSoggetto());
					 * datiVecchi.setIdAtto(attoProposta); datiVecchi.setIdProcedimento(idProc);
					 * datiVecchi.setIdUfficio(idUff); datiVecchi =
					 * misuraService.caricaMisuraPersonale(datiVecchi);
					 */
				}
			}
			// if (datiVecchi != null) {
			// MisuraForm misura = soggettoForm.getMisura();
			// MisuraForm misuraVecchia = datiVecchi.getMisura();
			// soggettoValidation.validateMisuraPersonalePerAggravamento(misura, misuraVecchia, result);
			// }
		}
		AttoForm currentAttoDecisorioIGrado = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
		if (result.hasErrors()) {
			soggettoForm
					.setIdUfficio((Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE));
			if ("F".equals(soggettoForm.getTipoSoggetto())) {
				soggettoForm = procedimentoService.caricaValoriDettaglioSoggettoFisico(soggettoForm);
				soggettoForm.setTipoSoggetto(Constants.SOGGETTO_FISICO);
			} else {
				soggettoForm = procedimentoService.caricaValoriDettaglioSoggettoGiuridico(soggettoForm);
				soggettoForm.setTipoSoggetto(Constants.SOGGETTO_GIURIDICO);
			}
			List<ComboForm> list = commonService.listaPrescrizioniMisuraPersonale();
			map.addAttribute("listaPrescrizioni", list);
			String carattereMisura = "P";
			ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession()
					.getAttribute(Constants.ID_PROCEDIMENTO);
			if (procedimento.getTipoProcedimento() != null
					&& procedimento.getTipoProcedimento().getId() != null
					&& Constants.TIPOPROC_AGGRAVAMENTO_PERSONALE
							.equals(procedimento.getTipoProcedimento().getId())) {
				carattereMisura = "G";
			}

			long idCategoriaAtto = Long.valueOf(Constants.DECRETO_DECISORIO_I_GRADO);
			if (Constants.DECRETO_DECISORIO_I_GRADO_CAMERE
					.equals(currentAttoDecisorioIGrado.getCategoriaAtto().getId()))
				idCategoriaAtto = Long.valueOf(Constants.DECRETO_DECISORIO_I_GRADO_CAMERE);
			long idTipoUfficio = profilo.getIdTipoUfficio();
			if (idTipoUfficio == Constants.TIPO_UFFICIO_CORTE_D_APPELLO) {
				idCategoriaAtto = Long.valueOf(Constants.DECRETO_DECISORIO_II_GRADO);
			}
			String attoIstanza = "";

			if (idCategoriaAtto == Long.valueOf(Constants.DECRETO_DECISORIO_I_GRADO_CAMERE)
					|| idTipoUfficio == Constants.TIPO_UFFICIO_CORTE_D_APPELLO) {
				if (Long.toString(Constants.TIPOPROC_MODIFICA_PERSONALE_CAM)
						.equals(procedimento.getTipoProcedimento().getId()))
					attoIstanza = Constants.ISTANZA_MODIFICA_PERSONALE;
				if (Long.toString(Constants.TIPOPROC_CAUZIONI_CAM)
						.equals(procedimento.getTipoProcedimento().getId())) {
					attoIstanza = Constants.ISTANZA_CAUZIONE;
					carattereMisura = "CA";
				}
				if (Long.toString(Constants.TIPOPROC_PERMESSI_CAM)
						.equals(procedimento.getTipoProcedimento().getId())) {
					attoIstanza = Constants.ISTANZA_PERMESSI;
					carattereMisura = "PE";
				}

				List<AttoForm> istanze = attoService.getricercaIstanza(
						(Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE),
						((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO)))
								.getIdProcedimento(),
						Long.parseLong(Constants.TIPO_ATTO_ISTANZA_CAM), Long.parseLong(attoIstanza));
				AttoForm istanza = new AttoForm();

				if (istanze != null && !istanze.isEmpty()) {
					istanza = istanze.get(0);
					soggettoForm.setDescRichiesta(istanza.getNote());
				}
			}
			List<ComboForm> esiti = procedimentoService.getEsiti(carattereMisura, idCategoriaAtto,
					profilo.getIdTipoUfficio());
			map.addAttribute("esiti", esiti);
			if (soggettoForm.getMisura().getCauzione() != null
					&& "S".equalsIgnoreCase(soggettoForm.getMisura().getCauzione())) {
				map.addAttribute("importoCauzione", true);
			}

			map.addAttribute("soggettoForm", soggettoForm);
			// Carico nel ModelAndView le Normative
			caricaNormative(soggettoForm, null, map);
			return "procedimento/soggetto/misuraPersonale";
		}
		// ottieni l'atto (decisorio) corrente dalla sessione
		ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO);

		long vidProc = procedimento.getIdProcedimento();
		long tipoProc = Long.valueOf(procedimento.getTipoProcedimento().getId()).longValue();
		Integer vidUffOwn = recuperaIdUfficioUtente();
		String vcodUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);
		long idSoggInt = soggettoForm.getIdSoggetto();

		/*
		 * if ( procedimento.getTipoProcedimento().getId().equals(""+Constants.TIPOPROC_PERMESSI_CAM) ||
		 * procedimento.getTipoProcedimento().getId().equals(""+Constants.TIPOPROC_CAUZIONI_CAM)){ ComboForm
		 * tipologiaPrevenzione=datiVecchi.getTipologiaPrevenzione(); ComboForm
		 * normativa=datiVecchi.getMisura().getNormativaDiRiferimento();
		 * soggettoForm.setTipologiaPrevenzione(tipologiaPrevenzione); soggettoForm.setNormativa(normativa); }
		 */

		long idTipoPrev = 0;
		if (soggettoForm.getTipologiaPrevenzione() != null
				&& soggettoForm.getTipologiaPrevenzione().getId() != null)
			idTipoPrev = Long.parseLong(soggettoForm.getTipologiaPrevenzione().getId());
		long idTipoNorm = 0L;
		if (soggettoForm.getNormativa() != null && soggettoForm.getNormativa().getId() != null)
			idTipoNorm = Long.parseLong(soggettoForm.getNormativa().getId());
		// setto l'ID dell'atto decisorio di I Grado corrente altrimenti
		// prenderebbe quello della proposta
		soggettoForm.setIdAtto(currentAttoDecisorioIGrado.getCodAtto());

		misuraService.inserisciMisuraPersonaleDecisorioIGrado(vidProc, tipoProc, vidUffOwn, idSoggInt,
				vcodUtente, soggettoForm);
		if ((!Long.toString(Constants.TIPOPROC_PERMESSI_CAM)
				.equals(procedimento.getTipoProcedimento().getId()))
				&& (!Long.toString(Constants.TIPOPROC_MODIFICA_PERSONALE_CAM)
						.equals(procedimento.getTipoProcedimento().getId()))
				&& (!Long.toString(Constants.TIPOPROC_CAUZIONI_CAM)
						.equals(procedimento.getTipoProcedimento().getId()))) {
			procedimentoService.aggiornaTipoPrevenzioneNormativaSoggetto(vidProc, vidUffOwn, idSoggInt,
					idTipoPrev, idTipoNorm);
			if (soggettoForm.getTipologiaPrevenzioneDb() != null
					&& soggettoForm.getTipologiaPrevenzioneDb().getId() != null
					&& soggettoForm.getTipologiaPrevenzione() != null
					&& soggettoForm.getTipologiaPrevenzione().getId() != null) {
				if (!soggettoForm.getTipologiaPrevenzioneDb().getId()
						.equals(soggettoForm.getTipologiaPrevenzione().getId())) {
					procedimentoService.aggiornaFlgTipoPrevenzioneProcedimento(vidProc, vidUffOwn);
				}
			}
		}
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation", "INSERIMENTO", false);
		return attoController.copertinaAtto(new AttoForm(), result, request, map);
	}

	@RequestMapping(value = "/procedimento/soggetto/modificaSoggettoFisico.htm", method = RequestMethod.POST, params = {
			"SalvaErede" })
	public ModelAndView salvaNuovoErede(@Valid SoggettoForm soggettoForm, BindingResult result) {

		ModelAndView mnv = new ModelAndView("procedimento/soggetto/modificaSoggettoFisico", "soggettoForm",
				soggettoForm);
		if (result.hasErrors()) {
			List<EredeConviventeForm> listaEredi = procedimentoService.listaErediDelDefunto(soggettoForm);
			mnv.addObject("erediAssociati", listaEredi);
			return mnv;
		}
		String codiceUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);
		procedimentoService.inserisciEredeDelDefunto(soggettoForm, codiceUtente);
		List<EredeConviventeForm> listaEredi = procedimentoService.listaErediDelDefunto(soggettoForm);
		mnv.addObject("erediAssociati", listaEredi);
		soggettoForm.setGestioneInserimentoModificaErede(null);
		return mnv;
	}

	// metodo per la selezione delle comboForm
	@RequestMapping(value = "/procedimento/soggetto/misuraPersonale.htm", method = RequestMethod.POST)
	public ModelAndView selezioneComboMisuraPersonale(SoggettoForm soggettoForm) {

		ModelAndView mnv = new ModelAndView("procedimento/soggetto/misuraPersonale", "soggettoForm",
				soggettoForm);
		mnv.addObject("provenienza", soggettoForm.getSchermataDiProvenienza().toLowerCase());
		ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO);
		// Se passo da pagina disabilitata ad una qualsiasi altra Decisione
		if ((soggettoForm.getMisura().getTipoMisura() == null)
				&& !(Long.toString(Constants.TIPOPROC_MODIFICA_PERSONALE_CAM)
						.equals(procedimento.getTipoProcedimento().getId())
						|| Long.toString(Constants.TIPOPROC_CAUZIONI_CAM)
								.equals(procedimento.getTipoProcedimento().getId())
						|| Long.toString(Constants.TIPOPROC_PERMESSI_CAM)
								.equals(procedimento.getTipoProcedimento().getId()))) {
			String cauzProp = soggettoForm.getMisura().getCauzioneProp();
			String art67Prop = soggettoForm.getMisura().getArt67Prop();
			ComboForm decisioneMisura = soggettoForm.getMisura().getDecisioneMisuraPersonale();
			soggettoForm
					.setIdUfficio((Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE));
			soggettoForm.setIdProcedimento(
					((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO)))
							.getIdProcedimento());
			soggettoForm.setIdAtto(0L);
			AttoForm currentAtto = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
			soggettoForm.setIdAtto(currentAtto.getCodAtto());
			soggettoForm = misuraService.caricaMisuraPersonale(soggettoForm);
			soggettoForm.getMisura().setCauzioneProp(cauzProp);
			soggettoForm.getMisura().setArt67Prop(art67Prop);
			soggettoForm.getMisura().setDecisioneMisuraPersonale(decisioneMisura);
			// Carico le informazioni da T_REL_PROC_SOGINT
			soggettoForm = procedimentoService.caricaValoriGratuitoPatrocinioDelSoggetto(soggettoForm);
			soggettoForm.setTipologiaPrevenzioneDb(soggettoForm.getTipologiaPrevenzione());
		}
		// Carico nel ModelAndView le Normative
		caricaNormative(soggettoForm, mnv, null);
		soggettoForm.setIdUfficio((Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE));
		if ("F".equals(soggettoForm.getTipoSoggetto())) {
			soggettoForm = procedimentoService.caricaValoriDettaglioSoggettoFisico(soggettoForm);
			soggettoForm.setTipoSoggetto(Constants.SOGGETTO_FISICO);
		} else {
			soggettoForm = procedimentoService.caricaValoriDettaglioSoggettoGiuridico(soggettoForm);
			soggettoForm.setTipoSoggetto(Constants.SOGGETTO_GIURIDICO);
		}
		if (soggettoForm.getMisura().getTipoMisura() != null) {
			if ("1".equals(soggettoForm.getMisura().getTipoMisura().getId())) {
				soggettoForm.getMisura().setLocalita(null);
			}
		}
		List<ComboForm> list = commonService.listaPrescrizioniMisuraPersonale();
		mnv.addObject("listaPrescrizioni", list);
		// controlla che in sessione esista un atto di tipo Decisorio I Grado
		// se esiste, carica la combo degli esiti
		ProfiloForm profilo = (ProfiloForm) request.getSession().getAttribute("profilo");
		AttoForm currentAtto = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
		if ((currentAtto != null)
				&& (Constants.DECRETO_DECISORIO_I_GRADO.equals(currentAtto.getCategoriaAtto().getId())
						|| Constants.DECRETO_DECISORIO_II_GRADO.equals(currentAtto.getCategoriaAtto().getId())
						|| Constants.DECRETO_DECISORIO_I_GRADO_CAMERE
								.equals(currentAtto.getCategoriaAtto().getId()))) {
			String carattereMisura = "P";
			if (procedimento.getTipoProcedimento() != null
					&& procedimento.getTipoProcedimento().getId() != null
					&& Constants.TIPOPROC_AGGRAVAMENTO_PERSONALE
							.equals(procedimento.getTipoProcedimento().getId())) {
				carattereMisura = "G";
			}
			long idCategoriaAtto = Long.valueOf(Constants.DECRETO_DECISORIO_I_GRADO);
			if (Constants.DECRETO_DECISORIO_I_GRADO_CAMERE.equals(currentAtto.getCategoriaAtto().getId())) {
				idCategoriaAtto = Long.valueOf(Constants.DECRETO_DECISORIO_I_GRADO_CAMERE);
				String attoIstanza = "";
				if (Long.toString(Constants.TIPOPROC_MODIFICA_PERSONALE_CAM)
						.equals(procedimento.getTipoProcedimento().getId()))
					attoIstanza = Constants.ISTANZA_MODIFICA_PERSONALE;
				if (Long.toString(Constants.TIPOPROC_CAUZIONI_CAM)
						.equals(procedimento.getTipoProcedimento().getId())) {
					attoIstanza = Constants.ISTANZA_CAUZIONE;
					carattereMisura = "CA";
				}
				if (Long.toString(Constants.TIPOPROC_PERMESSI_CAM)
						.equals(procedimento.getTipoProcedimento().getId())) {
					attoIstanza = Constants.ISTANZA_PERMESSI;
					carattereMisura = "PE";
				}
				AttoForm attoOrigine = (attoService.getAttiByProcedimento(procedimento.getIdProcedimento(),
						Long.parseLong(Constants.TIPO_ATTO_ISTANZA_CAM), null)).get(0);
				List<SoggettoStralcioForm> listaRicorso = attoService.getRicercaMisureIstanza(
						procedimento.getIdProcedimento(), recuperaIdUfficioUtente(), attoOrigine, null,
						Integer.parseInt(attoIstanza));
				mnv.addObject("listaRicorso", listaRicorso);
			}
			long idTipoUfficio = profilo.getIdTipoUfficio();
			if (idTipoUfficio == Constants.TIPO_UFFICIO_CORTE_D_APPELLO) {
				idCategoriaAtto = Long.valueOf(Constants.DECRETO_DECISORIO_II_GRADO);
			}

			String attoIstanza = "";

			if (idCategoriaAtto == Long.valueOf(Constants.DECRETO_DECISORIO_I_GRADO_CAMERE)
					|| idTipoUfficio == Constants.TIPO_UFFICIO_CORTE_D_APPELLO) {
				if (Long.toString(Constants.TIPOPROC_MODIFICA_PERSONALE_CAM)
						.equals(procedimento.getTipoProcedimento().getId()))
					attoIstanza = Constants.ISTANZA_MODIFICA_PERSONALE;
				if (Long.toString(Constants.TIPOPROC_CAUZIONI_CAM)
						.equals(procedimento.getTipoProcedimento().getId())) {
					attoIstanza = Constants.ISTANZA_CAUZIONE;
					carattereMisura = "CA";
				}
				if (Long.toString(Constants.TIPOPROC_PERMESSI_CAM)
						.equals(procedimento.getTipoProcedimento().getId())) {
					attoIstanza = Constants.ISTANZA_PERMESSI;
					carattereMisura = "PE";
				}

				List<AttoForm> istanze = attoService.getricercaIstanza(
						(Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE),
						((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO)))
								.getIdProcedimento(),
						Long.parseLong(Constants.TIPO_ATTO_ISTANZA_CAM), Long.parseLong(attoIstanza));
				AttoForm istanza = new AttoForm();

				if (istanze != null && !istanze.isEmpty()) {
					istanza = istanze.get(0);
					soggettoForm.setDescRichiesta(istanza.getNote());
				}
			}

			List<ComboForm> esiti = procedimentoService.getEsiti(carattereMisura, idCategoriaAtto,
					profilo.getIdTipoUfficio());
			mnv.addObject("esiti", esiti);
			if (soggettoForm.getMisura().getCauzione() != null) {
				if ("S".equalsIgnoreCase(soggettoForm.getMisura().getCauzione())) {
					mnv.addObject("importoCauzione", true);
					if (soggettoForm.getMisura().getCauzioneApp() != null) {
						if ("S".equalsIgnoreCase(soggettoForm.getMisura().getCauzioneApp())) {
							// Imposto i valori di appoggio con quelli presenti in p6agina - Indipendemente
							// dalla Cauzione S o N
							soggettoForm.getMisura().setImportoApp(soggettoForm.getMisura().getImporto());
							soggettoForm.getMisura().setNoteApp(soggettoForm.getMisura().getNote());
						} else {
							soggettoForm.getMisura().setImporto(soggettoForm.getMisura().getImportoApp());
							soggettoForm.getMisura().setNote(soggettoForm.getMisura().getNoteApp());
						}
						soggettoForm.getMisura().setCauzioneApp("S");
					}
				} else {
					if (soggettoForm.getMisura().getCauzioneApp() != null) {
						if ("S".equalsIgnoreCase(soggettoForm.getMisura().getCauzioneApp())) {
							soggettoForm.getMisura().setImporto(soggettoForm.getMisura().getImportoApp());
							soggettoForm.getMisura().setNote(soggettoForm.getMisura().getNoteApp());
						}
						soggettoForm.getMisura().setCauzioneApp("N");
					}
				}
			}
		}
		// modifica cauzione
		if (procedimento.getTipoProcedimento() != null && procedimento.getTipoProcedimento().getId() != null
				&& Long.toString(Constants.TIPOPROC_CAUZIONI_CAM)
						.equals(procedimento.getTipoProcedimento().getId())) {
			mnv.addObject("importoCauzione", true);
		}

		return mnv;
	}

	public void caricaNormative(SoggettoForm soggettoForm, ModelAndView mnv, ModelMap map) {

		// Inizio Valorizzazione combo delle Normative
		List<ComboForm> normativeTipoPrev = new ArrayList<ComboForm>();
		List<ComboForm> normativeTipoMisura = new ArrayList<ComboForm>();
		if (soggettoForm.getTipologiaPrevenzione() != null) {
			if (soggettoForm.getTipologiaPrevenzione().getId() != null
					&& !Constants.NO_ITEM_SELECTED.equals(soggettoForm.getTipologiaPrevenzione().getId())) {
				if (soggettoForm.getIpotesiMisura() != null) {
					// Imposto manualmente ad 1 il Tipo di Procedimento
					soggettoForm.getIpotesiMisura().setId("1");
				} else {
					// Imposto manualmente ad 1 il Tipo di Procedimento
					soggettoForm.setIpotesiMisura(new ComboForm("1", ""));
				}
				normativeTipoPrev = commonService
						.getNormativaByTipoProcedAndTipologiaPrevenzione(soggettoForm);
			}
		}
		if (soggettoForm.getMisura().getTipoMisura() != null) {
			if (soggettoForm.getMisura().getTipoMisura().getId() != null
					&& !Constants.NO_ITEM_SELECTED.equals(soggettoForm.getMisura().getTipoMisura().getId())) {
				normativeTipoMisura = getNormativeMisuraPersonaleNew(soggettoForm, null);
			}
		}
		if (mnv != null) {
			mnv.addObject("normativeTipoPrev", normativeTipoPrev);
			mnv.addObject("normativeMisPers", normativeTipoMisura);
		}
		if (map != null) {
			map.addAttribute("normativeTipoPrev", normativeTipoPrev);
			map.addAttribute("normativeMisPers", normativeTipoMisura);
		}
		// Fine Valorizzazione combo delle Normative
	}

	public void setBeneService(BeneService beneServiceParam) {

		beneService = beneServiceParam;
	}

	public void setProcedimentoValidation(ProcedimentoValidation procedimentoValidationParam) {

		procedimentoValidation = procedimentoValidationParam;
	}

	@RequestMapping(value = "/procedimento/soggetto/difensoriAssociatiModifica.htm", method = RequestMethod.POST, params = {
			"VisualizzaDaReginde" })
	public ModelAndView visualizzaDifensoreDaReginde(DifensoreSoggettoForm difensoreSoggettoForm,
			BindingResult result) {

		caricaValoriDettaglioSoggetto(difensoreSoggettoForm.getSoggettoForm());
		difensoreSoggettoForm = procedimentoService.caricaDifensoreAssociatoSoggetto(difensoreSoggettoForm);

		return new ModelAndView("procedimento/soggetto/difensoriAssociatiModifica", "difensoreSoggettoForm",
				difensoreSoggettoForm);
	}

	// ***************** GESTIONE SOGGETTO ****************//
	@RequestMapping(value = "/procedimento/soggetto/visualizzaDirettiInteressati.htm", method = RequestMethod.GET)
	public ModelAndView visualizzaSoggettiDirettiInteressati(SoggettoForm soggettoForm) {

		if (request.getSession().getAttribute("SovrascitturaIndietro") != null
				&& !"".equals(request.getSession().getAttribute("SovrascitturaIndietro"))) {
			// Se provengo dall'inserimento di una nuova cauzione devo tornare li
			ModelAndView mav = new ModelAndView(
					"redirect:" + request.getSession().getAttribute("SovrascitturaIndietro"));
			request.getSession().setAttribute("SovrascitturaIndietro", "");
			return mav;
		}

		if (isInterveniente(soggettoForm)) {
			addBreadcrumbs("GET", "/procedimento/soggetto/visualizzaIntervenienti.htm", null);
		} else {
			addBreadcrumbs("GET", "/procedimento/soggetto/visualizzaDirettiInteressati.htm", null);
		}

		ProcedimentoForm procedimentoForm = (ProcedimentoForm) request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO);
		String idStatoProc = null;

		// svuota i dati del soggetto //
		soggettoForm.setIdSoggetto(null);
		soggettoForm.setIdVersione(null);
		// fine svuota dati del soggetto //

		if (procedimentoForm.getStatoProcedimento() != null
				&& procedimentoForm.getStatoProcedimento().getId() != null) {
			idStatoProc = procedimentoForm.getStatoProcedimento().getId();
		}

		if (Constants.STATOPROC_ISTRUTTORIA.equals(idStatoProc)
				|| Constants.STATOPROC_IN_ACQUISIZ_PROPOSTA.equals(idStatoProc)) {
			request.setAttribute("permessoMisura", true);
		}
		if (request.getSession().getAttribute(Constants.ATTO) != null)
			request.getSession().removeAttribute(Constants.ATTO);
		request.setAttribute("statoProcedimento", idStatoProc);
		long idProcedimento = procedimentoForm.getIdProcedimento();
		ModelAndView mnv = new ModelAndView("/procedimento/soggetto/direttiInteressati", "soggettoForm",
				soggettoForm);
		List<SoggettoForm> soggetti = null;

		if (procedimentoForm.getTipoProcedimento() != null
				&& (Long.toString(Constants.TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM)
						.equals(procedimentoForm.getTipoProcedimento().getId())
						|| Long.toString(Constants.TIPOPROC_CORREZIONE_ERRORI_CAM)
								.equals(procedimentoForm.getTipoProcedimento().getId()))) {
			Integer idUffOwn = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
			List<IntestatarioDto> listaIntestatari = procedimentoServiceCam.getIntestatari(idUffOwn,
					procedimentoForm.getIdProcedimento());
			mnv.addObject("listaIntestatari", listaIntestatari);
		} else {
			if ((procedimentoForm.getTipoProcedimento() != null) && (Constants.TIPOPROC_AGGRAVAMENTO_PERSONALE
					.equals(procedimentoForm.getTipoProcedimento().getId())
					|| Constants.TIPOPROC_RIESAME_PROVV_QUESTURA
							.equals(procedimentoForm.getTipoProcedimento().getId()))) {
				soggetti = procedimentoService.soggettiDirettiInteressatiDelProcedimento(
						procedimentoForm.getIdProcedimento(), null, Constants.Flag.S);
			} else {
				String ruoloRicerca = Constants.RUOLO_DIRETTO;
				if (isInterveniente(soggettoForm)) {
					ruoloRicerca = Constants.RUOLO_INTERVENTIENTE;
					soggettoForm.setIdRuolo(Constants.ID_RUOLO_INTERVENIENTE);
					mnv = new ModelAndView("/procedimento/soggetto/intervenienti", "soggettoForm",
							soggettoForm);
				}

				// Segnalazione EMS-140
				if (procedimentoForm.getTipoProcedimento() != null) {
					if (!isInterveniente(soggettoForm)
							&& Long.toString(Constants.TIPOPROC_TERZO_INTERVENIENTE_CAM)
									.equals(procedimentoForm.getTipoProcedimento().getId()))
						ruoloRicerca = Constants.RUOLO_INTERVENTIENTE;
				}

				soggetti = procedimentoService.soggettiDirettiInteressatiDelProcedimento(idProcedimento,
						ruoloRicerca, Constants.Flag.S);
				if (isInterveniente(soggettoForm) && soggetti.size() > 0) {
					// per ogni soggetto interveniente trovato vado a vedere se esistono soggetti associati
					List<SoggettoForm> soggettiInv = new ArrayList<SoggettoForm>();
					for (SoggettoForm soggettoInv : soggetti) {
						long idSogInv = soggettoInv.getIdSoggetto();
						List<SoggettoForm> soggettiAssociati = procedimentoService
								.soggettiDirettiInteressatiInvDelProcedimento(idProcedimento, idSogInv);
						if (soggettiAssociati.size() > 0) {
							soggettoInv.setFlgIntervSoggAssociati("SI");
						} else {
							soggettoInv.setFlgIntervSoggAssociati("NO");
						}
						soggettiInv.add(soggettoInv);
					}
					if (soggettiInv.size() > 0) {
						soggetti = soggettiInv;
					}
				}
			}

			mnv.addObject("risultatiRicerca", soggetti);
		}
		if (request.getSession().getAttribute("RuoloSession") != null
				&& request.getSession().getAttribute("RuoloSession").equals(Constants.RUOLO_TERZO)) {
			request.getSession().removeAttribute("RuoloSession");
		}
		return mnv;
	}

	@RequestMapping(value = "/procedimento/soggetto/visualizzaIntervenienti.htm", method = RequestMethod.GET)
	public ModelAndView visualizzaIntervenienti(SoggettoForm soggettoForm, HttpServletRequest request) {

		soggettoForm.setIdRuolo(Constants.ID_RUOLO_INTERVENIENTE);
		soggettoForm.setIdSoggettoInterveniente(null);
		soggettoForm.setIdSoggetto(null);
		soggettoForm.setIdVersione(null);
		soggettoForm.setTipoSoggetto(null);
		return visualizzaSoggettiDirettiInteressati(soggettoForm);
	}

	@RequestMapping(value = "/procedimento/soggetto/visualizzaDirettiInteressati.htm", method = RequestMethod.POST, params = {
			"VisualizzaAssociaSoggetti" })
	public ModelAndView visualizzaAssociaSoggettiIntervenienti(SoggettoForm soggettoForm,
			BindingResult result) {

		if (isInterveniente(soggettoForm)) {
			soggettoForm.setIdSoggetto(soggettoForm.getIdSoggettoInterveniente());
			soggettoValidation.validateGestioneSoggettoInterveniente(soggettoForm, result, request);
			if (result.hasErrors()) {
				return visualizzaIntervenienti(soggettoForm, request);
			}
		}

		long idSogInv = soggettoForm.getIdSoggettoInterveniente();
		addBreadcrumbs();

		ProcedimentoForm procedimentoForm = (ProcedimentoForm) request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO);
		String idStatoProc = null;

		caricaValoriDettaglioSoggetto(soggettoForm);

		// svuota i dati del soggetto //
		// soggettoForm.setIdSoggetto(null);
		// soggettoForm.setIdVersione(null);
		// fine svuota dati del soggetto //

		if (procedimentoForm.getStatoProcedimento() != null
				&& procedimentoForm.getStatoProcedimento().getId() != null) {
			idStatoProc = procedimentoForm.getStatoProcedimento().getId();
		}

		if (request.getSession().getAttribute(Constants.ATTO) != null)
			request.getSession().removeAttribute(Constants.ATTO);
		request.setAttribute("statoProcedimento", idStatoProc);
		long idProcedimento = procedimentoForm.getIdProcedimento();
		ModelAndView mnv = new ModelAndView("/procedimento/soggetto/associaDirettiInteressati",
				"soggettoForm", soggettoForm);

		List<SoggettoForm> soggettiDaAssociare = procedimentoService
				.soggettiDirettiInteressatiDelProcedimento(idProcedimento, Constants.RUOLO_DIRETTO,
						Constants.Flag.S);
		List<SoggettoForm> soggettiAssociati = procedimentoService
				.soggettiDirettiInteressatiInvDelProcedimento(idProcedimento, idSogInv);

		for (int i = 0; i < soggettiAssociati.size(); i++) {
			long idSoggettoAssociato = soggettiAssociati.get(i).getIdSoggetto();
			for (int j = 0; j < soggettiDaAssociare.size(); j++) {
				long idSoggettoDaAssociare = soggettiDaAssociare.get(j).getIdSoggetto();
				if (idSoggettoDaAssociare == idSoggettoAssociato) {
					soggettiDaAssociare.remove(j);
					j--;
				}
			}
		}
		mnv.addObject("soggettiDaAssociare", soggettiDaAssociare);
		mnv.addObject("soggettiAssociati", soggettiAssociati);

		return mnv;
	}

	@RequestMapping(value = "/procedimento/soggetto/visualizzaDirettiInteressati.htm", method = RequestMethod.POST, params = {
			"AssociaSoggetto" })
	public ModelAndView associaRelSoggettiInterveniente(SoggettoForm soggettoForm, BindingResult result) {

		soggettoValidation.validateCheckBoxEliminaAssociazioneIntervenienti(soggettoForm, result, request);
		if (!result.hasErrors()) {
			ProcedimentoForm procedimentoForm = (ProcedimentoForm) request.getSession()
					.getAttribute(Constants.ID_PROCEDIMENTO);
			long idProcedimento = procedimentoForm.getIdProcedimento();
			Integer idUffOwn = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
			String descUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);
			long idSogInv = soggettoForm.getIdSoggettoInterveniente();
			long idSogInt = soggettoForm.getIdSoggetto();
			TRelSoginvSogint rec = new TRelSoginvSogint();
			TRelSoginvSogintId pk = new TRelSoginvSogintId();
			pk.setCodiKIdProc(idProcedimento);
			pk.setCodiKIdSoggInt(idSogInt);
			pk.setCodiKIdSoggInv(idSogInv);
			pk.setCodiKIdUffOwner(idUffOwn);
			rec.setId(pk);
			rec.setDescUtente(descUtente);
			rec.setNote(soggettoForm.getNote());
			procedimentoService.associaRelSoggettoInterveniente(rec);
			soggettoForm.setNote(null);
		}
		return visualizzaAssociaSoggettiIntervenienti(soggettoForm, result);
	}

	@RequestMapping(value = "/procedimento/soggetto/visualizzaDirettiInteressati.htm", method = RequestMethod.POST, params = {
			"EliminaAssociazioneSoggetto" })
	public ModelAndView eliminaRelSoggettiInterveniente(SoggettoForm soggettoForm, BindingResult result) {

		soggettoValidation.validateCheckBoxEliminaAssociazioneIntervenienti(soggettoForm, result, request);
		if (!result.hasErrors()) {
			ProcedimentoForm procedimentoForm = (ProcedimentoForm) request.getSession()
					.getAttribute(Constants.ID_PROCEDIMENTO);
			long idProcedimento = procedimentoForm.getIdProcedimento();
			Integer idUffOwn = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
			long idSogInv = soggettoForm.getIdSoggettoInterveniente();
			long idSogInt = soggettoForm.getIdSoggetto();
			TRelSoginvSogintId pk = new TRelSoginvSogintId();
			pk.setCodiKIdProc(idProcedimento);
			pk.setCodiKIdSoggInt(idSogInt);
			pk.setCodiKIdSoggInv(idSogInv);
			pk.setCodiKIdUffOwner(idUffOwn);
			procedimentoService.eliminaRelSoggettoInterveniente(pk);
		}
		soggettoForm.setIdSoggetto(soggettoForm.getIdSoggettoInterveniente());
		return visualizzaAssociaSoggettiIntervenienti(soggettoForm, result);
	}

	@RequestMapping(value = "/procedimento/soggetto/visualizzaDirettiInteressati.htm", method = RequestMethod.POST, params = {
			"VisualizzaBeniAssociati" })
	public ModelAndView visualizzaAssociaBeneSoggettiIntervenienti(SoggettoForm soggettoForm,
			BindingResult result, ModelMap modelMap) {

		if (isInterveniente(soggettoForm)) {
			soggettoValidation.validateGestioneSoggettoInterveniente(soggettoForm, result, request);
			if (result.hasErrors()) {
				return visualizzaIntervenienti(soggettoForm, request);
			}
		}

		long idSogInv = soggettoForm.getIdSoggettoInterveniente();
		addBreadcrumbs();

		ProcedimentoForm procedimentoForm = (ProcedimentoForm) request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO);
		long idProcedimento = procedimentoForm.getIdProcedimento();
		Integer idUffOwn = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);

		// svuota i dati del soggetto //
		soggettoForm.setIdSoggetto(null);
		soggettoForm.setIdVersione(null);
		// fine svuota dati del soggetto //

		BeneSoggettoForm beneSoggettoForm = new BeneSoggettoForm();
		beneSoggettoForm.setSoggetto(soggettoForm);

		BeneForm beneForm = new BeneForm();
		List<BeneForm> beniAssociati = beneService.listaBeniPerProcedimento(0, idUffOwn, idProcedimento,
				beneForm, idSogInv, null);
		List<BeneForm> beniDaAssociare = beneService.listaBeniPerProcedimento(0, idUffOwn, idProcedimento,
				beneForm);

		for (int i = 0; i < beniAssociati.size(); i++) {
			long idBeneAssociato = beniAssociati.get(i).getIdBene();
			for (int j = 0; j < beniDaAssociare.size(); j++) {
				long idBeneDaAssociare = beniDaAssociare.get(j).getIdBene();
				if (idBeneDaAssociare == idBeneAssociato) {
					beniDaAssociare.remove(j);
					j--;
				}
			}
		}

		modelMap.addAttribute("beniAssociati", beniAssociati);
		modelMap.addAttribute("beniDaAssociare", beniDaAssociare);

		modelMap.addAttribute("beneSoggettoForm", beneSoggettoForm);

		ModelAndView mnv = new ModelAndView("/procedimento/soggetto/associaBeniIntervenienti");
		return mnv;
	}

	@RequestMapping(value = "/procedimento/soggetto/visualizzaDirettiInteressati.htm", method = RequestMethod.POST, params = {
			"AssociaBeneInterveniente" })
	public ModelAndView associaRelBeneSoggettiInterveniente(BeneSoggettoForm beneSoggettoForm,
			BindingResult result, ModelMap modelMap) {

		ProcedimentoForm procedimentoForm = (ProcedimentoForm) request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO);

		long idProcedimento = procedimentoForm.getIdProcedimento();
		Integer idUffOwn = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		String descUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);
		long idSogInv = beneSoggettoForm.getSoggetto().getIdSoggettoInterveniente();

		if (beneSoggettoForm.getListaIdCheckati() != null
				&& !beneSoggettoForm.getListaIdCheckati().isEmpty()) {
			for (int i = 0; i < beneSoggettoForm.getListaIdCheckati().size(); i++) {
				long codiKIdBene = beneSoggettoForm.getListaIdCheckati().get(i);

				TRelSoginvBene rec = new TRelSoginvBene();
				TRelSoginvBeneId pk = new TRelSoginvBeneId();
				pk.setCodiKIdProc(idProcedimento);
				pk.setCodiKIdBene(codiKIdBene);
				pk.setCodiKIdSoggInv(idSogInv);
				pk.setCodiKIdUffOwner(idUffOwn);

				rec.setId(pk);
				rec.setDescUtente(descUtente);
				procedimentoService.associaRelBeneSoggettoInterveniente(rec);
			}
		}

		return visualizzaAssociaBeneSoggettiIntervenienti(beneSoggettoForm.getSoggetto(), result, modelMap);
	}

	@RequestMapping(value = "/procedimento/soggetto/visualizzaDirettiInteressati.htm", method = RequestMethod.POST, params = {
			"EliminaBeneInterveniente" })
	public ModelAndView eliminaRelBeneSoggettiInterveniente(BeneSoggettoForm beneSoggettoForm,
			BindingResult result, ModelMap modelMap) {

		ProcedimentoForm procedimentoForm = (ProcedimentoForm) request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO);

		long idProcedimento = procedimentoForm.getIdProcedimento();
		Integer idUffOwn = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		long idSogInv = beneSoggettoForm.getSoggetto().getIdSoggettoInterveniente();

		if (beneSoggettoForm.getListaIdCheckati() != null
				&& !beneSoggettoForm.getListaIdCheckati().isEmpty()) {
			for (int i = 0; i < beneSoggettoForm.getListaIdCheckati().size(); i++) {
				long codiKIdBene = beneSoggettoForm.getListaIdCheckati().get(i);

				TRelSoginvBeneId pk = new TRelSoginvBeneId();
				pk.setCodiKIdProc(idProcedimento);
				pk.setCodiKIdBene(codiKIdBene);
				pk.setCodiKIdSoggInv(idSogInv);
				pk.setCodiKIdUffOwner(idUffOwn);

				procedimentoService.eliminaRelBeneSoggettoInterveniente(pk);
			}
		}

		return visualizzaAssociaBeneSoggettiIntervenienti(beneSoggettoForm.getSoggetto(), result, modelMap);
	}

	@RequestMapping(value = "/procedimento/soggetto/visualizzaDirettiInteressati.htm", method = RequestMethod.POST, params = {
			"EliminaInterveniente" })
	public ModelAndView eliminaInterveniente(SoggettoForm soggettoForm, BindingResult result) {

		if (isInterveniente(soggettoForm)) {
			soggettoValidation.validateGestioneSoggettoInterveniente(soggettoForm, result, request);
			if (result.hasErrors()) {
				return visualizzaIntervenienti(soggettoForm, request);
			}
		}

		ProcedimentoForm procedimentoForm = (ProcedimentoForm) request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO);

		long idProcedimento = procedimentoForm.getIdProcedimento();
		Integer idUffOwn = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		long idSogInv = soggettoForm.getIdSoggettoInterveniente();

		String msg = procedimentoService.eliminaSogInv(idProcedimento, idUffOwn, idSogInv);

		if ("OK".equals(msg)) {
			super.setMessage(Constants.SUCCESS_MESSAGE, "success", null, false);
		} else if ("UDIENZE".equals(msg)) {
			super.setMessage(Constants.MESSAGE, "error.soggetto.interveniente.udienze", null, false);
		} else if ("IMPUGNAZIONI".equals(msg)) {
			super.setMessage(Constants.MESSAGE, "error.soggetto.interveniente.impugnazioni", null, false);
		} else if ("DIFENSORI".equals(msg)) {
			super.setMessage(Constants.MESSAGE, "error.soggetto.interveniente.difensori", null, false);
		}

		return visualizzaIntervenienti(soggettoForm, request);
	}

	@RequestMapping(value = "/procedimento/soggettiInteressati/dettaglio.htm", method = RequestMethod.POST, params = {
			"Versioni" })
	public ModelAndView visualizzaVersioniSoggetti(SoggettoForm soggettoForm, BindingResult result) {

		addBreadcrumbs("Dettaglio", request);

		ProcedimentoForm procedimentoForm = (ProcedimentoForm) request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO);

		ModelAndView mnv = new ModelAndView("/procedimento/soggetto/versioniSoggetto", "soggettoForm",
				soggettoForm);
		List<SoggettoForm> soggetti = null;

		// procedimentoService.setIdSoggettoIdVersioneTipoSoggFromChiave(soggettoForm);
		Integer idUffOwn = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		long idProc = procedimentoForm.getIdProcedimento();
		soggettoForm.setIdUfficio(idUffOwn);
		soggettoForm.setIdProcedimento(idProc);

		// Imposto a null la versione
		soggettoForm.setIdVersione(null);
		//
		long idSogg = soggettoForm.getIdSoggetto();

		// svuota i dati del soggetto //
		soggettoForm.setIdVersione(null);
		// fine svuota dati del soggetto //

		if ((procedimentoForm.getTipoProcedimento() != null) && (Constants.TIPOPROC_AGGRAVAMENTO_PERSONALE
				.equals(procedimentoForm.getTipoProcedimento().getId())
				|| Constants.TIPOPROC_RIESAME_PROVV_QUESTURA
						.equals(procedimentoForm.getTipoProcedimento().getId()))) {
			soggetti = procedimentoService.soggettiDirettiInteressatiDelProcedimentoVer(idProc, null,
					Constants.Flag.S, idSogg, idUffOwn, null);
		} else {
			if (soggettoForm.getDescrRuolo() != null
					&& Constants.RUOLO_TERZO.equals(soggettoForm.getDescrRuolo())) {
				soggetti = procedimentoService.soggettiDirettiInteressatiDelProcedimentoVer(idProc,
						Constants.RUOLO_TERZO, Constants.Flag.S, idSogg, idUffOwn, null);
			} else {
				soggetti = procedimentoService.soggettiDirettiInteressatiDelProcedimentoVer(idProc,
						Constants.RUOLO_DIRETTO, Constants.Flag.S, idSogg, idUffOwn, null);
			}
		}

		mnv.addObject("risultatiRicerca", soggetti);
		if (request.getSession().getAttribute("RuoloSession") != null
				&& request.getSession().getAttribute("RuoloSession").equals(Constants.RUOLO_TERZO)) {
			request.getSession().removeAttribute("RuoloSession");
		}

		return mnv;
	}

	@RequestMapping(value = "/procedimento/soggetto/decisioneErrore.htm", method = RequestMethod.POST, params = {
			"verificaCF" })
	public String controllaCFSoggettoFisico(@Valid SoggettoForm soggettoForm, BindingResult result,
			ModelMap modelMap) {

		String msgErrore = "";

		if ("".equals(soggettoForm.getNome()) || soggettoForm.getNome() == null
				|| "".equals(soggettoForm.getCognome()) || soggettoForm.getCognome() == null) {
			msgErrore = "errors.campi.controllo.codicefiscale.nomeocognome";
		} else {
			// Invoco il WS
			EsitoControlloCFePIWrapper esito = agenziaEntrateService.controllaDatiAnagraficiPF(soggettoForm);
			if (esito.isInvocazioneFallita()) {
				super.setMessage(Constants.MESSAGE, "errors.campi.controllo.codicefiscale", esito.getErrore(),
						false);
			} else {
				if (soggettoForm.getCodiceFiscale() != null && !"".equals(soggettoForm.getCodiceFiscale())) {
					// Compara CF
					if (soggettoForm.getCodiceFiscale().equals(esito.getCodiceFiscale())) {
						super.setMessage(Constants.SUCCESS_MESSAGE,
								"success.operation.codicefiscale.corretto", " ", true);
					} else {
						super.setMessage(Constants.MESSAGE, "errors.campi.controllo.codicefiscale.diverso",
								esito.getCodiceFiscale(), false);
					}
				} else {
					soggettoForm.setCodiceFiscale(esito.getCodiceFiscale());
				}
			}
		}

		if (!"".equals(msgErrore)) {
			super.setMessage(Constants.MESSAGE, msgErrore, null, false);
		}
		return "procedimento/atto/decisioneErroreMateriale";
	}

	@RequestMapping(value = "/procedimento/soggetto/decisioneErrore.htm", method = RequestMethod.POST, params = {
			"SalvaDecisioneErroreMateriale" })
	public String salvaDecisorioErroreMateriale(@Valid SoggettoForm soggettoForm, BindingResult result,
			ModelMap modelMap) {

		ProcedimentoForm procedimento = (ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO));
		long idProcedimento = procedimento.getIdProcedimento();
		AttoForm atto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO)));
		long idAtto = atto.getCodAtto();
		int idUffOwn = recuperaIdUfficioUtente();
		String descUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);

		if (atto.getCategoriaAtto() != null && Constants.SENTENZA_SU_RICORSO_II_GRADO_DA_TRIB
				.equalsIgnoreCase(atto.getCategoriaAtto().getId())) {
			if (soggettoForm.getDecisioneErroreMaterialeIIIGrado() != null && Constants.NO_ITEM_SELECTED
					.equalsIgnoreCase(soggettoForm.getDecisioneErroreMaterialeIIIGrado().getId())) {
				result.rejectValue("decisioneErroreMaterialeIIIGrado.id", "NotNull");
			}

			if (soggettoForm.getNumeAnnoSentenza() == null
					|| "".equalsIgnoreCase(soggettoForm.getNumeAnnoSentenza())) {
				result.rejectValue("numeAnnoSentenza", "NotNull");
			}

			if (soggettoForm.getNumeSentenza() == null
					|| "".equalsIgnoreCase(soggettoForm.getNumeSentenza())) {
				result.rejectValue("numeSentenza", "NotNull");
			}

			if (soggettoForm.getDataRicezioneSentenza() == null) {
				result.rejectValue("dataRicezioneSentenza", "NotNull");
			}

			String descAltro = soggettoForm.getDescAltro3();
			ComboForm decIIIGrado = soggettoForm.getDecisioneErroreMaterialeIIIGrado();
			String numero = soggettoForm.getNumeSentenza();
			String Anno = soggettoForm.getNumeAnnoSentenza();
			java.util.Date data = soggettoForm.getDataRicezioneSentenza();
			soggettoForm = procedimentoServiceCam.getIntestatarioProc(idUffOwn, idProcedimento);
			soggettoForm.setDescAltro3(descAltro);
			soggettoForm.setDecisioneErroreMaterialeIIIGrado(decIIIGrado);
			soggettoForm.setNumeSentenza(numero);
			soggettoForm.setNumeAnnoSentenza(Anno);
			soggettoForm.setDataRicezioneSentenza(data);

		} else {
			if (soggettoForm.getDecisioneErroreMaterialeIGrado() != null && Constants.NO_ITEM_SELECTED
					.equalsIgnoreCase(soggettoForm.getDecisioneErroreMaterialeIGrado().getId())) {
				result.rejectValue("decisioneErroreMaterialeIGrado.id", "NotNull");
			}

			if (soggettoForm != null && soggettoForm.getDecisioneErroreMaterialeIGrado() != null
					&& "257".equalsIgnoreCase(soggettoForm.getDecisioneErroreMaterialeIGrado().getId())) {
				if (soggettoForm != null && "F".equalsIgnoreCase(soggettoForm.getTipoSoggetto())) {
					soggettoValidation.validateNuovoSoggettoFisicoObbl(soggettoForm, result);
				} else {
					soggettoValidation.validateSoggettoGiuridico(soggettoForm, result);
				}

			} else {
				String descAltro = soggettoForm.getDescAltro1();
				ComboForm decIGrado = soggettoForm.getDecisioneErroreMaterialeIGrado();
				soggettoForm = procedimentoServiceCam.getIntestatarioProc(idUffOwn, idProcedimento);
				soggettoForm.setDescAltro1(descAltro);
				soggettoForm.setDecisioneErroreMaterialeIGrado(decIGrado);
			}
		}

		if (result.hasErrors()) {
			super.setMessage(Constants.MESSAGE, "errors.campi.controllo", null, false);
			SoggettoForm soggetto = new SoggettoForm();
			if (atto.getCategoriaAtto() != null && Constants.SENTENZA_SU_RICORSO_II_GRADO_DA_TRIB
					.equalsIgnoreCase(atto.getCategoriaAtto().getId())) {
				soggetto = procedimentoServiceCam.getIntestatarioProc(idUffOwn, idProcedimento);
			} else {
				soggetto = soggettoForm;
			}
			return onchangeComboDecisorio(soggetto, result, modelMap, "S");
		}

		if (soggettoForm.getStatoNascita() != null
				&& !"1".equalsIgnoreCase(soggettoForm.getStatoNascita().getId())
				&& !"-1".equalsIgnoreCase(soggettoForm.getStatoNascita().getId())) {

			if (soggettoForm.getComuneNascita() != null) {
				soggettoForm
						.setDescrizioneComuneEsteroNascita(soggettoForm.getComuneNascita().getDescrizione());
				soggettoForm.setComuneNascita(new ComboForm());
			}
		}

		if (soggettoForm.getStatoSedeLegale() != null
				&& !"1".equalsIgnoreCase(soggettoForm.getStatoSedeLegale().getId())
				&& !"-1".equalsIgnoreCase(soggettoForm.getStatoSedeLegale().getId())) {

			if (soggettoForm.getComuneSedeLegale() != null) {
				soggettoForm.setDescrizioneComuneEsteroSedeLegale(
						soggettoForm.getComuneSedeLegale().getDescrizione());
				soggettoForm.setComuneSedeLegale(new ComboForm());
			}
		}

		if (atto.getCategoriaAtto() != null && Constants.SENTENZA_SU_RICORSO_II_GRADO_DA_TRIB
				.equalsIgnoreCase(atto.getCategoriaAtto().getId())) {
			attoService.salvaDecErroreMateriale(idAtto, idProcedimento, descUtente, idUffOwn,
					"SENTENZA_CASSAZIONE", soggettoForm);
		} else {
			attoService.salvaDecErroreMateriale(idAtto, idProcedimento, descUtente, idUffOwn, "DECISORIO",
					soggettoForm);
		}

		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", null, false);

		return onchangeComboDecisorio(soggettoForm, result, modelMap, null);
	}

	// Segnalazione EMS-102
	@RequestMapping(value = "/procedimento/soggetto/decisioneErrore.htm", method = RequestMethod.POST, params = {
			"SalvaDecisioneDivietiQuestore" })
	public String salvaDecisorioOpposizioneDivietiQuestore(
			@ModelAttribute("soggettoForm") SoggettoForm soggettoForm, BindingResult result, ModelMap map) {

		AttoForm currentAttoDecisorioIIIGrado = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
		soggettoValidation.validateSalvaSentenzaOppDivietiQuestore(soggettoForm, result, request);

		if (result.hasErrors()) {
			// procedimentoService.setIdSoggettoIdVersioneTipoSoggFromChiave(soggettoForm);
			// AttoForm currentAtto = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
			//
			// soggettoForm.setIdUfficio((Integer)
			// request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE));
			// soggettoForm.setIdProcedimento(((ProcedimentoForm)
			// (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento());
			// soggettoForm.setIdAtto(currentAtto.getCodAtto());
			//
			// soggettoForm = misuraService.caricaMisuraPersonaleIIIGrado(soggettoForm);
			//
			// String tipologiaPrev = soggettoForm.getTipologiaPrevenzione().getDescrizione();
			//
			// if ("F".equals(soggettoForm.getTipoSoggetto())) {
			// soggettoForm =
			// procedimentoService.caricaValoriDettaglioSoggettoFisicoProcedimento(soggettoForm);
			// soggettoForm.setTipoSoggetto(Constants.SOGGETTO_FISICO);
			// if (soggettoForm.getTipologiaPrevenzione() != null &&
			// soggettoForm.getTipologiaPrevenzione().getDescrizione() == null) {
			// soggettoForm.getTipologiaPrevenzione().setDescrizione(tipologiaPrev);
			// }
			// } else {
			// soggettoForm =
			// procedimentoService.caricaValoriDettaglioSoggettoGiuridicoProcedimento(soggettoForm);
			// soggettoForm.setTipoSoggetto(Constants.SOGGETTO_GIURIDICO);
			// if (soggettoForm.getTipologiaPrevenzione() != null &&
			// soggettoForm.getTipologiaPrevenzione().getDescrizione() == null) {
			// soggettoForm.getTipologiaPrevenzione().setDescrizione(tipologiaPrev);
			// }
			// }
			//
			// // Carico le informazioni da T_REL_PROC_SOGINT
			// soggettoForm = procedimentoService.caricaValoriGratuitoPatrocinioDelSoggetto(soggettoForm);
			// soggettoForm.setTipologiaPrevenzioneDb(soggettoForm.getTipologiaPrevenzione());
			//
			//
			// // Carico nel ModelAndView le Normative
			// caricaNormative(soggettoForm, null, map);
			// ProcedimentoForm procedimento = recuperaProcedimento();
			// List<ComboForm> list = commonService.listaPrescrizioniMisuraPersonale();
			// map.addAttribute("listaPrescrizioni", list);
			//
			//
			// long idCategoriaAtto = Long.valueOf(Constants.SENTENZA_SU_RICORSO_II_GRADO);
			//
			// if(String.valueOf(request.getSession().getAttribute("idTipoUfficio")).equals(Constants.TIPO_UFFICIO_TRIBUNALE)){
			// idCategoriaAtto = Long.valueOf(Constants.SENTENZA_SU_RICORSO_II_GRADO_DA_TRIB);
			// }
			//
			//
			// if (soggettoForm.getMisura() != null && soggettoForm.getMisura().getCauzione() != null
			// && soggettoForm.getMisura().getCauzione().equalsIgnoreCase("S")) {
			// map.addAttribute("importoCauzione", true);
			// }
			//
			// String carattereMisura = "X";
			// List<ComboForm> esiti = null;
			// long idUfficio = 1l; // per ufficio di cassazione
			//
			// esiti =procedimentoService.getEsiti(carattereMisura, idCategoriaAtto, idUfficio);
			// map.addAttribute("esiti", esiti);
			// map.addAttribute("soggettoForm", soggettoForm);

			super.setMessage(Constants.MESSAGE, "errors.campi.controllo", null, false);
			return "procedimento/atto/decisioneOpposizioneDivietiQuestore";
		}

		ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO);

		long vidProc = procedimento.getIdProcedimento();
		long tipoProc = Long.valueOf(procedimento.getTipoProcedimento().getId()).longValue();
		Integer vidUffOwn = recuperaIdUfficioUtente();
		String vcodUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);

		soggettoForm.setIdAtto(currentAttoDecisorioIIIGrado.getCodAtto());
		misuraService.salvaSentenzaOppDivietiQuestore(vidProc, tipoProc, vidUffOwn, 0, vcodUtente,
				soggettoForm, currentAttoDecisorioIIIGrado.getCodAtto());

		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation", "INSERIMENTO", false);
		return attoController.copertinaAtto(new AttoForm(), result, request, map);
	}

	@RequestMapping(value = "/procedimento/soggetto/decisioneErrore.htm", method = RequestMethod.POST)
	public String onchangeComboDecisorio(@Valid SoggettoForm soggettoForm, BindingResult result,
			ModelMap modelMap, String errore) {

		ProcedimentoForm procedimento = (ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO));
		long idProcedimento = procedimento.getIdProcedimento();
		AttoForm atto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO)));
		long idAtto = atto.getCodAtto();
		int idUffOwn = recuperaIdUfficioUtente();
		String codiceUtente = recuperaIdCodiceUtente();

		List<ComboForm> esiti = procedimentoService.getEsiti("EM",
				Long.parseLong(Constants.DECRETO_DECISORIO_I_GRADO_CAMERE), 0);
		modelMap.addAttribute("esitiDecisione", esiti);

		SoggettoForm soggetto = soggettoForm;

		if (!"S".equalsIgnoreCase(errore))
			soggetto = procedimentoServiceCam.getIntestatarioProc(idUffOwn, idProcedimento);

		List<ComboForm> esiti3 = null;
		if (atto.getCategoriaAtto() != null && Constants.SENTENZA_SU_RICORSO_II_GRADO_DA_TRIB
				.equalsIgnoreCase(atto.getCategoriaAtto().getId())) {

			AttoForm attotmp = attoService.dettaglioAtto(idUffOwn, idAtto, idProcedimento, codiceUtente);
			if (attotmp != null) {
				soggetto.setDataRicezioneSentenza(attotmp.getDataRicezione());
				soggetto.setNumeAnnoSentenza(attotmp.getNumeAnnoAtto());
				soggetto.setNumeSentenza(attotmp.getNumeAtto());
			}

			esiti3 = procedimentoService.getEsiti("X",
					Long.parseLong(Constants.SENTENZA_SU_RICORSO_II_GRADO_DA_TRIB), 0);

			TRelAttoIstGenId id = new TRelAttoIstGenId();
			id.setCodiKIdAtto(idAtto);
			id.setCodiKIdProc(idProcedimento);
			id.setCodiKIdUffOwner(idUffOwn);
			TRelAttoIstGen istGen = beneService.getDecisorioBeniIstGen(id);

			if (istGen != null) {
				soggetto.setDescAltro3(istGen.getDescAltro3());
			}

			List attiDecIgrado = attoService.getAttiByProcedimento(idProcedimento, 3l,
					Constants.DECRETO_DECISORIO_I_GRADO_CAMERE);
			if (attiDecIgrado != null && !attiDecIgrado.isEmpty()) {

				AttoForm attoDecIgrado = (AttoForm) attiDecIgrado.get(0);
				TRelAttoIstGenId idIgrado = new TRelAttoIstGenId();
				idIgrado.setCodiKIdAtto(attoDecIgrado.getCodAtto());
				idIgrado.setCodiKIdProc(idProcedimento);
				idIgrado.setCodiKIdUffOwner(idUffOwn);
				TRelAttoIstGen istGenIgrado = beneService.getDecisorioBeniIstGen(idIgrado);

				if (istGenIgrado != null) {
					ComboForm decisione = new ComboForm();
					if (istGenIgrado.getTTpEsitoByCodiFkIdEsito1() != null)
						decisione.setId(istGenIgrado.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito() + "");
					soggetto.setDecisioneErroreMaterialeIGrado(decisione);
				}
			}
			soggetto.setDecisioneErroreMaterialeIIIGrado(soggettoForm.getDecisioneErroreMaterialeIIIGrado());
		} else {
			TRelAttoIstGenId id = new TRelAttoIstGenId();
			id.setCodiKIdAtto(idAtto);
			id.setCodiKIdProc(idProcedimento);
			id.setCodiKIdUffOwner(idUffOwn);
			TRelAttoIstGen istGen = beneService.getDecisorioBeniIstGen(id);

			if (istGen != null) {
				soggetto.setDescAltro1(istGen.getDescAltro1());
			}

			soggetto.setDecisioneErroreMaterialeIGrado(soggettoForm.getDecisioneErroreMaterialeIGrado());
		}

		modelMap.addAttribute("esitiDecisione3", esiti3);

		modelMap.addAttribute("soggettoForm", soggetto);

		List<IntestatarioDto> listaIntestatari = procedimentoServiceCam.getIntestatari(idUffOwn,
				idProcedimento);
		SoggettoForm soggettoDaCorregere = null;
		if (listaIntestatari != null && !listaIntestatari.isEmpty()) {
			Iterator<IntestatarioDto> iter = listaIntestatari.iterator();
			IntestatarioDto intestatario = iter.next();
			soggettoDaCorregere = procedimentoServiceCam.getSoggettoDaCorregere(
					intestatario.getCODI_FK_ID_SOGG_INT().longValue(),
					intestatario.getCODI_FK_ID_VER().longValue(),
					intestatario.getCODI_FK_ID_UFF_OWNER().intValue());
		}
		modelMap.addAttribute("soggettoDaCorregere", soggettoDaCorregere);

		String readOnly = "true";
		String readOnlyMotivazione = "false";
		String readOnlyDecisione3Grado = null;

		if (atto.getCategoriaAtto() != null && Constants.SENTENZA_SU_RICORSO_II_GRADO_DA_TRIB
				.equalsIgnoreCase(atto.getCategoriaAtto().getId())) {
			readOnlyDecisione3Grado = "false";
			readOnly = "true";
			readOnlyMotivazione = "false";
		} else {
			if (soggettoForm != null && soggettoForm.getDecisioneErroreMaterialeIGrado() != null
					&& "257".equalsIgnoreCase(soggettoForm.getDecisioneErroreMaterialeIGrado().getId())) {
				readOnly = "false";
				readOnlyMotivazione = "true";
				soggetto.setDescAltro1(null);
			}
		}

		modelMap.addAttribute("readOnly", readOnly);
		modelMap.addAttribute("readOnlyMotivazione", readOnlyMotivazione);
		modelMap.addAttribute("readOnlyDecisioneIGrado", "false");
		modelMap.addAttribute("readOnlyDecisione3Grado", readOnlyDecisione3Grado);

		if (soggetto != null && soggetto.getDescrizioneComuneEsteroNascita() != null
				&& !"".equalsIgnoreCase(soggetto.getDescrizioneComuneEsteroNascita())) {
			ComboForm comune = new ComboForm();
			comune.setDescrizione(soggetto.getDescrizioneComuneEsteroNascita());
			soggetto.setComuneNascita(comune);
		}

		if (soggetto != null && soggetto.getDescrizioneComuneEsteroSedeLegale() != null
				&& !"".equalsIgnoreCase(soggetto.getDescrizioneComuneEsteroSedeLegale())) {
			ComboForm comune = new ComboForm();
			comune.setDescrizione(soggetto.getDescrizioneComuneEsteroSedeLegale());
			soggetto.setComuneNascita(comune);
		}

		return "procedimento/atto/decisioneErroreMateriale";
	}

	@RequestMapping(value = "/procedimento/soggetto/decisioneErrore.htm", method = RequestMethod.POST, params = {
			"verificaPI" })
	public String controllaPISoggettoFisico(@Valid SoggettoForm soggettoForm, BindingResult result,
			ModelMap modelMap) {

		String msgErrore = "";

		if ("".equals(soggettoForm.getRagioneSociale()) || soggettoForm.getRagioneSociale() == null) {
			msgErrore = "errors.campi.controllo.pi.ragionesociale";
		} else {
			// Invoco il WS
			EsitoControlloCFePIWrapper esito = agenziaEntrateService.controllaDatiAnagraficiPI(soggettoForm);
			if (esito.isInvocazioneFallita()) {
				super.setMessage(Constants.MESSAGE, "errors.campi.controllo.pi", esito.getErrore(), false);
			} else {
				if (soggettoForm.getPartitaIva() != null && !"".equals(soggettoForm.getPartitaIva())) {
					// Compara CF
					if (soggettoForm.getPartitaIva().equals(esito.getPartitaIva())) {
						super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.pi.corretta", " ",
								true);
					} else {
						super.setMessage(Constants.MESSAGE, "errors.campi.controllo.pi.diverso",
								esito.getPartitaIva(), false);
					}
				} else {
					soggettoForm.setPartitaIva(esito.getPartitaIva());
				}
			}
		}
		if (!"".equals(msgErrore)) {
			super.setMessage(Constants.MESSAGE, msgErrore, null, false);
		}
		return "procedimento/atto/decisioneErroreMateriale";

	}

	@RequestMapping(value = "/procedimento/soggettiInteressati/dettaglio.htm", method = RequestMethod.POST, params = {
			"DettaglioVersione" })
	public ModelAndView visualizzaDettaglioVersione(SoggettoForm soggettoForm, BindingResult result) {

		addBreadcrumbs("POST", "/procedimento/soggettiInteressati/dettaglio.htm", "Versioni");

		soggettoValidation.validateGestioneVersioneSoggetto(soggettoForm, result, request);
		if (result.hasErrors()) {
			return visualizzaVersioniSoggetti(soggettoForm, result);
		}
		ModelAndView mnv = null;
		soggettoForm.setIdUfficio((Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE));
		soggettoForm.setIdProcedimento(
				((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO)))
						.getIdProcedimento());

		if (Constants.SOGGETTO_FISICO.equalsIgnoreCase(soggettoForm.getTipoSoggetto())) {
			soggettoForm = soggettoService.caricaValoriDettaglioSoggFisVerCopertinaAtto(soggettoForm);
			soggettoForm.setTipoSoggetto(Constants.SOGGETTO_FISICO);
			mnv = new ModelAndView("procedimento/nuovo/dettaglioSoggettoFisicoProcedimento", "soggettoForm",
					soggettoForm);
		} else if (Constants.SOGGETTO_GIURIDICO.equalsIgnoreCase(soggettoForm.getTipoSoggetto())) {
			soggettoForm = soggettoService.caricaValoriDettaglioSoggGiuVerCopertinaAtto(soggettoForm);
			soggettoForm.setTipoSoggetto(Constants.SOGGETTO_GIURIDICO);
			mnv = new ModelAndView("procedimento/nuovo/dettaglioSoggettoGiuridicoProcedimento",
					"soggettoForm", soggettoForm);
		}
		mnv.addObject("provenienzaVersione", "S");
		// Segnalazione EMS-22
		request.setAttribute("contesto", "versioni");
		return mnv;
	}

	@RequestMapping(value = "/procedimento/soggetto/visualizzaDirettiInteressatiAtto.htm", method = RequestMethod.GET)
	public ModelAndView visualizzaSoggettiDirettiInteressatiAtto(SoggettoForm soggettoForm) {

		ProcedimentoForm procedimentoForm = (ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO));
		long idProcedimento = ((ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		AttoForm atto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO)));
		long idAtto = atto.getCodAtto();
		List<SoggettoForm> soggetti = null;

		ModelAndView mnv = new ModelAndView("/procedimento/soggetto/elencoDirettiInteressatiAtto",
				"soggettoForm", soggettoForm);

		if (procedimentoForm.getTipoProcedimento() != null
				&& (Long.toString(Constants.TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM)
						.equals(procedimentoForm.getTipoProcedimento().getId())
						|| Long.toString(Constants.TIPOPROC_CORREZIONE_ERRORI_CAM)
								.equals(procedimentoForm.getTipoProcedimento().getId()))) {
			Integer idUffOwn = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
			List<IntestatarioDto> listaIntestatari = procedimentoServiceCam.getIntestatari(idUffOwn,
					procedimentoForm.getIdProcedimento());
			mnv.addObject("listaIntestatari", listaIntestatari);
		} else {
			if (Constants.STATOATTO_IN_LAVORAZIONE_S.equals(atto.getStatoAtto().getId()))
				soggetti = procedimentoService.soggettiDirettiInteressatiDelProcedimento(idProcedimento,
						Constants.RUOLO_DIRETTO, Constants.Flag.S);
			else
				soggetti = attoService.getRicercaSoggettoAtto(idProcedimento, idAtto,
						(Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE),
						Constants.ID_RUOLO_DIRETTO, Constants.Flag.S);

			mnv.addObject("risultatiRicerca", soggetti);
		}

		if (isAttoSequestro(atto)) {
			List<SoggettoForm> escludiSoggettiSoloMisuraPersonale = escludiSoggettiSoloMisuraPersonale(
					soggetti);
			mnv.addObject("risultatiRicerca", escludiSoggettiSoloMisuraPersonale);
		}
		return mnv;
	}

	private List<SoggettoForm> escludiSoggettiSoloMisuraPersonale(List<SoggettoForm> soggetti) {

		List<SoggettoForm> res = new ArrayList<SoggettoForm>();
		if (!CollectionUtils.isEmpty(soggetti)) {
			for (SoggettoForm soggettoForm : soggetti) {
				if (!"PERSONALE".equals(soggettoForm.getMisura().getDescrizioneMisura())) {
					res.add(soggettoForm);
				}
			}
		}
		return res;
	}

	private boolean isAttoSequestro(AttoForm atto) {

		return ComparatorUtil.containsValue(atto.getCategoriaAtto().getId(),
				Constants.DECRETO_RIGETTO_TOTALE_SEQUESTRO, Constants.DECRETO_PROROGA_SEQUESTRO,
				Constants.DECRETO_PROROGA_SOSPENSIONE_TRIB, Constants.DECRETO_SEQUESTRO_URGENTE,
				Constants.DECRETO_SEQUESTRO_ANTICIPATO, Constants.DECRETO_SEQUESTRO_ORDINARIO,
				Constants.DECRETO_REVOCA_SEQUESTRO, Constants.DECRETO_SEQUESTRO_SOSPENSIONE_TRIB,
				Constants.DECRETO_SEQUESTRO_SOSPENSIONE_CDA, Constants.DECRETO_CONVALIDA_SEQUESTRO_URGENTE,
				Constants.DECRETO_CONVALIDA_SEQUESTRO_ANTICIPATO,
				Constants.REVOCA_DECRETO_OPPOSIZIONE_DISSEQUESTRO,
				Constants.REVOCA_DECRETO_OPPOSIZIONE_DISSEQUESTRO_II_GRADO,
				Constants.DECRETO_ORDINANZA_REST_BENI, Constants.DECRETO_SOSPENSIONE_TEMP_TRIB,
				Constants.DECRETO_SEQUESTRO_ORDINARIO_II_GRADO, Constants.DECRETO_SOSPENSIONE_TEMP_CDA,
				Constants.DECRETO_REVOCA_SEQUESTRO_II_GRADO, Constants.DECRETO_PROROGA_SEQUESTRO_CDA,
				Constants.PERDITA_EFFICACIA_SEQUESTRO);
	}

	@RequestMapping(value = "/procedimento/soggetto/visualizzaDirettiInteressatiRiesame.htm", method = RequestMethod.GET)
	public ModelAndView visualizzaSoggettiDirettiInteressatiRiesame(SoggettoForm soggettoForm) {

		addBreadcrumbs("/procedimento/soggetto/visualizzaDirettiInteressatiRiesame.htm", request);
		ProcedimentoForm procedimentoForm = (ProcedimentoForm) request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO);
		long idProcedimento = procedimentoForm.getIdProcedimento();
		ModelAndView mnv = new ModelAndView("/procedimento/soggetto/direttiInteressatiRiesame",
				"soggettoForm", soggettoForm);
		List<SoggettoForm> soggetti = procedimentoService.soggettiDirettiInteressatiDelProcedimento(
				idProcedimento, Constants.RUOLO_DIRETTO, Constants.Flag.S);
		mnv.addObject("risultatiRicerca", soggetti);
		return mnv;
	}

	@RequestMapping(value = "/procedimento/soggetto/visualizzaDirettiInteressatiTerzi.htm", method = RequestMethod.GET)
	public ModelAndView visualizzaSoggettiDirettiInteressatiTerzi(SoggettoForm soggettoForm) {

		// Giacomo 12/07/2013 Aggiunta Breadcrumbs
		addBreadcrumbs("GET", "/procedimento/soggetto/visualizzaDirettiInteressatiTerzi.htm", null);

		ProcedimentoForm procedimentoForm = (ProcedimentoForm) request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO);
		String idStatoProc = null;

		if (procedimentoForm.getStatoProcedimento() != null
				&& procedimentoForm.getStatoProcedimento().getId() != null) {
			idStatoProc = procedimentoForm.getStatoProcedimento().getId();
		}
		if (request.getSession().getAttribute(Constants.ATTO) != null) {
			request.getSession().removeAttribute(Constants.ATTO);
		}

		if (idStatoProc != null && Constants.STATOPROC_IN_ACQUISIZ_PROPOSTA.equals(idStatoProc)) {
			request.setAttribute("disattivaDifensore", true);
		}

		request.setAttribute("statoProcedimento", idStatoProc);
		long idProcedimento = procedimentoForm.getIdProcedimento();
		ModelAndView mnv = new ModelAndView("/procedimento/soggetto/direttiInteressatiTerzi", "soggettoForm",
				soggettoForm);
		List<SoggettoForm> soggetti = null;

		soggetti = procedimentoService.soggettiDirettiInteressatiDelProcedimento(idProcedimento,
				Constants.RUOLO_TERZO, Constants.Flag.S);
		mnv.addObject("risultatiRicerca", soggetti);
		request.getSession().setAttribute("RuoloSession", Constants.RUOLO_TERZO);
		return mnv;
	}

	@RequestMapping(value = "/procedimento/soggetto/aggiungiSoggetto.htm", method = RequestMethod.POST, params = {
			"AggiungiSoggettoFisico" })
	public ModelAndView visualizzaSoggettoFisico() {

		return new ModelAndView("/procedimento/nuovo/ricercaSoggettoFisico", "soggettoForm",
				new SoggettoForm());
	}

	@RequestMapping(value = "/procedimento/soggetto/aggiungiSoggetto.htm", method = RequestMethod.POST, params = {
			"AggiungiSoggettoGiuridico" })
	public ModelAndView visualizzaSoggettoGiuridico() {

		return new ModelAndView("/procedimento/nuovo/ricercaSoggettoGiuridico", "soggettoForm",
				new SoggettoForm());
	}

	// ***************** METODI PRIVATI **********************//
	private void caricaValoriDettaglioSoggetto(SoggettoForm soggettoForm) {

		Integer idUfficioUtente = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		Long idProcedimento = ((ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();

		if (request.getSession().getAttribute(Constants.TIPO_FASCICOLO) != null && request.getSession()
				.getAttribute(Constants.TIPO_FASCICOLO).equals(Constants.TIPO_FASCICOLO_GESTIONE)) {
			NumeroRegistroForm formPadre = procedimentoService.getNumeroRegistroPadre(idProcedimento);
			idProcedimento = formPadre.getIdProc2();
			idUfficioUtente = formPadre.getIdUfficio2();
		}

		soggettoForm.setIdUfficio(idUfficioUtente);
		soggettoForm.setIdProcedimento(idProcedimento);
		if ("F".equals(soggettoForm.getTipoSoggetto())) {
			soggettoForm = procedimentoService.caricaValoriDettaglioSoggettoFisicoProcedimento(soggettoForm);
			soggettoForm.setTipoSoggetto(Constants.SOGGETTO_FISICO);
		} else {
			soggettoForm = procedimentoService
					.caricaValoriDettaglioSoggettoGiuridicoProcedimento(soggettoForm);
			soggettoForm.setTipoSoggetto(Constants.SOGGETTO_GIURIDICO);
		}
	}

	private ModelAndView difensoriAssociatiJsp(DifensoreSoggettoForm difensoreSoggettoForm,
			BindingResult result) {

		Integer idUfficioUtente = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		ProcedimentoForm procedimentoForm = (ProcedimentoForm) request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO);
		Long idProcedimento = procedimentoForm.getIdProcedimento();

		ModelAndView mnv = new ModelAndView("procedimento/soggetto/difensoriAssociati",
				"difensoreSoggettoForm", difensoreSoggettoForm);
		List<DifensoreSoggettoForm> listaDifensoriAssociati;
		if (procedimentoForm.getTipoProcedimento() != null
				&& (Long.toString(Constants.TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM)
						.equals(procedimentoForm.getTipoProcedimento().getId())
						|| Long.toString(Constants.TIPOPROC_CORREZIONE_ERRORI_CAM)
								.equals(procedimentoForm.getTipoProcedimento().getId()))) {
			listaDifensoriAssociati = procedimentoService.listaDifensoriAssociatiIntestatari(idProcedimento,
					idUfficioUtente, difensoreSoggettoForm.getSoggettoForm().getIdSoggetto());
		} else {
			if (request.getSession().getAttribute(Constants.TIPO_FASCICOLO) != null && request.getSession()
					.getAttribute(Constants.TIPO_FASCICOLO).equals(Constants.TIPO_FASCICOLO_GESTIONE)) {
				NumeroRegistroForm formPadre = procedimentoService.getNumeroRegistroPadre(idProcedimento);
				idProcedimento = formPadre.getIdProc2();
				idUfficioUtente = formPadre.getIdUfficio2();
			}

			listaDifensoriAssociati = procedimentoService.listaDifensoriAssociatiSoggetto(idProcedimento,
					idUfficioUtente, difensoreSoggettoForm.getSoggettoForm().getIdSoggetto());

		}
		mnv.addObject("difensoriAssociati", listaDifensoriAssociati);
		return mnv;
	}

	private ModelAndView includeElencoMisuraPatrimonialeAtto(SoggettoForm soggettoForm,
			BindingResult result) {

		long idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
		Integer idUfficioUtente = recuperaIdUfficioUtente();
		long idProcedimento = ((ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		procedimentoService.setIdSoggettoIdVersioneTipoSoggFromChiave(soggettoForm);
		soggettoForm.setIdUfficio((Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE));
		if ("F".equals(soggettoForm.getTipoSoggetto()))
			soggettoForm = procedimentoService.caricaValoriDettaglioSoggettoFisico(soggettoForm);
		else
			soggettoForm = procedimentoService.caricaValoriDettaglioSoggettoGiuridico(soggettoForm);
		BeneSoggettoForm beneSoggettoForm = new BeneSoggettoForm();
		beneSoggettoForm.setSoggetto(soggettoForm);
		long idSoggetto = soggettoForm.getIdSoggetto();
		List<BeneSoggettoForm> beniDellaMisuraPatrimoniale = procedimentoService
				.listaBeniConSoggettiAssociatiAllaMisuraPatrimonialeAtto(idSoggetto, idProcedimento, idAtto,
						idUfficioUtente);
		List<BeneSoggettoForm> beniNonAssociati = procedimentoService.listaBeniAttoNoMisPatr(idUfficioUtente,
				idProcedimento, idAtto, idSoggetto, beneSoggettoForm.getIdSoggettiTitolari(),
				beneSoggettoForm.getIdTipologia(), beneSoggettoForm.getIdCategoria(),
				beneSoggettoForm.getIdSottocategoria());
		if (!soggettoForm.isTipologiaPrevenzioneNew()) {
			// Carico le informazioni da T_REL_PROC_SOGINT
			soggettoForm.setIdProcedimento(idProcedimento);
			soggettoForm = procedimentoService.caricaValoriGratuitoPatrocinioDelSoggetto(soggettoForm);
			soggettoForm.setTipologiaPrevenzioneDb(soggettoForm.getTipologiaPrevenzione());
		}
		List<ComboForm> normative = new ArrayList<ComboForm>();
		if ((soggettoForm.getTipologiaPrevenzione() != null)) {
			/*
			 * ISSUE MEV : aggiunto controllo preventivo ed invertito il controllo esistente
			 * Numero MEV : 19b
			 * Autore : Gioggi
			 * Data : 05 set 2018
			 * Branch : MEV_19b
			 */
			if (soggettoForm.getTipologiaPrevenzione().getId() != null
					&& !Constants.NO_ITEM_SELECTED.equals(soggettoForm.getTipologiaPrevenzione().getId())) {
				// ***** FINE INTERVENTO MEV_19b *****//
				if (soggettoForm.getIpotesiMisura() != null)
					// Imposto manualmente ad 1 il Tipo di Procedimento
					soggettoForm.getIpotesiMisura().setId("1");
				else
					// Imposto manualmente ad 1 il Tipo di Procedimento
					soggettoForm.setIpotesiMisura(new ComboForm("1", ""));
				normative = commonService.getNormativaByTipoProcedAndTipologiaPrevenzione(soggettoForm);
			}
		}

		// Andrea (AS): visualizzazione misure patrimoniali per decreti di rigetto
		String jsp2Show = (!ComparatorUtil.containsValue(recuperaAtto().getCategoriaAtto().getId(),
				Constants.DECRETO_RIGETTO_TOTALE_SEQUESTRO,
				Constants.DECRETO_RIGETTO_STATO_RICHIESTA_SEQUESTRO))
						? "/procedimento/soggetto/misuraPatrimonialeAtto"
						: "/procedimento/soggetto/misuraPatrimonialeAttoRigettoSequestro";
		ModelAndView mnv = new ModelAndView(jsp2Show, "beneSoggetto", beneSoggettoForm);

		mnv.addObject("risultatoRicerca", beniDellaMisuraPatrimoniale);
		mnv.addObject("risultatiRicerca", beniNonAssociati);
		mnv.addObject("normativeMisPatr", normative);
		request.getSession().setAttribute("soggettoForm", soggettoForm);
		return mnv;
	}

	// ***************** GESTIONE Annullamento SOGGETTO ****************//
	@RequestMapping(value = "/procedimento/soggetto/visualizzaSoggettiPerAnnullamento.htm", method = RequestMethod.GET)
	public ModelAndView visualizzaSoggettiPerAnnullamento(SoggettoForm soggettoForm) {

		// svuota i dati del soggetto //
		soggettoForm.setIdSoggetto(null);
		soggettoForm.setIdVersione(null);
		// fine svuota dati del soggetto //

		if (request.getSession().getAttribute(Constants.ATTO) != null) {
			request.getSession().removeAttribute(Constants.ATTO);
		}

		long idProcedimento = recuperaIdProcedimento();
		Integer idUffOwner = recuperaIdUfficioUtente();
		ProcedimentoForm procedimentoForm = procedimentoService.getDettaglioProcedimento(idProcedimento);
		boolean abilitazione = soggettoValidation.validateAbilitazioneSoggettiDuplicati(procedimentoForm,
				request);
		if (!abilitazione) {
			return new ModelAndView("redirect:/procedimento/apri/copertinaProcedimento.htm");
		}

		ModelAndView mnv = new ModelAndView("/procedimento/soggetto/elencoSoggettiAnnullabili",
				"soggettoForm", soggettoForm);
		List<SoggettoForm> soggetti = procedimentoService
				.soggettiDirettiInteressatiDelProcedimento(idProcedimento, null, Constants.Flag.S);

		if (soggetti != null) {
			for (int i = 0; soggetti != null && i < soggetti.size(); i++) {
				SoggettoForm sogg = soggetti.get(i);
				List<TRelBeneAtto> lista = beneService.getListaRelBeneAtto(idProcedimento, 0, idUffOwner, 0,
						sogg.getIdSoggetto().longValue());
				if (lista != null && lista.size() > 0
						&& sogg.getDescrRuolo().equalsIgnoreCase(Constants.RUOLO_DIRETTO)) {
					sogg.setDescrRuolo(sogg.getDescrRuolo().concat(" / ").concat(Constants.RUOLO_TERZO));
				}
			}
		}

		mnv.addObject("risultatiRicerca", soggetti);

		addBreadcrumbs("GET", "/procedimento/soggetto/visualizzaSoggettiPerAnnullamento.htm", null);

		return mnv;
	}

	@RequestMapping(value = "/procedimento/soggetto/startAnnullaSoggetto.htm", method = RequestMethod.POST, params = {
			"startAnnullaSoggetto" })
	public ModelAndView startAnnullaSoggetto(@ModelAttribute("soggettoForm") SoggettoForm soggettoForm,
			BindingResult result, HttpServletRequest request, ModelMap model) {

		// ProcedimentoForm procedimentoForm = (ProcedimentoForm) request.getSession()
		// .getAttribute(Constants.ID_PROCEDIMENTO);

		// dati soggetto valido
		long idSoggetto = soggettoForm.getIdSoggetto();
		long idVersione = soggettoForm.getIdVersione();
		String tipoSoggetto = soggettoForm.getTipoSoggetto();

		// dati soggetto duplicato
		long idSoggettoDuplicato = soggettoForm.getIdSoggettoDuplicato();
		long idVersioneDuplicato = soggettoForm.getIdVersioneDuplicato();
		String tipoSoggettoDuplicato = soggettoForm.getTipoSoggettoDuplicato();

		Integer idUffOwner = recuperaIdUfficioUtente();
		
		//FC 16/05/2023 MAC inserito controllo sui ruoli del soggetto (si possono annullare solo soggetti con ruoli uguali
		soggettoValidation.validaRuoloSoggettiDaAnnullare(soggettoForm, result);
		if (result.hasErrors()) {
			super.setMessage(Constants.MESSAGE, "errore.soggetto.generale", null, false);
			ModelAndView mnv = this.visualizzaSoggettiPerAnnullamento(soggettoForm);
			return mnv;
		}
		
		
		ProcedimentoForm procForm = recuperaProcedimento();
		soggettoValidation.validaRegistrazioneProcedimentoSoggetto( procForm,  soggettoForm, result);
		if (result.hasErrors()) {
			super.setMessage(Constants.MESSAGE, "errore.soggetto.generale", null, false);
			ModelAndView mnv = this.visualizzaSoggettiPerAnnullamento(soggettoForm);
			return mnv;
		}
		SoggettoForm soggettoFormValido = new SoggettoForm();

		if (Constants.SOGGETTO_FISICO.equalsIgnoreCase(tipoSoggetto)) {
			soggettoFormValido = procedimentoService.caricaValoriDettaglioSoggettoFisico(idSoggetto,
					idVersione, idUffOwner);
		} else if (Constants.SOGGETTO_GIURIDICO.equalsIgnoreCase(tipoSoggetto)) {
			soggettoFormValido = procedimentoService.caricaValoriDettaglioSoggettoGiuridico(idSoggetto,
					idVersione, idUffOwner);
		}
		soggettoFormValido.setTipoSoggetto(tipoSoggetto);
		model.addAttribute("soggettoFormValido", soggettoFormValido);

		SoggettoForm soggettoFormDupl = new SoggettoForm();

		if (Constants.SOGGETTO_FISICO.equalsIgnoreCase(tipoSoggettoDuplicato)) {
			soggettoFormDupl = procedimentoService.caricaValoriDettaglioSoggettoFisico(idSoggettoDuplicato,
					idVersioneDuplicato, idUffOwner);
		} else if (Constants.SOGGETTO_GIURIDICO.equalsIgnoreCase(tipoSoggettoDuplicato)) {
			soggettoFormDupl = procedimentoService.caricaValoriDettaglioSoggettoGiuridico(idSoggettoDuplicato,
					idVersioneDuplicato, idUffOwner);
		}
		soggettoFormDupl.setTipoSoggetto(tipoSoggettoDuplicato);
		model.addAttribute("soggettoFormDuplicato", soggettoFormDupl);
		
		
		// Controllo che i campi obbligatori siano uguali
		soggettoValidation.validaSoggettoDaAnnullare(soggettoFormValido, soggettoFormDupl, result);
		if (result.hasErrors()) {
			super.setMessage(Constants.MESSAGE, "errore.soggetto.annullabile.blocco", null, false);
		} else {
			request.setAttribute("abilitaConferma", "abilitaConferma");
		}
		
		ModelAndView mnv = new ModelAndView("/procedimento/soggetto/confermaSoggettiAnnullabili",
				"soggettoForm", soggettoForm);

		addBreadcrumbs("POST", "/procedimento/soggetto/startAnnullaSoggetto.htm", null);

		return mnv;
	}

	@RequestMapping(value = "/procedimento/soggetto/startAnnullaSoggetto.htm", method = RequestMethod.POST, params = {
			"Conferma" })
	public ModelAndView confermaAnnullaSoggetto(@ModelAttribute("soggettoForm") SoggettoForm soggettoForm,
			BindingResult result, HttpServletRequest request, ModelMap model) {

		Integer idUffOwner = recuperaIdUfficioUtente();
		long idProcedimento = recuperaIdProcedimento();
		String nomeMetodo = super.printMethodName();
		String utente = recuperaIdCodiceUtente();

		// dati soggetto valido
		long idSoggettoValido = soggettoForm.getIdSoggetto();

		// dati soggetto duplicato
		long idSoggettoDuplicato = soggettoForm.getIdSoggettoDuplicato();

		KeyMessageDto ritornoDb = annullamentoDuplicatoService.annullamentoSoggettoDuplicato(idProcedimento,
				idSoggettoDuplicato, idSoggettoValido, idUffOwner, utente, nomeMetodo);
		if (ritornoDb.isError()) {
			super.setMessage(Constants.MESSAGE, "messaggio.gestito.db", ritornoDb.getMessage(), false);
			result.reject(Constants.MESSAGE);
		} else {
			super.setMessage(Constants.SUCCESS_MESSAGE, "messaggio.gestito.db", ritornoDb.getMessage(),
					false);
			result.reject(Constants.MESSAGE);
		}

		addBreadcrumbs();

		return visualizzaSoggettiPerAnnullamento(soggettoForm);
	}

	// II Grado
	@RequestMapping(value = "/procedimento/soggetto/misuraPersonaleIIGrado.htm", method = RequestMethod.POST, params = {
			"SalvaMisuraDecisorioIIGrado" })
	public String salvaMisuraPersonaleDecisorioIIGrado(
			@ModelAttribute("soggettoForm") SoggettoForm soggettoForm, BindingResult result, ModelMap map) {

		AttoForm currentAttoDecisorioIIGrado = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
		ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO);
		ProfiloForm profilo = (ProfiloForm) request.getSession().getAttribute("profilo");
		if (Long.toString(Constants.TIPOPROC_PERMESSI_CAM).equals(procedimento.getTipoProcedimento().getId())
				|| Long.toString(Constants.TIPOPROC_CAUZIONI_CAM)
						.equals(procedimento.getTipoProcedimento().getId()))
			soggettoValidation.validateSalvaMisuraPersonaleCauzPermIIGrado(soggettoForm, result, request);
		else {
			if ("1".equals(soggettoForm.getMisura().getDecisioneMisuraPersonaleIIGrado().getId().toString())
					// Segnalazione EMS-125
					|| "262".equals(soggettoForm.getMisura().getDecisioneMisuraPersonaleIIGrado().getId()
							.toString())) {
				ComboForm decisioneMisura = soggettoForm.getMisura().getDecisioneMisuraPersonaleIIGrado();
				soggettoForm.setIdProcedimento(procedimento.getIdProcedimento());
				soggettoForm.setIdUfficio(
						(Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE));
				soggettoForm = misuraService.caricaMisuraPersonaleIIGrado(soggettoForm);
				soggettoForm.getMisura().setDecisioneMisuraPersonaleIIGrado(decisioneMisura);
			}
			soggettoValidation.validateSalvaMisuraPersonaleIIGrado(soggettoForm, result, request);
		}

		if (result.hasErrors()) {
			soggettoForm
					.setIdUfficio((Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE));
			if ("F".equals(soggettoForm.getTipoSoggetto())) {
				soggettoForm = procedimentoService.caricaValoriDettaglioSoggettoFisico(soggettoForm);
				soggettoForm.setTipoSoggetto(Constants.SOGGETTO_FISICO);
			} else {
				soggettoForm = procedimentoService.caricaValoriDettaglioSoggettoGiuridico(soggettoForm);
				soggettoForm.setTipoSoggetto(Constants.SOGGETTO_GIURIDICO);
			}

			List<ComboForm> list = commonService.listaPrescrizioniMisuraPersonale();
			map.addAttribute("listaPrescrizioni", list);
			String carattereMisura = "P";

			if (procedimento.getTipoProcedimento() != null
					&& procedimento.getTipoProcedimento().getId() != null
					&& Constants.TIPOPROC_AGGRAVAMENTO_PERSONALE
							.equals(procedimento.getTipoProcedimento().getId())) {
				carattereMisura = "G";
			}

			long idCategoriaAtto = Long.valueOf(Constants.DECRETO_DECISORIO_II_GRADO);
			if (Constants.DECRETO_DECISORIO_II_GRADO_CAM
					.equals(currentAttoDecisorioIIGrado.getCategoriaAtto().getId())) {
				idCategoriaAtto = Long.valueOf(Constants.DECRETO_DECISORIO_II_GRADO_CAM);
				carattereMisura = "X";
				// per tutti i cameralini usiamo il carattere misura X
			}

			List<ComboForm> esiti = procedimentoService.getEsiti(carattereMisura, idCategoriaAtto,
					profilo.getIdTipoUfficio());
			map.addAttribute("esiti", esiti);
			if (soggettoForm.getMisura() != null && soggettoForm.getMisura().getCauzione() != null
					&& "S".equalsIgnoreCase(soggettoForm.getMisura().getCauzione())) {
				map.addAttribute("importoCauzione", true);
			}
			map.addAttribute("soggettoForm", soggettoForm);
			// Carico nel ModelAndView le Normative
			caricaNormative(soggettoForm, null, map);
			return "procedimento/soggetto/misuraPersonaleIIGrado";
		}

		long vidProc = procedimento.getIdProcedimento();
		long tipoProc = Long.valueOf(procedimento.getTipoProcedimento().getId()).longValue();
		Integer vidUffOwn = recuperaIdUfficioUtente();
		String vcodUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);
		long idSoggInt = soggettoForm.getIdSoggetto();

		// long idTipoPrev = 0L;
		// long idTipoNorm = 0L;
		// if (soggettoForm.getTipologiaPrevenzione() != null
		// && soggettoForm.getTipologiaPrevenzione().getId() != null)
		// idTipoPrev = Long.parseLong(soggettoForm.getTipologiaPrevenzione().getId());
		// if (soggettoForm.getNormativa() != null && soggettoForm.getNormativa().getId() != null)
		// idTipoNorm = Long.parseLong(soggettoForm.getNormativa().getId());

		soggettoForm.setIdAtto(currentAttoDecisorioIIGrado.getCodAtto());

		if (Long.toString(Constants.TIPOPROC_CAUZIONI_CAM).equals(procedimento.getTipoProcedimento().getId()))
			map.addAttribute("importoCauzione", true);

		if (Long.toString(Constants.TIPOPROC_PERMESSI_CAM).equals(procedimento.getTipoProcedimento().getId())
				|| Long.toString(Constants.TIPOPROC_CAUZIONI_CAM)
						.equals(procedimento.getTipoProcedimento().getId()))
			misuraService.inserisciMisuraPersonaleDecisorioIIGrado(vidProc, tipoProc, vidUffOwn, idSoggInt,
					vcodUtente, soggettoForm, true);
		else
			misuraService.inserisciMisuraPersonaleDecisorioIIGrado(vidProc, tipoProc, vidUffOwn, idSoggInt,
					vcodUtente, soggettoForm, false);
		/*
		 * commentato
		 * 
		 * if (idTipoPrev!=0) procedimentoService.aggiornaTipoPrevenzioneNormativaSoggetto(vidProc, vidUffOwn,
		 * idSoggInt, idTipoPrev, idTipoNorm);
		 * 
		 * if(soggettoForm.getTipologiaPrevenzione()!=null && soggettoForm.getTipologiaPrevenzioneDb()!=null
		 * && !soggettoForm.getTipologiaPrevenzioneDb().getId().equals(soggettoForm.getTipologiaPrevenzione().
		 * getId())) { procedimentoService.aggiornaFlgTipoPrevenzioneProcedimento(vidProc, vidUffOwn); }
		 * 
		 */

		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation", "INSERIMENTO", false);
		return attoController.copertinaAtto(new AttoForm(), result, request, map);
	}

	@RequestMapping(value = "/procedimento/soggetto/misuraPersonaleIIGrado.htm", method = RequestMethod.GET)
	public ModelAndView cambioPaginaMisuraPersonaleIIGrado(SoggettoForm soggettoForm, ModelMap modelMap) {

		return new ModelAndView("/procedimento/soggetto/misuraPersonaleIIGrado", "soggettoForm",
				soggettoForm);
	}

	@RequestMapping(value = "/procedimento/soggetto/misuraPersonaleIIGrado.htm", method = RequestMethod.POST)
	public ModelAndView selezioneComboMisuraPersonaleIIGrado(SoggettoForm soggettoForm, BindingResult result,
			ModelMap map) {

		ModelAndView mnv = new ModelAndView("procedimento/soggetto/misuraPersonaleIIGrado", "soggettoForm",
				soggettoForm);
		// mnv.addObject("provenienza", soggettoForm.getSchermataDiProvenienza().toLowerCase());
		// Se passo da pagina disabilitata ad una qualsiasi altra Decisione

		AttoForm currentAtto = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
		long idProcedimento = ((ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		Integer idUfficio = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		String descUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);
		long idAtto = currentAtto.getCodAtto();

		ProfiloForm profilo = (ProfiloForm) request.getSession().getAttribute("profilo");
		String carattereMisura = "P";
		long idCategoriaAtto = Long.valueOf(Constants.DECRETO_DECISORIO_II_GRADO);
		ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO);
		if (procedimento.getTipoProcedimento() != null && procedimento.getTipoProcedimento().getId() != null
				&& Constants.TIPOPROC_AGGRAVAMENTO_PERSONALE
						.equals(procedimento.getTipoProcedimento().getId())) {
			carattereMisura = "G";
		}

		if (Constants.DECRETO_DECISORIO_II_GRADO_CAM.equals(currentAtto.getCategoriaAtto().getId())) {
			idCategoriaAtto = Long.valueOf(Constants.DECRETO_DECISORIO_II_GRADO_CAM);
			carattereMisura = "X";
			// per tutti i cameralini usiamo il carattere misura X
		}

		if (Constants.DECRETO_DECISORIO_PROVV_ISTANZA.equals(currentAtto.getCategoriaAtto().getId())) {
			if (procedimento.getTipoProcedimento() != null
					&& procedimento.getTipoProcedimento().getId() != null
					&& Constants.TIPOPROC_AGGRAVAMENTO_PERSONALE
							.equals(procedimento.getTipoProcedimento().getId()))
				idCategoriaAtto = Long.valueOf(Constants.DECRETO_DECISORIO_I_GRADO);
			else
				idCategoriaAtto = Long.valueOf(Constants.DECRETO_DECISORIO_I_GRADO_CAMERE);
		}

		String attoIstanza = Constants.ISTANZA_MODIFICA_PERSONALE;
		if (Constants.DECRETO_DECISORIO_II_GRADO_CAM.equals(currentAtto.getCategoriaAtto().getId())) {
			idCategoriaAtto = Long.valueOf(Constants.DECRETO_DECISORIO_II_GRADO_CAM);
			carattereMisura = "X";
			attoIstanza = Constants.ISTANZA_MODIFICA_PERSONALE;
			// per tutti i cameralini usiamo il carattere misura X
		}

		ComboForm idTribComp = commonService.getTribunaleCompetente(idProcedimento);

		// Se sto in corte d'appello vado a controllare che sia in un provvedimento d'istanza oppure in
		// un decisorio di un procedimento aperto in un corte d'appello in devo prendere gli esiti di primo
		// grado

		if ((Integer) request.getSession()
				.getAttribute("idTipoUfficio") == Constants.TIPO_UFFICIO_CORTE_D_APPELLO)
			// sono un decreto decisorio su provvedimento d'istanza //sono un decisorio di II grado di un
			// procedimento cauzione o permessi
			if (Constants.DECRETO_DECISORIO_PROVV_ISTANZA.equals(currentAtto.getCategoriaAtto().getId())) {
				if (Long.toString(Constants.TIPOPROC_CAUZIONI_CAM)
						.equals(procedimento.getTipoProcedimento().getId())) {
					attoIstanza = Constants.ISTANZA_CAUZIONE;
					carattereMisura = "CA";
				}
				if (Long.toString(Constants.TIPOPROC_PERMESSI_CAM)
						.equals(procedimento.getTipoProcedimento().getId())) {
					attoIstanza = Constants.ISTANZA_PERMESSI;
					carattereMisura = "PE";
				}

				// se si origina in corte d'appello
				if (idTribComp.getId() == null) {
					if (Constants.TIPOPROC_AGGRAVAMENTO_PERSONALE
							.equals(procedimento.getTipoProcedimento().getId()))
						idCategoriaAtto = Long.valueOf(Constants.DECRETO_DECISORIO_I_GRADO);
					else
						idCategoriaAtto = Long.valueOf(Constants.DECRETO_DECISORIO_I_GRADO_CAMERE);
				}
			}

		if (Constants.DECRETO_DECISORIO_I_GRADO_CAMERE.equals(currentAtto.getCategoriaAtto().getId())
				|| Constants.DECRETO_DECISORIO_II_GRADO_CAM.equals(currentAtto.getCategoriaAtto().getId())
				|| Constants.DECRETO_DECISORIO_PROVV_ISTANZA.equals(currentAtto.getCategoriaAtto().getId())) {
			List<AttoForm> istanze = null;
			if (Constants.DECRETO_DECISORIO_II_GRADO_CAM.equals(currentAtto.getCategoriaAtto().getId())
					&& idTribComp.getId() != null) {
				List<TRelProcProc> listaProcedimenti = procedimentoService
						.getListaProcedimentiCdaTrib(idProcedimento, recuperaIdUfficioUtente());
				if (listaProcedimenti != null && !listaProcedimenti.isEmpty()) {
					Iterator<TRelProcProc> iter = listaProcedimenti.iterator();
					Map map1 = (Map) iter.next();
					TRelProcProc proc = (TRelProcProc) map1.get("rel");
					// attoForm.setIdProcedimentoCda(proc.getId().getCodiKIdProc2());

					if (!"".equals(attoIstanza))
						istanze = attoService.getricercaIstanza(new Integer(idTribComp.getId()),
								proc.getId().getCodiKIdProc2(),
								Long.parseLong(Constants.TIPO_ATTO_ISTANZA_CAM), Long.parseLong(attoIstanza));
					if (listaProcedimenti.size() > 1 && (istanze == null || istanze.isEmpty())) {
						map1 = (Map) iter.next();
						proc = (TRelProcProc) map1.get("rel");
						if (!"".equals(attoIstanza))
							istanze = attoService.getricercaIstanza(new Integer(idTribComp.getId()),
									proc.getId().getCodiKIdProc2(),
									Long.parseLong(Constants.TIPO_ATTO_ISTANZA_CAM),
									Long.parseLong(attoIstanza));
					}
				}
			}

			if (Constants.DECRETO_DECISORIO_I_GRADO_CAMERE.equals(currentAtto.getCategoriaAtto().getId())
					|| Constants.DECRETO_DECISORIO_PROVV_ISTANZA
							.equals(currentAtto.getCategoriaAtto().getId()))
				if (!"".equals(attoIstanza))
					istanze = attoService.getricercaIstanza(
							(Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE),
							((ProcedimentoForm) (request.getSession()
									.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento(),
							Long.parseLong(Constants.TIPO_ATTO_ISTANZA_CAM), Long.parseLong(attoIstanza));

			AttoForm istanza = new AttoForm();
			if (istanze != null && !istanze.isEmpty()) {
				istanza = istanze.get(0);
				soggettoForm.setDescRichiesta(istanza.getNote());
			}
		}

		String cauzPrec = soggettoForm.getMisura().getCauzione();
		// SITMP-91 DG  casistica con cauzione proveniente da tribunale 
		BigDecimal cauzioneTribunale = soggettoForm.getMisura().getImporto();
		String art67Prec = soggettoForm.getMisura().getArt67();
		long idSoggetto = soggettoForm.getIdSoggetto();
		ComboForm decisioneMisura = new ComboForm();
		if (soggettoForm.getMisura() != null
				&& soggettoForm.getMisura().getDecisioneMisuraPersonaleIIGrado() != null)
			decisioneMisura = soggettoForm.getMisura().getDecisioneMisuraPersonaleIIGrado();

		if (decisioneMisura != null && decisioneMisura.getId() != null
				&& !"-1".equals(decisioneMisura.getId()) && !"".equals(decisioneMisura.getId())) {
			long esito = Long.parseLong(decisioneMisura.getId());
			 
			if(decisioneMisura.getId().equals("2") || decisioneMisura.getId().equals("4") || decisioneMisura.getId().equals("5") || decisioneMisura.getId().equals("6")){
				soggettoForm.getMisura().setCauzione("N");
				cauzPrec = soggettoForm.getMisura().getCauzione();
			}else {
				if(cauzioneTribunale != null ) {
					soggettoForm.getMisura().setCauzione("S");
					cauzPrec = soggettoForm.getMisura().getCauzione();
				}
			}
			KeyMessageDto ritornoDb = attoService.controllaUpdEsitoMisPers(idProcedimento, idAtto, idSoggetto,
					esito, cauzPrec, art67Prec, idUfficio, descUtente, "II_GRADO", super.printMethodName());
			if (ritornoDb.isError()) {
				// disabilito il bottone salva
				mnv.addObject("disabled", "true");
				// disabilito i campi
				mnv.addObject("disabledObject", "true");
				// valorizzo la lista degli esiti anche se non è corretta la decisione
				// per permettere un'ulteriore scelta
				List<ComboForm> esiti = null;
				if (Constants.DECRETO_DECISORIO_PROVV_ISTANZA.equals(currentAtto.getCategoriaAtto().getId()))
					esiti = procedimentoService.getEsiti(carattereMisura, idCategoriaAtto, 0);
				else
					esiti = procedimentoService.getEsiti(carattereMisura, idCategoriaAtto,
							profilo.getIdTipoUfficio());

				mnv.addObject("esiti", esiti);
				List<ComboForm> list = commonService.listaPrescrizioniMisuraPersonale();
				mnv.addObject("listaPrescrizioni", list);
				caricaNormative(soggettoForm, mnv, null);
				super.setMessage(Constants.MESSAGE, "messaggio.gestito.db", ritornoDb.getMessage(), false);
				result.reject(Constants.MESSAGE);
				return mnv;
			}
		}
		// se passa il controllo sull'esito il bottone salva si deve vedere
		mnv.addObject("disabled", "false");

		if (soggettoForm.getMisura().getTipoMisura() == null) {
			soggettoForm.setIdUfficio(idUfficio);
			soggettoForm.setIdProcedimento(idProcedimento);

			soggettoForm.setIdAtto(idAtto);
			soggettoForm = misuraService.caricaMisuraPersonaleIIGrado(soggettoForm);

			if (cauzPrec != null)
				soggettoForm.getMisura().setCauzione(cauzPrec);
			if (art67Prec != null)
				soggettoForm.getMisura().setArt67(art67Prec);
			soggettoForm.getMisura().setDecisioneMisuraPersonaleIIGrado(decisioneMisura);
			// Carico le informazioni da T_REL_PROC_SOGINT
			soggettoForm = procedimentoService.caricaValoriGratuitoPatrocinioDelSoggetto(soggettoForm);
			soggettoForm.setTipologiaPrevenzioneDb(soggettoForm.getTipologiaPrevenzione());
			soggettoForm.setIpotesiMisura(soggettoForm.getIpotesiMisura());
		}

		// Carico nel ModelAndView le Normative
		caricaNormative(soggettoForm, mnv, null);
		List<ComboForm> list = commonService.listaPrescrizioniMisuraPersonale();
		mnv.addObject("listaPrescrizioni", list);
		soggettoForm.setIdUfficio((Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE));
		if ("F".equals(soggettoForm.getTipoSoggetto())) {
			soggettoForm = procedimentoService.caricaValoriDettaglioSoggettoFisico(soggettoForm);
			soggettoForm.setTipoSoggetto(Constants.SOGGETTO_FISICO);
		} else {
			soggettoForm = procedimentoService.caricaValoriDettaglioSoggettoGiuridico(soggettoForm);
			soggettoForm.setTipoSoggetto(Constants.SOGGETTO_GIURIDICO);
		}
		if (soggettoForm.getMisura().getTipoMisura() != null) {
			if (soggettoForm.getMisura().getTipoMisura().getId().equals("1")) {
				soggettoForm.getMisura().setLocalita(null);
			}
		}

		// se non ci sono misure personali disabilito i campi della misura personale
		if ("N".equals(soggettoForm.getVisibilitaIIGrado()))
			mnv.addObject("disabledObject", "true");
		else
			mnv.addObject("disabledObject", "false");

		if ("S".equals(soggettoForm.getVisibilitaIIGrado())) {
			List<ComboForm> esiti = null;
			if (Constants.DECRETO_DECISORIO_PROVV_ISTANZA.equals(currentAtto.getCategoriaAtto().getId()))
				esiti = procedimentoService.getEsiti(carattereMisura, idCategoriaAtto, 0);
			else
				esiti = procedimentoService.getEsiti(carattereMisura, idCategoriaAtto,
						profilo.getIdTipoUfficio());

			mnv.addObject("esiti", esiti);
		}
		if (soggettoForm.getMisura().getCauzione() != null) {
			if ("S".equalsIgnoreCase(soggettoForm.getMisura().getCauzione()))
				mnv.addObject("importoCauzione", true);
		}

		if (Long.toString(Constants.TIPOPROC_CAUZIONI_CAM).equals(procedimento.getTipoProcedimento().getId()))
			mnv.addObject("importoCauzione", true);

		// Gestione disabilitazione importo cauzione Segnalazione EMS-97
		if (soggettoForm.getMisura() != null
				&& soggettoForm.getMisura().getDecisioneMisuraPersonaleIIGrado() != null
				&& !"264".equals(soggettoForm.getMisura().getDecisioneMisuraPersonaleIIGrado().getId()))
			mnv.addObject("disabledImporto", "true");
		if (Long.toString(Constants.TIPOPROC_CAUZIONI_CAM).equals(procedimento.getTipoProcedimento().getId()))
			if (soggettoForm.getMisura().getImporto() == null)
				soggettoForm.getMisura()
						.setImporto(misuraService.getImportoCauzione(idProcedimento, idAtto, idUfficio));

		// Segnalazione EMS-46
		if ("1".equals(decisioneMisura.getId().toString())
				// Segnalazione EMS-125
				|| "262".equals(decisioneMisura.getId().toString())) {
			soggettoForm.setIdProcedimento(procedimento.getIdProcedimento());
			soggettoForm = misuraService.caricaMisuraPersonaleIIGrado(soggettoForm);
			soggettoForm.getMisura().setDecisioneMisuraPersonaleIIGrado(decisioneMisura);
			mnv.addObject("disabledObject", "true");
		}
		return mnv;
	}

	@RequestMapping(value = "/procedimento/soggetto/misuraPersonaleSentenzaCassazione.htm", method = RequestMethod.POST, params = {
			"SalvaMisuraDecisorioIIIGrado" })
	public String salvaMisuraPersonaleDecisorioIIIGrado(
			@ModelAttribute("soggettoForm") SoggettoForm soggettoForm, BindingResult result, ModelMap map) {

		AttoForm currentAttoDecisorioIIIGrado = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
		// ProfiloForm profilo = (ProfiloForm) request.getSession().getAttribute("profilo");
		soggettoValidation.validateSalvaMisuraPersonaleIIIGrado(soggettoForm, result, request);

		if (result.hasErrors()) {
			procedimentoService.setIdSoggettoIdVersioneTipoSoggFromChiave(soggettoForm);
			AttoForm currentAtto = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
			soggettoForm
					.setIdUfficio((Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE));
			soggettoForm.setIdProcedimento(
					((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO)))
							.getIdProcedimento());
			soggettoForm.setIdAtto(currentAtto.getCodAtto());

			soggettoForm = misuraService.caricaMisuraPersonaleIIIGrado(soggettoForm);

			String tipologiaPrev = soggettoForm.getTipologiaPrevenzione().getDescrizione();

			if ("F".equals(soggettoForm.getTipoSoggetto())) {
				soggettoForm = procedimentoService
						.caricaValoriDettaglioSoggettoFisicoProcedimento(soggettoForm);
				soggettoForm.setTipoSoggetto(Constants.SOGGETTO_FISICO);
				if (soggettoForm.getTipologiaPrevenzione() != null
						&& soggettoForm.getTipologiaPrevenzione().getDescrizione() == null) {
					soggettoForm.getTipologiaPrevenzione().setDescrizione(tipologiaPrev);
				}
			} else {
				soggettoForm = procedimentoService
						.caricaValoriDettaglioSoggettoGiuridicoProcedimento(soggettoForm);
				soggettoForm.setTipoSoggetto(Constants.SOGGETTO_GIURIDICO);
				if (soggettoForm.getTipologiaPrevenzione() != null
						&& soggettoForm.getTipologiaPrevenzione().getDescrizione() == null) {
					soggettoForm.getTipologiaPrevenzione().setDescrizione(tipologiaPrev);
				}
			}

			// Carico le informazioni da T_REL_PROC_SOGINT
			soggettoForm = procedimentoService.caricaValoriGratuitoPatrocinioDelSoggetto(soggettoForm);
			soggettoForm.setTipologiaPrevenzioneDb(soggettoForm.getTipologiaPrevenzione());

			// Carico nel ModelAndView le Normative
			caricaNormative(soggettoForm, null, map);
			// ProcedimentoForm procedimento = recuperaProcedimento();
			List<ComboForm> list = commonService.listaPrescrizioniMisuraPersonale();
			map.addAttribute("listaPrescrizioni", list);

			long idCategoriaAtto = Long.valueOf(Constants.SENTENZA_SU_RICORSO_II_GRADO);

			if (Constants.TIPO_UFFICIO_TRIBUNALE
					.equals(String.valueOf(request.getSession().getAttribute("idTipoUfficio")))) {
				idCategoriaAtto = Long.valueOf(Constants.SENTENZA_SU_RICORSO_II_GRADO_DA_TRIB);
			}

			if (soggettoForm.getMisura() != null && soggettoForm.getMisura().getCauzione() != null
					&& "S".equalsIgnoreCase(soggettoForm.getMisura().getCauzione())) {
				map.addAttribute("importoCauzione", true);
			}

			String carattereMisura = "X";
			List<ComboForm> esiti = null;
			long idUfficio = 1l; // per ufficio di cassazione

			esiti = procedimentoService.getEsiti(carattereMisura, idCategoriaAtto, idUfficio);
			map.addAttribute("esiti", esiti);
			map.addAttribute("soggettoForm", soggettoForm);

			return "procedimento/soggetto/misuraPersonaleSentenzaCassazione";
		}

		ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO);

		long vidProc = procedimento.getIdProcedimento();
		long tipoProc = Long.valueOf(procedimento.getTipoProcedimento().getId()).longValue();
		Integer vidUffOwn = recuperaIdUfficioUtente();
		String vcodUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);
		long idSoggInt = soggettoForm.getIdSoggetto();

		soggettoForm.setIdAtto(currentAttoDecisorioIIIGrado.getCodAtto());
		misuraService.inserisciMisuraPersonaleDecisorioIIIGrado(vidProc, tipoProc, vidUffOwn, idSoggInt,
				vcodUtente, soggettoForm);

		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation", "INSERIMENTO", false);
		return attoController.copertinaAtto(new AttoForm(), result, request, map);
	}

	// Segnalazione EMS-102
	@RequestMapping(value = "/procedimento/atto/decisioneIstanzaCauzione.htm", method = RequestMethod.POST, params = {
			"SalvaDecisorioIIIGrado" })
	public String salvaIstanzaCauzioneDecisorioIIIGrado(
			@ModelAttribute("soggettoForm") SoggettoForm soggettoForm, BindingResult result, ModelMap map) {

		AttoForm currentAttoDecisorioIIIGrado = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
		soggettoValidation.validateSalvaMisuraPersonaleIIIGrado(soggettoForm, result, request);

		if (result.hasErrors()) {
			// procedimentoService.setIdSoggettoIdVersioneTipoSoggFromChiave(soggettoForm);
			AttoForm currentAtto = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
			soggettoForm
					.setIdUfficio((Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE));
			soggettoForm.setIdProcedimento(
					((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO)))
							.getIdProcedimento());
			soggettoForm.setIdAtto(currentAtto.getCodAtto());

			soggettoForm = misuraService.caricaMisuraPersonaleIIIGrado(soggettoForm);

			String tipologiaPrev = soggettoForm.getTipologiaPrevenzione().getDescrizione();

			soggettoForm = procedimentoService.caricaValoriDettaglioSoggettoFisicoProcedimento(soggettoForm);
			soggettoForm.setTipoSoggetto(Constants.SOGGETTO_FISICO);
			if (soggettoForm.getTipologiaPrevenzione() != null
					&& soggettoForm.getTipologiaPrevenzione().getDescrizione() == null)
				soggettoForm.getTipologiaPrevenzione().setDescrizione(tipologiaPrev);

			// Carico le informazioni da T_REL_PROC_SOGINT
			soggettoForm = procedimentoService.caricaValoriGratuitoPatrocinioDelSoggetto(soggettoForm);
			soggettoForm.setTipologiaPrevenzioneDb(soggettoForm.getTipologiaPrevenzione());

			// Carico nel ModelAndView le Normative
			caricaNormative(soggettoForm, null, map);
			List<ComboForm> list = commonService.listaPrescrizioniMisuraPersonale();
			map.addAttribute("listaPrescrizioni", list);

			long idCategoriaAtto = Long.valueOf(Constants.SENTENZA_SU_RICORSO_II_GRADO);

			if (Constants.TIPO_UFFICIO_TRIBUNALE
					.equals(String.valueOf(request.getSession().getAttribute("idTipoUfficio")))) {
				idCategoriaAtto = Long.valueOf(Constants.SENTENZA_SU_RICORSO_II_GRADO_DA_TRIB);
			}

			if (soggettoForm.getMisura() != null && soggettoForm.getMisura().getCauzione() != null
					&& "S".equalsIgnoreCase(soggettoForm.getMisura().getCauzione())) {
				map.addAttribute("importoCauzione", true);
			}

			String carattereMisura = "X";
			List<ComboForm> esiti = null;
			long idUfficio = 1l; // per ufficio di cassazione

			esiti = procedimentoService.getEsiti(carattereMisura, idCategoriaAtto, idUfficio);
			map.addAttribute("esiti", esiti);
			map.addAttribute("soggettoForm", soggettoForm);

			String[] decisioni = procedimentoServiceCam.getEsitoDecisione(
					((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO)))
							.getIdProcedimento(),
					currentAtto.getCodAtto(),
					(Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE),
					recuperaIdCodiceUtente());
			soggettoForm.setDecisioneSuDecreto(decisioni[0]);
			if (decisioni[1] != null && decisioni[1].length() > 0) {
				if (soggettoForm.getMisura() == null) {
					MisuraForm misura = new MisuraForm();
					soggettoForm.setMisura(misura);
				}
				ComboForm combo = new ComboForm();
				combo.setDescrizione(decisioni[1]);
				soggettoForm.getMisura().setDecisioneMisuraPersonaleIIGrado(combo);
			}

			return "procedimento/atto/decisioneIstanzaCauzione";
		}

		ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO);

		long vidProc = procedimento.getIdProcedimento();
		long tipoProc = Long.valueOf(procedimento.getTipoProcedimento().getId()).longValue();
		Integer vidUffOwn = recuperaIdUfficioUtente();
		String vcodUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);
		long idSoggInt = soggettoForm.getIdSoggetto();

		soggettoForm.setIdAtto(currentAttoDecisorioIIIGrado.getCodAtto());
		misuraService.inserisciMisuraPersonaleDecisorioIIIGrado(vidProc, tipoProc, vidUffOwn, idSoggInt,
				vcodUtente, soggettoForm);

		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation", "INSERIMENTO", false);
		return attoController.copertinaAtto(new AttoForm(), result, request, map);
	}

	@RequestMapping(value = "/procedimento/soggetto/visualizzaDirettiInteressati.htm", method = RequestMethod.POST, params = {
			"RiepilogoSoggetto" })
	public ModelAndView riepilogoSoggetto(SoggettoForm soggettoForm, BindingResult result, ModelMap map) {

		int idUffOwn = RequestUtil.recuperaIdUfficioUtente(request);
		long idProcedimento = RequestUtil.recuperaIdProcedimento(request);

		// DETTAGLIO SOGGETTO
		ModelAndView mv = direttiInteressatiDettaglio(soggettoForm, result);

		if (!result.hasErrors()) {
			if ("F".equals(soggettoForm.getTipoSoggetto())) {
				mv = new ModelAndView("procedimento/nuovo/riepilogoSoggettoFisicoProcedimento",
						"soggettoForm", soggettoForm);
			} else {
				mv = new ModelAndView("procedimento/nuovo/riepilogoSoggettoGiuridicoProcedimento",
						"soggettoForm", soggettoForm);
			}

			long idSoggetto = soggettoForm.getIdSoggetto();
			long idVersione = soggettoForm.getIdVersione();

			soggettoForm.setIdUfficio(idUffOwn);
			soggettoForm.setIdProcedimento(idProcedimento);
			soggettoForm.setIdSoggetto(idSoggetto);
			soggettoForm.setIdVersione(idVersione);
			soggettoForm.setTipoSoggetto(soggettoForm.getTipoSoggetto());

			if ("F".equals(soggettoForm.getTipoSoggetto())) {
				soggettoForm = procedimentoService
						.caricaValoriDettaglioSoggettoFisicoProcedimento(soggettoForm);
			} else {
				soggettoForm = procedimentoService
						.caricaValoriDettaglioSoggettoGiuridicoProcedimento(soggettoForm);
			}

			// DIFENSORI
			SoggettoForm difensoreForm = new SoggettoForm();
			difensoreForm.setIdProcedimento(soggettoForm.getIdProcedimento());
			difensoreForm.setIdSoggetto(soggettoForm.getIdSoggetto());
			difensoreForm.setIdUfficio(soggettoForm.getIdUfficio());

			difensoreForm = procedimentoService.caricaValoriGratuitoPatrocinioDelSoggetto(difensoreForm);
			map.addAttribute("difensoreForm", difensoreForm);

			List<DifensoreSoggettoForm> listaDifensoriAssociati = procedimentoService
					.listaDifensoriAssociatiSoggetto(idProcedimento, idUffOwn, soggettoForm.getIdSoggetto());
			map.addAttribute("difensoriAssociati", listaDifensoriAssociati);

			// Dettaglio soggetto procedimento
			TRelProcSogint dettaglioSoggetto = soggettoService.dettaglioRelSogInt(idSoggetto, idUffOwn,
					idProcedimento);
			map.addAttribute("dettaglioSoggetto", dettaglioSoggetto);

			// MISURE
			List<MisurePersSoggDto> misurePersonali = soggettoService.misurePersonaliSoggetto(idSoggetto,
					idUffOwn, idProcedimento);
			List<MisurePersSoggDto> cauzioniSoggetto = soggettoService.cauzioniSoggetto(idSoggetto, idUffOwn,
					idProcedimento);
			List<MisurePersSoggDto> art67Soggetto = soggettoService.art67Soggetto(idSoggetto, idUffOwn,
					idProcedimento);
			List<MisurePatrSoggDto> misurePatrimoniali = procedimentoService
					.misurePatrimonialiSoggetto(idUffOwn, idProcedimento, idSoggetto, null);

			/************ inizio trattazione soggetto annullato **************/

			// a mio avviso non è questa la soluzione ideale ne dal punto di vista funzionale ne dal punto di
			// vista della pulizia del codice
			// questa soluzione indicatami dagli analisti non copre i casi di due o più riunioni e altri casi
			// da verificare,
			// inoltre dal punto di vista tecnico è inguardabile

			Long sogAnnullato = soggettoService.getIdSoggettoAnnullato(idSoggetto, idUffOwn, idProcedimento);

			if (sogAnnullato != null && sogAnnullato.longValue() != 0l) {
				List<MisurePersSoggDto> misurePersonaliAnn = soggettoService
						.misurePersonaliSoggetto(sogAnnullato, idUffOwn, idProcedimento);
				List<MisurePersSoggDto> cauzioniSoggettoAnn = soggettoService.cauzioniSoggetto(sogAnnullato,
						idUffOwn, idProcedimento);
				List<MisurePersSoggDto> art67SoggettoAnn = soggettoService.art67Soggetto(sogAnnullato,
						idUffOwn, idProcedimento);
				List<MisurePatrSoggDto> misurePatrimonialiAnn = procedimentoService
						.misurePatrimonialiSoggetto(idUffOwn, idProcedimento, sogAnnullato, null);

				Boolean inserisci = true;
				if (misurePersonaliAnn != null) {
					Iterator<MisurePersSoggDto> iterSeqAn = misurePersonaliAnn.iterator();
					while (iterSeqAn.hasNext()) {
						MisurePersSoggDto misAnn = iterSeqAn.next();
						String caratMisAn = misAnn.getRelAttoMisPers().getTMisuraPers().getTTpCaratMisura()
								.getCodiKIdCaratMis();
						String desccaratMisAn = misAnn.getRelAttoMisPers().getTMisuraPers()
								.getTTpCaratMisura().getDescCaratMisura();
						if (misurePersonali != null) {
							Iterator<MisurePersSoggDto> iterSeq = misurePersonali.iterator();
							while (iterSeq.hasNext()) {
								MisurePersSoggDto mis = iterSeq.next();
								String caratMis = mis.getRelAttoMisPers().getTMisuraPers().getTTpCaratMisura()
										.getCodiKIdCaratMis();
								String desccaratMis = mis.getRelAttoMisPers().getTMisuraPers()
										.getTTpCaratMisura().getDescCaratMisura();
								if (caratMis != null && caratMis.equalsIgnoreCase(caratMisAn)
										&& mis.getRelAttoMisPers().getTAtto().getTTpCatAtto()
												.getCodiKIdCatAtto() == misAnn.getRelAttoMisPers().getTAtto()
														.getTTpCatAtto().getCodiKIdCatAtto()) {
									inserisci = false;
								} else {
									if ("P".equalsIgnoreCase(desccaratMisAn) && desccaratMis != null
											&& desccaratMis.equalsIgnoreCase(desccaratMisAn)
											&& mis.getRelAttoMisPers().getTAtto().getTTpCatAtto()
													.getCodiKIdCatAtto() == misAnn.getRelAttoMisPers()
															.getTAtto().getTTpCatAtto().getCodiKIdCatAtto()) {
										inserisci = false;
									}
								}
							}
							if (inserisci)
								misurePersonali.add(misAnn);
						} else {
							misurePersonali = new ArrayList<MisurePersSoggDto>();
							misurePersonali.add(misAnn);
						}
					}
				}

				inserisci = true;
				if (cauzioniSoggettoAnn != null) {
					Iterator<MisurePersSoggDto> iterSeqAn = cauzioniSoggettoAnn.iterator();
					while (iterSeqAn.hasNext()) {
						MisurePersSoggDto misAnn = iterSeqAn.next();
						String caratMisAn = misAnn.getRelAttoMisPers().getTMisuraPers().getTTpCaratMisura()
								.getCodiKIdCaratMis();
						if (cauzioniSoggetto != null) {
							Iterator<MisurePersSoggDto> iterSeq = cauzioniSoggetto.iterator();
							while (iterSeq.hasNext()) {
								MisurePersSoggDto mis = iterSeq.next();
								String caratMis = mis.getRelAttoMisPers().getTMisuraPers().getTTpCaratMisura()
										.getCodiKIdCaratMis();
								if (caratMis != null && caratMis.equalsIgnoreCase(caratMisAn)
										&& mis.getRelAttoMisPers().getTAtto().getTTpCatAtto()
												.getCodiKIdCatAtto() == misAnn.getRelAttoMisPers().getTAtto()
														.getTTpCatAtto().getCodiKIdCatAtto()) {
									inserisci = false;
								}
							}
							if (inserisci)
								cauzioniSoggetto.add(misAnn);
						} else {
							cauzioniSoggetto = new ArrayList<MisurePersSoggDto>();
							cauzioniSoggetto.add(misAnn);
						}
					}
				}

				inserisci = true;
				if (art67SoggettoAnn != null) {
					Iterator<MisurePersSoggDto> iterSeqAn = art67SoggettoAnn.iterator();
					while (iterSeqAn.hasNext()) {
						MisurePersSoggDto misAnn = iterSeqAn.next();
						String caratMisAn = misAnn.getRelAttoMisPers().getTMisuraPers().getTTpCaratMisura()
								.getCodiKIdCaratMis();
						if (art67Soggetto != null) {
							Iterator<MisurePersSoggDto> iterSeq = art67Soggetto.iterator();
							while (iterSeq.hasNext()) {
								MisurePersSoggDto mis = iterSeq.next();
								String caratMis = mis.getRelAttoMisPers().getTMisuraPers().getTTpCaratMisura()
										.getCodiKIdCaratMis();
								if (caratMis != null && caratMis.equalsIgnoreCase(caratMisAn)
										&& mis.getRelAttoMisPers().getTAtto().getTTpCatAtto()
												.getCodiKIdCatAtto() == misAnn.getRelAttoMisPers().getTAtto()
														.getTTpCatAtto().getCodiKIdCatAtto()) {
									inserisci = false;
								}
							}
							if (inserisci)
								art67Soggetto.add(misAnn);
						} else {
							art67Soggetto = new ArrayList<MisurePersSoggDto>();
							art67Soggetto.add(misAnn);
						}
					}
				}

				inserisci = true;
				if (misurePatrimonialiAnn != null) {
					Iterator<MisurePatrSoggDto> iterSeqAn = misurePatrimonialiAnn.iterator();
					while (iterSeqAn.hasNext()) {
						MisurePatrSoggDto misAnn = iterSeqAn.next();
						BigDecimal idBeneAnn = misAnn.getCODI_K_ID_BENE();
						if (misurePatrimoniali != null) {
							Iterator<MisurePatrSoggDto> iterSeq = misurePatrimoniali.iterator();
							while (iterSeq.hasNext()) {
								MisurePatrSoggDto mis = iterSeq.next();
								BigDecimal idBene = mis.getCODI_K_ID_BENE();
								if (idBeneAnn != null && idBeneAnn.equals(idBene)) {
									inserisci = false;
								}
							}
							if (inserisci)
								misurePatrimoniali.add(misAnn);
						} else {
							misurePatrimoniali = new ArrayList<MisurePatrSoggDto>();
							misurePatrimoniali.add(misAnn);
						}
					}
				}
			}

			/************ fine trattazione soggetto annullato **************/

			map.addAttribute("misurePersonali", misurePersonali);
			map.addAttribute("cauzioniSoggetto", cauzioniSoggetto);
			map.addAttribute("art67Soggetto", art67Soggetto);
			map.addAttribute("misurePatrimoniali", misurePatrimoniali);

			if (Long.toString(Constants.TIPO_UFFICIO_CORTE_D_APPELLO)
					.equals(String.valueOf(request.getSession().getAttribute("idTipoUfficio")))) {
				List<RiepilogoSoggettoDto> procedimentiTrib = new ArrayList<RiepilogoSoggettoDto>();
				List<NumeroRegistroForm> lNumReg = procedimentoService.getNumeriRegistro(idProcedimento);

				if (lNumReg != null && !lNumReg.isEmpty()) {
					for (int j = 0; j < lNumReg.size(); j++) {
						NumeroRegistroForm numReg = lNumReg.get(j);
						if (numReg.getIdTipoUfficio2() == Integer
								.parseInt(Constants.TIPO_UFFICIO_TRIBUNALE)) {

							ProcedimentoForm procedimentoTrib = procedimentoService
									.getDettaglioProcedimento(numReg.getIdProc2());

							DettaglioSoggettoDto procedimentoMerito = new DettaglioSoggettoDto();
							procedimentoMerito.setProcedimento(procedimentoTrib);

							RiepilogoSoggettoDto riepilogoSoggetto = new RiepilogoSoggettoDto();

							// Dettaglio soggetto procedimento
							TRelProcSogint dettaglioSoggettoTrib = soggettoService.dettaglioRelSogInt(
									idSoggetto, numReg.getIdUfficio2(), numReg.getIdProc2());
							riepilogoSoggetto.setDettaglioSoggetto(dettaglioSoggettoTrib);

							// MISURE PERSONALI
							List<MisurePersSoggDto> misurePersonaliTrib = soggettoService
									.misurePersonaliSoggetto(idSoggetto, numReg.getIdUfficio2(),
											numReg.getIdProc2());
							procedimentoMerito.setMisurePersonali(misurePersonaliTrib);

							List<MisurePersSoggDto> cauzioniSoggettoTrib = soggettoService.cauzioniSoggetto(
									idSoggetto, numReg.getIdUfficio2(), numReg.getIdProc2());
							procedimentoMerito.setCauzioniSoggetto(cauzioniSoggettoTrib);

							List<MisurePersSoggDto> art67SoggettoTrib = soggettoService
									.art67Soggetto(idSoggetto, numReg.getIdUfficio2(), numReg.getIdProc2());
							procedimentoMerito.setArt67Soggetto(art67SoggettoTrib);

							List<MisurePatrSoggDto> misurePatrimonialiTrib = procedimentoService
									.misurePatrimonialiSoggetto(numReg.getIdUfficio2(), numReg.getIdProc2(),
											idSoggetto, null);
							procedimentoMerito.setMisurePatrimoniali(misurePatrimonialiTrib);

							riepilogoSoggetto.setProcedimentoMerito(procedimentoMerito);

							procedimentiTrib.add(riepilogoSoggetto);
						}
					}

					if (procedimentiTrib != null && !procedimentiTrib.isEmpty()) {
						map.addAttribute("procedimenti", procedimentiTrib);
					}
				}
			}
		}

		if ((soggettoForm.getPressoIndirizzoDomicilioEletto() != null)
				&& "1".equals(soggettoForm.getPressoIndirizzoDomicilioEletto())) {
			soggettoForm.setPressoIndirizzoDomicilioEletto("AVVOCATO");
		} else if ((soggettoForm.getPressoIndirizzoDomicilioEletto() != null)
				&& "2".equals(soggettoForm.getPressoIndirizzoDomicilioEletto())) {
			soggettoForm.setPressoIndirizzoDomicilioEletto("ALTRO");
		}

		addBreadcrumbs();
		return mv;
	}

	@RequestMapping(value = "/ricerche/soggetto/visualizzaRicercaSoggettoFisico.htm", method = RequestMethod.GET)
	private String visualizzaRicercaSoggettoFisico(@Valid SoggettoForm soggettoForm, BindingResult result,
			ModelMap modelMap) {

		// Segnalazione TMC0908
		super.removeFromSession("TIPO_FASCICOLO");
		soggettoForm = new SoggettoForm();
		soggettoForm.setDescrRuolo(Constants.RUOLO_DIRETTO);
		soggettoForm.setIdRuolo(Constants.ID_RUOLO_DIRETTO);
		modelMap.addAttribute("soggettoForm", soggettoForm);
		modelMap.addAttribute("ricerche", "true");
		return "/procedimento/nuovo/ricercaSoggettoFisico";
	}

	@RequestMapping(value = "/ricerche/soggetto/visualizzaRicercaSoggettoGiuridico.htm", method = RequestMethod.GET)
	private String visualizzaRicercaSoggettoGiuridico(@Valid SoggettoForm soggettoForm, BindingResult result,
			ModelMap modelMap) {

		// Segnalazione TMC0908
		super.removeFromSession("TIPO_FASCICOLO");
		soggettoForm = new SoggettoForm();
		soggettoForm.setDescrRuolo(Constants.RUOLO_DIRETTO);
		soggettoForm.setIdRuolo(Constants.ID_RUOLO_DIRETTO);
		modelMap.addAttribute("soggettoForm", soggettoForm);
		modelMap.addAttribute("ricerche", "true");
		return "/procedimento/nuovo/ricercaSoggettoGiuridico";
	}

	@RequestMapping(value = { "/ricerche/soggetto/ricercaSoggettoFisico.htm" }, method = RequestMethod.GET)
	public String paginazioneSoggettoFisico(@Valid SoggettoForm soggettoForm, BindingResult result,
			ModelMap modelMap) {

		modelMap.addAttribute("ricerche", "true");
		// INIZIO tkt EMS-32
		soggettoForm = (SoggettoForm) request.getSession().getAttribute(Constants.Key.DATI_PER_INDIETRO);
		// FINE tkt EMS-32
		modelMap.put("soggettoForm", soggettoForm);
		return modelAndViewRisultatiRicercaSoggettoFisico(modelMap);

	}

	@RequestMapping(value = {
			"/ricerche/soggetto/ricercaSoggettoFisico.htm" }, method = RequestMethod.POST, params = {
					"Ricerca" })
	public String ricercaSoggettoFisico(@Valid SoggettoForm soggettoForm, BindingResult result,
			ModelMap modelMap) {

		modelMap.addAttribute("ricerche", "true");
		request.getSession().removeAttribute(Constants.ID_PROCEDIMENTO);
		soggettoValidation.validateRicercaSoggettoFisico(soggettoForm, result, request);
		if (result.hasErrors()) {
			return "/procedimento/nuovo/ricercaSoggettoFisico";
		}

		request.getSession().setAttribute(Constants.Key.DATI_PER_INDIETRO, soggettoForm);
		addBreadcrumbs("Ricerca", request);

		return modelAndViewRisultatiRicercaSoggettoFisico(modelMap);
	}

	@RequestMapping(value = "/ricerche/soggetto/ricercaSoggettoGiuridico.htm", method = RequestMethod.POST, params = {
			"Ricerca" })
	public String ricercaSoggettoGiuridico(@Valid SoggettoForm soggettoForm, BindingResult result,
			ModelMap modelMap) {

		modelMap.addAttribute("ricerche", "true");
		request.getSession().removeAttribute(Constants.ID_PROCEDIMENTO);
		soggettoValidation.validateRicercaSoggettoGiuridico(soggettoForm, result, request);
		if (result.hasErrors()) {
			return "/procedimento/nuovo/ricercaSoggettoGiuridico";
		}

		// INIZIO tkt EMS-32
		request.getSession().setAttribute(Constants.Key.DATI_PER_INDIETRO, soggettoForm);
		// FINE tkt EMS-32
		addBreadcrumbs("Ricerca", request);

		return modelAndViewRisultatiRicercaSoggettoGiuridico(modelMap);
	}

	@RequestMapping(value = "/ricerche/soggetto/ricercaSoggettoGiuridico.htm", method = RequestMethod.GET)
	public String paginazioneSoggettoGiuridico(@Valid SoggettoForm soggettoForm, BindingResult result,
			ModelMap modelMap) {

		modelMap.addAttribute("ricerche", "true");
		// INIZIO tkt EMS-32
		soggettoForm = (SoggettoForm) request.getSession().getAttribute(Constants.Key.DATI_PER_INDIETRO);
		// FINE tkt EMS-32
		modelMap.put("soggettoForm", soggettoForm);

		return modelAndViewRisultatiRicercaSoggettoGiuridico(modelMap);
	}

	private String modelAndViewRisultatiRicercaSoggettoFisico(ModelMap modelMap) {

		SoggettoForm soggettoForm = (SoggettoForm) modelMap.get("soggettoForm");

		int idUffOwner = (Integer) request.getSession().getAttribute("idUffOwner");
		List<SoggettoForm> listaSoggettoForm = procedimentoService.listaSoggettiByCriteria(idUffOwner,
				soggettoForm.getNome(), soggettoForm.getCognome(),
				String.valueOf(Constants.NO_ITEM_SELECTED).equals(soggettoForm.getStatoNascita().getId())
						? null
						: soggettoForm.getStatoNascita().getId(),
				soggettoForm.getComuneNascita() == null ? null : soggettoForm.getComuneNascita().getId(),
				soggettoForm.getDescrizioneComuneEsteroNascita(),
				Utility.convertDateToString(soggettoForm.getDataNascita()), soggettoForm.getCodiceFiscale(),
				soggettoForm.getCui(), true, null);
		modelMap.addAttribute("risultatiRicerca", listaSoggettoForm);
		return "/procedimento/nuovo/ricercaSoggettoFisico";
	}

	private String modelAndViewRisultatiRicercaSoggettoGiuridico(ModelMap modelMap) {

		SoggettoForm soggettoForm = (SoggettoForm) modelMap.get("soggettoForm");

		int idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
		String piva = StringUtils.isNotEmpty(soggettoForm.getPartitaIvaSiglaStato())
				? soggettoForm.getPartitaIvaSiglaStato()
				: "";
		if (StringUtils.isNotEmpty(soggettoForm.getPartitaIva())) {
			piva.concat(soggettoForm.getPartitaIva().trim());
		}
		List<SoggettoForm> listaSoggettoForm = procedimentoService.listaSoggettiGiurByCriteria(
				idUfficioUtente,
				soggettoForm.getRagioneSociale() != null ? soggettoForm.getRagioneSociale() : null, piva,
				String.valueOf(Constants.NO_ITEM_SELECTED).equals(soggettoForm.getStatoSedeLegale().getId())
						? null
						: Integer.parseInt(soggettoForm.getStatoSedeLegale().getId()),
				soggettoForm.getComuneSedeLegale().getId() == null ? null
						: Integer.parseInt(soggettoForm.getComuneSedeLegale().getId()),
				soggettoForm.getDescrizioneComuneEsteroSedeLegale(), true, null);
		modelMap.addAttribute("risultatiRicercaSoggettoGiuridico", listaSoggettoForm);
		return "/procedimento/nuovo/ricercaSoggettoGiuridico";
	}

	@RequestMapping(value = { "/ricerche/soggetto/ricercaSoggettoFisico.htm",
			"/ricerche/soggetto/ricercaSoggettoGiuridico.htm" }, method = RequestMethod.POST, params = {
					"DettaglioSoggetto" })
	private ModelAndView dettaglioSoggettoDaRicerca(@Valid SoggettoForm soggettoForm, BindingResult result,
			ModelMap map) {

		ModelAndView mv;

		int idUffOwn = RequestUtil.recuperaIdUfficioUtente(request);

		// ricerca procedimenti associati al soggetto
		String tipiFascicolo = String.valueOf(Constants.TIPO_FASCICOLO_MERITO) + ", "
				+ String.valueOf(Constants.TIPO_FASCICOLO_OPP_DIVIETI_QUESTORE) + ", "
				+ String.valueOf(Constants.TIPO_FASCICOLO_ACCERTAMENTO);

		List<ProcedimentoForm> procs = procedimentoService.getListaProcedimentiSoggMultiFascicolo(idUffOwn,
				tipiFascicolo, soggettoForm.getIdSoggetto());

		List<RiepilogoSoggettoDto> procedimenti = new ArrayList<RiepilogoSoggettoDto>();

		soggettoValidation.validateGestioneSoggetto(soggettoForm, result, request);

		if (!result.hasErrors()) {
			mv = new ModelAndView("procedimento/nuovo/riepilogoSoggetto", "soggettoForm", soggettoForm);

			long idSoggetto = soggettoForm.getIdSoggetto();
			// long idVersione = soggettoForm.getIdVersione();

			for (int i = 0; i < procs.size(); i++) {
				ProcedimentoForm procDettaglio = procs.get(i);
				long idProcedimento = procDettaglio.getIdProcedimento();

				RiepilogoSoggettoDto riepilogoSogg = new RiepilogoSoggettoDto();

				// Procedimento ------------------------------------
				DettaglioSoggettoDto procMerito = new DettaglioSoggettoDto();
				procMerito.setProcedimento(procDettaglio);

				// Dettaglio soggetto procedimento
				TRelProcSogint dettaglioSoggetto = soggettoService.dettaglioRelSogInt(idSoggetto, idUffOwn,
						idProcedimento);
				riepilogoSogg.setDettaglioSoggetto(dettaglioSoggetto);

				soggettoForm.setIdUfficio(
						(Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE));
				if ("F".equals(soggettoForm.getTipoSoggetto())) {
					soggettoForm = procedimentoService.caricaValoriDettaglioSoggettoFisico(soggettoForm);
					soggettoForm.setTipoSoggetto(Constants.SOGGETTO_FISICO);
				} else {
					soggettoForm = procedimentoService.caricaValoriDettaglioSoggettoGiuridico(soggettoForm);
					soggettoForm.setTipoSoggetto(Constants.SOGGETTO_GIURIDICO);
				}

				// --------------------------------------------------------

				// Il dettaglio delle misure va visualizzazto solo per i fascicoli non di accertamento
				if (!String.valueOf(Constants.TIPO_FASCICOLO_ACCERTAMENTO)
						.equals(procDettaglio.getTipoFascicolo().getId())) {
					// Misure soggetto nel provedimento di merito
					List<MisurePersSoggDto> misurePersonali = soggettoService
							.misurePersonaliSoggetto(idSoggetto, idUffOwn, idProcedimento);
					procMerito.setMisurePersonali(misurePersonali);

					List<MisurePersSoggDto> cauzioniSoggetto = soggettoService.cauzioniSoggetto(idSoggetto,
							idUffOwn, idProcedimento);
					procMerito.setCauzioniSoggetto(cauzioniSoggetto);

					List<MisurePersSoggDto> art67Soggetto = soggettoService.art67Soggetto(idSoggetto,
							idUffOwn, idProcedimento);
					procMerito.setArt67Soggetto(art67Soggetto);

					List<MisurePatrSoggDto> misurePatrimoniali = procedimentoService
							.misurePatrimonialiSoggetto(idUffOwn, idProcedimento, idSoggetto, idSoggetto);
					procMerito.setMisurePatrimoniali(misurePatrimoniali);
				}

				// ---------------------------------------------------------
				riepilogoSogg.setProcedimentoMerito(procMerito);
				// ---------------------------------------------------------

				if (String.valueOf(Constants.TIPO_FASCICOLO_MERITO)
						.equals(procDettaglio.getTipoFascicolo().getId())) {
					// Procedimento Aggravamento --------------------------------
					List<TProcedimento> procsAggr = procedimentoService.getProcAggravamento(idUffOwn,
							idProcedimento, idSoggetto);

					if (procsAggr != null && procsAggr.size() > 0) {
						List<DettaglioSoggettoDto> procedimentiAggravamento = new ArrayList<DettaglioSoggettoDto>();
						for (int j = 0; j < procsAggr.size(); j++) {
							TProcedimento idProcAggr = procsAggr.get(j);
							Long idProcedimentoAggravamento = idProcAggr.getCodiKIdProc();

							// Dettaglio del procedimento di aggravamento
							ProcedimentoForm procAggravamentoForm = procedimentoService
									.getDettaglioProcedimentoNum(idUffOwn, idProcedimentoAggravamento);

							DettaglioSoggettoDto procAggravamento = new DettaglioSoggettoDto();
							procAggravamento.setProcedimento(procAggravamentoForm);

							/* TRelProcSogint dettaglioSoggettoAggravamento = */soggettoService
									.dettaglioRelSogInt(idSoggetto, idUffOwn, idProcedimentoAggravamento);
							// riepilogoSogg.setDettaglioSoggetto(dettaglioSoggettoAggravamento);
							// ---------------------------------------------------------

							// Misure soggetto nel provedimento di aggravamento
							List<MisurePersSoggDto> misurePersonaliAggravamento = soggettoService
									.misurePersonaliSoggetto(idSoggetto, idUffOwn,
											idProcedimentoAggravamento);
							procAggravamento.setMisurePersonali(misurePersonaliAggravamento);
							procedimentiAggravamento.add(procAggravamento);
						}
						// ---------------------------------------------------------
						riepilogoSogg.setProcedimentiAggravamento(procedimentiAggravamento);
						// ---------------------------------------------------------
					}
					// ---------------------------------------------------------

					// Procedimenti istanze camerali --------------------------------
					List<TProcedimento> procsCamere = procedimentoService.getProcCamere(idUffOwn,
							idProcedimento, idSoggetto);
					if (procsCamere != null && procsCamere.size() > 0) {
						List<DettaglioSoggettoDto> procedimentiCamera = new ArrayList<DettaglioSoggettoDto>();
						for (int j = 0; j < procsCamere.size(); j++) {
							TProcedimento idProcCamera = procsCamere.get(j);
							Integer tipoProcFascCamera = idProcCamera.getTTpTipoProc().getCodiKIdTipoProc();
							Long idProcedimentoCamera = idProcCamera.getCodiKIdProc();
							// Dettaglio del procedimento di camera di consiglio
							ProcedimentoForm procCameraForm = procedimentoService
									.getDettaglioProcedimentoNum(idUffOwn, idProcedimentoCamera);
							procCameraForm.setTipoProcedimento(new ComboForm("" + tipoProcFascCamera, null));

							DettaglioSoggettoDto procCamera = new DettaglioSoggettoDto();
							procCamera.setProcedimento(procCameraForm);

							/* TRelProcSogint dettaglioSoggettoCamera = */soggettoService
									.dettaglioRelSogInt(idSoggetto, idUffOwn, idProcedimentoCamera);
							// riepilogoSogg.setDettaglioSoggetto(dettaglioSoggettoAggravamento);
							// ---------------------------------------------------------

							// TIPOPROC_MODIFICA_PATRIMONIALE_CAM
							// TIPOPROC_MODIFICA_PERSONALE_CAM
							// TIPOPROC_PERMESSI_CAM
							// TIPOPROC_CAUZIONI_CAM
							// TIPOPROC_TERZO_INTERVENIENTE_CAM

							// non gestiti
							// TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM
							// TIPOPROC_CORREZIONE_ERRORI_CAM

							// Misure soggetto nel provedimento di camera di consiglio
							if (tipoProcFascCamera == Constants.TIPOPROC_MODIFICA_PERSONALE_CAM
									|| tipoProcFascCamera == Constants.TIPOPROC_PERMESSI_CAM
									|| tipoProcFascCamera == Constants.TIPOPROC_CAUZIONI_CAM) {
								List<MisurePersSoggDto> misurePersonali = soggettoService
										.misurePersonaliSoggetto(idSoggetto, idUffOwn, idProcedimentoCamera);
								procCamera.setMisurePersonali(misurePersonali);

								List<MisurePersSoggDto> cauzioniSoggetto = soggettoService
										.cauzioniSoggetto(idSoggetto, idUffOwn, idProcedimentoCamera);
								procCamera.setCauzioniSoggetto(cauzioniSoggetto);

								List<MisurePersSoggDto> art67Soggetto = soggettoService
										.art67Soggetto(idSoggetto, idUffOwn, idProcedimentoCamera);
								procCamera.setArt67Soggetto(art67Soggetto);
							}

							if (tipoProcFascCamera == Constants.TIPOPROC_MODIFICA_PATRIMONIALE_CAM
									|| tipoProcFascCamera == Constants.TIPOPROC_TERZO_INTERVENIENTE_CAM) {
								List<MisurePatrSoggDto> misurePatrimoniali = procedimentoService
										.misurePatrimonialiSoggetto(idUffOwn, idProcedimentoCamera,
												idSoggetto, idSoggetto);
								procCamera.setMisurePatrimoniali(misurePatrimoniali);
							}
							procedimentiCamera.add(procCamera);
						}
						// ---------------------------------------------------------
						riepilogoSogg.setProcedimentiCamera(procedimentiCamera);
						// ---------------------------------------------------------
					}
					// ---------------------------------------------------------
				}
				// fine dettaglio fascicolo
				procedimenti.add(riepilogoSogg);
			}
			map.addAttribute("procedimenti", procedimenti);
		} else {
			String page;
			StringBuffer path = request.getRequestURL();
			// INIZIO tkt EMS-32
			// if(path.indexOf("Fisico") !=-1) page = visualizzaRicercaSoggettoFisico(soggettoForm, result,
			// map);
			// else page = visualizzaRicercaSoggettoGiuridico(soggettoForm, result, map);
			if (path.indexOf("Fisico") != -1)
				page = modelAndViewRisultatiRicercaSoggettoFisico(map);
			else
				page = modelAndViewRisultatiRicercaSoggettoGiuridico(map);
			map.addAttribute("ricerche", "true");
			// FINE tkt EMS-32
			mv = new ModelAndView(page);
		}

		return mv;
	}

	@RequestMapping(value = "/procedimento/soggetto/visualizzaDirettiInteressati.htm", method = RequestMethod.POST, params = {
			"DettaglioAmministratore" })
	public ModelAndView visualizzaDettaglioAmministratore(SoggettoForm soggettoForm, BindingResult result,
			ModelMap map) {

		ProcedimentoForm procedimentoForm = (ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO));
		if (soggettoForm.getIdSoggetto() == null) {
			if (procedimentoForm.getTipoProcedimento() != null
					&& Long.toString(Constants.TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM)
							.equals(procedimentoForm.getTipoProcedimento().getId())) {
				if (soggettoForm.getIdSoggetto() == null) {
					result.reject(Constants.MESSAGE);
					super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
					if (request.getSession().getAttribute("RuoloSession") != null && Constants.RUOLO_TERZO
							.equals(request.getSession().getAttribute("RuoloSession"))) {
						request.getSession().removeAttribute("RuoloSession");
						return visualizzaSoggettiDirettiInteressatiTerzi(soggettoForm);
					} else
						return visualizzaSoggettiDirettiInteressati(soggettoForm);
				}
			}
		}
		long idProcedimento = procedimentoForm.getIdProcedimento();
		int idUffOwn = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		long idAmministratore = soggettoForm.getIdSoggetto();

		if (procedimentoForm.getTipoProcedimento() != null
				&& Long.toString(Constants.TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM)
						.equals(procedimentoForm.getTipoProcedimento().getId())) {
			List<AmministratoreForm> amms = beneService.getAmministratori(idUffOwn, idProcedimento, null,
					null, true);
			if (amms != null && !amms.isEmpty())
				idAmministratore = amms.get(0).getId();
		}

		List<AmministratoreForm> amministratoreSel = beneService.getAmministratoreById(idAmministratore);
		AmministratoreForm amministratoreForm = amministratoreSel.get(0);
		amministratoreForm.setId(idAmministratore);
		if (amministratoreForm.getComuneEsteroLegale() != null) {
			amministratoreForm
					.setComuneDomProf(new ComboForm(null, amministratoreForm.getComuneEsteroLegale()));
		}
		amministratoreForm.setDettaglio("DETTAGLIO");
		map.addAttribute("amministratoreForm", amministratoreForm);
		addBreadcrumbs("DettaglioAmministratore", request);
		return new ModelAndView("/procedimento/bene/amministratore/amministratoreAnagrafica",
				"amministratoreForm", amministratoreForm);
	}

	@RequestMapping(value = "/procedimento/soggetto/difensoriAssociaNuovo.htm", method = RequestMethod.POST, params = {
			"SalvaDifensore" })
	public ModelAndView salvaDifensoreAssociato(@Valid DifensoreSoggettoForm difensoreSoggettoForm,
			BindingResult result) {

		caricaValoriDettaglioSoggetto(difensoreSoggettoForm.getSoggettoForm());
		soggettoValidation.validateSalvaDifensoreAssociaNuovo(difensoreSoggettoForm, result);
		if (result.hasErrors())
			return new ModelAndView("procedimento/soggetto/difensoriAssociaNuovo", "difensoreSoggettoForm",
					difensoreSoggettoForm);

		String codiceUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);
		if (Long.toString(Constants.TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM)
				.equals(((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO)))
						.getTipoProcedimento().getId())
				|| Long.toString(Constants.TIPOPROC_CORREZIONE_ERRORI_CAM).equals(
						((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO)))
								.getTipoProcedimento().getId()))
			procedimentoService.inserisciModificaDifensoreAssociatoIntestatario(difensoreSoggettoForm,
					codiceUtente);
		else
			procedimentoService.inserisciModificaDifensoreAssociatoSoggetto(difensoreSoggettoForm,
					codiceUtente);
		difensoreSoggettoForm.setSoggettoForm(procedimentoService
				.caricaValoriGratuitoPatrocinioDelSoggetto(difensoreSoggettoForm.getSoggettoForm()));
		if (difensoreSoggettoForm.getSoggettoForm().getGratuitoPatrocinio() == true) {
			difensoreSoggettoForm.setGratuitoPatrocinioCaricato(true);
		} else {
			difensoreSoggettoForm.setGratuitoPatrocinioCaricato(false);
		}
		// altrimenti cancella il difensore anche senza selezionarlo
		difensoreSoggettoForm.getDifensoreForm().setIdDifensore(null);

		removeLast("POST", "/SMP2/procedimento/soggetto/gestioneDifensori.htm", "AbilitaRicerca", "",
				request.getSession());
		addBreadcrumbs("POST", "/SMP2/procedimento/soggetto/visualizzaDirettiInteressati.htm", "Difensori");

		return difensoriAssociatiJsp(difensoreSoggettoForm, result);
	}

}