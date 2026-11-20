//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2015.03.10 alle 02:34:02 PM CET 
//


package it.giustizia.smp.webservice.client.glmp.servizitrasmissione;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per statoTrasmissione complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="statoTrasmissione">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="avvisoDownload" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="destinatario" type="{http://www.giustizia.it/gestorelocale/invioAtti}infoDestinatario" minOccurs="0"/>
 *         &lt;element name="storicoEventi" type="{http://www.giustizia.it/gestorelocale/invioAtti}eventoTrasmissione" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="idTrasmissione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nomeElenco" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sorgenti" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="stato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "statoTrasmissione", propOrder = {
    "avvisoDownload",
    "destinatario",
    "storicoEventi",
    "idTrasmissione",
    "mail",
    "nomeElenco",
    "sorgenti",
    "stato"
})
public class StatoTrasmissione {

    protected boolean avvisoDownload;
    protected InfoDestinatario destinatario;
    protected List<EventoTrasmissione> storicoEventi;
    protected String idTrasmissione;
    protected String mail;
    protected String nomeElenco;
    protected String sorgenti;
    protected String stato;

    /**
     * Recupera il valore della proprietà avvisoDownload.
     * 
     */
    public boolean isAvvisoDownload() {
        return avvisoDownload;
    }

    /**
     * Imposta il valore della proprietà avvisoDownload.
     * 
     */
    public void setAvvisoDownload(boolean value) {
        this.avvisoDownload = value;
    }

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
     * Gets the value of the storicoEventi property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the storicoEventi property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStoricoEventi().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EventoTrasmissione }
     * 
     * 
     */
    public List<EventoTrasmissione> getStoricoEventi() {
        if (storicoEventi == null) {
            storicoEventi = new ArrayList<EventoTrasmissione>();
        }
        return this.storicoEventi;
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

    /**
     * Recupera il valore della proprietà sorgenti.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSorgenti() {
        return sorgenti;
    }

    /**
     * Imposta il valore della proprietà sorgenti.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSorgenti(String value) {
        this.sorgenti = value;
    }

    /**
     * Recupera il valore della proprietà stato.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStato() {
        return stato;
    }

    /**
     * Imposta il valore della proprietà stato.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStato(String value) {
        this.stato = value;
    }

}
