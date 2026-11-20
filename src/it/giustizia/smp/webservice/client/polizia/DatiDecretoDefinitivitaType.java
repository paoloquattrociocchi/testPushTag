
package it.giustizia.smp.webservice.client.polizia;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiDecretoDefinitivitaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiDecretoDefinitivitaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ID_DECRETO" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="DESCR_NUM_DECRETO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiDecretoDefinitivitaType", namespace = "http://trasmissione.webservice.smp.giustizia.it/polizia/type", propOrder = {
    "iddecreto",
    "descrnumdecreto"
})
public class DatiDecretoDefinitivitaType {

    @XmlElement(name = "ID_DECRETO", required = true)
    protected BigInteger iddecreto;
    @XmlElement(name = "DESCR_NUM_DECRETO", required = true)
    protected String descrnumdecreto;

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

}
