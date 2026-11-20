
package it.giustizia.smp.xml.amministratori;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for denaroExtType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="denaroExtType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="denaro" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}denaroType"/>
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
@XmlType(name = "denaroExtType", propOrder = {
    "denaro",
    "datiMobileGest"
})
public class DenaroExtType {

    @XmlElement(required = true)
    protected DenaroType denaro;
    protected DatiMobileGestType datiMobileGest;

    /**
     * Gets the value of the denaro property.
     * 
     * @return
     *     possible object is
     *     {@link DenaroType }
     *     
     */
    public DenaroType getDenaro() {
        return denaro;
    }

    /**
     * Sets the value of the denaro property.
     * 
     * @param value
     *     allowed object is
     *     {@link DenaroType }
     *     
     */
    public void setDenaro(DenaroType value) {
        this.denaro = value;
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
