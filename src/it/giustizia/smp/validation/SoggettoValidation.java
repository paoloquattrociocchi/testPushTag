package it.giustizia.smp.validation;

import it.giustizia.smp.controller.procedimento.NuovoController;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.DifensoreSoggettoForm;
import it.giustizia.smp.form.MisuraForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.service.CommonService;
import it.giustizia.smp.service.utility.UtilityService;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.DateUtils;
import it.giustizia.smp.utility.Message;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.utility.ValidationUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("soggettoValidation")
public class SoggettoValidation extends BaseValidation implements Validator {

	@Autowired
	private CommonService	commonService;

	public boolean supports(Class<?> clazz) {

		return NuovoController.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {

		// serve per implementare validator
	}

	// ***************** MISURA PERSONALE ****************//
	public void validateBottoneMisuraPersonale(String tipoSoggetto, String tipoMisuraSelected, String statoAtto, Errors errors, HttpServletRequest request) {

	    // PERSONA GIURIDICA
		if (tipoSoggetto.equals("G")) {
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "soggetto.misuraPersonale.error.noSoggettoGiuridico", null, false);
		}
		else {
		    // PERSONA FISICA
		    validateTipoMisuraStatoAtto(tipoMisuraSelected,statoAtto,"PATRIMONIALE", errors);
        }
	}

    // ***************** MISURA PATRIMONIALE ****************//
    public void validateBottoneMisuraPatrimoniale(String tipoMisuraSelected, String statoAtto, Errors errors, HttpServletRequest request) {

        validateTipoMisuraStatoAtto(tipoMisuraSelected, statoAtto, "PERSONALE", errors);
    }
    
    private void validateTipoMisuraStatoAtto(String tipoMisuraSelected, String statoAtto, String tipo, Errors errors) {

        String disabilita = (String)request.getSession().getAttribute("disabledViewProc");
        // Con StatoProc in Acquisizione Proposta non viene impostato
        if(disabilita!=null && disabilita.equalsIgnoreCase("true")){
            if(statoAtto==null){
                //Atto non presente
                if (tipoMisuraSelected!=null && tipoMisuraSelected.equalsIgnoreCase(tipo)) {
                    errors.reject(Constants.MESSAGE);
                    super.setMessage(Constants.MESSAGE, "soggetto.misura.nonPresente", null, false);
                } 
            }
            else{
                //Atto presente
                if (tipoMisuraSelected!=null && tipoMisuraSelected.equalsIgnoreCase(tipo) && !statoAtto.equalsIgnoreCase("1")) {
                    errors.reject(Constants.MESSAGE);
                    super.setMessage(Constants.MESSAGE, "soggetto.misura.nonPresente", null, false);
                } 
            }  
        }
    }

	public void validateDettaglioProcedimentiCollegati(SoggettoForm soggetto, Errors errors, HttpServletRequest request) {

		if (soggetto.getIdProcedimento() == null) {
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
		}
	}

	public void validateGestioneSoggettoInterveniente(SoggettoForm soggetto, Errors errors, HttpServletRequest request) {


			if (soggetto.getIdSoggettoInterveniente() == null) {
				errors.reject(Constants.MESSAGE);
				super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
			}

	}

	public void validateGestioneSoggetto(SoggettoForm soggetto, Errors errors, HttpServletRequest request) {

			if (soggetto.getChiave() == null) {
				errors.reject(Constants.MESSAGE);
				super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
			}


	}
	
	public void validateGestioneVersioneSoggetto(SoggettoForm soggetto, Errors errors, HttpServletRequest request) {

        if (soggetto.getIdSoggetto() == null || soggetto.getIdVersione() == null) {
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
		}
	}	

	// ***************** DIFENSORE ****************//
	public void validateGratuitoPatrocinio(DifensoreSoggettoForm difensore, Errors errors) {

		List<Message> customMessageList = new ArrayList<Message>();
		if (difensore.getSoggettoForm() != null) {
			if (difensore.getSoggettoForm().getAnnoRegistroGratuitoPatrocinio() == null) {
				errors.rejectValue("soggettoForm.annoRegistroGratuitoPatrocinio", "NotNull");
			}
			if (difensore.getSoggettoForm().getNumeroRegistroGratuitoPatrocinio() == null) {
				errors.rejectValue("soggettoForm.numeroRegistroGratuitoPatrocinio", "NotNull");
			}
			if (difensore.getSoggettoForm().getDataIstanzaGratuitoPatrocinio() == null) {
				errors.rejectValue("soggettoForm.dataIstanzaGratuitoPatrocinio", "NotNull");
			}
			if (difensore.getSoggettoForm().getDataProvvedimentoGratuitoPatrocinio() != null) 
			{
				if(difensore.getSoggettoForm().getDataIstanzaGratuitoPatrocinio() != null)
				{
					if(difensore.getSoggettoForm().getDataProvvedimentoGratuitoPatrocinio().before(difensore.getSoggettoForm().getDataIstanzaGratuitoPatrocinio()))
						super.addMessageIntoList(customMessageList, Constants.MESSAGE, "soggetto.difensore.gratuitoPatrocinio.error.dataProvvedimento.maggiore.dataIstanza", null);
				}
				if ((difensore.getSoggettoForm().getTenoreProvvedimentoGratuitoPatrocinio() == null) || ((difensore.getSoggettoForm().getTenoreProvvedimentoGratuitoPatrocinio() != null) && difensore.getSoggettoForm().getTenoreProvvedimentoGratuitoPatrocinio().getId().equals("-1"))) 
					super.addMessageIntoList(customMessageList, Constants.MESSAGE, "soggetto.difensore.gratuitoPatrocinio.error.tenore.assente.dataProvvedimento.presente", null);
			}			

			controllaCoerenzaAnnoRegistro(difensore, customMessageList);
		}
		super.setMessages(customMessageList, false, errors);
	}
	
	public void validateCognomeReginde(DifensoreSoggettoForm difensore, Errors errors) {
		if (difensore.getSoggettoForm() != null) {
			if (difensore.getDifensoreForm().getCognome() == null) {
				errors.rejectValue("difensoreForm.cognome", "NotNull");
			}
		}
	}

	// ***************** SOGGETTO FISICO ****************//
	public void validateNuovoSoggettoFisicoObbl(SoggettoForm soggetto, Errors errors) {
	    
		if (soggetto.getIdRuolo() != Constants.ID_RUOLO_INTERVENIENTE) {
			if (request.getSession().getAttribute(Constants.TIPO_UTENTE).equals(Constants.TIPO_UTENTE_SMP) && soggetto.getIdRuolo() != Constants.ID_RUOLO_TERZO)
				validaSceltaTipoPrevenzioneMisuraNormativaObbl(soggetto, errors);

			if (soggetto.getDataNascita() == null) {
				errors.rejectValue("dataNascita", "NotNull");
			}
			if (soggetto.getDataNascita() != null && soggetto.getDataNascita().after(new Date())) {
				errors.rejectValue("dataNascita", "nuovosoggettofisico.dataNascita.errata");
			}
			if (soggetto.getDataNascita() != null && !DateUtils.isMaggiorenne(soggetto.getDataNascita())) {
				errors.rejectValue("dataNascita", "nuovosoggettofisico.dataNascita.nonMaggiorenne");
			}
			if (soggetto.getStatoNascita().getId().equalsIgnoreCase(Constants.NO_ITEM_SELECTED)) {
				errors.rejectValue("statoNascita.id", "NotNull");
			}
			if (soggetto.getSesso().equalsIgnoreCase(Constants.NO_ITEM_SELECTED)) {
				errors.rejectValue("sesso", "NotNull");
			}
			ValidationUtil.checkComuneSelezionato(soggetto.getComuneNascita(), soggetto.getDescrizioneComuneEsteroNascita(), errors, "comuneNascita.descrizione");
		}

	    if (soggetto.getNome() == null) {
            errors.rejectValue("nome", "NotNull");
        }
        if (soggetto.getCognome() == null) {
            errors.rejectValue("cognome", "NotNull");
        }
	}

