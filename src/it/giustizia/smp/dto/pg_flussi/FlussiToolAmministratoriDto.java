/**
 * 
 */
package it.giustizia.smp.dto.pg_flussi;

import it.giustizia.smp.form.SoggettoForm;

import java.math.BigDecimal;
import java.util.Date;


public class FlussiToolAmministratoriDto {

	private BigDecimal ID_DEPOSITO;
	private Date       DATA_RICEZIONE;
	
	private BigDecimal ID_PROC_FG; //fascicolo gestione
	private String     DESC_PROC_FG;

	private BigDecimal ID_PROC;    //fascicolo merito
	private String     DESC_PROC;

	private String 	   DESC_CODICE_FISCALE;
	private String     DESC_COGNOME;
	private String     DESC_NOME;
	private BigDecimal ID_UFF_MIT;
	//private BigDecimal CODI_K_ID_AMMIN;
	
	public BigDecimal getID_DEPOSITO() {
		return ID_DEPOSITO;
	}
	public void setID_DEPOSITO(BigDecimal IDDEPOSITO) {
		ID_DEPOSITO = IDDEPOSITO;
	}
	public Date getDATA_RICEZIONE() {
		return DATA_RICEZIONE;
	}
	public void setDATA_RICEZIONE(Date DATARICEZIONE) {
		DATA_RICEZIONE = DATARICEZIONE;
	}
	public BigDecimal getID_PROC_FG() {
		return ID_PROC_FG;
	}
	public void setID_PROC_FG(BigDecimal ID_PROCFG) {
		ID_PROC_FG = ID_PROCFG;
	}
	public String getDESC_PROC_FG() {
		return DESC_PROC_FG;
	}
	public void setDESC_PROC_FG(String DESC_PROCFG) {
		DESC_PROC_FG = DESC_PROCFG;
	}
	public BigDecimal getID_PROC() {
		return ID_PROC;
	}
	public void setID_PROC(BigDecimal IDPROC) {
		ID_PROC = IDPROC;
	}
	public String getDESC_PROC() {
		return DESC_PROC;
	}
	public void setDESC_PROC(String DESCPROC) {
		DESC_PROC = DESCPROC;
	}

	public String getDESC_CODICE_FISCALE() {
		return DESC_CODICE_FISCALE;
	}
	public void setDESC_CODICE_FISCALE(String DESC_CF) {
		DESC_CODICE_FISCALE = DESC_CF;
	}
	public String getDESC_COGNOME() {
		return DESC_COGNOME;
	}
	public void setDESC_COGNOME(String COGNOME) {
		DESC_COGNOME = COGNOME;
	}
	public String getDESC_NOME() {
		return DESC_NOME;
	}
	public void setDESC_NOME(String NOME) {
		DESC_NOME = NOME;
	}
	public BigDecimal getID_UFF_MIT() {
		return ID_UFF_MIT;
	}
	public void setID_UFF_MIT(BigDecimal IDUFFMIT) {
		ID_UFF_MIT = IDUFFMIT;
	}
	//public BigDecimal getCODI_K_ID_AMMIN() {
	//	return CODI_K_ID_AMMIN;
	//}
	//public void setCODI_K_ID_AMMIN(BigDecimal IDAMM) {
	//	CODI_K_ID_AMMIN = IDAMM;
	//}

}
