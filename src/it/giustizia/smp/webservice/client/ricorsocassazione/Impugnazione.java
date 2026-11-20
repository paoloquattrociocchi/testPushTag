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
 * <p>Classe Java per Impugnazione complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Impugnazione">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiKIdImpugnaz" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codiKIdUffOwner" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="numeAnnoAtto" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="numeAtto" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="codiFkIdUffDep" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="codiFkIdIstDet" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="flgUffDep" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataDepImp" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="oraDepImp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Impugnazione", propOrder = {
    "codiKIdImpugnaz",
    "codiKIdUffOwner",
    "numeAnnoAtto",
    "numeAtto",
    "codiFkIdUffDep",
    "codiFkIdIstDet",
    "flgUffDep",
    "dataDepImp",
    "oraDepImp"
})
public class Impugnazione {

    protected long codiKIdImpugnaz;
    protected int codiKIdUffOwner;
    protected Short numeAnnoAtto;
    protected Long numeAtto;
    protected Integer codiFkIdUffDep;
    protected Integer codiFkIdIstDet;
    protected String flgUffDep;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataDepImp;
    protected String oraDepImp;

    /**
     * Recupera il valore della proprietà codiKIdImpugnaz.
     * 
     */
    public long getCodiKIdImpugnaz() {
        return codiKIdImpugnaz;
    }

    /**
     * Imposta il valore della proprietà codiKIdImpugnaz.
     * 
     */
    public void setCodiKIdImpugnaz(long value) {
        this.codiKIdImpugnaz = value;
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
     * Recupera il valore della proprietà numeAnnoAtto.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getNumeAnnoAtto() {
        return numeAnnoAtto;
    }

    /**
     * Imposta il valore della proprietà numeAnnoAtto.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setNumeAnnoAtto(Short value) {
        this.numeAnnoAtto = value;
    }

    /**
     * Recupera il valore della proprietà numeAtto.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getNumeAtto() {
        return numeAtto;
    }

    /**
     * Imposta il valore della proprietà numeAtto.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNumeAtto(Long value) {
        this.numeAtto = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdUffDep.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodiFkIdUffDep() {
        return codiFkIdUffDep;
    }

    /**
     * Imposta il valore della proprietà codiFkIdUffDep.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodiFkIdUffDep(Integer value) {
        this.codiFkIdUffDep = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdIstDet.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodiFkIdIstDet() {
        return codiFkIdIstDet;
    }

    /**
     * Imposta il valore della proprietà codiFkIdIstDet.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodiFkIdIstDet(Integer value) {
        this.codiFkIdIstDet = value;
    }

    /**
     * Recupera il valore della proprietà flgUffDep.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlgUffDep() {
        return flgUffDep;
    }

    /**
     * Imposta il valore della proprietà flgUffDep.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlgUffDep(String value) {
        this.flgUffDep = value;
    }

    /**
     * Recupera il valore della proprietà dataDepImp.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataDepImp() {
        return dataDepImp;
    }

    /**
     * Imposta il valore della proprietà dataDepImp.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataDepImp(XMLGregorianCalendar value) {
        this.dataDepImp = value;
    }

    /**
     * Recupera il valore della proprietà oraDepImp.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOraDepImp() {
        return oraDepImp;
    }

    /**
     * Imposta il valore della proprietà oraDepImp.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOraDepImp(String value) {
        this.oraDepImp = value;
    }

}
