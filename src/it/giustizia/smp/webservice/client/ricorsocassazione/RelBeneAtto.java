//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.12.22 alle 12:20:29 PM CET 
//


package it.giustizia.smp.webservice.client.ricorsocassazione;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per RelBeneAtto complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RelBeneAtto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiKIdProc" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codiKIdBene" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codiKIdUffOwner" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="codiKIdAtto" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codiKIdTipoInt" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="codiKIdSoggTit" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="numeQuota" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="numeFrazioneNum" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="numeFrazioneDen" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="codiFkIdNormativa" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="codiFkIdTipoMis" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="codiFkIdEsito1" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="codiFkIdEsito2" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="codiFkIdEsitoCsz" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="idEsitoSent" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="RelAttoMisPatr" type="{http://it/eng/giustizia/sitmp/webservices/ricorsoservice}RelAttoMisPatr" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RelBeneAtto", propOrder = {
    "codiKIdProc",
    "codiKIdBene",
    "codiKIdUffOwner",
    "codiKIdAtto",
    "codiKIdTipoInt",
    "codiKIdSoggTit",
    "numeQuota",
    "numeFrazioneNum",
    "numeFrazioneDen",
    "codiFkIdNormativa",
    "codiFkIdTipoMis",
    "codiFkIdEsito1",
    "codiFkIdEsito2",
    "codiFkIdEsitoCsz",
    "idEsitoSent",
    "relAttoMisPatr"
})
public class RelBeneAtto {

    protected long codiKIdProc;
    protected long codiKIdBene;
    protected int codiKIdUffOwner;
    protected long codiKIdAtto;
    protected int codiKIdTipoInt;
    protected long codiKIdSoggTit;
    protected BigDecimal numeQuota;
    protected Short numeFrazioneNum;
    protected Short numeFrazioneDen;
    protected Integer codiFkIdNormativa;
    protected Integer codiFkIdTipoMis;
    protected Integer codiFkIdEsito1;
    protected Integer codiFkIdEsito2;
    protected Integer codiFkIdEsitoCsz;
    protected long idEsitoSent;
    @XmlElement(name = "RelAttoMisPatr", required = true)
    protected List<RelAttoMisPatr> relAttoMisPatr;

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
     * Recupera il valore della proprietà numeQuota.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNumeQuota() {
        return numeQuota;
    }

    /**
     * Imposta il valore della proprietà numeQuota.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNumeQuota(BigDecimal value) {
        this.numeQuota = value;
    }

    /**
     * Recupera il valore della proprietà numeFrazioneNum.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getNumeFrazioneNum() {
        return numeFrazioneNum;
    }

    /**
     * Imposta il valore della proprietà numeFrazioneNum.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setNumeFrazioneNum(Short value) {
        this.numeFrazioneNum = value;
    }

    /**
     * Recupera il valore della proprietà numeFrazioneDen.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getNumeFrazioneDen() {
        return numeFrazioneDen;
    }

    /**
     * Imposta il valore della proprietà numeFrazioneDen.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setNumeFrazioneDen(Short value) {
        this.numeFrazioneDen = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdNormativa.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodiFkIdNormativa() {
        return codiFkIdNormativa;
    }

    /**
     * Imposta il valore della proprietà codiFkIdNormativa.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodiFkIdNormativa(Integer value) {
        this.codiFkIdNormativa = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdTipoMis.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodiFkIdTipoMis() {
        return codiFkIdTipoMis;
    }

    /**
     * Imposta il valore della proprietà codiFkIdTipoMis.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodiFkIdTipoMis(Integer value) {
        this.codiFkIdTipoMis = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdEsito1.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodiFkIdEsito1() {
        return codiFkIdEsito1;
    }

    /**
     * Imposta il valore della proprietà codiFkIdEsito1.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodiFkIdEsito1(Integer value) {
        this.codiFkIdEsito1 = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdEsito2.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodiFkIdEsito2() {
        return codiFkIdEsito2;
    }

    /**
     * Imposta il valore della proprietà codiFkIdEsito2.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodiFkIdEsito2(Integer value) {
        this.codiFkIdEsito2 = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdEsitoCsz.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodiFkIdEsitoCsz() {
        return codiFkIdEsitoCsz;
    }

    /**
     * Imposta il valore della proprietà codiFkIdEsitoCsz.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodiFkIdEsitoCsz(Integer value) {
        this.codiFkIdEsitoCsz = value;
    }

    /**
     * Recupera il valore della proprietà idEsitoSent.
     * 
     */
    public long getIdEsitoSent() {
        return idEsitoSent;
    }

    /**
     * Imposta il valore della proprietà idEsitoSent.
     * 
     */
    public void setIdEsitoSent(long value) {
        this.idEsitoSent = value;
    }

    /**
     * Gets the value of the relAttoMisPatr property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the relAttoMisPatr property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRelAttoMisPatr().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RelAttoMisPatr }
     * 
     * 
     */
    public List<RelAttoMisPatr> getRelAttoMisPatr() {
        if (relAttoMisPatr == null) {
            relAttoMisPatr = new ArrayList<RelAttoMisPatr>();
        }
        return this.relAttoMisPatr;
    }

}
