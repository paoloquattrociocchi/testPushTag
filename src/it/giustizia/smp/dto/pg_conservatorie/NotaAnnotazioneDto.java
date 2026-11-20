package it.giustizia.smp.dto.pg_conservatorie;

import java.math.BigDecimal;
import java.util.Date;

public class NotaAnnotazioneDto {

	private BigDecimal CODI_K_ID_NOTA;
	private BigDecimal CODI_K_ID_UFF_OWNER;
	private Character  FLG_VOLTURA;
	private Date       DATA_FORMALITA;
	private BigDecimal NUME_FORMALITA_1;
	private BigDecimal NUME_FORMALITA_2;
	private BigDecimal NUME_VALORE_IMM_INT;
	private BigDecimal NUME_VALORE_IMM_DEC;
	
	public BigDecimal getCODI_K_ID_NOTA() {
		return CODI_K_ID_NOTA;
	}
	public void setCODI_K_ID_NOTA(BigDecimal cODI_K_ID_NOTA) {
		CODI_K_ID_NOTA = cODI_K_ID_NOTA;
	}
	public BigDecimal getCODI_K_ID_UFF_OWNER() {
		return CODI_K_ID_UFF_OWNER;
	}
	public void setCODI_K_ID_UFF_OWNER(BigDecimal cODI_K_ID_UFF_OWNER) {
		CODI_K_ID_UFF_OWNER = cODI_K_ID_UFF_OWNER;
	}
	public Character getFLG_VOLTURA() {
		return FLG_VOLTURA;
	}
	public void setFLG_VOLTURA(Character fLG_VOLTURA) {
		FLG_VOLTURA = fLG_VOLTURA;
	}
	public Date getDATA_FORMALITA() {
		return DATA_FORMALITA;
	}
	public void setDATA_FORMALITA(Date dATA_FORMALITA) {
		DATA_FORMALITA = dATA_FORMALITA;
	}
	public BigDecimal getNUME_FORMALITA_1() {
		return NUME_FORMALITA_1;
	}
	public void setNUME_FORMALITA_1(BigDecimal nUME_FORMALITA_1) {
		NUME_FORMALITA_1 = nUME_FORMALITA_1;
	}
	public BigDecimal getNUME_FORMALITA_2() {
		return NUME_FORMALITA_2;
	}
	public void setNUME_FORMALITA_2(BigDecimal nUME_FORMALITA_2) {
		NUME_FORMALITA_2 = nUME_FORMALITA_2;
	}
	public BigDecimal getNUME_VALORE_IMM_INT() {
		return NUME_VALORE_IMM_INT;
	}
	public void setNUME_VALORE_IMM_INT(BigDecimal nUME_VALORE_IMM_INT) {
		NUME_VALORE_IMM_INT = nUME_VALORE_IMM_INT;
	}
	public BigDecimal getNUME_VALORE_IMM_DEC() {
		return NUME_VALORE_IMM_DEC;
	}
	public void setNUME_VALORE_IMM_DEC(BigDecimal nUME_VALORE_IMM_DEC) {
		NUME_VALORE_IMM_DEC = nUME_VALORE_IMM_DEC;
	}

}
