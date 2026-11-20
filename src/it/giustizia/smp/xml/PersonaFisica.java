package it.giustizia.smp.xml;

import java.util.Date;

public class PersonaFisica extends Persona {

	private String cognome;
	private String nome;
	private String sesso;
	private Date dataNascita;
	private Ubicazione luogoNascita;
	private String codiceFiscale;
	private String cui;

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public Ubicazione getLuogoNascita() {
		return luogoNascita;
	}

	public void setLuogoNascita(Ubicazione luogoNascita) {
		this.luogoNascita = luogoNascita;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getCui() {
		return cui;
	}

	public void setCui(String cui) {
		this.cui = cui;
	}
}
