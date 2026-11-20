
package it.giustizia.smp.webservice.client.polizia;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiMisuraPatrimonialeSecondoGradoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiMisuraPatrimonialeSecondoGradoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NUME_PROG_BENE" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="NUME_PROG_PROP" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="NUME_PROG_TITOL" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="ID_TIPO_INTERESSE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NUME_QUOTA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ID_NORM_MIS_PATR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ID_ESITO_1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ID_ESITO_2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiMisuraPatrimonialeSecondoGradoType", namespace = "http://trasmissione.webservice.smp.giustizia.it/polizia/type", propOrder = {
    "numeprogbene",
    "numeprogprop",
    "numeprogtitol",
    "idtipointeresse",
    "numequota",
    "idnormmispatr",
    "idesito1",
    "idesito2"
})
public class DatiMisuraPatrimonialeSecondoGradoType {

    @XmlElement(name = "NUME_PROG_BENE", required = true)
    protected BigInteger numeprogbene;
    @XmlElement(name = "NUME_PROG_PROP", required = true)
    protected BigInteger numeprogprop;
    @XmlElement(name = "NUME_PROG_TITOL", required = true)
    protected BigInteger numeprogtitol;
    @XmlElement(name = "ID_TIPO_INTERESSE", required = true)
    protected String idtipointeresse;
    @XmlElement(name = "NUME_QUOTA", required = true)
    protected String numequota;
    @XmlElement(name = "ID_NORM_MIS_PATR", required = true, nillable = true)
    protected String idnormmispatr;
    @XmlElement(name = "ID_ESITO_1", required = true, nillable = true)
    protected String idesito1;
    @XmlElement(name = "ID_ESITO_2", required = true, nillable = true)
    protected String idesito2;

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

    /**
     * Gets the value of the idesito1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDESITO1() {
        return idesito1;
    }

    /**
     * Sets the value of the idesito1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDESITO1(String value) {
        this.idesito1 = value;
    }

    /**
     * Gets the value of the idesito2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDESITO2() {
        return idesito2;
    }

    /**
     * Sets the value of the idesito2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDESITO2(String value) {
        this.idesito2 = value;
    }

}
