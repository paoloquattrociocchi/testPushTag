package it.giustizia.smp.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import it.giustizia.smp.dto.pg_misura.DettaglioMisuraPersonaleDto;
import it.giustizia.smp.model.TBene;
import it.giustizia.smp.model.TCauzione;
import it.giustizia.smp.model.TDefPers;
import it.giustizia.smp.model.TMisuraPers;
import it.giustizia.smp.model.TMisuraPersId;
import it.giustizia.smp.model.TPersonaFisica;
import it.giustizia.smp.model.TRelAttoMisPers;
import it.giustizia.smp.model.TRelAttoMisPersId;
import it.giustizia.smp.utility.Utility;

/**
 * MisuraDaoImpl
 * 
 * @author Almaviva S.p.A.
 *         classe dao all'interno della quale viene fatto l'accesso al DB
 */
public class MisuraDaoImpl extends HibernateDaoSupport implements MisuraDao {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<DettaglioMisuraPersonaleDto> caricaMisuraPersonale(final long vidProc, final long vidAtto, final long vidUffOwn, final long idSoggInt) {

		return (List<DettaglioMisuraPersonaleDto>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_MISURA_caricaMisuraPers");
				q.setParameter("idProc", vidProc, Hibernate.LONG);
				if (vidAtto > 0) {
					q.setParameter("idAtto", vidAtto, Hibernate.LONG);
				} else {
					q.setParameter("idAtto", null, Hibernate.LONG);
				}
				q.setParameter("idUffOwn", vidUffOwn, Hibernate.LONG);
				q.setParameter("idSoggInt", idSoggInt, Hibernate.LONG);
				q.setResultTransformer(Transformers.aliasToBean(DettaglioMisuraPersonaleDto.class));
				List<DettaglioMisuraPersonaleDto> l = q.list();
				if ((l != null) && (l.size() > 0)) {
					return l;
				}
				return null;
			}
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TMisuraPers getTMisuraPerById(final long idMisura, final int idUfficioUtente) {
		return (TMisuraPers)getHibernateTemplate().execute(new HibernateCallback() {

			public TMisuraPers doInHibernate(final Session session) throws HibernateException, SQLException {
				TMisuraPersId id = new TMisuraPersId(idUfficioUtente,idMisura);
				return (TMisuraPers)session.load(TMisuraPers.class, id);
			}
		});
		
	}
	
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	public Long duplicaMisuraPersonalePerAggravamento(final long idMisura, final int idUfficioUtente) {
//
//		return (Long) getHibernateTemplate().execute(new HibernateCallback() {
//
//			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
//
//				Query q = session.getNamedQuery("PG_MISURA_duplicaMisuraPers");
//				q.setParameter("idMisura", idMisura, Hibernate.LONG);
//				q.setParameter("idUffOwn", idUfficioUtente, Hibernate.INTEGER);
//				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
//				List l = q.list();
//				Long idMisuraInserita = 0L;
//				if ((l != null) && (l.size() > 0)) {
//					Map<String, Object> ret = (Map<String, Object>) l.get(0);
//					idMisuraInserita = (Long) ret.get("idMisuraInserita");
//				}
//				return (idMisuraInserita != 0) ? idMisuraInserita : null;
//			}
//		});
//	}
		
	
	@SuppressWarnings({ "unchecked", "rawtypes" })	
	public void duplicaMisuraPersonalePerAggravamento(final Long idPadre, final Long idAggravamento,final Long idAttoPadreAggiornato,final Long idAttoAggravamento,final Long idSoggettoInteressato,final Integer idUffOwn){ 
		   getHibernateTemplate().execute(new HibernateCallback() {

			public  Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_MISURA_duplicaMisuraPers");
				q.setParameter("idPadre", idPadre, Hibernate.LONG);
				q.setParameter("idAggravamento", idAggravamento, Hibernate.LONG);
				q.setParameter("idAttoPadreAggiornato", idAttoPadreAggiornato, Hibernate.LONG);
				q.setParameter("idAttoAggravamento", idAttoAggravamento, Hibernate.LONG);
				q.setParameter("idSoggettoInteressato", idSoggettoInteressato, Hibernate.LONG);
				q.setParameter("idUffOwn", idUffOwn, Hibernate.INTEGER);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				Long idMisuraInserita = 0L;
				if ((l != null) && (l.size() > 0)) {
					Map<String, Object> ret = (Map<String, Object>) l.get(0);
					idMisuraInserita = (Long) ret.get("IDMISURAPERS");
				}
				return (idMisuraInserita != 0 && idMisuraInserita!= null) ? idMisuraInserita : null;
		
			}
		});
	}		

