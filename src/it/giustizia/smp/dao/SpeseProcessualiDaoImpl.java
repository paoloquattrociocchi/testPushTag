package it.giustizia.smp.dao;

import static it.giustizia.smp.utility.StaticQueries.*;

import it.giustizia.smp.model.TRelSoggSpese;


import java.sql.SQLException;


import java.util.List;
import java.util.Map;

import org.dozer.Mapper;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import org.hibernate.Session;

import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


@SuppressWarnings({ "unchecked" })
public class SpeseProcessualiDaoImpl extends HibernateDaoSupport implements SpeseProcessualiDao {

	@Autowired
	private Mapper	mapperBean;
	
	@Autowired
	private CommonDao commonDao;
	
	public void salvaRelSpeseProc(final TRelSoggSpese speseP) {

		getHibernateTemplate().execute(new HibernateCallback<Object>() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_SOGGETTO_gestioneRelSpeseProc");
				q.setParameter("idProcedimento", speseP.getId().getCodiKIdProc(), Hibernate.LONG);
				q.setParameter("idSoggetto", speseP.getId().getCodiKIdSoggInt(), Hibernate.LONG);
				q.setParameter("idVersione", speseP.getId().getCodiFkIdVer(), Hibernate.SHORT);
				q.setParameter("idRuolo", speseP.getId().getCodiFkIdRuolo(), Hibernate.INTEGER);
				q.setParameter("idUfficio", speseP.getId().getCodiKIdUffOwner(), Hibernate.INTEGER);	
				q.setParameter("idAtto", speseP.getId().getCodiKIdAtto(), Hibernate.LONG);
				q.setParameter("idSpeseProc", speseP.getId().getCodiKIdProc(), Hibernate.INTEGER);
								
				q.setParameter("idCodiceUtente", speseP.getDescUtente(), Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List<Map<?, ?>> l = q.list();
				return l;
			}
		});
	}
	

}