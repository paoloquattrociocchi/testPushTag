package it.giustizia.smp.dao;

import static it.giustizia.smp.utility.StaticQueries.*;
import it.giustizia.smp.dto.BeneTitoloDirittoDto;
import it.giustizia.smp.dto.KeyMessageDto;
import it.giustizia.smp.dto.pg_procedimento.SogIntDettaglioProcedimentoDto;
import it.giustizia.smp.form.AmministratoreForm;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.BeneForm;
import it.giustizia.smp.form.BeneSoggettoForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.model.TAmministratori;
import it.giustizia.smp.model.TAnAltriOper;
import it.giustizia.smp.model.TAnComuni;
import it.giustizia.smp.model.TAnNormative;
import it.giustizia.smp.model.TAnOperFinanz;
import it.giustizia.smp.model.TAnOperFinanzId;
import it.giustizia.smp.model.TAnStato;
import it.giustizia.smp.model.TAnimali;
import it.giustizia.smp.model.TAtto;
import it.giustizia.smp.model.TAzienda;
import it.giustizia.smp.model.TBene;
import it.giustizia.smp.model.TBeneFin;
import it.giustizia.smp.model.TBeneImmat;
import it.giustizia.smp.model.TBeneImmobile;
import it.giustizia.smp.model.TBeneImmobileId;
import it.giustizia.smp.model.TCollezioni;
import it.giustizia.smp.model.TCollezioniId;
import it.giustizia.smp.model.TContante;
import it.giustizia.smp.model.TDenaro;
import it.giustizia.smp.model.TDepositiAmm;
import it.giustizia.smp.model.TIstanza;
import it.giustizia.smp.model.TRelAttoIstGen;
import it.giustizia.smp.model.TRelAttoIstGenId;
import it.giustizia.smp.model.TRelAttoMisPatr;
import it.giustizia.smp.model.TRelAttoProc;
import it.giustizia.smp.model.TRelBeneAtto;
import it.giustizia.smp.model.TRelBeneAttoId;
import it.giustizia.smp.model.TRelBeneBene;
import it.giustizia.smp.model.TRelProcAttoAmm;
import it.giustizia.smp.model.TRelProcBene;
import it.giustizia.smp.model.TRelProcBeneAttoAmm;
import it.giustizia.smp.model.TRelProcBeneId;
import it.giustizia.smp.model.TRelProcBeneTit;
import it.giustizia.smp.model.TTpSottocatBene;
import it.giustizia.smp.model.TTpStatoConserv;
import it.giustizia.smp.model.TTpTipoInteresse;
import it.giustizia.smp.model.TTpTipoMisura;
import it.giustizia.smp.model.TTpTipoPrevenzione;
import it.giustizia.smp.model.TVeicolo;
import it.giustizia.smp.model.TVeicoloId;
import it.giustizia.smp.utility.ComparatorUtil;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dozer.Mapper;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.CollectionUtils;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class BeneDaoImpl extends HibernateDaoSupport implements BeneDao {

	@Autowired
	private Mapper	mapperBean;
	
	@Autowired
	private CommonDao commonDao;

	public void associaBeneAlProcedimento(final long idBene, final int idUffOwner, final String codUtente, final long idProcedimento, final long vidstatocons) {

		getHibernateTemplate().execute(new HibernateCallback<Object>() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				TRelProcBene relProcBene = new TRelProcBene();
				TRelProcBeneId relProcBeneId = new TRelProcBeneId();
				TTpStatoConserv statoBene = null;
				relProcBene.setDescUtente(codUtente);
				relProcBeneId.setCodiKIdUffOwner(idUffOwner);
				relProcBeneId.setCodiKIdBene(idBene);
				relProcBeneId.setCodiKIdProc(idProcedimento);
				if (vidstatocons > 0) {
					statoBene = new TTpStatoConserv();
					statoBene.setCodiKIdStatoConserv((int)vidstatocons);
				}
				relProcBene.setTTpStatoConserv(statoBene);
				relProcBene.setId(relProcBeneId);
				session.save(relProcBene);
				session.flush();
				return null;
			}
		});
	}

	
	public void associaTitoloDiDirittoAlBene(final BeneSoggettoForm form, final long idProcedimento, 
			final long idAtto, final Integer idUfficioUtente, final String codUtente, 
			final Boolean daProposta, final String nomeMetodo) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				SoggettoForm soggetto = form.getSoggetto();
				Query q = session.getNamedQuery("PG_MISURA_inserisciModificaRelBeneAtto");
				q.setParameter("idProc", idProcedimento);
				q.setParameter("idAtto", idAtto);
				q.setParameter("idUffOwn", idUfficioUtente);
				q.setParameter("idBene", form.getBene().getIdBene());
				q.setParameter("idTipoInteresse", form.getIdTipoInteresse());
				
				if(soggetto.getIdSoggetto()!=null){
					q.setParameter("idSoggTitolare", soggetto.getIdSoggetto(), Hibernate.LONG);
				}else{
					q.setParameter("idSoggTitolare", null, Hibernate.LONG);
				}
				if(form.getQuotaInteresse()!=null){
					q.setParameter("numeQuota", new BigDecimal(form.getQuotaInteresse()), Hibernate.BIG_DECIMAL);
				}else{
					q.setParameter("numeQuota", null, Hibernate.BIG_DECIMAL);
				}
				
				
				if (form.getNumeratoreFrazioneQuota() != null) {
					q.setParameter("numeFrazioneNum", form.getNumeratoreFrazioneQuota().shortValue(), Hibernate.SHORT);
				}else{
					q.setParameter("numeFrazioneNum", null, Hibernate.SHORT);
				}
				if (form.getDenominatoreFrazioneQuota() != null) {
					q.setParameter("numeFrazioneDen", form.getDenominatoreFrazioneQuota().shortValue(),Hibernate.SHORT);
				}else{
					q.setParameter("numeFrazioneDen", null, Hibernate.SHORT);
				}
				
				q.setParameter("importoBene", null, Hibernate.DOUBLE);
				
				if(soggetto.getNormativa()!= null && soggetto.getNormativa().getId()!=null && soggetto.getNormativa().getId().trim().length()>0){
					q.setParameter("idNormativa", Integer.parseInt(soggetto.getNormativa().getId()), Hibernate.INTEGER);
				}else{
					q.setParameter("idNormativa", null, Hibernate.INTEGER);
				}
				
				if(soggetto.getMisura()!= null && soggetto.getMisura().getTipoMisura() != null && soggetto.getMisura().getTipoMisura().getId()!=null && soggetto.getMisura().getTipoMisura().getId().trim().length()>0){
					q.setParameter("idTipoMisura", Integer.parseInt(soggetto.getMisura().getTipoMisura().getId()),Hibernate.INTEGER);
				}else{
					q.setParameter("idTipoMisura", null, Hibernate.INTEGER);
				}
				
				q.setParameter("idEsito1", null, Hibernate.INTEGER);
				q.setParameter("idEsito2", null, Hibernate.INTEGER);
				q.setParameter("idEsito3", null, Hibernate.INTEGER);
				q.setParameter("idEsitoFin", null, Hibernate.INTEGER);
				q.setParameter("descAltro1", null, Hibernate.STRING);
				q.setParameter("descAltro2", null, Hibernate.STRING);
				q.setParameter("descAltro3", null, Hibernate.STRING);
				q.setParameter("descUtente", codUtente, Hibernate.STRING);
				
				if(daProposta!=null && daProposta){
					q.setParameter("daProposta", "S", Hibernate.CHARACTER);
				}else{
					q.setParameter("daProposta", "N", Hibernate.CHARACTER);
				}
				
				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
				
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
				
			}
		});
	}

	
	
	public void modificaRelBeneTit(final BeneSoggettoForm form, final long idProcedimento, 
			final long idAtto, final Integer idUfficioUtente, final String codUtente, 
			final Boolean daProposta, final double numeQuotaSeq, final double numeFrazioneNumSeq,
			final double numeFrazioneDenSeq, final String tipoOperazione, final String nomeMetodo) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				SoggettoForm soggetto = form.getSoggetto();
				Query q = session.getNamedQuery("PG_MISURA_modificaRelBeneTit");
				q.setParameter("idProc", idProcedimento);
				q.setParameter("idAtto", idAtto);
				q.setParameter("idUffOwn", idUfficioUtente);
				q.setParameter("idBene", form.getBene().getIdBene());
				q.setParameter("idTipoInteresse", form.getIdTipoInteresse());
				
				if(soggetto.getIdSoggetto()!=null){
					q.setParameter("idSoggTitolare", soggetto.getIdSoggetto(), Hibernate.LONG);
				}else{
					q.setParameter("idSoggTitolare", null, Hibernate.LONG);
				}
					
				q.setParameter("numeQuotaSeq", numeQuotaSeq, Hibernate.DOUBLE);
				q.setParameter("numeFrazioneNumSeq", numeFrazioneNumSeq, Hibernate.DOUBLE);
				q.setParameter("numeFrazioneDenSeq", numeFrazioneDenSeq, Hibernate.DOUBLE);
		
				q.setParameter("descUtente", codUtente, Hibernate.STRING);
				
				if(daProposta!=null && daProposta){
					q.setParameter("daProposta", "S", Hibernate.CHARACTER);
				}else{
					q.setParameter("daProposta", "N", Hibernate.CHARACTER);
				}
				q.setParameter("tipoOperazione", tipoOperazione, Hibernate.STRING);
				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
				
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
				
			}
		});
	}
	
	
	public KeyMessageDto controlloQuota(final BeneSoggettoForm form, final long idProcedimento, 
			final long idAtto, final Integer idUfficioUtente, final String codUtente, 
			final Boolean daProposta, final String tipoOperazione, final String nomeMetodo) {

		return (KeyMessageDto)getHibernateTemplate().execute(new HibernateCallback(){

			public KeyMessageDto doInHibernate(final Session session) throws HibernateException, SQLException {
				SoggettoForm soggetto = form.getSoggetto();
				Query q = session.getNamedQuery("PG_MISURA_controlloQuota");
				q.setParameter("idProc", idProcedimento);
				q.setParameter("idAtto", idAtto);
				q.setParameter("idUffOwn", idUfficioUtente);
				q.setParameter("idBene", form.getBene().getIdBene());
				q.setParameter("idTipoInteresse", form.getIdTipoInteresse());
				
				if(soggetto.getIdSoggetto()!=null){
					q.setParameter("idSoggTitolare", soggetto.getIdSoggetto(), Hibernate.LONG);
				}else{
					q.setParameter("idSoggTitolare", null, Hibernate.LONG);
				}
					
				q.setParameter("numeQuotaSeq", form.getQuotaInteresse(), Hibernate.DOUBLE);
				q.setParameter("numeFrazioneNumSeq", form.getNumeratoreFrazioneQuota(), Hibernate.INTEGER);
				q.setParameter("numeFrazioneDenSeq", form.getDenominatoreFrazioneQuota(), Hibernate.INTEGER);
				if(soggetto.getMisura()!= null && soggetto.getMisura().getTipoMisura()!=null && soggetto.getMisura().getTipoMisura().getId()!=null && soggetto.getMisura().getTipoMisura().getId().trim().length()>0){
					q.setParameter("idTipoMisura", Integer.parseInt(soggetto.getMisura().getTipoMisura().getId()),Hibernate.INTEGER);
				}else{
					q.setParameter("idTipoMisura", null, Hibernate.INTEGER);
				}
				q.setParameter("descUtente", codUtente, Hibernate.STRING);
				
				if(daProposta!=null && daProposta){
					q.setParameter("daProposta", "S", Hibernate.CHARACTER);
				}else{
					q.setParameter("daProposta", "N", Hibernate.CHARACTER);
				}
				q.setParameter("tipoOperazione", tipoOperazione, Hibernate.STRING);
				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
				
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				KeyMessageDto rit = new KeyMessageDto();

				if(l!=null && l.size()==1){
					HashMap ritorno = (HashMap) l.get(0);
					rit.setMessage((String) ritorno.get(Constants.TipoMessaggiDb.MESSAGE));
					rit.setStatus((String) ritorno.get(Constants.TipoMessaggiDb.STATUS));
					
				}
				return rit;
				
			}
		});
	}
	
		
	public List beniRicercaCriteri(final long vidUffOwn, final long vidprocedimento, final long vtipobene, final long vcatbene, final long vsottocatbene, final TBene bene, final TAzienda azienda, final TBeneImmobile immobile, final TBeneImmat immateriale, final TVeicolo veicolo, final TBeneFin finanziario, final TContante contante, final TDenaro denaro, final Date vdataMin, final Date vdataMax) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_BENE_beni_ricerca_criteri");
				q.setParameter("vidUffOwn", vidUffOwn);
				q.setParameter("vidprocedimento", vidprocedimento);
				long vidstato = 0;
				long vidcomune = 0;
				String vcomuneestero = null;
				long vidtipostrada = 0;
				String vdescindirizzo = null;
				String vnumcivico = null;
				String vnumcap = null;
				String vragionesociale = null;
				long vsettoreaativita = 0;
				String vsezione = null;
				String vfoglio = null;
				String vparticella1 = null;
				String vsubparticella1 = null;
				String vparticella2 = null;
				String vsubparticella2 = null;
				String vnumeroRegistrazione = "";
				String vtarga = null;
				String vnumident = null;
				String vdescente = null;
				String vtipoVal = null;
				
				if (bene != null) {
					if ((bene.getTAnStato() != null) && (bene.getTAnStato().getCodiKIdStato() > 0)) {
						vidstato = bene.getTAnStato().getCodiKIdStato();
					}
					if ((bene.getTAnComuni() != null) && (bene.getTAnComuni().getCodiKIdComune() > 0)) {
						vidcomune = bene.getTAnComuni().getCodiKIdComune();
					}
					vcomuneestero = bene.getDescComuneEstero();
					if ((bene.getTTpTipoStrada() != null) && (bene.getTTpTipoStrada().getCodiKIdTipoStrada() > 0)) {
						vidtipostrada = bene.getTTpTipoStrada().getCodiKIdTipoStrada();
					}
					vdescindirizzo = bene.getDescIndirizzo();
					vnumcivico = bene.getDescCivico();
					vnumcap = bene.getDescCap();
				}
				if (vtipobene == 5L) {
					vragionesociale = azienda.getDescRagioneSoc();
					if ((azienda.getTTpSettoreAtt() != null) && (azienda.getTTpSettoreAtt().getCodiKIdSettoreAtt() > 0)) {
						vsettoreaativita = azienda.getTTpSettoreAtt().getCodiKIdSettoreAtt();
					}
				} else
					if (vtipobene == 1L) {
						vsezione = immobile.getDescSezione();
						vfoglio = immobile.getDescFoglio();
						vparticella1 = immobile.getDescParticella1();
						vparticella2 = immobile.getDescParticella2();
						vsubparticella1 = immobile.getDescSubParticella1();
						vsubparticella2 = immobile.getDescSubParticella2();
					}
					else 
					    if (vtipobene == 2L) { // Tipo Bene : Mobile 
					        // Categoria : Denaro - Sottocategoria : Contante
					        if (vcatbene == 6L && vsottocatbene == 35L) {
					        	 vdescente = contante.getDescEnteDepos();
			                     vnumident = contante.getDescIdentEnte();
					            if ((contante.getTTpValuta() != null) && (!contante.getTTpValuta().getCodiKIdValuta().equals("-1"))) {
			                        vtipoVal = contante.getTTpValuta().getCodiKIdValuta();
			                    }
					        }
					        if (vcatbene == 6L && vsottocatbene != 35L) 
					        {
					        	vdescente = denaro.getDescEnteDepos();
		                        vnumident = denaro.getDescIdentEnte();
					        }
					    }
    					else
    						if (vtipobene == 3L) {
    							if (vcatbene == 10L) {
    								if (veicolo.getDescReg() != null) {
    									vnumeroRegistrazione = veicolo.getDescReg();
    								}
    								vtarga = veicolo.getDescTarga();
    							}
    							if (vcatbene == 11L) {
    								if (immateriale.getDescRegistrazione() != null) {
    									vnumeroRegistrazione = immateriale.getDescRegistrazione();
    								}
    							}
    						} else
    							if (vtipobene == 4L) {
    								
    								if (finanziario.getDescRegistrazione() != null) {
    									vnumident = finanziario.getDescRegistrazione();
    								}
    								if (finanziario.getDescEnteCreditizio() != null) {
    									vdescente = finanziario.getDescEnteCreditizio();
    								}
    							}
				q.setParameter("vtipobene", vtipobene);
				if (vcatbene > 0) {
					q.setParameter("vcatbene", vcatbene);
				} else {
					q.setParameter("vcatbene", null, Hibernate.LONG);
				}
				if (vsottocatbene > 0) {
					q.setParameter("vsottocatbene", vsottocatbene);
				} else {
					q.setParameter("vsottocatbene", null, Hibernate.LONG);
				}
				if (vidstato > 0) {
					q.setParameter("vidstato", vidstato);
				} else {
					q.setParameter("vidstato", null, Hibernate.LONG);
				}
				if (vidcomune > 0) {
					q.setParameter("vidcomune", vidcomune);
				} else {
					q.setParameter("vidcomune", null, Hibernate.LONG);
				}
				q.setParameter("vcomuneestero", vcomuneestero);
				if (vidtipostrada > 0) {
					q.setParameter("vidtipostrada", vidtipostrada);
				} else {
					q.setParameter("vidtipostrada", null, Hibernate.LONG);
				}
				q.setParameter("vdescindirizzo", vdescindirizzo);
				q.setParameter("vnumcivico", vnumcivico);
				q.setParameter("vnumcap", vnumcap);
				q.setParameter("vragionesociale", vragionesociale);
				if (vsettoreaativita > 0) {
					q.setParameter("vsettoreaativita", vsettoreaativita);
				} else {
					q.setParameter("vsettoreaativita", null, Hibernate.LONG);
				}
				q.setParameter("vsezione", vsezione);
				q.setParameter("vfoglio", vfoglio);
				q.setParameter("vparticella1", vparticella1);
				q.setParameter("vparticella2", vparticella2);
				q.setParameter("vsubparticella1", vsubparticella1);
				q.setParameter("vsubparticella2", vsubparticella2);
				q.setParameter("vnumeroRegistrazione", vnumeroRegistrazione);
				q.setParameter("vtarga", vtarga);
				q.setParameter("vdataMin", vdataMin, Hibernate.DATE);
				q.setParameter("vdataMax", vdataMax, Hibernate.DATE);
				if (vnumident != null) {
					q.setParameter("vnumident", vnumident);
				} else {
					q.setParameter("vnumident", null, Hibernate.STRING);
				}
				q.setParameter("vdescente", vdescente);
				q.setParameter("vtipoVal", vtipoVal);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});
	}

	public void copiaTitoloDiDiritto(final long idBene, final long idProcedimento, final Integer idUfficioUtente, final String listaIdBeni, final long idAtto, final String codUtente, final String nomeMetodo) {

		getHibernateTemplate().execute(new HibernateCallback<Object>() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_BENE_copia_titoli_diritto");
				q.setParameter("idBene", idBene);
				q.setParameter("idProcedimento", idProcedimento);
				q.setParameter("idUfficioUtente", idUfficioUtente);
				q.setParameter("listaIdBeni", listaIdBeni);
				q.setParameter("idAtto", idAtto);
				q.setParameter("idCodiceUtente", codUtente);
				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List<Map<?, ?>> l = q.list();
				return l;
			}
		});
	}

	
	public void eliminaSoggettoComeTitoloDiInteresse(final long idPersona, final long idAtto, final long idProcedimento, final Integer idUfficioUtente, final long idBene, final int idTipoInteresse) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				
				Query q = session.getNamedQuery("PG_MISURA_elimina_rel_bene_atto");
                // chiave
				q.setParameter("idProc", idProcedimento);
				q.setParameter("idAtto", idAtto);
				q.setParameter("idUffOwn", idUfficioUtente);
				q.setParameter("idBene", idBene);
				q.setParameter("idTipoInteresse", idTipoInteresse);
				q.setParameter("idSoggTitolare", idPersona);

				 q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                return l;
				
				/*
				TRelBeneAttoId id = new TRelBeneAttoId();
				id.setCodiKIdAtto(idAtto);
				id.setCodiKIdBene(idBene);
				id.setCodiKIdProc(idProcedimento);
				id.setCodiKIdSoggTit(idPersona);
				id.setCodiKIdTipoInt(idTipoInteresse);
				id.setCodiKIdUffOwner(idUfficioUtente);
				TRelBeneAtto relazione = new TRelBeneAtto();
				relazione.setId(id);
				session.delete(relazione);
				session.flush();
				return null;*/
			}
		});
	}

	public boolean esistonoAnimaliIdentici(BeneForm beneForm) {

		TBene bene = new TBene();
		mapperBean.map(beneForm, bene);
		TAnimali animale = new TAnimali();
		mapperBean.map(beneForm, animale);
		DetachedCriteria criteria = DetachedCriteria.forClass(TAnimali.class);
		criteria.add(Example.create(animale).excludeNone());
		List<TAnimali> risultati = getHibernateTemplate().findByCriteria(criteria);
		return ((risultati != null) && !risultati.isEmpty());
	}

	public boolean esistonoAziendeIdentiche(BeneForm beneForm) {

		TBene bene = new TBene();
		mapperBean.map(beneForm, bene);
		TAzienda azienda = new TAzienda();
		mapperBean.map(beneForm, azienda);
		DetachedCriteria criteria = DetachedCriteria.forClass(TAzienda.class);
		criteria.add(Example.create(azienda).excludeNone());
		List<TAzienda> risultati = getHibernateTemplate().findByCriteria(criteria);
		return ((risultati != null) && !risultati.isEmpty());
	}

	public boolean esistonoFinanziariIdentici(BeneForm beneForm) {

		TBene bene = new TBene();
		mapperBean.map(beneForm, bene);
		TBeneFin finanziario = new TBeneFin();
		mapperBean.map(beneForm, finanziario);
		DetachedCriteria criteria = DetachedCriteria.forClass(TBeneFin.class);
		criteria.add(Example.create(finanziario).excludeNone());
		List<TBeneFin> risultati = getHibernateTemplate().findByCriteria(criteria);
		return ((risultati != null) && !risultati.isEmpty());
	}

	public boolean esistonoImmobiliIdentici(BeneForm beneForm) {

		TBene bene = new TBene();
		mapperBean.map(beneForm, bene);
		TBeneImmobile immobile = new TBeneImmobile();
		mapperBean.map(beneForm, immobile);
		DetachedCriteria criteria = DetachedCriteria.forClass(TBeneImmobile.class);
		criteria.add(Example.create(immobile).excludeNone());
		List<TBeneImmobile> risultati = getHibernateTemplate().findByCriteria(criteria);
		return ((risultati != null) && !risultati.isEmpty());
	}

	public boolean esistonoVeicoliIdentici(BeneForm beneForm) {

		TBene bene = new TBene();
		mapperBean.map(beneForm, bene);
		TVeicolo veicolo = new TVeicolo();
		mapperBean.map(beneForm, veicolo);
		DetachedCriteria criteria = DetachedCriteria.forClass(TVeicolo.class);
		criteria.add(Example.create(veicolo).excludeNone());
		List<TVeicolo> risultati = getHibernateTemplate().findByCriteria(criteria);
		return ((risultati != null) && !risultati.isEmpty());
	}

	public void frazionamentoBene(final Integer idUfficioUtente, final String codUtente, final long idProcedimento, final long idBene, final String listaIdBeni, final long idAtto) {

		getHibernateTemplate().execute(new HibernateCallback<Object>() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_BENE_frazionamento_bene");
				q.setParameter("idUffOwn", idUfficioUtente);
				q.setParameter("idCodiceUtente", codUtente);
				q.setParameter("idProcedimento", idProcedimento);
				q.setParameter("idBene", idBene);
				q.setParameter("idAtto", idAtto);
				q.setParameter("listaIdBeni", listaIdBeni);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List<Map<?, ?>> l = q.list();
				return l;
			}
		});
	}

	
	public List<Map> getDescEnte(final String codiceAbi) {

		return getHibernateTemplate().execute(new HibernateCallback<List<Map>>() {

			public List<Map> doInHibernate(final Session session) throws HibernateException, SQLException {

				String qry = "SELECT DISTINCT OP_FIN.DESC_BANCA as desc_banca FROM t_an_oper_finanz op_fin WHERE OP_FIN.CODI_K_ID_ABI = :codiAbi " +
				" AND OP_FIN.DESC_BANCA <> :opFittizio";
				SQLQuery query = session.createSQLQuery(qry);
				query.setParameter("codiAbi", codiceAbi, Hibernate.STRING);
				query.setParameter("opFittizio", Constants.EnteFittizio.DESCRIZIONE, Hibernate.STRING);
				query.addScalar("desc_banca", Hibernate.STRING);
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List<Map> list = query.list();
				return list;
			}
		});
	}

	public Map<String, Object> getDettaglioBene(final HashMap<String, Object> hashDettaglio) {

		return (Map<String, Object>) getHibernateTemplate().execute(new HibernateCallback<Object>() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_BENE_dettaglio_bene");
				if (hashDettaglio.get("imm") != null) {
					TBeneImmobileId idImm = (TBeneImmobileId) hashDettaglio.get("imm");
					q.setParameter("vidUffOwn", idImm.getCodiKIdUffOwner());
					q.setParameter("vidBene", idImm.getCodiKIdBeneImm());
				} else
					if (hashDettaglio.get("veic") != null) {
						TVeicoloId idVeic = (TVeicoloId) hashDettaglio.get("veic");
						q.setParameter("vidUffOwn", idVeic.getCodiKIdUffOwner());
						q.setParameter("vidBene", idVeic.getCodiKIdVeicolo());
					}
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List<Map<?, ?>> l = q.list();
				if ((l != null) && (l.size() > 0)) {
					return l.get(0);
				}
				return null;
			}
		});
	}

	public Long getIdAttoByUfficioEProcedimento(Integer idUfficioUtente, long idProcedimento) {

		List<TRelAttoProc> lista = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelAttoProc.class);
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente.intValue()));
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		criteria.createAlias("TAtto", "atto");
		criteria.add(Restrictions.eq("atto.TTpTipoAtto.codiKIdTipoAtto", 9)); // è 9
																		// per
																		// volontà
																		// divina
		lista = getHibernateTemplate().findByCriteria(criteria);
		if (!Utility.isDefinito(lista)) {
			return null;
		}
		TRelAttoProc relazione = lista.get(0);
		Long risultato = relazione.getId().getCodiKIdAtto();
		return risultato;
	}
	
	public Long getIdAttoByUfficioEProcedimentoCameralini(Integer idUfficioUtente, long idProcedimento) {

		List<TRelAttoProc> lista = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelAttoProc.class);
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente.intValue()));
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		criteria.createAlias("TAtto", "atto");
		criteria.add(Restrictions.eq("atto.TTpTipoAtto.codiKIdTipoAtto", 5));
		lista = getHibernateTemplate().findByCriteria(criteria);
		if (!Utility.isDefinito(lista)) {
			return null;
		}
		TRelAttoProc relazione = lista.get(0);
		Long risultato = relazione.getId().getCodiKIdAtto();
		return risultato;
	}

	public TTpTipoInteresse getInteresseById(long idTipoInteresse) {

		TTpTipoInteresse risultato = getHibernateTemplate().get(TTpTipoInteresse.class, (int)idTipoInteresse);
		return risultato;
	}

	public List<TAnOperFinanz> getOperFinanz(String codiceAbi, String codiceCab) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TAnOperFinanz.class);
		criteria.add(Restrictions.eq("id.codiKIdAbi", codiceAbi));
		criteria.add(Restrictions.eq("id.codiKIdCab", codiceCab));
		List<TAnOperFinanz> opFin = getHibernateTemplate().findByCriteria(criteria);
		return opFin;
	}

	public TRelBeneAtto getRelazioneBeneAttoByAttoAndBene(long idBene, long idProcedimento, Integer idUfficioUtente, long idAtto, long idSoggetto, long tipoInt) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TRelBeneAtto.class);
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente.intValue()));
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		criteria.add(Restrictions.eq("id.codiKIdAtto", idAtto));
		criteria.add(Restrictions.eq("id.codiKIdBene", idBene));
		criteria.add(Restrictions.eq("id.codiKIdSoggTit", idSoggetto));
		if(tipoInt!=0)
		    criteria.add(Restrictions.eq("id.codiKIdTipoInt", (int)tipoInt));
		TRelBeneAtto risultato = DataAccessUtils.requiredUniqueResult((List<TRelBeneAtto>)getHibernateTemplate().findByCriteria(criteria));
		return risultato;
	}

	public TRelBeneAtto getRelazioneBeneProcedimentoById(BeneSoggettoForm form, long idProcedimento, Integer idUfficioUtente, long idAtto) {

		TRelBeneAttoId id = new TRelBeneAttoId();
		id.setCodiKIdProc(idProcedimento);
		id.setCodiKIdUffOwner(idUfficioUtente);
		id.setCodiKIdAtto(idAtto);
		id.setCodiKIdBene(form.getBene().getIdBene());
		id.setCodiKIdSoggTit(form.getSoggetto().getIdSoggetto());
		if (form.getIdTipoInteresse() != -1) {
			id.setCodiKIdTipoInt(form.getIdTipoInteresse());
		}
		TRelBeneAtto risultato = getHibernateTemplate().get(TRelBeneAtto.class, id);
		return risultato;
	}

	public List<TRelBeneAtto> getRelazioniByTipoInteresse(BeneSoggettoForm form, long idProcedimento, Integer idUfficioUtente, long idAtto) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TRelBeneAtto.class);
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente.intValue()));
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		if(idAtto>0){
			criteria.add(Restrictions.eq("id.codiKIdAtto", idAtto));
		}
		criteria.add(Restrictions.eq("id.codiKIdBene", form.getBene().getIdBene()));
		criteria.add(Restrictions.eq("id.codiKIdTipoInt", form.getIdTipoInteresse()));
		List<TRelBeneAtto> risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}
	
	public List<TRelProcBeneTit> getRelazioniProcedimentoBeneTitolare(BeneSoggettoForm form, long idProcedimento, Integer idUfficioUtente) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TRelProcBeneTit.class);
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente.intValue()));
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		criteria.add(Restrictions.eq("id.codiKIdBene", form.getBene().getIdBene()));
		criteria.add(Restrictions.eq("id.codiKIdTipoInt", form.getIdTipoInteresse()));
		List<TRelProcBeneTit> risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}
	
	public TRelProcBeneTit getRelazioniProcedimentoBeneTitolare(long idProcedimento,long idBene, int idUfficioUtente, int idTipoIntesse, long idSoggetto) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelProcBeneTit.class);
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente));
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		criteria.add(Restrictions.eq("id.codiKIdBene", idBene));
		if(idTipoIntesse>0){
			criteria.add(Restrictions.eq("id.codiKIdTipoInt", idTipoIntesse));
		}
		if(idSoggetto >0){
			criteria.add(Restrictions.eq("id.codiKIdSoggTit", idSoggetto));
		}
		List<TRelProcBeneTit> risultati = null; 
		risultati = getHibernateTemplate().findByCriteria(criteria);
		return CollectionUtils.isEmpty(risultati) ? null : risultati.get(0);
	}

	public boolean existsSequestroRegistrato(final long idProcedimento, final long idBene, final int idUfficioUtente) {
		return (Boolean) getHibernateTemplate().execute(new HibernateCallback<Object>() {
			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				Query q = session.createSQLQuery(QUERY_SEQUESTRO_REGISTRATO);
				q.setParameter("idUffOwner", idUfficioUtente);
				q.setParameter("idProc", idProcedimento);
				q.setParameter("idBene", idBene);
				return !CollectionUtils.isEmpty(q.list());
			}
		});
	}

	public TAnimali getTAnimaliById(long idBene, int idUffOwn) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TAnimali.class);
		criteria.add(Restrictions.naturalId().set("id.codiKIdAnimale", idBene).set("id.codiKIdUffOwner", idUffOwn));
		List<TAnimali> risultati = getHibernateTemplate().findByCriteria(criteria);
		TAnimali risultato = DataAccessUtils.requiredSingleResult(risultati);
		return risultato;
	}

	public TAzienda getTAziendaById(long idBene, int idUffOwn) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TAzienda.class);
		criteria.add(Restrictions.naturalId().set("id.codiKIdAzienda", idBene).set("id.codiKIdUffOwner", idUffOwn));
		List<TAzienda> risultati = getHibernateTemplate().findByCriteria(criteria);
		TAzienda risultato = DataAccessUtils.requiredSingleResult(risultati);
		return risultato;
	}

	public TBene getTBeneById(long idBene, int idUffOwn) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TBene.class);
		criteria.add(Restrictions.naturalId().set("id.codiKIdBene", idBene).set("id.codiKIdUffOwner", idUffOwn));
		List<TBene> risultati = getHibernateTemplate().findByCriteria(criteria);
		TBene risultato = new TBene();
		if (risultati != null && risultati.size() > 0)
			risultato = DataAccessUtils.requiredSingleResult(risultati);
		return risultato;
	}

	public TBeneFin getTBeneFinanziarioById(long idBene, int idUffOwn) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TBeneFin.class);
		criteria.add(Restrictions.naturalId().set("id.codiKIdBeneFin", idBene).set("id.codiKIdUffOwner", idUffOwn));
		List<TBeneFin> risultati = getHibernateTemplate().findByCriteria(criteria);
		TBeneFin risultato = DataAccessUtils.requiredSingleResult(risultati);
		return risultato;
	}
	
	public TContante getTContanteById(long idBene, int idUffOwn) {

	    DetachedCriteria criteria = DetachedCriteria.forClass(TContante.class);
        criteria.add(Restrictions.naturalId().set("id.codiKIdContante", idBene).set("id.codiKIdUffOwner", idUffOwn));
        List<TContante> risultati = getHibernateTemplate().findByCriteria(criteria);
        TContante risultato = DataAccessUtils.requiredSingleResult(risultati);
        return risultato;
    }
	
	public TDenaro getTDenaroById(long idBene, int idUffOwn){
	    
	    DetachedCriteria criteria = DetachedCriteria.forClass(TDenaro.class);
        criteria.add(Restrictions.naturalId().set("id.codiKIdDenaro", idBene).set("id.codiKIdUffOwner", idUffOwn));
        List<TDenaro> risultati = getHibernateTemplate().findByCriteria(criteria);
        TDenaro risultato = DataAccessUtils.requiredSingleResult(risultati);
        return risultato;
	}
	
	
	public TBeneImmat getTBeneImmaterialeById(long idBene, int idUffOwn) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TBeneImmat.class);
		criteria.add(Restrictions.naturalId().set("id.codiKIdBeneImm", idBene).set("id.codiKIdUffOwner", idUffOwn));
		List<TBeneImmat> risultati = getHibernateTemplate().findByCriteria(criteria);
		TBeneImmat risultato = DataAccessUtils.requiredSingleResult(risultati);
		return risultato;
	}

	public TBeneImmobile getTBeneImmobileById(long idBene, int idUffOwn) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TBeneImmobile.class);
		criteria.add(Restrictions.naturalId().set("id.codiKIdBeneImm", idBene).set("id.codiKIdUffOwner", idUffOwn));
		List<TBeneImmobile> risultati = getHibernateTemplate().findByCriteria(criteria);
		TBeneImmobile risultato = DataAccessUtils.requiredSingleResult(risultati);
		return risultato;
	}

	public TCollezioni getTCollezioniById(long idBene, int idUffOwn) {

		TCollezioniId colId = new TCollezioniId();
		colId.setCodiKIdColl(idBene);
		colId.setCodiKIdUffOwner(idUffOwn);
		TCollezioni risultato = getHibernateTemplate().get(TCollezioni.class, colId);
		return risultato;
	}

	public TRelBeneAtto getTipoInteressePerSoggettoInteressato(long idProcedimento, long idAtto, int idUfficioUtente, long idBene, long idSoggettoTitolare) {

		List<TRelBeneAtto> relazioni = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelBeneAtto.class);
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		criteria.add(Restrictions.eq("id.codiKIdAtto", idAtto));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente));
		criteria.add(Restrictions.eq("id.codiKIdBene", idBene));
		criteria.add(Restrictions.eq("id.codiKIdSoggTit", idSoggettoTitolare));
		relazioni = getHibernateTemplate().findByCriteria(criteria);
		TRelBeneAtto risultato = relazioni.get(0);
		return risultato;
	}

	public TAnNormative getTAnNormativeById(long idNormativa) {

		TAnNormative risultato = getHibernateTemplate().get(TAnNormative.class, (int)idNormativa);
		return risultato;
	}

	public TRelBeneAtto getTRelBeneAttoById(Long idBene, long idPersona, Integer idUfficioUtente, long idProcedimento, long idAtto, long idTipoInteresse) {

		TRelBeneAttoId id = new TRelBeneAttoId();
		id.setCodiKIdAtto(idAtto);
		id.setCodiKIdBene(idBene);
		id.setCodiKIdProc(idProcedimento);
		id.setCodiKIdSoggTit(idPersona);
		id.setCodiKIdTipoInt((int)idTipoInteresse);
		id.setCodiKIdUffOwner(idUfficioUtente);
		TRelBeneAtto risultato = getHibernateTemplate().get(TRelBeneAtto.class, id);
		/**TODO
		risultato.setTTpTipoPrevenzione(risultato.getTTpTipoPrevenzione());
		*/
		return risultato;
	}

	public TRelProcBene getTRelProcBeneById(long idProc, long idBene, int idUffOwn) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TRelProcBene.class);
		criteria.add(Restrictions.naturalId().set("id.codiKIdBene", idBene).set("id.codiKIdUffOwner", idUffOwn).set("id.codiKIdProc", idProc));
		List<TRelProcBene> risultati = getHibernateTemplate().findByCriteria(criteria);
		TRelProcBene risultato = new TRelProcBene();
		if ((risultati != null) && !risultati.isEmpty()) {
			risultato = DataAccessUtils.requiredSingleResult(risultati);
		}
		return risultato;
	}

	public TTpTipoMisura getTTpTipoMisuraById(long idTipoMisura) {

		TTpTipoMisura risultato = getHibernateTemplate().get(TTpTipoMisura.class, (int)idTipoMisura);
		return risultato;
	}

	public TTpTipoPrevenzione getTTpTipoPrevenzioneById(long idTipoPrevenzione) {

		TTpTipoPrevenzione risultato = getHibernateTemplate().get(TTpTipoPrevenzione.class, idTipoPrevenzione);
		return risultato;
	}

	public TVeicolo getTVeicoloById(long idBene, int idUffOwn) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TVeicolo.class);
		criteria.add(Restrictions.naturalId().set("id.codiKIdVeicolo", idBene).set("id.codiKIdUffOwner", idUffOwn));
		List<TVeicolo> risultati = getHibernateTemplate().findByCriteria(criteria);
		TVeicolo risultato = DataAccessUtils.requiredSingleResult(risultati);
		return risultato;
	}

	
	public List<Map> inserisciAzienda(final long vidUffOwn, final String vcodUtente, final long vidprocedimento, final long vidstatocons, final TBene bene, final TAzienda azienda, final String vdescComune, final String vtipoStrada, final String vdesccategoria) {

	    return (List<Map>) getHibernateTemplate().execute(new HibernateCallback<List<Map>>() {

            public List<Map> doInHibernate(final Session session) throws HibernateException, SQLException {

				long vidstato = 0;
				long vidcomune = 0;
				long vidtipostrada = 0;
				String vdescindirizzo = null;
				String vnumcivico = null;
				String vnumcap = null;
				String vdesccomuneestero = null;
				String vragionesociale = null;
				String vcodicefiscale = null;
				String vnumerociaa = null;
				String vdescrappsociale = null;
				String vdescamministratore = null;
				String vpartitaiva = "";
				BigDecimal vcapitalesociale = new BigDecimal(0);
				long vnumdipendenti = 0;
				long vsettoreattivita = 0;
				
				if ((bene != null) && (bene.getTTpTipoBene() != null) && (bene.getTTpTipoBene().getCodiKIdTipoBene() == 5L)) {
					if ((bene.getTAnStato() != null) && (bene.getTAnStato().getCodiKIdStato() > 0)) {
						vidstato = bene.getTAnStato().getCodiKIdStato();
					}
					if ((bene.getTAnComuni() != null) && (bene.getTAnComuni().getCodiKIdComune() > 0)) {
						vidcomune = bene.getTAnComuni().getCodiKIdComune();
					}
					if ((bene.getTTpTipoStrada() != null) && (bene.getTTpTipoStrada().getCodiKIdTipoStrada() > 0)) {
						vidtipostrada = bene.getTTpTipoStrada().getCodiKIdTipoStrada();
					}
					vdesccomuneestero = bene.getDescComuneEstero();
					vdescindirizzo = bene.getDescIndirizzo();
					vnumcivico = bene.getDescCivico();
					vnumcap = bene.getDescCap();
					vnumerociaa = azienda.getDescCciaa();
					vdescrappsociale = azienda.getDescRappresSoc();
					if ((azienda.getNumeDipendenti() != null) && (azienda.getNumeDipendenti() > 0)) {
						vnumdipendenti = azienda.getNumeDipendenti();
					}
					if (azienda.getDescPIva() != null) {
						vpartitaiva = azienda.getDescPIva();
					}
					vcodicefiscale = azienda.getDescCodFisc();
					vdescamministratore = azienda.getDescAmministratore();
					vcapitalesociale = azienda.getImpoCapitaleSoc();
					vragionesociale = azienda.getDescRagioneSoc();
					if ((azienda.getTTpSettoreAtt() != null) && (azienda.getTTpSettoreAtt().getCodiKIdSettoreAtt() > 0)) {
						vsettoreattivita = azienda.getTTpSettoreAtt().getCodiKIdSettoreAtt();
					}
				}
				Query q = session.getNamedQuery("PG_BENE_inserisciAzienda");
				if( bene.getId() != null && bene.getId().getCodiKIdBene() > 0 )
                    q.setParameter("vidBene", bene.getId().getCodiKIdBene(), Hibernate.LONG);
                else
                    q.setParameter("vidBene", null, Hibernate.LONG);
				q.setParameter("vidUffOwn", vidUffOwn);
				q.setParameter("vdescUtente", vcodUtente);
				q.setParameter("vtipo", bene.getTTpTipoBene().getCodiKIdTipoBene());
				if ((bene.getTTpCatBene() != null) && (bene.getTTpCatBene().getCodiKIdCatBene() > 0)) {
					q.setParameter("vcategoria", bene.getTTpCatBene().getCodiKIdCatBene());
				} else {
					q.setParameter("vcategoria", null, Hibernate.LONG);
				}
				if ((bene.getTTpSottocatBene() != null) && (bene.getTTpSottocatBene().getCodiKIdSottocat() > 0)) {
					q.setParameter("vsottocategoria", bene.getTTpSottocatBene().getCodiKIdSottocat());
				} else {
					q.setParameter("vsottocategoria", null, Hibernate.LONG);
				}
				if (vidstato > 0) {
					q.setParameter("vidstato", vidstato);
				} else {
					q.setParameter("vidstato", null, Hibernate.LONG);
				}
				if (vidcomune > 0) {
					q.setParameter("vidcomune", vidcomune);
				} else {
					q.setParameter("vidcomune", null, Hibernate.LONG);
				}
				if (vidtipostrada > 0) {
					q.setParameter("vidtipostrada", vidtipostrada);
				} else {
					q.setParameter("vidtipostrada", null, Hibernate.LONG);
				}
				q.setParameter("vdescindirizzo", vdescindirizzo);
				q.setParameter("vnumcivico", vnumcivico);
				q.setParameter("vnumcap", vnumcap);
				q.setParameter("vdesccomuneestero", vdesccomuneestero);
				q.setParameter("vnote", bene.getDescNote());
				q.setParameter("vflagcostazienda", bene.getFlgCostAzienda());
				q.setParameter("vflaggravame", bene.getFlgGravame());
				q.setParameter("vragionesociale", vragionesociale);
				q.setParameter("vcodicefiscale", vcodicefiscale);
				q.setParameter("vnumerociaa", vnumerociaa);
				q.setParameter("vdescrappsociale", vdescrappsociale);
				q.setParameter("vdescamministratore", vdescamministratore);
				if (vpartitaiva != null) {
					q.setParameter("vpartitaiva", vpartitaiva);
				} else {
					q.setParameter("vpartitaiva", null, Hibernate.STRING);
				}
				if (vcapitalesociale != null) {
					q.setParameter("vcapitalesociale", vcapitalesociale, Hibernate.BIG_DECIMAL);
				} else {
					q.setParameter("vcapitalesociale", null, Hibernate.BIG_DECIMAL);
				}
				if (vnumdipendenti > 0) {
					q.setParameter("vnumdipendenti", vnumdipendenti);
				} else {
					q.setParameter("vnumdipendenti", null, Hibernate.LONG);
				}
				if (vsettoreattivita > 0) {
					q.setParameter("vsettoreattivita", vsettoreattivita);
				} else {
					q.setParameter("vsettoreattivita", null, Hibernate.LONG);
				}
				if (vidstatocons > 0) {
					q.setParameter("vidstatocons", vidstatocons);
				} else {
					q.setParameter("vidstatocons", null, Hibernate.LONG);
				}
				q.setParameter("vidprocedimento", vidprocedimento);
				q.setParameter("vdescComune", vdescComune);
				q.setParameter("vtipoStrada", vtipoStrada);
				q.setParameter("vdesccategoria", vdesccategoria);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List<Map> l = q.list();
				return l;
			}
		});
	}
	
	
	public void modificaAzienda(final long vidUffOwn, final String vcodUtente, final long vidprocedimento, final long vidstatobene, final TBene bene, final TAzienda azienda, final String vdescComune, final String vtipoStrada, final String vdesccategoria) {
		getHibernateTemplate().execute(new HibernateCallback<Void>() {
			
			public Void doInHibernate(Session session) throws HibernateException, SQLException {
				long vidstato = 0;
				long vidcomune = 0;
				long vidtipostrada = 0;
				String vdescindirizzo = null;
				String vnumcivico = null;
				String vnumcap = null;
				String vdesccomuneestero = null;
				String vragionesociale = null;
				String vcodicefiscale = null;
				String vnumerociaa = null;
				String vdescrappsociale = null;
				String vdescamministratore = null;
				String vpartitaiva = "";
				BigDecimal vcapitalesociale = new BigDecimal(0);
				long vnumdipendenti = 0;
				long vsettoreattivita = 0;
				
				if ((bene != null) && (bene.getTTpTipoBene() != null) && (bene.getTTpTipoBene().getCodiKIdTipoBene() == 5L)) {
					if ((bene.getTAnStato() != null) && (bene.getTAnStato().getCodiKIdStato() > 0)) {
						vidstato = bene.getTAnStato().getCodiKIdStato();
					}
					if ((bene.getTAnComuni() != null) && (bene.getTAnComuni().getCodiKIdComune() > 0)) {
						vidcomune = bene.getTAnComuni().getCodiKIdComune();
					}
					if ((bene.getTTpTipoStrada() != null) && (bene.getTTpTipoStrada().getCodiKIdTipoStrada() > 0)) {
						vidtipostrada = bene.getTTpTipoStrada().getCodiKIdTipoStrada();
					}
					vdesccomuneestero = bene.getDescComuneEstero();
					vdescindirizzo = bene.getDescIndirizzo();
					vnumcivico = bene.getDescCivico();
					vnumcap = bene.getDescCap();
					vnumerociaa = azienda.getDescCciaa();
					vdescrappsociale = azienda.getDescRappresSoc();
					if ((azienda.getNumeDipendenti() != null) && (azienda.getNumeDipendenti() > 0)) {
						vnumdipendenti = azienda.getNumeDipendenti();
					}
					if (azienda.getDescPIva() != null) {
						vpartitaiva = azienda.getDescPIva();
					}
					vcodicefiscale = azienda.getDescCodFisc();
					vdescamministratore = azienda.getDescAmministratore();
					vcapitalesociale = azienda.getImpoCapitaleSoc();
					vragionesociale = azienda.getDescRagioneSoc();
					if ((azienda.getTTpSettoreAtt() != null) && (azienda.getTTpSettoreAtt().getCodiKIdSettoreAtt() > 0)) {
						vsettoreattivita = azienda.getTTpSettoreAtt().getCodiKIdSettoreAtt();
					}
				}
				Query q = session.getNamedQuery("PG_BENE_modificaAzienda");
				if( bene.getId() != null && bene.getId().getCodiKIdBene() > 0 )
                    q.setParameter("vidBene", bene.getId().getCodiKIdBene(), Hibernate.LONG);
                else
                    q.setParameter("vidBene", null, Hibernate.LONG);
				q.setParameter("vidUffOwn", vidUffOwn);
				q.setParameter("vdescUtente", vcodUtente);
				q.setParameter("vtipo", bene.getTTpTipoBene().getCodiKIdTipoBene());
				if ((bene.getTTpCatBene() != null) && (bene.getTTpCatBene().getCodiKIdCatBene() > 0)) {
					q.setParameter("vcategoria", bene.getTTpCatBene().getCodiKIdCatBene());
				} else {
					q.setParameter("vcategoria", null, Hibernate.LONG);
				}
				if ((bene.getTTpSottocatBene() != null) && (bene.getTTpSottocatBene().getCodiKIdSottocat() > 0)) {
					q.setParameter("vsottocategoria", bene.getTTpSottocatBene().getCodiKIdSottocat());
				} else {
					q.setParameter("vsottocategoria", null, Hibernate.LONG);
				}
				if (vidstato > 0) {
					q.setParameter("vidstato", vidstato);
				} else {
					q.setParameter("vidstato", null, Hibernate.LONG);
				}
				if (vidcomune > 0) {
					q.setParameter("vidcomune", vidcomune);
				} else {
					q.setParameter("vidcomune", null, Hibernate.LONG);
				}
				if (vidtipostrada > 0) {
					q.setParameter("vidtipostrada", vidtipostrada);
				} else {
					q.setParameter("vidtipostrada", null, Hibernate.LONG);
				}
				q.setParameter("vdescindirizzo", vdescindirizzo);
				q.setParameter("vnumcivico", vnumcivico);
				q.setParameter("vnumcap", vnumcap);
				q.setParameter("vdesccomuneestero", vdesccomuneestero);
				q.setParameter("vnote", bene.getDescNote());
				q.setParameter("vflagcostazienda", bene.getFlgCostAzienda());
				q.setParameter("vflaggravame", bene.getFlgGravame());
				q.setParameter("vragionesociale", vragionesociale);
				q.setParameter("vcodicefiscale", vcodicefiscale);
				q.setParameter("vnumerociaa", vnumerociaa);
				q.setParameter("vdescrappsociale", vdescrappsociale);
				q.setParameter("vdescamministratore", vdescamministratore);
				if (vpartitaiva != null) {
					q.setParameter("vpartitaiva", vpartitaiva);
				} else {
					q.setParameter("vpartitaiva", null, Hibernate.STRING);
				}
				if (vcapitalesociale != null) {
					q.setParameter("vcapitalesociale", vcapitalesociale, Hibernate.BIG_DECIMAL);
				} else {
					q.setParameter("vcapitalesociale", null, Hibernate.BIG_DECIMAL);
				}
				if (vnumdipendenti > 0) {
					q.setParameter("vnumdipendenti", vnumdipendenti);
				} else {
					q.setParameter("vnumdipendenti", null, Hibernate.LONG);
				}
				if (vsettoreattivita > 0) {
					q.setParameter("vsettoreattivita", vsettoreattivita);
				} else {
					q.setParameter("vsettoreattivita", null, Hibernate.LONG);
				}
				if (vidstatobene > 0) {
					q.setParameter("vidstatocons", vidstatobene);
				} else {
					q.setParameter("vidstatocons", null, Hibernate.LONG);
				}
				q.setParameter("vidprocedimento", vidprocedimento);
				q.setParameter("vdescComune", vdescComune);
				q.setParameter("vtipoStrada", vtipoStrada);
				q.setParameter("vdesccategoria", vdesccategoria);
				
				q.list();
				return null;
			}
		});
	}
	
	
	public List<Map> inserisciDenaro_Contante(final long vidUffOwn, final String vcodUtente, final long vidprocedimento, final long vidstatobene, final TBene bene, final TDenaro denaro, final TContante contante) {

        return (List<Map>) getHibernateTemplate().execute(new HibernateCallback<List<Map>>() {

            public List<Map> doInHibernate(final Session session) throws HibernateException, SQLException {

                String sottocategoria = "";
                if ((bene.getTTpSottocatBene() != null) && (bene.getTTpSottocatBene().getCodiKIdSottocat() > 0)) {
                    sottocategoria = String.valueOf(bene.getTTpSottocatBene().getCodiKIdSottocat());
                }
                // Per qualsiasi tipo di bene Denaro
                String codAbi = null;
                String descEnte = null;
                String codCab = null;
                String codIban = null;
                String codSwift = null;
                String flgVersamento = null;
                String numEnte = "";
                BigDecimal valutaEur = null;
                String tipoVal = null;
                BigDecimal valutaEst = null;
                // Solo per Bene Denaro non Contante
                String descIndirizzo = null;
                String direttEnte = null;
                // Solo per Bene Denaro Contante
                String descIntestatario = null;
                long numeroModTrib = 0;
                String bollettino = null;
                
                if(!sottocategoria.equalsIgnoreCase("35")){
                    // ************************** INIZIO VARIABILI  BENE DENARO
                    // *********************************
                    descEnte = denaro.getDescEnteDepos();
                    descIndirizzo = bene.getDescIndirizzo();
                    direttEnte = denaro.getDescDirettore();
                    if (denaro.getDescEnteDepos() != null)
                        numEnte = denaro.getDescIdentEnte();
                    valutaEst = denaro.getImpoValuta();
                    valutaEur = denaro.getImpoEuro();
                    if ((denaro.getTTpValuta() != null) && (denaro.getTTpValuta().getCodiKIdValuta() != null &&! denaro.getTTpValuta().getCodiKIdValuta().equals("-1"))) {
                        tipoVal = denaro.getTTpValuta().getCodiKIdValuta();
                    }
                    if (denaro.getTAnOperFinanz() != null && (denaro.getTAnOperFinanz().getId() != null)) {
                        TAnOperFinanzId id = denaro.getTAnOperFinanz().getId();
                        codAbi = id.getCodiKIdAbi();
                        codCab = id.getCodiKIdCab();
                    }
                    if (denaro.getDescIban() != null){
                        codIban = denaro.getDescIban();
                    }
                    if(denaro.getDescBicSwift()!=null){
                    	codSwift=denaro.getDescBicSwift();
                    }
                    // ************************** FINE VARIABILI BENE DENARO
                    // *********************************
                } 
                else{
                    // ************************** INIZIO VARIABILI BENE DENARO CONTANTE
                    // *********************************
                    descEnte = contante.getDescEnteDepos();
                    //descIndirizzo non c'è
                    //direttEnte non c'è
                    descIndirizzo = contante.getDescIntestatario();
                    if (contante.getDescIdentEnte() != null)
                        numEnte = contante.getDescIdentEnte();
                    valutaEst = contante.getImpoValuta();
                    valutaEur = contante.getImpoEuro();
                    if ((contante.getTTpValuta() != null) && (!contante.getTTpValuta().getCodiKIdValuta().equals("-1"))) {
                        tipoVal = contante.getTTpValuta().getCodiKIdValuta();
                    }
                    if (contante.getTAnOperFinanz() != null && (contante.getTAnOperFinanz().getId() != null)) {
                        TAnOperFinanzId id = contante.getTAnOperFinanz().getId();
                        codAbi = id.getCodiKIdAbi();
                        codCab = id.getCodiKIdCab();
                    }
                    if (contante.getDescIban() != null){
                        codIban = contante.getDescIban();
                    }
                    if (contante.getDescBicSwift()!= null){
                        codSwift = contante.getDescBicSwift();
                    }
                    if (contante.getFlgVersamento()!= null){
                    	flgVersamento = contante.getFlgVersamento();
                    }
                    if(contante.getDescIntestatario()!=null){
                        descIntestatario = contante.getDescIntestatario();
                    }
                    if(contante.getNumeIdentModTrib()!=null){
                        numeroModTrib = contante.getNumeIdentModTrib();
                    }
                    if(contante.getDescBollettino()!=null){
                        bollettino = contante.getDescBollettino();
                    }
                    // ************************** FINE VARIABILI BENE DENARO CONTANTE
                    // *********************************
                }
                Query q = session.getNamedQuery("PG_BENE_inserisciDenaro_Contante");
                // *************************** PARAMETRI BENE
                // *************************************
                if( bene.getId() != null && bene.getId().getCodiKIdBene() > 0 )
                    q.setParameter("vidBene", bene.getId().getCodiKIdBene(), Hibernate.LONG);
                else
                    q.setParameter("vidBene", null, Hibernate.LONG);
                q.setParameter("vidUffOwn", vidUffOwn);
                q.setParameter("vdescUtente", vcodUtente);
                q.setParameter("vtipo", bene.getTTpTipoBene().getCodiKIdTipoBene());
                if ((bene.getTTpCatBene() != null) && (bene.getTTpCatBene().getCodiKIdCatBene() > 0)) {
                    q.setParameter("vcategoria", bene.getTTpCatBene().getCodiKIdCatBene(), Hibernate.INTEGER);
                } else {
                    q.setParameter("vcategoria", null, Hibernate.INTEGER);
                }
                if ((bene.getTTpSottocatBene() != null) && (bene.getTTpSottocatBene().getCodiKIdSottocat() > 0)) {
                    q.setParameter("vsottocategoria", bene.getTTpSottocatBene().getCodiKIdSottocat(), Hibernate.INTEGER);
                } else {
                    q.setParameter("vsottocategoria", null, Hibernate.INTEGER);
                }
                if (vidstatobene > 0) {
                    q.setParameter("vidstatocons", vidstatobene, Hibernate.LONG);
                } else {
                    q.setParameter("vidstatocons", null, Hibernate.LONG);
                }
                if (vidprocedimento > 0) {
                    q.setParameter("vidprocedimento", vidprocedimento, Hibernate.LONG);
                } else {
                    q.setParameter("vidprocedimento", null, Hibernate.LONG);
                }
                if ((bene.getTAnStato() != null) && (bene.getTAnStato().getCodiKIdStato() > 0)) {
                    q.setParameter("vidstato", bene.getTAnStato().getCodiKIdStato(), Hibernate.INTEGER);
                } else {
                    q.setParameter("vidstato", null, Hibernate.INTEGER);
                }
                if ((bene.getTAnComuni() != null) && (bene.getTAnComuni().getCodiKIdComune() > 0)) {
                    q.setParameter("vidcomune", bene.getTAnComuni().getCodiKIdComune(), Hibernate.INTEGER);
                } else {
                    q.setParameter("vidcomune", null, Hibernate.INTEGER);
                }
                if ((bene.getTTpTipoStrada() != null) && (bene.getTTpTipoStrada().getCodiKIdTipoStrada() > 0)) {
                    q.setParameter("vidtipostrada", bene.getTTpTipoStrada().getCodiKIdTipoStrada(), Hibernate.INTEGER);
                } else {
                    q.setParameter("vidtipostrada", null, Hibernate.INTEGER);
                }
                q.setParameter("vdescindirizzo", bene.getDescIndirizzo());
                q.setParameter("vnumcivico", bene.getDescCivico());
                q.setParameter("vnumcap", bene.getDescCap());
                q.setParameter("vdesccomuneestero", bene.getDescComuneEstero());
                q.setParameter("vflagcostazienda", bene.getFlgCostAzienda());
                q.setParameter("vnote", bene.getDescNote());
                q.setParameter("vflaggravame", bene.getFlgGravame());
                // *************************** PARAMETRI BENE
                // *************************************
                // *************************** PARAMETRI BENE DENARO - CONTANTE
                // *************************************
                q.setParameter("vdescente", descEnte);
                q.setParameter("vdescindirizzoDen", descIndirizzo);
                q.setParameter("vdirettEnte", direttEnte);
                if (numEnte != null) {
                    q.setParameter("vnumident", numEnte, Hibernate.STRING);
                } else {
                    q.setParameter("vnumident", null, Hibernate.STRING);
                }
                if (valutaEst != null) {
                    q.setParameter("vvalutaest", valutaEst, Hibernate.BIG_DECIMAL);
                } else {
                    q.setParameter("vvalutaest", null, Hibernate.BIG_DECIMAL);
                }
                q.setParameter("vtipoval", tipoVal);
                if (valutaEur != null) {
                    q.setParameter("vvalutaeur", valutaEur, Hibernate.BIG_DECIMAL);
                } else {
                    q.setParameter("vvalutaeur", null, Hibernate.BIG_DECIMAL);
                }
                q.setParameter("vcodabi", codAbi);
                if (codCab == null || codCab.equals("0"))
                    q.setParameter("vcodcab", null, Hibernate.STRING);
                else q.setParameter("vcodcab", codCab);
                q.setParameter("vcodiban", codIban);
                q.setParameter("vcodswift", codSwift);
                if (flgVersamento != null && !flgVersamento.equals("-1")) {
                    q.setParameter("flgVersamento", flgVersamento, Hibernate.STRING);
                } else {
                    q.setParameter("flgVersamento", null, Hibernate.STRING);
                }
                q.setParameter("vintestatario", descIntestatario);
                if (numeroModTrib > 0) {
                    q.setParameter("vnummodtrib", numeroModTrib, Hibernate.LONG);
                } else {
                    q.setParameter("vnummodtrib", null, Hibernate.LONG);
                }
                q.setParameter("vbollettino", bollettino);
                // *************************** PARAMETRI BENE DENARO
                // *************************************
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List<Map> l = q.list();
                return l;
            }
        });
    }
	
	
	public List<Map> inserisciFinanziario(final long vidUffOwn, final String vcodUtente, final long vidprocedimento, final long vidstatobene, final TBene bene, final TBeneFin finanziario) {

		return (List<Map>) getHibernateTemplate().execute(new HibernateCallback<List<Map>>() {

			public List<Map> doInHibernate(final Session session) throws HibernateException, SQLException {

				// ************************** VARIABILI BENE FIN
				// *********************************
				String descEnte = null;
				String descIndirizzo = null;
				String direttEnte = null;
				String numIdent = null;
				BigDecimal valutaEst = null;
				BigDecimal valutaEur = null;
				String tipoVal = null;
				String codAbi = null;
				String codCab = null;
				String codIban = null;
				String codSwift=null;
				descEnte = finanziario.getDescEnteCreditizio();
				descIndirizzo = bene.getDescIndirizzo();
				direttEnte = finanziario.getDescDirettoreEnte();
				if (finanziario.getDescRegistrazione() != null)
					numIdent = finanziario.getDescRegistrazione();
				valutaEst = finanziario.getImpoValuta();
				valutaEur = finanziario.getImpoEuro();
				if ((finanziario.getTTpValuta() != null) && (finanziario.getTTpValuta().getCodiKIdValuta() != null &&! finanziario.getTTpValuta().getCodiKIdValuta().equals("-1"))) {
					tipoVal = finanziario.getTTpValuta().getCodiKIdValuta();
				}
				if (finanziario.getTAnOperFinanz() != null && (finanziario.getTAnOperFinanz().getId() != null)) {
					TAnOperFinanzId id = finanziario.getTAnOperFinanz().getId();
					codAbi = id.getCodiKIdAbi();
					codCab = id.getCodiKIdCab();
				}
				if (finanziario.getDescIban() != null){
					codIban = finanziario.getDescIban();
				}
				if(finanziario.getDescBicSwift()!=null){
					codSwift=finanziario.getDescBicSwift();
				}
				// ************************** VARIABILI BENE FIN
				// *********************************
				Query q = session.getNamedQuery("PG_BENE_inserisciFinanziario");
				// *************************** PARAMETRI BENE
				// *************************************
				if( bene.getId() != null && bene.getId().getCodiKIdBene() > 0 )
                    q.setParameter("vidBene", bene.getId().getCodiKIdBene(), Hibernate.LONG);
                else
                    q.setParameter("vidBene", null, Hibernate.LONG);
				q.setParameter("vidUffOwn", vidUffOwn);
				q.setParameter("vdescUtente", vcodUtente);
				q.setParameter("vtipo", bene.getTTpTipoBene().getCodiKIdTipoBene());
				if ((bene.getTTpCatBene() != null) && (bene.getTTpCatBene().getCodiKIdCatBene() > 0)) {
					q.setParameter("vcategoria", bene.getTTpCatBene().getCodiKIdCatBene(), Hibernate.INTEGER);
				} else {
					q.setParameter("vcategoria", null, Hibernate.INTEGER);
				}
				if ((bene.getTTpSottocatBene() != null) && (bene.getTTpSottocatBene().getCodiKIdSottocat() > 0)) {
					q.setParameter("vsottocategoria", bene.getTTpSottocatBene().getCodiKIdSottocat(), Hibernate.INTEGER);
				} else {
					q.setParameter("vsottocategoria", null, Hibernate.INTEGER);
				}
				if (vidstatobene > 0) {
					q.setParameter("vidstatocons", vidstatobene, Hibernate.LONG);
				} else {
					q.setParameter("vidstatocons", null, Hibernate.LONG);
				}
				if (vidprocedimento > 0) {
					q.setParameter("vidprocedimento", vidprocedimento, Hibernate.LONG);
				} else {
					q.setParameter("vidprocedimento", null, Hibernate.LONG);
				}
				if ((bene.getTAnStato() != null) && (bene.getTAnStato().getCodiKIdStato() > 0)) {
					q.setParameter("vidstato", bene.getTAnStato().getCodiKIdStato(), Hibernate.INTEGER);
				} else {
					q.setParameter("vidstato", null, Hibernate.INTEGER);
				}
				if ((bene.getTAnComuni() != null) && (bene.getTAnComuni().getCodiKIdComune() > 0)) {
					q.setParameter("vidcomune", bene.getTAnComuni().getCodiKIdComune(), Hibernate.INTEGER);
				} else {
					q.setParameter("vidcomune", null, Hibernate.INTEGER);
				}
				if ((bene.getTTpTipoStrada() != null) && (bene.getTTpTipoStrada().getCodiKIdTipoStrada() > 0)) {
					q.setParameter("vidtipostrada", bene.getTTpTipoStrada().getCodiKIdTipoStrada(), Hibernate.INTEGER);
				} else {
					q.setParameter("vidtipostrada", null, Hibernate.INTEGER);
				}
				q.setParameter("vdescindirizzo", bene.getDescIndirizzo());
				q.setParameter("vnumcivico", bene.getDescCivico());
				q.setParameter("vnumcap", bene.getDescCap());
				q.setParameter("vdesccomuneestero", bene.getDescComuneEstero());
				q.setParameter("vflagcostazienda", bene.getFlgCostAzienda());
				q.setParameter("vnote", bene.getDescNote());
				q.setParameter("vflaggravame", bene.getFlgGravame());
				// *************************** PARAMETRI BENE
				// *************************************
				// *************************** PARAMETRI BENE_FIN
				// *************************************
				q.setParameter("vdescente", descEnte);
				q.setParameter("vdescindirizzoFin", descIndirizzo);
				q.setParameter("vdirettEnte", direttEnte);
				if (numIdent != null) {
					q.setParameter("vnumident", numIdent, Hibernate.STRING);
				} else {
					q.setParameter("vnumident", null, Hibernate.STRING);
				}
				if (valutaEst != null) {
					q.setParameter("vvalutaest", valutaEst, Hibernate.BIG_DECIMAL);
				} else {
					q.setParameter("vvalutaest", null, Hibernate.BIG_DECIMAL);
				}
				q.setParameter("vtipoval", tipoVal);
				if (valutaEur != null) {
					q.setParameter("vvalutaeur", valutaEur, Hibernate.BIG_DECIMAL);
				} else {
					q.setParameter("vvalutaeur", null, Hibernate.BIG_DECIMAL);
				}
				q.setParameter("vcodabi", codAbi);
				if (codCab == null || codCab.equals("0"))
					q.setParameter("vcodcab", null, Hibernate.STRING);
				else q.setParameter("vcodcab", codCab);
				q.setParameter("vcodiban", codIban);
				q.setParameter("vcodswift", codSwift);
				// *************************** PARAMETRI BENE_FIN
				// *************************************
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List<Map> l = q.list();
				return l;
			}
		});
	}

	
	public List<Map> inserisciImmobile(final long vidUffOwn, final String vcodUtente, final long vidprocedimento, final long vidStatoCons, final TBene bene, final TBeneImmobile immobile, final long vidDisponibilita, final long vidVincolo, final String vdescVincolo,final long vtipoOccupanti) {

	    return (List<Map>) getHibernateTemplate().execute(new HibernateCallback<List<Map>>() {
	        
            public List<Map> doInHibernate(final Session session) throws HibernateException, SQLException {
	    
	            Query q = session.getNamedQuery("PG_BENE_inserisciImmobile");
				if( bene.getId() != null && bene.getId().getCodiKIdBene() > 0 ){
				    q.setParameter("vidBene", bene.getId().getCodiKIdBene(), Hibernate.LONG);
				}else{
				    q.setParameter("vidBene", null, Hibernate.LONG);
				}
				q.setParameter("vidprocedimento", vidprocedimento);
				q.setParameter("vidUffOwn", vidUffOwn);
				q.setParameter("vdescUtente", vcodUtente);
				q.setParameter("vtipo", bene.getTTpTipoBene().getCodiKIdTipoBene());
				if ((bene.getTTpCatBene() != null) && (bene.getTTpCatBene().getCodiKIdCatBene() > 0)) {
					q.setParameter("vcategoria", bene.getTTpCatBene().getCodiKIdCatBene());
				} else {
					q.setParameter("vcategoria", null, Hibernate.INTEGER);
				}
				if ((bene.getTTpSottocatBene() != null) && (bene.getTTpSottocatBene().getCodiKIdSottocat() > 0)) {
					q.setParameter("vsottocategoria", bene.getTTpSottocatBene().getCodiKIdSottocat());
				} else {
					q.setParameter("vsottocategoria", null, Hibernate.INTEGER);
				}
				if ((bene.getTAnStato() != null) && (bene.getTAnStato().getCodiKIdStato() > 0)) {
					q.setParameter("vidStatoUbi", bene.getTAnStato().getCodiKIdStato());
				} else {
					q.setParameter("vidStatoUbi", null, Hibernate.INTEGER);
				}
				if ((bene.getTAnComuni() != null) && (bene.getTAnComuni().getCodiKIdComune() > 0)) {
					q.setParameter("vidComuneUbi", bene.getTAnComuni().getCodiKIdComune());
				} else {
					q.setParameter("vidComuneUbi", null, Hibernate.INTEGER);
				}
				if ((bene.getTTpTipoStrada() != null) && (bene.getTTpTipoStrada().getCodiKIdTipoStrada() > 0)) {
					q.setParameter("vidTipoStradaUbi", bene.getTTpTipoStrada().getCodiKIdTipoStrada());
				} else {
					q.setParameter("vidTipoStradaUbi", null, Hibernate.INTEGER);
				}
				q.setParameter("vdescIndirizzoUbi", bene.getDescIndirizzo());
				q.setParameter("vnumCivicoUbi", bene.getDescCivico());
				q.setParameter("vnumCapUbi", bene.getDescCap());
				q.setParameter("vdescComuneEsteroUbi", bene.getDescComuneEstero());
				q.setParameter("vdescScalaUbi", immobile.getDescScala());
				q.setParameter("vdescInternoUbi", immobile.getDescInterno());
				q.setParameter("vdescPianoUbi", immobile.getDescPiano());
				q.setParameter("vdescEdificioUbi", immobile.getDescEdificio());
				q.setParameter("vdescLottoUbi", immobile.getDescLotto());
				if ((immobile.getTAnComuniByCodiFkIdComuneCat() != null) && (immobile.getTAnComuniByCodiFkIdComuneCat().getCodiKIdComune() > 0)) {
					q.setParameter("vidComuneCatastale", immobile.getTAnComuniByCodiFkIdComuneCat().getCodiKIdComune());
				} else {
					q.setParameter("vidComuneCatastale", null, Hibernate.INTEGER);
				}
				q.setParameter("vdescCodiceCatastale", immobile.getDescCatasto());
				if(immobile.getTTpTipoCatastoByCodiFkIdTipoCat()!=null  && immobile.getTTpTipoCatastoByCodiFkIdTipoCat().getCodiKIdTipoCat()>0 ){
					q.setParameter("vtipoCatasto", immobile.getTTpTipoCatastoByCodiFkIdTipoCat().getCodiKIdTipoCat());
				}else{
					q.setParameter("vtipoCatasto", null, Hibernate.CHARACTER);
				}
				
				if ((immobile.getTTpCatCatasto() != null) && (immobile.getTTpCatCatasto().getCodiKIdCategCat() > 0)) {
					q.setParameter("vidCategoriaCatastale", immobile.getTTpCatCatasto().getCodiKIdCategCat());
				} else {
					q.setParameter("vidCategoriaCatastale", null, Hibernate.INTEGER);
				}
				q.setParameter("vdataAccatastamento", immobile.getDataDomandaAccatast(), Hibernate.DATE);
				if(immobile.getTTpTipoFondo().getCodiKIdFondo()>0){
					q.setParameter("vfondo", immobile.getTTpTipoFondo().getCodiKIdFondo());
				}else{
					q.setParameter("vfondo", null,Hibernate.CHARACTER);
				}
				q.setParameter("vsezione", immobile.getDescSezione());
				q.setParameter("vfoglio", immobile.getDescFoglio());
				q.setParameter("vparticella1", immobile.getDescParticella1());
				q.setParameter("vparticella2", immobile.getDescParticella2());
				q.setParameter("vsubParticella1", immobile.getDescSubParticella1());
				q.setParameter("vsubParticella2", immobile.getDescSubParticella2());
				if ( immobile.getTTpTipoDenunciaByCodiFkIdTipoDenuncia()!=null && immobile.getTTpTipoDenunciaByCodiFkIdTipoDenuncia().getCodiKIdTipoDen()>0){
					q.setParameter("vidTipoDenuncia", immobile.getTTpTipoDenunciaByCodiFkIdTipoDenuncia().getCodiKIdTipoDen(), Hibernate.CHARACTER);
				}else{
					q.setParameter("vidTipoDenuncia", null, Hibernate.CHARACTER);
				}
				
				if ((immobile.getNumeDenuncia1() != null) && (immobile.getNumeDenuncia1() > 0)) {
					q.setParameter("vnumeroDenuncia1", immobile.getNumeDenuncia1());
				} else {
					q.setParameter("vnumeroDenuncia1", null, Hibernate.INTEGER);
				}
				if ((immobile.getNumeDenuncia2() != null) && (immobile.getNumeDenuncia2() > 0)) {
					q.setParameter("vnumeroDenuncia2", immobile.getNumeDenuncia2());
				} else {
					q.setParameter("vnumeroDenuncia2", null, Hibernate.SHORT);
				}
				if ((immobile.getNumeAnnoDenuncia() != null) && (immobile.getNumeAnnoDenuncia() > 0)) {
					q.setParameter("vannoDenuncia", immobile.getNumeAnnoDenuncia());
				} else {
					q.setParameter("vannoDenuncia", null, Hibernate.SHORT);
				}
				q.setParameter("vcodiSchedaAcc", immobile.getDescSchedaAccatast());
				if (immobile.getDescZona() != null) {
					q.setParameter("vzonaCensuaria", immobile.getDescZona());
				} else {
					q.setParameter("vzonaCensuaria", null, Hibernate.STRING);
				}
				if ((immobile.getImpoRenditaCat() != null) && (immobile.getImpoRenditaCat().doubleValue() > 0d)) {
					q.setParameter("vrenditaCatastale", immobile.getImpoRenditaCat());
				} else {
					q.setParameter("vrenditaCatastale", null, Hibernate.BIG_DECIMAL);
				}
				if ((immobile.getImpoRenditaDom() != null) && (immobile.getImpoRenditaDom().doubleValue() > 0d)) {
					q.setParameter("vrenditaDominicale", immobile.getImpoRenditaDom());
				} else {
					q.setParameter("vrenditaDominicale", null, Hibernate.BIG_DECIMAL);
				}
				if ((immobile.getNumeSupEttari() != null) && (immobile.getNumeSupEttari() > 0)) {
					q.setParameter("vettari", immobile.getNumeSupEttari());
				} else {
					q.setParameter("vettari", null, Hibernate.INTEGER);
				}
				if ((immobile.getNumeSupAre() != null) && (immobile.getNumeSupAre() > 0)) {
					q.setParameter("vare", immobile.getNumeSupAre());
				} else {
					q.setParameter("vare", null, Hibernate.INTEGER);
				}
				if ((immobile.getNumeSupCentiare() != null) && (immobile.getNumeSupCentiare() > 0)) {
					q.setParameter("vcentiare", immobile.getNumeSupCentiare());
				} else {
					q.setParameter("vcentiare", null, Hibernate.INTEGER);
				}
				if ((immobile.getNumeVaniInt() != null) && (immobile.getNumeVaniInt() > 0)) {
					q.setParameter("vvaniInt", immobile.getNumeVaniInt());
				} else {
					q.setParameter("vvaniInt", null, Hibernate.SHORT);
				}
				if (immobile.getNumeVaniDec()!=null && immobile.getNumeVaniDec()>0) {
					q.setParameter("vvaniDec", immobile.getNumeVaniDec());
				} else {
					q.setParameter("vvaniDec", null, Hibernate.SHORT);
				}
				if ((immobile.getNumeMcInt() != null) && (immobile.getNumeMcInt() > 0)) {
					q.setParameter("vmetriCubiInt", immobile.getNumeMcInt());
				} else {
					q.setParameter("vmetriCubiInt", null, Hibernate.SHORT);
				}
				if ((immobile.getNumeMcDec() != null) && (immobile.getNumeMcDec() > 0)) {
					q.setParameter("vmetriCubiDec", immobile.getNumeMcDec());
				} else {
					q.setParameter("vmetriCubiDec", null, Hibernate.BYTE);
				}
				if ((immobile.getNumeSupMqInt() != null) && (immobile.getNumeSupMqInt() > 0)) {
					q.setParameter("vmetriQuadriInt", immobile.getNumeSupMqInt());
				} else {
					q.setParameter("vmetriQuadriInt", null, Hibernate.SHORT);
				}
				if ((immobile.getNumeSupMqDec() != null) && (immobile.getNumeSupMqDec() > 0)) {
					q.setParameter("vmetriQuadriDec", immobile.getNumeSupMqDec());
				} else {
					q.setParameter("vmetriQuadriDec", null, Hibernate.BYTE);
				}
				q.setParameter("vflagAppFabbricatoConfiscato", immobile.getFlgAppFabConf());
				if ((immobile.getTAnComuniByCodiFkIdComuneCatP() != null) && (immobile.getTAnComuniByCodiFkIdComuneCatP().getCodiKIdComune() > 0)) {
					q.setParameter("vidComuneCatastaleP", immobile.getTAnComuniByCodiFkIdComuneCatP().getCodiKIdComune());
				} else {
					q.setParameter("vidComuneCatastaleP", null, Hibernate.INTEGER);
				}
				q.setParameter("vdescCodiceCatastaleP", immobile.getDescCatastoP());
				if(immobile.getTTpTipoCatastoByCodiFkIdTipoCatP()!=null && immobile.getTTpTipoCatastoByCodiFkIdTipoCatP().getCodiKIdTipoCat()>0){
					q.setParameter("vtipoCatastoP", immobile.getTTpTipoCatastoByCodiFkIdTipoCatP());
				}else{
					q.setParameter("vtipoCatastoP", null, Hibernate.CHARACTER);
				}
				q.setParameter("vsezioneP", immobile.getDescSezioneP());
				q.setParameter("vfoglioP", immobile.getDescFoglioP());
				q.setParameter("vparticella1P", immobile.getDescParticella1P());
				q.setParameter("vparticella2P", immobile.getDescParticella2P());
				q.setParameter("vsubParticella1P", immobile.getDescSubalterno1P());
				q.setParameter("vsubParticella2P", immobile.getDescSubalterno2P());
				if(immobile.getTTpTipoDenunciaByCodiFkIdTipoDenunciaP()!=null && immobile.getTTpTipoDenunciaByCodiFkIdTipoDenunciaP().getCodiKIdTipoDen()>0){
					q.setParameter("vidTipoDenunciaP", immobile.getTTpTipoDenunciaByCodiFkIdTipoDenunciaP().getCodiKIdTipoDen());
				}else{
					q.setParameter("vidTipoDenunciaP", null, Hibernate.CHARACTER);
				}
				if ((immobile.getNumeDenuncia1P() != null) && (immobile.getNumeDenuncia1P() > 0)) {
					q.setParameter("vnumeroDenuncia1_P", immobile.getNumeDenuncia1P());
				} else {
					q.setParameter("vnumeroDenuncia1_P", null, Hibernate.INTEGER);
				}
				if ((immobile.getNumeDenuncia2P() != null) && (immobile.getNumeDenuncia2P() > 0)) {
					q.setParameter("vnumeroDenuncia2_P", immobile.getNumeDenuncia2P());
				} else {
					q.setParameter("vnumeroDenuncia2_P", null, Hibernate.SHORT);
				}
				if ((immobile.getNumeAnnoDenunciaP() != null) && (immobile.getNumeAnnoDenunciaP() > 0)) {
					q.setParameter("vannoDenunciaP", immobile.getNumeAnnoDenunciaP());
				} else {
					q.setParameter("vannoDenunciaP", null, Hibernate.SHORT);
				}
				if (vidVincolo > 0) {
					q.setParameter("vidVincolo", vidVincolo);
				} else {
					q.setParameter("vidVincolo", null, Hibernate.LONG);
				}
				q.setParameter("vdescVincolo", vdescVincolo);
				if (vidStatoCons > 0) {
					q.setParameter("vidStatoCons", vidStatoCons);
				} else {
					q.setParameter("vidStatoCons", null, Hibernate.LONG);
				}
				q.setParameter("vflagGravame", bene.getFlgGravame());
				if (vidDisponibilita > 0) {
					q.setParameter("vidDisponibilita", vidDisponibilita);
				} else {
					q.setParameter("vidDisponibilita", null, Hibernate.LONG);
				}
				//commentato da sabino
				if (vtipoOccupanti > 0) {
					q.setParameter("vtipoOccupanti", vtipoOccupanti);
				}else{
					q.setParameter("vtipoOccupanti", null, Hibernate.LONG);
				}
				
				q.setParameter("vnote", bene.getDescNote());
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List<Map> l = q.list();
				return l;
			}
		});
	}
	
	
	
	public List<Map> modificaImmobile(final long vidUffOwn, final String vcodUtente, final long vidprocedimento, final long vidStatoCons, final TBene bene, final TBeneImmobile immobile, final long vidDisponibilita, final long vidVincolo, final String vdescVincolo,final long vtipoOccupanti) {

	    return (List<Map>) getHibernateTemplate().execute(new HibernateCallback<List<Map>>() {

            public List<Map> doInHibernate(final Session session) throws HibernateException, SQLException {
	    
	            Query q = session.getNamedQuery("PG_BENE_modificaImmobile");
				
				q.setParameter("vidBene", bene.getId().getCodiKIdBene(), Hibernate.LONG);
				
				q.setParameter("vidprocedimento", vidprocedimento);
				q.setParameter("vidUffOwn", vidUffOwn);
				q.setParameter("vdescUtente", vcodUtente);
				q.setParameter("vtipo", bene.getTTpTipoBene().getCodiKIdTipoBene());
				if ((bene.getTTpCatBene() != null) && (bene.getTTpCatBene().getCodiKIdCatBene() > 0)) {
					q.setParameter("vcategoria", bene.getTTpCatBene().getCodiKIdCatBene());
				} else {
					q.setParameter("vcategoria", null, Hibernate.INTEGER);
				}
				if ((bene.getTTpSottocatBene() != null) && (bene.getTTpSottocatBene().getCodiKIdSottocat() > 0)) {
					q.setParameter("vsottocategoria", bene.getTTpSottocatBene().getCodiKIdSottocat());
				} else {
					q.setParameter("vsottocategoria", null, Hibernate.INTEGER);
				}
				if ((bene.getTAnStato() != null) && (bene.getTAnStato().getCodiKIdStato() > 0)) {
					q.setParameter("vidStatoUbi", bene.getTAnStato().getCodiKIdStato());
				} else {
					q.setParameter("vidStatoUbi", null, Hibernate.INTEGER);
				}
				if ((bene.getTAnComuni() != null) && (bene.getTAnComuni().getCodiKIdComune() > 0)) {
					q.setParameter("vidComuneUbi", bene.getTAnComuni().getCodiKIdComune());
				} else {
					q.setParameter("vidComuneUbi", null, Hibernate.INTEGER);
				}
				if ((bene.getTTpTipoStrada() != null) && (bene.getTTpTipoStrada().getCodiKIdTipoStrada() > 0)) {
					q.setParameter("vidTipoStradaUbi", bene.getTTpTipoStrada().getCodiKIdTipoStrada());
				} else {
					q.setParameter("vidTipoStradaUbi", null, Hibernate.INTEGER);
				}
				q.setParameter("vdescIndirizzoUbi", bene.getDescIndirizzo());
				q.setParameter("vnumCivicoUbi", bene.getDescCivico());
				q.setParameter("vnumCapUbi", bene.getDescCap());
				q.setParameter("vdescComuneEsteroUbi", bene.getDescComuneEstero());
				q.setParameter("vdescScalaUbi", immobile.getDescScala());
				q.setParameter("vdescInternoUbi", immobile.getDescInterno());
				q.setParameter("vdescPianoUbi", immobile.getDescPiano());
				q.setParameter("vdescEdificioUbi", immobile.getDescEdificio());
				q.setParameter("vdescLottoUbi", immobile.getDescLotto());
				if ((immobile.getTAnComuniByCodiFkIdComuneCat() != null) && (immobile.getTAnComuniByCodiFkIdComuneCat().getCodiKIdComune() > 0)) {
					q.setParameter("vidComuneCatastale", immobile.getTAnComuniByCodiFkIdComuneCat().getCodiKIdComune());
				} else {
					q.setParameter("vidComuneCatastale", null, Hibernate.INTEGER);
				}
				q.setParameter("vdescCodiceCatastale", immobile.getDescCatasto());
				if(immobile.getTTpTipoCatastoByCodiFkIdTipoCat()!=null  && immobile.getTTpTipoCatastoByCodiFkIdTipoCat().getCodiKIdTipoCat()>0 ){
					q.setParameter("vtipoCatasto", immobile.getTTpTipoCatastoByCodiFkIdTipoCat().getCodiKIdTipoCat());
				}else{
					q.setParameter("vtipoCatasto", null, Hibernate.CHARACTER);
				}
				
				if ((immobile.getTTpCatCatasto() != null) && (immobile.getTTpCatCatasto().getCodiKIdCategCat() > 0)) {
					q.setParameter("vidCategoriaCatastale", immobile.getTTpCatCatasto().getCodiKIdCategCat());
				} else {
					q.setParameter("vidCategoriaCatastale", null, Hibernate.INTEGER);
				}
				q.setParameter("vdataAccatastamento", immobile.getDataDomandaAccatast(), Hibernate.DATE);
				if(immobile.getTTpTipoFondo()!=null && immobile.getTTpTipoFondo().getCodiKIdFondo()>0){
					q.setParameter("vfondo", immobile.getTTpTipoFondo().getCodiKIdFondo());
				}else{
					q.setParameter("vfondo", null,Hibernate.CHARACTER);
				}
				q.setParameter("vsezione", immobile.getDescSezione());
				q.setParameter("vfoglio", immobile.getDescFoglio());
				q.setParameter("vparticella1", immobile.getDescParticella1());
				q.setParameter("vparticella2", immobile.getDescParticella2());
				q.setParameter("vsubParticella1", immobile.getDescSubParticella1());
				q.setParameter("vsubParticella2", immobile.getDescSubParticella2());
				if ( immobile.getTTpTipoDenunciaByCodiFkIdTipoDenuncia()!=null && immobile.getTTpTipoDenunciaByCodiFkIdTipoDenuncia().getCodiKIdTipoDen()>0){
					q.setParameter("vidTipoDenuncia", immobile.getTTpTipoDenunciaByCodiFkIdTipoDenuncia().getCodiKIdTipoDen(), Hibernate.CHARACTER);
				}else{
					q.setParameter("vidTipoDenuncia", null, Hibernate.CHARACTER);
				}
				
				if ((immobile.getNumeDenuncia1() != null) && (immobile.getNumeDenuncia1() > 0)) {
					q.setParameter("vnumeroDenuncia1", immobile.getNumeDenuncia1());
				} else {
					q.setParameter("vnumeroDenuncia1", null, Hibernate.INTEGER);
				}
				if ((immobile.getNumeDenuncia2() != null) && (immobile.getNumeDenuncia2() > 0)) {
					q.setParameter("vnumeroDenuncia2", immobile.getNumeDenuncia2());
				} else {
					q.setParameter("vnumeroDenuncia2", null, Hibernate.SHORT);
				}
				if ((immobile.getNumeAnnoDenuncia() != null) && (immobile.getNumeAnnoDenuncia() > 0)) {
					q.setParameter("vannoDenuncia", immobile.getNumeAnnoDenuncia());
				} else {
					q.setParameter("vannoDenuncia", null, Hibernate.SHORT);
				}
				q.setParameter("vcodiSchedaAcc", immobile.getDescSchedaAccatast());
				if (immobile.getDescZona() != null) {
					q.setParameter("vzonaCensuaria", immobile.getDescZona());
				} else {
					q.setParameter("vzonaCensuaria", null, Hibernate.STRING);
				}
				if ((immobile.getImpoRenditaCat() != null) && (immobile.getImpoRenditaCat().doubleValue() > 0d)) {
					q.setParameter("vrenditaCatastale", immobile.getImpoRenditaCat());
				} else {
					q.setParameter("vrenditaCatastale", null, Hibernate.BIG_DECIMAL);
				}
				if ((immobile.getImpoRenditaDom() != null) && (immobile.getImpoRenditaDom().doubleValue() > 0d)) {
					q.setParameter("vrenditaDominicale", immobile.getImpoRenditaDom());
				} else {
					q.setParameter("vrenditaDominicale", null, Hibernate.BIG_DECIMAL);
				}
				if ((immobile.getNumeSupEttari() != null) && (immobile.getNumeSupEttari() > 0)) {
					q.setParameter("vettari", immobile.getNumeSupEttari());
				} else {
					q.setParameter("vettari", null, Hibernate.INTEGER);
				}
				if ((immobile.getNumeSupAre() != null) && (immobile.getNumeSupAre() > 0)) {
					q.setParameter("vare", immobile.getNumeSupAre());
				} else {
					q.setParameter("vare", null, Hibernate.INTEGER);
				}
				if ((immobile.getNumeSupCentiare() != null) && (immobile.getNumeSupCentiare() > 0)) {
					q.setParameter("vcentiare", immobile.getNumeSupCentiare());
				} else {
					q.setParameter("vcentiare", null, Hibernate.INTEGER);
				}
				if ((immobile.getNumeVaniInt() != null) && (immobile.getNumeVaniInt() > 0)) {
					q.setParameter("vvaniInt", immobile.getNumeVaniInt());
				} else {
					q.setParameter("vvaniInt", null, Hibernate.SHORT);
				}
				if (immobile.getNumeVaniDec()!=null && immobile.getNumeVaniDec()>0) {
					q.setParameter("vvaniDec", immobile.getNumeVaniDec());
				} else {
					q.setParameter("vvaniDec", null, Hibernate.SHORT);
				}
				if ((immobile.getNumeMcInt() != null) && (immobile.getNumeMcInt() > 0)) {
					q.setParameter("vmetriCubiInt", immobile.getNumeMcInt());
				} else {
					q.setParameter("vmetriCubiInt", null, Hibernate.SHORT);
				}
				if ((immobile.getNumeMcDec() != null) && (immobile.getNumeMcDec() > 0)) {
					q.setParameter("vmetriCubiDec", immobile.getNumeMcDec());
				} else {
					q.setParameter("vmetriCubiDec", null, Hibernate.BYTE);
				}
				if ((immobile.getNumeSupMqInt() != null) && (immobile.getNumeSupMqInt() > 0)) {
					q.setParameter("vmetriQuadriInt", immobile.getNumeSupMqInt());
				} else {
					q.setParameter("vmetriQuadriInt", null, Hibernate.SHORT);
				}
				if ((immobile.getNumeSupMqDec() != null) && (immobile.getNumeSupMqDec() > 0)) {
					q.setParameter("vmetriQuadriDec", immobile.getNumeSupMqDec());
				} else {
					q.setParameter("vmetriQuadriDec", null, Hibernate.BYTE);
				}
				q.setParameter("vflagAppFabbricatoConfiscato", immobile.getFlgAppFabConf());
				if ((immobile.getTAnComuniByCodiFkIdComuneCatP() != null) && (immobile.getTAnComuniByCodiFkIdComuneCatP().getCodiKIdComune() > 0)) {
					q.setParameter("vidComuneCatastaleP", immobile.getTAnComuniByCodiFkIdComuneCatP().getCodiKIdComune());
				} else {
					q.setParameter("vidComuneCatastaleP", null, Hibernate.INTEGER);
				}
				q.setParameter("vdescCodiceCatastaleP", immobile.getDescCatastoP());
				if(immobile.getTTpTipoCatastoByCodiFkIdTipoCatP()!=null && immobile.getTTpTipoCatastoByCodiFkIdTipoCatP().getCodiKIdTipoCat()>0){
					q.setParameter("vtipoCatastoP", immobile.getTTpTipoCatastoByCodiFkIdTipoCatP());
				}else{
					q.setParameter("vtipoCatastoP", null, Hibernate.CHARACTER);
				}
				q.setParameter("vsezioneP", immobile.getDescSezioneP());
				q.setParameter("vfoglioP", immobile.getDescFoglioP());
				q.setParameter("vparticella1P", immobile.getDescParticella1P());
				q.setParameter("vparticella2P", immobile.getDescParticella2P());
				q.setParameter("vsubParticella1P", immobile.getDescSubalterno1P());
				q.setParameter("vsubParticella2P", immobile.getDescSubalterno2P());
				if(immobile.getTTpTipoDenunciaByCodiFkIdTipoDenunciaP()!=null && immobile.getTTpTipoDenunciaByCodiFkIdTipoDenunciaP().getCodiKIdTipoDen()>0){
					q.setParameter("vidTipoDenunciaP", immobile.getTTpTipoDenunciaByCodiFkIdTipoDenunciaP().getCodiKIdTipoDen());
				}else{
					q.setParameter("vidTipoDenunciaP", null, Hibernate.CHARACTER);
				}
				if ((immobile.getNumeDenuncia1P() != null) && (immobile.getNumeDenuncia1P() > 0)) {
					q.setParameter("vnumeroDenuncia1_P", immobile.getNumeDenuncia1P());
				} else {
					q.setParameter("vnumeroDenuncia1_P", null, Hibernate.INTEGER);
				}
				if ((immobile.getNumeDenuncia2P() != null) && (immobile.getNumeDenuncia2P() > 0)) {
					q.setParameter("vnumeroDenuncia2_P", immobile.getNumeDenuncia2P());
				} else {
					q.setParameter("vnumeroDenuncia2_P", null, Hibernate.SHORT);
				}
				if ((immobile.getNumeAnnoDenunciaP() != null) && (immobile.getNumeAnnoDenunciaP() > 0)) {
					q.setParameter("vannoDenunciaP", immobile.getNumeAnnoDenunciaP());
				} else {
					q.setParameter("vannoDenunciaP", null, Hibernate.SHORT);
				}
				if (vidVincolo > 0) {
					q.setParameter("vidVincolo", vidVincolo);
				} else {
					q.setParameter("vidVincolo", null, Hibernate.LONG);
				}
				q.setParameter("vdescVincolo", vdescVincolo);
				if (vidStatoCons > 0) {
					q.setParameter("vidStatoCons", vidStatoCons);
				} else {
					q.setParameter("vidStatoCons", null, Hibernate.LONG);
				}
				q.setParameter("vflagGravame", bene.getFlgGravame());
				if (vidDisponibilita > 0) {
					q.setParameter("vidDisponibilita", vidDisponibilita);
				} else {
					q.setParameter("vidDisponibilita", null, Hibernate.LONG);
				}
				//commentato da sabino
				if (vtipoOccupanti > 0) {
					q.setParameter("vtipoOccupanti", vtipoOccupanti);
				}else{
					q.setParameter("vtipoOccupanti", null, Hibernate.LONG);
				}
				
				q.setParameter("vnote", bene.getDescNote());
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List<Map> l = q.list();
				return l;
			}
		});
	}

	
	public List<Map> inserisciMobile(final long vidUffOwn, final String vcodUtente, final long vidprocedimento, final long vidstatocons, final TBene bene, final TAnimali animali, final TCollezioni collezioni, final String vdescComune, final String vtipoStrada, final String vdesccategoria, final String vdescSottoCat) {

	    return (List<Map>) getHibernateTemplate().execute(new HibernateCallback<List<Map>>() {
            public List<Map> doInHibernate(final Session session) throws HibernateException, SQLException {

				long vidstato = 0;
				long vidcomune = 0;
				long vidtipostrada = 0;
				String vdescindirizzo = null;
				String vnumcivico = null;
				String vnumcap = null;
				String vdesccomuneestero = null;
				String vdescrizioneCapo = null;
				long vnumeroAnimali = 0;
				String vdescrizioneColl = null;
				long vnumeroPezzi = 0;
				String vubicazioneColl = null;
				if ((bene.getTAnStato() != null) && (bene.getTAnStato().getCodiKIdStato() > 0)) {
					vidstato = bene.getTAnStato().getCodiKIdStato();
				}
				if ((bene.getTAnComuni() != null) && (bene.getTAnComuni().getCodiKIdComune() > 0)) {
					vidcomune = bene.getTAnComuni().getCodiKIdComune();
				}
				if ((bene.getTTpTipoStrada() != null) && (bene.getTTpTipoStrada().getCodiKIdTipoStrada() > 0)) {
					vidtipostrada = bene.getTTpTipoStrada().getCodiKIdTipoStrada();
				}
				vdesccomuneestero = bene.getDescComuneEstero();
				vdescindirizzo = bene.getDescIndirizzo();
				vnumcivico = bene.getDescCivico();
				vnumcap = bene.getDescCap();
				
				if(bene.getTTpCatBene() != null){
					if(ComparatorUtil.containsValue(bene.getTTpCatBene().getCodiKIdCatBene(), Constants.CategoriaBeni.ID_TIPO_ANIMALI)){
						vdescrizioneCapo = animali.getDescCapo();
						if (animali.getNumeAnimali() != null) {
							vnumeroAnimali = animali.getNumeAnimali();
						}
					}
					if(ComparatorUtil.containsValue(bene.getTTpCatBene().getCodiKIdCatBene(), Constants.CategoriaBeni.ID_TIPO_COLLEZIONE)){
						vdescrizioneColl = collezioni.getDescCollezione();
						vnumeroPezzi = collezioni.getNumePezzi();
						vubicazioneColl = "";
					}
					//Giacomo 28/08/2013 Aggiunto gestione Altri Oggetti
					if(ComparatorUtil.containsValue(bene.getTTpCatBene().getCodiKIdCatBene(), Constants.CategoriaBeni.ID_ALTRI_OGGETTI)){
						vdescrizioneColl = bene.getDescrBene();
					}
				}
				
				
				Query q = session.getNamedQuery("PG_BENE_inserisciMobile");
				if( bene.getId() != null && bene.getId().getCodiKIdBene() > 0 )
                    q.setParameter("vidBene", bene.getId().getCodiKIdBene(), Hibernate.LONG);
                else
                    q.setParameter("vidBene", null, Hibernate.LONG);
				q.setParameter("vidUffOwn", vidUffOwn);
				q.setParameter("vdescUtente", vcodUtente);
				q.setParameter("vtipo", bene.getTTpTipoBene().getCodiKIdTipoBene());
				if ((bene.getTTpCatBene() != null) && (bene.getTTpCatBene().getCodiKIdCatBene() > 0)) {
					q.setParameter("vcategoria", bene.getTTpCatBene().getCodiKIdCatBene());
				} else {
					q.setParameter("vcategoria", null, Hibernate.LONG);
				}
				if ((bene.getTTpSottocatBene() != null) && (bene.getTTpSottocatBene().getCodiKIdSottocat() > 0)) {
					q.setParameter("vsottocategoria", bene.getTTpSottocatBene().getCodiKIdSottocat());
				} else {
					q.setParameter("vsottocategoria", null, Hibernate.LONG);
				}
				if (vidstato > 0) {
					q.setParameter("vidstato", vidstato);
				} else {
					q.setParameter("vidstato", null, Hibernate.LONG);
				}
				if (vidcomune > 0) {
					q.setParameter("vidcomune", vidcomune);
				} else {
					q.setParameter("vidcomune", null, Hibernate.LONG);
				}
				if (vidtipostrada > 0) {
					q.setParameter("vidtipostrada", vidtipostrada);
				} else {
					q.setParameter("vidtipostrada", null, Hibernate.LONG);
				}
				q.setParameter("vdescindirizzo", vdescindirizzo);
				q.setParameter("vnumcivico", vnumcivico);
				q.setParameter("vnumcap", vnumcap);
				q.setParameter("vdesccomuneestero", vdesccomuneestero);
				q.setParameter("vnote", bene.getDescNote());
				q.setParameter("vflaggravame", bene.getFlgGravame());
				if (vidstatocons > 0) {
					q.setParameter("vidstatocons", vidstatocons);
				} else {
					q.setParameter("vidstatocons", null, Hibernate.LONG);
				}
				q.setParameter("vidprocedimento", vidprocedimento);
				q.setParameter("vdescComune", vdescComune);
				q.setParameter("vtipoStrada", vtipoStrada);
				q.setParameter("vdesccategoria", vdesccategoria);
				q.setParameter("vdescSottoCat", vdescSottoCat);
				q.setParameter("vdescrizioneCapo", vdescrizioneCapo);
				if (vnumeroAnimali > 0) {
					q.setParameter("vnumeroAnimali", vnumeroAnimali);
				} else {
					q.setParameter("vnumeroAnimali", null, Hibernate.LONG);
				}
				q.setParameter("vdescColl", vdescrizioneColl);
				if (vnumeroPezzi > 0) {
					q.setParameter("vnumPezzi", vnumeroPezzi);
				} else {
					q.setParameter("vnumPezzi", null, Hibernate.LONG);
				}
				q.setParameter("vubicazione", vubicazioneColl);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List<Map> l = q.list();
				return l;
			}
		});
	}

	/**
	 * inserisciMobileRegistrato
	 * 
	 * @param vidUffOwn
	 * @param vcodUtente
	 * @param vidprocedimento
	 * @param vidstatobene
	 * @param bene
	 * @param veicolo
	 * @param vdescComune
	 * @param vtipoStrada
	 * @param vdesccategoria
	 * @param vdescSottoCat
	 * @param vidDisponibilita
	 */
	
	public List<Map> inserisciMobileRegistrato(final long vidUffOwn, final String vcodUtente, final long vidprocedimento, final long vidstatobene, final TBene bene, final TVeicolo veicolo, final TBeneImmat immateriale, final String vdescComune, final String vtipoStrada, final String vdesccategoria, final String vdescSottoCat, final long vidDisponibilita) {

	    return (List<Map>) getHibernateTemplate().execute(new HibernateCallback<List<Map>>() {
            public List<Map> doInHibernate(final Session session) throws HibernateException, SQLException {

				String vnumeroRegistrazione = null;
				Date vdataRegistrazione = null;
				Date vdataScadenza = null;
				String vmodello = null;
				String vcodiceImmatricolazione = null;
				String vtarga = null;
				String vtelaio = null;
				String vcolore = null;
				String vnumeroIdentificazione = null;
				if ((bene != null) && (bene.getTTpCatBene() != null) && (bene.getTTpCatBene().getCodiKIdCatBene() == 10L)) {// se
					// il
					// MOBILE
					// REGISTRATO
					// è
					// un
					// VEICOLO
					if (veicolo.getDescReg() != null) {
						vnumeroRegistrazione = veicolo.getDescReg();
					}
					vdataRegistrazione = veicolo.getDataReg();
					vmodello = veicolo.getDescModello();
					if (veicolo.getDescImmatricolaz() != null) {
						vcodiceImmatricolazione = veicolo.getDescImmatricolaz();
					}
					vtarga = veicolo.getDescTarga();
					vtelaio = veicolo.getDescTelaio();
					vcolore = veicolo.getDescColore();
					if (veicolo.getDescIdentif() != null) {
						vnumeroIdentificazione = veicolo.getDescIdentif();
					}
				}
				if ((bene != null) && (bene.getTTpCatBene() != null) && (bene.getTTpCatBene().getCodiKIdCatBene() == 11L)) {// se
					// il
					// MOBILE
					// REGISTRATO
					// è
					// un
					// BENE
					// IMMATERIALE
					if (immateriale.getDescRegistrazione() != null) {
						vnumeroRegistrazione = immateriale.getDescRegistrazione();
					}
					if (immateriale.getDataRegistrazione() != null) {
						vdataRegistrazione = immateriale.getDataRegistrazione();
					}
					if (immateriale.getDataScadenza() != null) {
						vdataScadenza = immateriale.getDataScadenza();
					}
				}
				Query q = session.getNamedQuery("PG_BENE_inserisciMobileRegistrato");
				if( bene.getId() != null && bene.getId().getCodiKIdBene() > 0 )
                    q.setParameter("vidBene", bene.getId().getCodiKIdBene(), Hibernate.LONG);
                else
                    q.setParameter("vidBene", null, Hibernate.LONG);
				q.setParameter("vidUffOwn", vidUffOwn);
				q.setParameter("vdescUtente", vcodUtente);
				q.setParameter("vtipo", bene.getTTpTipoBene().getCodiKIdTipoBene());
				if ((bene.getTTpCatBene() != null) && (bene.getTTpCatBene().getCodiKIdCatBene() > 0)) {
					q.setParameter("vcategoria", bene.getTTpCatBene().getCodiKIdCatBene());
				} else {
					q.setParameter("vcategoria", null, Hibernate.LONG);
				}
				if ((bene.getTTpSottocatBene() != null) && (bene.getTTpSottocatBene().getCodiKIdSottocat() > 0)) {
					q.setParameter("vsottocategoria", bene.getTTpSottocatBene().getCodiKIdSottocat());
				} else {
					q.setParameter("vsottocategoria", null, Hibernate.INTEGER);
				}
				if (vidstatobene > 0) {
					q.setParameter("vidstatocons", vidstatobene);
				} else {
					q.setParameter("vidstatocons", null, Hibernate.LONG);
				}
				if (vidprocedimento > 0) {
					q.setParameter("vidprocedimento", vidprocedimento);
				} else {
					q.setParameter("vidprocedimento", null, Hibernate.LONG);
				}
				if ((bene.getTAnStato() != null) && (bene.getTAnStato().getCodiKIdStato() > 0)) {
					q.setParameter("vidstato", bene.getTAnStato().getCodiKIdStato());
				} else {
					q.setParameter("vidstato", null, Hibernate.INTEGER);
				}
				if ((bene.getTAnComuni() != null) && (bene.getTAnComuni().getCodiKIdComune() > 0)) {
					q.setParameter("vidcomune", bene.getTAnComuni().getCodiKIdComune());
				} else {
					q.setParameter("vidcomune", null, Hibernate.INTEGER);
				}
				if ((bene.getTTpTipoStrada() != null) && (bene.getTTpTipoStrada().getCodiKIdTipoStrada() > 0)) {
					q.setParameter("vidtipostrada", bene.getTTpTipoStrada().getCodiKIdTipoStrada());
				} else {
					q.setParameter("vidtipostrada", null, Hibernate.INTEGER);
				}
				q.setParameter("vdescindirizzo", bene.getDescIndirizzo());
				q.setParameter("vnumcivico", bene.getDescCivico());
				q.setParameter("vnumcap", bene.getDescCap());
				q.setParameter("vdesccomuneestero", bene.getDescComuneEstero());
				q.setParameter("vnote", bene.getDescNote());
				q.setParameter("vflagcostazienda", bene.getFlgCostAzienda());
				q.setParameter("vflaggravame", bene.getFlgGravame());
				if (vnumeroRegistrazione != null) {
					q.setParameter("vnumeroRegistrazione", vnumeroRegistrazione);
				} else {
					q.setParameter("vnumeroRegistrazione", null, Hibernate.STRING);
				}
				q.setParameter("vdataRegistrazione", vdataRegistrazione, Hibernate.DATE);
				if (vnumeroIdentificazione != null) {
					q.setParameter("vnumeroIdentificazione", vnumeroIdentificazione);
				} else {
					q.setParameter("vnumeroIdentificazione", null, Hibernate.STRING);
				}
				if (vcodiceImmatricolazione != null) {
					q.setParameter("vcodiceImmatricolazione", vcodiceImmatricolazione);
				} else {
					q.setParameter("vcodiceImmatricolazione", null, Hibernate.STRING);
				}
				q.setParameter("vtarga", vtarga);
				q.setParameter("vtelaio", vtelaio);
				q.setParameter("vcolore", vcolore);
				q.setParameter("vmodello", vmodello);
				if (vidDisponibilita > 0) {
					q.setParameter("vidDisponibilita", vidDisponibilita);
				} else {
					q.setParameter("vidDisponibilita", null, Hibernate.LONG);
				}
				if (vdataScadenza != null) {
					q.setParameter("vdataScadenza", vdataScadenza, Hibernate.DATE);
				} else {
					q.setParameter("vdataScadenza", null, Hibernate.DATE);
				}
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List<Map> l = q.list();
				return l;
			}
		});
	}

	
	public List listaBeniAttoNoMisPatr(final int idUffOwn, final long idProcedimento, final long idAtto, final long idSoggetto, final Long idSoggettoTit, final Long idTipologia, final Long idCategoria, final Long idSottocategoria) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				List lista = null;
				Query q = session.getNamedQuery("PG_BENE_beni_atto_no_mis_patr");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setParameter("idAtto", idAtto, Hibernate.LONG);
				q.setParameter("idSoggetto", idSoggetto, Hibernate.LONG);
				if (idSoggettoTit > 0) 
					q.setParameter("vidSoggettoTit", idSoggettoTit);
				else 
					q.setParameter("vidSoggettoTit", null, Hibernate.LONG);
				if (idTipologia > 0) 
					q.setParameter("vidTipologia", idTipologia);
				else 
					q.setParameter("vidTipologia", null, Hibernate.LONG);
				if (idCategoria > 0)
					q.setParameter("vidCategoria", idCategoria);
				else 
					q.setParameter("vidCategoria", null, Hibernate.LONG);
				if (idSottocategoria > 0)
					q.setParameter("vidSottocategoria", idSottocategoria);
				else 
					q.setParameter("vidSottocategoria", null, Hibernate.LONG);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				
				lista = q.list();
				return lista;
			}
		});
	}

	
	public List listaBeniPerProcedimento(final int filtro, final int idUffOwn, final long idProcedimento, final Long idBene, final Long idTipologia, final Long idCategoria, final Long idSottocategoria, final Long idSogInv, final Integer idCatAtto) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

