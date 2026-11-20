package it.giustizia.smp.form;

import it.giustizia.smp.validation.date.ModernDate;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class CondizioneGiuridicaForm {

	private int codiKIdCondGiur;
	private String descCondGiur;
	@ModernDate
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date  dataFineValidita;
	
	public int getCodiKIdCondGiur() {
		return codiKIdCondGiur;
	}
	public void setCodiKIdCondGiur(int codiKIdCondGiur) {
		this.codiKIdCondGiur = codiKIdCondGiur;
	}
	public String getDescCondGiur() {
		return descCondGiur;
	}
	public void setDescCondGiur(String descCondGiur) {
		this.descCondGiur = descCondGiur;
	}
	public Date getDataFineValidita() {
		return dataFineValidita;
	}
	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}
}
