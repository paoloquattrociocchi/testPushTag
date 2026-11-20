package it.giustizia.smp.dao;

import it.giustizia.smp.dto.pg_procedimento.AssociaPersonaDto;
import it.giustizia.smp.dto.pg_procedimento.ContaDifensoriSogIntDto;
import it.giustizia.smp.dto.pg_procedimento.ListaProcedimentiSoggDto;
import it.giustizia.smp.dto.pg_procedimento.MisurePatrSoggDto;
import it.giustizia.smp.dto.pg_procedimento.RicercaProcedimentoDto;
import it.giustizia.smp.dto.pg_procedimento.SogIntDettaglioProcedimentoDto;
import it.giustizia.smp.dto.pg_procedimento.SogTitDettaglioProcedimentoDto;
import it.giustizia.smp.dto.pg_soggetto.DettaglioPersonaFisicaDto;
import it.giustizia.smp.dto.pg_soggetto.DettaglioPersonaFisicaProcDto;
import it.giustizia.smp.dto.pg_soggetto.DettaglioPersonaGiurProcDto;
import it.giustizia.smp.model.TAnComuni;
import it.giustizia.smp.model.TAnMagistrati;
import it.giustizia.smp.model.TAnManuali;
import it.giustizia.smp.model.TAnNormative;
import it.giustizia.smp.model.TAnProcedente;
import it.giustizia.smp.model.TAnStato;
import it.giustizia.smp.model.TAnUfficio;
import it.giustizia.smp.model.TAtto;
import it.giustizia.smp.model.TAvvocati;
import it.giustizia.smp.model.TCassMsg;
import it.giustizia.smp.model.TComunicazione;
import it.giustizia.smp.model.TDefPatr;
import it.giustizia.smp.model.TDefPers;
import it.giustizia.smp.model.TIstanza;
import it.giustizia.smp.model.TMisuraPers;
import it.giustizia.smp.model.TPersonaFisica;
import it.giustizia.smp.model.TPersonaFisicaId;
import it.giustizia.smp.model.TPersonaGiur;
import it.giustizia.smp.model.TPersonaGiurId;
import it.giustizia.smp.model.TProcedimento;
import it.giustizia.smp.model.TRelAttoMisPatr;
import it.giustizia.smp.model.TRelAttoMisPatrId;
import it.giustizia.smp.model.TRelAttoMisPers;
import it.giustizia.smp.model.TRelAttoProc;
import it.giustizia.smp.model.TRelAttoStatoAtto;
import it.giustizia.smp.model.TRelBeneAtto;
import it.giustizia.smp.model.TRelIterProc;
import it.giustizia.smp.model.TRelMagProc;
import it.giustizia.smp.model.TRelMagProcId;
import it.giustizia.smp.model.TRelMagUff;
import it.giustizia.smp.model.TRelProcBene;
import it.giustizia.smp.model.TRelProcCoadiutori;
import it.giustizia.smp.model.TRelProcIntestatariAvv;
import it.giustizia.smp.model.TRelProcProc;
import it.giustizia.smp.model.TRelProcSogint;
import it.giustizia.smp.model.TRelProcSogintAvv;
import it.giustizia.smp.model.TRelProcSogintId;
import it.giustizia.smp.model.TRelProcSogintVer;
import it.giustizia.smp.model.TRelSoggIst;
import it.giustizia.smp.model.TRelSogintErediConv;
import it.giustizia.smp.model.TRelSogintErediConvId;
import it.giustizia.smp.model.TRelSoginvBene;
import it.giustizia.smp.model.TRelSoginvBeneId;
import it.giustizia.smp.model.TRelSoginvSogint;
import it.giustizia.smp.model.TRelSoginvSogintId;
import it.giustizia.smp.model.TSoggetto;
import it.giustizia.smp.model.TTpCatAtto;
import it.giustizia.smp.model.TTpEsito;
import it.giustizia.smp.model.TTpStatoAtto;
import it.giustizia.smp.model.TTpStatoProc;
import it.giustizia.smp.model.TTpTipoAtto;
import it.giustizia.smp.model.TTpTipoInteresse;
import it.giustizia.smp.model.TTpTipoMisura;
import it.giustizia.smp.model.TTpTipoPrevProc;
import it.giustizia.smp.model.TTpTipoUfficio;
import it.giustizia.smp.model.TUdienza;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.TipoFonteComparator;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.utility.tree.ProcedimentoTree;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * ProcedimentoDaoImpl
 * 
 * @author Almaviva S.p.A. classe dao all'interno della quale viene fatto
 *         l'accesso al DB
 */
@SuppressWarnings("unchecked")
public class ProcedimentoDaoImpl extends HibernateDaoSupport implements ProcedimentoDao {

	//private final static int CODICE_ESITO_1_ACCOGLIE = 28;
	//private final static int CODICE_ESITO_2_ACCOGLIE = 27;
	//private final static int CODICE_ESITO_FIN_APPLICA = 1;
	@Autowired(required = true)
	private HttpServletRequest request;
	
