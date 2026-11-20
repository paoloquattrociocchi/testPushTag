
package it.giustizia.smp.xml.amministratori;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for denaroType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="denaroType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="descrizioneEnteDepositario" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string200" minOccurs="0"/>
 *         &lt;element name="descrizioneFiliale" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string200" minOccurs="0"/>
 *         &lt;element name="descrizioneDirettore" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string200" minOccurs="0"/>
 *         &lt;element name="numeroRegistrazioneEnte" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string20" minOccurs="0"/>
 *         &lt;element name="importoValuta" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}decimal15_2" minOccurs="0"/>
 *         &lt;element name="importoEuro" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}decimal15_2" minOccurs="0"/>
 *         &lt;element name="iban" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string34" minOccurs="0"/>
 *         &lt;element name="swift" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string11" minOccurs="0"/>
 *         &lt;element name="tipoValuta" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}tipoValutaType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "denaroType", propOrder = {
    "descrizioneEnteDepositario",
    "descrizioneFiliale",
    "descrizioneDirettore",
    "numeroRegistrazioneEnte",
    "importoValuta",
    "importoEuro",
    "iban",
    "swift",
    "tipoValuta"
})
public class DenaroType {

    protected String descrizioneEnteDepositario;
    protected String descrizioneFiliale;
    protected String descrizioneDirettore;
    protected String numeroRegistrazioneEnte;
    protected BigDecimal importoValuta;
    protected BigDecimal importoEuro;
    protected String iban;
    protected String swift;
    protected TipoValutaType tipoValuta;

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
     * Gets the value of the descrizioneFiliale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneFiliale() {
        return descrizioneFiliale;
    }

    /**
     * Sets the value of the descrizioneFiliale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneFiliale(String value) {
        this.descrizioneFiliale = value;
    }

    /**
     * Gets the value of the descrizioneDirettore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneDirettore() {
        return descrizioneDirettore;
    }

    /**
     * Sets the value of the descrizioneDirettore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneDirettore(String value) {
        this.descrizioneDirettore = value;
    }

    /**
     * Gets the value of the numeroRegistrazioneEnte property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroRegistrazioneEnte() {
        return numeroRegistrazioneEnte;
    }

    /**
     * Sets the value of the numeroRegistrazioneEnte property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroRegistrazioneEnte(String value) {
        this.numeroRegistrazioneEnte = value;
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

}
