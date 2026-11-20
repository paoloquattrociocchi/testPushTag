
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
 *         &lt;element name="UfficioEmittente" type="{http://trasmissione.webservice.smp.giustizia.it/polizia/type}DatiUfficioCompetenteType"/>
 *         &lt;element name="Sentenza" type="{http://trasmissione.webservice.smp.giustizia.it/polizia/type}DatiSentenzaType"/>
 *         &lt;element name="Bene" type="{http://trasmissione.webservice.smp.giustizia.it/polizia/type}DatiBeneType" maxOccurs="unbounded"/>
 *         &lt;element name="SoggettiCoinvolti" type="{http://trasmissione.webservice.smp.giustizia.it/polizia/type}SoggettiCoinvoltiType" maxOccurs="unbounded"/>
 *         &lt;element name="MisuraPatrimoniale" type="{http://trasmissione.webservice.smp.giustizia.it/polizia/type}DatiMisuraPatrimonialeCassazioneType" maxOccurs="unbounded"/>
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
    "ufficioEmittente",
    "sentenza",
    "bene",
    "soggettiCoinvolti",
    "misuraPatrimoniale"
})
@XmlRootElement(name = "SentenzaCassazionePatrimonialiRequest")
public class SentenzaCassazionePatrimonialiRequest {

    @XmlElement(name = "AutoritaRicevente", required = true)
    protected DatiAutoritaIdType autoritaRicevente;
    @XmlElement(name = "UfficioEmittente", required = true)
    protected DatiUfficioCompetenteType ufficioEmittente;
    @XmlElement(name = "Sentenza", required = true)
    protected DatiSentenzaType sentenza;
    @XmlElement(name = "Bene", required = true)
    protected List<DatiBeneType> bene;
    @XmlElement(name = "SoggettiCoinvolti", required = true)
    protected List<SoggettiCoinvoltiType> soggettiCoinvolti;
    @XmlElement(name = "MisuraPatrimoniale", required = true)
    protected List<DatiMisuraPatrimonialeCassazioneType> misuraPatrimoniale;

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
     * Gets the value of the ufficioEmittente property.
     * 
     * @return
     *     possible object is
     *     {@link DatiUfficioCompetenteType }
     *     
     */
    public DatiUfficioCompetenteType getUfficioEmittente() {
        return ufficioEmittente;
    }

    /**
     * Sets the value of the ufficioEmittente property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiUfficioCompetenteType }
     *     
     */
    public void setUfficioEmittente(DatiUfficioCompetenteType value) {
        this.ufficioEmittente = value;
    }

    /**
     * Gets the value of the sentenza property.
     * 
     * @return
     *     possible object is
     *     {@link DatiSentenzaType }
     *     
     */
    public DatiSentenzaType getSentenza() {
        return sentenza;
    }

    /**
     * Sets the value of the sentenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiSentenzaType }
     *     
     */
    public void setSentenza(DatiSentenzaType value) {
        this.sentenza = value;
    }

    /**
     * Gets the value of the bene property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bene property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBene().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DatiBeneType }
     * 
     * 
     */
    public List<DatiBeneType> getBene() {
        if (bene == null) {
            bene = new ArrayList<DatiBeneType>();
        }
        return this.bene;
    }

    /**
     * Gets the value of the soggettiCoinvolti property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the soggettiCoinvolti property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSoggettiCoinvolti().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SoggettiCoinvoltiType }
     * 
     * 
     */
    public List<SoggettiCoinvoltiType> getSoggettiCoinvolti() {
        if (soggettiCoinvolti == null) {
            soggettiCoinvolti = new ArrayList<SoggettiCoinvoltiType>();
        }
        return this.soggettiCoinvolti;
    }

    /**
     * Gets the value of the misuraPatrimoniale property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the misuraPatrimoniale property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMisuraPatrimoniale().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DatiMisuraPatrimonialeCassazioneType }
     * 
     * 
     */
    public List<DatiMisuraPatrimonialeCassazioneType> getMisuraPatrimoniale() {
        if (misuraPatrimoniale == null) {
            misuraPatrimoniale = new ArrayList<DatiMisuraPatrimonialeCassazioneType>();
        }
        return this.misuraPatrimoniale;
    }

}
