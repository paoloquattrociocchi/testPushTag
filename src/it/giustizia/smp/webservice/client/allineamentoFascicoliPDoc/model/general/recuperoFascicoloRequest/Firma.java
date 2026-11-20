//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2020.07.13 alle 02:50:11 PM CEST 
//


package it.giustizia.smp.webservice.client.allineamentoFascicoliPDoc.model.general.recuperoFascicoloRequest;

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
 *         &lt;element ref="{}autenticazione"/>
 *         &lt;element ref="{}codicefascicolo"/>
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
    "autenticazione",
    "codicefascicolo"
})
@XmlRootElement(name = "firma")
public class Firma {

    @XmlElement(required = true)
    protected Autenticazione autenticazione;
    @XmlElement(required = true)
    protected String codicefascicolo;

    /**
     * Recupera il valore della proprietà autenticazione.
     * 
     * @return
     *     possible object is
     *     {@link Autenticazione }
     *     
     */
    public Autenticazione getAutenticazione() {
        return autenticazione;
    }

    /**
     * Imposta il valore della proprietà autenticazione.
     * 
     * @param value
     *     allowed object is
     *     {@link Autenticazione }
     *     
     */
    public void setAutenticazione(Autenticazione value) {
        this.autenticazione = value;
    }

    /**
     * Recupera il valore della proprietà codicefascicolo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodicefascicolo() {
        return codicefascicolo;
    }

    /**
     * Imposta il valore della proprietà codicefascicolo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodicefascicolo(String value) {
        this.codicefascicolo = value;
    }

}
