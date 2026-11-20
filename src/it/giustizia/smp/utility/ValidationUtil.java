/**
 * 
 */
package it.giustizia.smp.utility;

import it.giustizia.smp.form.ComboForm;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

/**
 * Classe di utilità per la validazione
 * 
 * @author g.gabriele
 *
 */
public class ValidationUtil {

	/**
	 * Valida se il campo passato è null o meno
	 * @param campo
	 * @param errors
	 */
	public static void required(String campo, BindingResult errors){
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, campo, "NotNull");
	}
	/**
	 * Valida se la comboForm è selezionata o meno
	 * @param comboForm
	 * @param campo
	 * @param errors
	 */
	public static void requiredComboForm(ComboForm comboForm,String campo,BindingResult errors){
		if(comboForm== null || StringUtils.isEmpty(comboForm.getId()) || NumberUtils.toInt(comboForm.getId(), 0)<=0){
			errors.rejectValue(campo, "NotNull");
		}
	}
	/**
	 * Validazione campi codice fiscale e partitaIva. Restituisce true se entrambi non sono valorizzati, false altrimenti
	 * @param codiceFiscale
	 * @param partitaIva
	 * @param errors
	 * @return Restituisce true se codiceFiscale e partitaIva non sono valorizzati, false altrimenti
	 */
	public static boolean requiredCfAndOrPIva(String codiceFiscale,String partitaIva,BindingResult errors){
		
		Object valueCF = errors.getFieldValue(codiceFiscale);
		Object valuePiva = errors.getFieldValue(partitaIva);
		
		if(valueCF == null && valuePiva==null){
			errors.reject(Constants.MESSAGE);
			return true;
		}
		String valueCFString = ConvertUtils.convert(valueCF);
		String valuePivaString = ConvertUtils.convert(valuePiva);
		
		
		
		if(StringUtils.isEmpty(valueCFString) && StringUtils.isEmpty(valuePivaString)){
			errors.reject(Constants.MESSAGE);
			return true;
		}
		
		return false;
	}
	/**
	 * Restituisce true se almeno una delle proprietà e valorizzata, false altrimenti
	 * @param errors
	 * @param props
	 * @return true se almeno una delle proprietà e valorizzata, false altrimenti
	 */
	public static boolean requiredAtLeastOne(BindingResult errors,String... props){
		for(String prop:props){
			
			Object value = errors.getFieldValue(prop);
			String valuetring = ConvertUtils.convert(value);
			
			if(StringUtils.isNotEmpty(valuetring) && !StringUtils.equalsIgnoreCase(valuetring, "-1")){
				return false;
			}
		}
		errors.reject(Constants.MESSAGE);
		return true;
	}
	
	/**
	 * Restituisce true se almeno una delle proprietà e valorizzata, false altrimenti
	 * @param errors
	 * @param props
	 * @return true se almeno una delle proprietà e valorizzata, false altrimenti
	 */
	public static boolean checkAllEmpty(BindingResult errors,String... props){
		for(String prop:props){
			
			Object value = errors.getFieldValue(prop);
			String valuetring = ConvertUtils.convert(value);
			
			if(StringUtils.isNotEmpty(valuetring) && !StringUtils.equalsIgnoreCase(valuetring, "-1")){
				return false;
			}
		}
	
		return true;
	}
	
	/**
	 * 
	 * @param soggetto
	 * @param errors
	 */
	public static void checkComuneSelezionato(ComboForm comune, String descComuneEstero, Errors errors, String campo) {
		
		if (comune.getDescrizione() == null && descComuneEstero == null) {
            errors.rejectValue(campo, "NotNull");
        }else if (Constants.KEY_DEFAULT_CAMPO_HIDDEN.equalsIgnoreCase(comune.getId()) && descComuneEstero == null) {
        	//caso di stato Italia con idComune 9999: comune non selezionato correttamente
        	errors.rejectValue(campo, "nuovosoggetto.comuneInesistente");
        }
	}
	
	
}
