
package it.giustizia.smp.xml.amministratori;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for datiFinanzGestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="datiFinanzGestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="stima" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}decimal12_2"/>
 *         &lt;element name="dataStima" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="dirittiTerzi" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="descDirittiTerzi" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string2000" minOccurs="0"/>
 *         &lt;element name="beneRinvenuto" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="dataPossesso" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dataPerditaPossesso" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="descAltriVincoli" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string2000" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element name="finanziario">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="descIntestatarioOriginale" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string120" minOccurs="0"/>
 *                     &lt;element name="descIntestatarioAttuale" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string120" minOccurs="0"/>
 *                     &lt;element name="comunicazioneFUG" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                     &lt;element name="dataComunicazioneFug" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *                     &lt;element name="saldoPrimaComunicazione" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}decimal12_2" minOccurs="0"/>
 *                     &lt;element name="saldoPrimaRelazione" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}decimal12_2" minOccurs="0"/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="mobile">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="oneriCustodia" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                     &lt;element name="valoreOneriCustodia" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}decimal12_2" minOccurs="0"/>
 *                     &lt;element name="dataOneriCustodia" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "datiFinanzGestType", propOrder = {
    "stima",
    "dataStima",
    "dirittiTerzi",
    "descDirittiTerzi",
    "beneRinvenuto",
    "dataPossesso",
    "dataPerditaPossesso",
    "descAltriVincoli",
    "finanziario",
    "mobile"
})
public class DatiFinanzGestType {

    @XmlElement(required = true)
    protected BigDecimal stima;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataStima;
    protected Boolean dirittiTerzi;
    protected String descDirittiTerzi;
    protected Boolean beneRinvenuto;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataPossesso;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataPerditaPossesso;
    protected String descAltriVincoli;
    protected DatiFinanzGestType.Finanziario finanziario;
    protected DatiFinanzGestType.Mobile mobile;

    /**
     * Gets the value of the stima property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getStima() {
        return stima;
    }

    /**
     * Sets the value of the stima property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setStima(BigDecimal value) {
        this.stima = value;
    }

    /**
     * Gets the value of the dataStima property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataStima() {
        return dataStima;
    }

    /**
     * Sets the value of the dataStima property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataStima(XMLGregorianCalendar value) {
        this.dataStima = value;
    }

    /**
     * Gets the value of the dirittiTerzi property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDirittiTerzi() {
        return dirittiTerzi;
    }

    /**
     * Sets the value of the dirittiTerzi property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDirittiTerzi(Boolean value) {
        this.dirittiTerzi = value;
    }

    /**
     * Gets the value of the descDirittiTerzi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescDirittiTerzi() {
        return descDirittiTerzi;
    }

    /**
     * Sets the value of the descDirittiTerzi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescDirittiTerzi(String value) {
        this.descDirittiTerzi = value;
    }

    /**
     * Gets the value of the beneRinvenuto property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isBeneRinvenuto() {
        return beneRinvenuto;
    }

    /**
     * Sets the value of the beneRinvenuto property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setBeneRinvenuto(Boolean value) {
        this.beneRinvenuto = value;
    }

    /**
     * Gets the value of the dataPossesso property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataPossesso() {
        return dataPossesso;
    }

    /**
     * Sets the value of the dataPossesso property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataPossesso(XMLGregorianCalendar value) {
        this.dataPossesso = value;
    }

    /**
     * Gets the value of the dataPerditaPossesso property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataPerditaPossesso() {
        return dataPerditaPossesso;
    }

    /**
     * Sets the value of the dataPerditaPossesso property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataPerditaPossesso(XMLGregorianCalendar value) {
        this.dataPerditaPossesso = value;
    }

    /**
     * Gets the value of the descAltriVincoli property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescAltriVincoli() {
        return descAltriVincoli;
    }

    /**
     * Sets the value of the descAltriVincoli property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescAltriVincoli(String value) {
        this.descAltriVincoli = value;
    }

    /**
     * Gets the value of the finanziario property.
     * 
     * @return
     *     possible object is
     *     {@link DatiFinanzGestType.Finanziario }
     *     
     */
    public DatiFinanzGestType.Finanziario getFinanziario() {
        return finanziario;
    }

    /**
     * Sets the value of the finanziario property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiFinanzGestType.Finanziario }
     *     
     */
    public void setFinanziario(DatiFinanzGestType.Finanziario value) {
        this.finanziario = value;
    }

    /**
     * Gets the value of the mobile property.
     * 
     * @return
     *     possible object is
     *     {@link DatiFinanzGestType.Mobile }
     *     
     */
    public DatiFinanzGestType.Mobile getMobile() {
        return mobile;
    }

