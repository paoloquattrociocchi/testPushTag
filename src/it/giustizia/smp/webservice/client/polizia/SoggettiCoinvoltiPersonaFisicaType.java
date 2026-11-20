
package it.giustizia.smp.webservice.client.polizia;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SoggettiCoinvoltiPersonaFisicaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SoggettiCoinvoltiPersonaFisicaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NUME_PROG" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="ID_SOGGETTO" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="ID_TIPO_SOGGETTO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DatiAnagraficiPersonaFisica" type="{http://trasmissione.webservice.smp.giustizia.it/polizia/type}DatiAnagraficiPersonaFisicaType" maxOccurs="unbounded"/>
 *         &lt;element name="ID_NORMATIVA" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SoggettiCoinvoltiPersonaFisicaType", namespace = "http://trasmissione.webservice.smp.giustizia.it/polizia/type", propOrder = {
    "numeprog",
    "idsoggetto",
    "idtiposoggetto",
    "datiAnagraficiPersonaFisica",
    "idnormativa"
})
public class SoggettiCoinvoltiPersonaFisicaType {

    @XmlElement(name = "NUME_PROG", required = true)
    protected BigInteger numeprog;
    @XmlElement(name = "ID_SOGGETTO", required = true)
    protected BigInteger idsoggetto;
    @XmlElement(name = "ID_TIPO_SOGGETTO", required = true)
    protected String idtiposoggetto;
    @XmlElement(name = "DatiAnagraficiPersonaFisica", required = true)
    protected List<DatiAnagraficiPersonaFisicaType> datiAnagraficiPersonaFisica;
    @XmlElement(name = "ID_NORMATIVA", required = true, nillable = true)
    protected BigInteger idnormativa;

    /**
     * Gets the value of the numeprog property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNUMEPROG() {
        return numeprog;
    }

    /**
     * Sets the value of the numeprog property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNUMEPROG(BigInteger value) {
        this.numeprog = value;
    }

    /**
     * Gets the value of the idsoggetto property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIDSOGGETTO() {
        return idsoggetto;
    }

    /**
     * Sets the value of the idsoggetto property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIDSOGGETTO(BigInteger value) {
        this.idsoggetto = value;
    }

    /**
     * Gets the value of the idtiposoggetto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDTIPOSOGGETTO() {
        return idtiposoggetto;
    }

    /**
     * Sets the value of the idtiposoggetto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDTIPOSOGGETTO(String value) {
        this.idtiposoggetto = value;
    }

    /**
     * Gets the value of the datiAnagraficiPersonaFisica property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the datiAnagraficiPersonaFisica property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDatiAnagraficiPersonaFisica().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DatiAnagraficiPersonaFisicaType }
     * 
     * 
     */
    public List<DatiAnagraficiPersonaFisicaType> getDatiAnagraficiPersonaFisica() {
        if (datiAnagraficiPersonaFisica == null) {
            datiAnagraficiPersonaFisica = new ArrayList<DatiAnagraficiPersonaFisicaType>();
        }
        return this.datiAnagraficiPersonaFisica;
    }

    /**
     * Gets the value of the idnormativa property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIDNORMATIVA() {
        return idnormativa;
    }

    /**
     * Sets the value of the idnormativa property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIDNORMATIVA(BigInteger value) {
        this.idnormativa = value;
    }

}
