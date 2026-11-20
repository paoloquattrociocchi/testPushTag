package it.giustizia.smp.dto.pg_misura;

import java.math.BigDecimal;
import java.util.Date;

public class DettaglioMisuraPersonaleDto {

    private BigDecimal ANNI;
    private String     ART67;
    private String     CARATTERE_MISURA;
    private Date       DATA_FINE_VALIDITA;
    private BigDecimal DECISIONE_MISURA;
    private String	   DESC_DECISIONE_MISURA;
    private BigDecimal DECISIONE_MISURA_I_GRADO;
    private String	   DESC_DECISIONE_MISURA_I_GRADO;
    private BigDecimal DECISIONE_MISURA_II_GRADO;
    private String	   DESC_DECISIONE_MISURA_II_GRADO;
    private BigDecimal DEC_MISURA_III_GRADO;
    private String	   DESC_DEC_MISURA_III_GRADO;       
    private String     DESC_NORMATIVA;
    private String     DESC_TIPO_MISURA;
    private String     DESC_TIPO_PREV;
    private BigDecimal GIORNI;
    private BigDecimal ID_ATTO;
    private BigDecimal ID_MISURA_INSERITA;
    private BigDecimal ID_NORMATIVA;
    private BigDecimal ID_TIPO_MISURA;
    private BigDecimal ID_TIPO_PREV;
    private BigDecimal IMPORTO_CAUZIONE;
    private String     LOCALITA;
    private BigDecimal MESI;
    private String     NOTE;
    private String     ULTERIORI_PRESCRIZIONI;
    private Character     BLOCCO;

    /**
     * @return the aNNI
     */
    public BigDecimal getANNI() {

        return ANNI;
    }

    /**
     * @return the aRT67
     */
    public String getART67() {

        return ART67;
    }

    /**
     * @return the cARATTERE_MISURA
     */
    public String getCARATTERE_MISURA() {

        return CARATTERE_MISURA;
    }

    /**
     * @return the dATA_FINE_VALIDITA
     */
    public Date getDATA_FINE_VALIDITA() {

        return DATA_FINE_VALIDITA;
    }

    /**
     * @return the dECISIONE_MISURA
     */
    public BigDecimal getDECISIONE_MISURA() {

        return DECISIONE_MISURA;
    }

    /**
	 * @return the dESC_DECISIONE_MISURA
	 */
	public String getDESC_DECISIONE_MISURA() {
		return DESC_DECISIONE_MISURA;
	}

	/**
     * @return the dESC_NORMATIVA
     */
    public String getDESC_NORMATIVA() {

        return DESC_NORMATIVA;
    }

    /**
     * @return the dESC_TIPO_MISURA
     */
    public String getDESC_TIPO_MISURA() {

        return DESC_TIPO_MISURA;
    }

    /**
     * @return the dESC_TIPO_PREV
     */
    public String getDESC_TIPO_PREV() {

        return DESC_TIPO_PREV;
    }

    /**
     * @return the gIORNI
     */
    public BigDecimal getGIORNI() {

        return GIORNI;
    }

    /**
     * @return the iD_ATTO
     */
    public BigDecimal getID_ATTO() {

        return ID_ATTO;
    }

    /**
     * @return the iD_MISURA_INSERITA
     */
    public BigDecimal getID_MISURA_INSERITA() {

        return ID_MISURA_INSERITA;
    }

    /**
     * @return the iD_NORMATIVA
     */
    public BigDecimal getID_NORMATIVA() {

        return ID_NORMATIVA;
    }

    /**
     * @return the iD_TIPO_MISURA
     */
    public BigDecimal getID_TIPO_MISURA() {

        return ID_TIPO_MISURA;
    }

    /**
     * @return the iD_TIPO_PREV
     */
    public BigDecimal getID_TIPO_PREV() {

        return ID_TIPO_PREV;
    }

    /**
     * @return the iMPORTO_CAUZIONE
     */
    public BigDecimal getIMPORTO_CAUZIONE() {

        return IMPORTO_CAUZIONE;
    }

