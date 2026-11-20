package it.giustizia.smp.controller.amministrazione;

import it.giustizia.smp.controller.BreadcrumbsController;
import it.giustizia.smp.controller.MenuController;
import it.giustizia.smp.form.CategoriaCatastaleForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.ComuneForm;
import it.giustizia.smp.form.CondizioneGiuridicaForm;
import it.giustizia.smp.form.ConservatoriaForm;
import it.giustizia.smp.form.IstitutoDetenzioneForm;
import it.giustizia.smp.form.MagistratoForm;
import it.giustizia.smp.form.NumeroRegistroForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.ProfessioneForm;
import it.giustizia.smp.form.ProfiloForm;
import it.giustizia.smp.form.ProvinciaForm;
import it.giustizia.smp.form.SettoreAttivitaForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.form.StatoForm;
import it.giustizia.smp.form.UfficiForm;
import it.giustizia.smp.jms.JmsMessage;
import it.giustizia.smp.jms.JmsMessageProducer;
import it.giustizia.smp.model.TProcedimento;
import it.giustizia.smp.model.TRelMagProc;
import it.giustizia.smp.multidb.UserContextHolder;
import it.giustizia.smp.service.AmministrazioneService;
import it.giustizia.smp.service.CommonService;
import it.giustizia.smp.service.ProcedimentoService;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.utility.UtilityLogger;
import it.giustizia.smp.validation.AmministrazioneValidation;
import it.giustizia.smp.validation.MagistratoValidation;
import it.giustizia.smp.validation.ProcedimentoValidation;
import it.giustizia.smp.webservice.client.documentale.stub.Configuration;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
public class AmministrazioneController extends BreadcrumbsController{
	
	@Autowired
	private AmministrazioneService 		amministrazioneService;
	@Autowired
	private CommonService 				commonService;
	@Autowired
	private MagistratoValidation		magistratoValidation;
	@Autowired
	private AmministrazioneValidation 	amministrazioneValidation;
	@Autowired
	private ProcedimentoValidation	procedimentoValidation;
	@Autowired
	private ProcedimentoService		procedimentoService;
//	@Autowired
	private JmsMessage jmsMessage;
//	@Autowired
	private JmsMessageProducer jmsService;
	


	public JmsMessage getJmsMessage() {
		return jmsMessage;
	}

	public void setJmsMessage(JmsMessage jmsMessage) {
		this.jmsMessage = jmsMessage;
	}

	public JmsMessageProducer getJmsService() {
		return jmsService;
	}

	public void setJmsService(JmsMessageProducer jmsService) {
		this.jmsService = jmsService;
	}
	
	/******* Inserimento di un nuovo magistrato in base dati *********/
	
