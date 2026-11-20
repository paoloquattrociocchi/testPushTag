package it.giustizia.smp.dto.pg_conservatorie;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class SoggettoInteressatoDto
{

    private String      descSoggCon; 

    // ATTRIBUTI Soggetto Generico

    private BigDecimal  IDSOGGETTO;      /* T_SOGGETTO.CODI_K_ID_SOGG_INT */
    private BigDecimal  VERSIONE;        /* T_REL_PROC_SOGINT.CODI_FK_ID_VER */
    private BigDecimal  PROGRESSIVO;     /* T_NOTA_SOGG.CODI_K_PROGR_SOGG */
    private Character   TIPOLOGIA;       /* T_NOTA_SOGG.FLG_TIPO_SOGG */
    private String      SOGGETTO;        /* cognome + nome + "nato a" + comune nascita + "il" + data nascita  oppure  denominazione + 'a' + comune sede */
	private BigDecimal	SOGGETTOSITMP; 
    private Character   FLAGERRORE;      /* FLG_ERR; */

    // ATTRIBUTI Soggetto Fisico

	private String 		COGNOME; 
	private String 		NOME; 
	private Character 	FLGSESSO; 
    @DateTimeFormat( pattern = "dd/MM/yyyy" )
    private Date        DATAMORTE;       //dataMorte
    @DateTimeFormat( pattern = "dd/MM/yyyy" )
	private Date 		DATANASCITA; 
	private String 		CODICECOMUNENASC; 
	private String 		COMUNENASCITA; 
	private String 		PROVNASCITA; 
    private String      STATONASCITA;
	private String 		CODICEFISCALEPF; 
	private Character 	REGIME; 
	private String 		DESCREGIME; 
	private BigDecimal 	CODPROGRSOGGC ;
   
    // ATTRIBUTI Soggetto Giuridico
    private String    DENOMINAZIONE;
    private String    STATOSEDE;
    private BigDecimal CODICESTATOSEDE;
    private String    COMUNESEDE;
    private String    PROVSEDE;
    private String    PARTITAIVA;
    private String    CODICECOMUNESEDE;

    // ATTRIBUTI Soggetto Unit‡Negoziali
    private BigDecimal  PROGSOGGETTO;       			/* T_NOTA_SOGG_UN.CODI_K_PROGR_SOGG */
    private BigDecimal  PROGUNITANEGOZIALE; 			/* T_NOTA_SOGG_UN.CODI_K_PROGR_UNEG */
    private Character   QUALIFICAUNITANEGOZIALE;		/* T_NOTA_SOGG_UN.CODI_K_TIPO_RECORD */
    private BigDecimal	NUMEQUOTAINTUNITANEGOZIALE;	/* T_NOTA_SOGG_UN.NUME_QUOTA_NUM_INT */
    private BigDecimal	NUMEQUOTADECUNITANEGOZIALE;	/* T_NOTA_SOGG_UN.NUME_QUOTA_NUM_DEC */
    private BigDecimal	NUMEQUOTADENOMUNITANEGOZIALE;	/* T_NOTA_SOGG_UN.NUME_QUOTA_DEN */
    private BigDecimal	TITOLOINTERESSEUNITANEGOZIALE;/* T_NOTA_SOGG_UN.CODI_K_ID_TIPO_INT */
    private String	    DESCRINTERESSEUNITANEGOZIALE;	/* T_NOTA_SOGG_UN.DESC_TIPO_INT */
    private Character 	IDREGIMEUNITANEGOZIALE;  		/* T_NOTA_SOGG_UN.CODI_FK_ID_TIPO_REGIME */
    private BigDecimal	REGIMECONIUGIUNITANEGOZIALE;	/* T_NOTA_SOGG_UN.CODI_FK_PROGR_SOGG_C */
   
    public SoggettoInteressatoDto()
    {}

	public BigDecimal getIDSOGGETTO() {
		return IDSOGGETTO;
	}

	public void setIDSOGGETTO(BigDecimal iDSOGGETTO) {
		IDSOGGETTO = iDSOGGETTO;
	}

	public BigDecimal getVERSIONE() {
		return VERSIONE;
	}

	public void setVERSIONE(BigDecimal vERSIONE) {
		VERSIONE = vERSIONE;
	}

	public BigDecimal getPROGRESSIVO() {
		return PROGRESSIVO;
	}

	public void setPROGRESSIVO(BigDecimal pROGRESSIVO) {
		PROGRESSIVO = pROGRESSIVO;
	}

	public Character getTIPOLOGIA() {
		return TIPOLOGIA;
	}

	public void setTIPOLOGIA(Character tIPOLOGIA) {
		TIPOLOGIA = tIPOLOGIA;
	}

	public String getSOGGETTO() {
		return SOGGETTO;
	}

	public void setSOGGETTO(String sOGGETTO) {
		SOGGETTO = sOGGETTO;
	}

	public BigDecimal getSOGGETTOSITMP() {
		return SOGGETTOSITMP;
	}

	public void setSOGGETTOSITMP(BigDecimal sOGGETTOSITMP) {
		SOGGETTOSITMP = sOGGETTOSITMP;
	}

	public Character getFLAGERRORE() {
		return FLAGERRORE;
	}

	public void setFLAGERRORE(Character fLAGERRORE) {
		FLAGERRORE = fLAGERRORE;
	}

	public String getCOGNOME() {
		return COGNOME;
	}

	public void setCOGNOME(String cOGNOME) {
		COGNOME = cOGNOME;
	}

	public String getNOME() {
		return NOME;
	}

	public void setNOME(String nOME) {
		NOME = nOME;
	}

	public Character getFLGSESSO() {
		return FLGSESSO;
	}

	public void setFLGSESSO(Character fLGSESSO) {
		FLGSESSO = fLGSESSO;
	}

	public Date getDATAMORTE() {
		return DATAMORTE;
	}

	public void setDATAMORTE(Date dATAMORTE) {
		DATAMORTE = dATAMORTE;
	}

	public Date getDATANASCITA() {
		return DATANASCITA;
	}

	public void setDATANASCITA(Date dATANASCITA) {
		DATANASCITA = dATANASCITA;
	}

	public String getCODICECOMUNENASC() {
		return CODICECOMUNENASC;
	}

	public void setCODICECOMUNENASC(String cODICECOMUNENASC) {
		CODICECOMUNENASC = cODICECOMUNENASC;
	}

	public String getCOMUNENASCITA() {
		return COMUNENASCITA;
	}

	public void setCOMUNENASCITA(String cOMUNENASCITA) {
		COMUNENASCITA = cOMUNENASCITA;
	}

	public String getPROVNASCITA() {
		return PROVNASCITA;
	}

	public void setPROVNASCITA(String pROVNASCITA) {
		PROVNASCITA = pROVNASCITA;
	}

	public String getSTATONASCITA() {
		return STATONASCITA;
	}

	public void setSTATONASCITA(String sTATONASCITA) {
		STATONASCITA = sTATONASCITA;
	}

	public String getCODICEFISCALEPF() {
		return CODICEFISCALEPF;
	}

	public void setCODICEFISCALEPF(String cODICEFISCALEPF) {
		CODICEFISCALEPF = cODICEFISCALEPF;
	}

	public Character getREGIME() {
		return REGIME;
	}

	public void setREGIME(Character rEGIME) {
		REGIME = rEGIME;
	}

	public String getDESCREGIME() {
		return DESCREGIME;
	}

	public void setDESCREGIME(String dESCREGIME) {
		DESCREGIME = dESCREGIME;
	}

	public BigDecimal getCODPROGRSOGGC() {
		return CODPROGRSOGGC;
	}

	public void setCODPROGRSOGGC(BigDecimal cODPROGRSOGGC) {
		CODPROGRSOGGC = cODPROGRSOGGC;
	}

	public String getDENOMINAZIONE() {
		return DENOMINAZIONE;
	}

	public void setDENOMINAZIONE(String dENOMINAZIONE) {
		DENOMINAZIONE = dENOMINAZIONE;
	}

	public String getSTATOSEDE() {
		return STATOSEDE;
	}

	public void setSTATOSEDE(String sTATOSEDE) {
		STATOSEDE = sTATOSEDE;
	}

	
	public BigDecimal getCODICESTATOSEDE() {
		return CODICESTATOSEDE;
	}

	public void setCODICESTATOSEDE(BigDecimal cODICESTATOSEDE) {
		CODICESTATOSEDE = cODICESTATOSEDE;
	}

	public String getCOMUNESEDE() {
		return COMUNESEDE;
	}

	public void setCOMUNESEDE(String cOMUNESEDE) {
		COMUNESEDE = cOMUNESEDE;
	}

	public String getPROVSEDE() {
		return PROVSEDE;
	}

	public void setPROVSEDE(String pROVSEDE) {
		PROVSEDE = pROVSEDE;
	}

	public String getPARTITAIVA() {
		return PARTITAIVA;
	}

	public void setPARTITAIVA(String pARTITAIVA) {
		PARTITAIVA = pARTITAIVA;
	}

	public String getCODICECOMUNESEDE() {
		return CODICECOMUNESEDE;
	}

	public void setCODICECOMUNESEDE(String cODICECOMUNESEDE) {
		CODICECOMUNESEDE = cODICECOMUNESEDE;
	}

	public BigDecimal getPROGSOGGETTO() {
		return PROGSOGGETTO;
	}

	public void setPROGSOGGETTO(BigDecimal pROGSOGGETTO) {
		PROGSOGGETTO = pROGSOGGETTO;
	}

	public BigDecimal getPROGUNITANEGOZIALE() {
		return PROGUNITANEGOZIALE;
	}

	public void setPROGUNITANEGOZIALE(BigDecimal pROGUNITANEGOZIALE) {
		PROGUNITANEGOZIALE = pROGUNITANEGOZIALE;
	}

	public Character getQUALIFICAUNITANEGOZIALE() {
		return QUALIFICAUNITANEGOZIALE;
	}

	public void setQUALIFICAUNITANEGOZIALE(Character qUALIFICAUNITANEGOZIALE) {
		QUALIFICAUNITANEGOZIALE = qUALIFICAUNITANEGOZIALE;
	}

	public BigDecimal getNUMEQUOTAINTUNITANEGOZIALE() {
		return NUMEQUOTAINTUNITANEGOZIALE;
	}

	public void setNUMEQUOTAINTUNITANEGOZIALE(BigDecimal nUMEQUOTAINTUNITANEGOZIALE) {
		NUMEQUOTAINTUNITANEGOZIALE = nUMEQUOTAINTUNITANEGOZIALE;
	}

	public BigDecimal getNUMEQUOTADECUNITANEGOZIALE() {
		return NUMEQUOTADECUNITANEGOZIALE;
	}

	public void setNUMEQUOTADECUNITANEGOZIALE(BigDecimal nUMEQUOTADECUNITANEGOZIALE) {
		NUMEQUOTADECUNITANEGOZIALE = nUMEQUOTADECUNITANEGOZIALE;
	}

	public BigDecimal getNUMEQUOTADENOMUNITANEGOZIALE() {
		return NUMEQUOTADENOMUNITANEGOZIALE;
	}

	public void setNUMEQUOTADENOMUNITANEGOZIALE(
			BigDecimal nUMEQUOTADENOMUNITANEGOZIALE) {
		NUMEQUOTADENOMUNITANEGOZIALE = nUMEQUOTADENOMUNITANEGOZIALE;
	}

	public BigDecimal getTITOLOINTERESSEUNITANEGOZIALE() {
		return TITOLOINTERESSEUNITANEGOZIALE;
	}

	public void setTITOLOINTERESSEUNITANEGOZIALE(
			BigDecimal tITOLOINTERESSEUNITANEGOZIALE) {
		TITOLOINTERESSEUNITANEGOZIALE = tITOLOINTERESSEUNITANEGOZIALE;
	}

	public String getDESCRINTERESSEUNITANEGOZIALE() {
		return DESCRINTERESSEUNITANEGOZIALE;
	}

	public void setDESCRINTERESSEUNITANEGOZIALE(String dESCRINTERESSEUNITANEGOZIALE) {
		DESCRINTERESSEUNITANEGOZIALE = dESCRINTERESSEUNITANEGOZIALE;
	}

	public Character getIDREGIMEUNITANEGOZIALE() {
		return IDREGIMEUNITANEGOZIALE;
	}

	public void setIDREGIMEUNITANEGOZIALE(Character iDREGIMEUNITANEGOZIALE) {
		IDREGIMEUNITANEGOZIALE = iDREGIMEUNITANEGOZIALE;
	}

	public BigDecimal getREGIMECONIUGIUNITANEGOZIALE() {
		return REGIMECONIUGIUNITANEGOZIALE;
	}

	public void setREGIMECONIUGIUNITANEGOZIALE(
			BigDecimal rEGIMECONIUGIUNITANEGOZIALE) {
		REGIMECONIUGIUNITANEGOZIALE = rEGIMECONIUGIUNITANEGOZIALE;
	}

    public String getDescSoggCon()
    {
        return descSoggCon;
    }

    public void setDescSoggCon( String descSoggCon )
    {
        this.descSoggCon = descSoggCon;
    }

    
    
}
