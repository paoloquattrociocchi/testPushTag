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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per notifica complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="notifica">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="oggettoComunicazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="avvisoDownload" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="allegaContenuti" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="allegati" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="destinatari" type="{http://www.giustizia.it/gestorelocale/invioAtti}infoDestinatario" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="attributi" type="{http://www.giustizia.it/gestorelocale/invioAtti}attributo" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "notifica", propOrder = {
    "oggettoComunicazione",
    "avvisoDownload",
    "allegaContenuti",
    "allegati",
    "destinatari",
    "attributi"
})
public class Notifica {

    protected String oggettoComunicazione;
    protected boolean avvisoDownload;
    protected boolean allegaContenuti;
    @XmlElement(nillable = true)
    protected List<String> allegati;
    @XmlElement(nillable = true)
    protected List<InfoDestinatario> destinatari;
    @XmlElement(nillable = true)
    protected List<Attributo> attributi;

    /**
     * Recupera il valore della proprietà oggettoComunicazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOggettoComunicazione() {
        return oggettoComunicazione;
    }

    /**
     * Imposta il valore della proprietà oggettoComunicazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOggettoComunicazione(String value) {
        this.oggettoComunicazione = value;
    }

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
     * Recupera il valore della proprietà allegaContenuti.
     * 
     */
    public boolean isAllegaContenuti() {
        return allegaContenuti;
    }

    /**
     * Imposta il valore della proprietà allegaContenuti.
     * 
     */
    public void setAllegaContenuti(boolean value) {
        this.allegaContenuti = value;
    }

    /**
     * Gets the value of the allegati property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the allegati property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAllegati().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getAllegati() {
        if (allegati == null) {
            allegati = new ArrayList<String>();
        }
        return this.allegati;
    }

    /**
     * Gets the value of the destinatari property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the destinatari property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDestinatari().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InfoDestinatario }
     * 
     * 
     */
    public List<InfoDestinatario> getDestinatari() {
        if (destinatari == null) {
            destinatari = new ArrayList<InfoDestinatario>();
        }
        return this.destinatari;
    }

    /**
     * Gets the value of the attributi property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attributi property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttributi().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Attributo }
     * 
     * 
     */
    public List<Attributo> getAttributi() {
        if (attributi == null) {
            attributi = new ArrayList<Attributo>();
        }
        return this.attributi;
    }

}
