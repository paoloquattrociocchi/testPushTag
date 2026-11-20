package it.giustizia.smp.form;

import it.giustizia.smp.validation.date.ModernDate;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class CategoriaCatastaleForm {

	private int codiKIdCategCat;
	private String descBreveCategoriaCat;
	private String descCategoriaCat;
	@ModernDate
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date  dataFineValidita;	
	
	public String getDescBreveCategoriaCat() {
		return descBreveCategoriaCat;
	}
	public void setDescBreveCategoriaCat(String descBreveCategoriaCat) {
		this.descBreveCategoriaCat = descBreveCategoriaCat;
	}
	public String getDescCategoriaCat() {
		return descCategoriaCat;
	}
	public void setDescCategoriaCat(String descCategoriaCat) {
		this.descCategoriaCat = descCategoriaCat;
	}
	public int getCodiKIdCategCat() {
		return codiKIdCategCat;
	}
	public void setCodiKIdCategCat(int codiKIdCategCat) {
		this.codiKIdCategCat = codiKIdCategCat;
	}
	public Date getDataFineValidita() {
		return dataFineValidita;
	}
	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}
}
