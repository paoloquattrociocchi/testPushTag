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
 * <p>Classe Java per Bene complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Bene">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiKIdBene" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codiKIdUffOwner" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="codiFkIdTipoBene" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="codiFkIdCatBene" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="codiFkIdSctgBene" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="descrBene" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiFkIdStato" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="codiFkIdComune" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="descComuneEstero" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiFkIdTipoStra" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="descIndirizzo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descCivico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descCap" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flgCostAzienda" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descNote" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flgGravame" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BeneImmobile" type="{http://it/eng/giustizia/sitmp/webservices/ricorsoservice}BeneImmobile" minOccurs="0"/>
 *         &lt;element name="BeneFinanziario" type="{http://it/eng/giustizia/sitmp/webservices/ricorsoservice}BeneFinanziario" minOccurs="0"/>
 *         &lt;element name="BeneImmat" type="{http://it/eng/giustizia/sitmp/webservices/ricorsoservice}BeneImmat" minOccurs="0"/>
 *         &lt;element name="Azienda" type="{http://it/eng/giustizia/sitmp/webservices/ricorsoservice}Azienda" minOccurs="0"/>
 *         &lt;element name="Contante" type="{http://it/eng/giustizia/sitmp/webservices/ricorsoservice}Contante" minOccurs="0"/>
 *         &lt;element name="Denaro" type="{http://it/eng/giustizia/sitmp/webservices/ricorsoservice}Denaro" minOccurs="0"/>
 *         &lt;element name="Veicolo" type="{http://it/eng/giustizia/sitmp/webservices/ricorsoservice}Veicolo" minOccurs="0"/>
 *         &lt;element name="Collezioni" type="{http://it/eng/giustizia/sitmp/webservices/ricorsoservice}Collezioni" minOccurs="0"/>
 *         &lt;element name="Animali" type="{http://it/eng/giustizia/sitmp/webservices/ricorsoservice}Animali" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Bene", propOrder = {
    "codiKIdBene",
    "codiKIdUffOwner",
    "codiFkIdTipoBene",
    "codiFkIdCatBene",
    "codiFkIdSctgBene",
    "descrBene",
    "codiFkIdStato",
    "codiFkIdComune",
    "descComuneEstero",
    "codiFkIdTipoStra",
    "descIndirizzo",
    "descCivico",
    "descCap",
    "flgCostAzienda",
    "descNote",
    "flgGravame",
    "beneImmobile",
    "beneFinanziario",
    "beneImmat",
    "azienda",
    "contante",
    "denaro",
    "veicolo",
    "collezioni",
    "animali"
})
public class Bene {

    protected long codiKIdBene;
    protected int codiKIdUffOwner;
    protected Integer codiFkIdTipoBene;
    protected Integer codiFkIdCatBene;
    protected Integer codiFkIdSctgBene;
    protected String descrBene;
    protected Integer codiFkIdStato;
    protected Integer codiFkIdComune;
    protected String descComuneEstero;
    protected Integer codiFkIdTipoStra;
    protected String descIndirizzo;
    protected String descCivico;
    protected String descCap;
    protected String flgCostAzienda;
    protected String descNote;
    protected String flgGravame;
    @XmlElement(name = "BeneImmobile")
    protected BeneImmobile beneImmobile;
    @XmlElement(name = "BeneFinanziario")
    protected BeneFinanziario beneFinanziario;
    @XmlElement(name = "BeneImmat")
    protected BeneImmat beneImmat;
    @XmlElement(name = "Azienda")
    protected Azienda azienda;
    @XmlElement(name = "Contante")
    protected Contante contante;
    @XmlElement(name = "Denaro")
    protected Denaro denaro;
    @XmlElement(name = "Veicolo")
    protected Veicolo veicolo;
    @XmlElement(name = "Collezioni")
    protected Collezioni collezioni;
    @XmlElement(name = "Animali")
    protected Animali animali;

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
     * Recupera il valore della proprietà codiFkIdTipoBene.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodiFkIdTipoBene() {
        return codiFkIdTipoBene;
    }

