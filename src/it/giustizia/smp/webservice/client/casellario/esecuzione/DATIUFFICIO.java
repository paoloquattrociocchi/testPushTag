
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
 *         &lt;element name="CODICE_TIPO_UFFICIO" type="{http://it/mig/sippi/service/inviaProvvedimentoEsecuzione/type}TIPO_UFFICIO"/>
 *         &lt;element name="CODICE_SEDE_UFFICIO">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="6"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
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
    "codicetipoufficio",
    "codicesedeufficio"
})
@XmlRootElement(name = "DATI_UFFICIO")
public class DATIUFFICIO {

    @XmlElement(name = "CODICE_TIPO_UFFICIO", required = true)
    protected String codicetipoufficio;
    @XmlElement(name = "CODICE_SEDE_UFFICIO", required = true)
    protected String codicesedeufficio;

    /**
     * Gets the value of the codicetipoufficio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODICETIPOUFFICIO() {
        return codicetipoufficio;
    }

    /**
     * Sets the value of the codicetipoufficio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODICETIPOUFFICIO(String value) {
        this.codicetipoufficio = value;
    }

    /**
     * Gets the value of the codicesedeufficio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODICESEDEUFFICIO() {
        return codicesedeufficio;
    }

    /**
     * Sets the value of the codicesedeufficio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODICESEDEUFFICIO(String value) {
        this.codicesedeufficio = value;
    }

}
