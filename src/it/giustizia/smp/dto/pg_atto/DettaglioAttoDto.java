package it.giustizia.smp.dto.pg_atto;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Date;

public class DettaglioAttoDto {

//		--T_ATTO---
	   private BigDecimal CODI_K_ID_ATTO; 
//	   private String --CODI_K_ID_UFF_OWNER; 
	   private BigDecimal CODI_FK_ID_TIPO_ATTO; 
	   private BigDecimal CODI_FK_ID_CAT_ATTO; 
	   private Date DATA_REGISTRAZIONE; 
	   private Date DATA_EMISSIONE; 
	   private Date DATA_DEPOSITO; 
	   private Date DATA_RICEZIONE; 
//	   private String --DATA_DEPOSITO_RIC; 
//	   private String --DATA_INVIO; 
	   private String DESC_NOTE; 
	   private BigDecimal CODI_ID_PROC_EMISS; 
	   private String DESC_TIPO_DOCUMENTO; 
	   private String DESC_NOME_DOCUMENTO; 
	   private Blob FILE_DOCUMENTO; 
	   private BigDecimal NUME_DOCUMENTO_PDOC; 
//	   private String --DESC_VISIBILITA; 
//	   private String --DESC_NOTE_PREC; 
//	   private String --DESC_UTENTE; 
//	   private String --DATA_ULT_AGG;
	   private String DESC_TIPO_UFFICIO;
	   private BigDecimal CODI_K_ID_UFFICIO;
	   private String DESC_SEDE_UFFICIO;
	   private BigDecimal CODI_K_ID_COMUNE;
//	   private BigDecimal CODI_FK_ID_ISTAT_PROV;
	   private String CODI_FK_ID_ISTAT_PROV;
	   private BigDecimal CODI_K_ID_TIPO_UFF;
	   private String DESC_TIPO_ATTO;
	   private String DESC_CAT_ATTO;
	   private String DESC_STATO_ATTO;
	   private BigDecimal CODI_K_ID_STATO_ATTO;
	   private Character FLG_FIRMA;
//	   private String --T_ISTANZA
	   private BigDecimal CODI_FK_ID_UFF_MITT; 
	   private String DESC_RICHIESTA; 
	   private String DESC_NUME_RIF; 
	   private String DESC_TIPO_PROP; 
	   private String DESC_COGN_PROP; 
	   private String DESC_NOME_PROP; 
	   private String DESC_UTENTE_ISTANZA; 
	   private BigDecimal CODI_FK_ID_MOD;
	   private String DESC_MOD_TRASMISSIONE;
//	   private String --DATA_ULT_AGG;   
//	   private String --T_VERBALE
	   private BigDecimal CODI_FK_ID_UDIENZA_VERBALE; 
	   private String DESC_COGN_FUNZ; 
	   private String DESC_NOME_FUNZ; 
	   private String DESC_UTENTE_VERBALE;
//	  -private String -- DATA_ULT_AGG
//	   T_DECRETO
	   private BigDecimal CODI_FK_ID_UDIENZA_DECRETO;
	   private String NUME_ANNO_ATTO;
	   private String NUME_ATTO;
	   
