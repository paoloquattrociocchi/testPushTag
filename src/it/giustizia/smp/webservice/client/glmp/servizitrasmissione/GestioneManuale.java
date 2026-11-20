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
 * <p>Classe Java per gestioneManuale complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="gestioneManuale">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idTrasmissione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ritiroInCancelleria" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "gestioneManuale", propOrder = {
    "idTrasmissione",
    "descrizione",
    "ritiroInCancelleria"
})
public class GestioneManuale {

    protected String idTrasmissione;
    protected String descrizione;
    protected boolean ritiroInCancelleria;

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
     * Recupera il valore della proprietà descrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Imposta il valore della proprietà descrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizione(String value) {
        this.descrizione = value;
    }

    /**
     * Recupera il valore della proprietà ritiroInCancelleria.
     * 
     */
    public boolean isRitiroInCancelleria() {
        return ritiroInCancelleria;
    }

    /**
     * Imposta il valore della proprietà ritiroInCancelleria.
     * 
     */
    public void setRitiroInCancelleria(boolean value) {
        this.ritiroInCancelleria = value;
    }

}
