package it.giustizia.smp.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;

import it.giustizia.smp.dto.KeyMessageDto;
import it.giustizia.smp.dto.pg_atto.AttoDocumentaleDto;
import it.giustizia.smp.dto.pg_atto.DettaglioAttoDto;
import it.giustizia.smp.dto.pg_atto.ImpugnazioneCassellarioDTO;
import it.giustizia.smp.dto.pg_atto.RedazioneSchedaDto;
import it.giustizia.smp.dto.pg_cameralini.IntestatarioDto;
import it.giustizia.smp.dto.pg_procedimento.SogIntDettaglioProcedimentoDto;
import it.giustizia.smp.dto.pg_procedimento.SogSpesePDettaglioProcedimentoDto;
import it.giustizia.smp.dto.pg_stralcio.RicercaStralcioDto;
import it.giustizia.smp.model.TAnimali;
import it.giustizia.smp.model.TAtto;
import it.giustizia.smp.model.TAttoGenerico;
import it.giustizia.smp.model.TAttoId;
import it.giustizia.smp.model.TAvvocati;
import it.giustizia.smp.model.TAzienda;
import it.giustizia.smp.model.TBene;
import it.giustizia.smp.model.TBeneFin;
import it.giustizia.smp.model.TBeneImmat;
import it.giustizia.smp.model.TBeneImmobile;
import it.giustizia.smp.model.TCertificatoPenale;
import it.giustizia.smp.model.TCollezioni;
import it.giustizia.smp.model.TComunicazione;
import it.giustizia.smp.model.TContante;
import it.giustizia.smp.model.TDecreto;
import it.giustizia.smp.model.TDefIstGen;
import it.giustizia.smp.model.TDefIstGenId;
import it.giustizia.smp.model.TDefPatr;
import it.giustizia.smp.model.TDefPers;
import it.giustizia.smp.model.TDenaro;
import it.giustizia.smp.model.TImpugnazione;
import it.giustizia.smp.model.TIstanza;
import it.giustizia.smp.model.TNotifDest;
import it.giustizia.smp.model.TNotifRicevute;
import it.giustizia.smp.model.TPersonaFisica;
import it.giustizia.smp.model.TPersonaGiur;
import it.giustizia.smp.model.TProcedimento;
import it.giustizia.smp.model.TProvFg;
import it.giustizia.smp.model.TRelAtti;
import it.giustizia.smp.model.TRelAttoComunicaz;
import it.giustizia.smp.model.TRelAttoIstGen;
import it.giustizia.smp.model.TRelAttoMisPers;
import it.giustizia.smp.model.TRelAttoMisPersId;
import it.giustizia.smp.model.TRelAttoProc;
import it.giustizia.smp.model.TRelBeneAtto;
import it.giustizia.smp.model.TRelBeneAttoId;
import it.giustizia.smp.model.TRelProcBene;
import it.giustizia.smp.model.TRelProcSogint;
import it.giustizia.smp.model.TRelProcSogintAtto;
import it.giustizia.smp.model.TRelProcSogintAttoId;
import it.giustizia.smp.model.TRelProcSogintVer;
import it.giustizia.smp.model.TRelSoggImp;
import it.giustizia.smp.model.TRelSoggIst;
import it.giustizia.smp.model.TRelSoggSpese;
import it.giustizia.smp.model.TSoggetto;
import it.giustizia.smp.model.TTpCatAtto;
import it.giustizia.smp.model.TTpTipoFilePdoc;
import it.giustizia.smp.model.TUdienza;
import it.giustizia.smp.model.TUdienzaId;
import it.giustizia.smp.model.TVeicolo;


/**
 * AttoDao
 * 
 * @author Almvaviva S.p.A.
 *         Interfaccia Atto DAO
 */
public interface AttoDao {

	
	public Long aggiornaIstanzaSoggettoDiffidato( long idUffOwn,  long idDestinatario,  long idProcedimento,  TAtto atto,  String codUtente, TIstanza istanza);
	
	public long aggiornaAccertamento( long idUfficio,  TAtto atto,  String codiceUtente,  long idProc,  long idStatoProc,  TIstanza istanza);

	public long applicaDefinitivita(TRelBeneAttoId idRelBeneAtto, TRelAttoMisPersId idRelAttoMisPers, TProcedimento procedimento, String codUtente, long idTipoAtto, Date dataDefinitivita, long idUffProcProp, Long idDestinatario);

	public void copiaMisBeneAtto(long idProcedimento, long idAttoDecreto, long idBene, int idUffOwn, String codUtente, String flagRevoca, String nomeMetodo);

	public DettaglioAttoDto dettaglioAtto(int idUffOwn, long idAtto, long idProcedimento, String codUtente);

	public Map<String, Object> dettaglioMagistratoPerAtto(Integer idUffOwn, long idAtto, long idMagistrato, String descRuolo);

	public void eliminaMisBeneAtto(long idProcedimento, long idAtto, long idBene, int idUffOwn, long idPersona, long idTitoloInt);

	public void eliminaRelMagAtto(Integer idUffOwn, long idAtto, long idMagistrato, String descRuolo);

	public void esecuzioneAttoRegistrato(Date dataEsecuzione, Long idBene, Long idSoggetto, Long idInteresse, long idProcedimento, int idUfficioUtente, long idAtto, String codUtente);

	public List<TAtto> getAttiByProcedimento(long idProcedimento, long tipoAtto, String listaCategoriaAtto);
	
	public List<TAtto> getAttiOpposizioneDissequestro(long idProcedimento);
	
	public List<TAtto> getAttiPerditaEfficaciaSequestro(long idProcedimento);
	
	public List<TAtto> getAttiRevocaOpposizioneDissequestro(long idProcedimento);

	public List<TAtto> getAttiRevocazioneConfisca(long idProcedimento);

	public List<TAtto> getAttiParere(long idProcedimento);
	
	public List<Map> getRelAttoProcByIdProcAndCat(long idProc, int categoria);

	public List<TAtto> getAttiOrdineCronologico( long idProcedimento);

	public TAtto getAttoById(TAttoId id);
	
	public TRelProcSogintAtto getTRelProcSogintAttoById(TRelProcSogintAttoId id);

//	public List<TRelAttoMisPers> getRelazioniAttiMisurePersonali(long idProcedimento, long idTipoAtto, int idUfficioUtente);

	public TRelAttoProc getTAttoProcByProcedimento(long idAggravamento, int idUffOwn);
	
