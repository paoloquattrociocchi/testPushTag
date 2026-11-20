
package it.giustizia.smp.webservice.client.polizia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiAnagraficiPersonaFisicaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiAnagraficiPersonaFisicaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DESCR_COGNOME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_NOME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DATA_NASCITA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ID_ISTAT_STATO_NAS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ID_ISTAT_COMUNE_NAS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_COMUNE_NAS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiAnagraficiPersonaFisicaType", namespace = "http://trasmissione.webservice.smp.giustizia.it/polizia/type", propOrder = {
    "descrcognome",
    "descrnome",
    "datanascita",
    "idistatstatonas",
    "idistatcomunenas",
    "descrcomunenas"
})
public class DatiAnagraficiPersonaFisicaType {

    @XmlElement(name = "DESCR_COGNOME", required = true)
    protected String descrcognome;
    @XmlElement(name = "DESCR_NOME", required = true)
    protected String descrnome;
    @XmlElement(name = "DATA_NASCITA", required = true, nillable = true)
    protected String datanascita;
    @XmlElement(name = "ID_ISTAT_STATO_NAS", required = true, nillable = true)
    protected String idistatstatonas;
    @XmlElement(name = "ID_ISTAT_COMUNE_NAS", required = true, nillable = true)
    protected String idistatcomunenas;
    @XmlElement(name = "DESCR_COMUNE_NAS", required = true, nillable = true)
    protected String descrcomunenas;

    /**
     * Gets the value of the descrcognome property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRCOGNOME() {
        return descrcognome;
    }

    /**
     * Sets the value of the descrcognome property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRCOGNOME(String value) {
        this.descrcognome = value;
    }

    /**
     * Gets the value of the descrnome property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRNOME() {
        return descrnome;
    }

    /**
     * Sets the value of the descrnome property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRNOME(String value) {
        this.descrnome = value;
    }

    /**
     * Gets the value of the datanascita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDATANASCITA() {
        return datanascita;
    }

    /**
     * Sets the value of the datanascita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDATANASCITA(String value) {
        this.datanascita = value;
    }

    /**
     * Gets the value of the idistatstatonas property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDISTATSTATONAS() {
        return idistatstatonas;
    }

    /**
     * Sets the value of the idistatstatonas property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDISTATSTATONAS(String value) {
        this.idistatstatonas = value;
    }

    /**
     * Gets the value of the idistatcomunenas property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDISTATCOMUNENAS() {
        return idistatcomunenas;
    }

    /**
     * Sets the value of the idistatcomunenas property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDISTATCOMUNENAS(String value) {
        this.idistatcomunenas = value;
    }

    /**
     * Gets the value of the descrcomunenas property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRCOMUNENAS() {
        return descrcomunenas;
    }

    /**
     * Sets the value of the descrcomunenas property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRCOMUNENAS(String value) {
        this.descrcomunenas = value;
    }

}
