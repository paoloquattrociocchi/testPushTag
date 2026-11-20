package it.giustizia.smp.dao;

import it.giustizia.smp.dto.pg_procedimento.AssociaPersonaDto;
import it.giustizia.smp.dto.pg_procedimento.ContaDifensoriSogIntDto;
import it.giustizia.smp.dto.pg_procedimento.ListaProcedimentiSoggDto;
import it.giustizia.smp.dto.pg_procedimento.MisurePatrSoggDto;
import it.giustizia.smp.dto.pg_procedimento.RicercaProcedimentoDto;
import it.giustizia.smp.dto.pg_procedimento.SogIntDettaglioProcedimentoDto;
import it.giustizia.smp.dto.pg_procedimento.SogTitDettaglioProcedimentoDto;
import it.giustizia.smp.dto.pg_soggetto.DettaglioPersonaFisicaDto;
import it.giustizia.smp.dto.pg_soggetto.DettaglioPersonaFisicaProcDto;
import it.giustizia.smp.dto.pg_soggetto.DettaglioPersonaGiurProcDto;
import it.giustizia.smp.model.TAnMagistrati;
import it.giustizia.smp.model.TAnManuali;
import it.giustizia.smp.model.TAnNormative;
import it.giustizia.smp.model.TAnProcedente;
import it.giustizia.smp.model.TAnUfficio;
import it.giustizia.smp.model.TAtto;
import it.giustizia.smp.model.TAvvocati;
import it.giustizia.smp.model.TComunicazione;
import it.giustizia.smp.model.TDefPatr;
import it.giustizia.smp.model.TDefPers;
import it.giustizia.smp.model.TIstanza;
import it.giustizia.smp.model.TPersonaFisica;
import it.giustizia.smp.model.TPersonaFisicaId;
import it.giustizia.smp.model.TPersonaGiur;
import it.giustizia.smp.model.TPersonaGiurId;
import it.giustizia.smp.model.TProcedimento;
import it.giustizia.smp.model.TRelAttoMisPatr;
import it.giustizia.smp.model.TRelAttoMisPers;
import it.giustizia.smp.model.TRelAttoProc;
import it.giustizia.smp.model.TRelAttoStatoAtto;
import it.giustizia.smp.model.TRelBeneAtto;
import it.giustizia.smp.model.TRelIterProc;
import it.giustizia.smp.model.TRelMagProc;
import it.giustizia.smp.model.TRelMagUff;
import it.giustizia.smp.model.TRelProcBene;
import it.giustizia.smp.model.TRelProcCoadiutori;
import it.giustizia.smp.model.TRelProcProc;
import it.giustizia.smp.model.TRelProcSogint;
import it.giustizia.smp.model.TRelProcSogintId;
import it.giustizia.smp.model.TRelSoggIst;
import it.giustizia.smp.model.TRelSogintErediConv;
import it.giustizia.smp.model.TRelSoginvBene;
import it.giustizia.smp.model.TRelSoginvBeneId;
import it.giustizia.smp.model.TRelSoginvSogint;
import it.giustizia.smp.model.TRelSoginvSogintId;
import it.giustizia.smp.model.TSoggetto;
import it.giustizia.smp.model.TTpCatAtto;
import it.giustizia.smp.model.TTpEsito;
import it.giustizia.smp.model.TTpTipoInteresse;
import it.giustizia.smp.model.TTpTipoMisura;
import it.giustizia.smp.model.TTpTipoPrevProc;
import it.giustizia.smp.model.TTpTipoUfficio;
import it.giustizia.smp.utility.tree.ProcedimentoTree;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * ProcedimentoDao
 * 
 * @author Almvaviva S.p.A.
 *         Interfaccia Procedimento DAO
 */
public interface ProcedimentoDao {

    public long aggiornaPersonaFisica(AssociaPersonaDto personaFisicaDto, long idProcedimento, String codUtente);
    
    public long aggiornaPersonaGiuridica(AssociaPersonaDto dto, long idProcedimento, String codUtente);

    public Long aggiornaProcedimento(long idUffOwn, String codUtente, TProcedimento proc, Date dataUltAggiornamento);

    public Long aggiornaPropostaAtto(long idUfficio, long idDestinatario, long idUltDestinatario, long idProcedimento, TAtto atto, String codUtente, TIstanza istanza);

