
package it.giustizia.smp.xml.amministratori;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for finanziarioExtType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="finanziarioExtType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="finanziario" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}finanziarioType"/>
 *         &lt;element name="datiFinanzGest" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}datiFinanzGestType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "finanziarioExtType", propOrder = {
    "finanziario",
    "datiFinanzGest"
})
@XmlSeeAlso({
    it.giustizia.smp.xml.amministratori.BeneExtType.DatiSpecificiBene.Finanziario.class
})
public class FinanziarioExtType {

    @XmlElement(required = true)
    protected FinanziarioType finanziario;
    protected DatiFinanzGestType datiFinanzGest;

    /**
     * Gets the value of the finanziario property.
     * 
     * @return
     *     possible object is
     *     {@link FinanziarioType }
     *     
     */
    public FinanziarioType getFinanziario() {
        return finanziario;
    }

    /**
     * Sets the value of the finanziario property.
     * 
     * @param value
     *     allowed object is
     *     {@link FinanziarioType }
     *     
     */
    public void setFinanziario(FinanziarioType value) {
        this.finanziario = value;
    }

    /**
     * Gets the value of the datiFinanzGest property.
     * 
     * @return
     *     possible object is
     *     {@link DatiFinanzGestType }
     *     
     */
    public DatiFinanzGestType getDatiFinanzGest() {
        return datiFinanzGest;
    }

    /**
     * Sets the value of the datiFinanzGest property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiFinanzGestType }
     *     
     */
    public void setDatiFinanzGest(DatiFinanzGestType value) {
        this.datiFinanzGest = value;
    }

}
