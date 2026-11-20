package it.giustizia.smp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import it.giustizia.smp.dao.BeneDao;
import it.giustizia.smp.dao.CommonDao;
import it.giustizia.smp.dto.BeneTitoloDirittoDto;
import it.giustizia.smp.dto.KeyMessageDto;
import it.giustizia.smp.dto.SchedaAttiAmmDto;
import it.giustizia.smp.dto.pg_procedimento.SogIntDettaglioProcedimentoDto;
import it.giustizia.smp.form.AmministratoreForm;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.BeneForm;
import it.giustizia.smp.form.BeneSoggettoForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.IndirizzoForm;
import it.giustizia.smp.form.MisuraForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.model.TAmministratori;
import it.giustizia.smp.model.TAnAltriOper;
import it.giustizia.smp.model.TAnComuni;
import it.giustizia.smp.model.TAnNormative;
import it.giustizia.smp.model.TAnOperFinanz;
import it.giustizia.smp.model.TAnStato;
import it.giustizia.smp.model.TAnimali;
import it.giustizia.smp.model.TAtto;
import it.giustizia.smp.model.TAzienda;
import it.giustizia.smp.model.TBene;
import it.giustizia.smp.model.TBeneFin;
import it.giustizia.smp.model.TBeneFinId;
import it.giustizia.smp.model.TBeneImmat;
import it.giustizia.smp.model.TBeneImmobile;
import it.giustizia.smp.model.TCollezioni;
import it.giustizia.smp.model.TContante;
import it.giustizia.smp.model.TDenaro;
import it.giustizia.smp.model.TDepositiAmm;
import it.giustizia.smp.model.TIstanza;
import it.giustizia.smp.model.TRelAttoIstGen;
import it.giustizia.smp.model.TRelAttoIstGenId;
import it.giustizia.smp.model.TRelAttoMisPatr;
import it.giustizia.smp.model.TRelBeneAtto;
import it.giustizia.smp.model.TRelBeneAttoId;
import it.giustizia.smp.model.TRelBeneBene;
import it.giustizia.smp.model.TRelBeneProcDisp;
import it.giustizia.smp.model.TRelImmProcVinc;
import it.giustizia.smp.model.TRelProcAttoAmm;
import it.giustizia.smp.model.TRelProcBene;
import it.giustizia.smp.model.TRelProcBeneAttoAmm;
import it.giustizia.smp.model.TRelProcBeneTit;
import it.giustizia.smp.model.TTpCatCatasto;
import it.giustizia.smp.model.TTpEsito;
import it.giustizia.smp.model.TTpTipoInteresse;
import it.giustizia.smp.model.TTpTipoMisura;
import it.giustizia.smp.model.TTpTipoPrevenzione;
import it.giustizia.smp.model.TVeicolo;
import it.giustizia.smp.service.webservices.BDCService;
import it.giustizia.smp.utility.ComparatorUtil;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.SottoCategoriaUtils;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.validation.BeneValidation;

