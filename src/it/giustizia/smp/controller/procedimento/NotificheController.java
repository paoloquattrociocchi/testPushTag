package it.giustizia.smp.controller.procedimento;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import it.giustizia.smp.controller.BreadcrumbsController;
import it.giustizia.smp.dao.AttoDao;
import it.giustizia.smp.dao.StaticListLoader;
import it.giustizia.smp.dto.pg_notifiche.DestinatarioDto;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.NotificaAttoForm;
import it.giustizia.smp.form.NotificaDettaglioForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.model.TAtto;
import it.giustizia.smp.model.TAttoId;
import it.giustizia.smp.model.TNotifAmm;
import it.giustizia.smp.service.CommonService;
import it.giustizia.smp.service.NotificheService;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.RequestUtil;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.utility.notifiche.ServiceResult;

/***
 * Controller per la gestione delle notifiche 
 * @author Almaviva
 *
 */

@Controller
public class NotificheController extends BreadcrumbsController {
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private NotificheService notificheService;
	
	@Autowired
	private CommonService  commonService;
	
	@Autowired
	private AttoDao attoDao;
	
	
	private static final String LISTA_DESTINATARI = "LISTA_DESTINATARI";
	
	/***
	 * Predispone la maschera per l'invio di una notifica 
	 * @param attoForm
	 * @param result
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = { "/procedimento/notifiche/apriInviaNotifica.htm" }, method = {RequestMethod.POST})
	public ModelAndView apriInviaNotifica(
			@ModelAttribute("attoForm") NotificaAttoForm attoForm, BindingResult result, HttpServletRequest request,
			ModelMap modelMap
			) {

		addBreadcrumbs("POST","/procedimento/notifiche/inviaNotifica.htm",null);
		logger.info("Apertura atto " + attoForm.getCodAtto() + " del proc. " + recuperaIdProcedimento() + " per invio notifica");
		// Carico la liste dei destinatari possibili per l'atto passato
		attoForm.setListaDestinatari(
				notificheService.caricaListaDestinatari(recuperaIdProcedimento() , attoForm.getCodAtto(),recuperaIdUfficioUtente())
		);
		;
		attoForm.setHasAmministratori(false);
		if (isAttoPerAmministratori((long)RequestUtil.recuperaIdCategoriaAtto(request))) {
			// Valorizzo il flag per capire se c'è un amministratore
			if (Utility.isValid(attoForm.getListaDestinatari())) {
				attoForm.setHasAmministratori(notificheService.controllaPresenzaAmministratori(attoForm.getListaDestinatari()));
			}
		}
		
		request.getSession().setAttribute(LISTA_DESTINATARI, attoForm.getListaDestinatari());

		// Preseleziono le check sulla base del flag preselezionate
		for (DestinatarioDto dto : attoForm.getListaDestinatari()) {
			if ("S".equals(dto.getFLG_PRESELEZIONATO())) {
				attoForm.getListaIdCheckboxDestinatari().add("CHK_" + dto.getCODI_DEST());
			}
		} 
		// Valorizzo l'oggetto ed il testo della comunicazione
		if (attoForm.getListaDestinatari().size()>0) {
			attoForm.setOggetto(attoForm.getListaDestinatari().get(0).getOGGETTO());
			attoForm.setTesto(attoForm.getListaDestinatari().get(0).getEVENTO());
		}
		ModelAndView mnv = new ModelAndView("/procedimento/notifiche/inviaNotifica", "attoForm", attoForm);
		return mnv;
	}
	
	/***
	 * Restituisce true se l'atto è uno di quelli inviabili agli amministratori
	 * @param codAtto
	 * @return
	 */
	private boolean isAttoPerAmministratori(long codAtto) {
		if (Constants.ATTI_INVIABILI_AD_AMMINISTRATORI.contains(codAtto)) 
			return true;
		return false;
	}




