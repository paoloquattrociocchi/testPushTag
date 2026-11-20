//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.09.17 alle 11:32:38 AM CEST 
//


package it.giustizia.smp.webservice.client.casellario;

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
 *         &lt;element ref="{http://it/mig/sippi/service/inviaProvvedimento/type}DATI_UFFICIO"/>
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
     * Recupera il valore della proprietà datiufficio.
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
     * Imposta il valore della proprietà datiufficio.
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
     * Recupera il valore della proprietà cognomeutente.
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
     * Imposta il valore della proprietà cognomeutente.
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
     * Recupera il valore della proprietà nomeutente.
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
     * Imposta il valore della proprietà nomeutente.
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
     * Recupera il valore della proprietà usernamesippi.
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
     * Imposta il valore della proprietà usernamesippi.
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
