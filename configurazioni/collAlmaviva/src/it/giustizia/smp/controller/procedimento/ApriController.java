package it.giustizia.smp.controller.procedimento;

import it.giustizia.smp.controller.BreadcrumbsController;
import it.giustizia.smp.controller.MenuController;
import it.giustizia.smp.dto.pg_cameralini.IntestatarioDto;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.NumeroRegistroForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.ProfiloForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.jms.JmsMessage;
import it.giustizia.smp.jms.JmsMessageProducer;
import it.giustizia.smp.model.TProcedimento;
import it.giustizia.smp.multidb.UserContextHolder;
import it.giustizia.smp.service.CommonService;
import it.giustizia.smp.service.ProcedimentoService;
import it.giustizia.smp.service.cameralini.ProcedimentoServiceCam;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.utility.UtilityLogger;
import it.giustizia.smp.validation.ProcedimentoValidation;
import it.giustizia.smp.webservice.client.documentale.stub.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * ApriController
 * 
 * @author Almaviva S.p.A.
 *         Classe Controller per la gestione del flusso
 *         inerente alle ricerche dei procedimenti
 */
@Controller
public class ApriController extends BreadcrumbsController {

	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private CommonService			commonService;
	@Autowired
	private ProcedimentoService		procedimentoService;
	@Autowired
	protected ProcedimentoServiceCam 	procedimentoServiceCam;
	@Autowired
	private ProcedimentoValidation	procedimentoValidation;
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

	
	

	
	// ************ INIZIO COPERTINA PROCEDIMENTO ****************	

