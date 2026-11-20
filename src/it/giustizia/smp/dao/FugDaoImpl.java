package it.giustizia.smp.dao;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import it.giustizia.smp.dto.TRelBeneBeneDto;
import it.giustizia.smp.dto.fug.DettaglioAttoFugDto;
import it.giustizia.smp.dto.fug.DettaglioInvioFugDto;
import it.giustizia.smp.form.BeneForm;
import it.giustizia.smp.form.FugForm;
import it.giustizia.smp.model.TFugInvio;
import it.giustizia.smp.model.TFugMsg;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.webservice.client.fug.modelli.EsitoFUG;

@SuppressWarnings("unchecked")
public class FugDaoImpl extends HibernateDaoSupport implements FugDao{
	
	
	@SuppressWarnings("rawtypes")
	public List<BeneForm> listaBeniFug(final int idUffOwn, final long idProcedimento, final Long idBene) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				List lista = null;
				Query q = session.getNamedQuery("PG_FUG_beni_da_inviare");
				q.setParameter("vidUffOwn", idUffOwn);
				q.setParameter("vidProcedimento", idProcedimento, Hibernate.LONG);
				if (idBene != null) {
					q.setParameter("vidBene", idBene);
				} else {
					q.setParameter("vidBene", null, Hibernate.LONG);
				}
				
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				lista = q.list();
				return lista;
			}
		});
	}

	@SuppressWarnings("rawtypes")
	private List<DettaglioAttoFugDto> quoteModelli(final int idUffOwn, final long idProcedimento, final Long idBene, final String idModello, final String namedQuery) {

		return (List<DettaglioAttoFugDto>)getHibernateTemplate().execute(new HibernateCallback() {

			public  List<DettaglioAttoFugDto>  doInHibernate(final Session session) throws HibernateException, SQLException {
			
				List lista = null;
				Query q = session.getNamedQuery(namedQuery);
				q.setParameter("vidUffOwn", idUffOwn);
				q.setParameter("vidProcedimento", idProcedimento, Hibernate.LONG);
				if (idBene != null) {
					q.setParameter("vidBene", idBene);
				} else {
					q.setParameter("vidBene", null, Hibernate.LONG);
				}
				if (idModello != null) {
					q.setParameter("vidModello", idModello);
				} else {
					q.setParameter("vidModello", null, Hibernate.STRING);
				}
				q.setResultTransformer(Transformers.aliasToBean(DettaglioAttoFugDto.class));
				lista = q.list();
				return lista;
			}
		});
	}
	
	public List<DettaglioAttoFugDto> quoteModelliA_D(final int idUffOwn, final long idProcedimento, final Long idBene, final String idModello) {
		return quoteModelli(idUffOwn, idProcedimento, idBene, idModello, "PG_FUG_quote_modelli_A_D");
	}
	
	public List<DettaglioAttoFugDto> quoteModelliC(final int idUffOwn, final long idProcedimento, final Long idBene, final String idModello) {
		return quoteModelli(idUffOwn, idProcedimento, idBene, idModello, "PG_FUG_quote_modelli_C");
	}
	
	@SuppressWarnings("rawtypes")
	public List<TRelBeneBeneDto> datiModelliH(final int idUffOwn, final long idProcedimento, final Long idBene, final String idModello) {

		return (List)getHibernateTemplate().execute(new HibernateCallback() {

			public  List  doInHibernate(final Session session) throws HibernateException, SQLException {
			
				List lista = null;
				Query q = session.getNamedQuery("PG_FUG_quote_modelli_H");
				q.setParameter("vidUffOwn", idUffOwn);
				q.setParameter("vidProcedimento", idProcedimento, Hibernate.LONG);
				if (idBene != null) {
					q.setParameter("vidBene", idBene);
				} else {
					q.setParameter("vidBene", null, Hibernate.LONG);
				}
				if (idModello != null) {
					q.setParameter("vidModello", idModello);
				} else {
					q.setParameter("vidModello", null, Hibernate.STRING);
				}
				q.setResultTransformer(Transformers.aliasToBean(TRelBeneBeneDto.class));
				lista = q.list();
				return lista;
			}
		});
	}
	
	@SuppressWarnings("rawtypes")
	public long insertFugInvio(final int idUffOwn, final String codiceUtente, final long idProcedimento, final long idAtto, final long idBene,
			final String modello, final String protocolloRif, final Long idInvio, final String idProtocollo, final String esitoAnnullamento, 
			final Date dataNotifica, final Date dataIrrRev, final String flgStatoInvio, final Long idSogTit,
			final BigDecimal importoValuta, final String idValuta, final BigDecimal importoEuro,
			final String nomeMetodo) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_FUG_inserisci_FugInvio");
				
				q.setParameter("IDUFFOWN", idUffOwn);
				q.setParameter("DESCUTENTE", codiceUtente);
				q.setParameter("IDPROCEDIMENTO", idProcedimento);
				if (idAtto >0) {
					q.setParameter("IDATTO", idAtto, Hibernate.LONG);
				} else {
					q.setParameter("IDATTO", null, Hibernate.LONG);
				}
				q.setParameter("IDBENE", idBene, Hibernate.LONG);
				if (modello != null) {
					q.setParameter("MODELLO", modello, Hibernate.STRING);
				} else {
					q.setParameter("MODELLO", null, Hibernate.STRING);
				}
				if (idProtocollo != null) {
					q.setParameter("PROTOCOLLO", idProtocollo, Hibernate.STRING);
				} else {
					q.setParameter("PROTOCOLLO", null, Hibernate.STRING);
				}
				if (protocolloRif != null) {
					q.setParameter("PROTOCOLLORIF", protocolloRif, Hibernate.STRING);
				} else {
					q.setParameter("PROTOCOLLORIF", null, Hibernate.STRING);
				}
				if (idInvio != null) {
					q.setParameter("IDINVIO", idInvio, Hibernate.LONG);
				} else {
					q.setParameter("IDINVIO", null, Hibernate.LONG);
				}
				if (dataNotifica != null) {
					q.setParameter("DATANOTIFICA", dataNotifica, Hibernate.DATE);
				} else {
					q.setParameter("DATANOTIFICA", null, Hibernate.DATE);
				}
				if (dataIrrRev != null) {
					q.setParameter("DATAIRRREVOCA", dataIrrRev, Hibernate.DATE);
				} else {
					q.setParameter("DATAIRRREVOCA", null, Hibernate.DATE);
				}
				if (esitoAnnullamento != null) {
					q.setParameter("FLGESITOANNULL", esitoAnnullamento, Hibernate.STRING);
				} else {
					q.setParameter("FLGESITOANNULL", null, Hibernate.STRING);
				}
				if (flgStatoInvio != null) {
					q.setParameter("FLGSTATOINVIO", flgStatoInvio,  Hibernate.STRING);
				} else {
					q.setParameter("FLGSTATOINVIO", null, Hibernate.STRING);
				}
				if (idSogTit != null) {
					q.setParameter("IDSOGTIT", idSogTit, Hibernate.LONG);
				} else {
					q.setParameter("IDSOGTIT", null, Hibernate.LONG);
				}
				
				//campi per la risposta asincrona
				q.setParameter("FLGDEFPOS", null, Hibernate.STRING);
				q.setParameter("DATADEFPOS", null, Hibernate.DATE);
				q.setParameter("NUMERISORSAEQUI", null, Hibernate.LONG); 
				q.setParameter("NUMEPROVVEQUI", null, Hibernate.LONG);   
				q.setParameter("NOTIFEQUI", null, Hibernate.STRING);     
				q.setParameter("NOTEEQUI", null, Hibernate.STRING);
				if (Utility.isValid(importoValuta, false)) {
					q.setParameter("IMPOVALUTA", importoValuta, Hibernate.BIG_DECIMAL);
				} else {
					q.setParameter("IMPOVALUTA", null, Hibernate.BIG_DECIMAL);
				}
				if (idValuta != null) {
					q.setParameter("IDVALUTA", idValuta, Hibernate.STRING);
				} else {
					q.setParameter("IDVALUTA", null, Hibernate.STRING);
				}
				if (Utility.isValid(importoEuro, false)) {
					q.setParameter("IMPOEURO", importoEuro, Hibernate.BIG_DECIMAL);
				} else {
					q.setParameter("IMPOEURO", null, Hibernate.BIG_DECIMAL);
				}
				
				q.setParameter("NOMEMETODO", nomeMetodo, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
                Long id = (Long) first.get("IDCURRENTINVIO");
                
                return id;
			}
        });
    }
	
	@SuppressWarnings("rawtypes")
	public List<DettaglioAttoFugDto> listaInvioFug(final int idUffOwn, final long idProcedimento, final Long idBene, final Long idAtto, final Long idSogg, final String idModello, final String seAnnull, final Date dataDa, final Date dataA) {

		return (List<DettaglioAttoFugDto>)getHibernateTemplate().execute(new HibernateCallback() {
			
			public  List<DettaglioAttoFugDto>  doInHibernate(final Session session) throws HibernateException, SQLException {
				
				List lista = null;
				Query q = session.getNamedQuery("PG_FUG_modelli_inviati");
				q.setParameter("vidUffOwn", idUffOwn);
				q.setParameter("vidProcedimento", idProcedimento, Hibernate.LONG);
				if (idBene != null) {
					q.setParameter("vidBene", idBene);
				} else {
					q.setParameter("vidBene", null, Hibernate.LONG);
				}
				if (idAtto != null) {
					q.setParameter("vidAtto", idAtto);
				} else {
					q.setParameter("vidAtto", null, Hibernate.LONG);
				}
				if (idSogg != null) {
					q.setParameter("vidSogg", idSogg);
				} else {
					q.setParameter("vidSogg", null, Hibernate.LONG);
				}
				if (idModello != null) {
					q.setParameter("vidModello", idModello);
				} else {
					q.setParameter("vidModello", null, Hibernate.STRING);
				}
				if (seAnnull != null) {
					q.setParameter("seAnnull", seAnnull);
				} else {
					q.setParameter("seAnnull", null, Hibernate.STRING);
				}
				if (dataDa != null) {
					q.setParameter("dataDa", dataDa);
				} else {
					q.setParameter("dataDa", null, Hibernate.DATE);
				}
				if (dataA != null) {
					q.setParameter("dataA", dataA);
				} else {
					q.setParameter("dataA", null, Hibernate.DATE);
				}
				q.setResultTransformer(Transformers.aliasToBean(DettaglioAttoFugDto.class));
				lista = q.list();
				return lista;
			
			}
		});
	}
	
	public TFugInvio getTFugInvioById(long idInvio) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TFugInvio.class);
		criteria.add(Restrictions.eq("codiKIdInvio", idInvio));
		List<TFugInvio> risultati = getHibernateTemplate().findByCriteria(criteria);
        TFugInvio risultato = DataAccessUtils.requiredSingleResult(risultati);
		return risultato;
	}
	
	@SuppressWarnings("rawtypes")
	public long updateFugInvio(final FugForm fugForm) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_FUG_update_FugInvio");
				
				q.setParameter("IDINVIO", fugForm.getIdInvio(), Hibernate.LONG);
				if (fugForm.getDescProtocollo() != null) {
					q.setParameter("PROTOCOLLO", fugForm.getDescProtocollo(), Hibernate.STRING);
				} else {
					q.setParameter("PROTOCOLLO", null, Hibernate.CHARACTER);
				}
				if (fugForm.getStatoInvio() != null) {
					q.setParameter("STATOINVIO", fugForm.getStatoInvio(), Hibernate.CHARACTER);
				} else {
					q.setParameter("STATOINVIO", null, Hibernate.CHARACTER);
				}
				if (fugForm.getFlgEsitoAnnull() != null) {
					q.setParameter("ESITOANNULL", fugForm.getFlgEsitoAnnull(), Hibernate.CHARACTER);
				} else {
					q.setParameter("ESITOANNULL", null, Hibernate.CHARACTER);
				}
				
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long id = (Long) first.get("IDINVIO");
				return id;
        	}
        });
    }

	@SuppressWarnings("rawtypes")
	public List listaSoggettiBeneficiari(final int idUffOwn, final long idProcedimento, final Long idBene, final Long idAtto, final Long idSoggetto) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				
				Query q = session.getNamedQuery("GetSoggettiIntBene");
				q.setParameter("vidUffOwn", idUffOwn);
				q.setParameter("vidProcedimento", idProcedimento);
				q.setParameter("vidAtto", idAtto);
				q.setParameter("vidBene", idBene);	
				if (idSoggetto != null) {
					q.setParameter("vidSoggetto", idSoggetto, Hibernate.LONG);
				} else {
					q.setParameter("vidSoggetto", null, Hibernate.STRING);
				}
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List soggetti = q.list();
				return soggetti;
			}
		});
	}
	
	@SuppressWarnings("rawtypes")
	public long insertFugErrori(final Long idInvio, final int progressivo, final EsitoFUG recordErrore, final String codiceUtente) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_FUG_inserisci_FugErrori");
				q.setParameter("DESCUTENTE", codiceUtente);
		//parametro per la procedura asincrona 
				q.setParameter("VDESCPROT", null, Hibernate.LONG);
				
				q.setParameter("IDINVIO", idInvio, Hibernate.LONG);
				q.setParameter("IDPROGR", progressivo, Hibernate.INTEGER);
				if (recordErrore.getCodiceSezione() != null) {
					q.setParameter("CODSEZIONE", recordErrore.getCodiceSezione(), Hibernate.STRING);
				} else {
					q.setParameter("CODSEZIONE", null, Hibernate.STRING);
				}
				if (recordErrore.getCodiceCampo() != null) {
					q.setParameter("CODCAMPO", recordErrore.getCodiceCampo(), Hibernate.STRING);
				} else {
					q.setParameter("CODCAMPO", null, Hibernate.STRING);
				}
				if (recordErrore.getCodiceErrore() != null) {
					q.setParameter("CODERRORE", recordErrore.getCodiceErrore(), Hibernate.STRING);
				} else {
					q.setParameter("CODERRORE", null, Hibernate.STRING);
				}
				if (recordErrore.getDescrizioneErrore() != null) {
					q.setParameter("DESCERRORE", recordErrore.getDescrizioneErrore(), Hibernate.STRING);
				} else {
					q.setParameter("DESCERRORE", null, Hibernate.STRING);
				}
			//  S = sincrono	
				q.setParameter("TIPOERRORE", "S", Hibernate.STRING);
				
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
                long id = 0;
                return id;
			}
        });
    }

	@SuppressWarnings("rawtypes")
	public Map<String, String> insertFugMessaggi(final Long idInvio,
			final String idProtocollo, final char flagDirezione, final char flagTipo, final Blob messaggio) {
			return (Map<String, String>)getHibernateTemplate().execute(new HibernateCallback() {

			public Map<String, String> doInHibernate(final Session session) throws HibernateException, SQLException {
				//session.getTransaction().begin();
				Query q = session.getNamedQuery("PG_FUG_inserisci_FugMessaggi");
				q.setParameter("VIDINVIO", idInvio, Hibernate.LONG);
				q.setParameter("VFLGDIREZIONE", flagDirezione, Hibernate.CHARACTER);
				q.setParameter("VFLGTIPO", flagTipo, Hibernate.CHARACTER);
				q.setParameter("VDESCPROTOCOLLO", idProtocollo, Hibernate.STRING);
				q.setParameter("VMESSAGGIO", messaggio, Hibernate.BLOB);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Integer id = (Integer) first.get("VRESULT");
				Map<String,String> result = new HashMap<String,String>();
				if(id == null)
					result.put("ERRORE_INTERNO","Errore generico");
				else if(id.intValue() == 1)
			    	result.put("OK","Operazione correttamente eseguita");
			    else if(id.intValue() == 99)
			    	result.put("ERRORE_INTERNO","Errore database");
				//session.getTransaction().commit();
				return result;
			}
        });
    }
	
	@SuppressWarnings("rawtypes")
	public List<DettaglioAttoFugDto> getDataSequestro(final int idUffOwn, final long idAtto) {

		return (List<DettaglioAttoFugDto>)getHibernateTemplate().execute(new HibernateCallback() {

			public  List<DettaglioAttoFugDto>  doInHibernate(final Session session) throws HibernateException, SQLException {
			
				List lista = null;
				Query q = session.getNamedQuery("PG_FUG_get_DataSequestro");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("idAtto", idAtto, Hibernate.LONG);
				
				q.setResultTransformer(Transformers.aliasToBean(DettaglioAttoFugDto.class));
				lista = q.list();
				return lista;
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public List<DettaglioAttoFugDto> getDataRestituzione(final int idUffOwn, final long idAtto) {

		return (List<DettaglioAttoFugDto>)getHibernateTemplate().execute(new HibernateCallback() {

			public  List<DettaglioAttoFugDto>  doInHibernate(final Session session) throws HibernateException, SQLException {
			
				List lista = null;
				Query q = session.getNamedQuery("PG_FUG_get_DataRestituzione");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("idAtto", idAtto, Hibernate.LONG);
				
				q.setResultTransformer(Transformers.aliasToBean(DettaglioAttoFugDto.class));
				lista = q.list();
				return lista;
			}
		});
	}

	@SuppressWarnings("rawtypes")
  public List<DettaglioAttoFugDto> getDatiModelloInviato(final long idProc, final Long idAtto, final Long idBene, final int idUffOwner, final String descModello) {
  	
		return (List<DettaglioAttoFugDto>)getHibernateTemplate().execute(new HibernateCallback() {
			
			public  List<DettaglioAttoFugDto>  doInHibernate(final Session session) throws HibernateException, SQLException {
				
				List lista = null;
				Query q = session.getNamedQuery("PG_FUG_get_dati_modello_inviato");
				q.setParameter("idProc", idProc);
				q.setParameter("idAtto", idAtto);
				q.setParameter("idBene", idBene);
				q.setParameter("idUffOwner", idUffOwner);
				q.setParameter("descModello", descModello);
				q.setResultTransformer(Transformers.aliasToBean(DettaglioAttoFugDto.class));
				lista = q.list();
				return lista;
			
			}
		});
  }

	@SuppressWarnings("rawtypes")
	public List<TFugInvio> gatInvioFUGCollegato(final int idUffOwn, final String protRif) {

		return (List<TFugInvio>)getHibernateTemplate().execute(new HibernateCallback() {

			public List<TFugInvio> doInHibernate(final Session session) throws HibernateException, SQLException {
			
				List lista = null;
				Query q = session.getNamedQuery("PG_FUG_get_InvioFUGCollegato");
				q.setParameter("idUffOwner", idUffOwn);
				q.setParameter("protRif", protRif);
				
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				lista = q.list();
				return lista;
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public Map<String, String> getRispostaEdEsitoXML(final int idUffOwn, final Long idInvio) {

		return (Map<String, String>)getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_FUG_recupera_invii_XML");
				q.setParameter("idUffOwner", idUffOwn);
				q.setParameter("idInvio", idInvio, Hibernate.LONG);
				q.setResultTransformer(Transformers.aliasToBean(DettaglioInvioFugDto.class));
				List l = q.list();
				Map<String,String> result = new HashMap<String,String>();
				if (Utility.isValid(l)) {
					DettaglioInvioFugDto first = (DettaglioInvioFugDto) l.get(0);
					Blob richiesta = first.getRICHIESTAXML();
					Blob esito = first.getESITOXML();
					result.put(Constants.FUG_RICHIESTA, Utility.convert(richiesta));
		    	result.put(Constants.FUG_ESITO, Utility.convert(esito));
				}
				return result;
			}
		});
	}

	@SuppressWarnings("rawtypes")
	private boolean isVerificaEsistenza(final long idProc, final Long idAtto, final Long idBene, final int idUffOwner, final String namedQuery) {

		return (Boolean)getHibernateTemplate().execute(new HibernateCallback() {

			public Boolean doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery(namedQuery);
				q.setParameter("idProc", idProc);
				q.setParameter("idAtto", idAtto);
				q.setParameter("idBene", idBene);
				q.setParameter("idUffOwner", idUffOwner);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				boolean result = false;
				if (Utility.isValid(l)) {
					HashMap first = (HashMap) l.get(0);
					Integer id = (Integer) first.get("VRESULT");
					result = ((id == null) || (id.intValue() == 0));
				}
				return result;
			}
		});
	}

	public boolean isBeneDecretoRev(final long idProc, final Long idAtto, final Long idBene, final int idUffOwner) {
		return isVerificaEsistenza(idProc, idAtto, idBene, idUffOwner, "PG_FUG_is_bene_decreto_rev");
}

	public boolean isBeneDecretoConf(final long idProc, final Long idAtto, final Long idBene, final int idUffOwner) {
		return isVerificaEsistenza(idProc, idAtto, idBene, idUffOwner, "PG_FUG_is_bene_decreto_conf");
	}

	@Override
	public TFugMsg getTFugMsgFromTFugInvio(long id) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TFugMsg.class);
		criteria.add(Restrictions.eq("TFugInvio.codiKIdInvio", id));
		criteria.add(Restrictions.eq("flgDirezione", 'I'));
		List<TFugMsg> messaggio = getHibernateTemplate().findByCriteria(criteria);
		if(messaggio != null)
			return messaggio.get(0);
		return null;
	}

}
