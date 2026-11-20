package it.giustizia.smp.xml;

import java.math.BigDecimal;
import javax.xml.datatype.XMLGregorianCalendar;

public class DatiBeneImmaterialeGest {

    private BigDecimal stima;
    private XMLGregorianCalendar dataStima;
    private Boolean dirittiTerzi;
    private String descDirittiTerzi;
    private Boolean trascrizioneSequestro;
    private XMLGregorianCalendar dataTrascrizioneSequestro;
    private String descCustode;
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
	public Boolean getTrascrizioneSequestro() {
		return trascrizioneSequestro;
	}
	public XMLGregorianCalendar getDataTrascrizioneSequestro() {
		return dataTrascrizioneSequestro;
	}
	public String getDescCustode() {
		return descCustode;
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
}
