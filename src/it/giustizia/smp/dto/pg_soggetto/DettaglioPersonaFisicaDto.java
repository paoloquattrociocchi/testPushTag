package it.giustizia.smp.dto.pg_soggetto;

import java.math.BigDecimal;
import java.util.Date;

public class DettaglioPersonaFisicaDto {

    private String     DESC_CUI;
    private BigDecimal CODI_K_ID_UFF_OWNER;
    private BigDecimal CODI_K_ID_PERSONA_FIS;
    private BigDecimal CODI_K_ID_VER;
    private String     DESC_CODICE_FISCALE;
    private Date       DATA_DECESSO;
    private Date       DATA_NASCITA;
    private String     DESC_COG_MADRE;
    private String     DESC_COG_PADRE;
    private String     DESC_COGNOME;
    private String     DESC_COM_ESTERO_MORTE;
    private String     DESC_COM_ESTERO_NASC;
    private String     DESC_COMUNE_MORT;
    private String     DESC_COMUNE_NASC;
    private String     DESC_NOME;
    private String     DESC_NOME_MADRE;
    private String     DESC_NOME_PADRE;
    private String     DESC_STATO_MORT;
    private String     DESC_STATO_NASC;
    private Character  FLG_DATA_PRESUNTA;
   // private Character  FLG_PERS_DA_IDENT;
    private BigDecimal ID_COMUNE_MORT;
    private BigDecimal ID_COMUNE_NASC;
    private String     ID_PROV_MORT;
    private String     ID_PROV_NASC;
    private BigDecimal ID_STATO_MORT;
    private BigDecimal ID_STATO_NASC;
    private Character  FLG_SESSO;
    private String     SIGLA_PROV_MORT;
    private String     SIGLA_PROVINCIA_NASC;
    