    public Long aggiornaStatoDelProcedimento(long idProcedimento, long idStatoProcedimentoNuovo, Long idAtto, int idUfficioUtente, String codiceUtente);
    
    public Long annullaStatoDelProcedimento(long idProcedimento, long idStatoProcedimentoNuovo, Long idAtto, int idUfficioUtente, String codiceUtente);
    
    public Long aggiornaTipoProcPrevProc(long idProc, long idUffOwn, String codUtente);

    public Long associaAggravamentoAlPadre(Long idAggravamento, Long idPadre, Long numero, Short anno, Integer idUffOwn, String codUtente);

    public void associaMagistratoAlProcedimento(Long idMagistrato, Date dataFineAssegnazione, String tipoSostituzione, Byte ordineDiVisualizzazione, String relatore, Date dataAssegnazione, String ruolo, String altroMotivo, long idProcedimento, Integer idUfficioUtente);

    public AssociaPersonaDto associaNuovaPersonaFisica(int idUffOwn, String codUtente, Long idProc, AssociaPersonaDto soggetto, Date dataUltAggiornamento);

    public AssociaPersonaDto associaNuovaPersonaGiuridica(int idUffOwn, String codUtente, Long idProc, AssociaPersonaDto soggetto, Date dataUltAggiornamento);

    public AssociaPersonaDto nuovaPersonaFisica(int idUffOwn, String codUtente, AssociaPersonaDto soggetto, Date dataUltAggiornamento);

    public AssociaPersonaDto nuovaPersonaGiuridica(int idUffOwn, String codUtente, AssociaPersonaDto soggetto, Date dataUltAggiornamento);

    public AssociaPersonaDto associaPersonaFisica(int idUffOwn, String codUtente, Long idProc, AssociaPersonaDto soggetto, Date dataUltAggiornamento);

    public AssociaPersonaDto associaPersonaGiuridica(int idUffOwn, String codUtente, Long idProc, AssociaPersonaDto soggetto, Date dataUltAggiornamento);

    public void associaSoggettiTitolariPerBeneMisuraPatrimoniale(Long idBene, List<String> idSoggettiTitolariETitoli, long idProcedimento, int idUfficioUtente, long idAtto, long idSoggettoInteressato, String codUtente);

    public void associaSoggettoDirettoInteressato(TRelProcSogint direttoInteressato);

    public void cancellaAvvocato(int idUfficioUtente, Long idAvvocato);

    public void cancellaEredeOppureConviventeDelSoggetto(long idSoggettoInteressato, long idProcedimento, int idUffOwn, int idEredeConv);
    
    public List<Object[]> verificaCancellaRelazioneAvvocatoProcSogint(long idProcedimento, int idUfficio, long idDifensore);
    
    public long verificaDataRevocaDifensoreAssociato(long idProcedimento, int idUfficio, long idDifensore, Date dataRevoca);

    public void cancellaRelazioneAvvocatoProcSogint(long idProcedimento, int idUfficioUtente, long idSoggettoInteressato, Long idAvvocato);
    
    public void cancellaRelazioneIntestatariAvv(long idProcedimento, int idUfficioUtente, long idSoggettoInteressato, Long idAvvocato);

    public List<Object[]> caricaDifensoriAssociatiSoggetto(long idProcedimento, int idUfficioUtente, long idSoggettoInteressato, Long idAvvocato);
    
    public List<Object[]> caricaDifensoriAssociatiIntestatari(long idProcedimento, int idUfficioUtente, long idSoggettoInteressato, Long idAvvocato);

    public ProcedimentoTree costruisciAlberaturaAtti(long tipoUfficio);

    public ProcedimentoTree costruisciAlberaturaAtti(long tipoUfficio, Long idTipoFascicolo, Long idTipoProcedimento);

    public Map<String, Object> dettaglioMagistratoPerProcedimento(int idUfficioUtente, long idProcedimento, long idMagistrato);

    public Map dettaglioPropostaAtto(long idUfficio, long idProcedimento, long idAtto);

    public void dissociaSoggettoDirettoInteressato(TRelProcSogint direttoInteressato);
    
    public List<SogTitDettaglioProcedimentoDto> getSoggettiTit_DettaglioProcedimento(long id);

    public void eliminaAssociazioneMagistratoProcedimento(Long idMagistrato, long idProcedimento, Integer idUfficioUtente);

    public void eliminaRelazioniAttoMisuraPatrimoniale(long idBene, long idProcedimento, int idUfficioUtente, long idAtto, long idSoggettoInteressato);
    
