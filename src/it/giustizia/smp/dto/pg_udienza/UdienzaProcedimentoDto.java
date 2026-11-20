package it.giustizia.smp.dto.pg_udienza;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Date;

public class UdienzaProcedimentoDto {

    private String     AULA_RINVIO;
    private String     DESC_AULA;
    private BigDecimal CODI_FK_ID_ESITO_UD;
    private BigDecimal CODI_FK_ID_PROC;
    private BigDecimal CODI_K_ID_MAGISTRATO;
    private BigDecimal CODI_K_ID_SOGG_DIF;
    private BigDecimal CODI_K_ID_STATO_UD;
    private BigDecimal CODI_K_ID_UDIENZA;
    private Date       DATA_RINVIO;
    private Date       DATA_UDIENZA;
    private Date	   DATA_ASS_STATO;
    private String     DESC_ALTRICONV;
    private String     DESC_COGNOME_ASS_GIU;
    private String     DESC_COGNOME_AVV;
    private String     DESC_COGNOME_MAG;
    private String     DESC_ESITO_UD;        // DA T_UDIENZA
    private String     DESC_ESITO_UDIENZA;   // DA T_ESITO_UDIENZA
    private String     DESC_FLG_PRES;
    private String     DESC_INDIR_AULA;
    private String     DESC_NOME_ASS_GIU;
    private String     DESC_NOME_AVV;
    private String     DESC_NOME_MAG;
    private String     DESC_RUOLO;
    private String     DESC_RUOLO_MAG;
    private BigDecimal ID_RUOLO_MAG;
    private String     DESC_STATO_UDIENZA;
    private String     DESC_NOTE_UDIENZA;
    private Character     FLG_PRES;
    private String     INDIR_AULA_RINVIO;
    // SOGGETTI CONVOCATI e DIFENSORI
    private String     NOMINATIVO;
    private String     ORA_FINE_UDIENZA;
    private String     ORA_INIZIO_UDIENZA;
    // RINVIO UDIENZA
    private String      ORA_UDIENZA_RINVIO;
    private String 		DESC_TIPO_DOCUMENTO; 
	private String 		DESC_NOME_DOCUMENTO; 
	private Blob 		FILE_DOCUMENTO; 
	private BigDecimal  NUME_DOCUMENTO_PDOC;
    
	public String getAULA_RINVIO() {

        return AULA_RINVIO;
    }

    public BigDecimal getCODI_FK_ID_ESITO_UD() {

        return CODI_FK_ID_ESITO_UD;
    }

    public BigDecimal getCODI_FK_ID_PROC() {

        return CODI_FK_ID_PROC;
    }

    public BigDecimal getCODI_K_ID_MAGISTRATO() {

        return CODI_K_ID_MAGISTRATO;
    }

    public BigDecimal getCODI_K_ID_SOGG_DIF() {

        return CODI_K_ID_SOGG_DIF;
    }

    public BigDecimal getCODI_K_ID_STATO_UD() {

        return CODI_K_ID_STATO_UD;
    }

    public BigDecimal getCODI_K_ID_UDIENZA() {

        return CODI_K_ID_UDIENZA;
    }

    public Date getDATA_RINVIO() {

        return DATA_RINVIO;
    }

    public Date getDATA_UDIENZA() {

        return DATA_UDIENZA;
    }

    /**
	 * @return the dATA_ASS_STATO
	 */
	public Date getDATA_ASS_STATO() {
		return DATA_ASS_STATO;
	}

	public String getDESC_ALTRICONV() {

        return DESC_ALTRICONV;
    }

    public String getDESC_COGNOME_ASS_GIU() {

        return DESC_COGNOME_ASS_GIU;
    }

    public String getDESC_COGNOME_AVV() {

        return DESC_COGNOME_AVV;
    }

    public String getDESC_COGNOME_MAG() {

        return DESC_COGNOME_MAG;
    }

    public String getDESC_ESITO_UD() {

        return DESC_ESITO_UD;
    }

    public String getDESC_ESITO_UDIENZA() {

        return DESC_ESITO_UDIENZA;
    }

    public String getDESC_FLG_PRES() {

        if (FLG_PRES == null) {
            DESC_FLG_PRES = "";
        } else
            if (FLG_PRES.equals("S")) {
                DESC_FLG_PRES = "Si";
            } else {
                DESC_FLG_PRES = "No";
            }
        return DESC_FLG_PRES;
    }

