package it.giustizia.smp.form;

import it.giustizia.smp.validation.date.ModernDate;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class ConservatoriaForm {

	private short codiKIdConserv;
	private String descConserv;
	private String descCodiConserv;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date  dataFineValidita;
	
	public short getCodiKIdConserv() {
		return codiKIdConserv;
	}
	public void setCodiKIdConserv(short codiKIdConserv) {
		this.codiKIdConserv = codiKIdConserv;
	}
	public String getDescConserv() {
		return descConserv;
	}
	public void setDescConserv(String descConserv) {
		this.descConserv = descConserv;
	}
	public String getDescCodiConserv() {
		return descCodiConserv;
	}
	public void setDescCodiConserv(String descCodiConserv) {
		this.descCodiConserv = descCodiConserv;
	}
	public Date getDataFineValidita() {
		return dataFineValidita;
	}
	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}
}
