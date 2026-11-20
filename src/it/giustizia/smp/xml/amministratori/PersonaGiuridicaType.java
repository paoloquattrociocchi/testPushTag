
package it.giustizia.smp.xml.amministratori;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for personaGiuridicaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="personaGiuridicaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="denominazione" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string200" minOccurs="0"/>
 *         &lt;element name="partitaIVA" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string14" minOccurs="0"/>
 *         &lt;element name="tipologia" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}tipoSocietaType" minOccurs="0"/>
 *         &lt;element name="sedeLegale" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ubicazione" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}ubicazioneType" minOccurs="0"/>
 *                   &lt;element name="indirizzoSedeLegale" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string150" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
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
@XmlType(name = "personaGiuridicaType", propOrder = {
    "denominazione",
    "partitaIVA",
    "tipologia",
    "sedeLegale"
})
public class PersonaGiuridicaType {

    protected String denominazione;
    protected String partitaIVA;
    protected TipoSocietaType tipologia;
    protected PersonaGiuridicaType.SedeLegale sedeLegale;

    /**
     * Gets the value of the denominazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDenominazione() {
        return denominazione;
    }

    /**
     * Sets the value of the denominazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDenominazione(String value) {
        this.denominazione = value;
    }

    /**
     * Gets the value of the partitaIVA property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartitaIVA() {
        return partitaIVA;
    }

    /**
     * Sets the value of the partitaIVA property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartitaIVA(String value) {
        this.partitaIVA = value;
    }

    /**
     * Gets the value of the tipologia property.
     * 
     * @return
     *     possible object is
     *     {@link TipoSocietaType }
     *     
     */
    public TipoSocietaType getTipologia() {
        return tipologia;
    }

    /**
     * Sets the value of the tipologia property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoSocietaType }
     *     
     */
    public void setTipologia(TipoSocietaType value) {
        this.tipologia = value;
    }

    /**
     * Gets the value of the sedeLegale property.
     * 
     * @return
     *     possible object is
     *     {@link PersonaGiuridicaType.SedeLegale }
     *     
     */
    public PersonaGiuridicaType.SedeLegale getSedeLegale() {
        return sedeLegale;
    }

    /**
     * Sets the value of the sedeLegale property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonaGiuridicaType.SedeLegale }
     *     
     */
    public void setSedeLegale(PersonaGiuridicaType.SedeLegale value) {
        this.sedeLegale = value;
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
     *         &lt;element name="ubicazione" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}ubicazioneType" minOccurs="0"/>
     *         &lt;element name="indirizzoSedeLegale" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string150" minOccurs="0"/>
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
        "ubicazione",
        "indirizzoSedeLegale"
    })
    public static class SedeLegale {

        protected UbicazioneType ubicazione;
        protected String indirizzoSedeLegale;

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
         * Gets the value of the indirizzoSedeLegale property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIndirizzoSedeLegale() {
            return indirizzoSedeLegale;
        }

        /**
         * Sets the value of the indirizzoSedeLegale property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIndirizzoSedeLegale(String value) {
            this.indirizzoSedeLegale = value;
        }

    }

}
