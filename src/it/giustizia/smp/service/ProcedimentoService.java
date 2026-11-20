package it.giustizia.smp.service;

import it.giustizia.smp.dto.pg_procedimento.AssociaPersonaDto;
import it.giustizia.smp.dto.pg_procedimento.MisurePatrSoggDto;
import it.giustizia.smp.form.AmministratoreForm;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.BeneForm;
import it.giustizia.smp.form.BeneSoggettoForm;
import it.giustizia.smp.form.CoadiutoreForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.DifensoreSoggettoForm;
import it.giustizia.smp.form.EredeConviventeForm;
import it.giustizia.smp.form.MagistratoForm;
import it.giustizia.smp.form.NumeroRegistroForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.model.TAnManuali;
import it.giustizia.smp.model.TDefPatr;
import it.giustizia.smp.model.TDefPers;
import it.giustizia.smp.model.TProcedimento;
import it.giustizia.smp.model.TRelAttoProc;
import it.giustizia.smp.model.TRelBeneAtto;
import it.giustizia.smp.model.TRelIterProc;
import it.giustizia.smp.model.TRelMagProc;
import it.giustizia.smp.model.TRelProcProc;
import it.giustizia.smp.model.TRelSoginvBene;
import it.giustizia.smp.model.TRelSoginvBeneId;
import it.giustizia.smp.model.TRelSoginvSogint;
import it.giustizia.smp.model.TRelSoginvSogintId;
import it.giustizia.smp.model.TTpEsito;
import it.giustizia.smp.utility.tree.TreeNode;
import it.giustizia.smp.webservice.client.allineamentoFascicoliPDoc.model.general.recuperoFascicoloResponse.Risposta;

import java.util.List;
import java.util.Map;

import org.springframework.validation.Errors;

/**
 * ProcedimentoService
 *
 * @author Almaviva S.p.A.
 *         Interfaccia Procedimento Service
 */
public interface ProcedimentoService {

	public long aggiornaPersonaFisica(SoggettoForm soggettoForm, long idProcedimento, String codUtente);

    public Long aggiornaProcedimento(long idUffOwn, String codUtente, ProcedimentoForm procForm);

    public Long aggiornaPropostaAtto(long idUfficio, long idDestinatario, long idUltDestinatario, long idProcedimento, AttoForm attoForm, String codUtente);

    public void aggiornaStatoDelProcedimento(long idProcedimento, Integer idUfficioUtente, String codUtente, String tipoUtente, long idAtto);
    
    public void annullaStatoDelProcedimento(long idProcedimento, Integer idUfficioUtente, String codUtente, String tipoUtente, long idAtto);
    

    public Long aggiornaTipoProcPrevProc(long idProc, long idUffOwn, String codUtente);

    public void associaMagistratoAlProcedimento(MagistratoForm magistrato, long idProcedimento, Integer idUfficioUtente);

    public void associaSoggettiTitolariPerBeneMisuraPatrimoniale(List<BeneForm> beniSelezionati, long idProcedimento, int idUfficioUtente, long idAtto, long idSoggettoInteressato, String codUtente);

    public AssociaPersonaDto associaSoggettoProcedimento(int idUffOwn, String codUtente, Long idProc, SoggettoForm soggettoForm);

    public AssociaPersonaDto nuovaPersonaFisica(int idUffOwn, String codUtente, SoggettoForm soggettoForm);

    public AssociaPersonaDto nuovaPersonaGiuridica(int idUffOwn, String codUtente, SoggettoForm soggettoForm);

    public void cancellaDifensoreAssociato(DifensoreSoggettoForm dsf);
    
    public void cancellaDifensoreAssociatoIntestatario(DifensoreSoggettoForm dsf);

    public void cancellaEredeDelDefunto(SoggettoForm soggettoForm);

    public DifensoreSoggettoForm caricaDifensoreAssociatoSoggetto(DifensoreSoggettoForm dsf);
    
    public DifensoreSoggettoForm caricaDifensoreAssociatoIntestatario(DifensoreSoggettoForm dsf);

