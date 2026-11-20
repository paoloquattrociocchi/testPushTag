package it.giustizia.smp.dao;

import it.giustizia.smp.dto.flussi.SENTENZA_CASSAZIONEDto;
import it.giustizia.smp.dto.pg_flussi.FlussiInDto;
import it.giustizia.smp.dto.pg_flussi.FlussiPoliziaDto;
import it.giustizia.smp.dto.pg_flussi.FlussiToolAmministratoriDto;

import it.giustizia.smp.model.TAnComuni;
import it.giustizia.smp.model.TAnProvincia;
import it.giustizia.smp.model.TAtto;
import it.giustizia.smp.model.TAttoId;
import it.giustizia.smp.model.TDepositi;
import it.giustizia.smp.model.TFlussiIn;
import it.giustizia.smp.model.TNotifPdoc;
import it.giustizia.smp.model.TPoliziaMsg;
import it.giustizia.smp.model.TRelProcSogint;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import it.giustizia.smp.model.TBene;
import it.giustizia.smp.model.TBeneId;
import it.giustizia.smp.model.TDepositiAmm;
import it.giustizia.smp.model.TAmministratori;


/**
 * FlussiDaoImpl
 * 
 * @author Almaviva S.p.A.
 *         classe dao all'interno della quale viene fatto l'accesso al DB
 */
