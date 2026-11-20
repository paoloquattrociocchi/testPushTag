package it.giustizia.smp.xml;

import java.math.BigInteger;

public class Procedimento {

	private BigInteger id;
	private long ufficioCompetente;
	private int annoProcedimento;
	private BigInteger numeroProcedimento;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public long getUfficioCompetente() {
		return ufficioCompetente;
	}

	public void setUfficioCompetente(long ufficioCompetente) {
		this.ufficioCompetente = ufficioCompetente;
	}

	public int getAnnoProcedimento() {
		return annoProcedimento;
	}

	public void setAnnoProcedimento(int annoProcedimento) {
		this.annoProcedimento = annoProcedimento;
	}

	public BigInteger getNumeroProcedimento() {
		return numeroProcedimento;
	}

	public void setNumeroProcedimento(BigInteger numeroProcedimento) {
		this.numeroProcedimento = numeroProcedimento;
	}
}
