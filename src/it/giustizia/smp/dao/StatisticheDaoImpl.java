package it.giustizia.smp.dao;

import it.giustizia.smp.form.StatisticheForm;
import it.giustizia.smp.model.TRelMagUff;
import it.giustizia.smp.model.TStampa;
import it.giustizia.smp.model.TTpStampa;
import it.giustizia.smp.model.VProcedenti;
import it.giustizia.smp.utility.Utility;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.connection.ConnectionProvider;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.SessionFactoryImplementor;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;



/**
 * Implementazione del dao per la funzione di gestione statistiche (monitoraggio)
 * @author m.prisco
 *
 */

public class StatisticheDaoImpl extends HibernateDaoSupport implements StatisticheDao {

	
	
	/* (non-Javadoc)
	 * @see it.giustizia.smp.dao.StatisticheDao#getStampaByID(int)
	 */
	public TStampa getStampaByID(int idStampa) {
		return getHibernateTemplate().get(TStampa.class, idStampa);
	}

	
	/* (non-Javadoc)
	 * @see it.giustizia.smp.dao.StatisticheDao#listaIdMagistratiByUfficio(int)
	 */
	@SuppressWarnings({  "unchecked" })
	public List<Long> listaIdMagistratiByUfficio(int idUfficioUtente) {

		List<TRelMagUff> relazioniUfficio = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelMagUff.class)
			.add(Restrictions.eq("id.codiKIdUfficio", idUfficioUtente))
			.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente))
			//.add(Restrictions.isNull("dataFineAssegn"))
			.addOrder(Order.asc("id.codiKIdMagistrato"));
		relazioniUfficio = getHibernateTemplate().findByCriteria(criteria);
		if (!Utility.isDefinito(relazioniUfficio)) {
			return null;
		}
		List<Long> risultati = new ArrayList<Long>();
		Iterator<TRelMagUff> iterator = relazioniUfficio.iterator();
		while (iterator.hasNext()) {
			risultati.add(iterator.next().getId().getCodiKIdMagistrato());
		}
		return risultati;
	}
	

	
	
	/* (non-Javadoc)
	 * @see it.giustizia.smp.dao.StatisticheDao#listaIdProcedentiByUfficio(int)
	 */
	@SuppressWarnings({  "unchecked" })
	public List<Object[]> listaIdProcedentiByUfficio(int idUfficioUtente) {
		/*
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.property("codiKIdProced"));
		projList.add(Projections.property("descProced"));
		List<Object[]> lista = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TAnProcedente.class)
			.createAlias("TAnProcedUffs", "rel")
			.add(Restrictions.eq("rel.id.codiKIdUfficio", idUfficioUtente))
			.setProjection(Projections.distinct(projList))
			.addOrder(Order.asc("descProced"));
		lista = getHibernateTemplate().findByCriteria(criteria);
		*/
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.property("idprocedente"));
		projList.add(Projections.property("descrizioneprocedente"));
		List<Object[]> lista = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(VProcedenti.class)
			//.add(Restrictions.eq("idUfficioDdaCompetenza", idUfficioUtente))
				.add(Restrictions.eq("idProcura", idUfficioUtente))
			.setProjection(Projections.distinct(projList))
			.addOrder(Order.asc("descrizioneprocedente"));
		lista = getHibernateTemplate().findByCriteria(criteria);
		return lista ;
	}
	
	
	public Connection getSqlConnection() {
		SessionFactoryImplementor sessionFactoryImplementation = (SessionFactoryImplementor) getHibernateTemplate().getSessionFactory();
	    ConnectionProvider connectionProvider = sessionFactoryImplementation.getConnectionProvider();
	    try {
	           return connectionProvider.getConnection();
	    } catch (Exception e){
	    	e.printStackTrace();
	    }
	    return null;
	}


	@SuppressWarnings("unchecked")
	public List<TTpStampa> getValoriFiltro1(StatisticheForm statisticheForm) {
		List<TTpStampa> lista = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpStampa.class)
			.add(Restrictions.isNull("codiFKIdCatPadre"))
			.addOrder(Order.asc("codiKIdCategoria"));
		lista = getHibernateTemplate().findByCriteria(criteria);
		return lista ;
	}


	@SuppressWarnings("unchecked")
	public List<TTpStampa> getValoriFiltro2(StatisticheForm statisticheForm) {
		List<TTpStampa> lista = new ArrayList<TTpStampa>();
		if (!(statisticheForm.getFiltro1()!=null && !"-1".equals(statisticheForm.getFiltro1().getId()))) {
			return lista;
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpStampa.class);
		criteria.add(Restrictions.eq("codiFKIdCatPadre",Integer.valueOf(statisticheForm.getFiltro1().getId())));
		criteria.addOrder(Order.asc("codiKIdCategoria"));
		lista = getHibernateTemplate().findByCriteria(criteria);
		return lista ;
	}

	@SuppressWarnings("unchecked")
	public List<TTpStampa> getValoriFiltro3(StatisticheForm statisticheForm) {
		List<TTpStampa> lista = new ArrayList<TTpStampa>();
		if (!(statisticheForm.getFiltro2()!=null && !"-1".equals(statisticheForm.getFiltro2().getId()))) {
			return lista;
		}	
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpStampa.class);
		criteria.add(Restrictions.eq("codiFKIdCatPadre",Integer.valueOf(statisticheForm.getFiltro2().getId())));
		criteria.addOrder(Order.asc("codiKIdCategoria"));
		lista = getHibernateTemplate().findByCriteria(criteria);
		return lista ;
	}
	
	@SuppressWarnings("unchecked")
	/* (non-Javadoc)
	 * @see it.giustizia.smp.dao.StatisticheDao#getListaStampeByTipoUfficio(int)
	 */
	public List<TStampa> getListaStampeByTipoUfficio(int idTipoUff, StatisticheForm statisticheForm) {
		String filtro = "";
		if (statisticheForm.getFiltro3()!=null &&  !"-1".equals(statisticheForm.getFiltro3().getId())) {
			// Se il terzo filtro è valorizzato mostro solo quelle per il terzo filtro
			filtro = " rel1_.CODI_K_ID_STAMPA in (Select CODI_K_ID_STAMPA from T_REL_STAMPA_CAT where CODI_K_ID_CATEGORIA = " + statisticheForm.getFiltro3().getId() + ")";
		} else if (statisticheForm.getFiltro2()!=null &&  !"-1".equals(statisticheForm.getFiltro2().getId())) {
			filtro = " rel1_.CODI_K_ID_STAMPA in (Select CODI_K_ID_STAMPA from T_REL_STAMPA_CAT where CODI_K_ID_CATEGORIA = " + statisticheForm.getFiltro2().getId() + ")";
		} else if (statisticheForm.getFiltro1()!=null &&  !"-1".equals(statisticheForm.getFiltro1().getId())) {
			filtro = " rel1_.CODI_K_ID_STAMPA in (Select CODI_K_ID_STAMPA from T_REL_STAMPA_CAT where CODI_K_ID_CATEGORIA = " + statisticheForm.getFiltro1().getId() + ")";
		}
		
		DetachedCriteria criteria = DetachedCriteria.forClass(TStampa.class)
				.createAlias("TRelStamTipouffs", "rel")
				.add(Restrictions.eq("rel.id.codiKIdTipoUff", idTipoUff))
				.addOrder(Order.asc("descStampa"));
		if (!filtro.equals("")) criteria.add(Restrictions.sqlRestriction(filtro));
		List<TStampa> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	
}
