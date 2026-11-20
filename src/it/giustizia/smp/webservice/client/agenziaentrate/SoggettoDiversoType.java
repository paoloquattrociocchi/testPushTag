
package it.giustizia.smp.webservice.client.agenziaentrate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for soggettoDiversoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="soggettoDiversoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiceFiscale" type="{http://types.verifica.anagrafica}codiceFiscaleType" minOccurs="0"/>
 *         &lt;element name="descrizioneDenominazione" type="{http://types.verifica.anagrafica}descrizioneDenominazioneType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="dettagli" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "soggettoDiversoType", propOrder = {
    "codiceFiscale",
    "descrizioneDenominazione"
})
public class SoggettoDiversoType {

    protected String codiceFiscale;
    protected String descrizioneDenominazione;
    @XmlAttribute
    protected String dettagli;

    /**
     * Gets the value of the codiceFiscale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    /**
     * Sets the value of the codiceFiscale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceFiscale(String value) {
        this.codiceFiscale = value;
    }

    /**
     * Gets the value of the descrizioneDenominazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneDenominazione() {
        return descrizioneDenominazione;
    }

    /**
     * Sets the value of the descrizioneDenominazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneDenominazione(String value) {
        this.descrizioneDenominazione = value;
    }

    /**
     * Gets the value of the dettagli property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDettagli() {
        return dettagli;
    }

    /**
     * Sets the value of the dettagli property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDettagli(String value) {
        this.dettagli = value;
    }

}
