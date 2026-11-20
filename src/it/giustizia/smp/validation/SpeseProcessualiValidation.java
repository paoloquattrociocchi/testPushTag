package it.giustizia.smp.validation;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.giustizia.smp.controller.procedimento.NuovoController;
import it.giustizia.smp.dao.AmministrazioneDao;
import it.giustizia.smp.form.SpeseProcessualiForm;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.DateUtils;

@Component("speseProcessualiValidation")
public class SpeseProcessualiValidation extends BaseValidation implements Validator {

	@Autowired
	private AmministrazioneDao amministrazioneDao;
	
	public boolean supports(Class<?> clazz) {

		return NuovoController.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {

		// serve per implementare validator
	}
	
	public void validateInserimento(SpeseProcessualiForm speseProcessuali, Errors errors) {
		this.checkImporto(speseProcessuali, errors);
        this.checkDataInizioValidita(speseProcessuali, errors);
	}
	
	public void validateModifica(Date dataFineValidita, SpeseProcessualiForm spesaSession, Errors errors) {
		if(dataFineValidita != null) {
			if(spesaSession.getDataFineValidita() != null && dataFineValidita.compareTo(spesaSession.getDataFineValidita()) != 0) {
				errors.rejectValue("dataFineValidita", "dataFineValiditaNotValid");
			} else if(dataFineValidita.before(spesaSession.getDataInizioValidita())
						|| dataFineValidita.before(DateUtils.parseDate(DateUtils.formatDate(new Date(), DateUtils.FORMAT_STANDARD), DateUtils.FORMAT_STANDARD))
						|| dataFineValidita.equals(DateUtils.parseDate(DateUtils.formatDate(new Date(), DateUtils.FORMAT_STANDARD), DateUtils.FORMAT_STANDARD))) {
				errors.rejectValue("dataFineValidita", "dataFineValiditaNotValid");
			}
		}
	}
	
	private void checkImporto(SpeseProcessualiForm speseProcessuali, Errors errors) {
		if (speseProcessuali.getImportoSpese() == null || speseProcessuali.getImportoSpese().doubleValue() <= 0){
            errors.reject(Constants.MESSAGE);
            errors.rejectValue("importoSpese", "NotNull");
		}
		if (speseProcessuali.getImportoSpese() != null) {
			if(speseProcessuali.getImportoSpese().longValue() >= 10000000000000L) {
				errors.rejectValue("importoSpese", "importoNotValid");
			} else {
				boolean alreadyExistValidImportoSpese = amministrazioneDao.existImportoSpese(speseProcessuali.getImportoSpese(), speseProcessuali.getDataInizioValidita());
				if(alreadyExistValidImportoSpese) {
					errors.rejectValue("importoSpese", "importoAlreadyExist");
				}
			}
		}
	}
	
	private void checkDataInizioValidita(SpeseProcessualiForm speseProcessuali, Errors errors) {
		if(speseProcessuali.getDataInizioValidita() == null) {
        	errors.reject(Constants.MESSAGE);
            errors.rejectValue("dataInizioValidita", "NotNull");
        }
	}
}