    public EredeConviventeForm caricaEredeDelDefunto(SoggettoForm soggettoForm);
    
    public TProcedimento getIdProcedimentoDestinatario(long idProcedimento,int idUffOwn,int idTipoFasc,int idUfficioArrivo);
    
    public void cancellaDirettoInteressato(long idSoggetto, long idProcedimento, int idUfficioUtente, long idVersione, String tipoSoggetto, Errors errors, boolean ruoloTerzo);

    /**
     * caricaValoriDettaglioSoggettoFisico
     * 
     * @param soggetto
     * @return SoggettoForm
     */
    public SoggettoForm caricaValoriDettaglioSoggettoFisico(SoggettoForm soggetto);
    
	public SoggettoForm caricaValoriDettaglioSoggettoFisicoVersionamento(SoggettoForm soggetto);    

    public SoggettoForm caricaValoriDettaglioSoggettoFisicoProcedimento(SoggettoForm soggetto);

    public SoggettoForm caricaValoriDettaglioSoggettoGiuridico(SoggettoForm soggetto);

    public SoggettoForm caricaValoriDettaglioSoggettoGiuridicoProcedimento(SoggettoForm soggetto);
    
	public SoggettoForm caricaValoriDettaglioSoggettoGiuridicoVersionamento(SoggettoForm soggetto);



    //se controllaMisure è true, la proposta verrà restituita solo se presenti misure
    public AttoForm dettaglioPropostaAtto(long idUfficio, long idProcedimento, long idAtto, boolean controllaMisure);

    public void eliminaAssociazioneMagistratoProcedimento(MagistratoForm magistrato, long idProcedimento, Integer idUfficioUtente);

    public void eliminaRelazioniAttoMisuraPatrimoniale(List<Long> listaIdCheckati, long idProcedimento, int idUfficioUtente, long idAtto, long idSoggettoInteressato, String codUtente);

    /**
     * costruisciAlberaturaAtti
     */
    public List<TreeNode> getAlberaturaAtti(long idProcedimento, long tipoUfficio);

    public List<TreeNode> getAlberaturaAtti(long idProcedimento, long tipoUfficio, Long idTipoFascicolo, Long idTipoProcedimento);

    public List<ComboForm> getAllAttoByType(String type);

    public List<ComboForm> getAllFonte();

    public MagistratoForm getDettaglioMagistratoProcedimento(Long idMagistrato, long idProcedimento, int idUfficioUtente);

    public ProcedimentoForm getDettaglioProcedimento(long idProcedimento);

    public List<ComboForm> getEsiti(String carattereMisura, long idCategoriaAtto, long idTipoUfficio);

    public Long getIdPadreAggravamento(long idProcedimento);

    public List<Long> getIdProcByAnnoNumProc(int idUffOwn, short annoProc, long numeProc, int idTipoFasc);

    public List<ComboForm> getIndirizzoByOrganoAndComune(String idCodiceComune, long idOrgano);

    public List<ComboForm> getListaProcedenti(long idUfficio, Long idTipoPrev, Long idTipoFasc);

    public MagistratoForm getMagistratoById(Long id);

    public List<NumeroRegistroForm> getNumeriRegistro(long idProcedimento);
    
    public NumeroRegistroForm getNumeroRegistroPadre(long idProcedimento);
    
    public List<NumeroRegistroForm> getNumeriRegistroAccertamento(long idProcedimento);

    public List<ComboForm> getOrgani(String tipo);

    public SoggettoForm getPersonaById(long identificativoSoggettoProcedimento, Integer idUfficioUtente);

    public TRelBeneAtto getRelazioneBeneAttoByAttoAndBene(long idBene, long idProcedimento, Integer idUfficioUtente, long idAtto, long idSoggetto, long tipoInt);

    // tutti i soggetti diretti interessati associati a una fonte
    public List<SoggettoForm> getSoggettiInteressatiAssociati(long idUfficio, long idProcedimento, long idFonte);