	/***
	 * Gestione dell'invio della notifica
	 * @param attoForm
	 * @param result
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/procedimento/notifiche/inviaNotifica.htm" }, method = RequestMethod.POST, params={"inviaButton"})
	public ModelAndView inviaNotifica(
			@Valid @ModelAttribute("attoForm") NotificaAttoForm attoForm, BindingResult result, HttpServletRequest request,
			ModelMap modelMap
			) {
		
		// Valorizzo la lista destinatari con l'oggetto in sessione
		attoForm.setListaDestinatari((List<DestinatarioDto>) request.getSession().getAttribute(LISTA_DESTINATARI));
		attoForm.setHasAmministratori(false);
		if (isAttoPerAmministratori((long)RequestUtil.recuperaIdCategoriaAtto(request))) {
			// Valorizzo il flag per capire se c'è un amministratore
			if (Utility.isValid(attoForm.getListaDestinatari())) {
				attoForm.setHasAmministratori(notificheService.controllaPresenzaAmministratori(attoForm.getListaDestinatari()));
			}
		}
		// Verifico se e` stato selezionato almeno un destinatario
		if (!Utility.isValid(attoForm.getListaDestinatari()) || !Utility.isValid(attoForm.getListaIdCheckboxDestinatari())) {
			super.setMessage(Constants.MESSAGE, "notifiche.invianotifiche.selezioneDestinatari", null, false);
			return new ModelAndView("/procedimento/notifiche/inviaNotifica", "attoForm", attoForm);
		}
		ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute(Constants.ID_PROCEDIMENTO);
		AttoForm atto = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
		// setto i parametri mancanti in NotificaAttoForm, da AttoForm
		attoForm.setDataRegistrazione(atto.getDataRegistrazione());
		attoForm.setDataEmissione(atto.getDataEmissione());
		attoForm.setDataDeposito(atto.getDataDeposito());
		attoForm.setNumeAnnoAtto(atto.getNumeAnnoAtto());
		attoForm.setNumeAtto(atto.getNumeAtto());
		String descUtente = recuperaIdCodiceUtente();

		// Se ho passato tutti i controlli invoco l'invio con la chiamata al servizio
		ServiceResult serviceResult = notificheService.inviaNotifiche(attoForm, procedimento, recuperaIdUfficioUtente(), recuperaNomeECognomeUtente(request.getSession()), descUtente);
		if (serviceResult.getReturnCode().equals(ServiceResult.Result.OK)) {
			// Se non ci sono warnig
			if (serviceResult.getWarnigList().size()==0) {
				modelMap.addAttribute("isReadOnly", true);
				super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", null, false);
			} else {
				modelMap.addAttribute("isReadOnly", true);
				String elencoWarning = "";
				for (String warning : serviceResult.getWarnigList()) {
					elencoWarning += warning;
				}
				String msg = "Operazione eseguita solo per alcuni destinatari. Le seguenti operazioni sono fallite:<br/>" + elencoWarning ;
				super.setMessage(Constants.SUCCESS_MESSAGE, "messaggio.gestito.db", msg, false);
				//super.setMessage(Constants.MESSAGE, "messaggio.gestito.db", serviceResult.getErrorDescription() , false);
			}
		} else {
			// In caso di errore
			super.setMessage(Constants.MESSAGE, "messaggio.gestito.db", serviceResult.getErrorDescription() , false);
			return new ModelAndView("/procedimento/notifiche/inviaNotifica", "attoForm", attoForm);
		}
		
		ModelAndView mnv = new ModelAndView("/procedimento/notifiche/inviaNotifica", "attoForm", attoForm);
		return mnv;
	}
	
	

	/***
	 * Gestione del ritorno dalla selezione di un altro destinatario
	 * @param attoForm
	 * @param result
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/procedimento/notifiche/inviaNotifica.htm" }, method = RequestMethod.POST, params={"annullaAltroDestinatario"})
	public ModelAndView annullaAltroDestinatario(
			@ModelAttribute("attoForm") NotificaAttoForm attoForm, BindingResult result, HttpServletRequest request,
			ModelMap modelMap
			) {
		attoForm.setListaDestinatari((List<DestinatarioDto>) request.getSession().getAttribute(LISTA_DESTINATARI));
		ModelAndView mnv = new ModelAndView("/procedimento/notifiche/inviaNotifica", "attoForm", attoForm);
		return mnv;
	}
	
	
	/***
	 * Gestione della dell'inserimente di un nuovo destinatario nella lista dei destinatari
	 * @param attoForm
	 * @param result
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/procedimento/notifiche/inviaNotifica.htm" }, method = RequestMethod.POST, params={"confermaAltroDestinatario"})
	public ModelAndView confermaAltroDestinatario(
			@ModelAttribute("attoForm") NotificaAttoForm attoForm, BindingResult result, HttpServletRequest request,
			ModelMap modelMap
			) {
	
		if (attoForm.getUfficioDestinatario().getId() != null &&  !Constants.NO_ITEM_SELECTED.equals(attoForm.getUfficioDestinatario().getId())) {

			DestinatarioDto destinatarioDto = notificheService.getDetinatarioDTObyIdUfficio(Integer.parseInt(attoForm.getUfficioDestinatario().getId()));
			// Controllo se già esiste nella lista
			for (DestinatarioDto iter : (List<DestinatarioDto>)request.getSession().getAttribute(LISTA_DESTINATARI)) {
				if (iter.getCODI_DEST()!=null && iter.getCODI_DEST().equals(destinatarioDto.getCODI_DEST())  ||
					iter.getDESC_DEST()!=null && iter.getDESC_DEST().equals(destinatarioDto.getDESC_DEST()) 
					) {
					// Se già esiste esco con l'errore
					modelMap.addAttribute("inSelezionaAltroDestinatario", true);
					super.setMessage(Constants.MESSAGE, "notifiche.invianotifiche.desitinatarioduplicato", null, false);
					return apriSelezionaAltroDestinatario(attoForm, result, request, modelMap);
				}
			}

			// Se è selezionata la combo degli uffici 
			// Aggiungo l'ufficio appena selezionato alla lista dei destinatari
			((List<DestinatarioDto>) request.getSession().getAttribute(LISTA_DESTINATARI)).add(destinatarioDto);
			attoForm.setListaDestinatari((List<DestinatarioDto>) request.getSession().getAttribute(LISTA_DESTINATARI));
			ModelAndView mnv = new ModelAndView("/procedimento/notifiche/inviaNotifica", "attoForm", attoForm);
			return mnv;

		} else {
			modelMap.addAttribute("inSelezionaAltroDestinatario", true);
			super.setMessage(Constants.MESSAGE, "notifiche.invianotifiche.selezioneUffDestinatario", null, false);
			return apriSelezionaAltroDestinatario(attoForm, result, request, modelMap);
		}
	}
	
	
	
	/***
	 * Apertura della selezione di un nuovo destinatario 
	 * @param attoForm
	 * @param result
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/procedimento/notifiche/inviaNotifica.htm" }, method = RequestMethod.POST, params={"altroDestinatario"})
	public ModelAndView apriSelezionaAltroDestinatario(
			@Valid @ModelAttribute("attoForm") NotificaAttoForm attoForm, BindingResult result, HttpServletRequest request,
			ModelMap modelMap
			) {
		
		// Valorizzo la lista destinatari con l'oggetto in sessione
		attoForm.setListaDestinatari((List<DestinatarioDto>) request.getSession().getAttribute(LISTA_DESTINATARI));
		modelMap.addAttribute("inSelezionaAltroDestinatario", true);
		caricaComboSelezioneAltroDestinatario(attoForm,modelMap);
		// Se ho passato tutti i controlli invoco l'invio con la chiamata al servizio
		ModelAndView mnv = new ModelAndView("/procedimento/notifiche/inviaNotifica", "attoForm", attoForm);
		return mnv;
	}
	
	/***
	 * Gestione dei postback sulle combo per la selezione del nuovo destinatario
	 * @param attoForm
	 * @param result
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/procedimento/notifiche/gestisciCambioCombo.htm" }, method = RequestMethod.POST)
	public ModelAndView gestisciCambioCombo(
			@Valid @ModelAttribute("attoForm") NotificaAttoForm attoForm, BindingResult result, HttpServletRequest request,
			ModelMap modelMap
			) {
		
		// Valorizzo la lista destinatari con l'oggetto in sessione
		attoForm.setListaDestinatari((List<DestinatarioDto>) request.getSession().getAttribute(LISTA_DESTINATARI));
		modelMap.addAttribute("inSelezionaAltroDestinatario", true);
		caricaComboSelezioneAltroDestinatario(attoForm,modelMap);
		// Se ho passato tutti i controlli invoco l'invio con la chiamata al servizio
		ModelAndView mnv = new ModelAndView("/procedimento/notifiche/inviaNotifica", "attoForm", attoForm);
		return mnv;
	}
	
	
	
	/***
	 * Caricamento delle combo della maschera altri destinatari
	 * @param atto
	 * @param map
	 */
	private void caricaComboSelezioneAltroDestinatario(NotificaAttoForm atto, ModelMap map) {
		List<ComboForm> tipiDestinatario = notificheService.getTipoUfficiPerDesinatario();
		List<ComboForm> comuni = null;
		List<ComboForm> province = null;
		List<ComboForm> uffici = null;
		
		
		// Carico le province per tipo destinatari
		if (tipiDestinatario != null && atto.getTipoDestinatario().getId() != null  && !Constants.NO_ITEM_SELECTED.equals(atto.getTipoDestinatario().getId())) {
			province = StaticListLoader.getProvince(Long.valueOf(atto.getTipoDestinatario().getId()));
		}  else {
			province = StaticListLoader.getAllProvince();
			atto.getProvinciaDestinatario().setId(null);
		}
		if (province!=null && province.size()==1) {
			atto.getProvinciaDestinatario().setId(province.get(0).getId());
		}
		
		// Carico i comuni per provincia
		if (province != null && atto.getProvinciaDestinatario().getId() != null && !Constants.NO_ITEM_SELECTED.equals(atto.getProvinciaDestinatario().getId())) {
			comuni = commonService.getAllComuniByOrganoAndProvincia(atto.getProvinciaDestinatario().getId(), Long.parseLong(atto.getTipoDestinatario().getId()));
		} else {
			comuni = null;
			atto.getComuneDestinatario().setId(null);
		}
		if (comuni!=null && comuni.size()==1) {
			atto.getComuneDestinatario().setId(comuni.get(0).getId());
		}
	
		// Carico gli uffici per comune e tipo destinatario
		if (comuni != null && atto.getComuneDestinatario().getId() != null &&  !Constants.NO_ITEM_SELECTED.equals(atto.getComuneDestinatario().getId())) {
			uffici = commonService.getAllUfficiByOrganoAndComune(Integer.parseInt(atto.getComuneDestinatario().getId()), Long.parseLong(atto.getTipoDestinatario().getId()));
		} else {
			uffici = null;
			atto.getUfficioDestinatario().setId(null);
		}
		if (uffici!=null && uffici.size()==1) {
			atto.getUfficioDestinatario().setId(uffici.get(0).getId());
		}

		// Carico le liste nella mappa
		map.addAttribute("tipiDestinatario", tipiDestinatario);
		map.addAttribute("province", province);
		map.addAttribute("comuni", comuni);
		map.addAttribute("uffici", uffici);
	}

