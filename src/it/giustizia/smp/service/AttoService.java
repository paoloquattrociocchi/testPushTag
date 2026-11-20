package it.giustizia.smp.service;

import it.giustizia.smp.dto.KeyMessageDto;
import it.giustizia.smp.dto.pg_atto.ImpugnazioneCassellarioDTO;
import it.giustizia.smp.dto.pg_atto.RedazioneSchedaDto;
import it.giustizia.smp.form.AllegatoForm;
import it.giustizia.smp.form.AmministratoreForm;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.BeneForm;
import it.giustizia.smp.form.CertificatoPenaleForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.ComunicazioneForm;
import it.giustizia.smp.form.DifensoreForm;
import it.giustizia.smp.form.ImpugnazioneForm;
import it.giustizia.smp.form.MagistratoForm;
import it.giustizia.smp.form.NotificaAttoForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.form.SoggettoStralcioForm;
import it.giustizia.smp.form.UdienzaForm;
import it.giustizia.smp.model.TAtto;
import it.giustizia.smp.model.TAttoGenerico;
import it.giustizia.smp.model.TAvvocati;
import it.giustizia.smp.model.TCertificatoPenale;
import it.giustizia.smp.model.TComunicazione;
import it.giustizia.smp.model.TDecreto;
import it.giustizia.smp.model.TDefIstGen;
import it.giustizia.smp.model.TDefIstGenId;
import it.giustizia.smp.model.TRelAtti;
import it.giustizia.smp.model.TRelAttoMisPers;
import it.giustizia.smp.model.TTpCatAtto;
import it.giustizia.smp.webservice.client.documentale.model.general.stralcio.Documento;

import java.math.BigInteger;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;

/**
 * AttoService
 * 
 * @author Almaviva S.p.A.
 *         Interfaccia Atto Service
 */
@SuppressWarnings("rawtypes")
public interface AttoService {

	public Long aggiornaIstanzaSoggettoDiffidato(long idUffOwn, long idProcedimento, AttoForm attoForm, String codUtente);
	
	public void aggiornaAccertamento(long idUfficio, long idProcedimento, AttoForm form, String codiceUtente, long idStatoProc);

	public void copiaMisBeneAtto(long idProcedimento, long idAttoDecreto, long idBene, int idUffOwn, String idCodiceUtente, String flagRevoca, String nomeMetodo);

	public AttoForm dettaglioAtto(int idUffOwn, long idAtto, long idProcedimento, String codiceUtente);

	public void eliminaMisBeneAtto(long idProcedimento, long idAtto, long idBene, int idUffOwn, long idPersona, long idTitoloInt);

	public void eliminaRelMagAtto(Integer idUffOwn, long idAtto, long idMagistrato, String descRuolo);

	public void esecuzioneAttoRegistrato(Date dataEsecuzione, List<BeneForm> beniSelezionati, long idProcedimento, int idUfficioUtente, long idAtto, String codUtente);

	public List<AttoForm> getAttiByProcedimento(long idProcedimento, long tipoAtto, String listaCategoriaAtto);
	
	public List<AttoForm> getAttiOpposizioneDissequestro(long idProcedimento);
	
	public List<AttoForm> getAttiPerditaEfficaciaSequestro(long idProcedimento);
	
	public List<AttoForm> getAttiRevocaOpposizioneDissequestro(long idProcedimento);
	
	public List<AttoForm> getAttiRevocazioneConfisca(long idProcedimento);
	
	public List<AttoForm> getAttiParere(long idProcedimento);
	
	public List<AttoForm> getAttiOrdineCronologico(final long idProcedimento, final String codiceUtente, final String nomeMetodo, final Boolean filtraRicevute);
	
	public List<AttoForm> getElencoAttiTribunale(final long idProcedimento, final String codiceUtente, final String nomeMetodo, final Boolean filtraRicevute);

	public List<AttoForm> getElencoAttiCda(long idProcedimento, String codiceUtente, String nomeMetodo);	
	
	public List<AttoForm> getAttiOrdineCronologico(long idProcedimento, String codiceUtente, String nomeMetodo, String tipologiaAtto);

	public AttoForm getAttoById(long idAtto, long idUfficio);
	
	public AttoForm getTRelProcSogintAttoById(long idAtto, long idUfficio,long idProc);

	public MagistratoForm getDettaglioMagistratoAtto(Integer idUffOwn, long idAtto, long idMagistrato, String descRuolo);

	public Long getIdAttoAggiornatoPerPadreAggravamento(Long idPadre, Integer idUffOwn);

	public UdienzaForm getUdienzaById(long codiceUdienza, long codiceUfficio);

	public void inserisciMagistratoPerAtto(Integer idUffOwn, long idAtto, long idMagistrato, String tipoRuoloMag, String flgRelatore, String codiUtente);

	public long inviaAtto(long idProcedimento, long idUfficio, String codiceUtente, long idStatoAtto, AttoForm attoForm, long idDest, long idUdienza);

	public Long inviaAttoDecretoSequestro(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long tipoAtto,long categoriaAtto, long idStatoAtto, long idDestinatario, long idUltDestinatario, long codUdienzaAtto, long codUdienzaFiss, long codUdienzaInFaseDiRip, long codUdienzaInFaseDiRim, long statoUdienzaPrec, long idMagistrato, String giudiceDelegato);

	public Long aggiornaAttoDecretoSequestro(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long tipoAtto,long categoriaAtto, long idStatoAtto, long idDestinatario, long idUltDestinatario);
	
	public Long inviaAttoDecretoConvalidaSequestro(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long tipoAtto,long categoriaAtto, long idStatoAtto, long idDestinatario, long idUltDestinatario, long codUdienzaAtto, long codUdienzaFiss, long codUdienzaInFaseDiRip, long codUdienzaInFaseDiRim, long statoUdienzaPrec, String nomeMetodo);
	