    // tutti i soggetti diretti interessati non associati ad alcuna fonte
    public List<SoggettoForm> getSoggettiInteressatiNonAssociati(long idUfficio, long idProcedimento);

    public SoggettoForm getSogIntById(long idSogInt, long idUfficio, long idProcedimento);

    public SoggettoForm getSogIntById(long idSogInt, long idUfficio, long idProcedimento, String ruolo);

    public List<SoggettoForm> getSogIntSenzaDifensore(long idProcedimento, long idUfficio);

    public void inserisciEredeDelDefunto(SoggettoForm soggettoForm, String codiceUtente);

    public long inserisciFonte(long idUfficio, String codiceUtente, long idProcedimento, long idStatoProcedimento, AttoForm form);
    
    public long inserisciArchivizione(long idUfficio, String codiceUtente, long idProcedimento, long idStatoProcedimento, AttoForm form);

    public Long inserisciModificaDifensoreAssociatoSoggetto(DifensoreSoggettoForm dsf, String codiceUtente);
    
    public Long inserisciModificaDifensoreAssociatoIntestatario(DifensoreSoggettoForm dsf, String codiceUtente);

    public List<AssociaPersonaDto> inserisciProcedimento(int idUffOwn, String codUtente, ProcedimentoForm procForm, long statoProc, List<SoggettoForm> listaSoggetti, int idTipoFascicolo);

    public Long iscriviProcedimentoDiAggravamento(Integer idUffOwn, String codUtente, ProcedimentoForm procForm, Long idSoggettoInteressato, Long idPadre, String tipoUtente, int idTipoFascicolo);

    public List<BeneSoggettoForm> listaBeniAttoDecisorio(long idProcedimento, long idAtto, int idUfficioUtente, long idSoggettoDiretto, long idSoggettoTitolare, long idTipologia, long idCategoria, long idSottocategoria);

    public List<BeneSoggettoForm> listaBeniAttoNoMisPatr(int idUffOwn, long idProcedimento, long idAtto, long idSoggetto, long idSoggettoTit, long idTipologia, long idCategoria, long idSottocategoria );

    public List<BeneSoggettoForm> listaBeniConSoggettiAssociatiAdAttoEProcedimento(long idProcedimento, long idAtto, Integer idUfficioUtente);

	public List<BeneSoggettoForm> listaBeniConSoggettiAssociatiAdAttoEProcedimento(long idProcedimento, long idAtto, Integer idUfficioUtente, boolean isCda);

    public List<BeneSoggettoForm> listaBeniConSoggettiAssociatiAllaMisuraPatrimoniale(long idSoggetto, long idProcedimento, long idAtto, Integer idUfficioUtente);

    public List<BeneSoggettoForm> listaBeniConSoggettiAssociatiAllaMisuraPatrimonialeAtto(long idSoggetto, long idProcedimento, long idAtto, Integer idUfficioUtente);

    public List<DifensoreSoggettoForm> listaDifensoriAssociatiSoggetto(long idProcedimento, int idUfficioUtente, long idSoggettoInteressato);
    
    public List<DifensoreSoggettoForm> listaDifensoriAssociatiIntestatari(long idProcedimento, int idUfficioUtente, long idSoggettoInteressato);

    public List<DifensoreSoggettoForm> listaDifensoriReginde(String cognome);
    
    public List<DifensoreSoggettoForm> listaDifensoriNoReginde(String cognome, long idSogg, long idProcedimento, int idUffOwn);

    public List<EredeConviventeForm> listaErediDelDefunto(SoggettoForm soggettoForm);

    public List<MagistratoForm> listaMagistratiNonGiaAssociati(List<MagistratoForm> magistratiDelProcedimento, List<MagistratoForm> magistratiAssociati);

    /**
     * listaMagistratiPerProcedimento
     * 
     * @param idProcedimento
     * @return List<DMagistrati>
     */
    public List<MagistratoForm> listaMagistratiPerProcedimento(int idUffOwn, long idProcedimento, int ruoloMag);

    public List<MagistratoForm> listaMagistratiPerProcedimento(int idUffOwn, long idProcedimento ,int ruoloMag, boolean utenteDelTribunale);

