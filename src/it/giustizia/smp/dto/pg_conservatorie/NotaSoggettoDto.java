package it.giustizia.smp.dto.pg_conservatorie;

import java.util.Date;

public class NotaSoggettoDto 
{
    // campi in comune
    private Long        CODI_K_ID_NOTA;   
    private Integer     CODI_K_ID_UFF_OWNER;   
    private Character   CODI_K_TIPO_RECORD;   
    private Integer     CODI_K_PROGR_SOGG;
    private Character   FLG_TIPO_SOGG;   
    private Character   FLG_ERR; 
    private String      DESC_UTENTE;
    private Date        DATA_ULT_AGG;
    private Long        CODI_FK_ID_SOGG_INT;
    
    public NotaSoggettoDto()
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

    public Character getFLG_TIPO_SOGG()
    {
        return FLG_TIPO_SOGG;
    }

    public void setFLG_TIPO_SOGG( Character fLG_TIPO_SOGG )
    {
        FLG_TIPO_SOGG = fLG_TIPO_SOGG;
    }

    public Character getFLG_ERR()
    {
        return FLG_ERR;
    }

    public void setFLG_ERR( Character fLG_ERR )
    {
        FLG_ERR = fLG_ERR;
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

    public Long getCODI_FK_ID_SOGG_INT()
    {
        return CODI_FK_ID_SOGG_INT;
    }

    public void setCODI_FK_ID_SOGG_INT( Long cODI_FK_ID_SOGG_INT )
    {
        CODI_FK_ID_SOGG_INT = cODI_FK_ID_SOGG_INT;
    }

}
