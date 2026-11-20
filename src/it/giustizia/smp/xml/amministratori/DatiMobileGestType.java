
package it.giustizia.smp.xml.amministratori;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for datiMobileGestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="datiMobileGestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="stima" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}decimal12_2"/>
 *         &lt;element name="dataStima" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="dirittiTerzi" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="descDirittiTerzi" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string2000" minOccurs="0"/>
 *         &lt;element name="beneRinvenuto" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="dataPossesso" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dataPerditaPossesso" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="oneriCustodia" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="valoreOneriCustodia" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}decimal12_2" minOccurs="0"/>
 *         &lt;element name="dataOneriCustodia" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="descAltriVincoli" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string2000" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "datiMobileGestType", propOrder = {
    "stima",
    "dataStima",
    "dirittiTerzi",
    "descDirittiTerzi",
    "beneRinvenuto",
    "dataPossesso",
    "dataPerditaPossesso",
    "oneriCustodia",
    "valoreOneriCustodia",
    "dataOneriCustodia",
    "descAltriVincoli"
})
public class DatiMobileGestType {

    @XmlElement(required = true)
    protected BigDecimal stima;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataStima;
    protected Boolean dirittiTerzi;
    protected String descDirittiTerzi;
    protected Boolean beneRinvenuto;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataPossesso;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataPerditaPossesso;
    protected Boolean oneriCustodia;
    protected BigDecimal valoreOneriCustodia;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataOneriCustodia;
    protected String descAltriVincoli;

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
     * Gets the value of the beneRinvenuto property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isBeneRinvenuto() {
        return beneRinvenuto;
    }

    /**
     * Sets the value of the beneRinvenuto property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setBeneRinvenuto(Boolean value) {
        this.beneRinvenuto = value;
    }

    /**
     * Gets the value of the dataPossesso property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataPossesso() {
        return dataPossesso;
    }

    /**
     * Sets the value of the dataPossesso property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataPossesso(XMLGregorianCalendar value) {
        this.dataPossesso = value;
    }

    /**
     * Gets the value of the dataPerditaPossesso property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataPerditaPossesso() {
        return dataPerditaPossesso;
    }

    /**
     * Sets the value of the dataPerditaPossesso property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataPerditaPossesso(XMLGregorianCalendar value) {
        this.dataPerditaPossesso = value;
    }

    /**
     * Gets the value of the oneriCustodia property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isOneriCustodia() {
        return oneriCustodia;
    }

    /**
     * Sets the value of the oneriCustodia property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setOneriCustodia(Boolean value) {
        this.oneriCustodia = value;
    }

    /**
     * Gets the value of the valoreOneriCustodia property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValoreOneriCustodia() {
        return valoreOneriCustodia;
    }

    /**
     * Sets the value of the valoreOneriCustodia property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValoreOneriCustodia(BigDecimal value) {
        this.valoreOneriCustodia = value;
    }

    /**
     * Gets the value of the dataOneriCustodia property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataOneriCustodia() {
        return dataOneriCustodia;
    }

    /**
     * Sets the value of the dataOneriCustodia property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataOneriCustodia(XMLGregorianCalendar value) {
        this.dataOneriCustodia = value;
    }

    /**
     * Gets the value of the descAltriVincoli property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescAltriVincoli() {
        return descAltriVincoli;
    }

    /**
     * Sets the value of the descAltriVincoli property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescAltriVincoli(String value) {
        this.descAltriVincoli = value;
    }

}
