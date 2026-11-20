package it.giustizia.smp.dto.flussi;

import it.giustizia.smp.utility.Utility;


public class T_PERSONA_FISICADto {

    private String CODI_FK_ID_COM_NASC;
    private String CODI_FK_ID_STATO_NASC;
    private String CODI_K_ID_UFF_OWNER;
    private String CODI_K_ID_PERSONA_FIS;
    private String CODI_K_ID_VER;
    private String DESC_UTENTE;
    private String DATA_NASCITA;
    private String DATA_ORIG_VER;
    private String DATA_ULT_AGG;
    private String DESC_COGNOME;
    private String DESC_COM_ESTERO_NASC;
    private String DESC_COMUNE_NASC;
    private String DESC_NOME;
  //  private String FLG_PERS_DA_IDENT;
    private String FLG_VALIDITA_VER;
    private String RUOLO;
    private String FLG_SESSO;
    private String SIGLA_PROV_NASC;
    private String TIPO_PERSONA;
    private String FLG_DATA_PRESUNTA;
    private String DESC_STATO_NASC;


	/**
	 * @return the tIPO_PERSONA
	 */
	public String getTIPO_PERSONA() {
		return "F";
	}

	/**
	 * @param tIPO_PERSONA the tIPO_PERSONA to set
	 */
	public void setTIPO_PERSONA(String tIPO_PERSONA) {
		TIPO_PERSONA = "F";
	}

	public String getCODI_FK_ID_COM_NASC() {
		return CODI_FK_ID_COM_NASC;
	}

	public void setCODI_FK_ID_COM_NASC(String cODI_FK_ID_COM_NASC) {
		CODI_FK_ID_COM_NASC = cODI_FK_ID_COM_NASC;
	}

	public String getCODI_FK_ID_STATO_NASC() {
		return CODI_FK_ID_STATO_NASC;
	}

	public void setCODI_FK_ID_STATO_NASC(String cODI_FK_ID_STATO_NASC) {
		CODI_FK_ID_STATO_NASC = cODI_FK_ID_STATO_NASC;
	}

	public String getCODI_K_ID_UFF_OWNER() {
		return CODI_K_ID_UFF_OWNER;
	}

	public void setCODI_K_ID_UFF_OWNER(String cODI_K_ID_UFF_OWNER) {
		CODI_K_ID_UFF_OWNER = cODI_K_ID_UFF_OWNER;
	}

	public String getCODI_K_ID_PERSONA_FIS() {
		return CODI_K_ID_PERSONA_FIS;
	}

	public void setCODI_K_ID_PERSONA_FIS(String cODI_K_ID_PERSONA_FIS) {
		CODI_K_ID_PERSONA_FIS = cODI_K_ID_PERSONA_FIS;
	}

	public String getCODI_K_ID_VER() {
		return CODI_K_ID_VER;
	}

	public void setCODI_K_ID_VER(String cODI_K_ID_VER) {
		CODI_K_ID_VER = cODI_K_ID_VER;
	}

	public String getDESC_UTENTE() {
		return DESC_UTENTE;
	}

	public void setDESC_UTENTE(String dESC_UTENTE) {
		DESC_UTENTE = dESC_UTENTE;
	}

	public String getDATA_NASCITA() {
		return DATA_NASCITA;
	}

	public void setDATA_NASCITA(String dATA_NASCITA) {
		DATA_NASCITA = Utility.formattaData(Utility.stringToDate(dATA_NASCITA, "dd/MM/yyyy HH:mm:ss"), "dd/MM/yyyy");
	}

	public String getDATA_ORIG_VER() {
		return DATA_ORIG_VER;
	}

	public void setDATA_ORIG_VER(String dATA_ORIG_VER) {
		DATA_ORIG_VER = Utility.formattaData(Utility.stringToDate(dATA_ORIG_VER, "dd/MM/yyyy HH:mm:ss"), "dd/MM/yyyy");
	}

	public String getDATA_ULT_AGG() {
		return DATA_ULT_AGG;
	}

	public void setDATA_ULT_AGG(String dATA_ULT_AGG) {
		DATA_ULT_AGG = Utility.formattaData(Utility.stringToDate(dATA_ULT_AGG, "dd/MM/yyyy HH:mm:ss"), "dd/MM/yyyy");
	}

	public String getDESC_COGNOME() {
		return DESC_COGNOME;
	}

	public void setDESC_COGNOME(String dESC_COGNOME) {
		DESC_COGNOME = dESC_COGNOME;
	}

	public String getDESC_COM_ESTERO_NASC() {
		return DESC_COM_ESTERO_NASC;
	}

	public void setDESC_COM_ESTERO_NASC(String dESC_COM_ESTERO_NASC) {
		DESC_COM_ESTERO_NASC = dESC_COM_ESTERO_NASC;
	}

	public String getDESC_COMUNE_NASC() {
		return DESC_COMUNE_NASC;
	}

	public void setDESC_COMUNE_NASC(String dESC_COMUNE_NASC) {
		DESC_COMUNE_NASC = dESC_COMUNE_NASC;
	}

	public String getDESC_NOME() {
		return DESC_NOME;
	}

	public void setDESC_NOME(String dESC_NOME) {
		DESC_NOME = dESC_NOME;
	}

	public String getFLG_VALIDITA_VER() {
		return FLG_VALIDITA_VER;
	}

	public void setFLG_VALIDITA_VER(String fLG_VALIDITA_VER) {
		FLG_VALIDITA_VER = fLG_VALIDITA_VER;
	}

	public String getFLG_SESSO() {
		return FLG_SESSO;
	}

	public void setFLG_SESSO(String fLG_SESSO) {
		FLG_SESSO = fLG_SESSO;
	}

	public String getRUOLO() {
		return RUOLO;
	}

	public void setRUOLO(String rUOLO) {
		RUOLO = rUOLO;
	}

	public String getSIGLA_PROV_NASC() {
		return SIGLA_PROV_NASC;
	}

	public void setSIGLA_PROV_NASC(String sIGLA_PROV_NASC) {
		SIGLA_PROV_NASC = sIGLA_PROV_NASC;
	}

	public String getFLG_DATA_PRESUNTA() {
		return FLG_DATA_PRESUNTA;
	}

	public void setFLG_DATA_PRESUNTA(String fLG_DATA_PRESUNTA) {
		FLG_DATA_PRESUNTA = fLG_DATA_PRESUNTA;
	}

	public String getDESC_STATO_NASC() {
		return DESC_STATO_NASC;
	}

	public void setDESC_STATO_NASC(String dESC_STATO_NASC) {
		DESC_STATO_NASC = dESC_STATO_NASC;
	}

    
}
