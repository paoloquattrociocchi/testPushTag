package it.giustizia.smp.dto.pg_cameralini;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author GF020115
 *
 */
public class ProcedimentoDto {
	
	private BigDecimal CODI_K_ID_PROC;
	private BigDecimal CODI_FK_ID_UFF_OWNER;
	private BigDecimal NUME_ANNO_PROC;
	private BigDecimal NUME_PROC;
	private BigDecimal CODI_FK_ID_TIPO_FASC;
	private BigDecimal CODI_FK_ID_TIPO_PROC;
	private BigDecimal CODI_FK_ID_STATO_PROC;
	private BigDecimal CODI_FK_ID_PROCED;
	private String DESC_ALTRO_PROCED;
    private Date DATA_ISCRIZIONE;
    private Date DATA_REST_TRIB;
    private Date DATA_TRASM_CDA;
    private BigDecimal CODI_FK_ID_PU;
    private Character FLG_PROC_MAFIA;
    private Date DATA_ACCERT_EMIS;
    private Date DATA_ACCERT_DEP;
    private String DESC_ACCERT_NUME;
    private Character FLG_RICONCILIATO;
    private Character FLG_ORDINARIA;
    private Character FLG_ANTIMAFIA;
    private Character FLG_ANTITERRORISMO;
    private String DESC_FASCICOLO_PDOC;
    private String DESC_UTENTE;
    private Date DATA_ULT_AGG;
    private Date DATA_DEFINITIVITA;
    private String DESC_TIPO_PROC;
    private String DESC_STATO_PROC;
    private Date CODI_K_ID_DATA_ASS;
    