	   //T_ATTO_GENERICO
	   private Character FLG_SPED_RIC;
	   private String DESC_ORGANO;
	   private String DESC_SEDE_ORGANO;
	   private BigDecimal ID_FONTE_ATTO_GEN;
	   
	   
	public BigDecimal getCODI_K_ID_ATTO() {
		return CODI_K_ID_ATTO;
	}
	public void setCODI_K_ID_ATTO(BigDecimal cODI_K_ID_ATTO) {
		CODI_K_ID_ATTO = cODI_K_ID_ATTO;
	}
	public BigDecimal getCODI_FK_ID_TIPO_ATTO() {
		return CODI_FK_ID_TIPO_ATTO;
	}
	public void setCODI_FK_ID_TIPO_ATTO(BigDecimal cODI_FK_ID_TIPO_ATTO) {
		CODI_FK_ID_TIPO_ATTO = cODI_FK_ID_TIPO_ATTO;
	}
	public BigDecimal getCODI_FK_ID_CAT_ATTO() {
		return CODI_FK_ID_CAT_ATTO;
	}
	public void setCODI_FK_ID_CAT_ATTO(BigDecimal cODI_FK_ID_CAT_ATTO) {
		CODI_FK_ID_CAT_ATTO = cODI_FK_ID_CAT_ATTO;
	}
	public Date getDATA_REGISTRAZIONE() {
		return DATA_REGISTRAZIONE;
	}
	public void setDATA_REGISTRAZIONE(Date dATA_REGISTRAZIONE) {
		DATA_REGISTRAZIONE = dATA_REGISTRAZIONE;
	}
	public Date getDATA_EMISSIONE() {
		return DATA_EMISSIONE;
	}
	public void setDATA_EMISSIONE(Date dATA_EMISSIONE) {
		DATA_EMISSIONE = dATA_EMISSIONE;
	}
	public Date getDATA_DEPOSITO() {
		return DATA_DEPOSITO;
	}
	public void setDATA_DEPOSITO(Date dATA_DEPOSITO) {
		DATA_DEPOSITO = dATA_DEPOSITO;
	}
	public Date getDATA_RICEZIONE() {
		return DATA_RICEZIONE;
	}
	public void setDATA_RICEZIONE(Date dATA_RICEZIONE) {
		DATA_RICEZIONE = dATA_RICEZIONE;
	}
	public String getDESC_NOTE() {
		return DESC_NOTE;
	}
	public void setDESC_NOTE(String dESC_NOTE) {
		DESC_NOTE = dESC_NOTE;
	}
	public BigDecimal getCODI_ID_PROC_EMISS() {
		return CODI_ID_PROC_EMISS;
	}
	public void setCODI_ID_PROC_EMISS(BigDecimal cODI_ID_PROC_EMISS) {
		CODI_ID_PROC_EMISS = cODI_ID_PROC_EMISS;
	}
	public String getDESC_TIPO_DOCUMENTO() {
		return DESC_TIPO_DOCUMENTO;
	}
	public void setDESC_TIPO_DOCUMENTO(String dESC_TIPO_DOCUMENTO) {
		DESC_TIPO_DOCUMENTO = dESC_TIPO_DOCUMENTO;
	}
	public Blob getFILE_DOCUMENTO() {
		return FILE_DOCUMENTO;
	}
	public void setFILE_DOCUMENTO(Blob fILE_DOCUMENTO) {
		FILE_DOCUMENTO = fILE_DOCUMENTO;
	}
	public String getDESC_TIPO_UFFICIO() {
		return DESC_TIPO_UFFICIO;
	}
	public void setDESC_TIPO_UFFICIO(String dESC_TIPO_UFFICIO) {
		DESC_TIPO_UFFICIO = dESC_TIPO_UFFICIO;
	}
	public BigDecimal getCODI_K_ID_UFFICIO() {
		return CODI_K_ID_UFFICIO;
	}
	public void setCODI_K_ID_UFFICIO(BigDecimal cODI_K_ID_UFFICIO) {
		CODI_K_ID_UFFICIO = cODI_K_ID_UFFICIO;
	}
	public String getDESC_SEDE_UFFICIO() {
		return DESC_SEDE_UFFICIO;
	}
	public void setDESC_SEDE_UFFICIO(String dESC_SEDE_UFFICIO) {
		DESC_SEDE_UFFICIO = dESC_SEDE_UFFICIO;
	}
	public BigDecimal getCODI_K_ID_COMUNE() {
		return CODI_K_ID_COMUNE;
	}
	public void setCODI_K_ID_COMUNE(BigDecimal cODI_K_ID_COMUNE) {
		CODI_K_ID_COMUNE = cODI_K_ID_COMUNE;
	}
	public String getDESC_TIPO_ATTO() {
		return DESC_TIPO_ATTO;
	}
	public void setDESC_TIPO_ATTO(String dESC_TIPO_ATTO) {
		DESC_TIPO_ATTO = dESC_TIPO_ATTO;
	}
	public String getDESC_CAT_ATTO() {
		return DESC_CAT_ATTO;
	}
	public void setDESC_CAT_ATTO(String dESC_CAT_ATTO) {
		DESC_CAT_ATTO = dESC_CAT_ATTO;
	}
	public String getDESC_STATO_ATTO() {
		return DESC_STATO_ATTO;
	}
	public void setDESC_STATO_ATTO(String dESC_STATO_ATTO) {
		DESC_STATO_ATTO = dESC_STATO_ATTO;
	}
	
