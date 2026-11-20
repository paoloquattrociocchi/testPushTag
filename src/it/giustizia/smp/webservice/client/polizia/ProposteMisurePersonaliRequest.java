
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
 *         &lt;element name="AutoritaProponente" type="{http://trasmissione.webservice.smp.giustizia.it/polizia/type}DatiAutoritaProponenteType"/>
 *         &lt;element name="TribunaleCompetente" type="{http://trasmissione.webservice.smp.giustizia.it/polizia/type}DatiUfficioCompetenteType"/>
 *         &lt;element name="SoggettiProposti" type="{http://trasmissione.webservice.smp.giustizia.it/polizia/type}DatiSoggettoPersonaFisicaType" maxOccurs="unbounded"/>
 *         &lt;element name="MisuraPersonale" type="{http://trasmissione.webservice.smp.giustizia.it/polizia/type}DatiMisuraPersonaleType" maxOccurs="unbounded"/>
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
    "autoritaProponente",
    "tribunaleCompetente",
    "soggettiProposti",
    "misuraPersonale"
})
@XmlRootElement(name = "ProposteMisurePersonaliRequest")
public class ProposteMisurePersonaliRequest {

    @XmlElement(name = "AutoritaRicevente", required = true)
    protected DatiAutoritaIdType autoritaRicevente;
    @XmlElement(name = "AutoritaProponente", required = true)
    protected DatiAutoritaProponenteType autoritaProponente;
    @XmlElement(name = "TribunaleCompetente", required = true)
    protected DatiUfficioCompetenteType tribunaleCompetente;
    @XmlElement(name = "SoggettiProposti", required = true)
    protected List<DatiSoggettoPersonaFisicaType> soggettiProposti;
    @XmlElement(name = "MisuraPersonale", required = true)
    protected List<DatiMisuraPersonaleType> misuraPersonale;

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
     * Gets the value of the autoritaProponente property.
     * 
     * @return
     *     possible object is
     *     {@link DatiAutoritaProponenteType }
     *     
     */
    public DatiAutoritaProponenteType getAutoritaProponente() {
        return autoritaProponente;
    }

    /**
     * Sets the value of the autoritaProponente property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiAutoritaProponenteType }
     *     
     */
    public void setAutoritaProponente(DatiAutoritaProponenteType value) {
        this.autoritaProponente = value;
    }

    /**
     * Gets the value of the tribunaleCompetente property.
     * 
     * @return
     *     possible object is
     *     {@link DatiUfficioCompetenteType }
     *     
     */
    public DatiUfficioCompetenteType getTribunaleCompetente() {
        return tribunaleCompetente;
    }

    /**
     * Sets the value of the tribunaleCompetente property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiUfficioCompetenteType }
     *     
     */
    public void setTribunaleCompetente(DatiUfficioCompetenteType value) {
        this.tribunaleCompetente = value;
    }

    /**
     * Gets the value of the soggettiProposti property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the soggettiProposti property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSoggettiProposti().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DatiSoggettoPersonaFisicaType }
     * 
     * 
     */
    public List<DatiSoggettoPersonaFisicaType> getSoggettiProposti() {
        if (soggettiProposti == null) {
            soggettiProposti = new ArrayList<DatiSoggettoPersonaFisicaType>();
        }
        return this.soggettiProposti;
    }

    /**
     * Gets the value of the misuraPersonale property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the misuraPersonale property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMisuraPersonale().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DatiMisuraPersonaleType }
     * 
     * 
     */
    public List<DatiMisuraPersonaleType> getMisuraPersonale() {
        if (misuraPersonale == null) {
            misuraPersonale = new ArrayList<DatiMisuraPersonaleType>();
        }
        return this.misuraPersonale;
    }

}
