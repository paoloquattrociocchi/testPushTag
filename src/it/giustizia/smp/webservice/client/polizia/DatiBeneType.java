
package it.giustizia.smp.webservice.client.polizia;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiBeneType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiBeneType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NUME_PROG_BENE" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="ID_BENE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ID_TIPO_BENE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ID_CAT_BENE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ID_SUBCAT_BENE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_BENE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiBeneType", namespace = "http://trasmissione.webservice.smp.giustizia.it/polizia/type", propOrder = {
    "numeprogbene",
    "idbene",
    "idtipobene",
    "idcatbene",
    "idsubcatbene",
    "descrbene"
})
public class DatiBeneType {

    @XmlElement(name = "NUME_PROG_BENE", required = true)
    protected BigInteger numeprogbene;
    @XmlElement(name = "ID_BENE", required = true)
    protected String idbene;
    @XmlElement(name = "ID_TIPO_BENE", required = true)
    protected String idtipobene;
    @XmlElement(name = "ID_CAT_BENE", required = true)
    protected String idcatbene;
    @XmlElement(name = "ID_SUBCAT_BENE", required = true)
    protected String idsubcatbene;
    @XmlElement(name = "DESCR_BENE", required = true, nillable = true)
    protected String descrbene;

    /**
     * Gets the value of the numeprogbene property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNUMEPROGBENE() {
        return numeprogbene;
    }

    /**
     * Sets the value of the numeprogbene property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNUMEPROGBENE(BigInteger value) {
        this.numeprogbene = value;
    }

    /**
     * Gets the value of the idbene property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDBENE() {
        return idbene;
    }

    /**
     * Sets the value of the idbene property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDBENE(String value) {
        this.idbene = value;
    }

    /**
     * Gets the value of the idtipobene property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDTIPOBENE() {
        return idtipobene;
    }

    /**
     * Sets the value of the idtipobene property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDTIPOBENE(String value) {
        this.idtipobene = value;
    }

    /**
     * Gets the value of the idcatbene property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDCATBENE() {
        return idcatbene;
    }

    /**
     * Sets the value of the idcatbene property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDCATBENE(String value) {
        this.idcatbene = value;
    }

    /**
     * Gets the value of the idsubcatbene property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDSUBCATBENE() {
        return idsubcatbene;
    }

    /**
     * Sets the value of the idsubcatbene property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDSUBCATBENE(String value) {
        this.idsubcatbene = value;
    }

    /**
     * Gets the value of the descrbene property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRBENE() {
        return descrbene;
    }

    /**
     * Sets the value of the descrbene property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRBENE(String value) {
        this.descrbene = value;
    }

}
