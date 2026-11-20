package it.giustizia.smp.form;

import it.giustizia.smp.validation.date.ModernDate;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class CoadiutoreForm {

	private Long idProcedimento;
	private Integer idUfficio;
	private Long idAmministratore;
	private Integer idProgrCoad;
	private String cognome;
	private String  nome;
	@ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")	
	private Date dataNascita;
	private String codiceFiscale;
	private String luogoNascita;
	private String recapito;
	@ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date inizioIncarico;
	@ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date fineIncarico;
	private String descUtente;
	
	public Long getIdProcedimento() {
		return idProcedimento;
	}
	public void setIdProcedimento(Long idProcedimento) {
		this.idProcedimento = idProcedimento;
	}
	public Integer getIdUfficio() {
		return idUfficio;
	}
	public void setIdUfficio(Integer idUfficio) {
		this.idUfficio = idUfficio;
	}
	public Long getIdAmministratore() {
		return idAmministratore;
	}
	public void setIdAmministratore(Long idAmministratore) {
		this.idAmministratore = idAmministratore;
	}
	public Integer getIdProgrCoad() {
		return idProgrCoad;
	}
	public void setIdProgrCoad(Integer idProgrCoad) {
		this.idProgrCoad = idProgrCoad;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getLuogoNascita() {
		return luogoNascita;
	}
	public void setLuogoNascita(String luogoNascita) {
		this.luogoNascita = luogoNascita;
	}
	public String getRecapito() {
		return recapito;
	}
	public void setRecapito(String recapito) {
		this.recapito = recapito;
	}
	public Date getInizioIncarico() {
		return inizioIncarico;
	}
	public void setInizioIncarico(Date inizioIncarico) {
		this.inizioIncarico = inizioIncarico;
	}
	public Date getFineIncarico() {
		return fineIncarico;
	}
	public void setFineIncarico(Date fineIncarico) {
		this.fineIncarico = fineIncarico;
	}
	public String getDescUtente() {
		return descUtente;
	}
	public void setDescUtente(String descUtente) {
		this.descUtente = descUtente;
	}

}
