/**
 * 
 */
package it.giustizia.smp.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import it.giustizia.smp.dto.pg_procedimento.SogIntDettaglioProcedimentoDto;
import it.giustizia.smp.dto.pg_procedimento.SogSpesePDettaglioProcedimentoDto;
import it.giustizia.smp.dto.pg_soggetto.BeniCollegatiTerzoDto;
import it.giustizia.smp.dto.pg_soggetto.DettaglioPersonaFisicaDto;
import it.giustizia.smp.model.TIntestatario;
import it.giustizia.smp.model.TPersonaFisicaId;
import it.giustizia.smp.model.TPersonaGiurId;
import it.giustizia.smp.model.TRelAttoMisPers;
import it.giustizia.smp.model.TRelBeneAtto;
import it.giustizia.smp.model.TRelProcSogint;
import it.giustizia.smp.model.TRelSoggSpese;

/**
 * @author dario.barbarini
 *
 */

public class SoggettoDaoImpl extends HibernateDaoSupport implements  SoggettoDao {


	@SuppressWarnings("rawtypes")
	public DettaglioPersonaFisicaDto getDettaglioPersonaFisicaVer(final TPersonaFisicaId id) {

		return (DettaglioPersonaFisicaDto) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_SOGGETTO_dettaglioPersonaFisicaVer");
				q.setParameter("vidPersonaFis", id.getCodiKIdPersonaFis());
				q.setParameter("vidUffOwn", id.getCodiKIdUffOwner());
				q.setParameter("vidVer", id.getCodiKIdVer());
				q.setResultTransformer(Transformers.aliasToBean(DettaglioPersonaFisicaDto.class));
				List l = q.list();
				if ((l != null) && (l.size() > 0)) {
					return l.get(0);
				}
				return null;
			}
		});
	}	
	

	
	
	@SuppressWarnings("rawtypes")
	public Map<String, Object> getDettaglioPersonaGiuVer(final TPersonaGiurId id) {

		return (Map<String, Object>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_SOGGETTO_dettaglioPersonaGiuridicaVer");
				q.setParameter("vidPersonaGiur", id.getCodiKIdPersGiur());
				q.setParameter("vidUffOwn", id.getCodiKIdUffOwner());
				q.setParameter("vidVer", id.getCodiKIdVer());
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				if ((l != null) && (l.size() > 0)) {
					return l.get(0);
				}
				return null;
			}
		});
	}

	private Criteria misurePersonali(long idSoggetto, int idUffOwn, long idProcedimento) {
		
		Criteria criteria = getSession().createCriteria(TRelAttoMisPers.class);
		
		criteria.createAlias("TProcedimento", "proc");
		criteria.createAlias("proc.TAnUfficioByCodiFkIdUffOwner", "uff");
		criteria.createAlias("TMisuraPers", "misPers");
		criteria.createAlias("misPers.TTpCaratMisura", "carMis");
		criteria.createAlias("misPers.TSoggetto", "sogg");
		criteria.createAlias("TAtto", "atto");
		criteria.createAlias("atto.TTpCatAtto", "catAtto");
		criteria.createAlias("TTpTipoMisura", "tipoMis");

		criteria.createAlias("TAnNormative", "normat", Criteria.LEFT_JOIN);
		criteria.createAlias("TTpEsitoByCodiFkIdEsito1", "esito1", Criteria.LEFT_JOIN);		
		criteria.createAlias("TTpEsitoByCodiFkIdEsito2", "esito2", Criteria.LEFT_JOIN);
		criteria.createAlias("TTpEsitoByCodiFkIdEsito3", "esito3", Criteria.LEFT_JOIN);
		criteria.createAlias("TDefPers", "defPers", Criteria.LEFT_JOIN);
		
		criteria.add(Restrictions.eq("proc.codiKIdProc", idProcedimento));
		criteria.add(Restrictions.eq("uff.codiKIdUfficio", idUffOwn));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUffOwn));
		criteria.add(Restrictions.eq("sogg.id.codiKIdSoggInt", idSoggetto));
		
		//criteria.add(Restrictions.eq("flgValido", "S"));
		Criterion aFlgVal = Restrictions.eq("flgValido", "S");
		Criterion bFlgVal = Restrictions.eq("flgValido", "R");
		criteria.add(Restrictions.or(aFlgVal, bFlgVal));	
	
		criteria.addOrder(Order.desc("atto.dataDeposito"));
	
		return criteria;
	}
		
	public List<TRelAttoMisPers> misurePersonaliSoggetto(long idSoggetto, int idUffOwn, long idProcedimento) {
		
		Criteria criteria = misurePersonali(idSoggetto, idUffOwn, idProcedimento);
		
		Criterion a = Restrictions.eq("carMis.codiKIdCaratMis", "P");
		Criterion b = Restrictions.eq("carMis.codiKIdCaratMis", "AP");
		criteria.add(Restrictions.or(a, b));	
		
		List<TRelAttoMisPers> lista = criteria.list();
		return lista;		
	}
	

	
	public List<TRelAttoMisPers> cauzioniSoggetto(long idSoggetto, int idUffOwn, long idProcedimento) {
		
		Criteria criteria = misurePersonali(idSoggetto, idUffOwn, idProcedimento);
		
		criteria.createAlias("TCauzione", "cauz", Criteria.LEFT_JOIN);
		criteria.add(Restrictions.eq("carMis.codiKIdCaratMis", "C"));

		List<TRelAttoMisPers> lista = criteria.list();
		return lista;		
	}	

	public List<TRelAttoMisPers> art67Soggetto(long idSoggetto, int idUffOwn, long idProcedimento) {
		
		Criteria criteria = misurePersonali(idSoggetto, idUffOwn, idProcedimento);
		
		criteria.add(Restrictions.eq("carMis.codiKIdCaratMis", "A"));

		List<TRelAttoMisPers> lista = criteria.list();
		return lista;		
	}
	
	public List<SogIntDettaglioProcedimentoDto> getListaSoggCondannati(final long idUfficio, final long idProcedimento, final long idAtto,
																		final String flagSpese, final String flagCassa ) {

		return (List<SogIntDettaglioProcedimentoDto>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_SOGGETTO_listaSoggCondannati");
				q.setParameter("IDUFFOWN", idUfficio);
				q.setParameter("IDPROCEDIMENTO", idProcedimento);
				q.setParameter("IDATTO", idAtto);
				q.setParameter("FLAGSPESE", flagSpese);
				q.setParameter("FLAGCASSA", flagCassa);
	
				q.setResultTransformer(Transformers.aliasToBean(SogIntDettaglioProcedimentoDto.class));
				return q.list();
			}
		});
	}	
	
	public TRelProcSogint dettaglioRelSogInt(long idSoggetto, int idUffOwn, long idProcedimento) {
		
		Criteria criteria = getSession().createCriteria(TRelProcSogint.class);
		
		criteria.createAlias("TTpTipoRuolo", "tipoRuolo");
		criteria.createAlias("TTpTipoPrevProc", "tipoPrevProc", Criteria.LEFT_JOIN);
		criteria.createAlias("tipoPrevProc.TTpTipoPrevenzione", "tipoPrevenzione", Criteria.LEFT_JOIN); 
		
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUffOwn));
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		criteria.add(Restrictions.eq("id.codiKIdSoggInt", idSoggetto));

		List<TRelProcSogint> lista = criteria.list();
		if(lista!=null && lista.size()>0) return lista.get(0);
		else return null;
	}	

	public List<BeniCollegatiTerzoDto> beniCollegatiTerzo(final long idUfficio, final long idProcedimento, final long idSoggTit) {

		return (List<BeniCollegatiTerzoDto>) getHibernateTemplate().execute(new HibernateCallback() {
		
		public Object doInHibernate(final Session session) throws HibernateException, SQLException {
		
			Query q = session.getNamedQuery("PG_SOGGETTO_beniCollegatiTerzo");
			q.setParameter("IDUFFOWN", idUfficio);
			q.setParameter("IDPROCEDIMENTO", idProcedimento);
			q.setParameter("IDSOGGTIT", idSoggTit);
			
			q.setResultTransformer(Transformers.aliasToBean(BeniCollegatiTerzoDto.class));
			return q.list();
			}
		});
	}
	

	
	public Long getIdSoggettoAnnullato(final long idSoggetto, final int idUffOwn,final long idProcedimento) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {
	
				
				Query q = session.getNamedQuery("PG_SOGGETTO_getIdSoggettoAnnullato");
				q.setParameter("idUffOwn", idUffOwn, Hibernate.INTEGER);
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setParameter("idSoggetto", idSoggetto, Hibernate.LONG);
			


				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				
				if(l !=null && !l.isEmpty()){
					HashMap first = (HashMap) l.get(0);
					Long id = (Long) first.get("IDCURRENTSOGGETTO");
					return id;					
				}
				
				return null;

			}
		});
	}	

	public TIntestatario getIntestatario(final long idSoggetto, final int idUffOwner){
	DetachedCriteria criteria = DetachedCriteria.forClass(TIntestatario.class);
	criteria.add(Restrictions.eq("id.codiKIdIntestatario", idSoggetto));
	criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUffOwner));
	
	List<TIntestatario> risultati = getHibernateTemplate().findByCriteria(criteria);
	if(risultati != null && risultati.size() > 0)
	{
		TIntestatario intestatario = (TIntestatario)risultati.get(0);
		return intestatario;
	}
	else
		return null;
}

	public List<TRelBeneAtto> flagDecretoSequestro(final long idSoggetto, final int idUffOwn, final long idProcedimento) {

		return (List<TRelBeneAtto>) getHibernateTemplate().execute(new HibernateCallback() {

			public List<TRelBeneAtto> doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_SOGGETTO_flagDecretoSequestro");
				q.setParameter("idUffOwn", idUffOwn, Hibernate.INTEGER);
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setParameter("idSoggetto", idSoggetto, Hibernate.LONG);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				return q.list();
			}
		});
	}

	public void salvaRelSpeseProc(final TRelSoggSpese speseP, final String tipoOper) {
		

		getHibernateTemplate().execute(new HibernateCallback<Object>() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_SOGGETTO_gestioneRelSpeseProc");
				q.setParameter("idProcedimento", speseP.getId().getCodiKIdProc(), Hibernate.LONG);
				q.setParameter("idSoggetto", speseP.getId().getCodiKIdSoggInt(), Hibernate.LONG);
				q.setParameter("idVersione", speseP.getId().getCodiFkIdVer(), Hibernate.SHORT);
				q.setParameter("idRuolo", speseP.getId().getCodiFkIdRuolo(), Hibernate.INTEGER);
				q.setParameter("idUfficio", speseP.getId().getCodiKIdUffOwner(), Hibernate.INTEGER);	
				q.setParameter("idAtto", speseP.getId().getCodiKIdAtto(), Hibernate.LONG);
				q.setParameter("idSpeseProc", speseP.getId().getCodiKIdSpeseProcessuali(), Hibernate.INTEGER);				
				q.setParameter("descUtente", speseP.getDescUtente(), Hibernate.STRING);
				q.setParameter("tipoOper", tipoOper, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List<Map<?, ?>> l = q.list();
				return l;
			}
		});
	}
}

