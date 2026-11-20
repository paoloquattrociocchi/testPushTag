
package it.giustizia.gestorelocale.accettazione.esiti;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for riepilogoEsiti complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="riepilogoEsiti">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="contentSummary" type="{http://www.giustizia.it/gestorelocale/accettazione/esiti}riepilogoContenuto" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="catId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="contentHash" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="destination" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="docType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="engineName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="extraInfo" type="{http://www.giustizia.it/gestorelocale/accettazione/esiti}extraInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="idMsgGC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idMsgPdA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mainCatId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="notes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numPro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="receiveTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="receiver" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="register" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sender" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="signer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="urgente" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "riepilogoEsiti", propOrder = {
    "contentSummary",
    "catId",
    "contentHash",
    "destination",
    "docType",
    "engineName",
    "extraInfo",
    "idMsgGC",
    "idMsgPdA",
    "mainCatId",
    "notes",
    "numPro",
    "receiveTime",
    "receiver",
    "register",
    "sender",
    "signer",
    "status",
    "urgente"
})
public class RiepilogoEsiti {

    protected List<RiepilogoContenuto> contentSummary;
    protected String catId;
    protected String contentHash;
    protected String destination;
    protected String docType;
    protected String engineName;
    protected List<ExtraInfo> extraInfo;
    protected String idMsgGC;
    protected String idMsgPdA;
    protected String mainCatId;
    protected String notes;
    protected String numPro;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar receiveTime;
    protected String receiver;
    protected String register;
    protected String sender;
    protected String signer;
    protected String status;
    protected Boolean urgente;

    /**
     * Gets the value of the contentSummary property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contentSummary property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContentSummary().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RiepilogoContenuto }
     * 
     * 
     */
    public List<RiepilogoContenuto> getContentSummary() {
        if (contentSummary == null) {
            contentSummary = new ArrayList<RiepilogoContenuto>();
        }
        return this.contentSummary;
    }

    /**
     * Gets the value of the catId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCatId() {
        return catId;
    }

    /**
     * Sets the value of the catId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCatId(String value) {
        this.catId = value;
    }

    /**
     * Gets the value of the contentHash property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContentHash() {
        return contentHash;
    }

    /**
     * Sets the value of the contentHash property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContentHash(String value) {
        this.contentHash = value;
    }

    /**
     * Gets the value of the destination property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Sets the value of the destination property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestination(String value) {
        this.destination = value;
    }

    /**
     * Gets the value of the docType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocType() {
        return docType;
    }

    /**
     * Sets the value of the docType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocType(String value) {
        this.docType = value;
    }

    /**
     * Gets the value of the engineName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEngineName() {
        return engineName;
    }

    /**
     * Sets the value of the engineName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEngineName(String value) {
        this.engineName = value;
    }

    /**
     * Gets the value of the extraInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the extraInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExtraInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExtraInfo }
     * 
     * 
     */
    public List<ExtraInfo> getExtraInfo() {
        if (extraInfo == null) {
            extraInfo = new ArrayList<ExtraInfo>();
        }
        return this.extraInfo;
    }

    /**
     * Gets the value of the idMsgGC property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdMsgGC() {
        return idMsgGC;
    }

    /**
     * Sets the value of the idMsgGC property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdMsgGC(String value) {
        this.idMsgGC = value;
    }

    /**
     * Gets the value of the idMsgPdA property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdMsgPdA() {
        return idMsgPdA;
    }

    /**
     * Sets the value of the idMsgPdA property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdMsgPdA(String value) {
        this.idMsgPdA = value;
    }

    /**
     * Gets the value of the mainCatId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMainCatId() {
        return mainCatId;
    }

    /**
     * Sets the value of the mainCatId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMainCatId(String value) {
        this.mainCatId = value;
    }

    /**
     * Gets the value of the notes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the value of the notes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotes(String value) {
        this.notes = value;
    }

    /**
     * Gets the value of the numPro property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumPro() {
        return numPro;
    }

    /**
     * Sets the value of the numPro property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumPro(String value) {
        this.numPro = value;
    }

    /**
     * Gets the value of the receiveTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getReceiveTime() {
        return receiveTime;
    }

    /**
     * Sets the value of the receiveTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setReceiveTime(XMLGregorianCalendar value) {
        this.receiveTime = value;
    }

    /**
     * Gets the value of the receiver property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * Sets the value of the receiver property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceiver(String value) {
        this.receiver = value;
    }

    /**
     * Gets the value of the register property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegister() {
        return register;
    }

    /**
     * Sets the value of the register property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegister(String value) {
        this.register = value;
    }

    /**
     * Gets the value of the sender property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSender() {
        return sender;
    }

    /**
     * Sets the value of the sender property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSender(String value) {
        this.sender = value;
    }

    /**
     * Gets the value of the signer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSigner() {
        return signer;
    }

    /**
     * Sets the value of the signer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSigner(String value) {
        this.signer = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the urgente property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isUrgente() {
        return urgente;
    }

    /**
     * Sets the value of the urgente property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUrgente(Boolean value) {
        this.urgente = value;
    }

}
