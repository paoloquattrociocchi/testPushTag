package it.giustizia.smp.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
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
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import it.giustizia.smp.form.CategoriaCatastaleForm;
import it.giustizia.smp.form.ComuneForm;
import it.giustizia.smp.form.CondizioneGiuridicaForm;
import it.giustizia.smp.form.ConservatoriaForm;
import it.giustizia.smp.form.IstitutoDetenzioneForm;
import it.giustizia.smp.form.ProfessioneForm;
import it.giustizia.smp.form.ProvinciaForm;
import it.giustizia.smp.form.SettoreAttivitaForm;
import it.giustizia.smp.form.StatoForm;
import it.giustizia.smp.form.UfficiForm;
import it.giustizia.smp.model.TAnComuni;
import it.giustizia.smp.model.TAnConservatorie;
import it.giustizia.smp.model.TAnDistretto;
import it.giustizia.smp.model.TAnIstitutoDet;
import it.giustizia.smp.model.TAnMagistrati;
import it.giustizia.smp.model.TAnProvincia;
import it.giustizia.smp.model.TAnStato;
import it.giustizia.smp.model.TAnUfficio;
import it.giustizia.smp.model.TRelMagProc;
import it.giustizia.smp.model.TTpCatCatasto;
import it.giustizia.smp.model.TTpCondGiur;
import it.giustizia.smp.model.TTpProfessione;
import it.giustizia.smp.model.TTpSettoreAtt;
import it.giustizia.smp.model.TTpSpeseProcessuali;
import it.giustizia.smp.model.TTpTipoIstituto;
import it.giustizia.smp.model.TTpTipoUfficio;
import it.giustizia.smp.utility.Constants;

public class AmministrazioneDaoImpl extends HibernateDaoSupport implements AmministrazioneDao {
	
	@Autowired
	private CommonDao commonDao;
	
	public List<TAnMagistrati> listaMagistrati(final String cognome, final String nome, final Date dataNascita, final Integer matricola)
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(TAnMagistrati.class);
		if(cognome != null && cognome.length() > 0)
			criteria.add(Restrictions.like("descCognomeMag", cognome + "%"));
		if(nome != null && nome.length() > 0)
			criteria.add(Restrictions.like("descNomeMag", nome + "%"));
		if(dataNascita != null)
			criteria.add(Restrictions.eq("dataNascita", dataNascita));
		if(matricola != null)
			criteria.add(Restrictions.eq("numeMatricola", matricola));
		
		criteria.addOrder(Order.asc("descCognomeMag"));
		
		List<TAnMagistrati> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
	
	public TAnMagistrati getMagistrato(long codice) {
		TAnMagistrati result = getHibernateTemplate().get(TAnMagistrati.class, (long)codice);
		return result;
	}

	public void inserisciMagistrato(String cognome, String nome, Date dataNascita, Integer matricola, String descUtente) {
		TAnMagistrati magistrato = new TAnMagistrati();
		magistrato.setDescCognomeMag(cognome);
		magistrato.setDescNomeMag(nome);
		magistrato.setDataNascita(dataNascita);
		magistrato.setNumeMatricola(matricola);
		magistrato.setDescUtente(descUtente);
		Long result = (Long) getHibernateTemplate().save(magistrato);
		getHibernateTemplate().flush();
	}
	
	@SuppressWarnings("unchecked")
	public List listaMagistratiNonAssociati(final int idUffOwn, final String cognome, final String nome, final Integer matricola)
	{
		return (List)getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_AMMINISTRAZIONE_lista_magistrati_non_associati");
				q.setParameter("vidUffOwn", idUffOwn, Hibernate.INTEGER);
				q.setParameter("vcognome", cognome, Hibernate.STRING);
				q.setParameter("vnome", nome, Hibernate.STRING);
				q.setParameter("vmatricola", matricola, Hibernate.INTEGER);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				return q.list();
			}
		});
	}

