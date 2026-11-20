
package it.giustizia.smp.xml.amministratori;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for beneExtType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="beneExtType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identificativoBene" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}integer15" minOccurs="0"/>
 *         &lt;element name="tipologia" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}tipoBeneType"/>
 *         &lt;element name="categoria" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}categoriaBeneType"/>
 *         &lt;element name="sottoCategoria" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}sottoCategoriaBeneType" minOccurs="0"/>
 *         &lt;element name="ubicazione" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}ubicazioneType" minOccurs="0"/>
 *         &lt;element name="descBene" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string250" minOccurs="0"/>
 *         &lt;element name="datiSpecificiBene">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice minOccurs="0">
 *                   &lt;element name="altriOggetti" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}altriOggettiExtType"/>
 *                   &lt;element name="animali" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}animaliExtType"/>
 *                   &lt;element name="azienda" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}aziendaExtType"/>
 *                   &lt;element name="finanziario">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;extension base="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}finanziarioExtType">
 *                           &lt;attribute name="tipo" use="required" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}tipoFinanziarioType" />
 *                         &lt;/extension>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="immateriale" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}immaterialeExtType"/>
 *                   &lt;element name="immobile" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}immobileExtType"/>
 *                   &lt;element name="collezioni" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}collezioniExtType"/>
 *                   &lt;element name="veicolo" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}veicoloExtType"/>
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
@XmlType(name = "beneExtType", propOrder = {
    "identificativoBene",
    "tipologia",
    "categoria",
    "sottoCategoria",
    "ubicazione",
    "descBene",
    "datiSpecificiBene",
    "gravatoDaPegnoIpoteca"
})
public class BeneExtType {

    protected BigInteger identificativoBene;
    @XmlElement(required = true)
    protected TipoBeneType tipologia;
    @XmlElement(required = true)
    protected CategoriaBeneType categoria;
    protected SottoCategoriaBeneType sottoCategoria;
    protected UbicazioneType ubicazione;
    protected String descBene;
    @XmlElement(required = true)
    protected BeneExtType.DatiSpecificiBene datiSpecificiBene;
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
     *     {@link BeneExtType.DatiSpecificiBene }
     *     
     */
    public BeneExtType.DatiSpecificiBene getDatiSpecificiBene() {
        return datiSpecificiBene;
    }

