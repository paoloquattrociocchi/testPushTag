//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.10.14 alle 10:17:21 AM CEST 
//


package it.giustizia.smp.webservice.client.casellario.ricerca;

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
 *         &lt;element name="TIPO_CERTIFICATO">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;length value="3"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ANNO_PROC">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               &lt;totalDigits value="4"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="NUMERO_PROC">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               &lt;totalDigits value="9"/>
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
    "tipocertificato",
    "annoproc",
    "numeroproc"
})
@XmlRootElement(name = "DATI_CERTIFICATO")
public class DATICERTIFICATO {

    @XmlElement(name = "TIPO_CERTIFICATO", required = true)
    protected String tipocertificato;
    @XmlElement(name = "ANNO_PROC", required = true)
    protected BigInteger annoproc;
    @XmlElement(name = "NUMERO_PROC", required = true)
    protected BigInteger numeroproc;

    /**
     * Recupera il valore della proprietà tipocertificato.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTIPOCERTIFICATO() {
        return tipocertificato;
    }

    /**
     * Imposta il valore della proprietà tipocertificato.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTIPOCERTIFICATO(String value) {
        this.tipocertificato = value;
    }

    /**
     * Recupera il valore della proprietà annoproc.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getANNOPROC() {
        return annoproc;
    }

    /**
     * Imposta il valore della proprietà annoproc.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setANNOPROC(BigInteger value) {
        this.annoproc = value;
    }

    /**
     * Recupera il valore della proprietà numeroproc.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNUMEROPROC() {
        return numeroproc;
    }

    /**
     * Imposta il valore della proprietà numeroproc.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNUMEROPROC(BigInteger value) {
        this.numeroproc = value;
    }

}
