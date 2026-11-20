
package it.giustizia.smp.webservice.client.polizia;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiPersonaFisicaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiPersonaFisicaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NUME_PROG" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="ID_PERSONA_F" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="DESCR_COGNOME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_NOME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DATA_NASCITA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ID_ISTAT_STATO_NAS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ID_ISTAT_COMUNE_NAS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_COMUNE_NAS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FLG_SESSO">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;length value="1"/>
 *               &lt;enumeration value="M"/>
 *               &lt;enumeration value="F"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ID_ISTAT_STATO_RES" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ID_ISTAT_COMUNE_RES" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_COMUNE_RES" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_INDIRIZZO_RES" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_CIVICO_RES" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_NOTE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiPersonaFisicaType", namespace = "http://trasmissione.webservice.smp.giustizia.it/polizia/type", propOrder = {
    "numeprog",
    "idpersonaf",
    "descrcognome",
    "descrnome",
    "datanascita",
    "idistatstatonas",
    "idistatcomunenas",
    "descrcomunenas",
    "flgsesso",
    "idistatstatores",
    "idistatcomuneres",
    "descrcomuneres",
    "descrindirizzores",
    "descrcivicores",
    "descrnote"
})
public class DatiPersonaFisicaType {

    @XmlElement(name = "NUME_PROG", required = true)
    protected BigInteger numeprog;
    @XmlElement(name = "ID_PERSONA_F", required = true)
    protected BigInteger idpersonaf;
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
    @XmlElement(name = "FLG_SESSO", required = true, nillable = true)
    protected String flgsesso;
    @XmlElement(name = "ID_ISTAT_STATO_RES", required = true, nillable = true)
    protected String idistatstatores;
    @XmlElement(name = "ID_ISTAT_COMUNE_RES", required = true, nillable = true)
    protected String idistatcomuneres;
    @XmlElement(name = "DESCR_COMUNE_RES", required = true, nillable = true)
    protected String descrcomuneres;
    @XmlElement(name = "DESCR_INDIRIZZO_RES", required = true, nillable = true)
    protected String descrindirizzores;
    @XmlElement(name = "DESCR_CIVICO_RES", required = true, nillable = true)
    protected String descrcivicores;
    @XmlElement(name = "DESCR_NOTE", required = true, nillable = true)
    protected String descrnote;

    /**
     * Gets the value of the numeprog property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNUMEPROG() {
        return numeprog;
    }

    /**
     * Sets the value of the numeprog property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNUMEPROG(BigInteger value) {
        this.numeprog = value;
    }

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

    /**
     * Gets the value of the flgsesso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFLGSESSO() {
        return flgsesso;
    }

    /**
     * Sets the value of the flgsesso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFLGSESSO(String value) {
        this.flgsesso = value;
    }

    /**
     * Gets the value of the idistatstatores property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDISTATSTATORES() {
        return idistatstatores;
    }

    /**
     * Sets the value of the idistatstatores property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDISTATSTATORES(String value) {
        this.idistatstatores = value;
    }

    /**
     * Gets the value of the idistatcomuneres property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDISTATCOMUNERES() {
        return idistatcomuneres;
    }

    /**
     * Sets the value of the idistatcomuneres property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDISTATCOMUNERES(String value) {
        this.idistatcomuneres = value;
    }

    /**
     * Gets the value of the descrcomuneres property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRCOMUNERES() {
        return descrcomuneres;
    }

    /**
     * Sets the value of the descrcomuneres property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRCOMUNERES(String value) {
        this.descrcomuneres = value;
    }

    /**
     * Gets the value of the descrindirizzores property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRINDIRIZZORES() {
        return descrindirizzores;
    }

    /**
     * Sets the value of the descrindirizzores property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRINDIRIZZORES(String value) {
        this.descrindirizzores = value;
    }

    /**
     * Gets the value of the descrcivicores property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRCIVICORES() {
        return descrcivicores;
    }

    /**
     * Sets the value of the descrcivicores property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRCIVICORES(String value) {
        this.descrcivicores = value;
    }

    /**
     * Gets the value of the descrnote property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRNOTE() {
        return descrnote;
    }

    /**
     * Sets the value of the descrnote property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRNOTE(String value) {
        this.descrnote = value;
    }

}
