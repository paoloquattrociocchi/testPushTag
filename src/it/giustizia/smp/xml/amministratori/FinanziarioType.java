
package it.giustizia.smp.xml.amministratori;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for finanziarioType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="finanziarioType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="descEnte" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string200" minOccurs="0"/>
 *         &lt;element name="filialeModulo" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string200" minOccurs="0"/>
 *         &lt;element name="direttoreIntestatario" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string200" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element name="ente">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="swift" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string11" minOccurs="0"/>
 *                     &lt;sequence>
 *                       &lt;element name="iban" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string34"/>
 *                       &lt;sequence>
 *                         &lt;element name="abi" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}stringInteger5"/>
 *                         &lt;element name="cab" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}stringInteger5"/>
 *                         &lt;element name="numeroContoCorrente" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string12char"/>
 *                       &lt;/sequence>
 *                     &lt;/sequence>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="assicurazione">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="codFiscaleEnte" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string16"/>
 *                     &lt;element name="numeroPolizza" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string34"/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *         &lt;/choice>
 *         &lt;element name="tipoValuta" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}tipoValutaType" minOccurs="0"/>
 *         &lt;element name="importoValuta" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}decimal15_2" minOccurs="0"/>
 *         &lt;element name="importoEuro" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}decimal15_2" minOccurs="0"/>
 *         &lt;element name="ident" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "finanziarioType", propOrder = {
    "descEnte",
    "filialeModulo",
    "direttoreIntestatario",
    "ente",
    "assicurazione",
    "tipoValuta",
    "importoValuta",
    "importoEuro",
    "ident"
})
@XmlSeeAlso({
    it.giustizia.smp.xml.amministratori.BeneType.DatiSpecificiBene.Finanziario.class
})
public class FinanziarioType {

    protected String descEnte;
    protected String filialeModulo;
    protected String direttoreIntestatario;
    protected FinanziarioType.Ente ente;
    protected FinanziarioType.Assicurazione assicurazione;
    protected TipoValutaType tipoValuta;
    protected BigDecimal importoValuta;
    protected BigDecimal importoEuro;
    protected String ident;

    /**
     * Gets the value of the descEnte property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescEnte() {
        return descEnte;
    }

    /**
     * Sets the value of the descEnte property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescEnte(String value) {
        this.descEnte = value;
    }

    /**
     * Gets the value of the filialeModulo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFilialeModulo() {
        return filialeModulo;
    }

    /**
     * Sets the value of the filialeModulo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFilialeModulo(String value) {
        this.filialeModulo = value;
    }

    /**
     * Gets the value of the direttoreIntestatario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirettoreIntestatario() {
        return direttoreIntestatario;
    }

    /**
     * Sets the value of the direttoreIntestatario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirettoreIntestatario(String value) {
        this.direttoreIntestatario = value;
    }

    /**
     * Gets the value of the ente property.
     * 
     * @return
     *     possible object is
     *     {@link FinanziarioType.Ente }
     *     
     */
    public FinanziarioType.Ente getEnte() {
        return ente;
    }

    /**
     * Sets the value of the ente property.
     * 
     * @param value
     *     allowed object is
     *     {@link FinanziarioType.Ente }
     *     
     */
    public void setEnte(FinanziarioType.Ente value) {
        this.ente = value;
    }

    /**
     * Gets the value of the assicurazione property.
     * 
     * @return
     *     possible object is
     *     {@link FinanziarioType.Assicurazione }
     *     
     */
    public FinanziarioType.Assicurazione getAssicurazione() {
        return assicurazione;
    }

    /**
     * Sets the value of the assicurazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link FinanziarioType.Assicurazione }
     *     
     */
    public void setAssicurazione(FinanziarioType.Assicurazione value) {
        this.assicurazione = value;
    }

    /**
     * Gets the value of the tipoValuta property.
     * 
     * @return
     *     possible object is
     *     {@link TipoValutaType }
     *     
     */
    public TipoValutaType getTipoValuta() {
        return tipoValuta;
    }

    /**
     * Sets the value of the tipoValuta property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoValutaType }
     *     
     */
    public void setTipoValuta(TipoValutaType value) {
        this.tipoValuta = value;
    }

    /**
     * Gets the value of the importoValuta property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoValuta() {
        return importoValuta;
    }

    /**
     * Sets the value of the importoValuta property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoValuta(BigDecimal value) {
        this.importoValuta = value;
    }

    /**
     * Gets the value of the importoEuro property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoEuro() {
        return importoEuro;
    }

    /**
     * Sets the value of the importoEuro property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoEuro(BigDecimal value) {
        this.importoEuro = value;
    }

    /**
     * Gets the value of the ident property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdent() {
        return ident;
    }

    /**
     * Sets the value of the ident property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdent(String value) {
        this.ident = value;
    }


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
     *         &lt;element name="codFiscaleEnte" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string16"/>
     *         &lt;element name="numeroPolizza" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string34"/>
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
        "codFiscaleEnte",
        "numeroPolizza"
    })
    public static class Assicurazione {

        @XmlElement(required = true)
        protected String codFiscaleEnte;
        @XmlElement(required = true)
        protected String numeroPolizza;

        /**
         * Gets the value of the codFiscaleEnte property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCodFiscaleEnte() {
            return codFiscaleEnte;
        }

        /**
         * Sets the value of the codFiscaleEnte property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCodFiscaleEnte(String value) {
            this.codFiscaleEnte = value;
        }

        /**
         * Gets the value of the numeroPolizza property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNumeroPolizza() {
            return numeroPolizza;
        }

        /**
         * Sets the value of the numeroPolizza property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNumeroPolizza(String value) {
            this.numeroPolizza = value;
        }

    }


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
     *         &lt;element name="swift" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string11" minOccurs="0"/>
     *         &lt;sequence>
     *           &lt;element name="iban" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string34"/>
     *           &lt;sequence>
     *             &lt;element name="abi" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}stringInteger5"/>
     *             &lt;element name="cab" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}stringInteger5"/>
     *             &lt;element name="numeroContoCorrente" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string12char"/>
     *           &lt;/sequence>
     *         &lt;/sequence>
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
        "swift",
        "iban",
        "abi",
        "cab",
        "numeroContoCorrente"
    })
    public static class Ente {

        protected String swift;
        @XmlElement(required = true, nillable = true)
        protected String iban;
        @XmlElement(required = true, nillable = true)
        protected String abi;
        @XmlElement(required = true, nillable = true)
        protected String cab;
        @XmlElement(required = true, nillable = true)
        protected String numeroContoCorrente;

        /**
         * Gets the value of the swift property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSwift() {
            return swift;
        }

        /**
         * Sets the value of the swift property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSwift(String value) {
            this.swift = value;
        }

        /**
         * Gets the value of the iban property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIban() {
            return iban;
        }

        /**
         * Sets the value of the iban property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIban(String value) {
            this.iban = value;
        }

        /**
         * Gets the value of the abi property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAbi() {
            return abi;
        }

        /**
         * Sets the value of the abi property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAbi(String value) {
            this.abi = value;
        }

        /**
         * Gets the value of the cab property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCab() {
            return cab;
        }

        /**
         * Sets the value of the cab property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCab(String value) {
            this.cab = value;
        }

        /**
         * Gets the value of the numeroContoCorrente property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNumeroContoCorrente() {
            return numeroContoCorrente;
        }

        /**
         * Sets the value of the numeroContoCorrente property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNumeroContoCorrente(String value) {
            this.numeroContoCorrente = value;
        }

    }

}
