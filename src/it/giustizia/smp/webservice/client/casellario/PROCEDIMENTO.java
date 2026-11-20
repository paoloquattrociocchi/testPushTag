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
 *         &lt;element ref="{http://it/mig/sippi/service/inviaProvvedimento/type}DATI_PROVVEDIMENTO"/>
 *         &lt;element ref="{http://it/mig/sippi/service/inviaProvvedimento/type}ArrayPrimoGrado" minOccurs="0"/>
 *         &lt;element ref="{http://it/mig/sippi/service/inviaProvvedimento/type}ArraySecondoGrado" minOccurs="0"/>
 *         &lt;element ref="{http://it/mig/sippi/service/inviaProvvedimento/type}ArrayCassazione" minOccurs="0"/>
 *         &lt;element ref="{http://it/mig/sippi/service/inviaProvvedimento/type}ArrayMisurePrevenzione"/>
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
    "datiprovvedimento",
    "arrayPrimoGrado",
    "arraySecondoGrado",
    "arrayCassazione",
    "arrayMisurePrevenzione"
})
@XmlRootElement(name = "PROCEDIMENTO")
public class PROCEDIMENTO {

    @XmlElement(name = "DATI_PROVVEDIMENTO", required = true)
    protected DATIPROVVEDIMENTO datiprovvedimento;
    @XmlElement(name = "ArrayPrimoGrado")
    protected ArrayPrimoGrado arrayPrimoGrado;
    @XmlElement(name = "ArraySecondoGrado")
    protected ArraySecondoGrado arraySecondoGrado;
    @XmlElement(name = "ArrayCassazione")
    protected ArrayCassazione arrayCassazione;
    @XmlElement(name = "ArrayMisurePrevenzione", required = true)
    protected ArrayMisurePrevenzione arrayMisurePrevenzione;

    /**
     * Recupera il valore della proprietà datiprovvedimento.
     * 
     * @return
     *     possible object is
     *     {@link DATIPROVVEDIMENTO }
     *     
     */
    public DATIPROVVEDIMENTO getDATIPROVVEDIMENTO() {
        return datiprovvedimento;
    }

    /**
     * Imposta il valore della proprietà datiprovvedimento.
     * 
     * @param value
     *     allowed object is
     *     {@link DATIPROVVEDIMENTO }
     *     
     */
    public void setDATIPROVVEDIMENTO(DATIPROVVEDIMENTO value) {
        this.datiprovvedimento = value;
    }

    /**
     * Recupera il valore della proprietà arrayPrimoGrado.
     * 
     * @return
     *     possible object is
     *     {@link ArrayPrimoGrado }
     *     
     */
    public ArrayPrimoGrado getArrayPrimoGrado() {
        return arrayPrimoGrado;
    }

    /**
     * Imposta il valore della proprietà arrayPrimoGrado.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayPrimoGrado }
     *     
     */
    public void setArrayPrimoGrado(ArrayPrimoGrado value) {
        this.arrayPrimoGrado = value;
    }

    /**
     * Recupera il valore della proprietà arraySecondoGrado.
     * 
     * @return
     *     possible object is
     *     {@link ArraySecondoGrado }
     *     
     */
    public ArraySecondoGrado getArraySecondoGrado() {
        return arraySecondoGrado;
    }

    /**
     * Imposta il valore della proprietà arraySecondoGrado.
     * 
     * @param value
     *     allowed object is
     *     {@link ArraySecondoGrado }
     *     
     */
    public void setArraySecondoGrado(ArraySecondoGrado value) {
        this.arraySecondoGrado = value;
    }

    /**
     * Recupera il valore della proprietà arrayCassazione.
     * 
     * @return
     *     possible object is
     *     {@link ArrayCassazione }
     *     
     */
    public ArrayCassazione getArrayCassazione() {
        return arrayCassazione;
    }

    /**
     * Imposta il valore della proprietà arrayCassazione.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayCassazione }
     *     
     */
    public void setArrayCassazione(ArrayCassazione value) {
        this.arrayCassazione = value;
    }

    /**
     * Recupera il valore della proprietà arrayMisurePrevenzione.
     * 
     * @return
     *     possible object is
     *     {@link ArrayMisurePrevenzione }
     *     
     */
    public ArrayMisurePrevenzione getArrayMisurePrevenzione() {
        return arrayMisurePrevenzione;
    }

    /**
     * Imposta il valore della proprietà arrayMisurePrevenzione.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayMisurePrevenzione }
     *     
     */
    public void setArrayMisurePrevenzione(ArrayMisurePrevenzione value) {
        this.arrayMisurePrevenzione = value;
    }

}
