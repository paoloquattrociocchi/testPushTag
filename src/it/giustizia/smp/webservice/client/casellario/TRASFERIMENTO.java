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
 *       &lt;choice>
 *         &lt;element ref="{http://it/mig/sippi/service/inviaProvvedimento/type}DATI_CHIAMATA_TRASFERIMENTO"/>
 *         &lt;element ref="{http://it/mig/sippi/service/inviaProvvedimento/type}DATI_RISPOSTA_TRASFERIMENTO"/>
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
    "datichiamatatrasferimento",
    "datirispostatrasferimento"
})
@XmlRootElement(name = "TRASFERIMENTO")
public class TRASFERIMENTO {

    @XmlElement(name = "DATI_CHIAMATA_TRASFERIMENTO")
    protected DATICHIAMATATRASFERIMENTO datichiamatatrasferimento;
    @XmlElement(name = "DATI_RISPOSTA_TRASFERIMENTO")
    protected DATIRISPOSTATRASFERIMENTO datirispostatrasferimento;

    /**
     * Recupera il valore della proprietà datichiamatatrasferimento.
     * 
     * @return
     *     possible object is
     *     {@link DATICHIAMATATRASFERIMENTO }
     *     
     */
    public DATICHIAMATATRASFERIMENTO getDATICHIAMATATRASFERIMENTO() {
        return datichiamatatrasferimento;
    }

    /**
     * Imposta il valore della proprietà datichiamatatrasferimento.
     * 
     * @param value
     *     allowed object is
     *     {@link DATICHIAMATATRASFERIMENTO }
     *     
     */
    public void setDATICHIAMATATRASFERIMENTO(DATICHIAMATATRASFERIMENTO value) {
        this.datichiamatatrasferimento = value;
    }

    /**
     * Recupera il valore della proprietà datirispostatrasferimento.
     * 
     * @return
     *     possible object is
     *     {@link DATIRISPOSTATRASFERIMENTO }
     *     
     */
    public DATIRISPOSTATRASFERIMENTO getDATIRISPOSTATRASFERIMENTO() {
        return datirispostatrasferimento;
    }

    /**
     * Imposta il valore della proprietà datirispostatrasferimento.
     * 
     * @param value
     *     allowed object is
     *     {@link DATIRISPOSTATRASFERIMENTO }
     *     
     */
    public void setDATIRISPOSTATRASFERIMENTO(DATIRISPOSTATRASFERIMENTO value) {
        this.datirispostatrasferimento = value;
    }

}
