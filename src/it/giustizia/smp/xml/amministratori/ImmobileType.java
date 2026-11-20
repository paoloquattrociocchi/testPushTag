
package it.giustizia.smp.xml.amministratori;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for immobileType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="immobileType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="edificio" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string2" minOccurs="0"/>
 *         &lt;element name="scala" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string3" minOccurs="0"/>
 *         &lt;element name="piano" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string3" minOccurs="0"/>
 *         &lt;element name="interno" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string3" minOccurs="0"/>
 *         &lt;element name="volume_int" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}integer3" minOccurs="0"/>
 *         &lt;element name="volume_dec" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}integer1" minOccurs="0"/>
 *         &lt;element name="vani_int" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}integer3" minOccurs="0"/>
 *         &lt;element name="vani_dec" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}integer1" minOccurs="0"/>
 *         &lt;element name="lotto" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string2" minOccurs="0"/>
 *         &lt;element name="superficie_int" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}integer3" minOccurs="0"/>
 *         &lt;element name="superficie_dec" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}integer1" minOccurs="0"/>
 *         &lt;element name="ettari" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}integer5" minOccurs="0"/>
 *         &lt;element name="are" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}integer5" minOccurs="0"/>
 *         &lt;element name="centiare" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}integer5" minOccurs="0"/>
 *         &lt;element name="datiCatastali">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="tipoCatasto" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}tipoCatastoType" minOccurs="0"/>
 *                   &lt;element name="identificativoComuneCatasto" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string4" minOccurs="0"/>
 *                   &lt;element name="sezioneCatasto" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string4" minOccurs="0"/>
 *                   &lt;element name="foglioCatasto" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string4" minOccurs="0"/>
 *                   &lt;element name="particellaCatasto1" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string5" minOccurs="0"/>
 *                   &lt;element name="particellaCatasto2" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string4" minOccurs="0"/>
 *                   &lt;element name="subparticellaCatasto1" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string4" minOccurs="0"/>
 *                   &lt;element name="subparticellaCatasto2" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string3" minOccurs="0"/>
 *                   &lt;element name="identificativoCategoriaCatastale" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}categoriaCatastaleType" minOccurs="0"/>
 *                   &lt;element name="importoRenditaCatastale" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}decimal15_2" minOccurs="0"/>
 *                   &lt;element name="importoRenditaDominicale" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}decimal15_2" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="numeroDomandaAccatastamento" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string10" minOccurs="0"/>
 *         &lt;element name="dataDomandaAccatastamento" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="appartenenzaaFabbricatoConfiscato" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string1" minOccurs="0"/>
 *         &lt;element name="tipoFondo" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}tipoFondoType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "immobileType", propOrder = {
    "edificio",
    "scala",
    "piano",
    "interno",
    "volumeInt",
    "volumeDec",
    "vaniInt",
    "vaniDec",
    "lotto",
    "superficieInt",
    "superficieDec",
    "ettari",
    "are",
    "centiare",
    "datiCatastali",
    "numeroDomandaAccatastamento",
    "dataDomandaAccatastamento",
    "appartenenzaaFabbricatoConfiscato",
    "tipoFondo"
})
public class ImmobileType {

    protected String edificio;
    protected String scala;
    protected String piano;
    protected String interno;
    @XmlElement(name = "volume_int")
    protected BigInteger volumeInt;
    @XmlElement(name = "volume_dec")
    protected BigInteger volumeDec;
    @XmlElement(name = "vani_int")
    protected BigInteger vaniInt;
    @XmlElement(name = "vani_dec")
    protected BigInteger vaniDec;
    protected String lotto;
    @XmlElement(name = "superficie_int")
    protected BigInteger superficieInt;
    @XmlElement(name = "superficie_dec")
    protected BigInteger superficieDec;
    protected BigInteger ettari;
    protected BigInteger are;
    protected BigInteger centiare;
    @XmlElement(required = true)
    protected ImmobileType.DatiCatastali datiCatastali;
    protected String numeroDomandaAccatastamento;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataDomandaAccatastamento;
    protected String appartenenzaaFabbricatoConfiscato;
    protected TipoFondoType tipoFondo;

