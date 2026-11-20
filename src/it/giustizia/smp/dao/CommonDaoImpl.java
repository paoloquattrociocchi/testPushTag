package it.giustizia.smp.dao;

import it.giustizia.smp.dto.ChiaveRicorsoCassazioneDto;
import it.giustizia.smp.dto.KeyMessageDto;
import it.giustizia.smp.form.BeneForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.model.TAnAltriOper;
import it.giustizia.smp.model.TAnComuni;
import it.giustizia.smp.model.TAnConservatorie;
import it.giustizia.smp.model.TAnDistretto;
import it.giustizia.smp.model.TAnIstitutoDet;
import it.giustizia.smp.model.TAnMagistrati;
import it.giustizia.smp.model.TAnNormative;
import it.giustizia.smp.model.TAnOperFinanz;
import it.giustizia.smp.model.TAnProcedente;
import it.giustizia.smp.model.TAnProvincia;
import it.giustizia.smp.model.TAnStato;
import it.giustizia.smp.model.TAnUfficio;
import it.giustizia.smp.model.TAnUtenzePdoc;
import it.giustizia.smp.model.TPersonaFisica;
import it.giustizia.smp.model.TRelMagUff;
import it.giustizia.smp.model.TRelProcTrib;
import it.giustizia.smp.model.TTpCatAtto;
import it.giustizia.smp.model.TTpCatBene;
import it.giustizia.smp.model.TTpCatCatasto;
import it.giustizia.smp.model.TTpCondGiur;
import it.giustizia.smp.model.TTpEsitoUdienza;
import it.giustizia.smp.model.TTpModPerv;
import it.giustizia.smp.model.TTpOrdineProf;
import it.giustizia.smp.model.TTpProfessione;
import it.giustizia.smp.model.TTpSedeOrdine;
import it.giustizia.smp.model.TTpSettoreAtt;
import it.giustizia.smp.model.TTpSottocatBene;
import it.giustizia.smp.model.TTpSpeseProcessuali;
import it.giustizia.smp.model.TTpStatoConserv;
import it.giustizia.smp.model.TTpStatoProc;
import it.giustizia.smp.model.TTpTipoAtto;
import it.giustizia.smp.model.TTpTipoBene;
import it.giustizia.smp.model.TTpTipoCatasto;
import it.giustizia.smp.model.TTpTipoDenuncia;
import it.giustizia.smp.model.TTpTipoDisp;
import it.giustizia.smp.model.TTpTipoFascicolo;
import it.giustizia.smp.model.TTpTipoFondo;
import it.giustizia.smp.model.TTpTipoInteresse;
import it.giustizia.smp.model.TTpTipoIpotesi;
import it.giustizia.smp.model.TTpTipoIstituto;
import it.giustizia.smp.model.TTpTipoMisura;
import it.giustizia.smp.model.TTpTipoNota;
import it.giustizia.smp.model.TTpTipoOccupanti;
import it.giustizia.smp.model.TTpTipoPrevProc;
import it.giustizia.smp.model.TTpTipoPrevenzione;
import it.giustizia.smp.model.TTpTipoProc;
import it.giustizia.smp.model.TTpTipoRegime;
import it.giustizia.smp.model.TTpTipoRuoloMag;
import it.giustizia.smp.model.TTpTipoSocieta;
import it.giustizia.smp.model.TTpTipoStrada;
import it.giustizia.smp.model.TTpTipoTrasform;
import it.giustizia.smp.model.TTpTipoUfficio;
import it.giustizia.smp.model.TTpTipoVincolo;
import it.giustizia.smp.model.TTpValuta;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.DateUtils;
import it.giustizia.smp.utility.StatoComparator;
import it.giustizia.smp.utility.StatoPivaComparator;
import it.giustizia.smp.utility.TipoSocietaComparator;
import it.giustizia.smp.utility.TipoStradaComparator;
import it.giustizia.smp.utility.Utility;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class CommonDaoImpl extends HibernateDaoSupport implements CommonDao {

	@SuppressWarnings("unchecked")
	public List<TAnComuni> getAllComuni() {

		DetachedCriteria criteria = DetachedCriteria.forClass(TAnComuni.class);
		criteria.add(Restrictions.isNull("dataFineValidita")).addOrder(Order.asc("descComune"));
		List<TAnComuni> u = getHibernateTemplate().findByCriteria(criteria);
		return u;
	}
	
	@SuppressWarnings("unchecked")
	public List<TAnComuni> getAllComuneByProvincia(String codiceIstatProvincia) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TAnComuni.class);
		Criterion provincia = Restrictions.eq("TAnProvincia.codiKIdIstatProv", codiceIstatProvincia);
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
//		criteria.add(provincia).add(Restrictions.isNull("dataFineValidita")).addOrder(Order.asc("descComune"));
	    criteria.add(provincia).addOrder(Order.asc("descComune"));
		List<TAnComuni> results = getHibernateTemplate().findByCriteria(criteria);
		return results;
	}

	public List<TAnComuni> getAllComuniByOrganoAndProvincia(String codiceIstatProvincia, long idOrgano) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TAnComuni.class);
		Criterion provincia = Restrictions.eq("TAnProvincia.codiKIdIstatProv", codiceIstatProvincia);
		criteria.add(provincia).addOrder(Order.asc("descComune"));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		criteria.createAlias("TAnUfficios", "ufficio");
		criteria.add(Restrictions.eq("ufficio.TTpTipoUfficio.codiKIdTipoUff", (int) idOrgano));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<TAnComuni> results = getHibernateTemplate().findByCriteria(criteria);
		return results;
	}
	
	public List<TAnUfficio> getAllUfficiByOrganoAndComune(int codiceComune, long idOrgano) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TAnUfficio.class);
		criteria.add(Restrictions.eq("TAnComuni.codiKIdComune", codiceComune));
		criteria.add(Restrictions.eq("TTpTipoUfficio.codiKIdTipoUff", (int) idOrgano));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<TAnUfficio> results = getHibernateTemplate().findByCriteria(criteria);
		return results;
	}

	public List<TAnComuni> getAllComunyStartingWith(String pattern) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TAnComuni.class);
		Criterion provincia = Restrictions.like("descComune", pattern + "%");
		criteria.add(provincia).addOrder(Order.asc("descComune"));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		criteria.setFetchMode("TAnProvincia", FetchMode.EAGER);
		List<TAnComuni> results = getHibernateTemplate().findByCriteria(criteria);
		return results;
	}

	@SuppressWarnings("unchecked")
	public List<TTpCondGiur> getAllCondizioneGiuridica() {

		DetachedCriteria criteria = DetachedCriteria.forClass(TTpCondGiur.class);
		criteria.addOrder(Order.asc("descCondGiur"));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		List<TTpCondGiur> u = getHibernateTemplate().findByCriteria(criteria);
		return u;
	}

	@SuppressWarnings("unchecked")
	public List<TAnIstitutoDet> getAllIstitutiDetenzione() {

		DetachedCriteria criteria = DetachedCriteria.forClass(TAnIstitutoDet.class);
		criteria.addOrder(Order.asc("descIstituto"));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		List<TAnIstitutoDet> u = getHibernateTemplate().findByCriteria(criteria);
		return u;
	}

	@SuppressWarnings("unchecked")
	public List<TTpProfessione> getAllProfessione() {

		DetachedCriteria criteria = DetachedCriteria.forClass(TTpProfessione.class);
		criteria.addOrder(Order.asc("descProfessione"));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		List<TTpProfessione> u = getHibernateTemplate().findByCriteria(criteria);
		return u;
	}

	@SuppressWarnings("unchecked")
	public List<TAnProvincia> getAllProvincia() {

		DetachedCriteria criteria = DetachedCriteria.forClass(TAnProvincia.class);
		criteria.addOrder(Order.asc("descProvincia"));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		List<TAnProvincia> u = getHibernateTemplate().findByCriteria(criteria);
		return u;
	}

	public List<TAnProvincia> getProvinciaByOrgano(final long idOrgano) {

		return (List<TAnProvincia>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				List<TAnProvincia> province = null;
				String qry = "select distinct T_AN_PROVINCIA.* from T_AN_PROVINCIA,t_an_comuni,T_AN_UFFICIO, T_TP_TIPO_UFFICIO ttp_tip_uff "
						+ " where T_AN_PROVINCIA.DATA_FINE_VALIDITA is null "
						+ " and T_AN_PROVINCIA.CODI_K_ID_ISTAT_PROV = t_an_comuni.CODI_FK_ID_ISTAT_PROV "
						+ " and t_an_comuni.CODI_K_ID_COMUNE = T_AN_UFFICIO.CODI_FK_ID_COM_SEDE "
						+ " and T_AN_UFFICIO.CODI_FK_ID_TIPO_UFF = ttp_tip_uff.CODI_K_ID_TIPO_UFF "
						+ " and ttp_tip_uff.CODI_K_ID_TIPO_UFF =:id_tipo_ufficio_organo " + " order by T_AN_PROVINCIA.DESC_PROVINCIA";
				SQLQuery query = getSession().createSQLQuery(qry);
				query.addEntity(TAnProvincia.class);
				query.setParameter("id_tipo_ufficio_organo", idOrgano, Hibernate.LONG);
				return query.list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List<TAnStato> getAllStato() {

		List<TAnStato> risultati = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TAnStato.class);
		criteria.addOrder(Order.asc("descStato"));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		risultati = getHibernateTemplate().findByCriteria(criteria);
		// lo Stato 'ITALIA' deve essere collocato in testa alla lista: utilizzo
		// la ricerca logaritmica per cercarlo e inserirlo in testa
		// da tener presente che la lista deve essere ORDINATA per poter
		// applicare la ricerca logatitmica
		TAnStato italia = new TAnStato();
		italia.setDescStato("ITALIA");
		// int indice = Collections.binarySearch(risultati, italia, new StatoComparator());
		int indice = Collections.binarySearch(risultati, italia, new StatoComparator());
		italia = risultati.get(indice);
		// rimuovo l'ITALIA dalla sua posizione attuale per inserirla, dopo, in
		// testa (posizione 0)
		risultati.remove(indice);
		risultati.add(0, italia);
		return risultati;
	}

	@SuppressWarnings("unchecked")
	public List<TAnStato> getAllStatoPiva() {

		List<TAnStato> risultati = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TAnStato.class);
		criteria.add(Restrictions.isNotNull("descSiglaPIva")).addOrder(Order.asc("descSiglaPIva"));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		risultati = getHibernateTemplate().findByCriteria(criteria);
		TAnStato italia = new TAnStato();
		italia.setDescSiglaPIva("IT");
		int indice = Collections.binarySearch(risultati, italia, new StatoPivaComparator());
		italia = risultati.get(indice);
		risultati.remove(indice);
		risultati.add(0, italia);
		return risultati;
	}

	
	public TTpTipoUfficio getTipoUfficioByIdUfficio(int idUffOwn) {

		TAnUfficio ufficio = getHibernateTemplate().get(TAnUfficio.class, idUffOwn);
	
		return ufficio.getTTpTipoUfficio();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<TTpTipoSocieta> getAllTipiSocieta() {

		DetachedCriteria criteria = DetachedCriteria.forClass(TTpTipoSocieta.class);
		criteria.addOrder(Order.asc("descTipoSoc"));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		List<TTpTipoSocieta> u = getHibernateTemplate().findByCriteria(criteria);
		TTpTipoSocieta altro = new TTpTipoSocieta();
		altro.setDescTipoSoc("ALTRO");
		int indice = Collections.binarySearch(u, altro, new TipoSocietaComparator());
		altro = u.get(indice);
		// rimuovo ALTRO dalla sua posizione attuale per inserirla, dopo, in coda
		u.remove(indice);
		u.add(u.size(), altro);
		return u;
	}

	public List<TAnOperFinanz> getCodiciCab(final String input, final String codiceAbi, final String provinciaSigla) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TAnOperFinanz.class);
		if (input != null)
			criteria.add(Restrictions.like("id.codiKIdCab", "%" + input + "%"));
		criteria.add(Restrictions.eq("id.codiKIdAbi", codiceAbi));
		if ((provinciaSigla != null) && !provinciaSigla.equals("")) {
			criteria.add(Restrictions.eq("descSiglaProv", provinciaSigla));
		}
		criteria.addOrder(Order.asc("id.codiKIdCab"));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    Criterion rest3= Restrictions.not(Restrictions.eq("descBanca", Constants.EnteFittizio.DESCRIZIONE));	    
	    criteria.add(Restrictions.or(rest1, rest2));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(rest3);
		List<TAnOperFinanz> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@SuppressWarnings({ "unchecked" })
	public TAnComuni getComuneById(long id) {

		List<TAnComuni> lista = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TAnComuni.class);
		criteria.add(Restrictions.eq("codiKIdComune", (int) id));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		lista = getHibernateTemplate().findByCriteria(criteria);
		if (!Utility.isDefinito(lista)) {
			return null;
		}
		return lista.get(0);
	}

	public List<TTpEsitoUdienza> getEsitiUdienze() {

		List<TTpEsitoUdienza> risultati = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpEsitoUdienza.class);
		criteria.addOrder(Order.asc("codiKIdEsitoUd"));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	@SuppressWarnings({ "unchecked", "null" })
	public TAnMagistrati getMagistratoById(long id) {

		List<TAnMagistrati> lista =  new ArrayList<TAnMagistrati>();
		List<HashMap> resultQry = getMagistratiById(id); 
		Iterator<HashMap> i = resultQry.iterator();
        while (i.hasNext()) {
            HashMap hm = i.next();
            TAnMagistrati tmp = (TAnMagistrati)hm.get("magistrato");
            lista.add(tmp);
        }
	
		if (!Utility.isDefinito(lista)) {
			return null;
		}
		return lista.get(0);
	}

	public List<TAnNormative> getNormativaByTipoPrevenzioneAndTipoMisura(SoggettoForm soggettoForm) {

		List<TAnNormative> lista = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TAnNormative.class);

		// prova
		if ((soggettoForm.getMisura() != null) && (soggettoForm.getMisura().getTipoMisura() != null)) {
			criteria.add(Restrictions.isNull("TTpTipoPrevenzione.codiKIdTipoPrev"))
					.add(Restrictions.eq("TTpTipoMisura.codiKIdTipoMisura", Integer.parseInt(soggettoForm.getMisura().getTipoMisura().getId())));
			Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
		    Criterion rest2= Restrictions.isNull("dataFineValidita");
		    criteria.add(Restrictions.or(rest1, rest2));
			lista = getHibernateTemplate().findByCriteria(criteria);
		}
		return lista;
	}

	public List<TTpTipoPrevProc> getNormativaByTipoProcedAndTipologiaPrevenzione(SoggettoForm soggetto) {

		List<TTpTipoPrevProc> lista = null;
		String tipologiaPrevenzione=soggetto.getTipologiaPrevenzione().getId();
		
		if (soggetto.getTipologiaPrevenzione().getId().indexOf(",") != -1) tipologiaPrevenzione=tipologiaPrevenzione.substring(0,tipologiaPrevenzione.indexOf(","));
		
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpTipoPrevProc.class);
		criteria.add(Restrictions.eq("TTpTipoProc.codiKIdTipoProc", Integer.parseInt(soggetto.getIpotesiMisura().getId())))
				.add(Restrictions.eq("TTpTipoPrevenzione.codiKIdTipoPrev", Integer.parseInt(tipologiaPrevenzione)));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
	    criteria.addOrder(Order.asc("descTipoNorm"));
		lista = getHibernateTemplate().findByCriteria(criteria);
		if (!Utility.isDefinito(lista)) {
			return null;
		}
		return lista;
	}

	public List<TAnOperFinanz> getOperatoriFinanziari(final String pattern) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TAnOperFinanz.class);
		criteria.add(Restrictions.like("descBanca", "%" + pattern + "%"));
		criteria.addOrder(Order.asc("descBanca"));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    
	    Criterion rest3= Restrictions.not(Restrictions.eq("descBanca", Constants.EnteFittizio.DESCRIZIONE));
	    criteria.add(Restrictions.or(rest1, rest2));
	    criteria.add(rest3);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<TAnOperFinanz> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@SuppressWarnings("unchecked")
	public List<TAnAltriOper> getOperatoriFinanziariPolizze(final String pattern) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TAnAltriOper.class);
		criteria.add(Restrictions.like("descOperatore", "%" + pattern + "%"));
		criteria.addOrder(Order.asc("descOperatore"));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    Criterion rest3= Restrictions.not(Restrictions.eq("descOperatore", Constants.EnteFittizio.DESCRIZIONE));
	    criteria.add(Restrictions.or(rest1, rest2));
	    criteria.add(rest3);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return getHibernateTemplate().findByCriteria(criteria);
	}

	public TAnUfficio getProcuraDDA(final long idUffOwn) {

		return (TAnUfficio) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				TAnUfficio procuraDDA = null;
				SQLQuery query = getSession().createSQLQuery(
						"SELECT uffDistr.* " + "FROM T_AN_UFFICIO uff " + "INNER JOIN T_AN_UFFICIO uffDistr "
								+ "ON uff.CODI_FK_ID_DISTRETTO = uffDistr.CODI_FK_ID_DISTRETTO " + "WHERE uff.CODI_K_ID_UFFICIO = :idUffOwn "
								+ "AND uffDistr.FLG_DDA = 'S' ");
				query.addEntity(TAnUfficio.class);
				query.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
				List<TAnUfficio> list = query.list();
				if ((list != null) && (list.size() > 0)) {
					procuraDDA = list.get(0);
				}
				return procuraDDA;
			}
		});
	}

	public TAnUfficio getProcuraGenerale(final long idUffOwn) {

		return getHibernateTemplate().execute(new HibernateCallback<TAnUfficio>() {

			public TAnUfficio doInHibernate(final Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery("SELECT procGen.* " + "FROM T_AN_UFFICIO uff " + "INNER JOIN T_AN_UFFICIO procGen "
						+ "ON uff.CODI_FK_ID_DISTRETTO = procGen.CODI_FK_ID_DISTRETTO " + "WHERE uff.CODI_K_ID_UFFICIO = :idUffOwn "
						+ "AND procGen.CODI_FK_ID_TIPO_UFF = 8 ");
				query.addEntity(TAnUfficio.class);
				query.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
				List<TAnUfficio> list = query.list();
				if ((list != null) && (list.size() > 0)) {
					return list.get(0);
				}
				return null;
			}
		});
	}

	public TAnUfficio getCDA(final long idUffOwn) {

		return getHibernateTemplate().execute(new HibernateCallback<TAnUfficio>() {

			public TAnUfficio doInHibernate(final Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery("SELECT procCDA.* " + "FROM T_AN_UFFICIO uff " + "INNER JOIN T_AN_UFFICIO procCDA "
						+ "ON uff.CODI_FK_ID_DISTRETTO = procCDA.CODI_FK_ID_DISTRETTO " + "WHERE uff.CODI_K_ID_UFFICIO = :idUffOwn "
						+ "AND procCDA.CODI_FK_ID_TIPO_UFF = 6 ");
				query.addEntity(TAnUfficio.class);
				query.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
				List<TAnUfficio> list = query.list();
				if ((list != null) && (list.size() > 0)) {
					return list.get(0);
				}
				return null;
			}
		});
	}

	public TAnUfficio getProcuraInUdienzaAssociata(final long idProc) {

		return getHibernateTemplate().execute(new HibernateCallback<TAnUfficio>() {

			public TAnUfficio doInHibernate(final Session session) throws HibernateException, SQLException {

				SQLQuery query = session.createSQLQuery("SELECT procUdi.* " + "FROM T_AN_UFFICIO procUdi "
						+ "INNER JOIN T_PROCEDIMENTO procedimento " + "ON procedimento.CODI_FK_ID_PU = procUdi.CODI_K_ID_UFFICIO "
						+ "WHERE procedimento.CODI_K_ID_PROC = :idProc");
				query.addEntity(TAnUfficio.class);
				query.setParameter("idProc", idProc, Hibernate.LONG);
				List<TAnUfficio> list = query.list();
				if ((list != null) && (list.size() > 0)) {
					return list.get(0);
				}
				return null;
			}
		});
	}

	public TAnUfficio getProcuraInUdienza(final long idUffOwn) {

		return getHibernateTemplate().execute(new HibernateCallback<TAnUfficio>() {

			public TAnUfficio doInHibernate(final Session session) throws HibernateException, SQLException {

				TAnUfficio procInUdienza = null;
				Query q = session.getNamedQuery("PG_COMMON_getProcuraInUdienza");
				q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List<Map<String, Object>> list = q.list();
				Map<String, Object> map = list.get(0);
				procInUdienza = (TAnUfficio) map.get("ufficio");
				return procInUdienza;
			}
		});
	}

	public TAnUfficio getProcuraProponente(final long idProc) {

		return (TAnUfficio) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				String qry = "SELECT uff.* " + "FROM T_REL_PROC_PROC rel " + "INNER JOIN T_AN_UFFICIO uff "
						+ "ON REL.CODI_FK_ID_UFF_OWNER2 = uff.CODI_K_ID_UFFICIO " + "INNER JOIN T_ATTO atto "
						+ "ON rel.CODI_FK_ID_ATTO_AA = atto.CODI_K_ID_ATTO " + "AND atto.CODI_FK_ID_TIPO_ATTO = 9 "
						+ "AND atto.CODI_FK_ID_CAT_ATTO = 2 " + "WHERE " + "rel.CODI_K_ID_PROC1 = :idProc AND " + "rel.FLG_PADRE IS NULL AND "
						+ "uff.CODI_FK_ID_TIPO_UFF = :idTipoUff AND " + "rel.FLG_TIPO_REL = 'O' ";
				SQLQuery query = getSession().createSQLQuery(qry);
				query.addEntity("ufficio", TAnUfficio.class);
				query.setParameter("idProc", idProc, Hibernate.LONG);
				query.setParameter("idTipoUff", new Long(Constants.TIPO_UFFICIO_PROCURA), Hibernate.LONG);
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List list = query.list();
				if ((list != null) && (list.size() > 0)) {
					Map map = (Map) list.get(0);
					return (TAnUfficio) map.get("ufficio");
				} else {
					return null;
				}
			}
		});
	}

	public List<TAnUfficio> getProcureDistrettualiByTribComp(final long idUffOwnTrib, final boolean excludeDDA) {

		return (List<TAnUfficio>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				List<TAnUfficio> procure = null;
				String qry = "SELECT uffDistr.* " + "FROM T_AN_UFFICIO uff " + "INNER JOIN T_AN_UFFICIO uffDistr "
						+ "ON uff.CODI_FK_ID_DISTRETTO = uffDistr.CODI_FK_ID_DISTRETTO " + "WHERE uff.CODI_K_ID_UFFICIO = :idUffOwn "
						+ "AND uffDistr.CODI_FK_ID_TIPO_UFF = :idTipoUff " + "AND uffDistr.CODI_FK_ID_TRIB_COMP = :idUffOwn2 ";
				if (excludeDDA) {
					qry += " AND uffDistr.FLG_DDA IS NULL ";
				}
				SQLQuery query = getSession().createSQLQuery(qry);
				query.addEntity(TAnUfficio.class);
				query.setParameter("idUffOwn", idUffOwnTrib, Hibernate.LONG);
				query.setParameter("idTipoUff", new Long(Constants.TIPO_UFFICIO_PROCURA), Hibernate.LONG);
				query.setParameter("idUffOwn2", idUffOwnTrib, Hibernate.LONG);
				return query.list();
			}
		});
	}

	public List<ComboForm> getTipiSostituzioneMagistrato() {

		List<ComboForm> risultati = new ArrayList<ComboForm>();
		ComboForm incompatibilita = new ComboForm("Incompatibilità", "Incompatibilità");
		ComboForm trasferimento = new ComboForm("Trasferimento", "Trasferimento");
		ComboForm altro = new ComboForm("Altro", "Altro");
		risultati.add(incompatibilita);
		risultati.add(trasferimento);
		risultati.add(altro);
		return risultati;
	}

	@SuppressWarnings( "unchecked" )
    public List<TTpTipoAtto> getTipoAtto() {

		DetachedCriteria criteria = DetachedCriteria.forClass(TTpTipoAtto.class);
		criteria.addOrder(Order.asc("descTipoAtto"));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		List<TTpTipoAtto> results = getHibernateTemplate().findByCriteria(criteria);
		return results;
	}

	@SuppressWarnings( "unchecked" )
	public List<TTpTipoNota> getTipoNota()
	{
		DetachedCriteria criteria = DetachedCriteria.forClass( TTpTipoNota.class );
		Criterion rest1 = Restrictions.gt( "dataFineValidita", new Date() );
		Criterion rest2 = Restrictions.isNull( "dataFineValidita" );
		criteria.add( Restrictions.or( rest1, rest2 ) );
		criteria.addOrder( Order.asc( "descTipoNota" ) );
		List<TTpTipoNota> results = getHibernateTemplate().findByCriteria( criteria );
		return results;
	}

    @SuppressWarnings( "unchecked" )
    public List<TTpTipoRegime> getTipoRegime()
    {
        DetachedCriteria criteria = DetachedCriteria.forClass( TTpTipoRegime.class );
        criteria.addOrder( Order.asc( "descTipoRegime" ) );
        Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
        List<TTpTipoRegime> results = getHibernateTemplate().findByCriteria( criteria );
        return results;
    }

	public List getTipoAttoAbilitati(final long idProcedimento, final int idTipoUfficio,final int idTipoProc,final int idStatoProc) {
		return (List)getHibernateTemplate().execute(new HibernateCallback() {

			public List doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_COMMON_getTipoAttoAbilitati");
				q.setParameter("ID_PROCEDIMENTO", idProcedimento, Hibernate.LONG);
				q.setParameter("ID_TIPOUFFICIO", idTipoUfficio, Hibernate.INTEGER);
				q.setParameter("ID_TIPOPROC", idTipoProc, Hibernate.INTEGER);
				q.setParameter("ID_STATOPROC", idStatoProc, Hibernate.INTEGER);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List list = q.list();
				return list;

			}
		});
	}
	

	
	public List getCategoriaAttoAbilitate(final long idProcedimento, final int idTipoUfficio,final int idTipoProc,final int idStatoProc,final int idTipoAtto) {
		return (List)getHibernateTemplate().execute(new HibernateCallback() {

			public List doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_COMMON_getCategoriaAttoAbilitate");
				q.setParameter("ID_PROCEDIMENTO", idProcedimento, Hibernate.LONG);
				q.setParameter("ID_TIPOUFFICIO", idTipoUfficio, Hibernate.INTEGER);
				q.setParameter("ID_TIPOPROC", idTipoProc, Hibernate.INTEGER);
				q.setParameter("ID_STATOPROC", idStatoProc, Hibernate.INTEGER);
				q.setParameter("ID_TIPO_ATTO", idTipoAtto, Hibernate.INTEGER);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List list = q.list();
				return list;

			}
		});
	}	

	@SuppressWarnings({ "unchecked", "null" })
	public TTpTipoUfficio getTipoUfficioById(Integer id) {

		List<TTpTipoUfficio> lista = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpTipoUfficio.class);
		criteria.add(Restrictions.eq("codiKIdTipoUff", id));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		lista = getHibernateTemplate().findByCriteria(criteria);
		if (!Utility.isDefinito(lista)) {
			return null;
		}
		TTpTipoUfficio risultato = lista.get(0);
		return risultato;
	}


	public List<TRelProcTrib> getRelProcTrib(long idUfficio, String tipoUfficio, long idTipoPrev, String flgDda, Long excludeIdUfficio) {

        DetachedCriteria criteria = DetachedCriteria.forClass(TRelProcTrib.class);
        String tipoUffJoin = null;
        String tipoUffRic = null;
        if(tipoUfficio.equals(Constants.TIPO_UFFICIO_PROCURA)) {
        	tipoUffRic = "TAnUfficioByCodiKIdUffProc";
        	tipoUffJoin = "TAnUfficioByCodiKIdUffTrib";
        } else if(tipoUfficio.equals(Constants.TIPO_UFFICIO_TRIBUNALE)) {
        	tipoUffRic = "TAnUfficioByCodiKIdUffTrib";
        	tipoUffJoin = "TAnUfficioByCodiKIdUffProc";
        } else {
        	throw new RuntimeException("tipoUfficio può essere PROCURA O TRIBUNALE");
        }
    	criteria.setFetchMode(tipoUffJoin, FetchMode.JOIN);
    	criteria.createAlias(tipoUffJoin, "joinTable");
        criteria.add(Restrictions.eq(tipoUffRic+".codiKIdUfficio", Integer.valueOf(""+idUfficio)));
       
        if(flgDda!=null) {
        	criteria.add(Restrictions.eq("joinTable.flgDda", flgDda));
        }
        if(excludeIdUfficio!=null) {
        	criteria.add(Restrictions.not(Restrictions.eq("joinTable.codiKIdUfficio", Integer.valueOf(""+excludeIdUfficio))));
        }
        
        Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
        Criterion rest2= Restrictions.isNull("dataFineValidita");
     
        criteria.add(Restrictions.or(rest1, rest2));
        criteria.add(Restrictions.eq("TTpTipoPrevenzione.codiKIdTipoPrev", Integer.valueOf(""+idTipoPrev)));

        //criteria.addOrder(Order.asc("joinTable.descSedeUfficio"));
        //pez
        criteria.add(Restrictions.sqlRestriction(" 1=1 ORDER BY abs(jointable1_.CODI_K_ID_UFFICIO-"+idUfficio+") asc"));
         //  criteria.addOrder(Order.asc(Restrictions.eq("TTpTipoPrevenzione.codiKIdTipoPrev", Integer.valueOf(""+idTipoPrev))));
		List<TRelProcTrib> results = getHibernateTemplate().findByCriteria(criteria);
        return results;
	}
		
	
	public TAnUfficio getTribunaleCompetente(final long idProc) {

		return (TAnUfficio) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				TAnUfficio tribComp = null;
				SQLQuery query = getSession().createSQLQuery(
						  "SELECT DISTINCT uff.* "
						+ "FROM T_AN_UFFICIO uff "
						+ "WHERE uff.CODI_K_ID_UFFICIO IN "
						+ "(SELECT CODI_FK_ID_UFF_OWNER2 "
						+ "FROM T_REL_PROC_PROC "
						+ "WHERE CODI_K_ID_PROC1 = :idProc "
						+ "AND CODI_FK_ID_STATO_PROC2 NOT IN (35,36,37) "
						+ "AND FLG_PADRE IS NULL AND (FLG_TIPO_REL NOT IN('R','S') OR FLG_TIPO_REL IS NULL) "
						+ ") "
						+ "AND uff.CODI_FK_ID_TIPO_UFF = :tipoUff");

				query.addEntity(TAnUfficio.class);
				query.setParameter("idProc", idProc, Hibernate.LONG);
				query.setParameter("tipoUff", Long.valueOf(Constants.TIPO_UFFICIO_TRIBUNALE), Hibernate.LONG);

				List<TAnUfficio> list = query.list();
				if ((list != null) && (list.size() > 0)) {
					tribComp = list.get(0);
				}
				return tribComp;
			}
		});
	}

	public List<Map> getUfficiCompetenza(final long idProc, final Long idTipoUff) {

		return (List<Map>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				// String qry = "SELECT rel.*, uff.* " + "FROM T_REL_PROC_PROC rel " + "INNER JOIN T_AN_UFFICIO uff " +
				// "ON REL.CODI_FK_ID_UFF_OWNER2 = uff.CODI_K_ID_UFFICIO " + "WHERE " + "rel.CODI_K_ID_PROC1 = :idProc AND "
				String qry = "SELECT distinct uff.* " + "FROM T_REL_PROC_PROC rel " + "INNER JOIN T_AN_UFFICIO uff "
						+ "ON REL.CODI_FK_ID_UFF_OWNER2 = uff.CODI_K_ID_UFFICIO " + "WHERE " + "rel.CODI_K_ID_PROC1 = :idProc AND "

						+ "(rel.FLG_TIPO_REL NOT IN('R','S') OR rel.FLG_TIPO_REL IS NULL)  ";
				if (idTipoUff != null) {
					qry += " AND uff.CODI_FK_ID_TIPO_UFF = :idTipoUff";
				}
				SQLQuery query = getSession().createSQLQuery(qry);
				// query.addEntity("relProcProc", TRelProcProc.class);
				query.addEntity("ufficio", TAnUfficio.class);
				query.setParameter("idProc", idProc, Hibernate.LONG);
				if (idTipoUff != null) {
					query.setParameter("idTipoUff", idTipoUff, Hibernate.LONG);
				}
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				return query.list();
			}
		});
	}

	// ricerca gli uffici legati al procedimento (T_REL_PROC_PROC), e nel caso in cui esistano più uffici
	// dello stesso tipo, privilegia quello legato all'atto origine dell'atto in parametro
	public List<Map> getUfficiCompetenzaDistinctTipoUff(final long idProc, final Long idAtto, final Long idUffOwn, final long excludeTipoUff) {

		return (List<Map>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				String qry = "SELECT distinct uff.* " + "FROM T_REL_PROC_PROC rel " + "INNER JOIN T_AN_UFFICIO uff "
						+ "ON REL.CODI_FK_ID_UFF_OWNER2 = uff.CODI_K_ID_UFFICIO " +

						"INNER JOIN " + "(select rel_proc_proc.CODI_FK_ID_UFF_OWNER2, uff.CODI_FK_ID_TIPO_UFF " + "from t_rel_Atti rel_atti " +

						"INNER JOIN T_ATTO atto " + "on rel_atti.CODI_K_ID_ATTO_OR = atto.CODI_K_ID_ATTO "
						+ "and rel_atti.CODI_K_ID_UFF_OWNER_OR = ATTO.CODI_K_ID_UFF_OWNER " +

						"INNER JOIN T_REL_PROC_PROC rel_proc_proc " + "on atto.CODI_ID_PROC_EMISS = rel_proc_proc.CODI_K_ID_PROC2 " +

						"INNER JOIN T_AN_UFFICIO uff " + "ON rel_proc_proc.CODI_FK_ID_UFF_OWNER2 = uff.CODI_K_ID_UFFICIO " +

						"where rel_atti.CODI_K_ID_ATTO_FI = :idAtto " + "AND rel_atti.CODI_K_ID_UFF_OWNER_FI = :idUffOwn  "
						+ "AND rel_proc_proc.CODI_K_ID_PROC1 = :idProc2) rel_atto " + "ON ( "
						+ "(uff.CODI_FK_ID_TIPO_UFF = rel_atto.CODI_FK_ID_TIPO_UFF  "
						+ "AND rel.CODI_FK_ID_UFF_OWNER2 = rel_atto.CODI_FK_ID_UFF_OWNER2)  "
						+ "OR uff.CODI_FK_ID_TIPO_UFF <> rel_atto.CODI_FK_ID_TIPO_UFF " + ") " +

						"WHERE " + "rel.CODI_K_ID_PROC1 = :idProc AND " + "rel.FLG_PADRE IS NULL AND rel.FLG_TIPO_REL NOT IN('R','S') ";

				if (excludeTipoUff > 0) {
					qry += "AND uff.CODI_FK_ID_TIPO_UFF <> :excludeTipoUff ";
				}

				SQLQuery query = getSession().createSQLQuery(qry);
				// query.addEntity("relProcProc", TRelProcProc.class);
				query.addEntity("ufficio", TAnUfficio.class);
				query.setParameter("idProc", idProc, Hibernate.LONG);
				query.setParameter("idProc2", idProc, Hibernate.LONG);
				query.setParameter("idAtto", idAtto, Hibernate.LONG);
				query.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
				if (excludeTipoUff > 0) {
					query.setParameter("excludeTipoUff", excludeTipoUff, Hibernate.LONG);
				}

				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				return query.list();
			}
		});
	}
	
	public TAnUfficio getUfficioCompetenzaAggravamento(final long idProcPadre) {

		return (TAnUfficio) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				String qry = "select distinct " +
						"uff.* " +
						"from " +
						"T_AN_UFFICIO uff " +
						"join T_REL_PROC_PROC trpp on trpp.CODI_FK_ID_UFF_OWNER2 = uff.CODI_K_ID_UFFICIO " +
						"join T_ATTO a ON a.CODI_ID_PROC_EMISS = trpp.CODI_K_ID_PROC2 " +
						"join T_REL_ATTO_PROC trap on trap.CODI_K_ID_ATTO = a.CODI_K_ID_ATTO and trap.CODI_K_ID_UFF_OWNER = a.CODI_K_ID_UFF_OWNER " +
						"where " +
						"trap.CODI_K_ID_PROC = :idProc and a.CODI_FK_ID_CAT_ATTO = 4 " +
						"and rownum = 1 ";

//				String qry = "select uff.* " + "from t_rel_Atti rel_atti " +
//
//				"INNER JOIN T_ATTO atto " + "on rel_atti.CODI_K_ID_ATTO_OR = atto.CODI_K_ID_ATTO "
//						+ "and rel_atti.CODI_K_ID_UFF_OWNER_OR = ATTO.CODI_K_ID_UFF_OWNER " +
//
//						"INNER JOIN T_REL_PROC_PROC rel_proc_proc " + "on atto.CODI_ID_PROC_EMISS = rel_proc_proc.CODI_K_ID_PROC2 " +
//
//						"INNER JOIN T_AN_UFFICIO uff " + "ON rel_proc_proc.CODI_FK_ID_UFF_OWNER2 = uff.CODI_K_ID_UFFICIO " +
//
//						"where rel_atti.CODI_K_ID_ATTO_FI = :idAtto  " + "AND rel_atti.CODI_K_ID_UFF_OWNER_FI = :idUffOwn "
//						+ "AND rel_proc_proc.CODI_K_ID_PROC1 = :idProc ";

				SQLQuery query = getSession().createSQLQuery(qry);
				query.addEntity("ufficio", TAnUfficio.class);
				query.setParameter("idProc", idProcPadre, Hibernate.LONG);				
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

				List<Map> uffici = query.list();
				TAnUfficio uff = null;
				if (uffici != null && uffici.size() > 0) {
					Map map = uffici.get(0);
					uff = (TAnUfficio) map.get("ufficio");
				}
				return uff;
			}
		});
	}

	// ricerca l'ufficio legato all'atto origine
	public TAnUfficio getUfficioAttoOrigine(final long idProc, final long idAttoFi, final long idUffOwn) {

		return (TAnUfficio) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				String qry = "select uff.* " + "from t_rel_Atti rel_atti " +

				"INNER JOIN T_ATTO atto " + "on rel_atti.CODI_K_ID_ATTO_OR = atto.CODI_K_ID_ATTO "
						+ "and rel_atti.CODI_K_ID_UFF_OWNER_OR = ATTO.CODI_K_ID_UFF_OWNER " +

						"INNER JOIN T_REL_PROC_PROC rel_proc_proc " + "on atto.CODI_ID_PROC_EMISS = rel_proc_proc.CODI_K_ID_PROC2 " +

						"INNER JOIN T_AN_UFFICIO uff " + "ON rel_proc_proc.CODI_FK_ID_UFF_OWNER2 = uff.CODI_K_ID_UFFICIO " +

						"where rel_atti.CODI_K_ID_ATTO_FI = :idAtto  " + "AND rel_atti.CODI_K_ID_UFF_OWNER_FI = :idUffOwn "
						+ "AND rel_proc_proc.CODI_K_ID_PROC1 = :idProc ";

				SQLQuery query = getSession().createSQLQuery(qry);
				query.addEntity("ufficio", TAnUfficio.class);
				query.setParameter("idProc", idProc, Hibernate.LONG);
				query.setParameter("idAtto", idAttoFi, Hibernate.LONG);
				query.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);

				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

				List<Map> uffici = query.list();
				TAnUfficio uff = null;
				if (uffici != null && uffici.size() > 0) {
					Map map = uffici.get(0);
					uff = (TAnUfficio) map.get("ufficio");
				}
				return uff;
			}
		});
	}

	public List<Map> getAllUfficiCompetenza(final long idProc, final Long idTipoUff) {

		return (List<Map>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				String qry = "SELECT distinct uff.* " + "FROM T_REL_PROC_PROC rel " + "INNER JOIN T_AN_UFFICIO uff "
						+ "ON REL.CODI_FK_ID_UFF_OWNER2 = uff.CODI_K_ID_UFFICIO " + "WHERE " + "rel.CODI_K_ID_PROC1 = :idProc ";

				if (idTipoUff != null) {
					qry += " AND uff.CODI_FK_ID_TIPO_UFF = :idTipoUff";
				}
				SQLQuery query = getSession().createSQLQuery(qry);
				query.addEntity("ufficio", TAnUfficio.class);
				query.setParameter("idProc", idProc, Hibernate.LONG);
				if (idTipoUff != null) {
					query.setParameter("idTipoUff", idTipoUff, Hibernate.LONG);
				}
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				return query.list();
			}
		});
	}

	public List<TAnUfficio> getUfficiDestinatariIncompetenzaProcura(final long idProcedimento) {

		return (List<TAnUfficio>) getHibernateTemplate().execute(new HibernateCallback() {

			public List<TAnUfficio> doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_ufficiDestIncompetenzaProcura");
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				return q.list();
			}
		});
	}

	/**
	 * listaCategorieCatastali
	 * 
	 * @return List<TTpCatCatasto>
	 */
	@SuppressWarnings("unchecked")
	public List<TTpCatCatasto> listaCategorieCatastali() {

		List<TTpCatCatasto> risultati = null;
		risultati = getHibernateTemplate().loadAll(TTpCatCatasto.class);
		return risultati;
	}

	@SuppressWarnings("unchecked")
	public List<TTpCatBene> listaCategorieDelBene(BeneForm bene) {

		List<TTpCatBene> risultati = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpCatBene.class);
		criteria.add(Restrictions.eq("TTpTipoBene.codiKIdTipoBene", Integer.parseInt(bene.getTipologia().getId())))
				.addOrder(Order.asc("descCatBene"));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	@SuppressWarnings("unchecked")
	public List<TTpTipoBene> listaDellaTipologiaDelBene() {

		List<TTpTipoBene> listaTipoBene = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpTipoBene.class);
		criteria.addOrder(Order.asc("descTipoBene"));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		listaTipoBene = getHibernateTemplate().findByCriteria(criteria);
		return listaTipoBene;
	}

	/**
	 * listaDisponibilita
	 * 
	 * @return List<TTpTipoDisp>
	 */
	@SuppressWarnings("unchecked")
	public List<TTpTipoDisp> listaDisponibilita() {

		List<TTpTipoDisp> risultati = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpTipoDisp.class);
		criteria.addOrder(Order.asc("descTipoDisp"));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	@SuppressWarnings({ "null", "unchecked" })
	public List<Long> listaIdMagistratiByUfficio(int idUfficioUtente) {

		List<TRelMagUff> relazioniUfficio = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelMagUff.class);
		criteria.add(Restrictions.eq("id.codiKIdUfficio", idUfficioUtente)).addOrder(Order.asc("id.codiKIdMagistrato"));
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

	@SuppressWarnings("unchecked")
	public List<TTpTipoMisura> listaPrescrizioniMisuraPersonale() {

		List<TTpTipoMisura> risultati = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpTipoMisura.class);
		criteria.createAlias("TTpCaratMisura", "car");
		criteria.add(Restrictions.eq("car.codiKIdCaratMis", "R")).addOrder(Order.asc("descTipoMisura"));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}
	
		public List<TAnProcedente> listaProcedenti(final long idUfficio, final long tipoUfficioConnesso, final long tipoUfficioRic) {

		return (List<TAnProcedente>)getHibernateTemplate().execute(new HibernateCallback() {

			public List<TAnProcedente> doInHibernate(final Session session) throws HibernateException, SQLException {

				List<TAnProcedente> result = new ArrayList<TAnProcedente>();
				Query q = session.getNamedQuery("PG_COMMON_listaProcedRic");
				q.setParameter("IDUFFOWN", idUfficio, Hibernate.LONG);
				q.setParameter("IDTIPOUFFOWN", tipoUfficioConnesso, Hibernate.LONG);
				q.setParameter("IDTIPOUFFRIC", tipoUfficioRic, Hibernate.LONG);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List<Map<String, Object>> list = q.list();
				Iterator i = list.iterator();
				while (i.hasNext()) {
					Map map = (Map) i.next();
					result.add((TAnProcedente)map.get("procedente"));
				}
				return result;
			}
		});
	}
	//

	public List<Map<String, Object>> listaProvinceAbi(final String codiceAbi) {

		return getHibernateTemplate().execute(new HibernateCallback<List<Map<String, Object>>>() {

			public List<Map<String, Object>> doInHibernate(final Session session) throws HibernateException, SQLException {

				String qry = "SELECT distinct OP_FIN.DESC_SIGLA_PROV as prov, OP_FIN.DESC_PROV as desc_prov " + " FROM t_an_oper_finanz op_fin "
						+ " WHERE OP_FIN.CODI_K_ID_ABI = :codiAbi " + " ORDER BY OP_FIN.DESC_SIGLA_PROV asc";
				SQLQuery query = session.createSQLQuery(qry);
				query.setParameter("codiAbi", codiceAbi, Hibernate.STRING);
				query.addScalar("prov", Hibernate.STRING);
				query.addScalar("desc_prov", Hibernate.STRING);
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List<Map<String, Object>> list = query.list();
				return list;
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List<TTpSettoreAtt> listaSettoreAttivita() {

		List<TTpSettoreAtt> risultati = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpSettoreAtt.class);
		criteria.addOrder(Order.asc("descSettoreAtt"));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	@SuppressWarnings("unchecked")
	public List<TTpSettoreAtt> listaSettoriAttivita() {

		List<TTpSettoreAtt> risultati = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpSettoreAtt.class);
		criteria.addOrder(Order.asc("descSettoreAtt"));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	@SuppressWarnings("unchecked")
	public List<TTpSottocatBene> listaSottocategorieDelBene(BeneForm bene) {

		List<TTpSottocatBene> risultati = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpSottocatBene.class);
		criteria.add(Restrictions.eq("TTpCatBene.codiKIdCatBene", Integer.parseInt(bene.getCategoria().getId()))).addOrder(
				Order.asc("descSottocatBene"));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	@SuppressWarnings("unchecked")
	public List<TTpStatoConserv> listaStatiConservazione(int tipo) {

		List<TTpStatoConserv> risultati = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpStatoConserv.class);
		criteria.add(Restrictions.eq("TTpTipoBene.codiKIdTipoBene", tipo)).addOrder(Order.asc("descStatoConserv"));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	@SuppressWarnings("unchecked")
	public List<TTpStatoProc> listaStatiDelProcedimento() {

		List<TTpStatoProc> risultati = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpStatoProc.class);
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		criteria.addOrder(Order.asc("descStatoProc"));
		risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	@SuppressWarnings("unchecked")
	public List<TTpTipoProc> listaTipiDiProcedimento(int idTipoFascicolo) {

		List<TTpTipoProc> risultati = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpTipoProc.class);
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		if (idTipoFascicolo > 0) {
			criteria.add(Restrictions.eq("TTpTipoFascicolo.codiKIdTipoFasc", idTipoFascicolo));
		}
		criteria.addOrder(Order.asc("descTipoProc"));
		risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	// simone 03/02/2014
	@SuppressWarnings("unchecked")
	public List<TTpTipoFascicolo> listaTipiFascicolo(Integer tipoFascicolo) {

		List<TTpTipoFascicolo> risultati = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpTipoFascicolo.class);
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		if (tipoFascicolo != null) {
			criteria.add(Restrictions.eq("codiKIdTipoFasc", tipoFascicolo.intValue()));

		}
		criteria.addOrder(Order.asc("codiKIdTipoFasc"));
		risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	//

	@SuppressWarnings("unchecked")
	public List<TTpTipoIpotesi> listaTipoIpotesi(int idTipoFascicolo, String tipoSoggetto) {

		List<TTpTipoIpotesi> risultati = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpTipoIpotesi.class);
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		if (idTipoFascicolo > 0) {
			criteria.add(Restrictions.eq("TTpTipoFascicolo.codiKIdTipoFasc", idTipoFascicolo));
		}
		criteria.add(Restrictions.eq("id.codiKIdTipoSogg", tipoSoggetto));
		criteria.addOrder(Order.asc("descTipoIpotesi"));
		risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	@SuppressWarnings("unchecked")
	public List<TTpTipoInteresse> listaTipiInteresse() {

		List<TTpTipoInteresse> risultati = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpTipoInteresse.class);
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		criteria.addOrder(Order.asc("descTipoInt"));
		risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	@SuppressWarnings("unchecked")
	public List<TTpTipoMisura> listaTipiMisura(String carattereMisura) {

		List<TTpTipoMisura> risultati = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpTipoMisura.class);
		criteria.createAlias("TTpCaratMisura", "car");
		// criteria.add(Restrictions.eq("car.descCaratMisura", "P")).add(Restrictions.isNull("dataFineValidita"));
		criteria.add(Restrictions.eq("car.codiKIdCaratMis", carattereMisura));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	@SuppressWarnings("unchecked")
	public List<TTpTipoMisura> listaTipiMisuraDecretoSequestro(String carattereMisura) {

		List<TTpTipoMisura> risultati = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpTipoMisura.class);
		if (carattereMisura.equals("SA") || carattereMisura.equals("SU")) {
			criteria.createAlias("TTpCaratMisura", "car");
			criteria.add(Restrictions.eq("car.codiKIdCaratMis", carattereMisura));
		} else {
			criteria.createAlias("TTpCaratMisura", "car");
			criteria.add(Restrictions.or(Restrictions.eq("car.codiKIdCaratMis", carattereMisura), Restrictions.eq("car.codiKIdCaratMis", "SR")));
		}
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		criteria.addOrder(Order.asc("descTipoMisura"));
		risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	@SuppressWarnings("unchecked")
	public List<TTpTipoMisura> listaTipiMisuraTipoT() {

		List<TTpTipoMisura> risultati = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpTipoMisura.class);
		criteria.createAlias("TTpCaratMisura", "car");
		criteria.add(Restrictions.eq("car.codiKIdCaratMis", "T"));
		criteria.add(Restrictions.isNull("flgTrattamento"));
		criteria.addOrder(Order.asc("descTipoMisura"));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		risultati = getHibernateTemplate().findByCriteria(criteria);

		return risultati;
	}

	@SuppressWarnings("unchecked")
	public List<TTpTipoPrevenzione> listaTipiPrevenzione() {

		List<TTpTipoPrevenzione> risultati = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpTipoPrevenzione.class);
		criteria.addOrder(Order.asc("descTipoPrev"));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	
	@SuppressWarnings("unchecked")
	public List<TTpTipoUfficio> listaTipoUfficio() {

		List<TTpTipoUfficio> risultati = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpTipoUfficio.class);
		criteria.addOrder(Order.asc("descTipoUff"));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}
	

	// simone
	@SuppressWarnings({ "unchecked" })
	public List<TTpTipoUfficio> listaTipiProcedenteByUfficio(int idUfficioUtente) {

		return (List<TTpTipoUfficio>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				SQLQuery query = getSession().createSQLQuery(
						"SELECT DISTINCT T_TP_TIPO_UFFICIO.* " + "FROM T_AN_PROCEDENTE, T_AN_UFFICIO, T_TP_TIPO_UFFICIO "
								+ "WHERE T_AN_PROCEDENTE.CODI_FK_ID_UFFICIO = T_AN_UFFICIO.CODI_K_ID_UFFICIO "
								+ "and T_AN_UFFICIO.CODI_FK_ID_TIPO_UFF = T_TP_TIPO_UFFICIO.CODI_K_ID_TIPO_UFF");
				query.addEntity(TTpTipoUfficio.class);

				return query.list();
			}
		});
	}

	//

	public List<TTpTipoStrada> listaTipiStrada() {

		DetachedCriteria criteria = DetachedCriteria.forClass(TTpTipoStrada.class);
		criteria.addOrder(Order.asc("descTipoStrada"));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		List<TTpTipoStrada> risultati = getHibernateTemplate().findByCriteria(criteria);
		TTpTipoStrada tipoVia = new TTpTipoStrada();
		tipoVia.setDescTipoStrada("VIA");
		int indice = Collections.binarySearch(risultati, tipoVia, new TipoStradaComparator());
		tipoVia = risultati.get(indice);
		// rimuovo il tipo 'VIA' dalla sua posizione attuale per inserirla,
		// dopo, in
		// testa (posizione 0)
		risultati.remove(indice);
		risultati.add(0, tipoVia);
		return risultati;
	}

	public List<TTpValuta> listaValuta() {

		List<TTpValuta> risultati = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpValuta.class);
		criteria.addOrder(Order.asc("codiKIdValuta"));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	public TTpValuta getTpValuta(final String codice) {
		return (TTpValuta) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				return session.get(TTpValuta.class, codice);
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List<TTpTipoVincolo> listaVincoli() {

		List<TTpTipoVincolo> risultati = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpTipoVincolo.class);
		criteria.addOrder(Order.asc("descTipoVincolo"));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	private List<TTpTipoUfficio> distinguiTipiUfficio(List<TTpTipoUfficio> tipi) {

		List<TTpTipoUfficio> risultati = new ArrayList<TTpTipoUfficio>();
		List<Long> listaAppoggio = new ArrayList<Long>();
		Iterator<TTpTipoUfficio> iterator = tipi.iterator();
		while (iterator.hasNext()) {
			TTpTipoUfficio tmp = iterator.next();
			if (!listaAppoggio.contains(tmp.getCodiKIdTipoUff())) {
				listaAppoggio.add(new Long(tmp.getCodiKIdTipoUff() + ""));
				risultati.add(tmp);
			}
		}
		return risultati;
	}

	public boolean existMisurePersonali(final long idProc, final long idAtto) {

		Boolean ret = (Boolean) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Boolean retVal = new Boolean(false);
				Integer count = new Integer(0);
				String qry = "SELECT count(*) as countMis " + "FROM T_REL_ATTO_MIS_PERS rel_atto_mp " + "INNER JOIN T_MISURA_PERS mis_pers "
						+ "ON mis_pers.CODI_K_ID_MISURA = rel_atto_mp.CODI_K_ID_MISURA_PERS "
						+ "AND mis_pers.CODI_K_ID_UFF_OWNER = rel_atto_mp.CODI_K_ID_UFF_OWNER " + "WHERE rel_atto_mp.CODI_K_ID_PROC = :idProc "
						+ "AND rel_atto_mp.CODI_K_ID_ATTO = :idAtto";
				SQLQuery query = session.createSQLQuery(qry);
				query.setParameter("idProc", idProc, Hibernate.LONG);
				query.setParameter("idAtto", idAtto, Hibernate.LONG);
				query.addScalar("countMis", Hibernate.INTEGER);
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List<Map<String, Object>> list = query.list();
				Iterator i = list.iterator();
				while (i.hasNext()) {
					Map map = (Map) i.next();
					count = (Integer) map.get("countMis");
				}
				if (count.intValue() > 0) {
					retVal = new Boolean(true);
				}
				return retVal;
			}
		});
		return ret.booleanValue();
	}

	public boolean existMisurePatrimoniali(final long idProc, final long idAtto) {

		Boolean ret = (Boolean) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Boolean retVal = new Boolean(false);
				Integer count = new Integer(0);
				String qry = "SELECT count(*) as countMis " + "FROM T_REL_ATTO_MIS_PATR rel_atto_mp " + "WHERE rel_atto_mp.CODI_K_ID_PROC = :idProc "
						+ "AND rel_atto_mp.CODI_K_ID_ATTO = :idAtto";
				SQLQuery query = session.createSQLQuery(qry);
				query.setParameter("idProc", idProc, Hibernate.LONG);
				query.setParameter("idAtto", idAtto, Hibernate.LONG);
				query.addScalar("countMis", Hibernate.INTEGER);
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List<Map<String, Object>> list = query.list();
				Iterator i = list.iterator();
				while (i.hasNext()) {
					Map map = (Map) i.next();
					count = (Integer) map.get("countMis");
				}
				if (count.intValue() > 0) {
					retVal = new Boolean(true);
				}
				return retVal;
			}
		});
		return ret.booleanValue();
	}

	public List<TAnUfficio> getProcureDistrettualiByCdA(int idUffOwn) {

		TAnUfficio cda = getHibernateTemplate().get(TAnUfficio.class, idUffOwn);
		long idSede = cda.getTAnComuni().getCodiKIdComune();
		DetachedCriteria criteria = DetachedCriteria.forClass(TAnUfficio.class);
		criteria.add(Restrictions.eq("TAnComuni.codiKIdComune", idSede));
		criteria.add(Restrictions.eq("TTpTipoUfficio.codiKIdTipoUff", Constants.TIPO_UFFICIO_PROCURA_GENERALE));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		List<TAnUfficio> risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	public List<TAnUfficio> getUfficiDestinatari(final long codAtto, final long idUffOwn) {

		return (List<TAnUfficio>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				SQLQuery query = getSession().createSQLQuery(
						"select uff.* from T_AN_UFFICIO uff " + "where uff.CODI_K_ID_UFFICIO in "
								+ "(select distinct flussi_out.CODI_FK_ID_UFF_DEST " + "from T_FLUSSI_OUT flussi_out "
								+ " where flussi_out.CODI_FK_ID_ATTO = :codAtto " + " and flussi_out.CODI_FK_ID_UFF_MITT = :idUffOwn"
								+ " and flussi_out.DESC_UTENTE != '9999')");
				query.addEntity(TAnUfficio.class);
				query.setParameter("codAtto", codAtto, Hibernate.LONG);
				query.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
				return query.list();
			}
		});
	}

	/**
	 * getListaOrdineAmministratori
	 * 
	 * @return List<TTpOrdineProf>
	 */
	public List<TTpOrdineProf> getListaOrdineAmministratori() {

		List<TTpOrdineProf> risultati = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpOrdineProf.class);
		criteria.addOrder(Order.asc("descOrdineProf"));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;

	}

	/**
	 * getSediOrdineAmministratori
	 * 
	 * @param idOrdine
	 * @return
	 */
	public List<TTpSedeOrdine> getSediOrdineAmministratori(String idOrdine) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TTpSedeOrdine.class);
		Criterion tipoOrdine = Restrictions.eq("TTpOrdineProf.codiKIdOrdineProf", idOrdine);
		criteria.add(tipoOrdine).addOrder(Order.asc("descSedeOrdine"));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		List<TTpSedeOrdine> results = getHibernateTemplate().findByCriteria(criteria);
		return results;
	}

	public List<TTpTipoCatasto> listaTipoCatasto() {
		List<TTpTipoCatasto> risultati = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpTipoCatasto.class);
		criteria.addOrder(Order.asc("descBreveCatasto"));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	public List<TTpTipoFondo> listaTipoFondo() {
		List<TTpTipoFondo> risultati = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpTipoFondo.class);
		criteria.addOrder(Order.asc("descFondo"));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	public List<TTpTipoDenuncia> listaTipoDenuncia() {
		List<TTpTipoDenuncia> risultati = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpTipoDenuncia.class);
		criteria.addOrder(Order.asc("descTipoDenuncia"));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	public List<TTpTipoOccupanti> listaTipoOccupanti() {
		List<TTpTipoOccupanti> risultati = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpTipoOccupanti.class);
		criteria.addOrder(Order.asc("descTipoOcc"));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	public List<TTpTipoRuoloMag> getAllTipoRuoliMagistratiCollegio() {

		List<TTpTipoRuoloMag> risultati = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpTipoRuoloMag.class);
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		// Carico solo i ruoli associabili ad un Collegio
		criteria.add(Restrictions.eq("flgCollegio", "S"));
		criteria.addOrder(Order.asc("descRuolo"));
		risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	public List<TTpModPerv> getAllTipoModalitaPervenimento() {

		List<TTpModPerv> risultati = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpModPerv.class);
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		criteria.addOrder(Order.asc("descModalita"));
		risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.giustizia.smp.dao.CommonDao#getTipoTrasformazioni(java.lang.Character)
	 */
	@SuppressWarnings("unchecked")
	public List<TTpTipoTrasform> getTipoTrasformazioni(Character tipoTrasformazione) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpTipoTrasform.class);

