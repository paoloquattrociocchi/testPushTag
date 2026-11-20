package it.giustizia.smp.dto.pg_stralcio;

import java.math.BigDecimal;
import java.util.Date;

public class RicercaStralcioDto {
	
    private String TIPO_MISURA; 
    private BigDecimal ORDINE_MISURA; 
    private Character FLG_VALIDO; 
    private BigDecimal CODI_K_ID_PROC; 
    private BigDecimal ID_TIPO_PREV; 
    private String DESC_TIPO_PREV; 
    private BigDecimal ID_ATTO; 
    private BigDecimal ID_MISURA_INSERITA; 
    private BigDecimal ID_TIPO_MISURA; 
    private BigDecimal ID_NORMATIVA; 
    private BigDecimal ANNI; 
    private BigDecimal MESI; 
    private BigDecimal GIORNI; 
    private String LOCALITA; 
    private BigDecimal DECISIONE_MISURA; 
    private BigDecimal DECISIONE_MISURA2; 
    private String ULTERIORI_PRESCRIZIONI; 
    private String DESC_TIPO_MISURA; 
    private String CARATTERE_MISURA; 
    private String DESC_NORMATIVA; 
    private Date DATA_FINE_VALIDITA; 
    private String DESC_DECISIONE_MISURA; 
    private String DESC_DECISIONE_MISURA2; 
    private BigDecimal CODI_K_ID_ATTO; 
    private BigDecimal CODI_K_ID_UFF_OWNER; 
    private BigDecimal CODI_K_ID_BENE; 
    private BigDecimal CODI_K_ID_TIPO_INT;  
    private BigDecimal CODI_K_ID_SOGG_INT; 
    private BigDecimal CODI_K_ID_SOGG_TIT; 
    private String DESC_CAT_ATTO; 
    private Character CODI_FK_ID_TIPO_SOGG_INT; 
    private String DESC_COGNOME_INT; 
    private String DESC_NOME_INT; 
    private Date DATA_NASCITA_INT; 
    private Character FLG_DATA_PRESUNTA_INT; 
    private String DESC_CODICE_FISCALE_INT; 
    private BigDecimal ID_STATO_NASC_INT; 
    private String DESC_STATO_NASC_INT; 
    private BigDecimal ID_COMUNE_NASC_INT; 
    private String DESC_COMUNE_NASC_INT; 
    private String ID_PROV_NASC_INT; 
    private String SIGLA_PROVINCIA_NASC_INT; 
    private String DESC_COM_ESTERO_NASC_INT; 
    private Character FLG_SESSO_INT; 
    private String DESC_RAGIONE_SOC_INT; 
    private String DESC_PARTITA_IVA_INT; 
    private BigDecimal ID_STATO_SEDE_INT; 
    private String DESC_STATO_SEDE_INT; 
    private BigDecimal ID_COMUNE_SEDE_INT; 
    private String DESC_COMUNE_SEDE_INT; 
    private String ID_PROV_SEDE_INT; 
    private String SIGLA_PROVINCIA_SEDE_INT; 
    private Character CODI_FK_ID_TIPO_SOGG_TIT;
    private String DESC_COGNOME_TIT; 
    private String DESC_NOME_TIT; 
    private Date DATA_NASCITA_TIT; 
    private Character FLG_DATA_PRESUNTA_TIT; 
    private String DESC_CODICE_FISCALE_TIT; 
    private BigDecimal ID_STATO_NASC_TIT; 
    private String DESC_STATO_NASC_TIT; 
    private BigDecimal ID_COMUNE_NASC_TIT; 
    private String DESC_COMUNE_NASC_TIT; 
    private String ID_PROV_NASC_TIT; 
    private String SIGLA_PROVINCIA_NASC_TIT; 
    private String DESC_COM_ESTERO_NASC_TIT; 
    private Character FLG_SESSO_TIT; 
    private String DESC_RAGIONE_SOC_TIT; 
    private String DESC_PARTITA_IVA_TIT; 
    private BigDecimal ID_STATO_SEDE_TIT; 
    private String DESC_STATO_SEDE_TIT; 
    private BigDecimal ID_COMUNE_SEDE_TIT; 
    private String DESC_COMUNE_SEDE_TIT; 
    private String ID_PROV_SEDE_TIT; 
    private String SIGLA_PROVINCIA_SEDE_TIT; 
    private String DESCR_BENE;
    private BigDecimal NUME_QUOTA;
    private BigDecimal NUME_FRAZIONE_NUM;
    private BigDecimal NUME_FRAZIONE_DEN;
    private String DESC_TIPO_INT;
    private String CAUZIONE;
    private String ART67;
    private BigDecimal IMPO_CAUZIONE;
	
    
	public String getTIPO_MISURA() {
		return TIPO_MISURA;
	}
	public BigDecimal getORDINE_MISURA() {
		return ORDINE_MISURA;
	}
	public Character getFLG_VALIDO() {
		return FLG_VALIDO;
	}
	public BigDecimal getCODI_K_ID_PROC() {
		return CODI_K_ID_PROC;
	}
	public BigDecimal getID_TIPO_PREV() {
		return ID_TIPO_PREV;
	}
	public String getDESC_TIPO_PREV() {
		return DESC_TIPO_PREV;
	}
	public BigDecimal getID_ATTO() {
		return ID_ATTO;
	}
	public BigDecimal getID_MISURA_INSERITA() {
		return ID_MISURA_INSERITA;
	}
	public BigDecimal getID_TIPO_MISURA() {
		return ID_TIPO_MISURA;
	}
	public BigDecimal getID_NORMATIVA() {
		return ID_NORMATIVA;
	}
	public BigDecimal getANNI() {
		return ANNI;
	}
	public BigDecimal getMESI() {
		return MESI;
	}
	public BigDecimal getGIORNI() {
		return GIORNI;
	}
	public String getLOCALITA() {
		return LOCALITA;
	}
	public BigDecimal getDECISIONE_MISURA() {
		return DECISIONE_MISURA;
	}
	public String getULTERIORI_PRESCRIZIONI() {
		return ULTERIORI_PRESCRIZIONI;
	}
	public String getDESC_TIPO_MISURA() {
		return DESC_TIPO_MISURA;
	}
	public String getCARATTERE_MISURA() {
		return CARATTERE_MISURA;
	}
	public String getDESC_NORMATIVA() {
		return DESC_NORMATIVA;
	}
	public Date getDATA_FINE_VALIDITA() {
		return DATA_FINE_VALIDITA;
	}
	public String getDESC_DECISIONE_MISURA() {
		return DESC_DECISIONE_MISURA;
	}
	public BigDecimal getCODI_K_ID_ATTO() {
		return CODI_K_ID_ATTO;
	}
	public BigDecimal getCODI_K_ID_UFF_OWNER() {
		return CODI_K_ID_UFF_OWNER;
	}
	public BigDecimal getCODI_K_ID_BENE() {
		return CODI_K_ID_BENE;
	}
	public BigDecimal getCODI_K_ID_TIPO_INT() {
		return CODI_K_ID_TIPO_INT;
	}
	public BigDecimal getCODI_K_ID_SOGG_INT() {
		return CODI_K_ID_SOGG_INT;
	}
	public BigDecimal getCODI_K_ID_SOGG_TIT() {
		return CODI_K_ID_SOGG_TIT;
	}
	public String getDESC_CAT_ATTO() {
		return DESC_CAT_ATTO;
	}
	public Character getCODI_FK_ID_TIPO_SOGG_INT() {
		return CODI_FK_ID_TIPO_SOGG_INT;
	}
	public String getDESC_COGNOME_INT() {
		return DESC_COGNOME_INT;
	}
	public String getDESC_NOME_INT() {
		return DESC_NOME_INT;
	}
	public Date getDATA_NASCITA_INT() {
		return DATA_NASCITA_INT;
	}
	public Character getFLG_DATA_PRESUNTA_INT() {
		return FLG_DATA_PRESUNTA_INT;
	}
	public String getDESC_CODICE_FISCALE_INT() {
		return DESC_CODICE_FISCALE_INT;
	}
	public BigDecimal getID_STATO_NASC_INT() {
		return ID_STATO_NASC_INT;
	}
	public String getDESC_STATO_NASC_INT() {
		return DESC_STATO_NASC_INT;
	}
	public BigDecimal getID_COMUNE_NASC_INT() {
		return ID_COMUNE_NASC_INT;
	}
	public String getDESC_COMUNE_NASC_INT() {
		return DESC_COMUNE_NASC_INT;
	}
	public String getID_PROV_NASC_INT() {
		return ID_PROV_NASC_INT;
	}
	public String getSIGLA_PROVINCIA_NASC_INT() {
		return SIGLA_PROVINCIA_NASC_INT;
	}
	public String getDESC_COM_ESTERO_NASC_INT() {
		return DESC_COM_ESTERO_NASC_INT;
	}
	public Character getFLG_SESSO_INT() {
		return FLG_SESSO_INT;
	}
	public String getDESC_RAGIONE_SOC_INT() {
		return DESC_RAGIONE_SOC_INT;
	}
	public String getDESC_PARTITA_IVA_INT() {
		return DESC_PARTITA_IVA_INT;
	}
	public BigDecimal getID_STATO_SEDE_INT() {
		return ID_STATO_SEDE_INT;
	}
	public String getDESC_STATO_SEDE_INT() {
		return DESC_STATO_SEDE_INT;
	}
	public BigDecimal getID_COMUNE_SEDE_INT() {
		return ID_COMUNE_SEDE_INT;
	}
	public String getDESC_COMUNE_SEDE_INT() {
		return DESC_COMUNE_SEDE_INT;
	}
	public String getID_PROV_SEDE_INT() {
		return ID_PROV_SEDE_INT;
	}
	public String getSIGLA_PROVINCIA_SEDE_INT() {
		return SIGLA_PROVINCIA_SEDE_INT;
	}
	public String getDESC_COGNOME_TIT() {
		return DESC_COGNOME_TIT;
	}
	public String getDESC_NOME_TIT() {
		return DESC_NOME_TIT;
	}
	public Date getDATA_NASCITA_TIT() {
		return DATA_NASCITA_TIT;
	}
	public Character getFLG_DATA_PRESUNTA_TIT() {
		return FLG_DATA_PRESUNTA_TIT;
	}
	public String getDESC_CODICE_FISCALE_TIT() {
		return DESC_CODICE_FISCALE_TIT;
	}
	public BigDecimal getID_STATO_NASC_TIT() {
		return ID_STATO_NASC_TIT;
	}
	public String getDESC_STATO_NASC_TIT() {
		return DESC_STATO_NASC_TIT;
	}
	public BigDecimal getID_COMUNE_NASC_TIT() {
		return ID_COMUNE_NASC_TIT;
	}
	public String getDESC_COMUNE_NASC_TIT() {
		return DESC_COMUNE_NASC_TIT;
	}
	public String getID_PROV_NASC_TIT() {
		return ID_PROV_NASC_TIT;
	}
	public String getSIGLA_PROVINCIA_NASC_TIT() {
		return SIGLA_PROVINCIA_NASC_TIT;
	}
	public String getDESC_COM_ESTERO_NASC_TIT() {
		return DESC_COM_ESTERO_NASC_TIT;
	}
	public Character getFLG_SESSO_TIT() {
		return FLG_SESSO_TIT;
	}
	public String getDESC_RAGIONE_SOC_TIT() {
		return DESC_RAGIONE_SOC_TIT;
	}
	public String getDESC_PARTITA_IVA_TIT() {
		return DESC_PARTITA_IVA_TIT;
	}
	public BigDecimal getID_STATO_SEDE_TIT() {
		return ID_STATO_SEDE_TIT;
	}
	public String getDESC_STATO_SEDE_TIT() {
		return DESC_STATO_SEDE_TIT;
	}
	public BigDecimal getID_COMUNE_SEDE_TIT() {
		return ID_COMUNE_SEDE_TIT;
	}
	public String getDESC_COMUNE_SEDE_TIT() {
		return DESC_COMUNE_SEDE_TIT;
	}
	public String getID_PROV_SEDE_TIT() {
		return ID_PROV_SEDE_TIT;
	}
	public String getSIGLA_PROVINCIA_SEDE_TIT() {
		return SIGLA_PROVINCIA_SEDE_TIT;
	}
	public String getDESCR_BENE() {
		return DESCR_BENE;
	}
	public BigDecimal getNUME_QUOTA() {
		return NUME_QUOTA;
	}
	public BigDecimal getNUME_FRAZIONE_NUM() {
		return NUME_FRAZIONE_NUM;
	}
	public BigDecimal getNUME_FRAZIONE_DEN() {
		return NUME_FRAZIONE_DEN;
	}
	public String getDESC_TIPO_INT() {
		return DESC_TIPO_INT;
	}
	public void setTIPO_MISURA(String tIPO_MISURA) {
		TIPO_MISURA = tIPO_MISURA;
	}
	public void setORDINE_MISURA(BigDecimal oRDINE_MISURA) {
		ORDINE_MISURA = oRDINE_MISURA;
	}
	public void setFLG_VALIDO(Character fLG_VALIDO) {
		FLG_VALIDO = fLG_VALIDO;
	}
	public void setCODI_K_ID_PROC(BigDecimal cODI_K_ID_PROC) {
		CODI_K_ID_PROC = cODI_K_ID_PROC;
	}
	public void setID_TIPO_PREV(BigDecimal iD_TIPO_PREV) {
		ID_TIPO_PREV = iD_TIPO_PREV;
	}
	public void setDESC_TIPO_PREV(String dESC_TIPO_PREV) {
		DESC_TIPO_PREV = dESC_TIPO_PREV;
	}
	public void setID_ATTO(BigDecimal iD_ATTO) {
		ID_ATTO = iD_ATTO;
	}
	public void setID_MISURA_INSERITA(BigDecimal iD_MISURA_INSERITA) {
		ID_MISURA_INSERITA = iD_MISURA_INSERITA;
	}
	public void setID_TIPO_MISURA(BigDecimal iD_TIPO_MISURA) {
		ID_TIPO_MISURA = iD_TIPO_MISURA;
	}
	public void setID_NORMATIVA(BigDecimal iD_NORMATIVA) {
		ID_NORMATIVA = iD_NORMATIVA;
	}
	public void setANNI(BigDecimal aNNI) {
		ANNI = aNNI;
	}
	public void setMESI(BigDecimal mESI) {
		MESI = mESI;
	}
	public void setGIORNI(BigDecimal gIORNI) {
		GIORNI = gIORNI;
	}
	public void setLOCALITA(String lOCALITA) {
		LOCALITA = lOCALITA;
	}
	public void setDECISIONE_MISURA(BigDecimal dECISIONE_MISURA) {
		DECISIONE_MISURA = dECISIONE_MISURA;
	}
	public void setULTERIORI_PRESCRIZIONI(String uLTERIORI_PRESCRIZIONI) {
		ULTERIORI_PRESCRIZIONI = uLTERIORI_PRESCRIZIONI;
	}
	public void setDESC_TIPO_MISURA(String dESC_TIPO_MISURA) {
		DESC_TIPO_MISURA = dESC_TIPO_MISURA;
	}
	public void setCARATTERE_MISURA(String cARATTERE_MISURA) {
		CARATTERE_MISURA = cARATTERE_MISURA;
	}
	public void setDESC_NORMATIVA(String dESC_NORMATIVA) {
		DESC_NORMATIVA = dESC_NORMATIVA;
	}
	public void setDATA_FINE_VALIDITA(Date dATA_FINE_VALIDITA) {
		DATA_FINE_VALIDITA = dATA_FINE_VALIDITA;
	}
	public void setDESC_DECISIONE_MISURA(String dESC_DECISIONE_MISURA) {
		DESC_DECISIONE_MISURA = dESC_DECISIONE_MISURA;
	}
	public void setCODI_K_ID_ATTO(BigDecimal cODI_K_ID_ATTO) {
		CODI_K_ID_ATTO = cODI_K_ID_ATTO;
	}
	public void setCODI_K_ID_UFF_OWNER(BigDecimal cODI_K_ID_UFF_OWNER) {
		CODI_K_ID_UFF_OWNER = cODI_K_ID_UFF_OWNER;
	}
	public void setCODI_K_ID_BENE(BigDecimal cODI_K_ID_BENE) {
		CODI_K_ID_BENE = cODI_K_ID_BENE;
	}
	public void setCODI_K_ID_TIPO_INT(BigDecimal cODI_K_ID_TIPO_INT) {
		CODI_K_ID_TIPO_INT = cODI_K_ID_TIPO_INT;
	}
	public void setCODI_K_ID_SOGG_INT(BigDecimal cODI_K_ID_SOGG_INT) {
		CODI_K_ID_SOGG_INT = cODI_K_ID_SOGG_INT;
	}
	public void setCODI_K_ID_SOGG_TIT(BigDecimal cODI_K_ID_SOGG_TIT) {
		CODI_K_ID_SOGG_TIT = cODI_K_ID_SOGG_TIT;
	}
	public void setDESC_CAT_ATTO(String dESC_CAT_ATTO) {
		DESC_CAT_ATTO = dESC_CAT_ATTO;
	}
	public void setCODI_FK_ID_TIPO_SOGG_INT(Character cODI_FK_ID_TIPO_SOGG_INT) {
		CODI_FK_ID_TIPO_SOGG_INT = cODI_FK_ID_TIPO_SOGG_INT;
	}
	public void setDESC_COGNOME_INT(String dESC_COGNOME_INT) {
		DESC_COGNOME_INT = dESC_COGNOME_INT;
	}
	public void setDESC_NOME_INT(String dESC_NOME_INT) {
		DESC_NOME_INT = dESC_NOME_INT;
	}
	public void setDATA_NASCITA_INT(Date dATA_NASCITA_INT) {
		DATA_NASCITA_INT = dATA_NASCITA_INT;
	}
	public void setFLG_DATA_PRESUNTA_INT(Character fLG_DATA_PRESUNTA_INT) {
		FLG_DATA_PRESUNTA_INT = fLG_DATA_PRESUNTA_INT;
	}
	public void setDESC_CODICE_FISCALE_INT(String dESC_CODICE_FISCALE_INT) {
		DESC_CODICE_FISCALE_INT = dESC_CODICE_FISCALE_INT;
	}
	public void setID_STATO_NASC_INT(BigDecimal iD_STATO_NASC_INT) {
		ID_STATO_NASC_INT = iD_STATO_NASC_INT;
	}
	public void setDESC_STATO_NASC_INT(String dESC_STATO_NASC_INT) {
		DESC_STATO_NASC_INT = dESC_STATO_NASC_INT;
	}
	public void setID_COMUNE_NASC_INT(BigDecimal iD_COMUNE_NASC_INT) {
		ID_COMUNE_NASC_INT = iD_COMUNE_NASC_INT;
	}
	public void setDESC_COMUNE_NASC_INT(String dESC_COMUNE_NASC_INT) {
		DESC_COMUNE_NASC_INT = dESC_COMUNE_NASC_INT;
	}
	public void setID_PROV_NASC_INT(String iD_PROV_NASC_INT) {
		ID_PROV_NASC_INT = iD_PROV_NASC_INT;
	}
	public void setSIGLA_PROVINCIA_NASC_INT(String sIGLA_PROVINCIA_NASC_INT) {
		SIGLA_PROVINCIA_NASC_INT = sIGLA_PROVINCIA_NASC_INT;
	}
	public void setDESC_COM_ESTERO_NASC_INT(String dESC_COM_ESTERO_NASC_INT) {
		DESC_COM_ESTERO_NASC_INT = dESC_COM_ESTERO_NASC_INT;
	}
	public void setFLG_SESSO_INT(Character fLG_SESSO_INT) {
		FLG_SESSO_INT = fLG_SESSO_INT;
	}
	public void setDESC_RAGIONE_SOC_INT(String dESC_RAGIONE_SOC_INT) {
		DESC_RAGIONE_SOC_INT = dESC_RAGIONE_SOC_INT;
	}
	public void setDESC_PARTITA_IVA_INT(String dESC_PARTITA_IVA_INT) {
		DESC_PARTITA_IVA_INT = dESC_PARTITA_IVA_INT;
	}
	public void setID_STATO_SEDE_INT(BigDecimal iD_STATO_SEDE_INT) {
		ID_STATO_SEDE_INT = iD_STATO_SEDE_INT;
	}
	public void setDESC_STATO_SEDE_INT(String dESC_STATO_SEDE_INT) {
		DESC_STATO_SEDE_INT = dESC_STATO_SEDE_INT;
	}
	public void setID_COMUNE_SEDE_INT(BigDecimal iD_COMUNE_SEDE_INT) {
		ID_COMUNE_SEDE_INT = iD_COMUNE_SEDE_INT;
	}
	public void setDESC_COMUNE_SEDE_INT(String dESC_COMUNE_SEDE_INT) {
		DESC_COMUNE_SEDE_INT = dESC_COMUNE_SEDE_INT;
	}
	public void setID_PROV_SEDE_INT(String iD_PROV_SEDE_INT) {
		ID_PROV_SEDE_INT = iD_PROV_SEDE_INT;
	}
	public void setSIGLA_PROVINCIA_SEDE_INT(String sIGLA_PROVINCIA_SEDE_INT) {
		SIGLA_PROVINCIA_SEDE_INT = sIGLA_PROVINCIA_SEDE_INT;
	}
	public void setDESC_COGNOME_TIT(String dESC_COGNOME_TIT) {
		DESC_COGNOME_TIT = dESC_COGNOME_TIT;
	}
	public void setDESC_NOME_TIT(String dESC_NOME_TIT) {
		DESC_NOME_TIT = dESC_NOME_TIT;
	}
	public void setDATA_NASCITA_TIT(Date dATA_NASCITA_TIT) {
		DATA_NASCITA_TIT = dATA_NASCITA_TIT;
	}
	public void setFLG_DATA_PRESUNTA_TIT(Character fLG_DATA_PRESUNTA_TIT) {
		FLG_DATA_PRESUNTA_TIT = fLG_DATA_PRESUNTA_TIT;
	}
	public void setDESC_CODICE_FISCALE_TIT(String dESC_CODICE_FISCALE_TIT) {
		DESC_CODICE_FISCALE_TIT = dESC_CODICE_FISCALE_TIT;
	}
	public void setID_STATO_NASC_TIT(BigDecimal iD_STATO_NASC_TIT) {
		ID_STATO_NASC_TIT = iD_STATO_NASC_TIT;
	}
	public void setDESC_STATO_NASC_TIT(String dESC_STATO_NASC_TIT) {
		DESC_STATO_NASC_TIT = dESC_STATO_NASC_TIT;
	}
	public void setID_COMUNE_NASC_TIT(BigDecimal iD_COMUNE_NASC_TIT) {
		ID_COMUNE_NASC_TIT = iD_COMUNE_NASC_TIT;
	}
	public void setDESC_COMUNE_NASC_TIT(String dESC_COMUNE_NASC_TIT) {
		DESC_COMUNE_NASC_TIT = dESC_COMUNE_NASC_TIT;
	}
	public void setID_PROV_NASC_TIT(String iD_PROV_NASC_TIT) {
		ID_PROV_NASC_TIT = iD_PROV_NASC_TIT;
	}
	public void setSIGLA_PROVINCIA_NASC_TIT(String sIGLA_PROVINCIA_NASC_TIT) {
		SIGLA_PROVINCIA_NASC_TIT = sIGLA_PROVINCIA_NASC_TIT;
	}
	public void setDESC_COM_ESTERO_NASC_TIT(String dESC_COM_ESTERO_NASC_TIT) {
		DESC_COM_ESTERO_NASC_TIT = dESC_COM_ESTERO_NASC_TIT;
	}
	public void setFLG_SESSO_TIT(Character fLG_SESSO_TIT) {
		FLG_SESSO_TIT = fLG_SESSO_TIT;
	}
	public void setDESC_RAGIONE_SOC_TIT(String dESC_RAGIONE_SOC_TIT) {
		DESC_RAGIONE_SOC_TIT = dESC_RAGIONE_SOC_TIT;
	}
	public void setDESC_PARTITA_IVA_TIT(String dESC_PARTITA_IVA_TIT) {
		DESC_PARTITA_IVA_TIT = dESC_PARTITA_IVA_TIT;
	}
	public void setID_STATO_SEDE_TIT(BigDecimal iD_STATO_SEDE_TIT) {
		ID_STATO_SEDE_TIT = iD_STATO_SEDE_TIT;
	}
	public void setDESC_STATO_SEDE_TIT(String dESC_STATO_SEDE_TIT) {
		DESC_STATO_SEDE_TIT = dESC_STATO_SEDE_TIT;
	}
	public void setID_COMUNE_SEDE_TIT(BigDecimal iD_COMUNE_SEDE_TIT) {
		ID_COMUNE_SEDE_TIT = iD_COMUNE_SEDE_TIT;
	}
	public void setDESC_COMUNE_SEDE_TIT(String dESC_COMUNE_SEDE_TIT) {
		DESC_COMUNE_SEDE_TIT = dESC_COMUNE_SEDE_TIT;
	}
	public void setID_PROV_SEDE_TIT(String iD_PROV_SEDE_TIT) {
		ID_PROV_SEDE_TIT = iD_PROV_SEDE_TIT;
	}
	public void setSIGLA_PROVINCIA_SEDE_TIT(String sIGLA_PROVINCIA_SEDE_TIT) {
		SIGLA_PROVINCIA_SEDE_TIT = sIGLA_PROVINCIA_SEDE_TIT;
	}
	public void setDESCR_BENE(String dESCR_BENE) {
		DESCR_BENE = dESCR_BENE;
	}
	public void setNUME_QUOTA(BigDecimal nUME_QUOTA) {
		NUME_QUOTA = nUME_QUOTA;
	}
	public void setNUME_FRAZIONE_NUM(BigDecimal nUME_FRAZIONE_NUM) {
		NUME_FRAZIONE_NUM = nUME_FRAZIONE_NUM;
	}
	public void setNUME_FRAZIONE_DEN(BigDecimal nUME_FRAZIONE_DEN) {
		NUME_FRAZIONE_DEN = nUME_FRAZIONE_DEN;
	}
	public void setDESC_TIPO_INT(String dESC_TIPO_INT) {
		DESC_TIPO_INT = dESC_TIPO_INT;
	}
	public Character getCODI_FK_ID_TIPO_SOGG_TIT() {
		return CODI_FK_ID_TIPO_SOGG_TIT;
	}
	public void setCODI_FK_ID_TIPO_SOGG_TIT(Character cODI_FK_ID_TIPO_SOGG_TIT) {
		CODI_FK_ID_TIPO_SOGG_TIT = cODI_FK_ID_TIPO_SOGG_TIT;
	}
	public String getCAUZIONE() {
		return CAUZIONE;
	}
	public String getART67() {
		return ART67;
	}
	public void setCAUZIONE(String cAUZIONE) {
		CAUZIONE = cAUZIONE;
	}
	public void setART67(String aRT67) {
		ART67 = aRT67;
	}
	public BigDecimal getDECISIONE_MISURA2() {
		return DECISIONE_MISURA2;
	}
	public void setDECISIONE_MISURA2(BigDecimal dECISIONE_MISURA2) {
		DECISIONE_MISURA2 = dECISIONE_MISURA2;
	}
	public String getDESC_DECISIONE_MISURA2() {
		return DESC_DECISIONE_MISURA2;
	}
	public void setDESC_DECISIONE_MISURA2(String dESC_DECISIONE_MISURA2) {
		DESC_DECISIONE_MISURA2 = dESC_DECISIONE_MISURA2;
	}
	public BigDecimal getIMPO_CAUZIONE() {
		return IMPO_CAUZIONE;
	}
	public void setIMPO_CAUZIONE(BigDecimal iMPO_CAUZIONE) {
		IMPO_CAUZIONE = iMPO_CAUZIONE;
	}
	
    
}
