package it.giustizia.smp.dao;

import java.math.BigInteger;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import it.giustizia.smp.model.TAmministratori;
import it.giustizia.smp.model.TAnOperFinanz;
import it.giustizia.smp.model.TAnUfficio;
import it.giustizia.smp.model.TAnbscMsg;
import it.giustizia.smp.model.TAnimali;
import it.giustizia.smp.model.TAtto;
import it.giustizia.smp.model.TAttoId;
import it.giustizia.smp.model.TAzienda;
import it.giustizia.smp.model.TBene;
import it.giustizia.smp.model.TBeneFin;
import it.giustizia.smp.model.TBeneImmat;
import it.giustizia.smp.model.TBeneImmobile;
import it.giustizia.smp.model.TCollezioni;
import it.giustizia.smp.model.TContante;
import it.giustizia.smp.model.TDecreto;
import it.giustizia.smp.model.TDefPatr;
import it.giustizia.smp.model.TDenaro;
import it.giustizia.smp.model.TPersonaFisica;
import it.giustizia.smp.model.TPersonaFisicaId;
import it.giustizia.smp.model.TPersonaGiur;
import it.giustizia.smp.model.TPersonaGiurId;
import it.giustizia.smp.model.TProcedimento;
import it.giustizia.smp.model.TRelAtti;
import it.giustizia.smp.model.TRelAttoMisPatr;
import it.giustizia.smp.model.TRelAttoProc;
import it.giustizia.smp.model.TRelBeneAtto;
import it.giustizia.smp.model.TRelBeneProcDisp;
import it.giustizia.smp.model.TRelImmProcVinc;
import it.giustizia.smp.model.TRelMagProc;
import it.giustizia.smp.model.TRelProcBene;
import it.giustizia.smp.model.TRelProcBeneAttoAmm;
import it.giustizia.smp.model.TRelProcProc;
import it.giustizia.smp.model.TRelProcSogint;
import it.giustizia.smp.model.TRelUdienzaStatoUd;
import it.giustizia.smp.model.TSoggetto;
import it.giustizia.smp.model.TTpAnbscMsg;
import it.giustizia.smp.model.TTpSpeseProcessuali;
import it.giustizia.smp.model.TUdienza;
import it.giustizia.smp.model.TVeicolo;
import it.giustizia.smp.utility.AnbscEnum;

@SuppressWarnings("unchecked")
public class AnbscDaoImpl extends HibernateDaoSupport implements AnbscDao {
	
	public boolean verificaInvioSequestroAnbsc(long idProcedimento, long idAtto){
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelBeneAtto.class);
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		criteria.add(Restrictions.eq("id.codiKIdAtto", idAtto));
		
		List<TRelBeneAtto> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
		{
			int totale = risultati.size();
			int count = 0;
			for (TRelBeneAtto tRelBeneAtto : risultati) {
				if (tRelBeneAtto.getDataAssunzione()!=null) 
					count++;
			}
			if(count == totale)
				return true;
			else
				return false;
		}
        else
        	return false;
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
	
