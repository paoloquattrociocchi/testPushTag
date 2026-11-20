
package it.giustizia.smp.webservice.server.polizia;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Trasmissione dei fascicoli di “iscrizione indagini” (accertamenti)
 * 
 * <p>Java class for EsitoIndaginiType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EsitoIndaginiType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://webservice.smp.giustizia.it/server/polizia/type}DatiAutoritaMittente"/>
 *         &lt;element ref="{http://webservice.smp.giustizia.it/server/polizia/type}DatiUfficioGiudiziario"/>
 *         &lt;element ref="{http://webservice.smp.giustizia.it/server/polizia/type}DatiSoggettiIndagati" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://webservice.smp.giustizia.it/server/polizia/type}DatiEsitoIndagini" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EsitoIndaginiType", propOrder = {
    "datiAutoritaMittente",
    "datiUfficioGiudiziario",
    "datiSoggettiIndagati",
    "datiEsitoIndagini"
})
public class EsitoIndaginiType {

    @XmlElement(name = "DatiAutoritaMittente", required = true)
    protected String datiAutoritaMittente;
    @XmlElement(name = "DatiUfficioGiudiziario", required = true)
    protected DatiUfficioGiudiziarioType datiUfficioGiudiziario;
    @XmlElement(name = "DatiSoggettiIndagati", required = true)
    protected List<DatiSoggettiIndagatiType> datiSoggettiIndagati;
    @XmlElement(name = "DatiEsitoIndagini", required = true)
    protected List<DatiEsitoIndagatiType> datiEsitoIndagini;

    /**
     *  Dati dell autorità che invia le informazioni
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatiAutoritaMittente() {
        return datiAutoritaMittente;
    }

    /**
     * Sets the value of the datiAutoritaMittente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatiAutoritaMittente(String value) {
        this.datiAutoritaMittente = value;
    }

    /**
     *  Dati dell ufficio giudiziario che ha delegato le indagini
     * 
     * @return
     *     possible object is
     *     {@link DatiUfficioGiudiziarioType }
     *     
     */
    public DatiUfficioGiudiziarioType getDatiUfficioGiudiziario() {
        return datiUfficioGiudiziario;
    }

    /**
     * Sets the value of the datiUfficioGiudiziario property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiUfficioGiudiziarioType }
     *     
     */
    public void setDatiUfficioGiudiziario(DatiUfficioGiudiziarioType value) {
        this.datiUfficioGiudiziario = value;
    }

    /**
     *  Dati identificativi dei soggetti indagati Gets the value of the datiSoggettiIndagati property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the datiSoggettiIndagati property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDatiSoggettiIndagati().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DatiSoggettiIndagatiType }
     * 
     * 
     */
    public List<DatiSoggettiIndagatiType> getDatiSoggettiIndagati() {
        if (datiSoggettiIndagati == null) {
            datiSoggettiIndagati = new ArrayList<DatiSoggettiIndagatiType>();
        }
        return this.datiSoggettiIndagati;
    }

    /**
     *  Dati esito delle indagini Gets the value of the datiEsitoIndagini property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the datiEsitoIndagini property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDatiEsitoIndagini().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DatiEsitoIndagatiType }
     * 
     * 
     */
    public List<DatiEsitoIndagatiType> getDatiEsitoIndagini() {
        if (datiEsitoIndagini == null) {
            datiEsitoIndagini = new ArrayList<DatiEsitoIndagatiType>();
        }
        return this.datiEsitoIndagini;
    }

}
