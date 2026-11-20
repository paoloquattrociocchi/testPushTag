
package it.giustizia.smp.xml.amministratori;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for collezioniType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="collezioniType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="descrizioneCollezione" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string200" minOccurs="0"/>
 *         &lt;element name="numeroPezzi" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}integer10" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "collezioniType", propOrder = {
    "descrizioneCollezione",
    "numeroPezzi"
})
public class CollezioniType {

    protected String descrizioneCollezione;
    protected BigInteger numeroPezzi;

    /**
     * Gets the value of the descrizioneCollezione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneCollezione() {
        return descrizioneCollezione;
    }

    /**
     * Sets the value of the descrizioneCollezione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneCollezione(String value) {
        this.descrizioneCollezione = value;
    }

    /**
     * Gets the value of the numeroPezzi property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumeroPezzi() {
        return numeroPezzi;
    }

    /**
     * Sets the value of the numeroPezzi property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumeroPezzi(BigInteger value) {
        this.numeroPezzi = value;
    }

}
