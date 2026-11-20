package it.giustizia.smp.service;

import it.giustizia.smp.dao.CommonDao;
import it.giustizia.smp.dto.KeyMessageDto;
import it.giustizia.smp.form.BeneForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.form.UfficioForm;
import it.giustizia.smp.model.TAnComuni;
import it.giustizia.smp.model.TAnConservatorie;
import it.giustizia.smp.model.TAnDistretto;
import it.giustizia.smp.model.TAnIstitutoDet;
import it.giustizia.smp.model.TAnMagistrati;
import it.giustizia.smp.model.TAnNormative;
import it.giustizia.smp.model.TAnOperFinanz;
import it.giustizia.smp.model.TAnProcedente;
import it.giustizia.smp.model.TAnProvincia;
import it.giustizia.smp.model.TAnStato;
import it.giustizia.smp.model.TAnUfficio;
import it.giustizia.smp.model.TAnUtenzePdoc;
import it.giustizia.smp.model.TPersonaFisica;
import it.giustizia.smp.model.TRelProcTrib;
import it.giustizia.smp.model.TTpCatAtto;
import it.giustizia.smp.model.TTpCatBene;
import it.giustizia.smp.model.TTpCatCatasto;
import it.giustizia.smp.model.TTpCondGiur;
import it.giustizia.smp.model.TTpEsitoUdienza;
import it.giustizia.smp.model.TTpModPerv;
import it.giustizia.smp.model.TTpOrdineProf;
import it.giustizia.smp.model.TTpProfessione;
import it.giustizia.smp.model.TTpSedeOrdine;
import it.giustizia.smp.model.TTpSettoreAtt;
import it.giustizia.smp.model.TTpSottocatBene;
import it.giustizia.smp.model.TTpSpeseProcessuali;
import it.giustizia.smp.model.TTpStatoConserv;
import it.giustizia.smp.model.TTpStatoProc;
import it.giustizia.smp.model.TTpTipoAtto;
import it.giustizia.smp.model.TTpTipoBene;
import it.giustizia.smp.model.TTpTipoCatasto;
import it.giustizia.smp.model.TTpTipoDenuncia;
import it.giustizia.smp.model.TTpTipoDisp;
import it.giustizia.smp.model.TTpTipoFascicolo;
import it.giustizia.smp.model.TTpTipoFondo;
import it.giustizia.smp.model.TTpTipoInteresse;
import it.giustizia.smp.model.TTpTipoIpotesi;
import it.giustizia.smp.model.TTpTipoIstituto;
import it.giustizia.smp.model.TTpTipoMisura;
import it.giustizia.smp.model.TTpTipoNota;
import it.giustizia.smp.model.TTpTipoOccupanti;
import it.giustizia.smp.model.TTpTipoPrevProc;
import it.giustizia.smp.model.TTpTipoPrevenzione;
import it.giustizia.smp.model.TTpTipoProc;
import it.giustizia.smp.model.TTpTipoRegime;
import it.giustizia.smp.model.TTpTipoRuoloMag;
import it.giustizia.smp.model.TTpTipoSocieta;
import it.giustizia.smp.model.TTpTipoStrada;
import it.giustizia.smp.model.TTpTipoTrasform;
import it.giustizia.smp.model.TTpTipoUfficio;
import it.giustizia.smp.model.TTpTipoVincolo;
import it.giustizia.smp.model.TTpValuta;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
public class CommonServiceImpl implements CommonService {

    private static final long ID_TIPO_INTERESSE_INTESTATARIO = 1L;
    private CommonDao         commonDao;
    @Autowired
    private Mapper            mapperBean;

    public List<ComboForm> getAllComuneByProvincia(String codiceIstatProvincia) {

        List<TAnComuni> lista = commonDao.getAllComuneByProvincia(codiceIstatProvincia);
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TAnComuni> iterator = lista.iterator();
        while (iterator.hasNext()) {
            TAnComuni comuni = iterator.next();
            risultati.add(new ComboForm(String.valueOf(comuni.getCodiKIdComune()), comuni.getDescComune()));
        }
        return risultati;
    }

    public List<ComboForm> getAllComuniByOrganoAndProvincia(String codiceIstatProvincia, long idOrgano) {

        List<TAnComuni> lista = commonDao.getAllComuniByOrganoAndProvincia(codiceIstatProvincia, idOrgano);
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TAnComuni> iterator = lista.iterator();
        while (iterator.hasNext()) {
            TAnComuni comuni = iterator.next();
            risultati.add(new ComboForm(String.valueOf(comuni.getCodiKIdComune()), comuni.getDescComune()));
        }
        return risultati;
    }
    
    public List<ComboForm> getAllUfficiByOrganoAndComune(int codiceComune, long idOrgano) {

        List<TAnUfficio> lista = commonDao.getAllUfficiByOrganoAndComune(codiceComune, idOrgano);
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TAnUfficio> iterator = lista.iterator();
        while (iterator.hasNext()) {
        	TAnUfficio ufficio = iterator.next();
        	String nome = ufficio.getDescUfficio();
        	if (ufficio.getDescIndirizzoSede()!=null) {
        		 nome += " - " + ufficio.getDescIndirizzoSede();
        	}
            risultati.add(new ComboForm(String.valueOf(ufficio.getCodiKIdUfficio()), nome));
        }
        return risultati;
    }
    

    public List<ComboForm> getAllCondizioneGiuridica() {

        List<TTpCondGiur> lista = commonDao.getAllCondizioneGiuridica();
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TTpCondGiur> iterator = lista.iterator();
        while (iterator.hasNext()) {
            TTpCondGiur condGiur = iterator.next();
            risultati.add(new ComboForm(String.valueOf(condGiur.getCodiKIdCondGiur()), condGiur.getDescCondGiur()));
        }
        return risultati;
    }

    public List<ComboForm> getAllIstitutiDetenzione() {

        List<TAnIstitutoDet> lista = commonDao.getAllIstitutiDetenzione();
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TAnIstitutoDet> iterator = lista.iterator();
        while (iterator.hasNext()) {
            TAnIstitutoDet ist = iterator.next();
            risultati.add(new ComboForm(String.valueOf(ist.getCodiKIdIstituto()), ist.getDescIstituto()));
        }
        return risultati;
    }

    public List<ComboForm> getAllMinuti() {

        List<ComboForm> listaMinuti = new ArrayList<ComboForm>();
        for (int i = 0; i < 60; i++) {
            if (i < 10) {
                listaMinuti.add(new ComboForm("0" + i, "0" + i));
            } else {
                listaMinuti.add(new ComboForm("" + i, "" + i));
            }
        }
        return listaMinuti;
    }

