package it.giustizia.smp.dto.pg_procedimento;

import java.util.Date;

public class RicercaProcedimentoDto {

    private java.math.BigDecimal CODI_K_ID_PROC;
    private java.math.BigDecimal NUME_ANNO_PROC;
    private java.math.BigDecimal NUME_PROC;
    private Date DATA_ISCRIZIONE;
	private String DESC_STATO_PROC;
	private java.math.BigDecimal CODI_K_ID_TIPO_FASC;
	private String DESC_TIPO_FASC;
	
	private String NUM_PROCURA;
	private String NUM_TRIBUNALE;
	private String NUM_PG;
	private String NUM_CDA;	
	
	public String getNUM_PROCURA() {
		return NUM_PROCURA;
	}
	public void setNUM_PROCURA(String nUM_PROCURA) {
		NUM_PROCURA = nUM_PROCURA;
	}
	public String getNUM_TRIBUNALE() {
		return NUM_TRIBUNALE;
	}
	public void setNUM_TRIBUNALE(String nUM_TRIBUNALE) {
		NUM_TRIBUNALE = nUM_TRIBUNALE;
	}
	public String getNUM_PG() {
		return NUM_PG;
	}
	public void setNUM_PG(String nUM_PG) {
		NUM_PG = nUM_PG;
	}
	public String getNUM_CDA() {
		return NUM_CDA;
	}
	public void setNUM_CDA(String nUM_CDA) {
		NUM_CDA = nUM_CDA;
	}
	public java.math.BigDecimal getNUME_ANNO_PROC() {
		return NUME_ANNO_PROC;
	}
	public void setNUME_ANNO_PROC(java.math.BigDecimal nUME_ANNO_PROC) {
		NUME_ANNO_PROC = nUME_ANNO_PROC;
	}
	public java.math.BigDecimal getNUME_PROC() {
		return NUME_PROC;
	}
	public void setNUME_PROC(java.math.BigDecimal nUME_PROC) {
		NUME_PROC = nUME_PROC;
	}
	public Date getDATA_ISCRIZIONE() {
		return DATA_ISCRIZIONE;
	}
	public void setDATA_ISCRIZIONE(Date dATA_ISCRIZIONE) {
		DATA_ISCRIZIONE = dATA_ISCRIZIONE;
	}
	public String getDESC_STATO_PROC() {
		return DESC_STATO_PROC;
	}
	public void setDESC_STATO_PROC(String dESC_STATO_PROC) {
		DESC_STATO_PROC = dESC_STATO_PROC;
	}
	public java.math.BigDecimal getCODI_K_ID_PROC() {
		return CODI_K_ID_PROC;
	}
	public void setCODI_K_ID_PROC(java.math.BigDecimal cODI_K_ID_PROC) {
		CODI_K_ID_PROC = cODI_K_ID_PROC;
	}
	public String getDESC_TIPO_FASC() {
		return DESC_TIPO_FASC;
	}
	public void setDESC_TIPO_FASC(String dESC_TIPO_FASC) {
		DESC_TIPO_FASC = dESC_TIPO_FASC;
	}
	public java.math.BigDecimal getCODI_K_ID_TIPO_FASC() {
		return CODI_K_ID_TIPO_FASC;
	}
	public void setCODI_K_ID_TIPO_FASC(java.math.BigDecimal cODI_K_ID_TIPO_FASC) {
		CODI_K_ID_TIPO_FASC = cODI_K_ID_TIPO_FASC;
	}

}
