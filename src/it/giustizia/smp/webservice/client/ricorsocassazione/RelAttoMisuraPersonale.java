//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.12.22 alle 12:20:29 PM CET 
//


package it.giustizia.smp.webservice.client.ricorsocassazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per RelAttoMisuraPersonale complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RelAttoMisuraPersonale">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiKIdProc" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codiKIdAtto" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codiKIdUffOwner" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="codiKIdMisuraPers" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codiFkIdNormativa" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="codiFkIdTipoMis" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="numeDurataAnni" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="numeDurataMesi" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="numeDurataGiorni" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="descLoc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descUltPresc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiFkIdCauzione" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="codiFkIdEsito1" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="codiFkIdEsito2" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="codiFkIdEsitoCsz" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="idEsitoSent" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="MisuraPersonale" type="{http://it/eng/giustizia/sitmp/webservices/ricorsoservice}MisuraPersonale"/>
 *         &lt;element name="Cauzione" type="{http://it/eng/giustizia/sitmp/webservices/ricorsoservice}Cauzione" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RelAttoMisuraPersonale", propOrder = {
    "codiKIdProc",
    "codiKIdAtto",
    "codiKIdUffOwner",
    "codiKIdMisuraPers",
    "codiFkIdNormativa",
    "codiFkIdTipoMis",
    "numeDurataAnni",
    "numeDurataMesi",
    "numeDurataGiorni",
    "descLoc",
    "descUltPresc",
    "codiFkIdCauzione",
    "codiFkIdEsito1",
    "codiFkIdEsito2",
    "codiFkIdEsitoCsz",
    "idEsitoSent",
    "misuraPersonale",
    "cauzione"
})
public class RelAttoMisuraPersonale {

    protected long codiKIdProc;
    protected long codiKIdAtto;
    protected int codiKIdUffOwner;
    protected long codiKIdMisuraPers;
    protected Integer codiFkIdNormativa;
    protected int codiFkIdTipoMis;
    protected Short numeDurataAnni;
    protected Short numeDurataMesi;
    protected Short numeDurataGiorni;
    protected String descLoc;
    protected String descUltPresc;
    protected Long codiFkIdCauzione;
    protected Integer codiFkIdEsito1;
    protected Integer codiFkIdEsito2;
    protected Integer codiFkIdEsitoCsz;
    protected Long idEsitoSent;
    @XmlElement(name = "MisuraPersonale", required = true)
    protected MisuraPersonale misuraPersonale;
    @XmlElement(name = "Cauzione")
    protected Cauzione cauzione;

    /**
     * Recupera il valore della proprietà codiKIdProc.
     * 
     */
    public long getCodiKIdProc() {
        return codiKIdProc;
    }

    /**
     * Imposta il valore della proprietà codiKIdProc.
     * 
     */
    public void setCodiKIdProc(long value) {
        this.codiKIdProc = value;
    }

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
     * Recupera il valore della proprietà codiKIdMisuraPers.
     * 
     */
    public long getCodiKIdMisuraPers() {
        return codiKIdMisuraPers;
    }

    /**
     * Imposta il valore della proprietà codiKIdMisuraPers.
     * 
     */
    public void setCodiKIdMisuraPers(long value) {
        this.codiKIdMisuraPers = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdNormativa.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodiFkIdNormativa() {
        return codiFkIdNormativa;
    }

    /**
     * Imposta il valore della proprietà codiFkIdNormativa.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodiFkIdNormativa(Integer value) {
        this.codiFkIdNormativa = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdTipoMis.
     * 
     */
    public int getCodiFkIdTipoMis() {
        return codiFkIdTipoMis;
    }

    /**
     * Imposta il valore della proprietà codiFkIdTipoMis.
     * 
     */
    public void setCodiFkIdTipoMis(int value) {
        this.codiFkIdTipoMis = value;
    }

    /**
     * Recupera il valore della proprietà numeDurataAnni.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getNumeDurataAnni() {
        return numeDurataAnni;
    }

    /**
     * Imposta il valore della proprietà numeDurataAnni.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setNumeDurataAnni(Short value) {
        this.numeDurataAnni = value;
    }

    /**
     * Recupera il valore della proprietà numeDurataMesi.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getNumeDurataMesi() {
        return numeDurataMesi;
    }

    /**
     * Imposta il valore della proprietà numeDurataMesi.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setNumeDurataMesi(Short value) {
        this.numeDurataMesi = value;
    }

    /**
     * Recupera il valore della proprietà numeDurataGiorni.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getNumeDurataGiorni() {
        return numeDurataGiorni;
    }

    /**
     * Imposta il valore della proprietà numeDurataGiorni.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setNumeDurataGiorni(Short value) {
        this.numeDurataGiorni = value;
    }

    /**
     * Recupera il valore della proprietà descLoc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescLoc() {
        return descLoc;
    }

    /**
     * Imposta il valore della proprietà descLoc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescLoc(String value) {
        this.descLoc = value;
    }

    /**
     * Recupera il valore della proprietà descUltPresc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescUltPresc() {
        return descUltPresc;
    }

    /**
     * Imposta il valore della proprietà descUltPresc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescUltPresc(String value) {
        this.descUltPresc = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdCauzione.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCodiFkIdCauzione() {
        return codiFkIdCauzione;
    }

    /**
     * Imposta il valore della proprietà codiFkIdCauzione.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCodiFkIdCauzione(Long value) {
        this.codiFkIdCauzione = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdEsito1.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodiFkIdEsito1() {
        return codiFkIdEsito1;
    }

    /**
     * Imposta il valore della proprietà codiFkIdEsito1.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodiFkIdEsito1(Integer value) {
        this.codiFkIdEsito1 = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdEsito2.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodiFkIdEsito2() {
        return codiFkIdEsito2;
    }

    /**
     * Imposta il valore della proprietà codiFkIdEsito2.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodiFkIdEsito2(Integer value) {
        this.codiFkIdEsito2 = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdEsitoCsz.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodiFkIdEsitoCsz() {
        return codiFkIdEsitoCsz;
    }

    /**
     * Imposta il valore della proprietà codiFkIdEsitoCsz.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodiFkIdEsitoCsz(Integer value) {
        this.codiFkIdEsitoCsz = value;
    }

    /**
     * Recupera il valore della proprietà idEsitoSent.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIdEsitoSent() {
        return idEsitoSent;
    }

    /**
     * Imposta il valore della proprietà idEsitoSent.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIdEsitoSent(Long value) {
        this.idEsitoSent = value;
    }

    /**
     * Recupera il valore della proprietà misuraPersonale.
     * 
     * @return
     *     possible object is
     *     {@link MisuraPersonale }
     *     
     */
    public MisuraPersonale getMisuraPersonale() {
        return misuraPersonale;
    }

    /**
     * Imposta il valore della proprietà misuraPersonale.
     * 
     * @param value
     *     allowed object is
     *     {@link MisuraPersonale }
     *     
     */
    public void setMisuraPersonale(MisuraPersonale value) {
        this.misuraPersonale = value;
    }

    /**
     * Recupera il valore della proprietà cauzione.
     * 
     * @return
     *     possible object is
     *     {@link Cauzione }
     *     
     */
    public Cauzione getCauzione() {
        return cauzione;
    }

    /**
     * Imposta il valore della proprietà cauzione.
     * 
     * @param value
     *     allowed object is
     *     {@link Cauzione }
     *     
     */
    public void setCauzione(Cauzione value) {
        this.cauzione = value;
    }

}