	public TRelProcSogintVer getTRelProcSogintVerByAtto(long idAtto, int idUffOwn);
		
	public TUdienza getUdienzaById(TUdienzaId idUdienza);

	public void inserisciMagistratoPerAtto(Integer idUffOwn, long idAtto, long idMagistrato, String tipoRuoloMag, String flgRelatore, String codUtente);

	public long inviaAtto( long idProcedimento, TAtto atto, long idStatoAtto, long idDest,  long idUdienza);

	public void attoDecretoApplicazioneProvvisoria ( TAtto atto,  String idMisureCaricate,  long vidProc,  long vidAtto,  long vidUffOwn,  TRelAttoMisPers relAttoMisPers,  long idSoggInt,  String cauzione,  String articolo67,  String vcodUtente,  String idPrescrizioni,  BigDecimal importoCauzione, String articolo9c1);
	
	public void attoDecretoRevocaApplicazioneProvvisoria ( TAtto atto,  String idMisureCaricate,  long vidProc,  long vidAtto,  long vidUffOwn,  TRelAttoMisPers relAttoMisPers,  long idSoggInt,  String cauzione,  String articolo67,  String vcodUtente,  String idPrescrizioni,  BigDecimal importoCauzione, String articolo9c1);
	
    public Long inviaAttoDecretoSequestro( long idUfficio,  long idProcedimento,  TAtto atto,  String codUtente,  long tipoAtto, long categoriaAtto,  long idStatoAtto,  long idDestinatario,  long idUltDestinatario,  long codUdienzaAtto,  long codUdienzaFiss,  long codUdienzaInFaseDiRip,  long codUdienzaInFaseDiRim,  long statoUdienzaPrec,  long idMagistrato,  String giudiceDelegato);

    public Long aggiornaAttoDecretoSequestro( long idUfficio,  long idProcedimento,  TAtto atto,  String codUtente,  long tipoAtto, long categoriaAtto,  long idStatoAtto,  long idDestinatario,  long idUltDestinatario);
    
	public Long inviaAttoDecretoConvalidaRevocaSequestro( long idUfficio,  long idProcedimento,  TAtto atto,  String codUtente,  long tipoAtto, long categoriaAtto,  long idStatoAtto,  long idDestinatario,  long idUltDestinatario,  long codUdienzaAtto,  long codUdienzaFiss,  long codUdienzaInFaseDiRip,  long codUdienzaInFaseDiRim,  long statoUdienzaPrec,  String tipoConvalidaRevoca,  String nomeMetodo);

	public Long inviaDecretoPerditaEfficaciaSequestro( long idUfficio,  long idProcedimento,  TAtto atto,  String codUtente,  long tipoAtto, long categoriaAtto,  long idStatoAtto,long codUdienzaAtto,  long codUdienzaFiss,  long codUdienzaInFaseDiRip,  long codUdienzaInFaseDiRim,  long statoUdienzaPrec,  String tipoConvalidaRevoca,  String nomeMetodo);	
	
	public Long inviaAttoDecretoOpposizioneDissequestro( long idUfficio,  long idProcedimento,  TAtto atto,  String codUtente,  long tipoAtto, long categoriaAtto,  long idStatoAtto,  long idDestinatario,  String nomeMetodo);
	
	public Long inviaAttoDecretoRevocaOpposizioneDissequestro( long idUfficio,  long idProcedimento,  TAtto atto,  String codUtente,  long tipoAtto, long categoriaAtto,  long idStatoAtto,  long idDestinatario,  String nomeMetodo);

	public Long inviaAttoRevocazioneConfisca( long idUfficio,  long idProcedimento,  TAtto atto,  String codUtente,  long tipoAtto, long categoriaAtto,  long idStatoAtto,  long idDestinatario,  String nomeMetodo);
	
	public long inviaDecretoIncompetenza(long idProcedimento, long idAtto, long idMittente, long idDestinatario1, long idDestinatario2, long idDestinatario3, String codUtente, long idUfficio, long categoriaAtto, String nomeMetodo);

	public void inviaDecretoIncompetenzaProcura(long idProcedimento, long idAtto, long idMittente, long idDestinatarioProposta, long idDestinatario2, long idDestinatario3, String codUtente, long idUfficio, long categoriaAtto, String nomeMetodo);

	public void inviaDefinitivitaDecisorioIGradoRiesame( long idProcedimento,  long numeProc,  long annoProc,  long idAtto,  long tipoAtto, 
														 long idMittente,  long idProcGenerale,  long idProcUdienza,  String codUtente, 
														 Date dataDefinitivita);
	
	public List listaBeniNoMisPatr(long idUffOwn, long idProcedimento, long idAtto);

	public List listaBeniNoTitDir(long idUffOwn, long idProcedimento, long idAtto);

	public List listaMagistratiPerAtto(int idUffOwn, long idAtto, int ruoloMag);
	
	// Specifiche Intervento-006-SpeseProces-NumAtto: aggiunto parametro di passaggio
	public long modificaAtto(int idUffOwn, TAtto atto, long idStatoAtto, TDecreto decreto, boolean test);

	public void modificaMagistratoPerAtto(Integer idUffOwn, long idAtto, long idMagistrato, String descRuolo, String descNuovoRuolo, String flgRelatore);

	public long modificaUdienza(TUdienza udienza, long idAtto, Blob blobDocumento, String nomeDocumento, String fileType, Date dataEmissione, Date dataDeposito);

	public long salvaAtto(int idUffOwn, long idProcedimento, String codUtente, TAtto atto, long idStatoAtto);

	public long salvaAttoDecretoConMagistrato(int idUffOwn, long idProcedimento, String codUtente, TAtto atto, long idStatoAtto, TDecreto decreto);

	public long salvaAttoDecretoDecisorio(int idUffOwn, long idProcedimento, String codUtente, TAtto atto, long idStatoAtto, long idAttoOrigineMisure, TDecreto decreto);
	
	public long salvaAttoSentenzaRicorsoIIGrado(int idUffOwn, long idProcedimento, String codUtente, TAtto atto, long idStatoAtto, long idAttoOrigineMisure, TDecreto decreto);

	public Map salvaUdienza(TUdienza udienza, long idTipoAtto, long idCategoriaAtto, Blob blobDocumento, String nomeDocumento, String fileType);

