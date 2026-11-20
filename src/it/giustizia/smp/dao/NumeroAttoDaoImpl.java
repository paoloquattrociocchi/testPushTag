package it.giustizia.smp.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import it.giustizia.smp.model.TAtto;
import it.giustizia.smp.model.TRelAttoMisPers;

/**
 * NumeroAttoDaoImpl: classe dao all'interno della quale viene fatto l'accesso al DB
 * 
 * @author Gioggi
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class NumeroAttoDaoImpl extends HibernateDaoSupport implements NumeroAttoDao {

	// private Logger logger = Logger.getLogger(getClass());

	/*
	 * ISSUE MEV : Specifiche Intervento-006-SpeseProces-NumAtto
	 * Numero MEV : 006
	 * Autore : Gioggi
	 * Data : 16 apr 2020
	 * Branch : MEV_006
	 */
	public Long generaNumeroAtto(final long idUffOwn, final long idProcUdi, final long idProcGen,
			final long idProcProp, final long idProcedimento, final TAtto atto, final String codUtente,
			final int tipoUfficio, final long idMagistrato, final String giudiceDelegato) {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				String query = "PG_NUMERO_ATTO_generaNumeroAtto";
				Long idAtto = null;
				Query q = session.getNamedQuery(query);

				q.setParameter("idUffOwn", idUffOwn);
				if (idUffOwn > 0)
					q.setParameter("idMittente", idUffOwn, Hibernate.LONG);
				else
					q.setParameter("idMittente", null, Hibernate.LONG);
				if (idProcUdi > 0)
					q.setParameter("idProcInUdienza", idProcUdi, Hibernate.LONG);
				else
					q.setParameter("idProcInUdienza", null, Hibernate.LONG);
				if (idProcGen > 0)
					q.setParameter("idProcGenerale", idProcGen, Hibernate.LONG);
				else
					q.setParameter("idProcGenerale", null, Hibernate.LONG);
				if (idProcProp > 0)
					q.setParameter("idProcProponente", idProcProp, Hibernate.LONG);
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

	public void generaNumeroAttoDI(final long idProcedimento, final long idAtto, final long idMittente,
			final long idDestinatario1, final long idDestinatario2, final long idDestinatario3,
			final String codUtente, final long idUfficio, final long categoriaAtto, final String nomeMetodo) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_NUMERO_ATTO_generaNumeroAttoDI");
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
				return l;
			}
		});
	}

	/**
	 * Metodo per generare i due flussi per il decreto di incompetenza della Procura
	 */
	public void generaNumeroAttoDIP(final long idProcedimento, final long idAtto, final long idMittente,
			final long idDestinatarioProposta, final long idDestinatario2, final long idDestinatario3,
			final String codUtente, final long idUfficio, final long categoriaAtto, final String nomeMetodo) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_NUMERO_ATTO_generaNumeroAttoDIP");
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
			}
		});
	}

	public Long generaNumeroAttoDS(final long idUfficio, final long idProcedimento, final TAtto atto,
			final String codUtente, final long tipoAtto, final long categoriaAtto, final long idStatoAtto,
			final long idDestinatario, final long idUltDestinatario, final long codUdienzaAtto,
			final long codUdienzaFiss, final long codUdienzaInFaseDiRip, final long codUdienzaInFaseDiRim,
			final long statoUdienzaPrec, final long idMagistrato, final String giudiceDelegato) {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_NUMERO_ATTO_generaNumeroAttoDS");
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

	public Long generaNumeroAttoDRTS(final long idUfficio, final long idProcedimento, final TAtto atto,
			final String codUtente, final long tipoAtto, final long categoriaAtto, final long idStatoAtto,
			final long idDestinatario, final long idUltDestinatario, final long codUdienzaAtto,
			final long codUdienzaFiss, final long codUdienzaInFaseDiRip, final long codUdienzaInFaseDiRim,
			final long statoUdienzaPrec, final String nomeMetodo) {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_NUMERO_ATTO_generaNumeroAttoDRTS");
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

	public void generaNumeroAttoDAP(final TAtto atto, final String idMisureCaricate,
			final long idProcedimento, final long vidAtto, final long vidUffOwn,
			final TRelAttoMisPers relAttoMisPers, final long idSoggInt, final String cauzione,
			final String articolo67, final String vcodUtente, final String idPrescrizioni,
			final BigDecimal importoCauzione, final String articolo9c1) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_NUMERO_ATTO_generaNumeroAttoDAP");

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

				if (atto.getDataRegistrazione() != null) {
					q.setParameter("idStatoAtto", 3l, Hibernate.LONG); // registrato
				} else {
					q.setParameter("idStatoAtto", null, Hibernate.LONG);
				}

				// MISURE
				q.setParameter("idMisureCaricate", idMisureCaricate);
				q.setParameter("idProc", idProcedimento);
				if (vidAtto > 0) {
					q.setParameter("idAtto", vidAtto);
				} else {
					q.setParameter("idAtto", null, Hibernate.LONG);
				}
				q.setParameter("idUffOwn", vidUffOwn);
				q.setParameter("idSoggInt", idSoggInt);

				if (relAttoMisPers != null && relAttoMisPers.getTTpTipoMisura() != null
						&& relAttoMisPers.getTTpTipoMisura().getCodiKIdTipoMisura() > 0) {
					q.setParameter("idTipoMisura", relAttoMisPers.getTTpTipoMisura().getCodiKIdTipoMisura());
				} else {
					q.setParameter("idTipoMisura", null, Hibernate.LONG);
				}

				if (relAttoMisPers != null && relAttoMisPers.getTAnNormative() != null
						&& relAttoMisPers.getTAnNormative().getCodiKIdNormativa() > 0) {
					q.setParameter("idNormativa", relAttoMisPers.getTAnNormative().getCodiKIdNormativa());
				} else {
					q.setParameter("idNormativa", null, Hibernate.LONG);
				}

				if (relAttoMisPers != null && relAttoMisPers.getNumeDurataAnni() != null) {
					q.setParameter("durataAnni", relAttoMisPers.getNumeDurataAnni());
				} else {
					q.setParameter("durataAnni", null, Hibernate.LONG);
				}
				if (relAttoMisPers != null && relAttoMisPers.getNumeDurataMesi() != null) {
					q.setParameter("durataMesi", relAttoMisPers.getNumeDurataMesi());
				} else {
					q.setParameter("durataMesi", null, Hibernate.LONG);
				}
				if (relAttoMisPers != null && relAttoMisPers.getNumeDurataGiorni() != null) {
					q.setParameter("durataGiorni", relAttoMisPers.getNumeDurataGiorni());
				} else {
					q.setParameter("durataGiorni", null, Hibernate.LONG);
				}
				q.setParameter("descLoc", relAttoMisPers.getDescLoc());
				q.setParameter("descUltPresc", relAttoMisPers.getDescUltPresc());
				q.setParameter("cauzione", cauzione, Hibernate.STRING);
				q.setParameter("articolo67", articolo67);

				if (relAttoMisPers != null && relAttoMisPers.getTTpEsitoByCodiFkIdEsito1() != null
						&& relAttoMisPers.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito() > 0) {
					q.setParameter("idEsito1",
							relAttoMisPers.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito());
				} else {
					q.setParameter("idEsito1", null, Hibernate.LONG);
				}
				if (relAttoMisPers != null && relAttoMisPers.getTTpEsitoByCodiFkIdEsito2() != null
						&& relAttoMisPers.getTTpEsitoByCodiFkIdEsito2().getCodiKIdEsito() > 0) {
					q.setParameter("idEsito2",
							relAttoMisPers.getTTpEsitoByCodiFkIdEsito2().getCodiKIdEsito());
				} else {
					q.setParameter("idEsito2", null, Hibernate.LONG);
				}
				if (relAttoMisPers != null && relAttoMisPers.getTTpEsitoByCodiFkIdEsito3() != null
						&& relAttoMisPers.getTTpEsitoByCodiFkIdEsito3().getCodiKIdEsito() > 0) {
					q.setParameter("idEsito3",
							relAttoMisPers.getTTpEsitoByCodiFkIdEsito3().getCodiKIdEsito());
				} else {
					q.setParameter("idEsito3", null, Hibernate.LONG);
				}
				if (relAttoMisPers != null && relAttoMisPers.getTTpEsitoFinale() != null
						&& relAttoMisPers.getTTpEsitoFinale().getCodiKIdEsitoFin() > 0) {
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
				q.setParameter("articolo9c1", articolo9c1);// -- valori ammessi S o N

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});
	}

	public void generaNumeroAttoDRAP(final TAtto atto, final String idMisureCaricate, final long vidProc,
			final long vidAtto, final long vidUffOwn, final TRelAttoMisPers relAttoMisPers,
			final long idSoggInt, final String cauzione, final String articolo67, final String vcodUtente,
			final String idPrescrizioni, final BigDecimal importoCauzione, final String articolo9c1) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_NUMERO_ATTO_generaNumeroAttoDRAP");

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

				if (atto.getDataRegistrazione() != null) {
					q.setParameter("idStatoAtto", 3l, Hibernate.LONG); // registrato
				} else {
					q.setParameter("idStatoAtto", null, Hibernate.LONG);
				}

				// MISURE
				q.setParameter("idMisureCaricate", idMisureCaricate);
				q.setParameter("idProc", vidProc);
				if (vidAtto > 0) {
					q.setParameter("idAtto", vidAtto);
				} else {
					q.setParameter("idAtto", null, Hibernate.LONG);
				}
				q.setParameter("idUffOwn", vidUffOwn);
				q.setParameter("idSoggInt", idSoggInt);

				if ((relAttoMisPers != null) && (relAttoMisPers.getTTpTipoMisura() != null)
						&& relAttoMisPers.getTTpTipoMisura().getCodiKIdTipoMisura() > 0) {
					q.setParameter("idTipoMisura", relAttoMisPers.getTTpTipoMisura().getCodiKIdTipoMisura());
				} else {
					q.setParameter("idTipoMisura", null, Hibernate.LONG);
				}

				if ((relAttoMisPers != null) && (relAttoMisPers.getTAnNormative() != null)
						&& relAttoMisPers.getTAnNormative().getCodiKIdNormativa() > 0) {
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
				q.setParameter("cauzione", cauzione, Hibernate.STRING);
				q.setParameter("articolo67", articolo67);

				if ((relAttoMisPers != null) && (relAttoMisPers.getTTpEsitoByCodiFkIdEsito1() != null)
						&& (relAttoMisPers.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito() > 0)) {
					q.setParameter("idEsito1",
							relAttoMisPers.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito());
				} else {
					q.setParameter("idEsito1", null, Hibernate.LONG);
				}
				if ((relAttoMisPers != null) && (relAttoMisPers.getTTpEsitoByCodiFkIdEsito2() != null)
						&& (relAttoMisPers.getTTpEsitoByCodiFkIdEsito2().getCodiKIdEsito() > 0)) {
					q.setParameter("idEsito2",
							relAttoMisPers.getTTpEsitoByCodiFkIdEsito2().getCodiKIdEsito());
				} else {
					q.setParameter("idEsito2", null, Hibernate.LONG);
				}
				if ((relAttoMisPers != null) && (relAttoMisPers.getTTpEsitoByCodiFkIdEsito3() != null)
						&& (relAttoMisPers.getTTpEsitoByCodiFkIdEsito3().getCodiKIdEsito() > 0)) {
					q.setParameter("idEsito3",
							relAttoMisPers.getTTpEsitoByCodiFkIdEsito3().getCodiKIdEsito());
				} else {
					q.setParameter("idEsito3", null, Hibernate.LONG);
				}
				if ((relAttoMisPers != null) && (relAttoMisPers.getTTpEsitoFinale() != null)
						&& (relAttoMisPers.getTTpEsitoFinale().getCodiKIdEsitoFin() > 0)) {
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
				q.setParameter("articolo9c1", articolo9c1);// -- valori ammessi S o N

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});
	}

	public Long generaNumeroAttoDCRS(final long idUfficio, final long idProcedimento, final TAtto atto,
			final String codUtente, final long tipoAtto, final long categoriaAtto, final long idStatoAtto,
			final long idDestinatario, final long idUltDestinatario, final long codUdienzaAtto,
			final long codUdienzaFiss, final long codUdienzaInFaseDiRip, final long codUdienzaInFaseDiRim,
			final long statoUdienzaPrec, final String tipoConvalidaRevoca, final String nomeMetodo) {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_NUMERO_ATTO_generaNumeroAttoDCRS");
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

	public Long generaNumeroAttoDPES(final long idUfficio, final long idProcedimento, final TAtto atto, final String codUtente,
			final long tipoAtto, final long categoriaAtto, final long idStatoAtto, final long codUdienzaAtto,
			final long codUdienzaFiss, final long codUdienzaInFaseDiRip, final long codUdienzaInFaseDiRim,
			final long statoUdienzaPrec, final String tipoConvalidaRevoca, final String nomeMetodo) {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_NUMERO_ATTO_generaNumeroAttoDPES");
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

    public Long generaNumeroAttoDPS(final long idUfficio, final long idProcedimento, final TAtto atto,
    		final String codUtente, final long tipoAtto, final long categoriaAtto, final long idStatoAtto,
    		final String nomeMetodo) {

        return (Long) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_NUMERO_ATTO_generaNumeroAttoDPS");
                q.setParameter("idUffOwn", idUfficio);
                q.setParameter("idProc", idProcedimento, Hibernate.LONG);
                if (atto.getDataRegistrazione() != null) {
                    q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
                } else {
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

    public Long generaNumeroAttoDST(final long idUfficio, final long idProcedimento, final TAtto atto,
    		final String codUtente, final long tipoAtto,final long categoriaAtto, final long idStatoAtto,
    		final String nomeMetodo) {

        return (Long) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_NUMERO_ATTO_generaNumeroAttoDST");
                q.setParameter("idUffOwn", idUfficio);
                q.setParameter("idProc", idProcedimento, Hibernate.LONG);
                if (atto.getDataRegistrazione() != null) {
                    q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
                } else {
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

    public Long generaNumeroAttoDSST(final long idUfficio, final long idProcedimento, final TAtto atto,
    		final String codUtente, final long tipoAtto, final long categoriaAtto, final long idStatoAtto,
    		final String nomeMetodo) {

        return (Long) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_NUMERO_ATTO_generaNumeroAttoDSST");
                q.setParameter("idUffOwn", idUfficio);
                q.setParameter("idProc", idProcedimento, Hibernate.LONG);
                if (atto.getDataRegistrazione() != null) {
                    q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
                } else {
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

    public Long generaNumeroAttoDPST(final long idUfficio, final long idProcedimento, final TAtto atto,
    		final String codUtente, final long tipoAtto,final long categoriaAtto, final long idStatoAtto,
    		final String nomeMetodo) {

        return (Long) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                Query q = session.getNamedQuery("PG_NUMERO_ATTO_generaNumeroAttoDPST");
                q.setParameter("idUffOwn", idUfficio);
                q.setParameter("idProc", idProcedimento, Hibernate.LONG);
                if (atto.getDataRegistrazione() != null) {
                    q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
                } else {
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

    public Long generaNumeroAttoSRC(final long idUffOwn, final long idUff1, final long idUff2, final long idUff3,
			final long idProcedimento, final TAtto atto, final String codUtente) {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Long idAtto = null;
				Query q = session.getNamedQuery("PG_NUMERO_ATTO_generaNumeroAttoSRC");

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

    public Long generaNumeroAttoSOD(final long idUffOwn, final long idUff1, final long idUff2, final long idUff3,
			final long idProcedimento, final TAtto atto, final String codUtente) {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Long idAtto = null;
				Query q = session.getNamedQuery("PG_NUMERO_ATTO_generaNumeroAttoSOD");

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

    public Long generaNumeroAttoDO(final long idUffOwn, final long idProcInUdienza, final long idProcGenerale,
			final long idProcedimento, final TAtto atto, final String codUtente, final int idEsito) {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Long idAtto = null;
				Query q = session.getNamedQuery("PG_NUMERO_ATTO_generaNumeroAttoDO");

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
	// ***** FINE INTERVENTO MEV_006 *****//

}