    public List<ProcedimentoForm> listaProcedimentiByCriteria(Integer idUfficioUtente, ProcedimentoForm procedimento, Integer idTpUffRic);

    /**
     * listaSoggettiByCriteria(
     * 
     * @param nome
     * @param cognome
     * @return List<SoggettoForm
     */
    public List<SoggettoForm> listaSoggettiByCriteria(int idUffOwn, String nome, String cognome, String codiceStatoNascita, String codiceComuneNascita, String comuneNascitaEstero, String dataNascita, String codiceFiscale, String codiceCui, boolean escludiIntervenienti, Integer idRuolo);

    /**
     * listaSoggettiDellaFonte
     * 
     * @param idProcedimento
     * @param fonte
     * @return List<SoggettoForm>
     */
    public List<SoggettoForm> listaSoggettiDellaFonte(long idProcedimento, String fonte);

    /**
     * listaSoggettiDelProcedimentoNonAssociati
     * 
     * @param idProcedimento
     * @return List<SoggettoForm>
     */
    public List<SoggettoForm> listaSoggettiDelProcedimentoNonAssociati(long idProcedimento);

    public List<SoggettoForm> listaSoggettiGiurByCriteria(int idUffOwn, String ragioneSociale, String partitaIva, Integer vidStatoSede, Integer vidComuneSede, String vcomuneSedeEstero, boolean escludiIntervenienti, Integer idRuolo);

    public boolean localitaObbligatoriaNelTipoMisuraSelezionato(Long idTipoMisura);

    public void modificaDatiMagistratoSuProcedimento(MagistratoForm magistrato, long idProcedimento, Integer idUfficioUtente);

    public void modificaEredeDelDefunto(SoggettoForm soggettoForm, String codiceUtente);

    public void modificaFonte(long idUfficio, long idProcedimento, AttoForm atto, String codiceUtente);

    public void modificaGratuitoPatrocinioSoggProc(SoggettoForm form, long l, Integer integer);
    
    public void cancellaGratuitoPatrocinioSoggProc(SoggettoForm form, long l, Integer integer);

    public List<MagistratoForm> ricercaMagistrati(MagistratoForm magistrato, int idUfficioUtente);

//    public void rimuoviDocumentoAllegatoDaAtto(long idUfficio, long codAtto, BigInteger numeDocumentoPdoc, long idProcedimento);

    public void setIdSoggettoIdVersioneTipoSoggFromChiave(SoggettoForm soggetto);

    public List<SoggettoForm> soggettiDirettiInteressatiDelProcedimento(long idProcedimento);
    
    public List<SoggettoForm> soggettiDirettiInteressatiApplicazioneProvvisoria(long idProcedimento, int idUffOwn, String codiceUtente);

    public List<SoggettoForm> soggettiDirettiInteressatiDelProcedimento(long idProcedimento, String descRuolo, Character flagValido);
    
    public List<SoggettoForm> soggettiDirettiInteressatiInvDelProcedimento(long idProcedimento, long idSogInv);
    
    public List<SoggettoForm> soggettiFisiciDirettiInteressatiDelProcedimento(long idProcedimento, String descRuolo, Character flagValido);
    
    public List<SoggettoForm> soggettiDirettiInteressatiDelProcedimentoVer(long idProcedimento, String descRuolo, Character flagValido, long idSogg, long idUffOwn, Integer idVer);

    public List<SoggettoForm> soggettiInteressatiDelProcedimentoPerRegistrazioneAggravamento(Long idProcedimento, int idUfficioUtente);
    
    public List<TRelProcProc> getProc1ByIdProc2(final long idProc2, final long idUffMitt, final long idUffOwn);

    public List<TRelProcProc> getProc1ByIdProc2(final long idProc2, final long idUffMitt);
    
    public List<TRelProcProc> getProc1ByIdProc2AndTipoFasc(final long idProc2, final long idUffMitt, final long idTipoFasc); 
    
