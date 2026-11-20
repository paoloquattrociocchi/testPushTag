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
 * <p>Classe Java per RelProcSoggetto complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RelProcSoggetto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiKIdProc" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codiKIdSoggInt" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codiKIdUffOwner" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="codiFkIdVer" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="codiFkIdRuolo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Soggetto" type="{http://it/eng/giustizia/sitmp/webservices/ricorsoservice}Soggetto"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RelProcSoggetto", propOrder = {
    "codiKIdProc",
    "codiKIdSoggInt",
    "codiKIdUffOwner",
    "codiFkIdVer",
    "codiFkIdRuolo",
    "soggetto"
})
public class RelProcSoggetto {

    protected long codiKIdProc;
    protected long codiKIdSoggInt;
    protected int codiKIdUffOwner;
    protected short codiFkIdVer;
    protected Integer codiFkIdRuolo;
    @XmlElement(name = "Soggetto", required = true)
    protected Soggetto soggetto;

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
     * Recupera il valore della proprietà codiKIdSoggInt.
     * 
     */
    public long getCodiKIdSoggInt() {
        return codiKIdSoggInt;
    }

    /**
     * Imposta il valore della proprietà codiKIdSoggInt.
     * 
     */
    public void setCodiKIdSoggInt(long value) {
        this.codiKIdSoggInt = value;
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
     * Recupera il valore della proprietà codiFkIdVer.
     * 
     */
    public short getCodiFkIdVer() {
        return codiFkIdVer;
    }

    /**
     * Imposta il valore della proprietà codiFkIdVer.
     * 
     */
    public void setCodiFkIdVer(short value) {
        this.codiFkIdVer = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdRuolo.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodiFkIdRuolo() {
        return codiFkIdRuolo;
    }

    /**
     * Imposta il valore della proprietà codiFkIdRuolo.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodiFkIdRuolo(Integer value) {
        this.codiFkIdRuolo = value;
    }

    /**
     * Recupera il valore della proprietà soggetto.
     * 
     * @return
     *     possible object is
     *     {@link Soggetto }
     *     
     */
    public Soggetto getSoggetto() {
        return soggetto;
    }

    /**
     * Imposta il valore della proprietà soggetto.
     * 
     * @param value
     *     allowed object is
     *     {@link Soggetto }
     *     
     */
    public void setSoggetto(Soggetto value) {
        this.soggetto = value;
    }

}
