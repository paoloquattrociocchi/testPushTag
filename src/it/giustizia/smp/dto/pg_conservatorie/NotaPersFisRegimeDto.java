package it.giustizia.smp.dto.pg_conservatorie;

import java.math.BigDecimal;

public class NotaPersFisRegimeDto 
{
    private BigDecimal  CODI_K_ID_NOTA;   
    private BigDecimal  CODI_K_ID_UFF_OWNER;   
    private Character   CODI_K_TIPO_RECORD;   
    private BigDecimal  CODI_K_PROGR_SOGG;
    private BigDecimal	CODI_FK_PROGR_SOGG_C;
    private String      DESC_CODI_FISCALE;
    private String      DESC_COGNOME;
    private String      DESC_NOME;
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
	public Character getCODI_K_TIPO_RECORD() {
		return CODI_K_TIPO_RECORD;
	}
	public void setCODI_K_TIPO_RECORD(Character cODI_K_TIPO_RECORD) {
		CODI_K_TIPO_RECORD = cODI_K_TIPO_RECORD;
	}
	public BigDecimal getCODI_K_PROGR_SOGG() {
		return CODI_K_PROGR_SOGG;
	}
	public void setCODI_K_PROGR_SOGG(BigDecimal cODI_K_PROGR_SOGG) {
		CODI_K_PROGR_SOGG = cODI_K_PROGR_SOGG;
	}
	public BigDecimal getCODI_FK_PROGR_SOGG_C() {
		return CODI_FK_PROGR_SOGG_C;
	}
	public void setCODI_FK_PROGR_SOGG_C(BigDecimal cODI_FK_PROGR_SOGG_C) {
		CODI_FK_PROGR_SOGG_C = cODI_FK_PROGR_SOGG_C;
	}
	public String getDESC_CODI_FISCALE() {
		return DESC_CODI_FISCALE;
	}
	public void setDESC_CODI_FISCALE(String dESC_CODI_FISCALE) {
		DESC_CODI_FISCALE = dESC_CODI_FISCALE;
	}
	public String getDESC_COGNOME() {
		return DESC_COGNOME;
	}
	public void setDESC_COGNOME(String dESC_COGNOME) {
		DESC_COGNOME = dESC_COGNOME;
	}
	public String getDESC_NOME() {
		return DESC_NOME;
	}
	public void setDESC_NOME(String dESC_NOME) {
		DESC_NOME = dESC_NOME;
	}
}
