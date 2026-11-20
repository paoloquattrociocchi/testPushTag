package it.giustizia.smp.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.SQLException;
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
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import it.giustizia.smp.dto.KeyMessageDto;
import it.giustizia.smp.dto.pg_atto.AttoDocumentaleDto;
import it.giustizia.smp.dto.pg_atto.DettaglioAttoDto;
import it.giustizia.smp.dto.pg_atto.ImpugnazioneCassellarioDTO;
import it.giustizia.smp.dto.pg_atto.RedazioneSchedaDto;
import it.giustizia.smp.dto.pg_cameralini.IntestatarioDto;
import it.giustizia.smp.dto.pg_procedimento.SogIntDettaglioProcedimentoDto;
import it.giustizia.smp.dto.pg_procedimento.SogSpesePDettaglioProcedimentoDto;
import it.giustizia.smp.dto.pg_stralcio.RicercaStralcioDto;
import it.giustizia.smp.model.TAnOperFinanzId;
import it.giustizia.smp.model.TAnUfficio;
import it.giustizia.smp.model.TAnimali;
import it.giustizia.smp.model.TAnimaliId;
import it.giustizia.smp.model.TAtto;
import it.giustizia.smp.model.TAttoGenerico;
import it.giustizia.smp.model.TAttoId;
import it.giustizia.smp.model.TAvvocati;
import it.giustizia.smp.model.TAzienda;
import it.giustizia.smp.model.TAziendaId;
import it.giustizia.smp.model.TBene;
import it.giustizia.smp.model.TBeneFin;
import it.giustizia.smp.model.TBeneFinId;
import it.giustizia.smp.model.TBeneImmat;
import it.giustizia.smp.model.TBeneImmatId;
import it.giustizia.smp.model.TBeneImmobile;
import it.giustizia.smp.model.TBeneImmobileId;
import it.giustizia.smp.model.TCertificatoPenale;
import it.giustizia.smp.model.TCollezioni;
import it.giustizia.smp.model.TCollezioniId;
import it.giustizia.smp.model.TComunicazione;
import it.giustizia.smp.model.TContante;
import it.giustizia.smp.model.TContanteId;
import it.giustizia.smp.model.TDecreto;
import it.giustizia.smp.model.TDecretoId;
import it.giustizia.smp.model.TDefIstGen;
import it.giustizia.smp.model.TDefIstGenId;
import it.giustizia.smp.model.TDefPatr;
import it.giustizia.smp.model.TDefPers;
import it.giustizia.smp.model.TDenaro;
import it.giustizia.smp.model.TDenaroId;
import it.giustizia.smp.model.TImpugnazione;
import it.giustizia.smp.model.TImpugnazioneId;
import it.giustizia.smp.model.TIstanza;
import it.giustizia.smp.model.TNotifDest;
import it.giustizia.smp.model.TNotifRicevute;
import it.giustizia.smp.model.TPersonaFisica;
import it.giustizia.smp.model.TPersonaFisicaId;
import it.giustizia.smp.model.TPersonaGiur;
import it.giustizia.smp.model.TPersonaGiurId;
import it.giustizia.smp.model.TProcedimento;
import it.giustizia.smp.model.TProvFg;
import it.giustizia.smp.model.TRelAtti;
import it.giustizia.smp.model.TRelAttoComunicaz;
import it.giustizia.smp.model.TRelAttoIstGen;
import it.giustizia.smp.model.TRelAttoMisPatr;
import it.giustizia.smp.model.TRelAttoMisPers;
import it.giustizia.smp.model.TRelAttoMisPersId;
import it.giustizia.smp.model.TRelAttoProc;
import it.giustizia.smp.model.TRelBeneAtto;
import it.giustizia.smp.model.TRelBeneAttoId;
import it.giustizia.smp.model.TRelMagAtto;
import it.giustizia.smp.model.TRelMagProc;
import it.giustizia.smp.model.TRelProcBene;
import it.giustizia.smp.model.TRelProcBeneId;
import it.giustizia.smp.model.TRelProcSogint;
import it.giustizia.smp.model.TRelProcSogintAtto;
import it.giustizia.smp.model.TRelProcSogintAttoId;
import it.giustizia.smp.model.TRelProcSogintId;
import it.giustizia.smp.model.TRelProcSogintVer;
import it.giustizia.smp.model.TRelSoggImp;
import it.giustizia.smp.model.TRelSoggIst;
import it.giustizia.smp.model.TRelSoggSpese;
import it.giustizia.smp.model.TSoggetto;
import it.giustizia.smp.model.TSoggettoId;
import it.giustizia.smp.model.TTpCatAtto;
import it.giustizia.smp.model.TTpTipoFilePdoc;
import it.giustizia.smp.model.TUdienza;
import it.giustizia.smp.model.TUdienzaId;
import it.giustizia.smp.model.TVeicolo;
import it.giustizia.smp.model.TVeicoloId;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;

/**
 * AttoDaoImpl
 * 
 * @author Almaviva S.p.A. classe dao all'interno della quale viene fatto l'accesso al DB
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class AttoDaoImpl extends HibernateDaoSupport implements AttoDao {
	
	public Long aggiornaIstanzaSoggettoDiffidato(final long idUffOwn, final long idDestinatario, final long idProcedimento, final TAtto atto,
			final String codUtente, final TIstanza istanza) {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Long idAtto = null;
				Query q = session.getNamedQuery("PG_ATTO_aggiornaIstanzaSoggettoDiffidato");
				q.setParameter("idUffOwn", idUffOwn);
				if (idUffOwn > 0) {
					q.setParameter("idMittente", idUffOwn, Hibernate.LONG);
				} else {
					q.setParameter("idMittente", null, Hibernate.LONG);
				}
				if (idDestinatario > 0) {
					q.setParameter("idDestinatario", idDestinatario, Hibernate.LONG);
				} else {
					q.setParameter("idDestinatario", null, Hibernate.LONG);
				}
				q.setParameter("idProc", idProcedimento, Hibernate.LONG);
				if (atto.getDataEmissione() != null) {
					q.setParameter("dataPresentazione", atto.getDataEmissione(), Hibernate.DATE);
				} else {
					q.setParameter("dataPresentazione", null, Hibernate.DATE);
				}
				if (atto.getDataDeposito() != null) {
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				} else {
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				}
				q.setParameter("modalitaTrasmissione", istanza.getTTpModPerv().getCodiKIdModalita(), Hibernate.INTEGER);
				if (istanza.getDescAltroMod() != null) {
					q.setParameter("descAltroMod", istanza.getDescAltroMod(), Hibernate.STRING);
				} else {
					q.setParameter("descAltroMod", null, Hibernate.STRING);
				}
				if (atto.getDataRicezione() != null) {
					q.setParameter("dataRicezioneDoc", atto.getDataRicezione(), Hibernate.DATE);
				} else {
					q.setParameter("dataRicezioneDoc", null, Hibernate.DATE);
				}
				if (atto.getDataRegistrazione() != null) {
					q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				} else {
					q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				}
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				q.setParameter("descUtente", codUtente, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				Iterator iter = l.iterator();
				while (iter.hasNext()) {
					Map map = (Map) iter.next();
					idAtto = (Long) map.get("IDATTO");
				}
				return idAtto;
			}
		});
	}

	public long aggiornaAccertamento(final long idUfficio, final TAtto atto, final String codiceUtente, final long idProc, final long idStatoProc,
			final TIstanza istanza) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_aggiornaAccertamento");
				q.setParameter("idUffOwn", idUfficio, Hibernate.LONG);
				q.setParameter("idCodUtente", codiceUtente, Hibernate.STRING);
				q.setParameter("idProc", idProc, Hibernate.LONG);
				q.setParameter("idStatoProc", idStatoProc, Hibernate.LONG);
				q.setParameter("idAtto", atto.getId().getCodiKIdAtto(), Hibernate.LONG);
				q.setParameter("numeroRiferimento", istanza.getDescNumeRif(), Hibernate.STRING);
				q.setParameter("dataInformativa", atto.getDataEmissione(), Hibernate.DATE);
				q.setParameter("note", atto.getDescNote(), Hibernate.STRING);
				q.setParameter("dataRicezione", atto.getDataRicezione(), Hibernate.DATE);
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long id = (Long) first.get("IDATTO");
				return id;
			}
		});
	}

	public long applicaDefinitivita(final TRelBeneAttoId idRelBeneAtto, final TRelAttoMisPersId idRelAttoMisPers, final TProcedimento procedimento,
			final String codUtente, final long idTipoAtto, final Date dataDefinitivita, final long idUffProcProp, final Long idDestinatario) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_applicaDefinitivita");
				q.setParameter("idAtto", idRelBeneAtto.getCodiKIdAtto(), Hibernate.LONG);
				q.setParameter("idTipoAtto", idTipoAtto, Hibernate.LONG);
				q.setParameter("idProcedimento", idRelBeneAtto.getCodiKIdProc(), Hibernate.LONG);
				q.setParameter("numeProc", procedimento.getNumeProc());
				q.setParameter("annoProc", procedimento.getNumeAnnoProc());
				q.setParameter("idMittente", idRelBeneAtto.getCodiKIdUffOwner(), Hibernate.INTEGER);
				q.setParameter("idUffProcProp", idUffProcProp, Hibernate.LONG);
				if ((idDestinatario != null) && (idDestinatario > 0)) {
					q.setParameter("idDestinatario", idDestinatario, Hibernate.LONG);
				} else {
					q.setParameter("idDestinatario", null, Hibernate.LONG);
				}
				q.setParameter("descUtente", codUtente, Hibernate.STRING);
				q.setParameter("dataDefinitivita", dataDefinitivita, Hibernate.DATE);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long id = (Long) first.get("IDFLUSSO");
				return id;
			}
		});
	}

	/**
	 * copiaMisBeneAtto
	 * 
	 * @param idProcedimento
	 * @param idAtto
	 * @param idBene
	 * @param idUffOwn
	 * @param idCodiceUtente
	 */
	public void copiaMisBeneAtto(final long idProcedimento, final long idAttoDecreto, final long idBene, final int idUffOwn, final String codUtente,
			final String flagRevoca, final String nomeMetodo) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_copiaMisBeneAtto");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("descUtente", codUtente);
				q.setParameter("idProcedimento", idProcedimento);
				q.setParameter("idBene", idBene);
				q.setParameter("idAttoDecreto", idAttoDecreto);
				q.setParameter("flagRevoca", flagRevoca);
				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});
	}

	/**
	 * definitivitaMisPatrAttoRegistratoSalva
	 * 
	 * @param dataDefinitivita
	 * @param idBene
	 * @param idSoggetto
	 * @param idInteresse
	 * @param idProcedimento
	 * @param idUfficioUtente
	 * @param idAtto
	 * @param tipoAtto
	 * @param tipoMisura
	 * @param codUtente
	 * @return void
	 */
	public void definitivitaMisPatrAttoRegistratoSalva(final Date dataDefinitivita, final Long idBene, final long idSoggetto, final long idInteresse,
			final long idProcedimento, final Integer idUfficioUtente, final long idAtto, final long tipoAtto, final long tipoMisura,
			final String codUtente) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_definitivita_atto_registrato_mis_patr_salva");
				q.setParameter("dataDefinitivita", dataDefinitivita);
				q.setParameter("idBene", idBene);
				q.setParameter("idSoggetto", idSoggetto);
				q.setParameter("idInteresse", idInteresse);
				q.setParameter("idProcedimento", idProcedimento);
				q.setParameter("idUfficioUtente", idUfficioUtente.intValue());
				q.setParameter("idAtto", idAtto);
				q.setParameter("descUtente", codUtente);
				if (tipoMisura != 0)
					q.setParameter("tipoMisura", tipoMisura);
				else
					q.setParameter("tipoMisura", null, Hibernate.LONG);
				q.setParameter("tipoAtto", tipoAtto);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});
	}

	/**
	 * definitivitaMisPersAttoRegistratoSalva
	 * 
	 * @param dataDefinitivita
	 * @param idProcedimento
	 * @param idUfficioUtente
	 * @param idAtto
	 * @param idMisura
	 * @param tipoMisura
	 * @param tipoAtto
	 * @param codUtente
	 * @return void
	 */
	public void definitivitaMisPersAttoRegistratoSalva(final Date dataDefinitivita, final long idProcedimento, final Integer idUfficioUtente,
			final long idAtto, final long idMisura, final long tipoMisura, final long tipoAtto, final String codUtente) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_definitivita_atto_registrato_mis_pers_salva");
				q.setParameter("dataDefinitivita", dataDefinitivita);
				q.setParameter("idMisura", idMisura);
				q.setParameter("idProcedimento", idProcedimento);
				q.setParameter("idUfficioUtente", idUfficioUtente.intValue());
				q.setParameter("idAtto", idAtto);
				q.setParameter("descUtente", codUtente);
				q.setParameter("tipoMisura", tipoMisura);
				q.setParameter("tipoAtto", tipoAtto);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});
	}
	
	
	/**
	 * definitivitaMisPatrCancella
	 * 
	 * @param idBene
	 * @param idSoggetto
	 * @param idInteresse
	 * @param idProcedimento
	 * @param idUfficioUtente
	 * @param idAtto
	 * @param tipoAtto
	 * @param tipoMisura
	 * @param codUtente
	 * @return void
	 */
	public void definitivitaMisPatrCancella( final Long idBene, final long idSoggetto, final long idInteresse,
			final long idProcedimento, final Integer idUfficioUtente, final long idAtto, final long tipoAtto, final long tipoMisura,
			final String codUtente) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_definitivita_mis_patr_cancella");
				q.setParameter("idBene", idBene);
				q.setParameter("idSoggetto", idSoggetto);
				q.setParameter("idInteresse", idInteresse);
				q.setParameter("idProcedimento", idProcedimento);
				q.setParameter("idUfficioUtente", idUfficioUtente.intValue());
				q.setParameter("idAtto", idAtto);
				q.setParameter("descUtente", codUtente);
				if (tipoMisura != 0)
					q.setParameter("tipoMisura", tipoMisura);
				else
					q.setParameter("tipoMisura", null, Hibernate.LONG);
				q.setParameter("tipoAtto", tipoAtto);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});
	}

	/**
	 * definitivitaMisPersCancella
	 * 
	 * @param idProcedimento
	 * @param idUfficioUtente
	 * @param idAtto
	 * @param idMisura
	 * @param tipoMisura
	 * @param tipoAtto
	 * @param codUtente
	 * @return void
	 */
	public void definitivitaMisPersCancella( final long idProcedimento, final Integer idUfficioUtente,
			final long idAtto, final long idMisura, final long tipoMisura, final long tipoAtto, final String codUtente) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_definitivita_mis_pers_cancella");

				q.setParameter("idMisura", idMisura);
				q.setParameter("idProcedimento", idProcedimento);
				q.setParameter("idUfficioUtente", idUfficioUtente.intValue());
				q.setParameter("idAtto", idAtto);
				q.setParameter("descUtente", codUtente);
				q.setParameter("tipoMisura", tipoMisura);
				q.setParameter("tipoAtto", tipoAtto);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});
	}	

	/**
	 * definitivitaAttoRegistratoConfermaInvia
	 * 
	 * @param idProcedimento
	 * @param idAtto
	 * @param idUfficioUtente
	 * @param idProcUdi
	 * @param idProcProp
	 * @param idProcGen
	 * @param codUtente
	 * @param aggiornaStatoProc
	 * @return void
	 */
	public void definitivitaAttoRegistratoConfermaInvia(final long idProcedimento, final long idAtto, final int idUfficioUtente,
			final long idProcUdi, final long idProcProp, final long idProcGen, final String codUtente, final int aggiornaStatoProc) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_definitivita_atto_registrato_conf_invia");
				q.setParameter("idProcedimento", idProcedimento);
				q.setParameter("idAtto", idAtto);
				q.setParameter("idUfficioUtente", idUfficioUtente);
				q.setParameter("idProcUdi", idProcUdi);
				q.setParameter("idProcProp", idProcProp);
				q.setParameter("idProcGen", idProcGen);
				q.setParameter("descUtente", codUtente);
				q.setParameter("aggiornaStatoProc", aggiornaStatoProc);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});

	}
	
	public void definitivitaAttoRegistratoConfermaInviaData(final long idProcedimento, final long idAtto, final int idUfficioUtente,
			final long idProcUdi, final long idProcProp, final long idProcGen, final String codUtente, final int aggiornaStatoProc, final Date dataDef) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_definitivita_atto_registrato_conf_invia_DATA");
				q.setParameter("idProcedimento", idProcedimento);
				q.setParameter("idAtto", idAtto);
				q.setParameter("idUfficioUtente", idUfficioUtente);
				q.setParameter("idProcUdi", idProcUdi);
				q.setParameter("idProcProp", idProcProp);
				q.setParameter("idProcGen", idProcGen);
				q.setParameter("descUtente", codUtente);
				q.setParameter("aggiornaStatoProc", aggiornaStatoProc);
				q.setParameter("data_definitivita", dataDef);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});

	}	

	public void definitivitaIIGradoConfermaInvia(final long idProcedimento, final long idAtto, final int idUffOwn, final long idDest1,
			final long idDest2, final long idDest3, final String codUtente, final int aggiornaStatoProc) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_definitivita_II_grado");
				q.setParameter("idProcedimento", idProcedimento);
				q.setParameter("idAtto", idAtto);
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("idDest1", idDest1);
				q.setParameter("idDest2", idDest2);
				q.setParameter("idDest3", idDest3);
				q.setParameter("descUtente", codUtente);
				q.setParameter("aggiornaStatoProc", aggiornaStatoProc);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});

	}

	public void definitivitaRevocazioneConfiscaConfermaInvia(final long idProcedimento, final long idAtto, final int idUffOwn, final long idDest1,
			final long idDest2, final long idDest3, final String codUtente, final int aggiornaStatoProc) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_definitivita_revocazione_confisca");
				q.setParameter("idProcedimento", idProcedimento);
				q.setParameter("idAtto", idAtto);
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("idDest1", idDest1);
				q.setParameter("idDest2", idDest2);
				q.setParameter("idDest3", idDest3);
				q.setParameter("descUtente", codUtente);
				q.setParameter("aggiornaStatoProc", aggiornaStatoProc);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});

	}

	/**
	 * dettaglioAtto
	 * 
	 * @param idAtto
	 * @param idProcedimento
	 * @param codiceUtente
	 * @return Map<String, Object>
	 */
	public DettaglioAttoDto dettaglioAtto(final int idUffOwn, final long idAtto, final long idProcedimento, final String codiceUtente) {

		return (DettaglioAttoDto) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_dettaglioAtto");
				q.setParameter("idAtto", idAtto);
				q.setParameter("idProcedimento", idProcedimento);
				q.setParameter("descUtente", codiceUtente);
				q.setParameter("idUffOwner", idUffOwn);
				// q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				q.setResultTransformer(Transformers.aliasToBean(DettaglioAttoDto.class));
				List l = q.list();
				if ((l != null) && (l.size() > 0)) {
					return l.get(0);
				}
				return null;
			}
		});
	}

	/**
	 * dettaglioMagistratoPerAtto
	 * 
	 * @param idUffOwn
	 * @param idAtto
	 * @param idMagistrato
	 * @param descRuolo
	 * @return Map<String, Object>
	 */
	
	public Map<String, Object> dettaglioMagistratoPerAtto(final Integer idUffOwn, final long idAtto, final long idMagistrato, final String descRuolo) {

		return (Map<String, Object>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_dettaglio_magistrato_per_atto");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("idAtto", idAtto);
				q.setParameter("idMagistrato", idMagistrato);
				q.setParameter("idRuolo", Integer.parseInt(descRuolo));
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				if ((l != null) && (l.size() > 0)) {
					return l.get(0);
				}
				return null;
			}
		});
	}

	/**
	 * eliminaMisBeneAtto
	 * 
	 * @param idProcedimento
	 * @param idAtto
	 * @param idBene
	 * @param idUffOwn
	 * @param idPersona
	 * @param idTitoloInt
	 */
	public void eliminaMisBeneAtto(final long idProcedimento, final long idAtto, final long idBene, final int idUffOwn, final long idPersona,
			final long idTitoloInt) {

		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				Query q = session.getNamedQuery("PG_ATTO_eliminaMisBeneAtto");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("idProcedimento", idProcedimento);
				q.setParameter("idBene", idBene);
				if (idAtto > 0) {
					q.setParameter("idAtto", idAtto, Hibernate.LONG);
				} else {
					q.setParameter("idAtto", null, Hibernate.LONG);
				}
				if (idPersona > 0) {
					q.setParameter("idPersona", idPersona, Hibernate.LONG);
				} else {
					q.setParameter("idPersona", null, Hibernate.LONG);
				}
				if (idTitoloInt > 0) {
					q.setParameter("idTitoloInt", idTitoloInt, Hibernate.LONG);
				} else {
					q.setParameter("idTitoloInt", null, Hibernate.LONG);
				}
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});
	}

	/**
	 * eliminaRelMagAtto
	 * 
	 * @param idUffOwn
	 * @param idAtto
	 * @param idMagistrato
	 * @param descRuolo
	 */
	public void eliminaRelMagAtto(final Integer idUffOwn, final long idAtto, final long idMagistrato, final String descRuolo) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_elimina_mag_per_Atto");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("idAtto", idAtto);
				q.setParameter("idMagistrato", idMagistrato);
				q.setParameter("idRuolo", Integer.parseInt(descRuolo));
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});
	}

	/**
	 * esecuzioneAttoRegistrato
	 * 
	 * @param dataEsecuzione
	 * @param idBene
	 * @param idSoggetto
	 * @param idInteresse
	 * @param idProcedimento
	 * @param idUfficioUtente
	 * @param idAtto
	 * @param codUtente
	 */
	public void esecuzioneAttoRegistrato(final Date dataEsecuzione, final Long idBene, final Long idSoggetto, final Long idInteresse,
			final long idProcedimento, final int idUfficioUtente, final long idAtto, final String codUtente) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_esecuzione_atto_registrato");
				q.setParameter("dataEsecuzione", dataEsecuzione, Hibernate.DATE);
				q.setParameter("idBene", idBene);
				q.setParameter("idSoggetto", idSoggetto);
				q.setParameter("idInteresse", idInteresse);
				q.setParameter("idProcedimento", idProcedimento);
				q.setParameter("idUfficioUtente", idUfficioUtente);
				q.setParameter("idAtto", idAtto);
				q.setParameter("descUtente", codUtente);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});
	}

	/**
	 * getAttiByProcedimento
	 * 
	 * @param idProcedimento
	 * @param tipoAtto
	 * @param listaCategoriaAtto
	 * @return List<TAtto>
	 */
	public List<TAtto> getAttiByProcedimento(final long idProcedimento, final long tipoAtto, final String listaCategoriaAtto) {

		return (List<TAtto>)getHibernateTemplate().execute(new HibernateCallback() {

			public List<TAtto> doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_listaAttiByProcedimento");
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setParameter("tipologiaAtto", tipoAtto, Hibernate.LONG);
				q.setParameter("listaCategoriaAtto", listaCategoriaAtto);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				return q.list();
			}
		});
	}

	/**
	 * getAttiOpposizioneDissequestro
	 * 
	 * @param idProcedimento
	 * @return List<TAtto>
	 */
	public List<TAtto> getAttiOpposizioneDissequestro(final long idProcedimento) {

		return (List<TAtto>)getHibernateTemplate().execute(new HibernateCallback() {

			public List<TAtto> doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_listaAttiOpposizioneDissequestro");
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				return q.list();
			}
		});
	}
	
	/**
	 * getAttiPerditaEfficaciaSequestro
	 * 
	 * @param idProcedimento
	 * @return List<TAtto>
	 */
	public List<TAtto> getAttiPerditaEfficaciaSequestro(final long idProcedimento) {

		return (List<TAtto>)getHibernateTemplate().execute(new HibernateCallback() {

			public List<TAtto> doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_listaAttiPerditaEfficaciaSequestro");
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				return q.list();
			}
		});
	}	

	/**
	 * getAttiRevocaOpposizioneDissequestro
	 * 
	 * @param idProcedimento
	 * @return List<TAtto>
	 */
	public List<TAtto> getAttiRevocaOpposizioneDissequestro(final long idProcedimento) {

		return (List<TAtto>)getHibernateTemplate().execute(new HibernateCallback() {

			public List<TAtto> doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_listaAttiRevocaOpposizioneDissequestro");
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				return q.list();
			}
		});
	}

	/**
	 * getAttiRevocazioneConfisca
	 * 
	 * @param idProcedimento
	 * @return List<TAtto>
	 */
	public List<TAtto> getAttiRevocazioneConfisca(final long idProcedimento) {

		return (List<TAtto>)getHibernateTemplate().execute(new HibernateCallback() {

			public List<TAtto> doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_listaAttiRevocazioneConfisca");
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				return q.list();
			}
		});
	}

	public List<TAtto> getAttiParere(final long idProcedimento) {

		return (List<TAtto>)getHibernateTemplate().execute(new HibernateCallback() {

			public List<TAtto> doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_listaAttiParere");
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				return q.list();
			}
		});
	}

	public List<Map> getRelAttoProcByIdProcAndCat(final long idProc, final int categoria) {

		return (List<Map>)getHibernateTemplate().execute(new HibernateCallback() {

			public List<TRelAttoProc> doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_listaRelAttoProcByIdProcAndCat");
				q.setParameter("idProcedimento", idProc, Hibernate.LONG);
				q.setParameter("idCategoria", categoria, Hibernate.INTEGER);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				return q.list();
			}
		});
	}

	/**
	 * getAttiOrdineCronologico
	 * 
	 * @param idProcedimento
	 */
	public List<TAtto> getAttiOrdineCronologico(final long idProcedimento) {

		return (List<TAtto>)getHibernateTemplate().execute(new HibernateCallback() {

			public List<TAtto> doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_listaAttiOrdineCronologico");
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				return q.list();
			}
		});
	}
	
	
	public TDefPatr getTDefPatrById(TDefPatr id) {

		List<TDefPatr> lista = null;
		TDefPatr defPatr = new TDefPatr();
		DetachedCriteria criteria = DetachedCriteria.forClass(TDefPatr.class);
		criteria.add(Restrictions.eq("id", id));	
		lista = getHibernateTemplate().findByCriteria(criteria);
		if (lista.size() > 0) {
			defPatr = lista.get(0);
		}
		return defPatr;
	}	
	
	public TDefPers getTDefPersById(TDefPers id) {

		List<TDefPers> lista = null;
		TDefPers defPers = new TDefPers();
		DetachedCriteria criteria = DetachedCriteria.forClass(TDefPers.class);
		criteria.add(Restrictions.eq("id", id));
		lista = getHibernateTemplate().findByCriteria(criteria);
		if (lista.size() > 0) {
			defPers = lista.get(0);
		}
		return defPers;
	}		

	/**
	 * getAttoById
	 * 
	 * @param id
	 * @return
	 */
	public TAtto getAttoById(TAttoId id) {

		List<TAtto> lista = null;
		TAtto atto = new TAtto();
		DetachedCriteria criteria = DetachedCriteria.forClass(TAtto.class);
		criteria.add(Restrictions.eq("id", id));
		lista = getHibernateTemplate().findByCriteria(criteria);
		if (lista.size() > 0) {
			atto = lista.get(0);
		}
		return atto;
	}

	/**
	 * getCatAttoById
	 * 
	 * @param idCatAtto
	 * @return TCatAtto
	 */
	public TTpCatAtto getCatAttoById(int idCatAtto) {

		List<TTpCatAtto> lista = null;
		TTpCatAtto catAtto = new TTpCatAtto();
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpCatAtto.class);
		criteria.add(Restrictions.eq("codiKIdCatAtto", idCatAtto));
		lista = getHibernateTemplate().findByCriteria(criteria);
		if (lista.size() > 0) {
			catAtto = lista.get(0);
		}
		return catAtto;
	}
	
	

	/*public List<TRelAttoMisPers> getRelazioniAttiMisurePersonali(long idProcedimento, long idTipoAtto, int idUfficioUtente) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TAtto.class);
		// criteria.add(Restrictions.eq("id.codiFkIdUffOwner", idUfficioUtente));
		criteria.add(Restrictions.eq("TTpTipoAtto.codiKIdTipoAtto", idTipoAtto));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente));
		List<TRelAttoMisPers> risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}*/

	public TRelAttoProc getTAttoProcByProcedimento(long idAggravamento, int idUffOwn) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TRelAttoProc.class);
		criteria.add(Restrictions.eq("id.codiKIdProc", idAggravamento));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUffOwn));
		TRelAttoProc risultato = DataAccessUtils.requiredUniqueResult((List<TRelAttoProc>)getHibernateTemplate().findByCriteria(criteria));
		return risultato;
	}

	/**
	 * estrae il record di trelprocsogint
	 * 
	 * @param idAtto
	 * @param idUffOwn
	 * @return TRelProcSogintVer
	 */
	public TRelProcSogintVer getTRelProcSogintVerByAtto(long idAtto, int idUffOwn) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TRelProcSogintVer.class);
		criteria.add(Restrictions.eq("TAtto.id.codiKIdAtto", idAtto));
		criteria.add(Restrictions.eq("TAtto.id.codiKIdUffOwner", idUffOwn));
		criteria.setFetchMode("TSoggetto", FetchMode.JOIN);
		criteria.setFetchMode("TSoggetto.TTpTipoSoggetto", FetchMode.JOIN);
		List<TRelProcSogintVer> lista = null;
		lista = getHibernateTemplate().findByCriteria(criteria);
		if (lista.size() > 0) {
			TRelProcSogintVer ritorno = lista.get(0);
			return ritorno;

		} else
			return null;
	}

	/**
	 * getUdienzaById
	 * 
	 * @param idUdienza
	 * @return TUdienza
	 */
	public TUdienza getUdienzaById(TUdienzaId idUdienza) {

		List<TUdienza> lista = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TUdienza.class);
		criteria.add(Restrictions.eq("id", idUdienza));
		lista = getHibernateTemplate().findByCriteria(criteria);
		if (lista.size() > 0) {
			TUdienza udienza = lista.get(0);
			return udienza;
		} else
			return null;
	}

	/**
	 * inserisciMagistratoPerAtto
	 * 
	 * @param idUffOwn
	 * @param idAtto
	 * @param idMagistrato
	 * @param descRuolo
	 * @param flgRelatore
	 * @param codiUtente
	 */
	
	public void inserisciMagistratoPerAtto(final Integer idUffOwn, final long idAtto, final long idMagistrato, final String tipoRuoloMag,
			final String flgRelatore, final String codiUtente) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_inserisci_magistrato_per_atto");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("idAtto", idAtto);
				q.setParameter("idMagistrato", idMagistrato);
				q.setParameter("idRuolo", Integer.valueOf(tipoRuoloMag));
				q.setParameter("flgRelatore", flgRelatore);
				q.setParameter("codiUtente", codiUtente);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});
	}

	public long inviaAtto(final long idProcedimento, final TAtto atto, final long idStatoAtto, final long idDest, final long idUdienza) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_inviaAtto_fissUdienza");
				// chiave
				q.setParameter("idAtto", atto.getId().getCodiKIdAtto(), Hibernate.LONG);
				q.setParameter("idUffOwn", atto.getId().getCodiKIdUffOwner(), Hibernate.INTEGER);
				// parametri
				q.setParameter("idProc", idProcedimento, Hibernate.LONG);
				q.setParameter("idUdienza", idUdienza, Hibernate.LONG);
				q.setParameter("idDestinatario", idDest, Hibernate.LONG);
				q.setParameter("descUtente", atto.getDescUtente(), Hibernate.STRING);
				q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				q.setParameter("idStatoAtto", idStatoAtto, Hibernate.LONG);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long id = (Long) first.get("IDCURRENTATTO");
				return id;
			}
		});
	}

	/**
	 * inviaAttoDecretoSequestro
	 * 
	 * @param idUfficio
	 * @param idProcedimento
	 * @param atto
	 * @param codUtente
	 * @param categoriaAtto
	 * @param tipoAtto
	 * @param idStatoAtto
	 * @param idDestinatario
	 * @param idUltDestinatario
	 * @param codUdienzaAtto
	 * @param codUdienzaFiss
	 * @param codUdienzaInFaseDiRip
	 * @param codUdienzaInFaseDiRim
	 * @param statoUdienzaPrec
	 * @return Long
	 */
	public Long inviaAttoDecretoSequestro(final long idUfficio, final long idProcedimento, final TAtto atto, final String codUtente,
			final long tipoAtto, final long categoriaAtto, final long idStatoAtto, final long idDestinatario, final long idUltDestinatario,
			final long codUdienzaAtto, final long codUdienzaFiss, final long codUdienzaInFaseDiRip, final long codUdienzaInFaseDiRim,
			final long statoUdienzaPrec, final long idMagistrato, final String giudiceDelegato) {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				/* 
				 * ISSUE MEV : Specifiche Intervento-006-SpeseProces-NumAtto
				 * Numero MEV : 006
				 * Autore    : Gioggi
				 * Data      : 18 apr 2020
				 * Branch    : MEV_006
				 */
				Query q;
				if (esisteNumAtto(atto.getId().getCodiKIdAtto(), idUfficio))
					q = session.getNamedQuery("PG_NUMERO_ATTO_generaFlussoDopoNumeroAttoDS");
				else
					q = session.getNamedQuery("PG_ATTO_invia_atto_decreto_seq");
				//***** FINE INTERVENTO MEV_006 *****//
				q.setParameter("idUffOwn", idUfficio);
				q.setParameter("idProc", idProcedimento, Hibernate.LONG);
				if (atto.getDataRegistrazione() != null) {
					q.setParameter("idMittente", idUfficio, Hibernate.LONG);
					q.setParameter("idDestinatario", idDestinatario, Hibernate.LONG);
					q.setParameter("idUltDestinatario", idUltDestinatario, Hibernate.LONG);
					q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				} else {
					q.setParameter("idMittente", null, Hibernate.LONG);
					q.setParameter("idDestinatario", null, Hibernate.LONG);
					q.setParameter("idUltDestinatario", null, Hibernate.LONG);
					q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				}
				if (atto.getDataEmissione() != null) {
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				} else {
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				}
				if (atto.getDataDeposito() != null) {
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				} else {
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				}
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				if (atto.getDataRicezione() != null) {
					q.setParameter("dataRicezioneDoc", atto.getDataRicezione(), Hibernate.DATE);
				} else {
					q.setParameter("dataRicezioneDoc", null, Hibernate.DATE);
				}
				q.setParameter("descUtente", codUtente);
				q.setParameter("idAtto", atto.getId().getCodiKIdAtto(), Hibernate.LONG);
				q.setParameter("tipoAtto", tipoAtto, Hibernate.LONG);
				q.setParameter("categoriaAtto", categoriaAtto, Hibernate.LONG);
				q.setParameter("idStatoAtto", idStatoAtto, Hibernate.LONG);
				q.setParameter("idUdienzaAtto", codUdienzaAtto, Hibernate.LONG);
				q.setParameter("idUdienzaFiss", codUdienzaFiss, Hibernate.LONG);
				q.setParameter("idUdienzaRip", codUdienzaInFaseDiRip, Hibernate.LONG);
				q.setParameter("idUdienzaRim", codUdienzaInFaseDiRim, Hibernate.LONG);
				q.setParameter("statoUdienzaOld", statoUdienzaPrec, Hibernate.LONG);
				if (idMagistrato != 0) {
					q.setParameter("idMagistrato", idMagistrato, Hibernate.LONG);
				} else {
					q.setParameter("idMagistrato", null, Hibernate.LONG);
				}
				if (giudiceDelegato != null) {
					q.setParameter("giudiceDelegato", giudiceDelegato, Hibernate.STRING);
				} else {
					q.setParameter("giudiceDelegato", null, Hibernate.STRING);
				}
				
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				q.list();
				return atto.getId().getCodiKIdAtto();
			}
		});
	}

	/**
	 * aggiornaAttoDecretoSequestro
	 * 
	 * @param idUfficio
	 * @param idProcedimento
	 * @param atto
	 * @param codUtente
	 * @param categoriaAtto
	 * @param tipoAtto
	 * @param idStatoAtto
	 * @param idDestinatario
	 * @param idUltDestinatario
	 * @return Long
	 */
	public Long aggiornaAttoDecretoSequestro(final long idUfficio, final long idProcedimento, final TAtto atto, final String codUtente,
			final long tipoAtto, final long categoriaAtto, final long idStatoAtto, final long idDestinatario, final long idUltDestinatario) {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_aggiorna_atto_decreto_seq");
				q.setParameter("idUffOwn", idUfficio);
				q.setParameter("idProc", idProcedimento, Hibernate.LONG);
				if (atto.getDataRegistrazione() != null) {
					q.setParameter("idMittente", idUfficio, Hibernate.LONG);
					q.setParameter("idDestinatario", idDestinatario, Hibernate.LONG);
					q.setParameter("idUltDestinatario", idUltDestinatario, Hibernate.LONG);
					q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				} else {
					q.setParameter("idMittente", null, Hibernate.LONG);
					q.setParameter("idDestinatario", null, Hibernate.LONG);
					q.setParameter("idUltDestinatario", null, Hibernate.LONG);
					q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				}
				if (atto.getDataEmissione() != null) {
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				} else {
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				}
				if (atto.getDataDeposito() != null) {
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				} else {
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				}
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				if (atto.getDataRicezione() != null) {
					q.setParameter("dataRicezioneDoc", atto.getDataRicezione(), Hibernate.DATE);
				} else {
					q.setParameter("dataRicezioneDoc", null, Hibernate.DATE);
				}
				q.setParameter("descUtente", codUtente);
				q.setParameter("idAtto", atto.getId().getCodiKIdAtto(), Hibernate.LONG);
				q.setParameter("tipoAtto", tipoAtto, Hibernate.LONG);
				q.setParameter("categoriaAtto", categoriaAtto, Hibernate.LONG);
				q.setParameter("idStatoAtto", idStatoAtto, Hibernate.LONG);
				
				//solo per il decreto di restituzione dei beni
				if(atto.getDescNote() != null){
					q.setParameter("noteRestituzione", atto.getDescNote(), Hibernate.STRING);
				} else {
					q.setParameter("noteRestituzione", null, Hibernate.STRING);
				}					
				
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				q.list();
				return atto.getId().getCodiKIdAtto();
			}
		});
	}
	
	/**
	 * @param idUfficio
	 * @param idProcedimento
	 * @param atto
	 * @param codUtente
	 * @param tipoAtto
	 * @param categoriaAtto
	 * @param idStatoAtto
	 * @param idDestinatario
	 * @param idUltDestinatario
	 * @param codUdienzaAtto
	 * @param codUdienzaFiss
	 * @param codUdienzaInFaseDiRip
	 * @param codUdienzaInFaseDiRim
	 * @param statoUdienzaPrec
	 * @return Long
	 */
	public Long inviaAttoDecretoConvalidaRevocaSequestro(final long idUfficio, final long idProcedimento, final TAtto atto, final String codUtente,
			final long tipoAtto, final long categoriaAtto, final long idStatoAtto, final long idDestinatario, final long idUltDestinatario,
			final long codUdienzaAtto, final long codUdienzaFiss, final long codUdienzaInFaseDiRip, final long codUdienzaInFaseDiRim,
			final long statoUdienzaPrec, final String tipoConvalidaRevoca, final String nomeMetodo) {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				/* 
				 * ISSUE MEV : Specifiche Intervento-006-SpeseProces-NumAtto
				 * Numero MEV : 006
				 * Autore    : Gioggi
				 * Data      : 18 apr 2020
				 * Branch    : MEV_006
				 */
				Query q;
				if (esisteNumAtto(atto.getId().getCodiKIdAtto(), idUfficio))
					q = session.getNamedQuery("PG_NUMERO_ATTO_generaFlussoDopoNumeroAttoDCRS");
				else
					q = session.getNamedQuery("PG_ATTO_inviaDecretoConvalidaRevocaSequestro");
				//***** FINE INTERVENTO MEV_006 *****//

				q.setParameter("idUffOwn", idUfficio);
				q.setParameter("idProc", idProcedimento, Hibernate.LONG);
				if (atto.getDataRegistrazione() != null) {
					q.setParameter("idMittente", idUfficio, Hibernate.LONG);
					q.setParameter("idDestinatario", idDestinatario, Hibernate.LONG);
					q.setParameter("idUltDestinatario", idUltDestinatario, Hibernate.LONG);
					q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				} else {
					q.setParameter("idMittente", null, Hibernate.LONG);
					q.setParameter("idDestinatario", null, Hibernate.LONG);
					q.setParameter("idUltDestinatario", null, Hibernate.LONG);
					q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				}
				if (atto.getDataEmissione() != null) {
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				} else {
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				}
				if (atto.getDataDeposito() != null) {
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				} else {
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				}
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				if (atto.getDataRicezione() != null) {
					q.setParameter("dataRicezioneDoc", atto.getDataRicezione(), Hibernate.DATE);
				} else {
					q.setParameter("dataRicezioneDoc", null, Hibernate.DATE);
				}
				q.setParameter("descUtente", codUtente);
				q.setParameter("idAtto", atto.getId().getCodiKIdAtto(), Hibernate.LONG);
				q.setParameter("tipoAtto", tipoAtto, Hibernate.LONG);
				q.setParameter("categoriaAtto", categoriaAtto, Hibernate.LONG);
				q.setParameter("idStatoAtto", idStatoAtto, Hibernate.LONG);
				q.setParameter("idUdienzaAtto", codUdienzaAtto, Hibernate.LONG);
				q.setParameter("idUdienzaFiss", codUdienzaFiss, Hibernate.LONG);
				q.setParameter("idUdienzaRip", codUdienzaInFaseDiRip, Hibernate.LONG);
				q.setParameter("idUdienzaRim", codUdienzaInFaseDiRim, Hibernate.LONG);
				q.setParameter("statoUdienzaOld", statoUdienzaPrec, Hibernate.LONG);
				q.setParameter("convalidaRevoca", tipoConvalidaRevoca, Hibernate.STRING);
				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				q.list();
				return atto.getId().getCodiKIdAtto();
			}
		});
	}
	
	public Long inviaDecretoPerditaEfficaciaSequestro(final long idUfficio, final long idProcedimento, final TAtto atto, final String codUtente,
			final long tipoAtto, final long categoriaAtto, final long idStatoAtto,
			final long codUdienzaAtto, final long codUdienzaFiss, final long codUdienzaInFaseDiRip, final long codUdienzaInFaseDiRim,
			final long statoUdienzaPrec, final String tipoConvalidaRevoca, final String nomeMetodo) {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				/* 
				 * ISSUE MEV : Specifiche Intervento-006-SpeseProces-NumAtto
				 * Numero MEV : 006
				 * Autore    : Gioggi
				 * Data      : 18 apr 2020
				 * Branch    : MEV_006
				 */
				Query q;
				if (esisteNumAtto(atto.getId().getCodiKIdAtto(), idUfficio))
					q = session.getNamedQuery("PG_NUMERO_ATTO_generaFlussoDopoNumeroAttoDPES");
				else
					q = session.getNamedQuery("PG_ATTO_inviaDecretoPerditaEfficaciaSequestro");
				//***** FINE INTERVENTO MEV_006 *****//

				q.setParameter("idUffOwn", idUfficio);
				q.setParameter("idProc", idProcedimento, Hibernate.LONG);
				q.setParameter("idMittente", idUfficio, Hibernate.LONG);

				if (atto.getDataEmissione() != null) {
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				} else {
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				}
				if (atto.getDataDeposito() != null) {
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				} else {
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				}

				if (atto.getDataRegistrazione() != null) {
					q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				} else {
					q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				}
				
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}

				q.setParameter("descUtente", codUtente);
				q.setParameter("idAtto", atto.getId().getCodiKIdAtto(), Hibernate.LONG);
				q.setParameter("tipoAtto", tipoAtto, Hibernate.LONG);
				q.setParameter("categoriaAtto", categoriaAtto, Hibernate.LONG);
				q.setParameter("idStatoAtto", idStatoAtto, Hibernate.LONG);
				q.setParameter("idUdienzaAtto", codUdienzaAtto, Hibernate.LONG);
				q.setParameter("idUdienzaFiss", codUdienzaFiss, Hibernate.LONG);
				q.setParameter("idUdienzaRip", codUdienzaInFaseDiRip, Hibernate.LONG);
				q.setParameter("idUdienzaRim", codUdienzaInFaseDiRim, Hibernate.LONG);
				q.setParameter("statoUdienzaOld", statoUdienzaPrec, Hibernate.LONG);
				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				q.list();
				return atto.getId().getCodiKIdAtto();
			}
		});
	}	

	/**
	 * @param idUfficio
	 * @param idProcedimento
	 * @param atto
	 * @param codUtente
	 * @param tipoAtto
	 * @param categoriaAtto
	 * @param idStatoAtto
	 * @param idDestinatario
	 * @param idUltDestinatario
	 * @param codUdienzaAtto
	 * @param codUdienzaFiss
	 * @param codUdienzaInFaseDiRip
	 * @param codUdienzaInFaseDiRim
	 * @param statoUdienzaPrec
	 * @return Long
	 */
	public Long inviaAttoDecretoOpposizioneDissequestro(final long idUfficio, final long idProcedimento, final TAtto atto, final String codUtente,
			final long tipoAtto, final long categoriaAtto, final long idStatoAtto, final long idDestinatario, final String nomeMetodo) {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_inviaDecretoOpposizioneDissequestro");
				q.setParameter("idUffOwn", idUfficio);
				q.setParameter("idProc", idProcedimento, Hibernate.LONG);
				if (atto.getDataRegistrazione() != null) {
					q.setParameter("idMittente", idUfficio, Hibernate.LONG);
					q.setParameter("idDestinatario", idDestinatario, Hibernate.LONG);
					// q.setParameter("idUltDestinatario", idUltDestinatario, Hibernate.LONG);
					q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				} else {
					q.setParameter("idMittente", null, Hibernate.LONG);
					q.setParameter("idDestinatario", null, Hibernate.LONG);
					// q.setParameter("idUltDestinatario", null, Hibernate.LONG);
					q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				}
				if (atto.getDataEmissione() != null) {
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				} else {
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				}
				if (atto.getDataDeposito() != null) {
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				} else {
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				}
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				if (atto.getDataRicezione() != null) {
					q.setParameter("dataRicezioneDoc", atto.getDataRicezione(), Hibernate.DATE);
				} else {
					q.setParameter("dataRicezioneDoc", null, Hibernate.DATE);
				}
				q.setParameter("descUtente", codUtente);
				q.setParameter("idAtto", atto.getId().getCodiKIdAtto(), Hibernate.LONG);
				q.setParameter("tipoAtto", tipoAtto, Hibernate.LONG);
				q.setParameter("categoriaAtto", categoriaAtto, Hibernate.LONG);
				q.setParameter("idStatoAtto", idStatoAtto, Hibernate.LONG);
				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				q.list();
				return atto.getId().getCodiKIdAtto();
			}
		});
	}

	public Long inviaAttoDecretoRevocaOpposizioneDissequestro(final long idUfficio, final long idProcedimento, final TAtto atto,
			final String codUtente, final long tipoAtto, final long categoriaAtto, final long idStatoAtto, final long idDestinatario,
			final String nomeMetodo) {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_inviaDecretoRevocaOpposizioneDissequestro");
				q.setParameter("idUffOwn", idUfficio);
				q.setParameter("idProc", idProcedimento, Hibernate.LONG);
				if (atto.getDataRegistrazione() != null) {
					q.setParameter("idMittente", idUfficio, Hibernate.LONG);
					q.setParameter("idDestinatario", idDestinatario, Hibernate.LONG);
					// q.setParameter("idUltDestinatario", idUltDestinatario, Hibernate.LONG);
					q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				} else {
					q.setParameter("idMittente", null, Hibernate.LONG);
					q.setParameter("idDestinatario", null, Hibernate.LONG);
					// q.setParameter("idUltDestinatario", null, Hibernate.LONG);
					q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				}
				if (atto.getDataEmissione() != null) {
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				} else {
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				}
				if (atto.getDataDeposito() != null) {
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				} else {
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				}
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				if (atto.getDataRicezione() != null) {
					q.setParameter("dataRicezioneDoc", atto.getDataRicezione(), Hibernate.DATE);
				} else {
					q.setParameter("dataRicezioneDoc", null, Hibernate.DATE);
				}
				q.setParameter("descUtente", codUtente);
				q.setParameter("idAtto", atto.getId().getCodiKIdAtto(), Hibernate.LONG);
				q.setParameter("tipoAtto", tipoAtto, Hibernate.LONG);
				q.setParameter("categoriaAtto", categoriaAtto, Hibernate.LONG);
				q.setParameter("idStatoAtto", idStatoAtto, Hibernate.LONG);
				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				q.list();
				return atto.getId().getCodiKIdAtto();
			}
		});
	}

	public Long inviaAttoRevocazioneConfisca(final long idUfficio, final long idProcedimento, final TAtto atto, final String codUtente,
			final long tipoAtto, final long categoriaAtto, final long idStatoAtto, final long idDestinatario, final String nomeMetodo) {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_inviaRevocazioneConfisca");
				q.setParameter("idUffOwn", idUfficio);
				q.setParameter("idProc", idProcedimento, Hibernate.LONG);
				if (atto.getDataRegistrazione() != null) {
					q.setParameter("idMittente", idUfficio, Hibernate.LONG);
					q.setParameter("idDestinatario", idDestinatario, Hibernate.LONG);
					// q.setParameter("idUltDestinatario", idUltDestinatario, Hibernate.LONG);
					q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				} else {
					q.setParameter("idMittente", null, Hibernate.LONG);
					q.setParameter("idDestinatario", null, Hibernate.LONG);
					// q.setParameter("idUltDestinatario", null, Hibernate.LONG);
					q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				}
				if (atto.getDataEmissione() != null) {
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				} else {
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				}
				if (atto.getDataDeposito() != null) {
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				} else {
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				}
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				if (atto.getDataRicezione() != null) {
					q.setParameter("dataRicezioneDoc", atto.getDataRicezione(), Hibernate.DATE);
				} else {
					q.setParameter("dataRicezioneDoc", null, Hibernate.DATE);
				}
				q.setParameter("descUtente", codUtente);
				q.setParameter("idAtto", atto.getId().getCodiKIdAtto(), Hibernate.LONG);
				q.setParameter("tipoAtto", tipoAtto, Hibernate.LONG);
				q.setParameter("categoriaAtto", categoriaAtto, Hibernate.LONG);
				q.setParameter("idStatoAtto", idStatoAtto, Hibernate.LONG);
				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				q.list();
				return atto.getId().getCodiKIdAtto();
			}
		});
	}

	/**
	 * inviaDecretoIncompetenza
	 * 
	 * @param idProcedimento
	 * @param idAtto
	 * @param idMittente
	 * @param idDestinatario
	 * @param idCodiceUtente
	 * @param idUfficio
	 * @param categoriaAtto
	 *            * return long
	 */
	public long inviaDecretoIncompetenza(final long idProcedimento, final long idAtto, final long idMittente, final long idDestinatario1,
			final long idDestinatario2, final long idDestinatario3, final String codUtente, final long idUfficio, final long categoriaAtto,
			final String nomeMetodo) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				/* 
				 * ISSUE MEV : Specifiche Intervento-006-SpeseProces-NumAtto
				 * Numero MEV : 006
				 * Autore    : Gioggi
				 * Data      : 18 apr 2020
				 * Branch    : MEV_006
				 */
				Query q;
				if (esisteNumAtto(idAtto, idUfficio))
					q = session.getNamedQuery("PG_NUMERO_ATTO_generaFlussoDopoNumeroAttoDI");
				else
					q = session.getNamedQuery("PG_ATTO_inviaDecretoIncompetenza");
				//***** FINE INTERVENTO MEV_006 *****//
			
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setParameter("idAtto", idAtto, Hibernate.LONG);
				q.setParameter("idMittente", idMittente, Hibernate.LONG);
				q.setParameter("idDestinatario", idDestinatario1, Hibernate.LONG);

				if (idDestinatario2 > 0) {
					q.setParameter("idDestinatario2", idDestinatario2, Hibernate.LONG);
				} else {
					q.setParameter("idDestinatario2", null, Hibernate.LONG);
				}

				if (idDestinatario3 > 0) {
					q.setParameter("idDestinatario3", idDestinatario3, Hibernate.LONG);
				} else {
					q.setParameter("idDestinatario3", null, Hibernate.LONG);
				}

				q.setParameter("descUtente", codUtente, Hibernate.STRING);
				q.setParameter("idUffOwn", idUfficio, Hibernate.LONG);
				q.setParameter("categoriaAtto", categoriaAtto, Hibernate.LONG);
				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long id = (Long) first.get("IDFLUSSO");
				return id;
			}
		});
	}

	/**
	 * Metodo per generare i due flussi per il decreto di incompetenza della Procura
	 */
	public void inviaDecretoIncompetenzaProcura(final long idProcedimento, final long idAtto, final long idMittente,
			final long idDestinatarioProposta, final long idDestinatario2, final long idDestinatario3, final String codUtente, final long idUfficio,
			final long categoriaAtto, final String nomeMetodo) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				/* 
				 * ISSUE MEV : Specifiche Intervento-006-SpeseProces-NumAtto
				 * Numero MEV : 006
				 * Autore    : Gioggi
				 * Data      : 18 apr 2020
				 * Branch    : MEV_006
				 */
				Query q;
				if (esisteNumAtto(idAtto, idUfficio))
					q = session.getNamedQuery("PG_NUMERO_ATTO_generaFlussoDopoNumeroAttoDIP");
				else
					q = session.getNamedQuery("PG_ATTO_inviaDecretoIncompetenzaProcura");
				//***** FINE INTERVENTO MEV_006 *****//

				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setParameter("idAtto", idAtto, Hibernate.LONG);
				q.setParameter("idMittente", idMittente, Hibernate.LONG);
				q.setParameter("idDestinatario", idDestinatarioProposta, Hibernate.LONG);
				q.setParameter("idDestinatario2", idDestinatario2, Hibernate.LONG);

				if (idDestinatario3 > 0) {
					q.setParameter("idDestinatario3", idDestinatario3, Hibernate.LONG);
				} else {
					q.setParameter("idDestinatario3", null, Hibernate.LONG);
				}

				q.setParameter("descUtente", codUtente, Hibernate.STRING);
				q.setParameter("idUffOwn", idUfficio, Hibernate.LONG);
				q.setParameter("categoriaAtto", categoriaAtto, Hibernate.LONG);
				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
				// HashMap first = (HashMap) l.get(0);
				// Long id = (Long) first.get("IDFLUSSO");
				// return id;
			}
		});
	}

	/**
	 * Metodo per generare i due flussi per il decreto di incompetenza della Procura
	 */
	public void inviaDefinitivitaDecisorioIGradoRiesame(final long idProcedimento, final long numeProc, final long annoProc, final long idAtto,
			final long tipoAtto, final long idMittente, final long idProcGenerale, final long idProcUdienza, final String codUtente,
			final Date dataDefinitivita) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_inviaDefinitivitaDecisorioIGradoRiesame");
				q.setParameter("idAtto", idAtto, Hibernate.LONG);
				q.setParameter("idTipoAtto", tipoAtto, Hibernate.LONG);
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setParameter("numeProc", numeProc, Hibernate.LONG);
				q.setParameter("annoProc", annoProc, Hibernate.LONG);
				q.setParameter("idMittente", idMittente, Hibernate.LONG);
				q.setParameter("idProcGenerale", idProcGenerale, Hibernate.LONG);
				q.setParameter("idProcUdienza", idProcUdienza, Hibernate.LONG);
				q.setParameter("descUtente", codUtente, Hibernate.STRING);
				q.setParameter("dataDefinitivita", dataDefinitivita, Hibernate.DATE);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				return q.list();
			}
		});
	}

	/**
	 * listaBeniNoMisPatr
	 * 
	 * @param idUffOwn
	 * @param idProcedimento
	 * @param idAtto
	 * @return List
	 */
	public List listaBeniNoMisPatr(final long idUffOwn, final long idProcedimento, final long idAtto) {

		return (List)getHibernateTemplate().execute(new HibernateCallback() {

			public List doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_lista_Beni_No_Mis_Patr");
				q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setParameter("idAtto", idAtto, Hibernate.LONG);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				return q.list();
			}
		});
	}

	/**
	 * listaBeniNoTitDir
	 * 
	 * @param idUffOwn
	 * @param idProcedimento
	 * @param idAtto
	 * @return List
	 */
	public List listaBeniNoTitDir(final long idUffOwn, final long idProcedimento, final long idAtto) {

		return (List)getHibernateTemplate().execute(new HibernateCallback() {

			public List doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_lista_Beni_No_Tit_Dir");
				q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setParameter("idAtto", idAtto, Hibernate.LONG);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				return q.list();
			}
		});
	}

	/**
	 * listaMagistratiPerAtto
	 * 
	 * @param idUffOwn
	 * @param idAtto
	 * @return List
	 */
	
	public List listaMagistratiPerAtto(final int idUffOwn, final long idAtto, final int ruoloMag) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				List<Object[]> lista = null;
				Query q = session.getNamedQuery("PG_ATTO_magistrati_per_atto");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("vidatto", idAtto);
				q.setParameter("ruoloMag", ruoloMag);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				lista = q.list();
				return lista;
			}
		});
	}

	/**
	 * modificaAtto
	 * 
	 * @param idUffOwn
	 * @param atto
	 * @param idStatoAtto
	 * @return long
	 */
	// Specifiche Intervento-006-SpeseProces-NumAtto: aggiunto parametro di passaggio
	public long modificaAtto(final int idUffOwn, final TAtto atto, final long idStatoAtto, final TDecreto decreto, final boolean test) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_modificaAttoProc");
				q.setParameter("idAtto", atto.getId().getCodiKIdAtto(), Hibernate.LONG);
				q.setParameter("idUffOwn", idUffOwn);
				if ((atto.getTTpTipoAtto() != null) && (atto.getTTpTipoAtto().getCodiKIdTipoAtto() > 0)) {
					q.setParameter("idTipoAtto", atto.getTTpTipoAtto().getCodiKIdTipoAtto());
				} else {
					q.setParameter("idTipoAtto", null, Hibernate.LONG);
				}
				if (atto.getTTpCatAtto().getCodiKIdCatAtto() > 0) {
					q.setParameter("idCatAtto", atto.getTTpCatAtto().getCodiKIdCatAtto());
				} else {
					q.setParameter("idCatAtto", null, Hibernate.LONG);
				}
				if (decreto != null && decreto.getTUdienza() != null && decreto.getTUdienza().getId() != null
						&& decreto.getTUdienza().getId().getCodiKIdUdienza() > 0) {
					q.setParameter("idUdienza", decreto.getTUdienza().getId().getCodiKIdUdienza());
				} else {
					q.setParameter("idUdienza", null, Hibernate.LONG);
				}
				if (idStatoAtto > 0) {
					q.setParameter("idStatoAtto", idStatoAtto);
				} else {
					q.setParameter("idStatoAtto", null, Hibernate.LONG);
				}
				if (atto.getDataEmissione() != null) {
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				} else {
					if (test)
						q.setParameter("dataEmissione", null, Hibernate.DATE);
					else
						q.setParameter("dataEmissione", new Date(), Hibernate.DATE);
				}
				if (atto.getDataDeposito() != null) {
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				} else {
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				}
				if (atto.getDataRegistrazione() != null) {
					q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				} else {
					q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				}
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long id = (Long) first.get("IDATTO");
				return id;
			}
		});
	}

	/**
	 * modificaMagistratoPerAtto
	 * 
	 * @param idUffOwn
	 * @param idAtto
	 * @param idMagistrato
	 * @param descRuolo
	 * @param descNuovoRuolo
	 * @param flgRelatore
	 */
	
	public void modificaMagistratoPerAtto(final Integer idUffOwn, final long idAtto, final long idMagistrato, final String descRuolo,
			final String descNuovoRuolo, final String flgRelatore) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_modifica_magistrato_per_atto");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("idAtto", idAtto);
				q.setParameter("idMagistrato", idMagistrato);
				q.setParameter("idRuolo", Integer.parseInt(descRuolo));
				q.setParameter("descNuovoRuolo", descNuovoRuolo);
				q.setParameter("flgRelatore", flgRelatore);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});
	}

	public long modificaUdienza(final TUdienza udienza, final long idAtto, final Blob blobDocumento, final String nomeDocumento,
			final String fileType, final Date dataEmissione, final Date dataDeposito) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_modificaUdienza");
				// chiave
				q.setParameter("idUffOwn", udienza.getId().getCodiKIdUffOwner(), Hibernate.INTEGER);
				q.setParameter("idUdienza", udienza.getId().getCodiKIdUdienza(), Hibernate.LONG);
				// parametri
				q.setParameter("idAtto", idAtto, Hibernate.LONG);
				q.setParameter("dataUdienza", udienza.getDataUdienza(), Hibernate.DATE);
				q.setParameter("oraInizioUdienza", udienza.getOraInizioUdienza(), Hibernate.STRING);
				q.setParameter("indirizzoAula", udienza.getDescIndirAula(), Hibernate.STRING);
				q.setParameter("cognomeCanc", udienza.getDescCognomeAssGiu(), Hibernate.STRING);
				q.setParameter("nomeCanc", udienza.getDescNomeAssGiu(), Hibernate.STRING);
				q.setParameter("aula", udienza.getDescAula(), Hibernate.STRING);
				q.setParameter("dataEmissione", dataEmissione, Hibernate.DATE);
				q.setParameter("dataDeposito", dataDeposito, Hibernate.DATE);
				if (blobDocumento != null) {
					q.setParameter("documento", blobDocumento, Hibernate.BLOB);
					q.setParameter("tipoDocumento", fileType, Hibernate.STRING);
					q.setParameter("nomeDocumento", nomeDocumento, Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long id = (Long) first.get("IDCURRENTUDIENZA");
				return id;
			}
		});
	}

	/**
	 * salvaAtto
	 * 
	 * @param idUffOwn
	 * @param idProcedimento
	 * @param codiceUtente
	 * @param atto
	 * @param idStatoAtto
	 * @return long
	 */
	public long salvaAtto(final int idUffOwn, final long idProcedimento, final String codiceUtente, final TAtto atto, final long idStatoAtto) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_inserisciAttoDecretoProcStato");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("descUtente", codiceUtente);
				q.setParameter("idProcedimento", idProcedimento);
				if ((atto.getTTpTipoAtto() != null) && (atto.getTTpTipoAtto().getCodiKIdTipoAtto() > 0)) {
					q.setParameter("idTipoAtto", atto.getTTpTipoAtto().getCodiKIdTipoAtto());
				} else {
					q.setParameter("idTipoAtto", null, Hibernate.LONG);
				}
				if (atto.getTTpCatAtto().getCodiKIdCatAtto() > 0) {
					q.setParameter("idCatAtto", atto.getTTpCatAtto().getCodiKIdCatAtto());
				} else {
					q.setParameter("idCatAtto", null, Hibernate.LONG);
				}
				if (idStatoAtto > 0) {
					q.setParameter("idStatoAtto", idStatoAtto);
				} else {
					q.setParameter("idStatoAtto", null, Hibernate.LONG);
				}
				if (atto.getDataEmissione() != null) {
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				} else {
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				}
				if (atto.getDataDeposito() != null) {
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				} else {
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				}
				if (atto.getDataRegistrazione() != null) {
					q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				} else {
					q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				}
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long id = (Long) first.get("IDATTO");
				return id;
			}
		});
	}

	/**
	 * salvaAttoConMagistrato
	 * 
	 * @param idUffOwn
	 * @param idProcedimento
	 * @param codiceUtente
	 * @param atto
	 * @param idStatoAtto
	 * @return long
	 */
	public long salvaAttoDecretoConMagistrato(final int idUffOwn, final long idProcedimento, final String codiceUtente, final TAtto atto,
			final long idStatoAtto, final TDecreto decreto) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_salvaAttoDecretoConMagistrato");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("descUtente", codiceUtente);
				q.setParameter("idProcedimento", idProcedimento);
				if ((atto.getTTpTipoAtto() != null) && (atto.getTTpTipoAtto().getCodiKIdTipoAtto() > 0)) {
					q.setParameter("idTipoAtto", atto.getTTpTipoAtto().getCodiKIdTipoAtto());
				} else {
					q.setParameter("idTipoAtto", null, Hibernate.LONG);
				}
				if (atto.getTTpCatAtto().getCodiKIdCatAtto() > 0) {
					q.setParameter("idCatAtto", atto.getTTpCatAtto().getCodiKIdCatAtto());
				} else {
					q.setParameter("idCatAtto", null, Hibernate.LONG);
				}
				if (decreto != null && decreto.getTUdienza() != null && decreto.getTUdienza().getId() != null
						&& decreto.getTUdienza().getId().getCodiKIdUdienza() > 0) {
					q.setParameter("idUdienza", decreto.getTUdienza().getId().getCodiKIdUdienza());
				} else {
					q.setParameter("idUdienza", null, Hibernate.LONG);
				}
				if (idStatoAtto > 0) {
					q.setParameter("idStatoAtto", idStatoAtto);
				} else {
					q.setParameter("idStatoAtto", null, Hibernate.LONG);
				}
				if (atto.getDataEmissione() != null) {
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				} else {
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				}
				if (atto.getDataDeposito() != null) {
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				} else {
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				}
				if (atto.getDataRegistrazione() != null) {
					q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				} else {
					q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				}
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long id = (Long) first.get("IDATTO");
				return id;
			}
		});
	}

	/**
	 * salvaAtto
	 * 
	 * @param idUffOwn
	 * @param idProcedimento
	 * @param codiceUtente
	 * @param atto
	 * @param idStatoAtto
	 * @return long
	 */
	public long salvaAttoDecretoDecisorio(final int idUffOwn, final long idProcedimento, final String codiceUtente, final TAtto atto,
			final long idStatoAtto, final long idAttoOrigineMisure, final TDecreto decreto) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_inserisciDecretoDecisorio");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("descUtente", codiceUtente);
				q.setParameter("idProcedimento", idProcedimento);
				if ((atto.getTTpTipoAtto() != null) && (atto.getTTpTipoAtto().getCodiKIdTipoAtto() > 0)) {
					q.setParameter("idTipoAtto", atto.getTTpTipoAtto().getCodiKIdTipoAtto(), Hibernate.INTEGER);
				} else {
					q.setParameter("idTipoAtto", null, Hibernate.INTEGER);
				}
				q.setParameter("idAttoOrigineMisure", idAttoOrigineMisure, Hibernate.LONG);
				if (atto.getTTpCatAtto().getCodiKIdCatAtto() > 0) {
					q.setParameter("idCatAtto", atto.getTTpCatAtto().getCodiKIdCatAtto(), Hibernate.INTEGER);
				} else {
					q.setParameter("idCatAtto", null, Hibernate.INTEGER);
				}
				if (decreto != null && decreto.getTUdienza() != null && decreto.getTUdienza().getId() != null
						&& decreto.getTUdienza().getId().getCodiKIdUdienza() > 0) {
					q.setParameter("idUdienza", decreto.getTUdienza().getId().getCodiKIdUdienza(), Hibernate.LONG);
				} else {
					q.setParameter("idUdienza", null, Hibernate.LONG);
				}
				if (idStatoAtto > 0) {
					q.setParameter("idStatoAtto", idStatoAtto, Hibernate.LONG);
				} else {
					q.setParameter("idStatoAtto", null, Hibernate.LONG);
				}
				if (atto.getDataEmissione() != null) {
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				} else {
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				}
				if (atto.getDataDeposito() != null) {
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				} else {
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				}
				// if (atto.getDataRegistrazione() != null) {
				// q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				// } else {
				q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				// }
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long id = (Long) first.get("IDATTO");
				return id;
			}
		});
	}

	public long salvaAttoSentenzaRicorsoIIGrado(final int idUffOwn, final long idProcedimento, final String codiceUtente, final TAtto atto,
			final long idStatoAtto, final long idAttoOrigineMisure, final TDecreto decreto) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_inserisciDecretoDecisorio");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("descUtente", codiceUtente);
				q.setParameter("idProcedimento", idProcedimento);
				if ((atto.getTTpTipoAtto() != null) && (atto.getTTpTipoAtto().getCodiKIdTipoAtto() > 0)) {
					q.setParameter("idTipoAtto", atto.getTTpTipoAtto().getCodiKIdTipoAtto(), Hibernate.INTEGER);
				} else {
					q.setParameter("idTipoAtto", null, Hibernate.INTEGER);
				}
				q.setParameter("idAttoOrigineMisure", idAttoOrigineMisure, Hibernate.LONG);
				if (atto.getTTpCatAtto().getCodiKIdCatAtto() > 0) {
					q.setParameter("idCatAtto", atto.getTTpCatAtto().getCodiKIdCatAtto(), Hibernate.INTEGER);
				} else {
					q.setParameter("idCatAtto", null, Hibernate.INTEGER);
				}
				if (decreto != null && decreto.getTUdienza() != null && decreto.getTUdienza().getId() != null
						&& decreto.getTUdienza().getId().getCodiKIdUdienza() > 0) {
					q.setParameter("idUdienza", decreto.getTUdienza().getId().getCodiKIdUdienza(), Hibernate.LONG);
				} else {
					q.setParameter("idUdienza", null, Hibernate.LONG);
				}
				if (idStatoAtto > 0) {
					q.setParameter("idStatoAtto", idStatoAtto, Hibernate.LONG);
				} else {
					q.setParameter("idStatoAtto", null, Hibernate.LONG);
				}
				if (atto.getDataEmissione() != null) {
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				} else {
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				}
				if (atto.getDataDeposito() != null) {
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				} else {
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				}
				// if (atto.getDataRegistrazione() != null) {
				// q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				// } else {
				q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				// }
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long id = (Long) first.get("IDATTO");
				return id;
			}
		});
	}

	public Map salvaUdienza(final TUdienza udienza, final long idTipoAtto, final long idCategoriaAtto, final Blob blobDocumento,
			final String nomeDocumento, final String fileType) {

		return (Map<String, Long>)getHibernateTemplate().execute(new HibernateCallback() {

			public Map<String, Long> doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_salvaUdienza");
				q.setParameter("idUffOwn", udienza.getId().getCodiKIdUffOwner(), Hibernate.INTEGER);
				q.setParameter("descUtente", udienza.getDescUtente(), Hibernate.STRING);
				q.setParameter("idProcedimento", udienza.getTProcedimento().getCodiKIdProc(), Hibernate.LONG);
				q.setParameter("idTipoAtto", idTipoAtto, Hibernate.LONG);
				q.setParameter("idCatAtto", idCategoriaAtto, Hibernate.LONG);
				q.setParameter("dataUdienza", udienza.getDataUdienza(), Hibernate.DATE);
				q.setParameter("oraInizioUdienza", udienza.getOraInizioUdienza(), Hibernate.STRING);
				q.setParameter("indirizzoAula", udienza.getDescIndirAula(), Hibernate.STRING);
				q.setParameter("cognomeCanc", udienza.getDescCognomeAssGiu(), Hibernate.STRING);
				q.setParameter("nomeCanc", udienza.getDescNomeAssGiu(), Hibernate.STRING);
				q.setParameter("aula", udienza.getDescAula(), Hibernate.STRING);
				if (blobDocumento != null) {
					q.setParameter("documento", blobDocumento, Hibernate.BLOB);
					q.setParameter("tipoDocumento", fileType, Hibernate.STRING);
					q.setParameter("nomeDocumento", nomeDocumento, Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long idUdienza = (Long) first.get("IDCURRENTUDIENZA");
				Long idAtto = (Long) first.get("IDCURRENTATTO");
				Map<String, Long> resultMap = new HashMap<String, Long>();
				resultMap.put("ID_UDIENZA", idUdienza);
				resultMap.put("ID_ATTO", idAtto);
				return resultMap;
			}
		});
	}

	public Map salvaUdienzaDecretoSeqConv(final TUdienza udienza, final long codiceAtto, final long codUdienzaFiss, final long codUdienzaVerbRis) {

		return (Map<String, Long>)getHibernateTemplate().execute(new HibernateCallback() {

			public Map<String, Long> doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_salvaUdienzaDecretoSeqConv");
				q.setParameter("idUffOwn", udienza.getId().getCodiKIdUffOwner(), Hibernate.INTEGER);
				q.setParameter("descUtente", udienza.getDescUtente(), Hibernate.STRING);
				q.setParameter("idProcedimento", udienza.getTProcedimento().getCodiKIdProc(), Hibernate.LONG);
				q.setParameter("codAtto", codiceAtto, Hibernate.LONG);
				q.setParameter("dataUdienza", udienza.getDataUdienza(), Hibernate.DATE);
				q.setParameter("oraInizioUdienza", udienza.getOraInizioUdienza(), Hibernate.STRING);
				q.setParameter("indirizzoAula", udienza.getDescIndirAula(), Hibernate.STRING);
				q.setParameter("aula", udienza.getDescAula(), Hibernate.STRING);
				q.setParameter("idUdienzaFiss", codUdienzaFiss, Hibernate.LONG);
				q.setParameter("idUdienzaVerbRis", codUdienzaVerbRis, Hibernate.LONG);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long idUdienza = (Long) first.get("IDCURRENTUDIENZA");
				Map<String, Long> resultMap = new HashMap<String, Long>();
				resultMap.put("ID_UDIENZA", idUdienza);
				return resultMap;
			}
		});
	}

	public void eliminaUdienzaSeqConv(final long idUdienza, final long codAtto, final long idUfficio) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_elimina_udienza_seq_conv");
				q.setParameter("vidUdienza", idUdienza, Hibernate.LONG);
				q.setParameter("vidAtto", codAtto, Hibernate.LONG);
				q.setParameter("vidUffOwn", idUfficio, Hibernate.LONG);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});
	}

	/**
	 * aggiornaDecisorioIGradoSoggettoDiffidato
	 * 
	 * @param idUffOwn
	 * @param idProcInUdienza
	 * @param idProcGenerale
	 * @param idProcedimento
	 * @param atto
	 * @param codUtente
	 * @param idEsito
	 * @return long
	 */
	public Long aggiornaDecisorioIGradoSoggettoDiffidato(final long idUffOwn, final long idProcInUdienza, final long idProcGenerale,
			final long idProcedimento, final TAtto atto, final String codUtente, final int idEsito) {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Long idAtto = null;

				Query q;
				/* 
				 * ISSUE MEV : Specifiche Intervento-006-SpeseProces-NumAtto
				 * Numero MEV : 006
				 * Autore    : Gioggi
				 * Data      : 18 apr 2020
				 * Branch    : MEV_006
				 */
				if (esisteNumAtto(atto.getId().getCodiKIdAtto(), idUffOwn))
					q = session.getNamedQuery("PG_NUMERO_ATTO_generaFlussoDopoNumeroAttoDO");
				else
					q = session.getNamedQuery("PG_ATTO_aggiornaDecisorioIGradoIstanzaSoggDiffidato");;
				//***** FINE INTERVENTO MEV_006 *****//

				q.setParameter("idUffOwn", idUffOwn);
				if (idUffOwn > 0)
					q.setParameter("idMittente", idUffOwn, Hibernate.LONG);
				else
					q.setParameter("idMittente", null, Hibernate.LONG);
				if (idProcInUdienza > 0)
					q.setParameter("idProcInUdienza", idProcInUdienza, Hibernate.LONG);
				else
					q.setParameter("idProcInUdienza", null, Hibernate.LONG);
				if (idProcGenerale > 0)
					q.setParameter("idProcGenerale", idProcGenerale, Hibernate.LONG);
				else
					q.setParameter("idProcGenerale", null, Hibernate.LONG);
				q.setParameter("idProc", idProcedimento, Hibernate.LONG);
				if (atto.getDataEmissione() != null)
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				else
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				if (atto.getDataDeposito() != null)
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				else
					q.setParameter("dataDeposito", null, Hibernate.DATE);

				if (atto.getDataRicezione() != null)
					q.setParameter("dataRicezioneDoc", atto.getDataRicezione(), Hibernate.DATE);
				else
					q.setParameter("dataRicezioneDoc", null, Hibernate.DATE);
				if (atto.getDataRegistrazione() != null)
					q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				else
					q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				q.setParameter("idAtto", atto.getId().getCodiKIdAtto());
				if (idEsito > 0)
					q.setParameter("idEsitoOpposizione", idEsito);
				else
					q.setParameter("idEsitoOpposizione", null, Hibernate.LONG);

				q.setParameter("descUtente", codUtente);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				Iterator iter = l.iterator();
				while (iter.hasNext()) {
					Map map = (Map) iter.next();
					idAtto = (Long) map.get("IDATTO");
				}
				return idAtto;
			}
		});
	}

	/**
	 * aggiornaDecisorioPrevenzione
	 * 
	 * @param idUffOwn
	 * @param idProcInUdienza
	 * @param idProcGenerale
	 * @param idProcProponente
	 * @param idProcedimento
	 * @param atto
	 * @param codUtente
	 * @return long
	 */
	public Long aggiornaDecisorioPrevenzione(final long idUffOwn, final long idProcInUdienza, final long idProcGenerale, final long idProcProponente,
			final long idProcedimento, final TAtto atto, final String codUtente, final int tipoUff, final long idMagistrato,
			final String giudiceDelegato) {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				// CATE decisorio di I o di II grado.
				String query;
				if (tipoUff == Constants.TIPO_UFFICIO_CORTE_D_APPELLO) {
					/* 
					 * ISSUE MEV : Specifiche Intervento-006-SpeseProces-NumAtto
					 * Numero MEV : 006
					 * Autore    : Gioggi
					 * Data      : 18 apr 2020
					 * Branch    : MEV_006
					 */
					if (esisteNumAtto(atto.getId().getCodiKIdAtto(), idUffOwn))
						query = "PG_NUMERO_ATTO_generaFlussoDopoNumeroAttoIIGrado";
					else
						query = "PG_ATTO_aggiornaDecisorioIIGradoMerito";
					//***** FINE INTERVENTO MEV_006 *****//
				} else {
					/* 
					 * ISSUE MEV : Specifiche Intervento-006-SpeseProces-NumAtto
					 * Numero MEV : 006
					 * Autore    : Gioggi
					 * Data      : 18 apr 2020
					 * Branch    : MEV_006
					 */
					if (esisteNumAtto(atto.getId().getCodiKIdAtto(), idUffOwn))
						query = "PG_NUMERO_ATTO_generaFlussoDopoNumeroAttoIGrado";
					else
						query = "PG_ATTO_aggiornaDecisorioIGradoPrevenzione";
					//***** FINE INTERVENTO MEV_006 *****//
				}
				Long idAtto = null;
				Query q = session.getNamedQuery(query);

				q.setParameter("idUffOwn", idUffOwn);
				if (idUffOwn > 0)
					q.setParameter("idMittente", idUffOwn, Hibernate.LONG);
				else
					q.setParameter("idMittente", null, Hibernate.LONG);
				if (idProcInUdienza > 0)
					q.setParameter("idProcInUdienza", idProcInUdienza, Hibernate.LONG);
				else
					q.setParameter("idProcInUdienza", null, Hibernate.LONG);
				if (idProcGenerale > 0)
					q.setParameter("idProcGenerale", idProcGenerale, Hibernate.LONG);
				else
					q.setParameter("idProcGenerale", null, Hibernate.LONG);
				if (idProcProponente > 0)
					q.setParameter("idProcProponente", idProcProponente, Hibernate.LONG);
				else
					q.setParameter("idProcProponente", null, Hibernate.LONG);
				q.setParameter("idProc", idProcedimento, Hibernate.LONG);
				if (atto.getDataEmissione() != null)
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				else
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				if (atto.getDataDeposito() != null)
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				else
					q.setParameter("dataDeposito", null, Hibernate.DATE);

				if (atto.getDataRicezione() != null)
					q.setParameter("dataRicezioneDoc", atto.getDataRicezione(), Hibernate.DATE);
				else
					q.setParameter("dataRicezioneDoc", null, Hibernate.DATE);
				if (atto.getDataRegistrazione() != null)
					q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				else
					q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				q.setParameter("idAtto", atto.getId().getCodiKIdAtto());
				q.setParameter("descUtente", codUtente);
				if (idMagistrato != 0) {
					q.setParameter("idMagistrato", idMagistrato, Hibernate.LONG);
				} else {
					q.setParameter("idMagistrato", null, Hibernate.LONG);
				}
				if (giudiceDelegato != null) {
					q.setParameter("giudiceDelegato", giudiceDelegato, Hibernate.STRING);
				} else {
					q.setParameter("giudiceDelegato", null, Hibernate.STRING);
				}
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				Iterator iter = l.iterator();
				while (iter.hasNext()) {
					Map map = (Map) iter.next();
					idAtto = (Long) map.get("IDATTO");
				}
				return idAtto;
			}
		});
	}

	/**
	 * aggiornaSentenzaOpposizioneDissequestro
	 * 
	 * @param idUffOwn
	 * @param idProcInUdienza
	 * @param idTribunale
	 * @param idPg_Cda
	 * @param idProcedimento
	 * @param atto
	 * @param codUtente
	 * @return long
	 */
	public Long aggiornaSentenzaOpposizioneDissequestro(final long idUffOwn, final long idUff1, final long idUff2, final long idUff3,
			final long idProcedimento, final TAtto atto, final String codUtente) {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Long idAtto = null;
				Query q;
				/* 
				 * ISSUE MEV : Specifiche Intervento-006-SpeseProces-NumAtto
				 * Numero MEV : 006
				 * Autore    : Gioggi
				 * Data      : 18 apr 2020
				 * Branch    : MEV_006
				 */
				if (esisteNumAtto(atto.getId().getCodiKIdAtto(), idUffOwn))
					q = session.getNamedQuery("PG_NUMERO_ATTO_generaFlussoDopoNumeroAttoSOD");
				else
					q = session.getNamedQuery("PG_ATTO_aggiornaSentenzaOpposizioneDissequestro");
				//***** FINE INTERVENTO MEV_006 *****//

				q.setParameter("idUffOwn", idUffOwn);
				if (idUffOwn > 0)
					q.setParameter("idMittente", idUffOwn, Hibernate.LONG);
				else
					q.setParameter("idMittente", null, Hibernate.LONG);
				if (idUff1 > 0)
					q.setParameter("idUff1", idUff1, Hibernate.LONG);
				else
					q.setParameter("idUff1", null, Hibernate.LONG);
				if (idUff2 > 0)
					q.setParameter("idUff2", idUff2, Hibernate.LONG);
				else
					q.setParameter("idUff2", null, Hibernate.LONG);
				if (idUff3 > 0)
					q.setParameter("idUff3", idUff3, Hibernate.LONG);
				else
					q.setParameter("idUff3", null, Hibernate.LONG);
				q.setParameter("idProc", idProcedimento, Hibernate.LONG);
				if (atto.getDataEmissione() != null)
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				else
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				if (atto.getDataDeposito() != null)
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				else
					q.setParameter("dataDeposito", null, Hibernate.DATE);

				if (atto.getDataRicezione() != null)
					q.setParameter("dataRicezioneDoc", atto.getDataRicezione(), Hibernate.DATE);
				else
					q.setParameter("dataRicezioneDoc", null, Hibernate.DATE);
				if (atto.getDataRegistrazione() != null)
					q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				else
					q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				q.setParameter("idAtto", atto.getId().getCodiKIdAtto());
				q.setParameter("descUtente", codUtente);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				Iterator iter = l.iterator();
				while (iter.hasNext()) {
					Map map = (Map) iter.next();
					idAtto = (Long) map.get("IDATTO");
				}
				return idAtto;
			}
		});
	}

	/**
	 * aggiornaSentenzaRevocazioneConfisca
	 * 
	 * @param idUffOwn
	 * @param idProcInUdienza
	 * @param idTribunale
	 * @param idPg_Cda
	 * @param idProcedimento
	 * @param atto
	 * @param codUtente
	 * @return long
	 */
	public Long aggiornaSentenzaRevocazioneConfisca(final long idUffOwn, final long idUff1, final long idUff2, final long idUff3,
			final long idProcedimento, final TAtto atto, final String codUtente) {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Long idAtto = null;
				Query q;
				/* 
				 * ISSUE MEV : Specifiche Intervento-006-SpeseProces-NumAtto
				 * Numero MEV : 006
				 * Autore    : Gioggi
				 * Data      : 18 apr 2020
				 * Branch    : MEV_006
				 */
				if (esisteNumAtto(atto.getId().getCodiKIdAtto(), idUffOwn))
					q = session.getNamedQuery("PG_NUMERO_ATTO_generaFlussoDopoNumeroAttoSRC");
				else
					q = session.getNamedQuery("PG_ATTO_aggiornaSentenzaRevocazioneConfisca");
				//***** FINE INTERVENTO MEV_006 *****//

				q.setParameter("idUffOwn", idUffOwn);
				if (idUffOwn > 0)
					q.setParameter("idMittente", idUffOwn, Hibernate.LONG);
				else
					q.setParameter("idMittente", null, Hibernate.LONG);
				if (idUff1 > 0)
					q.setParameter("idUff1", idUff1, Hibernate.LONG);
				else
					q.setParameter("idUff1", null, Hibernate.LONG);
				if (idUff2 > 0)
					q.setParameter("idUff2", idUff2, Hibernate.LONG);
				else
					q.setParameter("idUff2", null, Hibernate.LONG);
				if (idUff3 > 0)
					q.setParameter("idUff3", idUff3, Hibernate.LONG);
				else
					q.setParameter("idUff3", null, Hibernate.LONG);
				q.setParameter("idProc", idProcedimento, Hibernate.LONG);
				if (atto.getDataEmissione() != null)
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				else
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				if (atto.getDataDeposito() != null)
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				else
					q.setParameter("dataDeposito", null, Hibernate.DATE);

				if (atto.getDataRicezione() != null)
					q.setParameter("dataRicezioneDoc", atto.getDataRicezione(), Hibernate.DATE);
				else
					q.setParameter("dataRicezioneDoc", null, Hibernate.DATE);
				if (atto.getDataRegistrazione() != null)
					q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				else
					q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				q.setParameter("idAtto", atto.getId().getCodiKIdAtto());
				q.setParameter("descUtente", codUtente);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				Iterator iter = l.iterator();
				while (iter.hasNext()) {
					Map map = (Map) iter.next();
					idAtto = (Long) map.get("IDATTO");
				}
				return idAtto;
			}
		});
	}

	public Map inserisciDecretoAnticipazioneRinvioUdienza(final TUdienza udienza, final long idTipoAtto, final long idCategoriaAtto,
			final Blob blobDocumento, final String nomeDocumento, final String fileType, final long idUdienzaPrecedente) {

		return (Map<String, Long>)getHibernateTemplate().execute(new HibernateCallback() {

			public Map<String, Long> doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_insDecretoAnticipazioneRinvioUdienza");
				q.setParameter("idUffOwn", udienza.getId().getCodiKIdUffOwner(), Hibernate.INTEGER);
				q.setParameter("descUtente", udienza.getDescUtente(), Hibernate.STRING);
				q.setParameter("idUdienzaPrecedente", idUdienzaPrecedente, Hibernate.LONG);
				q.setParameter("idProcedimento", udienza.getTProcedimento().getCodiKIdProc(), Hibernate.LONG);
				q.setParameter("idTipoAtto", idTipoAtto, Hibernate.LONG);
				q.setParameter("idCatAtto", idCategoriaAtto, Hibernate.LONG);
				q.setParameter("dataUdienza", udienza.getDataUdienza(), Hibernate.DATE);
				q.setParameter("oraInizioUdienza", udienza.getOraInizioUdienza(), Hibernate.STRING);
				q.setParameter("indirizzoAula", udienza.getDescIndirAula(), Hibernate.STRING);
				q.setParameter("cognomeCanc", udienza.getDescCognomeAssGiu(), Hibernate.STRING);
				q.setParameter("nomeCanc", udienza.getDescNomeAssGiu(), Hibernate.STRING);
				q.setParameter("aula", udienza.getDescAula(), Hibernate.STRING);
				if (blobDocumento != null) {
					q.setParameter("documento", blobDocumento, Hibernate.BLOB);
					q.setParameter("tipoDocumento", fileType, Hibernate.STRING);
					q.setParameter("nomeDocumento", nomeDocumento, Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long idUdienza = (Long) first.get("IDCURRENTUDIENZA");
				Long idAtto = (Long) first.get("IDCURRENTATTO");
				Map<String, Long> resultMap = new HashMap<String, Long>();
				resultMap.put("ID_UDIENZA", idUdienza);
				resultMap.put("ID_ATTO", idAtto);
				return resultMap;
			}
		});
	}

	public Long aggiornaDecretoAnticipazioneRinvioUdienza(final TUdienza udienza, final TAtto atto, final long idUdienzaPrecedente,
			final long idUffDest, final long idProc, final String codUtente) {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_aggDecretoAnticipazioneRinvioUdienza");
				q.setParameter("idUffOwn", udienza.getId().getCodiKIdUffOwner(), Hibernate.INTEGER);
				q.setParameter("idMittente", udienza.getId().getCodiKIdUffOwner(), Hibernate.INTEGER);
				q.setParameter("idProcInUdienza", idUffDest, Hibernate.LONG);
				q.setParameter("idUdienza", udienza.getId().getCodiKIdUdienza(), Hibernate.LONG);
				q.setParameter("dataUdienza", udienza.getDataUdienza(), Hibernate.DATE);
				q.setParameter("oraInizioUdienza", udienza.getOraInizioUdienza(), Hibernate.STRING);
				q.setParameter("indirizzoAula", udienza.getDescIndirAula(), Hibernate.STRING);
				q.setParameter("cognomeCanc", udienza.getDescCognomeAssGiu(), Hibernate.STRING);
				q.setParameter("nomeCanc", udienza.getDescNomeAssGiu(), Hibernate.STRING);
				q.setParameter("aula", udienza.getDescAula(), Hibernate.STRING);
				q.setParameter("idUdienzaPrecedente", idUdienzaPrecedente, Hibernate.LONG);
				q.setParameter("idProc", idProc, Hibernate.LONG);
				q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				q.setParameter("dataRicezioneDoc", atto.getDataRicezione(), Hibernate.DATE);
				q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				q.setParameter("idAtto", atto.getId().getCodiKIdAtto(), Hibernate.LONG);
				q.setParameter("idTipoAtto", atto.getTTpTipoAtto().getCodiKIdTipoAtto(), Hibernate.INTEGER);
				q.setParameter("idCategoriaAtto", atto.getTTpCatAtto().getCodiKIdCatAtto(), Hibernate.INTEGER);
				q.setParameter("descUtente", codUtente, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

				Long idAtto = null;
				List l = q.list();
				Iterator iter = l.iterator();
				while (iter.hasNext()) {
					Map map = (Map) iter.next();
					idAtto = (Long) map.get("IDATTO");
				}
				return idAtto;
			}
		});
	}

	public long inserisciAttoDecretoConvalidaSequestro(final int idUffOwn, final long idProcedimento, final String codiceUtente, final TAtto atto,
			final long idStatoAtto, final long idAttoOrigine, final TDecreto decreto, final String nomeMetodo) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_inserisciDecretoConvalidaSequestro");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("descUtente", codiceUtente);
				q.setParameter("idProcedimento", idProcedimento);
				if ((atto.getTTpTipoAtto() != null) && (atto.getTTpTipoAtto().getCodiKIdTipoAtto() > 0)) {
					q.setParameter("idTipoAtto", atto.getTTpTipoAtto().getCodiKIdTipoAtto());
				} else {
					q.setParameter("idTipoAtto", null, Hibernate.LONG);
				}
				q.setParameter("idAttoOrigine", idAttoOrigine, Hibernate.LONG);
				if (atto.getTTpCatAtto().getCodiKIdCatAtto() > 0) {
					q.setParameter("idCatAtto", atto.getTTpCatAtto().getCodiKIdCatAtto());
				} else {
					q.setParameter("idCatAtto", null, Hibernate.LONG);
				}
				if (decreto != null && decreto.getTUdienza() != null && decreto.getTUdienza().getId() != null
						&& decreto.getTUdienza().getId().getCodiKIdUdienza() > 0) {
					q.setParameter("idUdienza", decreto.getTUdienza().getId().getCodiKIdUdienza());
				} else {
					q.setParameter("idUdienza", null, Hibernate.LONG);
				}
				if (idStatoAtto > 0) {
					q.setParameter("idStatoAtto", idStatoAtto);
				} else {
					q.setParameter("idStatoAtto", null, Hibernate.LONG);
				}
				if (atto.getDataEmissione() != null) {
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				} else {
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				}
				if (atto.getDataDeposito() != null) {
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				} else {
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				}
				if (atto.getDataRegistrazione() != null) {
					q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				} else {
					q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				}
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long id = (Long) first.get("IDATTO");
				return id;
			}
		});
	}
	
	
	
	public long inserisciDecretoPerditaEfficacia(final int idUffOwn, final long idProcedimento, final String codiceUtente, final TAtto atto,
			final long idStatoAtto, final long idAttoOrigine, final TDecreto decreto, final String nomeMetodo) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_inserisciDecretoPerditaEfficacia");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("descUtente", codiceUtente);
				q.setParameter("idProcedimento", idProcedimento);
				if ((atto.getTTpTipoAtto() != null) && (atto.getTTpTipoAtto().getCodiKIdTipoAtto() > 0)) {
					q.setParameter("idTipoAtto", atto.getTTpTipoAtto().getCodiKIdTipoAtto());
				} else {
					q.setParameter("idTipoAtto", null, Hibernate.LONG);
				}
				q.setParameter("idAttoOrigine", idAttoOrigine, Hibernate.LONG);
				if (atto.getTTpCatAtto().getCodiKIdCatAtto() > 0) {
					q.setParameter("idCatAtto", atto.getTTpCatAtto().getCodiKIdCatAtto());
				} else {
					q.setParameter("idCatAtto", null, Hibernate.LONG);
				}
				if (decreto != null && decreto.getTUdienza() != null && decreto.getTUdienza().getId() != null
						&& decreto.getTUdienza().getId().getCodiKIdUdienza() > 0) {
					q.setParameter("idUdienza", decreto.getTUdienza().getId().getCodiKIdUdienza());
				} else {
					q.setParameter("idUdienza", null, Hibernate.LONG);
				}
				if (idStatoAtto > 0) {
					q.setParameter("idStatoAtto", idStatoAtto);
				} else {
					q.setParameter("idStatoAtto", null, Hibernate.LONG);
				}
				if (atto.getDataEmissione() != null) {
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				} else {
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				}
				if (atto.getDataDeposito() != null) {
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				} else {
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				}
				if (atto.getDataRegistrazione() != null) {
					q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				} else {
					q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				}
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long id = (Long) first.get("IDATTO");
				return id;
			}
		});
	}	

	public long inserisciAttoDecretoOpposizioneDissequestro(final int idUffOwn, final long idProcedimento, final String codiceUtente,
			final TAtto atto, final long idStatoAtto, final long idAttoOrigine, final TDecreto decreto, final String nomeMetodo) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_inserisciDecretoOpposizioneDissequestro");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("descUtente", codiceUtente);
				q.setParameter("idProcedimento", idProcedimento);
				if ((atto.getTTpTipoAtto() != null) && (atto.getTTpTipoAtto().getCodiKIdTipoAtto() > 0)) {
					q.setParameter("idTipoAtto", atto.getTTpTipoAtto().getCodiKIdTipoAtto());
				} else {
					q.setParameter("idTipoAtto", null, Hibernate.LONG);
				}
				q.setParameter("idAttoOrigine", idAttoOrigine, Hibernate.LONG);
				if (atto.getTTpCatAtto().getCodiKIdCatAtto() > 0) {
					q.setParameter("idCatAtto", atto.getTTpCatAtto().getCodiKIdCatAtto());
				} else {
					q.setParameter("idCatAtto", null, Hibernate.LONG);
				}
				if (decreto != null && decreto.getTUdienza() != null && decreto.getTUdienza().getId() != null
						&& decreto.getTUdienza().getId().getCodiKIdUdienza() > 0) {
					q.setParameter("idUdienza", decreto.getTUdienza().getId().getCodiKIdUdienza());
				} else {
					q.setParameter("idUdienza", null, Hibernate.LONG);
				}
				if (idStatoAtto > 0) {
					q.setParameter("idStatoAtto", idStatoAtto);
				} else {
					q.setParameter("idStatoAtto", null, Hibernate.LONG);
				}
				if (atto.getDataEmissione() != null) {
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				} else {
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				}
				if (atto.getDataDeposito() != null) {
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				} else {
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				}
				if (atto.getDataRegistrazione() != null) {
					q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				} else {
					q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				}
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long id = (Long) first.get("IDATTO");
				return id;
			}
		});
	}

	public long inserisciAttoRevocaDecretoOpposizioneDissequestro(final int idUffOwn, final long idProcedimento, final String codiceUtente,
			final TAtto atto, final long idStatoAtto, final long idAttoOrigine, final TDecreto decreto, final String nomeMetodo) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_inserisciRevocaDecretoOpposizioneDissequestro");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("descUtente", codiceUtente);
				q.setParameter("idProcedimento", idProcedimento);
				if ((atto.getTTpTipoAtto() != null) && (atto.getTTpTipoAtto().getCodiKIdTipoAtto() > 0)) {
					q.setParameter("idTipoAtto", atto.getTTpTipoAtto().getCodiKIdTipoAtto());
				} else {
					q.setParameter("idTipoAtto", null, Hibernate.LONG);
				}
				q.setParameter("idAttoOrigine", idAttoOrigine, Hibernate.LONG);
				if (atto.getTTpCatAtto().getCodiKIdCatAtto() > 0) {
					q.setParameter("idCatAtto", atto.getTTpCatAtto().getCodiKIdCatAtto());
				} else {
					q.setParameter("idCatAtto", null, Hibernate.LONG);
				}
				if (decreto != null && decreto.getTUdienza() != null && decreto.getTUdienza().getId() != null
						&& decreto.getTUdienza().getId().getCodiKIdUdienza() > 0) {
					q.setParameter("idUdienza", decreto.getTUdienza().getId().getCodiKIdUdienza());
				} else {
					q.setParameter("idUdienza", null, Hibernate.LONG);
				}
				if (idStatoAtto > 0) {
					q.setParameter("idStatoAtto", idStatoAtto);
				} else {
					q.setParameter("idStatoAtto", null, Hibernate.LONG);
				}
				if (atto.getDataEmissione() != null) {
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				} else {
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				}
				if (atto.getDataDeposito() != null) {
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				} else {
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				}
				if (atto.getDataRegistrazione() != null) {
					q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				} else {
					q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				}
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long id = (Long) first.get("IDATTO");
				return id;
			}
		});
	}

	public long inserisciAttoRevocazioneConfisca(final int idUffOwn, final long idProcedimento, final String codiceUtente, final TAtto atto,
			final long idStatoAtto, final long idAttoOrigine, final TDecreto decreto, final String nomeMetodo) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_inserisciRevocazioneConfisca");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("descUtente", codiceUtente);
				q.setParameter("idProcedimento", idProcedimento);
				if ((atto.getTTpTipoAtto() != null) && (atto.getTTpTipoAtto().getCodiKIdTipoAtto() > 0)) {
					q.setParameter("idTipoAtto", atto.getTTpTipoAtto().getCodiKIdTipoAtto());
				} else {
					q.setParameter("idTipoAtto", null, Hibernate.LONG);
				}
				q.setParameter("idAttoOrigine", idAttoOrigine, Hibernate.LONG);
				if (atto.getTTpCatAtto().getCodiKIdCatAtto() > 0) {
					q.setParameter("idCatAtto", atto.getTTpCatAtto().getCodiKIdCatAtto());
				} else {
					q.setParameter("idCatAtto", null, Hibernate.LONG);
				}
				if (decreto != null && decreto.getTUdienza() != null && decreto.getTUdienza().getId() != null
						&& decreto.getTUdienza().getId().getCodiKIdUdienza() > 0) {
					q.setParameter("idUdienza", decreto.getTUdienza().getId().getCodiKIdUdienza());
				} else {
					q.setParameter("idUdienza", null, Hibernate.LONG);
				}
				if (idStatoAtto > 0) {
					q.setParameter("idStatoAtto", idStatoAtto);
				} else {
					q.setParameter("idStatoAtto", null, Hibernate.LONG);
				}
				if (atto.getDataEmissione() != null) {
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				} else {
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				}
				if (atto.getDataDeposito() != null) {
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				} else {
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				}
				if (atto.getDataRegistrazione() != null) {
					q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				} else {
					q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				}
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long id = (Long) first.get("IDATTO");
				return id;
			}
		});
	}

	public long inserisciAttoSentenzaOpposizioneDissequestro(final int idUffOwn, final long idProcedimento, final String codiceUtente,
			final TAtto atto, final long idStatoAtto, final long idAttoOrigine, final TDecreto decreto, final String nomeMetodo) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_inserisciSentenzaOpposizioneDissequestro");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("descUtente", codiceUtente);
				q.setParameter("idProcedimento", idProcedimento);
				if ((atto.getTTpTipoAtto() != null) && (atto.getTTpTipoAtto().getCodiKIdTipoAtto() > 0)) {
					q.setParameter("idTipoAtto", atto.getTTpTipoAtto().getCodiKIdTipoAtto());
				} else {
					q.setParameter("idTipoAtto", null, Hibernate.LONG);
				}
				q.setParameter("idAttoOrigine", idAttoOrigine, Hibernate.LONG);
				if (atto.getTTpCatAtto().getCodiKIdCatAtto() > 0) {
					q.setParameter("idCatAtto", atto.getTTpCatAtto().getCodiKIdCatAtto());
				} else {
					q.setParameter("idCatAtto", null, Hibernate.LONG);
				}
				if (decreto != null && decreto.getTUdienza() != null && decreto.getTUdienza().getId() != null
						&& decreto.getTUdienza().getId().getCodiKIdUdienza() > 0) {
					q.setParameter("idUdienza", decreto.getTUdienza().getId().getCodiKIdUdienza());
				} else {
					q.setParameter("idUdienza", null, Hibernate.LONG);
				}
				if (idStatoAtto > 0) {
					q.setParameter("idStatoAtto", idStatoAtto);
				} else {
					q.setParameter("idStatoAtto", null, Hibernate.LONG);
				}
				if (atto.getDataEmissione() != null) {
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				} else {
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				}
				if (atto.getDataDeposito() != null) {
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				} else {
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				}
				if (atto.getDataRegistrazione() != null) {
					q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				} else {
					q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				}
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long id = (Long) first.get("IDATTO");
				return id;
			}
		});
	}

	public long inserisciAttoSentenzaRevocazioneConfisca(final int idUffOwn, final long idProcedimento, final String codiceUtente, final TAtto atto,
			final long idStatoAtto, final long idAttoOrigine, final TDecreto decreto, final String nomeMetodo) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_inserisciAttoSentenzaRevocazioneConfisca");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("descUtente", codiceUtente);
				q.setParameter("idProcedimento", idProcedimento);
				if ((atto.getTTpTipoAtto() != null) && (atto.getTTpTipoAtto().getCodiKIdTipoAtto() > 0)) {
					q.setParameter("idTipoAtto", atto.getTTpTipoAtto().getCodiKIdTipoAtto());
				} else {
					q.setParameter("idTipoAtto", null, Hibernate.LONG);
				}
				q.setParameter("idAttoOrigine", idAttoOrigine, Hibernate.LONG);
				if (atto.getTTpCatAtto().getCodiKIdCatAtto() > 0) {
					q.setParameter("idCatAtto", atto.getTTpCatAtto().getCodiKIdCatAtto());
				} else {
					q.setParameter("idCatAtto", null, Hibernate.LONG);
				}
				if (decreto != null && decreto.getTUdienza() != null && decreto.getTUdienza().getId() != null
						&& decreto.getTUdienza().getId().getCodiKIdUdienza() > 0) {
					q.setParameter("idUdienza", decreto.getTUdienza().getId().getCodiKIdUdienza());
				} else {
					q.setParameter("idUdienza", null, Hibernate.LONG);
				}
				if (idStatoAtto > 0) {
					q.setParameter("idStatoAtto", idStatoAtto);
				} else {
					q.setParameter("idStatoAtto", null, Hibernate.LONG);
				}
				if (atto.getDataEmissione() != null) {
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				} else {
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				}
				if (atto.getDataDeposito() != null) {
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				} else {
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				}
				if (atto.getDataRegistrazione() != null) {
					q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				} else {
					q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				}
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long id = (Long) first.get("IDATTO");
				return id;
			}
		});
	}

	public long inserisciAttoDecretoRevocaSequestro(final int idUffOwn, final long idProcedimento, final String codiceUtente, final TAtto atto,
			final long idStatoAtto, final long idAttoOrigine, final String nomeMetodo) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_inserisciDecretoRevocaSequestro");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("descUtente", codiceUtente);
				q.setParameter("idProcedimento", idProcedimento);
				if ((atto.getTTpTipoAtto() != null) && (atto.getTTpTipoAtto().getCodiKIdTipoAtto() > 0)) {
					q.setParameter("idTipoAtto", atto.getTTpTipoAtto().getCodiKIdTipoAtto());
				} else {
					q.setParameter("idTipoAtto", null, Hibernate.LONG);
				}
				q.setParameter("idAttoOrigine", idAttoOrigine, Hibernate.LONG);
				if (atto.getTTpCatAtto().getCodiKIdCatAtto() > 0) {
					q.setParameter("idCatAtto", atto.getTTpCatAtto().getCodiKIdCatAtto());
				} else {
					q.setParameter("idCatAtto", null, Hibernate.LONG);
				}
				if (idStatoAtto > 0) {
					q.setParameter("idStatoAtto", idStatoAtto);
				} else {
					q.setParameter("idStatoAtto", null, Hibernate.LONG);
				}
				if (atto.getDataEmissione() != null) {
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				} else {
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				}
				if (atto.getDataDeposito() != null) {
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				} else {
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				}
				if (atto.getDataRegistrazione() != null) {
					q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				} else {
					q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				}
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long id = (Long) first.get("IDATTO");
				return id;
			}
		});
	}

	public boolean esisteDecretoConvalidaSequesto(long idAttoOrigine, long idUffOwn) {

		Query query = getSession().createSQLQuery(
				"SELECT rel.* " + "								   FROM T_REL_ATTI rel  " + "								   WHERE rel.CODI_K_ID_ATTO_OR = " + idAttoOrigine
						+ "									 AND rel.CODI_K_ID_UFF_OWNER_OR = " + idUffOwn).addEntity("rel", TRelAtti.class);
		List l = query.list();

		if (l != null && l.size() > 0)
			return true;
		else
			return false;

	}

	public Map inserisciAttoDecretoAsseRemNuovoRuolo(final TUdienza udienza, final long idTipoAtto, final long idCategoriaAtto,
			final long idUdienzaPrecedente) {

		return (Map)getHibernateTemplate().execute(new HibernateCallback() {

			public Map doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_insDecretoAsseRemNuovoRuolo");
				q.setParameter("idUffOwn", udienza.getId().getCodiKIdUffOwner(), Hibernate.INTEGER);
				q.setParameter("descUtente", udienza.getDescUtente(), Hibernate.STRING);
				q.setParameter("idUdienzaPrecedente", idUdienzaPrecedente, Hibernate.LONG);
				q.setParameter("idProcedimento", udienza.getTProcedimento().getCodiKIdProc(), Hibernate.LONG);
				q.setParameter("idTipoAtto", idTipoAtto, Hibernate.LONG);
				q.setParameter("idCatAtto", idCategoriaAtto, Hibernate.LONG);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long idAtto = (Long) first.get("IDCURRENTATTO");
				Map<String, Long> resultMap = new HashMap<String, Long>();
				resultMap.put("ID_ATTO", idAtto);
				return resultMap;
			}
		});
	}

	public Map salvaUdienzaDecretoRiemissioneSuRuolo(final TUdienza udienza, final long codiceAtto, final TAtto atto) {

		return (Map)getHibernateTemplate().execute(new HibernateCallback() {

			public Map doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_salvaUdienzaDecretoRemSuRuolo");
				q.setParameter("idUffOwn", udienza.getId().getCodiKIdUffOwner(), Hibernate.INTEGER);
				q.setParameter("descUtente", udienza.getDescUtente(), Hibernate.STRING);
				q.setParameter("idProcedimento", udienza.getTProcedimento().getCodiKIdProc(), Hibernate.LONG);
				q.setParameter("codAtto", codiceAtto, Hibernate.LONG);
				q.setParameter("dataUdienza", udienza.getDataUdienza(), Hibernate.DATE);
				q.setParameter("oraInizioUdienza", udienza.getOraInizioUdienza(), Hibernate.STRING);
				q.setParameter("indirizzoAula", udienza.getDescIndirAula(), Hibernate.STRING);
				q.setParameter("aula", udienza.getDescAula(), Hibernate.STRING);
				q.setParameter("cognomeCanc", udienza.getDescCognomeAssGiu(), Hibernate.STRING);
				q.setParameter("nomeCanc", udienza.getDescNomeAssGiu(), Hibernate.STRING);
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long idUdienza = (Long) first.get("IDCURRENTUDIENZA");
				Map<String, Long> resultMap = new HashMap<String, Long>();
				resultMap.put("ID_UDIENZA", idUdienza);
				return resultMap;
			}
		});
	}

	public long inviaAttoDecretoRemissioneSuRuolo(final TAtto atto, final int idUffOwn, final Long idProcedimento, final String codiceUtente,
			final long idStatoAtto, final long codUdienzaPrec, final long codUdienzaNew, final long idProcUdi) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_inserisciDecretoRemissioneSuRuolo");
				q.setParameter("idUffOwn", idUffOwn, Hibernate.INTEGER);
				q.setParameter("idProc", idProcedimento, Hibernate.LONG);
				q.setParameter("idMittente", idUffOwn, Hibernate.INTEGER);
				q.setParameter("idProcInUdienza", idProcUdi, Hibernate.LONG);
				if (atto.getDataEmissione() != null) {
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				} else {
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				}
				if (atto.getDataDeposito() != null) {
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				} else {
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				}
				if (atto.getDataRegistrazione() != null) {
					q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				} else {
					q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				}
				q.setParameter("descUtente", codiceUtente, Hibernate.STRING);
				q.setParameter("idAtto", atto.getId().getCodiKIdAtto(), Hibernate.LONG);
				if ((atto.getTTpTipoAtto() != null) && (atto.getTTpTipoAtto().getCodiKIdTipoAtto() > 0)) {
					q.setParameter("idTipoAtto", atto.getTTpTipoAtto().getCodiKIdTipoAtto());
				} else {
					q.setParameter("idTipoAtto", null, Hibernate.LONG);
				}
				if (atto.getTTpCatAtto().getCodiKIdCatAtto() > 0) {
					q.setParameter("idCatAtto", atto.getTTpCatAtto().getCodiKIdCatAtto());
				} else {
					q.setParameter("idCatAtto", null, Hibernate.LONG);
				}
				if (idStatoAtto > 0) {
					q.setParameter("idStatoAtto", idStatoAtto);
				} else {
					q.setParameter("idStatoAtto", null, Hibernate.LONG);
				}
				q.setParameter("idUdienzaPrec", codUdienzaPrec, Hibernate.LONG);
				q.setParameter("idUdienzaNew", codUdienzaNew, Hibernate.LONG);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long id = (Long) first.get("IDATTO");
				return id;
			}
		});

	}

	public long inviaAttoDecretoAssegnazioneNuovoRuolo(final TAtto atto, final int idUffOwn, final long idProcedimento, final String codiceUtente,
			final long idStatoAtto, final long codUdienza, final long idProcUdi) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_inserisciDecretoAssegnazioneNuovoRuolo");
				q.setParameter("idUffOwn", idUffOwn, Hibernate.INTEGER);
				q.setParameter("idProc", idProcedimento, Hibernate.LONG);
				q.setParameter("idMittente", idUffOwn, Hibernate.INTEGER);
				q.setParameter("idProcInUdienza", idProcUdi, Hibernate.LONG);
				if (atto.getDataEmissione() != null) {
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				} else {
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				}
				if (atto.getDataDeposito() != null) {
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				} else {
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				}
				if (atto.getDataRegistrazione() != null) {
					q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				} else {
					q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				}
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				q.setParameter("descUtente", codiceUtente, Hibernate.STRING);
				q.setParameter("idAtto", atto.getId().getCodiKIdAtto(), Hibernate.LONG);
				if ((atto.getTTpTipoAtto() != null) && (atto.getTTpTipoAtto().getCodiKIdTipoAtto() > 0)) {
					q.setParameter("idTipoAtto", atto.getTTpTipoAtto().getCodiKIdTipoAtto());
				} else {
					q.setParameter("idTipoAtto", null, Hibernate.LONG);
				}
				if (atto.getTTpCatAtto().getCodiKIdCatAtto() > 0) {
					q.setParameter("idCatAtto", atto.getTTpCatAtto().getCodiKIdCatAtto());
				} else {
					q.setParameter("idCatAtto", null, Hibernate.LONG);
				}
				if (idStatoAtto > 0) {
					q.setParameter("idStatoAtto", idStatoAtto);
				} else {
					q.setParameter("idStatoAtto", null, Hibernate.LONG);
				}
				q.setParameter("idUdienza", codUdienza, Hibernate.LONG);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long id = (Long) first.get("IDATTO");
				return id;
			}
		});
	}

	public Object getAttoByPrimaryKey(Class clazz, Criterion c1, Criterion c2) {

		List<?> lista = null;
		Object model = new Object();
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		criteria.add(c1);
		criteria.add(c2);
		lista = getHibernateTemplate().findByCriteria(criteria);
		if (lista.size() > 0) {
			model = lista.get(0);
		} else {
			model = null;
		}
		return model;
	}

	public long inserisciAttoGenerico(final int idUffOwn, final long idProcedimento, final String codiceUtente, final int idTipoAtto,
			final int idCatAtto, final int idStatoAtto, final String nomeMetodo) {
		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_inserisci_atto_generico");
				q.setParameter("idUffOwn", idUffOwn, Hibernate.INTEGER);
				q.setParameter("descUtente", codiceUtente, Hibernate.STRING);
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setParameter("idTipoAtto", idTipoAtto, Hibernate.INTEGER);
				q.setParameter("idCatAtto", idCatAtto, Hibernate.INTEGER);
				q.setParameter("idStatoAtto", idStatoAtto, Hibernate.INTEGER);
				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long idAtto = (Long) first.get("IDCURRENTATTO");

				return idAtto;
			}
		});
	}

	// INIZIO GESTIONE DOCUMENTALE

	// UPDATE T_ATTO
	// Set
	// FLG_FIRMA = 'S'
	// WHERE NUME_DOCUMENTO_PDOC = vIdDocumento;
	public boolean eseguitoFirmaRemota(final long idDocumento) {
		return (Boolean)getHibernateTemplate().execute(new HibernateCallback() {
			public Boolean doInHibernate(final Session session) throws HibernateException, SQLException {
				boolean result = false;
				try {
					Query q = session.getNamedQuery("PG_ATTO_eseguitoFirmaRemota");
					q.setParameter("vIdDocumento", idDocumento, Hibernate.LONG);
					q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
					List l = q.list();
					HashMap first = (HashMap) l.get(0);
					Long idDocResult = (Long) first.get("ID_DOC");
					if (idDocResult != null && idDocResult.longValue() > 0)
						result = true;
				} catch (Exception e) {
					result = false;
				} finally {
					System.out.println(result);
				}
				return result;
			}
		});
	}

	public boolean inserisciDocumentoPDoc(final Long idDocumentoInserito, final Long vCodiKIdAtto, final int vCodiKIdUffOwn,
			final String tipoDocumento, final String fileName, final String codugnaList) {
		return (Boolean)getHibernateTemplate().execute(new HibernateCallback() {
			public Boolean doInHibernate(final Session session) throws HibernateException, SQLException {
				boolean result = false;
				try {
					if(logger.isDebugEnabled()){
						logger.debug("    3.1) Valori ==> " + idDocumentoInserito + "  idAttoUfficio " + vCodiKIdAtto + "-" + vCodiKIdUffOwn);
						logger.debug("       **TipoDoc==> " + tipoDocumento + " nome Documento " + fileName + " - " + codugnaList);
					}
					Query q = session.getNamedQuery("PG_ATTO_inserisciDocumentoPDoc");
					q.setParameter("vIdDocumentoPDoc", idDocumentoInserito, Hibernate.LONG);
					q.setParameter("vCodiKIdAtto", vCodiKIdAtto, Hibernate.LONG);
					q.setParameter("vCodiKIdUffOwn", vCodiKIdUffOwn, Hibernate.INTEGER);
					q.setParameter("vTipoDocumento", tipoDocumento, Hibernate.STRING);
					q.setParameter("vFileName", fileName, Hibernate.STRING);
					q.setParameter("str_codugna", codugnaList, Hibernate.STRING);
					q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
					List l = q.list();
					HashMap first = (HashMap) l.get(0);
					Long idProcResult = (Long) first.get("ID_DOC");
					if (idProcResult != null && idProcResult.longValue() > 0)
						result = true;
				} catch (Exception e) {
					result = false;
				} finally {
					System.out.println(result);
				}
				return result;
			}
		});
	}

	public long rimuoviDocumentoAllegatoDaAtto(final long idUfficio, final long codAtto, final long idProcedimento) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_rimuoviDocumentoAllegato");
				q.setParameter("idUffOwn", idUfficio, Hibernate.LONG);
				q.setParameter("idAtto", codAtto, Hibernate.LONG);
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long id = (Long) first.get("IDCURRENTATTO");
				return id;
			}
		});
	}

	// FINE GESTIONE DOCUMENTALE

	public KeyMessageDto cancellaAtto(final int idUfficio, final long codAtto, final String descUtente, final String nomeMetodo) {

		return (KeyMessageDto) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_cancellaAtto");
				q.setParameter("idUffOwn", idUfficio, Hibernate.INTEGER);
				q.setParameter("idAtto", codAtto, Hibernate.LONG);
				q.setParameter("descUtente", descUtente, Hibernate.STRING);
				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return new KeyMessageDto(l);
			}
		});
	}

	public List<TRelAtti> getRelAtti(final long idAttoOrigine, final int idUffOwnerOrigine, final long idAttoFinale, final int idUffOwnerFinale,
			final String descUtente, final String nomeMetodo) {

		return (List<TRelAtti>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_listaRelAtti");
				if (idAttoOrigine > 0) {
					q.setParameter("idAttoOrigine", idAttoOrigine, Hibernate.LONG);
				} else {
					q.setParameter("idAttoOrigine", null, Hibernate.LONG);
				}
				if (idUffOwnerOrigine > 0) {
					q.setParameter("idUffOwnOrigine", idUffOwnerOrigine, Hibernate.INTEGER);
				} else {
					q.setParameter("idUffOwnOrigine", null, Hibernate.INTEGER);
				}
				if (idAttoFinale > 0) {
					q.setParameter("idAttoFinale", idAttoFinale, Hibernate.LONG);
				} else {
					q.setParameter("idAttoFinale", null, Hibernate.LONG);
				}
				if (idUffOwnerFinale > 0) {
					q.setParameter("idUffOwnFinale", idUffOwnerFinale, Hibernate.INTEGER);
				} else {
					q.setParameter("idUffOwnFinale", null, Hibernate.INTEGER);
				}

				q.setParameter("descUtente", descUtente, Hibernate.STRING);
				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});
	}
	

	public List<TRelAttoIstGen> getSogNoDecErroreMat(final long idProcedimento, final long idAtto, final long idUffOwn,final String descAtto) {

		return (List<TRelAttoIstGen>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_listaRelAttoIstGen");
				
				if (idUffOwn > 0) {
					q.setParameter("IDUFFOWN", idUffOwn, Hibernate.LONG);
				} else {
					q.setParameter("IDUFFOWN", null, Hibernate.LONG);
				}				
				if (idProcedimento > 0) {
					q.setParameter("IDPROC", idProcedimento, Hibernate.LONG);
				} else {
					q.setParameter("IDPROC", null, Hibernate.LONG);
				}
				if (idAtto > 0) {
					q.setParameter("IDATTO", idAtto, Hibernate.LONG);
				} else {
					q.setParameter("IDATTO", null, Hibernate.LONG);
				}


				q.setParameter("DESC_ATTO", descAtto, Hibernate.STRING);


				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});
	}	

	public TRelAttoProc getRelAttoProcByAtto(final long idProcedimento, final int idUfficioUtente, final long codiKIdAtto) {
		return (TRelAttoProc) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				List<TRelAttoProc> lista = null;
				DetachedCriteria criteria = DetachedCriteria.forClass(TRelAttoProc.class);
				criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente));
				criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
				criteria.add(Restrictions.eq("id.codiKIdAtto", codiKIdAtto));

				lista = getHibernateTemplate().findByCriteria(criteria);
				TRelAttoProc relazione = lista.get(0);

				return relazione;
			}
		});
	}

	
	public KeyMessageDto isEsisteDecretoSequestroProcedimento(final long idProcedimento, final Integer idUfficioUtente, final String descUtente,
			final String nomeMetodo) {

		return (KeyMessageDto)getHibernateTemplate().execute(new HibernateCallback() {

			public KeyMessageDto doInHibernate(final Session session) throws HibernateException, SQLException {
				Query q = session.getNamedQuery("PG_ATTO_isEsisteDecretoSequestroProcedimento");
				q.setParameter("idProc", idProcedimento);
				q.setParameter("idUffOwn", idUfficioUtente);
				q.setParameter("descUtente", descUtente, Hibernate.STRING);
				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				KeyMessageDto rit = new KeyMessageDto();

				if (l != null && l.size() == 1) {
					HashMap ritorno = (HashMap) l.get(0);
					rit.setMessage((String) ritorno.get(Constants.TipoMessaggiDb.MESSAGE));
					rit.setStatus((String) ritorno.get(Constants.TipoMessaggiDb.STATUS));

				}
				return rit;

			}
		});
	}

	public KeyMessageDto insDecretoRigettoTotaleSequestro(final int idUffOwn, final long idProcedimento, final String codiceUtente, final TAtto atto,
			final long idStatoAtto, final String nomeMetodo) {

		return (KeyMessageDto)getHibernateTemplate().execute(new HibernateCallback() {

			public KeyMessageDto doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_insDecretoRigettoTotaleSequestro");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("descUtente", codiceUtente);
				q.setParameter("idProcedimento", idProcedimento);
				if ((atto.getTTpTipoAtto() != null) && (atto.getTTpTipoAtto().getCodiKIdTipoAtto() > 0)) {
					q.setParameter("idTipoAtto", atto.getTTpTipoAtto().getCodiKIdTipoAtto());
				} else {
					q.setParameter("idTipoAtto", null, Hibernate.LONG);
				}
				if (atto.getTTpCatAtto().getCodiKIdCatAtto() > 0) {
					q.setParameter("idCatAtto", atto.getTTpCatAtto().getCodiKIdCatAtto());
				} else {
					q.setParameter("idCatAtto", null, Hibernate.LONG);
				}
				if (idStatoAtto > 0) {
					q.setParameter("idStatoAtto", idStatoAtto);
				} else {
					q.setParameter("idStatoAtto", null, Hibernate.LONG);
				}
				if (atto.getDataEmissione() != null) {
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				} else {
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				}
				if (atto.getDataDeposito() != null) {
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				} else {
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				}
				if (atto.getDataRegistrazione() != null) {
					q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				} else {
					q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				}
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return new KeyMessageDto(l);
			}
		});
	}

	public Long inviaAttoDecretoRigettoTotaleSequestro(final long idUfficio, final long idProcedimento, final TAtto atto, final String codUtente,
			final long tipoAtto, final long categoriaAtto, final long idStatoAtto, final long idDestinatario, final long idUltDestinatario,
			final long codUdienzaAtto, final long codUdienzaFiss, final long codUdienzaInFaseDiRip, final long codUdienzaInFaseDiRim,
			final long statoUdienzaPrec, final String nomeMetodo) {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				/* 
				 * ISSUE MEV : Specifiche Intervento-006-SpeseProces-NumAtto
				 * Numero MEV : 006
				 * Autore    : Gioggi
				 * Data      : 18 apr 2020
				 * Branch    : MEV_006
				 */
				Query q;
				if (esisteNumAtto(atto.getId().getCodiKIdAtto(), idUfficio))
					q = session.getNamedQuery("PG_NUMERO_ATTO_generaFlussoDopoNumeroAttoDRTS");
				else
					q = session.getNamedQuery("PG_ATTO_inviaDecretoRigettoTotaleSequestro");
				//***** FINE INTERVENTO MEV_006 *****//

				q.setParameter("idUffOwn", idUfficio);
				q.setParameter("idProc", idProcedimento, Hibernate.LONG);
				if (atto.getDataRegistrazione() != null) {
					q.setParameter("idMittente", idUfficio, Hibernate.LONG);
					q.setParameter("idDestinatario", idDestinatario, Hibernate.LONG);
					q.setParameter("idUltDestinatario", idUltDestinatario, Hibernate.LONG);
					q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				} else {
					q.setParameter("idMittente", null, Hibernate.LONG);
					q.setParameter("idDestinatario", null, Hibernate.LONG);
					q.setParameter("idUltDestinatario", null, Hibernate.LONG);
					q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				}
				if (atto.getDataEmissione() != null) {
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				} else {
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				}
				if (atto.getDataDeposito() != null) {
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				} else {
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				}
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				if (atto.getDataRicezione() != null) {
					q.setParameter("dataRicezioneDoc", atto.getDataRicezione(), Hibernate.DATE);
				} else {
					q.setParameter("dataRicezioneDoc", null, Hibernate.DATE);
				}
				q.setParameter("descUtente", codUtente);
				q.setParameter("idAtto", atto.getId().getCodiKIdAtto(), Hibernate.LONG);
				q.setParameter("tipoAtto", tipoAtto, Hibernate.LONG);
				q.setParameter("categoriaAtto", categoriaAtto, Hibernate.LONG);
				q.setParameter("idStatoAtto", idStatoAtto, Hibernate.LONG);
				q.setParameter("idUdienzaAtto", codUdienzaAtto, Hibernate.LONG);
				q.setParameter("idUdienzaFiss", codUdienzaFiss, Hibernate.LONG);
				q.setParameter("idUdienzaRip", codUdienzaInFaseDiRip, Hibernate.LONG);
				q.setParameter("idUdienzaRim", codUdienzaInFaseDiRim, Hibernate.LONG);
				q.setParameter("statoUdienzaOld", statoUdienzaPrec, Hibernate.LONG);
				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				q.list();
				return atto.getId().getCodiKIdAtto();
			}
		});
	}

	public Long registraAttoGenerico(final long idUfficio, final long idProcedimento, final TAtto atto, final String codUtente,
			final long idStatoAtto, final TAttoGenerico attoGenerico, final String nomeMetodo) {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_registraAttoGenerico");
				q.setParameter("idUffOwn", idUfficio);
				q.setParameter("idProc", idProcedimento, Hibernate.LONG);

				if (atto.getDataEmissione() != null) {
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				} else {
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				}
				if (atto.getDataRegistrazione() != null) {
					q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				} else {
					q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				}

				if (atto.getDataRicezione() != null) {
					q.setParameter("dataRicezione", atto.getDataRicezione(), Hibernate.DATE);
				} else {
					q.setParameter("dataRicezione", null, Hibernate.DATE);
				}

				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}

				q.setParameter("descUtente", codUtente);

				q.setParameter("idAtto", atto.getId().getCodiKIdAtto(), Hibernate.LONG);
				if ((atto.getTTpTipoAtto() != null) && (atto.getTTpTipoAtto().getCodiKIdTipoAtto() > 0)) {
					q.setParameter("tipoAtto", atto.getTTpTipoAtto().getCodiKIdTipoAtto(), Hibernate.INTEGER);
				} else {
					q.setParameter("tipoAtto", null, Hibernate.INTEGER);
				}

				if ((atto.getTTpCatAtto() != null) && (atto.getTTpCatAtto().getCodiKIdCatAtto() > 0)) {
					q.setParameter("categoriaAtto", atto.getTTpCatAtto().getCodiKIdCatAtto(), Hibernate.INTEGER);
				} else {
					q.setParameter("categoriaAtto", null, Hibernate.INTEGER);
				}

				if (idStatoAtto > 0) {
					q.setParameter("idStatoAtto", idStatoAtto, Hibernate.LONG);
				} else {
					q.setParameter("idStatoAtto", null, Hibernate.LONG);
				}
				if (attoGenerico != null && attoGenerico.getTAnUfficio() != null && attoGenerico.getTAnUfficio().getCodiKIdUfficio() > 0) {
					q.setParameter("idUffMittente", attoGenerico.getTAnUfficio().getCodiKIdUfficio(), Hibernate.INTEGER);
				} else {
					q.setParameter("idUffMittente", null, Hibernate.INTEGER);
				}
				q.setParameter("descNote", atto.getDescNote(), Hibernate.STRING);
				if (attoGenerico != null) {
					q.setParameter("flgSpedRic", attoGenerico.getFlgSpedRic(), Hibernate.CHARACTER);
					q.setParameter("descNumeRif", attoGenerico.getDescNumeRif(), Hibernate.STRING);
					q.setParameter("descOrgano", attoGenerico.getDescOrgano(), Hibernate.STRING);
					q.setParameter("descSedeOrgano", attoGenerico.getDescSedeOrgano(), Hibernate.STRING);
				} else {
					q.setParameter("flgSpedRic", null, Hibernate.CHARACTER);
					q.setParameter("descNumeRif", null, Hibernate.STRING);
					q.setParameter("descOrgano", null, Hibernate.STRING);
					q.setParameter("descSedeOrgano", null, Hibernate.STRING);
				}
				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				q.list();
				return atto.getId().getCodiKIdAtto();
			}
		});
	}

	
	public Long registraOrdinanazaArt15Antimafia( final long idUfficio,  final long idProcedimento,  final TAtto atto, final String codUtente,
			final long idStatoAtto,final Date dataCessazioneMisura, final TAttoGenerico attoGenerico, final String nomeMetodo)
     {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				
				Query q = session.getNamedQuery("PG_ATTO_registraOrdinanazaArt15Antimafia");
				q.setParameter("idUffOwn", idUfficio);
				q.setParameter("idProc", idProcedimento, Hibernate.LONG);
				q.setParameter("idAtto", atto.getId().getCodiKIdAtto(), Hibernate.LONG);
				
				if (atto.getDataDeposito() != null) {
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				} else {
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				}


				if (dataCessazioneMisura != null) {
					q.setParameter("dataCessazioneMisura", dataCessazioneMisura, Hibernate.DATE);
				} else {
					q.setParameter("dataCessazioneMisura", null, Hibernate.DATE);
				}
				
				if (atto.getDataRicezione() != null) {
					q.setParameter("dataRicezione", atto.getDataRicezione(), Hibernate.DATE);
				} else {
					q.setParameter("dataRicezione", null, Hibernate.DATE);
				}				
				
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}

				q.setParameter("descUtente", codUtente);

				if (idStatoAtto > 0) {
					q.setParameter("idStatoAtto", idStatoAtto, Hibernate.LONG);
				} else {
					q.setParameter("idStatoAtto", null, Hibernate.LONG);
				}

				if (attoGenerico != null && attoGenerico.getTAnUfficio() != null && attoGenerico.getTAnUfficio().getCodiKIdUfficio() > 0) {
					q.setParameter("idUffMittente", attoGenerico.getTAnUfficio().getCodiKIdUfficio(), Hibernate.INTEGER);
				} else {
					q.setParameter("idUffMittente", null, Hibernate.INTEGER);
				}				
				q.setParameter("descNote", atto.getDescNote(), Hibernate.STRING);
				
				q.setParameter("flgSpedRic", attoGenerico.getFlgSpedRic(), Hibernate.CHARACTER);
				q.setParameter("descNumeRif", attoGenerico.getDescNumeRif(), Hibernate.STRING);
				q.setParameter("descOrgano", attoGenerico.getDescOrgano(), Hibernate.STRING);
				q.setParameter("descSedeOrgano", attoGenerico.getDescSedeOrgano(), Hibernate.STRING);
				


				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				q.list();
				return atto.getId().getCodiKIdAtto();
			}
		});
	}
		

	public Long registraIntercettazioniTelefoniche( final long idUfficio,  final long idProcedimento,  final TAtto atto, final String codUtente,
			final long idStatoAtto,final long idMagistrato, final TAttoGenerico attoGenerico, final String nomeMetodo)
     {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				
				Query q = session.getNamedQuery("PG_ATTO_registraIntercettazioniTelefoniche");
				q.setParameter("idUffOwn", idUfficio);
				q.setParameter("idProc", idProcedimento, Hibernate.LONG);
				q.setParameter("idAtto", atto.getId().getCodiKIdAtto(), Hibernate.LONG);
				
				if (atto.getDataEmissione() != null) {
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				} else {
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				}
				if (atto.getDataDeposito() != null) {
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				} else {
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				}				
				
				q.setParameter("idMagistrato", idMagistrato, Hibernate.LONG);
				
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}

				q.setParameter("descUtente", codUtente);

				if (idStatoAtto > 0) {
					q.setParameter("idStatoAtto", idStatoAtto, Hibernate.LONG);
				} else {
					q.setParameter("idStatoAtto", null, Hibernate.LONG);
				}

				if (attoGenerico != null && attoGenerico.getTAnUfficio() != null && attoGenerico.getTAnUfficio().getCodiKIdUfficio() > 0) {
					q.setParameter("idUffMittente", attoGenerico.getTAnUfficio().getCodiKIdUfficio(), Hibernate.INTEGER);
				} else {
					q.setParameter("idUffMittente", null, Hibernate.INTEGER);
				}
				
				
				q.setParameter("descNote", atto.getDescNote(), Hibernate.STRING);
				
				q.setParameter("flgSpedRic", attoGenerico.getFlgSpedRic(), Hibernate.CHARACTER);
				//q.setParameter("descNumeRif", attoGenerico.getDescNumeRif(), Hibernate.STRING);
				q.setParameter("descOrgano", attoGenerico.getDescOrgano(), Hibernate.STRING);
				q.setParameter("descSedeOrgano", attoGenerico.getDescSedeOrgano(), Hibernate.STRING);
				


				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				q.list();
				return atto.getId().getCodiKIdAtto();
			}
		});
	}	
	
	public Long registraOrdinanazaArt14Antimafia( final long idUfficio,  final long idProcedimento,  final TAtto atto, final String codUtente,
			final long idStatoAtto,final long idMagistrato,final Date dataCessazioneMisura, final Date dataRipresaMisura, final TAttoGenerico attoGenerico, final String nomeMetodo)
     {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				
				Query q = session.getNamedQuery("PG_ATTO_registraOrdinanazaArt14Antimafia");
				q.setParameter("idUffOwn", idUfficio);
				q.setParameter("idProc", idProcedimento, Hibernate.LONG);
				q.setParameter("idAtto", atto.getId().getCodiKIdAtto(), Hibernate.LONG);
				
				if (atto.getDataEmissione() != null) {
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				} else {
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				}
				if (atto.getDataDeposito() != null) {
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				} else {
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				}
				
				q.setParameter("idMagistrato", idMagistrato, Hibernate.LONG);
				
				if (dataRipresaMisura != null) {
					q.setParameter("dataRipresaMisura", dataRipresaMisura, Hibernate.DATE);
				} else {
					q.setParameter("dataRipresaMisura", null, Hibernate.DATE);
				}

				if (dataCessazioneMisura != null) {
					q.setParameter("dataCessazioneMisura", dataCessazioneMisura, Hibernate.DATE);
				} else {
					q.setParameter("dataCessazioneMisura", null, Hibernate.DATE);
				}
				
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}

				q.setParameter("descUtente", codUtente);

				if (idStatoAtto > 0) {
					q.setParameter("idStatoAtto", idStatoAtto, Hibernate.LONG);
				} else {
					q.setParameter("idStatoAtto", null, Hibernate.LONG);
				}

				q.setParameter("descNote", atto.getDescNote(), Hibernate.STRING);

				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				q.list();
				return atto.getId().getCodiKIdAtto();
			}
		});
	}
	

	
	public List<SogIntDettaglioProcedimentoDto> getRicercaSoggettoAtto(final long idProcedimento, final long idatto, final int iduff,
			final int descRuolo, final Character flagValido) {

		return (List<SogIntDettaglioProcedimentoDto>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_ricercaSoggettoAtto");

				q.setParameter("idProcedimento", idProcedimento);
				q.setParameter("idatto", idatto);
				q.setParameter("iduff", iduff);

				if (descRuolo >= 0) {
					q.setParameter("descruolo", descRuolo);
				} else {
					q.setParameter("descruolo", null, Hibernate.INTEGER);
				}

				if ((flagValido != null) && !"".equals(flagValido.toString())) {
					q.setParameter("flagvalido", flagValido, Hibernate.CHARACTER);
				} else {
					q.setParameter("flagvalido", null, Hibernate.CHARACTER);
				}

				q.setResultTransformer(Transformers.aliasToBean(SogIntDettaglioProcedimentoDto.class));
				return q.list();
			}
		});
	}

	
	public List<SogIntDettaglioProcedimentoDto> getRicercaSoggettoRicorso(final long idProcedimento, final long idatto, final int iduff,final int descRuolo) {
		return (List<SogIntDettaglioProcedimentoDto>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_ricercaSoggettoRicorso");
				q.setParameter("idProcedimento", idProcedimento);
				q.setParameter("idatto", idatto);
				q.setParameter("iduff", iduff);

				if (descRuolo >= 0) {
					q.setParameter("descruolo", descRuolo);
				} else {
					q.setParameter("descruolo", null, Hibernate.INTEGER);
				}

				q.setResultTransformer(Transformers.aliasToBean(SogIntDettaglioProcedimentoDto.class));
				return q.list();
			}
		});
	}
	

	
	public List<TAvvocati> getRicercaAvvRicorso(final long idProcedimento, final long idatto, final int iduff) {
		return (List<TAvvocati>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_ricercaAvvRicorso");
				q.setParameter("idProcedimento", idProcedimento);
				q.setParameter("idatto", idatto);
				q.setParameter("iduff", iduff);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				return q.list();
				
			}
		});
	}	

	// MODIFICA 01/2014
	// CONTROLLO NUOVO ATTO PRESIDENZIALE/COLLEGIALE
	public KeyMessageDto controllaCollegioAtto(final long idProcedimento, final String idCatAtto, final String descUtente, final long idUffOwn,
			final String printMethodName) {

		return (KeyMessageDto) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_gestioneCollegioAtto");
				q.setParameter("idProc", idProcedimento, Hibernate.LONG);
				q.setParameter("idCatAtto", idCatAtto, Hibernate.STRING);
				q.setParameter("descUtente", descUtente, Hibernate.STRING);
				q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
				q.setParameter("nomeMetodo", printMethodName, Hibernate.STRING);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return new KeyMessageDto(l);
			}
		});
	}

	// CONTROLLO INVIO ATTO PRESIDENZIALE/COLLEGIALE
	public KeyMessageDto controllaCollegioAttoInvio(final long idProcedimento, final long idAtto, final String descUtente, final long idUfficio,
			final String printMethodName) {

		return (KeyMessageDto) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_gestioneCollegioAttoInvio");
				q.setParameter("idProc", idProcedimento, Hibernate.LONG);
				q.setParameter("idAtto", idAtto, Hibernate.LONG);
				q.setParameter("descUtente", descUtente, Hibernate.STRING);
				q.setParameter("idUfficio", idUfficio, Hibernate.LONG);
				q.setParameter("nomeMetodo", printMethodName, Hibernate.STRING);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return new KeyMessageDto(l);
			}
		});
	}

	// MODIFICA 01/2014

	public Long getMagistratoPerAtto(final Integer idUffOwn, final long idAtto, final int descRuolo) {
		return (Long) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				List<TRelMagAtto> lista = null;
				DetachedCriteria criteria = DetachedCriteria.forClass(TRelMagAtto.class);
				criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUffOwn));
				criteria.add(Restrictions.eq("id.codiKIdRuoloMag", descRuolo));
				criteria.add(Restrictions.eq("id.codiKIdAtto", idAtto));

				lista = getHibernateTemplate().findByCriteria(criteria);

				if (lista.size() > 0) {
					return lista.get(0).getId().getCodiKIdMagistrato();
				}

				return null;
			}
		});
	}

	
	public List<RicercaStralcioDto> getRicercaMisureAtto(final long idAtto, final long idProcedimento, final long idUfficio, final String idMisure,
			final String idSoggettiInt, final String idSoggettiTit, final Integer idTipoBene, final Integer idCatBene, final Integer idSctgBene,
			final String flgValido, final int categoriaAtto, final int stato) {

		return (List<RicercaStralcioDto>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_ricercaMisureAtto");

				if (idAtto > 0) {
					q.setParameter("idAtto", idAtto, Hibernate.LONG);
				} else {
					q.setParameter("idAtto", 0, Hibernate.LONG);
				}

				if (idProcedimento > 0) {
					q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				} else {
					q.setParameter("idProcedimento", 0, Hibernate.LONG);
				}
				if (idUfficio > 0) {
					q.setParameter("idUfficio", idUfficio, Hibernate.LONG);
				} else {
					q.setParameter("idUfficio", 0, Hibernate.LONG);
				}
				if (idMisure != null) {
					q.setParameter("idMisure", idMisure, Hibernate.STRING);
				} else {
					q.setParameter("idMisure", null, Hibernate.STRING);
				}

				if (idSoggettiInt != null) {
					q.setParameter("idSoggettiInt", idSoggettiInt, Hibernate.STRING);
				} else {
					q.setParameter("idSoggettiInt", null, Hibernate.STRING);
				}

				if (idSoggettiTit != null) {
					q.setParameter("idSoggettiTit", idSoggettiTit, Hibernate.STRING);
				} else {
					q.setParameter("idSoggettiTit", null, Hibernate.STRING);
				}

				if (idTipoBene != null) {
					q.setParameter("idTipoBene", idTipoBene, Hibernate.INTEGER);
				} else {
					q.setParameter("idTipoBene", null, Hibernate.INTEGER);
				}

				if (idCatBene != null) {
					q.setParameter("idCatBene", idCatBene, Hibernate.INTEGER);
				} else {
					q.setParameter("idCatBene", null, Hibernate.INTEGER);
				}
				if (idSctgBene != null) {
					q.setParameter("idSctgBene", idSctgBene, Hibernate.INTEGER);
				} else {
					q.setParameter("idSctgBene", null, Hibernate.INTEGER);
				}

				if (flgValido != null) {
					q.setParameter("flgValido", flgValido, Hibernate.STRING);
				} else {
					q.setParameter("flgValido", null, Hibernate.STRING);
				}

				q.setParameter("categoriaAtto", categoriaAtto, Hibernate.INTEGER);

				q.setParameter("stato", stato, Hibernate.INTEGER);

				q.setResultTransformer(Transformers.aliasToBean(RicercaStralcioDto.class));
				return q.list();
			}
		});
	}

	
	public List<RicercaStralcioDto> getRicercaMisureAttoCass(final long idAtto, final long idProcedimento, final long idUfficio,
			final String idMisure, final String idSoggettiInt, final String idSoggettiTit, final Integer idTipoBene, final Integer idCatBene,
			final Integer idSctgBene, final String flgValido, final int categoriaAtto, final int stato) {

		return (List<RicercaStralcioDto>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_ricercaMisureAttoCass");

				if (idAtto > 0) {
					q.setParameter("idAtto", idAtto, Hibernate.LONG);
				} else {
					q.setParameter("idAtto", 0, Hibernate.LONG);
				}

				if (idProcedimento > 0) {
					q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				} else {
					q.setParameter("idProcedimento", 0, Hibernate.LONG);
				}
				if (idUfficio > 0) {
					q.setParameter("idUfficio", idUfficio, Hibernate.LONG);
				} else {
					q.setParameter("idUfficio", 0, Hibernate.LONG);
				}
				if (idMisure != null) {
					q.setParameter("idMisure", idMisure, Hibernate.STRING);
				} else {
					q.setParameter("idMisure", null, Hibernate.STRING);
				}

				if (idSoggettiInt != null) {
					q.setParameter("idSoggettiInt", idSoggettiInt, Hibernate.STRING);
				} else {
					q.setParameter("idSoggettiInt", null, Hibernate.STRING);
				}

				if (idSoggettiTit != null) {
					q.setParameter("idSoggettiTit", idSoggettiTit, Hibernate.STRING);
				} else {
					q.setParameter("idSoggettiTit", null, Hibernate.STRING);
				}

				if (idTipoBene != null) {
					q.setParameter("idTipoBene", idTipoBene, Hibernate.INTEGER);
				} else {
					q.setParameter("idTipoBene", null, Hibernate.INTEGER);
				}

				if (idCatBene != null) {
					q.setParameter("idCatBene", idCatBene, Hibernate.INTEGER);
				} else {
					q.setParameter("idCatBene", null, Hibernate.INTEGER);
				}
				if (idSctgBene != null) {
					q.setParameter("idSctgBene", idSctgBene, Hibernate.INTEGER);
				} else {
					q.setParameter("idSctgBene", null, Hibernate.INTEGER);
				}

				if (flgValido != null) {
					q.setParameter("flgValido", flgValido, Hibernate.STRING);
				} else {
					q.setParameter("flgValido", null, Hibernate.STRING);
				}

				q.setParameter("categoriaAtto", categoriaAtto, Hibernate.INTEGER);

				q.setParameter("stato", stato, Hibernate.INTEGER);

				q.setResultTransformer(Transformers.aliasToBean(RicercaStralcioDto.class));
				return q.list();
			}
		});
	}

	/**
	 * salvaAttoRicorso
	 * 
	 * @param idUffOwn
	 * @param idProcedimento
	 * @param codiceUtente
	 * @param atto
	 * @param idStatoAtto
	 * @param idAttoOrigineMisure
	 * @return id atto originato
	 */
	public long salvaAttoRicorso(final int idUffOwn, final long idProcedimento, final String codiceUtente, final TAtto atto, final long idStatoAtto,
			final long idAttoOrigineMisure) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_inserisciRicorso");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("descUtente", codiceUtente);
				q.setParameter("idProcedimento", idProcedimento);
				if ((atto.getTTpTipoAtto() != null) && (atto.getTTpTipoAtto().getCodiKIdTipoAtto() > 0)) {
					q.setParameter("idTipoAtto", atto.getTTpTipoAtto().getCodiKIdTipoAtto(), Hibernate.INTEGER);
				} else {
					q.setParameter("idTipoAtto", null, Hibernate.INTEGER);
				}
				q.setParameter("idAttoOrigineMisure", idAttoOrigineMisure, Hibernate.LONG);
				if (atto.getTTpCatAtto().getCodiKIdCatAtto() > 0) {
					q.setParameter("idCatAtto", atto.getTTpCatAtto().getCodiKIdCatAtto(), Hibernate.INTEGER);
				} else {
					q.setParameter("idCatAtto", null, Hibernate.INTEGER);
				}

				if (idStatoAtto > 0) {
					q.setParameter("idStatoAtto", idStatoAtto, Hibernate.LONG);
				} else {
					q.setParameter("idStatoAtto", null, Hibernate.LONG);
				}
				if (atto.getDataEmissione() != null) {
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				} else {
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				}
				if (atto.getDataDeposito() != null) {
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				} else {
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				}

				q.setParameter("dataRegistrazione", null, Hibernate.DATE);

				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long id = (Long) first.get("IDATTO");
				return id;
			}
		});
	}

	public Long registraAttoRicorsoInAppello(final long idUfficio, final long idProcedimento,final String listaIdSoggetti, final TRelSoggImp soggImpTmp,final TImpugnazione impugnazione, final TAtto atto,
			final String codUtente, final long tipoAtto, final long categoriaAtto, final long idStatoAtto, final long idDestinatario, final Long codiKIdAmministratore, final BigDecimal idIntestatario) {
		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_registraRicorsoInAppello");
				q.setParameter("idUffOwn", idUfficio);
				q.setParameter("idProc", idProcedimento, Hibernate.LONG);
				if (atto.getDataRegistrazione() != null) {
					q.setParameter("idMittente", idUfficio, Hibernate.LONG);
					q.setParameter("idDestinatario", idDestinatario, Hibernate.LONG);

					q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				} else {
					q.setParameter("idMittente", null, Hibernate.LONG);
					q.setParameter("idDestinatario", null, Hibernate.LONG);
					q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				}
				if (atto.getDataEmissione() != null) {
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				} else {
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				}
				if (atto.getDataDeposito() != null) {
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				} else {
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				}
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				if (atto.getDataRicezione() != null) {
					q.setParameter("dataRicezioneDoc", atto.getDataRicezione(), Hibernate.DATE);
				} else {
					q.setParameter("dataRicezioneDoc", null, Hibernate.DATE);
				}
				q.setParameter("descUtente", codUtente);
				q.setParameter("idAtto", atto.getId().getCodiKIdAtto(), Hibernate.LONG);
				q.setParameter("tipoAtto", tipoAtto, Hibernate.LONG);
				q.setParameter("categoriaAtto", categoriaAtto, Hibernate.LONG);
				q.setParameter("idStatoAtto", idStatoAtto, Hibernate.LONG);

				/************* INIZIO IMPUGNAZIONE **************************/
				// DEPOSITO
				if (impugnazione.getTAnUfficio() != null && impugnazione.getTAnUfficio().getCodiKIdUfficio() != 0) {
					q.setParameter("codiFkIdUffDep", impugnazione.getTAnUfficio().getCodiKIdUfficio(), Hibernate.INTEGER);
				} else {
					q.setParameter("codiFkIdUffDep", null, Hibernate.INTEGER);
				}

				q.setParameter("flgUffDep", impugnazione.getFlgUffDep(), Hibernate.CHARACTER);
				if (impugnazione.getDataDepImp() != null) {
					q.setParameter("dataDepImp", impugnazione.getDataDepImp(), Hibernate.DATE);
				} else {
					q.setParameter("dataDepImp", null, Hibernate.DATE);
				}
				q.setParameter("oraDepImp", impugnazione.getOraDepImp(), Hibernate.STRING);

				if (impugnazione.getDataRegImp() != null) {
					q.setParameter("dataRegImp", impugnazione.getDataRegImp(), Hibernate.DATE);
				} else {
					q.setParameter("dataRegImp", null, Hibernate.DATE);
				}
				q.setParameter("oraRegImp", impugnazione.getOraRegImp(), Hibernate.STRING);

				// PERVENIMENTO
				if (impugnazione.getTTpModPerv() != null) {
					q.setParameter("codiKIdModPerv", impugnazione.getTTpModPerv().getCodiKIdModalita(), Hibernate.INTEGER);
				} else {
					q.setParameter("codiKIdModPerv", null, Hibernate.INTEGER);
				}

				if (impugnazione.getDataSpedizione() != null) {
					q.setParameter("dataSpedizione", impugnazione.getDataSpedizione(), Hibernate.DATE);
				} else {
					q.setParameter("dataSpedizione", null, Hibernate.DATE);
				}

				if (impugnazione.getDescProvPosta() != null) {
					q.setParameter("descProvPosta", impugnazione.getDescProvPosta(), Hibernate.STRING);
				} else {
					q.setParameter("descProvPosta", null, Hibernate.STRING);
				}

				if (impugnazione.getDataPervImp() != null) {
					q.setParameter("dataPervImp", impugnazione.getDataPervImp(), Hibernate.DATE);
				} else {
					q.setParameter("dataPervImp", null, Hibernate.DATE);
				}
				q.setParameter("oraPervImp", impugnazione.getOraPervImp(), Hibernate.STRING);

				// DEPOSITANTE
				q.setParameter("flaTipoParte", soggImpTmp.getFlgTipoParte(), Hibernate.CHARACTER);

				q.setParameter("descTipoProp", soggImpTmp.getDescTipoProp(), Hibernate.STRING);

				
				if(listaIdSoggetti != null){
					q.setParameter("listaIdSoggetti", listaIdSoggetti, Hibernate.STRING);
				} else {
					q.setParameter("listaIdSoggetti", null, Hibernate.STRING);
				}				

				if (soggImpTmp.getTAvvocati() != null
						&& soggImpTmp.getTAvvocati().getCodiKIdAvvocato() > 0) {
					q.setParameter("codiKIdAvvocato", soggImpTmp.getTAvvocati().getCodiKIdAvvocato(), Hibernate.LONG);
				} else {
					q.setParameter("codiKIdAvvocato", null, Hibernate.LONG);
				}

				// RICEVENTE
				q.setParameter("descCognFunz", impugnazione.getDescCognFunz(), Hibernate.STRING);
				q.setParameter("descNomeFunz", impugnazione.getDescNomeFunz(), Hibernate.STRING);

				// campo per altri istituti
				if (impugnazione.getTAnIstitutoDet() != null && impugnazione.getTAnIstitutoDet().getCodiKIdIstituto() > 0) {
					q.setParameter("codiFkIdIstDet", impugnazione.getTAnIstitutoDet().getCodiKIdIstituto(), Hibernate.INTEGER);
				} else {
					q.setParameter("codiFkIdIstDet", null, Hibernate.INTEGER);
				}

				// AGGIUNTE
				q.setParameter("descAltroMod", impugnazione.getDescAltroMod(), Hibernate.STRING);

				q.setParameter("descCognAvv", soggImpTmp.getDescCognAvv(), Hibernate.STRING);
				q.setParameter("descNomeAvv", soggImpTmp.getDescNomeAvv(), Hibernate.STRING);

				if(codiKIdAmministratore!=null) {
					q.setParameter("codiKIdAmministratore",codiKIdAmministratore, Hibernate.LONG);
				} else {
					q.setParameter("codiKIdAmministratore", null, Hibernate.LONG);
				}
				
				if(idIntestatario != null){
					q.setParameter("idIntestatario",idIntestatario, Hibernate.BIG_DECIMAL);
				}else{
					q.setParameter("idIntestatario", null, Hibernate.BIG_DECIMAL);
				}
				
				
				
				
				/************* FINE IMPUGNAZIONE **************************/

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				q.list();
				return atto.getId().getCodiKIdAtto();
			}
		});
	}

	/**
	 * metodo per la registrazione del ricorso in cassazione
	 * 
	 * @param idUfficio
	 * @param idProcedimento
	 * @param impugnazione
	 * @param atto
	 * @param codUtente
	 * @param tipoAtto
	 * @param categoriaAtto
	 * @param idStatoAtto
	 * @param idDestinatario
	 * @return
	 */
	public Long registraAttoRicorsoInCassazione(final long idUfficio, final long idProcedimento,final String listaIdSoggetti,final TRelSoggImp soggImpTmp, final TImpugnazione impugnazione, final TAtto atto,
			final String codUtente, final long tipoAtto, final long categoriaAtto, final long idStatoAtto, final long idDestinatario, final Long codiKIdAmministratore) {
		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_registraRicorsoInCassazione");
				q.setParameter("idUffOwn", idUfficio);
				q.setParameter("idProc", idProcedimento, Hibernate.LONG);
				if (atto.getDataRegistrazione() != null) {
					q.setParameter("idMittente", idUfficio, Hibernate.LONG);
					q.setParameter("idDestinatario", idDestinatario, Hibernate.LONG);

					q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				} else {
					q.setParameter("idMittente", null, Hibernate.LONG);
					q.setParameter("idDestinatario", null, Hibernate.LONG);
					q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				}
				if (atto.getDataEmissione() != null) {
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				} else {
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				}
				if (atto.getDataDeposito() != null) {
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				} else {
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				}
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				if (atto.getDataRicezione() != null) {
					q.setParameter("dataRicezioneDoc", atto.getDataRicezione(), Hibernate.DATE);
				} else {
					q.setParameter("dataRicezioneDoc", null, Hibernate.DATE);
				}
				q.setParameter("descUtente", codUtente);
				q.setParameter("idAtto", atto.getId().getCodiKIdAtto(), Hibernate.LONG);
				q.setParameter("tipoAtto", tipoAtto, Hibernate.LONG);
				q.setParameter("categoriaAtto", categoriaAtto, Hibernate.LONG);
				q.setParameter("idStatoAtto", idStatoAtto, Hibernate.LONG);

				/************* INIZIO IMPUGNAZIONE **************************/
				// DEPOSITO
				if (impugnazione.getTAnUfficio() != null && impugnazione.getTAnUfficio().getCodiKIdUfficio() != 0) {
					q.setParameter("codiFkIdUffDep", impugnazione.getTAnUfficio().getCodiKIdUfficio(), Hibernate.INTEGER);
				} else {
					q.setParameter("codiFkIdUffDep", null, Hibernate.INTEGER);
				}

				q.setParameter("flgUffDep", impugnazione.getFlgUffDep(), Hibernate.CHARACTER);
				if (impugnazione.getDataDepImp() != null) {
					q.setParameter("dataDepImp", impugnazione.getDataDepImp(), Hibernate.DATE);
				} else {
					q.setParameter("dataDepImp", null, Hibernate.DATE);
				}

				if (impugnazione.getOraDepImp() != null) {
					q.setParameter("oraDepImp", impugnazione.getOraDepImp(), Hibernate.STRING);
				} else {
					q.setParameter("oraDepImp", null, Hibernate.STRING);
				}

				if (impugnazione.getDataRegImp() != null) {
					q.setParameter("dataRegImp", impugnazione.getDataRegImp(), Hibernate.DATE);
				} else {
					q.setParameter("dataRegImp", null, Hibernate.DATE);
				}

				if (impugnazione.getOraRegImp() != null) {
					q.setParameter("oraRegImp", impugnazione.getOraRegImp(), Hibernate.STRING);
				} else {
					q.setParameter("oraRegImp", null, Hibernate.STRING);
				}

				// PERVENIMENTO
				if (impugnazione.getTTpModPerv() != null) {
					q.setParameter("codiKIdModPerv", impugnazione.getTTpModPerv().getCodiKIdModalita(), Hibernate.INTEGER);
				} else {
					q.setParameter("codiKIdModPerv", null, Hibernate.INTEGER);
				}

				if (impugnazione.getDataSpedizione() != null) {
					q.setParameter("dataSpedizione", impugnazione.getDataSpedizione(), Hibernate.DATE);
				} else {
					q.setParameter("dataSpedizione", null, Hibernate.DATE);
				}

				if (impugnazione.getDescProvPosta() != null) {
					q.setParameter("descProvPosta", impugnazione.getDescProvPosta(), Hibernate.STRING);
				} else {
					q.setParameter("descProvPosta", null, Hibernate.STRING);
				}

				if (impugnazione.getDataPervImp() != null) {
					q.setParameter("dataPervImp", impugnazione.getDataPervImp(), Hibernate.DATE);
				} else {
					q.setParameter("dataPervImp", null, Hibernate.DATE);
				}
				q.setParameter("oraPervImp", impugnazione.getOraPervImp(), Hibernate.STRING);

				// DEPOSITANTE
				q.setParameter("flaTipoParte", soggImpTmp.getFlgTipoParte(), Hibernate.CHARACTER);

				q.setParameter("descTipoProp", soggImpTmp.getDescTipoProp(), Hibernate.STRING);


				
				if(listaIdSoggetti != null){
					q.setParameter("listaIdSoggetti", listaIdSoggetti, Hibernate.STRING);
				} else {
					q.setParameter("listaIdSoggetti", null, Hibernate.STRING);
				}

				if (soggImpTmp.getTAvvocati() != null && soggImpTmp.getTAvvocati().getCodiKIdAvvocato() > 0) {
					q.setParameter("codiKIdAvvocato", soggImpTmp.getTAvvocati().getCodiKIdAvvocato(), Hibernate.LONG);
				} else {
					q.setParameter("codiKIdAvvocato", null, Hibernate.LONG);
				}

				// RICEVENTE
				q.setParameter("descCognFunz", impugnazione.getDescCognFunz(), Hibernate.STRING);
				q.setParameter("descNomeFunz", impugnazione.getDescNomeFunz(), Hibernate.STRING);

				// campo per altri istituti
				if (impugnazione.getTAnIstitutoDet() != null && impugnazione.getTAnIstitutoDet().getCodiKIdIstituto() > 0) {
					q.setParameter("codiFkIdIstDet", impugnazione.getTAnIstitutoDet().getCodiKIdIstituto(), Hibernate.INTEGER);
				} else {
					q.setParameter("codiFkIdIstDet", null, Hibernate.INTEGER);
				}

				// AGGIUNTE
				q.setParameter("descAltroMod", impugnazione.getDescAltroMod(), Hibernate.STRING);

				q.setParameter("descCognAvv", soggImpTmp.getDescCognAvv(), Hibernate.STRING);
				q.setParameter("descNomeAvv", soggImpTmp.getDescNomeAvv(), Hibernate.STRING);

				if(codiKIdAmministratore!=null) {
					q.setParameter("codiKIdAmministratore", codiKIdAmministratore, Hibernate.LONG);
				} else {
					q.setParameter("codiKIdAmministratore", null, Hibernate.LONG);
				}
				
				/************* FINE IMPUGNAZIONE **************************/

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				q.list();
				return atto.getId().getCodiKIdAtto();
			}
		});
	}

	
	public Character impostaRicorsoMisPatr(final long idProcedimento, final long idAtto, final long idUffOwner, final boolean checkedSoggValido,
			final long idSoggInt, final long idBene, final String idSoggTit, final int tipoBene, final int catBene, final int sottoCatBene,
			final String codiceUtente, final int catAtto) {
		return (Character) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_imposta_ricorso_mis_patr");

				if (idProcedimento > 0) {
					q.setParameter("VID_PROCEDIMENTO", idProcedimento, Hibernate.LONG);
				} else {
					q.setParameter("VID_PROCEDIMENTO", 0, Hibernate.LONG);
				}

				if (idAtto > 0) {
					q.setParameter("VID_ATTO", idAtto, Hibernate.LONG);
				} else {
					q.setParameter("VID_ATTO", 0, Hibernate.LONG);
				}

				if (idUffOwner > 0) {
					q.setParameter("VID_UFF_OWNER", idUffOwner, Hibernate.LONG);
				} else {
					q.setParameter("VID_UFF_OWNER", 0, Hibernate.LONG);
				}

				if (idBene > 0) {
					q.setParameter("VID_BENE", idBene, Hibernate.LONG);
				} else {
					q.setParameter("VID_BENE", null, Hibernate.LONG);
				}

				if (idSoggInt > 0) {
					q.setParameter("VID_SOGG_INT", idSoggInt, Hibernate.LONG);
				} else {
					q.setParameter("VID_SOGG_INT", null, Hibernate.LONG);
				}

				if (idSoggTit != null && !"".equals(idSoggTit)) {
					q.setParameter("VID_SOGG_TIT", idSoggTit, Hibernate.STRING);
				} else {
					q.setParameter("VID_SOGG_TIT", null, Hibernate.STRING);
				}

				if (tipoBene > 0) {
					q.setParameter("VID_TIPO_BENE", tipoBene, Hibernate.INTEGER);
				} else {
					q.setParameter("VID_TIPO_BENE", null, Hibernate.INTEGER);
				}

				if (catBene > 0) {
					q.setParameter("VID_CAT_BENE", catBene, Hibernate.INTEGER);
				} else {
					q.setParameter("VID_CAT_BENE", null, Hibernate.INTEGER);
				}

				if (sottoCatBene > 0) {
					q.setParameter("VID_SCTG_BENE", sottoCatBene, Hibernate.INTEGER);
				} else {
					q.setParameter("VID_SCTG_BENE", null, Hibernate.INTEGER);
				}

				if (checkedSoggValido) {
					q.setParameter("VFLG_VALIDO", 'R');
				} else {
					q.setParameter("VFLG_VALIDO", 'S', Hibernate.CHARACTER);
				}

				q.setParameter("IJ_UTENTE", codiceUtente, Hibernate.STRING);

				q.setParameter("VID_CAT_ATTO", catAtto, Hibernate.INTEGER);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				Character upd = null;

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List list = q.list();
				Iterator it = list.iterator();
				while (it.hasNext()) {
					Map<String, Object> row = (Map<String, Object>) it.next();
					upd = (Character) row.get("UPD");
				}
				return upd;

			}
		});
	}

	
	public Character impostaRicorsoCassMisPatr(final long idProcedimento, final long idAtto, final long idUffOwner, final boolean checkedSoggValido,
			final long idSoggInt, final long idBene, final String idSoggTit, final int tipoBene, final int catBene, final int sottoCatBene,
			final String codiceUtente, final int catAtto) {
		return (Character) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_imposta_ricorso_cass_mis_patr");

				if (idProcedimento > 0) {
					q.setParameter("ID_PROCEDIMENTO", idProcedimento, Hibernate.LONG);
				} else {
					q.setParameter("ID_PROCEDIMENTO", 0, Hibernate.LONG);
				}

				if (idAtto > 0) {
					q.setParameter("ID_ATTO", idAtto, Hibernate.LONG);
				} else {
					q.setParameter("ID_ATTO", 0, Hibernate.LONG);
				}

				if (idUffOwner > 0) {
					q.setParameter("ID_UFF_OWNER", idUffOwner, Hibernate.LONG);
				} else {
					q.setParameter("ID_UFF_OWNER", 0, Hibernate.LONG);
				}

				if (idBene > 0) {
					q.setParameter("ID_BENE", idBene, Hibernate.LONG);
				} else {
					q.setParameter("ID_BENE", null, Hibernate.LONG);
				}

				if (idSoggInt > 0) {
					q.setParameter("ID_SOGG_INT", idSoggInt, Hibernate.LONG);
				} else {
					q.setParameter("ID_SOGG_INT", null, Hibernate.LONG);
				}

				if (idSoggTit != null && !"".equals(idSoggTit)) {
					q.setParameter("ID_SOGG_TIT", idSoggTit, Hibernate.STRING);
				} else {
					q.setParameter("ID_SOGG_TIT", null, Hibernate.STRING);
				}

				if (tipoBene > 0) {
					q.setParameter("ID_TIPO_BENE", tipoBene, Hibernate.INTEGER);
				} else {
					q.setParameter("ID_TIPO_BENE", null, Hibernate.INTEGER);
				}

				if (catBene > 0) {
					q.setParameter("ID_CAT_BENE", catBene, Hibernate.INTEGER);
				} else {
					q.setParameter("ID_CAT_BENE", null, Hibernate.INTEGER);
				}

				if (sottoCatBene > 0) {
					q.setParameter("ID_SCTG_BENE", sottoCatBene, Hibernate.INTEGER);
				} else {
					q.setParameter("ID_SCTG_BENE", null, Hibernate.INTEGER);
				}

				if (checkedSoggValido) {
					q.setParameter("FLG_VALIDO", 'R');
				} else {
					q.setParameter("FLG_VALIDO", 'S', Hibernate.CHARACTER);
				}

				q.setParameter("IJ_UTENTE", codiceUtente, Hibernate.STRING);

				q.setParameter("ID_CAT_ATTO", catAtto, Hibernate.INTEGER);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				Character upd = null;

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List list = q.list();
				Iterator it = list.iterator();
				while (it.hasNext()) {
					Map<String, Object> row = (Map<String, Object>) it.next();
					upd = (Character) row.get("UPD");
				}
				return upd;

			}
		});
	}

	
	public Character impostaRicorsoCassMisPers(final long idProcedimento, final long idAtto, final long idUffOwner, final boolean checkedSoggValido,
			final long idMisuraPers, final long idSoggInt, final String codiceUtente, final int catAtto) {
		return (Character) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_imposta_ricorso_mis_cass_pers");

				if (idProcedimento > 0) {
					q.setParameter("ID_PROCEDIMENTO", idProcedimento, Hibernate.LONG);
				} else {
					q.setParameter("ID_PROCEDIMENTO", 0, Hibernate.LONG);
				}
				if (idAtto > 0) {
					q.setParameter("ID_ATTO", idAtto, Hibernate.LONG);
				} else {
					q.setParameter("ID_ATTO", 0, Hibernate.LONG);
				}

				if (idUffOwner > 0) {
					q.setParameter("ID_UFF_OWNER", idUffOwner, Hibernate.LONG);
				} else {
					q.setParameter("ID_UFF_OWNER", 0, Hibernate.LONG);
				}

				if (idSoggInt > 0) {
					q.setParameter("ID_SOGG_INT", idSoggInt, Hibernate.LONG);
				} else {
					q.setParameter("ID_SOGG_INT", null, Hibernate.LONG);
				}

				if (idMisuraPers > 0) {
					q.setParameter("ID_MISURA_PERS", idMisuraPers, Hibernate.LONG);
				} else {
					q.setParameter("ID_MISURA_PERS", null, Hibernate.LONG);
				}
				if (checkedSoggValido) {
					q.setParameter("FLG_VALIDO", 'R');
				} else {
					q.setParameter("FLG_VALIDO", 'S', Hibernate.CHARACTER);
				}
				q.setParameter("IJ_UTENTE", codiceUtente, Hibernate.STRING);

				q.setParameter("ID_CAT_ATTO", catAtto, Hibernate.INTEGER);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				Character upd = null;

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List list = q.list();
				Iterator it = list.iterator();
				while (it.hasNext()) {
					Map<String, Object> row = (Map<String, Object>) it.next();
					upd = (Character) row.get("UPD");
				}
				return upd;

			}
		});
	}

	
	public Character impostaRicorsoCassSoggetto(final long idProcedimento, final long idAtto, final long idUffOwner, final boolean checkedSoggValido,
			final long idSoggInt, final String idsSoggTit, final String idsMisure, final int tipoBene, final int catBene, final int sottoCatBene,
			final String codiceUtente, final int catAtto) {
		return (Character) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_imposta_ricorso_cass_soggetto");

				if (idProcedimento > 0) {
					q.setParameter("ID_PROCEDIMENTO", idProcedimento, Hibernate.LONG);
				} else {
					q.setParameter("ID_PROCEDIMENTO", 0, Hibernate.LONG);
				}
				if (idProcedimento > 0) {
					q.setParameter("ID_ATTO", idAtto, Hibernate.LONG);
				} else {
					q.setParameter("ID_ATTO", 0, Hibernate.LONG);
				}

				if (idUffOwner > 0) {
					q.setParameter("ID_UFF_OWNER", idUffOwner, Hibernate.LONG);
				} else {
					q.setParameter("ID_UFF_OWNER", 0, Hibernate.LONG);
				}

				if (idSoggInt > 0) {
					q.setParameter("ID_SOGG_INT", idSoggInt, Hibernate.LONG);
				} else {
					q.setParameter("ID_SOGG_INT", null, Hibernate.LONG);
				}

				q.setParameter("ID_BENE", null, Hibernate.STRING);

				if (idsSoggTit != null && !"".equals(idsSoggTit)) {
					q.setParameter("ID_SOGG_TIT", idsSoggTit, Hibernate.STRING);
				} else {
					q.setParameter("ID_SOGG_TIT", null, Hibernate.STRING);
				}

				if (idsMisure != null && !"".equals(idsMisure)) {
					q.setParameter("IDS_MIS", idsMisure, Hibernate.STRING);
				} else {
					q.setParameter("IDS_MIS", null, Hibernate.STRING);
				}

				if (tipoBene > 0) {
					q.setParameter("ID_TIPO_BENE", tipoBene, Hibernate.INTEGER);
				} else {
					q.setParameter("ID_TIPO_BENE", null, Hibernate.INTEGER);
				}

				if (catBene > 0) {
					q.setParameter("ID_CAT_BENE", catBene, Hibernate.INTEGER);
				} else {
					q.setParameter("ID_CAT_BENE", null, Hibernate.INTEGER);
				}

				if (sottoCatBene > 0) {
					q.setParameter("ID_SCTG_BENE", sottoCatBene, Hibernate.INTEGER);
				} else {
					q.setParameter("ID_SCTG_BENE", null, Hibernate.INTEGER);
				}

				if (checkedSoggValido) {
					q.setParameter("FLG_VALIDO", 'R');
				} else {
					q.setParameter("FLG_VALIDO", 'S', Hibernate.CHARACTER);
				}
				q.setParameter("IJ_UTENTE", codiceUtente, Hibernate.STRING);

				q.setParameter("ID_CAT_ATTO", catAtto, Hibernate.INTEGER);

				Character upd = null;

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List list = q.list();
				Iterator it = list.iterator();
				while (it.hasNext()) {
					Map<String, Object> row = (Map<String, Object>) it.next();
					upd = (Character) row.get("UPD");
				}
				return upd;

			}
		});
	}

	
	public Character impostaRicorsoSoggetto(final long idProcedimento, final long idAtto, final long idUffOwner, final boolean checkedSoggValido,
			final long idSoggInt, final String idsSoggTit, final String idsMisure, final int tipoBene, final int catBene, final int sottoCatBene,
			final String codiceUtente, final int catAtto) {
		return (Character) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_imposta_ricorso_soggetto");

				if (idProcedimento > 0) {
					q.setParameter("VID_PROCEDIMENTO", idProcedimento, Hibernate.LONG);
				} else {
					q.setParameter("VID_PROCEDIMENTO", 0, Hibernate.LONG);
				}
				if (idProcedimento > 0) {
					q.setParameter("VID_ATTO", idAtto, Hibernate.LONG);
				} else {
					q.setParameter("VID_ATTO", 0, Hibernate.LONG);
				}

				if (idUffOwner > 0) {
					q.setParameter("VID_UFF_OWNER", idUffOwner, Hibernate.LONG);
				} else {
					q.setParameter("VID_UFF_OWNER", 0, Hibernate.LONG);
				}

				if (idSoggInt > 0) {
					q.setParameter("VID_SOGG_INT", idSoggInt, Hibernate.LONG);
				} else {
					q.setParameter("VID_SOGG_INT", null, Hibernate.LONG);
				}

				// /
				q.setParameter("VID_BENE", null, Hibernate.STRING);

				if (idsSoggTit != null && !"".equals(idsSoggTit)) {
					q.setParameter("VID_SOGG_TIT", idsSoggTit, Hibernate.STRING);
				} else {
					q.setParameter("VID_SOGG_TIT", null, Hibernate.STRING);
				}

				if (idsMisure != null && !"".equals(idsMisure)) {
					q.setParameter("VIDS_MIS", idsMisure, Hibernate.STRING);
				} else {
					q.setParameter("VIDS_MIS", null, Hibernate.STRING);
				}

				if (tipoBene > 0) {
					q.setParameter("VID_TIPO_BENE", tipoBene, Hibernate.INTEGER);
				} else {
					q.setParameter("VID_TIPO_BENE", null, Hibernate.INTEGER);
				}

				if (catBene > 0) {
					q.setParameter("VID_CAT_BENE", catBene, Hibernate.INTEGER);
				} else {
					q.setParameter("VID_CAT_BENE", null, Hibernate.INTEGER);
				}

				if (sottoCatBene > 0) {
					q.setParameter("VID_SCTG_BENE", sottoCatBene, Hibernate.INTEGER);
				} else {
					q.setParameter("VID_SCTG_BENE", null, Hibernate.INTEGER);
				}
				// /
				if (checkedSoggValido) {
					q.setParameter("VFLG_VALIDO", 'R');
				} else {
					q.setParameter("VFLG_VALIDO", 'S', Hibernate.CHARACTER);
				}
				q.setParameter("IJ_UTENTE", codiceUtente, Hibernate.STRING);

				q.setParameter("VID_CAT_ATTO", catAtto, Hibernate.INTEGER);

				Character upd = null;

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List list = q.list();
				Iterator it = list.iterator();
				while (it.hasNext()) {
					Map<String, Object> row = (Map<String, Object>) it.next();
					upd = (Character) row.get("UPD");
				}
				return upd;

			}
		});
	}

	public List<TAvvocati> getAvvocatiDifensori(long idProc, long idUfficio, long idAvvocato) {

		String qry = " select distinct avvocati.* " + " from  T_AVVOCATI avvocati " + " inner join T_REL_PROC_SOGINT_AVV RELPROCSOGG "
				//+ " on RELPROCSOGG.CODI_K_ID_UFF_OWNER = AVVOCATI.CODI_K_ID_UFF_OWNER "
				//+ " and  RELPROCSOGG.CODI_K_ID_AVVOCATO=AVVOCATI.CODI_K_ID_AVVOCATO  " + " where RELPROCSOGG.CODI_K_ID_PROC = :idProc "
				+ " ON  RELPROCSOGG.CODI_K_ID_AVVOCATO=AVVOCATI.CODI_K_ID_AVVOCATO  " + " where RELPROCSOGG.CODI_K_ID_PROC = :idProc "
				+ " and RELPROCSOGG.CODI_K_ID_UFF_OWNER =:idUfficio ";
		if (idAvvocato > 0)
			qry += " and AVVOCATI.CODI_K_ID_AVVOCATO =:idAvvocato ";

		SQLQuery query = getSession().createSQLQuery(qry);
		query.addEntity(TAvvocati.class);
		query.setParameter("idProc", idProc, Hibernate.LONG);
		query.setParameter("idUfficio", idUfficio, Hibernate.LONG);
		if (idAvvocato > 0)
			query.setParameter("idAvvocato", idAvvocato, Hibernate.LONG);

		return query.list();

	}
	
	public List<TAvvocati> getAvvocatiDifensoriIntestatari(long idProc, long idUfficio, long idAvvocato) {

		String qry = " select distinct avvocati.* " + " from  T_AVVOCATI avvocati " + " inner join T_REL_PROC_INTESTATARI_AVV RELPROCSOGG "
				+ " ON  RELPROCSOGG.CODI_K_ID_AVVOCATO=AVVOCATI.CODI_K_ID_AVVOCATO  " + " where RELPROCSOGG.CODI_K_ID_PROC = :idProc "
				+ " and RELPROCSOGG.CODI_K_ID_UFF_OWNER =:idUfficio ";
		if (idAvvocato > 0)
			qry += " and AVVOCATI.CODI_K_ID_AVVOCATO =:idAvvocato ";

		SQLQuery query = getSession().createSQLQuery(qry);
		query.addEntity(TAvvocati.class);
		query.setParameter("idProc", idProc, Hibernate.LONG);
		query.setParameter("idUfficio", idUfficio, Hibernate.LONG);
		if (idAvvocato > 0)
			query.setParameter("idAvvocato", idAvvocato, Hibernate.LONG);

		return query.list();

	}	

	public List<SogIntDettaglioProcedimentoDto> getSoggettiDifensori(final long idUfficio, final long idProcedimento, final long idAvvocato) {

		return (List<SogIntDettaglioProcedimentoDto>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_elencoSoggettiDifensori");
				q.setParameter("idUffOwn", idUfficio);
				q.setParameter("idProcedimento", idProcedimento);
				q.setParameter("idAvvocato", idAvvocato);
				q.setResultTransformer(Transformers.aliasToBean(SogIntDettaglioProcedimentoDto.class));
				return q.list();
			}
		});
	}
	

	

	public List<SogIntDettaglioProcedimentoDto> getSoggettiDifensoriProcura(final long idUfficio, final long idProcedimento) {

		return (List<SogIntDettaglioProcedimentoDto>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_elencoSoggettiDifensoriProcura");
				q.setParameter("idUffOwn", idUfficio);
				q.setParameter("idProcedimento", idProcedimento);
				q.setResultTransformer(Transformers.aliasToBean(SogIntDettaglioProcedimentoDto.class));
				return q.list();
			}
		});
	}

	
	public Character impostaRicorsoMisPers(final long idProcedimento, final long idAtto, final long idUffOwner, final boolean checkedSoggValido,
			final long idMisuraPers, final long idSoggInt, final String codiceUtente, final int catAtto) {
		return (Character) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_imposta_ricorso_mis_pers");

				if (idProcedimento > 0) {
					q.setParameter("VID_PROCEDIMENTO", idProcedimento, Hibernate.LONG);
				} else {
					q.setParameter("VID_PROCEDIMENTO", 0, Hibernate.LONG);
				}
				if (idAtto > 0) {
					q.setParameter("VID_ATTO", idAtto, Hibernate.LONG);
				} else {
					q.setParameter("VID_ATTO", 0, Hibernate.LONG);
				}

				if (idUffOwner > 0) {
					q.setParameter("VID_UFF_OWNER", idUffOwner, Hibernate.LONG);
				} else {
					q.setParameter("VID_UFF_OWNER", 0, Hibernate.LONG);
				}

				if (idSoggInt > 0) {
					q.setParameter("VID_SOGG_INT", idSoggInt, Hibernate.LONG);
				} else {
					q.setParameter("VID_SOGG_INT", null, Hibernate.LONG);
				}

				if (idMisuraPers > 0) {
					q.setParameter("VID_MISURA_PERS", idMisuraPers, Hibernate.LONG);
				} else {
					q.setParameter("VID_MISURA_PERS", null, Hibernate.LONG);
				}
				if (checkedSoggValido) {
					q.setParameter("VFLG_VALIDO", 'R');
				} else {
					q.setParameter("VFLG_VALIDO", 'S', Hibernate.CHARACTER);
				}
				q.setParameter("IJ_UTENTE", codiceUtente, Hibernate.STRING);

				q.setParameter("VID_CAT_ATTO", catAtto, Hibernate.INTEGER);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				Character upd = null;

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List list = q.list();
				Iterator it = list.iterator();
				while (it.hasNext()) {
					Map<String, Object> row = (Map<String, Object>) it.next();
					upd = (Character) row.get("UPD");
				}
				return upd;

			}
		});
	}

	public TImpugnazione getImpugnazioneByIdAttoUfficioOwner(final long idAtto, final int idUfficio) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TImpugnazione.class);
		criteria.add(Restrictions.eq("id.codiKIdImpugnaz", idAtto));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficio));
		TImpugnazione risultato = DataAccessUtils.requiredUniqueResult((List<TImpugnazione>)getHibernateTemplate().findByCriteria(criteria));
		return risultato;
	}

	/**
	 * ricercaAttiRicorso
	 * 
	 * @param idProcedimento
	 * @param IdUffOwner
	 * @return List<TAtto>
	 */
	public List<TAtto> ricercaAttiRicorso(final long idProcedimento, final long idUffOwner) {

		return (List<TAtto>)getHibernateTemplate().execute(new HibernateCallback() {

			public List<TAtto> doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_ricercaAttiRicorso");
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setParameter("idUffOwner", idUffOwner, Hibernate.LONG);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				return q.list();
			}
		});
	}

	/**
	 * inviaRicorsoInAppello
	 * 
	 * @param iduffown
	 * @param idproc
	 * @param iddestinatario
	 * @param descutente
	 */
	public void inviaRicorsoInAppello(final long iduffown, final long idproc, final long iddestinatario,final Date datatrasmCda, final String descutente) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_inviaRicorsoInAppello");
				q.setParameter("iduffown", iduffown);
				q.setParameter("idproc", idproc);
				q.setParameter("iddestinatario", iddestinatario);
				q.setParameter("datatrasmCda", datatrasmCda);
				q.setParameter("descutente", descutente);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});

	}

	/**
	 * ricercaRicorsoInLavorazione
	 * 
	 * @param idProcedimento
	 * @param idUffOwner
	 * @return
	 */
	public List<TAtto> ricercaRicorsoInLavorazione(final long idProcedimento, final long idUffOwner) {

		return (List<TAtto>)getHibernateTemplate().execute(new HibernateCallback() {

			public List<TAtto> doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_ricercaRicorsiInLavorazione");
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setParameter("iduffown", idUffOwner, Hibernate.LONG);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				return q.list();
			}
		});
	}

	public KeyMessageDto controllaMisureDefinitivitaIGrado(final long idProc, final long idAtto, final long idUfficio, final String descUtente,
			final String funzione, final String metodo) {
		return (KeyMessageDto) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_controllaMisureDefinitivitaIGrado");
				q.setParameter("I_ID_PROCEDIMENTO", idProc, Hibernate.LONG);
				q.setParameter("I_ID_ATTO", idAtto, Hibernate.LONG);
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
	
	
	public KeyMessageDto controllaImpugnazioneCauArt67(final long idProc, final long idAtto, final long idUfficio, final String descUtente, final String metodo) {
		return (KeyMessageDto) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_controllaImpugnazioneArt67Cauz");
				q.setParameter("I_ID_PROCEDIMENTO", idProc, Hibernate.LONG);
				q.setParameter("I_ID_ATTO", idAtto, Hibernate.LONG);
				q.setParameter("I_ID_UFFICIO", idUfficio, Hibernate.LONG);
				q.setParameter("I_ID_UTENTE", descUtente, Hibernate.STRING);
				q.setParameter("I_NOME_METODO", metodo, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return new KeyMessageDto(l);
			}
		});

	}	

	public KeyMessageDto controllaDefinitivitaIIGradoSuMisure(final long idProc, final long idAtto, final long idUfficio, final String descUtente,
			final String funzione, final String metodo) {
		return (KeyMessageDto) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_controllaDefinitivitaIIGradoSuMisure");
				q.setParameter("I_ID_PROCEDIMENTO", idProc, Hibernate.LONG);
				q.setParameter("I_ID_ATTO", idAtto, Hibernate.LONG);
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

	public KeyMessageDto controllaMisureDefinitivitaIIGrado(final long idProc, final long idAtto, final long idUfficio, final String descUtente,
			final String funzione, final String metodo) {
		return (KeyMessageDto) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_controllaMisureDefinitivitaIIGrado");
				q.setParameter("I_ID_PROCEDIMENTO", idProc, Hibernate.LONG);
				q.setParameter("I_ID_ATTO", idAtto, Hibernate.LONG);
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

	public long inserisciParere(final int idUffOwn, final long idProcedimento, final String codiceUtente, final TAtto atto, final long idStatoAtto,
			final long idAttoOrigine, final TAttoGenerico attoGenerico, final String nomeMetodo) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_inserisciParere");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("descUtente", codiceUtente);
				q.setParameter("idProcedimento", idProcedimento);
				if ((atto.getTTpTipoAtto() != null) && (atto.getTTpTipoAtto().getCodiKIdTipoAtto() > 0)) {
					q.setParameter("idTipoAtto", atto.getTTpTipoAtto().getCodiKIdTipoAtto());
				} else {
					q.setParameter("idTipoAtto", null, Hibernate.LONG);
				}
				if (idAttoOrigine > 0) {
					q.setParameter("idAttoOrigine", idAttoOrigine, Hibernate.LONG);
				} else {
					q.setParameter("idAttoOrigine", null, Hibernate.LONG);
				}
				if (atto.getTTpCatAtto().getCodiKIdCatAtto() > 0) {
					q.setParameter("idCatAtto", atto.getTTpCatAtto().getCodiKIdCatAtto());
				} else {
					q.setParameter("idCatAtto", null, Hibernate.LONG);
				}
				if (idStatoAtto > 0) {
					q.setParameter("idStatoAtto", idStatoAtto);
				} else {
					q.setParameter("idStatoAtto", null, Hibernate.LONG);
				}
				if (atto.getDataEmissione() != null) {
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				} else {
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				}
				if (atto.getDataDeposito() != null) {
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				} else {
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				}
				if (atto.getDataRegistrazione() != null) {
					q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				} else {
					q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				}
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long id = (Long) first.get("IDATTO");
				return id;
			}
		});
	}

	public Long aggiornaInviaParere(final boolean isRichiesta, final long idUfficio, final long idProcedimento, final TAtto atto,
			final String codUtente, final long tipoAtto, final long categoriaAtto, final long idStatoAtto, final long idDestinatario,
			final String nomeMetodo) {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_aggiornaInviaParere");

				int isRichiestaN = 0;
				if (isRichiesta)
					isRichiestaN = 1;
				q.setParameter("isRichiesta", isRichiestaN, Hibernate.INTEGER);
				q.setParameter("idUffOwn", idUfficio);
				q.setParameter("idProc", idProcedimento, Hibernate.LONG);
				if (atto.getDataRegistrazione() != null) {
					q.setParameter("idMittente", idUfficio, Hibernate.LONG);
					q.setParameter("idDestinatario", idDestinatario, Hibernate.LONG);
					q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				} else {
					q.setParameter("idMittente", null, Hibernate.LONG);
					q.setParameter("idDestinatario", null, Hibernate.LONG);
					q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				}
				if (atto.getDataEmissione() != null) {
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				} else {
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				}
				if (atto.getDataDeposito() != null) {
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				} else {
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				}
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				if (atto.getDataRicezione() != null) {
					q.setParameter("dataRicezioneDoc", atto.getDataRicezione(), Hibernate.DATE);
				} else {
					q.setParameter("dataRicezioneDoc", null, Hibernate.DATE);
				}
				q.setParameter("descUtente", codUtente);
				q.setParameter("idAtto", atto.getId().getCodiKIdAtto(), Hibernate.LONG);
				q.setParameter("tipoAtto", tipoAtto, Hibernate.LONG);
				q.setParameter("categoriaAtto", categoriaAtto, Hibernate.LONG);
				q.setParameter("idStatoAtto", idStatoAtto, Hibernate.LONG);
				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				q.list();
				return atto.getId().getCodiKIdAtto();
			}
		});
	}

	public KeyMessageDto controllaRegImpugnazione(final long idProc, final long idAtto, final long idUfficio, final String descUtente,
			final String metodo) {
		return (KeyMessageDto) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_controllaRegImpugnazione");
				q.setParameter("I_ID_PROCEDIMENTO", idProc, Hibernate.LONG);
				q.setParameter("I_ID_ATTO", idAtto, Hibernate.LONG);
				q.setParameter("I_ID_UFFICIO", idUfficio, Hibernate.LONG);
				q.setParameter("I_ID_UTENTE", descUtente, Hibernate.STRING);
				q.setParameter("I_NOME_METODO", metodo, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return new KeyMessageDto(l);
			}
		});

	}

	/**
	 * controllaInsertDefinitivita
	 * 
	 * @param idProc
	 * @param idMisPers
	 * @param idBene
	 * @param idMisTipoInt
	 * @param idMisIdSoggInt
	 * @param idMisIdSoggTit
	 * @param idUfficio
	 * @param descUtente
	 * @param funzione
	 * @param metodo
	 * @return KeyMessageDto
	 */
	public KeyMessageDto controllaInsertDefinitivita(final long idProc, final long idMisPers, final long idBene, final long idInteresse,
			final long idSoggetto, final Integer idUfficio, final String descUtente, final String funzione, final String metodo) {
		return (KeyMessageDto) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_controllaInsertDefinitivita");
				q.setParameter("I_ID_PROCEDIMENTO", idProc, Hibernate.LONG);
				q.setParameter("I_ID_MISURA_PERS", idMisPers, Hibernate.LONG);
				q.setParameter("ID_BENE", idBene, Hibernate.LONG);
				q.setParameter("ID_MIS_TIPO_INT", idInteresse, Hibernate.LONG);
				q.setParameter("ID_MIS_ID_SOGG", idSoggetto, Hibernate.LONG);
				q.setParameter("I_ID_UFFICIO", idUfficio, Hibernate.INTEGER);
				q.setParameter("I_ID_UTENTE", descUtente, Hibernate.STRING);
				q.setParameter("I_TIPO_FUNZIONE", funzione, Hibernate.STRING);
				q.setParameter("I_NOME_METODO", metodo, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return new KeyMessageDto(l);
			}
		});

	}

	/**
	 * controllaUpdEsitoMisPers
	 * 
	 * @param idProc
	 * @param idAtto
	 * @param idEsito
	 * @param cauzione
	 * @param art67
	 * @param idUfficio
	 * @param descUtente
	 * @param funzione
	 * @param metodo
	 * @return KeyMessageDto
	 */

	public KeyMessageDto controllaUpdEsitoMisPers(final long idProc, final long idAtto,final long idSoggetto, final long idEsito, final String cauzione,
			final String art67, final Integer idUfficio, final String descUtente, final String funzione, final String metodo) {
		return (KeyMessageDto) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_controllaUpdEsitoMisPers");
				q.setParameter("I_ID_PROCEDIMENTO", idProc, Hibernate.LONG);
				q.setParameter("I_ID_ATTO", idAtto, Hibernate.LONG);
				q.setParameter("I_ID_SOGGETTO", idSoggetto, Hibernate.LONG);
				q.setParameter("I_ID_ESITO", idEsito, Hibernate.LONG);
				q.setParameter("CAUZIONE", cauzione, Hibernate.STRING);
				q.setParameter("ART67", art67, Hibernate.STRING);
				q.setParameter("I_TIPO_FUNZIONE", funzione, Hibernate.STRING);
				q.setParameter("I_ID_UFFICIO", idUfficio, Hibernate.INTEGER);
				q.setParameter("I_ID_UTENTE", descUtente, Hibernate.STRING);
				q.setParameter("I_NOME_METODO", metodo, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return new KeyMessageDto(l);
			}
		});

	}

	
	
	
	
	public long inserisciDecretoIncompetenza(final int idUffOwn, final long idProcedimento, final String codiceUtente, final TAtto atto,
			final long idStatoAtto, final String nomeMetodo) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_inserisciDecretoIncompetenza");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("descUtente", codiceUtente);
				q.setParameter("idProcedimento", idProcedimento);
				if ((atto.getTTpTipoAtto() != null) && (atto.getTTpTipoAtto().getCodiKIdTipoAtto() > 0)) {
					q.setParameter("idTipoAtto", atto.getTTpTipoAtto().getCodiKIdTipoAtto());
				} else {
					q.setParameter("idTipoAtto", null, Hibernate.LONG);
				}
				if (atto.getTTpCatAtto().getCodiKIdCatAtto() > 0) {
					q.setParameter("idCatAtto", atto.getTTpCatAtto().getCodiKIdCatAtto());
				} else {
					q.setParameter("idCatAtto", null, Hibernate.LONG);
				}
				if (idStatoAtto > 0) {
					q.setParameter("idStatoAtto", idStatoAtto);
				} else {
					q.setParameter("idStatoAtto", null, Hibernate.LONG);
				}
				q.setParameter("nomeMetodo", nomeMetodo);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long id = (Long) first.get("IDATTO");
				return id;
			}
		});
	}

	public long inserisciDecretoOrdinanzaRestituzioneBeni(final int idUffOwn, final long idProcedimento, final String codiceUtente, final TAtto atto,
			final long idStatoAtto, final String nomeMetodo) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_inserisciDecretoOrdRestBeni");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("descUtente", codiceUtente);
				q.setParameter("idProcedimento", idProcedimento);
				if ((atto.getTTpTipoAtto() != null) && (atto.getTTpTipoAtto().getCodiKIdTipoAtto() > 0)) {
					q.setParameter("idTipoAtto", atto.getTTpTipoAtto().getCodiKIdTipoAtto());
				} else {
					q.setParameter("idTipoAtto", null, Hibernate.LONG);
				}
				if (atto.getTTpCatAtto().getCodiKIdCatAtto() > 0) {
					q.setParameter("idCatAtto", atto.getTTpCatAtto().getCodiKIdCatAtto());
				} else {
					q.setParameter("idCatAtto", null, Hibernate.LONG);
				}
				if (idStatoAtto > 0) {
					q.setParameter("idStatoAtto", idStatoAtto);
				} else {
					q.setParameter("idStatoAtto", null, Hibernate.LONG);
				}
				if (atto.getDataEmissione() != null) {
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				} else {
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				}
				if (atto.getDataDeposito() != null) {
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				} else {
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				}
				if (atto.getDataRegistrazione() != null) {
					q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				} else {
					q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				}
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
				
				
				
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long id = (Long) first.get("IDATTO");
				return id;
			}
		});
	}

	public Long inviaAttoDecretoRestBeni(final long idUfficio, final long idProcedimento, final TAtto atto, final String codUtente,
			final long tipoAtto, final long categoriaAtto, final long idStatoAtto, final long idDestinatario, final String nomeMetodo) {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_inviaDecretoRestituzioneBeni");
				q.setParameter("idUffOwn", idUfficio);
				q.setParameter("idProc", idProcedimento, Hibernate.LONG);
				if (atto.getDataRegistrazione() != null) {
					q.setParameter("idMittente", idUfficio, Hibernate.LONG);
					q.setParameter("idDestinatario", idDestinatario, Hibernate.LONG);
					q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				} else {
					q.setParameter("idMittente", null, Hibernate.LONG);
					q.setParameter("idDestinatario", null, Hibernate.LONG);
					q.setParameter("idUltDestinatario", null, Hibernate.LONG);
					q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				}
				if (atto.getDataEmissione() != null) {
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				} else {
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				}
				if (atto.getDataDeposito() != null) {
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				} else {
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				}
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				if (atto.getDataRicezione() != null) {
					q.setParameter("dataRicezioneDoc", atto.getDataRicezione(), Hibernate.DATE);
				} else {
					q.setParameter("dataRicezioneDoc", null, Hibernate.DATE);
				}
				q.setParameter("descUtente", codUtente);
				q.setParameter("idAtto", atto.getId().getCodiKIdAtto(), Hibernate.LONG);
				q.setParameter("tipoAtto", tipoAtto, Hibernate.LONG);
				q.setParameter("categoriaAtto", categoriaAtto, Hibernate.LONG);
				q.setParameter("idStatoAtto", idStatoAtto, Hibernate.LONG);
				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
				
				//solo per il decreto di restituzione dei beni
				if(atto.getDescNote() != null){
					q.setParameter("noteRestituzione", atto.getDescNote(), Hibernate.STRING);
				} else {
					q.setParameter("noteRestituzione", null, Hibernate.STRING);
				}	
				
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				q.list();
				return atto.getId().getCodiKIdAtto();
			}
		});
	}

	public void revocaDefinitivitaMisPersAttoRegistratoSalva(final long idProcedimento, final Integer idUfficioUtente, final long idAtto,
			final long idMisura, final String codUtente, final String tipoFunzione, final String nomeMetodo) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_revocaDefMisPersSalva");
				q.setParameter("idProcedimento", idProcedimento);
				q.setParameter("idAtto", idAtto);
				q.setParameter("idMisura", idMisura);
				q.setParameter("idUfficio", idUfficioUtente.intValue());
				q.setParameter("tipoFunzione", tipoFunzione);
				q.setParameter("idUtente", codUtente);
				q.setParameter("nomeMetodo", nomeMetodo);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});
	}

	public void revocaDefinitivitaMisPatrAttoRegistratoSalva(final Long idBene, final long idSoggetto, final long idInteresse,
			final long idProcedimento, final Integer idUfficioUtente, final long idAtto, final String codUtente, final String tipoFunzione,
			final String nomeMetodo) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_revocaDefMisPatrSalva");
				q.setParameter("idProcedimento", idProcedimento);
				q.setParameter("idAtto", idAtto);
				q.setParameter("idBene", idBene);
				q.setParameter("idSoggetto", idSoggetto);
				q.setParameter("idTipoInt", idInteresse);
				q.setParameter("idUfficio", idUfficioUtente.intValue());
				q.setParameter("tipoFunzione", tipoFunzione);
				q.setParameter("idUtente", codUtente);
				q.setParameter("nomeMetodo", nomeMetodo);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});
	}

	public void revocaDefinitivitaInvio(final long idProcedimento, final Integer idUfficioUtente, final long idAtto, final String codUtente,
			final String tipoFunzione, final String nomeMetodo) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_revocaDefinitivitaInvio");
				q.setParameter("idProcedimento", idProcedimento);
				q.setParameter("idAtto", idAtto);
				q.setParameter("idUfficio", idUfficioUtente.intValue());
				q.setParameter("tipoFunzione", tipoFunzione);
				q.setParameter("idUtente", codUtente);
				q.setParameter("nomeMetodo", nomeMetodo);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});
	}

	public KeyMessageDto controllaRegistrazioneDecisorio(final long idProc, final long idAtto, final long idUfficio, final String descUtente,
			final String funzione, final String metodo) {
		return (KeyMessageDto) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_controllaRegistrazioneDecisorio");
				q.setParameter("I_ID_PROCEDIMENTO", idProc, Hibernate.LONG);
				q.setParameter("I_ID_ATTO", idAtto, Hibernate.LONG);
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

	public List<AttoDocumentaleDto> getDocumentiJobDocumentale() {
		return (List<AttoDocumentaleDto>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_getDocumentiJobDocumentale");
				q.setResultTransformer(Transformers.aliasToBean(AttoDocumentaleDto.class));
				List<AttoDocumentaleDto> l = q.list();
				// q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				// List l = q.list();
				if ((l != null) && (l.size() > 0)) {
					return l;
				}
				return null;
			}
		});
	}

	/*
	 * Estrae la lista delle misure per la redazione della scheda del casellario (non-Javadoc)
	 * 
	 * @see it.giustizia.smp.dao.AttoDao#getListaMisurePerRedazioneScheda(long, int)
	 */
	public List<RedazioneSchedaDto> getListaMisurePerRedazioneScheda(final long idProcedimento, final int idUffOwn,final String tipo) {
		return (List<RedazioneSchedaDto>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = null;
				if ("FC".equals(tipo)) {
					q = session.getNamedQuery("PG_ATTO_listaRedazioneSchedaFC");
				} else	if ("FCEM".equals(tipo)) {
					q = session.getNamedQuery("PG_ATTO_listaRedazioneSchedaFCEM");
				} else {
					q = session.getNamedQuery("PG_ATTO_listaRedazioneScheda");
				}
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setParameter("idUfficio", idUffOwn, Hibernate.INTEGER);
				q.setResultTransformer(Transformers.aliasToBean(RedazioneSchedaDto.class));
				List<RedazioneSchedaDto> l = q.list();
				if ((l != null) && (l.size() > 0)) {
					return l;
				}
				return null;
			}
		});
	}

	public List<SogIntDettaglioProcedimentoDto> listaTerziNoTitDir(final long idUffOwn, final long idProcedimento, final long idAtto) {

		return (List<SogIntDettaglioProcedimentoDto>)getHibernateTemplate().execute(new HibernateCallback() {

			public List<SogIntDettaglioProcedimentoDto> doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_lista_Terzi_No_Tit_Dir");
				q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setParameter("idAtto", idAtto, Hibernate.LONG);
				q.setResultTransformer(Transformers.aliasToBean(SogIntDettaglioProcedimentoDto.class));
				return q.list();
			}
		});
	}

	/***
	 * Esegue l'inserimento dell'atto relativo alla redazione scheda
	 * 
	 * @param idUffOwn
	 * @param idTipoProc
	 * @param dataRedazioneScheda
	 * @param descUtente
	 * @param descSedeOrgano
	 * @param descOrgano
	 * @param idAttoMisura
	 * @param idMisuraPersonale
	 * @param idProcedimento
	 * @return
	 */
	public Long inserisciRedazioneScheda(final long idUffOwn, final long idTipoProc, final Date dataRedazioneScheda, final String descUtente,
			final String descSedeOrgano, final String descOrgano, final long idAttoMisura, final long idMisuraPersonale, final long idProcedimento) {
		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {
				Query q = session.getNamedQuery("PG_ATTO_inserisciRedazioneScheda");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("idTipoProc", idTipoProc, Hibernate.LONG);
				q.setParameter("dataRedazioneScheda", dataRedazioneScheda, Hibernate.DATE);
				q.setParameter("descUtente", descUtente, Hibernate.STRING);
				q.setParameter("descSedeOrgano", descSedeOrgano, Hibernate.STRING);
				q.setParameter("descOrgano", descOrgano, Hibernate.STRING);
				q.setParameter("idAttoMisura", idAttoMisura, Hibernate.LONG);
				q.setParameter("idMisuraPersonale", idMisuraPersonale, Hibernate.LONG);
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				return (Long) first.get("IDATTO");
			}
		});
	}

	
	/***
	 * Esegue l'inserimento dell'atto relativo alla redazione scheda
	 * 
	 * @param idUffOwn
	 * @param idTipoProc
	 * @param dataRedazioneScheda
	 * @param descUtente
	 * @param descSedeOrgano
	 * @param descOrgano
	 * @param idAttoMisura
	 * @param idMisuraPersonale
	 * @param idProcedimento
	 * @return
	 */
	public Long inserisciRedazioneSchedaFC(final long idUffOwn, final long idTipoProc, final Date dataRedazioneScheda, final String descUtente,
			final String descSedeOrgano, final String descOrgano, final long idAttoMisura, final Long idMisuraPersonale, final long idProcedimento, boolean fcem, final Long idSogInt) {
		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {
				Query q = session.getNamedQuery("PG_ATTO_inserisciRedazioneSchedaFC");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("idTipoProc", idTipoProc, Hibernate.LONG);
				q.setParameter("dataRedazioneScheda", dataRedazioneScheda, Hibernate.DATE);
				q.setParameter("descUtente", descUtente, Hibernate.STRING);
				q.setParameter("descSedeOrgano", descSedeOrgano, Hibernate.STRING);
				q.setParameter("descOrgano", descOrgano, Hibernate.STRING);
				q.setParameter("idAttoMisura", idAttoMisura, Hibernate.LONG);
				q.setParameter("idMisuraPersonale", idMisuraPersonale, Hibernate.LONG);
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setParameter("idSogInt", idSogInt, Hibernate.LONG);
		
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				return (Long) first.get("IDATTO");
			}
		});
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see it.giustizia.smp.dao.AttoDao#getTRelAttoComunicaz(long)
	 */
	public List<TRelAttoComunicaz> getTRelAttoComunicaz(long idAtto) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelAttoComunicaz.class);
		criteria.add(Restrictions.eq("id.codiKIdAtto", idAtto));
		List<TRelAttoComunicaz> risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	public List<TRelAtti> getTRelAtto(long idAtto,int idUffOwn) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelAtti.class);
		criteria.add(Restrictions.eq("id.codiKIdAttoFi", idAtto));
		criteria.add(Restrictions.eq("id.codiKIdUffOwnerFi", idUffOwn));
		List<TRelAtti> risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	
	public TDecreto getTDecreto(long idAttoDecreto, int idUffOwnerDecreto){
		return getHibernateTemplate().get(TDecreto.class, new TDecretoId(idAttoDecreto,idUffOwnerDecreto));
	}
	
	public TImpugnazione getTImpugnazione(long idAttoImpugnazione, int idUffOwn) {
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			TImpugnazione tImpugnazione = (TImpugnazione)session.get(TImpugnazione.class, new TImpugnazioneId(idAttoImpugnazione,idUffOwn));
			for (TRelSoggImp tRelSoggImp : tImpugnazione.getTRelSoggImps()) {
				tRelSoggImp.getId();	
			}
			return tImpugnazione;
		} catch(Exception e){
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}

	public List<TRelAttoMisPers> getTRelAttoMisPers(long idProcedimento, long idAtto, int idUffOwn) {
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
	
			Criteria criteria = session.createCriteria(TRelAttoMisPers.class);
			criteria.add(Restrictions.eq("id.codiKIdAtto", idAtto));
			criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUffOwn));
			criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
			List<TRelAttoMisPers> risultati = criteria.list();
			for (TRelAttoMisPers tRelAttoMisPers : risultati) {
				if (tRelAttoMisPers.getTMisuraPers()!=null) {
					tRelAttoMisPers.getTMisuraPers().getTTpCaratMisura();
					tRelAttoMisPers.getTMisuraPers().getTSoggetto();
				}
				if (tRelAttoMisPers.getTCauzione()!=null) tRelAttoMisPers.getTCauzione().getTAnUfficio();
				tRelAttoMisPers.getTAnNormative();
				tRelAttoMisPers.getTAnUfficio();
				tRelAttoMisPers.getTAtto();
				tRelAttoMisPers.getTProcedimento();
				tRelAttoMisPers.getTTpEsitoByCodiFkIdEsito1();
				tRelAttoMisPers.getTTpEsitoByCodiFkIdEsito2();
				tRelAttoMisPers.getTTpEsitoByCodiFkIdEsito3();
				tRelAttoMisPers.getTTpEsitoFinale();
				tRelAttoMisPers.getTTpTipoMisura();
				tRelAttoMisPers.getTTpTipoMotivo();
				
			}
			return risultati;
		} catch(Exception e){
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	
	
	public List<TRelBeneAtto> getTRelBeneAtto(long idProcedimento, long idAtto, int idUffOwn) {
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Criteria criteria = session.createCriteria(TRelBeneAtto.class);
			criteria.add(Restrictions.eq("id.codiKIdAtto", idAtto));
			criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUffOwn));
			criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
			List<TRelBeneAtto> risultati = criteria.list();
			for (TRelBeneAtto tRelBeneAtto : risultati) {
				for (TRelAttoMisPatr tRelAttoMisPatrs : tRelBeneAtto.getTRelAttoMisPatrs()) {
					tRelAttoMisPatrs.getId();
				}
			}
			return risultati;
		} catch(Exception e){
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	
	public TRelProcSogint getTRelProcSogint(long idProcedimento, Long codiFkIdSoggInt, int idUffOwn) {
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			TRelProcSogint procSogint =	(TRelProcSogint)session.get(TRelProcSogint.class, new TRelProcSogintId(idUffOwn,idProcedimento,codiFkIdSoggInt));
			if (procSogint.getTRelProcSogintVer()!=null) {
				TSoggetto soggeto = procSogint.getTRelProcSogintVer().getTSoggetto();
				soggeto.getTTpTipoSoggetto();
				//soggeto.getTPersonaFisicas().size();
				//soggeto.getTPersonaGiurs().size();
				
			}
			return 	procSogint;
		} catch(Exception e){
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	
	public TPersonaFisica getTPersonaFisica(long codiKIdSoggInt, short codiFkIdVer, int codiKIdUffOwner) {
		return 	getHibernateTemplate().get(TPersonaFisica.class, new TPersonaFisicaId(codiKIdUffOwner,codiKIdSoggInt,codiFkIdVer));
	}
	
	public TPersonaGiur getTPersonaGiur(long codiKIdSoggInt, short codiFkIdVer, int codiKIdUffOwner) {
		return 	getHibernateTemplate().get(TPersonaGiur.class, new TPersonaGiurId(codiKIdUffOwner,codiKIdSoggInt,codiFkIdVer));
			
	}
	
	public TRelProcBene getTRelProcBene(long idProcedimento, long codiKIdBene, int idUffOwn) {
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			TRelProcBene relProcBene =(TRelProcBene)session.get(TRelProcBene.class, new TRelProcBeneId(idProcedimento,idUffOwn,codiKIdBene));
			relProcBene.getTBene().getTTpCatBene();
			return 	relProcBene ;
		} catch(Exception e){
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	/*
	public List<TRelSoggImp> getTTRelSoggImp(long idImpugnazione, int idUffOwn) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelSoggImp.class);
		criteria.add(Restrictions.eq("id.codiKIdImpugnaz", idImpugnazione));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUffOwn));
		List<TRelSoggImp> risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}*/
	
	public TComunicazione getTComunicazioneById(long idComuniczione) {
		return getHibernateTemplate().get(TComunicazione.class, idComuniczione);

	}
	
	
	public TAnimali getTAnimali(long codiKIdBene, int idUffOwn) {
		return getHibernateTemplate().get(TAnimali.class, new TAnimaliId(idUffOwn,codiKIdBene));
	}
	
	public TCollezioni getTCollezioni(long codiKIdBene, int idUffOwn) {
		return getHibernateTemplate().get(TCollezioni.class, new TCollezioniId(idUffOwn,codiKIdBene));
	}

	public TVeicolo getTVeicolo(long codiKIdBene, int idUffOwn) {
		return getHibernateTemplate().get(TVeicolo.class, new TVeicoloId(codiKIdBene,idUffOwn));
	}
	
	public TDenaro getTDenaro(long codiKIdBene, int idUffOwn) {
		return getHibernateTemplate().get(TDenaro.class, new TDenaroId(codiKIdBene,idUffOwn));
	}
	
	public TContante getTContante(long codiKIdBene, int idUffOwn) {
		return getHibernateTemplate().get(TContante.class, new TContanteId(codiKIdBene,idUffOwn));
	}
	
	public TAzienda getTAzienda(long codiKIdBene, int idUffOwn) {
		return getHibernateTemplate().get(TAzienda.class, new TAziendaId(idUffOwn,codiKIdBene));
	}
	
	public TBeneImmat getTBeneImmat(long codiKIdBene, int idUffOwn) {
		return getHibernateTemplate().get(TBeneImmat.class, new TBeneImmatId(codiKIdBene,idUffOwn));
	}
	
	public TBeneFin getTBeneFin(long codiKIdBene, int idUffOwn) {
		return getHibernateTemplate().get(TBeneFin.class, new TBeneFinId(codiKIdBene,idUffOwn));
	}
	
	public TBeneImmobile getTBeneImmobile(long codiKIdBene, int idUffOwn) {
		return getHibernateTemplate().get(TBeneImmobile.class, new TBeneImmobileId(codiKIdBene,idUffOwn));
			
	}

	/**
	 * metodo per il versionamento del soggetto
	 * 
	 * @param idUffOwn
	 * @param idProcedimento
	 * @param descUtente
	 * @param atto
	 * @param idMagistrato
	 * @param personaFisica
	 * @param personaGiur
	 * @param tipoSoggetto
	 * @return Long idAtto
	 */
	public Long inserisciAttoVerSoggetto(final long idUffOwn, final long idProcedimento, final String descUtente, final TAtto atto,
			final long idMagistrato, final TPersonaFisica personaFisica, final TPersonaGiur personaGiur, final String tipoSoggetto) {
		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {
				Query q = session.getNamedQuery("PG_ATTO_inserisciAttoVerSoggetto");
				q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
				q.setParameter("descUtente", descUtente, Hibernate.STRING);
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setParameter("idTipoAtto", atto.getTTpTipoAtto().getCodiKIdTipoAtto(), Hibernate.INTEGER);
				q.setParameter("idCatAtto", atto.getTTpCatAtto().getCodiKIdCatAtto(), Hibernate.INTEGER);

				if (idMagistrato > 0) {
					q.setParameter("idMagistrato", idMagistrato, Hibernate.LONG);
				} else {
					q.setParameter("idMagistrato", null, Hibernate.LONG);
				}

				if (personaFisica.getId() != null && tipoSoggetto.equalsIgnoreCase(Constants.SOGGETTO_FISICO)) {
					q.setParameter("idPersFis", personaFisica.getId().getCodiKIdPersonaFis(), Hibernate.LONG);
				} else {
					q.setParameter("idPersFis", null, Hibernate.LONG);
				}

				if (tipoSoggetto.equalsIgnoreCase(Constants.SOGGETTO_FISICO)) {
					if (personaFisica.getId() != null) {
						q.setParameter("idVersione", personaFisica.getId().getCodiKIdVer(), Hibernate.SHORT);
					} else {
						q.setParameter("idVersione", null, Hibernate.SHORT);
					}
				} else if (tipoSoggetto.equalsIgnoreCase(Constants.SOGGETTO_GIURIDICO)) {
					if (personaGiur.getId() != null) {
						q.setParameter("idVersione", personaGiur.getId().getCodiKIdVer(), Hibernate.SHORT);
					} else {
						q.setParameter("idVersione", null, Hibernate.SHORT);
					}
				}

				if (personaFisica.getDescCognome() != null) {
					q.setParameter("cognome", personaFisica.getDescCognome(), Hibernate.STRING);
				} else {
					q.setParameter("cognome", null, Hibernate.STRING);
				}
				if (personaFisica.getDescCognome() != null) {
					q.setParameter("nome", personaFisica.getDescNome(), Hibernate.STRING);
				} else {
					q.setParameter("nome", null, Hibernate.STRING);
				}

				if (personaFisica.getDataNascita() != null) {
					q.setParameter("dataNascita", personaFisica.getDataNascita(), Hibernate.DATE);
				} else {
					q.setParameter("dataNascita", null, Hibernate.DATE);
				}
				if (personaFisica.getTAnStatoByCodiFkIdStatoNasc() != null) {
					q.setParameter("idStatoNasc", personaFisica.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato(), Hibernate.INTEGER);
				} else {
					q.setParameter("idStatoNasc", null, Hibernate.INTEGER);
				}
				if (personaFisica.getDescComEsteroNasc() != null) {
					q.setParameter("ComuneEsteroNasc", personaFisica.getDescComEsteroNasc(), Hibernate.STRING);
				} else {
					q.setParameter("ComuneEsteroNasc", null, Hibernate.STRING);
				}

				if (personaFisica.getTAnComuniByCodiFkIdComNasc() != null && personaFisica.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune() > 0) {
					q.setParameter("idComuneNasc", personaFisica.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune(), Hibernate.INTEGER);
				} else {
					q.setParameter("idComuneNasc", null, Hibernate.INTEGER);
				}
				if (personaFisica.getDescCodiceFiscale() != null) {
					q.setParameter("codiceFiscale", personaFisica.getDescCodiceFiscale(), Hibernate.STRING);
				} else {
					q.setParameter("codiceFiscale", null, Hibernate.STRING);
				}
				if (personaFisica.getDescCui() != null) {
					q.setParameter("cui", personaFisica.getDescCui(), Hibernate.STRING);
				} else {
					q.setParameter("cui", null, Hibernate.STRING);
				}

				if (personaGiur.getId() != null && tipoSoggetto.equalsIgnoreCase(Constants.SOGGETTO_GIURIDICO)) {
					q.setParameter("idPersGiur", personaGiur.getId().getCodiKIdPersGiur(), Hibernate.LONG);
				} else {
					q.setParameter("idPersGiur", null, Hibernate.LONG);
				}

				q.setParameter("tipoSoggetto", tipoSoggetto, Hibernate.STRING);

				if (personaGiur.getDescRagioneSoc() != null) {
					q.setParameter("ragioneSociale", personaGiur.getDescRagioneSoc(), Hibernate.STRING);
				} else {
					q.setParameter("ragioneSociale", null, Hibernate.STRING);
				}
				if (personaGiur.getTTpTipoSocieta() != null) {
					q.setParameter("tipoSocieta", personaGiur.getTTpTipoSocieta().getCodiKIdTipoSoc(), Hibernate.INTEGER);
				} else {
					q.setParameter("tipoSocieta", null, Hibernate.INTEGER);
				}
				if (personaGiur.getDescPartitaIva() != null) {
					q.setParameter("piva", personaGiur.getDescPartitaIva(), Hibernate.STRING);
				} else {
					q.setParameter("piva", null, Hibernate.STRING);
				}
				if (personaGiur.getTAnStato() != null && personaGiur.getTAnStato().getCodiKIdStato() > 0) {
					q.setParameter("idStatoSede", personaGiur.getTAnStato().getCodiKIdStato(), Hibernate.INTEGER);
				} else {
					q.setParameter("idStatoSede", null, Hibernate.INTEGER);
				}
				if (personaGiur.getTAnComuni() != null && personaGiur.getTAnComuni().getCodiKIdComune() > 0) {
					q.setParameter("idComuneSede", personaGiur.getTAnComuni().getCodiKIdComune(), Hibernate.INTEGER);
				} else {
					q.setParameter("idComuneSede", null, Hibernate.INTEGER);
				}
				if (personaGiur.getDescComEsteroSede() != null) {
					q.setParameter("desComuneEsteroSede", personaGiur.getDescComEsteroSede(), Hibernate.STRING);
				} else {
					q.setParameter("desComuneEsteroSede", null, Hibernate.STRING);
				}

				// non versionano ma servono per l'INSERIMENTO---persona fisica
				if (personaFisica.getFlgSesso() != null) {
					q.setParameter("sesso", personaFisica.getFlgSesso(), Hibernate.CHARACTER);
				} else {
					q.setParameter("sesso", null, Hibernate.CHARACTER);
				}

				if (personaFisica.getTAnStatoByCodiFkIdStatoMorte() != null && personaFisica.getTAnStatoByCodiFkIdStatoMorte().getCodiKIdStato() > 0) {
					q.setParameter("statoDecesso", personaFisica.getTAnStatoByCodiFkIdStatoMorte().getCodiKIdStato(), Hibernate.INTEGER);
				} else {
					q.setParameter("statoDecesso", null, Hibernate.INTEGER);
				}
				if (personaFisica.getTAnComuniByCodiFkIdComMorte() != null && personaFisica.getTAnComuniByCodiFkIdComMorte().getCodiKIdComune() > 0) {
					q.setParameter("comuneDecesso", personaFisica.getTAnComuniByCodiFkIdComMorte().getCodiKIdComune(), Hibernate.INTEGER);
				} else {
					q.setParameter("comuneDecesso", null, Hibernate.INTEGER);
				}
				if (personaFisica.getDescComEsteroMorte() != null) {
					q.setParameter("comuneEsteroDecesso", personaFisica.getDescComEsteroMorte(), Hibernate.STRING);
				} else {
					q.setParameter("comuneEsteroDecesso", null, Hibernate.STRING);
				}

				if (personaFisica.getDataDecesso() != null) {
					q.setParameter("dataDecesso", personaFisica.getDataDecesso(), Hibernate.DATE);
				} else {
					q.setParameter("dataDecesso", null, Hibernate.DATE);
				}
				if (personaFisica.getDescCogPadre() != null) {
					q.setParameter("cognomePadre", personaFisica.getDescCogPadre(), Hibernate.STRING);
				} else {
					q.setParameter("cognomePadre", null, Hibernate.STRING);
				}
				if (personaFisica.getDescNomePadre() != null) {
					q.setParameter("nomePadre", personaFisica.getDescNomePadre(), Hibernate.STRING);
				} else {
					q.setParameter("nomePadre", null, Hibernate.STRING);
				}
				if (personaFisica.getDescCogMadre() != null) {
					q.setParameter("cognomeMadre", personaFisica.getDescCogMadre(), Hibernate.STRING);
				} else {
					q.setParameter("cognomeMadre", null, Hibernate.STRING);
				}
				if (personaFisica.getDescNomeMadre() != null) {
					q.setParameter("nomeMadre", personaFisica.getDescNomeMadre(), Hibernate.STRING);
				} else {
					q.setParameter("nomeMadre", null, Hibernate.STRING);
				}

				// non versionano ma servono per l'INSERIMENTO---persona giuridica

				if (personaGiur.getDescNumeCciaa() != null) {
					q.setParameter("cciaa", personaGiur.getDescNumeCciaa(), Hibernate.STRING);
				} else {
					q.setParameter("cciaa", null, Hibernate.STRING);
				}
				if (personaGiur.getTTpTipoStrada() != null && personaGiur.getTTpTipoStrada().getCodiKIdTipoStrada() > 0) {
					q.setParameter("tipoStradaSede", personaGiur.getTTpTipoStrada().getCodiKIdTipoStrada(), Hibernate.INTEGER);
				} else {
					q.setParameter("tipoStradaSede", null, Hibernate.INTEGER);
				}
				if (personaGiur.getDescIndirizzoSede() != null) {
					q.setParameter("indirizzoSede", personaGiur.getDescIndirizzoSede(), Hibernate.STRING);
				} else {
					q.setParameter("indirizzoSede", null, Hibernate.STRING);
				}
				if (personaGiur.getDescCivico() != null) {
					q.setParameter("civicoSede", personaGiur.getDescCivico(), Hibernate.STRING);
				} else {
					q.setParameter("civicoSede", null, Hibernate.STRING);
				}
				if (personaGiur.getDescRapprSoc() != null) {
					q.setParameter("rappresentante", personaGiur.getDescRapprSoc(), Hibernate.STRING);
				} else {
					q.setParameter("rappresentante", null, Hibernate.STRING);
				}
				if (personaGiur.getDescAmmin() != null) {
					q.setParameter("amministratore", personaGiur.getDescAmmin(), Hibernate.STRING);
				} else {
					q.setParameter("amministratore", null, Hibernate.STRING);
				}
				if (personaGiur.getImpoCapSoc() != null) {
					q.setParameter("capitaleSociale", personaGiur.getImpoCapSoc(), Hibernate.BIG_DECIMAL);
				} else {
					q.setParameter("capitaleSociale", null, Hibernate.BIG_DECIMAL);
				}
				if (personaGiur.getTTpSettoreAtt() != null && personaGiur.getTTpSettoreAtt().getCodiKIdSettoreAtt() > 0) {
					q.setParameter("settoreAttivita", personaGiur.getTTpSettoreAtt().getCodiKIdSettoreAtt(), Hibernate.INTEGER);
				} else {
					q.setParameter("settoreAttivita", null, Hibernate.INTEGER);
				}
				if (personaGiur.getDescNote() != null) {
					q.setParameter("annotazioni", personaGiur.getDescNote(), Hibernate.STRING);
				} else {
					q.setParameter("annotazioni", null, Hibernate.STRING);
				}

				// documento
				if (atto.getDataDeposito() != null) {
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				} else {
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				}

				if (atto.getDataEmissione() != null) {
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				} else {
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				}

				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				return (Long) first.get("IDATTO");
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.giustizia.smp.dao.AttoDao#invioCasellario(long, long, java.util.Date, long, long, long, java.lang.String, java.sql.Blob, java.sql.Blob,
	 * java.sql.Blob)
	 */
	public String invioCasellario(final Long idUffOwn, final Long idAttoComunicazione, final Date dataInvio, final Long idComunicazione,
			final Long idProcedimento, final Long numSoggettoCasellario, final String descUtente, final Blob xmlComunicazione,
			final Blob fileEstrattoCertif, final Blob moduloElettorale, final Character flagStatoInvio) {
		return (String) getHibernateTemplate().execute(new HibernateCallback() {

			public String doInHibernate(final Session session) throws HibernateException, SQLException {
				Query q = session.getNamedQuery("PG_ATTO_invioCasellario");

				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("idAttoComunicazione", idAttoComunicazione, Hibernate.LONG);
				q.setParameter("dataInvio", dataInvio, Hibernate.DATE);
				q.setParameter("idComunicazione", idComunicazione, Hibernate.LONG);
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setParameter("numSoggettoCasellario", numSoggettoCasellario, Hibernate.LONG);
				q.setParameter("descUtente", descUtente, Hibernate.STRING);
				q.setParameter("xmlComunicazione", xmlComunicazione, Hibernate.BLOB);
				q.setParameter("fileEstrattoCertif", fileEstrattoCertif, Hibernate.BLOB);
				q.setParameter("moduloElettorale", moduloElettorale, Hibernate.BLOB);
				q.setParameter("flagStatoInvio", flagStatoInvio, Hibernate.CHARACTER);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				return (String) first.get("STATOUPDATE");
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.giustizia.smp.dao.AttoDao#cancellaCasellario(long, long, java.lang.String)
	 */
	public String cancellaCasellario(final Long idUffOwn, final Long idComunicazione, final String descUtente) {
		return (String) getHibernateTemplate().execute(new HibernateCallback() {

			public String doInHibernate(final Session session) throws HibernateException, SQLException {
				Query q = session.getNamedQuery("PG_ATTO_cancellaCasellario");

				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("idComunicazione", idComunicazione, Hibernate.LONG);
				q.setParameter("descUtente", descUtente, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				return (String) first.get("STATOUPDATE");
			}
		});
	}

	public KeyMessageDto controllaEsitoFinale(final int idUffOwn, final long idProc, final long idAtto, final int catAtto) {

		return (KeyMessageDto) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_cntr_EsitoFinale");
				q.setParameter("IDUFFOWN", idUffOwn);
				q.setParameter("IDPROCEDIMENTO", idProc, Hibernate.LONG);
				q.setParameter("IDATTO", idAtto, Hibernate.LONG);
				q.setParameter("ICATATTO", catAtto, Hibernate.INTEGER);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return new KeyMessageDto(l);
			}
		});

	}

	/**
	 * Restituisce le impugnazione della misura per il grado precedente
	 * 
	 * @param idProcedimento
	 * @param IdUffOwner
	 * @param idMisuraPers
	 * @param idAtto
	 *            , Atto decisorio del grado attuale
	 * @return List<ImpugnazioneCassellarioForm>
	 */
	public List<ImpugnazioneCassellarioDTO> listaImpugnazioniGradoPrec(final long idProcedimento, final long idUffOwner, final long idMisuraPers,
			final long idAtto) {

		return (List<ImpugnazioneCassellarioDTO>)getHibernateTemplate().execute(new HibernateCallback() {

			public List<ImpugnazioneCassellarioDTO> doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_listaImpugnazioniGradoPrec");
				q.setParameter("IDPROCEDIMENTO", idProcedimento, Hibernate.LONG);
				q.setParameter("ID_UFF_OWNER", idUffOwner, Hibernate.LONG);
				q.setParameter("ID_MISURA_PERS", idMisuraPers, Hibernate.LONG);
				q.setParameter("ID_ATTO", idAtto, Hibernate.LONG);
				// q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				q.setResultTransformer(Transformers.aliasToBean(ImpugnazioneCassellarioDTO.class));
				return q.list();

			}
		});
	}

	/***
	 * Esegue l'inserimento dell'atto relativo alla richiesta di un certificato penale
	 * 
	 * @param idProcedimento
	 * @param idUffOwn
	 * @param descUtente
	 * @param descSedeOrgano
	 * @param descOrgano
	 * @param idSoggetto
	 * @param flagStatoInvio
	 * @param xmlRichiesta
	 * @param xmlRisposta
	 * @return ritorna l'id dell'atto appena inserito.
	 */
	public Long inserisciCertificatoPenale(final long idProcedimento, final long idUffOwn, final String descUtente, final String descSedeOrgano,
			final String descOrgano, final long idSoggetto, final Character flagStatoInvio, final Blob xmlRichiesta, final Blob xmlRisposta) {
		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {
				Query q = session.getNamedQuery("PG_ATTO_inserisciCertificatoPenale");
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("descUtente", descUtente, Hibernate.STRING);
				q.setParameter("descSedeOrgano", descSedeOrgano, Hibernate.STRING);
				q.setParameter("descOrgano", descOrgano, Hibernate.STRING);
				q.setParameter("idSoggetto", idSoggetto, Hibernate.LONG);
				q.setParameter("flagStatoInvio", flagStatoInvio, Hibernate.CHARACTER);
				q.setParameter("xmlRichiesta", xmlRichiesta, Hibernate.BLOB);
				q.setParameter("xmlRisposta", xmlRisposta, Hibernate.BLOB);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				return (Long) first.get("IDATTO");
			}
		});
	}

	public TCertificatoPenale getTCertificatoPenale(long idProcedimento, long idAtto, long idUffOwn) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TCertificatoPenale.class)
				.add(Restrictions.eq("TRelProcSogint.id.codiKIdUffOwner", (int) idUffOwn))
				.add(Restrictions.eq("TRelProcSogint.id.codiKIdProc", idProcedimento)).add(Restrictions.eq("codiFKIdAtto", idAtto));
		List<TCertificatoPenale> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0) {
			return risultati.get(0);
		}
		return null;
	}

	public boolean controllaPresenzaGDPerProc(int idUfficio, long idProcedimento) {
		int ruoloGD =4;
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelMagProc.class);
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficio));
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		criteria.add(Restrictions.eq("TTpTipoRuoloMag.codiKIdRuoloMag", ruoloGD));

		List<TRelMagProc> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
        	return true;
        else
        	return false;
	}

	public void aggiornaDatiAtto(final long codiceAtto, final int idUffOwner, final String note) {
		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				TAtto query = getHibernateTemplate().get(TAtto.class, new TAttoId(codiceAtto, idUffOwner));

				query.setDescNote(Utility.limitaStringa(note, Constants.TATTO_CAMPO_NOTE_LEN));

				session.saveOrUpdate(query);
				session.flush();
				return null;
			}
		});
	}

	public void aggiornaDatiAtto(final TAtto datiAtto) {
		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				// TAttoId key = datiAtto.getId();
				// key.setCodiKIdAtto(datiAtto.getCodiKIdAtto());
				//
				// key.setId(datiAtto.getId());
				TAtto query = getHibernateTemplate().get(TAtto.class, datiAtto.getId());

				query.setDescNote(datiAtto.getDescNote());

				session.saveOrUpdate(query);
				session.flush();
				return null;
			}
		});
	}

	public KeyMessageDto controllaAmministratoriSenzaBeni(final int idUffOwn, final long idProc, final long idAtto) {

		return (KeyMessageDto) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_controllaAmministratoriSenzaBeni");
				q.setParameter("IDUFFOWN", idUffOwn);
				q.setParameter("IDPROCEDIMENTO", idProc, Hibernate.LONG);
				q.setParameter("IDATTO", idAtto, Hibernate.LONG);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return new KeyMessageDto(l);
			}
		});

	}
	
	public List<Map> controllaAmministratoriSenzaBeniDecisorio(final int idUffOwn, final long idProc, final long idAtto) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_controllaAmministratoriSenzaBeniDecisorio");
				q.setParameter("IDUFFOWN", idUffOwn);
				q.setParameter("IDPROCEDIMENTO", idProc, Hibernate.LONG);
				q.setParameter("IDATTO", idAtto, Hibernate.LONG);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				return q.list();
			}
		});

	}	

	// P.Q. Mev 2020/48 Inizio
	public List<Map> controllaAmministratoriSenzaBeniDecisorioSecondoGrado(final int idUffOwn, final long idProc, final long idAtto, final long idFascicoloGestione) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException { 

				Query q = session.getNamedQuery("PG_ATTO_controllaAmministratoriSenzaBeniDecisorioSecondoGrado");
				q.setParameter("IDUFFOWN", idUffOwn);
				q.setParameter("IDPROCEDIMENTO", idProc, Hibernate.LONG);
				q.setParameter("IDATTO", idAtto, Hibernate.LONG);
				q.setParameter("IDFASC_GEST", idFascicoloGestione, Hibernate.LONG);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				return q.list();
			}
		});

	}	
	// P.Q. Mev 2020/48 Fine
	
	public List<Map> controllaBeniAmministratoriSenzaAmministratore(final int idUffOwn, final long idProc, final long idAtto) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_controllaBeniSenzaAmministratore");
				q.setParameter("IDUFFOWN", idUffOwn);
				q.setParameter("IDPROCEDIMENTO", idProc, Hibernate.LONG);
				q.setParameter("IDATTO", idAtto, Hibernate.LONG);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				return q.list();
			}
		});

	}		

	public ImpugnazioneCassellarioDTO getSentenzaCassazione(int idUffOwn, long idProc, long idAttoDecisorioAttuale, long idMisuraPersAttuale) {
		TAttoId attoId = new TAttoId();
		attoId.setCodiKIdAtto(idAttoDecisorioAttuale);
		attoId.setCodiKIdUffOwner(idUffOwn);
		TAtto atto = getAttoById(attoId);
		ImpugnazioneCassellarioDTO cassellarioDTO = new ImpugnazioneCassellarioDTO();
		cassellarioDTO.setDESC_CAT_ATTO(atto.getTTpCatAtto().getDescCatAtto());
		cassellarioDTO.setID_ATTO_DECISORIO(BigDecimal.valueOf(idAttoDecisorioAttuale));
		cassellarioDTO.setID_UFF_DECISORIO(BigDecimal.valueOf(Constants.ID_UFFICIO_CASSAZIONE));

		TRelAttoMisPersId attoMisPersId = new TRelAttoMisPersId();
		attoMisPersId.setCodiKIdAtto(idAttoDecisorioAttuale);
		attoMisPersId.setCodiKIdMisuraPers(idMisuraPersAttuale);
		attoMisPersId.setCodiKIdProc(idProc);
		attoMisPersId.setCodiKIdUffOwner(idUffOwn);
		TRelAttoMisPers attoMisPers = getHibernateTemplate().get(TRelAttoMisPers.class, attoMisPersId);

		// getTRelAttoMisPersesForCodiFkIdEsito3().iterator().next().getTTpEsitoByCodiFkIdEsito1();
		cassellarioDTO.setID_ESITO_DEC_DECISORIO_1(BigDecimal.valueOf(attoMisPers.getTTpEsitoByCodiFkIdEsito3().getCodiKIdEsito()));
		cassellarioDTO.setDESC_ESITO_DEC_DECISORIO_NSC_1(attoMisPers.getTTpEsitoByCodiFkIdEsito3().getDescCodiNSC());
		cassellarioDTO.setDESC_ESITO_DEC_DECISORIO_1(attoMisPers.getTTpEsitoByCodiFkIdEsito3().getDescEsito());

		cassellarioDTO.setDATA_EMISSIONE_DECISORIO(atto.getDataEmissione());

		TAnUfficio ufficioCassazione = getHibernateTemplate().get(TAnUfficio.class, Constants.ID_UFFICIO_CASSAZIONE);
		cassellarioDTO.setDESC_UFFICIO_OWNER(ufficioCassazione.getDescUfficio());
		cassellarioDTO.setCODI_AUTORITA_NSC(ufficioCassazione.getTTpTipoUfficio().getDescCodiAutoritaNSC());
		cassellarioDTO.setCODI_SEDE_AUTORITA_NSC(ufficioCassazione.getTAnComuni().getDescCodiNSC());

		TDecreto decreto = getHibernateTemplate().get(TDecreto.class, new TDecretoId(atto.getId().getCodiKIdAtto(), idUffOwn));

		cassellarioDTO.setANNO_ATTO(BigDecimal.valueOf(decreto.getNumeAnnoAtto()));
		cassellarioDTO.setNUMERO_ATTO(BigDecimal.valueOf(decreto.getNumeAtto()));

		return cassellarioDTO;
	}
	    
    public List<TAtto> getAttiProrogaSequestro(final long idProcedimento, final long idUffOwn) {    

        return (List<TAtto>)getHibernateTemplate().execute(new HibernateCallback() {

            public List<TAtto> doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_ATTO_listaAttiProrogaSequestro");
                q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
                q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
                
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                return q.list();
            }
        });
    }
    
    public List<TAtto> getAttiProrogaSequestroCdA(final long idProcedimento, final long idUffOwn) {    

        return (List<TAtto>)getHibernateTemplate().execute(new HibernateCallback() {

            public List<TAtto> doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_ATTO_listaAttiProrogaSequestroCdA");
                q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
                q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
                
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                return q.list();
            }
        });
    }
 
    
	public long inserisciAttoDecretoProrogaSequestro(final int idUffOwn, final long idProcedimento, final String codiceUtente, final TAtto atto, final long idStatoAtto, final long idAttoOrigine, final TDecreto decreto, final String nomeMetodo) {

        return (Long)getHibernateTemplate().execute(new HibernateCallback() {

            public Long doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_ATTO_inserisciDecretoProrogaSequestro"); 
                q.setParameter("idUffOwn", idUffOwn);
                q.setParameter("descUtente", codiceUtente);
                q.setParameter("idProcedimento", idProcedimento);
                if ((atto.getTTpTipoAtto() != null) && (atto.getTTpTipoAtto().getCodiKIdTipoAtto() > 0)) {
                    q.setParameter("idTipoAtto", atto.getTTpTipoAtto().getCodiKIdTipoAtto());
                } else {
                    q.setParameter("idTipoAtto", null, Hibernate.LONG);
                }
                q.setParameter("idAttoOrigine", idAttoOrigine, Hibernate.LONG);
                if (atto.getTTpCatAtto().getCodiKIdCatAtto() > 0) {
                    q.setParameter("idCatAtto", atto.getTTpCatAtto().getCodiKIdCatAtto());
                } else {
                    q.setParameter("idCatAtto", null, Hibernate.LONG);
                }
                if (decreto != null && decreto.getTUdienza() != null && decreto.getTUdienza().getId() != null && decreto.getTUdienza().getId().getCodiKIdUdienza() > 0) {
                    q.setParameter("idUdienza", decreto.getTUdienza().getId().getCodiKIdUdienza());
                } else {
                    q.setParameter("idUdienza", null, Hibernate.LONG);
                }
                if (idStatoAtto > 0) {
                    q.setParameter("idStatoAtto", idStatoAtto);
                } else {
                    q.setParameter("idStatoAtto", null, Hibernate.LONG);
                }
                if (atto.getDataEmissione() != null) {
                    q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
                } else {
                    q.setParameter("dataEmissione", null, Hibernate.DATE);
                }
                if (atto.getDataDeposito() != null) {
                    q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
                } else {
                    q.setParameter("dataDeposito", null, Hibernate.DATE);
                }
                if (atto.getDataRegistrazione() != null) {
                    q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
                } else {
                    q.setParameter("dataRegistrazione", null, Hibernate.DATE);
                }
                if (atto.getFileDocumento() != null) {
                    q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
                    q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
                    q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
                } else {
                    q.setParameter("documento", null, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", null, Hibernate.STRING);
                    q.setParameter("nomeDocumento", null, Hibernate.STRING);
                }
                q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                HashMap first = (HashMap) l.get(0);
                Long id = (Long) first.get("IDATTO");
                return id;
            }
        });
    }
	
    public Long inviaAttoDecretoProrogaSequestro(final long idUfficio, final long idProcedimento, final TAtto atto, final String codUtente, final long tipoAtto,final long categoriaAtto, final long idStatoAtto, final String nomeMetodo) {

        return (Long) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

            	/* 
				 * ISSUE MEV : Specifiche Intervento-006-SpeseProces-NumAtto
				 * Numero MEV : 006
				 * Autore    : Gioggi
				 * Data      : 18 apr 2020
				 * Branch    : MEV_006
				 */
				Query q;
				if (esisteNumAtto(atto.getId().getCodiKIdAtto(), idUfficio))
					q = session.getNamedQuery("PG_NUMERO_ATTO_generaFlussoDopoNumeroAttoDPS");
				else
					q = session.getNamedQuery("PG_ATTO_inviaDecretoProrogaSequestro");
				//***** FINE INTERVENTO MEV_006 *****//

                q.setParameter("idUffOwn", idUfficio);
                q.setParameter("idProc", idProcedimento, Hibernate.LONG);
                if (atto.getDataRegistrazione() != null) {
                    //q.setParameter("idMittente", idUfficio, Hibernate.LONG);
                    //q.setParameter("idDestinatario", idDestinatario, Hibernate.LONG);
                    q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
                } else {
                    //q.setParameter("idMittente", null, Hibernate.LONG);
                    //q.setParameter("idDestinatario", null, Hibernate.LONG);
                    //q.setParameter("idUltDestinatario", null, Hibernate.LONG);
                    q.setParameter("dataRegistrazione", null, Hibernate.DATE);
                }
                if (atto.getDataEmissione() != null) {
                    q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
                } else {
                    q.setParameter("dataEmissione", null, Hibernate.DATE);
                }
                if (atto.getDataDeposito() != null) {
                    q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
                } else {
                    q.setParameter("dataDeposito", null, Hibernate.DATE);
                }
                if (atto.getFileDocumento() != null) {
                    q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
                    q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
                    q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
                } else {
                    q.setParameter("documento", null, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", null, Hibernate.STRING);
                    q.setParameter("nomeDocumento", null, Hibernate.STRING);
                }
                if (atto.getDataRicezione() != null) {
                    q.setParameter("dataRicezioneDoc", atto.getDataRicezione(), Hibernate.DATE);
                } else {
                    q.setParameter("dataRicezioneDoc", null, Hibernate.DATE);
                }
                q.setParameter("descUtente", codUtente);
                q.setParameter("idAtto", atto.getId().getCodiKIdAtto(), Hibernate.LONG);
                q.setParameter("tipoAtto", tipoAtto, Hibernate.LONG);
                q.setParameter("categoriaAtto", categoriaAtto, Hibernate.LONG);
                q.setParameter("idStatoAtto", idStatoAtto, Hibernate.LONG);
                q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                q.list();
                return atto.getId().getCodiKIdAtto();
            }
        });
    }		

		public KeyMessageDto controllaInserimentoDecretoSospensioneTemporanea(final long idProcedimento, final int idUffOwn) {
			
        return (KeyMessageDto)getHibernateTemplate().execute(new HibernateCallback() {

            public KeyMessageDto doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_ATTO_controllaInserimentoDecretoSospensioneTemporanea");
                q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
                q.setParameter("idUffOwn", idUffOwn, Hibernate.INTEGER);
                
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
		
	public long inserisciAttoDecretoSospensioneTemporanea(final int idUffOwn, final long idProcedimento, final String codiceUtente, final TAtto atto, final long idStatoAtto, final TDecreto decreto, final String nomeMetodo) {

        return (Long)getHibernateTemplate().execute(new HibernateCallback() {

            public Long doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_ATTO_inserisciDecretoSospensioneTemporanea"); 
                q.setParameter("idUffOwn", idUffOwn);
                q.setParameter("descUtente", codiceUtente);
                q.setParameter("idProcedimento", idProcedimento);
                if ((atto.getTTpTipoAtto() != null) && (atto.getTTpTipoAtto().getCodiKIdTipoAtto() > 0)) {
                    q.setParameter("idTipoAtto", atto.getTTpTipoAtto().getCodiKIdTipoAtto());
                } else {
                    q.setParameter("idTipoAtto", null, Hibernate.LONG);
                }
                if (atto.getTTpCatAtto().getCodiKIdCatAtto() > 0) {
                    q.setParameter("idCatAtto", atto.getTTpCatAtto().getCodiKIdCatAtto());
                } else {
                    q.setParameter("idCatAtto", null, Hibernate.LONG);
                }
                if (decreto != null && decreto.getTUdienza() != null && decreto.getTUdienza().getId() != null && decreto.getTUdienza().getId().getCodiKIdUdienza() > 0) {
                    q.setParameter("idUdienza", decreto.getTUdienza().getId().getCodiKIdUdienza());
                } else {
                    q.setParameter("idUdienza", null, Hibernate.LONG);
                }
                if (idStatoAtto > 0) {
                    q.setParameter("idStatoAtto", idStatoAtto);
                } else {
                    q.setParameter("idStatoAtto", null, Hibernate.LONG);
                }
                if (atto.getDataEmissione() != null) {
                    q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
                } else {
                    q.setParameter("dataEmissione", null, Hibernate.DATE);
                }
                if (atto.getDataDeposito() != null) {
                    q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
                } else {
                    q.setParameter("dataDeposito", null, Hibernate.DATE);
                }
                if (atto.getDataRegistrazione() != null) {
                    q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
                } else {
                    q.setParameter("dataRegistrazione", null, Hibernate.DATE);
                }
                if (atto.getFileDocumento() != null) {
                    q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
                    q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
                    q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
                } else {
                    q.setParameter("documento", null, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", null, Hibernate.STRING);
                    q.setParameter("nomeDocumento", null, Hibernate.STRING);
                }
                q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                HashMap first = (HashMap) l.get(0);
                Long id = (Long) first.get("IDATTO");
                return id;
            }
        });
    }  	
	
    public Long inviaAttoDecretoSospensioneTemporanea(final long idUfficio, final long idProcedimento, final TAtto atto, final String codUtente, final long tipoAtto,final long categoriaAtto, final long idStatoAtto, final String nomeMetodo) {

        return (Long) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

            	/* 
				 * ISSUE MEV : Specifiche Intervento-006-SpeseProces-NumAtto
				 * Numero MEV : 006
				 * Autore    : Gioggi
				 * Data      : 18 apr 2020
				 * Branch    : MEV_006
				 */
				Query q;
				if (esisteNumAtto(atto.getId().getCodiKIdAtto(), idUfficio))
					q = session.getNamedQuery("PG_NUMERO_ATTO_generaFlussoDopoNumeroAttoDST");
				else
					q = session.getNamedQuery("PG_ATTO_inviaDecretoSospensioneTemporanea");
				//***** FINE INTERVENTO MEV_006 *****//

                q.setParameter("idUffOwn", idUfficio);
                q.setParameter("idProc", idProcedimento, Hibernate.LONG);
                if (atto.getDataRegistrazione() != null) {
                    //q.setParameter("idMittente", idUfficio, Hibernate.LONG);
                    //q.setParameter("idDestinatario", idDestinatario, Hibernate.LONG);
                    q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
                } else {
                    //q.setParameter("idMittente", null, Hibernate.LONG);
                    //q.setParameter("idDestinatario", null, Hibernate.LONG);
                    //q.setParameter("idUltDestinatario", null, Hibernate.LONG);
                    q.setParameter("dataRegistrazione", null, Hibernate.DATE);
                }
                if (atto.getDataEmissione() != null) {
                    q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
                } else {
                    q.setParameter("dataEmissione", null, Hibernate.DATE);
                }
                if (atto.getDataDeposito() != null) {
                    q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
                } else {
                    q.setParameter("dataDeposito", null, Hibernate.DATE);
                }
                if (atto.getFileDocumento() != null) {
                    q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
                    q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
                    q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
                } else {
                    q.setParameter("documento", null, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", null, Hibernate.STRING);
                    q.setParameter("nomeDocumento", null, Hibernate.STRING);
                }
                if (atto.getDataRicezione() != null) {
                    q.setParameter("dataRicezioneDoc", atto.getDataRicezione(), Hibernate.DATE);
                } else {
                    q.setParameter("dataRicezioneDoc", null, Hibernate.DATE);
                }
                q.setParameter("descUtente", codUtente);
                q.setParameter("idAtto", atto.getId().getCodiKIdAtto(), Hibernate.LONG);
                q.setParameter("tipoAtto", tipoAtto, Hibernate.LONG);
                q.setParameter("categoriaAtto", categoriaAtto, Hibernate.LONG);
                q.setParameter("idStatoAtto", idStatoAtto, Hibernate.LONG);
                q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                q.list();
                return atto.getId().getCodiKIdAtto();
            }
        });
    }			
    
	public long inserisciAttoDecretoProrogaSospensioneTemporanea(final int idUffOwn, final long idProcedimento, final String codiceUtente, final TAtto atto, final long idStatoAtto, final long idAttoOrigine, final TDecreto decreto, final String nomeMetodo) {

        return (Long)getHibernateTemplate().execute(new HibernateCallback() {

            public Long doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_ATTO_inserisciDecretoProrogaSospensioneTemporanea"); 
                q.setParameter("idUffOwn", idUffOwn);
                q.setParameter("descUtente", codiceUtente);
                q.setParameter("idProcedimento", idProcedimento);
                if ((atto.getTTpTipoAtto() != null) && (atto.getTTpTipoAtto().getCodiKIdTipoAtto() > 0)) {
                    q.setParameter("idTipoAtto", atto.getTTpTipoAtto().getCodiKIdTipoAtto());
                } else {
                    q.setParameter("idTipoAtto", null, Hibernate.LONG);
                }
                q.setParameter("idAttoOrigine", idAttoOrigine, Hibernate.LONG);
                if (atto.getTTpCatAtto().getCodiKIdCatAtto() > 0) {
                    q.setParameter("idCatAtto", atto.getTTpCatAtto().getCodiKIdCatAtto());
                } else {
                    q.setParameter("idCatAtto", null, Hibernate.LONG);
                }
                if (decreto != null && decreto.getTUdienza() != null && decreto.getTUdienza().getId() != null && decreto.getTUdienza().getId().getCodiKIdUdienza() > 0) {
                    q.setParameter("idUdienza", decreto.getTUdienza().getId().getCodiKIdUdienza());
                } else {
                    q.setParameter("idUdienza", null, Hibernate.LONG);
                }
                if (idStatoAtto > 0) {
                    q.setParameter("idStatoAtto", idStatoAtto);
                } else {
                    q.setParameter("idStatoAtto", null, Hibernate.LONG);
                }
                if (atto.getDataEmissione() != null) {
                    q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
                } else {
                    q.setParameter("dataEmissione", null, Hibernate.DATE);
                }
                if (atto.getDataDeposito() != null) {
                    q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
                } else {
                    q.setParameter("dataDeposito", null, Hibernate.DATE);
                }
                if (atto.getDataRegistrazione() != null) {
                    q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
                } else {
                    q.setParameter("dataRegistrazione", null, Hibernate.DATE);
                }
                if (atto.getFileDocumento() != null) {
                    q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
                    q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
                    q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
                } else {
                    q.setParameter("documento", null, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", null, Hibernate.STRING);
                    q.setParameter("nomeDocumento", null, Hibernate.STRING);
                }
                q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                HashMap first = (HashMap) l.get(0);
                Long id = (Long) first.get("IDATTO");
                return id;
            }
        });
    }	    
	
    public Long inviaAttoDecretoProrogaSospensioneTemporanea(final long idUfficio, final long idProcedimento, final TAtto atto, final String codUtente, final long tipoAtto,final long categoriaAtto, final long idStatoAtto, final String nomeMetodo) {

        return (Long) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

            	/* 
				 * ISSUE MEV : Specifiche Intervento-006-SpeseProces-NumAtto
				 * Numero MEV : 006
				 * Autore    : Gioggi
				 * Data      : 18 apr 2020
				 * Branch    : MEV_006
				 */
				Query q;
				if (esisteNumAtto(atto.getId().getCodiKIdAtto(), idUfficio))
					q = session.getNamedQuery("PG_NUMERO_ATTO_generaFlussoDopoNumeroAttoDPST");
				else
					q = session.getNamedQuery("PG_ATTO_inviaDecretoProrogaSospensioneTemporanea");
				//***** FINE INTERVENTO MEV_006 *****//

                q.setParameter("idUffOwn", idUfficio);
                q.setParameter("idProc", idProcedimento, Hibernate.LONG);
                if (atto.getDataRegistrazione() != null) {
                    //q.setParameter("idMittente", idUfficio, Hibernate.LONG);
                    //q.setParameter("idDestinatario", idDestinatario, Hibernate.LONG);
                    q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
                } else {
                    //q.setParameter("idMittente", null, Hibernate.LONG);
                    //q.setParameter("idDestinatario", null, Hibernate.LONG);
                    //q.setParameter("idUltDestinatario", null, Hibernate.LONG);
                    q.setParameter("dataRegistrazione", null, Hibernate.DATE);
                }
                if (atto.getDataEmissione() != null) {
                    q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
                } else {
                    q.setParameter("dataEmissione", null, Hibernate.DATE);
                }
                if (atto.getDataDeposito() != null) {
                    q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
                } else {
                    q.setParameter("dataDeposito", null, Hibernate.DATE);
                }
                if (atto.getFileDocumento() != null) {
                    q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
                    q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
                    q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
                } else {
                    q.setParameter("documento", null, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", null, Hibernate.STRING);
                    q.setParameter("nomeDocumento", null, Hibernate.STRING);
                }
                if (atto.getDataRicezione() != null) {
                    q.setParameter("dataRicezioneDoc", atto.getDataRicezione(), Hibernate.DATE);
                } else {
                    q.setParameter("dataRicezioneDoc", null, Hibernate.DATE);
                }
                q.setParameter("descUtente", codUtente);
                q.setParameter("idAtto", atto.getId().getCodiKIdAtto(), Hibernate.LONG);
                q.setParameter("tipoAtto", tipoAtto, Hibernate.LONG);
                q.setParameter("categoriaAtto", categoriaAtto, Hibernate.LONG);
                q.setParameter("idStatoAtto", idStatoAtto, Hibernate.LONG);
                q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                q.list();
                return atto.getId().getCodiKIdAtto();
            }
        });
    }		
    
    public List<TAtto> getAttiSospensioneTemporanea(final long idProcedimento, final long idUffOwn) {    

        return (List<TAtto>)getHibernateTemplate().execute(new HibernateCallback() {

            public List<TAtto> doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_ATTO_listaAttiSospensioneTemporanea");
                q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
                q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
                
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                return q.list();
            }
        });
    }	    

	public long inserisciAttoDecretoSequestroSospensioneTemporanea(final int idUffOwn, final long idProcedimento, final String codiceUtente, final TAtto atto, final long idStatoAtto, final long idAttoOrigine, final TDecreto decreto, final String nomeMetodo) {

        return (Long)getHibernateTemplate().execute(new HibernateCallback() {

            public Long doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_ATTO_inserisciDecretoSequestroSospensioneTemporanea"); 
                q.setParameter("idUffOwn", idUffOwn);
                q.setParameter("descUtente", codiceUtente);
                q.setParameter("idProcedimento", idProcedimento);
                if ((atto.getTTpTipoAtto() != null) && (atto.getTTpTipoAtto().getCodiKIdTipoAtto() > 0)) {
                    q.setParameter("idTipoAtto", atto.getTTpTipoAtto().getCodiKIdTipoAtto());
                } else {
                    q.setParameter("idTipoAtto", null, Hibernate.LONG);
                }
                q.setParameter("idAttoOrigine", idAttoOrigine, Hibernate.LONG);
                if (atto.getTTpCatAtto().getCodiKIdCatAtto() > 0) {
                    q.setParameter("idCatAtto", atto.getTTpCatAtto().getCodiKIdCatAtto());
                } else {
                    q.setParameter("idCatAtto", null, Hibernate.LONG);
                }
                if (decreto != null && decreto.getTUdienza() != null && decreto.getTUdienza().getId() != null && decreto.getTUdienza().getId().getCodiKIdUdienza() > 0) {
                    q.setParameter("idUdienza", decreto.getTUdienza().getId().getCodiKIdUdienza());
                } else {
                    q.setParameter("idUdienza", null, Hibernate.LONG);
                }
                if (idStatoAtto > 0) {
                    q.setParameter("idStatoAtto", idStatoAtto);
                } else {
                    q.setParameter("idStatoAtto", null, Hibernate.LONG);
                }
                if (atto.getDataEmissione() != null) {
                    q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
                } else {
                    q.setParameter("dataEmissione", null, Hibernate.DATE);
                }
                if (atto.getDataDeposito() != null) {
                    q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
                } else {
                    q.setParameter("dataDeposito", null, Hibernate.DATE);
                }
                if (atto.getDataRegistrazione() != null) {
                    q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
                } else {
                    q.setParameter("dataRegistrazione", null, Hibernate.DATE);
                }
                if (atto.getFileDocumento() != null) {
                    q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
                    q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
                    q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
                } else {
                    q.setParameter("documento", null, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", null, Hibernate.STRING);
                    q.setParameter("nomeDocumento", null, Hibernate.STRING);
                }
                q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                HashMap first = (HashMap) l.get(0);
                Long id = (Long) first.get("IDATTO");
                return id;
            }
        });
    }
	
	public void attoDecretoApplicazioneProvvisoria (final TAtto atto, final String idMisureCaricate, final long vidProc,
			final long vidAtto, final long vidUffOwn, final TRelAttoMisPers relAttoMisPers, final long idSoggInt,
			final String cauzione, final String articolo67, final String vcodUtente, final String idPrescrizioni,
			final BigDecimal importoCauzione, final String articolo9c1) {

		 getHibernateTemplate().execute(new HibernateCallback() {

			public List doInHibernate(final Session session) throws HibernateException, SQLException {

				/* 
				 * ISSUE MEV : Specifiche Intervento-006-SpeseProces-NumAtto
				 * Numero MEV : 006
				 * Autore    : Gioggi
				 * Data      : 18 apr 2020
				 * Branch    : MEV_006
				 */
				Query q;
				if (esisteNumAtto(atto.getId().getCodiKIdAtto(), vidUffOwn))
					q = session.getNamedQuery("PG_NUMERO_ATTO_generaFlussoDopoNumeroAttoDAP");
				else
					q = session.getNamedQuery("PG_ATTO_inserisciAttoDecretoApplicazioneProvvisoria");
				//***** FINE INTERVENTO MEV_006 *****//

				if (atto.getDataEmissione() != null) {
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				} else {
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				}				

				if (atto.getDataDeposito() != null) {
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				} else {
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				}
				if (atto.getDataRegistrazione() != null){ 
					q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				} else {
					q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				}				
				
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}

				if (atto.getDataRegistrazione() != null){ 
				   q.setParameter("idStatoAtto", 3l, Hibernate.LONG); //registrato
				}else{
					q.setParameter("idStatoAtto", null, Hibernate.LONG); 					
				}

				//MISURE
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
	
	public void attoDecretoRevocaApplicazioneProvvisoria (final TAtto atto, final String idMisureCaricate, final long vidProc, final long vidAtto, final long vidUffOwn, final TRelAttoMisPers relAttoMisPers, final long idSoggInt, final String cauzione, final String articolo67, final String vcodUtente, final String idPrescrizioni, final BigDecimal importoCauzione,final String articolo9c1) {

		 getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				/* 
				 * ISSUE MEV : Specifiche Intervento-006-SpeseProces-NumAtto
				 * Numero MEV : 006
				 * Autore    : Gioggi
				 * Data      : 18 apr 2020
				 * Branch    : MEV_006
				 */
				Query q;
				if (esisteNumAtto(atto.getId().getCodiKIdAtto(), vidUffOwn))
					q = session.getNamedQuery("PG_NUMERO_ATTO_generaFlussoDopoNumeroAttoDRAP");
				else
					q = session.getNamedQuery("PG_ATTO_salvaAttoDecretoRevocaApplicazioneProvvisoria");
				//***** FINE INTERVENTO MEV_006 *****//

				if (atto.getDataEmissione() != null) {
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				} else {
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				}				

				if (atto.getDataDeposito() != null) {
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
					System.out.println("dataDeposito: " + atto.getDataDeposito());
				} else {
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				}
				if (atto.getDataRegistrazione() != null){ 
					q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
					System.out.println("dataRegistrazione: " + atto.getDataRegistrazione());
				} else {
					q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				}				
				
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}

				if (atto.getDataRegistrazione() != null){ 
				   q.setParameter("idStatoAtto", 3l, Hibernate.LONG); //registrato
				}else{
					q.setParameter("idStatoAtto", null, Hibernate.LONG); 					
				}

				//MISURE
				q.setParameter("idMisureCaricate", idMisureCaricate);
				System.out.println("idMisureCaricate: " + idMisureCaricate);
				q.setParameter("idProc", vidProc);
				System.out.println("idProc: " + vidProc);
				if (vidAtto > 0) {
					q.setParameter("idAtto", vidAtto);
					System.out.println("idAtto: " + vidAtto);
				} else {
					q.setParameter("idAtto", null, Hibernate.LONG);
				}
				q.setParameter("idUffOwn", vidUffOwn);
				System.out.println("idUffOwn: " + vidUffOwn);
				q.setParameter("idSoggInt", idSoggInt);
				System.out.println("idSoggInt: " + idSoggInt);
				
				if ((relAttoMisPers != null) && (relAttoMisPers.getTTpTipoMisura() != null) && relAttoMisPers.getTTpTipoMisura().getCodiKIdTipoMisura()>0) {
					q.setParameter("idTipoMisura", relAttoMisPers.getTTpTipoMisura().getCodiKIdTipoMisura());
					System.out.println("idTipoMisura: " + relAttoMisPers.getTTpTipoMisura().getCodiKIdTipoMisura());
				} else {
					q.setParameter("idTipoMisura", null, Hibernate.LONG);
				}
				
				if ((relAttoMisPers != null) && (relAttoMisPers.getTAnNormative() != null) && relAttoMisPers.getTAnNormative().getCodiKIdNormativa()>0) {
					q.setParameter("idNormativa", relAttoMisPers.getTAnNormative().getCodiKIdNormativa());
					System.out.println("idNormativa: " + relAttoMisPers.getTAnNormative().getCodiKIdNormativa());
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
				q.setParameter("cauzione", cauzione, Hibernate.STRING);
				System.out.println("cauzione: " + cauzione);
				// --
																		// valori
																		// ammessi
																		// S o N
				q.setParameter("articolo67", articolo67);
				System.out.println("articolo67: " + articolo67);
				// -- valori ammessi S
															// o N
				
				if ((relAttoMisPers != null) && (relAttoMisPers.getTTpEsitoByCodiFkIdEsito1() != null) && (relAttoMisPers.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito() > 0)) {
					q.setParameter("idEsito1", relAttoMisPers.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito());
					System.out.println("idEsito1: " + relAttoMisPers.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito());
				} else {
					q.setParameter("idEsito1", null, Hibernate.LONG);
				}
				if ((relAttoMisPers != null) && (relAttoMisPers.getTTpEsitoByCodiFkIdEsito2() != null) && (relAttoMisPers.getTTpEsitoByCodiFkIdEsito2().getCodiKIdEsito() > 0)) {
					q.setParameter("idEsito2", relAttoMisPers.getTTpEsitoByCodiFkIdEsito2().getCodiKIdEsito());
					System.out.println("idEsito2: " + relAttoMisPers.getTTpEsitoByCodiFkIdEsito2().getCodiKIdEsito());
				} else {
					q.setParameter("idEsito2", null, Hibernate.LONG);
				}
				if ((relAttoMisPers != null) && (relAttoMisPers.getTTpEsitoByCodiFkIdEsito3() != null) && (relAttoMisPers.getTTpEsitoByCodiFkIdEsito3().getCodiKIdEsito() > 0)) {
					q.setParameter("idEsito3", relAttoMisPers.getTTpEsitoByCodiFkIdEsito3().getCodiKIdEsito());
					System.out.println("idEsito3: " + relAttoMisPers.getTTpEsitoByCodiFkIdEsito3().getCodiKIdEsito());
				} else {
					q.setParameter("idEsito3", null, Hibernate.LONG);
				}
				if ((relAttoMisPers != null) && (relAttoMisPers.getTTpEsitoFinale() != null) && (relAttoMisPers.getTTpEsitoFinale().getCodiKIdEsitoFin() > 0)) {
					q.setParameter("idEsitoFin", relAttoMisPers.getTTpEsitoFinale().getCodiKIdEsitoFin());
					System.out.println("idEsitoFin: " + relAttoMisPers.getTTpEsitoFinale().getCodiKIdEsitoFin());
				} else {
					q.setParameter("idEsitoFin", null, Hibernate.LONG);
				}
				q.setParameter("descAltro1", relAttoMisPers.getDescAltro1());
				q.setParameter("descAltro2", relAttoMisPers.getDescAltro2());
				q.setParameter("descAltro3", relAttoMisPers.getDescAltro3());
				q.setParameter("flgValido", "S");
				q.setParameter("codiMotivo", relAttoMisPers.getTTpTipoMotivo());
				System.out.println("codiMotivo: " + relAttoMisPers.getTTpTipoMotivo());
				if (relAttoMisPers.getTAtto() != null && relAttoMisPers.getTAtto().getTTpTipoAtto() != null) {
					q.setParameter("idTipoAtto", relAttoMisPers.getTAtto().getTTpTipoAtto(), Hibernate.LONG);
					System.out.println("idTipoAtto: " + relAttoMisPers.getTAtto().getTTpTipoAtto());
				} else {
					q.setParameter("idTipoAtto", 9L, Hibernate.LONG);
					System.out.println("idTipoAtto: " + 9L);
				}
				
				q.setParameter("descUtente", vcodUtente, Hibernate.STRING);
				q.setParameter("idPrescrizioni", idPrescrizioni);
				System.out.println("idPrescrizioni: " + idPrescrizioni);
				System.out.println("importoCauzione: " + importoCauzione);
				if (importoCauzione != null) {
					q.setParameter("importoCauzione", importoCauzione, Hibernate.BIG_DECIMAL);
					System.out.println("importoCauzione: " + importoCauzione);
				} else {
					q.setParameter("importoCauzione", null, Hibernate.BIG_DECIMAL);
				}
				q.setParameter("articolo9c1", articolo9c1);// -- valori ammessi S  o N	
				System.out.println("articolo9c1: " + articolo9c1);
				
				
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
				
				
			}
		});
	}		
	
    public Long inviaAttoDecretoSequestroSospensioneTemporanea(final long idUfficio, final long idProcedimento, final TAtto atto, final String codUtente, final long tipoAtto,final long categoriaAtto, final long idStatoAtto, final String nomeMetodo) {

        return (Long) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

            	/* 
				 * ISSUE MEV : Specifiche Intervento-006-SpeseProces-NumAtto
				 * Numero MEV : 006
				 * Autore    : Gioggi
				 * Data      : 18 apr 2020
				 * Branch    : MEV_006
				 */
				Query q;
				if (esisteNumAtto(atto.getId().getCodiKIdAtto(), idUfficio))
					q = session.getNamedQuery("PG_NUMERO_ATTO_generaFlussoDopoNumeroAttoDSST");
				else
					q = session.getNamedQuery("PG_ATTO_inviaDecretoSequestroSospensioneTemporanea");
				//***** FINE INTERVENTO MEV_006 *****//

                q.setParameter("idUffOwn", idUfficio);
                q.setParameter("idProc", idProcedimento, Hibernate.LONG);
                if (atto.getDataRegistrazione() != null) {
                    //q.setParameter("idMittente", idUfficio, Hibernate.LONG);
                    //q.setParameter("idDestinatario", idDestinatario, Hibernate.LONG);
                    q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
                } else {
                    //q.setParameter("idMittente", null, Hibernate.LONG);
                    //q.setParameter("idDestinatario", null, Hibernate.LONG);
                    //q.setParameter("idUltDestinatario", null, Hibernate.LONG);
                    q.setParameter("dataRegistrazione", null, Hibernate.DATE);
                }
                if (atto.getDataEmissione() != null) {
                    q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
                } else {
                    q.setParameter("dataEmissione", null, Hibernate.DATE);
                }
                if (atto.getDataDeposito() != null) {
                    q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
                } else {
                    q.setParameter("dataDeposito", null, Hibernate.DATE);
                }
                if (atto.getFileDocumento() != null) {
                    q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
                    q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
                    q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
                } else {
                    q.setParameter("documento", null, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", null, Hibernate.STRING);
                    q.setParameter("nomeDocumento", null, Hibernate.STRING);
                }
                if (atto.getDataRicezione() != null) {
                    q.setParameter("dataRicezioneDoc", atto.getDataRicezione(), Hibernate.DATE);
                } else {
                    q.setParameter("dataRicezioneDoc", null, Hibernate.DATE);
                }
                q.setParameter("descUtente", codUtente);
                q.setParameter("idAtto", atto.getId().getCodiKIdAtto(), Hibernate.LONG);
                q.setParameter("tipoAtto", tipoAtto, Hibernate.LONG);
                q.setParameter("categoriaAtto", categoriaAtto, Hibernate.LONG);
                q.setParameter("idStatoAtto", idStatoAtto, Hibernate.LONG);
                q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                q.list();
                return atto.getId().getCodiKIdAtto();
            }
        });
    }
    
    public List<TAtto> getAttiDecretoApplicazioneProvvisoria(final long idProcedimento, final long idUffOwn) {    

        return (List<TAtto>)getHibernateTemplate().execute(new HibernateCallback() {

            public List<TAtto> doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_ATTO_listaAttiApplicazioneProvvisoria");
                q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
                q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
                
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                return q.list();
            }
        });
    }	 

	public long inserisciAttoDecretoRevocaApplicazioneProvvisoria(final int idUffOwn, final long idProcedimento, final String codiceUtente, final TAtto atto, final long idStatoAtto, final long idAttoOrigine, final TDecreto decreto, final String nomeMetodo) {

        return (Long)getHibernateTemplate().execute(new HibernateCallback() {

            public Long doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_ATTO_inserisciAttoDecretoRevocaApplicazioneProvvisoria"); 
                q.setParameter("idUffOwn", idUffOwn);
                q.setParameter("descUtente", codiceUtente);
                q.setParameter("idProcedimento", idProcedimento);
                if ((atto.getTTpTipoAtto() != null) && (atto.getTTpTipoAtto().getCodiKIdTipoAtto() > 0)) {
                    q.setParameter("idTipoAtto", atto.getTTpTipoAtto().getCodiKIdTipoAtto());
                } else {
                    q.setParameter("idTipoAtto", null, Hibernate.LONG);
                }
                q.setParameter("idAttoOrigine", idAttoOrigine, Hibernate.LONG);
                if (atto.getTTpCatAtto().getCodiKIdCatAtto() > 0) {
                    q.setParameter("idCatAtto", atto.getTTpCatAtto().getCodiKIdCatAtto());
                } else {
                    q.setParameter("idCatAtto", null, Hibernate.LONG);
                }
                if (decreto != null && decreto.getTUdienza() != null && decreto.getTUdienza().getId() != null && decreto.getTUdienza().getId().getCodiKIdUdienza() > 0) {
                    q.setParameter("idUdienza", decreto.getTUdienza().getId().getCodiKIdUdienza());
                } else {
                    q.setParameter("idUdienza", null, Hibernate.LONG);
                }
                if (idStatoAtto > 0) {
                    q.setParameter("idStatoAtto", idStatoAtto);
                } else {
                    q.setParameter("idStatoAtto", null, Hibernate.LONG);
                }
                if (atto.getDataEmissione() != null) {
                    q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
                } else {
                    q.setParameter("dataEmissione", null, Hibernate.DATE);
                }
                if (atto.getDataDeposito() != null) {
                    q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
                } else {
                    q.setParameter("dataDeposito", null, Hibernate.DATE);
                }
                if (atto.getDataRegistrazione() != null) {
                    q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
                } else {
                    q.setParameter("dataRegistrazione", null, Hibernate.DATE);
                }
                if (atto.getFileDocumento() != null) {
                    q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
                    q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
                    q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
                } else {
                    q.setParameter("documento", null, Hibernate.BLOB);
                    q.setParameter("tipoDocumento", null, Hibernate.STRING);
                    q.setParameter("nomeDocumento", null, Hibernate.STRING);
                }
                q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List l = q.list();
                HashMap first = (HashMap) l.get(0);
                Long id = (Long) first.get("IDATTO");
                return id;
            }
        });
    }
	
	public TSoggetto getTSoggettoById(long codiKIdSoggInt, int codiKIdUffOwner) {
		return getHibernateTemplate().get(TSoggetto.class, new TSoggettoId(codiKIdUffOwner, codiKIdSoggInt));
	}

	public long salvaIstanzaFascicoloGestione(final int idUffOwn, final long idProcedimento, final Long idAmministratore, final String codiceUtente, 
			final long statoAtto, final long tipoAtto, final long categoriaAtto, final TAtto atto,
			final TIstanza istanza, final String nomeMetodo) {
		
		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Long idAtto = null;
				Query q = session.getNamedQuery("PG_ATTO_salvaIstanzaFascicoloGestione");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("idProc", idProcedimento, Hibernate.LONG);
				
				if(idAmministratore!=null) {
					q.setParameter("idAmministratore", idAmministratore, Hibernate.LONG);
				} else {
					q.setParameter("idAmministratore", null, Hibernate.LONG);
				}
				
				q.setParameter("codAtto", atto.getId().getCodiKIdAtto(), Hibernate.LONG);
				q.setParameter("statoAtto", statoAtto, Hibernate.LONG);
				q.setParameter("idTipoAtto", tipoAtto, Hibernate.LONG); 
				q.setParameter("idCategoriaAtto", categoriaAtto, Hibernate.LONG);
				q.setParameter("dataRicezione", atto.getDataRicezione(), Hibernate.DATE);
				q.setParameter("note", istanza.getDescRichiesta(), Hibernate.STRING);
				
				if (istanza.getDescNumeRif() != null) {
					q.setParameter("numeroRiferimento", istanza.getDescNumeRif(), Hibernate.STRING);
				} else {
					q.setParameter("numeroRiferimento", null, Hibernate.STRING);
				}
				
				q.setParameter("tipoProponente", istanza.getDescTipoProp(), Hibernate.STRING);
				q.setParameter("cognomeProp", istanza.getDescCognProp(), Hibernate.STRING);
				q.setParameter("nomeProp", istanza.getDescNomeProp(), Hibernate.STRING);
				q.setParameter("descrEnteAltro", istanza.getDescAltroOrgano(), Hibernate.STRING);
				
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				
				if(istanza.getDataSpedizione()!=null) {
					q.setParameter("dalPeriodoRif", istanza.getDataSpedizione(), Hibernate.DATE);
				} else {
					q.setParameter("dalPeriodoRif", null, Hibernate.DATE);
				}
				if(istanza.getDataPervIst()!=null) {
					q.setParameter("alPeriodoRif", istanza.getDataPervIst(), Hibernate.DATE);
				} else {
					q.setParameter("alPeriodoRif", null, Hibernate.DATE);
				}				
                				
				q.setParameter("descUtente", codiceUtente, Hibernate.STRING);
				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				Iterator iter = l.iterator();
				while (iter.hasNext()) {
					Map map = (Map) iter.next();
					idAtto = (Long) map.get("IDATTO");
				}
				return idAtto;
			}
		});
	}
	
	
	
	
	
	public List<RicercaStralcioDto> getRicercaMisureIstanza(final long idAtto, final long idProcedimento, final long idUfficio,
			final String flgValido, final int categoriaAtto, final int stato) {

		return (List<RicercaStralcioDto>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_ricercaMisureIstanza");

				if (idAtto > 0) {
					q.setParameter("idAtto", idAtto, Hibernate.LONG);
				} else {
					q.setParameter("idAtto", 0, Hibernate.LONG);
				}

				if (idProcedimento > 0) {
					q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				} else {
					q.setParameter("idProcedimento", 0, Hibernate.LONG);
				}
				if (idUfficio > 0) {
					q.setParameter("idUfficio", idUfficio, Hibernate.LONG);
				} else {
					q.setParameter("idUfficio", 0, Hibernate.LONG);
				}
				
				q.setParameter("idsMis", null, Hibernate.STRING);
				q.setParameter("idsSoggInt", null, Hibernate.STRING);
				q.setParameter("idsSoggTit", null, Hibernate.STRING);

				if (flgValido != null) {
					q.setParameter("flgValido", flgValido, Hibernate.STRING);
				} else {
					q.setParameter("flgValido", null, Hibernate.STRING);
				}

				q.setParameter("categoriaAtto", categoriaAtto, Hibernate.INTEGER);

				q.setParameter("stato", stato, Hibernate.INTEGER);

				q.setResultTransformer(Transformers.aliasToBean(RicercaStralcioDto.class));
				return q.list();
			}
		});
	}
	public List<SogIntDettaglioProcedimentoDto> getRicercaSoggettoIstanza(final long idProcedimento, final long idatto, final int iduff,
			final int descRuolo) {

		return (List<SogIntDettaglioProcedimentoDto>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_ricercaSoggettoIstanza");

				q.setParameter("idProcedimento", idProcedimento);
				q.setParameter("idatto", idatto);
				q.setParameter("iduff", iduff);

				if (descRuolo >= 0) {
					q.setParameter("descruolo", descRuolo);
				} else {
					q.setParameter("descruolo", null, Hibernate.INTEGER);
				}

				

				q.setResultTransformer(Transformers.aliasToBean(SogIntDettaglioProcedimentoDto.class));
				return q.list();
			}
		});
	}
	
	
	public List<TAvvocati> getRicercaAvvIstanza(final long idProcedimento, final long idatto, final int iduff) {
		return (List<TAvvocati>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_ricercaAvvIstanza");
				q.setParameter("idProcedimento", idProcedimento);
				q.setParameter("idatto", idatto);
				q.setParameter("iduff", iduff);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				return q.list();
				
			}
		});
	}	
	
	
	
	public Character impostaIstanzaMisPatr(final long idProcedimento, final long idAtto, final long idUffOwner, final boolean checkedSoggValido,
			final long idSoggInt, final long idBene,final String idSoggTit,
			final String codiceUtente, final int catAtto) {
		return (Character) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_imposta_istanza_mis_patr");

				if (idProcedimento > 0) {
					q.setParameter("VID_PROCEDIMENTO", idProcedimento, Hibernate.LONG);
				} else {
					q.setParameter("VID_PROCEDIMENTO", 0, Hibernate.LONG);
				}

				if (idAtto > 0) {
					q.setParameter("VID_ATTO", idAtto, Hibernate.LONG);
				} else {
					q.setParameter("VID_ATTO", 0, Hibernate.LONG);
				}

				if (idUffOwner > 0) {
					q.setParameter("VID_UFF_OWNER", idUffOwner, Hibernate.LONG);
				} else {
					q.setParameter("VID_UFF_OWNER", 0, Hibernate.LONG);
				}

				if (idBene > 0) {
					q.setParameter("VID_BENE", idBene, Hibernate.LONG);
				} else {
					q.setParameter("VID_BENE", null, Hibernate.LONG);
				}

				if (idSoggInt > 0) {
					q.setParameter("VID_SOGG_INT", idSoggInt, Hibernate.LONG);
				} else {
					q.setParameter("VID_SOGG_INT", null, Hibernate.LONG);
				}

				if (idSoggTit != null && !"0".equals(idSoggTit)) {
					q.setParameter("VID_SOGG_TIT", idSoggTit, Hibernate.STRING);
				} else {
					q.setParameter("VID_SOGG_TIT", null, Hibernate.STRING);
				}

				if (checkedSoggValido) {
					q.setParameter("VFLG_VALIDO", 'R');
				} else {
					q.setParameter("VFLG_VALIDO", 'S', Hibernate.CHARACTER);
				}

				q.setParameter("IJ_UTENTE", codiceUtente, Hibernate.STRING);

				q.setParameter("VID_CAT_ATTO", catAtto, Hibernate.INTEGER);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				Character upd = null;

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List list = q.list();
				Iterator it = list.iterator();
				while (it.hasNext()) {
					Map<String, Object> row = (Map<String, Object>) it.next();
					upd = (Character) row.get("UPD");
				}
				return upd;

			}
		});
	}
	
	public Character impostaIstanzaSoggetto(final long idProcedimento, final long idAtto, final long idUffOwner, final boolean checkedSoggValido,
			final long idSoggInt,final String codiceUtente, final int catAtto) {
		return (Character) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_imposta_istanza_soggetto");

				if (idProcedimento > 0) {
					q.setParameter("VID_PROCEDIMENTO", idProcedimento, Hibernate.LONG);
				} else {
					q.setParameter("VID_PROCEDIMENTO", 0, Hibernate.LONG);
				}
				if (idProcedimento > 0) {
					q.setParameter("VID_ATTO", idAtto, Hibernate.LONG);
				} else {
					q.setParameter("VID_ATTO", 0, Hibernate.LONG);
				}

				if (idUffOwner > 0) {
					q.setParameter("VID_UFF_OWNER", idUffOwner, Hibernate.LONG);
				} else {
					q.setParameter("VID_UFF_OWNER", 0, Hibernate.LONG);
				}

				if (idSoggInt > 0) {
					q.setParameter("VID_SOGG_INT", idSoggInt, Hibernate.LONG);
				} else {
					q.setParameter("VID_SOGG_INT", null, Hibernate.LONG);
				}

				// /
				q.setParameter("VID_BENE", null, Hibernate.STRING);
				
				
				q.setParameter("VID_SOGG_TIT",null, Hibernate.STRING);
			
				q.setParameter("VIDS_MIS", null, Hibernate.STRING);
				
				
				
			
				if (checkedSoggValido) {
					q.setParameter("VFLG_VALIDO", 'R');
				} else {
					q.setParameter("VFLG_VALIDO", 'S', Hibernate.CHARACTER);
				}
				q.setParameter("IJ_UTENTE", codiceUtente, Hibernate.STRING);

				q.setParameter("VID_CAT_ATTO", catAtto, Hibernate.INTEGER);

				Character upd = null;

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List list = q.list();
				Iterator it = list.iterator();
				while (it.hasNext()) {
					Map<String, Object> row = (Map<String, Object>) it.next();
					upd = (Character) row.get("UPD");
				}
				return upd;

			}
		});
	}

	public Character impostaIstanzaMisPers(final long idProcedimento, final long idAtto, final long idUffOwner, final boolean checkedSoggValido,
			final long idMisuraPers, final long idSoggInt, final String codiceUtente, final int catAtto) {
		return (Character) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_imposta_ricorso_mis_pers");

				if (idProcedimento > 0) {
					q.setParameter("VID_PROCEDIMENTO", idProcedimento, Hibernate.LONG);
				} else {
					q.setParameter("VID_PROCEDIMENTO", 0, Hibernate.LONG);
				}
				if (idAtto > 0) {
					q.setParameter("VID_ATTO", idAtto, Hibernate.LONG);
				} else {
					q.setParameter("VID_ATTO", 0, Hibernate.LONG);
				}

				if (idUffOwner > 0) {
					q.setParameter("VID_UFF_OWNER", idUffOwner, Hibernate.LONG);
				} else {
					q.setParameter("VID_UFF_OWNER", 0, Hibernate.LONG);
				}

				if (idSoggInt > 0) {
					q.setParameter("VID_SOGG_INT", idSoggInt, Hibernate.LONG);
				} else {
					q.setParameter("VID_SOGG_INT", null, Hibernate.LONG);
				}

				if (idMisuraPers > 0) {
					q.setParameter("VID_MISURA_PERS", idMisuraPers, Hibernate.LONG);
				} else {
					q.setParameter("VID_MISURA_PERS", null, Hibernate.LONG);
				}
				if (checkedSoggValido) {
					q.setParameter("VFLG_VALIDO", 'R');
				} else {
					q.setParameter("VFLG_VALIDO", 'S', Hibernate.CHARACTER);
				}
				q.setParameter("IJ_UTENTE", codiceUtente, Hibernate.STRING);

				q.setParameter("VID_CAT_ATTO", catAtto, Hibernate.INTEGER);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				Character upd = null;

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List list = q.list();
				Iterator it = list.iterator();
				while (it.hasNext()) {
					Map<String, Object> row = (Map<String, Object>) it.next();
					upd = (Character) row.get("UPD");
				}
				return upd;

			}
		});
	}

	
  /**
   * getTRelSoggImpByIdImpugnazioneIdOwner
   * @param idImpugnazione
   * @param IdUffOwner
   * @return TRelSoggImp
   */
	public TRelSoggImp getTRelSoggImpByIdImpugnazioneIdOwner(long idImpugnazione,int IdUffOwner) {

		List<TRelSoggImp> lista = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelSoggImp.class);
		criteria.add(Restrictions.eq("id.codiKIdImpugnaz", idImpugnazione));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", IdUffOwner));	
		lista = getHibernateTemplate().findByCriteria(criteria);
		if (lista.size() > 0) {
			TRelSoggImp soggImp = lista.get(0);
			return soggImp;
		} else
			return null;
	}

	/**
	   * getTRelSoggImpByIdImpugnazioneIdOwner
	   * @param idImpugnazione
	   * @param IdUffOwner
	   * @return TRelSoggImp
	   */
		public TRelSoggIst getTRelSoggIstByIdIstanzaIdOwner(long idIstanza,int IdUffOwner) {

			List<TRelSoggIst> lista = null;
			DetachedCriteria criteria = DetachedCriteria.forClass(TRelSoggIst.class);
			criteria.add(Restrictions.eq("id.codiKIdIstanza", idIstanza));
			criteria.add(Restrictions.eq("id.codiKIdUffOwner", IdUffOwner));	
			lista = getHibernateTemplate().findByCriteria(criteria);
			if (lista.size() > 0) {
				TRelSoggIst soggIst = lista.get(0);
				return soggIst;
			} else
				return null;
		}
		
		public TRelSoggImp getTRelSoggImpByIdIstanzaIdOwner(long idImpugnazione,int IdUffOwner) {

			List<TRelSoggImp> lista = null;
			DetachedCriteria criteria = DetachedCriteria.forClass(TRelSoggImp.class);
			criteria.add(Restrictions.eq("id.codiKIdImpugnaz", idImpugnazione));
			criteria.add(Restrictions.eq("id.codiKIdUffOwner", IdUffOwner));	
			lista = getHibernateTemplate().findByCriteria(criteria);
			if (lista.size() > 0) {
				TRelSoggImp soggIst = lista.get(0);
				return soggIst;
			} else
				return null;
		}		
	
	
	public List<SogIntDettaglioProcedimentoDto> getSoggettiDifensoriRicorso(final long idProc, final long idImpugnazione,final long idUfficio, final long idAvvocato, final String cognomeAvv,final String nomeAvv,final String descUtente) {

		return (List<SogIntDettaglioProcedimentoDto>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_elencoSoggettiDifensoriRicorso");
				q.setParameter("ID_PROC", idProc);
				q.setParameter("ID_IMPUGNAZIONE", idImpugnazione);
				q.setParameter("IDUFFOWN", idUfficio);
				q.setParameter("IDAVVOCATO", idAvvocato);
				q.setParameter("COGNOME_AVV", cognomeAvv);
				q.setParameter("NOME_AVV", nomeAvv);
				q.setParameter("DESCUTENTE", descUtente);
				q.setResultTransformer(Transformers.aliasToBean(SogIntDettaglioProcedimentoDto.class));
				return q.list();
			}
		});
	}	
	
	public List<SogIntDettaglioProcedimentoDto> getSoggettiDifensoriIstanza(final long idImpugnazione,final long idUfficio, final long idAvvocato, final String cognomeAvv,final String nomeAvv,final String descUtente) {

		return (List<SogIntDettaglioProcedimentoDto>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_elencoSoggettiDifensoriIstanza");
				q.setParameter("ID_ISTANZA", idImpugnazione);
				q.setParameter("IDUFFOWN", idUfficio);
				q.setParameter("IDAVVOCATO", idAvvocato);
				q.setParameter("COGNOME_AVV", cognomeAvv);
				q.setParameter("NOME_AVV", nomeAvv);
				q.setParameter("DESCUTENTE", descUtente);
				q.setResultTransformer(Transformers.aliasToBean(SogIntDettaglioProcedimentoDto.class));
				return q.list();
			}
		});
	}	
	
	public TNotifRicevute caricaNotificatoDaRicevuta(long codAtto, int idUfficio) {
		/*List<TNotifiche> lista = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TNotifiche.class);
		criteria.setFetchMode("TNotifRichiestes", FetchMode.JOIN);
		criteria.setFetchMode("TNotifRichiestes.TNotifDests", FetchMode.JOIN);
		criteria.createAlias("TNotifRichiestes", "richieste");
		criteria.createAlias("TNotifRichiestes.TNotifDests", "dest");
		criteria.createAlias("TNotifRichiestes.TNotifDests.TNotifRicevutes", "ricevute");
		criteria.add(Restrictions.eq("ricevute.codiFkIdAttoRicevuta", codAtto));
		criteria.add(Restrictions.eq("ricevute.codiFkIdUffOwnerRicevuta", idUfficio));
		*/
		DetachedCriteria criteria = DetachedCriteria.forClass(TNotifRicevute.class);
		criteria.setFetchMode("TNotifDest", FetchMode.JOIN);
		criteria.setFetchMode("TNotifRichieste", FetchMode.JOIN);
		criteria.add(Restrictions.eq("codiFkIdAttoRicevuta", codAtto));
		criteria.add(Restrictions.eq("codiFkIdUffOwnerRicevuta", idUfficio));

		List<TNotifRicevute> lista = null;
		
		lista = getHibernateTemplate().findByCriteria(criteria);
		if (lista.size() > 0) {
			lista.get(0).getTNotifDest().getTNotifRichieste().getTNotifiche().getCodiFkIdAtto();
			return lista.get(0);
		}
		return null;
	}
	
	public TNotifDest caricaNotificatoDaRapporto(long codAtto, int idUfficio) {
		/*List<TNotifiche> lista = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TNotifiche.class);
		criteria.setFetchMode("TNotifRichiestes", FetchMode.JOIN);
		criteria.setFetchMode("TNotifRichiestes.TNotifDests", FetchMode.JOIN);
		criteria.createAlias("TNotifRichiestes", "richieste");
		criteria.createAlias("TNotifRichiestes.TNotifDests", "dest");
		criteria.createAlias("TNotifRichiestes.TNotifDests.TNotifRicevutes", "ricevute");
		criteria.add(Restrictions.eq("ricevute.codiFkIdAttoRicevuta", codAtto));
		criteria.add(Restrictions.eq("ricevute.codiFkIdUffOwnerRicevuta", idUfficio));
		*/
		DetachedCriteria criteria = DetachedCriteria.forClass(TNotifDest.class);
		criteria.setFetchMode("TNotifRichieste", FetchMode.JOIN);
		criteria.add(Restrictions.eq("codiFkAttoRapporto", codAtto));
		
		List<TNotifDest> lista = null;
		
		lista = getHibernateTemplate().findByCriteria(criteria);
		for (TNotifDest tNotifDest : lista) {
			if (tNotifDest.getTNotifRichieste().getTNotifiche().getCodiFkIdUffOwner() == idUfficio)
				return tNotifDest;
		}
		return null;
	}
	
	
	
	public long inserisciProvvedimentoFascicoloGestione(final int idUffOwn, final long idProcedimento, final String codiceUtente,
			final TAtto atto, final long idStatoAtto, final long idAttoOrigine, final TDecreto decreto, final String nomeMetodo) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_inserisciProvvedimentoFascicoloGestione");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("descUtente", codiceUtente);
				q.setParameter("idProcedimento", idProcedimento);
				if ((atto.getTTpTipoAtto() != null) && (atto.getTTpTipoAtto().getCodiKIdTipoAtto() > 0)) {
					q.setParameter("idTipoAtto", atto.getTTpTipoAtto().getCodiKIdTipoAtto());
				} else {
					q.setParameter("idTipoAtto", null, Hibernate.LONG);
				}
				q.setParameter("idAttoOrigine", idAttoOrigine, Hibernate.LONG);
				if (atto.getTTpCatAtto().getCodiKIdCatAtto() > 0) {
					q.setParameter("idCatAtto", atto.getTTpCatAtto().getCodiKIdCatAtto());
				} else {
					q.setParameter("idCatAtto", null, Hibernate.LONG);
				}
				if (decreto != null && decreto.getTUdienza() != null && decreto.getTUdienza().getId() != null
						&& decreto.getTUdienza().getId().getCodiKIdUdienza() > 0) {
					q.setParameter("idUdienza", decreto.getTUdienza().getId().getCodiKIdUdienza());
				} else {
					q.setParameter("idUdienza", null, Hibernate.LONG);
				}
				if (idStatoAtto > 0) {
					q.setParameter("idStatoAtto", idStatoAtto);
				} else {
					q.setParameter("idStatoAtto", null, Hibernate.LONG);
				}
				if (atto.getDataEmissione() != null) {
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				} else {
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				}
				if (atto.getDataDeposito() != null) {
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				} else {
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				}
				if (atto.getDataRegistrazione() != null) {
					q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				} else {
					q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				}
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long id = (Long) first.get("IDATTO");
				return id;
			}
		});
	}

	public long salvaProvvedimentoFascicoloGestione(final int idUffOwn, final long idProcedimento, final long idAtto, final String codiceUtente, final TAtto atto, final long idStatoAtto, final TProvFg provv, final String nomeMetodo) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_salvaProvvedimentoFascicoloGestione");
				
				q.setParameter("IDATTO"           ,idAtto);
                q.setParameter("IDUFFOWN"         ,idUffOwn);
                q.setParameter("IDPROC"           ,idProcedimento);
                q.setParameter("NOTE"             ,atto.getDescNote() ,Hibernate.STRING);
                q.setParameter("DESCUTENTE"       ,codiceUtente, Hibernate.STRING);
                
                if (atto.getDataEmissione() != null) {
                	q.setParameter("DATAEMISSIONE"    ,atto.getDataEmissione() ,Hibernate.DATE);
                } else {
                	q.setParameter("DATAEMISSIONE"    ,null ,Hibernate.DATE);
                }
                
                if (atto.getDataDeposito() != null) {
                	q.setParameter("DATADEPOSITO"     ,atto.getDataDeposito() ,Hibernate.DATE);
                } else {
                	q.setParameter("DATADEPOSITO"     ,null ,Hibernate.DATE);
                }
                
                if(provv.getDataUdienza()!=null) {
                	q.setParameter("DATAUDIENZA"     ,provv.getDataUdienza() ,Hibernate.DATE);
                } else {
                	q.setParameter("DATAUDIENZA"     ,null ,Hibernate.DATE);
                }

                if(provv.getDescEsito()!=null) {
                	q.setParameter("DESCESITO"     ,provv.getDescEsito() ,Hibernate.STRING);
                } else {
                	q.setParameter("DESCESITO"     ,null ,Hibernate.STRING);
                }
				
                if(provv.getEsito()!=null && !provv.getEsito().equals(Constants.NO_ITEM_SELECTED)) {
                	q.setParameter("VESITO"     ,provv.getEsito() ,Hibernate.STRING);
                } else {
                	q.setParameter("VESITO"     ,null ,Hibernate.STRING);
                }
                
                if(provv.getFlgImp()!=null && !provv.getFlgImp().equals(Constants.NO_ITEM_SELECTED)) {
                	q.setParameter("FLG_IMP"     ,provv.getFlgImp() ,Hibernate.STRING);
                } else {
                	q.setParameter("FLG_IMP"     ,null ,Hibernate.STRING);
                }

                if(provv.getDescEsitoImp()!=null) {
                	q.setParameter("DESC_ESITO_IMP"     ,provv.getDescEsitoImp() ,Hibernate.STRING);
                } else {
                	q.setParameter("DESC_ESITO_IMP"     ,null ,Hibernate.STRING);
                }
                
                if(provv.getFlgCas()!=null && !provv.getFlgCas().equals(Constants.NO_ITEM_SELECTED)) {
                	q.setParameter("FLG_CAS"     ,provv.getFlgCas() ,Hibernate.STRING);
                } else {
                	q.setParameter("FLG_CAS"     ,null ,Hibernate.STRING);
                }               

                if(provv.getDescEsitoCas()!=null) {
                	q.setParameter("DESC_ESITO_CAS"     ,provv.getDescEsitoCas() ,Hibernate.STRING);
                } else {
                	q.setParameter("DESC_ESITO_CAS"     ,null ,Hibernate.STRING);
                }     
                
                if(provv.getDescNote()!=null) {
                	q.setParameter("DESCNOTE"     ,provv.getDescNote() ,Hibernate.STRING);
                } else {
                	q.setParameter("DESCNOTE"     ,null ,Hibernate.STRING);
                }     
                
                if (atto.getFileDocumento() != null) {
					q.setParameter("DOCUMENTO"    , atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("TIPODOCUMENTO", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("NOMEDOCUMENTO", atto.getDescNomeDocumento(), Hibernate.STRING);	                
                } else {
	                q.setParameter("DOCUMENTO"        ,null ,Hibernate.BLOB);
	                q.setParameter("TIPODOCUMENTO"    ,null ,Hibernate.STRING);
	                q.setParameter("NOMEDOCUMENTO"    ,null ,Hibernate.STRING);                	
                }
                if (atto.getDataRegistrazione() != null) {
                	q.setParameter("DATAREGISTRAZIONE" ,atto.getDataRegistrazione() ,Hibernate.DATE);
                } else {
                	q.setParameter("DATAREGISTRAZIONE" ,null ,Hibernate.DATE);
                }
                
                if (idStatoAtto > 0) {
                	q.setParameter("IDSTATOATTO"      ,idStatoAtto ,Hibernate.LONG);
                } else {
                	q.setParameter("IDSTATOATTO"      ,null ,Hibernate.LONG);
                }

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long id = (Long) first.get("IDATTO");
				return id;
			}
		});
	}	

	public long inserisciOrdinanzaAntimafia(final int idUffOwn, final long idProcedimento, final String codiceUtente, final int idTipoAtto,
			final int idCatAtto, final int idStatoAtto, final String nomeMetodo,final long idSoggetto) {
		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_inserisciOrdinanzaAntimafia");
				q.setParameter("idUffOwn", idUffOwn, Hibernate.INTEGER);
				q.setParameter("descUtente", codiceUtente, Hibernate.STRING);
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setParameter("idTipoAtto", idTipoAtto, Hibernate.INTEGER);
				q.setParameter("idCatAtto", idCatAtto, Hibernate.INTEGER);
				q.setParameter("idStatoAtto", idStatoAtto, Hibernate.INTEGER);
				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
				q.setParameter("idSoggetto", idSoggetto, Hibernate.LONG);
				
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long idAtto = (Long) first.get("IDCURRENTATTO");

				return idAtto;
			}
		});
	}

	/**
	 * getSogAntimafia
	 * @param idUffOwn
	 * @param idProc
	 * @return List<SogIntDettaglioProcedimentoDto>
	 */
	public List<SogIntDettaglioProcedimentoDto> getSogAntimafia(final int idUffOwn, final Long idProc,final Long idAtto)
	{
		 return (List<SogIntDettaglioProcedimentoDto>)getHibernateTemplate().execute( new HibernateCallback()
	        {
	            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
	            {
	                Query q = session.getNamedQuery( "PG_ATTO_sogAntimafia" );
	                q.setParameter( "IDPROC", idProc, Hibernate.LONG );
	                q.setParameter( "IDUFFOWN", idUffOwn, Hibernate.INTEGER );
	                q.setParameter( "IDATTO", idAtto, Hibernate.LONG );
	                
	                q.setResultTransformer(Transformers.aliasToBean(SogIntDettaglioProcedimentoDto.class));
					return q.list();
	            }
	        } );
	} 	
	
	public TRelProcSogintAtto getTRelProcSogintAttoById(TRelProcSogintAttoId id) {

		List<TRelProcSogintAtto> lista = null;
		TRelProcSogintAtto sogintAtto = new TRelProcSogintAtto();
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelProcSogintAtto.class);
		criteria.add(Restrictions.eq("id", id));
		lista = getHibernateTemplate().findByCriteria(criteria);
		if (lista.size() > 0) {
			sogintAtto = lista.get(0);
		}
		return sogintAtto;
	}	

	public long inserisciProvvedimentoRevocaAmministratori(final int idUffOwn, final long idProcedimento, final String codiceUtente,
			final TAtto atto, final long idStatoAtto, final TDecreto decreto, final String nomeMetodo, final Long idAttoOrigine) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_inserisciProvvedimentoRevocaAmministratori");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("descUtente", codiceUtente);
				q.setParameter("idProcedimento", idProcedimento);
				if ((atto.getTTpTipoAtto() != null) && (atto.getTTpTipoAtto().getCodiKIdTipoAtto() > 0)) {
					q.setParameter("idTipoAtto", atto.getTTpTipoAtto().getCodiKIdTipoAtto());
				} else {
					q.setParameter("idTipoAtto", null, Hibernate.LONG);
				}
				if (atto.getTTpCatAtto().getCodiKIdCatAtto() > 0) {
					q.setParameter("idCatAtto", atto.getTTpCatAtto().getCodiKIdCatAtto());
				} else {
					q.setParameter("idCatAtto", null, Hibernate.LONG);
				}
				if (decreto != null && decreto.getTUdienza() != null && decreto.getTUdienza().getId() != null
						&& decreto.getTUdienza().getId().getCodiKIdUdienza() > 0) {
					q.setParameter("idUdienza", decreto.getTUdienza().getId().getCodiKIdUdienza());
				} else {
					q.setParameter("idUdienza", null, Hibernate.LONG);
				}
				if (idStatoAtto > 0) {
					q.setParameter("idStatoAtto", idStatoAtto);
				} else {
					q.setParameter("idStatoAtto", null, Hibernate.LONG);
				}
				if (atto.getDataEmissione() != null) {
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				} else {
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				}
				if (atto.getDataDeposito() != null) {
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				} else {
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				}
				if (atto.getDataRegistrazione() != null) {
					q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				} else {
					q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				}
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
				
				if(idAttoOrigine!=null) {
					q.setParameter("idAttoOrigine", idAttoOrigine, Hibernate.LONG);
				} else {
					q.setParameter("idAttoOrigine", null, Hibernate.LONG);
				}
				
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long id = (Long) first.get("IDATTO");
				return id;
			}
		});
	}

	public long salvaProvvedimentoRevocaAmministratori(final int idUffOwn, final long idProcedimento, final long idAtto, final String codiceUtente, final TAtto atto, final long idStatoAtto, final TDecreto decreto, final String nomeMetodo) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_salvaProvvedimentoRevocaAmministratori");
				
				q.setParameter("IDATTO"           ,idAtto);
                q.setParameter("IDUFFOWN"         ,idUffOwn);
                q.setParameter("IDPROC"           ,idProcedimento);
                q.setParameter("DESCUTENTE"       ,codiceUtente, Hibernate.STRING);
                
                if (atto.getDataEmissione() != null) {
                	q.setParameter("DATAEMISSIONE"    ,atto.getDataEmissione() ,Hibernate.DATE);
                } else {
                	q.setParameter("DATAEMISSIONE"    ,null ,Hibernate.DATE);
                }
                
                if (atto.getDataDeposito() != null) {
                	q.setParameter("DATADEPOSITO"     ,atto.getDataDeposito() ,Hibernate.DATE);
                } else {
                	q.setParameter("DATADEPOSITO"     ,null ,Hibernate.DATE);
                }

                if (atto.getFileDocumento() != null) {
					q.setParameter("DOCUMENTO"    , atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("TIPODOCUMENTO", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("NOMEDOCUMENTO", atto.getDescNomeDocumento(), Hibernate.STRING);	                
                } else {
	                q.setParameter("DOCUMENTO"        ,null ,Hibernate.BLOB);
	                q.setParameter("TIPODOCUMENTO"    ,null ,Hibernate.STRING);
	                q.setParameter("NOMEDOCUMENTO"    ,null ,Hibernate.STRING);                	
                }
                if (atto.getDataRegistrazione() != null) {
                	q.setParameter("DATAREGISTRAZIONE" ,atto.getDataRegistrazione() ,Hibernate.DATE);
                } else {
                	q.setParameter("DATAREGISTRAZIONE" ,null ,Hibernate.DATE);
                }
                
                if (idStatoAtto > 0) {
                	q.setParameter("IDSTATOATTO"      ,idStatoAtto ,Hibernate.LONG);
                } else {
                	q.setParameter("IDSTATOATTO"      ,null ,Hibernate.LONG);
                }

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long id = (Long) first.get("IDATTO");
				return id;
			}
		});
	}	
	
	public long inserisciProvvedimentoNominaAmministratori(final int idUffOwn, final long idProcedimento, final String codiceUtente,
			final TAtto atto, final long idStatoAtto, final TDecreto decreto, final String nomeMetodo) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_inserisciProvvedimentoNominaAmministratori");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("descUtente", codiceUtente);
				q.setParameter("idProcedimento", idProcedimento);
				if ((atto.getTTpTipoAtto() != null) && (atto.getTTpTipoAtto().getCodiKIdTipoAtto() > 0)) {
					q.setParameter("idTipoAtto", atto.getTTpTipoAtto().getCodiKIdTipoAtto());
				} else {
					q.setParameter("idTipoAtto", null, Hibernate.LONG);
				}
				if (atto.getTTpCatAtto().getCodiKIdCatAtto() > 0) {
					q.setParameter("idCatAtto", atto.getTTpCatAtto().getCodiKIdCatAtto());
				} else {
					q.setParameter("idCatAtto", null, Hibernate.LONG);
				}
				if (decreto != null && decreto.getTUdienza() != null && decreto.getTUdienza().getId() != null
						&& decreto.getTUdienza().getId().getCodiKIdUdienza() > 0) {
					q.setParameter("idUdienza", decreto.getTUdienza().getId().getCodiKIdUdienza());
				} else {
					q.setParameter("idUdienza", null, Hibernate.LONG);
				}
				if (idStatoAtto > 0) {
					q.setParameter("idStatoAtto", idStatoAtto);
				} else {
					q.setParameter("idStatoAtto", null, Hibernate.LONG);
				}
				if (atto.getDataEmissione() != null) {
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				} else {
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				}
				if (atto.getDataDeposito() != null) {
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				} else {
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				}
				if (atto.getDataRegistrazione() != null) {
					q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				} else {
					q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				}
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long id = (Long) first.get("IDATTO");
				return id;
			}
		});
	}

	public long salvaProvvedimentoNominaAmministratori(final int idUffOwn, final long idProcedimento, final long idAtto, final String codiceUtente, final TAtto atto, final long idStatoAtto, final TDecreto decreto, final String nomeMetodo) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_salvaProvvedimentoNominaAmministratori");
				
				q.setParameter("IDATTO"           ,idAtto);
                q.setParameter("IDUFFOWN"         ,idUffOwn);
                q.setParameter("IDPROC"           ,idProcedimento);
                q.setParameter("DESCUTENTE"       ,codiceUtente, Hibernate.STRING);
                
                if (atto.getDataEmissione() != null) {
                	q.setParameter("DATAEMISSIONE"    ,atto.getDataEmissione() ,Hibernate.DATE);
                } else {
                	q.setParameter("DATAEMISSIONE"    ,null ,Hibernate.DATE);
                }
                
                if (atto.getDataDeposito() != null) {
                	q.setParameter("DATADEPOSITO"     ,atto.getDataDeposito() ,Hibernate.DATE);
                } else {
                	q.setParameter("DATADEPOSITO"     ,null ,Hibernate.DATE);
                }

                if (atto.getFileDocumento() != null) {
					q.setParameter("DOCUMENTO"    , atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("TIPODOCUMENTO", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("NOMEDOCUMENTO", atto.getDescNomeDocumento(), Hibernate.STRING);	                
                } else {
	                q.setParameter("DOCUMENTO"        ,null ,Hibernate.BLOB);
	                q.setParameter("TIPODOCUMENTO"    ,null ,Hibernate.STRING);
	                q.setParameter("NOMEDOCUMENTO"    ,null ,Hibernate.STRING);                	
                }
                if (atto.getDataRegistrazione() != null) {
                	q.setParameter("DATAREGISTRAZIONE" ,atto.getDataRegistrazione() ,Hibernate.DATE);
                } else {
                	q.setParameter("DATAREGISTRAZIONE" ,null ,Hibernate.DATE);
                }
                
                if (idStatoAtto > 0) {
                	q.setParameter("IDSTATOATTO"      ,idStatoAtto ,Hibernate.LONG);
                } else {
                	q.setParameter("IDSTATOATTO"      ,null ,Hibernate.LONG);
                }

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long id = (Long) first.get("IDATTO");
				return id;
			}
		});
	}

	public void revocaAmministratore(final int idUffOwn, final long idProcedimento, final long idAtto, final String idsAmm, final Date dataDeposito, final String codiceUtente, final String nomeMetodo) {
		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_revocaAmministratore");
				
                q.setParameter("IDUFFOWN"         ,idUffOwn);
                q.setParameter("IDPROC"           ,idProcedimento);
                q.setParameter("IDATTO"           ,idAtto);		
                if(idsAmm!=null) {
                	q.setParameter("IDSAMM"           ,idsAmm);
                } else {
                	q.setParameter("IDSAMM"           ,null, Hibernate.STRING);
                }
                q.setParameter("DATADEPOSITO"     ,dataDeposito		,Hibernate.DATE);
                q.setParameter("DESCUTENTE"       ,codiceUtente, Hibernate.STRING);
                
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				return q.list();
			}
		});
	}	
	
	
    public List getAmministratoriRevocabili(final int idUffOwn, final Long idProcedimento) {

        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                List<Object[]> lista = null;
                Query q = session.getNamedQuery("PG_ATTO_amministratoriRevocabili");
                q.setParameter("IDUFFOWN", idUffOwn);
               	q.setParameter("IDPROC", idProcedimento);
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                lista = q.list();
                return lista;
            }
        });
    }	
	
	
    public List getAmministratoriRevocati(final int idUffOwn, final Long idProcedimento, final Long idAtto) {

        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                List<Object[]> lista = null;
                Query q = session.getNamedQuery("PG_ATTO_amministratoriRevocati");
                q.setParameter("IDUFFOWN", idUffOwn);
               	q.setParameter("IDPROC", idProcedimento);
            	q.setParameter("IDATTO", idAtto);
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                lista = q.list();
                return lista;
            }
        });
    }
	
	public Date getMaxDataEmissioneProposta(final int idUffOwn, final long idProcedimento) {
		
        return (Date) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_ATTO_MAX_DATA_EMIS_PROPOSTA");
                q.setParameter("IDUFFOWN", idUffOwn);
               	q.setParameter("IDPROC", idProcedimento);
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                Date maxDataEmissioneProposta = null;
				List l = q.list();
				if(l!=null && l.size()>0) {
					HashMap first = (HashMap) l.get(0);
					maxDataEmissioneProposta = (Date) first.get("MAX_DATA_EMISSIONE");
				}
				return maxDataEmissioneProposta;
            }
        });
	}

	/**
	 * aggiornaDecisorioIIIGrado
	 * 
	 * @param idUffOwn
	 * @param idProcedimento
	 * @param atto
	 * @param codUtente
	 * @return long
	 */
	public Long aggiornaDecisorioIIIGrado(final long idUffOwn, final long idProcedimento, final TAtto atto, final String codUtente, final int tipoUff) {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				String query;
				query = "PG_ATTO_aggiornaDecisorioIIIGrado";

				Long idAtto = null;
				Query q = session.getNamedQuery(query);

				q.setParameter("idUffOwn", idUffOwn);
				
				if (idUffOwn > 0)
					q.setParameter("idMittente", idUffOwn, Hibernate.LONG);
				else
					q.setParameter("idMittente", null, Hibernate.LONG);
				
				q.setParameter("idProc", idProcedimento, Hibernate.LONG);
				
				if (atto.getDataEmissione() != null)
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				else
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				if (atto.getDataDeposito() != null)
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				else
					q.setParameter("dataDeposito", null, Hibernate.DATE);

				if (atto.getDataRicezione() != null)
					q.setParameter("dataRicezioneDoc", atto.getDataRicezione(), Hibernate.DATE);
				else
					q.setParameter("dataRicezioneDoc", null, Hibernate.DATE);
				if (atto.getDataRegistrazione() != null)
					q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				else
					q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				q.setParameter("idAtto", atto.getId().getCodiKIdAtto());
				q.setParameter("descUtente", codUtente);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				Iterator iter = l.iterator();
				while (iter.hasNext()) {
					Map map = (Map) iter.next();
					idAtto = (Long) map.get("IDATTO");
				}
				return idAtto;
			}
		});
	}


	/***
	 * Restituisce il codice della misura da inviare a nsc solo se la misura è inviabile
	 * altrimenti restituisce null
	 * @param idProcedimento
	 * @param idUffOwn
	 * @param codi_K_ID_ATTO
	 * @param codi_K_ID_MISURA
	 */
	public String getCodiceEsitoNSCPerFoglioComplementare(final long idProcedimento,final  int idUffOwn,final  long idAtto,final  Long idMisura) {
		return (String) getHibernateTemplate().execute(new HibernateCallback() {
			public String doInHibernate(final Session session) throws HibernateException, SQLException {
				Query q = session.getNamedQuery("PG_ATTO_getCodiceEsitoNSCPerFoglioComplementare");
				q.setParameter("idProc", idProcedimento);
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("idAtto", idAtto);
				q.setParameter("idMisura", idMisura);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				if(l!=null && l.size()>0) {
					HashMap first = (HashMap) l.get(0);
					return (String) first.get("COD_ESITO");
				}
				return null;
			}
		});

	
	}	
	
	
	
	public List<TIstanza> getricercaIstanza(final int idUffOwn, final Long idProcedimento, final Long idTipoAtto, final Long idCatAtto) {
		return (List<TIstanza>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(final Session session) throws HibernateException, SQLException {


                List<Object[]> lista = null;
                Query q = session.getNamedQuery("PG_ATTO_ricercaIstanza");
               	q.setParameter("IDPROCEDIMENTO", idProcedimento);
                q.setParameter("IDUFFOWNER", idUffOwn);
            	q.setParameter("IDTIPOATTO", idTipoAtto);
            	q.setParameter("IDCATEATTO", idCatAtto);            	
            	
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                lista = q.list();
                return lista;
				
			}
		});
	}	
	
	public TDefIstGen caricaDefinitivitaIstGen(TDefIstGenId id) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TDefIstGen.class);
		criteria.add(Restrictions.eq("id.codiKIdProc", id.getCodiKIdProc()));
		criteria.add(Restrictions.eq("id.codiKIdAtto", id.getCodiKIdAtto()));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", id.getCodiKIdUffOwner()));
		List<TDefIstGen> lista = null;
		
		lista = getHibernateTemplate().findByCriteria(criteria);
		if (lista.size() > 0) {
			return lista.get(0);
		} else {
			return null;
		}
	}

	public void salvaDecErroreMateriale(final long idAtto, final long idProcedimento, final String idCodiceUtente, final int idUfficio, final String descDecisorio, final int idDecisorio,final IntestatarioDto intestario, final TRelAttoIstGen rec,final Date dataRicezione,final Long numeAnnoSentenza,final Long numeSentenza) {

		getHibernateTemplate().execute(new HibernateCallback<Object>() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
	            
				Query q = session.getNamedQuery("PG_ATTO_salvaDecErroreMateriale");
				q.setParameter("idAtto", idAtto, Hibernate.LONG);
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setParameter("idUfficio", idUfficio, Hibernate.INTEGER);
				q.setParameter("idDecisione", idDecisorio, Hibernate.INTEGER);
				q.setParameter("descUtente", idCodiceUtente, Hibernate.STRING);
				q.setParameter("descDecisorio", descDecisorio, Hibernate.STRING);
	
				
				if(rec!=null && rec.getDescAltro1()!=null) {
					q.setParameter("DESC_ALTRO_1", rec.getDescAltro1(), Hibernate.STRING);
				} else {
					q.setParameter("DESC_ALTRO_1", null, Hibernate.STRING);
				}
				if(rec!=null && rec.getDescAltro3()!=null) {
					q.setParameter("DESC_ALTRO_3", rec.getDescAltro3(), Hibernate.STRING);
				} else {
					q.setParameter("DESC_ALTRO_3", null, Hibernate.STRING);
				}
				
				q.setParameter("tipsoggetto", intestario.getCODI_FK_ID_TIPO_SOGG(), Hibernate.CHARACTER);
				
				
				if(intestario!=null && intestario.getDESC_NOME()!=null) {
					q.setParameter("DESC_NOME", intestario.getDESC_NOME(), Hibernate.STRING);
				} else {
					q.setParameter("DESC_NOME", null, Hibernate.STRING);
				}
				
				if(intestario!=null && intestario.getDESC_COGNOME()!=null) {
					q.setParameter("DESC_COGNOME", intestario.getDESC_COGNOME(), Hibernate.STRING);
				} else {
					q.setParameter("DESC_COGNOME", null, Hibernate.STRING);
				}	

				if(intestario!=null && intestario.getFLG_SESSO()!=null) {	
					q.setParameter("FLG_SESSO", intestario.getFLG_SESSO(), Hibernate.CHARACTER);
				} else {
					q.setParameter("FLG_SESSO", null, Hibernate.CHARACTER);
				}

				if(intestario!=null && intestario.getDESC_CODICE_FISCALE()!=null) {
					q.setParameter("DESC_CODICE_FISCALE", intestario.getDESC_CODICE_FISCALE(), Hibernate.STRING);
				} else {
					q.setParameter("DESC_CODICE_FISCALE", null, Hibernate.STRING);
				}
				
				if(intestario!=null && intestario.getDATA_NASCITA()!=null) {
					q.setParameter("DATA_NASCITA", intestario.getDATA_NASCITA(), Hibernate.DATE);
				} else {
					q.setParameter("DATA_NASCITA", null, Hibernate.DATE);
				}
						

				if(intestario!=null && intestario.getCODI_FK_ID_COM_NASC()!=null) {
					q.setParameter("CODI_FK_ID_COM_NASC", intestario.getCODI_FK_ID_COM_NASC(), Hibernate.BIG_DECIMAL);
				} else {
					q.setParameter("CODI_FK_ID_COM_NASC", null, Hibernate.BIG_DECIMAL);
				}

				if(intestario!=null && intestario.getCODI_FK_ID_STATO_NASC()!=null) {
					q.setParameter("CODI_FK_ID_STATO_NASC", intestario.getCODI_FK_ID_STATO_NASC(), Hibernate.BIG_DECIMAL);
				} else {
					q.setParameter("CODI_FK_ID_STATO_NASC", null, Hibernate.BIG_DECIMAL);
				}
				
			
				if(intestario!=null && intestario.getDESC_COM_ESTERO_NASC()!=null) {
					q.setParameter("DESC_COM_ESTERO_NASC", intestario.getDESC_COM_ESTERO_NASC(), Hibernate.STRING);
				} else {
					q.setParameter("DESC_COM_ESTERO_NASC", null, Hibernate.STRING);
				}
				
				
				if(intestario!=null && intestario.getCODI_FK_ID_TIPO_SOC()!=null) {
					q.setParameter("CODI_FK_ID_TIPO_SOC", intestario.getCODI_FK_ID_TIPO_SOC(), Hibernate.BIG_DECIMAL);
				} else {
					q.setParameter("CODI_FK_ID_TIPO_SOC", null, Hibernate.BIG_DECIMAL);
				}
				
				if(intestario!=null && intestario.getCODI_FK_ID_STATO_SEDE()!=null) {
					q.setParameter("CODI_FK_ID_STATO_SEDE", intestario.getCODI_FK_ID_STATO_SEDE(), Hibernate.BIG_DECIMAL);
				} else {
					q.setParameter("CODI_FK_ID_STATO_SEDE", null, Hibernate.BIG_DECIMAL);
				}
				
				if(intestario!=null && intestario.getCODI_FK_ID_COM_SEDE()!=null) {
					q.setParameter("CODI_FK_ID_COM_SEDE", intestario.getCODI_FK_ID_COM_SEDE(), Hibernate.BIG_DECIMAL);
				} else {
					q.setParameter("CODI_FK_ID_COM_SEDE", null, Hibernate.BIG_DECIMAL);
				}
				
				
				if(intestario!=null && intestario.getDESC_COM_ESTERO_SEDE()!=null) {
					q.setParameter("DESC_COM_ESTERO_SEDE", intestario.getDESC_COM_ESTERO_SEDE(), Hibernate.STRING);
				} else {
					q.setParameter("DESC_COM_ESTERO_SEDE", null, Hibernate.STRING);
				}
				if(intestario!=null && intestario.getDESC_INDIRIZZO_SEDE()!=null) {
					q.setParameter("DESC_INDIRIZZO_SEDE", intestario.getDESC_INDIRIZZO_SEDE(), Hibernate.STRING);
				} else {
					q.setParameter("DESC_INDIRIZZO_SEDE", null, Hibernate.STRING);
				}
				if(intestario!=null && intestario.getDESC_RAGIONE_SOC()!=null) {
					q.setParameter("DESC_RAGIONE_SOC", intestario.getDESC_RAGIONE_SOC(), Hibernate.STRING);
				} else {
					q.setParameter("DESC_RAGIONE_SOC", null, Hibernate.STRING);
				}
				
				
				if(intestario!=null && intestario.getDESC_PARTITA_IVA()!=null) {
					q.setParameter("DESC_PARTITA_IVA", intestario.getDESC_PARTITA_IVA(), Hibernate.STRING);
				} else {
					q.setParameter("DESC_PARTITA_IVA", null, Hibernate.STRING);
				}
				if(intestario!=null && intestario.getDESC_NUME_CCIAA()!=null) {
					q.setParameter("DESC_NUME_CCIAA", intestario.getDESC_NUME_CCIAA(), Hibernate.STRING);
				} else {
					q.setParameter("DESC_NUME_CCIAA", null, Hibernate.STRING);
				}
				if(intestario!=null && intestario.getDESC_CIVICO()!=null) {
					q.setParameter("DESC_CIVICO", intestario.getDESC_CIVICO(), Hibernate.STRING);
				} else {
					q.setParameter("DESC_CIVICO", null, Hibernate.STRING);
				}	
				
				
				if(intestario!=null && intestario.getDESC_CAP()!=null) {
					q.setParameter("DESC_CAP", intestario.getDESC_CAP(), Hibernate.STRING);
				} else {
					q.setParameter("DESC_CAP", null, Hibernate.STRING);
				}	
				
				if(intestario!=null && intestario.getCODI_FK_ID_TIPO_STRADA()!=null && intestario.getCODI_FK_ID_TIPO_STRADA().compareTo(BigDecimal.ZERO) >0) {
					q.setParameter("CODI_FK_ID_TIPO_STRADA", intestario.getCODI_FK_ID_TIPO_STRADA(), Hibernate.BIG_DECIMAL);
				} else {
					q.setParameter("CODI_FK_ID_TIPO_STRADA", null, Hibernate.BIG_DECIMAL);
				}	
				
				
				if(intestario!=null && intestario.getCODI_K_ID_INTESTATARIO()!=null) {
					q.setParameter("CODI_K_ID_INTESTATARIO", intestario.getCODI_K_ID_INTESTATARIO(), Hibernate.BIG_DECIMAL);
				} else {
					q.setParameter("CODI_K_ID_INTESTATARIO", null, Hibernate.BIG_DECIMAL);
				}	
				
				if(dataRicezione!=null ) {
					q.setParameter("dataRicezione", dataRicezione, Hibernate.DATE);
				} else {
					q.setParameter("dataRicezione", null, Hibernate.DATE);
				}
						
				if(numeAnnoSentenza!=null) {
					q.setParameter("numeAnnoSentenza", numeAnnoSentenza, Hibernate.LONG);
				} else {
					q.setParameter("numeAnnoSentenza", null, Hibernate.LONG);
				}
				
				if(numeSentenza!=null) {
					q.setParameter("numeSentenza", numeSentenza, Hibernate.LONG);
				} else {
					q.setParameter("numeSentenza", null, Hibernate.LONG);
				}
				
				

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List<Map<?, ?>> l = q.list();
				return l;
			}
		});
	}		
	
	/**
	 * Metodo per ricavare la lista
	 * dei documenti inviati al documentale in formato diverso da PDF
	 * @return
	 */
	public List<TAtto> listAttiConversionePdfJob() {
		List<TAtto> result = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TAtto.class);
		criteria.add(Restrictions.isNotNull("numeDocumentoPdoc"));
		criteria.add(Restrictions.isNotNull("descTipoDocumento"));
//		criteria.add(Restrictions.ne("descTipoDocumento","application/pdf")); tolta per nuova gestione tipo file da inviare tramite tabella
		criteria.add(Restrictions.isNull("numeConvPdoc"));
//		criteria.add(Restrictions.isNotNull("fileDocumento"));  
//		criteria.add(Restrictions.ne("descTipoDocumento","APPLICATION/PDF"));
		result = getHibernateTemplate().findByCriteria(criteria);
		return result;
	}
	
	/**
	 * Metodo per ricavare la lista dei documenti inviati al documentale
	 * in formato diverso da PDF per interrogarne lo stato di avanzamento
	 * @return
	 */
	public List<TAtto> listMonitorRenderPdfJob() {
		List<TAtto> result = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TAtto.class);
		criteria.add(Restrictions.isNotNull("numeConvPdoc"));
		criteria.add(Restrictions.ne("descTipoDocumento","application/pdf")); 
		criteria.add(Restrictions.ne("descTipoDocumento","APPLICATION/PDF")); // esclude anche questa casistica !!
		result = getHibernateTemplate().findByCriteria(criteria);
		return result;
	}
	
	/**
	 * Metodo per aggiornare il formato file in pdf dopo conversione da parte del Documentale
	 */
	public void aggiornaTipoDocumento(final String nomeDocumento, final long idAtto, final BigInteger idDocumento, final int uffOwner){
		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				TAtto query = getHibernateTemplate().get(TAtto.class, new TAttoId(idAtto, uffOwner));

				query.setDescTipoDocumento("application/pdf");
				query.setDescNomeDocumento(nomeDocumento);

				session.saveOrUpdate(query);
				session.flush();
				return null;
			}
		});
	}

	public List existsIntervenientiNonAssociati(final Long idProcedimento, final int idUfficio) {
		return (List)getHibernateTemplate().execute(new HibernateCallback() {
			public List doInHibernate(final Session session) throws HibernateException, SQLException {
				return session.getNamedQuery("PG_PROCEDIMENTO_intervenientiNonAssociati").setParameter("IDPROC", idProcedimento, Hibernate.LONG).setParameter("IDUFFOWN", idUfficio, Hibernate.INTEGER).list();
			}
		});
	}
	
	public boolean isAttoOrigine(long idAtto, int idUffOwn) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TRelAtti.class);
		criteria.add(Restrictions.eq("id.codiKIdAttoOr", idAtto));
		criteria.add(Restrictions.eq("id.codiKIdUffOwnerOr", idUffOwn));
		List<TRelAtti> lista = null;
		
		lista = getHibernateTemplate().findByCriteria(criteria);
		if (lista.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public void insertIdTransazioneDocumentale(final long idAtto, final BigInteger idTransazione, final int uffOwner){
		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				TAtto query = getHibernateTemplate().get(TAtto.class, new TAttoId(idAtto, uffOwner));

				query.setNumeConvPdoc(idTransazione.longValue());
				
				session.saveOrUpdate(query);
				session.flush();
				return null;
			}
		});
	}
	
	public void cancelIdTransazioneDocumentale(final long idAtto,  final int uffOwner){
		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				TAtto query = getHibernateTemplate().get(TAtto.class, new TAttoId(idAtto, uffOwner));

				query.setNumeConvPdoc(null);
				
				session.saveOrUpdate(query);
				session.flush();
				return null;
			}
		});
		
	}
	
	public List<Map> ordinanzaVenditaBene(final long vidUffOwn, final String vcodUtente, final long vidprocedimento, final long vidstatobene, final TBene bene, final TDenaro denaro, final String numeroBollettino, final TAtto atto) {

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
                
                
                Query q = session.getNamedQuery("PG_ATTO_ordinanzaVenditaBene");
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
                
				if (atto.getDataEmissione() != null) {
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				} else {
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				}
				if (atto.getDataDeposito() != null) {
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				} else {
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				}
				if (atto.getDataRegistrazione() != null) {
					q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				} else {
					q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				}
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
                // *************************** PARAMETRI BENE DENARO
                // *************************************
                q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List<Map> l = q.list();
                return l;
            }
        });
    }	

	public long inserisciAttoRelazioneAmm(final int idUffOwn,final String idCodiceUtente, final long idProcFG, final long idTipoAtto, final 
			   long idCatAtto, final long idStatoAtto, final Date dataRicezione, final long idAmm,final String cognomeAmm, final String nomeAmm, 
			   final Date dataInizio, final Date dataFine, final long idPDOC, final Blob blob, final String tipoAllegato, final String nomeAllegato, final String numeroRiferimento) {	
			
						return (Long) getHibernateTemplate().execute(new HibernateCallback() {

								public Object doInHibernate(final Session session) throws HibernateException, SQLException 	{

									Long idAtto = null;
									Query q = session.getNamedQuery("PG_ATTO_inserisciAttoRelazioneAmminstratore");
									q.setParameter("idUffOwn", idUffOwn);
									q.setParameter("idProc", idProcFG, Hibernate.LONG);
														
									q.setParameter("idStatoAtto", idStatoAtto, Hibernate.LONG);
									q.setParameter("idTipoAtto", idTipoAtto, Hibernate.LONG); 
									q.setParameter("idCategoriaAtto", idCatAtto, Hibernate.LONG);
									q.setParameter("dataRicezione", dataRicezione, Hibernate.DATE);		
									q.setParameter("numeroRiferimento", numeroRiferimento, Hibernate.STRING);
									
									if (idPDOC > 0) {
									   q.setParameter("numPDOC", idPDOC, Hibernate.LONG);
									}  else {
									   q.setParameter("numPDOC", null, Hibernate.LONG);
									}
									if (blob != null) {
									    q.setParameter("documento", blob, Hibernate.BLOB);
									} else {
									    q.setParameter("documento", null, Hibernate.BLOB);
									}
									q.setParameter("tipoDocumento", tipoAllegato, Hibernate.STRING);
									q.setParameter("nomeDocumento", nomeAllegato, Hibernate.STRING);
									
									if(dataInizio != null) {
									    q.setParameter("dataInizio", dataInizio, Hibernate.DATE);
									} else {
									    q.setParameter("dataInizio", null, Hibernate.DATE);
									}
									if(dataFine != null) {
									    q.setParameter("dataFine", dataFine, Hibernate.DATE);
									} else {
									    q.setParameter("dataFine", null, Hibernate.DATE);
									}				
				                				
				                    q.setParameter("idAmm", idAmm, Hibernate.LONG);
									q.setParameter("cognomeAmm", cognomeAmm, Hibernate.STRING);
									q.setParameter("nomeAmm", nomeAmm, Hibernate.STRING);

									q.setParameter("descUtente", idCodiceUtente, Hibernate.STRING);

									q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
									List l = q.list();
									Iterator iter = l.iterator();
									while (iter.hasNext()) {
										Map map = (Map) iter.next();
										idAtto = (Long) map.get("IDATTO");
									}
									return idAtto;
								}
							});
						}
	
	public TAttoGenerico getTAttoGenerico(long idAtto, long idUfficio){
		List<TAttoGenerico> lista = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TAttoGenerico.class);
		criteria.add(Restrictions.eq("id.codiKIdAttoGen", idAtto));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", new Long(idUfficio).intValue()));
		lista = getHibernateTemplate().findByCriteria(criteria);
		if (lista!=null && !lista.isEmpty())
			return lista.get(0);
		else
			return null;
	}
	
	
	
	public long contaInvioMsgCassazione(final long idProc, final long idAtto, final long idUffOwn) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_contaInvioMsgCassazione");
				q.setParameter("idProc", idProc, Hibernate.LONG);
				q.setParameter("idAtto", idAtto, Hibernate.LONG);
				q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
				

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long id = (Long) first.get("CONTA_INVIO");
				return id;
			}
		});
	}

	@Override
	public int getUffOwnerFromIdDocumento(BigInteger idDocumento) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TAtto.class);
		criteria.add(Restrictions.eq("numeDocumentoPdoc", idDocumento.longValue()));
		List risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati!=null && !risultati.isEmpty())
		{
			TAtto atto =  (TAtto)risultati.get(0);
			return atto.getId().getCodiKIdUffOwner();
		}
		else
			return 0;
	}	
	
	public int getUffOwnerTestDocumentale(){
		DetachedCriteria criteria = DetachedCriteria.forClass(TAtto.class);
//		criteria.add(Restrictions.sqlRestriction("LIMIT 1"));
		criteria.getExecutableCriteria(getSession()).setMaxResults(1);
		List risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati!=null && !risultati.isEmpty())
		{
			TAtto atto =  (TAtto)risultati.get(0);
			return atto.getId().getCodiKIdUffOwner();
		}
		else
			return 0;
	}
	
	public String cleanBlobJobDocumentale() {
		return (String) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				String result = null;
				Query q = session.getNamedQuery("PG_ATTO_cleanBlobDocumentale");
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				Iterator iter = l.iterator();
				while (iter.hasNext()) {
					Map map = (Map) iter.next();
					result = (String) map.get("ESITO");
				}
				System.out.println("    7.1) - Risultato Cancellazione Blob : " + result);
				return result;
				 
			}
		});
	}
	
	public KeyMessageDto controllaPresenzaRicorso(final long idProc, final long idUfficio, final String funzione, final String descUtente) {
		return (KeyMessageDto) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_controllaPresenzaRicorsi");
				q.setParameter("I_ID_PROCEDIMENTO", idProc, Hibernate.LONG);
				q.setParameter("I_ID_UFFICIO", idUfficio, Hibernate.LONG);
				q.setParameter("I_ID_UTENTE", descUtente, Hibernate.STRING);
				q.setParameter("I_TIPO_FUNZIONE", funzione, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return new KeyMessageDto(l);
			}
		});
	}
	
	public KeyMessageDto impostaGiudizioDefinitivoI(final long idProc, final long idUfficio, final String funzione, final String descUtente) {
		return (KeyMessageDto) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_impostaGiudizioDefinitivoI");
				q.setParameter("I_ID_PROCEDIMENTO", idProc, Hibernate.LONG);
				q.setParameter("I_ID_UFFICIO", idUfficio, Hibernate.LONG);
				q.setParameter("I_ID_UTENTE", descUtente, Hibernate.STRING);
				q.setParameter("I_TIPO_FUNZIONE", funzione, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return new KeyMessageDto(l);
			}
		});
	}
	
	public Long getIdDocumentale(long idAtto, int idUfficio){
		DetachedCriteria criteria = DetachedCriteria.forClass(TAtto.class);
		criteria.add(Restrictions.eq("id.codiKIdAtto", idAtto));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficio));
		List risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati!=null && !risultati.isEmpty())
		{
			TAtto atto =  (TAtto)risultati.get(0);
			if(atto!=null && atto.getNumeDocumentoPdoc()!=null)
				return atto.getNumeDocumentoPdoc();
			else
				return null;
		}
		else
			return null;
	}
	
	/**
	 * Metodo per ricavare la lista tipo file che devono essere convertiti in pdf - 13 Aprile 2018 Alfredo G.
	 * 
	 * @return
	 */
	public List<TTpTipoFilePdoc> listaTipoFilePdoc() {
		List<TTpTipoFilePdoc> result = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpTipoFilePdoc.class);
		criteria.add(Restrictions.ne("convertibile","0"));
		result = getHibernateTemplate().findByCriteria(criteria);
		return result;
	}

	/* 
	 * ISSUE MEV : Specifiche Intervento-006-SpeseProces-NumAtto
	 * Numero MEV : 006
	 * Autore    : Gioggi
	 * Data      : 16 apr 2020
	 * Branch    : MEV_006
	 */
	private boolean esisteNumAtto(long idDecreto, long idUffOwn) {

		// d.nume_anno_atto, d.nume_atto
		Query query = getSession().createSQLQuery("select d.* from t_decreto d "
				+ "where d.codi_k_id_decreto = " + idDecreto + " and d.codi_k_id_uff_owner = " + idUffOwn)
				.addEntity("d", TDecreto.class);
		List l = query.list();
		if (l != null && l.size() > 0) {
			TDecreto td = (TDecreto) l.get(0);
			if (td.getNumeAnnoAtto() != null && td.getNumeAtto() != null)
				return true;
		}
		return false;
	}
	//***** FINE INTERVENTO MEV_006 *****//
	
	//mev spese processuali marzo 2020
	public List<SogSpesePDettaglioProcedimentoDto> getRicercaSoggettoAttoSpeseProc(final long idProcedimento, final long idatto, final int iduff,
			final int descRuolo, final Character flagValido) {

		return (List<SogSpesePDettaglioProcedimentoDto>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_ricercaSoggettoAttoSpeseProc");
				//Query q = session.getNamedQuery("PG_ATTO_ricercaSoggettoAtto");
				q.setParameter("idProcedimento", idProcedimento);
				q.setParameter("idatto", idatto);
				q.setParameter("iduff", iduff);

				if (descRuolo >= 0) {
					q.setParameter("descruolo", descRuolo);
				} else {
					q.setParameter("descruolo", null, Hibernate.INTEGER);
				}

				if ((flagValido != null) && !"".equals(flagValido.toString())) {
					q.setParameter("flagvalido", flagValido, Hibernate.CHARACTER);
				} else {
					q.setParameter("flagvalido", null, Hibernate.CHARACTER);
				}
			
				q.setResultTransformer(Transformers.aliasToBean(SogSpesePDettaglioProcedimentoDto.class));
				return q.list();
			}
		});
	}

	public TRelSoggSpese recuperaSoggSpeseProc(final long idProc, final long idUfficio, final long idAtto, final long idSoggetto, final long idVer, final long idRuolo) {
		return (TRelSoggSpese) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				 
				String qry = " SELECT TRELSOGGSPESE.* FROM T_REL_SOGG_SPESE TRELSOGGSPESE " 
								+ " where CODI_K_ID_PROC      =: idProc "
								+ " and   CODI_K_ID_UFF_OWNER =: idUfficio "
								+ " and   CODI_K_ID_ATTO      =: idAtto"
								+ " and   CODI_K_ID_SOGG_INT  =: idSoggetto"
								+ " and   CODI_FK_ID_VER  	  =: idVer"
								+ " and   CODI_FK_ID_RUOLO    =: idRuolo";
				SQLQuery query = getSession().createSQLQuery(qry);
				query.addEntity(TRelSoggSpese.class);
				//query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				return query.list();
			}
			
		});
	}

}