    public void eliminaRelazioniAttoNormativaMisuraPatrimoniale(long idProcedimento, int idUfficioUtente, long idSoggettoInteressato);
    
    public TProcedimento getIdProcedimentoDestinatario(long idProcedimento,int idUffOwn,int idTipoFasc,int idUfficioArrivo);
 
    public List<TTpCatAtto> getAllAttoByType(int type);

    public List<TTpCatAtto> getAllFonte();

    public DettaglioPersonaFisicaDto getDettaglioPersonaFisica(TPersonaFisicaId id);

    public DettaglioPersonaFisicaProcDto getDettaglioPersonaFisicaProc(TPersonaFisicaId id, long codiKIdProc);

    public Map<String, Object> getDettaglioPersonaGiur(TPersonaGiurId id);

    public DettaglioPersonaGiurProcDto getDettaglioPersonaGiurProc(TPersonaGiurId id, long codiKIdProc);

    public Map<String, Object> getDettaglioProcedimento(TProcedimento id);

    public List<TTpEsito> getEsiti(String carattereMisura, long idCategoriaAtto, long idTipoUfficio);

    public Long getIdPadreAggravamento(long idProcedimento);

    public List<Long> getIdProcByAnnoNumProc(int idUffOwn, short annoProc, long numeProc, int idTipoFasc);

    public List<TAnUfficio> getIndirizzoByOrganoAndComune(String idCodiceComune, long idOrgano);

    public List<TAnProcedente> getListaProcedenti(long idUfficio, Long idTipoPrev, Long idTipoFasc);
    
    @SuppressWarnings("rawtypes")
    public List getListaProcedimentiByCriteria(int idUffOwn, Short annoProc, Long numProcDa, Long numProcA, Date dataIscrDa, Date dataIscrA, Long idStatoProc, String cognSogg, String nomeSogg, String ragSociale, Long idPMAssegn, Long idTipoProc, Short annoDecreto, Long numDecreto, Long idTipoProcedente, Long idProcedente, Boolean antimafia, Boolean antiTerrorismo, Boolean ordinaria, Long tipoFascicolo, Integer idTpUffRic, Integer idUffRic, String nomeDifensore, String cognnomeDifensore, String cfDifensore);

    public List<TAnMagistrati> getMagistratiByGeneralita(TAnMagistrati magistrato);

    public TAnMagistrati getMagistratoById(long idMagistrato);

    public List getNumeriRegistro(long idProcedimento);
    
    public List getNumeroRegistroPadre(long idProcedimento);
    
    public List getNumeriRegistroAccertamento(long idProcedimento);

    public List<TTpTipoUfficio> getOrgani(String tipo);

    public List<TSoggetto> getPersonePiuVersioni(long identificativoSoggettoProcedimento, Integer idUfficioUtente);

    public TProcedimento getProcedimentoById(long idProcedimento);
    
    public TProcedimento getProcedimentoById2(long idProcedimento);
    

    public List<SogIntDettaglioProcedimentoDto> getSoggettiInt_Associati(long idUfficio, long idProcedimento, long idFonte);

    public List<SogIntDettaglioProcedimentoDto> getSoggettiInt_DettaglioProcedimento(TProcedimento id, String descRuolo, Character flagValido);
    
	public List<SogIntDettaglioProcedimentoDto> getSoggettiIntInv_DettaglioProcedimento(long idProcedimento, long idSogInv);
    		
    public List<SogIntDettaglioProcedimentoDto> soggettiDirettiInteressatiApplicazioneProvvisoria(long idProcedimento, int idUffOwn, String codiceUtente);
    
    public List<SogIntDettaglioProcedimentoDto> getSoggettiInt_DettaglioProcedimentoVer(TProcedimento id, String descRuolo, Character flagValido, long idSogg, long idUffOwn, Integer idVer);

    public List<SogIntDettaglioProcedimentoDto> getSoggettiInt_NonAssociati(long idUfficio, long idProcedimento);

    public SogIntDettaglioProcedimentoDto getSogIntById(long idSogInt, long idUfficio, long idProcedimento);

    public SogIntDettaglioProcedimentoDto getSogIntById(long idSogInt, long idUfficio, long idProcedimento, String ruolo);

    public List<ContaDifensoriSogIntDto> getSogIntSenzaDifensore(long idProcedimento, long idUfficio);