    public TRelAttoProc getProcByIdAttoAndTipoFasc(final long idAtto, final long idUffMitt, final long idTipoFasc);
    
    public List<TRelMagProc> verificaOrdineMagistrati(final long idProcedimento, final int idUfficioUtente, final byte numOrdine, final long idMagistrato);

	public long aggiornaPersonaGiuridica(SoggettoForm soggettoForm,Long idProc, String codUtente);
	
	//P.Q. 04/09/2025 Ticket 20250807017 Inizio
	public Long getIdProcDaRelProcProc(Short annoPadre, Integer numeroPadre, Integer idUfficioUtente);
	//P.Q. 04/09/2025 Ticket 20250807017 Fine
	
	public Long getIdByAttoEUfficio(Long idAtto, Integer idUfficioUtente);

    public List<BeneSoggettoForm> listaSoggettiMisuraPersonaleAssociatiAdAttoEProcedimento(long idProcedimento, long idAtto, int idUffOwn);
    public List<BeneSoggettoForm> listaSoggettiMisuraPersonaleAssociatiAdAttoEProcedimentoFiltrati(long idProcedimento, long idAtto, int idUffOwn, long idSoggettoDiretto);
    
    /**
     * definitivitaMisurePersonali
     * 
     * @param idProcedimento
     * @param idAtto
     * @param idUfficioUtente
     * @param tipoRicerca
     * @return List<TDefPers>
     */
    public List<TDefPers> definitivitaMisurePersonali(long idProcedimento, long idAtto, Integer idUfficioUtente, String tipoRicerca);

    /**
     * definitivitaMisurePatrimoniali
     * 
     * @param idProcedimento
     * @param idAtto
     * @param idUfficioUtente
     * @param tipoRicerca
     * @return List<TDefPatr>
     */
    public List<TDefPatr> definitivitaMisurePatrimoniali(long idProcedimento, long idAtto, Integer idUfficioUtente, String tipoRicerca);
    
    /**
     * @param idUffOwn
     * @param listaIdSoggetti
     * @return
     */
    public List<ProcedimentoForm> listaAccertamentiConSoggettiProposta(int idUffOwn, String idProced, String listaIdSoggetti);
    
    /**
     * @param idProc1
     * @param idProc2
     * @param idUffOwn
     * @param codUtente
     * @param idTipoAtto
     * @param idCatAtto
     * @param codiTipoRel
     */
    public void inserisciTRelProcProc(long idProc1, long idProc2, int idUffOwn, String codUtente, long idTipoAtto, long idCatAtto, String codiTipoRel);
 
    /**
     * @param soggettoForm
     * @param idUffOwn
     * @return
     */
    public SoggettoForm getPersonaFisicaByCampiChiave(SoggettoForm soggettoForm, int idUffOwn);
    
    /**
     * @param soggettoForm
     * @param idUffOwn
     * @return
     */
    public SoggettoForm getPersonaGiuridicaByCampiChiave (SoggettoForm soggettoForm, int idUffOwn);

    public boolean getAttoDecretoFissUdienzaProcedimento(long idProcedimento, long idUfficio);

    public void aggiornaTipoPrevenzioneNormativaSoggetto(long idProcedimento, Integer idUfficioUtente, long idSoggettoInteressato, long idTipoPrev, long idTipoNorm);

    public void aggiornaFlgTipoPrevenzioneProcedimento(long vidProc, Integer idUfficio);

    public boolean verificaCancellaDifensoreAssociato(DifensoreSoggettoForm difensoreSoggettoForm);

    public boolean verificaDataRevocaDifensoreAssociato(DifensoreSoggettoForm difensoreSoggettoForm);
    
    public boolean inserisciECreaFascicoloPDoc(long idProcedimento, String codiceFascicolo);

	public TProcedimento getProcedimentoById(long idProcedimento);
	
	public List<SoggettoForm> soggettiTitolariDelProcedimento(long idProcedimento);
	
	public List<BeneSoggettoForm> listaBeniConSoggettiAssociatiAdAttoEProcedimentoPerRevoca(long idProcedimento, long idAtto, Integer idUfficioUtente, String descUtente, String nomeMetodo);
	
