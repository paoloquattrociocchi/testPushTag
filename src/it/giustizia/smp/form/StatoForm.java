package it.giustizia.smp.form;

import java.util.Date;

import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.validation.date.ModernDate;

import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

public class StatoForm {

	private int			codiKIdStato;
	@Pattern(regexp = Constants.RegExp.STATO)
    private String      descStato;
    private String      descSiglaStato;
    private String      descIstatStato;
    private String      descBelfiore;
    private String      descSiglaPartitaIva;  
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date  dataFineValidita;
    
	public StatoForm() {

	}

	public int getCodiKIdStato() {
		return codiKIdStato;
	}

	public void setCodiKIdStato(int codiKIdStato) {
		this.codiKIdStato = codiKIdStato;
	}

	public String getDescStato() {
		return descStato;
	}

	public void setDescStato(String descStato) {
		this.descStato = descStato;
	}

	public String getDescSiglaStato() {
		return descSiglaStato;
	}

	public void setDescSiglaStato(String descSiglaStato) {
		this.descSiglaStato = descSiglaStato;
	}

	public String getDescIstatStato() {
		return descIstatStato;
	}

	public void setDescIstatStato(String descIstatStato) {
		this.descIstatStato = descIstatStato;
	}

	public String getDescBelfiore() {
		return descBelfiore;
	}

	public void setDescBelfiore(String descBelfiore) {
		this.descBelfiore = descBelfiore;
	}

	public String getDescSiglaPartitaIva() {
		return descSiglaPartitaIva;
	}

	public void setDescSiglaPartitaIva(String descSiglaPartitaIva) {
		this.descSiglaPartitaIva = descSiglaPartitaIva;
	}

	public Date getDataFineValidita() {
		return dataFineValidita;
	}

	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}

	
		
}
