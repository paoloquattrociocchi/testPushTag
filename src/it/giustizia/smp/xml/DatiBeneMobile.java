package it.giustizia.smp.xml;

import java.math.BigDecimal;
import java.util.Date;

public class DatiBeneMobile {

	private Boolean oneriCustodia;
	private BigDecimal valoreOneriCustodia;
	private Date dataOneriCustodia;

	public Boolean getOneriCustodia() {
		return oneriCustodia;
	}

	public void setOneriCustodia(Boolean oneriCustodia) {
		this.oneriCustodia = oneriCustodia;
	}

	public BigDecimal getValoreOneriCustodia() {
		return valoreOneriCustodia;
	}

	public void setValoreOneriCustodia(BigDecimal valoreOneriCustodia) {
		this.valoreOneriCustodia = valoreOneriCustodia;
	}

	public Date getDataOneriCustodia() {
		return dataOneriCustodia;
	}

	public void setDataOneriCustodia(Date dataOneriCustodia) {
		this.dataOneriCustodia = dataOneriCustodia;
	}
}
