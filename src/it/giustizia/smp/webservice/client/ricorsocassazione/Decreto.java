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
 * <p>Classe Java per Decreto complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Decreto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiKIdDecreto" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codiKIdUffOwner" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="numeAnnoAtto" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="numeAtto" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Decreto", propOrder = {
    "codiKIdDecreto",
    "codiKIdUffOwner",
    "numeAnnoAtto",
    "numeAtto"
})
public class Decreto {

    protected long codiKIdDecreto;
    protected int codiKIdUffOwner;
    protected Short numeAnnoAtto;
    protected Long numeAtto;

    /**
     * Recupera il valore della proprietà codiKIdDecreto.
     * 
     */
    public long getCodiKIdDecreto() {
        return codiKIdDecreto;
    }

    /**
     * Imposta il valore della proprietà codiKIdDecreto.
     * 
     */
    public void setCodiKIdDecreto(long value) {
        this.codiKIdDecreto = value;
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

}
