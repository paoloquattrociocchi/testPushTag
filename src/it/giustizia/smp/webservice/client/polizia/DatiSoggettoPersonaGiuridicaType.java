
package it.giustizia.smp.webservice.client.polizia;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiSoggettoPersonaGiuridicaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiSoggettoPersonaGiuridicaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NUME_PROG" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="ID_RUOLO">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;length value="1"/>
 *               &lt;enumeration value="D"/>
 *               &lt;enumeration value="T"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ID_PERSONA_G" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="ID_TIPO_PREV">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;length value="1"/>
 *               &lt;enumeration value="1"/>
 *               &lt;enumeration value="2"/>
 *               &lt;enumeration value="3"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ID_NORMATIVA_RIF" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="ID_TIPO_SOCIETA" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="DESCR_SOCIALE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_NUM_CCIAA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_PARTITA_IVA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ID_ISTAT_STATO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ID_ISTAT_COMUNE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_COMUNE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_INDIRIZZO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_CIVICO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_RAPPR_SOC" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_AMMINISTR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IMPO_CAPITALE_SOC" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ID_SETTORE_ATT" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "DatiSoggettoPersonaGiuridicaType", namespace = "http://trasmissione.webservice.smp.giustizia.it/polizia/type", propOrder = {
    "numeprog",
    "idruolo",
    "idpersonag",
    "idtipoprev",
    "idnormativarif",
    "idtiposocieta",
    "descrsociale",
    "descrnumcciaa",
    "descrpartitaiva",
    "idistatstato",
    "idistatcomune",
    "descrcomune",
    "descrindirizzo",
    "descrcivico",
    "descrrapprsoc",
    "descramministr",
    "impocapitalesoc",
    "idsettoreatt",
    "descrnote"
})
public class DatiSoggettoPersonaGiuridicaType {

    @XmlElement(name = "NUME_PROG", required = true)
    protected BigInteger numeprog;
    @XmlElement(name = "ID_RUOLO", required = true)
    protected String idruolo;
    @XmlElement(name = "ID_PERSONA_G", required = true)
    protected BigInteger idpersonag;
    @XmlElement(name = "ID_TIPO_PREV", required = true)
    protected String idtipoprev;
    @XmlElement(name = "ID_NORMATIVA_RIF", required = true)
    protected BigInteger idnormativarif;
    @XmlElement(name = "ID_TIPO_SOCIETA", required = true)
    protected BigInteger idtiposocieta;
    @XmlElement(name = "DESCR_SOCIALE", required = true)
    protected String descrsociale;
    @XmlElement(name = "DESCR_NUM_CCIAA", required = true)
    protected String descrnumcciaa;
    @XmlElement(name = "DESCR_PARTITA_IVA", required = true)
    protected String descrpartitaiva;
    @XmlElement(name = "ID_ISTAT_STATO", required = true)
    protected String idistatstato;
    @XmlElement(name = "ID_ISTAT_COMUNE", required = true, nillable = true)
    protected String idistatcomune;
    @XmlElement(name = "DESCR_COMUNE", required = true, nillable = true)
    protected String descrcomune;
    @XmlElement(name = "DESCR_INDIRIZZO", required = true, nillable = true)
    protected String descrindirizzo;
    @XmlElement(name = "DESCR_CIVICO", required = true, nillable = true)
    protected String descrcivico;
    @XmlElement(name = "DESCR_RAPPR_SOC", required = true, nillable = true)
    protected String descrrapprsoc;
    @XmlElement(name = "DESCR_AMMINISTR", required = true, nillable = true)
    protected String descramministr;
    @XmlElement(name = "IMPO_CAPITALE_SOC", required = true, nillable = true)
    protected String impocapitalesoc;
    @XmlElement(name = "ID_SETTORE_ATT", required = true, nillable = true)
    protected String idsettoreatt;
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
     * Gets the value of the idruolo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDRUOLO() {
        return idruolo;
    }

    /**
     * Sets the value of the idruolo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDRUOLO(String value) {
        this.idruolo = value;
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
     * Gets the value of the idtipoprev property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDTIPOPREV() {
        return idtipoprev;
    }

    /**
     * Sets the value of the idtipoprev property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDTIPOPREV(String value) {
        this.idtipoprev = value;
    }

    /**
     * Gets the value of the idnormativarif property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIDNORMATIVARIF() {
        return idnormativarif;
    }

    /**
     * Sets the value of the idnormativarif property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIDNORMATIVARIF(BigInteger value) {
        this.idnormativarif = value;
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
     * Gets the value of the descrnumcciaa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRNUMCCIAA() {
        return descrnumcciaa;
    }

    /**
     * Sets the value of the descrnumcciaa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRNUMCCIAA(String value) {
        this.descrnumcciaa = value;
    }

    /**
     * Gets the value of the descrpartitaiva property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRPARTITAIVA() {
        return descrpartitaiva;
    }

    /**
     * Sets the value of the descrpartitaiva property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRPARTITAIVA(String value) {
        this.descrpartitaiva = value;
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
     * Gets the value of the descrindirizzo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRINDIRIZZO() {
        return descrindirizzo;
    }

    /**
     * Sets the value of the descrindirizzo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRINDIRIZZO(String value) {
        this.descrindirizzo = value;
    }

    /**
     * Gets the value of the descrcivico property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRCIVICO() {
        return descrcivico;
    }

    /**
     * Sets the value of the descrcivico property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRCIVICO(String value) {
        this.descrcivico = value;
    }

    /**
     * Gets the value of the descrrapprsoc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRRAPPRSOC() {
        return descrrapprsoc;
    }

    /**
     * Sets the value of the descrrapprsoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRRAPPRSOC(String value) {
        this.descrrapprsoc = value;
    }

    /**
     * Gets the value of the descramministr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRAMMINISTR() {
        return descramministr;
    }

    /**
     * Sets the value of the descramministr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRAMMINISTR(String value) {
        this.descramministr = value;
    }

    /**
     * Gets the value of the impocapitalesoc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIMPOCAPITALESOC() {
        return impocapitalesoc;
    }

    /**
     * Sets the value of the impocapitalesoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIMPOCAPITALESOC(String value) {
        this.impocapitalesoc = value;
    }

    /**
     * Gets the value of the idsettoreatt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDSETTOREATT() {
        return idsettoreatt;
    }

    /**
     * Sets the value of the idsettoreatt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDSETTOREATT(String value) {
        this.idsettoreatt = value;
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