	public String getDESC_CUI() {
		return DESC_CUI;
	}
	public void setDESC_CUI(String dESC_CUI) {
		DESC_CUI = dESC_CUI;
	}
	public BigDecimal getCODI_K_ID_UFF_OWNER() {
		return CODI_K_ID_UFF_OWNER;
	}
	public void setCODI_K_ID_UFF_OWNER(BigDecimal cODI_K_ID_UFF_OWNER) {
		CODI_K_ID_UFF_OWNER = cODI_K_ID_UFF_OWNER;
	}
	public BigDecimal getCODI_K_ID_PERSONA_FIS() {
		return CODI_K_ID_PERSONA_FIS;
	}
	public void setCODI_K_ID_PERSONA_FIS(BigDecimal cODI_K_ID_PERSONA_FIS) {
		CODI_K_ID_PERSONA_FIS = cODI_K_ID_PERSONA_FIS;
	}
	public BigDecimal getCODI_K_ID_VER() {
		return CODI_K_ID_VER;
	}
	public void setCODI_K_ID_VER(BigDecimal cODI_K_ID_VER) {
		CODI_K_ID_VER = cODI_K_ID_VER;
	}
	public String getDESC_CODICE_FISCALE() {
		return DESC_CODICE_FISCALE;
	}
	public void setDESC_CODICE_FISCALE(String dESC_CODICE_FISCALE) {
		DESC_CODICE_FISCALE = dESC_CODICE_FISCALE;
	}
	public Date getDATA_DECESSO() {
		return DATA_DECESSO;
	}
	public void setDATA_DECESSO(Date dATA_DECESSO) {
		DATA_DECESSO = dATA_DECESSO;
	}
	public Date getDATA_NASCITA() {
		return DATA_NASCITA;
	}
	public void setDATA_NASCITA(Date dATA_NASCITA) {
		DATA_NASCITA = dATA_NASCITA;
	}
	public String getDESC_COG_MADRE() {
		return DESC_COG_MADRE;
	}
	public void setDESC_COG_MADRE(String dESC_COG_MADRE) {
		DESC_COG_MADRE = dESC_COG_MADRE;
	}
	public String getDESC_COG_PADRE() {
		return DESC_COG_PADRE;
	}
	public void setDESC_COG_PADRE(String dESC_COG_PADRE) {
		DESC_COG_PADRE = dESC_COG_PADRE;
	}
	public String getDESC_COGNOME() {
		return DESC_COGNOME;
	}
	public void setDESC_COGNOME(String dESC_COGNOME) {
		DESC_COGNOME = dESC_COGNOME;
	}
	public String getDESC_COM_ESTERO_MORTE() {
		return DESC_COM_ESTERO_MORTE;
	}
	public void setDESC_COM_ESTERO_MORTE(String dESC_COM_ESTERO_MORTE) {
		DESC_COM_ESTERO_MORTE = dESC_COM_ESTERO_MORTE;
	}
	public String getDESC_COM_ESTERO_NASC() {
		return DESC_COM_ESTERO_NASC;
	}
	public void setDESC_COM_ESTERO_NASC(String dESC_COM_ESTERO_NASC) {
		DESC_COM_ESTERO_NASC = dESC_COM_ESTERO_NASC;
	}
	public String getDESC_COMUNE_MORT() {
		return DESC_COMUNE_MORT;
	}
	public void setDESC_COMUNE_MORT(String dESC_COMUNE_MORT) {
		DESC_COMUNE_MORT = dESC_COMUNE_MORT;
	}
	public String getDESC_COMUNE_NASC() {
		return DESC_COMUNE_NASC;
	}
	public void setDESC_COMUNE_NASC(String dESC_COMUNE_NASC) {
		DESC_COMUNE_NASC = dESC_COMUNE_NASC;
	}
	public String getDESC_NOME() {
		return DESC_NOME;
	}
	public void setDESC_NOME(String dESC_NOME) {
		DESC_NOME = dESC_NOME;
	}
	public String getDESC_NOME_MADRE() {
		return DESC_NOME_MADRE;
	}
	public void setDESC_NOME_MADRE(String dESC_NOME_MADRE) {
		DESC_NOME_MADRE = dESC_NOME_MADRE;
	}
	public String getDESC_NOME_PADRE() {
		return DESC_NOME_PADRE;
	}
	public void setDESC_NOME_PADRE(String dESC_NOME_PADRE) {
		DESC_NOME_PADRE = dESC_NOME_PADRE;
	}
	public String getDESC_STATO_MORT() {
		return DESC_STATO_MORT;
	}
	public void setDESC_STATO_MORT(String dESC_STATO_MORT) {
		DESC_STATO_MORT = dESC_STATO_MORT;
	}
	public String getDESC_STATO_NASC() {
		return DESC_STATO_NASC;
	}
	public void setDESC_STATO_NASC(String dESC_STATO_NASC) {
		DESC_STATO_NASC = dESC_STATO_NASC;
	}
	public BigDecimal getID_COMUNE_MORT() {
		return ID_COMUNE_MORT;
	}
	public void setID_COMUNE_MORT(BigDecimal iD_COMUNE_MORT) {
		ID_COMUNE_MORT = iD_COMUNE_MORT;
	}
	public BigDecimal getID_COMUNE_NASC() {
		return ID_COMUNE_NASC;
	}
	public void setID_COMUNE_NASC(BigDecimal iD_COMUNE_NASC) {
		ID_COMUNE_NASC = iD_COMUNE_NASC;
	}
	public String getID_PROV_MORT() {
		return ID_PROV_MORT;
	}
	public void setID_PROV_MORT(String iD_PROV_MORT) {
		ID_PROV_MORT = iD_PROV_MORT;
	}
	public String getID_PROV_NASC() {
		return ID_PROV_NASC;
	}
	public void setID_PROV_NASC(String iD_PROV_NASC) {
		ID_PROV_NASC = iD_PROV_NASC;
	}
	public BigDecimal getID_STATO_MORT() {
		return ID_STATO_MORT;
	}
	public void setID_STATO_MORT(BigDecimal iD_STATO_MORT) {
		ID_STATO_MORT = iD_STATO_MORT;
	}
	public BigDecimal getID_STATO_NASC() {
		return ID_STATO_NASC;
	}
	public void setID_STATO_NASC(BigDecimal iD_STATO_NASC) {
		ID_STATO_NASC = iD_STATO_NASC;
	}
	
	public String getSIGLA_PROV_MORT() {
		return SIGLA_PROV_MORT;
	}
	public void setSIGLA_PROV_MORT(String sIGLA_PROV_MORT) {
		SIGLA_PROV_MORT = sIGLA_PROV_MORT;
	}
	public String getSIGLA_PROVINCIA_NASC() {
		return SIGLA_PROVINCIA_NASC;
	}
	public void setSIGLA_PROVINCIA_NASC(String sIGLA_PROVINCIA_NASC) {
		SIGLA_PROVINCIA_NASC = sIGLA_PROVINCIA_NASC;
	}
	
	public Character getFLG_SESSO() {
		return FLG_SESSO;
	}
	public void setFLG_SESSO(Character fLG_SESSO) {
		FLG_SESSO = fLG_SESSO;
	}
	public Character getFLG_DATA_PRESUNTA() {
		return FLG_DATA_PRESUNTA;
	}
	public void setFLG_DATA_PRESUNTA(Character fLG_DATA_PRESUNTA) {
		FLG_DATA_PRESUNTA = fLG_DATA_PRESUNTA;
	}

    
}