	public void validateNuovoSoggettoFisicoObblVer(SoggettoForm soggetto, Errors errors) {
	    
	    if (soggetto.getNome() == null) {
            errors.rejectValue("soggetto.nome", "NotNull");
        }
        if (soggetto.getCognome() == null) {
            errors.rejectValue("soggetto.cognome", "NotNull");
        }
        if (soggetto.getDataNascita() == null) {
            errors.rejectValue("soggetto.dataNascita", "NotNull");
        }
        if(soggetto.getDataNascita()!=null && soggetto.getDataNascita().after(new Date())){
        	errors.rejectValue("soggetto.dataNascita", "nuovosoggettofisico.dataNascita.errata");
        }
        if (soggetto.getStatoNascita().getId().equalsIgnoreCase(Constants.NO_ITEM_SELECTED)) {
            errors.rejectValue("soggetto.statoNascita.id", "NotNull");
        }  
        if (soggetto.getSesso().equalsIgnoreCase(Constants.NO_ITEM_SELECTED)) {
            errors.rejectValue("soggetto.sesso", "NotNull");
        }


        ValidationUtil.checkComuneSelezionato(soggetto.getComuneNascita(), soggetto.getDescrizioneComuneEsteroNascita(), errors, "soggetto.comuneNascita.descrizione");
        
	}	
	
	
	
	
	public void validateNuovoSoggettoNonObbl(SoggettoForm soggetto, Errors errors) {
		
		//se è stato inserito un comune di residenza (italiano o estero), effettuo il check del Comune selezionato/inserito
		if(soggetto.getStatoResidenza()!=null && !Constants.NO_ITEM_SELECTED.equalsIgnoreCase(soggetto.getStatoResidenza().getId())) {
			ValidationUtil.checkComuneSelezionato(soggetto.getComuneResidenza(), soggetto.getDescComuneEsteroResidenza(), errors, "comuneResidenza.descrizione");
		}
		
		//se è stato inserito un comune di domicilio (italiano o estero), effettuo il check del Comune selezionato/inserito
		if(soggetto.getStatoDomicilio()!=null && !Constants.NO_ITEM_SELECTED.equalsIgnoreCase(soggetto.getStatoDomicilio().getId())) {
			ValidationUtil.checkComuneSelezionato(soggetto.getComuneDomicilio(), soggetto.getDescComuneEsteroDomicilio(), errors, "comuneDomicilio.descrizione" );
		}
				
		//se è stato inserito un comune di dimora (italiano o estero), effettuo il check del Comune selezionato/inserito
		if(soggetto.getStatoDimora()!=null && !Constants.NO_ITEM_SELECTED.equalsIgnoreCase(soggetto.getStatoDimora().getId())) {
			ValidationUtil.checkComuneSelezionato(soggetto.getComuneDimora(), soggetto.getDescComuneEsteroDimora(), errors, "comuneDimora.descrizione");
		}

		//se è stato inserito un comune di domicilio eletto (italiano o estero), effettuo il check del Comune selezionato/inserito
		if(soggetto.getStatoDomicilioEletto()!=null && !Constants.NO_ITEM_SELECTED.equalsIgnoreCase(soggetto.getStatoDomicilioEletto().getId())) {
			ValidationUtil.checkComuneSelezionato(soggetto.getComuneDomicilioEletto(), soggetto.getDescComuneEsteroDomicilioEletto(), errors, "comuneDomicilioEletto.descrizione");
		}
		
		//se è stato inserito un comune di morte (italiano o estero), effettuo il check del Comune selezionato/inserito
		if(soggetto.getStatoMorte()!=null && soggetto.getStatoMorte().getId() != null && !Constants.NO_ITEM_SELECTED.equalsIgnoreCase(soggetto.getStatoMorte().getId())) {
			ValidationUtil.checkComuneSelezionato(soggetto.getComuneMorte(), soggetto.getDescrizioneComuneEsteroMorte(), errors, "comuneMorte.descrizione");
		}

	}


    public void validateSoggettoGiuridico(SoggettoForm soggetto, Errors errors) {
               
		if (soggetto.getIdTipoSocieta()==(Long.parseLong(Constants.NO_ITEM_SELECTED)) ){
			errors.rejectValue("soggetto.idTipoSocieta", "NotNull");
		}
		
		if (soggetto.getRagioneSociale() == null) {
			errors.rejectValue("soggetto.ragioneSociale", "NotNull");
		}
		
		if (soggetto.getPartitaIva() == null) {
			errors.rejectValue("soggetto.partitaIva", "NotNull");
		}		
		

		if (soggetto.getStatoSedeLegale() == null || soggetto.getStatoSedeLegale().getId().equals(Constants.NO_ITEM_SELECTED) ) {
			errors.rejectValue("soggetto.statoSedeLegale.id", "NotNull");
		}

		if ( (soggetto.getComuneSedeLegale()==null && soggetto.getComuneSedeLegale().getId()==null) ||
				soggetto.getComuneSedeLegale().getDescrizione() == null) {
			errors.rejectValue("soggetto.comuneSedeLegale.id", "NotNull");
		}

		if(soggetto.getStatoSedeLegale() != null && soggetto.getStatoSedeLegale().getId().equals("1") &&
				(soggetto.getComuneSedeLegale()==null || soggetto.getComuneSedeLegale().getId()==null || soggetto.getComuneSedeLegale().getId().equals("9999"))) {
			errors.rejectValue("soggetto.comuneSedeLegale.id", "NotNull");
		}
		
		Utility.controlloPartitaIvaVer(soggetto, errors, null);
	}
	
    public void validateNuovoSoggettoGiuridicoVer(SoggettoForm soggetto, Errors errors) {
        
        
		if (soggetto.getIdTipoSocieta()==(Long.parseLong(Constants.NO_ITEM_SELECTED)) ){
			errors.rejectValue("soggetto.idTipoSocieta", "NotNull");
		}
		
		if (soggetto.getRagioneSociale() == null) {
			errors.rejectValue("soggetto.ragioneSociale", "NotNull");
		}
		
		if (soggetto.getPartitaIva() == null) {
			errors.rejectValue("soggetto.partitaIva", "NotNull");
		}		
		
		if(soggetto.getCapitaleSociale() !=null){
			if(soggetto.getCapitaleSociale().longValue() >= 10000000000000L)
				errors.rejectValue("soggetto.capitaleSociale", "capSocNotValid");
		}

		if (soggetto.getStatoSedeLegale() == null || soggetto.getStatoSedeLegale().getId().equals(Constants.NO_ITEM_SELECTED) ) {
			errors.rejectValue("soggetto.statoSedeLegale.id", "NotNull");
		}

		if ( (soggetto.getComuneSedeLegale()==null && soggetto.getComuneSedeLegale().getId()==null) ||
				soggetto.getComuneSedeLegale().getDescrizione() == null) {
			errors.rejectValue("soggetto.comuneSedeLegale.id", "NotNull");
		}

		if(soggetto.getStatoSedeLegale() != null && soggetto.getStatoSedeLegale().getId().equals("1") &&
				(soggetto.getComuneSedeLegale()==null || soggetto.getComuneSedeLegale().getId()==null || soggetto.getComuneSedeLegale().getId().equals("9999"))) {
			errors.rejectValue("soggetto.comuneSedeLegale.id", "NotNull");
		}
		
		Utility.controlloPartitaIvaVer(soggetto, errors, null);
	}	
	
