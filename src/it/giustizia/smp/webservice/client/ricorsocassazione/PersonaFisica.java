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
 * <p>Classe Java per PersonaFisica complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="PersonaFisica">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiKIdPersonaFis" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codiKIdUffOwner" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="codiKIdVer" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="descCognome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descNome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flgSesso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataNascita" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="flgDataPresunta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiFkIdStatoNasc" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="codiFkIdComNasc" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="descComEsteroNasc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descCodiceFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonaFisica", propOrder = {
    "codiKIdPersonaFis",
    "codiKIdUffOwner",
    "codiKIdVer",
    "descCognome",
    "descNome",
    "flgSesso",
    "dataNascita",
    "flgDataPresunta",
    "codiFkIdStatoNasc",
    "codiFkIdComNasc",
    "descComEsteroNasc",
    "descCodiceFiscale"
})
public class PersonaFisica {

    protected long codiKIdPersonaFis;
    protected int codiKIdUffOwner;
    protected short codiKIdVer;
    protected String descCognome;
    protected String descNome;
    protected String flgSesso;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataNascita;
    protected String flgDataPresunta;
    protected Integer codiFkIdStatoNasc;
    protected Integer codiFkIdComNasc;
    protected String descComEsteroNasc;
    protected String descCodiceFiscale;

    /**
     * Recupera il valore della proprietà codiKIdPersonaFis.
     * 
     */
    public long getCodiKIdPersonaFis() {
        return codiKIdPersonaFis;
    }

    /**
     * Imposta il valore della proprietà codiKIdPersonaFis.
     * 
     */
    public void setCodiKIdPersonaFis(long value) {
        this.codiKIdPersonaFis = value;
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
     * Recupera il valore della proprietà descCognome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescCognome() {
        return descCognome;
    }

    /**
     * Imposta il valore della proprietà descCognome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescCognome(String value) {
        this.descCognome = value;
    }

    /**
     * Recupera il valore della proprietà descNome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescNome() {
        return descNome;
    }

    /**
     * Imposta il valore della proprietà descNome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescNome(String value) {
        this.descNome = value;
    }

    /**
     * Recupera il valore della proprietà flgSesso.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlgSesso() {
        return flgSesso;
    }

    /**
     * Imposta il valore della proprietà flgSesso.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlgSesso(String value) {
        this.flgSesso = value;
    }

    /**
     * Recupera il valore della proprietà dataNascita.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataNascita() {
        return dataNascita;
    }

    /**
     * Imposta il valore della proprietà dataNascita.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataNascita(XMLGregorianCalendar value) {
        this.dataNascita = value;
    }

    /**
     * Recupera il valore della proprietà flgDataPresunta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlgDataPresunta() {
        return flgDataPresunta;
    }

    /**
     * Imposta il valore della proprietà flgDataPresunta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlgDataPresunta(String value) {
        this.flgDataPresunta = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdStatoNasc.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodiFkIdStatoNasc() {
        return codiFkIdStatoNasc;
    }

    /**
     * Imposta il valore della proprietà codiFkIdStatoNasc.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodiFkIdStatoNasc(Integer value) {
        this.codiFkIdStatoNasc = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdComNasc.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodiFkIdComNasc() {
        return codiFkIdComNasc;
    }

    /**
     * Imposta il valore della proprietà codiFkIdComNasc.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodiFkIdComNasc(Integer value) {
        this.codiFkIdComNasc = value;
    }

    /**
     * Recupera il valore della proprietà descComEsteroNasc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescComEsteroNasc() {
        return descComEsteroNasc;
    }

    /**
     * Imposta il valore della proprietà descComEsteroNasc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescComEsteroNasc(String value) {
        this.descComEsteroNasc = value;
    }

    /**
     * Recupera il valore della proprietà descCodiceFiscale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescCodiceFiscale() {
        return descCodiceFiscale;
    }

    /**
     * Imposta il valore della proprietà descCodiceFiscale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescCodiceFiscale(String value) {
        this.descCodiceFiscale = value;
    }

}
