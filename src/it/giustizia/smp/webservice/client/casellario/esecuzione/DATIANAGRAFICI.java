
package it.giustizia.smp.webservice.client.casellario.esecuzione;

import java.math.BigInteger;
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
 *         &lt;element name="PROG_ANAGRAFICA">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               &lt;totalDigits value="15"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="PERS_COGNOME">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="35"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="PERS_NOME">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="35"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="CODI_LUOGO_NASCITA" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="6"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="CODI_STATO_ESTERO_NAS">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="8"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="DESC_COMUNE_ESTERO" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="72"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="DATA_NASCITA" type="{http://it/mig/sippi/service/inviaProvvedimentoEsecuzione/type}DATA" minOccurs="0"/>
 *         &lt;element name="CODI_FISCALE" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="16"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="CODI_IMPRONTA_DIGITALE" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="7"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="PERS_PATERNITA" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="35"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="SESSO" type="{http://it/mig/sippi/service/inviaProvvedimentoEsecuzione/type}SESSO"/>
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
    "proganagrafica",
    "perscognome",
    "persnome",
    "codiluogonascita",
    "codistatoesteronas",
    "desccomuneestero",
    "datanascita",
    "codifiscale",
    "codiimprontadigitale",
    "perspaternita",
    "sesso"
})
@XmlRootElement(name = "DATI_ANAGRAFICI")
public class DATIANAGRAFICI {

    @XmlElement(name = "PROG_ANAGRAFICA", required = true)
    protected BigInteger proganagrafica;
    @XmlElement(name = "PERS_COGNOME", required = true)
    protected String perscognome;
    @XmlElement(name = "PERS_NOME", required = true)
    protected String persnome;
    @XmlElement(name = "CODI_LUOGO_NASCITA")
    protected String codiluogonascita;
    @XmlElement(name = "CODI_STATO_ESTERO_NAS", required = true)
    protected String codistatoesteronas;
    @XmlElement(name = "DESC_COMUNE_ESTERO")
    protected String desccomuneestero;
    @XmlElement(name = "DATA_NASCITA")
    protected DATA datanascita;
    @XmlElement(name = "CODI_FISCALE")
    protected String codifiscale;
    @XmlElement(name = "CODI_IMPRONTA_DIGITALE")
    protected String codiimprontadigitale;
    @XmlElement(name = "PERS_PATERNITA")
    protected String perspaternita;
    @XmlElement(name = "SESSO", required = true)
    protected SESSO sesso;

    /**
     * Gets the value of the proganagrafica property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPROGANAGRAFICA() {
        return proganagrafica;
    }

    /**
     * Sets the value of the proganagrafica property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPROGANAGRAFICA(BigInteger value) {
        this.proganagrafica = value;
    }

    /**
     * Gets the value of the perscognome property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPERSCOGNOME() {
        return perscognome;
    }

    /**
     * Sets the value of the perscognome property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPERSCOGNOME(String value) {
        this.perscognome = value;
    }

    /**
     * Gets the value of the persnome property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPERSNOME() {
        return persnome;
    }

    /**
     * Sets the value of the persnome property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPERSNOME(String value) {
        this.persnome = value;
    }

    /**
     * Gets the value of the codiluogonascita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODILUOGONASCITA() {
        return codiluogonascita;
    }

    /**
     * Sets the value of the codiluogonascita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODILUOGONASCITA(String value) {
        this.codiluogonascita = value;
    }

    /**
     * Gets the value of the codistatoesteronas property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODISTATOESTERONAS() {
        return codistatoesteronas;
    }

    /**
     * Sets the value of the codistatoesteronas property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODISTATOESTERONAS(String value) {
        this.codistatoesteronas = value;
    }

    /**
     * Gets the value of the desccomuneestero property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCCOMUNEESTERO() {
        return desccomuneestero;
    }

    /**
     * Sets the value of the desccomuneestero property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCCOMUNEESTERO(String value) {
        this.desccomuneestero = value;
    }

    /**
     * Gets the value of the datanascita property.
     * 
     * @return
     *     possible object is
     *     {@link DATA }
     *     
     */
    public DATA getDATANASCITA() {
        return datanascita;
    }

    /**
     * Sets the value of the datanascita property.
     * 
     * @param value
     *     allowed object is
     *     {@link DATA }
     *     
     */
    public void setDATANASCITA(DATA value) {
        this.datanascita = value;
    }

    /**
     * Gets the value of the codifiscale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODIFISCALE() {
        return codifiscale;
    }

    /**
     * Sets the value of the codifiscale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODIFISCALE(String value) {
        this.codifiscale = value;
    }

    /**
     * Gets the value of the codiimprontadigitale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODIIMPRONTADIGITALE() {
        return codiimprontadigitale;
    }

    /**
     * Sets the value of the codiimprontadigitale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODIIMPRONTADIGITALE(String value) {
        this.codiimprontadigitale = value;
    }

    /**
     * Gets the value of the perspaternita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPERSPATERNITA() {
        return perspaternita;
    }

    /**
     * Sets the value of the perspaternita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPERSPATERNITA(String value) {
        this.perspaternita = value;
    }

    /**
     * Gets the value of the sesso property.
     * 
     * @return
     *     possible object is
     *     {@link SESSO }
     *     
     */
    public SESSO getSESSO() {
        return sesso;
    }

    /**
     * Sets the value of the sesso property.
     * 
     * @param value
     *     allowed object is
     *     {@link SESSO }
     *     
     */
    public void setSESSO(SESSO value) {
        this.sesso = value;
    }

}