    public List<ComboForm> getAllOra() {

        List<ComboForm> listaOre = new ArrayList<ComboForm>();
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                listaOre.add(new ComboForm("0" + i, "0" + i));
            } else {
                listaOre.add(new ComboForm("" + i, "" + i));
            }
        }
        return listaOre;
    }

    public List<ComboForm> getAllProfessione() {

        List<TTpProfessione> lista = commonDao.getAllProfessione();
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TTpProfessione> iterator = lista.iterator();
        while (iterator.hasNext()) {
            TTpProfessione professioni = iterator.next();
            risultati.add(new ComboForm(String.valueOf(professioni.getCodiKIdProfessione()), professioni.getDescProfessione()));
        }
        return risultati;
    }

    public List<ComboForm> getAllProvincia() {

        List<TAnProvincia> lista = commonDao.getAllProvincia();
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TAnProvincia> iterator = lista.iterator();
        while (iterator.hasNext()) {
            TAnProvincia provincia = iterator.next();
            risultati.add(new ComboForm(String.valueOf(provincia.getCodiKIdIstatProv()), provincia.getDescProvincia()));
        }
        return risultati;
    }
	
    public List<ComboForm> getProvinciaByOrgano(long idOrgano) {
        
    	List<TAnProvincia> lista = commonDao.getProvinciaByOrgano(idOrgano);
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TAnProvincia> iterator = lista.iterator();
        while (iterator.hasNext()) {
            TAnProvincia provincia = iterator.next();
            risultati.add(new ComboForm(String.valueOf(provincia.getCodiKIdIstatProv()), provincia.getDescProvincia()));
        }
        return risultati;

	}
    
    public List<ComboForm> getAllStato() {

        List<TAnStato> lista = commonDao.getAllStato();
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TAnStato> iterator = lista.iterator();
        while (iterator.hasNext()) {
            TAnStato stato = iterator.next();
            risultati.add(new ComboForm(String.valueOf(stato.getCodiKIdStato()), stato.getDescStato()));
        }
        return risultati;
    }
    
    public List<ComboForm> getAllDescIstatStato() {

        List<TAnStato> lista = commonDao.getAllStato(); 
        lista.get( 0 ).setDescIstatStato( "1" );      // forzo il codice dell'Italia = 1
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TAnStato> iterator = lista.iterator();
        while (iterator.hasNext()) {
            TAnStato stato = iterator.next();
            risultati.add(new ComboForm(String.valueOf(stato.getDescIstatStato()), stato.getDescStato()));
        }
        return risultati;
    }
    
    public List<ComboForm> getAllDescBelfStato() {

        List<TAnStato> lista = commonDao.getAllStato(); 
        lista.get( 0 ).setDescBelfiore( "1" );      // forzo il codice dell'Italia = 1
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TAnStato> iterator = lista.iterator();
        while (iterator.hasNext()) {
            TAnStato stato = iterator.next();
            risultati.add(new ComboForm(String.valueOf(stato.getDescBelfiore()), stato.getDescStato()));
        }
        return risultati;
    }
    
    public List<ComboForm> getAllStatoPiva() {

        List<TAnStato> lista = commonDao.getAllStatoPiva();
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TAnStato> iterator = lista.iterator();
        while (iterator.hasNext()) {
            TAnStato stato = iterator.next();
            risultati.add(new ComboForm(String.valueOf(stato.getDescSiglaPIva()), stato.getDescSiglaPIva()));
        }
        return risultati;
    }

    public List<ComboForm> getAllTipiSocieta() {

        List<TTpTipoSocieta> lista = commonDao.getAllTipiSocieta();
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TTpTipoSocieta> iterator = lista.iterator();
        while (iterator.hasNext()) {
            TTpTipoSocieta ts = iterator.next();
            risultati.add(new ComboForm(String.valueOf(ts.getCodiKIdTipoSoc()), ts.getDescTipoSoc()));
        }
        return risultati;
    }

    /**
     * getCategorieCatastali
     * 
     * @return List<ComboForm>
     */
    public List<ComboForm> getCategorieCatastali() {

        List<TTpCatCatasto> lista = commonDao.listaCategorieCatastali();
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TTpCatCatasto> iterator = lista.iterator();
        while (iterator.hasNext()) {
            TTpCatCatasto categoriaCatastale = iterator.next();
            risultati.add(new ComboForm(String.valueOf(categoriaCatastale.getCodiKIdCategCat()), categoriaCatastale.getDescBreveCategoriaCat()+" "+categoriaCatastale.getDescCategoriaCat()));
        }
        return risultati;
    }

    /**
     * listaDisponibilita
     * 
     * @return List<ComboForm>
     */
    public List<ComboForm> getDisponibilita() {

        List<TTpTipoDisp> lista = commonDao.listaDisponibilita();
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TTpTipoDisp> iterator = lista.iterator();
        while (iterator.hasNext()) {
            TTpTipoDisp tipoDisponibilita = iterator.next();
            risultati.add(new ComboForm(String.valueOf(tipoDisponibilita.getCodiKIdTipoDisp()), tipoDisponibilita.getDescTipoDisp()));
        }
        return risultati;
    }

    public List<ComboForm> getFlgPresenza() {

        ComboForm vuoto = new ComboForm("", "");
        ComboForm si = new ComboForm("S", "Si");
        ComboForm no = new ComboForm("N", "No");
        List<ComboForm> flgPresenza = new ArrayList<ComboForm>();
        flgPresenza.add(vuoto);
        flgPresenza.add(si);
        flgPresenza.add(no);
        return flgPresenza;
    }

    public List<ComboForm> getNormativaByTipoPrevenzioneAndTipoMisura(SoggettoForm soggettoForm) {

        List<TAnNormative> normative = commonDao.getNormativaByTipoPrevenzioneAndTipoMisura(soggettoForm);
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        if (normative!= null){
        Iterator<TAnNormative> iter = normative.iterator();
        while (iter.hasNext()) {
            ComboForm normativaComboForm = new ComboForm();
            TAnNormative currentNormativa = iter.next();
            mapperBean.map(currentNormativa, normativaComboForm);
            risultati.add(normativaComboForm);
        }
        }
        return risultati;
    }

    public List<ComboForm> getNormativaByTipoProcedAndTipologiaPrevenzione(SoggettoForm soggetto) {

        List<TTpTipoPrevProc> normative = commonDao.getNormativaByTipoProcedAndTipologiaPrevenzione(soggetto);
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TTpTipoPrevProc> iter = normative!=null? normative.iterator(): null;
        if(iter!=null){
	        while (iter.hasNext()) {
	            ComboForm normativaComboForm = new ComboForm();
	            TTpTipoPrevProc currentNormativa = iter.next();
	            mapperBean.map(currentNormativa, normativaComboForm);
	            risultati.add(normativaComboForm);
	        }
        }
        return risultati;
    }

    public ComboForm getProcuraDDA(long idUffOwn) {

        TAnUfficio procuraDDA = commonDao.getProcuraDDA(idUffOwn);
        ComboForm cForm = new ComboForm();
        cForm.setId("" + procuraDDA.getCodiKIdUfficio());
        cForm.setDescrizione(procuraDDA.getDescTipoUfficio() + " di " + procuraDDA.getDescSedeUfficio());
        return cForm;
    }

    public ComboForm getProcuraInUdienza(final long idUffOwn) {

        ComboForm procInUdienza = new ComboForm();
        TAnUfficio uff = commonDao.getProcuraInUdienza(idUffOwn);
        if(uff != null) {
        	procInUdienza.setId("" + uff.getCodiKIdUfficio());
            procInUdienza.setDescrizione(uff.getDescTipoUfficio() + " di " + uff.getDescSedeUfficio());
            return procInUdienza;
        }
        
        return null;
    }
    
    public ComboForm getProcuraInUdienzaAssociata(final long idProc) {

        ComboForm procInUdienza = new ComboForm();
        TAnUfficio uff = commonDao.getProcuraInUdienzaAssociata(idProc);
        if(uff != null) {
        	procInUdienza.setId("" + uff.getCodiKIdUfficio());
            procInUdienza.setDescrizione(uff.getDescTipoUfficio() + " di " + uff.getDescSedeUfficio());
            return procInUdienza;
        }
        
        return null;
    }

    public ComboForm getProcuraProponente(long idProc) {

    	ComboForm cForm = new ComboForm();
        TAnUfficio procProp = commonDao.getProcuraProponente(idProc);
        if(procProp != null) {
        	cForm.setId("" + procProp.getCodiKIdUfficio());
            cForm.setDescrizione(procProp.getDescTipoUfficio() + " di " + procProp.getDescSedeUfficio());
            return cForm;
        }
        
        return null;
    }
    
    public ComboForm getProcuraGenerale(long idUffOwn) {

    	ComboForm cForm = new ComboForm();
    	TAnUfficio procGen = commonDao.getProcuraGenerale(idUffOwn);
        if(procGen != null) {
        	cForm.setId("" + procGen.getCodiKIdUfficio());
            cForm.setDescrizione(procGen.getDescTipoUfficio() + " di " + procGen.getDescSedeUfficio());
            return cForm;
        }
        return null;
    }
    
    public ComboForm getCDA(long idUffOwn) {

    	ComboForm cForm = new ComboForm();
    	TAnUfficio cda = commonDao.getCDA(idUffOwn);
        if(cda != null) {
        	cForm.setId("" + cda.getCodiKIdUfficio());
            cForm.setDescrizione(cda.getDescTipoUfficio() + " di " + cda.getDescSedeUfficio());
            return cForm;
        }
        return null;
    }       

    public List<ComboForm> getProcureDistrettualiByTribComp(long idUffOwnTrib, boolean excludeDDA) {

        List<TAnUfficio> procure = commonDao.getProcureDistrettualiByTribComp(idUffOwnTrib, excludeDDA);
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TAnUfficio> iter = procure.iterator();
        while (iter.hasNext()) {
        	TAnUfficio currentProcura = iter.next();
            ComboForm cForm = new ComboForm();
            cForm.setId("" + currentProcura.getCodiKIdUfficio());
            cForm.setDescrizione(currentProcura.getDescTipoUfficio() + " di " + currentProcura.getDescSedeUfficio());
            risultati.add(cForm);
        }
        return risultati;
    }

    public List<ComboForm> getTipiEsitoUdienza() {

        List<TTpEsitoUdienza> esiti = commonDao.getEsitiUdienze();
        List<ComboForm> esitiUdienze = new ArrayList<ComboForm>();
        Iterator<TTpEsitoUdienza> iter = esiti.iterator();
        ComboForm def = new ComboForm("-1", "");
        esitiUdienze.add(def);
        while (iter.hasNext()) {
        	TTpEsitoUdienza currentEsitoUdienza = iter.next();
            ComboForm currentEsitoUdienzaComboForm = new ComboForm();
            currentEsitoUdienzaComboForm.setId("" + currentEsitoUdienza.getCodiKIdEsitoUd());
            currentEsitoUdienzaComboForm.setDescrizione(currentEsitoUdienza.getDescEsitoUdienza());
            esitiUdienze.add(currentEsitoUdienzaComboForm);
        }
        return esitiUdienze;
    }

    public List<ComboForm> getTipiSostituzioneMagistrato() {

        return commonDao.getTipiSostituzioneMagistrato();
    }

