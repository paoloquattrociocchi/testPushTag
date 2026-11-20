
package it.giustizia.smp.xml.amministratori;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for veicoloExtType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="veicoloExtType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="veicolo" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}veicoloType"/>
 *         &lt;element name="datiVeicoloGest" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}datiVeicoloGestType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "veicoloExtType", propOrder = {
    "veicolo",
    "datiVeicoloGest"
})
public class VeicoloExtType {

    @XmlElement(required = true)
    protected VeicoloType veicolo;
    protected DatiVeicoloGestType datiVeicoloGest;

    /**
     * Gets the value of the veicolo property.
     * 
     * @return
     *     possible object is
     *     {@link VeicoloType }
     *     
     */
    public VeicoloType getVeicolo() {
        return veicolo;
    }

    /**
     * Sets the value of the veicolo property.
     * 
     * @param value
     *     allowed object is
     *     {@link VeicoloType }
     *     
     */
    public void setVeicolo(VeicoloType value) {
        this.veicolo = value;
    }

    /**
     * Gets the value of the datiVeicoloGest property.
     * 
     * @return
     *     possible object is
     *     {@link DatiVeicoloGestType }
     *     
     */
    public DatiVeicoloGestType getDatiVeicoloGest() {
        return datiVeicoloGest;
    }

    /**
     * Sets the value of the datiVeicoloGest property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiVeicoloGestType }
     *     
     */
    public void setDatiVeicoloGest(DatiVeicoloGestType value) {
        this.datiVeicoloGest = value;
    }

}
