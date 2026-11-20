package it.giustizia.smp.form;

import it.giustizia.smp.validation.date.ModernDate;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class IstitutoDetenzioneForm {

	private int codiKIdIstituto;
	private ComboForm itemTipoIstituto;
	private ComboForm itemComune;
	private String descIndirizzo;
	private String descIstituto;
	private String descUffGiudiz;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date  dataFineValidita;
	
	public int getCodiKIdIstituto() {
		return codiKIdIstituto;
	}
	public void setCodiKIdIstituto(int codiKIdIstituto) {
		this.codiKIdIstituto = codiKIdIstituto;
	}
	public ComboForm getItemTipoIstituto() {
		return itemTipoIstituto;
	}
	public void setItemTipoIstituto(ComboForm itemTipoIstituto) {
		this.itemTipoIstituto = itemTipoIstituto;
	}
	public ComboForm getItemComune() {
		return itemComune;
	}
	public void setItemComune(ComboForm itemComune) {
		this.itemComune = itemComune;
	}
	public String getDescIndirizzo() {
		return descIndirizzo;
	}
	public void setDescIndirizzo(String descIndirizzo) {
		this.descIndirizzo = descIndirizzo;
	}
	public String getDescIstituto() {
		return descIstituto;
	}
	public void setDescIstituto(String descIstituto) {
		this.descIstituto = descIstituto;
	}
	public String getDescUffGiudiz() {
		return descUffGiudiz;
	}
	public void setDescUffGiudiz(String descUffGiudiz) {
		this.descUffGiudiz = descUffGiudiz;
	}
	public Date getDataFineValidita() {
		return dataFineValidita;
	}
	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}
}
