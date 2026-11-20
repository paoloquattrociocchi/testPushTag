package it.giustizia.smp.xml;

import java.math.BigDecimal;
import java.util.Date;

public abstract class DatiGest {

	private BigDecimal stima;
	private Date dataStima;
	private Boolean dirittiTerzi;
	private String descDirittiTerzi;

	public BigDecimal getStima() {
		return stima;
	}

	public void setStima(BigDecimal stima) {
		this.stima = stima;
	}

	public Date getDataStima() {
		return dataStima;
	}

	public void setDataStima(Date dataStima) {
		this.dataStima = dataStima;
	}
	public Boolean getDirittiTerzi() {
		return dirittiTerzi;
	}

	public void setDirittiTerzi(Boolean dirittiTerzi) {
		this.dirittiTerzi = dirittiTerzi;
	}

	public String getDescDirittiTerzi() {
		return descDirittiTerzi;
	}

	public void setDescDirittiTerzi(String descDirittiTerzi) {
		this.descDirittiTerzi = descDirittiTerzi;
	}
}
