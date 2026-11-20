//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.12.22 alle 12:20:29 PM CET 
//


package it.giustizia.smp.webservice.client.ricorsocassazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per Soggetto complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Soggetto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiKIdSoggInt" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codiKIdUffOwner" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="codiFkIdTipoSogg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PersonaFisica" type="{http://it/eng/giustizia/sitmp/webservices/ricorsoservice}PersonaFisica" minOccurs="0"/>
 *         &lt;element name="PersonaGiuridica" type="{http://it/eng/giustizia/sitmp/webservices/ricorsoservice}PersonaGiuridica" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Soggetto", propOrder = {
    "codiKIdSoggInt",
    "codiKIdUffOwner",
    "codiFkIdTipoSogg",
    "personaFisica",
    "personaGiuridica"
})
public class Soggetto {

    protected long codiKIdSoggInt;
    protected int codiKIdUffOwner;
    protected String codiFkIdTipoSogg;
    @XmlElement(name = "PersonaFisica")
    protected PersonaFisica personaFisica;
    @XmlElement(name = "PersonaGiuridica")
    protected PersonaGiuridica personaGiuridica;

    /**
     * Recupera il valore della proprietà codiKIdSoggInt.
     * 
     */
    public long getCodiKIdSoggInt() {
        return codiKIdSoggInt;
    }

    /**
     * Imposta il valore della proprietà codiKIdSoggInt.
     * 
     */
    public void setCodiKIdSoggInt(long value) {
        this.codiKIdSoggInt = value;
    }

    /**
     * Recupera il valore della proprietà codiKIdUffOwner.
     * 
     */
    public int getCodiKIdUffOwner() {
        return codiKIdUffOwner;
    }

    /**
     * Imposta il valore della proprietà codiKIdUffOwner.
     * 
     */
    public void setCodiKIdUffOwner(int value) {
        this.codiKIdUffOwner = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdTipoSogg.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiFkIdTipoSogg() {
        return codiFkIdTipoSogg;
    }

    /**
     * Imposta il valore della proprietà codiFkIdTipoSogg.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiFkIdTipoSogg(String value) {
        this.codiFkIdTipoSogg = value;
    }

    /**
     * Recupera il valore della proprietà personaFisica.
     * 
     * @return
     *     possible object is
     *     {@link PersonaFisica }
     *     
     */
    public PersonaFisica getPersonaFisica() {
        return personaFisica;
    }

    /**
     * Imposta il valore della proprietà personaFisica.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonaFisica }
     *     
     */
    public void setPersonaFisica(PersonaFisica value) {
        this.personaFisica = value;
    }

    /**
     * Recupera il valore della proprietà personaGiuridica.
     * 
     * @return
     *     possible object is
     *     {@link PersonaGiuridica }
     *     
     */
    public PersonaGiuridica getPersonaGiuridica() {
        return personaGiuridica;
    }

    /**
     * Imposta il valore della proprietà personaGiuridica.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonaGiuridica }
     *     
     */
    public void setPersonaGiuridica(PersonaGiuridica value) {
        this.personaGiuridica = value;
    }

}
