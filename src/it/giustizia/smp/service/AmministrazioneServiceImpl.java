package it.giustizia.smp.service;

import it.giustizia.smp.dao.AmministrazioneDao;
import it.giustizia.smp.dao.CommonDao;
import it.giustizia.smp.form.CategoriaCatastaleForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.ComuneForm;
import it.giustizia.smp.form.CondizioneGiuridicaForm;
import it.giustizia.smp.form.ConservatoriaForm;
import it.giustizia.smp.form.IstitutoDetenzioneForm;
import it.giustizia.smp.form.MagistratoForm;
import it.giustizia.smp.form.ProfessioneForm;
import it.giustizia.smp.form.ProvinciaForm;
import it.giustizia.smp.form.SettoreAttivitaForm;
import it.giustizia.smp.form.SpeseProcessualiForm;
import it.giustizia.smp.form.StatoForm;
import it.giustizia.smp.form.UfficiForm;
import it.giustizia.smp.model.TAnComuni;
import it.giustizia.smp.model.TAnConservatorie;
import it.giustizia.smp.model.TAnDistretto;
import it.giustizia.smp.model.TAnIstitutoDet;
import it.giustizia.smp.model.TAnMagistrati;
import it.giustizia.smp.model.TAnProvincia;
import it.giustizia.smp.model.TAnStato;
import it.giustizia.smp.model.TAnUfficio;
import it.giustizia.smp.model.TProcedimento;
import it.giustizia.smp.model.TRelMagProc;
import it.giustizia.smp.model.TTpCatCatasto;
import it.giustizia.smp.model.TTpCondGiur;
import it.giustizia.smp.model.TTpProfessione;
import it.giustizia.smp.model.TTpSettoreAtt;
import it.giustizia.smp.model.TTpSpeseProcessuali;
import it.giustizia.smp.model.TTpTipoIstituto;
import it.giustizia.smp.model.TTpTipoUfficio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

public class AmministrazioneServiceImpl implements AmministrazioneService {
	
	@Autowired
	private AmministrazioneDao amministrazioneDao;
	@Autowired
	private CommonDao commonDao;
	@Autowired
	private Mapper mapperBean;
	
	public void associaMagistrato(long codiKIdMagistrato, int idUffOwn,	String descUtente) {
		amministrazioneDao.associaMagistrato(codiKIdMagistrato, idUffOwn, descUtente);	
	}
	
	public void setAmministrazioneDao(AmministrazioneDao amministrazioneDao) {
		this.amministrazioneDao = amministrazioneDao;
	}

	public List<MagistratoForm> listaMagistrati(String cognome, String nome, Date dataNascita, Integer matricola) {
		List queryResults = null;
		queryResults = amministrazioneDao.listaMagistrati(cognome, nome, dataNascita, matricola);
		if ((queryResults == null) || queryResults.isEmpty()) {
			return null;
		}
		List<MagistratoForm> risultati = new ArrayList<MagistratoForm>();
		Iterator iterator = queryResults.iterator();
		while (iterator.hasNext()) {
			TAnMagistrati row = (TAnMagistrati) iterator.next();
			MagistratoForm form = new MagistratoForm();
			mapperBean.map(row, form);
			risultati.add(form);
		}
		return risultati;
	}

	public MagistratoForm getMagistrato(long id) {
		MagistratoForm form = new MagistratoForm();
		TAnMagistrati row = amministrazioneDao.getMagistrato(id);
		mapperBean.map(row, form);
		return form;
	}
	
	public void inserisciMagistrato(String cognome, String nome, Date dataNascita, Integer matricola, String descUtente) {
		amministrazioneDao.inserisciMagistrato(cognome, nome, dataNascita, matricola, descUtente);
	}
	
