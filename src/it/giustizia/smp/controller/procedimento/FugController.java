package it.giustizia.smp.controller.procedimento;

import it.giustizia.smp.controller.BreadcrumbsController;
import it.giustizia.smp.controller.LoginController;
import it.giustizia.smp.dao.CommonDao;
import it.giustizia.smp.dao.FugDao;
import it.giustizia.smp.dao.StaticListLoader;
import it.giustizia.smp.dto.TRelBeneBeneDto;
import it.giustizia.smp.exception.EccezioneGenerica;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.BeneForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.FugForm;
import it.giustizia.smp.form.FugModelloForm;
import it.giustizia.smp.form.IndirizzoForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.model.TAnOperFinanz;
import it.giustizia.smp.model.TAnUfficio;
import it.giustizia.smp.model.TFugInvio;
import it.giustizia.smp.model.TUtente;
import it.giustizia.smp.service.BeneService;
import it.giustizia.smp.service.CommonService;
import it.giustizia.smp.service.FugService;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.validation.FugValidation;
import it.giustizia.smp.webservice.client.fug.AcquisisciModelloA;
import it.giustizia.smp.webservice.client.fug.AcquisisciModelloC;
import it.giustizia.smp.webservice.client.fug.AcquisisciModelloC2;
import it.giustizia.smp.webservice.client.fug.AcquisisciModelloD;
import it.giustizia.smp.webservice.client.fug.AcquisisciModelloH;
import it.giustizia.smp.webservice.client.fug.RichiestaAnnullamentoModello;
import it.giustizia.smp.webservice.client.fug.modelli.RispostaModello;
import it.giustizia.smp.webservice.utility.JAXBUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FugController extends BreadcrumbsController {
	// F.C. 18/10/2023 Ticket#20231011013 — SITMP - Invio FUG (RITM0705401)
	//corretto settaggio classe per logger
	protected Logger logger	= Logger.getLogger(getClass());
	@Autowired
	private FugValidation fugValidation;
	@Autowired
	private FugService fugService;
	@Autowired
	private BeneService beneService;
	@Autowired
	private FugDao fugDao;
	@Autowired
	private CommonDao commonDao;
	
	private List<ComboForm> valuta;
	
	@RequestMapping(value = "/procedimento/bene/fug/beniFug.htm", method = RequestMethod.GET)
	public String visualizzaBeniFug(BeneForm beneForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {

		ProcedimentoForm procedimento = recuperaProcedimento();
		if(procedimento == null){
			super.setMessage(Constants.MESSAGE, "error.seleziona.procedimento", null, true);
			return "/procedimento/bene/fug/beniFug";
		}
		if(Integer.parseInt(procedimento.getTipoFascicolo().getId())!=Constants.TIPO_FASCICOLO_MERITO){
			super.setMessage(Constants.MESSAGE, "error.seleziona.procedimento.prevenzione", null, true);
			return "/procedimento/bene/fug/beniFug";
		}	
		long idProcedimento = procedimento.getIdProcedimento();
		List<BeneForm> beniFug = new ArrayList<BeneForm>();
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
		beniFug = fugService.listaBeniFug(idUfficioUtente, idProcedimento, beneForm);
		
		modelMap.addAttribute("risultatiRicerca", beniFug);
		addBreadcrumbs("GET", "/procedimento/bene/fug/beniFug.htm", null);

		return "/procedimento/bene/fug/beniFug";
	}

	@RequestMapping(value = "/procedimento/bene/fug/beniFug.htm", method = RequestMethod.POST, params = { "Dettaglio" })
	public String dettaglioBeneFug(BeneForm beneForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {
		
		// P.Q. Inizio MEV 2021/19 Per il denaro (MODELLO A) il versato su deve avere i valori C/C , Libretto e Libretto Depos. Giud.
		boolean isModelloA = true;
		if (beneForm.getModelloFugSelected() != null && beneForm.getModelloFugSelected().getId() != null) {
			isModelloA = beneForm.getModelloFugSelected().getId().equals("A") ||
					     beneForm.getModelloFugSelected().getId().equals("C") ||
					     beneForm.getModelloFugSelected().getId().equals("C2") ||
					     beneForm.getModelloFugSelected().getId().equals("D") ||
					     beneForm.getModelloFugSelected().getId().equals("-1");
		}
		List<ComboForm> listaVersato = riempiComboVersato(isModelloA);
		modelMap.addAttribute("isModelloA", isModelloA);
		modelMap.addAttribute("listaVersato", listaVersato);
		// P.Q. Inizio MEV 2021/19 Per il denaro (MODELLO A) il versato su deve avere i valori C/C , Libretto e Libretto Depos. Giud.
		
		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		Integer idUffOwner = recuperaIdUfficioUtente();
		
		if (beneForm.getDettAssBene()==null){
			if (!result.hasErrors()){
				fugValidation.validateSceltaCheck(beneForm, result, request);
				if (result.hasErrors()) {
					return visualizzaBeniFug(beneForm, result, request, modelMap);
				}
				beneForm.setIdBene(beneForm.getListaIdCheckbox().get(0));
			}	
		}
		
		BeneForm bene = beneService.beneById(idUffOwner, beneForm.getIdBene(), idProcedimento);
		
		addBreadcrumbs("POST", "/procedimento/bene/fug/beniFug.htm", "Dettaglio");	
		
		bene.setDettAssBene(null);
		
		if (bene.getIndirizzoBeneFin() == null) {
			if(bene.getCodiceAbi() != null && bene.getCodiceCab().getId() != null){
			BeneForm beneForm2 = beneService.caricaInfoOperFinanz(bene.getCodiceAbi(), bene.getCodiceCab().getId());
				IndirizzoForm indirizzo = Utility.costruisciIndirizzo(beneForm2);
				bene.setIndirizzoBeneFin(indirizzo.getDenominazione());	
			}
		}
		bene.setModelloFugSelected(beneForm.getModelloFugSelected());
		
		beneForm.setDisabledValutaEst(false);
		//F.C. 08/11/2021 il cliente ha chiesto che nel caso il bene FUG sia associato ad un ente fittizio
		//i dati di tale ente non debbano essere visualizzati nel dettaglio e non sia possibile confermare il modello
		//L'utente dovrà entrare nella funzionalita' di modifica bene e specificare un bene
		//diverso da ente fittizio per poter procedere.
		bene = this.verificaDettBeneEnteFittizio(bene);
		if (bene.isEnteFittizio()) 
			beneForm.setEnteFittizio(true);
		
		else 
			beneForm.setEnteFittizio(false);
		
		modelMap.addAttribute("isPolizzaNonBancaria", Utility.isPolizzaNonBancaria(bene.getCategoria(), bene.getSottocategoria()));
		modelMap.addAttribute("beneForm", bene);
		fugValidation.parseNumBollettino(bene, modelMap, request);
		
		return "/procedimento/bene/fug/dettaglioBeneFug";
	}

	
	@RequestMapping(value = "/procedimento/bene/fug/dettaglioBeneFug.htm", method = RequestMethod.POST)
	public String postDettaglioBeneFug(BeneForm beneForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {
		
		// P.Q. Inizio MEV 2021/19 Per il denaro (MODELLO A) il versato su deve avere i valori C/C , Libretto e Libretto Depos. Giud.
		boolean isModelloA = true;
		if (beneForm.getModelloFugSelected() != null && beneForm.getModelloFugSelected().getId() != null) {
			isModelloA = beneForm.getModelloFugSelected().getId().equals("A") ||
				     	 beneForm.getModelloFugSelected().getId().equals("C") ||
				     	 beneForm.getModelloFugSelected().getId().equals("C2") ||
				     	 beneForm.getModelloFugSelected().getId().equals("D") ||
				     	 beneForm.getModelloFugSelected().getId().equals("-1");
		}
		
		List<ComboForm> listaVersato = riempiComboVersato(isModelloA);
		modelMap.addAttribute("isModelloA", isModelloA);
		modelMap.addAttribute("listaVersato", listaVersato);
		// P.Q. Fine MEV 2021/19 Per il denaro (MODELLO A) il versato su deve avere i valori C/C , Libretto e Libretto Depos. Giud.		
		
		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		Integer idUffOwner = recuperaIdUfficioUtente();
		
		BeneForm bene = beneService.beneById(idUffOwner, beneForm.getIdBene(), idProcedimento);
		
		if (beneForm.getTipoValuta() != null) {
			if (beneForm.getTipoValuta().getId().equals("-1")) {
				beneForm.setDisabledValutaEst(true);
				beneForm.setValutaEst(null);
				bene.setValutaEst(null);
			} else {
				beneForm.setDisabledValutaEst(false);
				bene.setValutaEur(null);
			}
		} else {
			beneForm.setDisabledValutaEst(true);
		}
		
		bene.setTipoValuta(new ComboForm(beneForm.getTipoValuta().getId(), beneForm.getTipoValuta().getDescrizione()));
		
		// P.Q. Inizio MEV 2021/19 Per il denaro (MODELLO A) il versato su deve avere i valori C/C , Libretto e Libretto Depos. Giud.
		bene.setModelloFugSelected(new ComboForm(beneForm.getModelloFugSelected().getId(), beneForm.getModelloFugSelected().getDescrizione()));		
		// P.Q. Fine MEV 2021/19 Per il denaro (MODELLO A) il versato su deve avere i valori C/C , Libretto e Libretto Depos. Giud.		
		
		modelMap.addAttribute("isPolizzaNonBancaria", Utility.isPolizzaNonBancaria(bene.getCategoria(), bene.getSottocategoria()));
		modelMap.addAttribute("beneForm", bene);
		fugValidation.parseNumBollettino(bene, modelMap, request);
		
		// P.Q. Inizio MEV 2021/19
		// Non visualizzo ENTE FITTIZIO nella pagina di dettaglio
		verificaDettBeneEnteFittizio(bene);
		//P.Q. Fine MEV 2021/19 
		
		return "/procedimento/bene/fug/dettaglioBeneFug";
	}
	
	@RequestMapping(value = "/procedimento/bene/fug/dettaglioBeneFug.htm", method = RequestMethod.POST, params = { "Modifica" })
	public String modificaBene(BeneForm beneForm, BindingResult result, ModelMap modelMap, HttpServletRequest request) {

		// P.Q. Inizio MEV 2021/19 Per il denaro (MODELLO A) il versato su deve avere i valori C/C , Libretto e Libretto Depos. Giud.
		boolean isModelloA = true;
		if (beneForm.getModelloFugSelected() != null && beneForm.getModelloFugSelected().getId() != null) {
			isModelloA = beneForm.getModelloFugSelected().getId().equals("A") ||
						 beneForm.getModelloFugSelected().getId().equals("C") ||
						 beneForm.getModelloFugSelected().getId().equals("C2") ||
						 beneForm.getModelloFugSelected().getId().equals("D") ||
						 beneForm.getModelloFugSelected().getId().equals("-1");
		}
		
		List<ComboForm> listaVersato = riempiComboVersato(isModelloA);
		modelMap.addAttribute("isModelloA", isModelloA);
		modelMap.addAttribute("listaVersato", listaVersato);
		// P.Q. Inizio MEV 2021/19 Per il denaro (MODELLO A) il versato su deve avere i valori C/C , Libretto e Libretto Depos. Giud.		
		
		ProcedimentoForm procedimentoForm = recuperaProcedimento();
		
		long idprocedimento = procedimentoForm.getIdProcedimento();
		Integer idUffOwner = recuperaIdUfficioUtente();
		String codUtente = recuperaIdCodiceUtente();
		
		//F.C. SITMP-387 Invio bene FUG
		//Quando carica il dettaglio bene fug le informazioni su tipologia, categoria e sottocategoria vengono resettati
		BeneForm bene = beneService.beneById(idUffOwner, beneForm.getIdBene(), idprocedimento);
		beneForm.setTipologia(bene.getTipologia());
		beneForm.setCategoria(bene.getCategoria());
		beneForm.setSottocategoria(bene.getSottocategoria());
		//Fine modifica F.C.
		
		modelMap.addAttribute("beneForm", beneForm);
		fugValidation.parseNumBollettino(beneForm, modelMap, request);
		
		fugValidation.validaAggBene(beneForm, result);
		if (result.hasErrors()) {
			beneForm.setDettAssBene("Dettaglio");
			super.setMessage(Constants.MESSAGE, "errors.campi.controllo", null, false);
			return "/procedimento/bene/fug/dettaglioBeneFug";
		}

	//Nel bene imposto l'ABI e il CAB a partire dall'IBAN
	    beneForm = this.impostaAbiCabDaIBAN(beneForm);
		
		beneService.modificaBene(idUffOwner, codUtente, idprocedimento, beneForm);
		
		beneForm.setDettAssBene("Modifica");
		super.setMessage(Constants.SUCCESS_MESSAGE, "success", null, true);
		return dettaglioBeneFug(beneForm, result, request, modelMap);
	}
	
	@RequestMapping(value = "/procedimento/bene/fug/dettaglioBeneFug.htm", method = RequestMethod.POST, params = { "ConfermaModello" })
	public String elencoQuoteModello(BeneForm beneForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {
		
		// P.Q. Inizio MEV 2021/19 Per il denaro (MODELLO A) il versato su deve avere i valori C/C , Libretto e Libretto Depos. Giud.
		boolean isModelloA = true;
		if (beneForm.getModelloFugSelected() != null && beneForm.getModelloFugSelected().getId() != null) {
			isModelloA = beneForm.getModelloFugSelected().getId().equals("A") ||
						 beneForm.getModelloFugSelected().getId().equals("C") ||
						 beneForm.getModelloFugSelected().getId().equals("C2") ||
						 beneForm.getModelloFugSelected().getId().equals("D") ||
						 beneForm.getModelloFugSelected().getId().equals("-1");
		}
		
		List<ComboForm> listaVersato = riempiComboVersato(isModelloA);
		modelMap.addAttribute("isModelloA", isModelloA);
		modelMap.addAttribute("listaVersato", listaVersato);
		// P.Q. Inizio MEV 2021/19 Per il denaro (MODELLO A) il versato su deve avere i valori C/C , Libretto e Libretto Depos. Giud.		
		
		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		Integer idUffOwner = recuperaIdUfficioUtente();
		String codUtente = recuperaIdCodiceUtente();
		
		//F.C. SITMP-387 Invio bene FUG
		//Quando carica il dettaglio bene fug le informazioni su tipologia, categoria e sottocategoria vengono resettati
		BeneForm bene = beneService.beneById(idUffOwner, beneForm.getIdBene(), idProcedimento);
		beneForm.setTipologia(bene.getTipologia());
		beneForm.setCategoria(bene.getCategoria());
		beneForm.setSottocategoria(bene.getSottocategoria());
		//Fine modifica F.C.
		
		if (!result.hasErrors()) {
			//Aggiorno il bene con ABI e il CAB ricavati dall'IBAN
		    beneForm = this.impostaAbiCabDaIBAN(beneForm);
						
			fugValidation.validaBene(beneForm, result);
			if (result.hasErrors()) {
				beneForm.setDettAssBene("Dettaglio");
				super.setMessage(Constants.MESSAGE, "errors.campi.controllo", null, false);
				modelMap.addAttribute("beneForm", beneForm);
				fugValidation.parseNumBollettino(beneForm, modelMap, request);
				return "/procedimento/bene/fug/dettaglioBeneFug";
			}	
			//F.C. modifica 11/11/2021 nel caso di ente fittizio viene 
			//visualizzato il messaggio d'errore e l'utente non puo' 
			//inviare il modello.
			fugValidation.validaBeneOpFittizioFug(beneForm, result);
			if (result.hasErrors()) {
				beneForm.setDettAssBene("Dettaglio");
				
				super.setMessage(Constants.MESSAGE, "error.bene.fug.ente.fittizio", null, false);
				modelMap.addAttribute("beneForm", beneForm);
				fugValidation.parseNumBollettino(beneForm, modelMap, request);
				return "/procedimento/bene/fug/dettaglioBeneFug";
			}	
			
			beneService.modificaBene(idUffOwner, codUtente, idProcedimento, beneForm);
			
		}
		
		beneForm.setIdBene(beneForm.getIdBene());
		bene = beneService.beneById(idUffOwner, beneForm.getIdBene(), idProcedimento);
		
		bene.setIdUffOwner(idUffOwner);
		bene.setIdProcedimento(idProcedimento);
		bene.setDettAssBene(null);
		String modelloSelected = beneForm.getModelloFugSelected().getId();
		bene.setModelloFugSelected(new ComboForm(modelloSelected,"MODELLO "+modelloSelected))  ;
		bene.setNumBollettino(beneForm.getNumBollettino());
		
		modelMap.addAttribute("beneForm", bene);
		fugValidation.parseNumBollettino(bene, modelMap, request);
		modelMap.remove("ceckAutomatico");
		
		if (!result.hasErrors()){
			fugValidation.validateComboModello(beneForm, result, request);
			if (result.hasErrors()) {
				bene.setDettAssBene("Dettaglio");
				super.setMessage(Constants.MESSAGE, "errors.campi.controllo", null, false);
				return "/procedimento/bene/fug/dettaglioBeneFug";
			}
		}
	
		if ((Constants.FUG_MODELLO_C_BIS).equals(beneForm.getModelloFugSelected().getId())){
			FugForm fugForm = new FugForm();
			
			fugForm.setIdUfficio(idUffOwner);
			fugForm.setIdProcedimento(idProcedimento);
			fugForm.setIdBene(bene.getIdBene());
			fugForm.setDescBene(bene.getDescrizioneBene());
			
			List<FugForm> modelliCconIdEquitalia = new ArrayList<FugForm>();
			try {
		    //Cerca i modelli C inviati e acquisiti da Equitalia a fronte dei quali bisogna inviare il modello C-Bis
				modelliCconIdEquitalia = recuperaModelliCAcquisitiDaEquitalia(fugForm, codUtente, request);
			} catch (EccezioneGenerica e) {
				super.setMessage(Constants.MESSAGE, e, false);
				return "/procedimento/bene/fug/dettaglioBeneFug";
			}

			if (modelliCconIdEquitalia.size()<2){
				modelMap.addAttribute("ceckAutomatico", "S");
			}
			
			modelMap.addAttribute("fugForm", fugForm);
			modelMap.addAttribute("modelliInviati", modelliCconIdEquitalia);
			
			addBreadcrumbs("POST", "/procedimento/bene/fug/dettaglioBeneFug.htm", "ConfermaModello");
			return "/procedimento/bene/fug/elencoQuoteCBis";
		}
		if ((Constants.FUG_MODELLO_H).equals(beneForm.getModelloFugSelected().getId())){
			String showJSP = "";
			TRelBeneBeneDto dati = fugService.datiModelloH(idUffOwner, idProcedimento, bene, codUtente, super.printMethodName());
			if (dati.getDATA_TRASF() == null) {
				super.setMessage(Constants.MESSAGE, "error.modelloH", null, false);
				showJSP = "/procedimento/bene/fug/dettaglioBeneFug";
			} else {
				bene.setDataTrasferimento(dati.getDATA_TRASF());
				showJSP = "/procedimento/bene/fug/dettaglioBeneFugModelloH";
			}
			return showJSP;
		} else { // Gestione modelli A, D, C
			List<AttoForm> listaQuote = fugService.elencoQuoteModello(idUffOwner, idProcedimento, bene, codUtente, super.printMethodName());	
		
			if (listaQuote.isEmpty()){
				if ((Constants.FUG_MODELLO_A).equals(beneForm.getModelloFugSelected().getId())){
//					super.setMessage(Constants.MESSAGE, "error.modelloA.nonCongruente", null, false);
// mev 343 Ginesti -- sostituzione messaggio errore					
//					super.setMessage(Constants.MESSAGE, "error.modelloA.Sequestro.NonConvalidato", null, false);
					super.setMessage(Constants.MESSAGE, "error.modelloA.Sequestro.ProblemiTecnici", null, false);
// fine mev 343					
				}
				if ((Constants.FUG_MODELLO_D).equals(beneForm.getModelloFugSelected().getId())){
					super.setMessage(Constants.MESSAGE, "error.modelloD.nonCongruente", null, false);
				}
				if ((Constants.FUG_MODELLO_C).equals(beneForm.getModelloFugSelected().getId())){
					super.setMessage(Constants.MESSAGE, "error.modelloC.nonCongruente", null, false);
				}
				return "/procedimento/bene/fug/dettaglioBeneFug";
			}
			
			if (listaQuote.size()<2){
				modelMap.addAttribute("ceckAutomatico", "S");
			}
			
			modelMap.addAttribute("listaQuote", listaQuote);
			
			addBreadcrumbs("POST", "/procedimento/bene/fug/dettaglioBeneFug.htm", "ConfermaModello");
			return "/procedimento/bene/fug/elencoQuoteModello";
		}
		
	}

	private List<FugForm> recuperaModelliCAcquisitiDaEquitalia(FugForm fugForm, String codUtente, HttpServletRequest request) throws EccezioneGenerica {
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
		List<FugForm> modelliC = fugService.elencoModelliC(idUfficioUtente, fugForm, codUtente, super.printMethodName());
		List<FugForm> modelliCconIdEquitalia = new ArrayList<FugForm>();
		for (FugForm el : modelliC) {
			if (hasIdRicevutaEquitalia(el)) modelliCconIdEquitalia.add(el);
		}
		if (modelliCconIdEquitalia.isEmpty()) {
			throw new EccezioneGenerica((modelliC.isEmpty() ? "error.modelloCBis" : "error.modelloCBis.nonAcquisito"));
		}
		return modelliCconIdEquitalia;
	}

	private List<FugForm> datiModelloH(FugForm fugForm, String codUtente, HttpServletRequest request) throws EccezioneGenerica {
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
		List<FugForm> modelliC = fugService.elencoModelliC(idUfficioUtente, fugForm, codUtente, super.printMethodName());
		List<FugForm> modelliCconIdEquitalia = new ArrayList<FugForm>();
		for (FugForm el : modelliC) {
			if (hasIdRicevutaEquitalia(el)) modelliCconIdEquitalia.add(el);
		}
		if (modelliCconIdEquitalia.isEmpty()) {
			throw new EccezioneGenerica((modelliC.isEmpty() ? "error.modelloCBis" : "error.modelloCBis.nonAcquisito"));
		}
		return modelliCconIdEquitalia;
	}

	@RequestMapping(value = "/procedimento/bene/fug/paginazioneelencoQuoteModello.htm", method = RequestMethod.GET)
	public String paginazioneelencoQuoteModello(BeneForm beneForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {

		return elencoQuoteModello(beneForm, result, request, modelMap);
	}
	
	@RequestMapping(value = "/procedimento/bene/fug/paginazioneelencoSoggettiCBis.htm", method = RequestMethod.GET)
	public String paginazioneelencoSoggettiCBis(FugForm fugForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {

		return elencoSoggettiModelloCBis(fugForm, result, request, modelMap);
	}
	
//mostra la lista dei soggetti per i quali deve essere mandato il modello C bis  	
	@RequestMapping(value = "/procedimento/bene/fug/elencoQuoteCBis.htm", method = RequestMethod.POST, params = { "Seleziona" })
	public String elencoSoggettiModelloCBis(FugForm fugForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {
		
		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		Integer idUffOwner = recuperaIdUfficioUtente();
		
		BeneForm beneForm = beneService.beneById(idUffOwner, fugForm.getIdBene(), idProcedimento);
		
		beneForm.setIdUffOwner(idUffOwner);
		beneForm.setIdProcedimento(idProcedimento);
		beneForm.setDettAssBene(null);
		String modelloSelected = Constants.FUG_MODELLO_C_BIS;
		beneForm.setModelloFugSelected(new ComboForm(modelloSelected,"MODELLO C-bis"))  ;
		
		long idInvioSelezinato = fugForm.getIdInvio();
		TFugInvio tFugInvio = fugDao.getTFugInvioById(idInvioSelezinato);
		
		if (!result.hasErrors()){
			fugValidation.validateSelLista(idInvioSelezinato, result, request);
			if (result.hasErrors()) {
				return elencoQuoteModello(beneForm, result, request, modelMap);
			}	
		}
		
		beneForm.setListaSoggetti(fugService.getListaSoggettiBeneficiari(idUffOwner, idProcedimento, beneForm, tFugInvio.getTAtto().getId().getCodiKIdAtto(),null,Constants.FUG_MODELLO_C_BIS));
		
		if (beneForm.getListaSoggetti().size()<2){
			modelMap.addAttribute("ceckAutomatico", "S");
		}
		
		beneForm.setIdInvio(idInvioSelezinato);
		beneForm.setProtocolloRif(tFugInvio.getDescProtocollo());
		beneForm.setCodAtto(tFugInvio.getTAtto().getId().getCodiKIdAtto());
		beneForm.setIdBene(tFugInvio.getTBene().getId().getCodiKIdBene());
		modelMap.addAttribute("beneForm", beneForm);
			
		addBreadcrumbs("POST", "/procedimento/bene/fug/elencoSoggettiCBis.htm", null);
		return "/procedimento/bene/fug/elencoSoggettiCBis";
		
	}
	
	@RequestMapping(value = { "/procedimento/bene/fug/elencoQuoteModello.htm", "/procedimento/bene/fug/elencoSoggettiCBis.htm", "/procedimento/bene/fug/dettaglioBeneFug.htm" }, method = RequestMethod.POST, params = { "InviaModello" })
	public String inviaModello(BeneForm beneForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {
		
		long idAttoSelezinato = beneForm.getCodAtto();
		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		TUtente utente = (TUtente) (request.getSession().getAttribute(Constants.ID_UTENTE));
		Integer idUffOwner = recuperaIdUfficioUtente();
		String descUtente = recuperaIdCodiceUtente();
		
		beneForm.setIdBene(beneForm.getIdBene());
		BeneForm beneModello = beneService.beneById(idUffOwner, beneForm.getIdBene(), idProcedimento);
		String importoBeneTotale = Utility.getImporto(beneModello);
		
		TAnUfficio ufficioCorrente = commonDao.getUfficioById((int)idUffOwner);
		
		FugForm fugForm = new FugForm(); 
		if (beneForm.getModelloFugSelected().getId().equals(Constants.FUG_MODELLO_C_BIS)) {
			fugForm.setIdInvio(beneForm.getIdInvio());
			fugForm.setIdAtto(beneForm.getIdAtto());
			fugForm.setIdBene(beneForm.getIdBene());
			if(beneForm.getIdSelezionato() == null){
				super.setMessage(Constants.MESSAGE,"table.item.selection",null,false);
				return elencoSoggettiModelloCBis(fugForm, result, request, modelMap);
			}	
			if((beneForm.getDataNotifica() == null && beneForm.getDataIrrevocabileRevoca() == null) ||
					(beneForm.getDataNotifica() != null && beneForm.getDataIrrevocabileRevoca() != null)){
				super.setMessage(Constants.MESSAGE,"error.dataNotifica.dataIrrRev",null,false);
				return elencoSoggettiModelloCBis(fugForm, result, request, modelMap);
			}	
			beneModello.setIdSoggetto(beneForm.getIdSelezionato());
			beneModello.setProtocolloRif(beneForm.getProtocollo());
			beneModello.setDataNotifica(beneForm.getDataNotifica());
			beneModello.setDataIrrevocabileRevoca(beneForm.getDataIrrevocabileRevoca());
		}
		
		beneModello.setUffMittente(ufficioCorrente.getDescUffGiudiz());
		beneModello.setIdAtto(idAttoSelezinato);
		beneModello.setNumeAtto(beneForm.getNumeAtto());
		beneModello.setIdProcedimento(idProcedimento);
		beneModello.setCancelliere(utente.getCodiceFiscale());
		beneModello.setCodiceOperatore(getCodiceOperatore(beneModello));
		beneModello.setDettAssBene("Dettaglio");
		beneModello.setModelloFugSelected(new ComboForm(beneForm.getModelloFugSelected().getId(),"MODELLO "+beneForm.getModelloFugSelected().getId()));
		beneModello.setDataDeposito(beneForm.getDataDeposito());
		beneModello.setDataDefinitivita(beneForm.getDataDefinitivita());
		beneModello.setDataTrasferimento(beneForm.getDataTrasferimento());
		beneModello.setCodAtto(beneForm.getCodAtto());
		beneModello.setIdInvio(beneForm.getIdInvio()); // Identificativo dell'invio C per il C-Bis 
		// P.Q. Inizio MEV 2021/19
		if(beneForm.getNumBollettino() != null) {
		beneModello.setNumBollettino(beneForm.getNumBollettino());
		}
		// P.Q. Fine MEV 2021/19	

		if(beneForm.getImporto() != null){
			beneModello.setImporto(beneForm.getImporto().trim().replaceAll("\\.", ""));
		} else {
			beneModello.setImporto(importoBeneTotale);
		}
		modelMap.addAttribute("beneForm", beneModello);
		fugValidation.parseNumBollettino(beneModello, modelMap, request);

		String importoBeneModello = beneModello.getImporto();
		String importoBeneForm = beneForm.getImporto();
		
		String modelloSelected = beneForm.getModelloFugSelected().getId();
		AcquisisciModelloA acquisisciModelloA = null;
		AcquisisciModelloD acquisisciModelloD = null;
		AcquisisciModelloC acquisisciModelloC = null;
		AcquisisciModelloC2 acquisisciModelloC2 = null;
		AcquisisciModelloH acquisisciModelloH = null;
		
		try {
			if(modelloSelected.equals(Constants.FUG_MODELLO_A)){
				acquisisciModelloA = fugService.riempiModelloA(beneModello, idProcedimento, idUffOwner, importoBeneTotale);	
				
				boolean valida = this.validaModelloA(acquisisciModelloA,beneForm);
				if (!valida) {
					super.setMessage(Constants.MESSAGE, "error.modello.validazione", null, false);
					
					// P.Q. Inizio MEV 2021/19 Per il denaro (MODELLO A) il versato su deve avere i valori C/C , Libretto e Libretto Depos. Giud.
					boolean isModelloA = true;
					if (beneForm.getModelloFugSelected() != null && beneForm.getModelloFugSelected().getId() != null) {
						isModelloA = beneForm.getModelloFugSelected().getId().equals("A") ||
									 beneForm.getModelloFugSelected().getId().equals("C") ||
									 beneForm.getModelloFugSelected().getId().equals("C2") ||
									 beneForm.getModelloFugSelected().getId().equals("D") ||
									 beneForm.getModelloFugSelected().getId().equals("-1");
			}
					
					List<ComboForm> listaVersato = riempiComboVersato(isModelloA);
					modelMap.addAttribute("isModelloA", isModelloA);
					modelMap.addAttribute("listaVersato", listaVersato);
														
					// P.Q. Fine MEV 2021/19 Per il denaro (MODELLO A) il versato su deve avere i valori C/C , Libretto e Libretto Depos. Giud.
										
					return "/procedimento/bene/fug/dettaglioBeneFug";
				}
			}
			if(modelloSelected.equals(Constants.FUG_MODELLO_D)){
				acquisisciModelloD = fugService.riempiModelloD(beneModello, idProcedimento, idUffOwner, importoBeneTotale);	
				boolean valida = this.validaModelloD(acquisisciModelloD,beneForm);
				if (!valida) {
					super.setMessage(Constants.MESSAGE, "error.modello.validazione", null, false);
					
					// P.Q. Inizio MEV 2021/19 Per il denaro (MODELLO A) il versato su deve avere i valori C/C , Libretto e Libretto Depos. Giud.
					boolean isModelloA = true;
					if (beneForm.getModelloFugSelected() != null && beneForm.getModelloFugSelected().getId() != null) {
						isModelloA = beneForm.getModelloFugSelected().getId().equals("A") ||
									 beneForm.getModelloFugSelected().getId().equals("C") ||
									 beneForm.getModelloFugSelected().getId().equals("C2") ||
									 beneForm.getModelloFugSelected().getId().equals("D") ||
									 beneForm.getModelloFugSelected().getId().equals("-1");
					}
					
					List<ComboForm> listaVersato = riempiComboVersato(isModelloA);
					modelMap.addAttribute("isModelloA", isModelloA);
					modelMap.addAttribute("listaVersato", listaVersato);
														
					// P.Q. Fine MEV 2021/19 Per il denaro (MODELLO A) il versato su deve avere i valori C/C , Libretto e Libretto Depos. Giud.					
					
					return "/procedimento/bene/fug/dettaglioBeneFug";
				}
				beneForm.setImporto(beneModello.getImporto());
			}
			if(modelloSelected.equals(Constants.FUG_MODELLO_C)){
				acquisisciModelloC = fugService.riempiModelloC(beneModello, idProcedimento, idUffOwner);
//				Segnalazione EML-01
				if(acquisisciModelloC == null)
				{
					super.setMessage(Constants.MESSAGE, "error.modelloC.domicilio", null, false);
					
					// P.Q. Inizio MEV 2021/19 Per il denaro (MODELLO A) il versato su deve avere i valori C/C , Libretto e Libretto Depos. Giud.
					boolean isModelloA = true;
					if (beneForm.getModelloFugSelected() != null && beneForm.getModelloFugSelected().getId() != null) {
						isModelloA = beneForm.getModelloFugSelected().getId().equals("A") ||
									 beneForm.getModelloFugSelected().getId().equals("C") ||
									 beneForm.getModelloFugSelected().getId().equals("C2") ||
									 beneForm.getModelloFugSelected().getId().equals("D") ||
									 beneForm.getModelloFugSelected().getId().equals("-1");
					}
					
					List<ComboForm> listaVersato = riempiComboVersato(isModelloA);
					modelMap.addAttribute("isModelloA", isModelloA);
					modelMap.addAttribute("listaVersato", listaVersato);
														
					// P.Q. Fine MEV 2021/19 Per il denaro (MODELLO A) il versato su deve avere i valori C/C , Libretto e Libretto Depos. Giud.
					
					return "/procedimento/bene/fug/dettaglioBeneFug";
				}
				
				boolean valida = this.validaModelloC(acquisisciModelloC,beneForm);
				if (!valida) {
					super.setMessage(Constants.MESSAGE, "error.modello.validazione", null, false);
					
					// P.Q. Inizio MEV 2021/19 Per il denaro (MODELLO A) il versato su deve avere i valori C/C , Libretto e Libretto Depos. Giud.
					boolean isModelloA = true;
					if (beneForm.getModelloFugSelected() != null && beneForm.getModelloFugSelected().getId() != null) {
						isModelloA = beneForm.getModelloFugSelected().getId().equals("A") ||
						     	 	 beneForm.getModelloFugSelected().getId().equals("C") ||
						     	 	 beneForm.getModelloFugSelected().getId().equals("C2") ||
						     	 	 beneForm.getModelloFugSelected().getId().equals("D") ||
						     	 	 beneForm.getModelloFugSelected().getId().equals("-1");
					}
					
					List<ComboForm> listaVersato = riempiComboVersato(isModelloA);
					modelMap.addAttribute("isModelloA", isModelloA);
					modelMap.addAttribute("listaVersato", listaVersato);
														
					// P.Q. Fine MEV 2021/19 Per il denaro (MODELLO A) il versato su deve avere i valori C/C , Libretto e Libretto Depos. Giud.					
					
					return "/procedimento/bene/fug/dettaglioBeneFug";
				}
				beneForm.setImporto(beneModello.getImporto());
			}
			if(modelloSelected.equals(Constants.FUG_MODELLO_C_BIS)){
				acquisisciModelloC2 = fugService.riempiModelloC2(beneModello, idProcedimento, idUffOwner, importoBeneTotale);	
				boolean valida = this.validaModelloC2(acquisisciModelloC2,beneForm);
				if (!valida) {
					super.setMessage(Constants.MESSAGE, "error.modello.validazione", null, false);
					
					// P.Q. Inizio MEV 2021/19 Per il denaro (MODELLO A) il versato su deve avere i valori C/C , Libretto e Libretto Depos. Giud.
					boolean isModelloA = true;
					if (beneForm.getModelloFugSelected() != null && beneForm.getModelloFugSelected().getId() != null) {
						isModelloA = beneForm.getModelloFugSelected().getId().equals("A") ||
						     	 	 beneForm.getModelloFugSelected().getId().equals("C") ||
						     	 	 beneForm.getModelloFugSelected().getId().equals("C2") ||
						     	 	 beneForm.getModelloFugSelected().getId().equals("D") ||
						     	 	 beneForm.getModelloFugSelected().getId().equals("-1");						
					}
					
					List<ComboForm> listaVersato = riempiComboVersato(isModelloA);
					modelMap.addAttribute("isModelloA", isModelloA);
					modelMap.addAttribute("listaVersato", listaVersato);
														
					// P.Q. Fine MEV 2021/19 Per il denaro (MODELLO A) il versato su deve avere i valori C/C , Libretto e Libretto Depos. Giud.					
					
					return "/procedimento/bene/fug/dettaglioBeneFug";
				}
				
				beneForm.setImporto(beneModello.getImporto());
			}
			if(modelloSelected.equals(Constants.FUG_MODELLO_H)){
				acquisisciModelloH = fugService.riempiModelloH(beneModello, idProcedimento, idUffOwner, importoBeneTotale);
				boolean valida = this.validaModelloH(acquisisciModelloH,beneForm);
				if (!valida) {
					super.setMessage(Constants.MESSAGE, "error.modello.validazione", null, false);
					
					// P.Q. Inizio MEV 2021/19 Per il denaro (MODELLO A) il versato su deve avere i valori C/C , Libretto e Libretto Depos. Giud.
					boolean isModelloA = true;
					if (beneForm.getModelloFugSelected() != null && beneForm.getModelloFugSelected().getId() != null) {
						isModelloA = beneForm.getModelloFugSelected().getId().equals("A") ||
						     	 	 beneForm.getModelloFugSelected().getId().equals("C") ||
						     	 	 beneForm.getModelloFugSelected().getId().equals("C2") ||
						     	 	 beneForm.getModelloFugSelected().getId().equals("D") ||
						     	 	 beneForm.getModelloFugSelected().getId().equals("-1");
					}
					
					List<ComboForm> listaVersato = riempiComboVersato(isModelloA);
					modelMap.addAttribute("isModelloA", isModelloA);
					modelMap.addAttribute("listaVersato", listaVersato);
														
					// P.Q. Fine MEV 2021/19 Per il denaro (MODELLO A) il versato su deve avere i valori C/C , Libretto e Libretto Depos. Giud.					
					
					return "/procedimento/bene/fug/dettaglioBeneFug";
				}
				beneForm.setImporto(beneModello.getImporto());
			}
		} catch (EccezioneGenerica e) {
			super.setMessage(Constants.MESSAGE, e, false);
			return "/procedimento/bene/fug/dettaglioBeneFug";
		} catch (Exception e) {
			super.setMessage(Constants.MESSAGE, "error.invio.modello.noProtocollo", e.getMessage(), false);
			return "/procedimento/bene/fug/dettaglioBeneFug";
		}
		
		long idInvio = fugDao.insertFugInvio(idUffOwner, descUtente, idProcedimento, beneModello.getIdAtto(), beneModello.getIdBene(), 
				beneModello.getModelloFugSelected().getId(), beneModello.getProtocolloRif(), null, null, null, 
				beneModello.getDataNotifica(), beneModello.getDataIrrevocabileRevoca(),
				null, 
				beneModello.getIdSoggetto(), 
				null, null, Utility.getImporto(beneForm.getImporto()),
				super.printMethodName());

		beneModello.setImporto(importoBeneModello); // Ripristina il valore iniziale
		beneForm.setImporto(importoBeneForm); // Ripristina il valore iniziale

		RispostaModello rispostaModello = null;
		
		if(modelloSelected.equals(Constants.FUG_MODELLO_A)){
			rispostaModello = fugService.inviaModello(acquisisciModelloA, idInvio, descUtente);	
		}
		if(modelloSelected.equals(Constants.FUG_MODELLO_D)){
			rispostaModello = fugService.inviaModello(acquisisciModelloD, idInvio, descUtente);	
		}
		if(modelloSelected.equals(Constants.FUG_MODELLO_C)){
			rispostaModello = fugService.inviaModello(acquisisciModelloC, idInvio, descUtente);	
		}
		if(modelloSelected.equals(Constants.FUG_MODELLO_C_BIS)){
			rispostaModello = fugService.inviaModello(acquisisciModelloC2, idInvio, descUtente);	
		}
		if(modelloSelected.equals(Constants.FUG_MODELLO_H)){
			rispostaModello = fugService.inviaModello(acquisisciModelloH, idInvio, descUtente);	
		}
		
		String idProtocollo = rispostaModello.getIdProtocollo();
		boolean noProtocollo = RispostaModello.NO_ID_PROTOCOLLO.equals(idProtocollo);
		
		if (Utility.isValid(rispostaModello.getEsito()) || noProtocollo){
			fugService.insertFugErrori(rispostaModello, idInvio, descUtente);
			if(noProtocollo){
				super.setMessage(Constants.MESSAGE, "error.invio.modello", null, false);	
			} else {
				String msg = "error.invio.modello." + ((idProtocollo != null) ? "protocollo" : "idInvio");
				super.setMessage(Constants.MESSAGE, msg, ((idProtocollo != null) ? idProtocollo : (idInvio + "")), false);
			}
			
			// P.Q. Inizio MEV 2021/19 Per il denaro (MODELLO A) il versato su deve avere i valori C/C , Libretto e Libretto Depos. Giud.
			boolean isModelloA = true;
			if (beneForm.getModelloFugSelected() != null && beneForm.getModelloFugSelected().getId() != null) {
				isModelloA = beneForm.getModelloFugSelected().getId().equals("A") ||
						     beneForm.getModelloFugSelected().getId().equals("C") ||
						     beneForm.getModelloFugSelected().getId().equals("C2") ||
						     beneForm.getModelloFugSelected().getId().equals("D") ||
						     beneForm.getModelloFugSelected().getId().equals("-1");
			}
			List<ComboForm> listaVersato = riempiComboVersato(isModelloA);
			modelMap.addAttribute("isModelloA", isModelloA);
			modelMap.addAttribute("listaVersato", listaVersato);
			// P.Q. Inizio MEV 2021/19 Per il denaro (MODELLO A) il versato su deve avere i valori C/C , Libretto e Libretto Depos. Giud.
			
			return "/procedimento/bene/fug/dettaglioBeneFug";
		}
		
		modelMap.addAttribute("checkSi", idProtocollo);
		modelMap.addAttribute("fugForm", fugForm);
		
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation", "INVIO MODELLO con protocollo " + idProtocollo, false);
		if (!beneForm.getModelloFugSelected().getId().equals(Constants.FUG_MODELLO_C_BIS)) {
			return elencoInvioFug(fugForm, result, request, modelMap);	
		}else{
			return elencoSoggettiModelloCBis(fugForm, result, request, modelMap);
		}
	}
	
	@RequestMapping(value = "/procedimento/bene/fug/elencoInvioFug.htm", method = RequestMethod.GET)
	public String elencoInvioFug(FugForm fugForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {

		if (request.getSession().getAttribute(Constants.SUCCESS_MESSAGE) != null) {
			modelMap.addAttribute(Constants.SUCCESS_MESSAGE, request.getSession().getAttribute(Constants.SUCCESS_MESSAGE));
			request.getSession().removeAttribute(Constants.SUCCESS_MESSAGE);
		}
		if (request.getSession().getAttribute(Constants.SUCCESS_MESSAGE_LIST) != null) {
			modelMap.addAttribute(Constants.SUCCESS_MESSAGE_LIST, request.getSession().getAttribute(Constants.SUCCESS_MESSAGE_LIST));
			request.getSession().removeAttribute(Constants.SUCCESS_MESSAGE_LIST);
		}
		
		ProcedimentoForm procedimento = recuperaProcedimento();
				
		if (fugForm.getModelloFugSelected() != null) {
			if (fugForm.getModelloFugSelected().getId().equals("-1")) {
				fugForm.setModelloFugSelected(null);
			}
		}
		
		if(Integer.parseInt(procedimento.getTipoFascicolo().getId())!=Constants.TIPO_FASCICOLO_MERITO){
			super.setMessage(Constants.MESSAGE, "error.seleziona.procedimento.prevenzione", null, true);
			return "/procedimento/bene/fug/beniFug";
		}
		
		fugForm.setIdProcedimento(procedimento.getIdProcedimento());
		
		String descUtente = recuperaIdCodiceUtente();
		
		List<FugForm> modelliInviati = new ArrayList<FugForm>();
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
		
		modelliInviati = fugService.elencoInvioFug(idUfficioUtente, fugForm, descUtente, super.printMethodName());
		
		modelMap.addAttribute("modelliInviati", modelliInviati);
				
		addBreadcrumbs("GET", "/procedimento/bene/fug/elencoInvioFug.htm", null);
		
		return "/procedimento/bene/fug/elencoInvioFug";
	}

	@RequestMapping(value = "/procedimento/bene/fug/elencoInvioFug.htm", method = RequestMethod.POST)
	public String postElencoInvioFug(FugForm fugForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {

		if (!fugForm.isVisualizzaFiltro()) {
			fugForm.setModelloFugSelected(null);
			fugForm.setDataInvioDa(null);
			fugForm.setDataInvioA(null);
		}else{
			fugValidation.validateDate(fugForm, result, request);
		}
		
		return elencoInvioFug(fugForm, result, request, modelMap);
	}
	
	
	@RequestMapping(value = "/procedimento/bene/fug/paginazioneElencoInvioFug.htm", method = RequestMethod.GET)
	public String paginazioneElencoInvioFug(FugForm fugForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {
		return elencoInvioFug(fugForm, result, request, modelMap);
	}
	
	private boolean hasIdRicevutaEquitalia(TFugInvio tFugInvio) {
		return (tFugInvio != null) ? hasIdRicevutaEquitalia(tFugInvio.getNumeRisorsaEqui(), tFugInvio.getNumeProvvEqui(), tFugInvio.getDescNotifEqui()) : false;
	}
	
	private boolean hasIdRicevutaEquitalia(FugForm tFugInvio) {
		return (tFugInvio != null) ? hasIdRicevutaEquitalia(tFugInvio.getNumeRisorsaEqui(), tFugInvio.getNumeProvvEqui(), tFugInvio.getDescNotifEqui()) : false;
	}
	
	private boolean hasIdRicevutaEquitalia(String numeRisorsaEqui, String numeProvvEqui, String descNotifEqui) {
		return (numeRisorsaEqui != null) || (numeProvvEqui != null) || (descNotifEqui != null);
	}
	
	@RequestMapping(value = "/procedimento/bene/fug/elencoInvioFug.htm", method = RequestMethod.POST, params = { "AnnullaModello" })
	public String annullaModello(FugForm fugForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {
		
		Long idInvioSelezionato = fugForm.getIdInvio();
		String statoInvio = fugForm.getStatoInvio();
		
		if (!result.hasErrors()){
			fugValidation.validateSelLista(idInvioSelezionato, result, request);
			if (result.hasErrors()) {
				return elencoInvioFug(fugForm, result, request, modelMap);
			}	
		}
		
		TFugInvio tFugInvio = fugDao.getTFugInvioById(idInvioSelezionato);
		String flgStatoInvio = Character.toString(Utility.getValid(tFugInvio.getFlgStatoInvio()));
		if (flgStatoInvio.equalsIgnoreCase(Constants.FUG_STATO_INVIATO_CON_ERRORI)){
			super.setMessage(Constants.MESSAGE, "error.modelloNonAnnullabileInErrore", null, false);
			return elencoInvioFug(fugForm, result, request, modelMap);
		}
		
		if(statoInvio == null){
			super.setMessage(Constants.MESSAGE, "error.modelloNonAnnullabile", null, false);
			return elencoInvioFug(fugForm, result, request, modelMap);
		}

		if (statoInvio.equalsIgnoreCase(Constants.FUG_RICHIESTO_ANNULLAMENTO_STR)){
			String dataInvioRichiesta = Utility.formatDate(tFugInvio.getDataUltAgg());
			super.setMessage(Constants.MESSAGE, "error.modelloRichiestoAnnullamento", dataInvioRichiesta, false);
			return elencoInvioFug(fugForm, result, request, modelMap);
		}
		
		if (statoInvio.equalsIgnoreCase(Constants.FUG_INVIO_ANNULLATO_STR)){
			TFugInvio tFugInvioAnnullamento = fugService.gatInvioFUGCollegato(recuperaIdUfficioUtente(), tFugInvio.getDescProtocollo());
			String dataAnnullamento = Utility.formatDate(tFugInvioAnnullamento.getDataInvio());
			super.setMessage(Constants.MESSAGE, "error.modelloGiaAnnullato", dataAnnullamento, false);
			return elencoInvioFug(fugForm, result, request, modelMap);
		}
		
		if (statoInvio.equalsIgnoreCase(Constants.FUG_INVIO_NON_ANNULLABILE_STR) ||
				statoInvio.equalsIgnoreCase(Constants.FUG_INVIO_NON_VALIDATO_STR)){
			super.setMessage(Constants.MESSAGE, "error.modelloNonAnnullabile", null, false);
			return elencoInvioFug(fugForm, result, request, modelMap);
		}
		
		if (!hasIdRicevutaEquitalia(tFugInvio)){
			super.setMessage(Constants.MESSAGE, "error.modelloSenzaIdRicevutaEquitalia", null, false);
			return elencoInvioFug(fugForm, result, request, modelMap);
		}
		
		if (tFugInvio.getDataDefPos() != null){
			String dataDefPos = Utility.formatDate(tFugInvio.getDataDefPos());
			super.setMessage(Constants.MESSAGE, "error.modelloDefinitaPosizione", dataDefPos, false);
			return elencoInvioFug(fugForm, result, request, modelMap);
		}
		
		RichiestaAnnullamentoModello richiestaAnnullamento = null;
		Integer idUffOwner = recuperaIdUfficioUtente();
		String descUtente = recuperaIdCodiceUtente();
		
		try {
			richiestaAnnullamento = fugService.richiestaAnnullamentoModello(idUffOwner, tFugInvio);
		} catch (Exception e) {
			super.setMessage(Constants.MESSAGE, "error.invio.modello.noProtocollo", e.getMessage(), false);
			return elencoInvioFug(fugForm, result, request, modelMap);
		}
		
		long idInvio = fugDao.insertFugInvio(idUffOwner, descUtente, tFugInvio.getTProcedimento().getCodiKIdProc(), 
					tFugInvio.getTAtto().getId().getCodiKIdAtto(), tFugInvio.getTBene().getId().getCodiKIdBene(),
					Constants.ANNULLA_MODELLO_FUG, tFugInvio.getDescProtocollo(), idInvioSelezionato, null, null, null, null, null, 
					tFugInvio.getDescModello().equals(Constants.FUG_MODELLO_C_BIS)? tFugInvio.getTSoggetto().getId().getCodiKIdSoggInt() : null,
					null, null, null,
					super.printMethodName());
		
		RispostaModello rispostaModello = null;
		
		rispostaModello = fugService.annullaModello(richiestaAnnullamento, idInvio, idInvioSelezionato, descUtente);	
		
		String idProtocollo = rispostaModello.getIdProtocollo();
		boolean noProtocollo = RispostaModello.NO_ID_PROTOCOLLO.equals(idProtocollo);
		
		if (Utility.isValid(rispostaModello.getEsito()) || noProtocollo){
			fugService.insertFugErrori(rispostaModello, idInvio, descUtente);
			String returnTo = "";
			if(noProtocollo){
				super.setMessage(Constants.MESSAGE, "error.invio.modello", null, false);	
				returnTo = "/procedimento/bene/fug/dettaglioBeneFug";
			} else {
				String msg = "error.invio.modello." + ((idProtocollo != null) ? "protocollo" : "idInvio");
				super.setMessage(Constants.MESSAGE, msg, ((idProtocollo != null) ? idProtocollo : (idInvio + "")), false);
				returnTo = elencoInvioFug(fugForm, result, request, modelMap);
			}
			return returnTo;
		}
		
		modelMap.addAttribute("checkSi", tFugInvio.getDescProtocollo());
		
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation", "ANNULLAMENTO con protocollo n° "+idProtocollo, false);
		
		return elencoInvioFug(fugForm, result, request, modelMap);
	}

	
	@RequestMapping(value = "/procedimento/bene/fug/elencoInvioFug.htm", method = RequestMethod.POST, params = { "Dettaglio" })
	public String dettaglioInvioFug(FugForm fugForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {
		
		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		Integer idUffOwner = recuperaIdUfficioUtente();
		
		long idInvioSelezinato = fugForm.getIdInvio();
		String statoInvio = fugForm.getStatoInvio();
		
		if (!result.hasErrors()){
			fugValidation.validateSelLista(idInvioSelezinato, result, request);
			if (result.hasErrors()) {
				return elencoInvioFug(fugForm, result, request, modelMap);
			}	
		}
		
		FugModelloForm fugModelloForm = new FugModelloForm();
		fugModelloForm.setStatoInvio(statoInvio);
		
		TFugInvio tFugInvio = fugDao.getTFugInvioById(idInvioSelezinato);
		
		fugModelloForm.setIdBene(tFugInvio.getTBene().getId().getCodiKIdBene());
		fugModelloForm.setDescModello(tFugInvio.getDescModello());
		fugModelloForm.setDescProtocollo(tFugInvio.getDescProtocollo());
		
		BeneForm bene = beneService.beneById(idUffOwner, fugModelloForm.getIdBene(), idProcedimento);
		
		fugModelloForm.setCategoria(bene.getCategoria());
		fugModelloForm.setSottocategoria(bene.getSottocategoria());
		fugModelloForm.setTipologia(bene.getTipologia());
		fugModelloForm.setIdUfficio(idUffOwner.intValue());
		fugModelloForm.setIdInvio(fugForm.getIdInvio());
		
		fugService.getRispostaEdEsitoXML(fugModelloForm);

		modelMap.addAttribute("checkSi", tFugInvio.getDescProtocollo());
		
		modelMap.addAttribute("fugModelloForm", fugModelloForm);
		
		addBreadcrumbs("GET", "/procedimento/bene/fug/dettaglioInvioFug.htm", null);
		
		return "/procedimento/bene/fug/dettaglioInvioFug";
	}
	
	// P.Q. Inizio MEV 2021/19 Per il denaro (MODELLO A) il versato su deve avere i valori C/C , Libretto e Libretto Depos. Giud.
//	@ModelAttribute("listaVersato")
//	private static List<ComboForm> riempiComboVersato(String valore) {
//		
//		List<ComboForm> listaVersato = new ArrayList<ComboForm>();
//
//		Map<String, String> scelte = FugValidation.getElencoListaVersato();
//
//		ComboForm combo = null;
//		for (Map.Entry<String, String> elem : scelte.entrySet()) {
//			combo = new ComboForm();
//			combo.setId(elem.getKey());
//			combo.setDescrizione(elem.getValue());
//			listaVersato.add(combo);
//		}
//
//		return listaVersato;
//	}
	
	private static List<ComboForm> riempiComboVersato(boolean isModelloA) {
		
		List<ComboForm> listaVersato = new ArrayList<ComboForm>();

		Map<String, String> scelte = FugValidation.getElencoListaVersato(isModelloA);

		ComboForm combo = null;
		for (Map.Entry<String, String> elem : scelte.entrySet()) {
			combo = new ComboForm();
			combo.setId(elem.getKey());
			combo.setDescrizione(elem.getValue());
			listaVersato.add(combo);
		}

		return listaVersato;
	}
	// P.Q. Fine MEV 2021/19 Per il denaro (MODELLO A) il versato su deve avere i valori C/C , Libretto e Libretto Depos. Giud.	
	
	@ModelAttribute("valuta")
	public List<ComboForm> getValuta() {

		valuta = StaticListLoader.getTipiValuta();
		return valuta;
	}
	
	private BeneForm verificaDettBeneEnteFittizio(BeneForm beneForm){
		//Nel caso in cui il dettaglio del beni fug e' relativo ad un ente fittizio 
		if(beneForm.getCodiceAbi() != null && Constants.EnteFittizio.ABI.compareTo(beneForm.getCodiceAbi()) == 0){
			//imposto i dati del bene a vuoto per 
			beneForm.getDescOperFinanz().setDescrizione("");
			beneForm.setCodiceAbi("");
			beneForm.getCodiceCab().setDescrizione("");
			beneForm.getCodiceCab().setId("");
			beneForm.setNumeroRegistrazione("");
			beneForm.setIndirizzoBeneFin("");
			beneForm.getStato().setDescrizione("");
			beneForm.getStato().setId("");
			beneForm.setEnteFittizio(true);
		}
		
		return beneForm;
	}
	
	private BeneForm impostaAbiCabDaIBAN(BeneForm beneForm){
		String appoAbi = null;
	    String appoCab = null;
		if(beneForm.getCodiceAbi() == null ){
	    	if(beneForm.getCodiceIban()!=null && beneForm.getCodiceIban().length() > 10){
	    		appoAbi = beneForm.getCodiceIban().substring(5, 10);
	    	}
	    }
	    if(beneForm.getCodiceCab().getId() == null ){
	    	if(beneForm.getCodiceIban()!=null && beneForm.getCodiceIban().length() > 15){
	    		appoCab = beneForm.getCodiceIban().substring(10, 15);
		    }	
	    }
	 //verifico la correttezza dell'abi e cab estratti dall'IBAN   
	    if (appoAbi != null && appoCab != null){
		    List<TAnOperFinanz> lista = commonDao.getCodiciCab(appoCab, appoAbi, null);
	        if (lista.size() > 0) {
	        	beneForm.setCodiceAbi(appoAbi);
	        	beneForm.getCodiceCab().setId(appoCab);
	        	//F.C. 11/11/2021 quando dall'iban riesco a trovare un abi e cab corretto
	        	//visualizzo a schermo anche l'operatore finanziario e l'indirizzo.
	        	beneForm.getDescOperFinanz().setDescrizione(lista.get(0).getDescBanca());
	        	BeneForm beneForm2 = beneService.caricaInfoOperFinanz(appoAbi, appoCab);
	        	if (beneForm2 != null) {
				IndirizzoForm indirizzo = Utility.costruisciIndirizzo(beneForm2);
				beneForm.setIndirizzoBeneFin(indirizzo.getDenominazione());	
	        	//Fine Modifica
	        	}
	        	
	        	
	        }
	    }
	    return beneForm;
	}
	
	private String getCodiceOperatore(BeneForm beneForm){
		String codiceOperatore = null;
	    if (beneForm.getCodiceAbi() != null && beneForm.getCodiceCab() != null){
		    List<TAnOperFinanz> lista = commonDao.getCodiciCab(beneForm.getCodiceCab().getId(), beneForm.getCodiceAbi(), null);
	        if (lista.size() > 0) {
	        	 TAnOperFinanz tAnOperFinanz = lista.get(0);
	        	 codiceOperatore = tAnOperFinanz.getDescCodfiscale();
	        }
	    }
	    return codiceOperatore;
	}
	 		 	
	private void stampaErroreValidazione(BeneForm beneForm, String modello,Exception e) {
				
		ProcedimentoForm procedimento = recuperaProcedimento();
		Integer idUffOwner = recuperaIdUfficioUtente();		
		BeneForm bene = beneService.beneById(idUffOwner, beneForm.getIdBene(), procedimento.getIdProcedimento());
		
		logger.error("**************************************");
		logger.error("Errore validazione Modello " + modello);
		logger.error(e);
		logger.error("*********DATI FUG CON ERRORE***************");
		logger.error("NUMERO PROCEDIMENTO: " + procedimento.getAnnoNumeroProcedimento());
		logger.error("ENTE:" + bene.getDescOperFinanz().getDescrizione());
		logger.error("ID BENE:" + bene.getIdBene());
		logger.error("*********FINE DATI FUG CON ERRORE***************");
		logger.error("**************************************");
	}
	
	private boolean validaModelloA (AcquisisciModelloA in,BeneForm beneForm) {
		boolean ret = false;
		byte[] out = null;
		try {
			out = JAXBUtil.validationAndMarshalFug(in);
			ret = true;
		} catch (Exception e) {
			stampaErroreValidazione( beneForm,  "A", e);
			ret = false;
		}	
	 
		return ret;
	}

	private boolean validaModelloC (AcquisisciModelloC in,BeneForm beneForm) {
		boolean ret = false;
		byte[] out = null;
		try {
			out = JAXBUtil.validationAndMarshalFug(in);
			ret = true;
		} catch (Exception e) {
			stampaErroreValidazione( beneForm,  "C", e);
			ret = false;
		}	
	 
		return ret;
	}

	private boolean validaModelloD (AcquisisciModelloD in,BeneForm beneForm) {
		boolean ret = false;
		byte[] out = null;
		try {
			out = JAXBUtil.validationAndMarshalFug(in);
			ret = true;
		} catch (Exception e) {
			stampaErroreValidazione( beneForm,  "D", e);
			ret = false;
		}	
	 
		return ret;
	}

	private boolean validaModelloC2 (AcquisisciModelloC2 in,BeneForm beneForm) {
		boolean ret = false;
		byte[] out = null;
		try {
			out = JAXBUtil.validationAndMarshalFug(in);
			ret = true;
		} catch (Exception e) {
			stampaErroreValidazione( beneForm,  "C2", e);
			ret = false;
		}	
	 
		return ret;
	}

	private boolean validaModelloH (AcquisisciModelloH in,BeneForm beneForm) {
		boolean ret = false;
		byte[] out = null;
		try {
			out = JAXBUtil.validationAndMarshalFug(in);
			ret = true;
		} catch (Exception e) {
			stampaErroreValidazione( beneForm,  "H", e);
			ret = false;
		}	
	 
		return ret;
	}
	 		 	
}