    public TTpTipoInteresse getTipoInteresseById(int idTipoInteresse);

    public TTpTipoMisura getTipoMisuraById(Long codiFkIdTipoMis);

   

    public void inserisciEredeOppureConviventeDelSoggetto(long idSoggettoInteressato, long idProcedimento, int idUffOwn, String codiceUtente, String codiTipo, TRelSogintErediConv trel);

    public long inserisciFonte(long idUfficio, String codiceUtente, long idProcedimento, long idStatoProcedimento, TAtto atto, TIstanza istanza);
    
    public long inserisciArchiviazione(long idUfficio, String codiceUtente, long idProcedimento, long idStatoProcedimento, TAtto atto, TIstanza istanza);

    public Long inserisciModificaDifensoreAssociato(long idProc, int idUffOWn, long idSogInt, Long idAvvocato, TAvvocati avv, Date dataNomina, Date dataRevoca, String tipoNomina, String codiceUtente);
    
    public Long inserisciModificaDifensoreIntestatario(long idProc, int idUffOWn, long idSogInt, Long idAvvocato, TAvvocati avv, Date dataNomina, Date dataRevoca, String tipoNomina, String codiceUtente);

    public Long inserisciProcedimento(int idUffOwn, String codUtente, TProcedimento proc, Date dataUltAggiornamento, int idTipoFasc);

    public List<TRelSogintErediConv> listaErediOppureConviventiDelSoggetto(long idSoggettoInteressato, long idProcedimento, int idUffOwn, String codiTipo);

    @SuppressWarnings("rawtypes")
    public List listaMagistrati(int idUffOwn, String cognomeMag, String nomeMag);

    /**
     * listaMagistratiPerProcedimento
     * 
     * @param idProcedimento
     * @return List<DMagistrati>
     */
    @SuppressWarnings("rawtypes")
    public List listaMagistratiPerProcedimento(int idUffOwn, long idProcedimento, int ruoloMag);

    public List<TRelBeneAtto> listaRelBeneAttoByAttoAndProc(long idProcedimento, long idAtto, int idUfficioUtente);

    public List<TRelBeneAtto> listaRelBeneAttoByBene(long idBene, long idProcedimento, long idAtto, int idUfficioUtente);

    public List<TRelBeneAtto> listaRelBeneAttoByBeneNoFrazionato(long idBene, long idProcedimento, long idAtto, int idUfficioUtente);
    
    public List<TRelBeneAtto> listaRelBeneAttoByBeneNoFrazionatoConvalida(long idBene, long idProcedimento, long idAtto, int idUfficioUtente);

    public List<TRelAttoMisPatr> listaRelMisPatr(long idSoggetto, long idProcedimento, long idAtto, Integer idUfficioUtente);

    public List<TRelAttoMisPatr> listaRelMisPatrForBene(long idBene, long idSoggetto, long idProcedimento, long idAtto, int idUfficioUtente);

    public List<TRelProcBene> listaRelProcBene(long idProcedimento, int idUfficioUtente);

    public List<TRelMagUff> listaRelUffByUfficio(int idUfficio);
    
	public List<TRelAttoMisPatr> listaRelMisPatrForFiltro(long idBene, long idSoggettoInt, long idSoggettoTit, long idProcedimento, long idAtto, int idUfficioUtente);

    public List<TRelMagUff> listaRelUffByUfficio(int idUfficioUtente, List<TAnMagistrati> mag);

    /**
     * listaSoggettiByCriteria(
     * 
     * @param nome
     * @param cognome
     * @return List<DPersonaFisica>
     */
    @SuppressWarnings({ "rawtypes" })
    public List listaSoggettiByCriteria(int idUffOwn, String nome, String cognome, String codiceStatoNascita, String codiceComuneNascita, String comuneNascitaEstero, String dataNascita, String codiceFiscale, String codiceCui, boolean escludiIntervenienti, Integer idRuolo);

    @SuppressWarnings("rawtypes")
    public List listaSoggettiGiurByCriteria(int idUffOwn, String ragioneSociale, String partitaIva, Integer vidStatoSede, Integer vidComuneSede, String vstatoSedeEstero, boolean escludiIntervenienti, Integer idRuolo);

    public void modificaDatiMagistratoSuProcedimento(Long idMagistrato, Date dataFineAssegnazione, String tipoSostituzione, Byte ordineDiVisualizzazione, String relatore, Date dataAssegnazione, String ruolo, String altroMotivo, long idProcedimento, Integer idUfficioUtente);

