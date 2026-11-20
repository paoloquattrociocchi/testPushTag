
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
 *         &lt;element ref="{http://it/mig/sippi/service/inviaProvvedimentoEsecuzione/type}DATI_ANAGRAFICI"/>
 *         &lt;element name="CERTIFICATO_CONTROLLO" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
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
    "datianagrafici",
    "certificatocontrollo"
})
@XmlRootElement(name = "OMONIMO")
public class OMONIMO {

    @XmlElement(name = "DATI_ANAGRAFICI", required = true)
    protected DATIANAGRAFICI datianagrafici;
    @XmlElement(name = "CERTIFICATO_CONTROLLO", required = true)
    protected byte[] certificatocontrollo;

    /**
     * Gets the value of the datianagrafici property.
     * 
     * @return
     *     possible object is
     *     {@link DATIANAGRAFICI }
     *     
     */
    public DATIANAGRAFICI getDATIANAGRAFICI() {
        return datianagrafici;
    }

    /**
     * Sets the value of the datianagrafici property.
     * 
     * @param value
     *     allowed object is
     *     {@link DATIANAGRAFICI }
     *     
     */
    public void setDATIANAGRAFICI(DATIANAGRAFICI value) {
        this.datianagrafici = value;
    }

    /**
     * Gets the value of the certificatocontrollo property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getCERTIFICATOCONTROLLO() {
        return certificatocontrollo;
    }

    /**
     * Sets the value of the certificatocontrollo property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setCERTIFICATOCONTROLLO(byte[] value) {
        this.certificatocontrollo = ((byte[]) value);
    }

}
