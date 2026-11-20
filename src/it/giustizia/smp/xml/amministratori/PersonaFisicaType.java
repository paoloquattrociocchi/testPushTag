
package it.giustizia.smp.xml.amministratori;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for personaFisicaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="personaFisicaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cognome" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string35" minOccurs="0"/>
 *         &lt;element name="nome" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string35" minOccurs="0"/>
 *         &lt;element name="sesso" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string1" minOccurs="0"/>
 *         &lt;element name="dataNascita" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="luogoNascita" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}ubicazioneType" minOccurs="0"/>
 *         &lt;element name="codiceFiscale" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string16" minOccurs="0"/>
 *         &lt;element name="cui" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string7" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "personaFisicaType", propOrder = {
    "cognome",
    "nome",
    "sesso",
    "dataNascita",
    "luogoNascita",
    "codiceFiscale",
    "cui"
})
public class PersonaFisicaType {

    protected String cognome;
    protected String nome;
    protected String sesso;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataNascita;
    protected UbicazioneType luogoNascita;
    protected String codiceFiscale;
    protected String cui;

    /**
     * Gets the value of the cognome property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Sets the value of the cognome property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCognome(String value) {
        this.cognome = value;
    }

    /**
     * Gets the value of the nome property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNome() {
        return nome;
    }

    /**
     * Sets the value of the nome property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNome(String value) {
        this.nome = value;
    }

    /**
     * Gets the value of the sesso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSesso() {
        return sesso;
    }

    /**
     * Sets the value of the sesso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSesso(String value) {
        this.sesso = value;
    }

    /**
     * Gets the value of the dataNascita property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataNascita() {
        return dataNascita;
    }

    /**
     * Sets the value of the dataNascita property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataNascita(XMLGregorianCalendar value) {
        this.dataNascita = value;
    }

    /**
     * Gets the value of the luogoNascita property.
     * 
     * @return
     *     possible object is
     *     {@link UbicazioneType }
     *     
     */
    public UbicazioneType getLuogoNascita() {
        return luogoNascita;
    }

    /**
     * Sets the value of the luogoNascita property.
     * 
     * @param value
     *     allowed object is
     *     {@link UbicazioneType }
     *     
     */
    public void setLuogoNascita(UbicazioneType value) {
        this.luogoNascita = value;
    }

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
     * Gets the value of the cui property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCui() {
        return cui;
    }

    /**
     * Sets the value of the cui property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCui(String value) {
        this.cui = value;
    }

}
