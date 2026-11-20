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
 * <p>Classe Java per Animali complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Animali">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiKIdAnimale" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codiKIdUffOwner" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="numeAnimali" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="descCapo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Animali", propOrder = {
    "codiKIdAnimale",
    "codiKIdUffOwner",
    "numeAnimali",
    "descCapo"
})
public class Animali {

    protected long codiKIdAnimale;
    protected int codiKIdUffOwner;
    protected Long numeAnimali;
    protected String descCapo;

    /**
     * Recupera il valore della proprietà codiKIdAnimale.
     * 
     */
    public long getCodiKIdAnimale() {
        return codiKIdAnimale;
    }

    /**
     * Imposta il valore della proprietà codiKIdAnimale.
     * 
     */
    public void setCodiKIdAnimale(long value) {
        this.codiKIdAnimale = value;
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
     * Recupera il valore della proprietà numeAnimali.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getNumeAnimali() {
        return numeAnimali;
    }

    /**
     * Imposta il valore della proprietà numeAnimali.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNumeAnimali(Long value) {
        this.numeAnimali = value;
    }

    /**
     * Recupera il valore della proprietà descCapo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescCapo() {
        return descCapo;
    }

    /**
     * Imposta il valore della proprietà descCapo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescCapo(String value) {
        this.descCapo = value;
    }

}
