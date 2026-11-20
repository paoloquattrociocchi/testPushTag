package it.giustizia.smp.xml;

import java.math.BigDecimal;
import javax.xml.datatype.XMLGregorianCalendar;


public class DatiBeneVeicoloGest {

    private BigDecimal stima;
    private XMLGregorianCalendar dataStima;
    private Boolean dirittiTerzi;
    private String descDirittiTerzi;
    private Boolean beneRinvenuto;
    private XMLGregorianCalendar dataPossesso;
    private XMLGregorianCalendar dataPerditaPossesso;
    private String descStatoManutenzione;
    private Boolean trascrizioneSequestro;
    private XMLGregorianCalendar dataTrascrizioneSequestro;
    private String descCustode;
    private Boolean oneriCustodia;
    private BigDecimal valoreOneriCustodia;
    private XMLGregorianCalendar dataOneriCustodia;
    private String descAltriVincoli;
    
	public BigDecimal getStima() {
		return stima;
	}
	public XMLGregorianCalendar getDataStima() {
		return dataStima;
	}
	public Boolean getDirittiTerzi() {
		return dirittiTerzi;
	}
	public String getDescDirittiTerzi() {
		return descDirittiTerzi;
	}
	public Boolean getBeneRinvenuto() {
		return beneRinvenuto;
	}
	public XMLGregorianCalendar getDataPossesso() {
		return dataPossesso;
	}
	public XMLGregorianCalendar getDataPerditaPossesso() {
		return dataPerditaPossesso;
	}
	public String getDescStatoManutenzione() {
		return descStatoManutenzione;
	}
	public Boolean getTrascrizioneSequestro() {
		return trascrizioneSequestro;
	}
	public XMLGregorianCalendar getDataTrascrizioneSequestro() {
		return dataTrascrizioneSequestro;
	}
	public String getDescCustode() {
		return descCustode;
	}
	public Boolean getOneriCustodia() {
		return oneriCustodia;
	}
	public BigDecimal getValoreOneriCustodia() {
		return valoreOneriCustodia;
	}
	public XMLGregorianCalendar getDataOneriCustodia() {
		return dataOneriCustodia;
	}
	public String getDescAltriVincoli() {
		return descAltriVincoli;
	}
	public void setStima(BigDecimal stima) {
		this.stima = stima;
	}
	public void setDataStima(XMLGregorianCalendar dataStima) {
		this.dataStima = dataStima;
	}
	public void setDirittiTerzi(Boolean dirittiTerzi) {
		this.dirittiTerzi = dirittiTerzi;
	}
	public void setDescDirittiTerzi(String descDirittiTerzi) {
		this.descDirittiTerzi = descDirittiTerzi;
	}
	public void setBeneRinvenuto(Boolean beneRinvenuto) {
		this.beneRinvenuto = beneRinvenuto;
	}
	public void setDataPossesso(XMLGregorianCalendar dataPossesso) {
		this.dataPossesso = dataPossesso;
	}
	public void setDataPerditaPossesso(XMLGregorianCalendar dataPerditaPossesso) {
		this.dataPerditaPossesso = dataPerditaPossesso;
	}
	public void setDescStatoManutenzione(String descStatoManutenzione) {
		this.descStatoManutenzione = descStatoManutenzione;
	}
	public void setTrascrizioneSequestro(Boolean trascrizioneSequestro) {
		this.trascrizioneSequestro = trascrizioneSequestro;
	}
	public void setDataTrascrizioneSequestro(
			XMLGregorianCalendar dataTrascrizioneSequestro) {
		this.dataTrascrizioneSequestro = dataTrascrizioneSequestro;
	}
	public void setDescCustode(String descCustode) {
		this.descCustode = descCustode;
	}
	public void setOneriCustodia(Boolean oneriCustodia) {
		this.oneriCustodia = oneriCustodia;
	}
	public void setValoreOneriCustodia(BigDecimal valoreOneriCustodia) {
		this.valoreOneriCustodia = valoreOneriCustodia;
	}
	public void setDataOneriCustodia(XMLGregorianCalendar dataOneriCustodia) {
		this.dataOneriCustodia = dataOneriCustodia;
	}
	public void setDescAltriVincoli(String descAltriVincoli) {
		this.descAltriVincoli = descAltriVincoli;
	}

}
