//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.12.22 alle 12:20:29 PM CET 
//


package it.giustizia.smp.webservice.client.ricorsocassazione;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per Cauzione complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Cauzione">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiKIdCauzione" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codiKIdUffOwner" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="codiFkIdProc" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="codiFkIdSoggInt" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="impoCauzione" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Cauzione", propOrder = {
    "codiKIdCauzione",
    "codiKIdUffOwner",
    "codiFkIdProc",
    "codiFkIdSoggInt",
    "impoCauzione"
})
public class Cauzione {

    protected long codiKIdCauzione;
    protected int codiKIdUffOwner;
    protected Long codiFkIdProc;
    protected long codiFkIdSoggInt;
    protected BigDecimal impoCauzione;

    /**
     * Recupera il valore della proprietà codiKIdCauzione.
     * 
     */
    public long getCodiKIdCauzione() {
        return codiKIdCauzione;
    }

    /**
     * Imposta il valore della proprietà codiKIdCauzione.
     * 
     */
    public void setCodiKIdCauzione(long value) {
        this.codiKIdCauzione = value;
    }

    /**
     * Recupera il valore della proprietà codiKIdUffOwner.
     * 
     */
    public int getCodiKIdUffOwner() {
        return codiKIdUffOwner;
    }

    /**
     * Imposta il valore della proprietà codiKIdUffOwner.
     * 
     */
    public void setCodiKIdUffOwner(int value) {
        this.codiKIdUffOwner = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdProc.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCodiFkIdProc() {
        return codiFkIdProc;
    }

    /**
     * Imposta il valore della proprietà codiFkIdProc.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCodiFkIdProc(Long value) {
        this.codiFkIdProc = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdSoggInt.
     * 
     */
    public long getCodiFkIdSoggInt() {
        return codiFkIdSoggInt;
    }

    /**
     * Imposta il valore della proprietà codiFkIdSoggInt.
     * 
     */
    public void setCodiFkIdSoggInt(long value) {
        this.codiFkIdSoggInt = value;
    }

    /**
     * Recupera il valore della proprietà impoCauzione.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImpoCauzione() {
        return impoCauzione;
    }

    /**
     * Imposta il valore della proprietà impoCauzione.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImpoCauzione(BigDecimal value) {
        this.impoCauzione = value;
    }

}