	/***
	 * Gestione della response del download della ricevuta
	 * @param attoForm
	 * @param result
	 * @param request
	 * @param map
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/procedimento/notifiche/scaricaRicevuta" }, method = RequestMethod.POST)
	public ModelAndView downloadRicevuta(NotificaAttoForm attoForm, BindingResult result, HttpServletRequest request, ModelMap map, HttpServletResponse response) throws Exception {
		Long idAtto = attoForm.getIdAttoRicevuta(); 
		Integer idUfficio = attoForm.getIdUffRicevuta();
		try {
			downlaodRicevuta(idAtto,idUfficio,response);
			return null;
		} catch (Exception ex) {
			super.setMessage(Constants.MESSAGE, "notificheEComunicazioni.nofile", null, false);
			result.reject(Constants.MESSAGE);
			return apriListaNotifica(attoForm,result,request,map);
		}
	}
	
	/***
	 * Gestione della response del download della fileXmlAmministratori dalla lista notifiche
	 * @param attoForm
	 * @param result
	 * @param request
	 * @param map
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/procedimento/notifiche/scaricaXmlToolAmm" }, method = RequestMethod.POST)
	public ModelAndView scaricaXmlToolAmm(NotificaAttoForm attoForm, BindingResult result, HttpServletRequest request, ModelMap map, HttpServletResponse response) throws Exception {
		Long idNotifAmm = attoForm.getIdNotifAmm(); 
		try {
			downlaodXMLToolAmm(idNotifAmm,response);
			return null;
		} catch (Exception ex) {
			super.setMessage(Constants.MESSAGE, "notificheEComunicazioni.nofile", null, false);
			result.reject(Constants.MESSAGE);
			return apriListaNotifica(attoForm,result,request,map);
		}
	}


	/***
	 * Gestione della response del download della ricevuta
	 * @param attoForm
	 * @param result
	 * @param request
	 * @param map
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/procedimento/notifiche/scaricaRicevutaDaAtto" }, method = RequestMethod.POST)
	public ModelAndView downloadRicevutaDaRicevuta(AttoForm attoForm, BindingResult result, HttpServletRequest request, ModelMap map, HttpServletResponse response) throws Exception {
		Long idAtto = attoForm.getCodAtto(); 
		Integer idUfficio = attoForm.getIdUfficio();
		try {
			downlaodRicevuta(idAtto,idUfficio,response);
			return null;
		} catch (Exception ex) {
			super.setMessage(Constants.MESSAGE, "notificheEComunicazioni.nofile", null, false);
			result.reject(Constants.MESSAGE);
			return new ModelAndView("/procedimento/atto/copertinaRicevuta");
		}
	}



	
	
	
	
	/***
	 * Implementazione del download della ricevuta di un atto
	 * @param idAtto
	 * @param idUfficio
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private File downlaodRicevuta(Long idAtto,Integer idUfficio,HttpServletResponse response) throws Exception {
		
		TAtto attoRicevuta = attoDao.getAttoById(new TAttoId(idAtto, idUfficio));
		InputStream in = null;
		if(attoRicevuta.getFileDocumento()==null ) {
			if (attoRicevuta.getNumeDocumentoPdoc()!=null) {
				// Estrazione il documento dal WS attraverso il pdoc
				byte[] bytes =  documentaleService.recuperoDocumento(BigInteger.valueOf(attoRicevuta.getNumeDocumentoPdoc()),attoRicevuta.getId().getCodiKIdUffOwner());
				try {
					in = new ByteArrayInputStream(bytes ) ;
					
				} catch (Exception ex){
					logger.error("Errore durante il recupero del file dal documentale",ex);
					throw new RuntimeException("Errore: Il file della ricevuta non presente nel blob e nel documentale.");
				}
				// Decommentare Per testare il caso negativo 
				// throw new RuntimeException("Errore: Il file della ricevuta non presente nel blob e nel documentale.");
			
			} else {
				throw new RuntimeException("Errore: Il file della ricevuta non presente nel blob e nel documentale.");
			}
			
		} else {
			in = attoRicevuta.getFileDocumento().getBinaryStream();	
		}
		OutputStream out = response.getOutputStream();
		// Scrittura del file sulla response
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
	
		response.setContentType(attoRicevuta.getDescTipoDocumento());
		response.setHeader("Content-Disposition", "attachment;filename=" + attoRicevuta.getDescNomeDocumento());
		int n = 0;
		while ((n = in.read(buf)) >= 0) {
			baos.write(buf, 0, n);
		}
		in.close();
		byte[] bytes = baos.toByteArray();
		out.write(bytes);
		out.flush();
		return null;
	}
	
	
	/***
	 * Gestione della response del download del file inviato al tool degli amministratori
	 * @param idNotifAmm
	 * @param response
	 */
	private File downlaodXMLToolAmm(Long idNotifAmm, HttpServletResponse response) throws Exception {
		TNotifAmm tNotifAmm = notificheService.getTNotifAmmById(idNotifAmm);
		InputStream in = null;
		if(tNotifAmm.getXmlToolAmm() ==null ) {
			throw new RuntimeException("Errore: Il file per inviato al tool non è stato trovato.");
		} else {
			try {
				in = tNotifAmm.getXmlToolAmm().getBinaryStream()  ;
			} catch (Exception ex){
				throw new RuntimeException("Errore: Il file per inviato al tool non è stato trovato.");
			}
		}
		OutputStream out = response.getOutputStream();
		// Scrittura del file sulla response
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
	
		response.setContentType("application/xml");
		response.setHeader("Content-Disposition", "attachment;filename=" + "FileXmlPerToolAmm.xml");
		int n = 0;
		while ((n = in.read(buf)) >= 0) {
			baos.write(buf, 0, n);
		}
		in.close();
		byte[] bytes = baos.toByteArray();
		out.write(bytes);
		out.flush();
		return null;
		
	}

	
	/***
	 * Apre la lista delle notifiche per l'atto passato 
	 * @param attoForm
	 * @param result
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = { "/procedimento/notifiche/apriListaNotifica.htm" }, method =  {RequestMethod.POST,RequestMethod.GET})
	public ModelAndView apriListaNotifica(
			@Valid @ModelAttribute("attoForm") NotificaAttoForm attoForm, BindingResult result, HttpServletRequest request,
			ModelMap modelMap
			) {
		
		ArrayList<NotificaDettaglioForm> listNotifiche = notificheService.caricaListaNotifiche(attoForm.getCodAtto(), recuperaIdUfficioUtente());
		attoForm.setListaNotifiche(listNotifiche);
		
		addBreadcrumbs("POST","/procedimento/notifiche/listaNotifiche.htm",null);
		logger.info("Apertura atto " + attoForm.getCodAtto() + " del proc. " + recuperaIdProcedimento() + " per visualizzazione lista notifiche");
		return new ModelAndView("/procedimento/notifiche/listaNotifiche", "attoForm", attoForm);
		
	}

	
}
