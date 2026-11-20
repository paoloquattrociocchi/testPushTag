//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.10.14 alle 10:17:21 AM CEST 
//


package it.giustizia.smp.webservice.client.casellario.ricerca;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
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
 *         &lt;element name="CODI_STATO_ESTERO_NAS" minOccurs="0">
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
 *         &lt;element name="DATA_NASCITA" type="{http://it/mig/sippi/service/richiestaCertificato/type}DATA" minOccurs="0"/>
 *         &lt;element name="SESSO" type="{http://it/mig/sippi/service/richiestaCertificato/type}SESSO"/>
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
 *         &lt;element name="PERS_COGNOME_MADRE" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="35"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="PERS_NOME_MADRE" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="35"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="FLAG_OMONINO_SINONIMO">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="1"/>
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
    "proganagrafica",
    "perscognome",
    "persnome",
    "codiluogonascita",
    "codistatoesteronas",
    "desccomuneestero",
    "datanascita",
    "sesso",
    "codifiscale",
    "codiimprontadigitale",
    "perspaternita",
    "perscognomemadre",
    "persnomemadre",
    "flagomoninosinonimo"
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
    @XmlElement(name = "CODI_STATO_ESTERO_NAS")
    protected String codistatoesteronas;
    @XmlElement(name = "DESC_COMUNE_ESTERO")
    protected String desccomuneestero;
    @XmlElement(name = "DATA_NASCITA")
    protected DATA datanascita;
    @XmlElement(name = "SESSO", required = true)
    protected SESSO sesso;
    @XmlElement(name = "CODI_FISCALE")
    protected String codifiscale;
    @XmlElement(name = "CODI_IMPRONTA_DIGITALE")
    protected String codiimprontadigitale;
    @XmlElement(name = "PERS_PATERNITA")
    protected String perspaternita;
    @XmlElement(name = "PERS_COGNOME_MADRE")
    protected String perscognomemadre;
    @XmlElement(name = "PERS_NOME_MADRE")
    protected String persnomemadre;
    @XmlElement(name = "FLAG_OMONINO_SINONIMO", required = true)
    protected String flagomoninosinonimo;

    /**
     * Recupera il valore della proprietà proganagrafica.
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
     * Imposta il valore della proprietà proganagrafica.
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
     * Recupera il valore della proprietà perscognome.
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
     * Imposta il valore della proprietà perscognome.
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
     * Recupera il valore della proprietà persnome.
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
     * Imposta il valore della proprietà persnome.
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
     * Recupera il valore della proprietà codiluogonascita.
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
     * Imposta il valore della proprietà codiluogonascita.
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
     * Recupera il valore della proprietà codistatoesteronas.
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
     * Imposta il valore della proprietà codistatoesteronas.
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
     * Recupera il valore della proprietà desccomuneestero.
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
     * Imposta il valore della proprietà desccomuneestero.
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
     * Recupera il valore della proprietà datanascita.
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
     * Imposta il valore della proprietà datanascita.
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
     * Recupera il valore della proprietà sesso.
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
     * Imposta il valore della proprietà sesso.
     * 
     * @param value
     *     allowed object is
     *     {@link SESSO }
     *     
     */
    public void setSESSO(SESSO value) {
        this.sesso = value;
    }

    /**
     * Recupera il valore della proprietà codifiscale.
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
     * Imposta il valore della proprietà codifiscale.
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
     * Recupera il valore della proprietà codiimprontadigitale.
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
     * Imposta il valore della proprietà codiimprontadigitale.
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
     * Recupera il valore della proprietà perspaternita.
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
     * Imposta il valore della proprietà perspaternita.
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
     * Recupera il valore della proprietà perscognomemadre.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPERSCOGNOMEMADRE() {
        return perscognomemadre;
    }

    /**
     * Imposta il valore della proprietà perscognomemadre.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPERSCOGNOMEMADRE(String value) {
        this.perscognomemadre = value;
    }

    /**
     * Recupera il valore della proprietà persnomemadre.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPERSNOMEMADRE() {
        return persnomemadre;
    }

    /**
     * Imposta il valore della proprietà persnomemadre.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPERSNOMEMADRE(String value) {
        this.persnomemadre = value;
    }

    /**
     * Recupera il valore della proprietà flagomoninosinonimo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFLAGOMONINOSINONIMO() {
        return flagomoninosinonimo;
    }

    /**
     * Imposta il valore della proprietà flagomoninosinonimo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFLAGOMONINOSINONIMO(String value) {
        this.flagomoninosinonimo = value;
    }

}
