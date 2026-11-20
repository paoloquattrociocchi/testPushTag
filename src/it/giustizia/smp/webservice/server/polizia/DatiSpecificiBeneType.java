
package it.giustizia.smp.webservice.server.polizia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiSpecificiBeneType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiSpecificiBeneType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ID_ISTAT_STATO" type="{http://webservice.smp.giustizia.it/basetypes}CodStatoIstatType"/>
 *         &lt;element name="ID_ISTAT_COMUNE" type="{http://webservice.smp.giustizia.it/basetypes}CodComuneIstatType"/>
 *         &lt;element name="DESCR_COMUNE" type="{http://webservice.smp.giustizia.it/basetypes}ComuneType"/>
 *         &lt;element name="DESC_INDIRIZZO" type="{http://webservice.smp.giustizia.it/basetypes}IndirizzoType" minOccurs="0"/>
 *         &lt;element name="DESC_CIVICO" type="{http://webservice.smp.giustizia.it/basetypes}CivicoType" minOccurs="0"/>
 *         &lt;element name="ALTRO_OGGETTO" type="{http://webservice.smp.giustizia.it/server/polizia/type}AltriOggettiType"/>
 *         &lt;element name="ANIMALE" type="{http://webservice.smp.giustizia.it/server/polizia/type}AnimaliType"/>
 *         &lt;element name="AZIENDA" type="{http://webservice.smp.giustizia.it/server/polizia/type}AziendaType"/>
 *         &lt;element name="COLLEZIONE" type="{http://webservice.smp.giustizia.it/server/polizia/type}CollezioniType"/>
 *         &lt;element name="DENARO" type="{http://webservice.smp.giustizia.it/server/polizia/type}DenaroType"/>
 *         &lt;element name="DENARO_CONTANTE" type="{http://webservice.smp.giustizia.it/server/polizia/type}DenaroContanteType"/>
 *         &lt;element name="FINANZIARIO" type="{http://webservice.smp.giustizia.it/server/polizia/type}FinanziarioType"/>
 *         &lt;element name="IMMATERIALE" type="{http://webservice.smp.giustizia.it/server/polizia/type}ImmaterialeType"/>
 *         &lt;element name="IMMOBILE" type="{http://webservice.smp.giustizia.it/server/polizia/type}ImmobileType"/>
 *         &lt;element name="VEICOLO" type="{http://webservice.smp.giustizia.it/server/polizia/type}VeicoloType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiSpecificiBeneType", propOrder = {
    "idistatstato",
    "idistatcomune",
    "descrcomune",
    "descindirizzo",
    "desccivico",
    "altrooggetto",
    "animale",
    "azienda",
    "collezione",
    "denaro",
    "denarocontante",
    "finanziario",
    "immateriale",
    "immobile",
    "veicolo"
})
public class DatiSpecificiBeneType {

    @XmlElement(name = "ID_ISTAT_STATO", required = true, nillable = true)
    protected String ID_ISTAT_STATO;
    @XmlElement(name = "ID_ISTAT_COMUNE", required = true, nillable = true)
    protected String ID_ISTAT_COMUNE;
    @XmlElement(name = "DESCR_COMUNE", required = true)
    protected String DESCR_COMUNE;
    @XmlElement(name = "DESC_INDIRIZZO")
    protected String DESC_INDIRIZZO;
    @XmlElement(name = "DESC_CIVICO")
    protected String DESC_CIVICO;
    /*@XmlElement(name = "ALTRO_OGGETTO", required = true, nillable = true)
    protected AltriOggettiType ALTRO_OGGETTO;
    @XmlElement(name = "ANIMALE", required = true, nillable = true)
    protected AnimaliType ANIMALE;
    @XmlElement(name = "AZIENDA", required = true, nillable = true)
    protected AziendaType AZIENDA;
    @XmlElement(name = "COLLEZIONE", required = true, nillable = true)
    protected CollezioniType COLLEZIONE;
    @XmlElement(name = "DENARO", required = true, nillable = true)
    protected DenaroType DENARO;
    @XmlElement(name = "DENARO_CONTANTE", required = true, nillable = true)
    protected DenaroContanteType DENARO_CONTANTE;
    @XmlElement(name = "FINANZIARIO", required = true, nillable = true)
    protected FinanziarioType FINANZIARIO;
    @XmlElement(name = "IMMATERIALE", required = true, nillable = true)
    protected ImmaterialeType IMMATERIALE;
    @XmlElement(name = "IMMOBILE", required = true, nillable = true)
    protected ImmobileType IMMOBILE;
    @XmlElement(name = "VEICOLO", required = true, nillable = true)
    protected VeicoloType VEICOLO;*/
    
	public String getID_ISTAT_STATO() {
		return ID_ISTAT_STATO;
	}
	public void setID_ISTAT_STATO(String iD_ISTAT_STATO) {
		ID_ISTAT_STATO = iD_ISTAT_STATO;
	}
	public String getID_ISTAT_COMUNE() {
		return ID_ISTAT_COMUNE;
	}
	public void setID_ISTAT_COMUNE(String iD_ISTAT_COMUNE) {
		ID_ISTAT_COMUNE = iD_ISTAT_COMUNE;
	}
	public String getDESCR_COMUNE() {
		return DESCR_COMUNE;
	}
	public void setDESCR_COMUNE(String dESCR_COMUNE) {
		DESCR_COMUNE = dESCR_COMUNE;
	}
	public String getDESC_INDIRIZZO() {
		return DESC_INDIRIZZO;
	}
	public void setDESC_INDIRIZZO(String dESC_INDIRIZZO) {
		DESC_INDIRIZZO = dESC_INDIRIZZO;
	}
	public String getDESC_CIVICO() {
		return DESC_CIVICO;
	}
	public void setDESC_CIVICO(String dESC_CIVICO) {
		DESC_CIVICO = dESC_CIVICO;
	}

 }
