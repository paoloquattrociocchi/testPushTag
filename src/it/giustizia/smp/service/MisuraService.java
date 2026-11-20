package it.giustizia.smp.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import it.giustizia.smp.form.BeneForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.model.TMisuraPers;
import it.giustizia.smp.model.TRelAttoMisPers;

public interface MisuraService {

    /**
     * @param soggettoForm
     * @return
     */
    public SoggettoForm caricaMisuraPersonale(SoggettoForm soggettoForm);
    
    public SoggettoForm caricaMisuraPersonaleCas(SoggettoForm soggettoForm);

	public void duplicaMisuraPersonalePerAggravamento(Long idPadre,Long idAggravamento,Long idAttoPadreAggiornato,Long idAttoAggravamento,Long idSoggettoInteressato,Integer idUffOwn); 
    
    public void inserisciMisuraPersonale(long idProc, long idUffOwn, long idSoggInt, String codUtente, SoggettoForm soggettoForm);

    public void inserisciMisuraPersonaleDecisorioIGrado(long idProc, long tipoProc, long idUffOwn, long idSoggInt, String codUtente, SoggettoForm soggettoForm);

    public void inserisciMisuraSoggettoDiffidato(long idProc, int idUffOwn, long idSoggInt, String codUtente);

    public List<TMisuraPers> listaMisurePersByIdSoggetto(long idSoggettoInteressato, int idUffOwn);
    
	public List<TRelAttoMisPers> listaRelAttoMisurePersByIdAtto(long idAtto, int idUffOwn,long idProcedimento);
    
    public List<TMisuraPers> listaMisurePersByIdSoggetto(long idSoggettoInteressato, long idProcedimento, int idUffOwn);

    public List<TRelAttoMisPers> listaRelAttoMisPersByProcEUfficio(Long idPadre, Integer idUffOwn);

    public TRelAttoMisPers relAttoMisPersByProcMisPersEAtto(long idMisura, long idPadre, long idAttoPadreAggiornato, int idUffOwn);

    public void saveRelazioneMisuraPersonalePerAggravamento(TRelAttoMisPers relazione, long idAggravamento, long idAttoAggravamento, long idMisura, int idUfficioUtente);

    public boolean stataAccolta(TRelAttoMisPers misura);
    
    public List<SoggettoForm> getSogIntSenzaDecisorio(final long idProcedimento, final long idAtto, final long idUffOwn, final String tipiMisura,String descAtto);
    
    public List<BeneForm> getBeniSenzaDecisorio(final long idProcedimento, final long idAtto, final long idUffOwn,String descAtto);

	public void eliminaMisuraPersonaleDelSoggetto(long idSoggetto);

    public List<TRelAttoMisPers> listaIdMisureSpecificheProposta(long vidProc, long vidUffOwn, long idSoggInt, long tipoAttoMisuraPers, String carMisura);
    
    public void convalidaNonConvalidaSequestro(long idProcedimento, long idAtto, long idBene, int idTipoInteresse, long idSoggetto, long idTipoMisura, int idUffOwn, String descUtente, String tipoOperazione, String nomeMetodo);
    
    public Date getDataDefinitivitaDecisorioIGradoRiesame(long idMisura, long idProc, long idAtto, int idUffOwn);
    
    public SoggettoForm caricaMisuraPersonaleIIGrado(SoggettoForm soggettoForm);
    
	public SoggettoForm caricaMisuraPersonaleIIIGrado(SoggettoForm soggettoForm);
    
	public List<TRelAttoMisPers> listaIdMisureSpecificheRicorso(long vidProc, long vidUffOwn, long idSoggInt, long vidAtto, String carMisura) ;

	public void inserisciMisuraPersonaleDecisorioIIGrado(long idProc, long tipoProc, long idUffOwn, long idSoggInt, String codUtente, SoggettoForm soggettoForm,boolean annullaNormativa);
	
	public void inserisciMisuraPersonaleDecisorioIIIGrado(long vidProc, long tipoProc, long vidUffOwn, long idSoggInt, String vcodUtente, SoggettoForm soggettoForm);
	 
	public BigDecimal getImportoCauzione(long vidProc, long vidAtto, Integer vidUffOwn);
	
	public void salvaSentenzaOppDivietiQuestore(long vidProc, long tipoProc, long vidUffOwn, long idSoggInt, String vcodUtente, SoggettoForm soggettoForm, long idAtto);
}