	public Long inviaDecretoPerditaEfficaciaSequestro(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long tipoAtto,long categoriaAtto, long idStatoAtto, long codUdienzaAtto, long codUdienzaFiss, long codUdienzaInFaseDiRip, long codUdienzaInFaseDiRim, long statoUdienzaPrec, String nomeMetodo);
	
	public Long inviaAttoDecretoRevocaSequestro(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long tipoAtto,long categoriaAtto, long idStatoAtto, long idDestinatario, long idUltDestinatario, long codUdienzaAtto, long codUdienzaFiss, long codUdienzaInFaseDiRip, long codUdienzaInFaseDiRim, long statoUdienzaPrec, String nomeMetodo);

	public Long inviaAttoDecretoOpposizioneDissequestro(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long tipoAtto,long categoriaAtto, long idStatoAtto, long idDestinatario, String nomeMetodo);
	
	public Long inviaAttoDecretoRevocaOpposizioneDissequestro(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long tipoAtto,long categoriaAtto, long idStatoAtto, long idDestinatario, String nomeMetodo);
	
	public Long inviaAttoRevocazioneConfisca(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long tipoAtto,long categoriaAtto, long idStatoAtto, long idDestinatario, String nomeMetodo);
	
	public long inviaDecretoIncompetenza(long idProcedimento, long idAtto, long idMittente, long idDestinatario1, long idDestinatario2, long idDestinatario3, String idCodiceUtente, long idUfficio, long categoriaAtto, String nomeMetodo);

	public void inviaDecretoIncompetenzaProcura(long idProcedimento, long idAtto, long idMittente, long idDestinatarioProposta, long idDestinatario2, long idDestinatario3, String idCodiceUtente, long idUfficio, long categoriaAtto, String nomeMetodo);

	public long inviaDefinitivita(AttoForm attoForm, String codUtente, long idUfficio, ProcedimentoForm procedimentoForm, long idUffProcProp, Long idDestinatario);
	
	public void inviaDefinitivitaDecisorioIGradoRiesame(AttoForm attoForm, String codUtente, long idUffOwn, ProcedimentoForm procedimentoForm, long idProcUdienza, long idProcGenerale);

	public void inviaUdienza(long idUfficio, String codiceUtente, long idProcedimento, long idTipoAtto, long idCategoriaAtto, AttoForm udienza, long idDest);

	public List<BeneForm> listaBeniNoMisPatr(long idUffOwn, long idProcedimento, long idAtto);

	public List<BeneForm> listaBeniNoTitDir(long idUffOwn, long idProcedimento, long idAtto);

	/**
	 * listaMagistratiPerAtto
	 * 
	 * @param idUffOwn
	 * @param idAtto
	 * @return List<MagistratoForm>
	 */
	public List<MagistratoForm> listaMagistratiPerAtto(int idUffOwn, long idAtto, int ruoloMag);
	
	/**
	 * modificaAtto
	 * 
	 * @param attoForm
	 * @param idStatoAtto
	 * @param test 
	 * @return long
	 */
	// Specifiche Intervento-006-SpeseProces-NumAtto: aggiunto parametro di passaggio
	public long modificaAtto(int idUffOwn, AttoForm attoForm, long idStatoAtto, boolean test);

	/**
	 * modificaMagistratoPerAtto
	 * 
	 * @param idUffOwn
	 * @param idAtto
	 * @param idMagistrato
	 * @param descRuolo
	 * @param descNuovoRuolo
	 * @param flgRelatore
	 */
	public void modificaMagistratoPerAtto(Integer idUffOwn, long idAtto, long idMagistrato, String descRuolo, String descNuovoRuolo, String flgRelatore);

	public void modificaUdienza(long idUfficio, UdienzaForm udienza);

	/**
	 * salvaAtto
	 * 
	 * @param idUffOwn
	 * @param idProcedimento
	 * @param codiceUtente
	 * @param attoForm
	 * @param idStatoAtto
	 * @return long
	 */
	public long salvaAtto(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm, long idStatoAtto);

	public long salvaAttoDecretoConMagistrato(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm, long idStatoAtto);

	public long salvaAttoDecretoDecisorio(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm, long idStatoAtto, long idAttoOrigineMisure);
	
	public long salvaAttoSentenzaRicorsoIIGrado(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm, long idStatoAtto, long idAttoOrigineMisure);
	
	/**
	 * @param idUfficio
	 * @param codiceUtente
	 * @param idProcedimento
	 * @param idTipoAtto
	 * @param idCategoriaAtto
	 * @param udienza
	 * @return
	 */
	public Map salvaUdienza(long idUfficio, String codiceUtente, long idProcedimento, long idTipoAtto, long idCategoriaAtto, AttoForm udienza);

	/**
	 * @param idUfficio
	 * @param codiceUtente
	 * @param idProcedimento
	 * @param idTipoAtto
	 * @param idCategoriaAtto
	 * @param udienza
	 * @return
	 */
	public Map inserisciDecretoAnticipazioneRinvioUdienza(long idUfficio, String codiceUtente, long idProcedimento, long idTipoAtto, long idCategoriaAtto, AttoForm udienza);
	
	/**
	 * aggiornaIstanzaSoggettoDiffidato
	 * 
	 * @param idUffOwn
	 * @param idProcInUdienza
	 * @param idProcGenerale
	 * @param idProcedimento
	 * @param attoForm
	 * @param codUtente
	 * @return long
	 */
	public Long aggiornaDecisorioIGradoSoggettoDiffidato(long idUffOwn, long idProcInUdienza, long idProcGenerale, long idProcedimento, AttoForm attoForm, String codUtente);

