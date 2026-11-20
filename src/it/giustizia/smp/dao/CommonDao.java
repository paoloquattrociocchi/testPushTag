package it.giustizia.smp.dao;

import it.giustizia.smp.dto.ChiaveRicorsoCassazioneDto;
import it.giustizia.smp.dto.KeyMessageDto;
import it.giustizia.smp.form.BeneForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.model.TAnAltriOper;
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

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CommonDao {

    public List<TAnComuni> getAllComuni();
    
    public List<TAnComuni> getAllComuneByProvincia(String codiceIstatProvincia);

    public List<TAnComuni> getAllComuniByOrganoAndProvincia(String codiceIstatProvincia, long idOrgano);

    public List<TAnComuni> getAllComunyStartingWith(String pattern);

    public List<TTpCondGiur> getAllCondizioneGiuridica();
    
    public List<TAnDistretto> getAllDistretti();

    public List<TAnIstitutoDet> getAllIstitutiDetenzione();

    public List<TTpProfessione> getAllProfessione();

    public List<TAnProvincia> getAllProvincia();

    public List<TAnStato> getAllStato();
    
    public List<TAnStato> getAllStatoPiva();

    public List<TTpTipoSocieta> getAllTipiSocieta();
    
    public List<TAnOperFinanz> getCodiciCab(String input, String codiceAbi, String provincia);

    public TAnComuni getComuneById(long id);

    public List<TTpEsitoUdienza> getEsitiUdienze();

    public TAnMagistrati getMagistratoById(long id);

    public List<TAnNormative> getNormativaByTipoPrevenzioneAndTipoMisura(SoggettoForm soggettoForm);
    
    public List<TTpTipoPrevProc> getNormativaByTipoProcedAndTipologiaPrevenzione(SoggettoForm soggetto);
    
    public List<TAnOperFinanz> getOperatoriFinanziari(final String pattern);

	public List<TAnAltriOper> getOperatoriFinanziariPolizze(final String pattern);

    public TAnUfficio getProcuraDDA(long idUffOwn);
    
    public TAnUfficio getProcuraGenerale(long idUffOwn);
    
    public TAnUfficio getCDA(final long idUffOwn);

    public TAnUfficio getProcuraInUdienza(long idUffOwn);
    
    public TAnUfficio getProcuraInUdienzaAssociata(final long idProc);

    public TAnUfficio getProcuraProponente(long idProc);

    public List<TAnUfficio> getProcureDistrettualiByTribComp(long idUffOwn, boolean excludeDDA);
    
    public List<ComboForm> getTipiSostituzioneMagistrato();
    
    public List<TTpTipoAtto> getTipoAtto();

    public List<TTpTipoNota> getTipoNota();

    public TTpTipoUfficio getTipoUfficioById(Integer id);

    //public TAnUfficio getTribunaleCompetenza(long idUffOwn);

    //public List<TAnUfficio> getTribunaliDistrettuali(long idUffOwn, long idProc);
    
    public List<TRelProcTrib> getRelProcTrib(long idUfficio, String tipoUfficio, long idTipoPrev, String flgDda, Long excludeIdUfficio);

    public List<Map> getUfficiCompetenza(long idProc, Long idTipoUff);
 
	//ricerca gli uffici legati al procedimento (T_REL_PROC_PROC), e nel caso in cui esistano più uffici
	//dello stesso tipo, privilegia quello legato all'atto origine dell'atto in parametro
	public List<Map> getUfficiCompetenzaDistinctTipoUff(long idProc, Long idAtto, Long idUffOwn, long excludeTipoUff);

	public TAnUfficio getUfficioCompetenzaAggravamento(final long idProcPadre);
	
	//ricerca l'ufficio legato all'atto origine
	public TAnUfficio getUfficioAttoOrigine(long idProc, long idAttoFi, long idUffOwn);

    public List<Map> getAllUfficiCompetenza(long idProc, Long idTipoUff);

    public List<TAnUfficio> getUfficiDestinatariIncompetenzaProcura(long idProcedimento);

    public List<TTpCatCatasto> listaCategorieCatastali();

    public List<TTpCatBene> listaCategorieDelBene(BeneForm bene);

    public List<TTpTipoBene> listaDellaTipologiaDelBene();

    public List<TTpTipoDisp> listaDisponibilita();

    public List<Long> listaIdMagistratiByUfficio(int idUfficioUtente);

    public List<TTpTipoMisura> listaPrescrizioniMisuraPersonale();

    public List<TAnProcedente> listaProcedenti(long idUfficio, long tipoUfficioConnesso, long tipoUfficioRic);
    
    public List<Map<String, Object>> listaProvinceAbi(String codiceAbi);

    public List<TTpSettoreAtt> listaSettoreAttivita();

    public List<TTpSettoreAtt> listaSettoriAttivita();

    public List<TTpSottocatBene> listaSottocategorieDelBene(BeneForm bene);

    public List<TTpStatoConserv> listaStatiConservazione(int tipo);

    public List<TTpStatoProc> listaStatiDelProcedimento();

    public List<TTpTipoProc> listaTipiDiProcedimento(int idTipoFascicolo);
    
    //simone 03/02/2014
    public List<TTpTipoFascicolo> listaTipiFascicolo(Integer tipoFascicolo);
    //
    
    public List<TTpTipoIpotesi> listaTipoIpotesi(int idTipoFascicolo, String tipoSoggetto);

    public List<TTpTipoInteresse> listaTipiInteresse();

    public List<TTpTipoMisura> listaTipiMisura(String carattereMisura);

    public List<TTpTipoMisura> listaTipiMisuraDecretoSequestro(String carattereMisura);

    public List<TTpTipoMisura> listaTipiMisuraTipoT();

    public List<TTpTipoPrevenzione> listaTipiPrevenzione();

    public List<TTpTipoUfficio> listaTipiProcedenteByUfficio(int idUfficioUtente);

    public List<TTpTipoStrada> listaTipiStrada();

//    public List<TTenore> listaTipiTenore();
    
    public List<TTpValuta> listaValuta();

    public List<TTpTipoVincolo> listaVincoli(); 
    
    public boolean existMisurePersonali(long idProc, long idAtto);
    
    public boolean existMisurePatrimoniali(long idProc, long idAtto);

	public List<TAnUfficio> getProcureDistrettualiByCdA(int idUffOwn);

	public List<TAnUfficio> getUfficiDestinatari(long codAtto, long idUffOwn);

	public List<TAnProvincia> getProvinciaByOrgano(long idOrgano);
	
	public List<TTpTipoCatasto> listaTipoCatasto();
	 
	public List<TTpTipoFondo> listaTipoFondo();
	 
    public List<TTpTipoDenuncia> listaTipoDenuncia();
	 
	public List<TTpTipoOccupanti> listaTipoOccupanti();
	
	/**
	 * getTipoOrdineAmministratori
	 * @return List<ComboForm>
	 */
	public List<TTpOrdineProf> getListaOrdineAmministratori();
	
	/**
	 * getSediOrdineAmministratori
	 * @param idOrdine
	 * @return
	 */
	public List<TTpSedeOrdine> getSediOrdineAmministratori(String idOrdine);	
	
	public List<TTpTipoRuoloMag> getAllTipoRuoliMagistratiCollegio();

	public List<TTpModPerv> getAllTipoModalitaPervenimento();

	//public List<TTpTipoAtto> getTipoAttoAbilitati(int idTipoUfficio, int idTipoProc, int idStatoProc);
	public List getTipoAttoAbilitati( long idProcedimento,  int idTipoUfficio, int idTipoProc, int idStatoProc);


	//public List<TTpCatAtto> getCategoriaAttoAbilitate(int idTipoUfficio, int idTipoProc, int idStatoProc, int idCatAtto);
	public List getCategoriaAttoAbilitate( long idProcedimento,  int idTipoUfficio, int idTipoProc, int idStatoProc, int idTipoAtto);

	/**
	 * Restituisce la lista delle trasformazioni
	 * @param tipo trasformazioni T = Trasformazione;   L = Legame
	 * @return la lista delle trasformazioni
	 */
	public List<TTpTipoTrasform> getTipoTrasformazioni(Character tipoTrasformazione);
	
	public TTpTipoBene getTipologiaBene(int idTipologia);
	public TTpCatBene getCategoriaBene(int idCategoria);
	public TTpSottocatBene getSottoCategoriaBene(int idSottoCategoria);
	
	public TTpCatAtto getCategoriaAtto(long idCatAtto);
	
	public TAnUfficio getDettaglioUfficio(long idUff);
	
	
	/**
	 * restituisce la trelprocproc di un tipo ufficio specifico
	 * @param idProc
	 * @param idTipoUff
	 * @return TRelProcProc
	 */
	public List getNumeProcTRelProcProc( long idProc, long idTipoUff) ;	
	
	public List<TAnUfficio> getElencoUfficiDistretto(long idUfficio);
	
	public List<TTpModPerv> listaTipoModPerv();
	
	public TAnUfficio getTribunaleCompetente(final long idProc);
	
	public KeyMessageDto controllaInvioProcuraPG(long idProc,long idUfficio,String descUtente,String funzione,String metodo);
	
	public KeyMessageDto controllaAperturaRicorsi(long idProc,long idUfficio,String descUtente,String funzione,String metodo);
	
	public List getUfficiRevocazioneConfisca(long idUffOwn, long idTipoUfficio); 
	
	public KeyMessageDto controllaPresenzaAttiInLavorazione(final long idProc, final long tipoAtto, final long idUfficio, final String descUtente, final String funzione, final String metodo);
	
	public String controlloIncompetenzaProcura(long idProcedimento);

	public TTpTipoAtto getTipoAttoById(int idTipoAtto);
	
	public TAnUfficio getUfficioById(int idUfficio);

	public TAnStato getStatoById(int idStato);

	public TTpCatAtto getCatAttoById(int idCatAtto);

	public TTpTipoMisura getTipoMisuraById(int idTipoMisura);

	public TAnStato getStatoByIdCasellario(String codiceCasellario);

	public TAnComuni getComuneByIdCasellario(String codiceCasellario); 
	
	public KeyMessageDto controllaRelMagistratoUfficio(final int idUffOwn, final long idMagistrato);

	public List<TAnConservatorie> getAllConservatoria();

    public List<TTpTipoRegime> getTipoRegime();

    public List<TAnUfficio> getListaUffici(int idUfficio)  ;

    public List<TAnUfficio> getElencoUfficiCdaTribunali(int idDistretto) ;

	public void inserisciInTCassMsg(int idUffOwn, long idAttoImpugnazione, long idProcedimento, String descUtente);

	public void modificaTCassMsg(int idUffOwn, long idAttoImpugnazione, long idProcedimento, char c, String string, byte[] requestXML);

	public List<ChiaveRicorsoCassazioneDto> getListaRicorsiDaInviare();

	public List<TAnUfficio> getAllUfficiByOrganoAndComune(int codiceComune, long idOrgano);

	public HashMap getFascicoloGestione(long idProc, long idUffOwn, String descUtente);
	
	public List<HashMap> getMagistratiByUff(long idUffOwn);	
	
	public List<HashMap> getMagistratiById(long idMagistrato);	
	
	public List<TTpTipoUfficio> listaTipoUfficio();
	
	public List<TAnUfficio> listaUfficiTribunali();
	
	public List<TTpTipoIstituto> listaTipoIstituto();

    public TTpTipoIstituto getTipoIstitutoById(int id);
    
    public TAnDistretto getDistrettoById(int id);

	public TTpValuta getTpValuta(final String codice);

	public <T> T getEntity(Class<T> clazz, Serializable id);
	
	public int getIdDocumentale(String codugna);

	public TTpTipoUfficio getTipoUfficioByIdUfficio(int idUffOwn);
	
	public TAnUtenzePdoc getAutenticazioneRandom();

	public TTpTipoMisura getTTpTipoMisuraById(long idTipoMIsura);
	public TAnNormative getTAnNormativeById(long idNormativa);
	public TTpTipoProc getTTpTipoProcById(long idTipoProc);
	public TTpTipoPrevProc getTTpTipoPrevProc(long codiKIdTipoNorm);
	public TAnComuni getComuneByDescCodiNsc(String descCodiNsc);
	public TAnComuni getComuneByDescIstatComune(String descIstatComune);
	
	public TTpTipoBene getIdTipologiaBene(String tipologia);
	public TTpCatBene getIdCategoriaBene(TTpTipoBene tTipoBene, String categoria);
	public TTpSottocatBene getIdSottoCategoriaBene(TTpCatBene tCategoriaBene, String sottoCategoria);
	public TAnComuni getComuneByDescBelfiore(String descBelfiore);

	public TPersonaFisica getTPersonaFisicaById(long idSoggetto, long idUfficio);

	public TTpTipoStrada getTTpTipoStrada(int idTipologia);
	public TTpTipoStrada getTTpTipoStradaByDesc(String descStrada);

	public TAnStato getStatoByIdIstat(int idStatoIstat);
	
	public TAnUtenzePdoc getUtenzaPdoc(int idUffOwn);
	
	public TAnUtenzePdoc getUtenzaPdoc(String codUgna);
	
	//novembre 2019 - versione db
	public String getVersioneDb(String softwareVersion);
	
	//marzo 2020 - gestione spese processuali 
	public List<TTpSpeseProcessuali> listaSpeseProcessuali();

	public TTpSpeseProcessuali ricercaSpeseProcessuali(int idSpesaP);
}
