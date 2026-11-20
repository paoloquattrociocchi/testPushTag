package it.giustizia.smp.service;

import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.StatisticheForm;
import it.giustizia.smp.model.TStampa;

import java.util.List;


/**
 * Interfaccia del service per la funzione di gestione statistiche (monitoraggio)
 * 
 * @author m.prisco
 * 
 */
public interface StatisticheService {

	/***
	 * Restituisce la lista delle stampe disponibili per l'idTipoUff passato
	 * @param idTipoUff
	 * @param statisticheForm 
	 * @return
	 */
	List<TStampa> getListaStampeByTipoUfficio(int idTipoUff, StatisticheForm statisticheForm);

	/***
	 * Restituisce l'oggetto TStampa con l'idPassato
	 * @param idStampa
	 * @return
	 */
	TStampa getStampaByID(int idStampa);

	/**
	 * Restituisce la lista dei magistrati associati all'idUfficioUtente passato da caricare nella combo
	 * @param idUfficioUtente
	 * @return
	 */
	List<ComboForm> getComboMagistrati(int idUfficioUtente);

	/**
	 * Restituisce la lista dei procedenti associati all'idUfficioUtente passato da caricare nella combo
	 * @param idUfficioUtente
	 * @return
	 */
	List<ComboForm> getComboProcedente(int idUfficioUtente);

	List<ComboForm>  getValoriFiltro1(StatisticheForm statisticheForm);
	List<ComboForm>  getValoriFiltro2(StatisticheForm statisticheForm); 
	List<ComboForm>  getValoriFiltro3(StatisticheForm statisticheForm); 
	
}