	/**
	 * aggiornaDecisorioIGradoPrevenzione
	 * 
	 * @param idUffOwn
	 * @param idProcInUdienza
	 * @param idProcGenerale
	 * @param idProcProponente
	 * @param idProcedimento
	 * @param attoForm
	 * @param codUtente
	 * @return long
	 */
	public Long aggiornaDecisorioPrevenzione(long idUffOwn, long idProcInUdienza, long idProcGenerale, long idProcProponente, long idProcedimento, AttoForm attoForm, String codUtente,int tipoUff, long idMagistrato, String giudiceDelegato);

	/**
	 * aggiornaSentenzaOpposizioneDissequestro
	 * 
	 * @param idUffOwn
	 * @param idProcInUdienza
	 * @param idTribunale
	 * @param idPg_Cda
	 * @param idProcedimento
	 * @param attoForm
	 * @param codUtente
	 * @return long
	 */
	public Long aggiornaSentenzaOpposizioneDissequestro(long idUffOwn, long idUff1, long idUff2, long idUff3, long idProcedimento, AttoForm attoForm, String codUtente);

	/**
	 * aggiornaSentenzaRevocazioneConfisca
	 * 
	 * @param idUffOwn
	 * @param idProcInUdienza
	 * @param idTribunale
	 * @param idPg_Cda
	 * @param idProcedimento
	 * @param attoForm
	 * @param codUtente
	 * @return long
	 */
	public Long aggiornaSentenzaRevocazioneConfisca(long idUffOwn, long idUff1, long idUff2, long idUff3, long idProcedimento, AttoForm attoForm, String codUtente);

	/**
	 * @param udienzaForm
	 * @param idUdienzaPrecedente
	 * @param idUffOwn
	 * @param idProcInUdienza
	 * @param idProcedimento
	 * @param codUtente
	 * @return
	 */
	public Long aggiornaDecretoAnticipazioneRinvioUdienza(UdienzaForm udienzaForm, long idUdienzaPrecedente, int idUffOwn, long idProcInUdienza, long idProcedimento,  String codUtente) ;
	
	 /**
     * definitivitaAttoRegistratoSalva
     * 
     * @param dataDefinitivita
     * @param misurePatrimoniali
     * @param misurePersonali
     * @param idProcedimento
     * @param idUfficioUtente
     * @param idAtto
     * @param tipoAtto
     * @param codUtente
     * @param tipo
     * @return void
     */
    public void definitivitaAttoRegistratoSalva(Date dataDefinitivita, List<BeneForm> misurePatrimoniali, List<SoggettoForm> misurePersonali, long idProcedimento, Integer idUfficioUtente, long idAtto, long tipoAtto, String codUtente, String tipo);
    
    /**
     * definitivitaCancella
     * @param misurePatrimoniali
     * @param misurePersonali
     * @param idProcedimento
     * @param idUfficioUtente
     * @param idAtto
     * @param tipoAtto
     * @param codUtente
     * @param tipo
     */
	public void definitivitaCancella(List<BeneForm> misurePatrimoniali, List<SoggettoForm> misurePersonali,
			long idProcedimento, Integer idUfficioUtente, long idAtto, long tipoAtto, String codUtente, String tipo);    
    
	
	
    public void definitivitaFascGestAttoRegistratoSalva(Date dataDefinitivita, List<BeneForm> misurePatrimoniali, 
			long idProcedimento, Integer idUfficioUtente, long idAtto, long tipoAtto, String codUtente, String tipo);
    
	public void definitivitaFascGestCancella( List<BeneForm> misurePatrimoniali, 
			long idProcedimento, Integer idUfficioUtente, long idAtto, long tipoAtto, String codUtente, String tipo);     
    
    /**
     * definitivitaAttoRegistratoConfermaeInvia
     * 
     * @param idProcedimento
     * @param idAtto
     * @param idUfficioUtente
     * @param idProcUdi
     * @param idProcProp
     * @param idProcGen
     * @param codUtente
     * @param aggiornaStatoProc
     * @return void
     */
    public void definitivitaAttoRegistratoConfermaeInvia(long idProcedimento, long idAtto, Integer idUfficioUtente, long idProcUdi, long idProcProp, long idProcGen, String codUtente, int aggiornaStatoProc);
    
    public void definitivitaAttoRegistratoConfermaeInviaData(long idProcedimento, long idAtto, Integer idUfficioUtente, long idProcUdi, long idProcProp, long idProcGen, String codUtente, int aggiornaStatoProc, Date dataDef);
    
    public void definitivitaRevocazioneConfiscaConfermaInvia(long idProcedimento, long idAtto, Integer idUffOwn, long idDest1, long idDest2, long idDest3, String codUtente, int aggiornaStatoProc);

    public void definitivitaIIGradoConfermaInvia(long idProcedimento, long idAtto, Integer idUffOwn, long idDest1, long idDest2, long idDest3, String codUtente, int aggiornaStatoProc);
    
    /**
     * @param idUffOwn
     * @param idProcedimento
     * @param codiceUtente
     * @param attoForm
     * @param idStatoAtto
     * @param idAttoOrigine
     * @return
     */
    public long inserisciAttoDecretoConvalidaSequestro(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm, long idStatoAtto, long idAttoOrigine, String nomeMetodo);
  
    /**
     * inserisciDecretoPerditaEfficacia
     * @param idUffOwn
     * @param idProcedimento
     * @param codiceUtente
     * @param attoForm
     * @param idStatoAtto
     * @param idAttoOrigine
     * @param nomeMetodo
     * @return
     */
    
    public long inserisciDecretoPerditaEfficacia(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm, long idStatoAtto, long idAttoOrigine, String nomeMetodo);
        
    
    /**
     * @param idUffOwn
     * @param idProcedimento
     * @param codiceUtente
     * @param attoForm
     * @param idStatoAtto
     * @param idAttoOrigine
     * @return
     */
    public long inserisciAttoDecretoOpposizioneDissequestro(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm, long idStatoAtto, long idAttoOrigine, String nomeMetodo);

