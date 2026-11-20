
package it.giustizia.smp.webservice.client.polizia;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiMisuraPatrimonialeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiMisuraPatrimonialeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NUME_PROG_BENE" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="ID_BENE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ID_TIPO_BENE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ID_CAT_BENE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ID_SUBCAT_BENE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_BENE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NUME_PROG_PROP" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="NUME_PROG_TITOL" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="ID_TIPO_INTERESSE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NUME_QUOTA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ID_TIPO_PREV">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;length value="1"/>
 *               &lt;enumeration value="1"/>
 *               &lt;enumeration value="2"/>
 *               &lt;enumeration value="3"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ID_NORMATIVA_RIF" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="ID_TIPO_MIS_PATR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ID_NORM_MIS_PATR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiMisuraPatrimonialeType", namespace = "http://trasmissione.webservice.smp.giustizia.it/polizia/type", propOrder = {
    "numeprogbene",
    "idbene",
    "idtipobene",
    "idcatbene",
    "idsubcatbene",
    "descrbene",
    "numeprogprop",
    "numeprogtitol",
    "idtipointeresse",
    "numequota",
    "idtipoprev",
    "idnormativarif",
    "idtipomispatr",
    "idnormmispatr"
})
public class DatiMisuraPatrimonialeType {

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
    @XmlElement(name = "NUME_PROG_PROP", required = true)
    protected BigInteger numeprogprop;
    @XmlElement(name = "NUME_PROG_TITOL", required = true)
    protected BigInteger numeprogtitol;
    @XmlElement(name = "ID_TIPO_INTERESSE", required = true)
    protected String idtipointeresse;
    @XmlElement(name = "NUME_QUOTA", required = true)
    protected String numequota;
    @XmlElement(name = "ID_TIPO_PREV", required = true)
    protected String idtipoprev;
    @XmlElement(name = "ID_NORMATIVA_RIF", required = true)
    protected BigInteger idnormativarif;
    @XmlElement(name = "ID_TIPO_MIS_PATR", required = true)
    protected String idtipomispatr;
    @XmlElement(name = "ID_NORM_MIS_PATR", required = true, nillable = true)
    protected String idnormmispatr;

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
     * Gets the value of the numeprogtitol property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNUMEPROGTITOL() {
        return numeprogtitol;
    }

    /**
     * Sets the value of the numeprogtitol property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNUMEPROGTITOL(BigInteger value) {
        this.numeprogtitol = value;
    }

    /**
     * Gets the value of the idtipointeresse property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDTIPOINTERESSE() {
        return idtipointeresse;
    }

    /**
     * Sets the value of the idtipointeresse property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDTIPOINTERESSE(String value) {
        this.idtipointeresse = value;
    }

    /**
     * Gets the value of the numequota property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNUMEQUOTA() {
        return numequota;
    }

    /**
     * Sets the value of the numequota property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNUMEQUOTA(String value) {
        this.numequota = value;
    }

    /**
     * Gets the value of the idtipoprev property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDTIPOPREV() {
        return idtipoprev;
    }

    /**
     * Sets the value of the idtipoprev property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDTIPOPREV(String value) {
        this.idtipoprev = value;
    }

    /**
     * Gets the value of the idnormativarif property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIDNORMATIVARIF() {
        return idnormativarif;
    }

    /**
     * Sets the value of the idnormativarif property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIDNORMATIVARIF(BigInteger value) {
        this.idnormativarif = value;
    }

    /**
     * Gets the value of the idtipomispatr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDTIPOMISPATR() {
        return idtipomispatr;
    }

    /**
     * Sets the value of the idtipomispatr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDTIPOMISPATR(String value) {
        this.idtipomispatr = value;
    }

    /**
     * Gets the value of the idnormmispatr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDNORMMISPATR() {
        return idnormmispatr;
    }

    /**
     * Sets the value of the idnormmispatr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDNORMMISPATR(String value) {
        this.idnormmispatr = value;
    }

}
