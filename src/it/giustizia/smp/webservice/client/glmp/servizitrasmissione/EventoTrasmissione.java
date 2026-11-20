//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2015.03.10 alle 02:34:02 PM CET 
//


package it.giustizia.smp.webservice.client.glmp.servizitrasmissione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per eventoTrasmissione complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="eventoTrasmissione">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idEvento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataEvento" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="codiceStato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="messaggio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idRicevuta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eventoTrasmissione", propOrder = {
    "idEvento",
    "dataEvento",
    "codiceStato",
    "messaggio",
    "idRicevuta"
})
public class EventoTrasmissione {

    protected String idEvento;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataEvento;
    protected String codiceStato;
    protected String messaggio;
    protected String idRicevuta;

    /**
     * Recupera il valore della proprietà idEvento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdEvento() {
        return idEvento;
    }

    /**
     * Imposta il valore della proprietà idEvento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdEvento(String value) {
        this.idEvento = value;
    }

    /**
     * Recupera il valore della proprietà dataEvento.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataEvento() {
        return dataEvento;
    }

    /**
     * Imposta il valore della proprietà dataEvento.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataEvento(XMLGregorianCalendar value) {
        this.dataEvento = value;
    }

    /**
     * Recupera il valore della proprietà codiceStato.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceStato() {
        return codiceStato;
    }

    /**
     * Imposta il valore della proprietà codiceStato.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceStato(String value) {
        this.codiceStato = value;
    }

    /**
     * Recupera il valore della proprietà messaggio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessaggio() {
        return messaggio;
    }

    /**
     * Imposta il valore della proprietà messaggio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessaggio(String value) {
        this.messaggio = value;
    }

    /**
     * Recupera il valore della proprietà idRicevuta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdRicevuta() {
        return idRicevuta;
    }

    /**
     * Imposta il valore della proprietà idRicevuta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdRicevuta(String value) {
        this.idRicevuta = value;
    }

}
