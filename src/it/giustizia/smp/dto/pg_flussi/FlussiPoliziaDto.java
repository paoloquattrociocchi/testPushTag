/**
 * 
 */
package it.giustizia.smp.dto.pg_flussi;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author antonio
 *
 */
public class FlussiPoliziaDto {

	private BigDecimal ID_FLUSSO;
	private Date       DATA_FLUSSO;
	private BigDecimal ID_PROCEDIMENTO; 
	private BigDecimal ID_ATTO;
	
	private BigDecimal ID_SERVIZIO;
	private String     DESC_TIPO_FLUSSO;
	private BigDecimal ID_STATO;
	private String 	   DESC_STATO;
	
	private BigDecimal ID_UFF_MITTENTE;
	private String     DESC_TIPO_UFF_MIT;
	private String     DESC_SEDE_UFF_MIT;
	private String     DESC_UFF_MIT;
	
	private BigDecimal ID_UFF_DESTINATARIO;
	private String     DESC_TIPO_UFF_DEST;
	private String     DESC_SEDE_UFF_DEST;
	private String     DESC_UFF_DEST;
	
	private String 	   NUM_PROC;
	private char     FLG_DIREZIONE;
	
	private BigDecimal ID_CAT_ATTO;
	private String     DESC_CAT_ATTO;
	
	public BigDecimal getID_FLUSSO() {
		return ID_FLUSSO;
	}
	public void setID_FLUSSO(BigDecimal iD_FLUSSO) {
		ID_FLUSSO = iD_FLUSSO;
	}
	public Date getDATA_FLUSSO() {
		return DATA_FLUSSO;
	}
	public void setDATA_FLUSSO(Date dATA_FLUSSO) {
		DATA_FLUSSO = dATA_FLUSSO;
	}
	public BigDecimal getID_PROCEDIMENTO() {
		return ID_PROCEDIMENTO;
	}
	public void setID_PROCEDIMENTO(BigDecimal iD_PROCEDIMENTO) {
		ID_PROCEDIMENTO = iD_PROCEDIMENTO;
	}
	public BigDecimal getID_ATTO() {
		return ID_ATTO;
	}
	public void setID_ATTO(BigDecimal iD_ATTO) {
		ID_ATTO = iD_ATTO;
	}
	public BigDecimal getID_SERVIZIO() {
		return ID_SERVIZIO;
	}
	public void setID_SERVIZIO(BigDecimal iD_SERVIZIO) {
		ID_SERVIZIO = iD_SERVIZIO;
	}
	public String getDESC_TIPO_FLUSSO() {
		return DESC_TIPO_FLUSSO;
	}
	public void setDESC_TIPO_FLUSSO(String dESC_TIPO_FLUSSO) {
		DESC_TIPO_FLUSSO = dESC_TIPO_FLUSSO;
	}
	public BigDecimal getID_STATO() {
		return ID_STATO;
	}
	public void setID_STATO(BigDecimal iD_STATO) {
		ID_STATO = iD_STATO;
	}
	public String getDESC_STATO() {
		return DESC_STATO;
	}
	public void setDESC_STATO(String dESC_STATO) {
		DESC_STATO = dESC_STATO;
	}
	public BigDecimal getID_UFF_MITTENTE() {
		return ID_UFF_MITTENTE;
	}
	public void setID_UFF_MITTENTE(BigDecimal iD_UFF_MITTENTE) {
		ID_UFF_MITTENTE = iD_UFF_MITTENTE;
	}
	public String getDESC_TIPO_UFF_MIT() {
		return DESC_TIPO_UFF_MIT;
	}
	public void setDESC_TIPO_UFF_MIT(String dESC_TIPO_UFF_MIT) {
		DESC_TIPO_UFF_MIT = dESC_TIPO_UFF_MIT;
	}
	public String getDESC_SEDE_UFF_MIT() {
		return DESC_SEDE_UFF_MIT;
	}
	public void setDESC_SEDE_UFF_MIT(String dESC_SEDE_UFF_MIT) {
		DESC_SEDE_UFF_MIT = dESC_SEDE_UFF_MIT;
	}
	public String getDESC_UFF_MIT() {
		return DESC_UFF_MIT;
	}
	public void setDESC_UFF_MIT(String dESC_UFF_MIT) {
		DESC_UFF_MIT = dESC_UFF_MIT;
	}
	public BigDecimal getID_UFF_DESTINATARIO() {
		return ID_UFF_DESTINATARIO;
	}
	public void setID_UFF_DESTINATARIO(BigDecimal iD_UFF_DESTINATARIO) {
		ID_UFF_DESTINATARIO = iD_UFF_DESTINATARIO;
	}
	public String getDESC_TIPO_UFF_DEST() {
		return DESC_TIPO_UFF_DEST;
	}
	public void setDESC_TIPO_UFF_DEST(String dESC_TIPO_UFF_DEST) {
		DESC_TIPO_UFF_DEST = dESC_TIPO_UFF_DEST;
	}
	public String getDESC_SEDE_UFF_DEST() {
		return DESC_SEDE_UFF_DEST;
	}
	public void setDESC_SEDE_UFF_DEST(String dESC_SEDE_UFF_DEST) {
		DESC_SEDE_UFF_DEST = dESC_SEDE_UFF_DEST;
	}
	public String getDESC_UFF_DEST() {
		return DESC_UFF_DEST;
	}
	public void setDESC_UFF_DEST(String dESC_UFF_DEST) {
		DESC_UFF_DEST = dESC_UFF_DEST;
	}
	public String getNUM_PROC() {
		return NUM_PROC;
	}
	public void setNUM_PROC(String nUM_PROC) {
		NUM_PROC = nUM_PROC;
	}
	public char getFLG_DIREZIONE() {
		return FLG_DIREZIONE;
	}
	public void setFLG_DIREZIONE(char fLG_DIREZIONE) {
		FLG_DIREZIONE = fLG_DIREZIONE;
	}
	public BigDecimal getID_CAT_ATTO() {
		return ID_CAT_ATTO;
	}
	public void setID_CAT_ATTO(BigDecimal iD_CAT_ATTO) {
		ID_CAT_ATTO = iD_CAT_ATTO;
	}
	public String getDESC_CAT_ATTO() {
		return DESC_CAT_ATTO;
	}
	public void setDESC_CAT_ATTO(String dESC_CAT_ATTO) {
		DESC_CAT_ATTO = dESC_CAT_ATTO;
	}
	
}
