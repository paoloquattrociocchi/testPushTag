package it.giustizia.smp.dto.pg_conservatorie;

import java.math.BigDecimal;
import java.util.Date;

public class BeneAssociabileDto {
	
	private BigDecimal CODI_K_ID_BENE;  		//identificativo bene in SitMP(T_REL_BENE_ATTO.CODI_K_ID_BENE )
	private String     DESCR_BENE;				//descrizione del bene in SitMP(T_BENE. DESCR_BENE) 
	private String     DESC_BREVE_CATASTO;		//descrizione catastale del bene in SitMP (T_TP_TIPO_CATASTO.DESC_BREVE_CATASTO) 
	private String     DESCR_CATASTO;		    //descrizione catastale del bene in SitMP (T_TP_TIPO_CATASTO.DESC_CATASTO) 
	private Character  CODI_FK_ID_TIPO_CAT;		//campo da non visualizzare
	private String     DESC_CATASTO;			//Codice del catasto(T_BENE_IMMOBILE.DESC_CATASTO)
	private String     DESC_COMUNE;				//Descrizione del comune in cui è localizzato il bene (T_AN_COMUNI.DESC_COMUNE) 
	private BigDecimal CODI_K_ID_PROC;			//chiave del procedimento T_REL_BENE_ATTO.CODI_K_ID_PROC)
	private BigDecimal CODI_FK_ID_COMUNE;       //codice del comune del bene T_BENE.CODI_FK_ID_COMUNE
	private BigDecimal CODI_K_ID_CONSERV;       //codice della conservatoria del comune del bene T_AN_COMUNI_CONSERV.CODI_K_ID_CONSERV
	private String     DESC_CONSERV;            //descrizione della conservatoria T_AN_CONSERVATORIE.DESC_CONSERV
	
	public BigDecimal getCODI_K_ID_BENE() {
		return CODI_K_ID_BENE;
	}
	public void setCODI_K_ID_BENE(BigDecimal cODI_K_ID_BENE) {
		CODI_K_ID_BENE = cODI_K_ID_BENE;
	}
	public String getDESCR_BENE() {
		return DESCR_BENE;
	}
	public void setDESCR_BENE(String dESCR_BENE) {
		DESCR_BENE = dESCR_BENE;
	}
	public String getDESC_BREVE_CATASTO() {
		return DESC_BREVE_CATASTO;
	}
	public void setDESC_BREVE_CATASTO(String dESC_BREVE_CATASTO) {
		DESC_BREVE_CATASTO = dESC_BREVE_CATASTO;
	}
	public String getDESCR_CATASTO() {
		return DESCR_CATASTO;
	}
	public void setDESCR_CATASTO(String dESCR_CATASTO) {
		DESCR_CATASTO = dESCR_CATASTO;
	}
	public Character getCODI_FK_ID_TIPO_CAT() {
		return CODI_FK_ID_TIPO_CAT;
	}
	public void setCODI_FK_ID_TIPO_CAT(Character cODI_FK_ID_TIPO_CAT) {
		CODI_FK_ID_TIPO_CAT = cODI_FK_ID_TIPO_CAT;
	}
	public String getDESC_CATASTO() {
		return DESC_CATASTO;
	}
	public void setDESC_CATASTO(String dESC_CATASTO) {
		DESC_CATASTO = dESC_CATASTO;
	}
	public String getDESC_COMUNE() {
		return DESC_COMUNE;
	}
	public void setDESC_COMUNE(String dESC_COMUNE) {
		DESC_COMUNE = dESC_COMUNE;
	}
	public BigDecimal getCODI_K_ID_PROC() {
		return CODI_K_ID_PROC;
	}
	public void setCODI_K_ID_PROC(BigDecimal cODI_K_ID_PROC) {
		CODI_K_ID_PROC = cODI_K_ID_PROC;
	}
	public BigDecimal getCODI_FK_ID_COMUNE() {
		return CODI_FK_ID_COMUNE;
	}
	public void setCODI_FK_ID_COMUNE(BigDecimal cODI_FK_ID_COMUNE) {
		CODI_FK_ID_COMUNE = cODI_FK_ID_COMUNE;
	}
	public BigDecimal getCODI_K_ID_CONSERV() {
		return CODI_K_ID_CONSERV;
	}
	public void setCODI_K_ID_CONSERV(BigDecimal cODI_K_ID_CONSERV) {
		CODI_K_ID_CONSERV = cODI_K_ID_CONSERV;
	}
	public String getDESC_CONSERV() {
		return DESC_CONSERV;
	}
	public void setDESC_CONSERV(String dESC_CONSERV) {
		DESC_CONSERV = dESC_CONSERV;
	}

}
