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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per risultatoComunicazione complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="risultatoComunicazione">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="trasmissioni" type="{http://www.giustizia.it/gestorelocale/invioAtti}infoTrasmissione" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="eccezioni" type="{http://www.giustizia.it/gestorelocale/invioAtti}infoEccezione" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "risultatoComunicazione", propOrder = {
    "trasmissioni",
    "eccezioni"
})
@XmlRootElement(name = "RisultatoComunicazione")
public class RisultatoComunicazione {

    @XmlElement(nillable = true)
    protected List<InfoTrasmissione> trasmissioni;
    @XmlElement(nillable = true)
    protected List<InfoEccezione> eccezioni;

    /**
     * Gets the value of the trasmissioni property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the trasmissioni property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTrasmissioni().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InfoTrasmissione }
     * 
     * 
     */
    public List<InfoTrasmissione> getTrasmissioni() {
        if (trasmissioni == null) {
            trasmissioni = new ArrayList<InfoTrasmissione>();
        }
        return this.trasmissioni;
    }

    /**
     * Gets the value of the eccezioni property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the eccezioni property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEccezioni().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InfoEccezione }
     * 
     * 
     */
    public List<InfoEccezione> getEccezioni() {
        if (eccezioni == null) {
            eccezioni = new ArrayList<InfoEccezione>();
        }
        return this.eccezioni;
    }

}