//				List lista = null;
				Query q = session.getNamedQuery("PG_BENE_beni_per_proc");
				q.setParameter("vFiltro", filtro);
				q.setParameter("vidUffOwn", idUffOwn);
				q.setParameter("vidProcedimento", idProcedimento, Hibernate.LONG);
				if (idBene != null) {
					q.setParameter("vidBene", idBene);
				} else {
					q.setParameter("vidBene", null, Hibernate.LONG);
				}
				if (idTipologia != null) {
					q.setParameter("vidTipologia", idTipologia);
				} else {
					q.setParameter("vidTipologia", null, Hibernate.LONG);
				}
				if (idCategoria != null) {
					q.setParameter("vidCategoria", idCategoria);
				} else {
					q.setParameter("vidCategoria", null, Hibernate.LONG);
				}
				if (idSottocategoria != null) {
					q.setParameter("vidSottocategoria", idSottocategoria);
				} else {
					q.setParameter("vidSottocategoria", null, Hibernate.LONG);
				}
				
				if (idSogInv != null) {
					q.setParameter("vidSogInv", idSogInv);
				} else {
					q.setParameter("vidSogInv", null, Hibernate.LONG);
				}
				
				q.setParameter("vIsFascGest", null, Hibernate.LONG);
				
				if (idCatAtto != null) {
					q.setParameter("idCatAtto", idCatAtto, Hibernate.INTEGER);
				} else {				
					q.setParameter("idCatAtto", null, Hibernate.INTEGER);
				}
				
				q.setResultTransformer(Transformers.aliasToBean(BeneTitoloDirittoDto.class));
				
				List<BeneTitoloDirittoDto> l = q.list();
				if ((l != null) && (l.size() > 0)) {
					return l;
				}
				
				
				return null;
			}
		});
	}
	
	
	public List listaBeniPerProcedimentoFascicoloGestione(final int filtro, final int idUffOwn, final long idProcedimento, final Long idBene, final Long idTipologia, final Long idCategoria, final Long idSottocategoria) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

