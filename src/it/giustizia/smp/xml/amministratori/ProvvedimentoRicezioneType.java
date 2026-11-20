
package it.giustizia.smp.xml.amministratori;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for provvedimentoRicezioneType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="provvedimentoRicezioneType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiceFiscale" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string16"/>
 *         &lt;element name="dataInizioPeriodo" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dataFinePeriodo" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dataPresentazioneRelazione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="numeroRiferimento" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string20" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "provvedimentoRicezioneType", propOrder = {
    "codiceFiscale",
    "dataInizioPeriodo",
    "dataFinePeriodo",
    "dataPresentazioneRelazione",
    "numeroRiferimento"
})
public class ProvvedimentoRicezioneType {

    @XmlElement(required = true)
    protected String codiceFiscale;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataInizioPeriodo;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataFinePeriodo;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataPresentazioneRelazione;
    protected String numeroRiferimento;

    /**
     * Gets the value of the codiceFiscale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    /**
     * Sets the value of the codiceFiscale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceFiscale(String value) {
        this.codiceFiscale = value;
    }

    /**
     * Gets the value of the dataInizioPeriodo property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataInizioPeriodo() {
        return dataInizioPeriodo;
    }

    /**
     * Sets the value of the dataInizioPeriodo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataInizioPeriodo(XMLGregorianCalendar value) {
        this.dataInizioPeriodo = value;
    }

    /**
     * Gets the value of the dataFinePeriodo property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataFinePeriodo() {
        return dataFinePeriodo;
    }

    /**
     * Sets the value of the dataFinePeriodo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataFinePeriodo(XMLGregorianCalendar value) {
        this.dataFinePeriodo = value;
    }

    /**
     * Gets the value of the dataPresentazioneRelazione property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataPresentazioneRelazione() {
        return dataPresentazioneRelazione;
    }

    /**
     * Sets the value of the dataPresentazioneRelazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataPresentazioneRelazione(XMLGregorianCalendar value) {
        this.dataPresentazioneRelazione = value;
    }

    /**
     * Gets the value of the numeroRiferimento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroRiferimento() {
        return numeroRiferimento;
    }

    /**
     * Sets the value of the numeroRiferimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroRiferimento(String value) {
        this.numeroRiferimento = value;
    }

}
