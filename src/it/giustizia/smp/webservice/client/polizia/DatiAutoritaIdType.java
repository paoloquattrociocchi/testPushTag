
package it.giustizia.smp.webservice.client.polizia;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiAutoritaIdType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiAutoritaIdType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ID_AUTORITA" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiAutoritaIdType", namespace = "http://trasmissione.webservice.smp.giustizia.it/polizia/type", propOrder = {
    "idautorita"
})
public class DatiAutoritaIdType {

    @XmlElement(name = "ID_AUTORITA", required = true)
    protected BigInteger idautorita;

    /**
     * Gets the value of the idautorita property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIDAUTORITA() {
        return idautorita;
    }

    /**
     * Sets the value of the idautorita property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIDAUTORITA(BigInteger value) {
        this.idautorita = value;
    }

}