	public TProcedimento getProcedimentoEmittente(long idProcedimento) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelProcProc.class);
		criteria.add(Restrictions.eq("id.codiKIdProc1", idProcedimento));
		
		List<TRelProcProc> listaTRelProcProc = getHibernateTemplate().findByCriteria(criteria);
		if (listaTRelProcProc.size() > 0)
		{
			Iterator<TRelProcProc> iter = listaTRelProcProc.iterator();
			while(iter.hasNext())
			{
				TRelProcProc modelTRelProcProc = (TRelProcProc)iter.next();
				DetachedCriteria criteria2 = DetachedCriteria.forClass(TProcedimento.class);
				criteria2.add(Restrictions.eq("codiKIdProc", modelTRelProcProc.getId().getCodiKIdProc2()));
				criteria2.createAlias("TTpTipoFascicolo", "tipoFasc");
				criteria2.add(Restrictions.eq("tipoFasc.codiKIdTipoFasc", 7));
				List<TProcedimento> procedimenti = getHibernateTemplate().findByCriteria(criteria2);
				if(procedimenti.size() > 0)
				{
					return procedimenti.get(0);
				}
			}
			return null;
		}
		else
			return null;
	}

	
	
	public TRelProcProc getProcedimentoConfiscaIIGrado(long idProc) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelProcProc.class);
		criteria.add(Restrictions.eq("id.codiKIdProc1", idProc));
		criteria.createAlias("TAnUfficio", "ufficio");
		criteria.add(Restrictions.eq("ufficio.TTpTipoUfficio.codiKIdTipoUff", 19));
		
		List<TRelProcProc> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
		{
			TRelProcProc tRelProcProc = risultati.get(0);
			return tRelProcProc;
		}
		else
			return null;
	}


	public Date getDataPropostaSequestro(final long idProcedimento) {
			return (Date) getHibernateTemplate().execute(new HibernateCallback() {

				public Object doInHibernate(final Session session) throws HibernateException, SQLException {

					Query q = session.getNamedQuery("PG_ANBSC_get_data_proposta_sequestro");
					q.setParameter("vidProc", idProcedimento, Hibernate.LONG);
					q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
					List l = q.list();
					//Map<Long, String> map = new HashMap<Long, String>();
					if ((l != null) && (l.size() > 0)) {
						HashMap element = (HashMap) l.get(0);
						Date dataPropostaSequestro = (Date) element.get("dataEmiss");
						return dataPropostaSequestro;
					}
					return null;
				}
			});
		}

	public TRelMagProc getGiudiceDelegato(long idProcedimento) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelMagProc.class);
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		criteria.add(Restrictions.eq("TTpTipoRuoloMag.codiKIdRuoloMag", 4));
		criteria.add(Restrictions.isNotNull("dataFineAssegn"));
		
		List<TRelMagProc> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
		{
			TRelMagProc tRelMagProc = risultati.get(0);
			return tRelMagProc;
		}
		else
			return null;
	}

	public String getUfficio(Integer idUfficio) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TAnUfficio.class);
		criteria.add(Restrictions.eq("codiKIdUfficio", idUfficio));
		
		List<TAnUfficio> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
		{
			TAnUfficio ufficio = risultati.get(0);
			return ufficio.getDescUffGiudiz();
		}
		else
			return null;
	}

	public TDecreto getDecreto(long idDecreto) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TDecreto.class);
		criteria.add(Restrictions.eq("id.codiKIdDecreto", idDecreto));
		
		List<TDecreto> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
		{
			TDecreto decreto = risultati.get(0);
			return decreto;
		}
		else
			return null;
	}

	public Date getDataDepositoProvv(long idAtto) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TAtto.class);
		criteria.add(Restrictions.eq("id.codiKIdAtto", idAtto));
		
		List<TAtto> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
		{
			TAtto atto = risultati.get(0);
			return atto.getDataDeposito();
		}
		else
			return null;
	}

	public List<Long> getListaIdBeni(long idProcedimento, long idAtto) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelBeneAtto.class);
		criteria.add(Restrictions.eq("id.codiKIdAtto", idAtto));
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		criteria.setProjection(Projections.distinct(Projections.projectionList()
			    .add(Projections.property("id.codiKIdBene"), "id.codiKIdBene")));
		List<Long> risultati = getHibernateTemplate().findByCriteria(criteria);

		return risultati;
	}

	public TBene getBene(long idBene) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TBene.class);
		criteria.add(Restrictions.eq("id.codiKIdBene", idBene));
		
		List<TBene> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
		{
			TBene bene = risultati.get(0);
			return bene;
		}
		else
			return null;
	}
	
	public BigInteger getTipologiaBene(long idBene){
		DetachedCriteria criteria = DetachedCriteria.forClass(TBene.class);
		criteria.add(Restrictions.eq("id.codiKIdBene", idBene));
		
		List<TBene> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
		{
			if(risultati.get(0).getTTpTipoBene() != null)
				return BigInteger.valueOf(risultati.get(0).getTTpTipoBene().getCodiKIdTipoBene());
			else
				return null;
		}
		else
			return null;
	}
	
	public BigInteger getCategoriaBene(long idBene){
		DetachedCriteria criteria = DetachedCriteria.forClass(TBene.class);
		criteria.add(Restrictions.eq("id.codiKIdBene", idBene));
		
		List<TBene> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
		{
			if(risultati.get(0).getTTpCatBene() != null)
				return BigInteger.valueOf(risultati.get(0).getTTpCatBene().getCodiKIdCatBene());
			else
				return null;
		}
		else
			return null;
	}
	
	public BigInteger getSottocategoriaBene(long idBene){
		DetachedCriteria criteria = DetachedCriteria.forClass(TBene.class);
		criteria.add(Restrictions.eq("id.codiKIdBene", idBene));
		
		List<TBene> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
		{
			if(risultati.get(0).getTTpSottocatBene() != null)
				return BigInteger.valueOf(risultati.get(0).getTTpSottocatBene().getCodiKIdSottocat());
			else
				return null;
		}
		else
			return null;
	}

	public TAnimali getAnimali(long idBene) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TAnimali.class);
		criteria.add(Restrictions.eq("id.codiKIdAnimale", idBene));
		
		List<TAnimali> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
		{
			TAnimali animale = risultati.get(0);
			return animale;
		}
		else
			return null;
	}

	public TCollezioni getCollezioni(long idBene) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TCollezioni.class);
		criteria.add(Restrictions.eq("id.codiKIdColl", idBene));
		
		List<TCollezioni> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
		{
			TCollezioni collezione = risultati.get(0);
			return collezione;
		}
		else
			return null;
	}

	public TBeneImmat getImmateriali(long idBene) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TBeneImmat.class);
		criteria.add(Restrictions.eq("id.codiKIdBeneImm", idBene));
		
		List<TBeneImmat> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
		{
			TBeneImmat immateriale = risultati.get(0);
			return immateriale;
		}
		else
			return null;
	}

	public TVeicolo getVeicoli(long idBene) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TVeicolo.class);
		criteria.add(Restrictions.eq("id.codiKIdVeicolo", idBene));
		
		List<TVeicolo> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
		{
			TVeicolo veicolo = risultati.get(0);
			return veicolo;
		}
		else
			return null;
	}

	public TAzienda getAzienda(long idBene) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TAzienda.class);
		criteria.add(Restrictions.eq("id.codiKIdAzienda", idBene));
		
		List<TAzienda> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
		{
			TAzienda azienda = risultati.get(0);
			return azienda;
		}
		else
			return null;
	}

	public TDenaro getDenaro(long idBene) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TDenaro.class);
		criteria.add(Restrictions.eq("id.codiKIdDenaro", idBene));
		criteria.createAlias("TAnOperFinanz", "operatoreFin");
