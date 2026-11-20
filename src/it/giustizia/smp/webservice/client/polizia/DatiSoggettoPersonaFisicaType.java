
package it.giustizia.smp.webservice.client.polizia;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiSoggettoPersonaFisicaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiSoggettoPersonaFisicaType">
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
 *         &lt;element name="ID_PERSONA" type="{http://www.w3.org/2001/XMLSchema}integer"/>
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
 *         &lt;element name="DESCR_CODICE_FISC" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ID_PROFESSIONE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_CUI" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ID_CONDIZIONE_GIUR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ID_ISTAT_STATO_RES" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ID_ISTAT_COMUNE_RES" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_COMUNE_RES" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_INDIRIZZO_RES" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_CIVICO_RES" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ID_ISTATO_STATO_DOM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ID_ISTATO_COMUNE_DOM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_COMUNE_DOM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_INDIRIZZO_DOM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_CIVICO_DOM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ID_ISTAT_STATO_DIM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ID_ISTAT_COMUNE_DIM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_COMUNE_DIM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_INDIRIZZO_DIM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_CIVICO_DIM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ID_ISTAT_STATO_ELE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ID_ISTAT_COMUNE_ELE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_COMUNE_ELE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_INDIRIZZO_ELE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_CIVICO_ELE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ID_ISTAT_STATO_MORTE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ID_ISTAT_COMUNE_MORTE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_COMUNE_MORTE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DATA_MORTE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_COGNOME_PADR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_NOME_PADR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_COGNOME_MADR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_NOME_MADR" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "DatiSoggettoPersonaFisicaType", namespace = "http://trasmissione.webservice.smp.giustizia.it/polizia/type", propOrder = {
    "numeprog",
    "idruolo",
    "idpersona",
    "idtipoprev",
    "idnormativarif",
    "descrcognome",
    "descrnome",
    "datanascita",
    "idistatstatonas",
    "idistatcomunenas",
    "descrcomunenas",
    "flgsesso",
    "descrcodicefisc",
    "idprofessione",
    "descrcui",
    "idcondizionegiur",
    "idistatstatores",
    "idistatcomuneres",
    "descrcomuneres",
    "descrindirizzores",
    "descrcivicores",
    "idistatostatodom",
    "idistatocomunedom",
    "descrcomunedom",
    "descrindirizzodom",
    "descrcivicodom",
    "idistatstatodim",
    "idistatcomunedim",
    "descrcomunedim",
    "descrindirizzodim",
    "descrcivicodim",
    "idistatstatoele",
    "idistatcomuneele",
    "descrcomuneele",
    "descrindirizzoele",
    "descrcivicoele",
    "idistatstatomorte",
    "idistatcomunemorte",
    "descrcomunemorte",
    "datamorte",
    "descrcognomepadr",
    "descrnomepadr",
    "descrcognomemadr",
    "descrnomemadr",
    "descrnote"
})
public class DatiSoggettoPersonaFisicaType {

