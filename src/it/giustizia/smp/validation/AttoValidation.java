package it.giustizia.smp.validation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.giustizia.smp.controller.procedimento.AttoController;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.BeneForm;
import it.giustizia.smp.form.BeneSoggettoForm;
import it.giustizia.smp.form.BeneStralcioForm;
import it.giustizia.smp.form.MagistratoForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.RiunioneForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.form.SoggettoStralcioForm;
import it.giustizia.smp.form.StralcioForm;
import it.giustizia.smp.form.UdienzaForm;
import it.giustizia.smp.model.TRelBeneAtto;
import it.giustizia.smp.service.AttoService;
import it.giustizia.smp.service.BeneService;
import it.giustizia.smp.service.CommonService;
import it.giustizia.smp.service.MisuraService;
import it.giustizia.smp.service.ProcedimentoService;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Message;
import it.giustizia.smp.utility.RequestUtil;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.utility.ValidationUtil;

@Component("attoValidation")
public class AttoValidation extends BaseValidation implements Validator {

	@Autowired
	private AttoService				attoService;
	@Autowired
	private ProcedimentoService		procedimentoService;
	@Autowired
	private MisuraService			misuraService;
	@Autowired
	private MagistratoValidation	magistratoValidation;
	@Autowired
	private BeneService				beneService;
	@Autowired
	private CommonService			commonService;	
	@Autowired
	private ImpugnazioneValidation				impugnazioneValidation;

	public void setMagistratoValidation(MagistratoValidation magistratoValidation) {

		this.magistratoValidation = magistratoValidation;
	}

	public boolean supports(Class<?> clazz) {

		return AttoController.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {

	}
	
	public void validateElencoTribunale(AttoForm atto, Errors errors, HttpServletRequest request) {

		if (atto.getIdProcedimentoCda() == null) {
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "elenco.tribunale.item.selection", null, false);
			// request.setAttribute(MESSAGE, "no.item.found");
		}
	}
	
	public void validateRedazioneScheda(AttoForm attoForm, BindingResult errors, HttpServletRequest request) {

		if (attoForm.getDataRedazioneScheda() == null) {
			errors.rejectValue("dataRedazioneScheda", "NotNull");
		}
	}

	public void validateAttoDate(AttoForm attoForm, BindingResult errors, HttpServletRequest request) {
		if (attoForm.getDataEmissione() == null) {
			errors.rejectValue("dataEmissione", "NotNull");
		}
//		if(attoForm.getTipoAtto()!=null && attoForm.getTipoAtto().getId()!=null && attoForm.getTipoAtto().getId().equals(Constants.SENTENZA_DI_CASSAZIONE_DA_CDA) == false)
		// Andrea (AS): recupera il tipo dell'atto verificandone l'esistenza prima nel form (parametro "AttoForm") e successivamente,
		// nel caso in cui non fosse presente nell'oggetto form, cerca di recuperarlo dalla sessione (parametro "request").
		String idTipoAtto = (attoForm.getTipoAtto() != null) ? attoForm.getTipoAtto().getId() : (RequestUtil.recuperaIdTipoAtto(request) + "");
		if(!idTipoAtto.equals(Constants.SENTENZA_DI_CASSAZIONE_DA_CDA))
		{
			if (attoForm.getDataDeposito() == null) {
				errors.rejectValue("dataDeposito", "NotNull");
			}
		}

		Date dataDeposito = attoForm.getDataDeposito();
		Date dataEmissione = attoForm.getDataEmissione();

		/**
		 * MEV Console: Decreto di remissione su ruolo - 
		 * Il sistema invia messaggio errato quando al conferma e invia 
		 * la data di deposito è inferiore alla data di emissione 
		 * (dovrebbe inviare “la data deposito non può essere inferiore alla data di emissione”)
		 */

		if ((dataDeposito != null && dataEmissione != null) && dataEmissione.after(dataDeposito)) {
			super.setMessage(Constants.MESSAGE,"error.dataEmiss.dataDep",null,false);
			errors.reject(Constants.MESSAGE);
		}
		
		//controllo sulla data apertura fascicolo
		ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute("procedimento");
		Date dataApeFasc = Utility.removeTime(procedimento.getDataApertura());
		if ((dataEmissione != null) && dataEmissione.before(dataApeFasc)) {
			errors.rejectValue("dataEmissione", "error.dataApeFasc");
		}
		if ((dataDeposito != null) && dataDeposito.before(dataApeFasc)) {
			errors.rejectValue("dataDeposito", "error.dataApeFasc");
		}		
		//
	}

	public void validateAttoDateSalva(AttoForm attoForm, BindingResult errors, HttpServletRequest request) {
		
		if ((attoForm.getDataDeposito() != null && attoForm.getDataEmissione() != null) && attoForm.getDataEmissione().after(attoForm.getDataDeposito())) {
			super.setMessage(Constants.MESSAGE,"error.dataEmiss.dataDep",null,false);
			errors.reject(Constants.MESSAGE);
		}
		
		//controllo sulla data apertura fascicolo
		ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute("procedimento");
		Date dataApeFasc = Utility.removeTime(procedimento.getDataApertura());
		if ((attoForm.getDataEmissione() != null) && attoForm.getDataEmissione().before(dataApeFasc)) {
			errors.rejectValue("dataEmissione", "error.dataApeFasc");
		}
		if ((attoForm.getDataDeposito() != null) && attoForm.getDataDeposito().before(dataApeFasc)) {
			errors.rejectValue("dataDeposito", "error.dataApeFasc");
		}		
		//
	}
	
	public void validateRestituzionePerEquivalente(AttoForm attoForm, BindingResult errors, HttpServletRequest request) {
				

		if (attoForm.isRestituzionePerEquivalente() && (attoForm.getNote() == null || "".equalsIgnoreCase(attoForm.getNote()))) {
			errors.rejectValue("note", "NotNull");
		}		
		
	}	
	public void validateAttoDateSalvaCorrezioneErroreMateriale(AttoForm attoForm, BindingResult errors, HttpServletRequest request) {
		
		Date now = Calendar.getInstance().getTime();		
		if ((attoForm.getDataEmissione() != null) && attoForm.getDataEmissione().after(now)) {
			errors.rejectValue("dataEmissione", "error.dataCorrente");
		}
		//controllo sulla data apertura fascicolo
		ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute("procedimento");
		Date dataApeFasc = Utility.removeTime(procedimento.getDataApertura());
		if ((attoForm.getDataEmissione() != null) && attoForm.getDataEmissione().before(dataApeFasc)) {
			errors.rejectValue("dataEmissione", "error.dataApeFasc");
		}
		//
	}
	
	public void validateAttoDataEmissioneAfterDeposito(AttoForm attoForm, BindingResult errors, HttpServletRequest request) {

		if ((attoForm.getDataDeposito() != null && attoForm.getDataEmissione() != null) && attoForm.getDataEmissione().after(attoForm.getDataDeposito())) {
			super.setMessage(Constants.MESSAGE,"error.dataEmiss.dataDep",null,false);
			errors.reject(Constants.MESSAGE);
		}
	}
	public void validateConfermaDefinitivita(AttoForm attoForm, BindingResult errors, HttpServletRequest request, ModelMap map) {

		if (attoForm.getDataDefinitivita() == null) {
			errors.rejectValue("dataDefinitivita", "NotNull");
		}
	}

	public void validateFormDecretoIncompetenza(AttoForm attoForm, BindingResult errors, HttpServletRequest request, ModelMap map,boolean invio) {

		//List<Message> customMessageList = new ArrayList<Message>();
		if (attoForm.getDataEmissione() == null) {
			errors.rejectValue("dataEmissione", "NotNull");
		}
		if (attoForm.getDataDeposito() == null) {
			errors.rejectValue("dataDeposito", "NotNull");
		}
		
		
//		if(invio && (attoForm.getCategoriaAtto().getId().equals(Constants.DECRETO_INCOMPETENZA_TRASF_ALTRO_TRIBUNALE) ||
//				     attoForm.getCategoriaAtto().getId().equals(Constants.DECRETO_INCOMPETENZA_RESTITUZIONE_ATTI_PROCURA))){			
//			//cerco se esite la procura generale -->tipo ufficio uguale ad 8
//			List l = commonService.getNumeProcTRelProcProc(attoForm.getIdProcedimento(),Constants.TIPO_UFFICIO_PROCURA_GENERALE);
//			if(l == null || (l != null && l.isEmpty())){
//				 errors.reject(Constants.MESSAGE);
//				super.addMessageIntoList(customMessageList, Constants.MESSAGE, "error.procedimento.non.acquisito", null);
//			}			
//		}
//		super.setMessages(customMessageList, false);
	}

	public void validateSalvaDecisorioIGradoOpposizione(AttoForm attoForm, Errors errors, HttpServletRequest request) {

		ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute("procedimento");
		Date dataApeFasc = Utility.removeTime(procedimento.getDataApertura());
		Date now = Calendar.getInstance().getTime();
		if (attoForm.getDataRegistrazione() != null) {
			if (attoForm.getEsitoOpposizione().getId() != null && Long.parseLong(attoForm.getEsitoOpposizione().getId()) < 0) {
				errors.rejectValue("esitoOpposizione.id", "NotNull");
			}
			if (attoForm.getDataEmissione() == null) {
				errors.rejectValue("dataEmissione", "NotNull");
			}
			if (attoForm.getDataDeposito() == null) {
				errors.rejectValue("dataDeposito", "NotNull");
			}
		}
		if ((attoForm.getDataEmissione() != null) && attoForm.getDataEmissione().after(now)) {
			errors.rejectValue("dataEmissione", "error.dataCorrente");
		}
		if ((attoForm.getDataDeposito() != null) && attoForm.getDataDeposito().after(now)) {
			errors.rejectValue("dataDeposito", "error.dataCorrente");
		}
		if ((attoForm.getDataEmissione() != null) && attoForm.getDataEmissione().before(dataApeFasc)) {
			errors.rejectValue("dataEmissione", "error.dataApeFasc");
		}
		if ((attoForm.getDataDeposito() != null) && attoForm.getDataDeposito().before(dataApeFasc)) {
			errors.rejectValue("dataDeposito", "error.dataApeFasc");
		}
	}
	
	public void validateInviaDefinitivitaDecisorioIGradoOpposizione(AttoForm attoForm, Errors errors, HttpServletRequest request) {

		if(attoForm.getDataDefinitivita() == null) {
			errors.rejectValue("dataDefinitivita", "NotNull");
			super.setMessage(Constants.MESSAGE, "error.atto.datadefinitivita", null, false);
		}
	}

	
	public void validateDecretoApplicazioneProvvisoria(ProcedimentoForm procedimento,SoggettoForm soggettoForm,AttoForm attoForm, BindingResult result, HttpServletRequest request) {

		
		Date now = Calendar.getInstance().getTime();
		Date dataIscrizioneProc = Utility.removeTime(procedimento.getDataApertura());
		Date dataEmissioneAtto = attoForm.getDataEmissione();
		Date dataDepositoAtto = attoForm.getDataDeposito();
		
		if(soggettoForm.getMisura().getIdSelezionati().size()>=0 && soggettoForm.getMisura().getTipoMisura().getId().equals("-1")){
			super.setMessage(Constants.MESSAGE,"error.soggetto.seleziona.misura",null,false);
			result.reject(Constants.MESSAGE);
		}
		
        if (soggettoForm.getMisura() != null && soggettoForm.getMisura().getNormativaDiRiferimento() != null && soggettoForm.getMisura().getNormativaDiRiferimento().getId() != null) {

            boolean localitaObbligatoria = commonService.localitaObbligatoriaNelTipoMisuraSelezionato(Long.parseLong(soggettoForm.getMisura().getTipoMisura().getId()),"AP");
            if ((soggettoForm.getMisura().getLocalita() == null) && localitaObbligatoria) {

                super.setMessage(Constants.MESSAGE, "error.soggetto.misurapersonale", null, false);
            	result.reject(Constants.MESSAGE);
            }
        }		
        
        if(soggettoForm.getMisura() != null && soggettoForm.getMisura().getTipoMisura() != null
        		&& !soggettoForm.getMisura().getTipoMisura().getId().equals(Constants.NO_ITEM_SELECTED)
        		&& soggettoForm.getMisura().getNormativaDiRiferimento() != null 
        		&& soggettoForm.getMisura().getNormativaDiRiferimento().getId() != null
        				&& soggettoForm.getMisura().getNormativaDiRiferimento().getId().equals(Constants.NO_ITEM_SELECTED)) {
            super.setMessage(Constants.MESSAGE, "error.soggetto.normativa.obbligatoria", null, false);
        	result.reject(Constants.MESSAGE);        	
        }
		
		if(dataEmissioneAtto!=null){
			if(dataIscrizioneProc.after(dataEmissioneAtto)){
				super.setMessage(Constants.MESSAGE,"error.data.emissione.iscrizione",null,false);
				result.reject(Constants.MESSAGE);
				
			}
			if(dataEmissioneAtto.after(now)){
				super.setMessage(Constants.MESSAGE,"error.data.emissione.now",null,false);
				result.reject(Constants.MESSAGE);
				
			}
			
		}
		if(dataDepositoAtto!=null&&dataEmissioneAtto!=null){
			if(dataDepositoAtto.before(dataEmissioneAtto)){
				super.setMessage(Constants.MESSAGE,"error.data.emissione.deposito",null,false);
				result.reject(Constants.MESSAGE);
				
			}
		}
		
		if(dataDepositoAtto!=null){
			if(dataIscrizioneProc.after(dataDepositoAtto)){
				super.setMessage(Constants.MESSAGE,"error.data.deposito.iscrizione",null,false);
				result.reject(Constants.MESSAGE);
				
			}
			if(dataDepositoAtto.after(now)){
				super.setMessage(Constants.MESSAGE,"error.data.deposito.now",null,false);
				result.reject(Constants.MESSAGE);
				
			}
			
		}
		
		
	}
	
	
	
	
	public void validateSalvaDecisorioPrevenzione(AttoForm attoForm, Errors errors, HttpServletRequest request, ModelMap modelMap) {

//		List<Message> customMessageList = new ArrayList<Message>();
		ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute(Constants.ID_PROCEDIMENTO);
//		long idUffOwn = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		Date dataApeFasc = Utility.removeTime(procedimento.getDataApertura());
		Date now = Calendar.getInstance().getTime();
		if (attoForm.getDataRegistrazione() != null) {
			if (attoForm.getDataEmissione() == null) {
				errors.rejectValue("dataEmissione", "NotNull");
			}
			if (attoForm.getDataDeposito() == null) {
				errors.rejectValue("dataDeposito", "NotNull");
			}

		}
		if ((attoForm.getDataEmissione() != null) && attoForm.getDataEmissione().after(now)) {
			errors.rejectValue("dataEmissione", "error.dataCorrente");
		}
		if ((attoForm.getDataDeposito() != null) && attoForm.getDataDeposito().after(now)) {
			errors.rejectValue("dataDeposito", "error.dataCorrente");
		}
		if ((attoForm.getDataEmissione() != null) && attoForm.getDataEmissione().before(dataApeFasc)) {
			errors.rejectValue("dataEmissione", "error.dataApeFasc");
		}
		if ((attoForm.getDataDeposito() != null) && attoForm.getDataDeposito().before(dataApeFasc)) {
			errors.rejectValue("dataDeposito", "error.dataApeFasc");
		}
	}
	
