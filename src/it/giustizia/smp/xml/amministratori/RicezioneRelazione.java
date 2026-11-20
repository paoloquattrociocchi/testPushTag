
package it.giustizia.smp.xml.amministratori;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="procedimento" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}procedimentoType"/>
 *         &lt;element name="provvedimento" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}provvedimentoRicezioneType"/>
 *         &lt;element name="bene" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}beneExtType" maxOccurs="unbounded"/>
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
    "provvedimento",
    "bene"
})
@XmlRootElement(name = "RicezioneRelazione")
public class RicezioneRelazione {

    @XmlElement(required = true)
    protected ProcedimentoType procedimento;
    @XmlElement(required = true)
    protected ProvvedimentoRicezioneType provvedimento;
    @XmlElement(required = true)
    protected List<BeneExtType> bene;

    /**
     * Gets the value of the procedimento property.
     * 
     * @return
     *     possible object is
     *     {@link ProcedimentoType }
     *     
     */
    public ProcedimentoType getProcedimento() {
        return procedimento;
    }

    /**
     * Sets the value of the procedimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProcedimentoType }
     *     
     */
    public void setProcedimento(ProcedimentoType value) {
        this.procedimento = value;
    }

    /**
     * Gets the value of the provvedimento property.
     * 
     * @return
     *     possible object is
     *     {@link ProvvedimentoRicezioneType }
     *     
     */
    public ProvvedimentoRicezioneType getProvvedimento() {
        return provvedimento;
    }

    /**
     * Sets the value of the provvedimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProvvedimentoRicezioneType }
     *     
     */
    public void setProvvedimento(ProvvedimentoRicezioneType value) {
        this.provvedimento = value;
    }

    /**
     * Gets the value of the bene property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bene property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBene().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BeneExtType }
     * 
     * 
     */
    public List<BeneExtType> getBene() {
        if (bene == null) {
            bene = new ArrayList<BeneExtType>();
        }
        return this.bene;
    }

}
