
package it.giustizia.smp.webservice.client.casellario.esecuzione;

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
 *         &lt;element name="DATA_PROVVEDIMENTO" type="{http://it/mig/sippi/service/inviaProvvedimentoEsecuzione/type}DATA"/>
 *         &lt;element name="CODI_AUTORITA">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="3"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="CODI_SEDE_AUTORITA_PRIN_DIST">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="6"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
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
    "dataprovvedimento",
    "codiautorita",
    "codisedeautoritaprindist"
})
@XmlRootElement(name = "DATI_PROVVEDIMENTO")
public class DATIPROVVEDIMENTO {

    @XmlElement(name = "DATA_PROVVEDIMENTO", required = true)
    protected DATA dataprovvedimento;
    @XmlElement(name = "CODI_AUTORITA", required = true)
    protected String codiautorita;
    @XmlElement(name = "CODI_SEDE_AUTORITA_PRIN_DIST", required = true)
    protected String codisedeautoritaprindist;

    /**
     * Gets the value of the dataprovvedimento property.
     * 
     * @return
     *     possible object is
     *     {@link DATA }
     *     
     */
    public DATA getDATAPROVVEDIMENTO() {
        return dataprovvedimento;
    }

    /**
     * Sets the value of the dataprovvedimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link DATA }
     *     
     */
    public void setDATAPROVVEDIMENTO(DATA value) {
        this.dataprovvedimento = value;
    }

    /**
     * Gets the value of the codiautorita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODIAUTORITA() {
        return codiautorita;
    }

    /**
     * Sets the value of the codiautorita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODIAUTORITA(String value) {
        this.codiautorita = value;
    }

    /**
     * Gets the value of the codisedeautoritaprindist property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODISEDEAUTORITAPRINDIST() {
        return codisedeautoritaprindist;
    }

    /**
     * Sets the value of the codisedeautoritaprindist property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODISEDEAUTORITAPRINDIST(String value) {
        this.codisedeautoritaprindist = value;
    }

}
