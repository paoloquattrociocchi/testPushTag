
package it.giustizia.smp.xml.amministratori;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for provvedimentoTrasmissioneType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="provvedimentoTrasmissioneType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identificativoProvvedimento" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}integer15"/>
 *         &lt;element name="annoProvvedimento" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}integer4"/>
 *         &lt;element name="numProvvedimento" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}integer15"/>
 *         &lt;element name="dataDepositoProvv" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "provvedimentoTrasmissioneType", propOrder = {
    "identificativoProvvedimento",
    "annoProvvedimento",
    "numProvvedimento",
    "dataDepositoProvv"
})
public class ProvvedimentoTrasmissioneType {

    @XmlElement(required = true)
    protected BigInteger identificativoProvvedimento;
    @XmlElement(required = true)
    protected BigInteger annoProvvedimento;
    @XmlElement(required = true)
    protected BigInteger numProvvedimento;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataDepositoProvv;

    /**
     * Gets the value of the identificativoProvvedimento property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdentificativoProvvedimento() {
        return identificativoProvvedimento;
    }

    /**
     * Sets the value of the identificativoProvvedimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdentificativoProvvedimento(BigInteger value) {
        this.identificativoProvvedimento = value;
    }

    /**
     * Gets the value of the annoProvvedimento property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAnnoProvvedimento() {
        return annoProvvedimento;
    }

    /**
     * Sets the value of the annoProvvedimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAnnoProvvedimento(BigInteger value) {
        this.annoProvvedimento = value;
    }

    /**
     * Gets the value of the numProvvedimento property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumProvvedimento() {
        return numProvvedimento;
    }

    /**
     * Sets the value of the numProvvedimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumProvvedimento(BigInteger value) {
        this.numProvvedimento = value;
    }

    /**
     * Gets the value of the dataDepositoProvv property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataDepositoProvv() {
        return dataDepositoProvv;
    }

    /**
     * Sets the value of the dataDepositoProvv property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataDepositoProvv(XMLGregorianCalendar value) {
        this.dataDepositoProvv = value;
    }

}
