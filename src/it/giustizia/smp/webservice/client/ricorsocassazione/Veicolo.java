//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.12.22 alle 12:20:29 PM CET 
//


package it.giustizia.smp.webservice.client.ricorsocassazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per Veicolo complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Veicolo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiKIdVeicolo" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codiKIdUffOwner" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="descReg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataReg" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="descIdentif" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descImmatricolaz" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descModello" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descTarga" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descTelaio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descColore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Veicolo", propOrder = {
    "codiKIdVeicolo",
    "codiKIdUffOwner",
    "descReg",
    "dataReg",
    "descIdentif",
    "descImmatricolaz",
    "descModello",
    "descTarga",
    "descTelaio",
    "descColore"
})
public class Veicolo {

    protected long codiKIdVeicolo;
    protected int codiKIdUffOwner;
    protected String descReg;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataReg;
    protected String descIdentif;
    protected String descImmatricolaz;
    protected String descModello;
    protected String descTarga;
    protected String descTelaio;
    protected String descColore;

    /**
     * Recupera il valore della proprietà codiKIdVeicolo.
     * 
     */
    public long getCodiKIdVeicolo() {
        return codiKIdVeicolo;
    }

    /**
     * Imposta il valore della proprietà codiKIdVeicolo.
     * 
     */
    public void setCodiKIdVeicolo(long value) {
        this.codiKIdVeicolo = value;
    }

    /**
     * Recupera il valore della proprietà codiKIdUffOwner.
     * 
     */
    public int getCodiKIdUffOwner() {
        return codiKIdUffOwner;
    }

    /**
     * Imposta il valore della proprietà codiKIdUffOwner.
     * 
     */
    public void setCodiKIdUffOwner(int value) {
        this.codiKIdUffOwner = value;
    }

    /**
     * Recupera il valore della proprietà descReg.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescReg() {
        return descReg;
    }

    /**
     * Imposta il valore della proprietà descReg.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescReg(String value) {
        this.descReg = value;
    }

    /**
     * Recupera il valore della proprietà dataReg.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataReg() {
        return dataReg;
    }

    /**
     * Imposta il valore della proprietà dataReg.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataReg(XMLGregorianCalendar value) {
        this.dataReg = value;
    }

    /**
     * Recupera il valore della proprietà descIdentif.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescIdentif() {
        return descIdentif;
    }

    /**
     * Imposta il valore della proprietà descIdentif.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescIdentif(String value) {
        this.descIdentif = value;
    }

    /**
     * Recupera il valore della proprietà descImmatricolaz.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescImmatricolaz() {
        return descImmatricolaz;
    }

    /**
     * Imposta il valore della proprietà descImmatricolaz.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescImmatricolaz(String value) {
        this.descImmatricolaz = value;
    }

    /**
     * Recupera il valore della proprietà descModello.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescModello() {
        return descModello;
    }

    /**
     * Imposta il valore della proprietà descModello.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescModello(String value) {
        this.descModello = value;
    }

    /**
     * Recupera il valore della proprietà descTarga.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescTarga() {
        return descTarga;
    }

    /**
     * Imposta il valore della proprietà descTarga.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescTarga(String value) {
        this.descTarga = value;
    }

    /**
     * Recupera il valore della proprietà descTelaio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescTelaio() {
        return descTelaio;
    }

    /**
     * Imposta il valore della proprietà descTelaio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescTelaio(String value) {
        this.descTelaio = value;
    }

    /**
     * Recupera il valore della proprietà descColore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescColore() {
        return descColore;
    }

    /**
     * Imposta il valore della proprietà descColore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescColore(String value) {
        this.descColore = value;
    }

}
