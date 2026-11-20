package it.giustizia.smp.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import it.giustizia.smp.dto.pg_misura.DettaglioMisuraPersonaleDto;
import it.giustizia.smp.model.TBene;
import it.giustizia.smp.model.TMisuraPers;
import it.giustizia.smp.model.TPersonaFisica;
import it.giustizia.smp.model.TRelAttoMisPers;

public interface MisuraDao {

    /**
     * caricaMisuraPersonale
     * 
     * @param vidProc
     * @param vidAtto
     * @param vidUffOwn
     * @param idSoggInt
     * @return
     */
    public List<DettaglioMisuraPersonaleDto> caricaMisuraPersonale(final long vidProc, final long vidAtto, final long vidUffOwn, final long idSoggInt);

    public void duplicaMisuraPersonalePerAggravamento(final Long idPadre, final Long idAggravamento,final Long idAttoPadreAggiornato,final Long idAttoAggravamento,final Long idSoggettoInteressato,final Integer idUffOwn); 
    

    /**
     * inserisciMisuraPersonale
     * 
     * @param vidProc
     * @param vidAtto
     * @param vidUffOwn
     * @param relAttoMisPers
     * @param idSoggInt
     * @param vcodUtente
     */
    public void inserisciMisuraPersonale(final String idMisureCaricate, final long vidProc, final long vidAtto, final long vidUffOwn, final TRelAttoMisPers relAttoMisPers, final long idSoggInt, final String cauzione, final String articolo67, final String codUtente, final String idPrescrizioni, final BigDecimal importoCauzione,final String articolo9c1);

    public void inserisciMisuraPersonaleDecisorioIGrado(final long idProc, final long idEsito, final long idAtto, final long idUffOwn, final String codUtente);

    public void inserisciMisuraPersonaleDecIGrado(final long vidProc, final long vidAtto, final long vidUffOwn, final TRelAttoMisPers relAttoMisPers, final long idSoggInt, final String cauzione, final String cauzioneProp, final String noteCauz, final String articolo67, final String articolo67Prop, final String codUtente, final BigDecimal importoCauzione, final String azionePrescrAcc, final String azionePrescrNoAcc, final long idDecisione, final long tipoProc);

    public Long inserisciMisuraSoggettoDiffidato(long idProc, int idUffOwn, long idSoggInt, long idTipoMisura, String carattereMisura, String codUtente);

    public List<TMisuraPers> listaMisurePersByIdSoggetto(long idSoggettoInteressato, long idProcedimento, int idUffOwn);
    
    public List<TMisuraPers> listaMisurePersByIdSoggetto(long idSoggettoInteressato, int idUffOwn);
    
    /**
     * listaRelAttoMisurePersByIdAtto
     * @param idAtto
     * @param idUffOwn
     * @param idProcedimento
     * @return
     */
	public List<TRelAttoMisPers> listaRelAttoMisurePersByIdAtto(long idAtto, int idUffOwn,long idProcedimento);

    
    public Long aggiornaTipoProcPrevProc(long idProc, long idUffOwn, String codUtente);

    public List<TRelAttoMisPers> listaRelAttoMisPersByProcEUfficio(Long idPadre, Integer idUffOwn);

    public TRelAttoMisPers relAttoMisPersByProcMisPersEAtto(long idMisura, long idPadre, long idAttoPadreAggiornato, int idUffOwn);

    public void salvaRelazioneMisuraPersonalePerAggravamento(TRelAttoMisPers relazione, long idAggravamento, long idAttoAggravamento, long idMisura, int idUfficioUtente);
    
    public List<TPersonaFisica> getSogIntSenzaDecisorio(final long idProcedimento, final long idAtto, final long idUffOwn, final String tipiMisura,final String descAtto);
    
    public List<TBene> getBeniSenzaDecisorio(final long idProcedimento, final long idAtto, final long idUffOwn,final String descAtto);

	public void eliminaRelazioneAttoMisuraPersonale(TRelAttoMisPers relazione);

	public void eliminaMisuraPersonale(TMisuraPers misuraPersonale);

    public List listaIdMisSpecificheProposta(long vidProc, long vidUffOwn, long idSoggInt, long idAtto, String carMisura);
    
    public void convalidaNonConvalidaSequestro( long idProcedimento, long idAtto, long idBene, int idTipoInteresse, long idSoggetto, long idTipoMisura, int idUffOwn, String descUtente, String tipoOperazione, String nomeMetodo);
    
    public Date getDataDefinitivitaDecisorioIGradoRiesame(long idMisura, long idProc, long idAtto, int idUffOwn);
    //2 GRADO
    
    public List<DettaglioMisuraPersonaleDto> caricaMisuraPersonaleIIGrado(final long vidProc, final long vidAtto, final long vidUffOwn, final long idSoggInt);
    
    public List<DettaglioMisuraPersonaleDto> caricaMisuraPersonaleIIIGrado(final long vidProc, final long vidAtto, final long vidUffOwn, final long idSoggInt);

    
    public List listaIdMisSpecificheRicorso(long vidProc, long vidUffOwn, long idSoggInt, long idAtto, String carMisura);
    
    public void inserisciMisuraPersonaleDecIIGrado(final long vidProc, final long vidAtto, final long vidUffOwn, final TRelAttoMisPers relAttoMisPers, final long idSoggInt, final String cauzione, final String noteCauz, final String articolo67,  final String codUtente, final BigDecimal importoCauzione, final long idDecisione, final long tipoProc,final String idPrescrizioni,final String visibilita);
    //FINE 2 GRADO
    
    
	public TMisuraPers getTMisuraPerById(final long idMisura, final int idUfficioUtente);
	
	/**
	 * inserisciMisuraPersonaleDecIIIGrado
	 * @param vidProc
	 * @param vidAtto
	 * @param vidUffOwn
	 * @param relAttoMisPers
	 * @param idSoggInt
	 * @param vcodUtente
	 * @param idDecisione
	 * @param tipoProc
	 */
    public void inserisciMisuraPersonaleDecIIIGrado( long vidProc,  long vidAtto,  long vidUffOwn,  long idSoggInt,   String vcodUtente,   long idDecisione, long tipoProc,Date dataRicezione, long annoSentenza,long numeroSentenza);	

    public BigDecimal getImportoCauzione(long vidProc, long vidAtto, Integer vidUffOwn);

}
