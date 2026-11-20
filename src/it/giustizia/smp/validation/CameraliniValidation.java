package it.giustizia.smp.validation;

import javax.servlet.http.HttpServletRequest;

import it.giustizia.smp.controller.cameralini.RicercaBaseController;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.form.cameralini.BaseForm;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.utility.ValidationUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("cameraliniValidation")
public class CameraliniValidation extends BaseValidation implements Validator {
	

	public void validateCriteriRicercaPuntuale(ProcedimentoForm procedimento, Errors errors) {

		if (procedimento.getAnno() == null) {
			errors.rejectValue("procedPadre.anno", "NotNull");
		}
		if (procedimento.getNumero() == null) {
			errors.rejectValue("procedPadre.numero", "NotNull");
		}
	}

	public void validateDatiRichiedente(BaseForm form, Errors errors) {

		if (form.getNomeRichiedente() == null || "".equals(form.getNomeRichiedente())) {
			errors.rejectValue("nomeRichiedente", "NotNull");
		}
		
		if (form.getCognomeRichiedente() == null || "".equals(form.getCognomeRichiedente())) {
			errors.rejectValue("cognomeRichiedente", "NotNull");
		}
		
		if (form.getDataDeposito() == null ) {
			errors.rejectValue("dataDeposito", "NotNull");
		}
		
		if (form.getDataSottoscrizione() == null ) {
			errors.rejectValue("dataSottoscrizione", "NotNull");
		}
		if ("-1".equals(form.getTrasmissione().getId())){
			errors.rejectValue("trasmissione", "NotNull");
		}
	}
	
	
	public void validateDatiSoggetto(SoggettoForm soggetto, Errors errors) {

		// DATI PERSONA FISICA
		if ("F".equals(soggetto.getTipoSoggetto())) {
		
			if (soggetto.getNome() == null || "".equals(soggetto.getNome())) {
				errors.rejectValue("nome", "NotNull");
			}
			
			if (soggetto.getCognome() == null || "".equals(soggetto.getCognome())) {
				errors.rejectValue("cognome", "NotNull");
			}
			if (soggetto.getSesso() == null || "-1".equals(soggetto.getSesso())){
				errors.rejectValue("sesso", "NotNull");
			}
			
			/*
			if (soggetto.getCodiceFiscale() == null ) {
				errors.rejectValue("codiceFiscale", "NotNull");
			} else {
				if(StringUtils.isNotBlank(soggetto.getCodiceFiscale())){
					String message = Utility.ControllaCodiceFiscale(soggetto.getCodiceFiscale());
					if(StringUtils.isNotBlank(message)){
						errors.rejectValue("codiceFiscale", message);
					}
				}
			}
			*/
			
			if (soggetto.getDataNascita() == null ) {
				errors.rejectValue("dataNascita", "NotNull");
			}
			if ("-1".equals(soggetto.getStatoNascita().getId())){
				errors.rejectValue("statoNascita.id", "NotNull");
			}
			if ("1".equals(soggetto.getStatoNascita().getId())){
				if (soggetto.getComuneNascita().getDescrizione() == null || "-1".equals(soggetto.getComuneNascita().getId())){
					errors.rejectValue("comuneNascita.descrizione", "NotNull");
				} else {
					ValidationUtil.checkComuneSelezionato(soggetto.getComuneNascita(), soggetto.getDescrizioneComuneEsteroNascita(), errors, "comuneNascita.descrizione");
				}
			}else{
				if (soggetto.getDescrizioneComuneEsteroNascita()== null || "".equalsIgnoreCase(soggetto.getDescrizioneComuneEsteroNascita())){
					errors.rejectValue("comuneNascita.descrizione", "NotNull");
				}
				
			}
		}
		
		// DATI PERSONA GIURIDICA
		if ("G".equals(soggetto.getTipoSoggetto())) {
			if (soggetto.getRagioneSociale() == null ) {
				errors.reject(Constants.MESSAGE);
				errors.rejectValue("ragioneSociale", "NotNull");
			}
			
			if ((soggetto.getPartitaIvaSiglaStato() == null) || (soggetto.getPartitaIva() == null)) {
				errors.reject(Constants.MESSAGE);
				errors.rejectValue("partitaIva", "NotNull");
			} else {
				Utility.controlloPartitaIva(soggetto, errors,null);
			}
			if (soggetto.getIdTipoSocieta() == -1 ) 
			{
				errors.reject(Constants.MESSAGE);
				errors.rejectValue("idTipoSocieta", "NotNull");
				
			}
			if ("-1".equals(soggetto.getStatoSedeLegale().getId())) 
			{
				errors.reject(Constants.MESSAGE);
				errors.rejectValue("statoSedeLegale.id", "NotNull");
			}
			if (soggetto.getComuneSedeLegale().getDescrizione() == null )
			{
				errors.reject(Constants.MESSAGE);
				errors.rejectValue("comuneSedeLegale.descrizione", "NotNull");
			} else {
				ValidationUtil.checkComuneSelezionato(soggetto.getComuneSedeLegale(), soggetto.getComuneSedeLegale().getDescrizione(), errors, "comuneSedeLegale.descrizione");
			}
		}
	}
	
	public void validateRicercaSoggettoFisico(SoggettoForm soggetto, Errors errors) {

		if ((soggetto.getCognome() == null) && (soggetto.getNome() == null) && (soggetto.getDataNascita() == null)
				&& (soggetto.getStatoNascita().getId().equals(String.valueOf(Constants.NO_ITEM_SELECTED))) && (soggetto.getComuneNascita().getId() == null)
				&& StringUtils.isEmpty(soggetto.getCodiceFiscale())) {
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "form.selection", null, false);
			return;
		}
		
		
		if(StringUtils.isNotBlank(soggetto.getCodiceFiscale())){
			String message = Utility.ControllaCodiceFiscale(soggetto.getCodiceFiscale());
			if(StringUtils.isNotBlank(message)){
				errors.rejectValue("codiceFiscale", message);
//				super.setMessage(Constants.MESSAGE, message, null, false);
			}
		}
		
		
	}
	
	public void validateRicercaSoggettoGiuridico(SoggettoForm soggetto, Errors errors) {
		
		if ((soggetto.getRagioneSociale() == null)&& (soggetto.getPartitaIvaSiglaStato() == null) && (soggetto.getPartitaIva() == null) && soggetto.getStatoSedeLegale().getId().equals(String.valueOf(Constants.NO_ITEM_SELECTED)) && (soggetto.getComuneSedeLegale().getId() == null)) {
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "form.selection", null, false);
		}
	}
	
	// protected static String MESSAGE = "customMessage";
	public boolean supports(Class<?> clazz) {
		return RicercaBaseController.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		// Necessario per implementare Validator
	}
}
