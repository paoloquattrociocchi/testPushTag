package it.giustizia.smp.dto;

import java.math.BigDecimal;
import java.util.Date;

public class TRelBeneBeneDto {

	private BigDecimal CODI_K_ID_PROC;
	private BigDecimal CODI_K_ID_BENE1;
	private BigDecimal CODI_K_ID_BENE2;
	private BigDecimal CODI_K_ID_UFF_OWNER;
	private BigDecimal CODI_FK_ID_ATTO;
	private Date DATA_TRASF;
	private BigDecimal CODI_FK_ID_TIPO_TRASF;
	private Character FLG_VALIDO;
	private Character CODI_FK_ID_MOTIVO;
	private String DESC_UTENTE;
	private Date DATA_ULT_AGG;

	public BigDecimal getCODI_K_ID_PROC() {
		return CODI_K_ID_PROC;
	}

	public void setCODI_K_ID_PROC(BigDecimal cODI_K_ID_PROC) {
		CODI_K_ID_PROC = cODI_K_ID_PROC;
	}

	public BigDecimal getCODI_K_ID_BENE1() {
		return CODI_K_ID_BENE1;
	}

	public void setCODI_K_ID_BENE1(BigDecimal cODI_K_ID_BENE1) {
		CODI_K_ID_BENE1 = cODI_K_ID_BENE1;
	}

	public BigDecimal getCODI_K_ID_BENE2() {
		return CODI_K_ID_BENE2;
	}

	public void setCODI_K_ID_BENE2(BigDecimal cODI_K_ID_BENE2) {
		CODI_K_ID_BENE2 = cODI_K_ID_BENE2;
	}

	public BigDecimal getCODI_K_ID_UFF_OWNER() {
		return CODI_K_ID_UFF_OWNER;
	}

	public void setCODI_K_ID_UFF_OWNER(BigDecimal cODI_K_ID_UFF_OWNER) {
		CODI_K_ID_UFF_OWNER = cODI_K_ID_UFF_OWNER;
	}

	public BigDecimal getCODI_FK_ID_ATTO() {
		return CODI_FK_ID_ATTO;
	}

	public void setCODI_FK_ID_ATTO(BigDecimal cODI_FK_ID_ATTO) {
		CODI_FK_ID_ATTO = cODI_FK_ID_ATTO;
	}

	public Date getDATA_TRASF() {
		return DATA_TRASF;
	}

	public void setDATA_TRASF(Date dATA_TRASF) {
		DATA_TRASF = dATA_TRASF;
	}

	public BigDecimal getCODI_FK_ID_TIPO_TRASF() {
		return CODI_FK_ID_TIPO_TRASF;
	}

	public void setCODI_FK_ID_TIPO_TRASF(BigDecimal cODI_FK_ID_TIPO_TRASF) {
		CODI_FK_ID_TIPO_TRASF = cODI_FK_ID_TIPO_TRASF;
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

}
