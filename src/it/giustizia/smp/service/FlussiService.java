package it.giustizia.smp.service;

import java.io.IOException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;

import it.giustizia.smp.form.AllegatoForm;
import it.giustizia.smp.form.BeneForm;
import it.giustizia.smp.form.FlussiForm;
import it.giustizia.smp.form.FlussiPoliziaForm;
import it.giustizia.smp.form.FlussiToolAmministratoriForm;
import it.giustizia.smp.model.TDepositiAmm;
import it.giustizia.smp.model.TFlussiIn;
import it.giustizia.smp.xml.amministratori.BeneExtType;

/**
 * Flussi Service
 * 
 * @author Almaviva
 *         Interfaccia FlussiService
 */
@Transactional(rollbackFor = Exception.class)
public interface FlussiService {

    public void dettaglioFlusso(FlussiForm flussiForm);
    
    public void dettaglioFlussoSentenza(FlussiForm flussiForm,int idUffOwn,String descUtente);

    public TFlussiIn getFlussoIn(long codiKIdFlussoIn);

    public ArrayList<Object> leggiXML(FlussiForm flussiForm);

    public List<FlussiForm> listaFlussiDaElaborare(int idUffOwn, FlussiForm flussiForm);

    public List processFlussoDefinitivita(long idFlusso, String codUtente, long idUffOwn);

    public List processFlussoIncompRestAtti(long idFlusso, String codUtente, long idUffOwn);

    public List processFlussoIstanzaSoggDiffidato(FlussiForm form, String codUtente, long idUffOwn);

    public List processFlussoProposta(FlussiForm form, String codUtente, long idUffOwn);
    
    public List processFlussoFascicoloCauzione(FlussiForm form, String codUtente, long idUffOwn);
    
    public List processFlussoSequestro(FlussiForm form, String codUtente, long idUffOwn);
    
    public List processFlussoDecisorioIGrado(FlussiForm form, String codUtente, long idUffOwn);

    public List processFlussoDecretoUdienza(FlussiForm flussiForm, String codUtente, long idUffOwn);
    
    public List processFlussoVerbaleUdienza(FlussiForm form, String codUtente, long idUffOwn);
    
    public List processFlussoDecretoConvalidaSequestro(FlussiForm form, String codUtente, long idUffOwn);
    
    public List processFlussoPerditaEfficaciaSequestro(FlussiForm form, String codUtente, long idUffOwn);

    public List processFlussoRemAssRuoloUdienza(FlussiForm flussiForm, String codUtente, long idUffOwn);
    
    public List processFlussoDecretoRevocaSequestro(FlussiForm form, String codUtente, long idUffOwn);
    
    public List processFlussoDecretoRigettoTotaleSequestro(FlussiForm form, String codUtente, long idUffOwn);
    
    public List processFlussoOpposizioneDissequestro(FlussiForm form, String codUtente, long idUffOwn);
    
    public List processFlussoRevocaOpposizioneDissequestro(FlussiForm form, String codUtente, long idUffOwn);
    
    public List processFlussoRevocazioneConfisca(FlussiForm form, String codUtente, long idUffOwn);
    
    public List processFlussoSentenzaOpposizioneDissequestro(FlussiForm form, String codUtente, long idUffOwn);
    
    public List processFlussoSentenzaRevocazioneConfisca(FlussiForm form, String codUtente, long idUffOwn);
    
	public List processFlussoRicorsoInAppello(FlussiForm form, String codUtente, long idUffOwn,long idProcedimentoCda);
	
	public List processFlussoAttoRifiutato(FlussiForm form, String codUtente, long idUffOwn);	
	
	public List processFlussoDecDecisorioIIGrado(FlussiForm form, String codUtente, long idUffOwn);	
	
	public List processFlussoProvvIstFascicolo(FlussiForm form,String codUtente, long idUffOwn);
	
	public List processFlussoParere(FlussiForm form, String codUtente, long idUffOwn);
	
	public List processFlussoVersionamento(FlussiForm form, String codUtente, long idUffOwn);
	
