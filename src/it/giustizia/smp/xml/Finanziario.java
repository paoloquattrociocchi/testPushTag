package it.giustizia.smp.xml;

import java.math.BigDecimal;

public class Finanziario {

	private String tipo;
	private String descEnte;
	private String filialeModulo;
	private String direttore;
	// Per gli uffici bancari, postali...
	private String swift;
	private String iban;
	// A partire dai campi abi, cab e numerocc puo` essere composto l'iban
	private String abi;
	private String cab;
	private String nnumerocc;
	// Per le assicurazioni
	private String codiceFiscale;
	private String numeroPolizza;
	private String tipoValuta;
	private BigDecimal importoValuta;
	private BigDecimal importoEuro;
	private String ident;

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescEnte() {
		return descEnte;
	}

	public void setDescEnte(String descEnte) {
		this.descEnte = descEnte;
	}

	public String getFilialeModulo() {
		return filialeModulo;
	}

	public void setFilialeModulo(String filialeModulo) {
		this.filialeModulo = filialeModulo;
	}

	public String getDirettore() {
		return direttore;
	}

	public void setDirettore(String direttore) {
		this.direttore = direttore;
	}

	public String getSwift() {
		return swift;
	}

	public void setSwift(String swift) {
		this.swift = swift;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getAbi() {
		return abi;
	}

	public void setAbi(String abi) {
		this.abi = abi;
	}

	public String getCab() {
		return cab;
	}

	public void setCab(String cab) {
		this.cab = cab;
	}

	public String getNnumerocc() {
		return nnumerocc;
	}

	public void setNnumerocc(String nnumerocc) {
		this.nnumerocc = nnumerocc;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getNumeroPolizza() {
		return numeroPolizza;
	}

	public void setNumeroPolizza(String numeroPolizza) {
		this.numeroPolizza = numeroPolizza;
	}

	public String getTipoValuta() {
		return tipoValuta;
	}

	public void setTipoValuta(String tipoValuta) {
		this.tipoValuta = tipoValuta;
	}

	public BigDecimal getImportoValuta() {
		return importoValuta;
	}

	public void setImportoValuta(BigDecimal importoValuta) {
		this.importoValuta = importoValuta;
	}

	public BigDecimal getImportoEuro() {
		return importoEuro;
	}

	public void setImportoEuro(BigDecimal importoEuro) {
		this.importoEuro = importoEuro;
	}

	public String getIdent() {
		return ident;
	}

	public void setIdent(String ident) {
		this.ident = ident;
	}
}
