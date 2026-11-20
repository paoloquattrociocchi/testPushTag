
package it.giustizia.smp.webservice.client.polizia;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VariazioneAnagraficiType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VariazioneAnagraficiType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ID_PERSONA_F" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="DESCR_COGNOME_OLD" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_NOME_OLD" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DATA_NASCITA_OLD" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ID_ISTAT_STATO_NAS_OLD" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ID_ISTAT_COMUNE_NAS_OLD" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_COMUNE_NAS_OLD" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_COGNOME_NEW" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_NOME_NEW" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DATA_NASCITA_NEW" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ID_ISTAT_STATO_NAS_NEW" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ID_ISTAT_COMUNE_NAS_NEW" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_COMUNE_NAS_NEW" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VariazioneAnagraficiType", namespace = "http://trasmissione.webservice.smp.giustizia.it/polizia/type", propOrder = {
    "idpersonaf",
    "descrcognomeold",
    "descrnomeold",
    "datanascitaold",
    "idistatstatonasold",
    "idistatcomunenasold",
    "descrcomunenasold",
    "descrcognomenew",
    "descrnomenew",
    "datanascitanew",
    "idistatstatonasnew",
    "idistatcomunenasnew",
    "descrcomunenasnew"
})
public class VariazioneAnagraficiType {

    @XmlElement(name = "ID_PERSONA_F", required = true)
    protected BigInteger idpersonaf;
    @XmlElement(name = "DESCR_COGNOME_OLD", required = true)
    protected String descrcognomeold;
    @XmlElement(name = "DESCR_NOME_OLD", required = true)
    protected String descrnomeold;
    @XmlElement(name = "DATA_NASCITA_OLD", required = true, nillable = true)
    protected String datanascitaold;
    @XmlElement(name = "ID_ISTAT_STATO_NAS_OLD", required = true, nillable = true)
    protected String idistatstatonasold;
    @XmlElement(name = "ID_ISTAT_COMUNE_NAS_OLD", required = true, nillable = true)
    protected String idistatcomunenasold;
    @XmlElement(name = "DESCR_COMUNE_NAS_OLD", required = true, nillable = true)
    protected String descrcomunenasold;
    @XmlElement(name = "DESCR_COGNOME_NEW", required = true)
    protected String descrcognomenew;
    @XmlElement(name = "DESCR_NOME_NEW", required = true)
    protected String descrnomenew;
    @XmlElement(name = "DATA_NASCITA_NEW", required = true, nillable = true)
    protected String datanascitanew;
    @XmlElement(name = "ID_ISTAT_STATO_NAS_NEW", required = true, nillable = true)
    protected String idistatstatonasnew;
    @XmlElement(name = "ID_ISTAT_COMUNE_NAS_NEW", required = true, nillable = true)
    protected String idistatcomunenasnew;
    @XmlElement(name = "DESCR_COMUNE_NAS_NEW", required = true, nillable = true)
    protected String descrcomunenasnew;

    /**
     * Gets the value of the idpersonaf property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIDPERSONAF() {
        return idpersonaf;
    }

    /**
     * Sets the value of the idpersonaf property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIDPERSONAF(BigInteger value) {
        this.idpersonaf = value;
    }

    /**
     * Gets the value of the descrcognomeold property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRCOGNOMEOLD() {
        return descrcognomeold;
    }

    /**
     * Sets the value of the descrcognomeold property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRCOGNOMEOLD(String value) {
        this.descrcognomeold = value;
    }

    /**
     * Gets the value of the descrnomeold property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRNOMEOLD() {
        return descrnomeold;
    }

    /**
     * Sets the value of the descrnomeold property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRNOMEOLD(String value) {
        this.descrnomeold = value;
    }

    /**
     * Gets the value of the datanascitaold property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDATANASCITAOLD() {
        return datanascitaold;
    }

    /**
     * Sets the value of the datanascitaold property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDATANASCITAOLD(String value) {
        this.datanascitaold = value;
    }

    /**
     * Gets the value of the idistatstatonasold property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDISTATSTATONASOLD() {
        return idistatstatonasold;
    }

    /**
     * Sets the value of the idistatstatonasold property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDISTATSTATONASOLD(String value) {
        this.idistatstatonasold = value;
    }

    /**
     * Gets the value of the idistatcomunenasold property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDISTATCOMUNENASOLD() {
        return idistatcomunenasold;
    }

    /**
     * Sets the value of the idistatcomunenasold property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDISTATCOMUNENASOLD(String value) {
        this.idistatcomunenasold = value;
    }

    /**
     * Gets the value of the descrcomunenasold property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRCOMUNENASOLD() {
        return descrcomunenasold;
    }

    /**
     * Sets the value of the descrcomunenasold property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRCOMUNENASOLD(String value) {
        this.descrcomunenasold = value;
    }

    /**
     * Gets the value of the descrcognomenew property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRCOGNOMENEW() {
        return descrcognomenew;
    }

    /**
     * Sets the value of the descrcognomenew property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRCOGNOMENEW(String value) {
        this.descrcognomenew = value;
    }

    /**
     * Gets the value of the descrnomenew property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRNOMENEW() {
        return descrnomenew;
    }

    /**
     * Sets the value of the descrnomenew property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRNOMENEW(String value) {
        this.descrnomenew = value;
    }

    /**
     * Gets the value of the datanascitanew property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDATANASCITANEW() {
        return datanascitanew;
    }

    /**
     * Sets the value of the datanascitanew property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDATANASCITANEW(String value) {
        this.datanascitanew = value;
    }

    /**
     * Gets the value of the idistatstatonasnew property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDISTATSTATONASNEW() {
        return idistatstatonasnew;
    }

    /**
     * Sets the value of the idistatstatonasnew property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDISTATSTATONASNEW(String value) {
        this.idistatstatonasnew = value;
    }

    /**
     * Gets the value of the idistatcomunenasnew property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDISTATCOMUNENASNEW() {
        return idistatcomunenasnew;
    }

    /**
     * Sets the value of the idistatcomunenasnew property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDISTATCOMUNENASNEW(String value) {
        this.idistatcomunenasnew = value;
    }

    /**
     * Gets the value of the descrcomunenasnew property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRCOMUNENASNEW() {
        return descrcomunenasnew;
    }

    /**
     * Sets the value of the descrcomunenasnew property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRCOMUNENASNEW(String value) {
        this.descrcomunenasnew = value;
    }

}