    public long inserisciAttoRevocaDecretoOpposizioneDissequestro(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm, long idStatoAtto, long idAttoOrigine, String nomeMetodo);
    
    public long inserisciAttoRevocazioneConfisca(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm, long idStatoAtto, long idAttoOrigine, String nomeMetodo);
    
    public long inserisciAttoSentenzaOpposizioneDissequestro(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm, long idStatoAtto, int idCatAtto, String nomeMetodo);

    public long inserisciAttoSentenzaRevocazioneConfisca(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm, long idStatoAtto, int idCatAtto, String nomeMetodo);
    
    /**
     * @param idUffOwn
     * @param idProcedimento
     * @param codiceUtente
     * @param attoForm
     * @param idStatoAtto
     * @param idAttoOrigine
     * @return
     */
    public long inserisciAttoDecretoRevocaSequestro(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm, long idStatoAtto, long idAttoOrigine, String nomeMetodo);
    
    public void convalidaNonConvalidaSequestro(List<BeneForm> beniSelezionati, long idProcedimento, int idUffOwn, long idAtto, long idTipoMisura, String descUtente, String tipoOperazione, String nomeMetodo);
    
    /**
     * @param idAttoOrigine
     * @param idUffOwn
     * @return
     */
    public boolean esisteDecretoConvalidaSequesto(long idAttoOrigine, long idUffOwn);

    /**
     * @param idUfficio
     * @param codiceUtente
     * @param idProcedimento
     * @param codAtto
     * @param codUdienzaFiss
     * @param codUdienzaVerbRis
     * @param udienzaForm
     * @return
     */
    public Map<String, Long> salvaUdienzaDecretoSeqConv(long idUfficio, String codiceUtente, long idProcedimento, long codAtto, long codUdienzaFiss, long codUdienzaVerbRis, UdienzaForm udienzaForm);

    /**
     * @param idUdienza
     * @param codAtto
     * @param idUfficio
     * @return
     */
    public void eliminaUdienzaSeqConv(long idUdienza, long codAtto, long idUfficio);
    
    /**
     * getAttoById
     * 
     * @param idCatAtto
     * @return TCatAtto
     */
    public TTpCatAtto getCatAttoById(int idCatAtto);

    public Map<String, Long> inserisciAttoDecretoAsseRemNuovoRuolo(long idUfficio, String codUtente, Long idProcedimento, long idTipoAtto, long idCategoriaAtto, UdienzaForm udienzaForm);

    public Map<String, Long> salvaUdienzaDecretoRiemissioneSuRuolo(long idUfficio, String codiceUtente, long idProcedimento, long codAtto, UdienzaForm udienzaForm);
    
    public long inviaAttoDecretoAssegnazioneNuovoRuolo(AttoForm attoForm, long idStatoAtto, int idUffOwn, Long idProcedimento, String codiceUtente, long idProcUdi, long codUdienza);

    public long inviaAttoDecretoRemissioneSuRuolo(AttoForm attoForm, long idStatoAtto, int idUffOwn, Long idProcedimento, String codiceUtente, long idProcUdi, long codUdienzaPrec, long codUdienzaNew);

    /**
     * Inserisce un atto e un atto generico
     * @param idUfficio
     * @param codUtente
     * @param idProcedimento
     * @param attoForm
     * @return
     */
    public long inserisciAttoGenerico(int idUfficio, String codUtente, Long idProcedimento, AttoForm attoForm, long idStatoAtto, String nomeMetodo);
    
    public Object getAttoByPrimaryKey(Class<?> clazz, Criterion c1, Criterion c2);
    
    public TAtto getTAttoById(long idAtto, long idUfficio);

    public boolean firmaRemotaDocumento(final AttoForm attoForm, final long idDocumentale, final long idUfficio);
    
    public void rimuoviDocumentoAllegatoDaAtto(long idUfficio, long codAtto, BigInteger numeDocumentoPdoc, long idProcedimento);

	//P.Q. 05/07/2022 Ticket 20220627018 Inizio
    public AllegatoForm extractBlobFromDocumentazione(Long numeroAttoPDoc, int idUffOwn);
	//P.Q. 05/07/2022 Ticket 20220627018 Fine
    
    public void rimuoviDocumentoAllegatoDaTAtto(final long idUfficio, final long codAtto, final long idProcedimento);
    
    public KeyMessageDto cancellaAtto(final int idUfficio, final long codAtto, final String descUtente, final String nomeMetodo);
    
    public ArrayList<Documento> getDocumentiAttiByProcedimento(long idProcedimento);
    
    public ArrayList<BigInteger> getIdsDocumentiAttiByProcedimento(long idProcedimento);
    
    public List<TRelAtti> getRelAtti(long idAttoOrigine, int idUffOwnerOrigine, long idAttoFinale, int idUffOwnerFinale, String descUtente, String nomeMetodo);
    
    public String numeroAttoOrigine(long idProcedimento, long idAttoFinale, int idUffOwnerFinale, String descUtente, String nomeMetodo);
    
    public long idAttoOrigine(long idProcedimento, long idAttoFinale, int idUffOwnerFinale, String descUtente, String nomeMetodo);
    
  	public KeyMessageDto esisteDecretoSequestroProcedimento(int idUffOwn, long idProcedimento, String codiceUtente, long idStatoAtto, String nomeMetodo);

  	public long salvaDecretoRigettoTotaleSequestro(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm, long idStatoAtto);

  	public KeyMessageDto insDecretoRigettoTotaleSequestro(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm, long idStatoAtto, String nomeMetodo);
    
    public Long inviaAttoDecretoRigettoTotaleSequestro(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long tipoAtto,long categoriaAtto, long idStatoAtto, long idDestinatario, long idUltDestinatario, long codUdienzaAtto, long codUdienzaFiss, long codUdienzaInFaseDiRip, long codUdienzaInFaseDiRim, long statoUdienzaPrec, String nomeMetodo);
    
