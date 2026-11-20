
package it.giustizia.smp.webservice.client.polizia;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiPersonaGiuridicaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiPersonaGiuridicaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NUME_PROG" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="ID_PERSONA_G" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="ID_TIPO_SOCIETA" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="DESCR_SOCIALE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ID_ISTAT_STATO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ID_ISTAT_COMUNE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_COMUNE" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "DatiPersonaGiuridicaType", namespace = "http://trasmissione.webservice.smp.giustizia.it/polizia/type", propOrder = {
    "numeprog",
    "idpersonag",
    "idtiposocieta",
    "descrsociale",
    "idistatstato",
    "idistatcomune",
    "descrcomune",
    "descrnote"
})
public class DatiPersonaGiuridicaType {

    @XmlElement(name = "NUME_PROG", required = true)
    protected BigInteger numeprog;
    @XmlElement(name = "ID_PERSONA_G", required = true)
    protected BigInteger idpersonag;
    @XmlElement(name = "ID_TIPO_SOCIETA", required = true)
    protected BigInteger idtiposocieta;
    @XmlElement(name = "DESCR_SOCIALE", required = true)
    protected String descrsociale;
    @XmlElement(name = "ID_ISTAT_STATO", required = true)
    protected String idistatstato;
    @XmlElement(name = "ID_ISTAT_COMUNE", required = true, nillable = true)
    protected String idistatcomune;
    @XmlElement(name = "DESCR_COMUNE", required = true, nillable = true)
    protected String descrcomune;
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
     * Gets the value of the idpersonag property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIDPERSONAG() {
        return idpersonag;
    }

    /**
     * Sets the value of the idpersonag property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIDPERSONAG(BigInteger value) {
        this.idpersonag = value;
    }

    /**
     * Gets the value of the idtiposocieta property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIDTIPOSOCIETA() {
        return idtiposocieta;
    }

    /**
     * Sets the value of the idtiposocieta property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIDTIPOSOCIETA(BigInteger value) {
        this.idtiposocieta = value;
    }

    /**
     * Gets the value of the descrsociale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRSOCIALE() {
        return descrsociale;
    }

    /**
     * Sets the value of the descrsociale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRSOCIALE(String value) {
        this.descrsociale = value;
    }

    /**
     * Gets the value of the idistatstato property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDISTATSTATO() {
        return idistatstato;
    }

    /**
     * Sets the value of the idistatstato property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDISTATSTATO(String value) {
        this.idistatstato = value;
    }

    /**
     * Gets the value of the idistatcomune property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDISTATCOMUNE() {
        return idistatcomune;
    }

    /**
     * Sets the value of the idistatcomune property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDISTATCOMUNE(String value) {
        this.idistatcomune = value;
    }

    /**
     * Gets the value of the descrcomune property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRCOMUNE() {
        return descrcomune;
    }

    /**
     * Sets the value of the descrcomune property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRCOMUNE(String value) {
        this.descrcomune = value;
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
