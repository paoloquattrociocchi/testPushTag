
package it.giustizia.smp.webservice.client.polizia;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiAutoritaProponenteType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiAutoritaProponenteType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ID_AUTORITA" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="ID_PROPOSTA" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="DESCR_NUM_PROPOSTA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DATA_EMISSIONE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DATA_DEPOSITO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiAutoritaProponenteType", namespace = "http://trasmissione.webservice.smp.giustizia.it/polizia/type", propOrder = {
    "idautorita",
    "idproposta",
    "descrnumproposta",
    "dataemissione",
    "datadeposito"
})
public class DatiAutoritaProponenteType {

    @XmlElement(name = "ID_AUTORITA", required = true)
    protected BigInteger idautorita;
    @XmlElement(name = "ID_PROPOSTA", required = true)
    protected BigInteger idproposta;
    @XmlElement(name = "DESCR_NUM_PROPOSTA", required = true)
    protected String descrnumproposta;
    @XmlElement(name = "DATA_EMISSIONE", required = true)
    protected String dataemissione;
    @XmlElement(name = "DATA_DEPOSITO", required = true)
    protected String datadeposito;

    /**
     * Gets the value of the idautorita property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIDAUTORITA() {
        return idautorita;
    }

    /**
     * Sets the value of the idautorita property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIDAUTORITA(BigInteger value) {
        this.idautorita = value;
    }

    /**
     * Gets the value of the idproposta property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIDPROPOSTA() {
        return idproposta;
    }

    /**
     * Sets the value of the idproposta property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIDPROPOSTA(BigInteger value) {
        this.idproposta = value;
    }

    /**
     * Gets the value of the descrnumproposta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRNUMPROPOSTA() {
        return descrnumproposta;
    }

    /**
     * Sets the value of the descrnumproposta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRNUMPROPOSTA(String value) {
        this.descrnumproposta = value;
    }

    /**
     * Gets the value of the dataemissione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDATAEMISSIONE() {
        return dataemissione;
    }

    /**
     * Sets the value of the dataemissione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDATAEMISSIONE(String value) {
        this.dataemissione = value;
    }

    /**
     * Gets the value of the datadeposito property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDATADEPOSITO() {
        return datadeposito;
    }

    /**
     * Sets the value of the datadeposito property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDATADEPOSITO(String value) {
        this.datadeposito = value;
    }

}