	public BigDecimal getCODI_K_ID_PROC() {
		return CODI_K_ID_PROC;
	}
	public void setCODI_K_ID_PROC(BigDecimal cODI_K_ID_PROC) {
		CODI_K_ID_PROC = cODI_K_ID_PROC;
	}
	public BigDecimal getCODI_FK_ID_UFF_OWNER() {
		return CODI_FK_ID_UFF_OWNER;
	}
	public void setCODI_FK_ID_UFF_OWNER(BigDecimal cODI_FK_ID_UFF_OWNER) {
		CODI_FK_ID_UFF_OWNER = cODI_FK_ID_UFF_OWNER;
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
	public BigDecimal getCODI_FK_ID_TIPO_FASC() {
		return CODI_FK_ID_TIPO_FASC;
	}
	public void setCODI_FK_ID_TIPO_FASC(BigDecimal cODI_FK_ID_TIPO_FASC) {
		CODI_FK_ID_TIPO_FASC = cODI_FK_ID_TIPO_FASC;
	}
	public BigDecimal getCODI_FK_ID_TIPO_PROC() {
		return CODI_FK_ID_TIPO_PROC;
	}
	public void setCODI_FK_ID_TIPO_PROC(BigDecimal cODI_FK_ID_TIPO_PROC) {
		CODI_FK_ID_TIPO_PROC = cODI_FK_ID_TIPO_PROC;
	}
	public BigDecimal getCODI_FK_ID_STATO_PROC() {
		return CODI_FK_ID_STATO_PROC;
	}
	public void setCODI_FK_ID_STATO_PROC(BigDecimal cODI_FK_ID_STATO_PROC) {
		CODI_FK_ID_STATO_PROC = cODI_FK_ID_STATO_PROC;
	}
	public BigDecimal getCODI_FK_ID_PROCED() {
		return CODI_FK_ID_PROCED;
	}
	public void setCODI_FK_ID_PROCED(BigDecimal cODI_FK_ID_PROCED) {
		CODI_FK_ID_PROCED = cODI_FK_ID_PROCED;
	}
	public String getDESC_ALTRO_PROCED() {
		return DESC_ALTRO_PROCED;
	}
	public void setDESC_ALTRO_PROCED(String dESC_ALTRO_PROCED) {
		DESC_ALTRO_PROCED = dESC_ALTRO_PROCED;
	}
	public Date getDATA_ISCRIZIONE() {
		return DATA_ISCRIZIONE;
	}
	public void setDATA_ISCRIZIONE(Date dATA_ISCRIZIONE) {
		DATA_ISCRIZIONE = dATA_ISCRIZIONE;
	}
	public Date getDATA_REST_TRIB() {
		return DATA_REST_TRIB;
	}
	public void setDATA_REST_TRIB(Date dATA_REST_TRIB) {
		DATA_REST_TRIB = dATA_REST_TRIB;
	}
	public Date getDATA_TRASM_CDA() {
		return DATA_TRASM_CDA;
	}
	public void setDATA_TRASM_CDA(Date dATA_TRASM_CDA) {
		DATA_TRASM_CDA = dATA_TRASM_CDA;
	}
	public BigDecimal getCODI_FK_ID_PU() {
		return CODI_FK_ID_PU;
	}
	public void setCODI_FK_ID_PU(BigDecimal cODI_FK_ID_PU) {
		CODI_FK_ID_PU = cODI_FK_ID_PU;
	}
	public Character getFLG_PROC_MAFIA() {
		return FLG_PROC_MAFIA;
	}
	public void setFLG_PROC_MAFIA(Character fLG_PROC_MAFIA) {
		FLG_PROC_MAFIA = fLG_PROC_MAFIA;
	}
	public Date getDATA_ACCERT_EMIS() {
		return DATA_ACCERT_EMIS;
	}
	public void setDATA_ACCERT_EMIS(Date dATA_ACCERT_EMIS) {
		DATA_ACCERT_EMIS = dATA_ACCERT_EMIS;
	}
	public Date getDATA_ACCERT_DEP() {
		return DATA_ACCERT_DEP;
	}
	public void setDATA_ACCERT_DEP(Date dATA_ACCERT_DEP) {
		DATA_ACCERT_DEP = dATA_ACCERT_DEP;
	}
	public String getDESC_ACCERT_NUME() {
		return DESC_ACCERT_NUME;
	}
	public void setDESC_ACCERT_NUME(String dESC_ACCERT_NUME) {
		DESC_ACCERT_NUME = dESC_ACCERT_NUME;
	}
	public Character getFLG_RICONCILIATO() {
		return FLG_RICONCILIATO;
	}
	public void setFLG_RICONCILIATO(Character fLG_RICONCILIATO) {
		FLG_RICONCILIATO = fLG_RICONCILIATO;
	}
	public Character getFLG_ORDINARIA() {
		return FLG_ORDINARIA;
	}
	public void setFLG_ORDINARIA(Character fLG_ORDINARIA) {
		FLG_ORDINARIA = fLG_ORDINARIA;
	}
	public Character getFLG_ANTIMAFIA() {
		return FLG_ANTIMAFIA;
	}
	public void setFLG_ANTIMAFIA(Character fLG_ANTIMAFIA) {
		FLG_ANTIMAFIA = fLG_ANTIMAFIA;
	}
	public Character getFLG_ANTITERRORISMO() {
		return FLG_ANTITERRORISMO;
	}
	public void setFLG_ANTITERRORISMO(Character fLG_ANTITERRORISMO) {
		FLG_ANTITERRORISMO = fLG_ANTITERRORISMO;
	}
	public String getDESC_FASCICOLO_PDOC() {
		return DESC_FASCICOLO_PDOC;
	}
	public void setDESC_FASCICOLO_PDOC(String dESC_FASCICOLO_PDOC) {
		DESC_FASCICOLO_PDOC = dESC_FASCICOLO_PDOC;
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
	public Date getDATA_DEFINITIVITA() {
		return DATA_DEFINITIVITA;
	}
	public void setDATA_DEFINITIVITA(Date dATA_DEFINITIVITA) {
		DATA_DEFINITIVITA = dATA_DEFINITIVITA;
	}
	public String getDESC_TIPO_PROC() {
		return DESC_TIPO_PROC;
	}
	public void setDESC_TIPO_PROC(String dESC_TIPO_PROC) {
		DESC_TIPO_PROC = dESC_TIPO_PROC;
	}
	public String getDESC_STATO_PROC() {
		return DESC_STATO_PROC;
	}
	public void setDESC_STATO_PROC(String dESC_STATO_PROC) {
		DESC_STATO_PROC = dESC_STATO_PROC;
	}
	public Date getCODI_K_ID_DATA_ASS() {
		return CODI_K_ID_DATA_ASS;
	}
	public void setCODI_K_ID_DATA_ASS(Date cODI_K_ID_DATA_ASS) {
		CODI_K_ID_DATA_ASS = cODI_K_ID_DATA_ASS;
	}
    
    
}
