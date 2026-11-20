package it.giustizia.smp.dto.pg_procedimento;

import java.math.BigDecimal;
import java.util.Date;

public class SogTitDettaglioProcedimentoDto {

    protected String     COGNOME;
    protected String     COM_EST_NASC;
    protected String     COM_EST_SEDE;
//    protected Date       DATA_ISCRIZIONE;
    protected Date       DATA_NASCITA;
    protected String     DESC_COMUNE_NASC;
    protected String     DESC_COMUNE_SEDE;
    protected String     DESC_STATO_NASC;
    protected String     DESC_STATO_SEDE;
    protected BigDecimal ID_COM_NASC;
    protected BigDecimal ID_COM_SEDE;
    protected BigDecimal ID_PERS;
    protected BigDecimal ID_STATO_NASC;
    protected BigDecimal ID_STATO_SEDE;
    protected BigDecimal ID_VER;
    protected String     NOME;
    protected String     PARTITA_IVA;
    protected String     RAG_SOC;
    protected String     SIGLA_PROVINCIA_NASC;
    protected String     SIGLA_PROVINCIA_SEDE;
    protected Character  TIPO_PERS;
    private String       ART67;
    private String       CAUZIONE;
    private BigDecimal   CODI_K_ID_TIPO_INT;
    private String       DESC_RUOLO;
    private int			 ID_RUOLO;
    private String       DESC_TIPO_INT;
//    private BigDecimal   ID_FONTE;
//    private BigDecimal   ID_TIPO_PREV;
//    private BigDecimal   ID_TIPO_PROC;
    private BigDecimal   ID_UFF_OWNER;
    private String       MISURA;
    private BigDecimal   NUME_FRAZIONE_DEN;
    private BigDecimal   NUME_FRAZIONE_NUM;
    private BigDecimal   NUME_QUOTA;
    private Character    SESSO;
    protected BigDecimal CODI_FK_ID_TIPO_SOC;
    protected BigDecimal CODI_FK_ID_TIPO_MIS;

  

	public String getART67() {

        return ART67;
    }

    public String getCAUZIONE() {

        return CAUZIONE;
    }

    public BigDecimal getCODI_K_ID_TIPO_INT() {

        return CODI_K_ID_TIPO_INT;
    }

    public String getCOGNOME() {

        return COGNOME;
    }

    public String getCOM_EST_NASC() {

        return COM_EST_NASC;
    }

    public String getCOM_EST_SEDE() {

        return COM_EST_SEDE;
    }

    public Date getDATA_NASCITA() {

        return DATA_NASCITA;
    }

    public String getDESC_COMUNE_NASC() {

        return DESC_COMUNE_NASC;
    }

    public String getDESC_COMUNE_SEDE() {

        return DESC_COMUNE_SEDE;
    }

    public String getDESC_RUOLO() {

        return DESC_RUOLO;
    }

    public String getDESC_STATO_NASC() {

        return DESC_STATO_NASC;
    }

    public String getDESC_STATO_SEDE() {

        return DESC_STATO_SEDE;
    }

    public String getDESC_TIPO_INT() {

        return DESC_TIPO_INT;
    }

    public BigDecimal getID_COM_NASC() {

        return ID_COM_NASC;
    }

    public BigDecimal getID_COM_SEDE() {

        return ID_COM_SEDE;
    }

    public BigDecimal getID_PERS() {

        return ID_PERS;
    }

    public BigDecimal getID_STATO_NASC() {

        return ID_STATO_NASC;
    }

    public BigDecimal getID_STATO_SEDE() {

        return ID_STATO_SEDE;
    }

    public BigDecimal getID_UFF_OWNER() {

        return ID_UFF_OWNER;
    }

    public BigDecimal getID_VER() {

        return ID_VER;
    }

    public String getMISURA() {

        return MISURA;
    }

    public String getNOME() {

        return NOME;
    }

    public BigDecimal getNUME_FRAZIONE_DEN() {

        return NUME_FRAZIONE_DEN;
    }

    public BigDecimal getNUME_FRAZIONE_NUM() {

        return NUME_FRAZIONE_NUM;
    }

    public BigDecimal getNUME_QUOTA() {

        return NUME_QUOTA;
    }


    public String getRAG_SOC() {

        return RAG_SOC;
    }

    public String getSIGLA_PROVINCIA_NASC() {

        return SIGLA_PROVINCIA_NASC;
    }

    public String getSIGLA_PROVINCIA_SEDE() {

        return SIGLA_PROVINCIA_SEDE;
    }

    public Character getTIPO_PERS() {

        return TIPO_PERS;
    }

    public void setART67(String aRT67) {

        ART67 = aRT67;
    }

    public void setCAUZIONE(String cAUZIONE) {

        CAUZIONE = cAUZIONE;
    }

    public void setCODI_K_ID_TIPO_INT(BigDecimal cODI_K_ID_TIPO_INT) {

        CODI_K_ID_TIPO_INT = cODI_K_ID_TIPO_INT;
    }

    public void setCOGNOME(String cOGNOME) {

        COGNOME = cOGNOME;
    }

