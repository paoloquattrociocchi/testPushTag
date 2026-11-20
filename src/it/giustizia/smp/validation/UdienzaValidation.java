package it.giustizia.smp.validation;

import it.giustizia.smp.controller.procedimento.AttoController;
import it.giustizia.smp.form.MagistratoForm;
import it.giustizia.smp.form.UdienzaForm;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Message;
import it.giustizia.smp.utility.Utility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("udienzaValidation")
public class UdienzaValidation extends BaseValidation implements Validator {

      public boolean supports(Class<?> clazz) {

        return AttoController.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {

    }

    public void validateGestioneMagistratiCollegio(MagistratoForm magistrato, List<MagistratoForm> magistratiAssociati, Errors errors, HttpServletRequest request, boolean operazioneDiInserimento) {

        // Se arrivo da NuovoMag --> magistrato = null e operazioneDiInserimento
        // = true
        // Se arrivo da Associa e ConfModificaMag --> magistrato != null e
        // operazioneDiInserimento = false
        int numeroTotaleMagistrati = 0;
        boolean esistePresidente = false;
        // Verifico se esiste il PRESIDENTE nel collegio
        if (Utility.isDefinito(magistratiAssociati)) {
            for (MagistratoForm tmp : magistratiAssociati) {
                numeroTotaleMagistrati++;
                if (tmp.getRuolo() != null) {
                    if (tmp.getRuolo().getDescrizione().equalsIgnoreCase("PRESIDENTE")) {
                        esistePresidente = true;
                    }
                }
            }
        }
        if (operazioneDiInserimento) {// Inserimento Magistrato
            if (magistratiAssociati.size() > 2) {
                errors.reject(Constants.MESSAGE);
                super.setMessage(Constants.MESSAGE, "magistrato.error.collegio.massimoGiudiciSuperato", null, false);
                return;
            }
        } else {// ConfModificaMag o Associa Magistrato
            if (magistrato.getRuolo() == null) {
                errors.reject(Constants.MESSAGE);
                super.setMessage(Constants.MESSAGE, "magistrato.error.ruoloObbligatorio", null, false);
                return;
            }
            if (!Utility.isDefinito(magistrato.getRuolo())) {
                errors.reject(Constants.MESSAGE);
                super.setMessage(Constants.MESSAGE, "magistrato.error.ruoloObbligatorio", null, false);
                return;
            }
            if (esistePresidente && magistrato.getRuolo().getId().equalsIgnoreCase("2")) {
                errors.reject(Constants.MESSAGE);
                super.setMessage(Constants.MESSAGE, "magistrato.error.collegio.esistePresidente", null, false);
                return;
            } else
                if (magistratiAssociati.size() == 2) {
                    if (!esistePresidente && !magistrato.getRuolo().getId().equalsIgnoreCase("2")) {
                        errors.reject(Constants.MESSAGE);
                        super.setMessage(Constants.MESSAGE, "magistrato.error.collegio.nonEsistePresidente", null, false);
                        return;
                    }
                }
        }
    }

    public void validateListaUdienze(UdienzaForm udienza, HttpServletRequest request, BindingResult result) {

        if (udienza.getCodUdienza() == 0) {
            result.reject(Constants.MESSAGE);
            super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
        }
    }

    public boolean validateModificaRuoloMagistrato(MagistratoForm magForm, List<MagistratoForm> magistratiAssociati, BindingResult result, HttpServletRequest request) {

        // Se ho selezionato un PRESIDENTE
//        if (magForm.getRuolo().equalsIgnoreCase("PRESIDENTE")) {
        if ((magForm.getRuolo() != null) && Constants.ID_TIPO_RUOLO_PRES.equalsIgnoreCase(magForm.getRuolo().getId())) {
            super.setMessage(Constants.MESSAGE, "udienza.selected.modifica.mag.presidente", null, false);
            return true;
        }
        // Se ho selezionato un GIUDICE A LATERE
        else {
            // Controllo se esiste un PRESIDENTE nel collegio
            boolean esistePresidente = false;
            if (Utility.isDefinito(magistratiAssociati)) {
                for (MagistratoForm tmp : magistratiAssociati) {
                    if (tmp.getRuolo().getDescrizione().equalsIgnoreCase("PRESIDENTE")) {
                        esistePresidente = true;
                    }
                }
            }
            // Se esiste già un PRESIDENTE non posso modificare il ruolo del
            // GIUDICE A LATERE
            if (esistePresidente) {
                super.setMessage(Constants.MESSAGE, "magistrato.error.collegio.esistePresidente", null, false);
                return true;
            } else {
                return false;
            }
        }
    }

    public void validateModificaUdienza(UdienzaForm udienza, HttpServletRequest request, BindingResult result) {

        boolean ecc = false;
        if (udienza.getStato().getId() != null) {
            if (!udienza.getStato().getId().equals("1") && !udienza.getStato().getId().equals("13")) {
                ecc = true;
            }
        } else {
            ecc = true;
        }
        if (ecc) {
            result.reject(Constants.MESSAGE);
            super.setMessage(Constants.MESSAGE, "udienza.selected.modifica", null, false);
        }
        return;
    }

    public void validateSceltaEliminaPM(MagistratoForm form, Errors errors, HttpServletRequest request) {

        if ((form == null) || (form.getId() == null)) {
            errors.reject(Constants.MESSAGE);
            super.setMessage(Constants.MESSAGE, "udienza.selected.elimina.pm", null, false);
        }
        return;
    }

    public void validateSceltaPm(UdienzaForm udienzaForm, Errors errors, HttpServletRequest request) {

        if (udienzaForm.getListaPm() != null) {
            if (udienzaForm.getListaPm().getId().equals("-1")) {
                errors.reject(Constants.MESSAGE);
                super.setMessage(Constants.MESSAGE, "udienza.selected.aggiunta.pm", null, false);
            }
        }
        return;
    }

    public void validateSceltaRadioButtonMagistrato(MagistratoForm form, Errors errors, HttpServletRequest request, String tipologia) {

        if ((form == null) || (form.getId() == null)) {
            errors.reject(Constants.MESSAGE);
            if (tipologia.equals("Associa")) {
                super.setMessage(Constants.MESSAGE, "udienza.selected.aggiunta.mag", null, false);
            } else
                if (tipologia.equals("Modifica")) {
                    super.setMessage(Constants.MESSAGE, "udienza.selected.modifica.mag", null, false);
                } else
                    if (tipologia.equals("Elimina")) {
                        super.setMessage(Constants.MESSAGE, "udienza.selected.elimina.mag", null, false);
                    }
        }
        return;
    }

    public boolean validaUdienza(UdienzaForm udienzaForm, Errors errors, HttpServletRequest request) {

        boolean errore = false;
        List<Message> customMessageList = new ArrayList<Message>();
        // La data di Fine Udienza al momento del conferma e invia è
        // obbligatoria
        if (udienzaForm.getOraFineUdienza().getId().equals("-1") || udienzaForm.getMinutiFineUdienza().getId().equals("-1")) {
            super.addMessageIntoList(customMessageList, Constants.MESSAGE, "udienza.oraFine.obbligatoria", null);
        }
     // L'ora di Fine Udienza deve essere maggiore dell'ora Inizio 
        if (!udienzaForm.getOraFineUdienza().getId().equals("-1")) {
        	if (Long.valueOf(udienzaForm.getOraFineUdienza().getId()) < Long.valueOf(udienzaForm.getOraInizioUdienza().getId())) {
        		super.addMessageIntoList(customMessageList, Constants.MESSAGE, "udienza.error.oraFine", null);
         	}else if (Long.valueOf(udienzaForm.getOraFineUdienza().getId()) == Long.valueOf(udienzaForm.getOraInizioUdienza().getId())) {
        		if (Long.valueOf(udienzaForm.getMinutiFineUdienza().getId()) < Long.valueOf(udienzaForm.getMinutiInizioUdienza().getId())) {
        			super.addMessageIntoList(customMessageList, Constants.MESSAGE, "udienza.error.oraFine", null);		
        		}
        	}
        }

		/**
		 * controllo bypassato 
		 */

        // Controllo sui Flag di Presenza dei SoggettiConvocati e dei Difensori
		// List<String> flgSogConv = udienzaForm.getAllFlgPresSogConv();
		// List<String> flgDif = udienzaForm.getAllFlgPresDif();
		// for (int i = 0; i < flgSogConv.size(); i++) {
		// String flagSc = flgSogConv.get(i);
		// if (flagSc.equals("")) {
		// super.addMessageIntoList(customMessageList, Constants.MESSAGE,
		// "udienza.soggetticonv.flgpres.obbligatori", null);
		// break;
		// }
		// }
		// for (int i = 0; i < flgDif.size(); i++) {
		// String flagD = flgDif.get(i);
		// if (flagD.equals("")) {
		// super.addMessageIntoList(customMessageList, Constants.MESSAGE,
		// "udienza.difensori.flgpres.obbligatori", null);
		// break;
		// }
		// }

		// Fine Controllo sui Flag di Presenza dei SoggettiConvocati e dei
		// Difensori

        // Se esito è Rinvio la data di Rinvio e l'ora Udienza sono obbligatori
        if (!udienzaForm.getEsito().getId().equals("10")) {
            if (udienzaForm.getDataRinvio() == null) {
                super.addMessageIntoList(customMessageList, Constants.MESSAGE, "udienza.rinvio.data.obbligatoria", null);
            } else if(udienzaForm.getDataRinvio().before(udienzaForm.getDataUdienza())){
            	super.addMessageIntoList(customMessageList, Constants.MESSAGE, "futureWithParam", "'Data Rinvio';'Data Udienza'");
            }
            if (udienzaForm.getOraUdienzaRinvio().getId().equals("-1") || udienzaForm.getMinutiUdienzaRinvio().getId().equals("-1")) {
                super.addMessageIntoList(customMessageList, Constants.MESSAGE, "udienza.rinvio.ora.obbligatoria", null);
            }
        }
        
        List<MagistratoForm> collegioUdienza = (List<MagistratoForm>) request.getSession().getAttribute("collegio_Udienza");
        Iterator<MagistratoForm> iteratore = collegioUdienza.iterator();
        boolean senzaPresidente = true;
        while(iteratore.hasNext()) {
        	MagistratoForm magistrato = iteratore.next();
        	if ((magistrato.getRuolo() != null) && Constants.ID_TIPO_RUOLO_PRES.equalsIgnoreCase(magistrato.getRuolo().getId())) {
//        	if("PRESIDENTE".equalsIgnoreCase(magistrato.getRuolo())) {
        		senzaPresidente = false;
        	}
        }
        if(senzaPresidente)
        	super.addMessageIntoList(customMessageList, Constants.MESSAGE, "magistrato.error.collegio.nonEsistePresidente", null);
        
        
        super.setMessages(customMessageList, false);
        if (customMessageList.size() > 0) {
            errore = true;
        }
        return errore;
    }
    
    public void validaUdienzaOre(UdienzaForm udienzaForm, Errors errors, HttpServletRequest request) {

     // L'ora di Fine Udienza deve essere maggiore dell'ora Inizio 
        if (!udienzaForm.getOraFineUdienza().getId().equals("-1")) {
        	if (Long.valueOf(udienzaForm.getOraFineUdienza().getId()) < Long.valueOf(udienzaForm.getOraInizioUdienza().getId())) {
        		errors.reject(Constants.MESSAGE);
                super.setMessage(Constants.MESSAGE, "udienza.error.oraFine", null, false);  		
        	}else if (Long.valueOf(udienzaForm.getOraFineUdienza().getId()) == Long.valueOf(udienzaForm.getOraInizioUdienza().getId())) {
        		if (Long.valueOf(udienzaForm.getMinutiFineUdienza().getId()) < Long.valueOf(udienzaForm.getMinutiInizioUdienza().getId())) {
        			errors.reject(Constants.MESSAGE);
                    super.setMessage(Constants.MESSAGE, "udienza.error.oraFine", null, false);		
        		}
        	}
        }
      
        return;
    }
}