    public void modificaEredeOppureConviventeDelSoggetto(long idSoggettoInteressato, long idProcedimento, int idUffOwn, String codiceUtente, TRelSogintErediConv trel);

    public long modificaFonte(long idUfficio, TAtto atto, String codiceUtente, TIstanza istanza);

    public void modificaGratuitoPatrocinioSoggProc(long idProcedimento, int idUfficioUtente, long idSoggettoInteressato, Short annoRegistroGP, Long numeroRegistroGP, Date dataIstanzaGP, Date dataProvvedimentoGP, Date dataRevocaGP,String descNoteGp, String idTenoreGP);

    public void cancellaGratuitoPatrocinioSoggProc(long idProcedimento, int idUfficioUtente, long idSoggettoInteressato) ;
    
//    public long rimuoviDocumentoAllegatoDaAtto(long idUfficio, long atto, long idProcediemnto);

    public List<TRelAttoMisPers> misurePersonaliIscrizioneAggravamento(Long idProcedimento, Integer idUfficioUtente, Long idSoggetto);
    
    public List<TRelProcProc> getProc1ByIdProc2(long idProc2, long idUffMitt, long idUffOwn);
    
    public List<TRelProcProc> getProc1ByIdProc2(long idProc2, long idUffMitt);
    
    public List<TRelProcProc> getProc1ByIdProc2AndTipoFasc(long idProc2, long idUffMitt, long idTipoFasc);
    
    public TRelAttoProc getProcByIdAttoAndTipoFasc(long idAtto, long idUffOwn, long idTipoFasc);
    
    public void eliminaTRelProcSogInt(long idSoggetto, long idProcedimento, int idUfficioUtente);

    public void eliminaTRelProcSogIntEredi(long idSoggetto, long idProcedimento, int idUfficioUtente);
    
    public List<TRelMagProc> verificaOrdineMagistratoProcedimento(long idProcedimento, int idUfficioUtente, byte numOrdine, long idMagistrato);
    
  //P.Q. 04/09/2025 Ticket 20250807017 Inizio
    public Long getIdProcDaRelProcProc(Short annoPadre, Integer numeroPadre, Integer idUfficioUtente);
  //P.Q. 04/09/2025 Ticket 20250807017 Fine
    public Long getIdByAttoEUfficio(Long idAtto, Integer idUfficioUtente);
    
    public List<TDefPatr> getTDefPatrById(long idProc, long idAtto, Integer idUfficioUtente, long idBene, long tipoInt, long sogTit, String tipoRicerca);

    public List<TDefPers> getTDefPersById(long idProcedimento, long idMisura, long idAtto, int idUffOwn, String tipoRicerca);

    public List listaSoggettiMisPersDefinitivita(long idProcedimento, long idAtto, int idUffOwn);
    public List listaSoggettiMisPersDefinitivitaFiltrati(long idProcedimento, long idAtto, int idUffOwn, long idSoggettoDiretto);
    
    public List listaMisPersPerSoggettoDefinitivita(long idSoggetto, long idProcedimento, long idAtto, int idUffOwn);

    public TTpEsito getEsitoById(Integer integer);
    
    /**
     * @param idUffOwn
     * @param listaIdSoggetti
     * @return
     */
    public List<TProcedimento> listaAccertamentiConSoggettiProposta(int idUffOwn, String idProced, String listaIdSoggetti);
    
    /**
     * @param idProc1
     * @param idProc2
     * @param idUffOwn
     * @param codUtente
     * @param idTipoAtto
     * @param idCatAtto
     * @param codiTipoRel
     * @return
     */
    public Long inserisciTRelProcProc(long idProc1, long idProc2, int idUffOwn, String codUtente, long idTipoAtto, long idCatAtto, String codiTipoRel);
    
    
    /**
     * @param personaFisica
     * @param idUffOwn
     * @return
     */
    public TPersonaFisica getPersonaFisicaByCampiChiave (TPersonaFisica personaFisica, int idUffOwn);
    
    /**
     * @param personaGiuridica
     * @param idUffOwn
     * @return
     */
    public TPersonaGiur getPersonaGiuridicaByCampiChiave (TPersonaGiur personaGiuridica, int idUffOwn);

    public List getDecretoFissUdienzaProcedimento(long idProcedimento, long idUfficio);

