
package it.giustizia.smp.xml.amministratori;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for immaterialeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="immaterialeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="numeroRegistrazione" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string20" minOccurs="0"/>
 *         &lt;element name="dataRegistrazione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dataScadenza" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "immaterialeType", propOrder = {
    "numeroRegistrazione",
    "dataRegistrazione",
    "dataScadenza"
})
public class ImmaterialeType {

    protected String numeroRegistrazione;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataRegistrazione;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataScadenza;

    /**
     * Gets the value of the numeroRegistrazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroRegistrazione() {
        return numeroRegistrazione;
    }

    /**
     * Sets the value of the numeroRegistrazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroRegistrazione(String value) {
        this.numeroRegistrazione = value;
    }

    /**
     * Gets the value of the dataRegistrazione property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataRegistrazione() {
        return dataRegistrazione;
    }

    /**
     * Sets the value of the dataRegistrazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataRegistrazione(XMLGregorianCalendar value) {
        this.dataRegistrazione = value;
    }

    /**
     * Gets the value of the dataScadenza property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataScadenza() {
        return dataScadenza;
    }

    /**
     * Sets the value of the dataScadenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataScadenza(XMLGregorianCalendar value) {
        this.dataScadenza = value;
    }

}
