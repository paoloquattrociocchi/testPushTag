/**
 * 
 */
package it.giustizia.smp.service;

import it.giustizia.smp.model.TAnPagina;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author g.gabriele
 *
 */
public interface UtilService {

	List<TAnPagina> findPagine(HttpServletRequest request);
}