//		criteria.add(Restrictions.or(Restrictions.isNull("dataFineValidita"), Restrictions.le("dataFineValidita", new Date())));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		if (tipoTrasformazione != null) {
			criteria.add(Restrictions.eq("flgRelazione", tipoTrasformazione));
		}
		criteria.add(Restrictions.ge("codiKIdTipoTrasf",3));
		
		return getHibernateTemplate().findByCriteria(criteria);
	}

	public TTpTipoBene getTipologiaBene(int idTipologia) {
		return getHibernateTemplate().get(TTpTipoBene.class, idTipologia);
	}

	public TTpCatBene getCategoriaBene(int idCategoria) {
		return getHibernateTemplate().get(TTpCatBene.class, idCategoria);
	}

	public TTpSottocatBene getSottoCategoriaBene(int idSottoCategoria) {
		return getHibernateTemplate().get(TTpSottocatBene.class, idSottoCategoria);
	}

	public TTpCatAtto getCategoriaAtto(long idCatAtto) {
		return getHibernateTemplate().get(TTpCatAtto.class, (int) idCatAtto);
	}

	public TAnUfficio getDettaglioUfficio(final long idUffOwn) {

		return (TAnUfficio) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				TAnUfficio ufficio = null;
				SQLQuery query = getSession().createSQLQuery("select uff.* " + "from T_AN_UFFICIO uff " + "where uff.CODI_K_ID_UFFICIO = :idUffOwn ");
				query.addEntity(TAnUfficio.class);
				query.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
				List<TAnUfficio> list = query.list();
				if ((list != null) && (list.size() > 0)) {
					ufficio = list.get(0);
				}
				return ufficio;
			}
		});
	}

	/**
	 * restituisce la trelprocproc di un tipo ufficio specifico
	 * 
	 * @param idProc
	 * @param idTipoUff
	 * @return TRelProcProc
	 */

	public List getNumeProcTRelProcProc(final long idProc, final long idTipoUff) {

		return getHibernateTemplate().execute(new HibernateCallback<List>() {

			public List doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_COMMON_getNumeProcTRelProcProc");
				q.setParameter("idProc", idProc, Hibernate.LONG);
				q.setParameter("idTipoUff", idTipoUff, Hibernate.LONG);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List list = q.list();

				return list;
			}
		});
	}

	public List<TAnUfficio> getElencoUfficiDistretto(final long idUffOwn) {

		return (List<TAnUfficio>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
// DG eliminato il parametro 16 da CODI_FK_ID_TIPO_UFF ( tribunale distaccato) dal filtro e inserito controllo null su data fine validitÃ 
				SQLQuery query = getSession().createSQLQuery(
						" SELECT T_AN_UFFICIO.* " + " FROM T_AN_UFFICIO " + " WHERE    CODI_FK_ID_TIPO_UFF in (19) "
								+ " AND  CODI_K_ID_UFFICIO <>:idUffOwn "
								+ " AND  DATA_FINE_VALIDITA is NULL "
								+ "order by desc_tipo_ufficio,desc_sede_ufficio ");
				query.addEntity(TAnUfficio.class);
				query.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
				return query.list();
			}
		});
	}

	public List<TTpModPerv> listaTipoModPerv() {
		List<TTpModPerv> risultati = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpModPerv.class);
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		//Segnalazione SMP10
//		criteria.addOrder(Order.asc("codiKIdModalita"));
		criteria.addOrder(Order.asc("descModalita"));
		risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	public KeyMessageDto controllaInvioProcuraPG(final long idProc, final long idUfficio, final String descUtente, final String funzione,
			final String metodo) {
		return (KeyMessageDto) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_COMMON_controllaInvioProcuraPG");
				q.setParameter("I_ID_PROCEDIMENTO", idProc, Hibernate.LONG);
				q.setParameter("I_ID_UFFICIO", idUfficio, Hibernate.LONG);
				q.setParameter("I_ID_UTENTE", descUtente, Hibernate.STRING);
				q.setParameter("I_TIPO_FUNZIONE", funzione, Hibernate.STRING);
				q.setParameter("I_NOME_METODO", metodo, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return new KeyMessageDto(l);
			}
		});
	}

	public KeyMessageDto controllaAperturaRicorsi(final long idProc, final long idUfficio, final String descUtente, final String funzione,
			final String metodo) {
		return (KeyMessageDto) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_COMMON_controllaAperturaRicorsi");
				q.setParameter("I_ID_PROCEDIMENTO", idProc, Hibernate.LONG);
				q.setParameter("I_ID_UFFICIO", idUfficio, Hibernate.LONG);
				q.setParameter("I_ID_UTENTE", descUtente, Hibernate.STRING);
				q.setParameter("I_TIPO_FUNZIONE", funzione, Hibernate.STRING);
				q.setParameter("I_NOME_METODO", metodo, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return new KeyMessageDto(l);
			}
		});

	}

	public List getUfficiRevocazioneConfisca(final long idUffOwn, final long idTipoUfficio) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_COMMON_UfficiRevocazioneConfisca");
				q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
				q.setParameter("idTipoUfficio", idTipoUfficio, Hibernate.LONG);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});
	}

	public KeyMessageDto controllaPresenzaAttiInLavorazione(final long idProc, final long tipoAtto, final long idUfficio, final String descUtente,
			final String funzione, final String metodo) {
		return (KeyMessageDto) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_COMMON_ControllaPresenzaAttiInLavorazione");
				q.setParameter("I_ID_PROCEDIMENTO", idProc, Hibernate.LONG);
				if (tipoAtto == 0)
					q.setParameter("I_ID_TIPO_ATTO", null, Hibernate.LONG);
				else
					q.setParameter("I_ID_TIPO_ATTO", tipoAtto, Hibernate.LONG);
				q.setParameter("I_ID_CAT_ATTO", null, Hibernate.LONG);
				q.setParameter("I_ID_UFFICIO", idUfficio, Hibernate.LONG);
				q.setParameter("I_ID_UTENTE", descUtente, Hibernate.STRING);
				q.setParameter("I_TIPO_FUNZIONE", funzione, Hibernate.STRING);
				q.setParameter("I_NOME_METODO", metodo, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return new KeyMessageDto(l);
			}
		});

	}

	public String controlloIncompetenzaProcura(final long idProcedimento) {

		return (String)getHibernateTemplate().execute(new HibernateCallback() {

			public String doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_controlloIncompetenzaProcura");
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				Map first = (Map) l.get(0);
				String resp = (String) first.get("RESPONSE");
				return resp;
			}
		});
	}

	public TTpTipoAtto getTipoAttoById(final int idTipoAtto) {
		return getHibernateTemplate().get(TTpTipoAtto.class, idTipoAtto);
	}
	
	public TAnUfficio getUfficioById(final int idUfficio) {
		return getHibernateTemplate().get(TAnUfficio.class, idUfficio);
	}
	
	public TAnStato getStatoById(int idStato) {
		return getHibernateTemplate().get(TAnStato.class, idStato);
	}
	
	public TTpCatAtto getCatAttoById(int idCatAtto) {
		return getHibernateTemplate().get(TTpCatAtto.class, idCatAtto);
	}
	
	public TTpTipoMisura getTipoMisuraById(int idTipoMisura)  {
		return getHibernateTemplate().get(TTpTipoMisura.class, idTipoMisura);
	}
	
	public TAnStato getStatoByIdCasellario(String codiceCasellario) {
		TAnStato ex = new TAnStato();
		ex.setDescCodiNSC(codiceCasellario);
		List<TAnStato> result = getHibernateTemplate().findByExample(ex);
		if (result.size()>0) {
			return result.get(0);
		}
		return null;
	}
	
	public TAnComuni getComuneByIdCasellario(String codiceCasellario) {
		TAnComuni ex = new TAnComuni();
		ex.setDescCodiNSC(codiceCasellario);
		List<TAnComuni> result = getHibernateTemplate().findByExample(ex);
		if (result.size()>0) {
			return result.get(0);
		}
		return null;
	}
	
	public KeyMessageDto controllaRelMagistratoUfficio(final int idUffOwn, final long idMagistrato) {
		
		return (KeyMessageDto) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_COMMON_cntr_RelMagistratoUfficio");
				 q.setParameter("IDUFFOWN", idUffOwn);
				q.setParameter("IDMAGISTRATO", idMagistrato, Hibernate.LONG);
				
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return new KeyMessageDto(l);
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List<TAnConservatorie> getAllConservatoria() { 
			DetachedCriteria criteria = DetachedCriteria.forClass(TAnConservatorie.class);
			Criterion rest1 = Restrictions.gt( "dataFineValidita", new Date() );
			Criterion rest2 = Restrictions.isNull( "dataFineValidita" );
			criteria.add( Restrictions.or( rest1, rest2 ) );
			criteria.addOrder(Order.asc("descConserv"));
			List<TAnConservatorie> u = getHibernateTemplate().findByCriteria(criteria);
			return u;
	}

	public List<TAnUfficio> getListaUffici(final int idUfficio) {

		return (List<TAnUfficio>)getHibernateTemplate().execute(new HibernateCallback() {

			public List<TAnUfficio> doInHibernate(final Session session) throws HibernateException, SQLException {
				List<TAnUfficio> result =new ArrayList<TAnUfficio>();
				
				Query q = session.getNamedQuery("PG_COMMON_ufficiRicercaProcedimenti");
				q.setParameter("idUfficio", idUfficio, Hibernate.INTEGER);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List<Map<String, Object>> list = q.list();
				Iterator i = list.iterator();
				while (i.hasNext()) {
					Map map = (Map) i.next();
					result.add((TAnUfficio)map.get("ufficio"));
				}
				return result;
			}
		});
	}
	
	public List<TAnUfficio> getElencoUfficiCdaTribunali(final int idDistretto) {

		return (List<TAnUfficio>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				SQLQuery query = getSession().createSQLQuery(
						" SELECT T_AN_UFFICIO.* " + " FROM T_AN_UFFICIO " + " WHERE    CODI_FK_ID_TIPO_UFF in (19) AND FLG_TRIB_MP= 'S' AND CODI_FK_ID_DISTRETTO  = :idDistretto "
								+ " order by desc_tipo_ufficio,desc_sede_ufficio ");
				query.addEntity(TAnUfficio.class);
				query.setParameter("idDistretto", idDistretto, Hibernate.INTEGER);
				
				return query.list();
			}
		});
	}
	
	public void inserisciInTCassMsg(final int idUffOwn, final long idAttoImpugnazione,final long idProcedimento,final String descUtente) {
		getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_COMMON_insert_into_t_cass_mag");
                q.setParameter("idUffOwn", idUffOwn, Hibernate.INTEGER);
                q.setParameter("idAttoImpugnazione", idAttoImpugnazione, Hibernate.LONG);
                q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
                q.setParameter("descUtente", descUtente, Hibernate.STRING);
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                return q.list();
            }
        });
	}
	
	public void modificaTCassMsg(final int idUffOwn,final long idAttoImpugnazione,final long idProcedimento,final char flagInviato,final String descErrore,final byte[] blobXML) {
		getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_COMMON_modifica_t_cass_mag");
                q.setParameter("idUffOwn", idUffOwn, Hibernate.INTEGER);
                q.setParameter("idAttoImpugnazione", idAttoImpugnazione, Hibernate.LONG);
                q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
                q.setParameter("flagInviato", Character.valueOf(flagInviato), Hibernate.CHARACTER);
                q.setParameter("descErrore", descErrore, Hibernate.STRING);
                q.setParameter("blobXML", Hibernate.createBlob(blobXML), Hibernate.BLOB);
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List<?> l = q.list();
                return l;
            }
        });
	}

	public List<ChiaveRicorsoCassazioneDto> getListaRicorsiDaInviare() {
		return (List)getHibernateTemplate().execute(new HibernateCallback() {

			public List doInHibernate(final Session session) throws HibernateException, SQLException {
				Query q = session.getNamedQuery("PG_COMMON_getRicorsiCassDaInviare");
				//GET_RIC_CASS_DA_INVIARE(ORS  OUT CURRECORD)
				q.setResultTransformer(Transformers.aliasToBean(ChiaveRicorsoCassazioneDto.class));
				List list = q.list();
				return list;
			}
		});
	} 
	   
	public HashMap getFascicoloGestione(final long idProc, final long idUffOwn, final String descUtente) {

		return (HashMap)getHibernateTemplate().execute(new HibernateCallback() {

			public HashMap doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_COMMON_GET_FASCICOLO_GEST");
				q.setParameter("idProc", idProc);
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("descUtente", descUtente);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				if(l!=null && l.size()>0) {
					HashMap first = (HashMap) l.get(0);
					return first;
				} else return null;
			}
		});
	}	

	public List<HashMap> getMagistratiByUff(final long idUffOwn) {

		return (List<HashMap> )getHibernateTemplate().execute(new HibernateCallback() {

			public List<HashMap> doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_COMMON_getMagistratiByUff");
				
				q.setParameter("idUffOwn", idUffOwn);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				return q.list();
			}
		});
	}
	
	public List<HashMap> getMagistratiById(final long idMagistrato) {

		return (List<HashMap> )getHibernateTemplate().execute(new HibernateCallback() {

			public List<HashMap> doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_COMMON_getMagistratiById");
				
				q.setParameter("idMagistrato", idMagistrato);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				return q.list();
			}
		});
	}		

	public List<TAnUfficio> listaUfficiTribunali() {
		DetachedCriteria criteria = DetachedCriteria.forClass(TAnUfficio.class);
		criteria.add(Restrictions.eq("TTpTipoUfficio.codiKIdTipoUff", 19)).addOrder(Order.asc("descUfficio"));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		List<TAnUfficio> lista = (List<TAnUfficio>)getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	public List<TAnDistretto> getAllDistretti() {
		DetachedCriteria criteria = DetachedCriteria.forClass(TAnDistretto.class);
		criteria.addOrder(Order.asc("descDistretto"));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		List<TAnDistretto> u = getHibernateTemplate().findByCriteria(criteria);
		return u;
	}

	public List<TTpTipoIstituto> listaTipoIstituto() {
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpTipoIstituto.class);
		criteria.addOrder(Order.asc("descTipoIst"));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		List<TTpTipoIstituto> u = getHibernateTemplate().findByCriteria(criteria);
		return u;
	}

	public TTpTipoIstituto getTipoIstitutoById(int idTipoIst) {
		return getHibernateTemplate().get(TTpTipoIstituto.class, idTipoIst);
	}

	public TAnDistretto getDistrettoById(int idDistretto) {
		return getHibernateTemplate().get(TAnDistretto.class, idDistretto);
	}

	public <T> T getEntity(Class<T> clazz, Serializable id) {
		return getHibernateTemplate().get(clazz, id);
	}
	
	public int getIdDocumentale(String codugna){
		List<TAnUtenzePdoc> risultati = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TAnUtenzePdoc.class);
		criteria.add(Restrictions.eq("codiKIdUffGiudiz", codugna));
		risultati = getHibernateTemplate().findByCriteria(criteria);
		if(risultati!=null && risultati.size()>0)
			return risultati.get(0).getNumeUtenzaPdoc();
		else return 0;
	}
	
	public TAnUtenzePdoc getAutenticazioneRandom(){
		List<TAnUtenzePdoc> risultati = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TAnUtenzePdoc.class);
		criteria.add(Restrictions.isNotNull("descCodiceApplicazione"));
		criteria.add(Restrictions.isNotNull("numeUtenzaPdoc"));
		criteria.add(Restrictions.isNotNull("descPassword"));
		criteria.add(Restrictions.isNotNull("descUtentePdoc"));
		risultati = getHibernateTemplate().findByCriteria(criteria);
		if(risultati!=null && risultati.size()>0)
			return risultati.get(0);
		else 
			return null;
	}

	public TTpTipoMisura getTTpTipoMisuraById(long idTipoMIsura){
		List<TTpTipoMisura> risultati = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpTipoMisura.class);
		criteria.add(Restrictions.eq("codiKIdTipoMisura", new Integer(new Long(idTipoMIsura).intValue())));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati.get(0);
	}
	
	public TAnNormative getTAnNormativeById(long idNormativa) {
		List<TAnNormative> risultati = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TAnNormative.class);
		criteria.add(Restrictions.eq("codiKIdNormativa", new Integer(new Long(idNormativa).intValue()).intValue()));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati.get(0);
	}
	
	public TTpTipoProc getTTpTipoProcById(long idTipoProc){
		List<TTpTipoProc> risultati = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpTipoProc.class);
		criteria.add(Restrictions.eq("codiKIdTipoProc", new Integer(new Long(idTipoProc).intValue())));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati.get(0);
	}
	
	public TTpTipoPrevProc getTTpTipoPrevProc(long codiKIdTipoNorm) {
		List<TTpTipoPrevProc> risultati = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpTipoPrevProc.class);
		criteria.add(Restrictions.eq("codiKIdTipoNorm", new Integer(new Long(codiKIdTipoNorm).intValue()).intValue()));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
	    criteria.addOrder(Order.asc("descTipoNorm"));
		risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati.get(0);
	}
	
	public TAnComuni getComuneByDescCodiNsc(String descCodiNsc) {
		List<TAnComuni> lista = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TAnComuni.class);
		criteria.add(Restrictions.eq("descCodiNSC", descCodiNsc));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		lista = getHibernateTemplate().findByCriteria(criteria);
		if (!Utility.isDefinito(lista)) {
			return null;
		}
		return lista.get(0);
	}
	
	public TAnComuni getComuneByDescIstatComune(String descIstatComune) {
		List<TAnComuni> lista = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TAnComuni.class);
		criteria.add(Restrictions.eq("descIstatComune", descIstatComune));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		lista = getHibernateTemplate().findByCriteria(criteria);
		if (!Utility.isDefinito(lista)) {
			return null;
		}
		return lista.get(0);
	}
	
	public TTpTipoBene getIdTipologiaBene(String tipologia) {
		List<TTpTipoBene> lista = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpTipoBene.class);
		criteria.add(Restrictions.eq("descTipoBene", tipologia));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		lista = getHibernateTemplate().findByCriteria(criteria);
		if (lista != null && lista.size() > 0) {
   		    return lista.get(0);
		}
		return null;
	}

	public TTpCatBene getIdCategoriaBene(TTpTipoBene tTipoBene, String categoria) {
		List<TTpCatBene> lista = null;
		TTpCatBene tCatBene = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpCatBene.class);
		criteria.add(Restrictions.eq("TTpTipoBene.codiKIdTipoBene", tTipoBene.getCodiKIdTipoBene()));		
		criteria.add(Restrictions.eq("descCatBene", categoria));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		lista = getHibernateTemplate().findByCriteria(criteria);
		if (lista != null && lista.size() > 0) {
   		    return lista.get(0);
		}
		return null;
	}

	public TTpSottocatBene getIdSottoCategoriaBene(TTpCatBene tCategoriaBene,String sottoCategoria) {
		List<TTpSottocatBene> lista = null;				
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpSottocatBene.class);
		criteria.add(Restrictions.eq("TTpCatBene.codiKIdCatBene", tCategoriaBene.getCodiKIdCatBene()));				
		criteria.add(Restrictions.eq("descSottocatBene", sottoCategoria));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		lista = getHibernateTemplate().findByCriteria(criteria);
		if (lista != null && lista.size() > 0) {
   		    return lista.get(0);
		}
		return null;
	}
	
	public TAnComuni getComuneByDescBelfiore(String descBelfiore) {
		List<TAnComuni> lista = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TAnComuni.class);
		criteria.add(Restrictions.eq("descBelfiore", descBelfiore));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		lista = getHibernateTemplate().findByCriteria(criteria);
		if (!Utility.isDefinito(lista)) {
			return null;
		}
		return lista.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public TPersonaFisica getTPersonaFisicaById(long idSoggetto, long idUfficio){
		List<TPersonaFisica> lista = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TPersonaFisica.class);
		criteria.add(Restrictions.eq("id.codiKIdPersonaFis", idSoggetto));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", new Long(idUfficio).intValue()));
		lista = getHibernateTemplate().findByCriteria(criteria);
		if (lista!=null && !lista.isEmpty())
			return lista.get(0);
		else
			return null;
	}

	public TTpTipoStrada getTTpTipoStrada(int idTipologia) {
		List<TTpTipoStrada> lista = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpTipoStrada.class);
		criteria.add(Restrictions.eq("id", idTipologia));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		lista = getHibernateTemplate().findByCriteria(criteria);
		if (lista != null && lista.size() > 0) {
   		    return lista.get(0);
		}
		return null;
	}

	public TTpTipoStrada getTTpTipoStradaByDesc(String descStrada) {
		List<TTpTipoStrada> lista = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpTipoStrada.class);
		criteria.add(Restrictions.eq("descTipoStrada", descStrada));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		lista = getHibernateTemplate().findByCriteria(criteria);
		if (lista != null && lista.size() > 0) {
   		    return lista.get(0);
		}
		return null;
	}


	public TAnStato getStatoByIdIstat(int idStatoIstat) {
		TAnStato ex = new TAnStato();
		ex.setDescIstatStato(idStatoIstat+"");
		List<TAnStato> result = getHibernateTemplate().findByExample(ex);
		if (result.size()>0) {
			return result.get(0);
		}
		return null;
	}
	
	public TAnUtenzePdoc getUtenzaPdoc(int idUffOwn) {
		TAnUtenzePdoc utenzaPdoc = null;
		List<TAnUfficio> listaUffici = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TAnUfficio.class);
		criteria.add(Restrictions.eq("codiKIdUfficio", idUffOwn));
		listaUffici = getHibernateTemplate().findByCriteria(criteria);
		if (listaUffici!=null && !listaUffici.isEmpty())
		{
			TAnUfficio ufficio = listaUffici.get(0);
			List<TAnUtenzePdoc> listaUtenzePdoc = null;
			DetachedCriteria criteriaUtenze = DetachedCriteria.forClass(TAnUtenzePdoc.class);
			criteriaUtenze.add(Restrictions.eq("codiKIdUffGiudiz", ufficio.getDescUffGiudiz()));
			listaUtenzePdoc = getHibernateTemplate().findByCriteria(criteriaUtenze);
			if (listaUtenzePdoc!=null && !listaUtenzePdoc.isEmpty())
			{
				utenzaPdoc = listaUtenzePdoc.get(0);
			}
			else
				return null;
		}
		return utenzaPdoc;
	}
	
	public TAnUtenzePdoc getUtenzaPdoc(String codUgna) {
		List<TAnUtenzePdoc> listaUtenzePdoc = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TAnUtenzePdoc.class);
		criteria.add(Restrictions.eq("codiKIdUffGiudiz", codUgna));
		listaUtenzePdoc = getHibernateTemplate().findByCriteria(criteria);
		if (listaUtenzePdoc!=null && !listaUtenzePdoc.isEmpty())
		{
			return listaUtenzePdoc.get(0);
		}
		else
			return null;
	}
	
	
	// novembre 2019 -  versione Db
	@Override
	public String getVersioneDb(final String softwareVersion) {

		return (String) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				String versioneDb = null;
				String qry = 
						" SELECT VERSIONE " + " FROM T_VERSIONE " 
								+ " where VERSIONE_SOFTWARE =:versione_software "
								+ " order by codi_k_id_ver desc";
				SQLQuery query = getSession().createSQLQuery(qry);
				query.setParameter("versione_software", softwareVersion, Hibernate.STRING);
				List<String> list = query.list();
				if ((list != null) && (list.size() > 0)) {
					versioneDb = list.get(0);
				}
				return versioneDb ;
			}
		});
	}
	// marzo 2020 - gestione spese processuali

	public List<TTpSpeseProcessuali> listaSpeseProcessuali() {
		Date todayToCompare = DateUtils.parseDate(DateUtils.formatDate(new Date(), DateUtils.FORMAT_STANDARD), DateUtils.FORMAT_STANDARD);
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpSpeseProcessuali.class);
		criteria.add(Restrictions.le("dataInizioValidita", todayToCompare));
		criteria.add(Restrictions.or(Restrictions.isNull("dataFineValidita"), Restrictions.gt("dataFineValidita", todayToCompare)));
		criteria.addOrder(Order.asc("importoSpese"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override

	public TTpSpeseProcessuali ricercaSpeseProcessuali(final int IdSpesaP) {

		return (TTpSpeseProcessuali) getHibernateTemplate().execute(new HibernateCallback() {
	
			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				TTpSpeseProcessuali speseProcessuali = null;
				SQLQuery query = getSession().createSQLQuery(""
						+ "SELECT T_TP_SPESE_PROCESSUALI.* FROM T_TP_SPESE_PROCESSUALI " 
								+ " where CODI_K_ID_SPESE_PROCESSUALI =:CODI_K_ID_SPESE_PROCESSUALI");
				query.addEntity(TTpSpeseProcessuali.class);
				query.setParameter("CODI_K_ID_SPESE_PROCESSUALI", IdSpesaP, Hibernate.INTEGER);
				List<TTpSpeseProcessuali> list = query.list();
				if ((list != null) && (list.size() > 0)) {
					speseProcessuali = list.get(0);
				}
				return speseProcessuali;
			}
		});
	}
}
