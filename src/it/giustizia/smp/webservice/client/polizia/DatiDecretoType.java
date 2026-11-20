
package it.giustizia.smp.webservice.client.polizia;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiDecretoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiDecretoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ID_DECRETO" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="DESCR_NUM_DECRETO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DATA_EMIS_DECRETO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DATA_DEP_DECRETO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiDecretoType", namespace = "http://trasmissione.webservice.smp.giustizia.it/polizia/type", propOrder = {
    "iddecreto",
    "descrnumdecreto",
    "dataemisdecreto",
    "datadepdecreto"
})
public class DatiDecretoType {

    @XmlElement(name = "ID_DECRETO", required = true)
    protected BigInteger iddecreto;
    @XmlElement(name = "DESCR_NUM_DECRETO", required = true)
    protected String descrnumdecreto;
    @XmlElement(name = "DATA_EMIS_DECRETO", required = true)
    protected String dataemisdecreto;
    @XmlElement(name = "DATA_DEP_DECRETO", required = true)
    protected String datadepdecreto;

    /**
     * Gets the value of the iddecreto property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIDDECRETO() {
        return iddecreto;
    }

    /**
     * Sets the value of the iddecreto property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIDDECRETO(BigInteger value) {
        this.iddecreto = value;
    }

    /**
     * Gets the value of the descrnumdecreto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRNUMDECRETO() {
        return descrnumdecreto;
    }

    /**
     * Sets the value of the descrnumdecreto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRNUMDECRETO(String value) {
        this.descrnumdecreto = value;
    }

    /**
     * Gets the value of the dataemisdecreto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDATAEMISDECRETO() {
        return dataemisdecreto;
    }

    /**
     * Sets the value of the dataemisdecreto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDATAEMISDECRETO(String value) {
        this.dataemisdecreto = value;
    }

    /**
     * Gets the value of the datadepdecreto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDATADEPDECRETO() {
        return datadepdecreto;
    }

    /**
     * Sets the value of the datadepdecreto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDATADEPDECRETO(String value) {
        this.datadepdecreto = value;
    }

}
