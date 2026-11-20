
package it.giustizia.smp.xml.amministratori;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for denaroContanteType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="denaroContanteType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="descrizioneEnteDepositario" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string200"/>
 *         &lt;element name="descrizioneIntestatario" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string200" minOccurs="0"/>
 *         &lt;element name="iban" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string34" minOccurs="0"/>
 *         &lt;element name="swift" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string11" minOccurs="0"/>
 *         &lt;element name="importoValuta" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}decimal15_2" minOccurs="0"/>
 *         &lt;element name="importoEuro" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}decimal15_2" minOccurs="0"/>
 *         &lt;element name="tipoValuta" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}tipoValutaType" minOccurs="0"/>
 *         &lt;element name="numeroLibretto" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string20" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "denaroContanteType", propOrder = {
    "descrizioneEnteDepositario",
    "descrizioneIntestatario",
    "iban",
    "swift",
    "importoValuta",
    "importoEuro",
    "tipoValuta",
    "numeroLibretto"
})
public class DenaroContanteType {

    @XmlElement(required = true)
    protected String descrizioneEnteDepositario;
    protected String descrizioneIntestatario;
    protected String iban;
    protected String swift;
    protected BigDecimal importoValuta;
    protected BigDecimal importoEuro;
    protected TipoValutaType tipoValuta;
    protected String numeroLibretto;

    /**
     * Gets the value of the descrizioneEnteDepositario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneEnteDepositario() {
        return descrizioneEnteDepositario;
    }

    /**
     * Sets the value of the descrizioneEnteDepositario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneEnteDepositario(String value) {
        this.descrizioneEnteDepositario = value;
    }

    /**
     * Gets the value of the descrizioneIntestatario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneIntestatario() {
        return descrizioneIntestatario;
    }

    /**
     * Sets the value of the descrizioneIntestatario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneIntestatario(String value) {
        this.descrizioneIntestatario = value;
    }

    /**
     * Gets the value of the iban property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIban() {
        return iban;
    }

    /**
     * Sets the value of the iban property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIban(String value) {
        this.iban = value;
    }

    /**
     * Gets the value of the swift property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSwift() {
        return swift;
    }

    /**
     * Sets the value of the swift property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSwift(String value) {
        this.swift = value;
    }

    /**
     * Gets the value of the importoValuta property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoValuta() {
        return importoValuta;
    }

    /**
     * Sets the value of the importoValuta property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoValuta(BigDecimal value) {
        this.importoValuta = value;
    }

    /**
     * Gets the value of the importoEuro property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoEuro() {
        return importoEuro;
    }

    /**
     * Sets the value of the importoEuro property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoEuro(BigDecimal value) {
        this.importoEuro = value;
    }

    /**
     * Gets the value of the tipoValuta property.
     * 
     * @return
     *     possible object is
     *     {@link TipoValutaType }
     *     
     */
    public TipoValutaType getTipoValuta() {
        return tipoValuta;
    }

    /**
     * Sets the value of the tipoValuta property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoValutaType }
     *     
     */
    public void setTipoValuta(TipoValutaType value) {
        this.tipoValuta = value;
    }

    /**
     * Gets the value of the numeroLibretto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroLibretto() {
        return numeroLibretto;
    }

    /**
     * Sets the value of the numeroLibretto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroLibretto(String value) {
        this.numeroLibretto = value;
    }

}
