package it.giustizia.smp.service;

import java.util.Date;
import java.util.List;

import it.giustizia.smp.dto.KeyMessageDto;
import it.giustizia.smp.dto.SchedaAttiAmmDto;
import it.giustizia.smp.form.AmministratoreForm;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.BeneForm;
import it.giustizia.smp.form.BeneSoggettoForm;
import it.giustizia.smp.model.TAnAltriOper;
import it.giustizia.smp.model.TBene;
import it.giustizia.smp.model.TDepositiAmm;
import it.giustizia.smp.model.TRelAttoIstGen;
import it.giustizia.smp.model.TRelAttoIstGenId;
import it.giustizia.smp.model.TRelBeneAtto;
import it.giustizia.smp.model.TRelBeneBene;
import it.giustizia.smp.model.TRelProcAttoAmm;
import it.giustizia.smp.model.TRelProcBene;
import it.giustizia.smp.model.TRelProcBeneAttoAmm;
import it.giustizia.smp.model.TRelProcBeneTit;
import it.giustizia.smp.model.TTpTipoInteresse;
import it.giustizia.smp.model.TTpTipoMisura;


/**
 * BeneService
 * 
 * @author Almaviva S.p.A.
 *         Interfaccia Bene Service
 */
public interface BeneService {
    
	// P.Q. Mev 2020/48 Inizio
	public List<TRelProcAttoAmm> getAmministratoriCopertina(int idUffOwn, Long idProcedimento, Long idAtto, Long idAmministratore, boolean escludiAgenzia);
	public List<TRelProcBeneAttoAmm> getAmministratoriBeni(int idUffOwn, Long idProcedimento, Long idAtto, Long idAmministratore, boolean escludiAgenzia, Boolean escludiAtto);
	// P.Q. Mev 2020/48 Inizio
	
    public void associaBeneAlProcedimento(int idUffOwner, String codUtente, long idProcedimento, BeneForm beneForm);
    
	public KeyMessageDto associaTitoloDiDirittoAlBene(String tipoFasciciolo,BeneSoggettoForm beneSoggettoForm, long idProcedimento, Integer idUfficioUtente,long idAtto, String codUtente, 
    		Boolean daProposta, String tipoOperazione, String nomeMetodo,String catAtto);
    
    public void modificaRelBeneTit(final BeneSoggettoForm form, final long idProcedimento, 
			long idAtto, Integer idUfficioUtente, String codUtente, Boolean daProposta, double numeQuotaSeq, 
			double numeFrazioneNumSeq, double numeFrazioneDenSeq, String tipoOperazione, String nomeMetodo);
    /**
     * beneById
     * 
     * @param idUffOwn
     * @param idBene
     * @return BeneForm
     */
    public BeneForm beneById(int idUffOwn, long idBene,long idProcedimento);

    /**
     * beniProcCriteri
     * 
     * @param idUffOwn
     * @param idprocedimento
     * @param bene
     * @return List<BeneForm>
     */
    public List<BeneForm> beniRicercaCriteri(long idUffOwn, long idprocedimento, BeneForm beneForm);
    
    public BeneForm caricaInfoOperFinanz(String codiceAbi, String codiceCab);
    
    public boolean controllaPresenzaSoggettoInMisuraPatrimoniale(long idPersona, long idProcedimento, Integer idUfficioUtente, long idBene, Integer idTipoInteresse);
    
    /**
     * copiaTitoloDiDiritto
     * 
     * @param beneForm
     * @param idProcedimento
     * @param idUfficioUtente
     * @param idAtto
     * @param idCodiceUtente
     */
    public void copiaTitoloDiDiritto(BeneForm beneForm, long idProcedimento, Integer idUfficioUtente,long idAtto,String codUtente, String nomeMetodo);
    
    public void eliminaSoggettoComeTitoloDiInteresse(long idPersona, long idProcedimento, Integer idUfficioUtente, long idBene, int idTipoInteresse,long idAtto,String codUtente);
    
    public boolean esistonoIdentici(BeneForm beneForm);
    
    /**
     * frazionamentoBene
     * 
     * @param beneForm
     * @param idProcedimento
     * @param idUfficioUtente
     * @param idAtto
     * @param idCodiceUtente
     */
    public void frazionamentoBene(BeneForm beneForm, long idProcedimento, Integer idUfficioUtente,long idAtto,String codUtente);
    
    public BeneSoggettoForm getBeneSoggettoFormConInformazioniTitoliDiDiritto(BeneSoggettoForm beneSoggettoForm, Long idBene, long idPersona, Integer idUfficioUtente, long idProcedimento, long idAtto, long idTipoInteresse);
    
