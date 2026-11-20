
package it.giustizia.smp.webservice.server.polizia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiAutoritaProcedenteType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiAutoritaProcedenteType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ID_AUTORITA" type="{http://webservice.smp.giustizia.it/basetypes}IDAutoritaType"/>
 *         &lt;element name="DESCR_NUM_ACCERT" type="{http://webservice.smp.giustizia.it/basetypes}IDDescNumAccertType"/>
 *         &lt;element name="DATA_EMISSIONE" type="{http://webservice.smp.giustizia.it/basetypes}Data"/>
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
@XmlType(name = "DatiAutoritaProcedenteType", propOrder = {
    "idautorita",
    "descrnumaccert",
    "dataemissione",
    "descrnote"
})
public class DatiAutoritaProcedenteType {

    @XmlElement(name = "ID_AUTORITA", required = true)
    protected String ID_AUTORITA;
    @XmlElement(name = "DESCR_NUM_ACCERT", required = true)
    protected String DESCR_NUM_ACCERT;
    @XmlElement(name = "DATA_EMISSIONE", required = true)
    protected String DATA_EMISSIONE;
    @XmlElement(name = "DESCR_NOTE", required = true, nillable = true)
    protected String DESCR_NOTE;
    
	public String getID_AUTORITA() {
		return ID_AUTORITA;
	}
	public void setID_AUTORITA(String iD_AUTORITA) {
		ID_AUTORITA = iD_AUTORITA;
	}
	public String getDESCR_NUM_ACCERT() {
		return DESCR_NUM_ACCERT;
	}
	public void setDESCR_NUM_ACCERT(String dESCR_NUM_ACCERT) {
		DESCR_NUM_ACCERT = dESCR_NUM_ACCERT;
	}
	public String getDATA_EMISSIONE() {
		return DATA_EMISSIONE;
	}
	public void setDATA_EMISSIONE(String dATA_EMISSIONE) {
		DATA_EMISSIONE = dATA_EMISSIONE;
	}
	public String getDESCR_NOTE() {
		return DESCR_NOTE;
	}
	public void setDESCR_NOTE(String dESCR_NOTE) {
		DESCR_NOTE = dESCR_NOTE;
	}

   
}
