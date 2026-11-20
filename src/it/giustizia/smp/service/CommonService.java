package it.giustizia.smp.service;

import it.giustizia.smp.dto.KeyMessageDto;
import it.giustizia.smp.form.BeneForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.form.UfficioForm;
import it.giustizia.smp.model.TAnComuni;
import it.giustizia.smp.model.TAnNormative;
import it.giustizia.smp.model.TAnStato;
import it.giustizia.smp.model.TAnUfficio;
import it.giustizia.smp.model.TAnUtenzePdoc;
import it.giustizia.smp.model.TTpCatAtto;
import it.giustizia.smp.model.TTpCatBene;
import it.giustizia.smp.model.TTpSottocatBene;
import it.giustizia.smp.model.TTpSpeseProcessuali;
import it.giustizia.smp.model.TTpTipoBene;
import it.giustizia.smp.model.TTpTipoCatasto;
import it.giustizia.smp.model.TTpTipoFondo;
import it.giustizia.smp.model.TTpTipoMisura;
import it.giustizia.smp.model.TTpTipoPrevProc;
import it.giustizia.smp.model.TTpTipoProc;
import it.giustizia.smp.model.TTpValuta;

import java.util.HashMap;
import java.util.List;

public interface CommonService {
    

	public List<ComboForm> getAllComuneByProvincia(String codiceIstatProvincia);

	public List<ComboForm> getAllComuniByOrganoAndProvincia(String codiceIstatProvincia, long idOrgano);
	
	public List<ComboForm> getAllUfficiByOrganoAndComune(int codiceComune, long idOrgano);
	
	//public List<ComboForm> getAllComuniByOrganoAndProvincia(String codiceIstatProvincia, long idOrgano);

	public List<ComboForm> getAllCondizioneGiuridica();

	public List<ComboForm> getAllIstitutiDetenzione();

	public List<ComboForm> getAllMinuti();

	public List<ComboForm> getAllOra();

	public List<ComboForm> getAllProfessione();

	public List<ComboForm> getAllProvincia();

	public List<ComboForm> getAllStato();
	
	public List<ComboForm> getAllStatoPiva();

	public List<ComboForm> getAllTipiSocieta();

	public List<ComboForm> getCategorieCatastali();

	public List<ComboForm> getDisponibilita();

	public List<ComboForm> getFlgPresenza();

	public List<ComboForm> getNormativaByTipoPrevenzioneAndTipoMisura(SoggettoForm soggettoForm);

	public List<ComboForm> getNormativaByTipoProcedAndTipologiaPrevenzione(SoggettoForm soggetto);

	public ComboForm getProcuraDDA(long idUffOwn);

	public ComboForm getProcuraInUdienza(long idUffOwn);
	
	public ComboForm getProcuraInUdienzaAssociata(final long idProc);

	public ComboForm getProcuraProponente(long idProc);
	
	public ComboForm getProcuraGenerale(long idUffOwn);
	
	public ComboForm getCDA(long idUffOwn);

	public List<ComboForm> getProcureDistrettualiByTribComp(long idUffOwn, boolean excludeDDA);

	public List<ComboForm> getTipiEsitoUdienza();

	public List<ComboForm> getTipiSostituzioneMagistrato();

	//public ComboForm getTribunaleCompetenza(long idUffOwn);

	//public List<ComboForm> getTribunaliDistrettuali(long idUffOwn, long idProc);
	
	public List<ComboForm> getRelProcTrib(long idUfficio, String tipoUfficio, long idTipoPrev, String flgDda, Long excludeIdUfficio);

	public List<ComboForm> getUfficoCompetenzaAggravamento(long idProcPadre);
	
	public List<ComboForm> getUfficiCompetenza(long idProc, Long idTipoUff);
	
	public List<ComboForm> getUfficiCompetenzaCdaTrib( int idUfficio);
	
	 public String getDescUfficioCompetenza(int idUfficio);

	//ricerca gli uffici legati al procedimento (T_REL_PROC_PROC), e nel caso in cui esistano più uffici
	//dello stesso tipo, privilegia quello legato all'atto origine dell'atto in parametro
 	public List<ComboForm> getUfficiCompetenzaDistinctTipoUff(long idProc, long idAtto, long idUffOwn, long excludeTipoUff);	