    /**
     * Sets the value of the datiSpecificiBene property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeneExtType.DatiSpecificiBene }
     *     
     */
    public void setDatiSpecificiBene(BeneExtType.DatiSpecificiBene value) {
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
     *         &lt;element name="altriOggetti" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}altriOggettiExtType"/>
     *         &lt;element name="animali" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}animaliExtType"/>
     *         &lt;element name="azienda" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}aziendaExtType"/>
     *         &lt;element name="finanziario">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;extension base="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}finanziarioExtType">
     *                 &lt;attribute name="tipo" use="required" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}tipoFinanziarioType" />
     *               &lt;/extension>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="immateriale" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}immaterialeExtType"/>
     *         &lt;element name="immobile" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}immobileExtType"/>
     *         &lt;element name="collezioni" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}collezioniExtType"/>
     *         &lt;element name="veicolo" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}veicoloExtType"/>
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

        protected AltriOggettiExtType altriOggetti;
        protected AnimaliExtType animali;
        protected AziendaExtType azienda;
        protected BeneExtType.DatiSpecificiBene.Finanziario finanziario;
        protected ImmaterialeExtType immateriale;
        protected ImmobileExtType immobile;
        protected CollezioniExtType collezioni;
        protected VeicoloExtType veicolo;

        /**
         * Gets the value of the altriOggetti property.
         * 
         * @return
         *     possible object is
         *     {@link AltriOggettiExtType }
         *     
         */
        public AltriOggettiExtType getAltriOggetti() {
            return altriOggetti;
        }

        /**
         * Sets the value of the altriOggetti property.
         * 
         * @param value
         *     allowed object is
         *     {@link AltriOggettiExtType }
         *     
         */
        public void setAltriOggetti(AltriOggettiExtType value) {
            this.altriOggetti = value;
        }

        /**
         * Gets the value of the animali property.
         * 
         * @return
         *     possible object is
         *     {@link AnimaliExtType }
         *     
         */
        public AnimaliExtType getAnimali() {
            return animali;
        }

        /**
         * Sets the value of the animali property.
         * 
         * @param value
         *     allowed object is
         *     {@link AnimaliExtType }
         *     
         */
        public void setAnimali(AnimaliExtType value) {
            this.animali = value;
        }

        /**
         * Gets the value of the azienda property.
         * 
         * @return
         *     possible object is
         *     {@link AziendaExtType }
         *     
         */
        public AziendaExtType getAzienda() {
            return azienda;
        }

        /**
         * Sets the value of the azienda property.
         * 
         * @param value
         *     allowed object is
         *     {@link AziendaExtType }
         *     
         */
        public void setAzienda(AziendaExtType value) {
            this.azienda = value;
        }

        /**
         * Gets the value of the finanziario property.
         * 
         * @return
         *     possible object is
         *     {@link BeneExtType.DatiSpecificiBene.Finanziario }
         *     
         */
        public BeneExtType.DatiSpecificiBene.Finanziario getFinanziario() {
            return finanziario;
        }

        /**
         * Sets the value of the finanziario property.
         * 
         * @param value
         *     allowed object is
         *     {@link BeneExtType.DatiSpecificiBene.Finanziario }
         *     
         */
        public void setFinanziario(BeneExtType.DatiSpecificiBene.Finanziario value) {
            this.finanziario = value;
        }

        /**
         * Gets the value of the immateriale property.
         * 
         * @return
         *     possible object is
         *     {@link ImmaterialeExtType }
         *     
         */
        public ImmaterialeExtType getImmateriale() {
            return immateriale;
        }

        /**
         * Sets the value of the immateriale property.
         * 
         * @param value
         *     allowed object is
         *     {@link ImmaterialeExtType }
         *     
         */
        public void setImmateriale(ImmaterialeExtType value) {
            this.immateriale = value;
        }

        /**
         * Gets the value of the immobile property.
         * 
         * @return
         *     possible object is
         *     {@link ImmobileExtType }
         *     
         */
        public ImmobileExtType getImmobile() {
            return immobile;
        }

        /**
         * Sets the value of the immobile property.
         * 
         * @param value
         *     allowed object is
         *     {@link ImmobileExtType }
         *     
         */
        public void setImmobile(ImmobileExtType value) {
            this.immobile = value;
        }

        /**
         * Gets the value of the collezioni property.
         * 
         * @return
         *     possible object is
         *     {@link CollezioniExtType }
         *     
         */
        public CollezioniExtType getCollezioni() {
            return collezioni;
        }

        /**
         * Sets the value of the collezioni property.
         * 
         * @param value
         *     allowed object is
         *     {@link CollezioniExtType }
         *     
         */
        public void setCollezioni(CollezioniExtType value) {
            this.collezioni = value;
        }

        /**
         * Gets the value of the veicolo property.
         * 
         * @return
         *     possible object is
         *     {@link VeicoloExtType }
         *     
         */
        public VeicoloExtType getVeicolo() {
            return veicolo;
        }

        /**
         * Sets the value of the veicolo property.
         * 
         * @param value
         *     allowed object is
         *     {@link VeicoloExtType }
         *     
         */
        public void setVeicolo(VeicoloExtType value) {
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
         *     &lt;extension base="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}finanziarioExtType">
         *       &lt;attribute name="tipo" use="required" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}tipoFinanziarioType" />
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
            extends FinanziarioExtType
        {

            @XmlAttribute(required = true)
            protected TipoFinanziarioType tipo;

            /**
             * Gets the value of the tipo property.
             * 
             * @return
             *     possible object is
             *     {@link TipoFinanziarioType }
             *     
             */
            public TipoFinanziarioType getTipo() {
                return tipo;
            }

            /**
             * Sets the value of the tipo property.
             * 
             * @param value
             *     allowed object is
             *     {@link TipoFinanziarioType }
             *     
             */
            public void setTipo(TipoFinanziarioType value) {
                this.tipo = value;
            }

        }

    }

}