/**
 * BeneServiceImpl
 * 
 * @author Almaviva S.p.A. classe del Bene
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Transactional(rollbackFor = Exception.class)
public class BeneServiceImpl implements BeneService {
	
	private Logger logger = Logger.getLogger(getClass());

	private BeneDao beneDao;
	@Autowired
	private Mapper mapperBean;
	@Autowired
	private ProcedimentoService procedimentoService;
	@Autowired
	private BDCService bdcService;
	@Autowired
	private CommonDao commonDao;
	@Autowired
	private CommonService	commonService;

	public void setBeneValidation(BeneValidation beneValidation) {
	}

	public void setMisuraService(MisuraService misuraService) {
	}

	public void associaBeneAlProcedimento(int idUffOwner, String codUtente, long idProcedimento, BeneForm beneForm) {
		// determina se il bene che si sta associando proviene da BDC
		if (beneForm.isBeneFromBDC()) {
			// inserisci il bene in base dati e associalo al procedimento
			// corrente
			BeneForm beneFormBDC = bdcService.dettaglioBeneBDC(beneForm);
			beneFormBDC.setStatoConservazione(beneForm.getStatoConservazione());
			this.inserisciBene(idUffOwner, codUtente, idProcedimento, beneFormBDC);

			return;
		}
		long statoBene = 0;
		long idBene = 0;
		if (beneForm != null) {
			if ((beneForm.getStatoConservazione() != null) && (beneForm.getStatoConservazione().getId() != null)) {
				statoBene = Long.parseLong(beneForm.getStatoConservazione().getId());
			}
			if (beneForm.getIdBene() != null) {
				idBene = beneForm.getIdBene();
			}
		}
		beneDao.associaBeneAlProcedimento(idBene, idUffOwner, codUtente, idProcedimento, statoBene);
	}

	public KeyMessageDto associaTitoloDiDirittoAlBene(String tipoFasciciolo,BeneSoggettoForm beneSoggettoForm, long idProcedimento, Integer idUfficioUtente, long idAtto, String codUtente, 
			Boolean daProposta, String tipoOperazione, String nomeMetodo,String catAtto) {
		
		if (idAtto == 0l) {
			idAtto = getIdAttoByUfficioEProcedimento(idUfficioUtente, idProcedimento);
		}
		//gennaro 7/02/2014
		//Controllo che la procedura di Controllo_Quota dia esito positivo altrimenti interrompo l'operazione e mostro l'errore all'utente a video
		 KeyMessageDto messaggioRitorno = null;
		 
		 //se è decreto decisorio di I grado o II grado non faccio il controllo della quota da pl/sql
		 if ((catAtto!=null) && (!catAtto.equals(Constants.DECRETO_DECISORIO_I_GRADO)) && (!catAtto.equals(Constants.DECRETO_DECISORIO_II_GRADO))
				 && (!catAtto.equals(Constants.DECRETO_DECISORIO_I_GRADO_CAMERE))&& (!catAtto.equals(Constants.DECRETO_DECISORIO_II_GRADO_CAM))
				 && (!catAtto.equals(Constants.DECRETO_DECISORIO_PROVV_ISTANZA)))   
		 messaggioRitorno=beneDao.controlloQuota(beneSoggettoForm, idProcedimento, idAtto, idUfficioUtente, codUtente, daProposta, tipoOperazione, nomeMetodo);
		
		 else messaggioRitorno=new KeyMessageDto();
		//fine gennaro 7/02/2014
		 if (!messaggioRitorno.isError()){
			beneDao.associaTitoloDiDirittoAlBene(beneSoggettoForm, idProcedimento, idAtto, idUfficioUtente, codUtente, daProposta,nomeMetodo);
			if("1".equalsIgnoreCase(tipoFasciciolo)){
			    procedimentoService.aggiornaTipoProcPrevProc(idProcedimento, idUfficioUtente, codUtente);
			}
		}else{
			return messaggioRitorno;
		}
		return messaggioRitorno;
	}

	/**
	 * beneById
	 * 
	 * @param idUffOwn
	 * @param idBene
	 * @return BeneForm
	 */
	public BeneForm beneById(int idUffOwn, long idBene, long idProcedimento) {

		BeneForm form = new BeneForm();
		TBene bene = beneDao.getTBeneById(idBene, idUffOwn);
		mapperBean.map(bene, form);
		if ((form.getTipologia() != null) || (form.getTipologia().getId() != null)) {
			caricaDettaglioPerTipologia(form, idBene, idUffOwn, idProcedimento);
		}

		if (StringUtils.isNotEmpty(form.getCodiceCatastale1())) {
			if (form.getCodiceCatastale1().length() == 5) {
				String tmp = form.getCodiceCatastale1();
				form.setCodiceCatastale1(tmp.substring(0, 4));
				form.setCodiceCatastale2(tmp.substring(4));
			}
		}

		if (StringUtils.isNotEmpty(form.getCodiceCatastale1P())) {
			if (form.getCodiceCatastale1P().length() == 5) {
				String tmp = form.getCodiceCatastale1P();
				form.setCodiceCatastale1P(tmp.substring(0, 4));
				form.setCodiceCatastale2P(tmp.substring(4));
			}
		}

		if ("4".equals(form.getTipologia().getId())) {
			if (form.getSottocategoria() != null && SottoCategoriaUtils.isPolizza(form.getSottocategoria().getId())) {
				// carico i dati della polizza
				TBeneFin beneFin = commonDao.getEntity(TBeneFin.class, new TBeneFinId(idBene, idUffOwn));
//				TAnAltriOper tAnAltriOper = getTAnAltriOperByCodFiscale(beneFin.getDescIban());
				TAnAltriOper tAnAltriOper = getTAnAltriOperByDescrizione(form.getDescOperFinanz().getDescrizione());
				if(tAnAltriOper != null){
					form.setProvinciaEntePolizza(tAnAltriOper.getDescSiglaProv());
					form.setIndirizzoBeneFin(tAnAltriOper.getDescIndirizzo());
					form.setDescOperFinanz(new ComboForm(tAnAltriOper.getCodiKIdOperatore() + "", tAnAltriOper.getDescOperatore()));
				}
				form.setNumeroPolizza(beneFin.getDescRegistrazione());
	
			}
		}
		return form;
	}

	/**
	 * beniProcCriteri
	 * 
	 * @param idUffOwn
	 * @param idprocedimento
	 * @param bene
	 * @return List<BeneForm>
	 */
	public List<BeneForm> beniRicercaCriteri(long idUffOwn, long idprocedimento, BeneForm beneForm) {

		List queryResults = null;
//		try {
			TAzienda azienda = new TAzienda();
			TBeneImmobile immobile = new TBeneImmobile();
			TVeicolo veicolo = new TVeicolo();
			TBeneImmat immateriale = new TBeneImmat();
			TBeneFin finanziario = new TBeneFin();
			TContante contante = new TContante();
			TDenaro denaro = new TDenaro();
			TBene bene = new TBene();
			long tipologia = 0;
			long categoria = 0;
			long sottoCategoria = 0;
			Date dataMin = null;
			Date dataMax = null;
			if (beneForm != null) {
				mapperBean.map(beneForm, bene);
				if ((beneForm.getCategoria() != null) && (beneForm.getCategoria().getId() != null)) {
					categoria = Long.parseLong(beneForm.getCategoria().getId());
				}
				if ((beneForm.getSottocategoria() != null) && (beneForm.getSottocategoria().getId() != null)) {
					sottoCategoria = Long.parseLong(beneForm.getSottocategoria().getId());
				}
				if (beneForm.getTipologia() != null) {
					if (beneForm.getTipologia().getId() != null) {
						tipologia = Long.parseLong(beneForm.getTipologia().getId());
						if ("1".equalsIgnoreCase(beneForm.getTipologia().getId())) {
							mapperBean.map(beneForm, immobile);
							// else if
							// ("2".equalsIgnoreCase(beneForm.getTipologia().getId()))
							// --il mapping è stato spostato fuori,
							// mapperBean.map(beneForm, bene); --perchè vale per
							// tutti i beni
						} else if ("2".equalsIgnoreCase(beneForm.getTipologia().getId())) {
							if (categoria == 6 && sottoCategoria == 35) { // Denaro
																			// -
																			// Contante
								mapperBean.map(beneForm, contante);
							}
							if (categoria == 6 && sottoCategoria != 35) {
								mapperBean.map(beneForm, denaro);
							}
						} else if ("3".equalsIgnoreCase(beneForm.getTipologia().getId())) {
							if ("10".equalsIgnoreCase(beneForm.getCategoria().getId())) {
								mapperBean.map(beneForm, veicolo);
							}
							if ("11".equalsIgnoreCase(beneForm.getCategoria().getId())) {
								mapperBean.map(beneForm, immateriale);
							}
						} else if ("5".equalsIgnoreCase(beneForm.getTipologia().getId())) {
							mapperBean.map(beneForm, azienda);
						} else if ("4".equalsIgnoreCase(beneForm.getTipologia().getId())) {
							mapperBean.map(beneForm, finanziario);
							finanziario.setDescEnteCreditizio(beneForm.getDescrizioneEnte());
						}
					}
				}
				if (beneForm.getDataRegistrazioneMin() != null) {
					dataMin = beneForm.getDataRegistrazioneMin();
				}
				if (beneForm.getDataRegistrazioneMax() != null) {
					dataMax = beneForm.getDataRegistrazioneMax();
				}
			}
			queryResults = beneDao.beniRicercaCriteri(idUffOwn, idprocedimento, tipologia, categoria, sottoCategoria, bene, azienda, immobile, immateriale, veicolo, finanziario, contante, denaro, dataMin,
					dataMax);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		List<BeneForm> risultati = new ArrayList<BeneForm>();
		if ((queryResults == null) || queryResults.isEmpty()) {
			return risultati;
		}
		Iterator iterator = queryResults.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			TBene beneRis = (TBene) row.get("bene");
			BeneForm form = new BeneForm();
			if (beneRis != null) {
				mapperBean.map(beneRis, form);
			}
			risultati.add(form);
		}
		return risultati;
	}

	public BeneForm caricaInfoOperFinanz(String codiceAbi, String codiceCab) {

		BeneForm beneForm = new BeneForm();
		List<TAnOperFinanz> opFin = beneDao.getOperFinanz(codiceAbi, codiceCab);
		if (opFin != null && opFin.size() > 0) {
			TAnOperFinanz operFinanz = opFin.get(0);
			mapperBean.map(operFinanz, beneForm);
		}
		return beneForm;
	}

	public boolean controllaPresenzaSoggettoInMisuraPatrimoniale(long idPersona, long idProcedimento, Integer idUfficioUtente, long idBene, Integer idTipoInteresse) {

		long idAtto = getIdAttoByUfficioEProcedimento(idUfficioUtente, idProcedimento);
		List<TRelAttoMisPatr> relazioni = beneDao.listaRelAttoMisPatr(idPersona, idProcedimento, idAtto, idUfficioUtente, idBene, idTipoInteresse);
		return ((relazioni != null) && !relazioni.isEmpty());
	}

	/**
	 * copiaTitoloDiDiritto
	 * 
	 * @param beneForm
	 * @param idProcedimento
	 * @param idUfficioUtente
	 * @param idAtto
	 * @param idCodiceUtente
	 */
	public void copiaTitoloDiDiritto(BeneForm beneForm, long idProcedimento, Integer idUfficioUtente, long idAtto, String idCodiceUtente, String nomeMetodo) {

		Iterator itx = beneForm.getListaIdCheckbox().iterator();
		String listaIdBeneDaInserire = "";
		while (itx.hasNext()) {
			listaIdBeneDaInserire += "" + itx.next();
			if (itx.hasNext()) {
				listaIdBeneDaInserire += ";";
			}
		}
		beneDao.copiaTitoloDiDiritto(beneForm.getIdBene(), idProcedimento, idUfficioUtente, listaIdBeneDaInserire, idAtto, idCodiceUtente, nomeMetodo);
	}

	public String getDescEnte(String codiceAbi) {

		String descEnte = null;
		List<Map> lista = this.beneDao.getDescEnte(codiceAbi);
		if (lista.size() > 0)
			descEnte = (String) lista.get(0).get("desc_banca");
		return descEnte;
	}

	public void eliminaSoggettoComeTitoloDiInteresse(long idPersona, long idProcedimento, Integer idUfficioUtente, long idBene, int idTipoInteresse, long idAtto, String codUtente) {

		if (idAtto == 0l) {
			idAtto = getIdAttoByUfficioEProcedimento(idUfficioUtente, idProcedimento);
		}
		beneDao.eliminaSoggettoComeTitoloDiInteresse(idPersona, idAtto, idProcedimento, idUfficioUtente, idBene, idTipoInteresse);
		procedimentoService.aggiornaTipoProcPrevProc(idProcedimento, idUfficioUtente, codUtente);
	}

	public boolean esistonoIdentici(BeneForm beneForm) {

		if (beneForm.getTipologia().getId().equals("1")) {
			return beneDao.esistonoImmobiliIdentici(beneForm);
		}
		if (beneForm.getTipologia().getId().equals("2")) {
			if ((beneForm.getCategoria() != null) && (beneForm.getCategoria().getId() != null) && beneForm.getCategoria().getId().equals("8")) {
				return beneDao.esistonoAnimaliIdentici(beneForm);
			}
		}
		if (beneForm.getTipologia().getId().equals("3")) {
			if ((beneForm.getCategoria() != null) && (beneForm.getCategoria().getId() != null) && beneForm.getCategoria().getId().equals("10")) {
				return beneDao.esistonoVeicoliIdentici(beneForm);
			}
		}
		if (beneForm.getTipologia().getId().equals("4")) {
			return beneDao.esistonoFinanziariIdentici(beneForm);
		}
		if (beneForm.getTipologia().getId().equals("5")) {
			return beneDao.esistonoAziendeIdentiche(beneForm);
		}
		return true;
	}

	/**
	 * frazionamentoBene
	 * 
	 * @param beneForm
	 * @param idProcedimento
	 * @param idUfficioUtente
	 * @param idAtto
	 * @param idCodiceUtente
	 */
	public void frazionamentoBene(BeneForm beneForm, long idProcedimento, Integer idUfficioUtente, long idAtto, String idCodiceUtente) {

		Iterator itx = beneForm.getListaIdCheckbox().iterator();
		String listaIdBeni = "";
		while (itx.hasNext()) {
			listaIdBeni += "" + itx.next();
			if (itx.hasNext()) {
				listaIdBeni += ";";
			}
		}
		beneDao.frazionamentoBene(idUfficioUtente, idCodiceUtente, idProcedimento, beneForm.getIdBene(), listaIdBeni, idAtto);
	}

	public BeneSoggettoForm getBeneSoggettoFormConInformazioniTitoliDiDiritto(BeneSoggettoForm form, Long idBene, long idPersona, Integer idUfficioUtente, long idProcedimento, long idAtto,
			long idTipoInteresse) {

		BeneSoggettoForm risultato = new BeneSoggettoForm();
		risultato.setBene(form.getBene());
		TRelBeneAtto relazione = beneDao.getTRelBeneAttoById(idBene, idPersona, idUfficioUtente, idProcedimento, idAtto, idTipoInteresse);
		risultato.setIdTipoInteresse((int) idTipoInteresse);
		TTpTipoInteresse tipoInteresse = beneDao.getInteresseById(idTipoInteresse);
		risultato.setDescrizioneTipoInteresse(tipoInteresse.getDescTipoInt());
		if ((relazione.getNumeFrazioneNum() != null) && (relazione.getNumeFrazioneDen() != null) && (form.getQuotaInteresse() == null)) {
			risultato.setNumeratoreFrazioneQuota(Integer.valueOf(relazione.getNumeFrazioneNum().intValue()));
			risultato.setDenominatoreFrazioneQuota(Integer.valueOf(relazione.getNumeFrazioneDen().intValue()));
		} else if (relazione.getNumeQuota()!=null){
			risultato.setQuotaInteresse(relazione.getNumeQuota().doubleValue());
		}
		SoggettoForm soggetto = procedimentoService.getPersonaById(idPersona, idUfficioUtente);
		MisuraForm misura = new MisuraForm();
		if ((relazione.getTTpTipoMisura()!= null) && (relazione.getTTpTipoMisura().getCodiKIdTipoMisura() > 0)) {
			ComboForm tipoMisura = getCBTipoMisuraById(relazione.getTTpTipoMisura().getCodiKIdTipoMisura());
			misura.setTipoMisura(tipoMisura);
		}
		soggetto.setMisura(misura);
		if (relazione.getTAnNormative()!= null) {
		if (relazione.getTAnNormative().getTTpTipoPrevenzione() != null) {
			ComboForm tipologiaPrevenzione = getCBTipologiaPrevenzioneById(relazione.getTAnNormative().getTTpTipoPrevenzione().getCodiKIdTipoPrev());
			soggetto.setTipologiaPrevenzione(tipologiaPrevenzione);
		}
		if  (relazione.getTAnNormative().getCodiKIdNormativa() > 0) {
			ComboForm normativa = getCBNormativaById(relazione.getTAnNormative().getCodiKIdNormativa());
			soggetto.setNormativa(normativa);
		}
		}
		SoggettoForm soggettoVecchio = form.getSoggetto();
		if (soggettoVecchio != null) {
			reimpostaIValoriDelSoggettoVecchio(soggetto, soggettoVecchio);
		}
		risultato.setSoggetto(soggetto);
		return risultato;
	}

	public long getIdAttoByUfficioEProcedimento(Integer idUfficioUtente, long idProcedimento) {

		Long idAtto = beneDao.getIdAttoByUfficioEProcedimento(idUfficioUtente, idProcedimento);
		if(idAtto!=null) return idAtto;
		else return 0;
	}
	
	public long getIdAttoByUfficioEProcedimentoCameralini(Integer idUfficioUtente, long idProcedimento) {

		Long idAtto = beneDao.getIdAttoByUfficioEProcedimentoCameralini(idUfficioUtente, idProcedimento);
		if(idAtto!=null) return idAtto;
		else return 0;
	}

	public TRelBeneAtto getRelazioneBeneProcedimentoById(BeneSoggettoForm beneSoggettoForm, long idProcedimento, Integer idUfficioUtente, long idAtto) {

		return beneDao.getRelazioneBeneProcedimentoById(beneSoggettoForm, idProcedimento, idUfficioUtente, idAtto);
	}

	public List<TRelBeneAtto> getRelazioniByTipoInteresse(BeneSoggettoForm beneSoggettoForm, long idProcedimento, Integer idUfficioUtente, long idAtto) {

		return beneDao.getRelazioniByTipoInteresse(beneSoggettoForm, idProcedimento, idUfficioUtente, idAtto);
	}
	
	public List<TRelProcBeneTit> getRelazioniProcedimentoBeneTitolare(BeneSoggettoForm beneSoggettoForm, long idProcedimento, Integer idUfficioUtente) {

		return beneDao.getRelazioniProcedimentoBeneTitolare(beneSoggettoForm, idProcedimento, idUfficioUtente);
	}

	public int getTipoInteressePerSoggettoInteressato(long idProcedimento, long idAtto, int idUfficioUtente, long idBene, long idSoggettoTitolare) {

		TRelBeneAtto relazione = beneDao.getTipoInteressePerSoggettoInteressato(idProcedimento, idAtto, idUfficioUtente, idBene, idSoggettoTitolare);
		return relazione.getId().getCodiKIdTipoInt();
	}

	/**
	 * inserisciBene
	 * 
	 * @param idUffOwn
	 * @param codUtente
	 * @param idprocedimento
	 * @param beneForm
	 */
	
	public long inserisciBene(long idUffOwn, String codUtente, long idprocedimento, BeneForm beneForm) {

		long idBene = 0;
//		try {
			TAzienda azienda = new TAzienda();
			TBene bene = new TBene();
			TAnimali animali = new TAnimali();
			TCollezioni collezioni = new TCollezioni();
			TVeicolo veicolo = new TVeicolo();
			TBeneImmat immateriale = new TBeneImmat();
			TBeneImmobile immobile = new TBeneImmobile();
			TBeneFin finanziario = new TBeneFin();
			TDenaro denaro = new TDenaro();
			TContante contante = new TContante();
			long statoBene = 0;
			String descComune = null;
			String desctipoStrada = null;
			String desccategoria = null;
			String descSottoCat = null;
			long idDisponibilita = 0;
			long idVincolo = 0;
			String descVincolo = null;
			if (beneForm != null) {
				if ((beneForm.getStatoConservazione() != null) && (beneForm.getStatoConservazione().getId() != null)) {
					statoBene = Long.parseLong(beneForm.getStatoConservazione().getId());
				}
				if ((beneForm.getCategoria() != null) && (beneForm.getCategoria().getDescrizione() != null)) {
					desccategoria = beneForm.getCategoria().getDescrizione();
				}
				if ((beneForm.getSottocategoria() != null) && (beneForm.getSottocategoria().getDescrizione() != null)) {
					descSottoCat = beneForm.getSottocategoria().getDescrizione();
				}
				mapperBean.map(beneForm, bene);
				if (beneForm.getTipologia() != null) {
					if ((beneForm.getIndirizzo() != null) && (beneForm.getIndirizzo().getToponimo() != null) && (beneForm.getIndirizzo().getToponimo().getDescrizione() != null)) {
						desctipoStrada = beneForm.getIndirizzo().getToponimo().getDescrizione();
					}
					if ((beneForm.getComune() != null) && (beneForm.getComune().getDescrizione() != null)) {
						descComune = beneForm.getComune().getDescrizione();
					}
					if (beneForm.getTipologia().getId() != null) {
						if ("1".equalsIgnoreCase(beneForm.getTipologia().getId())) {
							mapperBean.map(beneForm, immobile);
							if ((beneForm.getVincolo() != null) && (beneForm.getVincolo().getId() != null)) {
								idVincolo = Long.parseLong(beneForm.getVincolo().getId());
							}
							if ((beneForm.getComboDisponibilita() != null) && (beneForm.getComboDisponibilita().getId() != null)) {
								idDisponibilita = Long.parseLong(beneForm.getComboDisponibilita().getId());
							}
							if ((beneForm.getCodiceCatastale2() != null) && (immobile.getDescCatasto() != null)) {
								immobile.setDescCatasto(immobile.getDescCatasto() + beneForm.getCodiceCatastale2());
							}
							if ((beneForm.getCodiceCatastale2P() != null) && (immobile.getDescCatastoP() != null)) {
								immobile.setDescCatastoP(immobile.getDescCatastoP() + beneForm.getCodiceCatastale2P());
							}
							if ("I".equalsIgnoreCase(beneForm.getSwitchDenunciaIdentifImmobile())) {
								immobile.setTTpTipoDenunciaByCodiFkIdTipoDenuncia(null);
								immobile.setNumeDenuncia1(null);
								immobile.setNumeDenuncia2(null);
								immobile.setNumeAnnoDenuncia(null);
							} else if ("D".equalsIgnoreCase(beneForm.getSwitchDenunciaIdentifImmobile())) {
								immobile.setDescSezione(null);
								immobile.setDescFoglio(null);
								immobile.setDescParticella1(null);
								immobile.setDescParticella2(null);
								immobile.setDescSubParticella1(null);
								immobile.setDescSubParticella2(null);
							}
							if ("I".equalsIgnoreCase(beneForm.getSwitchDenunciaIdentifImmobileP())) {
								immobile.setTTpTipoDenunciaByCodiFkIdTipoDenunciaP(null);
								immobile.setNumeDenuncia1P(null);
								immobile.setNumeDenuncia2P(null);
								immobile.setNumeAnnoDenunciaP(null);
							} else if ("D".equalsIgnoreCase(beneForm.getSwitchDenunciaIdentifImmobileP())) {
								immobile.setDescSezioneP(null);
								immobile.setDescFoglioP(null);
								immobile.setDescParticella1P(null);
								immobile.setDescParticella2P(null);
								immobile.setDescSubalterno1P(null);
								immobile.setDescSubalterno2P(null);
							}
							descVincolo = beneForm.getDescrizioneVincolo();

							// Giacomo 18/07/2013 Aggiunto il tipo occupante
							long vtipoOccupanti = 0;
							if (beneForm.getTipoOccupanti() != null) {
								vtipoOccupanti = NumberUtils.toLong(beneForm.getTipoOccupanti().getId(), 0);
							}

							List<Map> insImmobile = beneDao.inserisciImmobile(idUffOwn, codUtente, idprocedimento, statoBene, bene, immobile, idDisponibilita, idVincolo, descVincolo, vtipoOccupanti);
							if (insImmobile != null && insImmobile.size() > 0)
								idBene = (Long) insImmobile.get(0).get("IDBENE");
						}
						if ("2".equalsIgnoreCase(beneForm.getTipologia().getId())) { // TIPOLOGIA MOBILE
							if(beneForm.getCategoria() != null){
								if(ComparatorUtil.containsValue(beneForm.getCategoria().getId(), Constants.CategoriaBeni.ID_TIPO_DENARO_CONTANTE_S)){
									if (beneForm.getSottocategoria() != null) {
										if ("35".equalsIgnoreCase(beneForm.getSottocategoria().getId()) || "206".equalsIgnoreCase(beneForm.getSottocategoria().getId())){
											mapperBean.map(beneForm, contante);
										}else{
											mapperBean.map(beneForm, denaro);
										}
									}
									
									List<Map> insDenaro_Contante = beneDao.inserisciDenaro_Contante(idUffOwn, codUtente, idprocedimento, statoBene, bene, denaro, contante);
									if (insDenaro_Contante != null && insDenaro_Contante.size() > 0)
										idBene = (Long) insDenaro_Contante.get(0).get("IDBENE");
								}
								
								if(ComparatorUtil.containsValue(beneForm.getCategoria().getId(), Constants.CategoriaBeni.ID_TIPO_COLLEZIONE_S)){
									mapperBean.map(beneForm, collezioni);
								}
								
								if(ComparatorUtil.containsValue(beneForm.getCategoria().getId(), Constants.CategoriaBeni.ID_TIPO_ANIMALI_S)){
									mapperBean.map(beneForm, animali);
								}
								
								if(ComparatorUtil.containsValue(beneForm.getCategoria().getId(), Constants.CategoriaBeni.ID_TIPO_COLLEZIONE_S,Constants.CategoriaBeni.ID_TIPO_ANIMALI_S,Constants.CategoriaBeni.ID_ALTRI_OGGETTI_S)){
									List<Map> insMobile = beneDao.inserisciMobile(idUffOwn, codUtente, idprocedimento, statoBene, bene, animali, collezioni, descComune, desctipoStrada, desccategoria,
											descSottoCat);
									if (insMobile != null && insMobile.size() > 0)
										idBene = (Long) insMobile.get(0).get("IDBENE");
								}
							}

						}
						if ("3".equalsIgnoreCase(beneForm.getTipologia().getId())) {
							if ((beneForm.getCategoria() != null) && "10".equalsIgnoreCase(beneForm.getCategoria().getId())) {
								if ((beneForm.getComboDisponibilita() != null) && (beneForm.getComboDisponibilita().getId() != null)) {
									idDisponibilita = Long.parseLong(beneForm.getComboDisponibilita().getId());
								}
								mapperBean.map(beneForm, veicolo);
							}
							if ((beneForm.getCategoria() != null) && "11".equalsIgnoreCase(beneForm.getCategoria().getId())) {
								mapperBean.map(beneForm, immateriale);
							}
							List<Map> insMobReg = beneDao.inserisciMobileRegistrato(idUffOwn, codUtente, idprocedimento, statoBene, bene, veicolo, immateriale, descComune, desctipoStrada,
									desccategoria, descSottoCat, idDisponibilita);
							if (insMobReg != null && insMobReg.size() > 0)
								idBene = (Long) insMobReg.get(0).get("IDBENE");
						}
						if ("5".equalsIgnoreCase(beneForm.getTipologia().getId())) {
							mapperBean.map(beneForm, azienda);
							List<Map> insAzienda = beneDao.inserisciAzienda(idUffOwn, codUtente, idprocedimento, statoBene, bene, azienda, descComune, desctipoStrada, desccategoria);
							if (insAzienda != null && insAzienda.size() > 0)
								idBene = (Long) insAzienda.get(0).get("IDBENE");
						}
						if ("4".equalsIgnoreCase(beneForm.getTipologia().getId())) {
							mapperBean.map(beneForm, finanziario);
						String categoria = null;
						String sottocategoria = null;
						if (beneForm.getCategoria() != null) {
							categoria = beneForm.getCategoria().getId();
						}

						if (beneForm.getSottocategoria() != null) {
							sottocategoria = beneForm.getSottocategoria().getId();
						}
						if (ComparatorUtil.containsValue(categoria, Constants.CategoriaBeni.ID_POLIZZE)) {
							if (SottoCategoriaUtils.isPolizza(sottocategoria)) {
								TAnAltriOper altriOper = getTAnAltriOperByDescCodFisc(beneForm.getDescOperFinanz().getId());
								if (altriOper!=null) {
									finanziario.setDescIban(altriOper.getDescCodfiscale());
								}
								finanziario.setImpoEuro(beneForm.getValutaEur());
								if (beneForm.getTipoValuta()!=null) {
									finanziario.setTTpValuta(commonDao.getTpValuta(beneForm.getTipoValuta().getId()));
								}
								finanziario.setImpoValuta(beneForm.getValutaEst());
								finanziario.setDescRegistrazione(beneForm.getNumeroPolizza());
							}
						}
							List<Map> insBeneFin = beneDao.inserisciFinanziario(idUffOwn, codUtente, idprocedimento, statoBene, bene, finanziario);
						if (insBeneFin != null && insBeneFin.size() > 0) {
								idBene = (Long) insBeneFin.get(0).get("IDBENE");
						}
						}
					}
				}
			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return idBene;
	}

	private TAnAltriOper getTAnAltriOperByDescCodFisc(String descCodFiscale) {
		
		return beneDao.getTAnAltriOperByDescCodFisc(descCodFiscale);
	}

	/**
	 * listaBeniPerProcedimento
	 * 
	 * @param idProcedimento
	 * @param idUffOwn
	 * @param beneForm
	 * @return List<BeneForm>
	 */
	public List<BeneForm> listaBeniPerProcedimento(int filtro, int idUffOwn, long idProcedimento, BeneForm beneForm) {
		
		return listaBeniPerProcedimento(filtro, idUffOwn, idProcedimento, beneForm, null, null);
	}

	
	public List<BeneForm> listaBeniPerProcedimento(int filtro, int idUffOwn, long idProcedimento, BeneForm beneForm, Long idSogInv, Integer idCatAtto) {

		Long idBene = null;
		Long idTipologia = null;
		Long idCategoria = null;
		Long idSottocategoria = null;
		List<BeneTitoloDirittoDto> queryResults = null;
		if ((beneForm.getIdBene() != null) && !beneForm.getIdBene().toString().equals("-1")) {
			idBene = beneForm.getIdBene();
		}
		if ((beneForm.getTipologia() != null) && (beneForm.getTipologia().getId() != null) && !beneForm.getTipologia().getId().equals("-1")) {
			idTipologia = Long.parseLong(beneForm.getTipologia().getId());
		}
		if ((beneForm.getCategoria() != null) && (beneForm.getCategoria().getId() != null) && !beneForm.getCategoria().getId().equals("-1")) {
			idCategoria = Long.parseLong(beneForm.getCategoria().getId());
		}
		if ((beneForm.getSottocategoria() != null) && (beneForm.getSottocategoria().getId() != null) && !beneForm.getSottocategoria().getId().equals("-1")) {
			idSottocategoria = Long.parseLong(beneForm.getSottocategoria().getId());
		}
//		try {
			queryResults = beneDao.listaBeniPerProcedimento(filtro, idUffOwn, idProcedimento, idBene, idTipologia, idCategoria, idSottocategoria, idSogInv, idCatAtto);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		List<BeneForm> risultati = new ArrayList<BeneForm>();
		if ((queryResults == null) || queryResults.isEmpty()) {
			return risultati;
		}
		Iterator<BeneTitoloDirittoDto> iterator = queryResults.iterator();
		while (iterator.hasNext()) {	
			BeneTitoloDirittoDto bene=iterator.next();

			BeneForm form = new BeneForm();
			if (bene != null) {
				mapperBean.map(bene, form);
			}
			risultati.add(form);
		}
		return risultati;
	}
	
	public List<BeneForm> listaBeniPerProcedimentoFascicoloGestione(int filtro, int idUffOwn, long idProcedimento, BeneForm beneForm) {

		Long idBene = null;
		Long idTipologia = null;
		Long idCategoria = null;
		Long idSottocategoria = null;
		List<BeneTitoloDirittoDto> queryResults = null;
		if ((beneForm.getIdBene() != null) && !beneForm.getIdBene().toString().equals("-1")) {
			idBene = beneForm.getIdBene();
		}
		if ((beneForm.getTipologia() != null) && (beneForm.getTipologia().getId() != null) && !beneForm.getTipologia().getId().equals("-1")) {
			idTipologia = Long.parseLong(beneForm.getTipologia().getId());
		}
		if ((beneForm.getCategoria() != null) && (beneForm.getCategoria().getId() != null) && !beneForm.getCategoria().getId().equals("-1")) {
			idCategoria = Long.parseLong(beneForm.getCategoria().getId());
		}
		if ((beneForm.getSottocategoria() != null) && (beneForm.getSottocategoria().getId() != null) && !beneForm.getSottocategoria().getId().equals("-1")) {
			idSottocategoria = Long.parseLong(beneForm.getSottocategoria().getId());
		}
//		try {
			queryResults = beneDao.listaBeniPerProcedimentoFascicoloGestione(filtro, idUffOwn, idProcedimento, idBene, idTipologia, idCategoria, idSottocategoria);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		List<BeneForm> risultati = new ArrayList<BeneForm>();
		if ((queryResults == null) || queryResults.isEmpty()) {
			return risultati;
		}
		Iterator<BeneTitoloDirittoDto> iterator = queryResults.iterator();
		while (iterator.hasNext()) {	
			BeneTitoloDirittoDto bene=iterator.next();

			BeneForm form = new BeneForm();
			if (bene != null) {
				mapperBean.map(bene, form);
			}
			risultati.add(form);
		}
		return risultati;
	}	

	/**
	 * listaBeniPerProcedimentoNoAtto
	 * 
	 * @param idUffOwn
	 * @param idProcedimento
	 * @param idAtto1
	 * @param idAtto2
	 * @return List<BeneForm>
	 */
	public List<BeneForm> listaBeniPerProcedimentoNoAtto(int idUffOwn, long idProcedimento, long idAtto1, long idAtto2,
    		long idSoggettoDiretto, long idSoggettoTitolare, long idTipologia, long idCategoria, long idSottocategoria) {

		// se l' idAtto2 è -1 non faccio nulla, perchè la comparazione avviene
		// con l'atto passato idAtto1
		if (idAtto2 != -1l) {
			// se l' idAtto1 è uguale a 0 faccio la comparazione solo per la
			// proposta
			if (idAtto1 == 0l) {
				idAtto1 = getIdAttoByUfficioEProcedimento(idUffOwn, idProcedimento);
			} else if (idAtto2 == 0l) {
				// se l' idAtto1 è valorizzato faccio la comparazione sia
				// con il decreto che con la proposta
				idAtto2 = getIdAttoByUfficioEProcedimento(idUffOwn, idProcedimento);
			}
		}
		List queryResults = beneDao.listaBeniPerProcedimentoNoAtto(idUffOwn, idProcedimento, idAtto1, idAtto2,
	    								idSoggettoDiretto, idSoggettoTitolare, idTipologia, idCategoria, idSottocategoria);
		if ((queryResults == null) || queryResults.isEmpty()) {
			return null;
		}
		List<BeneForm> risultati = new ArrayList<BeneForm>();
		Iterator iterator = queryResults.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			TBene bene = (TBene) row.get("bene");
			BeneForm form = new BeneForm();
			if (bene != null) {
				mapperBean.map(bene, form);
			}
			risultati.add(form);
		}
		return risultati;
	}
	
	public List<BeneForm> listaBeniPerProcedimentoNoAttoPerRevoca(long idAtto, long idProcedimento, String descUtente, int idUffOwner, String nomeMetodo,
			long idSoggettoDiretto, long idSoggettoTitolare, long idTipologia, long idCategoria, long idSottocategoria) {

		List queryResults = beneDao.dettaglioRevocaTBene(idAtto, idProcedimento, descUtente, idUffOwner, nomeMetodo,
				 idSoggettoDiretto, idSoggettoTitolare, idTipologia, idCategoria, idSottocategoria);
		if ((queryResults == null) || queryResults.isEmpty()) {
			return null;
		}
		List<BeneForm> risultati = new ArrayList<BeneForm>();
		Iterator iterator = queryResults.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			TBene bene = (TBene) row.get("bene");
			BeneForm form = new BeneForm();
			if (bene != null) {
				mapperBean.map(bene, form);
			}
			risultati.add(form);
		}
		return risultati;
	}
	
	public List<BeneForm> listaBeniPerProcedimentoNoAttoPerDecRestBeni(long idAtto, long idProcedimento, String descUtente, int idUffOwner, String nomeMetodo,
													long idSoggettoDiretto, long idSoggettoTitolare, long idTipologia, long idCategoria, long idSottocategoria) {

		List queryResults = beneDao.elencoRestBeniTBene(idAtto, idProcedimento, descUtente, idUffOwner, nomeMetodo, idSoggettoDiretto, idSoggettoTitolare, idTipologia, idCategoria, idSottocategoria);
		if ((queryResults == null) || queryResults.isEmpty()) {
			return null;
		}
		List<BeneForm> risultati = new ArrayList<BeneForm>();
		Iterator iterator = queryResults.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			TBene bene = (TBene) row.get("bene");
			BeneForm form = new BeneForm();
			if (bene != null) {
				mapperBean.map(bene, form);
			}
			risultati.add(form);
		}
		return risultati;
	}	
	
	
	
	public List<BeneForm> listaBeniPerProcedimentoNoAttoPerOpposizione(long idAtto, long idProcedimento, String descUtente, int idUffOwner, String nomeMetodo,
			long idSoggettoDiretto, long idSoggettoTitolare, long idTipologia, long idCategoria, long idSottocategoria) {

		List queryResults = beneDao.dettaglioOpposTBene(idAtto, idProcedimento, descUtente, idUffOwner, nomeMetodo, idSoggettoDiretto, idSoggettoTitolare, idTipologia, idCategoria, idSottocategoria);
		if ((queryResults == null) || queryResults.isEmpty()) {
			return null;
		}
		List<BeneForm> risultati = new ArrayList<BeneForm>();
		Iterator iterator = queryResults.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			TBene bene = (TBene) row.get("bene");
			BeneForm form = new BeneForm();
			if (bene != null) {
				mapperBean.map(bene, form);
			}
			risultati.add(form);
		}
		return risultati;
	}
	
	public List<BeneForm> listaBeniPerProcedimentoNoAttoPerRevocaOpposizione(long idAtto, long idProcedimento, String descUtente, int idUffOwner, String nomeMetodo) {

		List queryResults = beneDao.dettaglioRevocaOpposTBene(idAtto, idProcedimento, descUtente, idUffOwner, nomeMetodo);
		if ((queryResults == null) || queryResults.isEmpty()) {
			return null;
		}
		List<BeneForm> risultati = new ArrayList<BeneForm>();
		Iterator iterator = queryResults.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			TBene bene = (TBene) row.get("bene");
			BeneForm form = new BeneForm();
			if (bene != null) {
				mapperBean.map(bene, form);
			}
			risultati.add(form);
		}
		return risultati;
	}		

	public List<BeneForm> listaBeniPerProcedimentoNoAttoPerRevocazione(long idAtto, long idProcedimento, String descUtente, int idUffOwner, String nomeMetodo,
    		long idSoggettoDiretto, long idSoggettoTitolare, long idTipologia, long idCategoria, long idSottocategoria) {	

		List queryResults = beneDao.dettaglioRevocazTBene(idAtto, idProcedimento, descUtente, idUffOwner, nomeMetodo,
														  idSoggettoDiretto, idSoggettoTitolare, idTipologia, idCategoria, idSottocategoria);
		if ((queryResults == null) || queryResults.isEmpty()) {
			return null;
		}
		List<BeneForm> risultati = new ArrayList<BeneForm>();
		Iterator iterator = queryResults.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			TBene bene = (TBene) row.get("bene");
			BeneForm form = new BeneForm();
			if (bene != null) {
				mapperBean.map(bene, form);
			}
			risultati.add(form);
		}
		return risultati;
	}	
	
	public List<BeneForm> controlloPresenzaBeniPerRevocaConfisca(long idAtto, long idProcedimento, String descUtente, int idUffOwner, String nomeMetodo) {	

		List queryResults = beneDao.controlloPresenzaBeniPerRevocaConfisca(idAtto, idProcedimento, descUtente, idUffOwner, nomeMetodo);
		if ((queryResults == null) || queryResults.isEmpty()) {
			return null;
		}
		List<BeneForm> risultati = new ArrayList<BeneForm>();
		Iterator iterator = queryResults.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			TBene bene = (TBene) row.get("bene");
			BeneForm form = new BeneForm();
			if (bene != null) {
				mapperBean.map(bene, form);
			}
			risultati.add(form);
		}
		return risultati;
	}	

	/**
	 * listaBeniProcDecretoFiltro
	 * 
	 * @param idProcedimento
	 * @param idUffOwn
	 * @param beneForm
	 * @param vidDecreto
	 * @param vidProposta
	 * @return List
	 */
	
	public List listaBeniProcDecretoFiltro(int filtro, int idUffOwn, long idProcedimento, BeneForm beneForm, long vidDecreto, long vidProposta, Long idAmministratore, String Amministratore) {

		Long idBene = null;
		Long idTipologia = null;
		Long idCategoria = null;
		Long idSottocategoria = null;
		List queryResults = null;
		if ((beneForm.getIdBene() != null) && !beneForm.getIdBene().toString().equals("-1")) {
			idBene = beneForm.getIdBene();
		}
		if ((beneForm.getTipologia() != null) && (beneForm.getTipologia().getId() != null) && !beneForm.getTipologia().getId().equals("-1")) {
			idTipologia = Long.parseLong(beneForm.getTipologia().getId());
		}
		if ((beneForm.getCategoria() != null) && (beneForm.getCategoria().getId() != null) && !beneForm.getCategoria().getId().equals("-1")) {
			idCategoria = Long.parseLong(beneForm.getCategoria().getId());
		}
		if ((beneForm.getSottocategoria() != null) && (beneForm.getSottocategoria().getId() != null) && !beneForm.getSottocategoria().getId().equals("-1")) {
			idSottocategoria = Long.parseLong(beneForm.getSottocategoria().getId());
		}
//		try {
			queryResults = beneDao.listaBeniProcDecretoFiltro(filtro, idUffOwn, idProcedimento, idBene, idTipologia, idCategoria, idSottocategoria, vidDecreto, vidProposta, idAmministratore, Amministratore);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		if ((queryResults == null) || queryResults.isEmpty()) {
			return null;
		}
		List<BeneForm> risultati = new ArrayList<BeneForm>();
		Iterator iterator = queryResults.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			TBene bene = (TBene) row.get("bene");
			BeneForm form = new BeneForm();
			if (bene != null) {
				mapperBean.map(bene, form);
			}
			risultati.add(form);
		}
		return risultati;
	}

	/**
	 * listaSoggettiBeniProc
	 * 
	 * @param idProcedimento
	 * @param idUffOwner
	 * @param idBene
	 * @return List<BeneSoggettoForm>
	 */
	public List<BeneSoggettoForm> listaSoggettiBeniProc(long idProcedimento, int idUffOwner, long idBene, long idAtto) {

		if (idAtto == 0l) {
			idAtto = getIdAttoByUfficioEProcedimento(idUffOwner, idProcedimento);
		}
		List<BeneSoggettoForm> lista = new ArrayList<BeneSoggettoForm>();
		List<SogIntDettaglioProcedimentoDto> soggettiBeniProc = beneDao.listaSoggettiBeniProc(idProcedimento, idUffOwner, idBene, idAtto);
		Iterator<SogIntDettaglioProcedimentoDto> iter = soggettiBeniProc.iterator();
		while (iter.hasNext()) {
			BeneSoggettoForm beneSoggettoForm = new BeneSoggettoForm();
			SoggettoForm soggetto = new SoggettoForm();
			beneSoggettoForm.setSoggetto(soggetto);
			SogIntDettaglioProcedimentoDto currentDto = iter.next();
			mapperBean.map(currentDto, beneSoggettoForm.getSoggetto());
			mapperBean.map(currentDto, beneSoggettoForm);
			beneSoggettoForm.setSoggetto(beneSoggettoForm.getSoggetto());
			BeneForm bene = new BeneForm();
			bene.setIdBene(idBene);
			beneSoggettoForm.setBene(bene);
			lista.add(beneSoggettoForm);
		}
		return lista;
	}
	
	public List<BeneSoggettoForm> listaSoggettiBeniProcSenzaIdAtto(long idProcedimento, int idUffOwner, long idBene) {

		/*if (idAtto == 0l) {
			idAtto = getIdAttoByUfficioEProcedimento(idUffOwner, idProcedimento);
		}*/
		long idAtto = 0l;
		List<BeneSoggettoForm> lista = new ArrayList<BeneSoggettoForm>();
		List<SogIntDettaglioProcedimentoDto> soggettiBeniProc = beneDao.listaSoggettiBeniProc(idProcedimento, idUffOwner, idBene, idAtto);
		Iterator<SogIntDettaglioProcedimentoDto> iter = soggettiBeniProc.iterator();
		while (iter.hasNext()) {
			BeneSoggettoForm beneSoggettoForm = new BeneSoggettoForm();
			SoggettoForm soggetto = new SoggettoForm();
			beneSoggettoForm.setSoggetto(soggetto);
			SogIntDettaglioProcedimentoDto currentDto = iter.next();
			mapperBean.map(currentDto, beneSoggettoForm.getSoggetto());
			mapperBean.map(currentDto, beneSoggettoForm);
			beneSoggettoForm.setSoggetto(beneSoggettoForm.getSoggetto());
			BeneForm bene = new BeneForm();
			bene.setIdBene(idBene);
			beneSoggettoForm.setBene(bene);
			lista.add(beneSoggettoForm);
		}
		return lista;
	}
	
	public List<BeneSoggettoForm> listaSoggettiBeniProcTuttiAtti(long idProcedimento, int idUffOwner, long idBene) {

		List<BeneSoggettoForm> lista = new ArrayList<BeneSoggettoForm>();
		List<SogIntDettaglioProcedimentoDto> soggettiBeniProc = beneDao.listaSoggettiBeniProcTuttiAtti(idProcedimento, idUffOwner, idBene);
		Iterator<SogIntDettaglioProcedimentoDto> iter = soggettiBeniProc.iterator();
		while (iter.hasNext()) {
			BeneSoggettoForm beneSoggettoForm = new BeneSoggettoForm();
			SoggettoForm soggetto = new SoggettoForm();
			beneSoggettoForm.setSoggetto(soggetto);
			SogIntDettaglioProcedimentoDto currentDto = iter.next();
			mapperBean.map(currentDto, beneSoggettoForm.getSoggetto());
			mapperBean.map(currentDto, beneSoggettoForm);
			beneSoggettoForm.setSoggetto(beneSoggettoForm.getSoggetto());
			lista.add(beneSoggettoForm);
		}
		return lista;
	}

	public void salvaDecisorioBeni(BeneSoggettoForm beneSoggettoForm, List<BeneForm> beniSelezionati, long idAtto, long idProcedimento, String idCodiceUtente, long idUfficio, long idTipoUff,String descDecisorio) {

		for (BeneForm bene : beniSelezionati) {
			for (String idSoggettiTitolareETitolo : bene.getListaIdCheckboxString()) {
				String idSoggetto = null;
				StringTokenizer st = new StringTokenizer(idSoggettiTitolareETitolo, ";");
				while (st.hasMoreTokens()) {
					idSoggetto = st.nextToken();
					st.nextToken();
				}
				TRelBeneAtto beneAtto = new TRelBeneAtto();
				TRelBeneAttoId idBene = new TRelBeneAttoId();
				idBene.setCodiKIdAtto(idAtto);
				idBene.setCodiKIdBene(bene.getIdBene());
				idBene.setCodiKIdSoggTit(new Long(idSoggetto));
				idBene.setCodiKIdProc(idProcedimento);
				idBene.setCodiKIdUffOwner((int) idUfficio);
				beneAtto.setId(idBene);
				beneAtto.setDescUtente(String.valueOf(idCodiceUtente));
				Long idEsitoDecisione = Long.parseLong(beneSoggettoForm.getDecisioneBeni().getId());
	
			    if ("DECISORIO".equalsIgnoreCase(descDecisorio)){
					beneAtto.setTTpEsitoByCodiFkIdEsito1(new TTpEsito(idEsitoDecisione.intValue()));
		
			    }else if ("DECISORIO_II".equalsIgnoreCase(descDecisorio)){
					beneAtto.setTTpEsitoByCodiFkIdEsito2(new TTpEsito(idEsitoDecisione.intValue()));
						    
			    }else if ("SENTENZA_CASSAZIONE".equalsIgnoreCase(descDecisorio)){
					beneAtto.setTTpEsitoByCodiFkIdEsito3(new TTpEsito(idEsitoDecisione.intValue()));

			    }
				
				beneDao.salvaDecisorioBeni(beneAtto,descDecisorio);	
		
			}
		}

		// Iterator<Long> iter =
		// beneSoggettoForm.getListaIdCheckati().iterator();
		// while (iter.hasNext()) {
		// Long currentIdBene = iter.next();
		// TRelBeneAtto bene = new TRelBeneAtto();
		// TRelBeneAttoId idBene = new TRelBeneAttoId();
		// idBene.setCodiKIdAtto(idAtto);
		// idBene.setCodiKIdBene(currentIdBene);
		// idBene.setCodiKIdProc(idProcedimento);
		// idBene.setCodiKIdUffOwner((int) idUfficio);
		// bene.setId(idBene);
		// Long idEsitoDecisione =
		// Long.parseLong(beneSoggettoForm.getDecisioneBeni().getId());
		// bene.setTEsitoByCodiFkIdEsito1(new
		// TEsito(idEsitoDecisione.intValue()));
		// bene.setCodiUtente(idCodiceUtente);
		// beneDao.salvaDecisorioBeni(bene);
		// }
	}
	
	public void salvaDecisorioBeniIstGen(long idAtto, long idProcedimento, String idCodiceUtente, int idUfficio, int idTipoUff, String descDecisorio, int idDecisorio, TRelAttoIstGen rec) {

		beneDao.salvaDecisorioBeniIstGen(idAtto, idProcedimento, idCodiceUtente, idUfficio, idTipoUff, descDecisorio, idDecisorio, rec);	
		
	}		
	
	public TRelAttoIstGen getDecisorioBeniIstGen(TRelAttoIstGenId id) {
		return beneDao.getDecisorioBeniIstGen(id);
	}

	public void setBeneDao(BeneDao beneDao) {

		this.beneDao = beneDao;
	}

	public void setProcedimentoService(ProcedimentoService procedimentoService) {

		this.procedimentoService = procedimentoService;
	}

	private void caricaDettaglioPerTipologia(BeneForm form, long idBene, int idUffOwn, long idProcedimento) {

		HashMap<String, Object> hashDettaglio = new HashMap<String, Object>();
		String tipologia = form.getTipologia().getId();
		String categoria = "";
		String sottocategoria = "";
		if ((form.getCategoria() != null) || (form.getCategoria().getId() != null)) {
			categoria = form.getCategoria().getId();
		}
		if ((form.getSottocategoria() != null) || (form.getSottocategoria().getId() != null)) {
			sottocategoria = form.getSottocategoria().getId();
		}
		if (tipologia.equals("1")) {// Immobile
			TBeneImmobile immobile = beneDao.getTBeneImmobileById(idBene, idUffOwn);
			mapperBean.map(immobile, form);
			
			//FC. 23/06/2022 Ticket#20220623016 nel caso di dettaglio bene non veniva visualizzato correttamente
			//la categoria catastale
			if (form.getCategoriaCatastale() != null) {
				if (form.getCategoriaCatastale().getId() != null) {
					List<ComboForm> categorieCatastali = commonService.getCategorieCatastali();
					for (ComboForm categoriaCatastale : categorieCatastali) {
						if(categoriaCatastale.getId().equalsIgnoreCase(form.getCategoriaCatastale().getId())){
							form.setCategoriaCatastale(categoriaCatastale);
							break;
						}
					}
				}
				else  {
					form.getCategoriaCatastale().setDescrizione(null);
				}
			}
			//fine modifica Ticket#20220623016
			
			// Giacomo 17/07/2013
			if (immobile.getTTpTipoDenunciaByCodiFkIdTipoDenuncia() != null) {
				form.setSwitchDenunciaIdentifImmobile("D");
			} else {
				form.setSwitchDenunciaIdentifImmobile("I");
			}

			if (immobile.getTTpTipoDenunciaByCodiFkIdTipoDenunciaP() != null) {
				form.setSwitchDenunciaIdentifImmobileP("D");
			} else {
				form.setSwitchDenunciaIdentifImmobileP("I");
			}

			hashDettaglio.put("imm", immobile.getId());
			hashDettaglio.put("veic", null);
			Map<String, Object> queryResult = beneDao.getDettaglioBene(hashDettaglio);
			TRelProcBene relProcBene = beneDao.getTRelProcBeneById(idProcedimento, idBene, idUffOwn);
			TRelBeneProcDisp disp = (TRelBeneProcDisp) queryResult.get("disp");
			TRelImmProcVinc vinc = (TRelImmProcVinc) queryResult.get("vinc");
			if (disp != null) {
				mapperBean.map(disp, form);
			}
			if (vinc != null) {
				mapperBean.map(vinc, form);
			}
			if (relProcBene != null) {
				mapperBean.map(relProcBene, form);
			}
			return;
		} else if (tipologia.equals("2")) { // Mobile
			if (categoria.equals("6")) { // Denaro
				if (sottocategoria.equals("35") || sottocategoria.equals("206")) { // Contante
					TContante contante = beneDao.getTContanteById(idBene, idUffOwn);
					mapperBean.map(contante, form);
					if (form.getCodiceAbi() != null) {
						// Se sto caricando il dettaglio del bene Mobile -
						// Denaro - Contante
						// Devo prendere dal DB l'indirizzo relativo alla
						// Descrizione dell'Ente
						BeneForm beneForm = caricaInfoOperFinanz(form.getCodiceAbi(), form.getCodiceCab().getId());
						IndirizzoForm indirizzo = Utility.costruisciIndirizzo(beneForm);
						form.setIndirizzoBeneFin(indirizzo.getDenominazione());
					} else
						form.setIndirizzoBeneFin("");
					return;
				} else {
					TDenaro denaro = beneDao.getTDenaroById(idBene, idUffOwn);
					
					mapperBean.map(denaro, form);
					return;
				}
			}
			if (categoria.equals("7")) {// Collezioni
				TCollezioni collezioni = beneDao.getTCollezioniById(idBene, idUffOwn);
				mapperBean.map(collezioni, form);
				return;
			}
			if (categoria.equals("8")) {// Animali
				TAnimali animali = beneDao.getTAnimaliById(idBene, idUffOwn);
				mapperBean.map(animali, form);
				return;
			}
			if (categoria.equals("9")) {// Altri Oggetti
				form.setDescrizioneBene(StringUtils.substringAfter(form.getDescrizioneBene(), " - "));
				if (form.getComune() != null && StringUtils.isEmpty(form.getComune().getDescrizione())) {
					form.getComune().setDescrizione(form.getDesComuneEstero());
				}
				return;
			}
		} else if (tipologia.equals("3")) {// Mobile Registrato
			if (categoria.equals("10")) {// Veicolo
				TVeicolo veicolo = beneDao.getTVeicoloById(idBene, idUffOwn);
				mapperBean.map(veicolo, form);
				hashDettaglio.put("imm", null);
				hashDettaglio.put("veic", veicolo.getId());
				Map<String, Object> queryResult = beneDao.getDettaglioBene(hashDettaglio);
				TRelBeneProcDisp disp = (TRelBeneProcDisp) queryResult.get("disp");
				if (disp != null) {
					mapperBean.map(disp, form);
				}
			} else if (categoria.equals("11")) {// Bene Immateriale
				TBeneImmat immateriale = beneDao.getTBeneImmaterialeById(idBene, idUffOwn);
				mapperBean.map(immateriale, form);
			}
			return;
		} else if (tipologia.equals("4")) {// Bene Finanziario
			TBeneFin finanziario = beneDao.getTBeneFinanziarioById(idBene, idUffOwn);
			mapperBean.map(finanziario, form);
			return;
		} else if (tipologia.equals("5")) {// Azienda
			TAzienda azienda = beneDao.getTAziendaById(idBene, idUffOwn);
			TBene bene = beneDao.getTBeneById(idBene, idUffOwn);
			mapperBean.map(azienda, form);
			mapperBean.map(bene, form);
			TRelProcBene procBene = beneDao.getTRelProcBeneById(idProcedimento, idBene, idUffOwn);
			if (procBene != null) {
				mapperBean.map(procBene, form);
			}
			return;
		}
	}

	private ComboForm getCBNormativaById(long idNormativa) {

		TAnNormative normativa = beneDao.getTAnNormativeById(idNormativa);
		if (normativa == null) {
			return null;
		}
		ComboForm risultato = new ComboForm();
		risultato.setId(String.valueOf(normativa.getCodiKIdNormativa()));
		risultato.setDescrizione(normativa.getDescNormativa());
		return risultato;
	}

	private ComboForm getCBTipologiaPrevenzioneById(long idTipoPrevenzione) {

		TTpTipoPrevenzione tipoPrevenzione = beneDao.getTTpTipoPrevenzioneById(idTipoPrevenzione);
		if (tipoPrevenzione == null) {
			return null;
		}
		ComboForm risultato = new ComboForm();
		risultato.setId(String.valueOf(tipoPrevenzione.getCodiKIdTipoPrev()));
		risultato.setDescrizione(tipoPrevenzione.getDescTipoPrev());
		return risultato;
	}

	private ComboForm getCBTipoMisuraById(long idTipoMisura) {

		TTpTipoMisura tipoMisura = beneDao.getTTpTipoMisuraById(idTipoMisura);
		if (tipoMisura == null) {
			return null;
		}
		ComboForm risultato = new ComboForm();
		risultato.setId(String.valueOf(tipoMisura.getCodiKIdTipoMisura()));
		risultato.setDescrizione(tipoMisura.getDescTipoMisura());
		return risultato;
	}

	private void reimpostaIValoriDelSoggettoVecchio(SoggettoForm soggetto, SoggettoForm soggettoVecchio) {

		ComboForm tipologiaPrevenzioneVecchia = soggettoVecchio.getTipologiaPrevenzione();
		MisuraForm misuraVecchia = soggettoVecchio.getMisura();
		ComboForm tipoMisuraVecchia = null;
		if (misuraVecchia != null) {
			tipoMisuraVecchia = misuraVecchia.getTipoMisura();
		}
		ComboForm normativaVecchia = soggettoVecchio.getNormativa();
		if (tipologiaPrevenzioneVecchia != null) {
			long idTipologiaPrevenzioneVecchia = Long.valueOf(tipologiaPrevenzioneVecchia.getId());
			soggetto.setTipologiaPrevenzione(getCBTipologiaPrevenzioneById(idTipologiaPrevenzioneVecchia));
		}
		if (tipoMisuraVecchia != null) {
			long idtipoMisuraVecchia = Long.valueOf(tipoMisuraVecchia.getId());
			soggetto.getMisura().setTipoMisura(getCBTipoMisuraById(idtipoMisuraVecchia));
		}
		if (normativaVecchia != null) {
			long idNormativaVecchia = Long.valueOf(normativaVecchia.getId());
			soggetto.setNormativa(getCBNormativaById(idNormativaVecchia));
		}
	}