	public void eliminaTSoggettoInteressatoAndTPersona(long idSoggetto, int idUfficioUtente, long idVersione, String tipoSoggetto, long idProcedimento);
	
	public long getAttoFonteDaEliminare(long idSoggetto, int idUfficioUtente, long idProcedimento);
	
	public void eliminaAttoFonteSenzaSoggetto(int idUfficioUtente, long idProcedimento, long idAttoFonte);

    public List<TRelAttoStatoAtto> verificaStatoAttoRegistrato(long codAtto, long statoAtto, int idUfficioUtente);

    public TAnNormative getNormativaById(long idNormativa);

    public void aggiornaTipoPrevenzioneNormativaSoggetto(long idProcedimento, Integer idUfficioUtente, long idSoggettoInteressato, long idTipoPrev, long idTipoNorm);

    public void aggiornaFlgTipoPrevenzioneProcedimento(TProcedimento procedimento, Character flagOrdinaria, Character flagAntimafia, Character flagAntiterrorismo);


    public boolean inserisciFascicoloPDoc(Long idProcedimento,String codiceFascicolo);
    
    public boolean associaDocFascicoloPDoc(Long vIdProcedimento,Long vCodiKIdAtto, int vCodiKIdUffOwn);
    
    public String ricercaFascicoloPDoc(Long idProcedimento);
    
    public List<TRelProcProc> getTRelProcProcByProc1(long idProc1, Character flgTipoRel, String descUtente, String nomeMetodo);
    
    public List<TRelProcProc> getTRelProcProcByProc2(long id, Character flgTipoRel);
    
    public List<TRelProcSogint> getSogIntPerProcedimento(Long idProcedimento, int idUffOwn);
    
    public List<TRelIterProc> verificaTRelIterProc(long idProcedimento, int idUfficioUtente);
    
	public List getListaProcedimentiRelProcProc(long idProcedimento, Integer idUffOwn, Integer idUffMitt,String funzione) ;
	
	public List getListaProcedimentiCdaTrib(long idProcedimento, Integer idUffOwn) ;
	
	public List getListaProcedimentiTribCda(long idProcedimento, Integer idUffOwn) ;	
	
	public List listaSoggettiMisPersRevocaDefinitivita(long idProcedimento,long idAtto,int idUffOwn,String codUtente,String tipoFunzione,String nomeMetodo);
	public List listaSoggettiMisPersRevocaDefinitivitaFiltrati(long idProcedimento,long idAtto,int idUffOwn,String codUtente,String tipoFunzione,String nomeMetodo,long idSoggettoDiretto);
	
	public List listaMisPersPerSoggettoRevocaDefinitivita(long idSoggetto, long idProcedimento, long idAtto, int idUffOwn,String tipoFunzione);

	public List listaSoggettiMisPatrRevocaDefinitivita(long idProcedimento, long idAtto, int idUffOwn,String codUtente,String tipoFunzione,String nomeMetodo);
	public List listaSoggettiMisPatrRevocaDefinitivitaFiltrati(long idProcedimento, long idAtto, int idUffOwn,String codUtente,String tipoFunzione,String nomeMetodo,long idSoggettoDiretto, long idSoggettoTitolare, long idTipologia, long idCategoria, long idSottocategoria);

	public List listaSoggettiMisPatrDefinitivita(long idProcedimento, long idAtto, int idUffOwn,String codUtente,String tipoFunzione,String nomeMetodo);
	public List listaSoggettiMisPatrDefinitivitaFiltrati(long idProcedimento, long idAtto, int idUffOwn,String codUtente,String tipoFunzione,String nomeMetodo,long idSoggettoDiretto, long idSoggettoTitolare, long idTipologia, long idCategoria, long idSottocategoria);

	public TRelProcSogint getTrelProcSogintByIdsoggIdProcIdUff(Long idProcedimento, Long idSoggetto, int idUffOwn, Long tipoPrev);
	
	public Map<Long,String> getProcedimentiJobDocumentale();
	
	// test gestione fascicoli con chiamata a procedura di verifica 14 maggio 2018
	public Map<Long,String> getTestProcedimentiJobDocumentale();
	// fine test gestione fascicoli con chiamata a procedura di verifica 14 maggio 2018
	
	public TTpTipoPrevProc getNormativaByTRelProcSogintId(TRelProcSogintId id);

	public Long copiaAttoAccertamentoInProcMerito(long idProcAccertamento, long idProcMerito, int idUffOwn, String codUtente);
 
