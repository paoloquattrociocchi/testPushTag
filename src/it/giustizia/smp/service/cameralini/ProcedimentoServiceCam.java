package it.giustizia.smp.service.cameralini;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import it.giustizia.smp.dto.pg_cameralini.IntestatarioDto;
import it.giustizia.smp.dto.pg_cameralini.ProcedimentoDto;
import it.giustizia.smp.dto.pg_procedimento.AssociaPersonaDto;
import it.giustizia.smp.form.AmministratoreForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.form.cameralini.AggiornaForm;
import it.giustizia.smp.model.TAnUfficio;
import it.giustizia.smp.model.TRelProcIntestatari;
import it.giustizia.smp.service.ProcedimentoService;

public interface ProcedimentoServiceCam extends ProcedimentoService
{
    public ProcedimentoDto getProcedimentoByKeys(Integer idUffOwn, Long numProc, Long annoProc,Long tipoProc );

    public TAnUfficio getCDA( final long idUffOwn );

    public void inserisciTRelProcProc( long idProc1, long idProc2, int idUffOwn, String codUtente, long idTipoAtto, long idCatAtto,
        String codiTipoRel, Character flagPadre );

    public void getProcedimentoByKeys( AggiornaForm form, Integer idUfficioUtente, Long numero, Short anno,Long tipoProc );

    
    public List<AssociaPersonaDto> inserisciProcedimentoCameralini(int idUffOwn, String codUtente, ProcedimentoForm procForm, long statoProc, List<SoggettoForm> listaSoggettiForm, int idTipoFascicolo,
    										Long idTipoAtto, Long idCatAtto,
    										String cognomeRichiedente,String nomeRichiedente,Date dataEmissione,Date dataDeposito,
    										Long idModalitaTrasmissione,Long idProcPadre);

    public List<AssociaPersonaDto> inserisciProcedimentoCameraliniNew(int idUffOwn, String codUtente, ProcedimentoForm procForm, long statoProc, List<SoggettoForm> listaSoggettiForm, int idTipoFascicolo,
			Long idTipoAtto, Long idCatAtto,
			String cognomeRichiedente,String nomeRichiedente,Date dataEmissione,Date dataDeposito,
			Long idModalitaTrasmissione,Long idProcPadre,int idTipoProc);
    
    public List<AssociaPersonaDto> inserisciProcedimentoCameraliniNew(int idUffOwn, String codUtente, ProcedimentoForm procForm, long statoProc, List<SoggettoForm> listaSoggettiForm, int idTipoFascicolo,
			Long idTipoAtto, Long idCatAtto,
			String cognomeRichiedente,String nomeRichiedente,Date dataEmissione,Date dataDeposito,
			Long idModalitaTrasmissione,Long idProcPadre,int idTipoProc, String tipoSoggetto, Long idVersione);
    
    public List<SoggettoForm> getSoggettiMisurePatrProcedimento( int idUffOwn, Long idProc );
    
    public List<SoggettoForm> getSoggettiMisurePersProcedimento(int idUffOwn, Long idProc,String tipoMisura );
    
    public List<AmministratoreForm> getAmmMisurePatrPersProcedimento(int idUffOwn, Long idProc);
    
    public List<IntestatarioDto> getAmministratoriOmonimi(String cognome,String nome, String ragSoc);
    
    public List<IntestatarioDto> getIntestatariOmonimi(Character tipoSoggetto,String cognome,String nome,Date dataNascita,
    		                                           Long idStatoNasc,Long idComNasc, String comuneNascEst, 
    		                                           String codiceFiscale,String ragioneSoc, String pIvaCompl,
    		                                           Long idStatoSede, Long idComuneSede,String comuneSedeEst);
    
    public List<AssociaPersonaDto> inserisciProcedimentoCameraliniInt(long idProc,int idUffOwn,final int idTipoProc, 
			  BigDecimal codIntestatario,   String codTipoSoggetto,   String nome,   String cognome,
			  String sesso,   Date dataNascita,   String comuneEsteroNasc,  String codiceFiscalePF,  String codiceFiscalePG,
			  String ragioneSociale,   String comuneEsteroSede,   String indirizzoSede,   String descPartitaIva,  int idUffIntest,
			   String descNumeCcia,    String descCivico,   String descCap,   Long codTipoStrada,   Long codComSede,
			  Long codComNasc,  Long codStatoSede,  Long codStatoNasc,  Long codTipoSoc,
			  Long codSoggInt,   Long idVer,   String descUtente);
    
    
    
    public BigDecimal updateIntestatario (long idProc,int idUffOwn,final int idTipoProc, 
			  BigDecimal codIntestatario,   String codTipoSoggetto,   String nome,   String cognome,
			  String sesso,   Date dataNascita,   String comuneEsteroNasc,  String codiceFiscalePF,  String codiceFiscalePG,
			  String ragioneSociale,   String comuneEsteroSede,   String indirizzoSede,   String descPartitaIva,  int idUffIntest,
			   String descNumeCcia,    String descCivico,   String descCap,   Long codTipoStrada,   Long codComSede,
			  Long codComNasc,  Long codStatoSede,  Long codStatoNasc,  Long codTipoSoc,
			  Long codSoggInt,   Long idVer,   String descUtente);
    
    
    public List<IntestatarioDto> getIntestatari(int idUffOwn, Long idProc);   

	public SoggettoForm getIntestatarioProc(int idUffOwn, Long idProc);   

	public ProcedimentoForm getDettaglioProcedimentoCam(long idProcFiglio);
	
    public List<Long> getIdComuneNascita( String descComune );
    
    public IntestatarioDto getIntestatarioById (Long idIntest, Integer idUfficio);
    
    public SoggettoForm getSoggettoDaCorregere(long idSoggetto, long idVersione, int idUffOwner);

	public TRelProcIntestatari getFirstTRelProcIntestatari(int idUffOwn, Long idProcedimento);
	
	public String[] getEsitoDecisione(long idProcedimento, Long idAtto, int idUfficioUtente, String codiceUtente);

	//Ginesti luglio 2017
	
	public ProcedimentoDto getProcedimentoByKeysProc(Integer idUffOwn, Long numProc, Long annoProc,Long tipoProc );
	// fine 
}