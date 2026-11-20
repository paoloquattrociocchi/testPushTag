
package it.giustizia.smp.xml.amministratori;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for immobileExtType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="immobileExtType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="immobile" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}immobileType"/>
 *         &lt;element name="datiImmobileGest" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}datiImmobileGestType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "immobileExtType", propOrder = {
    "immobile",
    "datiImmobileGest"
})
public class ImmobileExtType {

    @XmlElement(required = true)
    protected ImmobileType immobile;
    protected DatiImmobileGestType datiImmobileGest;

    /**
     * Gets the value of the immobile property.
     * 
     * @return
     *     possible object is
     *     {@link ImmobileType }
     *     
     */
    public ImmobileType getImmobile() {
        return immobile;
    }

    /**
     * Sets the value of the immobile property.
     * 
     * @param value
     *     allowed object is
     *     {@link ImmobileType }
     *     
     */
    public void setImmobile(ImmobileType value) {
        this.immobile = value;
    }

    /**
     * Gets the value of the datiImmobileGest property.
     * 
     * @return
     *     possible object is
     *     {@link DatiImmobileGestType }
     *     
     */
    public DatiImmobileGestType getDatiImmobileGest() {
        return datiImmobileGest;
    }

    /**
     * Sets the value of the datiImmobileGest property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiImmobileGestType }
     *     
     */
    public void setDatiImmobileGest(DatiImmobileGestType value) {
        this.datiImmobileGest = value;
    }

}
