package it.giustizia.smp.xml;

import it.giustizia.smp.xml.amministratori.TipoStradaType;

import java.math.BigInteger;

public class Ubicazione {

	private BigInteger idStato;
	private String idComuneItaliano;
	private String descrizioneComuneEstero;
	protected TipoStradaType tipoStrada;
    protected String indirizzo;
    protected String civico;
    protected String cap;
    
    
	public BigInteger getIdStato() {
		return idStato;
	}

	public void setIdStato(BigInteger idStato) {
		this.idStato = idStato;
	}

	public String getIdComuneItaliano() {
		return idComuneItaliano;
	}

	public void setIdComuneItaliano(String idComuneItaliano) {
		this.idComuneItaliano = idComuneItaliano;
	}

	public String getDescrizioneComuneEstero() {
		return descrizioneComuneEstero;
	}

	public void setDescrizioneComuneEstero(String descrizioneComuneEstero) {
		this.descrizioneComuneEstero = descrizioneComuneEstero;
	}

	public TipoStradaType getTipoStrada() {
		return tipoStrada;
	}

	public void setTipoStrada(TipoStradaType tipoStrada) {
		this.tipoStrada = tipoStrada;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getCivico() {
		return civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}
	
	
}
