package it.giustizia.smp.dto.pg_soggetto;

import java.math.BigDecimal;

public class BeniCollegatiTerzoDto {

	private BigDecimal CODI_K_ID_BENE;
	private String DESCR_BENE;
	private String DESC_TIPO_INT;
	private BigDecimal NUME_QUOTA;
	private BigDecimal NUME_FRAZIONE_NUM;
	private BigDecimal NUME_FRAZIONE_DEN; 
	private BigDecimal CODI_K_ID_CAT_ATTO;
    private String DESC_CAT_ATTO;
    private String DESC_TIPO_MISURA;
    private String DESC_ESITO1;
    private String DESC_ESITO2;
    private String DESC_ESITO3;
    
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
	public String getDESC_TIPO_INT() {
		return DESC_TIPO_INT;
	}
	public void setDESC_TIPO_INT(String dESC_TIPO_INT) {
		DESC_TIPO_INT = dESC_TIPO_INT;
	}
	public BigDecimal getNUME_QUOTA() {
		return NUME_QUOTA;
	}
	public void setNUME_QUOTA(BigDecimal nUME_QUOTA) {
		NUME_QUOTA = nUME_QUOTA;
	}
	public BigDecimal getNUME_FRAZIONE_NUM() {
		return NUME_FRAZIONE_NUM;
	}
	public void setNUME_FRAZIONE_NUM(BigDecimal nUME_FRAZIONE_NUM) {
		NUME_FRAZIONE_NUM = nUME_FRAZIONE_NUM;
	}
	public BigDecimal getNUME_FRAZIONE_DEN() {
		return NUME_FRAZIONE_DEN;
	}
	public void setNUME_FRAZIONE_DEN(BigDecimal nUME_FRAZIONE_DEN) {
		NUME_FRAZIONE_DEN = nUME_FRAZIONE_DEN;
	}
	public BigDecimal getCODI_K_ID_CAT_ATTO() {
		return CODI_K_ID_CAT_ATTO;
	}
	public void setCODI_K_ID_CAT_ATTO(BigDecimal cODI_K_ID_CAT_ATTO) {
		CODI_K_ID_CAT_ATTO = cODI_K_ID_CAT_ATTO;
	}
	public String getDESC_CAT_ATTO() {
		return DESC_CAT_ATTO;
	}
	public void setDESC_CAT_ATTO(String dESC_CAT_ATTO) {
		DESC_CAT_ATTO = dESC_CAT_ATTO;
	}
	public String getDESC_TIPO_MISURA() {
		return DESC_TIPO_MISURA;
	}
	public void setDESC_TIPO_MISURA(String dESC_TIPO_MISURA) {
		DESC_TIPO_MISURA = dESC_TIPO_MISURA;
	}
	public String getDESC_ESITO1() {
		return DESC_ESITO1;
	}
	public void setDESC_ESITO1(String dESC_ESITO1) {
		DESC_ESITO1 = dESC_ESITO1;
	}
	public String getDESC_ESITO2() {
		return DESC_ESITO2;
	}
	public void setDESC_ESITO2(String dESC_ESITO2) {
		DESC_ESITO2 = dESC_ESITO2;
	}
	public String getDESC_ESITO3() {
		return DESC_ESITO3;
	}
	public void setDESC_ESITO3(String dESC_ESITO3) {
		DESC_ESITO3 = dESC_ESITO3;
	}
    
}
