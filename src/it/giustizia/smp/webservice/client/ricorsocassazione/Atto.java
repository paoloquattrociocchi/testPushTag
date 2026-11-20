//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.12.22 alle 12:20:29 PM CET 
//


package it.giustizia.smp.webservice.client.ricorsocassazione;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per Atto complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Atto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiKIdAtto" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codiKIdUffOwner" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="codiFkIdTipoAtto" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="codiFkIdCatAtto" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="dataRegistrazione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dataEmissione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="descTipoInvRicorso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descTipoInvio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sicNrg" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="sicNrgreale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Decreto" type="{http://it/eng/giustizia/sitmp/webservices/ricorsoservice}Decreto"/>
 *         &lt;element name="Impugnazione" type="{http://it/eng/giustizia/sitmp/webservices/ricorsoservice}Impugnazione"/>
 *         &lt;element name="RelAttoMisuraPersonale" type="{http://it/eng/giustizia/sitmp/webservices/ricorsoservice}RelAttoMisuraPersonale" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="RelBeneAtto" type="{http://it/eng/giustizia/sitmp/webservices/ricorsoservice}RelBeneAtto" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Atto", propOrder = {
    "codiKIdAtto",
    "codiKIdUffOwner",
    "codiFkIdTipoAtto",
    "codiFkIdCatAtto",
    "dataRegistrazione",
    "dataEmissione",
    "descTipoInvRicorso",
    "descTipoInvio",
    "sicNrg",
    "sicNrgreale",
    "decreto",
    "impugnazione",
    "relAttoMisuraPersonale",
    "relBeneAtto"
})
public class Atto {

    protected long codiKIdAtto;
    protected int codiKIdUffOwner;
    protected int codiFkIdTipoAtto;
    protected int codiFkIdCatAtto;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataRegistrazione;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataEmissione;
    protected String descTipoInvRicorso;
    protected String descTipoInvio;
    protected Long sicNrg;
    protected String sicNrgreale;
    @XmlElement(name = "Decreto", required = true)
    protected Decreto decreto;
    @XmlElement(name = "Impugnazione", required = true)
    protected Impugnazione impugnazione;
    @XmlElement(name = "RelAttoMisuraPersonale")
    protected List<RelAttoMisuraPersonale> relAttoMisuraPersonale;
    @XmlElement(name = "RelBeneAtto")
    protected List<RelBeneAtto> relBeneAtto;

    /**
     * Recupera il valore della proprietà codiKIdAtto.
     * 
     */
    public long getCodiKIdAtto() {
        return codiKIdAtto;
    }

    /**
     * Imposta il valore della proprietà codiKIdAtto.
     * 
     */
    public void setCodiKIdAtto(long value) {
        this.codiKIdAtto = value;
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
     * Recupera il valore della proprietà codiFkIdTipoAtto.
     * 
     */
    public int getCodiFkIdTipoAtto() {
        return codiFkIdTipoAtto;
    }

    /**
     * Imposta il valore della proprietà codiFkIdTipoAtto.
     * 
     */
    public void setCodiFkIdTipoAtto(int value) {
        this.codiFkIdTipoAtto = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdCatAtto.
     * 
     */
    public int getCodiFkIdCatAtto() {
        return codiFkIdCatAtto;
    }

    /**
     * Imposta il valore della proprietà codiFkIdCatAtto.
     * 
     */
    public void setCodiFkIdCatAtto(int value) {
        this.codiFkIdCatAtto = value;
    }

    /**
     * Recupera il valore della proprietà dataRegistrazione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataRegistrazione() {
        return dataRegistrazione;
    }

    /**
     * Imposta il valore della proprietà dataRegistrazione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataRegistrazione(XMLGregorianCalendar value) {
        this.dataRegistrazione = value;
    }

    /**
     * Recupera il valore della proprietà dataEmissione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataEmissione() {
        return dataEmissione;
    }

    /**
     * Imposta il valore della proprietà dataEmissione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataEmissione(XMLGregorianCalendar value) {
        this.dataEmissione = value;
    }

    /**
     * Recupera il valore della proprietà descTipoInvRicorso.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescTipoInvRicorso() {
        return descTipoInvRicorso;
    }

    /**
     * Imposta il valore della proprietà descTipoInvRicorso.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescTipoInvRicorso(String value) {
        this.descTipoInvRicorso = value;
    }

    /**
     * Recupera il valore della proprietà descTipoInvio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescTipoInvio() {
        return descTipoInvio;
    }

    /**
     * Imposta il valore della proprietà descTipoInvio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescTipoInvio(String value) {
        this.descTipoInvio = value;
    }

    /**
     * Recupera il valore della proprietà sicNrg.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getSicNrg() {
        return sicNrg;
    }

    /**
     * Imposta il valore della proprietà sicNrg.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSicNrg(Long value) {
        this.sicNrg = value;
    }

    /**
     * Recupera il valore della proprietà sicNrgreale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSicNrgreale() {
        return sicNrgreale;
    }

    /**
     * Imposta il valore della proprietà sicNrgreale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSicNrgreale(String value) {
        this.sicNrgreale = value;
    }

    /**
     * Recupera il valore della proprietà decreto.
     * 
     * @return
     *     possible object is
     *     {@link Decreto }
     *     
     */
    public Decreto getDecreto() {
        return decreto;
    }

    /**
     * Imposta il valore della proprietà decreto.
     * 
     * @param value
     *     allowed object is
     *     {@link Decreto }
     *     
     */
    public void setDecreto(Decreto value) {
        this.decreto = value;
    }

    /**
     * Recupera il valore della proprietà impugnazione.
     * 
     * @return
     *     possible object is
     *     {@link Impugnazione }
     *     
     */
    public Impugnazione getImpugnazione() {
        return impugnazione;
    }

    /**
     * Imposta il valore della proprietà impugnazione.
     * 
     * @param value
     *     allowed object is
     *     {@link Impugnazione }
     *     
     */
    public void setImpugnazione(Impugnazione value) {
        this.impugnazione = value;
    }

    /**
     * Gets the value of the relAttoMisuraPersonale property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the relAttoMisuraPersonale property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRelAttoMisuraPersonale().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RelAttoMisuraPersonale }
     * 
     * 
     */
    public List<RelAttoMisuraPersonale> getRelAttoMisuraPersonale() {
        if (relAttoMisuraPersonale == null) {
            relAttoMisuraPersonale = new ArrayList<RelAttoMisuraPersonale>();
        }
        return this.relAttoMisuraPersonale;
    }

    /**
     * Gets the value of the relBeneAtto property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the relBeneAtto property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRelBeneAtto().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RelBeneAtto }
     * 
     * 
     */
    public List<RelBeneAtto> getRelBeneAtto() {
        if (relBeneAtto == null) {
            relBeneAtto = new ArrayList<RelBeneAtto>();
        }
        return this.relBeneAtto;
    }

}