	public BigDecimal getCODI_FK_ID_UFF_MITT() {
		return CODI_FK_ID_UFF_MITT;
	}
	public void setCODI_FK_ID_UFF_MITT(BigDecimal cODI_FK_ID_UFF_MITT) {
		CODI_FK_ID_UFF_MITT = cODI_FK_ID_UFF_MITT;
	}
	public String getDESC_RICHIESTA() {
		return DESC_RICHIESTA;
	}
	public void setDESC_RICHIESTA(String dESC_RICHIESTA) {
		DESC_RICHIESTA = dESC_RICHIESTA;
	}
	public String getDESC_TIPO_PROP() {
		return DESC_TIPO_PROP;
	}
	public void setDESC_TIPO_PROP(String dESC_TIPO_PROP) {
		DESC_TIPO_PROP = dESC_TIPO_PROP;
	}
	public String getDESC_COGN_PROP() {
		return DESC_COGN_PROP;
	}
	public void setDESC_COGN_PROP(String dESC_COGN_PROP) {
		DESC_COGN_PROP = dESC_COGN_PROP;
	}
	public String getDESC_NOME_PROP() {
		return DESC_NOME_PROP;
	}
	public void setDESC_NOME_PROP(String dESC_NOME_PROP) {
		DESC_NOME_PROP = dESC_NOME_PROP;
	}
	public String getDESC_UTENTE_ISTANZA() {
		return DESC_UTENTE_ISTANZA;
	}
	public void setDESC_UTENTE_ISTANZA(String dESC_UTENTE_ISTANZA) {
		DESC_UTENTE_ISTANZA = dESC_UTENTE_ISTANZA;
	}

