
package it.giustizia.smp.webservice.client.casellario.esecuzione;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PROG_MIS_PREVENZIONE">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               &lt;totalDigits value="15"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="CODI_TIPO_MP">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="3"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="DURATA_MISURA" type="{http://it/mig/sippi/service/inviaProvvedimentoEsecuzione/type}DURATA" minOccurs="0"/>
 *         &lt;element name="IMPORTO" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "progmisprevenzione",
    "coditipomp",
    "duratamisura",
    "importo"
})
@XmlRootElement(name = "MISURA_DI_PREVENZIONE")
public class MISURADIPREVENZIONE {

    @XmlElement(name = "PROG_MIS_PREVENZIONE", required = true)
    protected BigInteger progmisprevenzione;
    @XmlElement(name = "CODI_TIPO_MP", required = true)
    protected String coditipomp;
    @XmlElement(name = "DURATA_MISURA")
    protected DURATA duratamisura;
    @XmlElement(name = "IMPORTO")
    protected BigDecimal importo;

    /**
     * Gets the value of the progmisprevenzione property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPROGMISPREVENZIONE() {
        return progmisprevenzione;
    }

    /**
     * Sets the value of the progmisprevenzione property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPROGMISPREVENZIONE(BigInteger value) {
        this.progmisprevenzione = value;
    }

    /**
     * Gets the value of the coditipomp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODITIPOMP() {
        return coditipomp;
    }

    /**
     * Sets the value of the coditipomp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODITIPOMP(String value) {
        this.coditipomp = value;
    }

    /**
     * Gets the value of the duratamisura property.
     * 
     * @return
     *     possible object is
     *     {@link DURATA }
     *     
     */
    public DURATA getDURATAMISURA() {
        return duratamisura;
    }

    /**
     * Sets the value of the duratamisura property.
     * 
     * @param value
     *     allowed object is
     *     {@link DURATA }
     *     
     */
    public void setDURATAMISURA(DURATA value) {
        this.duratamisura = value;
    }

    /**
     * Gets the value of the importo property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIMPORTO() {
        return importo;
    }

    /**
     * Sets the value of the importo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIMPORTO(BigDecimal value) {
        this.importo = value;
    }

}
