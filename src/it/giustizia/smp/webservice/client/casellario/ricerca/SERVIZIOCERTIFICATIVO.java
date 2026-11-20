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
 *       &lt;choice>
 *         &lt;element ref="{http://it/mig/sippi/service/richiestaCertificato/type}DATI_RICHIESTA_CERTIFICATO"/>
 *         &lt;element ref="{http://it/mig/sippi/service/richiestaCertificato/type}DATI_RISPOSTA_CERTIFICATO"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "datirichiestacertificato",
    "datirispostacertificato"
})
@XmlRootElement(name = "SERVIZIO_CERTIFICATIVO")
public class SERVIZIOCERTIFICATIVO {

    @XmlElement(name = "DATI_RICHIESTA_CERTIFICATO")
    protected DATIRICHIESTACERTIFICATO datirichiestacertificato;
    @XmlElement(name = "DATI_RISPOSTA_CERTIFICATO")
    protected DATIRISPOSTACERTIFICATO datirispostacertificato;

    /**
     * Recupera il valore della proprietà datirichiestacertificato.
     * 
     * @return
     *     possible object is
     *     {@link DATIRICHIESTACERTIFICATO }
     *     
     */
    public DATIRICHIESTACERTIFICATO getDATIRICHIESTACERTIFICATO() {
        return datirichiestacertificato;
    }

    /**
     * Imposta il valore della proprietà datirichiestacertificato.
     * 
     * @param value
     *     allowed object is
     *     {@link DATIRICHIESTACERTIFICATO }
     *     
     */
    public void setDATIRICHIESTACERTIFICATO(DATIRICHIESTACERTIFICATO value) {
        this.datirichiestacertificato = value;
    }

    /**
     * Recupera il valore della proprietà datirispostacertificato.
     * 
     * @return
     *     possible object is
     *     {@link DATIRISPOSTACERTIFICATO }
     *     
     */
    public DATIRISPOSTACERTIFICATO getDATIRISPOSTACERTIFICATO() {
        return datirispostacertificato;
    }

    /**
     * Imposta il valore della proprietà datirispostacertificato.
     * 
     * @param value
     *     allowed object is
     *     {@link DATIRISPOSTACERTIFICATO }
     *     
     */
    public void setDATIRISPOSTACERTIFICATO(DATIRISPOSTACERTIFICATO value) {
        this.datirispostacertificato = value;
    }

}