	public List<TRelProcProc> getTRelProcProcByProc1(final long id, Character flgTipoRel, final String descUtente, final String nomeMetodo);
	
	public String getListaRelProcProcByProc1(final long id, Character flgTipoRel, final String descUtente, final String nomeMetodo);
	 
	public TProcedimento getTRelProcProcByProc2(final long id, Character flgTipoRel);
	
	public List<SoggettoForm> soggettiDirettiInteressatiDelProcedimentoRiunente(long idProcedimentoRiunito, String descRuolo, Character flagValido, long idProcedimentoRiunente);
	
	public SoggettoForm caricaValoriDettaglioSoggettoGiuridico(long idSoggetto, long idVersione, int idUffOwner);
	
	public SoggettoForm caricaValoriDettaglioSoggettoFisico(long idSoggetto, long idVersione, int idUffOwner);
	
    public int getNumSogInt(Long identificativoProcedimento, int idUfficioUtente);
    
    public void aggiornaStatoDelProcedimentoRiconcilia(long idProcedimento, Integer idUfficioUtente, String codUtente);

    public List<TRelIterProc> verificaTRelIterProc(final long idProcedimento, final int idUfficioUtente); 
    
	public List getListaProcedimentiRelProcProc(long idProcedimento, Integer idUffOwn, Integer idUffMitt,String funzione);

	public List getListaProcedimentiCdaTrib( long idProcedimento,Integer idUffOwn);
	
	public List getListaProcedimentiTribCda( long idProcedimento,Integer idUffOwn);
	
	
	public List<BeneSoggettoForm> listaBeniConSoggettiAssociatiAdAttoEProcedimentoPerDecRestBeni(long idProcedimento,long idAtto,  Integer idUfficioUtente, String descUtente, String nomeMetodo);
	
	public List<BeneSoggettoForm> listaSoggettiMisuraPersonaleRevocaDefinitiva(long idProcedimento, long idAtto, int idUffOwn,String descUtente,String tipoFunzione,String nomeMetodo);
	public List<BeneSoggettoForm> listaSoggettiMisuraPersonaleRevocaDefinitivaFiltrati(long idProcedimento, long idAtto, int idUffOwn,String descUtente,String tipoFunzione,String nomeMetodo,long idSoggettoDiretto);
	
	public List<BeneSoggettoForm> listaSoggettiMisPatrRevocaDefinitivita(long idProcedimento,long idAtto,int idUffOwn,String codUtente,String nomeMetodo,String tipoFunzione);
	public List<BeneSoggettoForm> listaSoggettiMisPatrRevocaDefinitivitaFiltrati(long idProcedimento,long idAtto,int idUffOwn,String codUtente,String nomeMetodo,String tipoFunzione, long idSoggettoDiretto, long idSoggettoTitolare, long idTipologia, long idCategoria, long idSottocategoria);

	public List<BeneSoggettoForm> listaSoggettiMisPatrDefinitivita(long idProcedimento,long idAtto,int idUffOwn,String codUtente,String nomeMetodo,String tipoFunzione);
	public List<BeneSoggettoForm> listaSoggettiMisPatrDefinitivitaFiltrati(long idProcedimento,long idAtto,int idUffOwn,String codUtente,String nomeMetodo,String tipoFunzione, long idSoggettoDiretto, long idSoggettoTitolare, long idTipologia, long idCategoria, long idSottocategoria);

    public SoggettoForm caricaValoriGratuitoPatrocinioDelSoggetto(SoggettoForm soggettoForm);
	
    public String getNormativaPrevProc(long idProcedimento,long idSogInt,int idUffOwn);

    public void copiaAttoAccertamentoInProcMerito(long idProcAccertamento, long idProcMerito, int idUffOwn, String codUtente);
    
    public void sostituisciGiudiceDelegato(int idUffOwn, long idMagistratoOut, long idMagistratoIn, long idProcedimento, String tipoSostituzione, final String codUtente);
    
