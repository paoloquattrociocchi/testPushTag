
package it.giustizia.smp.webservice.client.casellario.esecuzione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://it/mig/sippi/service/inviaProvvedimentoEsecuzione/type}DATI_UTENTE"/>
 *         &lt;element ref="{http://it/mig/sippi/service/inviaProvvedimentoEsecuzione/type}CHIAVI"/>
 *         &lt;element ref="{http://it/mig/sippi/service/inviaProvvedimentoEsecuzione/type}DATI_OPERAZIONE"/>
 *         &lt;element ref="{http://it/mig/sippi/service/inviaProvvedimentoEsecuzione/type}DATI_ANAGRAFICA_PRINCIPALE"/>
 *         &lt;element ref="{http://it/mig/sippi/service/inviaProvvedimentoEsecuzione/type}DATI_ANAGRAFICA_ART_130" minOccurs="0"/>
 *         &lt;element ref="{http://it/mig/sippi/service/inviaProvvedimentoEsecuzione/type}PROCEDIMENTO"/>
 *         &lt;element ref="{http://it/mig/sippi/service/inviaProvvedimentoEsecuzione/type}DATI_PROVVEDIMENTO"/>
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
    "datiutente",
    "chiavi",
    "datioperazione",
    "datianagraficaprincipale",
    "datianagraficaart130",
    "procedimento",
    "datiprovvedimento"
})
@XmlRootElement(name = "DATI_CHIAMATA_TRASFERIMENTO")
public class DATICHIAMATATRASFERIMENTO {

    @XmlElement(name = "DATI_UTENTE", required = true)
    protected DATIUTENTE datiutente;
    @XmlElement(name = "CHIAVI", required = true)
    protected CHIAVI chiavi;
    @XmlElement(name = "DATI_OPERAZIONE", required = true)
    protected DATIOPERAZIONE datioperazione;
    @XmlElement(name = "DATI_ANAGRAFICA_PRINCIPALE", required = true)
    protected DATIANAGRAFICAPRINCIPALE datianagraficaprincipale;
    @XmlElement(name = "DATI_ANAGRAFICA_ART_130")
    protected DATIANAGRAFICAART130 datianagraficaart130;
    @XmlElement(name = "PROCEDIMENTO", required = true)
    protected PROCEDIMENTO procedimento;
    @XmlElement(name = "DATI_PROVVEDIMENTO", required = true)
    protected DATIPROVVEDIMENTO datiprovvedimento;

    /**
     * Gets the value of the datiutente property.
     * 
     * @return
     *     possible object is
     *     {@link DATIUTENTE }
     *     
     */
    public DATIUTENTE getDATIUTENTE() {
        return datiutente;
    }

    /**
     * Sets the value of the datiutente property.
     * 
     * @param value
     *     allowed object is
     *     {@link DATIUTENTE }
     *     
     */
    public void setDATIUTENTE(DATIUTENTE value) {
        this.datiutente = value;
    }

    /**
     * Gets the value of the chiavi property.
     * 
     * @return
     *     possible object is
     *     {@link CHIAVI }
     *     
     */
    public CHIAVI getCHIAVI() {
        return chiavi;
    }

    /**
     * Sets the value of the chiavi property.
     * 
     * @param value
     *     allowed object is
     *     {@link CHIAVI }
     *     
     */
    public void setCHIAVI(CHIAVI value) {
        this.chiavi = value;
    }

    /**
     * Gets the value of the datioperazione property.
     * 
     * @return
     *     possible object is
     *     {@link DATIOPERAZIONE }
     *     
     */
    public DATIOPERAZIONE getDATIOPERAZIONE() {
        return datioperazione;
    }

    /**
     * Sets the value of the datioperazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link DATIOPERAZIONE }
     *     
     */
    public void setDATIOPERAZIONE(DATIOPERAZIONE value) {
        this.datioperazione = value;
    }

    /**
     * Gets the value of the datianagraficaprincipale property.
     * 
     * @return
     *     possible object is
     *     {@link DATIANAGRAFICAPRINCIPALE }
     *     
     */
    public DATIANAGRAFICAPRINCIPALE getDATIANAGRAFICAPRINCIPALE() {
        return datianagraficaprincipale;
    }

    /**
     * Sets the value of the datianagraficaprincipale property.
     * 
     * @param value
     *     allowed object is
     *     {@link DATIANAGRAFICAPRINCIPALE }
     *     
     */
    public void setDATIANAGRAFICAPRINCIPALE(DATIANAGRAFICAPRINCIPALE value) {
        this.datianagraficaprincipale = value;
    }

    /**
     * Gets the value of the datianagraficaart130 property.
     * 
     * @return
     *     possible object is
     *     {@link DATIANAGRAFICAART130 }
     *     
     */
    public DATIANAGRAFICAART130 getDATIANAGRAFICAART130() {
        return datianagraficaart130;
    }

    /**
     * Sets the value of the datianagraficaart130 property.
     * 
     * @param value
     *     allowed object is
     *     {@link DATIANAGRAFICAART130 }
     *     
     */
    public void setDATIANAGRAFICAART130(DATIANAGRAFICAART130 value) {
        this.datianagraficaart130 = value;
    }

    /**
     * Gets the value of the procedimento property.
     * 
     * @return
     *     possible object is
     *     {@link PROCEDIMENTO }
     *     
     */
    public PROCEDIMENTO getPROCEDIMENTO() {
        return procedimento;
    }

    /**
     * Sets the value of the procedimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link PROCEDIMENTO }
     *     
     */
    public void setPROCEDIMENTO(PROCEDIMENTO value) {
        this.procedimento = value;
    }

    /**
     * Gets the value of the datiprovvedimento property.
     * 
     * @return
     *     possible object is
     *     {@link DATIPROVVEDIMENTO }
     *     
     */
    public DATIPROVVEDIMENTO getDATIPROVVEDIMENTO() {
        return datiprovvedimento;
    }

    /**
     * Sets the value of the datiprovvedimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link DATIPROVVEDIMENTO }
     *     
     */
    public void setDATIPROVVEDIMENTO(DATIPROVVEDIMENTO value) {
        this.datiprovvedimento = value;
    }

}
