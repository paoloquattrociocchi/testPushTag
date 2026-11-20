//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.12.22 alle 12:20:29 PM CET 
//


package it.giustizia.smp.webservice.client.ricorsocassazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per RelProcBene complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RelProcBene">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiKIdProc" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codiKIdBene" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codiKIdUffOwner" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Bene" type="{http://it/eng/giustizia/sitmp/webservices/ricorsoservice}Bene"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RelProcBene", propOrder = {
    "codiKIdProc",
    "codiKIdBene",
    "codiKIdUffOwner",
    "bene"
})
public class RelProcBene {

    protected long codiKIdProc;
    protected long codiKIdBene;
    protected int codiKIdUffOwner;
    @XmlElement(name = "Bene", required = true)
    protected Bene bene;

    /**
     * Recupera il valore della proprietà codiKIdProc.
     * 
     */
    public long getCodiKIdProc() {
        return codiKIdProc;
    }

    /**
     * Imposta il valore della proprietà codiKIdProc.
     * 
     */
    public void setCodiKIdProc(long value) {
        this.codiKIdProc = value;
    }

    /**
     * Recupera il valore della proprietà codiKIdBene.
     * 
     */
    public long getCodiKIdBene() {
        return codiKIdBene;
    }

    /**
     * Imposta il valore della proprietà codiKIdBene.
     * 
     */
    public void setCodiKIdBene(long value) {
        this.codiKIdBene = value;
    }

    /**
     * Recupera il valore della proprietà codiKIdUffOwner.
     * 
     */
    public int getCodiKIdUffOwner() {
        return codiKIdUffOwner;
    }

    /**
     * Imposta il valore della proprietà codiKIdUffOwner.
     * 
     */
    public void setCodiKIdUffOwner(int value) {
        this.codiKIdUffOwner = value;
    }

    /**
     * Recupera il valore della proprietà bene.
     * 
     * @return
     *     possible object is
     *     {@link Bene }
     *     
     */
    public Bene getBene() {
        return bene;
    }

    /**
     * Imposta il valore della proprietà bene.
     * 
     * @param value
     *     allowed object is
     *     {@link Bene }
     *     
     */
    public void setBene(Bene value) {
        this.bene = value;
    }

}
