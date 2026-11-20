package it.giustizia.smp.dto.flussi;

public class T_REL_ATTO_PROCDto {

   // private String ANNO_PROCEDIMENTO;
    private String CODI_K_ID_ATTO;
  //  private String NUM_PROCEDIMENTO;
    private String CODI_K_ID_PROC;
    private String CODI_K_ID_UFF_OWNER;
    private String FLG_VALIDO;
    private String DESC_UTENTE;
    private String DATA_ULT_AGG;

	public String getCODI_K_ID_ATTO() {
		return CODI_K_ID_ATTO;
	}

	public void setCODI_K_ID_ATTO(String cODI_K_ID_ATTO) {
		CODI_K_ID_ATTO = cODI_K_ID_ATTO;
	}

	public String getCODI_K_ID_PROC() {
		return CODI_K_ID_PROC;
	}

	public void setCODI_K_ID_PROC(String cODI_K_ID_PROC) {
		CODI_K_ID_PROC = cODI_K_ID_PROC;
	}

	public String getCODI_K_ID_UFF_OWNER() {
		return CODI_K_ID_UFF_OWNER;
	}

	public void setCODI_K_ID_UFF_OWNER(String cODI_K_ID_UFF_OWNER) {
		CODI_K_ID_UFF_OWNER = cODI_K_ID_UFF_OWNER;
	}

	public String getFLG_VALIDO() {
		return FLG_VALIDO;
	}

	public void setFLG_VALIDO(String fLG_VALIDO) {
		FLG_VALIDO = fLG_VALIDO;
	}

	public String getDESC_UTENTE() {
		return DESC_UTENTE;
	}

	public void setDESC_UTENTE(String dESC_UTENTE) {
		DESC_UTENTE = dESC_UTENTE;
	}

	public String getDATA_ULT_AGG() {
		return DATA_ULT_AGG;
	}

	public void setDATA_ULT_AGG(String dATA_ULT_AGG) {
		DATA_ULT_AGG = dATA_ULT_AGG;
	}

    
}
