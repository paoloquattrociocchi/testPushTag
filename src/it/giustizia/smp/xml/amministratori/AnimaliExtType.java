
package it.giustizia.smp.xml.amministratori;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for animaliExtType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="animaliExtType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="animali" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}animaliType"/>
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
@XmlType(name = "animaliExtType", propOrder = {
    "animali",
    "datiMobileGest"
})
public class AnimaliExtType {

    @XmlElement(required = true)
    protected AnimaliType animali;
    protected DatiMobileGestType datiMobileGest;

    /**
     * Gets the value of the animali property.
     * 
     * @return
     *     possible object is
     *     {@link AnimaliType }
     *     
     */
    public AnimaliType getAnimali() {
        return animali;
    }

    /**
     * Sets the value of the animali property.
     * 
     * @param value
     *     allowed object is
     *     {@link AnimaliType }
     *     
     */
    public void setAnimali(AnimaliType value) {
        this.animali = value;
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