    public String getNumeroProcedimentoOrigine(long idProcedimento, long idAtto, int idUffOwn, String codiceUtente, String nomeMetodo);
    
    public Long registraAttoGenerico(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long idStatoAtto, String nomeMetodo);

    public Long registraOrdinanazaArt14Antimafia(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long idStatoAtto, String nomeMetodo);
   
    public Long registraIntercettazioniTelefoniche(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long idStatoAtto, String nomeMetodo);   
    
    public Long registraOrdinanazaArt15Antimafia(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long idStatoAtto, String nomeMetodo);
 
    //MODIFICA 01/2014 - Gestione controllo collegio Presidenziale/Collegiale
	public KeyMessageDto controllaCollegioAtto(long idProcedimento, String idCatAtto, String descUtente, long idUffOwn, String printMethodName);

	public KeyMessageDto controllaCollegioAttoInvio(long idProcedimento, long idAtto, String descUtente, long idUfficio, String printMethodName);
	//MODIFICA 01/2014
	
	 public MagistratoForm getMagistratoPerAtto(final Integer idUffOwn, final long idAtto, final int descRuolo);
	
	/**
	 * getRicercaSoggettoAtto
	 * @param idProcedimento
	 * @param descRuolo
	 * @param flagValido
	 * @return List<SoggettoForm>
	 */
	
    public List<SoggettoForm> getRicercaSoggettoAtto(long idProcedimento,long idatto,int iduff,int descRuolo, Character flagValido);	
    
    /**
	 * getRicercaSoggettoAttoSpeseProcessuali
	 * @param idProcedimento
	 * @param descRuolo
	 * @param flagValido
	 * @return List<SoggettoForm>
	 */
	
    public List<SoggettoForm> getRicercaSoggettoAttoSpeseProc(long idProcedimento,long idatto,int iduff,int descRuolo, Character flagValido, List<ComboForm> listaSpeseProcessuali);	
    
    
    /**
     * getRicercaSoggettoRicorso
     * @param idProcedimento
     * @param idatto
     * @param iduff
     * @param descRuolo
     * @return List<SoggettoForm>
     */
	public List<SoggettoForm> getRicercaSoggettoRicorso(long idProcedimento, long idatto, int iduff, int descRuolo);

   /**
    * getRicercaAvvRicorso
    * @param idProcedimento
    * @param idatto
    * @param iduff
    * @return List<DifensoreForm>
    */
	public List<DifensoreForm> getRicercaAvvRicorso(long idProcedimento,long idatto, int iduff); 
	
	
    /**
     * getRicercaMisureAtto
     * @param idProcedimento
     * @param idUfficio
     * @param attoForm
     * @param flgValido
     * @return
     */
		
    	public List<SoggettoStralcioForm> getRicercaMisureAtto(long idProcedimento, long idUfficio, AttoForm attoForm, String flgValido,int categoriaAtto); 
    	
	/**
	 * salvaAttoRicorso
	 * @param idUffOwn
	 * @param idProcedimento
	 * @param codiceUtente
	 * @param attoForm
	 * @param idStatoAtto
	 * @param idAttoOrigineMisure
	 * @return long id atto originato
	 */
       public long salvaAttoRicorso(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm, long idStatoAtto, long idAttoOrigineMisure);
    	

       public Long registraAttoRicorsoInAppello(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long tipoAtto,long categoriaAtto, long idStatoAtto, long idDestinatario,String tipoUfficio);

   	   public Long registraAttoRicorsoInCassazione(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long tipoAtto,long categoriaAtto, long idStatoAtto, long idDestinatario, int tipoUfficio);      
       
      //Ricorso in appello
       
       public void impostaRicorsoMisPatr(long idProcedimento,long idAtto, long idUffOwner, boolean checked, long idSoggInt, long idBene, String idSoggTit, final int tipoBene, final int catBene, final int sottoCatBene, final String codiceUtente,final int catAtto,final String lTipoRicorso);
       
       public void impostaRicorsoSoggetto(long idProcedimento,long idAtto, long idUffOwner, boolean checked, long idSoggInt, final String idsSoggTit,final String idsMisure, final int tipoBene, final int catBene, final int sottoCatBene, final String codiceUtente,final int catAtto,final String lTipoRicorso);
      
       public void impostaRicorsoMisPers(long idProcedimento,long idAtto, long idUffOwner, boolean checked,long idMisuraPers,long idSoggInt, final String codiceUtente,final int catAtto,final String lTipoRicorso);
       
       public List<TAvvocati> getAvvocatiDifensori(long idProc, long idUfficio,long idAvvocato);
       
       public List<SoggettoForm> getSoggettiDifensori(long idUfficio, long idProcedimento, long idAvvocato) ;
       
       public List<SoggettoForm> getSoggettiDifensoriProcura(long idUfficio, long idProcedimento) ;
       
       public ImpugnazioneForm getImpugnazioneByIdAttoUfficioOwner(long idAtto,int idUfficio);
       //Fine Ricorso in appello
       
       /**
        * ricercaAttiRicorso
        * @param idProcedimento
        * @param idUffOwner
        * @return List<AttoForm>
        */
       public List<AttoForm> ricercaAttiRicorso(long idProcedimento, long idUffOwner);    
       
       public void inviaRicorsoInAppello(long iduffown, long idproc, long iddestinatario,Date datatrasmCda, String descutente);
       
       public boolean isRicorsoInLavorazione(long idProcedimento, long idUffOwner);
       

    public KeyMessageDto controllaMisureDefinitivitaIGrado(long idProc,long idAtto,long idUfficio,String descUtente,String funzione,String metodo);
 
