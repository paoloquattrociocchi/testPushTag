package it.giustizia.smp.form;

import it.giustizia.smp.model.TAtto;

import java.util.LinkedHashMap;

public class NotificaDettaglioDestinatarioForm {
	Long codiKIdNotifDest;
	String tipoDestinatario;
	String destinatario;
	String statoComunicazione;
	String ricevuta;
	Boolean hasAmministratori = false;
	
	LinkedHashMap<String,TAtto> mappaRicevute = new LinkedHashMap<String,TAtto>();
	
	public String getTipoDestinatario() {
		return tipoDestinatario;
	}
	public void setTipoDestinatario(String tipoDestinatario) {
		this.tipoDestinatario = tipoDestinatario;
	}
	public String getDestinatario() {
		return destinatario;
	}
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	public String getStatoComunicazione() {
		return statoComunicazione;
	}
	public void setStatoComunicazione(String statoComunicazione) {
		this.statoComunicazione = statoComunicazione;
	}
	public String getRicevuta() {
		return ricevuta;
	}
	public void setRicevuta(String ricevuta) {
		this.ricevuta = ricevuta;
	}
	public LinkedHashMap<String, TAtto> getMappaRicevute() {
		return mappaRicevute;
	}
	public void setMappaRicevute(LinkedHashMap<String, TAtto> mappaRicevute) {
		this.mappaRicevute = mappaRicevute;
	}
	public Boolean getHasAmministratori() {
		return hasAmministratori;
	}
	public void setHasAmministratori(Boolean hasAmministratori) {
		this.hasAmministratori = hasAmministratori;
	}
	public Long getCodiKIdNotifDest() {
		return codiKIdNotifDest;
	}
	public void setCodiKIdNotifDest(Long codiKIdNotifDest) {
		this.codiKIdNotifDest = codiKIdNotifDest;
	}
}
