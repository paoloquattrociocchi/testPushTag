package it.giustizia.smp.form;

import it.giustizia.smp.validation.date.ModernDate;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class SettoreAttivitaForm {

	private int codiKIdSettoreAtt;
	private String descSettoreAtt;
	private String descSezioneAteco;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date  dataFineValidita;
	
	public int getCodiKIdSettoreAtt() {
		return codiKIdSettoreAtt;
	}
	public void setCodiKIdSettoreAtt(int codiKIdSettoreAtt) {
		this.codiKIdSettoreAtt = codiKIdSettoreAtt;
	}
	public String getDescSettoreAtt() {
		return descSettoreAtt;
	}
	public void setDescSettoreAtt(String descSettoreAtt) {
		this.descSettoreAtt = descSettoreAtt;
	}
	public String getDescSezioneAteco() {
		return descSezioneAteco;
	}
	public void setDescSezioneAteco(String descSezioneAteco) {
		this.descSezioneAteco = descSezioneAteco;
	}
	public Date getDataFineValidita() {
		return dataFineValidita;
	}
	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}
}
