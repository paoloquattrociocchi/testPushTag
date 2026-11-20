package it.giustizia.smp.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.ListUtils;
import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import it.giustizia.smp.dao.AttoDao;
import it.giustizia.smp.dao.BeneDao;
import it.giustizia.smp.dao.CommonDao;
import it.giustizia.smp.dao.NotificheDao;
import it.giustizia.smp.dao.ProcedimentoDao;
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
import it.giustizia.smp.form.AmministratoreForm;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.BeneForm;
import it.giustizia.smp.form.BeneSoggettoForm;
import it.giustizia.smp.form.CertificatoPenaleForm;
import it.giustizia.smp.form.CoadiutoreForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.ComunicazioneForm;
import it.giustizia.smp.form.DifensoreForm;
import it.giustizia.smp.form.DifensoreSoggettoForm;
import it.giustizia.smp.form.EredeConviventeForm;
import it.giustizia.smp.form.ImpugnazioneForm;
import it.giustizia.smp.form.MagistratoForm;
import it.giustizia.smp.form.MisuraForm;
import it.giustizia.smp.form.NumeroRegistroForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.ProfiloForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.form.UdienzaForm;
import it.giustizia.smp.model.TAnComuni;
import it.giustizia.smp.model.TAnMagistrati;
import it.giustizia.smp.model.TAnManuali;
import it.giustizia.smp.model.TAnNormative;
import it.giustizia.smp.model.TAnProcedente;
import it.giustizia.smp.model.TAnProvincia;
import it.giustizia.smp.model.TAnStato;
import it.giustizia.smp.model.TAnUfficio;
import it.giustizia.smp.model.TAtto;
import it.giustizia.smp.model.TAttoId;
import it.giustizia.smp.model.TAvvocati;
import it.giustizia.smp.model.TBene;
import it.giustizia.smp.model.TCauzione;
import it.giustizia.smp.model.TDecreto;
import it.giustizia.smp.model.TDefPatr;
import it.giustizia.smp.model.TDefPers;
import it.giustizia.smp.model.TIstanza;
import it.giustizia.smp.model.TMisuraPers;
import it.giustizia.smp.model.TPersonaFisica;
import it.giustizia.smp.model.TPersonaFisicaId;
import it.giustizia.smp.model.TPersonaGiur;
import it.giustizia.smp.model.TPersonaGiurId;
import it.giustizia.smp.model.TProcedimento;
import it.giustizia.smp.model.TProvFg;
import it.giustizia.smp.model.TRelAttoMisPatr;
import it.giustizia.smp.model.TRelAttoMisPers;
import it.giustizia.smp.model.TRelAttoProc;
import it.giustizia.smp.model.TRelBeneAtto;
import it.giustizia.smp.model.TRelIterProc;
import it.giustizia.smp.model.TRelMagProc;
import it.giustizia.smp.model.TRelProcBene;
import it.giustizia.smp.model.TRelProcCoadiutori;
import it.giustizia.smp.model.TRelProcIntestatariAvv;
import it.giustizia.smp.model.TRelProcProc;
import it.giustizia.smp.model.TRelProcSogint;
import it.giustizia.smp.model.TRelProcSogintAvv;
import it.giustizia.smp.model.TRelProcSogintId;
import it.giustizia.smp.model.TRelSoggImp;
import it.giustizia.smp.model.TRelSoggIst;
import it.giustizia.smp.model.TRelSogintErediConv;
import it.giustizia.smp.model.TRelSoginvBene;
import it.giustizia.smp.model.TRelSoginvBeneId;
import it.giustizia.smp.model.TRelSoginvSogint;
import it.giustizia.smp.model.TRelSoginvSogintId;
import it.giustizia.smp.model.TSoggetto;
import it.giustizia.smp.model.TSoggettoId;
import it.giustizia.smp.model.TTpCatAtto;
import it.giustizia.smp.model.TTpEsito;
import it.giustizia.smp.model.TTpSettoreAtt;
import it.giustizia.smp.model.TTpStatoAtto;
import it.giustizia.smp.model.TTpStatoProc;
import it.giustizia.smp.model.TTpTipoFascicolo;
import it.giustizia.smp.model.TTpTipoInteresse;
import it.giustizia.smp.model.TTpTipoMisura;
import it.giustizia.smp.model.TTpTipoPrevenzione;
import it.giustizia.smp.model.TTpTipoProc;
import it.giustizia.smp.model.TTpTipoSocieta;
import it.giustizia.smp.model.TTpTipoStrada;
import it.giustizia.smp.model.TTpTipoUfficio;
import it.giustizia.smp.model.VRappTrasm;
import it.giustizia.smp.model.VRicevuta;
import it.giustizia.smp.service.webservices.IAllineamentoFascicoliPDocService;
import it.giustizia.smp.service.webservices.IDocumentaleService;
import it.giustizia.smp.service.webservices.IRegindeService;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.utility.tree.JsNodeInfo;
import it.giustizia.smp.utility.tree.Node;
import it.giustizia.smp.utility.tree.ProcedimentoTree;
import it.giustizia.smp.utility.tree.TreeNode;
import it.giustizia.smp.webservice.client.allineamentoFascicoliPDoc.model.general.recuperoFascicoloResponse.Risposta;
import it.giustizia.smp.webservice.client.reginde.Ruoloente;
import it.giustizia.smp.webservice.client.reginde.Soggetto;

/**
 * ProcedimentoServiceImpl
 * 
 * @author Almaviva S.p.A. classe del procedimento
 */
@Transactional(rollbackFor = Exception.class)
public class ProcedimentoServiceImpl implements ProcedimentoService {

	private static final long ID_STATO_PROCEDIMENTO_IN_ACQUISIZIONE_PROPOSTA = 5L;
	private static final long ID_STATO_PROCEDIMENTO_IN_ISTRUTTORIA = 21L;
	private static final long ID_STATO_PROCEDIMENTO_IN_TRATTAZIONE_DI_PRIMO_GRADO = 6L;
	private static final long ID_STATO_PROCEDIMENTO_IN_TRATTAZIONE_DI_SECONDO_GRADO = 13L;
	private static final long ID_STATO_PROCEDIMENTO_IN_TRATTAZIONE_OPPOSIZIONE = 26L;
	private static final long ID_STATO_PROCEDIMENTO_IN_TRATTAZIONE_REVOCAZIONE = 44L;
	private static final long ID_STATO_PROCEDIMENTO_IN_LAVORAZIONE = 34L;
	private static final long ID_STATO_PROCEDIMENTO_ISCRITTO = 2L;
	private static final long ID_STATO_PROCEDIMENTO_ISCRITTO_PER_OPPOSIZIONE = 25L;
	private static final long ID_STATO_PROCEDIMENTO_ISCRITTO_PER_REVOCAZIONE = 43L;
	private static final long ID_STATO_PROCEDIMENTO_ASSEGNATO = 29L;
	private static final long ID_STATO_PROCEDIMENTO_RICONCILIATO = 41L;
	private final static long ID_TIPO_FASCICOLO_AGGRAVAMENTO = 3L;
	@SuppressWarnings("unused")
	private final static long ID_TIPO_FASCICOLO_ACCERTAMENTO = 26L;
	private final static long ID_TIPO_FASCICOLO_OPPOSIZIONE_RESTITUZIONE = 5L;
	private final static long ID_TIPO_FASCICOLO_OPPOSIZIONE_DIVIETI_DEL_QUESTORE = 4L;
	private final static long ID_TIPO_FASCICOLO_REVOCAZIONE_CONFISCA = 7L;
	private final static long ID_TIPO_FASCICOLO_ANNULLATO = 19L;
	private AttoDao attoDao;
	@Autowired
	private AttoService attoService;
	private BeneDao beneDao;
	private BeneService beneService;
	private CommonDao commonDao;
	@Autowired
	private Mapper mapperBean;
	@Autowired
	private MisuraService misuraService;
	@Autowired
	private UdienzaService udienzaService;
	private ProcedimentoDao procedimentoDao;
	@Autowired(required = true)
	private HttpServletRequest request;
	@Autowired
	private IDocumentaleService documentaleService;
	@Autowired
	private IRegindeService regindeService;

	@Autowired
	private NotificheDao notificheDao;

	// 20200709 [SG]: nuovo job per allineamento fascicoli PDoc andati in errore col
	// documentaleJob
	@Autowired
	private IAllineamentoFascicoliPDocService allineamentoFascicoliPDocService;

	private Logger logger = Logger.getLogger(getClass());

	public final void setDocumentaleService(IDocumentaleService documentaleService) {

		this.documentaleService = documentaleService;
	}

	public long aggiornaPersonaFisica(SoggettoForm soggettoForm, long idProcedimento, String codUtente) {

		TPersonaFisica persona = new TPersonaFisica();
		TSoggetto soggInt = new TSoggetto();
		TRelProcSogint relProcSoggInt = new TRelProcSogint();
		mapperBean.map(soggettoForm, persona);
		mapperBean.map(soggettoForm, soggInt);
		mapperBean.map(soggettoForm, relProcSoggInt);
		AssociaPersonaDto associaSoggetto = new AssociaPersonaDto();
		associaSoggetto.setPersFis(persona);
		associaSoggetto.setSoggInt(soggInt);
		associaSoggetto.setRelProcSoggInt(relProcSoggInt);
		associaSoggetto.setIDVER(BigDecimal.valueOf(soggettoForm.getIdVersione()));
		associaSoggetto.setIDPERS(BigDecimal.valueOf(soggettoForm.getIdSoggetto()));
		associaSoggetto.setIDUFFOWN(BigDecimal.valueOf(soggettoForm.getIdUfficio()));
		return procedimentoDao.aggiornaPersonaFisica(associaSoggetto, idProcedimento, codUtente);
	}

	public Long aggiornaProcedimento(long idUffOwn, String codUtente, ProcedimentoForm procForm) {

		Long idProc;
		TProcedimento proc = new TProcedimento();
		mapperBean.map(procForm, proc);
		Date now = Calendar.getInstance().getTime();
		idProc = procedimentoDao.aggiornaProcedimento(idUffOwn, codUtente, proc, now);
		return idProc;
	}

	public Long aggiornaPropostaAtto(long idUfficio, long idDestinatario, long idUltDestinatario, long idProcedimento,
			AttoForm attoForm, String codUtente) {

		TAtto atto = new TAtto();
		mapperBean.map(attoForm, atto);
		Utility.popolaDocumento(attoForm, atto);
		TIstanza istanza = new TIstanza();
		mapperBean.map(attoForm, istanza);
		return procedimentoDao.aggiornaPropostaAtto(idUfficio, idDestinatario, idUltDestinatario, idProcedimento, atto,
				codUtente, istanza);
	}

	public void annullaStatoDelProcedimento(long idProcedimento, Integer idUfficioUtente, String codUtente,
			String tipoUtente, long idAtto) {
		procedimentoDao.annullaStatoDelProcedimento(idProcedimento,
				Long.valueOf(ProcedimentoServiceImpl.ID_TIPO_FASCICOLO_ANNULLATO), null, idUfficioUtente, codUtente);
		return;
	}

	public void aggiornaStatoDelProcedimento(long idProcedimento, Integer idUfficioUtente, String codUtente,
			String tipoUtente, long idAtto) {

		TProcedimento procedimento = procedimentoDao.getProcedimentoById(idProcedimento);
		TTpStatoProc stato = procedimento.getTTpStatoProc();
		if (stato.getCodiKIdStatoProc() == ProcedimentoServiceImpl.ID_STATO_PROCEDIMENTO_ISCRITTO) {
			if (tipoUtente.equalsIgnoreCase(Constants.TIPO_UTENTE_SMP)) {
				int idTipoUffUtente = (Integer) request.getSession().getAttribute(Constants.ID_TIPO_UFFICIO);
				if (procedimento.getTTpTipoFascicolo()
						.getCodiKIdTipoFasc() != Constants.TIPO_FASCICOLO_OPP_DIVIETI_QUESTORE) {
					if (idTipoUffUtente == Constants.TIPO_UFFICIO_PROCURA_GENERALE) {
						procedimentoDao.aggiornaStatoDelProcedimento(idProcedimento,
								Long.valueOf(ProcedimentoServiceImpl.ID_STATO_PROCEDIMENTO_ASSEGNATO), null,
								idUfficioUtente, codUtente);
					} else {
						procedimentoDao.aggiornaStatoDelProcedimento(idProcedimento,
								Long.valueOf(ProcedimentoServiceImpl.ID_STATO_PROCEDIMENTO_IN_ISTRUTTORIA), null,
								idUfficioUtente, codUtente);
					}

					return;
				}
			}
			if (tipoUtente.equalsIgnoreCase(Constants.TIPO_UTENTE_SMP_GIU)) {
				if ((Integer) request.getSession().getAttribute("idTipoUfficio") == 6) {
					procedimentoDao.aggiornaStatoDelProcedimento(idProcedimento,
							Long.valueOf(ProcedimentoServiceImpl.ID_STATO_PROCEDIMENTO_IN_TRATTAZIONE_DI_SECONDO_GRADO),
							null, idUfficioUtente, codUtente);
					return;
				} else {
					procedimentoDao.aggiornaStatoDelProcedimento(idProcedimento,
							Long.valueOf(ProcedimentoServiceImpl.ID_STATO_PROCEDIMENTO_IN_TRATTAZIONE_DI_PRIMO_GRADO),
							null, idUfficioUtente, codUtente);
					return;
				}
			}
		} else if (stato
				.getCodiKIdStatoProc() == ProcedimentoServiceImpl.ID_STATO_PROCEDIMENTO_ISCRITTO_PER_OPPOSIZIONE) {
			procedimentoDao.aggiornaStatoDelProcedimento(idProcedimento,
					Long.valueOf(ProcedimentoServiceImpl.ID_STATO_PROCEDIMENTO_IN_TRATTAZIONE_OPPOSIZIONE), null,
					idUfficioUtente, codUtente);
			return;
		} else if (stato
				.getCodiKIdStatoProc() == ProcedimentoServiceImpl.ID_STATO_PROCEDIMENTO_ISCRITTO_PER_REVOCAZIONE) {
			int idTipoUffUtente = (Integer) request.getSession().getAttribute(Constants.ID_TIPO_UFFICIO);
			if (idTipoUffUtente == Constants.TIPO_UFFICIO_PROCURA_GENERALE) {
				procedimentoDao.aggiornaStatoDelProcedimento(idProcedimento,
						Long.valueOf(ProcedimentoServiceImpl.ID_STATO_PROCEDIMENTO_ASSEGNATO), null, idUfficioUtente,
						codUtente);
			} else {
				procedimentoDao.aggiornaStatoDelProcedimento(idProcedimento,
						Long.valueOf(ProcedimentoServiceImpl.ID_STATO_PROCEDIMENTO_IN_TRATTAZIONE_REVOCAZIONE), null,
						idUfficioUtente, codUtente);
			}
			return;
		}
	}

	public void aggiornaStatoDelProcedimentoRiconcilia(long idProcedimento, Integer idUfficioUtente, String codUtente) {

		procedimentoDao.aggiornaStatoDelProcedimento(idProcedimento,
				Long.valueOf(ProcedimentoServiceImpl.ID_STATO_PROCEDIMENTO_RICONCILIATO), null, idUfficioUtente,
				codUtente);

	}

	// ricalcolo ed aggiornamento del tipo procedimento
	// e dei flag antimafia, antiterrorismo e ordinaria di un procedimento
	public Long aggiornaTipoProcPrevProc(long idProc, long idUffOwn, String codUtente) {

		return procedimentoDao.aggiornaTipoProcPrevProc(idProc, idUffOwn, codUtente);
	}

	public void associaMagistratoAlProcedimento(MagistratoForm magistrato, long idProcedimento,
			Integer idUfficioUtente) {

		procedimentoDao.associaMagistratoAlProcedimento(magistrato.getId(), magistrato.getDataFineAssegnazione(),
				magistrato.getTipoSostituzione(), magistrato.getOrdineDiVisualizzazione(), magistrato.getRelatore(),
				magistrato.getDataAssegnazioneFascicolo(), magistrato.getRuolo().getId(), magistrato.getAltroMotivo(),
				idProcedimento, idUfficioUtente);
	}

	public void associaSoggettiTitolariPerBeneMisuraPatrimoniale(List<BeneForm> beniSelezionati, long idProcedimento,
			int idUfficioUtente, long idAtto, long idSoggettoInteressato, String codUtente) {

		for (BeneForm bene : beniSelezionati) {
			procedimentoDao.associaSoggettiTitolariPerBeneMisuraPatrimoniale(bene.getIdBene(),
					bene.getListaIdCheckboxString(), idProcedimento, idUfficioUtente, idAtto, idSoggettoInteressato,
					codUtente);
		}
		aggiornaTipoProcPrevProc(idProcedimento, idUfficioUtente, codUtente);
	}

	@SuppressWarnings("null")
	public AssociaPersonaDto associaSoggettoProcedimento(int idUffOwn, String codUtente, Long idProc,
			SoggettoForm soggettoForm) {

		ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute(Constants.ID_PROCEDIMENTO);
		Date now = Calendar.getInstance().getTime();
		TPersonaFisica persona = new TPersonaFisica();
		TPersonaGiur personaGiur = new TPersonaGiur();
		TTpTipoSocieta tipoSoc = new TTpTipoSocieta();
		TSoggetto soggInt = new TSoggetto();
		TRelProcSogint relProcSoggInt = new TRelProcSogint();
		mapperBean.map(soggettoForm, persona);
		mapperBean.map(soggettoForm, personaGiur);
		mapperBean.map(soggettoForm, tipoSoc);
		mapperBean.map(soggettoForm, soggInt);
		mapperBean.map(soggettoForm, relProcSoggInt);
		AssociaPersonaDto associaSoggetto = new AssociaPersonaDto();
		associaSoggetto.setPersFis(persona);
		associaSoggetto.setPersonaGiur(personaGiur);
		associaSoggetto.getPersonaGiur().setTTpTipoSocieta(tipoSoc);
		associaSoggetto.setSoggInt(soggInt);
		associaSoggetto.setRelProcSoggInt(relProcSoggInt);
		AssociaPersonaDto temp = null;
		if ((associaSoggetto.getPersFis().getId() != null)
				&& (associaSoggetto.getPersFis().getId().getCodiKIdPersonaFis() > 0)
				&& (associaSoggetto.getPersFis().getDescCognome() != null)) {
			temp = procedimentoDao.associaPersonaFisica(idUffOwn, codUtente, idProc, associaSoggetto, now);
		} else if ((associaSoggetto.getPersonaGiur().getId() != null)
				&& (associaSoggetto.getPersonaGiur().getId().getCodiKIdPersGiur() > 0)
				&& (associaSoggetto.getPersonaGiur().getDescRagioneSoc() != null)) {
			temp = procedimentoDao.associaPersonaGiuridica(idUffOwn, codUtente, idProc, associaSoggetto, now);
		} else if (associaSoggetto.getPersFis().getDescCognome() != null) {
			temp = procedimentoDao.associaNuovaPersonaFisica(idUffOwn, codUtente, idProc, associaSoggetto, now);
		} else if (associaSoggetto.getPersonaGiur().getDescRagioneSoc() != null) {
			temp = procedimentoDao.associaNuovaPersonaGiuridica(idUffOwn, codUtente, idProc, associaSoggetto, now);
		}
		associaSoggetto.setIDPERS(temp.getIDPERS());
		associaSoggetto.setIDPROC(temp.getIDPROC());
		associaSoggetto.setIDSOGGINT(temp.getIDSOGGINT());
		associaSoggetto.setIDUFFOWN(temp.getIDUFFOWN());
		associaSoggetto.setIDVER(temp.getIDVER());
		if (procedimento.getTipoProcedimento() != null && !procedimento.getTipoProcedimento().getId()
				.equalsIgnoreCase(Constants.TIPOPROC_AGGRAVAMENTO_PERSONALE))
			aggiornaTipoProcPrevProc(idProc, idUffOwn, codUtente);
		return associaSoggetto;
	}

	public AssociaPersonaDto nuovaPersonaFisica(int idUffOwn, String codUtente, SoggettoForm soggettoForm) {

		Date now = Calendar.getInstance().getTime();
		TPersonaFisica persona = new TPersonaFisica();
		TPersonaGiur personaGiur = new TPersonaGiur();
		TTpTipoSocieta tipoSoc = new TTpTipoSocieta();
		TSoggetto soggInt = new TSoggetto();
		TRelProcSogint relProcSoggInt = new TRelProcSogint();
		mapperBean.map(soggettoForm, persona);
		mapperBean.map(soggettoForm, personaGiur);
		mapperBean.map(soggettoForm, tipoSoc);
		mapperBean.map(soggettoForm, soggInt);
		mapperBean.map(soggettoForm, relProcSoggInt);
		AssociaPersonaDto associaSoggetto = new AssociaPersonaDto();
		associaSoggetto.setPersFis(persona);
		associaSoggetto.setPersonaGiur(personaGiur);
		associaSoggetto.getPersonaGiur().setTTpTipoSocieta(tipoSoc);
		associaSoggetto.setSoggInt(soggInt);
		associaSoggetto.setRelProcSoggInt(relProcSoggInt);
		AssociaPersonaDto temp = null;
		temp = procedimentoDao.nuovaPersonaFisica(idUffOwn, codUtente, associaSoggetto, now);
		associaSoggetto.setIDPERS(temp.getIDPERS());
		associaSoggetto.setIDPROC(temp.getIDPROC());
		associaSoggetto.setIDSOGGINT(temp.getIDSOGGINT());
		associaSoggetto.setIDUFFOWN(temp.getIDUFFOWN());
		associaSoggetto.setIDVER(temp.getIDVER());
		return associaSoggetto;
	}

	public AssociaPersonaDto nuovaPersonaGiuridica(int idUffOwn, String codUtente, SoggettoForm soggettoForm) {

		Date now = Calendar.getInstance().getTime();
		TPersonaFisica persona = new TPersonaFisica();
		TPersonaGiur personaGiur = new TPersonaGiur();
		TTpTipoSocieta tipoSoc = new TTpTipoSocieta();
		TSoggetto soggInt = new TSoggetto();
		TRelProcSogint relProcSoggInt = new TRelProcSogint();
		mapperBean.map(soggettoForm, persona);
		mapperBean.map(soggettoForm, personaGiur);
		mapperBean.map(soggettoForm, tipoSoc);
		mapperBean.map(soggettoForm, soggInt);
		mapperBean.map(soggettoForm, relProcSoggInt);
		AssociaPersonaDto associaSoggetto = new AssociaPersonaDto();
		associaSoggetto.setPersFis(persona);
		associaSoggetto.setPersonaGiur(personaGiur);
		associaSoggetto.getPersonaGiur().setTTpTipoSocieta(tipoSoc);
		associaSoggetto.setSoggInt(soggInt);
		associaSoggetto.setRelProcSoggInt(relProcSoggInt);
		AssociaPersonaDto temp = null;
		temp = procedimentoDao.nuovaPersonaGiuridica(idUffOwn, codUtente, associaSoggetto, now);
		associaSoggetto.setIDPERS(temp.getIDPERS());
		associaSoggetto.setIDPROC(temp.getIDPROC());
		associaSoggetto.setIDSOGGINT(temp.getIDSOGGINT());
		associaSoggetto.setIDUFFOWN(temp.getIDUFFOWN());
		associaSoggetto.setIDVER(temp.getIDVER());
		return associaSoggetto;
	}

	public boolean verificaDataRevocaDifensoreAssociato(DifensoreSoggettoForm dsf) {

		long ritorno = procedimentoDao.verificaDataRevocaDifensoreAssociato(
				dsf.getSoggettoForm().getIdProcedimento().longValue(), dsf.getSoggettoForm().getIdUfficio(),
				dsf.getDifensoreForm().getIdDifensore().longValue(), dsf.getDataRevoca());
		if (ritorno == 1L)
			return true;
		else
			return false;
	}

	public boolean verificaCancellaDifensoreAssociato(DifensoreSoggettoForm dsf) {

		List queryResults = null;
		queryResults = procedimentoDao.verificaCancellaRelazioneAvvocatoProcSogint(
				dsf.getSoggettoForm().getIdProcedimento().longValue(), dsf.getSoggettoForm().getIdUfficio(),
				dsf.getDifensoreForm().getIdDifensore().longValue());
		if ((queryResults == null) || queryResults.isEmpty())
			return false;
		else
			return true;
	}

	public void cancellaDifensoreAssociato(DifensoreSoggettoForm dsf) {

		procedimentoDao.cancellaRelazioneAvvocatoProcSogint(dsf.getSoggettoForm().getIdProcedimento(),
				dsf.getSoggettoForm().getIdUfficio(), dsf.getSoggettoForm().getIdSoggetto(),
				dsf.getDifensoreForm().getIdDifensore());
		// TODO : Cancellazione commentata per relazione su T_REL_AVV_UDIENZA
		// (se presente Udienza)
		/*
		 * if (dsf.getDifensoreForm().getIdReginde() == null) {
		 * procedimentoDao.cancellaAvvocato (dsf.getSoggettoForm().getIdUfficio(),
		 * dsf.getDifensoreForm().getIdDifensore()); }
		 */
	}

	public void cancellaDifensoreAssociatoIntestatario(DifensoreSoggettoForm dsf) {

		procedimentoDao.cancellaRelazioneIntestatariAvv(dsf.getSoggettoForm().getIdProcedimento(),
				dsf.getSoggettoForm().getIdUfficio(), dsf.getSoggettoForm().getIdSoggetto(),
				dsf.getDifensoreForm().getIdDifensore());
	}

	public void cancellaEredeDelDefunto(SoggettoForm soggettoForm) {

		procedimentoDao.cancellaEredeOppureConviventeDelSoggetto(soggettoForm.getIdSoggetto(),
				soggettoForm.getIdProcedimento(), soggettoForm.getIdUfficio(),
				soggettoForm.getEredeConviventeForm().getId());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DifensoreSoggettoForm caricaDifensoreAssociatoSoggetto(DifensoreSoggettoForm dsf) {

		List queryResults = null;
		queryResults = procedimentoDao.caricaDifensoriAssociatiSoggetto(dsf.getSoggettoForm().getIdProcedimento(),
				dsf.getSoggettoForm().getIdUfficio(), dsf.getSoggettoForm().getIdSoggetto(),
				dsf.getDifensoreForm().getIdDifensore());
		if ((queryResults == null) || queryResults.isEmpty()) {
			return dsf;
		}
		Map<String, Object> row = (Map<String, Object>) queryResults.get(0);
		TRelProcSogintAvv relProcSogAvv = (TRelProcSogintAvv) row.get("rel");
		TAvvocati avvocato = (TAvvocati) row.get("avv");
		mapperBean.map(relProcSogAvv, dsf);
		mapperBean.map(avvocato, dsf);
		return dsf;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DifensoreSoggettoForm caricaDifensoreAssociatoIntestatario(DifensoreSoggettoForm dsf) {

		List queryResults = null;
		queryResults = procedimentoDao.caricaDifensoriAssociatiIntestatari(dsf.getSoggettoForm().getIdProcedimento(),
				dsf.getSoggettoForm().getIdUfficio(), dsf.getSoggettoForm().getIdSoggetto(),
				dsf.getDifensoreForm().getIdDifensore());
		if ((queryResults == null) || queryResults.isEmpty()) {
			return dsf;
		}
		Map<String, Object> row = (Map<String, Object>) queryResults.get(0);
		TRelProcIntestatariAvv relProcIntestatariAvv = (TRelProcIntestatariAvv) row.get("rel");
		TAvvocati avvocato = (TAvvocati) row.get("avv");
		mapperBean.map(relProcIntestatariAvv, dsf);
		mapperBean.map(avvocato, dsf);
		return dsf;
	}

	public EredeConviventeForm caricaEredeDelDefunto(SoggettoForm soggettoForm) {

		List<TRelSogintErediConv> listaEredi = null;
		listaEredi = procedimentoDao.listaErediOppureConviventiDelSoggetto(soggettoForm.getIdSoggetto(),
				soggettoForm.getIdProcedimento(), soggettoForm.getIdUfficio(), "E");
		Iterator<TRelSogintErediConv> iter = listaEredi.iterator();
		EredeConviventeForm ecf = new EredeConviventeForm();
		while (iter.hasNext()) {
			TRelSogintErediConv currentErede = iter.next();
			if (currentErede.getId().getCodiKIdPrg() == soggettoForm.getEredeConviventeForm().getId()) {
				mapperBean.map(currentErede, ecf);
				return ecf;
			}
		}
		return ecf;
	}

	public SoggettoForm caricaValoriDettaglioSoggettoFisico(long idSoggetto, long idVersione, int idUffOwner) {

		SoggettoForm soggForm = new SoggettoForm();
		soggForm.setIdSoggetto(idSoggetto);
		soggForm.setIdVersione(idVersione);
		soggForm.setIdUfficio(idUffOwner);

		return caricaValoriDettaglioSoggettoFisico(soggForm);
	}

	public SoggettoForm caricaValoriDettaglioSoggettoFisico(SoggettoForm soggetto) {

		TPersonaFisicaId id = new TPersonaFisicaId();
		id.setCodiKIdUffOwner(soggetto.getIdUfficio());
		id.setCodiKIdPersonaFis(soggetto.getIdSoggetto());
		id.setCodiKIdVer((short) soggetto.getIdVersione().longValue());
		DettaglioPersonaFisicaDto personaFisicaDto = procedimentoDao.getDettaglioPersonaFisica(id);
		mapperBean.map(personaFisicaDto, soggetto);
		return soggetto;

	}

	public SoggettoForm caricaValoriDettaglioSoggettoFisicoVersionamento(SoggettoForm soggetto) {

		TPersonaFisicaId id = new TPersonaFisicaId();
		id.setCodiKIdUffOwner(soggetto.getIdUfficio());
		id.setCodiKIdPersonaFis(soggetto.getIdSoggetto());
		id.setCodiKIdVer((short) soggetto.getIdVersione().longValue());
		DettaglioPersonaFisicaDto personaFisicaDto = procedimentoDao.getDettaglioPersonaFisica(id);
		SoggettoForm soggettoReturn = new SoggettoForm();
		mapperBean.map(personaFisicaDto, soggettoReturn);
		return soggettoReturn;
	}

	public SoggettoForm caricaValoriDettaglioSoggettoGiuridicoVersionamento(SoggettoForm soggetto) {

		TPersonaGiurId id = new TPersonaGiurId();
		id.setCodiKIdUffOwner(soggetto.getIdUfficio());
		id.setCodiKIdPersGiur(soggetto.getIdSoggetto());
		id.setCodiKIdVer((short) soggetto.getIdVersione().longValue());
		Map<String, Object> risultato = procedimentoDao.getDettaglioPersonaGiur(id);
		TPersonaGiur personaG = (TPersonaGiur) risultato.get("p_giur");
		TTpTipoSocieta tiposoc = (TTpTipoSocieta) risultato.get("tiposoc");
		TAnStato stato = (TAnStato) risultato.get("stato");
		TAnComuni comuni = (TAnComuni) risultato.get("comuni");
		TAnProvincia provincia = (TAnProvincia) risultato.get("provincia");
		TTpTipoStrada strada = (TTpTipoStrada) risultato.get("strada");
		TTpSettoreAtt settAtt = (TTpSettoreAtt) risultato.get("settAtt");

		SoggettoForm soggettoReturn = new SoggettoForm();

		if (personaG != null) {
			mapperBean.map(personaG, soggettoReturn);
		}
		if (tiposoc != null) {
			mapperBean.map(tiposoc, soggettoReturn);
		}
		if (stato != null) {
			mapperBean.map(stato, soggettoReturn);
		}
		if (comuni != null) {
			mapperBean.map(comuni, soggettoReturn);
		}
		if (provincia != null) {
			mapperBean.map(provincia, soggettoReturn);
		}
		if (strada != null) {
			mapperBean.map(strada, soggettoReturn);
		}
		if (settAtt != null) {
			mapperBean.map(settAtt, soggettoReturn);
		}
		return soggettoReturn;
	}

	public SoggettoForm caricaValoriDettaglioSoggettoFisicoProcedimento(SoggettoForm soggetto) {

		TPersonaFisicaId id = new TPersonaFisicaId();
		id.setCodiKIdUffOwner(soggetto.getIdUfficio());
		id.setCodiKIdPersonaFis(soggetto.getIdSoggetto());
		id.setCodiKIdVer((short) soggetto.getIdVersione().longValue());
		DettaglioPersonaFisicaProcDto personaFisicaProcDto = procedimentoDao.getDettaglioPersonaFisicaProc(id,
				soggetto.getIdProcedimento());
		if (personaFisicaProcDto != null) {
			mapperBean.map(personaFisicaProcDto, soggetto);
		}
		return soggetto;
	}

	public SoggettoForm caricaValoriDettaglioSoggettoGiuridico(long idSoggetto, long idVersione, int idUffOwner) {

		SoggettoForm soggForm = new SoggettoForm();
		soggForm.setIdSoggetto(idSoggetto);
		soggForm.setIdVersione(idVersione);
		soggForm.setIdUfficio(idUffOwner);

		return caricaValoriDettaglioSoggettoGiuridico(soggForm);

	}

	public SoggettoForm caricaValoriDettaglioSoggettoGiuridico(SoggettoForm soggetto) {

		TPersonaGiurId id = new TPersonaGiurId();
		id.setCodiKIdUffOwner(soggetto.getIdUfficio());
		id.setCodiKIdPersGiur(soggetto.getIdSoggetto());
		id.setCodiKIdVer((short) soggetto.getIdVersione().longValue());
		Map<String, Object> risultato = procedimentoDao.getDettaglioPersonaGiur(id);
		TPersonaGiur personaG = (TPersonaGiur) risultato.get("p_giur");
		TTpTipoSocieta tiposoc = (TTpTipoSocieta) risultato.get("tiposoc");
		TAnStato stato = (TAnStato) risultato.get("stato");
		TAnComuni comuni = (TAnComuni) risultato.get("comuni");
		TAnProvincia provincia = (TAnProvincia) risultato.get("provincia");
		TTpTipoStrada strada = (TTpTipoStrada) risultato.get("strada");
		TTpSettoreAtt settAtt = (TTpSettoreAtt) risultato.get("settAtt");
		if (personaG != null) {
			mapperBean.map(personaG, soggetto);
		}
		if (tiposoc != null) {
			mapperBean.map(tiposoc, soggetto);
		}
		if (stato != null) {
			mapperBean.map(stato, soggetto);
		}
		if (comuni != null) {
			mapperBean.map(comuni, soggetto);
		}
		if (provincia != null) {
			mapperBean.map(provincia, soggetto);
		}
		if (strada != null) {
			mapperBean.map(strada, soggetto);
		}
		if (settAtt != null) {
			mapperBean.map(settAtt, soggetto);
		}
		return soggetto;
	}

	public SoggettoForm caricaValoriDettaglioSoggettoGiuridicoProcedimento(SoggettoForm soggetto) {

		TPersonaGiurId id = new TPersonaGiurId();
		id.setCodiKIdUffOwner(soggetto.getIdUfficio());
		id.setCodiKIdPersGiur(soggetto.getIdSoggetto());
		id.setCodiKIdVer((short) soggetto.getIdVersione().longValue());
		DettaglioPersonaGiurProcDto personaGiurProcDto = procedimentoDao.getDettaglioPersonaGiurProc(id,
				soggetto.getIdProcedimento());
		if (personaGiurProcDto != null)
			mapperBean.map(personaGiurProcDto, soggetto);
		return soggetto;
	}

	public SoggettoForm caricaValoriGratuitoPatrocinioDelSoggetto(SoggettoForm soggettoForm) {

		TRelProcSogint trelprosog = procedimentoDao.getTrelProcSogintByIdsoggIdProcIdUff(
				soggettoForm.getIdProcedimento(), soggettoForm.getIdSoggetto(), soggettoForm.getIdUfficio(), 0L);
		if (trelprosog != null) {
			mapperBean.map(trelprosog, soggettoForm);
		}
		if (soggettoForm.getAnnoRegistroGratuitoPatrocinio() == null) {
			soggettoForm.setGratuitoPatrocinio(false);
		} else {
			soggettoForm.setGratuitoPatrocinio(true);
		}
		return soggettoForm;
	}

	/*
	 * public DifensoreForm dettaglioDifensoreReginde(String codiceFiscale) {
	 * 
	 * DifensoreForm difensore = new DifensoreForm(); Soggetto soggetto =
	 * regindeService.dettagliSoggettoPerCodice(codiceFiscale); // Dati personali if
	 * (soggetto.getSoggetto().getIdSoggetto() != null) {
	 * difensore.setIdReginde(soggetto.getSoggetto().getIdSoggetto()); } if
	 * (soggetto.getSoggetto().getCognome() != null) {
	 * difensore.setCognome(soggetto.getSoggetto().getCognome()); } if
	 * (soggetto.getSoggetto().getNome() != null) {
	 * difensore.setNome(soggetto.getSoggetto().getNome()); } if
	 * (soggetto.getSoggetto().getLuogoNascita() != null) {
	 * difensore.setLuogoNascita(soggetto.getSoggetto().getLuogoNascita()); } if
	 * (soggetto.getSoggetto().getProvNascita() != null) {
	 * difensore.setProvinciaNascita(soggetto.getSoggetto().getProvNascita()); } if
	 * (soggetto.getSoggetto().getPec() != null) {
	 * difensore.setPec(soggetto.getSoggetto().getPec()); } if (soggetto.getStatus()
	 * != null) { difensore.setStatus(soggetto.getStatus().getDescr()); } //
	 * Controllo indirizzi if (soggetto.getIndirizzi() != null) { List<Indirizzi>
	 * lstIndirizzi = soggetto.getIndirizzi(); Iterator<Indirizzi> itrIndirizzi =
	 * lstIndirizzi.iterator(); while (itrIndirizzi.hasNext()) { Indirizzi indirizzo
	 * = itrIndirizzi.next(); // Dati domicilio legale if
	 * (indirizzo.getTpIndirizzo().equalsIgnoreCase("D")) { if (indirizzo.getCap()
	 * != null) { difensore.setCapDomicilioLegale(indirizzo.getCap()); } if
	 * (indirizzo.getComune() != null) {
	 * difensore.setComuneDomicilioLegale(indirizzo.getComune()); } if
	 * (indirizzo.getEmail() != null) {
	 * difensore.setEmailDomicilioLegale(indirizzo.getEmail()); } if
	 * (indirizzo.getFax() != null) {
	 * difensore.setFaxDomicilioLegale(indirizzo.getFax()); } if
	 * (indirizzo.getIndirizzo() != null) {
	 * difensore.setIndirizzoDomicilioLegale(indirizzo.getIndirizzo()); } if
	 * (indirizzo.getProv() != null) {
	 * difensore.setProvinciaDomicilioLegale(indirizzo.getProv()); } if
	 * (indirizzo.getTelefono() != null) {
	 * difensore.setTelefonoDomicilioLegale(indirizzo.getTelefono()); } } // Dati
	 * residenza if (indirizzo.getTpIndirizzo().equalsIgnoreCase("R")) { if
	 * (indirizzo.getCap() != null) { difensore.setCapResidenza(indirizzo.getCap());
	 * } if (indirizzo.getComune() != null) {
	 * difensore.setComuneResidenza(indirizzo.getComune()); } if
	 * (indirizzo.getEmail() != null) {
	 * difensore.setEmailResidenza(indirizzo.getEmail()); } if (indirizzo.getFax()
	 * != null) { difensore.setFaxResidenza(indirizzo.getFax()); } if
	 * (indirizzo.getIndirizzo() != null) {
	 * difensore.setIndirizzoResidenza(indirizzo.getIndirizzo()); } if
	 * (indirizzo.getProv() != null) {
	 * difensore.setProvinciaResidenza(indirizzo.getProv()); } if
	 * (indirizzo.getTelefono() != null) {
	 * difensore.setTelefonoResidenza(indirizzo.getTelefono()); } } } } return
	 * difensore; }
	 */

	public AttoForm dettaglioPropostaAtto(long idUfficio, long idProcedimento, long idAtto, boolean controllaMisure) {

		AttoForm attoForm = new AttoForm();
		// sono i campi dell'istanza
		ImpugnazioneForm impugnazioneForm = new ImpugnazioneForm();
		Map map = procedimentoDao.dettaglioPropostaAtto(idUfficio, idProcedimento, idAtto);
		if (map != null) {
			TAtto atto = (TAtto) map.get("atto");
			TTpStatoAtto statoAtto = (TTpStatoAtto) map.get("stato_atto");
			TIstanza istanza = (TIstanza) map.get("istanza");
			mapperBean.map(atto, attoForm);
			mapperBean.map(statoAtto, attoForm);
			mapperBean.map(istanza, attoForm);
			mapperBean.map(istanza, impugnazioneForm);
			// se è un istanza camerale
			if (atto.getTTpTipoAtto().getCodiKIdTipoAtto() == 5) {
				impugnazioneForm = getTRelSoggIstByIdIStanzaIdOwner(idAtto, Integer.parseInt("" + idUfficio),
						impugnazioneForm);
			}
			attoForm.setImpugnazione(impugnazioneForm);
		}
		// se controllaMisure è true, se non esistono misure restituisco
		// attoForm non valorizzato
		if (controllaMisure && attoForm != null && attoForm.getCodAtto() > 0
				&& (!attoForm.getTipoAtto().getId().equals("5"))) {
			if (!commonDao.existMisurePersonali(idProcedimento, attoForm.getCodAtto())
					&& !commonDao.existMisurePatrimoniali(idProcedimento, attoForm.getCodAtto())) {
				attoForm = new AttoForm();
			}
		}
		return attoForm;
	}

	public void eliminaAssociazioneMagistratoProcedimento(MagistratoForm magistrato, long idProcedimento,
			Integer idUfficioUtente) {

		procedimentoDao.eliminaAssociazioneMagistratoProcedimento(magistrato.getId(), idProcedimento, idUfficioUtente);
	}

	public void eliminaRelazioniAttoMisuraPatrimoniale(List<Long> listaIdCheckati, long idProcedimento,
			int idUfficioUtente, long idAtto, long idSoggettoInteressato, String codUtente) {

		for (long idBene : listaIdCheckati) {
			procedimentoDao.eliminaRelazioniAttoMisuraPatrimoniale(idBene, idProcedimento, idUfficioUtente, idAtto,
					idSoggettoInteressato);
		}
		aggiornaTipoProcPrevProc(idProcedimento, idUfficioUtente, codUtente);
	}

	public List<TreeNode> getAlberaturaAtti(long idProcedimento, long tipoUfficio) {

		ProcedimentoTree tree = procedimentoDao.costruisciAlberaturaAtti(tipoUfficio);
		riempiAlbero(tree, idProcedimento, tipoUfficio);
		return tree.toTreeNode();
	}

	public List<TreeNode> getAlberaturaAtti(long idProcedimento, long tipoUfficio, Long idTipoFascicolo,
			Long idTipoProcedimento) {

		ProcedimentoTree tree = procedimentoDao.costruisciAlberaturaAtti(tipoUfficio, idTipoFascicolo,
				idTipoProcedimento);
		if (idTipoFascicolo.equals(ProcedimentoServiceImpl.ID_TIPO_FASCICOLO_AGGRAVAMENTO)) {
			appendiFoglieAggravamento(tree, idProcedimento, tipoUfficio);

		} else if (idTipoFascicolo.equals(Long.valueOf(Constants.TIPO_FASCICOLO_CAMERA_DI_CONSIGLIO))) {
			appendiFoglieCamereConsiglio(tree, idProcedimento, tipoUfficio);

		} else if (idTipoFascicolo.equals(ProcedimentoServiceImpl.ID_TIPO_FASCICOLO_OPPOSIZIONE_RESTITUZIONE)) {
			appendiFoglieOpposizioneRestituzione(tree, idProcedimento, tipoUfficio);

		} else if (idTipoFascicolo.equals(Long.valueOf(Constants.TIPOPROC_ACCERTAMENTO))) {
			appendiFoglieAccertamento(tree, idProcedimento, tipoUfficio);

		} else if (idTipoFascicolo.equals(Long.valueOf(Constants.TIPOPROC_RIESAME_PROVV_QUESTURA))) {
			appendiFoglieOpposizione(tree, idProcedimento, tipoUfficio);

		} else if (idTipoFascicolo
				.equals(Long.valueOf(ProcedimentoServiceImpl.ID_TIPO_FASCICOLO_REVOCAZIONE_CONFISCA))) {
			appendiFoglieRevocazione(tree, idProcedimento, tipoUfficio);

		} else if (idTipoFascicolo
				.equals(Long.valueOf(ProcedimentoServiceImpl.ID_TIPO_FASCICOLO_OPPOSIZIONE_DIVIETI_DEL_QUESTORE))) {
			appendiFoglieOpposizioneDivietiQuestore(tree, idProcedimento, tipoUfficio);

		} else {
			riempiAlbero(tree, idProcedimento, tipoUfficio);
		}
		// NOTIFICHE - APPENDE DELLE RICEVUTE DI NOTIFICA
		List<TreeNode> nodiSenzaRicevute = tree.toTreeNode();
		appendiRicevute(tree, nodiSenzaRicevute);
		return tree.toTreeNode();
	}

	/***
	 * Appende le ricevuto per ogni atto notificato
	 * 
	 * @param tree
	 * @param nodiSenzaRicevute
	 */
	private void appendiRicevute(ProcedimentoTree tree, List<TreeNode> nodiSenzaRicevute) {
		for (TreeNode treeNodeSenzaRicevute : nodiSenzaRicevute) {
			// System.out.println("Node id: " + treeNodeSenzaRicevute.getNodeId() );

			if (treeNodeSenzaRicevute.getIdAtto() != 0) {
				int idUfficio = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
				List<VRicevuta> ricevute = notificheDao
						.getListaAttiRicevutoDaAttoNotificato(treeNodeSenzaRicevute.getIdAtto(), idUfficio);
				for (VRicevuta vRicevuta : ricevute) {
					// Caricare tutte le ricevute dell'atto
					Node nodoAttoNotificato = treeNodeSenzaRicevute.getNode();
					JsNodeInfo nodeRicevuta = new JsNodeInfo();
					nodeRicevuta.setLeafIcon("ricevuta.gif");
					nodeRicevuta.setIdAtto(vRicevuta.getIdAtto());
					nodeRicevuta.setDeletable(false);
					nodeRicevuta.setLabel(vRicevuta.getDescCatAtto() + " / Dest: " + vRicevuta.getDescDestinatario());
					nodeRicevuta.setAttachment(true);
					nodeRicevuta.setUrl("javascript:caricaAtto(" + "\\\'" + vRicevuta.getIdAtto() + "\\\'" + ","
							+ "\\\'" + vRicevuta.getIdTipoAtto() + "\\\'" + ")");
					nodoAttoNotificato.addChild(new Node(nodeRicevuta));
				}
				List<VRappTrasm> rappTrasm = notificheDao
						.getRappTrasmDaAttoNotificato(treeNodeSenzaRicevute.getIdAtto(), idUfficio);
				for (VRappTrasm vRappTrasm : rappTrasm) {
					// Caricare tutte le ricevute dell'atto
					Node nodoAttoNotificato = treeNodeSenzaRicevute.getNode();
					JsNodeInfo nodeRicevuta = new JsNodeInfo();
					nodeRicevuta.setLeafIcon("rapptrasm.png");
					nodeRicevuta.setIdAtto(vRappTrasm.getIdAtto());
					nodeRicevuta.setDeletable(false);
					nodeRicevuta.setLabel(vRappTrasm.getDescCatAtto() + " / Dest: " + vRappTrasm.getDescDestinatario());
					nodeRicevuta.setAttachment(true);
					nodeRicevuta.setUrl("javascript:caricaAtto(" + "\\\'" + vRappTrasm.getIdAtto() + "\\\'" + ","
							+ "\\\'" + vRappTrasm.getIdTipoAtto() + "\\\'" + ")");
					nodoAttoNotificato.addChild(new Node(nodeRicevuta));
				}

			}
		}
		tree.complete();
	}

	public List<ComboForm> getAllAttoByType(String type) {
		Integer typeLong = Integer.parseInt(type);
		List<TTpCatAtto> atti = procedimentoDao.getAllAttoByType(typeLong);
		List<ComboForm> risultati = new ArrayList<ComboForm>();
		Iterator<TTpCatAtto> iter = atti.iterator();
		while (iter.hasNext()) {
			TTpCatAtto current = iter.next();
			ComboForm cForm = new ComboForm();
			mapperBean.map(current, cForm);
			risultati.add(cForm);
		}
		return risultati;
	}

	public List<ComboForm> getAllFonte() {

		List<TTpCatAtto> fonti = procedimentoDao.getAllFonte();
		List<ComboForm> risultati = new ArrayList<ComboForm>();
		Iterator<TTpCatAtto> iter = fonti.iterator();
		while (iter.hasNext()) {
			TTpCatAtto fonte = iter.next();
			ComboForm cForm = new ComboForm();
			mapperBean.map(fonte, cForm);
			risultati.add(cForm);
		}
		return risultati;
	}

	public MagistratoForm getDettaglioMagistratoProcedimento(Long idMagistrato, long idProcedimento,
			int idUfficioUtente) {

		Map<String, Object> queryResults = null;
		queryResults = procedimentoDao.dettaglioMagistratoPerProcedimento(idUfficioUtente, idProcedimento,
				idMagistrato);
		MagistratoForm form = new MagistratoForm();
		TRelMagProc relProcedimento = (TRelMagProc) queryResults.get("rel");
		TAnMagistrati magistrato = (TAnMagistrati) queryResults.get("mag");
		mapperBean.map(relProcedimento, form);
		mapperBean.map(magistrato, form);
		return form;
	}

	public ProcedimentoForm getDettaglioProcedimento(long idProcedimento) {

		TProcedimento procedimento = new TProcedimento();
		ProcedimentoForm procForm = new ProcedimentoForm();
		procedimento.setCodiKIdProc(idProcedimento);
		Map<String, Object> queryResult = procedimentoDao.getDettaglioProcedimento(procedimento);

		TProcedimento proc = (TProcedimento) queryResult.get("proced");
		TAnProcedente procedente = (TAnProcedente) queryResult.get("procedente");
		TTpStatoProc statoProc = (TTpStatoProc) queryResult.get("statoProc");
		TTpTipoProc tipoProc = (TTpTipoProc) queryResult.get("tipoProc");
		TTpTipoFascicolo tipoFasc = (TTpTipoFascicolo) queryResult.get("tipoFasc");

		if (proc != null) {
			mapperBean.map(proc, procForm);

		}
		if (procedente != null) {
			mapperBean.map(procedente, procForm);
		}
		if (statoProc != null) {
			mapperBean.map(statoProc, procForm);
		}
		if (tipoProc != null) {
			mapperBean.map(tipoProc, procForm);
			if ((tipoProc.getCodiKIdTipoProc() == Integer.parseInt(Constants.TIPOPROC_AGGRAVAMENTO_PERSONALE))
					|| (tipoProc.getCodiKIdTipoProc() == Integer
							.parseInt("" + Constants.TIPOPROC_MODIFICA_PATRIMONIALE_CAM))
					|| (tipoProc.getCodiKIdTipoProc() == Integer
							.parseInt("" + Constants.TIPOPROC_MODIFICA_PERSONALE_CAM))) {
				Long idPadre = getIdPadreAggravamento(idProcedimento);
				procForm.setIdPadre(idPadre);
			}
		}
		if (tipoFasc != null) {
			mapperBean.map(tipoFasc, procForm);
		}
		return procForm;
	}

	public List<ComboForm> getEsiti(String carattereMisura, long idCategoriaAtto, long idTipoUfficio) {

		List<TTpEsito> rawData = procedimentoDao.getEsiti(carattereMisura, idCategoriaAtto, idTipoUfficio);
		List<ComboForm> list = new ArrayList<ComboForm>();
		Iterator<TTpEsito> iter = rawData.iterator();
		while (iter.hasNext()) {
			TTpEsito currentObj = iter.next();
			// Segnalazione EMS-59
			if (!(idCategoriaAtto == 355 && (currentObj.getCodiKIdEsito() == 242)))// ||
																					// currentObj.getCodiKIdEsito()==243)))
			{
				ComboForm currentFrm = new ComboForm();
				mapperBean.map(currentObj, currentFrm);
				list.add(currentFrm);
			}
		}
		return list;
	}

	public TProcedimento getIdProcedimentoDestinatario(long idProcedimento, int idUffOwn, int idTipoFasc,
			int idUfficioArrivo) {

		return procedimentoDao.getIdProcedimentoDestinatario(idProcedimento, idUffOwn, idTipoFasc, idUfficioArrivo);
	}

	public Long getIdPadreAggravamento(long idProcedimento) {

		return procedimentoDao.getIdPadreAggravamento(idProcedimento);
	}

	//P.Q. 04/09/2025 Ticket 20250807017 Inizio
	public Long getIdProcDaRelProcProc(Short annoPadre, Integer numeroPadre, Integer idUfficioUtente) {
	//P.Q. 04/09/2025 Ticket 20250807017 Fine
		
		return procedimentoDao.getIdProcDaRelProcProc(annoPadre, numeroPadre, idUfficioUtente);
	}

	public List<Long> getIdProcByAnnoNumProc(int idUffOwn, short annoProc, long numeProc, int idTipoFasc) {

		return procedimentoDao.getIdProcByAnnoNumProc(idUffOwn, annoProc, numeProc, idTipoFasc);
	}

	public List<ComboForm> getIndirizzoByOrganoAndComune(String idCodiceComune, long idOrgano) {

		List<TAnUfficio> uffici = procedimentoDao.getIndirizzoByOrganoAndComune(idCodiceComune, idOrgano);
		List<ComboForm> risultati = new ArrayList<ComboForm>();
		Iterator<TAnUfficio> iter = uffici.iterator();
		while (iter.hasNext()) {
			TAnUfficio currentOrgano = iter.next();
			ComboForm cForm = new ComboForm();
			mapperBean.map(currentOrgano, cForm);
			risultati.add(cForm);
		}
		return risultati;
	}

	@SuppressWarnings("rawtypes")
	public List<ComboForm> getListaProcedenti(long idUfficio, Long idTipoPrev, Long idTipoFasc) {

		List<TAnProcedente> procedenti = null;
		procedenti = procedimentoDao.getListaProcedenti(idUfficio, idTipoPrev, idTipoFasc);

		List<ComboForm> risultati = new ArrayList<ComboForm>();
		Iterator<TAnProcedente> iter = procedenti.iterator();
		while (iter.hasNext()) {
			Map map = (Map) iter.next();
			TAnProcedente procedente = (TAnProcedente) map.get("procedente");
			ComboForm cForm = new ComboForm();
			mapperBean.map(procedente, cForm);
			risultati.add(cForm);
		}
		return risultati;
	}

	public MagistratoForm getMagistratoById(Long id) {

		if (id == null) {
			return null;
		}
		TAnMagistrati temp = procedimentoDao.getMagistratoById(id);
		MagistratoForm risultato = new MagistratoForm(temp);
		return risultato;
	}

	public List<NumeroRegistroForm> getNumeriRegistro(long idProcedimento) {

		List daoResult = procedimentoDao.getNumeriRegistro(idProcedimento);
		List<NumeroRegistroForm> results = new ArrayList<NumeroRegistroForm>();
		Iterator iter = daoResult.iterator();
		while (iter.hasNext()) {
			NumeroRegistroForm form = new NumeroRegistroForm();
			Object[] current = (Object[]) iter.next();
			TRelProcProc rel = (TRelProcProc) current[0];
			TAnUfficio uff = (TAnUfficio) current[1];
			TTpStatoProc st = (TTpStatoProc) current[2];
			mapperBean.map(rel, form);
			mapperBean.map(uff, form);
			mapperBean.map(st, form);
			results.add(form);
		}
		return results;
	}

	public NumeroRegistroForm getNumeroRegistroPadre(long idProcedimento) {

		List daoResult = procedimentoDao.getNumeroRegistroPadre(idProcedimento);
		NumeroRegistroForm form = null;
		Iterator iter = daoResult.iterator();
		while (iter.hasNext()) {
			form = new NumeroRegistroForm();
			Object[] current = (Object[]) iter.next();
			TRelProcProc rel = (TRelProcProc) current[0];
			TAnUfficio uff = (TAnUfficio) current[1];
			TTpStatoProc st = (TTpStatoProc) current[2];
			mapperBean.map(rel, form);
			mapperBean.map(uff, form);
			mapperBean.map(st, form);
		}
		return form;
	}

	public List<NumeroRegistroForm> getNumeriRegistroAccertamento(long idProcedimento) {

		List daoResult = procedimentoDao.getNumeriRegistroAccertamento(idProcedimento);
		List<NumeroRegistroForm> results = new ArrayList<NumeroRegistroForm>();
		Iterator iter = daoResult.iterator();
		while (iter.hasNext()) {
			NumeroRegistroForm form = new NumeroRegistroForm();
			Object[] current = (Object[]) iter.next();
			TRelProcProc rel = (TRelProcProc) current[0];
			TAnUfficio uff = (TAnUfficio) current[1];
			TTpStatoProc st = (TTpStatoProc) current[2];
			mapperBean.map(rel, form);
			mapperBean.map(uff, form);
			mapperBean.map(st, form);
			results.add(form);
		}
		return results;
	}

	public List<ComboForm> getOrgani(String tipo) {

		List<TTpTipoUfficio> organi = procedimentoDao.getOrgani(tipo);
		List<ComboForm> risultati = new ArrayList<ComboForm>();
		Iterator<TTpTipoUfficio> iter = organi.iterator();
		while (iter.hasNext()) {
			TTpTipoUfficio currentOrgano = iter.next();
			ComboForm cForm = new ComboForm();
			mapperBean.map(currentOrgano, cForm);
			risultati.add(cForm);
		}
		return risultati;
	}

	public SoggettoForm getPersonaById(long identificativoSoggettoProcedimento, Integer idUfficioUtente) {
		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO)))
				.getIdProcedimento();
		return getPersonaById(identificativoSoggettoProcedimento, idUfficioUtente, idProcedimento);
	}

	public SoggettoForm getPersonaById(long identificativoSoggettoProcedimento, Integer idUfficioUtente,
			Long idProcedimento) {

		List<TSoggetto> persone = procedimentoDao.getPersonePiuVersioni(identificativoSoggettoProcedimento,
				idUfficioUtente);
		if ((persone == null) || persone.isEmpty()) {
			return null;
		}
		TSoggetto persona = persone.get(0);
		SoggettoForm soggetto = new SoggettoForm();
		mapperBean.map(persona, soggetto);

		TRelProcSogint rel = procedimentoDao.getTrelProcSogintByIdsoggIdProcIdUff(idProcedimento,
				identificativoSoggettoProcedimento, idUfficioUtente, (long) 0);
		if (rel != null) {
			soggetto.setIdVersione((long) rel.getTRelProcSogintVer().getId().getCodiKIdVer());
		}

		if (soggetto.getTipoSoggetto().equalsIgnoreCase("F")) {
			return caricaValoriDettaglioSoggettoFisico(soggetto);
		}
		return caricaValoriDettaglioSoggettoGiuridico(soggetto);
	}

	public SoggettoForm getPersonaById(long identificativoSoggettoProcedimento, long idProcedimento,
			Integer idUfficioUtente) {

		List<TSoggetto> persone = procedimentoDao.getPersonePiuVersioni(identificativoSoggettoProcedimento,
				idUfficioUtente);
		if ((persone == null) || persone.isEmpty()) {
			return null;
		}
		TSoggetto persona = persone.get(0);
		SoggettoForm soggetto = new SoggettoForm();
		mapperBean.map(persona, soggetto);

		TRelProcSogint rel = procedimentoDao.getTrelProcSogintByIdsoggIdProcIdUff(idProcedimento,
				identificativoSoggettoProcedimento, idUfficioUtente, (long) 0);
		if (rel != null) {
			soggetto.setIdVersione((long) rel.getTRelProcSogintVer().getId().getCodiKIdVer());
		}

		if (soggetto.getTipoSoggetto().equalsIgnoreCase("F")) {
			return caricaValoriDettaglioSoggettoFisico(soggetto);
		}
		return caricaValoriDettaglioSoggettoGiuridico(soggetto);
	}

	public TRelBeneAtto getRelazioneBeneAttoByAttoAndBene(long idBene, long idProcedimento, Integer idUfficioUtente,
			long idAtto, long idSoggetto, long tipoInt) {

		return beneDao.getRelazioneBeneAttoByAttoAndBene(idBene, idProcedimento, idUfficioUtente, idAtto, idSoggetto,
				tipoInt);
	}

	public List<SoggettoForm> getSoggettiInteressatiAssociati(long idUfficio, long idProcedimento, long idFonte) {

		List<SogIntDettaglioProcedimentoDto> soggetti = procedimentoDao.getSoggettiInt_Associati(idUfficio,
				idProcedimento, idFonte);
		List<SoggettoForm> risultati = new ArrayList<SoggettoForm>();
		Iterator<SogIntDettaglioProcedimentoDto> iter = soggetti.iterator();
		while (iter.hasNext()) {
			SogIntDettaglioProcedimentoDto currentDto = iter.next();
			SoggettoForm sForm = new SoggettoForm();
			mapperBean.map(currentDto, sForm);
			risultati.add(sForm);
		}
		return risultati;
	}

	public List<SoggettoForm> getSoggettiInteressatiNonAssociati(long idUfficio, long idProcedimento) {

		List<SogIntDettaglioProcedimentoDto> soggetti = procedimentoDao.getSoggettiInt_NonAssociati(idUfficio,
				idProcedimento);
		List<SoggettoForm> risultati = new ArrayList<SoggettoForm>();
		Iterator<SogIntDettaglioProcedimentoDto> iter = soggetti.iterator();
		while (iter.hasNext()) {
			SogIntDettaglioProcedimentoDto currentDto = iter.next();
			SoggettoForm sForm = new SoggettoForm();
			mapperBean.map(currentDto, sForm);
			risultati.add(sForm);
		}
		return risultati;
	}

	public SoggettoForm getSogIntById(long idSogInt, long idUfficio, long idProcedimento) {

		SogIntDettaglioProcedimentoDto sogInt = procedimentoDao.getSogIntById(idSogInt, idUfficio, idProcedimento);
		SoggettoForm sogIntForm = new SoggettoForm();
		mapperBean.map(sogInt, sogIntForm);
		return sogIntForm;
	}

	public SoggettoForm getSogIntById(long idSogInt, long idUfficio, long idProcedimento, String ruolo) {

		SogIntDettaglioProcedimentoDto sogInt = procedimentoDao.getSogIntById(idSogInt, idUfficio, idProcedimento,
				ruolo);
		SoggettoForm sogIntForm = new SoggettoForm();
		mapperBean.map(sogInt, sogIntForm);
		return sogIntForm;
	}

	public List<SoggettoForm> getSogIntSenzaDifensore(long idProcedimento, long idUfficio) {

		List<ContaDifensoriSogIntDto> soggetti = procedimentoDao.getSogIntSenzaDifensore(idProcedimento, idUfficio);
		List<SoggettoForm> risultati = new ArrayList<SoggettoForm>();
		Iterator<ContaDifensoriSogIntDto> iter = soggetti.iterator();
		while (iter.hasNext()) {
			ContaDifensoriSogIntDto currentDto = iter.next();
			SoggettoForm sForm = getSogIntById(currentDto.getIDSOGGINT().longValue(), idUfficio, idProcedimento);
			risultati.add(sForm);
		}
		return risultati;
	}

	public void inserisciEredeDelDefunto(SoggettoForm soggettoForm, String codiceUtente) {

		TRelSogintErediConv trel = new TRelSogintErediConv();
		EredeConviventeForm ecf = soggettoForm.getEredeConviventeForm();
		mapperBean.map(ecf, trel);
		if ((ecf.getComuneNascitaErCon().getId() == null) && (ecf.getComuneNascitaErCon().getDescrizione() != null)) {
			// è presente un comune estero
			trel.setDescComEstero(ecf.getComuneNascitaErCon().getDescrizione());
		}
		procedimentoDao.inserisciEredeOppureConviventeDelSoggetto(soggettoForm.getIdSoggetto(),
				soggettoForm.getIdProcedimento(), soggettoForm.getIdUfficio(), codiceUtente, "E", trel);
	}

	public long inserisciFonte(long idUfficio, String codiceUtente, long idProcedimento, long idStatoProcedimento,
			AttoForm form) {

		TAtto atto = new TAtto();
		TIstanza istanza = new TIstanza();
		// copio dataRicezione in dataEmissione
		form.setDataEmissione(form.getDataInformativa());
		mapperBean.map(form, atto);
		mapperBean.map(form, istanza);

		Utility.popolaDocumento(form, atto);

		long idFonte = procedimentoDao.inserisciFonte(idUfficio, codiceUtente, idProcedimento, idStatoProcedimento,
				atto, istanza);
		if ((form.getListaIdCheckbox() != null) && (form.getListaIdCheckbox().size() > 0)) {
			Iterator<Long> iter = form.getListaIdCheckbox().iterator();
			while (iter.hasNext()) {
				Long current = iter.next();
				TRelProcSogint direttoInteressato = new TRelProcSogint();
				// commentato da sabino
				// direttoInteressato.setCodiFkIdFonte(idFonte);
				TAtto tAtto = new TAtto();
				TAttoId attoId = new TAttoId();
				attoId.setCodiKIdAtto(idFonte);
				attoId.setCodiKIdUffOwner((int) idUfficio);
				tAtto.setId(attoId);
				direttoInteressato.setTAtto(tAtto);
				TRelProcSogintId id = new TRelProcSogintId((int) idUfficio, idProcedimento, current);
				direttoInteressato.setId(id);
				procedimentoDao.associaSoggettoDirettoInteressato(direttoInteressato);
			}
		}

		return idFonte;
	}

	public long inserisciArchivizione(long idUfficio, String codiceUtente, long idProcedimento,
			long idStatoProcedimento, AttoForm form) {

		TAtto atto = new TAtto();
		TIstanza istanza = new TIstanza();
		// copio dataRicezione in dataEmissione
		form.setDataEmissione(form.getDataInformativa());
		mapperBean.map(form, atto);
		mapperBean.map(form, istanza);

		Utility.popolaDocumento(form, atto);
		long idArchiviazione = procedimentoDao.inserisciArchiviazione(idUfficio, codiceUtente, idProcedimento,
				idStatoProcedimento, atto, istanza);
		return idArchiviazione;
	}

	public Long inserisciModificaDifensoreAssociatoSoggetto(DifensoreSoggettoForm dsf, String codiceUtente) {

		TAvvocati avvocato = new TAvvocati();
		mapperBean.map(dsf, avvocato);
		String tipoNomina = null;
		if ((dsf.getTipoNomina() != null) && (dsf.getTipoNomina().getId() != null)) {
			tipoNomina = dsf.getTipoNomina().getId();
		}
		Long value = procedimentoDao.inserisciModificaDifensoreAssociato(dsf.getSoggettoForm().getIdProcedimento(),
				dsf.getSoggettoForm().getIdUfficio(), dsf.getSoggettoForm().getIdSoggetto(),
				dsf.getDifensoreForm().getIdDifensore(), avvocato, dsf.getDataNomina(), dsf.getDataRevoca(), tipoNomina,
				codiceUtente);
		return value;
	}

	public Long inserisciModificaDifensoreAssociatoIntestatario(DifensoreSoggettoForm dsf, String codiceUtente) {

		TAvvocati avvocato = new TAvvocati();
		mapperBean.map(dsf, avvocato);
		String tipoNomina = null;
		if ((dsf.getTipoNomina() != null) && (dsf.getTipoNomina().getId() != null)) {
			tipoNomina = dsf.getTipoNomina().getId();
		}
		Long value = procedimentoDao.inserisciModificaDifensoreIntestatario(dsf.getSoggettoForm().getIdProcedimento(),
				dsf.getSoggettoForm().getIdUfficio(), dsf.getSoggettoForm().getIdSoggetto(),
				dsf.getDifensoreForm().getIdDifensore(), avvocato, dsf.getDataNomina(), dsf.getDataRevoca(), tipoNomina,
				codiceUtente);
		return value;
	}

	@SuppressWarnings("null")
	public List<AssociaPersonaDto> inserisciProcedimento(int idUffOwn, String codUtente, ProcedimentoForm procForm,
			long statoProc, List<SoggettoForm> listaSoggettiForm, int idTipoFascicolo) {

		TProcedimento proc = new TProcedimento();
		mapperBean.map(procForm, proc);
		TTpStatoProc tStatoProc = new TTpStatoProc((int) statoProc);
		proc.setTTpStatoProc(tStatoProc);
		List<AssociaPersonaDto> listaSoggetti = new ArrayList<AssociaPersonaDto>();
		Date now = Calendar.getInstance().getTime();
		Long idProc = procedimentoDao.inserisciProcedimento(idUffOwn, codUtente, proc, null, idTipoFascicolo);
		for (int i = 0; i < listaSoggettiForm.size(); i++) {
			SoggettoForm soggettoForm = listaSoggettiForm.get(i);
			TPersonaFisica persona = new TPersonaFisica();
			TPersonaGiur personaGiur = new TPersonaGiur();
			TTpTipoSocieta tipoSoc = new TTpTipoSocieta();
			TSoggetto soggInt = new TSoggetto();
			TRelProcSogint relProcSoggInt = new TRelProcSogint();
			mapperBean.map(soggettoForm, persona);
			mapperBean.map(soggettoForm, personaGiur);
			mapperBean.map(soggettoForm, tipoSoc);
			mapperBean.map(soggettoForm, soggInt);
			mapperBean.map(soggettoForm, relProcSoggInt);
			AssociaPersonaDto associaSoggetto = new AssociaPersonaDto();
			associaSoggetto.setPersFis(persona);
			associaSoggetto.setPersonaGiur(personaGiur);
			associaSoggetto.getPersonaGiur().setTTpTipoSocieta(tipoSoc);
			associaSoggetto.setSoggInt(soggInt);
			associaSoggetto.setRelProcSoggInt(relProcSoggInt);
			AssociaPersonaDto temp = null;
			if ((associaSoggetto.getPersFis().getId() != null)
					&& (associaSoggetto.getPersFis().getId().getCodiKIdPersonaFis() > 0)
					&& (associaSoggetto.getPersFis().getDescCognome() != null)) {
				temp = procedimentoDao.associaPersonaFisica(idUffOwn, codUtente, idProc, associaSoggetto, now);
			} else if ((associaSoggetto.getPersonaGiur().getId() != null)
					&& (associaSoggetto.getPersonaGiur().getId().getCodiKIdPersGiur() > 0)
					&& (associaSoggetto.getPersonaGiur().getDescRagioneSoc() != null)) {
				temp = procedimentoDao.associaPersonaGiuridica(idUffOwn, codUtente, idProc, associaSoggetto, now);
			} else if (associaSoggetto.getPersFis().getDescCognome() != null) {
				temp = procedimentoDao.associaNuovaPersonaFisica(idUffOwn, codUtente, idProc, associaSoggetto, now);
			} else if (associaSoggetto.getPersonaGiur().getDescRagioneSoc() != null) {
				temp = procedimentoDao.associaNuovaPersonaGiuridica(idUffOwn, codUtente, idProc, associaSoggetto, now);
			}
			associaSoggetto.setIDPERS(temp.getIDPERS());
			associaSoggetto.setIDPROC(temp.getIDPROC());
			associaSoggetto.setIDSOGGINT(temp.getIDSOGGINT());
			associaSoggetto.setIDUFFOWN(temp.getIDUFFOWN());
			associaSoggetto.setIDVER(temp.getIDVER());
			listaSoggetti.add(associaSoggetto);
		}
		return listaSoggetti;
	}

	public Long iscriviProcedimentoDiAggravamento(Integer idUffOwn, String codUtente, ProcedimentoForm procForm,
			Long idSoggettoInteressato, Long idPadre, String tipoUtente, int idTipoFascicolo) {

		TProcedimento proc = new TProcedimento();
		mapperBean.map(procForm, proc);
		TTpStatoProc tStatoProc = tipoUtente.equalsIgnoreCase(Constants.TIPO_UTENTE_SMP)
				? new TTpStatoProc((int) ProcedimentoServiceImpl.ID_STATO_PROCEDIMENTO_IN_LAVORAZIONE)
				: new TTpStatoProc((int) ProcedimentoServiceImpl.ID_STATO_PROCEDIMENTO_IN_ACQUISIZIONE_PROPOSTA);
		proc.setTTpStatoProc(tStatoProc);
		TTpTipoProc tipo = new TTpTipoProc();
		tipo.setCodiKIdTipoProc(Integer.parseInt(Constants.TIPOPROC_AGGRAVAMENTO_PERSONALE));
		proc.setTTpTipoProc(tipo);
		Date now = Calendar.getInstance().getTime();
		Long idAggravamento = procedimentoDao.inserisciProcedimento(idUffOwn, codUtente, proc, now, idTipoFascicolo);
		SoggettoForm soggettoForm = getSogIntById(idSoggettoInteressato, idUffOwn, procForm.getIdProcedimento(), null);
		// Carico le informazioni da T_REL_PROC_SOGINT
		soggettoForm.setIdProcedimento(procForm.getIdProcedimento());
		soggettoForm = caricaValoriGratuitoPatrocinioDelSoggetto(soggettoForm);
		associaSoggettoInteressatoAllAggravamento(soggettoForm, idAggravamento, idUffOwn, codUtente, now);
		ProcedimentoForm padre = getDettaglioProcedimento(idPadre);
		procedimentoDao.associaAggravamentoAlPadre(idAggravamento, idPadre, padre.getNumero(), padre.getAnno(),
				idUffOwn, codUtente);
		inserisciMisurePersonaliPerAggravamento(idAggravamento, idPadre, idSoggettoInteressato, idUffOwn);
		return idAggravamento;
	}

	public List<BeneSoggettoForm> listaBeniAttoDecisorio(long idProcedimento, long idAtto, int idUfficioUtente,
			long idSoggettoDiretto, long idSoggettoTitolare, long idTipologia, long idCategoria,
			long idSottocategoria) {

		List<BeneSoggettoForm> risultati = new ArrayList<BeneSoggettoForm>();
		List queryResults = procedimentoDao.listaBeniAttoDecisorioFiltrata(idProcedimento, idAtto, idUfficioUtente,
				idSoggettoDiretto, idSoggettoTitolare, idTipologia, idCategoria, idSottocategoria);
		if ((queryResults == null) || queryResults.isEmpty()) {
			//F.C. Ticket#20220506018
			//invece di ritornare null ritorna un array vuoto 
			return  new ArrayList<BeneSoggettoForm>();
		}

		for (int i = 0; i < queryResults.size(); i++) {
			long idBenePrecedente = 0l;
			if (i != 0) {
				Map<String, Object> riga = (Map<String, Object>) queryResults.get(i - 1);
				TBene benePrecedente = (TBene) riga.get("bene");
				if ((benePrecedente != null) && (benePrecedente.getId() != null)) {
					idBenePrecedente = benePrecedente.getId().getCodiKIdBene();
				}
			}
			Map<String, Object> row = (Map<String, Object>) queryResults.get(i);
			TBene bene = (TBene) row.get("bene");
			BeneForm beneForm = new BeneForm();
			mapperBean.map(bene, beneForm);
			if (idBenePrecedente != beneForm.getIdBene()) {
				BeneSoggettoForm beneSoggettoForm = new BeneSoggettoForm();
				beneSoggettoForm.setBene(beneForm);
				List<SoggettoForm> soggettiAssociatiAlBene = new ArrayList<SoggettoForm>();
				if (idSoggettoDiretto > 0 || idSoggettoTitolare > 0) {
					soggettiAssociatiAlBene = caricaSoggettiAssociatiFiltrati(queryResults, beneForm, idSoggettoDiretto,
							idSoggettoTitolare, idUfficioUtente);
				} else {
					soggettiAssociatiAlBene = caricaSoggettiAssociati(queryResults, beneForm, idProcedimento, idAtto,
							idUfficioUtente);
				}
				beneSoggettoForm.setSoggetti(soggettiAssociatiAlBene);
				risultati.add(beneSoggettoForm);
			}
		}
		return risultati;
	}

	/**
	 * + * listaBeniAttoNoMisPatr
	 * 
	 * @param idUffOwn
	 * @param idProcedimento
	 * @param idAtto
	 * @param idSoggetto
	 * @return List<BeneSoggettoForm>
	 */
	public List<BeneSoggettoForm> listaBeniAttoNoMisPatr(int idUffOwn, long idProcedimento, long idAtto,
			long idSoggetto, long idSoggettoTit, long idTipologia, long idCategoria, long idSottocategoria) {

		List queryResults = null;
		// try {
		queryResults = beneDao.listaBeniAttoNoMisPatr(idUffOwn, idProcedimento, idAtto, idSoggetto, idSoggettoTit,
				idTipologia, idCategoria, idSottocategoria);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		if ((queryResults == null) || queryResults.isEmpty()) {
			return null;
		}
		List<BeneSoggettoForm> risultati = new ArrayList<BeneSoggettoForm>();
		for (int i = 0; i < queryResults.size(); i++) {
			long idBenePrecedente = 0l;
			if (i != 0) {
				Map<String, Object> riga = (Map<String, Object>) queryResults.get(i - 1);
				TBene benePrecedente = (TBene) riga.get("bene");
				if ((benePrecedente != null) && (benePrecedente.getId() != null)) {
					idBenePrecedente = benePrecedente.getId().getCodiKIdBene();
				}
			}
			Map<String, Object> row = (Map<String, Object>) queryResults.get(i);
			TBene bene = (TBene) row.get("bene");
			BeneForm beneForm = new BeneForm();
			mapperBean.map(bene, beneForm);
			if (idBenePrecedente != beneForm.getIdBene()) {
				BeneSoggettoForm beneSoggettoForm = new BeneSoggettoForm();
				beneSoggettoForm.setBene(beneForm);
				List<SoggettoForm> soggettiAssociatiAlBene = caricaSoggettiAssociati(queryResults, beneForm,
						idProcedimento, idAtto, idUffOwn);
				beneSoggettoForm.setSoggetti(soggettiAssociatiAlBene);
				risultati.add(beneSoggettoForm);
			}
		}
		return risultati;
	}

	public List<BeneSoggettoForm> listaBeniConSoggettiAssociatiAdAttoEProcedimento(long idProcedimento, long idAtto,
			Integer idUfficioUtente) {

		return listaBeniConSoggettiAssociatiAdAttoEProcedimento(idProcedimento, idAtto, idUfficioUtente, false);
	}

	public List<BeneSoggettoForm> listaBeniConSoggettiAssociatiAdAttoEProcedimento(long idProcedimento, long idAtto,
			Integer idUfficioUtente, boolean isCda) {
		List<BeneSoggettoForm> risultati = new ArrayList<BeneSoggettoForm>();
		List<TRelProcBene> listaRelProcBene = procedimentoDao.listaRelProcBene(idProcedimento, idUfficioUtente);
		for (TRelProcBene tmp : listaRelProcBene) {
			long idBene = tmp.getId().getCodiKIdBene();
			creaBeneSoggettoFormDaIdBene(idProcedimento, idAtto, idUfficioUtente, risultati, idBene, isCda);
		}
		return risultati;
	}

	public List<BeneSoggettoForm> listaBeniConSoggettiAssociatiAdAttoEProcedimentoPerRevoca(long idProcedimento,
			long idAtto, Integer idUfficioUtente, String descUtente, String nomeMetodo) {

		List<BeneSoggettoForm> risultati = new ArrayList<BeneSoggettoForm>();
		List<TRelProcBene> listaRelProcBene = beneDao.dettaglioRevocaRelProcBene(idAtto, idProcedimento, descUtente,
				idUfficioUtente.intValue(), nomeMetodo);
		Iterator<TRelProcBene> iter = listaRelProcBene.iterator();
		while (iter.hasNext()) {
			Map<String, TRelProcBene> row = (Map<String, TRelProcBene>) iter.next();
			TRelProcBene tmp = (TRelProcBene) row.get("procBene");
			long idBene = tmp.getId().getCodiKIdBene();
			creaBeneSoggettoFormDaIdBene(idProcedimento, idAtto, idUfficioUtente, risultati, idBene);
		}
		return risultati;
	}

	public List<BeneSoggettoForm> listaBeniConSoggettiAssociatiAdAttoEProcedimentoPerDecRestBeni(long idProcedimento,
			long idAtto, Integer idUfficioUtente, String descUtente, String nomeMetodo) {

		List<BeneSoggettoForm> risultati = new ArrayList<BeneSoggettoForm>();
		List<TRelProcBene> listaRelProcBene = beneDao.elencoRestBeniRelProcBene(idProcedimento, descUtente,
				idUfficioUtente.intValue(), nomeMetodo);
		Iterator<TRelProcBene> iter = listaRelProcBene.iterator();
		while (iter.hasNext()) {
			Map<String, TRelProcBene> row = (Map<String, TRelProcBene>) iter.next();
			TRelProcBene tmp = (TRelProcBene) row.get("procBene");
			long idBene = tmp.getId().getCodiKIdBene();

			creaBeneSoggettoFormDaIdBene(idProcedimento, idAtto, idUfficioUtente, risultati, idBene);
		}
		return risultati;
	}

	public List<BeneSoggettoForm> listaSoggettiMisuraPersonaleAssociatiAdAttoEProcedimento(long idProcedimento,
			long idAtto, int idUffOwn) {

		// 2.Recupero tutte le misure collegate e le setto 'misure' del
		// BeneSoggettoForm

		List<BeneSoggettoForm> risultati = new ArrayList<BeneSoggettoForm>();// Lista
																				// finale
		// Recupero tutti i soggetti relativi alle Misure Personali presenti
		List idSoggetti = procedimentoDao.listaSoggettiMisPersDefinitivita(idProcedimento, idAtto, idUffOwn);
		if ((idSoggetti == null) || idSoggetti.isEmpty()) {
			return risultati;
		} else {
			Iterator iterator = idSoggetti.iterator();
			while (iterator.hasNext()) {
				// Per ogni soggetto ...
				HashMap first = (HashMap) iterator.next();
				BigDecimal idSog = (BigDecimal) first.get("CODI_FK_ID_SOGG_INT");
				Long idSogg = idSog.longValue();
				BeneSoggettoForm sogMisForm = new BeneSoggettoForm();
				SoggettoForm soggettoForm = new SoggettoForm();
				List<MisuraForm> misure = new ArrayList<MisuraForm>();
				// ... recupero le informazioni relative al soggetto stesso
				soggettoForm = getPersonaById(idSogg, idUffOwn);
				// ... recupero la lista delle Misure Personali collegate
				// (P,C,A)
				List queryResults = procedimentoDao.listaMisPersPerSoggettoDefinitivita(idSogg, idProcedimento, idAtto,
						idUffOwn);
				if ((queryResults == null) || queryResults.isEmpty()) {
					// return risultati;
				} else {
					Iterator iterator1 = queryResults.iterator();
					while (iterator1.hasNext()) {
						Map<String, Object> row = (Map<String, Object>) iterator1.next();
						TRelAttoMisPers attoMisPers = (TRelAttoMisPers) row.get("relam");
						TMisuraPers misPers = (TMisuraPers) row.get("mis");
						TCauzione cauz = (TCauzione) row.get("cauz");
						SoggettoForm soggForm = new SoggettoForm();
						mapperBean.map(attoMisPers, soggForm);
						soggForm.getMisura().setIdMisura(attoMisPers.getId().getCodiKIdMisuraPers());
						soggForm.setIdSoggetto(misPers.getTSoggetto().getId().getCodiKIdSoggInt());
						if (cauz != null) {
							if (cauz.getImpoCauzione() != null)
								soggForm.getMisura().setImporto(cauz.getImpoCauzione());
						}
						// *** START GESTIONE ESITO ***
						String descrizioneMisura = soggForm.getMisura().getTipoMisura().getDescrizione();
						// Se tipoMisura è 1,2,3 ossia MisuraPersonale
						if (soggForm.getMisura().getTipoMisura().getId().equalsIgnoreCase("1")
								|| soggForm.getMisura().getTipoMisura().getId().equalsIgnoreCase("2")
								|| soggForm.getMisura().getTipoMisura().getId().equalsIgnoreCase("3")) {
							if (soggForm.getMisura().getDurata() != null) {
								descrizioneMisura = descrizioneMisura + " - " + soggForm.getMisura().getDurata();
							}
						} else // Se tipoMisura è 25 ossia Cauzione
						if (soggForm.getMisura().getTipoMisura().getId().equalsIgnoreCase("25")) {
							if (soggForm.getMisura().getImporto() != null) {
								descrizioneMisura = descrizioneMisura + " - Importo : "
										+ soggForm.getMisura().getImporto();
							}
						} else // Se tipoMisura è 26 ossia Art.27
						if (soggForm.getMisura().getTipoMisura().getId().equalsIgnoreCase("26")) {
							descrizioneMisura = descrizioneMisura + "";
						} else
							descrizioneMisura = null;
						soggForm.getMisura().setDescrizioneMisura(descrizioneMisura);
						// *** END GESTIONE ESITO ***
						long idMisura = soggForm.getMisura().getIdMisura();
						TDefPers definitivita = null;
						List<TDefPers> risultatiDefPers = procedimentoDao.getTDefPersById(idProcedimento, idMisura,
								idAtto, idUffOwn, null);
						if (risultatiDefPers.size() > 0)
							definitivita = risultatiDefPers.get(0);
						if (definitivita != null) {
							if (definitivita.getDataDefinitivita() != null)
								soggForm.getMisura().setDataDefinitivita(definitivita.getDataDefinitivita());
							if (definitivita.getDataInvio() != null)
								soggForm.getMisura().setDisabledCheckDefinitivita(true);
						}
						MisuraForm misura = new MisuraForm();
						misura = soggForm.getMisura();
						misure.add(misura);
					}
					// Controllo se tutte le misure hanno il check a true,
					// imposto il valore per disabilitare l'id Soggetto
					// corrispondente
					boolean disabilita = true;
					for (MisuraForm misuraForm : misure) {
						if (!misuraForm.isDisabledCheckDefinitivita()) {
							disabilita = false;
							break;
						}
					}
					if (disabilita)
						soggettoForm.setDisabledCheckDefinitivita(true);
					// Fine controllo check Definitivita'
					// setto 'soggetto' nel mio BeneSoggettoForm(sogMisForm)
					sogMisForm.setSoggetto(soggettoForm);
					// Fine disabilita check
					// Recuperate le misure nel mio BeneSoggettoForm(sogMisForm)
					// setto 'misure'
					sogMisForm.setMisure(misure);
					risultati.add(sogMisForm);
				}
				// Fuori dal ciclo aggiungo il mio BeneSoggettoForm(sogMisForm)
				// a 'risultati'

			}
			return risultati;
		}
	}

	public List<BeneSoggettoForm> listaSoggettiMisuraPersonaleAssociatiAdAttoEProcedimentoFiltrati(long idProcedimento,
			long idAtto, int idUffOwn, long idSoggettoDiretto) {

		// 2.Recupero tutte le misure collegate e le setto 'misure' del
		// BeneSoggettoForm

		List<BeneSoggettoForm> risultati = new ArrayList<BeneSoggettoForm>();// Lista
																				// finale
		// Recupero tutti i soggetti relativi alle Misure Personali presenti
		List idSoggetti = procedimentoDao.listaSoggettiMisPersDefinitivitaFiltrati(idProcedimento, idAtto, idUffOwn,
				idSoggettoDiretto);

		if ((idSoggetti == null) || idSoggetti.isEmpty()) {
			return risultati;
		}
		Iterator iterator = idSoggetti.iterator();
		while (iterator.hasNext()) {
			// Per ogni soggetto ...
			HashMap first = (HashMap) iterator.next();
			BigDecimal idSog = (BigDecimal) first.get("CODI_FK_ID_SOGG_INT");
			Long idSogg = idSog.longValue();
			BeneSoggettoForm sogMisForm = new BeneSoggettoForm();
			SoggettoForm soggettoForm = new SoggettoForm();
			List<MisuraForm> misure = new ArrayList<MisuraForm>();
			// ... recupero le informazioni relative al soggetto stesso
			soggettoForm = getPersonaById(idSogg, idUffOwn);
			// ... recupero la lista delle Misure Personali collegate
			// (P,C,A)
			List queryResults = procedimentoDao.listaMisPersPerSoggettoDefinitivita(idSogg, idProcedimento, idAtto,
					idUffOwn);
			if ((queryResults == null) || queryResults.isEmpty()) {
				// return risultati;
			} else {
				Iterator iterator1 = queryResults.iterator();
				while (iterator1.hasNext()) {
					Map<String, Object> row = (Map<String, Object>) iterator1.next();
					TRelAttoMisPers attoMisPers = (TRelAttoMisPers) row.get("relam");
					TMisuraPers misPers = (TMisuraPers) row.get("mis");
					TCauzione cauz = (TCauzione) row.get("cauz");
					SoggettoForm soggForm = new SoggettoForm();
					mapperBean.map(attoMisPers, soggForm);
					soggForm.getMisura().setIdMisura(attoMisPers.getId().getCodiKIdMisuraPers());
					soggForm.setIdSoggetto(misPers.getTSoggetto().getId().getCodiKIdSoggInt());
					if (cauz != null) {
						if (cauz.getImpoCauzione() != null)
							soggForm.getMisura().setImporto(cauz.getImpoCauzione());
					}
					// *** START GESTIONE ESITO ***
					String descrizioneMisura = soggForm.getMisura().getTipoMisura().getDescrizione();
					// Se tipoMisura è 1,2,3 ossia MisuraPersonale
					if (soggForm.getMisura().getTipoMisura().getId().equalsIgnoreCase("1")
							|| soggForm.getMisura().getTipoMisura().getId().equalsIgnoreCase("2")
							|| soggForm.getMisura().getTipoMisura().getId().equalsIgnoreCase("3")) {
						if (soggForm.getMisura().getDurata() != null) {
							descrizioneMisura = descrizioneMisura + " - " + soggForm.getMisura().getDurata();
						}
					} else // Se tipoMisura è 25 ossia Cauzione
					if (soggForm.getMisura().getTipoMisura().getId().equalsIgnoreCase("25")) {
						if (soggForm.getMisura().getImporto() != null) {
							descrizioneMisura = descrizioneMisura + " - Importo : " + soggForm.getMisura().getImporto();
						}
					} else // Se tipoMisura è 26 ossia Art.27
					if (soggForm.getMisura().getTipoMisura().getId().equalsIgnoreCase("26")) {
						descrizioneMisura = descrizioneMisura + "";
					} else
						descrizioneMisura = null;
					soggForm.getMisura().setDescrizioneMisura(descrizioneMisura);
					// *** END GESTIONE ESITO ***
					long idMisura = soggForm.getMisura().getIdMisura();
					TDefPers definitivita = null;
					List<TDefPers> risultatiDefPers = procedimentoDao.getTDefPersById(idProcedimento, idMisura, idAtto,
							idUffOwn, null);
					if (risultatiDefPers.size() > 0)
						definitivita = risultatiDefPers.get(0);
					if (definitivita != null) {
						if (definitivita.getDataDefinitivita() != null)
							soggForm.getMisura().setDataDefinitivita(definitivita.getDataDefinitivita());
						if (definitivita.getDataInvio() != null)
							soggForm.getMisura().setDisabledCheckDefinitivita(true);
					}
					MisuraForm misura = new MisuraForm();
					misura = soggForm.getMisura();
					misure.add(misura);
				}
				// Controllo se tutte le misure hanno il check a true,
				// imposto il valore per disabilitare l'id Soggetto
				// corrispondente
				boolean disabilita = true;
				for (MisuraForm misuraForm : misure) {
					if (!misuraForm.isDisabledCheckDefinitivita()) {
						disabilita = false;
						break;
					}
				}
				if (disabilita)
					soggettoForm.setDisabledCheckDefinitivita(true);
				// Fine controllo check Definitivita'
				// setto 'soggetto' nel mio BeneSoggettoForm(sogMisForm)
				sogMisForm.setSoggetto(soggettoForm);
				// Fine disabilita check
				// Recuperate le misure nel mio BeneSoggettoForm(sogMisForm)
				// setto 'misure'
				sogMisForm.setMisure(misure);
				risultati.add(sogMisForm);
			}
			// Fuori dal ciclo aggiungo il mio BeneSoggettoForm(sogMisForm)
			// a 'risultati'

		}
		return risultati;
	}

	public List<BeneSoggettoForm> listaBeniConSoggettiAssociatiAllaMisuraPatrimoniale(long idSoggetto,
			long idProcedimento, long idAtto, Integer idUfficioUtente) {

		List<BeneSoggettoForm> risultati = new ArrayList<BeneSoggettoForm>();
		List<TRelAttoMisPatr> listaRelAttoMisPatr = procedimentoDao.listaRelMisPatr(idSoggetto, idProcedimento, idAtto,
				idUfficioUtente);
		Set<Long> idBeniSet = new HashSet<Long>();
		for (TRelAttoMisPatr tmp : listaRelAttoMisPatr) {
			idBeniSet.add(tmp.getId().getCodiKIdBene());
		}
		for (long tmpIdBene : idBeniSet) {
			creaBeneSoggettoFormDaIdBeneAssociato(idSoggetto, 0, idProcedimento, idAtto, idUfficioUtente, risultati,
					tmpIdBene, false);
		}
		return risultati;
	}

	public List<BeneSoggettoForm> listaBeniConSoggettiAssociatiAllaMisuraPatrimonialeAtto(long idSoggetto,
			long idProcedimento, long idAtto, Integer idUfficioUtente) {

		List<BeneSoggettoForm> risultati = new ArrayList<BeneSoggettoForm>();
		List<TRelAttoMisPatr> listaRelAttoMisPatr = procedimentoDao.listaRelMisPatr(idSoggetto, idProcedimento, idAtto,
				idUfficioUtente);
		Set<Long> idBeniSet = new HashSet<Long>();
		for (TRelAttoMisPatr tmp : listaRelAttoMisPatr) {
			idBeniSet.add(tmp.getId().getCodiKIdBene());
		}
		for (long tmpIdBene : idBeniSet) {
			creaBeneSoggettoFormDaIdBeneAssociatoAtto(idSoggetto, idProcedimento, idAtto, idUfficioUtente, risultati,
					tmpIdBene);
		}
		return risultati;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<DifensoreSoggettoForm> listaDifensoriAssociatiSoggetto(long idProcedimento, int idUfficioUtente,
			long idSoggettoInteressato) {

		Long idAvvocato = null;
		List queryResults = null;
		queryResults = procedimentoDao.caricaDifensoriAssociatiSoggetto(idProcedimento, idUfficioUtente,
				idSoggettoInteressato, idAvvocato);
		List<DifensoreSoggettoForm> listaDifensori = new ArrayList<DifensoreSoggettoForm>();
		if ((queryResults == null) || queryResults.isEmpty()) {
			return listaDifensori;
		}
		Iterator iter = queryResults.iterator();
		while (iter.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iter.next();
			TRelProcSogintAvv relProcSogAvv = (TRelProcSogintAvv) row.get("rel");
			TAvvocati avvocato = (TAvvocati) row.get("avv");
			DifensoreSoggettoForm dsf = new DifensoreSoggettoForm();
			mapperBean.map(relProcSogAvv, dsf);
			mapperBean.map(avvocato, dsf);
			listaDifensori.add(dsf);
		}
		return listaDifensori;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<DifensoreSoggettoForm> listaDifensoriAssociatiIntestatari(long idProcedimento, int idUfficioUtente,
			long idSoggettoInteressato) {

		Long idAvvocato = null;
		List queryResults = null;
		queryResults = procedimentoDao.caricaDifensoriAssociatiIntestatari(idProcedimento, idUfficioUtente,
				idSoggettoInteressato, idAvvocato);
		List<DifensoreSoggettoForm> listaDifensori = new ArrayList<DifensoreSoggettoForm>();
		if ((queryResults == null) || queryResults.isEmpty()) {
			return listaDifensori;
		}
		Iterator iter = queryResults.iterator();
		while (iter.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iter.next();
			TRelProcIntestatariAvv relProcSogAvv = (TRelProcIntestatariAvv) row.get("rel");
			TAvvocati avvocato = (TAvvocati) row.get("avv");
			DifensoreSoggettoForm dsf = new DifensoreSoggettoForm();
			mapperBean.map(relProcSogAvv, dsf);
			mapperBean.map(avvocato, dsf);
			listaDifensori.add(dsf);
		}
		return listaDifensori;
	}

	/**
	 * Funzione che verifica se il soggetto abbia il ruolo di avvocato
	 * 
	 * @param listaRuoloEnte
	 * @return
	 */
	private boolean isAvvocato(Soggetto soggetto) {
		boolean ret = false;
		List<String> tipoAvvocati = new ArrayList<String>();
		tipoAvvocati.add("avvocato");
		tipoAvvocati.add("praticanteabilitato");
		tipoAvvocati.add("cassazionista");
		List<Ruoloente> listaRuoloEnte = soggetto.getRuoliente();
		String cod = soggetto.getSoggetto().getCognome() + " - " + soggetto.getSoggetto().getCodFisc();
		if (listaRuoloEnte != null) {
			for (Ruoloente ruoloEnte : listaRuoloEnte) {
				String ruolo = ruoloEnte.getRuolo();
				StringBuffer sb = new StringBuffer();
				sb.append("Codice: " + ruoloEnte.getCodice());
				sb.append(", CodiceFiscale: " + ruoloEnte.getCodiceFiscale());
				sb.append(", Ruolo: " + ruoloEnte.getRuolo());
				sb.append(", PartitaIVA: " + ruoloEnte.getPartitaIVA());
				sb.append(", Descrizione: " + ruoloEnte.getDescrizione());
				if (ruolo != null && tipoAvvocati.contains(ruolo.toLowerCase())) {
					ret = true;
					// logger.debug("IS Avvocato " + sb + " (" + cod + ")");
				} // else {
					// logger.debug("NOT Avvocato " + sb + " (" + cod + ")");
					// }
			}
		}
		// logger.debug("### Avvocato: " + ret + " (" + cod + ")");
		return ret;
	}

	public List<DifensoreSoggettoForm> listaDifensoriReginde(String cognome) {

		List<Soggetto> lstSoggetto = null;
		List<DifensoreSoggettoForm> listaDifensoriReginde = new ArrayList<DifensoreSoggettoForm>();
		lstSoggetto = regindeService.ricercaSoggetto(cognome);
		if (lstSoggetto != null) {
			Iterator<Soggetto> itr = lstSoggetto.iterator();
			while (itr.hasNext()) {
				DifensoreForm difensore = new DifensoreForm();
				DifensoreSoggettoForm dsf = new DifensoreSoggettoForm();
				Soggetto soggetto = (Soggetto) itr.next();
				if (isAvvocato(soggetto)) {
					// if(soggetto.getSoggetto().getIdSoggetto() != null)
					// difensore.setIdReginde(soggetto.getSoggetto().getIdSoggetto());
					if (soggetto.getSoggetto().getCodFisc() != null) {
						difensore.setCodiceFiscale(soggetto.getSoggetto().getCodFisc());
						difensore.setIdReginde(soggetto.getSoggetto().getCodFisc());
					}
					if (soggetto.getSoggetto().getCognome() != null)
						difensore.setCognome(soggetto.getSoggetto().getCognome());
					if (soggetto.getSoggetto().getNome() != null)
						difensore.setNome(soggetto.getSoggetto().getNome());
					if (soggetto.getSoggetto().getLuogoNascita() != null)
						difensore.setLuogoNascita(soggetto.getSoggetto().getLuogoNascita());
					if (soggetto.getSoggetto().getPec() != null)
						difensore.setPec(soggetto.getSoggetto().getPec());
					if (soggetto.getSoggetto().getDataNascita() != null) {
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						difensore.setDataNascita(
								soggetto.getSoggetto().getDataNascita().toGregorianCalendar().getTime());
					}
					dsf.setDifensoreForm(difensore);
					listaDifensoriReginde.add(dsf);
				}
			}
			return listaDifensoriReginde;
		} else
			return null;
	}

	public List<DifensoreSoggettoForm> listaDifensoriNoReginde(String cognome, long idSogg, long idProcedimento,
			int idUffOwn) {
		List<DifensoreSoggettoForm> listaDifensoriForm = new ArrayList<DifensoreSoggettoForm>();
		List listaDifensori = procedimentoDao.listaDifensoriNoReginde(cognome, idSogg, idProcedimento, idUffOwn);
		if (listaDifensori != null) {
			Iterator itr = listaDifensori.iterator();
			while (itr.hasNext()) {
				Map<String, Object> row = (Map<String, Object>) itr.next();
				TAvvocati avvocato = (TAvvocati) row.get("avvocato");
				DifensoreSoggettoForm dsf = new DifensoreSoggettoForm();
				if (avvocato != null)
					mapperBean.map(avvocato, dsf);
				listaDifensoriForm.add(dsf);
			}
		}

		/*
		 * DifensoreForm difensoreReginde1 = new DifensoreForm(); DifensoreSoggettoForm
		 * dsf = new DifensoreSoggettoForm(); difensoreReginde1.setIdReginde("1234567");
		 * difensoreReginde1.setCognome("Rossi"); difensoreReginde1.setNome("Mario");
		 * difensoreReginde1.setLuogoNascita("Italia-Roma(RM)");
		 * 
		 * try { difensoreReginde1.setDataNascita(new
		 * SimpleDateFormat("dd/MM/yyyy").parse("15/05/1957")); } catch (ParseException
		 * e) { e.printStackTrace(); } dsf.setDifensoreForm(difensoreReginde1);
		 * listaDifensoriForm.add(dsf); DifensoreSoggettoForm dsf2 = new
		 * DifensoreSoggettoForm(); DifensoreForm difensoreReginde2 = new
		 * DifensoreForm(); difensoreReginde2.setIdReginde("1234568");
		 * difensoreReginde2.setCognome("Rossini");
		 * difensoreReginde2.setNome("Francesca");
		 * difensoreReginde2.setLuogoNascita("Irlanda-Dublino"); try {
		 * difensoreReginde2.setDataNascita(new
		 * SimpleDateFormat("dd/MM/yyyy").parse("16/12/1968")); } catch (ParseException
		 * e) { e.printStackTrace(); } dsf2.setDifensoreForm(difensoreReginde2);
		 * listaDifensoriForm.add(dsf2);
		 */

		return listaDifensoriForm;
	}

	public List<EredeConviventeForm> listaErediDelDefunto(SoggettoForm soggettoForm) {

		List<TRelSogintErediConv> listaEredi = null;
		listaEredi = procedimentoDao.listaErediOppureConviventiDelSoggetto(soggettoForm.getIdSoggetto(),
				soggettoForm.getIdProcedimento(), soggettoForm.getIdUfficio(), "E");
		List<EredeConviventeForm> listaErediForm = new ArrayList<EredeConviventeForm>();
		Iterator<TRelSogintErediConv> iter = listaEredi.iterator();
		while (iter.hasNext()) {
			TRelSogintErediConv currentErede = iter.next();
			EredeConviventeForm ecf = new EredeConviventeForm();
			mapperBean.map(currentErede, ecf);
			listaErediForm.add(ecf);
		}
		return listaErediForm;
	}

	public List<MagistratoForm> listaMagistratiNonGiaAssociati(List<MagistratoForm> magistratiDelProcedimento,
			List<MagistratoForm> magistratiAssociati) {

		List<MagistratoForm> risultati = new ArrayList<MagistratoForm>();
		if (magistratiDelProcedimento == null) {
			return null;
		}
		if (!Utility.isDefinito(magistratiAssociati)) {
			return magistratiDelProcedimento;
		}
		for (MagistratoForm tmp : magistratiDelProcedimento) {
			if (!magistratiAssociati.contains(tmp)) {
				risultati.add(tmp);
			}
		}
		return risultati;
	}

	public List<MagistratoForm> listaMagistratiPerProcedimento(int idUffOwn, long idProcedimento, int ruoloMag) {

		return listaMagistratiPerProcedimento(idUffOwn, idProcedimento, ruoloMag, false);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<MagistratoForm> listaMagistratiPerProcedimento(int idUffOwn, long idProcedimento, int ruoloMag,
			boolean utenteDelTribunale) {

		List queryResults = null;
		queryResults = procedimentoDao.listaMagistratiPerProcedimento(idUffOwn, idProcedimento, ruoloMag);
		if ((queryResults == null) || queryResults.isEmpty()) {
			return null;
		}
		List<MagistratoForm> risultati = new ArrayList<MagistratoForm>();
		Iterator iterator = queryResults.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			TRelMagProc relProcedimento = (TRelMagProc) row.get("rel");
			TAnMagistrati magistrato = (TAnMagistrati) row.get("mag");
			MagistratoForm form = new MagistratoForm();
			mapperBean.map(relProcedimento, form);
			mapperBean.map(magistrato, form);
			risultati.add(form);
		}
		if (utenteDelTribunale) {
			ordinaRisultatiPerRuolo(risultati);
		}
		ordinaRisultatiPerDataFineAssegnazioneNotNull(risultati);
		return risultati;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<ProcedimentoForm> listaProcedimentiByCriteria(Integer idUfficioUtente, ProcedimentoForm procedimento,
			Integer idTpUffRic) {

		List<RicercaProcedimentoDto> queryResults = null;
		Long tipoFascicolo = null;

		if (procedimento.getTipoFascicoloRicerca() != 0) {
			tipoFascicolo = Long.parseLong(String.valueOf(procedimento.getTipoFascicoloRicerca()));

		}

		Integer idUffRic = null;
		if (procedimento.getIdUffRic() != null && procedimento.getIdUffRic().getId() != Constants.NO_ITEM_SELECTED) {
			idUffRic = new Integer(procedimento.getIdUffRic().getId());
		}

		queryResults = procedimentoDao.getListaProcedimentiByCriteria(idUfficioUtente, procedimento.getAnno(),
				procedimento.getNumeroMin(), procedimento.getNumeroMax(), procedimento.getDataIscrizioneMin(),
				procedimento.getDataIscrizioneMax(), Long.parseLong(procedimento.getStatoProcedimento().getId()),
				procedimento.getCognomeSoggetto(), procedimento.getNomeSoggetto(), procedimento.getRagioneSociale(),
				Long.parseLong(procedimento.getPmAssegnatario().getId()),
				Long.parseLong(procedimento.getTipoProcedimento().getId()), procedimento.getAnnoDecretoDecisorio(),
				procedimento.getNumeroDecretoDecisorio(), Long.parseLong(procedimento.getTipoProcedente().getId()),
				Long.parseLong(procedimento.getProcedente().getId()), procedimento.isAntimafia(),
				procedimento.isAntiterrorismo(), procedimento.isOrdinaria(), tipoFascicolo, idTpUffRic, idUffRic,
				procedimento.getNomeDifensore(), procedimento.getCognomeDifensore(),procedimento.getCodiceFiscaleDifensore());

		List<ProcedimentoForm> risultati = new ArrayList<ProcedimentoForm>();
		if ((queryResults == null) || queryResults.isEmpty()) {
			return risultati;
		}
		Iterator iterator = queryResults.iterator();
		while (iterator.hasNext()) {
			RicercaProcedimentoDto row = (RicercaProcedimentoDto) iterator.next();
			ProcedimentoForm form = new ProcedimentoForm();
			mapperBean.map(row, form);
			risultati.add(form);
		}
		return risultati;
	}

	/**
	 * listaSoggettiByCriteria(
	 * 
	 * @param nome
	 * @param cognome
	 * @return List
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<SoggettoForm> listaSoggettiByCriteria(int idUffOwn, String nome, String cognome,
			String codiceStatoNascita, String codiceComuneNascita, String comuneNascitaEstero, String dataNascita,
			String codiceFiscale, String codiceCui, boolean escludiIntervenienti, Integer idRuolo) {

		List<TPersonaFisica> listaSoggetti = null;
		listaSoggetti = procedimentoDao.listaSoggettiByCriteria(idUffOwn, nome, cognome, codiceStatoNascita,
				codiceComuneNascita, comuneNascitaEstero, dataNascita, codiceFiscale, codiceCui, escludiIntervenienti,
				idRuolo);
		List<SoggettoForm> listaMVC = new ArrayList<SoggettoForm>();
		if (listaSoggetti != null) {
			Iterator iter = listaSoggetti.iterator();
			while (iter.hasNext()) {
				Map<String, Object> row = (Map<String, Object>) iter.next();
				TPersonaFisica currentPersonaF = (TPersonaFisica) row.get("pers");
				TAnStato currentStato = (TAnStato) row.get("stato");
				TAnComuni currentComune = (TAnComuni) row.get("comuni");
				TAnProvincia currentProvincia = (TAnProvincia) row.get("provincia");
				SoggettoForm sForm = new SoggettoForm();
				if (currentPersonaF != null) {
					mapperBean.map(currentPersonaF, sForm);
				}
				if (currentStato != null) {
					mapperBean.map(currentStato, sForm);
				}
				if (currentComune != null) {
					mapperBean.map(currentComune, sForm);
				}
				if (currentProvincia != null) {
					mapperBean.map(currentProvincia, sForm);
				}
				listaMVC.add(sForm);
			}
		}
		return listaMVC;
	}

	@SuppressWarnings("deprecation")
	public List<SoggettoForm> listaSoggettiDellaFonte(long idProcedimento, String fonte) {

		List<SoggettoForm> lista = new ArrayList<SoggettoForm>();
		SoggettoForm soggetto1 = new SoggettoForm();
		soggetto1.setIdSoggetto(423L);
		soggetto1.setNome("Carl");
		soggetto1.setCognome("Barks");
		soggetto1.setComuneNascita(new ComboForm("", "Roma"));
		soggetto1.setDataNascita(new Date(1, 4, 27));
		lista.add(soggetto1);
		SoggettoForm soggetto2 = new SoggettoForm();
		soggetto2.setIdSoggetto(424L);
		soggetto2.setNome("Mario");
		soggetto2.setCognome("Rossi");
		soggetto2.setComuneNascita(new ComboForm("", "Catanzaro"));
		soggetto2.setDataNascita(new Date(85, 11, 16));
		lista.add(soggetto2);
		return lista;
	}

	@SuppressWarnings("deprecation")
	public List<SoggettoForm> listaSoggettiDelProcedimentoNonAssociati(long idProcedimento) {

		List<SoggettoForm> lista = new ArrayList<SoggettoForm>();
		SoggettoForm soggetto2 = new SoggettoForm();
		soggetto2.setIdSoggetto(424L);
		soggetto2.setNome("Mario");
		soggetto2.setCognome("Rossi");
		soggetto2.setComuneNascita(new ComboForm("", "Catanzaro"));
		soggetto2.setDataNascita(new Date(85, 11, 16));
		MisuraForm misura2 = new MisuraForm();
		soggetto2.setMisura(misura2);
		soggetto2.getMisura().setDescrizioneMisura("Personale");
		soggetto2.getMisura().setCauzione("SI");
		soggetto2.getMisura().setArt67("SI");
		lista.add(soggetto2);
		SoggettoForm soggetto3 = new SoggettoForm();
		soggetto3.setIdSoggetto(425L);
		soggetto3.setRagioneSociale("Zeta & CO S.N.C.");
		soggetto3.setPartitaIva("0764352056");
		soggetto3.setIdVersione(1L);
		MisuraForm misura3 = new MisuraForm();
		soggetto3.setMisura(misura3);
		soggetto3.getMisura().setDescrizioneMisura("Patrimoniale");
		soggetto3.getMisura().setCauzione("SI");
		soggetto3.getMisura().setArt67("NO");
		lista.add(soggetto3);
		return lista;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<SoggettoForm> listaSoggettiGiurByCriteria(int idUffOwn, String ragioneSociale, String partitaIva,
			Integer vidStatoSede, Integer vidComuneSede, String vcomuneSedeEstero, boolean escludiIntervenienti,
			Integer idRuolo) {

		List queryResults = null;
		queryResults = procedimentoDao.listaSoggettiGiurByCriteria(idUffOwn, ragioneSociale, partitaIva, vidStatoSede,
				vidComuneSede, vcomuneSedeEstero, escludiIntervenienti, idRuolo);
		if ((queryResults == null) || queryResults.isEmpty()) {
			return new ArrayList<SoggettoForm>();
		}
		List<SoggettoForm> risultati = new ArrayList<SoggettoForm>();
		Iterator<Map<String, Object>> iterator = queryResults.iterator();
		while (iterator.hasNext()) {
			SoggettoForm sForm = new SoggettoForm();
			Map<String, Object> row = iterator.next();
			TPersonaGiur currentPersonaG = (TPersonaGiur) row.get("p_giur");
			TAnStato currentStato = (TAnStato) row.get("stato");
			TAnComuni currentComune = (TAnComuni) row.get("comuni");
			TAnProvincia currentProvincia = (TAnProvincia) row.get("provincia");
			if (currentPersonaG != null) {
				mapperBean.map(currentPersonaG, sForm);
			}
			if (currentStato != null) {
				mapperBean.map(currentStato, sForm);
			}
			if (currentComune != null) {
				mapperBean.map(currentComune, sForm);
			}
			if (currentProvincia != null) {
				mapperBean.map(currentProvincia, sForm);
			}
			risultati.add(sForm);
		}
		return risultati;
	}

	public boolean localitaObbligatoriaNelTipoMisuraSelezionato(Long idTipoMisura) {

		List<TTpTipoMisura> lista = commonDao.listaTipiMisura("P");
		for (TTpTipoMisura tmp : lista) {
			if ((tmp.getCodiKIdTipoMisura() == idTipoMisura) && tmp.getFlgLocalita().equals("S")) {
				return true;
			}
			return false;
		}
		return false;
	}

	public void modificaDatiMagistratoSuProcedimento(MagistratoForm magistrato, long idProcedimento,
			Integer idUfficioUtente) {

		procedimentoDao.modificaDatiMagistratoSuProcedimento(magistrato.getId(), magistrato.getDataFineAssegnazione(),
				magistrato.getTipoSostituzione(), magistrato.getOrdineDiVisualizzazione(), magistrato.getRelatore(),
				magistrato.getDataAssegnazioneFascicolo(), magistrato.getRuolo().getId(), magistrato.getAltroMotivo(),
				idProcedimento, idUfficioUtente);
	}

	public void modificaEredeDelDefunto(SoggettoForm soggettoForm, String codiceUtente) {

		TRelSogintErediConv trel = new TRelSogintErediConv();
		EredeConviventeForm ecf = soggettoForm.getEredeConviventeForm();
		mapperBean.map(ecf, trel);
		procedimentoDao.modificaEredeOppureConviventeDelSoggetto(soggettoForm.getIdSoggetto(),
				soggettoForm.getIdProcedimento(), soggettoForm.getIdUfficio(), codiceUtente, trel);
	}

	public void modificaFonte(long idUfficio, long idProcedimento, AttoForm form, String codiceUtente) {

		TAtto atto = new TAtto();
		TIstanza istanza = new TIstanza();
		TAttoId idAtto = new TAttoId(form.getCodAtto(), (int) idUfficio);
		// copio dataRicezione in dataEmissione
		form.setDataEmissione(form.getDataInformativa());
		atto.setFileDocumento(form.getAllegato().getDocumento());
		mapperBean.map(form, atto);
		mapperBean.map(form, istanza);
		atto.setId(idAtto);
		long idFonte = procedimentoDao.modificaFonte(idUfficio, atto, codiceUtente, istanza);
		if ((form.getListaIdCheckbox() != null) && (form.getListaIdCheckbox().size() > 0)) {
			Iterator<Long> iter = form.getListaIdCheckbox().iterator();
			while (iter.hasNext()) {
				Long current = iter.next();
				TRelProcSogint direttoInteressato = new TRelProcSogint();
				// commentato da sabino
				// direttoInteressato.setCodiFkIdFonte(idFonte);
				TAtto tAtto = new TAtto();
				TAttoId attoId = new TAttoId();
				attoId.setCodiKIdAtto(idFonte);
				attoId.setCodiKIdUffOwner((int) idUfficio);
				tAtto.setId(attoId);
				direttoInteressato.setTAtto(tAtto);
				TRelProcSogintId id = new TRelProcSogintId((int) idUfficio, idProcedimento, current);
				direttoInteressato.setId(id);
				procedimentoDao.associaSoggettoDirettoInteressato(direttoInteressato);
			}
		}
		if ((form.getListaIdCheckbox_associati() != null) && (form.getListaIdCheckbox_associati().size() > 0)) {
			Iterator<Long> iter = form.getListaIdCheckbox_associati().iterator();
			while (iter.hasNext()) {
				Long current = iter.next();
				TRelProcSogint direttoInteressato = new TRelProcSogint();
				// commentato da sabino
				// direttoInteressato.setCodiFkIdFonte(idFonte);
				TRelProcSogintId id = new TRelProcSogintId((int) idUfficio, idProcedimento, current);
				direttoInteressato.setId(id);
				procedimentoDao.dissociaSoggettoDirettoInteressato(direttoInteressato);
			}
		}
	}

	public void modificaGratuitoPatrocinioSoggProc(SoggettoForm form, long idProc, Integer idUff) {

		String idTenoreGP = null;
		if ((form.getTenoreProvvedimentoGratuitoPatrocinio() != null)
				&& (form.getTenoreProvvedimentoGratuitoPatrocinio().getId() != null)) {
			idTenoreGP = form.getTenoreProvvedimentoGratuitoPatrocinio().getId();
		}
		procedimentoDao.modificaGratuitoPatrocinioSoggProc(idProc, idUff, form.getIdSoggetto(),
				form.getAnnoRegistroGratuitoPatrocinio(), form.getNumeroRegistroGratuitoPatrocinio(),
				form.getDataIstanzaGratuitoPatrocinio(), form.getDataProvvedimentoGratuitoPatrocinio(),
				form.getDataRevocaGratuitoPatrocinio(), form.getDescNoteGp(), idTenoreGP);
	}

	public void cancellaGratuitoPatrocinioSoggProc(SoggettoForm form, long idProc, Integer idUff) {

		String idTenoreGP = null;
		if ((form.getTenoreProvvedimentoGratuitoPatrocinio() != null)
				&& (form.getTenoreProvvedimentoGratuitoPatrocinio().getId() != null)) {
			idTenoreGP = form.getTenoreProvvedimentoGratuitoPatrocinio().getId();
		}
		procedimentoDao.cancellaGratuitoPatrocinioSoggProc(idProc, idUff, form.getIdSoggetto());
	}

	public void aggiornaTipoPrevenzioneNormativaSoggetto(long idProcedimento, Integer idUfficioUtente,
			long idSoggettoInteressato, long idTipoPrev, long idTipoNorm) {

		procedimentoDao.aggiornaTipoPrevenzioneNormativaSoggetto(idProcedimento, idUfficioUtente, idSoggettoInteressato,
				idTipoPrev, idTipoNorm);
	}

	public void aggiornaFlgTipoPrevenzioneProcedimento(long idProc, Integer idUffOwn) {

		TProcedimento procedimento = procedimentoDao.getProcedimentoById(idProc);
		List<TTpTipoPrevenzione> tipiPrevenzione = commonDao.listaTipiPrevenzione();
		Character flagOrdinaria = null;
		Character flagAntimafia = null;
		Character flagAntiterrorismo = null;
		for (Iterator iterator = tipiPrevenzione.iterator(); iterator.hasNext();) {
			TTpTipoPrevenzione tTipoPrevenzione = (TTpTipoPrevenzione) iterator.next();
			long tipoPrev = tTipoPrevenzione.getCodiKIdTipoPrev();
			TRelProcSogint proc = procedimentoDao.getTrelProcSogintByIdsoggIdProcIdUff(idProc, null, idUffOwn,
					tipoPrev);
			if (proc != null) {
				if (tipoPrev == 1L) {
					flagOrdinaria = 'S';
				} else if (tipoPrev == 2L) {
					flagAntimafia = 'S';
				} else if (tipoPrev == 3L) {
					flagAntiterrorismo = 'S';
				}
			}
		}
		procedimentoDao.aggiornaFlgTipoPrevenzioneProcedimento(procedimento, flagOrdinaria, flagAntimafia,
				flagAntiterrorismo);
		return;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<MagistratoForm> ricercaMagistrati(MagistratoForm magistrato, int idUfficioUtente) {

		List queryResults = null;
		String nome = magistrato != null ? magistrato.getNome() : null;
		String cognome = magistrato != null ? magistrato.getCognome() : null;
		queryResults = procedimentoDao.listaMagistrati(idUfficioUtente, cognome, nome);
		List<MagistratoForm> risultati = new ArrayList<MagistratoForm>();
		if (queryResults != null) {
			Iterator iterator = queryResults.iterator();
			while (iterator.hasNext()) {
				Map<String, Object> row = (Map<String, Object>) iterator.next();
				TAnMagistrati tmp = (TAnMagistrati) row.get("mag");
				MagistratoForm form = new MagistratoForm();
				mapperBean.map(tmp, form);
				risultati.add(form);
			}
		}
		return risultati;
	}

	public void setAttoDao(AttoDao attoDao) {

		this.attoDao = attoDao;
	}

	public void setBeneDao(BeneDao beneDao) {

		this.beneDao = beneDao;
	}

	public void setBeneService(BeneService beneService) {

		this.beneService = beneService;
	}

	public void setCommonDao(CommonDao commonDao) {

		this.commonDao = commonDao;
	}

	public void setIdSoggettoIdVersioneTipoSoggFromChiave(SoggettoForm soggetto) {

		StringTokenizer st = new StringTokenizer(soggetto.getChiave(), ",");
		String idSoggettoStr = null;
		String idVersioneStr = null;
		String tipoSoggettoStr = null;
		while (st.hasMoreTokens()) {
			idSoggettoStr = st.nextToken();
			idVersioneStr = st.nextToken();
			if (st.hasMoreTokens()) {
				tipoSoggettoStr = st.nextToken();
			}
			break;
		}
		soggetto.setIdSoggetto(Long.parseLong(idSoggettoStr));
		soggetto.setIdVersione(Long.parseLong(idVersioneStr));
		soggetto.setTipoSoggetto(tipoSoggettoStr);
	}

	public void setMisuraService(MisuraService misuraService) {

		this.misuraService = misuraService;
	}

	public void setProcedimentoDao(ProcedimentoDao procedimentoDao) {

		this.procedimentoDao = procedimentoDao;
	}

	public List<SoggettoForm> soggettiDirettiInteressatiDelProcedimento(long idProcedimento) {

		return soggettiDirettiInteressatiDelProcedimento(idProcedimento, null, Constants.Flag.S);
	}

	public List<SoggettoForm> soggettiDirettiInteressatiApplicazioneProvvisoria(long idProcedimento, int idUffOwn,
			String codiceUtente) {

		return getSoggettiDirettiInteressatiApplicazioneProvvisoria(idProcedimento, idUffOwn, codiceUtente);
	}

	public List<SoggettoForm> getSoggettiDirettiInteressatiApplicazioneProvvisoria(long idProcedimento, int idUffOwn,
			String codiceUtente) {

		List<SoggettoForm> lista = new ArrayList<SoggettoForm>();

		List<SogIntDettaglioProcedimentoDto> soggettiApplProvv = procedimentoDao
				.soggettiDirettiInteressatiApplicazioneProvvisoria(idProcedimento, idUffOwn, codiceUtente);
		Iterator<SogIntDettaglioProcedimentoDto> iter = soggettiApplProvv.iterator();
		while (iter.hasNext()) {
			SoggettoForm soggettoForm = new SoggettoForm();
			SogIntDettaglioProcedimentoDto currentDto = iter.next();
			mapperBean.map(currentDto, soggettoForm);
			soggettoForm.getMisura().setDescrizioneMisura(currentDto.getDESC_MISURA());
			lista.add(soggettoForm);
		}
		return lista;
	}

	public List<SoggettoForm> soggettiFisiciDirettiInteressatiDelProcedimento(long idProcedimento, String descRuolo,
			Character flagValido) {

		List<SoggettoForm> lista = new ArrayList<SoggettoForm>();
		TProcedimento id = new TProcedimento();
		id.setCodiKIdProc(idProcedimento);
		List<SogIntDettaglioProcedimentoDto> soggettiProcedimento = procedimentoDao
				.getSoggettiInt_DettaglioProcedimento(id, descRuolo, flagValido);
		Iterator<SogIntDettaglioProcedimentoDto> iter = soggettiProcedimento.iterator();
		while (iter.hasNext()) {
			SoggettoForm soggettoForm = new SoggettoForm();
			SogIntDettaglioProcedimentoDto currentDto = iter.next();
			mapperBean.map(currentDto, soggettoForm);
			// Art67 e Cauzione sono significativi solo per le misure PERSONALI
			if (soggettoForm.getMisura().getDescrizioneMisura() == null
					|| soggettoForm.getMisura().getDescrizioneMisura().contains("PATRIMONIALE")) {
				soggettoForm.getMisura().setArt67(null);
				soggettoForm.getMisura().setCauzione(null);
			}
			if (Constants.SOGGETTO_FISICO.equals(soggettoForm.getTipoSoggetto())) {
				// Aggiugo solo i soggetti fisici
				lista.add(soggettoForm);
			}
		}
		return lista;
	}

	public List<SoggettoForm> soggettiDirettiInteressatiDelProcedimento(long idProcedimento, String descRuolo,
			Character flagValido) {

		List<SoggettoForm> lista = new ArrayList<SoggettoForm>();
		TProcedimento id = new TProcedimento();
		id.setCodiKIdProc(idProcedimento);
		List<SogIntDettaglioProcedimentoDto> soggettiProcedimento = procedimentoDao
				.getSoggettiInt_DettaglioProcedimento(id, descRuolo, flagValido);
		Iterator<SogIntDettaglioProcedimentoDto> iter = soggettiProcedimento.iterator();
		while (iter.hasNext()) {
			SoggettoForm soggettoForm = new SoggettoForm();
			SogIntDettaglioProcedimentoDto currentDto = iter.next();
			mapperBean.map(currentDto, soggettoForm);
			// Art67 e Cauzione sono significativi solo per le misure PERSONALI
			if (soggettoForm.getMisura().getDescrizioneMisura() == null
					|| soggettoForm.getMisura().getDescrizioneMisura().contains("PATRIMONIALE")) {
				soggettoForm.getMisura().setArt67(null);
				soggettoForm.getMisura().setCauzione(null);
			}
			lista.add(soggettoForm);
		}
		return lista;
	}

	public List<SoggettoForm> soggettiDirettiInteressatiInvDelProcedimento(long idProcedimento, long idSogInv) {

		List<SoggettoForm> lista = new ArrayList<SoggettoForm>();
		List<SogIntDettaglioProcedimentoDto> soggettiProcedimento = procedimentoDao
				.getSoggettiIntInv_DettaglioProcedimento(idProcedimento, idSogInv);
		Iterator<SogIntDettaglioProcedimentoDto> iter = soggettiProcedimento.iterator();
		while (iter.hasNext()) {
			SoggettoForm soggettoForm = new SoggettoForm();
			SogIntDettaglioProcedimentoDto currentDto = iter.next();
			mapperBean.map(currentDto, soggettoForm);
			// Art67 e Cauzione sono significativi solo per le misure PERSONALI
			if (soggettoForm.getMisura().getDescrizioneMisura() == null
					|| soggettoForm.getMisura().getDescrizioneMisura().contains("PATRIMONIALE")) {
				soggettoForm.getMisura().setArt67(null);
				soggettoForm.getMisura().setCauzione(null);
			}
			soggettoForm.setNote(currentDto.getNOTE());
			lista.add(soggettoForm);
		}
		return lista;
	}

	public List<SoggettoForm> soggettiDirettiInteressatiDelProcedimentoVer(long idProcedimento, String descRuolo,
			Character flagValido, long idSogg, long idUffOwn, Integer idVer) {

		List<SoggettoForm> lista = new ArrayList<SoggettoForm>();
		TProcedimento id = new TProcedimento();
		id.setCodiKIdProc(idProcedimento);
		List<SogIntDettaglioProcedimentoDto> soggettiProcedimento = procedimentoDao
				.getSoggettiInt_DettaglioProcedimentoVer(id, descRuolo, flagValido, idSogg, idUffOwn, idVer);
		Iterator<SogIntDettaglioProcedimentoDto> iter = soggettiProcedimento.iterator();
		while (iter.hasNext()) {
			SoggettoForm soggettoForm = new SoggettoForm();
			SogIntDettaglioProcedimentoDto currentDto = iter.next();
			mapperBean.map(currentDto, soggettoForm);
			// Art67 e Cauzione sono significativi solo per le misure PERSONALI
			if (soggettoForm.getMisura().getDescrizioneMisura() == null
					|| soggettoForm.getMisura().getDescrizioneMisura().contains("PATRIMONIALE")) {
				soggettoForm.getMisura().setArt67(null);
				soggettoForm.getMisura().setCauzione(null);
			}
			lista.add(soggettoForm);
		}
		return lista;
	}

	/*
	 * carico tutti i soggetti del procedimento riunente e verifico se i soggetti
	 * del procedimento riunito sono presenti (e prendo anche il RUOLO) anche in
	 * quello riunente.
	 */

	public List<SoggettoForm> soggettiDirettiInteressatiDelProcedimentoRiunente(long idProcedimentoRiunente,
			String descRuolo, Character flagValido, long idProcedimentoRiunito) {

		List<SoggettoForm> lista = new ArrayList<SoggettoForm>();
		TProcedimento id = new TProcedimento();
		id.setCodiKIdProc(idProcedimentoRiunente);
		List<SogIntDettaglioProcedimentoDto> soggettiProcedimento = procedimentoDao
				.getSoggettiInt_DettaglioProcedimento(id, descRuolo, flagValido);
		Iterator<SogIntDettaglioProcedimentoDto> iter = soggettiProcedimento.iterator();
		while (iter.hasNext()) {
			SoggettoForm soggettoForm = new SoggettoForm();
			SogIntDettaglioProcedimentoDto currentDto = iter.next();
			mapperBean.map(currentDto, soggettoForm);
			SogIntDettaglioProcedimentoDto sogInt = procedimentoDao.getSogIntById(currentDto.getID_PERS().longValue(),
					currentDto.getID_UFF_OWNER().longValue(), idProcedimentoRiunito, null);

			if (sogInt != null) {
				soggettoForm.setEsisteInProcedeimentoRiunito(Constants.Flag.SI + " (" + sogInt.getDESC_RUOLO() + ")");
			} else {
				soggettoForm.setEsisteInProcedeimentoRiunito(Constants.Flag.NO);
			}

			lista.add(soggettoForm);
		}
		return lista;
	}

	public List<SoggettoForm> soggettiTitolariDelProcedimento(long idProcedimento) {

		List<SoggettoForm> lista = new ArrayList<SoggettoForm>();
		List<SogTitDettaglioProcedimentoDto> soggettiProcedimento = procedimentoDao
				.getSoggettiTit_DettaglioProcedimento(idProcedimento);
		Iterator<SogTitDettaglioProcedimentoDto> iter = soggettiProcedimento.iterator();
		while (iter.hasNext()) {
			SoggettoForm soggettoForm = new SoggettoForm();
			SogTitDettaglioProcedimentoDto currentDto = iter.next();
			mapperBean.map(currentDto, soggettoForm);

			lista.add(soggettoForm);
		}
		return lista;
	}

	@SuppressWarnings("deprecation")
	public List<SoggettoForm> soggettiDirettiInteressatiDelProcedimentoMock(long idProcedimento) {

		List<SoggettoForm> lista = new ArrayList<SoggettoForm>();
		SoggettoForm soggetto1 = new SoggettoForm();
		soggetto1.setIdSoggetto(423L);
		soggetto1.setNome("Carl");
		soggetto1.setCognome("Barks");
		soggetto1.setComuneNascita(new ComboForm("", "Roma"));
		soggetto1.setDataNascita(new Date(1, 4, 27));

		try {
			soggetto1.setDataIscrizione(new SimpleDateFormat("dd/MM/yyyy").parse("21/10/2011"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		MisuraForm misura = new MisuraForm();
		soggetto1.setMisura(misura);
		soggetto1.getMisura().setDescrizioneMisura("Personale");
		soggetto1.getMisura().setCauzione("");
		soggetto1.getMisura().setArt67("");
		lista.add(soggetto1);
		SoggettoForm soggetto2 = new SoggettoForm();
		soggetto2.setIdSoggetto(424L);
		soggetto2.setNome("Mario");
		soggetto2.setCognome("Rossi");
		soggetto2.setComuneNascita(new ComboForm("", "Catanzaro"));
		soggetto2.setDataNascita(new Date(85, 11, 16));

		try {
			soggetto2.setDataIscrizione(new SimpleDateFormat("dd/MM/yyyy").parse("25/10/2011"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		MisuraForm misura2 = new MisuraForm();
		soggetto2.setMisura(misura2);
		soggetto2.getMisura().setDescrizioneMisura("Personale");
		soggetto2.getMisura().setCauzione("SI");
		soggetto2.getMisura().setArt67("SI");
		lista.add(soggetto2);
		SoggettoForm soggetto3 = new SoggettoForm();
		soggetto3.setIdSoggetto(425L);
		soggetto3.setRagioneSociale("Zeta & CO S.N.C.");
		soggetto3.setPartitaIva("0764352056");
		soggetto3.setIdVersione(1L);

		try {
			soggetto3.setDataIscrizione(new SimpleDateFormat("dd/MM/yyyy").parse("25/10/2011"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		MisuraForm misura3 = new MisuraForm();
		soggetto3.setMisura(misura3);
		soggetto3.getMisura().setDescrizioneMisura("Patrimoniale");
		soggetto3.getMisura().setCauzione("SI");
		soggetto3.getMisura().setArt67("NO");
		lista.add(soggetto3);
		SoggettoForm soggetto4 = new SoggettoForm();
		soggetto4.setIdSoggetto(426L);
		soggetto4.setRagioneSociale("DREAM S.R.L.");
		soggetto4.setPartitaIva("(123456789");
		soggetto4.setIdVersione(1L);

		try {
			soggetto4.setDataIscrizione(new SimpleDateFormat("dd/MM/yyyy").parse("25/10/2011"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		MisuraForm misura4 = new MisuraForm();
		soggetto4.setMisura(misura4);
		soggetto4.getMisura().setDescrizioneMisura("Personale e Patrimoniale");
		soggetto4.getMisura().setCauzione("SI");
		soggetto4.getMisura().setArt67("SI");
		lista.add(soggetto4);
		return lista;
	}

	public List<SoggettoForm> soggettiInteressatiDelProcedimentoPerRegistrazioneAggravamento(Long idProcedimento,
			int idUfficioUtente) {

		List<TRelAttoMisPers> relazioni = procedimentoDao.misurePersonaliIscrizioneAggravamento(idProcedimento,
				idUfficioUtente, null);
		if ((relazioni == null) || relazioni.isEmpty()) {
			return null;
		}
		Set<Long> idSoggettiSet = new HashSet<Long>();
		for (TRelAttoMisPers tmp : relazioni) {
			long idSoggetto = tmp.getTMisuraPers().getTSoggetto().getId().getCodiKIdSoggInt();
			idSoggettiSet.add(idSoggetto);
		}
		List<SoggettoForm> soggetti = soggettiDirettiInteressatiDelProcedimento(idProcedimento);
		List<SoggettoForm> risultati = new ArrayList<SoggettoForm>();
		for (SoggettoForm soggetto : soggetti) {
			if (idSoggettiSet.contains(soggetto.getIdSoggetto())) {
				risultati.add(soggetto);
			}
		}
		return risultati;
	}

	public List<TRelMagProc> verificaOrdineMagistrati(final long idProcedimento, final int idUfficioUtente,
			final byte numOrdine, final long idMagistrato) {

		return procedimentoDao.verificaOrdineMagistratoProcedimento(idProcedimento, idUfficioUtente, numOrdine,
				idMagistrato);
	}

	private void appendiFoglieAggravamento(ProcedimentoTree tree, long idProcedimento, long tipoUfficio) {

		Node propostaRoot = tree.getIndex().get(ProcedimentoTree.PROPOSTA);
		long idUfficio = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		if (tipoUfficio == ProcedimentoTree.PROCURA_REP) {
			Node iscrizioneProcedimentoNode = tree.getIndex().get(ProcedimentoTree.ISCRIZIONE_PROCEDIMENTO);
			List<AttoForm> fonte = attoService.getAttiByProcedimento(idProcedimento, 12l, null);
			inizializzaNodi(fonte, iscrizioneProcedimentoNode, 12l, -1l);

			Node chiusuraIstruttoriaNode = tree.getIndex().get(ProcedimentoTree.CHIUSURA_ISTRUTTORIA);

			// caricamento della proposta
			AttoForm proposta = dettaglioPropostaAtto(idUfficio, idProcedimento, -1, true);
			Long idTipologia = Long.valueOf(proposta.getTipoAtto().getId());
			Long idCategoria = Long.valueOf(proposta.getCategoriaAtto().getId());

			List<AttoForm> propostaTmp = new ArrayList<AttoForm>();
			propostaTmp.add(proposta);
			if (proposta.getCodAtto() > 0) {
				inizializzaNodi(propostaTmp, chiusuraIstruttoriaNode, 9, 2);
			}

			Node udienzaNode = tree.getIndex().get(ProcedimentoTree.UDIENZA);
			List<AttoForm> decretiFissazioneUdienza = attoService.getAttiByProcedimento(idProcedimento, 3l,
					Constants.DECRETO_FISSAZIONE_UDIENZA + "," + Constants.DECRETO_ANTICIPAZIONE_UDIENZA + ","
							+ Constants.DECRETO_RINVIO_UDIENZA);
			inizializzaNodi(decretiFissazioneUdienza, udienzaNode, 3l,
					Long.parseLong(Constants.DECRETO_FISSAZIONE_UDIENZA));

			Node verbaliNode = tree.getIndex().get(ProcedimentoTree.VERBALI_UDIENZE);
			List<AttoForm> attiVerbali = attoService.getAttiByProcedimento(idProcedimento, Long.valueOf("16"),
					"313, 21");
			inizializzaNodi(attiVerbali, verbaliNode, Long.valueOf("16"));

			Node impugnazioniIGradoNode = tree.getIndex().get(ProcedimentoTree.IMPUGNAZIONI_PRIMO_GRADO);
			List<AttoForm> impugnazioniIGrado = attoService.getAttiByProcedimento(idProcedimento, 4, "5,337,338");
			inizializzaNodi(impugnazioniIGrado, impugnazioniIGradoNode, 4, -1l);

			List<AttoForm> decretiDecisoriIGrado = attoService.getAttiByProcedimento(idProcedimento, 3,
					Constants.DECRETO_DECISORIO_I_GRADO);
			inizializzaNodi(decretiDecisoriIGrado, chiusuraIstruttoriaNode, 3, -1l);

			Node dibattimentoIIGradoNode = tree.getIndex().get(ProcedimentoTree.DIBATTIMENTO_SECONDO_GRADO);
			List<AttoForm> decretiDecisoriIIGrado = attoService.getAttiByProcedimento(idProcedimento, 18,
					Constants.DECRETO_DECISORIO_II_GRADO);
			inizializzaNodi(decretiDecisoriIIGrado, dibattimentoIIGradoNode, 18, -1l);

			// popola il nodo attiEsecuzioneGiudDef con TIPO_ATTO_ARCHIVIAZIONE
			Node esecuzioneGiudDefNode = tree.getIndex().get(ProcedimentoTree.CHIUSURA_ISTRUTTORIA);
			List<AttoForm> attiEsecuzioneAttiInArchivio = attoService.getAttiByProcedimento(idProcedimento,
					Long.valueOf(Constants.TIPO_ATTO_ARCHIVIAZIONE), "17,18,19,20");
			inizializzaNodi(attiEsecuzioneAttiInArchivio, esecuzioneGiudDefNode,
					Long.valueOf(Constants.TIPO_ATTO_ARCHIVIAZIONE), -1);

			// popola il nodo ATTI_GENERICI
			Node attiGenericiNode = tree.getIndex().get(ProcedimentoTree.ATTI_GENERICI);
			long tipoAtto = 14l;
			String categoriaAtto = getCategorieAtto(ProcedimentoTree.ATTI_GENERICI);
			List<AttoForm> attiGenerici = attoService.getAttiByProcedimento(idProcedimento, tipoAtto, categoriaAtto);
			inizializzaNodi(attiGenerici, attiGenericiNode, tipoAtto, -1l);
			tree.complete();
			return;
		} else if (tipoUfficio == ProcedimentoTree.TRIBUNALE) {
			// se è tribunale
			Node iscrizioneProcedimentoNode = tree.getIndex().get(ProcedimentoTree.ISCRIZIONE_PROCEDIMENTO);
			// caricamento della proposta
			AttoForm proposta = dettaglioPropostaAtto(idUfficio, idProcedimento, -1, true);
			Long idTipologia = Long.valueOf(proposta.getTipoAtto().getId());
			Long idCategoria = Long.valueOf(proposta.getCategoriaAtto().getId());

			List<AttoForm> propostaTmp = new ArrayList<AttoForm>();
			propostaTmp.add(proposta);
			if (proposta.getCodAtto() > 0) {
				inizializzaNodi(propostaTmp, iscrizioneProcedimentoNode, 9, 2);
			}
			Node dibattimentoPrimoGradoNode = tree.getIndex().get(ProcedimentoTree.DIBATTIMENTO_PRIMO_GRADO);

			Node udienzaNode = tree.getIndex().get(ProcedimentoTree.UDIENZA);
			List<AttoForm> decretiFissazioneUdienza = attoService.getAttiByProcedimento(idProcedimento, 3l,
					Constants.DECRETO_FISSAZIONE_UDIENZA + "," + Constants.DECRETO_ANTICIPAZIONE_UDIENZA + ","
							+ Constants.DECRETO_RINVIO_UDIENZA);
			inizializzaNodi(decretiFissazioneUdienza, udienzaNode, 3l,
					Long.parseLong(Constants.DECRETO_FISSAZIONE_UDIENZA));

			Node verbaliNode = tree.getIndex().get(ProcedimentoTree.VERBALI_UDIENZE);
			List<AttoForm> attiVerbali = attoService.getAttiByProcedimento(idProcedimento, Long.valueOf("16"),
					"313, 21");
			inizializzaNodi(attiVerbali, verbaliNode, Long.valueOf("16"));

			Node impugnazioniIGradoNode = tree.getIndex().get(ProcedimentoTree.IMPUGNAZIONI_PRIMO_GRADO);
			List<AttoForm> impugnazioniIGrado = attoService.getAttiByProcedimento(idProcedimento, 4, "5,337,338");
			inizializzaNodi(impugnazioniIGrado, impugnazioniIGradoNode, 4, -1l);

			Node postDibattimentoPrimoGradoNode = tree.getIndex().get(ProcedimentoTree.POST_DIBATTIMENTO_PRIMO_GRADO);

			List<AttoForm> decretiDecisoriIGrado = attoService.getAttiByProcedimento(idProcedimento, 3,
					Constants.DECRETO_DECISORIO_I_GRADO);
			inizializzaNodi(decretiDecisoriIGrado, postDibattimentoPrimoGradoNode, 3, -1l);

			Node dibattimentoIIGradoNode = tree.getIndex().get(ProcedimentoTree.DIBATTIMENTO_SECONDO_GRADO);
			List<AttoForm> decretiDecisoriIIGrado = attoService.getAttiByProcedimento(idProcedimento, 18,
					Constants.DECRETO_DECISORIO_II_GRADO);
			inizializzaNodi(decretiDecisoriIIGrado, dibattimentoIIGradoNode, 18, -1l);

			// popola il nodo attiEsecuzioneGiudDef con Atti in Archivio
			Node esecuzioneGiudDefNode = tree.getIndex().get(ProcedimentoTree.ESECUZIONE_GIUDIZIO_DEFINITIVO);
			List<AttoForm> attiEsecuzioneAttiInArchivio = attoService.getAttiByProcedimento(idProcedimento,
					Long.valueOf(Constants.TIPO_ATTO_ATTI_IN_ARCHIVIO), Constants.CategoriaAtto.ATTI_IN_ARCHIVIO);
			inizializzaNodi(attiEsecuzioneAttiInArchivio, esecuzioneGiudDefNode,
					Long.valueOf(Constants.CategoriaAtto.ATTI_IN_ARCHIVIO), -1);

			// popola il nodo ATTI_GENERICI
			Node attiGenericiNode = tree.getIndex().get(ProcedimentoTree.ATTI_GENERICI);
			long tipoAtto = 14l;
			String categoriaAtto = getCategorieAtto(ProcedimentoTree.ATTI_GENERICI);
			List<AttoForm> attiGenerici = attoService.getAttiByProcedimento(idProcedimento, tipoAtto, categoriaAtto);
			inizializzaNodi(attiGenerici, attiGenericiNode, tipoAtto, -1l);
			tree.complete();
			return;
		} else if (tipoUfficio == ProcedimentoTree.CORTE_D_APPELLO) {
			// se è corte d'appello
			Node iscrizioneProcedimentoNode = tree.getIndex().get(ProcedimentoTree.ISCRIZIONE_PROCEDIMENTO);
			List<AttoForm> impugnazioniIGrado = attoService.getAttiByProcedimento(idProcedimento, 4, "5,337,338");
			inizializzaNodi(impugnazioniIGrado, iscrizioneProcedimentoNode, 4, -1l);

			// Node dibattimentoSecondoGradoNode =
			// tree.getIndex().get(ProcedimentoTree.DIBATTIMENTO_SECONDO_GRADO);

			Node udienzaNode = tree.getIndex().get(ProcedimentoTree.UDIENZA);
			List<AttoForm> decretiFissazioneUdienza = attoService.getAttiByProcedimento(idProcedimento, 18l,
					Constants.DECRETO_FISSAZIONE_UDIENZA_II_GRADO + ","
							+ Constants.DECRETO_ANTICIPAZIONE_UDIENZA_II_GRADO + ","
							+ Constants.DECRETO_RINVIO_UDIENZA_II_GRADO);
			inizializzaNodi(decretiFissazioneUdienza, udienzaNode, 18l,
					Long.parseLong(Constants.DECRETO_FISSAZIONE_UDIENZA));

			Node verbaliNode = tree.getIndex().get(ProcedimentoTree.VERBALI_UDIENZE);
			List<AttoForm> attiVerbali = attoService.getAttiByProcedimento(idProcedimento, Long.valueOf("16"),
					"313, 21");
			inizializzaNodi(attiVerbali, verbaliNode, Long.valueOf("16"));

			Node postDibattimentoSecondoGradoNode = tree.getIndex()
					.get(ProcedimentoTree.POST_DIBATTIMENTO_SECONDO_GRADO);
			List<AttoForm> decretiDecisoriIIGrado = attoService.getAttiByProcedimento(idProcedimento, 18,
					Constants.DECRETO_DECISORIO_II_GRADO);
			inizializzaNodi(decretiDecisoriIIGrado, postDibattimentoSecondoGradoNode, 18, -1l);

			List<AttoForm> ImpugnazioniIIGrado = attoService.getAttiByProcedimento(idProcedimento, 19l, "24,335");
			inizializzaNodi(ImpugnazioniIIGrado, postDibattimentoSecondoGradoNode, 19l, -1l);

			// popola il nodo attiEsecuzioneGiudDef con Atti in Archivio
			Node esecuzioneGiudDefNode = tree.getIndex().get(ProcedimentoTree.ESECUZIONE_GIUDIZIO_DEFINITIVO);
			List<AttoForm> attiEsecuzioneAttiInArchivio = attoService.getAttiByProcedimento(idProcedimento,
					Long.valueOf(Constants.TIPO_ATTO_ATTI_IN_ARCHIVIO),
					Constants.CategoriaAtto.RESTITUZIONE_ATTI_IN_TRIBUNALE);
			inizializzaNodi(attiEsecuzioneAttiInArchivio, esecuzioneGiudDefNode,
					Long.valueOf(Constants.CategoriaAtto.RESTITUZIONE_ATTI_IN_TRIBUNALE), -1);

			// Giudizio legittimità
			Node sentenzaCassazione = tree.getIndex().get(ProcedimentoTree.GIUDIZIO_LEGITTIMITA);
			List<AttoForm> listaSentenzaCassazione = attoService.getAttiByProcedimento(idProcedimento, 21,
					Constants.SENTENZA_SU_RICORSO_II_GRADO);
			inizializzaNodi(listaSentenzaCassazione, sentenzaCassazione, 21, 40l);

			// popola il nodo ATTI_GENERICI
			Node attiGenericiNode = tree.getIndex().get(ProcedimentoTree.ATTI_GENERICI);
			long tipoAtto = 14l;
			String categoriaAtto = getCategorieAtto(ProcedimentoTree.ATTI_GENERICI);
			List<AttoForm> attiGenerici = attoService.getAttiByProcedimento(idProcedimento, tipoAtto, categoriaAtto);
			inizializzaNodi(attiGenerici, attiGenericiNode, tipoAtto, -1l);
			tree.complete();
			return;
		} else if (tipoUfficio == ProcedimentoTree.PG_CDA) {
			// se è Procura Generale
			Node iscrizioneProcedimentoNode = tree.getIndex().get(ProcedimentoTree.ISCRIZIONE_PROCEDIMENTO);
			List<AttoForm> proposta = attoService.getAttiByProcedimento(idProcedimento, 9, "2");
			List<AttoForm> decisorioIGrado = attoService.getAttiByProcedimento(idProcedimento, 3,
					Constants.DECRETO_DECISORIO_I_GRADO);
			List<AttoForm> procedimenti = ListUtils.union(proposta, decisorioIGrado);
			inizializzaNodi(procedimenti, iscrizioneProcedimentoNode, 9, -1l);

			Node impugnazioniNode = tree.getIndex().get(ProcedimentoTree.IMPUGNAZIONI);
			List<AttoForm> impugnazioniIGrado = attoService.getAttiByProcedimento(idProcedimento, 4, "5,337,338");
			inizializzaNodi(impugnazioniIGrado, impugnazioniNode, 4, -1l);

			Node verbaliNode = tree.getIndex().get(ProcedimentoTree.VERBALI_UDIENZE);
			List<AttoForm> attiVerbali = attoService.getAttiByProcedimento(idProcedimento, Long.valueOf("16"),
					"313, 21");
			inizializzaNodi(attiVerbali, verbaliNode, Long.valueOf("16"));

			Node udienzaNode = tree.getIndex().get(ProcedimentoTree.UDIENZA);
			List<AttoForm> decretiFissazioneUdienza = attoService.getAttiByProcedimento(idProcedimento, 18l,
					Constants.DECRETO_FISSAZIONE_UDIENZA_II_GRADO + ","
							+ Constants.DECRETO_ANTICIPAZIONE_UDIENZA_II_GRADO + ","
							+ Constants.DECRETO_RINVIO_UDIENZA_II_GRADO);
			inizializzaNodi(decretiFissazioneUdienza, udienzaNode, 18l,
					Long.parseLong(Constants.DECRETO_FISSAZIONE_UDIENZA));

			Node postDibattimentoSecondoGradoNode = tree.getIndex()
					.get(ProcedimentoTree.POST_DIBATTIMENTO_SECONDO_GRADO);
			List<AttoForm> decretiDecisoriIGrado = attoService.getAttiByProcedimento(idProcedimento, 18,
					Constants.DECRETO_DECISORIO_II_GRADO);
			inizializzaNodi(decretiDecisoriIGrado, postDibattimentoSecondoGradoNode, 3, -1l);

			List<AttoForm> ImpugnazioniIIGrado = attoService.getAttiByProcedimento(idProcedimento, 19l, "24,335");
			inizializzaNodi(ImpugnazioniIIGrado, postDibattimentoSecondoGradoNode, 19l, -1l);

			// Giudizio legittimità
			Node sentenzaCassazione = tree.getIndex().get(ProcedimentoTree.GIUDIZIO_LEGITTIMITA);
			List<AttoForm> listaSentenzaCassazione = attoService.getAttiByProcedimento(idProcedimento, 21,
					Constants.SENTENZA_SU_RICORSO_II_GRADO);
			inizializzaNodi(listaSentenzaCassazione, sentenzaCassazione, 21, 40l);

			Node attiGenericiNode = tree.getIndex().get(ProcedimentoTree.ATTI_GENERICI);
			long tipoAtto = 14l;
			String categoriaAtto = getCategorieAtto(ProcedimentoTree.ATTI_GENERICI);
			List<AttoForm> attiGenerici = attoService.getAttiByProcedimento(idProcedimento, tipoAtto, categoriaAtto);
			inizializzaNodi(attiGenerici, attiGenericiNode, tipoAtto, -1l);
			tree.complete();
			return;
		}
	}

	private void appendiFoglieCamereConsiglio(ProcedimentoTree tree, long idProcedimento, long tipoUfficio) {

		Node propostaRoot = tree.getIndex().get(ProcedimentoTree.PROPOSTA);
		long idUfficio = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		TProcedimento procedimento = procedimentoDao.getProcedimentoById(idProcedimento);
		int idTipoProcedimento = 0;
		if (procedimento != null && procedimento.getTTpTipoProc() != null)
			idTipoProcedimento = procedimento.getTTpTipoProc().getCodiKIdTipoProc();

		if (tipoUfficio == ProcedimentoTree.PROCURA_REP || tipoUfficio == ProcedimentoTree.PG_CDA) {
			Node iscrizioneProcedimentoNode = tree.getIndex().get(ProcedimentoTree.ISCRIZIONE_PROCEDIMENTO);

			Node chiusuraIstruttoriaNode = tree.getIndex().get(ProcedimentoTree.CHIUSURA_ISTRUTTORIA);

			boolean IIGrado = true;
			// caricamento della proposta

			AttoForm istanza = dettaglioPropostaAtto(idUfficio, idProcedimento, -1, true);
			if (istanza != null && istanza.getTipoAtto() != null && istanza.getTipoAtto().getId() != null) {
				Long idTipologia = Long.valueOf(istanza.getTipoAtto().getId());
				Long idCategoria = Long.valueOf(istanza.getCategoriaAtto().getId());

				if (idCategoria == 354L || idCategoria == 361L) {
					IIGrado = false;
				}

				List<AttoForm> istanzaTmp = new ArrayList<AttoForm>();
				istanzaTmp.add(istanza);
				if (istanza.getCodAtto() > 0) {
					inizializzaNodi(istanzaTmp, iscrizioneProcedimentoNode, idTipologia, idCategoria);
				}
			}

			Node udienzaNode = tree.getIndex().get(ProcedimentoTree.UDIENZA);
			List<AttoForm> decretiFissazioneUdienza = attoService.getAttiByProcedimento(idProcedimento, 3l,
					Constants.DECRETO_FISSAZIONE_UDIENZA + "," + Constants.DECRETO_ANTICIPAZIONE_UDIENZA + ","
							+ Constants.DECRETO_RINVIO_UDIENZA);
			inizializzaNodi(decretiFissazioneUdienza, udienzaNode, 3l,
					Long.parseLong(Constants.DECRETO_FISSAZIONE_UDIENZA));

			Node verbaliNode = tree.getIndex().get(ProcedimentoTree.VERBALI_UDIENZE);
			List<AttoForm> attiVerbali = attoService.getAttiByProcedimento(idProcedimento, Long.valueOf("16"),
					"313, 21");
			inizializzaNodi(attiVerbali, verbaliNode, Long.valueOf("16"));

			Node postDibattimentoPrimoGradoNode = tree.getIndex().get(ProcedimentoTree.POST_DIBATTIMENTO_PRIMO_GRADO);

			List<AttoForm> decretiDecisoriIGrado = attoService.getAttiByProcedimento(idProcedimento, 3,
					Constants.DECRETO_DECISORIO_I_GRADO_CAMERE);
			inizializzaNodi(decretiDecisoriIGrado, chiusuraIstruttoriaNode, 3, -1l);

			Node dibattimentoIIGradoNode = tree.getIndex().get(ProcedimentoTree.DIBATTIMENTO_SECONDO_GRADO);
			if (IIGrado) {
				List<AttoForm> decretiDecisoriIIGrado = attoService.getAttiByProcedimento(idProcedimento, 18,
						Constants.DECRETO_DECISORIO_II_GRADO_CAM);
				inizializzaNodi(decretiDecisoriIIGrado, dibattimentoIIGradoNode, 18, -1l);

			}

			List<AttoForm> ImpugnazioniIIGrado = attoService.getAttiByProcedimento(idProcedimento, 19l, "24,335");
			inizializzaNodi(ImpugnazioniIIGrado, dibattimentoIIGradoNode, 19l, -1l);

			Node impugnazioniIGradoNode = tree.getIndex().get(ProcedimentoTree.IMPUGNAZIONI_PRIMO_GRADO);
			List<AttoForm> impugnazioniIGrado = attoService.getAttiByProcedimento(idProcedimento, 4, "5,337,338");
			inizializzaNodi(impugnazioniIGrado, impugnazioniIGradoNode, 4, -1l);

			// popola il nodo attiEsecuzioneGiudDef con TIPO_ATTO_ARCHIVIAZIONE
			Node esecuzioneGiudDefNode = tree.getIndex().get(ProcedimentoTree.CHIUSURA_ISTRUTTORIA);
			List<AttoForm> attiEsecuzioneAttiInArchivio = attoService.getAttiByProcedimento(idProcedimento,
					Long.valueOf(Constants.TIPO_ATTO_ARCHIVIAZIONE), "17,18,19,20");
			inizializzaNodi(attiEsecuzioneAttiInArchivio, esecuzioneGiudDefNode,
					Long.valueOf(Constants.TIPO_ATTO_ARCHIVIAZIONE), -1);

			Node sentenzaCassazione = tree.getIndex().get(ProcedimentoTree.GIUDIZIO_LEGITTIMITA);
			List<AttoForm> listaSentenzaCassazioneIGr = attoService.getAttiByProcedimento(idProcedimento, 20, "39");
			inizializzaNodi(listaSentenzaCassazioneIGr, sentenzaCassazione, 20, -1l);

			List<AttoForm> listaSentenzaCassazioneIIIGr = attoService.getAttiByProcedimento(idProcedimento, 21,
					Constants.SENTENZA_SU_RICORSO_II_GRADO);
			inizializzaNodi(listaSentenzaCassazioneIIIGr, sentenzaCassazione, 21, -1l);

			// popola il nodo ATTI_GENERICI
			Node attiGenericiNode = tree.getIndex().get(ProcedimentoTree.ATTI_GENERICI);
			long tipoAtto = 14l;
			String categoriaAtto = getCategorieAtto(ProcedimentoTree.ATTI_GENERICI);
			List<AttoForm> attiGenerici = attoService.getAttiByProcedimento(idProcedimento, tipoAtto, categoriaAtto);
			inizializzaNodi(attiGenerici, attiGenericiNode, tipoAtto, -1l);
			tree.complete();
			return;
		} else if (tipoUfficio == ProcedimentoTree.TRIBUNALE) {
			// se è tribunale
			Node iscrizioneProcedimentoNode = tree.getIndex().get(ProcedimentoTree.ISCRIZIONE_PROCEDIMENTO);
			// caricamento della istanza
			AttoForm istanza = dettaglioPropostaAtto(idUfficio, idProcedimento, -1, true);
			boolean IIGrado = true;
			if (istanza != null && istanza.getTipoAtto() != null && istanza.getTipoAtto().getId() != null) {

				Long idTipologia = Long.valueOf(istanza.getTipoAtto().getId());
				Long idCategoria = Long.valueOf(istanza.getCategoriaAtto().getId());

				if (idCategoria == 354L || idCategoria == 361L) {
					IIGrado = false;
				}

				List<AttoForm> istanzaTmp = new ArrayList<AttoForm>();
				istanzaTmp.add(istanza);
				if (istanza.getCodAtto() > 0) {
					inizializzaNodi(istanzaTmp, iscrizioneProcedimentoNode, idTipologia, idCategoria);
				}
				Node dibattimentoPrimoGradoNode = tree.getIndex().get(ProcedimentoTree.DIBATTIMENTO_PRIMO_GRADO);
			}
			Node udienzaNode = tree.getIndex().get(ProcedimentoTree.UDIENZA);
			List<AttoForm> decretiFissazioneUdienza = attoService.getAttiByProcedimento(idProcedimento, 3l,
					Constants.DECRETO_FISSAZIONE_UDIENZA + "," + Constants.DECRETO_ANTICIPAZIONE_UDIENZA + ","
							+ Constants.DECRETO_RINVIO_UDIENZA);
			inizializzaNodi(decretiFissazioneUdienza, udienzaNode, 3l,
					Long.parseLong(Constants.DECRETO_FISSAZIONE_UDIENZA));

			Node verbaliNode = tree.getIndex().get(ProcedimentoTree.VERBALI_UDIENZE);
			List<AttoForm> attiVerbali = attoService.getAttiByProcedimento(idProcedimento, Long.valueOf("16"),
					"313, 21");
			inizializzaNodi(attiVerbali, verbaliNode, Long.valueOf("16"));

			Node impugnazioniIGradoNode = tree.getIndex().get(ProcedimentoTree.IMPUGNAZIONI_PRIMO_GRADO);
			List<AttoForm> impugnazioniIGrado = attoService.getAttiByProcedimento(idProcedimento, 4, "5,337,338");
			inizializzaNodi(impugnazioniIGrado, impugnazioniIGradoNode, 4, -1l);

			Node postDibattimentoPrimoGradoNode = tree.getIndex().get(ProcedimentoTree.POST_DIBATTIMENTO_PRIMO_GRADO);

			List<AttoForm> decretiDecisoriIGrado = attoService.getAttiByProcedimento(idProcedimento, 3,
					Constants.DECRETO_DECISORIO_I_GRADO_CAMERE);
			inizializzaNodi(decretiDecisoriIGrado, postDibattimentoPrimoGradoNode, 3, -1l);
			if (IIGrado) {
				if (idTipoProcedimento != Constants.TIPOPROC_TERZO_INTERVENIENTE_CAM) {
					Node dibattimentoIIGradoNode = tree.getIndex().get(ProcedimentoTree.DIBATTIMENTO_SECONDO_GRADO);
					List<AttoForm> decretiDecisoriIIGrado = attoService.getAttiByProcedimento(idProcedimento, 18,
							Constants.DECRETO_DECISORIO_II_GRADO_CAM);
					inizializzaNodi(decretiDecisoriIIGrado, dibattimentoIIGradoNode, 18, -1l);
				}
			}

			// Segnalazione EMS-92
			List<AttoForm> listaRicorsoInCassazione = attoService.getAttiByProcedimento(idProcedimento, 4,
					Constants.RICORSO_IN_CASSAZIONE_DA_TRIB);
			inizializzaNodi(listaRicorsoInCassazione, postDibattimentoPrimoGradoNode, 4, -1l);

			Node sentenzaCassazione = tree.getIndex().get(ProcedimentoTree.GIUDIZIO_LEGITTIMITA);
			List<AttoForm> listaSentenzaCassazioneIGr = attoService.getAttiByProcedimento(idProcedimento, 20, "39");
			inizializzaNodi(listaSentenzaCassazioneIGr, sentenzaCassazione, 20, -1l);

			List<AttoForm> listaSentenzaCassazioneIIIGr = attoService.getAttiByProcedimento(idProcedimento, 21,
					Constants.SENTENZA_SU_RICORSO_II_GRADO);
			inizializzaNodi(listaSentenzaCassazioneIIIGr, sentenzaCassazione, 21, -1l);

			// popola il nodo attiEsecuzioneGiudDef con Atti in Archivio
			if (idTipoProcedimento != Constants.TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM) {
				Node esecuzioneGiudDefNode = tree.getIndex().get(ProcedimentoTree.ESECUZIONE_GIUDIZIO_DEFINITIVO);
				List<AttoForm> attiEsecuzioneAttiInArchivio = attoService.getAttiByProcedimento(idProcedimento,
						Long.valueOf(Constants.TIPO_ATTO_ATTI_IN_ARCHIVIO), Constants.CategoriaAtto.ATTI_IN_ARCHIVIO);
				inizializzaNodi(attiEsecuzioneAttiInArchivio, esecuzioneGiudDefNode,
						Long.valueOf(Constants.CategoriaAtto.ATTI_IN_ARCHIVIO), -1);
			}

			// popola il nodo ATTI_GENERICI
			Node attiGenericiNode = tree.getIndex().get(ProcedimentoTree.ATTI_GENERICI);
			long tipoAtto = 14l;
			String categoriaAtto = getCategorieAtto(ProcedimentoTree.ATTI_GENERICI);
			List<AttoForm> attiGenerici = attoService.getAttiByProcedimento(idProcedimento, tipoAtto, categoriaAtto);
			inizializzaNodi(attiGenerici, attiGenericiNode, tipoAtto, -1l);
			tree.complete();
			return;
		} else if (tipoUfficio == ProcedimentoTree.CORTE_D_APPELLO) {

			// se è corte d'appello
			Node iscrizioneProcedimentoNode = tree.getIndex().get(ProcedimentoTree.ISCRIZIONE_PROCEDIMENTO);
			List<AttoForm> impugnazioniIGrado = attoService.getAttiByProcedimento(idProcedimento, 4, "5,337,338");
			inizializzaNodi(impugnazioniIGrado, iscrizioneProcedimentoNode, 4, -1l);

			AttoForm istanza = dettaglioPropostaAtto(idUfficio, idProcedimento, -1, true);
			boolean IIGrado = true;
			if (istanza != null && istanza.getTipoAtto() != null && istanza.getTipoAtto().getId() != null) {

				Long idTipologia = Long.valueOf(istanza.getTipoAtto().getId());
				Long idCategoria = Long.valueOf(istanza.getCategoriaAtto().getId());

				if (idCategoria == 354L || idCategoria == 361L) {
					IIGrado = false;
				}

				List<AttoForm> istanzaTmp = new ArrayList<AttoForm>();
				istanzaTmp.add(istanza);
				if (istanza.getCodAtto() > 0) {
					inizializzaNodi(istanzaTmp, iscrizioneProcedimentoNode, idTipologia, idCategoria);
				}
			}

			Node udienzaNode = tree.getIndex().get(ProcedimentoTree.UDIENZA);
			List<AttoForm> decretiFissazioneUdienza = attoService.getAttiByProcedimento(idProcedimento, 18l,
					Constants.DECRETO_FISSAZIONE_UDIENZA_II_GRADO + ","
							+ Constants.DECRETO_ANTICIPAZIONE_UDIENZA_II_GRADO + ","
							+ Constants.DECRETO_RINVIO_UDIENZA_II_GRADO + ","
							+ Constants.DECRETO_ASSEGNAZIONE_NUOVO_RUOLO_II_GRADO + ","
							+ Constants.DECRETO_REMISSIONE_SU_RUOLO_II_GRADO);
			inizializzaNodi(decretiFissazioneUdienza, udienzaNode, 18l,
					Long.parseLong(Constants.DECRETO_FISSAZIONE_UDIENZA));

			Node verbaliNode = tree.getIndex().get(ProcedimentoTree.VERBALI_UDIENZE);
			List<AttoForm> attiVerbali = attoService.getAttiByProcedimento(idProcedimento, Long.valueOf("16"),
					"313, 21");
			inizializzaNodi(attiVerbali, verbaliNode, Long.valueOf("16"));

			if (idTipoProcedimento != Constants.TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM) {

				Node postDibattimentoSecondoGradoNode = tree.getIndex()
						.get(ProcedimentoTree.POST_DIBATTIMENTO_SECONDO_GRADO);

				List<AttoForm> decretiDecisoriIGrado = attoService.getAttiByProcedimento(idProcedimento, 18,
						"373, 359");
				inizializzaNodi(decretiDecisoriIGrado, postDibattimentoSecondoGradoNode, 18, -1l);

				Node dibattimentoIIGradoNode = tree.getIndex().get(ProcedimentoTree.DIBATTIMENTO_SECONDO_GRADO);
				// Segnalazione EMS-129
				List<AttoForm> decretiDecisoriIIGrado = attoService.getAttiByProcedimento(idProcedimento, 18,
						"330,331,374,375");
				inizializzaNodi(decretiDecisoriIIGrado, dibattimentoIIGradoNode, 18, -1l);

				List<AttoForm> ImpugnazioniIIGrado = attoService.getAttiByProcedimento(idProcedimento, 19l, "24,335");
				inizializzaNodi(ImpugnazioniIIGrado, dibattimentoIIGradoNode, 19l, -1l);

				// popola il nodo attiEsecuzioneGiudDef con Atti in Archivio
				Node esecuzioneGiudDefNode = tree.getIndex().get(ProcedimentoTree.ESECUZIONE_GIUDIZIO_DEFINITIVO);
				List<AttoForm> attiEsecuzioneAttiInArchivio = attoService.getAttiByProcedimento(idProcedimento,
						Long.valueOf(Constants.TIPO_ATTO_ATTI_IN_ARCHIVIO),
						Constants.CategoriaAtto.RESTITUZIONE_ATTI_IN_TRIBUNALE);
				inizializzaNodi(attiEsecuzioneAttiInArchivio, esecuzioneGiudDefNode,
						Long.valueOf(Constants.CategoriaAtto.RESTITUZIONE_ATTI_IN_TRIBUNALE), -1);
			}

			Node sentenzaCassazione = tree.getIndex().get(ProcedimentoTree.GIUDIZIO_LEGITTIMITA);
			List<AttoForm> listaSentenzaCassazione = attoService.getAttiByProcedimento(idProcedimento, 21,
					Constants.SENTENZA_SU_RICORSO_II_GRADO);
			inizializzaNodi(listaSentenzaCassazione, sentenzaCassazione, 21, 40l);

			// popola il nodo ATTI_GENERICI
			Node attiGenericiNode = tree.getIndex().get(ProcedimentoTree.ATTI_GENERICI);
			long tipoAtto = 14l;
			String categoriaAtto = getCategorieAtto(ProcedimentoTree.ATTI_GENERICI);
			List<AttoForm> attiGenerici = attoService.getAttiByProcedimento(idProcedimento, tipoAtto, "16");
			inizializzaNodi(attiGenerici, attiGenericiNode, tipoAtto, -1l);
			tree.complete();
			return;
		}
	}

	@SuppressWarnings("unused")
	private String getStringWithVirgola(String... voce) {
		StringBuilder builder = new StringBuilder();
		for (String string : voce) {
			if (builder.length() == 0) {
				builder.append(string);
			} else {
				builder.append(" , ").append(string);
			}
		}
		return builder.toString();
	}

	private String getStringWithVirgola(int... voce) {
		StringBuilder builder = new StringBuilder();
		for (int intero : voce) {
			if (builder.length() == 0) {
				builder.append(intero);
			} else {
				builder.append(" , ").append(intero);
			}
		}
		return builder.toString();
	}

	private void appendiFoglieOpposizioneRestituzione(ProcedimentoTree tree, long idProcedimento, long tipoUfficio) {

		Node opposizioneRoot = tree.getIndex().get(ProcedimentoTree.OPPOSIZIONE_RESTITUZIONE);
		Node dibattimento = tree.getIndex().get(ProcedimentoTree.DIBATTIMENTO_SECONDO_GRADO);
		Node opposizioneUdienze = tree.getIndex().get(ProcedimentoTree.UDIENZA);

		Node chiusuraAccertamentoRoot = tree.getIndex().get(ProcedimentoTree.CHIUSURA_ACCERTAMENTO);
		long idUfficio = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		JsNodeInfo nodeInfo = new JsNodeInfo();
		List<AttoForm> attiOpposizioneRestituzione = attoService.getAttiByProcedimento(idProcedimento,
				Long.valueOf("4"), "6");
		List<AttoForm> attiOpposizioneRestituzioneSentenza = attoService.getAttiByProcedimento(idProcedimento,
				Long.valueOf("18"), "23");

		List<AttoForm> attiOpposizioneRestituzioneUdienze = attoService.getAttiByProcedimento(idProcedimento,
				Long.valueOf("18"), "25,59,60,65,66");

		// AttoForm accertamento = attiAccertamento.get(0);
		if (tipoUfficio == ProcedimentoTree.PROCURA_REP) {
			// se è procura
			inizializzaNodi(attiOpposizioneRestituzione, opposizioneRoot,
					Long.valueOf(Constants.TIPO_ATTO_ACCERTAMENTO));
			tree.complete();
			return;
		} else if (tipoUfficio == ProcedimentoTree.TRIBUNALE) {
			// se è tribunale
			inizializzaNodi(attiOpposizioneRestituzione, opposizioneRoot, Long.valueOf("4"));
			tree.complete();
			return;
		} else if (tipoUfficio == ProcedimentoTree.CORTE_D_APPELLO) {
			inizializzaNodi(attiOpposizioneRestituzione, opposizioneRoot, Long.valueOf("4"));
			inizializzaNodi(attiOpposizioneRestituzioneSentenza, dibattimento, Long.valueOf("18"));
			inizializzaNodi(attiOpposizioneRestituzioneUdienze, opposizioneUdienze, Long.valueOf("18"));
			tree.complete();
			return;
		} else if (tipoUfficio == ProcedimentoTree.PG_CDA) {
			inizializzaNodi(attiOpposizioneRestituzione, opposizioneRoot, Long.valueOf("4"));
			inizializzaNodi(attiOpposizioneRestituzioneSentenza, dibattimento, Long.valueOf("18"));
			inizializzaNodi(attiOpposizioneRestituzioneUdienze, opposizioneUdienze, Long.valueOf("18"));
			tree.complete();
			return;
		}
	}

	private void appendiFoglieAccertamento(ProcedimentoTree tree, long idProcedimento, long tipoUfficio) {

		Node accertamentoRoot = tree.getIndex().get(ProcedimentoTree.ACCERTAMENTO);
		Node chiusuraAccertamentoRoot = tree.getIndex().get(ProcedimentoTree.CHIUSURA_ACCERTAMENTO);
		long idUfficio = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		List<AttoForm> attiAccertamento = attoService.getAttiByProcedimento(idProcedimento, Long.valueOf("26"), "312");
		// AttoForm accertamento = attiAccertamento.get(0);
		if (tipoUfficio == ProcedimentoTree.PROCURA_REP) {
			// se è procura
			inizializzaNodi(attiAccertamento, accertamentoRoot, Long.valueOf(Constants.TIPO_ATTO_ACCERTAMENTO));
			tree.complete();
			return;
		} else if (tipoUfficio == ProcedimentoTree.TRIBUNALE) {
			// se è tribunale
			inizializzaNodi(attiAccertamento, accertamentoRoot, Long.valueOf("26"));
			tree.complete();
			return;
		} else if (tipoUfficio == ProcedimentoTree.CORTE_D_APPELLO) {
			// se è corte d'appello
			inizializzaNodi(attiAccertamento, accertamentoRoot, Long.valueOf("26"));
			tree.complete();
			return;
		}
	}

	private void appendiFoglieOpposizione(ProcedimentoTree tree, long idProcedimento, long tipoUfficio) {

		Node istanzaRoot = tree.getIndex().get(ProcedimentoTree.ISTANZA_SOGG_DIFFIDATO);
		Node postDibattimentoPrimoGradoRoot = tree.getIndex().get(ProcedimentoTree.POST_DIBATTIMENTO_PRIMO_GRADO);
		Node udienzeRoot = tree.getIndex().get(ProcedimentoTree.UDIENZA);
		Node verbaliRoot = tree.getIndex().get(ProcedimentoTree.VERBALI_UDIENZE);
		long idUfficio = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		String categoriaAtto = null;
		List<AttoForm> attoIstanza = attoService.getAttiByProcedimento(idProcedimento,
				Long.valueOf(Constants.TIPO_ATTO_ISTANZA), Constants.ISTANZA_DIFFIDA_SOGGETTO);
		// AttoForm accertamento = attiAccertamento.get(0);
		if (tipoUfficio == ProcedimentoTree.PROCURA_REP) {
			// se è procura
			inizializzaNodi(attoIstanza, istanzaRoot, Long.valueOf(Constants.TIPO_ATTO_ISTANZA));
			tree.complete();
			return;
		} else if (tipoUfficio == ProcedimentoTree.TRIBUNALE) {
			// se è tribunale
			inizializzaNodi(attoIstanza, istanzaRoot, Long.valueOf(Constants.TIPO_ATTO_ISTANZA));
			List<AttoForm> decretoDecisorio = attoService.getAttiByProcedimento(idProcedimento,
					Long.valueOf(Constants.TIPO_ATTO_DECRETO), Constants.DECRETO_DECISORIO_I_GRADO);
			inizializzaNodi(decretoDecisorio, postDibattimentoPrimoGradoRoot, Long.valueOf(Constants.TIPO_ATTO_DECRETO),
					Long.valueOf(Constants.DECRETO_DECISORIO_I_GRADO));
			categoriaAtto = Constants.DECRETO_FISSAZIONE_UDIENZA + "," + Constants.DECRETO_ANTICIPAZIONE_UDIENZA + ","
					+ Constants.DECRETO_RINVIO_UDIENZA;
			List<AttoForm> decretiUdienze = attoService.getAttiByProcedimento(idProcedimento,
					Long.valueOf(Constants.TIPO_ATTO_DECRETO), categoriaAtto);
			if (tipoUfficio == ProcedimentoTree.PROCURA_REP) {
				inizializzaNodi(decretiUdienze, udienzeRoot, Long.valueOf(Constants.TIPO_ATTO_DECRETO),
						Long.valueOf(Constants.DECRETO_FISSAZIONE_UDIENZA));
			}

			List<UdienzaForm> verbaliUdienze = udienzaService.listaUdienzePerProcedimento(idProcedimento, idUfficio);
			inizializzaNodiVerbaliUdienza(verbaliUdienze, verbaliRoot);
			tree.complete();
			return;
		} else if (tipoUfficio == ProcedimentoTree.CORTE_D_APPELLO) {
			// // se è corte d'appello
			// inizializzaNodi(attiAccertamento, accertamentoRoot,
			// Long.valueOf("26"));
			// tree.complete();
			return;
		}
	}

	private void appendiFoglieRevocazione(ProcedimentoTree tree, long idProcedimento, long tipoUfficio) {

		Node revocazioneRoot = tree.getIndex().get(ProcedimentoTree.REVOCAZIONE_CONFISCA);
		Node udienzeNode = tree.getIndex().get(ProcedimentoTree.UDIENZA);
		Node postDibattimentoNode = tree.getIndex().get(ProcedimentoTree.POST_DIBATTIMENTO_SECONDO_GRADO);
		Node verbaliNode = tree.getIndex().get(ProcedimentoTree.VERBALI_UDIENZE);

		long idUfficio = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		JsNodeInfo nodeInfo = new JsNodeInfo();
		List<AttoForm> attoRevocazioneConfisca = attoService.getAttiByProcedimento(idProcedimento, Long.valueOf("25"),
				"328");

		List<AttoForm> attiRichiestaParere = attoService.getAttiByProcedimento(idProcedimento, Long.valueOf("14"),
				"330");
		List<AttoForm> attiParere = attoService.getAttiByProcedimento(idProcedimento, Long.valueOf("14"), "331");

		List<AttoForm> attiUdienze = attoService.getAttiByProcedimento(idProcedimento, Long.valueOf("18"),
				"25,59,60,65,66");
		List<AttoForm> attoSentenza = attoService.getAttiByProcedimento(idProcedimento, Long.valueOf("18"), "329");

		List<AttoForm> attiVerbali = attoService.getAttiByProcedimento(idProcedimento, Long.valueOf("16"), "313, 21");

		inizializzaNodi(attoRevocazioneConfisca, revocazioneRoot, Long.valueOf("25"));

		inizializzaNodi(attiRichiestaParere, revocazioneRoot, Long.valueOf("14"));
		inizializzaNodi(attiParere, revocazioneRoot, Long.valueOf("18"));

		inizializzaNodi(attiUdienze, udienzeNode, Long.valueOf("18"));
		inizializzaNodi(attoSentenza, postDibattimentoNode, Long.valueOf("18"));

		inizializzaNodi(attiVerbali, verbaliNode, Long.valueOf("16"));
		tree.complete();
	}

	private void appendiFoglieOpposizioneDivietiQuestore(ProcedimentoTree tree, long idProcedimento, long tipoUfficio) {

		Node istanzaNode = tree.getIndex().get(ProcedimentoTree.ISTANZA_SOGG_DIFFIDATO);
		Node dibattimentoPrimoGradoNode = tree.getIndex().get(ProcedimentoTree.DIBATTIMENTO_PRIMO_GRADO);
		Node udienzaNode = tree.getIndex().get(ProcedimentoTree.UDIENZA);
		Node verbaliNode = tree.getIndex().get(ProcedimentoTree.VERBALI_UDIENZE);
		Node postDibattimentoPrimoGradoNode = tree.getIndex().get(ProcedimentoTree.POST_DIBATTIMENTO_PRIMO_GRADO);
		Node impugnazioniIGradoNode = tree.getIndex().get(ProcedimentoTree.IMPUGNAZIONI_PRIMO_GRADO);
		Node giudizioLegittimitaNode = tree.getIndex().get(ProcedimentoTree.GIUDIZIO_LEGITTIMITA);
		Node attiGenericiNode = tree.getIndex().get(ProcedimentoTree.ATTI_GENERICI);

		List<AttoForm> attoIstanza = attoService.getAttiByProcedimento(idProcedimento,
				Long.valueOf(Constants.TIPO_ATTO_ISTANZA), Constants.ISTANZA_DIFFIDA_SOGGETTO);
		inizializzaNodi(attoIstanza, istanzaNode, Long.valueOf(Constants.TIPO_ATTO_ISTANZA));

		List<AttoForm> decretiFissazioneUdienza = attoService.getAttiByProcedimento(idProcedimento, 3l,
				Constants.DECRETO_FISSAZIONE_UDIENZA + "," + Constants.DECRETO_ANTICIPAZIONE_UDIENZA + ","
						+ Constants.DECRETO_RINVIO_UDIENZA);
		inizializzaNodi(decretiFissazioneUdienza, udienzaNode, 3l,
				Long.parseLong(Constants.DECRETO_FISSAZIONE_UDIENZA));

		List<AttoForm> attiVerbali = attoService.getAttiByProcedimento(idProcedimento, Long.valueOf("16"), "313, 21");
		inizializzaNodi(attiVerbali, verbaliNode, Long.valueOf("16"));

		List<AttoForm> impugnazioniIGrado = attoService.getAttiByProcedimento(idProcedimento, 4, "5,337,338");
		inizializzaNodi(impugnazioniIGrado, impugnazioniIGradoNode, 4, -1l);

		List<AttoForm> decretiDecisoriIGrado = attoService.getAttiByProcedimento(idProcedimento, 3,
				Constants.DECRETO_DECISORIO_I_GRADO);
		inizializzaNodi(decretiDecisoriIGrado, postDibattimentoPrimoGradoNode, 3, -1l);

		// popola il nodo attiEsecuzioneGiudDef con Atti in Archivio
		Node esecuzioneGiudDefNode = tree.getIndex().get(ProcedimentoTree.ESECUZIONE_GIUDIZIO_DEFINITIVO);
		List<AttoForm> attiEsecuzioneAttiInArchivio = attoService.getAttiByProcedimento(idProcedimento,
				Long.valueOf(Constants.TIPO_ATTO_ATTI_IN_ARCHIVIO), Constants.CategoriaAtto.ATTI_IN_ARCHIVIO);
		inizializzaNodi(attiEsecuzioneAttiInArchivio, esecuzioneGiudDefNode,
				Long.valueOf(Constants.CategoriaAtto.ATTI_IN_ARCHIVIO), -1);

		// popola il nodo ATTI_GENERICI
		long tipoAtto = 14l;
		String categoriaAtto = getCategorieAtto(ProcedimentoTree.ATTI_GENERICI);
		List<AttoForm> attiGenerici = attoService.getAttiByProcedimento(idProcedimento, tipoAtto, categoriaAtto);
		inizializzaNodi(attiGenerici, attiGenericiNode, tipoAtto, -1l);

		tree.complete();
		return;
	}

	private void inizializzaNodiVerbaliUdienza(List<UdienzaForm> udienze, Node node) {

		Iterator<UdienzaForm> iter = udienze.iterator();

		while (iter.hasNext()) {
			UdienzaForm currentUdienza = iter.next();
			JsNodeInfo nodeInfo = new JsNodeInfo();
			String titoloLabel = "Verbale udienza del " + Utility.convertDateToString(currentUdienza.getDataUdienza());
			nodeInfo.setLabel(titoloLabel);

			nodeInfo.setUrl(
					"javascript:caricaVerbaleUdienza(" + "\\\'" + currentUdienza.getCodUdienza() + "\\\'" + ")");
			nodeInfo.setLeafIcon("icon_file.gif");
			nodeInfo.setIdAtto(currentUdienza.getCodAtto());
			Node lNode = new Node(nodeInfo);
			node.addChild(lNode);
		}
	}

	private void associaSoggettoInteressatoAllAggravamento(SoggettoForm soggettoForm, Long idProc, Integer idUffOwn,
			String codUtente, Date now) {

		TPersonaFisica persona = new TPersonaFisica();
		TPersonaGiur personaGiur = new TPersonaGiur();
		TSoggetto soggInt = new TSoggetto();
		TRelProcSogint relProcSoggInt = new TRelProcSogint();
		mapperBean.map(soggettoForm, persona);
		mapperBean.map(soggettoForm, personaGiur);
		mapperBean.map(soggettoForm, soggInt);
		mapperBean.map(soggettoForm, relProcSoggInt);
		AssociaPersonaDto associaSoggetto = new AssociaPersonaDto();
		associaSoggetto.setPersFis(persona);
		associaSoggetto.setPersonaGiur(personaGiur);
		associaSoggetto.setSoggInt(soggInt);
		associaSoggetto.setRelProcSoggInt(relProcSoggInt);
		if ((associaSoggetto.getPersFis().getId() != null)
				&& (associaSoggetto.getPersFis().getId().getCodiKIdPersonaFis() > 0)
				&& (associaSoggetto.getPersFis().getDescCognome() != null)) {
			procedimentoDao.associaPersonaFisica(idUffOwn, codUtente, idProc, associaSoggetto, now);
		} else if ((associaSoggetto.getPersonaGiur().getId() != null)
				&& (associaSoggetto.getPersonaGiur().getId().getCodiKIdPersGiur() > 0)
				&& (associaSoggetto.getPersonaGiur().getDescRagioneSoc() != null)) {
			procedimentoDao.associaPersonaGiuridica(idUffOwn, codUtente, idProc, associaSoggetto, now);
		}
	}

	private List<SoggettoForm> caricaSoggettiAssociati(List lista, BeneForm beneForm, long idProcedimento, long idAtto,
			int idUfficioUtente) {

		List<SoggettoForm> risultati = new ArrayList<SoggettoForm>();
		Iterator itx = lista.iterator();
		while (itx.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) itx.next();
			TRelBeneAtto relBeneAtto = (TRelBeneAtto) row.get("rel");
			if (relBeneAtto.getId().getCodiKIdBene() == beneForm.getIdBene()) {
				SoggettoForm risultato = creaSoggettoForm(relBeneAtto, idProcedimento, idUfficioUtente);
				risultati.add(risultato);
			}
		}
		return risultati;
	}

	private List<SoggettoForm> caricaSoggettiAssociatiFiltrati(List lista, BeneForm beneForm, long idSoggettoDiretto,
			long idSoggettoTitolare, int idUfficioUtente) {

		List<SoggettoForm> risultati = new ArrayList<SoggettoForm>();
		Iterator itx = lista.iterator();
		while (itx.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) itx.next();
			TRelBeneAtto relBeneAtto = (TRelBeneAtto) row.get("rel");
			if (relBeneAtto.getId().getCodiKIdBene() == beneForm.getIdBene()) {
				TRelAttoMisPatr attoMisPatr = (TRelAttoMisPatr) row.get("mis");
				boolean aggiungiSoggetto = true;
				if (idSoggettoDiretto > 0) {
					if (idSoggettoDiretto == attoMisPatr.getId().getCodiKIdSoggInt()) {
						aggiungiSoggetto = true;
					} else {
						aggiungiSoggetto = false;
					}
				}
				if (idSoggettoTitolare > 0) {
					if (idSoggettoTitolare == attoMisPatr.getId().getCodiKIdSoggTit()) {
						aggiungiSoggetto = true;
					} else {
						aggiungiSoggetto = false;
					}
				}
				if (aggiungiSoggetto) {
					SoggettoForm risultato = creaSoggettoForm(relBeneAtto, idUfficioUtente);
					risultati.add(risultato);
				}
			}
		}
		return risultati;
	}

	private void creaBeneSoggettoFormDaIdBene(long idProcedimento, long idAtto, Integer idUfficioUtente,
			List<BeneSoggettoForm> risultati, long idBene) {
		creaBeneSoggettoFormDaIdBene(idProcedimento, idAtto, idUfficioUtente, risultati, idBene, false);
	}

	private void creaBeneSoggettoFormDaIdBene(long idProcedimento, long idAtto, Integer idUfficioUtente,
			List<BeneSoggettoForm> risultati, long idBene, boolean isCda) {

		BeneSoggettoForm risultato = new BeneSoggettoForm();
		BeneForm bene = beneService.beneById(idUfficioUtente, idBene, idProcedimento);
		List<SoggettoForm> soggettiAssociatiAlBene = recuperaSoggettiAssociatiAlBene(bene, idProcedimento, idAtto,
				idUfficioUtente, isCda);
		// Controllo se tutti i soggetti hanno il check a true, imposto il
		// valore per disabilitare l'id Bene corrispondente
		boolean disabilita = true;
		for (SoggettoForm soggettoForm : soggettiAssociatiAlBene) {
			if (!soggettoForm.isDisabledCheckDefinitivita()) {
				disabilita = false;
				break;
			}
		}
		if (disabilita)
			bene.setDisabledCheckDefinitivita(true);
		// Fine disabilita check
		risultato.setBene(bene);
		risultato.setSoggetti(soggettiAssociatiAlBene);
		if ((soggettiAssociatiAlBene != null) && !soggettiAssociatiAlBene.isEmpty()) {
			risultati.add(risultato);
		}
	}

	@SuppressWarnings("unused")
	private void creaBeneSoggettoFormDaIdBeneFiltrata(long idProcedimento, long idAtto, Integer idUfficioUtente,
			List<BeneSoggettoForm> risultati, long idBene, long idSoggettoDiretto, long idSoggettoTitolare) {

		BeneSoggettoForm risultato = new BeneSoggettoForm();
		BeneForm bene = beneService.beneById(idUfficioUtente, idBene, idProcedimento);
		List<SoggettoForm> soggettiAssociatiAlBene = recuperaSoggettiAssociatiAlBeneFiltrati(bene, idProcedimento,
				idAtto, idUfficioUtente, idSoggettoDiretto, idSoggettoTitolare);
		// Controllo se tutti i soggetti hanno il check a true, imposto il
		// valore per disabilitare l'id Bene corrispondente
		boolean disabilita = true;
		for (SoggettoForm soggettoForm : soggettiAssociatiAlBene) {
			if (!soggettoForm.isDisabledCheckDefinitivita()) {
				disabilita = false;
				break;
			}
		}
		if (disabilita)
			bene.setDisabledCheckDefinitivita(true);
		// Fine disabilita check
		risultato.setBene(bene);
		risultato.setSoggetti(soggettiAssociatiAlBene);
		if ((soggettiAssociatiAlBene != null) && !soggettiAssociatiAlBene.isEmpty()) {
			risultati.add(risultato);
		}
	}

	private void creaBeneSoggettoFormDaIdBeneAssociato(long idSoggetto, long tipoInt, long idProcedimento, long idAtto,
			Integer idUfficioUtente, List<BeneSoggettoForm> risultati, long idBene, boolean isDecisorio) {

		BeneSoggettoForm risultato = new BeneSoggettoForm();
		BeneForm bene = beneService.beneById(idUfficioUtente, idBene, idProcedimento);
		List<SoggettoForm> soggettiAssociatiAlBene;
		if (isDecisorio) {
			soggettiAssociatiAlBene = recuperaSoggettiDecisorioBeni(bene, idSoggetto, idProcedimento, idAtto,
					idUfficioUtente);
		} else {
			soggettiAssociatiAlBene = recuperaSoggettiAssociatiAlBeneEAlSoggetto(bene, idSoggetto, idProcedimento,
					idAtto, idUfficioUtente);
		}
		// tipo 4 decisorio
		AttoForm attoForm = attoService.getAttoById(idAtto, idUfficioUtente);
		risultato.setBene(bene);
		risultato.setSoggetti(soggettiAssociatiAlBene);

		if ((soggettiAssociatiAlBene != null) && !soggettiAssociatiAlBene.isEmpty()) {
			/*
			 * if ((attoForm != null) && (attoForm.getTipoAtto() != null) && (Constants
			 * .TIPO_ATTO_DECRETO.equals(attoForm.getTipoAtto().getId()) || Constants
			 * .TIPO_ATTO_DECRETO_II_GRADO.equals(attoForm.getTipoAtto() .getId()))) {
			 * TRelBeneAtto rel = getRelazioneBeneAttoByAttoAndBene(idBene, idProcedimento,
			 * idUfficioUtente, idAtto, idSoggetto, tipoInt); ComboForm cf = new
			 * ComboForm(); if(Constants.TIPO_ATTO_DECRETO_II_GRADO.equals(attoForm
			 * .getTipoAtto().getId())) { if (rel.getTTpEsitoByCodiFkIdEsito2() != null) {
			 * cf.setId(((Integer)rel.getTTpEsitoByCodiFkIdEsito2().getCodiKIdEsito
			 * ()).toString()); cf.setDescrizione(rel.getTTpEsitoByCodiFkIdEsito2
			 * ().getDescEsito()); risultato.getBene().setDecisioneBene(cf); } }else{ if
			 * (rel.getTTpEsitoByCodiFkIdEsito1() != null) { cf.setId((
			 * (Integer)rel.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito ()).toString());
			 * cf.setDescrizione(rel.getTTpEsitoByCodiFkIdEsito1 ().getDescEsito());
			 * risultato.getBene().setDecisioneBene(cf);
			 * risultato.getBene().setDecisioneBeneIGrado(cf); } }
			 * 
			 * }
			 */
			risultati.add(risultato);
		}
	}

	private void creaBeneSoggettoFormDaIdBeneAssociatoAtto(long idSoggetto, long idProcedimento, long idAtto,
			Integer idUfficioUtente, List<BeneSoggettoForm> risultati, long idBene) {

		BeneSoggettoForm risultato = new BeneSoggettoForm();
		BeneForm bene = beneService.beneById(idUfficioUtente, idBene, idProcedimento);
		List<SoggettoForm> soggettiAssociatiAlBene = recuperaSoggettiAssociatiAlBeneEAlSoggettoAtto(bene, idSoggetto,
				idProcedimento, idAtto, idUfficioUtente);
		risultato.setBene(bene);
		risultato.setSoggetti(soggettiAssociatiAlBene);
		if ((soggettiAssociatiAlBene != null) && !soggettiAssociatiAlBene.isEmpty()) {
			risultati.add(risultato);
		}
	}

	private SoggettoForm creaSoggettoForm(TRelBeneAtto relazione, Integer idUfficioUtente) {
		return creaSoggettoForm(relazione, idUfficioUtente, null);
	}

	private SoggettoForm creaSoggettoForm(TRelBeneAtto relazione, Integer idUfficioUtente, Long idProcedimentoCda) {

		long idPersona = relazione.getId().getCodiKIdSoggTit();
		SoggettoForm risultato;
		if (idProcedimentoCda == null) {
			risultato = getPersonaById(idPersona, idUfficioUtente);
		} else {
			risultato = getPersonaById(idPersona, idUfficioUtente, idProcedimentoCda);
		}
		int idTipoInteresse = relazione.getId().getCodiKIdTipoInt();

		double quotaPercentuale = 0;
		int quotaNumeratore = 0;
		int quotaDenominatore = 0;

		quotaPercentuale = relazione.getNumeQuota() == null ? 0 : relazione.getNumeQuota().doubleValue();
		quotaNumeratore = relazione.getNumeFrazioneNum() == null ? 0 : relazione.getNumeFrazioneNum().intValue();
		quotaDenominatore = relazione.getNumeFrazioneDen() == null ? 0 : relazione.getNumeFrazioneDen().intValue();

		TTpTipoInteresse interesse = procedimentoDao.getTipoInteresseById(idTipoInteresse);
		risultato.setTipoInteresse(
				new ComboForm(String.valueOf(interesse.getCodiKIdTipoInt()), interesse.getDescTipoInt()));

		risultato.setQuotaInteresse(new Double(quotaPercentuale));

		TTpTipoMisura tipoMisura = null;

		if (relazione.getTTpTipoMisura() != null && relazione.getTTpTipoMisura().getCodiKIdTipoMisura() > 0) {
			tipoMisura = procedimentoDao.getTipoMisuraById((long) relazione.getTTpTipoMisura().getCodiKIdTipoMisura());
		}

		MisuraForm misura = new MisuraForm();
		if (tipoMisura != null) {
			misura.setTipoMisura(
					new ComboForm(String.valueOf(tipoMisura.getCodiKIdTipoMisura()), tipoMisura.getDescTipoMisura()));
		} else {
			misura.setTipoMisura(new ComboForm(null, null));
		}

		TTpTipoPrevenzione prevenzione = null;
		if (relazione.getTAnNormative() != null) {
			prevenzione = relazione.getTAnNormative().getTTpTipoPrevenzione();
		}
		if (prevenzione == null) {
			risultato.setTipologiaPrevenzione(new ComboForm("", ""));
		} else {
			risultato.setTipologiaPrevenzione(
					new ComboForm(String.valueOf(prevenzione.getCodiKIdTipoPrev()), prevenzione.getDescTipoPrev()));
		}
		if (relazione.getNumeFrazioneNum() != null) {
			risultato.setNumeratoreFrazioneQuota(Integer.valueOf(quotaNumeratore));
		}
		if (relazione.getNumeFrazioneDen() != null) {
			risultato.setDenominatoreFrazioneQuota(Integer.valueOf(quotaDenominatore));
		}
		if (relazione.getDataAssunzione() != null) {
			risultato.setDataEsecuzione(relazione.getDataAssunzione());
		} else {
			risultato.setDataEsecuzione(null);
		}
		// Prendo l'eventuale data Definitività e Data Invio da T_DEF_PATR
		long idProc = relazione.getId().getCodiKIdProc();
		long idAtto = relazione.getId().getCodiKIdAtto();
		long idBene = relazione.getId().getCodiKIdBene();
		long tipoInt = relazione.getId().getCodiKIdTipoInt();
		long sogTit = relazione.getId().getCodiKIdSoggTit();
		AttoForm attoForm = attoService.dettaglioAtto(idUfficioUtente, idAtto, idProc, "");
		int categoriaAtto = Integer.parseInt(attoForm.getCategoriaAtto().getId());
		int tipologiaAtto = Integer.parseInt(attoForm.getTipoAtto().getId());
		List<TDefPatr> risultati = procedimentoDao.getTDefPatrById(idProc, idAtto, idUfficioUtente, idBene, tipoInt,
				sogTit, null);
		TDefPatr definitivita = null;
		if (risultati.size() > 0)
			definitivita = risultati.get(0);
		if (definitivita != null) {
			if (definitivita.getDataDefinitivita() != null)
				misura.setDataDefinitivita(definitivita.getDataDefinitivita());
			if (definitivita.getDataInvio() != null)
				risultato.setDisabledCheckDefinitivita(true);
		}

		if (relazione.getTTpEsitoByCodiFkIdEsito2() != null) {
			TTpEsito esito = procedimentoDao.getEsitoById(relazione.getTTpEsitoByCodiFkIdEsito2().getCodiKIdEsito());
			ComboForm decisioneMisuraPersonale = new ComboForm(String.valueOf(esito.getCodiKIdEsito()),
					esito.getDescEsito());
			misura.setDecisioneMisuraPersonaleIIGrado(decisioneMisuraPersonale);
		}

		if (relazione.getTTpEsitoByCodiFkIdEsito1() != null) {
			TTpEsito esito = procedimentoDao.getEsitoById(relazione.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito());
			ComboForm decisioneMisuraPersonale = new ComboForm(String.valueOf(esito.getCodiKIdEsito()),
					esito.getDescEsito());
			misura.setDecisioneMisuraPersonale(decisioneMisuraPersonale);
		}

		if (relazione.getTTpEsitoByCodiFkIdEsito3() != null) {
			TTpEsito esito = procedimentoDao.getEsitoById(relazione.getTTpEsitoByCodiFkIdEsito3().getCodiKIdEsito());
			ComboForm decisioneMisuraPersonale = new ComboForm(String.valueOf(esito.getCodiKIdEsito()),
					esito.getDescEsito());
			misura.setDecisioneMisuraPersonaleSentenzaCassazione(decisioneMisuraPersonale);
		}

		if (relazione.getTAnNormative() != null && relazione.getTAnNormative().getCodiKIdNormativa() > 0) {
			TAnNormative normativa = procedimentoDao
					.getNormativaById(relazione.getTAnNormative().getCodiKIdNormativa());
			ComboForm normativaMisPatr = new ComboForm(String.valueOf(normativa.getCodiKIdNormativa()),
					normativa.getDescNormativa());
			risultato.setNormativa(normativaMisPatr);
		}

		risultato.setMisura(misura);
		return risultato;
	}

	private SoggettoForm creaSoggettoForm(TRelBeneAtto relazione, long idProcedimento, Integer idUfficioUtente) {

		long idPersona = relazione.getId().getCodiKIdSoggTit();
		SoggettoForm risultato = getPersonaById(idPersona, idProcedimento, idUfficioUtente);
		int idTipoInteresse = relazione.getId().getCodiKIdTipoInt();

		double quotaPercentuale = 0;
		int quotaNumeratore = 0;
		int quotaDenominatore = 0;

		quotaPercentuale = relazione.getNumeQuota() == null ? 0 : relazione.getNumeQuota().doubleValue();
		quotaNumeratore = relazione.getNumeFrazioneNum() == null ? 0 : relazione.getNumeFrazioneNum().intValue();
		quotaDenominatore = relazione.getNumeFrazioneDen() == null ? 0 : relazione.getNumeFrazioneDen().intValue();

		TTpTipoInteresse interesse = procedimentoDao.getTipoInteresseById(idTipoInteresse);
		risultato.setTipoInteresse(
				new ComboForm(String.valueOf(interesse.getCodiKIdTipoInt()), interesse.getDescTipoInt()));

		risultato.setQuotaInteresse(new Double(quotaPercentuale));

		TTpTipoMisura tipoMisura = null;

		if (relazione.getTTpTipoMisura() != null && relazione.getTTpTipoMisura().getCodiKIdTipoMisura() > 0) {
			tipoMisura = procedimentoDao.getTipoMisuraById((long) relazione.getTTpTipoMisura().getCodiKIdTipoMisura());
		}

		MisuraForm misura = new MisuraForm();
		if (tipoMisura != null) {
			misura.setTipoMisura(
					new ComboForm(String.valueOf(tipoMisura.getCodiKIdTipoMisura()), tipoMisura.getDescTipoMisura()));
		} else {
			misura.setTipoMisura(new ComboForm(null, null));
		}

		TTpTipoPrevenzione prevenzione = null;
		if (relazione.getTAnNormative() != null) {
			prevenzione = relazione.getTAnNormative().getTTpTipoPrevenzione();
		}
		if (prevenzione == null) {
			risultato.setTipologiaPrevenzione(new ComboForm("", ""));
		} else {
			risultato.setTipologiaPrevenzione(
					new ComboForm(String.valueOf(prevenzione.getCodiKIdTipoPrev()), prevenzione.getDescTipoPrev()));
		}
		if (relazione.getNumeFrazioneNum() != null) {
			risultato.setNumeratoreFrazioneQuota(Integer.valueOf(quotaNumeratore));
		}
		if (relazione.getNumeFrazioneDen() != null) {
			risultato.setDenominatoreFrazioneQuota(Integer.valueOf(quotaDenominatore));
		}
		if (relazione.getDataAssunzione() != null) {
			risultato.setDataEsecuzione(relazione.getDataAssunzione());
		} else {
			risultato.setDataEsecuzione(null);
		}
		// Prendo l'eventuale data Definitività e Data Invio da T_DEF_PATR
		long idProc = relazione.getId().getCodiKIdProc();
		long idAtto = relazione.getId().getCodiKIdAtto();
		long idBene = relazione.getId().getCodiKIdBene();
		long tipoInt = relazione.getId().getCodiKIdTipoInt();
		long sogTit = relazione.getId().getCodiKIdSoggTit();
		AttoForm attoForm = attoService.dettaglioAtto(idUfficioUtente, idAtto, idProc, "");
		int categoriaAtto = Integer.parseInt(attoForm.getCategoriaAtto().getId());
		int tipologiaAtto = Integer.parseInt(attoForm.getTipoAtto().getId());
		List<TDefPatr> risultati = procedimentoDao.getTDefPatrById(idProc, idAtto, idUfficioUtente, idBene, tipoInt,
				sogTit, null);
		TDefPatr definitivita = null;
		if (risultati.size() > 0)
			definitivita = risultati.get(0);
		if (definitivita != null) {
			if (definitivita.getDataDefinitivita() != null)
				misura.setDataDefinitivita(definitivita.getDataDefinitivita());
			if (definitivita.getDataInvio() != null)
				risultato.setDisabledCheckDefinitivita(true);
		}

		if (relazione.getTTpEsitoByCodiFkIdEsito2() != null) {
			TTpEsito esito = procedimentoDao.getEsitoById(relazione.getTTpEsitoByCodiFkIdEsito2().getCodiKIdEsito());
			ComboForm decisioneMisuraPersonale = new ComboForm(String.valueOf(esito.getCodiKIdEsito()),
					esito.getDescEsito());
			misura.setDecisioneMisuraPersonaleIIGrado(decisioneMisuraPersonale);
		}

		if (relazione.getTTpEsitoByCodiFkIdEsito1() != null) {
			TTpEsito esito = procedimentoDao.getEsitoById(relazione.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito());
			ComboForm decisioneMisuraPersonale = new ComboForm(String.valueOf(esito.getCodiKIdEsito()),
					esito.getDescEsito());
			misura.setDecisioneMisuraPersonale(decisioneMisuraPersonale);
		}

		if (relazione.getTTpEsitoByCodiFkIdEsito3() != null) {
			TTpEsito esito = procedimentoDao.getEsitoById(relazione.getTTpEsitoByCodiFkIdEsito3().getCodiKIdEsito());
			ComboForm decisioneMisuraPersonale = new ComboForm(String.valueOf(esito.getCodiKIdEsito()),
					esito.getDescEsito());
			misura.setDecisioneMisuraPersonaleSentenzaCassazione(decisioneMisuraPersonale);
		}

		if (relazione.getTAnNormative() != null && relazione.getTAnNormative().getCodiKIdNormativa() > 0) {
			TAnNormative normativa = procedimentoDao
					.getNormativaById(relazione.getTAnNormative().getCodiKIdNormativa());
			ComboForm normativaMisPatr = new ComboForm(String.valueOf(normativa.getCodiKIdNormativa()),
					normativa.getDescNormativa());
			risultato.setNormativa(normativaMisPatr);
		}

		risultato.setMisura(misura);
		return risultato;
	}

	private void inizializzaNodi(List<AttoForm> atti, Node node, long tipologia) {

		Iterator<AttoForm> iter = atti.iterator();
		// se non esiste alcuna fonte associata al procedimento
		// cambia l'icona al nodo 'Fonte' in modo che sia una cartella
		// aperta
		if (atti.size() == 0) {

			JsNodeInfo info;
			if (node != null) { // ginesti luglio 2017
				info = (JsNodeInfo) node.getInfo();
			} else {
				info = new JsNodeInfo(); // ginesti luglio 2017
			}

			info.setLeafIcon("folderopen.gif");
		}
		while (iter.hasNext()) {
			AttoForm currentAtto = iter.next();
			JsNodeInfo nodeInfo = new JsNodeInfo();
			String titoloLabel = "";

			if ((tipologia == Long.valueOf("9")) && currentAtto.getCategoriaAtto().getId().equalsIgnoreCase("2")) {
				titoloLabel = "Proposta ";
			} else {
				titoloLabel = currentAtto.getAltroOrgano() == null ? currentAtto.getFonte().getDescrizione()
						: currentAtto.getAltroOrgano();
			}
			String dataLabel = "";
			if (currentAtto.getFkUdienzaDecreto() > 0) {
				// carica udienza e prendi la data di udienza
				long idUfficio = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
				UdienzaForm udienzaFormTmp = attoService.getUdienzaById(currentAtto.getFkUdienzaDecreto(), idUfficio);
				dataLabel = " del " + Utility.convertDateToString(udienzaFormTmp.getDataUdienza());
			} else if (currentAtto.getDataEmissione() != null) {
				dataLabel = " del " + Utility.convertDateToString(currentAtto.getDataEmissione());
			}
			String label = titoloLabel + dataLabel + "   " + "(<span>" + currentAtto.getStatoAtto().getDescrizione()
					+ "</span>)";
			if (currentAtto.getAllegato().getFileName() != null) {
				nodeInfo.setAttachment(true);
			}
			if (!currentAtto.getTipoAtto().getId().equals(Constants.TipoAtto.TIPO_ATTO + "")
					&& (currentAtto.getStatoAtto().getId().equalsIgnoreCase(Constants.STATOATTO_IN_LAVORAZIONE_S)
							|| currentAtto.getStatoAtto().getId().equalsIgnoreCase(Constants.STATOATTO_ARCHIVIATO))
					&& !currentAtto.getCategoriaAtto().getId().equals(Constants.ISTANZA_DIFFIDA_SOGGETTO)) {
				nodeInfo.setDeletable(true);
				nodeInfo.setIdAtto(currentAtto.getCodAtto());
			} else {
				nodeInfo.setDeletable(false);
				nodeInfo.setIdAtto(0);
			}
			nodeInfo.setLabel(label);

			// Tipologia Fonte
			if (tipologia == Long.valueOf("12")) {
				nodeInfo.setUrl("javascript:caricaFonte(" + "\\'" + currentAtto.getCodAtto() + "\\'" + "," + "\\\'"
						+ currentAtto.getFonte().getId() + "\\\'" + "," + "\\\'" + currentAtto.getOrgano().getId()
						+ "\\\'" + "," + "\\\'" + currentAtto.getProvinciaOrgano().getId() + "\\\'" + "," + "\\\'"
						+ currentAtto.getComuneOrgano().getId() + "\\\'" + ")");

				// Tipologia decreto e decreto II grado
			} else if (tipologia == Long.valueOf("3")
					|| tipologia == Long.valueOf(Constants.TIPO_ATTO_DECRETO_II_GRADO)) {
				if (Long.valueOf(currentAtto.getCategoriaAtto().getId()).equals(Long.valueOf("8"))
						|| Long.valueOf(currentAtto.getCategoriaAtto().getId()).equals(Long.valueOf("25"))) {
					// è un DECRETO DI FISSAZIONE UDIENZA
					nodeInfo.setUrl("javascript:caricaDecretoFissazioneUdienza(" + "\\\'" + currentAtto.getCodAtto()
							+ "\\\'" + "," + "\\\'" + currentAtto.getTipoAtto().getId() + "\\\'" + ")");
				} else {
					nodeInfo.setUrl("javascript:caricaAtto(" + "\\\'" + currentAtto.getCodAtto() + "\\\'" + "," + "\\\'"
							+ currentAtto.getTipoAtto().getId() + "\\\'" + ")");
				}

				// Tipologia Proposta
			} else if (tipologia == Long.valueOf("9")) {
				nodeInfo.setUrl("/procedimento/atto/proposta.htm");

				// Tipologia Istanza
			} else if (tipologia == Long.valueOf(Constants.TIPO_ATTO_ISTANZA)) {
				nodeInfo.setUrl("javascript:caricaAtto(" + "\\\'" + currentAtto.getCodAtto() + "\\\'" + "," + "\\\'"
						+ currentAtto.getTipoAtto().getId() + "\\\'" + ")");

				// Tipologia Impugnazione
			} else if (tipologia == Long.valueOf("4")) {
				nodeInfo.setUrl("javascript:caricaAtto(" + "\\\'" + currentAtto.getCodAtto() + "\\\'" + "," + "\\\'"
						+ currentAtto.getTipoAtto().getId() + "\\\'" + ")");
				// Giudiziario generico
			} else if (tipologia == Long.valueOf("13")) {
				nodeInfo.setUrl("javascript:caricaAtto(" + "\\\'" + currentAtto.getCodAtto() + "\\\'" + "," + "\\\'"
						+ currentAtto.getTipoAtto().getId() + "\\\'" + ")");
				// Tipologia Verbale
			} else if (tipologia == Long.valueOf("14")) {
				nodeInfo.setUrl("javascript:caricaAtto(" + "\\\'" + currentAtto.getCodAtto() + "\\\'" + "," + "\\\'"
						+ currentAtto.getTipoAtto().getId() + "\\\'" + ")");
			} else if (tipologia == Long.valueOf("16")) {
				nodeInfo.setUrl("javascript:caricaAtto(" + "\\\'" + currentAtto.getCodAtto() + "\\\'" + "," + "\\\'"
						+ currentAtto.getTipoAtto().getId() + "\\\'" + ")");
			} else if (tipologia == Long.valueOf(Constants.REDAZIONE_SCHEDA)) {
				ComunicazioneForm comunicazioneForm = attoService.getComunicazioneByIdAtto(
						currentAtto.getIdProcedimento(), currentAtto.getCodAtto(), currentAtto.getIdUfficio());
				String nuovaLabel = "Redazione Scheda " + comunicazioneForm.getDescSoggetto() + " / "
						+ comunicazioneForm.getDescInvioComunicazione();
				nodeInfo.setLabel(nodeInfo.getLabel().replaceAll("Redazione scheda", nuovaLabel));
				nodeInfo.setUrl("javascript:caricaAttoRedazioneScheda(" + "\\\'" + currentAtto.getCodAtto() + "\\\'"
						+ "," + "\\\'" + currentAtto.getTipoAtto().getId() + "\\\'" + ")");
				if (currentAtto.getStatoAtto().getId().equalsIgnoreCase(Constants.STATOATTO_REGISTRATO + "")) {
					nodeInfo.setAttachment(true);
				} else {
					nodeInfo.setAttachment(false);
				}

			}
			nodeInfo.setIdAtto(currentAtto.getCodAtto());
			nodeInfo.setLeafIcon("icon_file.gif");
			Node lNode = new Node(nodeInfo);
			node.addChild(lNode);
		}
	}

	private void inizializzaNodi(List<AttoForm> atti, Node node, long tipologia, long categoriaAtto) {

		Iterator<AttoForm> iter = atti.iterator();
		// se non esiste alcuna fonte associata al procedimento
		// cambia l'icona al nodo 'Fonte' in modo che sia una cartella
		// aperta
		if (atti.size() == 0) {
			JsNodeInfo info;
			if (node != null) { // ginesti luglio 2017
				info = (JsNodeInfo) node.getInfo();
			} else {
				info = new JsNodeInfo(); // ginesti luglio 2017
			}

			// JsNodeInfo info = (JsNodeInfo) node.getInfo();
			info.setLeafIcon("folderopen.gif");
		}
		while (iter.hasNext()) {
			AttoForm currentAtto = iter.next();
			JsNodeInfo nodeInfo = new JsNodeInfo();
			String titoloLabel = "";
			if ((tipologia == 9) && (Long.parseLong(currentAtto.getCategoriaAtto().getId()) == 2)) {
				titoloLabel = "Proposta ";
			} else {
				// se è un istanza
				if (tipologia == 5) {
					if (Long.parseLong(currentAtto.getCategoriaAtto().getId()) == 341l)
						titoloLabel = "Istanza di modifica e revoca -personale ";
					if (Long.parseLong(currentAtto.getCategoriaAtto().getId()) == 340l)
						titoloLabel = "Istanza di modifica e revoca -patrimoniale ";

					if (Long.parseLong(currentAtto.getCategoriaAtto().getId()) == 342l)
						titoloLabel = "Istanza di permesso ";
					if (Long.parseLong(currentAtto.getCategoriaAtto().getId()) == 343l)
						titoloLabel = "Istanza di cauzione ";

					if (Long.parseLong(currentAtto.getCategoriaAtto().getId()) == 354l)
						titoloLabel = "Istanza liquidazione compensi amministratore ";
					if (Long.parseLong(currentAtto.getCategoriaAtto().getId()) == 360l)
						titoloLabel = "Istanza terzo interveniente (art. 666 cpp) ";

					if (Long.parseLong(currentAtto.getCategoriaAtto().getId()) == 361l)
						titoloLabel = "Istanza di correzione degli errori materiali ";
				}
				// Segnalazione EMS-37
				else if (tipologia == 15)
					titoloLabel = "Atto di archiviazione ";

				else
					titoloLabel = currentAtto.getAltroOrgano() == null ? currentAtto.getFonte().getDescrizione()
							: currentAtto.getAltroOrgano();
			}

			if (currentAtto.getCategoriaAtto().getId().equals(Constants.CategoriaAtto.CERTIFICATO_PENALE)) {
				CertificatoPenaleForm certificatoPenaleForm = attoService.getCertificatoPenaleByIdAtto(
						currentAtto.getIdProcedimento(), currentAtto.getCodAtto(), currentAtto.getIdUfficio());
				titoloLabel += " (" + certificatoPenaleForm.getDescSoggetto() + ")";
				nodeInfo.setAttachment(true);
			}

			String dataLabel = "";
			if (currentAtto.getFkUdienzaDecreto() > 0) {
				// carica udienza e prendi la data di udienza
				long idUfficio = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
				UdienzaForm udienzaFormTmp = attoService.getUdienzaById(currentAtto.getFkUdienzaDecreto(), idUfficio);
				dataLabel = " del " + Utility.convertDateToString(udienzaFormTmp.getDataUdienza());
			} else if (currentAtto.getDataEmissione() != null) {
				dataLabel = " del " + Utility.convertDateToString(currentAtto.getDataEmissione());
			}
			String label = titoloLabel + dataLabel + "   " + "(<span>" + currentAtto.getStatoAtto().getDescrizione()
					+ "</span>)";
			if (currentAtto.getAllegato().getFileName() != null) {
				nodeInfo.setAttachment(true);
			}
			if (!currentAtto.getTipoAtto().getId().equals(Constants.TipoAtto.TIPO_ATTO + "")
					&& (currentAtto.getStatoAtto().getId().equalsIgnoreCase(Constants.STATOATTO_IN_LAVORAZIONE_S)
							|| currentAtto.getStatoAtto().getId().equalsIgnoreCase(Constants.STATOATTO_ARCHIVIATO))) {
				nodeInfo.setDeletable(true);
				nodeInfo.setIdAtto(currentAtto.getCodAtto());
			} else {
				nodeInfo.setDeletable(false);
				nodeInfo.setIdAtto(0);
			}

			nodeInfo.setLabel(label);
			if (tipologia == 12l) {// FONTE
				// nodeInfo.setUrl("javascript:caricaFonte(" + "\\'" +
				// currentAtto.getCodAtto() + "\\'" + "," + "\\\'" +
				// currentAtto.getFonte().getId() + "\\\'" + "," + "\\\'" +
				// currentAtto.getOrgano().getId() + "\\\'" + "," + "\\\'" +
				// currentAtto.getProvinciaOrgano().getId() + "\\\'" + "," + "\\\'"
				// + currentAtto.getComuneOrgano().getId() + "\\\'" + ")");
				nodeInfo.setUrl("javascript:caricaAtto(" + "\\\'" + currentAtto.getCodAtto() + "\\\'" + "," + "\\\'"
						+ currentAtto.getTipoAtto().getId() + "\\\'" + ")");
			} else if (tipologia == 3l) {// Decreti
				if (Long.parseLong(currentAtto.getCategoriaAtto().getId()) == Long
						.parseLong(Constants.DECRETO_FISSAZIONE_UDIENZA)) {
					// è un DECRETO DI FISSAZIONE UDIENZA
					nodeInfo.setUrl("javascript:caricaDecretoFissazioneUdienza(" + "\\\'" + currentAtto.getCodAtto()
							+ "\\\'" + "," + "\\\'" + currentAtto.getTipoAtto().getId() + "\\\'" + ")");
				} else {
					nodeInfo.setUrl("javascript:caricaAtto(" + "\\\'" + currentAtto.getCodAtto() + "\\\'" + "," + "\\\'"
							+ currentAtto.getTipoAtto().getId() + "\\\'" + ")");
				}
			} else if (tipologia == 9l) {// Proposta

				nodeInfo.setUrl("/procedimento/atto/proposta.htm");

			} else if (tipologia == 5l) {// Istanza
				nodeInfo.setUrl("/procedimento/atto/istanzaModificaPersonali.htm");
			} else {
				nodeInfo.setUrl("javascript:caricaAtto(" + "\\\'" + currentAtto.getCodAtto() + "\\\'" + "," + "\\\'"
						+ currentAtto.getTipoAtto().getId() + "\\\'" + ")");
			}
			nodeInfo.setLeafIcon("icon_file.gif");
			nodeInfo.setIdAtto(currentAtto.getCodAtto());
			Node lNode = new Node(nodeInfo);
			node.addChild(lNode);

		}
	}

	private void inserisciMisurePersonaliPerAggravamento(Long idAggravamento, Long idPadre, Long idSoggettoInteressato,
			Integer idUffOwn) {

		TRelAttoProc attoProc = attoDao.getTAttoProcByProcedimento(idAggravamento, idUffOwn);
		Long idAttoAggravamento = attoProc.getId().getCodiKIdAtto();
		Long idAttoPadreAggiornato = attoService.getIdAttoAggiornatoPerPadreAggravamento(idPadre, idUffOwn);
		misuraService.duplicaMisuraPersonalePerAggravamento(idPadre, idAggravamento, idAttoPadreAggiornato,
				idAttoAggravamento, idSoggettoInteressato, idUffOwn);

		// Recupero le misurePersonali legate al soggetto Interessato
		// List<TMisuraPers> misurePersonaliPadre =
		// misuraService.listaMisurePersByIdSoggetto(idSoggettoInteressato, idUffOwn);
		// for (TMisuraPers misuraPadre : misurePersonaliPadre) {
		// 1)
		// Verifico se la MisuraPersonale è presente nell'Atto Decisorio del
		// Padre ed è stata "Accolta"
		// TRelAttoMisPers relazione =
		// misuraService.relAttoMisPersByProcMisPersEAtto(misuraPadre.getId().getCodiKIdMisura(),
		// idPadre,
		// idAttoPadreAggiornato, idUffOwn);
		// if (relazione != null) {
		// 2)
		// Se presente la misura la duplico in T_MISURA_PERS
		// Long idMisura =
		// misuraService.duplicaMisuraPersonalePerAggravamento(relazione.getTMisuraPers().getId().getCodiKIdMisura(),
		// idUffOwn);
		// 3)
		// Salvo la relazione su T_REL_ATTO_MIS_PERS
		// misuraService.saveRelazioneMisuraPersonalePerAggravamento(relazione,
		// idAggravamento, idAttoAggravamento, idMisura, idUffOwn);
		// }
		// }
	}

	private void ordinaRisultatiPerRuolo(List<MagistratoForm> risultati) {

		Comparator<MagistratoForm> comparator = new Comparator<MagistratoForm>() {

			public int compare(MagistratoForm o1, MagistratoForm o2) {

				if (Utility.isDefinito(o1.getRuolo()) && Utility.isDefinito(o2.getRuolo())) {
					if ((o1.getDataFineAssegnazione() == null) && (o2.getDataFineAssegnazione() != null)) {
						return -1;
					}
					if ((o1.getDataFineAssegnazione() != null) && (o2.getDataFineAssegnazione() == null)) {
						return 1;
					}
					if (Constants.ID_TIPO_RUOLO_PRES.equalsIgnoreCase(o1.getRuolo().getId())
							&& !Constants.ID_TIPO_RUOLO_PRES.equalsIgnoreCase(o2.getRuolo().getId())) {
						// if (o1.getRuolo().equalsIgnoreCase("PRESIDENTE") &&
						// !o2.getRuolo().equalsIgnoreCase("PRESIDENTE")) {
						return -1;
					}
					if (!Constants.ID_TIPO_RUOLO_PRES.equalsIgnoreCase(o1.getRuolo().getId())
							&& Constants.ID_TIPO_RUOLO_PRES.equalsIgnoreCase(o2.getRuolo().getId())) {
						// if (!o1.getRuolo().equalsIgnoreCase("PRESIDENTE") &&
						// o2.getRuolo().equalsIgnoreCase("PRESIDENTE")) {
						return 1;
					}
					if (o2.getRuolo().getId().equalsIgnoreCase(o1.getRuolo().getId())) {
						// if (o1.getRuolo().equalsIgnoreCase(o2.getRuolo())) {
						return 0;
					}
				}
				if (Utility.isDefinito(o1.getRuolo().getId())) {
					return -1;
				}
				if (Utility.isDefinito(o2.getRuolo().getId())) {
					return 1;
				}
				return 0;
			}
		};
		Collections.sort(risultati, comparator);
	}

	// intervento EMS 15
	private void ordinaRisultatiPerDataFineAssegnazioneNotNull(List<MagistratoForm> risultati) {

		if (risultati != null && risultati.size() != 1) {
			List<MagistratoForm> tempAttivi = new ArrayList<MagistratoForm>();
			List<MagistratoForm> tempNOAttivi = new ArrayList<MagistratoForm>();
			for (int i = 0; i < risultati.size(); i++) {
				if (risultati.get(i).getDataFineAssegnazione() != null)
					tempNOAttivi.add(risultati.get(i));
				else
					tempAttivi.add(risultati.get(i));
			}
			if (tempNOAttivi.size() != 0) {
				risultati.clear();
				risultati.addAll(tempAttivi);
				risultati.addAll(tempNOAttivi);
			}
		}
	}

	private List<SoggettoForm> recuperaSoggettiAssociatiAlBene(BeneForm bene, long idProcedimento, long idAtto,
			Integer idUfficioUtente, boolean isCda) {

		List<SoggettoForm> risultati = new ArrayList<SoggettoForm>();
		// AttoForm attoForm = attoService.getAttoById(idAtto, idUfficioUtente);
		List<TRelBeneAtto> relazioniNonAssociati = null;
		// if(attoForm.getCategoriaAtto() != null &&
		// (attoForm.getCategoriaAtto().getId().equals(Constants.DECRETO_CONVALIDA_SEQUESTRO_ANTICIPATO)
		// ||
		// attoForm.getCategoriaAtto().getId().equals(Constants.DECRETO_CONVALIDA_SEQUESTRO_URGENTE)))
		// {
		// relazioniNonAssociati =
		// procedimentoDao.listaRelBeneAttoByBeneNoFrazionatoConvalida(bene.getIdBene(),
		// idProcedimento, idAtto, idUfficioUtente);
		//
		// } else {f
		relazioniNonAssociati = procedimentoDao.listaRelBeneAttoByBeneNoFrazionato(bene.getIdBene(), idProcedimento,
				idAtto, idUfficioUtente);
		// }
		SoggettoForm risultato;
		for (TRelBeneAtto tmp : relazioniNonAssociati) {
			if (isCda) {
				risultato = creaSoggettoForm(tmp, idUfficioUtente, idProcedimento);
			} else {
				risultato = creaSoggettoForm(tmp, idUfficioUtente);
			}

			if (risultato != null)
				risultati.add(risultato);
		}
		return risultati;
	}

	private List<SoggettoForm> recuperaSoggettiAssociatiAlBeneFiltrati(BeneForm bene, long idProcedimento, long idAtto,
			Integer idUfficioUtente, long idSoggettoDiretto, long idSoggettoTitolare) {

		List<SoggettoForm> risultati = new ArrayList<SoggettoForm>();
		List<TRelBeneAtto> relazioniNonAssociati = null;
		relazioniNonAssociati = procedimentoDao.listaRelBeneAttoByBeneNoFrazionato(bene.getIdBene(), idProcedimento,
				idAtto, idUfficioUtente);

		for (TRelBeneAtto tmp : relazioniNonAssociati) {
			List<TRelAttoMisPatr> listaMP = procedimentoDao.listaRelMisPatrForFiltro(tmp.getId().getCodiKIdBene(),
					idSoggettoDiretto, idSoggettoTitolare, idProcedimento, idAtto, idUfficioUtente);
			if (listaMP.size() > 0) {
				SoggettoForm risultato = creaSoggettoForm(tmp, idUfficioUtente);
				if (risultato != null)
					risultati.add(risultato);
			}
		}
		return risultati;
	}

	private List<SoggettoForm> recuperaSoggettiAssociatiAlBeneEAlSoggetto(BeneForm bene, long idSoggetto,
			long idProcedimento, long idAtto, Integer idUfficioUtente) {

		List<SoggettoForm> risultati = new ArrayList<SoggettoForm>();
		List<TRelAttoMisPatr> relazioniAssociati = procedimentoDao.listaRelMisPatrForBene(bene.getIdBene(), idSoggetto,
				idProcedimento, idAtto, idUfficioUtente);
		Set<Long> soggettiTitolariSet = new HashSet<Long>();
		Set<Integer> titoliInteresseSet = new HashSet<Integer>();
		for (TRelAttoMisPatr tmp : relazioniAssociati) {
			soggettiTitolariSet.add(tmp.getId().getCodiKIdSoggTit());
			titoliInteresseSet.add(tmp.getId().getCodiKIdTipoInt());
		}
		List<TRelBeneAtto> relazioniNonAssociati = procedimentoDao.listaRelBeneAttoByBene(bene.getIdBene(),
				idProcedimento, idAtto, idUfficioUtente);
		for (TRelBeneAtto tmp : relazioniNonAssociati) {
			if (soggettiTitolariSet.contains(tmp.getId().getCodiKIdSoggTit())
					&& titoliInteresseSet.contains(tmp.getId().getCodiKIdTipoInt())) {
				SoggettoForm risultato = creaSoggettoForm(tmp, idProcedimento, idUfficioUtente);
				risultati.add(risultato);
			}
		}
		return risultati;
	}

	private List<SoggettoForm> recuperaSoggettiAssociatiAlBeneEAlSoggettoAtto(BeneForm bene, long idSoggetto,
			long idProcedimento, long idAtto, Integer idUfficioUtente) {

		List<SoggettoForm> risultati = new ArrayList<SoggettoForm>();
		List<TRelAttoMisPatr> relazioniAssociati = procedimentoDao.listaRelMisPatrForBene(bene.getIdBene(), idSoggetto,
				idProcedimento, idAtto, idUfficioUtente);
		Set<Long> soggettiTitolariSet = new HashSet<Long>();
		for (TRelAttoMisPatr tmp : relazioniAssociati) {
			soggettiTitolariSet.add(tmp.getId().getCodiKIdSoggTit());
		}
		List<TRelBeneAtto> relazioniNonAssociati = procedimentoDao.listaRelBeneAttoByBeneNoFrazionato(bene.getIdBene(),
				idProcedimento, idAtto, idUfficioUtente);
		for (TRelBeneAtto tmp : relazioniNonAssociati) {
			if (soggettiTitolariSet.contains(tmp.getId().getCodiKIdSoggTit())) {
				SoggettoForm risultato = creaSoggettoForm(tmp, idUfficioUtente);
				risultati.add(risultato);
			}
		}
		return risultati;
	}

	private List<SoggettoForm> recuperaSoggettiDecisorioBeni(BeneForm bene, long idSoggetto, long idProcedimento,
			long idAtto, Integer idUfficioUtente) {

		List<SoggettoForm> risultati = new ArrayList<SoggettoForm>();
		List<TRelBeneAtto> relazioniNonAssociati = procedimentoDao.listaRelBeneAttoByBene(bene.getIdBene(),
				idProcedimento, idAtto, idUfficioUtente);
		for (TRelBeneAtto tmp : relazioniNonAssociati) {
			SoggettoForm risultato = creaSoggettoForm(tmp, idUfficioUtente);
			risultati.add(risultato);
		}
		return risultati;
	}

	private void riempiAlbero(ProcedimentoTree tree, long idProcedimento, long tipoUfficio) {

		if (tipoUfficio == ProcedimentoTree.PROCURA_REP) {
			// valorizza i nodi dell'albero nel caso di
			// tipoUfficio = 'Procura della Repubblica'
			List<AttoForm> atti = attoService.getAttiByProcedimento(idProcedimento, 12l, null);
			Node fonteNode = tree.getIndex().get(ProcedimentoTree.FONTE);
			Node propostaNode = tree.getIndex().get(ProcedimentoTree.PROPOSTA);
			// caricamento della proposta
			long idUfficio = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);

			// Solo per l'ufficio Procura, se lo stato del procedimento
			// è ancora in lavorazione non devo vedere la
			// proposta
			ProcedimentoForm procedimento = getDettaglioProcedimento(idProcedimento);

			// AttoForm proposta = dettaglioPropostaAtto(idUfficio, idProcedimento, -1,
			// true);
			// List<AttoForm> propostaTmp = new ArrayList<AttoForm>();
			// propostaTmp.add(proposta);

			List<AttoForm> propostaTmp = attoService.getAttiByProcedimento(idProcedimento, Long.valueOf("9"), "2");

			inizializzaNodi(atti, fonteNode, 12l, -1l);
			// if (proposta.getCodAtto() > 0) {
			if (propostaTmp != null && !propostaTmp.isEmpty()) {
				if (Long.valueOf(procedimento.getStatoProcedimento().getId()) != Constants.STATOPROC_IN_LAVORAZIONE) {
					inizializzaNodi(propostaTmp, propostaNode, 9, 2);
				}
			}

			// Stralcio
			Node iscrizioneProcNode = tree.getIndex().get(ProcedimentoTree.ISCRIZIONE_PROCEDIMENTO);
			List<AttoForm> attiStralcio = attoService.getAttiByProcedimento(idProcedimento, Long.valueOf("13"), "14");
			inizializzaNodi(attiStralcio, iscrizioneProcNode, 13);
			//

			Node udienzaNode = tree.getIndex().get(ProcedimentoTree.UDIENZA);
			List<AttoForm> decretiFissazioneUdienza = attoService.getAttiByProcedimento(idProcedimento, 3l,
					Constants.DECRETO_FISSAZIONE_UDIENZA + "," + Constants.DECRETO_ANTICIPAZIONE_UDIENZA + ","
							+ Constants.DECRETO_RINVIO_UDIENZA);
			inizializzaNodi(decretiFissazioneUdienza, udienzaNode, 3l,
					Long.parseLong(Constants.DECRETO_FISSAZIONE_UDIENZA));

			Node verbaliNode = tree.getIndex().get(ProcedimentoTree.VERBALI_UDIENZE);
			List<AttoForm> attiVerbali = attoService.getAttiByProcedimento(idProcedimento, Long.valueOf("16"),
					"313, 21");
			inizializzaNodi(attiVerbali, verbaliNode, Long.valueOf("16"));

			Node decretiInterlocutoriNode = tree.getIndex().get(ProcedimentoTree.DECRETI_INTERLOCUTORI);
			String categoriaAtto = getCategorieAtto(ProcedimentoTree.DECRETI_INTERLOCUTORI);
			List<AttoForm> decretiInterlocutori = attoService.getAttiByProcedimento(idProcedimento, 3, categoriaAtto);
			inizializzaNodi(decretiInterlocutori, decretiInterlocutoriNode, 3l, -1l);

			Node postDibattimentoIGradoNode = tree.getIndex().get(ProcedimentoTree.POST_DIBATTIMENTO_PRIMO_GRADO);
			List<AttoForm> decretiDecisoriIGrado = attoService.getAttiByProcedimento(idProcedimento, 3,
					Constants.DECRETO_DECISORIO_I_GRADO);
			inizializzaNodi(decretiDecisoriIGrado, postDibattimentoIGradoNode, 3, -1l);

			Node impugnazioniIGradoNode = tree.getIndex().get(ProcedimentoTree.IMPUGNAZIONI_PRIMO_GRADO);
			List<AttoForm> impugnazioniIGrado = attoService.getAttiByProcedimento(idProcedimento, 4, "5,337,338");
			inizializzaNodi(impugnazioniIGrado, impugnazioniIGradoNode, 4, -1l);

			List<AttoForm> attiOpposizioneRestituzione = attoService.getAttiByProcedimento(idProcedimento,
					Long.valueOf("4"), "6");
			inizializzaNodi(attiOpposizioneRestituzione, impugnazioniIGradoNode, 4l, -1l);

			List<AttoForm> revocaOpposizioneDissequestroIGrado = attoService.getAttiByProcedimento(idProcedimento, 3l,
					Constants.REVOCA_DECRETO_OPPOSIZIONE_DISSEQUESTRO);
			inizializzaNodi(revocaOpposizioneDissequestroIGrado, impugnazioniIGradoNode, 3l, -1l);

			Node dibattimentoIIGradoNode = tree.getIndex().get(ProcedimentoTree.DIBATTIMENTO_SECONDO_GRADO);
			List<AttoForm> decretiDecisoriIIGrado = attoService.getAttiByProcedimento(idProcedimento, 18,
					Constants.DECRETO_DECISORIO_II_GRADO);
			inizializzaNodi(decretiDecisoriIIGrado, dibattimentoIIGradoNode, 18, -1l);

			List<AttoForm> attiOpposizioneRestituzioneSentenza = attoService.getAttiByProcedimento(idProcedimento,
					Long.valueOf("18"), "23");
			inizializzaNodi(attiOpposizioneRestituzioneSentenza, dibattimentoIIGradoNode, 18l, -1l);

			// popola il nodo attiEsecuzioneGiudDef con TIPO_ATTO_ARCHIVIAZIONE
			Node esecuzioneGiudDefNode = tree.getIndex().get(ProcedimentoTree.PROPOSTA);
			List<AttoForm> attiEsecuzioneAttiInArchivio = attoService.getAttiByProcedimento(idProcedimento,
					Long.valueOf(Constants.TIPO_ATTO_ARCHIVIAZIONE), "17,18,19,20");
			inizializzaNodi(attiEsecuzioneAttiInArchivio, esecuzioneGiudDefNode,
					Long.valueOf(Constants.TIPO_ATTO_ARCHIVIAZIONE), -1);

			Node sentenzaCassazione = tree.getIndex().get(ProcedimentoTree.GIUDIZIO_LEGITTIMITA);
			List<AttoForm> listaSentenzaCassazione = attoService.getAttiByProcedimento(idProcedimento, 21,
					Constants.SENTENZA_SU_RICORSO_II_GRADO);
			inizializzaNodi(listaSentenzaCassazione, sentenzaCassazione, 21, 40l);

			// popola il nodo ATTI_GENERICI
			Node attiGenericiNode = tree.getIndex().get(ProcedimentoTree.ATTI_GENERICI);
			long tipoAtto = 14l;
			categoriaAtto = getCategorieAtto(ProcedimentoTree.ATTI_GENERICI);
			List<AttoForm> attiGenerici = attoService.getAttiByProcedimento(idProcedimento, tipoAtto, categoriaAtto);
			inizializzaNodi(attiGenerici, attiGenericiNode, tipoAtto, -1l);

			// popola il nodo ATTI_GENERICI Versionamento
			categoriaAtto = getCategorieAtto(ProcedimentoTree.ATTI_GENERICI);
			long tipoAttoVer = 24l;
			List<AttoForm> attiGenericiVer = attoService.getAttiByProcedimento(idProcedimento, tipoAttoVer,
					categoriaAtto);
			inizializzaNodi(attiGenericiVer, attiGenericiNode, tipoAttoVer, -1l);

			Node accertamentoNode = tree.getIndex().get(ProcedimentoTree.ACCERTAMENTO);
			List<AttoForm> attiAccertamento = attoService.getAttiByProcedimento(idProcedimento, Long.valueOf("26"),
					"312");
			inizializzaNodi(attiAccertamento, accertamentoNode, tipoAtto, -1l);

		} else if (tipoUfficio == ProcedimentoTree.TRIBUNALE) {
			Node propostaNode = tree.getIndex().get(ProcedimentoTree.PROPOSTA);
			// caricamento della proposta
			long idUfficio = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);

			List<AttoForm> propostaTmp = attoService.getAttiByProcedimento(idProcedimento, Long.valueOf("9"), "2");
			// Solo per l'ufficio TRIBUNALE, se lo stato del procedimento
			// è ancora in lavorazione non devo vedere la
			// proposta

			// if (proposta.getCodAtto() > 0) {

			if (propostaTmp != null && !propostaTmp.isEmpty()) {

				inizializzaNodi(propostaTmp, propostaNode, 9, 2);

			}

			Node udienzaNode = tree.getIndex().get(ProcedimentoTree.UDIENZA);
			// popola il nodo 'Udienza'
			JsNodeInfo info = (JsNodeInfo) udienzaNode.getInfo();
			info.setLeafIcon("folderopen.gif");
			if (tipoUfficio == ProcedimentoTree.CORTE_D_APPELLO) {
				List<AttoForm> decretiFissazioneUdienza = attoService.getAttiByProcedimento(idProcedimento, 18,
						Constants.DECRETO_FISSAZIONE_UDIENZA_II_GRADO + ","
								+ Constants.DECRETO_ANTICIPAZIONE_UDIENZA_II_GRADO + ","
								+ Constants.DECRETO_RINVIO_UDIENZA_II_GRADO);
				inizializzaNodi(decretiFissazioneUdienza, udienzaNode, 18,
						Long.parseLong(Constants.DECRETO_FISSAZIONE_UDIENZA));
			} else {
				List<AttoForm> decretiFissazioneUdienza = attoService.getAttiByProcedimento(idProcedimento, 3l,
						Constants.DECRETO_FISSAZIONE_UDIENZA + "," + Constants.DECRETO_ANTICIPAZIONE_UDIENZA + ","
								+ Constants.DECRETO_RINVIO_UDIENZA);
				inizializzaNodi(decretiFissazioneUdienza, udienzaNode, 3l,
						Long.parseLong(Constants.DECRETO_FISSAZIONE_UDIENZA));
			}

			Node verbaliNode = tree.getIndex().get(ProcedimentoTree.VERBALI_UDIENZE);
			List<AttoForm> attiVerbali = attoService.getAttiByProcedimento(idProcedimento, Long.valueOf("16"),
					"313, 21");
			inizializzaNodi(attiVerbali, verbaliNode, Long.valueOf("16"));

			Node decretiInterlocutoriNode = tree.getIndex().get(ProcedimentoTree.DECRETI_INTERLOCUTORI);
			long tipoAtto = 3l;
			String categoriaAtto = getCategorieAtto(ProcedimentoTree.DECRETI_INTERLOCUTORI);
			List<AttoForm> atti = attoService.getAttiByProcedimento(idProcedimento, tipoAtto, categoriaAtto);
			inizializzaNodi(atti, decretiInterlocutoriNode, 3l, -1l);

			// Stralcio
			List<AttoForm> attiStralcio = attoService.getAttiByProcedimento(idProcedimento, Long.valueOf("13"), "14");
			inizializzaNodi(attiStralcio, decretiInterlocutoriNode, 13);
			//

			List<AttoForm> revocaOpposizioneDissequestroIGrado = attoService.getAttiByProcedimento(idProcedimento, 18l,
					Constants.REVOCA_DECRETO_OPPOSIZIONE_DISSEQUESTRO);
			inizializzaNodi(revocaOpposizioneDissequestroIGrado, decretiInterlocutoriNode, 18l, -1l);

			Node postDibattimentoIGradoNode = tree.getIndex().get(ProcedimentoTree.POST_DIBATTIMENTO_PRIMO_GRADO);
			categoriaAtto = Constants.DECRETO_INCOMPETENZA_RESTITUZIONE_ATTI_PROCURA + ","
					+ Constants.DECRETO_INCOMPETENZA_TRASF_ALTRO_TRIBUNALE;
			List<AttoForm> decretiDecisori = attoService.getAttiByProcedimento(idProcedimento, 3l, categoriaAtto);
			inizializzaNodi(decretiDecisori, postDibattimentoIGradoNode, 3l, -1l);

			List<AttoForm> decretoDecisorioIGrado = attoService.getAttiByProcedimento(idProcedimento, 3l,
					Constants.DECRETO_DECISORIO_I_GRADO);
			inizializzaNodi(decretoDecisorioIGrado, postDibattimentoIGradoNode, 3l, -1l);

			Node impugnazioniIGradoNode = tree.getIndex().get(ProcedimentoTree.IMPUGNAZIONI_PRIMO_GRADO);
			List<AttoForm> impugnazioniIGrado = attoService.getAttiByProcedimento(idProcedimento, 4, "5,337,338");
			inizializzaNodi(impugnazioniIGrado, impugnazioniIGradoNode, 4, -1l);

			List<AttoForm> attiOpposizioneRestituzione = attoService.getAttiByProcedimento(idProcedimento,
					Long.valueOf("4"), "6");
			inizializzaNodi(attiOpposizioneRestituzione, impugnazioniIGradoNode, 4l, -1l);

			Node dibattimentoIIGradoNode = tree.getIndex().get(ProcedimentoTree.DIBATTIMENTO_SECONDO_GRADO);

			List<AttoForm> decretiDecisoriIIGrado = attoService.getAttiByProcedimento(idProcedimento, 18,
					Constants.DECRETO_DECISORIO_II_GRADO);
			inizializzaNodi(decretiDecisoriIIGrado, dibattimentoIIGradoNode, 18, -1l);

			Node decretiInterlocutoriIIGradoNode = tree.getIndex()
					.get(ProcedimentoTree.DECRETI_INTERLOCUTORI_SECONDO_GRADO);
			List<AttoForm> attiDecretiInterlocutoriIIGrado = attoService.getAttiByProcedimento(idProcedimento,
					Long.valueOf("18"), "23,323,324,339");
			inizializzaNodi(attiDecretiInterlocutoriIIGrado, decretiInterlocutoriIIGradoNode, 18l, -1l);

			Node sentenzaCassazione = tree.getIndex().get(ProcedimentoTree.GIUDIZIO_LEGITTIMITA);
			List<AttoForm> listaSentenzaCassazione = attoService.getAttiByProcedimento(idProcedimento, 21,
					Constants.SENTENZA_SU_RICORSO_II_GRADO);
			inizializzaNodi(listaSentenzaCassazione, sentenzaCassazione, 21, 40l);

			// Inserire giudizio definitivo per redazione scheda redazione scheda
			Node esecuzioneGiudDefMisPersNode = tree.getIndex()
					.get(ProcedimentoTree.ESECUZIONE_GIUDIZIO_DEFINITIVO_MISURE_PERS);
			// info = (JsNodeInfo) esecuzioneGiudDefMisPersNode.getInfo();
			// info.setLeafIcon("folderopen.gif");
			List<AttoForm> attiEsecuzioneGiudDefMisPers = attoService.getAttiByProcedimento(idProcedimento,
					Long.valueOf(Constants.TIPO_ATTO_ESECUTIVO), Constants.REDAZIONE_SCHEDA);
			inizializzaNodi(attiEsecuzioneGiudDefMisPers, esecuzioneGiudDefMisPersNode,
					Long.valueOf(Constants.REDAZIONE_SCHEDA));

			// ESECUZIONE MISURE PATRIMONIALI
			Node esecuzioneGiudDefMisPatrNode = tree.getIndex()
					.get(ProcedimentoTree.ESECUZIONE_GIUDIZIO_DEFINITIVO_MISURE_PATR);
			List<AttoForm> attiEsecuzioneGiudDefMisPatr = attoService.getAttiByProcedimento(idProcedimento,
					Long.valueOf(Constants.TIPO_ATTO_DECRETO), Constants.DECRETO_ORDINANZA_REST_BENI);
			inizializzaNodi(attiEsecuzioneGiudDefMisPatr, esecuzioneGiudDefMisPatrNode,
					Long.valueOf(Constants.TIPO_ATTO_DECRETO));

			List<AttoForm> attiEsecuzioneGiudDefMisPatrRC = attoService.getAttiByProcedimento(idProcedimento,
					Long.valueOf(Constants.TIPO_ATTO_DECRETO_II_GRADO), Constants.SENTENZA_REVOCAZIONE_DELLA_CONFISCA);
			inizializzaNodi(attiEsecuzioneGiudDefMisPatrRC, esecuzioneGiudDefMisPatrNode,
					Long.valueOf(Constants.TIPO_ATTO_DECRETO_II_GRADO));

			List<AttoForm> attiEsecuzioneGiudDefMisPatrIS = attoService.getAttiByProcedimento(idProcedimento,
					Long.valueOf(Constants.TIPO_ATTO_ISTANZA), Constants.REVOCAZIONE_DELLA_CONFISCA);
			inizializzaNodi(attiEsecuzioneGiudDefMisPatrIS, esecuzioneGiudDefMisPatrNode,
					Long.valueOf(Constants.TIPO_ATTO_ISTANZA));

			List<AttoForm> attiEsecuzioneGiudDefMisPatrSCDC = attoService.getAttiByProcedimento(idProcedimento,
					Long.valueOf(Constants.TIPO_ATTO_SENTENZA_CASSAZIONE_DA_CDA),
					Constants.SENTENZA_SU_RICORSO_II_GRADO);
			if (attiEsecuzioneGiudDefMisPatrSCDC != null && !attiEsecuzioneGiudDefMisPatrSCDC.isEmpty()) {
				Iterator it = attiEsecuzioneGiudDefMisPatrSCDC.iterator();
				while (it.hasNext()) {
					AttoForm form = (AttoForm) it.next();
					if (form != null && form.getIdProcEmiss() > 0l && form.getIdProcEmiss() != idProcedimento) {

						inizializzaNodi(attiEsecuzioneGiudDefMisPatrSCDC, esecuzioneGiudDefMisPatrNode,
								Long.valueOf(Constants.TIPO_ATTO_SENTENZA_CASSAZIONE_DA_CDA));
					}
				}
			}

			// popola il nodo attiEsecuzioneGiudDef con Atti in Archivio
			Node esecuzioneGiudDefNode = tree.getIndex().get(ProcedimentoTree.ESECUZIONE_GIUDIZIO_DEFINITIVO);
			List<AttoForm> attiEsecuzioneAttiInArchivio = attoService.getAttiByProcedimento(idProcedimento,
					Long.valueOf(Constants.TIPO_ATTO_ATTI_IN_ARCHIVIO), Constants.CategoriaAtto.ATTI_IN_ARCHIVIO);
			inizializzaNodi(attiEsecuzioneAttiInArchivio, esecuzioneGiudDefNode,
					Long.valueOf(Constants.CategoriaAtto.ATTI_IN_ARCHIVIO), -1);

			// popola il nodo ATTI_GENERICI
			Node attiGenericiNode = tree.getIndex().get(ProcedimentoTree.ATTI_GENERICI);
			tipoAtto = 14l;
			categoriaAtto = getCategorieAtto(ProcedimentoTree.ATTI_GENERICI);
			List<AttoForm> attiGenerici = attoService.getAttiByProcedimento(idProcedimento, tipoAtto, categoriaAtto);
			inizializzaNodi(attiGenerici, attiGenericiNode, tipoAtto, -1l);

			// popola il nodo ATTI_GENERICI Versionamento
			categoriaAtto = getCategorieAtto(ProcedimentoTree.ATTI_GENERICI);
			long tipoAttoVer = 24l;

			List<AttoForm> attiGenericiVer = attoService.getAttiByProcedimento(idProcedimento, tipoAttoVer,
					categoriaAtto);
			inizializzaNodi(attiGenericiVer, attiGenericiNode, tipoAttoVer, -1l);

		} else if (tipoUfficio == ProcedimentoTree.PG_CDA) {
			// valorizza i nodi dell'albero nel caso di
			// tipoUfficio = 'Procura Generale presso CDA'
			Node propostaNode = tree.getIndex().get(ProcedimentoTree.PROPOSTA);
			// caricamento della proposta
			long idUfficio = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
			AttoForm proposta = dettaglioPropostaAtto(idUfficio, idProcedimento, -1, true);
			List<AttoForm> propostaTmp = new ArrayList<AttoForm>();
			propostaTmp.add(proposta);
			if (proposta.getCodAtto() > 0) {
				inizializzaNodi(propostaTmp, propostaNode, 9l, 2l);
			}
			// popola il nodo DECRETI_INTERLOCUTORI
			Node decretiInterlocutoriNode = tree.getIndex().get(ProcedimentoTree.DECRETI_INTERLOCUTORI);
			long tipoAtto = 3l;
			String categoriaAtto = getCategorieAtto(ProcedimentoTree.DECRETI_INTERLOCUTORI);
			List<AttoForm> attiInterlocutori = attoService.getAttiByProcedimento(idProcedimento, tipoAtto,
					categoriaAtto);
			inizializzaNodi(attiInterlocutori, decretiInterlocutoriNode, tipoAtto, -1l);

			// popola il nodo DECRETI_DECISORI
			Node postDibattimentoIGradoNode = tree.getIndex().get(ProcedimentoTree.POST_DIBATTIMENTO_PRIMO_GRADO);
			categoriaAtto = getCategorieAtto(ProcedimentoTree.DECRETI_DECISORI);
			List<AttoForm> decretiDecisori = attoService.getAttiByProcedimento(idProcedimento, tipoAtto, categoriaAtto);
			inizializzaNodi(decretiDecisori, postDibattimentoIGradoNode, tipoAtto, -1l);

			Node udienzaNode = tree.getIndex().get(ProcedimentoTree.UDIENZA);
			List<AttoForm> decretiFissazioneUdienza = attoService.getAttiByProcedimento(idProcedimento, 18l,
					Constants.DECRETO_FISSAZIONE_UDIENZA_II_GRADO + ","
							+ Constants.DECRETO_ANTICIPAZIONE_UDIENZA_II_GRADO + ","
							+ Constants.DECRETO_RINVIO_UDIENZA_II_GRADO);
			inizializzaNodi(decretiFissazioneUdienza, udienzaNode, 18l,
					Long.parseLong(Constants.DECRETO_FISSAZIONE_UDIENZA));

			Node verbaliNode = tree.getIndex().get(ProcedimentoTree.VERBALI_UDIENZE);
			List<AttoForm> attiVerbali = attoService.getAttiByProcedimento(idProcedimento, Long.valueOf("16"),
					"313, 21");
			inizializzaNodi(attiVerbali, verbaliNode, Long.valueOf("16"));

			Node dibattimentoIIGradoNode = tree.getIndex().get(ProcedimentoTree.DIBATTIMENTO_SECONDO_GRADO);
			List<AttoForm> revocaOpposizioneDissequestroIIGrado = attoService.getAttiByProcedimento(idProcedimento, 18l,
					Constants.REVOCA_DECRETO_OPPOSIZIONE_DISSEQUESTRO_II_GRADO);
			inizializzaNodi(revocaOpposizioneDissequestroIIGrado, dibattimentoIIGradoNode, 18l, -1l);

			Node decretiInterlocutoriIIGradoNode = tree.getIndex()
					.get(ProcedimentoTree.DECRETI_INTERLOCUTORI_SECONDO_GRADO);
			List<AttoForm> attiDibattimentoIIGrado = attoService.getAttiByProcedimento(idProcedimento,
					Long.valueOf("18"), "323,324,339");
			inizializzaNodi(attiDibattimentoIIGrado, decretiInterlocutoriIIGradoNode, 18l, -1l);

			Node impugnazioniIGradoNode = tree.getIndex().get(ProcedimentoTree.IMPUGNAZIONI_PRIMO_GRADO);
			List<AttoForm> impugnazioniIGrado = attoService.getAttiByProcedimento(idProcedimento, 4, "5,337,338");
			inizializzaNodi(impugnazioniIGrado, impugnazioniIGradoNode, 4, -1l);

			List<AttoForm> attiOpposizioneRestituzione = attoService.getAttiByProcedimento(idProcedimento,
					Long.valueOf("4"), "6");
			inizializzaNodi(attiOpposizioneRestituzione, impugnazioniIGradoNode, 4l, -1l);

			Node postDibattimentoIIGradoNode = tree.getIndex().get(ProcedimentoTree.POST_DIBATTIMENTO_SECONDO_GRADO);
			List<AttoForm> decretiDecisoriIIGrado = attoService.getAttiByProcedimento(idProcedimento, 18,
					Constants.DECRETO_DECISORIO_II_GRADO);
			inizializzaNodi(decretiDecisoriIIGrado, postDibattimentoIIGradoNode, 18, -1l);

			List<AttoForm> ImpugnazioniIIGrado = attoService.getAttiByProcedimento(idProcedimento, 19l, "24,335");
			inizializzaNodi(ImpugnazioniIIGrado, postDibattimentoIIGradoNode, 19l, -1l);

			// popola il nodo ATTI_GENERICI
			Node attiGenericiNode = tree.getIndex().get(ProcedimentoTree.ATTI_GENERICI);
			tipoAtto = 14l;
			categoriaAtto = getCategorieAtto(ProcedimentoTree.ATTI_GENERICI);
			List<AttoForm> attiGenerici = attoService.getAttiByProcedimento(idProcedimento, tipoAtto, categoriaAtto);
			inizializzaNodi(attiGenerici, attiGenericiNode, tipoAtto, -1l);

			Node sentenzaCassazione = tree.getIndex().get(ProcedimentoTree.GIUDIZIO_LEGITTIMITA);
			List<AttoForm> listaSentenzaCassazione = attoService.getAttiByProcedimento(idProcedimento, 21,
					Constants.SENTENZA_SU_RICORSO_II_GRADO);
			inizializzaNodi(listaSentenzaCassazione, sentenzaCassazione, 21, 40l);

			// popola il nodo ATTI_GENERICI Versionamento
			categoriaAtto = getCategorieAtto(ProcedimentoTree.ATTI_GENERICI);
			long tipoAttoVer = 24l;
			List<AttoForm> attiGenericiVer = attoService.getAttiByProcedimento(idProcedimento, tipoAttoVer,
					categoriaAtto);
			inizializzaNodi(attiGenericiVer, attiGenericiNode, tipoAttoVer, -1l);

		} else if (tipoUfficio == ProcedimentoTree.CORTE_D_APPELLO) {

			Node iscrizioneNode = tree.getIndex().get(ProcedimentoTree.ISCRIZIONE_PROCEDIMENTO);
			Node dibattimentoIIGradoNode = tree.getIndex().get(ProcedimentoTree.DIBATTIMENTO_SECONDO_GRADO);
			Node certificatoRitoNode = tree.getIndex().get(ProcedimentoTree.CERTIFICATO_RITO);
			Node udienzeNode = tree.getIndex().get(ProcedimentoTree.UDIENZA);
			Node decretiInterlocutoriNode = tree.getIndex().get(ProcedimentoTree.DECRETI_INTERLOCUTORI);
			Node verbaliNode = tree.getIndex().get(ProcedimentoTree.VERBALI_UDIENZE);
			Node postDibattimentoIIGradoNode = tree.getIndex().get(ProcedimentoTree.POST_DIBATTIMENTO_SECONDO_GRADO);
			Node giudizioLegittimitaNode = tree.getIndex().get(ProcedimentoTree.GIUDIZIO_LEGITTIMITA);
			Node esecuzioneGiudDefMisPersNode = tree.getIndex()
					.get(ProcedimentoTree.ESECUZIONE_GIUDIZIO_DEFINITIVO_MISURE_PERS);
			Node attiGenericiNode = tree.getIndex().get(ProcedimentoTree.ATTI_GENERICI);
			Node eccezioneIncostituzNode = tree.getIndex().get(ProcedimentoTree.ECCEZIONE_DI_INCOSTITUZIONALITA);

			List<AttoForm> attiIscrizione = attoService.getAttiByProcedimento(idProcedimento, Long.valueOf("4"),
					"5,337,338");

			List<AttoForm> attiDibattimentoIIGrado = new ArrayList<AttoForm>();
			List<AttoForm> attiCertificatoRito = new ArrayList<AttoForm>();
			List<AttoForm> attiUdienze = attoService.getAttiByProcedimento(idProcedimento, Long.valueOf("18"),
					"25,59,60,65,66");
			List<AttoForm> attiDecretiInterlocutori = attoService.getAttiByProcedimento(idProcedimento,
					Long.valueOf("18"), "324,323,322,339");
			List<AttoForm> attiVerbali = attoService.getAttiByProcedimento(idProcedimento, Long.valueOf("16"),
					"313, 21");

			List<AttoForm> decretoDecisorioIIGrado = attoService.getAttiByProcedimento(idProcedimento,
					Long.valueOf("18"), "22");

			List<AttoForm> attiGiudizioLegittimita = new ArrayList<AttoForm>();
			// Caricamente degli atti di tipo redazione scheda
			List<AttoForm> attiEsecuzioneGiudDef = attoService.getAttiByProcedimento(idProcedimento,
					Long.valueOf(Constants.TIPO_ATTO_ESECUTIVO), Constants.REDAZIONE_SCHEDA);

			List<AttoForm> attiGenerici = new ArrayList<AttoForm>();
			List<AttoForm> attiEccezioneIncostituz = new ArrayList<AttoForm>();

			inizializzaNodi(attiIscrizione, iscrizioneNode, Long.valueOf("4"));
			inizializzaNodi(attiDibattimentoIIGrado, dibattimentoIIGradoNode, 0);
			inizializzaNodi(attiCertificatoRito, certificatoRitoNode, 0);
			inizializzaNodi(attiUdienze, udienzeNode, Long.valueOf("18"));
			inizializzaNodi(attiDecretiInterlocutori, decretiInterlocutoriNode, Long.valueOf("18"));
			inizializzaNodi(attiVerbali, verbaliNode, Long.valueOf("16"));
			inizializzaNodi(decretoDecisorioIIGrado, postDibattimentoIIGradoNode, Long.valueOf("18"));
			inizializzaNodi(attiGiudizioLegittimita, giudizioLegittimitaNode, 0);
			inizializzaNodi(attiEsecuzioneGiudDef, esecuzioneGiudDefMisPersNode,
					Long.valueOf(Constants.REDAZIONE_SCHEDA));
			inizializzaNodi(attiGenerici, attiGenericiNode, 0);
			inizializzaNodi(attiEccezioneIncostituz, eccezioneIncostituzNode, 0);

			// popola il nodo attiEsecuzioneGiudDef con Atti in Archivio
			Node esecuzioneGiudDefNode = tree.getIndex().get(ProcedimentoTree.ESECUZIONE_GIUDIZIO_DEFINITIVO);
			List<AttoForm> attiEsecuzioneAttiInArchivio = attoService.getAttiByProcedimento(idProcedimento,
					Long.valueOf(Constants.TIPO_ATTO_ATTI_IN_ARCHIVIO),
					Constants.CategoriaAtto.RESTITUZIONE_ATTI_IN_TRIBUNALE);
			inizializzaNodi(attiEsecuzioneAttiInArchivio, esecuzioneGiudDefNode,
					Long.valueOf(Constants.CategoriaAtto.RESTITUZIONE_ATTI_IN_TRIBUNALE), -1);

			List<AttoForm> ImpugnazioniIIGrado = attoService.getAttiByProcedimento(idProcedimento, 19l, "24,335");
			inizializzaNodi(ImpugnazioniIIGrado, postDibattimentoIIGradoNode, 19l, -1l);

			// Stralcio
			List<AttoForm> attiStralcio = attoService.getAttiByProcedimento(idProcedimento, Long.valueOf("13"), "14");
			inizializzaNodi(attiStralcio, decretiInterlocutoriNode, 13);

			Node sentenzaCassazione = tree.getIndex().get(ProcedimentoTree.GIUDIZIO_LEGITTIMITA);
			List<AttoForm> listaSentenzaCassazione = attoService.getAttiByProcedimento(idProcedimento, 21,
					Constants.SENTENZA_SU_RICORSO_II_GRADO);
			inizializzaNodi(listaSentenzaCassazione, sentenzaCassazione, 21, 40l);

			Node esecuzioneGiudDefMisPatrNode = tree.getIndex()
					.get(ProcedimentoTree.ESECUZIONE_GIUDIZIO_DEFINITIVO_MISURE_PATR);
			List<AttoForm> attiEsecuzioneGiudDefMisPatr = attoService.getAttiByProcedimento(idProcedimento,
					Long.valueOf(Constants.TIPO_ATTO_DECRETO), Constants.DECRETO_ORDINANZA_REST_BENI);
			inizializzaNodi(attiEsecuzioneGiudDefMisPatr, esecuzioneGiudDefMisPatrNode,
					Long.valueOf(Constants.TIPO_ATTO_DECRETO));

			// popola il nodo ATTI_GENERICI Versionamento
			String categoriaAtto = getCategorieAtto(ProcedimentoTree.ATTI_GENERICI);
			long tipoAttoVer = 24l;
			List<AttoForm> attiGenericiVer = attoService.getAttiByProcedimento(idProcedimento, tipoAttoVer,
					categoriaAtto);
			inizializzaNodi(attiGenericiVer, attiGenericiNode, tipoAttoVer, -1l);
		}

		tree.complete();
	}

	private String getCategorieAtto(String node) {
		String categoriaAtto = null;
		if (node.equalsIgnoreCase(ProcedimentoTree.DECRETI_INTERLOCUTORI)) {
			categoriaAtto = Constants.DECRETO_APPLICAZIONE_PROVVISORIA + ","
					+ Constants.DECRETO_REVOCA_APPLICAZIONE_PROVVISORIA + ","
					+ Constants.DECRETO_SEQUESTRO_SOSPENSIONE_TEMPORANEA + "," + Constants.DECRETO_SEQUESTRO_ANTICIPATO
					+ "," + Constants.DECRETO_SEQUESTRO_URGENTE + "," + Constants.DECRETO_INCOMPETENZA_PROCURA + ","
					+ Constants.DECRETO_REVOCA_SEQUESTRO + "," + Constants.DECRETO_REVOCA_SEQUESTRO_II_GRADO + ","
					+ Constants.DECRETO_CONVALIDA_SEQUESTRO_URGENTE + ","
					+ Constants.DECRETO_CONVALIDA_SEQUESTRO_ANTICIPATO + ","
					+ Constants.DECRETO_RIGETTO_TOTALE_SEQUESTRO + ","
					+ Constants.DECRETO_RIGETTO_STATO_RICHIESTA_SEQUESTRO + "," + Constants.DECRETO_SEQUESTRO_BENI + ","
					+ Constants.DECRETO_PROROGA_SEQUESTRO + "," + Constants.DECRETO_SOSPENSIONE_TEMP_TRIB + ","
					+ Constants.DECRETO_PROROGA_SOSPENSIONE_TRIB + "," + Constants.DECRETO_SEQUESTRO_SOSPENSIONE_TRIB
					+ "," + Constants.DECRETO_SEQUESTRO_ORDINARIO + "," + Constants.DECRETO_SEQUESTRO_ORDINARIO_II_GRADO
					+ "," + Constants.REVOCA_DECRETO_OPPOSIZIONE_DISSEQUESTRO + ","
					+ Constants.DECRETO_INCOMPETENZA_PROCURA + "," + Constants.PERDITA_EFFICACIA_SEQUESTRO;
		} else if (node.equalsIgnoreCase(ProcedimentoTree.DECRETI_DECISORI)) {
			categoriaAtto = Constants.DECRETO_DECISORIO_I_GRADO + ","
					+ Constants.DECRETO_INCOMPETENZA_RESTITUZIONE_ATTI_PROCURA + ","
					+ Constants.DECRETO_INCOMPETENZA_TRASF_ALTRO_TRIBUNALE;
		} else if (node.equalsIgnoreCase(ProcedimentoTree.DECRETI_DECISORI_SECONDO_GRADO)) {
			categoriaAtto = Constants.DECRETO_DECISORIO_II_GRADO;

		} else if (node.equalsIgnoreCase(ProcedimentoTree.ATTI_GENERICI)) {
			categoriaAtto = Constants.ATTO_GENERICO + "," + Constants.TRASMISSIONE_CONFLITTO_COMPETENZA + ","
					+ Constants.DECISIONE_CONFLITTO_COMPETENZA + "," + Constants.RICHIESTA_INDAGINI + ","
					+ Constants.ESITO_INDAGINI + "," + Constants.VISTO_ATTI + "," + Constants.CERTIFICATO_PENALE + ","
					+ Constants.CERTIFICATO_CARICHI_PENDENTI + "," + Constants.CERTIFICATO_ANAGRAFICO + ","
					+ Constants.CERTIFICATO_MORTE + "," + Constants.RICHIESTA_PROROGA_SEQUESTRO + ","
					+ Constants.CORREZIONE_ERRORE_MATERIALE + "," + Constants.ATTO_VERSIONAMENTO_SOGGETTO + ","
					+ Constants.ORDINANZA_ART14_CODICE_ANTIMAFIA + "," + Constants.ORDINANZA_ART15_CODICE_ANTIMAFIA
					+ "," + Constants.AUTORIZ_INTERCETTAZIONI_TELEFONICHE + ","
					+ Constants.PROVV_DISTRUZIONE_INTERCETTAZIONI_TEL + ","
					// atti in arrivo da MPS verso SITMP segnalazione MP1
					+ Constants.COMUNICAZIONE_SOSPENSIONE_OBBLIGO_SOGGIORNO + ","
					+ Constants.COMUNICAZIONE_COMMISSIONE_DI_REATO + ","
					+ Constants.COMUNICAZIONE_SOTTOPOSIZIONE_MISURE_SICUREZZA + ","
					+ Constants.COMUNICAZIONE_SOTTOPOSIZIONE_LIBERTA_VIGILATA + ","
					+ Constants.VIOLAZIONE_SOTTOPOSIZIONE_MISURE_PERSONALI + ","
					+ Constants.COMUNICAZIONE_TERMINE_ESECUZIONE_MISPERS + ","
					+ Constants.COMUNICAZIONE_RIPRESA_SOTTOPOSIZIONE_MISPERS + ","
					+ Constants.COMUNICAZIONE_SOSPENSIONE_SOTTOPOSIZIONE_MISPERS + ","
					+ Constants.VERBALE_INIZIO_SOTTOPOSIZIONE_MISPERS + ","
					// Segnalazione EMS-81
					+ Constants.RICHIESTA_PARERE + "," + Constants.PARERE + ","
					+ Constants.DECRETO_RICHIESTA_PARERE_QUESTORE_S + ","
					+ Constants.PARERI_REVOCA_MODIFICA_MISURE_PERSONALI;
		}

		return categoriaAtto;

	}

	public List<TRelProcProc> getProc1ByIdProc2(final long idProc2, final long idUffMitt, final long idUffOwn) {

		List<TRelProcProc> lista = procedimentoDao.getProc1ByIdProc2(idProc2, idUffMitt, idUffOwn);
		return lista;
	}

	public List<TRelProcProc> getProc1ByIdProc2(final long idProc2, final long idUffMitt) {

		List<TRelProcProc> lista = procedimentoDao.getProc1ByIdProc2(idProc2, idUffMitt);
		return lista;
	}

	public List<TRelProcProc> getProc1ByIdProc2AndTipoFasc(final long idProc2, final long idUffMitt,
			final long idTipoFasc) {
		List<TRelProcProc> lista = procedimentoDao.getProc1ByIdProc2AndTipoFasc(idProc2, idUffMitt, idTipoFasc);
		return lista;
	}

	public TRelAttoProc getProcByIdAttoAndTipoFasc(final long idAtto, final long idUffOwn, final long idTipoFasc) {
		TRelAttoProc attoProc = procedimentoDao.getProcByIdAttoAndTipoFasc(idAtto, idUffOwn, idTipoFasc);
		return attoProc;
	}

	public void cancellaDirettoInteressato(long idSoggetto, long idProcedimento, int idUfficioUtente, long idVersione,
			String tipoSoggetto, Errors errors, boolean ruoloTerzo) {

		// Segnalazione TMC0718
		long idAttoFonteDaEliminare = 0;
		if (ruoloTerzo == false)
			idAttoFonteDaEliminare = procedimentoDao.getAttoFonteDaEliminare(idSoggetto, idUfficioUtente,
					idProcedimento);
		procedimentoDao.eliminaTRelProcSogInt(idSoggetto, idProcedimento, idUfficioUtente);
		procedimentoDao.eliminaTRelProcSogIntEredi(idSoggetto, idProcedimento, idUfficioUtente);
		procedimentoDao.eliminaTSoggettoInteressatoAndTPersona(idSoggetto, idUfficioUtente, idVersione, tipoSoggetto,
				idProcedimento);
		if (idAttoFonteDaEliminare != 0)
			procedimentoDao.eliminaAttoFonteSenzaSoggetto(idUfficioUtente, idProcedimento, idAttoFonteDaEliminare);
	}

	public long aggiornaPersonaGiuridica(SoggettoForm soggettoForm, Long idProc, String codUtente) {

		TPersonaGiur persona = new TPersonaGiur();
		TRelProcSogint relProcSoggInt = new TRelProcSogint();
		TSoggetto soggInt = new TSoggetto();
		mapperBean.map(soggettoForm, persona);
		mapperBean.map(soggettoForm, relProcSoggInt);
		mapperBean.map(soggettoForm, soggInt);
		AssociaPersonaDto associaSoggetto = new AssociaPersonaDto();
		associaSoggetto.setPersonaGiur(persona);
		associaSoggetto.setRelProcSoggInt(relProcSoggInt);
		associaSoggetto.setSoggInt(soggInt);
		associaSoggetto.setIDVER(BigDecimal.valueOf(soggettoForm.getIdVersione()));
		associaSoggetto.setIDPERS(BigDecimal.valueOf(soggettoForm.getIdSoggetto()));
		associaSoggetto.setIDUFFOWN(BigDecimal.valueOf(soggettoForm.getIdUfficio()));
		return procedimentoDao.aggiornaPersonaGiuridica(associaSoggetto, idProc, codUtente);
	}

	public Long getIdByAttoEUfficio(Long idAtto, Integer idUfficioUtente) {

		return procedimentoDao.getIdByAttoEUfficio(idAtto, idUfficioUtente);
	}

	public List<TDefPers> definitivitaMisurePersonali(long idProcedimento, long idAtto, Integer idUfficioUtente,
			String tipoRicerca) {

		return procedimentoDao.getTDefPersById(idProcedimento, 0, idAtto, idUfficioUtente.intValue(), tipoRicerca);
	}

	public List<TDefPatr> definitivitaMisurePatrimoniali(long idProcedimento, long idAtto, Integer idUfficioUtente,
			String tipoRicerca) {

		return procedimentoDao.getTDefPatrById(idProcedimento, idAtto, idUfficioUtente, 0, 0, 0, tipoRicerca);
	}

	/**
	 * @param idUffOwn
	 * @param listaIdSoggetti
	 * @return List<ProcedimentoForm>
	 */
	public List<ProcedimentoForm> listaAccertamentiConSoggettiProposta(int idUffOwn, String idProced,
			String listaIdSoggetti) {

		List<ProcedimentoForm> listaAccertamenti = null;
		if (listaIdSoggetti.length() > 0) {
			listaAccertamenti = new ArrayList<ProcedimentoForm>();
			List<TProcedimento> lista = procedimentoDao.listaAccertamentiConSoggettiProposta(idUffOwn, idProced,
					listaIdSoggetti);
			Iterator<TProcedimento> iter = lista.iterator();
			while (iter.hasNext()) {
				Map map = (Map) iter.next();
				TProcedimento proc = (TProcedimento) map.get("accertamenti");
				ProcedimentoForm procForm = new ProcedimentoForm();
				mapperBean.map(proc, procForm);
				listaAccertamenti.add(procForm);
			}
		}
		return listaAccertamenti;
	}

	public void inserisciTRelProcProc(long idProc1, long idProc2, int idUffOwn, String codUtente, long idTipoAtto,
			long idCatAtto, String codiTipoRel) {

		procedimentoDao.inserisciTRelProcProc(idProc1, idProc2, idUffOwn, codUtente, idTipoAtto, idCatAtto,
				codiTipoRel);
	}

	public SoggettoForm getPersonaFisicaByCampiChiave(SoggettoForm soggettoForm, int idUffOwn) {

		TPersonaFisica personaFisica = new TPersonaFisica();
		mapperBean.map(soggettoForm, personaFisica);
		personaFisica = procedimentoDao.getPersonaFisicaByCampiChiave(personaFisica, idUffOwn);
		SoggettoForm soggetto = null;
		if (personaFisica != null) {
			soggetto = new SoggettoForm();
			mapperBean.map(personaFisica, soggetto);
		}
		return soggetto;
	}

	public SoggettoForm getPersonaGiuridicaByCampiChiave(SoggettoForm soggettoForm, int idUffOwn) {

		TPersonaGiur personaGiuridica = new TPersonaGiur();
		mapperBean.map(soggettoForm, personaGiuridica);
		personaGiuridica = procedimentoDao.getPersonaGiuridicaByCampiChiave(personaGiuridica, idUffOwn);
		SoggettoForm soggetto = null;
		if (personaGiuridica != null) {
			soggetto = new SoggettoForm();
			mapperBean.map(personaGiuridica, soggetto);
		}
		return soggetto;
	}

	public boolean getAttoDecretoFissUdienzaProcedimento(long idProcedimento, long idUfficio) {

		boolean esiste = false;
		List udienze = procedimentoDao.getDecretoFissUdienzaProcedimento(idProcedimento, idUfficio);
		if (udienze.size() > 0)
			esiste = true;
		return esiste;
	}

	// INIZIO GESTIONE DOCUMENTALE

	/**
	 * Crea il fascicolo nel documentale e aggiorna il codice fascicolo su
	 * T_PROCEDIMENTO
	 */
	public boolean inserisciECreaFascicoloPDoc(long idProcedimento, String codiceFascicolo) {

		int uffOwner = procedimentoDao.getUffOwner(idProcedimento);
		if (logger.isDebugEnabled()) {
			logger.debug("====>   0.1) Creazione e aggiornamento Fascicolo PDoc ==> id: " + idProcedimento
					+ "  Fascicolo " + codiceFascicolo);
		}
		boolean esitoCreaFascicoloPDoc = documentaleService.creaFascicolo(codiceFascicolo, null, "Fascicolo SMP",
				uffOwner);

		if (esitoCreaFascicoloPDoc == true) {
			//TODO: GC 15/07/2020: Secondo me questa chiamata nons erve a niente perchè il codice fascicolo viene passato da SMP e non ricevuto dalla pdoc
			esitoCreaFascicoloPDoc = procedimentoDao.inserisciFascicoloPDoc(idProcedimento, codiceFascicolo);
			// GC 14/07/2020: Aggiunto il log in caso d'errore
			if (esitoCreaFascicoloPDoc != true) {
				logger.info("		1.1) ERRORE: Fascicolo: " + codiceFascicolo + " inserito in PDOC ma non aggiornato su SITmp!");
			}
		}
		// GC 14/07/2020: Aggiunto il log in caso d'errore
		// GC 15/07/2020: Il log era doppio
//		else {
//			logger.info("		1.1) ERRORE: Fascicolo: " + codiceFascicolo + " non creato in PDOC!");
//		}
		return esitoCreaFascicoloPDoc;
	}

	// FINE GESTIONE DOCUMENTALE

	public TProcedimento getProcedimentoById(long idProcedimento) {

		return procedimentoDao.getProcedimentoById(idProcedimento);
	}

	public List<TRelProcProc> getTRelProcProcByProc1(final long id, Character flgTipoRel, final String descUtente,
			final String nomeMetodo) {

		return procedimentoDao.getTRelProcProcByProc1(id, flgTipoRel, descUtente, nomeMetodo);
	}

	public TProcedimento getTRelProcProcByProc2(final long id, Character flgTipoRel) {

		List<TRelProcProc> lista = procedimentoDao.getTRelProcProcByProc2(id, flgTipoRel);
		if (lista.size() > 0) {
			TRelProcProc item = (TRelProcProc) lista.get(0);
			return procedimentoDao.getProcedimentoById(item.getId().getCodiKIdProc1());
		}
		return null;
	}

	public String getListaRelProcProcByProc1(final long id, Character flgTipoRel, final String descUtente,
			final String nomeMetodo) {

		String listRelProcProcByProc1 = "";
		List<TRelProcProc> hm = procedimentoDao.getTRelProcProcByProc1(id, flgTipoRel, descUtente, nomeMetodo);
		for (Iterator<TRelProcProc> iterator = hm.iterator(); iterator.hasNext();) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			TRelProcProc tRelProcProc = (TRelProcProc) row.get("relProcProc");
			listRelProcProcByProc1 = listRelProcProcByProc1.concat(String.valueOf(tRelProcProc.getNumeAnnoProc2())
					+ Constants.FormattazioneAnnoNumeroAtto.DIVISORE_ANNO_NUMERO_ATTO
					+ String.valueOf(tRelProcProc.getNumeProcedimento2()) + " - ");
		}

		if (listRelProcProcByProc1.length() > 2) {
			listRelProcProcByProc1 = listRelProcProcByProc1.substring(0, listRelProcProcByProc1.length() - 2);
		}

		return listRelProcProcByProc1;
	}

	public int getNumSogInt(Long idProcedimento, int idUfficioUtente) {

		int risultato = 0;
		List<TRelProcSogint> listaSog = procedimentoDao.getSogIntPerProcedimento(idProcedimento, idUfficioUtente);
		if (listaSog != null) {
			risultato = listaSog.size();
		}
		return risultato;
	}

	public List<TRelIterProc> verificaTRelIterProc(final long idProcedimento, final int idUfficioUtente) {

		return procedimentoDao.verificaTRelIterProc(idProcedimento, idUfficioUtente);

	}

	/**
	 * getListaProcedimentiRelProcProc
	 * 
	 * @param idProcedimento
	 * @param idUffOwn
	 * @param idUffMitt
	 * @return
	 */
	public List getListaProcedimentiRelProcProc(long idProcedimento, Integer idUffOwn, Integer idUffMitt,
			String funzione) {

		List queryResults = null;
		Long tipoFascicolo = null;

		queryResults = procedimentoDao.getListaProcedimentiRelProcProc(idProcedimento, idUffOwn, idUffMitt, funzione);

		List<ProcedimentoForm> risultati = new ArrayList<ProcedimentoForm>();
		if ((queryResults == null) || queryResults.isEmpty()) {
			return risultati;
		}

		Iterator iterator = queryResults.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			TProcedimento tmpProc = (TProcedimento) row.get("PROC");
			ProcedimentoForm form = new ProcedimentoForm();
			mapperBean.map(tmpProc, form);
			risultati.add(form);
		}
		return risultati;
	}

	public List getListaProcedimentiCdaTrib(long idProcedimento, Integer idUffOwn) {

		List<TRelProcProc> risultati = procedimentoDao.getListaProcedimentiCdaTrib(idProcedimento, idUffOwn);

		return risultati;
	}

	public List getListaProcedimentiTribCda(long idProcedimento, Integer idUffOwn) {

		List<TRelProcProc> risultati = procedimentoDao.getListaProcedimentiTribCda(idProcedimento, idUffOwn);

		return risultati;
	}

	public List<BeneSoggettoForm> listaSoggettiMisuraPersonaleRevocaDefinitiva(long idProcedimento, long idAtto,
			int idUffOwn, String descUtente, String tipoFunzione, String nomeMetodo) {

		// 2.Recupero tutte le misure collegate e le setto 'misure' del
		// BeneSoggettoForm

		List<BeneSoggettoForm> risultati = new ArrayList<BeneSoggettoForm>();// Lista
																				// finale
		// Recupero tutti i soggetti relativi alle Misure Personali presenti
		List idSoggetti = procedimentoDao.listaSoggettiMisPersRevocaDefinitivita(idProcedimento, idAtto, idUffOwn,
				descUtente, tipoFunzione, nomeMetodo);
		if ((idSoggetti == null) || idSoggetti.isEmpty()) {
			return risultati;
		}
		Iterator iterator = idSoggetti.iterator();
		while (iterator.hasNext()) {
			// Per ogni soggetto ...
			HashMap first = (HashMap) iterator.next();
			BigDecimal idSog = (BigDecimal) first.get("CODI_FK_ID_SOGG_INT");
			Long idSogg = idSog.longValue();
			BeneSoggettoForm sogMisForm = new BeneSoggettoForm();
			SoggettoForm soggettoForm = new SoggettoForm();
			List<MisuraForm> misure = new ArrayList<MisuraForm>();
			// ... recupero le informazioni relative al soggetto stesso
			soggettoForm = getPersonaById(idSogg, idUffOwn);
			// ... recupero la lista delle Misure Personali collegate
			// (P,C,A)
			List queryResults = procedimentoDao.listaMisPersPerSoggettoRevocaDefinitivita(idSogg, idProcedimento,
					idAtto, idUffOwn, tipoFunzione);
			if ((queryResults == null) || queryResults.isEmpty()) {
				return risultati;
			}
			Iterator iterator1 = queryResults.iterator();
			while (iterator1.hasNext()) {
				Map<String, Object> row = (Map<String, Object>) iterator1.next();
				TRelAttoMisPers attoMisPers = (TRelAttoMisPers) row.get("relam");
				TMisuraPers misPers = (TMisuraPers) row.get("mis");
				TCauzione cauz = (TCauzione) row.get("cauz");
				SoggettoForm soggForm = new SoggettoForm();
				mapperBean.map(attoMisPers, soggForm);
				soggForm.getMisura().setIdMisura(attoMisPers.getId().getCodiKIdMisuraPers());
				soggForm.setIdSoggetto(misPers.getTSoggetto().getId().getCodiKIdSoggInt());
				if (cauz != null) {
					if (cauz.getImpoCauzione() != null)
						soggForm.getMisura().setImporto(cauz.getImpoCauzione());
				}
				// *** START GESTIONE ESITO ***
				String descrizioneMisura = soggForm.getMisura().getTipoMisura().getDescrizione();
				// Se tipoMisura è 1,2,3 ossia MisuraPersonale
				if (soggForm.getMisura().getTipoMisura().getId().equalsIgnoreCase("1")
						|| soggForm.getMisura().getTipoMisura().getId().equalsIgnoreCase("2")
						|| soggForm.getMisura().getTipoMisura().getId().equalsIgnoreCase("3")) {
					if (soggForm.getMisura().getDurata() != null) {
						descrizioneMisura = descrizioneMisura + " - " + soggForm.getMisura().getDurata();
					}
				} else // Se tipoMisura è 25 ossia Cauzione
				if (soggForm.getMisura().getTipoMisura().getId().equalsIgnoreCase("25")) {
					if (soggForm.getMisura().getImporto() != null) {
						descrizioneMisura = descrizioneMisura + " - Importo : " + soggForm.getMisura().getImporto();
					}
				} else // Se tipoMisura è 26 ossia Art.27
				if (soggForm.getMisura().getTipoMisura().getId().equalsIgnoreCase("26")) {
					descrizioneMisura = descrizioneMisura + "";
				} else
					descrizioneMisura = null;
				soggForm.getMisura().setDescrizioneMisura(descrizioneMisura);
				// *** END GESTIONE ESITO ***
				long idMisura = soggForm.getMisura().getIdMisura();
				TDefPers definitivita = null;
				List<TDefPers> risultatiDefPers = procedimentoDao.getTDefPersById(idProcedimento, idMisura, idAtto,
						idUffOwn, null);
				if (risultatiDefPers.size() > 0)
					definitivita = risultatiDefPers.get(0);
				if (definitivita != null) {
					if (definitivita.getDataDefinitivita() != null)
						soggForm.getMisura().setDataDefinitivita(definitivita.getDataDefinitivita());
					if (definitivita.getDataInvio() != null)
						soggForm.getMisura().setDisabledCheckDefinitivita(true);
				}
				MisuraForm misura = new MisuraForm();
				misura = soggForm.getMisura();
				misure.add(misura);
			}
			// Controllo se tutte le misure hanno il check a true,
			// imposto il valore per disabilitare l'id Soggetto
			// corrispondente
			boolean disabilita = true;
			for (MisuraForm misuraForm : misure) {
				if (!misuraForm.isDisabledCheckDefinitivita()) {
					disabilita = false;
					break;
				}
			}
			if (disabilita)
				soggettoForm.setDisabledCheckDefinitivita(true);
			// Fine controllo check Definitivita'
			// setto 'soggetto' nel mio BeneSoggettoForm(sogMisForm)
			sogMisForm.setSoggetto(soggettoForm);
			// Fine disabilita check
			// Recuperate le misure nel mio BeneSoggettoForm(sogMisForm)
			// setto 'misure'
			sogMisForm.setMisure(misure);
			// Fuori dal ciclo aggiungo il mio BeneSoggettoForm(sogMisForm)
			// a 'risultati'
			risultati.add(sogMisForm);
		}
		return risultati;
	}

	public List<BeneSoggettoForm> listaSoggettiMisuraPersonaleRevocaDefinitivaFiltrati(long idProcedimento, long idAtto,
			int idUffOwn, String descUtente, String tipoFunzione, String nomeMetodo, long idSoggettoDiretto) {

		// 2.Recupero tutte le misure collegate e le setto 'misure' del
		// BeneSoggettoForm

		List<BeneSoggettoForm> risultati = new ArrayList<BeneSoggettoForm>();// Lista
																				// finale
		// Recupero tutti i soggetti relativi alle Misure Personali presenti
		List idSoggetti = procedimentoDao.listaSoggettiMisPersRevocaDefinitivitaFiltrati(idProcedimento, idAtto,
				idUffOwn, descUtente, tipoFunzione, nomeMetodo, idSoggettoDiretto);
		if ((idSoggetti == null) || idSoggetti.isEmpty()) {
			return risultati;
		}
		Iterator iterator = idSoggetti.iterator();
		while (iterator.hasNext()) {
			// Per ogni soggetto ...
			HashMap first = (HashMap) iterator.next();
			BigDecimal idSog = (BigDecimal) first.get("CODI_FK_ID_SOGG_INT");
			Long idSogg = idSog.longValue();
			BeneSoggettoForm sogMisForm = new BeneSoggettoForm();
			SoggettoForm soggettoForm = new SoggettoForm();
			List<MisuraForm> misure = new ArrayList<MisuraForm>();
			// ... recupero le informazioni relative al soggetto stesso
			soggettoForm = getPersonaById(idSogg, idUffOwn);
			// ... recupero la lista delle Misure Personali collegate
			// (P,C,A)
			List queryResults = procedimentoDao.listaMisPersPerSoggettoRevocaDefinitivita(idSogg, idProcedimento,
					idAtto, idUffOwn, tipoFunzione);
			if ((queryResults == null) || queryResults.isEmpty()) {
				return risultati;
			}
			Iterator iterator1 = queryResults.iterator();
			while (iterator1.hasNext()) {
				Map<String, Object> row = (Map<String, Object>) iterator1.next();
				TRelAttoMisPers attoMisPers = (TRelAttoMisPers) row.get("relam");
				TMisuraPers misPers = (TMisuraPers) row.get("mis");
				TCauzione cauz = (TCauzione) row.get("cauz");
				SoggettoForm soggForm = new SoggettoForm();
				mapperBean.map(attoMisPers, soggForm);
				soggForm.getMisura().setIdMisura(attoMisPers.getId().getCodiKIdMisuraPers());
				soggForm.setIdSoggetto(misPers.getTSoggetto().getId().getCodiKIdSoggInt());
				if (cauz != null) {
					if (cauz.getImpoCauzione() != null)
						soggForm.getMisura().setImporto(cauz.getImpoCauzione());
				}
				// *** START GESTIONE ESITO ***
				String descrizioneMisura = soggForm.getMisura().getTipoMisura().getDescrizione();
				// Se tipoMisura è 1,2,3 ossia MisuraPersonale
				if (soggForm.getMisura().getTipoMisura().getId().equalsIgnoreCase("1")
						|| soggForm.getMisura().getTipoMisura().getId().equalsIgnoreCase("2")
						|| soggForm.getMisura().getTipoMisura().getId().equalsIgnoreCase("3")) {
					if (soggForm.getMisura().getDurata() != null) {
						descrizioneMisura = descrizioneMisura + " - " + soggForm.getMisura().getDurata();
					}
				} else // Se tipoMisura è 25 ossia Cauzione
				if (soggForm.getMisura().getTipoMisura().getId().equalsIgnoreCase("25")) {
					if (soggForm.getMisura().getImporto() != null) {
						descrizioneMisura = descrizioneMisura + " - Importo : " + soggForm.getMisura().getImporto();
					}
				} else // Se tipoMisura è 26 ossia Art.27
				if (soggForm.getMisura().getTipoMisura().getId().equalsIgnoreCase("26")) {
					descrizioneMisura = descrizioneMisura + "";
				} else
					descrizioneMisura = null;
				soggForm.getMisura().setDescrizioneMisura(descrizioneMisura);
				// *** END GESTIONE ESITO ***
				long idMisura = soggForm.getMisura().getIdMisura();
				TDefPers definitivita = null;
				List<TDefPers> risultatiDefPers = procedimentoDao.getTDefPersById(idProcedimento, idMisura, idAtto,
						idUffOwn, null);
				if (risultatiDefPers.size() > 0)
					definitivita = risultatiDefPers.get(0);
				if (definitivita != null) {
					if (definitivita.getDataDefinitivita() != null)
						soggForm.getMisura().setDataDefinitivita(definitivita.getDataDefinitivita());
					if (definitivita.getDataInvio() != null)
						soggForm.getMisura().setDisabledCheckDefinitivita(true);
				}
				MisuraForm misura = new MisuraForm();
				misura = soggForm.getMisura();
				misure.add(misura);
			}
			// Controllo se tutte le misure hanno il check a true,
			// imposto il valore per disabilitare l'id Soggetto
			// corrispondente
			boolean disabilita = true;
			for (MisuraForm misuraForm : misure) {
				if (!misuraForm.isDisabledCheckDefinitivita()) {
					disabilita = false;
					break;
				}
			}
			if (disabilita)
				soggettoForm.setDisabledCheckDefinitivita(true);
			// Fine controllo check Definitivita'
			// setto 'soggetto' nel mio BeneSoggettoForm(sogMisForm)
			sogMisForm.setSoggetto(soggettoForm);
			// Fine disabilita check
			// Recuperate le misure nel mio BeneSoggettoForm(sogMisForm)
			// setto 'misure'
			sogMisForm.setMisure(misure);
			// Fuori dal ciclo aggiungo il mio BeneSoggettoForm(sogMisForm)
			// a 'risultati'
			risultati.add(sogMisForm);
		}
		return risultati;
	}

	public List<BeneSoggettoForm> listaSoggettiMisPatrRevocaDefinitivita(long idProcedimento, long idAtto, int idUffOwn,
			String codUtente, String tipoFunzione, String nomeMetodo) {

		List<BeneSoggettoForm> risultati = new ArrayList<BeneSoggettoForm>();
		List queryResults = procedimentoDao.listaSoggettiMisPatrRevocaDefinitivita(idProcedimento, idAtto, idUffOwn,
				codUtente, tipoFunzione, nomeMetodo);

		Iterator<TRelProcBene> iter = queryResults.iterator();
		while (iter.hasNext()) {
			Map<String, TRelProcBene> row = (Map<String, TRelProcBene>) iter.next();
			TRelProcBene tmp = (TRelProcBene) row.get("procBene");
			long idBene = tmp.getId().getCodiKIdBene();
			creaBeneSoggettoFormDaIdBene(idProcedimento, idAtto, idUffOwn, risultati, idBene);
		}
		return risultati;
	}

	public List<BeneSoggettoForm> listaSoggettiMisPatrRevocaDefinitivitaFiltrati(long idProcedimento, long idAtto,
			int idUffOwn, String codUtente, String tipoFunzione, String nomeMetodo, long idSoggettoDiretto,
			long idSoggettoTitolare, long idTipologia, long idCategoria, long idSottocategoria) {

		List<BeneSoggettoForm> risultati = new ArrayList<BeneSoggettoForm>();
		List queryResults = procedimentoDao.listaSoggettiMisPatrRevocaDefinitivitaFiltrati(idProcedimento, idAtto,
				idUffOwn, codUtente, tipoFunzione, nomeMetodo, idSoggettoDiretto, idSoggettoTitolare, idTipologia,
				idCategoria, idSottocategoria);

		if ((queryResults == null) || queryResults.isEmpty()) {
			return null;
		}

		for (int i = 0; i < queryResults.size(); i++) {
			long idBenePrecedente = 0l;
			if (i != 0) {
				Map<String, Object> riga = (Map<String, Object>) queryResults.get(i - 1);
				TBene benePrecedente = (TBene) riga.get("bene");
				if ((benePrecedente != null) && (benePrecedente.getId() != null)) {
					idBenePrecedente = benePrecedente.getId().getCodiKIdBene();
				}
			}
			Map<String, Object> row = (Map<String, Object>) queryResults.get(i);
			TBene bene = (TBene) row.get("bene");
			BeneForm beneForm = new BeneForm();
			mapperBean.map(bene, beneForm);
			if (idBenePrecedente != beneForm.getIdBene()) {
				BeneSoggettoForm beneSoggettoForm = new BeneSoggettoForm();
				beneSoggettoForm.setBene(beneForm);
				List<SoggettoForm> soggettiAssociatiAlBene = new ArrayList<SoggettoForm>();
				if (idSoggettoDiretto > 0 || idSoggettoTitolare > 0) {
					soggettiAssociatiAlBene = caricaSoggettiAssociatiFiltrati(queryResults, beneForm, idSoggettoDiretto,
							idSoggettoTitolare, idUffOwn);
				} else {
					soggettiAssociatiAlBene = caricaSoggettiAssociati(queryResults, beneForm, idProcedimento, idAtto,
							idUffOwn);
				}
				beneSoggettoForm.setSoggetti(soggettiAssociatiAlBene);
				risultati.add(beneSoggettoForm);
			}
		}
		return risultati;
	}

	public List<BeneSoggettoForm> listaSoggettiMisPatrDefinitivita(long idProcedimento, long idAtto, int idUffOwn,
			String codUtente, String tipoFunzione, String nomeMetodo) {

		List<BeneSoggettoForm> risultati = new ArrayList<BeneSoggettoForm>();
		List queryResults = procedimentoDao.listaSoggettiMisPatrDefinitivita(idProcedimento, idAtto, idUffOwn,
				codUtente, tipoFunzione, nomeMetodo);

		Iterator<TRelProcBene> iter = queryResults.iterator();
		while (iter.hasNext()) {
			Map<String, TRelProcBene> row = (Map<String, TRelProcBene>) iter.next();
			TRelProcBene tmp = (TRelProcBene) row.get("procBene");
			long idBene = tmp.getId().getCodiKIdBene();
			creaBeneSoggettoFormDaIdBene(idProcedimento, idAtto, idUffOwn, risultati, idBene);
		}
		return risultati;
	}

	public List<BeneSoggettoForm> listaSoggettiMisPatrDefinitivitaFiltrati(long idProcedimento, long idAtto,
			int idUffOwn, String codUtente, String tipoFunzione, String nomeMetodo, long idSoggettoDiretto,
			long idSoggettoTitolare, long idTipologia, long idCategoria, long idSottocategoria) {

		List<BeneSoggettoForm> risultati = new ArrayList<BeneSoggettoForm>();
		List queryResults = procedimentoDao.listaSoggettiMisPatrDefinitivitaFiltrati(idProcedimento, idAtto, idUffOwn,
				codUtente, tipoFunzione, nomeMetodo, idSoggettoDiretto, idSoggettoTitolare, idTipologia, idCategoria,
				idSottocategoria);

		if ((queryResults == null) || queryResults.isEmpty()) {
			return null;
		}

		for (int i = 0; i < queryResults.size(); i++) {
			long idBenePrecedente = 0l;
			if (i != 0) {
				Map<String, Object> riga = (Map<String, Object>) queryResults.get(i - 1);
				TBene benePrecedente = (TBene) riga.get("bene");
				if ((benePrecedente != null) && (benePrecedente.getId() != null)) {
					idBenePrecedente = benePrecedente.getId().getCodiKIdBene();
				}
			}
			Map<String, Object> row = (Map<String, Object>) queryResults.get(i);
			TBene bene = (TBene) row.get("bene");
			BeneForm beneForm = new BeneForm();
			mapperBean.map(bene, beneForm);
			if (idBenePrecedente != beneForm.getIdBene()) {
				BeneSoggettoForm beneSoggettoForm = new BeneSoggettoForm();
				beneSoggettoForm.setBene(beneForm);
				List<SoggettoForm> soggettiAssociatiAlBene = new ArrayList<SoggettoForm>();
				if (idSoggettoDiretto > 0 || idSoggettoTitolare > 0) {
					soggettiAssociatiAlBene = caricaSoggettiAssociatiFiltrati(queryResults, beneForm, idSoggettoDiretto,
							idSoggettoTitolare, idUffOwn);
				} else {
					soggettiAssociatiAlBene = caricaSoggettiAssociati(queryResults, beneForm, idProcedimento, idAtto,
							idUffOwn);
				}
				beneSoggettoForm.setSoggetti(soggettiAssociatiAlBene);
				risultati.add(beneSoggettoForm);
			}
		}
		return risultati;

	}

	public String getNormativaPrevProc(long idProcedimento, long idSogInt, int idUffOwn) {
		return procedimentoDao.getNormativaByTRelProcSogintId(new TRelProcSogintId(idUffOwn, idProcedimento, idSogInt))
				.getDescTipoNorm();
	}

	public void copiaAttoAccertamentoInProcMerito(long idProcAccertamento, long idProcMerito, int idUffOwn,
			String codUtente) {

		procedimentoDao.copiaAttoAccertamentoInProcMerito(idProcAccertamento, idProcMerito, idUffOwn, codUtente);
	}

	public void sostituisciGiudiceDelegato(int idUffOwn, long idMagistratoOut, long idMagistratoIn, long idProcedimento,
			String tipoSostituzione, final String codUtente) {

		procedimentoDao.sostituisciGiudiceDelegato(idUffOwn, idMagistratoOut, idMagistratoIn, idProcedimento,
				tipoSostituzione, codUtente);
	}

	public List<SoggettoForm> getListaSoggettiFisiciProcedimento(long idProcedimento, int idUffOwner) {
		procedimentoDao.getListaSoggettiFisiciProcedimento(idProcedimento, idUffOwner);
		return null;
	}

	public boolean hasAttiInLavorazioni(long idProcedimento, long idUffOwner) {
		return procedimentoDao.hasAttiInLavorazioni(idProcedimento, idUffOwner);
	}

	public TAnManuali getTAnManualiById(long id) {
		return procedimentoDao.getTAnManualiById(id);
	}

	public List<Long> listaSoggettiMisurePersAtto(long idProcedimento, long idAtto, int idUffOwn) {

		List<Long> risultati = new ArrayList<Long>();
		List lista = procedimentoDao.listaSoggettiMisPersDefinitivita(idProcedimento, idAtto, idUffOwn);
		Iterator iterator = lista.iterator();
		while (iterator.hasNext()) {
			HashMap obj = (HashMap) iterator.next();
			BigDecimal idSog = (BigDecimal) obj.get("CODI_FK_ID_SOGG_INT");
			Long idSogg = idSog.longValue();
			risultati.add(idSogg);
		}
		return risultati;
	}

	public List<ProcedimentoForm> getListaProcedimentiSogg(int idUffOwn, Long tipoFascicolo, Long idSogg) {

		List<RicercaProcedimentoDto> queryResults = null;

		queryResults = procedimentoDao.getListaProcedimentiSogg(idUffOwn, tipoFascicolo, idSogg);

		List<ProcedimentoForm> risultati = new ArrayList<ProcedimentoForm>();
		if ((queryResults == null) || queryResults.isEmpty()) {
			return risultati;
		}
		Iterator iterator = queryResults.iterator();
		while (iterator.hasNext()) {
			RicercaProcedimentoDto row = (RicercaProcedimentoDto) iterator.next();
			ProcedimentoForm form = new ProcedimentoForm();
			mapperBean.map(row, form);
			risultati.add(form);
		}
		return risultati;
	}

	public List<ProcedimentoForm> getListaProcedimentiSoggMultiFascicolo(int idUffOwn, String tipiFascicolo,
			Long idSogg) {

		List<RicercaProcedimentoDto> queryResults = null;

		queryResults = procedimentoDao.getListaProcedimentiSoggMultiFascicolo(idUffOwn, tipiFascicolo, idSogg);

		List<ProcedimentoForm> risultati = new ArrayList<ProcedimentoForm>();
		if ((queryResults == null) || queryResults.isEmpty()) {
			return risultati;
		}
		Iterator iterator = queryResults.iterator();
		while (iterator.hasNext()) {
			RicercaProcedimentoDto row = (RicercaProcedimentoDto) iterator.next();
			ProcedimentoForm form = new ProcedimentoForm();
			mapperBean.map(row, form);
			risultati.add(form);
		}
		return risultati;
	}

	public List<MisurePatrSoggDto> misurePatrimonialiSoggetto(int idUffOwn, Long idProc, Long idSoggInt,
			Long idSoggTit) {

		List<MisurePatrSoggDto> listaRelAttoMisPatr = procedimentoDao.misurePatrimonialiSoggetto(idUffOwn, idProc,
				idSoggInt, idSoggTit);
		if (listaRelAttoMisPatr != null && listaRelAttoMisPatr.size() > 0) {
			for (int i = 0; i < listaRelAttoMisPatr.size(); i++) {
				MisurePatrSoggDto misuraPatrSogg = listaRelAttoMisPatr.get(i);
				Long numeroAtto = null;
				Short annoAtto = null;
				String richiesta = null;

				String idTipoAtto = "" + misuraPatrSogg.getCODI_FK_ID_TIPO_ATTO();
				Long idAtto = misuraPatrSogg.getCODI_K_ID_ATTO().longValue();

				if (Utility.isTDecretoRecord(idTipoAtto)) {
					Criterion c1 = Restrictions.eq("id.codiKIdDecreto", idAtto);
					Criterion c2 = Restrictions.eq("id.codiKIdUffOwner", idUffOwn);
					Object obj = attoDao.getAttoByPrimaryKey(TDecreto.class, c1, c2);
					if (obj != null) {
						TDecreto record = (TDecreto) obj;
						numeroAtto = record.getNumeAtto();
						annoAtto = record.getNumeAnnoAtto();
					}
				} else if (Utility.isTIstanzaRecord(idTipoAtto)) {

					Criterion c1 = Restrictions.eq("id.codiKIdIstanza", idAtto);
					Criterion c2 = Restrictions.eq("id.codiKIdUffOwner", idUffOwn);
					Object obj = attoDao.getAttoByPrimaryKey(TIstanza.class, c1, c2);
					if (obj != null) {
						TIstanza record = (TIstanza) obj;
						numeroAtto = record.getNumeAtto();
						annoAtto = record.getNumeAnnoAtto();
						richiesta = record.getDescRichiesta();
					}
				} else if (Utility.isTProvvFGRecord(idTipoAtto)) {
					Criterion c1 = Restrictions.eq("id.codiKIdProvFg", idAtto);
					Criterion c2 = Restrictions.eq("id.codiKIdUffOwner", idUffOwn);
					Object obj = attoDao.getAttoByPrimaryKey(TProvFg.class, c1, c2);
					if (obj != null) {
						TProvFg record = (TProvFg) obj;
						numeroAtto = record.getNumeProvFg();
						annoAtto = record.getNumeAnnoProvFg();
					}
				}

				misuraPatrSogg.setAnnoAtto(annoAtto);
				misuraPatrSogg.setNumeroAtto(numeroAtto);
				misuraPatrSogg.setRichiesta(richiesta);

			}
		}
		return listaRelAttoMisPatr;
	}

	public DifensoreSoggettoForm dettaglioDifensore(long idDifensore) {
		DifensoreSoggettoForm difensoreForm = new DifensoreSoggettoForm();
		TAvvocati difensore = procedimentoDao.dettaglioDifensore(idDifensore);
		mapperBean.map(difensore, difensoreForm);
		return difensoreForm;
	}

	public ProcedimentoForm getDettaglioProcedimentoNum(int idUffOwn, long idProc) {

		RicercaProcedimentoDto row = procedimentoDao.getDettaglioProcedimentoNum(idUffOwn, idProc);
		ProcedimentoForm form = new ProcedimentoForm();
		if (row != null)
			mapperBean.map(row, form);
		return form;
	}

	public List<TProcedimento> getProcAggravamento(int idUffOwn, long idProc, long idSogg) {
		List<TProcedimento> result = null;
		List<Map> lMap = procedimentoDao.getProcAggravamento(idUffOwn, idProc, idSogg);
		if (lMap != null && lMap.size() > 0) {
			result = new ArrayList<TProcedimento>();
			for (int i = 0; i < lMap.size(); i++) {
				Map map = lMap.get(i);
				TProcedimento proc = (TProcedimento) map.get("proc");
				result.add(proc);
			}
		}
		return result;
	}

	public List<TProcedimento> getProcCamere(int idUffOwn, long idProc, long idSogg) {
		List<TProcedimento> result = null;
		List<Map> lMap = procedimentoDao.getProcCamere(idUffOwn, idProc, idSogg);
		if (lMap != null && lMap.size() > 0) {
			result = new ArrayList<TProcedimento>();
			for (int i = 0; i < lMap.size(); i++) {
				Map map = lMap.get(i);
				TProcedimento proc = (TProcedimento) map.get("proc");
				result.add(proc);
			}
		}
		return result;
	}

	public String getNumeroProcByIdProcedimento(long idProcedimento) {
		return procedimentoDao.getNumeroProcByIdProcedimento(idProcedimento);
	}

	public Long aggiornaIstanzaMP(long idUfficio, long idProcedimento, AttoForm attoForm, Long codiKIdAmministratore,
			String codUtente) {

		TAtto atto = new TAtto();

		TRelSoggIst soggIstTmp = new TRelSoggIst();

		mapperBean.map(attoForm, atto);

		Utility.popolaDocumento(attoForm, atto);
		TIstanza istanza = new TIstanza();
		mapperBean.map(attoForm, istanza);

		ImpugnazioneForm impugnazione = new ImpugnazioneForm();

		impugnazione = attoForm.getImpugnazione();

		mapperBean.map(impugnazione, istanza);

		mapperBean.map(attoForm.getImpugnazione(), soggIstTmp);

		TSoggetto soggetto = new TSoggetto();
		TSoggettoId soggettoId = new TSoggettoId();
		soggetto.setId(soggettoId);

		String listaIdSoggetti = "";

		if (impugnazione.getParte() != null) {
			if (impugnazione.getParte().getId() != null && impugnazione.getParte().getId().equals("D")) {

				Iterator itx = impugnazione.getIdsDirettiInteressati().iterator();
				while (itx.hasNext()) {
					listaIdSoggetti += "" + itx.next();
					if (itx.hasNext()) {
						listaIdSoggetti += ",";
					}
				}

			}
			if (impugnazione.getParte().getId() != null && impugnazione.getParte().getId().equals("T")) {

				Iterator itx = impugnazione.getIdsSoggettiTitolari().iterator();
				while (itx.hasNext()) {
					listaIdSoggetti += "" + itx.next();
					if (itx.hasNext()) {
						listaIdSoggetti += ",";
					}
				}

			}
			if (impugnazione.getParte().getId() != null && impugnazione.getParte().getId().equals("A")) {
				List<TAvvocati> listaAvvocati = attoService.getAvvocatiDifensori(idProcedimento, idUfficio,
						Long.parseLong(impugnazione.getAvvocatiDifensori().getId()));

				Iterator itx = impugnazione.getIdsSoggettiDifensori().iterator();
				while (itx.hasNext()) {
					listaIdSoggetti += "" + itx.next();
					if (itx.hasNext()) {
						listaIdSoggetti += ",";
					}
				}

			}

			if (impugnazione.getParte().getId() != null && impugnazione.getParte().getId().equals("E")) {
				List<TAvvocati> listaAvvocati = attoService.getAvvocatiDifensori(idProcedimento, idUfficio,
						Long.parseLong(impugnazione.getAvvocatiDifensori().getId()));

				Iterator itx = impugnazione.getIdsSoggettiDifensori().iterator();
				while (itx.hasNext()) {
					listaIdSoggetti += "" + itx.next();
					if (itx.hasNext()) {
						listaIdSoggetti += ",";
					}
				}

			}

			if (impugnazione.getParte().getId() != null && impugnazione.getParte().getId().equals("I")
					&& attoForm.getCategoriaAtto() != null
					&& attoForm.getCategoriaAtto().getId().equals(Constants.ISTANZA_TERZO_INTERVENIENTE)) {
				List<SoggettoForm> soggettiIntervenienti = soggettiDirettiInteressatiDelProcedimento(idProcedimento,
						Constants.RUOLO_INTERVENTIENTE, Constants.Flag.S);

				Iterator itx = soggettiIntervenienti.iterator();
				while (itx.hasNext()) {
					SoggettoForm sogg = (SoggettoForm) itx.next();
					listaIdSoggetti += "" + sogg.getIdSoggetto();
					if (itx.hasNext()) {
						listaIdSoggetti += ",";
					}
				}

			}
		}

		return procedimentoDao.aggiornaIstanzaMP(idUfficio, idProcedimento, atto, codUtente, istanza, soggIstTmp,
				listaIdSoggetti, codiKIdAmministratore);
	}

	private ImpugnazioneForm getTRelSoggIstByIdIStanzaIdOwner(long idIstanza, int IdUffOwner,
			ImpugnazioneForm impForm) {

		TRelSoggIst soggIm = attoDao.getTRelSoggIstByIdIstanzaIdOwner(idIstanza, IdUffOwner);
		ComboForm comboParte = new ComboForm();
		ComboForm comboDifensore = new ComboForm();

		if (soggIm != null) {

			if (soggIm.getFlgTipoParte() != null)
				comboParte.setId(soggIm.getFlgTipoParte().toString());

			if (soggIm.getTAvvocati() != null && soggIm.getTAvvocati().getCodiKIdAvvocato() != 0)
				comboDifensore.setId(String.valueOf(soggIm.getTAvvocati().getCodiKIdAvvocato()));

			impForm.setDescTipoProp(soggIm.getDescTipoProp());
			impForm.setDescCognAvv(soggIm.getDescCognAvv());
			impForm.setDescNomeAvv(soggIm.getDescNomeAvv());
			impForm.setAvvocatiDifensori(comboDifensore);
			impForm.setParte(comboParte);

		}

		return impForm;
	}

	/**
	 * getListSogAntimafia
	 * 
	 * @param idUffOwn
	 * @param idProc
	 * @return List<SoggettoForm>
	 */
	public List<SoggettoForm> getListSogAntimafia(int idUffOwn, Long idProc) {

		List<SogIntDettaglioProcedimentoDto> soggetti = procedimentoDao.getListSogAntimafia(idUffOwn, idProc);
		List<SoggettoForm> risultati = new ArrayList<SoggettoForm>();

		Iterator<SogIntDettaglioProcedimentoDto> iter = soggetti.iterator();
		while (iter.hasNext()) {
			SogIntDettaglioProcedimentoDto currentDto = iter.next();
			SoggettoForm sForm = new SoggettoForm();
			mapperBean.map(currentDto, sForm);
			risultati.add(sForm);
		}
		return risultati;
	}

	public List<ProcedimentoForm> listaProcedimentiSoggetto(int idUffOwn, long idSogg) {

		List<ListaProcedimentiSoggDto> soggetti = procedimentoDao.listaProcedimentiSoggetto(idUffOwn, idSogg);
		List<ProcedimentoForm> risultati = new ArrayList<ProcedimentoForm>();

		Iterator<ListaProcedimentiSoggDto> iter = soggetti.iterator();
		while (iter.hasNext()) {
			ListaProcedimentiSoggDto currentDto = iter.next();
			ProcedimentoForm sForm = new ProcedimentoForm();
			mapperBean.map(currentDto, sForm);
			risultati.add(sForm);
		}
		return risultati;
	}

	public List<CoadiutoreForm> listaCoadiutori(long idProc, int idUfficio, long idAmm) {

		List<CoadiutoreForm> risultato = new ArrayList<CoadiutoreForm>();
		List<Map> lista = procedimentoDao.listaCoadiutori(idProc, idUfficio, idAmm);
		Iterator<Map> iter = lista.iterator();
		while (iter.hasNext()) {
			Map currentMap = iter.next();
			TRelProcCoadiutori coad = (TRelProcCoadiutori) currentMap.get("coadiutore");
			CoadiutoreForm sForm = new CoadiutoreForm();
			mapperBean.map(coad, sForm);
			risultato.add(sForm);
		}
		return risultato;
	}

	public CoadiutoreForm dettaglioCoadiutore(long idProc, int idUfficio, long idAmm, int idProgrCoad) {

		CoadiutoreForm sForm = null;
		TRelProcCoadiutori coad = procedimentoDao.dettaglioCoadiutore(idProc, idUfficio, idAmm, idProgrCoad);
		if (coad != null) {
			sForm = new CoadiutoreForm();
			mapperBean.map(coad, sForm);
		}
		return sForm;
	}

	public short salvaCoadiutore(CoadiutoreForm coadiutoreForm) {
		TRelProcCoadiutori coadiutore = new TRelProcCoadiutori();
		mapperBean.map(coadiutoreForm, coadiutore);
		return procedimentoDao.salvaCoadiutore(coadiutore);
	}

	public void eliminaCoadiutore(CoadiutoreForm coadiutoreForm) {
		TRelProcCoadiutori coadiutore = new TRelProcCoadiutori();
		mapperBean.map(coadiutoreForm, coadiutore);
		procedimentoDao.eliminaCoadiutore(coadiutore);
	}

	public AmministratoreForm getAmministratoreByIstanza(long idProcedimento, long idIstanza, int idUffOwner) {

		AmministratoreForm amministratore = null;
		TRelSoggIst soggIm = attoDao.getTRelSoggIstByIdIstanzaIdOwner(idIstanza, idUffOwner);
		if (soggIm != null && soggIm.getTAmministratori() != null) {
			List<AmministratoreForm> lAmm = beneService
					.getAmministratoreById(soggIm.getTAmministratori().getCodiKIdAmmin());
			if (lAmm != null && lAmm.size() > 0)
				amministratore = lAmm.get(0);
		}

		return amministratore;
	}

	public AmministratoreForm getAmministratoreByImpugnazione(long idProcedimento, long idImpugnazione,
			int idUffOwner) {

		AmministratoreForm amministratore = null;
		TRelSoggImp soggIm = attoDao.getTRelSoggImpByIdImpugnazioneIdOwner(idImpugnazione, idUffOwner);
		if (soggIm != null && soggIm.getTAmministratori() != null) {
			List<AmministratoreForm> lAmm = beneService
					.getAmministratoreById(soggIm.getTAmministratori().getCodiKIdAmmin());
			if (lAmm != null && lAmm.size() > 0)
				amministratore = lAmm.get(0);
		}

		return amministratore;
	}

	public List<SoggettoForm> soggettiTitolariDelProcedimentoCombo(long idProcedimento) {

		List<SoggettoForm> soggettiTitolari = new ArrayList<SoggettoForm>();

		List<SogTitDettaglioProcedimentoDto> soggettiProcedimento = procedimentoDao
				.getSoggettiTit_Procedimento(idProcedimento);
		Iterator<SogTitDettaglioProcedimentoDto> iter = soggettiProcedimento.iterator();
		while (iter.hasNext()) {
			SoggettoForm soggettoForm = new SoggettoForm();
			SogTitDettaglioProcedimentoDto currentDto = iter.next();
			mapperBean.map(currentDto, soggettoForm);
			soggettiTitolari.add(soggettoForm);
		}

		return soggettiTitolari;
	}

	public List<SoggettoForm> soggettiTitolariDelProcedimentoAttoCombo(long idProcedimento, long idAtto) {

		List<SoggettoForm> soggettiTitolari = new ArrayList<SoggettoForm>();

		List<SogTitDettaglioProcedimentoDto> soggettiProcedimento = procedimentoDao
				.getSoggettiTit_ProcedimentoAtto(idProcedimento, idAtto);
		Iterator<SogTitDettaglioProcedimentoDto> iter = soggettiProcedimento.iterator();
		while (iter.hasNext()) {
			SoggettoForm soggettoForm = new SoggettoForm();
			SogTitDettaglioProcedimentoDto currentDto = iter.next();
			mapperBean.map(currentDto, soggettoForm);
			soggettiTitolari.add(soggettoForm);
		}

		return soggettiTitolari;
	}

	public void associaRelSoggettoInterveniente(TRelSoginvSogint rec) {

		procedimentoDao.associaRelSoggettoInterveniente(rec);
	}

	public void eliminaRelSoggettoInterveniente(TRelSoginvSogintId id) {

		procedimentoDao.eliminaRelSoggettoInterveniente(id);
	}

	public void associaRelBeneSoggettoInterveniente(TRelSoginvBene rec) {

		procedimentoDao.associaRelBeneSoggettoInterveniente(rec);
	}

	public void eliminaRelBeneSoggettoInterveniente(final TRelSoginvBeneId id) {

		procedimentoDao.eliminaRelBeneSoggettoInterveniente(id);
	}

	public Map<String, Object> getDettaglioProcedimentoById(long idProcedimento) {
		TProcedimento procedimento = new TProcedimento();
		procedimento.setCodiKIdProc(idProcedimento);
		Map<String, Object> queryResult = procedimentoDao.getDettaglioProcedimento(procedimento);
		return queryResult;
	}

	public String eliminaSogInv(long idProc, int idUffOwn, long idSogInv) {

		return procedimentoDao.eliminaSogInv(idProc, idUffOwn, idSogInv);

	}

	public TTpEsito getTTpEsito(long idEsito) {
		return procedimentoDao.getTTpEsito(idEsito);
	}

	public String intervenientiNonAssociati(Long idProcedimento, int idUfficio) {
		String intervenientiNonAssociati = procedimentoDao.listaIntervenientiNonAssociati(idProcedimento, idUfficio);
		return intervenientiNonAssociati;
	}

	// 20200709 [SG]: nuovo job per allineamento fascicoli PDoc andati in errore col
	// documentaleJob
	// GC 14/07/2020: corretto il metodo passando direttamente l'owner per evitare
	// l'ulteriore chiamata al db
	public Risposta recuperaFascicoloPDoc(int uffOwner, String codiceFascicolo) {

		// int uffOwner = procedimentoDao.getUffOwner(idProcedimento);
		if (logger.isDebugEnabled())
			logger.debug("====>   0.1) Recupero Fascicolo PDoc per IdUff ==> " + uffOwner + " e Fascicolo ==> "
					+ codiceFascicolo);

		return allineamentoFascicoliPDocService.recuperaFascicolo(codiceFascicolo, uffOwner);
	}

}
