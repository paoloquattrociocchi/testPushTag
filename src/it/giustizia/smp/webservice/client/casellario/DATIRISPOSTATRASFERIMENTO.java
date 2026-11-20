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
 *         &lt;element ref="{http://it/mig/sippi/service/inviaProvvedimento/type}CHIAVI"/>
 *         &lt;element ref="{http://it/mig/sippi/service/inviaProvvedimento/type}DATI_OPERAZIONE"/>
 *         &lt;element ref="{http://it/mig/sippi/service/inviaProvvedimento/type}ArrayOmonimi" minOccurs="0"/>
 *         &lt;element ref="{http://it/mig/sippi/service/inviaProvvedimento/type}ESTRATTO_PROVVEDIMENTO" minOccurs="0"/>
 *         &lt;element ref="{http://it/mig/sippi/service/inviaProvvedimento/type}ArrayEsito"/>
 *         &lt;element ref="{http://it/mig/sippi/service/inviaProvvedimento/type}MODULO_ELETTORALE"/>
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
    "arrayOmonimi",
    "estrattoprovvedimento",
    "arrayEsito",
    "moduloelettorale"
})
@XmlRootElement(name = "DATI_RISPOSTA_TRASFERIMENTO")
public class DATIRISPOSTATRASFERIMENTO {

    @XmlElement(name = "CHIAVI", required = true)
    protected CHIAVI chiavi;
    @XmlElement(name = "DATI_OPERAZIONE", required = true)
    protected DATIOPERAZIONE datioperazione;
    @XmlElement(name = "ArrayOmonimi")
    protected ArrayOmonimi arrayOmonimi;
    @XmlElement(name = "ESTRATTO_PROVVEDIMENTO")
    protected byte[] estrattoprovvedimento;
    @XmlElement(name = "ArrayEsito", required = true)
    protected ArrayEsito arrayEsito;
    @XmlElement(name = "MODULO_ELETTORALE", required = true)
    protected byte[] moduloelettorale;

    /**
     * Recupera il valore della proprietà chiavi.
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
     * Imposta il valore della proprietà chiavi.
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
     * Recupera il valore della proprietà datioperazione.
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
     * Imposta il valore della proprietà datioperazione.
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
     * Recupera il valore della proprietà arrayOmonimi.
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
     * Imposta il valore della proprietà arrayOmonimi.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOmonimi }
     *     
     */
    public void setArrayOmonimi(ArrayOmonimi value) {
        this.arrayOmonimi = value;
    }

    /**
     * Recupera il valore della proprietà estrattoprovvedimento.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getESTRATTOPROVVEDIMENTO() {
        return estrattoprovvedimento;
    }

    /**
     * Imposta il valore della proprietà estrattoprovvedimento.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setESTRATTOPROVVEDIMENTO(byte[] value) {
        this.estrattoprovvedimento = value;
    }

    /**
     * Recupera il valore della proprietà arrayEsito.
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
     * Imposta il valore della proprietà arrayEsito.
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
     * Recupera il valore della proprietà moduloelettorale.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getMODULOELETTORALE() {
        return moduloelettorale;
    }

    /**
     * Imposta il valore della proprietà moduloelettorale.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setMODULOELETTORALE(byte[] value) {
        this.moduloelettorale = value;
    }

}
