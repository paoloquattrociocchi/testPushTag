//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.12.22 alle 12:20:29 PM CET 
//


package it.giustizia.smp.webservice.client.ricorsocassazione;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per BeneFinanziario complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="BeneFinanziario">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiKIdBeneFin" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codiKIdUffOwner" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="codiFkIdAbi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiFkIdCab" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descIban" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descBicSwift" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descEnteCreditizio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descFilialeEnte" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descDirettoreEnte" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descRegistrazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="impoValuta" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="codiFkIdValuta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="impoEuro" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BeneFinanziario", propOrder = {
    "codiKIdBeneFin",
    "codiKIdUffOwner",
    "codiFkIdAbi",
    "codiFkIdCab",
    "descIban",
    "descBicSwift",
    "descEnteCreditizio",
    "descFilialeEnte",
    "descDirettoreEnte",
    "descRegistrazione",
    "impoValuta",
    "codiFkIdValuta",
    "impoEuro"
})
public class BeneFinanziario {

    protected long codiKIdBeneFin;
    protected int codiKIdUffOwner;
    protected String codiFkIdAbi;
    protected String codiFkIdCab;
    protected String descIban;
    protected String descBicSwift;
    protected String descEnteCreditizio;
    protected String descFilialeEnte;
    protected String descDirettoreEnte;
    protected String descRegistrazione;
    protected BigDecimal impoValuta;
    protected String codiFkIdValuta;
    protected BigDecimal impoEuro;

    /**
     * Recupera il valore della proprietà codiKIdBeneFin.
     * 
     */
    public long getCodiKIdBeneFin() {
        return codiKIdBeneFin;
    }

    /**
     * Imposta il valore della proprietà codiKIdBeneFin.
     * 
     */
    public void setCodiKIdBeneFin(long value) {
        this.codiKIdBeneFin = value;
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
     * Recupera il valore della proprietà codiFkIdAbi.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiFkIdAbi() {
        return codiFkIdAbi;
    }

    /**
     * Imposta il valore della proprietà codiFkIdAbi.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiFkIdAbi(String value) {
        this.codiFkIdAbi = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdCab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiFkIdCab() {
        return codiFkIdCab;
    }

    /**
     * Imposta il valore della proprietà codiFkIdCab.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiFkIdCab(String value) {
        this.codiFkIdCab = value;
    }

    /**
     * Recupera il valore della proprietà descIban.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescIban() {
        return descIban;
    }

    /**
     * Imposta il valore della proprietà descIban.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescIban(String value) {
        this.descIban = value;
    }

    /**
     * Recupera il valore della proprietà descBicSwift.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescBicSwift() {
        return descBicSwift;
    }

    /**
     * Imposta il valore della proprietà descBicSwift.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescBicSwift(String value) {
        this.descBicSwift = value;
    }

    /**
     * Recupera il valore della proprietà descEnteCreditizio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescEnteCreditizio() {
        return descEnteCreditizio;
    }

    /**
     * Imposta il valore della proprietà descEnteCreditizio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescEnteCreditizio(String value) {
        this.descEnteCreditizio = value;
    }

    /**
     * Recupera il valore della proprietà descFilialeEnte.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescFilialeEnte() {
        return descFilialeEnte;
    }

    /**
     * Imposta il valore della proprietà descFilialeEnte.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescFilialeEnte(String value) {
        this.descFilialeEnte = value;
    }

    /**
     * Recupera il valore della proprietà descDirettoreEnte.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescDirettoreEnte() {
        return descDirettoreEnte;
    }

    /**
     * Imposta il valore della proprietà descDirettoreEnte.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescDirettoreEnte(String value) {
        this.descDirettoreEnte = value;
    }

    /**
     * Recupera il valore della proprietà descRegistrazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescRegistrazione() {
        return descRegistrazione;
    }

    /**
     * Imposta il valore della proprietà descRegistrazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescRegistrazione(String value) {
        this.descRegistrazione = value;
    }

    /**
     * Recupera il valore della proprietà impoValuta.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImpoValuta() {
        return impoValuta;
    }

    /**
     * Imposta il valore della proprietà impoValuta.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImpoValuta(BigDecimal value) {
        this.impoValuta = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdValuta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiFkIdValuta() {
        return codiFkIdValuta;
    }

    /**
     * Imposta il valore della proprietà codiFkIdValuta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiFkIdValuta(String value) {
        this.codiFkIdValuta = value;
    }

    /**
     * Recupera il valore della proprietà impoEuro.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImpoEuro() {
        return impoEuro;
    }

    /**
     * Imposta il valore della proprietà impoEuro.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImpoEuro(BigDecimal value) {
        this.impoEuro = value;
    }

}
