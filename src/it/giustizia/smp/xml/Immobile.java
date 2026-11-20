package it.giustizia.smp.xml;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class Immobile {

	private String edificio;
	private String scala;
	private String piano;
	private String interno;
	private BigInteger volumeInt;
	private BigInteger volumeDec;
	private BigInteger vaniInt;
	private BigInteger vaniDec;
	private String lotto;
	private BigInteger superficieInt;
	private BigInteger superficieDec;
	private BigInteger ettari;
	private BigInteger are;
	private BigInteger centiare;
	// Dati catastali - inizio
	private String tipoCatasto;
	private String identificativoComuneCatasto;
	private String sezioneCatasto;
	private String foglioCatasto;
	private String particellaCatasto1;
	private String particellaCatasto2;
	private String subparticellaCatasto1;
	private String subparticellaCatasto2;
	private String identificativoCategoriaCatastale;
	private BigDecimal importoRenditaCatastale;
	private BigDecimal importoRenditaDominicale;
	// Dati catastali - fine
	private String numeroDomandaAccatastamento;

	public String getEdificio() {
		return edificio;
	}

	public void setEdificio(String edificio) {
		this.edificio = edificio;
	}

	public String getScala() {
		return scala;
	}

	public void setScala(String scala) {
		this.scala = scala;
	}

	public String getPiano() {
		return piano;
	}

	public void setPiano(String piano) {
		this.piano = piano;
	}

	public String getInterno() {
		return interno;
	}

	public void setInterno(String interno) {
		this.interno = interno;
	}

	public BigInteger getVolumeInt() {
		return volumeInt;
	}

	public void setVolumeInt(BigInteger volumeInt) {
		this.volumeInt = volumeInt;
	}

	public BigInteger getVolumeDec() {
		return volumeDec;
	}

	public void setVolumeDec(BigInteger volumeDec) {
		this.volumeDec = volumeDec;
	}

	public BigInteger getVaniInt() {
		return vaniInt;
	}

	public void setVaniInt(BigInteger vaniInt) {
		this.vaniInt = vaniInt;
	}

	public BigInteger getVaniDec() {
		return vaniDec;
	}

	public void setVaniDec(BigInteger vaniDec) {
		this.vaniDec = vaniDec;
	}

	public String getLotto() {
		return lotto;
	}

	public void setLotto(String lotto) {
		this.lotto = lotto;
	}

	public BigInteger getSuperficieInt() {
		return superficieInt;
	}

	public void setSuperficieInt(BigInteger superficieInt) {
		this.superficieInt = superficieInt;
	}

	public BigInteger getSuperficieDec() {
		return superficieDec;
	}

	public void setSuperficieDec(BigInteger superficieDec) {
		this.superficieDec = superficieDec;
	}

	public BigInteger getEttari() {
		return ettari;
	}

	public void setEttari(BigInteger ettari) {
		this.ettari = ettari;
	}

	public BigInteger getAre() {
		return are;
	}

	public void setAre(BigInteger are) {
		this.are = are;
	}

	public BigInteger getCentiare() {
		return centiare;
	}

	public void setCentiare(BigInteger centiare) {
		this.centiare = centiare;
	}

	public String getTipoCatasto() {
		return tipoCatasto;
	}

	public void setTipoCatasto(String tipoCatasto) {
		this.tipoCatasto = tipoCatasto;
	}

	public String getIdentificativoComuneCatasto() {
		return identificativoComuneCatasto;
	}

	public void setIdentificativoComuneCatasto(String identificativoComuneCatasto) {
		this.identificativoComuneCatasto = identificativoComuneCatasto;
	}

	public String getSezioneCatasto() {
		return sezioneCatasto;
	}

	public void setSezioneCatasto(String sezioneCatasto) {
		this.sezioneCatasto = sezioneCatasto;
	}

	public String getFoglioCatasto() {
		return foglioCatasto;
	}

	public void setFoglioCatasto(String foglioCatasto) {
		this.foglioCatasto = foglioCatasto;
	}

	public String getParticellaCatasto1() {
		return particellaCatasto1;
	}

	public void setParticellaCatasto1(String particellaCatasto1) {
		this.particellaCatasto1 = particellaCatasto1;
	}

	public String getParticellaCatasto2() {
		return particellaCatasto2;
	}

	public void setParticellaCatasto2(String particellaCatasto2) {
		this.particellaCatasto2 = particellaCatasto2;
	}

	public String getSubparticellaCatasto1() {
		return subparticellaCatasto1;
	}

	public void setSubparticellaCatasto1(String subparticellaCatasto1) {
		this.subparticellaCatasto1 = subparticellaCatasto1;
	}

	public String getSubparticellaCatasto2() {
		return subparticellaCatasto2;
	}

	public void setSubparticellaCatasto2(String subparticellaCatasto2) {
		this.subparticellaCatasto2 = subparticellaCatasto2;
	}

	public String getIdentificativoCategoriaCatastale() {
		return identificativoCategoriaCatastale;
	}

	public void setIdentificativoCategoriaCatastale(String identificativoCategoriaCatastale) {
		this.identificativoCategoriaCatastale = identificativoCategoriaCatastale;
	}

	public BigDecimal getImportoRenditaCatastale() {
		return importoRenditaCatastale;
	}

	public void setImportoRenditaCatastale(BigDecimal importoRenditaCatastale) {
		this.importoRenditaCatastale = importoRenditaCatastale;
	}

	public BigDecimal getImportoRenditaDominicale() {
		return importoRenditaDominicale;
	}

	public void setImportoRenditaDominicale(BigDecimal importoRenditaDominicale) {
		this.importoRenditaDominicale = importoRenditaDominicale;
	}

	public String getNumeroDomandaAccatastamento() {
		return numeroDomandaAccatastamento;
	}

	public void setNumeroDomandaAccatastamento(String numeroDomandaAccatastamento) {
		this.numeroDomandaAccatastamento = numeroDomandaAccatastamento;
	}

	public Date getDataDomandaAccatastamento() {
		return dataDomandaAccatastamento;
	}

	public void setDataDomandaAccatastamento(Date dataDomandaAccatastamento) {
		this.dataDomandaAccatastamento = dataDomandaAccatastamento;
	}

	public String getAppartenenzaaFabbricatoConfiscato() {
		return appartenenzaaFabbricatoConfiscato;
	}

	public void setAppartenenzaaFabbricatoConfiscato(String appartenenzaaFabbricatoConfiscato) {
		this.appartenenzaaFabbricatoConfiscato = appartenenzaaFabbricatoConfiscato;
	}

	public String getTipoFondo() {
		return tipoFondo;
	}

	public void setTipoFondo(String tipoFondo) {
		this.tipoFondo = tipoFondo;
	}

	private Date dataDomandaAccatastamento;
	private String appartenenzaaFabbricatoConfiscato;
	private String tipoFondo;
}