@SuppressWarnings("unchecked")
public class FlussiDaoImpl extends HibernateDaoSupport implements FlussiDao {

    
    @SuppressWarnings("rawtypes")
    public List flussiDaElaborare(final long vidUffOwn) {

        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_FLUSSI_flussi_da_elaborare");
                q.setParameter("vidUffOwn", vidUffOwn);
                q.setResultTransformer(Transformers.aliasToBean(FlussiInDto.class));
                List l = q.list();
                return l;
            }
        });
    }

    public TFlussiIn getFlussoIn(final long codiKIdFlussoIn) {

        return (TFlussiIn) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                TFlussiIn flusso = null;
                Query query = getSession().createSQLQuery("SELECT f.codi_k_id_flusso_in, f.data_flusso, f.codi_fk_id_tipo_flusso, " +
                		"       f.codi_fk_id_uff_mitt, f.codi_fk_id_uff_dest, f.flg_stato_flusso, " +
                		"       f.codi_fk_id_esito_flusso, f.xml_flusso.getclobval () AS xml_flusso, " +
                		"       f.desc_utente, f.data_ult_agg , nvl(f.nume_id_atto,0) as nume_id_atto " +
                		"  FROM t_flussi_in f " +
                		" WHERE f.codi_k_id_flusso_in = "
                		+ codiKIdFlussoIn).addEntity(TFlussiIn.class);
                List list = query.list();
                if ((list != null) && (list.size() > 0)) {
                    flusso = (TFlussiIn) list.get(0);
                }
                return flusso;
            }
        });
    }

    public List processFlussoDefinitivita(final long idFlussoIn, final String codUtente, final long idUffOwn) {

        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public List doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_FLUSSI_processFlussoDefinitivita");
                q.setParameter("idFlusso", idFlussoIn, Hibernate.LONG);
                q.setParameter("descUtente", codUtente, Hibernate.STRING);
                q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
//                HashMap first = (HashMap) l.get(0);
//                Long id = (Long) first.get("CODI_K_ID_PROC");
                return l;
            }
        });
    }

    public List processFlussoIncompRestAtti(final long idFlussoIn, final String codUtente, final long idUffOwn) {

        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public List doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_FLUSSI_processFlussoIncompRestAtti");
                q.setParameter("idFlusso", idFlussoIn, Hibernate.LONG);
                q.setParameter("descUtente", codUtente, Hibernate.STRING);
                q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
//                HashMap first = (HashMap) l.get(0);
//                Long id = (Long) first.get("CODI_K_ID_PROC");
                return l;
            }
        });
    }

    public List processFlussoIstanzaSoggDiffidato(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito, final Blob documento, final String tipoDocumento, final String nomeDocumento) {

        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public List doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_FLUSSI_processFlussoIstanzaSoggDiff");
                q.setParameter("idFlusso", idFlussoIn, Hibernate.LONG);
                q.setParameter("descUtente", codUtente, Hibernate.STRING);
                q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
                q.setParameter("dataDeposito", dataDeposito, Hibernate.STRING);
                if (documento != null) {
                    q.setParameter("documento", documento, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", tipoDocumento, Hibernate.STRING);
                    q.setParameter("nomeDocumento", nomeDocumento, Hibernate.STRING);
                } else {
                    q.setParameter("documento", null, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", null, Hibernate.STRING);
                    q.setParameter("nomeDocumento", null, Hibernate.STRING);
                }
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
//                HashMap first = (HashMap) l.get(0);
//                Long id = (Long) first.get("CODI_K_ID_PROC");
                return l;
            }
        });
    }

    public List processFlussoProposta(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito, final String dataRicezione, final Blob documento, final String tipoDocumento, final String nomeDocumento) {

        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public List doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_FLUSSI_processFlussoProposta");
                q.setParameter("idFlusso", idFlussoIn, Hibernate.LONG);
                q.setParameter("descUtente", codUtente, Hibernate.STRING);
                q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
                q.setParameter("dataDeposito", dataDeposito, Hibernate.STRING);
                
                q.setParameter("dataRicezione", dataRicezione, Hibernate.STRING);
                
                if (documento != null) {
                    q.setParameter("documento", documento, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", tipoDocumento, Hibernate.STRING);
                    q.setParameter("nomeDocumento", nomeDocumento, Hibernate.STRING);
                } else {
                    q.setParameter("documento", null, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", null, Hibernate.STRING);
                    q.setParameter("nomeDocumento", null, Hibernate.STRING);
                }
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
               // HashMap first = (HashMap) l.get(0);
               // Long id = (Long) first.get("CODI_K_ID_PROC");
                return l;
            }
        });
    }

    public List processFlussoFascicoloCauzione(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito, final String dataRicezione, final Blob documento, final String tipoDocumento, final String nomeDocumento) {

        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public List doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_FLUSSI_processFlussoFascicoloCauzione");
                q.setParameter("idFlusso", idFlussoIn, Hibernate.LONG);
                q.setParameter("descUtente", codUtente, Hibernate.STRING);
                q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
                q.setParameter("dataDeposito", dataDeposito, Hibernate.STRING);
                
                q.setParameter("dataRicezione", dataRicezione, Hibernate.STRING);
                
                if (documento != null) {
                    q.setParameter("documento", documento, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", tipoDocumento, Hibernate.STRING);
                    q.setParameter("nomeDocumento", nomeDocumento, Hibernate.STRING);
                } else {
                    q.setParameter("documento", null, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", null, Hibernate.STRING);
                    q.setParameter("nomeDocumento", null, Hibernate.STRING);
                }
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
               // HashMap first = (HashMap) l.get(0);
               // Long id = (Long) first.get("CODI_K_ID_PROC");
                return l;
            }
        });
    }


     
    
    public List processFlussoSequestro(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito,final String dataRicezione, final Blob documento, final String tipoDocumento, final String nomeDocumento) {

        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public List doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_FLUSSI_processFlussoSequestro");
                q.setParameter("idFlusso", idFlussoIn, Hibernate.LONG);
                q.setParameter("descUtente", codUtente, Hibernate.STRING);
                q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
                q.setParameter("dataDeposito", dataDeposito, Hibernate.STRING);
                q.setParameter("dataRicezione", dataRicezione, Hibernate.STRING);
                if (documento != null) {
                    q.setParameter("documento", documento, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", tipoDocumento, Hibernate.STRING);
                    q.setParameter("nomeDocumento", nomeDocumento, Hibernate.STRING);
                } else {
                    q.setParameter("documento", null, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", null, Hibernate.STRING);
                    q.setParameter("nomeDocumento", null, Hibernate.STRING);
                }
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
//                HashMap first = (HashMap) l.get(0);
//                Long id = (Long) first.get("CODI_K_ID_PROC");
                return l;
            }
        });
    }
    
    public List processFlussoDecisorioIGrado(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito, final Blob documento, final String tipoDocumento, final String nomeDocumento) {

        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public List doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_FLUSSI_processFlussoDecisorioIGrado");
                q.setParameter("idFlusso", idFlussoIn, Hibernate.LONG);
                q.setParameter("descUtente", codUtente, Hibernate.STRING);
                q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
                q.setParameter("dataDeposito", dataDeposito, Hibernate.STRING);
                if (documento != null) {
                    q.setParameter("documento", documento, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", tipoDocumento, Hibernate.STRING);
                    q.setParameter("nomeDocumento", nomeDocumento, Hibernate.STRING);
                } else {
                    q.setParameter("documento", null, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", null, Hibernate.STRING);
                    q.setParameter("nomeDocumento", null, Hibernate.STRING);
                }
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
//                HashMap first = (HashMap) l.get(0);
//                Long id = (Long) first.get("CODI_K_ID_PROC");
                return l;
            }
        });
    }
    
    public List processFlussoDecretoUdienza(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito, final Blob documento, final String tipoDocumento, final String nomeDocumento){
        
        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public List doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_FLUSSI_processFlussoDecretoUdienza");
                q.setParameter("idFlusso", idFlussoIn, Hibernate.LONG);
                q.setParameter("descUtente", codUtente, Hibernate.STRING);
                q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
                q.setParameter("dataDeposito", dataDeposito, Hibernate.STRING);
                if (documento != null) {
                    q.setParameter("documento", documento, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", tipoDocumento, Hibernate.STRING);
                    q.setParameter("nomeDocumento", nomeDocumento, Hibernate.STRING);
                } else {
                    q.setParameter("documento", null, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", null, Hibernate.STRING);
                    q.setParameter("nomeDocumento", null, Hibernate.STRING);
                }
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
//                HashMap first = (HashMap) l.get(0);
//                Long id = (Long) first.get("CODI_K_ID_PROC");
                return l;
            }
        });
    }
    
    public List processFlussoVerbaleUdienza(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito, final Blob documento, final String tipoDocumento, final String nomeDocumento){
        
        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public List doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_FLUSSI_processFlussoVerbaleUdienza");
                q.setParameter("idFlusso", idFlussoIn, Hibernate.LONG);
                q.setParameter("descUtente", codUtente, Hibernate.STRING);
                q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
                q.setParameter("dataDeposito", dataDeposito, Hibernate.STRING);
                if (documento != null) {
                    q.setParameter("documento", documento, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", tipoDocumento, Hibernate.STRING);
                    q.setParameter("nomeDocumento", nomeDocumento, Hibernate.STRING);
                } else {
                    q.setParameter("documento", null, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", null, Hibernate.STRING);
                    q.setParameter("nomeDocumento", null, Hibernate.STRING);
                }
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
//                HashMap first = (HashMap) l.get(0);
//                Long id = (Long) first.get("CODI_K_ID_PROC");
                return l;
            }
        });
    }
    
    public List processFlussoRemAssRuoloUdienza(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito, final Blob documento, final String tipoDocumento, final String nomeDocumento){
        
        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public List doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_FLUSSI_processFlussoRemAssRuoloUdienza");
                q.setParameter("idFlusso", idFlussoIn, Hibernate.LONG);
                q.setParameter("descUtente", codUtente, Hibernate.STRING);
                q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
                q.setParameter("dataDeposito", dataDeposito, Hibernate.STRING);
                if (documento != null) {
                    q.setParameter("documento", documento, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", tipoDocumento, Hibernate.STRING);
                    q.setParameter("nomeDocumento", nomeDocumento, Hibernate.STRING);
                } else {
                    q.setParameter("documento", null, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", null, Hibernate.STRING);
                    q.setParameter("nomeDocumento", null, Hibernate.STRING);
                }
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
//                HashMap first = (HashMap) l.get(0);
//                Long id = (Long) first.get("CODI_K_ID_PROC");
                return l;
            }
        });
    }
    
    public List processFlussoDecretoConvalidaSequestro(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito,final String dataRicezione, final Blob documento, final String tipoDocumento, final String nomeDocumento){
        
        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public List doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_FLUSSI_processFlussoDecretoConvalidaSequestro");
                q.setParameter("idFlusso", idFlussoIn, Hibernate.LONG);
                q.setParameter("descUtente", codUtente, Hibernate.STRING);
                q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
                q.setParameter("dataDeposito", dataDeposito, Hibernate.STRING);
                q.setParameter("dataRicezione", dataRicezione, Hibernate.STRING);
                if (documento != null) {
                    q.setParameter("documento", documento, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", tipoDocumento, Hibernate.STRING);
                    q.setParameter("nomeDocumento", nomeDocumento, Hibernate.STRING);
                } else {
                    q.setParameter("documento", null, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", null, Hibernate.STRING);
                    q.setParameter("nomeDocumento", null, Hibernate.STRING);
                }
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
//                HashMap first = (HashMap) l.get(0);
//                Long id = (Long) first.get("CODI_K_ID_PROC");
                return l;
            }
        });
    }
    
    public List processFlussoPerditaEfficaciaSequestro(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito,final String dataRicezione, final Blob documento, final String tipoDocumento, final String nomeDocumento){
        
        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public List doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_FLUSSI_processFlussoPerditaEfficaciaSequestro");
                q.setParameter("idFlusso", idFlussoIn, Hibernate.LONG);
                q.setParameter("descUtente", codUtente, Hibernate.STRING);
                q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
                q.setParameter("dataDeposito", dataDeposito, Hibernate.STRING);
                q.setParameter("dataRicezione", dataRicezione, Hibernate.STRING);
                if (documento != null) {
                    q.setParameter("documento", documento, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", tipoDocumento, Hibernate.STRING);
                    q.setParameter("nomeDocumento", nomeDocumento, Hibernate.STRING);
                } else {
                    q.setParameter("documento", null, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", null, Hibernate.STRING);
                    q.setParameter("nomeDocumento", null, Hibernate.STRING);
                }
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                return l;
            }
        });
    }    
    
