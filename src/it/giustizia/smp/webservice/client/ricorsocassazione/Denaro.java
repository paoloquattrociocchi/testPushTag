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
 * <p>Classe Java per Denaro complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Denaro">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiKIdDenaro" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codiKIdUffOwner" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="descEnteDepos" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descFiliale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descDirettore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descIdentEnte" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="impoEuro" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="impoValuta" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="codiFkIdValuta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiFkIdAbi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiFkIdCab" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descIban" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descBicSwift" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Denaro", propOrder = {
    "codiKIdDenaro",
    "codiKIdUffOwner",
    "descEnteDepos",
    "descFiliale",
    "descDirettore",
    "descIdentEnte",
    "impoEuro",
    "impoValuta",
    "codiFkIdValuta",
    "codiFkIdAbi",
    "codiFkIdCab",
    "descIban",
    "descBicSwift"
})
public class Denaro {

    protected long codiKIdDenaro;
    protected int codiKIdUffOwner;
    protected String descEnteDepos;
    protected String descFiliale;
    protected String descDirettore;
    protected String descIdentEnte;
    protected BigDecimal impoEuro;
    protected BigDecimal impoValuta;
    protected String codiFkIdValuta;
    protected String codiFkIdAbi;
    protected String codiFkIdCab;
    protected String descIban;
    protected String descBicSwift;

    /**
     * Recupera il valore della proprietà codiKIdDenaro.
     * 
     */
    public long getCodiKIdDenaro() {
        return codiKIdDenaro;
    }

    /**
     * Imposta il valore della proprietà codiKIdDenaro.
     * 
     */
    public void setCodiKIdDenaro(long value) {
        this.codiKIdDenaro = value;
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
     * Recupera il valore della proprietà descEnteDepos.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescEnteDepos() {
        return descEnteDepos;
    }

    /**
     * Imposta il valore della proprietà descEnteDepos.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescEnteDepos(String value) {
        this.descEnteDepos = value;
    }

    /**
     * Recupera il valore della proprietà descFiliale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescFiliale() {
        return descFiliale;
    }

    /**
     * Imposta il valore della proprietà descFiliale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescFiliale(String value) {
        this.descFiliale = value;
    }

    /**
     * Recupera il valore della proprietà descDirettore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescDirettore() {
        return descDirettore;
    }

    /**
     * Imposta il valore della proprietà descDirettore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescDirettore(String value) {
        this.descDirettore = value;
    }

    /**
     * Recupera il valore della proprietà descIdentEnte.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescIdentEnte() {
        return descIdentEnte;
    }

    /**
     * Imposta il valore della proprietà descIdentEnte.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescIdentEnte(String value) {
        this.descIdentEnte = value;
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

}
