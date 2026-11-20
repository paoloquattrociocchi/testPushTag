package it.giustizia.smp.dao;

import it.giustizia.smp.model.TAnProcedente;
import it.giustizia.smp.model.TAtto;
import it.giustizia.smp.model.TAttoGenerico;
import it.giustizia.smp.model.TAttoId;
import it.giustizia.smp.model.TCauzione;
import it.giustizia.smp.model.TCauzioneId;
import it.giustizia.smp.model.TDecreto;
import it.giustizia.smp.model.TIstanza;
import it.giustizia.smp.model.TIstanzaId;
import it.giustizia.smp.model.TMisuraPers;
import it.giustizia.smp.model.TPersonaFisica;
import it.giustizia.smp.model.TPersonaGiur;
import it.giustizia.smp.model.TPoliziaMsg;
import it.giustizia.smp.model.TProcedimento;
import it.giustizia.smp.model.TRelAttoMisPatr;
import it.giustizia.smp.model.TRelAttoMisPers;
import it.giustizia.smp.model.TRelProcProc;
import it.giustizia.smp.model.TRelProcSogint;
import it.giustizia.smp.model.TSoggetto;
import it.giustizia.smp.model.TTpCatAtto;
import it.giustizia.smp.model.TTpPoliziaServizi;
import it.giustizia.smp.model.TTpTipoPrevProc;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

@SuppressWarnings("unchecked")
public class PoliziaDaoImpl extends HibernateDaoSupport implements PoliziaDao {

	@Autowired
	private AttoDao attoDao;
	
	public Long insertPoliziaMsgDatabase(final int idTipoServizio, final long idProcedimento, final long idAtto, final Blob xml, final int statoMsg, final long idAutorita, final int ufficio, final String descUtente) {
		try {
			return (Long)getHibernateTemplate().execute(new HibernateCallback() {
	
				public Long doInHibernate(final Session session) throws HibernateException, SQLException {
					
					Query q = session.getNamedQuery("PG_POLIZIA_inserisci_Messaggio");
					q.setParameter("IDAUTORITA", idAutorita, Hibernate.LONG);
					q.setParameter("VIDUFFICIO", ufficio, Hibernate.INTEGER);
					q.setParameter("VIDTIPOSERV", idTipoServizio, Hibernate.INTEGER);
					q.setParameter("VIDPROC", idProcedimento, Hibernate.LONG);
					q.setParameter("VXML", xml, Hibernate.BLOB);
					q.setParameter("VIDATTO", idAtto, Hibernate.LONG);
					q.setParameter("VSTATO", statoMsg, Hibernate.INTEGER);
					q.setParameter("VERRORE", " ", Hibernate.STRING);
					q.setParameter("VUTENTE", descUtente, Hibernate.STRING);
					q.setParameter("VIDCATATTO", 0, Hibernate.INTEGER);
					q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
					List l = q.list();
					HashMap first = (HashMap) l.get(0);
					Long id = (Long) first.get("IDSER");
					return id;
				}
	        });
		} catch(Throwable t) {
			return new Long(0);
		}
	}
	
	public String updateRispostaMsgDatabase(final long idMsg, final int stato, final String descErrore){
		return (String) getHibernateTemplate().execute(new HibernateCallback() {

		public String doInHibernate(final Session session) throws HibernateException, SQLException {
			Query q = session.getNamedQuery("PG_POLIZIA_aggiornaStatoMsg");
			q.setParameter("id_mess", idMsg, Hibernate.LONG);
			byte statoB = 3;
			q.setParameter("numestato", statoB, Hibernate.BYTE);
			if(descErrore==null || descErrore.length()==0)
				q.setParameter("DESCRERRORE", null, Hibernate.STRING);
			else
				q.setParameter("DESCRERRORE", descErrore, Hibernate.STRING);
							
			q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List l = q.list();
			HashMap first = (HashMap) l.get(0);
			return (String) first.get("STATOUPDATE");
		}
		});
	}
	
