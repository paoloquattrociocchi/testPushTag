
package it.giustizia.smp.webservice.client.casellario.esecuzione;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="K_A_SIPPI">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               &lt;totalDigits value="15"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="K_P_P_SIPPI">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               &lt;totalDigits value="15"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="K_P_E_SIPPI">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               &lt;totalDigits value="9"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="K_A_130_SIPPI" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               &lt;totalDigits value="9"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "kasippi",
    "kppsippi",
    "kpesippi",
    "ka130SIPPI"
})
@XmlRootElement(name = "CHIAVI")
public class CHIAVI {

    @XmlElement(name = "K_A_SIPPI", required = true)
    protected BigInteger kasippi;
    @XmlElement(name = "K_P_P_SIPPI", required = true)
    protected BigInteger kppsippi;
    @XmlElement(name = "K_P_E_SIPPI", required = true)
    protected BigInteger kpesippi;
    @XmlElement(name = "K_A_130_SIPPI")
    protected BigInteger ka130SIPPI;

    /**
     * Gets the value of the kasippi property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKASIPPI() {
        return kasippi;
    }

    /**
     * Sets the value of the kasippi property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKASIPPI(BigInteger value) {
        this.kasippi = value;
    }

    /**
     * Gets the value of the kppsippi property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKPPSIPPI() {
        return kppsippi;
    }

    /**
     * Sets the value of the kppsippi property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKPPSIPPI(BigInteger value) {
        this.kppsippi = value;
    }

    /**
     * Gets the value of the kpesippi property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKPESIPPI() {
        return kpesippi;
    }

    /**
     * Sets the value of the kpesippi property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKPESIPPI(BigInteger value) {
        this.kpesippi = value;
    }

    /**
     * Gets the value of the ka130SIPPI property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKA130SIPPI() {
        return ka130SIPPI;
    }

    /**
     * Sets the value of the ka130SIPPI property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKA130SIPPI(BigInteger value) {
        this.ka130SIPPI = value;
    }

}
