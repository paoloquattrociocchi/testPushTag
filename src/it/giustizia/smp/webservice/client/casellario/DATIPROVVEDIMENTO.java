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
 *         &lt;element name="PROG_PROVV">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               &lt;totalDigits value="15"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="CODI_TIPO_PROVV">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="3"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="DATA_PASSAGGIO_GIUDICATO" type="{http://it/mig/sippi/service/inviaProvvedimento/type}DATA"/>
 *         &lt;element name="DATA_PROVVEDIMENTO" type="{http://it/mig/sippi/service/inviaProvvedimento/type}DATA"/>
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
 *         &lt;element name="NUMERO_PROVVEDIMENTO" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="9"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ANNO_PROVVEDIMENTO" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               &lt;totalDigits value="4"/>
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
 *         &lt;element name="DESC_NOTE" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="2000"/>
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
    "progprovv",
    "coditipoprovv",
    "datapassaggiogiudicato",
    "dataprovvedimento",
    "codiautorita",
    "codisedeautoritaprindist",
    "numeroprovvedimento",
    "annoprovvedimento",
    "numeroregistrogenerale",
    "annoregistrogenerale",
    "descnote"
})
@XmlRootElement(name = "DATI_PROVVEDIMENTO")
public class DATIPROVVEDIMENTO {

    @XmlElement(name = "PROG_PROVV", required = true)
    protected BigInteger progprovv;
    @XmlElement(name = "CODI_TIPO_PROVV", required = true)
    protected String coditipoprovv;
    @XmlElement(name = "DATA_PASSAGGIO_GIUDICATO", required = true)
    protected DATA datapassaggiogiudicato;
    @XmlElement(name = "DATA_PROVVEDIMENTO", required = true)
    protected DATA dataprovvedimento;
    @XmlElement(name = "CODI_AUTORITA", required = true)
    protected String codiautorita;
    @XmlElement(name = "CODI_SEDE_AUTORITA_PRIN_DIST", required = true)
    protected String codisedeautoritaprindist;
    @XmlElement(name = "NUMERO_PROVVEDIMENTO")
    protected String numeroprovvedimento;
    @XmlElement(name = "ANNO_PROVVEDIMENTO")
    protected BigInteger annoprovvedimento;
    @XmlElement(name = "NUMERO_REGISTRO_GENERALE")
    protected String numeroregistrogenerale;
    @XmlElement(name = "ANNO_REGISTRO_GENERALE")
    protected BigInteger annoregistrogenerale;
    @XmlElement(name = "DESC_NOTE")
    protected String descnote;

    /**
     * Recupera il valore della proprietà progprovv.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPROGPROVV() {
        return progprovv;
    }

    /**
     * Imposta il valore della proprietà progprovv.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPROGPROVV(BigInteger value) {
        this.progprovv = value;
    }

    /**
     * Recupera il valore della proprietà coditipoprovv.
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
     * Imposta il valore della proprietà coditipoprovv.
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
     * Recupera il valore della proprietà datapassaggiogiudicato.
     * 
     * @return
     *     possible object is
     *     {@link DATA }
     *     
     */
    public DATA getDATAPASSAGGIOGIUDICATO() {
        return datapassaggiogiudicato;
    }

    /**
     * Imposta il valore della proprietà datapassaggiogiudicato.
     * 
     * @param value
     *     allowed object is
     *     {@link DATA }
     *     
     */
    public void setDATAPASSAGGIOGIUDICATO(DATA value) {
        this.datapassaggiogiudicato = value;
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
     * Recupera il valore della proprietà numeroprovvedimento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNUMEROPROVVEDIMENTO() {
        return numeroprovvedimento;
    }

    /**
     * Imposta il valore della proprietà numeroprovvedimento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNUMEROPROVVEDIMENTO(String value) {
        this.numeroprovvedimento = value;
    }

    /**
     * Recupera il valore della proprietà annoprovvedimento.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getANNOPROVVEDIMENTO() {
        return annoprovvedimento;
    }

    /**
     * Imposta il valore della proprietà annoprovvedimento.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setANNOPROVVEDIMENTO(BigInteger value) {
        this.annoprovvedimento = value;
    }

    /**
     * Recupera il valore della proprietà numeroregistrogenerale.
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
     * Imposta il valore della proprietà numeroregistrogenerale.
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
     * Recupera il valore della proprietà annoregistrogenerale.
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
     * Imposta il valore della proprietà annoregistrogenerale.
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
     * Recupera il valore della proprietà descnote.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCNOTE() {
        return descnote;
    }

    /**
     * Imposta il valore della proprietà descnote.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCNOTE(String value) {
        this.descnote = value;
    }

}
