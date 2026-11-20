package it.giustizia.smp.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import it.giustizia.smp.form.CategoriaCatastaleForm;
import it.giustizia.smp.form.ComuneForm;
import it.giustizia.smp.form.CondizioneGiuridicaForm;
import it.giustizia.smp.form.ConservatoriaForm;
import it.giustizia.smp.form.IstitutoDetenzioneForm;
import it.giustizia.smp.form.ProfessioneForm;
import it.giustizia.smp.form.ProvinciaForm;
import it.giustizia.smp.form.SettoreAttivitaForm;
import it.giustizia.smp.form.StatoForm;
import it.giustizia.smp.form.UfficiForm;
import it.giustizia.smp.model.TAnComuni;
import it.giustizia.smp.model.TAnConservatorie;
import it.giustizia.smp.model.TAnIstitutoDet;
import it.giustizia.smp.model.TAnMagistrati;
import it.giustizia.smp.model.TAnProvincia;
import it.giustizia.smp.model.TAnStato;
import it.giustizia.smp.model.TAnUfficio;
import it.giustizia.smp.model.TRelMagProc;
import it.giustizia.smp.model.TTpCatCatasto;
import it.giustizia.smp.model.TTpCondGiur;
import it.giustizia.smp.model.TTpProfessione;
import it.giustizia.smp.model.TTpSettoreAtt;
import it.giustizia.smp.model.TTpSpeseProcessuali;

public interface AmministrazioneDao {

	public List<TAnMagistrati> listaMagistrati(String cognome, String nome, Date dataNascita, Integer matricola);
	
	public void inserisciMagistrato(String cognome, String nome, Date dataNascita, Integer matricola, String descUtente);
	
	public List listaMagistratiNonAssociati(int idUffOwn, String cognome, String nome, Integer matricola);
	
	public void associaMagistrato(long codiKIdMagistrato, int idUffOwn, String descUtente);
	
	public void modificaMagistrato(TAnMagistrati magistratoModel);
	
//	public void eliminaMagistrato(long codiKIdMagistrato);
	
	public TAnMagistrati getMagistrato(long codice);
	
	public List<TAnMagistrati> listaMagistratiSostituzione(String tipoMagistrato, int idUffOwn, int idTipoUfficio, Date dataFineAssegnazione);	
	
	public List listaMagistratiSubentro(int idUffOwn);
	
	public List<TAnStato> listaStati(String descStato, String descSiglaStato, String descIstatStato);
	
	public TAnStato getStato(int codice);
	
	public void modificaStato(StatoForm statoForm);
	
	public Integer inserisciStato(String descIstatStato, String descSiglaStato, String descStato, Date dataFineValidita, String descUtente);
	
	public List<TRelMagProc> listaProcedimentiSostituzione(String tipoMagistrato, long idMagistratoSostituito, int idUffOwn, int idTipoUfficio, Date dataFineAssegnazione, Short annoDa, Long numDa, Short annoA, Long numA);
	
	public List<TRelMagProc> controllaAssegnazioneMagistratoProcedimento(long idMagistratoSubentrante, int idUffOwn, long idProcedimento, Date dataFineAssegnazione);
	
	public void aggiornaSostituzioneMagistrato(String tipoMagistrato, long idMagistratoSostituito, long idMagistratoSubentrante, String motivo, int idUffOwn, long idProcedimento, Date dataFineAssegnazione, String descUtente);
	
	public List<TAnProvincia> listaProvince(String descProvincia, String descSiglaProvincia);

	public Integer inserisciProvincia (String codIstatProvincia, String descSiglaProvincia, String descProvincia, Date dataFineValidita, String descUtente);
	
	public TAnProvincia getProvincia(String codIstatProvincia);
	
	public void modificaProvincia(ProvinciaForm provinciaForm);
	
	public List<TAnComuni> listaComuni(String descComune, String descCapComune, String descIstatComune, String descCodBelfiore, String descCodCatasto);
	
	public TAnComuni getComune(int codiceComune);
	
	public Integer inserisciComune(String codiFkIdIstatProvincia, String descIstatComune, String descComune, String descCapComune, String descBelfiore, String descCatasto, Date dataFineValidita, String descUtente);
	
