
package it.giustizia.smp.webservice.server.polizia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiEsitoIndagatiType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiEsitoIndagatiType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DESCR_NUM_ESITO" type="{http://webservice.smp.giustizia.it/basetypes}numericoMax16"/>
 *         &lt;element name="DATA_EMISSIONE" type="{http://webservice.smp.giustizia.it/basetypes}Data"/>
 *         &lt;element name="DESCR_NOTE" type="{http://webservice.smp.giustizia.it/basetypes}Desc120Type"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiEsitoIndagatiType", propOrder = {
    "descrnumesito",
    "dataemissione",
    "descrnote"
})
public class DatiEsitoIndagatiType {

    @XmlElement(name = "DESCR_NUM_ESITO", required = true)
    protected String DESCR_NUM_ESITO;
    @XmlElement(name = "DATA_EMISSIONE", required = true)
    protected String DATA_EMISSIONE;
    @XmlElement(name = "DESCR_NOTE", required = true, nillable = true)
    protected String DESCR_NOTE;
    
	public String getDESCR_NUM_ESITO() {
		return DESCR_NUM_ESITO;
	}
	public void setDESCR_NUM_ESITO(String dESCR_NUM_ESITO) {
		DESCR_NUM_ESITO = dESCR_NUM_ESITO;
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
