package it.giustizia.smp.validation;

import it.giustizia.smp.form.NotificheEComunicazioniForm;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
/**
 * Classe per la validazione dei campi filtro per le statistiche
 * @author m.prisco
 *
 */
@Component("notificheEComunicazioniValidation")
public class NotificheEComunicazioniValidation extends BaseValidation implements Validator {
	
	/***
	 * Validazione della jsp filtriStatistiche.
	 * @param theForm
	 * @param errors
	 * @param request
	*/ 
	public void validateFiltri(NotificheEComunicazioniForm theForm, BindingResult errors, HttpServletRequest request) {
		if (theForm.getFiltroDataAl()!=null && theForm.getFiltroDataDal()!=null) {
			if (theForm.getFiltroDataAl().before(theForm.getFiltroDataDal())) {
				errors.rejectValue("filtroDataAl", "NumberFormat");
			}
		}  
	}
	 
	public void validate(Object target, Errors errors) {
	}
	
	
	public boolean supports(Class<?> clazz) {
		return NotificheEComunicazioniValidation.class.isAssignableFrom(clazz);
	}

}
