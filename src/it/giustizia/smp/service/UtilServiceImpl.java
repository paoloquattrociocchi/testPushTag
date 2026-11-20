/**
 * 
 */
package it.giustizia.smp.service;

import it.giustizia.smp.dao.UtilDao;
import it.giustizia.smp.model.TAnPagina;
import it.giustizia.smp.utility.RequestUtil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author g.gabriele
 *
 */
@Component
public class UtilServiceImpl implements UtilService {

	@Autowired
	private UtilDao utilDao;
	
	/* (non-Javadoc)
	 * @see it.giustizia.smp.service.UtilService#findPagine(javax.servlet.http.HttpServletRequest)
	 */
	
	public List<TAnPagina> findPagine(HttpServletRequest request) {
		
		String url = RequestUtil.getPathRequest(request);
		Integer idTipoUfficio = 0;
		Integer idStatoProc = RequestUtil.recuperaIdStatoProcedimento(request);
		if(RequestUtil.recuperaProfilo(request) != null){
			idTipoUfficio =(int) RequestUtil.recuperaProfilo(request).getIdTipoUfficio();
		}
		Integer idTipoAtto = RequestUtil.recuperaIdTipoAtto(request);
		Integer idCategoriaAtto = RequestUtil.recuperaIdCategoriaAtto(request);
		
		Integer idStatoAtto = RequestUtil.recuperaIdStatoAtto(request);
		
		Integer idFascicolo = RequestUtil.recuperaIdFascicolo(request);
		
		return utilDao.findPagine(url, idTipoUfficio, idStatoProc, idTipoAtto, idCategoriaAtto, idStatoAtto, idFascicolo);
//		return new LinkedList<TAnPagina>();
	}

}
