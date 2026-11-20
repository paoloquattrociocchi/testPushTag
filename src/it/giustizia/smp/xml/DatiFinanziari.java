package it.giustizia.smp.xml;

import java.util.Date;

public abstract class DatiFinanziari extends DatiGest {

	private Boolean beneRinvenuto;
	private Date dataPossesso;
	private Date dataPerditaPossesso;
	private String descAltriVincoli;

	public Boolean getBeneRinvenuto() {
		return beneRinvenuto;
	}

	public void setBeneRinvenuto(Boolean beneRinvenuto) {
		this.beneRinvenuto = beneRinvenuto;
	}

	public Date getDataPossesso() {
		return dataPossesso;
	}

	public void setDataPossesso(Date dataPossesso) {
		this.dataPossesso = dataPossesso;
	}

	public Date getDataPerditaPossesso() {
		return dataPerditaPossesso;
	}

	public void setDataPerditaPossesso(Date dataPerditaPossesso) {
		this.dataPerditaPossesso = dataPerditaPossesso;
	}

	public String getDescAltriVincoli() {
		return descAltriVincoli;
	}

	public void setDescAltriVincoli(String descAltriVincoli) {
		this.descAltriVincoli = descAltriVincoli;
	}
}
