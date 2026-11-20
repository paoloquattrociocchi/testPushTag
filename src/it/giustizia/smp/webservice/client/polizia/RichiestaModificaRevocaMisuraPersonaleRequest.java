
package it.giustizia.smp.webservice.client.polizia;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AutoritaRicevente" type="{http://trasmissione.webservice.smp.giustizia.it/polizia/type}DatiAutoritaIdType"/>
 *         &lt;element name="SoggettoRichiedente" type="{http://trasmissione.webservice.smp.giustizia.it/polizia/type}DatiSoggettoRichiestaType"/>
 *         &lt;element name="UfficioDecidente" type="{http://trasmissione.webservice.smp.giustizia.it/polizia/type}DatiUfficioDecidenteType"/>
 *         &lt;element name="UfficioCompetente" type="{http://trasmissione.webservice.smp.giustizia.it/polizia/type}DatiUfficioCompetenteType"/>
 *         &lt;element name="PersonaFisica" type="{http://trasmissione.webservice.smp.giustizia.it/polizia/type}DatiSoggettoPersonaFisicaType" maxOccurs="unbounded"/>
 *         &lt;element name="Richiesta" type="{http://trasmissione.webservice.smp.giustizia.it/polizia/type}DatiRichiestaModificaRevocaMisuraType"/>
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
    "autoritaRicevente",
    "soggettoRichiedente",
    "ufficioDecidente",
    "ufficioCompetente",
    "personaFisica",
    "richiesta"
})
@XmlRootElement(name = "RichiestaModificaRevocaMisuraPersonaleRequest")
public class RichiestaModificaRevocaMisuraPersonaleRequest {

    @XmlElement(name = "AutoritaRicevente", required = true)
    protected DatiAutoritaIdType autoritaRicevente;
    @XmlElement(name = "SoggettoRichiedente", required = true)
    protected DatiSoggettoRichiestaType soggettoRichiedente;
    @XmlElement(name = "UfficioDecidente", required = true)
    protected DatiUfficioDecidenteType ufficioDecidente;
    @XmlElement(name = "UfficioCompetente", required = true)
    protected DatiUfficioCompetenteType ufficioCompetente;
    @XmlElement(name = "PersonaFisica", required = true)
    protected List<DatiSoggettoPersonaFisicaType> personaFisica;
    @XmlElement(name = "Richiesta", required = true)
    protected DatiRichiestaModificaRevocaMisuraType richiesta;

    /**
     * Gets the value of the autoritaRicevente property.
     * 
     * @return
     *     possible object is
     *     {@link DatiAutoritaIdType }
     *     
     */
    public DatiAutoritaIdType getAutoritaRicevente() {
        return autoritaRicevente;
    }

    /**
     * Sets the value of the autoritaRicevente property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiAutoritaIdType }
     *     
     */
    public void setAutoritaRicevente(DatiAutoritaIdType value) {
        this.autoritaRicevente = value;
    }

    /**
     * Gets the value of the soggettoRichiedente property.
     * 
     * @return
     *     possible object is
     *     {@link DatiSoggettoRichiestaType }
     *     
     */
    public DatiSoggettoRichiestaType getSoggettoRichiedente() {
        return soggettoRichiedente;
    }

    /**
     * Sets the value of the soggettoRichiedente property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiSoggettoRichiestaType }
     *     
     */
    public void setSoggettoRichiedente(DatiSoggettoRichiestaType value) {
        this.soggettoRichiedente = value;
    }

    /**
     * Gets the value of the ufficioDecidente property.
     * 
     * @return
     *     possible object is
     *     {@link DatiUfficioDecidenteType }
     *     
     */
    public DatiUfficioDecidenteType getUfficioDecidente() {
        return ufficioDecidente;
    }

    /**
     * Sets the value of the ufficioDecidente property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiUfficioDecidenteType }
     *     
     */
    public void setUfficioDecidente(DatiUfficioDecidenteType value) {
        this.ufficioDecidente = value;
    }

    /**
     * Gets the value of the ufficioCompetente property.
     * 
     * @return
     *     possible object is
     *     {@link DatiUfficioCompetenteType }
     *     
     */
    public DatiUfficioCompetenteType getUfficioCompetente() {
        return ufficioCompetente;
    }

    /**
     * Sets the value of the ufficioCompetente property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiUfficioCompetenteType }
     *     
     */
    public void setUfficioCompetente(DatiUfficioCompetenteType value) {
        this.ufficioCompetente = value;
    }

    /**
     * Gets the value of the personaFisica property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the personaFisica property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPersonaFisica().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DatiSoggettoPersonaFisicaType }
     * 
     * 
     */
    public List<DatiSoggettoPersonaFisicaType> getPersonaFisica() {
        if (personaFisica == null) {
            personaFisica = new ArrayList<DatiSoggettoPersonaFisicaType>();
        }
        return this.personaFisica;
    }

    /**
     * Gets the value of the richiesta property.
     * 
     * @return
     *     possible object is
     *     {@link DatiRichiestaModificaRevocaMisuraType }
     *     
     */
    public DatiRichiestaModificaRevocaMisuraType getRichiesta() {
        return richiesta;
    }

    /**
     * Sets the value of the richiesta property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiRichiestaModificaRevocaMisuraType }
     *     
     */
    public void setRichiesta(DatiRichiestaModificaRevocaMisuraType value) {
        this.richiesta = value;
    }

}
