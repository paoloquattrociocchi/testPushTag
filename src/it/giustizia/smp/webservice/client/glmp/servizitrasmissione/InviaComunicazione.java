//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2015.03.10 alle 02:34:02 PM CET 
//


package it.giustizia.smp.webservice.client.glmp.servizitrasmissione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per inviaComunicazione complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="inviaComunicazione">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="notifica" type="{http://www.giustizia.it/gestorelocale/invioAtti}notifica" minOccurs="0"/>
 *         &lt;element name="fascicolo" type="{http://www.giustizia.it/gestorelocale/tipi}IdFascicolo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "inviaComunicazione", propOrder = {
    "notifica",
    "fascicolo"
})
@XmlRootElement(name = "InviaComunicazione")
public class InviaComunicazione {

    protected Notifica notifica;
    protected IdFascicolo fascicolo;

    /**
     * Recupera il valore della proprietà notifica.
     * 
     * @return
     *     possible object is
     *     {@link Notifica }
     *     
     */
    public Notifica getNotifica() {
        return notifica;
    }

    /**
     * Imposta il valore della proprietà notifica.
     * 
     * @param value
     *     allowed object is
     *     {@link Notifica }
     *     
     */
    public void setNotifica(Notifica value) {
        this.notifica = value;
    }

    /**
     * Recupera il valore della proprietà fascicolo.
     * 
     * @return
     *     possible object is
     *     {@link IdFascicolo }
     *     
     */
    public IdFascicolo getFascicolo() {
        return fascicolo;
    }

    /**
     * Imposta il valore della proprietà fascicolo.
     * 
     * @param value
     *     allowed object is
     *     {@link IdFascicolo }
     *     
     */
    public void setFascicolo(IdFascicolo value) {
        this.fascicolo = value;
    }

}
