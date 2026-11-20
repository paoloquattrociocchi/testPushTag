package it.giustizia.smp.dao;

import it.giustizia.smp.form.StatisticheForm;
import it.giustizia.smp.model.TStampa;
import it.giustizia.smp.model.TTpStampa;

import java.sql.Connection;
import java.util.List;

/***
 * Dao per la funzione di monitoraggio statistiche
 * @author m.prisco
 *
 */
public interface StatisticheDao {

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
	 * Restituisce la lista dei magistrati associati all'idUfficioUtente passato 
	 * @param idUfficioUtente
	 * @return
	 */
	List<Long> listaIdMagistratiByUfficio(int idUfficioUtente);

	/***
	 * Restituisce la lista dei procedenti associati idUfficioUtente passato
	 * @param idUfficioUtente
	 * @return
	 */
	List<Object[]> listaIdProcedentiByUfficio(int idUfficioUtente);
	
	
	/***
	 * Restituisce la connessione jdbc estratta da hibernate
	 * @param idUfficioUtente
	 * @return
	 */
	public Connection getSqlConnection();

	List<TTpStampa> getValoriFiltro1(StatisticheForm statisticheForm);
	List<TTpStampa> getValoriFiltro2(StatisticheForm statisticheForm);
	List<TTpStampa> getValoriFiltro3(StatisticheForm statisticheForm);
}
