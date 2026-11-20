
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
 *         &lt;element ref="{http://it/mig/sippi/service/inviaProvvedimentoEsecuzione/type}DATI_UFFICIO"/>
 *         &lt;element name="COGNOME_UTENTE">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="35"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="NOME_UTENTE">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="35"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="USERNAME_SIPPI">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="30"/>
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
    "datiufficio",
    "cognomeutente",
    "nomeutente",
    "usernamesippi"
})
@XmlRootElement(name = "DATI_UTENTE")
public class DATIUTENTE {

    @XmlElement(name = "DATI_UFFICIO", required = true)
    protected DATIUFFICIO datiufficio;
    @XmlElement(name = "COGNOME_UTENTE", required = true)
    protected String cognomeutente;
    @XmlElement(name = "NOME_UTENTE", required = true)
    protected String nomeutente;
    @XmlElement(name = "USERNAME_SIPPI", required = true)
    protected String usernamesippi;

    /**
     * Gets the value of the datiufficio property.
     * 
     * @return
     *     possible object is
     *     {@link DATIUFFICIO }
     *     
     */
    public DATIUFFICIO getDATIUFFICIO() {
        return datiufficio;
    }

    /**
     * Sets the value of the datiufficio property.
     * 
     * @param value
     *     allowed object is
     *     {@link DATIUFFICIO }
     *     
     */
    public void setDATIUFFICIO(DATIUFFICIO value) {
        this.datiufficio = value;
    }

    /**
     * Gets the value of the cognomeutente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCOGNOMEUTENTE() {
        return cognomeutente;
    }

    /**
     * Sets the value of the cognomeutente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCOGNOMEUTENTE(String value) {
        this.cognomeutente = value;
    }

    /**
     * Gets the value of the nomeutente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOMEUTENTE() {
        return nomeutente;
    }

    /**
     * Sets the value of the nomeutente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOMEUTENTE(String value) {
        this.nomeutente = value;
    }

    /**
     * Gets the value of the usernamesippi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSERNAMESIPPI() {
        return usernamesippi;
    }

    /**
     * Sets the value of the usernamesippi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSERNAMESIPPI(String value) {
        this.usernamesippi = value;
    }

}
