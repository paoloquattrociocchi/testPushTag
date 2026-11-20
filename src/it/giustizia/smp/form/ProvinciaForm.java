package it.giustizia.smp.form;

import java.util.Date;

import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.validation.date.ModernDate;

import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

public class ProvinciaForm {

	private String codiKIdIstatProv;
	@Pattern(regexp = Constants.RegExp.STATO)
    private String descProvincia;
	private String descSiglaProvincia;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date  dataFineValidita; 
  
	public ProvinciaForm() {

	}


	public String getCodiKIdIstatProv() {
		return codiKIdIstatProv;
	}


	public void setCodiKIdIstatProv(String codiKIdIstatProv) {
		this.codiKIdIstatProv = codiKIdIstatProv;
	}


	public String getDescProvincia() {
		return descProvincia;
	}


	public void setDescProvincia(String descProvincia) {
		this.descProvincia = descProvincia;
	}


	public String getDescSiglaProvincia() {
		return descSiglaProvincia;
	}


	public void setDescSiglaProvincia(String descSiglaProvincia) {
		this.descSiglaProvincia = descSiglaProvincia;
	}


	public Date getDataFineValidita() {
		return dataFineValidita;
	}


	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}

	
	
		
}
