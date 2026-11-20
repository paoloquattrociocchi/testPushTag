
package it.giustizia.smp.webservice.client.polizia;

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
 *         &lt;element name="UfficioDetermina" type="{http://trasmissione.webservice.smp.giustizia.it/polizia/type}DatiUfficioCompetenteType"/>
 *         &lt;element name="SoggettoVariazione" type="{http://trasmissione.webservice.smp.giustizia.it/polizia/type}VariazioneAnagraficiType"/>
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
    "ufficioDetermina",
    "soggettoVariazione"
})
@XmlRootElement(name = "VariazioneAnagraficheSoggettiInteressatiRequest")
public class VariazioneAnagraficheSoggettiInteressatiRequest {

    @XmlElement(name = "AutoritaRicevente", required = true)
    protected DatiAutoritaIdType autoritaRicevente;
    @XmlElement(name = "UfficioDetermina", required = true)
    protected DatiUfficioCompetenteType ufficioDetermina;
    @XmlElement(name = "SoggettoVariazione", required = true)
    protected VariazioneAnagraficiType soggettoVariazione;

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
     * Gets the value of the ufficioDetermina property.
     * 
     * @return
     *     possible object is
     *     {@link DatiUfficioCompetenteType }
     *     
     */
    public DatiUfficioCompetenteType getUfficioDetermina() {
        return ufficioDetermina;
    }

    /**
     * Sets the value of the ufficioDetermina property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiUfficioCompetenteType }
     *     
     */
    public void setUfficioDetermina(DatiUfficioCompetenteType value) {
        this.ufficioDetermina = value;
    }

    /**
     * Gets the value of the soggettoVariazione property.
     * 
     * @return
     *     possible object is
     *     {@link VariazioneAnagraficiType }
     *     
     */
    public VariazioneAnagraficiType getSoggettoVariazione() {
        return soggettoVariazione;
    }

    /**
     * Sets the value of the soggettoVariazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link VariazioneAnagraficiType }
     *     
     */
    public void setSoggettoVariazione(VariazioneAnagraficiType value) {
        this.soggettoVariazione = value;
    }

}