	public String getDESC_COGN_FUNZ() {
		return DESC_COGN_FUNZ;
	}
	public void setDESC_COGN_FUNZ(String dESC_COGN_FUNZ) {
		DESC_COGN_FUNZ = dESC_COGN_FUNZ;
	}
	public String getDESC_NOME_FUNZ() {
		return DESC_NOME_FUNZ;
	}
	public void setDESC_NOME_FUNZ(String dESC_NOME_FUNZ) {
		DESC_NOME_FUNZ = dESC_NOME_FUNZ;
	}
	public String getDESC_UTENTE_VERBALE() {
		return DESC_UTENTE_VERBALE;
	}
	public void setDESC_UTENTE_VERBALE(String dESC_UTENTE_VERBALE) {
		DESC_UTENTE_VERBALE = dESC_UTENTE_VERBALE;
	}
	public String getDESC_NOME_DOCUMENTO() {
		return DESC_NOME_DOCUMENTO;
	}
	public void setDESC_NOME_DOCUMENTO(String dESC_NOME_DOCUMENTO) {
		DESC_NOME_DOCUMENTO = dESC_NOME_DOCUMENTO;
	}
	public BigDecimal getCODI_K_ID_TIPO_UFF() {
		return CODI_K_ID_TIPO_UFF;
	}
	public void setCODI_K_ID_TIPO_UFF(BigDecimal cODI_K_ID_TIPO_UFF) {
		CODI_K_ID_TIPO_UFF = cODI_K_ID_TIPO_UFF;
	}
	public String getCODI_FK_ID_ISTAT_PROV() {
		return CODI_FK_ID_ISTAT_PROV;
	}
	public void setCODI_FK_ID_ISTAT_PROV(String cODI_FK_ID_ISTAT_PROV) {
		CODI_FK_ID_ISTAT_PROV = cODI_FK_ID_ISTAT_PROV;
	}
	public BigDecimal getCODI_K_ID_STATO_ATTO() {
		return CODI_K_ID_STATO_ATTO;
	}
	public void setCODI_K_ID_STATO_ATTO(BigDecimal cODI_K_ID_STATO_ATTO) {
		CODI_K_ID_STATO_ATTO = cODI_K_ID_STATO_ATTO;
	}
	public BigDecimal getCODI_FK_ID_UDIENZA_VERBALE() {
		return CODI_FK_ID_UDIENZA_VERBALE;
	}
	public void setCODI_FK_ID_UDIENZA_VERBALE(BigDecimal cODI_FK_ID_UDIENZA_VERBALE) {
		CODI_FK_ID_UDIENZA_VERBALE = cODI_FK_ID_UDIENZA_VERBALE;
	}
	public BigDecimal getCODI_FK_ID_UDIENZA_DECRETO() {
		return CODI_FK_ID_UDIENZA_DECRETO;
	}
	public void setCODI_FK_ID_UDIENZA_DECRETO(BigDecimal cODI_FK_ID_UDIENZA_DECRETO) {
		CODI_FK_ID_UDIENZA_DECRETO = cODI_FK_ID_UDIENZA_DECRETO;
	}
	public BigDecimal getCODI_FK_ID_MOD() {
		return CODI_FK_ID_MOD;
	}
	public void setCODI_FK_ID_MOD(BigDecimal cODI_FK_ID_MOD) {
		CODI_FK_ID_MOD = cODI_FK_ID_MOD;
	}
	public String getDESC_MOD_TRASMISSIONE() {
		return DESC_MOD_TRASMISSIONE;
	}
	public void setDESC_MOD_TRASMISSIONE(String dESC_MOD_TRASMISSIONE) {
		DESC_MOD_TRASMISSIONE = dESC_MOD_TRASMISSIONE;
	}
	public BigDecimal getNUME_DOCUMENTO_PDOC() {
		return NUME_DOCUMENTO_PDOC;
	}
	public void setNUME_DOCUMENTO_PDOC(BigDecimal nUME_DOCUMENTO_PDOC) {
		NUME_DOCUMENTO_PDOC = nUME_DOCUMENTO_PDOC;
	}
	public Character getFLG_FIRMA() {
		return FLG_FIRMA;
	}
	public void setFLG_FIRMA(Character fLG_FIRMA) {
		FLG_FIRMA = fLG_FIRMA;
	}
	public String getNUME_ANNO_ATTO() {
		return NUME_ANNO_ATTO;
	}
	public void setNUME_ANNO_ATTO(String nUME_ANNO_ATTO) {
		NUME_ANNO_ATTO = nUME_ANNO_ATTO;
	}
	public String getNUME_ATTO() {
		return NUME_ATTO;
	}
	public void setNUME_ATTO(String nUME_ATTO) {
		NUME_ATTO = nUME_ATTO;
	}
	public String getDESC_NUME_RIF() {
		return DESC_NUME_RIF;
	}
	public void setDESC_NUME_RIF(String dESC_NUME_RIF) {
		DESC_NUME_RIF = dESC_NUME_RIF;
	}
	public Character getFLG_SPED_RIC() {
		return FLG_SPED_RIC;
	}
	public void setFLG_SPED_RIC(Character fLG_SPED_RIC) {
		FLG_SPED_RIC = fLG_SPED_RIC;
	}
	public String getDESC_ORGANO() {
		return DESC_ORGANO;
	}
	public void setDESC_ORGANO(String dESC_ORGANO) {
		DESC_ORGANO = dESC_ORGANO;
	}
	public String getDESC_SEDE_ORGANO() {
		return DESC_SEDE_ORGANO;
	}
	public void setDESC_SEDE_ORGANO(String dESC_SEDE_ORGANO) {
		DESC_SEDE_ORGANO = dESC_SEDE_ORGANO;
	}
	public BigDecimal getID_FONTE_ATTO_GEN() {
		return ID_FONTE_ATTO_GEN;
	}
	public void setID_FONTE_ATTO_GEN(BigDecimal iD_FONTE_ATTO_GEN) {
		ID_FONTE_ATTO_GEN = iD_FONTE_ATTO_GEN;
	}

	
	
	
}
