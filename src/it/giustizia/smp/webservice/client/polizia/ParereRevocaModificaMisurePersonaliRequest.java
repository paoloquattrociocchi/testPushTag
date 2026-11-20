
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
 *         &lt;element name="AutoritaParere" type="{http://trasmissione.webservice.smp.giustizia.it/polizia/type}DatiAutoritaIdType"/>
 *         &lt;element name="UfficioParere" type="{http://trasmissione.webservice.smp.giustizia.it/polizia/type}DatiUfficioGiudiziarioType"/>
 *         &lt;element name="SoggettoSottoposto" type="{http://trasmissione.webservice.smp.giustizia.it/polizia/type}DatiPersonaFisicaType" maxOccurs="unbounded"/>
 *         &lt;element name="Parere" type="{http://trasmissione.webservice.smp.giustizia.it/polizia/type}DatiParereType"/>
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
    "autoritaParere",
    "ufficioParere",
    "soggettoSottoposto",
    "parere"
})
@XmlRootElement(name = "ParereRevocaModificaMisurePersonaliRequest")
public class ParereRevocaModificaMisurePersonaliRequest {

    @XmlElement(name = "AutoritaParere", required = true)
    protected DatiAutoritaIdType autoritaParere;
    @XmlElement(name = "UfficioParere", required = true)
    protected DatiUfficioGiudiziarioType ufficioParere;
    @XmlElement(name = "SoggettoSottoposto", required = true)
    protected List<DatiPersonaFisicaType> soggettoSottoposto;
    @XmlElement(name = "Parere", required = true)
    protected DatiParereType parere;

    /**
     * Gets the value of the autoritaParere property.
     * 
     * @return
     *     possible object is
     *     {@link DatiAutoritaIdType }
     *     
     */
    public DatiAutoritaIdType getAutoritaParere() {
        return autoritaParere;
    }

    /**
     * Sets the value of the autoritaParere property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiAutoritaIdType }
     *     
     */
    public void setAutoritaParere(DatiAutoritaIdType value) {
        this.autoritaParere = value;
    }

    /**
     * Gets the value of the ufficioParere property.
     * 
     * @return
     *     possible object is
     *     {@link DatiUfficioGiudiziarioType }
     *     
     */
    public DatiUfficioGiudiziarioType getUfficioParere() {
        return ufficioParere;
    }

    /**
     * Sets the value of the ufficioParere property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiUfficioGiudiziarioType }
     *     
     */
    public void setUfficioParere(DatiUfficioGiudiziarioType value) {
        this.ufficioParere = value;
    }

    /**
     * Gets the value of the soggettoSottoposto property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the soggettoSottoposto property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSoggettoSottoposto().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DatiPersonaFisicaType }
     * 
     * 
     */
    public List<DatiPersonaFisicaType> getSoggettoSottoposto() {
        if (soggettoSottoposto == null) {
            soggettoSottoposto = new ArrayList<DatiPersonaFisicaType>();
        }
        return this.soggettoSottoposto;
    }

    /**
     * Gets the value of the parere property.
     * 
     * @return
     *     possible object is
     *     {@link DatiParereType }
     *     
     */
    public DatiParereType getParere() {
        return parere;
    }

    /**
     * Sets the value of the parere property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiParereType }
     *     
     */
    public void setParere(DatiParereType value) {
        this.parere = value;
    }

}