    public long getIdAttoByUfficioEProcedimento(Integer idUfficioUtente, long idProcedimento);
    
    public long getIdAttoByUfficioEProcedimentoCameralini(Integer idUfficioUtente, long idProcedimento);

    public TRelBeneAtto getRelazioneBeneProcedimentoById(BeneSoggettoForm beneSoggettoForm, long idProcedimento, Integer idUfficioUtente, long idAtto);

    public List<TRelBeneAtto> getRelazioniByTipoInteresse(BeneSoggettoForm beneSoggettoForm, long idProcedimento, Integer idUfficioUtente, long idAtto);
    
    public List<TRelProcBeneTit> getRelazioniProcedimentoBeneTitolare(BeneSoggettoForm beneSoggettoForm, long idProcedimento, Integer idUfficioUtente);
    
    public TRelProcBeneTit getRelazioniProcedimentoBeneTitolare(long idProcedimento,long idBene, int idUfficioUtente, int idTipoIntesse, long idSoggetto);

    public int getTipoInteressePerSoggettoInteressato(long idProcedimento, long idAtto, int idUfficioUtente, long idBene, long idSoggettoTitolare);

    /**
     * inserisciBene
     * 
     * @param idUffOwn
     * @param codUtente
     * @param idprocedimento
     * @param beneForm
     */
    public long inserisciBene(long idUffOwn, String codUtente, long idprocedimento, BeneForm beneForm);
    
    /**
     * Modifica bene
     * @param idUffOwn
     * @param codUtente
     * @param idprocedimento
     * @param beneForm
     * @return
     */
    public void modificaBene(long idUffOwn, String codUtente, long idprocedimento, BeneForm beneForm);

//	public void modificaBenePolizza(long idUffOwn, String codUtente, long idprocedimento, BeneForm beneForm);

    /**
     * Vendita di un Bene
     * @param idUffOwn
     * @param codUtente
     * @param idprocedimento
     * @param beneForm
     */
    public long venditaBene(long idUffOwn, String codUtente, long idprocedimento, BeneForm beneForm);

    /**
     * listaBeniPerProcedimento
     * 
     * @param idUffOwn
     * @param idProcedimento
     * @param beneForm
     * @return
     */
    public List<BeneForm> listaBeniPerProcedimento(int filtro, int idUffOwn, long idProcedimento, BeneForm beneForm);
     
    public List<BeneForm> listaBeniPerProcedimento(int filtro, int idUffOwn, long idProcedimento, BeneForm beneForm, Long idSogInv, Integer idCatAtto);
    
    public List<BeneForm> listaBeniPerProcedimentoFascicoloGestione(int filtro, int idUffOwn, long idProcedimento, BeneForm beneForm);
    /**
     * listaBeniPerProcedimentoNoAtto
     * 
     * @param idUffOwn
     * @param idProcedimento
     * @param idAtto1
     * @param idAtto2
     * @param idSoggettoDiretto
     * @param idSoggettoTitolare
     * @param idTipologia
     * @param idCategoria
     * @param idSottocategoria
     * @return List<BeneForm>
     */
    public List<BeneForm>  listaBeniPerProcedimentoNoAtto(int idUffOwn, long idProcedimento, long idAtto1,long idAtto2,
    		long idSoggettoDiretto, long idSoggettoTitolare, long idTipologia, long idCategoria, long idSottocategoria);

    /**
     * listaBeniProcDecretoFiltro
     * 
     * @param idProcedimento
     * @param idUffOwn
     * @param beneForm
     * @param vidDecreto
     * @param vidProposta
     * @return List
     */
    @SuppressWarnings("rawtypes")
    public List listaBeniProcDecretoFiltro(int filtro, int idUffOwn, long idProcedimento, BeneForm beneForm, long vidDecreto, long vidProposta, Long idAmministratore, String Amministratore);

    /**
     * listaSoggettiBeniProc
     * 
     * @param idProcedimento
     * @param idUffOwner
     * @param idBene
     * @param idAtto
     * @return List<BeneSoggettoForm>
     */
    public List<BeneSoggettoForm> listaSoggettiBeniProc(long idProcedimento, int idUffOwner, long idBene, long idAtto);
    
    public List<BeneSoggettoForm> listaSoggettiBeniProcSenzaIdAtto(long idProcedimento, int idUffOwner, long idBene);
    
    public List<BeneSoggettoForm> listaSoggettiBeniProcTuttiAtti(long idProcedimento, int idUffOwner, long idBene);
    
    public void salvaDecisorioBeni(BeneSoggettoForm beneSoggettoForm, List<BeneForm> beniSelezionati, long idAtto, long idProcedimento, String codUtente, long idUfficio, long idTipoUff,String descDecisorio);