	public void validateSalvaSentenzaCassazione(AttoForm attoForm, Errors errors, HttpServletRequest request, ModelMap modelMap) {

		List<Message> customMessageList = new ArrayList<Message>();
		ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute(Constants.ID_PROCEDIMENTO);
		Date dataApeFasc = Utility.removeTime(procedimento.getDataApertura());
		Date now = Calendar.getInstance().getTime();
		

		if (attoForm.getDataEmissione() == null) {
			errors.rejectValue("dataEmissione", "NotNull");
		}
		if (attoForm.getDataDeposito() == null) {
			errors.rejectValue("dataDeposito", "NotNull");
		}

	
		if ((attoForm.getDataEmissione() != null) && attoForm.getDataEmissione().after(now)) {
			errors.rejectValue("dataEmissione", "error.dataCorrente");
		}
		if ((attoForm.getDataDeposito() != null) && attoForm.getDataDeposito().after(now)) {
			errors.rejectValue("dataDeposito", "error.dataCorrente");
		}
		if ((attoForm.getDataEmissione() != null) && attoForm.getDataEmissione().before(dataApeFasc)) {
			errors.rejectValue("dataEmissione", "error.dataApeFasc");
		}
		if ((attoForm.getDataDeposito() != null) && attoForm.getDataDeposito().before(dataApeFasc)) {
			errors.rejectValue("dataDeposito", "error.dataApeFasc");
		}
		super.setMessages(customMessageList, false);
	}
	
	@SuppressWarnings("rawtypes")
	public void validateSalvaSentenzaOpposizioneDissequestro(AttoForm attoForm, Errors errors, HttpServletRequest request, ModelMap modelMap) {

		List<Message> customMessageList = new ArrayList<Message>();
		ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute(Constants.ID_PROCEDIMENTO);
		long idUffOwn = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		Date dataApeFasc = Utility.removeTime(procedimento.getDataApertura());
		Date now = Calendar.getInstance().getTime();
		List beniSenzaDecisione = misuraService.getBeniSenzaDecisorio(procedimento.getIdProcedimento(), attoForm.getCodAtto(), idUffOwn, "I_GRADO");
		if (beniSenzaDecisione != null && beniSenzaDecisione.size() > 0) {
			modelMap.addAttribute("beniSenzaDecisione", beniSenzaDecisione);
			errors.reject(Constants.MESSAGE);
			super.addMessageIntoList(customMessageList, Constants.MESSAGE, "atto.decreto.decisorioIGrado.beniSenzaDecisioni.error", null);
		}
		if ((attoForm.getDataEmissione() != null) && attoForm.getDataEmissione().after(now)) {
			errors.rejectValue("dataEmissione", "error.dataCorrente");
		}
		if ((attoForm.getDataDeposito() != null) && attoForm.getDataDeposito().after(now)) {
			errors.rejectValue("dataDeposito", "error.dataCorrente");
		}
		if ((attoForm.getDataEmissione() != null) && attoForm.getDataEmissione().before(dataApeFasc)) {
			errors.rejectValue("dataEmissione", "error.dataApeFasc");
		}
		if ((attoForm.getDataDeposito() != null) && attoForm.getDataDeposito().before(dataApeFasc)) {
			errors.rejectValue("dataDeposito", "error.dataApeFasc");
		}
		super.setMessages(customMessageList, false);
	}

	@SuppressWarnings("rawtypes")
	public void validateSalvaSentenzaOpposizioneDissequestroIIGrado(AttoForm attoForm, Errors errors, HttpServletRequest request, ModelMap modelMap) {

		List<Message> customMessageList = new ArrayList<Message>();
		ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute(Constants.ID_PROCEDIMENTO);
		long idUffOwn = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		Date dataApeFasc = Utility.removeTime(procedimento.getDataApertura());
		Date now = Calendar.getInstance().getTime();
		List beniSenzaDecisione = misuraService.getBeniSenzaDecisorio(procedimento.getIdProcedimento(), attoForm.getCodAtto(), idUffOwn, "II_GRADO");
		if (beniSenzaDecisione != null && beniSenzaDecisione.size() > 0) {
			modelMap.addAttribute("beniSenzaDecisione", beniSenzaDecisione);
			errors.reject(Constants.MESSAGE);
			super.addMessageIntoList(customMessageList, Constants.MESSAGE, "atto.decreto.decisorioIGrado.beniSenzaDecisioni.error", null);
		}
		if (attoForm.getDataEmissione() == null)
			errors.rejectValue("dataEmissione", "NotNull");
		if (attoForm.getDataDeposito() == null)
			errors.rejectValue("dataDeposito", "NotNull");
		
		if ((attoForm.getDataEmissione() != null) && attoForm.getDataEmissione().after(now)) {
			errors.rejectValue("dataEmissione", "error.dataCorrente");
		}
		if ((attoForm.getDataDeposito() != null) && attoForm.getDataDeposito().after(now)) {
			errors.rejectValue("dataDeposito", "error.dataCorrente");
		}
		if (((attoForm.getDataEmissione() != null) && attoForm.getDataEmissione().before(dataApeFasc))
				||((attoForm.getDataDeposito() != null) && attoForm.getDataDeposito().before(dataApeFasc))) {
//			errors.rejectValue("dataEmissione", "error.dataApeFasc");
			super.addMessageIntoList(customMessageList, Constants.MESSAGE, "error.dataApeFasc", null);
		}
//		if ((attoForm.getDataDeposito() != null) && attoForm.getDataDeposito().before(dataApeFasc)) {
//			errors.rejectValue("dataDeposito", "error.dataApeFasc");
//			super.addMessageIntoList(customMessageList, Constants.MESSAGE, "error.dataApeFasc", null);
//		}
		super.setMessages(customMessageList, false);
	}	
	
	@SuppressWarnings("rawtypes")
	public void validateSalvaSentenzaRevocazioneConfisca(AttoForm attoForm, Errors errors, HttpServletRequest request, ModelMap modelMap) {

		List<Message> customMessageList = new ArrayList<Message>();
		ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute(Constants.ID_PROCEDIMENTO);
		long idUffOwn = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		Date dataApeFasc = Utility.removeTime(procedimento.getDataApertura());
		Date now = Calendar.getInstance().getTime();
		
		String descUfficio = "";
		if (request.getSession().getAttribute("idTipoUfficio") == Constants.TIPO_UFFICIO_TRIBUNALE) {
			descUfficio = "I_GRADO";
		}
		else{
			descUfficio = "II_GRADO";
		}

		List beniSenzaDecisione = misuraService.getBeniSenzaDecisorio(procedimento.getIdProcedimento(), attoForm.getCodAtto(), idUffOwn, descUfficio);
		if (beniSenzaDecisione != null && beniSenzaDecisione.size() > 0) {
			modelMap.addAttribute("beniSenzaDecisione", beniSenzaDecisione);
			errors.reject(Constants.MESSAGE);
			super.addMessageIntoList(customMessageList, Constants.MESSAGE, "atto.decreto.decisorioIGrado.beniSenzaDecisioni.error", null);
		}
		if ((attoForm.getDataEmissione() != null) && attoForm.getDataEmissione().after(now)) {
			errors.rejectValue("dataEmissione", "error.dataCorrente");
		}
		if ((attoForm.getDataDeposito() != null) && attoForm.getDataDeposito().after(now)) {
			errors.rejectValue("dataDeposito", "error.dataCorrente");
		}
		if ((attoForm.getDataEmissione() != null) && attoForm.getDataEmissione().before(dataApeFasc)) {
			errors.rejectValue("dataEmissione", "error.dataApeFasc");
		}
		if ((attoForm.getDataDeposito() != null) && attoForm.getDataDeposito().before(dataApeFasc)) {
			errors.rejectValue("dataDeposito", "error.dataApeFasc");
		}
		super.setMessages(customMessageList, false);
	}		

	public void validateInserisciFonte(AttoForm attoForm, Errors errors, HttpServletRequest request) {

		if ((attoForm.getCodAtto() == 0) && ((attoForm.getListaIdCheckbox() == null) || (attoForm.getListaIdCheckbox().size() == 0))) {
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "table.fonte.associati.item.selection", null, false);
		}
		long idUfficio = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		if ((attoForm.getCodAtto() > 0) && (attoForm.getListaIdCheckbox_associati() != null) && (procedimentoService.getSoggettiInteressatiAssociati(idUfficio, idProcedimento, attoForm.getCodAtto()).size() == attoForm.getListaIdCheckbox_associati().size())) {
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "table.fonte.associati.item.selection.one", null, false);
		}
		if (attoForm.getDataInformativa() == null) {
			errors.rejectValue("dataInformativa", "NotNull");
		}
		
		if (attoForm.getFonte().getId().equalsIgnoreCase(Constants.NO_ITEM_SELECTED)) {
			errors.rejectValue("fonte.id", "NotNull");
		}
		if (!attoForm.getFonte().getId().equalsIgnoreCase(Constants.NO_ITEM_SELECTED) && !attoForm.getFonte().getId().equalsIgnoreCase(Constants.FONTE_ALTRO)) {
			if (attoForm.getOrgano().getId().equalsIgnoreCase(Constants.NO_ITEM_SELECTED)) {
				errors.rejectValue("organo.id", "NotNull");
			}
			if (attoForm.getProvinciaOrgano().getId().equalsIgnoreCase(Constants.NO_ITEM_SELECTED)) {
				errors.rejectValue("provinciaOrgano.id", "NotNull");
			}
			if (attoForm.getComuneOrgano().getId().equalsIgnoreCase(Constants.NO_ITEM_SELECTED)) {
				errors.rejectValue("comuneOrgano.id", "NotNull");
			}
//			if (attoForm.getIndirizzoOrgano().getId().equalsIgnoreCase(Constants.NO_ITEM_SELECTED)) {
//				errors.rejectValue("indirizzoOrgano.id", "NotNull");
//			}
		}
		if (attoForm.getFonte().getId().equalsIgnoreCase(Constants.FONTE_ALTRO) && (attoForm.getAltroOrgano() == null)) {
			errors.rejectValue("altroOrgano", "NotNull");
		}
		//se ho già registrato l'atto non posso eliminarne la data di ricezione
		if(attoForm.getStatoAtto().getId() != null && attoForm.getStatoAtto().getId().equals(String.valueOf(Constants.STATOATTO_REGISTRATO)) && attoForm.getDataRicezione() == null){
			errors.rejectValue("dataRicezione", "NotNull");
		}
	}
	
	public void validateInserisciArchiviazione(AttoForm attoForm, Errors errors, HttpServletRequest request) {
		
		if (attoForm.getCategoriaAtto().getId().equalsIgnoreCase(Constants.NO_ITEM_SELECTED)) {
			errors.rejectValue("categoriaAtto.id", "NotNull");
		}
//		if (attoForm.getDataInformativa() == null) {
//			errors.rejectValue("dataInformativa", "NotNull");
//		}
		
		Date dataArchiviazione = attoForm.getDataInformativa();
		if(dataArchiviazione != null){
			
			ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute("procedimento");
			Date dataAperturaProc = procedimento.getDataApertura();
			
			//Date datadaIns = Utility.stringToDate(dataArchiviazione, "dd/MM/yyyy");
			if(dataArchiviazione.before(Utility.removeTime(dataAperturaProc))){
				errors.rejectValue("dataInformativa", "error.dataApeFasc");
			}else if(dataArchiviazione.after(Calendar.getInstance().getTime())){
				errors.rejectValue("dataInformativa", "error.dataCorrente");
			}
		}else{
			errors.rejectValue("dataInformativa", "NotNull");
		}
	}
	
	
