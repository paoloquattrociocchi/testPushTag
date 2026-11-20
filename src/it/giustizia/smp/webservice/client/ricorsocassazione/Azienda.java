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
 * <p>Classe Java per Azienda complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Azienda">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiKIdAzienda" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codiKIdUffOwner" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="descRagioneSoc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descPIva" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descCodFisc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descCciaa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descRappresSoc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descAmministratore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="impoCapitaleSoc" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="numeDipendenti" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="codiFkIdSettAtt" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Azienda", propOrder = {
    "codiKIdAzienda",
    "codiKIdUffOwner",
    "descRagioneSoc",
    "descPIva",
    "descCodFisc",
    "descCciaa",
    "descRappresSoc",
    "descAmministratore",
    "impoCapitaleSoc",
    "numeDipendenti",
    "codiFkIdSettAtt"
})
public class Azienda {

    protected long codiKIdAzienda;
    protected int codiKIdUffOwner;
    protected String descRagioneSoc;
    protected String descPIva;
    protected String descCodFisc;
    protected String descCciaa;
    protected String descRappresSoc;
    protected String descAmministratore;
    protected BigDecimal impoCapitaleSoc;
    protected Long numeDipendenti;
    protected Integer codiFkIdSettAtt;

    /**
     * Recupera il valore della proprietà codiKIdAzienda.
     * 
     */
    public long getCodiKIdAzienda() {
        return codiKIdAzienda;
    }

    /**
     * Imposta il valore della proprietà codiKIdAzienda.
     * 
     */
    public void setCodiKIdAzienda(long value) {
        this.codiKIdAzienda = value;
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
     * Recupera il valore della proprietà descPIva.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescPIva() {
        return descPIva;
    }

    /**
     * Imposta il valore della proprietà descPIva.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescPIva(String value) {
        this.descPIva = value;
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
     * Recupera il valore della proprietà descCciaa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescCciaa() {
        return descCciaa;
    }

    /**
     * Imposta il valore della proprietà descCciaa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescCciaa(String value) {
        this.descCciaa = value;
    }

    /**
     * Recupera il valore della proprietà descRappresSoc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescRappresSoc() {
        return descRappresSoc;
    }

    /**
     * Imposta il valore della proprietà descRappresSoc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescRappresSoc(String value) {
        this.descRappresSoc = value;
    }

    /**
     * Recupera il valore della proprietà descAmministratore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescAmministratore() {
        return descAmministratore;
    }

    /**
     * Imposta il valore della proprietà descAmministratore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescAmministratore(String value) {
        this.descAmministratore = value;
    }

    /**
     * Recupera il valore della proprietà impoCapitaleSoc.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImpoCapitaleSoc() {
        return impoCapitaleSoc;
    }

    /**
     * Imposta il valore della proprietà impoCapitaleSoc.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImpoCapitaleSoc(BigDecimal value) {
        this.impoCapitaleSoc = value;
    }

    /**
     * Recupera il valore della proprietà numeDipendenti.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getNumeDipendenti() {
        return numeDipendenti;
    }

    /**
     * Imposta il valore della proprietà numeDipendenti.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNumeDipendenti(Long value) {
        this.numeDipendenti = value;
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

}
