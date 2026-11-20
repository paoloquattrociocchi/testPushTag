
package it.giustizia.smp.xml.amministratori;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for beneType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="beneType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identificativoBene" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}integer15"/>
 *         &lt;element name="tipologia" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}tipoBeneType" minOccurs="0"/>
 *         &lt;element name="categoria" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}categoriaBeneType" minOccurs="0"/>
 *         &lt;element name="sottoCategoria" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}sottoCategoriaBeneType" minOccurs="0"/>
 *         &lt;element name="ubicazione" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}ubicazioneType" minOccurs="0"/>
 *         &lt;element name="descBene" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string250" minOccurs="0"/>
 *         &lt;element name="datiSpecificiBene">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice minOccurs="0">
 *                   &lt;element name="altriOggetti" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}altriOggettiType"/>
 *                   &lt;element name="animali" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}animaliType"/>
 *                   &lt;element name="azienda" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}aziendaType"/>
 *                   &lt;element name="finanziario">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;extension base="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}finanziarioType">
 *                           &lt;attribute name="tipoFinanziario" use="required" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}tipoFinanziarioType" />
 *                         &lt;/extension>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="immateriale" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}immaterialeType"/>
 *                   &lt;element name="immobile" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}immobileType"/>
 *                   &lt;element name="collezioni" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}collezioniType"/>
 *                   &lt;element name="veicolo" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}veicoloType"/>
 *                 &lt;/choice>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="gravatoDaPegnoIpoteca" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "beneType", propOrder = {
    "identificativoBene",
    "tipologia",
    "categoria",
    "sottoCategoria",
    "ubicazione",
    "descBene",
    "datiSpecificiBene",
    "gravatoDaPegnoIpoteca"
})
public class BeneType {

    @XmlElement(required = true)
    protected BigInteger identificativoBene;
    protected TipoBeneType tipologia;
    protected CategoriaBeneType categoria;
    protected SottoCategoriaBeneType sottoCategoria;
    protected UbicazioneType ubicazione;
    protected String descBene;
    @XmlElement(required = true)
    protected BeneType.DatiSpecificiBene datiSpecificiBene;
    protected Boolean gravatoDaPegnoIpoteca;

    /**
     * Gets the value of the identificativoBene property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdentificativoBene() {
        return identificativoBene;
    }

    /**
     * Sets the value of the identificativoBene property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdentificativoBene(BigInteger value) {
        this.identificativoBene = value;
    }

    /**
     * Gets the value of the tipologia property.
     * 
     * @return
     *     possible object is
     *     {@link TipoBeneType }
     *     
     */
    public TipoBeneType getTipologia() {
        return tipologia;
    }

    /**
     * Sets the value of the tipologia property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoBeneType }
     *     
     */
    public void setTipologia(TipoBeneType value) {
        this.tipologia = value;
    }

    /**
     * Gets the value of the categoria property.
     * 
     * @return
     *     possible object is
     *     {@link CategoriaBeneType }
     *     
     */
    public CategoriaBeneType getCategoria() {
        return categoria;
    }

    /**
     * Sets the value of the categoria property.
     * 
     * @param value
     *     allowed object is
     *     {@link CategoriaBeneType }
     *     
     */
    public void setCategoria(CategoriaBeneType value) {
        this.categoria = value;
    }

    /**
     * Gets the value of the sottoCategoria property.
     * 
     * @return
     *     possible object is
     *     {@link SottoCategoriaBeneType }
     *     
     */
    public SottoCategoriaBeneType getSottoCategoria() {
        return sottoCategoria;
    }

    /**
     * Sets the value of the sottoCategoria property.
     * 
     * @param value
     *     allowed object is
     *     {@link SottoCategoriaBeneType }
     *     
     */
    public void setSottoCategoria(SottoCategoriaBeneType value) {
        this.sottoCategoria = value;
    }

    /**
     * Gets the value of the ubicazione property.
     * 
     * @return
     *     possible object is
     *     {@link UbicazioneType }
     *     
     */
    public UbicazioneType getUbicazione() {
        return ubicazione;
    }

    /**
     * Sets the value of the ubicazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link UbicazioneType }
     *     
     */
    public void setUbicazione(UbicazioneType value) {
        this.ubicazione = value;
    }

    /**
     * Gets the value of the descBene property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescBene() {
        return descBene;
    }

    /**
     * Sets the value of the descBene property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescBene(String value) {
        this.descBene = value;
    }

    /**
     * Gets the value of the datiSpecificiBene property.
     * 
     * @return
     *     possible object is
     *     {@link BeneType.DatiSpecificiBene }
     *     
     */
    public BeneType.DatiSpecificiBene getDatiSpecificiBene() {
        return datiSpecificiBene;
    }

    /**
     * Sets the value of the datiSpecificiBene property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeneType.DatiSpecificiBene }
     *     
     */
    public void setDatiSpecificiBene(BeneType.DatiSpecificiBene value) {
        this.datiSpecificiBene = value;
    }

