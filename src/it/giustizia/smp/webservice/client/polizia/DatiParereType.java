
package it.giustizia.smp.webservice.client.polizia;

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
 *         &lt;element name="DESCR_RICH_PARERE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_NOTE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiParereType", namespace = "http://trasmissione.webservice.smp.giustizia.it/polizia/type", propOrder = {
    "descrrichparere",
    "descrnote"
})
public class DatiParereType {

    @XmlElement(name = "DESCR_RICH_PARERE", required = true)
    protected String descrrichparere;
    @XmlElement(name = "DESCR_NOTE", required = true)
    protected String descrnote;

    /**
     * Gets the value of the descrrichparere property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRRICHPARERE() {
        return descrrichparere;
    }

    /**
     * Sets the value of the descrrichparere property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRRICHPARERE(String value) {
        this.descrrichparere = value;
    }

    /**
     * Gets the value of the descrnote property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRNOTE() {
        return descrnote;
    }

    /**
     * Sets the value of the descrnote property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRNOTE(String value) {
        this.descrnote = value;
    }

}
