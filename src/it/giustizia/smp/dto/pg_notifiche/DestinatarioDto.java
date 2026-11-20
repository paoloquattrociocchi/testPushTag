package it.giustizia.smp.dto.pg_notifiche;


public class DestinatarioDto {

	private String OGGETTO; 
	private String EVENTO;  
	private String FLG_PRESELEZIONATO;  // S/N
	private String FLG_SELEZIONABILE; 	// S/N
	private String FLG_ALLEGA_CONT; 	// S/N
	
	private java.math.BigDecimal CODI_K_RUOLO_DEST;
	private String DESC_RUOLO_DEST;
	private String DESC_RUOLO_DEST_SITMP;
	private String CODI_DEST;
	private String DESC_DEST;
	
	
	public String getFLG_ALLEGA_CONT() {
		return FLG_ALLEGA_CONT;
	}
	public void setFLG_ALLEGA_CONT(String fLG_ALLEGA_CONT) {
		FLG_ALLEGA_CONT = fLG_ALLEGA_CONT;
	}
	public String getOGGETTO() {
		return OGGETTO;
	}
	public void setOGGETTO(String oGGETTO) {
		OGGETTO = oGGETTO;
	}
	public String getEVENTO() {
		return EVENTO;
	}
	public void setEVENTO(String eVENTO) {
		EVENTO = eVENTO;
	}
	public String getFLG_PRESELEZIONATO() {
		return FLG_PRESELEZIONATO;
	}
	public void setFLG_PRESELEZIONATO(String fLG_PRESELEZIONATO) {
		FLG_PRESELEZIONATO = fLG_PRESELEZIONATO;
	}
	public String getFLG_SELEZIONABILE() {
		return FLG_SELEZIONABILE;
	}
	public void setFLG_SELEZIONABILE(String fLG_SELEZIONABILE) {
		FLG_SELEZIONABILE = fLG_SELEZIONABILE;
	}
	public java.math.BigDecimal getCODI_K_RUOLO_DEST() {
		return CODI_K_RUOLO_DEST;
	}
	public void setCODI_K_RUOLO_DEST(java.math.BigDecimal cODI_K_RUOLO_DEST) {
		CODI_K_RUOLO_DEST = cODI_K_RUOLO_DEST;
	}
	public String getDESC_RUOLO_DEST() {
		return DESC_RUOLO_DEST;
	}
	public void setDESC_RUOLO_DEST(String dESC_RUOLO_DEST) {
		DESC_RUOLO_DEST = dESC_RUOLO_DEST;
	}
	public String getDESC_RUOLO_DEST_SITMP() {
		return DESC_RUOLO_DEST_SITMP;
	}
	public void setDESC_RUOLO_DEST_SITMP(String dESC_RUOLO_DEST_SITMP) {
		DESC_RUOLO_DEST_SITMP = dESC_RUOLO_DEST_SITMP;
	}
	public String getCODI_DEST() {
		return CODI_DEST;
	}
	public void setCODI_DEST(String cODI_DEST) {
		CODI_DEST = cODI_DEST;
	}
	public String getDESC_DEST() {
		return DESC_DEST;
	}
	public void setDESC_DEST(String dESC_DEST) {
		DESC_DEST = dESC_DEST;
	}
	
	
	
}