	@RequestMapping(value = "/procedimento/apri/copertinaProcedimento.htm")
	public ModelAndView copertinaProcedimento(ProcedimentoForm procedimento, BindingResult resultBinding, HttpServletRequest request) {
		ProcedimentoForm proced = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO)));
		//Giacomo Aggiunge la breadcrumbs
		addBreadcrumbs();
		return caricaDettaglioProcedimento(proced, resultBinding, request, null);
	}

	@RequestMapping(value = "/procedimento/apri/dettaglioProcedimento.htm", method = RequestMethod.GET)
	public ModelAndView copertinaProcedimentoCambioPaginaTabella(ProcedimentoForm procedimento, BindingResult result, HttpServletRequest request) {

		return copertinaProcedimento(procedimento, result, request);
	}
	
	@RequestMapping(value = "/procedimento/apri/dettaglioProcedimento.htm", method = RequestMethod.POST, params = { "annullaProcedimento" })
	public ModelAndView annullaProcedimento(ProcedimentoForm procedimento, BindingResult result, HttpServletRequest request) {
		int idUffOwn = recuperaIdUfficioUtente();
		
		if (procedimentoService.hasAttiInLavorazioni(recuperaProcedimento().getIdProcedimento(),idUffOwn)) {
			// Se esistono atti in lavorazione diversi dalla proposta blocco l'utente con un messaggio
			super.setMessage(Constants.MESSAGE, "archiviazione.errore.presenzaattiinlavorazione", null, false);
		} else {
			super.setMessage(Constants.MESSAGE, "procedimento.canferma.annulla", " ", true);
			request.setAttribute("pulsanteAnnulla", "si");
		}
		addBreadcrumbs();
		return copertinaProcedimento(procedimento, result, request);
	}
	
	@RequestMapping(value = "/procedimento/apri/dettaglioProcedimento.htm", method = RequestMethod.POST, params = { "confermaAnnullaProcedimento" })
	public ModelAndView confermaAnnullaProcedimento(ProcedimentoForm procedimento, BindingResult result, HttpServletRequest request) {
		
		//super.setMessage(Constants.SUCCESS_MESSAGE, "procedimento.canferma.annulla", " ", true);
		ProcedimentoForm proced = recuperaProcedimento();
		long idProcedimento = proced.getIdProcedimento();
		Integer idUfficioUtente = recuperaIdUfficioUtente();
		String codUtente = recuperaIdCodiceUtente();
		String tipoUtente = (String) request.getSession().getAttribute(Constants.TIPO_UTENTE);
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation", "ANNULLAMENTO",true);
		procedimentoService.annullaStatoDelProcedimento(idProcedimento, idUfficioUtente, codUtente, tipoUtente, 0);
		addBreadcrumbs();
		return copertinaProcedimento(procedimento, result, request);
	}

	// ************ FINE COPERTINA PROCEDIMENTO ****************	


	// ************ INIZIO RICERCA PROCEDIMENTO PER CRITERI ****************
	
	
	
	

		@RequestMapping(value = "/procedimento/apri/visualizzaRicercaPerCriteri.htm", method = RequestMethod.GET)
		public ModelAndView visualizzaRicercaPerCriteri(Model model) {
			//Giacomo 12/07/2013 Aggiunta Breadcrumbs
	//		cleanBreadcrumb();
			addBreadcrumbs();
			
			super.saveIntoSession(Constants.TIPO_FASCICOLO_MERITO, Constants.TIPO_FASCICOLO, true);
			//super.removeFromSession("idTpUffRic");
			model.addAttribute("tipiProcedimento", getTipiProcedimento());
			return new ModelAndView("/procedimento/apri/ricercaPerCriteri", "procedimento", new ProcedimentoForm());
		}
		
		@RequestMapping(value = "/procedimento/apri/ricercaPerCriteri.htm", method = RequestMethod.POST)
		public ModelAndView caricaCombo(@Valid @ModelAttribute("procedimento") ProcedimentoForm procedimento, BindingResult result) {

			return new ModelAndView("/procedimento/apri/ricercaPerCriteri", "procedimento", procedimento);
		}		
		
		@RequestMapping(value = "/procedimento/apri/riesame/visualizzaRicercaPerCriteri.htm", method = RequestMethod.GET)
		public ModelAndView visualizzaRicercaPerCriteriRiesame() {
			
			//request.getSession().setAttribute(Constants.ID_TIPO_PROCEDIMENTO, Constants.TIPOPROC_RIESAME_PROVV_QUESTURA);
			super.saveIntoSession(Constants.TIPO_FASCICOLO_OPP_DIVIETI_QUESTORE, Constants.TIPO_FASCICOLO, true);
			//super.removeFromSession("idTpUffRic");
			return new ModelAndView("/procedimento/apri/riesame/ricercaPerCriteri", "procedimento", new ProcedimentoForm());
		}
	
		@RequestMapping(value = "/procedimento/apri/visualizzaRicercaPerCriteriIndietro.htm", method = RequestMethod.GET)
		public ModelAndView visualizzaRicercaPerCriteriIndietro() {
			
			request.getSession().removeAttribute("urlIndietroCopertinaProc");
			Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
			MenuController.caricaMenu(Constants.ID_PADRE_MENU, request);
			ModelAndView mnv = new ModelAndView("/procedimento/apri/ricercaPerCriteri", "procedimento", new ProcedimentoForm());
			mnv.addObject("risultati", procedimentoService.listaProcedimentiByCriteria(idUfficioUtente, (ProcedimentoForm)request.getSession().getAttribute("procDiRicerca"), null));
			request.getSession().removeAttribute("procDiRicerca");
			//super.removeFromSession("idTpUffRic");
			return mnv;
		}
	
	
		@RequestMapping(value = "/procedimento/apri/ricercaPerCriteri.htm", method = RequestMethod.POST, params = { "Ricerca" })
		public ModelAndView ricercaCriteri(@Valid @ModelAttribute("procedimento") ProcedimentoForm procedimento, BindingResult result, HttpServletRequest request) {
	
			//Giacomo 12/07/2013 Aggiunta Breadcrumbs
			addBreadcrumbs("POST","/procedimento/apri/ricercaPerCriteri.htm","Ricerca");
			
			//a seconda del tipo procedimento imposta la ModelAndView corrispondente
			ModelAndView mnv = null;
			if(procedimento.getTipoProcedimento().getId().equals(Constants.TIPOPROC_RIESAME_PROVV_QUESTURA))
				mnv = new ModelAndView("/procedimento/apri/riesame/ricercaPerCriteri", "procedimento", procedimento);
			else
				mnv = new ModelAndView("/procedimento/apri/ricercaPerCriteri", "procedimento", procedimento);
			
			
			return ricercaProcedimentoPerCriteri(procedimento,mnv,result,request,false);
		}
	
		// metodo che serve per l'ordinamento della ricerca
		@RequestMapping(value = "/procedimento/apri/ricercaPerCriteri.htm", method = RequestMethod.GET)
		public ModelAndView ordinamentoCriteri(ProcedimentoForm procedimento, BindingResult result, HttpServletRequest request) {
			
			if(procedimento.getStatoProcedimento()==null)
				procedimento.setStatoProcedimento( new ComboForm("-1", null) );
			if(procedimento.getPmAssegnatario()==null)
				procedimento.setPmAssegnatario( new ComboForm("-1", null) );
			if(procedimento.getTipoProcedimento()==null)
				procedimento.setTipoProcedimento( new ComboForm("-1", null) );
			if(procedimento.getTipoProcedente()==null)
				procedimento.setTipoProcedente( new ComboForm("-1", null) );
			if(procedimento.getProcedente()==null)
				procedimento.setProcedente( new ComboForm("-1", null) );
			return ricercaCriteri(procedimento, result, request);
		}

		@RequestMapping(value = "/procedimento/apri/ricercaPerCriteri.htm", method = RequestMethod.POST, params = { "Dettaglio" })
		public ModelAndView dettaglioProcedimentoRicercaCriteri(@Valid @ModelAttribute("procedimento") ProcedimentoForm procedimento, BindingResult result, HttpServletRequest request) {
			
			if (result.hasErrors()) {
				return new ModelAndView("/procedimento/apri/ricercaPerCriteri.htm", "procedimento", procedimento);
			}
			
			//Giacomo 12/07/2013 Aggiunta Breadcrumbs		
//			addBreadcrumbs("Dettaglio");
			addBreadcrumbs("GET","/procedimento/apri/copertinaProcedimento.htm",null);
			/*
			String procDiRicercaQueryString  = "/procedimento/apri/visualizzaRicercaPerCriteriIndietro.htm";
			if(request.getSession().getAttribute("procDiRicerca")!=null)
				procDiRicercaQueryString += this.getProcQueryString((ProcedimentoForm)request.getSession().getAttribute("procDiRicerca"));
			else
				procDiRicercaQueryString += this.getProcQueryString(procedimento);
			request.getSession().setAttribute("urlIndietroCopertinaProc", procDiRicercaQueryString);
			*/
			
			return caricaDettaglioProcedimento(procedimento, result, request, "/procedimento/apri/ricercaPerCriteri");
		}
		

		
	
	// ************ FINE RICERCA PROCEDIMENTO PER CRITERI ****************//

	// ************ INIZIO RICERCA PROCEDIMENTO PUNTUALE****************// 

	@RequestMapping(value = "/procedimento/apri/ricercaPuntuale.htm", method = RequestMethod.GET)
	public ModelAndView visualizzaRicercaPuntuale() {

		// metto in sessione il tipo procedimento 1 (vale anche se sto cercando
		// prevenzioni che hanno tipo_proc =2 o =4
	//	request.getSession().setAttribute(Constants.ID_TIPO_PROCEDIMENTO, Constants.TIPOPROC_PERSONALE_E_PATRIMONIALE);
		super.saveIntoSession(Constants.TIPO_FASCICOLO_MERITO, Constants.TIPO_FASCICOLO, true);
		//super.removeFromSession("idTpUffRic");
		//request.getSession().removeAttribute("urlIndietroCopertinaProcPuntuale");
		MenuController.caricaMenu(Constants.ID_PADRE_MENU, request);
		ProcedimentoForm procedimento = new ProcedimentoForm();
		
		//Giacomo - pulisco la breadcrumb
		cleanBreadcrumb();
		addBreadcrumbs();
		
		return new ModelAndView("/procedimento/apri/ricercaPuntuale", "procedimento", procedimento);

		
	}

	@RequestMapping(value = "/procedimento/apri/riesame/ricercaPuntuale.htm", method = RequestMethod.GET)
	public ModelAndView visualizzaRicercaPuntualeRiesame() {

		// metto in sessione il tipo procedimento 10
	//	request.getSession().setAttribute(Constants.ID_TIPO_PROCEDIMENTO, Constants.TIPOPROC_RIESAME_PROVV_QUESTURA);
		super.saveIntoSession(Constants.TIPO_FASCICOLO_OPP_DIVIETI_QUESTORE, Constants.TIPO_FASCICOLO, true);
		//super.removeFromSession("idTpUffRic");
		//request.getSession().removeAttribute("urlIndietroCopertinaProcPuntuale");
		MenuController.caricaMenu(Constants.ID_PADRE_MENU, request);
		ProcedimentoForm procedimento = new ProcedimentoForm();
		return new ModelAndView("/procedimento/apri/ricercaPuntuale", "procedimento", procedimento);
	}
	
	@RequestMapping(value = "/procedimento/apri/visualizzaRicercaProcedimentoAccertamento.htm", method = RequestMethod.GET)
	public ModelAndView visualizzaRicercaProcedimentoAccertamento(Model model) {

		super.saveIntoSession(Constants.TIPO_FASCICOLO_ACCERTAMENTO, Constants.TIPO_FASCICOLO, true);
		super.saveIntoSession(null, Constants.ID_TIPO_PROCEDIMENTO, true);
		//super.removeFromSession("idTpUffRic");
		return visualizzaRicercaProcedimento(model);
	}
	
	@RequestMapping(value = "/procedimento/apri/dettaglioProcedimento.htm", method = RequestMethod.POST, params = { "RicercaPuntuale" })
	public ModelAndView dettaglioProcedimentoRicercaPuntuale(@Valid @ModelAttribute("procedimento") ProcedimentoForm procedimento, BindingResult bindingResult, HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			return new ModelAndView("/procedimento/apri/ricercaPuntuale", "procedimento", procedimento);
		}
		procedimentoValidation.validateCriteriRicercaPuntuale(procedimento, bindingResult);
		if (bindingResult.hasErrors()) {
			return new ModelAndView("/procedimento/apri/ricercaPuntuale", "procedimento", procedimento);
		}
			

		return dettaglioProcedimento( procedimento,  bindingResult, "/procedimento/apri/ricercaPuntuale",  request);
	}			


	
	@RequestMapping(value = "/procedimento/apri/aggravamento/ricerca/puntuale.htm", method = RequestMethod.GET)
	public ModelAndView aggravamentoRicercaPuntuale() {

		super.saveIntoSession(Constants.TIPO_FASCICOLO_AGGRAVAMENTO, Constants.TIPO_FASCICOLO, true);
		request.getSession().removeAttribute("urlIndietroCopertinaProcPuntuale");
		MenuController.caricaMenu(Constants.ID_PADRE_MENU, request);
		ProcedimentoForm procedimento = new ProcedimentoForm();
		return new ModelAndView("/procedimento/apri/aggravamentoRicercaPuntuale", "procedimento", procedimento);
	}

	@RequestMapping(value = "/procedimento/apri/aggravamento/dettaglio.htm", method = RequestMethod.POST, params = { "RICERCA" })
	public ModelAndView aggravamentoRicercaPuntualeRicerca(@Valid @ModelAttribute("procedimento") ProcedimentoForm procedimento, BindingResult result, HttpServletRequest request) {

		if (result.hasErrors()) {
			return new ModelAndView("/procedimento/apri/aggravamentoRicercaPuntuale", "procedimento", procedimento);
		}
		procedimentoValidation.validateCriteriRicercaPuntuale(procedimento, result);
		if (result.hasErrors()) {
			return new ModelAndView("/procedimento/apri/aggravamentoRicercaPuntuale", "procedimento", procedimento);
		}
		int idUfficio = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		int idTipoFascicolo = super.getIdTipoFascicolo(false);

		List<Long> procedimentoList = procedimentoService.getIdProcByAnnoNumProc(idUfficio, procedimento.getAnno(), procedimento.getNumero(), idTipoFascicolo);
		if(procedimentoList!=null && procedimentoList.size()>0) { 
			procedimento.setIdProcedimento(procedimentoList.get(0));
		}
		return caricaDettaglioProcedimento(procedimento, result, request, "/procedimento/apri/aggravamentoRicercaPuntuale");
	}
	
	// ************ FINE RICERCA PROCEDIMENTO PUNTUALE****************//
	
	

	// ************ INIZIO RICERCA PROCEDIMENTO ****************// 	
	  
	   /**
	    * visualizzaRicercaProcedimento
	    * Visualizza la form di ricerca del procedimento (ricerca puntuale e per criteri unite nella stessa form)
	    *      
	    * @return ModelAndView
	    */
		@RequestMapping(value = "/procedimento/apri/visualizzaRicercaProcedimento.htm", method = RequestMethod.GET)
		public ModelAndView visualizzaRicercaProcedimento(Model model) {

			MenuController.caricaMenu(Constants.ID_PADRE_MENU, request);
			
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
			
			
			//Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
			
			model.addAttribute("listaUffici", getListaUffici(request));
			//
		
			
			cleanBreadcrumb();
			addBreadcrumbs();

			
			return new ModelAndView("/procedimento/apri/ricercaProcedimento", "procedimento", new ProcedimentoForm());	
		}

		/**
	    * visualizzaRicercaProcedimentoPrevenzione
	    * Visualizza la form di ricerca del procedimento (ricerca puntuale e per criteri unite nella stessa form)
	    *      
	    * @return ModelAndView
	    */
		@RequestMapping(value = "/procedimento/apri/visualizzaRicercaProcedimentoPrevenzione.htm", method = RequestMethod.GET)
		public ModelAndView visualizzaRicercaProcedimentoPrevenzione(Model model) {

			super.saveIntoSession(Constants.TIPO_FASCICOLO_MERITO, Constants.TIPO_FASCICOLO, true);
			super.saveIntoSession(null, Constants.ID_TIPO_PROCEDIMENTO, true);
			//super.removeFromSession("idTpUffRic");
			return visualizzaRicercaProcedimento(model);
		}
		
		/*
		@RequestMapping(value = "/procedimento/apri/visualizzaRicercaProcedimentoPrevenzionePerRicorso.htm", method = RequestMethod.GET)
		public ModelAndView visualizzaRicercaProcedimentoPrevenzionePerRicorso(Model model) {
			
			super.saveIntoSession(Constants.TIPO_FASCICOLO_MERITO, Constants.TIPO_FASCICOLO, true);
			super.saveIntoSession(null, Constants.ID_TIPO_PROCEDIMENTO, true);
			super.saveIntoSession(Constants.TIPO_UFFICIO_TRIBUNALE,"idTpUffRic",true);
			return visualizzaRicercaProcedimento(model);
		}		

		@RequestMapping(value = "/procedimento/apri/visualizzaRicercaProcedimentoPrevenzionePerUdienza_Cassazione.htm", method = RequestMethod.GET)
		public ModelAndView visualizzaRicercaProcedimentoPrevenzionePerUdienza_Cassazione(Model model) {

			super.saveIntoSession(Constants.TIPO_FASCICOLO_MERITO, Constants.TIPO_FASCICOLO, true);
			super.saveIntoSession(null, Constants.ID_TIPO_PROCEDIMENTO, true);
			super.saveIntoSession(String.valueOf(Constants.TIPO_UFFICIO_CORTE_D_APPELLO), "idTpUffRic", true);
			return visualizzaRicercaProcedimento(model);
		}	
		*/
		/**
	    * visualizzaRicercaProcedimentoOpposizione
	    * Visualizza la form di ricerca del procedimento (ricerca puntuale e per criteri unite nella stessa form)
	    *      
	    * @return ModelAndView
	    */
		@RequestMapping(value = "/procedimento/apri/visualizzaRicercaProcedimentoOpposizioneRestituzione.htm", method = RequestMethod.GET)
		public ModelAndView visualizzaRicercaProcedimentoOpposizioneRestituzione(Model model) {

			super.saveIntoSession(Constants.TIPO_FASCICOLO_OPP_REVOCA_SEQUESTRO, Constants.TIPO_FASCICOLO, true);
			super.saveIntoSession(Constants.TIPOPROC_OPPOSIZIONE_ALLA_RESTITUZIONE, Constants.ID_TIPO_PROCEDIMENTO, true);
			//super.removeFromSession("idTpUffRic");
			return visualizzaRicercaProcedimento(model);
		}
		
		@RequestMapping(value = "/procedimento/apri/visualizzaRicercaRevocazione.htm", method = RequestMethod.GET)
		public ModelAndView visualizzaRicercaProcedimentoRevocazione(Model model) {

			super.saveIntoSession(Constants.TIPO_FASCICOLO_REVOCAZIONE, Constants.TIPO_FASCICOLO, true);
			super.saveIntoSession(Constants.TIPOPROC_REVOCAZIONE_CONFISCA, Constants.ID_TIPO_PROCEDIMENTO, true);
			//super.removeFromSession("idTpUffRic");
			return visualizzaRicercaProcedimento(model);
		}
		
		@RequestMapping(value = "/procedimento/apri/visualizzaRicercaProcedimentoAggravamento.htm", method = RequestMethod.GET)
		public ModelAndView visualizzaRicercaProcedimentoAggravamento(Model model) {

			super.saveIntoSession(Constants.TIPO_FASCICOLO_AGGRAVAMENTO, Constants.TIPO_FASCICOLO, true);
			super.saveIntoSession(Constants.TIPOPROC_AGGRAVAMENTO_PERSONALE, Constants.ID_TIPO_PROCEDIMENTO, true);
			//super.removeFromSession("idTpUffRic");
			return visualizzaRicercaProcedimento(model);
		}
		
		@RequestMapping(value = "/procedimento/apri/visualizzaRicercaProcedimentoDivietiQuestore.htm", method = RequestMethod.GET)
		public ModelAndView visualizzaRicercaProcedimentoDivietiQuestore(Model model) {

			super.saveIntoSession(Constants.TIPO_FASCICOLO_OPP_DIVIETI_QUESTORE, Constants.TIPO_FASCICOLO, true);
			super.saveIntoSession(Constants.TIPOPROC_OPPOSIZIONE_DIVIETI_QUESTORE, Constants.ID_TIPO_PROCEDIMENTO, true);
			//super.removeFromSession("idTpUffRic");
			return visualizzaRicercaProcedimento(model);
		}
		
		@RequestMapping(value = "/procedimento/apri/ricercaProcedimento.htm", method = RequestMethod.POST)
		public ModelAndView visualizzaRicercaProcedimentoPost(ProcedimentoForm procedimento, BindingResult result) {
			
			
			ModelAndView mnv = new ModelAndView("/procedimento/apri/ricercaProcedimento", "procedimento", procedimento);
			
            if (request.getSession().getAttribute(Constants.TIPO_FASCICOLO)!= null){
				
            	mnv.addObject("tipiProcedimento", getTipiProcedimento());	
			}
            
            else {
            	
            	if (procedimento.getTipoFascicolo()!= null && procedimento.getTipoFascicolo().getId()!= null && 
						 Long.valueOf(procedimento.getTipoFascicolo().getId()).longValue() != -1	)
            		mnv.addObject("tipiProcedimento",commonService.listaTipiDiProcedimento(Integer.parseInt(procedimento.getTipoFascicolo().getId())));
            }
			
            
			return mnv;
		}	
	

		/**
		 * ricercaProcedimento
		 * 
		 * ricerca procedimento che recepisce le informazioni sia nel caso di ricerca puntuale
		 * che per ricerca per criterio
		 * 
		 * @param procedimento
		 * @param result
		 * @param request
		 * @return ModelAndView
		 */
		@RequestMapping(value = "/procedimento/apri/ricercaProcedimento.htm", method = RequestMethod.POST, params = { "Ricerca" })
		public ModelAndView ricercaProcedimento(@Valid @ModelAttribute("procedimento") ProcedimentoForm procedimento, BindingResult result, HttpServletRequest request) {
	
			//ProfiloForm profilo = (ProfiloForm)session.getAttribute(Constants.PROFILO);
			//long idTipoUffUtente = profilo.getIdTipoUfficio();
			//long idTipoUffSel = new Long(procedimento.getRelProcTpUff().getId());

			//impostato solo per la ricerca procedimenti di prevenzione in PG
			//String idTpUffRic = (String)session.getAttribute("idTpUffRic");
			
			if(procedimento != null && (procedimento.getAnno() != null || procedimento.getNumero() != null)) {
				if (result.hasErrors()) {
					return new ModelAndView("/procedimento/apri/ricercaProcedimento", "procedimento", procedimento);
				}
				procedimentoValidation.validateCriteriRicercaPuntuale(procedimento, result);
				if (result.hasErrors()) {
					return new ModelAndView("/procedimento/apri/ricercaProcedimento", "procedimento", procedimento);
				}			
			}
			
			addBreadcrumbs("POST","/procedimento/apri/ricercaProcedimento.htm","Ricerca");
			
			/*usando la stessa jsp sono stato costretto a mappare l'anno della ricerca per criteri con il nome di
			  'annoCriterio', quindi per non modificare la store procedure ho scelto di passarlo al campo 'anno'*/
			if(procedimento.getAnno() == null) {
				procedimento.setAnno(procedimento.getAnnoCriterio());
			} else {
				procedimento.setNumeroMin(procedimento.getNumero());
				procedimento.setNumeroMax(procedimento.getNumero());
			}
			
			ModelAndView mnv = new ModelAndView("/procedimento/apri/ricercaProcedimento", "procedimento", procedimento);
		    mnv = ricercaProcedimentoPerCriteri(procedimento,mnv,result,request,false);
		    List<ProcedimentoForm> risultati = (List)mnv.getModelMap().get("risultati");
		    
		    //se l'elenco dei risultati produce un solo procedimento
		    //visualizzo la pagina del dettaglio del fascicolo
		    if(risultati!=null && risultati.size()==1) {
				procedimento = risultati.get(0);
				mnv = caricaDettaglioProcedimento(procedimento, result, request, "/procedimento/apri/ricercaProcedimento");
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
		

            //ordinamento solo nel caso di ricerca per criteri		
			@RequestMapping(value = "/procedimento/apri/ricercaProcedimento.htm", method = RequestMethod.GET) 
			public ModelAndView ordinamentoRicercaProcedimento(ProcedimentoForm procedimento, BindingResult result, HttpServletRequest request) {
				
				if(procedimento.getStatoProcedimento()==null)
					procedimento.setStatoProcedimento( new ComboForm("-1", null) );
				if(procedimento.getPmAssegnatario()==null)
					procedimento.setPmAssegnatario( new ComboForm("-1", null) );
				if(procedimento.getTipoProcedimento()==null)
					procedimento.setTipoProcedimento( new ComboForm("-1", null) );
				if(procedimento.getTipoProcedente()==null)
					procedimento.setTipoProcedente( new ComboForm("-1", null) );
				if(procedimento.getProcedente()==null)
					procedimento.setProcedente( new ComboForm("-1", null) );
				
				return ricercaProcedimento(procedimento, result, request);
			}			
			
			//Dettaglio successivo alla scelta dall'elenco della ricerca per criteri
			@RequestMapping(value = "/procedimento/apri/ricercaProcedimento.htm", method = RequestMethod.POST, params = { "Dettaglio" })
			public ModelAndView dettaglioRicercaProcedimento(@Valid @ModelAttribute("procedimento") ProcedimentoForm procedimento, BindingResult result, HttpServletRequest request) {
			
				procedimentoValidation.validateDettaglioProcedimento(procedimento, result, request);
				
				if (result.hasErrors()) {
					//return new ModelAndView("/procedimento/apri/ricercaProcedimento", "procedimento", procedimento);
					//return ricercaProcedimento(procedimento, result, request);
					ModelAndView mnv = new ModelAndView("/procedimento/apri/ricercaProcedimento", "procedimento", procedimento);
				    mnv = ricercaProcedimentoPerCriteri(procedimento,mnv,result,request,true);
				    return mnv;
					
				}
				
				//Giacomo 12/07/2013 Aggiunta Breadcrumbs		
				addBreadcrumbs("GET","/procedimento/apri/copertinaProcedimento.htm",null);
			
				return caricaDettaglioProcedimento(procedimento, result, request, "/procedimento/apri/ricercaProcedimento");
			}
						
			
		
	// ************ FINE RICERCA PROCEDIMENTO ****************// 			

		
	// ************ INIZIO CARICAMENTO COMBO ****************// 			
		@ModelAttribute("pmAssegnatari")
		public List<ComboForm> getPmAssegnatari(HttpServletRequest request) {

			Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
			//return commonService.listaPmAssegnatari(idUfficioUtente);
			return commonService.getMagistratiByUff(idUfficioUtente);
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

		@ModelAttribute("tipiProcedente")
		public List<ComboForm> getTipiProcedente(HttpServletRequest request) {

			List<ComboForm> result = null;
			Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
			result = commonService.listaTipiProcedente(idUfficioUtente);
			if(result!=null && result.size()>0) result.add(new ComboForm("0","Altro"));
			return result;
		}
		
		@ModelAttribute("tipiProcedimento")
		
		public List<ComboForm> getTipiProcedimento() {
	
			return commonService.listaTipiDiProcedimento(super.getIdTipoFascicolo(false));
 
		}	
		//simone 03/02/2014
		@ModelAttribute("tipoDiFascicolo")
		public List<ComboForm> getTipiFascicolo() {
		//	
			return commonService.listaTipiFascicolo(super.getIdTipoFascicolo(false));
		}
		
		@ModelAttribute("listaUffici")
		public List<ComboForm> getListaUffici(HttpServletRequest request) {
		//	prova. qui ci va il metodo che carica la combo degli uffici
			Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
			
			return commonService.getListaUffici(idUfficioUtente.intValue());
		}
		
	// ************ FINE CARICAMENTO COMBO ****************// 			
	
	//  ***************** INZIO METODI PRIVATI **********************//


		/**
		 * dettaglioProcedimento
		 * @param procedimento
		 * @param bindingResult
		 * @param url
		 * @param request
		 * @return ModelAndView
		 */
		public ModelAndView dettaglioProcedimento(ProcedimentoForm procedimento, BindingResult bindingResult, String url, HttpServletRequest request) {
		
			return dettaglioProcedimento(procedimento, bindingResult, url, request,true);
			
		}		
		
		/**
		 * dettaglioProcedimento
		 * @param procedimento
		 * @param bindingResult
		 * @param url
		 * @param request
		 * @return ModelAndView
		 */
		public ModelAndView dettaglioProcedimento(ProcedimentoForm procedimento, BindingResult bindingResult, String url, HttpServletRequest request,boolean addBreadcrumbs) {
			int idUfficio = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
			int idTipoFascicolo = super.getIdTipoFascicolo(false);
			List<Long> procedimentoList = procedimentoService.getIdProcByAnnoNumProc(idUfficio, procedimento.getAnno(), procedimento.getNumero(), idTipoFascicolo);
			if(procedimentoList!=null && procedimentoList.size()==1) {
				if (addBreadcrumbs)
					addBreadcrumbs("POST","/procedimento/apri/ricercaProcedimento.htm","Ricerca");
				Long idProcedimento = (Long)procedimentoList.get(0);
				procedimento.setIdProcedimento(idProcedimento);
				return caricaDettaglioProcedimento(procedimento, bindingResult, request, url);				
			} else {
				ModelAndView mnv = new ModelAndView("/procedimento/apri/ricercaProcedimento", "procedimento", procedimento);
				if (addBreadcrumbs)
					addBreadcrumbs("POST","/procedimento/apri/ricercaProcedimento.htm","Ricerca");
				Long numeroProc = procedimento.getNumero();
				Short annoProc = procedimento.getAnno();
				procedimento.setNumeroMin(numeroProc);
				procedimento.setNumeroMax(numeroProc);				
			    ModelAndView mv = ricercaProcedimentoPerCriteri(procedimento, mnv, bindingResult, request,false);
			    procedimento.setNumeroMin(null);
			    procedimento.setNumeroMax(null);
			    procedimento.setAnno(annoProc);
			    procedimento.setNumeroRicerca(""+numeroProc);
			    return mv;
			}
		}
		
		
		/**		
		 * ricercaProcedimentoPerCriteri
		 * @param procedimento
		 * @param mnv
		 * @param result
		 * @param request
		 * @return ModelAndView
		 */
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
			

			MenuController.caricaMenu(Constants.ID_PADRE_MENU, request);
			
			if (result.hasErrors() && !isError) {
				return mnv;
			}
			Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
			request.getSession().setAttribute("procDiRicerca", procedimento);
			//procedimento.setTipoFascicoloRicerca(super.getIdTipoFascicolo(false));
			
			procedimento.setTipoFascicoloRicerca(idTipoFasc);
			
			//String idTpUffRic = (String)session.getAttribute("idTpUffRic");
			//Integer idTpUffRicInt = null;
			//if(idTpUffRic!=null) idTpUffRicInt = new Integer(idTpUffRic);
			
			List<ProcedimentoForm> lista = procedimentoService.listaProcedimentiByCriteria(idUfficioUtente, procedimento, null);
			
			mnv.addObject("risultati", lista);
			
			return mnv;
		}				
		
	private void sendMessage(String idFascicolo, String annoFascicolo, String tipoFascicolo) {
		// jmsMessage.setCodIdUfficio(recuperaIdUfficioUtente().toString());
//		jmsMessage.setData(new Date());
		// jmsMessage.setDescUfficio(desc);
		try {
			jmsMessage.setIdFascicolo(idFascicolo);
			jmsMessage.setAnnoFascicolo(annoFascicolo);
			jmsMessage.setCodIdUfficio(recuperaIdUfficioUtente().toString());
			jmsMessage.setSchema(UserContextHolder.getUserType());
			jmsMessage.setIdUtente(recuperaIdCodiceUtente());
			jmsMessage.setTipoFascicolo(tipoFascicolo);
			
		
			jmsService.sendMessage(jmsMessage);
		} catch (Exception e) {
			logger.error("impossibile inviare messaggio di log: " + e.getMessage()+ "ID_Fascicolo = " + idFascicolo+" - " + annoFascicolo + " - " + tipoFascicolo	);
		}
	}
		
	/**
	 * 	caricaDettaglioProcedimento
	 * @param form
	 * @param result
	 * @param request
	 * @param pagina
	 * @return ModelAndView
	 */
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

		/*if(idTipoFascicolo!=Integer.parseInt(procedimento.getTipoProcedimento().getId())){
			//gestione errore per procedimento non trovato per quel tipo fascicolo
			
		}*/
		request.getSession().setAttribute("statoProcedimento", procedimento.getStatoProcedimento().getId());
		String view = "/procedimento/apri/dettaglioProcedimento";
	    String urlIndietroPuntuale = "/procedimento/apri/ricercaPuntuale.htm";
//	    String urlIndietroPuntuale = "/procedimento/apri/indietro_giacomo_da_fare.htm";
        if (pagina != null) {
            if(pagina.equals("/procedimento/apri/ricercaPerCriteri")){
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
				view = "/procedimento/apri/riesame/dettaglioProcedimentoRiesame";
//				urlIndietroPuntuale = "/procedimento/apri/riesame/ricercaPuntuale.htm";
			} else if (idTipoFascicolo==Constants.TIPO_FASCICOLO_ACCERTAMENTO) {
				view = "/procedimento/apri/dettaglioProcedimentoAccertamento";
//				urlIndietroPuntuale = "/procedimento/apri/accertamento/ricercaPuntuale.htm";
			}
//		}
		
		if (idTipoFascicolo==Constants.TIPO_FASCICOLO_AGGRAVAMENTO ||
			idTipoFascicolo==Constants.TIPO_FASCICOLO_CAMERA_DI_CONSIGLIO	) {
			Long idPadre = procedimentoService.getIdPadreAggravamento(procedimento.getIdProcedimento());
			if(idPadre != null){
				ProcedimentoForm padre = procedimentoService.getDettaglioProcedimento(idPadre);
				procedimento.setAnnoPadre(padre.getAnno());
				procedimento.setNumeroPadre(padre.getNumero());
				NumeroRegistroForm formPadre = procedimentoService.getNumeroRegistroPadre(procedimento.getIdProcedimento());
				if(formPadre!=null) 	
					procedimento.setUfficioPadre(formPadre.getUfficio());
			}
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
				 
				 SoggettoForm soggettoDaCorregere = null;
	
				if (procedimento.getTipoProcedimento().getId().equals(""+Constants.TIPOPROC_MODIFICA_PATRIMONIALE_CAM) ||
						procedimento.getTipoProcedimento().getId().equals(""+Constants.TIPOPROC_TERZO_INTERVENIENTE_CAM) ||
							procedimento.getTipoProcedimento().getId().equals(""+Constants.TIPOPROC_CAUZIONI_CAM)){
				    MenuController.caricaMenu(Constants.ID_PER_MENU_DETTAGLIO_PREVENZIONE, request, true);
				} else if(procedimento.getTipoProcedimento().getId().equals(""+Constants.TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM) ||
						//modifica CS
							procedimento.getTipoProcedimento().getId().equals(""+Constants.TIPOPROC_CORREZIONE_ERRORI_CAM) ) {
						//fine modifica CS
					MenuController.caricaMenu(Constants.ID_PER_MENU_DETTAGLIO_PREVENZIONE, request,true);
					Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
					List<IntestatarioDto> listaIntestatari = procedimentoServiceCam.getIntestatari(idUfficioUtente.intValue(), procedimento.getIdProcedimento());
					
					 if(procedimento.getTipoProcedimento().getId().equals(""+Constants.TIPOPROC_CORREZIONE_ERRORI_CAM) ) {
						 Iterator<IntestatarioDto> iter = listaIntestatari.iterator(); 
						 IntestatarioDto intestatario = iter.next();
						 soggettoDaCorregere = procedimentoServiceCam.getSoggettoDaCorregere(intestatario.getCODI_FK_ID_SOGG_INT().longValue(), intestatario.getCODI_FK_ID_VER().longValue(), intestatario.getCODI_FK_ID_UFF_OWNER().intValue());
					 }
					
					mnv.addObject("soggettoDaCorregere", soggettoDaCorregere);
					mnv.addObject("listaIntestatari", listaIntestatari);
		        } else { 
		        	MenuController.caricaMenu(Constants.ID_PER_MENU_DETTAGLIO_PREVENZIONE, request,true);
		        }	
				
			 }	
			 
				if(procedimento.getTipoProcedimento().getId().equals(""+Constants.TIPOPROC_TERZO_INTERVENIENTE_CAM)) {
					List<SoggettoForm> soggettiIntervenienti = procedimentoService.soggettiDirettiInteressatiDelProcedimento(procedimento.getIdProcedimento(), Constants.RUOLO_INTERVENTIENTE, Constants.Flag.S);
					mnv.addObject("direttiInteressati", soggettiIntervenienti);
				} else {
					mnv.addObject("direttiInteressati", procedimentoService.soggettiDirettiInteressatiDelProcedimento(procedimento.getIdProcedimento(), Constants.RUOLO_DIRETTO, Constants.Flag.S));
				}
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
	
	/*
	@ModelAttribute("tipiUfficioRicerca")
	private List<ComboForm> caricaTipiUfficio() {
		List<ComboForm> lista = new ArrayList<ComboForm>();
		lista.add(new ComboForm("17", "Procura della Repubblica"));
		lista.add(new ComboForm("8", "Procura Generale della Repubblica presso la CdA"));
		lista.add(new ComboForm("19", "Tribunale"));
		lista.add(new ComboForm("6", "Corte di Appello"));
		return lista;
	}
	*/
	
	
//	private void caricaDettaglioProcedimento(ProcedimentoForm form,ModelAndView mnv) {
//	ProcedimentoForm procedimento = procedimentoService.getDettaglioProcedimento(form.getIdProcedimento());
//	super.updateProcedimentoIntoSession(procedimento.getIdProcedimento());
//	if (request.getSession().getAttribute(Constants.ATTO) != null) {
//		request.getSession().removeAttribute(Constants.ATTO);
//	}
//	
//	mnv.addObject("numeriRegistro", procedimentoService.getNumeriRegistro(procedimento.getIdProcedimento()));
//	mnv.addObject("numeriRegistroAccertamento", procedimentoService.getNumeriRegistroAccertamento(procedimento.getIdProcedimento()));
//
//	if ((procedimento.getTipoProcedimento() == null) 
//			|| ((procedimento.getTipoProcedimento() != null) 
//					&& (procedimento.getTipoProcedimento().getId().equals(Constants.TIPOPROC_PERSONALE_E_PATRIMONIALE) 
//							|| procedimento.getTipoProcedimento().getId().equals(Constants.TIPOPROC_PATRIMONIALE) 
//							|| procedimento.getTipoProcedimento().getId().equals(Constants.TIPOPROC_PERSONALE)))) {
//		// Se il procedimento è una prevenzione
//		mnv.addObject("direttiInteressati", procedimentoService.soggettiDirettiInteressatiDelProcedimento(procedimento.getIdProcedimento(), Constants.RUOLO_DIRETTO));
//	}
//	if ((procedimento.getTipoProcedimento() != null) && procedimento.getTipoProcedimento().getId().equals(Constants.TIPOPROC_ACCERTAMENTO)) {
//		mnv.addObject("direttiInteressati", procedimentoService.soggettiDirettiInteressatiDelProcedimento(procedimento.getIdProcedimento(), Constants.RUOLO_DIRETTO));
//	}
//	if ((procedimento.getTipoProcedimento() != null) && 
//			(procedimento.getTipoProcedimento().getId().equals(Constants.TIPOPROC_AGGRAVAMENTO) ||
//			procedimento.getTipoProcedimento().getId().equals(Constants.TIPOPROC_RIESAME_PROVV_QUESTURA))) {
//		mnv.addObject("direttiInteressati", procedimentoService.soggettiDirettiInteressatiDelProcedimento(procedimento.getIdProcedimento(), null));
//	}
//}
	
	
	/*
	private String getProcQueryString(ProcedimentoForm procedimento) {
		String s = "?";
		if(procedimento.getAnno() != null)
			s += "&anno=" + procedimento.getAnno();
		if(procedimento.getStatoProcedimento()!=null && !procedimento.getStatoProcedimento().getId().equals(Constants.NO_ITEM_SELECTED))
			s += "&statoProcedimento.id=" + procedimento.getStatoProcedimento().getId();
		if(procedimento.getNumeroMin()!=null)
			s += "&numeroMin=" + procedimento.getNumeroMin();
		if(procedimento.getNumeroMax()!=null)
			s += "&numeroMax=" + procedimento.getNumeroMax();
		if(procedimento.getDataIscrizioneMin()!=null)
			s += "&dataIscrizioneMin=" + Utility.convertDateToString(procedimento.getDataIscrizioneMin());
		if(procedimento.getDataIscrizioneMax()!=null)
			s += "&dataIscrizioneMax=" + Utility.convertDateToString(procedimento.getDataIscrizioneMax());
		if(procedimento.getCognomeSoggetto()!=null)
			s += "&cognomeSoggetto=" + procedimento.getCognomeSoggetto();
		if(procedimento.getNomeSoggetto()!=null)
			s += "&nomeSoggetto=" + procedimento.getNomeSoggetto();
		if(procedimento.getRagioneSociale()!=null)
			s += "&ragioneSociale=" + procedimento.getRagioneSociale();
		if(procedimento.getPmAssegnatario()!=null && !procedimento.getPmAssegnatario().getId().equals(Constants.NO_ITEM_SELECTED))
			s += "&pmAssegnatario.id=" + procedimento.getPmAssegnatario().getId();
		if(procedimento.getTipoProcedimento()!=null && !procedimento.getTipoProcedimento().getId().equals(Constants.NO_ITEM_SELECTED))
			s += "&tipoProcedimento.id=" + procedimento.getTipoProcedimento().getId();
		if(procedimento.getAnnoDecretoDecisorio()!=null)
			s += "&annoDecretoDecisorio=" + procedimento.getAnnoDecretoDecisorio();
		if(procedimento.getNumeroDecretoDecisorio()!=null)
			s += "&numeroDecretoDecisorio=" + procedimento.getNumeroDecretoDecisorio();
		if(procedimento.getTipoProcedente()!=null && !procedimento.getTipoProcedente().getId().equals(Constants.NO_ITEM_SELECTED))
			s += "&tipoProcedente.id=" + procedimento.getTipoProcedente().getId();
		if(procedimento.getProcedente()!=null && !procedimento.getProcedente().getId().equals(Constants.NO_ITEM_SELECTED))
			s += "&procedente.id=" + procedimento.getProcedente().getId();
		if(procedimento.isAntimafia())
			s += "&antimafia=" + procedimento.isAntimafia();
		if(procedimento.isAntiterrorismo())
			s += "&antiterrorismo=" + procedimento.isAntiterrorismo();
		if(procedimento.isOrdinaria())
			s += "&ordinaria=" + procedimento.isOrdinaria();
		return s;
	}
	*/

	@RequestMapping(value = "/procedimento/apri/visualizzaRicercaProcedimentoGestione.htm", method = RequestMethod.GET)
	public ModelAndView visualizzaRicercaProcedimentoGestione(Model model) {

		super.saveIntoSession(Constants.TIPO_FASCICOLO_GESTIONE, Constants.TIPO_FASCICOLO, true);
		super.saveIntoSession(null, Constants.ID_TIPO_PROCEDIMENTO, true);
		return visualizzaRicercaProcedimento(model);
	}
	
	@RequestMapping(value = "/procedimento/apri/visualizzaRicercaProcedimentoModificaPersonali.htm", method = RequestMethod.GET)
	public ModelAndView visualizzaRicercaProcedimentoModificaPersonali(Model model) {
		/****************** MOCK ****************/
		//int idTipoUfficio = (Integer) request.getSession().getAttribute("idTipoUfficio");
		//if(idTipoUfficio == 6)
		//{	String mock = "test";
		//	mock.substring(10);
		//}
		/*************** END MOCK ***************/
		super.saveIntoSession(Constants.TIPO_FASCICOLO_CAMERA_DI_CONSIGLIO, Constants.TIPO_FASCICOLO, true);
		super.saveIntoSession(Constants.TIPOPROC_MODIFICA_PERSONALE_CAM, Constants.ID_TIPO_PROCEDIMENTO, true);
		//super.removeFromSession("idTpUffRic");
		return visualizzaRicercaProcedimento(model);
	}
	
	@RequestMapping(value = "/procedimento/apri/visualizzaRicercaProcedimentoModificaPatrimoniali.htm", method = RequestMethod.GET)
	public ModelAndView visualizzaRicercaProcedimentoModificaPatrimoniali(Model model) {

		/****************** MOCK ****************/
		//int idTipoUfficio = (Integer) request.getSession().getAttribute("idTipoUfficio");
		//if(idTipoUfficio == 6)
		//{	String mock = "test";
		//	mock.substring(10);
		//}
		/*************** END MOCK ***************/
		super.saveIntoSession(Constants.TIPO_FASCICOLO_CAMERA_DI_CONSIGLIO, Constants.TIPO_FASCICOLO, true);
		super.saveIntoSession(Constants.TIPOPROC_MODIFICA_PATRIMONIALE_CAM, Constants.ID_TIPO_PROCEDIMENTO, true);
		//super.removeFromSession("idTpUffRic");
		return visualizzaRicercaProcedimento(model);
	}
	
	// inizio modifica CS
	@RequestMapping(value = "/procedimento/apri/visualizzaRicercaCorrezione.htm", method = RequestMethod.GET)
	public ModelAndView visualizzaRicercaCorrezione(Model model) {
		/****************** MOCK ****************/
		/*int idTipoUfficio = (Integer) request.getSession().getAttribute("idTipoUfficio");
		if(idTipoUfficio == 6)
		{	String mock = "test";
			mock.substring(10);
		}*/
		/*************** END MOCK ***************/
		super.saveIntoSession(Constants.TIPO_FASCICOLO_CAMERA_DI_CONSIGLIO, Constants.TIPO_FASCICOLO, true);
		super.saveIntoSession(Constants.TIPOPROC_CORREZIONE_ERRORI_CAM, Constants.ID_TIPO_PROCEDIMENTO, true);
		//super.removeFromSession("idTpUffRic");
		return visualizzaRicercaProcedimento(model);
	}	
	// fine modifica CS
	
	
	@RequestMapping(value = "/procedimento/apri/visualizzaRicercaProcedimentoLiquidazioneAmministratore.htm", method = RequestMethod.GET)
	public ModelAndView visualizzaRicercaProcedimentoLiquidazioneAmministratore(Model model) {
		/****************** MOCK ****************/
		/*int idTipoUfficio = (Integer) request.getSession().getAttribute("idTipoUfficio");
		if(idTipoUfficio == 6)
		{	String mock = "test";
			mock.substring(10);
		}*/
		/*************** END MOCK ***************/
		super.saveIntoSession(Constants.TIPO_FASCICOLO_CAMERA_DI_CONSIGLIO, Constants.TIPO_FASCICOLO, true);
		super.saveIntoSession(Constants.TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM, Constants.ID_TIPO_PROCEDIMENTO, true);
		//super.removeFromSession("idTpUffRic");
		return visualizzaRicercaProcedimento(model);
	}	
	
	@RequestMapping(value = "/procedimento/apri/visualizzaRicercaProcedimentoCauzioni.htm", method = RequestMethod.GET)
	public ModelAndView visualizzaRicercaProcedimentoCauzioni(Model model) {

		
		super.saveIntoSession(Constants.TIPO_FASCICOLO_CAMERA_DI_CONSIGLIO, Constants.TIPO_FASCICOLO, true);
		super.saveIntoSession(Constants.TIPOPROC_CAUZIONI_CAM, Constants.ID_TIPO_PROCEDIMENTO, true);
		//super.removeFromSession("idTpUffRic");
		return visualizzaRicercaProcedimento(model);
	}
	
	@RequestMapping(value = "/procedimento/apri/visualizzaRicercaProcedimentoPermessi.htm", method = RequestMethod.GET)
	public ModelAndView visualizzaRicercaProcedimentoPermessi(Model model) {
		/****************** MOCK ****************/
		/*int idTipoUfficio = (Integer) request.getSession().getAttribute("idTipoUfficio");
		if(idTipoUfficio == 6)
		{	String mock = "test";
			mock.substring(10);
		}*/
		/*************** END MOCK ***************/
		super.saveIntoSession(Constants.TIPO_FASCICOLO_CAMERA_DI_CONSIGLIO, Constants.TIPO_FASCICOLO, true);
		super.saveIntoSession(Constants.TIPOPROC_PERMESSI_CAM, Constants.ID_TIPO_PROCEDIMENTO, true);
		//super.removeFromSession("idTpUffRic");
		return visualizzaRicercaProcedimento(model);
	}
	
	@RequestMapping(value = "/procedimento/apri/visualizzaRicercaProcedimentoTerzoInterveniente.htm", method = RequestMethod.GET)
	public ModelAndView visualizzaRicercaTerzoInterveniente(Model model) {
		/****************** MOCK ****************/
		/*int idTipoUfficio = (Integer) request.getSession().getAttribute("idTipoUfficio");
		if(idTipoUfficio == 6)
		{	String mock = "test";
			mock.substring(10);
		}*/
		/*************** END MOCK ***************/
		super.saveIntoSession(Constants.TIPO_FASCICOLO_CAMERA_DI_CONSIGLIO, Constants.TIPO_FASCICOLO, true);
		super.saveIntoSession(Constants.TIPOPROC_TERZO_INTERVENIENTE_CAM, Constants.ID_TIPO_PROCEDIMENTO, true);
		//super.removeFromSession("idTpUffRic");
		return visualizzaRicercaProcedimento(model);
	}	
		
}