    /**
     * Gets the value of the gravatoDaPegnoIpoteca property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isGravatoDaPegnoIpoteca() {
        return gravatoDaPegnoIpoteca;
    }

    /**
     * Sets the value of the gravatoDaPegnoIpoteca property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setGravatoDaPegnoIpoteca(Boolean value) {
        this.gravatoDaPegnoIpoteca = value;
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
     *       &lt;choice minOccurs="0">
     *         &lt;element name="altriOggetti" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}altriOggettiType"/>
     *         &lt;element name="animali" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}animaliType"/>
     *         &lt;element name="azienda" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}aziendaType"/>
     *         &lt;element name="finanziario">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;extension base="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}finanziarioType">
     *                 &lt;attribute name="tipoFinanziario" use="required" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}tipoFinanziarioType" />
     *               &lt;/extension>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="immateriale" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}immaterialeType"/>
     *         &lt;element name="immobile" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}immobileType"/>
     *         &lt;element name="collezioni" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}collezioniType"/>
     *         &lt;element name="veicolo" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}veicoloType"/>
     *       &lt;/choice>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "altriOggetti",
        "animali",
        "azienda",
        "finanziario",
        "immateriale",
        "immobile",
        "collezioni",
        "veicolo"
    })
    public static class DatiSpecificiBene {

        protected AltriOggettiType altriOggetti;
        protected AnimaliType animali;
        protected AziendaType azienda;
        protected BeneType.DatiSpecificiBene.Finanziario finanziario;
        protected ImmaterialeType immateriale;
        protected ImmobileType immobile;
        protected CollezioniType collezioni;
        protected VeicoloType veicolo;

        /**
         * Gets the value of the altriOggetti property.
         * 
         * @return
         *     possible object is
         *     {@link AltriOggettiType }
         *     
         */
        public AltriOggettiType getAltriOggetti() {
            return altriOggetti;
        }

        /**
         * Sets the value of the altriOggetti property.
         * 
         * @param value
         *     allowed object is
         *     {@link AltriOggettiType }
         *     
         */
        public void setAltriOggetti(AltriOggettiType value) {
            this.altriOggetti = value;
        }

        /**
         * Gets the value of the animali property.
         * 
         * @return
         *     possible object is
         *     {@link AnimaliType }
         *     
         */
        public AnimaliType getAnimali() {
            return animali;
        }

        /**
         * Sets the value of the animali property.
         * 
         * @param value
         *     allowed object is
         *     {@link AnimaliType }
         *     
         */
        public void setAnimali(AnimaliType value) {
            this.animali = value;
        }

        /**
         * Gets the value of the azienda property.
         * 
         * @return
         *     possible object is
         *     {@link AziendaType }
         *     
         */
        public AziendaType getAzienda() {
            return azienda;
        }

        /**
         * Sets the value of the azienda property.
         * 
         * @param value
         *     allowed object is
         *     {@link AziendaType }
         *     
         */
        public void setAzienda(AziendaType value) {
            this.azienda = value;
        }

        /**
         * Gets the value of the finanziario property.
         * 
         * @return
         *     possible object is
         *     {@link BeneType.DatiSpecificiBene.Finanziario }
         *     
         */
        public BeneType.DatiSpecificiBene.Finanziario getFinanziario() {
            return finanziario;
        }

        /**
         * Sets the value of the finanziario property.
         * 
         * @param value
         *     allowed object is
         *     {@link BeneType.DatiSpecificiBene.Finanziario }
         *     
         */
        public void setFinanziario(BeneType.DatiSpecificiBene.Finanziario value) {
            this.finanziario = value;
        }

        /**
         * Gets the value of the immateriale property.
         * 
         * @return
         *     possible object is
         *     {@link ImmaterialeType }
         *     
         */
        public ImmaterialeType getImmateriale() {
            return immateriale;
        }

        /**
         * Sets the value of the immateriale property.
         * 
         * @param value
         *     allowed object is
         *     {@link ImmaterialeType }
         *     
         */
        public void setImmateriale(ImmaterialeType value) {
            this.immateriale = value;
        }

        /**
         * Gets the value of the immobile property.
         * 
         * @return
         *     possible object is
         *     {@link ImmobileType }
         *     
         */
        public ImmobileType getImmobile() {
            return immobile;
        }

        /**
         * Sets the value of the immobile property.
         * 
         * @param value
         *     allowed object is
         *     {@link ImmobileType }
         *     
         */
        public void setImmobile(ImmobileType value) {
            this.immobile = value;
        }

        /**
         * Gets the value of the collezioni property.
         * 
         * @return
         *     possible object is
         *     {@link CollezioniType }
         *     
         */
        public CollezioniType getCollezioni() {
            return collezioni;
        }

        /**
         * Sets the value of the collezioni property.
         * 
         * @param value
         *     allowed object is
         *     {@link CollezioniType }
         *     
         */
        public void setCollezioni(CollezioniType value) {
            this.collezioni = value;
        }

        /**
         * Gets the value of the veicolo property.
         * 
         * @return
         *     possible object is
         *     {@link VeicoloType }
         *     
         */
        public VeicoloType getVeicolo() {
            return veicolo;
        }

        /**
         * Sets the value of the veicolo property.
         * 
         * @param value
         *     allowed object is
         *     {@link VeicoloType }
         *     
         */
        public void setVeicolo(VeicoloType value) {
            this.veicolo = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;extension base="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}finanziarioType">
         *       &lt;attribute name="tipoFinanziario" use="required" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}tipoFinanziarioType" />
         *     &lt;/extension>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Finanziario
            extends FinanziarioType
        {

            @XmlAttribute(required = true)
            protected TipoFinanziarioType tipoFinanziario;

            /**
             * Gets the value of the tipoFinanziario property.
             * 
             * @return
             *     possible object is
             *     {@link TipoFinanziarioType }
             *     
             */
            public TipoFinanziarioType getTipoFinanziario() {
                return tipoFinanziario;
            }

            /**
             * Sets the value of the tipoFinanziario property.
             * 
             * @param value
             *     allowed object is
             *     {@link TipoFinanziarioType }
             *     
             */
            public void setTipoFinanziario(TipoFinanziarioType value) {
                this.tipoFinanziario = value;
            }

        }

    }

}