    public KeyMessageDto controllaImpugnazioneCauArt67(long idProc,long idAtto,long idUfficio,String descUtente,String metodo);
    
	public KeyMessageDto controllaDefinitivitaIIGradoSuMisure(long idProc, long idAtto, long idUfficio, String descUtente, String funzione, String metodo);   
    
    public KeyMessageDto controllaMisureDefinitivitaIIGrado(long idProc,long idAtto,long idUfficio,String descUtente,String funzione,String metodo);
    
    public long inserisciRichiestaParere(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm, long idStatoAtto, String nomeMetodo);

    public long inserisciParere(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm, long idStatoAtto, long idAttoOrigine, String nomeMetodo);
    
    public Long aggiornaInviaParere(boolean isRichiesta, long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long tipoAtto,long categoriaAtto, long idStatoAtto, long idDestinatario, String nomeMetodo);

    public KeyMessageDto controllaRegImpugnazione( long idProc, long idAtto, long idUfficio, String descUtente, String metodo);   
    
    public KeyMessageDto controllaInsertDefinitivita(long idProc,long idMisPers,long idBene,long idInteresse,long idSoggetto ,Integer idUfficio,String descUtente,String funzione,String metodo);

    public KeyMessageDto controllaUpdEsitoMisPers(long idProc,long idAtto,long idSoggetto,long idEsito,String cauzione,String art67 ,Integer idUfficio, String descUtente, String funzione,String metodo);
	
   
    
    public long inserisciDecretoIncompetenza(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm, long idStatoAtto, String nomeMetodo);

	public long inserisciDecretoOrdinanzaRestituzioneBeni(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm, long idStatoAtto, String nomeMetodo);    

    public Long inviaAttoDecretoRestBeni(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long tipoAtto,long categoriaAtto, long idStatoAtto, long idDestinatario, String nomeMetodo);

    public void revocaDefinitivitaAttoRegistratoSalva(List<BeneForm> misurePatrimoniali, List<SoggettoForm> misurePersonali, long idProcedimento, Integer idUfficioUtente, long idAtto, String codUtente,String tipoOp,String tipoFunzione,String nomeMetodo);
    
    public void revocaDefinitivitaFascGestAttoRegistratoSalva(List<BeneForm> misurePatrimoniali, long idProcedimento, Integer idUfficioUtente, long idAtto, String codUtente, String tipo, String tipoFunzione, String nomeMetodo);
   
    public void revocaDefinitivitaInvio( long idProcedimento,  Integer idUfficioUtente, long idAtto, String codUtente,String tipoFunzione,String nomeMetodo) ;
    
    public KeyMessageDto controllaRegistrazioneDecisorio(long idProc,long idAtto,long idUfficio,String descUtente,String funzione,String metodo);

	public List<RedazioneSchedaDto> getListaMisurePerRedazioneScheda(long idProcedimento, int idUffOwn, String tipo);
	
	public List<SoggettoForm> listaTerziNoTitDir(long idUffOwn, long idProcedimento, long idAtto);
	
	/***
	 * Esegue l'inserimento dell'atto relativo alla redazione scheda
	 * @param idUffOwn
	 * @param idTipoProc
	 * @param dataRedazioneScheda
	 * @param descUtente
	 * @param descSedeOrgano
	 * @param descOrgano
	 * @param idAttoMisura
	 * @param idMisuraPersonale
	 * @param idProcedimento
		 * @return Ritorna l'id dell'atto appena inserito
	 */
	public Long inserisciRedazioneScheda (
			final long idUffOwn,
			final long idTipoProc,
			final Date dataRedazioneScheda,
			final String descUtente,
			final String descSedeOrgano,
			final String descOrgano,
			final long idAttoMisura,
			final Long idMisuraPersonale,
			final long idProcedimento
			
			); 
	
	
	/***
	 * Esegue l'inserimento dell'atto relativo alla redazione scheda
	 * @param idUffOwn
	 * @param idTipoProc
	 * @param dataRedazioneScheda
	 * @param descUtente
	 * @param descSedeOrgano
	 * @param descOrgano
	 * @param idAttoMisura
	 * @param idMisuraPersonale
	 * @param idProcedimento
	 * @param fcem, indica se stiamo trattando un proc di tipo FC errore materiale
	 * @param idSogInt
	 * @return Ritorna l'id dell'atto appena inserito
	 */
	public Long inserisciRedazioneSchedaFC(
			final long idUffOwn,
			final long idTipoProc,
			final Date dataRedazioneScheda,
			final String descUtente,
			final String descSedeOrgano,
			final String descOrgano,
			final long idAttoMisura,
			final Long idMisuraPersonale,
			final long idProcedimento,
			boolean fcem, 
			final Long idSogInt
			); 
	
	/***
	 * Imposta a cancellata la scheda calendario
	 * @param idUffOwn
	 * @param idComunicazione
	 * @param descUtente
	 * @return
	 */
	public String cancellaCasellario(
			final long idUffOwn,
			final long idComunicazione,
			final String descUtente
			);
	
	public ComunicazioneForm getComunicazioneByIdAtto(final long idProcedimento,final long idAtto,final long idUffOwn);

	public CertificatoPenaleForm getCertificatoPenaleByIdAtto(final long idProcedimento,final long idAtto,final long idUffOwn);


    /**
     * metodo service per il versionamento del soggetto
     * @param idUffOwn
     * @param idProcedimento
     * @param descUtente
     * @param attoForm
     * @param idMagistrato
     * @param soggettoForm
     * @return Ong i atto
     */
	public Long inserisciAttoVerSoggetto(long idUffOwn, long idProcedimento, String descUtente, AttoForm attoForm,
										  long idMagistrato, SoggettoForm soggettoForm);
	
	
	public TComunicazione getTComunicazioneById(long idComuniczione);
	