//	public List<TRelBeneAtto> getTRelBeneAtto(long idProcedimento, long idBene, Integer idUfficioUtente) {
//
//		return beneDao.getTRelBeneAtto(idProcedimento, idBene, idUfficioUtente);
//	}

	public List<TRelBeneAtto> getTRelBeneAtto(long idProcedimento, long idBene, Integer idUfficioUtente, long idAtto) {

		return beneDao.getTRelBeneAtto(idProcedimento, idBene, idUfficioUtente, idAtto);
	}

	public void eliminaRelProcBene(long idProcedimento, long idBene, Integer idUfficioUtente) {

		beneDao.eliminaTRelProcBene(idProcedimento, idBene, idUfficioUtente);
		return;
	}

	public void eliminaBene(long idBene, Integer idUfficioUtente) {

		beneDao.eliminaBene(idBene, idUfficioUtente);
		return;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.giustizia.smp.service.BeneService#isAssociato(long, int, long)
	 */
	
	public boolean isAssociato(long idBene, int idUfficioUtente, long idProcedimento) {
		return beneDao.isAssociato(idBene, idUfficioUtente, idProcedimento);
	}
	
	
	public void modificaBene(long idUffOwn, String codUtente, long idprocedimento, BeneForm beneForm) {
		
		TBene bene = new TBene();
		mapperBean.map(beneForm, bene);
		String categoria = "";
		String sottocategoria = "";
		//F.C. Ticket#20240312012 — Problemi con le società
		//sostituito or con and, perchè nel caso della categoria azienda non esiste la sottocategoria
		if ((beneForm.getCategoria() != null) && (beneForm.getCategoria().getId() != null)) {
			categoria = beneForm.getCategoria().getId();
		}
		if ((beneForm.getSottocategoria() != null) && (beneForm.getSottocategoria().getId() != null)) {
			sottocategoria = beneForm.getSottocategoria().getId();
		}
		long statoBene = 0;
		if(beneForm.getStatoConservazione()!=null){
			statoBene = NumberUtils.toLong(beneForm.getStatoConservazione().getId(),0);
		}
		
		if(ComparatorUtil.containsValue(beneForm.getTipologia().getId(), Constants.TipologieBeni.ID_TIPO_MOBILE_AZIENDA_S)){
			modificaAzienda(idUffOwn, codUtente, idprocedimento,statoBene, beneForm, bene);
		}
		
		if(ComparatorUtil.containsValue(beneForm.getTipologia().getId(), Constants.TipologieBeni.ID_TIPO_IMMOBILE_S)){
			modificaImmobile(idUffOwn, codUtente, idprocedimento,statoBene, beneForm, bene);
		}
		
		if(ComparatorUtil.containsValue(beneForm.getTipologia().getId(), Constants.TipologieBeni.ID_TIPO_MOBILE_REGISTRATO_S)){
			modificaBeneMobileRegistrato(idUffOwn, codUtente, idprocedimento, statoBene, beneForm, bene);
		}
		
		if(ComparatorUtil.containsValue(beneForm.getTipologia().getId(), Constants.TipologieBeni.ID_TIPO_MOBILE_S)){
			modificaBeneMobile(idUffOwn, codUtente, idprocedimento, statoBene, beneForm, bene);
		}
		
		if(ComparatorUtil.containsValue(beneForm.getTipologia().getId(), Constants.TipologieBeni.ID_TIPO_FINANZIARIO_S)){
			if (SottoCategoriaUtils.isPolizza(sottocategoria)) {
				modificaBenePolizza(idUffOwn, codUtente, idprocedimento, statoBene, beneForm, bene);
			}else {
				modificaBeneFinanziario(idUffOwn, codUtente, idprocedimento, statoBene, beneForm, bene);
			}
		}
	}
	
	private void modificaAzienda(long idUffOwn, String codUtente, long idprocedimento,long statoBene, BeneForm beneForm,TBene bene){
		TAzienda azienda = new TAzienda();
		mapperBean.map(beneForm, azienda);
		
		String descComune = null;
		if ((beneForm.getComune() != null)) {
			descComune = beneForm.getComune().getDescrizione();
		}
		
		String desctipoStrada = null;
		if ((beneForm.getIndirizzo() != null) && (beneForm.getIndirizzo().getToponimo() != null)) {
			desctipoStrada = beneForm.getIndirizzo().getToponimo().getDescrizione();
		}
		
		String desccategoria = null;
		if (beneForm.getCategoria() != null) {
			desccategoria = beneForm.getCategoria().getDescrizione();
		}
		
		beneDao.modificaAzienda(idUffOwn, codUtente, idprocedimento, statoBene, bene, azienda, descComune, desctipoStrada, desccategoria);
	}
	
	private void modificaImmobile(long idUffOwn, String codUtente, long idprocedimento,long statoBene, BeneForm beneForm,TBene bene){
		TBeneImmobile immobile = new TBeneImmobile();
		
		mapperBean.map(beneForm, immobile);
		
		long idVincolo = 0;
		if ((beneForm.getVincolo() != null)) {
			idVincolo = NumberUtils.toLong(beneForm.getVincolo().getId(), 0);
		}
		
		long idDisponibilita=0;
		if ((beneForm.getComboDisponibilita() != null)) {
			idDisponibilita = NumberUtils.toLong(beneForm.getComboDisponibilita().getId(), 0);
		}
		if ((beneForm.getCodiceCatastale2() != null) && (immobile.getDescCatasto() != null)) {
			immobile.setDescCatasto(immobile.getDescCatasto() + beneForm.getCodiceCatastale2());
		}
		if ((beneForm.getCodiceCatastale2P() != null) && (immobile.getDescCatastoP() != null)) {
			immobile.setDescCatastoP(immobile.getDescCatastoP() + beneForm.getCodiceCatastale2P());
		}
		if ("I".equalsIgnoreCase(beneForm.getSwitchDenunciaIdentifImmobile())) {
			immobile.setTTpTipoDenunciaByCodiFkIdTipoDenuncia(null);
			immobile.setNumeDenuncia1(null);
			immobile.setNumeDenuncia2(null);
			immobile.setNumeAnnoDenuncia(null);
		} else if ("D".equalsIgnoreCase(beneForm.getSwitchDenunciaIdentifImmobile())) {
			immobile.setDescSezione(null);
			immobile.setDescFoglio(null);
			immobile.setDescParticella1(null);
			immobile.setDescParticella2(null);
			immobile.setDescSubParticella1(null);
			immobile.setDescSubParticella2(null);
		}
		if ("I".equalsIgnoreCase(beneForm.getSwitchDenunciaIdentifImmobileP())) {
			immobile.setTTpTipoDenunciaByCodiFkIdTipoDenunciaP(null);
			immobile.setNumeDenuncia1P(null);
			immobile.setNumeDenuncia2P(null);
			immobile.setNumeAnnoDenunciaP(null);
		} else if ("D".equalsIgnoreCase(beneForm.getSwitchDenunciaIdentifImmobileP())) {
			immobile.setDescSezioneP(null);
			immobile.setDescFoglioP(null);
			immobile.setDescParticella1P(null);
			immobile.setDescParticella2P(null);
			immobile.setDescSubalterno1P(null);
			immobile.setDescSubalterno2P(null);
		}
		String descVincolo = beneForm.getDescrizioneVincolo();

		// Giacomo 18/07/2013 Aggiunto il tipo occupante
		long vtipoOccupanti = 0;
		if (beneForm.getTipoOccupanti() != null) {
			vtipoOccupanti = NumberUtils.toLong(beneForm.getTipoOccupanti().getId(), 0);
		}
		
		beneDao.modificaImmobile(idUffOwn, codUtente, idprocedimento, statoBene, bene, immobile, idDisponibilita, idVincolo, descVincolo, vtipoOccupanti);
	}
	
	
	private void modificaBeneMobileRegistrato(long idUffOwn, String codUtente, long idprocedimento,long statoBene, BeneForm beneForm,TBene bene){
		TBeneImmat immateriale = new TBeneImmat();
		TVeicolo veicolo = new TVeicolo();
		long idDisponibilita = 0;
		if(ComparatorUtil.containsValue(beneForm.getCategoria().getId(), Constants.CategoriaBeni.ID_TIPO_IMMATERIALE_S)){
			mapperBean.map(beneForm, immateriale);
		}
		if(ComparatorUtil.containsValue(beneForm.getCategoria().getId(), Constants.CategoriaBeni.ID_TIPO_VEICOLO_S)){
			mapperBean.map(beneForm, veicolo);
			if(beneForm.getComboDisponibilita()!=null && beneForm.getComboDisponibilita().getId()!=null){
				idDisponibilita=Long.parseLong(beneForm.getComboDisponibilita().getId());
			}
		}
		
		
		
		String descComune = null;
		if ((beneForm.getComune() != null)) {
			descComune = beneForm.getComune().getDescrizione();
		}
		
		String desctipoStrada = null;
		if ((beneForm.getIndirizzo() != null) && (beneForm.getIndirizzo().getToponimo() != null)) {
			desctipoStrada = beneForm.getIndirizzo().getToponimo().getDescrizione();
		}
		
		String desccategoria = null;
		if (beneForm.getCategoria() != null) {
			desccategoria = beneForm.getCategoria().getDescrizione();
		}
		String descSottoCat = null;
		if ((beneForm.getSottocategoria() != null) && (beneForm.getSottocategoria().getDescrizione() != null)) {
			descSottoCat = beneForm.getSottocategoria().getDescrizione();
		}
		
		
		beneDao.modificaMobileRegistrato(idUffOwn, codUtente, idprocedimento, statoBene, bene, veicolo, immateriale, descComune, desctipoStrada,
				desccategoria, descSottoCat, idDisponibilita);
		
	}
	
	private void modificaBeneFinanziario(long idUffOwn, String codUtente, long idprocedimento, long statoBene, BeneForm beneForm, TBene bene){
		
		TBeneFin finanziario = new TBeneFin();
		mapperBean.map(beneForm, finanziario);
		beneDao.modificaFinanziario(idUffOwn, codUtente, idprocedimento, statoBene, bene, finanziario);
	}
	
	private void modificaBeneMobile(long idUffOwn, String codUtente, long idprocedimento, long statoBene, BeneForm beneForm, TBene bene) {

		//DENARO CONTANTE
		if (ComparatorUtil.containsValue(beneForm.getCategoria().getId(), Constants.CategoriaBeni.ID_TIPO_DENARO_CONTANTE_S)) {
			
			TDenaro denaro = new TDenaro();
			TContante contante = new TContante();
			
			if (beneForm.getSottocategoria() != null) {
				// Contante
				if ("35".equalsIgnoreCase(beneForm.getSottocategoria().getId()) || "206".equalsIgnoreCase(beneForm.getSottocategoria().getId())) {
					mapperBean.map(beneForm, contante);
				} else {
					// Denaro
					mapperBean.map(beneForm, denaro);
				}
			}

			beneDao.modificaDenaroContante(idUffOwn, codUtente, idprocedimento, statoBene, bene, denaro, contante);
		}

		//ANIMALI E COLLEZIONI
		if (ComparatorUtil.containsValue(beneForm.getCategoria().getId(), Constants.CategoriaBeni.ID_TIPO_ANIMALI_S, Constants.CategoriaBeni.ID_TIPO_COLLEZIONE_S,Constants.CategoriaBeni.ID_ALTRI_OGGETTI_S)) {

			TAnimali animali = new TAnimali();
			TCollezioni collezioni = new TCollezioni();

			if (ComparatorUtil.containsValue(beneForm.getCategoria().getId(), Constants.CategoriaBeni.ID_TIPO_COLLEZIONE_S)) {
				mapperBean.map(beneForm, collezioni);
			}

			if (ComparatorUtil.containsValue(beneForm.getCategoria().getId(), Constants.CategoriaBeni.ID_TIPO_ANIMALI_S)) {
				mapperBean.map(beneForm, animali);
			}

			String descComune = null;
			if ((beneForm.getComune() != null)) {
				descComune = beneForm.getComune().getDescrizione();
			}

			String desctipoStrada = null;
			if ((beneForm.getIndirizzo() != null) && (beneForm.getIndirizzo().getToponimo() != null)) {
				desctipoStrada = beneForm.getIndirizzo().getToponimo().getDescrizione();
			}

			String desccategoria = null;
			if (beneForm.getCategoria() != null) {
				desccategoria = beneForm.getCategoria().getDescrizione();
			}
			String descSottoCat = null;
			if ((beneForm.getSottocategoria() != null) && (beneForm.getSottocategoria().getDescrizione() != null)) {
				descSottoCat = beneForm.getSottocategoria().getDescrizione();
			}

			beneDao.modificaMobile(idUffOwn, codUtente, idprocedimento, statoBene, bene, animali, collezioni, descComune, desctipoStrada, desccategoria, descSottoCat);
		}

	}
	/*
	 * (non-Javadoc)
	 * @see it.giustizia.smp.service.BeneService#venditaBene(long, int, long, it.giustizia.smp.form.BeneForm)
	 */
	
	
	public long venditaBene(long idUffOwn, String codUtente, long idprocedimento, BeneForm beneForm) {
		
		
		TBene bene = new TBene();
		mapperBean.map(beneForm, bene);
		
		TDenaro denaro = new TDenaro();
		mapperBean.map(beneForm, denaro);
		
		TContante contante = new TContante();
		mapperBean.map(beneForm, contante);

		
		long statoBene = 0;
		if(beneForm.getStatoConservazione()!=null){
			statoBene = NumberUtils.toLong(beneForm.getStatoConservazione().getId(),0);
		}
		
		List<Map> insImmobile = beneDao.venditaBene(idUffOwn, codUtente, idprocedimento, statoBene, bene, denaro, beneForm.getNumBollettino());
		
		// List<Map> insImmobile = beneDao.inserisciDenaro_Contante(idUffOwn,
		// codUtente, idprocedimento, statoBene, bene, denaro, contante);
		if (insImmobile != null && insImmobile.size() > 0){
			return  (Long) insImmobile.get(0).get("IDBENE");
		}
		return 0;
	}

	
	public long trasformaBene(long idUffOwn, String codUtente, long idprocedimento, long idBeneVecchio,long idBene, Long idAtto, BeneForm beneForm) {
		beneForm.setIdBene(null);
		
		long idBeneNuovo = 0l;
		if(idBene > 0l){
			idBeneNuovo = idBene; //per fascicolo di gestione
		}else{
			idBeneNuovo = inserisciBene(idUffOwn, codUtente, idprocedimento, beneForm);
		}
		
		if(idBeneNuovo<=0){
			return 0;
		}

		Integer idTipoTrasformazione = NumberUtils.toInt(beneForm.getTipoTrasformazione().getId(), 0);
		if(idTipoTrasformazione == 0){
			idTipoTrasformazione = null;
		}
		
		Character vidMotivo = null;
		beneDao.trasformaBene(idprocedimento, idBeneVecchio, idBeneNuovo, idUffOwn, codUtente, vidMotivo,idTipoTrasformazione ,idAtto);
		
		return idBeneNuovo;
	}
	
	public long insersciBeneInCorrezioneErroreMateriale(long idUffOwn, String codUtente, long idprocedimento, long idBeneVecchio, Long idAtto, BeneForm beneForm, String idTipoFasc, String abilFascGest) {
		beneForm.setIdBene(null);//setto l'id bene a null poichè provvengo da modifica
		
		long idBeneNuovo = inserisciBene(idUffOwn, codUtente, idprocedimento, beneForm);
		if(logger.isDebugEnabled()){
			logger.debug("Inserimento Bene identificativo : "+idBeneNuovo);
			logger.debug("Sostituzione Bene Vecchio : "+idBeneVecchio);
		}
		if(idBeneNuovo<=0){
			return 0;
		}
	
		Integer idTipoTrasformazione = Constants.TipiTrasformazione.ALTRO_TIPO;
		
		Character vidMotivo = null;
		beneDao.erroreMaterialeBene(idprocedimento, idBeneVecchio, idBeneNuovo, idUffOwn, codUtente, vidMotivo,idTipoTrasformazione ,idAtto);
		
		if(abilFascGest!=null && abilFascGest.equals("S") && idTipoFasc.equals(""+Constants.TIPO_FASCICOLO_MERITO)) { 
			//verifica fascicolo gestione -------
			HashMap fascGestione = commonService.getFascicoloGestione(idprocedimento, idUffOwn, codUtente);
			if(fascGestione!=null) {
				Long idProcGest = (Long) fascGestione.get("idProcGest");
				Long idUffRic = (Long) fascGestione.get("idUffRic");
				if(idProcGest!=null && idUffRic!=null && idProcGest>0 && idUffRic>0){			  
					try
					{
						long statoBene = 0;
						if(beneForm.getStatoConservazione()!=null && beneForm.getStatoConservazione().getId()!=null) {
							statoBene = Long.parseLong(beneForm.getStatoConservazione().getId());
						}
						beneDao.associaBeneAlProcedimento(idBeneNuovo, idUffRic.intValue(), codUtente, idProcGest, statoBene);
						
						beneDao.erroreMaterialeBene(idProcGest, idBeneVecchio, idBeneNuovo, idUffRic, codUtente, vidMotivo, idTipoTrasformazione ,idAtto);
					} catch(Exception e) {}
				}

			}
			//fine verifica fascicolo gestione -------
		}		
		return idBeneNuovo;
	}

	
	public TTpTipoMisura getTTpTipoMisuraById(long idTipoMisura) {
		return beneDao.getTTpTipoMisuraById(idTipoMisura);
	}

	
	public void modificaRelBeneTit(BeneSoggettoForm form,
			long idProcedimento, long idAtto, Integer idUfficioUtente,
			String codUtente, Boolean daProposta, double numeQuotaSeq,
			double numeFrazioneNumSeq, double numeFrazioneDenSeq, String tipoOperazione, String nomeMetodo) {
		
		beneDao.modificaRelBeneTit(form, idProcedimento, idAtto, idUfficioUtente, codUtente, daProposta, numeQuotaSeq, numeFrazioneNumSeq, numeFrazioneDenSeq, tipoOperazione, nomeMetodo);
		
	}

	public TRelProcBeneTit getRelazioniProcedimentoBeneTitolare(
			long idProcedimento, long idBene, int idUfficioUtente,
			int idTipoIntesse, long idSoggetto) {
		
		return beneDao.getRelazioniProcedimentoBeneTitolare(idProcedimento, idBene, idUfficioUtente, idTipoIntesse, idSoggetto);
	}

	public boolean existsSequestroRegistrato(final long idProcedimento, final long idBene, final int idUfficioUtente) {
		return beneDao.existsSequestroRegistrato(idProcedimento, idBene, idUfficioUtente);
	}

	public TTpTipoInteresse getInteresseById(int idTipoInteresse) {
	
		return beneDao.getInteresseById((long)idTipoInteresse);
	}

	public List<TRelProcBene> dettaglioRevocaRelProcBene(long idAttoRevoca,
			long idProcedimento, String descUtente, int idUffOwner,
			String nomeMetodo) {
		
		return beneDao.dettaglioRevocaRelProcBene(idAttoRevoca, idProcedimento, descUtente, idUffOwner, nomeMetodo);
	}

	public List<TBene> dettaglioRevocaTBene(long idAttoRevoca,
			long idProcedimento, String descUtente, int idUffOwner, String nomeMetodo, 			
			long idSoggettoDiretto, long idSoggettoTitolare, long idTipologia, long idCategoria, long idSottocategoria) {
		
		return beneDao.dettaglioRevocaTBene(idAttoRevoca, idProcedimento, descUtente, idUffOwner, nomeMetodo,
				 idSoggettoDiretto, idSoggettoTitolare, idTipologia, idCategoria, idSottocategoria);
	}

	public List<TRelBeneAtto> getListaRelBeneAtto(long idProcedimento,
			long idAtto, int idUfficioUtente, long idBene,
			long idSoggettoTitolare) {
		
		return beneDao.getListaRelBeneAtto(idProcedimento, idAtto, idUfficioUtente, idBene, idSoggettoTitolare);
	}

    public KeyMessageDto controllaDecrRestBeni(long vidprocedimento,long vidUffOwn,String descUtente){
    	
    	return beneDao.controllaDecrRestBeni(vidprocedimento,vidUffOwn,descUtente);
    } 	
    
    /**
	 * listaAmministratoriPerAtto
	 * 
	 * @param idUffOwn
	 * @param idAtto
	 * @return List<AmministratoreForm>
	 */
	public List<AmministratoreForm> getAmministratori(int idUffOwn, Long idProcedimento, Long idAtto, Long idAmministratore, boolean escludiAmministratore) {

		List queryResults = null;
		queryResults = beneDao.getAmministratori(idUffOwn, idProcedimento, idAtto, idAmministratore, escludiAmministratore);
		if ((queryResults == null) || queryResults.isEmpty()) {
			return null;
		}
		List<AmministratoreForm> risultati = new ArrayList<AmministratoreForm>();
		Iterator iterator = queryResults.iterator();
		long idAppoggio = 0;
		boolean scrivoilRecord = true;
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			TRelProcAttoAmm relProcAttoAmm = (TRelProcAttoAmm) row.get("rel");
			TAmministratori amministratore = (TAmministratori) row.get("amm");
			TAnComuni comuni = (TAnComuni) row.get("comuni");
			AmministratoreForm form = new AmministratoreForm();
		//Se ricerco gli amministratori per Procedimento devo riportare una sola volta l'amministratore con la minore delle date di presa in carico.
		//e siccome la select fa l'order by DATA_INIZIO_INCARICO asc, se ho già letto'id amministratore non lo riscrivo	
			scrivoilRecord = true;
			if(idAtto == null){
				if (amministratore.getCodiKIdAmmin() == idAppoggio){
					scrivoilRecord = false;
				}
				idAppoggio = amministratore.getCodiKIdAmmin();
			}
			form.setId(amministratore.getCodiKIdAmmin());
			form.setCognome(amministratore.getDescCognome());
			form.setNome(amministratore.getDescNome());
			if (amministratore.getDescPartitaIva()!=null){
				form.setCodiceFiscale(amministratore.getDescPartitaIva());	
			}
			if (amministratore.getDescCodiceFiscale()!=null){
				form.setCodiceFiscale(amministratore.getDescCodiceFiscale());	
			}
			form.setDescrIscrizioneAlbo(amministratore.getDescStatoIscrizioneAlbo());
			form.setDataIscrizioneAlbo(amministratore.getDataIscrizioneAlbo());
			if (amministratore.getDescComEsteroDl()!=null){
				form.setComuneDomicilioLegale(amministratore.getDescComEsteroDl());	
			}else{
				if(comuni!=null) {
					form.setComuneDomicilioLegale(comuni.getDescComune() + " ("+comuni.getTAnProvincia().getDescSiglaProvincia()+" )");
				}
			}
			form.setDataInizioIncarico(relProcAttoAmm.getDataInizioIncarico());
			form.setDataFineIncarico(relProcAttoAmm.getDataFineIncarico());
			if (scrivoilRecord) risultati.add(form);
		}
		return risultati;
	}
	
	public List<AmministratoreForm> getAmministratoriDaAssociare(int idUffOwn, Long idProcedimento, Long idAtto, AmministratoreForm amministratoreForm) {

		List queryResults = null;
		List<AmministratoreForm> risultati = new ArrayList<AmministratoreForm>();
		
		queryResults = beneDao.getAmministratoriDaAssociare(idUffOwn, idProcedimento, idAtto, amministratoreForm);
		
		if ((queryResults == null) || queryResults.isEmpty()) {
			return risultati;
		}
		
		Iterator iterator = queryResults.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			TAmministratori amministratore = (TAmministratori) row.get("amm");
			TAnComuni comuni = (TAnComuni) row.get("comuni");
			AmministratoreForm form = new AmministratoreForm();
			form.setId(amministratore.getCodiKIdAmmin());
			form.setCognome(amministratore.getDescCognome());
			form.setNome(amministratore.getDescNome());
			if (amministratore.getDescPartitaIva()!=null){
				form.setCodiceFiscale(amministratore.getDescPartitaIva());	
			}
			if (amministratore.getDescCodiceFiscale()!=null){
				form.setCodiceFiscale(amministratore.getDescCodiceFiscale());	
			}
			form.setDescrIscrizioneAlbo(amministratore.getDescStatoIscrizioneAlbo());
			form.setDataIscrizioneAlbo(amministratore.getDataIscrizioneAlbo());
			if (amministratore.getDescComEsteroDl()!=null){
				form.setComuneDomicilioLegale(amministratore.getDescComEsteroDl());	
			}else{
				if(comuni!=null) {
					String domicilioLegale = "" + comuni.getDescComune();
					if(comuni.getTAnProvincia()!=null) {
						domicilioLegale += " ("+comuni.getTAnProvincia().getDescSiglaProvincia()+")";
					}
					form.setComuneDomicilioLegale(domicilioLegale);
				}
			}
			risultati.add(form);
		}
		System.out.println("");
		return risultati;
	}
	
	public Long inserisciAmministratore(int idUffOwn, AmministratoreForm amministratoreForm, String codUtente, String nomeMetodo) {

		return new Long(beneDao.inserisciAmministratore(idUffOwn, amministratoreForm, codUtente, nomeMetodo));
		
	}
	
	public List<AmministratoreForm> getAmministratoreById(Long idAmministratore) {

		List queryResults = null;
		queryResults = beneDao.getAmministratoreById(idAmministratore);
		if ((queryResults == null) || queryResults.isEmpty()) {
			return null;
		}
		List<AmministratoreForm> risultati = new ArrayList<AmministratoreForm>();
		Iterator iterator = queryResults.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			TAmministratori amministratore = (TAmministratori) row.get("amm");
			TAnComuni comuni = (TAnComuni) row.get("comuni");
			TAnStato stato = (TAnStato) row.get("stato");
			AmministratoreForm form = new AmministratoreForm();
			form.setId(amministratore.getCodiKIdAmmin());
			form.setCognome(amministratore.getDescCognome());
			form.setNome(amministratore.getDescNome());
			form.setDataNascita(amministratore.getDataNascita());
			if (amministratore.getDescLuogoNascita()!=null){
				form.setLuogoNascita(amministratore.getDescLuogoNascita());	
			}
			if(stato!=null) { 
				form.setStatoDomProf(new ComboForm(String.valueOf(stato.getCodiKIdStato()), stato.getDescStato()));
			}
			if (amministratore.getDescComEsteroDl()!=null){
				form.setComuneDomProf(new ComboForm(null, amministratore.getDescComEsteroDl()));	
			}else{
				if(comuni!=null) {
					form.setComuneDomProf(new ComboForm(String.valueOf(comuni.getCodiKIdComune()), comuni.getDescComune()));
				}
			}
			if (amministratore.getDescPartitaIva()!=null){
				form.setPartitaIva(amministratore.getDescPartitaIva());	
			}
			form.setCodiceFiscale(amministratore.getDescCodiceFiscale());	
			form.setDescrIscrizioneAlbo(amministratore.getDescStatoIscrizioneAlbo());
			form.setDataIscrizioneAlbo(amministratore.getDataIscrizioneAlbo());
			form.setDescStudio(amministratore.getDescStudio());
			form.setPec(amministratore.getDescPec());
			form.setTel(amministratore.getDescTel());
			form.setFax(amministratore.getDescFax());
			if (amministratore.getDescComEsteroDl()!=null){
				form.setComuneDomicilioLegale(amministratore.getDescComEsteroDl());	
			}else{
				if(comuni!=null) {
					form.setComuneDomicilioLegale(comuni.getDescComune() + " ("+comuni.getTAnProvincia().getDescSiglaProvincia()+" )");
				}
			}
			risultati.add(form);
		}
		return risultati;
	}
	
	public Long inserisciRelAmministratoreAtto(int idUffOwn, AmministratoreForm amministratoreForm, AttoForm attoForm, String codUtente, String nomeMetodo) {

		return new Long(beneDao.inserisciRelAmministratoreAtto(idUffOwn, amministratoreForm, attoForm, codUtente, nomeMetodo));
		
	}
	
	public Long inserisciRelAmministratoreBeneAtto(int idUffOwn, long idAmministratore, AttoForm attoForm, long idBene, String codUtente, String nomeMetodo) {

		return new Long(beneDao.inserisciRelAmministratoreBeneAtto(idUffOwn, idAmministratore, attoForm, idBene, codUtente, nomeMetodo));
		
	}
	
	// P.Q. MEV 2020/48 Inizio
	
	public Long inserisciRelAmministratoreAttoTuttiParametri(int idUffOwn, long idAmministratore, long idAtto, long idProcedimento, Date dataInizioIncario, String codUtente, String nomeMetodo) {
		return new Long(beneDao.inserisciRelAmministratoreAttoTuttiParametri(idUffOwn, idAmministratore, idAtto, idProcedimento, dataInizioIncario, codUtente, nomeMetodo));
	}	
	
	public Long inserisciRelAmministratoreBeneAttoTuttiParametri(int idUffOwn, long idAmministratore, long idAtto, long idProcedimento, Date dataInizioIncario, long idBene, String codUtente, String nomeMetodo) {
		return new Long(beneDao.inserisciRelAmministratoreBeneAttoTuttiParametri(idUffOwn, idAmministratore, idAtto, idProcedimento, dataInizioIncario, idBene, codUtente, nomeMetodo));
	}	
	// P.Q. MEV 2020/48 Fine
	
	public void eliminaRelAmministratoreBeneAtto(int idUffOwn, long idAmministratore, AttoForm attoForm, long idBene) {

		beneDao.eliminaRelAmministratoreBeneAtto(idUffOwn, idAmministratore, attoForm, idBene);
		
	}
	
	public void eliminaRelAmministratoreBeneAtto(int idUffOwn, long idAmministratore, AttoForm attoForm) {

		beneDao.eliminaRelAmministratoreBeneAtto(idUffOwn, idAmministratore, attoForm, 0);
		
	}	
	
	public void eliminaRelAmministratoreAtto(int idUffOwn, long idAmministratore, AttoForm attoForm) {

		beneDao.eliminaRelAmministratoreAtto(idUffOwn, idAmministratore, attoForm);
		
	}

	public List<BeneForm> listaBeniPerProcedimentoNoAttoPerProrogaSequestro(long idAtto, long idProcedimento, String descUtente, int idUffOwner, String nomeMetodo) {

		List queryResults = beneDao.dettaglioProrogaSequestroTBene(idAtto, idProcedimento, descUtente, idUffOwner, nomeMetodo);
		if ((queryResults == null) || queryResults.isEmpty()) {
			return null;
		}
		List<BeneForm> risultati = new ArrayList<BeneForm>();
		Iterator iterator = queryResults.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			TBene bene = (TBene) row.get("bene");
			BeneForm form = new BeneForm();
			if (bene != null) {
				mapperBean.map(bene, form);
			}
			risultati.add(form);
		}
		return risultati;
	}
	
	public List<BeneForm> listaBeniInAmministrazione(int idUffOwn, long idProcedimento, Long idAtto, Long idAmministratore) {

		List queryResults = null;
		
		queryResults = beneDao.listaBeniInAmministrazione(idUffOwn, idProcedimento, idAtto, idAmministratore);
		
		List<BeneForm> risultati = new ArrayList<BeneForm>();
		if ((queryResults == null) || queryResults.isEmpty()) {
			return risultati;
		}
		Iterator iterator = queryResults.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			TBene bene = (TBene) row.get("bene");
			TRelProcBeneAttoAmm rel = (TRelProcBeneAttoAmm) row.get("rel");
			BeneForm form = new BeneForm();
			if (bene != null) {
				mapperBean.map(bene, form);
				form.setDataInizioIncarico(rel.getDataInizioIncarico());
				form.setDataFineIncarico(rel.getDataFineIncarico());
			}
			risultati.add(form);
		}
		
		return risultati;
	}
	
	public List<BeneForm> listaBeniPerProcedimentoNoAttoPerSequestroSospensioneTemporanea(long idAtto, long idProcedimento, String descUtente, int idUffOwner, String nomeMetodo) {

		List queryResults = beneDao.dettaglioSequestroSospensioneTBene(idAtto, idProcedimento, descUtente, idUffOwner, nomeMetodo);
		if ((queryResults == null) || queryResults.isEmpty()) {
			return null;
		}
		List<BeneForm> risultati = new ArrayList<BeneForm>();
		Iterator iterator = queryResults.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			TBene bene = (TBene) row.get("bene");
			BeneForm form = new BeneForm();
			if (bene != null) {
				mapperBean.map(bene, form);
			}
			risultati.add(form);
		}
		return risultati;
	}	
	
	public boolean isDefinito(BeneForm bene){

		return beneDao.isDefinito(bene);
		
	}
	
	public List<BeneForm> listaBeneCorrezioneErroreMateriale(long idAtto, long idProcedimento, String descUtente, int idUffOwner, String nomeMetodo) {	

		List<BeneForm> risultati = new ArrayList<BeneForm>();
		TRelBeneBene relBeneBene=beneDao.getTRelBeneBenebyIdAtto(idAtto,idProcedimento,idUffOwner);
		
		if (relBeneBene!=null) {
		long idBene1=relBeneBene.getId().getCodiKIdBene1();
		long idBene2=relBeneBene.getId().getCodiKIdBene2();
		
		BeneForm beneForm=beneById(idUffOwner, idBene1, idProcedimento);
		
		BeneForm beneForm2=beneById(idUffOwner, idBene2, idProcedimento);
		
		beneForm.setDescrizioneBene2(beneForm2.getDescrizioneBene());
		
		beneForm.setIdBene2(idBene2);
		
		
		
         risultati.add(beneForm);
		}
		
		return risultati;
	}	
	
	public String isBeneInIstanzaCamerale(long idProc, int idUffOwn, long idBene) {	
		
		List<Map> result = beneDao.isBeneInIstanzaCamerale(idProc, idUffOwn, idBene);
		if(result!=null && result.size()>0) return "SÌ";
		else return "NO";
	}
	
	
	public List listaBeniNominaAmministratore(int filtro, int idUffOwn, long idProcedimento, BeneForm beneForm, long vidDecreto, Long idAmministratore, String Amministratore) {

		Long idBene = null;
		Long idTipologia = null;
		Long idCategoria = null;
		Long idSottocategoria = null;
		List queryResults = null;
		
		if(beneForm!=null) {
			if ((beneForm.getIdBene() != null) && !beneForm.getIdBene().toString().equals("-1")) {
				idBene = beneForm.getIdBene();
			}
			if ((beneForm.getTipologia() != null) && (beneForm.getTipologia().getId() != null) && !beneForm.getTipologia().getId().equals("-1")) {
				idTipologia = Long.parseLong(beneForm.getTipologia().getId());
			}
			if ((beneForm.getCategoria() != null) && (beneForm.getCategoria().getId() != null) && !beneForm.getCategoria().getId().equals("-1")) {
				idCategoria = Long.parseLong(beneForm.getCategoria().getId());
			}
			if ((beneForm.getSottocategoria() != null) && (beneForm.getSottocategoria().getId() != null) && !beneForm.getSottocategoria().getId().equals("-1")) {
				idSottocategoria = Long.parseLong(beneForm.getSottocategoria().getId());
			}
		}
		
		queryResults = beneDao.listaBeniNominaAmministratore(filtro, idUffOwn, idProcedimento, idBene, idTipologia, idCategoria, idSottocategoria, vidDecreto, idAmministratore, Amministratore);

		if ((queryResults == null) || queryResults.isEmpty()) {
			return null;
		}
		List<BeneForm> risultati = new ArrayList<BeneForm>();
		Iterator iterator = queryResults.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			TBene bene = (TBene) row.get("bene");
			BeneForm form = new BeneForm();
			if (bene != null) {
				mapperBean.map(bene, form);
			}
			risultati.add(form);
		}
		return risultati;
	}	

	public List<BeneForm> listaBeniProcedimentoFiltrata(long idProcedimento, long idUffOwn, long idAtto, 
    		long idSoggettoDiretto, long idSoggettoTitolare, long idTipologia, long idCategoria, long idSottocategoria) {

		List<BeneForm> queryResults = beneDao.listaBeniProcedimentoFiltrata(idProcedimento, idUffOwn, idAtto,
	    								idSoggettoDiretto, idSoggettoTitolare, idTipologia, idCategoria, idSottocategoria);
		if ((queryResults == null) || queryResults.isEmpty()) {
			return null;
		}
		List<BeneForm> risultati = new ArrayList<BeneForm>();
		Iterator iterator = queryResults.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			TBene bene = (TBene) row.get("bene");
			BeneForm form = new BeneForm();
			if (bene != null) {
				mapperBean.map(bene, form);
			}
			risultati.add(form);
		}
		return risultati;
	}

	
	
	
	public List<Long> getListaIdBeniAssociatiAdAttoEProcedimento(long idProcedimento, long idAtto) {
		return beneDao.getListaIdBeniAssociatiAdAttoEProcedimento(idProcedimento, idAtto);
	}
	
	public TAnAltriOper getTAnAltriOper(Long id) {
		return beneDao.getTAnAltriOper(id);
	}

	public TAnAltriOper getTAnAltriOperByCodFiscale(String codiceFiscale) {
		return beneDao.getTAnAltriOperByCodFiscale(codiceFiscale);
	}
	
	public TAnAltriOper getTAnAltriOperByDescrizione(String descrizione) {
		return beneDao.getTAnAltriOperByDescrizione(descrizione);
	}

	public void modificaBenePolizza(long idUffOwn, String codUtente, long idprocedimento, long statoBene, BeneForm beneForm, TBene bene) {
		TBeneFin finanziario = new TBeneFin();
		mapperBean.map(beneForm, finanziario);
		if(beneForm.getNumeroPolizza() != null) {
			finanziario.setDescRegistrazione(beneForm.getNumeroPolizza());
		}
		beneDao.modificaFinanziario(idUffOwn, codUtente, idprocedimento, statoBene, bene, finanziario);
	}

	public List<SchedaAttiAmmDto> getAttoAmministratoreDelBene(long idBene, long idProcedimento, int idUfficioUtente,String categoriaAtto) {
	
		List<Object[]> queryResults = beneDao.getAttoAmministratoreDelBene(idBene, idProcedimento, idUfficioUtente, categoriaAtto);
		if ((queryResults == null) || queryResults.isEmpty()) {
			return null;
		}

		List<SchedaAttiAmmDto> risultati = new ArrayList<SchedaAttiAmmDto>();
		Iterator<Object[]> iter = queryResults.iterator();
		while (iter.hasNext()) {
			SchedaAttiAmmDto scheda = new SchedaAttiAmmDto();
			Object[] current = (Object[]) iter.next();
			TRelProcBeneAttoAmm rel = (TRelProcBeneAttoAmm) current[0];
			TAtto atto = (TAtto) current[1];
			TIstanza istanza = (TIstanza) current[2];
			TAmministratori ammin = (TAmministratori) current[3];
			mapperBean.map(rel, scheda);
			mapperBean.map(atto, scheda);
			mapperBean.map(istanza, scheda);
			mapperBean.map(ammin, scheda);
			risultati.add(scheda);
		}
		return risultati;
	}
	
	public TRelBeneBene getTRelBeneBeneByIdAtto(long idAtto){
		return beneDao.getTRelBeneBeneByIdAtto(idAtto);
	}	

	public TDepositiAmm getDepositoAmmByIdAtto(long idAtto, int idUffOwner, long idProcedimento) {
		TDepositiAmm tDepositoAmm = beneDao.getDepositoAmmByIdAtto(idAtto, idUffOwner, idProcedimento);
		if (tDepositoAmm != null)
			return tDepositoAmm;
		else
			return null;
	}

	public String getAmministratoreBeneAtto(long idProcedimento, long idAtto, int idUffOwner, long idBene) {
		return beneDao.getAmministratoreBeneAtto(idProcedimento, idAtto, idUffOwner, idBene);
	}
	
	public List<BeneForm> verificaImmobileGraffato(long idBene, long idProcedimento, int idUffOwner){
		List<TBeneImmobile> tBeneImmobileList = beneDao.verificaImmobileGraffato(idProcedimento, idUffOwner);
		if(tBeneImmobileList == null)
			return null;
		Iterator<TBeneImmobile> iterator = tBeneImmobileList.iterator();
		List<BeneForm> result = new ArrayList<BeneForm>();
		while (iterator.hasNext()) {	
			TBeneImmobile beneImmobile=iterator.next();
			TBene bene = beneDao.getTBeneById(beneImmobile.getId().getCodiKIdBeneImm(), idUffOwner);
			BeneForm form = new BeneForm();
			if (bene != null) {
				mapperBean.map(bene, form);
			}
			if(beneImmobile.getId().getCodiKIdBeneImm() != idBene)
				result.add(form);
		}
		return result;
	}
	
	public void inserisciGraffato(long idBene1, long idBene2, int idUffOwner){
		beneDao.inserisciGraffato(idBene1, idBene2, idUffOwner);
	}
	
	public BeneForm esisteGraffato(long idBene, int idUffOwner){
		TBeneImmobile beneImmobile = beneDao.esisteGraffato(idBene, idUffOwner);
		if(beneImmobile == null)
			return null;
		TBene bene = beneDao.getTBeneById(beneImmobile.getId().getCodiKIdBeneImm(), idUffOwner);
		BeneForm form = new BeneForm();
		mapperBean.map(bene, form);
		return form;
	}
	
	public void eliminaGraffato(long idBene1, long idBene2, int idUffOwner){
		beneDao.eliminaGraffato(idBene1, idBene2, idUffOwner);
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
	public String getDenaro(Long idBene, Integer idUffOwner) {

		List<Map> l = beneDao.getDenaro(idBene, idUffOwner);
		if (l.size() > 0)
			return (String) l.get(0).get("desc_abi");
		return null;
	}
	//***** FINE INTERVENTO MEV_10 *****//	

	// P.Q. Mev 2020/48 Inizio
	@Override
	public List<TRelProcAttoAmm> getAmministratoriCopertina(int idUffOwn, Long idProcedimento, Long idAtto, Long idAmministratore, boolean escludiAgenzia) {
		return beneDao.getAmministratoriCopertina(idUffOwn, idProcedimento, idAtto, idAmministratore, escludiAgenzia);
	}

	@Override
	public List<TRelProcBeneAttoAmm> getAmministratoriBeni(int idUffOwn, Long idProcedimento, Long idAtto, Long idAmministratore, boolean escludiAgenzia, Boolean escludiAtto) {
		return beneDao.getAmministratoriBeni(idUffOwn, idProcedimento, idAtto, idAmministratore, escludiAgenzia, escludiAtto);
	}
	// P.Q. Mev 2020/48 Fine
}