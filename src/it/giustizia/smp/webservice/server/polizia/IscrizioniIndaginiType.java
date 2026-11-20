
package it.giustizia.smp.webservice.server.polizia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Trasmissione dei fascicoli di “iscrizione indagini” (accertamenti)
 * 
 * <p>Java class for IscrizioniIndaginiType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IscrizioniIndaginiType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://webservice.smp.giustizia.it/server/polizia/type}DatiAutoritaProcedente"/>
 *         &lt;element ref="{http://webservice.smp.giustizia.it/server/polizia/type}DatiUfficioDestinatario"/>
 *         &lt;element ref="{http://webservice.smp.giustizia.it/server/polizia/type}DatiSoggettiIndagine"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IscrizioniIndaginiType", propOrder = {
    "datiAutoritaProcedente",
    "datiUfficioDestinatario",
    "datiSoggettiIndagine"
})
public class IscrizioniIndaginiType {

    @XmlElement(name = "DatiAutoritaProcedente", required = true)
    protected DatiAutoritaProcedenteType datiAutoritaProcedente;
    @XmlElement(name = "DatiUfficioDestinatario", required = true)
    protected DatiUfficioDestinatarioType datiUfficioDestinatario;
    @XmlElement(name = "DatiSoggettiIndagine", required = true)
    protected DatiSoggettiIndagineType datiSoggettiIndagine;

    /**
     *  Dati dell autorità che procede all accertamento
     * 
     * @return
     *     possible object is
     *     {@link DatiAutoritaProcedenteType }
     *     
     */
    public DatiAutoritaProcedenteType getDatiAutoritaProcedente() {
        return datiAutoritaProcedente;
    }

    /**
     * Sets the value of the datiAutoritaProcedente property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiAutoritaProcedenteType }
     *     
     */
    public void setDatiAutoritaProcedente(DatiAutoritaProcedenteType value) {
        this.datiAutoritaProcedente = value;
    }

    /**
     *  Dati dell autorità destinatario dell accertamento
     * 
     * @return
     *     possible object is
     *     {@link DatiUfficioDestinatarioType }
     *     
     */
    public DatiUfficioDestinatarioType getDatiUfficioDestinatario() {
        return datiUfficioDestinatario;
    }

    /**
     * Sets the value of the datiUfficioDestinatario property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiUfficioDestinatarioType }
     *     
     */
    public void setDatiUfficioDestinatario(DatiUfficioDestinatarioType value) {
        this.datiUfficioDestinatario = value;
    }

    /**
     *  Dati identificativi dei soggetti intestatari dell indagine
     * 
     * @return
     *     possible object is
     *     {@link DatiSoggettiIndagineType }
     *     
     */
    public DatiSoggettiIndagineType getDatiSoggettiIndagine() {
        return datiSoggettiIndagine;
    }

    /**
     * Sets the value of the datiSoggettiIndagine property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiSoggettiIndagineType }
     *     
     */
    public void setDatiSoggettiIndagine(DatiSoggettiIndagineType value) {
        this.datiSoggettiIndagine = value;
    }

}
