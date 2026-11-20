package it.giustizia.smp.xml;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class Amministrazione {

	private String codiceFiscale;
	private List<BigInteger> identificativoBene;
	private Date dataInizioIncarico;
	private Date dataFineIncarico;

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public List<BigInteger> getIdentificativoBene() {
		return identificativoBene;
	}

	public void setIdentificativoBene(List<BigInteger> identificativoBene) {
		this.identificativoBene = identificativoBene;
	}

	public Date getDataInizioIncarico() {
		return dataInizioIncarico;
	}

	public void setDataInizioIncarico(Date dataInizioIncarico) {
		this.dataInizioIncarico = dataInizioIncarico;
	}

	public Date getDataFineIncarico() {
		return dataFineIncarico;
	}

	public void setDataFineIncarico(Date dataFineIncarico) {
		this.dataFineIncarico = dataFineIncarico;
	}
}
