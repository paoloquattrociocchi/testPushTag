package it.giustizia.smp.form;

import it.giustizia.smp.validation.date.ModernDate;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class ProfessioneForm {

	private int codiKIdProfessione;
	private String descProfessione;
	
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date  dataFineValidita;
    
	public int getCodiKIdProfessione() {
		return codiKIdProfessione;
	}
	public void setCodiKIdProfessione(int codiKIdProfessione) {
		this.codiKIdProfessione = codiKIdProfessione;
	}
	public String getDescProfessione() {
		return descProfessione;
	}
	public void setDescProfessione(String descProfessione) {
		this.descProfessione = descProfessione;
	}
	
	public Date getDataFineValidita() {
		return dataFineValidita;
	}
	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}
}
