//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.12.22 alle 12:20:29 PM CET 
//


package it.giustizia.smp.webservice.client.ricorsocassazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per Collezioni complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Collezioni">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiKIdColl" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codiKIdUffOwner" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="descCollezione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numePezzi" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Collezioni", propOrder = {
    "codiKIdColl",
    "codiKIdUffOwner",
    "descCollezione",
    "numePezzi"
})
public class Collezioni {

    protected long codiKIdColl;
    protected int codiKIdUffOwner;
    protected String descCollezione;
    protected long numePezzi;

    /**
     * Recupera il valore della proprietà codiKIdColl.
     * 
     */
    public long getCodiKIdColl() {
        return codiKIdColl;
    }

    /**
     * Imposta il valore della proprietà codiKIdColl.
     * 
     */
    public void setCodiKIdColl(long value) {
        this.codiKIdColl = value;
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
     * Recupera il valore della proprietà descCollezione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescCollezione() {
        return descCollezione;
    }

    /**
     * Imposta il valore della proprietà descCollezione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescCollezione(String value) {
        this.descCollezione = value;
    }

    /**
     * Recupera il valore della proprietà numePezzi.
     * 
     */
    public long getNumePezzi() {
        return numePezzi;
    }

    /**
     * Imposta il valore della proprietà numePezzi.
     * 
     */
    public void setNumePezzi(long value) {
        this.numePezzi = value;
    }

}
