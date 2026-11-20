package it.giustizia.smp.dto.flussi;

import it.giustizia.smp.utility.Utility;

public class T_PERSONA_GIURDto {

	//private String CODI_K_ID_PERSONA_GIUR;
    private String CODI_FK_ID_COM_SEDE;
    private String CODI_FK_ID_STATO_SEDE;
    private String CODI_K_ID_PERS_GIUR;
    private String DATA_ORIG_VER;
    private String DESC_COM_ESTERO_SEDE;
    private String DESC_COMUNE_SEDE;
    private String DESC_RAGIONE_SOC;
    private String RUOLO;
    private String SIGLA_PROV_SEDE;
    private String TIPO_PERSONA;
    private String CODI_FK_ID_TIPO_SOC;
    private String DESC_PARTITA_IVA;
    
    

	/**
	 * @return the tIPO_PERSONA
	 */
	public String getTIPO_PERSONA() {
		return "G";
	}

	/**
	 * @param tIPO_PERSONA the tIPO_PERSONA to set
	 */
	public void setTIPO_PERSONA(String tIPO_PERSONA) {
		TIPO_PERSONA = "G";
	}

	public String getCODI_FK_ID_COM_SEDE() {
		return CODI_FK_ID_COM_SEDE;
	}

	public void setCODI_FK_ID_COM_SEDE(String cODI_FK_ID_COM_SEDE) {
		CODI_FK_ID_COM_SEDE = cODI_FK_ID_COM_SEDE;
	}

	public String getCODI_FK_ID_STATO_SEDE() {
		return CODI_FK_ID_STATO_SEDE;
	}

	public void setCODI_FK_ID_STATO_SEDE(String cODI_FK_ID_STATO_SEDE) {
		CODI_FK_ID_STATO_SEDE = cODI_FK_ID_STATO_SEDE;
	}

	public String getCODI_K_ID_PERS_GIUR() {
		return CODI_K_ID_PERS_GIUR;
	}

	public void setCODI_K_ID_PERS_GIUR(String cODI_K_ID_PERS_GIUR) {
		CODI_K_ID_PERS_GIUR = cODI_K_ID_PERS_GIUR;
	}

	public String getDATA_ORIG_VER() {
		return DATA_ORIG_VER;
	}

	public void setDATA_ORIG_VER(String dATA_ORIG_VER) {
		DATA_ORIG_VER = Utility.formattaData(Utility.stringToDate(dATA_ORIG_VER, "dd/MM/yyyy HH:mm:ss"), "dd/MM/yyyy");
	}

	public String getDESC_COM_ESTERO_SEDE() {
		return DESC_COM_ESTERO_SEDE;
	}

	public void setDESC_COM_ESTERO_SEDE(String dESC_COM_ESTERO_SEDE) {
		DESC_COM_ESTERO_SEDE = dESC_COM_ESTERO_SEDE;
	}

	public String getDESC_COMUNE_SEDE() {
		return DESC_COMUNE_SEDE;
	}

	public void setDESC_COMUNE_SEDE(String dESC_COMUNE_SEDE) {
		DESC_COMUNE_SEDE = dESC_COMUNE_SEDE;
	}

	

	public String getCODI_FK_ID_TIPO_SOC() {
		return CODI_FK_ID_TIPO_SOC;
	}

	public void setCODI_FK_ID_TIPO_SOC(String cODI_FK_ID_TIPO_SOC) {
		CODI_FK_ID_TIPO_SOC = cODI_FK_ID_TIPO_SOC;
	}

	public String getDESC_PARTITA_IVA() {
		return DESC_PARTITA_IVA;
	}

	public void setDESC_PARTITA_IVA(String dESC_PARTITA_IVA) {
		DESC_PARTITA_IVA = dESC_PARTITA_IVA;
	}

	public String getRUOLO() {
		return RUOLO;
	}

	public void setRUOLO(String rUOLO) {
		RUOLO = rUOLO;
	}

	public String getSIGLA_PROV_SEDE() {
		return SIGLA_PROV_SEDE;
	}

	public void setSIGLA_PROV_SEDE(String sIGLA_PROV_SEDE) {
		SIGLA_PROV_SEDE = sIGLA_PROV_SEDE;
	}

	public String getDESC_RAGIONE_SOC() {
		return DESC_RAGIONE_SOC;
	}

	public void setDESC_RAGIONE_SOC(String dESC_RAGIONE_SOC) {
		DESC_RAGIONE_SOC = dESC_RAGIONE_SOC;
	}

	
    
}