	public void modificaMagistrato(MagistratoForm magistratoForm) {
		TAnMagistrati magistratoModel = new TAnMagistrati();
		mapperBean.map(magistratoForm, magistratoModel);
		amministrazioneDao.modificaMagistrato(magistratoModel);
	}
	
//	public void eliminaMagistrato(long codiKIdMagistrato) {
//		amministrazioneDao.eliminaMagistrato(codiKIdMagistrato);
//	}
	
	
	public List<MagistratoForm> listaMagistratiNonAssociati(int idUffOwn, String cognome, String nome, Integer matricola) {
		List queryResults = null;
		queryResults = amministrazioneDao.listaMagistratiNonAssociati(idUffOwn,cognome, nome, matricola);
		if ((queryResults == null) || queryResults.isEmpty()) {
			return null;
		}
		List<MagistratoForm> risultati = new ArrayList<MagistratoForm>();
		Iterator iterator = queryResults.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
//			TRelMagProc relProcedimento = (TRelMagProc) row.get("rel");
			TAnMagistrati magistrato = (TAnMagistrati) row.get("mag");
			MagistratoForm form = new MagistratoForm();
			mapperBean.map(magistrato, form);
			risultati.add(form);
		}
		return risultati;
	}
	
	public List<ComboForm> listaMagistratiSostituzione(String tipoMagistrato, int idUffOwn, int idTipoUfficio, Date dataFineAssegnazione) {
		List<TAnMagistrati> queryResults = null;
		queryResults = amministrazioneDao.listaMagistratiSostituzione(tipoMagistrato, idUffOwn, idTipoUfficio, dataFineAssegnazione);
		if ((queryResults == null) || queryResults.isEmpty()) {
			return null;
		}
		List<ComboForm> risultati = new ArrayList<ComboForm>();
		Iterator iterator = queryResults.iterator();
		while (iterator.hasNext()) {
			TAnMagistrati magistrato = (TAnMagistrati)iterator.next();
			ComboForm combo = new ComboForm();
			combo.setId(String.valueOf(magistrato.getCodiKIdMagistrato()));
			combo.setDescrizione(magistrato.getDescCognomeMag() + " " + magistrato.getDescNomeMag()); 
			risultati.add(combo);
		}
		return risultati;
	}
	
	public List<ComboForm> listaMagistratiSubentro(int idUffOwn) {
		List queryResults = null;
		queryResults = amministrazioneDao.listaMagistratiSubentro(idUffOwn);
		if ((queryResults == null) || queryResults.isEmpty()) {
			return null;
		}
		List<ComboForm> risultati = new ArrayList<ComboForm>();
		Iterator iterator = queryResults.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
//			TRelMagProc relProcedimento = (TRelMagProc) row.get("rel");
			TAnMagistrati magistrato = (TAnMagistrati) row.get("mag");
//			TAnMagistrati magistrato = (TAnMagistrati)iterator.next();
			ComboForm combo = new ComboForm();
			combo.setId(String.valueOf(magistrato.getCodiKIdMagistrato()));
			combo.setDescrizione(magistrato.getDescCognomeMag() + " " + magistrato.getDescNomeMag()); 
			risultati.add(combo);
		}
		return risultati;
		
		
		/*List<TAnMagistrati> queryResults = null;
		queryResults = amministrazioneDao.listaMagistratiSubentro(idUffOwn);
		if ((queryResults == null) || queryResults.isEmpty()) {
			return null;
		}
		List<ComboForm> risultati = new ArrayList<ComboForm>();
		Iterator iterator = queryResults.iterator();
		while (iterator.hasNext()) {
			TAnMagistrati magistrato = (TAnMagistrati)iterator.next();
			ComboForm combo = new ComboForm();
			combo.setId(String.valueOf(magistrato.getCodiKIdMagistrato()));
			combo.setDescrizione(magistrato.getDescCognomeMag() + " " + magistrato.getDescNomeMag()); 
			risultati.add(combo);
		}
		return risultati;*/
	}
	
	public List<TRelMagProc> listaProcedimentiSostituzione(String tipoMagistrato, long idMagistratoDaSostituire, int idUffOwn, int idTipoUfficio, Date dataFineAssegnazione, Short annoDa, Long numDa, Short annoA, Long numA) {
		List<TRelMagProc> queryResults = null;
		queryResults = amministrazioneDao.listaProcedimentiSostituzione(tipoMagistrato, idMagistratoDaSostituire, idUffOwn, idTipoUfficio, dataFineAssegnazione, annoDa, numDa, annoA, numA);
		if ((queryResults == null) || queryResults.isEmpty()) {
			return null;
		} else return queryResults;
	}	

	public List<StatoForm> listaStati(String descStato, String descSiglaStato,String descIstatStato) {
		List queryResults = null;
		queryResults = amministrazioneDao.listaStati(descStato, descSiglaStato, descIstatStato);
		if ((queryResults == null) || queryResults.isEmpty()) {
			return null;
		}
		List<StatoForm> risultati = new ArrayList<StatoForm>();
		Iterator iterator = queryResults.iterator();
		while (iterator.hasNext()) {
			TAnStato row = (TAnStato) iterator.next();
			StatoForm form = new StatoForm();
			mapperBean.map(row, form);
			risultati.add(form);
		}
		return risultati;
	}

	public StatoForm getStato(int codice) {
		StatoForm form = new StatoForm();
		TAnStato row = amministrazioneDao.getStato(codice);
		mapperBean.map(row, form);
		return form;
	}

	public void modificaStato(StatoForm statoForm) {
		amministrazioneDao.modificaStato(statoForm);
	}
	
	public Integer inserisciStato(String descIstatStato,String descSiglaStato, String descStato, Date dataFineValidita, String descUtente) {
		return amministrazioneDao.inserisciStato(descIstatStato, descSiglaStato, descStato, dataFineValidita, descUtente);
		
	}
	
	public List<TRelMagProc> listaProcedimentiDettaglioMagistrato(String tipoMagistrato, long idMagistratoDaSostituire, int idUffOwn, int idTipoUfficio, Date dataFineAssegnazione) {
		List<TRelMagProc> queryResults = null;
		queryResults = amministrazioneDao.listaProcedimentiSostituzione(tipoMagistrato, idMagistratoDaSostituire, idUffOwn, idTipoUfficio, dataFineAssegnazione, null, null, null, null);
		if ((queryResults == null) || queryResults.isEmpty()) {
			return null;
		} else return queryResults;
	}
	
	public TProcedimento sostituisciMagistrato(MagistratoForm magistratoForm, int idUffOwn, long idProcedimento, String descUtente) {
		
		Date dataFineAssegnazione = magistratoForm.getDataFineAssegnazione();
		String tipoMagistrato = magistratoForm.getTipoMagistrato();
		long idMagistratoSubentrante = Long.valueOf(magistratoForm.getMagistratoSubentrante().getId());
		long idMagistratoSostituito = Long.valueOf(magistratoForm.getMagistratoDaSostituire().getId());
		String motivo = magistratoForm.getMotivazioneSostituzione().getId();
		
		List<TRelMagProc> controllo = amministrazioneDao.controllaAssegnazioneMagistratoProcedimento(idMagistratoSubentrante, idUffOwn, idProcedimento, dataFineAssegnazione);
		if(controllo!=null && controllo.size()>0) {
			TRelMagProc magProc = controllo.get(0);
			return magProc.getTProcedimento();
		} else {
			amministrazioneDao.aggiornaSostituzioneMagistrato(tipoMagistrato, idMagistratoSostituito, idMagistratoSubentrante, motivo, idUffOwn, idProcedimento, dataFineAssegnazione, descUtente);
			return null;
		}

	}
	
	public List<ProvinciaForm> listaProvince(String descProvincia, String descSiglaProvincia) {
		List queryResults = null;
		queryResults = amministrazioneDao.listaProvince(descProvincia, descSiglaProvincia);
		if ((queryResults == null) || queryResults.isEmpty()) {
			return null;
		}
		List<ProvinciaForm> risultati = new ArrayList<ProvinciaForm>();
		Iterator iterator = queryResults.iterator();
		while (iterator.hasNext()) {
			TAnProvincia model = (TAnProvincia) iterator.next();
			ProvinciaForm form = new ProvinciaForm();
			form.setCodiKIdIstatProv(model.getCodiKIdIstatProv());
			form.setDescProvincia(model.getDescProvincia()== null ? null : model.getDescProvincia().toUpperCase());
			form.setDescSiglaProvincia(model.getDescSiglaProvincia()== null ? null : model.getDescSiglaProvincia().toUpperCase());
			form.setDataFineValidita(model.getDataFineValidita());
			risultati.add(form);
		}
		return risultati;
	}

	public Integer inserisciProvincia(String codIstatProvincia, String descSiglaProvincia, String descProvincia, Date dataFineValidita, String descUtente) {
		
		return amministrazioneDao.inserisciProvincia(codIstatProvincia, descSiglaProvincia, descProvincia, dataFineValidita, descUtente);
	}

	public ProvinciaForm getProvincia(String codice) {
		ProvinciaForm form = new ProvinciaForm();
		TAnProvincia row = amministrazioneDao.getProvincia(codice);
		mapperBean.map(row, form);
		return form;
	}

	public void modificaProvincia(ProvinciaForm provinciaForm) {
		amministrazioneDao.modificaProvincia(provinciaForm);
		
	}

	public List<ComuneForm> listaComuni(String descComune, String descCapComune, String descIstatComune, String descCodBelfiore, String descCodCatasto) {
		List queryResults = null;
		queryResults = amministrazioneDao.listaComuni(descComune, descCapComune, descIstatComune, descCodBelfiore, descCodCatasto);
		if ((queryResults == null) || queryResults.isEmpty()) {
			return null;
		}
		List<ComuneForm> risultati = new ArrayList<ComuneForm>();
		Iterator iterator = queryResults.iterator();
		while (iterator.hasNext()) {
			TAnComuni model = (TAnComuni) iterator.next();
			ComuneForm form = new ComuneForm();
			form.setCodiKIdComune(model.getCodiKIdComune());
			form.setItemProvincia(new ComboForm(model.getTAnProvincia().getCodiKIdIstatProv(), ""));
			form.setDescComune(model.getDescComune() == null ? null : model.getDescComune().toUpperCase());
			form.setDescCapComune(model.getDescCapComune() == null ? null : model.getDescCapComune().toUpperCase());
			form.setDescIstatComune(model.getDescIstatComune()  == null ? null : model.getDescIstatComune().toUpperCase());
			form.setDescBelfiore(model.getDescBelfiore()  == null ? null : model.getDescBelfiore().toUpperCase());
			form.setDescCatasto(model.getDescCatasto()  == null ? null : model.getDescCatasto().toUpperCase());
			form.setDataFineValidita(model.getDataFineValidita());
			risultati.add(form);
		}
		return risultati;
	}

	public ComuneForm getComune(int codiceComune) {
		ComuneForm form = new ComuneForm();
		TAnComuni row = amministrazioneDao.getComune(codiceComune);
		form.setCodiKIdComune(row.getCodiKIdComune());
		form.setItemProvincia(new ComboForm(row.getTAnProvincia().getCodiKIdIstatProv(), row.getTAnProvincia().getDescProvincia()));
		form.setDescComune(row.getDescComune());
		form.setDescBelfiore(row.getDescBelfiore());
		form.setDescCapComune(row.getDescCapComune());
		form.setDescCatasto(row.getDescCatasto());
		form.setDescIstatComune(row.getDescIstatComune());
		form.setDataFineValidita(row.getDataFineValidita());
		return form;
	}

	public Integer inserisciComune(String codiFkIdIstatProvincia, String descIstatComune, String descComune, String descCapComune, String descBelfiore, String descCatasto, Date dataFineValidita, String descUtente) {
		
		return amministrazioneDao.inserisciComune(codiFkIdIstatProvincia, descIstatComune, descComune, descCapComune, descBelfiore, descCatasto, dataFineValidita, descUtente);
	}


	public void modificaComune(ComuneForm comuneForm) {
		amministrazioneDao.modificaComune(comuneForm);
		
	}

	public List<UfficiForm> listaUffici(int idTipoUfficio, int idComune) {
			
		List queryResults = null;
		queryResults = amministrazioneDao.listaUffici(idTipoUfficio, idComune);
		if ((queryResults == null) || queryResults.isEmpty()) {
			return null;
		}
		List<UfficiForm> risultati = new ArrayList<UfficiForm>();
		Iterator iterator = queryResults.iterator();
		while (iterator.hasNext()) {
			TAnUfficio model = (TAnUfficio) iterator.next();
			UfficiForm form = new UfficiForm();
			form.setCodiKIdUfficio(model.getCodiKIdUfficio());
			form.setDescUfficio(model.getDescUfficio());
			form.setDescIndirizzoSede(model.getDescIndirizzoSede());
			
			if(model.getFlgTribMp() != null){
			form.setFlgTribMp(model.getFlgTribMp().toString());
			}
			
			if(model.getFlgDda() != null){
			form.setFlgDda(model.getFlgDda().toString());
			}
			
			form.setDataFineValidita(model.getDataFineValidita());
			
			risultati.add(form);
		}
		return risultati;
	}
	
	public UfficiForm getUfficio(int codiceUfficio) {
		UfficiForm form = new UfficiForm();
		TAnUfficio row = amministrazioneDao.getUfficio(codiceUfficio);
		
		form.setCodiKIdUfficio(row.getCodiKIdUfficio());
		form.setDescUfficio(row.getDescUfficio());
		form.setDescIndirizzoSede(row.getDescIndirizzoSede());
		form.setDescUffGiudiz(row.getDescUffGiudiz());
		
		form.setItemTipoUfficio(new ComboForm(String.valueOf(row.getTTpTipoUfficio().getCodiKIdTipoUff()), row.getTTpTipoUfficio().getDescTipoUff()));
		
		if(row.getTAnComuni() != null &&  row.getTAnComuni().getCodiKIdComune() > 0){
			form.setItemComuneSede(new ComboForm(String.valueOf(row.getTAnComuni().getCodiKIdComune()), row.getTAnComuni().getDescComune()));
		}
		
		if(row.getTAnDistretto() != null &&  row.getTAnDistretto().getCodiKIdDistretto() > 0){
			form.setItemDistretto(new ComboForm(String.valueOf(row.getTAnDistretto().getCodiKIdDistretto()), row.getTAnDistretto().getDescDistretto()));
		}
		
		if(row.getTAnUfficio() != null &&  row.getTAnUfficio().getCodiKIdUfficio() > 0){
			form.setItemTribunale(new ComboForm(String.valueOf(row.getTAnUfficio().getCodiKIdUfficio()), row.getTAnUfficio().getDescUfficio()));
		}
		
		if(row.getFlgTribMp() != null){
			form.setFlgTribMp(row.getFlgTribMp().toString());
		}
		if(row.getFlgDda() != null){
			form.setFlgDda(row.getFlgDda().toString());
		}
		if(row.getFlgPatrQuestura() != null){
			form.setFlgMP(row.getFlgPatrQuestura().toString());
		}
		if(row.getFlgAutorizzaDna() != null){
			form.setFlgDNA(row.getFlgAutorizzaDna().toString());
		}
		
		form.setDataFineValidita(row.getDataFineValidita());

		return form;
	}

	public Integer inserisciUfficio(int idTipoUfficio, int idComune, String descIndirizzoSede, int idDistretto, String flagTribMp, int idUfficioTribunale, String flagDda, Date dataFineValidita, String descUtente ) {

		int idTpUff = idTipoUfficio;
		int idCom = idComune;
		int idDist = idDistretto;

		int idTrib = idUfficioTribunale;
		
		TTpTipoUfficio tpUff = null;  
		TAnComuni tCom = null;
		TAnDistretto tDistr = null;
		Character flagTrib = null;
		Character flagDDA = null;
		
		if(idTpUff > 0){
			tpUff =  commonDao.getTipoUfficioById(idTpUff);			
		} else {
			//ne creo uno base per scrivere null senza generare eccezione
			tpUff =  new TTpTipoUfficio();
		}
		
		if(idCom > 0){
			tCom = commonDao.getComuneById(idCom);			
		} else {
			//ne creo uno base per scrivere null senza generare eccezione
			tCom =  new TAnComuni();
		}

		if(idDist > 0){
			tDistr = commonDao.getDistrettoById(idDist);			
		} else {
			//ne creo uno base per scrivere null senza generare eccezione
			tDistr =  new TAnDistretto();
		}
		
		if(flagTribMp != null && flagTribMp.equals("S")){
			flagTrib = new Character(flagTribMp.charAt(0));
		}
		
		if(flagDda != null && flagDda.equals("S")){
			flagDDA = new Character(flagDda.charAt(0));
		}

		return amministrazioneDao.inserisciUfficio(tpUff.getCodiKIdTipoUff(), tCom.getCodiKIdComune(), tpUff.getDescTipoUff(), tCom.getDescComune(), descIndirizzoSede, tDistr.getCodiKIdDistretto(), tDistr.getDescDistretto(), flagTrib, idTrib, flagDDA, dataFineValidita, descUtente);
	}

	public Integer inserisciUfficio(UfficiForm ufficiForm, String descUtente ) {

		int idTpUff = 0;
		int idCom = 0;
		
		if(ufficiForm.getItemTipoUfficio() != null && !ufficiForm.getItemTipoUfficio().getId().equals("-1")){
			idTpUff = new Integer(ufficiForm.getItemTipoUfficio().getId());
		}
		
		if(ufficiForm.getItemComuneSede() != null && !ufficiForm.getItemComuneSede().getId().equals("-1")){
			idCom = new Integer(ufficiForm.getItemComuneSede().getId());
		}
		
		TTpTipoUfficio tpUff = null;  
		TAnComuni tCom = null;
		
		if(idTpUff > 0){
			tpUff =  commonDao.getTipoUfficioById(idTpUff);			
		} else {
			//ne creo uno base per scrivere null senza generare eccezione
			tpUff =  new TTpTipoUfficio();
		}
		
		if(idCom > 0){
			tCom = commonDao.getComuneById(idCom);			
		} else {
			//ne creo uno base per scrivere null senza generare eccezione
			tCom =  new TAnComuni();
		}

		return amministrazioneDao.inserisciUfficio(ufficiForm, tpUff.getCodiKIdTipoUff(), tCom.getCodiKIdComune(), tpUff.getDescTipoUff(), tCom.getDescComune(), descUtente);
	}

	public List<CategoriaCatastaleForm> listaCategorieCatastali(String descCategoriaCat, String descBreveCategoriaCat) {
		List<TTpCatCatasto> listModel = amministrazioneDao.listaCategorieCatastali(descCategoriaCat, descBreveCategoriaCat);
	
		if ((listModel == null) || listModel.isEmpty()) {
			return null;
		}
		
		List<CategoriaCatastaleForm> listForm = new ArrayList<CategoriaCatastaleForm>();
		Iterator<TTpCatCatasto> iterator = listModel.iterator();
		while (iterator.hasNext()) {
			TTpCatCatasto model = iterator.next();
			CategoriaCatastaleForm form = new CategoriaCatastaleForm();
			form.setCodiKIdCategCat(model.getCodiKIdCategCat());
			form.setDescCategoriaCat(model.getDescCategoriaCat() == null ? null : model.getDescCategoriaCat().toUpperCase());
			form.setDescBreveCategoriaCat(model.getDescBreveCategoriaCat() == null ? null : model.getDescBreveCategoriaCat().toUpperCase());	
			form.setDataFineValidita(model.getDataFineValidita());
			listForm.add(form);
		}
		return listForm;
	}
	
	public List<ProfessioneForm> listaProfessioni(String descProfessione) {

		List<TTpProfessione> listModel = amministrazioneDao.listaProfessioni(descProfessione);
		
		if ((listModel == null) || listModel.isEmpty()) {
			return null;
		}
		
		List<ProfessioneForm> listForm = new ArrayList<ProfessioneForm>();
		Iterator<TTpProfessione> iterator = listModel.iterator();
		while (iterator.hasNext()) {
			TTpProfessione model = iterator.next();
			ProfessioneForm form = new ProfessioneForm();
			form.setCodiKIdProfessione(model.getCodiKIdProfessione());
			form.setDescProfessione(model.getDescProfessione() == null ? null : model.getDescProfessione().toUpperCase());			
			form.setDataFineValidita(model.getDataFineValidita());
			listForm.add(form);
		}
		return listForm;
	}

	public List<IstitutoDetenzioneForm> listaIstitutiDetenzione(int idTipoIstituto, int idComuneIstituto) {
		
		List<TAnIstitutoDet> listModel = amministrazioneDao.listaIstitutiDetenzione(idTipoIstituto, idComuneIstituto);
		
		if ((listModel == null) || listModel.isEmpty()) {
			return null;
		}
		
		List<IstitutoDetenzioneForm> listForm = new ArrayList<IstitutoDetenzioneForm>();
		Iterator<TAnIstitutoDet> iterator = listModel.iterator();
		while (iterator.hasNext()) {
			TAnIstitutoDet model = iterator.next();
			IstitutoDetenzioneForm form = new IstitutoDetenzioneForm();
			form.setCodiKIdIstituto(model.getCodiKIdIstituto());
			form.setItemTipoIstituto(new ComboForm(String.valueOf(model.getTTpTipoIstituto().getCodiKIdTipoIst()), model.getTTpTipoIstituto().getDescTipoIst()));			
			
			if(model.getTAnComuni() != null &&  model.getTAnComuni().getCodiKIdComune() > 0){
				form.setItemComune(new ComboForm(String.valueOf(model.getTAnComuni().getCodiKIdComune()), model.getTAnComuni().getDescComune()));
			}
			
			form.setDescIstituto(model.getDescIstituto());
			form.setDescIndirizzo(model.getDescIndirizzo());
			form.setDescUffGiudiz(model.getDescUffGiudiz());
			form.setDataFineValidita(model.getDataFineValidita());
			listForm.add(form);
		}
		return listForm;
	}

	public List<ConservatoriaForm> listaConservatorie(String descConserv, String descCodiConserv) {

		List<TAnConservatorie> listModel = amministrazioneDao.listaConservatorie(descConserv, descCodiConserv);
		
		if ((listModel == null) || listModel.isEmpty()) {
			return null;
		}
		
		List<ConservatoriaForm> listForm = new ArrayList<ConservatoriaForm>();
		Iterator<TAnConservatorie> iterator = listModel.iterator();
		while (iterator.hasNext()) {
			TAnConservatorie model = iterator.next();
			ConservatoriaForm form = new ConservatoriaForm();
			form.setCodiKIdConserv(model.getCodiKIdConserv());
			form.setDescConserv(model.getDescConserv() == null ? null : model.getDescConserv().toUpperCase());			
			form.setDescCodiConserv(model.getDescCodiConserv() == null ? null : model.getDescCodiConserv().toUpperCase());
			form.setDataFineValidita(model.getDataFineValidita());
			listForm.add(form);
		}
		return listForm;
	}

	public List<SettoreAttivitaForm> listaSettoreAttivita(String descSettoreAtt, String descSezioneAteco) {

		List<TTpSettoreAtt> listModel = amministrazioneDao.listaSettoreAttivita(descSettoreAtt, descSezioneAteco);
		
		if ((listModel == null) || listModel.isEmpty()) {
			return null;
		}
		
		List<SettoreAttivitaForm> listForm = new ArrayList<SettoreAttivitaForm>();
		Iterator<TTpSettoreAtt> iterator = listModel.iterator();
		while (iterator.hasNext()) {
			TTpSettoreAtt model = iterator.next();
			SettoreAttivitaForm form = new SettoreAttivitaForm();
			form.setCodiKIdSettoreAtt(model.getCodiKIdSettoreAtt());
			form.setDescSettoreAtt(model.getDescSettoreAtt() == null ? null : model.getDescSettoreAtt().toUpperCase());			
			form.setDescSezioneAteco(model.getDescSezioneAteco() == null ? null : model.getDescSezioneAteco().toUpperCase());
			form.setDataFineValidita(model.getDataFineValidita());
			listForm.add(form);
		}
		return listForm;
	}

	public List<CondizioneGiuridicaForm> listaCondizioneGiuridica(String descCondGiur) {

		List<TTpCondGiur> listModel = amministrazioneDao.listaCondizioneGiuridica(descCondGiur);
		
		if ((listModel == null) || listModel.isEmpty()) {
			return null;
		}
		
		List<CondizioneGiuridicaForm> listForm = new ArrayList<CondizioneGiuridicaForm>();
		Iterator<TTpCondGiur> iterator = listModel.iterator();
		while (iterator.hasNext()) {
			TTpCondGiur model = iterator.next();
			CondizioneGiuridicaForm form = new CondizioneGiuridicaForm();
			form.setCodiKIdCondGiur(model.getCodiKIdCondGiur());
			form.setDescCondGiur(model.getDescCondGiur() == null ? null : model.getDescCondGiur().toUpperCase());
			form.setDataFineValidita(model.getDataFineValidita());
			listForm.add(form);
		}
		return listForm;
	}

	public CategoriaCatastaleForm getCategoriaCatastale(int codiceCatCatastale) {
		CategoriaCatastaleForm form = new CategoriaCatastaleForm();
		TTpCatCatasto row = amministrazioneDao.getCategoriaCatastale(codiceCatCatastale);
		mapperBean.map(row, form);
		return form;
	}

	public ProfessioneForm getProfessione(int codiceProfessione) {
		ProfessioneForm form = new ProfessioneForm();
		TTpProfessione row = amministrazioneDao.getProfessione(codiceProfessione);
		mapperBean.map(row, form);
		return form;
	}

	public IstitutoDetenzioneForm getIstitutoDetenzione(int codiceIstDetenzione) {
		IstitutoDetenzioneForm form = new IstitutoDetenzioneForm();
		TAnIstitutoDet row = amministrazioneDao.getIstitutoDetenzione(codiceIstDetenzione);
		form.setCodiKIdIstituto(row.getCodiKIdIstituto());
		form.setItemTipoIstituto(new ComboForm(String.valueOf(row.getTTpTipoIstituto().getCodiKIdTipoIst()), row.getTTpTipoIstituto().getDescTipoIst()));
		
		if(row.getTAnComuni() != null &&  row.getTAnComuni().getCodiKIdComune() > 0){
			form.setItemComune(new ComboForm(String.valueOf(row.getTAnComuni().getCodiKIdComune()), row.getTAnComuni().getDescComune()));
		}
		
		form.setDescIstituto(row.getDescIstituto());
		form.setDescIndirizzo(row.getDescIndirizzo());
		form.setDescUffGiudiz(row.getDescUffGiudiz());
		form.setDataFineValidita(row.getDataFineValidita());
		
		return form;
	}

	public ConservatoriaForm getConservatoria(short codiceConservatoria) {
		ConservatoriaForm form = new ConservatoriaForm();
		TAnConservatorie row = amministrazioneDao.getConservatoria(codiceConservatoria);
		mapperBean.map(row, form);
		return form;
	}

	public SettoreAttivitaForm getSettoreAttivita(int codiceSettAttivita) {
		SettoreAttivitaForm form = new SettoreAttivitaForm();
		TTpSettoreAtt row = amministrazioneDao.getSettoreAttivita(codiceSettAttivita);
		mapperBean.map(row, form);
		return form;
	}

	public CondizioneGiuridicaForm getCondGiuridica(int codiceCondGiuridica) {
		CondizioneGiuridicaForm form = new CondizioneGiuridicaForm();
		TTpCondGiur row = amministrazioneDao.getCondGiuridica(codiceCondGiuridica);
		mapperBean.map(row, form);
		return form;
	}

	public Integer inserisciCategoriaCatastale(String descCategoriaCat,	String descBreveCategoriaCat, Date dataFineValidita, String descUtente) {
		
		return amministrazioneDao.inserisciCategoriaCatastale(descCategoriaCat, descBreveCategoriaCat, dataFineValidita, descUtente);
	}

	public Integer inserisciProfessione(String descProfessione, Date dataFineValidita, String descUtente) {

		return amministrazioneDao.inserisciProfessione(descProfessione, dataFineValidita, descUtente);
	}

	public Integer inserisciIstitutoDetenzione(int idTipoIstituto, int idComuneIstituto, String descIndirizzoIstituto, String descUffGiudiz, Date dataFineValidita, String descUtente)  {
		
		int idTpIst = idTipoIstituto;
		int idCom = idComuneIstituto;
		
		TTpTipoIstituto tipoIst = null;
		TAnComuni tCom = null;
				
		if(idTpIst > 0){
			tipoIst =  commonDao.getTipoIstitutoById(idTpIst);			
		} else {
			//ne creo uno base per scrivere null senza generare eccezione
			tipoIst =  new TTpTipoIstituto();
		}
		
		if(idCom > 0){
			tCom = commonDao.getComuneById(idCom);			
		} else {
			//ne creo uno base per scrivere null senza generare eccezione
			tCom =  new TAnComuni();
		}
			
		return amministrazioneDao.inserisciIstitutoDetenzione(tipoIst.getCodiKIdTipoIst(), tipoIst.getDescTipoIst(), tCom.getCodiKIdComune(), tCom.getDescComune(), descIndirizzoIstituto, descUffGiudiz, dataFineValidita, descUtente);
	}

	public Integer inserisciConservatoria(String descConserv,	String descCodiConserv, Date dataFineValidita, String descUtente) {

		return amministrazioneDao.inserisciConservatoria(descConserv, descCodiConserv, dataFineValidita, descUtente);
	}

	public Integer inserisciSettoreAttivita(String descSettoreAtt,	String descSezioneAteco, Date dataFineValidita, String descUtente) {

		return amministrazioneDao.inserisciSettoreAttivita(descSettoreAtt, descSezioneAteco, dataFineValidita, descUtente);
	}

	public Integer inserisciCondGiuridica(String descCondGiur, Date dataFineValidita, String descUtente) {

		return amministrazioneDao.inserisciCondGiuridica(descCondGiur, dataFineValidita, descUtente);
	}

	public void modificaCatCatastale(CategoriaCatastaleForm categoriaCatastaleForm) {
		amministrazioneDao.modificaCatCatastale(categoriaCatastaleForm);
		
	}

	public void modificaCondGiuridica(CondizioneGiuridicaForm condizioneGiuridicaForm) {
		amministrazioneDao.modificaCondGiuridica(condizioneGiuridicaForm);
		
	}

	public void modificaConservatoria(ConservatoriaForm conservatoriaForm) {
		amministrazioneDao.modificaConservatoria(conservatoriaForm);
		
	}

	public void modificaIstitutoDet(IstitutoDetenzioneForm istitutoDetenzioneForm) {
		amministrazioneDao.modificaIstitutoDet(istitutoDetenzioneForm);
		
	}

	public void modificaProfessione(ProfessioneForm professioneForm) {
		amministrazioneDao.modificaProfessione(professioneForm);
		
	}

	public void modificaSettoreAttivita(SettoreAttivitaForm settoreAttivitaForm) {
		amministrazioneDao.modificaSettoreAttivita(settoreAttivitaForm);
		
	}

	public void modificaUfficio(UfficiForm ufficiForm) {
		amministrazioneDao.modificaUfficio(ufficiForm);
		
	}
	
	public TTpSettoreAtt getTpSettoreAttivita(int codiceSettAttivita) {
	     return amministrazioneDao.getSettoreAttivita(codiceSettAttivita);
	}
	
	public TTpCatCatasto getTpCategoriaCatastale(int codiceCatCatastale) {
	     return amministrazioneDao.getCategoriaCatastale(codiceCatCatastale);
	}

	public TTpSettoreAtt getTpSettoreAttivitaByDesc(String descSettoreAttivita) {
		return amministrazioneDao.getSettoreAttivitaByDesc(descSettoreAttivita);
	}

	@Override
	public List<SpeseProcessualiForm> listaSpeseProcessuali(BigDecimal importoSpese) {
		
		List<TTpSpeseProcessuali> listModel = amministrazioneDao.listaImportoSpese(importoSpese);
		
		if ((listModel == null) || listModel.isEmpty()) {
			return null;
		}
		
		List<SpeseProcessualiForm> listForm = new ArrayList<SpeseProcessualiForm>();
		Iterator<TTpSpeseProcessuali> iterator = listModel.iterator();
		while (iterator.hasNext()) {
			TTpSpeseProcessuali model = iterator.next();
			SpeseProcessualiForm form = new SpeseProcessualiForm();
			form.setCodiKIdSpeseProcessuali(model.getCodiKIdSpeseProcessuali());
			form.setImportoSpese(model.getImportoSpese());
			form.setDescImportoSpese(model.getDescImportoSpese());
			form.setDataInizioValidita(model.getDataInizioValidita());
			form.setDataFineValidita(model.getDataFineValidita());
			listForm.add(form);
		}
		return listForm;
		
	}

	@Override
	public void inserisciSpeseProcessuali(BigDecimal importoSpese, Date dataInizioValidita, String descImportoSpese,String descUtente) {
			
		amministrazioneDao.inserisciSpeseProcessuali(importoSpese, dataInizioValidita, descImportoSpese, descUtente);
	
	}

	@Override
	public void modificaSpeseProcessuali(int idSpeseProcessuali, BigDecimal importoSpese, Date dataInizioValidita, Date dataFineValidita, String descImportoSpese,
			String descUtente) {
		amministrazioneDao.modificaSpeseProcessuali(idSpeseProcessuali, importoSpese, dataInizioValidita, dataFineValidita, descImportoSpese, descUtente);
	}

	@Override
	public SpeseProcessualiForm getSpeseProcessuali(int codiKIdSpeseProcessuali) {
		
		SpeseProcessualiForm getSpeseProc =  new SpeseProcessualiForm();
		TTpSpeseProcessuali row = amministrazioneDao.getSpeseProcessuali(codiKIdSpeseProcessuali);
		mapperBean.map(row, getSpeseProc);
		return getSpeseProc;
	}

}
