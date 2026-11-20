
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
 *         &lt;element name="provvedimento" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}provvedimentoTrasmissioneType"/>
 *         &lt;element name="bene" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}beneType" maxOccurs="unbounded"/>
 *         &lt;element name="soggetto" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}soggettoType" maxOccurs="unbounded"/>
 *         &lt;element name="misura" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}misuraType" maxOccurs="unbounded"/>
 *         &lt;element name="amministrazione" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}amministrazioneType" maxOccurs="unbounded" minOccurs="0"/>
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
    "bene",
    "soggetto",
    "misura",
    "amministrazione"
})
@XmlRootElement(name = "TrasmissioneRelazione")
public class TrasmissioneRelazione {

    @XmlElement(required = true)
    protected ProcedimentoType procedimento;
    @XmlElement(required = true)
    protected ProvvedimentoTrasmissioneType provvedimento;
    @XmlElement(required = true)
    protected List<BeneType> bene;
    @XmlElement(required = true)
    protected List<SoggettoType> soggetto;
    @XmlElement(required = true)
    protected List<MisuraType> misura;
    protected List<AmministrazioneType> amministrazione;

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
     *     {@link ProvvedimentoTrasmissioneType }
     *     
     */
    public ProvvedimentoTrasmissioneType getProvvedimento() {
        return provvedimento;
    }

    /**
     * Sets the value of the provvedimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProvvedimentoTrasmissioneType }
     *     
     */
    public void setProvvedimento(ProvvedimentoTrasmissioneType value) {
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
     * {@link BeneType }
     * 
     * 
     */
    public List<BeneType> getBene() {
        if (bene == null) {
            bene = new ArrayList<BeneType>();
        }
        return this.bene;
    }

    /**
     * Gets the value of the soggetto property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the soggetto property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSoggetto().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SoggettoType }
     * 
     * 
     */
    public List<SoggettoType> getSoggetto() {
        if (soggetto == null) {
            soggetto = new ArrayList<SoggettoType>();
        }
        return this.soggetto;
    }

    /**
     * Gets the value of the misura property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the misura property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMisura().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MisuraType }
     * 
     * 
     */
    public List<MisuraType> getMisura() {
        if (misura == null) {
            misura = new ArrayList<MisuraType>();
        }
        return this.misura;
    }

    /**
     * Gets the value of the amministrazione property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the amministrazione property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAmministrazione().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AmministrazioneType }
     * 
     * 
     */
    public List<AmministrazioneType> getAmministrazione() {
        if (amministrazione == null) {
            amministrazione = new ArrayList<AmministrazioneType>();
        }
        return this.amministrazione;
    }

}
