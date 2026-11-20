package it.giustizia.smp.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import it.giustizia.smp.model.TAllineaFascPdoc;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class AllineaFascPdocDaoImpl extends HibernateDaoSupport implements AllineaFascPdocDao {

	public Object updateTAllineaFascPdoc(final long idRec, final String esito, final String messaggio) {
		return getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				DetachedCriteria criteria = DetachedCriteria.forClass(TAllineaFascPdoc.class);
				criteria.add(Restrictions.eq("codiKIdAllineaFascPdoc", idRec));
				TAllineaFascPdoc allineaFascModel = (TAllineaFascPdoc) getHibernateTemplate()
						.findByCriteria(criteria).get(0);
				allineaFascModel.setFlgEsitoAllineamento(esito);
				allineaFascModel.setDescEsitoAllineamento(messaggio);

				session.update(allineaFascModel);
				session.flush();

				return null;
			}
		});
	}

	public List<TAllineaFascPdoc> getDaInviare() {
		DetachedCriteria criteria = DetachedCriteria.forClass(TAllineaFascPdoc.class);

		Criterion crit1 = Restrictions.eq("flgEsitoAllineamento", "N");
		Criterion crit2 = Restrictions.isNull("flgEsitoAllineamento");
		criteria.add(Restrictions.or(crit1, crit2));

		List<TAllineaFascPdoc> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
			return risultati;
		else
			return null;
	}

}
