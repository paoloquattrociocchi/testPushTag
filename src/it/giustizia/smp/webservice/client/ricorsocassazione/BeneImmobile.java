//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.12.22 alle 12:20:29 PM CET 
//


package it.giustizia.smp.webservice.client.ricorsocassazione;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per BeneImmobile complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="BeneImmobile">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiKIdBeneImm" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codiKIdUffOwner" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="descScala" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descInterno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descPiano" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiFkIdTipoCat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiFkIdComuneCat" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="descSezione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descFoglio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descParticella1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descParticella2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descSubParticella1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descSubParticella2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descSchedaAccatast" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataDomandaAccatast" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="descZona" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="impoRenditaCat" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="impoRenditaDom" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="flgPresConcEdil" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flgAppFabConf" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiFkIdCategoriaCat" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="descCatasto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descCatastoP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiFkIdComuneCatP" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="descSezioneP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiFkIdTipoCatP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descFoglioP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descParticella1P" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descParticella2P" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descSubalterno1P" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descSubalterno2P" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiFkIdTipoDenuncia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeDenuncia1" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numeDenuncia2" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="numeAnnoDenuncia" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="codiFkIdTipoDenunciaP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeDenuncia1P" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numeDenuncia2P" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="numeAnnoDenunciaP" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="numeGraffati" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="codiFkIdFondo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeSupEttari" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numeSupAre" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numeSupCentiare" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numeSupMqInt" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="numeSupMqDec" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="numeVaniInt" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="numeVaniDec" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="numeMcInt" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="numeMcDec" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="descEdificio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descLotto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BeneImmobile", propOrder = {
    "codiKIdBeneImm",
    "codiKIdUffOwner",
    "descScala",
    "descInterno",
    "descPiano",
    "codiFkIdTipoCat",
    "codiFkIdComuneCat",
    "descSezione",
    "descFoglio",
    "descParticella1",
    "descParticella2",
    "descSubParticella1",
    "descSubParticella2",
    "descSchedaAccatast",
    "dataDomandaAccatast",
    "descZona",
    "impoRenditaCat",
    "impoRenditaDom",
    "flgPresConcEdil",
    "flgAppFabConf",
    "codiFkIdCategoriaCat",
    "descCatasto",
    "descCatastoP",
    "codiFkIdComuneCatP",
    "descSezioneP",
    "codiFkIdTipoCatP",
    "descFoglioP",
    "descParticella1P",
    "descParticella2P",
    "descSubalterno1P",
    "descSubalterno2P",
    "codiFkIdTipoDenuncia",
    "numeDenuncia1",
    "numeDenuncia2",
    "numeAnnoDenuncia",
    "codiFkIdTipoDenunciaP",
    "numeDenuncia1P",
    "numeDenuncia2P",
    "numeAnnoDenunciaP",
    "numeGraffati",
    "codiFkIdFondo",
    "numeSupEttari",
    "numeSupAre",
    "numeSupCentiare",
    "numeSupMqInt",
    "numeSupMqDec",
    "numeVaniInt",
    "numeVaniDec",
    "numeMcInt",
    "numeMcDec",
    "descEdificio",
    "descLotto"
})
public class BeneImmobile {

    protected long codiKIdBeneImm;
    protected int codiKIdUffOwner;
    protected String descScala;
    protected String descInterno;
    protected String descPiano;
    protected String codiFkIdTipoCat;
    protected Integer codiFkIdComuneCat;
    protected String descSezione;
    protected String descFoglio;
    protected String descParticella1;
    protected String descParticella2;
    protected String descSubParticella1;
    protected String descSubParticella2;
    protected String descSchedaAccatast;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataDomandaAccatast;
    protected String descZona;
    protected BigDecimal impoRenditaCat;
    protected BigDecimal impoRenditaDom;
    protected String flgPresConcEdil;
    protected String flgAppFabConf;
    protected Integer codiFkIdCategoriaCat;
    protected String descCatasto;
    protected String descCatastoP;
    protected Integer codiFkIdComuneCatP;
    protected String descSezioneP;
    protected String codiFkIdTipoCatP;
    protected String descFoglioP;
    protected String descParticella1P;
    protected String descParticella2P;
    protected String descSubalterno1P;
    protected String descSubalterno2P;
    protected String codiFkIdTipoDenuncia;
    protected Integer numeDenuncia1;
    protected Short numeDenuncia2;
    protected Short numeAnnoDenuncia;
    protected String codiFkIdTipoDenunciaP;
    protected Integer numeDenuncia1P;
    protected Short numeDenuncia2P;
    protected Short numeAnnoDenunciaP;
    protected Long numeGraffati;
    protected String codiFkIdFondo;
    protected Integer numeSupEttari;
    protected Integer numeSupAre;
    protected Integer numeSupCentiare;
    protected Short numeSupMqInt;
    protected Short numeSupMqDec;
    protected Short numeVaniInt;
    protected Short numeVaniDec;
    protected Short numeMcInt;
    protected Short numeMcDec;
    protected String descEdificio;
    protected String descLotto;