    /**
     * Imposta il valore della proprietà codiFkIdTipoBene.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodiFkIdTipoBene(Integer value) {
        this.codiFkIdTipoBene = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdCatBene.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodiFkIdCatBene() {
        return codiFkIdCatBene;
    }

    /**
     * Imposta il valore della proprietà codiFkIdCatBene.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodiFkIdCatBene(Integer value) {
        this.codiFkIdCatBene = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdSctgBene.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodiFkIdSctgBene() {
        return codiFkIdSctgBene;
    }

    /**
     * Imposta il valore della proprietà codiFkIdSctgBene.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodiFkIdSctgBene(Integer value) {
        this.codiFkIdSctgBene = value;
    }

    /**
     * Recupera il valore della proprietà descrBene.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrBene() {
        return descrBene;
    }

    /**
     * Imposta il valore della proprietà descrBene.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrBene(String value) {
        this.descrBene = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdStato.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodiFkIdStato() {
        return codiFkIdStato;
    }

    /**
     * Imposta il valore della proprietà codiFkIdStato.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodiFkIdStato(Integer value) {
        this.codiFkIdStato = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdComune.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodiFkIdComune() {
        return codiFkIdComune;
    }

    /**
     * Imposta il valore della proprietà codiFkIdComune.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodiFkIdComune(Integer value) {
        this.codiFkIdComune = value;
    }

    /**
     * Recupera il valore della proprietà descComuneEstero.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescComuneEstero() {
        return descComuneEstero;
    }

    /**
     * Imposta il valore della proprietà descComuneEstero.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescComuneEstero(String value) {
        this.descComuneEstero = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdTipoStra.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodiFkIdTipoStra() {
        return codiFkIdTipoStra;
    }

    /**
     * Imposta il valore della proprietà codiFkIdTipoStra.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodiFkIdTipoStra(Integer value) {
        this.codiFkIdTipoStra = value;
    }

    /**
     * Recupera il valore della proprietà descIndirizzo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescIndirizzo() {
        return descIndirizzo;
    }

    /**
     * Imposta il valore della proprietà descIndirizzo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescIndirizzo(String value) {
        this.descIndirizzo = value;
    }

    /**
     * Recupera il valore della proprietà descCivico.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescCivico() {
        return descCivico;
    }

    /**
     * Imposta il valore della proprietà descCivico.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescCivico(String value) {
        this.descCivico = value;
    }

    /**
     * Recupera il valore della proprietà descCap.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescCap() {
        return descCap;
    }

    /**
     * Imposta il valore della proprietà descCap.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescCap(String value) {
        this.descCap = value;
    }

    /**
     * Recupera il valore della proprietà flgCostAzienda.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlgCostAzienda() {
        return flgCostAzienda;
    }

    /**
     * Imposta il valore della proprietà flgCostAzienda.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlgCostAzienda(String value) {
        this.flgCostAzienda = value;
    }

    /**
     * Recupera il valore della proprietà descNote.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescNote() {
        return descNote;
    }

    /**
     * Imposta il valore della proprietà descNote.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescNote(String value) {
        this.descNote = value;
    }

    /**
     * Recupera il valore della proprietà flgGravame.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlgGravame() {
        return flgGravame;
    }

    /**
     * Imposta il valore della proprietà flgGravame.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlgGravame(String value) {
        this.flgGravame = value;
    }

    /**
     * Recupera il valore della proprietà beneImmobile.
     * 
     * @return
     *     possible object is
     *     {@link BeneImmobile }
     *     
     */
    public BeneImmobile getBeneImmobile() {
        return beneImmobile;
    }

    /**
     * Imposta il valore della proprietà beneImmobile.
     * 
     * @param value
     *     allowed object is
     *     {@link BeneImmobile }
     *     
     */
    public void setBeneImmobile(BeneImmobile value) {
        this.beneImmobile = value;
    }

    /**
     * Recupera il valore della proprietà beneFinanziario.
     * 
     * @return
     *     possible object is
     *     {@link BeneFinanziario }
     *     
     */
    public BeneFinanziario getBeneFinanziario() {
        return beneFinanziario;
    }

    /**
     * Imposta il valore della proprietà beneFinanziario.
     * 
     * @param value
     *     allowed object is
     *     {@link BeneFinanziario }
     *     
     */
    public void setBeneFinanziario(BeneFinanziario value) {
        this.beneFinanziario = value;
    }

    /**
     * Recupera il valore della proprietà beneImmat.
     * 
     * @return
     *     possible object is
     *     {@link BeneImmat }
     *     
     */
    public BeneImmat getBeneImmat() {
        return beneImmat;
    }

    /**
     * Imposta il valore della proprietà beneImmat.
     * 
     * @param value
     *     allowed object is
     *     {@link BeneImmat }
     *     
     */
    public void setBeneImmat(BeneImmat value) {
        this.beneImmat = value;
    }

    /**
     * Recupera il valore della proprietà azienda.
     * 
     * @return
     *     possible object is
     *     {@link Azienda }
     *     
     */
    public Azienda getAzienda() {
        return azienda;
    }

    /**
     * Imposta il valore della proprietà azienda.
     * 
     * @param value
     *     allowed object is
     *     {@link Azienda }
     *     
     */
    public void setAzienda(Azienda value) {
        this.azienda = value;
    }

    /**
     * Recupera il valore della proprietà contante.
     * 
     * @return
     *     possible object is
     *     {@link Contante }
     *     
     */
    public Contante getContante() {
        return contante;
    }

    /**
     * Imposta il valore della proprietà contante.
     * 
     * @param value
     *     allowed object is
     *     {@link Contante }
     *     
     */
    public void setContante(Contante value) {
        this.contante = value;
    }

    /**
     * Recupera il valore della proprietà denaro.
     * 
     * @return
     *     possible object is
     *     {@link Denaro }
     *     
     */
    public Denaro getDenaro() {
        return denaro;
    }

    /**
     * Imposta il valore della proprietà denaro.
     * 
     * @param value
     *     allowed object is
     *     {@link Denaro }
     *     
     */
    public void setDenaro(Denaro value) {
        this.denaro = value;
    }

    /**
     * Recupera il valore della proprietà veicolo.
     * 
     * @return
     *     possible object is
     *     {@link Veicolo }
     *     
     */
    public Veicolo getVeicolo() {
        return veicolo;
    }

    /**
     * Imposta il valore della proprietà veicolo.
     * 
     * @param value
     *     allowed object is
     *     {@link Veicolo }
     *     
     */
    public void setVeicolo(Veicolo value) {
        this.veicolo = value;
    }

    /**
     * Recupera il valore della proprietà collezioni.
     * 
     * @return
     *     possible object is
     *     {@link Collezioni }
     *     
     */
    public Collezioni getCollezioni() {
        return collezioni;
    }

    /**
     * Imposta il valore della proprietà collezioni.
     * 
     * @param value
     *     allowed object is
     *     {@link Collezioni }
     *     
     */
    public void setCollezioni(Collezioni value) {
        this.collezioni = value;
    }

    /**
     * Recupera il valore della proprietà animali.
     * 
     * @return
     *     possible object is
     *     {@link Animali }
     *     
     */
    public Animali getAnimali() {
        return animali;
    }

    /**
     * Imposta il valore della proprietà animali.
     * 
     * @param value
     *     allowed object is
     *     {@link Animali }
     *     
     */
    public void setAnimali(Animali value) {
        this.animali = value;
    }

}
