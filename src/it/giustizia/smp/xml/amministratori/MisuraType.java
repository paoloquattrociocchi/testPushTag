
package it.giustizia.smp.xml.amministratori;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for misuraType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="misuraType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identificativoBene" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}integer15" minOccurs="0"/>
 *         &lt;element name="tipoInteresse" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string1" minOccurs="0"/>
 *         &lt;element name="identificativoSoggettoTitolare" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}integer15" minOccurs="0"/>
 *         &lt;element name="quota" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}decimal10_2" minOccurs="0"/>
 *         &lt;element name="identificativoSoggettoProposto" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}integer15" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="identificativoNormativa" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}integer6" minOccurs="0"/>
 *         &lt;element name="decisione" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}decisioneType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "misuraType", propOrder = {
    "identificativoBene",
    "tipoInteresse",
    "identificativoSoggettoTitolare",
    "quota",
    "identificativoSoggettoProposto",
    "identificativoNormativa",
    "decisione"
})
public class MisuraType {

    protected BigInteger identificativoBene;
    protected String tipoInteresse;
    protected BigInteger identificativoSoggettoTitolare;
    protected BigDecimal quota;
    protected List<BigInteger> identificativoSoggettoProposto;
    protected BigInteger identificativoNormativa;
    protected DecisioneType decisione;

    /**
     * Gets the value of the identificativoBene property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdentificativoBene() {
        return identificativoBene;
    }

    /**
     * Sets the value of the identificativoBene property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdentificativoBene(BigInteger value) {
        this.identificativoBene = value;
    }

    /**
     * Gets the value of the tipoInteresse property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoInteresse() {
        return tipoInteresse;
    }

    /**
     * Sets the value of the tipoInteresse property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoInteresse(String value) {
        this.tipoInteresse = value;
    }

    /**
     * Gets the value of the identificativoSoggettoTitolare property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdentificativoSoggettoTitolare() {
        return identificativoSoggettoTitolare;
    }

    /**
     * Sets the value of the identificativoSoggettoTitolare property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdentificativoSoggettoTitolare(BigInteger value) {
        this.identificativoSoggettoTitolare = value;
    }

    /**
     * Gets the value of the quota property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getQuota() {
        return quota;
    }

    /**
     * Sets the value of the quota property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setQuota(BigDecimal value) {
        this.quota = value;
    }

    /**
     * Gets the value of the identificativoSoggettoProposto property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the identificativoSoggettoProposto property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIdentificativoSoggettoProposto().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BigInteger }
     * 
     * 
     */
    public List<BigInteger> getIdentificativoSoggettoProposto() {
        if (identificativoSoggettoProposto == null) {
            identificativoSoggettoProposto = new ArrayList<BigInteger>();
        }
        return this.identificativoSoggettoProposto;
    }

    /**
     * Gets the value of the identificativoNormativa property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdentificativoNormativa() {
        return identificativoNormativa;
    }

    /**
     * Sets the value of the identificativoNormativa property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdentificativoNormativa(BigInteger value) {
        this.identificativoNormativa = value;
    }

    /**
     * Gets the value of the decisione property.
     * 
     * @return
     *     possible object is
     *     {@link DecisioneType }
     *     
     */
    public DecisioneType getDecisione() {
        return decisione;
    }

    /**
     * Sets the value of the decisione property.
     * 
     * @param value
     *     allowed object is
     *     {@link DecisioneType }
     *     
     */
    public void setDecisione(DecisioneType value) {
        this.decisione = value;
    }

}
