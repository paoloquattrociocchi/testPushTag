
package it.giustizia.smp.xml.amministratori;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for datiAziendaGestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="datiAziendaGestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="stima" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}decimal12_2"/>
 *         &lt;element name="dataStima" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="dirittiTerzi" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="descDirittiTerzi" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string2000" minOccurs="0"/>
 *         &lt;element name="trascrizioneSequestro" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="dataTrascrizioneSequestro" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="descStatoAttivitaAziendale" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string2000" minOccurs="0"/>
 *         &lt;element name="prospettivaProsecuzioneAttivita" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string2000" minOccurs="0"/>
 *         &lt;element name="descDestinazioneImpresa" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string120" minOccurs="0"/>
 *         &lt;element name="tipoOrganoAmministrativo" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string120" minOccurs="0"/>
 *         &lt;element name="nominativoAmministratore" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string120" minOccurs="0"/>
 *         &lt;element name="dataNominaAmministratore" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="nominativoRappresentanteLegale" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string120" minOccurs="0"/>
 *         &lt;element name="dataNominaRappresentante" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="liquidazioneSociale" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string120" minOccurs="0"/>
 *         &lt;element name="dataLiquidazione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="nominativoLiquidatore" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string120" minOccurs="0"/>
 *         &lt;element name="tipoOrganoControllo" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string120" minOccurs="0"/>
 *         &lt;element name="revisioneLegaleConti" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string120" minOccurs="0"/>
 *         &lt;element name="nominativoRevisore" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string120" minOccurs="0"/>
 *         &lt;element name="dataUltimoBilancioApprovato" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="saldoUltimoBilancio" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}decimal12_2" minOccurs="0"/>
 *         &lt;element name="luogoScrittureContabili" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string120" minOccurs="0"/>
 *         &lt;element name="nominativaDetentoreScritture" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string120" minOccurs="0"/>
 *         &lt;element name="numeroDipendenti" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="ccnl" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string120" minOccurs="0"/>
 *         &lt;element name="tipoContrattoLavoro" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string120" minOccurs="0"/>
 *         &lt;element name="ammortizzatoreSociale" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string2000" minOccurs="0"/>
 *         &lt;element name="contenziosi" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="rispettoNormeSicurezza" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="lavoratoriStranieri" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="addettiIrregolari" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="lavoratoriFormazione" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="posizioneSocio" type="{http://www.giustizia.it/tool/amministratori/schema/trasmissione}string120" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "datiAziendaGestType", propOrder = {
    "stima",
    "dataStima",
    "dirittiTerzi",
    "descDirittiTerzi",
    "trascrizioneSequestro",
    "dataTrascrizioneSequestro",
    "descStatoAttivitaAziendale",
    "prospettivaProsecuzioneAttivita",
    "descDestinazioneImpresa",
    "tipoOrganoAmministrativo",
    "nominativoAmministratore",
    "dataNominaAmministratore",
    "nominativoRappresentanteLegale",
    "dataNominaRappresentante",
    "liquidazioneSociale",
    "dataLiquidazione",
    "nominativoLiquidatore",
    "tipoOrganoControllo",
    "revisioneLegaleConti",
    "nominativoRevisore",
    "dataUltimoBilancioApprovato",
    "saldoUltimoBilancio",
    "luogoScrittureContabili",
    "nominativaDetentoreScritture",
    "numeroDipendenti",
    "ccnl",
    "tipoContrattoLavoro",
    "ammortizzatoreSociale",
    "contenziosi",
    "rispettoNormeSicurezza",
    "lavoratoriStranieri",
    "addettiIrregolari",
    "lavoratoriFormazione",
    "posizioneSocio"
})
public class DatiAziendaGestType {

    @XmlElement(required = true)
    protected BigDecimal stima;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataStima;
    protected Boolean dirittiTerzi;
    protected String descDirittiTerzi;
    protected Boolean trascrizioneSequestro;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataTrascrizioneSequestro;
    protected String descStatoAttivitaAziendale;
    protected String prospettivaProsecuzioneAttivita;
    protected String descDestinazioneImpresa;
    protected String tipoOrganoAmministrativo;
    protected String nominativoAmministratore;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataNominaAmministratore;
    protected String nominativoRappresentanteLegale;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataNominaRappresentante;
    protected String liquidazioneSociale;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataLiquidazione;
    protected String nominativoLiquidatore;
    protected String tipoOrganoControllo;
    protected String revisioneLegaleConti;
    protected String nominativoRevisore;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataUltimoBilancioApprovato;
    protected BigDecimal saldoUltimoBilancio;
    protected String luogoScrittureContabili;
    protected String nominativaDetentoreScritture;
    protected BigInteger numeroDipendenti;
    protected String ccnl;
    protected String tipoContrattoLavoro;
    protected String ammortizzatoreSociale;
    protected Boolean contenziosi;
    protected Boolean rispettoNormeSicurezza;
    protected Boolean lavoratoriStranieri;
    protected Boolean addettiIrregolari;
    protected Boolean lavoratoriFormazione;
    protected String posizioneSocio;

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
     *     {@link Boolean }
     *     
     */
    public Boolean isDirittiTerzi() {
        return dirittiTerzi;
    }