    public void validateNuovoSoggettoGiuridico(SoggettoForm soggetto, Errors errors) {
		if (soggetto.getIdRuolo() != Constants.ID_RUOLO_INTERVENIENTE) {
			if (request.getSession().getAttribute(Constants.TIPO_UTENTE).equals(Constants.TIPO_UTENTE_SMP) && soggetto.getIdRuolo() != Constants.ID_RUOLO_TERZO)
				validaSceltaTipoPrevenzioneMisuraNormativaObbl(soggetto, errors);

			if (soggetto.getIdTipoSocieta() == (Long.parseLong(Constants.NO_ITEM_SELECTED))) {
				errors.rejectValue("idTipoSocieta", "NotNull");
			}

			if (soggetto.getPartitaIva() == null) {
				errors.rejectValue("partitaIva", "NotNull");
			}

			if (soggetto.getStatoSedeLegale() == null || soggetto.getStatoSedeLegale().getId().equals(Constants.NO_ITEM_SELECTED)) {
				errors.rejectValue("statoSedeLegale.id", "NotNull");
			}

			if ((soggetto.getComuneSedeLegale() == null && soggetto.getComuneSedeLegale().getId() == null) || soggetto.getComuneSedeLegale().getDescrizione() == null) {
				errors.rejectValue("comuneSedeLegale.id", "NotNull");
			}

			if (soggetto.getStatoSedeLegale() != null && soggetto.getStatoSedeLegale().getId().equals("1")
					&& (soggetto.getComuneSedeLegale() == null || soggetto.getComuneSedeLegale().getId() == null || soggetto.getComuneSedeLegale().getId().equals("9999"))) {
				errors.rejectValue("comuneSedeLegale.id", "NotNull");
			}

			if (soggetto.getCapitaleSociale() != null) {
				if (soggetto.getCapitaleSociale().longValue() >= 10000000000000L)
					errors.rejectValue("capitaleSociale", "capSocNotValid");
			}

			Utility.controlloPartitaIva(soggetto, errors, null);
		}
		if (soggetto.getRagioneSociale() == null) {
			errors.rejectValue("ragioneSociale", "NotNull");
		}
	}

public void validateModificaSoggettoGiuridico(SoggettoForm soggetto, Errors errors) {
        
        
		if (soggetto.getIdTipoSocieta()==(Long.parseLong(Constants.NO_ITEM_SELECTED)) ){
			errors.rejectValue("idTipoSocieta", "NotNull");
		}
		
		if (soggetto.getRagioneSociale() == null) {
			errors.rejectValue("ragioneSociale", "NotNull");
		}
		
		if (soggetto.getPartitaIva() == null) {
			errors.rejectValue("partitaIva", "NotNull");
		}		
		
		if(soggetto.getCapitaleSociale() !=null){
			if(soggetto.getCapitaleSociale().longValue() >= 10000000000000L)
				errors.rejectValue("capitaleSociale", "capSocNotValid");
		}

		if (soggetto.getStatoSedeLegale() == null || soggetto.getStatoSedeLegale().getId().equals(Constants.NO_ITEM_SELECTED) ) {
			errors.rejectValue("statoSedeLegale.id", "NotNull");
		}

		if ( (soggetto.getComuneSedeLegale()==null && soggetto.getComuneSedeLegale().getId()==null) ||
				soggetto.getComuneSedeLegale().getDescrizione() == null) {
			errors.rejectValue("comuneSedeLegale.id", "NotNull");
		}

		if(soggetto.getStatoSedeLegale() != null && soggetto.getStatoSedeLegale().getId().equals("1") &&
				(soggetto.getComuneSedeLegale()==null || soggetto.getComuneSedeLegale().getId()==null || soggetto.getComuneSedeLegale().getId().equals("9999"))) {
			errors.rejectValue("comuneSedeLegale.id", "NotNull");
		}
		
		Utility.controlloPartitaIvaVer(soggetto, errors, null);
        
	}

    private void validaSceltaTipoPrevenzioneMisuraNormativaObbl(SoggettoForm soggetto, Errors errors) {
        
        if(soggetto.getTipologiaPrevenzione()==null || soggetto.getTipologiaPrevenzione().getId() ==null  || soggetto.getTipologiaPrevenzione().getId().equals(Constants.NO_ITEM_SELECTED)){
            errors.rejectValue("tipologiaPrevenzione.id", "NotNull");
        }
        if(soggetto.getIpotesiMisura()==null || soggetto.getIpotesiMisura().getId() ==null  || soggetto.getIpotesiMisura().getId().equals(Constants.NO_ITEM_SELECTED)){
            errors.rejectValue("ipotesiMisura.id", "NotNull");
        }
        if(soggetto.getNormativa()==null || soggetto.getNormativa().getId() ==null || soggetto.getNormativa().getId().equals(Constants.NO_ITEM_SELECTED)){
            errors.rejectValue("normativa.id", "NotNull");
        }
    }
    
