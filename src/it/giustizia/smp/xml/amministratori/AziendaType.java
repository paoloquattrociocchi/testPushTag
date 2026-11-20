
package it.giustizia.smp.xml.amministratori;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for aziendaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="aziendaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ragioneSociale" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string200" minOccurs="0"/>
 *         &lt;element name="partitaIva" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string14" minOccurs="0"/>
 *         &lt;element name="numerocciaa" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string30" minOccurs="0"/>
 *         &lt;element name="codiceFiscale" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string11" minOccurs="0"/>
 *         &lt;element name="capitaleSociale" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}integer15" minOccurs="0"/>
 *         &lt;element name="descrizioneRappresentanteSocieta" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string200" minOccurs="0"/>
 *         &lt;element name="descrizioneAmministratore" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string200" minOccurs="0"/>
 *         &lt;element name="numeroDipendenti" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}integer15" minOccurs="0"/>
 *         &lt;element name="settoreAttivita" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}settoreAttivitaType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "aziendaType", propOrder = {
    "ragioneSociale",
    "partitaIva",
    "numerocciaa",
    "codiceFiscale",
    "capitaleSociale",
    "descrizioneRappresentanteSocieta",
    "descrizioneAmministratore",
    "numeroDipendenti",
    "settoreAttivita"
})
public class AziendaType {

    protected String ragioneSociale;
    protected String partitaIva;
    protected String numerocciaa;
    protected String codiceFiscale;
    protected BigInteger capitaleSociale;
    protected String descrizioneRappresentanteSocieta;
    protected String descrizioneAmministratore;
    protected BigInteger numeroDipendenti;
    protected SettoreAttivitaType settoreAttivita;

    /**
     * Gets the value of the ragioneSociale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRagioneSociale() {
        return ragioneSociale;
    }

    /**
     * Sets the value of the ragioneSociale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRagioneSociale(String value) {
        this.ragioneSociale = value;
    }

    /**
     * Gets the value of the partitaIva property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartitaIva() {
        return partitaIva;
    }

    /**
     * Sets the value of the partitaIva property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartitaIva(String value) {
        this.partitaIva = value;
    }

    /**
     * Gets the value of the numerocciaa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumerocciaa() {
        return numerocciaa;
    }

    /**
     * Sets the value of the numerocciaa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumerocciaa(String value) {
        this.numerocciaa = value;
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
     * Gets the value of the capitaleSociale property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCapitaleSociale() {
        return capitaleSociale;
    }

    /**
     * Sets the value of the capitaleSociale property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCapitaleSociale(BigInteger value) {
        this.capitaleSociale = value;
    }

    /**
     * Gets the value of the descrizioneRappresentanteSocieta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneRappresentanteSocieta() {
        return descrizioneRappresentanteSocieta;
    }

    /**
     * Sets the value of the descrizioneRappresentanteSocieta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneRappresentanteSocieta(String value) {
        this.descrizioneRappresentanteSocieta = value;
    }

    /**
     * Gets the value of the descrizioneAmministratore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneAmministratore() {
        return descrizioneAmministratore;
    }

    /**
     * Sets the value of the descrizioneAmministratore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneAmministratore(String value) {
        this.descrizioneAmministratore = value;
    }

    /**
     * Gets the value of the numeroDipendenti property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumeroDipendenti() {
        return numeroDipendenti;
    }

    /**
     * Sets the value of the numeroDipendenti property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumeroDipendenti(BigInteger value) {
        this.numeroDipendenti = value;
    }

    /**
     * Gets the value of the settoreAttivita property.
     * 
     * @return
     *     possible object is
     *     {@link SettoreAttivitaType }
     *     
     */
    public SettoreAttivitaType getSettoreAttivita() {
        return settoreAttivita;
    }

    /**
     * Sets the value of the settoreAttivita property.
     * 
     * @param value
     *     allowed object is
     *     {@link SettoreAttivitaType }
     *     
     */
    public void setSettoreAttivita(SettoreAttivitaType value) {
        this.settoreAttivita = value;
    }

}
