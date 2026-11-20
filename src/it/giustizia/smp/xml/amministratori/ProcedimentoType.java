
package it.giustizia.smp.xml.amministratori;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for procedimentoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="procedimentoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identificativoProcedimento" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}integer15"/>
 *         &lt;element name="ufficioCompetente" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}integer6"/>
 *         &lt;element name="annoProcedimento" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}integer4" minOccurs="0"/>
 *         &lt;element name="numeroProcedimento" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}integer12" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "procedimentoType", propOrder = {
    "identificativoProcedimento",
    "ufficioCompetente",
    "annoProcedimento",
    "numeroProcedimento"
})
public class ProcedimentoType {

    @XmlElement(required = true)
    protected BigInteger identificativoProcedimento;
    @XmlElement(required = true)
    protected BigInteger ufficioCompetente;
    protected BigInteger annoProcedimento;
    protected BigInteger numeroProcedimento;

    /**
     * Gets the value of the identificativoProcedimento property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdentificativoProcedimento() {
        return identificativoProcedimento;
    }

    /**
     * Sets the value of the identificativoProcedimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdentificativoProcedimento(BigInteger value) {
        this.identificativoProcedimento = value;
    }

    /**
     * Gets the value of the ufficioCompetente property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getUfficioCompetente() {
        return ufficioCompetente;
    }

    /**
     * Sets the value of the ufficioCompetente property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setUfficioCompetente(BigInteger value) {
        this.ufficioCompetente = value;
    }

    /**
     * Gets the value of the annoProcedimento property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAnnoProcedimento() {
        return annoProcedimento;
    }

    /**
     * Sets the value of the annoProcedimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAnnoProcedimento(BigInteger value) {
        this.annoProcedimento = value;
    }

    /**
     * Gets the value of the numeroProcedimento property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumeroProcedimento() {
        return numeroProcedimento;
    }

    /**
     * Sets the value of the numeroProcedimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumeroProcedimento(BigInteger value) {
        this.numeroProcedimento = value;
    }

}
