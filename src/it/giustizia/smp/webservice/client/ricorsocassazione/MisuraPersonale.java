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
 * <p>Classe Java per MisuraPersonale complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="MisuraPersonale">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiKIdMisura" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codiKIdUffOwner" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="codiFkIdSoggInt" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="codiFkIdCaratMis" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MisuraPersonale", propOrder = {
    "codiKIdMisura",
    "codiKIdUffOwner",
    "codiFkIdSoggInt",
    "codiFkIdCaratMis"
})
public class MisuraPersonale {

    protected long codiKIdMisura;
    protected int codiKIdUffOwner;
    protected Long codiFkIdSoggInt;
    protected String codiFkIdCaratMis;

    /**
     * Recupera il valore della proprietà codiKIdMisura.
     * 
     */
    public long getCodiKIdMisura() {
        return codiKIdMisura;
    }

    /**
     * Imposta il valore della proprietà codiKIdMisura.
     * 
     */
    public void setCodiKIdMisura(long value) {
        this.codiKIdMisura = value;
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
     * Recupera il valore della proprietà codiFkIdCaratMis.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiFkIdCaratMis() {
        return codiFkIdCaratMis;
    }

    /**
     * Imposta il valore della proprietà codiFkIdCaratMis.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiFkIdCaratMis(String value) {
        this.codiFkIdCaratMis = value;
    }

}
