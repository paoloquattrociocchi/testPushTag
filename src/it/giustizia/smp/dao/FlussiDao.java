package it.giustizia.smp.dao;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;

import it.giustizia.smp.model.TAmministratori;
import it.giustizia.smp.model.TBene;
import it.giustizia.smp.model.TDepositi;
import it.giustizia.smp.model.TDepositiAmm;
import it.giustizia.smp.model.TFlussiIn;
import it.giustizia.smp.model.TNotifPdoc;


/**
 * FlussiDao
 * 
 * @author Almvaviva S.p.A.
 *         Interfaccia Flussi DAO
 */
public interface FlussiDao {

    public List flussiDaElaborare(long vidUffOwn);

    public TFlussiIn getFlussoIn(long codiKIdFlussoIn);

    public List processFlussoDefinitivita(long idFlussoIn, String codUtente, long idUffOwn);

    public List processFlussoIncompRestAtti(long idFlussoIn, String codUtente, long idUffOwn);

    public List processFlussoIstanzaSoggDiffidato(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito, final Blob documento, final String tipoDocumento, final String nomeDocumento);

    public List processFlussoProposta(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito,final String dataRicezione, final Blob documento, final String tipoDocumento, final String nomeDocumento);
    
    public List processFlussoFascicoloCauzione(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito,final String dataRicezione, final Blob documento, final String tipoDocumento, final String nomeDocumento);
 
    public List processFlussoSequestro(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito, final String dataRicezione, final Blob documento, final String tipoDocumento, final String nomeDocumento);
   
    public List processFlussoDecisorioIGrado(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito, final Blob documento, final String tipoDocumento, final String nomeDocumento);

    public List processFlussoDecretoUdienza(final long idFlusso, final String codUtente, final long idUffOwn, final String data_DEPOSITO_ASSOCIA, final Blob documento, final String contentType, final String fileName);
    
    public List processFlussoVerbaleUdienza(long longValue, String codUtente, long idUffOwn, String data_DEPOSITO_ASSOCIA, Blob documento, String contentType, String fileName);
    
    public List processFlussoDecretoConvalidaSequestro(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito,final String dataRicezione, final Blob documento, final String tipoDocumento, final String nomeDocumento);
    
    public List processFlussoPerditaEfficaciaSequestro(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito,final String dataRicezione, final Blob documento, final String tipoDocumento, final String nomeDocumento);
    
    public List processFlussoDecretoRevocaSequestro(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito,final String dataRicezione, final Blob documento, final String tipoDocumento, final String nomeDocumento);
    
    public List processFlussoDecretoRigettoTotaleSequestro(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito, final Blob documento, final String tipoDocumento, final String nomeDocumento);

    public List processFlussoRemAssRuoloUdienza(long longValue, String codUtente, long idUffOwn, String data_DEPOSITO_ASSOCIA, Blob documento, String contentType, String fileName);
    
    public List processFlussoOpposizioneDissequestro(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito, final Blob documento, final String tipoDocumento, final String nomeDocumento);    
    
    public List processFlussoRevocaOpposizioneDissequestro(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito, final Blob documento, final String tipoDocumento, final String nomeDocumento);
    
    public List processFlussoRevocazioneConfisca(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito, final Blob documento, final String tipoDocumento, final String nomeDocumento);
    
    public List processFlussoSentenzaOpposizioneDissequestro(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito, final Blob documento, final String tipoDocumento, final String nomeDocumento);    

    public List processFlussoSentenzaRevocazioneConfisca(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito, final Blob documento, final String tipoDocumento, final String nomeDocumento);
    
    public List processFlussoRicorsoInAppello(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito, final Blob documento, final String tipoDocumento, final String nomeDocumento,final long idProcedimentoCda);

    public List processFlussoAttoRifiutato(final long idFlussoIn, final String codUtente, final long idUffOwn);

    public List processFlussoDecDecisorioIIGrado(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito,final String dataRicezione, final Blob documento, final String tipoDocumento, final String nomeDocumento);

