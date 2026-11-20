package it.giustizia.smp.dto.pg_conservatorie;

import java.math.BigDecimal;
import java.util.Date;

public class NotaTrascrizioneDto {

	private BigDecimal CODI_K_ID_NOTA;
	private BigDecimal CODI_K_ID_UFF_OWNER;
	private Character  FLG_VOLTURA;
	private Character  FLG_VOLTURA_DIFF;
	private Date       DATA_VOLTURA_DIFF;
	private Character  FLG_FOGLIO_INFO;
	private Character  FLG_CONDIZIONE;
	private Character  FLG_TERMINI;
	private Date       DATA_FORMALITA;
	private BigDecimal NUME_FORMALITA_1;
	private BigDecimal NUME_FORMALITA_2;
	private Character  FLG_QUADROD_A;
	private Character  FLG_QUADROD_B;
	private Character  FLG_QUADROD_C;
	
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
	public Character getFLG_VOLTURA_DIFF() {
		return FLG_VOLTURA_DIFF;
	}
	public void setFLG_VOLTURA_DIFF(Character fLG_VOLTURA_DIFF) {
		FLG_VOLTURA_DIFF = fLG_VOLTURA_DIFF;
	}
	public Date getDATA_VOLTURA_DIFF() {
		return DATA_VOLTURA_DIFF;
	}
	public void setDATA_VOLTURA_DIFF(Date dATA_VOLTURA_DIFF) {
		DATA_VOLTURA_DIFF = dATA_VOLTURA_DIFF;
	}
	public Character getFLG_FOGLIO_INFO() {
		return FLG_FOGLIO_INFO;
	}
	public void setFLG_FOGLIO_INFO(Character fLG_FOGLIO_INFO) {
		FLG_FOGLIO_INFO = fLG_FOGLIO_INFO;
	}
	public Character getFLG_CONDIZIONE() {
		return FLG_CONDIZIONE;
	}
	public void setFLG_CONDIZIONE(Character fLG_CONDIZIONE) {
		FLG_CONDIZIONE = fLG_CONDIZIONE;
	}
	public Character getFLG_TERMINI() {
		return FLG_TERMINI;
	}
	public void setFLG_TERMINI(Character fLG_TERMINI) {
		FLG_TERMINI = fLG_TERMINI;
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
	public Character getFLG_QUADROD_A() {
		return FLG_QUADROD_A;
	}
	public void setFLG_QUADROD_A(Character fLG_QUADROD_A) {
		FLG_QUADROD_A = fLG_QUADROD_A;
	}
	public Character getFLG_QUADROD_B() {
		return FLG_QUADROD_B;
	}
	public void setFLG_QUADROD_B(Character fLG_QUADROD_B) {
		FLG_QUADROD_B = fLG_QUADROD_B;
	}
	public Character getFLG_QUADROD_C() {
		return FLG_QUADROD_C;
	}
	public void setFLG_QUADROD_C(Character fLG_QUADROD_C) {
		FLG_QUADROD_C = fLG_QUADROD_C;
	}
}