	@SuppressWarnings("rawtypes")
	public List<TPoliziaMsg> getMessaggiDaInviare() {
		return (List<TPoliziaMsg>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_POLIZIA_getMsgDaInviare");
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List<TPoliziaMsg> l = q.list();
				if ((l != null) && (l.size() > 0)) {
					return l;
				}
				return null;
			}
		});
	}
	
	public TProcedimento getProcedimento(long idProcedimento, int uffOwner) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TProcedimento.class);
		criteria.add(Restrictions.eq("codiKIdProc", idProcedimento));
		criteria.add(Restrictions.eq("TAnUfficioByCodiFkIdUffOwner.codiKIdUfficio", uffOwner));
		
		List<TProcedimento> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
		{
			TProcedimento procedimento = risultati.get(0);
			return procedimento;
		}
		else
			return null;
	}
	
	public TAttoGenerico getAttoGenerico(long idAtto, int uffOwner) {
		
		TAttoGenerico record = null;
		
		Criterion c1 = Restrictions.eq("id.codiKIdAttoGen", idAtto);
		Criterion c2 = Restrictions.eq("id.codiKIdUffOwner", uffOwner);
		Object obj = attoDao.getAttoByPrimaryKey(TAttoGenerico.class, c1, c2);
		if (obj != null) {
			record = (TAttoGenerico) obj;
		}
		
		return record;
	}
	
	//serve per recuperare il dettaglio residenza del singolo soggetto 
	public TRelProcSogint getTRelProcSogint(long idProc, long idSogg, int uffOwner) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelProcSogint.class);
		criteria.add(Restrictions.eq("id.codiKIdProc", idProc));
		criteria.add(Restrictions.eq("id.codiKIdSoggInt", idSogg));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", uffOwner));
		
		List<TRelProcSogint> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
		{
			TRelProcSogint soggetto = risultati.get(0);
			return soggetto;
		}
		else
			return null;
	}
	
	//recupera la lista dei soggetti relativi ad un procedimento di un ufficio
	public List<TRelProcSogint> getTRelProcSogintList(final long idProc, final int uffOwner) {
		return (List<TRelProcSogint>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_POLIZIA_getTRelProcSogintList");
				q.setParameter("ID_PROC", idProc, Hibernate.LONG);
				q.setParameter("ID_UFF_OWNER", uffOwner, Hibernate.INTEGER);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
//				q.setResultTransformer(Transformers.aliasToBean(TRelProcSogint.class));
				List<TRelProcSogint> l = q.list();
				if ((l != null) && (l.size() > 0)) {
					return l;
				}
				return null;
			}
		});
	}
		
		
	//serve per capire se un soggetto è di tipo persona fisica o giuridica
	public String getTipoSoggetto(long idSoggetto, int uffOwner) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TSoggetto.class);
		criteria.add(Restrictions.eq("id.codiKIdSoggInt", idSoggetto));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", uffOwner));
		
		List<TSoggetto> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
		{
			TSoggetto soggetto = risultati.get(0);
			return String.valueOf(soggetto.getTTpTipoSoggetto().getCodiKIdTipoSogg());
		}
		else
			return null;
	}
	
	//recupera le informazioni relative alle persone fisiche
	public TPersonaFisica getPersonaFisica(long idSoggetto, int versioneSoggetto, int uffOwner) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TPersonaFisica.class);
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", uffOwner));
		criteria.add(Restrictions.eq("id.codiKIdPersonaFis", idSoggetto));
		criteria.add(Restrictions.eq("id.codiKIdVer", (short)versioneSoggetto));
		
		List<TPersonaFisica> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
		{
			TPersonaFisica personaFisica = risultati.get(0);
			return personaFisica;
		}
		else
			return null;
	}

	//recupera le informazioni relative alle persone fisiche
	public TPersonaFisica getPersonaFisica(long idSoggetto, int uffOwner) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TPersonaFisica.class);
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", uffOwner));
		criteria.add(Restrictions.eq("id.codiKIdPersonaFis", idSoggetto));
		criteria.setProjection(Projections.max("id.codiKIdVer") );
		List<TPersonaFisica> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
		{
			TPersonaFisica personaFisica = risultati.get(0);
			return personaFisica;
		}
		else
			return null;
	}
	
	//recupera le informazioni relative alle persone giuridiche
	public TPersonaGiur getPersonaGiuridica(long idSoggetto, int versioneSoggetto, int uffOwner) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TPersonaGiur.class);
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", uffOwner));
		criteria.add(Restrictions.eq("id.codiKIdPersGiur", idSoggetto));
		criteria.add(Restrictions.eq("id.codiKIdVer", (short)versioneSoggetto));
		
		List<TPersonaGiur> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
		{
			TPersonaGiur personaGiuridica = risultati.get(0);
			return personaGiuridica;
		}
		else
			return null;
	}

	//recupera le informazioni relative alle persone giuridiche
	public TPersonaGiur getPersonaGiuridica(long idSoggetto, int uffOwner) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TPersonaGiur.class);
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", uffOwner));
		criteria.add(Restrictions.eq("id.codiKIdPersGiur", idSoggetto));
		criteria.setProjection(Projections.max("id.codiKIdVer") );
		
		List<TPersonaGiur> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
		{
			TPersonaGiur personaGiuridica = risultati.get(0);
			return personaGiuridica;
		}
		else
			return null;
	}
	
	public TIstanza getIstanzabyId(TIstanzaId id) {
		List<TIstanza> lista = null;
		TIstanza istanza = new TIstanza();
		DetachedCriteria criteria = DetachedCriteria.forClass(TIstanza.class);
		criteria.add(Restrictions.eq("id", id));
		lista = getHibernateTemplate().findByCriteria(criteria);
		if (lista.size() > 0) {
			istanza = lista.get(0);
		}
		return istanza;
	}

	public TTpTipoPrevProc getTipoPrevenzioneProc(int codiKIdTipoNorm) {
		List<TTpTipoPrevProc> lista = null;
		TTpTipoPrevProc tipoPrevenzione = new TTpTipoPrevProc();
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpTipoPrevProc.class);
		criteria.add(Restrictions.eq("codiKIdTipoNorm", codiKIdTipoNorm));
		criteria.addOrder(Order.asc("descTipoNorm"));
		lista = getHibernateTemplate().findByCriteria(criteria);
		if (lista.size() > 0) {
			tipoPrevenzione = lista.get(0);
		}
		return tipoPrevenzione;
	}

	public TCauzione getCauzionebyId(TCauzioneId id) {
		List<TCauzione> lista = null;
		TCauzione cauzione = new TCauzione();
		DetachedCriteria criteria = DetachedCriteria.forClass(TCauzione.class);
		criteria.add(Restrictions.eq("id", id));
		lista = getHibernateTemplate().findByCriteria(criteria);
		if (lista.size() > 0) {
			cauzione = lista.get(0);
		}
		return cauzione;
	}
	
	public Long getIdUfficioDest(long idUffOwner) {
		String qry = " SELECT UFFICIO.CODI_K_ID_UFFICIO " +
					 " FROM T_AN_UFFICIO UFFICIO, T_AN_PROCEDENTE PROCEDENTE " +
					 " WHERE UFFICIO.CODI_FK_ID_TIPO_UFF = 5 " +
						    "AND UFFICIO.CODI_K_ID_UFFICIO = PROCEDENTE.CODI_FK_ID_UFFICIO " +
						    "AND PROCEDENTE.CODI_K_ID_PROCED IN ( "+
						        "SELECT DISTINCT PROCUFF.CODI_K_ID_PROCED "+
						        "FROM T_AN_PROCED_UFF PROCUFF "+
						        "WHERE PROCUFF.CODI_K_ID_UFFICIO = :idUffOwner ) ";

		SQLQuery query = getSession().createSQLQuery(qry);
		query.setParameter("idUffOwner", idUffOwner, Hibernate.LONG);

		List lista = query.list();
		if(lista==null || lista.isEmpty())
			return null;
		Long idUffDest = ((BigDecimal) lista.get(0)).longValue();
		return idUffDest;
	}

	public Long getIdUfficioDestByIdProcedente(int idProcedente) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TAnProcedente.class);
		criteria.add(Restrictions.eq("codiKIdProced", idProcedente));
		List<TAnProcedente> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
		{
			TAnProcedente procedente = risultati.get(0);
			if(procedente!=null && procedente.getTAnUfficio()!=null)
				return Long.valueOf(procedente.getTAnUfficio().getCodiKIdUfficio());
			else
				return null;
		}
		else
			return null;
	}
	
	public TDecreto getDecreto(long idDecreto, int uffOwner) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TDecreto.class);
		criteria.add(Restrictions.eq("id.codiKIdDecreto", idDecreto));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", uffOwner));
		
		List<TDecreto> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
		{
			TDecreto decreto = risultati.get(0);
			return decreto;
		}
		else
			return null;
	}

	public List<TRelAttoMisPatr> listaRelAttoMisPatrSogInt(long idPersona, long idProcedimento, long idAtto, int idUfficioUtente) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TRelAttoMisPatr.class);
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		criteria.add(Restrictions.eq("id.codiKIdAtto", idAtto));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente));
		criteria.add(Restrictions.eq("id.codiKIdSoggInt", idPersona));
		criteria.addOrder(Order.asc("id.codiKIdBene"));
		List<TRelAttoMisPatr> risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}
	
	public List<TMisuraPers> listaMisurePersonaliByIdSoggetto(long idSoggettoInteressato, long idProcedimento, long idAtto, int idUffOwn) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TMisuraPers.class);
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUffOwn));
		criteria.add(Restrictions.eq("TSoggetto.id.codiKIdSoggInt", idSoggettoInteressato));
		List<TMisuraPers> misurePersonali = getHibernateTemplate().findByCriteria(criteria);
		List<TMisuraPers> risultati = new ArrayList<TMisuraPers>();
		Set<Long> idMisuraAdded = new HashSet<Long>();
		for (TMisuraPers misuraPersonale : misurePersonali) {
			Set<TRelAttoMisPers> relazioni = misuraPersonale.getTRelAttoMisPerses();
			boolean assigned = false;
			for (TRelAttoMisPers relazione : relazioni) {
				if (relazione.getId().getCodiKIdProc() == idProcedimento && relazione.getId().getCodiKIdAtto() == idAtto) {
					assigned = true;
				}
				if (assigned) {
					//se la misura già è presente in elenco non la riaggiungo
					if (!idMisuraAdded.contains(new Long(misuraPersonale.getId().getCodiKIdMisura()))){
						idMisuraAdded.add(misuraPersonale.getId().getCodiKIdMisura());
						risultati.add(misuraPersonale);
					}
				}
			}
		}
		return risultati;
	}
	
	
	public List<TAtto> getListaSentenze() {
		return (List<TAtto>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_POLIZIA_getListaAttiDaProcedenti");
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List<TAtto> l = q.list();
				if ((l != null) && (l.size() > 0)) {
					return l;
				}
				return null;
			}
		});
	}
	
	public TPoliziaMsg getTPoliziaMsgById(final long codiKIdMsg) {
        return (TPoliziaMsg) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				Query q = session.getNamedQuery("PG_POLIZIA_get_flusso_polizia_by_id");
				q.setParameter("id_mes", codiKIdMsg, Hibernate.LONG);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List<TPoliziaMsg> l = q.list();
				if ((l != null) && (l.size() > 0)) {
					Iterator iterator = l.iterator();
					while (iterator.hasNext()) {
						Map<String, Object> row = (Map<String, Object>) iterator.next();
						TPoliziaMsg msg = (TPoliziaMsg) row.get("pol");
						return msg;
					}
				}
				return null;
				
            }
        });
    }
	
	public String registraFlussiIngressoPolizia(final long idMsg, final long idUfficio, final long idProcedimento, final long idAutorita, final java.sql.Date dataEmissione, final String descrNote, final String descrProcedura){
    	return (String) getHibernateTemplate().execute(new HibernateCallback() {
            public String doInHibernate(final Session session) throws HibernateException, SQLException {
                Query q = session.getNamedQuery(descrProcedura);
                q.setParameter("vidMsg", idMsg, Hibernate.LONG);
                q.setParameter("vidUfficio", idUfficio, Hibernate.LONG);
                q.setParameter("vidProcedimento", idProcedimento, Hibernate.LONG);
                q.setParameter("vidAutorita", idAutorita, Hibernate.LONG);
                q.setParameter("vdataEmissione", dataEmissione, Hibernate.DATE);
                q.setParameter("vdescrNote", descrNote, Hibernate.STRING);
                
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
    			HashMap first = (HashMap) l.get(0);
    			return (String) first.get("STATO");
            }
        });
    }

    public String registraFlussiIngressoPoliziaAgg(final long idMsg, final long idUfficio, final long idAutorita, final String descrNumAccert, final java.sql.Date dataEmissione, final String descrNote, final String descrProcedura){
    	return (String) getHibernateTemplate().execute(new HibernateCallback() {
            public String doInHibernate(final Session session) throws HibernateException, SQLException {
                Query q = session.getNamedQuery(descrProcedura);
                q.setParameter("vidMsg", idMsg, Hibernate.LONG);
                q.setParameter("vidUfficio", idUfficio, Hibernate.LONG);
                q.setParameter("vdescrNumAccert", descrNumAccert, Hibernate.STRING);
                q.setParameter("vidAutorita", idAutorita, Hibernate.LONG);
                q.setParameter("vdataEmissione", dataEmissione, Hibernate.DATE);
                q.setParameter("vdescrNote", descrNote, Hibernate.STRING);                
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
    			HashMap first = (HashMap) l.get(0);
    			return (String) first.get("STATO");
            }
        });
    }
    
    public TTpPoliziaServizi getTtpPoliziaServiziById(final long codiKIdServizi){
    	return (TTpPoliziaServizi) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
            	DetachedCriteria criteria = DetachedCriteria.forClass(TTpPoliziaServizi.class);
        		criteria.add(Restrictions.eq("codiKIdServizi", codiKIdServizi));
        		List<TTpPoliziaServizi> result = getHibernateTemplate().findByCriteria(criteria);
        		if(result!=null && result.size()!=0)
        			return result.get(0);
        		else
        			return null;
            }
        });
    }
    
    @SuppressWarnings("rawtypes")
    public void aggiornaFlgMps(final long idAtto, final int uffOwner, final Character flgMps){
    	getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				TAttoId attoId = new TAttoId();
				attoId.setCodiKIdAtto(idAtto);
				attoId.setCodiKIdUffOwner(uffOwner);
				TAtto attoModel = getHibernateTemplate().get(TAtto.class, attoId);
//				TAtto attoModel = new TAtto();
//				attoModel.setId(attoId);
				
				attoModel.setFlgMps(flgMps);
				
				session.saveOrUpdate(attoModel);
				session.flush();
	
				return null;
			}
		});
    }
    
    public List<BigDecimal> getUffDestinatario(final int procedente, final int uffOwner, final long idProcedimento, final String tipoProcedimento){    
    	return (List<BigDecimal>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                int idTipoProc = new Long(tipoProcedimento).intValue();
            	Query q = session.getNamedQuery("PG_POLIZIA_GET_UFFICIO_DESTINATARIO");
                q.setParameter("IDUFFOWNER", uffOwner);
                q.setParameter("IDPROCEDIMENTO", idProcedimento);
                q.setParameter("IDPROCEDENTE", procedente);
                q.setParameter("TIPOPROCEDIMENTO", idTipoProc);                  
                List l = q.list();
                if(l!=null && !l.isEmpty()){
                	if(l.size()==1 && ((BigDecimal)l.get(0)).intValue()==0)
                		return null;
                	return l;
                } else{
                	return null;
                }
            }
        });
    }
    
    public int getIdProcedente(int idUfficio){
		DetachedCriteria criteria = DetachedCriteria.forClass(TAnProcedente.class);
		criteria.add(Restrictions.eq("TAnUfficio.codiKIdUfficio", idUfficio));
		List<TAnProcedente> lista = getHibernateTemplate().findByCriteria(criteria);
		if (lista.size() > 0) {
			return lista.get(0).getCodiKIdProced();
		}
		else
			return 0;
    }
    
	public void inserisciDocumentoAllegatoAtto(final TAtto datiAtto) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
            	try {
					Query q = session.getNamedQuery("PG_POLIZIA_inserisciDocumentoAllegatoAtto");
					q.setParameter("IDPROCEMISS", datiAtto.getCodiIdProcEmiss(), Hibernate.LONG);
	                q.setParameter("IDATTO", datiAtto.getId().getCodiKIdAtto(), Hibernate.LONG);
	                q.setParameter("IDUFFOWN", new Long(datiAtto.getId().getCodiKIdUffOwner()), Hibernate.LONG);
	                q.setParameter("TIPODOCUMENTO", datiAtto.getDescTipoDocumento(), Hibernate.STRING);
	                q.setParameter("NOMEDOCUMENTO", datiAtto.getDescNomeDocumento(), Hibernate.STRING);
	                q.setParameter("DOCUMENTO", datiAtto.getFileDocumento(), Hibernate.BLOB);
	                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
	                List l = q.list();
	                return new Long(0);
            	} catch (Exception e) {
            		System.out.println("Errore inserimento allegato tabella T_ATTO");
					return null;
				}
            }
		});
	}

	public TRelProcProc getIdUffOwnerByProcAtto(final long idProc, final long idAtto) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelProcProc.class);
		criteria.add(Restrictions.eq("id.codiKIdProc1", idProc));
		criteria.add(Restrictions.eq("codiFkIdAttoAa", idAtto));
		List<TRelProcProc> risultati = getHibernateTemplate().findByCriteria(criteria);
		if(risultati!=null) 
			return risultati.get(0);
		else
			return null;
	}
	
	public TTpCatAtto getTtpCatAttoById(final long codiKIdCatAtto){
		return (TTpCatAtto) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
            	DetachedCriteria criteria = DetachedCriteria.forClass(TTpCatAtto.class);
        		criteria.add(Restrictions.eq("codiKIdCatAtto", new Long(codiKIdCatAtto).intValue()));
        		List<TTpCatAtto> result = getHibernateTemplate().findByCriteria(criteria);
        		if(result!=null && result.size()!=0)
        			return result.get(0);
        		else
        			return null;
            }
        });
	}
}
