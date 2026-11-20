
package it.giustizia.smp.webservice.client.casellario.esecuzione;

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
 *         &lt;element name="CODI_TIPO_PROVV">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="3"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
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
 *         &lt;element name="NUMERO_REGISTRO_GENERALE" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="6"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ANNO_REGISTRO_GENERALE" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               &lt;totalDigits value="4"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="CODI_NATURA">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               &lt;totalDigits value="5"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element ref="{http://it/mig/sippi/service/inviaProvvedimentoEsecuzione/type}MISURA_DI_PREVENZIONE"/>
 *         &lt;element name="MOTIVAZIONE_ART_130" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="1500"/>
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
    "coditipoprovv",
    "dataprovvedimento",
    "codiautorita",
    "codisedeautoritaprindist",
    "numeroregistrogenerale",
    "annoregistrogenerale",
    "codinatura",
    "misuradiprevenzione",
    "motivazioneart130"
})
@XmlRootElement(name = "PROVVEDIMENTO_ESECUZIONE_MP")
public class PROVVEDIMENTOESECUZIONEMP {

    @XmlElement(name = "CODI_TIPO_PROVV", required = true)
    protected String coditipoprovv;
    @XmlElement(name = "DATA_PROVVEDIMENTO", required = true)
    protected DATA dataprovvedimento;
    @XmlElement(name = "CODI_AUTORITA", required = true)
    protected String codiautorita;
    @XmlElement(name = "CODI_SEDE_AUTORITA_PRIN_DIST", required = true)
    protected String codisedeautoritaprindist;
    @XmlElement(name = "NUMERO_REGISTRO_GENERALE")
    protected String numeroregistrogenerale;
    @XmlElement(name = "ANNO_REGISTRO_GENERALE")
    protected BigInteger annoregistrogenerale;
    @XmlElement(name = "CODI_NATURA", required = true)
    protected BigInteger codinatura;
    @XmlElement(name = "MISURA_DI_PREVENZIONE", required = true)
    protected MISURADIPREVENZIONE misuradiprevenzione;
    @XmlElement(name = "MOTIVAZIONE_ART_130")
    protected String motivazioneart130;

    /**
     * Gets the value of the coditipoprovv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODITIPOPROVV() {
        return coditipoprovv;
    }

    /**
     * Sets the value of the coditipoprovv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODITIPOPROVV(String value) {
        this.coditipoprovv = value;
    }

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

    /**
     * Gets the value of the numeroregistrogenerale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNUMEROREGISTROGENERALE() {
        return numeroregistrogenerale;
    }

    /**
     * Sets the value of the numeroregistrogenerale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNUMEROREGISTROGENERALE(String value) {
        this.numeroregistrogenerale = value;
    }

    /**
     * Gets the value of the annoregistrogenerale property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getANNOREGISTROGENERALE() {
        return annoregistrogenerale;
    }

    /**
     * Sets the value of the annoregistrogenerale property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setANNOREGISTROGENERALE(BigInteger value) {
        this.annoregistrogenerale = value;
    }

    /**
     * Gets the value of the codinatura property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCODINATURA() {
        return codinatura;
    }

    /**
     * Sets the value of the codinatura property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCODINATURA(BigInteger value) {
        this.codinatura = value;
    }

    /**
     * Gets the value of the misuradiprevenzione property.
     * 
     * @return
     *     possible object is
     *     {@link MISURADIPREVENZIONE }
     *     
     */
    public MISURADIPREVENZIONE getMISURADIPREVENZIONE() {
        return misuradiprevenzione;
    }

    /**
     * Sets the value of the misuradiprevenzione property.
     * 
     * @param value
     *     allowed object is
     *     {@link MISURADIPREVENZIONE }
     *     
     */
    public void setMISURADIPREVENZIONE(MISURADIPREVENZIONE value) {
        this.misuradiprevenzione = value;
    }

    /**
     * Gets the value of the motivazioneart130 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMOTIVAZIONEART130() {
        return motivazioneart130;
    }

    /**
     * Sets the value of the motivazioneart130 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMOTIVAZIONEART130(String value) {
        this.motivazioneart130 = value;
    }

}
