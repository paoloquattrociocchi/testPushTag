
package it.giustizia.smp.webservice.server.polizia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiSoggettoFisicoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiSoggettoFisicoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NUME_PROG" type="{http://webservice.smp.giustizia.it/basetypes}IDNumProgType"/>
 *         &lt;element name="ID_RUOLO">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;length value="1"/>
 *               &lt;enumeration value="D"/>
 *               &lt;enumeration value="T"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ID_PERSONA_F" type="{http://webservice.smp.giustizia.it/basetypes}IDSoggettoType"/>
 *         &lt;element name="ID_TIPO_PREV">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;length value="1"/>
 *               &lt;enumeration value="1"/>
 *               &lt;enumeration value="2"/>
 *               &lt;enumeration value="3"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ID_IPOTESI_MIS">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;length value="1"/>
 *               &lt;enumeration value="P"/>
 *               &lt;enumeration value="T"/>
 *               &lt;enumeration value="PT"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ID_NORMATIVA_RIF" type="{http://webservice.smp.giustizia.it/basetypes}IDTipologicaGenericoType"/>
 *         &lt;element name="DESCR_COGNOME" type="{http://webservice.smp.giustizia.it/basetypes}CognomeType"/>
 *         &lt;element name="DESCR_NOME" type="{http://webservice.smp.giustizia.it/basetypes}NomeType"/>
 *         &lt;element name="DATA_NASCITA" type="{http://webservice.smp.giustizia.it/basetypes}Data"/>
 *         &lt;element name="ID_ISTAT_STATO_NAS" type="{http://webservice.smp.giustizia.it/basetypes}CodStatoIstatType"/>
 *         &lt;element name="ID_ISTAT_COMUNE_NAS" type="{http://webservice.smp.giustizia.it/basetypes}CodComuneIstatType"/>
 *         &lt;element name="DESCR_COMUNE_NAS" type="{http://webservice.smp.giustizia.it/basetypes}ComuneType"/>
 *         &lt;element name="FLG_SESSO">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;length value="1"/>
 *               &lt;enumeration value="M"/>
 *               &lt;enumeration value="F"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="DESCR_CODICE_FISC" type="{http://webservice.smp.giustizia.it/basetypes}alfanum16"/>
 *         &lt;element name="ID_PROFESSIONE" type="{http://webservice.smp.giustizia.it/basetypes}IDTipologicaGenericoType"/>
 *         &lt;element name="DESCR_CUI" type="{http://webservice.smp.giustizia.it/basetypes}CodiceCUIType"/>
 *         &lt;element name="ID_CONDIZIONE_GIUR" type="{http://webservice.smp.giustizia.it/basetypes}IDTipologicaGenericoType"/>
 *         &lt;element name="ID_ISTAT_STATO_RES" type="{http://webservice.smp.giustizia.it/basetypes}CodStatoIstatType"/>
 *         &lt;element name="ID_ISTAT_COMUNE_RES" type="{http://webservice.smp.giustizia.it/basetypes}CodComuneIstatType"/>
 *         &lt;element name="DESCR_COMUNE_RES" type="{http://webservice.smp.giustizia.it/basetypes}ComuneType"/>
 *         &lt;element name="DESCR_INDIRIZZO_RES" type="{http://webservice.smp.giustizia.it/basetypes}IndirizzoType"/>
 *         &lt;element name="DESCR_CIVICO_RES" type="{http://webservice.smp.giustizia.it/basetypes}CivicoType"/>
 *         &lt;element name="ID_ISTAT_STATO_DOM" type="{http://webservice.smp.giustizia.it/basetypes}CodStatoIstatType"/>
 *         &lt;element name="ID_ISTAT_COMUNE_DOM" type="{http://webservice.smp.giustizia.it/basetypes}CodComuneIstatType"/>
 *         &lt;element name="DESCR_COMUNE_DOM" type="{http://webservice.smp.giustizia.it/basetypes}ComuneType"/>
 *         &lt;element name="DESCR_INDIRIZZO_DOM" type="{http://webservice.smp.giustizia.it/basetypes}IndirizzoType"/>
 *         &lt;element name="DESCR_CIVICO_DOM" type="{http://webservice.smp.giustizia.it/basetypes}CivicoType"/>
 *         &lt;element name="ID_ISTAT_STATO_DIM" type="{http://webservice.smp.giustizia.it/basetypes}CodStatoIstatType"/>
 *         &lt;element name="ID_ISTAT_COMUNE_DIM" type="{http://webservice.smp.giustizia.it/basetypes}CodComuneIstatType"/>
 *         &lt;element name="DESCR_COMUNE_DIM" type="{http://webservice.smp.giustizia.it/basetypes}ComuneType"/>
 *         &lt;element name="DESCR_INDIRIZZO_DIM" type="{http://webservice.smp.giustizia.it/basetypes}IndirizzoType"/>
 *         &lt;element name="DESCR_CIVICO_DIM" type="{http://webservice.smp.giustizia.it/basetypes}CivicoType"/>
 *         &lt;element name="ID_ISTAT_STATO_ELE" type="{http://webservice.smp.giustizia.it/basetypes}CodStatoIstatType"/>
 *         &lt;element name="ID_ISTAT_COMUNE_ELE" type="{http://webservice.smp.giustizia.it/basetypes}CodComuneIstatType"/>
 *         &lt;element name="DESCR_COMUNE_ELE" type="{http://webservice.smp.giustizia.it/basetypes}ComuneType"/>
 *         &lt;element name="DESCR_INDIRIZZO_ELE" type="{http://webservice.smp.giustizia.it/basetypes}IndirizzoType"/>
 *         &lt;element name="DESCR_CIVICO_ELE" type="{http://webservice.smp.giustizia.it/basetypes}CivicoType"/>
 *         &lt;element name="ID_ISTAT_STATO_MORTE" type="{http://webservice.smp.giustizia.it/basetypes}CodStatoIstatType"/>
 *         &lt;element name="ID_ISTAT_COMUNE_MORTE" type="{http://webservice.smp.giustizia.it/basetypes}CodComuneIstatType"/>
 *         &lt;element name="DESCR_COMUNE_MORTE" type="{http://webservice.smp.giustizia.it/basetypes}ComuneType"/>
 *         &lt;element name="DATA_MORTE" type="{http://webservice.smp.giustizia.it/basetypes}Data"/>
 *         &lt;element name="DESCR_COGNOME_PADR" type="{http://webservice.smp.giustizia.it/basetypes}CognomeType"/>
 *         &lt;element name="DESCR_NOME_PADR" type="{http://webservice.smp.giustizia.it/basetypes}NomeType"/>
 *         &lt;element name="DESCR_COGNOME_MADR" type="{http://webservice.smp.giustizia.it/basetypes}CognomeType"/>
 *         &lt;element name="DESCR_NOME_MADR" type="{http://webservice.smp.giustizia.it/basetypes}NomeType"/>
 *         &lt;element name="DESCR_NOTE" type="{http://webservice.smp.giustizia.it/basetypes}Desc120Type"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiSoggettoFisicoType", propOrder = {
    "numeprog",
    "idruolo",
    "idpersonaf",
    "idtipoprev",
    "idipotesimis",
    "idnormativarif",
    "descrcognome",
    "descrnome",
    "datanascita",
    "idistatstatonas",
    "idistatcomunenas",
    "descrcomunenas",
    "flgsesso",
    "descrcodicefisc",
    "idprofessione",
    "descrcui",
    "idcondizionegiur",
    "idistatstatores",
    "idistatcomuneres",
    "descrcomuneres",
    "descrindirizzores",
    "descrcivicores",
    "idistatstatodom",
    "idistatcomunedom",
    "descrcomunedom",
    "descrindirizzodom",
    "descrcivicodom",
    "idistatstatodim",
    "idistatcomunedim",
    "descrcomunedim",
    "descrindirizzodim",
    "descrcivicodim",
    "idistatstatoele",
    "idistatcomuneele",
    "descrcomuneele",
    "descrindirizzoele",
    "descrcivicoele",
    "idistatstatomorte",
    "idistatcomunemorte",
    "descrcomunemorte",
    "datamorte",
    "descrcognomepadr",
    "descrnomepadr",
    "descrcognomemadr",
    "descrnomemadr",
    "descrnote"
})
public class DatiSoggettoFisicoType {

    @XmlElement(name = "NUME_PROG", required = true)
    protected String NUME_PROG;
    @XmlElement(name = "ID_RUOLO", required = true)
    protected String ID_RUOLO;
    @XmlElement(name = "ID_PERSONA_F", required = true, nillable = true)
    protected String ID_PERSONA_F;
    @XmlElement(name = "ID_TIPO_PREV", required = true)
    protected String ID_TIPO_PREV;
    @XmlElement(name = "ID_IPOTESI_MIS", required = true)
    protected String ID_IPOTESI_MIS;
    @XmlElement(name = "ID_NORMATIVA_RIF", required = true)
    protected String ID_NORMATIVA_RIF;
    @XmlElement(name = "DESCR_COGNOME", required = true)
    protected String DESCR_COGNOME;
    @XmlElement(name = "DESCR_NOME", required = true)
    protected String DESCR_NOME;
    @XmlElement(name = "DATA_NASCITA", required = true, nillable = true)
    protected String DATA_NASCITA;
    @XmlElement(name = "ID_ISTAT_STATO_NAS", required = true, nillable = true)
    protected String ID_ISTAT_STATO_NAS;
    @XmlElement(name = "ID_ISTAT_COMUNE_NAS", required = true, nillable = true)
    protected String ID_ISTAT_COMUNE_NAS;
    @XmlElement(name = "DESCR_COMUNE_NAS", required = true, nillable = true)
    protected String DESCR_COMUNE_NAS;
    @XmlElement(name = "FLG_SESSO", required = true, nillable = true)
    protected String FLG_SESSO;
    @XmlElement(name = "DESCR_CODICE_FISC", required = true, nillable = true)
    protected String DESCR_CODICE_FISC;
    @XmlElement(name = "ID_PROFESSIONE", required = true, nillable = true)
    protected String ID_PROFESSIONE;
    @XmlElement(name = "DESCR_CUI", required = true, nillable = true)
    protected String DESCR_CUI;
    @XmlElement(name = "ID_CONDIZIONE_GIUR", required = true)
    protected String ID_CONDIZIONE_GIUR;
    @XmlElement(name = "ID_ISTAT_STATO_RES", required = true, nillable = true)
    protected String ID_ISTAT_STATO_RES;
    @XmlElement(name = "ID_ISTAT_COMUNE_RES", required = true, nillable = true)
    protected String ID_ISTAT_COMUNE_RES;
    @XmlElement(name = "DESCR_COMUNE_RES", required = true, nillable = true)
    protected String DESCR_COMUNE_RES;
    @XmlElement(name = "DESCR_INDIRIZZO_RES", required = true, nillable = true)
    protected String DESCR_INDIRIZZO_RES;
    @XmlElement(name = "DESCR_CIVICO_RES", required = true, nillable = true)
    protected String DESCR_CIVICO_RES;
    @XmlElement(name = "ID_ISTAT_STATO_DOM", required = true)
    protected String ID_ISTAT_STATO_DOM;
    @XmlElement(name = "ID_ISTAT_COMUNE_DOM", required = true)
    protected String ID_ISTAT_COMUNE_DOM;
    @XmlElement(name = "DESCR_COMUNE_DOM", required = true)
    protected String DESCR_COMUNE_DOM;
    @XmlElement(name = "DESCR_INDIRIZZO_DOM", required = true)
    protected String DESCR_INDIRIZZO_DOM;
    @XmlElement(name = "DESCR_CIVICO_DOM", required = true)
    protected String DESCR_CIVICO_DOM;
    @XmlElement(name = "ID_ISTAT_STATO_DIM", required = true, nillable = true)
    protected String ID_ISTAT_STATO_DIM;
    @XmlElement(name = "ID_ISTAT_COMUNE_DIM", required = true, nillable = true)
    protected String ID_ISTAT_COMUNE_DIM;
    @XmlElement(name = "DESCR_COMUNE_DIM", required = true, nillable = true)
    protected String DESCR_COMUNE_DIM;
    @XmlElement(name = "DESCR_INDIRIZZO_DIM", required = true, nillable = true)
    protected String DESCR_INDIRIZZO_DIM;
    @XmlElement(name = "DESCR_CIVICO_DIM", required = true, nillable = true)
    protected String DESCR_CIVICO_DIM;
    @XmlElement(name = "ID_ISTAT_STATO_ELE", required = true, nillable = true)
    protected String ID_ISTAT_STATO_ELE;
    @XmlElement(name = "ID_ISTAT_COMUNE_ELE", required = true, nillable = true)
    protected String ID_ISTAT_COMUNE_ELE;
    @XmlElement(name = "DESCR_COMUNE_ELE", required = true, nillable = true)
    protected String DESCR_COMUNE_ELE;
    @XmlElement(name = "DESCR_INDIRIZZO_ELE", required = true, nillable = true)
    protected String DESCR_INDIRIZZO_ELE;
    @XmlElement(name = "DESCR_CIVICO_ELE", required = true, nillable = true)
    protected String DESCR_CIVICO_ELE;
    @XmlElement(name = "ID_ISTAT_STATO_MORTE", required = true, nillable = true)
    protected String ID_ISTAT_STATO_MORTE;
    @XmlElement(name = "ID_ISTAT_COMUNE_MORTE", required = true, nillable = true)
    protected String ID_ISTAT_COMUNE_MORTE;
    @XmlElement(name = "DESCR_COMUNE_MORTE", required = true, nillable = true)
    protected String DESCR_COMUNE_MORTE;
    @XmlElement(name = "DATA_MORTE", required = true, nillable = true)
    protected String DATA_MORTE;
    @XmlElement(name = "DESCR_COGNOME_PADR", required = true, nillable = true)
    protected String DESCR_COGNOME_PADR;
    @XmlElement(name = "DESCR_NOME_PADR", required = true, nillable = true)
    protected String DESCR_NOME_PADR;
    @XmlElement(name = "DESCR_COGNOME_MADR", required = true, nillable = true)
    protected String DESCR_COGNOME_MADR;
    @XmlElement(name = "DESCR_NOME_MADR", required = true, nillable = true)
    protected String DESCR_NOME_MADR;
    @XmlElement(name = "DESCR_NOTE", required = true, nillable = true)
    protected String DESCR_NOTE;
    
	public String getNUME_PROG() {
		return NUME_PROG;
	}
	public void setNUME_PROG(String nUME_PROG) {
		NUME_PROG = nUME_PROG;
	}
	public String getID_RUOLO() {
		return ID_RUOLO;
	}
	public void setID_RUOLO(String iD_RUOLO) {
		ID_RUOLO = iD_RUOLO;
	}
	public String getID_PERSONA_F() {
		return ID_PERSONA_F;
	}
	public void setID_PERSONA_F(String iD_PERSONA_F) {
		ID_PERSONA_F = iD_PERSONA_F;
	}
	public String getID_TIPO_PREV() {
		return ID_TIPO_PREV;
	}
	public void setID_TIPO_PREV(String iD_TIPO_PREV) {
		ID_TIPO_PREV = iD_TIPO_PREV;
	}
	public String getID_IPOTESI_MIS() {
		return ID_IPOTESI_MIS;
	}
	public void setID_IPOTESI_MIS(String iD_IPOTESI_MIS) {
		ID_IPOTESI_MIS = iD_IPOTESI_MIS;
	}
	public String getID_NORMATIVA_RIF() {
		return ID_NORMATIVA_RIF;
	}
	public void setID_NORMATIVA_RIF(String iD_NORMATIVA_RIF) {
		ID_NORMATIVA_RIF = iD_NORMATIVA_RIF;
	}
	public String getDESCR_COGNOME() {
		return DESCR_COGNOME;
	}
	public void setDESCR_COGNOME(String dESCR_COGNOME) {
		DESCR_COGNOME = dESCR_COGNOME;
	}
	public String getDESCR_NOME() {
		return DESCR_NOME;
	}
	public void setDESCR_NOME(String dESCR_NOME) {
		DESCR_NOME = dESCR_NOME;
	}
	public String getDATA_NASCITA() {
		return DATA_NASCITA;
	}
	public void setDATA_NASCITA(String dATA_NASCITA) {
		DATA_NASCITA = dATA_NASCITA;
	}
	public String getID_ISTAT_STATO_NAS() {
		return ID_ISTAT_STATO_NAS;
	}
	public void setID_ISTAT_STATO_NAS(String iD_ISTAT_STATO_NAS) {
		ID_ISTAT_STATO_NAS = iD_ISTAT_STATO_NAS;
	}
	public String getID_ISTAT_COMUNE_NAS() {
		return ID_ISTAT_COMUNE_NAS;
	}
	public void setID_ISTAT_COMUNE_NAS(String iD_ISTAT_COMUNE_NAS) {
		ID_ISTAT_COMUNE_NAS = iD_ISTAT_COMUNE_NAS;
	}
	public String getDESCR_COMUNE_NAS() {
		return DESCR_COMUNE_NAS;
	}
	public void setDESCR_COMUNE_NAS(String dESCR_COMUNE_NAS) {
		DESCR_COMUNE_NAS = dESCR_COMUNE_NAS;
	}
	public String getFLG_SESSO() {
		return FLG_SESSO;
	}
	public void setFLG_SESSO(String fLG_SESSO) {
		FLG_SESSO = fLG_SESSO;
	}
	public String getDESCR_CODICE_FISC() {
		return DESCR_CODICE_FISC;
	}
	public void setDESCR_CODICE_FISC(String dESCR_CODICE_FISC) {
		DESCR_CODICE_FISC = dESCR_CODICE_FISC;
	}
	public String getID_PROFESSIONE() {
		return ID_PROFESSIONE;
	}
	public void setID_PROFESSIONE(String iD_PROFESSIONE) {
		ID_PROFESSIONE = iD_PROFESSIONE;
	}
	public String getDESCR_CUI() {
		return DESCR_CUI;
	}
	public void setDESCR_CUI(String dESCR_CUI) {
		DESCR_CUI = dESCR_CUI;
	}
	public String getID_CONDIZIONE_GIUR() {
		return ID_CONDIZIONE_GIUR;
	}
	public void setID_CONDIZIONE_GIUR(String iD_CONDIZIONE_GIUR) {
		ID_CONDIZIONE_GIUR = iD_CONDIZIONE_GIUR;
	}
	public String getID_ISTAT_STATO_RES() {
		return ID_ISTAT_STATO_RES;
	}
	public void setID_ISTAT_STATO_RES(String iD_ISTAT_STATO_RES) {
		ID_ISTAT_STATO_RES = iD_ISTAT_STATO_RES;
	}
	public String getID_ISTAT_COMUNE_RES() {
		return ID_ISTAT_COMUNE_RES;
	}
	public void setID_ISTAT_COMUNE_RES(String iD_ISTAT_COMUNE_RES) {
		ID_ISTAT_COMUNE_RES = iD_ISTAT_COMUNE_RES;
	}
	public String getDESCR_COMUNE_RES() {
		return DESCR_COMUNE_RES;
	}
	public void setDESCR_COMUNE_RES(String dESCR_COMUNE_RES) {
		DESCR_COMUNE_RES = dESCR_COMUNE_RES;
	}
	public String getDESCR_INDIRIZZO_RES() {
		return DESCR_INDIRIZZO_RES;
	}
	public void setDESCR_INDIRIZZO_RES(String dESCR_INDIRIZZO_RES) {
		DESCR_INDIRIZZO_RES = dESCR_INDIRIZZO_RES;
	}
	public String getDESCR_CIVICO_RES() {
		return DESCR_CIVICO_RES;
	}
	public void setDESCR_CIVICO_RES(String dESCR_CIVICO_RES) {
		DESCR_CIVICO_RES = dESCR_CIVICO_RES;
	}
	public String getID_ISTAT_STATO_DOM() {
		return ID_ISTAT_STATO_DOM;
	}
	public void setID_ISTAT_STATO_DOM(String iD_ISTAT_STATO_DOM) {
		ID_ISTAT_STATO_DOM = iD_ISTAT_STATO_DOM;
	}
	public String getID_ISTAT_COMUNE_DOM() {
		return ID_ISTAT_COMUNE_DOM;
	}
	public void setID_ISTAT_COMUNE_DOM(String iD_ISTAT_COMUNE_DOM) {
		ID_ISTAT_COMUNE_DOM = iD_ISTAT_COMUNE_DOM;
	}
	public String getDESCR_COMUNE_DOM() {
		return DESCR_COMUNE_DOM;
	}
	public void setDESCR_COMUNE_DOM(String dESCR_COMUNE_DOM) {
		DESCR_COMUNE_DOM = dESCR_COMUNE_DOM;
	}
	public String getDESCR_INDIRIZZO_DOM() {
		return DESCR_INDIRIZZO_DOM;
	}
	public void setDESCR_INDIRIZZO_DOM(String dESCR_INDIRIZZO_DOM) {
		DESCR_INDIRIZZO_DOM = dESCR_INDIRIZZO_DOM;
	}
	public String getDESCR_CIVICO_DOM() {
		return DESCR_CIVICO_DOM;
	}
	public void setDESCR_CIVICO_DOM(String dESCR_CIVICO_DOM) {
		DESCR_CIVICO_DOM = dESCR_CIVICO_DOM;
	}
	public String getID_ISTAT_STATO_DIM() {
		return ID_ISTAT_STATO_DIM;
	}
	public void setID_ISTAT_STATO_DIM(String iD_ISTAT_STATO_DIM) {
		ID_ISTAT_STATO_DIM = iD_ISTAT_STATO_DIM;
	}
	public String getID_ISTAT_COMUNE_DIM() {
		return ID_ISTAT_COMUNE_DIM;
	}
	public void setID_ISTAT_COMUNE_DIM(String iD_ISTAT_COMUNE_DIM) {
		ID_ISTAT_COMUNE_DIM = iD_ISTAT_COMUNE_DIM;
	}
	public String getDESCR_COMUNE_DIM() {
		return DESCR_COMUNE_DIM;
	}
	public void setDESCR_COMUNE_DIM(String dESCR_COMUNE_DIM) {
		DESCR_COMUNE_DIM = dESCR_COMUNE_DIM;
	}
	public String getDESCR_INDIRIZZO_DIM() {
		return DESCR_INDIRIZZO_DIM;
	}
	public void setDESCR_INDIRIZZO_DIM(String dESCR_INDIRIZZO_DIM) {
		DESCR_INDIRIZZO_DIM = dESCR_INDIRIZZO_DIM;
	}
	public String getDESCR_CIVICO_DIM() {
		return DESCR_CIVICO_DIM;
	}
	public void setDESCR_CIVICO_DIM(String dESCR_CIVICO_DIM) {
		DESCR_CIVICO_DIM = dESCR_CIVICO_DIM;
	}
	public String getID_ISTAT_STATO_ELE() {
		return ID_ISTAT_STATO_ELE;
	}
	public void setID_ISTAT_STATO_ELE(String iD_ISTAT_STATO_ELE) {
		ID_ISTAT_STATO_ELE = iD_ISTAT_STATO_ELE;
	}
	public String getID_ISTAT_COMUNE_ELE() {
		return ID_ISTAT_COMUNE_ELE;
	}
	public void setID_ISTAT_COMUNE_ELE(String iD_ISTAT_COMUNE_ELE) {
		ID_ISTAT_COMUNE_ELE = iD_ISTAT_COMUNE_ELE;
	}
	public String getDESCR_COMUNE_ELE() {
		return DESCR_COMUNE_ELE;
	}
	public void setDESCR_COMUNE_ELE(String dESCR_COMUNE_ELE) {
		DESCR_COMUNE_ELE = dESCR_COMUNE_ELE;
	}
	public String getDESCR_INDIRIZZO_ELE() {
		return DESCR_INDIRIZZO_ELE;
	}
	public void setDESCR_INDIRIZZO_ELE(String dESCR_INDIRIZZO_ELE) {
		DESCR_INDIRIZZO_ELE = dESCR_INDIRIZZO_ELE;
	}
	public String getDESCR_CIVICO_ELE() {
		return DESCR_CIVICO_ELE;
	}
	public void setDESCR_CIVICO_ELE(String dESCR_CIVICO_ELE) {
		DESCR_CIVICO_ELE = dESCR_CIVICO_ELE;
	}
	public String getID_ISTAT_STATO_MORTE() {
		return ID_ISTAT_STATO_MORTE;
	}
	public void setID_ISTAT_STATO_MORTE(String iD_ISTAT_STATO_MORTE) {
		ID_ISTAT_STATO_MORTE = iD_ISTAT_STATO_MORTE;
	}
	public String getID_ISTAT_COMUNE_MORTE() {
		return ID_ISTAT_COMUNE_MORTE;
	}
	public void setID_ISTAT_COMUNE_MORTE(String iD_ISTAT_COMUNE_MORTE) {
		ID_ISTAT_COMUNE_MORTE = iD_ISTAT_COMUNE_MORTE;
	}
	public String getDESCR_COMUNE_MORTE() {
		return DESCR_COMUNE_MORTE;
	}
	public void setDESCR_COMUNE_MORTE(String dESCR_COMUNE_MORTE) {
		DESCR_COMUNE_MORTE = dESCR_COMUNE_MORTE;
	}
	public String getDATA_MORTE() {
		return DATA_MORTE;
	}
	public void setDATA_MORTE(String dATA_MORTE) {
		DATA_MORTE = dATA_MORTE;
	}
	public String getDESCR_COGNOME_PADR() {
		return DESCR_COGNOME_PADR;
	}
	public void setDESCR_COGNOME_PADR(String dESCR_COGNOME_PADR) {
		DESCR_COGNOME_PADR = dESCR_COGNOME_PADR;
	}
	public String getDESCR_NOME_PADR() {
		return DESCR_NOME_PADR;
	}
	public void setDESCR_NOME_PADR(String dESCR_NOME_PADR) {
		DESCR_NOME_PADR = dESCR_NOME_PADR;
	}
	public String getDESCR_COGNOME_MADR() {
		return DESCR_COGNOME_MADR;
	}
	public void setDESCR_COGNOME_MADR(String dESCR_COGNOME_MADR) {
		DESCR_COGNOME_MADR = dESCR_COGNOME_MADR;
	}
	public String getDESCR_NOME_MADR() {
		return DESCR_NOME_MADR;
	}
	public void setDESCR_NOME_MADR(String dESCR_NOME_MADR) {
		DESCR_NOME_MADR = dESCR_NOME_MADR;
	}
	public String getDESCR_NOTE() {
		return DESCR_NOTE;
	}
	public void setDESCR_NOTE(String dESCR_NOTE) {
		DESCR_NOTE = dESCR_NOTE;
	}

}
