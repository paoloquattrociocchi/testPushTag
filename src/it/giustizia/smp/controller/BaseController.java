package it.giustizia.smp.controller;

import java.math.BigInteger;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import it.giustizia.smp.exception.EccezioneGenerica;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.ProfiloForm;
import it.giustizia.smp.form.RiunioneForm;
import it.giustizia.smp.form.UdienzaForm;
import it.giustizia.smp.model.TAtto;
import it.giustizia.smp.model.TUtente;
import it.giustizia.smp.service.AttoService;
import it.giustizia.smp.service.CommonService;
import it.giustizia.smp.service.ProcedimentoService;
import it.giustizia.smp.service.webservices.IDocumentaleService;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Message;
import it.giustizia.smp.utility.RequestUtil;
import it.giustizia.smp.utility.Utility;

@Controller
public class BaseController  {

	protected static String							MESSAGELIST		= Constants.MESSAGELIST;
	protected static String							SUCCESS_MESSAGE	= Constants.SUCCESS_MESSAGE;
	protected Logger								logger			= Logger.getLogger(LoginController.class);
	//@Autowired(required = true)
	//protected HttpServletRequest					request;
	//@Autowired(required = true)
	//protected HttpSession							session;
	@Autowired
	private PropertyEditorRegistrar					customPropertyEditorRegistrar;
	
	@Autowired
	protected ProcedimentoService						procedimentoService;
	
	@Autowired
	protected IDocumentaleService	documentaleService;
	@Autowired
	protected AttoService			attoService;
	@Autowired
	protected CommonService commonService;

	public long getIdTipoProcedimentoInSessione(HttpServletRequest request) {
		long idTipoProc = 0;
		Object objTipoProcedimento = request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO);
		
		if(objTipoProcedimento instanceof String)
			idTipoProc = Long.parseLong((String) request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO));
		if(objTipoProcedimento instanceof Long)
			idTipoProc = (Long) request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO);
	
		return idTipoProc;
	}

	
	
	public Object getIndietroForm() {
//		Object datiPerIndietro = request.getSession().getAttribute(Constants.Key.DATI_PER_INDIETRO);
//		request.getSession().removeAttribute(Constants.Key.DATI_PER_INDIETRO);
//		return datiPerIndietro;
		return null;
	}

	public void setIndietroForm(Object datiPerIndietro) {
//		request.getSession().setAttribute(Constants.Key.DATI_PER_INDIETRO, datiPerIndietro);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {

		customPropertyEditorRegistrar.registerCustomEditors(binder);
	}
	

	// DG PROG 26 MEV MARZO_GIUGNO Ticket INC000001895203 formattazione data errorer soggetto con data nascita 30/05/1976
	
//	@InitBinder
//	public void initBinderDate(WebDataBinder binder) {
//	    CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true);
//	    binder.registerCustomEditor(Date.class, editor);
//	}

