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
 * <p>Classe Java per Avvocati complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Avvocati">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiKIdAvvocato" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codiKIdUffOwner" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="descCognomeAvv" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descNomeAvv" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiFkComForo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="dataNascita" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="descLuogoNascita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descProvNascita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descCodiceFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descPec" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descIndirRes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descCapRes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descComuneRes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descProvRes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descTelRes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descFaxRes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descEmailRes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descIndirDomleg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descCapDomleg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descComuneDomleg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descProvDomleg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descTelDomleg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descFaxDomleg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descEmailDomleg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descIdReginde" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Avvocati", propOrder = {
    "codiKIdAvvocato",
    "codiKIdUffOwner",
    "descCognomeAvv",
    "descNomeAvv",
    "codiFkComForo",
    "dataNascita",
    "descLuogoNascita",
    "descProvNascita",
    "descCodiceFiscale",
    "descPec",
    "descIndirRes",
    "descCapRes",
    "descComuneRes",
    "descProvRes",
    "descTelRes",
    "descFaxRes",
    "descEmailRes",
    "descIndirDomleg",
    "descCapDomleg",
    "descComuneDomleg",
    "descProvDomleg",
    "descTelDomleg",
    "descFaxDomleg",
    "descEmailDomleg",
    "descIdReginde"
})
public class Avvocati {

    protected long codiKIdAvvocato;
    protected int codiKIdUffOwner;
    protected String descCognomeAvv;
    protected String descNomeAvv;
    protected Integer codiFkComForo;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataNascita;
    protected String descLuogoNascita;
    protected String descProvNascita;
    protected String descCodiceFiscale;
    protected String descPec;
    protected String descIndirRes;
    protected String descCapRes;
    protected String descComuneRes;
    protected String descProvRes;
    protected String descTelRes;
    protected String descFaxRes;
    protected String descEmailRes;
    protected String descIndirDomleg;
    protected String descCapDomleg;
    protected String descComuneDomleg;
    protected String descProvDomleg;
    protected String descTelDomleg;
    protected String descFaxDomleg;
    protected String descEmailDomleg;
    protected String descIdReginde;

    /**
     * Recupera il valore della proprietà codiKIdAvvocato.
     * 
     */
    public long getCodiKIdAvvocato() {
        return codiKIdAvvocato;
    }

