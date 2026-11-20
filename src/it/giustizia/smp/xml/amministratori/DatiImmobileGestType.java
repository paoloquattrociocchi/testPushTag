
package it.giustizia.smp.xml.amministratori;

import java.math.BigDecimal;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for datiImmobileGestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="datiImmobileGestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="stima" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}decimal12_2"/>
 *         &lt;element name="dataStima" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="dirittiTerzi" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="descDirittiTerzi" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string2000" minOccurs="0"/>
 *         &lt;element name="dataPossesso" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="stimaOneriRistrutturazione" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}decimal12_2" minOccurs="0"/>
 *         &lt;element name="confiscaParziale" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="beneDivisibile" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="utilizzoAutonomoQuota" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="vocazioneTuristica" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="fabbricatoInteramenteConfiscato" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="immobileOccupato" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="descTipoOccupazione" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string40" minOccurs="0"/>
 *         &lt;element name="descAltroTipoOccupazione" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string80" minOccurs="0"/>
 *         &lt;element name="dataStipulaContratto" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dataScadenzaContratto" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="canoneAnnuoLocazione" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}decimal12_2" minOccurs="0"/>
 *         &lt;element name="mensilePattuito" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}decimal12_2" minOccurs="0"/>
 *         &lt;element name="mensileVersato" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}decimal12_2" minOccurs="0"/>
 *         &lt;element name="regolaritaPagamenti" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="descrizionePagamenti" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string200" minOccurs="0"/>
 *         &lt;element name="importoDovuto" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}decimal12_2" minOccurs="0"/>
 *         &lt;element name="importoDovutoDal" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="importoDovutoAl" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="condominioArretrato" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}decimal12_2" minOccurs="0"/>
 *         &lt;element name="condominioArretratoDal" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="condominioArretratoAl" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="condominioInScadenza" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}decimal12_2" minOccurs="0"/>
 *         &lt;element name="descTipoOccupanti" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string40" minOccurs="0"/>
 *         &lt;element name="presenzaServitu" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="usiCivici" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="altriVincoli" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "datiImmobileGestType", propOrder = {
    "stima",
    "dataStima",
    "dirittiTerzi",
    "descDirittiTerzi",
    "dataPossesso",
    "stimaOneriRistrutturazione",
    "confiscaParziale",
    "beneDivisibile",
    "utilizzoAutonomoQuota",
    "vocazioneTuristica",
    "fabbricatoInteramenteConfiscato",
    "immobileOccupato",
    "descTipoOccupazione",
    "descAltroTipoOccupazione",
    "dataStipulaContratto",
    "dataScadenzaContratto",
    "canoneAnnuoLocazione",
    "mensilePattuito",
    "mensileVersato",
    "regolaritaPagamenti",
    "descrizionePagamenti",
    "importoDovuto",
    "importoDovutoDal",
    "importoDovutoAl",
    "condominioArretrato",
    "condominioArretratoDal",
    "condominioArretratoAl",
    "condominioInScadenza",
    "descTipoOccupanti",
    "presenzaServitu",
    "usiCivici",
    "altriVincoli"
})
public class DatiImmobileGestType {

