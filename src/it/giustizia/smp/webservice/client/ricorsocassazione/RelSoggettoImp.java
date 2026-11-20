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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per RelSoggettoImp complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RelSoggettoImp">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiKIdImpugnaz" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codiKIdUffOwner" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="codiKIdProgr" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="descTipoProp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flgTipoParte" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiFkIdAvvocato" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="descNomeAvv" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descCognAvv" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiFkIdSoggInt" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="Avvocati" type="{http://it/eng/giustizia/sitmp/webservices/ricorsoservice}Avvocati" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RelSoggettoImp", propOrder = {
    "codiKIdImpugnaz",
    "codiKIdUffOwner",
    "codiKIdProgr",
    "descTipoProp",
    "flgTipoParte",
    "codiFkIdAvvocato",
    "descNomeAvv",
    "descCognAvv",
    "codiFkIdSoggInt",
    "avvocati"
})
public class RelSoggettoImp {

    protected long codiKIdImpugnaz;
    protected int codiKIdUffOwner;
    protected short codiKIdProgr;
    protected String descTipoProp;
    protected String flgTipoParte;
    protected Long codiFkIdAvvocato;
    protected String descNomeAvv;
    protected String descCognAvv;
    protected Long codiFkIdSoggInt;
    @XmlElement(name = "Avvocati", required = true)
    protected List<Avvocati> avvocati;

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
     * Recupera il valore della proprietà codiKIdProgr.
     * 
     */
    public short getCodiKIdProgr() {
        return codiKIdProgr;
    }

    /**
     * Imposta il valore della proprietà codiKIdProgr.
     * 
     */
    public void setCodiKIdProgr(short value) {
        this.codiKIdProgr = value;
    }

    /**
     * Recupera il valore della proprietà descTipoProp.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescTipoProp() {
        return descTipoProp;
    }

    /**
     * Imposta il valore della proprietà descTipoProp.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescTipoProp(String value) {
        this.descTipoProp = value;
    }

    /**
     * Recupera il valore della proprietà flgTipoParte.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlgTipoParte() {
        return flgTipoParte;
    }

    /**
     * Imposta il valore della proprietà flgTipoParte.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlgTipoParte(String value) {
        this.flgTipoParte = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdAvvocato.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCodiFkIdAvvocato() {
        return codiFkIdAvvocato;
    }

    /**
     * Imposta il valore della proprietà codiFkIdAvvocato.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCodiFkIdAvvocato(Long value) {
        this.codiFkIdAvvocato = value;
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
     * Recupera il valore della proprietà descCognAvv.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescCognAvv() {
        return descCognAvv;
    }

    /**
     * Imposta il valore della proprietà descCognAvv.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescCognAvv(String value) {
        this.descCognAvv = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdSoggInt.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCodiFkIdSoggInt() {
        return codiFkIdSoggInt;
    }

    /**
     * Imposta il valore della proprietà codiFkIdSoggInt.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCodiFkIdSoggInt(Long value) {
        this.codiFkIdSoggInt = value;
    }

    /**
     * Gets the value of the avvocati property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the avvocati property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAvvocati().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Avvocati }
     * 
     * 
     */
    public List<Avvocati> getAvvocati() {
        if (avvocati == null) {
            avvocati = new ArrayList<Avvocati>();
        }
        return this.avvocati;
    }

}