	public Long aggiornaDecisorioIGradoSoggettoDiffidato(long idUffOwn, long idProcInUdienza, long idProcGenerale,  long idProcedimento,  TAtto atto,  String codUtente,  int idEsito);

	public Long aggiornaDecisorioPrevenzione( long idUffOwn,  long idProcInUdienza,  long idProcGenerale,  long idProcProponente,  long idProcedimento,  TAtto atto,  String codUtente,  int tipoUff,  long idMagistrato, String giudiceDelegato);

	public Long aggiornaSentenzaOpposizioneDissequestro(long idUffOwn, long idUff1, long idUff2, long idUff3, long idProcedimento, TAtto attoForm, String codUtente);

	public Long aggiornaSentenzaRevocazioneConfisca(long idUffOwn, long idUff1, long idUff2, long idUff3, long idProcedimento, TAtto attoForm, String codUtente);

	public void definitivitaMisPatrAttoRegistratoSalva(Date dataDefinitivita, Long idBene, long idSoggetto, long idInteresse, long idProcedimento, Integer idUfficioUtente, long idAtto, long tipoAtto, long tipoMisura, String codUtente);

    public void definitivitaMisPersAttoRegistratoSalva(Date dataDefinitivita, long idProcedimento, Integer idUfficioUtente, long idAtto, long idMisura, long tipoMisura, long tipoAtto, String codUtente);
  
	public void definitivitaMisPatrCancella(Long idBene, long idSoggetto, long idInteresse, long idProcedimento, Integer idUfficioUtente, long idAtto, long tipoAtto, long tipoMisura, String codUtente);

    public void definitivitaMisPersCancella(long idProcedimento, Integer idUfficioUtente, long idAtto, long idMisura, long tipoMisura, long tipoAtto, String codUtente);
        
	public TDefPatr getTDefPatrById(TDefPatr id);
	
	public TDefPers getTDefPersById(TDefPers id); 
    
    public void definitivitaAttoRegistratoConfermaInvia(long idProcedimento, long idAtto, int intValue, long idProcUdi, long idProcProp, long idProcGen, String codUtente, int aggiornaStatoProc);
    
    public void definitivitaAttoRegistratoConfermaInviaData(long idProcedimento, long idAtto, int intValue, long idProcUdi, long idProcProp, long idProcGen, String codUtente, int aggiornaStatoProc, Date dataDef);
    
    public void definitivitaRevocazioneConfiscaConfermaInvia(long idProcedimento, long idAtto, int idUffOwn, long idDest1, long idDest2, long idDest3, String codUtente, int aggiornaStatoProc);
    
    public void definitivitaIIGradoConfermaInvia(long idProcedimento, long idAtto, int idUffOwn, long idDest1, long idDest2, long idDest3, String codUtente, int aggiornaStatoProc);
    
    public Map inserisciDecretoAnticipazioneRinvioUdienza( TUdienza udienza,  long idTipoAtto,  long idCategoriaAtto,  Blob blobDocumento,  String nomeDocumento,  String fileType,  long idUdienzaPrecedente);
    
    public Long aggiornaDecretoAnticipazioneRinvioUdienza( TUdienza udienza,  TAtto atto,  long idUdienzaPrecedente,  long idUffDest,  long idProc,  String codUtente);
    
    public long inserisciAttoDecretoConvalidaSequestro( int idUffOwn,  long idProcedimento,  String codUtente,  TAtto atto,  long idStatoAtto,  long idAttoOrigine,  TDecreto decreto,  String nomeMetodo);
      
    public long inserisciDecretoPerditaEfficacia( int idUffOwn,  long idProcedimento,  String codUtente,  TAtto atto,  long idStatoAtto,  long idAttoOrigine,  TDecreto decreto,  String nomeMetodo);
   
    public long inserisciAttoDecretoOpposizioneDissequestro( int idUffOwn,  long idProcedimento,  String codUtente,  TAtto atto,  long idStatoAtto,  long idAttoOrigine,  TDecreto decreto,  String nomeMetodo);
    
    public long inserisciAttoRevocaDecretoOpposizioneDissequestro( int idUffOwn,  long idProcedimento,  String codUtente,  TAtto atto,  long idStatoAtto,  long idAttoOrigine,  TDecreto decreto,  String nomeMetodo);
    
    public long inserisciAttoRevocazioneConfisca( int idUffOwn,  long idProcedimento,  String codUtente,  TAtto atto,  long idStatoAtto,  long idAttoOrigine,  TDecreto decreto,  String nomeMetodo);
    
    public long inserisciAttoSentenzaOpposizioneDissequestro( int idUffOwn,  long idProcedimento,  String codUtente,  TAtto atto,  long idStatoAtto,  long idAttoOrigine,  TDecreto decreto,  String nomeMetodo);
    
    public long inserisciAttoSentenzaRevocazioneConfisca( int idUffOwn,  long idProcedimento,  String codUtente,  TAtto atto,  long idStatoAtto,  long idAttoOrigine,  TDecreto decreto,  String nomeMetodo);
    
    public long inserisciAttoDecretoRevocaSequestro( int idUffOwn,  long idProcedimento,  String codUtente,  TAtto atto,  long idStatoAtto,  long idAttoOrigine,  String nomeMetodo);
    
    public boolean esisteDecretoConvalidaSequesto(long idAttoOrigine, long idUffOwn);

    public Map salvaUdienzaDecretoSeqConv(TUdienza udienza, long codiceAtto, long codUdienzaFiss, long codUdienzaVerbRis);

    public void eliminaUdienzaSeqConv(long idUdienza, long codAtto, long idUfficio);
    
    public TTpCatAtto getCatAttoById(int idCatAtto);

    public Map<String, Long> inserisciAttoDecretoAsseRemNuovoRuolo(TUdienza uPojo, long idTipoAtto, long idCategoriaAtto, long codUdienza);
    
    public Map salvaUdienzaDecretoRiemissioneSuRuolo(TUdienza uPojo, long codiceAtto, TAtto atto);
    
    public long inviaAttoDecretoAssegnazioneNuovoRuolo(  TAtto atto,  int idUffOwn,  long idProcedimento,  String codUtente,  long idStatoAtto,  long codUdienza,  long idProcUdi);

    public long inviaAttoDecretoRemissioneSuRuolo(TAtto atto, int idUffOwn, Long idProcedimento, String codUtente, long idStatoAtto, long codUdienzaPrec, long codUdienzaNew, long idProcUdi);
    