//    public List<ComboForm> getTipoNota() 
//    {
//        List<TTpTipoNota> rawResults = commonDao.getTipoNota();
//        List<ComboForm> results = new ArrayList<ComboForm>();
//        Iterator<TTpTipoNota> iter = rawResults.iterator();
//        while (iter.hasNext()) {
//        	TTpTipoNota currentTipoNota = iter.next();
//            ComboForm cForm = new ComboForm();
//            mapperBean.map(currentTipoNota, cForm);
//            results.add(cForm);
//        }
//        return results;
//    }
    
    public List<ComboForm> getTipoNota() 
    {
        List<TTpTipoNota> rawResults = commonDao.getTipoNota();
        List<ComboForm> results = new ArrayList<ComboForm>();
        Iterator<TTpTipoNota> iter = rawResults.iterator();
        while (iter.hasNext()) {
          TTpTipoNota currentTipoNota = iter.next();
          results.add(
          new ComboForm( String.valueOf( currentTipoNota.getCodiKIdTipoNota() ), currentTipoNota.getDescTipoNota() ) ) ;
        }
        return results;
    }
    
    public List<ComboForm> getTipoRegime() 
    {
        List<TTpTipoRegime> rawResults = commonDao.getTipoRegime();
        List<ComboForm> results = new ArrayList<ComboForm>();
        Iterator<TTpTipoRegime> iter = rawResults.iterator();
        while (iter.hasNext()) {
          TTpTipoRegime currentTipoRegime = iter.next();
          results.add(
          new ComboForm( String.valueOf( currentTipoRegime.getCodiKIdTipoRegime() ), currentTipoRegime.getDescTipoRegime() ) ) ;
        }
        return results;
    }
   
    public List<ComboForm> getTipoAtto()
    {
        List<TTpTipoAtto> rawResults = commonDao.getTipoAtto();
        List<ComboForm> results = new ArrayList<ComboForm>();
        Iterator<TTpTipoAtto> iter = rawResults.iterator();
        while (iter.hasNext()) {
        	TTpTipoAtto currentTipoAtto = iter.next();
            ComboForm cForm = new ComboForm();
            mapperBean.map(currentTipoAtto, cForm);
            results.add(cForm);
        }
        return results;
    }

    public List<ComboForm> getTipoAttoAbilitati(long idProcedimento, int idTipoUfficio, int idTipoProc, int idStatoProc) {

		List queryResults = commonDao.getTipoAttoAbilitati(idProcedimento,idTipoUfficio, idTipoProc, idStatoProc); 

		List<ComboForm>  risultati = new ArrayList<ComboForm>();
		if ((queryResults == null) || queryResults.isEmpty()) {
			return risultati;
		}
				
		Iterator iterator = queryResults.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			TTpTipoAtto currentTipoAtto = (TTpTipoAtto) row.get("tipoAtto");
			ComboForm cForm = new ComboForm();
		    mapperBean.map(currentTipoAtto, cForm);
			risultati.add(cForm);
		}		
		
        return risultati;
    }
    
    

    
    public List<ComboForm> getCategoriaAttoAbilitate(long idProcedimento,int idTipoUfficio, int idTipoProc, int idStatoProc, int idTipoAtto) {

		List queryResults = commonDao.getCategoriaAttoAbilitate(idProcedimento,idTipoUfficio, idTipoProc, idStatoProc,idTipoAtto); 

		List<ComboForm>  risultati = new ArrayList<ComboForm>();
		if ((queryResults == null) || queryResults.isEmpty()) {
			return risultati;
		}
				
		Iterator iterator = queryResults.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			TTpCatAtto currentCategoriaAtto = (TTpCatAtto) row.get("categoriaAtto");
			ComboForm cForm = new ComboForm();
		    mapperBean.map(currentCategoriaAtto, cForm);
			risultati.add(cForm);
		}		
		
        return risultati;
    }

    /*
    public ComboForm getTribunaleCompetenza(long idUffOwn) {

        TAnUfficio tribComp = commonDao.getTribunaleCompetenza(idUffOwn);
        ComboForm cForm = new ComboForm();
        cForm.setId("" + tribComp.getCodiKIdUfficio());
        cForm.setDescrizione(tribComp.getDescTipoUfficio() + " di " + tribComp.getDescSedeUfficio());
        return cForm;
    }

    public List<ComboForm> getTribunaliDistrettuali(long idUffOwn, long idProc) {

        List<TAnUfficio> tribDistr = commonDao.getTribunaliDistrettuali(idUffOwn, idProc);
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TAnUfficio> iter = tribDistr.iterator();
        while (iter.hasNext()) {
        	TAnUfficio currentTribDistr = iter.next();
            ComboForm cForm = new ComboForm();
            cForm.setId("" + currentTribDistr.getCodiKIdUfficio());
            cForm.setDescrizione(currentTribDistr.getDescTipoUfficio() + " di " + currentTribDistr.getDescSedeUfficio());
            risultati.add(cForm);
        }
        return risultati;
    }
	*/
    
	public List<ComboForm> getRelProcTrib(long idUfficio,
			String tipoUfficio, long idTipoPrev, String flgDda, Long excludeIdUfficio) {
		
        List<TRelProcTrib> uff = commonDao.getRelProcTrib(idUfficio, tipoUfficio, idTipoPrev, flgDda, excludeIdUfficio);
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TRelProcTrib> iter = uff.iterator();
        while (iter.hasNext()) {
        	TRelProcTrib currentRel = iter.next();
        	TAnUfficio currentUff = null;
            if(tipoUfficio.equals(Constants.TIPO_UFFICIO_PROCURA)) {
            	currentUff = currentRel.getTAnUfficioByCodiKIdUffTrib();
            } else if(tipoUfficio.equals(Constants.TIPO_UFFICIO_TRIBUNALE)) {
            	currentUff = currentRel.getTAnUfficioByCodiKIdUffProc();
            }
        	
            ComboForm cForm = new ComboForm();
            cForm.setId("" + currentUff.getCodiKIdUfficio());
            cForm.setDescrizione(currentUff.getDescTipoUfficio() + " di " + currentUff.getDescSedeUfficio());
            risultati.add(cForm);
        }
        return risultati;
	}     
   
    public ComboForm getTribunaleCompetente(long idProc) {

        TAnUfficio tribComp = commonDao.getTribunaleCompetente(idProc);
        ComboForm cForm = new ComboForm();
        if (tribComp != null &&  tribComp.getCodiKIdUfficio() > 0 ){
        cForm.setId("" + tribComp.getCodiKIdUfficio());
        cForm.setDescrizione(tribComp.getDescTipoUfficio() + " di " + tribComp.getDescSedeUfficio());
        }
        return cForm;
    }   
    
    public List<ComboForm> getUfficoCompetenzaAggravamento(long idProcPadre) {

        List<ComboForm> list = new ArrayList<ComboForm>();
        TAnUfficio uff = commonDao.getUfficioCompetenzaAggravamento(idProcPadre);
        ComboForm tribunale = new ComboForm();
        tribunale.setId("" + uff.getCodiKIdUfficio());
        tribunale.setDescrizione(uff.getDescTipoUfficio() + " di " + uff.getDescSedeUfficio());
        list.add(tribunale);
        return list;
    }
    
    public List<ComboForm> getUfficiCompetenza(long idProc, Long idTipoUff) {

        List<ComboForm> list = new ArrayList<ComboForm>();
        List<Map> tribunali = commonDao.getUfficiCompetenza(idProc, idTipoUff);
        if ((tribunali != null) && (tribunali.size() > 0)) {
            for (int i = 0; i < tribunali.size(); i++) {
                Map map = tribunali.get(i);
                //TRelProcProc proc = (TRelProcProc) map.get("relProcProc");
                TAnUfficio uff = (TAnUfficio) map.get("ufficio");
                ComboForm tribunale = new ComboForm();
                tribunale.setId("" + uff.getCodiKIdUfficio());
                tribunale.setDescrizione(uff.getDescTipoUfficio() + " di " + uff.getDescSedeUfficio());
                list.add(tribunale);
            }
        }
        return list;
    }
 
    public List<ComboForm> getUfficiCompetenzaCdaTrib(int idUfficio) {

        List<ComboForm> list = new ArrayList<ComboForm>();
        TAnUfficio ufficio =commonDao.getUfficioById(idUfficio);
       
        ComboForm ufficioComboUff = new ComboForm();
        ufficioComboUff.setId("" + ufficio.getCodiKIdUfficio());
        ufficioComboUff.setDescrizione(ufficio.getDescTipoUfficio() + " di " + ufficio.getDescSedeUfficio());
        list.add(ufficioComboUff);
        
        if (ufficio.getTTpTipoUfficio().getCodiKIdTipoUff() == 6){
        	List<TAnUfficio> uffici = commonDao.getElencoUfficiCdaTribunali(ufficio.getTAnDistretto().getCodiKIdDistretto());
        	if ((uffici != null) && (uffici.size() > 0)) {
                for (int i = 0; i < uffici.size(); i++) {
                	TAnUfficio uff = uffici.get(i);
                                 
                    ComboForm ufficioCombo = new ComboForm();
                    ufficioCombo.setId("" + uff.getCodiKIdUfficio());
                    ufficioCombo.setDescrizione(uff.getDescTipoUfficio() + " di " + uff.getDescSedeUfficio());
                    list.add(ufficioCombo);
                }
            }
        }
        
        return list;
    }
    
    public String getDescUfficioCompetenza(int idUfficio) {

        String descUfficio = "";
        TAnUfficio ufficio =commonDao.getUfficioById(idUfficio);
        descUfficio = ufficio.getDescTipoUfficio() + " di " + ufficio.getDescSedeUfficio();
        return descUfficio;
    }
    
	//ricerca gli uffici legati al procedimento (T_REL_PROC_PROC), e nel caso in cui esistano più uffici
	//dello stesso tipo, privilegia quello legato all'atto origine dell'atto in parametro
    public List<ComboForm> getUfficiCompetenzaDistinctTipoUff(long idProc, long idAtto, long idUffOwn, long excludeTipoUff) {

        List<ComboForm> list = new ArrayList<ComboForm>();
        List<Map> tribunali = commonDao.getUfficiCompetenzaDistinctTipoUff(idProc, idAtto, idUffOwn, excludeTipoUff);
        if ((tribunali != null) && (tribunali.size() > 0)) {
            for (int i = 0; i < tribunali.size(); i++) {
                Map map = tribunali.get(i);
                //TRelProcProc proc = (TRelProcProc) map.get("relProcProc");
                TAnUfficio uff = (TAnUfficio) map.get("ufficio");
                ComboForm tribunale = new ComboForm();
                tribunale.setId("" + uff.getCodiKIdUfficio());
                tribunale.setDescrizione(uff.getDescTipoUfficio() + " di " + uff.getDescSedeUfficio());
                list.add(tribunale);
            }
        }
        return list;
    }
    
	//ricerca gli uffici legati al procedimento (T_REL_PROC_PROC), e nel caso in cui esistano più uffici
	//dello stesso tipo, privilegia quello legato all'atto origine dell'atto in parametro
    public ComboForm getUfficioAttoOrigine(long idProc, long idAttoFi, long idUffOwn) {

    	TAnUfficio uff = commonDao.getUfficioAttoOrigine(idProc, idAttoFi, idUffOwn);
        ComboForm uffAtto = new ComboForm();
        uffAtto.setId("" + uff.getCodiKIdUfficio());
        uffAtto.setDescrizione(uff.getDescTipoUfficio() + " di " + uff.getDescSedeUfficio());
        return uffAtto;
    }       
    
    public List<UfficioForm> getAllUfficiCompetenza(long idProc, Long idTipoUff) {

        List<UfficioForm> list = new ArrayList<UfficioForm>();
        List<Map> uffici = commonDao.getAllUfficiCompetenza(idProc, idTipoUff);
        if ((uffici != null) && (uffici.size() > 0)) {
            for (int i = 0; i < uffici.size(); i++) {
                Map map = uffici.get(i);
                TAnUfficio uff = (TAnUfficio) map.get("ufficio");
                UfficioForm ufficio = new UfficioForm();
                ufficio.setId("" + uff.getCodiKIdUfficio());
                ufficio.setDescrizione(uff.getDescTipoUfficio() + " di " + uff.getDescSedeUfficio());
                ufficio.setIdTipoUfficio(uff.getTTpTipoUfficio().getCodiKIdTipoUff());
                list.add(ufficio);
            }
        }
        return list;
    }    

    /**
     * getUfficiCompetenzaEProcuraInUdienza
     * 
     * @param idUffOwn
     * @param tipoUfficio
     * @return List<ComboForm>
     */
    public List<ComboForm> getUfficiCompetenzaEProcuraInUdienza(int idUffOwn, Long tipoUfficio, long idProc) {

        List<ComboForm> ufficiList = getUfficiCompetenza(idProc, tipoUfficio); 
        
        ComboForm comboPU = getProcuraInUdienzaAssociata(idProc);
        
        Iterator itx = ufficiList.iterator();
        boolean procuraInUdienza = false;
        
        while (itx.hasNext()) {
            ComboForm combo = (ComboForm) itx.next();
            if ((combo != null) && (comboPU != null) && comboPU.getId().equalsIgnoreCase(combo.getId())) {
                procuraInUdienza = true;
            }
        }
        
        if (!procuraInUdienza) {
            ufficiList.add(comboPU);
        }
        return ufficiList;
    }

    /**
     * Verranno estratti due uffici, il primo è il mittente il secondo è la sede
     * di corte d'appello per distretto
     * 
     * @param idProcedimento
     * @return
     */
    public List<ComboForm> getUfficiDestinatariIncompetenzaProcura(final long idProcedimento) {

        List daoResult = commonDao.getUfficiDestinatariIncompetenzaProcura(idProcedimento);
        List<ComboForm> ufficiComboForm = new ArrayList<ComboForm>();
        Iterator iter = daoResult.iterator();
        while (iter.hasNext()) {
            Map map = (Map) iter.next();
            TAnUfficio currentUfficio = (TAnUfficio) map.get("ufficio");
            ComboForm currentUfficioComboForm = new ComboForm();
            currentUfficioComboForm.setId("" + currentUfficio.getCodiKIdUfficio());
            currentUfficioComboForm.setDescrizione(currentUfficio.getDescTipoUfficio() + " di " + currentUfficio.getDescSedeUfficio());
            ufficiComboForm.add(currentUfficioComboForm);
        }
        return ufficiComboForm;
    }

    /**
     * getVincoli
     * 
     * @return List<ComboForm>
     */
    public List<ComboForm> getVincoli() {

        List<TTpTipoVincolo> lista = commonDao.listaVincoli();
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TTpTipoVincolo> iterator = lista.iterator();
        while (iterator.hasNext()) {
        	TTpTipoVincolo vincolo = iterator.next();
            risultati.add(new ComboForm(String.valueOf(vincolo.getCodiKIdTipoVinc()), vincolo.getDescTipoVincolo()));
        }
        return risultati;
    }

    public List<ComboForm> listaCategorieDelBene(BeneForm bene) {

        List<TTpCatBene> lista = commonDao.listaCategorieDelBene(bene);
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TTpCatBene> iterator = lista.iterator();
        while (iterator.hasNext()) {
        	TTpCatBene tmp = iterator.next();
            risultati.add(new ComboForm(String.valueOf(tmp.getCodiKIdCatBene()), tmp.getDescCatBene()));
        }
        return risultati;
    }

	public List<ComboForm> listaCodiciCab(String codiceAbi, String provinciaSigla) {
	    
	    List<TAnOperFinanz> lista = this.commonDao.getCodiciCab(null, codiceAbi, provinciaSigla);
        List<ComboForm> cabList = new ArrayList<ComboForm>();
        HashMap<String,String> listCab = new HashMap<String,String>();
        Iterator<TAnOperFinanz> iter = lista.iterator();
        while (iter.hasNext()) {
        	TAnOperFinanz elem = iter.next();
            String cab = elem.getId().getCodiKIdCab();
            String descr = cab + " - " + elem.getDescIndirizzo() + " " + elem.getDescComune() + " (" + elem.getDescSiglaProv() + ")";
            if(!listCab.containsKey(cab)){
                listCab.put(cab, descr);
                cabList.add(new ComboForm(cab,descr));
            }
        }
        return cabList;
	}

    public List<ComboForm> listaDellaTipologiaDelBene() {

        List<TTpTipoBene> lista = commonDao.listaDellaTipologiaDelBene();
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TTpTipoBene> iterator = lista.iterator();
        while (iterator.hasNext()) {
        	TTpTipoBene tmp = iterator.next();
            risultati.add(new ComboForm(String.valueOf(tmp.getCodiKIdTipoBene()), tmp.getDescTipoBene()));
        }
        return risultati;
    }

    public List<ComboForm> listaPmAssegnatari(int idUfficioUtente) {

        List<Long> relazioniUfficio = commonDao.listaIdMagistratiByUfficio(idUfficioUtente);
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        if (relazioniUfficio != null) {
            Iterator<Long> iterator = relazioniUfficio.iterator();
            while (iterator.hasNext()) {
                TAnMagistrati tmp = commonDao.getMagistratoById(iterator.next());
                risultati.add(new ComboForm(String.valueOf(tmp.getCodiKIdMagistrato()), tmp.getDescCognomeMag() + " " + tmp.getDescNomeMag()));
            }
        }
        return risultati;
    }

    public List<ComboForm> listaPrescrizioniMisuraPersonale() {

        List<TTpTipoMisura> lista = commonDao.listaPrescrizioniMisuraPersonale();
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TTpTipoMisura> iterator = lista.iterator();
        while (iterator.hasNext()) {
        	TTpTipoMisura tipoMisura = iterator.next();
            risultati.add(new ComboForm(String.valueOf(tipoMisura.getCodiKIdTipoMisura()), tipoMisura.getDescTipoMisura()));
        }
        return risultati;
    }

    public List<ComboForm> listaProcedenti(long idUfficio, long tipoUfficioConnesso, long tipoUfficioRic) {

        List<TAnProcedente> lista = commonDao.listaProcedenti(idUfficio, tipoUfficioConnesso, tipoUfficioRic);
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TAnProcedente> iterator = lista.iterator();
        while (iterator.hasNext()) {
            TAnProcedente tmp = iterator.next();
            risultati.add(new ComboForm(String.valueOf(tmp.getCodiKIdProced()), tmp.getDescProced()));
        }
        return risultati;
    }

	public List<ComboForm> listaProvinceAbi(String codiceAbi) {

		List<Map<String, Object>> lista = this.commonDao.listaProvinceAbi(codiceAbi);
		List<ComboForm> provAbiList = new ArrayList<ComboForm>();
		for (int i = 0; i < lista.size(); i++) {
			provAbiList.add(new ComboForm((String) lista.get(i).get("prov"), (String) lista.get(i).get("desc_prov")));
		}
		return provAbiList;
	}

    public List<ComboForm> listaSettoriAttivita() {

        List<TTpSettoreAtt> lista = commonDao.listaSettoriAttivita();
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TTpSettoreAtt> iterator = lista.iterator();
        while (iterator.hasNext()) {
        	TTpSettoreAtt settoreAtt = iterator.next();
            risultati.add(new ComboForm(String.valueOf(settoreAtt.getCodiKIdSettoreAtt()), settoreAtt.getDescSettoreAtt()));
        }
        return risultati;
    }

    public List<ComboForm> listaSottocategorieDelBene(BeneForm bene) {

        List<TTpSottocatBene> lista = commonDao.listaSottocategorieDelBene(bene);
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TTpSottocatBene> iterator = lista.iterator();
        while (iterator.hasNext()) {
        	TTpSottocatBene tmp = iterator.next();
            risultati.add(new ComboForm(String.valueOf(tmp.getCodiKIdSottocat()), tmp.getDescSottocatBene()));
        }
        return risultati;
    }

    /**
     * listaStatiConservazione
     * 
     * @return List<ComboForm>
     */
    public List<ComboForm> listaStatiConservazione(int tipo) {

        List<TTpStatoConserv> lista = commonDao.listaStatiConservazione(tipo);
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TTpStatoConserv> iterator = lista.iterator();
        while (iterator.hasNext()) {
        	TTpStatoConserv statoBene = iterator.next();
            risultati.add(new ComboForm(String.valueOf(statoBene.getCodiKIdStatoConserv()), statoBene.getDescStatoConserv() ));
        }
        return risultati;
    }

    public List<ComboForm> listaStatiDelProcedimento() {

        List<TTpStatoProc> lista = commonDao.listaStatiDelProcedimento();
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TTpStatoProc> iterator = lista.iterator();
        while (iterator.hasNext()) {
        	TTpStatoProc tmp = iterator.next();
            risultati.add(new ComboForm(String.valueOf(tmp.getCodiKIdStatoProc()), tmp.getDescStatoProc()));
        }
        return risultati;
    }

    public List<ComboForm> listaTipiDiProcedimento(int idTipoFascicolo) {

        List<TTpTipoProc> lista = commonDao.listaTipiDiProcedimento(idTipoFascicolo);
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        if (idTipoFascicolo==0) return risultati;
        Iterator<TTpTipoProc> iterator = lista.iterator();
        while (iterator.hasNext()) {
        	TTpTipoProc tmp = iterator.next();
            risultati.add(new ComboForm(String.valueOf(tmp.getCodiKIdTipoProc()), tmp.getDescTipoProc()));
        }
        return risultati;
    }
    
    //Simone 03/02/2014
    public List<ComboForm> listaTipiFascicolo(Integer tipoFascicolo) {

    	if (tipoFascicolo!= null && Integer.parseInt(""+tipoFascicolo)==0)tipoFascicolo=null;
    	
        List<TTpTipoFascicolo> lista = commonDao.listaTipiFascicolo(tipoFascicolo);
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TTpTipoFascicolo> iterator = lista.iterator();
        while (iterator.hasNext()) {
        	TTpTipoFascicolo tmp = iterator.next();
            risultati.add(new ComboForm(String.valueOf(tmp.getCodiKIdTipoFasc()), tmp.getDescTipoFasc()));
        }
        return risultati;
    }
    //
    public List<ComboForm> listaTipoIpotesi(int idTipoFascicolo, String tipoSoggetto) {

        List<TTpTipoIpotesi> lista = commonDao.listaTipoIpotesi(idTipoFascicolo, tipoSoggetto);
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TTpTipoIpotesi> iterator = lista.iterator();
        while (iterator.hasNext()) {
        	TTpTipoIpotesi tmp = iterator.next();
            risultati.add(new ComboForm(String.valueOf(tmp.getId().getCodiKIdTipoIpotesi()), tmp.getDescTipoIpotesi()));
        }
        return risultati;
    }

    public List<ComboForm> listaTipiInteresse() {

        List<TTpTipoInteresse> tipiInteresse = commonDao.listaTipiInteresse();
        List<ComboForm> risultato = new ArrayList<ComboForm>();
        for (TTpTipoInteresse tmp : tipiInteresse) {
            ComboForm form = new ComboForm();
            if (tmp.getCodiKIdTipoInt() == CommonServiceImpl.ID_TIPO_INTERESSE_INTESTATARIO) {
                mapperBean.map(tmp, form);
                risultato.add(0, form);
            } else {
                mapperBean.map(tmp, form);
                risultato.add(form);
            }
            // if (tmp.getCodiKIdTipoInt() == ID_TIPO_INTERESSE_INTESTATARIO) {
            // risultato.add(0, form);
            // }
        }
        return risultato;
    }

    /**
     * listaTipiMisuraDecretoSequestro
     * 
     * @param carattereMisura
     * @return List<ComboForm>
     */
    public List<ComboForm> listaTipiMisuraDecretoSequestro(String carattereMisura) {

    	if(carattereMisura == null)
    		return null;
    	
        List<TTpTipoMisura> misure = commonDao.listaTipiMisuraDecretoSequestro(carattereMisura);
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TTpTipoMisura> iter = misure.iterator();
        while (iter.hasNext()) {
            ComboForm comboForm = new ComboForm();
            TTpTipoMisura currentMisura = iter.next();
            mapperBean.map(currentMisura, comboForm);
            risultati.add(comboForm);
        }
        return risultati;
    }

    public List<ComboForm> listaTipiMisuraTipoP() {

        List<TTpTipoMisura> lista = commonDao.listaTipiMisura("P");
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TTpTipoMisura> iterator = lista.iterator();
        while (iterator.hasNext()) {
        	TTpTipoMisura tipoMisura = iterator.next();
            risultati.add(new ComboForm(String.valueOf(tipoMisura.getCodiKIdTipoMisura()), tipoMisura.getDescTipoMisura()));
        }
        while (iterator.hasNext()) {
        	TTpTipoMisura tipoMisura = iterator.next();
            risultati.add(new ComboForm(String.valueOf(tipoMisura.getCodiKIdTipoMisura()), tipoMisura.getDescTipoMisura()));
        }
        return risultati;
    }
    
    public List<ComboForm> listaTipiMisuraTipoAP() {

        List<TTpTipoMisura> lista = commonDao.listaTipiMisura("AP");
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TTpTipoMisura> iterator = lista.iterator();
        while (iterator.hasNext()) {
        	TTpTipoMisura tipoMisura = iterator.next();
            risultati.add(new ComboForm(String.valueOf(tipoMisura.getCodiKIdTipoMisura()), tipoMisura.getDescTipoMisura()));
        }
        while (iterator.hasNext()) {
        	TTpTipoMisura tipoMisura = iterator.next();
            risultati.add(new ComboForm(String.valueOf(tipoMisura.getCodiKIdTipoMisura()), tipoMisura.getDescTipoMisura()));
        }
        return risultati;
    }

    public List<ComboForm> listaTipiMisuraTipoT() {

        List<TTpTipoMisura> risultatiRaw = commonDao.listaTipiMisuraTipoT();
        if ((risultatiRaw == null) || risultatiRaw.isEmpty()) {
            return null;
        }
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        for (TTpTipoMisura tmp : risultatiRaw) {
            risultati.add(new ComboForm(String.valueOf(tmp.getCodiKIdTipoMisura()), tmp.getDescTipoMisura()));
        }
        return risultati;
    }

    public List<ComboForm> listaTipiPrevenzione() {

        List<TTpTipoPrevenzione> lista = commonDao.listaTipiPrevenzione();
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TTpTipoPrevenzione> iterator = lista.iterator();
        while (iterator.hasNext()) {
        	TTpTipoPrevenzione tmp = iterator.next();
            risultati.add(new ComboForm(String.valueOf(tmp.getCodiKIdTipoPrev()), tmp.getDescTipoPrev()));
        }
        return risultati;
    }

    public List<ComboForm> listaTipiProcedente(int idUfficioUtente) {

        List<TTpTipoUfficio> lista = commonDao.listaTipiProcedenteByUfficio(idUfficioUtente);
        if (!Utility.isDefinito(lista)) {
            return null;
        }
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TTpTipoUfficio> iterator = lista.iterator();
        while (iterator.hasNext()) {
        	TTpTipoUfficio tmp = iterator.next();
            risultati.add(new ComboForm(String.valueOf(tmp.getCodiKIdTipoUff()), tmp.getDescTipoUff()));
        }
        return risultati;
    }

    public List<ComboForm> listaTipiStrada() {

        List<TTpTipoStrada> lista = commonDao.listaTipiStrada();
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TTpTipoStrada> iterator = lista.iterator();
        while (iterator.hasNext()) {
        	TTpTipoStrada tipoStr = iterator.next();
            risultati.add(new ComboForm(String.valueOf(tipoStr.getCodiKIdTipoStrada()), tipoStr.getDescTipoStrada()));
        }
        return risultati;
    }

    /*commentato da sabino
    public List<ComboForm> listaTipiTenore() {

        List<TTenore> lista = commonDao.listaTipiTenore();
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TTenore> iterator = lista.iterator();
        while (iterator.hasNext()) {
            TTenore tenore = iterator.next();
            risultati.add(new ComboForm(String.valueOf(tenore.getCodiKIdTenore()), tenore.getDescTenore()));
        }
        return risultati;
    }
    */
    
	public List<ComboForm> listaTipiTenore() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ComboForm> listaTipiValuta() {

		List<TTpValuta> lista = this.commonDao.listaValuta();
		List<ComboForm> valutaList = new ArrayList<ComboForm>();
		Iterator<TTpValuta> iter = lista.iterator();
		while (iter.hasNext()) {
			TTpValuta currentValuta = iter.next();
			ComboForm currentValutaComboForm = new ComboForm();
			currentValutaComboForm.setId(currentValuta.getCodiKIdValuta());
			currentValutaComboForm.setDescrizione(currentValuta.getDescValuta());
			valutaList.add(currentValutaComboForm);
		}
		return valutaList;
	}

    public boolean localitaObbligatoriaNelTipoMisuraSelezionato(Long idTipoMisura,String carattereMisura) {

        List<TTpTipoMisura> lista = commonDao.listaTipiMisura(carattereMisura);
        for (TTpTipoMisura tmp : lista) {
        	if(idTipoMisura == 35)
        		return false;
            if ((tmp.getCodiKIdTipoMisura() == idTipoMisura) && (tmp.getFlgLocalita() != null) && tmp.getFlgLocalita().equals('S')) {
                return true;
            }
        }
        return false;
    }

    public void setCommonDao(CommonDao commonDao) {

        this.commonDao = commonDao;
    }

	public List<ComboForm> getProcureDistrettualiPerCdA(int idUffOwn) {

		List<TAnUfficio> procure = commonDao.getProcureDistrettualiByCdA(idUffOwn);
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TAnUfficio> iter = procure.iterator();
        while (iter.hasNext()) {
            TAnUfficio currentProcura = iter.next();
            ComboForm cForm = new ComboForm();
            cForm.setId("" + currentProcura.getCodiKIdUfficio());
            cForm.setDescrizione(currentProcura.getDescTipoUfficio() + " di " + currentProcura.getDescSedeUfficio());
            risultati.add(cForm);
        }
        return risultati;
	}

	public List<ComboForm> getUfficiDestinatari(long codAtto, long idUffOwn) {
		
		List<TAnUfficio> uffici = commonDao.getUfficiDestinatari(codAtto,idUffOwn);
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TAnUfficio> iter = uffici.iterator();
        while (iter.hasNext()) {
        	TAnUfficio currentUfficio = iter.next();
            ComboForm cForm = new ComboForm();
            cForm.setId("" + currentUfficio.getCodiKIdUfficio());
            cForm.setDescrizione(currentUfficio.getDescTipoUfficio() + " di " + currentUfficio.getDescSedeUfficio());
            risultati.add(cForm);
        }
        return risultati;
	}

	
