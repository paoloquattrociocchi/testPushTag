
package it.giustizia.smp.webservice.client.polizia;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiRichiestaModificaRevocaMisuraType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiRichiestaModificaRevocaMisuraType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NUME_PROGR_MIS_PERS" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="NUME_PROG_PROP" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="ID_TIPO_MIS_PERS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_MIS_PERS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiRichiestaModificaRevocaMisuraType", namespace = "http://trasmissione.webservice.smp.giustizia.it/polizia/type", propOrder = {
    "numeprogrmispers",
    "numeprogprop",
    "idtipomispers",
    "descrmispers"
})
public class DatiRichiestaModificaRevocaMisuraType {

    @XmlElement(name = "NUME_PROGR_MIS_PERS", required = true)
    protected BigInteger numeprogrmispers;
    @XmlElement(name = "NUME_PROG_PROP", required = true)
    protected BigInteger numeprogprop;
    @XmlElement(name = "ID_TIPO_MIS_PERS", required = true)
    protected String idtipomispers;
    @XmlElement(name = "DESCR_MIS_PERS", required = true, nillable = true)
    protected String descrmispers;

    /**
     * Gets the value of the numeprogrmispers property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNUMEPROGRMISPERS() {
        return numeprogrmispers;
    }

    /**
     * Sets the value of the numeprogrmispers property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNUMEPROGRMISPERS(BigInteger value) {
        this.numeprogrmispers = value;
    }

    /**
     * Gets the value of the numeprogprop property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNUMEPROGPROP() {
        return numeprogprop;
    }

    /**
     * Sets the value of the numeprogprop property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNUMEPROGPROP(BigInteger value) {
        this.numeprogprop = value;
    }

    /**
     * Gets the value of the idtipomispers property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDTIPOMISPERS() {
        return idtipomispers;
    }

    /**
     * Sets the value of the idtipomispers property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDTIPOMISPERS(String value) {
        this.idtipomispers = value;
    }

    /**
     * Gets the value of the descrmispers property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRMISPERS() {
        return descrmispers;
    }

    /**
     * Sets the value of the descrmispers property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRMISPERS(String value) {
        this.descrmispers = value;
    }

}
