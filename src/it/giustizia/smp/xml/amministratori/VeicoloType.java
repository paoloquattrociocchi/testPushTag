
package it.giustizia.smp.xml.amministratori;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for veicoloType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="veicoloType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="numeroRegistro" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string10" minOccurs="0"/>
 *         &lt;element name="dataRegistrazione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="numeroIdentificativo" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string10" minOccurs="0"/>
 *         &lt;element name="codiceImmatricolazione" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string10" minOccurs="0"/>
 *         &lt;element name="descrizioneModello" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string40" minOccurs="0"/>
 *         &lt;element name="numeroTarga" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string10" minOccurs="0"/>
 *         &lt;element name="numeroTelaio" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string30" minOccurs="0"/>
 *         &lt;element name="descrizioneColore" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string30" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "veicoloType", propOrder = {
    "numeroRegistro",
    "dataRegistrazione",
    "numeroIdentificativo",
    "codiceImmatricolazione",
    "descrizioneModello",
    "numeroTarga",
    "numeroTelaio",
    "descrizioneColore"
})
public class VeicoloType {

    protected String numeroRegistro;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataRegistrazione;
    protected String numeroIdentificativo;
    protected String codiceImmatricolazione;
    protected String descrizioneModello;
    protected String numeroTarga;
    protected String numeroTelaio;
    protected String descrizioneColore;

    /**
     * Gets the value of the numeroRegistro property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroRegistro() {
        return numeroRegistro;
    }

    /**
     * Sets the value of the numeroRegistro property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroRegistro(String value) {
        this.numeroRegistro = value;
    }

    /**
     * Gets the value of the dataRegistrazione property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataRegistrazione() {
        return dataRegistrazione;
    }

    /**
     * Sets the value of the dataRegistrazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataRegistrazione(XMLGregorianCalendar value) {
        this.dataRegistrazione = value;
    }

    /**
     * Gets the value of the numeroIdentificativo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroIdentificativo() {
        return numeroIdentificativo;
    }

    /**
     * Sets the value of the numeroIdentificativo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroIdentificativo(String value) {
        this.numeroIdentificativo = value;
    }

    /**
     * Gets the value of the codiceImmatricolazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceImmatricolazione() {
        return codiceImmatricolazione;
    }

    /**
     * Sets the value of the codiceImmatricolazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceImmatricolazione(String value) {
        this.codiceImmatricolazione = value;
    }

    /**
     * Gets the value of the descrizioneModello property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneModello() {
        return descrizioneModello;
    }

    /**
     * Sets the value of the descrizioneModello property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneModello(String value) {
        this.descrizioneModello = value;
    }

    /**
     * Gets the value of the numeroTarga property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroTarga() {
        return numeroTarga;
    }

    /**
     * Sets the value of the numeroTarga property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroTarga(String value) {
        this.numeroTarga = value;
    }

    /**
     * Gets the value of the numeroTelaio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroTelaio() {
        return numeroTelaio;
    }

    /**
     * Sets the value of the numeroTelaio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroTelaio(String value) {
        this.numeroTelaio = value;
    }

    /**
     * Gets the value of the descrizioneColore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneColore() {
        return descrizioneColore;
    }

    /**
     * Sets the value of the descrizioneColore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneColore(String value) {
        this.descrizioneColore = value;
    }

}
