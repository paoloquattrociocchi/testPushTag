
package it.giustizia.smp.webservice.client.agenziaentrate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="codiceFiscale" type="{http://types.verifica.anagrafica}codiceFiscaleType" minOccurs="0"/>
 *         &lt;element name="cognome" type="{http://types.verifica.anagrafica}cognomeType" minOccurs="0"/>
 *         &lt;element name="nome" type="{http://types.verifica.anagrafica}nomeType" minOccurs="0"/>
 *         &lt;element name="sesso" type="{http://types.verifica.anagrafica}sessoType" minOccurs="0"/>
 *         &lt;element name="datiDiNascita" type="{http://types.verifica.anagrafica}datiDiNascitaType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="dettagli" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "personaFisicaType", propOrder = {
    "codiceFiscale",
    "cognome",
    "nome",
    "sesso",
    "datiDiNascita"
})
public class PersonaFisicaType {

    protected String codiceFiscale;
    protected String cognome;
    protected String nome;
    protected SessoType sesso;
    protected DatiDiNascitaType datiDiNascita;
    @XmlAttribute
    protected String dettagli;

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
     *     {@link SessoType }
     *     
     */
    public SessoType getSesso() {
        return sesso;
    }

    /**
     * Sets the value of the sesso property.
     * 
     * @param value
     *     allowed object is
     *     {@link SessoType }
     *     
     */
    public void setSesso(SessoType value) {
        this.sesso = value;
    }

    /**
     * Gets the value of the datiDiNascita property.
     * 
     * @return
     *     possible object is
     *     {@link DatiDiNascitaType }
     *     
     */
    public DatiDiNascitaType getDatiDiNascita() {
        return datiDiNascita;
    }

    /**
     * Sets the value of the datiDiNascita property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiDiNascitaType }
     *     
     */
    public void setDatiDiNascita(DatiDiNascitaType value) {
        this.datiDiNascita = value;
    }

    /**
     * Gets the value of the dettagli property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDettagli() {
        return dettagli;
    }

    /**
     * Sets the value of the dettagli property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDettagli(String value) {
        this.dettagli = value;
    }

}
