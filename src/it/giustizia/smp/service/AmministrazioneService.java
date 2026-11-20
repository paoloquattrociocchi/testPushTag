package it.giustizia.smp.service;

import it.giustizia.smp.form.CategoriaCatastaleForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.ComuneForm;
import it.giustizia.smp.form.CondizioneGiuridicaForm;
import it.giustizia.smp.form.ConservatoriaForm;
import it.giustizia.smp.form.IstitutoDetenzioneForm;
import it.giustizia.smp.form.MagistratoForm;
import it.giustizia.smp.form.ProfessioneForm;
import it.giustizia.smp.form.ProvinciaForm;
import it.giustizia.smp.form.SettoreAttivitaForm;
import it.giustizia.smp.form.SpeseProcessualiForm;
import it.giustizia.smp.form.StatoForm;
import it.giustizia.smp.form.UfficiForm;
import it.giustizia.smp.model.TProcedimento;
import it.giustizia.smp.model.TRelMagProc;
import it.giustizia.smp.model.TTpCatCatasto;
import it.giustizia.smp.model.TTpSettoreAtt;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface AmministrazioneService {
	
	 public List<MagistratoForm> listaMagistrati(String cognome, String nome ,Date dataNascita, Integer matricola);
	 
	 public void inserisciMagistrato(String cognome, String nome ,Date dataNascita, Integer matricola, String descUtente);
	 
	 public List<MagistratoForm> listaMagistratiNonAssociati(int idUffOwn, String cognome, String nome, Integer matricola);
	 
	 public void associaMagistrato(long codiKIdMagistrato, int idUffOwn, String descUtente);
	 
	 public void modificaMagistrato(MagistratoForm magistratoForm);
	 
//	 public void eliminaMagistrato(long codiKIdMagistrato);
	 
	 public List<ComboForm> listaMagistratiSostituzione(String tipoMagistrato, int idUffOwn, int idTipoUfficio, Date dataFineAssegnazione);
	 
	 public List<ComboForm> listaMagistratiSubentro(int idUffOwn);
	 
	 public List<StatoForm> listaStati(String descStato, String descSiglaStato, String descIstatStato);
	 
	 public StatoForm getStato(int codice);
	 
	 public MagistratoForm getMagistrato(long id);
	 
	 public void modificaStato(StatoForm statoForm);
	 
	 public Integer inserisciStato(String descIstatStato, String descSiglaStato, String descStato, Date dataFineValidita, String descUtente);
	 
	 public Integer inserisciProvincia(String codIstatProvincia, String descSiglaProvincia, String descProvincia, Date dataFineValidita, String descUtente);
	 
	 public List<TRelMagProc> listaProcedimentiSostituzione(String tipoMagistrato, long idMagistratoDaSostituire, int idUffOwn, int idTipoUfficio, Date dataFineAssegnazione, Short annoDa, Long numDa, Short annoA, Long numA);
	 
	 public List<TRelMagProc> listaProcedimentiDettaglioMagistrato(String tipoMagistrato, long idMagistratoDaSostituire, int idUffOwn, int idTipoUfficio, Date dataFineAssegnazione);
		 
	 public TProcedimento sostituisciMagistrato(MagistratoForm magistratoForm, int idUffOwn, long idProcedimento, String descUtente);
	 
	 public List<ProvinciaForm> listaProvince(String descProvincia, String descSiglaProvincia);
	 
	 public ProvinciaForm getProvincia(String codice);
	 
	 public void modificaProvincia(ProvinciaForm provinciaForm);
	 
	 public List<ComuneForm> listaComuni(String descComune, String descCapComune, String descIstatComune, String descCodBelfiore, String descCodCatasto);
	 
	 public ComuneForm getComune(int codiceComune);
	 
	 public Integer inserisciComune(String codiFkIdIstatProvincia, String descIstatComune, String descComune, String descCapComune, String descBelfiore, String descCatasto, Date dataFineValidita, String descUtente);
	 
	 public void modificaComune(ComuneForm comuneForm);
	 
	 public List<UfficiForm> listaUffici(int idTipoUfficio, int idComune);
	 
	 public UfficiForm getUfficio(int codiceUfficio);
	 
	 public Integer inserisciUfficio(int idTipoUfficio, int idComune, String descIndirizzoSede, int idDistretto, String flagTribMp, int idUfficioTribunale, String flagDda, Date dataFineValidita, String descUtente);

	 public Integer inserisciUfficio(UfficiForm ufficiForm, String descUtente);

	 public List<CategoriaCatastaleForm> listaCategorieCatastali(String descCategoriaCat, String descBreveCategoriaCat);
	 
	 public List<ProfessioneForm> listaProfessioni(String descProfessione);
	 
	 public List<IstitutoDetenzioneForm> listaIstitutiDetenzione(int idTipoIstituto, int idComuneIstituto);
	 
	 public List<ConservatoriaForm> listaConservatorie(String descConserv, String descCodiConserv);
	 
	 public List<SettoreAttivitaForm> listaSettoreAttivita(String descSettoreAtt, String descSezioneAteco);
	 
	 public List<CondizioneGiuridicaForm> listaCondizioneGiuridica(String descCondGiur);
	 
	 public CategoriaCatastaleForm getCategoriaCatastale(int codiceCatCatastale);
	 
	 public ProfessioneForm getProfessione(int codiceProfessione);
	 
	 public IstitutoDetenzioneForm getIstitutoDetenzione(int codiceIstDetenzione);
	 
	 public ConservatoriaForm getConservatoria(short codiceConservatoria);
	 
	 public SettoreAttivitaForm getSettoreAttivita(int codiceSettAttivita);
	 
	 public CondizioneGiuridicaForm getCondGiuridica(int codiceCondGiuridica);
	 
	 public Integer inserisciCategoriaCatastale(String descCategoriaCat, String descBreveCategoriaCat, Date dataFineValidita, String descUtente);
	 
	 public Integer inserisciProfessione(String descProfessione, Date dataFineValidita, String descUtente);
	 
	 public Integer inserisciIstitutoDetenzione(int idTipoIstituto, int idComuneIstituto, String descIndirizzoIstituto, String descUffGiudiz, Date dataFineValidita, String descUtente);
	 
	 public Integer inserisciConservatoria(String descConserv, String descCodiConserv, Date dataFineValidita, String descUtente);
	 
	 public Integer inserisciSettoreAttivita(String descSettoreAtt, String descSezioneAteco, Date dataFineValidita, String descUtente);
	 
	 public Integer inserisciCondGiuridica(String descCondGiur, Date dataFineValidita, String descUtente);

	 public void modificaCatCatastale(CategoriaCatastaleForm categoriaCatastaleForm);
	 
	 public void modificaCondGiuridica(CondizioneGiuridicaForm condizioneGiuridicaForm);
	 
	 public void modificaConservatoria(ConservatoriaForm conservatoriaForm);
	 
	 public void modificaIstitutoDet(IstitutoDetenzioneForm istitutoDetenzioneForm);
	 
	 public void modificaProfessione(ProfessioneForm professioneForm);
	 
	 public void modificaSettoreAttivita(SettoreAttivitaForm settoreAttivitaForm);
	 
	 public void modificaUfficio(UfficiForm ufficiForm);
	 
	 public TTpSettoreAtt getTpSettoreAttivita(int codiceSettAttivita);
	 
	 public TTpCatCatasto getTpCategoriaCatastale(int codiceCatCatastale);
	 
	 public TTpSettoreAtt getTpSettoreAttivitaByDesc(String descSettoreAttivita);

	public List<SpeseProcessualiForm> listaSpeseProcessuali(BigDecimal importoSpese);

	public void inserisciSpeseProcessuali(BigDecimal importoSpese, Date dataInizioValidita, String descImportoSpese,
			String descUtente);

	public void modificaSpeseProcessuali(int idSpeseProcessuali, BigDecimal importoSpese, Date dataInizioValidita, Date dataFineValidita, String descImportoSpese,
			String descUtente);

	public SpeseProcessualiForm getSpeseProcessuali(int codiKIdSpeseProcessuali); 

}


