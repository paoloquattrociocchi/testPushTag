package it.giustizia.smp.dto.pg_procedimento;

import java.math.BigDecimal;
import java.util.Date;

public class ListaProcedimentiSoggDto {

	private BigDecimal CODI_K_ID_PROC; 
	private BigDecimal NUME_ANNO_PROC;
	private BigDecimal NUME_PROC;
	private Date DATA_ISCRIZIONE; 
    private String DESC_STATO_PROC;
    private BigDecimal CODI_K_ID_TIPO_FASC;
    private String DESC_TIPO_FASC;
    private String DESC_RUOLO;
    
    public BigDecimal getCODI_K_ID_PROC() {
		return CODI_K_ID_PROC;
	}
	public void setCODI_K_ID_PROC(BigDecimal cODI_K_ID_PROC) {
		CODI_K_ID_PROC = cODI_K_ID_PROC;
	}
	public BigDecimal getNUME_ANNO_PROC() {
		return NUME_ANNO_PROC;
	}
	public void setNUME_ANNO_PROC(BigDecimal nUME_ANNO_PROC) {
		NUME_ANNO_PROC = nUME_ANNO_PROC;
	}
	public BigDecimal getNUME_PROC() {
		return NUME_PROC;
	}
	public void setNUME_PROC(BigDecimal nUME_PROC) {
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
	public String getDESC_TIPO_FASC() {
		return DESC_TIPO_FASC;
	}
	public void setDESC_TIPO_FASC(String dESC_TIPO_FASC) {
		DESC_TIPO_FASC = dESC_TIPO_FASC;
	}
	public String getDESC_RUOLO() {
		return DESC_RUOLO;
	}
	public void setDESC_RUOLO(String dESC_RUOLO) {
		DESC_RUOLO = dESC_RUOLO;
	}
	public BigDecimal getCODI_K_ID_TIPO_FASC() {
		return CODI_K_ID_TIPO_FASC;
	}
	public void setCODI_K_ID_TIPO_FASC(BigDecimal cODI_K_ID_TIPO_FASC) {
		CODI_K_ID_TIPO_FASC = cODI_K_ID_TIPO_FASC;
	}    
    
}