//	public void validateAllegatoEFirma(AllegatoForm allegatoForm, Errors errors, HttpServletRequest request, TTpCatAtto tTpCatAtto) {
//
////		boolean obbl = isAllegatoObbligatorioPerCatAtto(idCatAtto);
//		boolean obbl = false;
//		if (tTpCatAtto != null && tTpCatAtto.getFlgAllegObbl() != null && 'S'== tTpCatAtto.getFlgAllegObbl()) {
//			obbl =  true;
//		}		
//		
//		boolean mancaAllegato = false;
//		
//		//se l'allegato è obbligatorio ...
//		if (obbl && allegatoForm.getFileData() !=null && (allegatoForm.getFileData().getFileItem().getName() == null || "".equals(allegatoForm.getFileData().getFileItem().getName()))) {
//			mancaAllegato= true;
//		}
//		
//		// verifica che, se la firma immediata vale true, esista un documento allegato
//        if( allegatoForm.getFirmaRemota() != null 
//                && allegatoForm.getFirmaRemota().isFirmaImmediata() 
//                && allegatoForm.getFileData() != null
//                && allegatoForm.getFileData().isEmpty() )
//        	mancaAllegato= true;   
//        
//        // verifica che, se presente un allegato da firmare, esso sia un PDF
//        if( allegatoForm.getFileData() !=null && (allegatoForm.getFileData().getFileItem().getName() != null && !"".equals(allegatoForm.getFileData().getFileItem().getName())) &&
//                !allegatoForm.getFileData().getContentType().equalsIgnoreCase("application/pdf") 
//                 && allegatoForm.getFirmaRemota() != null  && allegatoForm.getFirmaRemota().isFirmaImmediata()) {
//            		errors.rejectValue("allegato.fileData", "operation.error.allegato.tipoContenuto", new Object[]{"PDF"}, "");            
//        }
//        
//        if(mancaAllegato){
//        	errors.rejectValue("allegato.fileData", "NotNull");
//        }
//		
//	}

	public void validateDataRicezione(AttoForm attoForm, Errors errors, HttpServletRequest request){
	
		//l'allegato è obbligatorio solo se è settata la data ricezione
		if (attoForm.getDataRicezione() != null && (attoForm.getAllegato().getFileData() !=null && (attoForm.getAllegato().getFileData().getFileItem().getName() == null || "".equals(attoForm.getAllegato().getFileData().getFileItem().getName())))) {
			errors.rejectValue("allegato.fileData", "NotNull");
		}
	}
	
	
	public void validateAggiornaAccertamento(AttoForm attoForm, Errors errors) {

		if (attoForm.getDataInformativa() == null) {
			errors.rejectValue("dataInformativa", "NotNull");
		}
	}

	public void validateInserisciUdienza(UdienzaForm udienzaForm, boolean confermaInvia, List<SoggettoForm> sogIntSenzaDifensore, BindingResult errors, HttpServletRequest request, ModelMap map) {

		if (confermaInvia) {
			if (udienzaForm.getDataEmissione() == null) {
				errors.rejectValue("dataEmissione", "NotNull");
			}
			if (udienzaForm.getDataDeposito() == null) {
				errors.rejectValue("dataDeposito", "NotNull");
			}
		}
		boolean errorForm = false;
		ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute(Constants.ID_PROCEDIMENTO);
//		long idUffOwn = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		Date dataApeFasc = Utility.removeTime(procedimento.getDataApertura());
		Date now = Calendar.getInstance().getTime();
		
		if ((udienzaForm.getDataEmissione() != null) && udienzaForm.getDataEmissione().after(now)) {
			errorForm = true;
			errors.rejectValue("dataEmissione", "error.dataCorrente");
		}
		if ((udienzaForm.getDataDeposito() != null) && udienzaForm.getDataDeposito().after(now)) {
			errorForm = true;
			errors.rejectValue("dataDeposito", "error.dataCorrente");
		}
		if ((udienzaForm.getDataEmissione() != null) && udienzaForm.getDataEmissione().before(dataApeFasc)) {
			errorForm = true;
			errors.rejectValue("dataEmissione", "error.dataApeFasc");
		}
		if ((udienzaForm.getDataDeposito() != null) && udienzaForm.getDataDeposito().before(dataApeFasc)) {
			errorForm = true;
			errors.rejectValue("dataDeposito", "error.dataApeFasc");
		}
		
		
		
		if (udienzaForm.getDataUdienza() == null) {
			errors.rejectValue("dataUdienza", "NotNull");
			errorForm = true;
		}else if (!controlloDataRispettoAllaDataProcedimento(udienzaForm.getDataUdienza())) {
			errors.rejectValue("dataUdienza", "error.dataApeFasc");
		}
		if (udienzaForm.getOraInizioUdienza().getId().equalsIgnoreCase(Constants.NO_ITEM_SELECTED) || udienzaForm.getMinutiInizioUdienza().getId().equalsIgnoreCase(Constants.NO_ITEM_SELECTED)) {
			errors.rejectValue("oraInizioUdienza.id", "NotNull");
			errorForm = true;
		}
		if (udienzaForm.getIndirizzoAulaUdienza() == null) {
			errors.rejectValue("indirizzoAulaUdienza", "NotNull");
			errorForm = true;
		}
		if (udienzaForm.getAulaUdienza() == null) {
			errors.rejectValue("aulaUdienza", "NotNull");
			errorForm = true;
		}
		if (!errorForm) {
			if (sogIntSenzaDifensore.size() > 0) {
				map.addAttribute("direttiInteressatiSenzaDifensore", sogIntSenzaDifensore);
				errors.reject(Constants.MESSAGE);
				super.setMessage(Constants.MESSAGE, "table.udienza.sogint.nodifensore", null, false);
			}
		}
	}

	public void validateInserisciUdienzaDecreto(UdienzaForm udienzaForm, BindingResult errors, HttpServletRequest request, ModelMap map) {

        if (udienzaForm.getDataUdienza() == null) {
            errors.rejectValue("dataUdienza", "NotNull");
        }
        if (udienzaForm.getOraInizioUdienza().getId().equalsIgnoreCase(Constants.NO_ITEM_SELECTED) || 
                udienzaForm.getMinutiInizioUdienza().getId().equalsIgnoreCase(Constants.NO_ITEM_SELECTED)) {
            errors.rejectValue("oraInizioUdienza.id", "NotNull");
        }
        if (udienzaForm.getIndirizzoAulaUdienza() == null) {
            errors.rejectValue("indirizzoAulaUdienza", "NotNull");
        }
        if (udienzaForm.getAulaUdienza() == null) {
            errors.rejectValue("aulaUdienza", "NotNull");
        }
    }

	public void validateInviaProposta(AttoForm attoForm, boolean aggravamento, BindingResult errors,boolean restituzioneAttiProcura) {

		if ((attoForm.getDataRegistrazione() == null) && !attoForm.getStatoAtto().getId().equals(String.valueOf(Constants.STATOATTO_REGISTRATO))) {
			if (attoForm.getDataEmissione() == null) {
				errors.rejectValue("dataEmissione", "NotNull");
			}
			if (attoForm.getDataDeposito() == null) {
				errors.rejectValue("dataDeposito", "NotNull");
			}
			if (aggravamento) {
				if (attoForm.getNote() == null) {
					errors.rejectValue("note", "NotNull");
				}
			}
								
		}
	}

	public List<BeneForm> validateListaBeniNoMisPatr(long idUffOwn, long idProcedimento, long idAtto, BindingResult result, HttpServletRequest request) {

		List<BeneForm> lista = attoService.listaBeniNoMisPatr(idUffOwn, idProcedimento, idAtto);
		if ((lista != null) && !lista.isEmpty()) {
			super.setMessage(Constants.MESSAGE, "bene.no.misuarapatrimoniale", null, false);
			result.reject(Constants.MESSAGE);
		}
		return lista;
	}

	public List<BeneForm> validateListaBeniNoTitDir(long idUffOwn, long idProcedimento, long idAtto, BindingResult result, HttpServletRequest request) {

		List<BeneForm> lista = attoService.listaBeniNoTitDir(idUffOwn, idProcedimento, idAtto);
		if ((lista != null) && !lista.isEmpty()) {
			super.setMessage(Constants.MESSAGE, "bene.no.titolodiritto", null, false);
			result.reject(Constants.MESSAGE);
		}
		return lista;
	}
	
	@SuppressWarnings("rawtypes")
	public List<BeneForm> validateListaBeniTitDirUgualiAZero(int idUffOwn, long idProcedimento, long idAtto, BindingResult result) {
		
		
		
		List<TRelBeneAtto> lista = beneService.getTRelBeneAtto(idProcedimento, 0, idUffOwn, idAtto);
		List<BeneForm> listaBeniQuoteZero = new ArrayList<BeneForm>();
		boolean checkValoriAZero = false;
		if ((lista != null) && !lista.isEmpty()) {
			for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
				TRelBeneAtto tRis = (TRelBeneAtto) iterator.next();
				double numQuota = tRis.getNumeQuota()!=null? tRis.getNumeQuota().doubleValue():0;
				int numFrazioneNumeratore = tRis.getNumeFrazioneNum()!=null? tRis.getNumeFrazioneNum().intValue():0;
				int numFrazioneDenominatore = tRis.getNumeFrazioneDen()!=null? tRis.getNumeFrazioneDen().intValue():0;
				
				if(numQuota <= 0 && numFrazioneNumeratore <=0 && numFrazioneDenominatore <= 0){
					checkValoriAZero=true;
					BeneForm beneForm = new BeneForm();
					beneForm.setIdBene(tRis.getId().getCodiKIdBene());
					//List<BeneForm> beniAssociati = beneService.listaBeniPerProcedimento(0,(int) idUffOwn, idProcedimento, beneForm);
					BeneForm beneConQuotaZero = beneService.beneById(idUffOwn, tRis.getId().getCodiKIdBene(), idProcedimento);
					listaBeniQuoteZero.add(beneConQuotaZero);
				}
				
			}
			if(checkValoriAZero){
				super.setMessage(Constants.MESSAGE, "bene.no.titolodiritto.valori.zero", null, false);
				result.reject(Constants.MESSAGE);
			}
		}
		return listaBeniQuoteZero;
	}

	public void validateBeniAssociatiAtto(long idProcedimento, long idAtto, int idUffOwn, BindingResult result) {
		
		List<BeneSoggettoForm> risultatiRicerca = procedimentoService.listaBeniConSoggettiAssociatiAdAttoEProcedimento(idProcedimento, idAtto, idUffOwn);
		if(risultatiRicerca == null || risultatiRicerca.size() == 0){
			super.setMessage(Constants.MESSAGE, "bene.no.atto", null, false);
			result.reject(Constants.MESSAGE);
		}		
	}

	
	public void validateProposta(AttoForm attoForm, ProcedimentoForm procedimento, List<BeneForm> listaBeniNoTitDir, List<BeneForm> listaBeniNoMisPatr, List<SoggettoForm>listaTerziNoTitDir, List<SoggettoForm> soggetti, BindingResult errors, boolean aggravamento, HttpServletRequest request, ModelMap map, int idUfficioUtente) {

		String idStatoProc = null;
		if (procedimento.getStatoProcedimento() != null && procedimento.getStatoProcedimento().getId() != null) {
			idStatoProc = procedimento.getStatoProcedimento().getId();
		}
		long idProcedimento = procedimento.getIdProcedimento();
		List<Message> customMessageList = new ArrayList<Message>();
		List<MagistratoForm> magistratiAssociati = procedimentoService.listaMagistratiPerProcedimento(idUfficioUtente, idProcedimento, 0);
		if ((attoForm.getDataRegistrazione() == null) && (attoForm.getStatoAtto()!=null && !attoForm.getStatoAtto().getId().equals(String.valueOf(Constants.STATOATTO_REGISTRATO)))) {
			if (request.getSession().getAttribute(Constants.TIPO_UTENTE).equals(Constants.TIPO_UTENTE_SMP)) {
				List<Message> errori = magistratoValidation.validaCompletezzaMagistratiProcura(magistratiAssociati);
				if (errori != null) {
					customMessageList.addAll(errori);
				}
				if (idStatoProc == null || !Constants.STATOPROC_ISTRUTTORIA.equals(idStatoProc)) {
					super.addMessageIntoList(customMessageList, Constants.MESSAGE, "error.proposta.statoProcInIstrutt", null);
				}
			} else
				if (request.getSession().getAttribute(Constants.TIPO_UTENTE).equals(Constants.TIPO_UTENTE_SMP_GIU)) {
					if (idStatoProc == null || !(Long.valueOf(Constants.STATOPROC_IN_ACQUISIZ_PROPOSTA)).toString().equals(idStatoProc)) {
						super.addMessageIntoList(customMessageList, Constants.MESSAGE, "error.proposta.statoProcInAcqProp", null);
					}
				}
			if (!aggravamento) {
				if ((listaBeniNoTitDir != null) && !listaBeniNoTitDir.isEmpty()) {
					super.addMessageIntoList(customMessageList, Constants.MESSAGE, "bene.no.titolodiritto", null);
				}
				if ((listaBeniNoMisPatr != null) && !listaBeniNoMisPatr.isEmpty()) {
					super.addMessageIntoList(customMessageList, Constants.MESSAGE, "bene.no.misuarapatrimoniale", null);
				}
				if ((listaTerziNoTitDir != null) && !listaTerziNoTitDir.isEmpty()) {
					super.addMessageIntoList(customMessageList, Constants.MESSAGE, "soggetto.no.titolididiritto", null);
				}
				
				if (request.getSession().getAttribute(Constants.TIPO_UTENTE).equals(Constants.TIPO_UTENTE_SMP)) {
					if (soggetti != null) {
						boolean trov = false;
						for (int i = 0; (i < soggetti.size()) && (trov == false); i++) {
							if (soggetti.get(i).getIdFonte() == null) {
								trov = true;
								super.addMessageIntoList(customMessageList, Constants.MESSAGE, "error.proposta.soggettiNoFonte", null);
							}
						}
					}
				}
			}
			if (soggetti != null) {
				boolean trov = false;
				for (int i = 0; (i < soggetti.size()) && (trov == false); i++) {
					if ((soggetti.get(i).getMisura() == null) || (soggetti.get(i).getMisura().getDescrizioneMisura() == null)) {
						trov = true;
						super.addMessageIntoList(customMessageList, Constants.MESSAGE, "error.proposta.soggettiNoMisura", null);
					}
				}
			}
		}
		super.setMessages(customMessageList, false);
	}

	public void validateSalvaIstanzaSoggDiffidato(AttoForm attoForm, BindingResult errors, HttpServletRequest request) {

		ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute("procedimento");
		Date dataApeFasc = Utility.removeTime(procedimento.getDataApertura());
		Date now = Calendar.getInstance().getTime();
		
		if(attoForm.getDataEmissione() == null){
			errors.rejectValue("dataEmissione", "NotNull");
		}
		
		ValidationUtil.requiredComboForm(attoForm.getModalitaTrasmissione(), "modalitaTrasmissione.id", errors);
		
		if(attoForm.getModalitaTrasmissione().getId()!= null && attoForm.getModalitaTrasmissione().getId().equals("4")){
			if(attoForm.getDescAltroMod() == null || attoForm.getDescAltroMod().equals(" ")){
				errors.rejectValue("descAltroMod", "NotNull");	
			}
		}
		
		if ((attoForm.getDataEmissione() != null) && attoForm.getDataEmissione().after(now)) {
			errors.rejectValue("dataEmissione", "error.dataCorrente");
		}
		
		/* 
		* ISSUE MEV : Risolve il Ticket INC000001831148 Tribunale di Bari: rimosso il controllo
		* Numero MEV : 20BIS
		* Autore    : Ginesti
		* Data      : 23 ottobre 2018
		* 
		*/
		
	//if ((attoForm.getDataEmissione() != null) && attoForm.getDataEmissione().before(dataApeFasc)) {
	//		errors.rejectValue("dataEmissione", "error.dataApeFasc");
	//	}
		
		if ((attoForm.getDataEmissione() != null) && attoForm.getDataEmissione().after(attoForm.getDataDeposito())) {
			errors.rejectValue("dataEmissione", "error.dataDepositoIst");
		}
	//***** FINE INTERVENTO MEV 20BIS *****//	
		
	}

	public void validateConfermaInviaIstanzaSoggDiffidato(AttoForm attoForm, BindingResult errors, HttpServletRequest request) {

		ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute("procedimento");
		Date dataApeFasc = Utility.removeTime(procedimento.getDataApertura());
		Date now = Calendar.getInstance().getTime();
		
		if(attoForm.getDataEmissione() == null){
			errors.rejectValue("dataEmissione", "NotNull");
		}
		if(attoForm.getDataDeposito() == null){
			errors.rejectValue("dataDeposito", "NotNull");
		}
		
		ValidationUtil.requiredComboForm(attoForm.getModalitaTrasmissione(), "modalitaTrasmissione.id", errors);
		
		if(attoForm.getModalitaTrasmissione().getId()!= null && attoForm.getModalitaTrasmissione().getId().equals("4")){
			if(attoForm.getDescAltroMod() == null || attoForm.getDescAltroMod().equals(" ")){
				errors.rejectValue("descAltroMod", "NotNull");	
			}
		}
		
		if ((attoForm.getDataEmissione() != null) && attoForm.getDataEmissione().after(now)) {
			errors.rejectValue("dataEmissione", "error.dataCorrente");
		}
		if ((attoForm.getDataDeposito() != null) && attoForm.getDataDeposito().after(now)) {
			errors.rejectValue("dataDeposito", "error.dataCorrente");
		}
		
		/* 
		* ISSUE MEV : Risolve il Ticket INC000001831148 Tribunale di Bari: rimosso il controllo
		* Numero MEV : 20BIS
		* Autore    : Ginesti
		* Data      : 23 ottobre 2018
		* 
		*/
		//if ((attoForm.getDataEmissione() != null) && attoForm.getDataEmissione().before(dataApeFasc)) {
	//		errors.rejectValue("dataEmissione", "error.dataApeFasc");
	//	}
		
		if ((attoForm.getDataEmissione() != null) && attoForm.getDataEmissione().after(attoForm.getDataDeposito())) {
			errors.rejectValue("dataEmissione", "error.dataDepositoIst");
		}
	//***** FINE INTERVENTO MEV 20BIS *****//	
		/* 
		* ISSUE MEV : Risolve il Ticket INC000001831148 Tribunale di Bari: rimosso il controllo
		* Numero MEV : 20
		* Autore    : Gioggi
		* Data      : 11 giu 2018
		* Branch    : MEV_20
		*/
//		if ((attoForm.getDataDeposito() != null) && attoForm.getDataDeposito().before(dataApeFasc))
//			errors.rejectValue("dataDeposito", "error.dataApeFasc");
		//***** FINE INTERVENTO MEV_20 *****//
	}

	
	public void validateSalvaProposta(AttoForm attoForm, Errors errors, HttpServletRequest request) {

		ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute("procedimento");
		Date dataApeFasc = Utility.removeTime(procedimento.getDataApertura());
		Date now = Calendar.getInstance().getTime();
		if ((attoForm.getDataEmissione() != null) && attoForm.getDataEmissione().after(now)) {
			errors.rejectValue("dataEmissione", "error.dataCorrente");
		}
		if ((attoForm.getDataDeposito() != null) && attoForm.getDataDeposito().after(now)) {
			errors.rejectValue("dataDeposito", "error.dataCorrente");
		}
		if ((attoForm.getDataRegistrazione() != null) && attoForm.getDataRegistrazione().before(dataApeFasc)) {
			errors.rejectValue("dataRegistrazione", "error.dataApeFasc");
		}
		if(request.getSession().getAttribute(Constants.TIPO_UTENTE).equals(Constants.TIPO_UTENTE_SMP)){
			if ((attoForm.getDataEmissione() != null) && attoForm.getDataEmissione().before(dataApeFasc)) {
				errors.rejectValue("dataEmissione", "error.dataApeFasc");
			}
			if ((attoForm.getDataDeposito() != null) && attoForm.getDataDeposito().before(dataApeFasc)) {
				errors.rejectValue("dataDeposito", "error.dataApeFasc");
			}
		}
		if(request.getSession().getAttribute(Constants.TIPO_UTENTE).equals(Constants.TIPO_UTENTE_SMP_GIU)){
			if ((attoForm.getDataDeposito() != null && attoForm.getDataEmissione() != null) && attoForm.getDataEmissione().after(attoForm.getDataDeposito())) {
				errors.rejectValue("dataDeposito", "error.dataEmiss.dataDep");
			}
		}
		if (attoForm.getDataRegistrazione() != null) {
			if ((attoForm.getDestinatario() == null) || (attoForm.getDestinatario().getId() == null) || (Long.valueOf(attoForm.getDestinatario().getId()) < 1)) {
				errors.rejectValue("destinatario.id", "NotNull");
			}
		}
	}

	public void validateSceltaListaStringBeneSoggettoFormData(AttoForm attoForm, BeneSoggettoForm beneSoggettoForm, List<BeneForm> beniSelezionati, List<SoggettoForm> misurePersonali, BindingResult result, HttpServletRequest request) {

        if(beneSoggettoForm.getSoggetto() != null){
            if (beneSoggettoForm.getSoggetto().getDataEsecuzione() == null) {
                super.setMessage(Constants.MESSAGE, "error.soggetto.dataesecuzione", null, false);
                result.reject(Constants.MESSAGE);
                return;
            }
            else{
                Date dataEsec = beneSoggettoForm.getSoggetto().getDataEsecuzione();
                Date sysdate = Calendar.getInstance().getTime();
				ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute("procedimento");
                if(dataEsec.after(sysdate)){
                    super.setMessage(Constants.MESSAGE, "error.soggetto.dataesecuzione.dataodierna", null, false);
                    result.reject(Constants.MESSAGE);
                    return;
				} else if (dataEsec.before(attoForm.getDataDeposito())) {
					super.setMessage(Constants.MESSAGE, "error.soggetto.dataesecuzione.databeforedatadeposito", null, false);
					result.reject(Constants.MESSAGE);
					return;
				}

				if (isSequestro(attoForm)) {
					List<AttoForm> decisori = attoService.getAttiByProcedimento(procedimento.getIdProcedimento(), 3l, Constants.DECRETO_DECISORIO_I_GRADO);
					if (!CollectionUtils.isEmpty(decisori)) {
						for (AttoForm attoForm2 : decisori) {
							if (attoForm2.getDataDeposito() != null && dataEsec.after(attoForm2.getDataDeposito())) {
								super.setMessage(Constants.MESSAGE, "error.soggetto.dataesecuzione.databeforedatadepositodecisorio", null, false);
								result.reject(Constants.MESSAGE);
								return;
							}
						}
					}
				}

            }
        }
        if ((beniSelezionati == null) || beniSelezionati.isEmpty()) {
            super.setMessage(Constants.MESSAGE, "soggetto.patrimoniale.error.select", null, false);
            result.reject(Constants.MESSAGE);
            return;
 		// P.Q. Inizio MEV 2021/19             
        } else {
        	// Per i beni selezionati devo controllare se sono MOBILE - DENARO / FINANZIARI 
        	// Se ENTE = OPERATORE FITTIZIO o null devo segnalare errore!
        	
        	for (BeneForm beneSel : beniSelezionati) {
        		ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute(Constants.ID_PROCEDIMENTO);
        		int idUffOwn = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
        	    BeneForm bene = beneService.beneById(idUffOwn, beneSel.getIdBene(), procedimento.getIdProcedimento());
        	    
        		if (bene.getCategoria() != null && bene.getCategoria().getId() != null) {
        			// Categoria 4 = Mobile - Denaro
        			// Tipo 4 = Finanziario
        			if(bene.getCategoria().getId().equals("6") || bene.getTipologia().getId().equals("4")) {
                	    if ( (bene.getDescOperFinanz() == null ||
                    	      bene.getDescOperFinanz().getDescrizione() == null ||
                    	      bene.getDescOperFinanz().getDescrizione().equals("")) || 
                	    	  bene.getDescOperFinanz().getDescrizione().equals(Constants.EnteFittizio.DESCRIZIONE) ) {
                    	    	
                                super.setMessage(Constants.MESSAGE, "error.esecuzione.sequestro", null, false);
                                result.reject(Constants.MESSAGE);
                                return;        	    	
                	    }        				
        			}
        		}   
        	}
        }
 		// P.Q. Fine MEV 2021/19
        
        for (BeneForm tmp : beniSelezionati) {
            if ((tmp.getIdBene() != null) && (tmp.getListaIdCheckboxString() != null) && !tmp.getListaIdCheckboxString().isEmpty()) {
                return;
            }
        }
        super.setMessage(Constants.MESSAGE, "soggetto.patrimoniale.error.select", null, false);
        result.reject(Constants.MESSAGE);
        return;
	}
	
	private boolean isSequestro(AttoForm attoForm) {
		String categoria = attoForm.getCategoriaAtto().getId();
		return categoria != null && (categoria.equals(Constants.DECRETO_SEQUESTRO_ORDINARIO) || categoria.equals(Constants.DECRETO_SEQUESTRO_URGENTE) || categoria.equals(Constants.DECRETO_SEQUESTRO_ANTICIPATO));
	}

	public boolean validateSceltaListaMisureDefinitivita(String tipoOpDefinitivita, AttoForm attoForm, BeneSoggettoForm beneSoggettoForm, List<BeneForm> misurePatrimoniali, List<SoggettoForm> misurePersonali, BindingResult result,
			HttpServletRequest request) {

        boolean noDataDefInserita = false;
        boolean noMisureSelezionate = false;
        Date dataDefinitivita = null;
        Date dataDeposito = null;
        
        if (beneSoggettoForm.getDataDefinitivitaAtto() == null && !("cancella").equals(tipoOpDefinitivita)) {
            noDataDefInserita = true;
        }
        else dataDefinitivita = beneSoggettoForm.getDataDefinitivitaAtto();
 
        if(attoForm!=null && attoForm.getDataDeposito()!=null){
            dataDeposito = attoForm.getDataDeposito();
        }
        if ( ((misurePatrimoniali == null) || misurePatrimoniali.isEmpty()) && ((misurePersonali==null) || (misurePersonali.isEmpty()))) {
            noMisureSelezionate = true;
        }
        // CONTROLLI SUL TASTO "SALVA" DEFINITIVITA'
        if(tipoOpDefinitivita.equals("salva")){
            //Se non è stata inserita la Data Definitivita --> Errore : Data Definitivita Obbligatoria 
            //(Indipendentemente se ho selezionato o meno qualche Misura)
            if (noDataDefInserita) {
                super.setMessage(Constants.MESSAGE, "error.atto.datadefinitivita", null, false);
                result.reject(Constants.MESSAGE);
                return true;
            }
            //Ho inserito la Data Definitivita
            boolean dataDefNonValida = false;
            dataDefNonValida = validaDataDefinitivita(dataDefinitivita, dataDeposito, result);
            if(!dataDefNonValida){
                //Se la Data Definitivita inserita è valida
                if(noMisureSelezionate){//Se non ho selezionato nessuna Misura
                    //Errore : Selezionare almeno una Misura
                    super.setMessage(Constants.MESSAGE, "error.atto.datadefinitivita.select", null, false);
                    result.reject(Constants.MESSAGE);
                    return true;
                }
                else return false;
            }
            //Se la Data Definitivita inserita non è valida
            else return true;
        }
        if(tipoOpDefinitivita.equals("cancella")){
            if(noMisureSelezionate){//Se non ho selezionato nessuna Misura
                //Errore : Selezionare almeno una Misura
                super.setMessage(Constants.MESSAGE, "error.atto.datadefinitivita.select", null, false);
                result.reject(Constants.MESSAGE);
                return true;
            }        	 
        }
 // CONTROLLI SUL TASTO "CONFERMA E INVIA" DEFINITIVITA'
        /*if(tipoOpDefinitivita.equals("confInvia"))*/{
            if(noMisureSelezionate){//Se non ho selezionato nessuna Misura
                if (noDataDefInserita) {//Se non ho inserito la Data Definitivita
                    return false;
                }
                else{//Se ho inserito la Data Definitivita
                    //Errore : Selezionare almeno una Misura per cui si vuole comunicare la Definitivita
                    super.setMessage(Constants.MESSAGE, "error.atto.datadefinitivita.select", null, false);
                    result.reject(Constants.MESSAGE);
                    return true; 
                }
            }
            else{//Ho selezionato almeno una Misura
                if (noDataDefInserita) {//Se non ho inserito la Data Definitivita --> Errore : Data Definitivita Obbligatoria 
                    super.setMessage(Constants.MESSAGE, "error.atto.datadefinitivita", null, false);
                    result.reject(Constants.MESSAGE);
                    return true;
                }
                else{
                	
                    if(tipoOpDefinitivita.equals("cancella")){
                    	return false;
                    }else{	
	                	 boolean dataDefNonValida = false;
	                     dataDefNonValida = validaDataDefinitivita(dataDefinitivita, dataDeposito, result);
	                     if(!dataDefNonValida){
	                    	 return false;
	                     }
	                    //Se la Data Definitivita inserita non è valida
	                    else return true;
	                	//Se ho inserito la Data Definitività devo passare prima dalla SP del Salva
	                   // return false;
                    }
                }
            }
        }
        
        

    }
    
	private boolean validaDataDefinitivita(Date dataDefinitivita, Date dataDeposito, BindingResult result) {

	    Date sysdate = Calendar.getInstance().getTime();
        if(dataDefinitivita.after(sysdate)){
            super.setMessage(Constants.MESSAGE, "error.atto.datadefinitivita.dataodierna", null, false);
            result.reject(Constants.MESSAGE);
            return true;
        }
        if(dataDeposito!=null){
            if(dataDefinitivita.before(dataDeposito)){
                super.setMessage(Constants.MESSAGE, "error.atto.datadefinitivita.datadeposito", null, false);
                result.reject(Constants.MESSAGE);
                return true;
            }
        }
        return false;
    }

    public void validateSelezionaSoggetto(AttoForm attoForm, Errors errors, HttpServletRequest request) {

		if ((attoForm.getIdentificativoSoggetto() == 0) && (attoForm.getVersioneSoggetto() == 0)) {
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
		}
	}

	public void validateDecisorioPrevenzione(String descAtto,Long idprocedimento,long idUffOwn,AttoForm attoForm, String idStatoProc, List<BeneForm> listaBeniNoTitDir, List<BeneForm> listaBeniNoMisPatr, List<SoggettoForm> soggetti, BindingResult errors, HttpServletRequest request, ModelMap map,boolean confermaInvia) {

		List<Message> customMessageList = new ArrayList<Message>();
		if ((attoForm.getDataRegistrazione() == null) && !attoForm.getStatoAtto().getId().equals(String.valueOf(Constants.STATOATTO_REGISTRATO))) {
			if ((listaBeniNoTitDir != null) && !listaBeniNoTitDir.isEmpty()) {
				super.addMessageIntoList(customMessageList, Constants.MESSAGE, "bene.no.titolodiritto", null);
			}
			if ((listaBeniNoMisPatr != null) && !listaBeniNoMisPatr.isEmpty()) {
				super.addMessageIntoList(customMessageList, Constants.MESSAGE, "bene.no.misuarapatrimoniale", null);
			}
			if (request.getSession().getAttribute(Constants.TIPO_UTENTE).equals(Constants.TIPO_UTENTE_SMP)) {
				if (soggetti != null) {
					boolean trov = false;
					for (int i = 0; (i < soggetti.size()) && (trov == false); i++) {
						if (soggetti.get(i).getIdFonte() == null) {
							trov = true;
							super.addMessageIntoList(customMessageList, Constants.MESSAGE, "error.proposta.soggettiNoFonte", null);
						}
					}
				}
			}
			if (soggetti != null) {
				boolean trov = false;
				for (int i = 0; (i < soggetti.size()) && (trov == false); i++) {
					if ((soggetti.get(i).getMisura() == null) || (soggetti.get(i).getMisura().getDescrizioneMisura() == null)) {
						trov = true;
						super.addMessageIntoList(customMessageList, Constants.MESSAGE, "error.proposta.soggettiNoMisura", null);
					}
				}
			}
			
	/*if (confermaInvia){		
		List soggettiSenzaDecisione = misuraService.getSogIntSenzaDecisorio(idprocedimento, attoForm.getCodAtto(), idUffOwn, "1,2,3",descAtto);
			if (soggettiSenzaDecisione != null && soggettiSenzaDecisione.size() > 0) {
				map.addAttribute("soggettiSenzaDecisione", soggettiSenzaDecisione);						
				super.addMessageIntoList(customMessageList, Constants.MESSAGE, "atto.decreto.decisorioIGrado.soggSenzaDecisioni.error", null);

			}
			List beniSenzaDecisione = misuraService.getBeniSenzaDecisorio(idprocedimento, attoForm.getCodAtto(), idUffOwn, descAtto);
			if (beniSenzaDecisione != null && beniSenzaDecisione.size() > 0) {
				map.addAttribute("beniSenzaDecisione", beniSenzaDecisione);
				super.addMessageIntoList(customMessageList, Constants.MESSAGE, "atto.decreto.decisorioIGrado.beniSenzaDecisioni.error", null);

			}	
	}
	*/
					
			
		}
		super.setMessages(customMessageList, false);
	}
	
	
	@SuppressWarnings("rawtypes")
	public void validateConfermaInviaDecisorio(String descAtto,Long idprocedimento,long idUffOwn,AttoForm attoForm, String idStatoProc, BindingResult errors, HttpServletRequest request, ModelMap map) {

		List<Message> customMessageList = new ArrayList<Message>();
		
		ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute("procedimento");
		String tipoMisura;
		if (procedimento.getTipoProcedimento().getId().equals("15")){
			tipoMisura = "25";	
		}
		else{
			tipoMisura = "1,2,3";
		}
		
		List soggettiSenzaDecisione = misuraService.getSogIntSenzaDecisorio(idprocedimento, attoForm.getCodAtto(), idUffOwn, tipoMisura,descAtto);
			if (soggettiSenzaDecisione != null && soggettiSenzaDecisione.size() > 0) {
				map.addAttribute("soggettiSenzaDecisione", soggettiSenzaDecisione);						
				super.addMessageIntoList(customMessageList, Constants.MESSAGE, "atto.decreto.decisorioIGrado.soggSenzaDecisioni.error", null);

			}
			List beniSenzaDecisione = misuraService.getBeniSenzaDecisorio(idprocedimento, attoForm.getCodAtto(), idUffOwn, descAtto);
			if (beniSenzaDecisione != null && beniSenzaDecisione.size() > 0) {
				map.addAttribute("beniSenzaDecisione", beniSenzaDecisione);
				super.addMessageIntoList(customMessageList, Constants.MESSAGE, "atto.decreto.decisorioIGrado.beniSenzaDecisioni.error", null);

			}

			if ( attoForm.getAllegato().getFileData() !=null 
					&& 
				(attoForm.getAllegato().getFileData().getFileItem().getName() == null || "".equals(attoForm.getAllegato().getFileData().getFileItem().getName())))
				{
					if( !(errors.getFieldError("allegato.fileData")!=null&&errors.getFieldError("allegato.fileData").getCode().equals("NotNull")) )
					errors.rejectValue("allegato.fileData", "NotNull");
				}
			
//			if(attoForm.getAllegato().getFileData() == null || attoForm.getAllegato().getFileData().getSize() <= 0){
//				 errors.rejectValue("allegato.fileData", "NotNull");
//			}
	
			if(attoForm.getDataEmissione() == null){
				if( !(errors.getFieldError("dataEmissione")!=null&&errors.getFieldError("dataEmissione").getCode().equals("NotNull")) )
				errors.rejectValue("dataEmissione", "NotNull");
			}

			if(attoForm.getDataDeposito() == null){
				if( !(errors.getFieldError("dataDeposito")!=null&&errors.getFieldError("dataDeposito").getCode().equals("NotNull")) )
				 errors.rejectValue("dataDeposito", "NotNull");
			}

			
		
		super.setMessages(customMessageList, false);
	}
	
	
	@SuppressWarnings("rawtypes")
	public void validateConfermaInviaDecisorioIIIGrado(String descAtto,Long idprocedimento,long idUffOwn,AttoForm attoForm, String idStatoProc, BindingResult errors, HttpServletRequest request, ModelMap map) {

		List<Message> customMessageList = new ArrayList<Message>();	
			
		ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute("procedimento");
		String tipoMisura;
		if (procedimento.getTipoProcedimento().getId().equals("15")){
			tipoMisura = "25";	
		}
		else{
			tipoMisura = "1,2,3";
		}
		
		List soggettiSenzaDecisione = misuraService.getSogIntSenzaDecisorio(idprocedimento, attoForm.getCodAtto(), idUffOwn, tipoMisura, descAtto);
			if (soggettiSenzaDecisione != null && soggettiSenzaDecisione.size() > 0) {
				map.addAttribute("soggettiSenzaDecisione", soggettiSenzaDecisione);						
				super.addMessageIntoList(customMessageList, Constants.MESSAGE, "atto.decreto.decisorioIGrado.soggSenzaDecisioni.error", null);
				errors.reject(Constants.MESSAGE);

			}

		
		super.setMessages(customMessageList, false);
	}	

	public void validateConfInviaDecIIIGradoErroreMateriale(String descAtto,Long idprocedimento,long idUffOwn,AttoForm attoForm, BindingResult errors, HttpServletRequest request, ModelMap map) {

		List<Message> customMessageList = new ArrayList<Message>();
		
					
		boolean SoggettoNoDecisono = attoService.getSogNoDecErroreMat(idprocedimento, attoForm.getCodAtto(), idUffOwn,descAtto);
		if (SoggettoNoDecisono) {
				map.addAttribute("soggettiSenzaDecisione", null);						
			super.addMessageIntoList(customMessageList, Constants.MESSAGE, "errore.materiale.decisorioIGrado.soggSenzaDecisioni.error", null);
				errors.reject(Constants.MESSAGE);

			}
	
		
		super.setMessages(customMessageList, false);
	}	
	
	public void validateDecretoAntRinvUdienzaCampiObbl(UdienzaForm udienzaForm, BindingResult errors, HttpServletRequest request, ModelMap map){
	 
	    if (udienzaForm.getDataUdienza() == null)
            errors.rejectValue("dataUdienza", "NotNull");
        if (udienzaForm.getOraInizioUdienza().getId().equalsIgnoreCase(Constants.NO_ITEM_SELECTED) || udienzaForm.getMinutiInizioUdienza().getId().equalsIgnoreCase(Constants.NO_ITEM_SELECTED))
            errors.rejectValue("oraInizioUdienza.id", "NotNull");
        if (udienzaForm.getIndirizzoAulaUdienza() == null)
            errors.rejectValue("indirizzoAulaUdienza", "NotNull");
        if (udienzaForm.getAulaUdienza() == null) 
            errors.rejectValue("aulaUdienza", "NotNull");
	}
	
	public void validateDecretoAnticipazioneUdienza(UdienzaForm udienzaForm, BindingResult errors, HttpServletRequest request, ModelMap map) {

		List<Message> customMessageList = new ArrayList<Message>();
		
		if (udienzaForm.getDataRegistrazione() != null) {
			validateAttoDate(udienzaForm,  errors,  request);
		}
		
		if(udienzaForm.getUdienzaPrecedente().getDataUdienza().before(udienzaForm.getDataUdienza())) {
			super.addMessageIntoList(customMessageList, Constants.MESSAGE, "atto.udienza.decretoAnticipazione.ora.past", null);
			errors.reject(Constants.MESSAGE);
		} else if(udienzaForm.getUdienzaPrecedente().getDataUdienza().equals(udienzaForm.getDataUdienza())) {
			if(udienzaForm.getOraInizioUdienza().getId().compareTo(udienzaForm.getUdienzaPrecedente().getOraInizioUdienza().getId()) >= 0) {
				super.addMessageIntoList(customMessageList, Constants.MESSAGE, "atto.udienza.decretoAnticipazione.ora.past", null);
				errors.reject(Constants.MESSAGE);
			}
		}		
		super.setMessages(customMessageList, false);
	}
	
	public void validateDecretoRinvioUdienza(UdienzaForm udienzaForm, BindingResult errors, HttpServletRequest request, ModelMap map) {

		List<Message> customMessageList = new ArrayList<Message>();
		
		if (udienzaForm.getDataRegistrazione() != null) {
			validateAttoDate(udienzaForm,  errors,  request);
		}
		
		if(udienzaForm.getUdienzaPrecedente().getDataUdienza().after(udienzaForm.getDataUdienza())) {
			super.addMessageIntoList(customMessageList, Constants.MESSAGE, "atto.udienza.decretoRinvio.ora.future", null);
			errors.reject(Constants.MESSAGE);
		} else if(udienzaForm.getUdienzaPrecedente().getDataUdienza().equals(udienzaForm.getDataUdienza())) {
			if(udienzaForm.getOraInizioUdienza().getId().compareTo(udienzaForm.getUdienzaPrecedente().getOraInizioUdienza().getId()) <= 0) {
				super.addMessageIntoList(customMessageList, Constants.MESSAGE, "atto.udienza.decretoRinvio.ora.future", null);
				errors.reject(Constants.MESSAGE);
			}
		}		
		super.setMessages(customMessageList, false);
	}
	
	public void validateSelezionaDecreto(AttoForm attoForm, BindingResult errors, HttpServletRequest request, ModelMap map) {
		if(attoForm.getCodAtto() < 0) {
			super.setMessage(Constants.MESSAGE,"error.list.selection.unique",null,false);
			errors.reject(Constants.MESSAGE);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void validateConfInviaConvalidaSequestro(BindingResult errors, HttpServletRequest request, ModelMap map) {
		
		List<Message> customMessageList = new ArrayList<Message>();
		List<BeneSoggettoForm> listaBeneSoggetto = (List<BeneSoggettoForm>) map.get("risultatiRicerca");
		
		if(Utility.isDefinito(listaBeneSoggetto)) {
			Iterator<BeneSoggettoForm> iterBeneSogg = listaBeneSoggetto.iterator();
			while(iterBeneSogg.hasNext()) {
				BeneSoggettoForm beneSoggetto = iterBeneSogg.next();
				List<SoggettoForm> soggetti = beneSoggetto.getSoggetti();
				if(Utility.isDefinito(soggetti)) {
					Iterator<SoggettoForm> iterSoggetto = soggetti.iterator();
					while(iterSoggetto.hasNext()) {
						SoggettoForm soggetto = iterSoggetto.next();
						if(soggetto.getMisura() == null
							|| soggetto.getMisura().getTipoMisura() == null
							|| soggetto.getMisura().getTipoMisura().getId() == null
							|| Long.parseLong(soggetto.getMisura().getTipoMisura().getId()) == (Constants.TIPO_MISURA_SEQUESTRO_ANTICIPATO)
							|| Long.parseLong(soggetto.getMisura().getTipoMisura().getId()) == (Constants.TIPO_MISURA_SEQUESTRO_URGENTE)) 
						{
							super.addMessageIntoList(customMessageList, Constants.MESSAGE, "atto.decreto.convalidaSequestro.notAll", null);
							errors.reject(Constants.MESSAGE);
						}
					}
				}
			}
		}
		super.setMessages(customMessageList, false, errors);
	}
	
	public void validateAttoGenerico(AttoForm attoForm, BindingResult errors, HttpServletRequest request, ModelMap map) {
		if(attoForm.getFlagSpedRic().getId().equalsIgnoreCase(Constants.NO_ITEM_SELECTED)) {
			errors.rejectValue("flagSpedRic.id", "NotNull");
		}
		
		if (attoForm.getTipoAtto()!=null && "14".equals(attoForm.getTipoAtto().getId())) {
			// Se l'atto è generico non valido la data emissione
		} else {
			if (attoForm.getDataEmissione() == null) {
				errors.rejectValue("dataEmissione", "NotNull");
			}
		}
		if (attoForm.getFonteAttoGenerico().getId().equalsIgnoreCase(Constants.NO_ITEM_SELECTED)) {
			errors.rejectValue("fonteAttoGenerico.id", "NotNull");
		}
		if (!attoForm.getFonteAttoGenerico().getId().equalsIgnoreCase(Constants.NO_ITEM_SELECTED) && !attoForm.getFonteAttoGenerico().getId().equalsIgnoreCase(Constants.FONTE_ALTRO)) {
			if (attoForm.getOrgano().getId().equalsIgnoreCase(Constants.NO_ITEM_SELECTED)) {
				errors.rejectValue("organo.id", "NotNull");
			}
			if (attoForm.getProvinciaOrgano().getId().equalsIgnoreCase(Constants.NO_ITEM_SELECTED)) {
				errors.rejectValue("provinciaOrgano.id", "NotNull");
			}
			if (attoForm.getComuneOrgano().getId().equalsIgnoreCase(Constants.NO_ITEM_SELECTED)) {
				errors.rejectValue("comuneOrgano.id", "NotNull");
			}
			if (attoForm.getIndirizzoOrgano().getId().equalsIgnoreCase(Constants.NO_ITEM_SELECTED)) {
				errors.rejectValue("indirizzoOrgano.id", "NotNull");
			}
		}
	}
	
	
	public void validateOrdinanazaArt15Antimafia(AttoForm attoForm, ProcedimentoForm procedimentoForm,BindingResult errors, HttpServletRequest request, ModelMap map) {

		
		if (attoForm.getDataDeposito() == null) {
			errors.rejectValue("dataDeposito", "NotNull");
		}
		
		
		Date dataIscrizione = Utility.removeTime(procedimentoForm.getDataApertura());
		
		if(attoForm.getDataDeposito() != null)
		{
			if (attoForm.getDataDeposito().before(dataIscrizione )) {
		        super.setMessage(Constants.MESSAGE, "antimafia.data.deposito.maggiore.uguale.fascicolo", null, false);
		        errors.reject(Constants.MESSAGE);	
			}				
		}		
				
		
		if (attoForm.getFonteAttoGenerico().getId().equalsIgnoreCase(Constants.NO_ITEM_SELECTED)) {
			errors.rejectValue("fonteAttoGenerico.id", "NotNull");
		}
		if (!attoForm.getFonteAttoGenerico().getId().equalsIgnoreCase(Constants.NO_ITEM_SELECTED)) {
			if (attoForm.getOrgano().getId().equalsIgnoreCase(Constants.NO_ITEM_SELECTED)) {
				errors.rejectValue("organo.id", "NotNull");
			}
			if (attoForm.getProvinciaOrgano().getId().equalsIgnoreCase(Constants.NO_ITEM_SELECTED)) {
				errors.rejectValue("provinciaOrgano.id", "NotNull");
			}
			if (attoForm.getComuneOrgano().getId().equalsIgnoreCase(Constants.NO_ITEM_SELECTED)) {
				errors.rejectValue("comuneOrgano.id", "NotNull");
			}
			if (attoForm.getIndirizzoOrgano().getId().equalsIgnoreCase(Constants.NO_ITEM_SELECTED)) {
				errors.rejectValue("indirizzoOrgano.id", "NotNull");
			}
		}
		
		if (attoForm.getDataCessazioneMisura() == null) {
			errors.rejectValue("dataCessazioneMisura", "NotNull");
		}
		
		if (attoForm.getDataCessazioneMisura() != null ){
			if (attoForm.getDataCessazioneMisura().before(dataIscrizione)) {
		        super.setMessage(Constants.MESSAGE, "antimafia.data.cessazione.maggiore.uguale.fascicolo", null, false);
		        errors.reject(Constants.MESSAGE);	
			}			
		}		
				
		
	}
	

	public void validateIntercettazioniTelefoniche(AttoForm attoForm,ProcedimentoForm procedimentoForm, BindingResult errors, HttpServletRequest request, ModelMap map) {

		
		if (attoForm.getDataDeposito() == null) {
			errors.rejectValue("dataDeposito", "NotNull");
		}
		
		
		Date dataIscrizione = Utility.removeTime(procedimentoForm.getDataApertura());
		
		if(attoForm.getDataDeposito() != null)
		{
			if (dataIscrizione.after(attoForm.getDataDeposito())) {
		        super.setMessage(Constants.MESSAGE, "antimafia.data.deposito.maggiore.uguale.fascicolo", null, false);
		        errors.reject(Constants.MESSAGE);	
			}				
		}		
		
		if (attoForm.getFonteAttoGenerico().getId().equalsIgnoreCase(Constants.NO_ITEM_SELECTED)) {
			errors.rejectValue("fonteAttoGenerico.id", "NotNull");
		}
		if (!attoForm.getFonteAttoGenerico().getId().equalsIgnoreCase(Constants.NO_ITEM_SELECTED)) {
			if (attoForm.getOrgano().getId().equalsIgnoreCase(Constants.NO_ITEM_SELECTED)) {
				errors.rejectValue("organo.id", "NotNull");
			}
			if (attoForm.getProvinciaOrgano().getId().equalsIgnoreCase(Constants.NO_ITEM_SELECTED)) {
				errors.rejectValue("provinciaOrgano.id", "NotNull");
			}
			if (attoForm.getComuneOrgano().getId().equalsIgnoreCase(Constants.NO_ITEM_SELECTED)) {
				errors.rejectValue("comuneOrgano.id", "NotNull");
			}
			if (attoForm.getIndirizzoOrgano().getId().equalsIgnoreCase(Constants.NO_ITEM_SELECTED)) {
				errors.rejectValue("indirizzoOrgano.id", "NotNull");
			}
		}
		
		if (attoForm.getMagistrato() == null || 
				(attoForm.getMagistrato() != null && attoForm.getMagistrato().getId() == null)||
				(attoForm.getMagistrato() != null && attoForm.getMagistrato().getId() != null && attoForm.getMagistrato().getId().equals("-1"))) {
			errors.rejectValue("magistrato.id", "NotNull");
		}			
	}
		
	
	public void validateOrdinanazaArt14Antimafia(AttoForm attoForm,ProcedimentoForm procedimentoForm, BindingResult errors, HttpServletRequest request, ModelMap map) {

		
		if (attoForm.getDataEmissione() == null) {
			errors.rejectValue("dataEmissione", "NotNull");
		}
		
		if (attoForm.getDataDeposito() == null) {
			errors.rejectValue("dataDeposito", "NotNull");
		}
		
		Date dataIscrizione = Utility.removeTime(procedimentoForm.getDataApertura());

		
		if(attoForm.getDataEmissione() != null && attoForm.getDataDeposito() != null)
		{
			if (attoForm.getDataDeposito().before(dataIscrizione )) {
		        super.setMessage(Constants.MESSAGE, "antimafia.data.deposito.maggiore.uguale.fascicolo", null, false);
		        errors.reject(Constants.MESSAGE);	
			}
			
			if (attoForm.getDataEmissione().before(dataIscrizione )) {
		        super.setMessage(Constants.MESSAGE, "antimafia.data.emissione.maggiore.uguale.fascicolo", null, false);
		        errors.reject(Constants.MESSAGE);	
			}
			
			if (attoForm.getDataDeposito().before(attoForm.getDataEmissione() )) {
		        super.setMessage(Constants.MESSAGE, "antimafia.data.deposito.maggiore.uguale", null, false);
		        errors.reject(Constants.MESSAGE);	
			}
			
		}
		if (attoForm.getDataCessazioneMisura() == null && attoForm.getDataRipresaMisura() == null) {
	        super.setMessage(Constants.MESSAGE, "error.antimafia.date", null, false);
	        errors.reject(Constants.MESSAGE);
		}
		
		if (attoForm.getDataCessazioneMisura() != null && attoForm.getDataRipresaMisura() != null) {
	        super.setMessage(Constants.MESSAGE, "error.antimafia.date.noentrambe", null, false);
	        errors.reject(Constants.MESSAGE);
		}
		
		if (attoForm.getDataCessazioneMisura() != null ){
			if (attoForm.getDataCessazioneMisura().before(dataIscrizione )) {
		        super.setMessage(Constants.MESSAGE, "antimafia.data.cessazione.maggiore.uguale.fascicolo", null, false);
		        errors.reject(Constants.MESSAGE);	
			}			
		}
		
		if (attoForm.getDataRipresaMisura() != null ){
			if (attoForm.getDataRipresaMisura().before(dataIscrizione)) {
		        super.setMessage(Constants.MESSAGE, "antimafia.data.ripresa.maggiore.uguale.fascicolo", null, false);
		        errors.reject(Constants.MESSAGE);	
			}			
		}
		
		
		if (attoForm.getMagistrato() == null || 
				(attoForm.getMagistrato() != null && attoForm.getMagistrato().getId() == null)||
				(attoForm.getMagistrato() != null && attoForm.getMagistrato().getId() != null && attoForm.getMagistrato().getId().equals("-1"))) {
			errors.rejectValue("magistrato.id", "NotNull");
		}		
     
	}	

//	public boolean isAllegatoObbligatorioPerCatAtto(long idCatAtto) {
//		System.out.println("");
////		recupera su T_TP_CAT_ATTO il valore della colonna allegato_obbligatorio (da aggiungere)
//		TTpCatAtto result = commonDao.getCategoriaAtto(idCatAtto);
//		
//		if (result != null && result.getFlgAllegObbl() != null && 'S'== result.getFlgAllegObbl()) {
//			return true;
//		}else {
//			return false;
//		}
//	}
	
	
	
	@SuppressWarnings("unchecked")
	public void validateEsecuzioneStralcio(StralcioForm stralcioForm, BindingResult errors, HttpServletRequest request, String vProvenienza) {
	
		String dataStralcio = stralcioForm.getDataStralcio();
		if (dataStralcio != null && !"".equalsIgnoreCase(dataStralcio)) {
			ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute("procedimento");
			Date dataAperturaProc = procedimento.getDataApertura();
			Date datadaIns = Utility.stringToDate(dataStralcio, "dd/MM/yyyy");
			if (datadaIns.before(Utility.removeTime(dataAperturaProc)))
				errors.rejectValue("dataStralcio", "error.dataApeFasc");
			else if (datadaIns.after(Calendar.getInstance().getTime()))
				errors.rejectValue("dataStralcio", "error.dataCorrente");
			
			/* 
			 * ISSUE MEV : Sitmp Istanza di liquidazione (concordate a seguito del p.11 PLO II/10)
			 * 	- punto a) --> aggiunta controllo per constatare esistenza fascicolo di "istanza di liquidazione" precedentemente inserito
			 * 	in stato "in lavorazione" per non permettere l'esecuzione dello stralcio
			 * Numero MEV : 1
			 * Autore    : Gioggi
			 * Data      : 26 giu 2018
			 * Branch    : MEV_1
			 */
			int idUffOwn = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
			List<ProcedimentoForm> listaProcedimenti = procedimentoService
					.getListaProcedimentiRelProcProc(procedimento.getIdProcedimento(), idUffOwn, idUffOwn, "");
			boolean existIstLiq = false;
			if (listaProcedimenti != null && !listaProcedimenti.isEmpty()) {
				Iterator<ProcedimentoForm> i = listaProcedimenti.iterator();
				while (i.hasNext()) {
					ProcedimentoForm pf = i.next();
					if (String.valueOf(Constants.TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM).equals(pf.getTipoProcedimento().getId())
							&& Constants.STATOPROC_IN_ACQUISIZ_ISTANZA.equals(pf.getStatoProcedimento().getId())) {
						existIstLiq = true;
						break;
					}
				}
			}
			if (existIstLiq) {
				List<Message> customMessageList = new ArrayList<Message>();
				errors.reject(Constants.MESSAGE);
				super.addMessageIntoList(customMessageList, "error.esiste.fascicolo.istanzaLiquidazione.inAcquisizioneIstanza.collegato");
				super.setMessages(customMessageList, false);
			}
			//***** FINE INTERVENTO MEV_1 *****//
		}else{
			errors.rejectValue("dataStralcio", "NotNull");
		}

		if (vProvenienza=="Conferma"){
			if(!Utility.isComboFormSelected(stralcioForm.getMagistrato())){
				errors.rejectValue("magistrato.id", "NotNull");
			}
		}
	}
	
	
	public void validateEsecuzioneRiunione(RiunioneForm riunioneForm, BindingResult errors, HttpServletRequest request, String vProvenienza) {
		
		String dataRiunione = riunioneForm.getDataRiunione();
		if(dataRiunione != null && !"".equalsIgnoreCase(dataRiunione)){
			
			ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute("procedimento");
			Date dataAperturaProc = procedimento.getDataApertura();
			
			Date datadaIns = Utility.stringToDate(dataRiunione, "dd/MM/yyyy");
			if(datadaIns.before(Utility.removeTime(dataAperturaProc))){
				errors.rejectValue("dataRiunione", "error.dataApeFasc");
			}else if(datadaIns.after(Calendar.getInstance().getTime())){
				errors.rejectValue("dataRiunione", "error.dataCorrente");
			}
		}else{
			errors.rejectValue("dataRiunione", "NotNull");
		}
		
		if(vProvenienza=="Conferma"){
			if(!Utility.isComboFormSelected(riunioneForm.getMagistrato())){
				errors.rejectValue("magistrato", "NotNull");
			}
		}
	}
	
	/**
	 * 
	 * @param dataInserire - la data che deve essere controllata rispetto alla data di inizo del procedimento
	 * @return false se è minore, true se è maggiore o uguale 
	 */
	private boolean controlloDataRispettoAllaDataProcedimento(Date dataInserire) {
				
			ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute("procedimento");
			Date dataAperturaProc = procedimento.getDataApertura();
			if(dataInserire.before(Utility.removeTime(dataAperturaProc))){
				return false;
			}
			return true;
	}
	
	
	  public boolean validateSceltaListaMisureRevocaDefinitivita(String tipoOpDefinitivita, AttoForm attoForm, BeneSoggettoForm beneSoggettoForm, List<BeneForm> misurePatrimoniali, List<SoggettoForm> misurePersonali, BindingResult result, HttpServletRequest request) {

	       
	        boolean noMisureSelezionate = false;
	      
	        
	       
	        if ( ((misurePatrimoniali == null) || misurePatrimoniali.isEmpty()) && ((misurePersonali==null) || (misurePersonali.isEmpty()))) {
	            noMisureSelezionate = true;
	        }
	            if(noMisureSelezionate){//Se non ho selezionato nessuna Misura
	                        //Errore : Selezionare almeno una Misura
	                        super.setMessage(Constants.MESSAGE, "error.atto.datadefinitivita.select", null, false);
	                        result.reject(Constants.MESSAGE);
	                        return true;
	                    }
	                
	            
	       return false;
	        

	    }	

	  public void validateSalvaCorrezioneErroreMateriale(AttoForm attoForm, Errors errors, HttpServletRequest request) {

			ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute("procedimento");
			Date dataApeFasc = Utility.removeTime(procedimento.getDataApertura());
			Date now = Calendar.getInstance().getTime();
//			if (attoForm.getDataEmissione() == null) {
//				errors.rejectValue("dataEmissione", "NotNull");
//			}
			
			if ((attoForm.getDataEmissione() != null) && attoForm.getDataEmissione().after(now)) {
				errors.rejectValue("dataEmissione", "error.dataCorrente");
			}

			if(request.getSession().getAttribute(Constants.TIPO_UTENTE).equals(Constants.TIPO_UTENTE_SMP)){
				if ((attoForm.getDataEmissione() != null) && attoForm.getDataEmissione().before(dataApeFasc)) {
					errors.rejectValue("dataEmissione", "error.dataApeFasc");
				}
				
			}
		
		}
	  public void validateSalvaIstanzaMisuraPersonale(AttoForm attoForm, Errors errors, HttpServletRequest request) {

			ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute("procedimento");
			Date dataApeFasc = Utility.removeTime(procedimento.getDataApertura());
			Date now = Calendar.getInstance().getTime();
		
			if ((attoForm.getDataRicezione() != null) && attoForm.getDataRicezione().after(now)) {
				errors.rejectValue("dataEmissione", "error.dataCorrente");
			}
			if ((attoForm.getDataDeposito() != null) && attoForm.getDataDeposito().after(now)) {
				errors.rejectValue("dataDeposito", "error.dataCorrente");
			}
			if ((attoForm.getDataRegistrazione() != null) && attoForm.getDataRegistrazione().before(dataApeFasc)) {
				errors.rejectValue("dataRegistrazione", "error.dataApeFasc");
			}
		
			/*	if(request.getSession().getAttribute(Constants.TIPO_UTENTE).equals(Constants.TIPO_UTENTE_SMP)){
				if ((attoForm.getDataEmissione() != null) && attoForm.getDataEmissione().before(dataApeFasc)) {
					errors.rejectValue("dataEmissione", "error.dataApeFasc");
				}
				if ((attoForm.getDataDeposito() != null) && attoForm.getDataDeposito().before(dataApeFasc)) {
					errors.rejectValue("dataDeposito", "error.dataApeFasc");
				}
			}
			*/
			
		}  
	  
	  public void validateSalvaIstanzaModificaPersonale(AttoForm attoForm, Errors errors, HttpServletRequest request) {

			ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute("procedimento");
			Date dataApeFasc = Utility.removeTime(procedimento.getDataApertura());
			Date now = Calendar.getInstance().getTime();
			
			//Segnalazione EMS-57
			if(attoForm.getCategoriaAtto().getId().equals(Constants.ISTANZA_CAUZIONE) && attoForm.getDataDeposito() == null)
				errors.rejectValue("dataDeposito", "NotNull");
			if ((attoForm.getDataDeposito() != null) && attoForm.getDataDeposito().after(now)) {
				errors.rejectValue("dataDeposito", "error.dataCorrente");
			}
			
			if ((attoForm.getDataRegistrazione() != null) && attoForm.getDataRegistrazione().before(dataApeFasc)) {
				errors.rejectValue("dataRegistrazione", "error.dataApeFasc");
			}
			if(request.getSession().getAttribute(Constants.TIPO_UTENTE).equals(Constants.TIPO_UTENTE_SMP)){
				
				if ((attoForm.getDataDeposito() != null) && attoForm.getDataDeposito().before(dataApeFasc)) {
					errors.rejectValue("dataDeposito", "error.dataApeFasc");
				}
			}
			if (attoForm.getNote() == null) {
				errors.rejectValue("note", "NotNull");
			}
			
			// Modifiche CS
			if (attoForm.getCategoriaAtto().getId().equals(Constants.ISTANZA_CORREZIONE_ERRORI_MATERIALI)) {
				
				if ("F".equals(attoForm.getSoggetto().getTipoSoggetto())) {
				
					if (attoForm.getSoggetto().getCognome() == null) {
						errors.rejectValue("soggetto.cognome", "NotNull");
					}
					
					if (attoForm.getSoggetto().getNome() == null) {
						errors.rejectValue("soggetto.nome", "NotNull");
					}
					
					if ((attoForm.getSoggetto().getDataNascita() == null) ) {
						errors.rejectValue("soggetto.dataNascita", "NotNull");
					}
					
					if ((attoForm.getSoggetto().getDataNascita() != null) && attoForm.getSoggetto().getDataNascita().after(now)) {
						errors.rejectValue("soggetto.dataNascita", "error.dataCorrente");
					}
					
					if (attoForm.getSoggetto().getComuneNascita().getDescrizione() == null) {
						errors.rejectValue("soggetto.comuneNascita.descrizione", "NotNull");
					}
					
					
					if (attoForm.getSoggetto().getCodiceFiscale() != null && StringUtils.isNotBlank(attoForm.getSoggetto().getCodiceFiscale())) {
                        
					   String message = Utility.ControllaCodiceFiscale(attoForm.getSoggetto().getCodiceFiscale());
                      if(StringUtils.isNotBlank(message)){
                              errors.rejectValue("soggetto.codiceFiscale", message);
                      }
					}
					
				} else {
					
					if (attoForm.getSoggetto().getPartitaIva() == null) {
						errors.rejectValue("soggetto.partitaIva", "NotNull");
					}
					
					if (attoForm.getSoggetto().getRagioneSociale() == null) {
						errors.rejectValue("soggetto.ragioneSociale", "NotNull");
					}
					
					if (attoForm.getSoggetto().getComuneSedeLegale().getDescrizione() == null) {
						errors.rejectValue("soggetto.comuneSedeLegale.descrizione", "NotNull");
					}
					
				}
					
			}

			
			impugnazioneValidation.validateIstanza(attoForm.getImpugnazione(), errors, request);
			
		}
	  
	  public void validateRegistraIstanzaModificaPersonale(AttoForm attoForm, BindingResult errors) {

			if ((attoForm.getDataRegistrazione() == null) && !attoForm.getStatoAtto().getId().equals(String.valueOf(Constants.STATOATTO_REGISTRATO))) {
				
				if (attoForm.getDataDeposito() == null) {
					errors.rejectValue("dataDeposito", "NotNull");
				}
				
					
				
									
			}
		}
	  
	  
	  public void validateSalvaIstanzaProcura(AttoForm attoForm, Errors errors, HttpServletRequest request) {

			ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute("procedimento");
			Date dataApeFasc = Utility.removeTime(procedimento.getDataApertura());
			Date now = Calendar.getInstance().getTime();
			
			if ((attoForm.getDataDeposito() != null) && attoForm.getDataDeposito().after(now)) {
				errors.rejectValue("dataDeposito", "error.dataCorrente");
			}
			
			if ((attoForm.getDataRegistrazione() != null) && attoForm.getDataRegistrazione().before(dataApeFasc)) {
				errors.rejectValue("dataRegistrazione", "error.dataApeFasc");
			}
			if(request.getSession().getAttribute(Constants.TIPO_UTENTE).equals(Constants.TIPO_UTENTE_SMP)){
				
				if ((attoForm.getDataDeposito() != null) && attoForm.getDataDeposito().before(dataApeFasc)) {
					errors.rejectValue("dataDeposito", "error.dataApeFasc");
				}
			}
			if (attoForm.getNote() == null) {
				errors.rejectValue("note", "NotNull");
			}
			
		
		}
	  
	  public void validateRegistraIstanzaProcura(AttoForm attoForm, BindingResult errors) {

			if ((attoForm.getDataRegistrazione() == null) && !attoForm.getStatoAtto().getId().equals(String.valueOf(Constants.STATOATTO_REGISTRATO))) {
				
				if (attoForm.getDataDeposito() == null) {
					errors.rejectValue("dataDeposito", "NotNull");
				}								
			}
		}

	  
	  
	  
	  public void validateSalvaIstanzaFascicoloGestione(AttoForm attoForm, BindingResult errors) {

			if (attoForm.getDataRicezione() == null) {
				errors.rejectValue("dataRicezione", "NotNull");
			}
			if (attoForm.getNote() == null) {
				errors.rejectValue("note", "NotNull");
			}
			if (attoForm.getTipoProponente() == null || attoForm.getTipoProponente().getId().equals(Constants.NO_ITEM_SELECTED)) {
				errors.rejectValue("tipoProponente.id", "NotNull");
			}
			if (attoForm.getTipoProponente() != null && !attoForm.getTipoProponente().getId().equals(Constants.NO_ITEM_SELECTED)) {
				if(attoForm.getTipoProponente().getId().equals("AMMINISTRATORE")) {
					if(attoForm.getAmministratore()==null || attoForm.getAmministratore().getId().equals(Constants.NO_ITEM_SELECTED)) {
						errors.rejectValue("amministratore.id", "NotNull");
					}  
				} else if(!attoForm.getTipoProponente().getId().equals("AMMINISTRATORE")) {
					if(attoForm.getAltroOrgano()==null || attoForm.getAltroOrgano().trim().length()==0) {
						errors.rejectValue("altroOrgano", "NotNull");
					} 		
					if(attoForm.getCognProponente()==null || attoForm.getCognProponente().trim().length()==0) {
						errors.rejectValue("cognProponente", "NotNull");
					} 					
					if(attoForm.getNomeProponente()==null || attoForm.getNomeProponente().trim().length()==0) {
						errors.rejectValue("nomeProponente", "NotNull");
					} 					
				}
			}	
			if(attoForm.getCategoriaAtto().getId().equals(Constants.ISTANZA_CONTO_GESTIONE) || attoForm.getCategoriaAtto().getId().equals(Constants.RELAZIONE_AMMINISTRATORE)) {
				if(attoForm.getPeriodoRifDal()==null ) {
					errors.rejectValue("periodoRifDal", "NotNull");
				} else {
					if(attoForm.getPeriodoRifDal().after(new Date())) {
						errors.rejectValue("periodoRifDal", "atto.error.dataSuperioreOdierna");
					}
				}
				if(attoForm.getPeriodoRifAl()==null) {
					errors.rejectValue("periodoRifAl", "NotNull");
				} else {
					if(attoForm.getPeriodoRifAl().after(new Date())) {
						errors.rejectValue("periodoRifAl", "atto.error.dataSuperioreOdierna");
					}
				}

				if(attoForm.getPeriodoRifDal()!=null && attoForm.getPeriodoRifAl()!=null) {
					if(attoForm.getPeriodoRifDal().after(attoForm.getPeriodoRifAl()) || attoForm.getPeriodoRifDal().equals(attoForm.getPeriodoRifAl())) {
						errors.rejectValue("periodoRifDal", "atto.error.dataDalSuperioreOUgualeAl");
					}
				}
			}

			
	  }
	  
	public void validateProvvedimentoFascicoloGestione(AttoForm attoForm, BindingResult errors, HttpServletRequest request, ModelMap map, String catAtto, String params) {
	
		if(params!=null) {
			
			if (attoForm.getEsitoProvv() != null && attoForm.getEsitoProvv().equals("S") &&
					(attoForm.getDescEsitoProvv() == null || attoForm.getDescEsitoProvv().trim().length()==0) ) {
				errors.rejectValue("descEsitoProvv", "NotNull");
			}

			if(catAtto.equals(Constants.PROVVEDIMENTO_SU_ISTANZA_DELLE_PARTI_E_AMMINISTRATORE)) {

				if (attoForm.getDescEsitoProvv() == null) {
					errors.rejectValue("descEsitoProvv", "NotNull");
				}
				
				//lunghezza dei campi textarea
				if (attoForm.getDescEsitoProvv() != null && attoForm.getDescEsitoProvv().length()>=200) {
					errors.rejectValue("descEsitoProvv", "typeMismatch");
				}
				
				
			} else if(catAtto.equals(Constants.PROVVEDIMENTO_CONTO_GESTIONE)) {

				if (attoForm.getEsitoProvv() == null || attoForm.getEsitoProvv().equals(Constants.NO_ITEM_SELECTED) ) {
					errors.rejectValue("esitoProvv", "NotNull");
				}
				
				if (attoForm.getFlgImp() != null && attoForm.getFlgImp().equals("S") &&
						(attoForm.getDescEsitoImp() == null || attoForm.getDescEsitoImp().trim().length()==0)
					) {
						errors.rejectValue("descEsitoImp", "NotNull");
				}
				
				if (attoForm.getFlgCas() != null && attoForm.getFlgCas().equals("S") &&
						(attoForm.getDescEsitoCas() == null || attoForm.getDescEsitoCas().trim().length()==0)
					) {
						errors.rejectValue("descEsitoCas", "NotNull");
				}

				//lunghezza dei campi textarea
				if (attoForm.getDescEsitoProvv() != null && attoForm.getDescEsitoProvv().length()>=200) {
					errors.rejectValue("descEsitoProvv", "typeMismatch");
				}
				
				if (attoForm.getDescEsitoImp() != null && attoForm.getDescEsitoImp().length()>=200) {
					errors.rejectValue("descEsitoImp", "typeMismatch");
				}
				
				if (attoForm.getDescEsitoCas() != null && attoForm.getDescEsitoCas().length()>=200) {
					errors.rejectValue("descEsitoCas", "typeMismatch");
				}				

				
				
			}

			if(params.equals("Conferma")) {
				if (attoForm.getDataEmissione() == null) {
					errors.rejectValue("dataEmissione", "NotNull");
				}
				if (attoForm.getDataDeposito() == null) {
					errors.rejectValue("dataDeposito", "NotNull");
				}
			}
			
		}
	}
	
	public void validateProvvedimentoNominaAmministratori(AttoForm attoForm, BindingResult errors, HttpServletRequest request, ModelMap map, String catAtto, String params) {
		
		if(params!=null) {

			if(params.equals("Conferma")) {
				if (attoForm.getDataEmissione() == null) {
					errors.rejectValue("dataEmissione", "NotNull");
				}
				if (attoForm.getDataDeposito() == null) {
					errors.rejectValue("dataDeposito", "NotNull");
				}
			}
			
		}
	}	
	
	public void validateProvvedimentoRevocaAmministratori(AttoForm attoForm, BindingResult errors, HttpServletRequest request, ModelMap map, String catAtto, String params) {
		
		if(params!=null) {

			if(params.equals("Conferma")) {
				if (attoForm.getDataEmissione() == null) {
					errors.rejectValue("dataEmissione", "NotNull");
				}
				if (attoForm.getDataDeposito() == null) {
					errors.rejectValue("dataDeposito", "NotNull");
				}
			}
			
		}
	}		

	//Modificato 10-03-2016 per segnalazione CO40
	public void validateSoggettiIntervenientiNonAssociati(Long idProcedimento, Integer idUfficio, BindingResult errors, HttpServletRequest request, ModelMap map) {
		if (attoService.existsIntervenientiNonAssociati(idProcedimento, idUfficio)) {
			String soggettiStr = procedimentoService.intervenientiNonAssociati(idProcedimento, idUfficio);
			List<SoggettoForm> soggettiSenzaDiretti = new ArrayList<SoggettoForm>();
			if(soggettiStr.length() > 0)
			{
				String[] listaSoggetti = soggettiStr.split(",");
				for(int i=0; i<listaSoggetti.length;i++)
				{
					SoggettoForm soggettoForm = new SoggettoForm();
					soggettoForm.setDescSoggetto(listaSoggetti[i]);
					soggettiSenzaDiretti.add(soggettoForm);
				}
				map.addAttribute("soggettiSenzaDiretti", soggettiSenzaDiretti);
			}
			
			errors.reject("atto.decreto.decisorioIGrado.intervenientiNonAssociati");
			/*List listaIntervenienti = attoService.intervenientiNonAssociati(idProcedimento, idUfficio);
			if(listaIntervenienti != null && !listaIntervenienti.isEmpty())
			{
				List<SoggettoForm> soggettiSenzaDiretti = new ArrayList<SoggettoForm>();
				for(int i=0; i<listaIntervenienti.size();i++)
				{
					SoggettoForm soggettoForm = new SoggettoForm();
					soggettoForm.setDescSoggetto(""+listaIntervenienti.get(i));
					soggettiSenzaDiretti.add(soggettoForm);
				}
				SoggettoForm soggettoForm;
				map.addAttribute("soggettiSenzaDiretti", soggettiSenzaDiretti);
				errors.reject("atto.decreto.decisorioIGrado.intervenientiNonAssociati");
			}*/
		}
	}

	private static Character beneCheckato = new Character('R');

	public boolean validateSelezioneBeneRegistrazioneIstanzaTerzoInterveniente(List<SoggettoStralcioForm> listaSoggettoStralcioForm, BindingResult result) {
		boolean error = true;
		if (listaSoggettoStralcioForm !=null) {
			for (SoggettoStralcioForm soggettoStralcioForm : listaSoggettoStralcioForm) {
				List<BeneStralcioForm> listaBeneStralcioForm = soggettoStralcioForm.getListaBeneStralcioForm();
				for (BeneStralcioForm beneStralcioForm : listaBeneStralcioForm) {
					if (beneCheckato.equals(beneStralcioForm.getFlgValidoBeneRicorso())) {
						error = false;
						break;
					}
				}
			}
		}
		return error;
	}

}
