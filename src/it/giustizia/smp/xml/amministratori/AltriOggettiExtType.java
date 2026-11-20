
package it.giustizia.smp.xml.amministratori;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for altriOggettiExtType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="altriOggettiExtType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="altriOggetti" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}altriOggettiType"/>
 *         &lt;element name="datiMobileGest" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}datiMobileGestType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "altriOggettiExtType", propOrder = {
    "altriOggetti",
    "datiMobileGest"
})
public class AltriOggettiExtType {

    @XmlElement(required = true)
    protected AltriOggettiType altriOggetti;
    protected DatiMobileGestType datiMobileGest;

    /**
     * Gets the value of the altriOggetti property.
     * 
     * @return
     *     possible object is
     *     {@link AltriOggettiType }
     *     
     */
    public AltriOggettiType getAltriOggetti() {
        return altriOggetti;
    }

    /**
     * Sets the value of the altriOggetti property.
     * 
     * @param value
     *     allowed object is
     *     {@link AltriOggettiType }
     *     
     */
    public void setAltriOggetti(AltriOggettiType value) {
        this.altriOggetti = value;
    }

    /**
     * Gets the value of the datiMobileGest property.
     * 
     * @return
     *     possible object is
     *     {@link DatiMobileGestType }
     *     
     */
    public DatiMobileGestType getDatiMobileGest() {
        return datiMobileGest;
    }

    /**
     * Sets the value of the datiMobileGest property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiMobileGestType }
     *     
     */
    public void setDatiMobileGest(DatiMobileGestType value) {
        this.datiMobileGest = value;
    }

}