/**	
 * getTipoOrdineAmministratori
 * @return  List<ComboForm>
 */
	public List<ComboForm> getTipoOrdineAmministratori() {
		
	       List<TTpOrdineProf> listaOrdini = commonDao.getListaOrdineAmministratori();
	        List<ComboForm> risultati = new ArrayList<ComboForm>();
	        Iterator<TTpOrdineProf> iterator = listaOrdini.iterator();
	        while (iterator.hasNext()) {
	        	TTpOrdineProf tipoOrdine = iterator.next();
	            risultati.add(new ComboForm(String.valueOf(tipoOrdine.getCodiKIdOrdineProf()), tipoOrdine.getDescOrdineProf()));
	        }
	        return risultati;		
	}


	/**
	 * getSediOrdineAmministratori
	 * @param idTipoOrdineAmministratori
	 * @return List<ComboForm>
	 */
	public List<ComboForm> getSediOrdineAmministratori(String idTipoOrdineAmministratori) {

		List<TTpSedeOrdine> lista = commonDao.getSediOrdineAmministratori(idTipoOrdineAmministratori);
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TTpSedeOrdine> iterator = lista.iterator();
        while (iterator.hasNext()) {
        	TTpSedeOrdine sedi = iterator.next();
            risultati.add(new ComboForm(String.valueOf(sedi.getCodiKIdSedeOrdine()), sedi.getDescSedeOrdine()));
        }
        return risultati;		
	}


	public List<ComboForm> listaTipoCatasto() {
		 List<TTpTipoCatasto> lista = commonDao.listaTipoCatasto();
	     List<ComboForm> risultati = new ArrayList<ComboForm>();
	     Iterator<TTpTipoCatasto> iterator = lista.iterator();
	        while (iterator.hasNext()) {
	        	TTpTipoCatasto tmp = iterator.next();
	            risultati.add(new ComboForm(String.valueOf(tmp.getCodiKIdTipoCat()), tmp.getDescBreveCatasto()));
	        }
	    return risultati;
	}

	
	public List<ComboForm> listaTipoFondo() {
		List<TTpTipoFondo> lista = commonDao.listaTipoFondo();
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TTpTipoFondo> iterator = lista.iterator();
        while (iterator.hasNext()) {
        	TTpTipoFondo tmp = iterator.next();
            risultati.add(new ComboForm(String.valueOf(tmp.getCodiKIdFondo()), tmp.getDescFondo()));
        }
        return risultati;
	}

	
	public List<ComboForm> listaTipoDenuncia() {
		List<TTpTipoDenuncia> lista = commonDao.listaTipoDenuncia();
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TTpTipoDenuncia> iterator = lista.iterator();
        while (iterator.hasNext()) {
        	TTpTipoDenuncia tmp = iterator.next();
            risultati.add(new ComboForm(String.valueOf(tmp.getCodiKIdTipoDen()), tmp.getDescTipoDenuncia()));
        }
        return risultati;
	}


	public List<ComboForm> listaTipoOccupanti() {
		List<TTpTipoOccupanti> lista = commonDao.listaTipoOccupanti();
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TTpTipoOccupanti> iterator = lista.iterator();
        while (iterator.hasNext()) {
        	TTpTipoOccupanti tmp = iterator.next();
            risultati.add(new ComboForm(String.valueOf(tmp.getCodiKIdOcc()), tmp.getDescTipoOcc()));
        }
        return risultati;
	}

	public List<ComboForm> getAllTipoRuoliMagistratiCollegio() {

        List<TTpTipoRuoloMag> lista = commonDao.getAllTipoRuoliMagistratiCollegio();
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TTpTipoRuoloMag> iterator = lista.iterator();
        while (iterator.hasNext()) {
        	TTpTipoRuoloMag elem = iterator.next();
            risultati.add(new ComboForm(String.valueOf(elem.getCodiKIdRuoloMag()), elem.getDescRuolo()));
        }
        return risultati;
    }

	
	public List<ComboForm> getAllTipoModalitaPervenimento() {
		
		List<TTpModPerv> lista = commonDao.getAllTipoModalitaPervenimento();
	        List<ComboForm> risultati = new ArrayList<ComboForm>();
	        Iterator<TTpModPerv> iterator = lista.iterator();
	        while (iterator.hasNext()) {
	        	TTpModPerv elem = iterator.next();
	            risultati.add(new ComboForm(String.valueOf(elem.getCodiKIdModalita()), elem.getDescModalita()));
	        }
	        return risultati;
	}
	/*
	 * (non-Javadoc)
	 * @see it.giustizia.smp.service.CommonService#getTipoTrasformazioni(java.lang.Character)
	 */
	
	public List<ComboForm> getTipoTrasformazioni(Character tipoTrasformazione) {
		List<TTpTipoTrasform> lista = commonDao.getTipoTrasformazioni(tipoTrasformazione);
		List<ComboForm> risultati = new ArrayList<ComboForm>();
		
		if(lista == null || lista.isEmpty()){
			return risultati;
		}
		
		for(TTpTipoTrasform tipoTrasf:lista){
			risultati.add(new ComboForm(String.valueOf(tipoTrasf.getCodiKIdTipoTrasf()), tipoTrasf.getDescTipoTrasf()));
		}
		
		return risultati;
	}
	
	public ComboForm getTipologiaBene(String idTipologia) {
		TTpTipoBene result = commonDao.getTipologiaBene(NumberUtils.toInt(idTipologia));
		return new ComboForm(idTipologia,result.getDescTipoBene());
	}
	
	
	public ComboForm getCategoriaBene(String idTipologia) {
		TTpCatBene result = commonDao.getCategoriaBene(NumberUtils.toInt(idTipologia));
		return new ComboForm(idTipologia,result.getDescCatBene());
	}
	
	
	public ComboForm getSottoCategoriaBene(String idTipologia) {
		TTpSottocatBene result = commonDao.getSottoCategoriaBene(NumberUtils.toInt(idTipologia));
		return new ComboForm(idTipologia,result.getDescSottocatBene());
	}
			

	public TTpCatAtto getCategoriaAtto(long idCatAtto) {
		TTpCatAtto result = commonDao.getCategoriaAtto(idCatAtto);	
		return result;
	}	
	
	 public ComboForm getDettaglioUfficio(long idUffOwn) {

        TAnUfficio ufficio = commonDao.getDettaglioUfficio(idUffOwn);
        ComboForm cForm = new ComboForm();
        cForm.setId("" + ufficio.getCodiKIdUfficio());
        cForm.setDescrizione(ufficio.getDescTipoUfficio() + " di " + ufficio.getDescSedeUfficio());
        return cForm;
    }

	 
	/**
	 * restituisce la trelprocproc di un tipo ufficio specifico
	 * @param idProc
	 * @param idTipoUff
	 * @return List
	 */			
    public List getNumeProcTRelProcProc(long idProc,long idTipoUff) {

    	List ritorno = commonDao.getNumeProcTRelProcProc(idProc,idTipoUff);
        if(ritorno != null && !ritorno.isEmpty()) {
            return ritorno;
        }   
        return null;
    }			
