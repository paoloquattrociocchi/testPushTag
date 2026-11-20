/**
 * 
 */
package it.giustizia.smp.dao;

import it.giustizia.smp.model.TAnPagina;

import java.util.List;

/**
 * @author g.gabriele
 *
 */
public interface UtilDao {

	List<TAnPagina> findPagine(String url,Integer idTipoUfficio,Integer idStatoProc,Integer idTipoAtto,Integer idCategoriaAtto, Integer idStatoAtto, Integer idFascicolo);
}
