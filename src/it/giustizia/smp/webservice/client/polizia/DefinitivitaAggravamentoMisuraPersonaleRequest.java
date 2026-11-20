
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
 *         &lt;element name="UfficioCompetente" type="{http://trasmissione.webservice.smp.giustizia.it/polizia/type}DatiUfficioCompetenteType"/>
 *         &lt;element name="Decreto" type="{http://trasmissione.webservice.smp.giustizia.it/polizia/type}DatiDecretoDefinitivitaType"/>
 *         &lt;element name="SoggettiCoinvoltiPersonaFisica" type="{http://trasmissione.webservice.smp.giustizia.it/polizia/type}SoggettiCoinvoltiPersonaFisicaType" maxOccurs="unbounded"/>
 *         &lt;element name="MisuraPersonale" type="{http://trasmissione.webservice.smp.giustizia.it/polizia/type}DatiMisuraPersonaleDefinitivitaType" maxOccurs="unbounded"/>
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
    "ufficioCompetente",
    "decreto",
    "soggettiCoinvoltiPersonaFisica",
    "misuraPersonale"
})
@XmlRootElement(name = "DefinitivitaAggravamentoMisuraPersonaleRequest")
public class DefinitivitaAggravamentoMisuraPersonaleRequest {

    @XmlElement(name = "AutoritaRicevente", required = true)
    protected DatiAutoritaIdType autoritaRicevente;
    @XmlElement(name = "UfficioCompetente", required = true)
    protected DatiUfficioCompetenteType ufficioCompetente;
    @XmlElement(name = "Decreto", required = true)
    protected DatiDecretoDefinitivitaType decreto;
    @XmlElement(name = "SoggettiCoinvoltiPersonaFisica", required = true)
    protected List<SoggettiCoinvoltiPersonaFisicaType> soggettiCoinvoltiPersonaFisica;
    @XmlElement(name = "MisuraPersonale", required = true)
    protected List<DatiMisuraPersonaleDefinitivitaType> misuraPersonale;

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
     * Gets the value of the decreto property.
     * 
     * @return
     *     possible object is
     *     {@link DatiDecretoDefinitivitaType }
     *     
     */
    public DatiDecretoDefinitivitaType getDecreto() {
        return decreto;
    }

    /**
     * Sets the value of the decreto property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiDecretoDefinitivitaType }
     *     
     */
    public void setDecreto(DatiDecretoDefinitivitaType value) {
        this.decreto = value;
    }

    /**
     * Gets the value of the soggettiCoinvoltiPersonaFisica property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the soggettiCoinvoltiPersonaFisica property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSoggettiCoinvoltiPersonaFisica().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SoggettiCoinvoltiPersonaFisicaType }
     * 
     * 
     */
    public List<SoggettiCoinvoltiPersonaFisicaType> getSoggettiCoinvoltiPersonaFisica() {
        if (soggettiCoinvoltiPersonaFisica == null) {
            soggettiCoinvoltiPersonaFisica = new ArrayList<SoggettiCoinvoltiPersonaFisicaType>();
        }
        return this.soggettiCoinvoltiPersonaFisica;
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
     * {@link DatiMisuraPersonaleDefinitivitaType }
     * 
     * 
     */
    public List<DatiMisuraPersonaleDefinitivitaType> getMisuraPersonale() {
        if (misuraPersonale == null) {
            misuraPersonale = new ArrayList<DatiMisuraPersonaleDefinitivitaType>();
        }
        return this.misuraPersonale;
    }

}