    @XmlElement(required = true)
    protected BigDecimal stima;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataStima;
    @XmlElementRef(name = "dirittiTerzi", namespace = "http://www.giustizia.it/tool/amministratori/schema/trasmissione", type = JAXBElement.class)
    protected JAXBElement<Boolean> dirittiTerzi;
    @XmlElementRef(name = "descDirittiTerzi", namespace = "http://www.giustizia.it/tool/amministratori/schema/trasmissione", type = JAXBElement.class)
    protected JAXBElement<String> descDirittiTerzi;
    @XmlElementRef(name = "dataPossesso", namespace = "http://www.giustizia.it/tool/amministratori/schema/trasmissione", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> dataPossesso;
    @XmlElementRef(name = "stimaOneriRistrutturazione", namespace = "http://www.giustizia.it/tool/amministratori/schema/trasmissione", type = JAXBElement.class)
    protected JAXBElement<BigDecimal> stimaOneriRistrutturazione;
    @XmlElementRef(name = "confiscaParziale", namespace = "http://www.giustizia.it/tool/amministratori/schema/trasmissione", type = JAXBElement.class)
    protected JAXBElement<Boolean> confiscaParziale;
    @XmlElementRef(name = "beneDivisibile", namespace = "http://www.giustizia.it/tool/amministratori/schema/trasmissione", type = JAXBElement.class)
    protected JAXBElement<Boolean> beneDivisibile;
    @XmlElementRef(name = "utilizzoAutonomoQuota", namespace = "http://www.giustizia.it/tool/amministratori/schema/trasmissione", type = JAXBElement.class)
    protected JAXBElement<Boolean> utilizzoAutonomoQuota;
    @XmlElementRef(name = "vocazioneTuristica", namespace = "http://www.giustizia.it/tool/amministratori/schema/trasmissione", type = JAXBElement.class)
    protected JAXBElement<Boolean> vocazioneTuristica;
    @XmlElementRef(name = "fabbricatoInteramenteConfiscato", namespace = "http://www.giustizia.it/tool/amministratori/schema/trasmissione", type = JAXBElement.class)
    protected JAXBElement<Boolean> fabbricatoInteramenteConfiscato;
    @XmlElementRef(name = "immobileOccupato", namespace = "http://www.giustizia.it/tool/amministratori/schema/trasmissione", type = JAXBElement.class)
    protected JAXBElement<Boolean> immobileOccupato;
    @XmlElementRef(name = "descTipoOccupazione", namespace = "http://www.giustizia.it/tool/amministratori/schema/trasmissione", type = JAXBElement.class)
    protected JAXBElement<String> descTipoOccupazione;
    @XmlElementRef(name = "descAltroTipoOccupazione", namespace = "http://www.giustizia.it/tool/amministratori/schema/trasmissione", type = JAXBElement.class)
    protected JAXBElement<String> descAltroTipoOccupazione;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataStipulaContratto;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataScadenzaContratto;
    @XmlElementRef(name = "canoneAnnuoLocazione", namespace = "http://www.giustizia.it/tool/amministratori/schema/trasmissione", type = JAXBElement.class)
    protected JAXBElement<BigDecimal> canoneAnnuoLocazione;
    @XmlElementRef(name = "mensilePattuito", namespace = "http://www.giustizia.it/tool/amministratori/schema/trasmissione", type = JAXBElement.class)
    protected JAXBElement<BigDecimal> mensilePattuito;
    @XmlElementRef(name = "mensileVersato", namespace = "http://www.giustizia.it/tool/amministratori/schema/trasmissione", type = JAXBElement.class)
    protected JAXBElement<BigDecimal> mensileVersato;
    @XmlElementRef(name = "regolaritaPagamenti", namespace = "http://www.giustizia.it/tool/amministratori/schema/trasmissione", type = JAXBElement.class)
    protected JAXBElement<Boolean> regolaritaPagamenti;
    @XmlElementRef(name = "descrizionePagamenti", namespace = "http://www.giustizia.it/tool/amministratori/schema/trasmissione", type = JAXBElement.class)
    protected JAXBElement<String> descrizionePagamenti;
    @XmlElementRef(name = "importoDovuto", namespace = "http://www.giustizia.it/tool/amministratori/schema/trasmissione", type = JAXBElement.class)
    protected JAXBElement<BigDecimal> importoDovuto;
    @XmlElementRef(name = "importoDovutoDal", namespace = "http://www.giustizia.it/tool/amministratori/schema/trasmissione", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> importoDovutoDal;
    @XmlElementRef(name = "importoDovutoAl", namespace = "http://www.giustizia.it/tool/amministratori/schema/trasmissione", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> importoDovutoAl;
    @XmlElementRef(name = "condominioArretrato", namespace = "http://www.giustizia.it/tool/amministratori/schema/trasmissione", type = JAXBElement.class)
    protected JAXBElement<BigDecimal> condominioArretrato;
    @XmlElementRef(name = "condominioArretratoDal", namespace = "http://www.giustizia.it/tool/amministratori/schema/trasmissione", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> condominioArretratoDal;
    @XmlElementRef(name = "condominioArretratoAl", namespace = "http://www.giustizia.it/tool/amministratori/schema/trasmissione", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> condominioArretratoAl;
    @XmlElementRef(name = "condominioInScadenza", namespace = "http://www.giustizia.it/tool/amministratori/schema/trasmissione", type = JAXBElement.class)
    protected JAXBElement<BigDecimal> condominioInScadenza;
    @XmlElementRef(name = "descTipoOccupanti", namespace = "http://www.giustizia.it/tool/amministratori/schema/trasmissione", type = JAXBElement.class)
    protected JAXBElement<String> descTipoOccupanti;
    @XmlElementRef(name = "presenzaServitu", namespace = "http://www.giustizia.it/tool/amministratori/schema/trasmissione", type = JAXBElement.class)
    protected JAXBElement<Boolean> presenzaServitu;
    @XmlElementRef(name = "usiCivici", namespace = "http://www.giustizia.it/tool/amministratori/schema/trasmissione", type = JAXBElement.class)
    protected JAXBElement<Boolean> usiCivici;
    @XmlElementRef(name = "altriVincoli", namespace = "http://www.giustizia.it/tool/amministratori/schema/trasmissione", type = JAXBElement.class)
    protected JAXBElement<Boolean> altriVincoli;

    /**
     * Gets the value of the stima property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getStima() {
        return stima;
    }

    /**
     * Sets the value of the stima property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setStima(BigDecimal value) {
        this.stima = value;
    }

    /**
     * Gets the value of the dataStima property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataStima() {
        return dataStima;
    }

    /**
     * Sets the value of the dataStima property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataStima(XMLGregorianCalendar value) {
        this.dataStima = value;
    }

    /**
     * Gets the value of the dirittiTerzi property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getDirittiTerzi() {
        return dirittiTerzi;
    }

    /**
     * Sets the value of the dirittiTerzi property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setDirittiTerzi(JAXBElement<Boolean> value) {
        this.dirittiTerzi = ((JAXBElement<Boolean> ) value);
    }

    /**
     * Gets the value of the descDirittiTerzi property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDescDirittiTerzi() {
        return descDirittiTerzi;
    }

    /**
     * Sets the value of the descDirittiTerzi property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDescDirittiTerzi(JAXBElement<String> value) {
        this.descDirittiTerzi = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the dataPossesso property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getDataPossesso() {
        return dataPossesso;
    }

    /**
     * Sets the value of the dataPossesso property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setDataPossesso(JAXBElement<XMLGregorianCalendar> value) {
        this.dataPossesso = ((JAXBElement<XMLGregorianCalendar> ) value);
    }

    /**
     * Gets the value of the stimaOneriRistrutturazione property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public JAXBElement<BigDecimal> getStimaOneriRistrutturazione() {
        return stimaOneriRistrutturazione;
    }

    /**
     * Sets the value of the stimaOneriRistrutturazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public void setStimaOneriRistrutturazione(JAXBElement<BigDecimal> value) {
        this.stimaOneriRistrutturazione = ((JAXBElement<BigDecimal> ) value);
    }

    /**
     * Gets the value of the confiscaParziale property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getConfiscaParziale() {
        return confiscaParziale;
    }

    /**
     * Sets the value of the confiscaParziale property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setConfiscaParziale(JAXBElement<Boolean> value) {
        this.confiscaParziale = ((JAXBElement<Boolean> ) value);
    }

    /**
     * Gets the value of the beneDivisibile property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getBeneDivisibile() {
        return beneDivisibile;
    }

    /**
     * Sets the value of the beneDivisibile property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setBeneDivisibile(JAXBElement<Boolean> value) {
        this.beneDivisibile = ((JAXBElement<Boolean> ) value);
    }

    /**
     * Gets the value of the utilizzoAutonomoQuota property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getUtilizzoAutonomoQuota() {
        return utilizzoAutonomoQuota;
    }

    /**
     * Sets the value of the utilizzoAutonomoQuota property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setUtilizzoAutonomoQuota(JAXBElement<Boolean> value) {
        this.utilizzoAutonomoQuota = ((JAXBElement<Boolean> ) value);
    }

    /**
     * Gets the value of the vocazioneTuristica property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getVocazioneTuristica() {
        return vocazioneTuristica;
    }

    /**
     * Sets the value of the vocazioneTuristica property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setVocazioneTuristica(JAXBElement<Boolean> value) {
        this.vocazioneTuristica = ((JAXBElement<Boolean> ) value);
    }

    /**
     * Gets the value of the fabbricatoInteramenteConfiscato property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getFabbricatoInteramenteConfiscato() {
        return fabbricatoInteramenteConfiscato;
    }

    /**
     * Sets the value of the fabbricatoInteramenteConfiscato property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setFabbricatoInteramenteConfiscato(JAXBElement<Boolean> value) {
        this.fabbricatoInteramenteConfiscato = ((JAXBElement<Boolean> ) value);
    }

    /**
     * Gets the value of the immobileOccupato property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getImmobileOccupato() {
        return immobileOccupato;
    }

    /**
     * Sets the value of the immobileOccupato property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setImmobileOccupato(JAXBElement<Boolean> value) {
        this.immobileOccupato = ((JAXBElement<Boolean> ) value);
    }

    /**
     * Gets the value of the descTipoOccupazione property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDescTipoOccupazione() {
        return descTipoOccupazione;
    }

    /**
     * Sets the value of the descTipoOccupazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDescTipoOccupazione(JAXBElement<String> value) {
        this.descTipoOccupazione = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the descAltroTipoOccupazione property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDescAltroTipoOccupazione() {
        return descAltroTipoOccupazione;
    }

    /**
     * Sets the value of the descAltroTipoOccupazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDescAltroTipoOccupazione(JAXBElement<String> value) {
        this.descAltroTipoOccupazione = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the dataStipulaContratto property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataStipulaContratto() {
        return dataStipulaContratto;
    }

    /**
     * Sets the value of the dataStipulaContratto property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataStipulaContratto(XMLGregorianCalendar value) {
        this.dataStipulaContratto = value;
    }

    /**
     * Gets the value of the dataScadenzaContratto property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataScadenzaContratto() {
        return dataScadenzaContratto;
    }

    /**
     * Sets the value of the dataScadenzaContratto property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataScadenzaContratto(XMLGregorianCalendar value) {
        this.dataScadenzaContratto = value;
    }

    /**
     * Gets the value of the canoneAnnuoLocazione property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public JAXBElement<BigDecimal> getCanoneAnnuoLocazione() {
        return canoneAnnuoLocazione;
    }

    /**
     * Sets the value of the canoneAnnuoLocazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public void setCanoneAnnuoLocazione(JAXBElement<BigDecimal> value) {
        this.canoneAnnuoLocazione = ((JAXBElement<BigDecimal> ) value);
    }

    /**
     * Gets the value of the mensilePattuito property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public JAXBElement<BigDecimal> getMensilePattuito() {
        return mensilePattuito;
    }

    /**
     * Sets the value of the mensilePattuito property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public void setMensilePattuito(JAXBElement<BigDecimal> value) {
        this.mensilePattuito = ((JAXBElement<BigDecimal> ) value);
    }

    /**
     * Gets the value of the mensileVersato property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public JAXBElement<BigDecimal> getMensileVersato() {
        return mensileVersato;
    }

    /**
     * Sets the value of the mensileVersato property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public void setMensileVersato(JAXBElement<BigDecimal> value) {
        this.mensileVersato = ((JAXBElement<BigDecimal> ) value);
    }

    /**
     * Gets the value of the regolaritaPagamenti property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getRegolaritaPagamenti() {
        return regolaritaPagamenti;
    }

    /**
     * Sets the value of the regolaritaPagamenti property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setRegolaritaPagamenti(JAXBElement<Boolean> value) {
        this.regolaritaPagamenti = ((JAXBElement<Boolean> ) value);
    }

    /**
     * Gets the value of the descrizionePagamenti property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDescrizionePagamenti() {
        return descrizionePagamenti;
    }

    /**
     * Sets the value of the descrizionePagamenti property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDescrizionePagamenti(JAXBElement<String> value) {
        this.descrizionePagamenti = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the importoDovuto property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public JAXBElement<BigDecimal> getImportoDovuto() {
        return importoDovuto;
    }

    /**
     * Sets the value of the importoDovuto property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public void setImportoDovuto(JAXBElement<BigDecimal> value) {
        this.importoDovuto = ((JAXBElement<BigDecimal> ) value);
    }

    /**
     * Gets the value of the importoDovutoDal property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getImportoDovutoDal() {
        return importoDovutoDal;
    }

    /**
     * Sets the value of the importoDovutoDal property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setImportoDovutoDal(JAXBElement<XMLGregorianCalendar> value) {
        this.importoDovutoDal = ((JAXBElement<XMLGregorianCalendar> ) value);
    }

    /**
     * Gets the value of the importoDovutoAl property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getImportoDovutoAl() {
        return importoDovutoAl;
    }

    /**
     * Sets the value of the importoDovutoAl property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setImportoDovutoAl(JAXBElement<XMLGregorianCalendar> value) {
        this.importoDovutoAl = ((JAXBElement<XMLGregorianCalendar> ) value);
    }

    /**
     * Gets the value of the condominioArretrato property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public JAXBElement<BigDecimal> getCondominioArretrato() {
        return condominioArretrato;
    }

    /**
     * Sets the value of the condominioArretrato property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public void setCondominioArretrato(JAXBElement<BigDecimal> value) {
        this.condominioArretrato = ((JAXBElement<BigDecimal> ) value);
    }

    /**
     * Gets the value of the condominioArretratoDal property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getCondominioArretratoDal() {
        return condominioArretratoDal;
    }

    /**
     * Sets the value of the condominioArretratoDal property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setCondominioArretratoDal(JAXBElement<XMLGregorianCalendar> value) {
        this.condominioArretratoDal = ((JAXBElement<XMLGregorianCalendar> ) value);
    }

    /**
     * Gets the value of the condominioArretratoAl property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getCondominioArretratoAl() {
        return condominioArretratoAl;
    }

    /**
     * Sets the value of the condominioArretratoAl property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setCondominioArretratoAl(JAXBElement<XMLGregorianCalendar> value) {
        this.condominioArretratoAl = ((JAXBElement<XMLGregorianCalendar> ) value);
    }

    /**
     * Gets the value of the condominioInScadenza property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public JAXBElement<BigDecimal> getCondominioInScadenza() {
        return condominioInScadenza;
    }

    /**
     * Sets the value of the condominioInScadenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public void setCondominioInScadenza(JAXBElement<BigDecimal> value) {
        this.condominioInScadenza = ((JAXBElement<BigDecimal> ) value);
    }

    /**
     * Gets the value of the descTipoOccupanti property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDescTipoOccupanti() {
        return descTipoOccupanti;
    }

    /**
     * Sets the value of the descTipoOccupanti property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDescTipoOccupanti(JAXBElement<String> value) {
        this.descTipoOccupanti = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the presenzaServitu property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getPresenzaServitu() {
        return presenzaServitu;
    }

    /**
     * Sets the value of the presenzaServitu property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setPresenzaServitu(JAXBElement<Boolean> value) {
        this.presenzaServitu = ((JAXBElement<Boolean> ) value);
    }

    /**
     * Gets the value of the usiCivici property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getUsiCivici() {
        return usiCivici;
    }

    /**
     * Sets the value of the usiCivici property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setUsiCivici(JAXBElement<Boolean> value) {
        this.usiCivici = ((JAXBElement<Boolean> ) value);
    }

    /**
     * Gets the value of the altriVincoli property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getAltriVincoli() {
        return altriVincoli;
    }

    /**
     * Sets the value of the altriVincoli property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setAltriVincoli(JAXBElement<Boolean> value) {
        this.altriVincoli = ((JAXBElement<Boolean> ) value);
    }

}
