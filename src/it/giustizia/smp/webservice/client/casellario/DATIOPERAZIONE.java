//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.09.17 alle 11:32:38 AM CEST 
//


package it.giustizia.smp.webservice.client.casellario;

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
 *         &lt;element name="ENTITA" type="{http://it/mig/sippi/service/inviaProvvedimento/type}ENTITA"/>
 *         &lt;element name="OPERAZIONE" type="{http://it/mig/sippi/service/inviaProvvedimento/type}OPERAZIONE"/>
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
    "entita",
    "operazione"
})
@XmlRootElement(name = "DATI_OPERAZIONE")
public class DATIOPERAZIONE {

    @XmlElement(name = "ENTITA", required = true)
    protected ENTITA entita;
    @XmlElement(name = "OPERAZIONE", required = true)
    protected OPERAZIONE operazione;

    /**
     * Recupera il valore della proprietà entita.
     * 
     * @return
     *     possible object is
     *     {@link ENTITA }
     *     
     */
    public ENTITA getENTITA() {
        return entita;
    }

    /**
     * Imposta il valore della proprietà entita.
     * 
     * @param value
     *     allowed object is
     *     {@link ENTITA }
     *     
     */
    public void setENTITA(ENTITA value) {
        this.entita = value;
    }

    /**
     * Recupera il valore della proprietà operazione.
     * 
     * @return
     *     possible object is
     *     {@link OPERAZIONE }
     *     
     */
    public OPERAZIONE getOPERAZIONE() {
        return operazione;
    }

    /**
     * Imposta il valore della proprietà operazione.
     * 
     * @param value
     *     allowed object is
     *     {@link OPERAZIONE }
     *     
     */
    public void setOPERAZIONE(OPERAZIONE value) {
        this.operazione = value;
    }

}
