package it.giustizia.smp.xml;

import java.math.BigInteger;

public class Azienda {

	private String ragioneSociale;
	private String partitaIVA;
	private String numeroCCIA;
	private String codiceFiscale;
	private BigInteger capitaleSociale;
	private String rappresentanteSocieta;
	private String amministratore;
	private BigInteger numeroDipendenti;
	private String settore;

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public String getPartitaIVA() {
		return partitaIVA;
	}

	public void setPartitaIVA(String partitaIVA) {
		this.partitaIVA = partitaIVA;
	}

	public String getNumeroCCIA() {
		return numeroCCIA;
	}

	public void setNumeroCCIA(String numeroCCIA) {
		this.numeroCCIA = numeroCCIA;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public BigInteger getCapitaleSociale() {
		return capitaleSociale;
	}

	public void setCapitaleSociale(BigInteger capitaleSociale) {
		this.capitaleSociale = capitaleSociale;
	}

	public String getRappresentanteSocieta() {
		return rappresentanteSocieta;
	}

	public void setRappresentanteSocieta(String rappresentanteSocieta) {
		this.rappresentanteSocieta = rappresentanteSocieta;
	}

	public String getAmministratore() {
		return amministratore;
	}

	public void setAmministratore(String amministratore) {
		this.amministratore = amministratore;
	}

	public BigInteger getNumeroDipendenti() {
		return numeroDipendenti;
	}

	public void setNumeroDipendenti(BigInteger numeroDipendenti) {
		this.numeroDipendenti = numeroDipendenti;
	}

	public String getSettore() {
		return settore;
	}

	public void setSettore(String settore) {
		this.settore = settore;
	}
}
