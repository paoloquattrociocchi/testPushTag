
package it.giustizia.smp.xml.amministratori;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for amministratoreType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="amministratoreType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identificativoAmministratore" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}integer15"/>
 *         &lt;element name="cognome" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string35"/>
 *         &lt;element name="nome" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string35"/>
 *         &lt;element name="codiceFiscale" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string16" minOccurs="0"/>
 *         &lt;element name="partitaIVA" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string14" minOccurs="0"/>
 *         &lt;element name="dataNascita" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="luogoNascita" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}ubicazioneType" minOccurs="0"/>
 *         &lt;element name="indirizzoStudio" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string35" minOccurs="0"/>
 *         &lt;element name="numeroIscrizioneAlbo" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string14" minOccurs="0"/>
 *         &lt;element name="pec" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string256" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "amministratoreType", propOrder = {
    "identificativoAmministratore",
    "cognome",
    "nome",
    "codiceFiscale",
    "partitaIVA",
    "dataNascita",
    "luogoNascita",
    "indirizzoStudio",
    "numeroIscrizioneAlbo",
    "pec"
})
public class AmministratoreType {

    @XmlElement(required = true)
    protected BigInteger identificativoAmministratore;
    @XmlElement(required = true)
    protected String cognome;
    @XmlElement(required = true)
    protected String nome;
    protected String codiceFiscale;
    protected String partitaIVA;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataNascita;
    protected UbicazioneType luogoNascita;
    protected String indirizzoStudio;
    protected String numeroIscrizioneAlbo;
    protected String pec;

    /**
     * Gets the value of the identificativoAmministratore property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdentificativoAmministratore() {
        return identificativoAmministratore;
    }

    /**
     * Sets the value of the identificativoAmministratore property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdentificativoAmministratore(BigInteger value) {
        this.identificativoAmministratore = value;
    }

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
     * Gets the value of the indirizzoStudio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndirizzoStudio() {
        return indirizzoStudio;
    }

    /**
     * Sets the value of the indirizzoStudio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndirizzoStudio(String value) {
        this.indirizzoStudio = value;
    }

    /**
     * Gets the value of the numeroIscrizioneAlbo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroIscrizioneAlbo() {
        return numeroIscrizioneAlbo;
    }

    /**
     * Sets the value of the numeroIscrizioneAlbo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroIscrizioneAlbo(String value) {
        this.numeroIscrizioneAlbo = value;
    }

    /**
     * Gets the value of the pec property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPec() {
        return pec;
    }

    /**
     * Sets the value of the pec property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPec(String value) {
        this.pec = value;
    }

}