    /**
     * Gets the value of the edificio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEdificio() {
        return edificio;
    }

    /**
     * Sets the value of the edificio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEdificio(String value) {
        this.edificio = value;
    }

    /**
     * Gets the value of the scala property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScala() {
        return scala;
    }

    /**
     * Sets the value of the scala property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScala(String value) {
        this.scala = value;
    }

    /**
     * Gets the value of the piano property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPiano() {
        return piano;
    }

    /**
     * Sets the value of the piano property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPiano(String value) {
        this.piano = value;
    }

    /**
     * Gets the value of the interno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInterno() {
        return interno;
    }

    /**
     * Sets the value of the interno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInterno(String value) {
        this.interno = value;
    }

    /**
     * Gets the value of the volumeInt property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getVolumeInt() {
        return volumeInt;
    }

    /**
     * Sets the value of the volumeInt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setVolumeInt(BigInteger value) {
        this.volumeInt = value;
    }

    /**
     * Gets the value of the volumeDec property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getVolumeDec() {
        return volumeDec;
    }

    /**
     * Sets the value of the volumeDec property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setVolumeDec(BigInteger value) {
        this.volumeDec = value;
    }

    /**
     * Gets the value of the vaniInt property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getVaniInt() {
        return vaniInt;
    }

    /**
     * Sets the value of the vaniInt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setVaniInt(BigInteger value) {
        this.vaniInt = value;
    }

    /**
     * Gets the value of the vaniDec property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getVaniDec() {
        return vaniDec;
    }

    /**
     * Sets the value of the vaniDec property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setVaniDec(BigInteger value) {
        this.vaniDec = value;
    }

    /**
     * Gets the value of the lotto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLotto() {
        return lotto;
    }

    /**
     * Sets the value of the lotto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLotto(String value) {
        this.lotto = value;
    }

    /**
     * Gets the value of the superficieInt property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSuperficieInt() {
        return superficieInt;
    }

    /**
     * Sets the value of the superficieInt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSuperficieInt(BigInteger value) {
        this.superficieInt = value;
    }

    /**
     * Gets the value of the superficieDec property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSuperficieDec() {
        return superficieDec;
    }

    /**
     * Sets the value of the superficieDec property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSuperficieDec(BigInteger value) {
        this.superficieDec = value;
    }

    /**
     * Gets the value of the ettari property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getEttari() {
        return ettari;
    }

    /**
     * Sets the value of the ettari property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setEttari(BigInteger value) {
        this.ettari = value;
    }

    /**
     * Gets the value of the are property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAre() {
        return are;
    }

    /**
     * Sets the value of the are property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAre(BigInteger value) {
        this.are = value;
    }

    /**
     * Gets the value of the centiare property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCentiare() {
        return centiare;
    }

    /**
     * Sets the value of the centiare property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCentiare(BigInteger value) {
        this.centiare = value;
    }

    /**
     * Gets the value of the datiCatastali property.
     * 
     * @return
     *     possible object is
     *     {@link ImmobileType.DatiCatastali }
     *     
     */
    public ImmobileType.DatiCatastali getDatiCatastali() {
        return datiCatastali;
    }

    /**
     * Sets the value of the datiCatastali property.
     * 
     * @param value
     *     allowed object is
     *     {@link ImmobileType.DatiCatastali }
     *     
     */
    public void setDatiCatastali(ImmobileType.DatiCatastali value) {
        this.datiCatastali = value;
    }

    /**
     * Gets the value of the numeroDomandaAccatastamento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroDomandaAccatastamento() {
        return numeroDomandaAccatastamento;
    }

    /**
     * Sets the value of the numeroDomandaAccatastamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroDomandaAccatastamento(String value) {
        this.numeroDomandaAccatastamento = value;
    }

    /**
     * Gets the value of the dataDomandaAccatastamento property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataDomandaAccatastamento() {
        return dataDomandaAccatastamento;
    }

    /**
     * Sets the value of the dataDomandaAccatastamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataDomandaAccatastamento(XMLGregorianCalendar value) {
        this.dataDomandaAccatastamento = value;
    }

    /**
     * Gets the value of the appartenenzaaFabbricatoConfiscato property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppartenenzaaFabbricatoConfiscato() {
        return appartenenzaaFabbricatoConfiscato;
    }

    /**
     * Sets the value of the appartenenzaaFabbricatoConfiscato property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppartenenzaaFabbricatoConfiscato(String value) {
        this.appartenenzaaFabbricatoConfiscato = value;
    }

    /**
     * Gets the value of the tipoFondo property.
     * 
     * @return
     *     possible object is
     *     {@link TipoFondoType }
     *     
     */
    public TipoFondoType getTipoFondo() {
        return tipoFondo;
    }

