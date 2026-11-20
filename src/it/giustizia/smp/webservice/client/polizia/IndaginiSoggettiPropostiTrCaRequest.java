
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
 *         &lt;element name="AutoritaDelegata" type="{http://trasmissione.webservice.smp.giustizia.it/polizia/type}DatiAutoritaIdType"/>
 *         &lt;element name="UfficioDelegante" type="{http://trasmissione.webservice.smp.giustizia.it/polizia/type}DatiUfficioGiudiziarioType"/>
 *         &lt;element name="PersonaGiuridicaIndagata" type="{http://trasmissione.webservice.smp.giustizia.it/polizia/type}DatiPersonaGiuridicaType" maxOccurs="unbounded"/>
 *         &lt;element name="PersonaFisicaIndagata" type="{http://trasmissione.webservice.smp.giustizia.it/polizia/type}DatiPersonaFisicaType" maxOccurs="unbounded"/>
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
    "autoritaDelegata",
    "ufficioDelegante",
    "personaGiuridicaIndagata",
    "personaFisicaIndagata"
})
@XmlRootElement(name = "IndaginiSoggettiPropostiTrCaRequest")
public class IndaginiSoggettiPropostiTrCaRequest {

    @XmlElement(name = "AutoritaDelegata", required = true)
    protected DatiAutoritaIdType autoritaDelegata;
    @XmlElement(name = "UfficioDelegante", required = true)
    protected DatiUfficioGiudiziarioType ufficioDelegante;
    @XmlElement(name = "PersonaGiuridicaIndagata", required = true)
    protected List<DatiPersonaGiuridicaType> personaGiuridicaIndagata;
    @XmlElement(name = "PersonaFisicaIndagata", required = true)
    protected List<DatiPersonaFisicaType> personaFisicaIndagata;

    /**
     * Gets the value of the autoritaDelegata property.
     * 
     * @return
     *     possible object is
     *     {@link DatiAutoritaIdType }
     *     
     */
    public DatiAutoritaIdType getAutoritaDelegata() {
        return autoritaDelegata;
    }

    /**
     * Sets the value of the autoritaDelegata property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiAutoritaIdType }
     *     
     */
    public void setAutoritaDelegata(DatiAutoritaIdType value) {
        this.autoritaDelegata = value;
    }

    /**
     * Gets the value of the ufficioDelegante property.
     * 
     * @return
     *     possible object is
     *     {@link DatiUfficioGiudiziarioType }
     *     
     */
    public DatiUfficioGiudiziarioType getUfficioDelegante() {
        return ufficioDelegante;
    }

    /**
     * Sets the value of the ufficioDelegante property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiUfficioGiudiziarioType }
     *     
     */
    public void setUfficioDelegante(DatiUfficioGiudiziarioType value) {
        this.ufficioDelegante = value;
    }

    /**
     * Gets the value of the personaGiuridicaIndagata property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the personaGiuridicaIndagata property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPersonaGiuridicaIndagata().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DatiPersonaGiuridicaType }
     * 
     * 
     */
    public List<DatiPersonaGiuridicaType> getPersonaGiuridicaIndagata() {
        if (personaGiuridicaIndagata == null) {
            personaGiuridicaIndagata = new ArrayList<DatiPersonaGiuridicaType>();
        }
        return this.personaGiuridicaIndagata;
    }

    /**
     * Gets the value of the personaFisicaIndagata property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the personaFisicaIndagata property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPersonaFisicaIndagata().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DatiPersonaFisicaType }
     * 
     * 
     */
    public List<DatiPersonaFisicaType> getPersonaFisicaIndagata() {
        if (personaFisicaIndagata == null) {
            personaFisicaIndagata = new ArrayList<DatiPersonaFisicaType>();
        }
        return this.personaFisicaIndagata;
    }

}
