package it.giustizia.smp.dto.pg_conservatorie;

import java.math.BigDecimal;

public class TipoCategAttiDto {
	
	private BigDecimal CODI_K_ID_CAT_ATTO;  	 //categoria atto 
	private BigDecimal CODI_FK_ID_TIPO_ATTO;  	 //tipo categoria atto
	private String     DESC_CAT_ATTO;  			 //descrizione categorai atto 
	private Character  FLG_DECISORIO;			 //flag decisorio

	public BigDecimal getCODI_K_ID_CAT_ATTO() {
		return CODI_K_ID_CAT_ATTO;
	}
	public void setCODI_K_ID_CAT_ATTO(BigDecimal cODI_K_ID_CAT_ATTO) {
		CODI_K_ID_CAT_ATTO = cODI_K_ID_CAT_ATTO;
	}
	public BigDecimal getCODI_FK_ID_TIPO_ATTO() {
		return CODI_FK_ID_TIPO_ATTO;
	}
	public void setCODI_FK_ID_TIPO_ATTO(BigDecimal cODI_FK_ID_TIPO_ATTO) {
		CODI_FK_ID_TIPO_ATTO = cODI_FK_ID_TIPO_ATTO;
	}
	public String getDESC_CAT_ATTO() {
		return DESC_CAT_ATTO;
	}
	public void setDESC_CAT_ATTO(String dESC_CAT_ATTO) {
		DESC_CAT_ATTO = dESC_CAT_ATTO;
	}
	public Character getFLG_DECISORIO() {
		return FLG_DECISORIO;
	}
	public void setFLG_DECISORIO(Character fLG_DECISORIO) {
		FLG_DECISORIO = fLG_DECISORIO;
	}
}
