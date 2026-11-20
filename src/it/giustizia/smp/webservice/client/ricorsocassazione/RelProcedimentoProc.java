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
 * <p>Classe Java per RelProcedimentoProc complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RelProcedimentoProc">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiKIdProc1" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codiKIdProc2" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="numeAnnoProc2" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="numeProcedimento2" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codiFkIdUffOwner2" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="dataApeProc2" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="flgTipoRel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flgPadre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RelProcedimentoProc", propOrder = {
    "codiKIdProc1",
    "codiKIdProc2",
    "numeAnnoProc2",
    "numeProcedimento2",
    "codiFkIdUffOwner2",
    "dataApeProc2",
    "flgTipoRel",
    "flgPadre"
})
public class RelProcedimentoProc {

    protected long codiKIdProc1;
    protected long codiKIdProc2;
    protected short numeAnnoProc2;
    protected long numeProcedimento2;
    protected int codiFkIdUffOwner2;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataApeProc2;
    protected String flgTipoRel;
    protected String flgPadre;

    /**
     * Recupera il valore della proprietà codiKIdProc1.
     * 
     */
    public long getCodiKIdProc1() {
        return codiKIdProc1;
    }

    /**
     * Imposta il valore della proprietà codiKIdProc1.
     * 
     */
    public void setCodiKIdProc1(long value) {
        this.codiKIdProc1 = value;
    }

    /**
     * Recupera il valore della proprietà codiKIdProc2.
     * 
     */
    public long getCodiKIdProc2() {
        return codiKIdProc2;
    }

    /**
     * Imposta il valore della proprietà codiKIdProc2.
     * 
     */
    public void setCodiKIdProc2(long value) {
        this.codiKIdProc2 = value;
    }

    /**
     * Recupera il valore della proprietà numeAnnoProc2.
     * 
     */
    public short getNumeAnnoProc2() {
        return numeAnnoProc2;
    }

    /**
     * Imposta il valore della proprietà numeAnnoProc2.
     * 
     */
    public void setNumeAnnoProc2(short value) {
        this.numeAnnoProc2 = value;
    }

    /**
     * Recupera il valore della proprietà numeProcedimento2.
     * 
     */
    public long getNumeProcedimento2() {
        return numeProcedimento2;
    }

    /**
     * Imposta il valore della proprietà numeProcedimento2.
     * 
     */
    public void setNumeProcedimento2(long value) {
        this.numeProcedimento2 = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdUffOwner2.
     * 
     */
    public int getCodiFkIdUffOwner2() {
        return codiFkIdUffOwner2;
    }

    /**
     * Imposta il valore della proprietà codiFkIdUffOwner2.
     * 
     */
    public void setCodiFkIdUffOwner2(int value) {
        this.codiFkIdUffOwner2 = value;
    }

    /**
     * Recupera il valore della proprietà dataApeProc2.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataApeProc2() {
        return dataApeProc2;
    }

    /**
     * Imposta il valore della proprietà dataApeProc2.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataApeProc2(XMLGregorianCalendar value) {
        this.dataApeProc2 = value;
    }

    /**
     * Recupera il valore della proprietà flgTipoRel.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlgTipoRel() {
        return flgTipoRel;
    }

    /**
     * Imposta il valore della proprietà flgTipoRel.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlgTipoRel(String value) {
        this.flgTipoRel = value;
    }

    /**
     * Recupera il valore della proprietà flgPadre.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlgPadre() {
        return flgPadre;
    }

    /**
     * Imposta il valore della proprietà flgPadre.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlgPadre(String value) {
        this.flgPadre = value;
    }

}
