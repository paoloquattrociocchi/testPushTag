package it.giustizia.smp.dao;

import it.giustizia.smp.dto.TRelBeneBeneDto;
import it.giustizia.smp.dto.fug.DettaglioAttoFugDto;
import it.giustizia.smp.form.BeneForm;
import it.giustizia.smp.form.FugForm;
import it.giustizia.smp.model.TFugInvio;
import it.giustizia.smp.model.TFugMsg;
import it.giustizia.smp.webservice.client.fug.modelli.EsitoFUG;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * FugDao
 * 
 * @author Almvaviva S.p.A.
 *         Interfaccia Fug DAO
 */
public interface FugDao {
	
	public List<BeneForm> listaBeniFug(int idUffOwn, long idProcedimento, final Long idBene);
    
    public  List<DettaglioAttoFugDto> quoteModelliA_D(int idUffOwn, long idProcedimento, final Long idBene, final String idModello);
    
    public  List<DettaglioAttoFugDto> quoteModelliC(int idUffOwn, long idProcedimento, final Long idBene, final String idModello);

    public  List<TRelBeneBeneDto> datiModelliH(int idUffOwn, long idProcedimento, final Long idBene, final String idModello);
    
    public long insertFugInvio(final int idUffOwn, final String codiceUtente, final long idProcedimento, final long idAtto, final long idBene,
			final String modello, final String protocolloRif, final Long idInvio, final String idProtocollo, final String esitoAnnullamento, 
			final Date dataNotifica, final Date dataIrrRev, final String flgStatoInvio, final Long idSogTit,
			final BigDecimal importoValuta, final String idValuta, final BigDecimal importoEuro,
			final String nomeMetodo);
    
    public long updateFugInvio(FugForm fugForm);
    
    public  List<DettaglioAttoFugDto> listaInvioFug(final int idUffOwn, final long idProcedimento, final Long idBene, final Long idAtto, final Long idSogg, final String idModello, final String seAnnull, final Date dataDa, final Date dataA);
    
    public TFugInvio getTFugInvioById(long idInvio);
    
    @SuppressWarnings("rawtypes")
    public List listaSoggettiBeneficiari(final int idUffOwn, final long idProcedimento, final Long idBene, final Long idAtto, final Long idSoggetto);
    
    public long insertFugErrori(final Long idInvio, final int progressivo, final EsitoFUG errore, final String codiceUtente);
    
    public Map<String, String> insertFugMessaggi(Long idInvio, String idProtocollo, char flagDirezione, char flagTipo, Blob messaggio);
    
    public List<DettaglioAttoFugDto> getDataSequestro(final int idUffOwn, final long idAtto);
    public List<DettaglioAttoFugDto> getDataRestituzione(final int idUffOwn, final long idAtto);

    public List<DettaglioAttoFugDto> getDatiModelloInviato(final long idProc, final Long idAtto, final Long idBene, final int idUffOwner, final String descModello);
    
    public List<TFugInvio> gatInvioFUGCollegato(final int idUffOwn, final String protRif);
    public Map<String, String> getRispostaEdEsitoXML(final int idUffOwn, final Long idInvio);
  	public boolean isBeneDecretoRev(final long idProc, final Long idAtto, final Long idBene, final int idUffOwner);
  	public boolean isBeneDecretoConf(final long idProc, final Long idAtto, final Long idBene, final int idUffOwner);
  	
  	public TFugMsg getTFugMsgFromTFugInvio(long id);
}