    public List<SoggettoForm> getListaSoggettiFisiciProcedimento(long idProcedimento,int idUffOwner );

    /***
     * Restituisce true se per il procedimento passatto esistono degli atti in lavorazioni diversi dalla proposta
     * @param idProcedimento
     * @param idUffOwner
     * @return
     */
    public boolean hasAttiInLavorazioni(long idProcedimento, long idUffOwner);
    
    public TAnManuali getTAnManualiById(long id);
    
    public List<Long> listaSoggettiMisurePersAtto(long idProcedimento, long idAtto, int idUffOwn);
    
    public DifensoreSoggettoForm dettaglioDifensore(long idDifensore);
    
    public List<ProcedimentoForm> getListaProcedimentiSogg(int idUffOwn, Long tipoFascicolo, Long idSogg);
    
    public List<ProcedimentoForm> getListaProcedimentiSoggMultiFascicolo(int idUffOwn, String tipiFascicolo, Long idSogg);
    	
    public List<MisurePatrSoggDto> misurePatrimonialiSoggetto(int idUffOwn, Long idProc, Long idSoggInt, Long idSoggTit);
    
	public ProcedimentoForm getDettaglioProcedimentoNum(int idUffOwn, long idProc);

	public List<TProcedimento> getProcAggravamento(int idUffOwn, long idProc, long idSogg); 
	
	public List<TProcedimento> getProcCamere(int idUffOwn, long idProc, long idSogg); 
	
	public String getNumeroProcByIdProcedimento(long idProcedimento);
	
	 public Long aggiornaIstanzaMP(long idUfficio, long idProcedimento, AttoForm attoForm, Long codiKIdAmministratore, String codUtente);
	 
	 public List<SoggettoForm> getListSogAntimafia(int idUffOwn, Long idProc);
	 
	 public List<ProcedimentoForm> listaProcedimentiSoggetto(int idUffOwn, long idSogg);	
	 
	 public List<CoadiutoreForm> listaCoadiutori(long idProc, int idUfficio, long idAmm);	
	 
	 public CoadiutoreForm dettaglioCoadiutore(long idProc, int idUfficio, long idAmm, int idProgrCoad);	 
	 
	 public short salvaCoadiutore(CoadiutoreForm coadiutoreForm);
	 
	 public void eliminaCoadiutore(CoadiutoreForm coadiutoreForm);
	 
	 public AmministratoreForm getAmministratoreByIstanza(long idProcedimento, long idIstanza, int idUffOwner);

	 public List<SoggettoForm> soggettiTitolariDelProcedimentoCombo(long idProcedimento);

	 public AmministratoreForm getAmministratoreByImpugnazione(long idProcedimento, long idImpugnazione, int idUffOwner);	 
	
	 public List<SoggettoForm> soggettiTitolariDelProcedimentoAttoCombo(long idProcedimento, long idAtto);
	 
	 public void associaRelSoggettoInterveniente(TRelSoginvSogint rec);
	 
	 public void eliminaRelSoggettoInterveniente(TRelSoginvSogintId id);
	 
	 public void associaRelBeneSoggettoInterveniente(TRelSoginvBene rec);
	
	 public void eliminaRelBeneSoggettoInterveniente(TRelSoginvBeneId id); 

	 public Map<String, Object> getDettaglioProcedimentoById(long idProcedimento);
	 
	 public String eliminaSogInv(long idProc, int idUffOwn, long idSogInv);	 
	 
	 public SoggettoForm getPersonaById(long identificativoSoggettoProcedimento, Integer idUfficioUtente, Long idProcedimento);
	 
	 public TTpEsito getTTpEsito(long idEsito);
	 
	 public String intervenientiNonAssociati(Long idProcedimento, int idUfficio);

	// 20200709 [SG]: nuovo job per allineamento fascicoli PDoc andati in errore col documentaleJob
	// GC 14/07/2020: corretto il metodo passando direttamente l'owner per evitare l'ulteriore chiamata al db
	public Risposta recuperaFascicoloPDoc(int uffOwner, String codiceFascicolo);

}