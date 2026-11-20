package it.giustizia.smp.xml;

public class PersonaGiuridica extends Persona {

	protected String denominazione;
	protected String partitaIVA;
	protected String tipologia;
	protected SedeLegale sedeLegale;

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getPartitaIVA() {
		return partitaIVA;
	}

	public void setPartitaIVA(String partitaIVA) {
		this.partitaIVA = partitaIVA;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public SedeLegale getSedeLegale() {
		return sedeLegale;
	}

	public void setSedeLegale(SedeLegale sedeLegale) {
		this.sedeLegale = sedeLegale;
	}
}
