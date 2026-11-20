package it.giustizia.smp.dto.pg_conservatorie;

import it.giustizia.smp.validation.date.ModernDate;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class ComunicazionePredispDto {

	private BigDecimal 	IDNOTA;
    private Character  STATO;
    @ModernDate
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date	DATATITOLO;
    private String DESCCONSERV;
    private BigDecimal IDCONSERV;
    private BigDecimal    PROGRNOTA;
    
	public BigDecimal getIDNOTA() {
		return IDNOTA;
	}
	public void setIDNOTA(BigDecimal iDNOTA) {
		IDNOTA = iDNOTA;
	}
	public Character getSTATO() {
		return STATO;
	}
	public void setSTATO(Character sTATO) {
		STATO = sTATO;
	}
	public Date getDATATITOLO() {
		return DATATITOLO;
	}
	public void setDATATITOLO(Date dATATITOLO) {
		DATATITOLO = dATATITOLO;
	}
	public String getDESCCONSERV() {
		return DESCCONSERV;
	}
	public void setDESCCONSERV(String dESCCONSERV) {
		DESCCONSERV = dESCCONSERV;
	}
	public BigDecimal getIDCONSERV() {
		return IDCONSERV;
	}
	public void setIDCONSERV(BigDecimal iDCONSERV) {
		IDCONSERV = iDCONSERV;
	}
	public BigDecimal getPROGRNOTA() {
		return PROGRNOTA;
	}
	public void setPROGRNOTA(BigDecimal pROGRNOTA) {
		PROGRNOTA = pROGRNOTA;
	}
    
}