//				List lista = null;
				Query q = session.getNamedQuery("PG_BENE_beni_per_proc_FG");
				q.setParameter("vFiltro", filtro);
				q.setParameter("vidUffOwn", idUffOwn);
				q.setParameter("vidProcedimento", idProcedimento, Hibernate.LONG);
				if (idBene != null) {
					q.setParameter("vidBene", idBene);
				} else {
					q.setParameter("vidBene", null, Hibernate.LONG);
				}
				if (idTipologia != null) {
					q.setParameter("vidTipologia", idTipologia);
				} else {
					q.setParameter("vidTipologia", null, Hibernate.LONG);
				}
				if (idCategoria != null) {
					q.setParameter("vidCategoria", idCategoria);
				} else {
					q.setParameter("vidCategoria", null, Hibernate.LONG);
				}
				if (idSottocategoria != null) {
					q.setParameter("vidSottocategoria", idSottocategoria);
				} else {
					q.setParameter("vidSottocategoria", null, Hibernate.LONG);
				}
				
				q.setResultTransformer(Transformers.aliasToBean(BeneTitoloDirittoDto.class));
				
				List<BeneTitoloDirittoDto> l = q.list();
				if ((l != null) && (l.size() > 0)) {
					return l;
				}
				
				
				return null;
			}
		});
	}	

	
	public List listaBeniPerProcedimentoNoAtto(final int idUffOwn, final long idProcedimento, final long idAtto1, final long idAtto2,
    		final long idSoggettoDiretto, final long idSoggettoTitolare, final long idTipologia, final long idCategoria, final long idSottocategoria) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				List lista = null;
				Query q = session.getNamedQuery("PG_BENE_beni_per_proc_no_atto");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setParameter("idAtto1", idAtto1, Hibernate.LONG);
				if (idAtto2 != 0l) {
					q.setParameter("idAtto2", idAtto2, Hibernate.LONG);
				} else {
					q.setParameter("idAtto2", null, Hibernate.LONG);
				}		
				

				if (idSoggettoDiretto > 0) {
					q.setParameter("vidSoggettoDiretto", idSoggettoDiretto);
				} else {
					q.setParameter("vidSoggettoDiretto", null, Hibernate.LONG);
				}
				if (idSoggettoTitolare > 0) {
					q.setParameter("vidSoggettoTit", idSoggettoTitolare);
				} else {
					q.setParameter("vidSoggettoTit", null, Hibernate.LONG);
				}
				if (idTipologia > 0) {
					q.setParameter("vidTipologia", idTipologia);
				} else {
					q.setParameter("vidTipologia", null, Hibernate.LONG);
				}
				if (idCategoria > 0) {
					q.setParameter("vidCategoria", idCategoria);
				} else {
					q.setParameter("vidCategoria", null, Hibernate.LONG);
				}
				if (idSottocategoria > 0) {
					q.setParameter("vidSottocategoria", idSottocategoria);
				} else {
					q.setParameter("vidSottocategoria", null, Hibernate.LONG);
				}

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				lista = q.list();
				return lista;
			}
		});
	}

	
	public List listaBeniProcDecretoFiltro(final int filtro, final int idUffOwn, final long idProcedimento, final Long idBene, final Long idTipologia, final Long idCategoria, final Long idSottocategoria, final long vidDecreto, final long vidProposta, final Long idAmministratore, final String Amministratore) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				List lista = null;
				Query q = session.getNamedQuery("PG_BENE_beni_proc_decreto_filtro");
				q.setParameter("vFiltro", filtro);
				q.setParameter("vidUffOwn", idUffOwn);
				q.setParameter("vidProcedimento", idProcedimento, Hibernate.LONG);
				if (idBene != null) {
					q.setParameter("vidBene", idBene);
				} else {
					q.setParameter("vidBene", null, Hibernate.LONG);
				}
				if (idTipologia != null) {
					q.setParameter("vidTipologia", idTipologia);
				} else {
					q.setParameter("vidTipologia", null, Hibernate.LONG);
				}
				if (idCategoria != null) {
					q.setParameter("vidCategoria", idCategoria);
				} else {
					q.setParameter("vidCategoria", null, Hibernate.LONG);
				}
				if (idSottocategoria != null) {
					q.setParameter("vidSottocategoria", idSottocategoria);
				} else {
					q.setParameter("vidSottocategoria", null, Hibernate.LONG);
				}
				if (idAmministratore != null) {
					q.setParameter("vidAmministratore", idAmministratore);
				} else {
					q.setParameter("vidAmministratore", null, Hibernate.LONG);
				}
				if (Amministratore != null) {
					q.setParameter("AMMINISTRATORE", Amministratore);
				} else {
					q.setParameter("AMMINISTRATORE", null, Hibernate.STRING);
				}
				q.setParameter("vidDecreto", vidDecreto, Hibernate.LONG);
				q.setParameter("vidProposta", vidProposta, Hibernate.LONG);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				lista = q.list();
				return lista;
			}
		});
	}

	public List<TRelAttoMisPatr> listaRelAttoMisPatr(long idPersona, long idProcedimento, long idAtto, Integer idUfficioUtente, long idBene, Integer idTipoInteresse) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TRelAttoMisPatr.class);
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		criteria.add(Restrictions.eq("id.codiKIdAtto", idAtto));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente.intValue()));
		criteria.add(Restrictions.eq("id.codiKIdBene", idBene));
		if (idTipoInteresse != null) {
			criteria.add(Restrictions.eq("id.codiKIdTipoInt", idTipoInteresse.intValue()));
		}
		criteria.add(Restrictions.eq("id.codiKIdSoggTit", idPersona));
		List<TRelAttoMisPatr> risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	
	public List listaSoggettiBeniProc(final long idProcedimento, final int idUffOwner, final long idBene, final long idAtto) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				List lista = null;
				Query q = session.getNamedQuery("PG_BENE_sogint_bene_proc_atto");
				q.setParameter("idProc", idProcedimento);
				q.setParameter("vidUffOwn", idUffOwner);
				q.setParameter("idBene", idBene);
				q.setParameter("idAtto", idAtto);
				q.setResultTransformer(Transformers.aliasToBean(SogIntDettaglioProcedimentoDto.class));
				lista = q.list();
				return lista;
			}
		});
	}
	
	
	public List listaSoggettiBeniProcTuttiAtti(final long idProcedimento, final int idUffOwner, final long idBene) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				List lista = null;
				Query q = session.getNamedQuery("PG_BENE_sogint_bene_proc_tutti_atti");
				q.setParameter("idProc", idProcedimento);
				q.setParameter("vidUffOwn", idUffOwner);
				q.setParameter("idBene", idBene);
				q.setResultTransformer(Transformers.aliasToBean(SogIntDettaglioProcedimentoDto.class));
				lista = q.list();
				return lista;
			}
		});
	}

	public void salvaDecisorioBeni(final TRelBeneAtto bene,final String descDecisorio) {

		getHibernateTemplate().execute(new HibernateCallback<Object>() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_BENE_salvaDecisorioBene");
				q.setParameter("idAtto", bene.getId().getCodiKIdAtto(), Hibernate.LONG);
				q.setParameter("idBene", bene.getId().getCodiKIdBene(), Hibernate.LONG);
				q.setParameter("idSoggetto", bene.getId().getCodiKIdSoggTit(), Hibernate.LONG);
				q.setParameter("idProcedimento", bene.getId().getCodiKIdProc(), Hibernate.LONG);
				q.setParameter("idUfficio", bene.getId().getCodiKIdUffOwner(), Hibernate.INTEGER);
								
			    if ("DECISORIO".equalsIgnoreCase(descDecisorio))
					q.setParameter("idDecisione", bene.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito(), Hibernate.INTEGER);
			    else if ("DECISORIO_II".equalsIgnoreCase(descDecisorio))
			    	q.setParameter("idDecisione", bene.getTTpEsitoByCodiFkIdEsito2().getCodiKIdEsito(), Hibernate.INTEGER);			    
			    else if ("SENTENZA_CASSAZIONE".equalsIgnoreCase(descDecisorio))
			    	q.setParameter("idDecisione", bene.getTTpEsitoByCodiFkIdEsito3().getCodiKIdEsito(), Hibernate.INTEGER);
			    else
			    	q.setParameter("idDecisione", null, Hibernate.INTEGER);	
		
				q.setParameter("idCodiceUtente", bene.getDescUtente(), Hibernate.STRING);
				q.setParameter("descDecisorio", descDecisorio, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List<Map<?, ?>> l = q.list();
				return l;
			}
		});
	}
	
	public void salvaDecisorioBeniIstGen(final long idAtto, final long idProcedimento, final String idCodiceUtente, final int idUfficio, final int idTipoUff, final String descDecisorio, final int idDecisorio, final TRelAttoIstGen rec) {

		getHibernateTemplate().execute(new HibernateCallback<Object>() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_BENE_salvaDecisorioBeneIstGen");
				q.setParameter("idAtto", idAtto, Hibernate.LONG);
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setParameter("idUfficio", idUfficio, Hibernate.INTEGER);
				q.setParameter("idDecisione", idDecisorio, Hibernate.INTEGER);
				q.setParameter("descUtente", idCodiceUtente, Hibernate.STRING);
				q.setParameter("descDecisorio", descDecisorio, Hibernate.STRING);
				
				if(rec!=null && rec.getImpo1()!=null) {
					q.setParameter("IMPO_1", rec.getImpo1(), Hibernate.BIG_DECIMAL);
				} else {
					q.setParameter("IMPO_1", null, Hibernate.BIG_DECIMAL);
				}
				if(rec!=null && rec.getDataPresent2()!=null) {
					q.setParameter("DATA_PRESENT_2", rec.getDataPresent2(), Hibernate.DATE);
				} else {
					q.setParameter("DATA_PRESENT_2", null, Hibernate.DATE);
				}
				
				if(rec!=null && rec.getDataDeposito2()!=null) {
					q.setParameter("DATA_DEPOSITO_2", rec.getDataDeposito2(), Hibernate.DATE);
				} else {
					q.setParameter("DATA_DEPOSITO_2", null, Hibernate.DATE);
				}
				
				if(rec!=null && rec.getImpo2()!=null) {	
					q.setParameter("IMPO_2", rec.getImpo2(), Hibernate.BIG_DECIMAL);
				} else {
					q.setParameter("IMPO_2", null, Hibernate.BIG_DECIMAL);
				}

				if(rec!=null && rec.getDataDef2()!=null) {
					q.setParameter("DATA_DEF_2", rec.getDataDef2(), Hibernate.DATE);
				} else {
					q.setParameter("DATA_DEF_2", null, Hibernate.DATE);
				}
				
				if(rec!=null && rec.getDataPresent3()!=null) {
					q.setParameter("DATA_PRESENT_3", rec.getDataPresent3(), Hibernate.DATE);
				} else {
					q.setParameter("DATA_PRESENT_3", null, Hibernate.DATE);
				}

				if(rec!=null && rec.getDataDeposito3()!=null) {
					q.setParameter("DATA_DEPOSITO_3", rec.getDataDeposito3(), Hibernate.DATE);
				} else {
					q.setParameter("DATA_DEPOSITO_3", null, Hibernate.DATE);
				}

				if(rec!=null && rec.getImpo3()!=null) {
					q.setParameter("IMPO_3", rec.getImpo3(), Hibernate.BIG_DECIMAL);
				} else {
					q.setParameter("IMPO_3", null, Hibernate.BIG_DECIMAL);
				}

				if(rec!=null && rec.getDataDef3()!=null) {
					q.setParameter("DATA_DEF_3", rec.getDataDef3(), Hibernate.DATE);
				} else {
					q.setParameter("DATA_DEF_3", null, Hibernate.DATE);
				}
				
				if(rec!=null && rec.getDescAltro1()!=null) {
					rec.setDescAltro1(rec.getDescAltro1().replaceAll("'", "''"));
					q.setParameter("DESC_ALTRO_1", rec.getDescAltro1(), Hibernate.STRING);
				} else {
					q.setParameter("DESC_ALTRO_1", null, Hibernate.STRING);
				}		
				
				if(rec!=null && rec.getDescAltro2()!=null) {
					rec.setDescAltro2(rec.getDescAltro2().replaceAll("'", "''"));
					q.setParameter("DESC_ALTRO_2", rec.getDescAltro2(), Hibernate.STRING);
				} else {
					q.setParameter("DESC_ALTRO_2", null, Hibernate.STRING);
				}	
				
				if(rec!=null && rec.getDescAltro3()!=null) {
					rec.setDescAltro3(rec.getDescAltro3().replaceAll("'", "''"));
					q.setParameter("DESC_ALTRO_3", rec.getDescAltro3(), Hibernate.STRING);
				} else {
					q.setParameter("DESC_ALTRO_3", null, Hibernate.STRING);
				}					

				if (rec != null && rec.getDataDecisione2() != null) {
					q.setParameter("DATA_DECISIONE", rec.getDataDecisione2(), Hibernate.DATE);
				} else {
					q.setParameter("DATA_DECISIONE", null, Hibernate.DATE);
				}

				if (rec != null && rec.getDataDepDecis2() != null) {
					q.setParameter("DATA_DEPOSITO_DECISIONE", rec.getDataDepDecis2(), Hibernate.DATE);
				} else {
					q.setParameter("DATA_DEPOSITO_DECISIONE", null, Hibernate.DATE);
				}

				if (rec != null && rec.getDataDecisione3() != null) {
					q.setParameter("DATA_SENTENZA", rec.getDataDecisione3(), Hibernate.DATE);
				} else {
					q.setParameter("DATA_SENTENZA", null, Hibernate.DATE);
				}

				if (rec != null && rec.getDataDepDecis3() != null) {
					q.setParameter("DATA_DEPOSITO_SENTENZA", rec.getDataDepDecis3(), Hibernate.DATE);
				} else {
					q.setParameter("DATA_DEPOSITO_SENTENZA", null, Hibernate.DATE);
				}

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List<Map<?, ?>> l = q.list();
				return l;
			}
		});
	}	

	public TRelAttoIstGen getDecisorioBeniIstGen(TRelAttoIstGenId id) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TRelAttoIstGen.class);
		criteria.add(Restrictions.eq("id.codiKIdProc", id.getCodiKIdProc()));
		criteria.add(Restrictions.eq("id.codiKIdAtto", id.getCodiKIdAtto()));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", id.getCodiKIdUffOwner()));
		List<TRelAttoIstGen> risultati = getHibernateTemplate().findByCriteria(criteria);
		if(risultati!=null && !risultati.isEmpty()) return risultati.get(0);
		else return null;
	}	

	/*public void salvaDecisorioBeni2(final TRelBeneAtto bene) {

		getHibernateTemplate().execute(new HibernateCallback<Object>() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_BENE_salvaDecisorioBene2");
				q.setParameter("idAtto", bene.getId().getCodiKIdAtto(), Hibernate.LONG);
				q.setParameter("idBene", bene.getId().getCodiKIdBene(), Hibernate.LONG);
				q.setParameter("idSoggetto", bene.getId().getCodiKIdSoggTit(), Hibernate.LONG);
				q.setParameter("idProcedimento", bene.getId().getCodiKIdProc(), Hibernate.LONG);
				q.setParameter("idUfficio", bene.getId().getCodiKIdUffOwner(), Hibernate.INTEGER);
				q.setParameter("idDecisione", bene.getTTpEsitoByCodiFkIdEsito2().getCodiKIdEsito(), Hibernate.INTEGER);
				q.setParameter("idCodiceUtente", bene.getDescUtente(), Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List<Map<?, ?>> l = q.list();
				return l;
			}
		});
	}*/
	
	public void setMapperBean(Mapper mapperBean) {

		this.mapperBean = mapperBean;
	}

	public List<TRelProcBene> listaBeniPerProcedimento(long idProcedimento, int idUfficioUtente) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TRelProcBene.class);
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente));
		return getHibernateTemplate().findByCriteria(criteria);
	}

