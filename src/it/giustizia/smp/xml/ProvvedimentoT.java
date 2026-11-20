package it.giustizia.smp.xml;

import java.math.BigInteger;
import java.util.Date;

public class ProvvedimentoT {

	private BigInteger id;
	private BigInteger annoProvvedimento;
	private Date dataDeposito;
	private BigInteger numero;
	private String tipo;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getAnnoProvvedimento() {
		return annoProvvedimento;
	}

	public void setAnnoProvvedimento(BigInteger annoProvvedimento) {
		this.annoProvvedimento = annoProvvedimento;
	}

	public Date getDataDeposito() {
		return dataDeposito;
	}

	public void setDataDeposito(Date dataDeposito) {
		this.dataDeposito = dataDeposito;
	}

	public BigInteger getNumero() {
		return numero;
	}

	public void setNumero(BigInteger numero) {
		this.numero = numero;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
