package it.giustizia.smp.service.cameralini;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import it.giustizia.smp.dao.CommonDao;
import it.giustizia.smp.dao.SoggettoDao;
import it.giustizia.smp.dao.cameralini.ProcedimentoDaoCam;
import it.giustizia.smp.dto.pg_cameralini.AmmDettaglioProcedimentoDto;
import it.giustizia.smp.dto.pg_cameralini.IntestatarioDto;
import it.giustizia.smp.dto.pg_cameralini.ProcedimentoDto;
import it.giustizia.smp.dto.pg_procedimento.AssociaPersonaDto;
import it.giustizia.smp.dto.pg_procedimento.SogIntDettaglioProcedimentoDto;
import it.giustizia.smp.dto.pg_soggetto.DettaglioPersonaFisicaDto;
import it.giustizia.smp.form.AmministratoreForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.form.cameralini.AggiornaForm;
import it.giustizia.smp.model.TAnUfficio;
import it.giustizia.smp.model.TPersonaFisica;
import it.giustizia.smp.model.TPersonaFisicaId;
import it.giustizia.smp.model.TPersonaGiur;
import it.giustizia.smp.model.TPersonaGiurId;
import it.giustizia.smp.model.TProcedimento;
import it.giustizia.smp.model.TRelProcIntestatari;
import it.giustizia.smp.model.TRelProcSogint;
import it.giustizia.smp.model.TSoggetto;
import it.giustizia.smp.model.TTpStatoProc;
import it.giustizia.smp.model.TTpTipoFascicolo;
import it.giustizia.smp.model.TTpTipoProc;
import it.giustizia.smp.model.TTpTipoSocieta;
import it.giustizia.smp.service.ProcedimentoServiceImpl;
import it.giustizia.smp.utility.Constants;

