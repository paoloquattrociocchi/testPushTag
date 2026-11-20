package it.giustizia.smp.xml;

import java.math.BigInteger;

public class DenaroContante extends DenaroBase {

	private String descrizioneIntestatario;
	private String numeroLibretto;
	private BigInteger numeroModelloTributario;

	public String getDescrizioneIntestatario() {
		return descrizioneIntestatario;
	}

	public void setDescrizioneIntestatario(String descrizioneIntestatario) {
		this.descrizioneIntestatario = descrizioneIntestatario;
	}

	public String getNumeroLibretto() {
		return numeroLibretto;
	}

	public void setNumeroLibretto(String numeroLibretto) {
		this.numeroLibretto = numeroLibretto;
	}

	public BigInteger getNumeroModelloTributario() {
		return numeroModelloTributario;
	}

	public void setNumeroModelloTributario(BigInteger numeroModelloTributario) {
		this.numeroModelloTributario = numeroModelloTributario;
	}
}