    /**
     * @return the lOCALITA
     */
    public String getLOCALITA() {

        return LOCALITA;
    }

    /**
     * @return the mESI
     */
    public BigDecimal getMESI() {

        return MESI;
    }

    /**
     * @return the NOTE
     */
    public String getNOTE() {
    
        return NOTE;
    }

    /**
     * @return the uLTERIORI_PRESCRIZIONI
     */
    public String getULTERIORI_PRESCRIZIONI() {

        return ULTERIORI_PRESCRIZIONI;
    }

    /**
     * @param aNNI
     *            the aNNI to set
     */
    public void setANNI(BigDecimal aNNI) {

        ANNI = aNNI;
    }

    /**
     * @param aRT67
     *            the aRT67 to set
     */
    public void setART67(String aRT67) {

        ART67 = aRT67;
    }

    /**
     * @param cARATTERE_MISURA
     *            the cARATTERE_MISURA to set
     */
    public void setCARATTERE_MISURA(String cARATTERE_MISURA) {

        CARATTERE_MISURA = cARATTERE_MISURA;
    }

    /**
     * @param dATA_FINE_VALIDITA
     *            the dATA_FINE_VALIDITA to set
     */
    public void setDATA_FINE_VALIDITA(Date dATA_FINE_VALIDITA) {

        DATA_FINE_VALIDITA = dATA_FINE_VALIDITA;
    }

    /**
     * @param dECISIONE_MISURA
     *            the dECISIONE_MISURA to set
     */
    public void setDECISIONE_MISURA(BigDecimal dECISIONE_MISURA) {

        DECISIONE_MISURA = dECISIONE_MISURA;
    }

    /**
	 * @param dESC_DECISIONE_MISURA the dESC_DECISIONE_MISURA to set
	 */
	public void setDESC_DECISIONE_MISURA(String dESC_DECISIONE_MISURA) {
		DESC_DECISIONE_MISURA = dESC_DECISIONE_MISURA;
	}
    
    /**
     * @param dESC_NORMATIVA
     *            the dESC_NORMATIVA to set
     */
    public void setDESC_NORMATIVA(String dESC_NORMATIVA) {

        DESC_NORMATIVA = dESC_NORMATIVA;
    }

    /**
     * @param dESC_TIPO_MISURA
     *            the dESC_TIPO_MISURA to set
     */
    public void setDESC_TIPO_MISURA(String dESC_TIPO_MISURA) {

        DESC_TIPO_MISURA = dESC_TIPO_MISURA;
    }

    /**
     * @param dESC_TIPO_PREV
     *            the dESC_TIPO_PREV to set
     */
    public void setDESC_TIPO_PREV(String dESC_TIPO_PREV) {

        DESC_TIPO_PREV = dESC_TIPO_PREV;
    }

    /**
     * @param gIORNI
     *            the gIORNI to set
     */
    public void setGIORNI(BigDecimal gIORNI) {

        GIORNI = gIORNI;
    }

    /**
     * @param iD_ATTO
     *            the iD_ATTO to set
     */
    public void setID_ATTO(BigDecimal iD_ATTO) {

        ID_ATTO = iD_ATTO;
    }

    /**
     * @param iD_MISURA_INSERITA
     *            the iD_MISURA_INSERITA to set
     */
    public void setID_MISURA_INSERITA(BigDecimal iD_MISURA_INSERITA) {

        ID_MISURA_INSERITA = iD_MISURA_INSERITA;
    }

    /**
     * @param iD_NORMATIVA
     *            the iD_NORMATIVA to set
     */
    public void setID_NORMATIVA(BigDecimal iD_NORMATIVA) {

        ID_NORMATIVA = iD_NORMATIVA;
    }

    /**
     * @param iD_TIPO_MISURA
     *            the iD_TIPO_MISURA to set
     */
    public void setID_TIPO_MISURA(BigDecimal iD_TIPO_MISURA) {

        ID_TIPO_MISURA = iD_TIPO_MISURA;
    }

