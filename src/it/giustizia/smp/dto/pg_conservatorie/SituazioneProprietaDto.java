package it.giustizia.smp.dto.pg_conservatorie;

import java.math.BigDecimal;

//import java.util.Comparator;

public class SituazioneProprietaDto implements Comparable<SituazioneProprietaDto>
{
    private Character   IDTIPORECORD;   
    private BigDecimal  PROGRSOGGETTO;
    private BigDecimal  IDTIPOINTERESSE;
    private BigDecimal  QUOTAINTERA;  
    private BigDecimal  QUOTADECIMALE;
    private BigDecimal  QUOTADENOM; 
    private Character   FLAGTIPOINTERESSE;   
    private String      DESCTIPOINTERESSE;   
    private BigDecimal  PROGRSOGGC;   
    private Character   TIPOREGIME;   
    
    private String  PROGRBENE;        //  T_NOTA_SOGG_BENE.CODI_K_PROGR_BENE as progrBene
    private String  PROGRSOGG;        //  T_NOTA_SOGG_BENE.CODI_K_PROGR_SOGG as progrSogg
    private String  TIPOINTERESSE;    //  T_NOTA_SOGG_BENE.CODI_K_ID_TIPO_INT as tipoInteresse
    private String  QUOTANUMERATORE;  //  T_NOTA_SOGG_BENE.NUME_QUOTA_NUM_DEC as quotaDecimale
    private String  QUOTADENOMINATORE; //  T_NOTA_SOGG_BENE.NUME_QUOTA_DEN as quotaDenom
    private String  CODFIS;           //  T_NOTA_SOGG_PF.DESC_CODI_FISCALE as Cod_Fis
    private String  PARTIVA;          //  T_NOTA_SOGG_PNF.DESC_CODI_FISCALE as part_Iva
    private String  STRINGAPARZUN;    //  AAAAAAAAAAAAAAAAX050 -- ovvero; codFis / partIva + tipoInteresse + quotaIntera, quotaDecimale

    private Float   QUOTA;            // = Float.valueOf( quotaNumeratore )/Float.valueOf( quotaDenominatore );
    private String  QUOTASSTR;        // = quota.toString();
    private String  CODFISPI;         //  CODICE FISCALE oppure PARTITA IVA
    private boolean GOOD = false;     //  CODICE FISCALE oppure PARTITA IVA

    /*  nvl( TRIM ( BOTH '.' FROM TRIM ( TRAILING '0' FROM 
                nvl( T_NOTA_SOGG_BENE.NUME_QUOTA_NUM_INT, 0 )  || '.' || nvl( T_NOTA_SOGG_BENE.NUME_QUOTA_NUM_DEC, 0 ) 
           )  ),'0'  ) as quotaNumeratore, 
    */
    public SituazioneProprietaDto()
    {}

    public int compareTo( SituazioneProprietaDto sitPr2 )
    {
        int ret = this.getCODFISPI().compareTo( sitPr2.getCODFISPI() );
        if( ret == 0 )
            ret = this.getTIPOINTERESSE().compareTo( sitPr2.getTIPOINTERESSE() );
        if( ret == 0 )
            ret = this.getPROGRBENE().compareTo( sitPr2.getPROGRBENE() );
        return ret;
    }

    /**
     * Trovo gli errori: good=false
     * Determino la quota d'interesse := (quotaIntera,quotaDecimale) / quotaDenominatore
     * Costruisco la stringa Parziale := (codiceFiscale o partitaIva) + tipoInteresse + quotaInteresse
     * @return
     */
    public boolean validate()
    {
        GOOD = false;
        if( TIPOINTERESSE == null )
            return false;
        if( CODFIS != null )
            CODFISPI = CODFIS;
        else if( PARTIVA != null )
            CODFISPI = PARTIVA;
        else return false;

        CODFISPI = (CODFISPI + "                ").substring( 0, 16 );
        TIPOINTERESSE = (TIPOINTERESSE + "      ").substring( 0, 6 );

        QUOTA = Float.valueOf( QUOTANUMERATORE ) / Float.valueOf( QUOTADENOMINATORE );
        QUOTASSTR = QUOTA.toString();

        STRINGAPARZUN = CODFISPI + TIPOINTERESSE + QUOTASSTR;
        GOOD = true;
        return true;
    }

    public Character getIDTIPORECORD()
    {
        return IDTIPORECORD;
    }

    public void setIDTIPORECORD( Character iDTIPORECORD )
    {
        IDTIPORECORD = iDTIPORECORD;
    }

    public BigDecimal getPROGRSOGGETTO()
    {
        return PROGRSOGGETTO;
    }

