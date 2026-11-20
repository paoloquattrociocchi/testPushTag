//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.12.22 alle 12:20:29 PM CET 
//


package it.giustizia.smp.webservice.client.ricorsocassazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per RelAttoMisPatr complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RelAttoMisPatr">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiKIdProc" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codiKIdAtto" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codiKIdUffOwner" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="codiKIdBene" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codiKIdTipoInt" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="codiKIdSoggTit" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codiKIdSoggInt" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RelAttoMisPatr", propOrder = {
    "codiKIdProc",
    "codiKIdAtto",
    "codiKIdUffOwner",
    "codiKIdBene",
    "codiKIdTipoInt",
    "codiKIdSoggTit",
    "codiKIdSoggInt"
})
public class RelAttoMisPatr {

    protected long codiKIdProc;
    protected long codiKIdAtto;
    protected int codiKIdUffOwner;
    protected long codiKIdBene;
    protected int codiKIdTipoInt;
    protected long codiKIdSoggTit;
    protected long codiKIdSoggInt;

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
     * Recupera il valore della proprietà codiKIdTipoInt.
     * 
     */
    public int getCodiKIdTipoInt() {
        return codiKIdTipoInt;
    }

    /**
     * Imposta il valore della proprietà codiKIdTipoInt.
     * 
     */
    public void setCodiKIdTipoInt(int value) {
        this.codiKIdTipoInt = value;
    }

    /**
     * Recupera il valore della proprietà codiKIdSoggTit.
     * 
     */
    public long getCodiKIdSoggTit() {
        return codiKIdSoggTit;
    }

    /**
     * Imposta il valore della proprietà codiKIdSoggTit.
     * 
     */
    public void setCodiKIdSoggTit(long value) {
        this.codiKIdSoggTit = value;
    }

    /**
     * Recupera il valore della proprietà codiKIdSoggInt.
     * 
     */
    public long getCodiKIdSoggInt() {
        return codiKIdSoggInt;
    }

    /**
     * Imposta il valore della proprietà codiKIdSoggInt.
     * 
     */
    public void setCodiKIdSoggInt(long value) {
        this.codiKIdSoggInt = value;
    }

}
