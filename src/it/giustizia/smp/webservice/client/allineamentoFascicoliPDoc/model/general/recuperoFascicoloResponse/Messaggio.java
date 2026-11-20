//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2020.07.13 alle 02:48:55 PM CEST 
//


package it.giustizia.smp.webservice.client.allineamentoFascicoliPDoc.model.general.recuperoFascicoloResponse;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}codicemessaggio"/>
 *         &lt;element ref="{}messagebody"/>
 *         &lt;element ref="{}esito"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "codicemessaggio",
    "messagebody",
    "esito"
})
@XmlRootElement(name = "messaggio")
public class Messaggio {

    @XmlElement(required = true)
    protected BigInteger codicemessaggio;
    @XmlElement(required = true)
    protected String messagebody;
    protected boolean esito;

    /**
     * Recupera il valore della proprietà codicemessaggio.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCodicemessaggio() {
        return codicemessaggio;
    }

    /**
     * Imposta il valore della proprietà codicemessaggio.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCodicemessaggio(BigInteger value) {
        this.codicemessaggio = value;
    }

    /**
     * Recupera il valore della proprietà messagebody.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessagebody() {
        return messagebody;
    }

    /**
     * Imposta il valore della proprietà messagebody.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessagebody(String value) {
        this.messagebody = value;
    }

    /**
     * Recupera il valore della proprietà esito.
     * 
     */
    public boolean isEsito() {
        return esito;
    }

    /**
     * Imposta il valore della proprietà esito.
     * 
     */
    public void setEsito(boolean value) {
        this.esito = value;
    }

}