    /**
     * Sets the value of the tipoFondo property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoFondoType }
     *     
     */
    public void setTipoFondo(TipoFondoType value) {
        this.tipoFondo = value;
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
     *         &lt;element name="tipoCatasto" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}tipoCatastoType" minOccurs="0"/>
     *         &lt;element name="identificativoComuneCatasto" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string4" minOccurs="0"/>
     *         &lt;element name="sezioneCatasto" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string4" minOccurs="0"/>
     *         &lt;element name="foglioCatasto" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string4" minOccurs="0"/>
     *         &lt;element name="particellaCatasto1" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string5" minOccurs="0"/>
     *         &lt;element name="particellaCatasto2" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string4" minOccurs="0"/>
     *         &lt;element name="subparticellaCatasto1" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string4" minOccurs="0"/>
     *         &lt;element name="subparticellaCatasto2" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string3" minOccurs="0"/>
     *         &lt;element name="identificativoCategoriaCatastale" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}categoriaCatastaleType" minOccurs="0"/>
     *         &lt;element name="importoRenditaCatastale" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}decimal15_2" minOccurs="0"/>
     *         &lt;element name="importoRenditaDominicale" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}decimal15_2" minOccurs="0"/>
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
        "tipoCatasto",
        "identificativoComuneCatasto",
        "sezioneCatasto",
        "foglioCatasto",
        "particellaCatasto1",
        "particellaCatasto2",
        "subparticellaCatasto1",
        "subparticellaCatasto2",
        "identificativoCategoriaCatastale",
        "importoRenditaCatastale",
        "importoRenditaDominicale"
    })
    public static class DatiCatastali {

        protected TipoCatastoType tipoCatasto;
        protected String identificativoComuneCatasto;
        protected String sezioneCatasto;
        protected String foglioCatasto;
        protected String particellaCatasto1;
        protected String particellaCatasto2;
        protected String subparticellaCatasto1;
        protected String subparticellaCatasto2;
        protected CategoriaCatastaleType identificativoCategoriaCatastale;
        protected BigDecimal importoRenditaCatastale;
        protected BigDecimal importoRenditaDominicale;

        /**
         * Gets the value of the tipoCatasto property.
         * 
         * @return
         *     possible object is
         *     {@link TipoCatastoType }
         *     
         */
        public TipoCatastoType getTipoCatasto() {
            return tipoCatasto;
        }

        /**
         * Sets the value of the tipoCatasto property.
         * 
         * @param value
         *     allowed object is
         *     {@link TipoCatastoType }
         *     
         */
        public void setTipoCatasto(TipoCatastoType value) {
            this.tipoCatasto = value;
        }

        /**
         * Gets the value of the identificativoComuneCatasto property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIdentificativoComuneCatasto() {
            return identificativoComuneCatasto;
        }

        /**
         * Sets the value of the identificativoComuneCatasto property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIdentificativoComuneCatasto(String value) {
            this.identificativoComuneCatasto = value;
        }

        /**
         * Gets the value of the sezioneCatasto property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSezioneCatasto() {
            return sezioneCatasto;
        }

        /**
         * Sets the value of the sezioneCatasto property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSezioneCatasto(String value) {
            this.sezioneCatasto = value;
        }

        /**
         * Gets the value of the foglioCatasto property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFoglioCatasto() {
            return foglioCatasto;
        }

        /**
         * Sets the value of the foglioCatasto property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFoglioCatasto(String value) {
            this.foglioCatasto = value;
        }

        /**
         * Gets the value of the particellaCatasto1 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getParticellaCatasto1() {
            return particellaCatasto1;
        }

        /**
         * Sets the value of the particellaCatasto1 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setParticellaCatasto1(String value) {
            this.particellaCatasto1 = value;
        }

        /**
         * Gets the value of the particellaCatasto2 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getParticellaCatasto2() {
            return particellaCatasto2;
        }

        /**
         * Sets the value of the particellaCatasto2 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setParticellaCatasto2(String value) {
            this.particellaCatasto2 = value;
        }

        /**
         * Gets the value of the subparticellaCatasto1 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSubparticellaCatasto1() {
            return subparticellaCatasto1;
        }

        /**
         * Sets the value of the subparticellaCatasto1 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSubparticellaCatasto1(String value) {
            this.subparticellaCatasto1 = value;
        }

        /**
         * Gets the value of the subparticellaCatasto2 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSubparticellaCatasto2() {
            return subparticellaCatasto2;
        }

        /**
         * Sets the value of the subparticellaCatasto2 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSubparticellaCatasto2(String value) {
            this.subparticellaCatasto2 = value;
        }

        /**
         * Gets the value of the identificativoCategoriaCatastale property.
         * 
         * @return
         *     possible object is
         *     {@link CategoriaCatastaleType }
         *     
         */
        public CategoriaCatastaleType getIdentificativoCategoriaCatastale() {
            return identificativoCategoriaCatastale;
        }

        /**
         * Sets the value of the identificativoCategoriaCatastale property.
         * 
         * @param value
         *     allowed object is
         *     {@link CategoriaCatastaleType }
         *     
         */
        public void setIdentificativoCategoriaCatastale(CategoriaCatastaleType value) {
            this.identificativoCategoriaCatastale = value;
        }

        /**
         * Gets the value of the importoRenditaCatastale property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getImportoRenditaCatastale() {
            return importoRenditaCatastale;
        }

        /**
         * Sets the value of the importoRenditaCatastale property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setImportoRenditaCatastale(BigDecimal value) {
            this.importoRenditaCatastale = value;
        }

        /**
         * Gets the value of the importoRenditaDominicale property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getImportoRenditaDominicale() {
            return importoRenditaDominicale;
        }

        /**
         * Sets the value of the importoRenditaDominicale property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setImportoRenditaDominicale(BigDecimal value) {
            this.importoRenditaDominicale = value;
        }

    }

}