    public Object getAttoByPrimaryKey(Class<?> clazz, Criterion c1, Criterion c2);
    
    public long inserisciAttoGenerico( int idUffOwn,  long idProcedimento,  String codUtente,  int idTipoAtto, int idCatAtto,  int idStatoAtto,  String nomeMetodo);

	public boolean eseguitoFirmaRemota(long longValue);
	
	public boolean inserisciDocumentoPDoc( Long idDocumentoInserito, Long vCodiKIdAtto, int vCodiKIdUffOwn,  String tipoDocumento,  String fileName, String codugnaList);
	
	public long rimuoviDocumentoAllegatoDaAtto( long idUfficio,  long atto,  long idProcediemnto);
	
	public KeyMessageDto cancellaAtto( int idUfficio,  long codAtto,  String descUtente,  String nomeMetodo);
	
	public List<TRelAtti> getRelAtti( long idAttoOrigine,  int idUffOwnerOrigine,  long idAttoe,  int idUffOwnere,  String descUtente,  String nomeMetodo);
	
	public List<TRelAttoIstGen> getSogNoDecErroreMat(long idProcedimento, long idAtto,long idUffOwn, String descAtto);

	
	public TRelAttoProc getRelAttoProcByAtto( long idProcedimento,  int idUfficioUtente,  long codiKIdAtto);
	
	public KeyMessageDto isEsisteDecretoSequestroProcedimento( long idProcedimento, Integer idUfficioUtente, String descUtente,  String nomeMetodo);
	
	public KeyMessageDto insDecretoRigettoTotaleSequestro( int idUffOwn,  long idProcedimento,  String codiceUtente,  TAtto atto,  long idStatoAtto,  String nomeMetodo);
	
	public Long inviaAttoDecretoRigettoTotaleSequestro( long idUfficio,  long idProcedimento,  TAtto atto,  String codUtente,  long tipoAtto, long categoriaAtto,  long idStatoAtto,  long idDestinatario,  long idUltDestinatario,  long codUdienzaAtto,  long codUdienzaFiss,  long codUdienzaInFaseDiRip,  long codUdienzaInFaseDiRim,  long statoUdienzaPrec,  String nomeMetodo);
	
	public Long registraAttoGenerico( long idUfficio,  long idProcedimento,  TAtto atto,  String codUtente,  long idStatoAtto,  TAttoGenerico attoGenerico,  String nomeMetodo);

	public Long registraOrdinanazaArt15Antimafia( long idUfficio,  long idProcedimento,  TAtto atto,  String codUtente,  long idStatoAtto,Date dataCessazioneMisura, TAttoGenerico attoGenerico,  String nomeMetodo);
	
	public Long registraIntercettazioniTelefoniche(  long idUfficio,   long idProcedimento,   TAtto atto,  String codUtente, long idStatoAtto, long idMagistrato,  TAttoGenerico attoGenerico,  String nomeMetodo);
 	
	public Long registraOrdinanazaArt14Antimafia( long idUfficio,  long idProcedimento,  TAtto atto,  String codUtente,  long idStatoAtto,long idMagistrato,Date dataCessazioneMisura,Date dataRipresaMisura, TAttoGenerico attoGenerico,  String nomeMetodo);
	
	public List<SogIntDettaglioProcedimentoDto> getRicercaSoggettoAtto( long idProcedimento, long idatto,  int iduff,  int descRuolo,  Character flagValido);
	
	public List<SogIntDettaglioProcedimentoDto> getRicercaSoggettoRicorso( long idProcedimento,  long idatto,  int iduff, int descRuolo);
	
	public List<TAvvocati> getRicercaAvvRicorso(long idProcedimento,long idatto,int iduff);
    
	//MEV spese processuali Marzo 2020
	public List<SogSpesePDettaglioProcedimentoDto> getRicercaSoggettoAttoSpeseProc( long idProcedimento, long idatto,  int iduff,  int descRuolo,  Character flagValido);
	//Mev spese processulai Marzo 2020
	
	//MODIFICA 01/2014
	public KeyMessageDto controllaCollegioAtto( long idProcedimento,  String idCatAtto,  String descUtente,  long idUffOwn, String printMethodName);

	public KeyMessageDto controllaCollegioAttoInvio(long idProcedimento, long idAtto, String descUtente, long idUfficio, String printMethodName);
	//MODIFICA 01/2014
	
	 public Long getMagistratoPerAtto( Integer idUffOwn,  long idAtto,  int descRuolo);
	 
	public List<RicercaStralcioDto> getRicercaMisureAtto( long idAtto,  long idProcedimento,  long idUfficio,  String idMisure,  String idSoggettiInt,  String idSoggettiTit,  Integer idTipoBene,  Integer idCatBene,  Integer idSctgBene,  String flgValido, int categoriaAtto, int stato);

	public List<RicercaStralcioDto> getRicercaMisureAttoCass( long idAtto,  long idProcedimento,  long idUfficio,  String idMisure,  String idSoggettiInt,  String idSoggettiTit,  Integer idTipoBene,  Integer idCatBene,  Integer idSctgBene,  String flgValido, int categoriaAtto, int stato);	

	/**
	 * salvaAttoRicorso
	 * @param idUffOwn
	 * @param idProcedimento
	 * @param codiceUtente
	 * @param atto
	 * @param idStatoAtto
	 * @param idAttoOrigineMisure
	 * @return id atto originato
	 */
    public long salvaAttoRicorso( int idUffOwn,  long idProcedimento,  String codiceUtente,  TAtto atto,  long idStatoAtto,  long idAttoOrigineMisure);

    public Long registraAttoRicorsoInAppello( long idUfficio,  long idProcedimento, String listaIdSoggetti,TRelSoggImp soggImpTmp, TImpugnazione impugnazione, TAtto atto,  String codUtente,  long tipoAtto, long categoriaAtto,  long idStatoAtto,  long idDestinatario, Long codiKIdAmministratore,BigDecimal idIntestatario);
 
    
    /**
     * metodo per la registrazione del ricorso in cassazione 
     * @param idUfficio
     * @param idProcedimento
     * @param impugnazione
     * @param atto
     * @param codUtente
     * @param tipoAtto
     * @param categoriaAtto
     * @param idStatoAtto
     * @param idDestinatario
     * @return
     */
     public Long registraAttoRicorsoInCassazione( long idUfficio,  long idProcedimento,String listaIdSoggetti, TRelSoggImp soggImpTmp,TImpugnazione impugnazione, TAtto atto,  String codUtente,  long tipoAtto, long categoriaAtto,  long idStatoAtto,  long idDestinatario, Long codiKIdAmministratore);

    
    //ricorso in appello
    public Character impostaRicorsoMisPatr( long idProcedimento, long idAtto,  long idUffOwner,  boolean checkedSoggValido,  long idSoggInt,  long idBene,  String idSoggTit,  int tipoBene,  int catBene,  int sottoCatBene,  String codiceUtente, int catAtto);
    
