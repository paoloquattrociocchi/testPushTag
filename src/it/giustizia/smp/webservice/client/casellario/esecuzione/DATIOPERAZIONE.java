
package it.giustizia.smp.webservice.client.casellario.esecuzione;

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
 *         &lt;element name="ENTITA" type="{http://it/mig/sippi/service/inviaProvvedimentoEsecuzione/type}ENTITA"/>
 *         &lt;element name="OPERAZIONE" type="{http://it/mig/sippi/service/inviaProvvedimentoEsecuzione/type}OPERAZIONE"/>
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
    "entita",
    "operazione"
})
@XmlRootElement(name = "DATI_OPERAZIONE")
public class DATIOPERAZIONE {

    @XmlElement(name = "ENTITA", required = true)
    protected ENTITA entita;
    @XmlElement(name = "OPERAZIONE", required = true)
    protected OPERAZIONE operazione;

    /**
     * Gets the value of the entita property.
     * 
     * @return
     *     possible object is
     *     {@link ENTITA }
     *     
     */
    public ENTITA getENTITA() {
        return entita;
    }

    /**
     * Sets the value of the entita property.
     * 
     * @param value
     *     allowed object is
     *     {@link ENTITA }
     *     
     */
    public void setENTITA(ENTITA value) {
        this.entita = value;
    }

    /**
     * Gets the value of the operazione property.
     * 
     * @return
     *     possible object is
     *     {@link OPERAZIONE }
     *     
     */
    public OPERAZIONE getOPERAZIONE() {
        return operazione;
    }

    /**
     * Sets the value of the operazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link OPERAZIONE }
     *     
     */
    public void setOPERAZIONE(OPERAZIONE value) {
        this.operazione = value;
    }

}