    public void salvaDecisorioBeniIstGen(long idAtto, long idProcedimento, String idCodiceUtente, int idUfficio, int idTipoUff, String descDecisorio, int idDecisorio, TRelAttoIstGen rec);
    
    public TRelAttoIstGen getDecisorioBeniIstGen(TRelAttoIstGenId id);
    
    public String getDescEnte(String codiceAbi);

//    public List<TRelBeneAtto> getTRelBeneAtto(long idProcedimento, long idBene, Integer idUfficioUtente);
    
    public List<TRelBeneAtto> getTRelBeneAtto(long idProcedimento, long idBene, Integer idUfficioUtente, long idAtto);

    public void eliminaRelProcBene(long idProcedimento, long idBene, Integer idUfficioUtente);

	public void eliminaBene(long idBene, Integer idUfficioUtente);
	
	/**
	 * Restituisce true se il bene è collegato a qualche altro procedimento, false altrimenti
	 * @param idBene
	 * @param idUfficioUtente
	 * @param idProceimento
	 * @return true se il bene è collegato a qualche altro procedimento, false altrimenti
	 */
	boolean isAssociato(long idBene, int idUfficioUtente,long idProcedimento);
	
	/**
	 * Trasforma un bene
	 * @param idUffOwn
	 * @param codUtente
	 * @param idprocedimento
	 * @param idBeneVecchio
	 * @param idAtto
	 * @param beneForm
	 */
	public long trasformaBene(long idUffOwn, String codUtente, long idprocedimento,long idBeneVecchio,long idBene,Long idAtto,BeneForm beneForm);
	
	/**
	 * Inserisce un bene nuovo e lo inserisce nell'atto di correzione errore materiale
	 * @param idUffOwn
	 * @param codUtente
	 * @param idprocedimento
	 * @param idBeneVecchio
	 * @param idAtto
	 * @param beneForm
	 * @return
	 */
	public long insersciBeneInCorrezioneErroreMateriale(long idUffOwn, String codUtente, long idprocedimento,long idBeneVecchio,Long idAtto,BeneForm beneForm,String idTipoFasc,String abilFascGest);
	
	
	public TTpTipoMisura getTTpTipoMisuraById(long idTipoMisura);
	
	public TTpTipoInteresse getInteresseById(int idTipoInteresse);
	
	public List<TRelProcBene> dettaglioRevocaRelProcBene(long idAttoRevoca, long idProcedimento, String descUtente, int idUffOwner, String nomeMetodo);
	
	public List<TBene> dettaglioRevocaTBene(long idAttoRevoca, long idProcedimento, String descUtente, int idUffOwner, String nomeMetodo,
			long idSoggettoDiretto, long idSoggettoTitolare, long idTipologia, long idCategoria, long idSottocategoria);
			
	public List<BeneForm> listaBeniPerProcedimentoNoAttoPerRevoca(long idAtto, long idProcedimento, String descUtente, int idUffOwner, String nomeMetodo,
			long idSoggettoDiretto, long idSoggettoTitolare, long idTipologia, long idCategoria, long idSottocategoria);
	
	public List<BeneForm> listaBeniPerProcedimentoNoAttoPerOpposizione(long idAtto, long idProcedimento, String descUtente, int idUffOwner, String nomeMetodo,
			long idSoggettoDiretto, long idSoggettoTitolare, long idTipologia, long idCategoria, long idSottocategoria);
	
	public List<BeneForm> listaBeniPerProcedimentoNoAttoPerRevocaOpposizione(long idAtto, long idProcedimento, String descUtente, int idUffOwner, String nomeMetodo);
	
	public List<BeneForm> listaBeniPerProcedimentoNoAttoPerRevocazione(long idAtto, long idProcedimento, String descUtente, int idUffOwner, String nomeMetodo,
    																   long idSoggettoDiretto, long idSoggettoTitolare, long idTipologia, long idCategoria, long idSottocategoria);
	
	public List<BeneForm> controlloPresenzaBeniPerRevocaConfisca(long idAtto, long idProcedimento, String descUtente, int idUffOwner, String nomeMetodo);
	
	public List <TRelBeneAtto> getListaRelBeneAtto(long idProcedimento, long idAtto, int idUfficioUtente, long idBene, long idSoggettoTitolare);

    public KeyMessageDto controllaDecrRestBeni(long vidprocedimento,long vidUffOwn,String descUtente);
    
	public List<BeneForm> listaBeniPerProcedimentoNoAttoPerDecRestBeni(long idAtto, long idProcedimento, String descUtente, int idUffOwner, String nomeMetodo,
			long idSoggettoDiretto, long idSoggettoTitolare, long idTipologia, long idCategoria, long idSottocategoria);

