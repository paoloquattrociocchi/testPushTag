
package it.giustizia.smp.xml.amministratori;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for denaroContanteExtType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="denaroContanteExtType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="denaroContante" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}denaroContanteType"/>
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
@XmlType(name = "denaroContanteExtType", propOrder = {
    "denaroContante",
    "datiMobileGest"
})
public class DenaroContanteExtType {

    @XmlElement(required = true)
    protected DenaroContanteType denaroContante;
    protected DatiMobileGestType datiMobileGest;

    /**
     * Gets the value of the denaroContante property.
     * 
     * @return
     *     possible object is
     *     {@link DenaroContanteType }
     *     
     */
    public DenaroContanteType getDenaroContante() {
        return denaroContante;
    }

    /**
     * Sets the value of the denaroContante property.
     * 
     * @param value
     *     allowed object is
     *     {@link DenaroContanteType }
     *     
     */
    public void setDenaroContante(DenaroContanteType value) {
        this.denaroContante = value;
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
