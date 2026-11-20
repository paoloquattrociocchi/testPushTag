package it.giustizia.smp.dao;

import it.giustizia.smp.dto.pg_udienza.UdienzaProcedimentoDto;
import it.giustizia.smp.model.TProcedimento;
import it.giustizia.smp.model.TUdienza;

import java.sql.Blob;
import java.util.List;
import java.util.Map;

/**
 * AttoDao
 * 
 * @author Almvaviva S.p.A.
 *         Interfaccia Udienza DAO
 */
public interface UdienzaDao {

    public void aggiornaDatiUdienza(long idUfficioUtente, UdienzaProcedimentoDto dettaglioUdienza);

    public void aggiornaFlagDif(long idUfficioUtente, long udienzaSelected, UdienzaProcedimentoDto dettDif);

    public void aggiornaFlagSoggConv(long idUfficioUtente, long udienzaSelected, UdienzaProcedimentoDto dettSoggConv);

    public void aggiornaMagUdienza(long idUfficioUtente, UdienzaProcedimentoDto magUdienza);

    public void confermaInvioUdienza(long idProcedimento, long idUfficioUtente, String codUtente, UdienzaProcedimentoDto dettaglioUdienza, long idProcUdi);
    
    public void confermaInvioVerbaleUdienza(long idProcedimento, long idUfficioUtente, String codUtente, UdienzaProcedimentoDto dettaglioUdienza, long idProcUdi, final long idAtto, final Blob blobDocumento, final String nomeDocumento, final String fileType);

    public void eliminaPmMagUdienza(long idUfficioUtente, UdienzaProcedimentoDto magUdienza);

    public List<UdienzaProcedimentoDto> getDettaglioUdienza(TProcedimento id, long idUfficioUtente, long udienzaSelected);

    public List<UdienzaProcedimentoDto> getUdienzeByIdProc(TProcedimento id, long idUfficioUtente);
    
    public List<UdienzaProcedimentoDto> getUdienzeByIdProcEStatoUdi(TProcedimento id, long idUfficioUtente, String idStatoUdienza);

    public void insertMagistratoUdienza(long idUfficioUtente, String codUtente, UdienzaProcedimentoDto dettaglioUdienza);

    public List<UdienzaProcedimentoDto> listaDifensoriUdienza(long idUfficioUtente, long udienzaSelected);

    public List<UdienzaProcedimentoDto> listaMagistratiCollegioUdienza(TProcedimento id, long idUfficioUtente, long udienzaSelected);

    public List<UdienzaProcedimentoDto> listaMagistratiPmUdienza(long idUfficioUtente, long udienzaSelected);

    public List<UdienzaProcedimentoDto> listaSoggettiConvocatiUdienza(TProcedimento id, long idUfficioUtente, long udienzaSelected);
    
    public void copiaDatiProcPerUdienza(long idUdienza, long idUffOwn, String codUtente, long idProcedimento);
    
    public long getIdUdienzaPrecedente(long idUdienza, int idUffOwn);

    public List<UdienzaProcedimentoDto> getUdienzaDecretoSeqConvByCodAtto(long codAtto, long idUfficioUtente);

    public Map esisteAttoInLavorazioneConDatiUdienza(long idProcedimento, long idUfficioUtente);

    public List<UdienzaProcedimentoDto> elencoMagistratiUff(long idUffOwn, String cognomeMag, String nomeMag, String dataUdienzaSelected);
    
    public List<UdienzaProcedimentoDto> elencoPmProcUdi(String dataUdienzaSelected, long idProcUdi);

    public List<UdienzaProcedimentoDto> getUdienzaByCodAttoEStatoUdi(long codAtto, long idUffOwn, String idStatoUdienza);
    
    public Map inserisciVerbaleUdienza(long idUfficio, String descUtente, long idProcedimento, long idTipoAtto, long idCategoriaAtto, long idUdienza, final String nomeMetodo);
    
    public void aggiornaVerbaleUdienza(final long idUfficioUtente, final UdienzaProcedimentoDto dettaglioUdienza, final long idAtto, final Blob blobDocumento, final String nomeDocumento, final String fileType);
    
	public TUdienza getUdienzaRicorso(long idAtto,long idUfficio,String descUtente);

}