//	public void setMessageSource(ReloadableResourceBundleMessageSource messageSource) {
//
//		this.messageSource = messageSource;
//	}

	protected void addMessageIntoList(List<Message> messageList, String tipoMessaggio, String codiceMessaggio, String arguments) {
		if(messageList==null){
			messageList = new ArrayList<Message>();
		}
		messageList.add(new Message(null, codiceMessaggio, arguments, Message.SEPARATOR));
	}

	protected void setMessage(String tipoMessaggio, String codiceMessaggio, String arguments, boolean redirect) {

		// String message =
		// messageSource.getMessage("procedimento.stato.richiesto", new
		// Object[]{"cic cic"}, Locale.ITALIAN);
		setMessage(tipoMessaggio, codiceMessaggio, arguments, redirect, Message.SEPARATOR);
	}
	
	protected void setMessage(String tipoMessaggio, EccezioneGenerica e, boolean redirect) {

		String codiceMessaggio = StringUtils.hasText(e.getCode()) ? e.getCode() : "segnalazione.generica";
		String messaggio = StringUtils.hasText(e.getMessage()) ? e.getMessage() : "";
		String separatore = (messaggio.indexOf(Message.SEPARATOR) > -1) ? "###" : Message.SEPARATOR;
		setMessage(tipoMessaggio, codiceMessaggio, messaggio, redirect, separatore);
	}
	
	private void setMessage(String tipoMessaggio, String codiceMessaggio, String arguments, boolean redirect, String separator) {
		Message message = new Message(null, codiceMessaggio, arguments, separator);
		if (redirect) {
			getSession().setAttribute(tipoMessaggio, message);
		} else {
			getRequest().setAttribute(tipoMessaggio, message);
		}
	}

	protected void saveIntoSession(Object obj, String id, boolean ignoreExisting) {
        // scrive in sessione se ignoreExisting=true OPPURE se non esiste già l'oggetto avente id pari a id
        if( ignoreExisting || !existsIntoSession(id) )
            getSession().setAttribute(id, obj);       
    }
	
	protected void removeFromSession(String id) {
        getSession().removeAttribute(id);
    }
	
	protected Object getIntoSession(String id, boolean remove) {
		if(existsIntoSession(id)){
			Object value = getSession().getAttribute(id);
			if(remove){
				removeFromSession(id);
			}
			return value;
		}
		return null;
		
	}
	
	private boolean existsIntoSession(String id) {
        return getSession().getAttribute(id) != null;
    }
    

	protected void setMessaggiErrori(List<Message> messages, boolean redirect) {

		if (redirect) {
			getSession().setAttribute(Constants.LISTAMESSAGGIERRORI, messages);
		} else {
			getRequest().setAttribute(Constants.LISTAMESSAGGIERRORI, messages);
		}
	}	
	
	protected void setMessages(List<Message> messages, boolean redirect) {

		if (redirect) {
			getSession().setAttribute(Constants.MESSAGELIST, messages);
		} else {
			getRequest().setAttribute(Constants.MESSAGELIST, messages);
		}
	}

	protected void setMessagesSuccess(List<Message> messages, boolean redirect) {

		if (redirect) {
			getSession().setAttribute(Constants.SUCCESS_MESSAGE_LIST, messages);
		} else {
			getRequest().setAttribute(Constants.SUCCESS_MESSAGE_LIST, messages);
		}
	}

	protected void updateProcedimentoIntoSession(long idProcedimento) {

		ProcedimentoForm procedimento = procedimentoService.getDettaglioProcedimento(idProcedimento);
		if (procedimento != null) {
			if (Utility.isDefinito(procedimento.getTipoProcedimento())) {
				String tipoProcedimento = procedimento.getTipoProcedimento().getId();
				getSession().setAttribute(Constants.ID_TIPO_PROCEDIMENTO, tipoProcedimento);
				if (tipoProcedimento.equals(Constants.TIPOPROC_AGGRAVAMENTO_PERSONALE) ||
					tipoProcedimento.equals(""+Constants.TIPOPROC_MODIFICA_PERSONALE_CAM) ||
					tipoProcedimento.equals(""+Constants.TIPOPROC_MODIFICA_PATRIMONIALE_CAM)) {
					Long idPadre = procedimentoService.getIdPadreAggravamento(idProcedimento);
					if (idPadre != null) {
						ProcedimentoForm padre = procedimentoService.getDettaglioProcedimento(idPadre);
						procedimento.setAnnoPadre(padre.getAnno());
						procedimento.setNumeroPadre(padre.getNumero());
					}
				}
			}
			getSession().setAttribute(Constants.ID_PROCEDIMENTO, procedimento);
			this.updateViewAndMenu(procedimento, getRequest());
		}
	}
	
	protected void updateViewAndMenu (ProcedimentoForm procedimento, HttpServletRequest request){

		// ------determino la modalità di visualizzazione delle pagine e le voci del menu da visualizzare
		request.getSession().setAttribute("disabledViewProc", null);
		request.getSession().setAttribute("disabledViewMenu", null);
		
		AttoForm atto = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
		int idTipoFascicolo = getIdTipoFascicolo(false);

		if((Long.toString((((ProfiloForm) request.getSession().getAttribute("profilo")).getIdTipoUfficio())).equals(Constants.TIPO_UFFICIO_PROCURA)) &&
				(procedimento.getStatoProcedimento().getId().equals(Constants.STATOPROC_INVIATO_AL_TRIBUNALE) || 
				 procedimento.getStatoProcedimento().getId().equals(Constants.STATOPROC_DEFINITO_IN_I_GRADO_PER_INCOMPETENZA_CON_RESTITUZIONE_ATTI_ALLA_PROCURA) ||
				 procedimento.getStatoProcedimento().getId().equals(Constants.STATOPROC_DEFINITO_IN_I_GRADO_PER_INCOMPETENZA_CON_INVIO_ATTI_AL_TRIBUNALE) ||
				 procedimento.getStatoProcedimento().getId().equals(Constants.STATOPROC_ISCRITTO_IN_I_GRADO_DI_GIUDIZIO) ||
				 procedimento.getStatoProcedimento().getId().equals(Constants.STATOPROC_RIAPERTO_PER_INCOMPETENZA_TRIBUNALE) ||
				 procedimento.getStatoProcedimento().getId().equals(Constants.STATOPROC_DEFINITO_IN_I_GRADO_PER_INCOMPETENZA_DELLA_PROCURA) ||
				 procedimento.getStatoProcedimento().getId().equals(Constants.STATOPROC_RIUNITO) ||
				 procedimento.getStatoProcedimento().getId().equals(Constants.STATOPROC_IMPUGNATO_CDA) ||
				 Long.valueOf(procedimento.getStatoProcedimento().getId()) == Constants.STATOPROC_GIUDIZIO_DEFINITIVO))
		{
			request.getSession().setAttribute("disabledViewProc", "true");
			request.getSession().setAttribute("disabledViewMenu", "all");
		}

		if( (Long.toString((((ProfiloForm) request.getSession().getAttribute("profilo")).getIdTipoUfficio())).equals(Constants.TIPO_UFFICIO_PROCURA)) &&
				(Long.valueOf(procedimento.getStatoProcedimento().getId()) == Constants.STATOPROC_IN_LAVORAZIONE ||
				procedimento.getStatoProcedimento().getId().equals(Constants.STATOPROC_ISCRITTO))){
			request.getSession().setAttribute("disabledViewMenu", "notAll_Procura_noBene");
		    
		}
		
		if( (Long.toString((((ProfiloForm) request.getSession().getAttribute("profilo")).getIdTipoUfficio())).equals(Constants.TIPO_UFFICIO_PROCURA)) &&
				((procedimento.getStatoProcedimento().getId().equals(Constants.STATOPROC_DEFINITO_I_GRADO)||
				  procedimento.getStatoProcedimento().getId().equals(Constants.STATOPROC_DEFINITO_II_GRADO)))){
			request.getSession().setAttribute("disabledViewProc", "true");
			request.getSession().setAttribute("disabledViewMenu", "notAll_Procura");
		}
		
		if( (Long.toString((((ProfiloForm) request.getSession().getAttribute("profilo")).getIdTipoUfficio())).equals(Constants.TIPO_UFFICIO_TRIBUNALE)) && 
				(procedimento.getStatoProcedimento().getId().equals(Constants.STATOPROC_DEFINITO_IN_I_GRADO_PER_INCOMPETENZA_CON_RESTITUZIONE_ATTI_ALLA_PROCURA) ||
						 procedimento.getStatoProcedimento().getId().equals(Constants.STATOPROC_DEFINITO_IN_I_GRADO_PER_INCOMPETENZA_CON_INVIO_ATTI_AL_TRIBUNALE) || Long.valueOf(procedimento
									.getStatoProcedimento().getId()) == Constants.STATOPROC_GIUDIZIO_DEFINITIVO))
		{
			request.getSession().setAttribute("disabledViewProc", "true");
			request.getSession().setAttribute("disabledViewMenu", "all");
		}

		if( (Long.toString((((ProfiloForm) request.getSession().getAttribute("profilo")).getIdTipoUfficio())).equals(Constants.TIPO_UFFICIO_TRIBUNALE)) && 
//						(procedimento.getStatoProcedimento().getId().equals(Constants.STATOPROC_ISCRITTO) ||
//						 procedimento.getStatoProcedimento().getId().equals(Constants.STATOPROC_TRATTAZIONE_I_GRADO) ||
//						 procedimento.getStatoProcedimento().getId().equals(Constants.STATOPROC_DEFINITO_I_GRADO) ||
//						 procedimento.getStatoProcedimento().getId().equals(Constants.STATOPROC_DEFINITO_II_GRADO) ||
//						 procedimento.getStatoProcedimento().getId().equals(Constants.STATOPROC_RIUNITO)))
				!(procedimento.getStatoProcedimento().getId().equals(Constants.STATOPROC_IN_ACQUISIZ_PROPOSTA)))
		{
			request.getSession().setAttribute("disabledViewProc", "true");
			request.getSession().setAttribute("disabledViewMenu", "notAll_Tribunale");
		}
		if(procedimento.getTipoProcedimento() != null && procedimento.getTipoProcedimento().getId() != null &&procedimento.getTipoProcedimento().getId().equals(Constants.TIPOPROC_ACCERTAMENTO) && 
				(procedimento.getStatoProcedimento().getId().equals(Constants.STATOPROC_ISCRITTO))) {
			request.getSession().setAttribute("disabledViewMenu", "notAll_Procura");
		}
		
		if( ( (((ProfiloForm) request.getSession().getAttribute("profilo")).getIdTipoUfficio() == Constants.TIPO_UFFICIO_CORTE_D_APPELLO))   && 
				(procedimento.getStatoProcedimento().getId().equals(Constants.STATOPROC_ISCRITTO)|| 
				 procedimento.getStatoProcedimento().getId().equals(Constants.STATOPROC_DEFINITO_II_GRADO)|| 
				 Long.valueOf(procedimento.getStatoProcedimento().getId()) ==Constants.STATOPROC_GIUDIZIO_DEFINITIVO ))
		{
			request.getSession().setAttribute("disabledViewProc", "true");
			request.getSession().setAttribute("disabledViewMenu", "all");
		}
		
		if(procedimento.getTipoProcedimento() != null && procedimento.getTipoProcedimento().getId() != null && procedimento.getTipoProcedimento().getId().equals(Constants.TIPOPROC_OPPOSIZIONE_ALLA_RESTITUZIONE)) {
			request.getSession().setAttribute("disabledViewProc", "true");
		}		

		if(procedimento.getTipoProcedimento() != null && procedimento.getTipoProcedimento().getId() != null && procedimento.getTipoProcedimento().getId().equals(Constants.TIPOPROC_REVOCAZIONE_CONFISCA)) {
			request.getSession().setAttribute("disabledViewProc", "true");
		}		

		if(procedimento.getTipoProcedimento() != null && procedimento.getTipoProcedimento().getId() != null && procedimento.getTipoProcedimento().getId().equals(Constants.TIPOPROC_RIESAME_PROVV_QUESTURA) &&
				Constants.TIPO_UFFICIO_PROCURA.equals( String.valueOf((((ProfiloForm) request.getSession().getAttribute("profilo")).getIdTipoUfficio()))) ) {
			request.getSession().setAttribute("disabledViewProc", "true");
		}
		
		if(atto != null && atto.getTipoAtto() != null && atto.getTipoAtto().getId().equals(Constants.TIPO_ATTO_ACCERTAMENTO)) {
			request.getSession().setAttribute("disabledViewMenu", "atto_Accertamento");
		}
		
		if(atto != null && atto.getTipoAtto() != null && atto.getTipoAtto().getId().equals(Constants.TIPO_ATTO_DECRETO) &&
			atto.getCategoriaAtto() != null && atto.getCategoriaAtto().getId().equals(Constants.DECRETO_DECISORIO_I_GRADO)) {
			request.getSession().setAttribute("disabledViewMenu", "atto_DecisorioIGrado");
		}
		if(atto != null && atto.getTipoAtto() != null && atto.getTipoAtto().getId().equals(Constants.TIPO_ATTO_DECRETO_II_GRADO) &&
				atto.getCategoriaAtto() != null && atto.getCategoriaAtto().getId().equals(Constants.DECRETO_DECISORIO_II_GRADO)) {
				request.getSession().setAttribute("disabledViewMenu", "atto_DecisorioIGrado");
		}
		if(atto != null && atto.getTipoAtto() != null && (atto.getTipoAtto().getId().equals(Constants.TIPO_ATTO_DECRETO) || atto.getTipoAtto().getId().equals(Constants.TIPO_ATTO_DECRETO_II_GRADO)) &&
			(atto.getCategoriaAtto() != null && (atto.getCategoriaAtto().getId().equals(Constants.DECRETO_FISSAZIONE_UDIENZA) ||
												atto.getCategoriaAtto().getId().equals(Constants.DECRETO_FISSAZIONE_UDIENZA_II_GRADO) ||
												atto.getCategoriaAtto().getId().equals(Constants.DECRETO_ANTICIPAZIONE_UDIENZA) ||
												atto.getCategoriaAtto().getId().equals(Constants.DECRETO_ANTICIPAZIONE_UDIENZA_II_GRADO) ||
												atto.getCategoriaAtto().getId().equals(Constants.DECRETO_RINVIO_UDIENZA)||
												atto.getCategoriaAtto().getId().equals(Constants.DECRETO_RINVIO_UDIENZA_II_GRADO)))) {
			request.getSession().setAttribute("disabledViewMenu", "atto_decretiUdienza");
		}
		if(atto != null && atto.getTipoAtto() != null && atto.getTipoAtto().getId().equals(Constants.TIPO_ATTO_DECRETO) &&
			(atto.getCategoriaAtto() != null && (atto.getCategoriaAtto().getId().equals(Constants.DECRETO_CONVALIDA_SEQUESTRO_ANTICIPATO) ||
												atto.getCategoriaAtto().getId().equals(Constants.DECRETO_CONVALIDA_SEQUESTRO_URGENTE) ))) {
			request.getSession().setAttribute("disabledViewProc", "true");
			request.getSession().setAttribute("disabledViewMenu", "atto_decretiConvalida");
		}
		if(atto != null && atto.getTipoAtto() != null && atto.getTipoAtto().getId().equals(Constants.TIPO_ATTO_DECRETO) &&
	            (atto.getCategoriaAtto() != null && (atto.getCategoriaAtto().getId().equals(Constants.DECRETO_SEQUESTRO_ANTICIPATO) ||
	                                                atto.getCategoriaAtto().getId().equals(Constants.DECRETO_SEQUESTRO_ORDINARIO) ))) {
	            request.getSession().setAttribute("disabledViewProc", "true");
	            request.getSession().setAttribute("disabledViewMenu", "atto_decretiSequestro");
	    }
		if(atto != null && atto.getTipoAtto() != null && atto.getTipoAtto().getId().equals(Constants.TIPO_ATTO_DECRETO_II_GRADO) &&
	            (atto.getCategoriaAtto() != null && atto.getCategoriaAtto().getId().equals(Constants.DECRETO_SEQUESTRO_ORDINARIO_II_GRADO) )) {
	            request.getSession().setAttribute("disabledViewProc", "true");
	            request.getSession().setAttribute("disabledViewMenu", "atto_decretiSequestro");
	    }
		if(atto != null && atto.getTipoAtto() != null && (atto.getTipoAtto().getId().equals(Constants.TIPO_ATTO_DECRETO) || atto.getTipoAtto().getId().equals(Constants.TIPO_ATTO_DECRETO_II_GRADO))&&
		        (atto.getCategoriaAtto() != null && 
		        	(atto.getCategoriaAtto().getId().equals(Constants.DECRETO_ASSEGNAZIONE_NUOVO_RUOLO) ||
	        			atto.getCategoriaAtto().getId().equals(Constants.DECRETO_ASSEGNAZIONE_NUOVO_RUOLO_II_GRADO) ||	
                        atto.getCategoriaAtto().getId().equals(Constants.DECRETO_REMISSIONE_SU_RUOLO) ||
                        atto.getCategoriaAtto().getId().equals(Constants.DECRETO_REMISSIONE_SU_RUOLO_II_GRADO)))) {
		        request.getSession().setAttribute("disabledViewMenu", "atto_decretiAssRem_ruolo");
		}
		// ------fine visualizzazione
		
		// ------gestione ricaricamento del menu al cambio stato del procedimento
		if (idTipoFascicolo == Constants.TIPO_FASCICOLO_MERITO){
			MenuController.caricaMenu(Constants.ID_PER_MENU_DETTAGLIO_PREVENZIONE, request);
		}
		if (idTipoFascicolo == Constants.TIPO_FASCICOLO_AGGRAVAMENTO){
			MenuController.caricaMenu(Constants.ID_PER_MENU_DETTAGLIO_AGGRAVAMENTO, request);
		}
		if (idTipoFascicolo == Constants.TIPO_FASCICOLO_OPP_DIVIETI_QUESTORE){
			MenuController.caricaMenu(Constants.ID_PER_MENU_DETTAGLIO_RIESAME, request);
		}
		if (idTipoFascicolo == Constants.TIPO_FASCICOLO_ACCERTAMENTO){
			MenuController.caricaMenu(Constants.ID_PER_MENU_DETTAGLIO_ACCERTAMENTO, request);
		}
		if (idTipoFascicolo == Constants.TIPO_FASCICOLO_GESTIONE){
			MenuController.caricaMenu(Constants.ID_PER_MENU_DETTAGLIO_GESTIONE, request);
		}
		if(idTipoFascicolo == Constants.TIPO_FASCICOLO_CAMERA_DI_CONSIGLIO) {
			MenuController.caricaMenu(Constants.ID_PER_MENU_DETTAGLIO_PREVENZIONE, request, true);
		}
		// ------fine gestione menu
		
		/****************** Utente Procura Generale ********************/
		if((Long.valueOf((((ProfiloForm) request.getSession().getAttribute("profilo")).getIdTipoUfficio())) == (Constants.TIPO_UFFICIO_PROCURA_GENERALE)))
		{
			request.getSession().setAttribute("disabledViewProc", "true");
			request.getSession().setAttribute("disabledViewMenu", "all");
		}		
	}
	
	protected int getIdTipoFascicolo(boolean removeFromSession) {
		Object tipoFascicolo = getIntoSession(Constants.TIPO_FASCICOLO, removeFromSession);
		int idTipoFascicolo =0;
		if(tipoFascicolo!=null){
			idTipoFascicolo = Integer.parseInt(tipoFascicolo.toString());
		}
		return idTipoFascicolo;
	}
	/**
	 * Imposta l'atto in sessione
	 * @param attoForm
	 */
	protected void impostaAtto(AttoForm attoForm){
		getSession().setAttribute(Constants.ATTO, attoForm);
	}
	
	/**
	 * Restituisce l'id ufficio utente
	 * @return l'id ufficio utente
	 */
	protected Integer recuperaIdUfficioUtente(){
		return RequestUtil.recuperaIdUfficioUtente(getRequest());
	}
	
	protected String recuperaNomeECognomeUtente(HttpSession	session){
		if (session.getAttribute(Constants.ID_UTENTE)!=null) {
			
			return ((TUtente)session.getAttribute(Constants.ID_UTENTE)).getNome() + " " + ((TUtente)session.getAttribute(Constants.ID_UTENTE)).getCognome() ;	
		}
		return "-Informazione non disponibile-"; 
		
	}
	
	/**
	 * Restituisce l'id del tipo ufficio
	 * @return l'id del tipo ufficio
	 */
	protected Integer recuperaIdTipoUfficio(){
		return RequestUtil.recuperaIdTipoUfficio(getRequest());
	}

	/**
	 * Restituisce l'id del procedimento
	 * @return l'id del procedimento
	 */
	protected long recuperaIdProcedimento(){
		return RequestUtil.recuperaIdProcedimento(getRequest());
	}
	/**
	 * Restituisce il procedimento
	 * @return il procedimento
	 */
	protected ProcedimentoForm recuperaProcedimento(){
		return RequestUtil.recuperaProcedimento(getRequest());
	}
	/**
	 * Restituisce il profilo
	 * @return il profilo
	 */
	protected ProfiloForm recuperaProfilo(){
		return RequestUtil.recuperaProfilo(getRequest());
	}
	/**
	 * Restituisce l'id del codice utente
	 * @return l'id del codice utente
	 */
	protected String recuperaIdCodiceUtente(){
		return RequestUtil.recuperaIdCodiceUtente(getRequest());
	}
	/**
	 * Restituisce l'atto
	 * @return l'atto
	 */
	protected AttoForm recuperaAtto(){
		return RequestUtil.recuperaAtto(getRequest());
	}
	
	protected String printMethodName(){
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement e = stacktrace[2];
	    return e.getClassName().concat(".").concat(e.getMethodName());
	}
	
	
	// INIZIO PIATTAFORMA DOCUMENTALE
	public void gestionePiattaformaDocumentale(AttoForm attoForm,
			HttpServletRequest request, long idUffOwn, BindingResult result) {
		
		BigInteger idAttoDocumentale= new BigInteger("-1");
		
		long idAtto;
		if(attoForm.getIdAtto() != null){
			idAtto = attoForm.getIdAtto();
		}else{
			idAtto = attoForm.getCodAtto();
		}
		
		AttoForm attoTmp= attoService.getAttoById(idAtto, idUffOwn);
		
		if(attoTmp != null){
		  attoForm.setDescVisibilita(attoTmp.getDescVisibilita());
		}
		
		if(attoForm.getAllegato().isAllegatoRimosso()){
			cancellaDocumento(attoForm, request);
			attoForm.getAllegato().setAllegatoRimosso(false);
		}
		
		if(logger.isDebugEnabled()){
			logger.debug("   0) - PiattaformaDocumentale ==> Invio Documento Pdoc online: " +  idAtto +" - " + idUffOwn);
			logger.debug("      -  attoForm.getIdAtto=  " +  attoForm.getIdAtto() +" - attoForm.getCodAtto = " + attoForm.getCodAtto());
		}
		
		Long uffOwnL = new Long (idUffOwn);
		Integer uffOwnI = uffOwnL != null ? uffOwnL.intValue() : null;
		idAttoDocumentale = documentaleService.invioDocumentoPDoc(attoForm, uffOwnI, RequestUtil.recuperaIdProcedimento(request));
		
		String tipoDocumento ="";
		if(attoForm.getAllegato().getContentType() != null){
			tipoDocumento =  attoForm.getAllegato().getContentType();
		}else if(attoForm.getAllegato().getFileData() != null){
			tipoDocumento =  attoForm.getAllegato().getFileData().getContentType();
		}
		if(!tipoDocumento.equalsIgnoreCase("application/pdf") && idAttoDocumentale!= null) {
			//long codAtto;
			//if(attoForm.getIdAtto()!=null) {
			//	attoForm.getIdAtto();
			//} else {
			//	attoForm.getCodAtto();
			//}
//			if(attoForm.getAllegato()!=null && attoForm.getAllegato().getFileName()!=null)
//				documentaleService.renderPdf(tipoDocumento, attoForm.getAllegato().getFileName(), codAtto, idAttoDocumentale, recuperaIdUfficioUtente());
		}
		
		gestioneFirmaRemotaDocumento(attoForm, idUffOwn, result, idAttoDocumentale);
	}

	public void gestioneFirmaRemotaDocumento(AttoForm attoForm, long idUffOwn,
			BindingResult result, BigInteger idAttoDocumentale) {
		boolean firmaOK = false;
		if(!"S".equalsIgnoreCase(attoForm.getAllegato().getDocumentoFirmato()) && attoForm.getAllegato().getFirmaRemota() != null &&
				attoForm.getAllegato().getFirmaRemota().isFirmaImmediata() 
				&& idAttoDocumentale != null && idAttoDocumentale.longValue() != -1 ){
			
			firmaOK  = attoService.firmaRemotaDocumento(attoForm, idAttoDocumentale.longValue(), idUffOwn);
        
			if (firmaOK){
				setMessage(Constants.SUCCESS_MESSAGE, "operation.success.atto.firma", null, false);
	        }else if(attoForm.getAllegato().getFirmaRemota().isFirmaImmediata()) {
	        	setMessage(Constants.MESSAGE, "operation.error.atto.firma", null, false);
	        	result.reject(Constants.MESSAGE);  	
	        }
			return;
		}
		
		if (attoForm.getAllegato()!=null && attoForm.getAllegato().getFirmaRemota() !=null && 
				attoForm.getAllegato().getFirmaRemota().isFirmaImmediata()
				&& idAttoDocumentale != null && idAttoDocumentale.longValue() <= 0){
			setMessage(Constants.MESSAGE, "operation.error.atto.firma", null, false);
        	result.reject(Constants.MESSAGE); 
        	return;
		}
	}
	
	protected void cancellaDocumento(AttoForm atto, HttpServletRequest request) {

		long idUfficio = recuperaIdUfficioUtente();
//		long idAtto = RequestUtil.recuperaIdAtto(request);
		long idAtto = atto.getCodAtto();

		TAtto tAtto = this.attoService.getTAttoById(idAtto, idUfficio);
		
		if(tAtto.getNumeDocumentoPdoc() != null){
			//lo cancello dalla PIATT DOC
			attoService.rimuoviDocumentoAllegatoDaAtto(idUfficio, atto.getCodAtto(), Utility.longToBigInteger(tAtto.getNumeDocumentoPdoc()), recuperaIdProcedimento());
		} else {
			//lo cancello da T_ATTO
//			attoService.rimuoviDocumentoAllegatoDaTAtto(idUfficio, atto.getCodAtto(), recuperaIdProcedimento());
			attoService.rimuoviDocumentoAllegatoDaTAtto(idUfficio, recuperaAtto().getCodAtto(), recuperaIdProcedimento());			
		}
		
		if(!atto.getAllegato().isAllegatoRimosso()){
			atto.getAllegato().setDocumento(null);
			atto.getAllegato().setFileData(null);
			atto.getAllegato().setFileName(null);
		}
		
		
	}

	protected void cancellaDocumentoRiunione(AttoForm atto, HttpServletRequest request) {

		long idUfficio = recuperaIdUfficioUtente();
//		long idAtto = RequestUtil.recuperaIdAtto(request);
		long idAtto = atto.getCodAtto();

		TAtto tAtto = this.attoService.getTAttoById(idAtto, idUfficio);
		
		if(tAtto.getNumeDocumentoPdoc() != null){
			//lo cancello dalla PIATT DOC
			attoService.rimuoviDocumentoAllegatoDaAtto(idUfficio, atto.getCodAtto(), Utility.longToBigInteger(tAtto.getNumeDocumentoPdoc()), recuperaIdProcedimento());
		} 
		
		if (tAtto.getFileDocumento() != null) {
			//lo cancello da T_ATTO
			attoService.rimuoviDocumentoAllegatoDaTAtto(idUfficio, recuperaAtto().getCodAtto(), recuperaIdProcedimento());			
		}
		
		if(!atto.getAllegato().isAllegatoRimosso()){
			atto.getAllegato().setDocumento(null);
			atto.getAllegato().setFileData(null);
			atto.getAllegato().setFileName(null);
		}
				
	}
	
	protected void inizializzaDocumento(AttoForm attoForm, HttpServletRequest request) {

		long idUfficio = recuperaIdUfficioUtente();
		// esiste un file
		if (attoForm.getAllegato().getFileData() != null && attoForm.getAllegato().getFileData().getSize() > 0) {
			attoForm.getAllegato().getFileData().getContentType();
			attoForm.getAllegato().setFileName(attoForm.getAllegato().getFileData().getOriginalFilename());
			attoForm.getAllegato().setContentType(attoForm.getAllegato().getFileData().getContentType());
			byte b[] = attoForm.getAllegato().getFileData().getBytes();
			Blob blob = Hibernate.createBlob(b);
			attoForm.getAllegato().setDocumento(blob);
		} else {
			// nessun file da associare: caricare quello esistente nel db
			// vale anche nel caso in cui non esiste alcun file
			AttoForm attoFromDB = this.attoService.getAttoById(attoForm.getCodAtto(), idUfficio);
			if (attoFromDB.getAllegato().getDocumento() != null) {
				attoForm.getAllegato().setDocumento(attoFromDB.getAllegato().getDocumento());
				attoForm.getAllegato().setFileName(attoFromDB.getAllegato().getFileName());
				attoForm.getAllegato().setContentType(attoFromDB.getAllegato().getContentType());
			}
		}
	}
	
	protected void inizializzaDocumentoUdi(UdienzaForm udienzaForm, HttpServletRequest request) {

		long idUfficio = recuperaIdUfficioUtente();
		// esiste un file
		if (udienzaForm.getAllegato().getFileData() != null && udienzaForm.getAllegato().getFileData().getSize() > 0) {
			udienzaForm.getAllegato().getFileData().getContentType();
			udienzaForm.getAllegato().setFileName(udienzaForm.getAllegato().getFileData().getOriginalFilename());
			udienzaForm.getAllegato().setContentType(udienzaForm.getAllegato().getFileData().getContentType());
			byte b[] = udienzaForm.getAllegato().getFileData().getBytes();
			Blob blob = Hibernate.createBlob(b);
			udienzaForm.getAllegato().setDocumento(blob);
		} else {
			// nessun file da associare: caricare quello esistente nel db
			// vale anche nel caso in cui non esiste alcun file
			AttoForm attoFromDB = this.attoService.getAttoById(udienzaForm.getCodAtto(), idUfficio);
			if (attoFromDB.getAllegato().getDocumento() != null) {
				udienzaForm.getAllegato().setDocumento(attoFromDB.getAllegato().getDocumento());
				udienzaForm.getAllegato().setFileName(attoFromDB.getAllegato().getFileName());
				udienzaForm.getAllegato().setContentType(attoFromDB.getAllegato().getContentType());
			}
		}
	}	
	
	protected void inizializzaDocumentoRiunione(RiunioneForm riunioneForm, HttpServletRequest request) {

		long idUfficio = recuperaIdUfficioUtente();
		// esiste un file
		if (riunioneForm.getAllegato().getFileData() != null && riunioneForm.getAllegato().getFileData().getSize() > 0) {
			riunioneForm.getAllegato().getFileData().getContentType();
			riunioneForm.getAllegato().setFileName(riunioneForm.getAllegato().getFileData().getOriginalFilename());
			riunioneForm.getAllegato().setContentType(riunioneForm.getAllegato().getFileData().getContentType());
			byte b[] = riunioneForm.getAllegato().getFileData().getBytes();
			Blob blob = Hibernate.createBlob(b);
			riunioneForm.getAllegato().setDocumento(blob);
		} else {
			// nessun file da associare: caricare quello esistente nel db
			// vale anche nel caso in cui non esiste alcun file
			AttoForm attoFromDB = this.attoService.getAttoById(riunioneForm.getCodAtto(), idUfficio);
			if (attoFromDB.getAllegato().getDocumento() != null) {
				riunioneForm.getAllegato().setDocumento(attoFromDB.getAllegato().getDocumento());
				riunioneForm.getAllegato().setFileName(attoFromDB.getAllegato().getFileName());
				riunioneForm.getAllegato().setContentType(attoFromDB.getAllegato().getContentType());
			}
		}
	}	
	
	// FINE PIATTAFORMA DOCUMENTALE

	protected Long recuperaIdAtto(){
		AttoForm attoForm = recuperaAtto();
		return (attoForm != null) ? attoForm.getIdAtto() : (Long) null;
	}
	
	protected long recuperaCodAtto(){
		AttoForm attoForm = recuperaAtto();
		return (attoForm != null) ? attoForm.getCodAtto() : 0;
	}
	
	protected String recuperaIdCategoriaAtto(){
		AttoForm attoForm = recuperaAtto();
		return (attoForm != null) ? ((attoForm.getCategoriaAtto() != null) ? attoForm.getCategoriaAtto().getId() : "") : "";
	}
	
	 public HttpServletRequest getRequest() {
	        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	        return attributes.getRequest();
	 }

	 public HttpSession getSession() {
		 return getRequest().getSession();
	 }

}