	/**
	 * InserisciMisuraPersonale
	 * 
	 * @param idMisureCaricate
	 * @param vidProc
	 * @param vidAtto
	 * @param vidUffOwn
	 * @param relAttoMisPers
	 * @param idSoggInt
	 * @param cauzione
	 * @param articolo67
	 * @param vcodUtente
	 * @param idPrescrizioni
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void inserisciMisuraPersonale(final String idMisureCaricate, final long vidProc, final long vidAtto, final long vidUffOwn, final TRelAttoMisPers relAttoMisPers, final long idSoggInt, final String cauzione, final String articolo67, final String vcodUtente, final String idPrescrizioni, final BigDecimal importoCauzione,final String articolo9c1) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_MISURA_inserisci_rel_atto_misPers");
				q.setParameter("idMisureCaricate", idMisureCaricate);
				q.setParameter("idProc", vidProc);
				if (vidAtto > 0) {
					q.setParameter("idAtto", vidAtto);
				} else {
					q.setParameter("idAtto", null, Hibernate.LONG);
				}
				q.setParameter("idUffOwn", vidUffOwn);
				q.setParameter("idSoggInt", idSoggInt);
				
				if ((relAttoMisPers != null) && (relAttoMisPers.getTTpTipoMisura() != null) && relAttoMisPers.getTTpTipoMisura().getCodiKIdTipoMisura()>0) {
					q.setParameter("idTipoMisura", relAttoMisPers.getTTpTipoMisura().getCodiKIdTipoMisura());
				} else {
					q.setParameter("idTipoMisura", null, Hibernate.LONG);
				}
				
				if ((relAttoMisPers != null) && (relAttoMisPers.getTAnNormative() != null) && relAttoMisPers.getTAnNormative().getCodiKIdNormativa()>0) {
					q.setParameter("idNormativa", relAttoMisPers.getTAnNormative().getCodiKIdNormativa());
				} else {
					q.setParameter("idNormativa", null, Hibernate.LONG);
				}
				
				if ((relAttoMisPers != null) && (relAttoMisPers.getNumeDurataAnni() != null)) {
					q.setParameter("durataAnni", relAttoMisPers.getNumeDurataAnni());
				} else {
					q.setParameter("durataAnni", null, Hibernate.LONG);
				}
				if ((relAttoMisPers != null) && (relAttoMisPers.getNumeDurataMesi() != null)) {
					q.setParameter("durataMesi", relAttoMisPers.getNumeDurataMesi());
				} else {
					q.setParameter("durataMesi", null, Hibernate.LONG);
				}
				if ((relAttoMisPers != null) && (relAttoMisPers.getNumeDurataGiorni() != null)) {
					q.setParameter("durataGiorni", relAttoMisPers.getNumeDurataGiorni());
				} else {
					q.setParameter("durataGiorni", null, Hibernate.LONG);
				}
				q.setParameter("descLoc", relAttoMisPers.getDescLoc());
				q.setParameter("descUltPresc", relAttoMisPers.getDescUltPresc());
				q.setParameter("cauzione", cauzione, Hibernate.STRING);// --
																		// valori
																		// ammessi
																		// S o N
				q.setParameter("articolo67", articolo67);// -- valori ammessi S
															// o N
				
				if ((relAttoMisPers != null) && (relAttoMisPers.getTTpEsitoByCodiFkIdEsito1() != null) && (relAttoMisPers.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito() > 0)) {
					q.setParameter("idEsito1", relAttoMisPers.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito());
				} else {
					q.setParameter("idEsito1", null, Hibernate.LONG);
				}
				if ((relAttoMisPers != null) && (relAttoMisPers.getTTpEsitoByCodiFkIdEsito2() != null) && (relAttoMisPers.getTTpEsitoByCodiFkIdEsito2().getCodiKIdEsito() > 0)) {
					q.setParameter("idEsito2", relAttoMisPers.getTTpEsitoByCodiFkIdEsito2().getCodiKIdEsito());
				} else {
					q.setParameter("idEsito2", null, Hibernate.LONG);
				}
				if ((relAttoMisPers != null) && (relAttoMisPers.getTTpEsitoByCodiFkIdEsito3() != null) && (relAttoMisPers.getTTpEsitoByCodiFkIdEsito3().getCodiKIdEsito() > 0)) {
					q.setParameter("idEsito3", relAttoMisPers.getTTpEsitoByCodiFkIdEsito3().getCodiKIdEsito());
				} else {
					q.setParameter("idEsito3", null, Hibernate.LONG);
				}
				if ((relAttoMisPers != null) && (relAttoMisPers.getTTpEsitoFinale() != null) && (relAttoMisPers.getTTpEsitoFinale().getCodiKIdEsitoFin() > 0)) {
					q.setParameter("idEsitoFin", relAttoMisPers.getTTpEsitoFinale().getCodiKIdEsitoFin());
				} else {
					q.setParameter("idEsitoFin", null, Hibernate.LONG);
				}
				q.setParameter("descAltro1", relAttoMisPers.getDescAltro1());
				q.setParameter("descAltro2", relAttoMisPers.getDescAltro2());
				q.setParameter("descAltro3", relAttoMisPers.getDescAltro3());
				q.setParameter("flgValido", "S");
				q.setParameter("codiMotivo", relAttoMisPers.getTTpTipoMotivo());
				if (relAttoMisPers.getTAtto() != null && relAttoMisPers.getTAtto().getTTpTipoAtto() != null) {
					q.setParameter("idTipoAtto", relAttoMisPers.getTAtto().getTTpTipoAtto(), Hibernate.LONG);
				} else {
					q.setParameter("idTipoAtto", 9L, Hibernate.LONG);
				}
				q.setParameter("descUtente", vcodUtente, Hibernate.STRING);
				q.setParameter("idPrescrizioni", idPrescrizioni);
				if (importoCauzione != null) {
					q.setParameter("importoCauzione", importoCauzione, Hibernate.BIG_DECIMAL);
				} else {
					q.setParameter("importoCauzione", null, Hibernate.BIG_DECIMAL);
				}
				q.setParameter("articolo9c1", articolo9c1);// -- valori ammessi S  o N
				
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});
	}

	/**
     * inserisciMisuraPersonaleDecIGrado
     * 
     * @param vidProc
     * @param vidAtto
     * @param vidUffOwn
     * @param relAttoMisPers
     * @param idSoggInt
     * @param cauzione
     * @param cauzioneProp
     * @param articolo67
     * @param articolo67Prop
     * @param vcodUtente
     * @param importoCauzione
     * @param azionePrescrAcc
     * @param azionePrescrNoAcc
     * @param idDecisione
     * @param tipoProc
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void inserisciMisuraPersonaleDecIGrado(final long vidProc, final long vidAtto, final long vidUffOwn, final TRelAttoMisPers relAttoMisPers, final long idSoggInt, final String cauzione, final String cauzioneProp, final String noteCauz, final String articolo67, final String articolo67Prop, final String vcodUtente, final BigDecimal importoCauzione, final String azionePrescrAcc, final String azionePrescrNoAcc, final long idDecisione, final long tipoProc) {

        getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_MISURA_inserisci_rel_atto_misPersDecIGrado");
                q.setParameter("idProc", vidProc);
                if (vidAtto > 0) {
                    q.setParameter("idAtto", vidAtto);
                } else {
                    q.setParameter("idAtto", null, Hibernate.LONG);
                }
                q.setParameter("idUffOwn", vidUffOwn);
                q.setParameter("idSoggInt", idSoggInt);
                if ((relAttoMisPers != null) && (relAttoMisPers.getTTpTipoMisura() != null) && (relAttoMisPers.getTTpTipoMisura().getCodiKIdTipoMisura() >0)) {
                    q.setParameter("idTipoMisura", relAttoMisPers.getTTpTipoMisura().getCodiKIdTipoMisura());
                }else{
                	q.setParameter("idTipoMisura", null, Hibernate.LONG);
                }
                if ((relAttoMisPers != null) && (relAttoMisPers.getTAnNormative() != null) && (relAttoMisPers.getTAnNormative().getCodiKIdNormativa() >0)) {
                    q.setParameter("idNormativa", relAttoMisPers.getTAnNormative().getCodiKIdNormativa());
                }
                else q.setParameter("idNormativa", null, Hibernate.LONG);
                if ((relAttoMisPers != null) && (relAttoMisPers.getNumeDurataAnni() != null)) {
                    q.setParameter("durataAnni", relAttoMisPers.getNumeDurataAnni());
                } else {
                    q.setParameter("durataAnni", null, Hibernate.LONG);
                }
                if ((relAttoMisPers != null) && (relAttoMisPers.getNumeDurataMesi() != null)) {
                    q.setParameter("durataMesi", relAttoMisPers.getNumeDurataMesi());
                } else {
                    q.setParameter("durataMesi", null, Hibernate.LONG);
                }
                if ((relAttoMisPers != null) && (relAttoMisPers.getNumeDurataGiorni() != null)) {
                    q.setParameter("durataGiorni", relAttoMisPers.getNumeDurataGiorni());
                } else {
                    q.setParameter("durataGiorni", null, Hibernate.LONG);
                }
                q.setParameter("descLoc", relAttoMisPers.getDescLoc());  
                q.setParameter("descUltPresc", relAttoMisPers.getDescUltPresc());
                q.setParameter("cauzione", cauzione, Hibernate.STRING);// --
                                                                        // valori
                                                                        // ammessi
                                                                        // S o N
                q.setParameter("cauzioneProp", cauzioneProp, Hibernate.STRING);// --
                                                                        // valori
                                                                        // ammessi
                                                                        // S o N
                q.setParameter("noteCauz", noteCauz);
                q.setParameter("articolo67", articolo67);// -- valori ammessi S
                                                            // o N
                q.setParameter("articolo67Prop", articolo67Prop);// -- valori ammessi S
                                                         // o N
                
                /**/
                if (relAttoMisPers != null && relAttoMisPers.getTTpEsitoByCodiFkIdEsito1() != null && relAttoMisPers.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito() > 0) {
                    q.setParameter("idTenore", relAttoMisPers.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito());
                } else {
                    q.setParameter("idTenore", null, Hibernate.INTEGER);
                }
                
