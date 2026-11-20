package it.giustizia.smp.dao;

import it.giustizia.smp.dto.KeyMessageDto;
import it.giustizia.smp.form.AmministratoreForm;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.BeneForm;
import it.giustizia.smp.form.BeneSoggettoForm;
import it.giustizia.smp.model.TAnAltriOper;
import it.giustizia.smp.model.TAnNormative;
import it.giustizia.smp.model.TAnOperFinanz;
import it.giustizia.smp.model.TAnimali;
import it.giustizia.smp.model.TAzienda;
import it.giustizia.smp.model.TBene;
import it.giustizia.smp.model.TBeneFin;
import it.giustizia.smp.model.TBeneImmat;
import it.giustizia.smp.model.TBeneImmobile;
import it.giustizia.smp.model.TCollezioni;
import it.giustizia.smp.model.TContante;
import it.giustizia.smp.model.TDenaro;
import it.giustizia.smp.model.TDepositiAmm;
import it.giustizia.smp.model.TRelAttoIstGen;
import it.giustizia.smp.model.TRelAttoIstGenId;
import it.giustizia.smp.model.TRelAttoMisPatr;
import it.giustizia.smp.model.TRelBeneAtto;
import it.giustizia.smp.model.TRelBeneBene;
import it.giustizia.smp.model.TRelProcAttoAmm;
import it.giustizia.smp.model.TRelProcBene;
import it.giustizia.smp.model.TRelProcBeneAttoAmm;
import it.giustizia.smp.model.TRelProcBeneTit;
import it.giustizia.smp.model.TTpTipoInteresse;
import it.giustizia.smp.model.TTpTipoMisura;
import it.giustizia.smp.model.TTpTipoPrevenzione;
import it.giustizia.smp.model.TVeicolo;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ProcedimentoDao
 * 
 * @author Almvaviva S.p.A.
 *         Interfaccia Bene DAO
 */
@SuppressWarnings("rawtypes")
public interface BeneDao {

	// P.Q. Mev 2020/48 Inizio
	public List<TRelProcAttoAmm> getAmministratoriCopertina(int idUffOwn, Long idProcedimento, Long idAtto, Long idAmministratore, boolean escludiAgenzia);
	public List<TRelProcBeneAttoAmm> getAmministratoriBeni(int idUffOwn, Long idProcedimento, Long idAtto, Long idAmministratore, boolean escludiAgenzia, Boolean escludiAtto);
	// P.Q. Mev 2020/48 Fine
	
	public void associaBeneAlProcedimento(final long idBene, final int idUffOwner, final String codUtente, final long idProcedimento, final long statoBene);

	public void associaTitoloDiDirittoAlBene(BeneSoggettoForm beneSoggettoForm, long idProcedimento, long idAtto, Integer idUfficioUtente, String codUtente, Boolean daProposta, String nomeMetodo);
	
	public void modificaRelBeneTit(final BeneSoggettoForm form, final long idProcedimento, 
			long idAtto, Integer idUfficioUtente, String codUtente, Boolean daProposta, 
			double numeQuotaSeq, double numeFrazioneNumSeq, double numeFrazioneDenSeq, 
			String tipoOperazione, String nomeMetodo);
	
	public KeyMessageDto controlloQuota(final BeneSoggettoForm form, final long idProcedimento, 
			long idAtto, Integer idUfficioUtente, String codUtente, Boolean daProposta,  
			String tipoOperazione, String nomeMetodo);
	
    public List beniRicercaCriteri(final long vidUffOwn, final long vidprocedimento, final long vtipobene, final long vcatbene, final long vsottocatbene, final TBene bene, final TAzienda azienda, final TBeneImmobile immobile, final TBeneImmat immateriale, final TVeicolo veicolo, final TBeneFin finanziario, final TContante contante, final TDenaro denaro, final Date dataMin, final Date dataMax);

	public void copiaTitoloDiDiritto(long idBene, long idProcedimento, Integer idUfficioUtente, String listaBeni, long idAtto, String codUtente, String nomeMetodo);

	public void eliminaSoggettoComeTitoloDiInteresse(long idPersona, long idAtto, long idProcedimento, Integer idUfficioUtente, long idBene, int idTipoInteresse);

	public boolean esistonoAnimaliIdentici(BeneForm beneForm);

	public boolean esistonoAziendeIdentiche(BeneForm beneForm);

	public boolean esistonoFinanziariIdentici(BeneForm beneForm);

	public boolean esistonoImmobiliIdentici(BeneForm beneForm);

