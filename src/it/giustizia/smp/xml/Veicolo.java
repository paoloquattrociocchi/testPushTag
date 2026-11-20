package it.giustizia.smp.xml;

import java.util.Date;

public class Veicolo {

	private String numeroRegistro;
	private Date dataRegistrazione;
	private String numeroIdentificativo;
	private String codiceImmatricolazione;
	private String descrizioneModello;
	private String numeroTarga;
	private String numeroTelaio;

	public String getNumeroRegistro() {
		return numeroRegistro;
	}

	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	public Date getDataRegistrazione() {
		return dataRegistrazione;
	}

	public void setDataRegistrazione(Date dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}

	public String getNumeroIdentificativo() {
		return numeroIdentificativo;
	}

	public void setNumeroIdentificativo(String numeroIdentificativo) {
		this.numeroIdentificativo = numeroIdentificativo;
	}

	public String getCodiceImmatricolazione() {
		return codiceImmatricolazione;
	}

	public void setCodiceImmatricolazione(String codiceImmatricolazione) {
		this.codiceImmatricolazione = codiceImmatricolazione;
	}

	public String getDescrizioneModello() {
		return descrizioneModello;
	}

	public void setDescrizioneModello(String descrizioneModello) {
		this.descrizioneModello = descrizioneModello;
	}

	public String getNumeroTarga() {
		return numeroTarga;
	}

	public void setNumeroTarga(String numeroTarga) {
		this.numeroTarga = numeroTarga;
	}

	public String getNumeroTelaio() {
		return numeroTelaio;
	}

	public void setNumeroTelaio(String numeroTelaio) {
		this.numeroTelaio = numeroTelaio;
	}

	public String getDescrizioneColore() {
		return descrizioneColore;
	}

	public void setDescrizioneColore(String descrizioneColore) {
		this.descrizioneColore = descrizioneColore;
	}

	private String descrizioneColore;
}
