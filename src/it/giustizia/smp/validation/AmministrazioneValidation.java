package it.giustizia.smp.validation;

import it.giustizia.smp.controller.procedimento.AmministratoreController;
import it.giustizia.smp.form.CategoriaCatastaleForm;
import it.giustizia.smp.form.ComuneForm;
import it.giustizia.smp.form.CondizioneGiuridicaForm;
import it.giustizia.smp.form.ConservatoriaForm;
import it.giustizia.smp.form.IstitutoDetenzioneForm;
import it.giustizia.smp.form.ProfessioneForm;
import it.giustizia.smp.form.ProvinciaForm;
import it.giustizia.smp.form.SettoreAttivitaForm;
import it.giustizia.smp.form.StatoForm;
import it.giustizia.smp.form.UfficiForm;
import it.giustizia.smp.utility.Constants;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("amministrazioneValidation")
public class AmministrazioneValidation extends BaseValidation implements Validator{

	public boolean supports(Class<?> clazz) {

		return AmministratoreController.class.isAssignableFrom(clazz);
	}

	public void validate(Object arg0, Errors arg1) {
		
	}
	
	public void validateStatoInserimento(StatoForm statoForm, Errors errors) {
		if(statoForm.getDescStato() == null){
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "amministrazione.stati.error.descStato", null, false);
			return;
		}
		if(statoForm.getDescSiglaStato() == null){
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "amministrazione.stati.error.descSiglaStato", null, false);
			return;
		}
		if(statoForm.getDescIstatStato() == null){
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "amministrazione.stati.error.descIstatStato", null, false);
			return;
		}
	}
	
	public void validateProvinciaInserimento(ProvinciaForm provinciaForm, Errors errors) {
		if(provinciaForm.getDescProvincia() == null){
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "amministrazione.province.error.descProvincia", null, false);
			return;
		}
		if(provinciaForm.getDescSiglaProvincia() == null){
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "amministrazione.province.error.descSiglaProvincia", null, false);
			return;
		}
		if(provinciaForm.getCodiKIdIstatProv() == null){
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "amministrazione.province.error.codiKIdIstatProv", null, false);
			return;
		}
	}
	
	public void validateComuneInserimento(ComuneForm comuneForm, Errors errors) {
		if((comuneForm.getItemProvincia() == null || comuneForm.getItemProvincia().getId().equals("-1") ) ){
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "amministrazione.comuni.error.fkIdProvincia", null, false);
			return;
		}
		if(comuneForm.getDescComune() == null){
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "amministrazione.comuni.error.descComune", null, false);
			return;
		}
		if(comuneForm.getDescBelfiore() == null){
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "amministrazione.comuni.error.descBelfiore", null, false);
			return;
		}
		if(comuneForm.getDescCapComune() == null){
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "amministrazione.comuni.error.descCapComune", null, false);
			return;
		}
		if(comuneForm.getDescIstatComune() == null){
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "amministrazione.comuni.error.descIstatComune", null, false);
			return;
		}
		if(comuneForm.getDescCatasto() == null){
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "amministrazione.comuni.error.descCatasto", null, false);
			return;
		}
	}
	
	public void validateUfficiInserimento(UfficiForm ufficiForm, Errors errors) {
				
		if(ufficiForm.getItemTipoUfficio() == null || ufficiForm.getItemTipoUfficio().getId().equals("-1")){
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "amministrazione.uffici.error.tipoUfficio", null, false);
			return;
		}
		
		if(ufficiForm.getItemComuneSede() == null || ufficiForm.getItemComuneSede().getId().equals("-1")){
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "amministrazione.uffici.error.comuneUfficio", null, false);
			return;
		}
		
		if(ufficiForm.getDescIndirizzoSede() == null || ufficiForm.getDescIndirizzoSede().trim().length() == 0){
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "amministrazione.uffici.error.indirizzo", null, false);
			return;
		}
	}
	
	public void validateIstitutoDetenzioneInserimento(IstitutoDetenzioneForm istitutoDetenzioneForm, Errors errors) {
		
		if(istitutoDetenzioneForm.getItemTipoIstituto() == null || istitutoDetenzioneForm.getItemTipoIstituto().getId().equals("-1")){
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "amministrazione.istitutoDet.error.tipoIstituto", null, false);
			return;
		}
		
		if(istitutoDetenzioneForm.getItemComune() == null || istitutoDetenzioneForm.getItemComune().getId().equals("-1")){
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "amministrazione.istitutoDet.error.comune", null, false);
			return;
		}
		
		if(istitutoDetenzioneForm.getDescIndirizzo() == null || istitutoDetenzioneForm.getDescIndirizzo().trim().length() == 0){
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "amministrazione.istitutoDet.error.indirizzo", null, false);
			return;
		}
	}
	
	public void validateCatCatastaliInserimento(CategoriaCatastaleForm categoriaCatastaleForm, Errors errors) {
		if(categoriaCatastaleForm.getDescCategoriaCat() == null){
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "amministrazione.catCatastali.error.descrizione", null, false);
			return;
		}
		if(categoriaCatastaleForm.getDescBreveCategoriaCat() == null){
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "amministrazione.catCatastali.error.descrbreve", null, false);
			return;
		}
	}
	
	public void validateProfessioneInserimento(ProfessioneForm professioneForm, Errors errors) {
		if(professioneForm.getDescProfessione() == null){
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "amministrazione.professioni.error.descrizione", null, false);
			return;
		}
	}

	public void validateSettoreAttivitaInserimento(SettoreAttivitaForm settoreAttivitaForm, Errors errors) {
		if(settoreAttivitaForm.getDescSettoreAtt() == null){
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "amministrazione.settore.error.descrizione", null, false);
			return;
		}
	}
	
	public void validateConservatoriaInserimento(ConservatoriaForm conservatoriaForm, Errors errors) {
		if(conservatoriaForm.getDescConserv() == null){
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "amministrazione.conservatorie.error.descrizione", null, false);
			return;
		}
		if(conservatoriaForm.getDescCodiConserv() == null){
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "amministrazione.conservatorie.error.codice.conservatoria", null, false);
			return;
		}
	}
	
	public void validateCondizioneGiuridicaInserimento(CondizioneGiuridicaForm condizioneGiuridicaForm, Errors errors) {
		if(condizioneGiuridicaForm.getDescCondGiur() == null){
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "amministrazione.condGiur.error.descrizione", null, false);
			return;
		}
	}
}