@Transactional(rollbackFor = Exception.class)
public class ProcedimentoServiceCamImpl extends ProcedimentoServiceImpl
		implements ProcedimentoServiceCam {

	@Autowired
	private ProcedimentoDaoCam procedimentoDaoCam;
	
	@Autowired
	private SoggettoDao soggettoDao;

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private Mapper mapperBean;

	public ProcedimentoDaoCam getProcedimentoDaoCam() {
		return procedimentoDaoCam;
	}

	public void setProcedimentoDaoCam(ProcedimentoDaoCam procedimentoDaoCam) {
		this.procedimentoDaoCam = procedimentoDaoCam;
	}

	public ProcedimentoDto getProcedimentoByKeys(Integer idUffOwn,
			Long numProc, Long annoProc, Long tipoProc) {
		ProcedimentoDto procedimento = procedimentoDaoCam
				.getProcedimentoByKeys(idUffOwn, numProc, annoProc, tipoProc);
		return procedimento;
	}

	public void getProcedimentoByKeys(AggiornaForm form, Integer idUffOwn,
			Long numProc, Short annoProc, Long tipoProc) {
		ProcedimentoDto proc = procedimentoDaoCam.getProcedimentoByKeys(
				idUffOwn, numProc, new Long(annoProc), tipoProc);
		if (proc != null) {
			form.getProcedCamerale().setIdProcedimento(
					proc.getCODI_K_ID_PROC().longValue());
			form.getProcedCamerale().setDataApertura(proc.getDATA_ISCRIZIONE());

			ComboForm cf = new ComboForm();
			cf.setDescrizione(proc.getDESC_STATO_PROC());
			cf.setId(proc.getCODI_FK_ID_STATO_PROC().toString());
			form.getProcedCamerale().setStatoProcedimento(cf);
			form.getProcedCamerale().setAnno(annoProc);
			form.getProcedCamerale().setNumero(numProc);
		}
	}

	public TAnUfficio getCDA(long idUffOwn) {

		return commonDao.getCDA(idUffOwn);
	}

	public List<SoggettoForm> getSoggettiMisurePatrProcedimento(int idUffOwn,
			Long idProc) {

		List<SoggettoForm> lista = new ArrayList<SoggettoForm>();
		TProcedimento id = new TProcedimento();
		id.setCodiKIdProc(idProc);
		List<SogIntDettaglioProcedimentoDto> soggettiProcedimento = procedimentoDaoCam
				.getSoggettiMisurePatrProcedimento(idUffOwn, idProc);
		Iterator<SogIntDettaglioProcedimentoDto> iter = soggettiProcedimento
				.iterator();
		while (iter.hasNext()) {
			SoggettoForm soggettoForm = new SoggettoForm();
			SogIntDettaglioProcedimentoDto currentDto = iter.next();
			mapperBean.map(currentDto, soggettoForm);
			// Art67 e Cauzione sono significativi solo per le misure PERSONALI
			if (soggettoForm.getMisura().getDescrizioneMisura() == null
					|| soggettoForm.getMisura().getDescrizioneMisura()
							.contains("PATRIMONIALE")) {
				soggettoForm.getMisura().setArt67(null);
				soggettoForm.getMisura().setCauzione(null);
			}
		//	if (Constants.SOGGETTO_FISICO
		//			.equals(soggettoForm.getTipoSoggetto())) {
				// Aggiugo solo i soggetti fisici
				lista.add(soggettoForm);
				
		//	}
		}
		return lista;

	}

	public List<SoggettoForm> getSoggettiMisurePersProcedimento(int idUffOwn,
			Long idProc, String tipoMisura) {

		List<SoggettoForm> lista = new ArrayList<SoggettoForm>();
		TProcedimento id = new TProcedimento();
		id.setCodiKIdProc(idProc);
		List<SogIntDettaglioProcedimentoDto> soggettiProcedimento = procedimentoDaoCam
				.getSoggettiMisurePersProcedimento(idUffOwn, idProc, tipoMisura);
		Iterator<SogIntDettaglioProcedimentoDto> iter = soggettiProcedimento
				.iterator();
		while (iter.hasNext()) {
			SoggettoForm soggettoForm = new SoggettoForm();
			SogIntDettaglioProcedimentoDto currentDto = iter.next();
			mapperBean.map(currentDto, soggettoForm);
			// Art67 e Cauzione sono significativi solo per le misure PERSONALI
			if (soggettoForm.getMisura().getDescrizioneMisura() == null
					|| soggettoForm.getMisura().getDescrizioneMisura()
							.contains("PATRIMONIALE")) {
				soggettoForm.getMisura().setArt67(null);
				soggettoForm.getMisura().setCauzione(null);
			}
			if (Constants.SOGGETTO_FISICO
					.equals(soggettoForm.getTipoSoggetto())) {
				// Aggiugo solo i soggetti fisici
				lista.add(soggettoForm);
			}
		}
		return lista;

	}

	public List<AmministratoreForm> getAmmMisurePatrPersProcedimento(
			int idUffOwn, Long idProc) {

		List<AmministratoreForm> lista = new ArrayList<AmministratoreForm>();
		TProcedimento id = new TProcedimento();
		id.setCodiKIdProc(idProc);
		List<AmmDettaglioProcedimentoDto> ammProcedimento = procedimentoDaoCam
				.getAmmMisurePatrPersProcedimento(idUffOwn, idProc);
		Iterator<AmmDettaglioProcedimentoDto> iter = ammProcedimento.iterator();
		while (iter.hasNext()) {
			AmministratoreForm amministratoreForm = new AmministratoreForm();
			AmmDettaglioProcedimentoDto currentDto = iter.next();
			mapperBean.map(currentDto, amministratoreForm);
			// Art67 e Cauzione sono significativi solo per le misure PERSONALI
			// if (amministratoreForm.getMisura().getDescrizioneMisura() == null
			// ||
			// soggettoForm.getMisura().getDescrizioneMisura().contains("PATRIMONIALE"))
			// {
			// amministratoreForm.getMisura().setArt67(null);
			// amministratoreForm.getMisura().setCauzione(null);
			// }
			// if
			// (Constants.SOGGETTO_FISICO.equals(soggettoForm.getTipoSoggetto()))
			// {
			// //Aggiugo solo i soggetti fisici
			// lista.add(soggettoForm);
			// }
			lista.add(amministratoreForm);
		}
		return lista;

	}

	public List<IntestatarioDto> getAmministratoriOmonimi(String cognome,
			String nome, String ragSoc) {

		List<IntestatarioDto> listaOmonimi = procedimentoDaoCam
				.getAmministratoriOmonimi(cognome, nome, ragSoc);
		return listaOmonimi;

	}

	public List<IntestatarioDto> getIntestatariOmonimi(Character tipoSoggetto, String cognome,String nome,Date dataNascita,
											           Long idStatoNasc,Long idComNasc, String comuneNascEst, 
											           String codiceFiscale,String ragioneSoc, String pIvaCompl,
											           Long idStatoSede, Long idComuneSede,String comuneSedeEst) {

		List<IntestatarioDto> listaOmonimi = procedimentoDaoCam.getIntestatariOmonimi(tipoSoggetto,cognome, nome, dataNascita, 
				                   idStatoNasc,idComNasc, comuneNascEst,
				                   codiceFiscale, ragioneSoc, pIvaCompl,
						           idStatoSede, idComuneSede, comuneSedeEst);
		return listaOmonimi;

	}

	public void inserisciTRelProcProc(long idProc1, long idProc2, int idUffOwn,
			String codUtente, long idTipoAtto, long idCatAtto,
			String codiTipoRel, Character flagPadre) {

		procedimentoDaoCam.inserisciTRelProcProc(idProc1, idProc2, idUffOwn,
				codUtente, idTipoAtto, idCatAtto, codiTipoRel, flagPadre);
	}

	public List<AssociaPersonaDto> inserisciProcedimentoCameralini(
			int idUffOwn, String codUtente, ProcedimentoForm procForm,
			long statoProc, List<SoggettoForm> listaSoggettiForm,
			int idTipoFascicolo, Long idTipoAtto, Long idCatAtto,
			String cognomeRichiedente, String nomeRichiedente,
			Date dataEmissione, Date dataDeposito, Long idModalitaTrasmissione,
			Long idProcPadre) {

		TAnUfficio uff = commonDao.getProcuraInUdienzaAssociata(idProcPadre);
		Long pu = null;
		if (uff != null)
			pu = new Long(uff.getCodiKIdUfficio());

		TProcedimento proc = new TProcedimento();
		mapperBean.map(procForm, proc);
		TTpStatoProc tStatoProc = new TTpStatoProc((int) statoProc);
		proc.setTTpStatoProc(tStatoProc);
		List<AssociaPersonaDto> listaSoggetti = new ArrayList<AssociaPersonaDto>();
		Date now = Calendar.getInstance().getTime();

		Long idProc = procedimentoDaoCam.inserisciProcedimentoCameralini(
				idUffOwn, codUtente, proc, null, idTipoFascicolo, idTipoAtto,
				idCatAtto, cognomeRichiedente, nomeRichiedente, dataEmissione,
				dataDeposito, idModalitaTrasmissione, pu);

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
					&& (associaSoggetto.getPersFis().getId()
							.getCodiKIdPersonaFis() > 0)
					&& (associaSoggetto.getPersFis().getDescCognome() != null)) {
				temp = procedimentoDaoCam.associaPersonaFisica(idUffOwn,
						codUtente, idProc, associaSoggetto, now);
			} else if ((associaSoggetto.getPersonaGiur().getId() != null)
					&& (associaSoggetto.getPersonaGiur().getId()
							.getCodiKIdPersGiur() > 0)
					&& (associaSoggetto.getPersonaGiur().getDescRagioneSoc() != null)) {
				temp = procedimentoDaoCam.associaPersonaGiuridica(idUffOwn,
						codUtente, idProc, associaSoggetto, now);
			} else if (associaSoggetto.getPersFis().getDescCognome() != null) {
				temp = procedimentoDaoCam.associaNuovaPersonaFisica(idUffOwn,
						codUtente, idProc, associaSoggetto, now);
			} else if (associaSoggetto.getPersonaGiur().getDescRagioneSoc() != null) {
				temp = procedimentoDaoCam.associaNuovaPersonaGiuridica(
						idUffOwn, codUtente, idProc, associaSoggetto, now);
			}
			associaSoggetto.setIDPERS(temp.getIDPERS());
			associaSoggetto.setIDPROC(temp.getIDPROC());
			associaSoggetto.setIDSOGGINT(temp.getIDSOGGINT());
			associaSoggetto.setIDUFFOWN(temp.getIDUFFOWN());
			associaSoggetto.setIDVER(temp.getIDVER());
			// si inseriscono gli intestatari
			if (associaSoggetto.getPersFis().getDescCognome() != null) {
				TAnUfficio ufficio = new TAnUfficio();
				ufficio.setCodiKIdUfficio(associaSoggetto.getIDUFFOWN()
						.intValue());
				associaSoggetto.getPersFis().setTAnUfficio(ufficio);
				procedimentoDaoCam.inserisciIntestatarioPersFis(
						associaSoggetto.getPersFis(), codUtente);
			} else if (associaSoggetto.getPersonaGiur().getDescRagioneSoc() != null) {
				TAnUfficio ufficio = new TAnUfficio();
				ufficio.setCodiKIdUfficio(associaSoggetto.getIDUFFOWN()
						.intValue());
				associaSoggetto.getPersonaGiur().setTAnUfficio(ufficio);
				procedimentoDaoCam.inserisciIntestatarioPersGiur(
						associaSoggetto.getPersonaGiur(), codUtente);
			}
			listaSoggetti.add(associaSoggetto);
		}
		inserisciTRelProcProc(listaSoggetti.get(0).getIDPROC().longValue(),
				idProcPadre.longValue(), idUffOwn, codUtente, idTipoAtto,
				idCatAtto, null, 'S');
		return listaSoggetti;
	}

	public List<AssociaPersonaDto> inserisciProcedimentoCameraliniNew(
			int idUffOwn, String codUtente, ProcedimentoForm procForm,
			long statoProc, List<SoggettoForm> listaSoggettiForm,
			int idTipoFascicolo, Long idTipoAtto, Long idCatAtto,
			String cognomeRichiedente, String nomeRichiedente,
			Date dataEmissione, Date dataDeposito, Long idModalitaTrasmissione,
			Long idProcPadre, int idTipoProc) {
		
		return inserisciProcedimentoCameraliniNew(idUffOwn, codUtente, procForm, statoProc, listaSoggettiForm, idTipoFascicolo, idTipoAtto, idCatAtto, cognomeRichiedente, nomeRichiedente, dataEmissione, dataDeposito, idModalitaTrasmissione, idProcPadre, idTipoProc, null, null);
	}
	
	public List<AssociaPersonaDto> inserisciProcedimentoCameraliniNew(
			int idUffOwn, String codUtente, ProcedimentoForm procForm,
			long statoProc, List<SoggettoForm> listaSoggettiForm,
			int idTipoFascicolo, Long idTipoAtto, Long idCatAtto,
			String cognomeRichiedente, String nomeRichiedente,
			Date dataEmissione, Date dataDeposito, Long idModalitaTrasmissione,
			Long idProcPadre, int idTipoProc, String tipoSoggetto, Long idVersione) {

		TAnUfficio uff = commonDao.getProcuraInUdienzaAssociata(idProcPadre);
		Long pu = null;
		if (uff != null)
			pu = new Long(uff.getCodiKIdUfficio());

		// soggetti
		Iterator itx = listaSoggettiForm.iterator();
		String listaIdSoggetti = "";
		while (itx.hasNext()) {
			SoggettoForm soggetto = (SoggettoForm) itx.next();
			listaIdSoggetti += "" + soggetto.getIdSoggetto();
			if (itx.hasNext()) {
				listaIdSoggetti += ",";
			}
		}
		Date now = Calendar.getInstance().getTime();

		TProcedimento proc = new TProcedimento();
		mapperBean.map(procForm, proc);

		Long idProc = procedimentoDaoCam.inserisciProcedimentoCameraliniNew(
				idUffOwn, codUtente, proc, null, idTipoFascicolo,
				listaIdSoggetti, idTipoProc, tipoSoggetto, idVersione);
		List<AssociaPersonaDto> listaSoggetti = new ArrayList<AssociaPersonaDto>();
		AssociaPersonaDto dto = new AssociaPersonaDto();
		dto.setIDPROC(new BigDecimal(idProc));
		listaSoggetti.add(dto);
		/*
		 * for (int i = 0; i < listaSoggettiForm.size(); i++) { SoggettoForm
		 * soggettoForm = listaSoggettiForm.get(i); TPersonaFisica persona = new
		 * TPersonaFisica(); TPersonaGiur personaGiur = new TPersonaGiur();
		 * TTpTipoSocieta tipoSoc = new TTpTipoSocieta(); TSoggetto soggInt =
		 * new TSoggetto(); TRelProcSogint relProcSoggInt = new
		 * TRelProcSogint(); mapperBean.map(soggettoForm, persona);
		 * mapperBean.map(soggettoForm, personaGiur);
		 * mapperBean.map(soggettoForm, tipoSoc); mapperBean.map(soggettoForm,
		 * soggInt); mapperBean.map(soggettoForm, relProcSoggInt);
		 * AssociaPersonaDto associaSoggetto = new AssociaPersonaDto();
		 * associaSoggetto.setPersFis(persona);
		 * associaSoggetto.setPersonaGiur(personaGiur);
		 * associaSoggetto.getPersonaGiur().setTTpTipoSocieta(tipoSoc);
		 * associaSoggetto.setSoggInt(soggInt);
		 * associaSoggetto.setRelProcSoggInt(relProcSoggInt); AssociaPersonaDto
		 * temp = null; if ((associaSoggetto.getPersFis().getId() != null) &&
		 * (associaSoggetto.getPersFis().getId().getCodiKIdPersonaFis() > 0) &&
		 * (associaSoggetto.getPersFis().getDescCognome() != null)) { temp =
		 * procedimentoDaoCam.associaPersonaFisica(idUffOwn, codUtente, idProc,
		 * associaSoggetto, now); } else if
		 * ((associaSoggetto.getPersonaGiur().getId() != null) &&
		 * (associaSoggetto.getPersonaGiur().getId().getCodiKIdPersGiur() > 0)
		 * && (associaSoggetto.getPersonaGiur().getDescRagioneSoc() != null)) {
		 * temp = procedimentoDaoCam.associaPersonaGiuridica(idUffOwn,
		 * codUtente, idProc, associaSoggetto, now); } else if
		 * (associaSoggetto.getPersFis().getDescCognome() != null) { temp =
		 * procedimentoDaoCam.associaNuovaPersonaFisica(idUffOwn, codUtente,
		 * idProc, associaSoggetto, now); } else if
		 * (associaSoggetto.getPersonaGiur().getDescRagioneSoc() != null) { temp
		 * = procedimentoDaoCam.associaNuovaPersonaGiuridica(idUffOwn,
		 * codUtente, idProc, associaSoggetto, now); }
		 * associaSoggetto.setIDPERS(temp.getIDPERS());
		 * associaSoggetto.setIDPROC(temp.getIDPROC());
		 * associaSoggetto.setIDSOGGINT(temp.getIDSOGGINT());
		 * associaSoggetto.setIDUFFOWN(temp.getIDUFFOWN());
		 * associaSoggetto.setIDVER(temp.getIDVER()); // si inseriscono gli
		 * intestatari if(associaSoggetto.getPersFis().getDescCognome() !=
		 * null){ TAnUfficio ufficio = new TAnUfficio();
		 * ufficio.setCodiKIdUfficio(associaSoggetto.getIDUFFOWN().intValue());
		 * associaSoggetto.getPersFis().setTAnUfficio(ufficio);
		 * procedimentoDaoCam
		 * .inserisciIntestatarioPersFis(associaSoggetto.getPersFis
		 * (),codUtente); }else if
		 * (associaSoggetto.getPersonaGiur().getDescRagioneSoc() != null){
		 * TAnUfficio ufficio = new TAnUfficio();
		 * ufficio.setCodiKIdUfficio(associaSoggetto.getIDUFFOWN().intValue());
		 * associaSoggetto.getPersonaGiur().setTAnUfficio(ufficio);
		 * procedimentoDaoCam
		 * .inserisciIntestatarioPersGiur(associaSoggetto.getPersonaGiur
		 * (),codUtente); }
		 */

		/*
		 * } inserisciTRelProcProc(listaSoggetti.get(0).getIDPROC().longValue(),
		 * idProcPadre.longValue(),idUffOwn,codUtente,idTipoAtto,idCatAtto,
		 * null,'S');
		 */
		return listaSoggetti;
	}

	public List<AssociaPersonaDto> inserisciProcedimentoCameraliniInt(
			long idProc, int idUffOwn, final int idTipoProc,
			BigDecimal codIntestatario, String codTipoSoggetto, String nome,
			String cognome, String sesso, Date dataNascita,
			String comuneEsteroNasc, String codiceFiscalePF,
			String codiceFiscalePG, String ragioneSociale,
			String comuneEsteroSede, String indirizzoSede,
			String descPartitaIva, int idUffIntest, String descNumeCcia,
			String descCivico, String descCap, Long codTipoStrada,
			Long codComSede, Long codComNasc, Long codStatoSede,
			Long codStatoNasc, Long codTipoSoc, Long codSoggInt, Long idVer,
			String descUtente) {

		Long idProced = procedimentoDaoCam.inserisciProcedimentoCameraliniInt(
				idProc, idUffOwn, idTipoProc, codIntestatario, codTipoSoggetto,
				nome, cognome, sesso, dataNascita, comuneEsteroNasc,
				codiceFiscalePF, codiceFiscalePG, ragioneSociale,
				comuneEsteroSede, indirizzoSede, descPartitaIva, idUffIntest,
				descNumeCcia, descCivico, descCap, codTipoStrada, codComSede,
				codComNasc, codStatoSede, codStatoNasc, codTipoSoc, codSoggInt,
				idVer, descUtente);
		List<AssociaPersonaDto> lista = new ArrayList<AssociaPersonaDto>();
		AssociaPersonaDto dto = new AssociaPersonaDto();
		dto.setIDPROC(new BigDecimal(idProced));
		lista.add(dto);

		return lista;
	}
	
	public BigDecimal updateIntestatario(
			long idProc, int idUffOwn, final int idTipoProc,
			BigDecimal codIntestatario, String codTipoSoggetto, String nome,
			String cognome, String sesso, Date dataNascita,
			String comuneEsteroNasc, String codiceFiscalePF,
			String codiceFiscalePG, String ragioneSociale,
			String comuneEsteroSede, String indirizzoSede,
			String descPartitaIva, int idUffIntest, String descNumeCcia,
			String descCivico, String descCap, Long codTipoStrada,
			Long codComSede, Long codComNasc, Long codStatoSede,
			Long codStatoNasc, Long codTipoSoc, Long codSoggInt, Long idVer,
			String descUtente) {

				BigDecimal idProced = procedimentoDaoCam.updateIntestatario(
				idProc, idUffOwn, idTipoProc, codIntestatario, codTipoSoggetto,
				nome, cognome, sesso, dataNascita, comuneEsteroNasc,
				codiceFiscalePF, codiceFiscalePG, ragioneSociale,
				comuneEsteroSede, indirizzoSede, descPartitaIva, idUffIntest,
				descNumeCcia, descCivico, descCap, codTipoStrada, codComSede,
				codComNasc, codStatoSede, codStatoNasc, codTipoSoc, codSoggInt,
				idVer, descUtente);
		
		return idProced;
		
	}
	
	
	/**
	 * getIntestatarioProc
	 * @param idUffOwn
	 * @param idProc
	 * @return SoggettoForm
	 */
	public SoggettoForm getIntestatarioProc(int idUffOwn, Long idProc) {
	
		List<IntestatarioDto> lista = new ArrayList<IntestatarioDto>();
		lista = procedimentoDaoCam.getIntestatari(idUffOwn, idProc);
		SoggettoForm soggettoReturn = new SoggettoForm(); 
	       if (lista != null && !lista.isEmpty())  {
	    	   IntestatarioDto intestatario = new IntestatarioDto();
	    	   intestatario = (IntestatarioDto)lista.get(0);
	    	   mapperBean.map(intestatario, soggettoReturn);
	       }
	       return soggettoReturn;		
	}
	
	

	public List<IntestatarioDto> getIntestatari(int idUffOwn, Long idProc) {
		List<IntestatarioDto> lista = new ArrayList<IntestatarioDto>();
		lista = procedimentoDaoCam.getIntestatari(idUffOwn, idProc);
		return lista;
	}

	public ProcedimentoForm getDettaglioProcedimentoCam(long idProcFiglio) {

		TProcedimento procedimento = new TProcedimento();
		ProcedimentoForm procForm = new ProcedimentoForm();
		procedimento.setCodiKIdProc(idProcFiglio);
		Map<String, Object> queryResult = procedimentoDaoCam.getDettaglioProcedimentoCam(procedimento);

		TProcedimento proc = (TProcedimento) queryResult.get("proced");
		TTpStatoProc statoProc = (TTpStatoProc) queryResult.get("statoProc");
		TTpTipoProc tipoProc = (TTpTipoProc) queryResult.get("tipoProc");
		TTpTipoFascicolo tipoFasc = (TTpTipoFascicolo) queryResult.get("tipoFasc");

		if (proc != null) {
			mapperBean.map(proc, procForm);
		}
		if (statoProc != null) {
			mapperBean.map(statoProc, procForm);
		}
		if (tipoProc != null) {
			mapperBean.map(tipoProc, procForm);
			if (tipoProc.getCodiKIdTipoProc() == Integer
					.parseInt(Constants.TIPOPROC_AGGRAVAMENTO_PERSONALE)) {
				Long idPadre = getIdPadreAggravamento(idProcFiglio);
				procForm.setIdPadre(idPadre);
			}
		}
		if (tipoFasc != null) {
			mapperBean.map(tipoFasc, procForm);
		}
		return procForm;
	}

	public List<Long> getIdComuneNascita(String descComune) {
		List<Long> lista = procedimentoDaoCam.getIdComuneNascita(descComune);
		return lista;
	}

	public IntestatarioDto getIntestatarioById(Long idIntest, Integer idUfficio) 
	{
		IntestatarioDto intestatario = procedimentoDaoCam.getIntestatarioById(idIntest, idUfficio);
		
		return intestatario;
	}
	
	
	public SoggettoForm getSoggettoDaCorregere(long idSoggetto, long idVersione, int idUffOwner)
    {
	  SoggettoForm soggettoReturn = new SoggettoForm(); 
	  
      TPersonaFisicaId idPF = new TPersonaFisicaId();
      idPF.setCodiKIdUffOwner(idUffOwner);
      idPF.setCodiKIdPersonaFis(idSoggetto);
      idPF.setCodiKIdVer(Short.parseShort(""+idVersione));
      DettaglioPersonaFisicaDto personaFisicaDto = soggettoDao.getDettaglioPersonaFisicaVer(idPF);
       if (personaFisicaDto != null)  {
    	   mapperBean.map(personaFisicaDto, soggettoReturn);
       }else { 
          TPersonaGiurId idPG = new TPersonaGiurId();
          idPG.setCodiKIdUffOwner(idUffOwner);
          idPG.setCodiKIdPersGiur(idSoggetto);
          idPG.setCodiKIdVer(Short.parseShort(""+idVersione));
          Map<String, Object> risultato = soggettoDao.getDettaglioPersonaGiuVer(idPG);
          TPersonaGiur personaG = (TPersonaGiur) risultato.get("p_giur");
          mapperBean.map(personaG, soggettoReturn);
         }
       return soggettoReturn;
    }

	public TRelProcIntestatari getFirstTRelProcIntestatari(int idUffOwn, Long idProcedimento) {
		
		return procedimentoDaoCam.getFirstTRelProcIntestatari(idUffOwn,idProcedimento);
	}
	
	public String[] getEsitoDecisione(long idProcedimento, Long idAtto, int idUfficioUtente, String codiceUtente){
		String[] decisioni = new String[2];
		Map<String, Object> queryResult = procedimentoDaoCam.getEsitoDecisione(idProcedimento, idAtto, idUfficioUtente, codiceUtente);
		decisioni[0] = (String) queryResult.get("DESC_ESITO1");
		decisioni[1] = (String) queryResult.get("DESC_ESITO2");
		return decisioni;
	}
	//ginesti luglio 2017
	public ProcedimentoDto getProcedimentoByKeysProc(Integer idUffOwn,
			Long numProc, Long annoProc, Long tipoProc) {
		ProcedimentoDto procedimento = procedimentoDaoCam
				.getProcedimentoByKeysProc(idUffOwn, numProc, annoProc, tipoProc);
		return procedimento;
	}
	
}
