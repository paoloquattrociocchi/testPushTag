
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
 *       &lt;choice>
 *         &lt;element ref="{http://it/mig/sippi/service/inviaProvvedimentoEsecuzione/type}DATI_CHIAMATA_TRASFERIMENTO"/>
 *         &lt;element ref="{http://it/mig/sippi/service/inviaProvvedimentoEsecuzione/type}DATI_RISPOSTA_TRASFERIMENTO"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "datichiamatatrasferimento",
    "datirispostatrasferimento"
})
@XmlRootElement(name = "TRASFERIMENTO")
public class TRASFERIMENTO {

    @XmlElement(name = "DATI_CHIAMATA_TRASFERIMENTO")
    protected DATICHIAMATATRASFERIMENTO datichiamatatrasferimento;
    @XmlElement(name = "DATI_RISPOSTA_TRASFERIMENTO")
    protected DATIRISPOSTATRASFERIMENTO datirispostatrasferimento;

    /**
     * Gets the value of the datichiamatatrasferimento property.
     * 
     * @return
     *     possible object is
     *     {@link DATICHIAMATATRASFERIMENTO }
     *     
     */
    public DATICHIAMATATRASFERIMENTO getDATICHIAMATATRASFERIMENTO() {
        return datichiamatatrasferimento;
    }

    /**
     * Sets the value of the datichiamatatrasferimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link DATICHIAMATATRASFERIMENTO }
     *     
     */
    public void setDATICHIAMATATRASFERIMENTO(DATICHIAMATATRASFERIMENTO value) {
        this.datichiamatatrasferimento = value;
    }

    /**
     * Gets the value of the datirispostatrasferimento property.
     * 
     * @return
     *     possible object is
     *     {@link DATIRISPOSTATRASFERIMENTO }
     *     
     */
    public DATIRISPOSTATRASFERIMENTO getDATIRISPOSTATRASFERIMENTO() {
        return datirispostatrasferimento;
    }

    /**
     * Sets the value of the datirispostatrasferimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link DATIRISPOSTATRASFERIMENTO }
     *     
     */
    public void setDATIRISPOSTATRASFERIMENTO(DATIRISPOSTATRASFERIMENTO value) {
        this.datirispostatrasferimento = value;
    }

}
