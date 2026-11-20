package it.giustizia.smp.dao.cameralini;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import it.giustizia.smp.dao.ProcedimentoDao;
import it.giustizia.smp.dto.pg_cameralini.AmmDettaglioProcedimentoDto;
import it.giustizia.smp.dto.pg_cameralini.IntestatarioDto;
import it.giustizia.smp.dto.pg_cameralini.ProcedimentoDto;
import it.giustizia.smp.dto.pg_procedimento.SogIntDettaglioProcedimentoDto;
import it.giustizia.smp.model.TPersonaFisica;
import it.giustizia.smp.model.TPersonaGiur;
import it.giustizia.smp.model.TProcedimento;
import it.giustizia.smp.model.TRelProcIntestatari;


public interface ProcedimentoDaoCam extends ProcedimentoDao
{
	public ProcedimentoDto getProcedimentoByKeys( final int idUffOwn, final Long numProc, final Long annoProc,final Long tipoProc);
	
	//inserisciTRelProcProc differisce dall' originale perchè è stato 
	// parametrizzato il flag Padre 
    public Long inserisciTRelProcProc(final long idProc1, final long idProc2, final int idUffOwn, final String codUtente, final long idTipoAtto, final long idCatAtto, final String codiTipoRel, final Character flagPadre);
    
    public Long inserisciProcedimentoCameralini(int idUffOwn, String codUtente, TProcedimento proc, Date dataUltAggiornamento, int idTipoFasc ,
    		Long idTipoAtto, Long idCatAtto,
			String cognomeRichiedente,String nomeRichiedente,Date dataEmissione,Date dataDeposito,
			Long idModalitaTrasmissione,Long codicePU);
    
    public Long inserisciProcedimentoCameraliniNew(int idUffOwn, String codUtente, TProcedimento proc, Date dataUltAggiornamento, int idTipoFasc ,
    		String listaSoggetti,int idTipoProc, String tipoSoggetto, Long idVersione); 
    
    public Long inserisciIntestatarioPersFis(TPersonaFisica persona,String utente);
    public Long inserisciIntestatarioPersGiur(TPersonaGiur  persona,String utente);
    
    public List<SogIntDettaglioProcedimentoDto> getSoggettiMisurePatrProcedimento(final int idUffOwn, final Long idProc);
    
    public List<SogIntDettaglioProcedimentoDto> getSoggettiMisurePersProcedimento(final int idUffOwn, final Long idProc,final String tipoMisura);
    
    public List<AmmDettaglioProcedimentoDto> getAmmMisurePatrPersProcedimento(final int idUffOwn, final Long idProc);
    
    public List<IntestatarioDto> getAmministratoriOmonimi(final String cognome, final String nome, String ragSoc);
    
	public List<IntestatarioDto> getIntestatariOmonimi(final Character tipoSoggetto,final String cognome, final String nome, final Date dataNascita, 
			final Long idStatoNasc, final Long idComNasc, final String comuneNascEst,
            final String codiceFiscale, final String ragioneSoc, final String pIvaCompl, 
            final Long idStatoSede,final Long idComuneSede, final String comuneSedeEst);
	
    public Long inserisciProcedimentoCameraliniInt(final long idProc,final int idUffOwn,final int idTipoProc, 
			final BigDecimal codIntestatario, final String codTipoSoggetto, final String nome, final String cognome,
			final String sesso, final Date dataNascita, final String comuneEsteroNasc,final String codiceFiscalePF,final String codiceFiscalePG,
			final String ragioneSociale, final String comuneEsteroSede, final String indirizzoSede, final String descPartitaIva,final int idUffIntest,
			final  String descNumeCcia, final  String descCivico,final  String descCap, final Long codTipoStrada, final Long codComSede,
			final Long codComNasc,final Long codStatoSede,final Long codStatoNasc,final Long codTipoSoc,
			final Long codSoggInt, final Long idVer, final String descUtente);
    
    public BigDecimal updateIntestatario(final long idProc,final int idUffOwn,final int idTipoProc, 
			final BigDecimal codIntestatario, final String codTipoSoggetto, final String nome, final String cognome,
			final String sesso, final Date dataNascita, final String comuneEsteroNasc,final String codiceFiscalePF,final String codiceFiscalePG,
			final String ragioneSociale, final String comuneEsteroSede, final String indirizzoSede, final String descPartitaIva,final int idUffIntest,
			final  String descNumeCcia, final  String descCivico,final  String descCap, final Long codTipoStrada, final Long codComSede,
			final Long codComNasc,final Long codStatoSede,final Long codStatoNasc,final Long codTipoSoc,
			final Long codSoggInt, final Long idVer, final String descUtente);
    
    public List<IntestatarioDto> getIntestatari(final int idUffOwn, final Long idProc);

	public Map<String, Object> getDettaglioProcedimentoCam(TProcedimento procedimento);
	
    public List<Long> getIdComuneNascita( final String descComune );

    public IntestatarioDto getIntestatarioById (final Long idIntest, final Integer idUfficio );

	public TRelProcIntestatari getFirstTRelProcIntestatari(int idUffOwn, Long idProcedimento);
	
	public Map<String, Object> getEsitoDecisione(long idProcedimento, Long idAtto, int idUfficioUtente, String codiceUtente);

	//ginesti Luglio 2017
	public ProcedimentoDto getProcedimentoByKeysProc( final int idUffOwn, final Long numProc, final Long annoProc,final Long tipoProc);
	
}
