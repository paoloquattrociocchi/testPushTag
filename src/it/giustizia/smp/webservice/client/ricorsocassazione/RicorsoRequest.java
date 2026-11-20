//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.12.22 alle 12:20:29 PM CET 
//


package it.giustizia.smp.webservice.client.ricorsocassazione;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="Procedimento" type="{http://it/eng/giustizia/sitmp/webservices/ricorsoservice}Procedimento"/>
 *         &lt;element name="RelProcedimentoProc" type="{http://it/eng/giustizia/sitmp/webservices/ricorsoservice}RelProcedimentoProc" maxOccurs="unbounded" minOccurs="0"/>
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
    "procedimento",
    "relProcedimentoProc"
})
@XmlRootElement(name = "RicorsoRequest")
public class RicorsoRequest {

    @XmlElement(name = "Procedimento", required = true)
    protected Procedimento procedimento;
    @XmlElement(name = "RelProcedimentoProc")
    protected List<RelProcedimentoProc> relProcedimentoProc;

    /**
     * Recupera il valore della proprietà procedimento.
     * 
     * @return
     *     possible object is
     *     {@link Procedimento }
     *     
     */
    public Procedimento getProcedimento() {
        return procedimento;
    }

    /**
     * Imposta il valore della proprietà procedimento.
     * 
     * @param value
     *     allowed object is
     *     {@link Procedimento }
     *     
     */
    public void setProcedimento(Procedimento value) {
        this.procedimento = value;
    }

    /**
     * Gets the value of the relProcedimentoProc property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the relProcedimentoProc property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRelProcedimentoProc().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RelProcedimentoProc }
     * 
     * 
     */
    public List<RelProcedimentoProc> getRelProcedimentoProc() {
        if (relProcedimentoProc == null) {
            relProcedimentoProc = new ArrayList<RelProcedimentoProc>();
        }
        return this.relProcedimentoProc;
    }

}