	@RequestMapping(value = "/funzioniAmministrazione/magistrati.htm", method = RequestMethod.GET)
	public ModelAndView amministrazioneMagistrato(ModelMap modelMap) {
		cleanBreadcrumb();
		MagistratoForm magistratoForm = new MagistratoForm();
		ModelAndView mnv = new ModelAndView("/amministrazione/ricercaMagistrato", "magistratoForm", magistratoForm);
		
		return mnv;
	}

	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaMagistrato.htm" , method = RequestMethod.POST, params = { "Ricerca" })
	public String ricercaMagistrato(MagistratoForm magistratoForm, BindingResult result, ModelMap modelMap) {
		String cognome = magistratoForm.getCognome()==null ? null : magistratoForm.getCognome().toUpperCase();
		String nome = magistratoForm.getNome()==null ? null : magistratoForm.getNome().toUpperCase();
		Date dataNascita = magistratoForm.getDataNascita()==null ? null : magistratoForm.getDataNascita();
		Integer matricola = magistratoForm.getNumeMatricola()==null ? null : magistratoForm.getNumeMatricola();  
		
		List<MagistratoForm> listaMagistratoForm = amministrazioneService.listaMagistrati(cognome, nome, dataNascita, matricola);
		if(listaMagistratoForm == null)
			listaMagistratoForm = new ArrayList<MagistratoForm>();
		modelMap.addAttribute("risultatiRicerca", listaMagistratoForm);
		return "/amministrazione/ricercaMagistrato";
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaMagistrato.htm" , method = RequestMethod.GET)
	public String ricercaMagistratoPaginazione(MagistratoForm magistratoForm, BindingResult result, ModelMap modelMap) {
		return ricercaMagistrato(magistratoForm, result, modelMap);
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaMagistrato.htm" , method = RequestMethod.POST, params = { "NuovoMagistrato" })
	public String nuovoMagistrato(MagistratoForm magistratoForm, BindingResult result, ModelMap modelMap) {
		modelMap.addAttribute("magistratoForm", magistratoForm);
		return "/amministrazione/nuovoMagistrato";
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/nuovoMagistrato.htm" , method = RequestMethod.POST, params = { "Indietro" })
	public String indietroNuovoMagistrato(MagistratoForm magistratoForm, BindingResult result, ModelMap modelMap) {
		return ricercaMagistrato(magistratoForm, result, modelMap);
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/nuovoMagistrato.htm" , method = RequestMethod.POST, params = { "Inserisci" })
	public String inserisciMagistrato(MagistratoForm magistratoForm, BindingResult result, ModelMap modelMap) {
		magistratoValidation.validateInserimento(magistratoForm, result);
		if (result.hasErrors()) {
			return "/amministrazione/nuovoMagistrato";
		}
		String descUtente = (recuperaIdCodiceUtente().toString());
		amministrazioneService.inserisciMagistrato(magistratoForm.getCognome(), magistratoForm.getNome(), magistratoForm.getDataNascita(), magistratoForm.getNumeMatricola(), descUtente);
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", " ", true);
		return ricercaMagistrato(magistratoForm, result, modelMap);
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaMagistrato.htm" , method = RequestMethod.POST, params = { "ModificaMagistrato" })
	public String modificaMagistrato(MagistratoForm magistratoForm, BindingResult result, ModelMap modelMap) {
		if(magistratoForm.getId() == null)
		{
			super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
			return ricercaMagistrato(magistratoForm, result, modelMap);
		}
		magistratoForm = amministrazioneService.getMagistrato(magistratoForm.getId());
		modelMap.addAttribute("magistratoForm", magistratoForm);
		return "/amministrazione/modificaMagistrato";
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaMagistrato.htm" , method = RequestMethod.POST, params = { "SalvaModifica" })
	public String salvaModificaMagistrato(MagistratoForm magistratoForm, BindingResult result, ModelMap modelMap) {
		amministrazioneService.modificaMagistrato(magistratoForm);
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", " ", true);
		return ricercaMagistrato(magistratoForm, result, modelMap);
	}
	/******* END Inserimento di un nuovo magistrato in base dati *********/
	
	/***** Associazione magistrato all'ufficio **********/
	@RequestMapping(value = "/funzioniAmministrazione/associaMagistrato.htm", method = RequestMethod.GET)
	public ModelAndView associazioneMagistrato(ModelMap modelMap) {
		MagistratoForm magistratoForm = new MagistratoForm();
		ModelAndView mnv = new ModelAndView("/amministrazione/associaMagistrato", "magistratoForm", magistratoForm);
		
		return mnv;
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaAssociaMagistrato.htm" , method = RequestMethod.POST, params = { "Ricerca" })
	public String listaAssociaMagistrato(MagistratoForm magistratoForm, BindingResult result, ModelMap modelMap) {
		String cognome = magistratoForm.getCognome()==null ? null : magistratoForm.getCognome().toUpperCase();
		String nome = magistratoForm.getNome()==null ? null : magistratoForm.getNome().toUpperCase();
		Integer matricola = magistratoForm.getNumeMatricola()==null ? null : magistratoForm.getNumeMatricola(); 
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
		
		List<MagistratoForm> listaMagistratoForm = amministrazioneService.listaMagistratiNonAssociati(idUfficioUtente,cognome, nome, matricola);
		if(listaMagistratoForm == null)
			listaMagistratoForm = new ArrayList<MagistratoForm>();
		modelMap.addAttribute("risultatiRicerca", listaMagistratoForm);
		return "/amministrazione/associaMagistrato";
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaAssociaMagistrato.htm" , method = RequestMethod.GET)
	public String associaMagistratoPaginazione(MagistratoForm magistratoForm, BindingResult result, ModelMap modelMap) {
		return listaAssociaMagistrato(magistratoForm, result, modelMap);
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaAssociaMagistrato.htm" , method = RequestMethod.POST, params = { "AssociaMagistrato" })
	public String associaMagistrato(@Valid MagistratoForm magistratoForm, BindingResult result, ModelMap modelMap) {
		if(magistratoForm != null && magistratoForm.getId()== null){
			super.setMessage(Constants.MESSAGE, "magistrato.error.selezione", null, false);
			result.reject(Constants.MESSAGE);
		}
		else{
			Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
			String descUtente = (recuperaIdCodiceUtente().toString());
			amministrazioneService.associaMagistrato(magistratoForm.getId(), idUfficioUtente, descUtente);
			magistratoForm.setId(null);
			super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", " ", true);
		}
		return listaAssociaMagistrato(magistratoForm,result,modelMap);
	}
	
	/***** END Associazione magistrato all'ufficio **********/

	
	/******* Amministrazione Stato in base dati *********/
	@RequestMapping(value = "/funzioniAmministrazione/stati.htm", method = RequestMethod.GET)
	public ModelAndView amministrazioneStato(ModelMap modelMap) {
		cleanBreadcrumb();
		StatoForm statoForm = new StatoForm();
		ModelAndView mnv = new ModelAndView("/amministrazione/ricercaStato", "statoForm", statoForm);
		
		return mnv;
	}

	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaStato.htm" , method = RequestMethod.POST, params = { "Ricerca" })
	public String ricercaStato(StatoForm statoForm, BindingResult result, ModelMap modelMap) {
		String descStato = statoForm.getDescStato()== null ? null : statoForm.getDescStato().toUpperCase();
		String descSiglaStato = statoForm.getDescSiglaStato()== null ? null : statoForm.getDescSiglaStato().toUpperCase();
		String descIstatStato = statoForm.getDescIstatStato()== null ? null : statoForm.getDescIstatStato();  
		
		List<StatoForm> listaStatoForm = amministrazioneService.listaStati(descStato, descSiglaStato, descIstatStato);
		if(listaStatoForm == null)
			listaStatoForm = new ArrayList<StatoForm>();
		modelMap.addAttribute("risultatiRicerca", listaStatoForm);
		addBreadcrumbs("/funzioniAmministrazione/visualizzaRicercaStato.htm");
		return "/amministrazione/ricercaStato";
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaStato.htm" , method = RequestMethod.GET)
	public String ricercaStatoPaginazione(StatoForm statoForm, BindingResult result, ModelMap modelMap) {
		return ricercaStato(statoForm, result, modelMap);
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaStato.htm" , method = RequestMethod.POST, params = { "NuovoStato" })
	public String nuovoStato(StatoForm statoForm, BindingResult result, ModelMap modelMap) {
		modelMap.addAttribute("statoForm", statoForm);
		return "/amministrazione/nuovoStato";
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/nuovoStato.htm" , method = RequestMethod.POST, params = { "Inserisci" })
	public String inserisciStato(StatoForm statoForm, BindingResult result, ModelMap modelMap) {
		amministrazioneValidation.validateStatoInserimento(statoForm, result);
		if (result.hasErrors()) {
			return "/amministrazione/nuovoStato";
		}
		
		addBreadcrumbs("/funzioniAmministrazione/stati.htm");
		String descUtente = (recuperaIdCodiceUtente().toString());
		Integer idStato = amministrazioneService.inserisciStato(statoForm.getDescIstatStato(), statoForm.getDescSiglaStato(), statoForm.getDescStato(), statoForm.getDataFineValidita(), descUtente);
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", " ", true);

		return ricercaStato(statoForm,result,modelMap);
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/nuovoStato.htm" , method = RequestMethod.POST, params = { "Indietro" })
	public String indietroRicercaStato(StatoForm statoForm, BindingResult result, ModelMap modelMap) {

		return ricercaStato(statoForm,result,modelMap);
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaStato.htm" , method = RequestMethod.POST, params = { "ModificaStato" })
	public String modificaStato(StatoForm statoForm, BindingResult result, ModelMap modelMap) {
		if(statoForm.getCodiKIdStato() == 0)
		{
			super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
			return ricercaStato(statoForm,result,modelMap);
		}
		statoForm = amministrazioneService.getStato(statoForm.getCodiKIdStato());
		modelMap.addAttribute("statoForm", statoForm);
		return "/amministrazione/modificaStato";
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaStato.htm" , method = RequestMethod.POST, params = { "SalvaModifica" })
	public String salvaModificaStato(StatoForm statoForm, BindingResult result, ModelMap modelMap) {
		
		amministrazioneValidation.validateStatoInserimento(statoForm, result);
		if (result.hasErrors()) {
			return "/amministrazione/modificaStato";
		}
		
		amministrazioneService.modificaStato(statoForm);
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", " ", true);
		return "/amministrazione/ricercaStato";
	}
	/******* END Amministrazione Stato in base dati *********/
	
	/******* Amministrazione Provincia in base dati *********/
	@RequestMapping(value = "/funzioniAmministrazione/province.htm", method = RequestMethod.GET)
	public ModelAndView amministrazioneProvincia(ModelMap modelMap) {
		ProvinciaForm provinciaForm = new ProvinciaForm();
		ModelAndView mnv = new ModelAndView("/amministrazione/ricercaProvincia", "provinciaForm", provinciaForm);
		
		return mnv;
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaProvincia.htm" , method = RequestMethod.POST, params = { "Ricerca" })
	public String ricercaProvincia(ProvinciaForm provinciaForm, BindingResult result, ModelMap modelMap) {
		String descProvincia = provinciaForm.getDescProvincia()== null ? null : provinciaForm.getDescProvincia().toUpperCase();
		String descSiglaProvincia = provinciaForm.getDescSiglaProvincia()== null ? null : provinciaForm.getDescSiglaProvincia().toUpperCase();
		
		List<ProvinciaForm> listaProvinciaForm = amministrazioneService.listaProvince(descProvincia, descSiglaProvincia);
		if(listaProvinciaForm == null)
			listaProvinciaForm = new ArrayList<ProvinciaForm>();
		modelMap.addAttribute("risultatiRicerca", listaProvinciaForm);
		return "/amministrazione/ricercaProvincia";
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaProvincia.htm" , method = RequestMethod.GET)
	public String ricercaProvinciaPaginazione(ProvinciaForm provinciaForm, BindingResult result, ModelMap modelMap) {
		return ricercaProvincia(provinciaForm, result, modelMap);
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaProvincia.htm" , method = RequestMethod.POST, params = { "NuovaProvincia" })
	public String nuovaProvincia(ProvinciaForm provinciaForm, BindingResult result, ModelMap modelMap) {
		modelMap.addAttribute("provinciaForm", provinciaForm);
		return "/amministrazione/nuovaProvincia";
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/nuovaProvincia.htm" , method = RequestMethod.POST, params = { "Indietro" })
	public String indietroRicercaProvincia(ProvinciaForm provinciaForm, BindingResult result, ModelMap modelMap) {

		return ricercaProvincia(provinciaForm,result,modelMap);
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/nuovaProvincia.htm" , method = RequestMethod.POST, params = { "Inserisci" })
	public String inserisciProvincia(ProvinciaForm provinciaForm, BindingResult result, ModelMap modelMap) {
		amministrazioneValidation.validateProvinciaInserimento(provinciaForm, result);
		if (result.hasErrors()) {
			return "/amministrazione/nuovaProvincia";
		}
		String descUtente = (recuperaIdCodiceUtente().toString());
		amministrazioneService.inserisciProvincia(provinciaForm.getCodiKIdIstatProv(), provinciaForm.getDescSiglaProvincia(), provinciaForm.getDescProvincia(), provinciaForm.getDataFineValidita(), descUtente);
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", " ", true);
		return ricercaProvincia(provinciaForm,result,modelMap);
	}

	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaProvincia.htm" , method = RequestMethod.POST, params = { "ModificaProvincia" })
	public String modificaProvincia(ProvinciaForm provinciaForm, BindingResult result, ModelMap modelMap) {
		if(provinciaForm.getCodiKIdIstatProv() == null)	{
			super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
			return ricercaProvincia(provinciaForm, result, modelMap);
		}
		
		provinciaForm = amministrazioneService.getProvincia(provinciaForm.getCodiKIdIstatProv());
		modelMap.addAttribute("provinciaForm", provinciaForm);
		return "/amministrazione/modificaProvincia";
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaProvincia.htm" , method = RequestMethod.POST, params = { "SalvaModifica" })
	public String salvaModificaProvincia(ProvinciaForm provinciaForm, BindingResult result, ModelMap modelMap) {
		
		amministrazioneValidation.validateProvinciaInserimento(provinciaForm, result);
		
		if (result.hasErrors()) {
			return "/amministrazione/modificaProvincia";
		}
		
		amministrazioneService.modificaProvincia(provinciaForm);
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", " ", true);
		return "/amministrazione/ricercaProvincia";
	}
	
	/******* END Amministrazione Provincia in base dati *********/
	
	@RequestMapping(value = "/funzioniAmministrazione/sostituzioneMagistrati.htm", method = RequestMethod.GET)
	public ModelAndView sostituzioneMagistrati(ModelMap modelMap) {
		MagistratoForm magistratoForm = new MagistratoForm();
		ModelAndView mnv = new ModelAndView("/amministrazione/sostituzioneMagistrati", "magistratoForm", magistratoForm);
		return mnv;
	}

	@RequestMapping(value = "/funzioniAmministrazione/sostituzioneMagistrati.htm", method = RequestMethod.POST, params = { "Annulla" })
	public ModelAndView sostituzioneMagistratiPost(ModelMap modelMap) {
		MagistratoForm magistratoForm = new MagistratoForm();
		ModelAndView mnv = new ModelAndView("/amministrazione/sostituzioneMagistrati", "magistratoForm", magistratoForm);
		return mnv;
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/sostituzioneMagistrati.htm", method = RequestMethod.POST, params = { "MAGISTRATIPM" })
	public ModelAndView sostituzioneMagistratiPM(MagistratoForm magistratoForm, BindingResult result, ModelMap modelMap) {
		magistratoForm.setTipoMagistrato("MAGISTRATIPM");
		magistratoForm.setCheckProcedimenti("T");
		return sostituzioneMagistratiDettaglio(magistratoForm, result, modelMap);
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/sostituzioneMagistrati.htm", method = RequestMethod.POST, params = { "MAGISTRATICOLLEGIO" })
	public ModelAndView sostituzioneMagistratiCollegio(MagistratoForm magistratoForm, BindingResult result, ModelMap modelMap) {
		magistratoForm.setTipoMagistrato("MAGISTRATICOLLEGIO");
		magistratoForm.setCheckProcedimenti("T");
		return sostituzioneMagistratiDettaglio(magistratoForm, result, modelMap);
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/sostituzioneMagistrati.htm", method = RequestMethod.POST, params = { "GIUDICEDELEGATO" })
	public ModelAndView sostituzioneGiudiceDelegato(MagistratoForm magistratoForm, BindingResult result, ModelMap modelMap) {
		magistratoForm.setTipoMagistrato("GIUDICEDELEGATO");
		magistratoForm.setCheckProcedimenti("T");
		return sostituzioneMagistratiDettaglio(magistratoForm, result, modelMap);
	}	
	
	@RequestMapping(value = "/funzioniAmministrazione/sostituzioneMagistratiDettaglio.htm", method = RequestMethod.GET)
	public ModelAndView sostituzioneMagistratiGestioneListaProcedimenti(MagistratoForm magistratoForm, BindingResult result, ModelMap modelMap, HttpServletRequest request) {
		return sostituzioneMagistratiDettaglio(magistratoForm, result, modelMap);
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/sostituzioneMagistrati.htm", method = RequestMethod.POST)
	public ModelAndView sostituzioneMagistratiDettaglio(MagistratoForm magistratoForm, BindingResult result, ModelMap modelMap) {
		ModelAndView mnv = new ModelAndView("/amministrazione/sostituzioneMagistrati", "magistratoForm", magistratoForm);
		
		int idUffOwn = recuperaIdUfficioUtente();
		
		String tipoMagistrato = magistratoForm.getTipoMagistrato();
		if(magistratoForm.getDataFineAssegnazione()==null) {
			result.rejectValue("dataFineAssegnazione", "NotNull");
			magistratoForm.setTipoMagistrato(null);
		}  else {
			int idTipoUfficio = (Integer) request.getSession().getAttribute("idTipoUfficio");
			List<ComboForm> magistratiDaSostituire = amministrazioneService.listaMagistratiSostituzione(tipoMagistrato, idUffOwn, idTipoUfficio, magistratoForm.getDataFineAssegnazione());
			List<ComboForm> magistratiSubentranti = amministrazioneService.listaMagistratiSubentro(tipoMagistrato, idUffOwn, idTipoUfficio, magistratoForm.getDataFineAssegnazione());
			ComboForm a = new ComboForm("Incompatibilità", "Incompatibilità");
			ComboForm b = new ComboForm("Trasferimento", "Trasferimento"); 
			ComboForm c = new ComboForm("Altro","Altro");
			List<ComboForm> motiviSostituzione = new ArrayList<ComboForm>();
			motiviSostituzione.add(a);
			motiviSostituzione.add(b);
			motiviSostituzione.add(c);

			if(magistratoForm.getMagistratoDaSostituire()!=null && !magistratoForm.getMagistratoDaSostituire().equals(Constants.NO_ITEM_SELECTED)) {
				long idMagistratoDaSostituire = Long.valueOf(magistratoForm.getMagistratoDaSostituire().getId());
				List<TRelMagProc> procedimentiMagistratoSostituito = amministrazioneService.listaProcedimentiDettaglioMagistrato(idMagistratoDaSostituire, idUffOwn, idTipoUfficio, magistratoForm.getDataFineAssegnazione());
				modelMap.addAttribute("procedimentiMagistratoSostituito", procedimentiMagistratoSostituito);
			}
			modelMap.addAttribute("magistratiDaSostituire", magistratiDaSostituire);
			modelMap.addAttribute("magistratiSubentranti", magistratiSubentranti); 
			modelMap.addAttribute("motivazioniSostituzione", motiviSostituzione);
		}
		
		return mnv;
	}	
	
	@RequestMapping(value = "/funzioniAmministrazione/sostituzioneMagistrati.htm", method = RequestMethod.POST, params = { "Conferma" })
	public ModelAndView sostituzioneMagistratiConferma(MagistratoForm magistratoForm, BindingResult result, ModelMap modelMap) {

		List<TProcedimento> procedimentiNonAggiornati = new ArrayList<TProcedimento>();
		String tipoMagistrato = magistratoForm.getTipoMagistrato();
		boolean ret = magistratoValidation.sostituzioneMagistratiConferma(magistratoForm, result, tipoMagistrato);
		
		if(!ret) {
			int idUffOwn = recuperaIdUfficioUtente();
			int idTipoUfficio = (Integer) request.getSession().getAttribute("idTipoUfficio");
			Short annoDa = magistratoForm.getProcedimentoDaAnno();
			Long numDa = magistratoForm.getProcedimentoDaNum();
			Short annoA = magistratoForm.getProcedimentoAAnno();
			Long numA = magistratoForm.getProcedimentoANum();
			
			long idMagistratoDaSostituire = Long.valueOf(magistratoForm.getMagistratoDaSostituire().getId());
			List<TRelMagProc> listaProcedimenti = amministrazioneService.listaProcedimentiSostituzione(tipoMagistrato, idMagistratoDaSostituire, idUffOwn, idTipoUfficio, magistratoForm.getDataFineAssegnazione(), annoDa, numDa, annoA, numA);
			if(listaProcedimenti!=null) {
				Iterator<TRelMagProc> iter = listaProcedimenti.iterator();
				while(iter.hasNext()) {
					TRelMagProc magProc = iter.next();
					Long idProcedimento = magProc.getTProcedimento().getCodiKIdProc();
					String descUtente = (recuperaIdCodiceUtente().toString());
					TProcedimento procedimentoNonAggiornato = amministrazioneService.sostituisciMagistrato(magistratoForm, idUffOwn, idProcedimento, descUtente);
					if(procedimentoNonAggiornato!=null) {
						procedimentiNonAggiornati.add(procedimentoNonAggiornato);
					}
				}
				modelMap.addAttribute("procedimentiNonAggiornati", procedimentiNonAggiornati);
				super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", null, false);
				
			} else {
				super.setMessage(Constants.MESSAGE, "magistrato.error.nessunProcedimentoAggiornato", null, false);
				result.reject(Constants.MESSAGE);
			}
			
		}
		
		return sostituzioneMagistratiDettaglio(magistratoForm, result, modelMap);
	}

	/******* INIZIO Amministrazione Comuni in base dati *********/
	
	@RequestMapping(value = "/funzioniAmministrazione/comuni.htm", method = RequestMethod.GET)
	public ModelAndView amministrazioneComuni(ModelMap modelMap) {
		ComuneForm comuneForm = new ComuneForm();
		
		ModelAndView mnv = new ModelAndView("/amministrazione/ricercaComune", "comuneForm", comuneForm);
		return mnv;
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaComuni.htm" , method = RequestMethod.POST, params = { "Ricerca" })
	public String ricercaComuni(ComuneForm comuneForm, BindingResult result, ModelMap modelMap) {
		String descComune = comuneForm.getDescComune() == null ? null : comuneForm.getDescComune().toUpperCase();
		String capComune = comuneForm.getDescCapComune() == null ? null : comuneForm.getDescCapComune().toUpperCase();
		String istatComune = comuneForm.getDescIstatComune() == null ? null : comuneForm.getDescIstatComune().toUpperCase();
		String codBelfioreComune = comuneForm.getDescBelfiore() == null ? null : comuneForm.getDescBelfiore().toUpperCase();
		String codCatastoComune = comuneForm.getDescCatasto() == null ? null : comuneForm.getDescCatasto().toUpperCase();
		
		List<ComuneForm> listaComuniForm = amministrazioneService.listaComuni(descComune,capComune, istatComune, codBelfioreComune, codCatastoComune );
		if(listaComuniForm == null)
			listaComuniForm = new ArrayList<ComuneForm>();
		modelMap.addAttribute("risultatiRicerca", listaComuniForm);
		return "/amministrazione/ricercaComune";
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaComuni.htm" , method = RequestMethod.GET)
	public String ricercaComuniPaginazione(ComuneForm comuneForm, BindingResult result, ModelMap modelMap) {
		return ricercaComuni(comuneForm, result, modelMap);
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaComuni.htm" , method = RequestMethod.POST, params = { "NuovoComune" })
	public String nuovoComune(ComuneForm comuneForm, BindingResult result, ModelMap modelMap) {
		
		List<ComboForm> elencoProvince = commonService.getAllProvincia();
		modelMap.addAttribute("elencoProvince", elencoProvince);
		modelMap.addAttribute("comuneForm", comuneForm);
		return "/amministrazione/nuovoComune";
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/nuovoComune.htm" , method = RequestMethod.POST, params = { "Indietro" })
	public String indietroRicercaComune(ComuneForm comuneForm, BindingResult result, ModelMap modelMap) {

		return ricercaComuni(comuneForm,result,modelMap);
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/nuovoComune.htm" , method = RequestMethod.POST, params = { "Inserisci" })
	public String inserisciComune(ComuneForm comuneForm, BindingResult result, ModelMap modelMap) {
		amministrazioneValidation.validateComuneInserimento(comuneForm, result);
		if (result.hasErrors()) {
			List<ComboForm> elencoProvince = commonService.getAllProvincia();
			modelMap.addAttribute("elencoProvince", elencoProvince);
			modelMap.addAttribute("comuneForm", comuneForm);
			return "/amministrazione/nuovoComune";
		}
		String descUtente = (recuperaIdCodiceUtente().toString());
		amministrazioneService.inserisciComune(comuneForm.getItemProvincia().getId(), comuneForm.getDescIstatComune(), comuneForm.getDescComune(), comuneForm.getDescCapComune(), comuneForm.getDescBelfiore(), comuneForm.getDescCatasto(), comuneForm.getDataFineValidita(), descUtente);
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", " ", true);
		return ricercaComuni(comuneForm,result,modelMap);
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaComuni.htm" , method = RequestMethod.POST, params = { "ModificaComune" })
	public String modificaComune(ComuneForm comuneForm, BindingResult result, ModelMap modelMap) {
		if(comuneForm.getCodiKIdComune() == 0 )	{
			super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
			return ricercaComuni(comuneForm, result, modelMap);
		}
		
		comuneForm = amministrazioneService.getComune(comuneForm.getCodiKIdComune());
		List<ComboForm> elencoProvince = commonService.getAllProvincia();
		modelMap.addAttribute("elencoProvince", elencoProvince);
		modelMap.addAttribute("comuneForm", comuneForm);
		return "/amministrazione/modificaComune";
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaComuni.htm" , method = RequestMethod.POST, params = { "SalvaModifica" })
	public String salvaModificaComuni(ComuneForm comuneForm, BindingResult result, ModelMap modelMap) {
		
		amministrazioneValidation.validateComuneInserimento(comuneForm, result);
		
		if (result.hasErrors()) {
			List<ComboForm> elencoProvince = commonService.getAllProvincia();
			modelMap.addAttribute("elencoProvince", elencoProvince);
			modelMap.addAttribute("comuneForm", comuneForm);
			return "/amministrazione/modificaComune";
		}
		
		amministrazioneService.modificaComune(comuneForm);
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", " ", true);
		return "/amministrazione/ricercaComune";
	}
	
	/******* END Amministrazione Comuni in base dati *********/
	
	
	@RequestMapping(value = "/funzioniAmministrazione/ricercaProcedimento.htm", method = RequestMethod.GET)
	public ModelAndView visualizzaRicercaProcedimento(Model model) {
		
		ProfiloForm profilo=(ProfiloForm) request.getSession().getAttribute("profilo");
		
		//lo svuoto perche' se torna indietro dal procedimento nella tendina dei
		//tipi procedimento devono venire tutti i tipi di procedimento
		if (profilo.getRuolo().trim().equals("MAGISTRATO")){
			request.getSession().setAttribute(Constants.TIPO_FASCICOLO,null);	
		}
		//simone 03/02/2014
		model.addAttribute("tipoDiFascicolo", getTipiFascicolo());
		
		
		if (request.getSession().getAttribute(Constants.TIPO_FASCICOLO)!= null){
			
			model.addAttribute("tipiProcedimento", getTipiProcedimento());	
		}
		else {
			//inizialmente carico tutti i tipi di procedimento per merito
			model.addAttribute("tipiProcedimento",commonService.listaTipiDiProcedimento(1));	
		}
		
		model.addAttribute("listaUffici", getListaUffici(request));
		cleanBreadcrumb();
		addBreadcrumbs();

		return new ModelAndView("/amministrazione/ricercaProcedimento", "procedimento", new ProcedimentoForm());	
	}
	
	@ModelAttribute("tipoDiFascicolo")
	public List<ComboForm> getTipiFascicolo() {
		return commonService.listaTipiFascicolo(super.getIdTipoFascicolo(false));
	}

	public List<ComboForm> getTipiProcedimento() {
		
		return commonService.listaTipiDiProcedimento(super.getIdTipoFascicolo(false));

	}	
	
	@ModelAttribute("listaUffici")
	public List<ComboForm> getListaUffici(HttpServletRequest request) {
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
		return commonService.getListaUffici(idUfficioUtente.intValue());
	}
	
	@ModelAttribute("tipiProcedente")
	public List<ComboForm> getTipiProcedente(HttpServletRequest request) {

		List<ComboForm> result = null;
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
		result = commonService.listaTipiProcedente(idUfficioUtente);
		if(result!=null && result.size()>0) result.add(new ComboForm("0","Altro"));
		return result;
	}
	
	@RequestMapping(value = "/amministrazione/ricercaProcedimento.htm", method = RequestMethod.GET, params = { "Ricerca" })
	public ModelAndView ricercaProcedimentoPaginazione(@Valid @ModelAttribute("procedimento") ProcedimentoForm procedimento, BindingResult result, HttpServletRequest request) {
		return ricercaProcedimento(procedimento, result, request);
	}

	@ModelAttribute("pmAssegnatari")
	public List<ComboForm> getPmAssegnatari(HttpServletRequest request) {

		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
		return commonService.getMagistratiByUff(idUfficioUtente);
	}
	
	@RequestMapping(value = "/amministrazione/ricercaProcedimento.htm", method = RequestMethod.POST, params = { "Ricerca" })
	public ModelAndView ricercaProcedimento(@Valid @ModelAttribute("procedimento") ProcedimentoForm procedimento, BindingResult result, HttpServletRequest request) {
		if(procedimento != null && (procedimento.getAnno() != null || procedimento.getNumero() != null)) {
			if (result.hasErrors()) {
				return new ModelAndView("/amministrazione/ricercaProcedimento", "procedimento", procedimento);
			}
			procedimentoValidation.validateCriteriRicercaPuntuale(procedimento, result);
			if (result.hasErrors()) {
				return new ModelAndView("/amministrazione/ricercaProcedimento", "procedimento", procedimento);
			}			
		}
		
		addBreadcrumbs("POST","/amministrazione/ricercaProcedimento.htm","Ricerca");
		
		/*usando la stessa jsp sono stato costretto a mappare l'anno della ricerca per criteri con il nome di
		  'annoCriterio', quindi per non modificare la store procedure ho scelto di passarlo al campo 'anno'*/
		if(procedimento.getAnno() == null) {
			procedimento.setAnno(procedimento.getAnnoCriterio());
		} else {
			procedimento.setNumeroMin(procedimento.getNumero());
			procedimento.setNumeroMax(procedimento.getNumero());
		}
		
		ModelAndView mnv = new ModelAndView("/amministrazione/ricercaProcedimento", "procedimento", procedimento);
	    mnv = ricercaProcedimentoPerCriteri(procedimento,mnv,result,request,false);
	    List<ProcedimentoForm> risultati = (List)mnv.getModelMap().get("risultati");
	    
	    //se l'elenco dei risultati produce un solo procedimento
	    //visualizzo la pagina del dettaglio del fascicolo
	    if(risultati!=null && risultati.size()==1) {
			procedimento = risultati.get(0);
			mnv = caricaDettaglioProcedimento(procedimento, result, request, "/amministrazione/ricercaProcedimento");
		} 
	    
	    if(procedimento.getAnno() != null && procedimento.getNumero()!=null) {
	    	procedimento.setNumeroMin(null);
			procedimento.setNumeroMax(null);	
	    } else {
	    	procedimento.setAnno(null);
	    	procedimento.setNumero(null);
	    }
	    return mnv;

	 }	
	
	private ModelAndView ricercaProcedimentoPerCriteri(ProcedimentoForm procedimento,ModelAndView mnv, BindingResult result, HttpServletRequest request,boolean isError) {
		
		//se non è stata selezionata la combo tipo procedimento prendo dalla sessione il tipoProc in cui mi trovo
		int idTipoFasc=super.getIdTipoFascicolo(false);
		
		if(procedimento.getTipoProcedimento() == null 
			|| procedimento.getTipoProcedimento().getId() == null 
			|| Long.valueOf(procedimento.getTipoProcedimento().getId()).longValue() == -1 ) {
		    long idTipoProc = getIdTipoProcedimentoInSessione(request);
			
			//modifica ricerca gennaro
			//è stato inserito anche il tipo di fascicolo che prima veniva preso dalla sessione
			//ora essendo presente la tendina nella jsp,lo prendi dalla comboboc tipoFascicolo
			//altrimenti dalla sessione
			
			
			if (procedimento.getTipoFascicolo()!= null && procedimento.getTipoFascicolo().getId()!= null && 
					 Long.valueOf(procedimento.getTipoFascicolo().getId()).longValue() != -1	)
				idTipoFasc=Integer.parseInt(procedimento.getTipoFascicolo().getId()); 	
				else idTipoFasc = super.getIdTipoFascicolo(false);
			
			//se in sessione ho uno di questi valori alla stored procedure passerò null, perché
			//è l'unico modo che ho nella stored procedure di distinguere se l'id l'ho preso dalla combo
			//o dalla sessione. 
			if(idTipoFasc == 1 || idTipoFasc == 2) 
				procedimento.setTipoProcedimento(new ComboForm("-1", null));
			else
				procedimento.setTipoProcedimento(new ComboForm(String.valueOf(idTipoProc), null));
		}
		

		if (result.hasErrors() && !isError) {
			return mnv;
		}
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
		request.getSession().setAttribute("procDiRicerca", procedimento);
		
		procedimento.setTipoFascicoloRicerca(idTipoFasc);
			
		List<ProcedimentoForm> lista = procedimentoService.listaProcedimentiByCriteria(idUfficioUtente, procedimento, null);
		
		mnv.addObject("risultati", lista);
		
		return mnv;
	}
	
	@SuppressWarnings("static-access")
	public ModelAndView caricaDettaglioProcedimento(ProcedimentoForm form, BindingResult result, HttpServletRequest request, String pagina) {
		
		ProcedimentoForm procedimento = null;
		if (form.getIdProcedimento()!=null){
			procedimento = procedimentoService.getDettaglioProcedimento(form.getIdProcedimento());
			String tipoFascicolo = null;
			if(procedimento.getTipoFascicolo().getId().equals(String.valueOf(Constants.TIPO_FASCICOLO_MERITO)))
				tipoFascicolo = "MERITO";
			else if(procedimento.getTipoFascicolo().getId().equals(String.valueOf(Constants.TIPO_FASCICOLO_ACCERTAMENTO)))
				tipoFascicolo = "ACCERTAMENTO";
			else if(procedimento.getTipoFascicolo().getId().equals(String.valueOf(Constants.TIPO_FASCICOLO_AGGRAVAMENTO)))
				tipoFascicolo = "AGGRAVAMENTO";
			else if(procedimento.getTipoFascicolo().getId().equals(String.valueOf(Constants.TIPO_FASCICOLO_OPP_DIVIETI_QUESTORE)))
				tipoFascicolo = "OPPOSIZIONE DIVIETI DEL QUESTORE";
			else if(procedimento.getTipoFascicolo().getId().equals(String.valueOf(Constants.TIPO_FASCICOLO_OPP_REVOCA_SEQUESTRO)))
				tipoFascicolo = "OPPOSIZIONE REVOCA DEL SEQUESTRO";
			else if(procedimento.getTipoFascicolo().getId().equals(String.valueOf(Constants.TIPO_FASCICOLO_CAMERA_DI_CONSIGLIO)))
				tipoFascicolo = "CAMERA DI CONSIGLIO";
			else if(procedimento.getTipoFascicolo().getId().equals(String.valueOf(Constants.TIPO_FASCICOLO_REVOCAZIONE)))
				tipoFascicolo = "REVOCAZIONE MISURA PATRIMONIALE";
			else if(procedimento.getTipoFascicolo().getId().equals(String.valueOf(Constants.TIPO_FASCICOLO_GESTIONE)))
				tipoFascicolo = "FASCICOLO DI GESTIONE";
			UtilityLogger.log.getLogger("MONITOR_UTENTI_FASCICOLI").info("ID Utente: "+ recuperaIdCodiceUtente() + "; ID Fascicolo: "+ procedimento.getAnno() + " / "+ procedimento.getNumero());
			//DGSIA
			if(Configuration.isConnessioneInterna() == false)
				sendMessage(procedimento.getNumero().toString(),procedimento.getAnno().toString(),tipoFascicolo);
		}
		
	
		//ho commentto questa riga perchè dava errore dopo aver acquisito i flussi
		//e aggiunto la riga sotto
		//int idTipoFascicolo = Integer.parseInt(super.getIntoSession(Constants.TIPO_FASCICOLO, false).toString());
		int idTipoFascicolo;
		Object idTipoFascicoloCollegato = request.getAttribute(Constants.TIPO_FASCICOLO_COLLEGATO);
		if(idTipoFascicoloCollegato!=null) {
			idTipoFascicolo = (Integer)idTipoFascicoloCollegato;
		} else idTipoFascicolo = super.getIdTipoFascicolo(false);
		request.getSession().setAttribute("statoProcedimento", procedimento.getStatoProcedimento().getId());
		String view = "/amministrazione/dettaglioProcedimento";
	    String urlIndietroPuntuale = "/amministrazione/ricercaProcedimento.htm";
        if (pagina != null) {
            if(pagina.equals("/amministrazione/ricercaPerCriteri")){
                procedimentoValidation.validateDettaglioProcedimento(form, result, request);
                if (result.hasErrors()) {
                    Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
                    ModelAndView mnv = new ModelAndView(pagina, "procedimento", form);
                    mnv.addObject("risultati", procedimentoService.listaProcedimentiByCriteria(idUfficioUtente, form, null));
                    request.getSession().removeAttribute("urlIndietroCopertinaProc");
                    request.getSession().removeAttribute("urlIndietroCopertinaProcPuntuale");
                    return mnv;
                }
            }
            else {
                procedimentoValidation.validateRicercaProcedimento(form, result, request);
                if (result.hasErrors()) {
                	request.getSession().removeAttribute("urlIndietroCopertinaProc");
                	request.getSession().removeAttribute("urlIndietroCopertinaProcPuntuale");
                    return new ModelAndView(pagina, "procedimento", form);
                }
            }
        }
		
		if (request.getSession().getAttribute(Constants.ATTO) != null) {
			request.getSession().removeAttribute(Constants.ATTO);
		}
		
		if(idTipoFascicoloCollegato==null) super.updateProcedimentoIntoSession(procedimento.getIdProcedimento());
        		
		// ------determino la jsp di dettaglio a seconda del tipo procedimento.
		//String idTipoProc = null;
		int idTipoFasc = getIdTipoFascicolo(false);
		if(idTipoFascicolo==0 && procedimento!=null && procedimento.getTipoFascicolo()!=null){
			super.saveIntoSession(procedimento.getTipoFascicolo().getId(), Constants.TIPO_FASCICOLO, true);
			idTipoFascicolo = getIdTipoFascicolo(false);
		}
//		if ((procedimento.getTipoProcedimento() != null) && (procedimento.getTipoProcedimento().getId() != null)) {
//			idTipoProc = procedimento.getTipoProcedimento().getId();
			
			if (idTipoFascicolo==Constants.TIPO_FASCICOLO_OPP_DIVIETI_QUESTORE) {
				view = "/amministrazione/dettaglioProcedimentoRiesame";
			} else if (idTipoFascicolo==Constants.TIPO_FASCICOLO_ACCERTAMENTO) {
				view = "/amministrazione/dettaglioProcedimentoAccertamento";
			}
//		}
		
		if (idTipoFascicolo==Constants.TIPO_FASCICOLO_AGGRAVAMENTO ||
			idTipoFascicolo==Constants.TIPO_FASCICOLO_CAMERA_DI_CONSIGLIO	) {
			Long idPadre = procedimentoService.getIdPadreAggravamento(procedimento.getIdProcedimento());
			ProcedimentoForm padre = procedimentoService.getDettaglioProcedimento(idPadre);
			procedimento.setAnnoPadre(padre.getAnno());
			procedimento.setNumeroPadre(padre.getNumero());
			NumeroRegistroForm formPadre = procedimentoService.getNumeroRegistroPadre(procedimento.getIdProcedimento());
			if(formPadre!=null) 	procedimento.setUfficioPadre(formPadre.getUfficio());
			

		} else {
			NumeroRegistroForm formPadre = procedimentoService.getNumeroRegistroPadre(procedimento.getIdProcedimento());
			if(formPadre!=null) {
				if(formPadre.getAnnoProcedimento()!=null) procedimento.setAnnoPadre(new Short(formPadre.getAnnoProcedimento()));
				if(formPadre.getNumeroProcedimento()!=null) procedimento.setNumeroPadre(new Long(formPadre.getNumeroProcedimento()));
				procedimento.setUfficioPadre(formPadre.getUfficio());
			}
		}		
		// ------
		ModelAndView mnv = new ModelAndView(view, "procedimento", procedimento);
		mnv.addObject("numeriRegistro", procedimentoService.getNumeriRegistro(procedimento.getIdProcedimento()));
		mnv.addObject("numeriRegistroAccertamento", procedimentoService.getNumeriRegistroAccertamento(procedimento.getIdProcedimento()));
		String procedimentiFigliStralcio = procedimentoService.getListaRelProcProcByProc1(procedimento.getIdProcedimento(), new Character('S'), recuperaIdCodiceUtente(), super.printMethodName());
		TProcedimento procedimentiPadreStralcio = procedimentoService.getTRelProcProcByProc2(procedimento.getIdProcedimento(), new Character('S'));
		
		String riunisceIlNumero = procedimentoService.getListaRelProcProcByProc1(procedimento.getIdProcedimento(), new Character('R'), recuperaIdCodiceUtente(), super.printMethodName());
		TProcedimento riunitoAlNumero = procedimentoService.getTRelProcProcByProc2(procedimento.getIdProcedimento(), new Character('R'));
		
		//fascicolo gestione -------
		if ((procedimento.getTipoFascicolo() == null) || ((procedimento.getTipoFascicolo() != null) && (procedimento.getTipoFascicolo().getId().equals(String.valueOf(Constants.TIPO_FASCICOLO_MERITO))))) {
			HashMap fascGestione = commonService.getFascicoloGestione(procedimento.getIdProcedimento(), recuperaIdUfficioUtente(), recuperaIdCodiceUtente());
			if(fascGestione!=null) {
				Long idProcGest = (Long) fascGestione.get("idProcGest");
				Long idUffRic = (Long) fascGestione.get("idUffRic");
				if(idProcGest!=null && idUffRic!=null && idProcGest>0 && idUffRic>0) {
					Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
					if (idUffRic.intValue() == idUfficioUtente.intValue()) {
						ProcedimentoForm procedimentoGestione = procedimentoService.getDettaglioProcedimento(idProcGest);
						mnv.addObject("procedimentoGestione", procedimentoGestione);
					}
				}
			}
		}
		//fine verifica fascicolo gestione -------

		mnv.addObject("procedimentiFigliStralcio", procedimentiFigliStralcio);
		mnv.addObject("procedimentiPadreStralcio", procedimentiPadreStralcio);
		
		mnv.addObject("riunitoAlNumero", riunitoAlNumero);
		mnv.addObject("riunisceIlNumero", riunisceIlNumero);
		
		List<SoggettoForm> list = procedimentoService.soggettiDirettiInteressatiDelProcedimento(procedimento.getIdProcedimento(), Constants.RUOLO_DIRETTO, Constants.Flag.N);
		List<SoggettoForm> listToView = new ArrayList<SoggettoForm>();
		
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			SoggettoForm soggettoForm = (SoggettoForm) iterator.next();
			if(soggettoForm.getDescrizioneProcedimentoDestinazione() != null){
				listToView.add(soggettoForm); 
			}
		}
		mnv.addObject("direttiInteressatiNonValidi", listToView);
		
		
		//se sto creando un nuovo procedimento pagina è null
		if(pagina != null) 
			request.getSession().setAttribute("urlIndietroCopertinaProcPuntuale", urlIndietroPuntuale);
		
		if ((procedimento.getTipoFascicolo() == null) || ((procedimento.getTipoFascicolo() != null) && (procedimento.getTipoFascicolo().getId().equals(String.valueOf(Constants.TIPO_FASCICOLO_MERITO))))) {
			// Se il procedimento è una prevenzione
			if(idTipoFascicoloCollegato!=null) MenuController.caricaMenu(Constants.ID_PER_MENU_PROCEDIMENTO_COLLEGATO, request);
			else MenuController.caricaMenu(Constants.ID_PER_MENU_DETTAGLIO_PREVENZIONE, request);
			mnv.addObject("direttiInteressati", procedimentoService.soggettiDirettiInteressatiDelProcedimento(procedimento.getIdProcedimento(), Constants.RUOLO_DIRETTO, Constants.Flag.S));
			return mnv;
		}
		if ((procedimento.getTipoFascicolo() != null) && procedimento.getTipoFascicolo().getId().equals(String.valueOf(Constants.TIPO_FASCICOLO_ACCERTAMENTO))) {
			if(idTipoFascicoloCollegato!=null) MenuController.caricaMenu(Constants.ID_PER_MENU_PROCEDIMENTO_COLLEGATO, request);
			else MenuController.caricaMenu(Constants.ID_PER_MENU_DETTAGLIO_ACCERTAMENTO, request);
			mnv.addObject("direttiInteressati", procedimentoService.soggettiDirettiInteressatiDelProcedimento(procedimento.getIdProcedimento(), Constants.RUOLO_DIRETTO, Constants.Flag.S));
		}
		if (((procedimento.getTipoFascicolo() != null) && 
				(procedimento.getTipoFascicolo().getId().equals(String.valueOf(Constants.TIPO_FASCICOLO_AGGRAVAMENTO)) )))
				
				{
			 if(idTipoFascicoloCollegato!=null) MenuController.caricaMenu(Constants.ID_PER_MENU_PROCEDIMENTO_COLLEGATO, request);
			 else {

			      MenuController.caricaMenu(Constants.ID_PER_MENU_DETTAGLIO_AGGRAVAMENTO, request);
			}
				  
			mnv.addObject("direttiInteressati", procedimentoService.soggettiDirettiInteressatiDelProcedimento(procedimento.getIdProcedimento(), null, Constants.Flag.S));
		}
		
		 if((procedimento.getTipoFascicolo() != null) && 
			(procedimento.getTipoFascicolo().getId().equals(String.valueOf(Constants.TIPO_FASCICOLO_CAMERA_DI_CONSIGLIO))))	
		 {
			 if(idTipoFascicoloCollegato!=null){
				 MenuController.caricaMenu(Constants.ID_PER_MENU_PROCEDIMENTO_COLLEGATO, request,true);
			 }else{
	
				if (procedimento.getTipoProcedimento().getId().equals(""+Constants.TIPOPROC_MODIFICA_PATRIMONIALE_CAM))
				    MenuController.caricaMenu(Constants.ID_PER_MENU_DETTAGLIO_PREVENZIONE, request,false);
				
				if (procedimento.getTipoProcedimento().getId().equals(""+Constants.TIPOPROC_MODIFICA_PERSONALE_CAM))
				    MenuController.caricaMenu(Constants.ID_PER_MENU_DETTAGLIO_PREVENZIONE, request,true);
		       
				if (procedimento.getTipoProcedimento().getId().equals(""+Constants.TIPOPROC_PERMESSI_CAM))
				    MenuController.caricaMenu(Constants.ID_PER_MENU_DETTAGLIO_PREVENZIONE, request,true);
				
				if (procedimento.getTipoProcedimento().getId().equals(""+Constants.TIPOPROC_CAUZIONI_CAM))
				    MenuController.caricaMenu(Constants.ID_PER_MENU_DETTAGLIO_PREVENZIONE, request,true);
				
				if (procedimento.getTipoProcedimento().getId().equals(""+Constants.TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM))
				    MenuController.caricaMenu(Constants.ID_PER_MENU_DETTAGLIO_PREVENZIONE, request,false);
				
				if (procedimento.getTipoProcedimento().getId().equals(""+Constants.TIPOPROC_TERZO_INTERVENIENTE_CAM))
				    MenuController.caricaMenu(Constants.ID_PER_MENU_DETTAGLIO_PREVENZIONE, request,false);
				
				if (procedimento.getTipoProcedimento().getId().equals(""+Constants.TIPOPROC_CORREZIONE_ERRORI_CAM))
				    MenuController.caricaMenu(Constants.ID_PER_MENU_DETTAGLIO_PREVENZIONE, request,true);
				
			 }	
				mnv.addObject("direttiInteressati", procedimentoService.soggettiDirettiInteressatiDelProcedimento(procedimento.getIdProcedimento(), Constants.RUOLO_DIRETTO, Constants.Flag.S));
				return mnv;
		 }
		
		
		if ((procedimento.getTipoFascicolo() != null) && 
				(procedimento.getTipoFascicolo().getId().equals(String.valueOf(Constants.TIPO_FASCICOLO_OPP_DIVIETI_QUESTORE)) ) ) {
			if(idTipoFascicoloCollegato!=null) MenuController.caricaMenu(Constants.ID_PER_MENU_PROCEDIMENTO_COLLEGATO, request);
			else MenuController.caricaMenu(Constants.ID_PER_MENU_DETTAGLIO_RIESAME, request);
			mnv.addObject("direttiInteressati", procedimentoService.soggettiDirettiInteressatiDelProcedimento(procedimento.getIdProcedimento(), null, Constants.Flag.S));
		}		
		//TODO:da verificare se le voci di menù per il fascicolo di Opposizione alla restituzione devono essere le stesse del fascicolo di Prevenzione
		if (
			(procedimento.getTipoFascicolo() == null) || 
			(
				(procedimento.getTipoFascicolo() != null) && 
				(
					(procedimento.getTipoFascicolo().getId().equals(String.valueOf(Constants.TIPO_FASCICOLO_OPP_REVOCA_SEQUESTRO))) ||
					(procedimento.getTipoFascicolo().getId().equals(String.valueOf(Constants.TIPO_FASCICOLO_REVOCAZIONE)))							
				)
			)
		) {
			// Se il procedimento è una prevenzione
			if(idTipoFascicoloCollegato!=null) MenuController.caricaMenu(Constants.ID_PER_MENU_PROCEDIMENTO_COLLEGATO, request);
			else MenuController.caricaMenu(Constants.ID_PER_MENU_DETTAGLIO_PREVENZIONE, request);
			mnv.addObject("direttiInteressati", procedimentoService.soggettiDirettiInteressatiDelProcedimento(procedimento.getIdProcedimento(), Constants.RUOLO_DIRETTO, Constants.Flag.S));
			return mnv;
		}	
		if ((procedimento.getTipoFascicolo() != null) && 
				(procedimento.getTipoFascicolo().getId().equals(String.valueOf(Constants.TIPO_FASCICOLO_GESTIONE)) ) ) {
			if(idTipoFascicoloCollegato!=null) MenuController.caricaMenu(Constants.ID_PER_MENU_PROCEDIMENTO_COLLEGATO, request);
			else MenuController.caricaMenu(Constants.ID_PER_MENU_DETTAGLIO_GESTIONE, request);
			mnv.addObject("direttiInteressati", procedimentoService.soggettiDirettiInteressatiDelProcedimento(procedimento.getIdProcedimento(), Constants.RUOLO_DIRETTO, Constants.Flag.S));
		}	
		
		return mnv;
	}

	
	private void sendMessage(String idFascicolo, String annoFascicolo, String tipoFascicolo) {
		// jmsMessage.setCodIdUfficio(recuperaIdUfficioUtente().toString());
//		jmsMessage.setData(new Date());
		// jmsMessage.setDescUfficio(desc);
		jmsMessage.setIdFascicolo(idFascicolo);
		jmsMessage.setAnnoFascicolo(annoFascicolo);
		jmsMessage.setCodIdUfficio(recuperaIdUfficioUtente().toString());
		jmsMessage.setSchema(UserContextHolder.getUserType());
		jmsMessage.setIdUtente(recuperaIdCodiceUtente());
		jmsMessage.setTipoFascicolo(tipoFascicolo);
		
		try {
			jmsService.sendMessage(jmsMessage);
		} catch (Exception e) {
			logger.error("impossibile inviare messaggio di log: " + e.getMessage()+ "ID_Fascicolo = " + idFascicolo+" - " + annoFascicolo + " - " + tipoFascicolo);
		}
	}
	
	//Dettaglio successivo alla scelta dall'elenco della ricerca per criteri
	@RequestMapping(value = "/amministrazione/ricercaProcedimento.htm", method = RequestMethod.POST, params = { "Dettaglio" })
	public ModelAndView dettaglioRicercaProcedimento(@Valid @ModelAttribute("procedimento") ProcedimentoForm procedimento, BindingResult result, HttpServletRequest request) {
	
		procedimentoValidation.validateDettaglioProcedimento(procedimento, result, request);
		
		if (result.hasErrors()) {
			ModelAndView mnv = new ModelAndView("/amministrazione/ricercaProcedimento", "procedimento", procedimento);
		    mnv = ricercaProcedimentoPerCriteri(procedimento,mnv,result,request,true);
		    return mnv;
			
		}
		
		//Giacomo 12/07/2013 Aggiunta Breadcrumbs		
		addBreadcrumbs("GET","/amministrazione/ricercaProcedimento.htm",null);
	
		return caricaDettaglioProcedimento(procedimento, result, request, "/amministrazione/ricercaProcedimento");
	}
	
	@ModelAttribute("procedenti")
	public List<ComboForm> getProcedenti(@Valid @ModelAttribute("procedimento") ProcedimentoForm procedimento, BindingResult result) {

		ProfiloForm profilo = (ProfiloForm)session.getAttribute(Constants.PROFILO);
		long idTipoUfficioConnesso = profilo.getIdTipoUfficio();
		if (!Utility.isDefinito(procedimento.getTipoProcedente())) {
			return new ArrayList<ComboForm>();
		}
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
		if(procedimento.getTipoProcedente()!=null && procedimento.getTipoProcedente().getId().equals("0")) {
			List<ComboForm> lista = new ArrayList<ComboForm>();
			lista.add(new ComboForm("271", "Altro"));
			return lista;
		} else return commonService.listaProcedenti(new Long(idUfficioUtente.toString()), idTipoUfficioConnesso, new Long(procedimento.getTipoProcedente().getId()));
	}
	
	
	/******* INIZIO Amministrazione Uffici in base dati *********/
	
	@RequestMapping(value = "/funzioniAmministrazione/uffici.htm", method = RequestMethod.GET)
	public ModelAndView amministrazioneUffici(ModelMap modelMap) {
		UfficiForm ufficiForm = new UfficiForm();
		List<ComboForm> elencoTipoUfficio = commonService.listaTipoUfficio();
		List<ComboForm> elencoComuni = commonService.getAllComuni();
		
		modelMap.addAttribute("elencoTipoUfficio", elencoTipoUfficio);
		modelMap.addAttribute("elencoComuni", elencoComuni);
		
		ModelAndView mnv = new ModelAndView("/amministrazione/ricercaUfficio", "ufficiForm", ufficiForm);
		return mnv;
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaUffici.htm" , method = RequestMethod.POST, params = { "Ricerca" })
	public String ricercaUffici(UfficiForm ufficiForm, BindingResult result, ModelMap modelMap) {

		List<ComboForm> elencoTipoUfficio = commonService.listaTipoUfficio();
		List<ComboForm> elencoComuni = commonService.getAllComuni();
		
		int idTipoUfficio = 0;
		int idComune = 0;
		if(ufficiForm.getItemTipoUfficio().getId() != null && !ufficiForm.getItemTipoUfficio().getId().equals("-1")){
			idTipoUfficio = new Integer(ufficiForm.getItemTipoUfficio().getId()).intValue();	
		}

		if(ufficiForm.getItemComuneSede().getId() != null && !ufficiForm.getItemComuneSede().getId().equals("-1")){
			idComune = new Integer(ufficiForm.getItemComuneSede().getId()).intValue();
		}
				
		List<UfficiForm> listaUfficiForm = amministrazioneService.listaUffici(idTipoUfficio, idComune);
		if(listaUfficiForm == null)
			listaUfficiForm = new ArrayList<UfficiForm>();
		
		modelMap.addAttribute("elencoTipoUfficio", elencoTipoUfficio);
		modelMap.addAttribute("elencoComuni", elencoComuni);
		modelMap.addAttribute("risultatiRicerca", listaUfficiForm);
		return "/amministrazione/ricercaUfficio";
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaUffici.htm" , method = RequestMethod.GET)
	public String ricercaUfficiPaginazione(UfficiForm ufficiForm, BindingResult result, ModelMap modelMap) {
		return ricercaUffici(ufficiForm, result, modelMap);
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaUffici.htm" , method = RequestMethod.POST, params = { "NuovoUfficio" })
	public String nuovoUfficio(UfficiForm ufficiForm, BindingResult result, ModelMap modelMap) {
		
		List<ComboForm> elencoTipoUfficio = commonService.listaTipoUfficio();
		List<ComboForm> elencoUfficiTribunali = commonService.listaUfficiTribunali();
		List<ComboForm> elencoComuni = commonService.getAllComuni();
		List<ComboForm> elencoDistretti = commonService.getAllDistretti();
		
		modelMap.addAttribute("elencoTipoUfficio", elencoTipoUfficio);
		modelMap.addAttribute("elencoUfficiTribunali", elencoUfficiTribunali);
		modelMap.addAttribute("elencoComuni", elencoComuni);
		modelMap.addAttribute("elencoDistretti", elencoDistretti);
		
		return "/amministrazione/nuovoUfficio";
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/nuovoUfficio.htm" , method = RequestMethod.POST, params = { "Indietro" })
	public String indietroRicercaUffici(UfficiForm ufficiForm, BindingResult result, ModelMap modelMap) {

		return ricercaUffici(ufficiForm,result,modelMap);
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/nuovoUfficio.htm" , method = RequestMethod.POST, params = { "Inserisci" })
	public String inserisciUfficio(UfficiForm ufficiForm, BindingResult result, ModelMap modelMap) {
		
		amministrazioneValidation.validateUfficiInserimento(ufficiForm, result);
		
		if (result.hasErrors()) {
			//inizio correzione del 21/10/2015
			List<ComboForm> elencoTipoUfficio = commonService.listaTipoUfficio();
			List<ComboForm> elencoUfficiTribunali = commonService.listaUfficiTribunali();
			List<ComboForm> elencoComuni = commonService.getAllComuni();
			List<ComboForm> elencoDistretti = commonService.getAllDistretti();
			
			modelMap.addAttribute("elencoTipoUfficio", elencoTipoUfficio);
			modelMap.addAttribute("elencoUfficiTribunali", elencoUfficiTribunali);
			modelMap.addAttribute("elencoComuni", elencoComuni);
			modelMap.addAttribute("elencoDistretti", elencoDistretti);
			//fine correzione del 21/10/2015
			return "/amministrazione/nuovoUfficio";
		}
		
		int idTipoUfficio = 0;
		int idComuneSede = 0;
		int idDistretto = 0;
		int idTribunale = 0;
		
		if(ufficiForm.getItemTipoUfficio() != null && !ufficiForm.getItemTipoUfficio().getId().equals("-1")){
			idTipoUfficio = new Integer(ufficiForm.getItemTipoUfficio().getId());
		}
		
		if(ufficiForm.getItemComuneSede() != null && !ufficiForm.getItemComuneSede().getId().equals("-1")){
			idComuneSede = new Integer(ufficiForm.getItemComuneSede().getId());
		}
		
		if(ufficiForm.getItemDistretto() != null && !ufficiForm.getItemDistretto().getId().equals("-1")){
			idDistretto = new Integer(ufficiForm.getItemDistretto().getId());
		}
		
		if(ufficiForm.getItemTribunale() != null && !ufficiForm.getItemTribunale().getId().equals("-1")){
			idTribunale = new Integer(ufficiForm.getItemTribunale().getId());
		}
		String descUtente = (recuperaIdCodiceUtente().toString());
		amministrazioneService.inserisciUfficio(idTipoUfficio, idComuneSede, ufficiForm.getDescIndirizzoSede(), idDistretto, ufficiForm.getFlgTribMp(), idTribunale, ufficiForm.getFlgDda(), ufficiForm.getDataFineValidita(), descUtente);
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", " ", true);
		return ricercaUffici(ufficiForm,result,modelMap);
	}

	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaUffici.htm" , method = RequestMethod.POST, params = { "ModificaUfficio" })
	public String modificaUfficio(UfficiForm ufficiForm, BindingResult result, ModelMap modelMap) {
		
		if(ufficiForm.getCodiKIdUfficio() == 0)	{
			super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
			return ricercaUffici(ufficiForm,result,modelMap);
		}
		
		ufficiForm = amministrazioneService.getUfficio(ufficiForm.getCodiKIdUfficio());
		
		List<ComboForm> elencoTipoUfficio = commonService.listaTipoUfficio();
		List<ComboForm> elencoUfficiTribunali = commonService.listaUfficiTribunali();
		List<ComboForm> elencoComuni = commonService.getAllComuni();
		List<ComboForm> elencoDistretti = commonService.getAllDistretti();		
		
		modelMap.addAttribute("elencoTipoUfficio", elencoTipoUfficio);
		modelMap.addAttribute("elencoUfficiTribunali", elencoUfficiTribunali);
		modelMap.addAttribute("elencoComuni", elencoComuni);
		modelMap.addAttribute("elencoDistretti", elencoDistretti);
		
		modelMap.addAttribute("ufficiForm", ufficiForm);
		return "/amministrazione/modificaUfficio";
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaUffici.htm" , method = RequestMethod.POST, params = { "SalvaModifica" })
	public String salvaModificaUfficio(UfficiForm ufficiForm, BindingResult result, ModelMap modelMap) {

		amministrazioneValidation.validateUfficiInserimento(ufficiForm, result);
		
		if (result.hasErrors()) {
			//inizio correzione del 21/10/2015
			List<ComboForm> elencoTipoUfficio = commonService.listaTipoUfficio();
			List<ComboForm> elencoUfficiTribunali = commonService.listaUfficiTribunali();
			List<ComboForm> elencoComuni = commonService.getAllComuni();
			List<ComboForm> elencoDistretti = commonService.getAllDistretti();

			modelMap.addAttribute("elencoTipoUfficio", elencoTipoUfficio);
			modelMap.addAttribute("elencoUfficiTribunali", elencoUfficiTribunali);
			modelMap.addAttribute("elencoComuni", elencoComuni);
			modelMap.addAttribute("elencoDistretti", elencoDistretti);
			//fine correzione del 21/10/2015
			
			return "/amministrazione/modificaUfficio";
		}
				
		amministrazioneService.modificaUfficio(ufficiForm);
		
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", " ", true);
		
		List<ComboForm> elencoTipoUfficio = commonService.listaTipoUfficio();
		List<ComboForm> elencoUfficiTribunali = commonService.listaUfficiTribunali();
		List<ComboForm> elencoComuni = commonService.getAllComuni();
		List<ComboForm> elencoDistretti = commonService.getAllDistretti();
		
		modelMap.addAttribute("elencoTipoUfficio", elencoTipoUfficio);
		modelMap.addAttribute("elencoUfficiTribunali", elencoUfficiTribunali);
		modelMap.addAttribute("elencoComuni", elencoComuni);
		modelMap.addAttribute("elencoDistretti", elencoDistretti);
		
		return "/amministrazione/ricercaUfficio";
	}
	
	/******* FINE Amministrazione Uffici in base dati *********/

	/******* INIZIO Amministrazione Categorie Catastali in base dati *********/
	
	@RequestMapping(value = "/funzioniAmministrazione/categorieCatastali.htm", method = RequestMethod.GET)
	public ModelAndView amministrazioneCatCatastali(ModelMap modelMap) {
		CategoriaCatastaleForm categoriaCatastaleForm = new CategoriaCatastaleForm();
		
		ModelAndView mnv = new ModelAndView("/amministrazione/ricercaCatCatastale", "categoriaCatastaleForm", categoriaCatastaleForm);
		return mnv;
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaCatCatastale.htm" , method = RequestMethod.POST, params = { "Ricerca" })
	public String ricercaCatCatastali(CategoriaCatastaleForm categoriaCatastaleForm, BindingResult result, ModelMap modelMap) {
	
		List<CategoriaCatastaleForm> listaCatCatastaliForm = amministrazioneService.listaCategorieCatastali(categoriaCatastaleForm.getDescCategoriaCat(), categoriaCatastaleForm.getDescBreveCategoriaCat());

		if(listaCatCatastaliForm == null)
			listaCatCatastaliForm = new ArrayList<CategoriaCatastaleForm>();
		
		modelMap.addAttribute("risultatiRicerca", listaCatCatastaliForm);

		return "/amministrazione/ricercaCatCatastale";
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaCatCatastale.htm" , method = RequestMethod.GET)
	public String ricercaCatCatastaliPaginazione(CategoriaCatastaleForm categoriaCatastaleForm, BindingResult result, ModelMap modelMap) {
		return ricercaCatCatastali(categoriaCatastaleForm, result, modelMap);
	}
	
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaCatCatastale.htm" , method = RequestMethod.POST, params = { "NuovaCatCatastale" })
	public String nuovaCatCatastale(CategoriaCatastaleForm categoriaCatastaleForm, BindingResult result, ModelMap modelMap) {
		modelMap.addAttribute("categoriaCatastaleForm", categoriaCatastaleForm);
		return "/amministrazione/nuovaCatCatastale";
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/nuovaCatCatastale.htm" , method = RequestMethod.POST, params = { "Indietro" })
	public String indietroRicercaCatCatastali(CategoriaCatastaleForm categoriaCatastaleForm, BindingResult result, ModelMap modelMap) {

		return ricercaCatCatastali(categoriaCatastaleForm, result, modelMap);
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/nuovaCatCatastale.htm" , method = RequestMethod.POST, params = { "Inserisci" })
	public String inserisciCatCatastale(CategoriaCatastaleForm categoriaCatastaleForm, BindingResult result, ModelMap modelMap) {
		amministrazioneValidation.validateCatCatastaliInserimento(categoriaCatastaleForm, result);

		if (result.hasErrors()) {
			return "/amministrazione/nuovaCatCatastale";
		}
		String descUtente = (recuperaIdCodiceUtente().toString());
		amministrazioneService.inserisciCategoriaCatastale(categoriaCatastaleForm.getDescCategoriaCat(), categoriaCatastaleForm.getDescBreveCategoriaCat(), categoriaCatastaleForm.getDataFineValidita(), descUtente);
		
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", " ", true);
		return ricercaCatCatastali(categoriaCatastaleForm, result, modelMap);
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaCatCatastale.htm" , method = RequestMethod.POST, params = { "ModificaCatCatastale" })
	public String modificaCatCatastale(CategoriaCatastaleForm categoriaCatastaleForm, BindingResult result, ModelMap modelMap) {
		if(categoriaCatastaleForm.getCodiKIdCategCat() == 0)	{
			super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
			return ricercaCatCatastali(categoriaCatastaleForm, result, modelMap);
		}
		
		categoriaCatastaleForm = amministrazioneService.getCategoriaCatastale(categoriaCatastaleForm.getCodiKIdCategCat());
		modelMap.addAttribute("categoriaCatastaleForm", categoriaCatastaleForm);
		return "/amministrazione/modificaCatCatastale";
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaCatCatastale.htm" , method = RequestMethod.POST, params = { "SalvaModifica" })
	public String salvaModificaCatCatastale(CategoriaCatastaleForm categoriaCatastaleForm, BindingResult result, ModelMap modelMap) {
		
		amministrazioneValidation.validateCatCatastaliInserimento(categoriaCatastaleForm, result);
		
		if (result.hasErrors()) {
			return "/amministrazione/modificaCatCatastale";
		}
		
		amministrazioneService.modificaCatCatastale(categoriaCatastaleForm);
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", " ", true);
		return "/amministrazione/ricercaCatCatastale";
	}
	
	/******* FINE Amministrazione Categorie Catastali in base dati *********/
	
	/******* INIZIO Amministrazione Professioni in base dati *********/
	
	@RequestMapping(value = "/funzioniAmministrazione/professioni.htm", method = RequestMethod.GET)
	public ModelAndView amministrazioneProfessioni(ModelMap modelMap) {

		ProfessioneForm professioneForm = new ProfessioneForm();
		ModelAndView mnv = new ModelAndView("/amministrazione/ricercaProfessione", "professioneForm", professioneForm);
		return mnv;
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaProfessioni.htm" , method = RequestMethod.POST, params = { "Ricerca" })
	public String ricercaProfessioni(ProfessioneForm professioneForm, BindingResult result, ModelMap modelMap) {

		List<ProfessioneForm> listaProfessioniForm = amministrazioneService.listaProfessioni(professioneForm.getDescProfessione());

		if(listaProfessioniForm == null)
			listaProfessioniForm = new ArrayList<ProfessioneForm>();
		
		modelMap.addAttribute("risultatiRicerca", listaProfessioniForm);
		return "/amministrazione/ricercaProfessione";
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaProfessioni.htm" , method = RequestMethod.GET)
	public String ricercaProfessioniPaginazione(ProfessioneForm professioneForm, BindingResult result, ModelMap modelMap) {
		return ricercaProfessioni(professioneForm, result, modelMap);
	}

	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaProfessioni.htm" , method = RequestMethod.POST, params = { "NuovaProfessione" })
	public String nuovaProfessione(ProfessioneForm professioneForm, BindingResult result, ModelMap modelMap) {
		modelMap.addAttribute("professioneForm", professioneForm);
		return "/amministrazione/nuovaProfessione";
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/nuovaProfessione.htm" , method = RequestMethod.POST, params = { "Indietro" })
	public String indietroRicercaProfessioni(ProfessioneForm professioneForm, BindingResult result, ModelMap modelMap) {
		return ricercaProfessioni(professioneForm, result, modelMap);
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/nuovaProfessione.htm" , method = RequestMethod.POST, params = { "Inserisci" })
	public String inserisciProfessione(ProfessioneForm professioneForm, BindingResult result, ModelMap modelMap) {
		amministrazioneValidation.validateProfessioneInserimento(professioneForm, result);
		if (result.hasErrors()) {
			return "/amministrazione/nuovaProfessione";
		}
		String descUtente = (recuperaIdCodiceUtente().toString());
		amministrazioneService.inserisciProfessione(professioneForm.getDescProfessione(), professioneForm.getDataFineValidita(), descUtente);
		
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", " ", true);
		return ricercaProfessioni(professioneForm, result, modelMap);
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaProfessioni.htm" , method = RequestMethod.POST, params = { "ModificaProfessione" })
	public String modificaProfessione(ProfessioneForm professioneForm, BindingResult result, ModelMap modelMap) {
		if(professioneForm.getCodiKIdProfessione() == 0)	{
			super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
			return ricercaProfessioni(professioneForm, result, modelMap);
		}
		
		professioneForm = amministrazioneService.getProfessione(professioneForm.getCodiKIdProfessione());
		modelMap.addAttribute("professioneForm", professioneForm);
		return "/amministrazione/modificaProfessione";
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaProfessioni.htm" , method = RequestMethod.POST, params = { "SalvaModifica" })
	public String salvaModificaProfessione(ProfessioneForm professioneForm, BindingResult result, ModelMap modelMap) {

		amministrazioneValidation.validateProfessioneInserimento(professioneForm, result);
		if (result.hasErrors()) {
			return "/amministrazione/modificaProfessione";
		}
		
		amministrazioneService.modificaProfessione(professioneForm);
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", " ", true);
		return "/amministrazione/ricercaProfessione";
	}
	
	/******* FINE Amministrazione Professioni in base dati *********/
	
	/******* INIZIO Istituto Detenzione in base dati *********/
	
	@RequestMapping(value = "/funzioniAmministrazione/istitutoDetenzione.htm", method = RequestMethod.GET)
	public ModelAndView amministrazioneIstDetenzione(ModelMap modelMap) {
		
		IstitutoDetenzioneForm istitutoDetenzioneForm = new IstitutoDetenzioneForm();
		
		List<ComboForm> elencoTipoIstituto = commonService.listaTipoIstituto();
		List<ComboForm> elencoComuni = commonService.getAllComuni();
		
		modelMap.addAttribute("elencoTipoIstituto", elencoTipoIstituto);
		modelMap.addAttribute("elencoComuni", elencoComuni);
		
		ModelAndView mnv = new ModelAndView("/amministrazione/ricercaIstDetenzione", "istitutoDetenzioneForm", istitutoDetenzioneForm);
		return mnv;
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaIstDetenzione.htm" , method = RequestMethod.POST, params = { "Ricerca" })
	public String ricercaIstDetenzione(IstitutoDetenzioneForm istitutoDetenzioneForm, BindingResult result, ModelMap modelMap) {

		int idTipoIstituto = 0;
		int idComuneIstituto = 0;
		
		List<ComboForm> elencoTipoIstituto = commonService.listaTipoIstituto();
		List<ComboForm> elencoComuni = commonService.getAllComuni();
		
		if(istitutoDetenzioneForm.getItemTipoIstituto().getId() != null && !istitutoDetenzioneForm.getItemTipoIstituto().getId().equals("-1")){
			idTipoIstituto = new Integer(istitutoDetenzioneForm.getItemTipoIstituto().getId()).intValue();
		} 
		
		if(istitutoDetenzioneForm.getItemComune().getId() != null && !istitutoDetenzioneForm.getItemComune().getId().equals("-1")){
			idComuneIstituto = new Integer(istitutoDetenzioneForm.getItemComune().getId()).intValue();
		} 
		
		List<IstitutoDetenzioneForm> listaIstDetenzioneForm = amministrazioneService.listaIstitutiDetenzione(idTipoIstituto, idComuneIstituto);

		if(listaIstDetenzioneForm == null)
			listaIstDetenzioneForm = new ArrayList<IstitutoDetenzioneForm>();
		
		modelMap.addAttribute("elencoTipoIstituto", elencoTipoIstituto);
		modelMap.addAttribute("elencoComuni", elencoComuni);
		modelMap.addAttribute("risultatiRicerca", listaIstDetenzioneForm);
		
		return "/amministrazione/ricercaIstDetenzione";
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaIstDetenzione.htm" , method = RequestMethod.GET)
	public String ricercaIstDetenzionePaginazione(IstitutoDetenzioneForm istitutoDetenzioneForm, BindingResult result, ModelMap modelMap) {
		return ricercaIstDetenzione(istitutoDetenzioneForm, result, modelMap);
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaIstDetenzione.htm" , method = RequestMethod.POST, params = { "NuovoIstitutoDet" })
	public String nuovoIstDetenzione(IstitutoDetenzioneForm istitutoDetenzioneForm, BindingResult result, ModelMap modelMap) {

		List<ComboForm> elencoTipoIstituto = commonService.listaTipoIstituto();
		List<ComboForm> elencoComuni = commonService.getAllComuni();
		modelMap.addAttribute("elencoTipoIstituto", elencoTipoIstituto);
		modelMap.addAttribute("elencoComuni", elencoComuni);
		modelMap.addAttribute("istitutoDetenzioneForm", istitutoDetenzioneForm);
		return "/amministrazione/nuovoIstDetenzione";
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/nuovoIstDetenzione.htm" , method = RequestMethod.POST, params = { "Indietro" })
	public String indietroRicercaIstDetenzione(IstitutoDetenzioneForm istitutoDetenzioneForm, BindingResult result, ModelMap modelMap) {

		return ricercaIstDetenzione(istitutoDetenzioneForm, result, modelMap);
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/nuovoIstDetenzione.htm" , method = RequestMethod.POST, params = { "Inserisci" })
	public String inserisciIstDetenzione(IstitutoDetenzioneForm istitutoDetenzioneForm, BindingResult result, ModelMap modelMap) {
		amministrazioneValidation.validateIstitutoDetenzioneInserimento(istitutoDetenzioneForm, result);
		if (result.hasErrors()) {
			//correzione del 21/10/2015
			List<ComboForm> elencoTipoIstituto = commonService.listaTipoIstituto();
			List<ComboForm> elencoComuni = commonService.getAllComuni();
			modelMap.addAttribute("elencoTipoIstituto", elencoTipoIstituto);
			modelMap.addAttribute("elencoComuni", elencoComuni);
			modelMap.addAttribute("istitutoDetenzioneForm", istitutoDetenzioneForm);
			//fine correzione
			return "/amministrazione/nuovoIstDetenzione";
		}
		
		int idTipoIst = 0;
		int idComune = 0;
		
		if(istitutoDetenzioneForm.getItemTipoIstituto() != null && !istitutoDetenzioneForm.getItemTipoIstituto().getId().equals("-1")){
			idTipoIst = new Integer(istitutoDetenzioneForm.getItemTipoIstituto().getId()).intValue();
		}
		
		if(istitutoDetenzioneForm.getItemComune() != null && !istitutoDetenzioneForm.getItemComune().getId().equals("-1")){
			idComune = new Integer(istitutoDetenzioneForm.getItemComune().getId()).intValue();
		}
		String descUtente = (recuperaIdCodiceUtente().toString());
		amministrazioneService.inserisciIstitutoDetenzione(idTipoIst, idComune, istitutoDetenzioneForm.getDescIndirizzo(), istitutoDetenzioneForm.getDescUffGiudiz(), istitutoDetenzioneForm.getDataFineValidita(), descUtente);
		
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", " ", true);
		return ricercaIstDetenzione(istitutoDetenzioneForm, result, modelMap);
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaIstDetenzione.htm" , method = RequestMethod.POST, params = { "ModificaIstitutoDet" })
	public String modificaIstitutoDet(IstitutoDetenzioneForm istitutoDetenzioneForm, BindingResult result, ModelMap modelMap) {
		if(istitutoDetenzioneForm.getCodiKIdIstituto() == 0)	{
			super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
			return ricercaIstDetenzione(istitutoDetenzioneForm, result, modelMap);
		}
		
		istitutoDetenzioneForm = amministrazioneService.getIstitutoDetenzione(istitutoDetenzioneForm.getCodiKIdIstituto());
		List<ComboForm> elencoTipoIstituto = commonService.listaTipoIstituto();
		List<ComboForm> elencoComuni = commonService.getAllComuni();
		modelMap.addAttribute("elencoTipoIstituto", elencoTipoIstituto);
		modelMap.addAttribute("elencoComuni", elencoComuni);
		modelMap.addAttribute("istitutoDetenzioneForm", istitutoDetenzioneForm);
		return "/amministrazione/modificaIstDetenzione";
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaIstDetenzione.htm" , method = RequestMethod.POST, params = { "SalvaModifica" })
	public String salvaModificaIstitutoDet(IstitutoDetenzioneForm istitutoDetenzioneForm, BindingResult result, ModelMap modelMap) {

		amministrazioneValidation.validateIstitutoDetenzioneInserimento(istitutoDetenzioneForm, result);
		
		if (result.hasErrors()) {
			//correzione del 21/10/2015
			List<ComboForm> elencoTipoIstituto = commonService.listaTipoIstituto();
			List<ComboForm> elencoComuni = commonService.getAllComuni();
			modelMap.addAttribute("elencoTipoIstituto", elencoTipoIstituto);
			modelMap.addAttribute("elencoComuni", elencoComuni);
			modelMap.addAttribute("istitutoDetenzioneForm", istitutoDetenzioneForm);
			//fine correzione
			return "/amministrazione/modificaIstDetenzione";
		}
		
		amministrazioneService.modificaIstitutoDet(istitutoDetenzioneForm);
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", " ", true);
		List<ComboForm> elencoTipoIstituto = commonService.listaTipoIstituto();
		List<ComboForm> elencoComuni = commonService.getAllComuni();
		modelMap.addAttribute("elencoTipoIstituto", elencoTipoIstituto);
		modelMap.addAttribute("elencoComuni", elencoComuni);
		
		return "/amministrazione/ricercaIstDetenzione";

	}
	
	/******* FINE Amministrazione Istituto Detenzione in base dati *********/

	/******* INIZIO Amministrazione Conservatorie in base dati *********/
	
	@RequestMapping(value = "/funzioniAmministrazione/conservatorie.htm", method = RequestMethod.GET)
	public ModelAndView amministrazioneConservatorie(ModelMap modelMap) {
		ConservatoriaForm conservatoriaForm = new ConservatoriaForm();
		
		ModelAndView mnv = new ModelAndView("/amministrazione/ricercaConservatoria", "conservatoriaForm", conservatoriaForm);
		return mnv;
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaConservatoria.htm" , method = RequestMethod.POST, params = { "Ricerca" })
	public String ricercaConservatoria(ConservatoriaForm conservatoriaForm, BindingResult result, ModelMap modelMap) {

		List<ConservatoriaForm> listaConservatorieForm = amministrazioneService.listaConservatorie(conservatoriaForm.getDescConserv(), conservatoriaForm.getDescCodiConserv());

		if(listaConservatorieForm == null)
			listaConservatorieForm = new ArrayList<ConservatoriaForm>();
		
		modelMap.addAttribute("risultatiRicerca", listaConservatorieForm);
		return "/amministrazione/ricercaConservatoria";
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaConservatoria.htm" , method = RequestMethod.GET)
	public String ricercaConservatoriaPaginazione(ConservatoriaForm conservatoriaForm, BindingResult result, ModelMap modelMap) {
		return ricercaConservatoria(conservatoriaForm, result, modelMap);
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaConservatoria.htm" , method = RequestMethod.POST, params = { "NuovaConservatoria" })
	public String nuovaCatCatastale(ConservatoriaForm conservatoriaForm, BindingResult result, ModelMap modelMap) {
		modelMap.addAttribute("conservatoriaForm", conservatoriaForm);
		return "/amministrazione/nuovaConservatoria";
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/nuovaConservatoria.htm" , method = RequestMethod.POST, params = { "Indietro" })
	public String indietroRicercaCatCatastali(ConservatoriaForm conservatoriaForm, BindingResult result, ModelMap modelMap) {

		return ricercaConservatoria(conservatoriaForm, result, modelMap);
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/nuovaConservatoria.htm" , method = RequestMethod.POST, params = { "Inserisci" })
	public String inserisciConservatoria(ConservatoriaForm conservatoriaForm, BindingResult result, ModelMap modelMap) {
		amministrazioneValidation.validateConservatoriaInserimento(conservatoriaForm, result);
		if (result.hasErrors()) {
			return "/amministrazione/nuovaConservatoria";
		}
		String descUtente = (recuperaIdCodiceUtente().toString());
		amministrazioneService.inserisciConservatoria(conservatoriaForm.getDescConserv(), conservatoriaForm.getDescCodiConserv(), conservatoriaForm.getDataFineValidita(), descUtente);
		
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", " ", true);
		return ricercaConservatoria(conservatoriaForm, result, modelMap);
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaConservatoria.htm" , method = RequestMethod.POST, params = { "ModificaConservatoria" })
	public String modificaConservatoria(ConservatoriaForm conservatoriaForm, BindingResult result, ModelMap modelMap) {
		if(conservatoriaForm.getCodiKIdConserv() == 0)	{
			super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
			return ricercaConservatoria(conservatoriaForm, result, modelMap);
		}
		
		conservatoriaForm = amministrazioneService.getConservatoria(conservatoriaForm.getCodiKIdConserv());
		modelMap.addAttribute("conservatoriaForm", conservatoriaForm);
		return "/amministrazione/modificaConservatoria";
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaConservatoria.htm" , method = RequestMethod.POST, params = { "SalvaModifica" })
	public String salvaModificaConservatoria(ConservatoriaForm conservatoriaForm, BindingResult result, ModelMap modelMap) {

		amministrazioneValidation.validateConservatoriaInserimento(conservatoriaForm, result);
		
		if (result.hasErrors()) {
			return "/amministrazione/modificaConservatoria";
		}
		
		amministrazioneService.modificaConservatoria(conservatoriaForm);
		
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", " ", true);
		return "/amministrazione/ricercaConservatoria";
	}
	
	/******* FINE Amministrazione Conservatorie in base dati *********/

	/******* INIZIO Amministrazione Settore Attivita in base dati *********/
	
	@RequestMapping(value = "/funzioniAmministrazione/settoreAttivita.htm", method = RequestMethod.GET)
	public ModelAndView amministrazioneSettoreAttivita(ModelMap modelMap) {
		SettoreAttivitaForm settoreAttivitaForm = new SettoreAttivitaForm();
		
		ModelAndView mnv = new ModelAndView("/amministrazione/ricercaSettoreAttivita", "settoreAttivitaForm", settoreAttivitaForm);
		return mnv;
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaSettoreAttivita.htm" , method = RequestMethod.POST, params = { "Ricerca" })
	public String ricercaSettoreAttivita(SettoreAttivitaForm settoreAttivitaForm, BindingResult result, ModelMap modelMap) {
			
		List<SettoreAttivitaForm> listaSettoreAttivitaForm = amministrazioneService.listaSettoreAttivita(settoreAttivitaForm.getDescSettoreAtt(), settoreAttivitaForm.getDescSezioneAteco());

		if(listaSettoreAttivitaForm == null)
			listaSettoreAttivitaForm = new ArrayList<SettoreAttivitaForm>();

		modelMap.addAttribute("risultatiRicerca", listaSettoreAttivitaForm);
		return "/amministrazione/ricercaSettoreAttivita";
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaSettoreAttivita.htm" , method = RequestMethod.GET)
	public String ricercaSettoreAttivitaPaginazione(SettoreAttivitaForm settoreAttivitaForm, BindingResult result, ModelMap modelMap) {
		return ricercaSettoreAttivita(settoreAttivitaForm, result, modelMap);
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaSettoreAttivita.htm" , method = RequestMethod.POST, params = { "NuovoSettoreAttivita" })
	public String nuovoSettoreAttivita(SettoreAttivitaForm settoreAttivitaForm, BindingResult result, ModelMap modelMap) {
		modelMap.addAttribute("settoreAttivitaForm", settoreAttivitaForm);
		return "/amministrazione/nuovoSettoreAttivita";
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/nuovoSettoreAttivita.htm" , method = RequestMethod.POST, params = { "Indietro" })
	public String indietroRicercaSettoreAttivita(SettoreAttivitaForm settoreAttivitaForm, BindingResult result, ModelMap modelMap) {

		return ricercaSettoreAttivita(settoreAttivitaForm, result, modelMap);
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/nuovoSettoreAttivita.htm" , method = RequestMethod.POST, params = { "Inserisci" })
	public String inserisciSettoreAttivita(SettoreAttivitaForm settoreAttivitaForm, BindingResult result, ModelMap modelMap) {
		amministrazioneValidation.validateSettoreAttivitaInserimento(settoreAttivitaForm, result);
		if (result.hasErrors()) {
			return "/amministrazione/nuovoSettoreAttivita";
		}
		String descUtente = (recuperaIdCodiceUtente().toString());
		amministrazioneService.inserisciSettoreAttivita(settoreAttivitaForm.getDescSettoreAtt(), settoreAttivitaForm.getDescSezioneAteco(), settoreAttivitaForm.getDataFineValidita(), descUtente);
		
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", " ", true);
		return ricercaSettoreAttivita(settoreAttivitaForm, result, modelMap);
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaSettoreAttivita.htm" , method = RequestMethod.POST, params = { "ModificaSettoreAttivita" })
	public String modificaSettoreAttivita(SettoreAttivitaForm settoreAttivitaForm, BindingResult result, ModelMap modelMap) {
		if(settoreAttivitaForm.getCodiKIdSettoreAtt() == 0)	{
			super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
			return ricercaSettoreAttivita(settoreAttivitaForm, result, modelMap);
		}
		
		settoreAttivitaForm = amministrazioneService.getSettoreAttivita(settoreAttivitaForm.getCodiKIdSettoreAtt());
		modelMap.addAttribute("settoreAttivitaForm", settoreAttivitaForm);
		return "/amministrazione/modificaSettoreAttivita";
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaSettoreAttivita.htm" , method = RequestMethod.POST, params = { "SalvaModifica" })
	public String salvaModificaSettoreAttivita(SettoreAttivitaForm settoreAttivitaForm, BindingResult result, ModelMap modelMap) {

		amministrazioneValidation.validateSettoreAttivitaInserimento(settoreAttivitaForm, result);
		
		if (result.hasErrors()) {
			return "/amministrazione/modificaSettoreAttivita";
		}
		amministrazioneService.modificaSettoreAttivita(settoreAttivitaForm);
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", " ", true);
		return "/amministrazione/ricercaSettoreAttivita";
	}
	
	/******* FINE Amministrazione Settore Attivita in base dati *********/

	/******* INIZIO Amministrazione Condizioni Giuridiche in base dati *********/
	
	@RequestMapping(value = "/funzioniAmministrazione/condizioniGiuridiche.htm", method = RequestMethod.GET)
	public ModelAndView amministrazioneCondizioniGiuridiche(ModelMap modelMap) {
		CondizioneGiuridicaForm condizioneGiuridicaForm = new CondizioneGiuridicaForm();
		
		ModelAndView mnv = new ModelAndView("/amministrazione/ricercaCondizioneGiuridica", "condizioneGiuridicaForm", condizioneGiuridicaForm);
		return mnv;
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaCondizioneGiuridica.htm" , method = RequestMethod.POST, params = { "Ricerca" })
	public String ricercaCondizioneGiuridica(CondizioneGiuridicaForm condizioneGiuridicaForm, BindingResult result, ModelMap modelMap) {
				
		List<CondizioneGiuridicaForm> listaCondizioneGiuridicaForm = amministrazioneService.listaCondizioneGiuridica(condizioneGiuridicaForm.getDescCondGiur());

		if(listaCondizioneGiuridicaForm == null)
			listaCondizioneGiuridicaForm = new ArrayList<CondizioneGiuridicaForm>();
		
		modelMap.addAttribute("risultatiRicerca", listaCondizioneGiuridicaForm);
		return "/amministrazione/ricercaCondizioneGiuridica";
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaCondizioneGiuridica.htm" , method = RequestMethod.GET)
	public String ricercaCondizioneGiuridicaPaginazione(CondizioneGiuridicaForm condizioneGiuridicaForm, BindingResult result, ModelMap modelMap) {
		return ricercaCondizioneGiuridica(condizioneGiuridicaForm, result, modelMap);
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaCondizioneGiuridica.htm" , method = RequestMethod.POST, params = { "NuovaCondGiuridica" })
	public String nuovaCondizioneGiuridica(CondizioneGiuridicaForm condizioneGiuridicaForm, BindingResult result, ModelMap modelMap) {
		modelMap.addAttribute("condizioneGiuridicaForm", condizioneGiuridicaForm);
		return "/amministrazione/nuovaCondizioneGiuridica";
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/nuovaCondizioneGiuridica.htm" , method = RequestMethod.POST, params = { "Indietro" })
	public String indietroRicercaSettoreAttivita(CondizioneGiuridicaForm condizioneGiuridicaForm, BindingResult result, ModelMap modelMap) {

		return ricercaCondizioneGiuridica(condizioneGiuridicaForm, result, modelMap);
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/nuovaCondizioneGiuridica.htm" , method = RequestMethod.POST, params = { "Inserisci" })
	public String inserisciCondizioneGiuridica(CondizioneGiuridicaForm condizioneGiuridicaForm, BindingResult result, ModelMap modelMap) {
		amministrazioneValidation.validateCondizioneGiuridicaInserimento(condizioneGiuridicaForm, result);
		if (result.hasErrors()) {
			return "/amministrazione/nuovaCondizioneGiuridica";
		}
		String descUtente = (recuperaIdCodiceUtente().toString());
		amministrazioneService.inserisciCondGiuridica(condizioneGiuridicaForm.getDescCondGiur(), condizioneGiuridicaForm.getDataFineValidita(), descUtente);
		
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", " ", true);
		return ricercaCondizioneGiuridica(condizioneGiuridicaForm, result, modelMap);
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaCondizioneGiuridica.htm" , method = RequestMethod.POST, params = { "ModificaCondGiuridica" })
	public String modificaCondGiuridica(CondizioneGiuridicaForm condizioneGiuridicaForm, BindingResult result, ModelMap modelMap) {
		if(condizioneGiuridicaForm.getCodiKIdCondGiur() == 0)	{
			super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
			return ricercaCondizioneGiuridica(condizioneGiuridicaForm, result, modelMap);
		}
		
		condizioneGiuridicaForm = amministrazioneService.getCondGiuridica(condizioneGiuridicaForm.getCodiKIdCondGiur());
		modelMap.addAttribute("condizioneGiuridicaForm", condizioneGiuridicaForm);
		return "/amministrazione/modificaCondizioneGiuridica";
	}
	
	@RequestMapping(value = "/funzioniAmministrazione/visualizzaRicercaCondizioneGiuridica.htm" , method = RequestMethod.POST, params = { "SalvaModifica" })
	public String salvaModificaCondGiuridica(CondizioneGiuridicaForm condizioneGiuridicaForm, BindingResult result, ModelMap modelMap) {

		amministrazioneValidation.validateCondizioneGiuridicaInserimento(condizioneGiuridicaForm, result);
		
		if (result.hasErrors()) {
			return "/amministrazione/modificaCondizioneGiuridica";
		}
		amministrazioneService.modificaCondGiuridica(condizioneGiuridicaForm);
		
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", " ", true);
		return "/amministrazione/ricercaCondizioneGiuridica";
	}
	
	/******* FINE Amministrazione Condizioni Giuridiche in base dati *********/

}