    /**
     * Recupera il valore della proprietà codiKIdBeneImm.
     * 
     */
    public long getCodiKIdBeneImm() {
        return codiKIdBeneImm;
    }

    /**
     * Imposta il valore della proprietà codiKIdBeneImm.
     * 
     */
    public void setCodiKIdBeneImm(long value) {
        this.codiKIdBeneImm = value;
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
     * Recupera il valore della proprietà descScala.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescScala() {
        return descScala;
    }

    /**
     * Imposta il valore della proprietà descScala.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescScala(String value) {
        this.descScala = value;
    }

    /**
     * Recupera il valore della proprietà descInterno.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescInterno() {
        return descInterno;
    }

    /**
     * Imposta il valore della proprietà descInterno.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescInterno(String value) {
        this.descInterno = value;
    }

    /**
     * Recupera il valore della proprietà descPiano.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescPiano() {
        return descPiano;
    }

    /**
     * Imposta il valore della proprietà descPiano.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescPiano(String value) {
        this.descPiano = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdTipoCat.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiFkIdTipoCat() {
        return codiFkIdTipoCat;
    }

    /**
     * Imposta il valore della proprietà codiFkIdTipoCat.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiFkIdTipoCat(String value) {
        this.codiFkIdTipoCat = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdComuneCat.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodiFkIdComuneCat() {
        return codiFkIdComuneCat;
    }

    /**
     * Imposta il valore della proprietà codiFkIdComuneCat.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodiFkIdComuneCat(Integer value) {
        this.codiFkIdComuneCat = value;
    }

    /**
     * Recupera il valore della proprietà descSezione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescSezione() {
        return descSezione;
    }

    /**
     * Imposta il valore della proprietà descSezione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescSezione(String value) {
        this.descSezione = value;
    }

    /**
     * Recupera il valore della proprietà descFoglio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescFoglio() {
        return descFoglio;
    }

    /**
     * Imposta il valore della proprietà descFoglio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescFoglio(String value) {
        this.descFoglio = value;
    }

    /**
     * Recupera il valore della proprietà descParticella1.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescParticella1() {
        return descParticella1;
    }

    /**
     * Imposta il valore della proprietà descParticella1.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescParticella1(String value) {
        this.descParticella1 = value;
    }

    /**
     * Recupera il valore della proprietà descParticella2.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescParticella2() {
        return descParticella2;
    }

    /**
     * Imposta il valore della proprietà descParticella2.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescParticella2(String value) {
        this.descParticella2 = value;
    }

    /**
     * Recupera il valore della proprietà descSubParticella1.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescSubParticella1() {
        return descSubParticella1;
    }

    /**
     * Imposta il valore della proprietà descSubParticella1.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescSubParticella1(String value) {
        this.descSubParticella1 = value;
    }

    /**
     * Recupera il valore della proprietà descSubParticella2.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescSubParticella2() {
        return descSubParticella2;
    }

    /**
     * Imposta il valore della proprietà descSubParticella2.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescSubParticella2(String value) {
        this.descSubParticella2 = value;
    }

    /**
     * Recupera il valore della proprietà descSchedaAccatast.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescSchedaAccatast() {
        return descSchedaAccatast;
    }

    /**
     * Imposta il valore della proprietà descSchedaAccatast.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescSchedaAccatast(String value) {
        this.descSchedaAccatast = value;
    }

    /**
     * Recupera il valore della proprietà dataDomandaAccatast.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataDomandaAccatast() {
        return dataDomandaAccatast;
    }

    /**
     * Imposta il valore della proprietà dataDomandaAccatast.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataDomandaAccatast(XMLGregorianCalendar value) {
        this.dataDomandaAccatast = value;
    }

    /**
     * Recupera il valore della proprietà descZona.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescZona() {
        return descZona;
    }

    /**
     * Imposta il valore della proprietà descZona.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescZona(String value) {
        this.descZona = value;
    }

    /**
     * Recupera il valore della proprietà impoRenditaCat.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImpoRenditaCat() {
        return impoRenditaCat;
    }

    /**
     * Imposta il valore della proprietà impoRenditaCat.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImpoRenditaCat(BigDecimal value) {
        this.impoRenditaCat = value;
    }

    /**
     * Recupera il valore della proprietà impoRenditaDom.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImpoRenditaDom() {
        return impoRenditaDom;
    }

    /**
     * Imposta il valore della proprietà impoRenditaDom.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImpoRenditaDom(BigDecimal value) {
        this.impoRenditaDom = value;
    }

    /**
     * Recupera il valore della proprietà flgPresConcEdil.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlgPresConcEdil() {
        return flgPresConcEdil;
    }

    /**
     * Imposta il valore della proprietà flgPresConcEdil.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlgPresConcEdil(String value) {
        this.flgPresConcEdil = value;
    }

    /**
     * Recupera il valore della proprietà flgAppFabConf.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlgAppFabConf() {
        return flgAppFabConf;
    }

    /**
     * Imposta il valore della proprietà flgAppFabConf.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlgAppFabConf(String value) {
        this.flgAppFabConf = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdCategoriaCat.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodiFkIdCategoriaCat() {
        return codiFkIdCategoriaCat;
    }

    /**
     * Imposta il valore della proprietà codiFkIdCategoriaCat.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodiFkIdCategoriaCat(Integer value) {
        this.codiFkIdCategoriaCat = value;
    }

    /**
     * Recupera il valore della proprietà descCatasto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescCatasto() {
        return descCatasto;
    }

    /**
     * Imposta il valore della proprietà descCatasto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescCatasto(String value) {
        this.descCatasto = value;
    }

    /**
     * Recupera il valore della proprietà descCatastoP.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescCatastoP() {
        return descCatastoP;
    }

    /**
     * Imposta il valore della proprietà descCatastoP.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescCatastoP(String value) {
        this.descCatastoP = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdComuneCatP.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodiFkIdComuneCatP() {
        return codiFkIdComuneCatP;
    }

    /**
     * Imposta il valore della proprietà codiFkIdComuneCatP.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodiFkIdComuneCatP(Integer value) {
        this.codiFkIdComuneCatP = value;
    }

    /**
     * Recupera il valore della proprietà descSezioneP.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescSezioneP() {
        return descSezioneP;
    }

    /**
     * Imposta il valore della proprietà descSezioneP.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescSezioneP(String value) {
        this.descSezioneP = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdTipoCatP.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiFkIdTipoCatP() {
        return codiFkIdTipoCatP;
    }

    /**
     * Imposta il valore della proprietà codiFkIdTipoCatP.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiFkIdTipoCatP(String value) {
        this.codiFkIdTipoCatP = value;
    }

    /**
     * Recupera il valore della proprietà descFoglioP.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescFoglioP() {
        return descFoglioP;
    }

    /**
     * Imposta il valore della proprietà descFoglioP.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescFoglioP(String value) {
        this.descFoglioP = value;
    }

    /**
     * Recupera il valore della proprietà descParticella1P.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescParticella1P() {
        return descParticella1P;
    }

    /**
     * Imposta il valore della proprietà descParticella1P.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescParticella1P(String value) {
        this.descParticella1P = value;
    }

    /**
     * Recupera il valore della proprietà descParticella2P.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescParticella2P() {
        return descParticella2P;
    }

    /**
     * Imposta il valore della proprietà descParticella2P.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescParticella2P(String value) {
        this.descParticella2P = value;
    }

    /**
     * Recupera il valore della proprietà descSubalterno1P.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescSubalterno1P() {
        return descSubalterno1P;
    }

    /**
     * Imposta il valore della proprietà descSubalterno1P.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescSubalterno1P(String value) {
        this.descSubalterno1P = value;
    }

    /**
     * Recupera il valore della proprietà descSubalterno2P.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescSubalterno2P() {
        return descSubalterno2P;
    }

    /**
     * Imposta il valore della proprietà descSubalterno2P.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescSubalterno2P(String value) {
        this.descSubalterno2P = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdTipoDenuncia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiFkIdTipoDenuncia() {
        return codiFkIdTipoDenuncia;
    }

    /**
     * Imposta il valore della proprietà codiFkIdTipoDenuncia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiFkIdTipoDenuncia(String value) {
        this.codiFkIdTipoDenuncia = value;
    }

    /**
     * Recupera il valore della proprietà numeDenuncia1.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumeDenuncia1() {
        return numeDenuncia1;
    }

    /**
     * Imposta il valore della proprietà numeDenuncia1.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumeDenuncia1(Integer value) {
        this.numeDenuncia1 = value;
    }

    /**
     * Recupera il valore della proprietà numeDenuncia2.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getNumeDenuncia2() {
        return numeDenuncia2;
    }

    /**
     * Imposta il valore della proprietà numeDenuncia2.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setNumeDenuncia2(Short value) {
        this.numeDenuncia2 = value;
    }

    /**
     * Recupera il valore della proprietà numeAnnoDenuncia.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getNumeAnnoDenuncia() {
        return numeAnnoDenuncia;
    }

    /**
     * Imposta il valore della proprietà numeAnnoDenuncia.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setNumeAnnoDenuncia(Short value) {
        this.numeAnnoDenuncia = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdTipoDenunciaP.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiFkIdTipoDenunciaP() {
        return codiFkIdTipoDenunciaP;
    }

    /**
     * Imposta il valore della proprietà codiFkIdTipoDenunciaP.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiFkIdTipoDenunciaP(String value) {
        this.codiFkIdTipoDenunciaP = value;
    }

    /**
     * Recupera il valore della proprietà numeDenuncia1P.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumeDenuncia1P() {
        return numeDenuncia1P;
    }

    /**
     * Imposta il valore della proprietà numeDenuncia1P.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumeDenuncia1P(Integer value) {
        this.numeDenuncia1P = value;
    }

    /**
     * Recupera il valore della proprietà numeDenuncia2P.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getNumeDenuncia2P() {
        return numeDenuncia2P;
    }

    /**
     * Imposta il valore della proprietà numeDenuncia2P.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setNumeDenuncia2P(Short value) {
        this.numeDenuncia2P = value;
    }

    /**
     * Recupera il valore della proprietà numeAnnoDenunciaP.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getNumeAnnoDenunciaP() {
        return numeAnnoDenunciaP;
    }

    /**
     * Imposta il valore della proprietà numeAnnoDenunciaP.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setNumeAnnoDenunciaP(Short value) {
        this.numeAnnoDenunciaP = value;
    }

    /**
     * Recupera il valore della proprietà numeGraffati.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getNumeGraffati() {
        return numeGraffati;
    }

    /**
     * Imposta il valore della proprietà numeGraffati.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNumeGraffati(Long value) {
        this.numeGraffati = value;
    }

    /**
     * Recupera il valore della proprietà codiFkIdFondo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiFkIdFondo() {
        return codiFkIdFondo;
    }

    /**
     * Imposta il valore della proprietà codiFkIdFondo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiFkIdFondo(String value) {
        this.codiFkIdFondo = value;
    }

    /**
     * Recupera il valore della proprietà numeSupEttari.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumeSupEttari() {
        return numeSupEttari;
    }

    /**
     * Imposta il valore della proprietà numeSupEttari.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumeSupEttari(Integer value) {
        this.numeSupEttari = value;
    }

    /**
     * Recupera il valore della proprietà numeSupAre.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumeSupAre() {
        return numeSupAre;
    }

    /**
     * Imposta il valore della proprietà numeSupAre.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumeSupAre(Integer value) {
        this.numeSupAre = value;
    }

    /**
     * Recupera il valore della proprietà numeSupCentiare.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumeSupCentiare() {
        return numeSupCentiare;
    }

    /**
     * Imposta il valore della proprietà numeSupCentiare.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumeSupCentiare(Integer value) {
        this.numeSupCentiare = value;
    }

    /**
     * Recupera il valore della proprietà numeSupMqInt.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getNumeSupMqInt() {
        return numeSupMqInt;
    }

    /**
     * Imposta il valore della proprietà numeSupMqInt.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setNumeSupMqInt(Short value) {
        this.numeSupMqInt = value;
    }

    /**
     * Recupera il valore della proprietà numeSupMqDec.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getNumeSupMqDec() {
        return numeSupMqDec;
    }

    /**
     * Imposta il valore della proprietà numeSupMqDec.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setNumeSupMqDec(Short value) {
        this.numeSupMqDec = value;
    }

    /**
     * Recupera il valore della proprietà numeVaniInt.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getNumeVaniInt() {
        return numeVaniInt;
    }

    /**
     * Imposta il valore della proprietà numeVaniInt.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setNumeVaniInt(Short value) {
        this.numeVaniInt = value;
    }

    /**
     * Recupera il valore della proprietà numeVaniDec.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getNumeVaniDec() {
        return numeVaniDec;
    }

    /**
     * Imposta il valore della proprietà numeVaniDec.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setNumeVaniDec(Short value) {
        this.numeVaniDec = value;
    }

    /**
     * Recupera il valore della proprietà numeMcInt.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getNumeMcInt() {
        return numeMcInt;
    }

    /**
     * Imposta il valore della proprietà numeMcInt.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setNumeMcInt(Short value) {
        this.numeMcInt = value;
    }

    /**
     * Recupera il valore della proprietà numeMcDec.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getNumeMcDec() {
        return numeMcDec;
    }

    /**
     * Imposta il valore della proprietà numeMcDec.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setNumeMcDec(Short value) {
        this.numeMcDec = value;
    }

    /**
     * Recupera il valore della proprietà descEdificio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescEdificio() {
        return descEdificio;
    }

    /**
     * Imposta il valore della proprietà descEdificio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescEdificio(String value) {
        this.descEdificio = value;
    }

    /**
     * Recupera il valore della proprietà descLotto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescLotto() {
        return descLotto;
    }

    /**
     * Imposta il valore della proprietà descLotto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescLotto(String value) {
        this.descLotto = value;
    }

}