    /**
     * Imposta il valore della proprietà codiKIdAvvocato.
     * 
     */
    public void setCodiKIdAvvocato(long value) {
        this.codiKIdAvvocato = value;
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
     * Recupera il valore della proprietà descCognomeAvv.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescCognomeAvv() {
        return descCognomeAvv;
    }

    /**
     * Imposta il valore della proprietà descCognomeAvv.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescCognomeAvv(String value) {
        this.descCognomeAvv = value;
    }

    /**
     * Recupera il valore della proprietà descNomeAvv.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescNomeAvv() {
        return descNomeAvv;
    }

    /**
     * Imposta il valore della proprietà descNomeAvv.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescNomeAvv(String value) {
        this.descNomeAvv = value;
    }

    /**
     * Recupera il valore della proprietà codiFkComForo.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodiFkComForo() {
        return codiFkComForo;
    }

    /**
     * Imposta il valore della proprietà codiFkComForo.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodiFkComForo(Integer value) {
        this.codiFkComForo = value;
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
     * Recupera il valore della proprietà descLuogoNascita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescLuogoNascita() {
        return descLuogoNascita;
    }

    /**
     * Imposta il valore della proprietà descLuogoNascita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescLuogoNascita(String value) {
        this.descLuogoNascita = value;
    }

    /**
     * Recupera il valore della proprietà descProvNascita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescProvNascita() {
        return descProvNascita;
    }

    /**
     * Imposta il valore della proprietà descProvNascita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescProvNascita(String value) {
        this.descProvNascita = value;
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

    /**
     * Recupera il valore della proprietà descPec.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescPec() {
        return descPec;
    }

    /**
     * Imposta il valore della proprietà descPec.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescPec(String value) {
        this.descPec = value;
    }

    /**
     * Recupera il valore della proprietà descIndirRes.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescIndirRes() {
        return descIndirRes;
    }

    /**
     * Imposta il valore della proprietà descIndirRes.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescIndirRes(String value) {
        this.descIndirRes = value;
    }

    /**
     * Recupera il valore della proprietà descCapRes.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescCapRes() {
        return descCapRes;
    }

    /**
     * Imposta il valore della proprietà descCapRes.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescCapRes(String value) {
        this.descCapRes = value;
    }

    /**
     * Recupera il valore della proprietà descComuneRes.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescComuneRes() {
        return descComuneRes;
    }

    /**
     * Imposta il valore della proprietà descComuneRes.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescComuneRes(String value) {
        this.descComuneRes = value;
    }

    /**
     * Recupera il valore della proprietà descProvRes.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescProvRes() {
        return descProvRes;
    }

    /**
     * Imposta il valore della proprietà descProvRes.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescProvRes(String value) {
        this.descProvRes = value;
    }

    /**
     * Recupera il valore della proprietà descTelRes.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescTelRes() {
        return descTelRes;
    }

    /**
     * Imposta il valore della proprietà descTelRes.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescTelRes(String value) {
        this.descTelRes = value;
    }

    /**
     * Recupera il valore della proprietà descFaxRes.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescFaxRes() {
        return descFaxRes;
    }

    /**
     * Imposta il valore della proprietà descFaxRes.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescFaxRes(String value) {
        this.descFaxRes = value;
    }

    /**
     * Recupera il valore della proprietà descEmailRes.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescEmailRes() {
        return descEmailRes;
    }

    /**
     * Imposta il valore della proprietà descEmailRes.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescEmailRes(String value) {
        this.descEmailRes = value;
    }

    /**
     * Recupera il valore della proprietà descIndirDomleg.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescIndirDomleg() {
        return descIndirDomleg;
    }

    /**
     * Imposta il valore della proprietà descIndirDomleg.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescIndirDomleg(String value) {
        this.descIndirDomleg = value;
    }

    /**
     * Recupera il valore della proprietà descCapDomleg.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescCapDomleg() {
        return descCapDomleg;
    }

    /**
     * Imposta il valore della proprietà descCapDomleg.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescCapDomleg(String value) {
        this.descCapDomleg = value;
    }

    /**
     * Recupera il valore della proprietà descComuneDomleg.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescComuneDomleg() {
        return descComuneDomleg;
    }

    /**
     * Imposta il valore della proprietà descComuneDomleg.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescComuneDomleg(String value) {
        this.descComuneDomleg = value;
    }

    /**
     * Recupera il valore della proprietà descProvDomleg.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescProvDomleg() {
        return descProvDomleg;
    }

    /**
     * Imposta il valore della proprietà descProvDomleg.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescProvDomleg(String value) {
        this.descProvDomleg = value;
    }

    /**
     * Recupera il valore della proprietà descTelDomleg.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescTelDomleg() {
        return descTelDomleg;
    }

    /**
     * Imposta il valore della proprietà descTelDomleg.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescTelDomleg(String value) {
        this.descTelDomleg = value;
    }

    /**
     * Recupera il valore della proprietà descFaxDomleg.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescFaxDomleg() {
        return descFaxDomleg;
    }

    /**
     * Imposta il valore della proprietà descFaxDomleg.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescFaxDomleg(String value) {
        this.descFaxDomleg = value;
    }

    /**
     * Recupera il valore della proprietà descEmailDomleg.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescEmailDomleg() {
        return descEmailDomleg;
    }

    /**
     * Imposta il valore della proprietà descEmailDomleg.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescEmailDomleg(String value) {
        this.descEmailDomleg = value;
    }

    /**
     * Recupera il valore della proprietà descIdReginde.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescIdReginde() {
        return descIdReginde;
    }

    /**
     * Imposta il valore della proprietà descIdReginde.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescIdReginde(String value) {
        this.descIdReginde = value;
    }

}
