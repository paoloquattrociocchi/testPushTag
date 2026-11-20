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
 * <p>Classe Java per PersonaGiuridica complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="PersonaGiuridica">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiKIdPersGiur" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codiKIdUffOwner" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="codiKIdVer" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="descRagioneSoc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiFkIdTipoSoc" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="descPartitaIva" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descNumeCciaa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descCodFisc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiFkIdStatoSede" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="codiFkIdComSede" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="descComEsteroSede" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiFkIdTipoStrada" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="descIndirizzoSede" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descCivico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descCap" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiFkIdSettAtt" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="impoCapSoc" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="descNote" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descRapprSoc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descAmmin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonaGiuridica", propOrder = {
    "codiKIdPersGiur",
    "codiKIdUffOwner",
    "codiKIdVer",
    "descRagioneSoc",
    "codiFkIdTipoSoc",
    "descPartitaIva",
    "descNumeCciaa",
    "descCodFisc",
    "codiFkIdStatoSede",
    "codiFkIdComSede",
    "descComEsteroSede",
    "codiFkIdTipoStrada",
    "descIndirizzoSede",
    "descCivico",
    "descCap",
    "codiFkIdSettAtt",
    "impoCapSoc",
    "descNote",
    "descRapprSoc",
    "descAmmin"
})
public class PersonaGiuridica {

    protected long codiKIdPersGiur;
    protected int codiKIdUffOwner;
    protected short codiKIdVer;
    protected String descRagioneSoc;
    protected Integer codiFkIdTipoSoc;
    protected String descPartitaIva;
    protected String descNumeCciaa;
    protected String descCodFisc;
    protected Integer codiFkIdStatoSede;
    protected Integer codiFkIdComSede;
    protected String descComEsteroSede;
    protected Integer codiFkIdTipoStrada;
    protected String descIndirizzoSede;
    protected String descCivico;
    protected String descCap;
    protected Integer codiFkIdSettAtt;
    protected BigDecimal impoCapSoc;
    protected String descNote;
    protected String descRapprSoc;
    protected String descAmmin;

    /**
     * Recupera il valore della proprietà codiKIdPersGiur.
     * 
     */
    public long getCodiKIdPersGiur() {
        return codiKIdPersGiur;
    }

    /**
     * Imposta il valore della proprietà codiKIdPersGiur.
     * 
     */
    public void setCodiKIdPersGiur(long value) {
        this.codiKIdPersGiur = value;
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
     * Recupera il valore della proprietà codiKIdVer.
     * 
     */
    public short getCodiKIdVer() {
        return codiKIdVer;
    }

    /**
     * Imposta il valore della proprietà codiKIdVer.
     * 
     */
    public void setCodiKIdVer(short value) {
        this.codiKIdVer = value;
    }

    /**
     * Recupera il valore della proprietà descRagioneSoc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescRagioneSoc() {
        return descRagioneSoc;
    }

    /**
     * Imposta il valore della proprietà descRagioneSoc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescRagioneSoc(String value) {
        this.descRagioneSoc = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdTipoSoc.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodiFkIdTipoSoc() {
        return codiFkIdTipoSoc;
    }

    /**
     * Imposta il valore della proprietà codiFkIdTipoSoc.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodiFkIdTipoSoc(Integer value) {
        this.codiFkIdTipoSoc = value;
    }

    /**
     * Recupera il valore della proprietà descPartitaIva.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescPartitaIva() {
        return descPartitaIva;
    }

    /**
     * Imposta il valore della proprietà descPartitaIva.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescPartitaIva(String value) {
        this.descPartitaIva = value;
    }

    /**
     * Recupera il valore della proprietà descNumeCciaa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescNumeCciaa() {
        return descNumeCciaa;
    }

    /**
     * Imposta il valore della proprietà descNumeCciaa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescNumeCciaa(String value) {
        this.descNumeCciaa = value;
    }

    /**
     * Recupera il valore della proprietà descCodFisc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescCodFisc() {
        return descCodFisc;
    }

    /**
     * Imposta il valore della proprietà descCodFisc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescCodFisc(String value) {
        this.descCodFisc = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdStatoSede.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodiFkIdStatoSede() {
        return codiFkIdStatoSede;
    }

    /**
     * Imposta il valore della proprietà codiFkIdStatoSede.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodiFkIdStatoSede(Integer value) {
        this.codiFkIdStatoSede = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdComSede.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodiFkIdComSede() {
        return codiFkIdComSede;
    }

    /**
     * Imposta il valore della proprietà codiFkIdComSede.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodiFkIdComSede(Integer value) {
        this.codiFkIdComSede = value;
    }

    /**
     * Recupera il valore della proprietà descComEsteroSede.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescComEsteroSede() {
        return descComEsteroSede;
    }

    /**
     * Imposta il valore della proprietà descComEsteroSede.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescComEsteroSede(String value) {
        this.descComEsteroSede = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdTipoStrada.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodiFkIdTipoStrada() {
        return codiFkIdTipoStrada;
    }

    /**
     * Imposta il valore della proprietà codiFkIdTipoStrada.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodiFkIdTipoStrada(Integer value) {
        this.codiFkIdTipoStrada = value;
    }

    /**
     * Recupera il valore della proprietà descIndirizzoSede.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescIndirizzoSede() {
        return descIndirizzoSede;
    }

    /**
     * Imposta il valore della proprietà descIndirizzoSede.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescIndirizzoSede(String value) {
        this.descIndirizzoSede = value;
    }

    /**
     * Recupera il valore della proprietà descCivico.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescCivico() {
        return descCivico;
    }

    /**
     * Imposta il valore della proprietà descCivico.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescCivico(String value) {
        this.descCivico = value;
    }

    /**
     * Recupera il valore della proprietà descCap.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescCap() {
        return descCap;
    }

    /**
     * Imposta il valore della proprietà descCap.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescCap(String value) {
        this.descCap = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdSettAtt.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodiFkIdSettAtt() {
        return codiFkIdSettAtt;
    }

    /**
     * Imposta il valore della proprietà codiFkIdSettAtt.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodiFkIdSettAtt(Integer value) {
        this.codiFkIdSettAtt = value;
    }

    /**
     * Recupera il valore della proprietà impoCapSoc.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImpoCapSoc() {
        return impoCapSoc;
    }

    /**
     * Imposta il valore della proprietà impoCapSoc.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImpoCapSoc(BigDecimal value) {
        this.impoCapSoc = value;
    }

    /**
     * Recupera il valore della proprietà descNote.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescNote() {
        return descNote;
    }

    /**
     * Imposta il valore della proprietà descNote.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescNote(String value) {
        this.descNote = value;
    }

    /**
     * Recupera il valore della proprietà descRapprSoc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescRapprSoc() {
        return descRapprSoc;
    }

    /**
     * Imposta il valore della proprietà descRapprSoc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescRapprSoc(String value) {
        this.descRapprSoc = value;
    }

    /**
     * Recupera il valore della proprietà descAmmin.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescAmmin() {
        return descAmmin;
    }

    /**
     * Imposta il valore della proprietà descAmmin.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescAmmin(String value) {
        this.descAmmin = value;
    }

}
