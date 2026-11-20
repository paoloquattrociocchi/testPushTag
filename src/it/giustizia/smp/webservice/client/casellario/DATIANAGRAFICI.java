//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.09.17 alle 11:32:38 AM CEST 
//


package it.giustizia.smp.webservice.client.casellario;

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
 *         &lt;element name="DATA_NASCITA" type="{http://it/mig/sippi/service/inviaProvvedimento/type}DATA" minOccurs="0"/>
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
 *         &lt;element name="DESC_ANNOTAZIONE" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="2000"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="FLAG_DECEDUTO_SN" type="{http://it/mig/sippi/service/inviaProvvedimento/type}FLAG_DECESSO"/>
 *         &lt;element name="CODI_LUOGO_MORTE" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="6"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="CODI_STATO_ESTERO_MORTE" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="8"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="NUME_ATTO_MORTE" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="9"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="DATA_MORTE" type="{http://it/mig/sippi/service/inviaProvvedimento/type}DATA" minOccurs="0"/>
 *         &lt;element name="SESSO" type="{http://it/mig/sippi/service/inviaProvvedimento/type}SESSO"/>
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
    "perscognomemadre",
    "persnomemadre",
    "descannotazione",
    "flagdecedutosn",
    "codiluogomorte",
    "codistatoesteromorte",
    "numeattomorte",
    "datamorte",
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
    @XmlElement(name = "PERS_COGNOME_MADRE")
    protected String perscognomemadre;
    @XmlElement(name = "PERS_NOME_MADRE")
    protected String persnomemadre;
    @XmlElement(name = "DESC_ANNOTAZIONE")
    protected String descannotazione;
    @XmlElement(name = "FLAG_DECEDUTO_SN", required = true, defaultValue = "N")
    protected FLAGDECESSO flagdecedutosn;
    @XmlElement(name = "CODI_LUOGO_MORTE")
    protected String codiluogomorte;
    @XmlElement(name = "CODI_STATO_ESTERO_MORTE")
    protected String codistatoesteromorte;
    @XmlElement(name = "NUME_ATTO_MORTE")
    protected String numeattomorte;
    @XmlElement(name = "DATA_MORTE")
    protected DATA datamorte;
    @XmlElement(name = "SESSO", required = true)
    protected SESSO sesso;

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
     * Recupera il valore della proprietà descannotazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCANNOTAZIONE() {
        return descannotazione;
    }

    /**
     * Imposta il valore della proprietà descannotazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCANNOTAZIONE(String value) {
        this.descannotazione = value;
    }

    /**
     * Recupera il valore della proprietà flagdecedutosn.
     * 
     * @return
     *     possible object is
     *     {@link FLAGDECESSO }
     *     
     */
    public FLAGDECESSO getFLAGDECEDUTOSN() {
        return flagdecedutosn;
    }

    /**
     * Imposta il valore della proprietà flagdecedutosn.
     * 
     * @param value
     *     allowed object is
     *     {@link FLAGDECESSO }
     *     
     */
    public void setFLAGDECEDUTOSN(FLAGDECESSO value) {
        this.flagdecedutosn = value;
    }

    /**
     * Recupera il valore della proprietà codiluogomorte.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODILUOGOMORTE() {
        return codiluogomorte;
    }

    /**
     * Imposta il valore della proprietà codiluogomorte.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODILUOGOMORTE(String value) {
        this.codiluogomorte = value;
    }

    /**
     * Recupera il valore della proprietà codistatoesteromorte.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODISTATOESTEROMORTE() {
        return codistatoesteromorte;
    }

    /**
     * Imposta il valore della proprietà codistatoesteromorte.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODISTATOESTEROMORTE(String value) {
        this.codistatoesteromorte = value;
    }

    /**
     * Recupera il valore della proprietà numeattomorte.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNUMEATTOMORTE() {
        return numeattomorte;
    }

    /**
     * Imposta il valore della proprietà numeattomorte.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNUMEATTOMORTE(String value) {
        this.numeattomorte = value;
    }

    /**
     * Recupera il valore della proprietà datamorte.
     * 
     * @return
     *     possible object is
     *     {@link DATA }
     *     
     */
    public DATA getDATAMORTE() {
        return datamorte;
    }

    /**
     * Imposta il valore della proprietà datamorte.
     * 
     * @param value
     *     allowed object is
     *     {@link DATA }
     *     
     */
    public void setDATAMORTE(DATA value) {
        this.datamorte = value;
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

}
