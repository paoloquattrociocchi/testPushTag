
package it.giustizia.smp.xml.amministratori;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ubicazioneType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ubicazioneType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idStato" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;choice>
 *           &lt;element name="idComuneItaliano" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string6"/>
 *           &lt;element name="descrizioneComuneEstero" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;/choice>
 *         &lt;element name="tipoStrada" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}tipoStradaType" minOccurs="0"/>
 *         &lt;element name="indirizzo" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string120" minOccurs="0"/>
 *         &lt;element name="civico" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string6" minOccurs="0"/>
 *         &lt;element name="cap" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string5" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ubicazioneType", propOrder = {
    "idStato",
    "idComuneItaliano",
    "descrizioneComuneEstero",
    "tipoStrada",
    "indirizzo",
    "civico",
    "cap"
})
public class UbicazioneType {

    @XmlElement(required = true)
    protected BigInteger idStato;
    protected String idComuneItaliano;
    protected String descrizioneComuneEstero;
    protected TipoStradaType tipoStrada;
    protected String indirizzo;
    protected String civico;
    protected String cap;

    /**
     * Gets the value of the idStato property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdStato() {
        return idStato;
    }

    /**
     * Sets the value of the idStato property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdStato(BigInteger value) {
        this.idStato = value;
    }

    /**
     * Gets the value of the idComuneItaliano property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdComuneItaliano() {
        return idComuneItaliano;
    }

    /**
     * Sets the value of the idComuneItaliano property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdComuneItaliano(String value) {
        this.idComuneItaliano = value;
    }

    /**
     * Gets the value of the descrizioneComuneEstero property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneComuneEstero() {
        return descrizioneComuneEstero;
    }

    /**
     * Sets the value of the descrizioneComuneEstero property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneComuneEstero(String value) {
        this.descrizioneComuneEstero = value;
    }

    /**
     * Gets the value of the tipoStrada property.
     * 
     * @return
     *     possible object is
     *     {@link TipoStradaType }
     *     
     */
    public TipoStradaType getTipoStrada() {
        return tipoStrada;
    }

    /**
     * Sets the value of the tipoStrada property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoStradaType }
     *     
     */
    public void setTipoStrada(TipoStradaType value) {
        this.tipoStrada = value;
    }

    /**
     * Gets the value of the indirizzo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndirizzo() {
        return indirizzo;
    }

    /**
     * Sets the value of the indirizzo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndirizzo(String value) {
        this.indirizzo = value;
    }

    /**
     * Gets the value of the civico property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCivico() {
        return civico;
    }

    /**
     * Sets the value of the civico property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCivico(String value) {
        this.civico = value;
    }

    /**
     * Gets the value of the cap property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCap() {
        return cap;
    }

    /**
     * Sets the value of the cap property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCap(String value) {
        this.cap = value;
    }

}
