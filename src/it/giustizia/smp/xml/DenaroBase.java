package it.giustizia.smp.xml;

import java.math.BigDecimal;

public abstract class DenaroBase {

	private String descrizioneEnteDepositario;
	private String iban;
	private String swift;
	private String tipoValuta;
	private BigDecimal importoValuta;
	private BigDecimal importoEuro;

	public String getDescrizioneEnteDepositario() {
		return descrizioneEnteDepositario;
	}

	public void setDescrizioneEnteDepositario(String descrizioneEnteDepositario) {
		this.descrizioneEnteDepositario = descrizioneEnteDepositario;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getSwift() {
		return swift;
	}

	public void setSwift(String swift) {
		this.swift = swift;
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

}