    public Character impostaRicorsoSoggetto( long idProcedimento, long idAtto,  long idUffOwner,  boolean checkedSoggValido,  long idSoggInt,  String idsSoggTit,  String idsMisure,  int tipoBene,  int catBene,  int sottoCatBene,  String codiceUtente, int catAtto);
    
    public List<TAvvocati> getAvvocatiDifensori( long idProc, long idUfficio, long idAvvocato);
    
    public List<TAvvocati> getAvvocatiDifensoriIntestatari(long idProc, long idUfficio, long idAvvocato);
   
    public List<SogIntDettaglioProcedimentoDto> getSoggettiDifensori( long idUfficio,  long idProcedimento,  long idAvvocato) ;
    
    public List<SogIntDettaglioProcedimentoDto> getSoggettiDifensoriProcura( long idUfficio,  long idProcedimento) ;
    
    public Character impostaRicorsoMisPers( long idProcedimento,  long idAtto, long idUffOwner,  boolean checkedSoggValido, long idMisuraPers,  long idSoggInt,  String codiceUtente, int catAtto);
    
    public TImpugnazione getImpugnazioneByIdAttoUfficioOwner( long idAtto, int idUfficio);
    //fine ricorso in appello
 
    /**
     * ricercaAttiRicorso
     * @param idProcedimento
     * @param IdUffOwner
     * @return List<TAtto> 
     */
    public List<TAtto> ricercaAttiRicorso( long idProcedimento, long idUffOwner);   
 
    /**
     * inviaRicorsoInAppello
     * @param iduffown
     * @param idproc
     * @param iddestinatario
     * @param descutente
     */
    public void inviaRicorsoInAppello( long iduffown,  long idproc,  long iddestinatario, Date datatrasmCda, String descutente);

    /**
     * ricercaRicorsoInLavorazione
     * @param idProcedimento
     * @param idUffOwner
     * @return
     */
   
    public Character impostaRicorsoCassMisPatr( long idProcedimento, long idAtto,  long idUffOwner,  boolean checkedSoggValido,  long idSoggInt,  long idBene,  String idSoggTit,  int tipoBene,  int catBene,  int sottoCatBene,  String codiceUtente, int catAtto);
    
    public Character impostaRicorsoCassSoggetto( long idProcedimento, long idAtto,  long idUffOwner,  boolean checkedSoggValido,  long idSoggInt,  String idsSoggTit,  String idsMisure,  int tipoBene,  int catBene,  int sottoCatBene,  String codiceUtente, int catAtto);
    
     public Character impostaRicorsoCassMisPers( long idProcedimento,  long idAtto, long idUffOwner,  boolean checkedSoggValido, long idMisuraPers,  long idSoggInt,  String codiceUtente, int catAtto);
    
    
    public List<TAtto> ricercaRicorsoInLavorazione( long idProcedimento, long idUffOwner);
    
    public KeyMessageDto controllaImpugnazioneCauArt67( long idProc, long idAtto, long idUfficio, String descUtente, String metodo);

    public KeyMessageDto controllaMisureDefinitivitaIGrado( long idProc, long idAtto, long idUfficio, String descUtente, String funzione, String metodo);
   
    
    public KeyMessageDto controllaDefinitivitaIIGradoSuMisure( long idProc, long idAtto, long idUfficio, String descUtente, String funzione, String metodo);   
    
    public KeyMessageDto controllaMisureDefinitivitaIIGrado( long idProc, long idAtto, long idUfficio, String descUtente, String funzione, String metodo);
    
    public long inserisciParere( int idUffOwn,  long idProcedimento,  String codiceUtente,  TAtto atto,  long idStatoAtto,  long idAttoOrigine,  TAttoGenerico attoGenerico,  String nomeMetodo);
    
    public Long aggiornaInviaParere( boolean isRichiesta,  long idUfficio,  long idProcedimento,  TAtto atto,  String codUtente,  long tipoAtto, long categoriaAtto,  long idStatoAtto,  long idDestinatario,  String nomeMetodo); 

    public KeyMessageDto controllaRegImpugnazione( long idProc, long idAtto, long idUfficio, String descUtente, String metodo);

    public KeyMessageDto controllaInsertDefinitivita( long idProc, long idMisPers, long idBene, long idInteresse, long idSoggetto , Integer idUfficio, String descUtente, String funzione, String metodo);

    public KeyMessageDto controllaUpdEsitoMisPers( long idProc, long idAtto,long idSoggetto, long idEsito, String cauzione, String art67 , Integer idUfficio, String descUtente, String funzione, String metodo);
    

    
	public long inserisciDecretoIncompetenza(int idUffOwn, long idProcedimento, String codUtente, TAtto atto, long idStatoAtto, String nomeMetodo);

	public long inserisciDecretoOrdinanzaRestituzioneBeni( int idUffOwn,  long idProcedimento,  String codiceUtente,  TAtto atto,  long idStatoAtto,  String nomeMetodo);

    public Long inviaAttoDecretoRestBeni( long idUfficio,  long idProcedimento,  TAtto atto,  String codUtente,  long tipoAtto, long categoriaAtto,  long idStatoAtto,  long idDestinatario,  String nomeMetodo);
	
    public void revocaDefinitivitaMisPatrAttoRegistratoSalva( Long idBene, long idSoggetto, long idInteresse, long idProcedimento, Integer idUfficioUtente, long idAtto, String codUtente,String tipoFunzione,String nomeMetodo);
	
    public void revocaDefinitivitaMisPersAttoRegistratoSalva(long idProcedimento, Integer idUfficioUtente, long idAtto, long idMisura, String codUtente,String tipoFunzione,String nomeMetodo);
   
    public void revocaDefinitivitaInvio(  long idProcedimento,  Integer idUfficioUtente,  long idAtto,  String codUtente, String tipoFunzione, String nomeMetodo) ;
    
    public KeyMessageDto controllaRegistrazioneDecisorio( long idProc, long idAtto, long idUfficio, String descUtente, String funzione, String metodo);
    
