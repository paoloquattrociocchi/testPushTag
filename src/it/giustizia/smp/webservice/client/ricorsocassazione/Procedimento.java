//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.12.22 alle 12:20:29 PM CET 
//


package it.giustizia.smp.webservice.client.ricorsocassazione;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per Procedimento complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Procedimento">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiFkIdTipoFasc" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="codiFkIdTipoProc" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="codiFkIdUffOwner" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="codiKIdProc" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="dataIscrizione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="flgAntimafia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flgAntiterrorismo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flgOrdinaria" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeAnnoProc" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="numeProc" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="RelAttoProc" type="{http://it/eng/giustizia/sitmp/webservices/ricorsoservice}RelAttoProc" maxOccurs="2" minOccurs="2"/>
 *         &lt;element name="RelSoggettoImp" type="{http://it/eng/giustizia/sitmp/webservices/ricorsoservice}RelSoggettoImp" maxOccurs="unbounded"/>
 *         &lt;element name="RelProcSoggetto" type="{http://it/eng/giustizia/sitmp/webservices/ricorsoservice}RelProcSoggetto" maxOccurs="unbounded"/>
 *         &lt;element name="RelProcBene" type="{http://it/eng/giustizia/sitmp/webservices/ricorsoservice}RelProcBene" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Procedimento", propOrder = {
    "codiFkIdTipoFasc",
    "codiFkIdTipoProc",
    "codiFkIdUffOwner",
    "codiKIdProc",
    "dataIscrizione",
    "flgAntimafia",
    "flgAntiterrorismo",
    "flgOrdinaria",
    "numeAnnoProc",
    "numeProc",
    "relAttoProc",
    "relSoggettoImp",
    "relProcSoggetto",
    "relProcBene"
})
public class Procedimento {

    protected Integer codiFkIdTipoFasc;
    protected Integer codiFkIdTipoProc;
    protected int codiFkIdUffOwner;
    protected long codiKIdProc;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataIscrizione;
    protected String flgAntimafia;
    protected String flgAntiterrorismo;
    protected String flgOrdinaria;
    protected Short numeAnnoProc;
    protected Long numeProc;
    @XmlElement(name = "RelAttoProc", required = true)
    protected List<RelAttoProc> relAttoProc;
    @XmlElement(name = "RelSoggettoImp", required = true)
    protected List<RelSoggettoImp> relSoggettoImp;
    @XmlElement(name = "RelProcSoggetto", required = true)
    protected List<RelProcSoggetto> relProcSoggetto;
    @XmlElement(name = "RelProcBene")
    protected List<RelProcBene> relProcBene;

    /**
     * Recupera il valore della proprietà codiFkIdTipoFasc.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodiFkIdTipoFasc() {
        return codiFkIdTipoFasc;
    }

    /**
     * Imposta il valore della proprietà codiFkIdTipoFasc.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodiFkIdTipoFasc(Integer value) {
        this.codiFkIdTipoFasc = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdTipoProc.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodiFkIdTipoProc() {
        return codiFkIdTipoProc;
    }

    /**
     * Imposta il valore della proprietà codiFkIdTipoProc.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodiFkIdTipoProc(Integer value) {
        this.codiFkIdTipoProc = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdUffOwner.
     * 
     */
    public int getCodiFkIdUffOwner() {
        return codiFkIdUffOwner;
    }

    /**
     * Imposta il valore della proprietà codiFkIdUffOwner.
     * 
     */
    public void setCodiFkIdUffOwner(int value) {
        this.codiFkIdUffOwner = value;
    }

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
     * Recupera il valore della proprietà dataIscrizione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataIscrizione() {
        return dataIscrizione;
    }

    /**
     * Imposta il valore della proprietà dataIscrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataIscrizione(XMLGregorianCalendar value) {
        this.dataIscrizione = value;
    }

    /**
     * Recupera il valore della proprietà flgAntimafia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlgAntimafia() {
        return flgAntimafia;
    }

    /**
     * Imposta il valore della proprietà flgAntimafia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlgAntimafia(String value) {
        this.flgAntimafia = value;
    }

    /**
     * Recupera il valore della proprietà flgAntiterrorismo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlgAntiterrorismo() {
        return flgAntiterrorismo;
    }

    /**
     * Imposta il valore della proprietà flgAntiterrorismo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlgAntiterrorismo(String value) {
        this.flgAntiterrorismo = value;
    }

    /**
     * Recupera il valore della proprietà flgOrdinaria.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlgOrdinaria() {
        return flgOrdinaria;
    }

    /**
     * Imposta il valore della proprietà flgOrdinaria.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlgOrdinaria(String value) {
        this.flgOrdinaria = value;
    }

    /**
     * Recupera il valore della proprietà numeAnnoProc.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getNumeAnnoProc() {
        return numeAnnoProc;
    }

    /**
     * Imposta il valore della proprietà numeAnnoProc.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setNumeAnnoProc(Short value) {
        this.numeAnnoProc = value;
    }

    /**
     * Recupera il valore della proprietà numeProc.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getNumeProc() {
        return numeProc;
    }

    /**
     * Imposta il valore della proprietà numeProc.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNumeProc(Long value) {
        this.numeProc = value;
    }

    /**
     * Gets the value of the relAttoProc property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the relAttoProc property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRelAttoProc().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RelAttoProc }
     * 
     * 
     */
    public List<RelAttoProc> getRelAttoProc() {
        if (relAttoProc == null) {
            relAttoProc = new ArrayList<RelAttoProc>();
        }
        return this.relAttoProc;
    }

    /**
     * Gets the value of the relSoggettoImp property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the relSoggettoImp property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRelSoggettoImp().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RelSoggettoImp }
     * 
     * 
     */
    public List<RelSoggettoImp> getRelSoggettoImp() {
        if (relSoggettoImp == null) {
            relSoggettoImp = new ArrayList<RelSoggettoImp>();
        }
        return this.relSoggettoImp;
    }

    /**
     * Gets the value of the relProcSoggetto property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the relProcSoggetto property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRelProcSoggetto().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RelProcSoggetto }
     * 
     * 
     */
    public List<RelProcSoggetto> getRelProcSoggetto() {
        if (relProcSoggetto == null) {
            relProcSoggetto = new ArrayList<RelProcSoggetto>();
        }
        return this.relProcSoggetto;
    }

    /**
     * Gets the value of the relProcBene property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the relProcBene property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRelProcBene().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RelProcBene }
     * 
     * 
     */
    public List<RelProcBene> getRelProcBene() {
        if (relProcBene == null) {
            relProcBene = new ArrayList<RelProcBene>();
        }
        return this.relProcBene;
    }

}