    /**
     * Sets the value of the dirittiTerzi property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDirittiTerzi(Boolean value) {
        this.dirittiTerzi = value;
    }

    /**
     * Gets the value of the descDirittiTerzi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescDirittiTerzi() {
        return descDirittiTerzi;
    }

    /**
     * Sets the value of the descDirittiTerzi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescDirittiTerzi(String value) {
        this.descDirittiTerzi = value;
    }

    /**
     * Gets the value of the trascrizioneSequestro property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isTrascrizioneSequestro() {
        return trascrizioneSequestro;
    }

    /**
     * Sets the value of the trascrizioneSequestro property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTrascrizioneSequestro(Boolean value) {
        this.trascrizioneSequestro = value;
    }

    /**
     * Gets the value of the dataTrascrizioneSequestro property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataTrascrizioneSequestro() {
        return dataTrascrizioneSequestro;
    }

    /**
     * Sets the value of the dataTrascrizioneSequestro property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataTrascrizioneSequestro(XMLGregorianCalendar value) {
        this.dataTrascrizioneSequestro = value;
    }

    /**
     * Gets the value of the descStatoAttivitaAziendale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescStatoAttivitaAziendale() {
        return descStatoAttivitaAziendale;
    }

    /**
     * Sets the value of the descStatoAttivitaAziendale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescStatoAttivitaAziendale(String value) {
        this.descStatoAttivitaAziendale = value;
    }

    /**
     * Gets the value of the prospettivaProsecuzioneAttivita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProspettivaProsecuzioneAttivita() {
        return prospettivaProsecuzioneAttivita;
    }

    /**
     * Sets the value of the prospettivaProsecuzioneAttivita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProspettivaProsecuzioneAttivita(String value) {
        this.prospettivaProsecuzioneAttivita = value;
    }

    /**
     * Gets the value of the descDestinazioneImpresa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescDestinazioneImpresa() {
        return descDestinazioneImpresa;
    }

    /**
     * Sets the value of the descDestinazioneImpresa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescDestinazioneImpresa(String value) {
        this.descDestinazioneImpresa = value;
    }

    /**
     * Gets the value of the tipoOrganoAmministrativo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoOrganoAmministrativo() {
        return tipoOrganoAmministrativo;
    }

    /**
     * Sets the value of the tipoOrganoAmministrativo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoOrganoAmministrativo(String value) {
        this.tipoOrganoAmministrativo = value;
    }

    /**
     * Gets the value of the nominativoAmministratore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNominativoAmministratore() {
        return nominativoAmministratore;
    }

    /**
     * Sets the value of the nominativoAmministratore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNominativoAmministratore(String value) {
        this.nominativoAmministratore = value;
    }

    /**
     * Gets the value of the dataNominaAmministratore property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataNominaAmministratore() {
        return dataNominaAmministratore;
    }

    /**
     * Sets the value of the dataNominaAmministratore property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataNominaAmministratore(XMLGregorianCalendar value) {
        this.dataNominaAmministratore = value;
    }

    /**
     * Gets the value of the nominativoRappresentanteLegale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNominativoRappresentanteLegale() {
        return nominativoRappresentanteLegale;
    }

    /**
     * Sets the value of the nominativoRappresentanteLegale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNominativoRappresentanteLegale(String value) {
        this.nominativoRappresentanteLegale = value;
    }

    /**
     * Gets the value of the dataNominaRappresentante property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataNominaRappresentante() {
        return dataNominaRappresentante;
    }

    /**
     * Sets the value of the dataNominaRappresentante property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataNominaRappresentante(XMLGregorianCalendar value) {
        this.dataNominaRappresentante = value;
    }

    /**
     * Gets the value of the liquidazioneSociale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLiquidazioneSociale() {
        return liquidazioneSociale;
    }

    /**
     * Sets the value of the liquidazioneSociale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLiquidazioneSociale(String value) {
        this.liquidazioneSociale = value;
    }

    /**
     * Gets the value of the dataLiquidazione property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataLiquidazione() {
        return dataLiquidazione;
    }

    /**
     * Sets the value of the dataLiquidazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataLiquidazione(XMLGregorianCalendar value) {
        this.dataLiquidazione = value;
    }

    /**
     * Gets the value of the nominativoLiquidatore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNominativoLiquidatore() {
        return nominativoLiquidatore;
    }

    /**
     * Sets the value of the nominativoLiquidatore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNominativoLiquidatore(String value) {
        this.nominativoLiquidatore = value;
    }

    /**
     * Gets the value of the tipoOrganoControllo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoOrganoControllo() {
        return tipoOrganoControllo;
    }

    /**
     * Sets the value of the tipoOrganoControllo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoOrganoControllo(String value) {
        this.tipoOrganoControllo = value;
    }

    /**
     * Gets the value of the revisioneLegaleConti property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRevisioneLegaleConti() {
        return revisioneLegaleConti;
    }

    /**
     * Sets the value of the revisioneLegaleConti property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRevisioneLegaleConti(String value) {
        this.revisioneLegaleConti = value;
    }

    /**
     * Gets the value of the nominativoRevisore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNominativoRevisore() {
        return nominativoRevisore;
    }

    /**
     * Sets the value of the nominativoRevisore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNominativoRevisore(String value) {
        this.nominativoRevisore = value;
    }

    /**
     * Gets the value of the dataUltimoBilancioApprovato property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataUltimoBilancioApprovato() {
        return dataUltimoBilancioApprovato;
    }

    /**
     * Sets the value of the dataUltimoBilancioApprovato property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataUltimoBilancioApprovato(XMLGregorianCalendar value) {
        this.dataUltimoBilancioApprovato = value;
    }

    /**
     * Gets the value of the saldoUltimoBilancio property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSaldoUltimoBilancio() {
        return saldoUltimoBilancio;
    }

    /**
     * Sets the value of the saldoUltimoBilancio property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSaldoUltimoBilancio(BigDecimal value) {
        this.saldoUltimoBilancio = value;
    }

    /**
     * Gets the value of the luogoScrittureContabili property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLuogoScrittureContabili() {
        return luogoScrittureContabili;
    }

    /**
     * Sets the value of the luogoScrittureContabili property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLuogoScrittureContabili(String value) {
        this.luogoScrittureContabili = value;
    }

    /**
     * Gets the value of the nominativaDetentoreScritture property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNominativaDetentoreScritture() {
        return nominativaDetentoreScritture;
    }

    /**
     * Sets the value of the nominativaDetentoreScritture property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNominativaDetentoreScritture(String value) {
        this.nominativaDetentoreScritture = value;
    }

    /**
     * Gets the value of the numeroDipendenti property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumeroDipendenti() {
        return numeroDipendenti;
    }

    /**
     * Sets the value of the numeroDipendenti property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumeroDipendenti(BigInteger value) {
        this.numeroDipendenti = value;
    }

    /**
     * Gets the value of the ccnl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCcnl() {
        return ccnl;
    }

    /**
     * Sets the value of the ccnl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCcnl(String value) {
        this.ccnl = value;
    }

    /**
     * Gets the value of the tipoContrattoLavoro property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoContrattoLavoro() {
        return tipoContrattoLavoro;
    }

    /**
     * Sets the value of the tipoContrattoLavoro property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoContrattoLavoro(String value) {
        this.tipoContrattoLavoro = value;
    }

    /**
     * Gets the value of the ammortizzatoreSociale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmmortizzatoreSociale() {
        return ammortizzatoreSociale;
    }

    /**
     * Sets the value of the ammortizzatoreSociale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmmortizzatoreSociale(String value) {
        this.ammortizzatoreSociale = value;
    }

    /**
     * Gets the value of the contenziosi property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isContenziosi() {
        return contenziosi;
    }

    /**
     * Sets the value of the contenziosi property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setContenziosi(Boolean value) {
        this.contenziosi = value;
    }

    /**
     * Gets the value of the rispettoNormeSicurezza property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRispettoNormeSicurezza() {
        return rispettoNormeSicurezza;
    }

    /**
     * Sets the value of the rispettoNormeSicurezza property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRispettoNormeSicurezza(Boolean value) {
        this.rispettoNormeSicurezza = value;
    }

    /**
     * Gets the value of the lavoratoriStranieri property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isLavoratoriStranieri() {
        return lavoratoriStranieri;
    }

    /**
     * Sets the value of the lavoratoriStranieri property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setLavoratoriStranieri(Boolean value) {
        this.lavoratoriStranieri = value;
    }

    /**
     * Gets the value of the addettiIrregolari property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAddettiIrregolari() {
        return addettiIrregolari;
    }

    /**
     * Sets the value of the addettiIrregolari property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAddettiIrregolari(Boolean value) {
        this.addettiIrregolari = value;
    }

    /**
     * Gets the value of the lavoratoriFormazione property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isLavoratoriFormazione() {
        return lavoratoriFormazione;
    }

    /**
     * Sets the value of the lavoratoriFormazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setLavoratoriFormazione(Boolean value) {
        this.lavoratoriFormazione = value;
    }

    /**
     * Gets the value of the posizioneSocio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPosizioneSocio() {
        return posizioneSocio;
    }

    /**
     * Sets the value of the posizioneSocio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPosizioneSocio(String value) {
        this.posizioneSocio = value;
    }

}
