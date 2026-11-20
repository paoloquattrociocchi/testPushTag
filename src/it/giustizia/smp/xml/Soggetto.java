package it.giustizia.smp.xml;


import java.math.BigInteger;

public class Soggetto implements Comparable<Soggetto>{

	private BigInteger identificativo;
	private PersonaGiuridica personaGiuridica;
	private PersonaFisica personaFisica;

	public BigInteger getIdentificativo() {
		return identificativo;
	}

	public void setIdentificativo(BigInteger identificativo) {
		this.identificativo = identificativo;
	}

	public PersonaGiuridica getPersonaGiuridica() {
		return personaGiuridica;
	}

	public void setPersonaGiuridica(PersonaGiuridica personaGiuridica) {
		this.personaGiuridica = personaGiuridica;
	}

	public PersonaFisica getPersonaFisica() {
		return personaFisica;
	}

	public void setPersonaFisica(PersonaFisica personaFisica) {
		this.personaFisica = personaFisica;
	}
	
	public int compareTo(Soggetto s) {
		 int id= s.getIdentificativo().intValue();
	        /* For Ascending order*/
	        return this.getIdentificativo().intValue()-id;
	}	
}