	//ricerca gli uffici legati al procedimento (T_REL_PROC_PROC), e nel caso in cui esistano più uffici
	//dello stesso tipo, privilegia quello legato all'atto origine dell'atto in parametro
    public ComboForm getUfficioAttoOrigine(long idProc, long idAttoFi, long idUffOwn);

	public List<UfficioForm> getAllUfficiCompetenza(long idProc, Long idTipoUff);

	/**
	 * getUfficiCompetenzaEProcuraInUdienza
	 * 
	 * @param idUffOwn
	 * @param tipoUfficio
	 * @return List<ComboForm>
	 */
	public List<ComboForm> getUfficiCompetenzaEProcuraInUdienza(int idUffOwn, Long tipoUfficio, long idProc);

	public List<ComboForm> getUfficiDestinatariIncompetenzaProcura(long idProcedimento);

	public List<ComboForm> getVincoli();

	public List<ComboForm> listaCategorieDelBene(BeneForm bene);
	
	public List<ComboForm> listaCodiciCab(String codiceAbi, String provincia);

	public List<ComboForm> listaDellaTipologiaDelBene();

	public List<ComboForm> listaPmAssegnatari(int idUfficioUtente);

	public List<ComboForm> listaPrescrizioniMisuraPersonale();

	public List<ComboForm> listaProcedenti(long idUfficio, long tipoUfficioConnesso, long tipoUfficioRic);

	public List<ComboForm> listaProvinceAbi(String codiceAbi);
	
	public List<ComboForm> listaSettoriAttivita();

	public List<ComboForm> listaSottocategorieDelBene(BeneForm bene);

	public List<ComboForm> listaStatiConservazione(int tipo);

	public List<ComboForm> listaStatiDelProcedimento();

	public List<ComboForm> listaTipiDiProcedimento(int idTipoFascicolo);
	
	//Simone 03/02/2014
	public List<ComboForm> listaTipiFascicolo(Integer tipoFascicolo);
	//


	public List<ComboForm> listaTipiInteresse();

	/**
	 * listaTipiMisuraDecretoSequestro
	 * 
	 * @param carattereMisura
	 * @return List<ComboForm>
	 */
	public List<ComboForm> listaTipiMisuraDecretoSequestro(String carattereMisura);

	public List<ComboForm> listaTipiMisuraTipoP();

	public List<ComboForm> listaTipiMisuraTipoT();
	
	 public List<ComboForm> listaTipiMisuraTipoAP();

	public List<ComboForm> listaTipiPrevenzione();

	public List<ComboForm> listaTipiProcedente(int idUfficioUtente);

	public List<ComboForm> listaTipiStrada();

	public List<ComboForm> listaTipiTenore();
	
	public List<ComboForm> listaTipiValuta();

	public boolean localitaObbligatoriaNelTipoMisuraSelezionato(Long idTipoMisura,String carattereMisura);

	public List<ComboForm> getProcureDistrettualiPerCdA(int idUffOwn);

	public List<ComboForm> getUfficiDestinatari(long codAtto, long idUffOwn);

	public List<ComboForm> getProvinciaByOrgano(long idOrgano);
	
	public List<ComboForm> listaTipoCatasto();
	
	public List<ComboForm> listaTipoFondo();
	
	public List<ComboForm> listaTipoDenuncia();
	
	public List<ComboForm> listaTipoOccupanti();
	
	
	/**
	 * getTipoOrdineAmministratori
	 * @return List<ComboForm>
	 */
	public List<ComboForm> getTipoOrdineAmministratori();

	/**
	 * getSediOrdineAmministratori
	 * @param idTipoOrdineAmministratori
	 * @return List<ComboForm>
	 */
	/**
	 * getSediOrdineAmministratori
	 * @param idTipoOrdineAmministratori
	 * @return List<ComboForm>
	 */
	public List<ComboForm> getSediOrdineAmministratori(String idTipoOrdineAmministratori);
	
	public List<ComboForm> getAllTipoRuoliMagistratiCollegio();

	public List<ComboForm> getAllTipoModalitaPervenimento();

	public List<ComboForm> getTipoAttoAbilitati(long idProcedimento,int idUfficio, int idTipoProc, int idStatoProc);

	public List<ComboForm> getCategoriaAttoAbilitate(long idProcedimento,int idTipoUfficio, int idTipoProc, int idStatoProc, int idTipoAtto);

	public List<ComboForm> listaTipoIpotesi(int idTipoFascicolo, String tipoSoggetto);
	
