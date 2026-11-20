
package it.giustizia.smp.webservice.server.polizia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiSoggettoGiuridicoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiSoggettoGiuridicoType">
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
 *         &lt;element name="ID_PERSONA_G" type="{http://webservice.smp.giustizia.it/basetypes}IDSoggettoType"/>
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
 *         &lt;element name="ID_TIPO_SOCIETA" type="{http://webservice.smp.giustizia.it/basetypes}IDTipologicaGenericoType"/>
 *         &lt;element name="DESCR_SOCIALE" type="{http://webservice.smp.giustizia.it/basetypes}RagioneSocialeType"/>
 *         &lt;element name="DESCR_NUM_CCIAA" type="{http://webservice.smp.giustizia.it/basetypes}alfanumMax60"/>
 *         &lt;element name="DESCR_PARTITA_IVA" type="{http://webservice.smp.giustizia.it/basetypes}numericoMax16"/>
 *         &lt;element name="ID_ISTAT_STATO" type="{http://webservice.smp.giustizia.it/basetypes}CodStatoIstatType"/>
 *         &lt;element name="ID_ISTAT_COMUNE" type="{http://webservice.smp.giustizia.it/basetypes}CodComuneIstatType"/>
 *         &lt;element name="DESCR_COMUNE" type="{http://webservice.smp.giustizia.it/basetypes}ComuneType"/>
 *         &lt;element name="DESCR_INDIRIZZO" type="{http://webservice.smp.giustizia.it/basetypes}IndirizzoType"/>
 *         &lt;element name="DESCR_CIVICO" type="{http://webservice.smp.giustizia.it/basetypes}CivicoType"/>
 *         &lt;element name="DESCR_RAPPR_SOC" type="{http://webservice.smp.giustizia.it/basetypes}Desc200Type"/>
 *         &lt;element name="DESCR_AMMINISTR" type="{http://webservice.smp.giustizia.it/basetypes}Desc200Type"/>
 *         &lt;element name="IMPO_CAPITALE_SOC" type="{http://webservice.smp.giustizia.it/basetypes}ImportoType"/>
 *         &lt;element name="ID_SETTORE_ATT" type="{http://webservice.smp.giustizia.it/basetypes}IDTipologicaGenericoType"/>
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
@XmlType(name = "DatiSoggettoGiuridicoType", propOrder = {
    "numeprog",
    "idruolo",
    "idpersonag",
    "idtipoprev",
    "idipotesimis",
    "idnormativarif",
    "idtiposocieta",
    "descrsociale",
    "descrnumcciaa",
    "descrpartitaiva",
    "idistatstato",
    "idistatcomune",
    "descrcomune",
    "descrindirizzo",
    "descrcivico",
    "descrrapprsoc",
    "descramministr",
    "impocapitalesoc",
    "idsettoreatt",
    "descrnote"
})
public class DatiSoggettoGiuridicoType {