    public List processFlussoProvvIstFascicolo(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito,final String dataRicezione, final Blob documento, final String tipoDocumento, final String nomeDocumento);
    
    public List processFlussoParere(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito, final Blob documento, final String tipoDocumento, final String nomeDocumento);
    
    public List processFlussoVersionamento(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataRicezione, final Blob documento, final String tipoDocumento, final String nomeDocumento);    

    public List processFlussoRicorsoInCassazione(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito, final Blob documento, final String tipoDocumento, final String nomeDocumento);   
    
    public List processFlussoProrogaSequestro(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataRicezione, final Blob documento, final String tipoDocumento, final String nomeDocumento);   
    
    public List processFlussoSospensioneTemporanea(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito, final String dataRicezione, final Blob documento, final String tipoDocumento, final String nomeDocumento);
    
    public List processFlussoProrogaSospensioneTemporanea(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito, final String dataRicezione, final Blob documento, final String tipoDocumento, final String nomeDocumento);
    
    public List processFlussoSequestroSospensioneTemporanea(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito, final String dataRicezione, final Blob documento, final String tipoDocumento, final String nomeDocumento);

    public List processFlussoApplicazioneProvv(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito, final String dataRicezione, final Blob documento, final String tipoDocumento, final String nomeDocumento);

    public List processFlussoSentenzaCassazione(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito, final String dataRicezione, final Blob documento, final String tipoDocumento, final String nomeDocumento);
 
    public List dettaglioFlussoSentenza(long idFlusso,int idUffOwn,String descUtente);
    
    public List<TFlussiIn> ricercaTFlussiIn(long idAtto,int idUffOwn,long tipoFlusso);
    
    public List processFlussoProcCDAdaCass(long idFlussoIn,String codUtente, long idUffOwn);
    
    public List processFlussoProcTridaCass(long idFlussoIn,String codUtente, long idUffOwn);
    
    public List processFlussoProcTridaCdA(long idFlussoIn,String codUtente, long idUffOwn);    
   
    public List processFlussoParereFascicolo(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito, final Blob documento, final String tipoDocumento, final String nomeDocumento);
    
    public List processFlussoUdienzaFascicolo(final long idFlusso, final String codUtente, final long idUffOwn, final String data_DEPOSITO_ASSOCIA, final Blob documento, final String contentType, final String fileName);

    public List processFlussoDecisorioIGradoFascicolo(final long idFlussoIn, final String codUtente, final long idUffOwn, final String dataDeposito, final Blob documento, final String tipoDocumento, final String nomeDocumento);
    
    public TDepositi getDeposito(Integer codiFkIdUffMit, Integer codiFkIdUffDes, long numeCopiaPdoc);
    
    public List getListFlussiPolizia(final long idUffOwn, final String flagDirezione);
    
    public Blob getXmlFlussoPolizia(final long codiKIdMsg);
    
    public TNotifPdoc getNotificaPDoc(Integer codiKIdUffOwner, long codiKIdAtto, Integer codiFkIdUffMitt);
    
    public void updateDeposito(TDepositi deposito);
    
    public List listaFlussiToolAmministratori(long vidUffOwn);
    public TDepositi getDettaglioDeposito(final long idDeposito, final int vidUffOwn);
    public TDepositiAmm getDettaglioDepositoAmm(final long idDeposito, final char flag);
    public TAmministratori getDettaglioAmministratore(final String codiceFiscale);
    public Blob getAllegatoDeposito(final long idDeposito);
    public Blob getXMLFileDeposito(final long idDeposito);
    public TBene getDescrizioneBene(final long idBene, final int idUffOwn);
    public void updateDepositoAmm(TDepositiAmm depositoAmm);

    public List<Map> getListaDepositoByIdProc(final long idProcedimento,final int idUffOwn, final char flag);
 
	public TDepositiAmm getDepositoAmmByIdAtto(long codAtto, int idUffOwner);
	
	public List processFlussoNRGCassazione(final long idFlussoIn, final String codUtente, final long idUffOwn);
	 
}
