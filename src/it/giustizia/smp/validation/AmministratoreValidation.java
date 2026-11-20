package it.giustizia.smp.validation;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import it.giustizia.smp.controller.procedimento.AmministratoreController;
import it.giustizia.smp.form.AmministratoreForm;
import it.giustizia.smp.form.CoadiutoreForm;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.DateUtils;
import it.giustizia.smp.utility.ValidationUtil;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("amministratoreValidation")
public class AmministratoreValidation extends BaseValidation implements Validator{
	
	public boolean supports(Class<?> clazz) {

		return AmministratoreController.class.isAssignableFrom(clazz);
	}

	public void validate(Object arg0, Errors arg1) {
		
	}
	
	public void validateDatiAmministratore(AmministratoreForm amministratore, Errors errors) {
		    
		    if (amministratore.getNome() == null) {
	            errors.rejectValue("nome", "NotNull");
	        }
	        if (amministratore.getCognome() == null) {
	            errors.rejectValue("cognome", "NotNull");
	        }
//	        Segnalazione EMS-179
//	        if (amministratore.getCodiceFiscale() == null) {
//	            errors.rejectValue("codiceFiscale", "NotNull");
//	        }
//	        if (amministratore.getDataNascita() == null) {
//	            errors.rejectValue("dataNascita", "NotNull");
//	        }
//	        if(amministratore.getDataNascita()!=null && amministratore.getDataNascita().after(new Date())){
//	        	errors.rejectValue("dataNascita", "nuovosoggettofisico.dataNascita.errata");
//	        }
//	        if(amministratore.getDataNascita() != null && !DateUtils.isMaggiorenne(amministratore.getDataNascita())){
//	        	errors.rejectValue("dataNascita", "nuovosoggettofisico.dataNascita.nonMaggiorenne");
//	        }
	        if(amministratore.getComuneDomProf().getId() == null && amministratore.getStatoDomProf().getId().equals("1")){
	        	errors.rejectValue("comuneDomProf", "error.amministratore.nuovo.comuneDomProf");
	        }
	        if(!amministratore.getStatoDomProf().getId().equals("1") && amministratore.getComuneEsteroLegale() == null){
	        	errors.rejectValue("comuneDomProf", "error.amministratore.nuovo.comuneDomProf");
	        }
	        /*    
	        ValidationUtil.checkComuneSelezionato(amministratore.getComuneDomProf(), amministratore.getComuneEsteroLegale(), errors, "error.amministratore.nuovo.comuneDomProf");
	         	*/
	      //se è stato inserito un comune di domicilio eletto (italiano o estero), effettuo il check del Comune selezionato/inserito
			if(amministratore.getStatoDomicilioLegale()!=null && !Constants.NO_ITEM_SELECTED.equalsIgnoreCase(amministratore.getStatoDomicilioLegale())) {
				ValidationUtil.checkComuneSelezionato(amministratore.getComuneDomProf(), amministratore.getComuneEsteroLegale(), errors, "comuneDomicilioEletto.descrizione");
			}
		}	
	
	public void validateDatiCoadiutore(CoadiutoreForm coadiutore, Errors errors) {
	    
	    if (coadiutore.getNome() == null) {
            errors.rejectValue("nome", "NotNull");
        }
        if (coadiutore.getCognome() == null) {
            errors.rejectValue("cognome", "NotNull");
        }
        if (coadiutore.getInizioIncarico() == null) {
            errors.rejectValue("inizioIncarico", "NotNull");
        }
        if (coadiutore.getInizioIncarico() != null && coadiutore.getFineIncarico() != null && coadiutore.getFineIncarico().before(coadiutore.getInizioIncarico())) {
        	super.setMessage(Constants.MESSAGE, "coadiutori.dateIncaricoError", null, false);
        }

	}		
	
}
