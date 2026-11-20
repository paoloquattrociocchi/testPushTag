
package it.giustizia.smp.xml.amministratori;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for immaterialeExtType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="immaterialeExtType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="immateriale" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}immaterialeType"/>
 *         &lt;element name="datiImmaterialeGest" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}datiImmaterialeGestType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "immaterialeExtType", propOrder = {
    "immateriale",
    "datiImmaterialeGest"
})
public class ImmaterialeExtType {

    @XmlElement(required = true)
    protected ImmaterialeType immateriale;
    protected DatiImmaterialeGestType datiImmaterialeGest;

    /**
     * Gets the value of the immateriale property.
     * 
     * @return
     *     possible object is
     *     {@link ImmaterialeType }
     *     
     */
    public ImmaterialeType getImmateriale() {
        return immateriale;
    }

    /**
     * Sets the value of the immateriale property.
     * 
     * @param value
     *     allowed object is
     *     {@link ImmaterialeType }
     *     
     */
    public void setImmateriale(ImmaterialeType value) {
        this.immateriale = value;
    }

    /**
     * Gets the value of the datiImmaterialeGest property.
     * 
     * @return
     *     possible object is
     *     {@link DatiImmaterialeGestType }
     *     
     */
    public DatiImmaterialeGestType getDatiImmaterialeGest() {
        return datiImmaterialeGest;
    }

    /**
     * Sets the value of the datiImmaterialeGest property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiImmaterialeGestType }
     *     
     */
    public void setDatiImmaterialeGest(DatiImmaterialeGestType value) {
        this.datiImmaterialeGest = value;
    }

}
