//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.09.17 alle 11:32:38 AM CEST 
//


package it.giustizia.smp.webservice.client.casellario;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IDENTIFICATIVO">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               &lt;totalDigits value="15"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="CODI_TIPO_RIFERIMENTO">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="3"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="DATA_PROVVEDIMENTO" type="{http://it/mig/sippi/service/inviaProvvedimento/type}DATA"/>
 *         &lt;element name="CODI_SEDE_AUTORITA_PRIN_DIST">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="6"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="CODI_AUTORITA">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="3"/>
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
    "identificativo",
    "coditiporiferimento",
    "dataprovvedimento",
    "codisedeautoritaprindist",
    "codiautorita"
})
@XmlRootElement(name = "IMPUGNAZIONE")
public class IMPUGNAZIONE {

    @XmlElement(name = "IDENTIFICATIVO", required = true)
    protected BigInteger identificativo;
    @XmlElement(name = "CODI_TIPO_RIFERIMENTO", required = true)
    protected String coditiporiferimento;
    @XmlElement(name = "DATA_PROVVEDIMENTO", required = true)
    protected DATA dataprovvedimento;
    @XmlElement(name = "CODI_SEDE_AUTORITA_PRIN_DIST", required = true)
    protected String codisedeautoritaprindist;
    @XmlElement(name = "CODI_AUTORITA", required = true)
    protected String codiautorita;

    /**
     * Recupera il valore della proprietà identificativo.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIDENTIFICATIVO() {
        return identificativo;
    }

    /**
     * Imposta il valore della proprietà identificativo.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIDENTIFICATIVO(BigInteger value) {
        this.identificativo = value;
    }

    /**
     * Recupera il valore della proprietà coditiporiferimento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODITIPORIFERIMENTO() {
        return coditiporiferimento;
    }

    /**
     * Imposta il valore della proprietà coditiporiferimento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODITIPORIFERIMENTO(String value) {
        this.coditiporiferimento = value;
    }

    /**
     * Recupera il valore della proprietà dataprovvedimento.
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
     * Imposta il valore della proprietà dataprovvedimento.
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
     * Recupera il valore della proprietà codisedeautoritaprindist.
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
     * Imposta il valore della proprietà codisedeautoritaprindist.
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
     * Recupera il valore della proprietà codiautorita.
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
     * Imposta il valore della proprietà codiautorita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODIAUTORITA(String value) {
        this.codiautorita = value;
    }

}