	public boolean esistonoVeicoliIdentici(BeneForm beneForm);

	public void frazionamentoBene(Integer idUfficioUtente, String codUtente, long idProcedimento, long idBene, String listaIdBeni, long idAtto);
	
	public List<Map> getDescEnte(String codiceAbi);

	public Map<String, Object> getDettaglioBene(HashMap<String, Object> hashDettaglio);

	public Long getIdAttoByUfficioEProcedimento(Integer idUfficioUtente, long idProcedimento);
	
	public Long getIdAttoByUfficioEProcedimentoCameralini(Integer idUfficioUtente, long idProcedimento);

	public TTpTipoInteresse getInteresseById(long idTipoInteresse);

	public List<TAnOperFinanz> getOperFinanz(String codiceAbi, String codiceCab);

	public TRelBeneAtto getRelazioneBeneAttoByAttoAndBene(long idBene, long idProcedimento, Integer idUfficioUtente, long idAtto, long idSoggetto, long tipoInt);

	public TRelBeneAtto getRelazioneBeneProcedimentoById(BeneSoggettoForm beneSoggettoForm, long idProcedimento, Integer idUfficioUtente, long idAtto);

	public List<TRelBeneAtto> getRelazioniByTipoInteresse(BeneSoggettoForm beneSoggettoForm, long idProcedimento, Integer idUfficioUtente, long idAtto);
	
	public List<TRelProcBeneTit> getRelazioniProcedimentoBeneTitolare(BeneSoggettoForm beneSoggettoForm, long idProcedimento, Integer idUfficioUtente);

	public TAnimali getTAnimaliById(long idBene, int idUffOwn);

	public TAzienda getTAziendaById(long idBene, int idUffOwn);

	public TBene getTBeneById(long idBene, int idUffOwn);

	public TBeneFin getTBeneFinanziarioById(long idBene, int idUffOwn);
	
    public TContante getTContanteById(long idBene, int idUffOwn);
	
    public TDenaro getTDenaroById(long idBene, int idUffOwn);

	public TBeneImmat getTBeneImmaterialeById(long idBene, int idUffOwn);

	public TBeneImmobile getTBeneImmobileById(long idBene, int idUffOwn);

	public TCollezioni getTCollezioniById(long idBene, int idUffOwn);

	public TRelBeneAtto getTipoInteressePerSoggettoInteressato(long idProcedimento, long idAtto, int idUfficioUtente, long idBene, long idSoggettoTitolare);
	
	public TRelProcBeneTit getRelazioniProcedimentoBeneTitolare(long idProcedimento,long idBene, int idUfficioUtente, int idTipoIntesse, long idSoggetto);

	public TAnNormative getTAnNormativeById(long idNormativa);

	public TRelBeneAtto getTRelBeneAttoById(Long idBene, long idPersona, Integer idUfficioUtente, long idProcedimento, long idAtto, long idTipoInteresse);

	public TRelProcBene getTRelProcBeneById(long idProc, long idBene, int idUffOwn);

	public TTpTipoMisura getTTpTipoMisuraById(long idTipoMisura);

	public TTpTipoPrevenzione getTTpTipoPrevenzioneById(long idTipoPrevenzione);

	public TVeicolo getTVeicoloById(long idBene, int idUffOwn);
	
	public List<Map> inserisciAzienda(final long vidUffOwn, final String codUtente, final long vidprocedimento, final long vidstatobene, final TBene bene, final TAzienda azienda, final String vdescComune, final String vtipoStrada, final String vdesccategoria);
	
	public List<Map> inserisciDenaro_Contante(final long vidUffOwn, final String codUtente, final long vidprocedimento, final long vidstatobene, final TBene bene, final TDenaro denaro, final TContante contante);
	
	public List<Map> inserisciFinanziario(final long idUffOwn, final String codUtente, final long vidprocedimento, final long vidstatobene, final TBene bene, final TBeneFin finanziario);
	
	public List<Map> inserisciImmobile(final long vidUffOwn, final String codUtente, final long vidprocedimento, final long vidStatoCons, final TBene bene, final TBeneImmobile immobile, final long vidDisponibilita, final long vidVincolo, final String vdescVincolo,final long vtipoOccupanti);
	
	public List<Map> inserisciMobile(final long vidUffOwn, final String codUtente, final long vidprocedimento, final long vidstatocons, final TBene bene, final TAnimali animali, final TCollezioni collezioni, final String vdescComune, final String vtipoStrada, final String vdesccategoria, final String vdescSottoCat);
	
