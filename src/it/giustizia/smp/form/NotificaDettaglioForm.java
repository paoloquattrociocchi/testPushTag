package it.giustizia.smp.form;

import it.giustizia.smp.validation.date.ModernDate;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class NotificaDettaglioForm {

    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
	Date dataInvio;
	Boolean datiSensibili;
	String oggetto;
	String evento;
	ArrayList<NotificaDettaglioDestinatarioForm> dettaglioDestinatari = new ArrayList<NotificaDettaglioDestinatarioForm>();
	
	public Date getDataInvio() {
		return dataInvio;
	}
	public void setDataInvio(Date dataInvio) {
		this.dataInvio = dataInvio;
	}
	public Boolean getDatiSensibili() {
		return datiSensibili;
	}
	public void setDatiSensibili(Boolean datiSensibili) {
		this.datiSensibili = datiSensibili;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public String getEvento() {
		return evento;
	}
	public void setEvento(String evento) {
		this.evento = evento;
	}
	public ArrayList<NotificaDettaglioDestinatarioForm> getDettaglioDestinatari() {
		return dettaglioDestinatari;
	}
	public void setDettaglioDestinatari(ArrayList<NotificaDettaglioDestinatarioForm> dettaglioDestinatari) {
		this.dettaglioDestinatari = dettaglioDestinatari;
	}  

	
	
}
