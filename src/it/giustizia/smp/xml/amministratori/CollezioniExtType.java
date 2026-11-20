
package it.giustizia.smp.xml.amministratori;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for collezioniExtType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="collezioniExtType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="collezioni" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}collezioniType"/>
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
@XmlType(name = "collezioniExtType", propOrder = {
    "collezioni",
    "datiMobileGest"
})
public class CollezioniExtType {

    @XmlElement(required = true)
    protected CollezioniType collezioni;
    protected DatiMobileGestType datiMobileGest;

    /**
     * Gets the value of the collezioni property.
     * 
     * @return
     *     possible object is
     *     {@link CollezioniType }
     *     
     */
    public CollezioniType getCollezioni() {
        return collezioni;
    }

    /**
     * Sets the value of the collezioni property.
     * 
     * @param value
     *     allowed object is
     *     {@link CollezioniType }
     *     
     */
    public void setCollezioni(CollezioniType value) {
        this.collezioni = value;
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
