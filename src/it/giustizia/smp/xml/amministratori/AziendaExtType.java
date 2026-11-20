
package it.giustizia.smp.xml.amministratori;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for aziendaExtType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="aziendaExtType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="azienda" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}aziendaType"/>
 *         &lt;element name="datiAziendaGest" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}datiAziendaGestType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "aziendaExtType", propOrder = {
    "azienda",
    "datiAziendaGest"
})
public class AziendaExtType {

    @XmlElement(required = true)
    protected AziendaType azienda;
    protected DatiAziendaGestType datiAziendaGest;

    /**
     * Gets the value of the azienda property.
     * 
     * @return
     *     possible object is
     *     {@link AziendaType }
     *     
     */
    public AziendaType getAzienda() {
        return azienda;
    }

    /**
     * Sets the value of the azienda property.
     * 
     * @param value
     *     allowed object is
     *     {@link AziendaType }
     *     
     */
    public void setAzienda(AziendaType value) {
        this.azienda = value;
    }

    /**
     * Gets the value of the datiAziendaGest property.
     * 
     * @return
     *     possible object is
     *     {@link DatiAziendaGestType }
     *     
     */
    public DatiAziendaGestType getDatiAziendaGest() {
        return datiAziendaGest;
    }

    /**
     * Sets the value of the datiAziendaGest property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiAziendaGestType }
     *     
     */
    public void setDatiAziendaGest(DatiAziendaGestType value) {
        this.datiAziendaGest = value;
    }

}