	public Long sostituisciGiudiceDelegato(int idUffOwn, long idMagistratoOut, long idMagistratoIn, long idProcedimento, String tipoSostituzione, String codUtente);

	public List<TRelProcSogint> getListaSoggettiFisiciProcedimento(Long idProcedimento,  Integer idUffOwner);
	
	/***
     * Restituisce true se per il procedimento passatto esistono degli atti in lavorazioni diversi dalla proposta
     * @param idProcedimento
     * @param idUffOwner
     * @return
     */
    public boolean hasAttiInLavorazioni(long idProcedimento, long idUffOwner);

    public TAnManuali getTAnManualiById(long id);
    
    public List<RicercaProcedimentoDto> getListaProcedimentiSogg(int idUffOwn, Long tipoFascicolo, Long idSogg);
    
    public List<RicercaProcedimentoDto> getListaProcedimentiSoggMultiFascicolo(int idUffOwn, String tipiFascicolo, Long idSogg);
    
    public List<MisurePatrSoggDto> misurePatrimonialiSoggetto(int idUffOwn, Long idProc, Long idSoggInt, Long idSoggTit);
    
    public List<TAvvocati> listaDifensoriNoReginde(String cognome, long idSogg, long idProcedimento, int idUffOwn);
    
    public TAvvocati dettaglioDifensore(long idDifensore);
    
	public RicercaProcedimentoDto getDettaglioProcedimentoNum(int idUffOwn, long idProc);

	public List<Map> getProcAggravamento(int idUffOwn, long idProc, long idSogg);
	
	public List<Map> getProcCamere(int idUffOwn, long idProc, long idSogg);
	
	public String getNumeroProcByIdProcedimento(long idProcedimento);
	
	public Long aggiornaIstanzaMP(long idUfficio,long idProcedimento, TAtto atto, String codUtente, TIstanza istanza,TRelSoggIst relSoggIst,String listaIdSoggetti,Long codiKIdAmministratore);

	public List<SogIntDettaglioProcedimentoDto> getListSogAntimafia(int idUffOwn, Long idProc);
	
	public List<ListaProcedimentiSoggDto> listaProcedimentiSoggetto(int idUffOwn, long idSogg);	
	
	public List<Map> listaCoadiutori(long idProc, int idUfficio, long idAmm);
	
	public TRelProcCoadiutori dettaglioCoadiutore(long idProc, int idUfficio, long idAmm, int idProgrCoad);	

	public short salvaCoadiutore(TRelProcCoadiutori coadiutore);

	public void eliminaCoadiutore(TRelProcCoadiutori coadiutore);

	public List<SogTitDettaglioProcedimentoDto> getSoggettiTit_Procedimento(final long id);

	public List<TRelBeneAtto> listaBeniAttoDecisorioFiltrata(long idProcedimento, long idAtto, long idUfficioUtente, 
			long idSoggettoDiretto, long idSoggettoTitolare, long idTipologia, long idCategoria, long idSottocategoria);
	
	public List<SogTitDettaglioProcedimentoDto> getSoggettiTit_ProcedimentoAtto(final long idProc, final long idAtto);

	public void associaRelSoggettoInterveniente(TRelSoginvSogint rec);
	
	public void eliminaRelSoggettoInterveniente(final TRelSoginvSogintId id);
	
	public void associaRelBeneSoggettoInterveniente(TRelSoginvBene rec);
	
	public void eliminaRelBeneSoggettoInterveniente(TRelSoginvBeneId id);
	
	public String eliminaSogInv(long idProc, int idUffOwn, long idSogInv);

	public List<TRelProcProc> getTRelProcProcByIdProc1Padre(long idProcedimento);

	public boolean hasInvioInCassazione(long idProc2);

	public List<TRelProcProc> getTRelProcProcByIdProc1(long idProc2);	
	
	public TTpEsito getTTpEsito(long idEsito);

	public TComunicazione getComunicazioneByIdProcESogIntMis(long idProc, long idSoggetto);

	public TComunicazione getComunicazioneByIdProc1ETipoUff(long idSoggetto, long idProc, int idTipoUff);
	
	public String listaIntervenientiNonAssociati(long idProcedimento, int idUfficio);
	
	public int getUffOwner(long idProcedimento);
	
	public List<TRelAttoProc> getAttoProcDaAssociare();
	
}
