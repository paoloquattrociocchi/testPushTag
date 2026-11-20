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
 *         &lt;element ref="{http://it/mig/sippi/service/richiestaCertificato/type}DATI_ANAGRAFICI"/>
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
    "datianagrafici"
})
@XmlRootElement(name = "OMONIMO")
public class OMONIMO {

    @XmlElement(name = "DATI_ANAGRAFICI", required = true)
    protected DATIANAGRAFICI datianagrafici;

    /**
     * Recupera il valore della proprietà datianagrafici.
     * 
     * @return
     *     possible object is
     *     {@link DATIANAGRAFICI }
     *     
     */
    public DATIANAGRAFICI getDATIANAGRAFICI() {
        return datianagrafici;
    }

    /**
     * Imposta il valore della proprietà datianagrafici.
     * 
     * @param value
     *     allowed object is
     *     {@link DATIANAGRAFICI }
     *     
     */
    public void setDATIANAGRAFICI(DATIANAGRAFICI value) {
        this.datianagrafici = value;
    }

}