// lista degli altri uffici giudiziari
    public List<ComboForm> getElencoUfficiDistretto(long idUfficio) {
    	
    	List<TAnUfficio> uffici = commonDao.getElencoUfficiDistretto(idUfficio);
    	 List<ComboForm> risultati = new ArrayList<ComboForm>();
         Iterator<TAnUfficio> iter = uffici.iterator();
         while (iter.hasNext()) {
         	TAnUfficio currentUfficio = iter.next();
             ComboForm cForm = new ComboForm();
             cForm.setId("" + currentUfficio.getCodiKIdUfficio());
             cForm.setDescrizione(currentUfficio.getDescTipoUfficio() + " di " + currentUfficio.getDescSedeUfficio());
	             risultati.add(cForm);
	         }
	         return risultati;
	 	}	
	    public List<ComboForm> listaTipoModPerv() {
			List<TTpModPerv> lista = commonDao.listaTipoModPerv();
			//Segnalazione SMP10
			Collections.swap(lista, 1, 4);Collections.swap(lista, 1, 3);Collections.swap(lista, 1, 2);
	        List<ComboForm> risultati = new ArrayList<ComboForm>();
	        Iterator<TTpModPerv> iterator = lista.iterator();
	        while (iterator.hasNext()) {
	        	TTpModPerv tmp = iterator.next();
	            risultati.add(new ComboForm(String.valueOf(tmp.getCodiKIdModalita()), tmp.getDescModalita()));
	        }
	        return risultati;
		}  
       
	    public KeyMessageDto controllaInvioProcuraPG(long idProc,long idUfficio,String descUtente,String funzione,String metodo){
	    	
	    	return commonDao.controllaInvioProcuraPG(idProc, idUfficio,descUtente,funzione,metodo);
	    }
 
	    public KeyMessageDto controllaAperturaRicorsi(long idProc,long idUfficio,String descUtente,String funzione,String metodo){
	   
	    	return commonDao.controllaAperturaRicorsi(idProc, idUfficio,descUtente,funzione,metodo);
	    }

		public List<ComboForm> getUfficiRevocazioneConfisca(long idUffOwn, long idTipoUfficio) {

			List lista = commonDao.getUfficiRevocazioneConfisca(idUffOwn, idTipoUfficio);
			List<ComboForm> risultati = new ArrayList<ComboForm>();
	        Iterator<Map> iterator = lista.iterator();
	        while (iterator.hasNext()) {
	        	Map tmp = iterator.next();
				TAnUfficio uff = (TAnUfficio) tmp.get("procCDA");
			ComboForm cda = new ComboForm(); 
            cda.setId("" + uff.getCodiKIdUfficio());
            cda.setDescrizione(uff.getDescTipoUfficio() + " di " + uff.getDescSedeUfficio());				
            risultati.add(cda);
        }
        return risultati;
	}
	
	public KeyMessageDto controllaPresenzaAttiInLavorazione(long idProc, long tipoAtto, long idUfficio, String descUtente, String funzione, String metodo){
	    	
	    	return commonDao.controllaPresenzaAttiInLavorazione(idProc, tipoAtto, idUfficio,descUtente,funzione,metodo);
	}
	
    public String controlloIncompetenzaProcura(final long idProcedimento) {

        return commonDao.controlloIncompetenzaProcura(idProcedimento);
    }

	public TAnStato getStatoByIdCasellario(String codiceCasellario) {
		 return commonDao.getStatoByIdCasellario(codiceCasellario);
	}

	public TAnComuni getComuneByIdCasellario(String codiceCasellario) {
		return commonDao.getComuneByIdCasellario(codiceCasellario);
	}
	
	public KeyMessageDto controllaRelMagistratoUfficio(int idUffOwn, long idMagistrato){
	    	
	    	return commonDao.controllaRelMagistratoUfficio(idUffOwn, idMagistrato);
	}

	public List<ComboForm> getAllConservatoria() 
	{
        List<TAnConservatorie> lista = commonDao.getAllConservatoria();
        List<ComboForm> risultati = new ArrayList<ComboForm>();
        Iterator<TAnConservatorie> iterator = lista.iterator();
		while( iterator.hasNext() )
		{
			TAnConservatorie conservatoria = iterator.next();
			risultati.add( 
				new ComboForm( String.valueOf( conservatoria.getCodiKIdConserv() ), conservatoria.getDescConserv() ) );
		}
        return risultati;
    }

	   public List<ComboForm> getListaUffici(int idUfficio ) {
				List<TAnUfficio> lista = commonDao.getListaUffici(idUfficio);
		        List<ComboForm> risultati = new ArrayList<ComboForm>();
                Iterator<TAnUfficio> iterator = lista.iterator();
		        while (iterator.hasNext()) {
		        	TAnUfficio tmp = iterator.next();
		            risultati.add(new ComboForm(String.valueOf(tmp.getCodiKIdUfficio()), tmp.getDescUfficio()));
		        }
		        return risultati;
			}  
	
	   //HashMap
	   //idProcGest: id procedimento fascicolo gestione;
	   //idUffRic:   id ufficio fascicolo gestione;
	   public HashMap getFascicoloGestione(long idProc, long idUffOwn, String descUtente) {
		   
		   return commonDao.getFascicoloGestione(idProc, idUffOwn, descUtente);
	   }
	   
	   public List<ComboForm> getMagistratiByUff(long idUffOwn) {
		   
		   List<ComboForm> result = new ArrayList<ComboForm>();
		   List<HashMap> resultQry = commonDao.getMagistratiByUff(idUffOwn); 
		   Iterator<HashMap> i = resultQry.iterator();
           while (i.hasNext()) {
               HashMap hm = i.next();
               TAnMagistrati tmp = (TAnMagistrati)hm.get("magistrato");
               result.add(new ComboForm(String.valueOf(tmp.getCodiKIdMagistrato()), tmp.getDescCognomeMag() + " " + tmp.getDescNomeMag()));
           }
           return result;		   
	   }

	public List<ComboForm> listaTipoUfficio() {
        
		List<ComboForm> listaComboTipoUfficio = new ArrayList<ComboForm>();
		List<TTpTipoUfficio> listaTipoUfficio = commonDao.listaTipoUfficio();
		
		Iterator<TTpTipoUfficio> i = listaTipoUfficio.iterator();
	        while (i.hasNext()) {
	        	TTpTipoUfficio item = i.next();
	        	listaComboTipoUfficio.add(new ComboForm(String.valueOf(item.getCodiKIdTipoUff()), item.getDescTipoUff()));
	        }
		
		
		return listaComboTipoUfficio;
	}

	public List<ComboForm> listaUfficiTribunali() {
        
		List<ComboForm> listaComboUfficioTribunali = new ArrayList<ComboForm>();
		List<TAnUfficio> listaUfficioTribunali = commonDao.listaUfficiTribunali();
		
		Iterator<TAnUfficio> i = listaUfficioTribunali.iterator();
	        while (i.hasNext()) {
	        	TAnUfficio item = i.next();
	        	listaComboUfficioTribunali.add(new ComboForm(String.valueOf(item.getCodiKIdUfficio()), item.getDescUfficio()));
	        }
		
		
		return listaComboUfficioTribunali;
	}
	
	public List<ComboForm> getAllComuni() {
		
		
		List<ComboForm> listaComboComuni = new ArrayList<ComboForm>();
		List<TAnComuni> listaComuni = commonDao.getAllComuni();
		
		Iterator<TAnComuni> i = listaComuni.iterator();
	        while (i.hasNext()) {
	        	TAnComuni item = i.next();
	        	listaComboComuni.add(new ComboForm(String.valueOf(item.getCodiKIdComune()), item.getDescComune()));
	        }
				
		return listaComboComuni;

	}

	public List<ComboForm> getAllDistretti() {

		List<ComboForm> listaComboDistretti = new ArrayList<ComboForm>();
		List<TAnDistretto> listaDistretti = commonDao.getAllDistretti();
		
		Iterator<TAnDistretto> i = listaDistretti.iterator();
	        while (i.hasNext()) {
	        	TAnDistretto item = i.next();
	        	listaComboDistretti.add(new ComboForm(String.valueOf(item.getCodiKIdDistretto()), item.getDescDistretto()));
	        }
				
		return listaComboDistretti;
	}

	public List<ComboForm> listaTipoIstituto() {
		List<ComboForm> listaComboTpIstituto = new ArrayList<ComboForm>();
		List<TTpTipoIstituto> listaTpIstituto = commonDao.listaTipoIstituto();
		
		Iterator<TTpTipoIstituto> i = listaTpIstituto.iterator();
	        while (i.hasNext()) {
	        	TTpTipoIstituto item = i.next();
	        	listaComboTpIstituto.add(new ComboForm(String.valueOf(item.getCodiKIdTipoIst()), item.getDescTipoIst()));
	        }
				
		return listaComboTpIstituto;
	}
	
	public TAnUfficio getTAnUfficioByIdUff(long idUffOwn){
		return commonDao.getDettaglioUfficio(idUffOwn);
	}
	
	public TTpTipoMisura getTTpTipoMisuraById(long idTipoMIsura){
		return commonDao.getTTpTipoMisuraById(idTipoMIsura);
	}
	
	public TTpTipoProc getTTpTipoProcById(long idTipoProc){
		return commonDao.getTTpTipoProcById(idTipoProc);
	}
	
	public TAnNormative getTAnNormativeById(long idNormativa){
		return commonDao.getTAnNormativeById(idNormativa);
	}

	public TTpTipoPrevProc getTTpTipoPrevProc(long codiKIdTipoNorm) {
		return commonDao.getTTpTipoPrevProc(codiKIdTipoNorm);
	}
	
	public TAnComuni getComuneById(long id){
		return commonDao.getComuneById(id);	
	}
		
	public TAnComuni getComuneByDescCodiNsc(String descCodiNsc){
		return commonDao.getComuneByDescCodiNsc(descCodiNsc);
	}
	
	public TAnComuni getComuneByDescIstatComune(String descIstatComune){
		return commonDao.getComuneByDescIstatComune(descIstatComune);
	}
	
	public TAnStato getStatoById(int idStato) {
		 return commonDao.getStatoById(idStato);
	}
	public TTpTipoBene getIdTipologiaBene(String tipologia){
		return commonDao.getIdTipologiaBene(tipologia);	
	}
	public TTpCatBene getIdCategoriaBene(TTpTipoBene tTipoBene, String categoria) {
		return commonDao.getIdCategoriaBene(tTipoBene,categoria);	
	}
	public TTpSottocatBene getIdSottoCategoriaBene(TTpCatBene tCategoriaBene, String sottoCategoria) {
		return commonDao.getIdSottoCategoriaBene(tCategoriaBene, sottoCategoria);	
	}
	public List<TTpTipoFondo> listaTpTipoFondo() {
	    return commonDao.listaTipoFondo();
	}
	public List<TTpTipoCatasto> listaTpTipoCatasto() {
	    return commonDao.listaTipoCatasto();
	}
	public TTpValuta getTpValuta(String codice){
	    return commonDao.getTpValuta(codice);
	}	
	public TAnComuni getComuneByDescBelfiore(String descBelfiore){
		return commonDao.getComuneByDescBelfiore(descBelfiore);
	}
	public SoggettoForm getSoggettoById(long idSoggetto, long idUfficio){
		SoggettoForm soggettoForm = null;
		TPersonaFisica tPersona = commonDao.getTPersonaFisicaById(idSoggetto, idUfficio);
		if(tPersona!=null){
			soggettoForm = new SoggettoForm();
			mapperBean.map(tPersona, soggettoForm);
		}
		return soggettoForm;
	}


	public TAnStato getStatoByIdIstat(int idStatoIstat) {
		return commonDao.getStatoByIdIstat(idStatoIstat);
	}
	
	public TAnUtenzePdoc getUtenzaPdoc(int idUffOwn) {
		return commonDao.getUtenzaPdoc(idUffOwn);
	}

	@Override
	public String getVersioneDb(String softwareVersion) {
		// novembre 2019 AG
		// recupero versione dalla tabella db per poi unirla alla versione software 
		String versioneDb = commonDao.getVersioneDb(softwareVersion);
		
		return versioneDb;
	}
	
	
	public List<ComboForm> listaSpeseProcessuali() {
		
		

		List<ComboForm> listaComboSpeseProcessuali = new ArrayList<ComboForm>();
		List<TTpSpeseProcessuali> listaSpeseProcessuali = commonDao.listaSpeseProcessuali();
		
		DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
		decimalFormatSymbols.setDecimalSeparator(',');
		decimalFormatSymbols.setGroupingSeparator('.');
		
		Iterator<TTpSpeseProcessuali> i = listaSpeseProcessuali.iterator();
	        while (i.hasNext()) {
	        	TTpSpeseProcessuali item = i.next();
	        	listaComboSpeseProcessuali.add(new ComboForm(String.valueOf(item.getCodiKIdSpeseProcessuali()), 
	        			String.valueOf(new DecimalFormat("#,##0.00", decimalFormatSymbols).format(item.getImportoSpese()))));
	        }
				
		return listaComboSpeseProcessuali;
	}
	
public TTpSpeseProcessuali ricercaSpeseProcessuali(int idSpesaP) {
			
	TTpSpeseProcessuali recSpesaP = commonDao.ricercaSpeseProcessuali(idSpesaP);
						
		return recSpesaP;
	}
}
