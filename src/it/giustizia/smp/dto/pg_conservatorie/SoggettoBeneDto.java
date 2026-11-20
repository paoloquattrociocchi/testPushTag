package it.giustizia.smp.dto.pg_conservatorie;

import java.math.BigDecimal;
import java.util.Date;

public class SoggettoBeneDto {
	
	private BigDecimal NUME_QUOTA;               //T T_REL_BENE_ATTO.NUME_QUOTA,
	private BigDecimal NUME_FRAZIONE_NUM;        //T_REL_BENE_ATTO.NUME_FRAZIONE_NUM,
	private BigDecimal NUME_FRAZIONE_DEN;	     //T_REL_BENE_ATTO.NUME_FRAZIONE_DEN,
	private BigDecimal CODI_K_ID_SOGG_INT;	     //T_SOGGETTO.CODI_K_ID_SOGG_INT,
	private BigDecimal CODI_K_ID_TIPO_INT;	     //T_REL_BENE_ATTO.CODI_K_ID_TIPO_INT,
	private String 	   DESC_TIPO_INT;            //T_TP_TIPO_INTERESSE.DESC_TIPO_INT ,
	private BigDecimal CODI_FK_ID_VER;           //T_REL_PROC_SOGINT.CODI_FK_ID_VER ,
	private Character  CODI_FK_ID_TIPO_SOGG;     //T_SOGGETTO.CODI_FK_ID_TIPO_SOGG 
	
	public BigDecimal getNUME_QUOTA() {
		return NUME_QUOTA;
	}
	public void setNUME_QUOTA(BigDecimal nUME_QUOTA) {
		NUME_QUOTA = nUME_QUOTA;
	}
	public BigDecimal getNUME_FRAZIONE_NUM() {
		return NUME_FRAZIONE_NUM;
	}
	public void setNUME_FRAZIONE_NUM(BigDecimal nUME_FRAZIONE_NUM) {
		NUME_FRAZIONE_NUM = nUME_FRAZIONE_NUM;
	}
	public BigDecimal getNUME_FRAZIONE_DEN() {
		return NUME_FRAZIONE_DEN;
	}
	public void setNUME_FRAZIONE_DEN(BigDecimal nUME_FRAZIONE_DEN) {
		NUME_FRAZIONE_DEN = nUME_FRAZIONE_DEN;
	}
	public BigDecimal getCODI_K_ID_SOGG_INT() {
		return CODI_K_ID_SOGG_INT;
	}
	public void setCODI_K_ID_SOGG_INT(BigDecimal cODI_K_ID_SOGG_INT) {
		CODI_K_ID_SOGG_INT = cODI_K_ID_SOGG_INT;
	}
	public BigDecimal getCODI_K_ID_TIPO_INT() {
		return CODI_K_ID_TIPO_INT;
	}
	public void setCODI_K_ID_TIPO_INT(BigDecimal cODI_K_ID_TIPO_INT) {
		CODI_K_ID_TIPO_INT = cODI_K_ID_TIPO_INT;
	}
	public String getDESC_TIPO_INT() {
		return DESC_TIPO_INT;
	}
	public void setDESC_TIPO_INT(String dESC_TIPO_INT) {
		DESC_TIPO_INT = dESC_TIPO_INT;
	}
	public BigDecimal getCODI_FK_ID_VER() {
		return CODI_FK_ID_VER;
	}
	public void setCODI_FK_ID_VER(BigDecimal cODI_FK_ID_VER) {
		CODI_FK_ID_VER = cODI_FK_ID_VER;
	}
	public Character getCODI_FK_ID_TIPO_SOGG() {
		return CODI_FK_ID_TIPO_SOGG;
	}
	public void setCODI_FK_ID_TIPO_SOGG(Character cODI_FK_ID_TIPO_SOGG) {
		CODI_FK_ID_TIPO_SOGG = cODI_FK_ID_TIPO_SOGG;
	}
	
}