//	public void associaMagistrato(long codiKIdMagistrato, int idUffOwn, String descUtente) {
//		TRelMagUff relMagistrato = new TRelMagUff();
//		TRelMagUffId idMagistrato = new TRelMagUffId();
//		idMagistrato.setCodiKIdMagistrato(codiKIdMagistrato);
//		idMagistrato.setCodiKIdUfficio(idUffOwn);
//		idMagistrato.setCodiKIdUffOwner(idUffOwn);
//		relMagistrato.setId(idMagistrato);
//		relMagistrato.setDescUtente(descUtente);
//		relMagistrato.setDataAssegnazione(new Date());
//		Object result = getHibernateTemplate().save(relMagistrato);
//		getHibernateTemplate().flush();
//		
//	}
	 @SuppressWarnings({ "unchecked", "rawtypes" })
	    public void associaMagistrato(final long codiKIdMagistrato, final int idUffOwn, final String descUtente) {

	        getHibernateTemplate().execute(new HibernateCallback() {

	            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

	                Query q = session.getNamedQuery("PG_AMMINISTRAZIONE_associa_mag_uff");
	                q.setParameter("idMag", codiKIdMagistrato, Hibernate.LONG);
	                q.setParameter("idUffOwn", idUffOwn, Hibernate.INTEGER);
	                q.setParameter("descUtente", descUtente, Hibernate.STRING);
	                
	                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
	                List l = q.list();
	                return l;
	            }
	        });
	    }
	
	public void modificaMagistrato(final TAnMagistrati magistratoModel) {
		 getHibernateTemplate().execute(new HibernateCallback() {
	            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
	            	  session.update(magistratoModel);
	                  session.flush();
	                  return null;
	            }
	        });
	    }
	
	public Integer inserisciStato(String descIstatStato, String descSiglaStato, String descStato, Date dataFineValidita, String descUtente) {
		TAnStato stato = new TAnStato();
		stato.setDescIstatStato(descIstatStato);
		stato.setDescSiglaStato(descSiglaStato);
		stato.setDescStato(descStato);
		stato.setDataFineValidita(dataFineValidita);
		stato.setDescUtente(descUtente);
		Integer result;
		try{
			result = (Integer) getHibernateTemplate().save(stato);
			getHibernateTemplate().flush();
		}
		catch(Exception ex)
		{
			result = new Integer(0);
		}
		return result;
	}

	public Integer inserisciProvincia(String codIstatProvincia, String descSiglaProvincia, String descProvincia, Date dataFineValidita, String descUtente) {
		TAnProvincia provincia = new TAnProvincia();
		provincia.setCodiKIdIstatProv(codIstatProvincia);
		provincia.setDescSiglaProvincia(descSiglaProvincia);
		provincia.setDescProvincia(descProvincia);
		provincia.setDescUtente(descUtente);
		if(dataFineValidita != null){
		provincia.setDataFineValidita(dataFineValidita);
		}
		Integer result;
		try{
			result = (Integer) getHibernateTemplate().save(provincia);
			getHibernateTemplate().flush();
		}
		catch(Exception ex)
		{
			result = new Integer(0);
		}
		return result;
	}
	
	public List<TAnMagistrati> listaMagistratiSostituzione(String tipoMagistrato, int idUffOwn, int idTipoUfficio, Date dataFineAssegnazione) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TAnMagistrati.class);
		criteria.createAlias("TRelMagProcs", "magProc");
		criteria.createAlias("magProc.TProcedimento", "proc");
		criteria.createAlias("magProc.TTpTipoRuoloMag", "tipoRuolo");
		criteria.createAlias("proc.TAnUfficioByCodiFkIdUffOwner", "uff");
		criteria.createAlias("proc.TTpStatoProc", "statoProc");

		criteria.add(Restrictions.eq("uff.codiKIdUfficio", idUffOwn));
		
		if(tipoMagistrato.equals("MAGISTRATIPM")) {
			criteria.add(Restrictions.le("proc.dataIscrizione", dataFineAssegnazione));
			criteria.add(Restrictions.ne("tipoRuolo.codiKIdRuoloMag", 4));
			
			criteria.add(Restrictions.ne("statoProc.codiKIdStatoProc", 2));
			criteria.add(Restrictions.ne("statoProc.codiKIdStatoProc", 19));
			criteria.add(Restrictions.ne("statoProc.codiKIdStatoProc", 14));
			criteria.add(Restrictions.ne("statoProc.codiKIdStatoProc", 24));
			criteria.add(Restrictions.ne("statoProc.codiKIdStatoProc", 20));
			criteria.add(Restrictions.ne("statoProc.codiKIdStatoProc", 22));
			criteria.add(Restrictions.ne("statoProc.codiKIdStatoProc", 33));			
		} else if(tipoMagistrato.equals("MAGISTRATICOLLEGIO")) {
			criteria.add(Restrictions.le("proc.dataIscrizione", dataFineAssegnazione));
			criteria.add(Restrictions.ne("tipoRuolo.codiKIdRuoloMag", 4));
			
			if(idTipoUfficio == Integer.valueOf(Constants.TIPO_UFFICIO_TRIBUNALE)) {
				criteria.add(Restrictions.eq("statoProc.codiKIdStatoProc", 6));
			} else if(idTipoUfficio == Integer.valueOf(Constants.TIPO_UFFICIO_CORTE_D_APPELLO)) {
				criteria.add(Restrictions.not(Restrictions.in("statoProc.codiKIdStatoProc", new Integer[]{13,26,44})));			
			}
		} else if(tipoMagistrato.equals("GIUDICEDELEGATO")) {
			criteria.add(Restrictions.eq("tipoRuolo.codiKIdRuoloMag", 4));
		}
		
		criteria.addOrder(Order.asc("descCognomeMag"));
		criteria.addOrder(Order.asc("descNomeMag"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<TAnMagistrati> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public List listaMagistratiSubentro(final int idUffOwn)
	{
		return (List)getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_AMMINISTRAZIONE_lista_magistrati_subentranti");
				q.setParameter("vidUffOwn", idUffOwn, Hibernate.INTEGER);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				return q.list();
			}
		});
	}
	
	/*public List<TAnMagistrati> listaMagistratiSubentro(String tipoMagistrato, int idUffOwn, int idTipoUfficio, Date dataFineAssegnazione) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TAnMagistrati.class);
		criteria.createAlias("TRelMagProcs", "magProc");
		criteria.createAlias("magProc.TProcedimento", "proc");
		criteria.createAlias("proc.TAnUfficioByCodiFkIdUffOwner", "uff");
		
		criteria.add(Restrictions.eq("uff.codiKIdUfficio", idUffOwn));
		criteria.add(Restrictions.le("magProc.id.codiKIdDataAss", dataFineAssegnazione));
		
		criteria.addOrder(Order.asc("descCognomeMag"));
		criteria.addOrder(Order.asc("descNomeMag"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<TAnMagistrati> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}*/	
	
	public List<TAnStato> listaStati(final String descStato, final String descSiglaStato, final String descIstatStato)
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(TAnStato.class);
		if(descStato != null && descStato.length() > 0)
			criteria.add(Restrictions.like("descStato", descStato + "%"));
		if(descSiglaStato != null && descSiglaStato.length() > 0)
			criteria.add(Restrictions.like("descSiglaStato", descSiglaStato + "%"));
		if(descIstatStato != null && descIstatStato.length() > 0)
			criteria.add(Restrictions.eq("descIstatStato", descIstatStato));
		
		criteria.addOrder(Order.asc("descStato"));
		
		List<TAnStato> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	public TAnStato getStato(int codice) {
		TAnStato result = getHibernateTemplate().get(TAnStato.class, (int)codice);
		return result;
	}

	public void modificaStato(final StatoForm statoForm) {
		getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
            	
            TAnStato statoModel = getStato(statoForm.getCodiKIdStato());
            statoModel.setDescStato(statoForm.getDescStato());
            statoModel.setDescSiglaStato(statoForm.getDescSiglaStato());
            statoModel.setDescIstatStato(statoForm.getDescIstatStato());
            statoModel.setDataFineValidita(statoForm.getDataFineValidita());  
     		session.update(statoModel);
            
            session.flush();
            return null;
            }
        });
	}
	
	
	public List<TRelMagProc> listaProcedimentiSostituzione(String tipoMagistrato, long idMagistratoSostituito, int idUffOwn, int idTipoUfficio, Date dataFineAssegnazione, Short annoDa, Long numDa, Short annoA, Long numA) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelMagProc.class);
		criteria.createAlias("TAnMagistrati", "mag");
		criteria.createAlias("TProcedimento", "proc");
		criteria.createAlias("TTpTipoRuoloMag", "tipoRuolo");
		criteria.createAlias("proc.TAnUfficioByCodiFkIdUffOwner", "uff");
		criteria.createAlias("proc.TTpStatoProc", "statoProc");
		
		criteria.add(Restrictions.eq("mag.codiKIdMagistrato", idMagistratoSostituito));
		criteria.add(Restrictions.eq("uff.codiKIdUfficio", idUffOwn));
		
		/*Criterion a = Restrictions.gt("dataFineAssegn", dataFineAssegnazione);
		Criterion b1 = Restrictions.le("id.codiKIdDataAss", dataFineAssegnazione);
		Criterion b2 = Restrictions.isNull("dataFineAssegn");
		Criterion b = Restrictions.and(b1, b2);
		criteria.add(Restrictions.or(a, b));*/
		
		Criterion a = Restrictions.gt("dataFineAssegn", dataFineAssegnazione);
		Criterion a1 = Restrictions.isNull("dataFineAssegn");
		Criterion b = Restrictions.or(a, a1);
		Criterion b1 = Restrictions.le("id.codiKIdDataAss", dataFineAssegnazione);
		criteria.add(Restrictions.and(b, b1));
		
		if(annoDa!=null) {
			criteria.add(Restrictions.ge("proc.numeAnnoProc", annoDa));
			criteria.add(Restrictions.ge("proc.numeProc", numDa));
		}
		if(annoA!=null) {
			criteria.add(Restrictions.le("proc.numeAnnoProc", annoA));
			criteria.add(Restrictions.le("proc.numeProc", numA));
		}		
		
		if(tipoMagistrato!=null) {
			if(tipoMagistrato.equals("MAGISTRATIPM")) {
				criteria.add(Restrictions.le("proc.dataIscrizione", dataFineAssegnazione));
				criteria.add(Restrictions.ne("tipoRuolo.codiKIdRuoloMag", 4));
				
				criteria.add(Restrictions.ne("statoProc.codiKIdStatoProc", 2));
				criteria.add(Restrictions.ne("statoProc.codiKIdStatoProc", 19));
				criteria.add(Restrictions.ne("statoProc.codiKIdStatoProc", 14));
				criteria.add(Restrictions.ne("statoProc.codiKIdStatoProc", 24));
				criteria.add(Restrictions.ne("statoProc.codiKIdStatoProc", 20));
				criteria.add(Restrictions.ne("statoProc.codiKIdStatoProc", 22));
				criteria.add(Restrictions.ne("statoProc.codiKIdStatoProc", 33));			
			} else if(tipoMagistrato.equals("MAGISTRATICOLLEGIO")) {
				criteria.add(Restrictions.le("proc.dataIscrizione", dataFineAssegnazione));
				criteria.add(Restrictions.ne("tipoRuolo.codiKIdRuoloMag", 4));
				
				if(idTipoUfficio == Integer.valueOf(Constants.TIPO_UFFICIO_TRIBUNALE)) {
					criteria.add(Restrictions.eq("statoProc.codiKIdStatoProc", 6));
				} else if(idTipoUfficio == Integer.valueOf(Constants.TIPO_UFFICIO_CORTE_D_APPELLO)) {
					criteria.add(Restrictions.not(Restrictions.in("statoProc.codiKIdStatoProc", new Integer[]{13,26,44})));							
				}
			} else if(tipoMagistrato.equals("GIUDICEDELEGATO")) {
				criteria.add(Restrictions.eq("tipoRuolo.codiKIdRuoloMag", 4));
			}
		}
		
		criteria.addOrder(Order.asc("proc.numeAnnoProc"));
		criteria.addOrder(Order.asc("proc.numeProc"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<TRelMagProc> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}	
	
	public List<TRelMagProc> controllaAssegnazioneMagistratoProcedimento(long idMagistratoSubentrante, int idUffOwn, long idProcedimento, Date dataFineAssegnazione) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TRelMagProc.class);
		criteria.createAlias("TProcedimento", "proc");
		criteria.createAlias("TAnMagistrati", "mag");
		criteria.createAlias("proc.TAnUfficioByCodiFkIdUffOwner", "uff");
		
		criteria.add(Restrictions.eq("mag.codiKIdMagistrato", idMagistratoSubentrante));
		criteria.add(Restrictions.eq("proc.codiKIdProc", idProcedimento));
		criteria.add(Restrictions.eq("uff.codiKIdUfficio", idUffOwn));
		
		Criterion a = Restrictions.gt("dataFineAssegn", dataFineAssegnazione);
		Criterion b1 = Restrictions.le("id.codiKIdDataAss", dataFineAssegnazione);
		Criterion b2 = Restrictions.isNull("dataFineAssegn");
		Criterion b = Restrictions.and(b1, b2);
		criteria.add(Restrictions.or(a, b));		
		
		criteria.addOrder(Order.asc("proc.numeAnnoProc"));
		criteria.addOrder(Order.asc("proc.numeProc"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<TRelMagProc> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
		
	}	

	public void aggiornaSostituzioneMagistrato(final String tipoMagistrato, final long idMagistratoSostituito, final long idMagistratoSubentrante, final String motivo, final int idUffOwn, final long idProcedimento, final Date dataFineAssegnazione, final String descUtente) {
		
		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				Query q = session.getNamedQuery("PG_PROCEDIMENTO_aggiornaMagistratoProcedimento");
				q.setParameter("TIPOMAG", tipoMagistrato, Hibernate.STRING);
				q.setParameter("IDMAGSOST", idMagistratoSostituito, Hibernate.LONG);
				q.setParameter("IDMAGSUB", idMagistratoSubentrante, Hibernate.LONG); 
				q.setParameter("MOTIVO", motivo, Hibernate.STRING);
				q.setParameter("IDUFFOWN", idUffOwn, Hibernate.INTEGER); 
				q.setParameter("ID_PROC", idProcedimento, Hibernate.LONG); 
				q.setParameter("DATAFINEASS", dataFineAssegnazione, Hibernate.DATE);
				q.setParameter("DESCUTENTE", descUtente, Hibernate.STRING); 
				
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;				
			}
		});		
	}
	
	public List<TAnProvincia> listaProvince(String descProvincia, String descSiglaProvincia) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TAnProvincia.class);
		if(descProvincia != null && descProvincia.length() > 0)
			criteria.add(Restrictions.like("descProvincia", descProvincia + "%"));
		if(descSiglaProvincia != null && descSiglaProvincia.length() > 0)
			criteria.add(Restrictions.like("descSiglaProvincia", descSiglaProvincia + "%"));

		criteria.addOrder(Order.asc("descProvincia"));
		
		List<TAnProvincia> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	public TAnProvincia getProvincia(String codiKIdIstatProv) {
		TAnProvincia result = getHibernateTemplate().get(TAnProvincia.class, codiKIdIstatProv);
		return result;
	}


	public void modificaProvincia(final ProvinciaForm provinciaForm ) {
		getHibernateTemplate().execute(new HibernateCallback() {
	        public Object doInHibernate(final Session session) throws HibernateException, SQLException {
	        	
		        TAnProvincia provinciaModel = getProvincia(provinciaForm.getCodiKIdIstatProv());
		        provinciaModel.setDescProvincia(provinciaForm.getDescProvincia());
		        provinciaModel.setDescSiglaProvincia(provinciaForm.getDescSiglaProvincia());
		        provinciaModel.setDataFineValidita(provinciaForm.getDataFineValidita());  
		 		session.update(provinciaModel);
		        
		        session.flush();
		        return null;
	        }
		});
	}

	public List<TAnComuni> listaComuni(String descComune, String descCapComune, String descIstatComune, String descCodBelfiore, String descCodCatasto) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TAnComuni.class);
		
		if(descComune != null && descComune.length() > 0)
			criteria.add(Restrictions.like("descComune", descComune + "%"));
		if(descCapComune != null && descCapComune.length() > 0)
			criteria.add(Restrictions.like("descCapComune", descCapComune + "%"));
		if(descIstatComune != null && descIstatComune.length() > 0)
			criteria.add(Restrictions.like("descIstatComune", descIstatComune + "%"));
		if(descCodBelfiore != null && descCodBelfiore.length() > 0)
			criteria.add(Restrictions.like("descBelfiore", descCodBelfiore + "%"));
		if(descCodCatasto != null && descCodCatasto.length() > 0)
			criteria.add(Restrictions.like("descCatasto", descCodCatasto + "%"));
		
			criteria.addOrder(Order.asc("descComune"));
			
		List<TAnComuni> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}


	public TAnComuni getComune(int codiceComune) {
		TAnComuni result = getHibernateTemplate().get(TAnComuni.class, codiceComune);
		return result;
	}


	public Integer inserisciComune(String codiFkIdIstatProvincia, String descIstatComune, String descComune, String descCapComune, String descBelfiore, String descCatasto, Date dataFineValidita, String descUtente) {
		
		TAnProvincia fkProvincia = new TAnProvincia(codiFkIdIstatProvincia);
		
		TAnComuni comune = new TAnComuni();
		comune.setTAnProvincia(fkProvincia);
		comune.setDescIstatComune(descIstatComune);
		comune.setDescComune(descComune);
		comune.setDescCapComune(descCapComune);
		comune.setDescBelfiore(descBelfiore);
		comune.setDescCatasto(descCatasto);
		comune.setDescUtente(descUtente);
		
		if(dataFineValidita != null){
		comune.setDataFineValidita(dataFineValidita);
		}
		Integer result;
		try{
			result = (Integer) getHibernateTemplate().save(comune);
			getHibernateTemplate().flush();
		}
		catch(Exception ex)
		{
			result = new Integer(0);
		}
		return result;
	}


	public void modificaComune(final ComuneForm comuneForm) {
		getHibernateTemplate().execute(new HibernateCallback() {
	        public Object doInHibernate(final Session session) throws HibernateException, SQLException {
	        
	        TAnProvincia fkIdProvincia = new TAnProvincia(comuneForm.getItemProvincia().getId());
	        
	        TAnComuni comuneModel = getComune(comuneForm.getCodiKIdComune());
	        comuneModel.setTAnProvincia(fkIdProvincia);
	        comuneModel.setDescIstatComune(comuneForm.getDescIstatComune());
	        comuneModel.setDescComune(comuneForm.getDescComune());
	        comuneModel.setDescCapComune(comuneForm.getDescCapComune());
	        comuneModel.setDescBelfiore(comuneForm.getDescBelfiore());
	        comuneModel.setDescCatasto(comuneForm.getDescCatasto());
	        comuneModel.setDataFineValidita(comuneForm.getDataFineValidita());
	 		session.update(comuneModel);
	        
	        session.flush();
	        return null;
	        }
	    });
	}
	
	public List<TAnUfficio> listaUffici(int idTipoUfficio, int idComune) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TAnUfficio.class);
		
		if(idTipoUfficio > 0)
			criteria.add(Restrictions.eq("TTpTipoUfficio.codiKIdTipoUff", idTipoUfficio));
		if(idComune > 0)
			criteria.add(Restrictions.like("TAnComuni.codiKIdComune", idComune));
		
		criteria.addOrder(Order.asc("descUfficio"));
		
		List<TAnUfficio> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	public TAnUfficio getUfficio(int codiceUfficio) {

		TAnUfficio result = getHibernateTemplate().get(TAnUfficio.class, codiceUfficio);
		return result;
	}

	public Integer inserisciUfficio(int idTipoUfficio, int idComune, String descTipoUfficio, String descSedeUfficio, String descIndirizzoSede, int idDistretto, String descDistretto, Character flagTribMp, int idUfficioTribunale, Character flagDda, Date dataFineValidita, String descUtente) {
		
		String descTpUff = "";
		String di = " DI ";
		String descComUff = "";
		
		if(descTipoUfficio != null){
			descTpUff = descTpUff.concat(descTipoUfficio);
		}
		
		if(descSedeUfficio != null){
			descComUff = descComUff.concat(di).concat(descSedeUfficio);
		}
		
		String DescrUfficio = descTpUff + descComUff;
		
		TAnUfficio ufficio = new TAnUfficio();
		
		TTpTipoUfficio tipoUfficio = new TTpTipoUfficio();
		tipoUfficio.setCodiKIdTipoUff(idTipoUfficio);
		ufficio.setTTpTipoUfficio(tipoUfficio);
		
		TAnComuni comuneSede = new TAnComuni();
		comuneSede.setCodiKIdComune(idComune);
		ufficio.setTAnComuni(comuneSede);
		
		if(idDistretto > 0 ){
			TAnDistretto distretto = new TAnDistretto();
			distretto.setCodiKIdDistretto(idDistretto);
			
			ufficio.setTAnDistretto(distretto);
		}
		
		if(idUfficioTribunale > 0 ){
			TAnUfficio tribunale = new TAnUfficio();
			tribunale.setCodiKIdUfficio(idUfficioTribunale);
		
			ufficio.setTAnUfficio(tribunale);
		}
		
		ufficio.setDescTipoUfficio(descTipoUfficio);
		ufficio.setDescIndirizzoSede(descIndirizzoSede);
		ufficio.setDescSedeUfficio(descSedeUfficio);
		ufficio.setDescUffGiudiz(descDistretto);
		ufficio.setFlgTribMp(flagTribMp);
		ufficio.setFlgDda(flagDda);
		ufficio.setDescUfficio(DescrUfficio);
		ufficio.setDescUtente(descUtente);
		
		if(dataFineValidita != null){
			ufficio.setDataFineValidita(dataFineValidita);
		}
		
		Integer result = (Integer) getHibernateTemplate().save(ufficio);
		getHibernateTemplate().flush();
		return result;
	}
	
	public Integer inserisciUfficio(UfficiForm ufficiForm, int idTipoUfficio, int idComune, String descTipoUfficio, String descSedeUfficio, String descUtente){
		
		String idDistretto = null;
		if(ufficiForm.getItemDistretto() != null && !ufficiForm.getItemDistretto().getId().equals("-1")){
			idDistretto = ufficiForm.getItemDistretto().getId().toString();
		}

		String idUfficioTribunale = null;
		if(ufficiForm.getItemTribunale() != null && !ufficiForm.getItemTribunale().getId().equals("-1")){
			idUfficioTribunale = ufficiForm.getItemTribunale().getId().toString();
		}

		String descTpUff = "";
		String di = " DI ";
		String descComUff = "";
		
		if(descTipoUfficio != null){
			descTpUff = descTpUff.concat(descTipoUfficio);
		}
		
		if(descSedeUfficio != null){
			descComUff = descComUff.concat(di).concat(descSedeUfficio);
		}
		
		String descrUfficio = descTpUff + descComUff;

        // PLO 2017-12 p.8
        // chiamo procedura passando query di inserimento

		StringBuffer sb = new StringBuffer(); 
		sb.append("insert into smp.T_AN_UFFICIO (");

		//sb.append("codi_k_id_ufficio,");
		sb.append("codi_fk_id_tipo_uff,");
		sb.append("codi_fk_id_com_sede,");
		sb.append("desc_tipo_ufficio,");
		sb.append("desc_sede_ufficio,");
		sb.append("desc_indirizzo_sede,");
		sb.append("codi_fk_id_distretto,");
		sb.append("desc_uff_giudiz,");
		sb.append("flg_trib_mp,");
		sb.append("codi_fk_id_trib_comp,");
		sb.append("flg_dda,");
		sb.append("data_fine_validita,");
		sb.append("desc_utente,");
		//sb.append("data_ult_agg,");
		sb.append("desc_ufficio,");
		//sb.append("desc_cf_pu,");
		//sb.append("desc_pubbl_uff,");
		sb.append("flg_patr_questura,");
		sb.append("flg_autorizza_dna");

		sb.append(") values (");

		//sb.append(parseString(null)+",");//v_codi_k_id_ufficio
		sb.append((idTipoUfficio)+",");//v_codi_fk_id_tipo_uff
		sb.append((idComune)+",");//v_codi_fk_id_com_sede
		sb.append(HelperDao.parseString(descTipoUfficio)+",");//v_desc_tipo_ufficio
		sb.append(HelperDao.parseString(descSedeUfficio)+",");//v_desc_sede_ufficio
		sb.append(HelperDao.parseString(ufficiForm.getDescIndirizzoSede())+",");//v_desc_indirizzo_sede
		sb.append(HelperDao.parseString(idDistretto)+",");//v_codi_fk_id_distretto
		sb.append(HelperDao.parseString(ufficiForm.getDescUffGiudiz())+",");//v_desc_uff_giudiz
		sb.append(HelperDao.parseString(ufficiForm.getFlgTribMp())+",");//v_flg_trib_mp
		sb.append(HelperDao.parseString(idUfficioTribunale)+",");//v_codi_fk_id_trib_comp
		sb.append(HelperDao.parseString(ufficiForm.getFlgDda())+",");//v_flg_dda
		sb.append(HelperDao.parseDate(ufficiForm.getDataFineValidita())+",");//v_data_fine_validita
		sb.append(HelperDao.parseString(descUtente)+",");//v_desc_utente
		//sb.append(parseString(null)+",");//v_data_ult_agg
		sb.append(HelperDao.parseString(descrUfficio)+",");//v_desc_ufficio
		//sb.append(parseString(null)+",");//v_desc_cf_pu
		//sb.append(parseString(null)+",");//v_desc_pubbl_uff
		sb.append(HelperDao.parseString(null)+",");//v_flg_patr_questura
		sb.append(HelperDao.parseString(null)+"");//v_flg_autorizza_dna

		sb.append(")");
		
		//Object ret = inserisciUfficioByProc(sb.toString());
		//Integer status = Integer.parseInt(ret.toString());
		// SIT-MP 110 DG modifica per errore precedente, richiamava lo schema sbagliato (SMP_GIU)
		String schema = HelperDao.DB_SMP;
		String procedura = HelperDao.PROC_PG_AMMINISTRAZIONE_aggiornamento_anag_uffici;
		SqlParameter[] paramDef = {
				// lista parametri
				new SqlOutParameter("eseguito", Types.NUMERIC), // par 1
				new SqlParameter(Types.VARCHAR), // par 2
		};
		Map<String, Object> result = HelperDao.eseguiProcedura(schema, procedura, paramDef, sb.toString());
		Integer eseguito = Integer.parseInt(result.get("eseguito").toString());
		
		return eseguito;
	}

	private Object inserisciUfficioByProc(final String query) {
		Object ret = getHibernateTemplate().execute(new HibernateCallback() {
	        public Object doInHibernate(final Session session) throws HibernateException, SQLException {
			
			String status = null;
			Query q = session.getNamedQuery("PG_AMMINISTRAZIONE_aggiornamento_anag_uffici");
			q.setParameter("vSQL", query, Hibernate.STRING);
			q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List l = q.list();
			if(l !=null && !l.isEmpty()){
				HashMap first = (HashMap) l.get(0);
				status = (String) first.get("STATUS");
			}
			
	        return status;
	        }
	    });		
		return ret;
	}

	public List<TTpCatCatasto> listaCategorieCatastali(	String descCategoriaCat, String descBreveCategoriaCat) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TTpCatCatasto.class);
		
		if(descCategoriaCat != null && descCategoriaCat.length() > 0)
			criteria.add(Restrictions.like("descCategoriaCat", descCategoriaCat + "%"));
		
		if(descBreveCategoriaCat != null && descBreveCategoriaCat.length() > 0)
			criteria.add(Restrictions.like("descBreveCategoriaCat", descBreveCategoriaCat + "%"));

		criteria.addOrder(Order.asc("descCategoriaCat"));
		
		List<TTpCatCatasto> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	public List<TTpProfessione> listaProfessioni(String descProfessione) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpProfessione.class);
		if(descProfessione != null && descProfessione.length() > 0)
			criteria.add(Restrictions.like("descProfessione", descProfessione + "%"));

		criteria.addOrder(Order.asc("descProfessione"));
		
		List<TTpProfessione> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	public List<TAnIstitutoDet> listaIstitutiDetenzione(int idTipoIstituto, int idComuneIstituto) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TAnIstitutoDet.class);
		if(idTipoIstituto > 0)
			criteria.add(Restrictions.eq("TTpTipoIstituto.codiKIdTipoIst", idTipoIstituto));
		if(idComuneIstituto > 0)
			criteria.add(Restrictions.eq("TAnComuni.codiKIdComune", idComuneIstituto));

		criteria.addOrder(Order.asc("descUffGiudiz"));
		
		List<TAnIstitutoDet> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	public List<TAnConservatorie> listaConservatorie(String descConserv, String descCodiConserv) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TAnConservatorie.class);
		if(descConserv != null && descConserv.length() > 0)
			criteria.add(Restrictions.like("descConserv", descConserv + "%"));
		
		if(descCodiConserv != null && descCodiConserv.length() > 0)
			criteria.add(Restrictions.like("descCodiConserv", descCodiConserv + "%"));

		criteria.addOrder(Order.asc("descConserv"));
		
		List<TAnConservatorie> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	public List<TTpSettoreAtt> listaSettoreAttivita(String descSettoreAtt, String descSezioneAteco) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpSettoreAtt.class);
		
		if(descSettoreAtt != null && descSettoreAtt.length() > 0)
			criteria.add(Restrictions.like("descSettoreAtt", descSettoreAtt + "%"));
		if(descSezioneAteco != null && descSezioneAteco.length() > 0)
			criteria.add(Restrictions.like("descSezioneAteco", descSezioneAteco + "%"));

		criteria.addOrder(Order.asc("descSettoreAtt"));
		
		List<TTpSettoreAtt> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	public List<TTpCondGiur> listaCondizioneGiuridica(String descCondGiur) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpCondGiur.class);
		
		if(descCondGiur != null && descCondGiur.length() > 0)
			criteria.add(Restrictions.like("descCondGiur", descCondGiur + "%"));

		criteria.addOrder(Order.asc("descCondGiur"));
		
		List<TTpCondGiur> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	public TTpCatCatasto getCategoriaCatastale(int codiceCatCatastale) {
		TTpCatCatasto result = getHibernateTemplate().get(TTpCatCatasto.class, codiceCatCatastale);
		return result;
	}

	public TTpProfessione getProfessione(int codiceProfessione) {
		TTpProfessione result = getHibernateTemplate().get(TTpProfessione.class, codiceProfessione);
		return result;
	}

	public TAnIstitutoDet getIstitutoDetenzione(int codiceIstDetenzione) {
		TAnIstitutoDet result = getHibernateTemplate().get(TAnIstitutoDet.class, codiceIstDetenzione);
		return result;
	}

	public TAnConservatorie getConservatoria(short codiceConservatoria) {
		TAnConservatorie result = getHibernateTemplate().get(TAnConservatorie.class, codiceConservatoria);
		return result;
	}

	public TTpSettoreAtt getSettoreAttivita(int codiceSettAttivita) {
		TTpSettoreAtt result = getHibernateTemplate().get(TTpSettoreAtt.class, codiceSettAttivita);
		return result;
	}

	public TTpCondGiur getCondGiuridica(int codiceCondGiuridica) {
		TTpCondGiur result = getHibernateTemplate().get(TTpCondGiur.class, codiceCondGiuridica);
		return result;
	}

	public Integer inserisciCategoriaCatastale(String descCategoriaCat,	String descBreveCategoriaCat, Date dataFineValidita, String descUtente) {
		TTpCatCatasto newRow = new TTpCatCatasto();
		newRow.setDescCategoriaCat(descCategoriaCat);
		newRow.setDescBreveCategoriaCat(descBreveCategoriaCat);
		newRow.setDescUtente(descUtente);
		if(dataFineValidita != null){
			newRow.setDataFineValidita(dataFineValidita);	
		}
		Integer result = (Integer) getHibernateTemplate().save(newRow);
		getHibernateTemplate().flush();
		return result;
	}

	public Integer inserisciProfessione(String descProfessione, Date dataFineValidita, String descUtente) {
		TTpProfessione newRow = new TTpProfessione();
		newRow.setDescProfessione(descProfessione);
		newRow.setDescUtente(descUtente);
		
		if(dataFineValidita != null){
			newRow.setDataFineValidita(dataFineValidita);	
		}
		
		Integer result = (Integer) getHibernateTemplate().save(newRow);
		getHibernateTemplate().flush();
		return result;
	}

	public Integer inserisciIstitutoDetenzione(int idTipoIstituto, String descTipoIstituto, int idComuneIstituto, String descComuneIstituto, String descIndirizzoIstituto, String descUffGiudiz, Date dataFineValidita, String descUtente) {
		
		String descTpIst = "";
		String di = " DI ";
		String descComIst = "";
		
		if(descTipoIstituto != null){
			descTpIst = descTpIst.concat(descTipoIstituto);
		} 
		
		if(descComuneIstituto != null){
			descComIst = descComIst.concat(di).concat(descComuneIstituto);
		}
		
		String DescrIstituto = descTpIst + descComIst;
		
		TAnIstitutoDet newRow = new TAnIstitutoDet();
		
		TTpTipoIstituto tpIst = new TTpTipoIstituto();
		
		if(idTipoIstituto > 0 ) {
			tpIst.setCodiKIdTipoIst(idTipoIstituto);
			newRow.setTTpTipoIstituto(tpIst);
		}
		
		TAnComuni comuneIst = new TAnComuni();
		
		if(idComuneIstituto > 0){
			comuneIst.setCodiKIdComune(idComuneIstituto);
			newRow.setTAnComuni(comuneIst);
		}
		
		newRow.setDescIstituto(DescrIstituto);
		newRow.setDescIndirizzo(descIndirizzoIstituto);
		newRow.setDescUffGiudiz(descUffGiudiz);
		newRow.setDescUtente(descUtente);
		if(dataFineValidita != null){
			newRow.setDataFineValidita(dataFineValidita);	
		}
		Integer result = (Integer) getHibernateTemplate().save(newRow);
		getHibernateTemplate().flush();
		return result;
	}

	public Integer inserisciConservatoria(String descConserv, String descCodiConserv, Date dataFineValidita, String descUtente) {
		TAnConservatorie newRow = new TAnConservatorie();
		newRow.setDescConserv(descConserv);
		newRow.setDescCodiConserv(descCodiConserv);
		newRow.setDescUtente(descUtente);
		if(dataFineValidita != null){
			newRow.setDataFineValidita(dataFineValidita);	
		}
		Integer result;
		try{
			result = (Integer) getHibernateTemplate().save(newRow);
			getHibernateTemplate().flush();
		}
		catch(Exception ex)
		{
			result = new Integer(0);
		}
		return result;
	}

	public Integer inserisciSettoreAttivita(String descSettoreAtt,	String descSezioneAteco, Date dataFineValidita, String descUtente) {
		TTpSettoreAtt newRow = new TTpSettoreAtt();
		newRow.setDescSettoreAtt(descSettoreAtt);
		newRow.setDescSezioneAteco(descSezioneAteco);
		newRow.setDescUtente(descUtente);
		if(dataFineValidita != null){
			newRow.setDataFineValidita(dataFineValidita);	
		}
		Integer result = (Integer) getHibernateTemplate().save(newRow);
		getHibernateTemplate().flush();
		return result;
	}

	public Integer inserisciCondGiuridica(String descCondGiur, Date dataFineValidita, String descUtente) {
		TTpCondGiur newRow = new TTpCondGiur();
		newRow.setDescCondGiur(descCondGiur);
		newRow.setDescUtente(descUtente);
		if(dataFineValidita != null){
			newRow.setDataFineValidita(dataFineValidita);	
		}
		Integer result = (Integer) getHibernateTemplate().save(newRow);
		getHibernateTemplate().flush();
		return result;
	}

	public void modificaCatCatastale(final CategoriaCatastaleForm categoriaCatastaleForm) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
        
				TTpCatCatasto tpCatastoModel = getCategoriaCatastale(categoriaCatastaleForm.getCodiKIdCategCat());
				tpCatastoModel.setDescCategoriaCat(categoriaCatastaleForm.getDescCategoriaCat());
				tpCatastoModel.setDescBreveCategoriaCat(categoriaCatastaleForm.getDescBreveCategoriaCat());
				tpCatastoModel.setDataFineValidita(categoriaCatastaleForm.getDataFineValidita());
				session.update(tpCatastoModel);
				session.flush();
				return null;
			}
		});
	}

	public void modificaCondGiuridica(final CondizioneGiuridicaForm condizioneGiuridicaForm) {
		getHibernateTemplate().execute(new HibernateCallback() {
	        public Object doInHibernate(final Session session) throws HibernateException, SQLException {
	        
	        TTpCondGiur condGiurModel = getCondGiuridica(condizioneGiuridicaForm.getCodiKIdCondGiur());
	        condGiurModel.setDescCondGiur(condizioneGiuridicaForm.getDescCondGiur());
	        condGiurModel.setDataFineValidita(condizioneGiuridicaForm.getDataFineValidita());
	 		session.update(condGiurModel);
	        
	        session.flush();
	        return null;
	        }
		});

	}

	public void modificaConservatoria(final ConservatoriaForm conservatoriaForm) {
		getHibernateTemplate().execute(new HibernateCallback() {
	        public Object doInHibernate(final Session session) throws HibernateException, SQLException {
		        
		        TAnConservatorie conservatorieModel = getConservatoria(conservatoriaForm.getCodiKIdConserv());
		        conservatorieModel.setDescConserv(conservatoriaForm.getDescConserv());
		        conservatorieModel.setDescCodiConserv(conservatoriaForm.getDescCodiConserv());
		        conservatorieModel.setDataFineValidita(conservatoriaForm.getDataFineValidita());
		        session.update(conservatorieModel);

		        session.flush();
		        return null;
	        }
		});
	}

	public void modificaIstitutoDet(final IstitutoDetenzioneForm istitutoDetenzioneForm) {
		getHibernateTemplate().execute(new HibernateCallback() {
	        public Object doInHibernate(final Session session) throws HibernateException, SQLException {
	        	
		        TAnIstitutoDet istDetModel = getIstitutoDetenzione(istitutoDetenzioneForm.getCodiKIdIstituto());
	    
				String descTpIst = null;
				String di = " DI ";
				String descComIst = null;

		        TTpTipoIstituto tipoIst = null;
				TAnComuni tCom = null;
						
				if(istitutoDetenzioneForm.getItemTipoIstituto() != null && !istitutoDetenzioneForm.getItemTipoIstituto().getId().equals("-1")){
					tipoIst =  commonDao.getTipoIstitutoById(new Integer(istitutoDetenzioneForm.getItemTipoIstituto().getId()).intValue());
					descTpIst = tipoIst.getDescTipoIst();
				} else {
					//ne creo uno base per scrivere null senza generare eccezione
					tipoIst =  new TTpTipoIstituto();
				}
				
				if(istitutoDetenzioneForm.getItemComune() != null && !istitutoDetenzioneForm.getItemComune().getId().equals("-1")){
					tCom = commonDao.getComuneById(new Integer(istitutoDetenzioneForm.getItemComune().getId()).intValue());
					descComIst = di.concat(tCom.getDescComune());
					
				} else {
					//ne creo uno base per scrivere null senza generare eccezione
					tCom =  new TAnComuni();
				}

				String DescrIstituto = descTpIst + descComIst;
				
		        istDetModel.setDescIstituto(DescrIstituto);
		        istDetModel.setDescIndirizzo(istitutoDetenzioneForm.getDescIndirizzo());
		        istDetModel.setDescUffGiudiz(istitutoDetenzioneForm.getDescUffGiudiz());
		        istDetModel.setTTpTipoIstituto(tipoIst);
		        istDetModel.setTAnComuni(tCom);
		        istDetModel.setDataFineValidita(istitutoDetenzioneForm.getDataFineValidita());
		 		session.update(istDetModel);
		        
		        session.flush();
		        return null;
	        }
    });
		
	}

	public void modificaProfessione(final ProfessioneForm professioneForm) {
		getHibernateTemplate().execute(new HibernateCallback() {
	        public Object doInHibernate(final Session session) throws HibernateException, SQLException {
		        
		        TTpProfessione professioneModel = getProfessione(professioneForm.getCodiKIdProfessione());
		        professioneModel.setDescProfessione(professioneForm.getDescProfessione());
		        professioneModel.setDataFineValidita(professioneForm.getDataFineValidita());
		 		session.update(professioneModel);
		        session.flush();
		        return null;
	        }
		});

	}

	public void modificaSettoreAttivita(final SettoreAttivitaForm settoreAttivitaForm) {
		getHibernateTemplate().execute(new HibernateCallback() {
        public Object doInHibernate(final Session session) throws HibernateException, SQLException {
	        
	        TTpSettoreAtt settoreAttModel = getSettoreAttivita(settoreAttivitaForm.getCodiKIdSettoreAtt());
	        settoreAttModel.setDescSettoreAtt(settoreAttivitaForm.getDescSettoreAtt());
	        settoreAttModel.setDescSezioneAteco(settoreAttivitaForm.getDescSezioneAteco());
	        settoreAttModel.setDataFineValidita(settoreAttivitaForm.getDataFineValidita());
	 		session.update(settoreAttModel);
	        
	        session.flush();
	        return null;
        }
		});	
	}

	@SuppressWarnings("unchecked")
	public void modificaUfficio(UfficiForm ufficiForm) {
		
        TAnUfficio ufficioModel = getUfficio(ufficiForm.getCodiKIdUfficio());
		TTpTipoUfficio tpUff = null;  
		TAnComuni tCom = null;
		TAnDistretto tDistr = null;
		TAnUfficio tTrib = null;
		Character cFlag = null;
        
		String descTpUff = null;
		String di = " DI ";
		String descComUff = null;
		
		if(ufficiForm.getItemTipoUfficio().getId() != null && !ufficiForm.getItemTipoUfficio().getId().equals("-1")){
			tpUff =  commonDao.getTipoUfficioById(new Integer(ufficiForm.getItemTipoUfficio().getId()).intValue());			
			ufficioModel.setDescTipoUfficio(tpUff.getDescTipoUff());
			descTpUff = tpUff.getDescTipoUff();
		} else {
			//ne creo uno base per scrivere null senza generare eccezione
			descTpUff = "";
			tpUff =  new TTpTipoUfficio();
		}
		
		if(ufficiForm.getItemComuneSede().getId() != null && !ufficiForm.getItemComuneSede().getId().equals("-1")){
			tCom = commonDao.getComuneById(new Integer(ufficiForm.getItemComuneSede().getId()).intValue());			
			ufficioModel.setDescSedeUfficio(tCom.getDescComune());
			descComUff = di + tCom.getDescComune();
		} else {
			//ne creo uno base per scrivere null senza generare eccezione
			descComUff = "";
			tCom =  new TAnComuni();
		}

		String cmbItemDistretto = null;
		if(ufficiForm.getItemDistretto() != null && !ufficiForm.getItemDistretto().getId().equals("-1")){
			tDistr = commonDao.getDistrettoById(new Integer(ufficiForm.getItemDistretto().getId()).intValue());	
			//ufficioModel.setDescUffGiudiz(tDistr.getDescDistretto());
			ufficioModel.setTAnDistretto(tDistr);
			cmbItemDistretto = ufficiForm.getItemDistretto().getId();
		} else {
			//ne creo uno base per scrivere null senza generare eccezione
			tDistr =  new TAnDistretto(0);
			ufficioModel.setTAnDistretto(tDistr);
		}
		
		cFlag = null;
		if(ufficiForm.getFlgTribMp() != null){
			cFlag = new Character(ufficiForm.getFlgTribMp().charAt(0));
		}
		ufficioModel.setFlgTribMp(cFlag);
		
		String cmbItemTribunale = null;
		if(ufficiForm.getItemTribunale() != null && !ufficiForm.getItemTribunale().getId().equals("-1")){
			tTrib = new TAnUfficio();
			Integer idTribunale = new Integer(ufficiForm.getItemTribunale().getId());
			tTrib.setCodiKIdUfficio(idTribunale.intValue());
			ufficioModel.setTAnUfficio(tTrib);
			cmbItemTribunale = ufficiForm.getItemTribunale().getId();
		}else{
			//tTrib = new TAnUfficio();
			//tTrib.setCodiKIdUfficio(null);
			ufficioModel.setTAnUfficio(null);
		}
		
		cFlag = null;
		if(ufficiForm.getFlgDda() != null){
			cFlag = new Character(ufficiForm.getFlgDda().charAt(0));
		}
		ufficioModel.setFlgDda(cFlag);

		cFlag = null;
		if(ufficiForm.getFlgMP() != null){
			cFlag = new Character(ufficiForm.getFlgMP().charAt(0));
		}
		ufficioModel.setFlgPatrQuestura(cFlag);
		
		cFlag = null;
		if(ufficiForm.getFlgDNA() != null){
			cFlag = new Character(ufficiForm.getFlgDNA().charAt(0));
		}
		ufficioModel.setFlgAutorizzaDna(cFlag);

		String descrUfficio = descTpUff + descComUff;
		
		ufficioModel.setTTpTipoUfficio(tpUff);
		ufficioModel.setTAnComuni(tCom);
        ufficioModel.setDescIndirizzoSede(ufficiForm.getDescIndirizzoSede());
        ufficioModel.setDescUffGiudiz(ufficiForm.getDescUffGiudiz());
        ufficioModel.setDescUfficio(descrUfficio);
        ufficioModel.setDataFineValidita(ufficiForm.getDataFineValidita());
        
        // PLO 2017-12 p.8
        // chiamo procedura passando query di modifica
		StringBuffer sb = new StringBuffer(); 
		sb.append("update smp.T_AN_UFFICIO set");
		//sb.append(" desc_tipo_ufficio = "+HelperDao.parseString(ufficioModel.getDescTipoUfficio())+",");
		//sb.append(" desc_sede_ufficio = "+HelperDao.parseString(ufficioModel.getDescSedeUfficio())+",");
		sb.append(" desc_indirizzo_sede = "+HelperDao.parseString(ufficioModel.getDescIndirizzoSede())+",");
		sb.append(" codi_fk_id_distretto = "+HelperDao.parseString(cmbItemDistretto)+",");
		sb.append(" desc_uff_giudiz = "+HelperDao.parseString(ufficioModel.getDescUffGiudiz())+",");
		sb.append(" flg_trib_mp = "+HelperDao.parseCharacter(ufficioModel.getFlgTribMp())+",");
		sb.append(" codi_fk_id_trib_comp = "+HelperDao.parseString(cmbItemTribunale)+",");
		sb.append(" flg_dda = "+HelperDao.parseCharacter(ufficioModel.getFlgDda())+",");
		sb.append(" data_fine_validita = "+HelperDao.parseDate(ufficioModel.getDataFineValidita())+",");
		//sb.append(" desc_utente = "+HelperDao.parseString(ufficioModel.getDescUtente())+",");
		//sb.append(" data_ult_agg = "+HelperDao.parseDate(ufficioModel.getDataUltAgg())+",");
		//sb.append(" desc_ufficio = "+HelperDao.parseString(ufficioModel.getDescUfficio())+",");
		//sb.append(" desc_cf_pu = "+HelperDao.parseString(ufficioModel.getDescCfPu())+",");
		//sb.append(" desc_pubbl_uff = "+HelperDao.parseString(ufficioModel.getDescPubblUff())+",");
		sb.append(" flg_patr_questura = "+HelperDao.parseCharacter(ufficioModel.getFlgPatrQuestura())+",");
		sb.append(" flg_autorizza_dna = "+HelperDao.parseCharacter(ufficioModel.getFlgAutorizzaDna())+"");
		sb.append(" where codi_k_id_ufficio = "+(ufficioModel.getCodiKIdUfficio())+"");
		
		// Object ret = inserisciUfficioByProc(sb.toString());
		// Integer status = Integer.parseInt(ret.toString());
		// SIT-MP 110 DG modifica per errore precedente, richiamava lo schema sbagliato (SMP_GIU)
		String schema = HelperDao.DB_SMP;
		String procedura = HelperDao.PROC_PG_AMMINISTRAZIONE_aggiornamento_anag_uffici;
		SqlParameter[] paramDef = {
			// lista parametri
			new SqlOutParameter("eseguito", Types.NUMERIC), // par 1
			new SqlParameter(Types.VARCHAR), // par 2
		};
		Map<String, Object> result = HelperDao.eseguiProcedura(schema , procedura, paramDef, sb.toString());
		String eseguito = result.get("eseguito").toString();
		
	}

	public TTpSettoreAtt getSettoreAttivitaByDesc(String descSettoreAttivita) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpSettoreAtt.class);
		criteria.add(Restrictions.eq("descSettoreAtt", descSettoreAttivita ));
		@SuppressWarnings("unchecked")
		List<TTpSettoreAtt> lista = getHibernateTemplate().findByCriteria(criteria);
		if (lista.size()>0) {
			return lista.get(0);
		}
		return null;
	}

	@Override
	public List<TTpSpeseProcessuali> listaImportoSpese(BigDecimal importoSpese) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpSpeseProcessuali.class);
		if(importoSpese != null )
		criteria.add(Restrictions.like("importoSpese", importoSpese ));
		criteria.addOrder(Order.desc("dataFineValidita"));
		criteria.addOrder(Order.asc("importoSpese"));
		criteria.addOrder(Order.asc("dataInizioValidita"));
		
		List<TTpSpeseProcessuali> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public void inserisciSpeseProcessuali(BigDecimal importoSpese, Date dataInizioValidita, String descImportoSpese,
			String descUtente) {
		TTpSpeseProcessuali speseProcessuali = new TTpSpeseProcessuali();
		speseProcessuali.setImportoSpese(importoSpese);
		speseProcessuali.setDescImportoSpese(descImportoSpese);
		speseProcessuali.setDataInizioValidita(dataInizioValidita);
		speseProcessuali.setDescUtente(descUtente);
		// cerco l'ultimo id inserito e incremento
		int nextVal = this.getNextValFromTTpSpeseProcessuali();
		speseProcessuali.setCodiKIdSpeseProcessuali(nextVal);
		long dataMilli = System.currentTimeMillis();
		//In formato data:
		java.util.Date dataUltAgg = new Date( dataMilli );
		speseProcessuali.setDataUltAgg(dataUltAgg);
		getHibernateTemplate().save(speseProcessuali);
		getHibernateTemplate().flush();
		
	}
	
	@SuppressWarnings("rawtypes")
	private int getNextValFromTTpSpeseProcessuali() {
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpSpeseProcessuali.class);
		criteria.setProjection(Projections.max("codiKIdSpeseProcessuali"));
		List list = getHibernateTemplate().findByCriteria(criteria);
		int number = 0;
		if(list != null && list.size() > 0 && list.get(0) != null) {
			number = (Integer) list.get(0);
		}
		int nextVal = number+1;
		return nextVal;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void modificaSpeseProcessuali(final int idSpeseProcessuali, final BigDecimal importoSpese, final Date dataInizioValidita, final Date dataFineValidita, final String descImportoSpese,
			 final String descUtente) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
		        
		        TTpSpeseProcessuali speseProcessuali =  new TTpSpeseProcessuali();
		        speseProcessuali.setCodiKIdSpeseProcessuali(idSpeseProcessuali);
				speseProcessuali.setImportoSpese(importoSpese);
				speseProcessuali.setDescImportoSpese(descImportoSpese);
				speseProcessuali.setDataFineValidita(dataFineValidita);
				speseProcessuali.setDataInizioValidita(dataInizioValidita);
				long dataMilli = System.currentTimeMillis();
				//In formato data:
				java.util.Date dataUltAgg = new Date( dataMilli );
				speseProcessuali.setDataUltAgg(dataUltAgg);
				speseProcessuali.setDescUtente(descUtente);
				session.update(speseProcessuali);
		        session.flush();
		        return null;
	        }
		});	
		
		
	}

	@Override
	public TTpSpeseProcessuali getSpeseProcessuali(int codiKIdSpeseProcessuali) {
		TTpSpeseProcessuali result = getHibernateTemplate().get(TTpSpeseProcessuali.class, codiKIdSpeseProcessuali);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean existImportoSpese(BigDecimal importoSpese, Date dataInizioValidita) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpSpeseProcessuali.class);
		criteria.add(Restrictions.like("importoSpese", importoSpese ));
		criteria.add(Restrictions.or(Restrictions.isNull("dataFineValidita"), Restrictions.ge("dataFineValidita", dataInizioValidita)));
		List<TTpSpeseProcessuali> lista = getHibernateTemplate().findByCriteria(criteria);
		if(lista != null && !lista.isEmpty()) {
			return true;
		}
		return false;
	}

}
