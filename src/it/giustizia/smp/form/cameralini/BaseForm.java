package it.giustizia.smp.form.cameralini;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.ProcedimentoForm;

/**
 * @author Almaviva S.p.A.
 *         classe per la gestione delle misure di prevenzione: personali e patrimoniali
 */
public class BaseForm {
	
	// campi relativi alla jsp ricerca
	private ComboForm						ufficio;
	private String							ufficioSelezionato;	
	private String							ufficioSelezionatoDesc; // passa la descrizione dell'ufficio selezionato
	
	private String							funzioneCameralini; 	// parametrizza la funzione richiamata dal menu 
	private String							tipologiaPrevenzione;   // descrizione della tipologia di prevenzione
	private String							tipologiaIstanza;   	// descrizione della tipologia di istanza
	
	private String							trasmissioneDesc;		// descrizione della tipologia di trasmissione
	
	ProcedimentoForm  procedPadre;
	ProcedimentoForm  procedCameralini;
	
	private String nomeRichiedente;
	private String cognomeRichiedente;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataSottoscrizione;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataDeposito;
	private ComboForm						trasmissione;

	public ComboForm getUfficio() {
		return ufficio;
	}
	public void setUfficio(ComboForm ufficio) {
		this.ufficio = ufficio;
	}
	public String getUfficioSelezionato() {
		return ufficioSelezionato;
	}
	public void setUfficioSelezionato(String ufficioSelezionato) {
		this.ufficioSelezionato = ufficioSelezionato;
	}
	public String getUfficioSelezionatoDesc() {
		return ufficioSelezionatoDesc;
	}
	public void setUfficioSelezionatoDesc(String ufficioSelezionatoDesc) {
		this.ufficioSelezionatoDesc = ufficioSelezionatoDesc;
	}
	public String getFunzioneCameralini() {
		return funzioneCameralini;
	}
	public void setFunzioneCameralini(String funzioneCameralini) {
		this.funzioneCameralini = funzioneCameralini;
	}
	public ProcedimentoForm getProcedPadre() {
		return procedPadre;
	}
	public void setProcedPadre(ProcedimentoForm procedPadre) {
		this.procedPadre = procedPadre;
	}
	public ProcedimentoForm getProcedCameralini() {
		return procedCameralini;
	}
	public void setProcedCameralini(ProcedimentoForm procedCameralini) {
		this.procedCameralini = procedCameralini;
	}
	public String getTipologiaPrevenzione() {
		return tipologiaPrevenzione;
	}
	public void setTipologiaPrevenzione(String tipologiaPrevenzione) {
		this.tipologiaPrevenzione = tipologiaPrevenzione;
	}
	
	public String getNomeRichiedente() {
		return nomeRichiedente;
	}
	public void setNomeRichiedente(String nomeRichiedente) {
		this.nomeRichiedente = nomeRichiedente;
	}
	public String getCognomeRichiedente() {
		return cognomeRichiedente;
	}
	public void setCognomeRichiedente(String cognomeRichiedente) {
		this.cognomeRichiedente = cognomeRichiedente;
	}
	public Date getDataSottoscrizione() {
		return dataSottoscrizione;
	}
	public void setDataSottoscrizione(Date dataSottoscrizione) {
		this.dataSottoscrizione = dataSottoscrizione;
	}
	public Date getDataDeposito() {
		return dataDeposito;
	}
	public void setDataDeposito(Date dataDeposito) {
		this.dataDeposito = dataDeposito;
	}
	public ComboForm getTrasmissione() {
		return trasmissione;
	}
	public void setTrasmissione(ComboForm trasmissione) {
		this.trasmissione = trasmissione;
	}
	public String getTipologiaIstanza() {
		return tipologiaIstanza;
	}
	public void setTipologiaIstanza(String tipologiaIstanza) {
		this.tipologiaIstanza = tipologiaIstanza;
	}
	public String getTrasmissioneDesc() {
		return trasmissioneDesc;
	}
	public void setTrasmissioneDesc(String trasmissioneDesc) {
		this.trasmissioneDesc = trasmissioneDesc;
	}
}
