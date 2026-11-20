
package it.giustizia.smp.xml.amministratori;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for amministrazioneType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="amministrazioneType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiceFiscale" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string16"/>
 *         &lt;element name="identificativoBene" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}integer15" maxOccurs="unbounded"/>
 *         &lt;element name="dataInizioIncarico" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dataFineIncarico" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "amministrazioneType", propOrder = {
    "codiceFiscale",
    "identificativoBene",
    "dataInizioIncarico",
    "dataFineIncarico"
})
public class AmministrazioneType {

    @XmlElement(required = true)
    protected String codiceFiscale;
    @XmlElement(required = true)
    protected List<BigInteger> identificativoBene;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataInizioIncarico;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataFineIncarico;

    /**
     * Gets the value of the codiceFiscale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    /**
     * Sets the value of the codiceFiscale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceFiscale(String value) {
        this.codiceFiscale = value;
    }

    /**
     * Gets the value of the identificativoBene property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the identificativoBene property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIdentificativoBene().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BigInteger }
     * 
     * 
     */
    public List<BigInteger> getIdentificativoBene() {
        if (identificativoBene == null) {
            identificativoBene = new ArrayList<BigInteger>();
        }
        return this.identificativoBene;
    }

    /**
     * Gets the value of the dataInizioIncarico property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataInizioIncarico() {
        return dataInizioIncarico;
    }

    /**
     * Sets the value of the dataInizioIncarico property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataInizioIncarico(XMLGregorianCalendar value) {
        this.dataInizioIncarico = value;
    }

    /**
     * Gets the value of the dataFineIncarico property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataFineIncarico() {
        return dataFineIncarico;
    }

    /**
     * Sets the value of the dataFineIncarico property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataFineIncarico(XMLGregorianCalendar value) {
        this.dataFineIncarico = value;
    }

}