	public List processFlussoRicorsoInCassazione(FlussiForm form, String codUtente, long idUffOwn);
	
	public List processFlussoProrogaSequestro(FlussiForm form, String codUtente, long idUffOwn);
	
	public List processFlussoSospensioneTemporanea(FlussiForm form, String codUtente, long idUffOwn);
	
	public List processFlussoProrogaSospensioneTemporanea(FlussiForm form, String codUtente, long idUffOwn);
	
	public List processFlussoSequestroSospensioneTemporanea(FlussiForm form, String codUtente, long idUffOwn);
	
    public List processFlussoApplicazioneProvv(FlussiForm form, String codUtente, long idUffOwn);
    
    public List processFlussoSentenzaCassazione(FlussiForm form, String codUtente, long idUffOwn);    
    
	public  TFlussiIn ricercaTFlussiIn(long idAtto,int idUffOwn,long tipoFlusso);
	
	public List processFlussoProcCDAdaCass(long idFlussoIn, String codUtente, long idUffOwn);
	
	public List processFlussoProcTridaCass(long idFlussoIn, String codUtente, long idUffOwn);
	
	public List processFlussoProcTridaCdA(long idFlussoIn, String codUtente, long idUffOwn);	
	
	 public List processFlussoUdienzaFascicolo(FlussiForm flussiForm, String codUtente, long idUffOwn);
	 
	 public List processFlussoParereFascicolo(FlussiForm flussiForm, String codUtente, long idUffOwn);
    
	 public List processFlussoDecisorioIGradoFascicolo(FlussiForm form, String codUtente, long idUffOwn);
	 
	 public void getDatiAccetazioneDeposito(FlussiForm flussiForm, int idUffDest, int idUffMit, long idAtto);
	 
	 public void processAccettazioneDeposito(int idUffDest, int idUffMit, long idAtto, String descUtente) throws IOException;
	 
	 public AllegatoForm downloadDocumentoAllegato(FlussiForm flussiForm, int idUffDest, int idUffMit, long idAtto) throws IOException;
	 
	 public List<FlussiPoliziaForm> getListFlussiPolizia(int idUffOwn, FlussiPoliziaForm flussiForm, final String flgDirezione);
	 
	 public FlussiPoliziaForm caricaDettaglioFlussoIngresso(FlussiPoliziaForm flussiForm);
		 
	 public boolean registraFlussoIngressoPolizia(FlussiPoliziaForm flussoPolizia);
	 
	 public List<FlussiToolAmministratoriForm> listaFlussiToolAmministratori(int idUffOwn, FlussiToolAmministratoriForm flussiForm);

	 public void getDettaglioDeposito(FlussiToolAmministratoriForm flussoForm, long idDeposito, int idUffOwn);

	 public void getDettaglioDepositoAmm(FlussiToolAmministratoriForm flussoForm, long idDeposito, char flag);

	 public void getDettaglioAmministratore(FlussiToolAmministratoriForm flussoForm);
	 
	 public void downloadAllegatoRelazione(FlussiToolAmministratoriForm flussoForm,HttpServletResponse response) throws Exception;

	 public List getListaBeniDeposito(FlussiToolAmministratoriForm flussoForm, long idDeposito);

	 public String registraAttoRelazione(FlussiToolAmministratoriForm flussoForm,int idUffOwn,String idCodiceUtente) throws Exception;
	
  	 public List<Map> getListaDepositoByIdProcFG(long idProcedimento, int idUffOwner, char flag);
  
 	 public List<BeneForm> getListaNuoviBeniByIdProcFG(long idProcedimento, int idUffOwner, char flag);
 
 	public Blob getXMLFileDeposito(long idDeposito);
 	
 	public void aggiornaXML(BeneForm beneForm,long idBeneInserito);
 	
 	public TDepositiAmm getDepositoAmmByIdAtto(long idAtto, int idUffOwner);
 	
	public String getBeneDescr(BeneExtType bene);

	public List processFlussoNRGCassazione(FlussiForm form, String codUtente,long idUffOwn);
 	
  }

