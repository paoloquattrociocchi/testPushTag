
package it.giustizia.smp.webservice.server.polizia;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ApplicazioneMisureSicurezzaLibertaVigType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApplicazioneMisureSicurezzaLibertaVigType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://webservice.smp.giustizia.it/server/polizia/type}DatiAutoritaComunicazione"/>
 *         &lt;element ref="{http://webservice.smp.giustizia.it/server/polizia/type}DatiUffGiuSottoposizione"/>
 *         &lt;element ref="{http://webservice.smp.giustizia.it/server/polizia/type}DatiSoggettiSottoposti" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://webservice.smp.giustizia.it/server/polizia/type}DatiComunicazione" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApplicazioneMisureSicurezzaLibertaVigType", propOrder = {
    "datiAutoritaComunicazione",
    "datiUffGiuSottoposizione",
    "datiSoggettiSottoposti",
    "datiComunicazione"
})
public class ApplicazioneMisureSicurezzaLibertaVigType {

    @XmlElement(name = "DatiAutoritaComunicazione", required = true)
    protected DatiAutoritaComunicazioneType datiAutoritaComunicazione;
    @XmlElement(name = "DatiUffGiuSottoposizione", required = true)
    protected DatiUffGiuSottoposizioneType datiUffGiuSottoposizione;
    @XmlElement(name = "DatiSoggettiSottoposti", required = true)
    protected List<DatiSoggettiSottopostoType> datiSoggettiSottoposti;
    @XmlElement(name = "DatiComunicazione", required = true)
    protected List<DatiComunicazioneType> datiComunicazione;

    /**
     *  Dati dell autorità che trasmette la comunicazione
     * 
     * @return
     *     possible object is
     *     {@link DatiAutoritaComunicazioneType }
     *     
     */
    public DatiAutoritaComunicazioneType getDatiAutoritaComunicazione() {
        return datiAutoritaComunicazione;
    }

    /**
     * Sets the value of the datiAutoritaComunicazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiAutoritaComunicazioneType }
     *     
     */
    public void setDatiAutoritaComunicazione(DatiAutoritaComunicazioneType value) {
        this.datiAutoritaComunicazione = value;
    }

    /**
     *  Dati ufficio giudiziario che ha deciso la sottoposizione
     * 
     * @return
     *     possible object is
     *     {@link DatiUffGiuSottoposizioneType }
     *     
     */
    public DatiUffGiuSottoposizioneType getDatiUffGiuSottoposizione() {
        return datiUffGiuSottoposizione;
    }

    /**
     * Sets the value of the datiUffGiuSottoposizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiUffGiuSottoposizioneType }
     *     
     */
    public void setDatiUffGiuSottoposizione(DatiUffGiuSottoposizioneType value) {
        this.datiUffGiuSottoposizione = value;
    }

    /**
     *  Dati identificativi dei soggetti sottoposti Gets the value of the datiSoggettiSottoposti property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the datiSoggettiSottoposti property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDatiSoggettiSottoposti().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DatiSoggettiSottopostoType }
     * 
     * 
     */
    public List<DatiSoggettiSottopostoType> getDatiSoggettiSottoposti() {
        if (datiSoggettiSottoposti == null) {
            datiSoggettiSottoposti = new ArrayList<DatiSoggettiSottopostoType>();
        }
        return this.datiSoggettiSottoposti;
    }

    /**
     *  Dati della comunicazione Gets the value of the datiComunicazione property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the datiComunicazione property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDatiComunicazione().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DatiComunicazioneType }
     * 
     * 
     */
    public List<DatiComunicazioneType> getDatiComunicazione() {
        if (datiComunicazione == null) {
            datiComunicazione = new ArrayList<DatiComunicazioneType>();
        }
        return this.datiComunicazione;
    }

}