    public void setPROGRSOGGETTO( BigDecimal pROGRSOGGETTO )
    {
        PROGRSOGGETTO = pROGRSOGGETTO;
    }

    public BigDecimal getIDTIPOINTERESSE()
    {
        return IDTIPOINTERESSE;
    }

    public void setIDTIPOINTERESSE( BigDecimal iDTIPOINTERESSE )
    {
        IDTIPOINTERESSE = iDTIPOINTERESSE;
    }

    public BigDecimal getQUOTAINTERA()
    {
        return QUOTAINTERA;
    }

    public void setQUOTAINTERA( BigDecimal qUOTAINTERA )
    {
        QUOTAINTERA = qUOTAINTERA;
    }

    public BigDecimal getQUOTADECIMALE()
    {
        return QUOTADECIMALE;
    }

    public void setQUOTADECIMALE( BigDecimal qUOTADECIMALE )
    {
        QUOTADECIMALE = qUOTADECIMALE;
    }

    public BigDecimal getQUOTADENOM()
    {
        return QUOTADENOM;
    }

    public void setQUOTADENOM( BigDecimal qUOTADENOM )
    {
        QUOTADENOM = qUOTADENOM;
    }

    public Character getFLAGTIPOINTERESSE()
    {
        return FLAGTIPOINTERESSE;
    }

    public void setFLAGTIPOINTERESSE( Character fLAGTIPOINTERESSE )
    {
        FLAGTIPOINTERESSE = fLAGTIPOINTERESSE;
    }

    public String getDESCTIPOINTERESSE()
    {
        return DESCTIPOINTERESSE;
    }

    public void setDESCTIPOINTERESSE( String dESCTIPOINTERESSE )
    {
        DESCTIPOINTERESSE = dESCTIPOINTERESSE;
    }

    public BigDecimal getPROGRSOGGC()
    {
        return PROGRSOGGC;
    }

    public void setPROGRSOGGC( BigDecimal pROGRSOGGC )
    {
        PROGRSOGGC = pROGRSOGGC;
    }

    public Character getTIPOREGIME()
    {
        return TIPOREGIME;
    }

    public void setTIPOREGIME( Character tIPOREGIME )
    {
        TIPOREGIME = tIPOREGIME;
    }

    public String getPROGRBENE()
    {
        return PROGRBENE;
    }

    public void setPROGRBENE( String pROGRBENE )
    {
        PROGRBENE = pROGRBENE;
    }

    public String getPROGRSOGG()
    {
        return PROGRSOGG;
    }

    public void setPROGRSOGG( String pROGRSOGG )
    {
        PROGRSOGG = pROGRSOGG;
    }

    public String getTIPOINTERESSE()
    {
        return TIPOINTERESSE;
    }

    public void setTIPOINTERESSE( String tIPOINTERESSE )
    {
        TIPOINTERESSE = tIPOINTERESSE;
    }

    public String getQUOTANUMERATORE()
    {
        return QUOTANUMERATORE;
    }

    public void setQUOTANUMERATORE( String qUOTANUMERATORE )
    {
        QUOTANUMERATORE = qUOTANUMERATORE;
    }

    public String getQUOTADENOMINATORE()
    {
        return QUOTADENOMINATORE;
    }

    public void setQUOTADENOMINATORE( String qUOTADENOMINATORE )
    {
        QUOTADENOMINATORE = qUOTADENOMINATORE;
    }

    public String getCODFIS()
    {
        return CODFIS;
    }

    public void setCODFIS( String cODFIS )
    {
        CODFIS = cODFIS;
    }

    public String getPARTIVA()
    {
        return PARTIVA;
    }

    public void setPARTIVA( String pARTIVA )
    {
        PARTIVA = pARTIVA;
    }

    public String getSTRINGAPARZUN()
    {
        return STRINGAPARZUN;
    }

    public void setSTRINGAPARZUN( String sTRINGAPARZUN )
    {
        STRINGAPARZUN = sTRINGAPARZUN;
    }

    public Float getQUOTA()
    {
        return QUOTA;
    }

    public void setQUOTA( Float qUOTA )
    {
        QUOTA = qUOTA;
    }

    public String getQUOTASSTR()
    {
        return QUOTASSTR;
    }

    public void setQUOTASSTR( String qUOTASSTR )
    {
        QUOTASSTR = qUOTASSTR;
    }

    public String getCODFISPI()
    {
        return CODFISPI;
    }

    public void setCODFISPI( String cODFISPI )
    {
        CODFISPI = cODFISPI;
    }

    public boolean isGOOD()
    {
        return GOOD;
    }

    public void setGOOD( boolean gOOD )
    {
        GOOD = gOOD;
    }
}
