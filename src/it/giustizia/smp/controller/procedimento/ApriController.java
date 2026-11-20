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
			addBreadcrumbs();
			super.saveIntoSession(Constants.TIPO_FASCICOLO_MERITO, Constants.TIPO_FASCICOLO, true);
			model.addAttribute("tipiProcedimento", getTipiProcedimento());
			return new ModelAndView("/procedimento/apri/ricercaPerCriteri", "procedimento", new ProcedimentoForm());
		}
		
		@RequestMapping(value = "/procedimento/apri/ricercaPerCriteri.htm", method = RequestMethod.POST)
		public ModelAndView caricaCombo(@Valid @ModelAttribute("procedimento") ProcedimentoForm procedimento, BindingResult result) {

			return new ModelAndView("/procedimento/apri/ricercaPerCriteri", "procedimento", procedimento);
		}		
		
		@RequestMapping(value = "/procedimento/apri/riesame/visualizzaRicercaPerCriteri.htm", method = RequestMethod.GET)
		public ModelAndView visualizzaRicercaPerCriteriRiesame() {
			super.saveIntoSession(Constants.TIPO_FASCICOLO_OPP_DIVIETI_QUESTORE, Constants.TIPO_FASCICOLO, true);
			return new ModelAndView("/procedimento/apri/riesame/ricercaPerCriteri", "procedimento", new ProcedimentoForm());
		}
	
		@RequestMapping(value = "/procedimento/apri/visualizzaRicercaPerCriteriIndietro.htm", method = RequestMethod.GET)
		public ModelAndView visualizzaRicercaPerCriteriIndietro( HttpServletRequest request) {
			
			request.getSession().removeAttribute("urlIndietroCopertinaProc");
//			Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
			Integer idUfficioUtente = recuperaIdUfficioUtente();
			MenuController.caricaMenu(Constants.ID_PADRE_MENU, request);
			ModelAndView mnv = new ModelAndView("/procedimento/apri/ricercaPerCriteri", "procedimento", new ProcedimentoForm());
			mnv.addObject("risultati", procedimentoService.listaProcedimentiByCriteria(idUfficioUtente, (ProcedimentoForm)request.getSession().getAttribute("procDiRicerca"), null));
			request.getSession().removeAttribute("procDiRicerca");
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
			addBreadcrumbs("GET","/procedimento/apri/copertinaProcedimento.htm",null);
			return caricaDettaglioProcedimento(procedimento, result, request, "/procedimento/apri/ricercaPerCriteri");
		}
		

		
	
	// ************ FINE RICERCA PROCEDIMENTO PER CRITERI ****************//

	// ************ INIZIO RICERCA PROCEDIMENTO PUNTUALE****************// 

	@RequestMapping(value = "/procedimento/apri/ricercaPuntuale.htm", method = RequestMethod.GET)
	public ModelAndView visualizzaRicercaPuntuale( HttpServletRequest request) {

		// metto in sessione il tipo procedimento 1 (vale anche se sto cercando
		// prevenzioni che hanno tipo_proc =2 o =4
	//	request.getSession().setAttribute(Constants.ID_TIPO_PROCEDIMENTO, Constants.TIPOPROC_PERSONALE_E_PATRIMONIALE);
		super.saveIntoSession(Constants.TIPO_FASCICOLO_MERITO, Constants.TIPO_FASCICOLO, true);
		//super.removeFromSession("idTpUffRic");
		//request.getSession().removeAttribute("urlIndietroCopertinaProcPuntuale");
		MenuController.caricaMenu(Constants.ID_PADRE_MENU, request);
		ProcedimentoForm procedimento = new ProcedimentoForm();
		
		//Giacomo - pulisco la breadcrumb
		cleanBreadcrumb(request.getSession());
		addBreadcrumbs();
		
		return new ModelAndView("/procedimento/apri/ricercaPuntuale", "procedimento", procedimento);

		
	}

	@RequestMapping(value = "/procedimento/apri/riesame/ricercaPuntuale.htm", method = RequestMethod.GET)
	public ModelAndView visualizzaRicercaPuntualeRiesame(HttpServletRequest request) {

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
	public ModelAndView visualizzaRicercaProcedimentoAccertamento(Model model, HttpServletRequest request) {

		super.saveIntoSession(Constants.TIPO_FASCICOLO_ACCERTAMENTO, Constants.TIPO_FASCICOLO, true);
		super.saveIntoSession(null, Constants.ID_TIPO_PROCEDIMENTO, true);
		return visualizzaRicercaProcedimento(model,request);
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
	public ModelAndView aggravamentoRicercaPuntuale( HttpServletRequest request) {

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
		public ModelAndView visualizzaRicercaProcedimento(Model model, HttpServletRequest request) {

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
				

			
			model.addAttribute("listaUffici", getListaUffici());	
			

			cleanBreadcrumb(request.getSession());
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
		public ModelAndView visualizzaRicercaProcedimentoPrevenzione(Model model, HttpServletRequest request) {

			super.saveIntoSession(Constants.TIPO_FASCICOLO_MERITO, Constants.TIPO_FASCICOLO, true);
			super.saveIntoSession(null, Constants.ID_TIPO_PROCEDIMENTO, true);
			//super.removeFromSession("idTpUffRic");
			return visualizzaRicercaProcedimento(model,request);
		}
		
		/**
	    * visualizzaRicercaProcedimentoOpposizione
	    * Visualizza la form di ricerca del procedimento (ricerca puntuale e per criteri unite nella stessa form)
	    *      
	    * @return ModelAndView
	    */
		@RequestMapping(value = "/procedimento/apri/visualizzaRicercaProcedimentoOpposizioneRestituzione.htm", method = RequestMethod.GET)
		public ModelAndView visualizzaRicercaProcedimentoOpposizioneRestituzione(Model model, HttpServletRequest request) {

			super.saveIntoSession(Constants.TIPO_FASCICOLO_OPP_REVOCA_SEQUESTRO, Constants.TIPO_FASCICOLO, true);
			super.saveIntoSession(Constants.TIPOPROC_OPPOSIZIONE_ALLA_RESTITUZIONE, Constants.ID_TIPO_PROCEDIMENTO, true);
			//super.removeFromSession("idTpUffRic");
			return visualizzaRicercaProcedimento(model,request);
		}
		
		@RequestMapping(value = "/procedimento/apri/visualizzaRicercaRevocazione.htm", method = RequestMethod.GET)
		public ModelAndView visualizzaRicercaProcedimentoRevocazione(Model model, HttpServletRequest request) {

			super.saveIntoSession(Constants.TIPO_FASCICOLO_REVOCAZIONE, Constants.TIPO_FASCICOLO, true);
			super.saveIntoSession(Constants.TIPOPROC_REVOCAZIONE_CONFISCA, Constants.ID_TIPO_PROCEDIMENTO, true);
			//super.removeFromSession("idTpUffRic");
			return visualizzaRicercaProcedimento(model,request);
		}
		
		@RequestMapping(value = "/procedimento/apri/visualizzaRicercaProcedimentoAggravamento.htm", method = RequestMethod.GET)
		public ModelAndView visualizzaRicercaProcedimentoAggravamento(Model model, HttpServletRequest request) {

			super.saveIntoSession(Constants.TIPO_FASCICOLO_AGGRAVAMENTO, Constants.TIPO_FASCICOLO, true);
			super.saveIntoSession(Constants.TIPOPROC_AGGRAVAMENTO_PERSONALE, Constants.ID_TIPO_PROCEDIMENTO, true);
			//super.removeFromSession("idTpUffRic");
			return visualizzaRicercaProcedimento(model,request);
		}
		
		@RequestMapping(value = "/procedimento/apri/visualizzaRicercaProcedimentoDivietiQuestore.htm", method = RequestMethod.GET)
		public ModelAndView visualizzaRicercaProcedimentoDivietiQuestore(Model model, HttpServletRequest request) {

			super.saveIntoSession(Constants.TIPO_FASCICOLO_OPP_DIVIETI_QUESTORE, Constants.TIPO_FASCICOLO, true);
			super.saveIntoSession(Constants.TIPOPROC_OPPOSIZIONE_DIVIETI_QUESTORE, Constants.ID_TIPO_PROCEDIMENTO, true);
			//super.removeFromSession("idTpUffRic");
			return visualizzaRicercaProcedimento(model, request);
		}
		
		@RequestMapping(value = "/procedimento/apri/ricercaProcedimento.htm", method = RequestMethod.POST)
		public ModelAndView visualizzaRicercaProcedimentoPost(ProcedimentoForm procedimento, BindingResult result, HttpServletRequest request) {
			
			
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
				
			if(procedimento != null && (procedimento.getAnno() != null || procedimento.getNumero() != null)) {
				if (result.hasErrors()) {
					return new ModelAndView("/procedimento/apri/ricercaProcedimento", "procedimento", procedimento);
				}
				procedimentoValidation.validateCriteriRicercaPuntuale(procedimento, result);
				if (result.hasErrors()) {
					return new ModelAndView("/procedimento/apri/ricercaProcedimento", "procedimento", procedimento);
				}			
			}
			
			
			procedimentoValidation.validateCriteriRicercaDifensore(procedimento, result);
			if (result.hasErrors()) {
				return new ModelAndView("/procedimento/apri/ricercaProcedimento", "procedimento", procedimento);
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
		    @SuppressWarnings("unchecked")
			List<ProcedimentoForm> risultati = (List<ProcedimentoForm>)mnv.getModelMap().get("risultati");
		    
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

//			Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
			Integer idUfficioUtente = recuperaIdUfficioUtente();
			//return commonService.listaPmAssegnatari(idUfficioUtente);
			return commonService.getMagistratiByUff(idUfficioUtente);
		}

		@ModelAttribute("procedenti")
		public List<ComboForm> getProcedenti(@Valid @ModelAttribute("procedimento") ProcedimentoForm procedimento, BindingResult result, HttpServletRequest request) {

			ProfiloForm profilo = (ProfiloForm)request.getSession().getAttribute(Constants.PROFILO);
			long idTipoUfficioConnesso = profilo.getIdTipoUfficio();
			if (!Utility.isDefinito(procedimento.getTipoProcedente())) {
				return new ArrayList<ComboForm>();
			}
//			Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
			Integer idUfficioUtente = recuperaIdUfficioUtente();
			if(procedimento.getTipoProcedente()!=null && procedimento.getTipoProcedente().getId().equals("0")) {
				List<ComboForm> lista = new ArrayList<ComboForm>();
				lista.add(new ComboForm("271", "Altro"));
				return lista;
			} else return commonService.listaProcedenti(new Long(idUfficioUtente.toString()), idTipoUfficioConnesso, new Long(procedimento.getTipoProcedente().getId()));
		}

		@ModelAttribute("tipiProcedente")
		public List<ComboForm> getTipiProcedente(HttpServletRequest request) {

			List<ComboForm> result = null;
//			Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
			Integer idUfficioUtente = recuperaIdUfficioUtente();
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
		public List<ComboForm> getListaUffici() {
//			Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
			Integer idUfficioUtente = recuperaIdUfficioUtente();
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
				else 
					idTipoFasc = super.getIdTipoFascicolo(false);
				
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
//			Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
			Integer idUfficioUtente = recuperaIdUfficioUtente();
			request.getSession().setAttribute("procDiRicerca", procedimento);
			
			procedimento.setTipoFascicoloRicerca(idTipoFasc);
			
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
			logger.error("impossibile inviare messaggio di log: " + e.getMessage());
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
//			Segnalazione EMS-85
			if(procedimento!=null && procedimento.getTipoFascicolo()!=null && procedimento.getTipoFascicolo().getId() != null)
				super.saveIntoSession(procedimento.getTipoFascicolo().getId(), Constants.TIPO_FASCICOLO, true);
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
		String view = "/procedimento/apri/dettaglioProcedimento";
	    String urlIndietroPuntuale = "/procedimento/apri/ricercaPuntuale.htm";
        if (pagina != null) {
            if(pagina.equals("/procedimento/apri/ricercaPerCriteri")){
                procedimentoValidation.validateDettaglioProcedimento(form, result, request);
                if (result.hasErrors()) {
//                    Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
                	Integer idUfficioUtente = recuperaIdUfficioUtente();
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
		getIdTipoFascicolo(false);
		if(idTipoFascicolo==0 && procedimento!=null && procedimento.getTipoFascicolo()!=null){
			super.saveIntoSession(procedimento.getTipoFascicolo().getId(), Constants.TIPO_FASCICOLO, true);
			idTipoFascicolo = getIdTipoFascicolo(false);
		}
			
			if (idTipoFascicolo==Constants.TIPO_FASCICOLO_OPP_DIVIETI_QUESTORE) {
				view = "/procedimento/apri/riesame/dettaglioProcedimentoRiesame";
			} else if (idTipoFascicolo==Constants.TIPO_FASCICOLO_ACCERTAMENTO) {
				view = "/procedimento/apri/dettaglioProcedimentoAccertamento";
			}
		
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
		
		/* 
		 * ISSUE MEV : MEV Sitmp Istanza di liquidazione (concordate a seguito del p.11 PLO II/10)
		 * 	- punto b) --> aggiunta or condition per visualizzazione n° fasc. gestione in caso di
		 * 	fascicolo di camera di consiglio e procedimento di liquidazione compensi amministratore
		 * Numero MEV : 1
		 * Autore    : Gioggi
		 * Data      : 18 giu 2018
		 * Branch    : MEV_1
		 */
		//fascicolo gestione -------
		if (procedimento.getTipoFascicolo() == null
				|| (procedimento.getTipoFascicolo() != null
				&& (procedimento.getTipoFascicolo().getId().equals(String.valueOf(Constants.TIPO_FASCICOLO_MERITO))
						|| (procedimento.getTipoFascicolo().getId().equals(String.valueOf(Constants.TIPO_FASCICOLO_CAMERA_DI_CONSIGLIO))
								&& procedimento.getTipoProcedimento().getId().equals(String.valueOf(Constants.TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM)))))) {
			//***** FINE INTERVENTO MEV_1 *****//
			@SuppressWarnings("unchecked")
			HashMap<Object,Object> fascGestione = commonService.getFascicoloGestione(procedimento.getIdProcedimento(), recuperaIdUfficioUtente(), recuperaIdCodiceUtente());
			if(fascGestione!=null) {
				Long idProcGest = (Long) fascGestione.get("idProcGest");
				Long idUffRic = (Long) fascGestione.get("idUffRic");
				if(idProcGest!=null && idUffRic!=null && idProcGest>0 && idUffRic>0) {
//					Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
					Integer idUfficioUtente = recuperaIdUfficioUtente();
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
		
		for (Iterator<SoggettoForm> iterator = list.iterator(); iterator.hasNext();) {
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
//					Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
					Integer idUfficioUtente = recuperaIdUfficioUtente();
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
	

	@RequestMapping(value = "/procedimento/apri/visualizzaRicercaProcedimentoGestione.htm", method = RequestMethod.GET)
	public ModelAndView visualizzaRicercaProcedimentoGestione(Model model, HttpServletRequest request) {

		super.saveIntoSession(Constants.TIPO_FASCICOLO_GESTIONE, Constants.TIPO_FASCICOLO, true);
		super.saveIntoSession(null, Constants.ID_TIPO_PROCEDIMENTO, true);
		return visualizzaRicercaProcedimento(model, request);
	}
	
	@RequestMapping(value = "/procedimento/apri/visualizzaRicercaProcedimentoModificaPersonali.htm", method = RequestMethod.GET)
	public ModelAndView visualizzaRicercaProcedimentoModificaPersonali(Model model, HttpServletRequest request) {
		super.saveIntoSession(Constants.TIPO_FASCICOLO_CAMERA_DI_CONSIGLIO, Constants.TIPO_FASCICOLO, true);
		super.saveIntoSession(Constants.TIPOPROC_MODIFICA_PERSONALE_CAM, Constants.ID_TIPO_PROCEDIMENTO, true);
		return visualizzaRicercaProcedimento(model, request);
	}
	
	@RequestMapping(value = "/procedimento/apri/visualizzaRicercaProcedimentoModificaPatrimoniali.htm", method = RequestMethod.GET)
	public ModelAndView visualizzaRicercaProcedimentoModificaPatrimoniali(Model model, HttpServletRequest request) {
		super.saveIntoSession(Constants.TIPO_FASCICOLO_CAMERA_DI_CONSIGLIO, Constants.TIPO_FASCICOLO, true);
		super.saveIntoSession(Constants.TIPOPROC_MODIFICA_PATRIMONIALE_CAM, Constants.ID_TIPO_PROCEDIMENTO, true);
		return visualizzaRicercaProcedimento(model, request);
	}
	
	// inizio modifica CS
	@RequestMapping(value = "/procedimento/apri/visualizzaRicercaCorrezione.htm", method = RequestMethod.GET)
	public ModelAndView visualizzaRicercaCorrezione(Model model, HttpServletRequest request) {
		super.saveIntoSession(Constants.TIPO_FASCICOLO_CAMERA_DI_CONSIGLIO, Constants.TIPO_FASCICOLO, true);
		super.saveIntoSession(Constants.TIPOPROC_CORREZIONE_ERRORI_CAM, Constants.ID_TIPO_PROCEDIMENTO, true);
		return visualizzaRicercaProcedimento(model, request);
	}	
	// fine modifica CS
	
	
	@RequestMapping(value = "/procedimento/apri/visualizzaRicercaProcedimentoLiquidazioneAmministratore.htm", method = RequestMethod.GET)
	public ModelAndView visualizzaRicercaProcedimentoLiquidazioneAmministratore(Model model, HttpServletRequest request) {
		super.saveIntoSession(Constants.TIPO_FASCICOLO_CAMERA_DI_CONSIGLIO, Constants.TIPO_FASCICOLO, true);
		super.saveIntoSession(Constants.TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM, Constants.ID_TIPO_PROCEDIMENTO, true);
		return visualizzaRicercaProcedimento(model, request);
	}	
	
	@RequestMapping(value = "/procedimento/apri/visualizzaRicercaProcedimentoCauzioni.htm", method = RequestMethod.GET)
	public ModelAndView visualizzaRicercaProcedimentoCauzioni(Model model, HttpServletRequest request) {

		
		super.saveIntoSession(Constants.TIPO_FASCICOLO_CAMERA_DI_CONSIGLIO, Constants.TIPO_FASCICOLO, true);
		super.saveIntoSession(Constants.TIPOPROC_CAUZIONI_CAM, Constants.ID_TIPO_PROCEDIMENTO, true);
		return visualizzaRicercaProcedimento(model, request);
	}
	
	@RequestMapping(value = "/procedimento/apri/visualizzaRicercaProcedimentoPermessi.htm", method = RequestMethod.GET)
	public ModelAndView visualizzaRicercaProcedimentoPermessi(Model model, HttpServletRequest request) {
		super.saveIntoSession(Constants.TIPO_FASCICOLO_CAMERA_DI_CONSIGLIO, Constants.TIPO_FASCICOLO, true);
		super.saveIntoSession(Constants.TIPOPROC_PERMESSI_CAM, Constants.ID_TIPO_PROCEDIMENTO, true);
		return visualizzaRicercaProcedimento(model, request);
	}
	
	@RequestMapping(value = "/procedimento/apri/visualizzaRicercaProcedimentoTerzoInterveniente.htm", method = RequestMethod.GET)
	public ModelAndView visualizzaRicercaTerzoInterveniente(Model model, HttpServletRequest request) {
		super.saveIntoSession(Constants.TIPO_FASCICOLO_CAMERA_DI_CONSIGLIO, Constants.TIPO_FASCICOLO, true);
		super.saveIntoSession(Constants.TIPOPROC_TERZO_INTERVENIENTE_CAM, Constants.ID_TIPO_PROCEDIMENTO, true);
		return visualizzaRicercaProcedimento(model, request);
	}	
		
}
