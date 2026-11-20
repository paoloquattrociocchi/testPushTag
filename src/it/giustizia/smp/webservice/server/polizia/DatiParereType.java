
package it.giustizia.smp.webservice.server.polizia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiParereType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiParereType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DESCR_NUM_PARERE" type="{http://webservice.smp.giustizia.it/basetypes}DescNumeroParere"/>
 *         &lt;element name="DATA_EMISSIONE" type="{http://webservice.smp.giustizia.it/basetypes}Data"/>
 *         &lt;element name="FLG_PARERE">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;length value="1"/>
 *               &lt;enumeration value="C"/>
 *               &lt;enumeration value="F"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="DESCR_NOTE" type="{http://webservice.smp.giustizia.it/basetypes}Desc200Type"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiParereType", propOrder = {
    "descrnumparere",
    "dataemissione",
    "flgparere",
    "descrnote"
})
public class DatiParereType {

    @XmlElement(name = "DESCR_NUM_PARERE", required = true)
    protected String DESCR_NUM_PARERE;
    @XmlElement(name = "DATA_EMISSIONE", required = true)
    protected String DATA_EMISSIONE;
    @XmlElement(name = "FLG_PARERE", required = true)
    protected String FLG_PARERE;
    @XmlElement(name = "DESCR_NOTE", required = true, nillable = true)
    protected String DESCR_NOTE;
    
	public String getDESCR_NUM_PARERE() {
		return DESCR_NUM_PARERE;
	}
	public void setDESCR_NUM_PARERE(String dESCR_NUM_PARERE) {
		DESCR_NUM_PARERE = dESCR_NUM_PARERE;
	}
	public String getDATA_EMISSIONE() {
		return DATA_EMISSIONE;
	}
	public void setDATA_EMISSIONE(String dATA_EMISSIONE) {
		DATA_EMISSIONE = dATA_EMISSIONE;
	}
	public String getFLG_PARERE() {
		return FLG_PARERE;
	}
	public void setFLG_PARERE(String fLG_PARERE) {
		FLG_PARERE = fLG_PARERE;
	}
	public String getDESCR_NOTE() {
		return DESCR_NOTE;
	}
	public void setDESCR_NOTE(String dESCR_NOTE) {
		DESCR_NOTE = dESCR_NOTE;
	}

}
