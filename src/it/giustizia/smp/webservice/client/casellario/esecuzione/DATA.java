
package it.giustizia.smp.webservice.client.casellario.esecuzione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DATA complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DATA">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GIORNO">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="MESE">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ANNO">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="4"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DATA", propOrder = {
    "giorno",
    "mese",
    "anno"
})
public class DATA {

    @XmlElement(name = "GIORNO", required = true)
    protected String giorno;
    @XmlElement(name = "MESE", required = true)
    protected String mese;
    @XmlElement(name = "ANNO", required = true)
    protected String anno;

    /**
     * Gets the value of the giorno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGIORNO() {
        return giorno;
    }

    /**
     * Sets the value of the giorno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGIORNO(String value) {
        this.giorno = value;
    }

    /**
     * Gets the value of the mese property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMESE() {
        return mese;
    }

    /**
     * Sets the value of the mese property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMESE(String value) {
        this.mese = value;
    }

    /**
     * Gets the value of the anno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getANNO() {
        return anno;
    }

    /**
     * Sets the value of the anno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setANNO(String value) {
        this.anno = value;
    }

}
