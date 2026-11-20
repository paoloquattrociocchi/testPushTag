
package it.giustizia.smp.webservice.client.agenziaentrate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for interrogazioneDatiAnagraficiPersonaFisicaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="interrogazioneDatiAnagraficiPersonaFisicaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cognome" type="{http://types.verifica.anagrafica}cognomeType"/>
 *         &lt;element name="nome" type="{http://types.verifica.anagrafica}nomeType" minOccurs="0"/>
 *         &lt;element name="sesso" type="{http://types.verifica.anagrafica}sessoType" minOccurs="0"/>
 *         &lt;element name="dataDiNascita" type="{http://types.verifica.anagrafica}dataType" minOccurs="0"/>
 *         &lt;element name="comuneDiNascita" type="{http://types.verifica.anagrafica}comuneType" minOccurs="0"/>
 *         &lt;element name="provinciaDiNascita" type="{http://types.verifica.anagrafica}provinciaType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "interrogazioneDatiAnagraficiPersonaFisicaType", propOrder = {
    "cognome",
    "nome",
    "sesso",
    "dataDiNascita",
    "comuneDiNascita",
    "provinciaDiNascita"
})
public class InterrogazioneDatiAnagraficiPersonaFisicaType {

    @XmlElement(required = true)
    protected String cognome;
    protected String nome;
    protected SessoType sesso;
    protected XMLGregorianCalendar dataDiNascita;
    protected String comuneDiNascita;
    protected String provinciaDiNascita;

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
     * Gets the value of the dataDiNascita property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataDiNascita() {
        return dataDiNascita;
    }

    /**
     * Sets the value of the dataDiNascita property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataDiNascita(XMLGregorianCalendar value) {
        this.dataDiNascita = value;
    }

    /**
     * Gets the value of the comuneDiNascita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComuneDiNascita() {
        return comuneDiNascita;
    }

    /**
     * Sets the value of the comuneDiNascita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComuneDiNascita(String value) {
        this.comuneDiNascita = value;
    }

    /**
     * Gets the value of the provinciaDiNascita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvinciaDiNascita() {
        return provinciaDiNascita;
    }

    /**
     * Sets the value of the provinciaDiNascita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvinciaDiNascita(String value) {
        this.provinciaDiNascita = value;
    }

}