	public long aggiornaPersonaFisica(final AssociaPersonaDto dto, final long idProcedimento, final String codUtente) {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_SOGGETTO_aggiornaPersFis");
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				// valorizzazione chiave
				q.setParameter("idPersFis", dto.getIDPERS().longValue());
				q.setParameter("versione", dto.getIDVER().longValue());
				q.setParameter("idUffOwn", dto.getIDUFFOWN().longValue());
				// valorizzazione dei campi
				TPersonaFisica persona = dto.getPersFis();
				q.setParameter("descUtente", codUtente);
				q.setParameter("cognome", persona.getDescCognome(), Hibernate.STRING);
				q.setParameter("nome", persona.getDescNome(), Hibernate.STRING);
				q.setParameter("sesso", persona.getFlgSesso(), Hibernate.CHARACTER);
				q.setParameter("dataNascita", persona.getDataNascita(), Hibernate.DATE);
				q.setParameter("flgDataPresunta", persona.getFlgDataPresunta(), Hibernate.CHARACTER);
				q.setParameter("flgPersDaIdent", persona.getFlgValiditaVer(), Hibernate.CHARACTER);
				if ((persona.getTAnStatoByCodiFkIdStatoNasc() != null) && (persona.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato() > 0))
					q.setParameter("idStatoNasc", persona.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato(), Hibernate.INTEGER);
				else
					q.setParameter("idStatoNasc", null, Hibernate.INTEGER);
				q.setParameter("comuneEsteroNasc", persona.getDescComEsteroNasc(), Hibernate.STRING);
				if ((persona.getTAnComuniByCodiFkIdComNasc() != null) && (persona.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune() > 0))
					q.setParameter("idComuneNasc", persona.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune(), Hibernate.INTEGER);
				else
					q.setParameter("idComuneNasc", null, Hibernate.INTEGER);
				q.setParameter("codiceFiscale", persona.getDescCodiceFiscale(), Hibernate.STRING);
				q.setParameter("cui", persona.getDescCui(), Hibernate.STRING);
				q.setParameter("nomePadre", persona.getDescNomePadre(), Hibernate.STRING);
				q.setParameter("cognomePadre", persona.getDescCogPadre(), Hibernate.STRING);
				q.setParameter("nomeMadre", persona.getDescNomeMadre(), Hibernate.STRING);
				q.setParameter("cognomeMadre", persona.getDescCogMadre(), Hibernate.STRING);
				q.setParameter("dataDecesso", persona.getDataDecesso(), Hibernate.DATE);
				if ((persona.getTAnStatoByCodiFkIdStatoMorte() != null) && (persona.getTAnStatoByCodiFkIdStatoMorte().getCodiKIdStato() > 0))
					q.setParameter("idStatoMorte", persona.getTAnStatoByCodiFkIdStatoMorte().getCodiKIdStato(), Hibernate.INTEGER);
				else
					q.setParameter("idStatoMorte", null, Hibernate.INTEGER);
				q.setParameter("comuneEsteroMorte", persona.getDescComEsteroMorte(), Hibernate.STRING);
				if ((persona.getTAnComuniByCodiFkIdComMorte() != null) && (persona.getTAnComuniByCodiFkIdComMorte().getCodiKIdComune() > 0))
					q.setParameter("idComuneMorte", persona.getTAnComuniByCodiFkIdComMorte().getCodiKIdComune(), Hibernate.INTEGER);
				else
					q.setParameter("idComuneMorte", null, Hibernate.INTEGER);
				TRelProcSogint relProcSoggInt = dto.getRelProcSoggInt();
				if ((relProcSoggInt.getTTpProfessione() != null) && (relProcSoggInt.getTTpProfessione().getCodiKIdProfessione() > 0))
					q.setParameter("idProfess", relProcSoggInt.getTTpProfessione().getCodiKIdProfessione(), Hibernate.INTEGER);
				else
					q.setParameter("idProfess", null, Hibernate.INTEGER);
				if ((relProcSoggInt.getTTpCondGiur() != null) && (relProcSoggInt.getTTpCondGiur().getCodiKIdCondGiur() > 0))
					q.setParameter("idCondGiur", relProcSoggInt.getTTpCondGiur().getCodiKIdCondGiur(), Hibernate.INTEGER);
				else
					q.setParameter("idCondGiur", null, Hibernate.INTEGER);
				if ((relProcSoggInt.getTAnIstitutoDet() != null) && (relProcSoggInt.getTAnIstitutoDet().getCodiKIdIstituto() > 0))
					q.setParameter("idIstDet", relProcSoggInt.getTAnIstitutoDet().getCodiKIdIstituto(), Hibernate.INTEGER);
				else
					q.setParameter("idIstDet", null, Hibernate.INTEGER);
				q.setParameter("descIstEstero", relProcSoggInt.getDescIstDetEstero(), Hibernate.STRING);
				if ((relProcSoggInt.getTAnStatoByCodiFkIdStatoRes() != null)
						&& (relProcSoggInt.getTAnStatoByCodiFkIdStatoRes().getCodiKIdStato() > 0))
					q.setParameter("idStatoRes", relProcSoggInt.getTAnStatoByCodiFkIdStatoRes().getCodiKIdStato(), Hibernate.INTEGER);
				else
					q.setParameter("idStatoRes", null, Hibernate.INTEGER);
				q.setParameter("descComuneEstRes", relProcSoggInt.getDescComEsteroRes(), Hibernate.STRING);
				if ((relProcSoggInt.getTAnComuniByCodiFkIdComRes() != null) && (relProcSoggInt.getTAnComuniByCodiFkIdComRes().getCodiKIdComune() > 0))
					q.setParameter("idComuneRes", relProcSoggInt.getTAnComuniByCodiFkIdComRes().getCodiKIdComune(), Hibernate.INTEGER);
				else
					q.setParameter("idComuneRes", null, Hibernate.INTEGER);
				if ((relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraRes() != null)
						&& (relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraRes().getCodiKIdTipoStrada() > 0))
					q.setParameter("idTipoStradaRes", relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraRes().getCodiKIdTipoStrada(),
							Hibernate.INTEGER);
				else
					q.setParameter("idTipoStradaRes", null, Hibernate.INTEGER);
				q.setParameter("indirizzoRes", relProcSoggInt.getDescIndirRes(), Hibernate.STRING);
				q.setParameter("numCivicoRes", relProcSoggInt.getDescCivicoRes(), Hibernate.STRING);
				q.setParameter("capRes", relProcSoggInt.getTAnComuniByCodiFkIdComRes().getDescCapComune(), Hibernate.STRING);
				if ((relProcSoggInt.getTAnStatoByCodiFkIdStatoDom() != null)
						&& (relProcSoggInt.getTAnStatoByCodiFkIdStatoDom().getCodiKIdStato() > 0))
					q.setParameter("idStatoDom", relProcSoggInt.getTAnStatoByCodiFkIdStatoDom().getCodiKIdStato(), Hibernate.INTEGER);
				else
					q.setParameter("idStatoDom", null, Hibernate.INTEGER);
				q.setParameter("descComuneEstDom", relProcSoggInt.getDescComEsteroDom(), Hibernate.STRING);
				if ((relProcSoggInt.getTAnComuniByCodiFkIdComDom() != null) && (relProcSoggInt.getTAnComuniByCodiFkIdComDom().getCodiKIdComune() > 0))
					q.setParameter("idComuneDom", relProcSoggInt.getTAnComuniByCodiFkIdComDom().getCodiKIdComune(), Hibernate.INTEGER);
				else
					q.setParameter("idComuneDom", null, Hibernate.INTEGER);
				if ((relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDom() != null)
						&& (relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDom().getCodiKIdTipoStrada() > 0))
					q.setParameter("idTipoStradaDom", relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDom().getCodiKIdTipoStrada(),
							Hibernate.INTEGER);
				else
					q.setParameter("idTipoStradaDom", null, Hibernate.INTEGER);
				q.setParameter("indirizzoDom", relProcSoggInt.getDescIndirDom(), Hibernate.STRING);
				q.setParameter("numCivicoDom", relProcSoggInt.getDescCivicoDom(), Hibernate.STRING);
				q.setParameter("capDom", relProcSoggInt.getTAnComuniByCodiFkIdComDom().getDescCapComune(), Hibernate.STRING);
				if ((relProcSoggInt.getTAnStatoByCodiFkIdStatoDim() != null)
						&& (relProcSoggInt.getTAnStatoByCodiFkIdStatoDim().getCodiKIdStato() > 0))
					q.setParameter("idStatoDim", relProcSoggInt.getTAnStatoByCodiFkIdStatoDim().getCodiKIdStato(), Hibernate.INTEGER);
				else
					q.setParameter("idStatoDim", null, Hibernate.INTEGER);
				q.setParameter("descComuneEstDim", relProcSoggInt.getDescComEsteroDim(), Hibernate.STRING);
				if ((relProcSoggInt.getTAnComuniByCodiFkIdComDim() != null) && (relProcSoggInt.getTAnComuniByCodiFkIdComDim().getCodiKIdComune() > 0))
					q.setParameter("idComuneDim", relProcSoggInt.getTAnComuniByCodiFkIdComDim().getCodiKIdComune(), Hibernate.INTEGER);
				else
					q.setParameter("idComuneDim", null, Hibernate.INTEGER);
				if ((relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDim() != null)
						&& (relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDim().getCodiKIdTipoStrada() > 0))
					q.setParameter("idTipoStradaDim", relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDim().getCodiKIdTipoStrada(),
							Hibernate.INTEGER);
				else
					q.setParameter("idTipoStradaDim", null, Hibernate.INTEGER);
				q.setParameter("indirizzoDim", relProcSoggInt.getDescIndirDim(), Hibernate.STRING);
				q.setParameter("numCivicoDim", relProcSoggInt.getDescCivicoDim(), Hibernate.STRING);

				if (relProcSoggInt.getTAnComuniByCodiFkIdComDim() != null)
					q.setParameter("capDim", relProcSoggInt.getTAnComuniByCodiFkIdComDim().getDescCapComune(), Hibernate.STRING);
				else
					q.setParameter("capDim", null, Hibernate.STRING);

				if ((relProcSoggInt.getTAnStatoByCodiFkIdStatoDomel() != null)
						&& (relProcSoggInt.getTAnStatoByCodiFkIdStatoDomel().getCodiKIdStato() > 0))
					q.setParameter("idStatoDomel", relProcSoggInt.getTAnStatoByCodiFkIdStatoDomel().getCodiKIdStato(), Hibernate.INTEGER);
				else
					q.setParameter("idStatoDomel", null, Hibernate.INTEGER);
				q.setParameter("descComuneEstDomel", relProcSoggInt.getDescComEsteroDomel(), Hibernate.STRING);
				if ((relProcSoggInt.getTAnComuniByCodiFkIdComDomel() != null)
						&& (relProcSoggInt.getTAnComuniByCodiFkIdComDomel().getCodiKIdComune() > 0))
					q.setParameter("idComuneDomel", relProcSoggInt.getTAnComuniByCodiFkIdComDomel().getCodiKIdComune(), Hibernate.INTEGER);
				else
					q.setParameter("idComuneDomel", null, Hibernate.INTEGER);
				if ((relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDomel() != null)
						&& (relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDomel().getCodiKIdTipoStrada() > 0))
					q.setParameter("idTipoStradaDomel", relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDomel().getCodiKIdTipoStrada(),
							Hibernate.INTEGER);
				else
					q.setParameter("idTipoStradaDomel", null, Hibernate.INTEGER);
				q.setParameter("indirizzoDomel", relProcSoggInt.getDescIndirDomel(), Hibernate.STRING);
				if (relProcSoggInt.getDescCivicoDomel() != null)
					q.setParameter("numCivicoDomel", relProcSoggInt.getDescCivicoDomel(), Hibernate.STRING);
				else
					q.setParameter("numCivicoDomel", null, Hibernate.STRING);

				if (relProcSoggInt.getTAnComuniByCodiFkIdComDomel() != null)
					q.setParameter("capDomel", relProcSoggInt.getTAnComuniByCodiFkIdComDomel().getDescCapComune(), Hibernate.STRING);
				else
					q.setParameter("capDomel", null, Hibernate.STRING);

				if ((relProcSoggInt.getDescDomelPresso() != null) && !relProcSoggInt.getDescDomelPresso().equals("-1"))
					q.setParameter("pressoDomel", relProcSoggInt.getDescDomelPresso(), Hibernate.STRING);
				else
					q.setParameter("pressoDomel", null, Hibernate.STRING);
				q.setParameter("domelNota", relProcSoggInt.getDescDomelNota(), Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long id = (Long) first.get("IDPRS");
				return id;
			}
		});
	}

	public long aggiornaPersonaGiuridica(final AssociaPersonaDto dto, final long idProcedimento, final String codUtente) {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_SOGGETTO_aggiornaPersGiur");
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				// valorizzazione chiave
				q.setParameter("idPersGiur", dto.getIDPERS().longValue());
				q.setParameter("versione", dto.getIDVER().longValue());
				q.setParameter("idUffOwn", dto.getIDUFFOWN().longValue());
				// valorizzazione dei campi
				TPersonaGiur persona = dto.getPersonaGiur();
				q.setParameter("descUtente", codUtente);

				q.setParameter("numCCIAA", persona.getDescNumeCciaa(), Hibernate.STRING);
				if ((persona.getTTpSettoreAtt() != null) && (persona.getTTpSettoreAtt().getCodiKIdSettoreAtt() > 0))
					q.setParameter("idSettAtt", persona.getTTpSettoreAtt().getCodiKIdSettoreAtt(), Hibernate.INTEGER);
				else
					q.setParameter("idSettAtt", null, Hibernate.INTEGER);
				if (persona.getImpoCapSoc() != null)
					q.setParameter("impoCapSoc", persona.getImpoCapSoc(), Hibernate.BIG_DECIMAL);
				else
					q.setParameter("impoCapSoc", null, Hibernate.BIG_DECIMAL);
				q.setParameter("note", persona.getDescNote(), Hibernate.STRING);
				q.setParameter("descRapprSoc", persona.getDescRapprSoc(), Hibernate.STRING);
				q.setParameter("descAmmin", persona.getDescAmmin(), Hibernate.STRING);

				TRelProcSogint relProcSoggInt = dto.getRelProcSoggInt();
				if ((relProcSoggInt.getTAnStatoByCodiFkIdStatoDomel() != null)
						&& (relProcSoggInt.getTAnStatoByCodiFkIdStatoDomel().getCodiKIdStato() > 0))
					q.setParameter("idStatoDomel", relProcSoggInt.getTAnStatoByCodiFkIdStatoDomel().getCodiKIdStato(), Hibernate.INTEGER);
				else
					q.setParameter("idStatoDomel", null, Hibernate.INTEGER);
				q.setParameter("descComuneEstDomel", relProcSoggInt.getDescComEsteroDomel(), Hibernate.STRING);
				if ((relProcSoggInt.getTAnComuniByCodiFkIdComDomel() != null)
						&& (relProcSoggInt.getTAnComuniByCodiFkIdComDomel().getCodiKIdComune() > 0))
					q.setParameter("idComuneDomel", relProcSoggInt.getTAnComuniByCodiFkIdComDomel().getCodiKIdComune(), Hibernate.INTEGER);
				else
					q.setParameter("idComuneDomel", null, Hibernate.INTEGER);
				if ((relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDomel() != null)
						&& (relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDomel().getCodiKIdTipoStrada() > 0))
					q.setParameter("idTipoStradaDomel", relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDomel().getCodiKIdTipoStrada(),
							Hibernate.INTEGER);
				else
					q.setParameter("idTipoStradaDomel", null, Hibernate.INTEGER);
				q.setParameter("indirizzoDomel", relProcSoggInt.getDescIndirDomel(), Hibernate.STRING);
				if (relProcSoggInt.getTAnComuniByCodiFkIdComDomel() != null)
					q.setParameter("numCivicoDomel", relProcSoggInt.getDescCivicoDomel(), Hibernate.STRING);
				else
					q.setParameter("numCivicoDomel", null, Hibernate.STRING);
				q.setParameter("pressoDomel", relProcSoggInt.getDescDomelPresso(), Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List<Map> l = q.list();
				Map first = l.get(0);
				Long id = (Long) first.get("IDPRS_GIUR");
				return id;
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public Long aggiornaProcedimento(final long idUffOwn, final String codUtente, final TProcedimento proc, final Date dataUltAggiornamento) {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Long idProc = null;
				Query q = session.getNamedQuery("PG_PROCEDIMENTO_aggiornaProcedimento");
				q.setParameter("idProc", proc.getCodiKIdProc(), Hibernate.LONG);
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("descUtente", codUtente);
				if ((proc.getTTpTipoProc() != null) && (proc.getTTpTipoProc().getCodiKIdTipoProc() > 0))
					q.setParameter("idTipoProc", proc.getTTpTipoProc().getCodiKIdTipoProc(), Hibernate.INTEGER);
				else
					q.setParameter("idTipoProc", null, Hibernate.INTEGER);
				q.setParameter("flgOrdinaria", proc.getFlgOrdinaria(), Hibernate.CHARACTER);
				q.setParameter("flgAntimafia", proc.getFlgAntimafia(), Hibernate.CHARACTER);
				q.setParameter("flgAntiterrorismo", proc.getFlgAntiterrorismo(), Hibernate.CHARACTER);
				q.setParameter("dataUltAggiornamento", dataUltAggiornamento, Hibernate.TIMESTAMP);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List list = q.list();
				Iterator it = list.iterator();
				while (it.hasNext()) {
					Map<String, Object> row = (Map<String, Object>) it.next();
					idProc = (Long) row.get("IDPROC");
				}
				return idProc;
			}
		});
	}

	public Long aggiornaPropostaAtto(final long idUfficio, final long idDestinatario, final long idUltDestinatario, final long idProcedimento,
			final TAtto atto, final String codUtente, final TIstanza istanza) {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Long idAtto = null;
				Query q = session.getNamedQuery("PG_ATTO_aggiornaPropostaAtto");
				q.setParameter("idUffOwn", idUfficio);
				q.setParameter("idProc", idProcedimento, Hibernate.LONG);
				if (atto.getDataRegistrazione() != null) {
					q.setParameter("idMittente", idUfficio, Hibernate.LONG);
					//P.Q. 21/06/2021 Mev 9 Inizio
					if (idDestinatario > 0)
						q.setParameter("idDestinatario", idDestinatario, Hibernate.LONG);
					else
						q.setParameter("idDestinatario", null, Hibernate.LONG);
					//P.Q. 21/06/2021 Mev 9 Fine
					if (idUltDestinatario > 0)
						q.setParameter("idUltDestinatario", idUltDestinatario, Hibernate.LONG);
					else
						q.setParameter("idUltDestinatario", null, Hibernate.LONG);
					q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				} else {
					q.setParameter("idMittente", null, Hibernate.LONG);
					q.setParameter("idDestinatario", null, Hibernate.LONG);
					q.setParameter("idUltDestinatario", null, Hibernate.LONG);
					q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				}
				if (atto.getDataEmissione() != null)
					q.setParameter("dataEmissione", atto.getDataEmissione(), Hibernate.DATE);
				else
					q.setParameter("dataEmissione", null, Hibernate.DATE);
				if (atto.getDataDeposito() != null)
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				else
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				if (atto.getDataRicezione() != null)
					q.setParameter("dataRicezioneDoc", atto.getDataRicezione(), Hibernate.DATE);
				else
					q.setParameter("dataRicezioneDoc", null, Hibernate.DATE);
				if (istanza != null && istanza.getDescNumeRif() != null)
					q.setParameter("numeroRif", istanza.getDescNumeRif(), Hibernate.STRING);
				else
					q.setParameter("numeroRif", null, Hibernate.STRING);
				if (istanza != null && istanza.getDescRichiesta() != null)
					q.setParameter("contDett", istanza.getDescRichiesta());
				else
					q.setParameter("contDett", null, Hibernate.STRING);

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

	@SuppressWarnings("rawtypes")
	public Long aggiornaStatoDelProcedimento(final long idProcedimento, final long idStatoProcedimentoNuovo, final Long idAtto,
			final int idUfficioUtente, final String codiceUtente) {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			@SuppressWarnings("unused")
			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query query = session.getNamedQuery("PG_PROCEDIMENTO_aggiorna_stato_proc");
				query.setParameter("idProc", idProcedimento, Hibernate.LONG);
				query.setParameter("idStatoProc", idStatoProcedimentoNuovo, Hibernate.LONG);
				query.setParameter("idAtto", idAtto, Hibernate.LONG);
				query.setParameter("idUffOwn", idUfficioUtente);
				query.setParameter("descUtente", codiceUtente);
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = query.list();
				return new Long(0);
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public Long annullaStatoDelProcedimento(final long idProcedimento, final long idStatoProcedimentoNuovo, final Long idAtto,
			final int idUfficioUtente, final String codiceUtente) {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			@SuppressWarnings("unused")
			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query query = session.getNamedQuery("PG_PROCEDIMENTO_aggiorna_stato_proc");
				query.setParameter("idProc", idProcedimento, Hibernate.LONG);
				query.setParameter("idStatoProc", idStatoProcedimentoNuovo, Hibernate.LONG);
				query.setParameter("idAtto", idAtto, Hibernate.LONG);
				query.setParameter("idUffOwn", idUfficioUtente);
				query.setParameter("descUtente", codiceUtente);
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = query.list();
				return new Long(0);
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

	@SuppressWarnings("rawtypes")
	public Long associaAggravamentoAlPadre(final Long idAggravamento, final Long idPadre, final Long numero, final Short anno,
			final Integer idUffOwn, final String codUtente) {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			@SuppressWarnings("unused")
			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query query = session.getNamedQuery("PG_PROCEDIMENTO_associa_aggravamento");
				query.setParameter("idAggravamento", idAggravamento, Hibernate.LONG);
				query.setParameter("idPadre", idPadre, Hibernate.LONG);
				query.setParameter("numeroPadre", numero, Hibernate.LONG);
				query.setParameter("idUffOwn", idUffOwn, Hibernate.INTEGER);
				query.setParameter("annoPadre", anno, Hibernate.SHORT);
				query.setParameter("descUtente", codUtente);
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = query.list();
				return new Long(0);
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public void associaMagistratoAlProcedimento(final Long idMagistrato, final Date dataFineAssegnazione, final String tipoSostituzione,
			final Byte ordineDiVisualizzazione, final String relatore, final Date dataAssegnazione, final String ruolo, final String altroMotivo,
			final long idProcedimento, final Integer idUfficioUtente) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				TRelMagProc relazione = new TRelMagProc();
				relazione.setDataFineAssegn(dataFineAssegnazione);
				relazione.setDescMotivoSost(tipoSostituzione);
				relazione.setPrgVisual(ordineDiVisualizzazione);
				if (altroMotivo != null)
					relazione.setDescAltroMotivo(altroMotivo);
				if (Utility.isDefinito(relatore))
					relazione.setFlgRelatore(new Character(relatore.charAt(0)));

				TRelMagProcId id = new TRelMagProcId();
				id.setCodiKIdProc(idProcedimento);
				id.setCodiKIdUffOwner(idUfficioUtente);
				id.setCodiKIdMagistrato(idMagistrato);
				id.setCodiKIdDataAss(dataAssegnazione);
				id.setCodiKIdRuoloMag(Integer.valueOf(ruolo));
				relazione.setId(id);
				session.saveOrUpdate(relazione);
				session.flush();
				return null;
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public List<TRelMagProc> verificaOrdineMagistratoProcedimento(final long idProcedimento, final int idUfficioUtente, final byte numOrdine,
			final long idMagistrato) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TRelMagProc.class);
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente));
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		criteria.add(Restrictions.eq("prgVisual", numOrdine));
		criteria.add(Restrictions.isNull("dataFineAssegn"));
		criteria.add(Restrictions.ne("id.codiKIdMagistrato", idMagistrato));
		List<TRelMagProc> relazioni = getHibernateTemplate().findByCriteria(criteria);
		return relazioni;
	}

	public AssociaPersonaDto associaNuovaPersonaFisica(final int idUffOwn, final String codUtente, final Long idProc,
			final AssociaPersonaDto soggetto, final Date dataUltAggiornamento) {

		return (AssociaPersonaDto) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				AssociaPersonaDto dto = new AssociaPersonaDto();
				Query q = session.getNamedQuery("PG_SOGGETTO_associaNuovaPersFis");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("descUtente", codUtente);
				q.setParameter("idProc", idProc, Hibernate.LONG);
				TPersonaFisica persona = soggetto.getPersFis();
				soggetto.getSoggInt();
				TRelProcSogint relProcSoggInt = soggetto.getRelProcSoggInt();
				q.setParameter("cognome", persona.getDescCognome(), Hibernate.STRING);
				q.setParameter("nome", persona.getDescNome(), Hibernate.STRING);
				q.setParameter("sesso", persona.getFlgSesso(), Hibernate.CHARACTER);
				q.setParameter("dataNascita", persona.getDataNascita(), Hibernate.DATE);
				q.setParameter("flgDataPresunta", persona.getFlgDataPresunta(), Hibernate.CHARACTER);
				if ((persona.getTAnStatoByCodiFkIdStatoNasc() != null) && (persona.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato() > 0))
					q.setParameter("idStatoNasc", persona.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato(), Hibernate.INTEGER);
				else
					q.setParameter("idStatoNasc", null, Hibernate.INTEGER);
				q.setParameter("comuneEsteroNasc", persona.getDescComEsteroNasc(), Hibernate.STRING);
				if ((persona.getTAnComuniByCodiFkIdComNasc() != null) && (persona.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune() > 0))
					q.setParameter("idComuneNasc", persona.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune(), Hibernate.INTEGER);
				else
					q.setParameter("idComuneNasc", null, Hibernate.INTEGER);
				q.setParameter("codiceFiscale", persona.getDescCodiceFiscale(), Hibernate.STRING);
				q.setParameter("cui", persona.getDescCui(), Hibernate.STRING);
				q.setParameter("nomePadre", persona.getDescNomePadre(), Hibernate.STRING);
				q.setParameter("cognomePadre", persona.getDescCogPadre(), Hibernate.STRING);
				q.setParameter("nomeMadre", persona.getDescNomeMadre(), Hibernate.STRING);
				q.setParameter("cognomeMadre", persona.getDescCogMadre(), Hibernate.STRING);
				q.setParameter("dataDecesso", persona.getDataDecesso(), Hibernate.DATE);
				if ((persona.getTAnStatoByCodiFkIdStatoMorte() != null) && (persona.getTAnStatoByCodiFkIdStatoMorte().getCodiKIdStato() > 0))
					q.setParameter("idStatoMorte", persona.getTAnStatoByCodiFkIdStatoMorte().getCodiKIdStato(), Hibernate.INTEGER);
				else
					q.setParameter("idStatoMorte", null, Hibernate.INTEGER);
				q.setParameter("comuneEsteroMorte", persona.getDescComEsteroMorte(), Hibernate.STRING);
				if ((persona.getTAnComuniByCodiFkIdComMorte() != null) && (persona.getTAnComuniByCodiFkIdComMorte().getCodiKIdComune() > 0))
					q.setParameter("idComuneMorte", persona.getTAnComuniByCodiFkIdComMorte().getCodiKIdComune(), Hibernate.INTEGER);
				else
					q.setParameter("idComuneMorte", null, Hibernate.INTEGER);
				/**
				 * TODO if ((persona.getTStatoEsteroByCodiFkIdStatoCitt() !=
				 * null) && (persona.getTStatoEsteroByCodiFkIdStatoCitt().
				 * getCodiKIdStato() > 0)) {
				 * q.setParameter("idStatoCittadinanza",
				 * persona.getTStatoEsteroByCodiFkIdStatoCitt
				 * ().getCodiKIdStato(), Hibernate.LONG); } else {
				 * q.setParameter("idStatoCittadinanza", null, Hibernate.LONG);
				 * }
				 */
				if ((relProcSoggInt.getTTpProfessione() != null) && (relProcSoggInt.getTTpProfessione().getCodiKIdProfessione() > 0))
					q.setParameter("idProfess", relProcSoggInt.getTTpProfessione().getCodiKIdProfessione(), Hibernate.INTEGER);
				else
					q.setParameter("idProfess", null, Hibernate.INTEGER);
				if ((relProcSoggInt.getTTpCondGiur() != null) && (relProcSoggInt.getTTpCondGiur().getCodiKIdCondGiur() > 0))
					q.setParameter("idCondGiur", relProcSoggInt.getTTpCondGiur().getCodiKIdCondGiur(), Hibernate.INTEGER);
				else
					q.setParameter("idCondGiur", null, Hibernate.INTEGER);
				if ((relProcSoggInt.getTAnIstitutoDet() != null) && (relProcSoggInt.getTAnIstitutoDet().getCodiKIdIstituto() > 0))
					q.setParameter("idIstDet", relProcSoggInt.getTAnIstitutoDet().getCodiKIdIstituto(), Hibernate.INTEGER);
				else
					q.setParameter("idIstDet", null, Hibernate.LONG);
				q.setParameter("descIstEstero", relProcSoggInt.getDescIstDetEstero(), Hibernate.STRING);
				if ((relProcSoggInt.getTAnStatoByCodiFkIdStatoRes() != null)
						&& (relProcSoggInt.getTAnStatoByCodiFkIdStatoRes().getCodiKIdStato() > 0))
					q.setParameter("idStatoRes", relProcSoggInt.getTAnStatoByCodiFkIdStatoRes().getCodiKIdStato(), Hibernate.INTEGER);
				else
					q.setParameter("idStatoRes", null, Hibernate.LONG);
				q.setParameter("descComuneEstRes", relProcSoggInt.getDescComEsteroRes(), Hibernate.STRING);
				if ((relProcSoggInt.getTAnComuniByCodiFkIdComRes() != null) && (relProcSoggInt.getTAnComuniByCodiFkIdComRes().getCodiKIdComune() > 0))
					q.setParameter("idComuneRes", relProcSoggInt.getTAnComuniByCodiFkIdComRes().getCodiKIdComune(), Hibernate.INTEGER);
				else
					q.setParameter("idComuneRes", null, Hibernate.INTEGER);
				if ((relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraRes() != null)
						&& (relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraRes().getCodiKIdTipoStrada() > 0))
					q.setParameter("idTipoStradaRes", relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraRes().getCodiKIdTipoStrada(),
							Hibernate.INTEGER);
				else
					q.setParameter("idTipoStradaRes", null, Hibernate.INTEGER);
				q.setParameter("indirizzoRes", relProcSoggInt.getDescIndirRes(), Hibernate.STRING);
				q.setParameter("numCivicoRes", relProcSoggInt.getDescCivicoRes(), Hibernate.STRING);
				q.setParameter("capRes", relProcSoggInt.getTAnComuniByCodiFkIdComRes().getDescCapComune(), Hibernate.STRING);
				if ((relProcSoggInt.getTAnStatoByCodiFkIdStatoDom() != null)
						&& (relProcSoggInt.getTAnStatoByCodiFkIdStatoDom().getCodiKIdStato() > 0))
					q.setParameter("idStatoDom", relProcSoggInt.getTAnStatoByCodiFkIdStatoDom().getCodiKIdStato(), Hibernate.INTEGER);
				else
					q.setParameter("idStatoDom", null, Hibernate.INTEGER);
				q.setParameter("descComuneEstDom", relProcSoggInt.getDescComEsteroDom(), Hibernate.STRING);
				if ((relProcSoggInt.getTAnComuniByCodiFkIdComDom() != null) && (relProcSoggInt.getTAnComuniByCodiFkIdComDom().getCodiKIdComune() > 0))
					q.setParameter("idComuneDom", relProcSoggInt.getTAnComuniByCodiFkIdComDom().getCodiKIdComune(), Hibernate.INTEGER);
				else
					q.setParameter("idComuneDom", null, Hibernate.INTEGER);
				if ((relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDom() != null)
						&& (relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDom().getCodiKIdTipoStrada() > 0))
					q.setParameter("idTipoStradaDom", relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDom().getCodiKIdTipoStrada(),
							Hibernate.INTEGER);
				else
					q.setParameter("idTipoStradaDom", null, Hibernate.INTEGER);
				q.setParameter("indirizzoDom", relProcSoggInt.getDescIndirDom(), Hibernate.STRING);
				q.setParameter("numCivicoDom", relProcSoggInt.getDescCivicoDom(), Hibernate.STRING);
				q.setParameter("capDom", relProcSoggInt.getTAnComuniByCodiFkIdComDom().getDescCapComune(), Hibernate.STRING);
				if ((relProcSoggInt.getTAnStatoByCodiFkIdStatoDim() != null)
						&& (relProcSoggInt.getTAnStatoByCodiFkIdStatoDim().getCodiKIdStato() > 0))
					q.setParameter("idStatoDim", relProcSoggInt.getTAnStatoByCodiFkIdStatoDim().getCodiKIdStato(), Hibernate.INTEGER);
				else
					q.setParameter("idStatoDim", null, Hibernate.INTEGER);
				q.setParameter("descComuneEstDim", relProcSoggInt.getDescComEsteroDim(), Hibernate.STRING);
				if ((relProcSoggInt.getTAnComuniByCodiFkIdComDim() != null) && (relProcSoggInt.getTAnComuniByCodiFkIdComDim().getCodiKIdComune() > 0))
					q.setParameter("idComuneDim", relProcSoggInt.getTAnComuniByCodiFkIdComDim().getCodiKIdComune(), Hibernate.INTEGER);
				else
					q.setParameter("idComuneDim", null, Hibernate.INTEGER);
				if ((relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDim() != null)
						&& (relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDim().getCodiKIdTipoStrada() > 0))
					q.setParameter("idTipoStradaDim", relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDim().getCodiKIdTipoStrada(),
							Hibernate.INTEGER);
				else
					q.setParameter("idTipoStradaDim", null, Hibernate.INTEGER);
				q.setParameter("indirizzoDim", relProcSoggInt.getDescIndirDim(), Hibernate.STRING);
				q.setParameter("numCivicoDim", relProcSoggInt.getDescCivicoDim(), Hibernate.STRING);
				if (relProcSoggInt.getTAnComuniByCodiFkIdComDim() != null)
					q.setParameter("capDim", relProcSoggInt.getTAnComuniByCodiFkIdComDim().getDescCapComune(), Hibernate.STRING);
				else
					q.setParameter("capDim", null, Hibernate.STRING);
				if ((relProcSoggInt.getTAnStatoByCodiFkIdStatoDomel() != null)
						&& (relProcSoggInt.getTAnStatoByCodiFkIdStatoDomel().getCodiKIdStato() > 0))
					q.setParameter("idStatoDomel", relProcSoggInt.getTAnStatoByCodiFkIdStatoDomel().getCodiKIdStato(), Hibernate.INTEGER);
				else
					q.setParameter("idStatoDomel", null, Hibernate.INTEGER);
				q.setParameter("descComuneEstDomel", relProcSoggInt.getDescComEsteroDomel(), Hibernate.STRING);
				if ((relProcSoggInt.getTAnComuniByCodiFkIdComDomel() != null)
						&& (relProcSoggInt.getTAnComuniByCodiFkIdComDomel().getCodiKIdComune() > 0))
					q.setParameter("idComuneDomel", relProcSoggInt.getTAnComuniByCodiFkIdComDomel().getCodiKIdComune(), Hibernate.INTEGER);
				else
					q.setParameter("idComuneDomel", null, Hibernate.LONG);
				if ((relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDomel() != null)
						&& (relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDomel().getCodiKIdTipoStrada() > 0))
					q.setParameter("idTipoStradaDomel", relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDomel().getCodiKIdTipoStrada(),
							Hibernate.INTEGER);
				else
					q.setParameter("idTipoStradaDomel", null, Hibernate.INTEGER);
				q.setParameter("indirizzoDomel", relProcSoggInt.getDescIndirDomel(), Hibernate.STRING);
				if (relProcSoggInt.getDescCivicoDomel() != null)
					q.setParameter("numCivicoDomel", relProcSoggInt.getDescCivicoDomel(), Hibernate.STRING);
				else
					q.setParameter("numCivicoDomel", null, Hibernate.STRING);

				if (relProcSoggInt.getTAnComuniByCodiFkIdComDomel() != null)
					q.setParameter("capDomel", relProcSoggInt.getTAnComuniByCodiFkIdComDomel().getDescCapComune(), Hibernate.STRING);
				else
					q.setParameter("capDomel", null, Hibernate.STRING);

				if ((relProcSoggInt.getDescDomelPresso() != null) && !relProcSoggInt.getDescDomelPresso().equals("-1"))
					q.setParameter("pressoDomel", relProcSoggInt.getDescDomelPresso(), Hibernate.STRING);
				else
					q.setParameter("pressoDomel", null, Hibernate.STRING);
				q.setParameter("domelNota", relProcSoggInt.getDescDomelNota(), Hibernate.STRING);
				q.setParameter("flgValido", relProcSoggInt.getFlgValido(), Hibernate.CHARACTER);
				q.setParameter("codMotivo", relProcSoggInt.getTTpTipoMotivo().getDescMotivo(), Hibernate.STRING);
				if ((relProcSoggInt.getTTpTipoPrevProc() != null && relProcSoggInt.getTTpTipoPrevProc().getTTpTipoPrevenzione() != null && relProcSoggInt
						.getTTpTipoPrevProc().getTTpTipoPrevenzione().getCodiKIdTipoPrev() > 0))
					q.setParameter("idTipoPrev", relProcSoggInt.getTTpTipoPrevProc().getTTpTipoPrevenzione().getCodiKIdTipoPrev(), Hibernate.INTEGER);
				else
					q.setParameter("idTipoPrev", null, Hibernate.INTEGER);
				if ((relProcSoggInt.getTTpTipoPrevProc() != null) && (relProcSoggInt.getTTpTipoPrevProc().getCodiKIdTipoNorm() > 0))
					q.setParameter("idTipoNorm", relProcSoggInt.getTTpTipoPrevProc().getCodiKIdTipoNorm(), Hibernate.INTEGER);
				else
					q.setParameter("idTipoNorm", null, Hibernate.INTEGER);

				if (relProcSoggInt.getTAtto() != null && relProcSoggInt.getTAtto().getId() != null
						&& relProcSoggInt.getTAtto().getId().getCodiKIdAtto() > 0)
					q.setParameter("idFonte", relProcSoggInt.getTAtto().getId().getCodiKIdAtto(), Hibernate.LONG);
				else
					q.setParameter("idFonte", null, Hibernate.LONG);
				q.setParameter("dataUltAggiornamento", dataUltAggiornamento, Hibernate.TIMESTAMP);
				q.setParameter("ruolo", relProcSoggInt.getTTpTipoRuolo().getCodiKIdRuolo(), Hibernate.INTEGER);
				q.setResultTransformer(Transformers.aliasToBean(AssociaPersonaDto.class));
				List list = q.list();
				if ((list != null) && (list.size() > 0)) {
					dto = (AssociaPersonaDto) list.get(0);
					soggetto.setIDPROC(dto.getIDPROC());
					soggetto.setIDPERS(dto.getIDPERS());
					soggetto.setIDSOGGINT(dto.getIDSOGGINT());
					soggetto.setIDUFFOWN(dto.getIDUFFOWN());
					soggetto.setIDVER(dto.getIDVER());
				}
				return dto;
			}
		});
	}
	
	public AssociaPersonaDto nuovaPersonaFisica(final int idUffOwn, final String codUtente, 
			final AssociaPersonaDto soggetto, final Date dataUltAggiornamento) {

		return (AssociaPersonaDto) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				AssociaPersonaDto dto = new AssociaPersonaDto();
				Query q = session.getNamedQuery("PG_SOGGETTO_nuovaPersFis");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("descUtente", codUtente);
				TPersonaFisica persona = soggetto.getPersFis();
				soggetto.getSoggInt();
				TRelProcSogint relProcSoggInt = soggetto.getRelProcSoggInt();
				q.setParameter("cognome", persona.getDescCognome(), Hibernate.STRING);
				q.setParameter("nome", persona.getDescNome(), Hibernate.STRING);
				q.setParameter("sesso", persona.getFlgSesso(), Hibernate.CHARACTER);
				q.setParameter("dataNascita", persona.getDataNascita(), Hibernate.DATE);
				q.setParameter("flgDataPresunta", persona.getFlgDataPresunta(), Hibernate.CHARACTER);
				if ((persona.getTAnStatoByCodiFkIdStatoNasc() != null) && (persona.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato() > 0))
					q.setParameter("idStatoNasc", persona.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato(), Hibernate.INTEGER);
				else
					q.setParameter("idStatoNasc", null, Hibernate.INTEGER);
				q.setParameter("comuneEsteroNasc", persona.getDescComEsteroNasc(), Hibernate.STRING);
				if ((persona.getTAnComuniByCodiFkIdComNasc() != null) && (persona.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune() > 0))
					q.setParameter("idComuneNasc", persona.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune(), Hibernate.INTEGER);
				else
					q.setParameter("idComuneNasc", null, Hibernate.INTEGER);
				q.setParameter("codiceFiscale", persona.getDescCodiceFiscale(), Hibernate.STRING);
				q.setParameter("cui", persona.getDescCui(), Hibernate.STRING);
				q.setParameter("nomePadre", persona.getDescNomePadre(), Hibernate.STRING);
				q.setParameter("cognomePadre", persona.getDescCogPadre(), Hibernate.STRING);
				q.setParameter("nomeMadre", persona.getDescNomeMadre(), Hibernate.STRING);
				q.setParameter("cognomeMadre", persona.getDescCogMadre(), Hibernate.STRING);
				q.setParameter("dataDecesso", persona.getDataDecesso(), Hibernate.DATE);
				if ((persona.getTAnStatoByCodiFkIdStatoMorte() != null) && (persona.getTAnStatoByCodiFkIdStatoMorte().getCodiKIdStato() > 0))
					q.setParameter("idStatoMorte", persona.getTAnStatoByCodiFkIdStatoMorte().getCodiKIdStato(), Hibernate.INTEGER);
				else
					q.setParameter("idStatoMorte", null, Hibernate.INTEGER);
				q.setParameter("comuneEsteroMorte", persona.getDescComEsteroMorte(), Hibernate.STRING);
				if ((persona.getTAnComuniByCodiFkIdComMorte() != null) && (persona.getTAnComuniByCodiFkIdComMorte().getCodiKIdComune() > 0))
					q.setParameter("idComuneMorte", persona.getTAnComuniByCodiFkIdComMorte().getCodiKIdComune(), Hibernate.INTEGER);
				else
					q.setParameter("idComuneMorte", null, Hibernate.INTEGER);
				/**
				 * TODO if ((persona.getTStatoEsteroByCodiFkIdStatoCitt() !=
				 * null) && (persona.getTStatoEsteroByCodiFkIdStatoCitt().
				 * getCodiKIdStato() > 0)) {
				 * q.setParameter("idStatoCittadinanza",
				 * persona.getTStatoEsteroByCodiFkIdStatoCitt
				 * ().getCodiKIdStato(), Hibernate.LONG); } else {
				 * q.setParameter("idStatoCittadinanza", null, Hibernate.LONG);
				 * }
				 */
				if ((relProcSoggInt.getTTpProfessione() != null) && (relProcSoggInt.getTTpProfessione().getCodiKIdProfessione() > 0))
					q.setParameter("idProfess", relProcSoggInt.getTTpProfessione().getCodiKIdProfessione(), Hibernate.INTEGER);
				else
					q.setParameter("idProfess", null, Hibernate.INTEGER);
				if ((relProcSoggInt.getTTpCondGiur() != null) && (relProcSoggInt.getTTpCondGiur().getCodiKIdCondGiur() > 0))
					q.setParameter("idCondGiur", relProcSoggInt.getTTpCondGiur().getCodiKIdCondGiur(), Hibernate.INTEGER);
				else
					q.setParameter("idCondGiur", null, Hibernate.INTEGER);
				if ((relProcSoggInt.getTAnIstitutoDet() != null) && (relProcSoggInt.getTAnIstitutoDet().getCodiKIdIstituto() > 0))
					q.setParameter("idIstDet", relProcSoggInt.getTAnIstitutoDet().getCodiKIdIstituto(), Hibernate.INTEGER);
				else
					q.setParameter("idIstDet", null, Hibernate.LONG);
				q.setParameter("descIstEstero", relProcSoggInt.getDescIstDetEstero(), Hibernate.STRING);
				if ((relProcSoggInt.getTAnStatoByCodiFkIdStatoRes() != null)
						&& (relProcSoggInt.getTAnStatoByCodiFkIdStatoRes().getCodiKIdStato() > 0))
					q.setParameter("idStatoRes", relProcSoggInt.getTAnStatoByCodiFkIdStatoRes().getCodiKIdStato(), Hibernate.INTEGER);
				else
					q.setParameter("idStatoRes", null, Hibernate.LONG);
				q.setParameter("descComuneEstRes", relProcSoggInt.getDescComEsteroRes(), Hibernate.STRING);
				if ((relProcSoggInt.getTAnComuniByCodiFkIdComRes() != null) && (relProcSoggInt.getTAnComuniByCodiFkIdComRes().getCodiKIdComune() > 0))
					q.setParameter("idComuneRes", relProcSoggInt.getTAnComuniByCodiFkIdComRes().getCodiKIdComune(), Hibernate.INTEGER);
				else
					q.setParameter("idComuneRes", null, Hibernate.INTEGER);
				if ((relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraRes() != null)
						&& (relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraRes().getCodiKIdTipoStrada() > 0))
					q.setParameter("idTipoStradaRes", relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraRes().getCodiKIdTipoStrada(),
							Hibernate.INTEGER);
				else
					q.setParameter("idTipoStradaRes", null, Hibernate.INTEGER);
				q.setParameter("indirizzoRes", relProcSoggInt.getDescIndirRes(), Hibernate.STRING);
				q.setParameter("numCivicoRes", relProcSoggInt.getDescCivicoRes(), Hibernate.STRING);
				q.setParameter("capRes", relProcSoggInt.getTAnComuniByCodiFkIdComRes().getDescCapComune(), Hibernate.STRING);
				if ((relProcSoggInt.getTAnStatoByCodiFkIdStatoDom() != null)
						&& (relProcSoggInt.getTAnStatoByCodiFkIdStatoDom().getCodiKIdStato() > 0))
					q.setParameter("idStatoDom", relProcSoggInt.getTAnStatoByCodiFkIdStatoDom().getCodiKIdStato(), Hibernate.INTEGER);
				else
					q.setParameter("idStatoDom", null, Hibernate.INTEGER);
				q.setParameter("descComuneEstDom", relProcSoggInt.getDescComEsteroDom(), Hibernate.STRING);
				if ((relProcSoggInt.getTAnComuniByCodiFkIdComDom() != null) && (relProcSoggInt.getTAnComuniByCodiFkIdComDom().getCodiKIdComune() > 0))
					q.setParameter("idComuneDom", relProcSoggInt.getTAnComuniByCodiFkIdComDom().getCodiKIdComune(), Hibernate.INTEGER);
				else
					q.setParameter("idComuneDom", null, Hibernate.INTEGER);
				if ((relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDom() != null)
						&& (relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDom().getCodiKIdTipoStrada() > 0))
					q.setParameter("idTipoStradaDom", relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDom().getCodiKIdTipoStrada(),
							Hibernate.INTEGER);
				else
					q.setParameter("idTipoStradaDom", null, Hibernate.INTEGER);
				q.setParameter("indirizzoDom", relProcSoggInt.getDescIndirDom(), Hibernate.STRING);
				q.setParameter("numCivicoDom", relProcSoggInt.getDescCivicoDom(), Hibernate.STRING);
				q.setParameter("capDom", relProcSoggInt.getTAnComuniByCodiFkIdComDom().getDescCapComune(), Hibernate.STRING);
				if ((relProcSoggInt.getTAnStatoByCodiFkIdStatoDim() != null)
						&& (relProcSoggInt.getTAnStatoByCodiFkIdStatoDim().getCodiKIdStato() > 0))
					q.setParameter("idStatoDim", relProcSoggInt.getTAnStatoByCodiFkIdStatoDim().getCodiKIdStato(), Hibernate.INTEGER);
				else
					q.setParameter("idStatoDim", null, Hibernate.INTEGER);
				q.setParameter("descComuneEstDim", relProcSoggInt.getDescComEsteroDim(), Hibernate.STRING);
				if ((relProcSoggInt.getTAnComuniByCodiFkIdComDim() != null) && (relProcSoggInt.getTAnComuniByCodiFkIdComDim().getCodiKIdComune() > 0))
					q.setParameter("idComuneDim", relProcSoggInt.getTAnComuniByCodiFkIdComDim().getCodiKIdComune(), Hibernate.INTEGER);
				else
					q.setParameter("idComuneDim", null, Hibernate.INTEGER);
				if ((relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDim() != null)
						&& (relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDim().getCodiKIdTipoStrada() > 0))
					q.setParameter("idTipoStradaDim", relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDim().getCodiKIdTipoStrada(),
							Hibernate.INTEGER);
				else
					q.setParameter("idTipoStradaDim", null, Hibernate.INTEGER);
				q.setParameter("indirizzoDim", relProcSoggInt.getDescIndirDim(), Hibernate.STRING);
				q.setParameter("numCivicoDim", relProcSoggInt.getDescCivicoDim(), Hibernate.STRING);
				if (relProcSoggInt.getTAnComuniByCodiFkIdComDim() != null)
					q.setParameter("capDim", relProcSoggInt.getTAnComuniByCodiFkIdComDim().getDescCapComune(), Hibernate.STRING);
				else
					q.setParameter("capDim", null, Hibernate.STRING);
				if ((relProcSoggInt.getTAnStatoByCodiFkIdStatoDomel() != null)
						&& (relProcSoggInt.getTAnStatoByCodiFkIdStatoDomel().getCodiKIdStato() > 0))
					q.setParameter("idStatoDomel", relProcSoggInt.getTAnStatoByCodiFkIdStatoDomel().getCodiKIdStato(), Hibernate.INTEGER);
				else
					q.setParameter("idStatoDomel", null, Hibernate.INTEGER);
				q.setParameter("descComuneEstDomel", relProcSoggInt.getDescComEsteroDomel(), Hibernate.STRING);
				if ((relProcSoggInt.getTAnComuniByCodiFkIdComDomel() != null)
						&& (relProcSoggInt.getTAnComuniByCodiFkIdComDomel().getCodiKIdComune() > 0))
					q.setParameter("idComuneDomel", relProcSoggInt.getTAnComuniByCodiFkIdComDomel().getCodiKIdComune(), Hibernate.INTEGER);
				else
					q.setParameter("idComuneDomel", null, Hibernate.LONG);
				if ((relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDomel() != null)
						&& (relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDomel().getCodiKIdTipoStrada() > 0))
					q.setParameter("idTipoStradaDomel", relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDomel().getCodiKIdTipoStrada(),
							Hibernate.INTEGER);
				else
					q.setParameter("idTipoStradaDomel", null, Hibernate.INTEGER);
				q.setParameter("indirizzoDomel", relProcSoggInt.getDescIndirDomel(), Hibernate.STRING);
				if (relProcSoggInt.getDescCivicoDomel() != null)
					q.setParameter("numCivicoDomel", relProcSoggInt.getDescCivicoDomel(), Hibernate.STRING);
				else
					q.setParameter("numCivicoDomel", null, Hibernate.STRING);

				if (relProcSoggInt.getTAnComuniByCodiFkIdComDomel() != null)
					q.setParameter("capDomel", relProcSoggInt.getTAnComuniByCodiFkIdComDomel().getDescCapComune(), Hibernate.STRING);
				else
					q.setParameter("capDomel", null, Hibernate.STRING);

				if ((relProcSoggInt.getDescDomelPresso() != null) && !relProcSoggInt.getDescDomelPresso().equals("-1"))
					q.setParameter("pressoDomel", relProcSoggInt.getDescDomelPresso(), Hibernate.STRING);
				else
					q.setParameter("pressoDomel", null, Hibernate.STRING);
				q.setParameter("domelNota", relProcSoggInt.getDescDomelNota(), Hibernate.STRING);
				// q.setParameter("flgAssociato",
				// relProcSoggInt.getFlgAssociato(), Hibernate.STRING);
				q.setParameter("flgValido", relProcSoggInt.getFlgValido(), Hibernate.CHARACTER);
				q.setParameter("codMotivo", relProcSoggInt.getTTpTipoMotivo().getDescMotivo(), Hibernate.STRING);
				// q.setParameter("descValido", relProcSoggInt.getDescValido(),
				// Hibernate.STRING);
				if ((relProcSoggInt.getTTpTipoPrevProc() != null && relProcSoggInt.getTTpTipoPrevProc().getTTpTipoPrevenzione() != null && relProcSoggInt
						.getTTpTipoPrevProc().getTTpTipoPrevenzione().getCodiKIdTipoPrev() > 0))
					q.setParameter("idTipoPrev", relProcSoggInt.getTTpTipoPrevProc().getTTpTipoPrevenzione().getCodiKIdTipoPrev(), Hibernate.INTEGER);
				else
					q.setParameter("idTipoPrev", null, Hibernate.INTEGER);
				if ((relProcSoggInt.getTTpTipoPrevProc() != null) && (relProcSoggInt.getTTpTipoPrevProc().getCodiKIdTipoNorm() > 0))
					q.setParameter("idTipoNorm", relProcSoggInt.getTTpTipoPrevProc().getCodiKIdTipoNorm(), Hibernate.INTEGER);
				else
					q.setParameter("idTipoNorm", null, Hibernate.INTEGER);

				if (relProcSoggInt.getTAtto() != null && relProcSoggInt.getTAtto().getId() != null
						&& relProcSoggInt.getTAtto().getId().getCodiKIdAtto() > 0)
					q.setParameter("idFonte", relProcSoggInt.getTAtto().getId().getCodiKIdAtto(), Hibernate.LONG);
				else
					q.setParameter("idFonte", null, Hibernate.LONG);
				q.setParameter("dataUltAggiornamento", dataUltAggiornamento, Hibernate.TIMESTAMP);
				q.setParameter("ruolo", relProcSoggInt.getTTpTipoRuolo().getCodiKIdRuolo(), Hibernate.INTEGER);
				q.setResultTransformer(Transformers.aliasToBean(AssociaPersonaDto.class));
				List list = q.list();
				if ((list != null) && (list.size() > 0)) {
					dto = (AssociaPersonaDto) list.get(0);
					soggetto.setIDPROC(dto.getIDPROC());
					soggetto.setIDPERS(dto.getIDPERS());
					soggetto.setIDSOGGINT(dto.getIDSOGGINT());
					soggetto.setIDUFFOWN(dto.getIDUFFOWN());
					soggetto.setIDVER(dto.getIDVER());
				}
				return dto;
			}
		});
	}	

	public AssociaPersonaDto associaNuovaPersonaGiuridica(final int idUffOwn, final String codUtente, final Long idProc,
			final AssociaPersonaDto soggetto, final Date dataUltAggiornamento) {

		return (AssociaPersonaDto) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				AssociaPersonaDto dto = new AssociaPersonaDto();
				Query q = session.getNamedQuery("PG_SOGGETTO_associaNuovaPersGiur");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("descUtente", codUtente);
				q.setParameter("idProc", idProc, Hibernate.LONG);
				TPersonaGiur persona = soggetto.getPersonaGiur();
				TRelProcSogint relProcSoggInt = soggetto.getRelProcSoggInt();
				if ((persona.getTTpTipoSocieta() != null) && persona.getTTpTipoSocieta().getCodiKIdTipoSoc() > 0)
					q.setParameter("idTipoSoc", persona.getTTpTipoSocieta().getCodiKIdTipoSoc(), Hibernate.INTEGER);
				else
					q.setParameter("idTipoSoc", null, Hibernate.INTEGER);
				q.setParameter("descRagSoc", persona.getDescRagioneSoc(), Hibernate.STRING);
				if (persona.getDescPartitaIva() != null)
					q.setParameter("partitaIva", persona.getDescPartitaIva(), Hibernate.STRING);
				else
					q.setParameter("partitaIva", null, Hibernate.STRING);
				q.setParameter("numCCIAA", persona.getDescNumeCciaa(), Hibernate.STRING);
				if ((persona.getTAnStato() != null) && (persona.getTAnStato().getCodiKIdStato() > 0))
					q.setParameter("idStatoSede", persona.getTAnStato().getCodiKIdStato(), Hibernate.INTEGER);
				else
					q.setParameter("idStatoSede", null, Hibernate.INTEGER);
				q.setParameter("descComuneEsteroSede", persona.getDescComEsteroSede(), Hibernate.STRING);
				if ((persona.getTAnComuni() != null) && (persona.getTAnComuni().getCodiKIdComune() > 0))
					q.setParameter("idComuneSede", persona.getTAnComuni().getCodiKIdComune(), Hibernate.INTEGER);
				else
					q.setParameter("idComuneSede", null, Hibernate.INTEGER);
				if ((persona.getTTpTipoStrada() != null) && (persona.getTTpTipoStrada().getCodiKIdTipoStrada() > 0))
					q.setParameter("idTipoStradaSede", persona.getTTpTipoStrada().getCodiKIdTipoStrada(), Hibernate.INTEGER);
				else
					q.setParameter("idTipoStradaSede", null, Hibernate.INTEGER);
				q.setParameter("descIndirizzoSede", persona.getDescIndirizzoSede(), Hibernate.STRING);
				q.setParameter("civicoSede", persona.getDescCivico(), Hibernate.STRING);
				q.setParameter("capSede", persona.getDescCap(), Hibernate.STRING);
				if ((persona.getTTpSettoreAtt() != null) && (persona.getTTpSettoreAtt().getCodiKIdSettoreAtt() > 0))
					q.setParameter("idSettAtt", persona.getTTpSettoreAtt().getCodiKIdSettoreAtt(), Hibernate.INTEGER);
				else
					q.setParameter("idSettAtt", null, Hibernate.INTEGER);
				if (persona.getImpoCapSoc() != null)
					q.setParameter("impoCapSoc", persona.getImpoCapSoc(), Hibernate.BIG_DECIMAL);
				else
					q.setParameter("impoCapSoc", null, Hibernate.BIG_DECIMAL);
				q.setParameter("note", persona.getDescNote(), Hibernate.STRING);
				q.setParameter("descRapprSoc", persona.getDescRapprSoc(), Hibernate.STRING);
				q.setParameter("descAmmin", persona.getDescAmmin(), Hibernate.STRING);
				if ((relProcSoggInt.getTAnStatoByCodiFkIdStatoDomel() != null)
						&& (relProcSoggInt.getTAnStatoByCodiFkIdStatoDomel().getCodiKIdStato() > 0))
					q.setParameter("idStatoDomel", relProcSoggInt.getTAnStatoByCodiFkIdStatoDomel().getCodiKIdStato(), Hibernate.INTEGER);
				else
					q.setParameter("idStatoDomel", null, Hibernate.INTEGER);
				q.setParameter("descComuneEstDomel", relProcSoggInt.getDescComEsteroDomel(), Hibernate.STRING);
				if ((relProcSoggInt.getTAnComuniByCodiFkIdComDomel() != null)
						&& (relProcSoggInt.getTAnComuniByCodiFkIdComDomel().getCodiKIdComune() > 0))
					q.setParameter("idComuneDomel", relProcSoggInt.getTAnComuniByCodiFkIdComDomel().getCodiKIdComune(), Hibernate.INTEGER);
				else
					q.setParameter("idComuneDomel", null, Hibernate.INTEGER);
				if ((relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDomel() != null)
						&& (relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDomel().getCodiKIdTipoStrada() > 0))
					q.setParameter("idTipoStradaDomel", relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDomel().getCodiKIdTipoStrada(),
							Hibernate.INTEGER);
				else
					q.setParameter("idTipoStradaDomel", null, Hibernate.INTEGER);
				q.setParameter("indirizzoDomel", relProcSoggInt.getDescIndirDomel(), Hibernate.STRING);
				if (relProcSoggInt.getTAnComuniByCodiFkIdComDomel() != null)
					q.setParameter("numCivicoDomel", relProcSoggInt.getDescCivicoDomel(), Hibernate.STRING);
				else
					q.setParameter("numCivicoDomel", null, Hibernate.STRING);

				if (relProcSoggInt.getTAnComuniByCodiFkIdComDomel() != null)
					q.setParameter("capDomel", relProcSoggInt.getTAnComuniByCodiFkIdComDomel().getDescCapComune(), Hibernate.STRING);
				else
					q.setParameter("capDomel", null, Hibernate.STRING);

				if ((relProcSoggInt.getDescDomelPresso() != null) && !relProcSoggInt.getDescDomelPresso().equals("-1"))
					q.setParameter("pressoDomel", relProcSoggInt.getDescDomelPresso(), Hibernate.STRING);
				else
					q.setParameter("pressoDomel", null, Hibernate.STRING);
				q.setParameter("domelNota", relProcSoggInt.getDescDomelNota(), Hibernate.STRING);
				// q.setParameter("flgAssociato",
				// relProcSoggInt.getFlgAssociato(), Hibernate.STRING);
				q.setParameter("flgValido", relProcSoggInt.getFlgValido(), Hibernate.STRING);
				q.setParameter("codMotivo", relProcSoggInt.getTTpTipoMotivo().getDescMotivo(), Hibernate.STRING);
				// q.setParameter("descValido", relProcSoggInt.getDescValido(),
				// Hibernate.STRING);
				if ((relProcSoggInt.getTTpTipoPrevProc() != null && relProcSoggInt.getTTpTipoPrevProc().getTTpTipoPrevenzione() != null && relProcSoggInt
						.getTTpTipoPrevProc().getTTpTipoPrevenzione().getCodiKIdTipoPrev() > 0))
					q.setParameter("idTipoPrev", relProcSoggInt.getTTpTipoPrevProc().getTTpTipoPrevenzione().getCodiKIdTipoPrev(), Hibernate.INTEGER);
				else
					q.setParameter("idTipoPrev", null, Hibernate.INTEGER);
				if ((relProcSoggInt.getTTpTipoPrevProc() != null) && (relProcSoggInt.getTTpTipoPrevProc().getCodiKIdTipoNorm() > 0))
					q.setParameter("idTipoNorm", relProcSoggInt.getTTpTipoPrevProc().getCodiKIdTipoNorm(), Hibernate.INTEGER);
				else
					q.setParameter("idTipoNorm", null, Hibernate.INTEGER);
				if (relProcSoggInt.getTAtto() != null && relProcSoggInt.getTAtto().getId() != null
						&& relProcSoggInt.getTAtto().getId().getCodiKIdAtto() > 0)
					q.setParameter("idFonte", relProcSoggInt.getTAtto().getId().getCodiKIdAtto(), Hibernate.LONG);
				else
					q.setParameter("idFonte", null, Hibernate.LONG);
				q.setParameter("dataUltAggiornamento", dataUltAggiornamento, Hibernate.TIMESTAMP);
				q.setParameter("ruolo", relProcSoggInt.getTTpTipoRuolo().getCodiKIdRuolo(), Hibernate.INTEGER);
				q.setResultTransformer(Transformers.aliasToBean(AssociaPersonaDto.class));
				List list = q.list();
				if ((list != null) && (list.size() > 0)) {
					dto = (AssociaPersonaDto) list.get(0);
					soggetto.setIDPROC(dto.getIDPROC());
					soggetto.setIDPERS(dto.getIDPERS());
					soggetto.setIDSOGGINT(dto.getIDSOGGINT());
					soggetto.setIDUFFOWN(dto.getIDUFFOWN());
					soggetto.setIDVER(dto.getIDVER());
				}
				return dto;
			}
		});
	}
	
	public AssociaPersonaDto nuovaPersonaGiuridica(final int idUffOwn, final String codUtente, 
			final AssociaPersonaDto soggetto, final Date dataUltAggiornamento) {

		return (AssociaPersonaDto) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				AssociaPersonaDto dto = new AssociaPersonaDto();
				Query q = session.getNamedQuery("PG_SOGGETTO_nuovaPersGiur");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("descUtente", codUtente);
				TPersonaGiur persona = soggetto.getPersonaGiur();
				TRelProcSogint relProcSoggInt = soggetto.getRelProcSoggInt();
				if ((persona.getTTpTipoSocieta() != null) && persona.getTTpTipoSocieta().getCodiKIdTipoSoc() > 0)
					q.setParameter("idTipoSoc", persona.getTTpTipoSocieta().getCodiKIdTipoSoc(), Hibernate.INTEGER);
				else
					q.setParameter("idTipoSoc", null, Hibernate.INTEGER);
				q.setParameter("descRagSoc", persona.getDescRagioneSoc(), Hibernate.STRING);
				if (persona.getDescPartitaIva() != null)
					q.setParameter("partitaIva", persona.getDescPartitaIva(), Hibernate.STRING);
				else
					q.setParameter("partitaIva", null, Hibernate.STRING);
				q.setParameter("numCCIAA", persona.getDescNumeCciaa(), Hibernate.STRING);
				if ((persona.getTAnStato() != null) && (persona.getTAnStato().getCodiKIdStato() > 0))
					q.setParameter("idStatoSede", persona.getTAnStato().getCodiKIdStato(), Hibernate.INTEGER);
				else
					q.setParameter("idStatoSede", null, Hibernate.INTEGER);
				q.setParameter("descComuneEsteroSede", persona.getDescComEsteroSede(), Hibernate.STRING);
				if ((persona.getTAnComuni() != null) && (persona.getTAnComuni().getCodiKIdComune() > 0))
					q.setParameter("idComuneSede", persona.getTAnComuni().getCodiKIdComune(), Hibernate.INTEGER);
				else
					q.setParameter("idComuneSede", null, Hibernate.INTEGER);
				if ((persona.getTTpTipoStrada() != null) && (persona.getTTpTipoStrada().getCodiKIdTipoStrada() > 0))
					q.setParameter("idTipoStradaSede", persona.getTTpTipoStrada().getCodiKIdTipoStrada(), Hibernate.INTEGER);
				else
					q.setParameter("idTipoStradaSede", null, Hibernate.INTEGER);
				q.setParameter("descIndirizzoSede", persona.getDescIndirizzoSede(), Hibernate.STRING);
				q.setParameter("civicoSede", persona.getDescCivico(), Hibernate.STRING);
				q.setParameter("capSede", persona.getDescCap(), Hibernate.STRING);
				if ((persona.getTTpSettoreAtt() != null) && (persona.getTTpSettoreAtt().getCodiKIdSettoreAtt() > 0))
					q.setParameter("idSettAtt", persona.getTTpSettoreAtt().getCodiKIdSettoreAtt(), Hibernate.INTEGER);
				else
					q.setParameter("idSettAtt", null, Hibernate.INTEGER);
				if (persona.getImpoCapSoc() != null)
					q.setParameter("impoCapSoc", persona.getImpoCapSoc(), Hibernate.BIG_DECIMAL);
				else
					q.setParameter("impoCapSoc", null, Hibernate.BIG_DECIMAL);
				q.setParameter("note", persona.getDescNote(), Hibernate.STRING);
				q.setParameter("descRapprSoc", persona.getDescRapprSoc(), Hibernate.STRING);
				q.setParameter("descAmmin", persona.getDescAmmin(), Hibernate.STRING);
				if ((relProcSoggInt.getTAnStatoByCodiFkIdStatoDomel() != null)
						&& (relProcSoggInt.getTAnStatoByCodiFkIdStatoDomel().getCodiKIdStato() > 0))
					q.setParameter("idStatoDomel", relProcSoggInt.getTAnStatoByCodiFkIdStatoDomel().getCodiKIdStato(), Hibernate.INTEGER);
				else
					q.setParameter("idStatoDomel", null, Hibernate.INTEGER);
				q.setParameter("descComuneEstDomel", relProcSoggInt.getDescComEsteroDomel(), Hibernate.STRING);
				if ((relProcSoggInt.getTAnComuniByCodiFkIdComDomel() != null)
						&& (relProcSoggInt.getTAnComuniByCodiFkIdComDomel().getCodiKIdComune() > 0))
					q.setParameter("idComuneDomel", relProcSoggInt.getTAnComuniByCodiFkIdComDomel().getCodiKIdComune(), Hibernate.INTEGER);
				else
					q.setParameter("idComuneDomel", null, Hibernate.INTEGER);
				if ((relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDomel() != null)
						&& (relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDomel().getCodiKIdTipoStrada() > 0))
					q.setParameter("idTipoStradaDomel", relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDomel().getCodiKIdTipoStrada(),
							Hibernate.INTEGER);
				else
					q.setParameter("idTipoStradaDomel", null, Hibernate.INTEGER);
				q.setParameter("indirizzoDomel", relProcSoggInt.getDescIndirDomel(), Hibernate.STRING);
				if (relProcSoggInt.getTAnComuniByCodiFkIdComDomel() != null)
					q.setParameter("numCivicoDomel", relProcSoggInt.getDescCivicoDomel(), Hibernate.STRING);
				else
					q.setParameter("numCivicoDomel", null, Hibernate.STRING);

				if (relProcSoggInt.getTAnComuniByCodiFkIdComDomel() != null)
					q.setParameter("capDomel", relProcSoggInt.getTAnComuniByCodiFkIdComDomel().getDescCapComune(), Hibernate.STRING);
				else
					q.setParameter("capDomel", null, Hibernate.STRING);

				if ((relProcSoggInt.getDescDomelPresso() != null) && !relProcSoggInt.getDescDomelPresso().equals("-1"))
					q.setParameter("pressoDomel", relProcSoggInt.getDescDomelPresso(), Hibernate.STRING);
				else
					q.setParameter("pressoDomel", null, Hibernate.STRING);
				q.setParameter("domelNota", relProcSoggInt.getDescDomelNota(), Hibernate.STRING);
				// q.setParameter("flgAssociato",
				// relProcSoggInt.getFlgAssociato(), Hibernate.STRING);
				q.setParameter("flgValido", relProcSoggInt.getFlgValido(), Hibernate.STRING);
				q.setParameter("codMotivo", relProcSoggInt.getTTpTipoMotivo().getDescMotivo(), Hibernate.STRING);
				// q.setParameter("descValido", relProcSoggInt.getDescValido(),
				// Hibernate.STRING);
				if ((relProcSoggInt.getTTpTipoPrevProc() != null && relProcSoggInt.getTTpTipoPrevProc().getTTpTipoPrevenzione() != null && relProcSoggInt
						.getTTpTipoPrevProc().getTTpTipoPrevenzione().getCodiKIdTipoPrev() > 0))
					q.setParameter("idTipoPrev", relProcSoggInt.getTTpTipoPrevProc().getTTpTipoPrevenzione().getCodiKIdTipoPrev(), Hibernate.INTEGER);
				else
					q.setParameter("idTipoPrev", null, Hibernate.INTEGER);
				if ((relProcSoggInt.getTTpTipoPrevProc() != null) && (relProcSoggInt.getTTpTipoPrevProc().getCodiKIdTipoNorm() > 0))
					q.setParameter("idTipoNorm", relProcSoggInt.getTTpTipoPrevProc().getCodiKIdTipoNorm(), Hibernate.INTEGER);
				else
					q.setParameter("idTipoNorm", null, Hibernate.INTEGER);
				if (relProcSoggInt.getTAtto() != null && relProcSoggInt.getTAtto().getId() != null
						&& relProcSoggInt.getTAtto().getId().getCodiKIdAtto() > 0)
					q.setParameter("idFonte", relProcSoggInt.getTAtto().getId().getCodiKIdAtto(), Hibernate.LONG);
				else
					q.setParameter("idFonte", null, Hibernate.LONG);
				q.setParameter("dataUltAggiornamento", dataUltAggiornamento, Hibernate.TIMESTAMP);
				q.setParameter("ruolo", relProcSoggInt.getTTpTipoRuolo().getCodiKIdRuolo(), Hibernate.INTEGER);
				q.setResultTransformer(Transformers.aliasToBean(AssociaPersonaDto.class));
				List list = q.list();
				if ((list != null) && (list.size() > 0)) {
					dto = (AssociaPersonaDto) list.get(0);
					soggetto.setIDPROC(dto.getIDPROC());
					soggetto.setIDPERS(dto.getIDPERS());
					soggetto.setIDSOGGINT(dto.getIDSOGGINT());
					soggetto.setIDUFFOWN(dto.getIDUFFOWN());
					soggetto.setIDVER(dto.getIDVER());
				}
				return dto;
			}
		});
	}	

	@SuppressWarnings("rawtypes")
	public AssociaPersonaDto associaPersonaFisica(final int idUffOwn, final String codUtente, final Long idProc, final AssociaPersonaDto soggetto,
			final Date dataUltAggiornamento) {

		return (AssociaPersonaDto) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				AssociaPersonaDto dto = new AssociaPersonaDto();
				Query q = session.getNamedQuery("PG_SOGGETTO_associaPersFis");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("descUtente", codUtente);
				q.setParameter("idProc", idProc, Hibernate.LONG);
				TPersonaFisica persona = soggetto.getPersFis();
				// TSoggetto soggInt = soggetto.getSoggInt();
				TRelProcSogint relProcSoggInt = soggetto.getRelProcSoggInt();
				// TODO MDM sostituire idTipoProc idTipoFasc

				// if (relProcSoggInt.getTTpTipoPrevProc() != null &&
				// relProcSoggInt.getTTpTipoPrevProc().getTTpTipoProc() != null
				// &&
				// relProcSoggInt.getTTpTipoPrevProc().getTTpTipoProc().getCodiKIdTipoProc()
				// > 0) {
				// q.setParameter("idTipoProc",
				// relProcSoggInt.getTTpTipoPrevProc().getTTpTipoProc().getCodiKIdTipoProc(),
				// Hibernate.INTEGER);
				// } else {
				// q.setParameter("idTipoProc", null, Hibernate.INTEGER);
				// }
				q.setParameter("idPers", persona.getId().getCodiKIdPersonaFis(), Hibernate.LONG);
				q.setParameter("idVers", persona.getId().getCodiKIdVer(), Hibernate.SHORT);
				if ((relProcSoggInt.getTTpProfessione() != null) && (relProcSoggInt.getTTpProfessione().getCodiKIdProfessione() > 0))
					q.setParameter("idProfess", relProcSoggInt.getTTpProfessione().getCodiKIdProfessione(), Hibernate.INTEGER);
				else
					q.setParameter("idProfess", null, Hibernate.INTEGER);
				if ((relProcSoggInt.getTTpCondGiur() != null) && (relProcSoggInt.getTTpCondGiur().getCodiKIdCondGiur() > 0))
					q.setParameter("idCondGiur", relProcSoggInt.getTTpCondGiur().getCodiKIdCondGiur(), Hibernate.INTEGER);
				else
					q.setParameter("idCondGiur", null, Hibernate.INTEGER);
				if ((relProcSoggInt.getTAnIstitutoDet() != null) && (relProcSoggInt.getTAnIstitutoDet().getCodiKIdIstituto() > 0))
					q.setParameter("idIstDet", relProcSoggInt.getTAnIstitutoDet().getCodiKIdIstituto(), Hibernate.INTEGER);
				else
					q.setParameter("idIstDet", null, Hibernate.INTEGER);
				q.setParameter("descIstEstero", relProcSoggInt.getDescIstDetEstero(), Hibernate.STRING);
				if ((relProcSoggInt.getTAnStatoByCodiFkIdStatoRes() != null)
						&& (relProcSoggInt.getTAnStatoByCodiFkIdStatoRes().getCodiKIdStato() > 0))
					q.setParameter("idStatoRes", relProcSoggInt.getTAnStatoByCodiFkIdStatoRes().getCodiKIdStato(), Hibernate.INTEGER);
				else
					q.setParameter("idStatoRes", null, Hibernate.INTEGER);
				q.setParameter("descComuneEstRes", relProcSoggInt.getDescComEsteroRes(), Hibernate.STRING);
				if ((relProcSoggInt.getTAnComuniByCodiFkIdComRes() != null) && (relProcSoggInt.getTAnComuniByCodiFkIdComRes().getCodiKIdComune() > 0))
					q.setParameter("idComuneRes", relProcSoggInt.getTAnComuniByCodiFkIdComRes().getCodiKIdComune(), Hibernate.INTEGER);
				else
					q.setParameter("idComuneRes", null, Hibernate.INTEGER);
				if ((relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraRes() != null)
						&& (relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraRes().getCodiKIdTipoStrada() > 0))
					q.setParameter("idTipoStradaRes", relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraRes().getCodiKIdTipoStrada(),
							Hibernate.INTEGER);
				else
					q.setParameter("idTipoStradaRes", null, Hibernate.INTEGER);
				q.setParameter("indirizzoRes", relProcSoggInt.getDescIndirRes(), Hibernate.STRING);
				q.setParameter("numCivicoRes", relProcSoggInt.getDescCivicoRes(), Hibernate.STRING);
				if (relProcSoggInt.getTAnComuniByCodiFkIdComRes() != null)
					q.setParameter("capRes", relProcSoggInt.getTAnComuniByCodiFkIdComRes().getDescCapComune(), Hibernate.STRING);
				else
					q.setParameter("capRes", null, Hibernate.STRING);
				if ((relProcSoggInt.getTAnStatoByCodiFkIdStatoDom() != null)
						&& (relProcSoggInt.getTAnStatoByCodiFkIdStatoDom().getCodiKIdStato() > 0))
					q.setParameter("idStatoDom", relProcSoggInt.getTAnStatoByCodiFkIdStatoDom().getCodiKIdStato(), Hibernate.INTEGER);
				else
					q.setParameter("idStatoDom", null, Hibernate.INTEGER);
				q.setParameter("descComuneEstDom", relProcSoggInt.getDescComEsteroDom(), Hibernate.STRING);
				if ((relProcSoggInt.getTAnComuniByCodiFkIdComDom() != null) && (relProcSoggInt.getTAnComuniByCodiFkIdComDom().getCodiKIdComune() > 0))
					q.setParameter("idComuneDom", relProcSoggInt.getTAnComuniByCodiFkIdComDom().getCodiKIdComune(), Hibernate.INTEGER);
				else
					q.setParameter("idComuneDom", null, Hibernate.INTEGER);
				if ((relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDom() != null)
						&& (relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDom().getCodiKIdTipoStrada() > 0))
					q.setParameter("idTipoStradaDom", relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDom().getCodiKIdTipoStrada(),
							Hibernate.INTEGER);
				else
					q.setParameter("idTipoStradaDom", null, Hibernate.INTEGER);
				q.setParameter("indirizzoDom", relProcSoggInt.getDescIndirDom(), Hibernate.STRING);
				q.setParameter("numCivicoDom", relProcSoggInt.getDescCivicoDom(), Hibernate.STRING);
				if (relProcSoggInt.getTAnComuniByCodiFkIdComDom() != null)
					q.setParameter("capDom", relProcSoggInt.getTAnComuniByCodiFkIdComDom().getDescCapComune(), Hibernate.STRING);
				else
					q.setParameter("capDom", null, Hibernate.STRING);

				if ((relProcSoggInt.getTAnStatoByCodiFkIdStatoDim() != null)
						&& (relProcSoggInt.getTAnStatoByCodiFkIdStatoDim().getCodiKIdStato() > 0))
					q.setParameter("idStatoDim", relProcSoggInt.getTAnStatoByCodiFkIdStatoDim().getCodiKIdStato(), Hibernate.INTEGER);
				else
					q.setParameter("idStatoDim", null, Hibernate.INTEGER);
				q.setParameter("descComuneEstDim", relProcSoggInt.getDescComEsteroDim(), Hibernate.STRING);
				if ((relProcSoggInt.getTAnComuniByCodiFkIdComDim() != null) && (relProcSoggInt.getTAnComuniByCodiFkIdComDim().getCodiKIdComune() > 0))
					q.setParameter("idComuneDim", relProcSoggInt.getTAnComuniByCodiFkIdComDim().getCodiKIdComune(), Hibernate.INTEGER);
				else
					q.setParameter("idComuneDim", null, Hibernate.INTEGER);
				if ((relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDim() != null)
						&& (relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDim().getCodiKIdTipoStrada() > 0))
					q.setParameter("idTipoStradaDim", relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDim().getCodiKIdTipoStrada(),
							Hibernate.INTEGER);
				else
					q.setParameter("idTipoStradaDim", null, Hibernate.INTEGER);
				q.setParameter("indirizzoDim", relProcSoggInt.getDescIndirDim(), Hibernate.STRING);
				q.setParameter("numCivicoDim", relProcSoggInt.getDescCivicoDim(), Hibernate.STRING);

				if (relProcSoggInt.getTAnComuniByCodiFkIdComDim() != null)
					q.setParameter("capDim", relProcSoggInt.getTAnComuniByCodiFkIdComDim().getDescCapComune(), Hibernate.STRING);
				else
					q.setParameter("capDim", null, Hibernate.STRING);

				if ((relProcSoggInt.getTAnStatoByCodiFkIdStatoDomel() != null)
						&& (relProcSoggInt.getTAnStatoByCodiFkIdStatoDomel().getCodiKIdStato() > 0))
					q.setParameter("idStatoDomel", relProcSoggInt.getTAnStatoByCodiFkIdStatoDomel().getCodiKIdStato(), Hibernate.INTEGER);
				else
					q.setParameter("idStatoDomel", null, Hibernate.INTEGER);
				q.setParameter("descComuneEstDomel", relProcSoggInt.getDescComEsteroDomel(), Hibernate.STRING);
				if ((relProcSoggInt.getTAnComuniByCodiFkIdComDomel() != null)
						&& (relProcSoggInt.getTAnComuniByCodiFkIdComDomel().getCodiKIdComune() > 0))
					q.setParameter("idComuneDomel", relProcSoggInt.getTAnComuniByCodiFkIdComDomel().getCodiKIdComune(), Hibernate.INTEGER);
				else
					q.setParameter("idComuneDomel", null, Hibernate.INTEGER);
				if ((relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDomel() != null)
						&& (relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDomel().getCodiKIdTipoStrada() > 0))
					q.setParameter("idTipoStradaDomel", relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDomel().getCodiKIdTipoStrada(),
							Hibernate.INTEGER);
				else
					q.setParameter("idTipoStradaDomel", null, Hibernate.INTEGER);
				q.setParameter("indirizzoDomel", relProcSoggInt.getDescIndirDomel(), Hibernate.STRING);

				if (relProcSoggInt.getTAnComuniByCodiFkIdComDomel() != null){
//					q.setParameter("numCivicoDomel", relProcSoggInt.getTAnComuniByCodiFkIdComDomel().getDescComune(), Hibernate.STRING);
					q.setParameter("numCivicoDomel", relProcSoggInt.getDescCivicoDomel(), Hibernate.STRING);
				}
				else
					q.setParameter("numCivicoDomel", null, Hibernate.STRING);

				if (relProcSoggInt.getTAnComuniByCodiFkIdComDomel() != null)
					q.setParameter("capDomel", relProcSoggInt.getTAnComuniByCodiFkIdComDomel().getDescCapComune(), Hibernate.STRING);
				else
					q.setParameter("capDomel", null, Hibernate.STRING);

				if ((relProcSoggInt.getDescDomelPresso() != null) && !relProcSoggInt.getDescDomelPresso().equals("-1"))
					q.setParameter("pressoDomel", relProcSoggInt.getDescDomelPresso(), Hibernate.STRING);
				else
					q.setParameter("pressoDomel", null, Hibernate.STRING);
				q.setParameter("domelNota", relProcSoggInt.getDescDomelNota(), Hibernate.STRING);
				// q.setParameter("flgAssociato",
				// relProcSoggInt.getFlgAssociato(), Hibernate.STRING);
				q.setParameter("flgValido", relProcSoggInt.getFlgValido(), Hibernate.CHARACTER);
				q.setParameter("codMotivo", relProcSoggInt.getTTpTipoMotivo().getDescMotivo(), Hibernate.STRING);
				// q.setParameter("descValido", relProcSoggInt.getDescValido(),
				// Hibernate.STRING);
				if ((relProcSoggInt.getTTpTipoPrevProc() != null && relProcSoggInt.getTTpTipoPrevProc().getTTpTipoPrevenzione() != null && relProcSoggInt
						.getTTpTipoPrevProc().getTTpTipoPrevenzione().getCodiKIdTipoPrev() > 0))
					q.setParameter("idTipoPrev", relProcSoggInt.getTTpTipoPrevProc().getTTpTipoPrevenzione().getCodiKIdTipoPrev(), Hibernate.INTEGER);
				else
					q.setParameter("idTipoPrev", null, Hibernate.INTEGER);
				if ((relProcSoggInt.getTTpTipoPrevProc() != null) && (relProcSoggInt.getTTpTipoPrevProc().getCodiKIdTipoNorm() > 0))
					q.setParameter("idTipoNorm", relProcSoggInt.getTTpTipoPrevProc().getCodiKIdTipoNorm(), Hibernate.INTEGER);
				else
					q.setParameter("idTipoNorm", null, Hibernate.INTEGER);
				if (relProcSoggInt.getTAtto() != null && relProcSoggInt.getTAtto().getId() != null
						&& relProcSoggInt.getTAtto().getId().getCodiKIdAtto() > 0)
					q.setParameter("idFonte", relProcSoggInt.getTAtto().getId().getCodiKIdAtto(), Hibernate.LONG);
				else
					q.setParameter("idFonte", null, Hibernate.LONG);
				q.setParameter("dataUltAggiornamento", dataUltAggiornamento, Hibernate.TIMESTAMP);
				q.setParameter("ruolo", relProcSoggInt.getTTpTipoRuolo().getCodiKIdRuolo(), Hibernate.INTEGER);
				q.setResultTransformer(Transformers.aliasToBean(AssociaPersonaDto.class));
				List list = q.list();
				if ((list != null) && (list.size() > 0)) {
					dto = (AssociaPersonaDto) list.get(0);
					soggetto.setIDPROC(dto.getIDPROC());
					soggetto.setIDPERS(dto.getIDPERS());
					soggetto.setIDSOGGINT(dto.getIDSOGGINT());
					soggetto.setIDUFFOWN(dto.getIDUFFOWN());
					soggetto.setIDVER(dto.getIDVER());
				}
				return dto;
			}
		});
	}

	public AssociaPersonaDto associaPersonaGiuridica(final int idUffOwn, final String codUtente, final Long idProc, final AssociaPersonaDto soggetto,
			final Date dataUltAggiornamento) {

		return (AssociaPersonaDto) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				AssociaPersonaDto dto = new AssociaPersonaDto();
				Query q = session.getNamedQuery("PG_SOGGETTO_associaPersGiur");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("descUtente", codUtente);
				q.setParameter("idProc", idProc, Hibernate.LONG);
				TPersonaGiur persona = soggetto.getPersonaGiur();
				TRelProcSogint relProcSoggInt = soggetto.getRelProcSoggInt();
				q.setParameter("idPers", persona.getId().getCodiKIdPersGiur(), Hibernate.LONG);
				q.setParameter("idVers", persona.getId().getCodiKIdVer(), Hibernate.SHORT);
				if ((relProcSoggInt.getTAnStatoByCodiFkIdStatoDomel() != null)
						&& (relProcSoggInt.getTAnStatoByCodiFkIdStatoDomel().getCodiKIdStato() > 0))
					q.setParameter("idStatoDomel", relProcSoggInt.getTAnStatoByCodiFkIdStatoDomel().getCodiKIdStato(), Hibernate.INTEGER);
				else
					q.setParameter("idStatoDomel", null, Hibernate.INTEGER);
				q.setParameter("descComuneEstDomel", relProcSoggInt.getDescComEsteroDomel(), Hibernate.STRING);
				if ((relProcSoggInt.getTAnComuniByCodiFkIdComDomel() != null)
						&& (relProcSoggInt.getTAnComuniByCodiFkIdComDomel().getCodiKIdComune() > 0))
					q.setParameter("idComuneDomel", relProcSoggInt.getTAnComuniByCodiFkIdComDomel().getCodiKIdComune(), Hibernate.INTEGER);
				else
					q.setParameter("idComuneDomel", null, Hibernate.INTEGER);
				if ((relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDomel() != null)
						&& (relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDomel().getCodiKIdTipoStrada() > 0))
					q.setParameter("idTipoStradaDomel", relProcSoggInt.getTTpTipoStradaByCodiFkIdTipostraDomel().getCodiKIdTipoStrada(),
							Hibernate.INTEGER);
				else
					q.setParameter("idTipoStradaDomel", null, Hibernate.INTEGER);
				q.setParameter("indirizzoDomel", relProcSoggInt.getDescIndirDomel(), Hibernate.STRING);
				if (relProcSoggInt.getTAnComuniByCodiFkIdComDomel() != null)
					q.setParameter("numCivicoDomel", relProcSoggInt.getDescCivicoDomel(), Hibernate.STRING);
				else
					q.setParameter("numCivicoDomel", null, Hibernate.STRING);

				if (relProcSoggInt.getTAnComuniByCodiFkIdComDomel() != null)
					q.setParameter("capDomel", relProcSoggInt.getTAnComuniByCodiFkIdComDomel().getDescCapComune(), Hibernate.STRING);
				else
					q.setParameter("capDomel", null, Hibernate.STRING);

				q.setParameter("pressoDomel", relProcSoggInt.getDescDomelPresso(), Hibernate.STRING);
				q.setParameter("domelNota", relProcSoggInt.getDescDomelNota(), Hibernate.STRING);
				// q.setParameter("flgAssociato",
				// relProcSoggInt.getFlgAssociato(), Hibernate.STRING);
				q.setParameter("flgValido", relProcSoggInt.getFlgValido(), Hibernate.CHARACTER);
				q.setParameter("codMotivo", relProcSoggInt.getTTpTipoMotivo().getDescMotivo(), Hibernate.STRING);
				// q.setParameter("descValido", relProcSoggInt.getDescValido(),
				// Hibernate.STRING);
				if ((relProcSoggInt.getTTpTipoPrevProc() != null && relProcSoggInt.getTTpTipoPrevProc().getTTpTipoPrevenzione() != null && relProcSoggInt
						.getTTpTipoPrevProc().getTTpTipoPrevenzione().getCodiKIdTipoPrev() > 0))
					q.setParameter("idTipoPrev", relProcSoggInt.getTTpTipoPrevProc().getTTpTipoPrevenzione().getCodiKIdTipoPrev(), Hibernate.INTEGER);
				else
					q.setParameter("idTipoPrev", null, Hibernate.INTEGER);
				if ((relProcSoggInt.getTTpTipoPrevProc() != null) && (relProcSoggInt.getTTpTipoPrevProc().getCodiKIdTipoNorm() > 0))
					q.setParameter("idTipoNorm", relProcSoggInt.getTTpTipoPrevProc().getCodiKIdTipoNorm(), Hibernate.INTEGER);
				else
					q.setParameter("idTipoNorm", null, Hibernate.INTEGER);
				if (relProcSoggInt.getTAtto() != null && relProcSoggInt.getTAtto().getId() != null
						&& relProcSoggInt.getTAtto().getId().getCodiKIdAtto() > 0)
					q.setParameter("idFonte", relProcSoggInt.getTAtto().getId().getCodiKIdAtto(), Hibernate.INTEGER);
				else
					q.setParameter("idFonte", null, Hibernate.INTEGER);
				q.setParameter("dataUltAggiornamento", dataUltAggiornamento, Hibernate.TIMESTAMP);
				q.setParameter("ruolo", relProcSoggInt.getTTpTipoRuolo().getCodiKIdRuolo(), Hibernate.INTEGER);

				// update su T_PERSONA_GIUR in caso di associazione a
				// procedimento di una persona giuridica

				if (persona.getImpoCapSoc() != null)
					q.setParameter("impoCapSoc", persona.getImpoCapSoc(), Hibernate.BIG_DECIMAL);
				else
					q.setParameter("impoCapSoc", null, Hibernate.BIG_DECIMAL);
				q.setParameter("note", persona.getDescNote(), Hibernate.STRING);
				q.setParameter("descRapprSoc", persona.getDescRapprSoc(), Hibernate.STRING);
				q.setParameter("descAmmin", persona.getDescAmmin(), Hibernate.STRING);

				q.setResultTransformer(Transformers.aliasToBean(AssociaPersonaDto.class));
				List list = q.list();
				if ((list != null) && (list.size() > 0)) {
					dto = (AssociaPersonaDto) list.get(0);
					soggetto.setIDPROC(dto.getIDPROC());
					soggetto.setIDPERS(dto.getIDPERS());
					soggetto.setIDSOGGINT(dto.getIDSOGGINT());
					soggetto.setIDUFFOWN(dto.getIDUFFOWN());
					soggetto.setIDVER(dto.getIDVER());
				}
				return dto;
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public void associaSoggettiTitolariPerBeneMisuraPatrimoniale(Long idBene, List<String> idSoggettiTitolariETitoli, long idProcedimento,
			int idUfficioUtente, long idAtto, long idSoggettoInteressato, String codUtente) {

		TRelAttoMisPatrId id = new TRelAttoMisPatrId();
		id.setCodiKIdAtto(idAtto);
		id.setCodiKIdBene(idBene);
		id.setCodiKIdProc(idProcedimento);
		id.setCodiKIdSoggInt(idSoggettoInteressato);
		id.setCodiKIdUffOwner(idUfficioUtente);
		for (String idSoggettiTitolareETitolo : idSoggettiTitolariETitoli) {
			String idSoggetto = null;
			String idInteresse = null;
			StringTokenizer st = new StringTokenizer(idSoggettiTitolareETitolo, ";");
			while (st.hasMoreTokens()) {
				idSoggetto = st.nextToken();
				idInteresse = st.nextToken();
			}
			id.setCodiKIdSoggTit(Long.parseLong(idSoggetto));
			// uno soggetto può avere diversi interessi su un stesso bene!!!!
			// long idTipoInteresse =
			// beneService.getTipoInteressePerSoggettoInteressato(idProcedimento,
			// idAtto, idUfficioUtente, idBene, idSoggettoTitolare);
			id.setCodiKIdTipoInt(Integer.parseInt(idInteresse));
			final TRelAttoMisPatr relazione = new TRelAttoMisPatr();
			relazione.setId(id);
			relazione.setDescUtente(codUtente);
			getHibernateTemplate().execute(new HibernateCallback() {

				public Object doInHibernate(final Session session) throws HibernateException, SQLException {

					session.save(relazione);
					session.flush();
					return null;
				}
			});
		}
	}

	public void associaSoggettoDirettoInteressato(final TRelProcSogint direttoInteressato) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_associaSoggettoDirettoInteressato");
				TRelProcSogintId id = direttoInteressato.getId();
				q.setParameter("idUffOwn", id.getCodiKIdUffOwner(), Hibernate.INTEGER);
				q.setParameter("idProcedimento", id.getCodiKIdProc(), Hibernate.LONG);
				q.setParameter("idSoggetto", id.getCodiKIdSoggInt(), Hibernate.LONG);
				q.setParameter("idFonte", direttoInteressato.getTAtto().getId().getCodiKIdAtto(), Hibernate.LONG);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public void cancellaAvvocato(final int idUfficioUtente, final Long idAvvocato) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				List<TAvvocati> relazione = null;
				DetachedCriteria criteria = DetachedCriteria.forClass(TAvvocati.class);
				criteria.add(Restrictions.eq("codiKIdAvvocato", idAvvocato));
				relazione = getHibernateTemplate().findByCriteria(criteria);
				TAvvocati relazioni = relazione.get(0);
				session.delete(relazioni);
				session.flush();
				return null;
			}
		});
	}

	public void cancellaEredeOppureConviventeDelSoggetto(long idSoggettoInteressato, long idProcedimento, int idUffOwn, int idEredeConv) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TRelSogintErediConv.class);
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		criteria.add(Restrictions.eq("id.codiKIdSoggInt", idSoggettoInteressato));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUffOwn));
		criteria.add(Restrictions.eq("id.codiKIdPrg", idEredeConv));
		TRelSogintErediConv itemSelezionato = (TRelSogintErediConv) getHibernateTemplate().findByCriteria(criteria).get(0);
		getHibernateTemplate().delete(itemSelezionato);
		getHibernateTemplate().flush();
		return;
	}

	@SuppressWarnings("rawtypes")
	public long verificaDataRevocaDifensoreAssociato(final long idProcedimento, final int idUfficio, final long idDifensore, final Date dataRevoca) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				long esito = 0L;
				Query q = session.getNamedQuery("PG_PROCEDIMENTO_verifica_data_revoca_difAss");
				q.setParameter("idProc", idProcedimento);
				q.setParameter("idUffOwn", idUfficio);
				q.setParameter("idDifensore", idDifensore);
				q.setParameter("dataRevoca", dataRevoca, Hibernate.DATE);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List list = q.list();
				Iterator it = list.iterator();
				while (it.hasNext()) {
					Map<String, Object> row = (Map<String, Object>) it.next();
					esito = (Long) row.get("ESITO");
				}
				return esito;
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public List<Object[]> verificaCancellaRelazioneAvvocatoProcSogint(final long idProcedimento, final int idUfficio, final long idDifensore) {

		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				List<Object[]> lista = null;
				Query q = session.getNamedQuery("PG_PROCEDIMENTO_verifica_cancella_difAss");
				q.setParameter("idProc", idProcedimento);
				q.setParameter("idUffOwn", idUfficio);
				q.setParameter("idDifensore", idDifensore);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				lista = q.list();
				return lista;
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public void cancellaRelazioneAvvocatoProcSogint(final long idProc, final int idUffOWn, final long idSogInt, final Long idAvvocato) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				List<TRelProcSogintAvv> relazione = null;
				DetachedCriteria criteria = DetachedCriteria.forClass(TRelProcSogintAvv.class);
				criteria.add(Restrictions.eq("id.codiKIdProc", idProc));
				criteria.add(Restrictions.eq("id.codiKIdSoggInt", idSogInt));
				criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUffOWn));
				criteria.add(Restrictions.eq("id.codiKIdAvvocato", idAvvocato));
				relazione = getHibernateTemplate().findByCriteria(criteria);
				TRelProcSogintAvv relazioni = relazione.get(0);
				session.delete(relazioni);
				session.flush();
				return null;
			}
		});
	}
	
	@SuppressWarnings("rawtypes")
	public void cancellaRelazioneIntestatariAvv(final long idProc, final int idUffOWn, final long idSogInt, final Long idAvvocato) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				List<TRelProcIntestatariAvv> relazione = null;
				DetachedCriteria criteria = DetachedCriteria.forClass(TRelProcIntestatariAvv.class);
				criteria.add(Restrictions.eq("id.codiKIdProc", idProc));
				criteria.add(Restrictions.eq("id.codiKIdIntestatario", idSogInt));
				criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUffOWn));
				criteria.add(Restrictions.eq("id.codiKIdAvvocato", idAvvocato));
				relazione = getHibernateTemplate().findByCriteria(criteria);
				TRelProcIntestatariAvv relazioni = relazione.get(0);
				session.delete(relazioni);
				session.flush();
				return null;
			}
		});
	}
	
	public List<Object[]> caricaDifensoriAssociatiSoggetto(final long idProcedimento, final int idUfficioUtente, final long idSoggettoInteressato,
			final Long idAvvocato) {

		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				List<Object[]> lista = null;
				Query q = session.getNamedQuery("PG_PROCEDIMENTO_carica_difensoreAssociato");
				q.setParameter("idProc", idProcedimento);
				q.setParameter("idUffOwn", idUfficioUtente);
				q.setParameter("idSoggetto", idSoggettoInteressato);
				if (idAvvocato != null)
					q.setParameter("idAvvocato", idAvvocato);
				else
					q.setParameter("idAvvocato", null, Hibernate.LONG);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				lista = q.list();
				return lista;
			}
		});
	}
	
	public List<Object[]> caricaDifensoriAssociatiIntestatari(final long idProcedimento, final int idUfficioUtente, final long idSoggettoInteressato,
			final Long idAvvocato) {

		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				List<Object[]> lista = null;
				Query q = session.getNamedQuery("PG_PROCEDIMENTO_carica_difensoreIntestatari");
				q.setParameter("idProc", idProcedimento);
				q.setParameter("idUffOwn", idUfficioUtente);
				q.setParameter("idSoggetto", idSoggettoInteressato);
				if (idAvvocato != null)
					q.setParameter("idAvvocato", idAvvocato);
				else
					q.setParameter("idAvvocato", null, Hibernate.LONG);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				lista = q.list();
				return lista;
			}
		});
	}

	public ProcedimentoTree costruisciAlberaturaAtti(long tipoUfficio) {

		ProcedimentoTree tree = ProcedimentoTree.getDefaultTree(tipoUfficio);
		tree.complete();
		return tree;
	}

	public ProcedimentoTree costruisciAlberaturaAtti(long tipoUfficio, Long idTipoFascicolo, Long idTipoProcedimento) {

		ProcedimentoTree tree = ProcedimentoTree.getDefaultTree(tipoUfficio, idTipoFascicolo, idTipoProcedimento);
		tree.complete();
		return tree;
	}

	@SuppressWarnings("rawtypes")
	public Map<String, Object> dettaglioMagistratoPerProcedimento(final int idUfficioUtente, final long idProcedimento, final long idMagistrato) {

		return (Map<String, Object>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_PROCEDIMENTO_dettaglio_magistrato_per_proc");
				q.setParameter("vidUffOwn", idUfficioUtente);
				q.setParameter("vidprocedimento", idProcedimento);
				q.setParameter("vidmagistrato", idMagistrato);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				if ((l != null) && (l.size() > 0))
					return l.get(0);
				return null;
			}
		});
	}

	public Map dettaglioPropostaAtto(final long idUfficio, final long idProcedimento, final long idAtto) {

		return (Map) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_dettaglioPropostaAtto");
				q.setParameter("idUffOwn", idUfficio);
				q.setParameter("idProc", idProcedimento, Hibernate.LONG);
				if (idAtto > 0)
					q.setParameter("idAtto", idAtto, Hibernate.LONG);
				else
					q.setParameter("idAtto", null, Hibernate.LONG);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				Iterator iter = l.iterator();
				Map map = null;
				while (iter.hasNext())
					map = (Map) iter.next();
				return map;
			}
		});
	}

	public void dissociaSoggettoDirettoInteressato(final TRelProcSogint direttoInteressato) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_dissociaSoggettoDirettoInteressato");
				TRelProcSogintId id = direttoInteressato.getId();
				q.setParameter("idUffOwn", id.getCodiKIdUffOwner(), Hibernate.INTEGER);
				q.setParameter("idProcedimento", id.getCodiKIdProc(), Hibernate.LONG);
				q.setParameter("idSoggetto", id.getCodiKIdSoggInt(), Hibernate.LONG);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public void eliminaAssociazioneMagistratoProcedimento(final Long idMagistrato, final long idProcedimento, final Integer idUfficioUtente) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				List<TRelMagProc> relazioni = null;
				DetachedCriteria criteria = DetachedCriteria.forClass(TRelMagProc.class);
				criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente));
				criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
				criteria.add(Restrictions.eq("id.codiKIdMagistrato", idMagistrato));
				relazioni = getHibernateTemplate().findByCriteria(criteria);
				TRelMagProc relazione = relazioni.get(0);
				session.delete(relazione);
				session.flush();
				return null;
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public void eliminaRelazioniAttoMisuraPatrimoniale(long idBene, long idProcedimento, int idUfficioUtente, long idAtto, long idSoggettoInteressato) {

		List<TRelAttoMisPatr> relazioni = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelAttoMisPatr.class);
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		criteria.add(Restrictions.eq("id.codiKIdAtto", idAtto));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente));
		criteria.add(Restrictions.eq("id.codiKIdBene", idBene));
		criteria.add(Restrictions.eq("id.codiKIdSoggInt", idSoggettoInteressato));
		relazioni = getHibernateTemplate().findByCriteria(criteria);
		for (final TRelAttoMisPatr relazione : relazioni)
			getHibernateTemplate().execute(new HibernateCallback() {

				public Object doInHibernate(final Session session) throws HibernateException, SQLException {

					session.delete(relazione);
					session.flush();
					return null;
				}
			});
	}
	
	
	

	public List<TTpCatAtto> getAllAttoByType(int type) {

		List<TTpCatAtto> risultati = null;
		// TTpCatAtto tipoAtto = new TTpCatAtto();
		// tipoAtto.setTTpTipoAtto(new TTpTipoAtto((int)type.longValue()));
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpCatAtto.class);
		criteria.addOrder(Order.asc("descCatAtto"));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		criteria.add(Restrictions.eq("TTpTipoAtto.codiKIdTipoAtto", type));
		risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	public List<TTpCatAtto> getAllFonte() {

		List<TTpCatAtto> risultati = null;
		TTpCatAtto tipoFonte = new TTpCatAtto();
		tipoFonte.setTTpTipoAtto(new TTpTipoAtto(Integer.parseInt(Constants.TIPO_ATTO_FONTE)));
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpCatAtto.class);
		criteria.addOrder(Order.asc("descCatAtto"));
		Criterion rest1= Restrictions.gt("dataFineValidita", new Date());
	    Criterion rest2= Restrictions.isNull("dataFineValidita");
	    criteria.add(Restrictions.or(rest1, rest2));
		criteria.add(Restrictions.eq("TTpTipoAtto.codiKIdTipoAtto", tipoFonte.getTTpTipoAtto().getCodiKIdTipoAtto()));
		risultati = getHibernateTemplate().findByCriteria(criteria);
		TTpCatAtto altraFonte = new TTpCatAtto();
		altraFonte.setDescCatAtto("Altra fonte");
		int indice = Collections.binarySearch(risultati, altraFonte, new TipoFonteComparator());
		if (indice != -1) {
			altraFonte = risultati.get(indice);
			// rimuovo Altra fonte dalla sua posizione attuale per inserirla,
			// dopo, in coda
			risultati.remove(indice);
			risultati.add(risultati.size(), altraFonte);
		}
		return risultati;
	}

	@SuppressWarnings("rawtypes")
	public DettaglioPersonaFisicaDto getDettaglioPersonaFisica(final TPersonaFisicaId id) {

		return (DettaglioPersonaFisicaDto) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_SOGGETTO_dettaglioPersonaFisica");
				q.setParameter("vidPersonaFis", id.getCodiKIdPersonaFis());
				q.setParameter("vidUffOwn", id.getCodiKIdUffOwner());
				q.setParameter("vidVer", id.getCodiKIdVer());
				q.setResultTransformer(Transformers.aliasToBean(DettaglioPersonaFisicaDto.class));
				List l = q.list();
				if ((l != null) && (l.size() > 0))
					return l.get(0);
				return null;
			}
		});
	}

	
	@SuppressWarnings("rawtypes")
	public void eliminaRelazioniAttoNormativaMisuraPatrimoniale(final long idProcedimento, final int idUfficioUtente, final long idSoggettoInteressato) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				TRelProcSogintId id = new TRelProcSogintId();
				id.setCodiKIdProc(idProcedimento);
				id.setCodiKIdUffOwner(idUfficioUtente);
				id.setCodiKIdSoggInt(idSoggettoInteressato);
				TRelProcSogint relazione = getHibernateTemplate().get(TRelProcSogint.class, id);
			
				TTpTipoPrevProc ttpv = new TTpTipoPrevProc();
				ttpv.setCodiKIdTipoNorm(0);
				relazione.setTTpTipoPrevProc(ttpv);

				session.saveOrUpdate(relazione);
				session.flush();
				return null;
			}
		});
	}

	
	
	@SuppressWarnings("rawtypes")
	public DettaglioPersonaFisicaProcDto getDettaglioPersonaFisicaProc(final TPersonaFisicaId id, final long codiKIdProc) {

		return (DettaglioPersonaFisicaProcDto) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_SOGGETTO_dettaglioPersonaFisica_Proc");
				q.setParameter("vidPersonaFis", id.getCodiKIdPersonaFis());
				q.setParameter("vidUffOwn", id.getCodiKIdUffOwner());
				q.setParameter("vidVer", id.getCodiKIdVer());
				q.setParameter("vidProc", codiKIdProc);
				q.setResultTransformer(Transformers.aliasToBean(DettaglioPersonaFisicaProcDto.class));
				List l = q.list();
				if ((l != null) && (l.size() > 0))
					return l.get(0);
				return null;
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public Map<String, Object> getDettaglioPersonaGiur(final TPersonaGiurId id) {

		return (Map<String, Object>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_SOGGETTO_dettaglioPersonaGiur");
				q.setParameter("vidPersonaGiur", id.getCodiKIdPersGiur());
				q.setParameter("vidUffOwn", id.getCodiKIdUffOwner());
				q.setParameter("vidVer", id.getCodiKIdVer());
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				if ((l != null) && (l.size() > 0))
					return l.get(0);
				return null;
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public DettaglioPersonaGiurProcDto getDettaglioPersonaGiurProc(final TPersonaGiurId id, final long codiKIdProc) {

		return (DettaglioPersonaGiurProcDto) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_SOGGETTO_dettaglioPersonaGiur_Proc");
				q.setParameter("vidPersonaGiur", id.getCodiKIdPersGiur());
				q.setParameter("vidUffOwn", id.getCodiKIdUffOwner());
				q.setParameter("vidVer", id.getCodiKIdVer());
				q.setParameter("vidProc", codiKIdProc);
				q.setResultTransformer(Transformers.aliasToBean(DettaglioPersonaGiurProcDto.class));
				List l = q.list();
				if ((l != null) && (l.size() > 0))
					return l.get(0);
				return null;
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public Map<String, Object> getDettaglioProcedimento(final TProcedimento id) {

		return (Map<String, Object>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_PROCEDIMENTO_dettaglioProcedimento");
				q.setParameter("vidProc", id.getCodiKIdProc());
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				if ((l != null) && (l.size() > 0))
					return l.get(0);
				return null;
			}
		});
	}

	public List<TTpEsito> getEsiti(String carattereMisura, long idCategoriaAtto, long idTipoUfficio) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpEsito.class);
		//Segnalazione SMP-03
		Criterion crit1 = Restrictions.gt("dataFineValidita", new Date());
		Criterion crit2 = Restrictions.isNull("dataFineValidita");
		Criterion crit1_2 = Restrictions.or(crit1, crit2);
		criteria.add(crit1_2);
		criteria.add(Restrictions.eq("TTpCatAtto.codiKIdCatAtto", (int) idCategoriaAtto));
		criteria.createAlias("TTpCaratMisura", "tCarMis");
		criteria.add(Restrictions.eq("tCarMis.codiKIdCaratMis", carattereMisura));
		if (idTipoUfficio > 0)
			criteria.add(Restrictions.eq("TTpTipoUfficio.codiKIdTipoUff", (int) idTipoUfficio));
		//criteria.addOrder(Order.asc("tCarMis.descCaratMisura"));
		criteria.addOrder(Order.asc("descEsito"));
		List<TTpEsito> dataFromDb = getHibernateTemplate().findByCriteria(criteria);
		return dataFromDb;
	}

	public Long getIdPadreAggravamento(long idProcedimento) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TRelProcProc.class);
		criteria.add(Restrictions.eq("id.codiKIdProc1", idProcedimento));
		criteria.add(Restrictions.eq("flgPadre", 'S'));
		List<TRelProcProc> relazioni = getHibernateTemplate().findByCriteria(criteria);
		if (relazioni.isEmpty())
			return null;
		TRelProcProc relazione = relazioni.get(0);
		return relazione.getId().getCodiKIdProc2();
	}
	
	public List<TRelProcProc> getTRelProcProcByIdProc1Padre(final long idProcedimento) {
		return (List<TRelProcProc>) getHibernateTemplate().execute(new HibernateCallback<List<TRelProcProc>>() {

			public List<TRelProcProc> doInHibernate(final Session session) throws HibernateException, SQLException {		
				Criteria criteria = session.createCriteria(TRelProcProc.class);
				criteria.add(Restrictions.eq("id.codiKIdProc1", idProcedimento));
				criteria.add(Restrictions.eq("flgPadre", 'S'));
				List<TRelProcProc> relazioni = criteria.list();
				if (relazioni.isEmpty())
					return null;
				for (TRelProcProc tRelProcProc : relazioni) {
					 if (tRelProcProc.getTAnUfficio()!=null) tRelProcProc.getTAnUfficio().getDescUfficio();
					 if (tRelProcProc.getTProcedimento()!=null) tRelProcProc.getTProcedimento().getDescUtente();
					 if (tRelProcProc.getTTpStatoProc()!=null) tRelProcProc.getTTpStatoProc().getDescUtente();
				}
				return relazioni;
			}
		});
		
		
	}	
	
	public List<TRelProcProc> getTRelProcProcByIdProc1(final long idProcedimento) {

		return (List<TRelProcProc>) getHibernateTemplate().execute(new HibernateCallback<List<TRelProcProc>>() {

			public List<TRelProcProc> doInHibernate(final Session session) throws HibernateException, SQLException {
				
				Criteria criteria = session.createCriteria(TRelProcProc.class);
				criteria.add(Restrictions.eq("id.codiKIdProc1", idProcedimento));
				criteria.add(Restrictions.not(Restrictions.in("flgTipoRel", new Character[] {'S','R'})));
				List<TRelProcProc> relazioni = criteria.list();
				if (relazioni.isEmpty())
					return null;
				for (TRelProcProc tRelProcProc : relazioni) {
					 if (tRelProcProc.getTAnUfficio()!=null) tRelProcProc.getTAnUfficio().getDescUfficio();
					 if (tRelProcProc.getTProcedimento()!=null) tRelProcProc.getTProcedimento().getDescUtente();
					 if (tRelProcProc.getTTpStatoProc()!=null) tRelProcProc.getTTpStatoProc().getDescUtente();
				}
				return relazioni;
			}
			
		});
	}

	/***
	 * Restituisce true se per il procedimento passato è stato fatto un invio in cassazione
	 */
	public boolean hasInvioInCassazione(long idProc2) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TCassMsg.class);
		criteria.add(Restrictions.eq("id.codiKIdProc", idProc2));
		criteria.add(Restrictions.eq("flgInviato", '1'));
		List<TRelProcProc> msg = getHibernateTemplate().findByCriteria(criteria);
		if (msg.isEmpty())
			return false;
		
		return true;
	}

	
	
	public TProcedimento getIdProcedimentoDestinatario(long idProcedimento,int idUffOwn,int idTipoFasc,int idUfficioArrivo) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TRelProcProc.class);
		criteria.createAlias("TProcedimento", "proc");
		
		criteria.add(Restrictions.eq("id.codiKIdProc2", idProcedimento));
		criteria.add(Restrictions.eq("TAnUfficio.codiKIdUfficio", idUffOwn));
		if (idTipoFasc!=0) criteria.add(Restrictions.eq("proc.TTpTipoFascicolo.codiKIdTipoFasc", idTipoFasc));
		criteria.add(Restrictions.eq("proc.TAnUfficioByCodiFkIdUffOwner.codiKIdUfficio", idUfficioArrivo));
		List<TRelProcProc> relazioni = getHibernateTemplate().findByCriteria(criteria);
		if (relazioni.isEmpty())
			return null;
		TRelProcProc relazione = relazioni.get(0);
		TProcedimento procedimento=getProcedimentoById(relazione.getId().getCodiKIdProc1());
		return procedimento;
	}

	//P.Q. 04/09/2025 Ticket 20250807017 Inizio
	public Long getIdProcDaRelProcProc(Short annoPadre, Integer numeroPadre, Integer idUfficioMittente) {
	//P.Q. 04/09/2025 Ticket 20250807017 Fine
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelProcProc.class);
		criteria.add(Restrictions.eq("numeAnnoProc2", annoPadre));
		criteria.add(Restrictions.eq("numeProcedimento2", numeroPadre.longValue()));
		criteria.add(Restrictions.eq("TAnUfficio.codiKIdUfficio", idUfficioMittente));
		criteria.add(Restrictions.isNull("flgPadre"));
		List<TRelProcProc> relazioni = getHibernateTemplate().findByCriteria(criteria);
		if (relazioni.isEmpty())
			return null;
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		for (TRelProcProc relazione : relazioni)
			if (isRelativaAllUfficio(relazione, idUfficioUtente))
				return relazione.getId().getCodiKIdProc1();
		return null;
	}

	private boolean isRelativaAllUfficio(final TRelProcProc relazione, final Integer idUfficioUtente) {

		String idProcStringa = String.valueOf(relazione.getId().getCodiKIdProc1());
		String idUfficioStringa = String.valueOf(idUfficioUtente);
		boolean risultato = idProcStringa.startsWith(idUfficioStringa);
		return risultato;
	}

	@SuppressWarnings("rawtypes")
	public List<Long> getIdProcByAnnoNumProc(final int idUffOwn, final short annoProc, final long numeProc, final int idTipoFasc) {

		return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_PROCEDIMENTO_ricercaIdProcedimento");
				q.setParameter("vidUffOwn", idUffOwn);
				q.setParameter("vnumProc", numeProc);
				q.setParameter("vannoProc", annoProc);
				if (idTipoFasc > 0)
					q.setParameter("idTipoFasc", idTipoFasc, Hibernate.INTEGER);
				else
					q.setParameter("idTipoFasc", null, Hibernate.INTEGER);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				List retList = null; 
				if ((l != null) && (l.size() > 0)) {
					retList = new ArrayList<Long>();
					for(int i=0; i<l.size(); i++) {
						Map<String, Object> ret = (Map<String, Object>) l.get(0);
						Long idProc = (Long) ret.get("CODI_K_ID_PROC");
						retList.add(idProc);
					}
				}
				return retList;
			}
		});
	}

	public List<TAnUfficio> getIndirizzoByOrganoAndComune(String idCodiceComune, long idOrgano) {

		List<TAnUfficio> risultati = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TAnUfficio.class);
		criteria.add(Restrictions.eq("TAnComuni.codiKIdComune", Integer.parseInt(idCodiceComune)));
		criteria.add(Restrictions.eq("TTpTipoUfficio.codiKIdTipoUff", (int) idOrgano));
		criteria.addOrder(Order.desc("descIndirizzoSede"));
		risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	public List<TAnProcedente> getListaProcedenti(final long idUfficio, final Long idTipoPrev, final Long idTipoFasc) {

		return (List<TAnProcedente>)getHibernateTemplate().execute(new HibernateCallback() {

			public List<TAnProcedente> doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_PROCEDIMENTO_listaProcedenti");
				q.setParameter("idUffOwn", idUfficio, Hibernate.LONG);
				if ((idTipoPrev != null) && (idTipoPrev > 0))
					q.setParameter("idTipoPrev", idTipoPrev, Hibernate.LONG);
				else
					q.setParameter("idTipoPrev", null, Hibernate.LONG);
				
				q.setParameter("idTipoFasc", idTipoFasc, Hibernate.LONG);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				return q.list();
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public List<RicercaProcedimentoDto> getListaProcedimentiByCriteria(final int idUffOwn, final Short annoProc, final Long numProcDa, final Long numProcA,
			final Date dataIscrDa, final Date dataIscrA, final Long idStatoProc, final String cognSogg, final String nomeSogg,
			final String ragSociale, final Long idPMAssegn, final Long idTipoProc, final Short annoDecreto, final Long numDecreto,
			final Long idTipoProcedente, final Long idProcedente, final Boolean antimafia, final Boolean antiTerrorismo, final Boolean ordinaria,
			final Long tipoFascicolo, final Integer idTpUffRic, final Integer idUffRic, final String nomeDifensore, 
			final String cognomeDifensore, final String cfDifensore) {

		return (List<RicercaProcedimentoDto>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				boolean difensoreFiltro = false;
				
				if (cognomeDifensore == null && nomeDifensore == null && cfDifensore == null)
				{
					difensoreFiltro = false;
				}
				else
				{
					difensoreFiltro = true;
				}
				
				Query q = null;
				if (difensoreFiltro)
					 q = session.getNamedQuery("PG_PROCEDIMENTO_listaProcedimenti_criteria_difensore");
				else
					 q = session.getNamedQuery("PG_PROCEDIMENTO_listaProcedimenti_criteria");
					
				q.setParameter("vidUffOwn", idUffOwn, Hibernate.INTEGER);
				q.setParameter("vannoProc", annoProc, Hibernate.SHORT);
				q.setParameter("vnumProcDa", numProcDa, Hibernate.LONG);
				q.setParameter("vnumProcA", numProcA, Hibernate.LONG);
				q.setParameter("vdataIscrDa", dataIscrDa, Hibernate.DATE);
				q.setParameter("vdataIscrA", dataIscrA, Hibernate.DATE);
				q.setParameter("vidStatoProc", idStatoProc, Hibernate.LONG);
				q.setParameter("vcognSogg", cognSogg, Hibernate.STRING);
				q.setParameter("vnomeSogg", nomeSogg, Hibernate.STRING);
				q.setParameter("vragSociale", ragSociale, Hibernate.STRING);
				q.setParameter("vidPMAssegn", idPMAssegn, Hibernate.LONG);
				q.setParameter("vidTipoProc", idTipoProc, Hibernate.LONG);
				q.setParameter("vannoDecreto", annoDecreto, Hibernate.SHORT);
				q.setParameter("vnumDecreto", numDecreto, Hibernate.LONG);
				q.setParameter("vidTipoProcedente", idTipoProcedente, Hibernate.LONG);
				q.setParameter("vidProcedente", idProcedente, Hibernate.LONG);
				char lAntimafia = '0';
				if (antimafia)
					lAntimafia = 'S';
				q.setParameter("vantimafia", lAntimafia, Hibernate.CHARACTER);
				char lAntiTerrorismo = '0';
				if (antiTerrorismo)
					lAntiTerrorismo = 'S';
				q.setParameter("vantiTerrorismo", lAntiTerrorismo, Hibernate.CHARACTER);
				char lOrdinaria = '0';
				if (ordinaria)
					lOrdinaria = 'S';
				q.setParameter("vordinaria", lOrdinaria, Hibernate.CHARACTER);
				q.setParameter("vtipoFascicolo", tipoFascicolo, Hibernate.LONG);
				
				if(idTpUffRic!=null)
					q.setParameter("IDTPUFFRIC", idTpUffRic, Hibernate.INTEGER);
				else
					q.setParameter("IDTPUFFRIC", null, Hibernate.INTEGER);
				
				if(idUffRic!=null)
					q.setParameter("IDUFFRIC", idUffRic, Hibernate.INTEGER);
				else
					q.setParameter("IDUFFRIC", null, Hibernate.INTEGER);
				
				if (difensoreFiltro)
				{
					if (cognomeDifensore != null)
						if (cognomeDifensore.trim().length() > 0)
							q.setParameter("COGNDIFEN", cognomeDifensore, Hibernate.STRING);
						else
							q.setParameter("COGNDIFEN", null, Hibernate.STRING);
					else
						q.setParameter("COGNDIFEN", null, Hibernate.STRING);
	
					//Inizio controllo parametri filtro difensore
					if (nomeDifensore != null)
						if (nomeDifensore.trim().length() > 0)
							q.setParameter("NOMEDIFEN", nomeDifensore, Hibernate.STRING);
						else
							q.setParameter("NOMEDIFEN", null, Hibernate.STRING);
					else
						q.setParameter("NOMEDIFEN", null, Hibernate.STRING);
					
					if (cfDifensore != null)
						if (cfDifensore.trim().length() > 0)
							q.setParameter("CFDIFEN", cfDifensore, Hibernate.STRING);
						else
							q.setParameter("CFDIFEN", null, Hibernate.STRING);
					else
						q.setParameter("CFDIFEN", null, Hibernate.STRING);
					
					//Fine controllo parametri filtro difensore
				}
				q.setResultTransformer(Transformers.aliasToBean(RicercaProcedimentoDto.class));

				List<RicercaProcedimentoDto> result = q.list();
				
				return result; 
			}
		});
	}
	
	public List<TAnMagistrati> getMagistratiByGeneralita(TAnMagistrati magistrato) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TAnMagistrati.class);
		if (Utility.isDefinito(magistrato.getDescCognomeMag()))
			criteria.add(Restrictions.eq("descCognomeMag", magistrato.getDescCognomeMag().toUpperCase()));
		if (Utility.isDefinito(magistrato.getDescNomeMag()))
			criteria.add(Restrictions.eq("descNomeMag", magistrato.getDescNomeMag().toUpperCase()));
		List<TAnMagistrati> risultato = null;
		risultato = getHibernateTemplate().findByCriteria(criteria);
		return risultato;
	}

	public TAnMagistrati getMagistratoById(long idMagistrato) {

		return getHibernateTemplate().get(TAnMagistrati.class, idMagistrato);
	}

	public List getNumeriRegistro(long idProcedimento) {

		Query query = getSession()
				.createSQLQuery(
						"SELECT DISTINCT r.*, u.*,s.* " + "FROM T_REL_PROC_PROC r INNER JOIN T_AN_UFFICIO u "
								+ "ON(u.CODI_K_ID_UFFICIO=r.CODI_FK_ID_UFF_OWNER2) " + "INNER JOIN T_TP_STATO_PROC s "
								+ "ON(s.CODI_K_ID_STATO_PROC=r.CODI_FK_ID_STATO_PROC2) " + "LEFT OUTER JOIN T_ATTO a "
								+ "ON CODI_FK_ID_ATTO_AA = CODI_K_ID_ATTO " + "WHERE r.CODI_K_ID_PROC1= " + idProcedimento
								+ "AND (r.FLG_TIPO_REL NOT IN ('A', 'S', 'R') OR r.FLG_TIPO_REL IS NULL) " + "AND FLG_PADRE IS NULL ")
				.addEntity("r", TRelProcProc.class).addEntity("u", TAnUfficio.class).addEntity("s", TTpStatoProc.class);
		List l = query.list();
		return l;
	}

	public List getNumeroRegistroPadre(long idProcedimento) {

		Query query = getSession()
				.createSQLQuery(
						"SELECT DISTINCT r.*, u.*,s.* " + "FROM T_REL_PROC_PROC r INNER JOIN T_AN_UFFICIO u "
								+ "ON(u.CODI_K_ID_UFFICIO=r.CODI_FK_ID_UFF_OWNER2) " + "INNER JOIN T_TP_STATO_PROC s "
								+ "ON(s.CODI_K_ID_STATO_PROC=r.CODI_FK_ID_STATO_PROC2) " + "INNER JOIN T_ATTO a "
								+ "ON CODI_FK_ID_ATTO_AA = CODI_K_ID_ATTO " + "WHERE r.CODI_K_ID_PROC1= " + idProcedimento + "AND FLG_PADRE = 'S' ")
				.addEntity("r", TRelProcProc.class).addEntity("u", TAnUfficio.class).addEntity("s", TTpStatoProc.class);
		List l = query.list();
		return l;
	}

	public List getNumeriRegistroAccertamento(long idProcedimento) {
		Query query = getSession()
				.createSQLQuery(
						"SELECT r.*, u.*,s.* " + "								   FROM T_REL_PROC_PROC r INNER JOIN T_AN_UFFICIO u "
								+ "														  	ON(u.CODI_K_ID_UFFICIO=r.CODI_FK_ID_UFF_OWNER2) "
								+ "														  INNER JOIN T_TP_STATO_PROC s "
								+ "															ON(s.CODI_K_ID_STATO_PROC=r.CODI_FK_ID_STATO_PROC2) " + "								   WHERE r.CODI_K_ID_PROC1= "
								+ idProcedimento + "									 AND r.FLG_TIPO_REL = 'A' ").addEntity("r", TRelProcProc.class)
				.addEntity("u", TAnUfficio.class).addEntity("s", TTpStatoProc.class);
		List l = query.list();
		return l;
	}

	public List<TTpTipoUfficio> getOrgani(final String tipo) {

		return (List<TTpTipoUfficio>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query query = getSession()
						.createQuery(
								"from TTpTipoUfficio tu " + "where tu.descTipoAut = :tipo " + "and (tu.dataFineValidita is null or tu.dataFineValidita > sysdate)"
										+ "and exists(from TAnUfficio u where u.TTpTipoUfficio.codiKIdTipoUff=tu.codiKIdTipoUff)"
										+ "order by tu.descTipoUff");
				query.setParameter("tipo", tipo);
				return query.list();
			}
		});
	}

	public List<TSoggetto> getPersonePiuVersioni(long identificativoSoggettoProcedimento, Integer idUfficioUtente) {

		List<TSoggetto> risultati = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TSoggetto.class);
		criteria.add(Restrictions.eq("id.codiKIdSoggInt", identificativoSoggettoProcedimento));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente));
		// criteria.addOrder(Order.desc("id.codiKIdVer"));
		risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	public TProcedimento getProcedimentoById(long idProcedimento) {

		TProcedimento procedimento = getHibernateTemplate().get(TProcedimento.class, idProcedimento);
		Hibernate.initialize(procedimento.getTAnUfficioByCodiFkIdUffOwner());
		procedimento.getTTpStatoProc();
		return procedimento;
	}
	
	public TProcedimento getProcedimentoById2(long idProcedimento) {
		TProcedimento procedimento = getHibernateTemplate().get(TProcedimento.class, idProcedimento);
		//Hibernate.initialize(procedimento.getTAnUfficioByCodiFkIdUffOwner());
		procedimento.getTTpStatoProc();
		return procedimento;
	}

	public List<SogIntDettaglioProcedimentoDto> getSoggettiInt_Associati(final long idUfficio, final long idProcedimento, final long idFonte) {

		return (List<SogIntDettaglioProcedimentoDto>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_PROCEDIMENTO_elencoDirettiInteressatiAssociati");
				q.setParameter("idUffOwn", idUfficio);
				q.setParameter("idProcedimento", idProcedimento);
				q.setParameter("idFonte", idFonte);
				q.setResultTransformer(Transformers.aliasToBean(SogIntDettaglioProcedimentoDto.class));
				return q.list();
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public List<SogIntDettaglioProcedimentoDto> getSoggettiInt_DettaglioProcedimento(final TProcedimento id, final String descRuolo,
			final Character flagValido) {

		return (List<SogIntDettaglioProcedimentoDto>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_PROCEDIMENTO_sogInt_dettaglioProcedimento");
				q.setParameter("vidProc", id.getCodiKIdProc());
				if ((descRuolo != null) && !"".equals(descRuolo))
					q.setParameter("descRuolo", descRuolo);
				else
					q.setParameter("descRuolo", null, Hibernate.STRING);

				q.setParameter("flagValido", flagValido.toString(), Hibernate.STRING);
				
				//System.out.printf("id / descruolo / flagcvalid " +  id.getCodiKIdProc(), " / " + descRuolo + " / " + flagValido.toString());

				q.setResultTransformer(Transformers.aliasToBean(SogIntDettaglioProcedimentoDto.class));
				//System.out.println("zzzzzzzzzzzzzzzzzzzz dopo trasformers.aliastobean");
				return q.list();
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public List<SogIntDettaglioProcedimentoDto> getSoggettiIntInv_DettaglioProcedimento(final long idProcedimento, final long idSogInv) {

		return (List<SogIntDettaglioProcedimentoDto>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_PROCEDIMENTO_sogIntInv_dettaglioProcedimento");
				q.setParameter("idProc", idProcedimento);
				q.setParameter("idSogInv", idSogInv);

				q.setResultTransformer(Transformers.aliasToBean(SogIntDettaglioProcedimentoDto.class));
				return q.list();
			}
		});
	}
	
	@SuppressWarnings("rawtypes")
	public List<SogIntDettaglioProcedimentoDto> soggettiDirettiInteressatiApplicazioneProvvisoria(final long idProcedimento, final int idUffOwn, final String codiceUtente){

		return (List<SogIntDettaglioProcedimentoDto>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_PROCEDIMENTO_lista_sogInt_dec_appl_prov");
				q.setParameter("vidProc", idProcedimento);
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("descUtente", codiceUtente);
				

				q.setResultTransformer(Transformers.aliasToBean(SogIntDettaglioProcedimentoDto.class));
				return q.list();
			}
		});
	}
	
	
	
	@SuppressWarnings("rawtypes")
	public List<SogIntDettaglioProcedimentoDto> getSoggettiInt_DettaglioProcedimentoVer(final TProcedimento id, final String descRuolo,
			final Character flagValido, final long idSogg, final long idUffOwn, final Integer idVer) {

		return (List<SogIntDettaglioProcedimentoDto>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_PROCEDIMENTO_sogInt_dettaglioProcedimentoVer");
				q.setParameter("vidProc", id.getCodiKIdProc());
				if ((descRuolo != null) && !"".equals(descRuolo))
					q.setParameter("descRuolo", descRuolo);
				else
					q.setParameter("descRuolo", null, Hibernate.STRING);

				q.setParameter("flagValido", flagValido.toString(), Hibernate.STRING);

				q.setParameter("idSogg", idSogg, Hibernate.LONG);
				q.setParameter("idUffOwn", idUffOwn, Hibernate.LONG);
				
				if (idVer!=null && idVer>0)
					q.setParameter("idVer", idVer);
				else
					q.setParameter("idVer", null, Hibernate.INTEGER);
				q.setResultTransformer(Transformers.aliasToBean(SogIntDettaglioProcedimentoDto.class));
				return q.list();
			}
		});
	}	

	@SuppressWarnings("rawtypes")
	public List<SogTitDettaglioProcedimentoDto> getSoggettiTit_DettaglioProcedimento(final long id) {
		return (List<SogTitDettaglioProcedimentoDto>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_PROCEDIMENTO_sogTit_dettaglioProcedimento");
				q.setParameter("vidProc", id);
				q.setResultTransformer(Transformers.aliasToBean(SogTitDettaglioProcedimentoDto.class));
				return q.list();
			}
		});
	}

	public List<SogIntDettaglioProcedimentoDto> getSoggettiInt_NonAssociati(final long idUfficio, final long idProcedimento) {

		return (List<SogIntDettaglioProcedimentoDto>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_PROCEDIMENTO_elencoDirettiInteressatiNonAssociati");
				q.setParameter("idUffOwn", idUfficio);
				q.setParameter("idProcedimento", idProcedimento);
				q.setResultTransformer(Transformers.aliasToBean(SogIntDettaglioProcedimentoDto.class));
				return q.list();
			}
		});
	}

	public SogIntDettaglioProcedimentoDto getSogIntById(final long idSogInt, final long idUfficio, final long idProcedimento) {

		return (SogIntDettaglioProcedimentoDto) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_SOGGETTO_getSogIntById");
				q.setParameter("idSogInt", idSogInt);
				q.setParameter("idUffOwn", idUfficio);
				q.setParameter("idProcedimento", idProcedimento);
				q.setResultTransformer(Transformers.aliasToBean(SogIntDettaglioProcedimentoDto.class));

				if (q.list() != null && q.list().size() > 0)
					return q.list().get(0);

				return null;
			}
		});
	}

	public SogIntDettaglioProcedimentoDto getSogIntById(final long idSogInt, final long idUfficio, final long idProcedimento, final String ruolo) {

		return (SogIntDettaglioProcedimentoDto) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_SOGGETTO_getSogIntByIdERuolo");
				q.setParameter("idSogInt", idSogInt);
				q.setParameter("idUffOwn", idUfficio);
				q.setParameter("idProcedimento", idProcedimento);
				q.setParameter("ruolo", ruolo);
				q.setResultTransformer(Transformers.aliasToBean(SogIntDettaglioProcedimentoDto.class));

				if (q.list() != null && q.list().size() > 0)
					return q.list().get(0);

				return null;
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public List<ContaDifensoriSogIntDto> getSogIntSenzaDifensore(final long idProcedimento, final long idUfficio) {

		return (List<ContaDifensoriSogIntDto>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_soggettiInteressatiSenzaDifensore");
				q.setParameter("idUffOwn", idUfficio);
				q.setParameter("idProcedimento", idProcedimento);
				q.setResultTransformer(Transformers.aliasToBean(ContaDifensoriSogIntDto.class));
				List l = q.list();
				return l;
			}
		});
	}

	public TTpTipoInteresse getTipoInteresseById(int idTipoInteresse) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TTpTipoInteresse.class);
		criteria.add(Restrictions.eq("codiKIdTipoInt", idTipoInteresse));
		List<TTpTipoInteresse> risultati = getHibernateTemplate().findByCriteria(criteria);
		return DataAccessUtils.requiredUniqueResult(risultati);
	}

	public TTpTipoMisura getTipoMisuraById(Long codiFkIdTipoMis) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TTpTipoMisura.class);
		criteria.add(Restrictions.eq("codiKIdTipoMisura", codiFkIdTipoMis.intValue()));
		List<TTpTipoMisura> risultati = getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size() > 0)
			return DataAccessUtils.requiredUniqueResult(risultati);
		return null;
	}

	public TRelProcSogint getTrelProcSogintByIdsoggIdProcIdUff(Long idProcedimento, Long idSoggetto, int idUffOwn, Long tipoPrev) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TRelProcSogint.class);
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento.longValue()));
		if (idSoggetto != null)
			criteria.add(Restrictions.eq("id.codiKIdSoggInt", idSoggetto.longValue()));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUffOwn));
		if (tipoPrev > 0) {
			criteria.createAlias("TTpTipoPrevProc", "prevProc");
			criteria.add(Restrictions.eq("prevProc.TTpTipoPrevenzione.codiKIdTipoPrev", (int) tipoPrev.longValue()));
		}
		List<TRelProcSogint> risultato = null;
		risultato = getHibernateTemplate().findByCriteria(criteria);
		if (risultato.size() > 0)
			return risultato.get(0);
		return null;
	}

	public void inserisciEredeOppureConviventeDelSoggetto(long idSoggettoInteressato, long idProcedimento, int idUffOwn, String codiceUtente,
			String codiTipo, TRelSogintErediConv trel) {

		TRelSogintErediConvId id = new TRelSogintErediConvId();
		int idProg = -1;
		if (trel.getId() != null)
			idProg = trel.getId().getCodiKIdPrg();
		id.setCodiKIdProc(idProcedimento);
		id.setCodiKIdSoggInt(idSoggettoInteressato);
		id.setCodiKIdUffOwner(idUffOwn);
		if (idProg > 0)
			id.setCodiKIdPrg((byte) idProg);
		trel.setId(id);
		TAnStato tstato = new TAnStato();
		TAnComuni tcomuni = new TAnComuni();
		if ((trel.getTAnStato() != null) && (trel.getTAnStato().getCodiKIdStato() != -1)) {
			tstato.setCodiKIdStato(trel.getTAnStato().getCodiKIdStato());
			if (trel.getTAnStato().getCodiKIdStato() == 1) {
				if ((trel.getTAnComuni() != null) && (trel.getTAnComuni().getCodiKIdComune() > 0)) {
					tcomuni.setCodiKIdComune(trel.getTAnComuni().getCodiKIdComune());
					trel.setDescComEstero(null);
				} else {
					tcomuni = null;
					trel.setDescComEstero(null);
				}
			} else if (trel.getDescComEstero() != null) {
				trel.setDescComEstero(trel.getDescComEstero());
				tcomuni = null;
			} else
				tcomuni = null;
		} else {
			tstato = null;
			tcomuni = null;
			trel.setDescComEstero(null);
		}
		trel.setTAnStato(tstato);
		trel.setTAnComuni(tcomuni);
		trel.setDescTipo(codiTipo.charAt(0));
		trel.setDescUtente(codiceUtente);
		getHibernateTemplate().saveOrUpdate(trel);
		getHibernateTemplate().flush();
		return;
	}

	public long inserisciFonte(final long idUfficio, final String codiceUtente, final long idProcedimento, final long idStatoProcedimento,
			final TAtto atto, final TIstanza istanza) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_inserisciFonte");
				q.setParameter("idUffOwn", idUfficio, Hibernate.LONG);
				q.setParameter("descUtente", codiceUtente, Hibernate.STRING);
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setParameter("idStatoProc", idStatoProcedimento, Hibernate.LONG);
				q.setParameter("idFonte", atto.getTTpCatAtto().getCodiKIdCatAtto(), Hibernate.INTEGER);
				if (atto.getTTpCatAtto().getCodiKIdCatAtto() == Long.parseLong(Constants.FONTE_ALTRO)) {
					// la fonte è 'Altro'
					q.setParameter("altroOrgano", istanza.getDescAltroOrgano(), Hibernate.STRING);
					q.setParameter("idOrgano", null, Hibernate.INTEGER);
				} else {
					q.setParameter("altroOrgano", null, Hibernate.STRING);
					q.setParameter("idOrgano", atto.getTAnUfficio().getCodiKIdUfficio(), Hibernate.INTEGER);
				}
				if (istanza != null && istanza.getDescNumeRif() != null)
					q.setParameter("numeroRiferimento", istanza.getDescNumeRif(), Hibernate.STRING);
				else
					q.setParameter("numeroRiferimento", null, Hibernate.STRING);
				q.setParameter("dataInformativa", atto.getDataEmissione(), Hibernate.DATE);

				if (atto.getDescNote() != null)
					q.setParameter("note", atto.getDescNote(), Hibernate.STRING);
				else
					q.setParameter("note", null, Hibernate.STRING);

				if (atto.getDataRicezione() != null)
					q.setParameter("dataRicezione", atto.getDataRicezione(), Hibernate.DATE);
				else
					q.setParameter("dataRicezione", null, Hibernate.DATE);
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}

				// controllo idUfficio -1
				if (atto.getTAnUfficio().getCodiKIdUfficio() > 0)
					q.setParameter("idOrgano", atto.getTAnUfficio().getCodiKIdUfficio(), Hibernate.INTEGER);
				else
					q.setParameter("idOrgano", null, Hibernate.INTEGER);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long id = (Long) first.get("IDCURRENTATTO");
				return id;
			}
		});
	}

	public long inserisciArchiviazione(final long idUfficio, final String codiceUtente, final long idProcedimento, final long idStatoProcedimento,
			final TAtto atto, final TIstanza istanza) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_inserisciArchiviazione");
				q.setParameter("idUffOwn", idUfficio, Hibernate.LONG);
				q.setParameter("descUtente", codiceUtente, Hibernate.STRING);
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setParameter("idStatoProc", idStatoProcedimento, Hibernate.LONG);
				q.setParameter("idCatAtto", atto.getTTpCatAtto().getCodiKIdCatAtto(), Hibernate.INTEGER);
				q.setParameter("idTipoAtto", atto.getTTpTipoAtto().getCodiKIdTipoAtto(), Hibernate.INTEGER);
				q.setParameter("idOrgano", null, Hibernate.INTEGER);
				q.setParameter("altroOrgano", null, Hibernate.STRING);
				// if (atto.getTTpCatAtto().getCodiKIdCatAtto() ==
				// Long.parseLong(Constants.FONTE_ALTRO)) {
				// // la fonte è 'Altro'
				// q.setParameter("altroOrgano", istanza.getDescAltroOrgano(),
				// Hibernate.STRING);
				// q.setParameter("idOrgano", null, Hibernate.INTEGER);
				// } else {
				// q.setParameter("altroOrgano", null, Hibernate.STRING);
				// q.setParameter("idOrgano",
				// atto.getTAnUfficio().getCodiKIdUfficio(), Hibernate.INTEGER);
				// }
				if (istanza != null && istanza.getDescNumeRif() != null)
					q.setParameter("numeroRiferimento", istanza.getDescNumeRif(), Hibernate.STRING);
				else
					q.setParameter("numeroRiferimento", null, Hibernate.STRING);
				q.setParameter("dataInformativa", atto.getDataEmissione(), Hibernate.DATE);

				if (atto.getDescNote() != null)
					q.setParameter("note", atto.getDescNote(), Hibernate.STRING);
				else
					q.setParameter("note", null, Hibernate.STRING);

				if (atto.getDataRicezione() != null)
					q.setParameter("dataRicezione", atto.getDataRicezione(), Hibernate.DATE);
				else
					q.setParameter("dataRicezione", null, Hibernate.DATE);
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}

				// controllo idUfficio -1
				if (atto.getTAnUfficio().getCodiKIdUfficio() > 0)
					q.setParameter("idOrgano", atto.getTAnUfficio().getCodiKIdUfficio(), Hibernate.INTEGER);
				else
					q.setParameter("idOrgano", null, Hibernate.INTEGER);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long id = (Long) first.get("IDCURRENTATTO");
				return id;
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public Long inserisciModificaDifensoreAssociato(final long idProc, final int idUffOWn, final long idSogInt, final Long idAvvocato,
			final TAvvocati avv, final Date dataNomina, final Date dataRevoca, final String tipoNomina, final String codiceUtente) {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_PROCEDIMENTO_insert_update_difensoreAssociato");
				q.setParameter("idProc", idProc, Hibernate.LONG);
				q.setParameter("idUffOwn", idUffOWn, Hibernate.INTEGER);
				q.setParameter("idSoggetto", idSogInt, Hibernate.LONG);
				if (idAvvocato != null)
					q.setParameter("idAvvocato", idAvvocato);
				else
					q.setParameter("idAvvocato", null, Hibernate.LONG);
				q.setParameter("cognomeAvv", avv.getDescCognomeAvv(), Hibernate.STRING);
				q.setParameter("nomeAvv", avv.getDescNomeAvv(), Hibernate.STRING);
				q.setParameter("dataNascita", avv.getDataNascita(), Hibernate.DATE);
				q.setParameter("luogoNascita", avv.getDescLuogoNascita(), Hibernate.STRING);
				q.setParameter("provinciaNascita", avv.getDescProvNascita(), Hibernate.STRING);
				q.setParameter("codiceFiscale", avv.getDescCodiceFiscale(), Hibernate.STRING);
				q.setParameter("pec", avv.getDescPec(), Hibernate.STRING);
				q.setParameter("indirizzoResidenza", avv.getDescIndirRes(), Hibernate.STRING);
				q.setParameter("capResidenza", avv.getDescCapRes(), Hibernate.STRING);
				q.setParameter("comuneResidenza", avv.getDescComuneRes(), Hibernate.STRING);
				q.setParameter("provinciaResidenza", avv.getDescProvRes(), Hibernate.STRING);
				q.setParameter("telResidenza", avv.getDescTelRes(), Hibernate.STRING);
				q.setParameter("faxResidenza", avv.getDescFaxRes(), Hibernate.STRING);
				q.setParameter("emailResidenza", avv.getDescEmailRes(), Hibernate.STRING);
				q.setParameter("indirizzoDomicilioLegale", avv.getDescIndirDomleg(), Hibernate.STRING);
				q.setParameter("capDomicilioLegale", avv.getDescCapDomleg(), Hibernate.STRING);
				q.setParameter("comuneDomicilioLegale", avv.getDescComuneDomleg(), Hibernate.STRING);
				q.setParameter("provinciaDomicilioLegale", avv.getDescProvDomleg(), Hibernate.STRING);
				q.setParameter("telDomicilioLegale", avv.getDescTelDomleg(), Hibernate.STRING);
				q.setParameter("faxDomicilioLegale", avv.getDescFaxDomleg(), Hibernate.STRING);
				q.setParameter("emailDomicilioLegale", avv.getDescEmailDomleg(), Hibernate.STRING);
				q.setParameter("dataNomina", dataNomina, Hibernate.DATE);
				q.setParameter("dataRevoca", dataRevoca, Hibernate.DATE);
				q.setParameter("tipoNomina", tipoNomina, Hibernate.STRING);
				q.setParameter("idReginde", avv.getDescIdReginde(), Hibernate.STRING);
				q.setParameter("codiceUtente", codiceUtente, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long id = (Long) first.get("IDAVVOCATO");
				return id;
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public Long inserisciModificaDifensoreIntestatario(final long idProc, final int idUffOWn, final long idSogInt, final Long idAvvocato,
			final TAvvocati avv, final Date dataNomina, final Date dataRevoca, final String tipoNomina, final String codiceUtente) {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_PROCEDIMENTO_insert_update_difensoreIntestatario");
				q.setParameter("idProc", idProc, Hibernate.LONG);
				q.setParameter("idUffOwn", idUffOWn, Hibernate.INTEGER);
				q.setParameter("idSoggetto", idSogInt, Hibernate.LONG);
				if (idAvvocato != null)
					q.setParameter("idAvvocato", idAvvocato);
				else
					q.setParameter("idAvvocato", null, Hibernate.LONG);
				q.setParameter("cognomeAvv", avv.getDescCognomeAvv(), Hibernate.STRING);
				q.setParameter("nomeAvv", avv.getDescNomeAvv(), Hibernate.STRING);
				q.setParameter("dataNascita", avv.getDataNascita(), Hibernate.DATE);
				q.setParameter("luogoNascita", avv.getDescLuogoNascita(), Hibernate.STRING);
				q.setParameter("provinciaNascita", avv.getDescProvNascita(), Hibernate.STRING);
				q.setParameter("codiceFiscale", avv.getDescCodiceFiscale(), Hibernate.STRING);
				q.setParameter("pec", avv.getDescPec(), Hibernate.STRING);
				q.setParameter("indirizzoResidenza", avv.getDescIndirRes(), Hibernate.STRING);
				q.setParameter("capResidenza", avv.getDescCapRes(), Hibernate.STRING);
				q.setParameter("comuneResidenza", avv.getDescComuneRes(), Hibernate.STRING);
				q.setParameter("provinciaResidenza", avv.getDescProvRes(), Hibernate.STRING);
				q.setParameter("telResidenza", avv.getDescTelRes(), Hibernate.STRING);
				q.setParameter("faxResidenza", avv.getDescFaxRes(), Hibernate.STRING);
				q.setParameter("emailResidenza", avv.getDescEmailRes(), Hibernate.STRING);
				q.setParameter("indirizzoDomicilioLegale", avv.getDescIndirDomleg(), Hibernate.STRING);
				q.setParameter("capDomicilioLegale", avv.getDescCapDomleg(), Hibernate.STRING);
				q.setParameter("comuneDomicilioLegale", avv.getDescComuneDomleg(), Hibernate.STRING);
				q.setParameter("provinciaDomicilioLegale", avv.getDescProvDomleg(), Hibernate.STRING);
				q.setParameter("telDomicilioLegale", avv.getDescTelDomleg(), Hibernate.STRING);
				q.setParameter("faxDomicilioLegale", avv.getDescFaxDomleg(), Hibernate.STRING);
				q.setParameter("emailDomicilioLegale", avv.getDescEmailDomleg(), Hibernate.STRING);
				q.setParameter("dataNomina", dataNomina, Hibernate.DATE);
				q.setParameter("dataRevoca", dataRevoca, Hibernate.DATE);
				q.setParameter("tipoNomina", tipoNomina, Hibernate.STRING);
				q.setParameter("idReginde", avv.getDescIdReginde(), Hibernate.STRING);
				q.setParameter("codiceUtente", codiceUtente, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long id = (Long) first.get("IDAVVOCATO");
				return id;
			}
		});
	}
	@SuppressWarnings("rawtypes")
	public Long inserisciProcedimento(final int idUffOwn, final String codUtente, final TProcedimento proc, final Date dataUltAggiornamento,
			final int idTipoFascicolo) {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Long idProc = null;
				Query q = session.getNamedQuery("PG_PROCEDIMENTO_inserisciProcedimento");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("descUtente", codUtente);
				// dataUltAggiornamento è null --> Viene impostata dal Trigger
				q.setParameter("dataAperFasc", dataUltAggiornamento, Hibernate.TIMESTAMP);
				if ((proc.getTTpStatoProc() != null) && (proc.getTTpStatoProc().getCodiKIdStatoProc() > 0))
					q.setParameter("idStatoProc", proc.getTTpStatoProc().getCodiKIdStatoProc(), Hibernate.INTEGER);
				else
					q.setParameter("idStatoProc", null, Hibernate.INTEGER);
				if ((proc.getTTpTipoProc() != null) && (proc.getTTpTipoProc().getCodiKIdTipoProc() > 0))
					q.setParameter("idTipoProc", proc.getTTpTipoProc().getCodiKIdTipoProc(), Hibernate.INTEGER);
				else
					q.setParameter("idTipoProc", null, Hibernate.INTEGER);
				if ((proc.getTAnProcedente() != null) && (proc.getTAnProcedente().getCodiKIdProced() > 0))
					q.setParameter("idProcedente", proc.getTAnProcedente().getCodiKIdProced(), Hibernate.INTEGER);
				else
					q.setParameter("idProcedente", null, Hibernate.INTEGER);
				if (idTipoFascicolo > 0)
					q.setParameter("idTipoFascicolo", idTipoFascicolo, Hibernate.INTEGER);
				else
					q.setParameter("idTipoFascicolo", null, Hibernate.INTEGER);
				q.setParameter("flgOrdinaria", proc.getFlgOrdinaria(), Hibernate.CHARACTER);
				q.setParameter("flgAntimafia", proc.getFlgAntimafia(), Hibernate.CHARACTER);
				q.setParameter("flgAntiterrorismo", proc.getFlgAntiterrorismo(), Hibernate.CHARACTER);
				// dataUltAggiornamento è null --> Viene impostata dal Trigger
				q.setParameter("dataUltAggiornamento", dataUltAggiornamento, Hibernate.TIMESTAMP);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List list = q.list();
				Iterator it = list.iterator();
				while (it.hasNext()) {
					Map<String, Object> row = (Map<String, Object>) it.next();
					idProc = (Long) row.get("IDPROC");
				}
				return idProc;
			}
		});
	}

	public List<TRelSogintErediConv> listaErediOppureConviventiDelSoggetto(long idSoggettoInteressato, long idProcedimento, int idUffOwn,
			String codiTipo) {

		List<TRelSogintErediConv> risultati = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelSogintErediConv.class);
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		criteria.add(Restrictions.eq("id.codiKIdSoggInt", idSoggettoInteressato));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUffOwn));
		criteria.add(Restrictions.eq("descTipo", codiTipo));
		criteria.addOrder(Order.asc("descCognome"));
		risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	@SuppressWarnings("rawtypes")
	public List listaMagistrati(final int idUffOwn, final String cognomeMag, final String nomeMag) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_PROCEDIMENTO_listaMagistrati");
				q.setParameter("vidUffOwn", idUffOwn);
				q.setParameter("vcognomeMag", cognomeMag);
				q.setParameter("vnomeMag", nomeMag);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				return q.list();
			}
		});
	}

	/**
	 * listaMagistratiPerProcedimento
	 * 
	 * @param idProcedimento
	 * @return List<Object[]>
	 */
	@SuppressWarnings("rawtypes")
	public List listaMagistratiPerProcedimento(final int idUffOwn, final long idProcedimento, final int ruoloMag) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				List<Object[]> lista = null;
				Query q = session.getNamedQuery("PG_PROCEDIMENTO_magistrati_per_proc");
				q.setParameter("vidUffOwn", idUffOwn);
				q.setParameter("vidprocedimento", idProcedimento);
				q.setParameter("ruoloMag", ruoloMag);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				lista = q.list();
				return lista;
			}
		});
	}

	public List<TRelBeneAtto> listaRelBeneAttoByAttoAndProc(long idProcedimento, long idAtto, int idUfficioUtente) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TRelBeneAtto.class);
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		criteria.add(Restrictions.eq("id.codiKIdAtto", idAtto));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente));
		List<TRelBeneAtto> risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	public List<TRelBeneAtto> listaRelBeneAttoByBene(long idBene, long idProcedimento, long idAtto, int idUfficioUtente) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TRelBeneAtto.class);
		criteria.add(Restrictions.eq("id.codiKIdBene", idBene));
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		criteria.add(Restrictions.eq("id.codiKIdAtto", idAtto));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente));
		criteria.add(Restrictions.ne("flgValido", new Character('N')));
		List<TRelBeneAtto> risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	public List<TRelBeneAtto> listaRelBeneAttoByBeneNoFrazionato(long idBene, long idProcedimento, long idAtto, int idUfficioUtente) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TRelBeneAtto.class);
		criteria.add(Restrictions.eq("id.codiKIdBene", idBene));
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		criteria.add(Restrictions.eq("id.codiKIdAtto", idAtto));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente));		
		criteria.add(Restrictions.eq("flgValido", new Character('S')));
		
		// Non deve essere un bene Disattivato - Sabino 31/07/2013
		Criterion crit1 = Restrictions.ne("TTpStatoBene.codiKIdTipoSb", 9);
		Criterion crit2 = Restrictions.isNull("TTpStatoBene.codiKIdTipoSb");
		
		criteria.add(Restrictions.or(crit1, crit2));
		List<TRelBeneAtto> risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	public List<TRelBeneAtto> listaRelBeneAttoByBeneNoFrazionatoConvalida(long idBene, long idProcedimento, long idAtto, int idUfficioUtente) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TRelBeneAtto.class);
		criteria.add(Restrictions.eq("id.codiKIdBene", idBene));
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		criteria.add(Restrictions.eq("id.codiKIdAtto", idAtto));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente));
		Criterion crit1 = Restrictions.ne("TTipoStatoBene.codiKIdTipoSb", 9l);
		Criterion crit2 = Restrictions.isNull("TTipoStatoBene.codiKIdTipoSb");
		Criterion crit3 = Restrictions.isNotNull("dataAssunzione");
		criteria.add(Restrictions.or(crit1, crit2));
		criteria.add(crit3);
		List<TRelBeneAtto> risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	public List<TRelAttoMisPatr> listaRelMisPatr(long idSoggetto, long idProcedimento, long idAtto, Integer idUfficioUtente) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TRelAttoMisPatr.class);
		criteria.add(Restrictions.eq("id.codiKIdSoggInt", idSoggetto));
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		criteria.add(Restrictions.eq("id.codiKIdAtto", idAtto));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente));
		criteria.add(Restrictions.ne("flgValido", new Character('N')));
		List<TRelAttoMisPatr> risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	public List<TRelAttoMisPatr> listaRelMisPatrForBene(long idBene, long idSoggetto, long idProcedimento, long idAtto, int idUfficioUtente) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TRelAttoMisPatr.class);
		criteria.add(Restrictions.eq("id.codiKIdBene", idBene));
		if (idSoggetto > 0)
			criteria.add(Restrictions.eq("id.codiKIdSoggInt", idSoggetto));
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		criteria.add(Restrictions.eq("id.codiKIdAtto", idAtto));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente));
		criteria.add(Restrictions.ne("flgValido", new Character('N')));
		List<TRelAttoMisPatr> risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	public List<TRelProcBene> listaRelProcBene(long idProcedimento, int idUfficioUtente) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TRelProcBene.class);
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente));
//		criteria.add(Restrictions.eq("flgValido", new Character('S')));
		
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente));
		Criterion crit1 = Restrictions.eq("flgValido", new Character('S'));
		
		Criterion crit2 = Restrictions.eq("flgValido", new Character('N'));
		Criterion crit2_1 = Restrictions.eq("TTpTipoMotivo.codiKIdMotivo", new Character('3'));
		Criterion crit3 = Restrictions.and(crit2, crit2_1);
		
		criteria.add(Restrictions.or(crit1, crit3));
		
		List<TRelProcBene> risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	public List<TRelMagUff> listaRelUffByUfficio(int idUfficio) {

		List<TRelMagUff> relazioni = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelMagUff.class);
		criteria.add(Restrictions.eq("id.codiKIdUfficio", idUfficio));
		relazioni = getHibernateTemplate().findByCriteria(criteria);
		return relazioni;
	}

	public List<TRelMagUff> listaRelUffByUfficio(int idUfficioUtente, List<TAnMagistrati> mag) {

		List<Long> idList = new ArrayList<Long>();
		Iterator<TAnMagistrati> iterator = mag.iterator();
		while (iterator.hasNext())
			idList.add(iterator.next().getCodiKIdMagistrato());
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelMagUff.class);
		criteria.add(Restrictions.eq("id.codiKIdUfficio", idUfficioUtente)).add(Restrictions.in("id.codiKIdMagistrato", idList));
		List<TRelMagUff> relUff = null;
		relUff = getHibernateTemplate().findByCriteria(criteria);
		return relUff;
	}

	public List<TRelAttoMisPatr> listaRelMisPatrForFiltro(long idBene, long idSoggettoInt, long idSoggettoTit, long idProcedimento, long idAtto, int idUfficioUtente) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TRelAttoMisPatr.class);
		criteria.add(Restrictions.eq("id.codiKIdBene", idBene));
		if (idSoggettoInt > 0)
			criteria.add(Restrictions.eq("id.codiKIdSoggInt", idSoggettoInt));
		if (idSoggettoTit > 0)
			criteria.add(Restrictions.eq("id.codiKIdSoggTit", idSoggettoTit));
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		criteria.add(Restrictions.eq("id.codiKIdAtto", idAtto));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente));
		criteria.add(Restrictions.ne("flgValido", new Character('N')));
		List<TRelAttoMisPatr> risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	/**
	 * listaSoggettiByCriteria
	 */
	@SuppressWarnings("rawtypes")
	public List listaSoggettiByCriteria(final int idUffOwn, final String nome, final String cognome, final String codiceStatoNascita,
			final String codiceComuneNascita, final String comuneNascitaEstero, final String dataNascita, final String codiceFiscale,
			final String codiceCui, final boolean escludiIntervenienti, final Integer idRuolo) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_SOGGETTO_listaPersonaFisica");
				q.setParameter("vidUffOwn", idUffOwn);
				q.setParameter("vnome", nome);
				q.setParameter("vcognome", cognome);
				q.setParameter("vstato", codiceStatoNascita);
				q.setParameter("vcomune", codiceComuneNascita);
				q.setParameter("vcomuneNascitaEstero", comuneNascitaEstero);
				q.setParameter("vdataNascita", dataNascita);
				q.setParameter("vcodiceFiscale", codiceFiscale);
				q.setParameter("vcodiceCui", codiceCui);
				
				if(escludiIntervenienti) q.setParameter("vescludiInterv", 1);
				else q.setParameter("vescludiInterv", null, Hibernate.INTEGER);
				
				if(idRuolo!=null) q.setParameter("vruolo", idRuolo);
				else q.setParameter("vruolo", null, Hibernate.INTEGER);
				
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List c = q.list();
				return c;
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public List listaSoggettiGiurByCriteria(final int idUffOwn, final String ragioneSociale, final String partitaIva, final Integer vidStatoSede,
			final Integer vidComuneSede, final String vstatoSedeEstero, final boolean escludiIntervenienti, final Integer idRuolo) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_SOGGETTO_listaPersonaGiur");
				q.setParameter("vidUffOwn", idUffOwn);
				q.setParameter("vragioneSociale", ragioneSociale, Hibernate.STRING);
				q.setParameter("vpartitaIva", partitaIva, Hibernate.STRING);
				q.setParameter("vidStatoSede", vidStatoSede, Hibernate.INTEGER);
				q.setParameter("vidComuneSede", vidComuneSede, Hibernate.INTEGER);
				q.setParameter("vstatoSedeEstero", vstatoSedeEstero, Hibernate.STRING);
				
				if(escludiIntervenienti) q.setParameter("vescludiInterv", 1);
				else q.setParameter("vescludiInterv", null, Hibernate.INTEGER);
				
				if(idRuolo!=null) q.setParameter("vruolo", idRuolo);	
				else q.setParameter("vruolo", null, Hibernate.INTEGER);
				
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List c = q.list();
				return c;
			}
		});
	}

	public void modificaDatiMagistratoSuProcedimento(Long idMagistrato, Date dataFineAssegnazione, String tipoSostituzione,
			Byte ordineDiVisualizzazione, String relatore, Date dataAssegnazione, String ruolo, String altroMotivo, long idProcedimento,
			Integer idUfficioUtente) {

		eliminaAssociazioneMagistratoProcedimento(idMagistrato, idProcedimento, idUfficioUtente);
		associaMagistratoAlProcedimento(idMagistrato, dataFineAssegnazione, tipoSostituzione, ordineDiVisualizzazione, relatore, dataAssegnazione,
				ruolo, altroMotivo, idProcedimento, idUfficioUtente);
	}

	public void modificaEredeOppureConviventeDelSoggetto(long idSoggettoInteressato, long idProcedimento, int idUffOwn, String codiceUtente,
			TRelSogintErediConv trel) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TRelSogintErediConv.class);
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		criteria.add(Restrictions.eq("id.codiKIdSoggInt", idSoggettoInteressato));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUffOwn));
		criteria.add(Restrictions.eq("id.codiKIdPrg", trel.getId().getCodiKIdPrg()));
		TRelSogintErediConv itemSelezionato = (TRelSogintErediConv) getHibernateTemplate().findByCriteria(criteria);
		itemSelezionato.setDescNome(trel.getDescNome());
		itemSelezionato.setDescCognome(trel.getDescCognome());
		itemSelezionato.setDataNascita(trel.getDataNascita());
		itemSelezionato.setDescParentela(trel.getDescParentela());
		itemSelezionato.setDescUtente(codiceUtente);
		TAnStato tstato = new TAnStato();
		TAnComuni tcomuni = new TAnComuni();
		if ((trel.getTAnStato() != null) && (trel.getTAnStato().getCodiKIdStato() != -1)) {
			tstato.setCodiKIdStato(trel.getTAnStato().getCodiKIdStato());
			if (trel.getTAnStato().getCodiKIdStato() == 1) {
				if ((trel.getTAnComuni() != null) && (trel.getTAnComuni().getCodiKIdComune() > 0)) {
					tcomuni.setCodiKIdComune(trel.getTAnComuni().getCodiKIdComune());
					itemSelezionato.setDescComEstero(null);
				} else {
					tcomuni = null;
					itemSelezionato.setDescComEstero(null);
				}
			} else if (trel.getDescComEstero() != null) {
				itemSelezionato.setDescComEstero(trel.getDescComEstero());
				tcomuni = null;
			} else
				tcomuni = null;
		} else
			tstato = null;
		itemSelezionato.setTAnStato(tstato);
		itemSelezionato.setTAnComuni(tcomuni);
		getHibernateTemplate().saveOrUpdate(itemSelezionato);
		getHibernateTemplate().flush();
		return;
	}

	public long modificaFonte(final long idUfficio, final TAtto atto, final String codiceUtente, final TIstanza istanza) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_modificaFonte");
				q.setParameter("idUffOwn", idUfficio, Hibernate.LONG);
				q.setParameter("idAtto", atto.getId().getCodiKIdAtto(), Hibernate.LONG);
				q.setParameter("codiceUtente", codiceUtente, Hibernate.STRING);
				q.setParameter("idFonte", atto.getTTpCatAtto().getCodiKIdCatAtto(), Hibernate.INTEGER);
				if (atto.getTTpCatAtto().getCodiKIdCatAtto() == Long.parseLong(Constants.FONTE_ALTRO)) {
					// la fonte è 'Altro'
					q.setParameter("altroOrgano", istanza.getDescAltroOrgano(), Hibernate.STRING);
					q.setParameter("idOrgano", null, Hibernate.INTEGER);
				} else {
					q.setParameter("altroOrgano", null, Hibernate.STRING);

					q.setParameter("idOrgano", atto.getTAnUfficio().getCodiKIdUfficio(), Hibernate.INTEGER);
				}
				if (istanza.getDescNumeRif() != null)
					q.setParameter("numeroRiferimento", istanza.getDescNumeRif(), Hibernate.STRING);
				else
					q.setParameter("numeroRiferimento", null, Hibernate.STRING);
				q.setParameter("dataInformativa", atto.getDataEmissione(), Hibernate.DATE);
				//System.out.println(atto.getDataEmissione());
				/*
				 */
				if (atto.getDescNote() != null)
					q.setParameter("note", atto.getDescNote(), Hibernate.STRING);
				else
					q.setParameter("note", null, Hibernate.STRING);
				if (atto.getDataRicezione() != null)
					q.setParameter("dataRicezione", atto.getDataRicezione(), Hibernate.DATE);
				else
					q.setParameter("dataRicezione", null, Hibernate.DATE);
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
				Long id = (Long) first.get("IDCURRENTATTO");
				return id;
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public void modificaGratuitoPatrocinioSoggProc(final long idProcedimento, final int idUfficioUtente, final long idSoggettoInteressato,
			final Short annoRegistroGP, final Long numeroRegistroGP, final Date dataIstanzaGP, final Date dataProvvedimentoGP,
			final Date dataRevocaGP,final String descNoteGp,final String idTenoreGP) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				TRelProcSogintId id = new TRelProcSogintId();
				id.setCodiKIdProc(idProcedimento);
				id.setCodiKIdUffOwner(idUfficioUtente);
				id.setCodiKIdSoggInt(idSoggettoInteressato);
				TRelProcSogint relazione = getHibernateTemplate().get(TRelProcSogint.class, id);
				relazione.setNumeAnnoRegGp(annoRegistroGP);
				relazione.setNumeRegistroGp(numeroRegistroGP);
				relazione.setDataIstanzaGp(dataIstanzaGP);
				relazione.setDataProvvGp(dataProvvedimentoGP);
				relazione.setDataRevocaGp(dataRevocaGP);
		        relazione.setDescNoteGp(descNoteGp);

				relazione.setFlgTenoreGp(idTenoreGP.charAt(0));

				session.saveOrUpdate(relazione);
				session.flush();
				return null;
			}
		});
	}

	public void cancellaGratuitoPatrocinioSoggProc(final long idProcedimento, final int idUfficioUtente, final long idSoggettoInteressato) {
		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				TRelProcSogintId id = new TRelProcSogintId();
				id.setCodiKIdProc(idProcedimento);
				id.setCodiKIdUffOwner(idUfficioUtente);
				id.setCodiKIdSoggInt(idSoggettoInteressato);
				TRelProcSogint relazione = getHibernateTemplate().get(TRelProcSogint.class, id);
				relazione.setNumeAnnoRegGp(null);
				relazione.setNumeRegistroGp(null);
				relazione.setDataIstanzaGp(null);
				relazione.setDataProvvGp(null);
				relazione.setDataRevocaGp(null);

				relazione.setFlgTenoreGp(null);

				session.saveOrUpdate(relazione);
				session.flush();
				return null;
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public void aggiornaTipoPrevenzioneNormativaSoggetto(final long idProcedimento, final Integer idUfficioUtente, final long idSoggettoInteressato,
			final long idTipoPrev, final long idTipoNorm) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				TRelProcSogintId id = new TRelProcSogintId();
				id.setCodiKIdProc(idProcedimento);
				id.setCodiKIdUffOwner(idUfficioUtente);
				id.setCodiKIdSoggInt(idSoggettoInteressato);
				TRelProcSogint relazione = getHibernateTemplate().get(TRelProcSogint.class, id);
				// TTpTipoPrevenzione tPrev = new TTpTipoPrevenzione();
				// tPrev.setCodiKIdTipoPrev((int)idTipoPrev);
				// relazione.getTTpTipoPrevProc().setTTpTipoPrevenzione(tPrev);
				TTpTipoPrevProc prevProc = new TTpTipoPrevProc();
				if(idTipoNorm>0)
				prevProc.setCodiKIdTipoNorm((int) idTipoNorm);
				else
				prevProc=null;	
				// relazione.getTTpTipoPrevProc().setCodiKIdTipoNorm((int)idTipoNorm);
				
				relazione.setTTpTipoPrevProc(prevProc);
				session.saveOrUpdate(relazione);
				session.flush();
				return null;
			}
		});
	}

	public long modificaUdienza(final TUdienza udienza) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_modificaUdienza");
				// chiave
				q.setParameter("idUffOwn", udienza.getId().getCodiKIdUffOwner(), Hibernate.INTEGER);
				q.setParameter("idUdienza", udienza.getId().getCodiKIdUdienza(), Hibernate.LONG);
				// parametri
				q.setParameter("dataUdienza", udienza.getDataUdienza(), Hibernate.DATE);
				q.setParameter("oraInizioUdienza", udienza.getOraInizioUdienza(), Hibernate.STRING);
				q.setParameter("indirizzoAula", udienza.getDescIndirAula(), Hibernate.STRING);
				q.setParameter("cognomeCanc", udienza.getDescCognomeAssGiu(), Hibernate.STRING);
				q.setParameter("nomeCanc", udienza.getDescNomeAssGiu(), Hibernate.STRING);
				q.setParameter("aula", udienza.getDescAula(), Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long id = (Long) first.get("IDCURRENTUDIENZA");
				return id;
			}
		});
	}

	public long salvaUdienza(final TUdienza udienza, final long idTipoAtto, final long idCategoriaAtto) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_salvaUdienza");
				q.setParameter("idUffOwn", udienza.getId().getCodiKIdUffOwner(), Hibernate.INTEGER);
				q.setParameter("idCodiceUtente", udienza.getDescUtente(), Hibernate.LONG);
				q.setParameter("idProcedimento", udienza.getTProcedimento().getCodiKIdProc(), Hibernate.LONG);
				q.setParameter("idTipoAtto", idTipoAtto, Hibernate.LONG);
				q.setParameter("idCatAtto", idCategoriaAtto, Hibernate.LONG);
				q.setParameter("dataUdienza", udienza.getDataUdienza(), Hibernate.DATE);
				q.setParameter("oraInizioUdienza", udienza.getOraInizioUdienza(), Hibernate.STRING);
				q.setParameter("indirizzoAula", udienza.getDescIndirAula(), Hibernate.STRING);
				q.setParameter("cognomeCanc", udienza.getDescCognomeAssGiu(), Hibernate.STRING);
				q.setParameter("nomeCanc", udienza.getDescNomeAssGiu(), Hibernate.STRING);
				q.setParameter("aula", udienza.getDescAula(), Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long id = (Long) first.get("IDCURRENTUDIENZA");
				return id;
			}
		});
	}

	public List<TRelAttoMisPers> misurePersonaliIscrizioneAggravamento(final Long idProcedimento, final Integer idUfficioUtente, final Long idSoggetto) {

		return (List<TRelAttoMisPers>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				List<TRelAttoMisPers> lista = new ArrayList<TRelAttoMisPers>();
				Query q = session.getNamedQuery("PG_PROCEDIMENTO_misurePersonali_iscrizione_aggravamento");
				q.setParameter("IDPROC", idProcedimento, Hibernate.LONG);
				q.setParameter("IDUFFOWN", idUfficioUtente, Hibernate.INTEGER);
				if (idSoggetto != null)
					q.setParameter("IDSOGGINT", idSoggetto, Hibernate.LONG);
				else
					q.setParameter("IDSOGGINT", null, Hibernate.LONG);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

				List result = q.list();
				if (result != null && result.size() > 0)
					for (int i = 0; i < result.size(); i++) {
						Map<String, Object> row = (Map<String, Object>) result.get(i);
						TRelAttoMisPers misPers = (TRelAttoMisPers) row.get("misPers");
						lista.add(misPers);
					}
				else
					return null;

				return lista;
			}
		});
	}

	public List<TRelProcProc> getProc1ByIdProc2(final long idProc2, final long idUffMitt, final long idUffOwn) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TRelProcProc.class);
		criteria.add(Restrictions.eq("id.codiKIdProc2", idProc2));
		criteria.add(Restrictions.eq("TAnUfficio.codiKIdUfficio", (new Long(idUffMitt)).intValue()));
		if (idUffOwn > 0)
			criteria.add(Restrictions.sqlRestriction(" TO_NUMBER(SUBSTR(TO_CHAR(CODI_K_ID_PROC1), 0, LENGTH(TO_CHAR(" + idUffOwn + ")))) = "
					+ idUffOwn));
		List<TRelProcProc> risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	public List<TRelProcProc> getProc1ByIdProc2AndTipoFasc(final long idProc2, final long idUffMitt, final long idTipoFasc) {

		String sql = "SELECT r.* " + "FROM T_REL_PROC_PROC r " + "INNER JOIN T_PROCEDIMENTO p " + "ON r.CODI_K_ID_PROC1 = p.CODI_K_ID_PROC "
				+ "WHERE r.CODI_K_ID_PROC2 = " + idProc2 + " " + "AND p.CODI_FK_ID_TIPO_FASC = " + idTipoFasc;

		Query query = getSession().createSQLQuery(sql).addEntity("r", TRelProcProc.class);
		return query.list();
	}

	public List<TRelProcProc> getProc1ByIdProc2(final long idProc2, final long idUffMitt) {

		return getProc1ByIdProc2(idProc2, idUffMitt, 0);
	}

	public TRelAttoProc getProcByIdAttoAndTipoFasc(final long idAtto, final long idUffOwn, final long idTipoFasc) {

		return (TRelAttoProc) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_PROCEDIMENTO_getProcByIdAttoIdTipoFasc");
				q.setParameter("IDATTO", idAtto, Hibernate.LONG);
				q.setParameter("IDUFFOWN", idUffOwn, Hibernate.LONG);
				q.setParameter("IDTIPOFASC", idTipoFasc, Hibernate.LONG);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

				List result = q.list();
				if (result != null && result.size() > 0) {
					Map<String, Object> row = (Map<String, Object>) result.get(0);
					TRelAttoProc attoProc = (TRelAttoProc) row.get("attoProc");
					return attoProc;
				} else
					return null;
			}
		});
	}

	public void eliminaTRelProcSogInt(long idSoggetto, long idProcedimento, int idUfficioUtente) {
		TRelProcSogintId id = new TRelProcSogintId(idUfficioUtente, idProcedimento, idSoggetto);
		TRelProcSogint relazione = getHibernateTemplate().get(TRelProcSogint.class, id);
		getHibernateTemplate().delete(relazione);
	}

	public void eliminaTRelProcSogIntEredi(long idSoggetto, long idProcedimento, int idUfficioUtente) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TRelSogintErediConv.class);
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		criteria.add(Restrictions.eq("id.codiKIdSoggInt", idSoggetto));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente));
		List<TRelSogintErediConv> eredi = getHibernateTemplate().findByCriteria(criteria);
		getHibernateTemplate().deleteAll(eredi);
	}

	public void eliminaTSoggettoInteressatoAndTPersona(long idSoggetto, int idUfficioUtente, long idVersione, String tipoSoggetto, long idProcedimento) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TRelProcSogint.class);
		criteria.add(Restrictions.eq("id.codiKIdSoggInt", idSoggetto));
		criteria.createAlias("TRelProcSogintVer", "tSoggIntVer");
		criteria.add(Restrictions.eq("tSoggIntVer.id.codiKIdVer", (short) idVersione));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente));
		List<TRelSogintErediConv> risultati = getHibernateTemplate().findByCriteria(criteria);

		// T_REL_PROC_SOGINT_VER
		criteria = DetachedCriteria.forClass(TRelProcSogintVer.class);
		criteria.add(Restrictions.eq("id.codiKIdSoggInt", idSoggetto));

		criteria.add(Restrictions.eq("id.codiKIdVer", (short) idVersione));
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente));
		risultati = getHibernateTemplate().findByCriteria(criteria);
		getHibernateTemplate().deleteAll(risultati);

		// se il soggetto non è collegato ad altro procedimento cancello anche
		// in T_SOGGETTO_INTERESSATO ed in T_PERSONA_X
		if (risultati == null || risultati.size() == 0) {

			// T_REL_PROC_SOGINT_VER
			criteria = DetachedCriteria.forClass(TRelProcSogintVer.class);
			criteria.add(Restrictions.eq("id.codiKIdSoggInt", idSoggetto));

			criteria.add(Restrictions.eq("id.codiKIdVer", (short) idVersione));
			criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente));
			risultati = getHibernateTemplate().findByCriteria(criteria);
			getHibernateTemplate().deleteAll(risultati);

			if (tipoSoggetto.equals(Constants.SOGGETTO_FISICO)) {
				// T_PERSONA_FISICA
				criteria = DetachedCriteria.forClass(TPersonaFisica.class);
				criteria.add(Restrictions.eq("id.codiKIdPersonaFis", idSoggetto));
				criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente));
				criteria.add(Restrictions.eq("id.codiKIdVer", (short) idVersione));
				risultati = getHibernateTemplate().findByCriteria(criteria);
				getHibernateTemplate().deleteAll(risultati);

			}
			if (tipoSoggetto.equals(Constants.SOGGETTO_GIURIDICO)) {
				criteria = DetachedCriteria.forClass(TPersonaGiur.class);
				criteria.add(Restrictions.eq("id.codiKIdPersGiur", idSoggetto));
				criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente));
				criteria.add(Restrictions.eq("id.codiKIdVer", (short) idVersione));
				risultati = getHibernateTemplate().findByCriteria(criteria);
				getHibernateTemplate().deleteAll(risultati);
			}
			// T_SOGGETTO
			criteria = DetachedCriteria.forClass(TSoggetto.class);
			criteria.add(Restrictions.eq("id.codiKIdSoggInt", idSoggetto));
			criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente));
			risultati = getHibernateTemplate().findByCriteria(criteria);
			getHibernateTemplate().deleteAll(risultati);
		}
	}
	
	//Segnalazione TMC0718
	public long getAttoFonteDaEliminare(long idSoggetto, int idUfficioUtente, long idProcedimento) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelProcSogint.class);
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		criteria.add(Restrictions.eq("id.codiKIdSoggInt", idSoggetto));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente));
		
		List<TRelProcSogint> soggettiList = getHibernateTemplate().findByCriteria(criteria);
		boolean deleteAtto = false;
		TAtto tAttoFonte = null;
		if(soggettiList!=null && !soggettiList.isEmpty())
		{
			for (TRelProcSogint tRelProcSogint : soggettiList) 
			{
				if(tRelProcSogint.getTAtto()!=null && tRelProcSogint.getTAtto().getTTpTipoAtto()!=null)
				if(tRelProcSogint.getTAtto().getTTpTipoAtto().getCodiKIdTipoAtto() == Integer.parseInt(Constants.TIPO_ATTO_FONTE))
				{
					tAttoFonte = tRelProcSogint.getTAtto();
					break;
				}
			}
		}
		
		if(tAttoFonte != null)
		{
			DetachedCriteria criteria2 = DetachedCriteria.forClass(TRelProcSogint.class);
			criteria2.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
			criteria2.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente));
			criteria2.add(Restrictions.eq("TAtto.id.codiKIdAtto", tAttoFonte.getId().getCodiKIdAtto()));
			List<TRelProcSogint> soggettiList2 = getHibernateTemplate().findByCriteria(criteria2);
			if(soggettiList2!=null && soggettiList2.size()>1)
				deleteAtto = false;
			else if(soggettiList2!=null && soggettiList2.size()==1)
				deleteAtto = true;
		}
		if(deleteAtto)
			return tAttoFonte.getId().getCodiKIdAtto();
		else
			return 0L;
	}
	
	public void eliminaAttoFonteSenzaSoggetto(int idUfficioUtente, long idProcedimento, long idAttoFonte) {
					//T_REL_ATTO_STATO_ATTO
					DetachedCriteria criteriaDelete1 = DetachedCriteria.forClass(TRelAttoStatoAtto.class);
					criteriaDelete1.add(Restrictions.eq("id.codiKIdAtto", idAttoFonte));
					criteriaDelete1.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente));
					List<TRelAttoStatoAtto> risultati1 = getHibernateTemplate().findByCriteria(criteriaDelete1);
					getHibernateTemplate().deleteAll(risultati1);
					//T_REL_ATTO_PROC
					DetachedCriteria criteriaDelete2 = DetachedCriteria.forClass(TRelAttoProc.class);
					criteriaDelete2.add(Restrictions.eq("id.codiKIdAtto", idAttoFonte));
					criteriaDelete2.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente));
					criteriaDelete2.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
					List<TRelAttoProc> risultati2 = getHibernateTemplate().findByCriteria(criteriaDelete2);
					getHibernateTemplate().deleteAll(risultati2);
					//T_REL_ITER_PROC
					DetachedCriteria criteriaDelete2b = DetachedCriteria.forClass(TRelIterProc.class);
					criteriaDelete2b.add(Restrictions.eq("TAtto.id.codiKIdAtto", idAttoFonte));
					criteriaDelete2b.add(Restrictions.eq("TAtto.id.codiKIdUffOwner", idUfficioUtente));
					criteriaDelete2b.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
					List<TRelAttoProc> risultati2b = getHibernateTemplate().findByCriteria(criteriaDelete2b);
					getHibernateTemplate().deleteAll(risultati2b);
					//T_ISTANZA
					DetachedCriteria criteriaDelete3 = DetachedCriteria.forClass(TIstanza.class);
					criteriaDelete3.add(Restrictions.eq("id.codiKIdIstanza", idAttoFonte));
					criteriaDelete3.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente));
					List<TIstanza> risultati3 = getHibernateTemplate().findByCriteria(criteriaDelete3);
					getHibernateTemplate().deleteAll(risultati3);
					//T_ATTO
					DetachedCriteria criteriaDelete4 = DetachedCriteria.forClass(TAtto.class);
					criteriaDelete4.add(Restrictions.eq("id.codiKIdAtto", idAttoFonte));
					criteriaDelete4.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente));
					List<TAtto> risultati4 = getHibernateTemplate().findByCriteria(criteriaDelete4);
					getHibernateTemplate().deleteAll(risultati4);
	}

	public Long getIdByAttoEUfficio(Long idAtto, Integer idUfficioUtente) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TRelAttoProc.class);
		criteria.add(Restrictions.eq("id.codiKIdAtto", idAtto));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente));
		List<TRelAttoProc> relazioni = getHibernateTemplate().findByCriteria(criteria);
		if (relazioni.isEmpty())
			return null;
		Long procPadre = null;
		for (TRelAttoProc tRelAttoProc : relazioni) {
			if (procPadre==null || procPadre>tRelAttoProc.getTProcedimento().getCodiKIdProc()) {
				procPadre =tRelAttoProc.getTProcedimento().getCodiKIdProc(); 
			}
		}
		return procPadre;
	}

	public List<TDefPatr> getTDefPatrById(long idProc, long idAtto, Integer idUfficioUtente, long idBene, long tipoInt, long sogTit,
			String tipoRicerca) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TDefPatr.class);
		criteria.add(Restrictions.eq("id.codiKIdProc", idProc));
		criteria.add(Restrictions.eq("id.codiKIdAtto", idAtto));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente));
		if (tipoInt != 0)
			criteria.add(Restrictions.eq("id.codiKIdTipoInt", (int) tipoInt));
		if (sogTit != 0)
			criteria.add(Restrictions.eq("id.codiKIdSoggTit", sogTit));
		if (idBene != 0)
			criteria.add(Restrictions.eq("id.codiKIdBene", idBene));
		if (tipoRicerca != null)
			if (tipoRicerca.equalsIgnoreCase("dataInvioNull"))
				criteria.add(Restrictions.isNull("dataInvio"));
			else if (tipoRicerca.equalsIgnoreCase("dataInvioNotNull"))
				criteria.add(Restrictions.isNotNull("dataInvio"));
		List<TDefPatr> risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	public List<TDefPers> getTDefPersById(long idProcedimento, long idMisura, long idAtto, int idUffOwn, String tipoRicerca) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TDefPers.class);
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));
		criteria.add(Restrictions.eq("id.codiKIdAtto", idAtto));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUffOwn));
		if (idMisura != 0)
			criteria.add(Restrictions.eq("id.codiKIdMisuraPers", idMisura));
		if (tipoRicerca != null)
			if (tipoRicerca.equalsIgnoreCase("dataInvioNull"))
				criteria.add(Restrictions.isNull("dataInvio"));
		List<TDefPers> risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	public TTpEsito getEsitoById(Integer idEsito) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TTpEsito.class);
		criteria.add(Restrictions.eq("codiKIdEsito", idEsito));
		List<TTpEsito> risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati.get(0);
	}

	public List listaSoggettiMisPersDefinitivita(final long idProcedimento, final long idAtto, final int idUffOwn) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				List lista = null;
				Query q = session.getNamedQuery("PG_ATTO_soggettiMisPersDefinitivita");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setParameter("idAtto", idAtto, Hibernate.LONG);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				lista = q.list();
				return lista;
			}
		});
	}

	public List listaSoggettiMisPersDefinitivitaFiltrati(final long idProcedimento, final long idAtto, final int idUffOwn, final long idSoggettoDiretto) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				List lista = null;
				Query q = session.getNamedQuery("PG_ATTO_soggettiMisPersDefinitivitaFiltrati");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setParameter("idAtto", idAtto, Hibernate.LONG);
				
				if (idSoggettoDiretto > 0 )
					q.setParameter("idSoggettoDiretto", idSoggettoDiretto, Hibernate.LONG);
				else
					q.setParameter("idSoggettoDiretto", null, Hibernate.LONG);
				
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				lista = q.list();
				return lista;
			}
		});
	}
	
	public List listaMisPersPerSoggettoDefinitivita(final long idSoggetto, final long idProcedimento, final long idAtto, final int idUffOwn) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				List lista = null;
				Query q = session.getNamedQuery("PG_ATTO_misPersPerSoggettoDefinitivita");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setParameter("idAtto", idAtto, Hibernate.LONG);
				q.setParameter("idSoggetto", idSoggetto, Hibernate.LONG);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				lista = q.list();
				return lista;
			}
		});
	}

	public List<TProcedimento> listaAccertamentiConSoggettiProposta(final int idUffOwn, final String idProced, final String listaIdSoggetti) {

		return (List<TProcedimento> )getHibernateTemplate().execute(new HibernateCallback() {

			public List<TProcedimento> doInHibernate(final Session session) throws HibernateException, SQLException {

				List lista = null;
				Query q = session.getNamedQuery("PG_PROCEDIMENTO_getAccertamentiSogg");
				q.setParameter("idUffOwn", idUffOwn);
				if (idProced != null && idProced.length() > 0)
					q.setParameter("idProced", Long.valueOf(idProced), Hibernate.LONG);
				else
					q.setParameter("idProced", null, Hibernate.LONG);
				q.setParameter("listaIdSoggetti", listaIdSoggetti, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				lista = q.list();
				return lista;
			}
		});
	}

	public Long inserisciTRelProcProc(final long idProc1, final long idProc2, final int idUffOwn, final String codUtente, final long idTipoAtto,
			final long idCatAtto, final String codiTipoRel) {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_PROCEDIMENTO_insTRelProcProc");
				q.setParameter("idProc1", idProc1, Hibernate.LONG);
				q.setParameter("idProc2", idProc2, Hibernate.LONG);
				q.setParameter("idUffOwn", idUffOwn, Hibernate.INTEGER);
				q.setParameter("descUtente", String.valueOf(codUtente), Hibernate.STRING);
				q.setParameter("idTipoAtto", idTipoAtto, Hibernate.LONG);
				q.setParameter("idCatAtto", idCatAtto, Hibernate.LONG);
				q.setParameter("codiTipoRel", codiTipoRel, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long id = (Long) first.get("IDPROC");
				return id;
			}
		});
	}

	public TPersonaFisica getPersonaFisicaByCampiChiave(final TPersonaFisica personaFisica, final int idUffOwn) {
		return (TPersonaFisica) getHibernateTemplate().execute(new HibernateCallback() {

			public TPersonaFisica doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_SOGGETTO_getSoggettoFisicoByCampiChiave");
				q.setParameter("idUffOwn", idUffOwn, Hibernate.INTEGER);
				q.setParameter("cognome", personaFisica.getDescCognome(), Hibernate.STRING);
				q.setParameter("nome", personaFisica.getDescNome(), Hibernate.STRING);
				// q.setParameter("dataNascita",
				// Utility.stringToDate(Utility.formatDate(personaFisica.getDataNascita()),"dd/MM/yyyy"),
				// Hibernate.DATE);
				q.setParameter("dataNascita", Utility.formatDate(personaFisica.getDataNascita()), Hibernate.STRING);
				if (personaFisica.getTAnStatoByCodiFkIdStatoNasc()!=null)
					q.setParameter("idStatoNascita", personaFisica.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato(), Hibernate.INTEGER);
				else 
					q.setParameter("idStatoNascita", null, Hibernate.INTEGER);
				if (personaFisica.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune() > 0)
					q.setParameter("idComuneNascita", personaFisica.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune(), Hibernate.INTEGER);
				else
					q.setParameter("idComuneNascita", null, Hibernate.INTEGER);
				q.setParameter("descComuneEsteroNascita", personaFisica.getDescComEsteroNasc(), Hibernate.STRING);
				q.setParameter("codiceFiscale", personaFisica.getDescCodiceFiscale(), Hibernate.STRING);
				q.setParameter("codiceCui", personaFisica.getDescCui(), Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();

				if (l.size() > 0) {
					HashMap first = (HashMap) l.get(0);
					TPersonaFisica persona = (TPersonaFisica) first.get("pers");
					return persona;
				}
				return null;
			}
		});
	}

	public TPersonaGiur getPersonaGiuridicaByCampiChiave(final TPersonaGiur personaGiuridica, final int idUffOwn) {
		return (TPersonaGiur) getHibernateTemplate().execute(new HibernateCallback() {

			public TPersonaGiur doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_SOGGETTO_getSoggettoGiuridicoByCampiChiave");
				q.setParameter("idUffOwn", idUffOwn, Hibernate.INTEGER);
				q.setParameter("tipoSocieta", personaGiuridica.getTTpTipoSocieta().getCodiKIdTipoSoc(), Hibernate.INTEGER);
				q.setParameter("ragioneSociale", personaGiuridica.getDescRagioneSoc(), Hibernate.STRING);
				q.setParameter("idStatoSede", personaGiuridica.getTAnStato().getCodiKIdStato(), Hibernate.INTEGER);
				q.setParameter("idComuneSede", personaGiuridica.getTAnComuni().getCodiKIdComune(), Hibernate.INTEGER);
				q.setParameter("comuneSedeEstero", personaGiuridica.getDescComEsteroSede(), Hibernate.STRING);
				q.setParameter("partitaIva", personaGiuridica.getDescPartitaIva(), Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();

				if (l.size() > 0) {
					HashMap first = (HashMap) l.get(0);
					TPersonaGiur persona = (TPersonaGiur) first.get("pers");
					return persona;
				}
				return null;
			}
		});
	}

	public List getDecretoFissUdienzaProcedimento(final long idProcedimento, final long idUfficio) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				List lista = null;
				Query q = session.getNamedQuery("PG_ATTO_esisteDecretoFissazioneUdienza");
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setParameter("idUffOwn", idUfficio, Hibernate.LONG);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				lista = q.list();
				return lista;
			}
		});
	}

	public List<TRelAttoStatoAtto> verificaStatoAttoRegistrato(long codAtto, long statoAtto, int idUfficioUtente) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TRelAttoStatoAtto.class);
		criteria.add(Restrictions.eq("id.codiKIdAtto", codAtto));
		criteria.add(Restrictions.eq("id.codiKIdStatoAtto", (int) statoAtto));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUfficioUtente));
		List<TRelAttoStatoAtto> risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;

	}

	public TAnNormative getNormativaById(long idNormativa) {

		return getHibernateTemplate().get(TAnNormative.class, (int) idNormativa);
	}

	@SuppressWarnings("rawtypes")
	public void aggiornaFlgTipoPrevenzioneProcedimento(final TProcedimento procedimento, final Character flagOrdinaria,
			final Character flagAntimafia, final Character flagAntiterrorismo) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				procedimento.setFlgAntimafia(flagAntimafia);
				procedimento.setFlgOrdinaria(flagOrdinaria);
				procedimento.setFlgAntiterrorismo(flagAntiterrorismo);
				session.saveOrUpdate(procedimento);
				session.flush();
				return null;
			}
		});

	}

	// INIZIO GESTIONE DOCUMENTALE
	// UPDATE T_PROCEDIMENTO
	// SET
	// DESC_FASCICOLO_PDOC = vCodiceFascicoloPDoc
	// WHERE CODI_K_ID_PROC = vIdProcedimento;
	public boolean inserisciFascicoloPDoc(final Long idProcedimento, final String codiceFascicolo) {
		return (Boolean)getHibernateTemplate().execute(new HibernateCallback() {
			public Boolean doInHibernate(final Session session) throws HibernateException, SQLException {
				boolean result = false;
				try {
					//GC 15/07/2020: Cambio del messaggi di log
					//System.out.println("    1X) - Inserisce FascicoloPDoc " );
					System.out.println("    	1.1.1) Aggiornamento del FascicoloPDoc " +  codiceFascicolo + " su SMP");
					Query q = session.getNamedQuery("PG_PROCEDIMENTO_inserisciFascicoloPDoc");
					q.setParameter("vIdProcedimento", idProcedimento, Hibernate.LONG);
					q.setParameter("vCodiceFascicoloPDoc", codiceFascicolo, Hibernate.STRING);
					q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
					List l = q.list();
					HashMap first = (HashMap) l.get(0);
					Long idProcResult = (Long) first.get("ID_PROC");
					if (idProcResult != null && idProcResult.longValue() > 0)
						result = true;

				} catch (Exception e) {
					result = false;
				} finally {
					return result;
				}
			}
		});
	}

	// UPDATE T_REL_ATTO_PROC
	// SET
	// FLG_ASSOCIA_PDOC = 'S'
	// WHERE CODI_K_ID_UFF_OWNER = vCodiKIdUffOwn
	// AND CODI_K_ID_ATTO = vCodiKIdAtto
	// AND CODI_K_ID_PROC = vIdProcedimento;
	public boolean associaDocFascicoloPDoc(final Long idProcedimento, final Long codiKIdAtto, final int codiKIdUffOwn) {
		return (Boolean)getHibernateTemplate().execute(new HibernateCallback() {
			public Boolean doInHibernate(final Session session) throws HibernateException, SQLException {
				boolean result = false;
				try {
					System.out.println("    4.1) - AssociaDoc FascicoloPDoc " );
					Query q = session.getNamedQuery("PG_PROCEDIMENTO_associaDocFascicoloPDoc");
					q.setParameter("vIdProcedimento", idProcedimento, Hibernate.LONG);
					q.setParameter("vIdDocumento", codiKIdAtto, Hibernate.LONG);
					q.setParameter("vCodiKIdUffOwn", codiKIdUffOwn, Hibernate.INTEGER);
					q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
					List l = q.list();
					HashMap first = (HashMap) l.get(0);
					Long idProcResult = (Long) first.get("ID_PROC");
					if (idProcResult != null && idProcResult.longValue() > 0)
						result = true;

				} catch (Exception e) {
					result = false;
				} finally {
					return result;
				}
			}
		});
	}

	public String ricercaFascicoloPDoc(Long idProcedimento) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TProcedimento.class);
		criteria.add(Restrictions.naturalId().set("codiKIdProc", idProcedimento));
		TProcedimento p = null;
		String fascicoloPDoc = null;
		try {
			List<TProcedimento> res = getHibernateTemplate().findByCriteria(criteria);
			p = DataAccessUtils.requiredSingleResult(res);
			fascicoloPDoc = p.getDescFascicoloPdoc();
		} catch (Exception e) {
			return null;
		}
		return fascicoloPDoc;
	}

	// FINE GESTIONE DOCUMENTALE

	public List<TRelProcProc> getTRelProcProcByProc1(final long idProc1, final Character flgTipoRel, final String descUtente, final String nomeMetodo) {

		// DetachedCriteria criteria =
		// DetachedCriteria.forClass(TRelProcProc.class);
		// criteria.add(Restrictions.eq("id.codiKIdProc1", id));
		// if(flgTipoRel != null){
		// criteria.add(Restrictions.eq("flgTipoRel", flgTipoRel));
		// }
		// List<TRelProcProc> risultati =
		// getHibernateTemplate().findByCriteria(criteria);
		// return risultati;

		return (List<TRelProcProc>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_ATTO_listaRelProcProcByProc1");
				if (idProc1 > 0)
					q.setParameter("idProc1", idProc1, Hibernate.LONG);
				else
					q.setParameter("idProc1", null, Hibernate.LONG);
				if (flgTipoRel != null)
					q.setParameter("flagValido", flgTipoRel, Hibernate.CHARACTER);
				else
					q.setParameter("flagValido", null, Hibernate.CHARACTER);

				q.setParameter("descUtente", descUtente, Hibernate.STRING);
				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});

	}

	public List<TRelProcProc> getTRelProcProcByProc2(final long id, Character flgTipoRel) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TRelProcProc.class);
		criteria.add(Restrictions.eq("id.codiKIdProc2", id));
		if (flgTipoRel != null)
			criteria.add(Restrictions.eq("flgTipoRel", flgTipoRel));
		List<TRelProcProc> risultati = getHibernateTemplate().findByCriteria(criteria);
		return risultati;
	}

	// lista dei soggetti legati a un procedimento
	public List<TRelProcSogint> getSogIntPerProcedimento(Long idProcedimento, int idUffOwn) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TRelProcSogint.class);
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento.longValue()));
		criteria.add(Restrictions.eq("id.codiKIdUffOwner", idUffOwn));
		List<TRelProcSogint> risultato = null;
		risultato = getHibernateTemplate().findByCriteria(criteria);
		if (risultato.size() > 0)
			return risultato;
		return null;
	}

	public List<TRelIterProc> verificaTRelIterProc(final long idProcedimento, final int idUfficioUtente) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TRelIterProc.class);
		criteria.add(Restrictions.eq("TAnUfficio.codiKIdUfficio", idUfficioUtente));
		criteria.add(Restrictions.eq("id.codiKIdProc", idProcedimento));

		List<TRelIterProc> relazioni = getHibernateTemplate().findByCriteria(criteria);
		return relazioni;
	}

	public List getListaProcedimentiRelProcProc(final long idProcedimento, final Integer idUffOwn, final Integer idUffMitt, final String funzione) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_PROCEDIMENTO_listaProcedimentiRelProcProc");
				if (idUffMitt== null) q.setParameter("IDUFFMITT", null, Hibernate.INTEGER);
				else q.setParameter("IDUFFMITT", idUffMitt, Hibernate.INTEGER);
				 q.setParameter("IDUFFOWN", idUffOwn, Hibernate.INTEGER);
				q.setParameter("IDPROC", idProcedimento, Hibernate.LONG);
				if (funzione== null) {
					q.setParameter("FUNZIONE", null, Hibernate.STRING);
				}
				else {
					q.setParameter("FUNZIONE", funzione, Hibernate.STRING);
				}

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

				return q.list();
			}
		});
	}

	
	public List getListaProcedimentiCdaTrib(final long idProcedimento, final Integer idUffOwn) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_PROCEDIMENTO_listaCdaTrib");
				
				 q.setParameter("IDUFFOWN", idUffOwn, Hibernate.INTEGER);
				 q.setParameter("IDPROC", idProcedimento, Hibernate.LONG);
			

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

				return q.list();
			}
		});
	}
	
	
	public List getListaProcedimentiTribCda(final long idProcedimento, final Integer idUffOwn) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_PROCEDIMENTO_listaTribCda");
				
				 q.setParameter("IDUFFOWN", idUffOwn, Hibernate.INTEGER);
				 q.setParameter("IDPROC", idProcedimento, Hibernate.LONG);
			

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

				return q.list();
			}
		});
	}	
	
	
	public List listaSoggettiMisPersRevocaDefinitivita(final long idProcedimento, final long idAtto, final int idUffOwn, final String codUtente,
			final String tipoFunzione, final String nomeMetodo) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				List lista = null;
				Query q = session.getNamedQuery("PG_ATTO_revocaDefSelMisPers");
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setParameter("idAtto", idAtto, Hibernate.LONG);
				q.setParameter("idUfficio", idUffOwn);
				q.setParameter("idUtente", codUtente, Hibernate.STRING);
				q.setParameter("tipoFunzione", tipoFunzione, Hibernate.STRING);
				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				lista = q.list();
				return lista;
			}
		});
	}

	public List listaSoggettiMisPersRevocaDefinitivitaFiltrati(final long idProcedimento, final long idAtto, final int idUffOwn, final String codUtente,
			final String tipoFunzione, final String nomeMetodo, final long idSoggettoDiretto) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				List lista = null;
				Query q = session.getNamedQuery("PG_ATTO_revocaDefSelMisPersFiltrati");
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setParameter("idAtto", idAtto, Hibernate.LONG);
				q.setParameter("idUfficio", idUffOwn);
				q.setParameter("idUtente", codUtente, Hibernate.STRING);
				q.setParameter("tipoFunzione", tipoFunzione, Hibernate.STRING);
				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
				
				if (idSoggettoDiretto != 0 && idSoggettoDiretto != -1)
					q.setParameter("idSoggettoDiretto", idSoggettoDiretto, Hibernate.LONG);
				else
					q.setParameter("idSoggettoDiretto", null, Hibernate.LONG);
				
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				lista = q.list();
				return lista;
			}
		});
	}
	
	public List listaSoggettiMisPatrRevocaDefinitivita(final long idProcedimento, final long idAtto, final int idUffOwn, final String codUtente,
			final String tipoFunzione, final String nomeMetodo) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				List lista = null;
				Query q = session.getNamedQuery("PG_ATTO_revocaDefSelMisPatr");
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setParameter("idAtto", idAtto, Hibernate.LONG);
				q.setParameter("idUfficio", idUffOwn);
				q.setParameter("idUtente", codUtente, Hibernate.STRING);
				q.setParameter("tipoFunzione", tipoFunzione, Hibernate.STRING);
				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);				
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				lista = q.list();
				return lista;
			}
		});
	}

	public List listaSoggettiMisPatrRevocaDefinitivitaFiltrati(final long idProcedimento, final long idAtto, final int idUffOwn, final String codUtente,
			final String tipoFunzione, final String nomeMetodo,final long idSoggettoDiretto, final long idSoggettoTitolare, final long idTipologia, final long idCategoria,final long idSottocategoria) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				List lista = null;
				Query q = session.getNamedQuery("PG_ATTO_revocaDefSelMisPatrFiltrati");
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setParameter("idAtto", idAtto, Hibernate.LONG);
				q.setParameter("idUfficio", idUffOwn);
				q.setParameter("idUtente", codUtente, Hibernate.STRING);
				q.setParameter("tipoFunzione", tipoFunzione, Hibernate.STRING);
				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
								
				if (idSoggettoDiretto != 0 && idSoggettoDiretto != -1)
					q.setParameter("idSoggettoDiretto", idSoggettoDiretto, Hibernate.LONG);
				else
					q.setParameter("idSoggettoDiretto", null, Hibernate.LONG);					

				if (idSoggettoTitolare !=0 && idSoggettoTitolare != -1)				
					q.setParameter("idSoggettoTitolare", idSoggettoTitolare, Hibernate.LONG);
				else
					q.setParameter("idSoggettoTitolare", null, Hibernate.LONG);
				
				if (idTipologia != 0 && idTipologia != -1)
					q.setParameter("idTipologia", idTipologia, Hibernate.LONG);
				else
					q.setParameter("idTipologia", null, Hibernate.LONG);
				
				if (idCategoria !=0 && idCategoria != -1)
					q.setParameter("idCategoria", idCategoria, Hibernate.LONG);
				else
					q.setParameter("idCategoria", null, Hibernate.LONG);
				
				if (idSottocategoria !=0 && idSottocategoria != -1)
					q.setParameter("idSottocategoria", idSottocategoria, Hibernate.LONG);
				else
					q.setParameter("idSottocategoria", null, Hibernate.LONG);
				
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				lista = q.list();
				return lista;
			}
		});
	}
	
	public List listaSoggettiMisPatrDefinitivita(final long idProcedimento, final long idAtto, final int idUffOwn, final String codUtente,
			final String tipoFunzione, final String nomeMetodo) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				List lista = null;
				Query q = session.getNamedQuery("PG_ATTO_defSelMisPatr");
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setParameter("idAtto", idAtto, Hibernate.LONG);
				q.setParameter("idUfficio", idUffOwn);
				q.setParameter("idUtente", codUtente, Hibernate.STRING);
				q.setParameter("tipoFunzione", tipoFunzione, Hibernate.STRING);
				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);				
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				lista = q.list();
				return lista;
			}
		});
	}

	public List listaSoggettiMisPatrDefinitivitaFiltrati(final long idProcedimento, final long idAtto, final int idUffOwn, final String codUtente,
			final String tipoFunzione, final String nomeMetodo,final long idSoggettoDiretto, final long idSoggettoTitolare, final long idTipologia, final long idCategoria,final long idSottocategoria) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				List lista = null;
				Query q = session.getNamedQuery("PG_ATTO_defSelMisPatrFiltrati");
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setParameter("idAtto", idAtto, Hibernate.LONG);
				q.setParameter("idUfficio", idUffOwn);
				q.setParameter("idUtente", codUtente, Hibernate.STRING);
				q.setParameter("tipoFunzione", tipoFunzione, Hibernate.STRING);
				q.setParameter("nomeMetodo", nomeMetodo, Hibernate.STRING);
								
				if (idSoggettoDiretto != 0 && idSoggettoDiretto != -1)
					q.setParameter("idSoggettoDiretto", idSoggettoDiretto, Hibernate.LONG);
				else
					q.setParameter("idSoggettoDiretto", null, Hibernate.LONG);					

				if (idSoggettoTitolare !=0 && idSoggettoTitolare != -1)				
					q.setParameter("idSoggettoTitolare", idSoggettoTitolare, Hibernate.LONG);
				else
					q.setParameter("idSoggettoTitolare", null, Hibernate.LONG);
				
				if (idTipologia != 0 && idTipologia != -1)
					q.setParameter("idTipologia", idTipologia, Hibernate.LONG);
				else
					q.setParameter("idTipologia", null, Hibernate.LONG);
				
				if (idCategoria !=0 && idCategoria != -1)
					q.setParameter("idCategoria", idCategoria, Hibernate.LONG);
				else
					q.setParameter("idCategoria", null, Hibernate.LONG);
				
				if (idSottocategoria !=0 && idSottocategoria != -1)
					q.setParameter("idSottocategoria", idSottocategoria, Hibernate.LONG);
				else
					q.setParameter("idSottocategoria", null, Hibernate.LONG);
				
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				lista = q.list();
				return lista;
			}
		});
	}
	
	public List listaMisPersPerSoggettoRevocaDefinitivita(final long idSoggetto, final long idProcedimento, final long idAtto, final int idUffOwn,final String tipoFunzione) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				List lista = null;
				Query q = session.getNamedQuery("PG_ATTO_misPersPerSoggettoRevocaDefinitivita");
				q.setParameter("idUffOwn", idUffOwn);
				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setParameter("idAtto", idAtto, Hibernate.LONG);
				q.setParameter("idSoggetto", idSoggetto, Hibernate.LONG);
				q.setParameter("tipoFunzione", tipoFunzione, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				lista = q.list();
				return lista;
			}
		});
	}

	public Map<Long, String> getProcedimentiJobDocumentale() {

		return (Map<Long, String>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_PROCEDIMENTO_getProcedimentiJobDocumentale");
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				Map<Long, String> map = new HashMap<Long, String>();
				if ((l != null) && (l.size() > 0)) {
					for (int i = 0; i < l.size(); i++) {
						HashMap element = (HashMap) l.get(i);
						BigDecimal tmp = (BigDecimal) element.get("ID_PROC");
						Long idProc = tmp.longValue();
						String idPDoc = (String) element.get("ID_PDOC");
						map.put(idProc, idPDoc);
					}
					return map;
				}
				return null;
			}
		});
	}
	
	// test gestione fascicoli con chiamata a procedura di verifica 14 maggio 2018

	public Map<Long, String> getTestProcedimentiJobDocumentale() {

		return (Map<Long, String>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("TEST_DOCUMENTALE_getTestProcedimentiJobDocumentale");
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				Map<Long, String> map = new HashMap<Long, String>();
				if ((l != null) && (l.size() > 0)) {
					for (int i = 0; i < l.size(); i++) {
						HashMap element = (HashMap) l.get(i);
						BigDecimal tmp = (BigDecimal) element.get("ID_PROC");
						Long idProc = tmp.longValue();
						String testChiamata = (String) element.get("TEST_CHIAMATA");
						map.put(idProc, testChiamata);
					}
					return map;
				}
				return null;
			}
		});
	}
	
	// fine test gestione fascicoli con chiamata a procedura di verifica 14 maggio 2018

	public Long copiaAttoAccertamentoInProcMerito(final long idProcAccertamento, final long idProcMerito, final int idUffOwn, final String codUtente) {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_PROCEDIMENTO_copiaAttoAccertamentoInProcMerito");
				q.setParameter("IDPROCACCERT", idProcAccertamento, Hibernate.LONG);
				q.setParameter("IDPROCMERITO", idProcMerito, Hibernate.LONG);
				q.setParameter("IDUFFOWN", idUffOwn, Hibernate.INTEGER);
				q.setParameter("CODUTENTE", codUtente, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long id = (Long) first.get("IDPROC");
				return id;
			}
		});
	}

	public Long sostituisciGiudiceDelegato(final int idUffOwn, final long idMagistratoOut, final long idMagistratoIn, final long idProcedimento, final String tipoSostituzione, final String codUtente) {

		return (Long)getHibernateTemplate().execute(new HibernateCallback() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_PROCEDIMENTO_sostituisciGiudiceDelegato");
				
				q.setParameter("IDUFFOWN", idUffOwn);
				q.setParameter("VIDMAGISTRATOOUT", idMagistratoOut, Hibernate.LONG);
				q.setParameter("VIDMAGISTRATOIN", idMagistratoIn, Hibernate.LONG);
				q.setParameter("VIDPROCEDIMENTO", idProcedimento, Hibernate.LONG);
				q.setParameter("TIPOSOSTITUZIONE", tipoSostituzione, Hibernate.STRING);
				q.setParameter("DESCUTENTE", codUtente, Hibernate.STRING);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				HashMap first = (HashMap) l.get(0);
				Long id = (Long) first.get("ID_PROC");
				return id;
			}
		});
	}
	
	public TTpTipoPrevProc getNormativaByTRelProcSogintId(TRelProcSogintId id) {
		TRelProcSogint procSogint = getHibernateTemplate().load(TRelProcSogint.class, id);
		return procSogint.getTTpTipoPrevProc();
	}
	
	
	public List<TRelProcSogint> getListaSoggettiFisiciProcedimento(final Long idProcedimento,final  Integer idUffOwner) {
		
		List<TRelProcSogint> result = getHibernateTemplate().findByCriteria(
				DetachedCriteria.forClass(TRelProcSogint.class)
				.add(Restrictions.eq("flgValido", Character.valueOf('S')))
				.add(Restrictions.eq("id.codiKIdProc", idProcedimento))
				.add(Restrictions.eq("id.codiKIdUffOwner", idUffOwner))
		);
		return result;
	}
	
	public boolean hasAttiInLavorazioni(long idProcedimento, long idUffOwner) {
		List<TRelAttoProc> result = getHibernateTemplate().findByCriteria(
				DetachedCriteria.forClass(TRelAttoProc.class)
				.add(Restrictions.eq("id.codiKIdProc", idProcedimento))
				.add(Restrictions.eq("id.codiKIdUffOwner", (int)idUffOwner))
		);
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:SS");
			for (TRelAttoProc tRelAttoProc : result) {
				Date maxDate = sdf.parse("01-01-1900");
				TTpStatoAtto lastStatoAtto = null;
				for (TRelAttoStatoAtto relAttoStatoAtto : tRelAttoProc.getTAtto().getTRelAttoStatoAttos())
					if (maxDate.before(relAttoStatoAtto.getDataAssStato())) {
						lastStatoAtto = relAttoStatoAtto.getTTpStatoAtto();
						maxDate = relAttoStatoAtto.getDataAssStato();
					}
				if (lastStatoAtto!=null 
					&& lastStatoAtto.getCodiKIdStatoAtto() == Constants.STATOATTO_IN_LAVORAZIONE
					&& !Constants.TIPO_ATTO_PROPOSTA.equals(tRelAttoProc.getTAtto().getTTpTipoAtto().getCodiKIdTipoAtto()+""))
					// Se l'atto è in lavorazione ed è diverso dalla proposta
					return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public TAnManuali getTAnManualiById(long id) {
		List<TAnManuali> result = getHibernateTemplate().findByCriteria(
				DetachedCriteria.forClass(TAnManuali.class)
				.add(Restrictions.eq("TTpTipoUfficio.codiKIdTipoUff", Integer.valueOf((int)id)))
		);
		return ((result != null) && (!result.isEmpty())) ? result.get(0) : new TAnManuali();
	}
	
	@SuppressWarnings("rawtypes")
	public List<RicercaProcedimentoDto> getListaProcedimentiSogg(final int idUffOwn, final Long tipoFascicolo, final Long idSogg) {

		return (List<RicercaProcedimentoDto>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_PROCEDIMENTO_listaProcedimenti_Sogg");
				q.setParameter("IDUFFOWN", idUffOwn, Hibernate.INTEGER);
				
				if(tipoFascicolo!=null)
					q.setParameter("TIPOFASCICOLO", tipoFascicolo, Hibernate.LONG);
				else
					q.setParameter("TIPOFASCICOLO", null, Hibernate.LONG);
				q.setParameter("IDSOGG", idSogg, Hibernate.LONG);
				
				q.setResultTransformer(Transformers.aliasToBean(RicercaProcedimentoDto.class));

				List<RicercaProcedimentoDto> result = q.list();
				
				return result; 
			}
		});
	}	
	
	@SuppressWarnings("rawtypes")
	public List<RicercaProcedimentoDto> getListaProcedimentiSoggMultiFascicolo(final int idUffOwn, final String tipiFascicolo, final Long idSogg) {

		return (List<RicercaProcedimentoDto>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_PROCEDIMENTO_listaProcedimenti_Sogg_multiFascicolo");
				q.setParameter("IDUFFOWN", idUffOwn, Hibernate.INTEGER);
				
				if(tipiFascicolo!=null)
					q.setParameter("TIPIFASCICOLO", tipiFascicolo, Hibernate.STRING);
				else
					q.setParameter("TIPIFASCICOLO", null, Hibernate.STRING);
				q.setParameter("IDSOGG", idSogg, Hibernate.LONG);
				
				q.setResultTransformer(Transformers.aliasToBean(RicercaProcedimentoDto.class));

				List<RicercaProcedimentoDto> result = q.list();
				
				return result; 
			}
		});
	}		
	
	public List<MisurePatrSoggDto> misurePatrimonialiSoggetto(final int idUffOwn, final Long idProc, final Long idSoggInt, final Long idSoggTit) {

		return (List<MisurePatrSoggDto>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				new AssociaPersonaDto();
				Query q = session.getNamedQuery("PG_PROCEDIMENTO_misurePatr_Sogg");
				q.setParameter("IDUFFOWN", idUffOwn);
				q.setParameter("IDPROC", idProc, Hibernate.LONG);
				
				if(idSoggInt!=null)
					q.setParameter("IDSOGGINT", idSoggInt, Hibernate.LONG);
				else
					q.setParameter("IDSOGGINT", null, Hibernate.LONG);
				if(idSoggTit!=null)
					q.setParameter("IDSOGGTIT", idSoggTit, Hibernate.LONG);
				else
					q.setParameter("IDSOGGTIT", null, Hibernate.LONG);
				q.setResultTransformer(Transformers.aliasToBean(MisurePatrSoggDto.class));
				return q.list();
			}
		});
	}
	
	public List listaDifensoriNoReginde(final String cognome, final long idSogg, final long idProcedimento, final int idUffOwn) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_PROCEDIMENTO_listaDifensoriNonAssociati");
				q.setParameter("COGNOME", cognome);
				q.setParameter("IDSOGG", idSogg);
				q.setParameter("IDPROC", idProcedimento);
				q.setParameter("IDUFFOWN", idUffOwn);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				return l;
			}
		});
	}

	public TAvvocati dettaglioDifensore(long idDifensore) {
		List<TAvvocati> result = getHibernateTemplate().findByCriteria(
				DetachedCriteria.forClass(TAvvocati.class)
				.add(Restrictions.eq("codiKIdAvvocato", Long.valueOf(idDifensore)))
		);
		return ((result != null) && (!result.isEmpty())) ? result.get(0) : new TAvvocati();
	}
	
	public RicercaProcedimentoDto getDettaglioProcedimentoNum(final int idUffOwn, final long idProc) {

		return (RicercaProcedimentoDto) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_PROCEDIMENTO_dettaglioProcedimento_Num");
				q.setParameter("IDUFFOWN", idUffOwn, Hibernate.INTEGER);
				q.setParameter("IDPROC", idProc, Hibernate.LONG);

				q.setResultTransformer(Transformers.aliasToBean(RicercaProcedimentoDto.class));

				List<RicercaProcedimentoDto> result = q.list();
				if(result!=null && result.size()>0)
					return q.list().get(0);
				else return null;
			}
		});
	}
	 
	public List<Map> getProcAggravamento(final int idUffOwn, final long idProc, final long idSogg) {

		return (List<Map>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_PROCEDIMENTO_getProcAggravamento");
				q.setParameter("IDUFFOWN", idUffOwn, Hibernate.INTEGER);
				q.setParameter("IDPROC", idProc, Hibernate.LONG);
				q.setParameter("IDSOGG", idSogg, Hibernate.LONG);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				return q.list();
			}
		});
	}	
	
	public List<Map> getProcCamere(final int idUffOwn, final long idProc, final long idSogg) {

		return (List<Map>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_PROCEDIMENTO_getProcCamere");
				q.setParameter("IDUFFOWN", idUffOwn, Hibernate.INTEGER);
				q.setParameter("IDPROC", idProc, Hibernate.LONG);
				q.setParameter("IDSOGG", idSogg, Hibernate.LONG);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				return q.list();
			}
		});
	}		
	
	/**
	 * getNumeroProcByIdProcedimento
	 * @param idProcedimento
	 * @return String
	 */
	public String getNumeroProcByIdProcedimento(long idProcedimento) {

		DetachedCriteria criteria = DetachedCriteria.forClass(TProcedimento.class);
		criteria.add(Restrictions.eq("codiKIdProc", idProcedimento));

		List<TProcedimento> proc = getHibernateTemplate().findByCriteria(criteria);
		if (proc.isEmpty())
			return null;
		TProcedimento procedimento = proc.get(0);
		return procedimento.getNumeAnnoProc()+"/"+procedimento.getNumeProc();
	}
	
	public Long aggiornaIstanzaMP(final long idUfficio,final long idProcedimento,
			final TAtto atto, final String codUtente,final TIstanza istanza,final TRelSoggIst soggIstTmp,final String listaIdSoggetti,
			final Long codiKIdAmministratore) {

		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Long idAtto = null;
				Query q = session.getNamedQuery("PG_ATTO_aggiornaIstanzaMP");
				q.setParameter("idUffOwn", idUfficio);
				q.setParameter("idProc", idProcedimento, Hibernate.LONG);
				// if (atto.getDataInvio() != null) {
				if (atto.getDataRegistrazione() != null)
					q.setParameter("dataRegistrazione", atto.getDataRegistrazione(), Hibernate.DATE);
				else
					q.setParameter("dataRegistrazione", null, Hibernate.DATE);
				
				if (atto.getDataDeposito() != null)
					q.setParameter("dataDeposito", atto.getDataDeposito(), Hibernate.DATE);
				else
					q.setParameter("dataDeposito", null, Hibernate.DATE);
				if (atto.getFileDocumento() != null) {
					q.setParameter("documento", atto.getFileDocumento(), Hibernate.BLOB);
					q.setParameter("tipoDocumento", atto.getDescTipoDocumento(), Hibernate.STRING);
					q.setParameter("nomeDocumento", atto.getDescNomeDocumento(), Hibernate.STRING);
				} else {
					q.setParameter("documento", null, Hibernate.BLOB);
					q.setParameter("tipoDocumento", null, Hibernate.STRING);
					q.setParameter("nomeDocumento", null, Hibernate.STRING);
				}
				if (atto.getDataRicezione() != null)
					q.setParameter("dataRicezioneDoc", atto.getDataRicezione(), Hibernate.DATE);
				else
					q.setParameter("dataRicezioneDoc", null, Hibernate.DATE);
				
				if (istanza != null && istanza.getDescRichiesta() != null)
					q.setParameter("contDett", istanza.getDescRichiesta());
				else
					q.setParameter("contDett", null, Hibernate.STRING);
				
			
				/************* INIZIO istanza **************************/
				// DEPOSITO
				if (istanza.getTAnUfficio() != null && istanza.getTAnUfficio().getCodiKIdUfficio() != 0)
					q.setParameter("codiFkIdUffDep", istanza.getTAnUfficio().getCodiKIdUfficio(), Hibernate.INTEGER);
				else
					q.setParameter("codiFkIdUffDep", null, Hibernate.INTEGER);

				q.setParameter("flgUffDep", istanza.getFlgUffDep(), Hibernate.CHARACTER);
				if (istanza.getDataDepIst() != null)
					q.setParameter("dataDepIst", istanza.getDataDepIst(), Hibernate.DATE);
				else
					q.setParameter("dataDepIst", null, Hibernate.DATE);
				q.setParameter("oraDepIst", istanza.getOraDepIst(), Hibernate.STRING);

				if (istanza.getDataRegIst() != null)
					q.setParameter("dataRegIst", istanza.getDataRegIst(), Hibernate.DATE);
				else
					q.setParameter("dataRegIst", null, Hibernate.DATE);
				q.setParameter("oraRegIst", istanza.getOraRegIst(), Hibernate.STRING);

				// PERVENIMENTO
				if (istanza.getTTpModPerv() != null)
					q.setParameter("codiKIdModPerv", istanza.getTTpModPerv().getCodiKIdModalita(), Hibernate.INTEGER);
				else
					q.setParameter("codiKIdModPerv", null, Hibernate.INTEGER);

				if (istanza.getDataSpedizione() != null)
					q.setParameter("dataSpedizione", istanza.getDataSpedizione(), Hibernate.DATE);
				else
					q.setParameter("dataSpedizione", null, Hibernate.DATE);

				if (istanza.getDescProvPosta() != null)
					q.setParameter("descProvPosta", istanza.getDescProvPosta(), Hibernate.STRING);
				else
					q.setParameter("descProvPosta", null, Hibernate.STRING);

				if (istanza.getDataPervIst() != null)
					q.setParameter("dataPervIst", istanza.getDataPervIst(), Hibernate.DATE);
				else
					q.setParameter("dataPervIst", null, Hibernate.DATE);
				q.setParameter("oraPervIst", istanza.getOraPervIst(), Hibernate.STRING);

				// DEPOSITANTE
				q.setParameter("flaTipoParte", soggIstTmp.getFlgTipoParte(), Hibernate.CHARACTER);

				q.setParameter("descTipoProp", soggIstTmp.getDescTipoProp(), Hibernate.STRING);

				
				if(listaIdSoggetti != null)
					q.setParameter("listaIdSoggetti", listaIdSoggetti, Hibernate.STRING);
				else
					q.setParameter("listaIdSoggetti", null, Hibernate.STRING);				

				if (soggIstTmp.getTAvvocati() != null
						&& soggIstTmp.getTAvvocati().getCodiKIdAvvocato() > 0)
					q.setParameter("codiKIdAvvocato", soggIstTmp.getTAvvocati().getCodiKIdAvvocato(), Hibernate.LONG);
				else
					q.setParameter("codiKIdAvvocato", null, Hibernate.LONG);

				// RICEVENTE
				q.setParameter("descCognFunz", istanza.getDescCognFunz(), Hibernate.STRING);
				q.setParameter("descNomeFunz", istanza.getDescNomeFunz(), Hibernate.STRING);

				// campo per altri istituti
				if (istanza.getTAnIstitutoDet() != null && istanza.getTAnIstitutoDet().getCodiKIdIstituto() > 0)
					q.setParameter("codiFkIdIstDet", istanza.getTAnIstitutoDet().getCodiKIdIstituto(), Hibernate.INTEGER);
				else
					q.setParameter("codiFkIdIstDet", null, Hibernate.INTEGER);

				// AGGIUNTE
				q.setParameter("descAltroMod", istanza.getDescAltroMod(), Hibernate.STRING);

				q.setParameter("descCognAvv", soggIstTmp.getDescCognAvv(), Hibernate.STRING);
				q.setParameter("descNomeAvv", soggIstTmp.getDescNomeAvv(), Hibernate.STRING);
				
				if(codiKIdAmministratore!=null)
					q.setParameter("codiKIdAmministratore", codiKIdAmministratore, Hibernate.LONG);
				else
					q.setParameter("codiKIdAmministratore", null, Hibernate.LONG);

				/************* FINE istanza **************************/
				
				
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
	 * getListSogAntimafia
	 * @param idUffOwn
	 * @param idProc
	 * @return List<SogIntDettaglioProcedimentoDto>
	 */
	public List<SogIntDettaglioProcedimentoDto> getListSogAntimafia(final int idUffOwn, final Long idProc)
	{
		 return (List<SogIntDettaglioProcedimentoDto>)getHibernateTemplate().execute( new HibernateCallback()
	        {
	            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
	            {
	                Query q = session.getNamedQuery( "PG_PROCEDIMENTO_listSogAntimafia" );
	                q.setParameter( "IDUFFOWN", idUffOwn, Hibernate.INTEGER );
	                q.setParameter( "IDPROC", idProc, Hibernate.LONG );
	                
	                q.setResultTransformer(Transformers.aliasToBean(SogIntDettaglioProcedimentoDto.class));
					return q.list();
	            }
	        } );
	} 	

	public List<ListaProcedimentiSoggDto> listaProcedimentiSoggetto(final int idUffOwn, final long idSogg) {
		 return (List<ListaProcedimentiSoggDto>)getHibernateTemplate().execute( new HibernateCallback()
	        {
	            public Object doInHibernate( final Session session ) throws HibernateException, SQLException
	            {
	                Query q = session.getNamedQuery("PG_PROCEDIMENTO_listaProcSogg");
	                q.setParameter("IDSOGG", idSogg, Hibernate.LONG);
	                q.setParameter("IDUFFOWN", idUffOwn, Hibernate.INTEGER);
	                
	                q.setResultTransformer(Transformers.aliasToBean(ListaProcedimentiSoggDto.class));
					return q.list();
	            }
	        } );
	}

	public List<Map> listaCoadiutori(final long idProc, final int idUfficio, final long idAmm) {
		return (List<Map>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_PROCEDIMENTO_listaCoadiutori");
				q.setParameter("IDPROC", idProc, Hibernate.LONG);
				q.setParameter("IDUFFOWN", idUfficio, Hibernate.INTEGER);
				q.setParameter("ID_AMM", idAmm, Hibernate.LONG);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				return q.list();
			}
		});		
	}

	public TRelProcCoadiutori dettaglioCoadiutore(final long idProc, final int idUfficio, final long idAmm, final int idProgrCoad) {
		return (TRelProcCoadiutori) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				TRelProcCoadiutori coad = null;
				Query q = session.getNamedQuery("PG_PROCEDIMENTO_dettaglioCoadiutore");
				q.setParameter("IDPROC", idProc, Hibernate.LONG);
				q.setParameter("IDUFFOWN", idUfficio, Hibernate.INTEGER);
				q.setParameter("ID_AMM", idAmm, Hibernate.LONG);
				q.setParameter("ID_PROGR_COAD", idProgrCoad, Hibernate.INTEGER);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				if(l!=null && l.size()>0) {
					HashMap m = (HashMap)l.get(0);
					coad = (TRelProcCoadiutori)m.get("coadiutore");
				}
				return coad;
			}
		});		
	}
	
	public short salvaCoadiutore(final TRelProcCoadiutori coadiutore) {
		return (Short) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Short ID_PROGR_COAD = null;
				Query q = session.getNamedQuery("PG_PROCEDIMENTO_salvaCoadiutori");
				q.setParameter("IDPROC", coadiutore.getId().getCodiKIdProc(), Hibernate.LONG);
				q.setParameter("IDUFFOWN", coadiutore.getId().getCodiKIdUffOwner(), Hibernate.INTEGER);
				q.setParameter("ID_AMM", coadiutore.getId().getCodiKIdAmm(), Hibernate.LONG);

				if(coadiutore.getId().getCodiKIdProgrCoad()>0)
					q.setParameter("ID_PROGR_COAD", coadiutore.getId().getCodiKIdProgrCoad(), Hibernate.SHORT);
				else
					q.setParameter("ID_PROGR_COAD", null, Hibernate.SHORT);
				
				q.setParameter("COGNOME", coadiutore.getDescCognome());
				q.setParameter("NOME", coadiutore.getDescNome());

				if(coadiutore.getDataNascita()!=null)
					q.setParameter("DATA_NASC", coadiutore.getDataNascita());
				else
					q.setParameter("DATA_NASC", null, Hibernate.DATE);
				
				q.setParameter("CODICE_FISCALE", coadiutore.getDescCodiceFiscale());
				q.setParameter("LUOGO_NASCITA", coadiutore.getDescLuogoNascita());
				q.setParameter("RECAPITO", coadiutore.getDescRecapito());
				
				if(coadiutore.getDataInizioIncarico()!=null)
					q.setParameter("DATA_INIZIO_INC", coadiutore.getDataInizioIncarico());
				else
					q.setParameter("DATA_INIZIO_INC", null, Hibernate.DATE);
				
				if(coadiutore.getDataFineIncarico()!=null)
					q.setParameter("DATA_FINE_INC", coadiutore.getDataFineIncarico());
				else
					q.setParameter("DATA_FINE_INC", null, Hibernate.DATE);
				
				q.setParameter("DESC_UT", coadiutore.getDescUtente());
				
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				Iterator iter = l.iterator();
				while (iter.hasNext()) {
					Map map = (Map) iter.next();
					ID_PROGR_COAD = (Short) map.get("ID_PROGR_COAD");
				}
				return ID_PROGR_COAD;				
				
			}
		});
	}

	public void eliminaCoadiutore(final TRelProcCoadiutori coadiutore) {
		getHibernateTemplate().execute(new HibernateCallback() {

			public Short doInHibernate(final Session session) throws HibernateException, SQLException {

				Short ID_PROGR_COAD = null;
				Query q = session.getNamedQuery("PG_PROCEDIMENTO_eliminaCoadiutori");
				q.setParameter("IDPROC", coadiutore.getId().getCodiKIdProc(), Hibernate.LONG);
				q.setParameter("IDUFFOWN", coadiutore.getId().getCodiKIdUffOwner(), Hibernate.INTEGER);
				q.setParameter("ID_AMM", coadiutore.getId().getCodiKIdAmm(), Hibernate.LONG);
				q.setParameter("ID_PROGR_COAD", coadiutore.getId().getCodiKIdProgrCoad(), Hibernate.SHORT);
				
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List l = q.list();
				Iterator iter = l.iterator();
				while (iter.hasNext()) {
					Map map = (Map) iter.next();
					ID_PROGR_COAD = (Short) map.get("ID_PROGR_COAD");
				}
				return ID_PROGR_COAD;				
				
			}
		});
		
	} 	
	@SuppressWarnings("rawtypes")
	public List<SogTitDettaglioProcedimentoDto> getSoggettiTit_Procedimento(final long id) {
		return (List<SogTitDettaglioProcedimentoDto>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_PROCEDIMENTO_sogTit_Procedimento");
				q.setParameter("vidProc", id);
				q.setResultTransformer(Transformers.aliasToBean(SogTitDettaglioProcedimentoDto.class));
				return q.list();
			}
		});
	}

	public List listaBeniAttoDecisorioFiltrata(final long idProcedimento, final long idAtto, final long idUfficioUtente, 
			final long idSoggettoDiretto, final long idSoggettoTitolare, final long idTipologia, final long idCategoria, final long idSottocategoria) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_PROCEDIMENTO_lista_Beni_Decisorio_Filtrata");

				q.setParameter("idProcedimento", idProcedimento, Hibernate.LONG);
				q.setParameter("idUfficioUtente", idUfficioUtente);
				q.setParameter("idAtto", idAtto);
				
				if (idSoggettoDiretto > 0)
					q.setParameter("idSoggettoDiretto", idSoggettoDiretto);
				else
					q.setParameter("idSoggettoDiretto", null, Hibernate.LONG);
				if (idSoggettoTitolare > 0)
					q.setParameter("idSoggettoTitolare", idSoggettoTitolare);
				else
					q.setParameter("idSoggettoTitolare", null, Hibernate.LONG);
				if (idTipologia > 0)
					q.setParameter("idTipologia", idTipologia);
				else
					q.setParameter("idTipologia", null, Hibernate.LONG);
				if (idCategoria > 0)
					q.setParameter("idCategoria", idCategoria);
				else
					q.setParameter("idCategoria", null, Hibernate.LONG);
				if (idSottocategoria > 0)
					q.setParameter("idSottocategoria", idSottocategoria);
				else
					q.setParameter("idSottocategoria", null, Hibernate.LONG);

				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List lista = q.list();
				return lista;
			}
		});
	}


	@SuppressWarnings("rawtypes")
	public List<SogTitDettaglioProcedimentoDto> getSoggettiTit_ProcedimentoAtto(final long idProc, final long idAtto) {
		return (List<SogTitDettaglioProcedimentoDto>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				Query q = session.getNamedQuery("PG_PROCEDIMENTO_sogTit_ProcedimentoAtto");
				q.setParameter("idProc", idProc);
				q.setParameter("idAtto", idAtto);				
				q.setResultTransformer(Transformers.aliasToBean(SogTitDettaglioProcedimentoDto.class));
				return q.list();
			}
		});
	}
	
	public void associaRelSoggettoInterveniente(final TRelSoginvSogint rec) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				session.saveOrUpdate(rec);
				session.flush();
				return null;
			}
		});
	}	

	public void eliminaRelSoggettoInterveniente(final TRelSoginvSogintId id) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				List<TRelSoginvSogint> recs = null;
				DetachedCriteria criteria = DetachedCriteria.forClass(TRelSoginvSogint.class);
				criteria.add(Restrictions.eq("id.codiKIdProc", id.getCodiKIdProc()));
				criteria.add(Restrictions.eq("id.codiKIdSoggInt", id.getCodiKIdSoggInt()));
				criteria.add(Restrictions.eq("id.codiKIdSoggInv", id.getCodiKIdSoggInv()));
				criteria.add(Restrictions.eq("id.codiKIdUffOwner", id.getCodiKIdUffOwner()));
				recs = getHibernateTemplate().findByCriteria(criteria);
				TRelSoginvSogint rec = recs.get(0);
				session.delete(rec);
				session.flush();
				return null;
			}
		});
	}	
	
	public void associaRelBeneSoggettoInterveniente(final TRelSoginvBene rec) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				session.saveOrUpdate(rec);
				session.flush();
				return null;
			}
		});
	}	
	
	public void eliminaRelBeneSoggettoInterveniente(final TRelSoginvBeneId id) {

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				List<TRelSoginvBene> recs = null;
				DetachedCriteria criteria = DetachedCriteria.forClass(TRelSoginvBene.class);
				criteria.add(Restrictions.eq("id.codiKIdProc", id.getCodiKIdProc()));
				criteria.add(Restrictions.eq("id.codiKIdSoggInv", id.getCodiKIdSoggInv()));
				criteria.add(Restrictions.eq("id.codiKIdBene", id.getCodiKIdBene()));
				criteria.add(Restrictions.eq("id.codiKIdUffOwner", id.getCodiKIdUffOwner()));
				recs = getHibernateTemplate().findByCriteria(criteria);
				TRelSoginvBene rec = recs.get(0);
				session.delete(rec);
				session.flush();
				return null;
			}
		});
	}		
	
	public String eliminaSogInv(final long idProc, final int idUffOwn, final long idSogInv) {

		return (String)getHibernateTemplate().execute(new HibernateCallback() {

			public String doInHibernate(final Session session) throws HibernateException, SQLException {

				String msg = null;
				Query q = session.getNamedQuery("PG_PROCEDIMENTO_eliminaSogInv");
				q.setParameter("IDPROC", idProc);
				q.setParameter("IDUFFOWN", idUffOwn);
				q.setParameter("IDSOGINV", idSogInv);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List list = q.list();
				Iterator it = list.iterator();
				while (it.hasNext()) {
					Map<String, Object> row = (Map<String, Object>) it.next();
					msg = (String) row.get("MESSAGE");
				}
				return msg;
			}
		});
	}

	/***
	 * Estrae la prima comunicazione dove il proc passato e presente 
	 * nella 
	 */
	public TComunicazione getComunicazioneByIdProcESogIntMis(final long idProc,final long idSoggetto) {
		return (TComunicazione)getHibernateTemplate().execute(new HibernateCallback() {

			public TComunicazione doInHibernate(final Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(TComunicazione.class);
				criteria.add(Restrictions.eq("TRelProcSogint.id.codiKIdProc", idProc));
				criteria.add(Restrictions.eq("flgStatoInvio", '2')); // Inviata
				List<TComunicazione>  risultati= criteria .list();
				if (risultati.size()>0) {
					for (TComunicazione com : risultati) {
						Long idMisPers = com.getCodiFkIdMisuraPers();
						if (idMisPers!=null) {
							criteria = session.createCriteria(TMisuraPers.class);
							criteria.add(Restrictions.eq("TSoggetto.id.codiKIdSoggInt", idSoggetto));
							List<TMisuraPers> listaMisure = criteria.list();
							if (listaMisure.size()>0) return com;
						}
					}
				}
				return null;
				
				
			}
		});
		/*
		DetachedCriteria criteria = DetachedCriteria.forClass(TComunicazione.class);
		criteria.add(Restrictions.eq("TRelProcSogint.id.codiKIdProc", idProc));
		criteria.add(Restrictions.eq("flgStatoInvio", '2')); // Inviata
		List<TComunicazione>  risultati= getHibernateTemplate().findByCriteria(criteria);
		if (risultati.size()>0) {
			for (TComunicazione com : risultati) {
				Long idMisPers = com.getCodiFkIdMisuraPers();
				if (idMisPers!=null) {
					criteria = DetachedCriteria.forClass(TMisuraPers.class);
					criteria.add(Restrictions.eq("TSoggetto.id.codiKIdSoggInt", idSoggetto));
					List<TMisuraPers> listaMisure = getHibernateTemplate().findByCriteria(criteria);
					if (listaMisure.size()>0) return com;
				}
			}
		}
		return null;*/
	}
	
	
	public TComunicazione getComunicazioneByIdProc1ETipoUff(final long idSoggetto,final  long idProc,final  int idTipoUff) {
		return (TComunicazione)getHibernateTemplate().execute(new HibernateCallback() {

			public TComunicazione doInHibernate(final Session session) throws HibernateException, SQLException {
			
				Criteria criteria = session.createCriteria(TRelProcProc.class);
					
				criteria.add(Restrictions.eq("TProcedimento.codiKIdProc", idProc));
				//criteria.add(Restrictions.eq("TAnUfficio.TTpTipoUfficio.codiKIdTipoUff", (idTipoUff==19?6:19))); // Se trib cda altrimenti trib
				List<TRelProcProc>  risultati= criteria.list();
				if (risultati.size()>0) {
					for (TRelProcProc procProc : risultati) {
						if ((idTipoUff==19?6:19) == procProc.getTAnUfficio().getTTpTipoUfficio().getCodiKIdTipoUff() ) {
							TComunicazione com = getComunicazioneByIdProcESogIntMis(procProc.getId().getCodiKIdProc2(), idSoggetto);
							if (com!=null) {
								return com;
							}
						}
					}
				}
				return null;
			
			}
		});
	
	}
	
	public TTpEsito getTTpEsito(long idEsito) {
		TTpEsito risultato = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpEsito.class);
		criteria.add(Restrictions.eq("codiKIdEsito", new Integer(new Long(idEsito).intValue())));
		List risultati = getHibernateTemplate().findByCriteria(criteria);
		return (TTpEsito)risultati.get(0);
	}
	
	public String listaIntervenientiNonAssociati(final long idProcedimento, final int idUfficio){
			return (String)getHibernateTemplate().execute(new HibernateCallback() {

				public String doInHibernate(final Session session) throws HibernateException, SQLException {

					String msg = null;
					Query q = session.getNamedQuery("PG_PROCEDIMENTO_listaIntervenientiNonAssociati");
					q.setParameter("IDPROC", idProcedimento);
					q.setParameter("IDUFFOWN", idUfficio);
					q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
					List list = q.list();
					Iterator it = list.iterator();
					while (it.hasNext()) {
						Map<String, Object> row = (Map<String, Object>) it.next();
						msg = (String) row.get("NOME_SOGGETTO");
					}
					return msg;
				}
			});
		}

	@Override
	public int getUffOwner(long idProcedimento) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TProcedimento.class);
		criteria.add(Restrictions.eq("codiKIdProc", idProcedimento));
		List risultati = getHibernateTemplate().findByCriteria(criteria);
		if(risultati != null)
		{
			TProcedimento procedimento =  (TProcedimento)risultati.get(0);
			return procedimento.getTAnUfficioByCodiFkIdUffOwner().getCodiKIdUfficio();
		}
		else
			return 0;
	}
	
	public List<TRelAttoProc> getAttoProcDaAssociare() {
		DetachedCriteria criteria = DetachedCriteria.forClass(TRelAttoProc.class);
		criteria.add(Restrictions.eq("flgAssociaPdoc", 'N'));
		List<TRelAttoProc> relAttoProc = getHibernateTemplate().findByCriteria(criteria);
		//mod documentale --> errore java.util.ConcurrentModificationException <-- 
		List<TRelAttoProc> relAttoProcFiltr = new ArrayList(relAttoProc); 
		// fine mod documentale 
		if (relAttoProc.isEmpty())
			return null;
		else
		{
			for (TRelAttoProc tRelAttoProc : relAttoProc) {
				DetachedCriteria criteriaAtto = DetachedCriteria.forClass(TAtto.class);
				criteriaAtto.add(Restrictions.eq("id.codiKIdAtto", tRelAttoProc.getTAtto().getId().getCodiKIdAtto()));
				criteriaAtto.add(Restrictions.eq("id.codiKIdUffOwner", tRelAttoProc.getId().getCodiKIdUffOwner()));
				List<TAtto> tAttoList = getHibernateTemplate().findByCriteria(criteriaAtto);
				if (!tAttoList.isEmpty())
				{
					TAtto tAtto = tAttoList.get(0);
					// modifica documentale 
					//if(tAtto.getFileDocumento()==null && tAtto.getNumeDocumentoPdoc()==null)
					if(tAtto.getNumeDocumentoPdoc()==null)
						//relAttoProc.remove(tRelAttoProc);
						relAttoProcFiltr.remove(tRelAttoProc);
				}
			}
			//return relAttoProc;  sostituito con nuova lista filtrata
			return relAttoProcFiltr;
		}
	}

}