	public void modificaComune(ComuneForm comuneForm);
	
	public List<TAnUfficio> listaUffici(int idTipoUfficio, int idComune);

	public TAnUfficio getUfficio(int codiceUfficio);

	public Integer inserisciUfficio(int idTipoUfficio, int idComune, String descTipoUfficio, String descSedeUfficio, String descIndirizzoSede, int idDistretto, String descDistretto, Character flagTribMp, int idUfficioTribunale, Character flagDda, Date dataFineValidita, String descUtente);

	public Integer inserisciUfficio(UfficiForm ufficiForm, int idTipoUfficio, int idComune, String descTipoUfficio, String descSedeUfficio, String descUtente);
	
	 public List<TTpCatCatasto> listaCategorieCatastali(String descCategoriaCat, String descBreveCategoriaCat);
	 
	 public List<TTpProfessione> listaProfessioni(String descProfessione);
	 
	 public List<TAnIstitutoDet> listaIstitutiDetenzione(int idTipoIstituto, int idComuneIstituto);
	 
	 public List<TAnConservatorie> listaConservatorie(String descConserv, String descCodiConserv);
	 
	 public List<TTpSettoreAtt> listaSettoreAttivita(String descSettoreAtt, String descSezioneAteco);
	 
	 public List<TTpCondGiur> listaCondizioneGiuridica(String descCondGiur);
	 
	 public TTpCatCatasto getCategoriaCatastale(int codiceCatCatastale);
	 
	 public TTpProfessione getProfessione(int codiceProfessione);
	 
	 public TAnIstitutoDet getIstitutoDetenzione(int codiceIstDetenzione);
	 
	 public TAnConservatorie getConservatoria(short codiceConservatoria);
	 
	 public TTpSettoreAtt getSettoreAttivita(int codiceSettAttivita);
	 
	 public TTpCondGiur getCondGiuridica(int codiceCondGiuridica);

	public Integer inserisciCategoriaCatastale(String descCategoriaCat,	String descBreveCategoriaCat, Date dataFineValidita, String descUtente);

	public Integer inserisciProfessione(String descProfessione, Date dataFineValidita, String descUtente);

	public Integer inserisciIstitutoDetenzione(int idTipoIstituto, String descTipoIstituto, int idComuneIstituto, String descComuneIstituto, String descIndirizzoIstituto, String descUffGiudiz, Date dataFineValidita, String descUtente);

	public Integer inserisciConservatoria(String descConserv, String descCodiConserv, Date dataFineValidita, String descUtente);

	public Integer inserisciSettoreAttivita(String descSettoreAtt,	String descSezioneAteco, Date dataFineValidita, String descUtente);

	public Integer inserisciCondGiuridica(String descCondGiur, Date dataFineValidita, String descUtente);

	public void modificaCatCatastale(CategoriaCatastaleForm categoriaCatastaleForm);

	public void modificaCondGiuridica(CondizioneGiuridicaForm condizioneGiuridicaForm);

	public void modificaConservatoria(ConservatoriaForm conservatoriaForm);

	public void modificaIstitutoDet(IstitutoDetenzioneForm istitutoDetenzioneForm);

	public void modificaProfessione(ProfessioneForm professioneForm);

	public void modificaSettoreAttivita(SettoreAttivitaForm settoreAttivitaForm);

	public void modificaUfficio(UfficiForm ufficiForm);

	public TTpSettoreAtt getSettoreAttivitaByDesc(String descSettoreAttivita);

	public List<TTpSpeseProcessuali> listaImportoSpese(BigDecimal importoSpese);

	public void inserisciSpeseProcessuali(BigDecimal importoSpese, Date dataInizioValidita, String descImportoSpese,
			String descUtente);

	public void modificaSpeseProcessuali(int idSpeseProcessuali, BigDecimal importoSpese, Date dataInizioValidita, Date dataFineValidita, String descImportoSpese,
			String descUtente);

	public TTpSpeseProcessuali getSpeseProcessuali(int codiKIdSpeseProcessuali); 
	
	public boolean existImportoSpese(BigDecimal importoSpese, Date dataInizioValidita);
	
}
