//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.09.17 alle 11:32:38 AM CEST 
//


package it.giustizia.smp.webservice.client.casellario;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per DATA complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="DATA">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GIORNO" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="MESE" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ANNO">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="4"/>
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
@XmlType(name = "DATA", propOrder = {
    "giorno",
    "mese",
    "anno"
})
public class DATA {

    @XmlElement(name = "GIORNO")
    protected String giorno;
    @XmlElement(name = "MESE")
    protected String mese;
    @XmlElement(name = "ANNO", required = true)
    protected String anno;

    /**
     * Recupera il valore della proprietà giorno.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGIORNO() {
        return giorno;
    }

    /**
     * Imposta il valore della proprietà giorno.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGIORNO(String value) {
        this.giorno = value;
    }

    /**
     * Recupera il valore della proprietà mese.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMESE() {
        return mese;
    }

    /**
     * Imposta il valore della proprietà mese.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMESE(String value) {
        this.mese = value;
    }

    /**
     * Recupera il valore della proprietà anno.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getANNO() {
        return anno;
    }

    /**
     * Imposta il valore della proprietà anno.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setANNO(String value) {
        this.anno = value;
    }

}
