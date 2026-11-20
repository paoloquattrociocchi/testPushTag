package it.giustizia.smp.dto.flussi;

import it.giustizia.smp.utility.Utility;

public class T_ATTODto {

    private String CODI_FK_ID_CAT_ATTO;
    private String CODI_FK_ID_TIPO_ATTO;
    private String CODI_K_ID_UFF_OWNER;
    private String CODI_K_ID_ATTO;
    private String DESC_UTENTE;
    private String DATA_DEPOSITO;
    private String DATA_DEPOSITO_ASSOCIA;
    private String DATA_EMISSIONE;
    private String DATA_RICEZIONE;
    private String DATA_RICEZIONE_ASSOCIA;
    private String DATA_ULT_AGG;
    private String DATA_INVIO;
    private String CODI_ID_PROC_EMISS;
    
	public String getCODI_FK_ID_CAT_ATTO() {
		return CODI_FK_ID_CAT_ATTO;
	}
	public void setCODI_FK_ID_CAT_ATTO(String cODI_FK_ID_CAT_ATTO) {
		CODI_FK_ID_CAT_ATTO = cODI_FK_ID_CAT_ATTO;
	}
	public String getCODI_FK_ID_TIPO_ATTO() {
		return CODI_FK_ID_TIPO_ATTO;
	}
	public void setCODI_FK_ID_TIPO_ATTO(String cODI_FK_ID_TIPO_ATTO) {
		CODI_FK_ID_TIPO_ATTO = cODI_FK_ID_TIPO_ATTO;
	}
	public String getCODI_K_ID_UFF_OWNER() {
		return CODI_K_ID_UFF_OWNER;
	}
	public void setCODI_K_ID_UFF_OWNER(String cODI_K_ID_UFF_OWNER) {
		CODI_K_ID_UFF_OWNER = cODI_K_ID_UFF_OWNER;
	}
	public String getCODI_K_ID_ATTO() {
		return CODI_K_ID_ATTO;
	}
	public void setCODI_K_ID_ATTO(String cODI_K_ID_ATTO) {
		CODI_K_ID_ATTO = cODI_K_ID_ATTO;
	}
	public String getDESC_UTENTE() {
		return DESC_UTENTE;
	}
	public void setDESC_UTENTE(String dESC_UTENTE) {
		DESC_UTENTE = dESC_UTENTE;
	}
	public String getDATA_DEPOSITO() {
		return DATA_DEPOSITO;
	}
	public void setDATA_DEPOSITO(String dATA_DEPOSITO) {
		DATA_DEPOSITO = Utility.formattaData(Utility.stringToDate(dATA_DEPOSITO, "dd/MM/yyyy HH:mm:ss"), "dd/MM/yyyy");
	}
	public String getDATA_EMISSIONE() {
		return DATA_EMISSIONE;
	}
	public void setDATA_EMISSIONE(String dATA_EMISSIONE) {
		DATA_EMISSIONE =  Utility.formattaData(Utility.stringToDate(dATA_EMISSIONE, "dd/MM/yyyy HH:mm:ss"), "dd/MM/yyyy");
	}
	public String getDATA_RICEZIONE() {
		return DATA_RICEZIONE;
	}
	public void setDATA_RICEZIONE(String dATA_RICEZIONE) {
		DATA_RICEZIONE = Utility.formattaData(Utility.stringToDate(dATA_RICEZIONE, "dd/MM/yyyy HH:mm:ss"), "dd/MM/yyyy");
	}
	public String getDATA_ULT_AGG() {
		return DATA_ULT_AGG;
	}
	public void setDATA_ULT_AGG(String dATA_ULT_AGG) {
		DATA_ULT_AGG = dATA_ULT_AGG;
	}
	public String getDATA_DEPOSITO_ASSOCIA() {
		return DATA_DEPOSITO_ASSOCIA;
	}
	public void setDATA_DEPOSITO_ASSOCIA(String dATA_DEPOSITO_ASSOCIA) {
		 DATA_DEPOSITO_ASSOCIA = dATA_DEPOSITO_ASSOCIA;
	}
	public String getDATA_INVIO() {
		return DATA_INVIO;
	}
	public void setDATA_INVIO(String dATA_INVIO) {
		DATA_INVIO = dATA_INVIO;
	}
	public String getCODI_ID_PROC_EMISS() {
		return CODI_ID_PROC_EMISS;
	}
	public void setCODI_ID_PROC_EMISS(String cODI_ID_PROC_EMISS) {
		CODI_ID_PROC_EMISS = cODI_ID_PROC_EMISS;
	}
	public String getDATA_RICEZIONE_ASSOCIA() {
		return DATA_RICEZIONE_ASSOCIA;
	}
	public void setDATA_RICEZIONE_ASSOCIA(String dATA_RICEZIONE_ASSOCIA) {
		DATA_RICEZIONE_ASSOCIA = dATA_RICEZIONE_ASSOCIA;
	}

}
