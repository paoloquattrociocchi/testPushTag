package it.giustizia.smp.controller.procedimento;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.AutoPopulatingList;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import it.giustizia.smp.controller.BreadcrumbsController;
import it.giustizia.smp.dao.FlussiDao;
import it.giustizia.smp.dao.StaticListLoader;
import it.giustizia.smp.dto.KeyMessageDto;
import it.giustizia.smp.dto.SchedaAttiAmmDto;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.BeneForm;
import it.giustizia.smp.form.BeneSoggettoForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.IndirizzoForm;
import it.giustizia.smp.form.MisuraForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.ProfiloForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.model.TAnAltriOper;
import it.giustizia.smp.model.TDepositiAmm;
import it.giustizia.smp.model.TRelBeneAtto;
import it.giustizia.smp.model.TRelProcBeneTit;
import it.giustizia.smp.model.TTpTipoInteresse;
import it.giustizia.smp.service.AttoService;
import it.giustizia.smp.service.BeneService;
import it.giustizia.smp.service.CommonService;
import it.giustizia.smp.service.FlussiService;
import it.giustizia.smp.service.ProcedimentoService;
import it.giustizia.smp.service.webservices.IBDCService;
import it.giustizia.smp.utility.ComparatorUtil;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Constants.CategoriaAtto;
import it.giustizia.smp.utility.Constants.CategoriaBeni;
import it.giustizia.smp.utility.Constants.TipiTrasformazione;
import it.giustizia.smp.utility.Constants.TipoAtto;
import it.giustizia.smp.utility.Constants.TipologieBeni;
import it.giustizia.smp.utility.Message;
import it.giustizia.smp.utility.SottoCategoriaUtils;
import it.giustizia.smp.utility.ToolAmmMapper;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.utility.ValidationUtil;
import it.giustizia.smp.validation.BeneValidation;
import it.giustizia.smp.xml.amministratori.BeneExtType;
import it.giustizia.smp.xml.amministratori.RicezioneRelazione;

/**
 * BeneController
 * 
 * @author Almaviva S.p.A. Classe Controller per la gestione dei beni inerenti al procedimento
 */
@Controller
public class BeneController extends BreadcrumbsController {

	@Autowired
	private AttoController attoController;
	@Autowired
	private AttoService attoService;
	@Autowired
	private BeneService beneService;
	@Autowired
	private BeneValidation beneValidation;
	@Autowired
	private CommonService commonService;
	@Autowired
	private ProcedimentoService procedimentoService;
	@Autowired
	private IBDCService bdcService;
	private List<ComboForm> valuta;
	@Autowired
	private FlussiService flussiService;
	@Autowired
	private FlussiDao flussiDao;

	/**
	 * funzione per prelevare i checkbox paginati
	 * 
	 * @param beneForm
	 * @param request
	 */
	private void chooseCheckBoxPaged(BeneForm beneForm, HttpServletRequest request) {
		List<Long> listaIdCheckbox = new ArrayList<Long>();
		String[] checkArray = request.getParameterValues("_chk");
		if (checkArray != null && checkArray.length > 0) {
			for (int ic = 0; ic < checkArray.length; ic++) {
				String xxx = checkArray[ic];
				Long yyy = Long.parseLong(xxx);
				listaIdCheckbox.add(yyy);
			}
		}
		beneForm.setListaIdCheckbox(listaIdCheckbox);
	}

	// ***************** NUOVO BENE ****************//
	@RequestMapping(value = "/procedimento/bene/nuovoBeneDelProcedimento.htm", method = RequestMethod.GET)
	public String aggiungiBene(BeneForm beneForm, ModelMap modelMap, HttpServletRequest request) {

		modelMap.addAttribute("beneForm", beneForm);
		removeFromSession("risultatiBDC");

		if (request.getSession().getAttribute(Constants.ATTO) != null)
			removeFromSession(Constants.ATTO);

		// Giacomo 11/07/2013 BreadCrumbs
		addBreadcrumbs();

		return "/procedimento/bene/aggiungiBene";
	}

	@RequestMapping(value = "/procedimento/bene/nuovoBeneDelProcedimentoAtto.htm", method = RequestMethod.GET)
	public String aggiungiBeneAtto(BeneForm beneForm, ModelMap modelMap) {

		modelMap.addAttribute("beneForm", beneForm);
		removeFromSession("risultatiBDC");

		// if (request.getSession().getAttribute(Constants.ATTO)!=null) removeFromSession(Constants.ATTO);

		// Giacomo 11/07/2013 BreadCrumbs
		addBreadcrumbs();

		return "/procedimento/bene/aggiungiBene";
	}

	@RequestMapping(value = "/procedimento/bene/ricercaBene.htm", method = RequestMethod.POST, params = {
			"Annulla" })
	public String annullaRicerca(ModelMap modelMap, HttpServletRequest request) {
		if (request.getSession().getAttribute(Constants.ATTO) != null)
			return aggiungiBeneAtto(new BeneForm(), modelMap);
		else
			return aggiungiBene(new BeneForm(), modelMap, request);
	}

	@RequestMapping(value = "/procedimento/bene/ricercaBene.htm", method = RequestMethod.POST, params = {
			"Associa" })
	public String associaBeneAlProcedimento(BeneForm beneForm, BindingResult result,
			HttpServletRequest request, ModelMap modelMap) {

		beneValidation.validateListaBeni(beneForm, request, result);
		if (result.hasErrors()) {
			if ("RicercaBDC".equalsIgnoreCase(request.getParameter("visualizzaPulsante"))) {
				return ricercaBeneinBDC(beneForm, result, request, modelMap);
			} else if ("Ricerca".equalsIgnoreCase(request.getParameter("visualizzaPulsante"))) {
				return ricercaBeneinBaseDati(beneForm, result, request, modelMap);
			}
		}

		// Giacomo 11/07/2013 BreadCrumbs
		addBreadcrumbs("Associa", request);
		return includeDettaglioBene(beneForm, result, modelMap, request, "Associa");
	}

	@RequestMapping(value = { "/procedimento/bene/inserimentoImmobile.htm" }, method = RequestMethod.POST)
	public ModelAndView caricaComboBI(BeneForm beneForm) {

		return new ModelAndView("/procedimento/bene/inserimentoImmobile", "bene", beneForm);
	}
	
	@RequestMapping(value = { "/procedimento/bene/inserimentoBeneFinanziario.htm",
			"/procedimento/bene/inserimentoDenaro.htm" }, method = RequestMethod.POST)
	public String caricaDatiBeneFinanziarioDenaro(@Valid BeneForm beneForm, BindingResult result,
			ModelMap modelMap) {

		// GESTIONE RITORNO SU PAGINA BENE DENARO(ANCHE CONTANTE) O BENE
		// FINANZIARIO
		String returnPage = "";
		String tipologia = "";
		String categoria = "";
		if ((beneForm.getTipologia() != null) && (beneForm.getTipologia().getId() != null)) {
			tipologia = beneForm.getTipologia().getId();
			if ((beneForm.getCategoria() != null) && (beneForm.getCategoria().getId() != null)) {
				categoria = beneForm.getCategoria().getId();
			}
		}

		String sottocategoria = null;
		if (beneForm.getSottocategoria() != null) {
			sottocategoria = beneForm.getSottocategoria().getId();
		}

		List<ComboForm> listaVersato = riempiComboVersato("Modifica");
		modelMap.addAttribute("listaVersato", listaVersato);

		if (tipologia.equals("2")) {
			if (categoria.equals("6")) {
				// Vale anche per sottocategoria CONTANTE
				returnPage = "/procedimento/bene/inserimentoDenaro";
			}
		} else if (tipologia.equals("4")) {
			if (SottoCategoriaUtils.isPolizza(sottocategoria)) {
				if (beneForm.getDescOperFinanz() != null
						/*
						 * ISSUE MEV 	: risolve Ticket INC000001842335, all’atto di confermare ed inviare il decreto di
						 * 					confisca compare la schermata - non è possibile confiscare beni non definiti.
						 * 					Aggiunto controllo di consistenza
						 * Numero MEV 	: 10
						 * Autore 		: Gioggi
						 * Data 		: 27 giu 2018
						 * Branch 		: MEV_10
						 */
						&& beneForm.getDescOperFinanz().getId() != null
						// ***** FINE INTERVENTO MEV_10 *****//
						&& !"".equals(beneForm.getDescOperFinanz().getId())) {
					// popolo il campo provincia
					TAnAltriOper tAnAltriOper = beneService
							.getTAnAltriOper(Long.valueOf(beneForm.getDescOperFinanz().getId()));
					beneForm.setProvinciaEntePolizza(tAnAltriOper.getDescSiglaProv());
					beneForm.setIndirizzoBeneFin(tAnAltriOper.getDescIndirizzo());
					
				}
				else
				{
					beneForm.setProvinciaEntePolizza("");
					beneForm.setIndirizzoBeneFin("");
				}

				return "/procedimento/bene/inserimentoPolizza";
			} else
				returnPage = "/procedimento/bene/inserimentoBeneFinanziario";
		}

		
		// P.Q. Inizio MEV 2021/19 Operatore Economico Estero allora i campi vanno valorizzati in automatico
		modelMap.addAttribute("isOperatoreEconomicoEstero", false);

		if (checkOperatoreEconomicoEstero(beneForm, modelMap)) {
			return returnPage;
		}		
		// P.Q. Fine MEV 2021/19 Operatore Economico Estero allora i campi vanno valorizzati in automatico
				
		if (result.hasErrors()) {
			
			// P.Q. 15/11/2021 Inizio Ticket 20211022016
			// Se ENTE FITTIZIO non carico la combo delle provincie (altrimenti vedrei N.D.)
			if (beneForm.getDescOperFinanz() != null && beneForm.getDescOperFinanz().getDescrizione() != null && Constants.EnteFittizio.ID.equals(beneForm.getDescOperFinanz().getId())) {
				modelMap.addAttribute("provinceAbi", null);
			} else {
				List<ComboForm> province = commonService.listaProvinceAbi(beneForm.getCodiceAbi());
				modelMap.addAttribute("provinceAbi", province);	
			}
						
			List<ComboForm> allCab = commonService.listaCodiciCab(beneForm.getCodiceAbi(), beneForm.getProvincia() != null ? beneForm.getProvincia().getId() : null);
			modelMap.addAttribute("codiciCab", allCab);
			
			ProcedimentoForm procedimentoForm = recuperaProcedimento();
			long idProcedimento = procedimentoForm.getIdProcedimento();			
			modelMap.addAttribute("modificabile", !(beneService.isAssociato(beneForm.getIdBene(),recuperaIdUfficioUtente(), idProcedimento)));
			// P.Q. 05/11/2021 Fine Ticket 20211022016
			
			modelMap.addAttribute("bene", beneForm);
			return returnPage;
		}

		// INIZIO GESTIONE CODICE ABI
		String abiInserito = null;
		boolean insManuale = false;
		boolean selectedProvincia = false;
		boolean provMod = false;
		boolean insCab = false;

		if (beneForm.getCodiceAbi() != null) {
			if (beneForm.getDescOperFinanz().getDescrizione() == null) {
				String abiPrecedente = beneForm.getAbi();
				if (abiPrecedente == null) {
					// Se ho inserito manualmente il codice ABI
					abiInserito = beneForm.getCodiceAbi();
					insManuale = true;
				} else {
					// Ho annullato la Descrizione Ente manualmente,
					// quindi annullo l'ABI e tutti gli altri campi
					abiInserito = null;
					beneForm.setAbi(null);
					beneForm.setCodiceAbi(null);
					beneForm.setDescOperFinanz(new ComboForm(null, null));
					beneForm.setDescrizioneEnte(null);
					if (beneForm.getProvincia() != null)
						beneForm.getProvincia().setId("-1");
					beneForm.setProvinciaSel(null);
					beneForm.setDisabledProvincia(true);
					beneForm.setNumeroRegistrazione(null);
					selectedProvincia = false;
					beneForm.setCodiceCab(new ComboForm(null, null));
					insCab = false;
					beneForm.setIndirizzoBeneFin(null);
				}
			} else {
				// CodiceAbi e Descrizione Ente sono pieni
				String abiMan = beneForm.getCodiceAbi();
				String abiMenu = beneForm.getDescOperFinanz().getId();
				String abiPrecedente = beneForm.getAbi();

				if (abiPrecedente != null) {
					// Ho inserito un nuovo codice ABI manualmente
					if (!abiMan.equalsIgnoreCase(abiPrecedente)) {
						abiInserito = abiMan;
						insManuale = true;
						if (beneForm.getProvincia() != null)
							beneForm.getProvincia().setId("-1");
						beneForm.setProvinciaSel(null);
						beneForm.setCodiceCab(new ComboForm(null, null));
						beneForm.setIndirizzoBeneFin(null);
					}
					// Ho inserito un nuovo codice ABI scegliendo un nuovo
					// operatore finanziario
					else if (abiMenu != null) {
						if (!abiMenu.equalsIgnoreCase(abiPrecedente)) {
							abiInserito = abiMenu;
							insManuale = false;
							if (beneForm.getProvincia() != null)
								beneForm.getProvincia().setId("-1");
							beneForm.setProvinciaSel(null);
							beneForm.setCodiceCab(new ComboForm(null, null));
							beneForm.setIndirizzoBeneFin(null);
							// P.Q. Inizio MEV 2021/19 Inizio (Il campo rimaneva sempre valorizzato anche cambiando banca)							
							beneForm.setNumeroRegistrazione(null);
							// P.Q. Inizio MEV 2021/19 Fine
						}
						// Ho modificato a mano la Descrizione Ente
						// senza selezionare un nuovo elemento dall'elenco
						// l'ABI resta quindi lo stesso di prima
						// cosi come la Descrizione Ente
						else {
							abiInserito = abiMenu;
							String descEnte = beneForm.getDescrizioneEnte();
							beneForm.setDescOperFinanz(new ComboForm(abiInserito, descEnte));
						}
					}
				} else {
					// CodiceAbi!=null &&
					// beneForm.getDescOperFinanz().getDescrizione() != null &&
					// beneForm.getAbi() == null
					// Dopo errore verificatosi durante il tentativo di
					// salvataggio del bene
					// Se in precedenza avevo annullato ABI ora inserisco
					// Descrizione Ente
					// oppure prima ho annullato la Descrizione Ente ora
					// inserisco l'ABI
					String descPrecedente = beneForm.getDescrizioneEnte();
					if (!abiMan.equalsIgnoreCase(abiMenu)) {
						// Serve controllare la Descrizione Ente precedente
						// Se Descrizione Ente precedente è null, il nuovo ABI è
						// quello del menu a tendina
						if (descPrecedente == null)
							abiInserito = abiMenu;
						// Altrimenti il nuovo ABI è quello inserito manualmente
						else {
							abiInserito = abiMan;
							insManuale = true;
						}
					}
					// Se nuovo ABI selezionato corrisponde alla Descrizione
					// Ente presente
					// oppure la Descrizione Ente nuova inserita corrisponde
					// all'ABI presente
					else
						abiInserito = abiMan; // abiMan=abiMenu
				}
			}
		} else {
			if (beneForm.getDescOperFinanz() != null
					&& beneForm.getDescOperFinanz().getDescrizione() != null) {
				String abiPrecedente = beneForm.getAbi();
				if (abiPrecedente == null) {
					// Se ho scelto l'operatore finanziario
					// dal menu ad autocompletamento
					abiInserito = beneForm.getDescOperFinanz().getId();
					insManuale = false;
				}
				// CodiceAbi null e Descrizione Ente piena
				else {
					// Ho annullato il codice ABI manualmente,
					// quindi annullo tutti gli altri campi
					abiInserito = null;
					beneForm.setAbi(null);
					beneForm.setCodiceAbi(null);
					beneForm.setDescOperFinanz(new ComboForm(null, null));
					beneForm.setDescrizioneEnte(null);
					if (beneForm.getProvincia() != null)
						beneForm.getProvincia().setId("-1");
					beneForm.setProvinciaSel(null);
					beneForm.setDisabledProvincia(true);
					selectedProvincia = false;
					beneForm.setCodiceCab(new ComboForm(null, null));
					insCab = false;
					beneForm.setIndirizzoBeneFin(null);
				}
			}
		}
		// FINE GESTIONE CODICE ABI

		// Inserimento del codice ABI :
		// Manualmente --> controllo se valido
		// Menu a completamento automatico della descrizione
		// In seguito imposto i campi CodiceAbi ed il menu DescOperFinanz
		if (abiInserito != null) {
			if (insManuale) {
				String descEnte = beneValidation.validateCodiceAbi(abiInserito, result);
				if (result.hasErrors()) {
					beneForm.setDescOperFinanz(new ComboForm(null, null));
					beneForm.setDescrizioneEnte(null);
					beneForm.setCodiceAbi(null);
					beneForm.setAbi(null);
					if (beneForm.getProvincia() != null)
						beneForm.getProvincia().setId("-1");
					beneForm.setProvinciaSel(null);
					beneForm.setDisabledProvincia(true);
					beneForm.setCodiceCab(new ComboForm(null, null));
					beneForm.setIndirizzoBeneFin(null);
					modelMap.addAttribute("bene", beneForm);
					return returnPage;
				} else {
					beneForm.setDescOperFinanz(new ComboForm(abiInserito, descEnte));
					beneForm.setDescrizioneEnte(descEnte);
				}
			} else {
				beneForm.setDescrizioneEnte(beneForm.getDescOperFinanz().getDescrizione());
			}
			beneForm.setCodiceAbi(abiInserito);
			beneForm.setAbi(abiInserito);
		} else {
			// Se ho inserito la Desc Ente manualmente
			// ma poi non ho selezionato nulla dal menu
			// a completamento automatico
			beneForm.setDescOperFinanz(new ComboForm(null, null));
			beneForm.setDescrizioneEnte(null);
		}

		// *************************************************************************************************************
		// Ho già inserito il codice ABI
		if (beneForm.getCodiceAbi() != null) {
			// Prendo le province associate all'ABI inserito
			List<ComboForm> province = commonService.listaProvinceAbi(beneForm.getCodiceAbi());
			modelMap.addAttribute("provinceAbi", province);
			beneForm.setDisabledProvincia(false);
			// Provincia selezionata
			if (beneForm.getProvincia() != null) {
				if ((beneForm.getProvincia().getId() != null)
						&& !beneForm.getProvincia().getId().equals("-1")) {
					selectedProvincia = true; // Ho inserito la Provincia
					if (beneForm.getProvinciaSel() == null)
						// Se non era presente una provincia precedente
						beneForm.setProvinciaSel(beneForm.getProvincia().getId());
					else {
						// Se provincia precedente è presente
						String provPrec = beneForm.getProvinciaSel();
						String provAtt = beneForm.getProvincia().getId();
						// Se l'attuale è diversa da quella precedente
						if (!provAtt.equalsIgnoreCase(provPrec)) {
							provMod = true;
							// Imposto la nuova provincia selezionata
							beneForm.setProvinciaSel(provAtt);
						}
					}
				} else {
					selectedProvincia = false;
					beneForm.setProvinciaSel(null);
				}
			}
			// Se ho cambiato la provincia
			// come se avessi annullato il CAB
			if (provMod)
				insCab = false;
			else {
				// Non ho modificato la provincia, quindi verifico se ho
				// selezionato il CAB
				// Sia nel caso della selezione dal menu a tendina semplice
				// che nel caso della selezione dal menu a completamento
				// automatico
				if ((beneForm.getCodiceCab().getId() != null)
						&& !beneForm.getCodiceCab().getId().equals("0")) {
					insCab = true;
				} else {
					insCab = false;
				}
			}
		}
		if (selectedProvincia) {
			// Carico il menu a tendina con tutti i CAB associati all'ABI
			// ed alla provincia selezionati
			List<ComboForm> allCab = commonService.listaCodiciCab(beneForm.getCodiceAbi(),
					beneForm.getProvincia().getId());
			modelMap.addAttribute("codiciCab", allCab);
			if (insCab) {
				// Setto il valore di default del CAB selezionato
				beneForm.setCodiceCab(
						new ComboForm(beneForm.getCodiceCab().getId(), beneForm.getCodiceCab().getId()));
				// Setto l'indirizzo associato ad ABI e CAB inseriti
				BeneForm beneForm2 = beneService.caricaInfoOperFinanz(beneForm.getCodiceAbi(),
						beneForm.getCodiceCab().getId());
				IndirizzoForm indirizzo = Utility.costruisciIndirizzo(beneForm2);
				beneForm.setIndirizzoBeneFin(indirizzo.getDenominazione());
			}
			// Se annullo il CAB, devo annullare anche l'indirizzo
			else {
				beneForm.setIndirizzoBeneFin(null);
			}
		} else if (insCab) {
			if (beneForm.getIndirizzoBeneFin() != null) {
				// Ho annullato la selezione della provincia in presenza di
				// CAB e Indirizzo, che sono quindi da annullare
				if (beneForm.getProvincia() != null)
					beneForm.getProvincia().setId("-1");
				beneForm.setProvinciaSel(null);
				beneForm.setCodiceCab(new ComboForm(null, null));
				beneForm.setIndirizzoBeneFin(null);
			} else {
				BeneForm beneForm2 = beneService.caricaInfoOperFinanz(beneForm.getCodiceAbi(),
						beneForm.getCodiceCab().getId());
				// Metto la provincia come valore di default nel menu a
				// tendina relativo
				beneForm.setProvincia(new ComboForm(beneForm2.getProvincia().getId(),
						beneForm2.getProvincia().getDescrizione()));
				beneForm.setProvinciaSel(beneForm2.getProvincia().getId());
				// Setto l'indirizzo associato ad ABI e CAB inseriti
				IndirizzoForm indirizzo = Utility.costruisciIndirizzo(beneForm2);
				beneForm.setIndirizzoBeneFin(indirizzo.getDenominazione());
				// Carico il menu a tendina con tutti i CAB associati
				// all'ABI ed alla provincia relativi
				List<ComboForm> allCab = commonService.listaCodiciCab(beneForm.getCodiceAbi(),
						beneForm2.getProvincia().getId());
				modelMap.addAttribute("codiciCab", allCab);
				beneForm.setCodiceCab(
						new ComboForm(beneForm.getCodiceCab().getId(), beneForm.getCodiceCab().getId()));
			}
		}
		// Se non sono presenti Provincia e CAB
		// Non deve essere presente l'indirizzo
		else
			beneForm.setIndirizzoBeneFin(null);

		// *************************************************************************************************************

		// Validazione codice IBAN
		if (beneForm.getCodiceIban() != null) {
			beneValidation.validateCodiceIban(beneForm, result);
		}
		// Gestione combo TipoValuta e campo Valuta Est
		if (beneForm.getTipoValuta() != null) {
			if (beneForm.getTipoValuta().getId().equals("-1")) {
				beneForm.setDisabledValutaEst(true);
				beneForm.setValutaEst(null);
			} else {
				beneForm.setDisabledValutaEst(false);
			}
		} else {
			beneForm.setDisabledValutaEst(true);
		}
		modelMap.addAttribute("bene", beneForm);
		return returnPage;
	}

	@RequestMapping(value = "/procedimento/bene/dettaglioBene.htm", method = RequestMethod.GET)
	public String getDettaglioBene(BeneForm beneForm, BindingResult result, HttpServletRequest request,
			ModelMap modelMap) {

		return includeDettaglioBene(beneForm, result, modelMap, request, "Dettaglio");
	}