//		criteria.createAlias("TTpValuta", "valuta");
		
		List<TDenaro> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
		{
			TDenaro denaro = risultati.get(0);
			return denaro;
		}
		else
			return null;
	}

	public TContante getContante(long idBene, Integer uffOwner) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TContante.class);
		criteria.add(Restrictions.eq("id.codiKIdContante", idBene));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", uffOwner));
		List<TContante> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
		{
			TContante contante = risultati.get(0);
			return contante;
		}
		else
			return null;
	}

	public TBeneFin getFinanziario(long idBene, Integer uffOwner) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TBeneFin.class);
		criteria.add(Restrictions.eq("id.codiKIdBeneFin", idBene));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", uffOwner));
		List<TBeneFin> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
		{
			TBeneFin finanziario = risultati.get(0);
			return finanziario;
		}
		else
			return null;
	}

	public TBeneImmobile getImmobile(long idBene, Integer uffOwner) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TBeneImmobile.class);
		criteria.add(Restrictions.eq("id.codiKIdBeneImm", idBene));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", uffOwner));
		List<TBeneImmobile> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
		{
			TBeneImmobile immobile = risultati.get(0);
			return immobile;
		}
		else
			return null;
	}

	public TRelBeneProcDisp getDisponibilita(long idProc, long idBene) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelBeneProcDisp.class);
		criteria.add(Restrictions.eq("id.codiKIdProc", idProc));
		criteria.add(Restrictions.eq("id.codiKIdBene", idBene));
		
		List<TRelBeneProcDisp> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
		{
			TRelBeneProcDisp disponibilita = risultati.get(0);
			return disponibilita;
		}
		else
			return null;
	}

	public TRelProcBene getProcBene(long idProc, long idBene) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelProcBene.class);
		criteria.add(Restrictions.eq("id.codiKIdProc", idProc));
		criteria.add(Restrictions.eq("id.codiKIdBene", idBene));
		
		List<TRelProcBene> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
		{
			TRelProcBene procBene = risultati.get(0);
			return procBene;
		}
		else
			return null;
	}

	public TRelImmProcVinc getVincolo(long idProc, long idBene) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelImmProcVinc.class);
		criteria.add(Restrictions.eq("id.codiKIdProc", idProc));
		criteria.add(Restrictions.eq("id.codiKIdBene", idBene));
		
		List<TRelImmProcVinc> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
		{
			TRelImmProcVinc vincolo = risultati.get(0);
			return vincolo;
		}
		else
			return null;
	}
	
	public TAnOperFinanz getOperatoreFinanziario(Class clazz, long idBene) {
		
		if(clazz.getName().equalsIgnoreCase("TDenaro")){
			DetachedCriteria criteria = DetachedCriteria.forClass(TDenaro.class);
			criteria.add(Restrictions.eq("id.codiKIdDenaro", idBene));
			List<TDenaro> risultati = getHibernateTemplate().findByCriteria(criteria);
			if (risultati.size() > 0)
			{
				TAnOperFinanz operatoreFinanziario = risultati.get(0).getTAnOperFinanz();
				return operatoreFinanziario;
			}
			else
				return null;
		}
		else
			return null;
	}
	
	public TAnOperFinanz getOperatoreFinanziario(String abi, String cab) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TAnOperFinanz.class);
		criteria.add(Restrictions.eq("id.codiKIdAbi", abi));
		criteria.add(Restrictions.eq("id.codiKIdCab", cab));
		List<TAnOperFinanz> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
		{
			TAnOperFinanz operatoreFinanziario = risultati.get(0);
			return operatoreFinanziario;
		}
		else
			return null;
	}

	public List<Long> getRelAttoMisPatr(final long idProc, final long idAtto, final long idBene) {
		List<Long> risultati = new ArrayList<Long>();
		//Soggetti titolari
		DetachedCriteria criteriaTitolare = DetachedCriteria.forClass(TRelAttoMisPatr.class);
		criteriaTitolare.add(Restrictions.eq("id.codiKIdProc", idProc));
		criteriaTitolare.add(Restrictions.eq("id.codiKIdAtto", idAtto));
		criteriaTitolare.add(Restrictions.eq("id.codiKIdBene", idBene));
		criteriaTitolare.setProjection(Projections.distinct(Projections.projectionList()
			    .add(Projections.property("id.codiKIdSoggTit"), "id.codiKIdSoggTit")));
        
		List<Long> listaTitolare = getHibernateTemplate().findByCriteria(criteriaTitolare);
		risultati = listaTitolare;
        
        //Soggetti interessati
        DetachedCriteria criteriaInteressato = DetachedCriteria.forClass(TRelAttoMisPatr.class);
        criteriaInteressato.add(Restrictions.eq("id.codiKIdProc", idProc));
        criteriaInteressato.add(Restrictions.eq("id.codiKIdAtto", idAtto));
        criteriaInteressato.add(Restrictions.eq("id.codiKIdBene", idBene));
        criteriaInteressato.setProjection(Projections.distinct(Projections.projectionList()
			    .add(Projections.property("id.codiKIdSoggInt"), "id.codiKIdSoggInt")));
        
        List<Long> listaInteressato = getHibernateTemplate().findByCriteria(criteriaInteressato);
        if(listaInteressato != null)
	    {
	      	Iterator<Long> iter = listaInteressato.iterator();
			while(iter.hasNext())
			{
				Long relAttoMisPatr = (Long)iter.next();
				risultati.add(relAttoMisPatr);
			}
	    }
        
        if(risultati != null && risultati.size()>1)
        {
        	Collections.sort(risultati);
        	long temp1 = risultati.get(0);
        	for(int i=1; i<risultati.size(); i++)
        	{
        		long temp2 = risultati.get(i);
        		if(temp1 == temp2)
        		{
        			risultati.remove(i);
        			i--;
        		}
        		temp1 = temp2;
        	}
        }
        
        return risultati;
	}

	public TRelProcSogint getTRelProcSogint(long idProc, long idSogg, Integer uffOwner) {
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
	
	public List<TRelProcSogint> getTRelProcSogintList(long idProc, long idSogg, Integer uffOwner) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelProcSogint.class);
		criteria.add(Restrictions.eq("id.codiKIdProc", idProc));
		criteria.add(Restrictions.eq("id.codiKIdSoggInt", idSogg));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", uffOwner));
		
		List<TRelProcSogint> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
			return risultati;
		else
			return null;
	}

	public String getTipoSoggetto(long idSoggetto) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TSoggetto.class);
		criteria.add(Restrictions.eq("id.codiKIdSoggInt", idSoggetto));
		
		List<TSoggetto> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
		{
			TSoggetto soggetto = risultati.get(0);
			return String.valueOf(soggetto.getTTpTipoSoggetto().getCodiKIdTipoSogg());
		}
		else
			return null;
	}

	public TPersonaFisica getPersonaFisica(long idSoggetto, short versioneSoggetto, int uffOwner) {
//		DetachedCriteria criteria = DetachedCriteria.forClass(TPersonaFisica.class);
//		criteria.add(Restrictions.eq("id.codiKIdUffOwner", uffOwner));
//		criteria.add(Restrictions.eq("id.codiKIdPersonaFis", idSoggetto));
//		criteria.add(Restrictions.eq("id.codiKIdVer", (short)versioneSoggetto));
//		
//		List<TPersonaFisica> risultati = getHibernateTemplate().findByCriteria(criteria);
//		if (risultati.size() > 0)
//		{
//			TPersonaFisica personaFisica = risultati.get(0);
//			return personaFisica;
//		}
//		else
//			return null;
		return 	getHibernateTemplate().get(TPersonaFisica.class, new TPersonaFisicaId(uffOwner,idSoggetto,versioneSoggetto));
	}

	public TPersonaGiur getPersonaGiuridica(long idSoggetto, short versioneSoggetto, int uffOwner) {
//		DetachedCriteria criteria = DetachedCriteria.forClass(TPersonaGiur.class);
//		criteria.add(Restrictions.eq("id.codiKIdUffOwner", uffOwner));
//		criteria.add(Restrictions.eq("id.codiKIdPersGiur", idSoggetto));
//		criteria.add(Restrictions.eq("id.codiKIdVer", (short)versioneSoggetto));
//		
//		List<TPersonaGiur> risultati = getHibernateTemplate().findByCriteria(criteria);
//		if (risultati.size() > 0)
//		{
//			TPersonaGiur personaGiuridica = risultati.get(0);
//			return personaGiuridica;
//		}
//		else
//			return null;
		return 	getHibernateTemplate().get(TPersonaGiur.class, new TPersonaGiurId(uffOwner,idSoggetto,versioneSoggetto));
	}

	public List<TRelAttoMisPatr> getSoggettoTitolare(long idProc, long idAtto, long idBene, int uffOwner) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelAttoMisPatr.class);
		criteria.add(Restrictions.eq("id.codiKIdProc", idProc));
		criteria.add(Restrictions.eq("id.codiKIdAtto", idAtto));
		criteria.add(Restrictions.eq("id.codiKIdBene", idBene));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", uffOwner));
		criteria.addOrder(Order.asc("id.codiKIdSoggTit"));
		
		List<TRelAttoMisPatr> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
		{
			return risultati;
		}
		else
			return null;
	}

	public TRelBeneAtto getTRelBeneAtto(long idProc, long idAtto, long idBene, long idSoggTit, int tipoInteresse, int uffOwner) {
		Criteria criteria = getSession().createCriteria(TRelBeneAtto.class);
		criteria.add(Restrictions.eq("id.codiKIdProc", idProc));
		criteria.add(Restrictions.eq("id.codiKIdAtto", idAtto));
		criteria.add(Restrictions.eq("id.codiKIdBene", idBene));
		criteria.add(Restrictions.eq("id.codiKIdSoggTit", idSoggTit));
		criteria.add(Restrictions.eq("id.codiKIdTipoInt", tipoInteresse));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", uffOwner));
		criteria.createAlias("TTpTipoMisura", "misura", Criteria.LEFT_JOIN);
		
		List<TRelBeneAtto> risultati = criteria.list();
		if (risultati.size() > 0)
		{
			TRelBeneAtto tRelBeneAtto = risultati.get(0);
			return tRelBeneAtto;
		}
		else
			return null;
	}

	public long getIdUdienza(long idAtto, Integer uffOwner) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TDecreto.class);
		criteria.add(Restrictions.eq("id.codiKIdDecreto", idAtto));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", uffOwner));
		
		List<TDecreto> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
		{
			TDecreto decreto = risultati.get(0);
			return decreto.getId().getCodiKIdDecreto();
		}
		else
			return 0L;
	}

	public TUdienza getUdienza(long idProcedimento, long idAtto, Integer idUfficio) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TUdienza.class);
		criteria.add(Restrictions.eq("TProcedimento.codiKIdProc", idProcedimento));
		criteria.add(Restrictions.eq("id.codiKIdUdienza", idAtto));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficio));
		
		List<TUdienza> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
		{
			TUdienza udienza = risultati.get(0);
			return udienza;
		}
		else
			return null;
	}

	public List<TRelUdienzaStatoUd> getStatoUdienza(long idAtto, Integer idUfficio) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelUdienzaStatoUd.class);
		criteria.add(Restrictions.eq("id.codiKIdUdienza", idAtto));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficio));
		
		List<TRelUdienzaStatoUd> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
			return risultati;
		else
			return null;
	}

	public List<Long> getIdAmministratore(long idProcedimento, long idAtto, Integer idUfficio) {
		 DetachedCriteria criteria = DetachedCriteria.forClass(TRelProcBeneAttoAmm.class);
		 criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		 criteria.add(Restrictions.eq("id.codiKIdAtto", idAtto));
		 criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficio));
		 criteria.setProjection(Projections.distinct(Projections.projectionList()
				    .add(Projections.property("id.codiKIdAmmin"), "id.codiKIdAmmin")));

		 List<Long> result = getHibernateTemplate().findByCriteria(criteria);
		 
		return result;
	}

	public TAmministratori getAmministratore(long idAmministratore) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TAmministratori.class);
		criteria.add(Restrictions.eq("codiKIdAmmin", idAmministratore));
				
		List<TAmministratori> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
			return risultati.get(0);
		else
			return null;
	}

	public List<TRelProcBeneAttoAmm> getAmministrazioni(final long idProcedimento, final long idAtto, final Integer uffOwner, boolean escludiAgenzia) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelProcBeneAttoAmm.class);
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		criteria.add(Restrictions.eq("id.codiKIdAtto", idAtto));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", uffOwner));
		if(escludiAgenzia)
			criteria.add(Restrictions.not(Restrictions.eq("id.codiKIdAmmin", 9999990000000L)));
		criteria.addOrder( Order.asc( "id.codiKIdAmmin" ));
		criteria.addOrder( Order.asc( "id.codiKIdBene" ));
		List<TRelProcBeneAttoAmm> result = getHibernateTemplate().findByCriteria(criteria);
		if(result.size() > 0)
			return result;
		else
			return null;
	}

	public List<Long> getSoggettiProposti(long idProc, long idAtto,	long idBene, long idSoggTit, Integer uffOwner) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelAttoMisPatr.class);
		criteria.add(Restrictions.eq("id.codiKIdProc", idProc));
		criteria.add(Restrictions.eq("id.codiKIdAtto", idAtto));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", uffOwner));
		criteria.add(Restrictions.eq("id.codiKIdBene", idBene));
		criteria.add(Restrictions.eq("id.codiKIdSoggTit", idSoggTit));
		criteria.setProjection(Projections.distinct(Projections.projectionList()
				    .add(Projections.property("id.codiKIdSoggInt"), "id.codiKIdSoggInt")));
		
		List<Long> result = getHibernateTemplate().findByCriteria(criteria);
		return result;
		
	}

	@Transactional
	public Long insertMsg(final long idProc, final long idAtto, final long idTipoAnbscMsg, final Blob xml, final String descUtente) {
		TTpAnbscMsg tipoMsg = this.getByCodiKIdTipoAnbscMsg(idTipoAnbscMsg);
		TAnbscMsg model = new TAnbscMsg();
		model.setCodiFkIdAtto(idAtto);
		model.setCodiFkIdProc(idProc);
		model.settTpAnbscMsg(tipoMsg);
		model.setDescUtente(descUtente);
		model.setXmlMessaggio(xml);
		//model.setDescTipoMsg(tipoMsg.getCodiAnbscMsg());
		model.setCodiKIdMsg(this.getNextValFromTAnbscMsg());
		Long idAnbscMsg = (Long) getHibernateTemplate().save(model);
		getHibernateTemplate().flush();
		return idAnbscMsg;
//		return (Long)getHibernateTemplate().execute(new HibernateCallback() {
//
//			public Long doInHibernate(final Session session) throws HibernateException, SQLException {
//				Query q = session.getNamedQuery("PG_ANBSC_inserisci_Messaggio");
//				q.setParameter("VIDPROC", idProc, Hibernate.LONG);
//				q.setParameter("VIDATTO", idAtto, Hibernate.LONG);
//				q.setParameter("VIDTIPOMSG", idTipoAnbscMsg, Hibernate.LONG);
//				q.setParameter("VXML", xml, Hibernate.BLOB);
//				q.setParameter("VDESCUTENTE", descUtente, Hibernate.STRING);
//				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
//				List l = q.list();
//				HashMap first = (HashMap) l.get(0);
//				Long id = (Long) first.get("IDMSG");
//				return id;
//			}
//        });
	}

	@SuppressWarnings("rawtypes")
	private long getNextValFromTAnbscMsg() {
		DetachedCriteria criteria = DetachedCriteria.forClass(TAnbscMsg.class);
		criteria.setProjection(Projections.max("codiKIdMsg"));
		List list = getHibernateTemplate().findByCriteria(criteria);
		long number = 0L;
		if(list != null && list.size() > 0 && list.get(0) != null) {
			number = (Long) list.get(0);
		}
		long nextVal = number+1L;
		return nextVal;
	}
	
	
	@SuppressWarnings("rawtypes")
	public Object updateRispostaMsgDatabase(final long idMsg, final char esito, final String risposta, final Blob xml){
		return getHibernateTemplate().execute(new HibernateCallback() {

		public Object doInHibernate(final Session session) throws HibernateException, SQLException {
			DetachedCriteria criteria = DetachedCriteria.forClass( TAnbscMsg.class );
            criteria.add(Restrictions.eq( "codiKIdMsg", idMsg ));
            TAnbscMsg anbscMsgModel = (TAnbscMsg)getHibernateTemplate().findByCriteria( criteria ).get( 0 );
            anbscMsgModel.setFlgEsito(esito);
			anbscMsgModel.setDescRisposta(risposta);
			if (xml != null)
				anbscMsgModel.setXmlAnbsc(xml);
			session.update(anbscMsgModel);
			session.flush();

			return null;
		}
		});
	}
	
	public List<TAnbscMsg> getDaInviare() {
		DetachedCriteria criteria = DetachedCriteria.forClass(TAnbscMsg.class);
		criteria.add(Restrictions.isNull("flgEsito"));
		criteria.addOrder(Order.asc( "dataUltAgg" ));
				
		List<TAnbscMsg> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
			return risultati;
		else
			return null;
	}


	public List<TRelBeneAtto> getListaTRelBeneAtto(long idProcedimentoOrigine, long idProcedimentoDestinazione, int idUfficio) {

		TProcedimento procedimentoDestModel = this.getProcedimento(idProcedimentoDestinazione,idUfficio);
		Short numeAnnoProc = procedimentoDestModel.getNumeAnnoProc();
		Long numeProc = procedimentoDestModel.getNumeProc();
		String descProcedimentoDestinazione = String.valueOf(numeAnnoProc) + "/" + String.valueOf(numeProc);
		
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelBeneAtto.class);
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimentoOrigine));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficio));
		criteria.add(Restrictions.eq("descProcDestinaz", descProcedimentoDestinazione));
		criteria.add(Restrictions.eq("flgValido", 'N'));
		criteria.add(Restrictions.eq("TTpTipoMotivo.codiKIdMotivo", '1'));
		
		List<TRelBeneAtto> result = getHibernateTemplate().findByCriteria(criteria);
		if (result.size() > 0)
		{
			return result;
		}
		
		return null;
	}

	public Date getDataDefinitivita(long idProcedimento, long idAtto, Integer idUfficio) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TDefPatr.class);
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		criteria.add(Restrictions.eq("id.codiKIdAtto", idAtto));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficio));
		/* Controllare questo order !
		//criteria.addOrder(Order.desc("id.dataUltAgg"));		 
		*/

		List<TDefPatr> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
		{
			TDefPatr rec = risultati.get(0);
			return rec.getDataDefinitivita();
		}
		else
			return null;
	}
	
	public boolean verificaInvioDecretoConvalidaAnbsc(long idProcedimento, long idAtto){
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelBeneAtto.class);
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		criteria.add(Restrictions.eq("id.codiKIdAtto", idAtto));
		criteria.createAlias("TTpTipoMisura", "tipoMisura");
		criteria.add(Restrictions.eq("tipoMisura.codiKIdTipoMisura", 5));
		
		List<TRelBeneAtto> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
		{
			return true;
		}
        else
        	return false;
	}
	
	
	
	public List<Long> getListaIdBeniDecretoConvalida(long idProcedimento, long idAtto) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelBeneAtto.class);
		criteria.add(Restrictions.eq("id.codiKIdAtto", idAtto));
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		criteria.createAlias("TTpTipoMisura", "tipoMisura");
		criteria.add(Restrictions.eq("tipoMisura.codiKIdTipoMisura", 5));
		criteria.setProjection(Projections.distinct(Projections.projectionList()
			    .add(Projections.property("id.codiKIdBene"), "id.codiKIdBene")));
		List<Long> risultati = getHibernateTemplate().findByCriteria(criteria);

		return risultati;
	}

	public long recuperaAttoOrig(long idAtto, Integer idUfficioUtente) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelAtti.class);
		criteria.add(Restrictions.eq("id.codiKIdAttoFi", idAtto));
		criteria.add(Restrictions.eq("id.codiKIdUffOwnerFi", idUfficioUtente));
		
		List<TRelAtti> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
		{
			TRelAtti atto = risultati.get(0);
			return atto.getId().getCodiKIdAttoOr();
		}
        else
        	return 0;
	}
	
	public int getTipoUfficio(Integer idUfficioUtente) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TAnUfficio.class);
		criteria.add(Restrictions.eq("codiKIdUfficio", idUfficioUtente));
		criteria.createAlias("TTpTipoUfficio", "tipoUfficio");
		
		List<TAnUfficio> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
		{
			TAnUfficio ufficio = risultati.get(0);
			return ufficio.getTTpTipoUfficio().getCodiKIdTipoUff();
		}
        else
        	return 0;
	}

	public List<TAtto> listSentenze() {
		DetachedCriteria criteria = DetachedCriteria.forClass(TAtto.class);
		criteria.add(Restrictions.eq("flgAnbsc", "N"));
		List<TAtto> risultati = getHibernateTemplate().findByCriteria(criteria);

		return risultati;
	}

	public long getIdProcedimento(long idAtto, Integer idUfficioUtente) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelAttoProc.class);
		criteria.add(Restrictions.eq("id.codiKIdAtto", idAtto));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente));
		criteria.addOrder(Order.asc("id.codiKIdProc"));
		
		List<TRelAttoProc> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
		{
			TRelAttoProc atto = risultati.get(0);
			return atto.getId().getCodiKIdProc();
		}
        else
        	return 0;
	}

	@SuppressWarnings("rawtypes")
	public void updateSentenzaInviata(final long idAtto, final Integer idUfficioUtente) {
		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				TAttoId id = new TAttoId();
				id.setCodiKIdAtto(idAtto);
				id.setCodiKIdUffOwner(idUfficioUtente);
				TAtto atto = getHibernateTemplate().get(TAtto.class, id);
				atto.setFlgAnbsc('S');
				
				session.saveOrUpdate(atto);
				session.flush();
				return null;
			}
		});
		
	}

	public boolean verificaInvioStralcioRiunione(long idProcedimento) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TAnbscMsg.class);
		criteria.add(Restrictions.eq("codiFkIdProc", idProcedimento));
		
		List<TRelBeneAtto> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
		{
			return true;
		}
        else
        	return false;
	}

	@Override
	public TTpAnbscMsg getByCodiKIdTipoAnbscMsg(long codiKIdTipoAnbscMsg) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpAnbscMsg.class);
		criteria.add(Restrictions.eq("codiKIdTipoAnbscMsg", codiKIdTipoAnbscMsg));
		List<TTpAnbscMsg> risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati.get(0);
	}
		
	
}