	public List<AmministratoreForm> getAmministratori(int idUffOwn, Long idProcedimento, Long idAtto, Long idAmministratore, boolean escludiAgenzia);
	
	public List<AmministratoreForm> getAmministratoriDaAssociare(int idUffOwn, Long idProcedimento, Long idAtto, AmministratoreForm amministratoreForm);
	
	public Long inserisciAmministratore(int idUffOwn, AmministratoreForm amministratoreForm, String codUtente, String nomeMetodo);
	
	public List<AmministratoreForm> getAmministratoreById(Long idAmministratore);
	
	public Long inserisciRelAmministratoreAtto(int idUffOwn, AmministratoreForm amministratoreForm, AttoForm attoForm, String codUtente, String nomeMetodo);
	
	public Long inserisciRelAmministratoreBeneAtto(int idUffOwn, long idAmministratore, AttoForm attoForm, long idBene, String codUtente, String nomeMetodo);
	
	// P.Q. MEV 2020/48 Inizio
	public Long inserisciRelAmministratoreAttoTuttiParametri(int idUffOwn, long idAmministratore, long idAtto, long idProcedimento, Date dataInizioIncario, String codUtente, String nomeMetodo);
	public Long inserisciRelAmministratoreBeneAttoTuttiParametri(int idUffOwn, long idAmministratore, long idAtto, long idProcedimento, Date dataInizioIncario, long idBene, String codUtente, String nomeMetodo);
	// P.Q. MEV 2020/48 Fine
	
	public void eliminaRelAmministratoreBeneAtto(int idUffOwn, long idAmministratore, AttoForm attoForm, long idBene);
	
	public void eliminaRelAmministratoreBeneAtto(int idUffOwn, long idAmministratore, AttoForm attoForm);
	
	public void eliminaRelAmministratoreAtto(int idUffOwn, long idAmministratore, AttoForm attoForm);

	public List<BeneForm> listaBeniPerProcedimentoNoAttoPerProrogaSequestro(long idAtto, long idProcedimento, String descUtente, int idUffOwner, String nomeMetodo);
	
	public List<BeneForm> listaBeniInAmministrazione(int idUffOwn, long idProcedimento, Long idAtto, Long idAmministratore);
	
	public List<BeneForm> listaBeniPerProcedimentoNoAttoPerSequestroSospensioneTemporanea(long idAtto, long idProcedimento, String descUtente, int idUffOwner, String nomeMetodo);	
    
	public boolean isDefinito(BeneForm bene);
	
	public List<BeneForm> listaBeneCorrezioneErroreMateriale(long idAtto, long idProcedimento, String descUtente, int idUffOwner, String nomeMetodo);
	
	public String isBeneInIstanzaCamerale(long idProc, int idUffOwn, long idBene);

	@SuppressWarnings("rawtypes")
	public List listaBeniNominaAmministratore(int filtro, int idUffOwn, long idProcedimento, BeneForm beneForm, long vidDecreto, Long idAmministratore, String Amministratore);

	public List<BeneForm> listaBeniProcedimentoFiltrata(long idProcedimento, long idUffOwn, long idAtto, 
			long idSoggettoDiretto, long idSoggettoTitolare, long idTipologia, long idCategoria, long idSottocategoria);

	public TAnAltriOper getTAnAltriOper(Long id);

	public TAnAltriOper getTAnAltriOperByCodFiscale(String codiceFiscale);

	boolean existsSequestroRegistrato(final long idProcedimento, final long idBene, final int idUfficioUtente);
		
	public List<Long> getListaIdBeniAssociatiAdAttoEProcedimento(long idProcedimento, long idAtto);

	public List<SchedaAttiAmmDto> getAttoAmministratoreDelBene(long idBene, long idProcedimento, int idUfficioUtente,String categoriaAtto);
	
	public TRelBeneBene getTRelBeneBeneByIdAtto(long idAtto);

	public TDepositiAmm getDepositoAmmByIdAtto(long idAtto, int idUffOwner, long idProcedimento);

	public String getAmministratoreBeneAtto(long idProcedimento, long idAtto, int idUffOwner, long idBene);
	
	public List<BeneForm> verificaImmobileGraffato(long idBene, long idProcedimento, int idUffOwner);
	
	public void inserisciGraffato(long idBene1, long idBene2, int idUffOwner);
	
	public BeneForm esisteGraffato(long idBene, int idUffOwner);
	
	public void eliminaGraffato(long idBene1, long idBene2, int idUffOwner);

	// MEV_10
	public String getDenaro(Long idBene, Integer idUffOwner);
}