    public List<AttoDocumentaleDto> getDocumentiJobDocumentale();

	public List<RedazioneSchedaDto> getListaMisurePerRedazioneScheda(long idProcedimento, int idUffOwn, String tipo);
    
	public List<SogIntDettaglioProcedimentoDto> listaTerziNoTitDir(long idUffOwn, long idProcedimento, long idAtto);
	
	
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
	 * @return
	 */
	public Long inserisciRedazioneScheda(
			 long idUffOwn,
			 long idTipoProc,
			 Date dataRedazioneScheda,
			 String descUtente,
			 String descSedeOrgano,
			 String descOrgano,
			 long idAttoMisura,
			 long idMisuraPersonale,
			 long idProcedimento
			); 
	
	/***
	 * Esegue l'inserimento dell'atto relativo alla scheda del foglio complementare
	 * @param idUffOwn
	 * @param idTipoProc
	 * @param dataRedazioneScheda
	 * @param descUtente
	 * @param descSedeOrgano
	 * @param descOrgano
	 * @param idAttoMisura
	 * @param idMisuraPersonale
	 * @param idProcedimento
	 * @return
	 */
	public Long inserisciRedazioneSchedaFC(
			 long idUffOwn,
			 long idTipoProc,
			 Date dataRedazioneScheda,
			 String descUtente,
			 String descSedeOrgano,
			 String descOrgano,
			 long idAttoMisura,
			 Long idMisuraPersonale,
			 long idProcedimento, 
			 boolean fcem, 
			 final Long idSogInt
			); 
	
	/**
	 * Restituisce la lista delle relazione della comunicazioni per l'atto passato
	 * @param idAtto
	 * @return
	 */
	public List<TRelAttoComunicaz> getTRelAttoComunicaz( long idAtto);
	
	public List<TRelAtti> getTRelAtto(long idAtto,int idUffOwn);
	
	/**
	 * Restituisce la lista delle relazione della comunicazioni per l'atto passato
	 * @param idAtto
	 * @return
	 */
	public TComunicazione getTComunicazioneById( long idComuniczione);


	/**
	 * metodo per il versionamento del soggetto
	 * @param idUffOwn
	 * @param idProcedimento
	 * @param descUtente
	 * @param atto
	 * @param idMagistrato
	 * @param personaFisica
	 * @param personaGiur
	 * @param tipoSoggetto
	 * @return Long idAtto 
	 */
	public Long inserisciAttoVerSoggetto( long idUffOwn, long idProcedimento, String descUtente, TAtto atto,
										  long idMagistrato, TPersonaFisica personaFisica, TPersonaGiur personaGiur, String tipoSoggetto);

	
	/**
	 * Implementazione dell'invio al casellario
	 * @param idUffOwn
	 * @param idAttoComunicazione
	 * @param dataInvio
	 * @param idComunicazione
	 * @param idProcedimento
	 * @param numSoggettoCasellario
	 * @param descUtente
	 * @param xmlComunicazione
	 * @param fileEstrattoCertif
	 * @param flagStatoInvio 
	 * @return
	 */
	public String invioCasellario(
			 Long idUffOwn,
			 Long idAttoComunicazione,
			 Date dataInvio,
			 Long idComunicazione,
			 Long idProcedimento,
			 Long numSoggettoCasellario,
			 String descUtente,
			 Blob xmlComunicazione,
			 Blob fileEstrattoCertif,
			 Blob moduloElettorale, 
			 Character flagStatoInvio
			);
	
	/***
	 * Imposta la comunicazione con il casellario a cancellata
	 * @param idUffOwn
	 * @param idComunicazione
	 * @param descUtente
	 * @return
	 */
	public String cancellaCasellario(
			 Long idUffOwn,
			 Long idComunicazione,
			 String descUtente
			);
	
	public KeyMessageDto controllaEsitoFinale( int idUffOwn,  long idProc,  long idAtto,  int catAtto);
	/**
     * Restituisce le impugnazione della misura per il grado precedente
     * @param idProcedimento
     * @param IdUffOwner
     * @param idMisuraPers
     * @param idAtto, Atto decisorio del grado attuale
     * @return List<ImpugnazioneCassellarioForm>
     */
    public List<ImpugnazioneCassellarioDTO> listaImpugnazioniGradoPrec( long idProcedimento, long idUffOwner, long idMisuraPers, long idAtto);
    
    
    /***
	 * Esegue l'inserimento dell'atto relativo alla richiesta di un certificato penale
	 * @param idProcedimento
	 * @param idUffOwn
	 * @param descUtente
	 * @param descSedeOrgano
	 * @param descOrgano
	 * @param idSoggetto
	 * @param flagStatoInvio
	 * @param xmlRichiesta
	 * @param xmlRisposta
	 * @return ritorna l'id dell'atto appena inserito.
	 */
	public Long inserisciCertificatoPenale(
			 long idProcedimento,
			 long idUffOwn,
			 String descUtente,
			 String descSedeOrgano,
			 String descOrgano,
			 long idSoggetto,
			 Character flagStatoInvio,
			 Blob xmlRichiesta,
			 Blob xmlRisposta
			);

	public TCertificatoPenale getTCertificatoPenale(long idProcedimento, long idAtto, long idUffOwn);
	
	public List<TAtto> getAttiProrogaSequestro(long idProcedimento, long idUffOwn);
	
    public long inserisciAttoDecretoProrogaSequestro( int idUffOwn,  long idProcedimento,  String codUtente,  TAtto atto,  long idStatoAtto,  long idAttoOrigine,  TDecreto decreto,  String nomeMetodo);
  
	public List<TAtto> getAttiProrogaSequestroCdA(long idProcedimento, long idUffOwn);
    
    public Long inviaAttoDecretoProrogaSequestro(long idUfficio, long idProcedimento, TAtto atto, String codUtente, long tipoAtto, long categoriaAtto, long idStatoAtto, String nomeMetodo);
        
    public boolean controllaPresenzaGDPerProc(int idUfficio, long idProcedimento);

    public void aggiornaDatiAtto( long codiceAtto,  int idUffOwner,  String note);

    public void aggiornaDatiAtto( TAtto datiAtto);
    
    public KeyMessageDto controllaAmministratoriSenzaBeni( int idUffOwn,  long idProc,  long idAtto);
    
