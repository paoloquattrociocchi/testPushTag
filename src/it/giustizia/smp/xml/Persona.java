package it.giustizia.smp.xml;

import java.math.BigInteger;

public abstract class Persona {
	
	private BigInteger identificativo;

	public BigInteger getIdentificativo() {
		return identificativo;
	}

	public void setIdentificativo(BigInteger identificativo) {
		this.identificativo = identificativo;
	}

}
