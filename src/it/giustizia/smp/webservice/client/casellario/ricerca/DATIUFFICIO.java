//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.10.14 alle 10:17:21 AM CEST 
//


package it.giustizia.smp.webservice.client.casellario.ricerca;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CODICE_TIPO_UFFICIO" type="{http://it/mig/sippi/service/richiestaCertificato/type}TIPO_UFFICIO"/>
 *         &lt;element name="CODICE_SEDE_UFFICIO">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="6"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "codicetipoufficio",
    "codicesedeufficio"
})
@XmlRootElement(name = "DATI_UFFICIO")
public class DATIUFFICIO {

    @XmlElement(name = "CODICE_TIPO_UFFICIO", required = true)
    protected String codicetipoufficio;
    @XmlElement(name = "CODICE_SEDE_UFFICIO", required = true)
    protected String codicesedeufficio;

    /**
     * Recupera il valore della proprietà codicetipoufficio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODICETIPOUFFICIO() {
        return codicetipoufficio;
    }

    /**
     * Imposta il valore della proprietà codicetipoufficio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODICETIPOUFFICIO(String value) {
        this.codicetipoufficio = value;
    }

    /**
     * Recupera il valore della proprietà codicesedeufficio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODICESEDEUFFICIO() {
        return codicesedeufficio;
    }

    /**
     * Imposta il valore della proprietà codicesedeufficio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODICESEDEUFFICIO(String value) {
        this.codicesedeufficio = value;
    }

}