	// ***************** SOGGETTO GIURIDICO ****************//
	public void validateRicercaSoggettoGiuridico(SoggettoForm soggetto, Errors errors, HttpServletRequest request) {
		
		if ((soggetto.getRagioneSociale() == null)&& (soggetto.getPartitaIvaSiglaStato() == null) && (soggetto.getPartitaIva() == null) && soggetto.getStatoSedeLegale().getId().equals(String.valueOf(Constants.NO_ITEM_SELECTED)) && (soggetto.getComuneSedeLegale().getId() == null)) {
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "form.selection", null, false);
		}
		Utility.controlloPartitaIva(soggetto, errors,null);
	}

	

	// ***************** SOGGETTO FISICA ****************//
	public void validateRicercaSoggettoFisico(SoggettoForm soggetto, Errors errors, HttpServletRequest request) {

		if ((soggetto.getCognome() == null) && (soggetto.getNome() == null) && (soggetto.getDataNascita() == null)
				&& (soggetto.getStatoNascita().getId().equals(String.valueOf(Constants.NO_ITEM_SELECTED))) && (soggetto.getComuneNascita().getId() == null)
				&& StringUtils.isEmpty(soggetto.getCui()) && StringUtils.isEmpty(soggetto.getCodiceFiscale())) {
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
	public void validateSalvaDifensore(DifensoreSoggettoForm difensoreSoggettoForm, Errors errors) {

		if (difensoreSoggettoForm.getDifensoreForm() != null) {
			if (difensoreSoggettoForm.getDifensoreForm().getCognome() == null) {
				errors.rejectValue("difensoreForm.cognome", "NotNull");
			}
			if (difensoreSoggettoForm.getDifensoreForm().getNome() == null) {
				errors.rejectValue("difensoreForm.nome", "NotNull");
			}
			if ((difensoreSoggettoForm.getTipoNomina() == null) || ((difensoreSoggettoForm.getTipoNomina() != null) && difensoreSoggettoForm.getTipoNomina().getId().equals("-1"))) {
				errors.rejectValue("tipoNomina.id", "NotNull");
			}
	        if(difensoreSoggettoForm.getDifensoreForm().getDataNascita() != null && !DateUtils.isMaggiorenne(difensoreSoggettoForm.getDifensoreForm().getDataNascita())){
	        	errors.rejectValue("difensoreForm.dataNascita", "nuovosoggettofisico.dataNascita.nonMaggiorenne");
	        }
			if (difensoreSoggettoForm.getDataNomina() == null) {
				errors.rejectValue("dataNomina", "NotNull");
				return;
			} else
				if (difensoreSoggettoForm.getDataNomina().compareTo(new Date()) > 0) {
					errors.rejectValue("dataNomina", "Past");
					return;
				}
			if ((difensoreSoggettoForm.getDataRevoca() != null) && (difensoreSoggettoForm.getDataRevoca().compareTo(difensoreSoggettoForm.getDataNomina()) < 0)) {
				errors.rejectValue("dataRevoca", "soggetto.difensore.error.dataRevoca");
			}

		}
	}

	public void validateSalvaMisuraPersonale(SoggettoForm soggetto, boolean isDecretoDecisorio, Errors errors, HttpServletRequest request) {

	    String decisioneMisuraPers = null;
		if (isDecretoDecisorio) {
			// verifica che sia selezionata una decisione (vale solo per il decisorio)
		    if(soggetto.getMisura() != null){
		        if (soggetto.getMisura().getDecisioneMisuraPersonale() != null){
		            if(soggetto.getMisura().getDecisioneMisuraPersonale().getId().equals(Constants.NO_ITEM_SELECTED)) {
    	                errors.reject(Constants.MESSAGE);
    	                super.setMessage(Constants.MESSAGE, "error.misuraPersonale.decisione", null, false);
		            }
		            else{
		                decisioneMisuraPers = soggetto.getMisura().getDecisioneMisuraPersonale().getId();
		            }
	            }
	            if(decisioneMisuraPers!=null){
	                if(decisioneMisuraPers.equals("28") || decisioneMisuraPers.equals("50") || decisioneMisuraPers.equals("51")
	                        || decisioneMisuraPers.equals("52") || decisioneMisuraPers.equals("53")
	                        || decisioneMisuraPers.equals("59") || decisioneMisuraPers.equals("60")
	                        || decisioneMisuraPers.equals("61") || decisioneMisuraPers.equals("62")){
	                    if(soggetto.getMisura().getCauzione()!=null){
	                        if (soggetto.getMisura().getCauzione().equalsIgnoreCase("S")){
	                            if (soggetto.getMisura().getImporto() == null || soggetto.getMisura().getImporto().doubleValue() <= 0){
	                                errors.reject(Constants.MESSAGE);
	                                errors.rejectValue("misura.importo", "NotNull");
	                            }
	                            if (soggetto.getMisura().getImporto() != null) {
	                                if(soggetto.getMisura().getImporto().longValue() >= 10000000000000L)
	                                    errors.rejectValue("misura.importo", "importoNotValid");
	                            } 
	                        }  
	                    } 
	                }
	            }
		    }
		}
		//Se non sono nel Decisorio oppure sono nel Decisorio e la Decisione = ACCOGLIE
		// Prosegui con la Validazione seguente ...
		if(decisioneMisuraPers == null || decisioneMisuraPers.equals("28") || decisioneMisuraPers.equals("50") || decisioneMisuraPers.equals("51")
		        || decisioneMisuraPers.equals("52") || decisioneMisuraPers.equals("53")
		        || decisioneMisuraPers.equals("59") || decisioneMisuraPers.equals("60")
                || decisioneMisuraPers.equals("61") || decisioneMisuraPers.equals("62")){
		    if (((soggetto.getMisura().getAnni() != null) && (soggetto.getMisura().getAnni() < 0)) || ((soggetto.getMisura().getMesi() != null) && (soggetto.getMisura().getMesi() < 0)) || ((soggetto.getMisura().getGiorni() != null) && (soggetto.getMisura().getGiorni() < 0))) {
	            errors.reject(Constants.MESSAGE);
	            super.setMessage(Constants.MESSAGE, "soggetto.misurapersonale.durata.error.negativo", null, false);
	        }
		    if (soggetto.getTipologiaPrevenzione().getId().equals(Constants.NO_ITEM_SELECTED)) {
                errors.rejectValue("tipologiaPrevenzione.id", "NotNull");
            }
		    if (soggetto.getNormativa().getId().equals(Constants.NO_ITEM_SELECTED)) {
                errors.rejectValue("normativa.id", "NotNull");
            }
		    if (soggetto.getMisura().getTipoMisura().getId().equals(Constants.NO_ITEM_SELECTED)) {
                errors.rejectValue("misura.tipoMisura.id", "NotNull");
            }
            if (soggetto.getMisura().getNormativaDiRiferimento()==null || soggetto.getMisura().getNormativaDiRiferimento().getId().equals(Constants.NO_ITEM_SELECTED)) {
                errors.rejectValue("misura.normativaDiRiferimento.id", "NotNull");
            }
	        if (durataMisuraHasError(soggetto.getMisura())) {
	            errors.reject(Constants.MESSAGE);
	            super.setMessage(Constants.MESSAGE, "soggetto.misurapersonale.durata.error", "1;5", false);
	        }
	        if ((soggetto.getMisura() == null) || (soggetto.getMisura().getNormativaDiRiferimento() == null) || (soggetto.getMisura().getNormativaDiRiferimento().getId() == null)) {
	            errors.reject(Constants.MESSAGE);
	            super.setMessage(Constants.MESSAGE, "error.specificaretipoprevenzioneemisura", null, false);
	        } else {
	            boolean localitaObbligatoria = commonService.localitaObbligatoriaNelTipoMisuraSelezionato(Long.parseLong(soggetto.getMisura().getTipoMisura().getId()),"P");
	            if ((soggetto.getMisura().getLocalita() == null) && localitaObbligatoria) {
	                errors.reject(Constants.MESSAGE);
	                super.setMessage(Constants.MESSAGE, "error.soggetto.misurapersonale", null, false);
	            }
	        }
	        if ((soggetto.getMisura() == null) || (soggetto.getMisura().getArt67() == null)) {
	            errors.rejectValue("misura.art67", "NotNull");
	        }
	        if ((soggetto.getMisura() == null) || ((soggetto.getMisura().getIdSelezionati() != null) && !soggetto.getMisura().getIdSelezionati().isEmpty())) {
	            for (Long tmp : soggetto.getMisura().getIdSelezionati()) {
	                if ((tmp == 32) && (soggetto.getMisura().getUlterioriPrescrizioni() == null)) {
	                    errors.reject(Constants.MESSAGE);
	                    super.setMessage(Constants.MESSAGE, "error.soggetto.ulterioriPrescrizioni", null, false);
	                }
	            }
	        }
		}
	}

	public void validateTabellaDifensoriAssociati(DifensoreSoggettoForm difensoreSoggettoForm, Errors errors, HttpServletRequest request) {

		if ((difensoreSoggettoForm.getDifensoreForm() == null) || ((difensoreSoggettoForm.getDifensoreForm() != null) && (difensoreSoggettoForm.getDifensoreForm().getIdDifensore() == null))) {
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
		}
	}

	public void validateTabellaDifensori(DifensoreSoggettoForm difensoreSoggettoForm, Errors errors, HttpServletRequest request) {

		if ((difensoreSoggettoForm.getDifensoreForm() == null) || ((difensoreSoggettoForm.getDifensoreForm() != null) && ((difensoreSoggettoForm.getDifensoreForm().getIdReginde() == null) && (difensoreSoggettoForm.getDifensoreForm().getIdDifensore() == null)))) {
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
		}
	}

	public void validateTabellaErediAssociati(SoggettoForm soggettoForm, Errors errors, HttpServletRequest request) {

		if (soggettoForm.getEredeConviventeForm() == null) {
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
		}
	}

	private boolean durataMisuraHasError(MisuraForm misura) {

		boolean error = false;
		double anni = 0;
		double mesi = 0;
		double giorni = 0;
		if (misura.getAnni() != null) {
			anni = misura.getAnni();
		}
		if (misura.getMesi() != null) {
			mesi = misura.getMesi();
		}
		if (misura.getGiorni() != null) {
			giorni = misura.getGiorni();
		}
		double durata = (365.25 * anni) + (30.4375 * mesi) + giorni;
//*****sostituito perchè la durata della misura non può essere maggiore di 5 anni (e non 6 come era precedentemente)
//		
//		if (durata == 2191.5) {
//			durata = 2191.53;
//		}
//		// durata maggiore di 6 anni OPPURE durata < 1 anno
//		if ((durata > 2191.53) || (durata < 365.25)) {
//			error = true;
//		}
//***** fine vecchio codice
		
		// durata maggiore di 5 anni OPPURE durata < 1 anno
		if ((durata > 1826.25) || (durata < 365.25)) {
			error = true;
		}
		return error;
	}

	public void validateMisuraPersonalePerAggravamento(MisuraForm misura, MisuraForm misuraVecchia, Errors errors) {

		String tipoDecisione = misura.getDecisioneMisuraPersonale().getId();
		//Validazione solo se la Decisione è ACCOGLIE ...
		if(tipoDecisione.equals("50") || tipoDecisione.equals("51") || tipoDecisione.equals("52") || tipoDecisione.equals("53")
				 || tipoDecisione.equals("59") || tipoDecisione.equals("60")
                 || tipoDecisione.equals("61") || tipoDecisione.equals("62")){
		    validateMisuraPersonaleNonMigliorativa(misura, misuraVecchia, errors);
	        if (!errors.hasErrors()) {
	            boolean misuraCambiata = isMisuraModificata(misura, misuraVecchia);
	            if (!misuraCambiata) {
	                List<Message> customMessageList = new ArrayList<Message>();
	                super.addMessageIntoList(customMessageList, "soggetto.misurapersonale.aggravamento.error.invariata");
	                super.setMessages(customMessageList, true, errors);
	            }
	        } 
		}
	}

	private boolean isMisuraModificata(MisuraForm misura, MisuraForm misuraVecchia) {

		boolean durataCambiata = isDurataCambiata(misura, misuraVecchia);
		if (durataCambiata) {
			return true;
		}
		boolean art67Cambiato = isArt67Cambiato(misura, misuraVecchia);
		if (art67Cambiato) {
			return true;
		}
		boolean cauzioneCambiata = isCauzioneCambiata(misura, misuraVecchia);
		if (cauzioneCambiata) {
			return true;
		}
		boolean prescrizioniCambiate = isPrescrizioniCambiate(misura, misuraVecchia);
		if (prescrizioniCambiate) {
			return true;
		}
		boolean importoCambiato = isImportoCambiato(misura, misuraVecchia);
		if (importoCambiato) {
			return true;
		}
		boolean tipoMisuraCambiato = isTipoMisuraCambiato(misura, misuraVecchia);
		if (tipoMisuraCambiato) {
			return true;
		}
		boolean ulterioriPrescrizioniCambiate = isUlterioriPrescrizioniCambiate(misura, misuraVecchia);
		if (ulterioriPrescrizioniCambiate) {
			return true;
		}
		return false;
	}

	private boolean isUlterioriPrescrizioniCambiate(MisuraForm misura, MisuraForm misuraVecchia) {

		if (misura.getUlterioriPrescrizioni() == null) {
			return (misuraVecchia.getUlterioriPrescrizioni() == null) ? false : true;
		}
		if (misuraVecchia.getUlterioriPrescrizioni() == null) {
			return (misura.getUlterioriPrescrizioni() == null) ? false : true;
		}
		return !misura.getUlterioriPrescrizioni().trim().equalsIgnoreCase(misuraVecchia.getUlterioriPrescrizioni().trim());
	}

	private boolean isTipoMisuraCambiato(MisuraForm misura, MisuraForm misuraVecchia) {

		if (misura.getTipoMisura() == null) {
			return (misuraVecchia.getTipoMisura() == null) ? false : true;
		}
		if (misuraVecchia.getTipoMisura() == null) {
			return (misura.getTipoMisura() == null) ? false : true;
		}
		return !misura.getTipoMisura().equals(misuraVecchia.getTipoMisura());
	}

	private boolean isImportoCambiato(MisuraForm misura, MisuraForm misuraVecchia) {

		if (misura.getImporto() == null) {
			return (misuraVecchia.getImporto() == null) ? false : true;
		}
		if (misuraVecchia.getImporto() == null) {
			return (misura.getImporto() == null) ? false : true;
		}
		return !misura.getImporto().equals(misuraVecchia.getImporto());
	}

	private boolean isPrescrizioniCambiate(MisuraForm misura, MisuraForm misuraVecchia) {

		if (misura.getIdSelezionati() == null) {
			return (misuraVecchia.getIdSelezionati() == null) ? false : true;
		}
		if (misuraVecchia.getIdSelezionati() == null) {
			return (misura.getIdSelezionati() == null) ? false : true;
		}
		return !misura.getIdSelezionati().equals(misuraVecchia.getIdSelezionati());
	}

	private boolean isCauzioneCambiata(MisuraForm misura, MisuraForm misuraVecchia) {

		if (misura.getCauzione() == null) {
			return (misuraVecchia.getCauzione() == null) ? false : true;
		}
		if (misuraVecchia.getCauzione() == null) {
			return (misura.getCauzione() == null) ? false : true;
		}
		return !misura.getCauzione().equals(misuraVecchia.getCauzione());
	}

	private boolean isArt67Cambiato(MisuraForm misura, MisuraForm misuraVecchia) {

		if (misura.getArt67() == null) {
			return (misuraVecchia.getArt67() == null) ? false : true;
		}
		if (misuraVecchia.getArt67() == null) {
			return (misura.getArt67() == null) ? false : true;
		}
		return !misura.getArt67().equals(misuraVecchia.getArt67());
	}

	private boolean isDurataCambiata(MisuraForm misura, MisuraForm misuraVecchia) {

		int anniVecchi = (misuraVecchia.getAnni() != null) ? misuraVecchia.getAnni() : 0;
		int mesiVecchi = (misuraVecchia.getMesi() != null) ? misuraVecchia.getMesi() : 0;
		int giorniVecchi = (misuraVecchia.getGiorni() != null) ? misuraVecchia.getGiorni() : 0;
		Calendar fineMisuraVecchia = Calendar.getInstance();
		fineMisuraVecchia.add(Calendar.DAY_OF_MONTH, giorniVecchi);
		fineMisuraVecchia.add(Calendar.MONTH, mesiVecchi);
		fineMisuraVecchia.add(Calendar.YEAR, anniVecchi);
		azzeraCampiSottoIlGiorno(fineMisuraVecchia);
		int anni = (misura.getAnni() != null) ? misura.getAnni() : 0;
		int mesi = (misura.getMesi() != null) ? misura.getMesi() : 0;
		int giorni = (misura.getGiorni() != null) ? misura.getGiorni() : 0;
		Calendar fineMisura = Calendar.getInstance();
		fineMisura.add(Calendar.DAY_OF_MONTH, giorni);
		fineMisura.add(Calendar.MONTH, mesi);
		fineMisura.add(Calendar.YEAR, anni);
		azzeraCampiSottoIlGiorno(fineMisura);
		int compareResult = fineMisura.compareTo(fineMisuraVecchia);
		return compareResult != 0;
	}

	private void azzeraCampiSottoIlGiorno(Calendar calendar) {

		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	}

	public void validateMisuraPersonaleNonMigliorativa(final MisuraForm misura, final MisuraForm misuraVecchia, final Errors errors) {

		List<Message> customMessageList = new ArrayList<Message>();
		boolean durataMigliorativa = isDurataNuovaMinoreDellaVecchia(misura, misuraVecchia);
		if (durataMigliorativa) {
			super.addMessageIntoList(customMessageList, "soggetto.misurapersonale.durata.error.migliorativa");
		}
		boolean tipoMigliorativo = isTipoMisuraNuovaMiglioreDelVecchio(misura, misuraVecchia);
		if (tipoMigliorativo) {
			super.addMessageIntoList(customMessageList, "soggetto.misurapersonale.tipo.error.migliorativo");
		}
		boolean prescrizioniMigliorative = isInsiemePrescrizioniMiglioreDelVecchio(misura, misuraVecchia);
		if (prescrizioniMigliorative) {
			super.addMessageIntoList(customMessageList, "soggetto.misurapersonale.prescrizioni.error.migliorative");
		}
		boolean ulterioriPrescrizioniRimosse = isTestoUlterioriPrescrizioniRimosso(misura, misuraVecchia);
		if (ulterioriPrescrizioniRimosse) {
			super.addMessageIntoList(customMessageList, "soggetto.misurapersonale.prescrizioni.error.ulterioriRimosse");
		}
		boolean cauzioneRimossa = isCauzioneRimossa(misura, misuraVecchia);
		if (cauzioneRimossa) {
			super.addMessageIntoList(customMessageList, "soggetto.misurapersonale.cauzione.error.rimossa");
		} else{
		    boolean cauzioneMigliorativa = isImportoCauzioneMigliorativo(misura, misuraVecchia);
	        if (cauzioneMigliorativa) {
	            super.addMessageIntoList(customMessageList, "soggetto.misurapersonale.cauzione.error.importoMigliorativo");
	        }
		}
		boolean articolo67Rimosso = isArticolo67Rimosso(misura, misuraVecchia);
		if (articolo67Rimosso) {
			super.addMessageIntoList(customMessageList, "soggetto.misurapersonale.art67.error.rimosso");
		}
		super.setMessages(customMessageList, true, errors);
	}

	private boolean isDurataNuovaMinoreDellaVecchia(final MisuraForm misura, final MisuraForm misuraVecchia) {

		int anniVecchi = (misuraVecchia.getAnni() != null) ? misuraVecchia.getAnni() : 0;
		int mesiVecchi = (misuraVecchia.getMesi() != null) ? misuraVecchia.getMesi() : 0;
		int giorniVecchi = (misuraVecchia.getGiorni() != null) ? misuraVecchia.getGiorni() : 0;
		Calendar fineMisuraVecchia = Calendar.getInstance();
		fineMisuraVecchia.add(Calendar.DAY_OF_MONTH, giorniVecchi);
		fineMisuraVecchia.add(Calendar.MONTH, mesiVecchi);
		fineMisuraVecchia.add(Calendar.YEAR, anniVecchi);
		int anni = (misura.getAnni() != null) ? misura.getAnni() : 0;
		int mesi = (misura.getMesi() != null) ? misura.getMesi() : 0;
		int giorni = (misura.getGiorni() != null) ? misura.getGiorni() : 0;
		Calendar fineMisura = Calendar.getInstance();
		fineMisura.add(Calendar.DAY_OF_MONTH, giorni);
		fineMisura.add(Calendar.MONTH, mesi);
		fineMisura.add(Calendar.YEAR, anni);
		return fineMisura.before(fineMisuraVecchia);
	}

	private boolean isTipoMisuraNuovaMiglioreDelVecchio(final MisuraForm misura, final MisuraForm misuraVecchia) {

		ComboForm tipoVecchio = misuraVecchia.getTipoMisura();
		ComboForm tipo = misura.getTipoMisura();
		if (Utility.isDefinito(tipoVecchio)) {
			long idTipoVecchio = Long.valueOf(tipoVecchio.getId());
			long idTipo = Long.valueOf(tipo.getId());
			return (idTipo < idTipoVecchio);
		}
		return false;
	}

	private boolean isInsiemePrescrizioniMiglioreDelVecchio(final MisuraForm misura, final MisuraForm misuraVecchia) {

		List<Long> listaPrescrizioniVecchie = misuraVecchia.getIdSelezionati();
		List<Long> listaPrescrizioniNuove = misura.getIdSelezionati();
		for (Long tmp : listaPrescrizioniVecchie) {
			if (!listaPrescrizioniNuove.contains(tmp)) {
				return true;
			}
		}
		return false;
	}

	private boolean isTestoUlterioriPrescrizioniRimosso(final MisuraForm misura, final MisuraForm misuraVecchia) {

		String testoVecchio = misuraVecchia.getUlterioriPrescrizioni();
		String testo = misura.getUlterioriPrescrizioni();
		return (Utility.isDefinito(testoVecchio)) ? ((Utility.isDefinito(testo)) ? false : true) : false;
	}

	private boolean isCauzioneRimossa(final MisuraForm misura, final MisuraForm misuraVecchia) {

		return (misuraVecchia.getCauzione().equals("S")) ? (misura.getCauzione().equals("S") ? false : true) : false;
	}

	private boolean isArticolo67Rimosso(final MisuraForm misura, final MisuraForm misuraVecchia) {

		return (misuraVecchia.getArt67().equals("S")) ? (misura.getArt67().equals("S") ? false : true) : false;
	}

	private boolean isImportoCauzioneMigliorativo(final MisuraForm misura, final MisuraForm misuraVecchia) {

		if (misuraVecchia.getImporto() != null) {
	            return misura.getImporto().doubleValue() < misuraVecchia.getImporto().doubleValue();
	   }
		return false;
	}

	private void controllaCoerenzaAnnoRegistro(DifensoreSoggettoForm difensore, List<Message> customMessageList) {

		if (difensore.getSoggettoForm() != null && difensore.getSoggettoForm().getAnnoRegistroGratuitoPatrocinio() != null) {
			int annoRegistro = difensore.getSoggettoForm().getAnnoRegistroGratuitoPatrocinio();
			if (annoRegistro < 1900) {
				super.addMessageIntoList(customMessageList, "soggetto.difensore.gratuitoPatrocinio.error.annoRegistro.tooOld");
			}
			Calendar calendar = Calendar.getInstance();
			int annoCorrente = calendar.get(Calendar.YEAR);
			if (annoRegistro > annoCorrente) {
				super.addMessageIntoList(customMessageList, "soggetto.difensore.gratuitoPatrocinio.error.annoRegistro.tooNew");
			}
		}
	}

	public void validateAbilitazioneEliminazioneMisuraPersonale(Errors errors) {

		ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute(Constants.ID_PROCEDIMENTO);
		String tipoUtente = (String) request.getSession().getAttribute(Constants.TIPO_UTENTE);
		String idStatoProcedimentoConsentito = null;
		if (tipoUtente.equals(Constants.TIPO_UTENTE_SMP)) {
			// se è un utente della procura
			idStatoProcedimentoConsentito = Constants.STATOPROC_ISTRUTTORIA;
		} else
			if (tipoUtente.equals(Constants.TIPO_UTENTE_SMP_GIU)) {
				// se è un utente del tribunale
				idStatoProcedimentoConsentito = Constants.STATOPROC_IN_ACQUISIZ_PROPOSTA;
			}
		if (procedimento.getStatoProcedimento() != null && procedimento.getStatoProcedimento().getId() != null && !procedimento.getStatoProcedimento().getId().equals(idStatoProcedimentoConsentito)) {
			List<Message> customMessageList = new ArrayList<Message>();
			super.addMessageIntoList(customMessageList, "soggetto.misuraPersonale.elimina.error.statoProcedimento");
			super.setMessages(customMessageList, true, errors);
		}
	}

    public void validaSceltaTipoPrevenzioneNormativa(SoggettoForm soggetto, Errors result) {

        List<Message> customMessageList = new ArrayList<Message>();
        if(soggetto.getTipologiaPrevenzione()==null || soggetto.getTipologiaPrevenzione().getId() ==null  || soggetto.getTipologiaPrevenzione().getId().equals(Constants.NO_ITEM_SELECTED)){
            result.reject(Constants.MESSAGE);
            super.addMessageIntoList(customMessageList, Constants.MESSAGE, "error.soggetto.tipologiaPrevenzione.obbligatoria", null);
        }
        if(soggetto.getNormativa()==null || soggetto.getNormativa().getId() ==null || soggetto.getNormativa().getId().equals(Constants.NO_ITEM_SELECTED)){
            result.reject(Constants.MESSAGE);
            super.addMessageIntoList(customMessageList, Constants.MESSAGE, "error.soggetto.normativa.obbligatoria", null);
        }
        
        super.setMessages(customMessageList, true, result);
    }
    
    
    
    
    public void validaSoggettoDaAnnullare(SoggettoForm soggettoValido, SoggettoForm soggettoDuplicato, Errors errors) {

        List<Message> customMessageList = new ArrayList<Message>();
        
        
        
        if(soggettoValido.getTipoSoggetto().equalsIgnoreCase(soggettoDuplicato.getTipoSoggetto())){
        	//persona fisica - Cognome-Nome-Data Nascita-Stato-Comune
        	if(soggettoValido.getTipoSoggetto().equalsIgnoreCase(Constants.SOGGETTO_FISICO)){
        		if((soggettoValido.getCognome()!=null && soggettoDuplicato.getCognome()==null) || (soggettoValido.getCognome()==null && soggettoDuplicato.getCognome()!=null)){
        			errors.rejectValue("cognome", "errore.soggetto.annullabile.campo");
        		}else if((soggettoValido.getCognome()!=null && soggettoDuplicato.getCognome()!=null) && !soggettoValido.getCognome().equalsIgnoreCase(soggettoDuplicato.getCognome())){
        			errors.rejectValue("cognome", "errore.soggetto.annullabile.campo");
        		}
        		
        		if((soggettoValido.getNome()!=null && soggettoDuplicato.getNome()==null) || (soggettoValido.getNome()==null && soggettoDuplicato.getNome()!=null)){
        			errors.rejectValue("nome", "errore.soggetto.annullabile.campo");
        		}else if((soggettoValido.getNome()!=null && soggettoDuplicato.getNome()!=null) && !soggettoValido.getNome().equalsIgnoreCase(soggettoDuplicato.getNome())){
        			errors.rejectValue("nome", "errore.soggetto.annullabile.campo");
        		}
        		
        		if((soggettoValido.getDataNascita()!=null && soggettoDuplicato.getDataNascita()==null) || (soggettoValido.getDataNascita()==null && soggettoDuplicato.getDataNascita()!=null)){
        			errors.rejectValue("dataNascita", "errore.soggetto.annullabile.campo");
        		}else if((soggettoValido.getDataNascita()!=null && soggettoDuplicato.getDataNascita()!=null) && !soggettoValido.getDataNascita().toString().equalsIgnoreCase(soggettoDuplicato.getDataNascita().toString())){
        			errors.rejectValue("dataNascita", "errore.soggetto.annullabile.campo");
        		}
        		
        		
        		if((soggettoValido.getStatoNascita()!=null && soggettoDuplicato.getStatoNascita()==null) || (soggettoValido.getStatoNascita()==null && soggettoDuplicato.getStatoNascita()!=null)){
        			errors.rejectValue("statoNascita.descrizione", "errore.soggetto.annullabile.campo");
        		}else if((soggettoValido.getStatoNascita()!=null && soggettoDuplicato.getStatoNascita()!=null) && !soggettoValido.getStatoNascita().getDescrizione().equalsIgnoreCase(soggettoDuplicato.getStatoNascita().getDescrizione())){
        			errors.rejectValue("statoNascita.descrizione", "errore.soggetto.annullabile.campo");
        		}
        		
        		if((soggettoValido.getDescrizioneComuneNascitaItalianoEstero()!=null && soggettoDuplicato.getDescrizioneComuneNascitaItalianoEstero()==null) || (soggettoValido.getDescrizioneComuneNascitaItalianoEstero()==null && soggettoDuplicato.getDescrizioneComuneNascitaItalianoEstero()!=null)){
        			errors.rejectValue("descrizioneComuneNascitaItalianoEstero", "errore.soggetto.annullabile.campo");
        		}else if((soggettoValido.getDescrizioneComuneNascitaItalianoEstero()!=null && soggettoDuplicato.getDescrizioneComuneNascitaItalianoEstero()!=null) &&  !soggettoValido.getDescrizioneComuneNascitaItalianoEstero().equalsIgnoreCase(soggettoDuplicato.getDescrizioneComuneNascitaItalianoEstero())){
        			errors.rejectValue("descrizioneComuneNascitaItalianoEstero", "errore.soggetto.annullabile.campo");
        		}
        	}

        	//persona giuridica - Tipo Società- Ragione Sociale (PIVA senza controllo)
        	if(soggettoValido.getTipoSoggetto().equalsIgnoreCase(Constants.SOGGETTO_GIURIDICO)){
        		if((soggettoValido.getTipoSocieta()!=null && soggettoDuplicato.getTipoSocieta()==null) || (soggettoValido.getTipoSocieta()==null && soggettoDuplicato.getTipoSocieta()!=null)){
        			errors.rejectValue("tipoSocieta", "errore.soggetto.annullabile.campo");
        		}else if((soggettoValido.getTipoSocieta()!=null && soggettoDuplicato.getTipoSocieta()!=null) && !soggettoValido.getTipoSocieta().equalsIgnoreCase(soggettoDuplicato.getTipoSocieta())){
					errors.rejectValue("tipoSocieta", "errore.soggetto.annullabile.campo");			
				}

        		if((soggettoValido.getRagioneSociale()!=null && soggettoDuplicato.getRagioneSociale()==null) || (soggettoValido.getRagioneSociale()==null && soggettoDuplicato.getRagioneSociale()!=null)){
        			errors.rejectValue("ragioneSociale", "errore.soggetto.annullabile.campo");
        		}else if((soggettoValido.getRagioneSociale()!=null && soggettoDuplicato.getRagioneSociale()!=null) && !soggettoValido.getRagioneSociale().equalsIgnoreCase(soggettoDuplicato.getRagioneSociale())){
					errors.rejectValue("ragioneSociale", "errore.soggetto.annullabile.campo");
				}
        		
        	}
        	
       	
        }else{
        	 errors.reject(Constants.MESSAGE);
             super.addMessageIntoList(customMessageList, Constants.MESSAGE, "errore.soggetto.tiposoggetto", null);
        }
        
        super.setMessages(customMessageList, true, errors);
    }
    
    
	public void validateSalvaMisuraPersonaleIIGrado(SoggettoForm soggetto, Errors errors, HttpServletRequest request) {

		    if(soggetto.getMisura() != null)
		    {
			         if (soggetto.getMisura().getDecisioneMisuraPersonaleIIGrado()!= null &&
			        		soggetto.getMisura().getDecisioneMisuraPersonaleIIGrado().getId().equals(Constants.NO_ITEM_SELECTED)) 
			           {
			                errors.reject(Constants.MESSAGE);
			                super.setMessage(Constants.MESSAGE, "error.misuraPersonale.decisione", null, false);
			           }
	
		                if (soggetto.getMisura().getCauzione()!=null && soggetto.getMisura().getCauzione().equalsIgnoreCase("S"))
		                {
		                    if (soggetto.getMisura().getImporto() == null || soggetto.getMisura().getImporto().doubleValue() <= 0)
		                    {
		                        errors.reject(Constants.MESSAGE);
		                        errors.rejectValue("misura.importo", "NotNull");
		                    }
		                    else if (soggetto.getMisura().getImporto() != null && soggetto.getMisura().getImporto().longValue() >= 10000000000000L)
		                    {
		                            errors.rejectValue("misura.importo", "importoNotValid");
		                    } 
		                }  
		            
		     }
				


	   
			if (((soggetto.getMisura().getAnni() != null) && (soggetto.getMisura().getAnni() < 0)) || ((soggetto.getMisura().getMesi() != null) && (soggetto.getMisura().getMesi() < 0)) 
				|| ((soggetto.getMisura().getGiorni() != null) && (soggetto.getMisura().getGiorni() < 0))) 
			{
	            errors.reject(Constants.MESSAGE);
	            super.setMessage(Constants.MESSAGE, "soggetto.misurapersonale.durata.error.negativo", null, false);
	        }
			
		    if (soggetto.getTipologiaPrevenzione()!= null && soggetto.getTipologiaPrevenzione().getId().equals(Constants.NO_ITEM_SELECTED)) 
		    {
                errors.rejectValue("tipologiaPrevenzione.id", "NotNull");
            }
		    
		    if (soggetto.getNormativa()!= null  && soggetto.getNormativa().getId().equals(Constants.NO_ITEM_SELECTED))
		    {
                errors.rejectValue("normativa.id", "NotNull");
            }
		    
		    if (soggetto.getMisura().getTipoMisura()!= null && soggetto.getMisura().getTipoMisura().getId().equals(Constants.NO_ITEM_SELECTED)) 
		    {
                errors.rejectValue("misura.tipoMisura.id", "NotNull");
            }
		    
            if (soggetto.getMisura().getNormativaDiRiferimento()!=null && soggetto.getMisura().getNormativaDiRiferimento().getId().equals(Constants.NO_ITEM_SELECTED)) 
            {
                errors.rejectValue("misura.normativaDiRiferimento.id", "NotNull");
            }
            
	        if ((soggetto.getMisura().getAnni() != null) && (durataMisuraHasError(soggetto.getMisura()))) 
	        {
	            errors.reject(Constants.MESSAGE);
	            super.setMessage(Constants.MESSAGE, "soggetto.misurapersonale.durata.error", "1;5", false);
	        }
	        if (soggetto.getVisibilitaIIGrado().equals("S")){
	       
	       if ((soggetto.getMisura() == null) || (soggetto.getMisura().getNormativaDiRiferimento() == null) || (soggetto.getMisura().getNormativaDiRiferimento().getId() == null)) 
	        {
	            errors.reject(Constants.MESSAGE);
	            super.setMessage(Constants.MESSAGE, "error.specificaretipoprevenzioneemisura", null, false);
	        } else
	        {
	            boolean localitaObbligatoria = commonService.localitaObbligatoriaNelTipoMisuraSelezionato(Long.parseLong(soggetto.getMisura().getTipoMisura().getId()),"P");
	            if ((soggetto.getMisura().getLocalita() == null) && localitaObbligatoria)
	            {
	                errors.reject(Constants.MESSAGE);
	                super.setMessage(Constants.MESSAGE, "error.soggetto.misurapersonale", null, false);
	            }
	        }
	      
	       if ((soggetto.getMisura() == null) || ((soggetto.getMisura().getIdSelezionati() != null) && !soggetto.getMisura().getIdSelezionati().isEmpty())) {
	            for (Long tmp : soggetto.getMisura().getIdSelezionati()) {
	                if ((tmp == 32) && (soggetto.getMisura().getUlterioriPrescrizioni() == null)) 
	                {
	                    errors.reject(Constants.MESSAGE);
	                    super.setMessage(Constants.MESSAGE, "error.soggetto.ulterioriPrescrizioni", null, false);
	                }
	            }
	        }
	        
	        }
	        
	   /*     if ((soggetto.getMisura() == null) || (soggetto.getMisura().getArt67() == null))
	        {
	            errors.rejectValue("misura.art67", "NotNull");
	        }
	        */
	       
		        
		
	}    
    
	
	public void validateSalvaMisuraPersonaleCauzPermIIGrado(SoggettoForm soggetto, Errors errors, HttpServletRequest request) {

	    if(soggetto.getMisura() != null)
	    {
		         if (soggetto.getMisura().getDecisioneMisuraPersonaleIIGrado()!= null &&
		        		soggetto.getMisura().getDecisioneMisuraPersonaleIIGrado().getId().equals(Constants.NO_ITEM_SELECTED)) 
		           {
		                errors.reject(Constants.MESSAGE);
		                super.setMessage(Constants.MESSAGE, "error.misuraPersonale.decisione", null, false);
		           }

	                if (soggetto.getMisura().getCauzione()!=null && soggetto.getMisura().getCauzione().equalsIgnoreCase("S"))
	                {
	                    if (soggetto.getMisura().getImporto() == null || soggetto.getMisura().getImporto().doubleValue() <= 0)
	                    {
	                        errors.reject(Constants.MESSAGE);
	                        errors.rejectValue("misura.importo", "NotNull");
	                    }
	                    else if (soggetto.getMisura().getImporto() != null && soggetto.getMisura().getImporto().longValue() >= 10000000000000L)
	                    {
	                            errors.rejectValue("misura.importo", "importoNotValid");
	                    } 
	                }  
	            
	     }
			


   
		
   /*     if ((soggetto.getMisura() == null) || (soggetto.getMisura().getArt67() == null))
        {
            errors.rejectValue("misura.art67", "NotNull");
        }
        */
       
	        
	
}    
	public void validateSalvaMisuraPersonaleIIIGrado(SoggettoForm soggetto, Errors errors, HttpServletRequest request) {

	    if(soggetto.getMisura() != null)
	    {    	
	         if (soggetto.getMisura().getDecisioneMisuraPersonaleSentenzaCassazione()!= null &&
	        		soggetto.getMisura().getDecisioneMisuraPersonaleSentenzaCassazione().getId().equals(Constants.NO_ITEM_SELECTED)) 
	           {
	                errors.reject(Constants.MESSAGE);
	                super.setMessage(Constants.MESSAGE, "error.misuraPersonale.decisione", null, false);
	           }
	     }
			  
	    if(soggetto.getNumeAnnoSentenza() == null || "".equalsIgnoreCase(soggetto.getNumeAnnoSentenza())){
			errors.rejectValue("numeAnnoSentenza", "NotNull");
	    }
	    
	    if(soggetto.getNumeSentenza() == null || "".equalsIgnoreCase(soggetto.getNumeSentenza())){
			errors.rejectValue("numeSentenza", "NotNull");
	    }
	    
		if (soggetto.getDataRicezioneSentenza() == null) {
			errors.rejectValue("dataRicezioneSentenza", "NotNull");
		}

	}   	
	
    public boolean validateAbilitazioneSoggettiDuplicati(ProcedimentoForm procedimento, HttpServletRequest request) {
    	//In tribunale invece la voce deve essere attiva quando il fascicolo assume gli stati:
    	//2 – ISCRITTO
    	//6 – TRATTAZIONE IN I GRADO 
		if ((procedimento != null) && (procedimento.getStatoProcedimento() != null) 
				&& (procedimento.getStatoProcedimento().getId().equals(Constants.STATOPROC_ISCRITTO) || procedimento.getStatoProcedimento().getId().equals(Constants.STATOPROC_TRATTAZIONE_I_GRADO))) {
			return true;
		}else{
			super.setMessage(Constants.MESSAGE, "operazione.no.possibile.statononconforme", null, true);
			return false;
		}

	}

	public void validateCheckBoxEliminaAssociazioneIntervenienti(SoggettoForm soggetto, Errors errors, HttpServletRequest request) {
		if (soggetto.getIdSoggetto() == null) {
			super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
			errors.reject(Constants.MESSAGE);
		}
	}
	
	public void validateSalvaSentenzaOppDivietiQuestore(SoggettoForm soggetto, Errors errors, HttpServletRequest request) {

	     if(soggetto.getNumeAnnoSentenza() == null || "".equalsIgnoreCase(soggetto.getNumeAnnoSentenza())){
			errors.rejectValue("numeAnnoSentenza", "NotNull");
	    }
	    
	    if(soggetto.getNumeSentenza() == null || "".equalsIgnoreCase(soggetto.getNumeSentenza())){
			errors.rejectValue("numeSentenza", "NotNull");
	    }
	    
		if (soggetto.getDataRicezioneSentenza() == null) {
			errors.rejectValue("dataRicezioneSentenza", "NotNull");
		}

	}
	
	public void validateSalvaDifensoreAssociaNuovo(DifensoreSoggettoForm difensoreSoggettoForm, Errors errors) {

		if (difensoreSoggettoForm.getDifensoreForm() != null) {
			if ((difensoreSoggettoForm.getTipoNomina() == null) || ((difensoreSoggettoForm.getTipoNomina() != null) && difensoreSoggettoForm.getTipoNomina().getId().equals("-1"))) {
				errors.rejectValue("tipoNomina.id", "NotNull");
			}
	        if (difensoreSoggettoForm.getDataNomina() == null) {
				errors.rejectValue("dataNomina", "NotNull");
				return;
			} else
				if (difensoreSoggettoForm.getDataNomina().compareTo(new Date()) > 0) {
					errors.rejectValue("dataNomina", "Past");
					return;
				}
			if ((difensoreSoggettoForm.getDataRevoca() != null) && (difensoreSoggettoForm.getDataRevoca().compareTo(difensoreSoggettoForm.getDataNomina()) < 0)) {
				errors.rejectValue("dataRevoca", "soggetto.difensore.error.dataRevoca");
			}
		}
	}
	//F.C.: MAC metodo che verifica i ruoli dei soggetti da annullare: per annullare un soggetto i ruoli devono essere uguali
	public void validaRuoloSoggettiDaAnnullare(SoggettoForm soggettoForm, Errors errors) {
    	List<Message> customMessageList = new ArrayList<Message>();
    	
    	if (soggettoForm.getDescrRuolo() == null || soggettoForm.getDescrRuolo() == "") {
    		errors.reject(Constants.MESSAGE);
            super.addMessageIntoList(customMessageList, Constants.MESSAGE, "errore.soggetto.ruolo.non.presente", null);
            super.setMessages(customMessageList, true, errors);
            return;
    	}
    	
    	if (soggettoForm.getDescrRuoloDuplicato() == null || soggettoForm.getDescrRuoloDuplicato() == "") {
    		errors.reject(Constants.MESSAGE);
            super.addMessageIntoList(customMessageList, Constants.MESSAGE, "errore.soggetto.ruolo.non.presente", null);
            super.setMessages(customMessageList, true, errors);
            return;
    	}
    	
    	if (soggettoForm.getDescrRuolo().compareTo(soggettoForm.getDescrRuoloDuplicato()) != 0) {
    		errors.reject(Constants.MESSAGE);
            super.addMessageIntoList(customMessageList, Constants.MESSAGE, "errore.soggetto.ruolosoggetto", null);
            super.setMessages(customMessageList, true, errors);
            return;
    	}
		// TODO Auto-generated method stub
		
	}
	//F.C.: MAC metodo che verifica se il procedimento è stato registrato dalla Procura Generale e dall'Ufficio della Procura:
	//Per annullare un soggetto il procedimento deve essere stato prima acquisito.
	public void validaRegistrazioneProcedimentoSoggetto(ProcedimentoForm procForm, SoggettoForm soggettoForm, Errors errors) {
		List<Message> customMessageList = new ArrayList<Message>();
		boolean ret = false;
		List l = commonService.getNumeProcTRelProcProc(procForm.getIdProcedimento(),Constants.TIPO_UFFICIO_PROCURA_GENERALE);
		if(l == null || (l != null && l.isEmpty())){			
			errors.reject(Constants.MESSAGE);
            super.addMessageIntoList(customMessageList, Constants.MESSAGE, "error.procedimento.non.acquisito", null);
            super.setMessages(customMessageList, true, errors);
            return;
		}
		
		l = commonService.getNumeProcTRelProcProc(procForm.getIdProcedimento(),Constants.TIPO_UFFICIO_PROCURA_L);
		if(l == null || (l != null && l.isEmpty())){
			errors.reject(Constants.MESSAGE);
            super.addMessageIntoList(customMessageList, Constants.MESSAGE, "error.procedimento.non.acquisito.PU", null);
            super.setMessages(customMessageList, true, errors);
            return;
		}
		
	}
}