	public List<Map> inserisciMobileRegistrato(final long vidUffOwn, final String codUtente, final long vidprocedimento, final long vidstatobene, final TBene bene, final TVeicolo veicolo, final TBeneImmat immateriale, final String vdescComune, final String vtipoStrada, final String vdesccategoria, final String vdescSottoCat, final long vidDisponibilita);
	
	public void modificaAzienda(final long vidUffOwn, final String codUtente, final long vidprocedimento, final long vidstatobene, final TBene bene, final TAzienda azienda, final String vdescComune, final String vtipoStrada, final String vdesccategoria);
	
	public List<Map> modificaImmobile(final long vidUffOwn, final String codUtente, final long vidprocedimento, final long vidStatoCons, final TBene bene, final TBeneImmobile immobile, final long vidDisponibilita, final long vidVincolo, final String vdescVincolo,final long vtipoOccupanti);
	
	public List<Map> modificaMobileRegistrato(final long vidUffOwn, final String codUtente, final long vidprocedimento, final long vidstatobene, final TBene bene, final TVeicolo veicolo, final TBeneImmat immateriale, final String vdescComune, final String vtipoStrada, final String vdesccategoria, final String vdescSottoCat, final long vidDisponibilita);
	
	public List<Map> modificaMobile(final long vidUffOwn, final String codUtente, final long vidprocedimento, final long vidstatocons, final TBene bene, final TAnimali animali, final TCollezioni collezioni, final String vdescComune, final String vtipoStrada, final String vdesccategoria, final String vdescSottoCat);
	
	public List<Map> modificaDenaroContante(final long vidUffOwn, final String codUtente, final long vidprocedimento, final long vidstatobene, final TBene bene, final TDenaro denaro, final TContante contante);
	
	public List<Map> modificaFinanziario(final long idUffOwn, final String codUtente, final long vidprocedimento, final long vidstatobene, final TBene bene, final TBeneFin finanziario);
	
	public List<Map> venditaBene(final long vidUffOwn, final String codUtente, final long vidprocedimento, final long vidstatobene, final TBene bene, final TDenaro denaro, final String numeroBollettino);
    
    public List listaBeniAttoNoMisPatr(int idUffOwn, long idProcedimento, long idAtto, long idSoggetto, final Long idSoggettoTit, final Long idTipologia, final Long idCategoria, final Long idSottocategoria);
    
    public List listaBeniPerProcedimento(int filtro, int idUffOwn, long idProcedimento, final Long idBene, final Long idTipologia, final Long idCategoria, final Long idSottocategoria, final Long idSogInv, final Integer idCatAtto);
    
    public List listaBeniPerProcedimentoFascicoloGestione(int filtro, int idUffOwn, long idProcedimento, final Long idBene, final Long idTipologia, final Long idCategoria, final Long idSottocategoria);

    
    public List listaBeniPerProcedimentoNoAtto(int idUffOwn, long idProcedimento, long idAtto1, long idAtto2,
    		long idSoggettoDiretto, long idSoggettoTitolare, long idTipologia, long idCategoria, long idSottocategoria);
    
    public List listaBeniProcDecretoFiltro(int filtro, int idUffOwn, long idProcedimento, Long idBene, Long idTipologia, Long idCategoria, Long idSottocategoria, long vidDecreto, long vidProposta, Long idAmministratore, final String Amministratore);

    public List<TRelAttoMisPatr> listaRelAttoMisPatr(long idPersona, long idProcedimento, long idAtto, Integer idUfficioUtente, long idBene, Integer idTipoInteresse);
    
    public List listaSoggettiBeniProc(long idProcedimento, int idUffOwner, long idBene, long idAtto);
    
    public List listaSoggettiBeniProcTuttiAtti(long idProcedimento, int idUffOwner, long idBene);

    //salva l'esito per i decredi decisorio 1°/2° e sentenza cassazione
    public void salvaDecisorioBeni(TRelBeneAtto bene,String descDecisorio);
    
    public void salvaDecisorioBeniIstGen(long idAtto, long idProcedimento, String idCodiceUtente, int idUfficio, int idTipoUff, String descDecisorio, int idDecisorio, TRelAttoIstGen rec);
    
    public TRelAttoIstGen getDecisorioBeniIstGen(TRelAttoIstGenId id);
    
    //salva l'esito per il secondo grado
    //public void salvaDecisorioBeni2(TRelBeneAtto bene);

	public List<TRelProcBene> listaBeniPerProcedimento(long idProcedimento, int idUfficioUtente);

//    public List<TRelBeneAtto> getTRelBeneAtto(long idProcedimento, long idBene, Integer idUfficioUtente);
    
