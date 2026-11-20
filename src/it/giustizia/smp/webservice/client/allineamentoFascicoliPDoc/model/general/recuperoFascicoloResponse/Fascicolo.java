//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2020.07.13 alle 02:48:55 PM CEST 
//


package it.giustizia.smp.webservice.client.allineamentoFascicoliPDoc.model.general.recuperoFascicoloResponse;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="codicefascicolo">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
 *               &lt;maxLength value="2048"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="codicefascicolopadre">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="2048"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="datacreazione" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="oggetto">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="500"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="idufficiocreatore" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="idutentecreatore" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="stato" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element ref="{}security"/>
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
    "codicefascicolo",
    "codicefascicolopadre",
    "datacreazione",
    "oggetto",
    "idufficiocreatore",
    "idutentecreatore",
    "stato",
    "security"
})
public class Fascicolo {

    @XmlElement(required = true)
    protected String codicefascicolo;
    @XmlElement(required = true)
    protected String codicefascicolopadre;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datacreazione;
    @XmlElement(required = true)
    protected String oggetto;
    @XmlElement(required = true)
    protected BigInteger idufficiocreatore;
    @XmlElement(required = true)
    protected BigInteger idutentecreatore;
    @XmlElement(required = true)
    protected String stato;
    @XmlElement(required = true)
    protected Security security;

    /**
     * Recupera il valore della proprietà codicefascicolo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodicefascicolo() {
        return codicefascicolo;
    }

    /**
     * Imposta il valore della proprietà codicefascicolo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodicefascicolo(String value) {
        this.codicefascicolo = value;
    }

    /**
     * Recupera il valore della proprietà codicefascicolopadre.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodicefascicolopadre() {
        return codicefascicolopadre;
    }

    /**
     * Imposta il valore della proprietà codicefascicolopadre.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodicefascicolopadre(String value) {
        this.codicefascicolopadre = value;
    }

    /**
     * Recupera il valore della proprietà datacreazione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDatacreazione() {
        return datacreazione;
    }

    /**
     * Imposta il valore della proprietà datacreazione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDatacreazione(XMLGregorianCalendar value) {
        this.datacreazione = value;
    }

    /**
     * Recupera il valore della proprietà oggetto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOggetto() {
        return oggetto;
    }

    /**
     * Imposta il valore della proprietà oggetto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOggetto(String value) {
        this.oggetto = value;
    }

    /**
     * Recupera il valore della proprietà idufficiocreatore.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdufficiocreatore() {
        return idufficiocreatore;
    }

    /**
     * Imposta il valore della proprietà idufficiocreatore.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdufficiocreatore(BigInteger value) {
        this.idufficiocreatore = value;
    }

    /**
     * Recupera il valore della proprietà idutentecreatore.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdutentecreatore() {
        return idutentecreatore;
    }

    /**
     * Imposta il valore della proprietà idutentecreatore.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdutentecreatore(BigInteger value) {
        this.idutentecreatore = value;
    }

    /**
     * Recupera il valore della proprietà stato.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStato() {
        return stato;
    }

    /**
     * Imposta il valore della proprietà stato.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStato(String value) {
        this.stato = value;
    }

    /**
     * Recupera il valore della proprietà security.
     * 
     * @return
     *     possible object is
     *     {@link Security }
     *     
     */
    public Security getSecurity() {
        return security;
    }

    /**
     * Imposta il valore della proprietà security.
     * 
     * @param value
     *     allowed object is
     *     {@link Security }
     *     
     */
    public void setSecurity(Security value) {
        this.security = value;
    }

}