    @XmlElement(name = "NUME_PROG", required = true)
    protected BigInteger numeprog;
    @XmlElement(name = "ID_RUOLO", required = true)
    protected String idruolo;
    @XmlElement(name = "ID_PERSONA", required = true, nillable = true)
    protected BigInteger idpersona;
    @XmlElement(name = "ID_TIPO_PREV", required = true)
    protected String idtipoprev;
    @XmlElement(name = "ID_NORMATIVA_RIF", required = true)
    protected BigInteger idnormativarif;
    @XmlElement(name = "DESCR_COGNOME", required = true)
    protected String descrcognome;
    @XmlElement(name = "DESCR_NOME", required = true)
    protected String descrnome;
    @XmlElement(name = "DATA_NASCITA", required = true)
    protected String datanascita;
    @XmlElement(name = "ID_ISTAT_STATO_NAS", required = true)
    protected String idistatstatonas;
    @XmlElement(name = "ID_ISTAT_COMUNE_NAS", required = true, nillable = true)
    protected String idistatcomunenas;
    @XmlElement(name = "DESCR_COMUNE_NAS", required = true, nillable = true)
    protected String descrcomunenas;
    @XmlElement(name = "FLG_SESSO", required = true, nillable = true)
    protected String flgsesso;
    @XmlElement(name = "DESCR_CODICE_FISC", required = true, nillable = true)
    protected String descrcodicefisc;
    @XmlElement(name = "ID_PROFESSIONE", required = true, nillable = true)
    protected String idprofessione;
    @XmlElement(name = "DESCR_CUI", required = true, nillable = true)
    protected String descrcui;
    @XmlElement(name = "ID_CONDIZIONE_GIUR", required = true, nillable = true)
    protected String idcondizionegiur;
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
    @XmlElement(name = "ID_ISTATO_STATO_DOM", required = true, nillable = true)
    protected String idistatostatodom;
    @XmlElement(name = "ID_ISTATO_COMUNE_DOM", required = true, nillable = true)
    protected String idistatocomunedom;
    @XmlElement(name = "DESCR_COMUNE_DOM", required = true, nillable = true)
    protected String descrcomunedom;
    @XmlElement(name = "DESCR_INDIRIZZO_DOM", required = true, nillable = true)
    protected String descrindirizzodom;
    @XmlElement(name = "DESCR_CIVICO_DOM", required = true, nillable = true)
    protected String descrcivicodom;
    @XmlElement(name = "ID_ISTAT_STATO_DIM", required = true, nillable = true)
    protected String idistatstatodim;
    @XmlElement(name = "ID_ISTAT_COMUNE_DIM", required = true, nillable = true)
    protected String idistatcomunedim;
    @XmlElement(name = "DESCR_COMUNE_DIM", required = true, nillable = true)
    protected String descrcomunedim;
    @XmlElement(name = "DESCR_INDIRIZZO_DIM", required = true, nillable = true)
    protected String descrindirizzodim;
    @XmlElement(name = "DESCR_CIVICO_DIM", required = true, nillable = true)
    protected String descrcivicodim;
    @XmlElement(name = "ID_ISTAT_STATO_ELE", required = true, nillable = true)
    protected String idistatstatoele;
    @XmlElement(name = "ID_ISTAT_COMUNE_ELE", required = true, nillable = true)
    protected String idistatcomuneele;
    @XmlElement(name = "DESCR_COMUNE_ELE", required = true, nillable = true)
    protected String descrcomuneele;
    @XmlElement(name = "DESCR_INDIRIZZO_ELE", required = true, nillable = true)
    protected String descrindirizzoele;
    @XmlElement(name = "DESCR_CIVICO_ELE", required = true, nillable = true)
    protected String descrcivicoele;
    @XmlElement(name = "ID_ISTAT_STATO_MORTE", required = true, nillable = true)
    protected String idistatstatomorte;
    @XmlElement(name = "ID_ISTAT_COMUNE_MORTE", required = true, nillable = true)
    protected String idistatcomunemorte;
    @XmlElement(name = "DESCR_COMUNE_MORTE", required = true, nillable = true)
    protected String descrcomunemorte;
    @XmlElement(name = "DATA_MORTE", required = true, nillable = true)
    protected String datamorte;
    @XmlElement(name = "DESCR_COGNOME_PADR", required = true, nillable = true)
    protected String descrcognomepadr;
    @XmlElement(name = "DESCR_NOME_PADR", required = true, nillable = true)
    protected String descrnomepadr;
    @XmlElement(name = "DESCR_COGNOME_MADR", required = true, nillable = true)
    protected String descrcognomemadr;
    @XmlElement(name = "DESCR_NOME_MADR", required = true, nillable = true)
    protected String descrnomemadr;
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
     * Gets the value of the idpersona property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIDPERSONA() {
        return idpersona;
    }