    public List<TRelBeneAtto> getTRelBeneAtto(long idProcedimento, long idBene, Integer idUfficioUtente, long idAtto);

    public void eliminaTRelProcBene(long idProcedimento, long idBene, Integer idUfficioUtente);

	public List<TRelAttoMisPatr> listaRelAttoMisPatr(long idPersona, long idProcedimento, Integer idUfficioUtente);

	public void eliminaBene(long idBene, Integer idUfficioUtente);

	public int countRelBeneAtto(long idSoggetto, long idProcedimento, Integer idUfficioUtente);
	
	boolean isAssociato(long idBene, int idUfficioUtente,long idProcedimento);
	
	void trasformaBene(long idProcedimento,long idBeneVecchio,long idBeneNuovo,long vidUffOwn, String codUtente,Character vidMotivo,Integer vidTipoTrasformazione,Long vidatto);
	
	void erroreMaterialeBene(long idProcedimento,long idBeneVecchio,long idBeneNuovo,long vidUffOwn, String codUtente,Character vidMotivo,Integer vidTipoTrasformazione,Long vidatto);
	
	public List <TRelProcBene> dettaglioRevocaRelProcBene(long idAttoRevoca, long idProcedimento, String descUtente, int idUffOwner, String nomeMetodo);
	
	public List<TBene> dettaglioRevocaTBene(long idAttoRevoca, long idProcedimento, String descUtente, int idUffOwner, String nomeMetodo,
			long idSoggettoDiretto, long idSoggettoTitolare, long idTipologia, long idCategoria, long idSottocategoria);
	
	public List<TBene> dettaglioOpposTBene(final long idAttoRevoca, final long idProcedimento, final String descUtente, final int idUffOwner, final String nomeMetodo,
			final long idSoggettoDiretto, final long idSoggettoTitolare, final long idTipologia, final long idCategoria, final long idSottocategoria);

	public List<TBene> dettaglioRevocaOpposTBene(final long idAttoRevoca, final long idProcedimento, final String descUtente, final int idUffOwner, final String nomeMetodo);
	
	public List<TBene> dettaglioRevocazTBene(final long idAttoRevoca, final long idProcedimento, final String descUtente, final int idUffOwner, final String nomeMetodo,
    		final long idSoggettoDiretto, final long idSoggettoTitolare, final long idTipologia, final long idCategoria, final long idSottocategoria);
	
	public List<TBene> controlloPresenzaBeniPerRevocaConfisca(final long idAttoRevoca, final long idProcedimento, final String descUtente, final int idUffOwner, final String nomeMetodo);

	public List <TRelBeneAtto> getListaRelBeneAtto(long idProcedimento, long idAtto, int idUfficioUtente, long idBene, long idSoggettoTitolare);
	
	/**
	 * controllaDecrRestBeni
	 * @param vidprocedimento
	 * @param vidUffOwn
	 * @param descUtente
	 * @return KeyMessageDto
	 */
    public KeyMessageDto controllaDecrRestBeni(final long vidprocedimento,final long vidUffOwn,final String descUtente);
    
	public List<TRelProcBene> elencoRestBeniRelProcBene( final long idProcedimento, final String descUtente, final int idUffOwner, final String nomeMetodo);    
	
	public List<TBene> elencoRestBeniTBene(final long idAtto, final long idProcedimento, final String descUtente, final int idUffOwner, final String nomeMetodo,
			final long idSoggettoDiretto, final long idSoggettoTitolare, final long idTipologia, final long idCategoria, final long idSottocategoria);

	public List getAmministratori(final int idUffOwn, final Long idProcedimento, final Long idAtto, final Long idAmministratore, boolean escludiAmministratore);

	public List getAmministratoriDaAssociare(final int idUffOwn, final Long idProcedimento, final Long idAtto, final AmministratoreForm amministratoreForm);
	
	public long inserisciAmministratore(final int idUffOwn, final AmministratoreForm form, final String codUtente, final String nomeMetodo);
	
	public List getAmministratoreById(final Long idAmministratore);
	
	public long inserisciRelAmministratoreAtto(final int idUffOwn, final AmministratoreForm amministratoreForm, final AttoForm attoForm, final String codUtente, final String nomeMetodo);
	
	public long inserisciRelAmministratoreBeneAtto(final int idUffOwn, final long idAmministratore, final AttoForm attoForm, final long idBene, final String codUtente, final String nomeMetodo);
	