	@RequestMapping(value = "/procedimento/bene/dettaglioBene.htm", method = RequestMethod.POST, params = {
			"ConfermaAssociaBene" })
	public String confermaAssociaBeneAlProcedimento(BeneForm beneForm, BindingResult result,
			HttpServletRequest request, ModelMap modelMap) {
		// boolean error = false;
		long idProcedimento = ((ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		int idUffOwner = (Integer) request.getSession().getAttribute("idUffOwner");
		String codUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);
		try {
			beneService.associaBeneAlProcedimento(idUffOwner, codUtente, idProcedimento, beneForm);
			super.setMessage(Constants.SUCCESS_MESSAGE, "bene.associabene.conferma",
					String.valueOf(beneForm.getIdBene()), true);
		} catch (Exception e) {
			// error = true;
			super.setMessage(Constants.MESSAGE, "bene.associabene.errore", null, true);
		}
		return "redirect:/procedimento/bene/beniCollegati.htm";
	}

	@RequestMapping(value = "/procedimento/bene/dettaglioBeneAtto.htm", method = RequestMethod.POST, params = {
			"ConfermaAssociaBene" })
	public String confermaAssociaBeneAlProcedimentoAtto(BeneForm beneForm, BindingResult result,
			HttpServletRequest request, ModelMap modelMap) {

		long idProcedimento = ((ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		int idUffOwner = (Integer) request.getSession().getAttribute("idUffOwner");
		String codUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);
		beneService.associaBeneAlProcedimento(idUffOwner, codUtente, idProcedimento, beneForm);
		super.setMessage(Constants.SUCCESS_MESSAGE, "bene.associabene.conferma",
				String.valueOf(beneForm.getIdBene()), true);
		return "redirect:/procedimento/bene/beniCollegatiAtto.htm";
	}

	@RequestMapping(value = "/procedimento/bene/dettaglioBeneAtto.htm", method = RequestMethod.POST, params = {
			"ConfermaEliminaBeneAtto" })
	public String confermaEliminaBeneAtto(BeneForm beneForm, BindingResult result, HttpServletRequest request,
			ModelMap modelMap) {

		long idProcedimento = ((ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		long idBene = beneForm.getIdBene();
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
		beneService.eliminaRelProcBene(idProcedimento, idBene, idUfficioUtente);
		super.setMessage(Constants.SUCCESS_MESSAGE, "bene.eliminabene.conferma", String.valueOf(idBene),
				true);
		beneForm.setIdBene(null);
		return "redirect:/procedimento/bene/beniCollegatiAtto.htm";
	}

	@RequestMapping(value = "/procedimento/bene/dettaglioBene.htm", method = RequestMethod.POST, params = {
			"ConfermaEliminaBene" })
	public String confermaEliminaBeneDalProcedimento(BeneForm beneForm, BindingResult result,
			HttpServletRequest request, ModelMap modelMap) {

		if (StringUtils.isEmpty(request.getParameter("controlloEffettuato"))) {
			super.setMessage(Constants.MESSAGE, "bene.eliminabene.confermaEliminazione", null, true);
			request.setAttribute("controlloEffettuato", Boolean.TRUE);
			return includeDettaglioBene(beneForm, result, modelMap, request, "Elimina");
		}

		long idProcedimento = ((ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		long idBene = beneForm.getIdBene();
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");

		

		

		beneService.eliminaBene(idBene, idUfficioUtente);
		
		attoService.eliminaMisBeneAtto(idProcedimento, -1, idBene, idUfficioUtente, -1, -1);
		
		beneService.eliminaRelProcBene(idProcedimento, idBene, idUfficioUtente);

		super.setMessage(Constants.SUCCESS_MESSAGE, "bene.eliminabene.conferma", String.valueOf(idBene),
				true);
		beneForm.setIdBene(null);
		return "redirect:/procedimento/bene/beniCollegati.htm";
	}

	@RequestMapping(value = "/procedimento/bene/dettaglioBene.htm", method = RequestMethod.POST, params = {
			"DettaglioDatiAmministratore" })
	public String dettaglioDatiAmministratoreDelBene(BeneForm beneForm, BindingResult result,
			HttpServletRequest request, ModelMap modelMap) {

		long idProcedimento = ((ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		int idUffOwner = (Integer) request.getSession().getAttribute("idUffOwner");
		long idBene = beneForm.getIdBene();
		if (beneForm.getIdAtto() == null) {
			super.setMessage(Constants.MESSAGE, "bene.atti.nonselezionato", null, true);
			return includeDettaglioBene(beneForm, result, modelMap, request, "Dettaglio");
		}
		long idAtto = beneForm.getIdAtto();

		beneForm = beneService.beneById(idUffOwner, idBene, idProcedimento);
		beneForm.setIdAtto(idAtto);

		// lettura depositi_amm da idAtto e idProcedimentoFg
		TDepositiAmm tDepositoAmm = beneService.getDepositoAmmByIdAtto(idAtto, idUffOwner, idProcedimento);
		beneForm.setIdDeposito(tDepositoAmm.getCodiKIdDepositoAmm());

		String amministratore = beneService.getAmministratoreBeneAtto(idProcedimento, idAtto, idUffOwner,
				idBene);
		beneForm.setAmministratore(amministratore);

		if (tDepositoAmm != null && tDepositoAmm.getXmlToolAmm() != null) {
			// lettura XML dati gestione del DEPOSITO_AMM
			Blob blobXml = tDepositoAmm.getXmlToolAmm();

			try {
				InputStream inStream = blobXml.getBinaryStream();
				InputStreamReader inStreamReader = new InputStreamReader(inStream);
				Reader reader = new BufferedReader(inStreamReader);

				JAXBContext jaxbContext = JAXBContext.newInstance(RicezioneRelazione.class);
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				RicezioneRelazione relazione = (RicezioneRelazione) unmarshaller.unmarshal(reader);

				// legge il bene
				List<BeneExtType> beni = relazione.getBene();
				for (BeneExtType bene : beni) {

					if (bene.getIdentificativoBene() != null) {
						long idBeneXml = Long.parseLong(bene.getIdentificativoBene().toString());
						if (idBene == idBeneXml) {

							ToolAmmMapper.map(bene, beneForm);

							break;
						}
					}
				}
			} catch (JAXBException e) {
				super.setMessage(Constants.MESSAGE, "bene.atti.errore", null, true);
				return includeDettaglioBene(beneForm, result, modelMap, request, "Dettaglio");
			} catch (SQLException e) {
				return includeDettaglioBene(beneForm, result, modelMap, request, "Dettaglio");
			}
			addBreadcrumbs("POST", "/procedimento/bene/dettaglioBene.htm", "DettaglioDatiAmministratore");
			modelMap.addAttribute("beneForm", beneForm);
			request.getSession().setAttribute("DatiSpecificiBene", beneForm.getDatiSpecificiBene());
			return "/procedimento/bene/amministratore/dettaglioDatiAmmBene";
		} else {
			super.setMessage(Constants.MESSAGE, "bene.atti.errore", null, true);
			return includeDettaglioBene(beneForm, result, modelMap, request, "Dettaglio");
		}
	}

	@RequestMapping(value = "/procedimento/bene/amministratore/dettaglioDatiAmmBene.htm", method = RequestMethod.GET)
	public String dettaglioDatiAmmBene(BeneForm beneForm, BindingResult result, HttpServletRequest request,
			ModelMap modelMap) {
		return "/procedimento/bene/amministratore/dettaglioDatiAmmBene";
	}

	@RequestMapping(value = "/procedimento/bene/frazionamentoBene.htm", method = RequestMethod.POST, params = {
			"ConfermaFrazionamentoBene" })
	public String confermaFrazionamentoBene(BeneForm beneForm, BindingResult result,
			HttpServletRequest request, ModelMap modelMap) {

		beneValidation.validateSceltaCheckboxAlmenoDueBeni(beneForm, result, request);
		if (result.hasErrors()) {
			return includeFrazionamentoBene(beneForm, result, request, modelMap);
		}
		long idProcedimento = ((ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		String idCodiceUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);
		long idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
		beneService.frazionamentoBene(beneForm, idProcedimento, idUfficioUtente, idAtto, idCodiceUtente);
		super.setMessage(Constants.SUCCESS_MESSAGE, "bene.frazionabene.conferma",
				String.valueOf(beneForm.getIdBene()), true);
		return "redirect:/procedimento/bene/beniCollegatiAtto.htm";
	}

	@RequestMapping(value = "/procedimento/bene/gestioneBeniDelProcedimento.htm", method = RequestMethod.POST, params = {
			"CopiaBene" })
	public String copiaBene(BeneForm beneForm, BindingResult result, ModelMap modelMap,
			HttpServletRequest request) {

		// funzione per prelevare i checkbox paginati
		chooseCheckBoxPaged(beneForm, request);

		beneValidation.validateSceltaSingolaCheckbox(beneForm, result, request);
		if (result.hasErrors()) {
			return visualizzaRicercaDelBene(beneForm, request, modelMap);
		}

		// Giacomo 11/07/2013 Aggiunta BreadCrumbs
		// addBreadcrumbs("CopiaBene");

		long idProcedimento = ((ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		beneForm.setIdBene(beneForm.getListaIdCheckbox().get(0));
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
		BeneForm bene = beneService.beneById(idUfficioUtente, beneForm.getIdBene(), idProcedimento);

		// P.Q. 28/06/2021 Mev 9 Inizio
		if (bene.getDescOperFinanz() != null && bene.getDescOperFinanz().getDescrizione() != null && bene.getDescOperFinanz().getDescrizione().equals(Constants.EnteFittizio.DESCRIZIONE)) {
			// imposto a blank i campi altrimenti vengono visualizzati in modifica
			bene.getDescOperFinanz().setDescrizione("");
			bene.setCodiceAbi("");
			bene.getCodiceCab().setDescrizione("");
			bene.getCodiceCab().setId("");
			//F.C. MEV 2021/19 
			//bene.setNumeroRegistrazione("");
		}
		// P.Q. 28/06/2021 Mev 9 Fine
		
		// gestione codici EQUITALIA
		List<ComboForm> listaVersato = riempiComboVersato("Modifica");

		modelMap.addAttribute("listaVersato", listaVersato);

		if (!StringUtils.equals("1", bene.getStato().getId())
				&& !StringUtils.equals("-1", bene.getStato().getId())) {
			bene.setComune(new ComboForm(null, bene.getDesComuneEstero()));
		}

		modelMap.addAttribute("categorie", getCategorie(bene, result));
		modelMap.addAttribute("sottocategorie", getSottocategorie(bene, result));
		modelMap.addAttribute("statiConservazione", getStatiConservazione(bene, result, request));
		return visualizzaInserisciBene(bene, modelMap, request);
	}

	@RequestMapping(value = "/procedimento/bene/gestioneBeniDelAtto.htm", method = RequestMethod.POST, params = {
			"CopiaBene" })
	public String copiaBeneAtto(BeneForm beneForm, BindingResult result, ModelMap modelMap,
			HttpServletRequest request) {

		beneValidation.validateSceltaSingolaCheckbox(beneForm, result, request);
		if (result.hasErrors()) {
			return visualizzaRicercaDelBeneAtto(beneForm, request, modelMap);
		}
		beneForm.setIdBene(beneForm.getListaIdCheckbox().get(0));
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		long idProcedimento = ((ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		BeneForm bene = beneService.beneById(idUfficioUtente, beneForm.getIdBene(), idProcedimento);
		modelMap.addAttribute("categorie", getCategorie(bene, result));
		modelMap.addAttribute("sottocategorie", getSottocategorie(bene, result));
		return visualizzaInserisciBene(bene, modelMap, request);
	}

	@RequestMapping(value = "/procedimento/bene/associaTitoloDiInteresse.htm", method = RequestMethod.POST, params = {
			"TerzoSoggettoFisico" })
	public String creaTerzoSoggettoFisico(BeneForm bene, BindingResult result, HttpServletRequest request) {
		request.getSession().setAttribute("indietroTerzoSoggetto",
				"/procedimento/bene/visualizzaTitoliDiInteresse.htm");
		return "redirect:/procedimento/nuovo/visualizzaRicercaSoggettoFisicoTerzo.htm";
	}

	@RequestMapping(value = "/procedimento/bene/associaTitoloDiInteresse.htm", method = RequestMethod.POST, params = {
			"TerzoSoggettoGiuridico" })
	public String creaTerzoSoggettoGiuridico(BeneForm bene, BindingResult result,
			HttpServletRequest request) {
		request.getSession().setAttribute("indietroTerzoSoggetto",
				"/procedimento/bene/visualizzaTitoliDiInteresse.htm");
		return "redirect:/procedimento/nuovo/visualizzaRicercaSoggettoGiuridicoTerzo.htm";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/procedimento/bene/gestioneBeniDelProcedimento.htm", method = RequestMethod.POST, params = {
			"Dettaglio" })
	public String dettaglioBene(BeneForm beneForm, BindingResult result, HttpServletRequest request,
			ModelMap modelMap) {

		// funzione per prelevare i checkbox paginati
		chooseCheckBoxPaged(beneForm, request);

		beneValidation.validateSceltaSingolaCheckbox(beneForm, result, request);
		if (result.hasErrors()) {
			return visualizzaRicercaDelBene(beneForm, request, modelMap);
		}

		// Giacomo 11/07/2013 Aggiunta BreadCrumbs
		// addBreadcrumbs("Dettaglio");
		addBreadcrumbs("POST", "/procedimento/bene/gestioneBeniDelProcedimento.htm", "Dettaglio");

		if (beneForm.getListaIdCheckbox() != null) {
			beneForm.setIdBene(beneForm.getListaIdCheckbox().get(0));
			return includeDettaglioBene(beneForm, result, modelMap, request, "Dettaglio");
		}
		if (beneForm.getListaIdCheckboxString() != null) {
			BeneForm formBene = null;
			String bene = beneForm.getListaIdCheckboxString().get(0);
			// bene = bene.replaceAll(",", "-");
			String[] parti = StringUtils.split(bene, '@'); // bene.split("@");
			int size = parti.length;
			long idDeposito = 0;
			String tipologia = "";
			String categoria = "";
			String sottoCategoria = "";
			if (size > 0)
				idDeposito = Long.valueOf(parti[0]);
			if (size > 1)
				tipologia = parti[1].replaceAll("§", ",");
			if (size > 2)
				categoria = parti[2].replaceAll("§", ",");
			if (size > 3)
				sottoCategoria = parti[3].replaceAll("§", ",");

			if (request.getSession().getAttribute("listaNuoviBeni") != null) {
				List<BeneForm> listaNuoviBeni = (List<BeneForm>) request.getSession()
						.getAttribute("listaNuoviBeni");
				for (int i = 0; i < listaNuoviBeni.size(); i++) {
					long deposito = 0;
					String tipo = "";
					String cat = "";
					String sottoCat = "";
					BeneForm form = listaNuoviBeni.get(i);
					if (form.getIdDeposito() != null && form.getIdDeposito() > 0)
						deposito = form.getIdDeposito();
					if (form.getTipologia() != null && form.getTipologia().getDescrizione() != null)
						tipo = form.getTipologia().getDescrizione();
					if (form.getCategoria() != null && form.getCategoria().getDescrizione() != null)
						cat = form.getCategoria().getDescrizione();
					if (form.getSottocategoria() != null && form.getSottocategoria().getDescrizione() != null)
						sottoCat = form.getSottocategoria().getDescrizione();
					if (deposito == idDeposito) {
						if (tipologia.equalsIgnoreCase(tipo) && categoria.equalsIgnoreCase(cat)
								&& sottoCategoria.equalsIgnoreCase(sottoCat)) {
							formBene = form;
							break;
						}
					}
				}

				return includeDettaglioNuovoBene(formBene, result, modelMap, request, "Dettaglio");
			}

		}

		return visualizzaRicercaDelBene(beneForm, request, modelMap);

	}

	@RequestMapping(value = "/procedimento/bene/gestioneBeniDelAtto.htm", method = RequestMethod.POST, params = {
			"Dettaglio" })
	public String dettaglioBeneAtto(BeneForm beneForm, BindingResult result, HttpServletRequest request,
			ModelMap modelMap) {

		beneValidation.validateSceltaSingolaCheckbox(beneForm, result, request);
		if (result.hasErrors()) {
			return visualizzaRicercaDelBeneAtto(beneForm, request, modelMap);
		}
		beneForm.setIdBene(beneForm.getListaIdCheckbox().get(0));
		addBreadcrumbs("Dettaglio", request);
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		long idProcedimento = ((ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		BeneForm bene = beneService.beneById(idUfficioUtente, beneForm.getIdBene(), idProcedimento);
		if (bene.getDescOperFinanz() != null && bene.getDescOperFinanz().getDescrizione() != null && Constants.EnteFittizio.DESCRIZIONE.equals(bene.getDescOperFinanz().getDescrizione())) {
			// imposto a blank i campi altrimenti vengono visualizzati in modifica
			bene.getDescOperFinanz().setDescrizione("");
			bene.getDescOperFinanz().setId("");
			bene.setDescrizioneEnte("");																
			bene.setCodiceAbi("");
			bene.getCodiceCab().setDescrizione("");
			bene.getCodiceCab().setId("");
			bene.setIndirizzoBeneFin("");
			bene.setProvinciaEntePolizza("");
			bene.getStato().setDescrizione("");
			bene.getStato().setId("");
			//F.C. MEV 2021/19 
			//beneForm.setNumeroRegistrazione("");
		}
		
		bene.setDettAssBene("Dettaglio");
		modelMap.addAttribute("beneForm", bene);
		if (bene.getCategoria().getId().equals(Constants.CategoriaBeni.ID_POLIZZE_S)) {
			return "/procedimento/atto/dettaglioAttoPolizza";
		}
		return "/procedimento/bene/dettaglioBeneAtto";
	}

	@RequestMapping(value = "/procedimento/bene/ricercaBene.htm", method = RequestMethod.POST, params = {
			"Dettaglio" })
	public String dettaglioBeneRicerca(BeneForm beneForm, BindingResult result, ModelMap modelMap,
			HttpServletRequest request) {

		beneValidation.validateListaBeni(beneForm, request, result);
		if (result.hasErrors()) {
			if ("RicercaBDC".equalsIgnoreCase(request.getParameter("visualizzaPulsante"))) {
				return ricercaBeneinBDC(beneForm, result, request, modelMap);
			} else if ("Ricerca".equalsIgnoreCase(request.getParameter("visualizzaPulsante"))) {
				return ricercaBeneinBaseDati(beneForm, result, request, modelMap);
			}
		}

		// Giacomo 11/07/2013 BreadCrumbs
		addBreadcrumbs("Dettaglio", request);

		return includeDettaglioBene(beneForm, result, modelMap, request, "Dettaglio");
	}

	@RequestMapping(value = "/procedimento/bene/gestioneBeniDelProcedimento.htm", method = RequestMethod.POST, params = {
			"EliminaBene" })
	public String eliminaBene(BeneForm beneForm, BindingResult result, ModelMap modelMap,
			HttpServletRequest request) {

		// funzione per prelevare i checkbox paginati
		chooseCheckBoxPaged(beneForm, request);

		beneValidation.validateSceltaSingolaCheckbox(beneForm, result, request);
		if (result.hasErrors()) {
			return visualizzaRicercaDelBene(beneForm, request, modelMap);
		}
		long idBene = beneForm.getListaIdCheckbox().get(0);
		if (result.hasErrors()) {
			return visualizzaRicercaDelBene(beneForm, request, modelMap);
		}
		beneForm.setIdBene(idBene);

		// Giacomo 11/07/2013 BreadCrumbs
		addBreadcrumbs("EliminaBene", request);
		return includeDettaglioBene(beneForm, result, modelMap, request, "Elimina");
	}

	@RequestMapping(value = "/procedimento/bene/gestioneBeniDelAtto.htm", method = RequestMethod.POST, params = {
			"EliminaBene" })
	public String eliminaBeneAtto(BeneForm beneForm, BindingResult result, ModelMap modelMap,
			HttpServletRequest request) {

		beneValidation.validateSceltaSingolaCheckbox(beneForm, result, request);
		if (result.hasErrors()) {
			return visualizzaRicercaDelBeneAtto(beneForm, request, modelMap);
		}
		long idProcedimento = ((ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		long idBene = beneForm.getListaIdCheckbox().get(0);
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		beneValidation.verificaPresenzaTitoliDiritto(idProcedimento, idBene, idUfficioUtente, result,
				request);
		if (!result.hasErrors()) {
			beneForm.setIdBene(idBene);
			BeneForm bene = beneService.beneById(idUfficioUtente, beneForm.getIdBene(), idProcedimento);
			bene.setDettAssBene("Elimina");
			modelMap.addAttribute("beneForm", bene);
			return "/procedimento/bene/dettaglioBeneAtto";
		} else
			return visualizzaRicercaDelBeneAtto(beneForm, request, modelMap);
	}

	@RequestMapping(value = "/procedimento/bene/copiaTitoliDiDiritto.htm", method = RequestMethod.POST, params = {
			"ConfermaCopiaTitoliDiDiritto" })
	public String eseguiCopiaTitoliDiDiritto(BeneForm beneForm, BindingResult result,
			HttpServletRequest request, ModelMap modelMap) {

		long idProcedimento = ((ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		String idCodiceUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);
		long idAtto = 0l;
		beneService.copiaTitoloDiDiritto(beneForm, idProcedimento, idUfficioUtente, idAtto, idCodiceUtente,
				super.printMethodName());
		super.setMessage(Constants.SUCCESS_MESSAGE, "bene.copiatitoli.conferma", " ", true);
		return "redirect:/procedimento/bene/beniCollegati.htm";
	}

	@RequestMapping(value = "/procedimento/bene/copiaTitoliDiDirittoAtto.htm", method = RequestMethod.POST, params = {
			"ConfermaCopiaTitoliDiDiritto" })
	public String eseguiCopiaTitoliDiDirittoAtto(BeneForm beneForm, BindingResult result,
			HttpServletRequest request, ModelMap modelMap) {

		long idProcedimento = ((ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		String idCodiceUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);
		long idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
		beneService.copiaTitoloDiDiritto(beneForm, idProcedimento, idUfficioUtente, idAtto, idCodiceUtente,
				super.printMethodName());
		super.setMessage(Constants.SUCCESS_MESSAGE, "bene.copiatitoli.conferma", " ", true);
		return "redirect:/procedimento/bene/beniCollegatiAtto.htm";

	}

	@RequestMapping(value = "/procedimento/bene/gestioneBeniDelAtto.htm", method = RequestMethod.POST, params = {
			"FrazionamentoBene" })
	public String frazionamentoBene(BeneForm beneForm, BindingResult result, HttpServletRequest request,
			ModelMap modelMap) {

		// frazionamento
		beneValidation.validateSceltaSingolaCheckbox(beneForm, result, request);
		if (result.hasErrors()) {
			return visualizzaRicercaDelBeneAtto(beneForm, request, modelMap);
		}
		// bene selezionato per fare il frazionamento
		Long idBene = beneForm.getListaIdCheckbox().get(0);
		beneForm.setIdBene(idBene);
		return includeFrazionamentoBene(beneForm, result, request, modelMap);
	}

	@ModelAttribute("categorie")
	public List<ComboForm> getCategorie(@Valid BeneForm beneForm, BindingResult result) {

		if ((beneForm == null) || (beneForm.getTipologia() == null)
				|| (beneForm.getTipologia().getId() == null)) {
			return null;
		}
		return commonService.listaCategorieDelBene(beneForm);
	}

	@ModelAttribute("normative")
	public List<ComboForm> getNormative(@Valid BeneSoggettoForm beneSoggettoForm, BindingResult result) {

		SoggettoForm soggetto = beneSoggettoForm.getSoggetto();
		if (soggetto != null) {
			ComboForm tipoPrevenzione = soggetto.getTipologiaPrevenzione();
			ComboForm tipoMisura = soggetto.getMisura().getTipoMisura();
			if (Utility.isDefinito(tipoPrevenzione) && Utility.isDefinito(tipoMisura)) {
				return commonService.getNormativaByTipoPrevenzioneAndTipoMisura(soggetto);
			}
		}
		return null;
	}

	@ModelAttribute("normativenew")
	public List<ComboForm> getNormativeNew(@Valid BeneSoggettoForm beneSoggettoForm, BindingResult result) {

		SoggettoForm soggetto = beneSoggettoForm.getSoggetto();
		if (soggetto != null) {
			ComboForm tipoMisura = soggetto.getMisura().getTipoMisura();
			if (Utility.isDefinito(tipoMisura)) {
				return commonService.getNormativaByTipoPrevenzioneAndTipoMisura(soggetto);
			}
		}
		return null;
	}

	@ModelAttribute("sottocategorie")
	public List<ComboForm> getSottocategorie(@Valid BeneForm beneForm, BindingResult result) {

		if ((beneForm == null) || (beneForm.getCategoria() == null)
				|| (beneForm.getCategoria().getId() == null)) {
			return null;
		}
		return commonService.listaSottocategorieDelBene(beneForm);
	}

	@ModelAttribute("statiConservazione")
	public List<ComboForm> getStatiConservazione(@Valid BeneForm beneForm, BindingResult result,
			HttpServletRequest request) {

		if (beneForm != null && beneForm.getTipologia() != null && beneForm.getTipologia().getId() != null) {

			return commonService.listaStatiConservazione(Integer.parseInt(beneForm.getTipologia().getId()));
		}
		return null;
	}

	@ModelAttribute("tipiMisuraTipoSequestro")
	public List<ComboForm> getTipiMisuraDecretoSequestro(@Valid AttoForm attoForm, BindingResult result,
			HttpServletRequest request) {

		if ((request.getSession().getAttribute(Constants.ATTO) != null)
				&& (((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto() > 0l)) {
			attoForm = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO)));
		}
		if ((attoForm != null) && (attoForm.getCategoriaAtto() != null)) {
			String carattereMisura = null;
			if (Constants.DECRETO_SEQUESTRO_URGENTE.equalsIgnoreCase(attoForm.getCategoriaAtto().getId())) {
				carattereMisura = "SU";
			} else if (Constants.DECRETO_SEQUESTRO_ANTICIPATO
					.equalsIgnoreCase(attoForm.getCategoriaAtto().getId())) {
				carattereMisura = "SA";
			} else if (Constants.DECRETO_SEQUESTRO_ORDINARIO
					.equalsIgnoreCase(attoForm.getCategoriaAtto().getId())
					|| Constants.DECRETO_SEQUESTRO_ORDINARIO_II_GRADO
							.equalsIgnoreCase(attoForm.getCategoriaAtto().getId())) {
				carattereMisura = "SO";
			} else if (Constants.DECRETO_REVOCA_SEQUESTRO
					.equalsIgnoreCase(attoForm.getCategoriaAtto().getId())
					|| Constants.DECRETO_REVOCA_SEQUESTRO_II_GRADO
							.equalsIgnoreCase(attoForm.getCategoriaAtto().getId())) {
				carattereMisura = "SR";
			}
			return commonService.listaTipiMisuraDecretoSequestro(carattereMisura);
		}
		return null;
	}

	@ModelAttribute("valuta")
	public List<ComboForm> getValuta() {

		valuta = StaticListLoader.getTipiValuta();
		return valuta;
	}

	// @RequestMapping(value = { "/procedimento/bene/inserimento*.htm",
	// "/procedimento/bene/inserimentoImmobile.htm",
	// "/procedimento/bene/inserimentoBeneFinanziario.htm",
	// "/procedimento/bene/inserimentoDenaro.htm",
	// "/procedimento/bene/associaTitoloDiInteresse.htm"}, method =
	// RequestMethod.POST, params = { "Indietro" })
	// public String indietro(BeneForm beneForm, HttpServletRequest request,
	// ModelMap modelMap) {
	//
	// beneForm = (BeneForm) super.getIndietroForm();
	// String tastoIndietro = beneForm.getTastoIndietro();
	// if(beneForm.isVisualizzaFiltro()){
	// beneForm.setIdBene(null);
	// beneForm.setTipologia(null);
	// beneForm.setCategoria(null);
	// beneForm.setSottocategoria(null);
	// beneForm.setVisualizzaFiltro(false);
	// }
	// if ("visualizzaRicercaDelBene".equalsIgnoreCase(tastoIndietro)) {
	// return "redirect:/procedimento/bene/beniCollegati.htm";
	// } else if
	// ("visualizzaRicercaDelBeneAtto".equalsIgnoreCase(tastoIndietro)) {
	// return "redirect:/procedimento/bene/beniCollegatiAtto.htm";
	// } else return ricercaBeneinBDC(beneForm, request, modelMap);
	// }

	// @RequestMapping(value = {
	// "/procedimento/bene/copiaTitoliDiDirittoAtto.htm" }, method =
	// RequestMethod.POST, params = { "IndietroDaCopiaAtto" })
	// public String indietroDaCopiaAtto(BeneForm beneForm, HttpServletRequest
	// request, ModelMap modelMap) {
	//
	// beneForm.setTastoIndietro("visualizzaRicercaDelBeneAtto");
	// if(beneForm.isVisualizzaFiltro()){
	// beneForm.setIdBene(null);
	// beneForm.setTipologia(null);
	// beneForm.setCategoria(null);
	// beneForm.setSottocategoria(null);
	// beneForm.setVisualizzaFiltro(false);
	// }
	// return visualizzaRicercaDelBeneAtto(beneForm, request, modelMap);
	// }

	// @RequestMapping(value = {
	// "/procedimento/bene/copiaTitoliDiDirittoAtto.htm" }, method =
	// RequestMethod.POST, params = { "IndietroACopiaAtto" })
	// public String indietroACopiaAtto(BeneForm beneForm, BindingResult result,
	// HttpServletRequest request, ModelMap modelMap) {
	//
	// beneForm.setTastoIndietro("visualizzaRicercaDelBeneAtto");
	// if(!beneForm.isVisualizzaFiltro()){
	// beneForm.setIdBene(null);
	// beneForm.setTipologia(null);
	// beneForm.setCategoria(null);
	// beneForm.setSottocategoria(null);
	// beneForm.setVisualizzaFiltro(false);
	// beneForm.setIdRadio(null);
	// beneForm.setDescrizioneBene(null);
	// }
	// return visualizzaTitoliDiDirittoDaCopiareAtto(beneForm, result, request,
	// modelMap);
	// }

	// @RequestMapping(value = { "/procedimento/bene/copiaTitoliDiDiritto.htm"
	// }, method = RequestMethod.POST, params = { "IndietroDaCopia" })
	// public String indietroDaCopia(BeneForm beneForm, HttpServletRequest
	// request, ModelMap modelMap) {
	//
	// beneForm.setTastoIndietro("visualizzaRicercaDelBene");
	// if(beneForm.isVisualizzaFiltro()){
	// beneForm.setIdBene(null);
	// beneForm.setTipologia(null);
	// beneForm.setCategoria(null);
	// beneForm.setSottocategoria(null);
	// beneForm.setVisualizzaFiltro(false);
	// }
	// return visualizzaRicercaDelBene(beneForm, request, modelMap);
	// }
	//
	// @RequestMapping(value = { "/procedimento/bene/copiaTitoliDiDiritto.htm"
	// }, method = RequestMethod.POST, params = { "IndietroACopia" })
	// public String indietroACopia(BeneForm beneForm, BindingResult result,
	// HttpServletRequest request, ModelMap modelMap) {
	//
	// beneForm.setTastoIndietro("visualizzaRicercaDelBene");
	// if(!beneForm.isVisualizzaFiltro()){
	// beneForm.setIdBene(null);
	// beneForm.setTipologia(null);
	// beneForm.setCategoria(null);
	// beneForm.setSottocategoria(null);
	// beneForm.setVisualizzaFiltro(false);
	// beneForm.setIdRadio(null);
	// beneForm.setDescrizioneBene(null);
	// }
	// return visualizzaTitoliDiDirittoDaCopiare(beneForm, result, request,
	// modelMap);
	// }

	/*********************** Fine gestione beni dell'atto ************************************************/
	// metodo per cambiare la pagina dell'elenco
	@RequestMapping(value = "/procedimento/bene/gestioneBeniDelProcedimento.htm", method = RequestMethod.GET)
	public String ordinamentoBeniAssociati(BeneForm beneForm, HttpServletRequest request, ModelMap modelMap) {

		return visualizzaRicercaDelBene(beneForm, request, modelMap);
	}

	@RequestMapping(value = "/procedimento/bene/gestioneBeniDelAtto.htm", method = RequestMethod.GET)
	public String ordinamentoBeniAssociatiAtto(BeneForm beneForm, HttpServletRequest request,
			ModelMap modelMap) {

		return visualizzaRicercaDelBeneAtto(beneForm, request, modelMap);
	}

	@RequestMapping(value = { "/procedimento/bene/gestioneSoggettiDelBene.htm",
			"/procedimento/bene/associaTitoloDiInteresse.htm" }, method = RequestMethod.GET)
	public String ordinamentoSoggettiBene(BeneForm beneForm, BindingResult result, HttpServletRequest request,
			ModelMap modelMap) {

		/* 
		 * ISSUE MEV : Ticket INC000001875926 e altri: Se si vuole associare il bene a un soggetto che è nelle pagine successive.
		 * 	Paginando appare la lista dei beni invece della seconda pagina dei soggetti.
		 * Numero MEV : 4
		 * Autore    : Gioggi
		 * Data      : 18 lug 2018
		 * Branch    : MEV_4
		 */
//		return titoliDiDiritto(beneForm, result, request, modelMap);
		return titoliDiDirittoUnchecked(beneForm, request, modelMap);
		//***** FINE INTERVENTO MEV_4 *****//
	}

	@RequestMapping(value = "/procedimento/bene/confermaAssociazioneTitoloDiInteresse.htm", method = RequestMethod.GET)
	public String ordinamentoSoggettiBeneGiaAssociati(BeneForm bene, BindingResult result,
			HttpServletRequest request, ModelMap modelMap) {

		return titoliDiDirittoAssocia(bene, result, request, modelMap);
	}

	@RequestMapping(value = "/procedimento/bene/frazionamentoBene.htm", method = RequestMethod.GET)
	public String pagFrazionamentoBene(BeneForm beneForm, BindingResult result, HttpServletRequest request,
			ModelMap modelMap) {

		return frazionamentoBene(beneForm, result, request, modelMap);
	}

	@RequestMapping(value = "/procedimento/bene/ricercaBene.htm", method = RequestMethod.GET)
	public String paginazione(BeneForm beneForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {
		/*
		 * boolean existsRicercaBeneBDC = (String)request.getParameter("RicercaBDC") != null; if(
		 * existsRicercaBeneBDC ) { try { //ProfiloForm profilo = (ProfiloForm)
		 * request.getSession().getAttribute("profilo"); // Integer idUfficioUtente = (Integer)
		 * request.getSession().getAttribute("idUffOwner"); //Long idTipoUfficio = profilo.getIdTipoUfficio();
		 * //long idProcedimento = ((ProcedimentoForm)
		 * (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO ))).getIdProcedimento();
		 * //modelMap.addAttribute("risultatiBDC",bdcService .ricercaBeniBDC(beneForm
		 * ,idUfficioUtente.longValue(), idTipoUfficio, idProcedimento)); } catch(Exception e) {
		 * super.setMessage(Constants.MESSAGE, "webservice.ricerca.error", "Ricerca bene in BDC", false); } }
		 */
		return ricercaBeneinBaseDati(beneForm, result, request, modelMap);
	}

	@RequestMapping(value = "/procedimento/bene/gestioneBeniDelAtto.htm", method = RequestMethod.POST)
	public String postGestioneBeniDelAtto(BeneForm beneForm, HttpServletRequest request, ModelMap modelMap) {

		if (!beneForm.isVisualizzaFiltro()) {
			beneForm.setIdBene(null);
			beneForm.setTipologia(null);
			beneForm.setCategoria(null);
			beneForm.setSottocategoria(null);
		}
		return visualizzaRicercaDelBeneAtto(beneForm, request, modelMap);
	}

	@RequestMapping(value = "/procedimento/atto/dettaglioAttoBene.htm", method = RequestMethod.POST, params = {
			"Indietro" })
	public String indietroDalBeneAttoPolizza(BeneForm beneForm, HttpServletRequest request,
			ModelMap modelMap) {
		return "redirect:/procedimento/bene/beniCollegatiAtto.htm";
	}

	@RequestMapping(value = "/procedimento/bene/gestioneBeniDelProcedimento.htm", method = RequestMethod.POST)
	public String postGestioneBeniDelProcedimento(BeneForm beneForm, HttpServletRequest request,
			ModelMap modelMap) {

		if (!beneForm.isVisualizzaFiltro()) {
			beneForm.setIdBene(null);
			beneForm.setTipologia(null);
			beneForm.setCategoria(null);
			beneForm.setSottocategoria(null);
		}
		return visualizzaRicercaDelBene(beneForm, request, modelMap);
	}

	@RequestMapping(value = "/procedimento/bene/ricercaBene.htm", method = RequestMethod.POST)
	public String postRicercaBene(BeneForm beneForm, ModelMap modelMap, HttpServletRequest request) {
		super.removeFromSession("risultatiBDC");
		if (request.getSession().getAttribute(Constants.ATTO) != null)
			return aggiungiBeneAtto(beneForm, modelMap);
		else
			return aggiungiBene(beneForm, modelMap, request);

	}

	@RequestMapping(value = "/procedimento/bene/ricercaBene.htm", method = RequestMethod.POST, params = {
			"Ricerca" })
	public String ricercaBeneinBaseDati(BeneForm beneForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {

		long idProcedimento = ((ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
		if ((beneForm != null) && (beneForm.getTipologia() != null)
				&& (beneForm.getTipologia().getId() != null)
				&& !"-1".equalsIgnoreCase(beneForm.getTipologia().getId())) {
			// ottobre 2018 reso obbligatorio la selezione della categoria!!! 
			//if ((beneForm.getTipologia().getId().equals("2") || beneForm.getTipologia().getId().equals("3"))
			//		&& ((beneForm.getCategoria() == null) || (beneForm.getCategoria().getId() == null)
			//				|| "-1".equalsIgnoreCase(beneForm.getCategoria().getId()))) {
			  if ((beneForm.getCategoria() == null) || (beneForm.getCategoria().getId() == null)
							|| "-1".equalsIgnoreCase(beneForm.getCategoria().getId())) {  
				super.setMessage(Constants.MESSAGE, "bene.ricerca.errore.categoriaMancante", null, false);
			} else {
				/*  GC 28/08/2024: TT#202407020117
				 * Verifico il campo numregistrazione in base al tipo di bene
				 * Per beneFinanziario è diventato alfanumerico; negli altri casi è numerico
				 */
				if (!chkNumReg(beneForm, result, request).hasErrors()) {
					modelMap.addAttribute("risultatiBaseDati",
						beneService.beniRicercaCriteri(idUfficioUtente, idProcedimento, beneForm));
				modelMap.addAttribute("visualizzaPulsante", "Ricerca");
				}
			}
		} else {
			super.setMessage(Constants.MESSAGE, "form.selection", null, false);
		}
		boolean existsRicercaBeneBDC = (String) request.getParameter("RicercaBDC") != null;
		if (!existsRicercaBeneBDC) {
			// rimuovi dalla sessione la lista dei beni frutto della ricerca su
			// BDC (opzionale)
			super.removeFromSession("risultatiBDC");
		}

		// Giacomo 11/07/2013 BreadCrumbs
		addBreadcrumbs("Ricerca", request);

		return "/procedimento/bene/aggiungiBene";
	}

	@RequestMapping(value = "/procedimento/bene/ricercaBene.htm", method = RequestMethod.POST, params = {
			"RicercaBDC" })
	public String ricercaBeneinBDC(BeneForm beneForm, BindingResult result ,HttpServletRequest request, ModelMap modelMap) {
		ProfiloForm profilo = (ProfiloForm) request.getSession().getAttribute("profilo");
		long idProcedimento = ((ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
		Long idTipoUfficio = profilo.getIdTipoUfficio();
		modelMap.addAttribute("visualizzaPulsante", "RicercaBDC");
		modelMap.addAttribute("beneForm", beneForm);
		modelMap.addAttribute("beneFormIndietro", beneForm);
		/*  GC/PQ 28/08/2024: TT#202407020117
		 * Verifico il campo numregistrazione in base al tipo di bene
		 * Per beneFinanziario è diventato alfanumerico; negli altri casi è numerico
		 */
		if (chkNumReg(beneForm, result, request).hasErrors()) {
			// GC/PQ 30/08/2024: TT#202407020117: in caso di errore torno allo status iniziale con il solo bottone Ricerca abilitato
			modelMap.addAttribute("visualizzaPulsante", null);
			super.removeFromSession("risultatiBDC");
			return "/procedimento/bene/aggiungiBene";
		}
		modelMap.addAttribute("risultatiBaseDati",
				beneService.beniRicercaCriteri(idUfficioUtente, idProcedimento, beneForm));
		if (beneForm.getTipologia().getId().equals("2") && beneForm.getDescOperFinanz() != null)
			beneForm.setDescrizioneEnte(beneForm.getDescOperFinanz().getDescrizione());
		try {
			// setta a null l'ID del bene in quanto la ricerca avviene sempre
			// per criteri che non riguardano l'identificativo
			beneForm.setIdBene(null);
			// memorizza in sessione i risultati della ricerca in modo da
			// paginare agevolmente in memoria centrale
			super.saveIntoSession(bdcService.ricercaBeniBDC(beneForm, idUfficioUtente.longValue(),
					idTipoUfficio, idProcedimento), "risultatiBDC", true);
		} catch (Exception e) {
			super.setMessage(Constants.MESSAGE, "webservice.ricerca.error", "Ricerca bene in BDC", false);
		}
		// Giacomo 11/07/2013 BreadCrumbs
		addBreadcrumbs("RicercaBDC", request);
		// super.setIndietroForm(beneForm);// memorizza il form con i valori
		// impostati per la gestione del tasto indietro
		return "/procedimento/bene/aggiungiBene";
	}

	@RequestMapping(value = "/procedimento/atto/noPaginazioneDecisorioBeni.htm", method = RequestMethod.POST)
	public String noPaginazioneDecisorioBeni(BeneSoggettoForm beneSoggettoForm, BindingResult result,
			HttpServletRequest request, ModelMap modelMap) {
		beneSoggettoForm.setPaginazione("N");
		return attoController.apriDecisiorioBeni(beneSoggettoForm, result, request, modelMap);
	}

	@RequestMapping(value = "/procedimento/atto/yesPaginazioneDecisorioBeni.htm", method = RequestMethod.POST)
	public String yesPaginazioneDecisorioBeni(BeneSoggettoForm beneSoggettoForm, BindingResult result,
			HttpServletRequest request, ModelMap modelMap) {
		beneSoggettoForm.setPaginazione("Y");
		return attoController.apriDecisiorioBeni(beneSoggettoForm, result, request, modelMap);
	}

	@RequestMapping(value = "/procedimento/atto/noPaginazioneDecisorioBeniIIGrado.htm", method = RequestMethod.POST)
	public String noPaginazioneDecisorioBeniIIGrado(BeneSoggettoForm beneSoggettoForm, BindingResult result,
			HttpServletRequest request, ModelMap modelMap) {
		beneSoggettoForm.setPaginazione("N");
		// System.out.println("---> no PaginazioneDecisorioBene" );
		return attoController.apriDecisiorioBeni(beneSoggettoForm, result, request, modelMap);
		// return attoController.apriDecisiorioBeniIGrado(beneSoggettoForm, result, request, modelMap);
	}

	@RequestMapping(value = "/procedimento/atto/yesPaginazioneDecisorioBeniIIGrado.htm", method = RequestMethod.POST)
	public String yesPaginazioneDecisorioBeniIIGRado(BeneSoggettoForm beneSoggettoForm, BindingResult result,
			HttpServletRequest request, ModelMap modelMap) {
		beneSoggettoForm.setPaginazione("Y");
		// System.out.println("---> no PaginazioneDecisorioBene" );
		return attoController.apriDecisiorioBeni(beneSoggettoForm, result, request, modelMap);
		// return attoController.apriDecisiorioBeniIIGrado(beneSoggettoForm, result, request, modelMap);

	}

	@RequestMapping(value = { "/procedimento/bene/inserimentoVeicolo.htm",
			"/procedimento/bene/inserimentoBeneImmateriale.htm", "/procedimento/bene/inserimentoAnimali.htm",
			"/procedimento/bene/inserimentoCollezione.htm", "/procedimento/bene/inserimentoAzienda.htm",
			"/procedimento/bene/inserimentoDenaro.htm", "/procedimento/bene/inserimentoBeneFinanziario.htm",
			"/procedimento/bene/inserimentoImmobile.htm",
			"/procedimento/bene/inserimentoAltriOggetti.htm" }, method = RequestMethod.POST, params = {
					"SalvaEAssocia" })
	public String salvaNuovoBene(@Valid BeneForm beneForm, BindingResult result, HttpServletRequest request,
			ModelMap modelMap) {

		/*  GC 28/08/2024: TT#202407020117
		 * Verifico il campo numregistrazione in base al tipo di bene
		 * Per beneFinanziario è diventato alfanumerico; negli altri casi è numerico
		 */
		if (chkNumReg(beneForm, result, request).hasErrors()) {
			return dispatchInserimento(beneForm, null);
		}
		
		//REQ-STM-019-02 controllo per i beni finanziari se il Numero registrazione e “Importo in euro”  è vuoto
		//vado a vedere se l'ente e l'iban sono vuoti e segnalo che almeno uno dei campi Importo e numero registrazione devono essere valorizzati
		//per i beni delle sotto-categorie diverse da Altro o Non-Definito 
		if ("4".equals(beneForm.getTipologia().getId()) && 
				(!"117".equals(beneForm.getSottocategoria().getId()) && 
				 !"99".equals(beneForm.getSottocategoria().getId())) ) {
			beneValidation.validateNumeroRegistrazioneImporto(beneForm, result, request);
			if (result.hasErrors()) {
				return dispatchInserimento(beneForm, null);
			}
		}
		//REQ-STM-019-02 controllo per i beni mobili contante se il Numero registrazione e “Importo in euro”  è vuoto
		if ("6".equalsIgnoreCase(beneForm.getCategoria().getId()) && 
				"35".equalsIgnoreCase(beneForm.getSottocategoria().getId())) {
			    
			beneValidation.validateNumeroRegistrazioneImporto(beneForm, result, request);
			if (result.hasErrors()) {
				return dispatchInserimento(beneForm, null);
			}

		}
		
		
		// SITMP-204 Adeguamento applicativo dei campi che utilizzano T_BENE_FIN.DESC_REGISTRAZIONE - aggiunto controllo lato server sul numero polizza che sia numerico
		beneValidation.validateNumeroPolizza(beneForm, result, request);
		if (result.hasErrors()) {
			return dispatchInserimento(beneForm, null);
		}

		if (beneForm.getIdBene() != null) {
			beneValidation.validateInserimentoBeneNonDuplicato(beneForm, result, request);
			if (result.hasErrors()) {
				return visualizzaInserisciBene(beneForm, modelMap, request);
			}
		}
		beneForm.setIdBene(null);

		// *** VALIDAZIONE BENE FINANZIARIO(TIPOLOGIA=4), BENE
		// DENARO(TIPOLOGIA=2, CATEGORIA=6) (COMPRESO CONTANTE) ***
		if ((beneForm.getTipologia().getId().equals("2") && beneForm.getCategoria().getId().equals("6"))
				|| (beneForm.getTipologia().getId().equals("4"))) {
			//F.C. REQ-STM-019-06 nel caso di:
			//CONTO CORRENTE BANCARIO
			//CONTO CORRENTE POSTALE
			//LIBRETTO BANCARIO
			//LIBRETTO POSTALE
			//vanno fatti i controlli su obbligatorieta' ABI, CAB, IBAN ecc.
			if (
					!"39".equalsIgnoreCase(beneForm.getSottocategoria().getId()) &&
					!"38".equalsIgnoreCase(beneForm.getSottocategoria().getId()) &&
					!"37".equalsIgnoreCase(beneForm.getSottocategoria().getId()) &&
					!"36".equalsIgnoreCase(beneForm.getSottocategoria().getId())
			) {
					//REQ-STM-019-02
					beneForm = beneValidation.checkEnteFittizio(beneForm,result);
			}
			// Valida presenza codici ABI, CAB e numero conto
			if (!SottoCategoriaUtils.isPolizza(beneForm.getSottocategoria().getId())) {
				String ritorno = beneValidation.validateInserimentoBeneFinanziario_Denaro(beneForm, result);
				if (result.hasErrors()) {
					if (!ritorno.equalsIgnoreCase("")) {
						// Se ho inserito un nuovo ABI non valido
						if (ritorno.equals("newAbiNotValid")) {
							beneForm.setAbi(null);
							beneForm.setDescOperFinanz(new ComboForm(null, null));
							beneForm.setDescrizioneEnte(null);
						} else
						// Se ho inserito un nuovo ABI valido
						if (ritorno.equals("newAbiValidDiffDescEnte")) {
							// Lascio in visualizzazione ABI e Descrizione Ente
							// Annullo gli altri campi
							beneForm.setAbi(beneForm.getCodiceAbi());
						} else
						// Se ho annullato la Descrizione Ente
						if (ritorno.equals("abiNotNulldescNull")) {
							// Lascio in visualizzazione l'ABI
							// Annullo gli altri campi
							beneForm.setAbi(null);
							beneForm.setDescrizioneEnte(null);
							beneForm.setDescOperFinanz(new ComboForm(null, null));
						} else
						// Se ho annullato l'ABI
						if (ritorno.equals("abiNullDescNotNull")) {
							// Lascio in visualizzazione la Descrizione
							// Ente
							// Svuoto gli altri campi
							beneForm.setAbi(null);
							beneForm.setCodiceAbi(null);
						}
						if (beneForm.getProvincia() != null)
							beneForm.getProvincia().setId("-1");
						beneForm.setProvinciaSel(null);
						beneForm.setDisabledProvincia(true);
						beneForm.setCodiceCab(new ComboForm(null, null));
						beneForm.setIndirizzoBeneFin(null);
					} else {
						if (beneForm.getCodiceAbi() != null) {
							String codiceAbi = beneForm.getCodiceAbi();
							List<ComboForm> province = commonService.listaProvinceAbi(codiceAbi);
							modelMap.addAttribute("provinceAbi", province);
							beneForm.setDisabledProvincia(false);
						}
						if (beneForm.getProvincia() != null) {
							if (beneForm.getProvincia().getId() != null
									&& !beneForm.getProvincia().getId().equals("-1")) {
								List<ComboForm> allCab = commonService.listaCodiciCab(beneForm.getCodiceAbi(),
										beneForm.getProvincia().getId());
								modelMap.addAttribute("codiciCab", allCab);
							}
						}
					}
					modelMap.addAttribute("beneForm", beneForm);
					if (result.hasErrors()) {
						// P.Q. Inizio MEV 2021/19 Operatore Economico Estero allora i campi vanno valorizzati in automatico
						checkOperatoreEconomicoEstero(beneForm, modelMap);
						// P.Q. Fine MEV 2021/19 Operatore Economico Estero allora i campi vanno valorizzati in automatico
						return dispatchInserimento(beneForm, null);
					}
				}
			} else {
			
				//REQ-STM-019-02
				beneForm = beneValidation.checkEnteFittizio(beneForm,result);
				
				//REQ-STM-019-02	

				// validazione per polizze
				beneValidation.validateInserimentoBeneFinanziario_Polizza(beneForm, result);
				if (result.hasErrors()) {
					return dispatchInserimento(beneForm, null);
				}
			}

		} // *** VALIDAZIONE BENE FINANZIARIO, BENE DENARO (COMPRESO CONTANTE)
			// ***
		else {
			modelMap.addAttribute("beneForm", beneForm);
			beneValidation.validateInserisciBene(beneForm, result);
			// Giacomo 27/058/2013 Spostato nel validator nel metodo
			// validateInserisciBene
			// Validazione Bene Immobile
			// if(beneForm.getTipologia().getId().equals("1")){
			// beneValidation.validateBeneImmobile(beneForm, result);
			// }
			if (result.hasErrors()) {
				return dispatchInserimento(beneForm, null);
			}
		}
		// long idProcedimento = ((ProcedimentoForm)
		// (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		// Integer idUfficioUtente = (Integer)
		// request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		// Integer codUtente = (Integer)
		// request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);

		long idProcedimento = recuperaIdProcedimento();
		Integer idUfficioUtente = recuperaIdUfficioUtente();
		String codUtente = recuperaIdCodiceUtente();

		long idBeneInserito = beneService.inserisciBene(idUfficioUtente, codUtente, idProcedimento, beneForm);
		if (idBeneInserito > 0) {
			List<Message> customMessageList = new ArrayList<Message>();
			super.addMessageIntoList(customMessageList, Constants.SUCCESS_MESSAGE, "success.operation",
					"INSERIMENTO");
			super.addMessageIntoList(customMessageList, Constants.SUCCESS_MESSAGE,
					"bene.inserimento.corretto", String.valueOf(idBeneInserito));
			super.setMessagesSuccess(customMessageList, true);
		}
		// se provengo dalla maschera dell'atto
		if ((request.getSession().getAttribute(Constants.ATTO) != null)
				&& (((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto() > 0l)) {
			return "redirect:/procedimento/bene/beniCollegatiAtto.htm";
		}
		return "redirect:/procedimento/bene/beniCollegati.htm";
	}

	@RequestMapping(value = "/procedimento/bene/gestioneBeniDelProcedimento.htm", method = RequestMethod.POST, params = {
			"TitoliDiDiritto" })
	public String titoliDiDiritto(BeneForm beneForm, BindingResult result, HttpServletRequest request,
			ModelMap modelMap) {

		// funzione per prelevare i checkbox paginati
		chooseCheckBoxPaged(beneForm, request);

		beneValidation.validateSceltaSingolaCheckbox(beneForm, result, request);
		if (result.hasErrors()) {
			return visualizzaRicercaDelBene(beneForm, request, modelMap);
		}

		// Giacomo 11/07/2013 Breadcrumbs
		addBreadcrumbs("TitoliDiDiritto", request);

		return titoliDiDirittoUnchecked(beneForm, request, modelMap);
	}

	@RequestMapping(value = "/procedimento/bene/associaTitoloDiInteresse.htm", method = RequestMethod.POST, params = {
			"Associa" })
	public String titoliDiDirittoAssocia(BeneForm beneForm, BindingResult result, HttpServletRequest request,
			ModelMap modelMap) {

		beneValidation.validateRadioButtonSoggetto(beneForm, result, request);
		if (result.hasErrors()) {
			resettaListaCheckboxBeneForm(beneForm, request);
			return titoliDiDirittoUnchecked(beneForm, request, modelMap);
		}

		// Giacomo 11/07/2013 Breadcrumbs
		addBreadcrumbs("POST", "/procedimento/bene/gestioneBeniDelAtto.htm", "TitoliDiDiritto")
				.addParameter("listaIdCheckbox", String.valueOf(beneForm.getIdBene()));
		addBreadcrumbs("Associa", request);

		return titoliDiDirittoAssociaUnchecked(beneForm, request, modelMap);
	}

	@RequestMapping(value = "/procedimento/bene/confermaAssociazioneTitoloDiInteresse.htm", method = RequestMethod.POST, params = {
			"Conferma" })
	public String titoliDiDirittoAssociaConferma(@Valid BeneSoggettoForm beneSoggettoForm,
			BindingResult result, HttpServletRequest request, ModelMap modelMap) {

		BeneForm beneForm = (BeneForm) request.getSession().getAttribute("beneScelto");
		AttoForm attoForm = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
		beneSoggettoForm.getBene().setIdBene(beneForm.getIdBene());
		List<Long> lista = new ArrayList<Long>();
		lista.add(beneForm.getIdBene());
		beneSoggettoForm.getBene().setListaIdCheckbox(lista);
		ProcedimentoForm proc = (ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO));
		long idProcedimento = proc.getIdProcedimento();
		String tipoFasciciolo = proc.getTipoFascicolo().getId();
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
		long idPersona = beneForm.getIdentificativoSoggettoProcedimento();
		SoggettoForm soggetto = procedimentoService.getPersonaById(idPersona, idUfficioUtente);
		// soggetto.setTipologiaPrevenzione(beneSoggettoForm.getSoggetto().getTipologiaPrevenzione());
		MisuraForm misura = new MisuraForm();
		// inserito per il decreto di I grado e di II grado che non ha misura e normativa
		String idTipoMisura = "";
		if ((beneSoggettoForm.getSoggetto() != null)
				&& (beneSoggettoForm.getSoggetto().getMisura() != null)) {
			misura.setTipoMisura(beneSoggettoForm.getSoggetto().getMisura().getTipoMisura());
			idTipoMisura = beneSoggettoForm.getSoggetto().getMisura().getTipoMisura().getId();
		}
		soggetto.setIdSoggetto(idPersona);
		soggetto.setMisura(misura);
		// inserito per il decreto di I grado e di II grado che non ha misura e normativa
		if ((beneSoggettoForm.getSoggetto() != null)
				&& (beneSoggettoForm.getSoggetto().getNormativa() != null))
			soggetto.setNormativa(beneSoggettoForm.getSoggetto().getNormativa());
		beneSoggettoForm.setSoggetto(soggetto);

		long idAtto = 0l;
		if ((request.getSession().getAttribute(Constants.ATTO) != null)
				&& (((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto() > idAtto)) {
			idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
		}

		int idTipoInteresse = beneSoggettoForm.getIdTipoInteresse();
		if (idTipoMisura != null && idTipoMisura.equalsIgnoreCase(Constants.TipoMisura.REVOCA_SEQUESTRO)) {
			beneValidation.validateRevocaSequestroPossibile(result, beneForm, idProcedimento, idUfficioUtente,
					idPersona, idTipoInteresse, beneSoggettoForm);

			if (result.hasErrors()) {
				return titoliDiDirittoAssociaReload(beneSoggettoForm, request, modelMap, beneForm,
						idProcedimento, idUfficioUtente, soggetto);
			}

		}

		beneValidation.validateCampiObbligatoriInserimentoOModificaTitoloDiDiritto(beneSoggettoForm, result,
				request, attoForm);
		if (result.hasErrors()) {
			return titoliDiDirittoAssociaReload(beneSoggettoForm, request, modelMap, beneForm, idProcedimento,
					idUfficioUtente, soggetto);
		}
		beneValidation.validateQuotaOFrazione(beneSoggettoForm, result);
		if (result.hasErrors()) {
			return titoliDiDirittoAssociaReload(beneSoggettoForm, request, modelMap, beneForm, idProcedimento,
					idUfficioUtente, soggetto);
		}
		beneValidation.validateFrazioneCoerente(beneSoggettoForm, result, request);
		if (!result.hasErrors()) {
			beneValidation.validateQuotaInteresseCoerente(beneSoggettoForm, result, request);
			if (!result.hasErrors()) {
				beneValidation.validateInteresseNonDuplicato(beneForm, beneSoggettoForm, idProcedimento,
						idUfficioUtente, result, request, idAtto);
			}
		}

		// se provengo dalla maschera dell'atto decreto di sequestro
		Boolean daProposta = true;

		if (attoForm != null
				&& !(attoForm.getTipoAtto().getId().equalsIgnoreCase((Constants.TipoAtto.TIPO_ATTO) + ""))) {
			if (!result.hasErrors()) {
				beneValidation.validateInteresseTotaleCoerente(beneForm, beneSoggettoForm, idProcedimento,
						idUfficioUtente, result, request, idAtto, attoForm.getTipoAtto().getId());
			}
			if (!result.hasErrors()) {
				daProposta = false;
			}

		} else if (attoForm == null || attoForm != null
				&& (attoForm.getTipoAtto().getId().equalsIgnoreCase((Constants.TipoAtto.TIPO_ATTO) + ""))) {
			if (!result.hasErrors()) {
				String tipoAtto = null;
				if (attoForm != null) {
					tipoAtto = attoForm.getTipoAtto().getId();
				}
				beneValidation.validateInteresseTotaleCoerente(beneForm, beneSoggettoForm, idProcedimento,
						idUfficioUtente, result, request, idAtto, tipoAtto);
			}
		}

		if (result.hasErrors()) {
			return titoliDiDirittoAssociaReload(beneSoggettoForm, request, modelMap, beneForm, idProcedimento,
					idUfficioUtente, soggetto);
		}

		String codUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);
		beneSoggettoForm.getBene().setListaIdCheckbox(new AutoPopulatingList<Long>(Long.class));
		beneSoggettoForm.getBene().getListaIdCheckbox().add(beneForm.getIdBene());
		// gennaro non faccio il controllo PL/SQL se categoria atto è 4 "decisorio di 1 grado" o 22 "decisorio
		// de 2 grado"
		String catAtto = "";
		if ((attoForm != null) && (attoForm.getCategoriaAtto().getId() != null))
			catAtto = attoForm.getCategoriaAtto().getId();
		// fine gennaro

		KeyMessageDto messaggioRitorno = beneService.associaTitoloDiDirittoAlBene(tipoFasciciolo,
				beneSoggettoForm, idProcedimento, idUfficioUtente, idAtto, codUtente, daProposta,
				Constants.TipiOperazioneCRUD.CREATE, super.printMethodName(), catAtto);
		if (messaggioRitorno.isError()) {
			super.setMessage(Constants.MESSAGE, "messaggio.gestito.db", messaggioRitorno.getMessage(), false);
			result.reject(Constants.MESSAGE);
			return titoliDiDirittoAssociaReload(beneSoggettoForm, request, modelMap, beneForm, idProcedimento,
					idUfficioUtente, soggetto);
		}

		addBreadcrumbs("POST", "/procedimento/bene/gestioneBeniDelProcedimento.htm", "TitoliDiDiritto")
				.addParameter("listaIdCheckbox", String.valueOf(beneForm.getIdBene()));

		return titoliDiDirittoUnchecked(beneSoggettoForm.getBene(), request, modelMap);
	}

	@RequestMapping(value = {
			"/procedimento/bene/confermaAssociazioneTitoloDiInteresse.htm" }, method = RequestMethod.POST)
	public String titoliDiDirittoAssociaRefresh(@Valid BeneSoggettoForm beneSoggettoForm,
			BindingResult result, HttpServletRequest request, ModelMap modelMap) {

		BeneForm beneForm = beneSoggettoForm.getBene();
		BeneSoggettoForm beneSoggetto = new BeneSoggettoForm();
		long idProcedimento = ((ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
		long idAtto = 0l;
		if ((request.getSession().getAttribute(Constants.ATTO) != null)
				&& (((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto() > idAtto)) {
			idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
		}
		long idPersona = beneForm.getIdentificativoSoggettoProcedimento();
		if (result.hasErrors()) {
			modelMap.addAttribute("soggettiDelBene", beneService.listaSoggettiBeniProc(idProcedimento,
					idUfficioUtente, beneForm.getIdBene(), idAtto));
			modelMap.addAttribute("beneSoggetto", beneSoggettoForm);
			return "/procedimento/bene/titoliDiInteresse-Associa";
		}
		SoggettoForm soggetto = beneSoggettoForm.getSoggetto();
		SoggettoForm soggettoNuovo = procedimentoService.getPersonaById(idPersona, idUfficioUtente);
		soggettoNuovo.setTipologiaPrevenzione(soggetto.getTipologiaPrevenzione());
		MisuraForm misura = new MisuraForm();
		misura.setTipoMisura(soggetto.getMisura().getTipoMisura());
		soggettoNuovo.setMisura(misura);
		soggettoNuovo.setNormativa(soggetto.getNormativa());
		beneSoggetto.setSoggetto(soggettoNuovo);
		beneSoggetto.setBene(beneForm);
		beneSoggetto.setIdTipoInteresse(beneSoggettoForm.getIdTipoInteresse());
		beneSoggetto.setQuotaInteresse(beneSoggettoForm.getQuotaInteresse());
		beneSoggetto.setNumeratoreFrazioneQuota(beneSoggettoForm.getNumeratoreFrazioneQuota());
		beneSoggetto.setDenominatoreFrazioneQuota(beneSoggettoForm.getDenominatoreFrazioneQuota());
		List<BeneSoggettoForm> beniSoggetti = beneService.listaSoggettiBeniProc(idProcedimento,
				idUfficioUtente, beneForm.getIdBene(), idAtto);
		modelMap.addAttribute("beneSoggetto", beneSoggetto);
		modelMap.addAttribute("soggettiDelBene", beniSoggetti);
		return "/procedimento/bene/titoliDiInteresse-Associa";
	}

	@RequestMapping(value = "/procedimento/bene/gestioneBeniDelAtto.htm", method = RequestMethod.POST, params = {
			"TitoliDiDiritto" })
	public String titoliDiDirittoAtto(BeneForm beneForm, BindingResult result, HttpServletRequest request,
			ModelMap modelMap) {

		beneValidation.validateSceltaSingolaCheckbox(beneForm, result, request);
		if (result.hasErrors()) {
			return visualizzaRicercaDelBeneAtto(beneForm, request, modelMap);
		}
		return titoliDiDirittoUnchecked(beneForm, request, modelMap);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/procedimento/bene/gestioneBeniDelProcedimento.htm", method = RequestMethod.POST, params = {
			"CopiaTitoliDiDiritto" })
	public String titoliDiDirittoCopia(BeneForm beneForm, BindingResult result, HttpServletRequest request,
			ModelMap modelMap) {

		// funzione per prelevare i checkbox paginati
		chooseCheckBoxPaged(beneForm, request);

		beneValidation.validateSceltaCheckbox(beneForm, result, request);
		if (result.hasErrors()) {
			return visualizzaRicercaDelBene(beneForm, request, modelMap);
		}
		TitoliDiDirittoCopia(beneForm, result, request, modelMap);
		if (result.hasErrors()) {
			return visualizzaRicercaDelBene(beneForm, request, modelMap);
		}

		if (beneForm.isVisualizzaFiltro()) {
			beneForm.setIdBene(null);
			beneForm.setTipologia(null);
			beneForm.setCategoria(null);
			beneForm.setSottocategoria(null);
			beneForm.setVisualizzaFiltro(false);
		}

		List<BeneForm> beniAssociati = new ArrayList<BeneForm>();
		List<BeneForm> beniAssociatiFinali = new ArrayList<BeneForm>();
		long idAtto = 0l;
		long idProcedimento = ((ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
		beniAssociati = beneService.listaBeniPerProcedimento(0, idUfficioUtente, idProcedimento, beneForm);

		if (beneForm.getListaIdCheckbox() != null && beneForm.getListaIdCheckbox().size() != 0) {
			BeneForm beneOut = new BeneForm();
			Iterator itx = beneForm.getListaIdCheckbox().iterator();
			while (itx.hasNext()) {
				Long idBene = (Long) itx.next();
				beneOut = beneService.beneById(idUfficioUtente, idBene, idProcedimento);
				beniAssociati.remove(beneOut);
			}
			for (int i = 0; i < beniAssociati.size(); i++) {
				List<BeneSoggettoForm> beniSoggetti = beneService.listaSoggettiBeniProc(idProcedimento,
						idUfficioUtente, beniAssociati.get(i).getIdBene(), idAtto);
				if (!((beniSoggetti == null) || beniSoggetti.isEmpty())) {
					beniAssociatiFinali.add(beniAssociati.get(i));
				}
			}
			beniAssociati = beniAssociatiFinali;
		}

		modelMap.addAttribute("risultatiRicerca", beniAssociati);

		// Giacomo 11/07/2013 BreadCrumbs
		addBreadcrumbs("CopiaTitoliDiDiritto", request);

		return "/procedimento/bene/copiaTitoliDiDiritto";
	}

	@RequestMapping(value = "/procedimento/bene/gestioneBeniDelAtto.htm", method = RequestMethod.POST, params = {
			"CopiaTitoliDiDiritto" })
	public String titoliDiDirittoCopiaAtto(BeneForm beneForm, BindingResult result,
			HttpServletRequest request, ModelMap modelMap) {

		beneValidation.validateSceltaCheckbox(beneForm, result, request);
		if (result.hasErrors()) {
			return visualizzaRicercaDelBeneAtto(beneForm, request, modelMap);
		}
		TitoliDiDirittoCopia(beneForm, result, request, modelMap);
		if (result.hasErrors()) {
			return visualizzaRicercaDelBeneAtto(beneForm, request, modelMap);
		}
		return "/procedimento/bene/copiaTitoliDiDirittoAtto";
	}

	@RequestMapping(value = "/procedimento/bene/gestioneSoggettiDelBene.htm", method = RequestMethod.POST, params = {
			"Elimina" })
	public String titoliDiDirittoElimina(BeneForm beneForm, BindingResult result, HttpServletRequest request,
			ModelMap modelMap) {

		beneValidation.validateRadioButtonTitoliDiritto(beneForm, result, request);
		if (result.hasErrors()) {
			resettaListaCheckboxBeneForm(beneForm, request);
			return titoliDiDirittoUnchecked(beneForm, request, modelMap);
		}
		long idProcedimento = ((ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
		BeneForm bene = (BeneForm) request.getSession().getAttribute("beneScelto");
		AttoForm attoForm = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
		// se provengo dalla maschera dell'atto decreto di sequestro
		long idAtto = 0l;
		if ((request.getSession().getAttribute(Constants.ATTO) != null)
				&& (((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto() > idAtto)) {
			idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
		}
		List<BeneSoggettoForm> beniSoggetti = beneService.listaSoggettiBeniProc(idProcedimento,
				idUfficioUtente, beneForm.getIdBene(), idAtto);
		impostaBeneDelBeneSoggetto(request, beniSoggetti);
		String identificativoSoggettoBeneProcedimentoTitoloDiritto = beneForm
				.getIdentificativoSoggettoBeneProcedimentoTitoloDiritto();
		String[] identPar = StringUtils.split(identificativoSoggettoBeneProcedimentoTitoloDiritto,
				Constants.CARATTERE_DIVISORE_CONC_CAMPI);
		long idPersona = Long.parseLong(identPar[0]);
		int idTipoInteresse = Integer.parseInt(identPar[1]);
		long idBene = bene.getIdBene();
		SoggettoForm soggetto = procedimentoService.getPersonaById(idPersona, idUfficioUtente);
		BeneSoggettoForm beneSoggettoSelezionato = null;
		for (BeneSoggettoForm tmp : beniSoggetti) {
			if (tmp.getBene().equals(bene) && tmp.getSoggetto().equals(soggetto)
					&& tmp.getIdTipoInteresse() == idTipoInteresse) {
				beneSoggettoSelezionato = tmp;
			}
		}
		// int idTipoInteresse = beneSoggettoSelezionato.getIdTipoInteresse();
		// se non provengo dalla maschera dell'atto decreto di sequestro
		if (request.getSession().getAttribute(Constants.ATTO) == null) {
			beneValidation.validatePresenzaComeMisuraPatrimoniale(idPersona, idProcedimento, idUfficioUtente,
					idBene, idTipoInteresse, result, request);
			if (result.hasErrors()) {
				resettaListaCheckboxBeneForm(beneForm, request);
				return titoliDiDirittoUnchecked(beneForm, request, modelMap);
			}
		}

		// se provengo dalla maschera dell'atto decreto di sequestro
		Boolean daProposta = true;

		if (attoForm != null
				&& !(attoForm.getTipoAtto().getId().equalsIgnoreCase((Constants.TipoAtto.TIPO_ATTO) + ""))) {
			daProposta = false;

		}

		// Carico le percentuali o frazioni per T_REL_PROC_BENE_TIT
		double quotaSequestro = 0;
		double numeratoreFrazioneSequestro = 0;
		double denominatoreFrazioneSequestro = 0;

		if (beneSoggettoSelezionato.getQuotaInteresse() != null) {
			quotaSequestro = beneSoggettoSelezionato.getQuotaInteresse().doubleValue();
		}
		if (beneSoggettoSelezionato.getNumeratoreFrazioneQuota() != null) {
			numeratoreFrazioneSequestro = beneSoggettoSelezionato.getNumeratoreFrazioneQuota().doubleValue();
		}
		if (beneSoggettoSelezionato.getDenominatoreFrazioneQuota() != null) {
			denominatoreFrazioneSequestro = beneSoggettoSelezionato.getDenominatoreFrazioneQuota()
					.doubleValue();
		}

		// se provengo dalla maschera dell'atto decreto di sequestro
		String codUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);
		if ((request.getSession().getAttribute(Constants.ATTO) != null)
				&& (((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto() > 0l)) {
			beneService.modificaRelBeneTit(beneSoggettoSelezionato, idProcedimento, idAtto, idUfficioUtente,
					codUtente, daProposta, quotaSequestro, numeratoreFrazioneSequestro,
					denominatoreFrazioneSequestro, Constants.TipiOperazioneCRUD.DELETE,
					super.printMethodName());
			attoService.eliminaMisBeneAtto(idProcedimento, idAtto, idBene, idUfficioUtente, idPersona,
					idTipoInteresse);
		} else {
			beneService.modificaRelBeneTit(beneSoggettoSelezionato, idProcedimento, idAtto, idUfficioUtente,
					codUtente, daProposta, quotaSequestro, numeratoreFrazioneSequestro,
					denominatoreFrazioneSequestro, Constants.TipiOperazioneCRUD.DELETE,
					super.printMethodName());
			beneService.eliminaSoggettoComeTitoloDiInteresse(idPersona, idProcedimento, idUfficioUtente,
					idBene, idTipoInteresse, idAtto, codUtente);
		}
		resettaListaCheckboxBeneForm(beneForm, request);
		return titoliDiDiritto(beneForm, result, request, modelMap);
	}

	@RequestMapping(value = "/procedimento/bene/gestioneSoggettiDelBene.htm", method = RequestMethod.POST, params = {
			"Modifica" })
	public String titoliDiDirittoModifica(BeneForm beneForm, BindingResult result, HttpServletRequest request,
			ModelMap modelMap, boolean validationDisabled) {

		if (!validationDisabled) {
			beneValidation.validateRadioButtonTitoliDiritto(beneForm, result, request);
			if (result.hasErrors()) {
				resettaListaCheckboxBeneForm(beneForm, request);
				return titoliDiDirittoUnchecked(beneForm, request, modelMap);
			}
		}
		BeneSoggettoForm beneSoggettoForm = new BeneSoggettoForm();
		long idProcedimento = ((ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
		String identificativoSoggettoBeneProcedimentoTitoloDiritto = beneForm
				.getIdentificativoSoggettoBeneProcedimentoTitoloDiritto();
		String[] identPar = StringUtils.split(identificativoSoggettoBeneProcedimentoTitoloDiritto,
				Constants.CARATTERE_DIVISORE_CONC_CAMPI);
		long idPersona = Long.parseLong(identPar[0]);
		long idTipoInteresse = Long.parseLong(identPar[1]);
		BeneForm beneScelto = (BeneForm) request.getSession().getAttribute("beneScelto");
		beneScelto.setIdentificativoSoggettoBeneProcedimentoTitoloDiritto(
				idPersona + Constants.CARATTERE_DIVISORE_CONC_CAMPI + idTipoInteresse);
		beneForm.setDescrizioneBene(beneScelto.getDescrizioneBene());
		beneForm.setIdBene(beneScelto.getIdBene());
		beneForm.setIdentificativoSoggettoProcedimento(idPersona);
		beneSoggettoForm.setBene(beneForm);
		// se provengo dalla maschera dell'atto decreto di sequestro
		long idAtto = 0l;
		if ((request.getSession().getAttribute(Constants.ATTO) != null)
				&& (((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto() > idAtto)) {
			idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
		} else {
			idAtto = beneService.getIdAttoByUfficioEProcedimento(idUfficioUtente, idProcedimento);
		}
		// long idTipoInteresse = beneService.getTipoInteressePerSoggettoInteressato(idProcedimento, idAtto,
		// idUfficioUtente, beneForm.getIdBene(), idPersona);

		BeneSoggettoForm formConInfo = beneService.getBeneSoggettoFormConInformazioniTitoliDiDiritto(
				beneSoggettoForm, beneForm.getIdBene(), idPersona, idUfficioUtente, idProcedimento, idAtto,
				idTipoInteresse);
		if (validationDisabled) {
			BeneSoggettoForm precedente = (BeneSoggettoForm) request.getSession()
					.getAttribute("beneSoggettoTmp");
			if (precedente != null) {
				formConInfo = precedente;
			}
		}
		request.getSession().removeAttribute("beneSoggettoTmp");
		List<ComboForm> normative = commonService
				.getNormativaByTipoPrevenzioneAndTipoMisura(formConInfo.getSoggetto());
		request.setAttribute("normativeRefresh", normative);
		request.getSession().setAttribute("beneScelto", beneScelto);
		List<BeneSoggettoForm> beniSoggetti = beneService.listaSoggettiBeniProc(idProcedimento,
				idUfficioUtente, beneForm.getIdBene(), idAtto);
		modelMap.addAttribute("soggettiDelBene", beniSoggetti);
		modelMap.addAttribute("beneSoggetto", formConInfo);
		addBreadcrumbs("POST", "/procedimento/bene/gestioneSoggettiDelBene.htm", "Modifica");
		return "/procedimento/bene/titoliDiInteresse-Modifica";
	}

	@RequestMapping(value = "/procedimento/bene/confermaModificaTitoloDiInteresse.htm", method = RequestMethod.POST, params = {
			"Conferma" })
	public String titoliDiDirittoModificaConferma(@Valid BeneSoggettoForm beneSoggettoForm,
			BindingResult result, HttpServletRequest request, ModelMap modelMap, boolean validationDisabled) {

		BeneForm beneForm = (BeneForm) request.getSession().getAttribute("beneScelto");
		AttoForm attoForm = (AttoForm) request.getSession().getAttribute(Constants.ATTO);

		beneSoggettoForm.getBene().setIdBene(beneForm.getIdBene());
		List<Long> lista = new ArrayList<Long>();
		lista.add(beneForm.getIdBene());
		beneSoggettoForm.getBene().setListaIdCheckbox(lista);
		ProcedimentoForm proc = (ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO));
		long idProcedimento = proc.getIdProcedimento();
		String tipoFasciciolo = proc.getTipoFascicolo().getId();
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
		String identificativoSoggettoBeneProcedimentoTitoloDiritto = beneForm
				.getIdentificativoSoggettoBeneProcedimentoTitoloDiritto();
		String[] identPar = StringUtils.split(identificativoSoggettoBeneProcedimentoTitoloDiritto,
				Constants.CARATTERE_DIVISORE_CONC_CAMPI);
		long idPersona = Long.parseLong(identPar[0]);
		SoggettoForm soggetto = procedimentoService.getPersonaById(idPersona, idUfficioUtente);
		// soggetto.setTipologiaPrevenzione(beneSoggettoForm.getSoggetto().getTipologiaPrevenzione());
		MisuraForm misura = new MisuraForm();
		// inserito per il decreto di I grado e di II grado che non ha misura e normativa
		if ((beneSoggettoForm.getSoggetto() != null) && (beneSoggettoForm.getSoggetto().getMisura() != null))
			misura.setTipoMisura(beneSoggettoForm.getSoggetto().getMisura().getTipoMisura());
		soggetto.setIdSoggetto(idPersona);
		soggetto.setMisura(misura);
		// inserito per il decreto di I grado e di II grado che non ha misura e normativa
		if ((beneSoggettoForm.getSoggetto() != null)
				&& (beneSoggettoForm.getSoggetto().getNormativa() != null))
			soggetto.setNormativa(beneSoggettoForm.getSoggetto().getNormativa());
		beneSoggettoForm.setSoggetto(soggetto);

		if (beneSoggettoForm.getIdTipoInteresse() > 0) {
			TTpTipoInteresse tTipoInteresse = beneService
					.getInteresseById(beneSoggettoForm.getIdTipoInteresse());
			beneSoggettoForm.setDescrizioneTipoInteresse(tTipoInteresse.getDescTipoInt());
		}

		beneValidation.validateCampiObbligatoriInserimentoOModificaTitoloDiDiritto(beneSoggettoForm, result,
				request, attoForm);
		if (result.hasErrors()) {
			beneForm.setIdentificativoSoggettoProcedimento(
					beneSoggettoForm.getBene().getIdentificativoSoggettoProcedimento());
			request.getSession().setAttribute("beneSoggettoTmp", beneSoggettoForm);
			return titoliDiDirittoModifica(beneForm, result, request, modelMap, true);
		}
		beneValidation.validateFrazioneCoerente(beneSoggettoForm, result, request);
		if (!result.hasErrors()) {

			beneValidation.validateQuotaInteresseCoerente(beneSoggettoForm, result, request);
			if (!result.hasErrors()) {
				beneValidation.validateConfermaModificaTitoloDiDiritto(beneSoggettoForm, result, request,
						attoForm);
				if (result.hasErrors()) {
					beneForm.setIdentificativoSoggettoProcedimento(
							beneSoggettoForm.getBene().getIdentificativoSoggettoProcedimento());
					// return titoliDiDirittoAssociaUnchecked(beneForm,
					// request, modelMap);
					return titoliDiDirittoModifica(beneForm, result, request, modelMap, true);
				}
			}
		}
		// se provengo dalla maschera dell'atto decreto di sequestro
		Boolean daProposta = true;

		if (attoForm != null
				&& !(attoForm.getTipoAtto().getId().equalsIgnoreCase((Constants.TipoAtto.TIPO_ATTO) + ""))) {

			if (!result.hasErrors()) {
				beneValidation.validateInteresseTotaleCoerentePerModifica(beneForm, beneSoggettoForm,
						idProcedimento, idUfficioUtente, result, request, attoForm.getTipoAtto().getId());
			}
			if (!result.hasErrors()) {

				daProposta = false;

				TRelBeneAtto relBeneAtto = beneService.getRelazioneBeneProcedimentoById(beneSoggettoForm,
						idProcedimento, idUfficioUtente, attoForm.getCodAtto());
				if (relBeneAtto != null) {
					if (relBeneAtto.getNumeQuota() != null) {
						beneSoggettoForm
								.setQuotaSequestroOriginaria(relBeneAtto.getNumeQuota().doubleValue());
					}
					if (relBeneAtto.getNumeFrazioneNum() != null) {
						beneSoggettoForm.setQuotaFrazioneNumeratoreSeqOriginaria(
								relBeneAtto.getNumeFrazioneNum().intValue());
						beneSoggettoForm.setQuotaFrazioneDenominatoreSeqOriginaria(
								relBeneAtto.getNumeFrazioneDen().intValue());
					}

				}

			}
		} else if (attoForm == null || attoForm != null
				&& (attoForm.getTipoAtto().getId().equalsIgnoreCase((Constants.TipoAtto.TIPO_ATTO) + ""))) {
			if (!result.hasErrors()) {
				String tipoAtto = null;
				if (attoForm != null) {
					tipoAtto = attoForm.getTipoAtto().getId();
				}
				beneValidation.validateInteresseTotaleCoerentePerModifica(beneForm, beneSoggettoForm,
						idProcedimento, idUfficioUtente, result, request, tipoAtto);
			}
		}
		long idAtto = 0l;
		if ((request.getSession().getAttribute(Constants.ATTO) != null)
				&& (((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto() > idAtto)) {
			idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
		}

		beneSoggettoForm.getBene().setListaIdCheckbox(new AutoPopulatingList<Long>(Long.class));
		beneSoggettoForm.getBene().getListaIdCheckbox().add(beneForm.getIdBene());
		if (result.hasErrors()) {
			request.getSession().setAttribute("beneSoggettoTmp", beneSoggettoForm);
			return titoliDiDirittoModifica(beneForm, result, request, modelMap, true);
		}
		if (!result.hasErrors()) {
			String codUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);
			// modifica gennaro categoria atto che non fa il controllo per decisorio di I grado e decisorio di
			// II grado
			String catAtto = "";
			if ((attoForm != null) && (attoForm.getCategoriaAtto().getId() != null))
				catAtto = attoForm.getCategoriaAtto().getId();
			// fine gennaro
			KeyMessageDto messaggioRitorno = beneService.associaTitoloDiDirittoAlBene(tipoFasciciolo,
					beneSoggettoForm, idProcedimento, idUfficioUtente, idAtto, codUtente, daProposta,
					Constants.TipiOperazioneCRUD.UPDATE, super.printMethodName(), catAtto);
			if (messaggioRitorno.isError()) {
				super.setMessage(Constants.MESSAGE, "messaggio.gestito.db", messaggioRitorno.getMessage(),
						false);
				result.reject(Constants.MESSAGE);
				beneForm.setIdentificativoSoggettoProcedimento(
						beneSoggettoForm.getBene().getIdentificativoSoggettoProcedimento());
				request.getSession().setAttribute("beneSoggettoTmp", beneSoggettoForm);
				return titoliDiDirittoModifica(beneForm, result, request, modelMap, true);
			}
		}
		removeLast("POST", "/procedimento/bene/gestioneSoggettiDelBene.htm", "Modifica", "remove",
				request.getSession());
		return titoliDiDirittoUnchecked(beneSoggettoForm.getBene(), request, modelMap);
	}

	@RequestMapping(value = {
			"/procedimento/bene/confermaModificaTitoloDiInteresse.htm" }, method = RequestMethod.POST)
	public String titoliDiDirittoModificaRefresh(@Valid BeneSoggettoForm beneSoggettoForm,
			BindingResult result, HttpServletRequest request, ModelMap modelMap) {

		BeneForm beneForm = beneSoggettoForm.getBene();
		long idProcedimento = ((ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
		long idPersona = beneForm.getIdentificativoSoggettoProcedimento();
		// se provengo dalla maschera dell'atto decreto di sequestro
		long idAtto = 0l;
		if ((request.getSession().getAttribute(Constants.ATTO) != null)
				&& (((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto() > idAtto)) {
			idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
		} else {
			idAtto = beneService.getIdAttoByUfficioEProcedimento(idUfficioUtente, idProcedimento);
		}
		if (result.hasErrors()) {
			modelMap.addAttribute("soggettiDelBene", beneService.listaSoggettiBeniProc(idProcedimento,
					idUfficioUtente, beneForm.getIdBene(), idAtto));
			modelMap.addAttribute("beneSoggetto", beneSoggettoForm);
			return "/procedimento/bene/titoliDiInteresse-Modifica";
		}

		int idTipoInteresse = beneService.getTipoInteressePerSoggettoInteressato(idProcedimento, idAtto,
				idUfficioUtente, beneForm.getIdBene(), idPersona);
		BeneSoggettoForm formConInfo = beneService.getBeneSoggettoFormConInformazioniTitoliDiDiritto(
				beneSoggettoForm, beneForm.getIdBene(), idPersona, idUfficioUtente, idProcedimento, idAtto,
				idTipoInteresse);
		formConInfo.setQuotaInteresse(beneSoggettoForm.getQuotaInteresse());
		formConInfo.setNumeratoreFrazioneQuota(beneSoggettoForm.getNumeratoreFrazioneQuota());
		formConInfo.setDenominatoreFrazioneQuota(beneSoggettoForm.getDenominatoreFrazioneQuota());

		String idTipoMisura = "";
		if (formConInfo.getSoggetto().getMisura() != null
				&& formConInfo.getSoggetto().getMisura().getTipoMisura() != null) {
			idTipoMisura = formConInfo.getSoggetto().getMisura().getTipoMisura().getId();
		}

		if (idTipoMisura != null && idTipoMisura.trim().length() > 0
				&& idTipoMisura.equalsIgnoreCase(Constants.TipoMisura.REVOCA_SEQUESTRO)) {
			beneValidation.validateRevocaSequestroPossibile(result, beneForm, idProcedimento, idUfficioUtente,
					idPersona, idTipoInteresse, formConInfo);

			if (result.hasErrors()) {
				BeneForm beneScelto = (BeneForm) request.getSession().getAttribute("beneScelto");
				beneScelto.setIdentificativoSoggettoProcedimento(idPersona);
				request.getSession().setAttribute("beneScelto", beneScelto);
				List<BeneSoggettoForm> beniSoggetti = beneService.listaSoggettiBeniProc(idProcedimento,
						idUfficioUtente, beneForm.getIdBene(), idAtto);
				modelMap.addAttribute("soggettiDelBene", beniSoggetti);
				modelMap.addAttribute("beneSoggetto", formConInfo);
				return "/procedimento/bene/titoliDiInteresse-Modifica";
			}
		}
		List<ComboForm> normative = new ArrayList<ComboForm>();
		if (formConInfo.getSoggetto().getMisura().getTipoMisura() != null && !formConInfo.getSoggetto()
				.getMisura().getTipoMisura().getId().equals(Constants.NO_ITEM_SELECTED)) {
			normative = commonService.getNormativaByTipoPrevenzioneAndTipoMisura(formConInfo.getSoggetto());
		}
		request.setAttribute("normativeRefresh", normative);
		BeneForm beneScelto = (BeneForm) request.getSession().getAttribute("beneScelto");
		beneScelto.setIdentificativoSoggettoProcedimento(idPersona);
		request.getSession().setAttribute("beneScelto", beneScelto);
		List<BeneSoggettoForm> beniSoggetti = beneService.listaSoggettiBeniProc(idProcedimento,
				idUfficioUtente, beneForm.getIdBene(), idAtto);
		modelMap.addAttribute("soggettiDelBene", beniSoggetti);
		modelMap.addAttribute("beneSoggetto", formConInfo);
		return "/procedimento/bene/titoliDiInteresse-Modifica";
	}

	@RequestMapping(value = "/procedimento/bene/visualizzaTitoliDiInteresse.htm", method = RequestMethod.GET)
	public String titoliDiDirittoShow(BeneForm beneForm, BindingResult result, HttpServletRequest request,
			ModelMap modelMap) {
		request.getSession().removeAttribute("indietroTerzoSoggetto");

		addBreadcrumbs("POST", "/procedimento/bene/gestioneBeniDelProcedimento.htm", "TitoliDiDiritto");

		return titoliDiDirittoUnchecked(beneForm, request, modelMap);
	}

	@RequestMapping(value = "/procedimento/bene/ricercaBene.htm", method = RequestMethod.POST, params = {
			"Nuovo" })
	public String visualizzaInserisciBene(BeneForm beneForm, ModelMap modelMap, HttpServletRequest request) {

		// Giacomo 11/07/2013 BreadCrumbs
		addBreadcrumbs("Nuovo", request);

		if (beneForm.getIdBene() == null || beneForm.getIdBene() == 0) {
			beneForm.setSwitchDenunciaIdentifImmobile("I");
			beneForm.setSwitchDenunciaIdentifImmobileP("I");
		}

		List<ComboForm> listaVersato = riempiComboVersato("Modifica");
		modelMap.addAttribute("listaVersato", listaVersato);

		// ***** GESTIONE PER BENE MOBILE DENARO e BENE FINANZIARIO *****
		boolean beneFinDen = false;
		if (beneForm.getTipologia().getId().equals("2")) { // Se Tipologia
															// Mobile
			if ((beneForm.getCategoria() != null) && (beneForm.getCategoria().getId() != null)
					&& beneForm.getCategoria().getId().equals("6")) {
				// Se Categoria Denaro
				beneForm.setTipologia(new ComboForm("2", "MOBILE"));
				beneForm.setCategoria(new ComboForm("6", "DENARO"));
				beneFinDen = true;
			}
		} else if (beneForm.getTipologia().getId().equals("4")) {
			beneForm.setTipologia(new ComboForm(beneForm.getTipologia().getId(), "FINANZIARIO"));
			beneFinDen = true;
		}
		if (beneFinDen) {
			// Se arrivo dal Copia Bene ...
			// Se c'è l'ABI ci sono anche Descrizione Ente, Provincia e CAB
			String abi = beneForm.getCodiceAbi();
			if (abi != null && !abi.equals("")) {
				beneForm.setAbi(abi);
				String cab = beneForm.getCodiceCab().getId();
				// Impostare la lista delle province e dei CAB se è presente
				// l'ABI
				BeneForm beneForm2 = beneService.caricaInfoOperFinanz(abi, cab);
				beneForm.setDescOperFinanz(
						new ComboForm(abi, beneForm2.getDescOperFinanz().getDescrizione()));
				beneForm.setDescrizioneEnte(beneForm2.getDescOperFinanz().getDescrizione());
				String provinciaPrec = beneForm2.getProvincia().getId();
				String descProvPrec = beneForm2.getProvincia().getDescrizione();
				// Prendo le province associate all'ABI inserito
				List<ComboForm> province = commonService.listaProvinceAbi(abi);
				modelMap.addAttribute("provinceAbi", province);
				beneForm.setDisabledProvincia(false);
				// Ed imposto il valore precedente come default nella pagina
				beneForm.setProvincia(new ComboForm(provinciaPrec, descProvPrec));
				beneForm.setProvinciaSel(provinciaPrec);
				// Prendo la lista dei CAB associati ad ABI e provincia
				// selezionati
				List<ComboForm> allCab = commonService.listaCodiciCab(abi, provinciaPrec);
				modelMap.addAttribute("codiciCab", allCab);
				// Ed imposto il valore precedente come default nella pagina
				beneForm.setCodiceCab(new ComboForm(cab, cab));
			} else {
				beneForm.setDisabledProvincia(true);
				beneForm.setDescrizioneEnte(null);
			}
			if (beneForm.getValutaEst() == null)
				beneForm.setDisabledValutaEst(true);
		} // ***** GESTIONE PER BEN MOBILE DENARO e BENE FINANZIARIO *****

		modelMap.addAttribute("beneForm", beneForm);

		// P.Q. Inizio MEV 2021/19 Operatore Economico Estero allora i campi vanno valorizzati in automatico
		modelMap.addAttribute("isOperatoreEconomicoEstero", false);
		checkOperatoreEconomicoEstero(beneForm, modelMap);		
		// P.Q. Fine MEV 2021/19 Operatore Economico Estero allora i campi vanno valorizzati in automatico				
		
		return dispatchInserimento(beneForm, null);
	}

	/**
	 * Dispatch alla jsp in base alla tipologia e categoria
	 * 
	 * @param beneForm
	 * @return
	 */
	private String dispatchInserimento(BeneForm beneForm, String operazione) {
		String tipologia = beneForm.getTipologia().getId();
		String categoria = null;
		String sottocategoria = null;
		if (beneForm.getCategoria() != null) {
			categoria = beneForm.getCategoria().getId();
		}

		if (beneForm.getSottocategoria() != null) {
			sottocategoria = beneForm.getSottocategoria().getId();
		}

		if (ComparatorUtil.containsValue(tipologia, Constants.TipologieBeni.ID_TIPO_IMMOBILE_S)) {
			return "/procedimento/bene/inserimentoImmobile";
		}

		if (ComparatorUtil.containsValue(tipologia, Constants.TipologieBeni.ID_TIPO_MOBILE_S)) {

			if (ComparatorUtil.containsValue(categoria, Constants.CategoriaBeni.ID_TIPO_DENARO_CONTANTE_S)) {
				// P.Q. 05/11/2021 Inizio Ticket 20211022016		
				if (beneForm.getDescOperFinanz() != null && beneForm.getDescOperFinanz().getDescrizione() != null && Constants.EnteFittizio.ID.equals(beneForm.getDescOperFinanz().getId())) {
						// imposto a blank i campi altrimenti vengono visualizzati in modifica
						beneForm.getDescOperFinanz().setDescrizione("");
						beneForm.getDescOperFinanz().setId("");
						beneForm.setDescrizioneEnte("");																
						beneForm.setCodiceAbi("");
						beneForm.getCodiceCab().setDescrizione("");
						beneForm.getCodiceCab().setId("");
					beneForm.setIndirizzoBeneFin("");
					beneForm.setProvinciaEntePolizza("");
					//F.C. MEV 2021/19 
					//beneForm.setNumeroRegistrazione("");
					}
										
				if (operazione != null && operazione.equals("modifica") ) {															
					return "/procedimento/bene/modificaDenaro";
				} else {
					return "/procedimento/bene/inserimentoDenaro";
				}
				// P.Q. 05/11/2021 Fine Ticket 20211022016				
			}

			if (ComparatorUtil.containsValue(categoria, Constants.CategoriaBeni.ID_TIPO_COLLEZIONE_S)) {
				return "/procedimento/bene/inserimentoCollezione";
			}

			if (ComparatorUtil.containsValue(categoria, Constants.CategoriaBeni.ID_TIPO_ANIMALI_S)) {
				return "/procedimento/bene/inserimentoAnimali";
			}

			if (ComparatorUtil.containsValue(categoria, Constants.CategoriaBeni.ID_ALTRI_OGGETTI_S)) {
				return "/procedimento/bene/inserimentoAltriOggetti";
			}

		}

		if (ComparatorUtil.containsValue(tipologia, Constants.TipologieBeni.ID_TIPO_MOBILE_REGISTRATO_S)) {

			if (ComparatorUtil.containsValue(categoria, Constants.CategoriaBeni.ID_TIPO_VEICOLO_S)) {
				return "/procedimento/bene/inserimentoVeicolo";
			}

			if (ComparatorUtil.containsValue(categoria, Constants.CategoriaBeni.ID_TIPO_IMMATERIALE_S)) {
				return "/procedimento/bene/inserimentoBeneImmateriale";
			}

		}

		if (ComparatorUtil.containsValue(tipologia, Constants.TipologieBeni.ID_TIPO_FINANZIARIO_S)) {
			
			if (beneForm.getDescOperFinanz() != null && beneForm.getDescOperFinanz().getDescrizione() != null && Constants.EnteFittizio.ID.equals(beneForm.getDescOperFinanz().getId())) {
				// imposto a blank i campi altrimenti vengono visualizzati in modifica
				beneForm.getDescOperFinanz().setDescrizione("");
				beneForm.getDescOperFinanz().setId("");
				beneForm.setDescrizioneEnte("");																
				beneForm.setCodiceAbi("");
				beneForm.getCodiceCab().setDescrizione("");
				beneForm.getCodiceCab().setId("");
				beneForm.setIndirizzoBeneFin("");
				beneForm.setProvinciaEntePolizza("");
				//F.C. MEV 2021/19 
				//beneForm.setNumeroRegistrazione("");
			}
						
			if (ComparatorUtil.containsValue(categoria, Constants.CategoriaBeni.ID_POLIZZE_S)) {
				if (SottoCategoriaUtils.isPolizza(sottocategoria)) {
					if (operazione != null && operazione.equals("modifica") ) {
						return "/procedimento/bene/modificaPolizza";
					}else {
						return "/procedimento/bene/inserimentoPolizza";
					}
					
				}
			}
			// P.Q. 15/11/2021 Inizio Ticket 20211022016 
			// commentata per assenza controlli in modifica bene finanziario 
			if (operazione != null && operazione.equals("modifica") ) {
				return "/procedimento/bene/modificaFinanziario";
			}else {
				return "/procedimento/bene/inserimentoBeneFinanziario";
			}
//			return "/procedimento/bene/inserimentoBeneFinanziario";
			// P.Q. 15/11/2021 Fine Ticket 20211022016
		}

		if (ComparatorUtil.containsValue(tipologia, Constants.TipologieBeni.ID_TIPO_MOBILE_AZIENDA_S)) {
			return "/procedimento/bene/inserimentoAzienda";
		}

		return "ERRORE! TIPO DI BENE NON ASSOCIATO A UNA JSP";
	}

	@RequestMapping(value = "/procedimento/bene/beniCollegati.htm", method = RequestMethod.GET)
	public String visualizzaRicercaDelBene(BeneForm beneForm, HttpServletRequest request, ModelMap modelMap) {

		// tolgo l'eventuale atto in sessione
		if (!isCorrezioneErroreMateriale())
			if (request.getSession().getAttribute(Constants.ATTO) != null)
				request.getSession().removeAttribute(Constants.ATTO);

		if (request.getSession().getAttribute(Constants.SUCCESS_MESSAGE) != null) {
			modelMap.addAttribute(Constants.SUCCESS_MESSAGE,
					request.getSession().getAttribute(Constants.SUCCESS_MESSAGE));
			request.getSession().removeAttribute(Constants.SUCCESS_MESSAGE);
		}
		if (request.getSession().getAttribute(Constants.SUCCESS_MESSAGE_LIST) != null) {
			modelMap.addAttribute(Constants.SUCCESS_MESSAGE_LIST,
					request.getSession().getAttribute(Constants.SUCCESS_MESSAGE_LIST));
			request.getSession().removeAttribute(Constants.SUCCESS_MESSAGE_LIST);
		}
		// ProcedimentoForm procedimento = recuperaProcedimento();
		// commentato su richiesta di stefania in data 06/11/2014
		/*
		 * boolean abilitazione = procedimentoValidation.validateAbilitaOperazioniProcura(procedimento,
		 * request); if (!abilitazione) { return "redirect:/procedimento/apri/copertinaProcedimento.htm"; }
		 */
		List<BeneForm> beniAssociati = new ArrayList<BeneForm>();
		long idProcedimento = ((ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");

		Object objTipoFascicolo = request.getSession().getAttribute(Constants.TIPO_FASCICOLO);
		int tipoFascicolo = 0;

		if (objTipoFascicolo != null) {
			if (objTipoFascicolo instanceof Integer) {
				tipoFascicolo = (Integer) objTipoFascicolo;
			} else if (objTipoFascicolo instanceof String) {
				tipoFascicolo = Integer.valueOf((String) objTipoFascicolo);
			}
		}

		// rimuovo eventuale lista nuovi beni (provenienti da Relazione amministratore)
		if (request.getSession().getAttribute("listaNuoviBeni") != null) {
			request.getSession().removeAttribute("listaNuoviBeni");
		}

		if (tipoFascicolo == Constants.TIPO_FASCICOLO_GESTIONE) {
			beniAssociati = beneService.listaBeniPerProcedimentoFascicoloGestione(0, idUfficioUtente,
					idProcedimento, beneForm);

			// beni senza idBene letti dall'xml della relazione dell'Amministratore
			List<BeneForm> beniNuovi = new ArrayList<BeneForm>();
			beniNuovi = flussiService.getListaNuoviBeniByIdProcFG(idProcedimento, idUfficioUtente, 'S');
			modelMap.addAttribute("listaNuoviBeni", beniNuovi);
			request.getSession().setAttribute("listaNuoviBeni", beniNuovi);

		} else {
			beniAssociati = beneService.listaBeniPerProcedimento(0, idUfficioUtente, idProcedimento,
					beneForm);
		}
		if (beniAssociati != null) {
			for (int i = 0; i < beniAssociati.size(); i++) {

				// List<BeneSoggettoForm> beniSoggetti =
				// beneService.listaSoggettiBeniProcSenzaIdAtto(idProcedimento, idUfficioUtente,
				// beniAssociati.get(i).getIdBene());
				// if (!((beniSoggetti == null) || beniSoggetti.isEmpty()))
				// beniAssociati.get(i).setTitoliDiDiritto("SI");

				if (tipoFascicolo == Constants.TIPO_FASCICOLO_GESTIONE) {
					String isBeneInIstanzaCamerale = beneService.isBeneInIstanzaCamerale(idProcedimento,
							idUfficioUtente, beniAssociati.get(i).getIdBene());
					beniAssociati.get(i).setIsBeneInIstanzaCamerale(isBeneInIstanzaCamerale);
				}

			}
		}
		modelMap.addAttribute("risultatiRicerca", beniAssociati);

		// Filippo non aggiunge il segnaposto se è un cambiopagina
		String cambiopagina = request.getParameter("cambiopagina");
		if (cambiopagina == null || "".equals(cambiopagina)) {
			// Giacomo 11/07/2013 Aggiunta Breadcrums
			addBreadcrumbs("GET", "/procedimento/bene/beniCollegati.htm", null);
		}

		return "/procedimento/bene/beniDelProcedimento";
	}

	/*********************** Inizio gestione beni dell'atto ************************************************/
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/procedimento/bene/beniCollegatiAtto.htm", method = RequestMethod.GET)
	public String visualizzaRicercaDelBeneAtto(BeneForm beneForm, HttpServletRequest request,
			ModelMap modelMap) {

		if (request.getSession().getAttribute(Constants.SUCCESS_MESSAGE) != null) {
			modelMap.addAttribute(Constants.SUCCESS_MESSAGE,
					request.getSession().getAttribute(Constants.SUCCESS_MESSAGE));
			request.getSession().removeAttribute(Constants.SUCCESS_MESSAGE);
		}
		if (request.getSession().getAttribute(Constants.SUCCESS_MESSAGE_LIST) != null) {
			modelMap.addAttribute(Constants.SUCCESS_MESSAGE_LIST,
					request.getSession().getAttribute(Constants.SUCCESS_MESSAGE_LIST));
			request.getSession().removeAttribute(Constants.SUCCESS_MESSAGE_LIST);
		}
		List<BeneForm> beniAssociati = new ArrayList<BeneForm>();
		long idProcedimento = ((ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
		AttoForm attoForm = (AttoForm) (request.getSession().getAttribute(Constants.ATTO));
		long vidDecreto = attoForm.getCodAtto();
		String idDecretoSequestroEProposta = null;
		long vidProposta = beneService.getIdAttoByUfficioEProcedimento(idUfficioUtente, idProcedimento);
		idDecretoSequestroEProposta = vidDecreto + ";" + vidProposta;
		beniAssociati = beneService.listaBeniProcDecretoFiltro(0, idUfficioUtente, idProcedimento, beneForm,
				vidDecreto, vidProposta, null, null);
		if (beniAssociati != null) {
			for (int i = 0; i < beniAssociati.size(); i++) {
				List<BeneSoggettoForm> lista = beneService.listaSoggettiBeniProc(idProcedimento,
						idUfficioUtente, beniAssociati.get(i).getIdBene(), vidDecreto);
				if (!((lista == null) || lista.isEmpty()))
					beniAssociati.get(i).setTitoliDiDiritto("SI");
			}
		}
		modelMap.addAttribute("idDecretoSequestroEProposta", idDecretoSequestroEProposta);
		modelMap.addAttribute("risultatiRicerca", beniAssociati);
		beneForm.setTastoIndietro("visualizzaRicercaDelBeneAtto");
		super.setIndietroForm(beneForm);

		// Andrea (AS): visualizzazione beni per decreti di rigetto
		String jsp2Show = (!ComparatorUtil.containsValue(recuperaAtto().getCategoriaAtto().getId(),
				Constants.DECRETO_RIGETTO_TOTALE_SEQUESTRO,
				Constants.DECRETO_RIGETTO_STATO_RICHIESTA_SEQUESTRO)) ? "/procedimento/bene/beniDelAtto"
						: "/procedimento/bene/beniDelRigetto";

		addBreadcrumbs("Elenco", request);

		return jsp2Show;
	}

	@RequestMapping(value = "/procedimento/bene/copiaTitoliDiDiritto.htm", method = RequestMethod.POST)
	public String visualizzaTitoliDiDirittoDaCopiare(BeneForm beneForm, BindingResult result,
			HttpServletRequest request, ModelMap modelMap) {

		long idProcedimento = ((ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
		long idAtto = 0l;
		if (beneForm.getIdRadio() != null) {
			beneForm.setIdBene(beneForm.getIdRadio());
		}
		if (beneForm.getIdBene() != null) {
			List<BeneSoggettoForm> beniSoggetti = beneService.listaSoggettiBeniProc(idProcedimento,
					idUfficioUtente, beneForm.getIdBene(), idAtto);
			modelMap.addAttribute("soggettiDelBene", beniSoggetti);
			beneValidation.validateBeneNonAssociatoTitoliDiritto(beniSoggetti, request, result);
		}
		TitoliDiDirittoCopia(beneForm, result, request, modelMap);

		// Giacomo 11/07/2013 BreadCrumbs
		addBreadcrumbs();
		return "/procedimento/bene/copiaTitoliDiDiritto";
	}

	@RequestMapping(value = "/procedimento/bene/copiaTitoliDiDiritto.htm", method = RequestMethod.GET)
	public String paginazioneCopia(BeneForm beneForm, BindingResult result, HttpServletRequest request,
			ModelMap modelMap) {
		return titoliDiDirittoCopia(beneForm, result, request, modelMap);
	}

	@RequestMapping(value = "/procedimento/bene/copiaTitoliDiDirittoAtto.htm", method = RequestMethod.GET)
	public String paginazioneCopiaAtto(BeneForm beneForm, BindingResult result, HttpServletRequest request,
			ModelMap modelMap) {
		return titoliDiDirittoCopiaAtto(beneForm, result, request, modelMap);
	}

	@RequestMapping(value = "/procedimento/bene/copiaTitoliDiDirittoAtto.htm", method = RequestMethod.POST)
	public String visualizzaTitoliDiDirittoDaCopiareAtto(BeneForm beneForm, BindingResult result,
			HttpServletRequest request, ModelMap modelMap) {

		long idProcedimento = ((ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
		long idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
		if (beneForm.getIdRadio() != null) {
			beneForm.setIdBene(beneForm.getIdRadio());
		}
		if (beneForm.getIdBene() != null) {
			List<BeneSoggettoForm> beniSoggetti = beneService.listaSoggettiBeniProc(idProcedimento,
					idUfficioUtente, beneForm.getIdBene(), idAtto);
			modelMap.addAttribute("soggettiDelBene", beniSoggetti);
			beneValidation.validateBeneNonAssociatoTitoliDiritto(beniSoggetti, request, result);
		}
		TitoliDiDirittoCopia(beneForm, result, request, modelMap);
		return "/procedimento/bene/copiaTitoliDiDirittoAtto";
	}

	// ***************** FINE NUOVO BENE ****************//

	private void impostaBeneDelBeneSoggetto(HttpServletRequest request, List<BeneSoggettoForm> beniSoggetti) {

		BeneForm beneDaImpostare = (BeneForm) request.getSession().getAttribute("beneDaImpostare");
		for (BeneSoggettoForm tmp : beniSoggetti) {
			tmp.setBene(beneDaImpostare);
		}
	}

	private String includeDettaglioBene(BeneForm beneForm, BindingResult result, ModelMap modelMap,
			HttpServletRequest request, String param) {
		BeneForm bene = recuperaDettaglioBene(beneForm);
		bene.setDettAssBene(param);
		if (bene.getFlgVersamento() != null) {
			bene.setFlgVersamento(riempiComboVersato(bene.getFlgVersamento().getId()).get(0));
		}
		if ("Elimina".equals(param)) {
			request.setAttribute("isCancellazione", Boolean.TRUE);
		}
		modelMap.addAttribute("beneForm", bene);

		if ("Dettaglio".equals(param)) {
			// lettura t_rel_proc_bene_atto_amm con idBene e idProcedimento
			Integer idUfficioUtente = recuperaIdUfficioUtente();
			long idProcedimento = recuperaIdProcedimento();
			long idBene = bene.getIdBene();

			List<SchedaAttiAmmDto> attiAssociati = beneService.getAttoAmministratoreDelBene(idBene,
					idProcedimento, idUfficioUtente, Constants.RELAZIONE_AMMINISTRATORE);
			// riempie in BeneForm
			bene.setListaSchedaAttiAmm(attiAssociati);
			modelMap.addAttribute("risultatiAtti", attiAssociati);
		}

		if (ComparatorUtil.containsValue(bene.getTipologia().getId(),
				Constants.TipologieBeni.ID_TIPO_FINANZIARIO_S, Constants.TipologieBeni.ID_TIPO_MOBILE_S)) {
			if (ComparatorUtil.containsValue(bene.getCategoria().getId(), Constants.CategoriaBeni.ID_POLIZZE,
					Constants.CategoriaBeni.ID_TIPO_DENARO_CONTANTE)) {
				if (SottoCategoriaUtils.isPolizza(bene.getSottocategoria().getId())) {

					if ("Associa".equals(param)) {
						return "/procedimento/bene/dettaglioBene";
					}
					return "/procedimento/bene/dettaglioPolizza";
				} else if ("206".equals(bene.getSottocategoria().getId())) {
					return "/procedimento/bene/dettaglioBene";
				}
			}
			if (!"Associa".equals(param) && !"Elimina".equals(param) && !"Dettaglio".equals(param)) {
				return "/procedimento/bene/inserimentoBeneFinanziario";
			}

		}

		// GP modifica del 28/05/2015
		if (modelMap.get("comunicazioneBeniForm") == null) {
			if ((request.getSession().getAttribute(Constants.ATTO) != null)
					&& (((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto() > 0l)
					&& (!((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getTipoAtto()
							.getId().equals(Constants.TIPO_ATTO_PROPOSTA))) {
				return "/procedimento/bene/dettaglioBeneAtto";
			}
		}
		// se provengo dalla maschera dell'atto
		// inserisci diverso dall'atto proposta per l'intestazione dell'atto che veniva visualizzato comunque
		// anche
		// per i beni del procedimento nellaproposta
		// GP modifica del 28/05/2015
		// if ((request.getSession().getAttribute(Constants.ATTO) != null) && (((AttoForm)
		// (request.getSession().getAttribute(Constants.ATTO))).getCodAtto() > 0l)
		// && (!((AttoForm)
		// (request.getSession().getAttribute(Constants.ATTO))).getTipoAtto().getId().equals(Constants.TIPO_ATTO_PROPOSTA)))
		// {
		// return "/procedimento/bene/dettaglioBeneAtto";
		// }

		// Segnalazione EMS-154
		if (ComparatorUtil.containsValue(bene.getTipologia().getId(),
				Constants.TipologieBeni.ID_TIPO_IMMOBILE_S)) {
			BeneForm beneGraffato = beneService.esisteGraffato(bene.getIdBene(), recuperaIdUfficioUtente());
			if (beneGraffato != null)
				modelMap.addAttribute("beneGraffato", beneGraffato);
		}

		return "/procedimento/bene/dettaglioBene";
	}

	/**
	 * Recupera il dettaglio di un bene da BDC oppure dalla base dati
	 * 
	 * @param beneForm
	 * @return il dettaglio di un bene da BDC oppure dalla base dati
	 */
	public BeneForm recuperaDettaglioBene(BeneForm beneForm) {

		// determina se il bene è relativo alla ricerca in SMP o in BDC
		// se il bene è relativo alla ricerca in BDC invoca il web service per
		// ottenere le informazioni di dettaglio
		BeneForm bene = null;
		if (beneForm.getIdBene() != null && beneForm.getVersioneBene() != null) {
			try {
				bene = bdcService.dettaglioBeneBDC(beneForm);
			} catch (Exception e) {
				super.setMessage(Constants.MESSAGE, "webservice.ricerca.error", "Dettaglio bene in BDC",
						false);
			}
		} else {

			Integer idUfficioUtente = recuperaIdUfficioUtente();
			long idProcedimento = recuperaIdProcedimento();

			bene = beneService.beneById(idUfficioUtente, beneForm.getIdBene(), idProcedimento);
		}
		if(beneForm.getDescOperFinanz() != null && beneForm.getDescOperFinanz().getId() != null) {
			bene.getDescOperFinanz().setId(beneForm.getDescOperFinanz().getId());
			TAnAltriOper tAnAltriOper = beneService
					.getTAnAltriOper(Long.valueOf(beneForm.getDescOperFinanz().getId()));
			bene.setProvinciaEntePolizza(tAnAltriOper.getDescSiglaProv());
			bene.setIndirizzoBeneFin(tAnAltriOper.getDescIndirizzo());
		}
		else {
			if (bene.getCodiceAbi() != null && bene.getCodiceCab().getId() != null)
			{
				BeneForm beneFormDett = beneService.caricaInfoOperFinanz(bene.getCodiceAbi(), bene.getCodiceCab().getId());
				if (beneFormDett != null) {
				bene.setDescOperFinanz(
						new ComboForm(bene.getCodiceAbi(), beneFormDett.getDescOperFinanz().getDescrizione()));
				//bene.setDescrizioneEnte(beneFormDett.getDescOperFinanz().getDescrizione());
				}
			}
		}
		
		//F.C. MEV 2021/19  Inizio
		if (bene.getDescOperFinanz() != null &&  bene.getDescOperFinanz().getDescrizione() != null && bene.getDescOperFinanz().getDescrizione().equals(Constants.EnteFittizio.DESCRIZIONE)) {
			// imposto a blank i campi altrimenti vengono visualizzati in modifica
			bene.getDescOperFinanz().setDescrizione("");
			bene.setCodiceAbi("");
			bene.getCodiceCab().setDescrizione("");
			bene.getCodiceCab().setId("");
	
			bene.setProvinciaEntePolizza("");
			bene.setIndirizzoBeneFin("");
			bene.getStato().setDescrizione("");
			bene.getStato().setId("");
								
			//F.C. MEV 2021/19 
			//bene.setNumeroRegistrazione("");
		}
		return bene;
	}

	private String includeFrazionamentoBene(BeneForm beneForm, BindingResult result,
			HttpServletRequest request, ModelMap modelMap) {

		long idProcedimento = ((ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		AttoForm attoForm = (AttoForm) (request.getSession().getAttribute(Constants.ATTO));
		long idAttoDecreto = 0L;
		if (attoForm != null)
			idAttoDecreto = attoForm.getCodAtto();
		BeneForm beneOut = beneService.beneById(idUfficioUtente, beneForm.getIdBene(), idProcedimento);
		List<BeneForm> beni = beneService.listaBeniPerProcedimentoNoAtto(idUfficioUtente, idProcedimento,
				idAttoDecreto, 0l, 0, 0, 0, 0, 0);
		modelMap.addAttribute("beni", beni);
		modelMap.addAttribute("bene", beneOut);
		return "/procedimento/bene/frazionamentoBene";
	}

	private void resettaListaCheckboxBeneForm(BeneForm beneForm, HttpServletRequest request) {

		List<Long> lista = new ArrayList<Long>();
		BeneForm beneScelto = (BeneForm) request.getSession().getAttribute("beneScelto");
		lista.add(beneScelto.getIdBene());
		beneForm.setListaIdCheckbox(lista);
	}

	private String titoliDiDirittoAssociaReload(BeneSoggettoForm beneSoggettoForm, HttpServletRequest request,
			ModelMap modelMap, BeneForm beneForm, long idProcedimento, Integer idUfficioUtente,
			SoggettoForm soggetto) {

		beneForm.setIdentificativoSoggettoProcedimento(
				beneSoggettoForm.getBene().getIdentificativoSoggettoProcedimento());
		BeneSoggettoForm beneSoggettoFormValidation = new BeneSoggettoForm();
		beneSoggettoFormValidation.setSoggetto(soggetto);
		beneSoggettoFormValidation.setBene(beneForm);
		beneSoggettoFormValidation.setIdTipoInteresse(beneSoggettoForm.getIdTipoInteresse());
		beneSoggettoFormValidation.setQuotaInteresse(beneSoggettoForm.getQuotaInteresse());
		beneSoggettoFormValidation.setNumeratoreFrazioneQuota(beneSoggettoForm.getNumeratoreFrazioneQuota());
		beneSoggettoFormValidation
				.setDenominatoreFrazioneQuota(beneSoggettoForm.getDenominatoreFrazioneQuota());
		request.getSession().setAttribute("beneScelto", beneForm);
		// se provengo dalla maschera dell'atto decreto di sequestro
		long idAtto = 0l;
		if ((request.getSession().getAttribute(Constants.ATTO) != null)
				&& (((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto() > idAtto)) {
			idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
		}
		List<BeneSoggettoForm> beniSoggetti = beneService.listaSoggettiBeniProc(idProcedimento,
				idUfficioUtente, beneForm.getIdBene(), idAtto);
		modelMap.addAttribute("beneSoggetto", beneSoggettoFormValidation);
		modelMap.addAttribute("soggettiDelBene", beniSoggetti);
		return "/procedimento/bene/titoliDiInteresse-Associa";
	}

	private String titoliDiDirittoAssociaUnchecked(BeneForm beneForm, HttpServletRequest request,
			ModelMap modelMap) {

		BeneSoggettoForm beneSoggettoForm = new BeneSoggettoForm();
		long idProcedimento = ((ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
		long idPersona = beneForm.getIdentificativoSoggettoProcedimento();
		SoggettoForm soggetto = procedimentoService.getPersonaById(idPersona, idUfficioUtente);
		beneSoggettoForm.setSoggetto(soggetto);
		beneSoggettoForm.setBene(beneForm);
		BeneForm beneScelto = (BeneForm) request.getSession().getAttribute("beneScelto");
		beneScelto.setIdentificativoSoggettoProcedimento(idPersona);
		request.getSession().setAttribute("beneScelto", beneScelto);
		// se provengo dalla maschera dell'atto decreto di sequestro
		long idAtto = 0l;
		if ((request.getSession().getAttribute(Constants.ATTO) != null)
				&& (((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto() > idAtto)) {
			idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
		}
		List<BeneSoggettoForm> beniSoggetti = beneService.listaSoggettiBeniProc(idProcedimento,
				idUfficioUtente, beneForm.getIdBene(), idAtto);
		modelMap.addAttribute("beneSoggetto", beneSoggettoForm);
		modelMap.addAttribute("soggettiDelBene", beniSoggetti);
		return "/procedimento/bene/titoliDiInteresse-Associa";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void TitoliDiDirittoCopia(BeneForm beneForm, BindingResult result, HttpServletRequest request,
			ModelMap modelMap) {

		long idProcedimento = ((ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
		BeneForm beneOut = new BeneForm();
		List<BeneForm> beni = new ArrayList();
		Iterator itx = beneForm.getListaIdCheckbox().iterator();
		while (itx.hasNext()) {
			Long idBene = (Long) itx.next();
			beneOut = beneService.beneById(idUfficioUtente, idBene, idProcedimento);
			beni.add(beneOut);
		}
		AttoForm attoForm = new AttoForm();
		long vidDecreto = 0l;
		// se provengo dalla maschera dell'atto decreto di sequestro
		if ((request.getSession().getAttribute(Constants.ATTO) != null)
				&& (((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto() > 0l)) {
			attoForm = (AttoForm) (request.getSession().getAttribute(Constants.ATTO));
			vidDecreto = attoForm.getCodAtto();
		}
		String idDecretoSequestroEProposta = null;
		if ((attoForm != null) && (attoForm.getCategoriaAtto() != null)
				&& (attoForm.getCategoriaAtto().getId() != null) && (vidDecreto != 0l)
				&& (attoForm.getCategoriaAtto().getId().equals(Constants.DECRETO_SEQUESTRO_URGENTE)
						|| attoForm.getCategoriaAtto().getId().equals(Constants.DECRETO_SEQUESTRO_ANTICIPATO)
						|| attoForm.getCategoriaAtto().getId().equals(Constants.DECRETO_SEQUESTRO_ORDINARIO)
						|| attoForm.getCategoriaAtto().getId()
								.equals(Constants.DECRETO_SEQUESTRO_ORDINARIO_II_GRADO))) {
			long vidProposta = beneService.getIdAttoByUfficioEProcedimento(idUfficioUtente, idProcedimento);
			idDecretoSequestroEProposta = vidDecreto + ";" + vidProposta;
			beneValidation.validateCopiaTitoliDiDiritto(beni, idUfficioUtente, idProcedimento, result);
		} else {
			beneValidation.validateCopiaTitoliDiDiritto(beni, idUfficioUtente, idProcedimento, result);
		}
		modelMap.addAttribute("idDecretoSequestroEProposta", idDecretoSequestroEProposta);
		modelMap.addAttribute("beni", beni);

		List<BeneForm> beniAssociati = new ArrayList<BeneForm>();
		List<BeneForm> beniAssociatiFinali = new ArrayList<BeneForm>();
		if (!beneForm.isVisualizzaFiltro()) {
			beneForm.setIdBene(null);
			beneForm.setTipologia(null);
			beneForm.setCategoria(null);
			beneForm.setSottocategoria(null);
			beneForm.setVisualizzaFiltro(false);
		}
		beniAssociati = beneService.listaBeniPerProcedimento(0, idUfficioUtente, idProcedimento, beneForm);
		for (int i = 0; i < beni.size(); i++) {
			if (beniAssociati.contains(beni.get(i))) {
				beniAssociati.remove(beni.get(i));
			}
		}
		for (int i = 0; i < beniAssociati.size(); i++) {
			List<BeneSoggettoForm> beniSoggetti = beneService.listaSoggettiBeniProc(idProcedimento,
					idUfficioUtente, beniAssociati.get(i).getIdBene(), vidDecreto);
			if (!((beniSoggetti == null) || beniSoggetti.isEmpty())) {
				beniAssociatiFinali.add(beniAssociati.get(i));
			}
		}
		beniAssociati = beniAssociatiFinali;
		modelMap.addAttribute("risultatiRicerca", beniAssociatiFinali);

		for (int i = 0; i < beniAssociatiFinali.size(); i++) {
			if (beneForm.getIdRadio() != null
					&& beniAssociatiFinali.get(i).getIdBene().equals(beneForm.getIdRadio())) {
				beneForm.setDescrizioneBene(beniAssociatiFinali.get(i).getDescrizioneBene());
				beneForm.setIdBene(beniAssociatiFinali.get(i).getIdBene());
			}
			if (beneForm.getIdRadio() == null
					&& beniAssociatiFinali.get(i).getIdBene().equals(beneForm.getIdBene())) {
				beneForm.setDescrizioneBene(beniAssociatiFinali.get(i).getDescrizioneBene());
			}
		}
	}

	private String titoliDiDirittoUnchecked(BeneForm beneForm, HttpServletRequest request,
			ModelMap modelMap) {

		long idProcedimento = ((ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
		// ATTENZIONE!!! controllo inserito perchè questo metodo viene richiamto
		// da più parti
		Long idBene;
		if ((beneForm.getListaIdCheckbox() != null) && !beneForm.getListaIdCheckbox().isEmpty()) {
			idBene = beneForm.getListaIdCheckbox().get(0);
		} else {
			if (((BeneForm) request.getSession().getAttribute("beneScelto")) != null
					&& ((BeneForm) request.getSession().getAttribute("beneScelto")).getIdBene() != null) {
				idBene = ((BeneForm) request.getSession().getAttribute("beneScelto")).getIdBene();
			} else {
				super.setMessage(Constants.MESSAGE, "error.beni.nuovi.nonconsentito", null, true);
				return "redirect:/procedimento/bene/beniCollegati.htm";

			}
		}
		BeneForm beneScelto = beneService.beneById(idUfficioUtente, idBene, idProcedimento);
		request.getSession().setAttribute("beneScelto", beneScelto);
		// imposto l'indietro
		// beneFormIndietro.setTastoIndietro("visualizzaRicercaDelBene");
		// super.setIndietroForm(beneFormIndietro);
		// se provengo dalla maschera dell'atto decreto di sequestro
		long idAtto = 0l;
		if ((request.getSession().getAttribute(Constants.ATTO) != null)
				&& (((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto() > idAtto)) {
			idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
			// imposto l'indietro nel contesto dell'atto
			// super.getIndietroForm(); //con questo get cancello l'indietroForm
			// in sessione
			// beneFormIndietro.setTastoIndietro("visualizzaRicercaDelBeneAtto");
			// super.setIndietroForm(beneFormIndietro);
		}
		List<BeneSoggettoForm> beniSoggetti = beneService.listaSoggettiBeniProc(idProcedimento,
				idUfficioUtente, idBene, idAtto);
		modelMap.addAttribute("soggettiDelBene", beniSoggetti);
		request.getSession().setAttribute("beneDaImpostare", beneScelto);
		modelMap.addAttribute("beneForm", beneScelto);
		modelMap.addAttribute("soggettiDelProcedimento",
				procedimentoService.soggettiDirettiInteressatiDelProcedimento(idProcedimento));
		return "/procedimento/bene/titoliDiInteresse";
	}

	@RequestMapping(value = "/procedimento/bene/gestioneBeniDelProcedimento.htm", method = RequestMethod.POST, params = {
			"Modifica" })
	public String validateModificaBene(BeneForm beneForm, BindingResult result, ModelMap modelMap,
			HttpServletRequest request) {

		// funzione per prelevare i checkbox paginati
		chooseCheckBoxPaged(beneForm, request);

		// beneValidation.validateListaBeni(beneForm, request, result);
		beneValidation.validateSceltaSingolaCheckbox(beneForm, result, request);
		if (result.hasErrors()) {
			return visualizzaRicercaDelBene(beneForm, request, modelMap);
		}
		// setto l'id del bene per la ricerca
		if (beneForm.getListaIdCheckbox() != null) {
			beneForm.setIdBene(beneForm.getListaIdCheckbox().get(0));
		}

		// Carico il bene
		BeneForm bene = recuperaDettaglioBene(beneForm);
		 //F.C. MEV 2021/19 
		if (bene.getDescOperFinanz() != null &&  bene.getDescOperFinanz().getDescrizione() != null && bene.getDescOperFinanz().getDescrizione().equals(Constants.EnteFittizio.DESCRIZIONE)) {
			// imposto a blank i campi altrimenti vengono visualizzati in modifica
			bene.getDescOperFinanz().setDescrizione("");
			bene.setCodiceAbi("");
			bene.getCodiceCab().setDescrizione("");
			bene.getCodiceCab().setId("");
			bene.setProvinciaEntePolizza("");
			bene.setIndirizzoBeneFin("");
			
			
				
		}
		 //F.C. MEV 2021/19  Fine		
		modelMap.addAttribute("beneForm", bene);

		String categoria = null;
		String sottocategoria = null;
		if (bene.getCategoria() != null) {
			categoria = bene.getCategoria().getId();
		}

		if (bene.getSottocategoria() != null) {
			sottocategoria = bene.getSottocategoria().getId();
		}
		if (ComparatorUtil.containsValue(categoria, Constants.CategoriaBeni.ID_POLIZZE)) {
			if (SottoCategoriaUtils.isPolizza(sottocategoria)) {
				// beneForm.setIndirizzoBeneFin(bene.getIndirizzoBeneFin());
				// beneForm.setProvinciaEntePolizza(bene.getProvinciaEntePolizza());
				// beneForm.setProvinciaEntePolizza(provinciaEntePolizza);
				// beneForm.setIndirizzoBeneFin(indirizzoBeneFin);
				// beneForm.setNumeroPolizza(numeroPolizza);

				request.setAttribute("isModifica", Boolean.TRUE);
				String caricaBenePolizza = caricaBenePolizzaPerModifica(bene, result, modelMap, request);
				return caricaBenePolizza;
//				return "/procedimento/bene/modificaPolizza";
			}
		}

		return caricaBenePerModifica(bene, result, modelMap, request);
	}
	
	private String caricaBenePolizzaPerModifica(BeneForm bene, BindingResult result, ModelMap modelMap,
			HttpServletRequest request) {
		// aggiungo la breadcrumb
		// addBreadcrumbs("POST", "/procedimento/bene/gestioneBeniDelProcedimento.htm", "Modifica");
		AttoForm attoForm = recuperaAtto();

		if (isCorrezioneErroreMateriale() && attoForm != null && attoForm.isDaModificaAttoCorrezione())
			addBreadcrumbs("POST", "/procedimento/atto/copertinaAtto.htm", "Dettaglio");
		else
			addBreadcrumbs("POST", "/procedimento/bene/gestioneBeniDelProcedimento.htm", "Modifica");


		String pageResult = "/procedimento/bene/modifica".concat(dispatchBene(bene));

		return pageResult;
	}
	
	private String caricaBenePerModifica(BeneForm bene, BindingResult result, ModelMap modelMap,
			HttpServletRequest request) {
		// aggiungo la breadcrumb
		// addBreadcrumbs("POST", "/procedimento/bene/gestioneBeniDelProcedimento.htm", "Modifica");
		AttoForm attoForm = recuperaAtto();

		if (isCorrezioneErroreMateriale() && attoForm != null && attoForm.isDaModificaAttoCorrezione())
			addBreadcrumbs("POST", "/procedimento/atto/copertinaAtto.htm", "Dettaglio");
		else
			addBreadcrumbs("POST", "/procedimento/bene/gestioneBeniDelProcedimento.htm", "Modifica");

		if (ComparatorUtil.containsValue(bene.getTipologia().getId(),
				Constants.TipologieBeni.ID_TIPO_IMMOBILE_S)) {

			if (bene.getComune() != null && StringUtils.isEmpty(bene.getComune().getDescrizione())) {
				bene.getComune().setDescrizione(bene.getDesComuneEstero());
			}
		}

		if (ComparatorUtil.containsValue(bene.getTipologia().getId(), TipologieBeni.ID_TIPO_MOBILE_S)) {
			if (ComparatorUtil.containsValue(bene.getCategoria().getId(), CategoriaBeni.ID_ALTRI_OGGETTI_S)) {
				//F.C. Ticket#20240625014 — malfunzionamento salvataggio descrizione bene
				long sottocategoria = 0;
				sottocategoria = Long.parseLong(bene.getSottocategoria().getId());
				
				if(sottocategoria>0 && SottoCategoriaUtils.isSottoCategoriaNonDefinita(sottocategoria)){
					bene.setDescrizioneBene(StringUtils.substringAfter(bene.getDescrizioneBene(), " - "));
				}
				//F.C. Ticket#20240625014 — malfunzionamento salvataggio descrizione bene
				if (bene.getComune() != null && StringUtils.isEmpty(bene.getComune().getDescrizione())) {
					bene.getComune().setDescrizione(bene.getDesComuneEstero());
				}
			}
		}
		// imposto i valori ad un bene finanziario se è di tipo finanziario
		impostaValoriBeneFinanziario(bene, modelMap);

		// ricarica le categorie
		modelMap.addAttribute("categorie", getCategorie(bene, result));
		modelMap.addAttribute("statiConservazione", getStatiConservazione(bene, result, request));
		modelMap.addAttribute("sottocategorie", getSottocategorie(bene, result));

		long idProcedimento = recuperaIdProcedimento();
		int idUfficioUtente = recuperaIdUfficioUtente();

		// Se non sto inserendo un atto di correzione errore materiale controllo se posso modificare i campi
		// obbligatori
		boolean isAssociato = false;
		if (!isCorrezioneErroreMateriale()) {
			isAssociato = beneService.isAssociato(bene.getIdBene(), idUfficioUtente, idProcedimento);
		}

		modelMap.addAttribute("modificabile", !isAssociato);

		if (logger.isDebugEnabled()) {
			logger.debug("E associato --> " + isAssociato);
			logger.debug("E modificabile --> " + !isAssociato);
		}

		String pageResult = "/procedimento/bene/modifica".concat(dispatchBene(bene));
		if (logger.isDebugEnabled()) {
			logger.debug("Dispatch --> " + pageResult);
		}

		/*
		 * ISSUE MEV 	: risolve Ticket INC000001842335, all’atto di confermare ed inviare il decreto di
		 * 					confisca compare la schermata - non è possibile confiscare beni non definiti.
		 * 					Aggiunto controllo di consistenza
		 * Numero MEV 	: 10
		 * Autore 		: Gioggi
		 * Data 		: 27 giu 2018
		 * Branch 		: MEV_10
		 */
		if (bene.getDescComuneCatastale() == null || bene.getDescComuneCatastale().isEmpty())
			bene.setDescComuneCatastale(bene.getComune() != null ? bene.getComune().getDescrizione() : "");
		// ***** FINE INTERVENTO MEV_10 *****//

		// Segnalazione EMS-166
		BeneForm beneGraffato = beneService.esisteGraffato(bene.getIdBene(), idUfficioUtente);
		if (beneGraffato != null)
			modelMap.addAttribute("beneGraffato", beneGraffato);
		else {
			List<BeneForm> beneFormList = beneService.verificaImmobileGraffato(bene.getIdBene(),
					idProcedimento, idUfficioUtente);
			if (beneFormList != null)
				modelMap.addAttribute("risultatiRicerca", beneFormList);
		}

		// P.Q. Inizio MEV 2021/19 Operatore Economico Estero allora i campi vanno valorizzati in automatico
		modelMap.addAttribute("isOperatoreEconomicoEstero", false);
		checkOperatoreEconomicoEstero(bene, modelMap);		
		// P.Q. Fine MEV 2021/19 Operatore Economico Estero allora i campi vanno valorizzati in automatico		
		
		return pageResult;
	}

	/**
	 * Esegue la dispatch delle jsp a seconda del tipo di bene
	 * 
	 * @param beneForm
	 * @return
	 */
	private String dispatchBene(BeneForm beneForm) {
		try {
			if (ComparatorUtil.containsValue(beneForm.getTipologia().getId(),
					Constants.TipologieBeni.ID_TIPO_MOBILE_AZIENDA_S)) {
				return "Azienda";
			}

			if (ComparatorUtil.containsValue(beneForm.getTipologia().getId(),
					Constants.TipologieBeni.ID_TIPO_IMMOBILE_S)) {
				return "Immobile";
			}

			if (ComparatorUtil.containsValue(beneForm.getTipologia().getId(),
					Constants.TipologieBeni.ID_TIPO_FINANZIARIO_S)) {
					if (SottoCategoriaUtils.isPolizza(beneForm.getSottocategoria().getId())) {
					return "Polizza";
				}else {
					return "Finanziario";
				}
				
			}
			if (ComparatorUtil.containsValue(beneForm.getTipologia().getId(),
					Constants.TipologieBeni.ID_TIPO_MOBILE_REGISTRATO_S) && beneForm.getCategoria() != null) {

				if (ComparatorUtil.containsValue(beneForm.getCategoria().getId(),
						Constants.CategoriaBeni.ID_TIPO_VEICOLO_S)) {
					return "Veicolo";
				}
				if (ComparatorUtil.containsValue(beneForm.getCategoria().getId(),
						Constants.CategoriaBeni.ID_TIPO_IMMATERIALE_S)) {
					return "BeneImmateriale";
				}
			}

			if (ComparatorUtil.containsValue(beneForm.getTipologia().getId(),
					Constants.TipologieBeni.ID_TIPO_MOBILE_S) && beneForm.getCategoria() != null) {
				if (ComparatorUtil.containsValue(beneForm.getCategoria().getId(),
						CategoriaBeni.ID_TIPO_ANIMALI_S)) {
					return "Animali";
				}

				if (ComparatorUtil.containsValue(beneForm.getCategoria().getId(),
						CategoriaBeni.ID_TIPO_COLLEZIONE_S)) {
					return "Collezione";
				}
				if (ComparatorUtil.containsValue(beneForm.getCategoria().getId(),
						CategoriaBeni.ID_TIPO_DENARO_CONTANTE_S)) {
					return "Denaro";
				}

				if (ComparatorUtil.containsValue(beneForm.getCategoria().getId(),
						CategoriaBeni.ID_ALTRI_OGGETTI_S)) {
					return "AltriOggetti";
				}
			}

		} catch (Exception e) {
			logger.error("Errore dispatch modifica Bene", e);
		}
		return "";
	}
		
	@RequestMapping(value = { "/procedimento/bene/modificaBene*.htm", "/procedimento/bene/modificaBenePolizza.htm" }, method = RequestMethod.POST)
	public String modificaBeneGenerico(BeneForm bene, BindingResult result, ModelMap modelMap) {

		if (ComparatorUtil.containsValue(bene.getTipologia().getId(), TipologieBeni.ID_TIPO_FINANZIARIO_S)
				|| (ComparatorUtil.containsValue(bene.getTipologia().getId(), TipologieBeni.ID_TIPO_MOBILE_S)
						&& ComparatorUtil.containsValue(bene.getCategoria().getId(),
								CategoriaBeni.ID_TIPO_DENARO_CONTANTE_S))) {
			caricaDatiBeneFinanziarioDenaro(bene, result, modelMap);
		}

		long idProcedimento = recuperaIdProcedimento();
		int idUfficioUtente = recuperaIdUfficioUtente();

		boolean isAssociato = false;
		// Se non sto inserendo un atto di correzione errore materiale controllo se posso modificare i campi
		// obbligatori
		if (!isCorrezioneErroreMateriale()) {
			isAssociato = beneService.isAssociato(bene.getIdBene(), idUfficioUtente, idProcedimento);
		}

		modelMap.addAttribute("modificabile", !isAssociato);

		if (logger.isDebugEnabled()) {
			logger.debug("E associato --> " + isAssociato);
			logger.debug("E modificabile --> " + !isAssociato);
		}
		// calcolo la jsp di destinazione al seconda della tipologia e categoria del bene
		String pageResult = "/procedimento/bene/modifica".concat(dispatchBene(bene));

		if (logger.isDebugEnabled()) {
			logger.debug("Dispatch --> " + pageResult);
		}

		return pageResult;

		// return new ModelAndView(pageResult, "bene", beneForm);
	}

	/**
	 * Restituisce true se sono nelle condizioni di inserimento di un atto di "Correzione Errore Materiale",
	 * false altrimenti
	 * 
	 * @return true se sono nelle condizioni di inserimento di un atto di "Correzione Errore Materiale", false
	 *         altrimenti
	 */
	private boolean isCorrezioneErroreMateriale() {
		long idTipoUfficio = recuperaIdTipoUfficio();
		// se non sono tribunale
		if (!ComparatorUtil.containsValue(idTipoUfficio, Constants.TIPO_UFFICIO_TRIBUNALE)) {
			return false;
		}
		// se non ho l'atto oppure la categoria è diverso da ATTo_GENERICO e sottocategoria atto è diverso da
		// Correzione errore materiale
		// vuol dire che non sono nell'atto di correzione errore materiale
		AttoForm attoForm = recuperaAtto();
		if (attoForm == null) {
			return false;
		}
		if (attoForm.getTipoAtto() == null || !ComparatorUtil.containsValue(attoForm.getTipoAtto().getId(),
				TipoAtto.TIPO_ATTO_GENERICO)) {
			return false;
		}

		if (attoForm.getCategoriaAtto() == null
				|| !ComparatorUtil.containsValue(attoForm.getCategoriaAtto().getId(),
						CategoriaAtto.CAT_CORREZIONE_ERRORE_MATERIALE)) {
			return false;
		}

		return true;
	}

	@RequestMapping(value = "/procedimento/bene/modificaBene*.htm", method = RequestMethod.POST, params = {
			"Modifica" })
	public String validateConfermaModificaBene(BeneForm beneForm, BindingResult result, ModelMap modelMap,
			HttpServletRequest request) {

		// P.Q. Inizio MEV 2021/19 Operatore Economico Estero allora i campi vanno valorizzati in automatico
		ProcedimentoForm procedimentoForm = recuperaProcedimento();
		long idprocedimento = procedimentoForm.getIdProcedimento();
		modelMap.addAttribute("isOperatoreEconomicoEstero", false);
		
		//REQ-STM-019-02 controllo per i beni mobili contante se il Numero registrazione e “Importo in euro”  è vuoto
		if ("6".equalsIgnoreCase(beneForm.getCategoria().getId()) && 
				"35".equalsIgnoreCase(beneForm.getSottocategoria().getId())) {
			    
			beneValidation.validateNumeroRegistrazioneImporto(beneForm, result, request);
			if (result.hasErrors()) {
				// Se ENTE FITTIZIO non carico la combo delle provincie (altrimenti vedrei N.D.)
				if (beneForm.getDescOperFinanz() != null && beneForm.getDescOperFinanz().getDescrizione() != null && Constants.EnteFittizio.ID.equals(beneForm.getDescOperFinanz().getId())) {
					modelMap.addAttribute("provinceAbi", null);
				} else {
					List<ComboForm> province = commonService.listaProvinceAbi(beneForm.getCodiceAbi());
					modelMap.addAttribute("provinceAbi", province);	
				}
							
				List<ComboForm> allCab = commonService.listaCodiciCab(beneForm.getCodiceAbi(), beneForm.getProvincia().getId());
				modelMap.addAttribute("codiciCab", allCab);			
				modelMap.addAttribute("modificabile", !(beneService.isAssociato(beneForm.getIdBene(),recuperaIdUfficioUtente(), idprocedimento)));
				
				return dispatchInserimento(beneForm, "modifica");
			}
	
		}
		
		//REQ-STM-019-02  per i beni finanziari se il Numero registrazione e “Importo in euro”  è vuoto
				//vado a vedere se l'ente e l'iban sono vuoti e segnalo che almeno uno dei campi Importo e numero registrazione devono essere valorizzati
				//per i beni delle sotto-categorie diverse da Altro o Non-Definito 
				if ("4".equals(beneForm.getTipologia().getId()) && 
						(!"117".equals(beneForm.getSottocategoria().getId()) && 
						 !"99".equals(beneForm.getSottocategoria().getId())) ) {
					beneValidation.validateNumeroRegistrazioneImporto(beneForm, result, request);
					if (result.hasErrors()) {
						// Se ENTE FITTIZIO non carico la combo delle provincie (altrimenti vedrei N.D.)
						if (beneForm.getDescOperFinanz() != null && beneForm.getDescOperFinanz().getDescrizione() != null && Constants.EnteFittizio.ID.equals(beneForm.getDescOperFinanz().getId())) {
							modelMap.addAttribute("provinceAbi", null);
						} else {
							List<ComboForm> province = commonService.listaProvinceAbi(beneForm.getCodiceAbi());
							modelMap.addAttribute("provinceAbi", province);	
						}
									
						List<ComboForm> allCab = commonService.listaCodiciCab(beneForm.getCodiceAbi(), beneForm.getProvincia().getId());
						modelMap.addAttribute("codiciCab", allCab);			
						modelMap.addAttribute("modificabile", !(beneService.isAssociato(beneForm.getIdBene(),recuperaIdUfficioUtente(), idprocedimento)));
						
						return dispatchInserimento(beneForm, "modifica");
					}
				}
				
				
		//REQ-STM-019-02 fine
		
		checkOperatoreEconomicoEstero(beneForm, modelMap);		
		// P.Q. Fine MEV 2021/19 Operatore Economico Estero allora i campi vanno valorizzati in automatico		
		
		// P.Q. 05/11/2021 Inizio Ticket 20211022016
		long idUffOwn = recuperaIdUfficioUtente();
		String codUtente = recuperaIdCodiceUtente();		
		
		//F.C. REQ-STM-019-06 nel caso di:
		//CONTO CORRENTE BANCARIO
		//CONTO CORRENTE POSTALE
		//LIBRETTO BANCARIO
		//LIBRETTO POSTALE
		//vanno fatti i controlli su obbligatorieta' ABI, CAB, IBAN ecc.
		
		//F.C. Ticket#20240312012 — Problemi con le società
		//nel caso della categoria azienda non esiste la sottocategoria
		if (beneForm.getSottocategoria() != null) {
			if (
					!"39".equalsIgnoreCase(beneForm.getSottocategoria().getId()) &&
					!"38".equalsIgnoreCase(beneForm.getSottocategoria().getId()) &&
					!"37".equalsIgnoreCase(beneForm.getSottocategoria().getId()) &&
					!"36".equalsIgnoreCase(beneForm.getSottocategoria().getId())
			) {
			
				//REQ-STM-019-02
				beneForm = beneValidation.checkEnteFittizio(beneForm,result);
			}
		}
		// P.Q. 05/11/2021 Fine Ticket 20211022016
		
		beneValidation.validateInserisciBene(beneForm, result);
		// P.Q. 05/11/2021 Inizio Ticket 20211022016
		if (result.hasErrors()) {

			// Se ENTE FITTIZIO non carico la combo delle provincie (altrimenti vedrei N.D.)
			if (beneForm.getDescOperFinanz() != null && beneForm.getDescOperFinanz().getDescrizione() != null && Constants.EnteFittizio.ID.equals(beneForm.getDescOperFinanz().getId())) {
				modelMap.addAttribute("provinceAbi", null);
			} else {
				List<ComboForm> province = commonService.listaProvinceAbi(beneForm.getCodiceAbi());
				modelMap.addAttribute("provinceAbi", province);	
			}
			
			//F.C. Ticket#20240625014 — malfunzionamento salvataggio descrizione bene: modifica per risolvere il nullpointer exception nel caso in cui al bene non abbia 
			//non venga messa la descrizione per i beni di tipo ALTRI OGGETTI 
			List<ComboForm> allCab;
			if (beneForm.getProvincia()!=null) {				
				allCab = commonService.listaCodiciCab(beneForm.getCodiceAbi(),  beneForm.getProvincia().getId());
					
			}
			else
			{
				allCab = commonService.listaCodiciCab(beneForm.getCodiceAbi(),  "");
			}
			//F.C. Ticket#20240625014 — malfunzionamento salvataggio descrizione bene FINE 
			modelMap.addAttribute("codiciCab", allCab);	
			
			modelMap.addAttribute("modificabile", !(beneService.isAssociato(beneForm.getIdBene(),recuperaIdUfficioUtente(), idprocedimento)));
			
			//return dispatchInserimento(beneForm, "modifica");
			String view = caricaBenePerModifica(beneForm, result, modelMap, request);
			
			return view;
		}		
		// P.Q. 05/11/2021 Fine Ticket 20211022016
		
		
		/*  GC 28/08/2024: TT#202407020117
		 * Verifico il campo numregistrazione in base al tipo di bene
		 * Per beneFinanziario è diventato alfanumerico; negli altri casi è numerico
		 * // SITMP-204 Adeguamento applicativo dei campi che utilizzano T_BENE_FIN.DESC_REGISTRAZIONE - aggiunto controllo lato server sul numero registrazione che sia numerico
			beneValidation.validateNumeroRegistrazione(beneForm, result, request);
			if(result.hasErrors())
		 */
		// P.Q. 05/11/2021 Inizio Ticket 20211022016		
		if (chkNumReg(beneForm, result, request).hasErrors()) {
			// Se ENTE FITTIZIO non carico la combo delle provincie (altrimenti vedrei N.D.)
			if (beneForm.getDescOperFinanz() != null && beneForm.getDescOperFinanz().getDescrizione() != null && Constants.EnteFittizio.ID.equals(beneForm.getDescOperFinanz().getId())) {
				modelMap.addAttribute("provinceAbi", null);
			} else {
				List<ComboForm> province = commonService.listaProvinceAbi(beneForm.getCodiceAbi());
				modelMap.addAttribute("provinceAbi", province);	
			}
			
			List<ComboForm> allCab = commonService.listaCodiciCab(beneForm.getCodiceAbi(), beneForm.getProvincia().getId());
			modelMap.addAttribute("codiciCab", allCab);					
			modelMap.addAttribute("modificabile", !(beneService.isAssociato(beneForm.getIdBene(),recuperaIdUfficioUtente(), idprocedimento)));			
			String view = caricaBenePerModifica(beneForm, result, modelMap, request);
			
			return view;
		}
		// P.Q. 05/11/2021 Fine Ticket 20211022016
		
		List<Message> customMessageList = new ArrayList<Message>();
		Long idBene = null;
		AttoForm attoForm = recuperaAtto();
		if (isCorrezioneErroreMateriale() && attoForm != null && !attoForm.isDaModificaAttoCorrezione()) {

			long idAtto = attoForm.getCodAtto();

			if (logger.isDebugEnabled()) {
				logger.debug(
						"Inserisco il bene modificato nell'atto \"Correzione Errore Materiale\" con identificativo atto:"
								+ idAtto);
			}

			String idTipoFasc = ((ProcedimentoForm) (request.getSession()
					.getAttribute(Constants.ID_PROCEDIMENTO))).getTipoFascicolo().getId();
			// String abilFascGest = configParameterSource.getMessage("ABIL_FASC_GEST", null, null);

			idBene = beneService.insersciBeneInCorrezioneErroreMateriale(idUffOwn, codUtente, idprocedimento,
					beneForm.getIdBene(), idAtto, beneForm, idTipoFasc, "S");
			super.addMessageIntoList(customMessageList, Constants.SUCCESS_MESSAGE, "success.operation",
					"Correzione Errore Materiale");
		} else {

			idBene = beneForm.getIdBene();

			if (logger.isDebugEnabled()) {
				logger.debug("Modifica del bene con identificativo :" + idBene);
			}

			beneService.modificaBene(idUffOwn, codUtente, idprocedimento, beneForm);
			super.addMessageIntoList(customMessageList, Constants.SUCCESS_MESSAGE, "success.operation",
					"MODIFICA");

		}
		super.addMessageIntoList(customMessageList, Constants.SUCCESS_MESSAGE, "bene.modifica.corretto",
				idBene != null ? String.valueOf(idBene) : null);
		super.setMessagesSuccess(customMessageList, true);

		String callback = "redirect:/procedimento/bene/gestioneBeniDelProcedimento.htm";

		if (StringUtils.isNotEmpty(procedimentoForm.getCallbackUrl())) {
			callback = procedimentoForm.getCallbackUrl();
		}

		// Modifica per segnalazione MS21
		if (isCorrezioneErroreMateriale() && attoForm != null)// && attoForm.isDaModificaAttoCorrezione())
			return attoController.copertinaAtto(attoForm, result, request, modelMap);

		if (logger.isDebugEnabled()) {
			logger.debug("Callback url -> " + callback);
		}

		return callback;
	}

	@RequestMapping(value = "/procedimento/bene/modificaBene*.htm", method = RequestMethod.POST, params = {
			"Graffato" })
	public String inserisciGraffato(BeneForm beneForm, BindingResult result, ModelMap modelMap,
			HttpServletRequest request) {
		beneService.inserisciGraffato(beneForm.getIdBene(), beneForm.getListaIdCheckbox().get(0).longValue(),
				recuperaIdUfficioUtente());
		BeneForm beneGraffato = beneService.esisteGraffato(beneForm.getIdBene(), recuperaIdUfficioUtente());
		if (beneGraffato != null)
			modelMap.addAttribute("beneGraffato", beneGraffato);
		return "/procedimento/bene/modificaImmobile";
	}

	@RequestMapping(value = "/procedimento/bene/modificaBene*.htm", method = RequestMethod.POST, params = {
			"EliminaGraffato" })
	public String eliminaGraffato(BeneForm beneForm, BindingResult result, ModelMap modelMap,
			HttpServletRequest request) {

		beneService.eliminaGraffato(beneForm.getIdBene(), beneForm.getNumeGraffati(),
				recuperaIdUfficioUtente());
		List<BeneForm> beneFormList = beneService.verificaImmobileGraffato(beneForm.getIdBene(),
				recuperaIdProcedimento(), recuperaIdUfficioUtente());
		if (beneFormList != null) {
			modelMap.addAttribute("risultatiRicerca", beneFormList);
		}
		return "/procedimento/bene/modificaImmobile";
	}

	@RequestMapping(value = "/procedimento/bene/gestioneBeniDelAtto.htm", method = RequestMethod.POST, params = {
			"TrasformaBene" })
	public String validateTrasformaBeneAtto(BeneForm beneForm, BindingResult result, ModelMap modelMap,
			HttpServletRequest request) {

		long idProcedimento = ((ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();

		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");

		beneValidation.validateSceltaSingolaCheckboxTrasforma(beneForm, idUfficioUtente, idProcedimento,
				result, request);
		if (result.hasErrors()) {
			return visualizzaRicercaDelBeneAtto(beneForm, request, modelMap);
		}

		// setto l'id del bene per la ricerca
		if (beneForm.getListaIdCheckbox() != null) {
			beneForm.setIdBene(beneForm.getListaIdCheckbox().get(0));
		}

		// Carico il bene
		BeneForm bene = recuperaDettaglioBene(beneForm);

		addBreadcrumbs("TrasformaBene", request);

		// solo se altra trasformazione
		bene.setAltraTrasformazione("S");

		ComboForm tipologia = new ComboForm();
		tipologia.setId("-1");
		bene.setTipologia(tipologia);

		ComboForm tipoTrasformazione = new ComboForm();
		tipoTrasformazione.setId("13");
		bene.setTipoTrasformazione(tipoTrasformazione);

		modelMap.addAttribute("beneForm", bene);

		return "/procedimento/bene/trasformaBene";
	}

	@RequestMapping(value = "/procedimento/bene/gestioneBeniDelProcedimento.htm", method = RequestMethod.POST, params = {
			"TrasformaBene" })
	public String validateTrasformaBene(BeneForm beneForm, BindingResult result, ModelMap modelMap,
			HttpServletRequest request) {

		beneValidation.validateSceltaSingolaCheckbox(beneForm, result, request);
		if (result.hasErrors()) {
			return visualizzaRicercaDelBene(beneForm, request, modelMap);
		}
		// setto l'id del bene per la ricerca
		if (beneForm.getListaIdCheckbox() != null) {
			beneForm.setIdBene(beneForm.getListaIdCheckbox().get(0));
		}

		// Carico il bene
		BeneForm bene = recuperaDettaglioBene(beneForm);
		modelMap.addAttribute("beneForm", bene);

		addBreadcrumbs("TrasformaBene", request);

		return "/procedimento/bene/trasformaBene";
	}

	@ModelAttribute("tipiTrasformazione")
	public List<ComboForm> getTipiTrasformazione(@Valid BeneForm beneForm, BindingResult result) {
		List<ComboForm> tipi = commonService.getTipoTrasformazioni(TipiTrasformazione.TRASFORMAZIONE);
		return tipi;
	}

	@RequestMapping(value = "/procedimento/bene/trasformaBene.htm", method = RequestMethod.POST)
	public String sceltaTrasformazione(BeneForm beneForm, BindingResult result, ModelMap modelMap,
			HttpServletRequest request) {

		addBreadcrumbs("POST", "/procedimento/bene/trasformaBene.htm", null);
		if (Utility.isDefinito(beneForm.getTipoTrasformazione())) {
			if (ComparatorUtil.containsValue(beneForm.getTipoTrasformazione().getId(),
					TipiTrasformazione.TIPO_VENDITA_S)) {
				Integer idUfficioUtente = recuperaIdUfficioUtente();
				long idProcedimento = recuperaIdProcedimento();
				if (isBeneVendibile(idProcedimento, beneForm.getIdBene(), idUfficioUtente)) {
					beneForm.setTipologia(new ComboForm(TipologieBeni.ID_TIPO_MOBILE_S, "MOBILE"));
					beneForm.setCategoria(new ComboForm(CategoriaBeni.ID_TIPO_DENARO_CONTANTE_S, "DENARO"));
					// Sotto categoria denaro ricavato da vendita
					// TODO Modificare sotto categoria
					beneForm.setSottocategoria(new ComboForm("36", "Denaro Ricavato da Vendita"));
					caricaDatiBeneFinanziarioDenaro(beneForm, result, modelMap);
					modelMap.addAttribute("sottocategorie", getSottocategorie(beneForm, result));
					if (StringUtils.isBlank(beneForm.getCodiceIban())) {
						beneForm.setCodiceIban(Constants.CODICE_IBAN_TRASFORMA_BENE);
					}

					return "/procedimento/bene/venditaBene";
				} else {// genero l'errore. Non è possibile vendere un bene che non presenta nessuna quota di
						// sequestro superiore a 0
					super.setMessage(Constants.MESSAGE, "error.venditabene.no.quote.sequestrate", null, true);
					return "/procedimento/bene/trasformaBene";
				}
			}

			if (ComparatorUtil.containsValue(beneForm.getTipoTrasformazione().getId(),
					TipiTrasformazione.FRAZIONAMENTO_S)) {
				// Long idBene = beneForm.getListaIdCheckbox().get(0);
				// beneForm.setIdBene(idBene);

				return includeFrazionamentoBene(beneForm, result, request, modelMap);
			}

			if (ComparatorUtil.containsValue(beneForm.getTipoTrasformazione().getId(),
					TipiTrasformazione.ALTRO_TIPO_S)) {
				modelMap.addAttribute("categorie", getCategorie(beneForm, result));
				modelMap.addAttribute("sottocategorie", getSottocategorie(beneForm, result));

				// solo se altra trasformazione
				beneForm.setAltraTrasformazione("S");

				ComboForm tipoTrasformazione = new ComboForm();
				tipoTrasformazione.setId("13");
				beneForm.setTipoTrasformazione(tipoTrasformazione);
			}

			return "/procedimento/bene/trasformaBene";
		}

		// vado in errore
		ValidationUtil.requiredComboForm(beneForm.getTipoTrasformazione(), "tipoTrasformazione.id", result);
		return "/procedimento/bene/trasformaBene";
	}

	/**
	 * Controlla che il bene sia sequestrato e che l'atto di sequestro sia registrato
	 * 
	 * @param beneSequestrato
	 * @return boolean
	 */
	public boolean isBeneVendibile(Long idProcedimento, long idBene, int idUfficioUtente) {
		return existsQuoteSequestrate(idProcedimento, idBene, idUfficioUtente)
				&& beneService.existsSequestroRegistrato(idProcedimento, idBene, idUfficioUtente);
	}

	public boolean existsQuoteSequestrate(final Long idProcedimento, long idBene, int idUfficioUtente) {
		TRelProcBeneTit beneSequestrato = beneService.getRelazioniProcedimentoBeneTitolare(idProcedimento,
				idBene, idUfficioUtente, 0, 0);
		return beneSequestrato != null && (beneSequestrato.getNumeQuotaSeq() != null
				&& beneSequestrato.getNumeQuotaSeq().intValue() > 0
				|| ((beneSequestrato.getNumeFrazioneNumSeq() != null
						&& beneSequestrato.getNumeFrazioneNumSeq() > 0)
						&& (beneSequestrato.getNumeFrazioneDenSeq() != null
								&& beneSequestrato.getNumeFrazioneDenSeq() > 0)));
	}

	@RequestMapping(value = "/procedimento/bene/trasformaBene*.htm", method = RequestMethod.POST)
	public String trasformaBeneGenerico(BeneForm bene, BindingResult result, ModelMap modelMap) {

		if (ComparatorUtil.containsValue(bene.getTipologia().getId(), TipologieBeni.ID_TIPO_FINANZIARIO_S)
				|| (ComparatorUtil.containsValue(bene.getTipologia().getId(), TipologieBeni.ID_TIPO_MOBILE_S)
						&& ComparatorUtil.containsValue(bene.getCategoria().getId(),
								CategoriaBeni.ID_TIPO_DENARO_CONTANTE_S))) {
			caricaDatiBeneFinanziarioDenaro(bene, result, modelMap);
		}
		String pageResult = "/procedimento/bene/trasforma".concat(dispatchBene(bene));

		if (logger.isDebugEnabled()) {
			logger.debug("Dispatch --> " + pageResult);
		}

		return pageResult;
	}

	@RequestMapping(value = "/procedimento/bene/trasformaBene.htm", method = RequestMethod.POST, params = {
			"TrasformaBene" })
	public String confermaSceltaTrasformaBene(BeneForm beneForm, BindingResult result, ModelMap modelMap) {
		// validazione Trasforma Bene
		ValidationUtil.requiredComboForm(beneForm.getTipoTrasformazione(), "tipoTrasformazione.id", result);
		ValidationUtil.requiredComboForm(beneForm.getTipologia(), "tipologia.id", result);
		ValidationUtil.requiredComboForm(beneForm.getCategoria(), "categoria.id", result);

		List<ComboForm> listaVersato = riempiComboVersato("Modifica");
		modelMap.addAttribute("listaVersato", listaVersato);

		if (result.hasErrors()) {
			return "/procedimento/bene/trasformaBene";
		}

		BeneForm bene = recuperaDettaglioBene(beneForm);

		// Se hanno la stessa tipologia e categoria posso fare il merge
		if (bene.getTipologia().equals(beneForm.getTipologia())
				&& bene.getCategoria().equals(beneForm.getCategoria())) {

			bene.setSottocategoria(beneForm.getSottocategoria());
			bene.setTipoTrasformazione(beneForm.getTipoTrasformazione());

			modelMap.addAttribute("beneForm", bene);
			// trasformaAnimali.jsp
			// return "/procedimento/bene/trasforma".concat(dispatchBene(beneForm));
			modelMap.addAttribute("sottocategorie", getSottocategorie(bene, result));

		} else {
			// se è di tipo IMMOBILE
			beneForm.setSwitchDenunciaIdentifImmobile("I");
			beneForm.setSwitchDenunciaIdentifImmobileP("I");

			beneForm.setTipologia(commonService.getTipologiaBene(beneForm.getTipologia().getId()));
			beneForm.setCategoria(commonService.getCategoriaBene(beneForm.getCategoria().getId()));
			modelMap.addAttribute("sottocategorie", getSottocategorie(beneForm, result));
		}

		String pageResult = "/procedimento/bene/trasforma".concat(dispatchBene(beneForm));

		if (logger.isDebugEnabled()) {
			logger.debug("Dispatch --> " + pageResult);
		}
		return pageResult;
		// return "/procedimento/bene/trasformaBene";
	}

	@RequestMapping(value = "/procedimento/bene/trasformaBene.htm", method = RequestMethod.POST, params = {
			"VenditaBene" })
	public String venditaBene(BeneForm beneForm, BindingResult result, ModelMap modelMap,
			HttpServletRequest request) {

		beneValidation.validateTrasformazioneBeneDenaro(beneForm, result);
		if (result.hasErrors()) {
			return sceltaTrasformazione(beneForm, result, modelMap, request);
		}

		long idUffOwn = recuperaIdUfficioUtente();
		long idprocedimento = recuperaIdProcedimento();
		String codUtente = recuperaIdCodiceUtente();
		beneForm.getSottocategoria().setId("206");

		long idBene = beneService.venditaBene(idUffOwn, codUtente, idprocedimento, beneForm);

		if (logger.isDebugEnabled()) {
			logger.debug("Vendita Bene - Nuovo Identificativo " + idBene);
		}

		List<Message> customMessageList = new ArrayList<Message>();
		super.addMessageIntoList(customMessageList, Constants.SUCCESS_MESSAGE, "success.operation",
				"VENDITA");
		super.addMessageIntoList(customMessageList, Constants.SUCCESS_MESSAGE, "bene.trasformazione.corretto",
				String.valueOf(idBene));
		super.setMessagesSuccess(customMessageList, true);

		return "redirect:/procedimento/bene/gestioneBeniDelProcedimento.htm";
	}

	/**
	 * Imposta i valori ad un bene finanziario
	 * 
	 * @param beneForm
	 * @param modelMap
	 */
	private void impostaValoriBeneFinanziario(BeneForm beneForm, ModelMap modelMap) {

		if (ComparatorUtil.containsValue(beneForm.getTipologia().getId(),
				Constants.TipologieBeni.ID_TIPO_FINANZIARIO_S)
				|| (ComparatorUtil.containsValue(beneForm.getTipologia().getId(),
						TipologieBeni.ID_TIPO_MOBILE_S)
						&& ComparatorUtil.containsValue(beneForm.getCategoria().getId(),
								CategoriaBeni.ID_TIPO_DENARO_CONTANTE_S))) {

			/*
			 * ISSUE MEV 	: risolve Ticket INC000001842335, all’atto di confermare ed inviare il decreto di
			 * 					confisca compare la schermata - non è possibile confiscare beni non definiti.
			 * 					Aggiunto controllo di consistenza
			 * Numero MEV 	: 10
			 * Autore 		: Gioggi
			 * Data 		: 27 giu 2018
			 * Branch 		: MEV_10
			 */
			if (beneForm.getCodiceAbi() == null && beneForm.getSottocategoria() != null
					&& "107".equals(beneForm.getSottocategoria().getId())) {
				String codiceAbi = beneService.getDenaro(beneForm.getIdBene(), beneForm.getIdUffOwner());
				beneForm.setCodiceAbi(codiceAbi);
			}
			// ***** FINE INTERVENTO MEV_10 *****//
			String descEnte = beneService.getDescEnte(beneForm.getCodiceAbi());
			//23/06/2021 in caso di modifica se la descrizione ente è vuota significa che
			//la modifica è viene fatta su un bene con ente fittizio
			if (descEnte == null && beneForm.getCodiceAbi() != null && Constants.EnteFittizio.ABI.compareTo(beneForm.getCodiceAbi())== 0) descEnte = Constants.EnteFittizio.DESCRIZIONE;
			beneForm.setDescOperFinanz(new ComboForm(beneForm.getCodiceAbi(), descEnte));
			beneForm.setDescrizioneEnte(descEnte);

			// gestione codici EQUITALIA
			List<ComboForm> listaVersato = riempiComboVersato("Modifica");
			modelMap.addAttribute("listaVersato", listaVersato);

			if (beneForm.getCodiceCab() != null) {
				beneForm.getCodiceCab().setDescrizione(beneForm.getCodiceCab().getId());

				BeneForm beneForm2 = beneService.caricaInfoOperFinanz(beneForm.getCodiceAbi(),
						beneForm.getCodiceCab().getId());
				if (beneForm2.getProvincia() != null) {
					// Metto la provincia come valore di default nel menu a
					beneForm.setProvincia(new ComboForm(beneForm2.getProvincia().getId(),
							beneForm2.getProvincia().getDescrizione()));
					beneForm.setProvinciaSel(beneForm2.getProvincia().getId());

					// Setto l'indirizzo associato ad ABI e CAB inseriti
					IndirizzoForm indirizzo = Utility.costruisciIndirizzo(beneForm2);
					beneForm.setIndirizzoBeneFin(indirizzo.getDenominazione());

					List<ComboForm> allCab = commonService.listaCodiciCab(beneForm.getCodiceAbi(),
							beneForm.getProvincia().getId());
					modelMap.addAttribute("codiciCab", allCab);
				}
				if (beneForm.getCodiceAbi() != null) {
					/*
					 * ISSUE MEV 	: risolve Ticket INC000001842335, all’atto di confermare ed inviare il decreto di
					 * 					confisca compare la schermata - non è possibile confiscare beni non definiti.
					 * 					Aggiunto controllo di consistenza
					 * Numero MEV 	: 10
					 * Autore 		: Gioggi
					 * Data 		: 27 giu 2018
					 * Branch 		: MEV_10
					 */
					List<ComboForm> allCab = commonService.listaCodiciCab(beneForm.getCodiceAbi(),
							(beneForm.getProvincia() != null) ? beneForm.getProvincia().getId() : "");
					// ***** FINE INTERVENTO MEV_10 *****//
					
					if (allCab.size() > 0)
						modelMap.addAttribute("codiciCab", allCab);
					//nel caso in cui si sta modificando un bene ENTE FITTIZIO
					//valorizzo la combo del codice cab con i dati dell'ente fittizio
					else if (Constants.EnteFittizio.ABI.equals(beneForm.getCodiceAbi())) {
						
						String cab = Constants.EnteFittizio.CAB;
			            String descr = cab + " - " +Constants.EnteFittizio.INDIRIZZO + " " + Constants.EnteFittizio.INDIRIZZO + " (" + Constants.EnteFittizio.PROVINCIA + ")";
			            ComboForm cmbCab = new ComboForm(cab,descr);
			            allCab.add(cmbCab );
			            modelMap.addAttribute("codiciCab", allCab);
					}
				}

			}

			List<ComboForm> province = commonService.listaProvinceAbi(beneForm.getCodiceAbi());
			modelMap.addAttribute("provinceAbi", province);
		}
	}

	@RequestMapping(value = "/procedimento/bene/trasformaBene*.htm", method = RequestMethod.POST, params = {
			"Salva" })
	public String trasformaBene(BeneForm beneForm, BindingResult result, ModelMap modelMap) {

		beneValidation.validateInserisciBene(beneForm, result);
		ValidationUtil.requiredComboForm(beneForm.getTipoTrasformazione(), "tipoTrasformazione.id", result);
		if (result.hasErrors()) {
			// imposto i valori ad un bene finanziario se è di tipo finanziario
			impostaValoriBeneFinanziario(beneForm, modelMap);
			return trasformaBeneGenerico(beneForm, result, modelMap);
		}

		long idUffOwn = recuperaIdUfficioUtente();
		long idprocedimento = recuperaIdProcedimento();
		String codUtente = recuperaIdCodiceUtente();

		AttoForm attoForm = recuperaAtto();
		Long idAtto = attoForm.getCodAtto();
		long idBeneVecchio = beneForm.getIdBene();
		long idBene = beneService.trasformaBene(idUffOwn, codUtente, idprocedimento, idBeneVecchio, 0l,
				idAtto, beneForm);

		if (logger.isDebugEnabled()) {
			logger.debug("Trasformazione Bene - Nuovo Identificativo " + idBene);
		}

		List<Message> customMessageList = new ArrayList<Message>();
		super.addMessageIntoList(customMessageList, Constants.SUCCESS_MESSAGE, "success.operation",
				"TRASFORMAZIONE");
		super.addMessageIntoList(customMessageList, Constants.SUCCESS_MESSAGE, "bene.trasformazione.corretto",
				String.valueOf(idBene));
		super.setMessagesSuccess(customMessageList, true);

		return "redirect:/procedimento/bene/beniCollegatiAtto.htm";
	}

	// ***************** GESTIONE Annullamento BENE ****************//

	@RequestMapping(value = "/procedimento/bene/visualizzaBeniPerAnnullamento.htm", method = RequestMethod.GET)
	public ModelAndView visualizzaBeniPerAnnullamento(BeneForm beneForm, HttpServletRequest request) {

		// svuota i dati del bene //
		beneForm.setIdBene(null);
		beneForm.setIdUffOwner(null);
		beneForm.setIdTipologia(0);
		beneForm.setIdCategoria(0);
		beneForm.setIdSottoCategoria(0);
		// fine svuota dati del bene //

		if (request.getSession().getAttribute(Constants.ATTO) != null) {
			request.getSession().removeAttribute(Constants.ATTO);
		}

		long idProcedimento = recuperaIdProcedimento();
		Integer idUffOwner = recuperaIdUfficioUtente();
		ModelAndView mnv = new ModelAndView("/procedimento/bene/elencoBeniAnnullabili", "beneForm", beneForm);
		List<BeneForm> beniProcedimento = beneService.listaBeniPerProcedimento(0, idUffOwner, idProcedimento,
				beneForm);

		mnv.addObject("risultatiRicerca", beniProcedimento);

		addBreadcrumbs("GET", "/procedimento/bene/visualizzaBeniPerAnnullamento.htm", null);

		return mnv;
	}

	@RequestMapping(value = "/procedimento/bene/startAnnullaBene.htm", method = RequestMethod.POST, params = {
			"startAnnullaBene" })
	public ModelAndView startAnnullaBene(@ModelAttribute("beneForm") BeneForm beneForm, BindingResult result,
			HttpServletRequest request, ModelMap model) {

		// ProcedimentoForm procedimentoForm = (ProcedimentoForm)
		// request.getSession().getAttribute(Constants.ID_PROCEDIMENTO);

		long idProcedimento = recuperaIdProcedimento();

		// dati bene valido
		long idBeneValido = beneForm.getIdBene();
		int idUffOwnerValido = beneForm.getIdUffOwner();

		// dati bene duplicato
		long idBeneDuplicato = beneForm.getIdBeneDuplicato();
		int idUffOwnerDuplicato = beneForm.getIdUffOwnerDuplicato();

		BeneForm beneFormValido = beneService.beneById(idUffOwnerValido, idBeneValido, idProcedimento);

		BeneForm beneFormDuplicato = beneService.beneById(idUffOwnerDuplicato, idBeneDuplicato,
				idProcedimento);

		model.addAttribute("beneFormValido", beneFormValido);
		model.addAttribute("beneFormDuplicato", beneFormDuplicato);

		// Controllo che i campi obbligatori siano uguali
		beneValidation.validaBeneDaAnnullare(beneForm, beneFormValido, beneFormDuplicato, result);
		if (result.hasErrors()) {
			super.setMessage(Constants.MESSAGE, "errore.soggetto.annullabile.blocco", null, false);
		} else {
			request.setAttribute("abilitaConferma", "abilitaConferma");
		}

		ModelAndView mnv = new ModelAndView("/procedimento/bene/confermaBeniAnnullabili", "beneForm",
				beneForm);

		addBreadcrumbs("POST", "/procedimento/bene/startAnnullaBene.htm", null);

		return mnv;
	}
	/*****************************************
	 * FINE SENTENZA CASSAZION
	 *****************************************/

	/*
	 * @RequestMapping(value = "/procedimento/atto/decisorioBeniSentenzaCassazione.htm", method =
	 * RequestMethod.POST, params = { "SalvaDecisioneBeniSentenzaCassazione" }) public String
	 * salvaDecisorioBeniSentenzaCassazione(BeneSoggettoForm beneSoggettoForm, BindingResult result,
	 * HttpServletRequest request, ModelMap modelMap) {
	 * 
	 * List<BeneForm> beniSelezionati = beneSoggettoForm.estrapolaListaBeniConIdSoggettiCheckati();
	 * beneValidation.validateSalvaDecisorioBeni(beneSoggettoForm, beniSelezionati, request, result); if
	 * (result.hasErrors()) { return attoController.apriDecisiorioBeniSentenzaCassazione(beneSoggettoForm,
	 * result, request, modelMap); }
	 * 
	 * 
	 * String idCodiceUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE); long
	 * idCurrentAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto(); long
	 * idProcedimento = ((ProcedimentoForm)
	 * (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento(); int idUffOwn =
	 * (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
	 * 
	 * int idTipoUfficio = (Integer)request.getSession().getAttribute("idTipoUfficio");
	 * beneService.salvaDecisorioBeni(beneSoggettoForm, beniSelezionati, idCurrentAtto, idProcedimento,
	 * idCodiceUtente, idUffOwn, idTipoUfficio,"SENTENZA_CASSAZIONE"); return
	 * attoController.apriDecisiorioBeniSentenzaCassazione(beneSoggettoForm, result, request, modelMap); }
	 * 
	 * @RequestMapping(value = "/procedimento/atto/decisorioBeniSentenzaCassazione.htm", method =
	 * RequestMethod.GET) public String paginazioneDecisorioBeniSentenzaCassazione(BeneSoggettoForm
	 * beneSoggettoForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {
	 * 
	 * return attoController.apriDecisiorioBeniSentenzaCassazione(beneSoggettoForm, result, request,
	 * modelMap); }
	 */

	/********************************************
	 * FINE SENTENZA CASSAZIONE
	 ***************************************/

	private List<ComboForm> riempiComboVersato(String valore) {

		List<ComboForm> listaVersato = new ArrayList<ComboForm>();
		ComboForm combo = new ComboForm();

		if (valore != null && valore.equals("Modifica")) {
			combo.setId("1");
			combo.setDescrizione("C/C");
			listaVersato.add(combo);
			combo = new ComboForm();
			combo.setId("3");
			combo.setDescrizione("Libretto");
			listaVersato.add(combo);
			combo = new ComboForm();
			combo.setId("98");
			combo.setDescrizione("Libretto di Deposito giudiziario");
		} else {
			if (valore != null && valore.equals("1")) {
				combo.setId("1");
				combo.setDescrizione("C/C");
			}
			if (valore != null && valore.equals("3")) {
				combo.setId("3");
				combo.setDescrizione("Libretto");
			}
			if (valore != null && valore.equals("98")) {
				combo.setId("98");
				combo.setDescrizione("Libretto di Deposito giudiziario");
			}
		}
		listaVersato.add(combo);
		return listaVersato;
	}

	@RequestMapping(value = "/procedimento/bene/modificaBenePolizza.htm", method = RequestMethod.POST, params = {
	"Modifica" })
	public String modificaPolizza(BeneForm beneForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {
		
		List<Message> customMessageList = new ArrayList<Message>();
		Long idBene = null;
		
		//REQ-STM-019-02 controllo per i beni finanziari se il Numero registrazione e “Importo in euro”  è vuoto
		//vado a vedere se l'ente e l'iban sono vuoti e segnalo che almeno uno dei campi Importo e numero registrazione devono essere valorizzati
		//per i beni delle sotto-categorie diverse da Altro o Non-Definito 
		if ("4".equals(beneForm.getTipologia().getId()) && 
				(!"117".equals(beneForm.getSottocategoria().getId()) && 
				 !"99".equals(beneForm.getSottocategoria().getId())) ) {
			beneValidation.validateNumeroRegistrazioneImporto(beneForm, result, request);
			if (result.hasErrors()) {
				return dispatchInserimento(beneForm, "modifica");
			}
		}
		
		//REQ-STM-019-02
		beneForm = beneValidation.checkEnteFittizio(beneForm,result);
		
		//REQ-STM-019-02		
				
				beneValidation.validateInserimentoBeneFinanziario_Polizza(beneForm, result);
	// SITMP-204 Adeguamento applicativo dei campi che utilizzano T_BENE_FIN.DESC_REGISTRAZIONE - aggiunto controllo lato server sul numero polizza che sia numerico				
				beneValidation.validateNumeroPolizza(beneForm, result, request);
				if (result.hasErrors()) {
					return dispatchInserimento(beneForm, "modifica");
				}
		ProcedimentoForm procedimentoForm = recuperaProcedimento();
		long idUffOwn = recuperaIdUfficioUtente();
		long idprocedimento = procedimentoForm.getIdProcedimento();
		String codUtente = recuperaIdCodiceUtente();
//		beneService.modificaBenePolizza(idUffOwn, codUtente, idprocedimento, beneForm);
		beneService.modificaBene(idUffOwn, codUtente, idprocedimento, beneForm);
		idBene = beneForm.getIdBene();
		super.addMessageIntoList(customMessageList, Constants.SUCCESS_MESSAGE, "success.operation",
				"MODIFICA");
		super.addMessageIntoList(customMessageList, Constants.SUCCESS_MESSAGE, "bene.modifica.corretto",
				idBene != null ? String.valueOf(idBene) : null);
		super.setMessagesSuccess(customMessageList, true);
		
//		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation", "MODIFICA", false);
		BeneForm bene = recuperaDettaglioBene(beneForm);
		//F.C. MEV 2021/19  Inizio
		if (bene.getDescOperFinanz() != null &&  bene.getDescOperFinanz().getDescrizione() != null && bene.getDescOperFinanz().getDescrizione().equals(Constants.EnteFittizio.DESCRIZIONE)) {
			// imposto a blank i campi altrimenti vengono visualizzati in modifica
			bene.getDescOperFinanz().setDescrizione("");
			bene.setCodiceAbi("");
			bene.getCodiceCab().setDescrizione("");
			bene.getCodiceCab().setId("");
	
			bene.setProvinciaEntePolizza("");
			bene.setIndirizzoBeneFin("");
								
			//F.C. MEV 2021/19 
			//bene.setNumeroRegistrazione("");
		}
		/*else {
			List<ComboForm> province = commonService.listaProvinceAbi(beneForm.getCodiceAbi());
			modelMap.addAttribute("provinceAbi", province);	
		}
		*/
		//F.C. MEV 2021/19  fine
		modelMap.addAttribute("beneForm", bene);
		return "redirect:/procedimento/bene/gestioneBeniDelProcedimento.htm";
//		return "/procedimento/bene/modificaPolizza";
	}

	private String includeDettaglioNuovoBene(BeneForm bene, BindingResult result, ModelMap modelMap,
			HttpServletRequest request, String param) {
		bene.setDettAssBene(param);
		if (bene.getFlgVersamento() != null) {
			bene.setFlgVersamento(riempiComboVersato(bene.getFlgVersamento().getId()).get(0));
		}
		if ("Elimina".equals(param)) {
			request.setAttribute("isCancellazione", Boolean.TRUE);
		}
		modelMap.addAttribute("beneForm", bene);

		if (ComparatorUtil.containsValue(bene.getTipologia().getId(),
				Constants.TipologieBeni.ID_TIPO_FINANZIARIO_S, Constants.TipologieBeni.ID_TIPO_MOBILE_S)) {
			if (ComparatorUtil.containsValue(bene.getCategoria().getId(), Constants.CategoriaBeni.ID_POLIZZE,
					Constants.CategoriaBeni.ID_TIPO_DENARO_CONTANTE)) {
				if (bene.getSottocategoria() != null
						&& SottoCategoriaUtils.isPolizza(bene.getSottocategoria().getId())) {

					if ("Associa".equals(param)) {
						return "/procedimento/bene/dettaglioBene";
					}
					return "/procedimento/bene/dettaglioPolizza";
				} else if (bene.getSottocategoria() != null
						&& "206".equals(bene.getSottocategoria().getId())) {
					return "/procedimento/bene/dettaglioBene";
				}
			}
			if (!"Associa".equals(param) && !"Elimina".equals(param) && !"Dettaglio".equals(param)) {
				return "/procedimento/bene/inserimentoBeneFinanziario";
			}

		}

		// if (modelMap.get("comunicazioneBeniForm") == null) {
		// if ((request.getSession().getAttribute(Constants.ATTO) != null) && (((AttoForm)
		// (request.getSession().getAttribute(Constants.ATTO))).getCodAtto() > 0l)
		// && (!((AttoForm)
		// (request.getSession().getAttribute(Constants.ATTO))).getTipoAtto().getId().equals(Constants.TIPO_ATTO_PROPOSTA)))
		// {
		// return "/procedimento/bene/dettaglioBeneAtto";
		// }
		// }
		// se provengo dalla maschera dell'atto
		// inserisci diverso dall'atto proposta per l'intestazione dell'atto che veniva visualizzato comunque
		// anche
		// per i beni del procedimento nellaproposta

		// if ((request.getSession().getAttribute(Constants.ATTO) != null) && (((AttoForm)
		// (request.getSession().getAttribute(Constants.ATTO))).getCodAtto() > 0l)
		// && (!((AttoForm)
		// (request.getSession().getAttribute(Constants.ATTO))).getTipoAtto().getId().equals(Constants.TIPO_ATTO_PROPOSTA)))
		// {
		// return "/procedimento/bene/dettaglioBeneAtto";
		// }

		return "/procedimento/bene/dettaglioBene";
	}

	@RequestMapping(value = {
			"/procedimento/bene/dettaglioBene.htm" }, method = RequestMethod.POST, params = {
					"ImportaInPrevenzione" })
	public String importaNuovoBeneInPrevenzione(@Valid BeneForm beneForm, BindingResult result,
			HttpServletRequest request, ModelMap modelMap) {

		// long idProcedimento = ((ProcedimentoForm)
		// (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		int idUffOwner = (Integer) request.getSession().getAttribute("idUffOwner");
		String codUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);
		String tipo = "";
		String cat = "";
		String sottoCat = "";
		String decrizioneBene = "";
		long idDeposito = 0;

		if (beneForm.getTipologia() != null && beneForm.getTipologia().getDescrizione() != null)
			tipo = beneForm.getTipologia().getDescrizione();
		if (beneForm.getCategoria() != null && beneForm.getCategoria().getDescrizione() != null)
			cat = beneForm.getCategoria().getDescrizione();
		if (beneForm.getSottocategoria() != null && beneForm.getSottocategoria().getDescrizione() != null)
			sottoCat = beneForm.getSottocategoria().getDescrizione();
		if (beneForm.getDescrizioneBene() != null && !beneForm.getDescrizioneBene().isEmpty())
			decrizioneBene = beneForm.getDescrizioneBene();
		if (beneForm.getIdDeposito() != null && beneForm.getIdDeposito() > 0)
			idDeposito = beneForm.getIdDeposito();

		@SuppressWarnings("unchecked")
		List<BeneForm> listaNuoviBeni = (List<BeneForm>) request.getSession().getAttribute("listaNuoviBeni");
		for (int i = 0; i < listaNuoviBeni.size(); i++) {
			String tipologia = "";
			String categoria = "";
			String sottoCategoria = "";
			String descrizione = "";
			long deposito = 0;
			BeneForm form = listaNuoviBeni.get(i);
			if (form.getTipologia() != null && form.getTipologia().getDescrizione() != null)
				tipologia = form.getTipologia().getDescrizione();
			if (form.getCategoria() != null && form.getCategoria().getDescrizione() != null)
				categoria = form.getCategoria().getDescrizione();
			if (form.getSottocategoria() != null && form.getSottocategoria().getDescrizione() != null)
				sottoCategoria = form.getSottocategoria().getDescrizione();
			if (form.getDescrizioneBene() != null)
				descrizione = form.getDescrizioneBene();
			if (form.getIdDeposito() != null && form.getIdDeposito() > 0)
				deposito = form.getIdDeposito();
			if (idDeposito == deposito) {
				if (tipologia.equalsIgnoreCase(tipo) && categoria.equalsIgnoreCase(cat)
						&& sottoCategoria.equalsIgnoreCase(sottoCat)) {

					if (decrizioneBene.trim().equalsIgnoreCase(descrizione.trim())) {
						beneForm = form;
						break;
					}
				}
			}
		}

		// prendo procedimento di prevenzione
		TDepositiAmm depositoAmm = flussiDao.getDettaglioDepositoAmm(beneForm.getIdDeposito(), 'S');
		long idProc = depositoAmm.getTProcedimentoByCodiFkIdProc().getCodiKIdProc();

		List<Message> customMessageList = new ArrayList<Message>();

		try {
			long idBeneInserito = beneService.inserisciBene(idUffOwner, codUtente, idProc, beneForm);
			if (idBeneInserito > 0) {

				// associare bene con idProcedimento prevenzione ???
				// beneService.associaBeneAlProcedimento(idBeneInserito, idUffOwner, codUtente, idProc,
				// statoBene);

				flussiService.aggiornaXML(beneForm, idBeneInserito);

				super.addMessageIntoList(customMessageList, Constants.SUCCESS_MESSAGE, "success.operation",
						"INSERIMENTO");
				super.setMessagesSuccess(customMessageList, true);

				beneForm.setWarning("ok");
				modelMap.addAttribute("beneForm", beneForm);

			}
		} catch (Exception e) {
			e.printStackTrace();
			super.addMessageIntoList(customMessageList, Constants.MESSAGE, "operation.error", "INSERIMENTO");
			super.setMessages(customMessageList, true);
			beneForm.setWarning("ko");
			modelMap.addAttribute("beneForm", beneForm);
		}

		return includeDettaglioNuovoBene(beneForm, result, modelMap, request, "Dettaglio");
	}

	@RequestMapping(value = "/procedimento/bene/dettaglioBene.htm", method = RequestMethod.POST, params = {
			"DettaglioDatiDellAmministratore" })
	public String dettaglioDatiAmministratoreNuovoBene(BeneForm beneForm, BindingResult result,
			HttpServletRequest request, ModelMap modelMap) {
		// long idProcedimento = ((ProcedimentoForm)
		// (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		// int idUffOwner = (Integer) request.getSession().getAttribute("idUffOwner");

		// lettura XML dati gestione del DEPOSITO_AMM
		Blob blobXml = flussiService.getXMLFileDeposito(beneForm.getIdDeposito());

		String tipo = "";
		String cat = "";
		String sottoCat = "";
		String decrizioneBene = "";

		if (beneForm.getTipologia() != null && beneForm.getTipologia().getDescrizione() != null)
			tipo = beneForm.getTipologia().getDescrizione();
		if (beneForm.getCategoria() != null && beneForm.getCategoria().getDescrizione() != null)
			cat = beneForm.getCategoria().getDescrizione();
		if (beneForm.getSottocategoria() != null && beneForm.getSottocategoria().getDescrizione() != null)
			sottoCat = beneForm.getSottocategoria().getDescrizione();
		if (beneForm.getDescrizioneBene() != null && !beneForm.getDescrizioneBene().isEmpty())
			decrizioneBene = beneForm.getDescrizioneBene();

		try {
			InputStream inStream = blobXml.getBinaryStream();
			InputStreamReader inStreamReader = new InputStreamReader(inStream);
			Reader reader = new BufferedReader(inStreamReader);

			JAXBContext jaxbContext = JAXBContext.newInstance(RicezioneRelazione.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			RicezioneRelazione relazione = (RicezioneRelazione) unmarshaller.unmarshal(reader);

			// legge il bene
			List<BeneExtType> beni = relazione.getBene();
			for (BeneExtType bene : beni) {
				String tipologia = "";
				String categoria = "";
				String sottocategoria = "";

				if (bene.getTipologia() != null)
					tipologia = bene.getTipologia().value();
				if (bene.getCategoria() != null)
					categoria = bene.getCategoria().value();
				if (bene.getSottoCategoria() != null)
					sottocategoria = bene.getSottoCategoria().value();

				if (tipologia.equalsIgnoreCase(tipo) && categoria.equalsIgnoreCase(cat)
						&& sottocategoria.equalsIgnoreCase(sottoCat)) {
					String descrBene = flussiService.getBeneDescr(bene);
					if (decrizioneBene.equalsIgnoreCase(descrBene)) {

						ToolAmmMapper.map(bene, beneForm);
						// beneForm.setDatiSpecificiBene(bene.getDatiSpecificiBene());

						break;
					}
				}
			}
		} catch (JAXBException e) {
			// super.setMessage(Constants.MESSAGE, "error.defaultError", null, true);
			return includeDettaglioNuovoBene(beneForm, result, modelMap, request, "Dettaglio");
		} catch (SQLException e) {
			// super.setMessage(Constants.MESSAGE, "error.defaultError", null, true);
			return includeDettaglioNuovoBene(beneForm, result, modelMap, request, "Dettaglio");
		}

		addBreadcrumbs("POST", "/procedimento/bene/dettaglioBene.htm", "DettaglioDatiDellAmministratore");
		modelMap.addAttribute("beneForm", beneForm);
		request.getSession().setAttribute("DatiSpecificiBene", beneForm.getDatiSpecificiBene());

		return "/procedimento/bene/amministratore/dettaglioDatiAmmBene";
	}

	// P.Q. Inizio MEV 2021/19 Operatore Economico Estero	
	private boolean checkOperatoreEconomicoEstero(BeneForm beneForm, ModelMap modelMap) {		
		if (beneForm.getDescOperFinanz() != null && 
		    beneForm.getDescOperFinanz().getDescrizione() != null && 
		    beneForm.getDescOperFinanz().getDescrizione().equals(Constants.OperatoreEstero.DESCRIZIONE)) {
						
			beneForm.setDescrizioneEnte(beneForm.getDescOperFinanz().getDescrizione());			
			modelMap.addAttribute("isOperatoreEconomicoEstero", true);			
			
			beneForm.setCodiceAbi(Constants.OperatoreEstero.ABI);
			beneForm.setAbi(Constants.OperatoreEstero.ABI);
			
			beneForm.setDisabledProvincia(false);
			
			List<ComboForm> province = commonService.listaProvinceAbi(Constants.OperatoreEstero.ABI);
			modelMap.addAttribute("provinceAbi", province);
			// Ed imposto il valore precedente come default nella pagina
			beneForm.setProvincia(new ComboForm(Constants.OperatoreEstero.PROVINCIA , "N.D."));
			//beneForm.setProvinciaSel(Constants.OperatoreEstero.PROVINCIA);			
					
			// Carico il menu a tendina con tutti i CAB associati all'ABI ed alla provincia relativi
			List<ComboForm> allCab = commonService.listaCodiciCab(beneForm.getCodiceAbi(), beneForm.getProvincia().getId());
			modelMap.addAttribute("codiciCab", allCab);
			
			beneForm.setCodiceCab(new ComboForm(Constants.OperatoreEstero.CAB , Constants.OperatoreEstero.CAB));
			
			beneForm.setNumeroRegistrazione("0");			
			
			return true;
			
		}							
		return false;		
	}
	// P.Q. Fine MEV 2021/19 Operatore Economico Estero
	
	/*
	 * GC/PQ 29/08/2024: TT#202407020117
	 * Creato un metodo per la verifica del campo numRegistrazione
	 */
	private BindingResult chkNumReg(BeneForm beneForm,BindingResult result, HttpServletRequest request) {
		/*  GC 28/08/2024: TT#202407020117
		 * Modificata la chiamata per distinguere i beni finanziari dagli altri
		 */
	
		if ("4".equals(beneForm.getTipologia().getId()) && (!"117".equals(beneForm.getSottocategoria().getId())
				&& !"99".equals(beneForm.getSottocategoria().getId()))) {
			// aggiunto controllo lato server sul numero registrazione che sia alfanumerico
			beneValidation.validateNumeroRegistrazioneBeneFin(beneForm, result, request);
		} else {
			// aggiunto controllo lato server sul numero registrazione che sia numerico
			beneValidation.validateNumeroRegistrazione(beneForm, result, request);
		}
		return result;
		
		/*
		if (result.hasErrors())
		{
			return false;
		}else {
			return true;
		}
		*/
	}
}