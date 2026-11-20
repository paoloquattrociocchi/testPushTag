package it.giustizia.smp.xml;

import java.math.BigDecimal;
import java.util.Date;

public class DatiVeicoloGest extends DatiGest {

	private Boolean beneRinvenuto;
	private Date dataPossesso;
	private Date dataPerditaPossesso;
	private String descStatoManutenzione;
	private Boolean trascrizioneSequestro;
	private Date dataTrascrizioneSequestro;
	private String descCustode;
	private Boolean oneriCustodia;
	private BigDecimal valoreOneriCustodia;
	private Date dataOneriCustodia;
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

	public String getDescStatoManutenzione() {
		return descStatoManutenzione;
	}

	public void setDescStatoManutenzione(String descStatoManutenzione) {
		this.descStatoManutenzione = descStatoManutenzione;
	}

	public Boolean getTrascrizioneSequestro() {
		return trascrizioneSequestro;
	}

	public void setTrascrizioneSequestro(Boolean trascrizioneSequestro) {
		this.trascrizioneSequestro = trascrizioneSequestro;
	}

	public Date getDataTrascrizioneSequestro() {
		return dataTrascrizioneSequestro;
	}

	public void setDataTrascrizioneSequestro(Date dataTrascrizioneSequestro) {
		this.dataTrascrizioneSequestro = dataTrascrizioneSequestro;
	}

	public String getDescCustode() {
		return descCustode;
	}

	public void setDescCustode(String descCustode) {
		this.descCustode = descCustode;
	}

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

	public String getDescAltriVincoli() {
		return descAltriVincoli;
	}

	public void setDescAltriVincoli(String descAltriVincoli) {
		this.descAltriVincoli = descAltriVincoli;
	}
}