    /**
     * @param iD_TIPO_PREV
     *            the iD_TIPO_PREV to set
     */
    public void setID_TIPO_PREV(BigDecimal iD_TIPO_PREV) {

        ID_TIPO_PREV = iD_TIPO_PREV;
    }

    /**
     * @param iMPORTO_CAUZIONE
     *            the iMPORTO_CAUZIONE to set
     */
    public void setIMPORTO_CAUZIONE(BigDecimal iMPORTO_CAUZIONE) {

        IMPORTO_CAUZIONE = iMPORTO_CAUZIONE;
    }

    /**
     * @param lOCALITA
     *            the lOCALITA to set
     */
    public void setLOCALITA(String lOCALITA) {

        LOCALITA = lOCALITA;
    }

    /**
     * @param mESI
     *            the mESI to set
     */
    public void setMESI(BigDecimal mESI) {

        MESI = mESI;
    }

    /**
     * @param nOTE
     *            the nOTE to set
     */
    public void setNOTE(String nOTE) {
    
        NOTE = nOTE;
    }

    /**
     * @param uLTERIORI_PRESCRIZIONI
     *            the uLTERIORI_PRESCRIZIONI to set
     */
    public void setULTERIORI_PRESCRIZIONI(String uLTERIORI_PRESCRIZIONI) {

        ULTERIORI_PRESCRIZIONI = uLTERIORI_PRESCRIZIONI;
    }

	public BigDecimal getDECISIONE_MISURA_I_GRADO() {
		return DECISIONE_MISURA_I_GRADO;
	}

	public void setDECISIONE_MISURA_I_GRADO(BigDecimal dECISIONE_MISURA_I_GRADO) {
		DECISIONE_MISURA_I_GRADO = dECISIONE_MISURA_I_GRADO;
	}

	public String getDESC_DECISIONE_MISURA_I_GRADO() {
		return DESC_DECISIONE_MISURA_I_GRADO;
	}

	public void setDESC_DECISIONE_MISURA_I_GRADO(
			String dESC_DECISIONE_MISURA_I_GRADO) {
		DESC_DECISIONE_MISURA_I_GRADO = dESC_DECISIONE_MISURA_I_GRADO;
	}
	

	public BigDecimal getDECISIONE_MISURA_II_GRADO() {
		return DECISIONE_MISURA_II_GRADO;
	}

	public void setDECISIONE_MISURA_II_GRADO(BigDecimal dECISIONE_MISURA_II_GRADO) {
		DECISIONE_MISURA_II_GRADO = dECISIONE_MISURA_II_GRADO;
	}

	public String getDESC_DECISIONE_MISURA_II_GRADO() {
		return DESC_DECISIONE_MISURA_II_GRADO;
	}

	public void setDESC_DECISIONE_MISURA_II_GRADO(
			String dESC_DECISIONE_MISURA_II_GRADO) {
		DESC_DECISIONE_MISURA_II_GRADO = dESC_DECISIONE_MISURA_II_GRADO;
	}

	public Character getBLOCCO() {
		return BLOCCO;
	}

	public void setBLOCCO(Character bLOCCO) {
		BLOCCO = bLOCCO;
	}

	public BigDecimal getDEC_MISURA_III_GRADO() {
		return DEC_MISURA_III_GRADO;
	}

	public void setDEC_MISURA_III_GRADO(BigDecimal dEC_MISURA_III_GRADO) {
		DEC_MISURA_III_GRADO = dEC_MISURA_III_GRADO;
	}

	public String getDESC_DEC_MISURA_III_GRADO() {
		return DESC_DEC_MISURA_III_GRADO;
	}

	public void setDESC_DEC_MISURA_III_GRADO(
			String dESC_DEC_MISURA_III_GRADO) {
		DESC_DEC_MISURA_III_GRADO = dESC_DEC_MISURA_III_GRADO;
	}

	
    
}