                if ((relAttoMisPers != null) && (relAttoMisPers.getTTpEsitoByCodiFkIdEsito1() != null) && (relAttoMisPers.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito() > 0)) {
                    q.setParameter("idEsito1", relAttoMisPers.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito());
                } else {
                    q.setParameter("idEsito1", null, Hibernate.LONG);
                }
                if ((relAttoMisPers != null) && (relAttoMisPers.getTTpEsitoByCodiFkIdEsito2() != null) && (relAttoMisPers.getTTpEsitoByCodiFkIdEsito2().getCodiKIdEsito() > 0)) {
                    q.setParameter("idEsito2", relAttoMisPers.getTTpEsitoByCodiFkIdEsito2().getCodiKIdEsito());
                } else {
                    q.setParameter("idEsito2", null, Hibernate.LONG);
                }
                if ((relAttoMisPers != null) && (relAttoMisPers.getTTpEsitoByCodiFkIdEsito3() != null) && (relAttoMisPers.getTTpEsitoByCodiFkIdEsito3().getCodiKIdEsito() > 0)) {
                    q.setParameter("idEsito3", relAttoMisPers.getTTpEsitoByCodiFkIdEsito3().getCodiKIdEsito());
                } else {
                    q.setParameter("idEsito3", null, Hibernate.LONG);
                }
                if ((relAttoMisPers != null) && (relAttoMisPers.getTTpEsitoFinale() != null) && (relAttoMisPers.getTTpEsitoFinale().getCodiKIdEsitoFin() > 0)) {
                    q.setParameter("idEsitoFin", relAttoMisPers.getTTpEsitoFinale().getCodiKIdEsitoFin());
                } else {
                    q.setParameter("idEsitoFin", null, Hibernate.LONG);
                }
                q.setParameter("descAltro1", relAttoMisPers.getDescAltro1());
                q.setParameter("descAltro2", relAttoMisPers.getDescAltro2());
                q.setParameter("descAltro3", relAttoMisPers.getDescAltro3());
                q.setParameter("flgValido", "S");
                q.setParameter("codiMotivo", relAttoMisPers.getTTpTipoMotivo());
                if (relAttoMisPers.getTAtto() != null && relAttoMisPers.getTAtto().getTTpTipoAtto() != null) {
                    q.setParameter("idTipoAtto", relAttoMisPers.getTAtto().getTTpTipoAtto(), Hibernate.LONG);
                } else {
                    q.setParameter("idTipoAtto", 3L, Hibernate.LONG);
                }
                q.setParameter("descUtente", vcodUtente, Hibernate.STRING);
                q.setParameter("azionePrescrAcc", azionePrescrAcc);
                q.setParameter("azionePrescrNoAcc", azionePrescrNoAcc);
                if (importoCauzione != null) {
                    q.setParameter("importoCauzione", importoCauzione, Hibernate.BIG_DECIMAL);
                } else {
                    q.setParameter("importoCauzione", null, Hibernate.BIG_DECIMAL);
                }
                q.setParameter("idDecisione", idDecisione, Hibernate.LONG);
                q.setParameter("tipoProc", tipoProc, Hibernate.LONG);
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                return l;
            }
        });
    }
	
	public void inserisciMisuraPersonaleDecisorioIGrado(final long idProc, final long idEsito, final long idAtto, final long idUffOwn, final String idUtente) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_MISURA_inserisci_rel_atto_misPersDecisorioIGrado");
				q.setParameter("idProc", idProc, Hibernate.LONG);
				q.setParameter("idEsito", idEsito, Hibernate.LONG);
				q.setParameter("idAtto", idAtto, Hibernate.LONG);
				q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
				q.setParameter("idUtente", idUtente, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Long inserisciMisuraSoggettoDiffidato(final long vidProc, final int vidUffOwn, final long vidSoggInt, final long vidTipoMisura, final String vcarattereMisura, final String vcodUtente) {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_MISURA_inserisci_misura_sogg_diffidato");
				q.setParameter("vidProc", vidProc, Hibernate.LONG);
				q.setParameter("vidUffOwn", vidUffOwn, Hibernate.INTEGER);
				q.setParameter("vidSoggInt", vidSoggInt, Hibernate.LONG);
				q.setParameter("vidTipoMisura", vidTipoMisura, Hibernate.LONG);
				q.setParameter("vcarattereMisura", vcarattereMisura, Hibernate.STRING);
				q.setParameter("vdescUtente", vcodUtente, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				Long idMisuraInserita = 0L;
				if ((l != null) && (l.size() > 0)) {
					Map<String, Object> ret = (Map<String, Object>) l.get(0);
					idMisuraInserita = (Long) ret.get("IDMISURAPERS");
				}
				return (idMisuraInserita != 0) ? idMisuraInserita : null;
			}
		});
	}

	
	public List<TMisuraPers> listaMisurePersByIdSoggetto(long idSoggettoInteressato, long idProcedimento, int idUffOwn) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TMisuraPers.class);
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUffOwn));
		criteria.add(Restrictions.eq("TSoggetto.id.codiKIdSoggInt", idSoggettoInteressato));
		List<TMisuraPers> misurePersonali = getHibernateTemplate().findByCriteria(criteria);
		List<TMisuraPers> risultati = new ArrayList<TMisuraPers>();
		for (TMisuraPers misuraPersonale : misurePersonali) {
			Set<TRelAttoMisPers> relazioni = misuraPersonale.getTRelAttoMisPerses();
			boolean assigned = false;
			for (TRelAttoMisPers relazione : relazioni) {
				if (relazione.getId().getCodiKIdProc() == idProcedimento) {
					assigned = true;
				}
				if (assigned) {
					risultati.add(misuraPersonale);
				}
			}
		}
		return risultati;
	}

	public List<TMisuraPers> listaMisurePersByIdSoggetto(long idSoggettoInteressato, int idUffOwn) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TMisuraPers.class);
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUffOwn));
		criteria.add(Restrictions.eq("TSoggetto.id.codiKIdSoggInt", idSoggettoInteressato));
		return getHibernateTemplate().findByCriteria(criteria);
	}


	public List<TRelAttoMisPers> listaRelAttoMisurePersByIdAtto(long idAtto, int idUffOwn,long idProcedimento) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TRelAttoMisPers.class);
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUffOwn));
		criteria.add(Restrictions.eq("id.codiKIdAtto", idAtto));
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		return getHibernateTemplate().findByCriteria(criteria);
	}	
	
	
	
	@SuppressWarnings("unchecked")
	public List<TRelAttoMisPers> listaRelAttoMisPersByProcEUfficio(Long idPadre, Integer idUffOwn) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TRelAttoMisPers.class);
		criteria.add(Restrictions.eq("id.codiKIdProc", idPadre));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUffOwn));
		criteria.add(Restrictions.eq("flgValido" , "S"));
		criteria.add(Restrictions.or(Restrictions.or(Restrictions.isNotNull("TTpEsitoByCodiFkIdEsito1"), Restrictions.isNotNull("TTpEsitoByCodiFkIdEsito2")), Restrictions.isNotNull("TTpEsitoByCodiFkIdEsito3")));
		criteria.setFetchMode("TTpEsitoByCodiFkIdEsito1", FetchMode.JOIN);
		criteria.setFetchMode("TTpEsitoByCodiFkIdEsito2", FetchMode.JOIN);
		criteria.setFetchMode("TTpEsitoByCodiFkIdEsito3", FetchMode.JOIN);
		List<TRelAttoMisPers> risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	public TRelAttoMisPers relAttoMisPersByProcMisPersEAtto(long idMisura, long idPadre, long idAttoPadreAggiornato, int idUffOwn) {
	    
	    DetachedCriteria criteria = DetachedCriteria.forClass(TRelAttoMisPers.class);
        criteria.add(Restrictions.eq("id.codiKIdMisuraPers", idMisura));
        criteria.add(Restrictions.eq("id.codiKIdProc", idPadre));
        criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUffOwn));
        criteria.add(Restrictions.eq("id.codiKIdAtto", idAttoPadreAggiornato));
        //criteria.add(Restrictions.eq("TTpEsitoByCodiFkIdEsito1.codiKIdEsito", 28));
        criteria.setFetchMode("TTpEsitoByCodiFkIdEsito1", FetchMode.JOIN);
        List<TRelAttoMisPers> lista = getHibernateTemplate().findByCriteria(criteria);
        if (!Utility.isDefinito(lista)) {
            return null;
        }
        TRelAttoMisPers risultato = lista.get(0);
        return risultato;
		
	}

	public void salvaRelazioneMisuraPersonalePerAggravamento(TRelAttoMisPers relazione, long idAggravamento, long idAttoAggravamento, long idMisura, int idUfficioUtente) {

		TRelAttoMisPersId idNuovo = new TRelAttoMisPersId();
		idNuovo.setCodiKIdProc(idAggravamento);
		idNuovo.setCodiKIdAtto(idAttoAggravamento);
		idNuovo.setCodiKIdMisuraPers(idMisura);
		idNuovo.setCodiKIdUffOwner(idUfficioUtente);
		TRelAttoMisPers relazioneNuova = new TRelAttoMisPers();
		copiaDatiRelAttoMisPers(relazioneNuova, relazione);
		relazioneNuova.setId(idNuovo);
		getHibernateTemplate().save(relazioneNuova);
		getHibernateTemplate().flush();
	}

	private void copiaDatiRelAttoMisPers(TRelAttoMisPers nuova, TRelAttoMisPers vecchia) {
		
		if(vecchia.getTCauzione()!=null){
			nuova.setTCauzione(vecchia.getTCauzione());
		}
		//nuova.setCodiFkIdTenore(vecchia.getCodiFkIdTenore());
		/**TODO	
		nuova.setCodiFkIdTipoAtto(9L);
		*/
		//nuova.getTAtto().setTTpTipoAtto(new TTpTipoAtto(9));
		nuova.setTTpTipoMotivo(vecchia.getTTpTipoMotivo());
		nuova.setDescUtente(vecchia.getDescUtente());
		nuova.setDescAltro1(vecchia.getDescAltro1());
		nuova.setDescAltro2(vecchia.getDescAltro2());
		nuova.setDescAltro3(vecchia.getDescAltro3());
		nuova.setDescLoc(vecchia.getDescLoc());
		nuova.setDescUltPresc(vecchia.getDescUltPresc());
		nuova.setFlgValido(vecchia.getFlgValido());
		nuova.setNumeDurataAnni(vecchia.getNumeDurataAnni());
		nuova.setNumeDurataGiorni(vecchia.getNumeDurataGiorni());
		nuova.setNumeDurataMesi(vecchia.getNumeDurataMesi());
		nuova.setTMisuraPers(vecchia.getTMisuraPers());
		nuova.setTAnNormative(vecchia.getTAnNormative());
		nuova.setTProcedimento(vecchia.getTProcedimento());
		nuova.setTTpEsitoFinale(vecchia.getTTpEsitoFinale());
		nuova.setTTpTipoMisura(vecchia.getTTpTipoMisura());
		nuova.setTAnUfficio(vecchia.getTAnUfficio());
	}

	@SuppressWarnings("unchecked")
	public List<TPersonaFisica> getSogIntSenzaDecisorio(final long idProcedimento, final long idAtto, final long idUffOwn, final String tipiMisura,final String descAtto) {

		return (List<TPersonaFisica>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_MISURA_soggettiInteressatiSenzaDecisione");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("idProc", idProcedimento);
				q.setParameter("idAtto", idAtto);
				q.setParameter("descAtto", descAtto);
				q.setParameter("tipiMisura", tipiMisura);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List<TBene> getBeniSenzaDecisorio(final long idProcedimento, final long idAtto, final long idUffOwn,final String descAtto) {

		return (List<TBene>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_MISURA_beniSenzaDecisione");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("idProc", idProcedimento);
				q.setParameter("idAtto", idAtto);
				q.setParameter("descAtto", descAtto);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});
	}

	public void eliminaRelazioneAttoMisuraPersonale(TRelAttoMisPers relazione) {

		getHibernateTemplate().delete(relazione);
	}

	public void eliminaMisuraPersonale(TMisuraPers misuraPersonale) {

		getHibernateTemplate().delete(misuraPersonale);
	}

    public List listaIdMisSpecificheProposta(final long vidProc, final long vidUffOwn, final long vidSoggInt, final long vidAtto, final String vcarMisura) {

        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_MISURA_misuraSpecAtto");
                q.setParameter("vidProc", vidProc, Hibernate.LONG);
                q.setParameter("vidUffOwn", vidUffOwn, Hibernate.LONG);
                q.setParameter("vidSoggInt", vidSoggInt, Hibernate.LONG);
                q.setParameter("vidAtto", vidAtto, Hibernate.LONG);
                q.setParameter("vcarMisura", vcarMisura, Hibernate.STRING);
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                return l;
            }
        });
    }
    
    public Long aggiornaTipoProcPrevProc(final long idProc, final long idUffOwn, final String codUtente) {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			@SuppressWarnings("unused")
			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Long idProcedimento = null;
				Query query = session.getNamedQuery("PG_PROCEDIMENTO_aggiorna_tipoProc_Prev_Proc");
				query.setParameter("idProc", idProc, Hibernate.LONG);
				query.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
				query.setParameter("descUtente", codUtente);
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List list = query.list();
				Iterator it = list.iterator();
				while (it.hasNext()) {
					Map<String, Object> row = (Map<String, Object>) it.next();
					idProcedimento = (Long) row.get("IDPROC");
				}
				return idProcedimento;
			}
		});
	}

    
    @SuppressWarnings("unchecked")
    public void convalidaNonConvalidaSequestro(final long idProcedimento, final long idAtto, final long idBene, final int idTipoInteresse ,final long idSoggetto, 
    		final long idTipoMisura, final int idUffOwn, final String descUtente, final String tipoOperazione, final String nomeMetodo) {

        getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_MISURA_convalidaNonConvalidaSequestro");
                q.setParameter("idProc", idProcedimento, Hibernate.LONG);
                q.setParameter("idAtto", idAtto, Hibernate.LONG);
                q.setParameter("idBene", idBene, Hibernate.LONG);
                q.setParameter("idTipoInteresse", idTipoInteresse, Hibernate.INTEGER);
                q.setParameter("idSoggetto", idSoggetto, Hibernate.LONG);
                if(idTipoMisura > 0){
                	q.setParameter("idTipoMisura", idTipoMisura, Hibernate.LONG);
                }else{
                	q.setParameter("idTipoMisura", null, Hibernate.LONG);
                }
                q.setParameter("idUffOwn", idUffOwn, Hibernate.INTEGER);
                q.setParameter("descUtente", descUtente, Hibernate.STRING);
                q.setParameter("tipoOperazione", tipoOperazione, Hibernate.STRING);
                q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                return l;
            }
        });
    }
    
    public Date getDataDefinitivitaDecisorioIGradoRiesame(long idMisura, long idProc, long idAtto, int idUffOwn) {
	    
	    DetachedCriteria criteria = DetachedCriteria.forClass(TDefPers.class);

        criteria.add(Restrictions.eq("id.codiKIdProc", idProc));
        criteria.add(Restrictions.eq("id.codiKIdMisuraPers", idMisura));
        criteria.add(Restrictions.eq("id.codiKIdAtto", idAtto));
        criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUffOwn));
        List<TDefPers> lista = getHibernateTemplate().findByCriteria(criteria);
        if (!Utility.isDefinito(lista)) {
            return null;
        }
       
        TDefPers defPers = lista.get(0);
        return defPers.getDataDefinitivita();
		
	}
    
    //2 GRADO
    public List<DettaglioMisuraPersonaleDto> caricaMisuraPersonaleIIGrado(final long vidProc, final long vidAtto, final long vidUffOwn, final long idSoggInt) {

		return (List<DettaglioMisuraPersonaleDto>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_MISURA_caricaMisuraPersIIGrado");
				q.setParameter("idProc", vidProc, Hibernate.LONG);
				if (vidAtto > 0) {
					q.setParameter("idAtto", vidAtto, Hibernate.LONG);
				} else {
					q.setParameter("idAtto", null, Hibernate.LONG);
				}
				q.setParameter("idUffOwn", vidUffOwn, Hibernate.LONG);
				q.setParameter("idSoggInt", idSoggInt, Hibernate.LONG);
				q.setResultTransformer(Transformers.aliasToBean(DettaglioMisuraPersonaleDto.class));
				List<DettaglioMisuraPersonaleDto> l = q.list();
				if ((l != null) && (l.size() > 0)) {
					return l;
				}
				return null;
			}
		});
	}
    
    //3 GRADO
    public List<DettaglioMisuraPersonaleDto> caricaMisuraPersonaleIIIGrado(final long vidProc, final long vidAtto, final long vidUffOwn, final long idSoggInt) {

		return (List<DettaglioMisuraPersonaleDto>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_MISURA_caricaMisuraPersIIIGrado");
				q.setParameter("idProc", vidProc, Hibernate.LONG);
				if (vidAtto > 0) {
					q.setParameter("idAtto", vidAtto, Hibernate.LONG);
				} else {
					q.setParameter("idAtto", null, Hibernate.LONG);
				}
				q.setParameter("idUffOwn", vidUffOwn, Hibernate.LONG);
				q.setParameter("idSoggInt", idSoggInt, Hibernate.LONG);
				q.setResultTransformer(Transformers.aliasToBean(DettaglioMisuraPersonaleDto.class));
				List<DettaglioMisuraPersonaleDto> l = q.list();
				if ((l != null) && (l.size() > 0)) {
					return l;
				}
				return null;
			}
		});
	}   
    
    public List listaIdMisSpecificheRicorso(final long vidProc, final long vidUffOwn, final long vidSoggInt, final long vidAtto, final String vcarMisura) {

        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_MISURA_misuraSpecAttoRicorso");
                q.setParameter("vidProc", vidProc, Hibernate.LONG);
                q.setParameter("vidUffOwn", vidUffOwn, Hibernate.LONG);
                q.setParameter("vidSoggInt", vidSoggInt, Hibernate.LONG);
                q.setParameter("vidAtto", vidAtto, Hibernate.LONG);
                q.setParameter("vcarMisura", vcarMisura, Hibernate.STRING);
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                return l;
            }
        });
    }
    public void inserisciMisuraPersonaleDecIIGrado(final long vidProc, final long vidAtto, final long vidUffOwn, final TRelAttoMisPers relAttoMisPers, final long idSoggInt, final String cauzione, final String noteCauz, final String articolo67, final String vcodUtente, final BigDecimal importoCauzione,  final long idDecisione, final long tipoProc,final String idPrescrizioni,final String visibilita) {

        getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_MISURA_inserisci_rel_atto_misPersDecIIGrado");
                q.setParameter("idProc", vidProc);
                if (vidAtto > 0) {
                    q.setParameter("idAtto", vidAtto);
                } else {
                    q.setParameter("idAtto", null, Hibernate.LONG);
                }
                q.setParameter("idUffOwn", vidUffOwn);
                q.setParameter("idSoggInt", idSoggInt);
                if ((relAttoMisPers != null) && (relAttoMisPers.getTTpTipoMisura() != null)) {
                    q.setParameter("idTipoMisura", relAttoMisPers.getTTpTipoMisura().getCodiKIdTipoMisura());
                }else{
                	q.setParameter("idTipoMisura", null, Hibernate.LONG);
                }
                if ((relAttoMisPers != null) && (relAttoMisPers.getTAnNormative() != null)) {
                    q.setParameter("idNormativa", relAttoMisPers.getTAnNormative().getCodiKIdNormativa());
                }
                else 
                 q.setParameter("idNormativa", null, Hibernate.LONG);
                if ((relAttoMisPers != null) && (relAttoMisPers.getNumeDurataAnni() != null)) {
                    q.setParameter("durataAnni", relAttoMisPers.getNumeDurataAnni());
                } else {
                    q.setParameter("durataAnni", null, Hibernate.LONG);
                }
                if ((relAttoMisPers != null) && (relAttoMisPers.getNumeDurataMesi() != null)) {
                    q.setParameter("durataMesi", relAttoMisPers.getNumeDurataMesi());
                } else {
                    q.setParameter("durataMesi", null, Hibernate.LONG);
                }
                if ((relAttoMisPers != null) && (relAttoMisPers.getNumeDurataGiorni() != null)) {
                    q.setParameter("durataGiorni", relAttoMisPers.getNumeDurataGiorni());
                } else {
                    q.setParameter("durataGiorni", null, Hibernate.LONG);
                }
                q.setParameter("descLoc", relAttoMisPers.getDescLoc());  
                q.setParameter("descUltPresc", relAttoMisPers.getDescUltPresc());
                q.setParameter("cauzione", cauzione, Hibernate.STRING);// --
                                                                        // valori
                                                                        // ammessi
                                                                        // S o N
           
                                                                        // valori
                                                                        // ammessi
                                                                        // S o N
                q.setParameter("noteCauz", noteCauz);
                q.setParameter("articolo67", articolo67);// -- valori ammessi S
                                                            // o N
              
                
                /*GENNARO    QUESTI CAMPI NON SERVONO A NIENTE*/
                
                q.setParameter("flgValido", "S");
                q.setParameter("codiMotivo", relAttoMisPers.getTTpTipoMotivo());
                if (relAttoMisPers.getTAtto() != null && relAttoMisPers.getTAtto().getTTpTipoAtto() != null) {
                    q.setParameter("idTipoAtto", relAttoMisPers.getTAtto().getTTpTipoAtto(), Hibernate.LONG);
                } else {
                    q.setParameter("idTipoAtto", 3L, Hibernate.LONG);
                }
                q.setParameter("descUtente", vcodUtente, Hibernate.STRING);
               
                if (importoCauzione != null) {
                    q.setParameter("importoCauzione", importoCauzione, Hibernate.BIG_DECIMAL);
                } else {
                    q.setParameter("importoCauzione", null, Hibernate.BIG_DECIMAL);
                }
                q.setParameter("idDecisione", idDecisione, Hibernate.LONG);
                q.setParameter("tipoProc", tipoProc, Hibernate.LONG);
                q.setParameter("idPrescrizioni",idPrescrizioni,Hibernate.STRING);
                q.setParameter("visibilita",visibilita,Hibernate.STRING);
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                return l;
            }
        });
    }
    
    
	/**
	 * inserisciMisuraPersonaleDecIIIGrado
	 * @param vidProc
	 * @param vidAtto
	 * @param vidUffOwn
	 * @param relAttoMisPers
	 * @param idSoggInt
	 * @param idDecisione
	 * @param tipoProc
	 */
    
    public void inserisciMisuraPersonaleDecIIIGrado(final long vidProc, final long vidAtto, final long vidUffOwn, final long idSoggInt,  final String vcodUtente,  final long idDecisione, final long tipoProc,final Date dataRicezione,final long annoSentenza,final long numeroSentenza) {

        getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_MISURA_inserisci_rel_atto_misPersDecIIIGrado");
                
                q.setParameter("idProc", vidProc);
                if (vidAtto > 0) {
                    q.setParameter("idAtto", vidAtto);
                } else {
                    q.setParameter("idAtto", null, Hibernate.LONG);
                }
                q.setParameter("idUffOwn", vidUffOwn);
                q.setParameter("idSoggInt", idSoggInt);
 
     
               
  
                q.setParameter("idDecisione", idDecisione, Hibernate.LONG);
                q.setParameter("idTipoProc", tipoProc, Hibernate.LONG);
                
                q.setParameter("DATA_RICEZIONE", dataRicezione, Hibernate.DATE);
                q.setParameter("ANNO_SENTENZA", annoSentenza, Hibernate.LONG);
                q.setParameter("NUMERO_SENTENZA", numeroSentenza, Hibernate.LONG);
                q.setParameter("descUtente", vcodUtente, Hibernate.STRING);

                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                return l;
         
           
            
            }
        });
    }    

    public BigDecimal getImportoCauzione(long vidProc, long vidAtto, Integer vidUffOwn)
    {
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelAttoMisPers.class);
		criteria.add(Restrictions.eq("id.codiKIdProc", vidProc));
		criteria.add(Restrictions.eq("id.codiKIdAtto", vidAtto));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", vidUffOwn.intValue()));
		criteria.add(Restrictions.eq("TTpTipoMisura.codiKIdTipoMisura", 25));
		List<TRelAttoMisPers> lista = getHibernateTemplate().findByCriteria(criteria);
		if(lista != null)
		{
			TRelAttoMisPers misura = (TRelAttoMisPers)lista.get(0);
			return misura.getTCauzione().getImpoCauzione();
		}
		else
			return null;
    }
}