	// P.Q. MEV 2020/48 Inizio
	public Long inserisciRelAmministratoreAttoTuttiParametri(int idUffOwn, long idAmministratore, long idAtto, long idProcedimento, Date dataInizioIncario, String codUtente, String nomeMetodo);	
	public Long inserisciRelAmministratoreBeneAttoTuttiParametri(int idUffOwn, long idAmministratore, long idAtto, long idProcedimento, Date dataInizioIncario, long idBene, String codUtente, String nomeMetodo);
	// P.Q. MEV 2020/48 Fine
	
	public void eliminaRelAmministratoreBeneAtto(int idUffOwn, long idAmministratore, AttoForm attoForm, long idBene);
	
	public void eliminaRelAmministratoreAtto(int idUffOwn, long idAmministratore, AttoForm attoForm);

	public boolean esisteRelAmministratoreAttoBeni(int idUfficio, long idProcedimento, Long idAtto, Long idAmministratore);
	
	public boolean esisteRelAmministratoreAtti(long idAmministratore);
	
	public boolean esistonoRelAttiAmministratore(int idUfficio, long idProcedimento, Long idAtto);
	
	public void eliminaTAmministratori(long idAmministratore); 

	public List<TBene> dettaglioProrogaSequestroTBene(long idAttoRevoca, long idProcedimento, String descUtente, int idUffOwner, String nomeMetodo);
	
	public List<BeneForm> listaBeniInAmministrazione(final int idUffOwn, final long idProcedimento, final Long idAtto, final Long idAmministratore);
	
	public void updateAmministratore(final int idUffOwn, final AmministratoreForm f, final String codUtente);
	 
	public List<TBene> dettaglioSequestroSospensioneTBene(long idAttoRevoca, long idProcedimento, String descUtente, int idUffOwner, String nomeMetodo);	 
    
    public boolean isDefinito(BeneForm bene);
    
    public TRelBeneBene getTRelBeneBenebyIdAtto(long idAtto, long idProcedimento, int idUffOwn);
    
    public List<Map> isBeneInIstanzaCamerale(long idProc, int idUffOwn, long idBene);

    public List listaBeniNominaAmministratore(int filtro, int idUffOwn, long idProcedimento, Long idBene, Long idTipologia, Long idCategoria, Long idSottocategoria, long vidDecreto, Long idAmministratore, final String Amministratore);

	public List<BeneForm> listaBeniProcedimentoFiltrata(final long idProcedimento, final long idUffOwn, final long idAtto,  
    		final long vidSoggettoDiretto, final long vidSoggettoTitolare, final long vidTipologia, final long vidCategoria, final long vidSottocategoria);

	public TAnAltriOper getTAnAltriOper(Long id);

	public TAnAltriOper getTAnAltriOperByCodFiscale(String codiceFiscale);
	
	public TAnAltriOper getTAnAltriOperByDescrizione(String descrizione);

	public void modificaBeneFinanaziarioPolizza(final Long idBene, final Integer idUffOwn, final BeneForm beneForm);
	
	boolean existsSequestroRegistrato(final long idProcedimento, final long idBene, final int idUfficioUtente);
	
	public TBene getBene(long idBene);
	
	public BigInteger getTipologiaBene(long idBene);
	
	public BigInteger getCategoriaBene(long idBene);
	
	public BigInteger getSottocategoriaBene(long idBene);
	
	public List<Long> getListaIdBeniAssociatiAdAttoEProcedimento(long idProcedimento, long idAtto);

	public List<Object[]> getAttoAmministratoreDelBene(final long idBene, final long idProcedimento, final int idUfficioUtente, final String categoriaAtto);	
	
	public TRelBeneBene getTRelBeneBeneByIdAtto(long idAtto);

	public TDepositiAmm getDepositoAmmByIdAtto(final long idAtto, final int idUffOwner, final long idProcedimento);

	public String getAmministratoreBeneAtto(long idProcedimento, long idAtto, int idUffOwner, long idBene);

	public TAnAltriOper getTAnAltriOperByDescCodFisc(String descCodFiscale);
	
	public List<TBeneImmobile> verificaImmobileGraffato(long idProcedimento, int idUffOwner);
	
	public void inserisciGraffato(long idBene1, long idBene2, int idUffOwner);
	
	public TBeneImmobile esisteGraffato(long idBene, int idUffOwner);
	
	public void eliminaGraffato(long idBene1, long idBene2, int idUffOwner);

	// MEV_10
	public List<Map> getDenaro(Long idBene, Integer idUffOwner);

}