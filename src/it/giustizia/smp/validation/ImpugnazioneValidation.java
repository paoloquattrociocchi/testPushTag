package it.giustizia.smp.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.giustizia.smp.controller.procedimento.AttoController;
import it.giustizia.smp.dao.CommonDao;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.ImpugnazioneForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.SoggettoStralcioForm;
import it.giustizia.smp.service.AttoService;
import it.giustizia.smp.service.BeneService;
import it.giustizia.smp.service.CommonService;
import it.giustizia.smp.service.MisuraService;
import it.giustizia.smp.service.ProcedimentoService;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Message;
import it.giustizia.smp.utility.Utility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("impugnazioneValidation")
public class ImpugnazioneValidation extends BaseValidation implements Validator {

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
	private CommonDao         commonDao;

	private Pattern pattern;
	private Matcher matcher;

    private String TIME24HOURS_PATTERN = 
               "([01]?[0-9]|2[0-3]):[0-5][0-9]";

	

	public boolean supports(Class<?> clazz) {

		return AttoController.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {

	}
    
	
	/**
	 * validateInvioRicorsoInAppello
	 * @param attoForm
	 * @param errors
	 * @param request
	 */
	public void validateInvioRicorsoInAppello(AttoForm attoForm, Errors errors, HttpServletRequest request){
		
		if (attoForm.getDataTrasmCda() == null) {
			errors.rejectValue("dataTrasmCda", "NotNull");
		}		
		
	}
	
	/**
	 * validateRicorsoAppello
	 * @param impugnazioneForm
	 * @param errors
	 * @param request
	 */
	public void validateRicorsoAppello(ImpugnazioneForm impugnazioneForm, Errors errors, HttpServletRequest request) 
	{
   
		ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute("procedimento");
		Date dataApeFasc = Utility.removeTime(procedimento.getDataApertura());
		Date now = Calendar.getInstance().getTime();
		
		List<Message> customMessageList = new ArrayList<Message>();
		
		long idUffOwn = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		Date dataImpugnazione = Utility.removeTime(impugnazioneForm.getDataImpugnazione());

	
		if (impugnazioneForm.getDataImpugnazione() == null) {
			errors.rejectValue("impugnazione.dataImpugnazione", "NotNull");
		}
		if ((impugnazioneForm.getDataImpugnazione() != null) && impugnazioneForm.getDataImpugnazione().after(now)) {
			errors.rejectValue("impugnazione.dataImpugnazione", "error.dataCorrente");
		}
		if ((impugnazioneForm.getDataImpugnazione() != null) && impugnazioneForm.getDataImpugnazione().before(dataApeFasc)) {
			errors.rejectValue("impugnazione.dataImpugnazione", "error.dataApeFasc");
		}
	
	/*	if (impugnazioneForm.getOrarioImpugnazione() == null) {
			errors.rejectValue("impugnazione.orarioImpugnazione", "NotNull");
		}
		else {
		*/
			 if ((impugnazioneForm.getOrarioImpugnazione() != null) && (!controllaOra(impugnazioneForm.getOrarioImpugnazione())))errors.rejectValue("impugnazione.orarioImpugnazione", "typeMismatch");				
	//	}
		//se non seleziona nulla a livello di tipo deposito
		if (impugnazioneForm.getTipoDeposito().getId() != null && impugnazioneForm.getTipoDeposito().getId().equals("-1")) {
			errors.rejectValue("impugnazione.tipoDeposito", "NotNull");
		}
		else{
		//se tipo Deposito non è vuoto
			if (impugnazioneForm.getTipoDeposito().getId() != null && impugnazioneForm.getTipoDeposito().getId().equals("A")) {
			if (impugnazioneForm.getAltriUffici().getId() != null && impugnazioneForm.getAltriUffici().getId().equals("-1")) {
			errors.rejectValue("impugnazione.altriUffici", "NotNull");
			}
			}
			if (impugnazioneForm.getTipoDeposito().getId() != null && impugnazioneForm.getTipoDeposito().getId().equals("I")) {
				if (impugnazioneForm.getIstitutoDiDetenzione().getId() != null && impugnazioneForm.getIstitutoDiDetenzione().getId().equals("-1")) {
				errors.rejectValue("impugnazione.istitutoDiDetenzione", "NotNull");
				}
			}
			
		}
		
		if (impugnazioneForm.getTipoModalitaPervenimento().getId() != null && impugnazioneForm.getTipoModalitaPervenimento().getId().equals("-1")) 
		{
			errors.rejectValue("impugnazione.tipoModalitaPervenimento", "NotNull");
		}
		else {
			if (impugnazioneForm.getTipoModalitaPervenimento().getId() != null && impugnazioneForm.getTipoModalitaPervenimento().getId().equals("4")) 
			{
				if (impugnazioneForm.getModalitaPervenimentoDesc()== null) {
					errors.rejectValue("impugnazione.modalitaPervenimentoDesc", "NotNull");
				}
			
		   }
		}
		
		if(impugnazioneForm.getTipoModalitaPervenimento() != null && !"3".equalsIgnoreCase(impugnazioneForm.getTipoModalitaPervenimento().getId())){
				if (impugnazioneForm.getDataSpedizione() == null) {
					errors.rejectValue("impugnazione.dataSpedizione", "NotNull");
				}
				if ((impugnazioneForm.getDataSpedizione() != null) && impugnazioneForm.getDataSpedizione().after(now)) {
					errors.rejectValue("impugnazione.dataSpedizione", "error.dataCorrente");
				}
				if ((impugnazioneForm.getDataSpedizione() != null) && impugnazioneForm.getDataSpedizione().before(dataApeFasc)) {
					errors.rejectValue("impugnazione.dataSpedizione", "error.dataApeFasc");
				}
		}
		else if(impugnazioneForm.getTipoModalitaPervenimento() != null && "3".equalsIgnoreCase(impugnazioneForm.getTipoModalitaPervenimento().getId())){
			if (impugnazioneForm.getDataSpedizione() != null) {
				errors.rejectValue("impugnazione.dataSpedizione", "error.dataSpedizioneAMano");
			}
		}
		
	
		if (impugnazioneForm.getTipoModalitaPervenimento().getId() != null && impugnazioneForm.getDataPervenimento() == null) {
			errors.rejectValue("impugnazione.dataPervenimento", "NotNull");
		}
		if (impugnazioneForm.getTipoModalitaPervenimento().getId() != null && (impugnazioneForm.getDataPervenimento() != null) && impugnazioneForm.getDataPervenimento().after(now)) {
			errors.rejectValue("impugnazione.dataPervenimento", "error.dataCorrente");
		}
		if (impugnazioneForm.getTipoModalitaPervenimento().getId() != null && (impugnazioneForm.getDataPervenimento() != null) && impugnazioneForm.getDataPervenimento().before(dataApeFasc)) {
			errors.rejectValue("impugnazione.dataPervenimento", "error.dataApeFasc");
		}
		
	/*	if (impugnazioneForm.getTipoModalitaPervenimento().getId() != null  && impugnazioneForm.getOrarioPervenimento() == null) {
			errors.rejectValue("impugnazione.orarioPervenimento", "NotNull");
		}
		else { */
			 if(impugnazioneForm.getTipoModalitaPervenimento().getId() != null  && impugnazioneForm.getOrarioPervenimento()!= null && !controllaOra(impugnazioneForm.getOrarioPervenimento()))errors.rejectValue("impugnazione.orarioPervenimento", "typeMismatch");				
	//	}
			
		
		if (impugnazioneForm.getParte() != null){
			if ( impugnazioneForm.getParte().getId() != null && impugnazioneForm.getParte().getId().equals("-1")) {
				errors.rejectValue("impugnazione.parte", "NotNull");
			}
			else {
				
			  if (impugnazioneForm.getParte().getId() != null && impugnazioneForm.getParte().getId().equals("D")) {
				  if (impugnazioneForm.getIdsDirettiInteressati() == null) {
					  errors.rejectValue("impugnazione.direttiInteressati", "NotNull");
			   }
			  } 
			  
			  if (impugnazioneForm.getParte().getId() != null && impugnazioneForm.getParte().getId().equals("T")) {
				  if (impugnazioneForm.getIdsSoggettiTitolari() == null) {
					  errors.rejectValue("impugnazione.soggettiTitolari", "NotNull");
			   }
			  }  
			  
			  if (impugnazioneForm.getParte().getId() != null && impugnazioneForm.getParte().getId().equals("A")) {
				  if (impugnazioneForm.getAvvocatiDifensori().getId() != null && impugnazioneForm.getAvvocatiDifensori().getId().equals("-1")) {
					  errors.rejectValue("impugnazione.avvocatiDifensori", "NotNull");
			    }
				  if (impugnazioneForm.getIdsSoggettiDifensori() == null ) {
					  errors.rejectValue("impugnazione.soggettiDifensori", "NotNull");
			   }	  
			 
			  }  
	  
			 }
		}
		if (impugnazioneForm.getNome() == null) {
			errors.rejectValue("impugnazione.nome", "NotNull");
		}
		if (impugnazioneForm.getCognome() == null) {
			errors.rejectValue("impugnazione.cognome", "NotNull");
		}
      }
	
	
	public void validateIstanza(ImpugnazioneForm impugnazioneForm, Errors errors, HttpServletRequest request) 
	{
   
		ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute("procedimento");
		Date dataApeFasc = Utility.removeTime(procedimento.getDataApertura());
		Date now = Calendar.getInstance().getTime();
		
		List<Message> customMessageList = new ArrayList<Message>();
		
		long idUffOwn = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		Date dataImpugnazione = Utility.removeTime(impugnazioneForm.getDataImpugnazione());

	
		
		//se non seleziona nulla a livello di tipo deposito
		if (impugnazioneForm.getTipoDeposito().getId() != null && impugnazioneForm.getTipoDeposito().getId().equals("-1")) {
			errors.rejectValue("impugnazione.tipoDeposito", "NotNull");
		}
		else{
		//se tipo Deposito non è vuoto
			if (impugnazioneForm.getTipoDeposito().getId() != null && impugnazioneForm.getTipoDeposito().getId().equals("A")) {
			if (impugnazioneForm.getAltriUffici().getId() != null && impugnazioneForm.getAltriUffici().getId().equals("-1")) {
			errors.rejectValue("impugnazione.altriUffici", "NotNull");
			}
			}
			if (impugnazioneForm.getTipoDeposito().getId() != null && impugnazioneForm.getTipoDeposito().getId().equals("I")) {
				if (impugnazioneForm.getIstitutoDiDetenzione().getId() != null && impugnazioneForm.getIstitutoDiDetenzione().getId().equals("-1")) {
				errors.rejectValue("impugnazione.istitutoDiDetenzione", "NotNull");
				}
			}
			
		}
		
		if (impugnazioneForm.getTipoModalitaPervenimento().getId() != null && impugnazioneForm.getTipoModalitaPervenimento().getId().equals("-1")) 
		{
			errors.rejectValue("impugnazione.tipoModalitaPervenimento", "NotNull");
		}
		else {
			if (impugnazioneForm.getTipoModalitaPervenimento().getId() != null && impugnazioneForm.getTipoModalitaPervenimento().getId().equals("4")) 
			{
				if (impugnazioneForm.getModalitaPervenimentoDesc()== null) {
					errors.rejectValue("impugnazione.modalitaPervenimentoDesc", "NotNull");
				}
			
		   }
		}
		
		if(impugnazioneForm.getTipoModalitaPervenimento() != null && !"3".equalsIgnoreCase(impugnazioneForm.getTipoModalitaPervenimento().getId())){
				if (impugnazioneForm.getDataSpedizione() == null) {
					errors.rejectValue("impugnazione.dataSpedizione", "NotNull");
				}
				if ((impugnazioneForm.getDataSpedizione() != null) && impugnazioneForm.getDataSpedizione().after(dataApeFasc)) {
					errors.rejectValue("impugnazione.dataSpedizione", "error.dataApeFascSucc");
				}
				
				if ((impugnazioneForm.getDataSpedizione() != null) && impugnazioneForm.getDataSpedizione().after(now)) {
					errors.rejectValue("impugnazione.dataSpedizione", "error.dataCorrente");
				}
		}
		else if(impugnazioneForm.getTipoModalitaPervenimento() != null && "3".equalsIgnoreCase(impugnazioneForm.getTipoModalitaPervenimento().getId())){
			if (impugnazioneForm.getDataSpedizione() != null) {
				errors.rejectValue("impugnazione.dataSpedizione", "error.dataSpedizioneAMano");
			}
		}
		
	
		if (impugnazioneForm.getTipoModalitaPervenimento().getId() != null && impugnazioneForm.getDataPervenimento() == null) {
			errors.rejectValue("impugnazione.dataPervenimento", "NotNull");
		}
		if (impugnazioneForm.getTipoModalitaPervenimento().getId() != null && (impugnazioneForm.getDataPervenimento() != null) && impugnazioneForm.getDataPervenimento().after(dataApeFasc)) {
			errors.rejectValue("impugnazione.dataPervenimento", "error.dataApeFascSucc");
		}
		if (impugnazioneForm.getTipoModalitaPervenimento().getId() != null && (impugnazioneForm.getDataPervenimento() != null) && impugnazioneForm.getDataPervenimento().after(now)) {
			errors.rejectValue("impugnazione.dataPervenimento", "error.dataCorrente");
		}
		
		if ( (impugnazioneForm.getDataPervenimento() != null) && impugnazioneForm.getDataSpedizione() != null && impugnazioneForm.getDataPervenimento().before(impugnazioneForm.getDataSpedizione()) ) {
			errors.rejectValue("impugnazione.dataPervenimento", "error.dataPervenimentoSpedizione");
		}
		
		
	//	if (impugnazioneForm.getTipoModalitaPervenimento().getId() != null  && impugnazioneForm.getOrarioPervenimento() == null) {
	//		errors.rejectValue("impugnazione.orarioPervenimento", "NotNull");
	//	}
	//	else {
			 if(impugnazioneForm.getTipoModalitaPervenimento().getId() != null  && impugnazioneForm.getOrarioPervenimento() != null && !controllaOra(impugnazioneForm.getOrarioPervenimento()))errors.rejectValue("impugnazione.orarioPervenimento", "typeMismatch");				
	//	}
			
		
		if (impugnazioneForm.getParte() != null){
			if ( impugnazioneForm.getParte().getId() != null && impugnazioneForm.getParte().getId().equals("-1")) {
				errors.rejectValue("impugnazione.parte", "NotNull");
			}
			else {
				
			  if (impugnazioneForm.getParte().getId() != null && impugnazioneForm.getParte().getId().equals("D")) {
				  if (impugnazioneForm.getIdsDirettiInteressati() == null) {
					  errors.rejectValue("impugnazione.direttiInteressati", "NotNull");
			   }
			  } 
			  
			  if (impugnazioneForm.getParte().getId() != null && impugnazioneForm.getParte().getId().equals("T")) {
				  if (impugnazioneForm.getIdsSoggettiTitolari() == null) {
					  errors.rejectValue("impugnazione.soggettiTitolari", "NotNull");
			   }
			  }  
			  
			  if (impugnazioneForm.getParte().getId() != null && (impugnazioneForm.getParte().getId().equals("A") || impugnazioneForm.getParte().getId().equals("E"))) {
				  if (impugnazioneForm.getAvvocatiDifensori().getId() != null && impugnazioneForm.getAvvocatiDifensori().getId().equals("-1")) {
					  errors.rejectValue("impugnazione.avvocatiDifensori", "NotNull");
			    }
				  if (impugnazioneForm.getIdsSoggettiDifensori() == null ) {
					  errors.rejectValue("impugnazione.soggettiDifensori", "NotNull");
			   }	  
			 
			  }  
	  
			 }
		}
		if (impugnazioneForm.getNome() == null) {
			errors.rejectValue("impugnazione.nome", "NotNull");
		}
		if (impugnazioneForm.getCognome() == null) {
			errors.rejectValue("impugnazione.cognome", "NotNull");
		}
      }
	/**
	 * validateRicorsoCassazione
	 * @param impugnazioneForm
	 * @param errors
	 * @param request
	 */
	
	public void validateRicorsoCassazione(ImpugnazioneForm impugnazioneForm, Errors errors, HttpServletRequest request ) 
	{
   
		ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute("procedimento");
		Date dataApeFasc = Utility.removeTime(procedimento.getDataApertura());
		Date now = Calendar.getInstance().getTime();
		
		if (impugnazioneForm.getDataImpugnazione() == null) {
			errors.rejectValue("impugnazione.dataImpugnazione", "NotNull");
		}
		if ((impugnazioneForm.getDataImpugnazione() != null) && impugnazioneForm.getDataImpugnazione().after(now)) {
			errors.rejectValue("impugnazione.dataImpugnazione", "error.dataCorrente");
		}
		if ((impugnazioneForm.getDataImpugnazione() != null) && impugnazioneForm.getDataImpugnazione().before(dataApeFasc)) {
			errors.rejectValue("impugnazione.dataImpugnazione", "error.dataApeFasc");
		}
	
		if(impugnazioneForm.getOrarioImpugnazione() != null && !controllaOra(impugnazioneForm.getOrarioImpugnazione()))
			errors.rejectValue("impugnazione.orarioImpugnazione", "typeMismatch");				
		//se non seleziona nulla a livello di tipo deposito
		if (impugnazioneForm.getTipoDeposito().getId() != null && impugnazioneForm.getTipoDeposito().getId().equals("-1")) {
			errors.rejectValue("impugnazione.tipoDeposito", "NotNull");
		}
		else{
		//se tipo Deposito non è vuoto
			if (impugnazioneForm.getTipoDeposito().getId() != null && impugnazioneForm.getTipoDeposito().getId().equals("A")) {
			if (impugnazioneForm.getAltriUffici().getId() != null && impugnazioneForm.getAltriUffici().getId().equals("-1")) {
			errors.rejectValue("impugnazione.altriUffici", "NotNull");
			}
			}
			if (impugnazioneForm.getTipoDeposito().getId() != null && impugnazioneForm.getTipoDeposito().getId().equals("I")) {
				if (impugnazioneForm.getIstitutoDiDetenzione().getId() != null && impugnazioneForm.getIstitutoDiDetenzione().getId().equals("-1")) {
				errors.rejectValue("impugnazione.istitutoDiDetenzione", "NotNull");
				}
			}
			
		}
		
		if (impugnazioneForm.getTipoModalitaPervenimento().getId() != null && impugnazioneForm.getTipoModalitaPervenimento().getId().equals("-1")) 
		{
			errors.rejectValue("impugnazione.tipoModalitaPervenimento", "NotNull");
		}
		else {
			if (impugnazioneForm.getTipoModalitaPervenimento().getId() != null && impugnazioneForm.getTipoModalitaPervenimento().getId().equals("4")) 
			{
				if (impugnazioneForm.getModalitaPervenimentoDesc()== null) {
					errors.rejectValue("impugnazione.modalitaPervenimentoDesc", "NotNull");
				}
			
		   }
		}
		
		if(impugnazioneForm.getTipoModalitaPervenimento() != null && !"3".equalsIgnoreCase(impugnazioneForm.getTipoModalitaPervenimento().getId())){
				if (impugnazioneForm.getDataSpedizione() == null) {
					errors.rejectValue("impugnazione.dataSpedizione", "NotNull");
				}
				if ((impugnazioneForm.getDataSpedizione() != null) && impugnazioneForm.getDataSpedizione().after(now)) {
					errors.rejectValue("impugnazione.dataSpedizione", "error.dataCorrente");
				}
				if ((impugnazioneForm.getDataSpedizione() != null) && impugnazioneForm.getDataSpedizione().before(dataApeFasc)) {
					errors.rejectValue("impugnazione.dataSpedizione", "error.dataApeFasc");
				}
		}
		else if(impugnazioneForm.getTipoModalitaPervenimento() != null && "3".equalsIgnoreCase(impugnazioneForm.getTipoModalitaPervenimento().getId())){
			if (impugnazioneForm.getDataSpedizione() != null) {
				errors.rejectValue("impugnazione.dataSpedizione", "error.dataSpedizioneAMano");
			}
		}
		
	
		if (impugnazioneForm.getTipoModalitaPervenimento().getId() != null && impugnazioneForm.getDataPervenimento() == null) {
			errors.rejectValue("impugnazione.dataPervenimento", "NotNull");
		}
		if (impugnazioneForm.getTipoModalitaPervenimento().getId() != null && (impugnazioneForm.getDataPervenimento() != null) && impugnazioneForm.getDataPervenimento().after(now)) {
			errors.rejectValue("impugnazione.dataPervenimento", "error.dataCorrente");
		}
		if (impugnazioneForm.getTipoModalitaPervenimento().getId() != null && (impugnazioneForm.getDataPervenimento() != null) && impugnazioneForm.getDataPervenimento().before(dataApeFasc)) {
			errors.rejectValue("impugnazione.dataPervenimento", "error.dataApeFasc");
		}
		
		if(impugnazioneForm.getTipoModalitaPervenimento().getId() != null  && impugnazioneForm.getOrarioPervenimento() != null && !controllaOra(impugnazioneForm.getOrarioPervenimento())){
			errors.rejectValue("impugnazione.orarioPervenimento", "typeMismatch");				
		}
		if (impugnazioneForm.getParte() != null){
			if ( impugnazioneForm.getParte().getId() != null && impugnazioneForm.getParte().getId().equals("-1")) {
				errors.rejectValue("impugnazione.parte", "NotNull");
			}
			else {
				
			  if (impugnazioneForm.getParte().getId() != null && impugnazioneForm.getParte().getId().equals("D")) {
				  if (impugnazioneForm.getIdsDirettiInteressati() == null) {
					  errors.rejectValue("impugnazione.direttiInteressati", "NotNull");
			   }
			  } 
			  
			  if (impugnazioneForm.getParte().getId() != null && impugnazioneForm.getParte().getId().equals("T")) {
				  if (impugnazioneForm.getIdsSoggettiTitolari()== null ) {
					  errors.rejectValue("impugnazione.soggettiTitolari", "NotNull");
			   }
			  }  
			  
			  if (impugnazioneForm.getParte().getId() != null && (impugnazioneForm.getParte().getId().equals("A")||impugnazioneForm.getParte().getId().equals("E"))) {
				  if (impugnazioneForm.getAvvocatiDifensori().getId() != null && impugnazioneForm.getAvvocatiDifensori().getId().equals("-1")) {
					  errors.rejectValue("impugnazione.avvocatiDifensori", "NotNull");
			    }
				  if (impugnazioneForm.getIdsSoggettiDifensori() == null) {
					  errors.rejectValue("impugnazione.soggettiDifensori", "NotNull");
			   }
				 
			  }  
	  
			 }
		}
		if (impugnazioneForm.getNome() == null) {
			errors.rejectValue("impugnazione.nome", "NotNull");
		}
		if (impugnazioneForm.getCognome() == null) {
			errors.rejectValue("impugnazione.cognome", "NotNull");
		}
	}
	public boolean controllaOra(String time){ 
	    Pattern pattern;
		Matcher matcher;
	    String TIME24HOURS_PATTERN = 
	               "([01]?[0-9]|2[0-3]):[0-5][0-9]";
	    pattern = Pattern.compile(TIME24HOURS_PATTERN);
		matcher = pattern.matcher(time);		
	return matcher.matches();	
		
	}
	
}