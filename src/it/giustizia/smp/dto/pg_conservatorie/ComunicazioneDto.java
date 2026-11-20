package it.giustizia.smp.dto.pg_conservatorie;

import it.giustizia.smp.validation.date.ModernDate;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class ComunicazioneDto {

    private BigDecimal 	IDNOTA;
    private String  DESCTIPOCATATTO;
    private BigDecimal    ANNOREPERTORIO;
    private BigDecimal    NUMEROREPERTORIO;
    private BigDecimal    PROGRESSIVOREPERTORIO;
    private Character  STATOCOMUNICAZIONE;
    private Character  TIPOCOMUNICAZIONE;
    private String DESCCONSERVATORIE;
    private Integer CODCONSERVATORIE;
    @ModernDate
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date	DATACOMUNICAZIONE;
    
    
	public String getDESCCONSERVATORIE() {
		return DESCCONSERVATORIE;
	}
	public void setDESCCONSERVATORIE(String dESCCONSERVATORIE) {
		DESCCONSERVATORIE = dESCCONSERVATORIE;
	}
	public Integer getCODCONSERVATORIE() {
		return CODCONSERVATORIE;
	}
	public void setCODCONSERVATORIE(Integer cODCONSERVATORIE) {
		CODCONSERVATORIE = cODCONSERVATORIE;
	}
	public BigDecimal getIDNOTA() {
		return IDNOTA;
	}
	public void setIDNOTA(BigDecimal iDNOTA) {
		IDNOTA = iDNOTA;
	}
	public String getDESCTIPOCATATTO() {
		return DESCTIPOCATATTO;
	}
	public void setDESCTIPOCATATTO(String dESCTIPOCATATTO) {
		DESCTIPOCATATTO = dESCTIPOCATATTO;
	}
	public BigDecimal getANNOREPERTORIO() {
		return ANNOREPERTORIO;
	}
	public void setANNOREPERTORIO(BigDecimal aNNOREPERTORIO) {
		ANNOREPERTORIO = aNNOREPERTORIO;
	}
	public BigDecimal getNUMEROREPERTORIO() {
		return NUMEROREPERTORIO;
	}
	public void setNUMEROREPERTORIO(BigDecimal nUMEROREPERTORIO) {
		NUMEROREPERTORIO = nUMEROREPERTORIO;
	}
	public BigDecimal getPROGRESSIVOREPERTORIO() {
		return PROGRESSIVOREPERTORIO;
	}
	public void setPROGRESSIVOREPERTORIO(BigDecimal pROGRESSIVOREPERTORIO) {
		PROGRESSIVOREPERTORIO = pROGRESSIVOREPERTORIO;
	}
	public Character getSTATOCOMUNICAZIONE() {
		return STATOCOMUNICAZIONE;
	}
	public void setSTATOCOMUNICAZIONE(Character sTATOCOMUNICAZIONE) {
		STATOCOMUNICAZIONE = sTATOCOMUNICAZIONE;
	}
	public Character getTIPOCOMUNICAZIONE() {
		return TIPOCOMUNICAZIONE;
	}
	public void setTIPOCOMUNICAZIONE(Character tIPOCOMUNICAZIONE) {
		TIPOCOMUNICAZIONE = tIPOCOMUNICAZIONE;
	}
	public Date getDATACOMUNICAZIONE() {
		return DATACOMUNICAZIONE;
	}
	public void setDATACOMUNICAZIONE(Date dATACOMUNICAZIONE) {
		DATACOMUNICAZIONE = dATACOMUNICAZIONE;
	}
    
}
