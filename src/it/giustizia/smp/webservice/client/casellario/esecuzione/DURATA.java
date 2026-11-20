
package it.giustizia.smp.webservice.client.casellario.esecuzione;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DURATA complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DURATA">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ANNI_DURATA" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               &lt;totalDigits value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="MESI_DURATA" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               &lt;totalDigits value="3"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="GIORNI_DURATA">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               &lt;totalDigits value="3"/>
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
@XmlType(name = "DURATA", propOrder = {
    "annidurata",
    "mesidurata",
    "giornidurata"
})
public class DURATA {

    @XmlElement(name = "ANNI_DURATA")
    protected BigInteger annidurata;
    @XmlElement(name = "MESI_DURATA")
    protected BigInteger mesidurata;
    @XmlElement(name = "GIORNI_DURATA", required = true)
    protected BigInteger giornidurata;

    /**
     * Gets the value of the annidurata property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getANNIDURATA() {
        return annidurata;
    }

    /**
     * Sets the value of the annidurata property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setANNIDURATA(BigInteger value) {
        this.annidurata = value;
    }

    /**
     * Gets the value of the mesidurata property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMESIDURATA() {
        return mesidurata;
    }

    /**
     * Sets the value of the mesidurata property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMESIDURATA(BigInteger value) {
        this.mesidurata = value;
    }

    /**
     * Gets the value of the giornidurata property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getGIORNIDURATA() {
        return giornidurata;
    }

    /**
     * Sets the value of the giornidurata property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setGIORNIDURATA(BigInteger value) {
        this.giornidurata = value;
    }

}
