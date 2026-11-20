
package it.giustizia.smp.webservice.client.polizia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiSentenzaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiSentenzaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ID_SENTENZA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_NUM_SENTENZA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DATA_EMIS_SENTENZA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DATA_DEP_SENTENZA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiSentenzaType", namespace = "http://trasmissione.webservice.smp.giustizia.it/polizia/type", propOrder = {
    "idsentenza",
    "descrnumsentenza",
    "dataemissentenza",
    "datadepsentenza"
})
public class DatiSentenzaType {

    @XmlElement(name = "ID_SENTENZA", required = true)
    protected String idsentenza;
    @XmlElement(name = "DESCR_NUM_SENTENZA", required = true)
    protected String descrnumsentenza;
    @XmlElement(name = "DATA_EMIS_SENTENZA", required = true)
    protected String dataemissentenza;
    @XmlElement(name = "DATA_DEP_SENTENZA", required = true)
    protected String datadepsentenza;

    /**
     * Gets the value of the idsentenza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDSENTENZA() {
        return idsentenza;
    }

    /**
     * Sets the value of the idsentenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDSENTENZA(String value) {
        this.idsentenza = value;
    }

    /**
     * Gets the value of the descrnumsentenza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRNUMSENTENZA() {
        return descrnumsentenza;
    }

    /**
     * Sets the value of the descrnumsentenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRNUMSENTENZA(String value) {
        this.descrnumsentenza = value;
    }

    /**
     * Gets the value of the dataemissentenza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDATAEMISSENTENZA() {
        return dataemissentenza;
    }

    /**
     * Sets the value of the dataemissentenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDATAEMISSENTENZA(String value) {
        this.dataemissentenza = value;
    }

    /**
     * Gets the value of the datadepsentenza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDATADEPSENTENZA() {
        return datadepsentenza;
    }

    /**
     * Sets the value of the datadepsentenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDATADEPSENTENZA(String value) {
        this.datadepsentenza = value;
    }

}
