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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per DURATA complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="DURATA">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ANNI_DURATA" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               &lt;totalDigits value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="MESI_DURATA">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               &lt;totalDigits value="3"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="GIORNI_DURATA">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               &lt;totalDigits value="3"/>
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
@XmlType(name = "DURATA", propOrder = {
    "annidurata",
    "mesidurata",
    "giornidurata"
})
public class DURATA {

    @XmlElement(name = "ANNI_DURATA")
    protected BigInteger annidurata;
    @XmlElement(name = "MESI_DURATA", required = true)
    protected BigInteger mesidurata;
    @XmlElement(name = "GIORNI_DURATA", required = true)
    protected BigInteger giornidurata;

    /**
     * Recupera il valore della proprietà annidurata.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getANNIDURATA() {
        return annidurata;
    }

    /**
     * Imposta il valore della proprietà annidurata.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setANNIDURATA(BigInteger value) {
        this.annidurata = value;
    }

    /**
     * Recupera il valore della proprietà mesidurata.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMESIDURATA() {
        return mesidurata;
    }

    /**
     * Imposta il valore della proprietà mesidurata.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMESIDURATA(BigInteger value) {
        this.mesidurata = value;
    }

    /**
     * Recupera il valore della proprietà giornidurata.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getGIORNIDURATA() {
        return giornidurata;
    }

    /**
     * Imposta il valore della proprietà giornidurata.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setGIORNIDURATA(BigInteger value) {
        this.giornidurata = value;
    }

}
