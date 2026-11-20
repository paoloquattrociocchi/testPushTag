
package it.giustizia.smp.webservice.client.polizia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DettaglioMessaggio" type="{http://trasmissione.webservice.smp.giustizia.it/polizia/type}DatiMsgErroreType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "dettaglioMessaggio"
})
@XmlRootElement(name = "PoliziaMsgErrorRequest")
public class PoliziaMsgErrorRequest {

    @XmlElement(name = "DettaglioMessaggio")
    protected DatiMsgErroreType dettaglioMessaggio;

    /**
     * Gets the value of the dettaglioMessaggio property.
     * 
     * @return
     *     possible object is
     *     {@link DatiMsgErroreType }
     *     
     */
    public DatiMsgErroreType getDettaglioMessaggio() {
        return dettaglioMessaggio;
    }

    /**
     * Sets the value of the dettaglioMessaggio property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiMsgErroreType }
     *     
     */
    public void setDettaglioMessaggio(DatiMsgErroreType value) {
        this.dettaglioMessaggio = value;
    }

}
