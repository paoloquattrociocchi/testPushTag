package it.giustizia.smp.dto.pg_atto;

import java.math.BigDecimal;
import java.util.Date;

public class RedazioneSchedaDto {

	private BigDecimal CODI_K_ID_PROC;
	private BigDecimal CODI_K_ID_MISURA;
	private BigDecimal CODI_K_ID_ATTO;
	private BigDecimal CODI_K_ID_PERSONA_FIS;
	private Date DATA_NASCITA;
	private String DESC_COGNOME; 
	private String DESC_NOME; 
	private String DESC_TIPO_MISURA;
	private Date  DATA_DEFINITIVITA;
	private BigDecimal CODI_FK_ID_VER;
	private BigDecimal CODI_FK_ID_TIPO_NORM;
	private String DESC_TIPO_NORM;
	
	private Date  DATA_EMISSIONE;
	private Character FLG_STATO_COMUNICAZ;
	private String STATO;
	private Boolean showOption;
	private BigDecimal CODI_K_ID_COMUNICAZ ;
	
	
	
	public BigDecimal getCODI_K_ID_COMUNICAZ() {
		return CODI_K_ID_COMUNICAZ;
	}
	public void setCODI_K_ID_COMUNICAZ(BigDecimal cODI_K_ID_COMUNICAZ) {
		CODI_K_ID_COMUNICAZ = cODI_K_ID_COMUNICAZ;
	}
	public Boolean getShowOption() {
		return showOption;
	}
	public void setShowOption(Boolean showOption) {
		this.showOption = showOption;
	}
	public Date getDATA_EMISSIONE() {
		return DATA_EMISSIONE;
	}
	public void setDATA_EMISSIONE(Date dATA_EMISSIONE) {
		DATA_EMISSIONE = dATA_EMISSIONE;
	}
	public Character getFLG_STATO_COMUNICAZ() {
		return FLG_STATO_COMUNICAZ;
	}
	public void setFLG_STATO_COMUNICAZ(Character fLG_STATO_COMUNICAZ) {
		FLG_STATO_COMUNICAZ = fLG_STATO_COMUNICAZ;
	}
	public String getSTATO() {
		return STATO;
	}
	public void setSTATO(String sTATO) {
		STATO = sTATO;
	}
	public BigDecimal getCODI_FK_ID_TIPO_NORM() {
		return CODI_FK_ID_TIPO_NORM;
	}
	public void setCODI_FK_ID_TIPO_NORM(BigDecimal cODI_FK_ID_TIPO_NORM) {
		CODI_FK_ID_TIPO_NORM = cODI_FK_ID_TIPO_NORM;
	}
	public String getDESC_TIPO_NORM() {
		return DESC_TIPO_NORM;
	}
	public void setDESC_TIPO_NORM(String dESC_TIPO_NORM) {
		DESC_TIPO_NORM = dESC_TIPO_NORM;
	}
	public BigDecimal getCODI_K_ID_ATTO() {
		return CODI_K_ID_ATTO;
	}
	public void setCODI_K_ID_ATTO(BigDecimal cODI_K_ID_ATTO) {
		CODI_K_ID_ATTO = cODI_K_ID_ATTO;
	}
	public BigDecimal getCODI_K_ID_PERSONA_FIS() {
		return CODI_K_ID_PERSONA_FIS;
	}
	public void setCODI_K_ID_PERSONA_FIS(BigDecimal cODI_K_ID_PERSONA_FIS) {
		CODI_K_ID_PERSONA_FIS = cODI_K_ID_PERSONA_FIS;
	}
	public BigDecimal getCODI_FK_ID_VER() {
		return CODI_FK_ID_VER;
	}
	public void setCODI_FK_ID_VER(BigDecimal cODI_FK_ID_VER) {
		CODI_FK_ID_VER = cODI_FK_ID_VER;
	}
	public BigDecimal getCODI_K_ID_PROC() {
		return CODI_K_ID_PROC;
	}
	public void setCODI_K_ID_PROC(BigDecimal cODI_K_ID_PROC) {
		CODI_K_ID_PROC = cODI_K_ID_PROC;
	}
	
	
	
	public BigDecimal getCODI_K_ID_MISURA() {
		return CODI_K_ID_MISURA;
	}
	public void setCODI_K_ID_MISURA(BigDecimal cODI_K_ID_MISURA) {
		CODI_K_ID_MISURA = cODI_K_ID_MISURA;
	}
	public Date getDATA_NASCITA() {
		return DATA_NASCITA;
	}
	public void setDATA_NASCITA(Date dATA_NASCITA) {
		DATA_NASCITA = dATA_NASCITA;
	}
	public String getDESC_COGNOME() {
		return DESC_COGNOME;
	}
	public void setDESC_COGNOME(String dESC_COGNOME) {
		DESC_COGNOME = dESC_COGNOME;
	}
	public String getDESC_NOME() {
		return DESC_NOME;
	}
	public void setDESC_NOME(String dESC_NOME) {
		DESC_NOME = dESC_NOME;
	}
	public String getDESC_TIPO_MISURA() {
		return DESC_TIPO_MISURA;
	}
	public void setDESC_TIPO_MISURA(String dESC_TIPO_MISURA) {
		DESC_TIPO_MISURA = dESC_TIPO_MISURA;
	}
	public Date getDATA_DEFINITIVITA() {
		return DATA_DEFINITIVITA;
	}
	public void setDATA_DEFINITIVITA(Date dATA_DEFINITIVITA) {
		DATA_DEFINITIVITA = dATA_DEFINITIVITA;
	}
	
	
	
}
