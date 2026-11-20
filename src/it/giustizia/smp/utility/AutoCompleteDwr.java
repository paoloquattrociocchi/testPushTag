package it.giustizia.smp.utility;

import it.giustizia.smp.dao.AutoCompleteDao;
import it.giustizia.smp.form.ComboForm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public class AutoCompleteDwr {

    @Autowired
    private AutoCompleteDao autoComplete;
    
    @Autowired
    private MessageSource messageSource;

    public String getCodiceCatastaleComune(String input) {

        long id = Long.parseLong(input);
        String codiceCatastaleComune = autoComplete.getCodiceCatastaleComune(id);
        return codiceCatastaleComune;
    }

    public List<ComboForm> getSuggestionsComboForm(String input) {

        List<ComboForm> listaRisultati = autoComplete.getResultsComboForm(input);
        return listaRisultati;
    }

    public List<ComboForm> getConservatComboForm(String nomeCombo, String input) {

        List<ComboForm> listaRisultati = autoComplete.getConservatComboForm( nomeCombo, input );
        return listaRisultati;
    }
        
    public List<String> getSuggestionsIdBene(String input, String input2, String input3) {

        List<String> listaRisultati = autoComplete.getResultsIdBene(input, input2, input3);
        return listaRisultati;
    }
    
    public List<String> getSuggestionsIdBeneCopia(String input, String input2, String input3) {

        List<String> listaRisultati = autoComplete.getResultsIdBeneCopia(input, input2, input3);
        return listaRisultati;
    }

    public List<ComboForm> getOperatoriFinanziari(String input){
        List<ComboForm> listaRisultati = autoComplete.getOperatoriFinanziariComboForm(input);          
        return listaRisultati;
    }
    
	public List<ComboForm> getOperatoriFinanziariPolizze(String input) {
		List<ComboForm> listaRisultati = autoComplete.getOperatoriFinanziariPolizzeComboForm(input);
		return listaRisultati;
	}

    public List<ComboForm> getCodiciCab(String input, String codiceAbi){
        List<ComboForm> listaRisultati = autoComplete.getCodiciCabComboForm(input, codiceAbi);         
        return listaRisultati;
    }
    
    public String getAutocompleteComuniError(String code){
        String message = messageSource.getMessage(code, null, null);         
        return message;
    }

}
