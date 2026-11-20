
package it.giustizia.smp.xml.amministratori;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for soggettoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="soggettoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identificativo" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}integer15"/>
 *         &lt;choice>
 *           &lt;element name="personaGiuridica" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}personaGiuridicaType" minOccurs="0"/>
 *           &lt;element name="personaFisica" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}personaFisicaType" minOccurs="0"/>
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
@XmlType(name = "soggettoType", propOrder = {
    "identificativo",
    "personaGiuridica",
    "personaFisica"
})
public class SoggettoType {

    @XmlElement(required = true)
    protected BigInteger identificativo;
    protected PersonaGiuridicaType personaGiuridica;
    protected PersonaFisicaType personaFisica;

    /**
     * Gets the value of the identificativo property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdentificativo() {
        return identificativo;
    }

    /**
     * Sets the value of the identificativo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdentificativo(BigInteger value) {
        this.identificativo = value;
    }

    /**
     * Gets the value of the personaGiuridica property.
     * 
     * @return
     *     possible object is
     *     {@link PersonaGiuridicaType }
     *     
     */
    public PersonaGiuridicaType getPersonaGiuridica() {
        return personaGiuridica;
    }

    /**
     * Sets the value of the personaGiuridica property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonaGiuridicaType }
     *     
     */
    public void setPersonaGiuridica(PersonaGiuridicaType value) {
        this.personaGiuridica = value;
    }

    /**
     * Gets the value of the personaFisica property.
     * 
     * @return
     *     possible object is
     *     {@link PersonaFisicaType }
     *     
     */
    public PersonaFisicaType getPersonaFisica() {
        return personaFisica;
    }

    /**
     * Sets the value of the personaFisica property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonaFisicaType }
     *     
     */
    public void setPersonaFisica(PersonaFisicaType value) {
        this.personaFisica = value;
    }

}
