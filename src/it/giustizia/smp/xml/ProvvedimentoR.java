package it.giustizia.smp.xml;

import java.util.Date;

public class ProvvedimentoR {

	private String codiceFiscale;
	private Date dataInizioPeriodo;
	private Date dataFinePeriodo;
	private Date dataPresentazioneRelazione;

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public Date getDataInizioPeriodo() {
		return dataInizioPeriodo;
	}

	public void setDataInizioPeriodo(Date dataInizioPeriodo) {
		this.dataInizioPeriodo = dataInizioPeriodo;
	}

	public Date getDataFinePeriodo() {
		return dataFinePeriodo;
	}

	public void setDataFinePeriodo(Date dataFinePeriodo) {
		this.dataFinePeriodo = dataFinePeriodo;
	}

	public Date getDataPresentazioneRelazione() {
		return dataPresentazioneRelazione;
	}

	public void setDataPresentazioneRelazione(Date dataPresentazioneRelazione) {
		this.dataPresentazioneRelazione = dataPresentazioneRelazione;
	}
}