	/**
	 * Restituisce la lista delle trasformazioni
	 * @param tipo trasformazioni T = Trasformazione;   L = Legame
	 * @return la lista delle trasformazioni
	 */
	public List<ComboForm> getTipoTrasformazioni(Character tipoTrasformazione);
	
	public ComboForm getTipologiaBene(String idTipologia);
	public ComboForm getCategoriaBene(String idTipologia);
	public ComboForm getSottoCategoriaBene(String idTipologia);
	public TTpCatAtto getCategoriaAtto(long idCatAtto);
	
	public ComboForm getDettaglioUfficio(long idUffOwn);
	
	/**
	 * restituisce la trelprocproc di un tipo ufficio specifico
	 * @param idProc
	 * @param idTipoUff
	 * @return List
	 */			
    public List getNumeProcTRelProcProc(long idProc,long idTipoUff);
    
    public List<ComboForm> getElencoUfficiDistretto(long idUfficio);
    
    public List<ComboForm> listaTipoModPerv();
    
    public ComboForm getTribunaleCompetente(long idProc);
    
    public KeyMessageDto controllaInvioProcuraPG(long idProc,long idUfficio,String descUtente,String funzione,String metodo);
    
    public KeyMessageDto controllaAperturaRicorsi(long idProc,long idUfficio,String descUtente,String funzione,String metodo);

	public List<ComboForm> getUfficiRevocazioneConfisca(long idUffOwn, long idTipoUfficio);
	
	public KeyMessageDto controllaPresenzaAttiInLavorazione(long idProc, long tipoAtto, long idUfficio, String descUtente, String funzione, String metodo);

	public String controlloIncompetenzaProcura(long idProcedimento);

	public TAnStato getStatoByIdCasellario(String codiceCasellario);

	public TAnComuni getComuneByIdCasellario(String codiceCasellario);
	
	public KeyMessageDto controllaRelMagistratoUfficio(int idUffOwn, long idMagistrato);
	
	public List<ComboForm> getTipoAtto();

	public List<ComboForm> getTipoNota();

	public List<ComboForm> getAllConservatoria();

	public List<ComboForm> getAllDescIstatStato();

    public List<ComboForm> getAllDescBelfStato();

    public List<ComboForm> getTipoRegime();
    
    public List<ComboForm> getListaUffici(int idUfficio);
    
    public HashMap getFascicoloGestione(long idProc, long idUffOwn, String descUtente);
    
    public List<ComboForm> getMagistratiByUff(long idUffOwn);
    
	public List<ComboForm> listaTipoUfficio();
	
	public List<ComboForm> listaUfficiTribunali();
	
    public List<ComboForm> getAllComuni();
    
    public List<ComboForm> getAllDistretti();

	public List<ComboForm> listaTipoIstituto();
	
	public TAnUfficio getTAnUfficioByIdUff(long idUffOwn);
	
	public TTpTipoMisura getTTpTipoMisuraById(long idTipoMIsura);
	public TAnNormative getTAnNormativeById(long idNormativa);
	public TTpTipoProc getTTpTipoProcById(long idTipoProc);
	public TTpTipoPrevProc getTTpTipoPrevProc(long codiKIdTipoNorm);
	public TAnComuni getComuneById(long id);
	public TAnComuni getComuneByDescCodiNsc(String descCodiNsc);
	public TAnComuni getComuneByDescIstatComune(String descIstatComune);

	public TAnStato getStatoById(int idStato);
	public TTpTipoBene getIdTipologiaBene(String tipologia);
	public TTpCatBene getIdCategoriaBene(TTpTipoBene tTipoBene, String categoria);
	public TTpSottocatBene getIdSottoCategoriaBene(TTpCatBene tCategoriaBene, String sottoCategoria);
	public List<TTpTipoFondo> listaTpTipoFondo();
	public List<TTpTipoCatasto> listaTpTipoCatasto();
	public TTpValuta getTpValuta(String codice);

	public TAnComuni getComuneByDescBelfiore(String descBelfiore);

	public SoggettoForm getSoggettoById(long idSoggetto, long idUfficio);

	public TAnStato getStatoByIdIstat(int intValue);
	
	public TAnUtenzePdoc getUtenzaPdoc(int idUffOwn);

	public String getVersioneDb(String softwareVersion);
	
	public List<ComboForm> listaSpeseProcessuali(); // mev spese processuali
	
	public TTpSpeseProcessuali ricercaSpeseProcessuali(int idSpesaP); // mev spese processuali

}