    /**
     * Sets the value of the mobile property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiFinanzGestType.Mobile }
     *     
     */
    public void setMobile(DatiFinanzGestType.Mobile value) {
        this.mobile = value;
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
     *         &lt;element name="descIntestatarioOriginale" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string120" minOccurs="0"/>
     *         &lt;element name="descIntestatarioAttuale" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string120" minOccurs="0"/>
     *         &lt;element name="comunicazioneFUG" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
     *         &lt;element name="dataComunicazioneFug" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
     *         &lt;element name="saldoPrimaComunicazione" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}decimal12_2" minOccurs="0"/>
     *         &lt;element name="saldoPrimaRelazione" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}decimal12_2" minOccurs="0"/>
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
        "descIntestatarioOriginale",
        "descIntestatarioAttuale",
        "comunicazioneFUG",
        "dataComunicazioneFug",
        "saldoPrimaComunicazione",
        "saldoPrimaRelazione"
    })
    public static class Finanziario {

        protected String descIntestatarioOriginale;
        protected String descIntestatarioAttuale;
        protected Boolean comunicazioneFUG;
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar dataComunicazioneFug;
        protected BigDecimal saldoPrimaComunicazione;
        protected BigDecimal saldoPrimaRelazione;

        /**
         * Gets the value of the descIntestatarioOriginale property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDescIntestatarioOriginale() {
            return descIntestatarioOriginale;
        }

        /**
         * Sets the value of the descIntestatarioOriginale property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDescIntestatarioOriginale(String value) {
            this.descIntestatarioOriginale = value;
        }

        /**
         * Gets the value of the descIntestatarioAttuale property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDescIntestatarioAttuale() {
            return descIntestatarioAttuale;
        }

        /**
         * Sets the value of the descIntestatarioAttuale property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDescIntestatarioAttuale(String value) {
            this.descIntestatarioAttuale = value;
        }

        /**
         * Gets the value of the comunicazioneFUG property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isComunicazioneFUG() {
            return comunicazioneFUG;
        }

        /**
         * Sets the value of the comunicazioneFUG property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setComunicazioneFUG(Boolean value) {
            this.comunicazioneFUG = value;
        }

        /**
         * Gets the value of the dataComunicazioneFug property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getDataComunicazioneFug() {
            return dataComunicazioneFug;
        }

        /**
         * Sets the value of the dataComunicazioneFug property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setDataComunicazioneFug(XMLGregorianCalendar value) {
            this.dataComunicazioneFug = value;
        }

        /**
         * Gets the value of the saldoPrimaComunicazione property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getSaldoPrimaComunicazione() {
            return saldoPrimaComunicazione;
        }

        /**
         * Sets the value of the saldoPrimaComunicazione property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setSaldoPrimaComunicazione(BigDecimal value) {
            this.saldoPrimaComunicazione = value;
        }

        /**
         * Gets the value of the saldoPrimaRelazione property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getSaldoPrimaRelazione() {
            return saldoPrimaRelazione;
        }

        /**
         * Sets the value of the saldoPrimaRelazione property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setSaldoPrimaRelazione(BigDecimal value) {
            this.saldoPrimaRelazione = value;
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
     *         &lt;element name="oneriCustodia" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
     *         &lt;element name="valoreOneriCustodia" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}decimal12_2" minOccurs="0"/>
     *         &lt;element name="dataOneriCustodia" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
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
        "oneriCustodia",
        "valoreOneriCustodia",
        "dataOneriCustodia"
    })
    public static class Mobile {

        protected Boolean oneriCustodia;
        protected BigDecimal valoreOneriCustodia;
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar dataOneriCustodia;

        /**
         * Gets the value of the oneriCustodia property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isOneriCustodia() {
            return oneriCustodia;
        }

        /**
         * Sets the value of the oneriCustodia property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setOneriCustodia(Boolean value) {
            this.oneriCustodia = value;
        }

        /**
         * Gets the value of the valoreOneriCustodia property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getValoreOneriCustodia() {
            return valoreOneriCustodia;
        }

        /**
         * Sets the value of the valoreOneriCustodia property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setValoreOneriCustodia(BigDecimal value) {
            this.valoreOneriCustodia = value;
        }

        /**
         * Gets the value of the dataOneriCustodia property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getDataOneriCustodia() {
            return dataOneriCustodia;
        }

        /**
         * Sets the value of the dataOneriCustodia property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setDataOneriCustodia(XMLGregorianCalendar value) {
            this.dataOneriCustodia = value;
        }

    }

}
