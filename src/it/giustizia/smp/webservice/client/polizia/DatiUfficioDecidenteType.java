
package it.giustizia.smp.webservice.client.polizia;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiUfficioDecidenteType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiUfficioDecidenteType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ID_UFFICIO" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="ID_PROCEDIMENTO" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="DESCR_NUM_PROC" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_NUM_DECRETO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiUfficioDecidenteType", namespace = "http://trasmissione.webservice.smp.giustizia.it/polizia/type", propOrder = {
    "idufficio",
    "idprocedimento",
    "descrnumproc",
    "descrnumdecreto"
})
public class DatiUfficioDecidenteType {

    @XmlElement(name = "ID_UFFICIO", required = true)
    protected BigInteger idufficio;
    @XmlElement(name = "ID_PROCEDIMENTO", required = true)
    protected BigInteger idprocedimento;
    @XmlElement(name = "DESCR_NUM_PROC", required = true)
    protected String descrnumproc;
    @XmlElement(name = "DESCR_NUM_DECRETO", required = true)
    protected String descrnumdecreto;

    /**
     * Gets the value of the idufficio property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIDUFFICIO() {
        return idufficio;
    }

    /**
     * Sets the value of the idufficio property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIDUFFICIO(BigInteger value) {
        this.idufficio = value;
    }

    /**
     * Gets the value of the idprocedimento property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIDPROCEDIMENTO() {
        return idprocedimento;
    }

    /**
     * Sets the value of the idprocedimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIDPROCEDIMENTO(BigInteger value) {
        this.idprocedimento = value;
    }

    /**
     * Gets the value of the descrnumproc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRNUMPROC() {
        return descrnumproc;
    }

    /**
     * Sets the value of the descrnumproc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRNUMPROC(String value) {
        this.descrnumproc = value;
    }

    /**
     * Gets the value of the descrnumdecreto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRNUMDECRETO() {
        return descrnumdecreto;
    }

    /**
     * Sets the value of the descrnumdecreto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRNUMDECRETO(String value) {
        this.descrnumdecreto = value;
    }

}
