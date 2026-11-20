
package it.giustizia.smp.xml.amministratori;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for datiImmaterialeGestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="datiImmaterialeGestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="stima" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}decimal12_2"/>
 *         &lt;element name="dataStima" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="dirittiTerzi" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="descDirittiTerzi" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string2000" minOccurs="0"/>
 *         &lt;element name="trascrizioneSequestro" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="dataTrascrizioneSequestro" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="descCustode" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string120" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "datiImmaterialeGestType", propOrder = {
    "stima",
    "dataStima",
    "dirittiTerzi",
    "descDirittiTerzi",
    "trascrizioneSequestro",
    "dataTrascrizioneSequestro",
    "descCustode"
})
public class DatiImmaterialeGestType {

    @XmlElement(required = true)
    protected BigDecimal stima;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataStima;
    protected Boolean dirittiTerzi;
    protected String descDirittiTerzi;
    protected Boolean trascrizioneSequestro;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataTrascrizioneSequestro;
    protected String descCustode;

    /**
     * Gets the value of the stima property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getStima() {
        return stima;
    }

    /**
     * Sets the value of the stima property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setStima(BigDecimal value) {
        this.stima = value;
    }

    /**
     * Gets the value of the dataStima property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataStima() {
        return dataStima;
    }

    /**
     * Sets the value of the dataStima property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataStima(XMLGregorianCalendar value) {
        this.dataStima = value;
    }

    /**
     * Gets the value of the dirittiTerzi property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDirittiTerzi() {
        return dirittiTerzi;
    }

    /**
     * Sets the value of the dirittiTerzi property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDirittiTerzi(Boolean value) {
        this.dirittiTerzi = value;
    }

    /**
     * Gets the value of the descDirittiTerzi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescDirittiTerzi() {
        return descDirittiTerzi;
    }

    /**
     * Sets the value of the descDirittiTerzi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescDirittiTerzi(String value) {
        this.descDirittiTerzi = value;
    }

    /**
     * Gets the value of the trascrizioneSequestro property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isTrascrizioneSequestro() {
        return trascrizioneSequestro;
    }

    /**
     * Sets the value of the trascrizioneSequestro property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTrascrizioneSequestro(Boolean value) {
        this.trascrizioneSequestro = value;
    }

    /**
     * Gets the value of the dataTrascrizioneSequestro property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataTrascrizioneSequestro() {
        return dataTrascrizioneSequestro;
    }

    /**
     * Sets the value of the dataTrascrizioneSequestro property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataTrascrizioneSequestro(XMLGregorianCalendar value) {
        this.dataTrascrizioneSequestro = value;
    }

    /**
     * Gets the value of the descCustode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescCustode() {
        return descCustode;
    }

    /**
     * Sets the value of the descCustode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescCustode(String value) {
        this.descCustode = value;
    }

}
