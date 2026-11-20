
package it.giustizia.smp.webservice.client.casellario.esecuzione;

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
 *         &lt;element ref="{http://it/mig/sippi/service/inviaProvvedimentoEsecuzione/type}CHIAVI"/>
 *         &lt;element ref="{http://it/mig/sippi/service/inviaProvvedimentoEsecuzione/type}DATI_OPERAZIONE"/>
 *         &lt;element ref="{http://it/mig/sippi/service/inviaProvvedimentoEsecuzione/type}ESTRATTO_PROVVEDIMENTO" minOccurs="0"/>
 *         &lt;element ref="{http://it/mig/sippi/service/inviaProvvedimentoEsecuzione/type}ArrayEsito"/>
 *         &lt;element ref="{http://it/mig/sippi/service/inviaProvvedimentoEsecuzione/type}ArrayOmonimi" minOccurs="0"/>
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
    "chiavi",
    "datioperazione",
    "estrattoprovvedimento",
    "arrayEsito",
    "arrayOmonimi"
})
@XmlRootElement(name = "DATI_RISPOSTA_TRASFERIMENTO")
public class DATIRISPOSTATRASFERIMENTO {

    @XmlElement(name = "CHIAVI", required = true)
    protected CHIAVI chiavi;
    @XmlElement(name = "DATI_OPERAZIONE", required = true)
    protected DATIOPERAZIONE datioperazione;
    @XmlElement(name = "ESTRATTO_PROVVEDIMENTO")
    protected byte[] estrattoprovvedimento;
    @XmlElement(name = "ArrayEsito", required = true)
    protected ArrayEsito arrayEsito;
    @XmlElement(name = "ArrayOmonimi")
    protected ArrayOmonimi arrayOmonimi;

    /**
     * Gets the value of the chiavi property.
     * 
     * @return
     *     possible object is
     *     {@link CHIAVI }
     *     
     */
    public CHIAVI getCHIAVI() {
        return chiavi;
    }

    /**
     * Sets the value of the chiavi property.
     * 
     * @param value
     *     allowed object is
     *     {@link CHIAVI }
     *     
     */
    public void setCHIAVI(CHIAVI value) {
        this.chiavi = value;
    }

    /**
     * Gets the value of the datioperazione property.
     * 
     * @return
     *     possible object is
     *     {@link DATIOPERAZIONE }
     *     
     */
    public DATIOPERAZIONE getDATIOPERAZIONE() {
        return datioperazione;
    }

    /**
     * Sets the value of the datioperazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link DATIOPERAZIONE }
     *     
     */
    public void setDATIOPERAZIONE(DATIOPERAZIONE value) {
        this.datioperazione = value;
    }

    /**
     * Gets the value of the estrattoprovvedimento property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getESTRATTOPROVVEDIMENTO() {
        return estrattoprovvedimento;
    }

    /**
     * Sets the value of the estrattoprovvedimento property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setESTRATTOPROVVEDIMENTO(byte[] value) {
        this.estrattoprovvedimento = ((byte[]) value);
    }

    /**
     * Gets the value of the arrayEsito property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayEsito }
     *     
     */
    public ArrayEsito getArrayEsito() {
        return arrayEsito;
    }

    /**
     * Sets the value of the arrayEsito property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayEsito }
     *     
     */
    public void setArrayEsito(ArrayEsito value) {
        this.arrayEsito = value;
    }

    /**
     * Gets the value of the arrayOmonimi property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOmonimi }
     *     
     */
    public ArrayOmonimi getArrayOmonimi() {
        return arrayOmonimi;
    }

    /**
     * Sets the value of the arrayOmonimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOmonimi }
     *     
     */
    public void setArrayOmonimi(ArrayOmonimi value) {
        this.arrayOmonimi = value;
    }

}
