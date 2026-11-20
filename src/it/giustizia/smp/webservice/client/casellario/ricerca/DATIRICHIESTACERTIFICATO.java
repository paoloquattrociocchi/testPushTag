//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.10.14 alle 10:17:21 AM CEST 
//


package it.giustizia.smp.webservice.client.casellario.ricerca;

import java.math.BigInteger;
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
 *         &lt;element ref="{http://it/mig/sippi/service/richiestaCertificato/type}DATI_UTENTE"/>
 *         &lt;choice>
 *           &lt;element ref="{http://it/mig/sippi/service/richiestaCertificato/type}ANAGRAFICA"/>
 *           &lt;element name="PROG_ANAGRAFICA_NSC" minOccurs="0">
 *             &lt;simpleType>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                 &lt;totalDigits value="15"/>
 *               &lt;/restriction>
 *             &lt;/simpleType>
 *           &lt;/element>
 *         &lt;/choice>
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
    "datiutente",
    "anagrafica",
    "proganagraficansc"
})
@XmlRootElement(name = "DATI_RICHIESTA_CERTIFICATO")
public class DATIRICHIESTACERTIFICATO {

    @XmlElement(name = "DATI_UTENTE", required = true)
    protected DATIUTENTE datiutente;
    @XmlElement(name = "ANAGRAFICA")
    protected ANAGRAFICA anagrafica;
    @XmlElement(name = "PROG_ANAGRAFICA_NSC")
    protected BigInteger proganagraficansc;

    /**
     * Recupera il valore della proprietà datiutente.
     * 
     * @return
     *     possible object is
     *     {@link DATIUTENTE }
     *     
     */
    public DATIUTENTE getDATIUTENTE() {
        return datiutente;
    }

    /**
     * Imposta il valore della proprietà datiutente.
     * 
     * @param value
     *     allowed object is
     *     {@link DATIUTENTE }
     *     
     */
    public void setDATIUTENTE(DATIUTENTE value) {
        this.datiutente = value;
    }

    /**
     * Recupera il valore della proprietà anagrafica.
     * 
     * @return
     *     possible object is
     *     {@link ANAGRAFICA }
     *     
     */
    public ANAGRAFICA getANAGRAFICA() {
        return anagrafica;
    }

    /**
     * Imposta il valore della proprietà anagrafica.
     * 
     * @param value
     *     allowed object is
     *     {@link ANAGRAFICA }
     *     
     */
    public void setANAGRAFICA(ANAGRAFICA value) {
        this.anagrafica = value;
    }

    /**
     * Recupera il valore della proprietà proganagraficansc.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPROGANAGRAFICANSC() {
        return proganagraficansc;
    }

    /**
     * Imposta il valore della proprietà proganagraficansc.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPROGANAGRAFICANSC(BigInteger value) {
        this.proganagraficansc = value;
    }

}
