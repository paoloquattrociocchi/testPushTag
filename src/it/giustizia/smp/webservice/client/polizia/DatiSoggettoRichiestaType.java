
package it.giustizia.smp.webservice.client.polizia;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiSoggettoRichiestaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiSoggettoRichiestaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DESCR_COGNOME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCR_NOME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ID_SOGGETTO" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiSoggettoRichiestaType", namespace = "http://trasmissione.webservice.smp.giustizia.it/polizia/type", propOrder = {
    "descrcognome",
    "descrnome",
    "idsoggetto"
})
public class DatiSoggettoRichiestaType {

    @XmlElement(name = "DESCR_COGNOME", required = true)
    protected String descrcognome;
    @XmlElement(name = "DESCR_NOME", required = true)
    protected String descrnome;
    @XmlElement(name = "ID_SOGGETTO", required = true)
    protected BigInteger idsoggetto;

    /**
     * Gets the value of the descrcognome property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRCOGNOME() {
        return descrcognome;
    }

    /**
     * Sets the value of the descrcognome property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRCOGNOME(String value) {
        this.descrcognome = value;
    }

    /**
     * Gets the value of the descrnome property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRNOME() {
        return descrnome;
    }

    /**
     * Sets the value of the descrnome property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRNOME(String value) {
        this.descrnome = value;
    }

    /**
     * Gets the value of the idsoggetto property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIDSOGGETTO() {
        return idsoggetto;
    }

    /**
     * Sets the value of the idsoggetto property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIDSOGGETTO(BigInteger value) {
        this.idsoggetto = value;
    }

}
