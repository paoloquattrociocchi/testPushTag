
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
 *         &lt;element ref="{http://it/mig/sippi/service/inviaProvvedimentoEsecuzione/type}PROVVEDIMENTO_ESECUZIONE_MP"/>
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
    "provvedimentoesecuzionemp"
})
@XmlRootElement(name = "PROCEDIMENTO")
public class PROCEDIMENTO {

    @XmlElement(name = "PROVVEDIMENTO_ESECUZIONE_MP")
    protected PROVVEDIMENTOESECUZIONEMP provvedimentoesecuzionemp;

    /**
     * Gets the value of the provvedimentoesecuzionemp property.
     * 
     * @return
     *     possible object is
     *     {@link PROVVEDIMENTOESECUZIONEMP }
     *     
     */
    public PROVVEDIMENTOESECUZIONEMP getPROVVEDIMENTOESECUZIONEMP() {
        return provvedimentoesecuzionemp;
    }

    /**
     * Sets the value of the provvedimentoesecuzionemp property.
     * 
     * @param value
     *     allowed object is
     *     {@link PROVVEDIMENTOESECUZIONEMP }
     *     
     */
    public void setPROVVEDIMENTOESECUZIONEMP(PROVVEDIMENTOESECUZIONEMP value) {
        this.provvedimentoesecuzionemp = value;
    }

}
