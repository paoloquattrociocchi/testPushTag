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
 *         &lt;element ref="{http://it/mig/sippi/service/inviaProvvedimento/type}DATI_ANAGRAFICI"/>
 *         &lt;element ref="{http://it/mig/sippi/service/inviaProvvedimento/type}PROCEDIMENTO"/>
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
    "datianagrafici",
    "procedimento"
})
@XmlRootElement(name = "ANAGRAFICA")
public class ANAGRAFICA {

    @XmlElement(name = "DATI_ANAGRAFICI", required = true)
    protected DATIANAGRAFICI datianagrafici;
    @XmlElement(name = "PROCEDIMENTO", required = true)
    protected PROCEDIMENTO procedimento;

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

    /**
     * Recupera il valore della proprietà procedimento.
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
     * Imposta il valore della proprietà procedimento.
     * 
     * @param value
     *     allowed object is
     *     {@link PROCEDIMENTO }
     *     
     */
    public void setPROCEDIMENTO(PROCEDIMENTO value) {
        this.procedimento = value;
    }

}
