//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2015.03.10 alle 02:34:02 PM CET 
//


package it.giustizia.smp.webservice.client.glmp.servizitrasmissione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per infoTrasmissione complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="infoTrasmissione">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="destinatario" type="{http://www.giustizia.it/gestorelocale/invioAtti}infoDestinatario" minOccurs="0"/>
 *         &lt;element name="idTrasmissione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nomeElenco" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "infoTrasmissione", propOrder = {
    "destinatario",
    "idTrasmissione",
    "mail",
    "nomeElenco"
})
public class InfoTrasmissione {

    protected InfoDestinatario destinatario;
    protected String idTrasmissione;
    protected String mail;
    protected String nomeElenco;

    /**
     * Recupera il valore della proprietà destinatario.
     * 
     * @return
     *     possible object is
     *     {@link InfoDestinatario }
     *     
     */
    public InfoDestinatario getDestinatario() {
        return destinatario;
    }

    /**
     * Imposta il valore della proprietà destinatario.
     * 
     * @param value
     *     allowed object is
     *     {@link InfoDestinatario }
     *     
     */
    public void setDestinatario(InfoDestinatario value) {
        this.destinatario = value;
    }

    /**
     * Recupera il valore della proprietà idTrasmissione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdTrasmissione() {
        return idTrasmissione;
    }

    /**
     * Imposta il valore della proprietà idTrasmissione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdTrasmissione(String value) {
        this.idTrasmissione = value;
    }

    /**
     * Recupera il valore della proprietà mail.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMail() {
        return mail;
    }

    /**
     * Imposta il valore della proprietà mail.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMail(String value) {
        this.mail = value;
    }

    /**
     * Recupera il valore della proprietà nomeElenco.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeElenco() {
        return nomeElenco;
    }

    /**
     * Imposta il valore della proprietà nomeElenco.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeElenco(String value) {
        this.nomeElenco = value;
    }

}
