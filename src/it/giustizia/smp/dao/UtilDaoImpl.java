/**
 * 
 */
package it.giustizia.smp.dao;

import it.giustizia.smp.model.TAnPagina;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

/**
 * @author g.gabriele
 * 
 */
@Component
public class UtilDaoImpl extends HibernateDaoSupport implements UtilDao {
	/*
	 * (non-Javadoc)
	 * 
	 * @see it.giustizia.smp.dao.UtilDao#findPagine(java.lang.String,
	 * java.lang.Integer, java.lang.Integer, java.lang.Integer,
	 * java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	
	public List<TAnPagina> findPagine(String url, Integer idTipoUfficio, Integer idStatoProc, Integer idTipoAtto, Integer idCategoriaAtto, Integer idStatoAtto, Integer idFascicolo) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TAnPagina.class);

		criteria.add(Restrictions.eq("descUri", url));

		if (idCategoriaAtto != null && idCategoriaAtto != 0) {
			criteria.add(Restrictions.or(Restrictions.isNull("TTpCatAtto.codiKIdCatAtto"), Restrictions.eq("TTpCatAtto.codiKIdCatAtto", idCategoriaAtto)));
		}

		if (idTipoUfficio != null && idTipoUfficio != 0) {
			criteria.add(Restrictions.or(Restrictions.isNull("TTpTipoUfficio.codiKIdTipoUff"), Restrictions.eq("TTpTipoUfficio.codiKIdTipoUff", idTipoUfficio)));
//			criteria.add(Restrictions.eq("TTpTipoUfficio.codiKIdTipoUff", idTipoUfficio));
		}

		if (idStatoProc != null && idStatoProc != 0) {
			criteria.add(Restrictions.or(Restrictions.isNull("TTpStatoProc.codiKIdStatoProc"), Restrictions.eq("TTpStatoProc.codiKIdStatoProc", idStatoProc)));
//			criteria.add(Restrictions.eq("TTpStatoProc.codiKIdStatoProc", idStatoProc));
		}

		if (idTipoAtto != null && idTipoAtto != 0) {
			criteria.add(Restrictions.or(Restrictions.isNull("TTpTipoAtto.codiKIdTipoAtto"), Restrictions.eq("TTpTipoAtto.codiKIdTipoAtto", idTipoAtto)));
//			criteria.add(Restrictions.eq("TTpTipoAtto.codiKIdTipoAtto", idTipoAtto));
		}
		
		if (idStatoAtto != null && idStatoAtto != 0) {
			criteria.add(Restrictions.or(Restrictions.isNull("TTpStatoAtto.codiKIdStatoAtto"), Restrictions.eq("TTpStatoAtto.codiKIdStatoAtto", idStatoAtto)));
		}
		
		if (idFascicolo != null && idFascicolo != 0) {
			criteria.add(Restrictions.or(Restrictions.isNull("TTpTipoFascicolo.codiKIdTipoFasc"), Restrictions.eq("TTpTipoFascicolo.codiKIdTipoFasc", idFascicolo)));
		}
		
		

		List<TAnPagina> result = getHibernateTemplate().findByCriteria(criteria);

		if (result == null) {
			result = new LinkedList<TAnPagina>();
		}

		return result;

	}

	@Autowired
	protected void init(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

}
