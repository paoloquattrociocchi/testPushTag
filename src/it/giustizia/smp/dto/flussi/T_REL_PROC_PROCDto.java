package it.giustizia.smp.dto.flussi;

public class T_REL_PROC_PROCDto {

	private String	CODI_K_ID_PROC1;
	private String	CODI_K_ID_PROC2;
	private String	NUME_ANNO_PROC2;
	private String	NUME_PROCEDIMENTO2;
	private String 	CODI_FK_ID_ATTO_AA;
	private String	CODI_FK_ID_UFF_OWNER2;
	private String	FLG_PADRE;
	private String	DESC_UTENTE;
	private String  CODI_K_ID_PROC_PADRE;

	public String getCODI_K_ID_PROC_PADRE() {
		return CODI_K_ID_PROC_PADRE;
	}

	public void setCODI_K_ID_PROC_PADRE(String cODI_K_ID_PROC_PADRE) {
		CODI_K_ID_PROC_PADRE = cODI_K_ID_PROC_PADRE;
	}

	public final String getCODI_K_ID_PROC1() {

		return CODI_K_ID_PROC1;
	}

	public final void setCODI_K_ID_PROC1(final String cODI_K_ID_PROC1) {

		CODI_K_ID_PROC1 = cODI_K_ID_PROC1;
	}

	public final String getCODI_K_ID_PROC2() {

		return CODI_K_ID_PROC2;
	}

	public final void setCODI_K_ID_PROC2(final String cODI_K_ID_PROC2) {

		CODI_K_ID_PROC2 = cODI_K_ID_PROC2;
	}

	public final String getNUME_ANNO_PROC2() {

		return NUME_ANNO_PROC2;
	}

	public final void setNUME_ANNO_PROC2(final String nUME_ANNO_PROC2) {

		NUME_ANNO_PROC2 = nUME_ANNO_PROC2;
	}

	public final String getNUME_PROCEDIMENTO2() {

		return NUME_PROCEDIMENTO2;
	}

	public final void setNUME_PROCEDIMENTO2(final String nUME_PROCEDIMENTO2) {

		NUME_PROCEDIMENTO2 = nUME_PROCEDIMENTO2;
	}

	public final String getCODI_FK_ID_UFF_OWNER2() {

		return CODI_FK_ID_UFF_OWNER2;
	}

	public final void setCODI_FK_ID_UFF_OWNER2(final String cODI_FK_ID_UFF_OWNER2) {

		CODI_FK_ID_UFF_OWNER2 = cODI_FK_ID_UFF_OWNER2;
	}

	public final String getFLG_PADRE() {

		return FLG_PADRE;
	}

	public final void setFLG_PADRE(final String fLG_PADRE) {

		FLG_PADRE = fLG_PADRE;
	}

	public String getCODI_FK_ID_ATTO_AA() {

		return CODI_FK_ID_ATTO_AA;
	}

	public void setCODI_FK_ID_ATTO_AA(String cODI_FK_ID_ATTO_AA) {

		CODI_FK_ID_ATTO_AA = cODI_FK_ID_ATTO_AA;
	}

	public String getDESC_UTENTE() {
		return DESC_UTENTE;
	}

	public void setDESC_UTENTE(String dESC_UTENTE) {
		DESC_UTENTE = dESC_UTENTE;
	}
}