    public void setCOM_EST_NASC(String cOM_EST_NASC) {

        COM_EST_NASC = cOM_EST_NASC;
    }

    public void setCOM_EST_SEDE(String cOM_EST_SEDE) {

        COM_EST_SEDE = cOM_EST_SEDE;
    }

    public void setDATA_NASCITA(Date dATA_NASCITA) {

        DATA_NASCITA = dATA_NASCITA;
    }

    public void setDESC_COMUNE_NASC(String dESC_COMUNE_NASC) {

        DESC_COMUNE_NASC = dESC_COMUNE_NASC;
    }

    public void setDESC_COMUNE_SEDE(String dESC_COMUNE_SEDE) {

        DESC_COMUNE_SEDE = dESC_COMUNE_SEDE;
    }

    public void setDESC_RUOLO(String dESC_RUOLO) {

        DESC_RUOLO = dESC_RUOLO;
    }

    public void setDESC_STATO_NASC(String dESC_STATO_NASC) {

        DESC_STATO_NASC = dESC_STATO_NASC;
    }

    public void setDESC_STATO_SEDE(String dESC_STATO_SEDE) {

        DESC_STATO_SEDE = dESC_STATO_SEDE;
    }

    public void setDESC_TIPO_INT(String dESC_TIPO_INT) {

        DESC_TIPO_INT = dESC_TIPO_INT;
    }

    public void setID_COM_NASC(BigDecimal iD_COM_NASC) {

        ID_COM_NASC = iD_COM_NASC;
    }

    public void setID_COM_SEDE(BigDecimal iD_COM_SEDE) {

        ID_COM_SEDE = iD_COM_SEDE;
    }

    public void setID_PERS(BigDecimal iD_PERS) {

        ID_PERS = iD_PERS;
    }

    public void setID_STATO_NASC(BigDecimal iD_STATO_NASC) {

        ID_STATO_NASC = iD_STATO_NASC;
    }

    public void setID_STATO_SEDE(BigDecimal iD_STATO_SEDE) {

        ID_STATO_SEDE = iD_STATO_SEDE;
    }

    public void setID_UFF_OWNER(BigDecimal iD_UFF_OWNER) {

        ID_UFF_OWNER = iD_UFF_OWNER;
    }

    public void setID_VER(BigDecimal iD_VER) {

        ID_VER = iD_VER;
    }

    public void setMISURA(String mISURA) {

        MISURA = mISURA;
    }

    public void setNOME(String nOME) {

        NOME = nOME;
    }

    public void setNUME_FRAZIONE_DEN(BigDecimal nUME_FRAZIONE_DEN) {

        NUME_FRAZIONE_DEN = nUME_FRAZIONE_DEN;
    }

    public void setNUME_FRAZIONE_NUM(BigDecimal nUME_FRAZIONE_NUM) {

        NUME_FRAZIONE_NUM = nUME_FRAZIONE_NUM;
    }

    public void setNUME_QUOTA(BigDecimal nUME_QUOTA) {

        NUME_QUOTA = nUME_QUOTA;
    }

    public void setRAG_SOC(String rAG_SOC) {

        RAG_SOC = rAG_SOC;
    }

    public void setSIGLA_PROVINCIA_NASC(String sIGLA_PROVINCIA_NASC) {

        SIGLA_PROVINCIA_NASC = sIGLA_PROVINCIA_NASC;
    }

    public void setSIGLA_PROVINCIA_SEDE(String sIGLA_PROVINCIA_SEDE) {

        SIGLA_PROVINCIA_SEDE = sIGLA_PROVINCIA_SEDE;
    }

    public void setTIPO_PERS(Character tIPO_PERS) {

        TIPO_PERS = tIPO_PERS;
    }

	/**
	 * @return the cODI_FK_ID_TIPO_SOC
	 */
	public BigDecimal getCODI_FK_ID_TIPO_SOC() {
		return CODI_FK_ID_TIPO_SOC;
	}

	/**
	 * @param cODI_FK_ID_TIPO_SOC the cODI_FK_ID_TIPO_SOC to set
	 */
	public void setCODI_FK_ID_TIPO_SOC(BigDecimal cODI_FK_ID_TIPO_SOC) {
		CODI_FK_ID_TIPO_SOC = cODI_FK_ID_TIPO_SOC;
	}

	public Character getSESSO() {
		return SESSO;
	}

	public void setSESSO(Character sESSO) {
		SESSO = sESSO;
	}

	public String getPARTITA_IVA() {
		return PARTITA_IVA;
	}

	public void setPARTITA_IVA(String pARTITA_IVA) {
		PARTITA_IVA = pARTITA_IVA;
	}

	public int getID_RUOLO() {
		return ID_RUOLO;
	}

	public void setID_RUOLO(int iD_RUOLO) {
		ID_RUOLO = iD_RUOLO;
	}

	public BigDecimal getCODI_FK_ID_TIPO_MIS() {
		return CODI_FK_ID_TIPO_MIS;
	}

	public void setCODI_FK_ID_TIPO_MIS(BigDecimal cODI_FK_ID_TIPO_MIS) {
		CODI_FK_ID_TIPO_MIS = cODI_FK_ID_TIPO_MIS;
	}


    
    
}
