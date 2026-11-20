package it.giustizia.smp.xml;

import java.math.BigInteger;

public abstract class BeneBase {

	private BigInteger id;
	private String tipo;
	private String categoria;
	private String sottocategoria;
	private Ubicazione ubicazione;
	private boolean gravatoDaPegnoIpoteca;
	private String descBene;
	
	
	public String getDescBene() {
		return descBene;
	}

	public void setDescBene(String descBene) {
		this.descBene = descBene;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getSottocategoria() {
		return sottocategoria;
	}

	public void setSottocategoria(String sottocategoria) {
		this.sottocategoria = sottocategoria;
	}

	public Ubicazione getUbicazione() {
		return ubicazione;
	}

	public void setUbicazione(Ubicazione ubicazione) {
		this.ubicazione = ubicazione;
	}

	public boolean isGravatoDaPegnoIpoteca() {
		return gravatoDaPegnoIpoteca;
	}

	public void setGravatoDaPegnoIpoteca(boolean gravatoDaPegnoIpoteca) {
		this.gravatoDaPegnoIpoteca = gravatoDaPegnoIpoteca;
	}
}
