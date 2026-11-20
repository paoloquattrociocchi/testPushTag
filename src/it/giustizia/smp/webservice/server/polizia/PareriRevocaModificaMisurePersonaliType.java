
package it.giustizia.smp.webservice.server.polizia;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PareriRevocaModificaMisurePersonaliType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PareriRevocaModificaMisurePersonaliType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://webservice.smp.giustizia.it/server/polizia/type}DatiAutoritaComunicazione"/>
 *         &lt;element ref="{http://webservice.smp.giustizia.it/server/polizia/type}DatiUffGiuRichiedente"/>
 *         &lt;element ref="{http://webservice.smp.giustizia.it/server/polizia/type}DatiSoggettoSottoposto" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://webservice.smp.giustizia.it/server/polizia/type}DatiParere" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PareriRevocaModificaMisurePersonaliType", propOrder = {
    "datiAutoritaComunicazione",
    "datiUffGiuRichiedente",
    "datiSoggettoSottoposto",
    "datiParere"
})
public class PareriRevocaModificaMisurePersonaliType {

    @XmlElement(name = "DatiAutoritaComunicazione", required = true)
    protected DatiAutoritaComunicazioneType datiAutoritaComunicazione;
    @XmlElement(name = "DatiUffGiuRichiedente", required = true)
    protected DatiUffGiuRichiedenteType datiUffGiuRichiedente;
    @XmlElement(name = "DatiSoggettoSottoposto", required = true)
    protected List<IdSoggettoSottopostoType> datiSoggettoSottoposto;
    @XmlElement(name = "DatiParere", required = true)
    protected List<DatiParereType> datiParere;

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
     *     {@link DatiUffGiuRichiedenteType }
     *     
     */
    public DatiUffGiuRichiedenteType getDatiUffGiuRichiedente() {
        return datiUffGiuRichiedente;
    }

    /**
     * Sets the value of the datiUffGiuRichiedente property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiUffGiuRichiedenteType }
     *     
     */
    public void setDatiUffGiuRichiedente(DatiUffGiuRichiedenteType value) {
        this.datiUffGiuRichiedente = value;
    }

    /**
     *  Dati identificativi dei sogetti sottoposti Gets the value of the datiSoggettoSottoposto property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the datiSoggettoSottoposto property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDatiSoggettoSottoposto().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IdSoggettoSottopostoType }
     * 
     * 
     */
    public List<IdSoggettoSottopostoType> getDatiSoggettoSottoposto() {
        if (datiSoggettoSottoposto == null) {
            datiSoggettoSottoposto = new ArrayList<IdSoggettoSottopostoType>();
        }
        return this.datiSoggettoSottoposto;
    }

    /**
     *  Dati del parere Gets the value of the datiParere property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the datiParere property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDatiParere().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DatiParereType }
     * 
     * 
     */
    public List<DatiParereType> getDatiParere() {
        if (datiParere == null) {
            datiParere = new ArrayList<DatiParereType>();
        }
        return this.datiParere;
    }

}