    public List<Map> controllaAmministratoriSenzaBeniDecisorio(int idUffOwn, long idProc, long idAtto);
    
    // P.Q. Mev 2020/48 Inizio
    public List<Map> controllaAmministratoriSenzaBeniDecisorioSecondoGrado(int idUffOwn, long idProc, long idAtto, long idFascicoloGestione);
    // P.Q. Mev 2020/48 Fine
    
    public List<Map> controllaBeniAmministratoriSenzaAmministratore(int idUffOwn, long idProc, long idAtto);

    public KeyMessageDto controllaInserimentoDecretoSospensioneTemporanea(long idProcedimento, int idUffOwn);
    
    public long inserisciAttoDecretoSospensioneTemporanea(int idUffOwn, long idProcedimento, String codUtente, TAtto atto, long idStatoAtto, TDecreto decreto, String nomeMetodo);
    
    public Long inviaAttoDecretoSospensioneTemporanea(long idUfficio, long idProcedimento, TAtto atto, String codUtente, long tipoAtto, long categoriaAtto, long idStatoAtto, String nomeMetodo);
    
    public long inserisciAttoDecretoProrogaSospensioneTemporanea( int idUffOwn,  long idProcedimento,  String codUtente,  TAtto atto,  long idStatoAtto,  long idAttoOrigine,  TDecreto decreto,  String nomeMetodo);

    public Long inviaAttoDecretoProrogaSospensioneTemporanea(long idUfficio, long idProcedimento, TAtto atto, String codUtente, long tipoAtto, long categoriaAtto, long idStatoAtto, String nomeMetodo);

	public List<TAtto> getAttiSospensioneTemporanea(long idProcedimento, long idUffOwn);
	
	public ImpugnazioneCassellarioDTO getSentenzaCassazione(int idUffOwn, long idProc, long idAttoDecisorioAttuale, long idMisuraPersAttuale);
	
    public long inserisciAttoDecretoSequestroSospensioneTemporanea( int idUffOwn,  long idProcedimento,  String codUtente,  TAtto atto,  long idStatoAtto,  long idAttoOrigine,  TDecreto decreto,  String nomeMetodo);

    public Long inviaAttoDecretoSequestroSospensioneTemporanea(long idUfficio, long idProcedimento, TAtto atto, String codUtente, long tipoAtto, long categoriaAtto, long idStatoAtto, String nomeMetodo);
    
    public List<TAtto> getAttiDecretoApplicazioneProvvisoria(long idProcedimento, long idUffOwn);
    
    public long inserisciAttoDecretoRevocaApplicazioneProvvisoria( int idUffOwn,  long idProcedimento,  String codUtente,  TAtto atto,  long idStatoAtto,  long idAttoOrigine,  TDecreto decreto,  String nomeMetodo);

	public TDecreto getTDecreto(long idAttoDecreto, int idUffOwnerDecreto);

	public TImpugnazione getTImpugnazione(long idAttoImpugnazione, int idUffOwn);

	public List<TRelAttoMisPers> getTRelAttoMisPers(long idProcedimento, long idAttoDecreto, int idUffOwn);

	public List<TRelBeneAtto> getTRelBeneAtto(long idProcedimento, long idAttoDecreto, int idUffOwn);

	public TRelProcSogint getTRelProcSogint(long idProcedimento, Long codiFkIdSoggInt, int idUffOwn);

	public TRelProcBene getTRelProcBene(long idProcedimento, long codiKIdBene, int idUffOwn);

	public TAnimali getTAnimali(long codiKIdBene, int idUffOwn);

	public TCollezioni getTCollezioni(long codiKIdBene, int idUffOwn);

	public TVeicolo getTVeicolo(long codiKIdBene, int idUffOwn);

	public TDenaro getTDenaro(long codiKIdBene, int idUffOwn);

	public TContante getTContante(long codiKIdBene, int idUffOwn);

	public TAzienda getTAzienda(long codiKIdBene, int idUffOwn);

	public TBeneImmat getTBeneImmat(long codiKIdBene, int idUffOwn);

	public TBeneFin getTBeneFin(long codiKIdBene, int idUffOwn);

	public TBeneImmobile getTBeneImmobile(long codiKIdBene, int idUffOwn);

	public TPersonaFisica getTPersonaFisica(long codiKIdSoggInt, short codiFkIdVer, int codiKIdUffOwner);

	public TPersonaGiur getTPersonaGiur(long codiKIdSoggInt, short codiFkIdVer, int codiKIdUffOwner);

	public TSoggetto getTSoggettoById(long codiKIdSoggInt, int codiKIdUffOwner);
  
	public long salvaIstanzaFascicoloGestione(int idUffOwn, long idProcedimento, Long idAmministratore, String codiceUtente, long statoAtto, long tipoAtto, long categoriaAtto, TAtto atto, TIstanza istanza, String nomeMetodo);

	public List<RicercaStralcioDto> getRicercaMisureIstanza( long idAtto,  long idProcedimento,  long idUfficio,String flgValido, int categoriaAtto, int stato);
	
	public List<SogIntDettaglioProcedimentoDto> getRicercaSoggettoIstanza( long idProcedimento, long idatto,  int iduff,  int descRuolo);
	
	public List<TAvvocati> getRicercaAvvIstanza(long idProcedimento,long idatto,int iduff);
     
	 public Character impostaIstanzaMisPatr( long idProcedimento, long idAtto,  long idUffOwner,  boolean checkedSoggValido,  long idSoggInt,  long idBene,String idSoggTit,String codiceUtente, int catAtto);
	    
	 public Character impostaIstanzaSoggetto( long idProcedimento, long idAtto,  long idUffOwner,  boolean checkedSoggValido,  long idSoggInt,   String codiceUtente, int catAtto);

	 public Character impostaIstanzaMisPers( long idProcedimento,  long idAtto, long idUffOwner,  boolean checkedSoggValido, long idMisuraPers,  long idSoggInt,  String codiceUtente, int catAtto);

	public TRelSoggImp getTRelSoggImpByIdImpugnazioneIdOwner(long idImpugnazione,int IdUffOwner);	
	
	public TRelSoggIst getTRelSoggIstByIdIstanzaIdOwner(long idIstanza,int IdUffOwner);
	
	public TRelSoggImp getTRelSoggImpByIdIstanzaIdOwner(long idImpugnazione,int IdUffOwner);
	 
