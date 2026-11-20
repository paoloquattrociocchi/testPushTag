package it.giustizia.smp.dto.pg_flussi;

import java.math.BigDecimal;
import java.util.Date;

public class FlussiInDto {

    private BigDecimal CODI_FK_ID_UFF_MITT;
    private BigDecimal CODI_K_ID_FLUSSO_IN;
    private Date       DATA_FLUSSO;
    private String     DESC_SEDE_UFFICIO;
    private String     DESC_TIPO_FLUSSO;
    private String     DESC_TIPO_UFFICIO;

    /**
     * @return the cODI_FK_ID_UFF_MITT
     */
    public BigDecimal getCODI_FK_ID_UFF_MITT() {

        return CODI_FK_ID_UFF_MITT;
    }

    /**
     * @return the cODI_K_ID_FLUSSO_IN
     */
    public BigDecimal getCODI_K_ID_FLUSSO_IN() {

        return CODI_K_ID_FLUSSO_IN;
    }

    /**
     * @return the dATA_FLUSSO
     */
    public Date getDATA_FLUSSO() {

        return DATA_FLUSSO;
    }

    /**
     * @return the dESC_SEDE_UFFICIO
     */
    public String getDESC_SEDE_UFFICIO() {

        return DESC_SEDE_UFFICIO;
    }


    /**
     * @return the dESC_TIPO_UFFICIO
     */
    public String getDESC_TIPO_UFFICIO() {

        return DESC_TIPO_UFFICIO;
    }

    /**
     * @param cODI_FK_ID_UFF_MITT
     *            the cODI_FK_ID_UFF_MITT to set
     */
    public void setCODI_FK_ID_UFF_MITT(BigDecimal cODI_FK_ID_UFF_MITT) {

        CODI_FK_ID_UFF_MITT = cODI_FK_ID_UFF_MITT;
    }

    /**
     * @param cODI_K_ID_FLUSSO_IN
     *            the cODI_K_ID_FLUSSO_IN to set
     */
    public void setCODI_K_ID_FLUSSO_IN(BigDecimal cODI_K_ID_FLUSSO_IN) {

        CODI_K_ID_FLUSSO_IN = cODI_K_ID_FLUSSO_IN;
    }

    /**
     * @param dATA_FLUSSO
     *            the dATA_FLUSSO to set
     */
    public void setDATA_FLUSSO(Date dATA_FLUSSO) {

        DATA_FLUSSO = dATA_FLUSSO;
    }

    /**
     * @param dESC_SEDE_UFFICIO
     *            the dESC_SEDE_UFFICIO to set
     */
    public void setDESC_SEDE_UFFICIO(String dESC_SEDE_UFFICIO) {

        DESC_SEDE_UFFICIO = dESC_SEDE_UFFICIO;
    }

    /**
     * @param dESC_TIPO_UFFICIO
     *            the dESC_TIPO_UFFICIO to set
     */
    public void setDESC_TIPO_UFFICIO(String dESC_TIPO_UFFICIO) {

        DESC_TIPO_UFFICIO = dESC_TIPO_UFFICIO;
    }

	public String getDESC_TIPO_FLUSSO() {
		return DESC_TIPO_FLUSSO;
	}

	public void setDESC_TIPO_FLUSSO(String dESC_TIPO_FLUSSO) {
		DESC_TIPO_FLUSSO = dESC_TIPO_FLUSSO;
	}
    
    
}
