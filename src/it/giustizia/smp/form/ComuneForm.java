package it.giustizia.smp.form;

import java.util.Date;

import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.validation.date.ModernDate;

import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

public class ComuneForm {

	private int			codiKIdComune;
	private String      descIstatComune;
	@Pattern(regexp = Constants.RegExp.STATO)
    private String      descComune;
    private String      descCapComune;
    private String      descBelfiore;
    private String      descCatasto; 
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date  dataFineValidita;
	
    private ComboForm	itemProvincia;
	
	public ComuneForm() {

	}

	public int getCodiKIdComune() {
		return codiKIdComune;
	}

	public void setCodiKIdComune(int codiKIdComune) {
		this.codiKIdComune = codiKIdComune;
	}

	public String getDescIstatComune() {
		return descIstatComune;
	}

	public void setDescIstatComune(String descIstatComune) {
		this.descIstatComune = descIstatComune;
	}

	public String getDescComune() {
		return descComune;
	}

	public void setDescComune(String descComune) {
		this.descComune = descComune;
	}

	public String getDescCapComune() {
		return descCapComune;
	}

	public void setDescCapComune(String descCapComune) {
		this.descCapComune = descCapComune;
	}

	public String getDescBelfiore() {
		return descBelfiore;
	}

	public void setDescBelfiore(String descBelfiore) {
		this.descBelfiore = descBelfiore;
	}

	public String getDescCatasto() {
		return descCatasto;
	}

	public void setDescCatasto(String descCatasto) {
		this.descCatasto = descCatasto;
	}

	public ComboForm getItemProvincia() {
		return itemProvincia;
	}

	public void setItemProvincia(ComboForm itemProvincia) {
		this.itemProvincia = itemProvincia;
	}

	public Date getDataFineValidita() {
		return dataFineValidita;
	}

	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}

}