	public List<SogIntDettaglioProcedimentoDto> getSoggettiDifensoriRicorso(long idProc, long idImpugnazione, long idUfficio,  long idAvvocato,  String cognomeAvv, String nomeAvv, String descUtente) ;
	
	public List<SogIntDettaglioProcedimentoDto> getSoggettiDifensoriIstanza( long idImpugnazione, long idUfficio,  long idAvvocato,  String cognomeAvv, String nomeAvv, String descUtente) ;

	public TNotifRicevute caricaNotificatoDaRicevuta(long codAtto, int idUfficio);
	
	public TNotifDest caricaNotificatoDaRapporto(long codAtto, int idUfficio);
	
	public long inserisciProvvedimentoFascicoloGestione(int idUffOwn, long idProcedimento, String codUtente, TAtto atto, long idStatoAtto, long idAttoOrigine, TDecreto decreto,  String nomeMetodo);
	
	public long salvaProvvedimentoFascicoloGestione(int idUffOwn, long idProcedimento, long idAtto, String codiceUtente, TAtto atto, long idStatoAtto, TProvFg provv, String nomeMetodo);

	public long inserisciOrdinanzaAntimafia( int idUffOwn,  long idProcedimento,  String codiceUtente,  int idTipoAtto, int idCatAtto,  int idStatoAtto,  String nomeMetodo, long idSoggetto);

	public List<SogIntDettaglioProcedimentoDto> getSogAntimafia(final int idUffOwn, final Long idProc,final Long idAtto);

	public long inserisciProvvedimentoRevocaAmministratori(int idUffOwn, long idProcedimento, String codUtente, TAtto atto, long idStatoAtto, TDecreto decreto,  String nomeMetodo, Long idAttoOrigine);
	
	public long salvaProvvedimentoRevocaAmministratori(int idUffOwn, long idProcedimento, long idAtto, String codiceUtente, TAtto atto, long idStatoAtto, TDecreto decreto, String nomeMetodo);	
	
	public void revocaAmministratore(int idUffOwn, long idProcedimento, long idAtto, String idsAmm, Date dataDeposito, String codiceUtente, String nomeMetodo);
	
	public long inserisciProvvedimentoNominaAmministratori(int idUffOwn, long idProcedimento, String codUtente, TAtto atto, long idStatoAtto, TDecreto decreto,  String nomeMetodo);
	
	public long salvaProvvedimentoNominaAmministratori(int idUffOwn, long idProcedimento, long idAtto, String codiceUtente, TAtto atto, long idStatoAtto, TDecreto decreto, String nomeMetodo);
	
	public List getAmministratoriRevocabili(int idUffOwn, Long idProcedimento);
	
	public List getAmministratoriRevocati(int idUffOwn, Long idProcedimento, Long idAtto);
	
	public Date getMaxDataEmissioneProposta(int idUffOwn, long idProcedimento);	
	
	/**
	 * aggiornaDecisorioIIIGrado
	 * 
	 * @param idUffOwn
	 * @param idProcedimento
	 * @param atto
	 * @param codUtente
	 * @return long
	 */
	public Long aggiornaDecisorioIIIGrado( long idUffOwn,  long idProcedimento,  TAtto atto,  String codUtente,  int tipoUff);
	
	public List<TIstanza> getricercaIstanza( int idUffOwn,  Long idProcedimento,  Long idTipoAtto,  Long idCatAtto);	

	
	/***
	 * Restituisce il codice della misura da inviare a nsc solo se la misura è inviabile
	 * altrimenti restituisce null
	 * @param idProcedimento
	 * @param idUffOwn
	 * @param codi_K_ID_ATTO
	 */
	public String getCodiceEsitoNSCPerFoglioComplementare(long idProcedimento, int idUffOwn, long idAtto, Long idMisura);

	public TDefIstGen caricaDefinitivitaIstGen(TDefIstGenId id);
	
	public void salvaDecErroreMateriale( long idAtto,  long idProcedimento,  String idCodiceUtente,  int idUfficio,  String descDecisorio,  int idDecisorio, IntestatarioDto intestario,  TRelAttoIstGen rec, Date dataRicezione,Long numeAnnoSentenza,Long numeSentenza);

	public List<TAtto> listAttiConversionePdfJob();
	
	public void aggiornaTipoDocumento(final String nomeDocumento, final long idAtto, final BigInteger idDocumento, final int uffOwner);

	@SuppressWarnings("rawtypes")
	public List existsIntervenientiNonAssociati(Long idProcedimento, int idUfficio);
	
	public boolean isAttoOrigine(long idAtto, int idUffOwn);
	
	public List<TAtto> listMonitorRenderPdfJob();
	
	public void insertIdTransazioneDocumentale(final long idAtto, final BigInteger idTransazione, final int uffOwner);

	public List<Map> ordinanzaVenditaBene(long vidUffOwn, String vcodUtente, long vidprocedimento, long vidstatobene, TBene bene, TDenaro denaro, String numeroBollettino, TAtto atto);
	
	public long inserisciAttoRelazioneAmm(int idUffOwn, String idCodiceUtente, long idProcFG, long idTipoAtto, long idCatAtto, 
			long idStatoAtto, Date dataRicezione, long idAmm, String cognomeAmm, String nomeAmm, Date dataInizio, Date dataFine, long idPDOC, 
			Blob blob, String tipoAllegato, String nomeAllegato, String numeroRiferimento);
	
	public TAttoGenerico getTAttoGenerico(long idAtto, long idUfficio);
	
	public long contaInvioMsgCassazione( long idProc,  long idAtto,  long idUffOwn);
	
	public int getUffOwnerFromIdDocumento(BigInteger idDocumento);
	
	public int getUffOwnerTestDocumentale();
	
	public String cleanBlobJobDocumentale();
	
	public KeyMessageDto controllaPresenzaRicorso(long idProc, long idUfficio, String funzione, String descUtente);
	
	public KeyMessageDto impostaGiudizioDefinitivoI(long idProc, long idUfficio, String funzione, String descUtente);
	
	public Long getIdDocumentale(long idAtto, int idUfficio);

	public List<TTpTipoFilePdoc> listaTipoFilePdoc();  //mev DOCUMENTALE 13 aprile 2018 Alfredo G.
	
	public void cancelIdTransazioneDocumentale(final long idAtto,  final int uffOwner); //mev Documentale

	public TRelSoggSpese recuperaSoggSpeseProc(long idProc, long idUfficio, long idAtto, long idSoggetto, long idVer, long idRuolo);

}