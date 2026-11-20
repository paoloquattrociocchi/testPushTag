package it.giustizia.smp.dto.pg_conservatorie;

import java.util.Date;

public class NotaSoggePersUnBeniDto 
{
    // campi in comune
    private Long        CODI_K_ID_NOTA;   
    private Integer     CODI_K_ID_UFF_OWNER;   
    private Character   CODI_K_TIPO_RECORD;   
    private Integer     CODI_K_PROGR_SOGG;
    private String      DESC_CODI_FISCALE;
    private String      DESC_UTENTE;
    private Date        DATA_ULT_AGG;
    private Character   CODI_FK_ID_TIPO_REGIME;   // PF - unita negoziali 
    private Integer     CODI_FK_PROGR_SOGG_C;     // PF - unita negoziali

    // campi persona fisica
    private String      DESC_COGNOME;
    private String      DESC_NOME;
    private Character   FLG_SESSO; 
    private Date        DATA_NASC;
    private String      DESC_CODI_COMUNE_NASC;
    private String      DESC_COMUNE_NASC;
    private String      DESC_PROV_NASC;
    
    // campi persona NON fisica
    private String      DESC_DENOMINAZIONE;
    private String      DESC_CODI_COMUNE_SEDE;
    private String      DESC_COMUNE_SEDE;
    private String      DESC_PROV_SEDE;

    // soggetto - unita negoziali
    private Integer     CODI_K_PROGR_UNEG;
    private Integer     CODI_K_ID_TIPO_INT;
    private Integer     NUME_PROGR_RIGA;
    private Integer     NUME_QUOTA_NUM_INT;
    private Integer     NUME_QUOTA_NUM_DEC;
    private Integer     NUME_QUOTA_DEN;
    private Character   FLG_TIPO_INT;
    private String      DESC_TIPO_INT;

    // soggetto - bene
    private Integer     CODI_K_PROGR_BENE;

    // Quadro D
    private Character   CODI_K_TIPO_INF;
    private Integer     CODI_K_PROGR_D;
    private String      DESC_NOTA;
    
    public NotaSoggePersUnBeniDto()
    {}

    public Long getCODI_K_ID_NOTA()
    {
        return CODI_K_ID_NOTA;
    }

    public void setCODI_K_ID_NOTA( Long cODI_K_ID_NOTA )
    {
        CODI_K_ID_NOTA = cODI_K_ID_NOTA;
    }

    public Integer getCODI_K_ID_UFF_OWNER()
    {
        return CODI_K_ID_UFF_OWNER;
    }

    public void setCODI_K_ID_UFF_OWNER( Integer cODI_K_ID_UFF_OWNER )
    {
        CODI_K_ID_UFF_OWNER = cODI_K_ID_UFF_OWNER;
    }

    public Character getCODI_K_TIPO_RECORD()
    {
        return CODI_K_TIPO_RECORD;
    }

    public void setCODI_K_TIPO_RECORD( Character cODI_K_TIPO_RECORD )
    {
        CODI_K_TIPO_RECORD = cODI_K_TIPO_RECORD;
    }

    public Integer getCODI_K_PROGR_SOGG()
    {
        return CODI_K_PROGR_SOGG;
    }

    public void setCODI_K_PROGR_SOGG( Integer cODI_K_PROGR_SOGG )
    {
        CODI_K_PROGR_SOGG = cODI_K_PROGR_SOGG;
    }

    public String getDESC_COGNOME()
    {
        return DESC_COGNOME;
    }

    public void setDESC_COGNOME( String dESC_COGNOME )
    {
        DESC_COGNOME = dESC_COGNOME;
    }

    public String getDESC_NOME()
    {
        return DESC_NOME;
    }

    public void setDESC_NOME( String dESC_NOME )
    {
        DESC_NOME = dESC_NOME;
    }

    public Character getFLG_SESSO()
    {
        return FLG_SESSO;
    }

    public void setFLG_SESSO( Character fLG_SESSO )
    {
        FLG_SESSO = fLG_SESSO;
    }

    public Date getDATA_NASC()
    {
        return DATA_NASC;
    }

    public void setDATA_NASC( Date dATA_NASC )
    {
        DATA_NASC = dATA_NASC;
    }

    public String getDESC_CODI_COMUNE_NASC()
    {
        return DESC_CODI_COMUNE_NASC;
    }

    public void setDESC_CODI_COMUNE_NASC( String dESC_CODI_COMUNE_NASC )
    {
        DESC_CODI_COMUNE_NASC = dESC_CODI_COMUNE_NASC;
    }

    public String getDESC_COMUNE_NASC()
    {
        return DESC_COMUNE_NASC;
    }

    public void setDESC_COMUNE_NASC( String dESC_COMUNE_NASC )
    {
        DESC_COMUNE_NASC = dESC_COMUNE_NASC;
    }

    public String getDESC_PROV_NASC()
    {
        return DESC_PROV_NASC;
    }

    public void setDESC_PROV_NASC( String dESC_PROV_NASC )
    {
        DESC_PROV_NASC = dESC_PROV_NASC;
    }

    public String getDESC_CODI_FISCALE()
    {
        return DESC_CODI_FISCALE;
    }

    public void setDESC_CODI_FISCALE( String dESC_CODI_FISCALE )
    {
        DESC_CODI_FISCALE = dESC_CODI_FISCALE;
    }

    public Character getCODI_FK_ID_TIPO_REGIME()
    {
        return CODI_FK_ID_TIPO_REGIME;
    }

    public void setCODI_FK_ID_TIPO_REGIME( Character cODI_FK_ID_TIPO_REGIME )
    {
        CODI_FK_ID_TIPO_REGIME = cODI_FK_ID_TIPO_REGIME;
    }