	/**
	 * getTRelProcSogintVerByAtto
	 * @param idAtto
	 * @param idUffOwn
	 * @return SoggettoForm
	 */
	public SoggettoForm getTRelProcSogintVerByAtto(long idAtto, int idUffOwn);
	
	public KeyMessageDto controllaEsitoFinale(int idUffOwn, long idProc, long idAtto, int catAtto);

	/***
	 * Restituisce la lista delle impugnazioni presenti nel grado precedente per un atto decisorio/ misura personale
	 * @param idUffOwn, ufficio corrente
	 * @param idProc
	 * @param idAttoDecisorioAttuale
	 * @param idMisuraPersAttuale
	 * @return
	 */
	public List<ImpugnazioneCassellarioDTO> getListaImpugnazioniGradoPrecedente(int idUffOwn, long idProc, long idAttoDecisorioAttuale, long idMisuraPersAttuale);

	public TCertificatoPenale getTCertificatoPenaleByIdAtto(Long idProcedimento, long codAtto, int idUffOwn);
	
	public List<AttoForm> getAttiProrogaSequestro(long idProcedimento, long idUffOwn);
	
	public long inserisciAttoDecretoProrogaSequestro(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm, long idStatoAtto, long idAttoOrigine, String nomeMetodo);

	public List<AttoForm> getAttiProrogaSequestroCdA(long idProcedimento, long idUffOwn);

	public Long inviaAttoDecretoProrogaSequestro(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long tipoAtto,long categoriaAtto, long idStatoAtto, String nomeMetodo);
	
	public void aggiornaDatiAtto(long codiceAtto, int idUffOwner, String note);
	
	public void aggiornaDatiAtto(AttoForm attoForm);
	
	public void attoDecretoApplicazioneProvvisoria(long vidProc, long vidUffOwn, long idSoggInt, String vcodUtente, SoggettoForm soggettoForm,AttoForm attoForm);
	
	public void attoDecretoRevocaApplicazioneProvvisoria(long vidProc, long vidUffOwn, long idSoggInt, String vcodUtente, SoggettoForm soggettoForm,AttoForm attoForm);

	public KeyMessageDto controllaAmministratoriSenzaBeni(int idUffOwn, long idProc, long idAtto);
	
	public boolean controllaAmministratoriSenzaBeniDecisorio(int idUffOwn, long idProc, long idAtto);
	
	// P.Q. MEV 2020/48 Inizio
	public boolean controllaAmministratoriSenzaBeniDecisorioSecondGrado(int idUffOwn, long idProc, long idAtto, long idFascicoloGestione);
	// P.Q. MEV 2020/48 Fine	
	
	public boolean controllaBeniAmministratoriSenzaAmministratore(int idUffOwn, long idProc, long idAtto);

	public ImpugnazioneCassellarioDTO getSentenzaCassazione(int idUffOwn, long idProc, long idAttoDecisorioAttuale, long idMisuraPersAttuale);
	
	public KeyMessageDto controllaInserimentoDecretoSospensioneTemporanea(long idProcedimento, int idUffOwn);
	
	public Long inserisciAttoDecretoSospensioneTemporanea(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm, long idStatoAtto, String nomeMetodo);
	
	public Long inviaAttoDecretoSospensioneTemporanea(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long tipoAtto,long categoriaAtto, long idStatoAtto, String nomeMetodo);
	
	public long inserisciAttoDecretoProrogaSospensioneTemporanea(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm, long idStatoAtto, long idAttoOrigine, String nomeMetodo);
	
	public Long inviaAttoDecretoProrogaSospensioneTemporanea(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long tipoAtto,long categoriaAtto, long idStatoAtto, String nomeMetodo);
	
	public List<AttoForm> getAttiSospensioneTemporanea(long idProcedimento, long idUffOwn);	
	
	public long inserisciAttoDecretoSequestroSospensioneTemporanea(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm, long idStatoAtto, long idAttoOrigine, String nomeMetodo);
	
	public Long inviaAttoDecretoSequestroSospensioneTemporanea(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long tipoAtto,long categoriaAtto, long idStatoAtto, String nomeMetodo);
	
	public List<AttoForm> getAttiDecretoApplicazioneProvvisoria(long idProcedimento, long idUffOwn);
	
	public long inserisciAttoDecretoRevocaApplicazioneProvvisoria(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm, long idStatoAtto, long idAttoOrigine, String nomeMetodo);
	
	public long salvaIstanzaFascicoloGestione(int idUffOwn, long idProcedimento, String codiceUtente, long statoAtto, long tipoAtto, long categoriaAtto, AttoForm attoForm, String nomeMetodo);
	
	public long salvaIstanzaRinunciaAmministratore(int idUffOwn, long idProcedimento, Long idAmministratore, String codiceUtente, long statoAtto, long tipoAtto, long categoriaAtto, AttoForm attoForm, String nomeMetodo);
	
	public List<SoggettoStralcioForm> getRicercaMisureIstanza(long idProcedimento, long idUfficio, AttoForm attoForm, String flgValido,int categoriaAtto); 
	
	 public List<SoggettoForm> getRicercaSoggettoIstanza(long idProcedimento,long idatto,int iduff,int descRuolo);	
	        
	public List<DifensoreForm> getRicercaAvvIstanza(long idProcedimento,long idatto, int iduff); 

	public void impostaIstanzaMisPatr(long idProcedimento,long idAtto, long idUffOwner, boolean checked, long idSoggInt, long idBene,String idSoggTit,  final String codiceUtente,final int catAtto,final String lTipoRicorso);
     
    public void impostaIstanzaSoggetto(long idProcedimento,long idAtto, long idUffOwner, boolean checked, long idSoggInt, final String codiceUtente,final int catAtto,final String lTipoRicorso);
    
    public void impostaIstanzaMisPers(long idProcedimento,long idAtto, long idUffOwner, boolean checked,long idMisuraPers,long idSoggInt, final String codiceUtente,final int catAtto,final String lTipoRicorso);
 	
