package it.giustizia.smp.validation;

import it.giustizia.smp.form.StatisticheForm;
import it.giustizia.smp.model.TStampa;
import it.giustizia.smp.service.StatisticheService;
import it.giustizia.smp.utility.ValidationUtil;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
/**
 * Classe per la validazione dei campi filtro per le statistiche
 * @author m.prisco
 *
 */
@Component("statisticheValidation")
public class StatisticheValidation extends BaseValidation implements Validator {
	@Autowired
	StatisticheService statisticheService;

	/***
	 * Validazione della jsp filtriStatistiche.
	 * @param statisticheForm
	 * @param errors
	 * @param request
	 */
	public void validateFiltri(StatisticheForm statisticheForm, BindingResult errors, HttpServletRequest request) {
		TStampa stampa = statisticheService.getStampaByID(statisticheForm.getIdStampa());
		String[] campiFiltro = stampa.getDescCampiFiltro().split(",");
		for (String filtro : campiFiltro) {
			filtro = filtro.replaceAll(" ","");
			if ("Magistrato".equalsIgnoreCase(filtro) ) {
				ValidationUtil.requiredComboForm(statisticheForm.getMagistrato(), "magistrato.id", errors);
			} else if ("DataDal".equalsIgnoreCase(filtro) ) {
				if (statisticheForm.getFiltroDataDal() == null) {
					errors.rejectValue("filtroDataDal", "NotNull");
				}
			} else if ("DataAl".equalsIgnoreCase(filtro) ) {
				if (statisticheForm.getFiltroDataAl() == null) {
					errors.rejectValue("filtroDataAl", "NotNull");
				}
			} else if ("Anno".equalsIgnoreCase(filtro) ) {
				ValidationUtil.requiredComboForm(statisticheForm.getAnno(), "anno.id", errors);
			} else if ("Semestre".equalsIgnoreCase(filtro) ) {
				ValidationUtil.requiredComboForm(statisticheForm.getSemestre(), "semestre.id", errors);
			} else if ("Procedente".equalsIgnoreCase(filtro) ) {
				ValidationUtil.requiredComboForm(statisticheForm.getProcedente(), "procedente.id", errors);
			}
		}
	}

	public void validate(Object target, Errors errors) {
	}
	
	
	public boolean supports(Class<?> clazz) {
		return StatisticheValidation.class.isAssignableFrom(clazz);
	}

}