    /**
     * Sets the value of the idpersona property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIDPERSONA(BigInteger value) {
        this.idpersona = value;
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
     * Gets the value of the descrcodicefisc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRCODICEFISC() {
        return descrcodicefisc;
    }

    /**
     * Sets the value of the descrcodicefisc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRCODICEFISC(String value) {
        this.descrcodicefisc = value;
    }

    /**
     * Gets the value of the idprofessione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDPROFESSIONE() {
        return idprofessione;
    }

    /**
     * Sets the value of the idprofessione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDPROFESSIONE(String value) {
        this.idprofessione = value;
    }

    /**
     * Gets the value of the descrcui property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRCUI() {
        return descrcui;
    }

    /**
     * Sets the value of the descrcui property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRCUI(String value) {
        this.descrcui = value;
    }

    /**
     * Gets the value of the idcondizionegiur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDCONDIZIONEGIUR() {
        return idcondizionegiur;
    }

    /**
     * Sets the value of the idcondizionegiur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDCONDIZIONEGIUR(String value) {
        this.idcondizionegiur = value;
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
     * Gets the value of the idistatostatodom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDISTATOSTATODOM() {
        return idistatostatodom;
    }

    /**
     * Sets the value of the idistatostatodom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDISTATOSTATODOM(String value) {
        this.idistatostatodom = value;
    }

    /**
     * Gets the value of the idistatocomunedom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDISTATOCOMUNEDOM() {
        return idistatocomunedom;
    }

    /**
     * Sets the value of the idistatocomunedom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDISTATOCOMUNEDOM(String value) {
        this.idistatocomunedom = value;
    }

    /**
     * Gets the value of the descrcomunedom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRCOMUNEDOM() {
        return descrcomunedom;
    }

    /**
     * Sets the value of the descrcomunedom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRCOMUNEDOM(String value) {
        this.descrcomunedom = value;
    }

    /**
     * Gets the value of the descrindirizzodom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRINDIRIZZODOM() {
        return descrindirizzodom;
    }

    /**
     * Sets the value of the descrindirizzodom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRINDIRIZZODOM(String value) {
        this.descrindirizzodom = value;
    }

    /**
     * Gets the value of the descrcivicodom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRCIVICODOM() {
        return descrcivicodom;
    }

    /**
     * Sets the value of the descrcivicodom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRCIVICODOM(String value) {
        this.descrcivicodom = value;
    }

    /**
     * Gets the value of the idistatstatodim property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDISTATSTATODIM() {
        return idistatstatodim;
    }

    /**
     * Sets the value of the idistatstatodim property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDISTATSTATODIM(String value) {
        this.idistatstatodim = value;
    }

    /**
     * Gets the value of the idistatcomunedim property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDISTATCOMUNEDIM() {
        return idistatcomunedim;
    }

    /**
     * Sets the value of the idistatcomunedim property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDISTATCOMUNEDIM(String value) {
        this.idistatcomunedim = value;
    }

    /**
     * Gets the value of the descrcomunedim property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRCOMUNEDIM() {
        return descrcomunedim;
    }

    /**
     * Sets the value of the descrcomunedim property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRCOMUNEDIM(String value) {
        this.descrcomunedim = value;
    }

    /**
     * Gets the value of the descrindirizzodim property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRINDIRIZZODIM() {
        return descrindirizzodim;
    }

    /**
     * Sets the value of the descrindirizzodim property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRINDIRIZZODIM(String value) {
        this.descrindirizzodim = value;
    }

    /**
     * Gets the value of the descrcivicodim property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRCIVICODIM() {
        return descrcivicodim;
    }

    /**
     * Sets the value of the descrcivicodim property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRCIVICODIM(String value) {
        this.descrcivicodim = value;
    }

    /**
     * Gets the value of the idistatstatoele property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDISTATSTATOELE() {
        return idistatstatoele;
    }

    /**
     * Sets the value of the idistatstatoele property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDISTATSTATOELE(String value) {
        this.idistatstatoele = value;
    }

    /**
     * Gets the value of the idistatcomuneele property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDISTATCOMUNEELE() {
        return idistatcomuneele;
    }

    /**
     * Sets the value of the idistatcomuneele property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDISTATCOMUNEELE(String value) {
        this.idistatcomuneele = value;
    }

    /**
     * Gets the value of the descrcomuneele property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRCOMUNEELE() {
        return descrcomuneele;
    }

    /**
     * Sets the value of the descrcomuneele property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRCOMUNEELE(String value) {
        this.descrcomuneele = value;
    }

    /**
     * Gets the value of the descrindirizzoele property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRINDIRIZZOELE() {
        return descrindirizzoele;
    }

    /**
     * Sets the value of the descrindirizzoele property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRINDIRIZZOELE(String value) {
        this.descrindirizzoele = value;
    }

    /**
     * Gets the value of the descrcivicoele property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRCIVICOELE() {
        return descrcivicoele;
    }

    /**
     * Sets the value of the descrcivicoele property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRCIVICOELE(String value) {
        this.descrcivicoele = value;
    }

    /**
     * Gets the value of the idistatstatomorte property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDISTATSTATOMORTE() {
        return idistatstatomorte;
    }

    /**
     * Sets the value of the idistatstatomorte property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDISTATSTATOMORTE(String value) {
        this.idistatstatomorte = value;
    }

    /**
     * Gets the value of the idistatcomunemorte property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDISTATCOMUNEMORTE() {
        return idistatcomunemorte;
    }

    /**
     * Sets the value of the idistatcomunemorte property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDISTATCOMUNEMORTE(String value) {
        this.idistatcomunemorte = value;
    }

    /**
     * Gets the value of the descrcomunemorte property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRCOMUNEMORTE() {
        return descrcomunemorte;
    }

    /**
     * Sets the value of the descrcomunemorte property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRCOMUNEMORTE(String value) {
        this.descrcomunemorte = value;
    }

    /**
     * Gets the value of the datamorte property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDATAMORTE() {
        return datamorte;
    }

    /**
     * Sets the value of the datamorte property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDATAMORTE(String value) {
        this.datamorte = value;
    }

    /**
     * Gets the value of the descrcognomepadr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRCOGNOMEPADR() {
        return descrcognomepadr;
    }

    /**
     * Sets the value of the descrcognomepadr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRCOGNOMEPADR(String value) {
        this.descrcognomepadr = value;
    }

    /**
     * Gets the value of the descrnomepadr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRNOMEPADR() {
        return descrnomepadr;
    }

    /**
     * Sets the value of the descrnomepadr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRNOMEPADR(String value) {
        this.descrnomepadr = value;
    }

    /**
     * Gets the value of the descrcognomemadr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRCOGNOMEMADR() {
        return descrcognomemadr;
    }

    /**
     * Sets the value of the descrcognomemadr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRCOGNOMEMADR(String value) {
        this.descrcognomemadr = value;
    }

    /**
     * Gets the value of the descrnomemadr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRNOMEMADR() {
        return descrnomemadr;
    }

    /**
     * Sets the value of the descrnomemadr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRNOMEMADR(String value) {
        this.descrnomemadr = value;
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
