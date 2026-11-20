package it.giustizia.smp.dto.pg_conservatorie;

import java.math.BigDecimal;

public class TitoloDirittoDto
{
    private BigDecimal PROG;         //T_NOTA_SOGG_BENE.CODI_K_PROGR_SOGG
    private String SOGGETTO;
    private String COGNOME;
    private String NOME;
    private String DENOMINAZIONE;
    private BigDecimal   PROGRIGA;
    private String DESCTIPOINT;
    private BigDecimal   NUMEQUOTAINT;
    private BigDecimal   NUMEQUOTADEC;
    private BigDecimal   NUMEQUOTADEN;
    private String NUMEQUOTA;
    private BigDecimal   SOGGETTOSITMP;
    private Character TIPOSOGG;
    private BigDecimal   IDTIPOINT;
    
    
    public BigDecimal getPROG()
    {
        return PROG;
    }
    public void setPROG( BigDecimal pROG )
    {
        PROG = pROG;
    }
    public String getSOGGETTO()
    {
        return SOGGETTO;
    }
    public void setSOGGETTO( String sOGGETTO )
    {
        SOGGETTO = sOGGETTO;
    }
    public String getCOGNOME()
    {
        return COGNOME;
    }
    public void setCOGNOME( String cOGNOME )
    {
        COGNOME = cOGNOME;
    }
    public String getNOME()
    {
        return NOME;
    }
    public void setNOME( String nOME )
    {
        NOME = nOME;
    }
    public String getDENOMINAZIONE()
    {
        return DENOMINAZIONE;
    }
    public void setDENOMINAZIONE( String dENOMINAZIONE )
    {
        DENOMINAZIONE = dENOMINAZIONE;
    }
    public BigDecimal getPROGRIGA()
    {
        return PROGRIGA;
    }
    public void setPROGRIGA( BigDecimal pROGRIGA )
    {
        PROGRIGA = pROGRIGA;
    }
    public String getDESCTIPOINT()
    {
        return DESCTIPOINT;
    }
    public void setDESCTIPOINT( String dESCTIPOINT )
    {
        DESCTIPOINT = dESCTIPOINT;
    }
    public BigDecimal getNUMEQUOTAINT()
    {
        return NUMEQUOTAINT;
    }
    public void setNUMEQUOTAINT( BigDecimal nUMEQUOTAINT )
    {
        NUMEQUOTAINT = nUMEQUOTAINT;
    }
    public BigDecimal getNUMEQUOTADEC()
    {
        return NUMEQUOTADEC;
    }
    public void setNUMEQUOTADEC( BigDecimal nUMEQUOTADEC )
    {
        NUMEQUOTADEC = nUMEQUOTADEC;
    }
    public BigDecimal getNUMEQUOTADEN()
    {
        return NUMEQUOTADEN;
    }
    public void setNUMEQUOTADEN( BigDecimal nUMEQUOTADEN )
    {
        NUMEQUOTADEN = nUMEQUOTADEN;
    }
    public String getNUMEQUOTA()
    {
        return NUMEQUOTA;
    }
    public void setNUMEQUOTA( String nUMEQUOTA )
    {
        NUMEQUOTA = nUMEQUOTA;
    }
    public BigDecimal getSOGGETTOSITMP()
    {
        return SOGGETTOSITMP;
    }
    public void setSOGGETTOSITMP( BigDecimal sOGGETTOSITMP )
    {
        SOGGETTOSITMP = sOGGETTOSITMP;
    }
    public Character getTIPOSOGG()
    {
        return TIPOSOGG;
    }
    public void setTIPOSOGG( Character tIPOSOGG )
    {
        TIPOSOGG = tIPOSOGG;
    }
    public BigDecimal getIDTIPOINT()
    {
        return IDTIPOINT;
    }
    public void setIDTIPOINT( BigDecimal iDTIPOINT )
    {
        IDTIPOINT = iDTIPOINT;
    }

}
