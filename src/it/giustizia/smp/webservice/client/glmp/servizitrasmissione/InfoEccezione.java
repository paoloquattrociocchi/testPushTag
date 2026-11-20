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
 * <p>Classe Java per infoEccezione complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="infoEccezione">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="destinatario" type="{http://www.giustizia.it/gestorelocale/invioAtti}infoDestinatario" minOccurs="0"/>
 *         &lt;element name="messaggioEccezione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "infoEccezione", propOrder = {
    "destinatario",
    "messaggioEccezione"
})
public class InfoEccezione {

    protected InfoDestinatario destinatario;
    protected String messaggioEccezione;

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
     * Recupera il valore della proprietà messaggioEccezione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessaggioEccezione() {
        return messaggioEccezione;
    }

    /**
     * Imposta il valore della proprietà messaggioEccezione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessaggioEccezione(String value) {
        this.messaggioEccezione = value;
    }

}
