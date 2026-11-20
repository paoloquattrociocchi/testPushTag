package it.giustizia.smp.dto.pg_atto;

import java.math.BigDecimal;
import java.util.Date;

public class AttoDocumentaleDto extends DettaglioAttoDto{

	private BigDecimal CODI_K_ID_PROC;
	private BigDecimal CODI_K_ID_UFF_OWNER;
	private Date DATA_DEPOSITO_RIC;
	private String DESC_VISIBILITA; 
	private String DESC_NOTE_PREC; 
	private Character FLG_VALIDO;
	private Character CODI_FK_ID_MOTIVO;
	private Character FLG_ATTO_ORIG;
	private Character FLG_ASSOCIA_PDOC;
	private String DESC_UTENTE; 
	private Date DATA_ULT_AGG;
	private BigDecimal NUME_COPIA_PDOC;
	private BigDecimal NUME_CONV_PDOC;
	private Character FLG_ANBSC;
	private Character FLG_MPS;
	
	
	public BigDecimal getCODI_K_ID_PROC() {
		return CODI_K_ID_PROC;
	}
	public void setCODI_K_ID_PROC(BigDecimal cODI_K_ID_PROC) {
		CODI_K_ID_PROC = cODI_K_ID_PROC;
	}
	public BigDecimal getCODI_K_ID_UFF_OWNER() {
		return CODI_K_ID_UFF_OWNER;
	}
	public void setCODI_K_ID_UFF_OWNER(BigDecimal cODI_K_ID_UFF_OWNER) {
		CODI_K_ID_UFF_OWNER = cODI_K_ID_UFF_OWNER;
	}
	public Date getDATA_DEPOSITO_RIC() {
		return DATA_DEPOSITO_RIC;
	}
	public void setDATA_DEPOSITO_RIC(Date dATA_DEPOSITO_RIC) {
		DATA_DEPOSITO_RIC = dATA_DEPOSITO_RIC;
	}
	public String getDESC_VISIBILITA() {
		return DESC_VISIBILITA;
	}
	public void setDESC_VISIBILITA(String dESC_VISIBILITA) {
		DESC_VISIBILITA = dESC_VISIBILITA;
	}
	public Character getFLG_VALIDO() {
		return FLG_VALIDO;
	}
	public void setFLG_VALIDO(Character fLG_VALIDO) {
		FLG_VALIDO = fLG_VALIDO;
	}
	public Character getCODI_FK_ID_MOTIVO() {
		return CODI_FK_ID_MOTIVO;
	}
	public void setCODI_FK_ID_MOTIVO(Character cODI_FK_ID_MOTIVO) {
		CODI_FK_ID_MOTIVO = cODI_FK_ID_MOTIVO;
	}
	public Character getFLG_ATTO_ORIG() {
		return FLG_ATTO_ORIG;
	}
	public void setFLG_ATTO_ORIG(Character fLG_ATTO_ORIG) {
		FLG_ATTO_ORIG = fLG_ATTO_ORIG;
	}
	public Character getFLG_ASSOCIA_PDOC() {
		return FLG_ASSOCIA_PDOC;
	}
	public void setFLG_ASSOCIA_PDOC(Character fLG_ASSOCIA_PDOC) {
		FLG_ASSOCIA_PDOC = fLG_ASSOCIA_PDOC;
	}
	public String getDESC_NOTE_PREC() {
		return DESC_NOTE_PREC;
	}
	public void setDESC_NOTE_PREC(String dESC_NOTE_PREC) {
		DESC_NOTE_PREC = dESC_NOTE_PREC;
	}
	public String getDESC_UTENTE() {
		return DESC_UTENTE;
	}
	public void setDESC_UTENTE(String dESC_UTENTE) {
		DESC_UTENTE = dESC_UTENTE;
	}
	public Date getDATA_ULT_AGG() {
		return DATA_ULT_AGG;
	}
	public void setDATA_ULT_AGG(Date dATA_ULT_AGG) {
		DATA_ULT_AGG = dATA_ULT_AGG;
	}
	public BigDecimal getNUME_COPIA_PDOC() {
		return NUME_COPIA_PDOC;
	}
	public void setNUME_COPIA_PDOC(BigDecimal nUME_COPIA_PDOC) {
		NUME_COPIA_PDOC = nUME_COPIA_PDOC;
	}
	public BigDecimal getNUME_CONV_PDOC() {
		return NUME_CONV_PDOC;
	}
	public void setNUME_CONV_PDOC(BigDecimal nUME_CONV_PDOC) {
		NUME_CONV_PDOC = nUME_CONV_PDOC;
	}
	public Character getFLG_ANBSC() {
		return FLG_ANBSC;
	}
	public void setFLG_ANBSC(Character fLG_ANBSC) {
		FLG_ANBSC = fLG_ANBSC;
	}
	public Character getFLG_MPS() {
		return FLG_MPS;
	}
	public void setFLG_MPS(Character fLG_MPS) {
		FLG_MPS = fLG_MPS;
	}
	
}
