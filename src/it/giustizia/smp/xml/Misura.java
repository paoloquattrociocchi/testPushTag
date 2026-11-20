package it.giustizia.smp.xml;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class Misura implements Comparable<Misura> {

	private BigInteger identificativoBene;
	private String tipoInteresse;
	private BigInteger identificativoSoggettoTitolare;
	private BigDecimal quota;
	private List<BigInteger> identificativoSoggettoProposto;
	private BigInteger identificativoNormativa;
	private String decisione;

	public BigInteger getIdentificativoBene() {
		return identificativoBene;
	}

	public void setIdentificativoBene(BigInteger identificativoBene) {
		this.identificativoBene = identificativoBene;
	}

	public String getTipoInteresse() {
		return tipoInteresse;
	}

	public void setTipoInteresse(String tipoInteresse) {
		this.tipoInteresse = tipoInteresse;
	}

	public BigInteger getIdentificativoSoggettoTitolare() {
		return identificativoSoggettoTitolare;
	}

	public void setIdentificativoSoggettoTitolare(BigInteger identificativoSoggettoTitolare) {
		this.identificativoSoggettoTitolare = identificativoSoggettoTitolare;
	}

	public BigDecimal getQuota() {
		return quota;
	}

	public void setQuota(BigDecimal quota) {
		this.quota = quota;
	}

	public List<BigInteger> getIdentificativoSoggettoProposto() {
		return identificativoSoggettoProposto;
	}

	public void setIdentificativoSoggettoProposto(List<BigInteger> identificativoSoggettoProposto) {
		this.identificativoSoggettoProposto = identificativoSoggettoProposto;
	}

	public BigInteger getIdentificativoNormativa() {
		return identificativoNormativa;
	}

	public void setIdentificativoNormativa(BigInteger identificativoNormativa) {
		this.identificativoNormativa = identificativoNormativa;
	}

	public String getDecisione() {
		return decisione;
	}

	public void setDecisione(String decisione) {
		this.decisione = decisione;
	}
	
	public int compareTo(Misura m) {
		if(m!=null && m.getIdentificativoNormativa()!=null)
		{
			 int id= m.getIdentificativoNormativa().intValue();
		        /* For Ascending order*/
		        return this.getIdentificativoNormativa().intValue()-id;
		}
		else
			return 0;
	}	
}