    public String getDESC_INDIR_AULA() {

        return DESC_INDIR_AULA;
    }

    public String getDESC_NOME_ASS_GIU() {

        return DESC_NOME_ASS_GIU;
    }

    public String getDESC_NOME_AVV() {

        return DESC_NOME_AVV;
    }

    public String getDESC_NOME_MAG() {

        return DESC_NOME_MAG;
    }

    public String getDESC_RUOLO() {

        return DESC_RUOLO;
    }

    public String getDESC_RUOLO_MAG() {

        return DESC_RUOLO_MAG;
    }

    public String getDESC_STATO_UDIENZA() {

        return DESC_STATO_UDIENZA;
    }

    public Character getFLG_PRES() {

        if (FLG_PRES == null) {
            return null;
        } else {
            return FLG_PRES;
        }
    }

    public String getINDIR_AULA_RINVIO() {

        return INDIR_AULA_RINVIO;
    }

    public String getNOMINATIVO() {

        return NOMINATIVO;
    }

    public String getORA_FINE_UDIENZA() {

        return ORA_FINE_UDIENZA;
    }

    public String getORA_INIZIO_UDIENZA() {

        return ORA_INIZIO_UDIENZA;
    }

    /*
     * public String getNOMINATIVO_AVV() {
     * return DESC_COGNOME_AVV + " " + DESC_NOME_AVV;
     * }
     */
    public String getORA_UDIENZA_RINVIO() {

        return ORA_UDIENZA_RINVIO;
    }

    public void setAULA_RINVIO(String aULA_RINVIO) {

        AULA_RINVIO = aULA_RINVIO;
    }


    public void setCODI_FK_ID_ESITO_UD(BigDecimal cODI_FK_ID_ESITO_UD) {

        CODI_FK_ID_ESITO_UD = cODI_FK_ID_ESITO_UD;
    }

    public void setCODI_FK_ID_PROC(BigDecimal cODI_FK_ID_PROC) {

        CODI_FK_ID_PROC = cODI_FK_ID_PROC;
    }

    public void setCODI_K_ID_MAGISTRATO(BigDecimal cODI_K_ID_MAGISTRATO) {

        CODI_K_ID_MAGISTRATO = cODI_K_ID_MAGISTRATO;
    }

    public void setCODI_K_ID_SOGG_DIF(BigDecimal cODI_K_ID_SOGG_DIF) {

        CODI_K_ID_SOGG_DIF = cODI_K_ID_SOGG_DIF;
    }

    public void setCODI_K_ID_STATO_UD(BigDecimal cODI_K_ID_STATO_UD) {

        CODI_K_ID_STATO_UD = cODI_K_ID_STATO_UD;
    }

    public void setCODI_K_ID_UDIENZA(BigDecimal cODI_K_ID_UDIENZA) {

        CODI_K_ID_UDIENZA = cODI_K_ID_UDIENZA;
    }

    public void setDATA_RINVIO(Date dATA_RINVIO) {

        DATA_RINVIO = dATA_RINVIO;
    }

    public void setDATA_UDIENZA(Date dATA_UDIENZA) {

        DATA_UDIENZA = dATA_UDIENZA;
    }

    /**
	 * @param dATA_ASS_STATO the dATA_ASS_STATO to set
	 */
	public void setDATA_ASS_STATO(Date dATA_ASS_STATO) {
		DATA_ASS_STATO = dATA_ASS_STATO;
	}

	public void setDESC_ALTRICONV(String dESC_ALTRICONV) {

        DESC_ALTRICONV = dESC_ALTRICONV;
    }

    public void setDESC_COGNOME_ASS_GIU(String dESC_COGNOME_ASS_GIU) {

        DESC_COGNOME_ASS_GIU = dESC_COGNOME_ASS_GIU;
    }

    public void setDESC_COGNOME_AVV(String dESC_COGNOME_AVV) {

        DESC_COGNOME_AVV = dESC_COGNOME_AVV;
    }

    public void setDESC_COGNOME_MAG(String dESC_COGNOME_MAG) {

        DESC_COGNOME_MAG = dESC_COGNOME_MAG;
    }

    public void setDESC_ESITO_UD(String dESC_ESITO_UD) {

        DESC_ESITO_UD = dESC_ESITO_UD;
    }

    public void setDESC_ESITO_UDIENZA(String dESC_ESITO_UDIENZA) {

        DESC_ESITO_UDIENZA = dESC_ESITO_UDIENZA;
    }

