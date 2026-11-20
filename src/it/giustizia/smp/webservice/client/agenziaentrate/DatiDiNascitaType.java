
package it.giustizia.smp.webservice.client.agenziaentrate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for datiDiNascitaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="datiDiNascitaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
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
@XmlType(name = "datiDiNascitaType", namespace = "http://types.verifica.anagrafica", propOrder = {
    "dataDiNascita",
    "comuneDiNascita",
    "provinciaDiNascita"
})
public class DatiDiNascitaType {

    protected XMLGregorianCalendar dataDiNascita;
    protected String comuneDiNascita;
    protected String provinciaDiNascita;

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