    @XmlElement(name = "NUME_PROG", required = true)
    protected String NUME_PROG;
    @XmlElement(name = "ID_RUOLO", required = true)
    protected String ID_RUOLO;
    @XmlElement(name = "ID_PERSONA_G", required = true, nillable = true)
    protected String ID_PERSONA_G;
    @XmlElement(name = "ID_TIPO_PREV", required = true)
    protected String ID_TIPO_PREV;
    @XmlElement(name = "ID_IPOTESI_MIS", required = true)
    protected String ID_IPOTESI_MIS;
    @XmlElement(name = "ID_NORMATIVA_RIF", required = true)
    protected String ID_NORMATIVA_RIF;
    @XmlElement(name = "ID_TIPO_SOCIETA", required = true)
    protected String ID_TIPO_SOCIETA;
    @XmlElement(name = "DESCR_SOCIALE", required = true)
    protected String DESCR_SOCIALE;
    @XmlElement(name = "DESCR_NUM_CCIAA", required = true, nillable = true)
    protected String DESCR_NUM_CCIAA;
    @XmlElement(name = "DESCR_PARTITA_IVA", required = true, nillable = true)
    protected String DESCR_PARTITA_IVA;
    @XmlElement(name = "ID_ISTAT_STATO", required = true, nillable = true)
    protected String ID_ISTAT_STATO;
    @XmlElement(name = "ID_ISTAT_COMUNE", required = true, nillable = true)
    protected String ID_ISTAT_COMUNE;
    @XmlElement(name = "DESCR_COMUNE", required = true, nillable = true)
    protected String DESCR_COMUNE;
    @XmlElement(name = "DESCR_INDIRIZZO", required = true, nillable = true)
    protected String DESCR_INDIRIZZO;
    @XmlElement(name = "DESCR_CIVICO", required = true, nillable = true)
    protected String DESCR_CIVICO;
    @XmlElement(name = "DESCR_RAPPR_SOC", required = true, nillable = true)
    protected String DESCR_RAPPR_SOC;
    @XmlElement(name = "DESCR_AMMINISTR", required = true, nillable = true)
    protected String DESCR_AMMINISTR;
    @XmlElement(name = "IMPO_CAPITALE_SOC", required = true, nillable = true)
    protected String IMPO_CAPITALE_SOC;
    @XmlElement(name = "ID_SETTORE_ATT", required = true, nillable = true)
    protected String ID_SETTORE_ATT;
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
	public String getID_PERSONA_G() {
		return ID_PERSONA_G;
	}
	public void setID_PERSONA_G(String iD_PERSONA_G) {
		ID_PERSONA_G = iD_PERSONA_G;
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
	public String getID_TIPO_SOCIETA() {
		return ID_TIPO_SOCIETA;
	}
	public void setID_TIPO_SOCIETA(String iD_TIPO_SOCIETA) {
		ID_TIPO_SOCIETA = iD_TIPO_SOCIETA;
	}
	public String getDESCR_SOCIALE() {
		return DESCR_SOCIALE;
	}
	public void setDESCR_SOCIALE(String dESCR_SOCIALE) {
		DESCR_SOCIALE = dESCR_SOCIALE;
	}
	public String getDESCR_NUM_CCIAA() {
		return DESCR_NUM_CCIAA;
	}
	public void setDESCR_NUM_CCIAA(String dESCR_NUM_CCIAA) {
		DESCR_NUM_CCIAA = dESCR_NUM_CCIAA;
	}
	public String getDESCR_PARTITA_IVA() {
		return DESCR_PARTITA_IVA;
	}
	public void setDESCR_PARTITA_IVA(String dESCR_PARTITA_IVA) {
		DESCR_PARTITA_IVA = dESCR_PARTITA_IVA;
	}
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
	public String getDESCR_INDIRIZZO() {
		return DESCR_INDIRIZZO;
	}
	public void setDESCR_INDIRIZZO(String dESCR_INDIRIZZO) {
		DESCR_INDIRIZZO = dESCR_INDIRIZZO;
	}
	public String getDESCR_CIVICO() {
		return DESCR_CIVICO;
	}
	public void setDESCR_CIVICO(String dESCR_CIVICO) {
		DESCR_CIVICO = dESCR_CIVICO;
	}
	public String getDESCR_RAPPR_SOC() {
		return DESCR_RAPPR_SOC;
	}
	public void setDESCR_RAPPR_SOC(String dESCR_RAPPR_SOC) {
		DESCR_RAPPR_SOC = dESCR_RAPPR_SOC;
	}
	public String getDESCR_AMMINISTR() {
		return DESCR_AMMINISTR;
	}
	public void setDESCR_AMMINISTR(String dESCR_AMMINISTR) {
		DESCR_AMMINISTR = dESCR_AMMINISTR;
	}
	public String getIMPO_CAPITALE_SOC() {
		return IMPO_CAPITALE_SOC;
	}
	public void setIMPO_CAPITALE_SOC(String iMPO_CAPITALE_SOC) {
		IMPO_CAPITALE_SOC = iMPO_CAPITALE_SOC;
	}
	public String getID_SETTORE_ATT() {
		return ID_SETTORE_ATT;
	}
	public void setID_SETTORE_ATT(String iD_SETTORE_ATT) {
		ID_SETTORE_ATT = iD_SETTORE_ATT;
	}
	public String getDESCR_NOTE() {
		return DESCR_NOTE;
	}
	public void setDESCR_NOTE(String dESCR_NOTE) {
		DESCR_NOTE = dESCR_NOTE;
	}

}