    public void setDESC_FLG_PRES(String dESC_FLG_PRES) {

        DESC_FLG_PRES = dESC_FLG_PRES;
    }

    public void setDESC_INDIR_AULA(String dESC_INDIR_AULA) {

        DESC_INDIR_AULA = dESC_INDIR_AULA;
    }

    public void setDESC_NOME_ASS_GIU(String dESC_NOME_ASS_GIU) {

        DESC_NOME_ASS_GIU = dESC_NOME_ASS_GIU;
    }

    public void setDESC_NOME_AVV(String dESC_NOME_AVV) {

        DESC_NOME_AVV = dESC_NOME_AVV;
    }

    public void setDESC_NOME_MAG(String dESC_NOME_MAG) {

        DESC_NOME_MAG = dESC_NOME_MAG;
    }

    public void setDESC_RUOLO(String dESC_RUOLO) {

        DESC_RUOLO = dESC_RUOLO;
    }

    public void setDESC_RUOLO_MAG(String dESC_RUOLO_MAG) {

        DESC_RUOLO_MAG = dESC_RUOLO_MAG;
    }

    public void setDESC_STATO_UDIENZA(String dESC_STATO_UDIENZA) {

        DESC_STATO_UDIENZA = dESC_STATO_UDIENZA;
    }

    public void setFLG_PRES(Character fLG_PRES) {

        FLG_PRES = fLG_PRES;
    }

    public void setINDIR_AULA_RINVIO(String iNDIR_AULA_RINVIO) {

        INDIR_AULA_RINVIO = iNDIR_AULA_RINVIO;
    }

    public void setNOMINATIVO(String nOMINATIVO) {

        NOMINATIVO = nOMINATIVO;
    }

    public void setORA_FINE_UDIENZA(String oRA_FINE_UDIENZA) {

        ORA_FINE_UDIENZA = oRA_FINE_UDIENZA;
    }

    public void setORA_INIZIO_UDIENZA(String oRA_INIZIO_UDIENZA) {

        ORA_INIZIO_UDIENZA = oRA_INIZIO_UDIENZA;
    }

    public void setORA_UDIENZA_RINVIO(String oRA_UDIENZA_RINVIO) {

        ORA_UDIENZA_RINVIO = oRA_UDIENZA_RINVIO;
    }

	public String getDESC_AULA() {
		return DESC_AULA;
	}

	public void setDESC_AULA(String dESC_AULA) {
		DESC_AULA = dESC_AULA;
	}

	public String getDESC_NOTE_UDIENZA() {
		return DESC_NOTE_UDIENZA;
	}

	public void setDESC_NOTE_UDIENZA(String dESC_NOTE_UDIENZA) {
		DESC_NOTE_UDIENZA = dESC_NOTE_UDIENZA;
	}

	public BigDecimal getID_RUOLO_MAG() {
		return ID_RUOLO_MAG;
	}

	public void setID_RUOLO_MAG(BigDecimal iD_RUOLO_MAG) {
		ID_RUOLO_MAG = iD_RUOLO_MAG;
	}

	public String getDESC_TIPO_DOCUMENTO() {
		return DESC_TIPO_DOCUMENTO;
	}

	public void setDESC_TIPO_DOCUMENTO(String dESC_TIPO_DOCUMENTO) {
		DESC_TIPO_DOCUMENTO = dESC_TIPO_DOCUMENTO;
	}

	public String getDESC_NOME_DOCUMENTO() {
		return DESC_NOME_DOCUMENTO;
	}

	public void setDESC_NOME_DOCUMENTO(String dESC_NOME_DOCUMENTO) {
		DESC_NOME_DOCUMENTO = dESC_NOME_DOCUMENTO;
	}

	public Blob getFILE_DOCUMENTO() {
		return FILE_DOCUMENTO;
	}

	public void setFILE_DOCUMENTO(Blob fILE_DOCUMENTO) {
		FILE_DOCUMENTO = fILE_DOCUMENTO;
	}

	public BigDecimal getNUME_DOCUMENTO_PDOC() {
		return NUME_DOCUMENTO_PDOC;
	}

	public void setNUME_DOCUMENTO_PDOC(BigDecimal nUME_DOCUMENTO_PDOC) {
		NUME_DOCUMENTO_PDOC = nUME_DOCUMENTO_PDOC;
	}
}