//    public List<TRelBeneAtto> getTRelBeneAtto(long idProcedimento, long idBene, Integer idUfficioUtente) {
//
//        DetachedCriteria criteria = DetachedCriteria.forClass(TRelBeneAtto.class);
//        criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente.intValue()));
//        criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
//        criteria.add(Restrictions.eq("id.codiKIdBene", idBene));
//        List<TRelBeneAtto> risultati = getHibernateTemplate().findByCriteria(criteria);
//        return risultati;
//    }
    
    public List<TRelBeneAtto> getTRelBeneAtto(long idProcedimento, long idBene, Integer idUfficioUtente, long idAtto) {

        DetachedCriteria criteria = DetachedCriteria.forClass(TRelBeneAtto.class);
        criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente.intValue()));
        criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
        if(idBene>0){
        	criteria.add(Restrictions.eq("id.codiKIdBene", idBene));
        }
        if(idAtto>0){
        	criteria.add(Restrictions.eq("id.codiKIdAtto", idAtto));
        }
        List<TRelBeneAtto> risultati = getHibernateTemplate().findByCriteria(criteria);
        return risultati;
    }

    public void eliminaTRelProcBene(long idProcedimento, long idBene, Integer idUfficioUtente) {

        DetachedCriteria criteria = DetachedCriteria.forClass(TRelProcBene.class);
        criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente.intValue()));
        criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
        if(idBene>0) {
        	criteria.add(Restrictions.eq("id.codiKIdBene", idBene));
        }
        List<TRelProcBene> risultati = getHibernateTemplate().findByCriteria(criteria);
        getHibernateTemplate().deleteAll(risultati);
    }

	public List<TRelAttoMisPatr> listaRelAttoMisPatr(long idPersona, long idProcedimento, Integer idUfficioUtente) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TRelAttoMisPatr.class);
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente.intValue()));
		criteria.add(Restrictions.eq("id.codiKIdSoggInt", idPersona));
		List<TRelAttoMisPatr> risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	public void eliminaBene(long idBene, Integer idUfficioUtente) {
        
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelProcBene.class);
        criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente.intValue()));
        criteria.add(Restrictions.eq("id.codiKIdBene", idBene));
        List<TRelProcBene> risultati = getHibernateTemplate().findByCriteria(criteria);
        if(risultati == null || risultati.size() == 0){
    		BeneForm form = new BeneForm();
    		TBene bene = getTBeneById(idBene, idUfficioUtente);
    		mapperBean.map(bene, form);
    		
    		//prima di cancellare in T_BENE cancello nelle tabelle relative alla categoria e alla sottoCategoria del bene
						//bene immobile    		
    		if(form.getTipologia().getId().equals("1")){
    	        criteria = DetachedCriteria.forClass(TBeneImmobile.class);
    	        criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente.intValue()));
    	        criteria.add(Restrictions.eq("id.codiKIdBeneImm", idBene));
    	        risultati = getHibernateTemplate().findByCriteria(criteria);
    	        getHibernateTemplate().deleteAll(risultati);
    		}
    					//bene finanziario
    		if(form.getTipologia().getId().equals("4")){
    	        criteria = DetachedCriteria.forClass(TBeneFin.class);
    	        criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente.intValue()));
    	        criteria.add(Restrictions.eq("id.codiKIdBeneFin", idBene));
    	        risultati = getHibernateTemplate().findByCriteria(criteria);
    	        getHibernateTemplate().deleteAll(risultati);
    		}
    					//azienda
    		if(form.getTipologia().getId().equals("5")){
    	        criteria = DetachedCriteria.forClass(TAzienda.class);
    	        criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente.intValue()));
    	        criteria.add(Restrictions.eq("id.codiKIdAzienda", idBene));
    	        risultati = getHibernateTemplate().findByCriteria(criteria);
    	        getHibernateTemplate().deleteAll(risultati);
    		}
    					//bene mobile registrato
    		if(form.getTipologia().getId().equals("3")){
    			if(form.getCategoria().getId().equals("10")){	//veicolo
        	        criteria = DetachedCriteria.forClass(TVeicolo.class);
        	        criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente.intValue()));
        	        criteria.add(Restrictions.eq("id.codiKIdVeicolo", idBene));
        	        risultati = getHibernateTemplate().findByCriteria(criteria);
        	        getHibernateTemplate().deleteAll(risultati);   				
    			}
    			if(form.getCategoria().getId().equals("11")){	//bene immateriale
        	        criteria = DetachedCriteria.forClass(TBeneImmat.class);
        	        criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente.intValue()));
        	        criteria.add(Restrictions.eq("id.codiKIdBeneImm", idBene));
        	        risultati = getHibernateTemplate().findByCriteria(criteria);
        	        getHibernateTemplate().deleteAll(risultati);   				    				
    			}
    		}
    					//bene mobile
    		if(form.getTipologia().getId().equals("2")){
    			if(form.getCategoria().getId().equals("8")){	//collezioni
        	        criteria = DetachedCriteria.forClass(TAnimali.class);
        	        criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente.intValue()));
        	        criteria.add(Restrictions.eq("id.codiKIdAnimale", idBene));
        	        risultati = getHibernateTemplate().findByCriteria(criteria);
        	        getHibernateTemplate().deleteAll(risultati);   				    				   				
    			}
    			if(form.getCategoria().getId().equals("7")){	//animali
        	        criteria = DetachedCriteria.forClass(TCollezioni.class);
        	        criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente.intValue()));
        	        criteria.add(Restrictions.eq("id.codiKIdColl", idBene));
        	        risultati = getHibernateTemplate().findByCriteria(criteria);
        	        getHibernateTemplate().deleteAll(risultati);   				    				   				    				
    			}
    			if(form.getCategoria().getId().equals("6")){	//denaro
    				if(form.getSottocategoria() != null && form.getSottocategoria().getId().equals("35")){	//contante
            	        criteria = DetachedCriteria.forClass(TContante.class);
            	        criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente.intValue()));
            	        criteria.add(Restrictions.eq("id.codiKIdContante", idBene));
            	        risultati = getHibernateTemplate().findByCriteria(criteria);
            	        getHibernateTemplate().deleteAll(risultati);   					
    				}
    				else{
            	        criteria = DetachedCriteria.forClass(TDenaro.class);
            	        criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente.intValue()));
            	        criteria.add(Restrictions.eq("id.codiKIdDenaro", idBene));
            	        risultati = getHibernateTemplate().findByCriteria(criteria);
            	        getHibernateTemplate().deleteAll(risultati);   					    					
    				}
    			}
    			if(form.getCategoria().getId().equals("9")){}	//altri oggetti
    		}
    			//cancello in T_BENE
	        criteria = DetachedCriteria.forClass(TBene.class);
	        criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente.intValue()));
	        criteria.add(Restrictions.eq("id.codiKIdBene", idBene));
	        risultati = getHibernateTemplate().findByCriteria(criteria);
	        getHibernateTemplate().deleteAll(risultati);

        }//fine if(risultati == null || risultati.size() == 0)		

	}//fine metodo

	public int countRelBeneAtto(long idSoggetto, long idProcedimento, Integer idUfficioUtente) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelBeneAtto.class);
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente.intValue()));
		criteria.add(Restrictions.eq("id.codiKIdSoggTit", idSoggetto));
		List<TRelBeneAtto> risultati = getHibernateTemplate().findByCriteria(criteria);

		return risultati.size();
	}
	
	
	public boolean isAssociato(long idBene, int idUfficioUtente,long idProcedimento) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelProcBene.class);
        criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente));
        criteria.add(Restrictions.eq("id.codiKIdBene", idBene));
        criteria.add(Restrictions.ne("id.codiKIdProc", idProcedimento));
        
        criteria.setProjection(Projections.rowCount());
        
        int count = DataAccessUtils.intResult(getHibernateTemplate().findByCriteria(criteria));
		return count>0;
	}
	
	
	
	public List<Map> modificaMobileRegistrato(final long vidUffOwn, final String vcodUtente, final long vidprocedimento, final long vidstatobene, final TBene bene, final TVeicolo veicolo,
			final TBeneImmat immateriale, final String vdescComune, final String vtipoStrada, final String vdesccategoria, final String vdescSottoCat, final long vidDisponibilita) {

		return (List<Map>) getHibernateTemplate().execute(new HibernateCallback<List<Map>>() {
			public List<Map> doInHibernate(final Session session) throws HibernateException, SQLException {

				String vnumeroRegistrazione = null;
				Date vdataRegistrazione = null;
				Date vdataScadenza = null;
				String vmodello = null;
				String vcodiceImmatricolazione = null;
				String vtarga = null;
				String vtelaio = null;
				String vcolore = null;
				String vnumeroIdentificazione = null;
				if ((bene != null) && (bene.getTTpCatBene() != null) && (bene.getTTpCatBene().getCodiKIdCatBene() == 10L)) {// se
					// il
					// MOBILE
					// REGISTRATO
					// è
					// un
					// VEICOLO
					if (veicolo.getDescReg() != null) {
						vnumeroRegistrazione = veicolo.getDescReg();
					}
					vdataRegistrazione = veicolo.getDataReg();
					vmodello = veicolo.getDescModello();
					if (veicolo.getDescImmatricolaz() != null) {
						vcodiceImmatricolazione = veicolo.getDescImmatricolaz();
					}
					vtarga = veicolo.getDescTarga();
					vtelaio = veicolo.getDescTelaio();
					vcolore = veicolo.getDescColore();
					if (veicolo.getDescIdentif() != null) {
						vnumeroIdentificazione = veicolo.getDescIdentif();
					}
				}
				if ((bene != null) && (bene.getTTpCatBene() != null) && (bene.getTTpCatBene().getCodiKIdCatBene() == 11L)) {// se
					// il
					// MOBILE
					// REGISTRATO
					// è
					// un
					// BENE
					// IMMATERIALE
					if (immateriale.getDescRegistrazione() != null) {
						vnumeroRegistrazione = immateriale.getDescRegistrazione();
					}
					if (immateriale.getDataRegistrazione() != null) {
						vdataRegistrazione = immateriale.getDataRegistrazione();
					}
					if (immateriale.getDataScadenza() != null) {
						vdataScadenza = immateriale.getDataScadenza();
					}
				}
				Query q = session.getNamedQuery("PG_BENE_modificaMobileRegistrato");

				q.setParameter("vidBene", bene.getId().getCodiKIdBene(), Hibernate.LONG);

				q.setParameter("vidUffOwn", vidUffOwn);
				q.setParameter("vdescUtente", vcodUtente);
				q.setParameter("vtipo", bene.getTTpTipoBene().getCodiKIdTipoBene());
				if ((bene.getTTpCatBene() != null) && (bene.getTTpCatBene().getCodiKIdCatBene() > 0)) {
					q.setParameter("vcategoria", bene.getTTpCatBene().getCodiKIdCatBene());
				} else {
					q.setParameter("vcategoria", null, Hibernate.LONG);
				}
				if ((bene.getTTpSottocatBene() != null) && (bene.getTTpSottocatBene().getCodiKIdSottocat() > 0)) {
					q.setParameter("vsottocategoria", bene.getTTpSottocatBene().getCodiKIdSottocat());
				} else {
					q.setParameter("vsottocategoria", null, Hibernate.INTEGER);
				}
				if (vidstatobene > 0) {
					q.setParameter("vidstatocons", vidstatobene);
				} else {
					q.setParameter("vidstatocons", null, Hibernate.LONG);
				}
				if (vidprocedimento > 0) {
					q.setParameter("vidprocedimento", vidprocedimento);
				} else {
					q.setParameter("vidprocedimento", null, Hibernate.LONG);
				}
				if ((bene.getTAnStato() != null) && (bene.getTAnStato().getCodiKIdStato() > 0)) {
					q.setParameter("vidstato", bene.getTAnStato().getCodiKIdStato());
				} else {
					q.setParameter("vidstato", null, Hibernate.INTEGER);
				}
				if ((bene.getTAnComuni() != null) && (bene.getTAnComuni().getCodiKIdComune() > 0)) {
					q.setParameter("vidcomune", bene.getTAnComuni().getCodiKIdComune());
				} else {
					q.setParameter("vidcomune", null, Hibernate.INTEGER);
				}
				if ((bene.getTTpTipoStrada() != null) && (bene.getTTpTipoStrada().getCodiKIdTipoStrada() > 0)) {
					q.setParameter("vidtipostrada", bene.getTTpTipoStrada().getCodiKIdTipoStrada());
				} else {
					q.setParameter("vidtipostrada", null, Hibernate.INTEGER);
				}
				q.setParameter("vdescindirizzo", bene.getDescIndirizzo());
				q.setParameter("vnumcivico", bene.getDescCivico());
				q.setParameter("vnumcap", bene.getDescCap());
				q.setParameter("vdesccomuneestero", bene.getDescComuneEstero());
				q.setParameter("vnote", bene.getDescNote());
				q.setParameter("vflagcostazienda", bene.getFlgCostAzienda());
				q.setParameter("vflaggravame", bene.getFlgGravame());
				if (vnumeroRegistrazione != null) {
					q.setParameter("vnumeroRegistrazione", vnumeroRegistrazione);
				} else {
					q.setParameter("vnumeroRegistrazione", null, Hibernate.STRING);
				}
				q.setParameter("vdataRegistrazione", vdataRegistrazione, Hibernate.DATE);
				if (vnumeroIdentificazione != null) {
					q.setParameter("vnumeroIdentificazione", vnumeroIdentificazione);
				} else {
					q.setParameter("vnumeroIdentificazione", null, Hibernate.STRING);
				}
				if (vcodiceImmatricolazione != null) {
					q.setParameter("vcodiceImmatricolazione", vcodiceImmatricolazione);
				} else {
					q.setParameter("vcodiceImmatricolazione", null, Hibernate.STRING);
				}
				q.setParameter("vtarga", vtarga);
				q.setParameter("vtelaio", vtelaio);
				q.setParameter("vcolore", vcolore);
				q.setParameter("vmodello", vmodello);
				if (vidDisponibilita > 0) {
					q.setParameter("vidDisponibilita", vidDisponibilita);
				} else {
					q.setParameter("vidDisponibilita", null, Hibernate.LONG);
				}
				if (vdataScadenza != null) {
					q.setParameter("vdataScadenza", vdataScadenza, Hibernate.DATE);
				} else {
					q.setParameter("vdataScadenza", null, Hibernate.DATE);
				}
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List<Map> l = q.list();
				return l;
			}
		});
	}
	
	
	
	public List<Map> modificaMobile(final long vidUffOwn, final String vcodUtente, final long vidprocedimento, final long vidstatocons, final TBene bene, final TAnimali animali, final TCollezioni collezioni, final String vdescComune, final String vtipoStrada, final String vdesccategoria, final String vdescSottoCat) {

	    return (List<Map>) getHibernateTemplate().execute(new HibernateCallback<List<Map>>() {
            public List<Map> doInHibernate(final Session session) throws HibernateException, SQLException {

				long vidstato = 0;
				long vidcomune = 0;
				long vidtipostrada = 0;
				String vdescindirizzo = null;
				String vnumcivico = null;
				String vnumcap = null;
				String vdesccomuneestero = null;
				String vdescrizioneCapo = null;
				long vnumeroAnimali = 0;
				String vdescrizioneColl = null;
				long vnumeroPezzi = 0;
				String vubicazioneColl = null;
				if ((bene.getTAnStato() != null) && (bene.getTAnStato().getCodiKIdStato() > 0)) {
					vidstato = bene.getTAnStato().getCodiKIdStato();
				}
				if ((bene.getTAnComuni() != null) && (bene.getTAnComuni().getCodiKIdComune() > 0)) {
					vidcomune = bene.getTAnComuni().getCodiKIdComune();
				}
				if ((bene.getTTpTipoStrada() != null) && (bene.getTTpTipoStrada().getCodiKIdTipoStrada() > 0)) {
					vidtipostrada = bene.getTTpTipoStrada().getCodiKIdTipoStrada();
				}
				vdesccomuneestero = bene.getDescComuneEstero();
				vdescindirizzo = bene.getDescIndirizzo();
				vnumcivico = bene.getDescCivico();
				vnumcap = bene.getDescCap();
				
				if((bene.getTTpCatBene() != null)){
					if(ComparatorUtil.containsValue(bene.getTTpCatBene().getCodiKIdCatBene(), Constants.CategoriaBeni.ID_TIPO_ANIMALI)){
						vdescrizioneCapo = animali.getDescCapo();
						if (animali.getNumeAnimali() != null) {
							vnumeroAnimali = animali.getNumeAnimali();
						}
					}
					
					if(ComparatorUtil.containsValue(bene.getTTpCatBene().getCodiKIdCatBene(), Constants.CategoriaBeni.ID_TIPO_COLLEZIONE)){
						vdescrizioneColl = collezioni.getDescCollezione();
						vnumeroPezzi = collezioni.getNumePezzi();
						vubicazioneColl = "";
					}
					
					//Giacomo 28/08/2013 Aggiunto gestione Altri Oggetti
					if(ComparatorUtil.containsValue(bene.getTTpCatBene().getCodiKIdCatBene(), Constants.CategoriaBeni.ID_ALTRI_OGGETTI)){
						vdescrizioneColl = bene.getDescrBene();
					}
				}

				Query q = session.getNamedQuery("PG_BENE_modificaMobile");
				q.setParameter("vidBene", bene.getId().getCodiKIdBene(), Hibernate.LONG);
				
				q.setParameter("vidUffOwn", vidUffOwn);
				q.setParameter("vdescUtente", vcodUtente);
				q.setParameter("vtipo", bene.getTTpTipoBene().getCodiKIdTipoBene());
				if ((bene.getTTpCatBene() != null) && (bene.getTTpCatBene().getCodiKIdCatBene() > 0)) {
					q.setParameter("vcategoria", bene.getTTpCatBene().getCodiKIdCatBene());
				} else {
					q.setParameter("vcategoria", null, Hibernate.LONG);
				}
				if ((bene.getTTpSottocatBene() != null) && (bene.getTTpSottocatBene().getCodiKIdSottocat() > 0)) {
					q.setParameter("vsottocategoria", bene.getTTpSottocatBene().getCodiKIdSottocat());
				} else {
					q.setParameter("vsottocategoria", null, Hibernate.LONG);
				}
				if (vidstato > 0) {
					q.setParameter("vidstato", vidstato);
				} else {
					q.setParameter("vidstato", null, Hibernate.LONG);
				}
				if (vidcomune > 0) {
					q.setParameter("vidcomune", vidcomune);
				} else {
					q.setParameter("vidcomune", null, Hibernate.LONG);
				}
				if (vidtipostrada > 0) {
					q.setParameter("vidtipostrada", vidtipostrada);
				} else {
					q.setParameter("vidtipostrada", null, Hibernate.LONG);
				}
				q.setParameter("vdescindirizzo", vdescindirizzo);
				q.setParameter("vnumcivico", vnumcivico);
				q.setParameter("vnumcap", vnumcap);
				q.setParameter("vdesccomuneestero", vdesccomuneestero);
				q.setParameter("vnote", bene.getDescNote());
				q.setParameter("vflaggravame", bene.getFlgGravame());
				if (vidstatocons > 0) {
					q.setParameter("vidstatocons", vidstatocons);
				} else {
					q.setParameter("vidstatocons", null, Hibernate.LONG);
				}
				q.setParameter("vidprocedimento", vidprocedimento);
				q.setParameter("vdescComune", vdescComune);
				q.setParameter("vtipoStrada", vtipoStrada);
				q.setParameter("vdesccategoria", vdesccategoria);
				q.setParameter("vdescSottoCat", vdescSottoCat);
				q.setParameter("vdescrizioneCapo", vdescrizioneCapo);
				if (vnumeroAnimali > 0) {
					q.setParameter("vnumeroAnimali", vnumeroAnimali);
				} else {
					q.setParameter("vnumeroAnimali", null, Hibernate.LONG);
				}
				q.setParameter("vdescColl", vdescrizioneColl);
				if (vnumeroPezzi > 0) {
					q.setParameter("vnumPezzi", vnumeroPezzi);
				} else {
					q.setParameter("vnumPezzi", null, Hibernate.LONG);
				}
				q.setParameter("vubicazione", vubicazioneColl);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List<Map> l = q.list();
				return l;
			}
		});
	}
	
	
	public List<Map> modificaDenaroContante(final long vidUffOwn, final String vcodUtente, final long vidprocedimento, final long vidstatobene, final TBene bene, final TDenaro denaro, final TContante contante) {

        return (List<Map>) getHibernateTemplate().execute(new HibernateCallback<List<Map>>() {

            public List<Map> doInHibernate(final Session session) throws HibernateException, SQLException {

                String sottocategoria = "";
                if ((bene.getTTpSottocatBene() != null) && (bene.getTTpSottocatBene().getCodiKIdSottocat() > 0)) {
                    sottocategoria = String.valueOf(bene.getTTpSottocatBene().getCodiKIdSottocat());
                }
                // Per qualsiasi tipo di bene Denaro
                String codAbi = null;
                String descEnte = null;
                String codCab = null;
                String codIban = null;
                String codSwift = null;
                String flgVersamento = null;
                String numEnte = "";
                BigDecimal valutaEur = null;
                String tipoVal = null;
                BigDecimal valutaEst = null;
                // Solo per Bene Denaro non Contante
                String descIndirizzo = null;
                String direttEnte = null;
                // Solo per Bene Denaro Contante
                String descIntestatario = null;
                long numeroModTrib = 0;
                String bollettino = null;
                
                if(!sottocategoria.equalsIgnoreCase("35") && !sottocategoria.equalsIgnoreCase("206")){
                    // ************************** INIZIO VARIABILI  BENE DENARO
                    // *********************************
                    descEnte = denaro.getDescEnteDepos();
                    descIndirizzo = bene.getDescIndirizzo();
                    direttEnte = denaro.getDescDirettore();
                    if (denaro.getDescEnteDepos() != null)
                        numEnte = denaro.getDescIdentEnte();
                    valutaEst = denaro.getImpoValuta();
                    valutaEur = denaro.getImpoEuro();
                    if ((denaro.getTTpValuta() != null) && (denaro.getTTpValuta().getCodiKIdValuta() != null &&! denaro.getTTpValuta().getCodiKIdValuta().equals("-1"))) {
                        tipoVal = denaro.getTTpValuta().getCodiKIdValuta();
                    }
                    if (denaro.getTAnOperFinanz() != null && (denaro.getTAnOperFinanz().getId() != null)) {
                        TAnOperFinanzId id = denaro.getTAnOperFinanz().getId();
                        codAbi = id.getCodiKIdAbi();
                        codCab = id.getCodiKIdCab();
                    }
                    if (denaro.getDescIban() != null){
                        codIban = denaro.getDescIban();
                    }
                    if(denaro.getDescBicSwift()!=null){
                    	codSwift=denaro.getDescBicSwift();
                    }
                    // ************************** FINE VARIABILI BENE DENARO
                    // *********************************
                } 
                else{
                    // ************************** INIZIO VARIABILI BENE DENARO CONTANTE
                    // *********************************
                    descEnte = contante.getDescEnteDepos();
                    //descIndirizzo non c'è
                    //direttEnte non c'è
                    descIndirizzo = contante.getDescIntestatario();
                    if (contante.getDescIdentEnte() != null)
                        numEnte = contante.getDescIdentEnte();
                    valutaEst = contante.getImpoValuta();
                    valutaEur = contante.getImpoEuro();
                    if ((contante.getTTpValuta() != null) && (!contante.getTTpValuta().getCodiKIdValuta().equals("-1"))) {
                        tipoVal = contante.getTTpValuta().getCodiKIdValuta();
                    }
                    if (contante.getTAnOperFinanz() != null && (contante.getTAnOperFinanz().getId() != null)) {
                        TAnOperFinanzId id = contante.getTAnOperFinanz().getId();
                        codAbi = id.getCodiKIdAbi();
                        codCab = id.getCodiKIdCab();
                    }
                    if (contante.getDescIban() != null){
                        codIban = contante.getDescIban();
                    }
                    if (contante.getDescBicSwift()!= null){
                        codSwift = contante.getDescBicSwift();
                    }
                    if (contante.getFlgVersamento() != null && !contante.getFlgVersamento().equals("-1")) {
                    	flgVersamento = contante.getFlgVersamento();
                    }
                    if(contante.getDescIntestatario()!=null){
                        descIntestatario = contante.getDescIntestatario();
                    }
                    if(contante.getNumeIdentModTrib()!=null){
                        numeroModTrib = contante.getNumeIdentModTrib();
                    }
                    if(contante.getDescBollettino()!=null){
                        bollettino = contante.getDescBollettino();
                    }
                    // ************************** FINE VARIABILI BENE DENARO CONTANTE
                    // *********************************
                }
                Query q = session.getNamedQuery("PG_BENE_modificaDenaro_Contante");
                // *************************** PARAMETRI BENE
                // *************************************
                q.setParameter("vidBene", bene.getId().getCodiKIdBene(), Hibernate.LONG);
                q.setParameter("vidUffOwn", vidUffOwn);
                q.setParameter("vdescUtente", vcodUtente);
                q.setParameter("vtipo", bene.getTTpTipoBene().getCodiKIdTipoBene());
                if ((bene.getTTpCatBene() != null) && (bene.getTTpCatBene().getCodiKIdCatBene() > 0)) {
                    q.setParameter("vcategoria", bene.getTTpCatBene().getCodiKIdCatBene(), Hibernate.INTEGER);
                } else {
                    q.setParameter("vcategoria", null, Hibernate.INTEGER);
                }
                if ((bene.getTTpSottocatBene() != null) && (bene.getTTpSottocatBene().getCodiKIdSottocat() > 0)) {
                    q.setParameter("vsottocategoria", bene.getTTpSottocatBene().getCodiKIdSottocat(), Hibernate.INTEGER);
                } else {
                    q.setParameter("vsottocategoria", null, Hibernate.INTEGER);
                }
                if (vidstatobene > 0) {
                    q.setParameter("vidstatocons", vidstatobene, Hibernate.LONG);
                } else {
                    q.setParameter("vidstatocons", null, Hibernate.LONG);
                }
                if (vidprocedimento > 0) {
                    q.setParameter("vidprocedimento", vidprocedimento, Hibernate.LONG);
                } else {
                    q.setParameter("vidprocedimento", null, Hibernate.LONG);
                }
                if ((bene.getTAnStato() != null) && (bene.getTAnStato().getCodiKIdStato() > 0)) {
                    q.setParameter("vidstato", bene.getTAnStato().getCodiKIdStato(), Hibernate.INTEGER);
                } else {
                    q.setParameter("vidstato", null, Hibernate.INTEGER);
                }
                if ((bene.getTAnComuni() != null) && (bene.getTAnComuni().getCodiKIdComune() > 0)) {
                    q.setParameter("vidcomune", bene.getTAnComuni().getCodiKIdComune(), Hibernate.INTEGER);
                } else {
                    q.setParameter("vidcomune", null, Hibernate.INTEGER);
                }
                if ((bene.getTTpTipoStrada() != null) && (bene.getTTpTipoStrada().getCodiKIdTipoStrada() > 0)) {
                    q.setParameter("vidtipostrada", bene.getTTpTipoStrada().getCodiKIdTipoStrada(), Hibernate.INTEGER);
                } else {
                    q.setParameter("vidtipostrada", null, Hibernate.INTEGER);
                }
                q.setParameter("vdescindirizzo", bene.getDescIndirizzo());
                q.setParameter("vnumcivico", bene.getDescCivico());
                q.setParameter("vnumcap", bene.getDescCap());
                q.setParameter("vdesccomuneestero", bene.getDescComuneEstero());
                q.setParameter("vflagcostazienda", bene.getFlgCostAzienda());
                q.setParameter("vnote", bene.getDescNote());
                q.setParameter("vflaggravame", bene.getFlgGravame());
                // *************************** PARAMETRI BENE
                // *************************************
                // *************************** PARAMETRI BENE DENARO - CONTANTE
                // *************************************
                q.setParameter("vdescente", descEnte);
                q.setParameter("vdescindirizzoDen", descIndirizzo);
                q.setParameter("vdirettEnte", direttEnte);
                if (numEnte != null) {
                    q.setParameter("vnumident", numEnte, Hibernate.STRING);
                } else {
                    q.setParameter("vnumident", null, Hibernate.STRING);
                }
                if (valutaEst != null) {
                    q.setParameter("vvalutaest", valutaEst, Hibernate.BIG_DECIMAL);
                } else {
                    q.setParameter("vvalutaest", null, Hibernate.BIG_DECIMAL);
                }
                q.setParameter("vtipoval", tipoVal);
                if (valutaEur != null) {
                    q.setParameter("vvalutaeur", valutaEur, Hibernate.BIG_DECIMAL);
                } else {
                    q.setParameter("vvalutaeur", null, Hibernate.BIG_DECIMAL);
                }
                q.setParameter("vcodabi", codAbi);
                if (codCab == null || codCab.equals("0"))
                    q.setParameter("vcodcab", null, Hibernate.STRING);
                else q.setParameter("vcodcab", codCab);
                q.setParameter("vcodiban", codIban);
                q.setParameter("vcodswift", codSwift);
                if (flgVersamento != null) {
                    q.setParameter("flgVersamento", flgVersamento, Hibernate.STRING);
                } else {
                    q.setParameter("flgVersamento", null, Hibernate.STRING);
                }
                q.setParameter("vintestatario", descIntestatario);
                if (numeroModTrib > 0) {
                    q.setParameter("vnummodtrib", numeroModTrib, Hibernate.LONG);
                } else {
                    q.setParameter("vnummodtrib", null, Hibernate.LONG);
                }
                q.setParameter("vbollettino", bollettino);
                // *************************** PARAMETRI BENE DENARO
                // *************************************
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List<Map> l = q.list();
                return l;
            }
        });
    }
	
	
	public List<Map> venditaBene(final long vidUffOwn, final String vcodUtente, final long vidprocedimento, final long vidstatobene, final TBene bene, final TDenaro denaro, final String numeroBollettino) {

        return (List<Map>) getHibernateTemplate().execute(new HibernateCallback<List<Map>>() {

            public List<Map> doInHibernate(final Session session) throws HibernateException, SQLException {

                // Per qualsiasi tipo di bene Denaro
                String codAbi = null;
                String descEnte = null;
                String codCab = null;
                String codIban = null;
                String codSwift = null;
                String numEnte = "";
                BigDecimal valutaEur = null;
                String tipoVal = null;
                BigDecimal valutaEst = null;
                // Solo per Bene Denaro non Contante
                String descIndirizzo = null;
                String direttEnte = null;
                // Solo per Bene Denaro Contante
                String descIntestatario = null;
                long numeroModTrib = 0;
				String bollettino = numeroBollettino;
                

                // ************************** INIZIO VARIABILI  BENE DENARO *********************************
                descEnte = denaro.getDescEnteDepos();
                descIndirizzo = bene.getDescIndirizzo();
                direttEnte = denaro.getDescDirettore();
                if (denaro.getDescEnteDepos() != null)
                    numEnte = denaro.getDescIdentEnte();
                valutaEst = denaro.getImpoValuta();
                valutaEur = denaro.getImpoEuro();
                if ((denaro.getTTpValuta() != null) && (denaro.getTTpValuta().getCodiKIdValuta() != null &&! denaro.getTTpValuta().getCodiKIdValuta().equals("-1"))) {
                    tipoVal = denaro.getTTpValuta().getCodiKIdValuta();
                }
                if (denaro.getTAnOperFinanz() != null && (denaro.getTAnOperFinanz().getId() != null)) {
                    TAnOperFinanzId id = denaro.getTAnOperFinanz().getId();
                    codAbi = id.getCodiKIdAbi();
                    codCab = id.getCodiKIdCab();
                }
                if (denaro.getDescIban() != null){
                    codIban = denaro.getDescIban();
                }
                if(denaro.getDescBicSwift()!=null){
                	codSwift=denaro.getDescBicSwift();
                }
                // ************************** FINE VARIABILI BENE DENARO *********************************
                
                
                Query q = session.getNamedQuery("PG_BENE_venditaBene");
                // *************************** PARAMETRI BENE *************************************
                q.setParameter("vidBene", bene.getId().getCodiKIdBene(), Hibernate.LONG);
               
                q.setParameter("vidUffOwn", vidUffOwn);
                q.setParameter("vdescUtente", vcodUtente);
                q.setParameter("vtipo", bene.getTTpTipoBene().getCodiKIdTipoBene());
                if ((bene.getTTpCatBene() != null) && (bene.getTTpCatBene().getCodiKIdCatBene() > 0)) {
                    q.setParameter("vcategoria", bene.getTTpCatBene().getCodiKIdCatBene(), Hibernate.INTEGER);
                } else {
                    q.setParameter("vcategoria", null, Hibernate.INTEGER);
                }
                if ((bene.getTTpSottocatBene() != null) && (bene.getTTpSottocatBene().getCodiKIdSottocat() > 0)) {
                    q.setParameter("vsottocategoria", bene.getTTpSottocatBene().getCodiKIdSottocat(), Hibernate.INTEGER);
                } else {
                    q.setParameter("vsottocategoria", null, Hibernate.INTEGER);
                }
                if (vidstatobene > 0) {
                    q.setParameter("vidstatocons", vidstatobene, Hibernate.LONG);
                } else {
                    q.setParameter("vidstatocons", null, Hibernate.LONG);
                }
                if (vidprocedimento > 0) {
                    q.setParameter("vidprocedimento", vidprocedimento, Hibernate.LONG);
                } else {
                    q.setParameter("vidprocedimento", null, Hibernate.LONG);
                }
                if ((bene.getTAnStato() != null) && (bene.getTAnStato().getCodiKIdStato() > 0)) {
                    q.setParameter("vidstato", bene.getTAnStato().getCodiKIdStato(), Hibernate.INTEGER);
                } else {
                    q.setParameter("vidstato", null, Hibernate.INTEGER);
                }
                if ((bene.getTAnComuni() != null) && (bene.getTAnComuni().getCodiKIdComune() > 0)) {
                    q.setParameter("vidcomune", bene.getTAnComuni().getCodiKIdComune(), Hibernate.INTEGER);
                } else {
                    q.setParameter("vidcomune", null, Hibernate.INTEGER);
                }
                if ((bene.getTTpTipoStrada() != null) && (bene.getTTpTipoStrada().getCodiKIdTipoStrada() > 0)) {
                    q.setParameter("vidtipostrada", bene.getTTpTipoStrada().getCodiKIdTipoStrada(), Hibernate.INTEGER);
                } else {
                    q.setParameter("vidtipostrada", null, Hibernate.INTEGER);
                }
                q.setParameter("vdescindirizzo", bene.getDescIndirizzo());
                q.setParameter("vnumcivico", bene.getDescCivico());
                q.setParameter("vnumcap", bene.getDescCap());
                q.setParameter("vdesccomuneestero", bene.getDescComuneEstero());
                q.setParameter("vflagcostazienda", bene.getFlgCostAzienda());
                q.setParameter("vnote", bene.getDescNote());
                q.setParameter("vflaggravame", bene.getFlgGravame());
                // *************************** PARAMETRI BENE
                // *************************************
                // *************************** PARAMETRI BENE DENARO - CONTANTE
                // *************************************
                q.setParameter("vdescente", descEnte);
                q.setParameter("vdescindirizzoDen", descIndirizzo);
                q.setParameter("vdirettEnte", direttEnte);
                if (numEnte != null) {
                    q.setParameter("vnumident", numEnte, Hibernate.STRING);
                } else {
                    q.setParameter("vnumident", null, Hibernate.STRING);
                }
                if (valutaEst != null) {
                    q.setParameter("vvalutaest", valutaEst, Hibernate.BIG_DECIMAL);
                } else {
                    q.setParameter("vvalutaest", null, Hibernate.BIG_DECIMAL);
                }
                q.setParameter("vtipoval", tipoVal);
                if (valutaEur != null) {
                    q.setParameter("vvalutaeur", valutaEur, Hibernate.BIG_DECIMAL);
                } else {
                    q.setParameter("vvalutaeur", null, Hibernate.BIG_DECIMAL);
                }
                q.setParameter("vcodabi", codAbi);
                if (codCab == null || codCab.equals("0"))
                    q.setParameter("vcodcab", null, Hibernate.STRING);
                else q.setParameter("vcodcab", codCab);
                q.setParameter("vcodiban", codIban);
                q.setParameter("vcodswift", codSwift);
                q.setParameter("vintestatario", descIntestatario);
                if (numeroModTrib > 0) {
                    q.setParameter("vnummodtrib", numeroModTrib, Hibernate.LONG);
                } else {
                    q.setParameter("vnummodtrib", null, Hibernate.LONG);
                }
                q.setParameter("vbollettino", bollettino);
                // *************************** PARAMETRI BENE DENARO
                // *************************************
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List<Map> l = q.list();
                return l;
            }
        });
    }
	
	
	public List<Map> modificaFinanziario(final long vidUffOwn, final String vcodUtente, final long vidprocedimento, final long vidstatobene, final TBene bene, final TBeneFin finanziario) {

		return (List<Map>) getHibernateTemplate().execute(new HibernateCallback<List<Map>>() {

			public List<Map> doInHibernate(final Session session) throws HibernateException, SQLException {

				// ************************** VARIABILI BENE FIN
				// *********************************
				String descEnte = null;
				String descIndirizzo = null;
				String direttEnte = null;
				String numIdent = null;
				BigDecimal valutaEst = null;
				BigDecimal valutaEur = null;
				String tipoVal = null;
				String codAbi = null;
				String codCab = null;
				String codIban = null;
				String codSwift=null;
				descEnte = finanziario.getDescEnteCreditizio();
				descIndirizzo = bene.getDescIndirizzo();
				direttEnte = finanziario.getDescDirettoreEnte();
				if (finanziario.getDescRegistrazione() != null)
					numIdent = finanziario.getDescRegistrazione();
				valutaEst = finanziario.getImpoValuta();
				valutaEur = finanziario.getImpoEuro();
				if ((finanziario.getTTpValuta() != null) && (finanziario.getTTpValuta().getCodiKIdValuta() != null &&! finanziario.getTTpValuta().getCodiKIdValuta().equals("-1"))) {
					tipoVal = finanziario.getTTpValuta().getCodiKIdValuta();
				}
				if (finanziario.getTAnOperFinanz() != null && (finanziario.getTAnOperFinanz().getId() != null)) {
					TAnOperFinanzId id = finanziario.getTAnOperFinanz().getId();
					codAbi = id.getCodiKIdAbi();
					codCab = id.getCodiKIdCab();
				}
				if (finanziario.getDescIban() != null){
					codIban = finanziario.getDescIban();
				}
				if(finanziario.getDescBicSwift()!=null){
					codSwift=finanziario.getDescBicSwift();
				}
				// ************************** VARIABILI BENE FIN
				// *********************************
				Query q = session.getNamedQuery("PG_BENE_modificaFinanziario");
				// *************************** PARAMETRI BENE
				q.setParameter("vidBene", bene.getId().getCodiKIdBene(), Hibernate.LONG);
				 
				q.setParameter("vidUffOwn", vidUffOwn);
				q.setParameter("vdescUtente", vcodUtente);
				q.setParameter("vtipo", bene.getTTpTipoBene().getCodiKIdTipoBene());
				if ((bene.getTTpCatBene() != null) && (bene.getTTpCatBene().getCodiKIdCatBene() > 0)) {
					q.setParameter("vcategoria", bene.getTTpCatBene().getCodiKIdCatBene(), Hibernate.INTEGER);
				} else {
					q.setParameter("vcategoria", null, Hibernate.INTEGER);
				}
				if ((bene.getTTpSottocatBene() != null) && (bene.getTTpSottocatBene().getCodiKIdSottocat() > 0)) {
					q.setParameter("vsottocategoria", bene.getTTpSottocatBene().getCodiKIdSottocat(), Hibernate.INTEGER);
				} else {
					q.setParameter("vsottocategoria", null, Hibernate.INTEGER);
				}
				if (vidstatobene > 0) {
					q.setParameter("vidstatocons", vidstatobene, Hibernate.LONG);
				} else {
					q.setParameter("vidstatocons", null, Hibernate.LONG);
				}
				if (vidprocedimento > 0) {
					q.setParameter("vidprocedimento", vidprocedimento, Hibernate.LONG);
				} else {
					q.setParameter("vidprocedimento", null, Hibernate.LONG);
				}
				if ((bene.getTAnStato() != null) && (bene.getTAnStato().getCodiKIdStato() > 0)) {
					q.setParameter("vidstato", bene.getTAnStato().getCodiKIdStato(), Hibernate.INTEGER);
				} else {
					q.setParameter("vidstato", null, Hibernate.INTEGER);
				}
				if ((bene.getTAnComuni() != null) && (bene.getTAnComuni().getCodiKIdComune() > 0)) {
					q.setParameter("vidcomune", bene.getTAnComuni().getCodiKIdComune(), Hibernate.INTEGER);
				} else {
					q.setParameter("vidcomune", null, Hibernate.INTEGER);
				}
				if ((bene.getTTpTipoStrada() != null) && (bene.getTTpTipoStrada().getCodiKIdTipoStrada() > 0)) {
					q.setParameter("vidtipostrada", bene.getTTpTipoStrada().getCodiKIdTipoStrada(), Hibernate.INTEGER);
				} else {
					q.setParameter("vidtipostrada", null, Hibernate.INTEGER);
				}
				q.setParameter("vdescindirizzo", bene.getDescIndirizzo());
				q.setParameter("vnumcivico", bene.getDescCivico());
				q.setParameter("vnumcap", bene.getDescCap());
				q.setParameter("vdesccomuneestero", bene.getDescComuneEstero());
				q.setParameter("vflagcostazienda", bene.getFlgCostAzienda());
				q.setParameter("vnote", bene.getDescNote());
				q.setParameter("vflaggravame", bene.getFlgGravame());
				// *************************** PARAMETRI BENE
				// *************************************
				// *************************** PARAMETRI BENE_FIN
				// *************************************
				q.setParameter("vdescente", descEnte);
				q.setParameter("vdescindirizzoFin", descIndirizzo);
				q.setParameter("vdirettEnte", direttEnte);
				if (numIdent != null) {
					q.setParameter("vnumident", numIdent, Hibernate.STRING);
				} else {
					q.setParameter("vnumident", null, Hibernate.STRING);
				}
				if (valutaEst != null) {
					q.setParameter("vvalutaest", valutaEst, Hibernate.BIG_DECIMAL);
				} else {
					q.setParameter("vvalutaest", null, Hibernate.BIG_DECIMAL);
				}
				q.setParameter("vtipoval", tipoVal);
				if (valutaEur != null) {
					q.setParameter("vvalutaeur", valutaEur, Hibernate.BIG_DECIMAL);
				} else {
					q.setParameter("vvalutaeur", null, Hibernate.BIG_DECIMAL);
				}
				q.setParameter("vcodabi", codAbi);
				if (codCab == null || codCab.equals("0"))
					q.setParameter("vcodcab", null, Hibernate.STRING);
				else q.setParameter("vcodcab", codCab);
				q.setParameter("vcodiban", codIban);
				q.setParameter("vcodswift", codSwift);
				// *************************** PARAMETRI BENE_FIN
				// *************************************
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List<Map> l = q.list();
				return l;
			}
		});
	}
	
	
	public void trasformaBene(final long idProcedimento, final long idBeneVecchio, final long idBeneNuovo, final long vidUffOwn, final String vcodUtente, final Character vidMotivo, final Integer vidTipoTrasformazione, final Long vidatto) {
		getHibernateTemplate().execute(new HibernateCallback<Void>() {
//:,:,:idBeneNuovo,:vidUffOwn,:descUtente,:vidMotivo,:vidTipoTrasformazione,:vidatto
			
			public Void doInHibernate(Session session) throws HibernateException, SQLException {
				Query q = session.getNamedQuery("PG_BENE_TRASFORMA_BENE");
				// *************************** PARAMETRI BENE
				q.setParameter("vidprocedimento", idProcedimento, Hibernate.LONG);
				q.setParameter("idBeneVecchio", idBeneVecchio, Hibernate.LONG);
				q.setParameter("idBeneNuovo", idBeneNuovo, Hibernate.LONG);
				 
				q.setParameter("vidUffOwn", vidUffOwn);
				
				q.setParameter("descUtente", vcodUtente);
				if(vidMotivo==null){
					q.setParameter("vidMotivo", null, Hibernate.CHARACTER);
				}else{
					q.setParameter("vidMotivo", vidMotivo);
				}
				
				
				if(vidTipoTrasformazione==null){
					q.setParameter("vidTipoTrasformazione", null, Hibernate.INTEGER);
				}else{
					q.setParameter("vidTipoTrasformazione", vidTipoTrasformazione);
				}
				
				if(vidatto==null){
					q.setParameter("vidatto", null, Hibernate.LONG);
				}else{
					q.setParameter("vidatto", vidatto);
				}
				
				
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				q.list();
				return null;
			}
			
		});
	}
	
	public void erroreMaterialeBene(final long idProcedimento, final long idBeneVecchio, final long idBeneNuovo, final long vidUffOwn, final String vcodUtente, final Character vidMotivo, final Integer vidTipoTrasformazione, final Long vidatto) {
		getHibernateTemplate().execute(new HibernateCallback<Void>() {
//:,:,:idBeneNuovo,:vidUffOwn,:descUtente,:vidMotivo,:vidTipoTrasformazione,:vidatto
			
			public Void doInHibernate(Session session) throws HibernateException, SQLException {
				Query q = session.getNamedQuery("PG_BENE_ERRORE_MATERIALE_BENE");
				// *************************** PARAMETRI BENE
				q.setParameter("vidprocedimento", idProcedimento, Hibernate.LONG);
				q.setParameter("idBeneVecchio", idBeneVecchio, Hibernate.LONG);
				q.setParameter("idBeneNuovo", idBeneNuovo, Hibernate.LONG);
				 
				q.setParameter("vidUffOwn", vidUffOwn);
				
				q.setParameter("descUtente", vcodUtente);
				if(vidMotivo==null){
					q.setParameter("vidMotivo", null, Hibernate.CHARACTER);
				}else{
					q.setParameter("vidMotivo", vidMotivo);
				}
				
				
				if(vidTipoTrasformazione==null){
					q.setParameter("vidTipoTrasformazione", null, Hibernate.INTEGER);
				}else{
					q.setParameter("vidTipoTrasformazione", vidTipoTrasformazione);
				}
				
				if(vidatto==null){
					q.setParameter("vidatto", null, Hibernate.LONG);
				}else{
					q.setParameter("vidatto", vidatto);
				}
				
				
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				q.list();
				return null;
			}
			
		});
	}
	
	public List<TRelProcBene> dettaglioRevocaRelProcBene(final long idAttoRevoca, final long idProcedimento, final String descUtente, final int idUffOwner, final String nomeMetodo) {

		return (List<TRelProcBene>)	getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				
				Query q = session.getNamedQuery("PG_BENE_dettaglioRevocaProcBene");
				q.setParameter("idAttoRevoca", idAttoRevoca);
				q.setParameter("idProcedimento", idProcedimento);
				q.setParameter("descUtente", descUtente);
				q.setParameter("idUffOwner", idUffOwner);
				q.setParameter("nomeMetodo", nomeMetodo);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});
	}
	
	
	public List<TRelProcBene> elencoRestBeniRelProcBene( final long idProcedimento, final String descUtente, final int idUffOwner, final String nomeMetodo) {

		return (List<TRelProcBene>)	getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				
				Query q = session.getNamedQuery("PG_BENE_elencoRestBeniRelProcBene");
				q.setParameter("idProcedimento", idProcedimento);
				q.setParameter("descUtente", descUtente);
				q.setParameter("idUffOwner", idUffOwner);
				q.setParameter("nomeMetodo", nomeMetodo);
				
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});
	}	
	
	public List<TBene> dettaglioRevocaTBene(final long idAttoRevoca, final long idProcedimento, final String descUtente, final int idUffOwner, final String nomeMetodo,
			final long idSoggettoDiretto, final long idSoggettoTitolare, final long idTipologia, final long idCategoria, final long idSottocategoria) {

		return (List<TBene>)	getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				
				Query q = session.getNamedQuery("PG_BENE_dettaglioRevocaTBene");
				q.setParameter("idAttoRevoca", idAttoRevoca);
				q.setParameter("idProcedimento", idProcedimento);
				q.setParameter("descUtente", descUtente);
				q.setParameter("idUffOwner", idUffOwner);
				q.setParameter("nomeMetodo", nomeMetodo);
			  	if (idSoggettoDiretto > 0) {
					q.setParameter("idSoggettoDiretto", idSoggettoDiretto);
				} else {
					q.setParameter("idSoggettoDiretto", null, Hibernate.LONG);
				}
				if (idSoggettoTitolare > 0) {
					q.setParameter("idSoggettoTitolare", idSoggettoTitolare);
				} else {
					q.setParameter("idSoggettoTitolare", null, Hibernate.LONG);
				}
				if (idTipologia > 0) {
					q.setParameter("idTipologia", idTipologia);
				} else {
					q.setParameter("idTipologia", null, Hibernate.LONG);
				}
				if (idCategoria > 0) {
					q.setParameter("idCategoria", idCategoria);
				} else {
					q.setParameter("idCategoria", null, Hibernate.LONG);
				}
				if (idSottocategoria > 0) {
					q.setParameter("idSottocategoria", idSottocategoria);
				} else {
					q.setParameter("idSottocategoria", null, Hibernate.LONG);
				}

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});
	}
	

	public List<TBene> dettaglioOpposTBene(final long idAttoRevoca, final long idProcedimento, final String descUtente, final int idUffOwner, final String nomeMetodo,
			final long idSoggettoDiretto, final long idSoggettoTitolare, final long idTipologia, final long idCategoria, final long idSottocategoria) {

		return (List<TBene>)	getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				
				Query q = session.getNamedQuery("PG_BENE_dettaglioOpposTBene");
				q.setParameter("idAttoRevoca", idAttoRevoca);
				q.setParameter("idProcedimento", idProcedimento);
				q.setParameter("descUtente", descUtente);
				q.setParameter("idUffOwner", idUffOwner);
				q.setParameter("nomeMetodo", nomeMetodo);
			  	if (idSoggettoDiretto > 0) {
					q.setParameter("idSoggettoDiretto", idSoggettoDiretto);
				} else {
					q.setParameter("idSoggettoDiretto", null, Hibernate.LONG);
				}
				if (idSoggettoTitolare > 0) {
					q.setParameter("idSoggettoTitolare", idSoggettoTitolare);
				} else {
					q.setParameter("idSoggettoTitolare", null, Hibernate.LONG);
				}
				if (idTipologia > 0) {
					q.setParameter("idTipologia", idTipologia);
				} else {
					q.setParameter("idTipologia", null, Hibernate.LONG);
				}
				if (idCategoria > 0) {
					q.setParameter("idCategoria", idCategoria);
				} else {
					q.setParameter("idCategoria", null, Hibernate.LONG);
				}
				if (idSottocategoria > 0) {
					q.setParameter("idSottocategoria", idSottocategoria);
				} else {
					q.setParameter("idSottocategoria", null, Hibernate.LONG);
				}

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});
	}
	
	public List<TBene> dettaglioRevocaOpposTBene(final long idAttoRevoca, final long idProcedimento, final String descUtente, final int idUffOwner, final String nomeMetodo) {

		return (List<TBene>)	getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				
				Query q = session.getNamedQuery("PG_BENE_dettaglioRevocaOpposTBene");
				q.setParameter("idAttoRevoca", idAttoRevoca);
				q.setParameter("idProcedimento", idProcedimento);
				q.setParameter("descUtente", descUtente);
				q.setParameter("idUffOwner", idUffOwner);
				q.setParameter("nomeMetodo", nomeMetodo);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});
	}		
	
	
	public List<TBene> dettaglioRevocazTBene(final long idAttoRevoca, final long idProcedimento, final String descUtente, final int idUffOwner, final String nomeMetodo,
    		final long idSoggettoDiretto, final long idSoggettoTitolare, final long idTipologia, final long idCategoria, final long idSottocategoria) {	

		return (List<TBene>)	getHibernateTemplate().execute(new HibernateCallback() {
	
			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				
				Query q = session.getNamedQuery("PG_BENE_dettaglioRevocazioneTBene");
				q.setParameter("idAttoRevoca", idAttoRevoca);
				q.setParameter("idProcedimento", idProcedimento);
				q.setParameter("descUtente", descUtente);
				q.setParameter("idUffOwner", idUffOwner);
				q.setParameter("nomeMetodo", nomeMetodo);
				
				if (idSoggettoDiretto > 0) {
					q.setParameter("idSoggettoDiretto", idSoggettoDiretto);
				} else {
					q.setParameter("idSoggettoDiretto", null, Hibernate.LONG);
				}
				if (idSoggettoTitolare > 0) {
					q.setParameter("idSoggettoTitolare", idSoggettoTitolare);
				} else {
					q.setParameter("idSoggettoTitolare", null, Hibernate.LONG);
				}
				if (idTipologia > 0) {
					q.setParameter("idTipologia", idTipologia);
				} else {
					q.setParameter("idTipologia", null, Hibernate.LONG);
				}
				if (idCategoria > 0) {
					q.setParameter("idCategoria", idCategoria);
				} else {
					q.setParameter("idCategoria", null, Hibernate.LONG);
				}
				if (idSottocategoria > 0) {
					q.setParameter("idSottocategoria", idSottocategoria);
				} else {
					q.setParameter("idSottocategoria", null, Hibernate.LONG);
				}
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});
	}	
	
	public List<TBene> controlloPresenzaBeniPerRevocaConfisca(final long idAtto, final long idProcedimento, final String descUtente, final int idUffOwner, final String nomeMetodo) {	

		return (List<TBene>)	getHibernateTemplate().execute(new HibernateCallback() {
	
			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				
				Query q = session.getNamedQuery("PG_BENE_controlloPresenzaBeni");
				q.setParameter("idAtto", idAtto);
				q.setParameter("idProcedimento", idProcedimento);
				q.setParameter("descUtente", descUtente);
				q.setParameter("idUffOwner", idUffOwner);
				q.setParameter("nomeMetodo", nomeMetodo);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});
	}		
	
	public List <TRelBeneAtto> getListaRelBeneAtto(long idProcedimento, long idAtto, int idUfficioUtente, long idBene, long idSoggettoTitolare) {


		DetachedCriteria criteria = DetachedCriteria.forClass(TRelBeneAtto.class);
		if(idProcedimento>0){
			criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		}
		if(idAtto>0){
			criteria.add(Restrictions.eq("id.codiKIdAtto", idAtto));
		}
		if(idUfficioUtente>0){
			criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente));
		}
		if(idBene>0){
			criteria.add(Restrictions.eq("id.codiKIdBene", idBene));
		}
		if(idSoggettoTitolare>0){
			criteria.add(Restrictions.eq("id.codiKIdSoggTit", idSoggettoTitolare));
		}

		List<TRelBeneAtto> risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	/**
	 * controllaDecrRestBeni
	 * @param vidprocedimento
	 * @param vidUffOwn
	 * @param descUtente
	 * @return KeyMessageDto
	 */
    public KeyMessageDto controllaDecrRestBeni(final long vidprocedimento,final long vidUffOwn,final String descUtente){
    	return (KeyMessageDto) getHibernateTemplate().execute(new HibernateCallback() {

    		public Object doInHibernate(final Session session) throws HibernateException, SQLException {

    			Query q = session.getNamedQuery("PG_BENE_cntrDecrRestBeni");
    			q.setParameter("vidprocedimento", vidprocedimento,Hibernate.LONG);
    			q.setParameter("vidUffOwn", vidUffOwn,Hibernate.LONG);    			
    			q.setParameter("descUtente", descUtente, Hibernate.STRING);

    			q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
    			List l = q.list();
    			return new KeyMessageDto(l);
    		}
    	});
    	    	
    	}	
   	
	public List<TBene> elencoRestBeniTBene(final long idAtto, final long idProcedimento, final String descUtente, final int idUffOwner, final String nomeMetodo,
			final long idSoggettoDiretto, final long idSoggettoTitolare, final long idTipologia, final long idCategoria, final long idSottocategoria) {

		return (List<TBene>)	getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				
				Query q = session.getNamedQuery("PG_BENE_elencoRestBeniTBene");
				q.setParameter("idAtto", idAtto);
				q.setParameter("idProcedimento", idProcedimento);
				q.setParameter("descUtente", descUtente);
				q.setParameter("idUffOwner", idUffOwner);
				q.setParameter("nomeMetodo", nomeMetodo);
			  	if (idSoggettoDiretto > 0) {
					q.setParameter("idSoggettoDiretto", idSoggettoDiretto);
				} else {
					q.setParameter("idSoggettoDiretto", null, Hibernate.LONG);
				}
				if (idSoggettoTitolare > 0) {
					q.setParameter("idSoggettoTitolare", idSoggettoTitolare);
				} else {
					q.setParameter("idSoggettoTitolare", null, Hibernate.LONG);
				}
				if (idTipologia > 0) {
					q.setParameter("idTipologia", idTipologia);
				} else {
					q.setParameter("idTipologia", null, Hibernate.LONG);
				}
				if (idCategoria > 0) {
					q.setParameter("idCategoria", idCategoria);
				} else {
					q.setParameter("idCategoria", null, Hibernate.LONG);
				}
				if (idSottocategoria > 0) {
					q.setParameter("idSottocategoria", idSottocategoria);
				} else {
					q.setParameter("idSottocategoria", null, Hibernate.LONG);
				}


				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});
	}	
	    	
	
    public List getAmministratori(final int idUffOwn, final Long idProcedimento, final Long idAtto, final Long idAmministratore, final boolean escludiAmministratore) {

        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                List<Object[]> lista = null;
                Query q = session.getNamedQuery("PG_BENE_get_amministratori");
                q.setParameter("idUffOwn", idUffOwn);
                if (idProcedimento != null) {
                	q.setParameter("vidProcedimento", idProcedimento);
                   } else {
                	 q.setParameter("vidProcedimento", null, Hibernate.LONG);
                }
                if (idAtto != null) {
                	q.setParameter("vidAtto", idAtto);
                   } else {
                	 q.setParameter("vidAtto", null, Hibernate.LONG);
                }
                if (idAmministratore != null) {
                	q.setParameter("vidAmministratore", idAmministratore);
                   } else {
                	 q.setParameter("vidAmministratore", null, Hibernate.LONG);
                }
                
                if(escludiAmministratore) {
                	 q.setParameter("vescludiAmm", new Long(1), Hibernate.LONG);
                } else {
                	 q.setParameter("vescludiAmm", new Long(0), Hibernate.LONG);
                }
                
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                lista = q.list();
                return lista;
            }
        });
    }
	
	
    public List getAmministratoriDaAssociare(final int idUffOwn, final Long idProcedimento, final Long idAtto, final AmministratoreForm amministratoreForm) {

        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                List<Object[]> lista = null;
                Query q = session.getNamedQuery("PG_BENE_elencoAmministratoriNonAsociatiAtto");
                q.setParameter("IDUFFOWN", idUffOwn);
                if (idProcedimento != null) {
                	q.setParameter("VIDPROCEDIMENTO", idProcedimento);
                   } else {
                	 q.setParameter("VIDPROCEDIMENTO", null, Hibernate.LONG);
                }
                if (idAtto != null) {
                	q.setParameter("VIDATTO", idAtto);
                   } else {
                	 q.setParameter("VIDATTO", null, Hibernate.LONG);
                }
                if (amministratoreForm != null) {
                	if (amministratoreForm.getCognome() != null) {
                		q.setParameter("COGNOME", amministratoreForm.getCognome());
                    } else {
                    	q.setParameter("COGNOME", null, Hibernate.STRING);		
                	}
                	if (amministratoreForm.getNome() != null) {
                		q.setParameter("NOME", amministratoreForm.getNome());
                    } else {
                    	q.setParameter("NOME", null, Hibernate.STRING);		
                	}
                	if (amministratoreForm.getCodiceFiscale() != null) {
                		q.setParameter("CODICEFISCALE", amministratoreForm.getCodiceFiscale());
                    } else {
                    	q.setParameter("CODICEFISCALE", null, Hibernate.STRING);		
                	}
         //Se ha impostato il comune cerco solo per codice comune, altrimenti vedo se ha impostato la provincia   	
                	if (amministratoreForm.getComuneDomProf().getId() != null) {
                		q.setParameter("CODICOMU", amministratoreForm.getComuneDomProf().getId());
                		q.setParameter("CODIPROV", null, Hibernate.STRING);	
                    } else {
                    	q.setParameter("CODICOMU", null, Hibernate.LONG);
                    	if (amministratoreForm.getProvinciaDomProf().getId() != null) {
                    		q.setParameter("CODIPROV", amministratoreForm.getProvinciaDomProf().getId());
                        } else {
                        	q.setParameter("CODIPROV", null, Hibernate.STRING);		
                    	}
                	}
                }
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                lista = q.list();
                return lista;
            }
        });
    }
    
	
	public long inserisciAmministratore(final int idUffOwn, final AmministratoreForm form, final String codUtente, final String nomeMetodo) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_BENE_inserisciAmministratore");
				
				q.setParameter("IDUFFOWN", idUffOwn);
				q.setParameter("COGNOME", form.getCognome(), Hibernate.STRING);
				q.setParameter("NOME", form.getNome(), Hibernate.STRING);
				q.setParameter("DATANASCITA", form.getDataNascita(), Hibernate.DATE);
				q.setParameter("CODICEFISCALE", form.getCodiceFiscale(), Hibernate.STRING);
				if (form.getComuneDomProf().getDescrizione() != null) {
            		q.setParameter("IDCOMUNEDL", form.getComuneDomProf().getId());
                } else {
                	q.setParameter("IDCOMUNEDL", null, Hibernate.LONG);		
            	}
				if (form.getComuneEsteroLegale() != null) {
            		q.setParameter("COMUNEESTERODL", form.getComuneEsteroLegale());
                } else {
                	q.setParameter("COMUNEESTERODL", null, Hibernate.STRING);		
            	}
				if (form.getStatoDomProf().getId() != null) {
            		q.setParameter("IDSTATODL", form.getStatoDomProf().getId());
                } else {
                	q.setParameter("IDSTATODL", null, Hibernate.LONG);		
            	}
				if (form.getLuogoNascita() != null) {
            		q.setParameter("LUOGONASCITA", form.getLuogoNascita());
                } else {
                	q.setParameter("LUOGONASCITA", null, Hibernate.LONG);		
            	}
				if (form.getPartitaIva() != null) {
            		q.setParameter("PARTITAIVA", form.getPartitaIva());
                } else {
                	q.setParameter("PARTITAIVA", null, Hibernate.STRING);		
            	}
				if (form.getDescStudio() != null) {
            		q.setParameter("STUDIO", form.getPartitaIva());
                } else {
                	q.setParameter("STUDIO", null, Hibernate.STRING);		
            	}
				if (form.getTel() != null) {
            		q.setParameter("TEL", form.getTel());
                } else {
                	q.setParameter("TEL", null, Hibernate.STRING);		
            	}
				if (form.getFax() != null) {
            		q.setParameter("FAX", form.getFax());
                } else {
                	q.setParameter("FAX", null, Hibernate.STRING);		
            	}
				if (form.getPec() != null) {
            		q.setParameter("PEC", form.getPec());
                } else {
                	q.setParameter("PEC", null, Hibernate.STRING);		
            	}
				if (form.getstatoIscrizioneAlbo() != null) {
            		q.setParameter("STATOISCRIZIONEALBO", form.getstatoIscrizioneAlbo());
                } else {
                	q.setParameter("STATOISCRIZIONEALBO", null, Hibernate.STRING);		
            	}
				if (form.getDescrIscrizioneAlbo() != null) {
            		q.setParameter("ISCRIZIONEALBO", form.getDescrIscrizioneAlbo());
                } else {
                	q.setParameter("ISCRIZIONEALBO", null, Hibernate.STRING);		
            	}
				if (form.getDataIscrizioneAlbo() != null) {
            		q.setParameter("DATAISCRIZIONEALBO", form.getDataIscrizioneAlbo());
                } else {
                	q.setParameter("DATAISCRIZIONEALBO", null, Hibernate.DATE);		
            	}
				q.setParameter("NOMEMETODO", nomeMetodo, Hibernate.STRING);
				q.setParameter("DESCUTENTE", codUtente, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                HashMap first = (HashMap) l.get(0);
                Long id = (Long) first.get("ID");
                return id;
			}
		});
	}
	
	
    public List getAmministratoreById(final Long idAmministratore) {

        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                List<Object[]> lista = null;
                Query q = session.getNamedQuery("PG_BENE_getAmministratoreById");
                if (idAmministratore != null) {
                	q.setParameter("vidAmministratore", idAmministratore);
                   } else {
                	 q.setParameter("vidAmministratore", null, Hibernate.LONG);
                }
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                lista = q.list();
                return lista;
            }
        });
    }
	
	public long inserisciRelAmministratoreAtto(final int idUffOwn, final AmministratoreForm amministratoreForm, final AttoForm attoForm, final String codUtente, final String nomeMetodo) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_BENE_associaAmministratoreAtto");
				
				q.setParameter("IDUFFOWN", idUffOwn);
				//DG Mac senza ticket restituisce la corretta associazione ma realmente non associa
				q.setParameter("DESCUTENTE", codUtente, Hibernate.STRING);
				q.setParameter("IDPROCEDIMENTO", attoForm.getIdProcedimento(), Hibernate.LONG);
				q.setParameter("IDATTO", attoForm.getCodAtto(), Hibernate.LONG);
				q.setParameter("IDAMMINISTRATORE", amministratoreForm.getId(), Hibernate.LONG);
               	q.setParameter("DATAINIZIOINCARICO", attoForm.getDataDeposito(), Hibernate.DATE);		
            	q.setParameter("NOMEMETODO", nomeMetodo, Hibernate.STRING);
				
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				//DG  Mac restituisce la corretta associazione ma realmente non associa
                List l = q.list();
                //HashMap first = (HashMap) l.get(0);
                //Long id = (Long) first.get("ID");
                long id = 0;
                return id;
			}
		});
	}

	public long inserisciRelAmministratoreBeneAtto(final int idUffOwn, final long idAmministratore, final AttoForm attoForm, final long idBene, final String codUtente, final String nomeMetodo) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_BENE_associaAmministratoreBeneAtto");
				
				q.setParameter("IDUFFOWN", idUffOwn);
				q.setParameter("DESCUTENTE", codUtente, Hibernate.STRING);
				q.setParameter("IDPROCEDIMENTO", attoForm.getIdProcedimento(), Hibernate.LONG);				
				q.setParameter("IDATTO", attoForm.getCodAtto(), Hibernate.LONG);
				q.setParameter("IDBENE", idBene, Hibernate.LONG);
				q.setParameter("IDAMMINISTRATORE", idAmministratore, Hibernate.LONG);
				q.setParameter("DATAINIZIOINCARICO", attoForm.getDataDeposito(), Hibernate.DATE);		
            	q.setParameter("NOMEMETODO", nomeMetodo, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				//DG  Mac restituisce la corretta associazione ma realmente non associa
				List l = q.list();
//                HashMap first = (HashMap) l.get(0);
//                Long id = (Long) first.get("ID");
                long id = 0;
                return id;
			}
		});
	}
		
	// P.Q. MEV 2020/48 Inizio
	public Long inserisciRelAmministratoreAttoTuttiParametri(final int idUffOwn, final long idAmministratore, final long idAtto, final long idProcedimento, final Date dataInizioIncario, final String codUtente, final String nomeMetodo) {
		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_BENE_associaAmministratoreAtto");
				
				q.setParameter("IDUFFOWN", idUffOwn);
				q.setParameter("DESCUTENTE", codUtente, Hibernate.STRING);
				q.setParameter("IDPROCEDIMENTO", idProcedimento, Hibernate.LONG);
				q.setParameter("IDATTO", idAtto, Hibernate.LONG);
				q.setParameter("IDAMMINISTRATORE", idAmministratore, Hibernate.LONG);
               	q.setParameter("DATAINIZIOINCARICO", dataInizioIncario, Hibernate.DATE);		
            	q.setParameter("NOMEMETODO", nomeMetodo, Hibernate.STRING);				
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);			
                List l = q.list();
                long id = 0;
                return id;
			}
		});
		
	}
	
	public Long inserisciRelAmministratoreBeneAttoTuttiParametri(final int idUffOwn, final long idAmministratore, final long idAtto, final long idProcedimento, final Date dataInizioIncario, final long idBene, final String codUtente, final String nomeMetodo) {
		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_BENE_associaAmministratoreBeneAtto");
				
				q.setParameter("IDUFFOWN", idUffOwn);
				q.setParameter("DESCUTENTE", codUtente, Hibernate.STRING);
				q.setParameter("IDPROCEDIMENTO", idProcedimento, Hibernate.LONG);				
				q.setParameter("IDATTO", idAtto, Hibernate.LONG);
				q.setParameter("IDBENE", idBene, Hibernate.LONG);
				q.setParameter("IDAMMINISTRATORE", idAmministratore, Hibernate.LONG);
				q.setParameter("DATAINIZIOINCARICO", dataInizioIncario, Hibernate.DATE);		
            	q.setParameter("NOMEMETODO", nomeMetodo, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
                long id = 0;
                return id;
			}
		});	
	}	
	// P.Q. MEV 2020/48 Fine
		
	public void eliminaRelAmministratoreBeneAtto(int idUffOwn, long idAmministratore, AttoForm attoForm, long idBene) {

		      DetachedCriteria criteria = DetachedCriteria.forClass(TRelProcBeneAttoAmm.class);
		        criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUffOwn));
		        criteria.add(Restrictions.eq("id.codiKIdProc", attoForm.getIdProcedimento()));
		        criteria.add(Restrictions.eq("id.codiKIdAtto", attoForm.getCodAtto()));
		        criteria.add(Restrictions.eq("id.codiKIdAmmin", idAmministratore));
		        if(idBene>0) {
		        	criteria.add(Restrictions.eq("id.codiKIdBene", idBene));
		        }
		        List<TRelProcBeneAttoAmm> risultati = getHibernateTemplate().findByCriteria(criteria);
		        getHibernateTemplate().deleteAll(risultati);
	}
	
	public void eliminaRelAmministratoreAtto(int idUffOwn, long idAmministratore, AttoForm attoForm) {

	    DetachedCriteria criteria = DetachedCriteria.forClass(TRelProcAttoAmm.class);
        criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUffOwn));
        criteria.add(Restrictions.eq("id.codiKIdProc", attoForm.getIdProcedimento()));
        criteria.add(Restrictions.eq("id.codiKIdAtto", attoForm.getCodAtto()));
        criteria.add(Restrictions.eq("id.codiKIdAmmin", idAmministratore));
        List<TRelProcAttoAmm> risultati = getHibernateTemplate().findByCriteria(criteria);
        getHibernateTemplate().deleteAll(risultati);
	}
	
	public boolean esisteRelAmministratoreAttoBeni(int idUfficio, long idProcedimento, Long idAtto, Long idAmministratore) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TRelProcBeneAttoAmm.class);
        criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficio));
        criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
        if(idAtto != null){
        	 criteria.add(Restrictions.eq("id.codiKIdAtto", idAtto)); 	
        }
        if(idAmministratore != null){
        	criteria.add(Restrictions.eq("id.codiKIdAmmin", idAmministratore));	
        }
        List<TRelProcBeneAttoAmm> risultati = getHibernateTemplate().findByCriteria(criteria);
        if (risultati.size() > 0)
        	return true;
        else
        	return false;
	}
	
	public boolean esisteRelAmministratoreAtti(long idAmministratore) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TRelProcAttoAmm.class);
        criteria.add(Restrictions.eq("id.codiKIdAmmin", idAmministratore));
        List<TRelProcAttoAmm> risultati = getHibernateTemplate().findByCriteria(criteria);
        if (risultati.size() > 0)
        	return true;
        else
        	return false;
	}
	
	public boolean esistonoRelAttiAmministratore(int idUfficio, long idProcedimento, Long idAtto) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TRelProcAttoAmm.class);
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficio));
        criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
        if(idAtto != null){
        	 criteria.add(Restrictions.eq("id.codiKIdAtto", idAtto)); 	
        }
        List<TRelProcAttoAmm> risultati = getHibernateTemplate().findByCriteria(criteria);
        if (risultati.size() > 0)
        	return true;
        else
        	return false;
	}
	
	public void eliminaTAmministratori(long idAmministratore) {

	    DetachedCriteria criteria = DetachedCriteria.forClass(TAmministratori.class);
        criteria.add(Restrictions.eq("codiKIdAmmin", idAmministratore));
        List<TAmministratori> risultati = getHibernateTemplate().findByCriteria(criteria);
        getHibernateTemplate().deleteAll(risultati);
	}
	
	public List<TBene> dettaglioProrogaSequestroTBene(final long idAttoRevoca, final long idProcedimento, final String descUtente, final int idUffOwner, final String nomeMetodo) {

		return (List<TBene>)getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				
				Query q = session.getNamedQuery("PG_BENE_dettaglioProrogaSequestroTBene");
				q.setParameter("idAttoRevoca", idAttoRevoca);
				q.setParameter("idProcedimento", idProcedimento);
				q.setParameter("descUtente", descUtente);
				q.setParameter("idUffOwner", idUffOwner);
				q.setParameter("nomeMetodo", nomeMetodo);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});
	}
	
	
	public List<BeneForm> listaBeniInAmministrazione(final int idUffOwn, final long idProcedimento, final Long idAtto, final Long idAmministratore) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				List lista = null;
				Query q = session.getNamedQuery("PG_BENE_beni_in_amministrazione");
				q.setParameter("vidUffOwn", idUffOwn);
				q.setParameter("vidProcedimento", idProcedimento, Hibernate.LONG);
				if (idAtto != null) {
					q.setParameter("vidAtto", idAtto);
				} else {
					q.setParameter("vidAtto", null, Hibernate.LONG);
				}
				if (idAmministratore != null) {
					q.setParameter("vidAmministratore", idAmministratore);
				} else {
					q.setParameter("vidAmministratore", null, Hibernate.LONG);
				}
				
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				lista = q.list();
				return lista;
			}
		});
	}
	
	
	
	public void updateAmministratore(final int idUffOwn, final AmministratoreForm f, final String codUtente) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				TAmministratori AministratoreModel = new TAmministratori();
				TAnComuni comuneModel = new TAnComuni();
				TAnStato statoModel = new TAnStato();
				
				AministratoreModel.setCodiKIdAmmin(f.getId());
				AministratoreModel.setDescCognome(f.getCognome());
				AministratoreModel.setDescNome(f.getNome());
				AministratoreModel.setDescCodiceFiscale(f.getCodiceFiscale());
				AministratoreModel.setDescPartitaIva(f.getPartitaIva());
				AministratoreModel.setDataNascita(f.getDataNascita());
				AministratoreModel.setDescLuogoNascita(f.getLuogoNascita());
				AministratoreModel.setDescComEsteroDl(f.getComuneEsteroLegale());
				if (Integer.parseInt(f.getStatoDomProf().getId()) == 1)
					comuneModel.setCodiKIdComune(Integer.parseInt(f.getComuneDomProf().getId()));
				else
					comuneModel = null;
				statoModel.setCodiKIdStato(Integer.parseInt(f.getStatoDomProf().getId()));
				
				AministratoreModel.setTAnComuni(comuneModel);
				AministratoreModel.setTAnStato(statoModel);
				
				AministratoreModel.setDescStudio(f.getDescStudio());
				AministratoreModel.setDescPec(f.getPec());
				AministratoreModel.setDescTel(f.getTel());
				AministratoreModel.setDescFax(f.getFax());
				AministratoreModel.setDataUltAgg(new Date());
				session.saveOrUpdate(AministratoreModel);
				session.flush();
	
				return null;
			}
		});
	}
	
	public List<TBene> dettaglioSequestroSospensioneTBene(final long idAttoRevoca, final long idProcedimento, final String descUtente, final int idUffOwner, final String nomeMetodo) {

		return (List<TBene>)getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				
				Query q = session.getNamedQuery("PG_BENE_dettaglioSequestroSospensioneTBene");
				q.setParameter("idAttoRevoca", idAttoRevoca);
				q.setParameter("idProcedimento", idProcedimento);
				q.setParameter("descUtente", descUtente);
				q.setParameter("idUffOwner", idUffOwner);
				q.setParameter("nomeMetodo", nomeMetodo);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});
	}
	
	//metodo per verificare che la categoria del bene sia Non definita nel qual caso
	//non deve fare i controlli
	public boolean isDefinito(BeneForm bene) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpSottocatBene.class);
		criteria.add(Restrictions.eq("TTpCatBene.codiKIdCatBene", Integer.parseInt(bene.getCategoria().getId())));
		criteria.add(Restrictions.eq("codiKIdSottocat", Integer.parseInt(bene.getSottocategoria().getId())));
		criteria.add(Restrictions.like("descSottocatBene", "%- NON DEFINITO"));
        criteria.setProjection(Projections.rowCount());
        
        int count = DataAccessUtils.intResult(getHibernateTemplate().findByCriteria(criteria));
		//se e' falso è un bene non definito
        //se e' vero è un bene definito
        return !(count>0);
	}
	
	public TRelBeneBene getTRelBeneBenebyIdAtto(long idAtto, long idProcedimento, int idUffOwn) {

		List<TRelBeneBene> lista = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelBeneBene.class);
		criteria.add(Restrictions.eq("TAtto.id.codiKIdAtto", idAtto));
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUffOwn));
		
		lista = getHibernateTemplate().findByCriteria(criteria);
		if (!Utility.isDefinito(lista)) {
			return null;
		}
		return lista.get(0);
	}

	public List<Map> isBeneInIstanzaCamerale(final long idProc, final int idUffOwn, final long idBene) {
		
		return (List<Map>)getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				
				Query q = session.getNamedQuery("PG_BENE_isBeneInIstanzaCamerale");
				q.setParameter("idProc", idProc);
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("idBene", idBene);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});
		
	}
	
	
	public List listaBeniNominaAmministratore(final int filtro, final int idUffOwn, final long idProcedimento, final Long idBene, final Long idTipologia, final Long idCategoria, final Long idSottocategoria, final long vidDecreto, final Long idAmministratore, final String Amministratore) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				List lista = null;
				Query q = session.getNamedQuery("PG_BENE_nomina_amm");
				q.setParameter("vFiltro", filtro);
				q.setParameter("vidUffOwn", idUffOwn);
				q.setParameter("vidProcedimento", idProcedimento, Hibernate.LONG);
				if (idBene != null) {
					q.setParameter("vidBene", idBene);
				} else {
					q.setParameter("vidBene", null, Hibernate.LONG);
				}
				if (idTipologia != null) {
					q.setParameter("vidTipologia", idTipologia);
				} else {
					q.setParameter("vidTipologia", null, Hibernate.LONG);
				}
				if (idCategoria != null) {
					q.setParameter("vidCategoria", idCategoria);
				} else {
					q.setParameter("vidCategoria", null, Hibernate.LONG);
				}
				if (idSottocategoria != null) {
					q.setParameter("vidSottocategoria", idSottocategoria);
				} else {
					q.setParameter("vidSottocategoria", null, Hibernate.LONG);
				}
				if (idAmministratore != null) {
					q.setParameter("vidAmministratore", idAmministratore);
				} else {
					q.setParameter("vidAmministratore", null, Hibernate.LONG);
				}
				if (Amministratore != null) {
					q.setParameter("AMMINISTRATORE", Amministratore);
				} else {
					q.setParameter("AMMINISTRATORE", null, Hibernate.STRING);
				}
				q.setParameter("vidDecreto", vidDecreto, Hibernate.LONG);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				lista = q.list();
				return lista;
			}
		});
	}
	
	public List<BeneForm> listaBeniProcedimentoFiltrata(final long idProcedimento, final long idUffOwn, final long idAtto, 
    		final long vidSoggettoDiretto, final long vidSoggettoTitolare, final long vidTipologia, final long vidCategoria, final long vidSottocategoria) {

		return (List<BeneForm>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				List<BeneForm> lista = null;
				Query q = session.getNamedQuery("PG_BENE_lista_rel_proc_bene_filtrata");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setParameter("idAtto", idAtto, Hibernate.LONG);
				
				if (vidSoggettoDiretto > 0) {
					q.setParameter("vidSoggettoDiretto", vidSoggettoDiretto);
				} else {
					q.setParameter("vidSoggettoDiretto", null, Hibernate.LONG);
				}
				if (vidSoggettoTitolare > 0) {
					q.setParameter("vidSoggettoTit", vidSoggettoTitolare);
				} else {
					q.setParameter("vidSoggettoTit", null, Hibernate.LONG);
				}
				if (vidTipologia > 0) {
					q.setParameter("vidTipologia", vidTipologia);
				} else {
					q.setParameter("vidTipologia", null, Hibernate.LONG);
				}
				if (vidCategoria > 0) {
					q.setParameter("vidCategoria", vidCategoria);
				} else {
					q.setParameter("vidCategoria", null, Hibernate.LONG);
				}
				if (vidSottocategoria > 0) {
					q.setParameter("vidSottocategoria", vidSottocategoria);
				} else {
					q.setParameter("vidSottocategoria", null, Hibernate.LONG);
				}

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				lista = q.list();
				return lista;
			}
		});
	}

	
	public TAnAltriOper getTAnAltriOper(final Long id) {
		return (TAnAltriOper) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				return session.get(TAnAltriOper.class, id);
			}
		});
	}

	public TAnAltriOper getTAnAltriOperByCodFiscale(String codiceFiscale) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TAnAltriOper.class);
		criteria.add(Restrictions.eq("descCodfiscale", codiceFiscale));
		List<TAnAltriOper> lista = getHibernateTemplate().findByCriteria(criteria);
		return CollectionUtils.isEmpty(lista) ? null : lista.get(0);
	}

	
	public TAnAltriOper getTAnAltriOperByDescrizione(String descrizione) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TAnAltriOper.class);
		criteria.add(Restrictions.eq("descOperatore", descrizione));
		
		List<TAnAltriOper> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() == 1) {
			return risultati.get(0);
		} else return null;
	}
	
	public void modificaBeneFinanaziarioPolizza(final Long idBene, final Integer idUffOwn, final BeneForm beneForm) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				TBeneFin tBeneFinanziarioById = getTBeneFinanziarioById(idBene, idUffOwn);
				tBeneFinanziarioById.setDescRegistrazione(beneForm.getNumeroPolizza());
				tBeneFinanziarioById.setImpoEuro(beneForm.getValutaEur());
				tBeneFinanziarioById.setTTpValuta(commonDao.getTpValuta(beneForm.getTipoValuta().getId()));
				tBeneFinanziarioById.setImpoValuta(beneForm.getValutaEst());
				session.saveOrUpdate(tBeneFinanziarioById);
				session.flush();
				return null;
			}
		});
	
		
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
	
	public List<Long> getListaIdBeniAssociatiAdAttoEProcedimento(long idProcedimento, long idAtto) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelBeneAtto.class);
		criteria.add(Restrictions.eq("id.codiKIdAtto", idAtto));
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		criteria.setProjection(Projections.distinct(Projections.projectionList()
			    .add(Projections.property("id.codiKIdBene"), "id.codiKIdBene")));

		Criterion crit1 = Restrictions.eq("flgValido", new Character('S'));
		
		Criterion crit2 = Restrictions.eq("flgValido", new Character('N'));
		Criterion crit2_1 = Restrictions.eq("TTpTipoMotivo.codiKIdMotivo", new Character('3'));
		Criterion crit3 = Restrictions.and(crit2, crit2_1);
		
		criteria.add(Restrictions.or(crit1, crit3));		
		
		List<Long> risultati = getHibernateTemplate().findByCriteria(criteria);

		return risultati;
	}	

	public List<Object[]> getAttoAmministratoreDelBene(final long idBene, final long idProcedimento, final int idUfficioUtente, final String categoriaAtto) {
		
		Query query = getSession()
				.createSQLQuery(
						"SELECT r.*, a.*, i.*, am.*"

					     + "  FROM T_REL_PROC_BENE_ATTO_AMM r" 
					     + "   INNER JOIN T_ATTO a"
					     + "    ON r.CODI_K_ID_ATTO = a.CODI_K_ID_ATTO AND r.CODI_K_ID_UFF_OWNER = a.CODI_K_ID_UFF_OWNER"
					     + "   INNER JOIN T_ISTANZA i"
					     + "    ON r.CODI_K_ID_ATTO = i.CODI_K_ID_ISTANZA AND r.CODI_K_ID_UFF_OWNER = i.CODI_K_ID_UFF_OWNER"
					     + "   INNER JOIN T_AMMINISTRATORI am"
					     + "    ON r.CODI_K_ID_AMMIN = am.CODI_K_ID_AMMIN"
						+ " WHERE r.CODI_K_ID_PROC = " + idProcedimento
						+ " AND r.CODI_K_ID_UFF_OWNER = " + idUfficioUtente
						+ " AND r.CODI_K_ID_BENE = " + idBene
						+ " AND a.CODI_FK_ID_CAT_ATTO = " + categoriaAtto //372
						+ " ORDER BY a.data_deposito DESC")
						.addEntity("r", TRelProcBeneAttoAmm.class)
						.addEntity("a", TAtto.class)
						.addEntity("i", TIstanza.class)
						.addEntity("am", TAmministratori.class);

		//query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Object[]> l = query.list();
		return l;
	}
	
	public TRelBeneBene getTRelBeneBeneByIdAtto(long idAtto){
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelBeneBene.class);
		criteria.add(Restrictions.eq("TAtto.id.codiKIdAtto", idAtto));
		
		List<TRelBeneBene> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0) {
			return risultati.get(0);
		} else return null;
	}
	
	public TDepositiAmm getDepositoAmmByIdAtto(final long idAtto, final int idUffOwner, final long idProcedimento) {
		Query query = getSession()
				.createSQLQuery(
						"SELECT D.*"
					     + "  FROM T_DEPOSITI_AMM D" 
						+ " WHERE D.CODI_FK_ID_PROC_FG = " + idProcedimento
						+ " AND D.CODI_FK_ID_UFF_OWNER = " + idUffOwner
						+ " AND D.CODI_FK_ID_ATTO = " + idAtto
						+ " AND D.FLG_ACQUISITO = 'S'")
						.addEntity(TDepositiAmm.class);
						
		List<TDepositiAmm> l = query.list();
		if (l.size() > 0) 
			return l.get(0);
		else
			return null;
	}
	
	public String getAmministratoreBeneAtto(long idProcedimento, long idAtto, int idUffOwner, long idBene) {
		Query query = getSession()
				.createSQLQuery(
						"SELECT A.DESC_COGNOME || ' ' || A.DESC_NOME AS amministratore"
						+ " FROM T_AMMINISTRATORI A, T_REL_PROC_BENE_ATTO_AMM R"
						+ " WHERE A.CODI_K_ID_AMMIN = R.CODI_K_ID_AMMIN"
						+ " AND R.CODI_K_ID_PROC = " + idProcedimento
						+ " AND R.CODI_K_ID_ATTO = " + idAtto
						+ " AND R.CODI_K_ID_UFF_OWNER = " + idUffOwner
						+ " AND R.CODI_K_ID_BENE = " + idBene)
						.addScalar("amministratore", Hibernate.STRING);
						
		List<String> l = query.list();
		if (l.size() > 0) 
			return l.get(0);
		else
			return null;
	}

	public TAnAltriOper getTAnAltriOperByDescCodFisc(String descCodFiscale) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TAnAltriOper.class);
		criteria.add(Restrictions.eq("descCodfiscale", descCodFiscale));
		
		List<TAnAltriOper> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0) {
			return risultati.get(0);
		} else return null;
	}
	
	public List<TBeneImmobile> verificaImmobileGraffato(long idProcedimento, int idUffOwner){
		List<TBeneImmobile> result= new ArrayList<TBeneImmobile>();
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelProcBene.class);
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUffOwner));
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		List<TRelProcBene> tRelProcBeneList = getHibernateTemplate().findByCriteria(criteria);
		if ((tRelProcBeneList != null) && !tRelProcBeneList.isEmpty()) {
			for (TRelProcBene tRelProcBene : tRelProcBeneList) {
				DetachedCriteria criteria2 = DetachedCriteria.forClass(TBeneImmobile.class);
				criteria2.add(Restrictions.eq("id.codiKIdUffOwner", idUffOwner));
				criteria2.add(Restrictions.eq("id.codiKIdBeneImm", tRelProcBene.getId().getCodiKIdBene()));
				criteria2.add(Restrictions.isNull("numeGraffati"));
				List<TBeneImmobile> tBeneImmobileList = getHibernateTemplate().findByCriteria(criteria2);
				if (tBeneImmobileList != null && !tBeneImmobileList.isEmpty())
					result.add(tBeneImmobileList.get(0));
			}
		}
		if(result.size()<=1)
			return null;
		return result;
	}

	public void inserisciGraffato(final long idBene1, final long idBene2, final int idUffOwner){
		getHibernateTemplate().execute(new HibernateCallback<Object>() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				TBeneImmobile bene1 = new TBeneImmobile();
				TBeneImmobileId beneId1 = new TBeneImmobileId();
				beneId1.setCodiKIdBeneImm(idBene1);
				beneId1.setCodiKIdUffOwner(idUffOwner);
				bene1.setId(beneId1);
				bene1.setNumeGraffati(idBene2);
				session.update(bene1);
				TBeneImmobile bene2 = new TBeneImmobile();
				TBeneImmobileId beneId2 = new TBeneImmobileId();
				beneId2.setCodiKIdBeneImm(idBene2);
				beneId2.setCodiKIdUffOwner(idUffOwner);
				bene2.setId(beneId2);
				bene2.setNumeGraffati(idBene1);
				session.update(bene2);
				
				session.flush();
				return null;
			}
		});		
	}
	
	public TBeneImmobile esisteGraffato(long idBene, int idUffOwner){
		DetachedCriteria criteria = DetachedCriteria.forClass(TBeneImmobile.class);
		criteria.add(Restrictions.eq("id.codiKIdBeneImm", idBene));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUffOwner));
		
		List<TBeneImmobile> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati!=null && risultati.size() > 0 && risultati.get(0).getNumeGraffati() != null)
		{
			TBeneImmobile immobile1 = risultati.get(0);
			DetachedCriteria criteria2 = DetachedCriteria.forClass(TBeneImmobile.class);
			criteria2.add(Restrictions.eq("id.codiKIdBeneImm", immobile1.getNumeGraffati()));
			criteria2.add(Restrictions.eq("id.codiKIdUffOwner", idUffOwner));
			List<TBeneImmobile> immobile2 = getHibernateTemplate().findByCriteria(criteria2);
			if (immobile2!= null && immobile2.size() > 0)
				return immobile2.get(0);
			else
				return null;
		}
		else
			return null;
	}
	
	public void eliminaGraffato(final long idBene1, final long idBene2, final int idUffOwner){
		getHibernateTemplate().execute(new HibernateCallback<Object>() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				TBeneImmobile bene1 = new TBeneImmobile();
				TBeneImmobileId beneId1 = new TBeneImmobileId();
				beneId1.setCodiKIdBeneImm(idBene1);
				beneId1.setCodiKIdUffOwner(idUffOwner);
				bene1.setNumeGraffati(null);
				bene1.setId(beneId1);
				session.update(bene1);
				
				TBeneImmobile bene2 = new TBeneImmobile();
				TBeneImmobileId beneId2 = new TBeneImmobileId();
				beneId2.setCodiKIdBeneImm(idBene2);
				beneId2.setCodiKIdUffOwner(idUffOwner);
				bene2.setNumeGraffati(null);
				bene2.setId(beneId2);
				session.update(bene2);
				
				session.flush();
				return null;
			}
		});
	}

	/* 
	 * ISSUE MEV : risolve Ticket INC000001842335, all’atto di confermare ed inviare il decreto di confisca compare la
	 * 	schermata - non è possibile confiscare beni non definiti.
	 * 	Aggiunto metodo
	 * Numero MEV : 10
	 * Autore    : Gioggi
	 * Data      : 27 giu 2018
	 * Branch    : MEV_10
	 */
	public List<Map> getDenaro(final Long idBene, final Integer idUffOwner) {

		return getHibernateTemplate().execute(new HibernateCallback<List<Map>>() {

			public List<Map> doInHibernate(final Session session) throws HibernateException, SQLException {
				String qry = "select t.codi_fk_id_abi as desc_abi from T_DENARO t where t.codi_k_id_uff_owner = :idUffOwner and t.codi_k_id_denaro = :idBene";
				SQLQuery query = session.createSQLQuery(qry);
				query.setParameter("idBene", idBene, Hibernate.LONG);
				query.setParameter("idUffOwner", idUffOwner, Hibernate.INTEGER);
				query.addScalar("desc_abi", Hibernate.STRING);
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				return query.list();
			}
		});
	}
	//***** FINE INTERVENTO MEV_10 *****//

	// P.Q. Mev 2020/48 Inizio
	@Override
	public List<TRelProcAttoAmm> getAmministratoriCopertina(int idUffOwn, Long idProcedimento, Long idAtto, Long idAmministratore, boolean escludiAgenzia) {
		
	    DetachedCriteria criteria = DetachedCriteria.forClass(TRelProcAttoAmm.class);
        criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUffOwn));
        criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
        if (idAtto != null)
        	criteria.add(Restrictions.eq("id.codiKIdAtto", idAtto));
        if(idAmministratore != null)
        	criteria.add(Restrictions.eq("id.codiKIdAmmin", idAmministratore));
        List<TRelProcAttoAmm> risultati = getHibernateTemplate().findByCriteria(criteria);
        				
		if(risultati.size() > 0)
			return risultati;
		else
			return new ArrayList<TRelProcAttoAmm>();
		
	}


	@Override
	public List<TRelProcBeneAttoAmm> getAmministratoriBeni(int idUffOwn, Long idProcedimento, Long idAtto, Long idAmministratore, boolean escludiAgenzia, Boolean escludiAtto) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TRelProcBeneAttoAmm.class);
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		
		if (escludiAtto != null) {
			if(escludiAtto == true) {			
				criteria.add(Restrictions.not(Restrictions.eq("id.codiKIdAtto", idAtto)));
			} else {
				criteria.add(Restrictions.eq("id.codiKIdAtto", idAtto));
			}
		}

        if(idAmministratore != null)
        	criteria.add(Restrictions.eq("id.codiKIdAmmin", idAmministratore));		
		
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUffOwn));
				
		if(escludiAgenzia)
			criteria.add(Restrictions.not(Restrictions.eq("id.codiKIdAmmin", 9999990000000L)));
		
		criteria.addOrder( Order.asc( "id.codiKIdAmmin" ));
		criteria.addOrder( Order.asc( "id.codiKIdBene" ));
		List<TRelProcBeneAttoAmm> risultati = getHibernateTemplate().findByCriteria(criteria);
		if(risultati.size() > 0)
			return risultati;
		else
			return new ArrayList<TRelProcBeneAttoAmm>();
	}
	// P.Q. Mev 2020/48 Fine
}