    public ImpugnazioneForm getTRelSoggImpByIdImpugnazioneIdOwner(long idImpugnazione,int IdUffOwner,ImpugnazioneForm impForm); 
 	 
 	public List<SoggettoForm> getSoggettiDifensoriIstanza( long idImpugnazione, long idUfficio,  long idAvvocato,  String cognomeAvv, String nomeAvv, String descUtente);
 	
 	public long inserisciProvvedimentoFascicoloGestione(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm, long idStatoAtto, long idAttoOrigine, String nomeMetodo);
 	
	public long salvaProvvedimentoFascicoloGestione(int idUffOwn, long idProcedimento, long idAtto, String codiceUtente, AttoForm attoForm, long idStatoAtto, String nomeMetodo); 	

	public NotificaAttoForm getAttoNoficatoDaRicevuta(int idUffOwn, long idAtto, long idProcedimento, String codiceUtente,Integer idCatAtto);

 	public List<SoggettoForm> getSoggettiDifensoriRicorso(long idProc, long idImpugnazione, long idUfficio,  long idAvvocato,  String cognomeAvv, String nomeAvv, String descUtente);

	public long inserisciOrdinanzaAntimafia(int idUfficio, String codUtente, Long idProcedimento, AttoForm attoForm, long idStatoAtto, String nomeMetodo,long idSoggetto);
	
	public SoggettoForm getSogAntimafia(int idUffOwn, Long idProc, Long idAtto);

	public long inserisciProvvedimentoRevocaAmministratori(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm, long idStatoAtto, String nomeMetodo, Long idAttoOrigine);
	
	public long salvaProvvedimentoRevocaAmministratori(int idUffOwn, long idProcedimento, long idAtto, String codiceUtente, AttoForm attoForm, long idStatoAtto, List<Long> amministratoriRevocati, String nomeMetodo); 	

	public long inserisciProvvedimentoNominaAmministratori(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm, long idStatoAtto, String nomeMetodo);
	
	public long salvaProvvedimentoNominaAmministratori(int idUffOwn, long idProcedimento, long idAtto, String codiceUtente, AttoForm attoForm, long idStatoAtto, String nomeMetodo); 	
	
	public List<AmministratoreForm> getAmministratoriRevocabili(int idUffOwn, Long idProcedimento);
	
	public boolean getSogNoDecErroreMat(long idProcedimento, long idAtto,long idUffOwn, String descAtto);
	
	
	public List<AmministratoreForm> getAmministratoriRevocati(int idUffOwn, Long idProcedimento, Long idAtto);
	
	public Date getMaxDataEmissioneProposta(int idUffOwn, long idProcedimento);
	
	/**
	 * aggiornaDecisorioIIIGrado
	 * @param idUffOwn
	 * @param idProc
	 * @param attoForm
	 * @param codUtente
	 * @param tipoUff
	 * @return
	 */
	public Long aggiornaDecisorioIIIGrado(long idUffOwn, long idProc,AttoForm attoForm, String codUtente, int tipoUff);

	/**
	 * Escude le misure per le quali non deve essere inviato il foglio complementare.
	 * L'esclusione avviene utilizzando un funzione che in base all'idAtto e della misura
	 * e ad un serie di altre considerazioni isolate su un funzione del db determina il codice
	 * della misura da invare a NSC .
	 * @param listaMisure
	 * @param idUffOwn 
	 * @param idProcedimento 
	 */
	public void escludiMisureNonInviabile(List<RedazioneSchedaDto> listaMisure, long idProcedimento, int idUffOwn);
	
	public List<AttoForm> getricercaIstanza(int idUffOwn,  Long idProcedimento,  Long idTipoAtto,  Long idCatAtto);	
	
	public TDefIstGen caricaDefinitivitaIstGen(TDefIstGenId id);
	
	
	public void salvaDecErroreMateriale(long idAtto,long idProcedimento,String idCodiceUtente,int idUfficio,String descDecisorio,SoggettoForm soggettoForm);

	public boolean existsIntervenientiNonAssociati(Long idProcedimento, int idUfficio);
	
	public boolean isAttoOrigine(long idAtto, int idUffOwn);	
	
	public long ordinanzaVenditaBene(long idUffOwn, String codUtente, long idprocedimento, BeneForm beneForm, AttoForm attoForm);

	public long inserisciAttoRelazioneAmm(int idUffOwn, String idCodiceUtente, long idProcFG, long idTipoAtto, long idCatAtto, 
			long idStatoAtto, Date dataRicezione, long idAmm, String cognomeAmm, String nomeAmm, Date dataInizio, Date dataFine, long idPDOC, 
			Blob blob, String tipoAllegato, String nomeAllegato, String numeroRiferimento);

	public TAttoGenerico getTAttoGenerico(long idAtto, long idUfficio);
	
	public long contaInvioMsgCassazione( long idProc,  long idAtto,  long idUffOwn);
	
	public int getUffOwnerFromIdDocumento(BigInteger idDocumento);
	
	public int getUffOwnerTestDocumentale();
	
	public boolean getRelAttoProcByIdProcAndCat( long idProcedimento,  int idCatAtto);
	
	public KeyMessageDto controllaPresenzaRicorso(long idProc, long idUfficio, String funzione, String descUtente);
	
	public List<TRelAttoMisPers> getTRelAttoMisPers(long idProcedimento,long idAttoImpugnazione,int idUffOwn);
	
	public TDecreto getTDecreto(long idAttoDecreto, int idUffOwnerDecreto);
	
	public KeyMessageDto impostaGiudizioDefinitivoI(long idProc, long idUfficio, String funzione, String descUtente);
	
	public void cancelIdTransazioneDocumentale(final long idAtto,  final int uffOwner);

}