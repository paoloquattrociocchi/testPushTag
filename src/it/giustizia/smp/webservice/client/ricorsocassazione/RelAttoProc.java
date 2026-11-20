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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per RelAttoProc complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RelAttoProc">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiKIdProc" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codiKIdAtto" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codiKIdUffOwner" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Atto" type="{http://it/eng/giustizia/sitmp/webservices/ricorsoservice}Atto" maxOccurs="2" minOccurs="2"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RelAttoProc", propOrder = {
    "codiKIdProc",
    "codiKIdAtto",
    "codiKIdUffOwner",
    "atto"
})
public class RelAttoProc {

    protected long codiKIdProc;
    protected long codiKIdAtto;
    protected int codiKIdUffOwner;
    @XmlElement(name = "Atto", required = true)
    protected List<Atto> atto;

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
     * Recupera il valore della proprietà codiKIdAtto.
     * 
     */
    public long getCodiKIdAtto() {
        return codiKIdAtto;
    }

    /**
     * Imposta il valore della proprietà codiKIdAtto.
     * 
     */
    public void setCodiKIdAtto(long value) {
        this.codiKIdAtto = value;
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
     * Gets the value of the atto property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the atto property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAtto().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Atto }
     * 
     * 
     */
    public List<Atto> getAtto() {
        if (atto == null) {
            atto = new ArrayList<Atto>();
        }
        return this.atto;
    }

}