public List processFlussoDecretoRevocaSequestro(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito, final String dataRicezione,final Blob documento, final String tipoDocumento, final String nomeDocumento){
        
        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public List doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_FLUSSI_processFlussoDecretoRevocaSequestro");
                q.setParameter("idFlusso", idFlussoIn, Hibernate.LONG);
                q.setParameter("descUtente", codUtente, Hibernate.STRING);
                q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
                q.setParameter("dataDeposito", dataDeposito, Hibernate.STRING);
                q.setParameter("dataRicezione", dataRicezione, Hibernate.STRING);
                if (documento != null) {
                    q.setParameter("documento", documento, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", tipoDocumento, Hibernate.STRING);
                    q.setParameter("nomeDocumento", nomeDocumento, Hibernate.STRING);
                } else {
                    q.setParameter("documento", null, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", null, Hibernate.STRING);
                    q.setParameter("nomeDocumento", null, Hibernate.STRING);
                }
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                return l;
            }
        });
    }


	public List processFlussoDecretoRigettoTotaleSequestro (final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito, final Blob documento, final String tipoDocumento, final String nomeDocumento){
		   
			return (List) getHibernateTemplate().execute(new HibernateCallback() {

	            public List doInHibernate(final Session session) throws HibernateException, SQLException {

	                Query q = session.getNamedQuery("PG_FLUSSI_processFlussoDecretoRigettoTotaleSequestro");
	                q.setParameter("idFlusso", idFlussoIn, Hibernate.LONG);
	                q.setParameter("descUtente", codUtente, Hibernate.STRING);
	                q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
	                q.setParameter("dataDeposito", dataDeposito, Hibernate.STRING);
	                if (documento != null) {
	                    q.setParameter("documento", documento, Hibernate.BLOB);
	                    q.setParameter("tipoDocumento", tipoDocumento, Hibernate.STRING);
	                    q.setParameter("nomeDocumento", nomeDocumento, Hibernate.STRING);
	                } else {
	                    q.setParameter("documento", null, Hibernate.BLOB);
	                    q.setParameter("tipoDocumento", null, Hibernate.STRING);
	                    q.setParameter("nomeDocumento", null, Hibernate.STRING);
	                }
	                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
	                List l = q.list();
	                return l;
	            }
	        });
		}
	
    public List processFlussoOpposizioneDissequestro(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito, final Blob documento, final String tipoDocumento, final String nomeDocumento) {

        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public List doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_FLUSSI_processFlussoOpposizioneDissequestro");
                q.setParameter("idFlusso", idFlussoIn, Hibernate.LONG);
                q.setParameter("descUtente", codUtente, Hibernate.STRING);
                q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
                q.setParameter("dataDeposito", dataDeposito, Hibernate.STRING);
                if (documento != null) {
                    q.setParameter("documento", documento, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", tipoDocumento, Hibernate.STRING);
                    q.setParameter("nomeDocumento", nomeDocumento, Hibernate.STRING);
                } else {
                    q.setParameter("documento", null, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", null, Hibernate.STRING);
                    q.setParameter("nomeDocumento", null, Hibernate.STRING);
                }
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                return l;
            }
        });
    }	
    
    public List processFlussoRevocaOpposizioneDissequestro(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito, final Blob documento, final String tipoDocumento, final String nomeDocumento) {

        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public List doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_FLUSSI_processFlussoRevocaOpposizioneDissequestro");
                q.setParameter("idFlusso", idFlussoIn, Hibernate.LONG);
                q.setParameter("descUtente", codUtente, Hibernate.STRING);
                q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
                q.setParameter("dataDeposito", dataDeposito, Hibernate.STRING);
                if (documento != null) {
                    q.setParameter("documento", documento, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", tipoDocumento, Hibernate.STRING);
                    q.setParameter("nomeDocumento", nomeDocumento, Hibernate.STRING);
                } else {
                    q.setParameter("documento", null, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", null, Hibernate.STRING);
                    q.setParameter("nomeDocumento", null, Hibernate.STRING);
                }
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                return l;
            }
        });
    }	    
    
	public List processFlussoRevocazioneConfisca(final long idFlussoIn,
			final String codUtente, final long idUffOwn, final String dataDeposito,
			final Blob documento, final String tipoDocumento, final String nomeDocumento) {
		
		return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public List doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_FLUSSI_processFlussoRevocazioneConfisca");
                q.setParameter("idFlusso", idFlussoIn, Hibernate.LONG);
                q.setParameter("descUtente", codUtente, Hibernate.STRING);
                q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
                q.setParameter("dataDeposito", dataDeposito, Hibernate.STRING);
                if (documento != null) {
                    q.setParameter("documento", documento, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", tipoDocumento, Hibernate.STRING);
                    q.setParameter("nomeDocumento", nomeDocumento, Hibernate.STRING);
                } else {
                    q.setParameter("documento", null, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", null, Hibernate.STRING);
                    q.setParameter("nomeDocumento", null, Hibernate.STRING);
                }
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                return l;
            }
        });
	}       
    
    public List processFlussoSentenzaOpposizioneDissequestro(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito, final Blob documento, final String tipoDocumento, final String nomeDocumento) {

        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public List doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_FLUSSI_processFlussoSentenzaOpposizioneDissequestro");
                q.setParameter("idFlusso", idFlussoIn, Hibernate.LONG);
                q.setParameter("descUtente", codUtente, Hibernate.STRING);
                q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
                q.setParameter("dataDeposito", dataDeposito, Hibernate.STRING);
                if (documento != null) {
                    q.setParameter("documento", documento, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", tipoDocumento, Hibernate.STRING);
                    q.setParameter("nomeDocumento", nomeDocumento, Hibernate.STRING);
                } else {
                    q.setParameter("documento", null, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", null, Hibernate.STRING);
                    q.setParameter("nomeDocumento", null, Hibernate.STRING);
                }
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                return l;
            }
        });
    }
    
    public List processFlussoSentenzaRevocazioneConfisca(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito, final Blob documento, final String tipoDocumento, final String nomeDocumento) {

        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public List doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_FLUSSI_processFlussoSentenzaRevocazioneConfisca");
                q.setParameter("idFlusso", idFlussoIn, Hibernate.LONG);
                q.setParameter("descUtente", codUtente, Hibernate.STRING);
                q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
                q.setParameter("dataDeposito", dataDeposito, Hibernate.STRING);
                if (documento != null) {
                    q.setParameter("documento", documento, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", tipoDocumento, Hibernate.STRING);
                    q.setParameter("nomeDocumento", nomeDocumento, Hibernate.STRING);
                } else {
                    q.setParameter("documento", null, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", null, Hibernate.STRING);
                    q.setParameter("nomeDocumento", null, Hibernate.STRING);
                }
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                return l;
            }
        });
    }	    
    
    public List processFlussoRicorsoInAppello(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito, final Blob documento, final String tipoDocumento, final String nomeDocumento,final long idProcedimentoCda) {

        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public List doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_FLUSSI_processFlussoRicorsoInAppello");
                q.setParameter("idFlusso", idFlussoIn, Hibernate.LONG);
                q.setParameter("descUtente", codUtente, Hibernate.STRING);
                q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
                q.setParameter("dataDeposito", dataDeposito, Hibernate.STRING);
                if (documento != null) {
                    q.setParameter("documento", documento, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", tipoDocumento, Hibernate.STRING);
                    q.setParameter("nomeDocumento", nomeDocumento, Hibernate.STRING);
                } else {
                    q.setParameter("documento", null, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", null, Hibernate.STRING);
                    q.setParameter("nomeDocumento", null, Hibernate.STRING);
                }
                
				if (idProcedimentoCda > 0) {
					q.setParameter("idProcedimentoCda",idProcedimentoCda, Hibernate.LONG);
				} else {
					q.setParameter("idProcedimentoCda", null, Hibernate.LONG);
				}
				
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                return l;
            }
        });
    }
    
    
     
    public List processFlussoDecDecisorioIIGrado(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito, final String dataRicezione, final Blob documento, final String tipoDocumento, final String nomeDocumento) {

        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public List doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_FLUSSI_processFlussoDecDecisorioIIGrado");
                q.setParameter("idFlusso", idFlussoIn, Hibernate.LONG);
                q.setParameter("descUtente", codUtente, Hibernate.STRING);
                q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
                q.setParameter("dataDeposito", dataDeposito, Hibernate.STRING);
                q.setParameter("dataRicezione", dataRicezione, Hibernate.STRING);
                
                if (documento != null) {
                    q.setParameter("documento", documento, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", tipoDocumento, Hibernate.STRING);
                    q.setParameter("nomeDocumento", nomeDocumento, Hibernate.STRING);
                } else {
                    q.setParameter("documento", null, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", null, Hibernate.STRING);
                    q.setParameter("nomeDocumento", null, Hibernate.STRING);
                }
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                return l;
            }
        });
    } 
    
    
    public List processFlussoProvvIstFascicolo(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito, final String dataRicezione, final Blob documento, final String tipoDocumento, final String nomeDocumento) {

        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public List doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_FLUSSI_processFlussoProvvIstFascicolo");
                q.setParameter("idFlusso", idFlussoIn, Hibernate.LONG);
                q.setParameter("descUtente", codUtente, Hibernate.STRING);
                q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
                q.setParameter("dataDeposito", dataDeposito, Hibernate.STRING);
                q.setParameter("dataRicezione", dataRicezione, Hibernate.STRING);
                
                if (documento != null) {
                    q.setParameter("documento", documento, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", tipoDocumento, Hibernate.STRING);
                    q.setParameter("nomeDocumento", nomeDocumento, Hibernate.STRING);
                } else {
                    q.setParameter("documento", null, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", null, Hibernate.STRING);
                    q.setParameter("nomeDocumento", null, Hibernate.STRING);
                }
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                return l;
            }
        });
    }      
    
    public List processFlussoAttoRifiutato(final long idFlussoIn, final String codUtente, final long idUffOwn) {

        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public List doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_FLUSSI_processFlussoAttoRifiutato");
                q.setParameter("idFlusso", idFlussoIn, Hibernate.LONG);
                q.setParameter("descUtente", codUtente, Hibernate.STRING);
                q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);

                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                return l;
            }
        });
    } 
    
    public List processFlussoParere(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito, final Blob documento, final String tipoDocumento, final String nomeDocumento) {

        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public List doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_FLUSSI_processFlussoParere");
                q.setParameter("idFlusso", idFlussoIn, Hibernate.LONG);
                q.setParameter("descUtente", codUtente, Hibernate.STRING);
                q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
                q.setParameter("dataDeposito", dataDeposito, Hibernate.STRING);
                if (documento != null) {
                    q.setParameter("documento", documento, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", tipoDocumento, Hibernate.STRING);
                    q.setParameter("nomeDocumento", nomeDocumento, Hibernate.STRING);
                } else {
                    q.setParameter("documento", null, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", null, Hibernate.STRING);
                    q.setParameter("nomeDocumento", null, Hibernate.STRING);
                }
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                return l;
            }
        });
    }   
    
    public List processFlussoVersionamento(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataRicezione, final Blob documento, final String tipoDocumento, final String nomeDocumento) {

        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public List doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_FLUSSI_processFlussoVersionamento");
                q.setParameter("idFlusso", idFlussoIn, Hibernate.LONG);
                q.setParameter("descUtente", codUtente, Hibernate.STRING);
                q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
                q.setParameter("dataRicezione", dataRicezione, Hibernate.STRING);
                if (documento != null) {
                    q.setParameter("documento", documento, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", tipoDocumento, Hibernate.STRING);
                    q.setParameter("nomeDocumento", nomeDocumento, Hibernate.STRING);
                } else {
                    q.setParameter("documento", null, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", null, Hibernate.STRING);
                    q.setParameter("nomeDocumento", null, Hibernate.STRING);
                }
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                return l;
            }
        });
    }  
    
    public List processFlussoRicorsoInCassazione(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito, final Blob documento, final String tipoDocumento, final String nomeDocumento) {

        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public List doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_FLUSSI_processFlussoRicorsoInCassazione");
                q.setParameter("idFlusso", idFlussoIn, Hibernate.LONG);
                q.setParameter("descUtente", codUtente, Hibernate.STRING);
                q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
                q.setParameter("dataDeposito", dataDeposito, Hibernate.STRING);
                if (documento != null) {
                    q.setParameter("documento", documento, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", tipoDocumento, Hibernate.STRING);
                    q.setParameter("nomeDocumento", nomeDocumento, Hibernate.STRING);
                } else {
                    q.setParameter("documento", null, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", null, Hibernate.STRING);
                    q.setParameter("nomeDocumento", null, Hibernate.STRING);
                }
                
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                return l;
            }
        });
    }  
    
    public List processFlussoProrogaSequestro(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataRicezione, final Blob documento, final String tipoDocumento, final String nomeDocumento) {

        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public List doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_FLUSSI_processFlussoProrogaSequestro");
                q.setParameter("idFlusso", idFlussoIn, Hibernate.LONG);
                q.setParameter("descUtente", codUtente, Hibernate.STRING);
                q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
                q.setParameter("dataRicezione", dataRicezione, Hibernate.STRING);
                if (documento != null) {
                    q.setParameter("documento", documento, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", tipoDocumento, Hibernate.STRING);
                    q.setParameter("nomeDocumento", nomeDocumento, Hibernate.STRING);
                } else {
                    q.setParameter("documento", null, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", null, Hibernate.STRING);
                    q.setParameter("nomeDocumento", null, Hibernate.STRING);
                }
                
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                return l;
            }
        });
    }     
    
    public List processFlussoSospensioneTemporanea(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito,final String dataRicezione, final Blob documento, final String tipoDocumento, final String nomeDocumento) {

        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public List doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_FLUSSI_processFlussoSospensioneTemporanea");
                q.setParameter("idFlusso", idFlussoIn, Hibernate.LONG);
                q.setParameter("descUtente", codUtente, Hibernate.STRING);
                q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
                q.setParameter("dataDeposito", dataDeposito, Hibernate.STRING);
                q.setParameter("dataRicezione", dataRicezione, Hibernate.STRING);
                if (documento != null) {
                    q.setParameter("documento", documento, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", tipoDocumento, Hibernate.STRING);
                    q.setParameter("nomeDocumento", nomeDocumento, Hibernate.STRING);
                } else {
                    q.setParameter("documento", null, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", null, Hibernate.STRING);
                    q.setParameter("nomeDocumento", null, Hibernate.STRING);
                }
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
//                HashMap first = (HashMap) l.get(0);
//                Long id = (Long) first.get("CODI_K_ID_PROC");
                return l;
            }
        });
    }
    
    public List processFlussoProrogaSospensioneTemporanea(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito,final String dataRicezione, final Blob documento, final String tipoDocumento, final String nomeDocumento) {

        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public List doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_FLUSSI_processFlussoProrogaSospensioneTemporanea");
                q.setParameter("idFlusso", idFlussoIn, Hibernate.LONG);
                q.setParameter("descUtente", codUtente, Hibernate.STRING);
                q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
                q.setParameter("dataRicezione", dataRicezione, Hibernate.STRING);
                if (documento != null) {
                    q.setParameter("documento", documento, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", tipoDocumento, Hibernate.STRING);
                    q.setParameter("nomeDocumento", nomeDocumento, Hibernate.STRING);
                } else {
                    q.setParameter("documento", null, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", null, Hibernate.STRING);
                    q.setParameter("nomeDocumento", null, Hibernate.STRING);
                }
                
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                return l;
            }
        });
    }        

    public List processFlussoSequestroSospensioneTemporanea(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito,final String dataRicezione, final Blob documento, final String tipoDocumento, final String nomeDocumento) {

        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public List doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_FLUSSI_processFlussoSequestroSospensioneTemporanea");
                q.setParameter("idFlusso", idFlussoIn, Hibernate.LONG);
                q.setParameter("descUtente", codUtente, Hibernate.STRING);
                q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
                q.setParameter("dataRicezione", dataRicezione, Hibernate.STRING);
                if (documento != null) {
                    q.setParameter("documento", documento, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", tipoDocumento, Hibernate.STRING);
                    q.setParameter("nomeDocumento", nomeDocumento, Hibernate.STRING);
                } else {
                    q.setParameter("documento", null, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", null, Hibernate.STRING);
                    q.setParameter("nomeDocumento", null, Hibernate.STRING);
                }
                
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                return l;
            }
        });
    } 
    
    public List processFlussoApplicazioneProvv(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito,final String dataRicezione, final Blob documento, final String tipoDocumento, final String nomeDocumento) {

        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public List doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_FLUSSI_processFlussoApplicazioneProvv");
                q.setParameter("idFlusso", idFlussoIn, Hibernate.LONG);
                q.setParameter("descUtente", codUtente, Hibernate.STRING);
                q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
                q.setParameter("dataDeposito", dataDeposito, Hibernate.STRING);
                q.setParameter("dataRicezione", dataRicezione, Hibernate.STRING);
                if (documento != null) {
                    q.setParameter("documento", documento, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", tipoDocumento, Hibernate.STRING);
                    q.setParameter("nomeDocumento", nomeDocumento, Hibernate.STRING);
                } else {
                    q.setParameter("documento", null, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", null, Hibernate.STRING);
                    q.setParameter("nomeDocumento", null, Hibernate.STRING);
                }
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();

                return l;
            }
        });
    } 
    
    public List processFlussoSentenzaCassazione(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito,final String dataRicezione, final Blob documento, final String tipoDocumento, final String nomeDocumento) {

        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public List doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_FLUSSI_processFlussoSentenzaCassazione");
                q.setParameter("idFlusso", idFlussoIn, Hibernate.LONG);
                q.setParameter("descUtente", codUtente, Hibernate.STRING);
                q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
                q.setParameter("dataDeposito", dataDeposito, Hibernate.STRING);
                q.setParameter("dataRicezione", dataRicezione, Hibernate.STRING);
                if (documento != null) {
                    q.setParameter("documento", documento, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", tipoDocumento, Hibernate.STRING);
                    q.setParameter("nomeDocumento", nomeDocumento, Hibernate.STRING);
                } else {
                    q.setParameter("documento", null, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", null, Hibernate.STRING);
                    q.setParameter("nomeDocumento", null, Hibernate.STRING);
                }
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();

                return l;
            }
        });
    }
    
    
    public List dettaglioFlussoSentenza(final long idFlusso,final int idUffOwn,final String descUtente) {

        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_FLUSSI_dettaglioFlussoSentenza");
                q.setParameter("idFlusso", idFlusso);
                q.setParameter("idUffOwn", idUffOwn);
                q.setParameter("descUtente", descUtente);
                
                q.setResultTransformer(Transformers.aliasToBean(SENTENZA_CASSAZIONEDto.class));
                List l = q.list();
                return l;
            }
        });
    } 

    public  List<TFlussiIn> ricercaTFlussiIn(final long idAtto,final int idUffOwn,final long tipoFlusso) {

        return ( List<TFlussiIn>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_FLUSSI_ricercaTFlussiIn");
                q.setParameter("IDUFFOWN", idUffOwn);
                q.setParameter("TIPOFLUSSO", tipoFlusso);
                q.setParameter("IDATTO", idAtto);
                 
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List list = q.list();
   
                return list;

            }
        });
    } 
    
    
    public List processFlussoProcCDAdaCass(final long idFlussoIn, final String codUtente, final long idUffOwn) {

        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public List doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_FLUSSI_processFlussoProcCDAdaCass");
                q.setParameter("idFlusso", idFlussoIn, Hibernate.LONG);
                q.setParameter("descUtente", codUtente, Hibernate.STRING);
                q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                return l;
            }
        });
    }   
    
    public List processFlussoProcTridaCass(final long idFlussoIn, final String codUtente, final long idUffOwn) {

        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public List doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_FLUSSI_processFlussoProcTridaCass");
                q.setParameter("idFlusso", idFlussoIn, Hibernate.LONG);
                q.setParameter("descUtente", codUtente, Hibernate.STRING);
                q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                return l;
            }
        });
    }  
    
    public List processFlussoProcTridaCdA(final long idFlussoIn, final String codUtente, final long idUffOwn) {

        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public List doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_FLUSSI_processFlussoProcTridaCdA");
                q.setParameter("idFlusso", idFlussoIn, Hibernate.LONG);
                q.setParameter("descUtente", codUtente, Hibernate.STRING);
                q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                return l;
            }
        });
    }          
    public List processFlussoParereFascicolo(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito, final Blob documento, final String tipoDocumento, final String nomeDocumento) {

        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public List doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_FLUSSI_processFlussoParereFascicolo");
                q.setParameter("idFlusso", idFlussoIn, Hibernate.LONG);
                q.setParameter("descUtente", codUtente, Hibernate.STRING);
                q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
                q.setParameter("dataDeposito", dataDeposito, Hibernate.STRING);
                if (documento != null) {
                    q.setParameter("documento", documento, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", tipoDocumento, Hibernate.STRING);
                    q.setParameter("nomeDocumento", nomeDocumento, Hibernate.STRING);
                } else {
                    q.setParameter("documento", null, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", null, Hibernate.STRING);
                    q.setParameter("nomeDocumento", null, Hibernate.STRING);
                }
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                return l;
            }
        });
    }  
 public List processFlussoUdienzaFascicolo(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito, final Blob documento, final String tipoDocumento, final String nomeDocumento){
        
        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public List doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_FLUSSI_processFlussoUdienzaFascicolo");
                q.setParameter("idFlusso", idFlussoIn, Hibernate.LONG);
                q.setParameter("descUtente", codUtente, Hibernate.STRING);
                q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
                q.setParameter("dataDeposito", dataDeposito, Hibernate.STRING);
                if (documento != null) {
                    q.setParameter("documento", documento, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", tipoDocumento, Hibernate.STRING);
                    q.setParameter("nomeDocumento", nomeDocumento, Hibernate.STRING);
                } else {
                    q.setParameter("documento", null, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", null, Hibernate.STRING);
                    q.setParameter("nomeDocumento", null, Hibernate.STRING);
                }
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
//                HashMap first = (HashMap) l.get(0);
//                Long id = (Long) first.get("CODI_K_ID_PROC");
                return l;
            }
        });
    }    
 public List processFlussoDecisorioIGradoFascicolo(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito, final Blob documento, final String tipoDocumento, final String nomeDocumento) {

     return (List) getHibernateTemplate().execute(new HibernateCallback() {

         public List doInHibernate(final Session session) throws HibernateException, SQLException {

             Query q = session.getNamedQuery("PG_FLUSSI_processFlussoDecIGFascicolo");
             q.setParameter("idFlusso", idFlussoIn, Hibernate.LONG);
             q.setParameter("descUtente", codUtente, Hibernate.STRING);
             q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
             q.setParameter("dataDeposito", dataDeposito, Hibernate.STRING);
             if (documento != null) {
                 q.setParameter("documento", documento, Hibernate.BLOB);
                 q.setParameter("tipoDocumento", tipoDocumento, Hibernate.STRING);
                 q.setParameter("nomeDocumento", nomeDocumento, Hibernate.STRING);
             } else {
                 q.setParameter("documento", null, Hibernate.BLOB);
                 q.setParameter("tipoDocumento", null, Hibernate.STRING);
                 q.setParameter("nomeDocumento", null, Hibernate.STRING);
             }
             q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
             List l = q.list();
//             HashMap first = (HashMap) l.get(0);
//             Long id = (Long) first.get("CODI_K_ID_PROC");
             return l;
         }
     });
 }
 
	public TDepositi getDeposito(Integer codiFkIdUffMit, Integer codiFkIdUffDes, long numeCopiaPdoc) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(TDepositi.class);
		criteria.add(Restrictions.eq("TAnUfficioByCodiFkIdUffMit.codiKIdUfficio", codiFkIdUffMit));
		criteria.add(Restrictions.eq("TAnUfficioByCodiFkIdUffDes.codiKIdUfficio", codiFkIdUffDes));
		criteria.add(Restrictions.eq("numeCopiaPdoc", numeCopiaPdoc));
		
		List<TDepositi> result = getHibernateTemplate().findByCriteria(criteria);
		if (result.size() > 0)
		{
			return result.get(0);
		}
		else
			return null;
	}
	
	public TNotifPdoc getNotificaPDoc(Integer codiKIdUffOwner, long codiKIdAtto, Integer codiFkIdUffMitt) {
				
		DetachedCriteria criteria = DetachedCriteria.forClass(TNotifPdoc.class);
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", codiKIdUffOwner));
		criteria.add(Restrictions.eq("id.codiKIdAtto", codiKIdAtto));		
		criteria.add(Restrictions.eq("codiFkIdUffMitt", codiFkIdUffMitt));
		
		List<TNotifPdoc> result = getHibernateTemplate().findByCriteria(criteria);
		if (result.size() > 0)
		{			
			return result.get(0);
		}
		else
			return null;
	}
	
	public void updateDeposito(final TDepositi deposito){
		
		getHibernateTemplate().execute(new HibernateCallback<Long>() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {
				
				session.saveOrUpdate(deposito);
				session.flush();
				
				return deposito.getCodiKIdDeposito();
			}
		});
	}
 
	@SuppressWarnings("rawtypes")
    public List getListFlussiPolizia(final long vidUffOwn, final String vflgDirezione) {
        return (List) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                Query q = session.getNamedQuery("PG_POLIZIA_flussi_polizia");
                q.setParameter("vidUffOwn", vidUffOwn, Hibernate.LONG);
                q.setParameter("vflgDirezione", vflgDirezione, Hibernate.STRING);
                q.setResultTransformer(Transformers.aliasToBean(FlussiPoliziaDto.class));
                List l = q.list();
                return l;
            }
        });
    }
    
	public Blob getXmlFlussoPolizia(final long codiKIdMsg) {
        return (Blob) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                Query query = getSession().createSQLQuery("" +
	                		"SELECT " +
						         "xmlserialize(document POLIZIA.XML_DATI as BLOB indent size = 2) as XML_DATI "+
						    "FROM " +
						    	"t_polizia_msg POLIZIA "+
						    "WHERE POLIZIA.CODI_K_ID_MSG = "+codiKIdMsg);

                List list = query.list();
                if ((list != null) && (list.size() > 0)) {
                	return (Blob)list.get(0);
                }
                return null;
            }
        });
    }
	
	

    @SuppressWarnings("rawtypes")
    public List listaFlussiToolAmministratori(final long vidUffOwn) {

        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_FLUSSI_listaFlussiToolAmministratori");
                q.setParameter("vidUffOwn", vidUffOwn);
                q.setResultTransformer(Transformers.aliasToBean(FlussiToolAmministratoriDto.class));
                List l = q.list();
                return l;
            }
        });
    }
    
	public TDepositi getDettaglioDeposito(final long idDeposito, final int idUffOwn) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(TDepositi.class);
		criteria.add(Restrictions.eq("codiKIdDeposito", idDeposito));
		criteria.add(Restrictions.eq("TAnUfficioByCodiFkIdUffDes.codiKIdUfficio", idUffOwn));
		
		List<TDepositi> result = getHibernateTemplate().findByCriteria(criteria);
		if (result.size() > 0)
		{
			return result.get(0);
		}
		else
			return null;
	}    
    
	public TDepositiAmm getDettaglioDepositoAmm(final long idDeposito,final char flag) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(TDepositiAmm.class);
		criteria.add(Restrictions.eq("codiKIdDepositoAmm", idDeposito));
		criteria.add(Restrictions.eq("flgAcquisito", flag));
		
		List<TDepositiAmm> result = getHibernateTemplate().findByCriteria(criteria);
		if (result.size() > 0)
		{
			return result.get(0);
		}
		else
			return null;
	}    
    
	public TAmministratori getDettaglioAmministratore(final String codiceFiscale) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(TAmministratori.class);
		criteria.add(Restrictions.eq("descCodiceFiscale", codiceFiscale));
		
		List<TAmministratori> result = getHibernateTemplate().findByCriteria(criteria);
		if (result.size() > 0)
		{
			return result.get(0);
		}
		else
			return null;
	}    
	
	public Blob getAllegatoDeposito(final long idDeposito) {
        return (Blob) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                Query query = getSession().createSQLQuery("" +
	                		"SELECT " +
						         "PDF_TOOL_AMM as ALLEGATO "+
						    "FROM " +
						    	"T_DEPOSITI_AMM DP "+
						    "WHERE DP.CODI_K_ID_DEPOSITO_AMM = "+idDeposito);

                List list = query.list();
                if ((list != null) && (list.size() > 0)) {
                	return (Blob)list.get(0);
                }
                return null;
            }
        });
    }

	public Blob getXMLFileDeposito(final long idDeposito) {
        return (Blob) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                Query query = getSession().createSQLQuery("" +
	                		"SELECT " +
						         "XML_TOOL_AMM as XMLFile "+
						    "FROM " +
						    	"T_DEPOSITI_AMM DP "+
						    "WHERE DP.CODI_K_ID_DEPOSITO_AMM = "+idDeposito);

                List list = query.list();
                if ((list != null) && (list.size() > 0)) {
                	return (Blob)list.get(0);
                }
                return null;
            }
        });
    }
	
	public TBene getDescrizioneBene(final long idBene,final int idUffOwn) {
		
		TBeneId id = new TBeneId(idUffOwn,idBene);
		
		DetachedCriteria criteria = DetachedCriteria.forClass(TBene.class);		
		criteria.add(Restrictions.eq("id", id));
		
		List<TBene> result = getHibernateTemplate().findByCriteria(criteria);
		if (result.size() > 0)
		{			
			return result.get(0);
		}
		else
			return null;
	} 

	public void updateDepositoAmm(final TDepositiAmm depositoAmm){
		
		getHibernateTemplate().execute(new HibernateCallback<Long>() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {
				
				session.saveOrUpdate(depositoAmm);
				session.flush();
				
				return depositoAmm.getCodiKIdDepositoAmm();
			}
		});
	}
	
    public List<Map> getListaDepositoByIdProc(final long idProcedimento,final int idUffOwn,final char flag) {
        return (List<Map>) getHibernateTemplate().execute(new HibernateCallback<List<Map>>() {
            public List<Map> doInHibernate(final Session session) throws HibernateException, SQLException {
                Query q = session.getNamedQuery("PG_FLUSSI_listaDepositoByProc");
                q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);               
                q.setParameter("idUffOwn",idUffOwn, Hibernate.INTEGER);
                q.setParameter("flag",flag, Hibernate.CHARACTER);                
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List<Map> l = q.list();
                return l;
            }
        });
    }

	
	public TDepositiAmm getDepositoAmmByIdAtto(final long codAtto,final int idUffOwner) {
		
		TAttoId attoId = new TAttoId(codAtto,idUffOwner); 
		TAtto atto = new TAtto();
		atto.setId(attoId);
		
		DetachedCriteria criteria = DetachedCriteria.forClass(TDepositiAmm.class);		
		criteria.add(Restrictions.eq("TAtto", atto));
		
		List<TDepositiAmm> result = getHibernateTemplate().findByCriteria(criteria);
		if (result.size() > 0)
		{			
			return result.get(0);
		}
		else
			return null;
	} 
	
	public List processFlussoNRGCassazione(final long idFlussoIn, final String codUtente, final long idUffOwn) {

        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public List doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_FLUSSI_processFlussoNRGCassazione");
                q.setParameter("idFlusso", idFlussoIn, Hibernate.LONG);
                q.setParameter("descUtente", codUtente, Hibernate.STRING);
                q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                return l;
            }
        });
    }
	
}
