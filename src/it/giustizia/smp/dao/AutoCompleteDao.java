package it.giustizia.smp.dao;

import it.giustizia.smp.form.ComboForm;
import java.util.List;
import org.springframework.dao.DataAccessException;

public interface AutoCompleteDao {

    public String getCodiceCatastaleComune(long input) throws DataAccessException;

    public List<String> getResults(String input) throws DataAccessException;

    public List<ComboForm> getResultsComboForm(String input) throws DataAccessException;

    public List<String> getResultsIdBene(String input, String input2, String input3) throws DataAccessException;
    
    public List<String> getResultsIdBeneCopia(String input, String input2, String input3) throws DataAccessException;

    public List<ComboForm> getOperatoriFinanziariComboForm(String input) throws DataAccessException;

	public List<ComboForm> getOperatoriFinanziariPolizzeComboForm(String input) throws DataAccessException;

    public List<ComboForm> getCodiciCabComboForm(String input, String codiceAbi) throws DataAccessException;

	public List<ComboForm> getConservatComboForm( String nomeCombo, String input );
     
}