    public Integer getCODI_FK_PROGR_SOGG_C()
    {
        return CODI_FK_PROGR_SOGG_C;
    }

    public void setCODI_FK_PROGR_SOGG_C( Integer cODI_FK_PROGR_SOGG_C )
    {
        CODI_FK_PROGR_SOGG_C = cODI_FK_PROGR_SOGG_C;
    }

    public String getDESC_UTENTE()
    {
        return DESC_UTENTE;
    }

    public void setDESC_UTENTE( String dESC_UTENTE )
    {
        DESC_UTENTE = dESC_UTENTE;
    }

    public Date getDATA_ULT_AGG()
    {
        return DATA_ULT_AGG;
    }

    public void setDATA_ULT_AGG( Date dATA_ULT_AGG )
    {
        DATA_ULT_AGG = dATA_ULT_AGG;
    }

    public String getDESC_DENOMINAZIONE()
    {
        return DESC_DENOMINAZIONE;
    }

    public void setDESC_DENOMINAZIONE( String dESC_DENOMINAZIONE )
    {
        DESC_DENOMINAZIONE = dESC_DENOMINAZIONE;
    }

    public String getDESC_CODI_COMUNE_SEDE()
    {
        return DESC_CODI_COMUNE_SEDE;
    }

    public void setDESC_CODI_COMUNE_SEDE( String dESC_CODI_COMUNE_SEDE )
    {
        DESC_CODI_COMUNE_SEDE = dESC_CODI_COMUNE_SEDE;
    }

    public String getDESC_COMUNE_SEDE()
    {
        return DESC_COMUNE_SEDE;
    }

    public void setDESC_COMUNE_SEDE( String dESC_COMUNE_SEDE )
    {
        DESC_COMUNE_SEDE = dESC_COMUNE_SEDE;
    }

    public String getDESC_PROV_SEDE()
    {
        return DESC_PROV_SEDE;
    }

    public void setDESC_PROV_SEDE( String dESC_PROV_SEDE )
    {
        DESC_PROV_SEDE = dESC_PROV_SEDE;
    }

    public Integer getCODI_K_PROGR_UNEG()
    {
        return CODI_K_PROGR_UNEG;
    }

    public void setCODI_K_PROGR_UNEG( Integer cODI_K_PROGR_UNEG )
    {
        CODI_K_PROGR_UNEG = cODI_K_PROGR_UNEG;
    }

    public Integer getCODI_K_ID_TIPO_INT()
    {
        return CODI_K_ID_TIPO_INT;
    }

    public void setCODI_K_ID_TIPO_INT( Integer cODI_K_ID_TIPO_INT )
    {
        CODI_K_ID_TIPO_INT = cODI_K_ID_TIPO_INT;
    }

    public Integer getNUME_PROGR_RIGA()
    {
        return NUME_PROGR_RIGA;
    }

    public void setNUME_PROGR_RIGA( Integer nUME_PROGR_RIGA )
    {
        NUME_PROGR_RIGA = nUME_PROGR_RIGA;
    }

    public Integer getNUME_QUOTA_NUM_INT()
    {
        return NUME_QUOTA_NUM_INT;
    }

    public void setNUME_QUOTA_NUM_INT( Integer nUME_QUOTA_NUM_INT )
    {
        NUME_QUOTA_NUM_INT = nUME_QUOTA_NUM_INT;
    }

    public Integer getNUME_QUOTA_NUM_DEC()
    {
        return NUME_QUOTA_NUM_DEC;
    }

    public void setNUME_QUOTA_NUM_DEC( Integer nUME_QUOTA_NUM_DEC )
    {
        NUME_QUOTA_NUM_DEC = nUME_QUOTA_NUM_DEC;
    }

    public Integer getNUME_QUOTA_DEN()
    {
        return NUME_QUOTA_DEN;
    }

    public void setNUME_QUOTA_DEN( Integer nUME_QUOTA_DEN )
    {
        NUME_QUOTA_DEN = nUME_QUOTA_DEN;
    }

    public Character getFLG_TIPO_INT()
    {
        return FLG_TIPO_INT;
    }

    public void setFLG_TIPO_INT( Character fLG_TIPO_INT )
    {
        FLG_TIPO_INT = fLG_TIPO_INT;
    }

    public String getDESC_TIPO_INT()
    {
        return DESC_TIPO_INT;
    }

    public void setDESC_TIPO_INT( String dESC_TIPO_INT )
    {
        DESC_TIPO_INT = dESC_TIPO_INT;
    }

    public Integer getCODI_K_PROGR_BENE()
    {
        return CODI_K_PROGR_BENE;
    }

    public void setCODI_K_PROGR_BENE( Integer cODI_K_PROGR_BENE )
    {
        CODI_K_PROGR_BENE = cODI_K_PROGR_BENE;
    }

    public Character getCODI_K_TIPO_INF()
    {
        return CODI_K_TIPO_INF;
    }

    public void setCODI_K_TIPO_INF( Character cODI_K_TIPO_INF )
    {
        CODI_K_TIPO_INF = cODI_K_TIPO_INF;
    }

    public Integer getCODI_K_PROGR_D()
    {
        return CODI_K_PROGR_D;
    }

    public void setCODI_K_PROGR_D( Integer cODI_K_PROGR_D )
    {
        CODI_K_PROGR_D = cODI_K_PROGR_D;
    }

    public String getDESC_NOTA()
    {
        return DESC_NOTA;
    }

    public void setDESC_NOTA( String dESC_NOTA )
    {
        DESC_NOTA = dESC_NOTA;
    }

}
