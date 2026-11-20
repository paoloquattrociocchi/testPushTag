package it.giustizia.smp.dto.pg_conservatorie;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class SoggettoInteressatoDescDto
{
    private String      descSoggCon; 

    // ATTRIBUTI Soggetto Generico

    private Long      idSoggetto;      /* T_SOGGETTO.CODI_K_ID_SOGG_INT */
    private Integer   versione;        /* T_REL_PROC_SOGINT.CODI_FK_ID_VER */
    private Long      progressivo;     /* T_NOTA_SOGG.CODI_K_PROGR_SOGG */
    private Character tipologia;       /* T_NOTA_SOGG.FLG_TIPO_SOGG */
    private String    soggetto;        /* cognome + nome + "nato a" + comune nascita + "il" + data nascita  oppure  denominazione + 'a' + comune sede */
    private Long      soggettoSitMp;   /* T_NOTA_SOGG.CODI_FK_ID_SOGG_INT   */
    private Character flagErrore;      /* FLG_ERR; */

    // ATTRIBUTI Soggetto Fisico
    private String    cognome;
    private String    nome;
    private Character flgSesso;         ;
    @DateTimeFormat( pattern = "dd/MM/yyyy" )
    private Date      dataNascita;     //dataNascita
    private Date      dataMorte;       //dataMorte
    private String    comuneNascita;
    private String    provNascita;
    private String    statoNascita;
    private String    codiceFiscalePF;
    private Character regime;
    private String    descRegime;
    private Integer   codProgrSoggC;
    private String    codiceComuneNasc;

    // ATTRIBUTI Soggetto Giuridico
    private String    denominazione;
    private String    statoSede;
    private String    comuneSede;
    private String    provSede;
    private String    partitaIva;
    private String    codiceComuneSede;
    
    // ATTRIBUTI Soggetto Unit‡Negoziali
    private Long      progSoggetto;       			/* T_NOTA_SOGG_UN.CODI_K_PROGR_SOGG */
    private Long      progUnitaNegoziale; 			/* T_NOTA_SOGG_UN.CODI_K_PROGR_UNEG */
    private Character qualificaUnitaNegoziale;		/* T_NOTA_SOGG_UN.CODI_K_TIPO_RECORD */
    private Long	  numeQuotaIntUnitaNegoziale;	/* T_NOTA_SOGG_UN.NUME_QUOTA_NUM_INT */
    private Long	  numeQuotaDecUnitaNegoziale;	/* T_NOTA_SOGG_UN.NUME_QUOTA_NUM_DEC */
    private Long	  numeQuotaDenomUnitaNegoziale;	/* T_NOTA_SOGG_UN.NUME_QUOTA_DEN */
    private Long	  titoloInteresseUnitaNegoziale;/* T_NOTA_SOGG_UN.CODI_K_ID_TIPO_INT */
    private String	  descrInteresseUnitaNegoziale;	/* T_NOTA_SOGG_UN.DESC_TIPO_INT */
    private Character idregimeUnitaNegoziale;  		/* T_NOTA_SOGG_UN.CODI_FK_ID_TIPO_REGIME */
    private Long	  regimeConiugiUnitaNegoziale;	/* T_NOTA_SOGG_UN.CODI_FK_PROGR_SOGG_C */
   
    private String stileDisplay = "";

   
    public SoggettoInteressatoDescDto()
    {}


	public Long getIdSoggetto() {
		return idSoggetto;
	}


	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}


	public Integer getVersione() {
		return versione;
	}


	public void setVersione(Integer versione) {
		this.versione = versione;
	}


	public Long getProgressivo() {
		return progressivo;
	}


	public void setProgressivo(Long progressivo) {
		this.progressivo = progressivo;
	}


	public Character getTipologia() {
		return tipologia;
	}

	public void setTipologia(Character tipologia) {
		this.tipologia = tipologia;
	}

    public String getDescTipologia()
    {
        if( this.tipologia.equals( 'F' ) )
            return "Persona fisica";
        else
            if( this.tipologia.equals( 'N' ) || this.tipologia.equals( 'G' ) )
                return "Persona giuridica";
            else
                return "";
    }

    public String getSoggetto() {
		return soggetto;
	}


	public void setSoggetto(String soggetto) {
		this.soggetto = soggetto;
	}


	public Long getSoggettoSitMp() {
		return soggettoSitMp;
	}


	public void setSoggettoSitMp(Long soggettoSitMp) {
		this.soggettoSitMp = soggettoSitMp;
	}


	public Character getFlagErrore() {
		return flagErrore;
	}


	public void setFlagErrore(Character flagErrore) {
		this.flagErrore = flagErrore;
	}


	public String getCognome() {
		return cognome;
	}


	public void setCognome(String cognome) {
		this.cognome = cognome;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public Character getFlgSesso() {
		return flgSesso;
	}


	public void setFlgSesso(Character flgSesso) {
		this.flgSesso = flgSesso;
	}


	public Date getDataNascita() {
		return dataNascita;
	}


	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}


	public Date getDataMorte() {
		return dataMorte;
	}


	public void setDataMorte(Date dataMorte) {
		this.dataMorte = dataMorte;
	}


	public String getComuneNascita() {
		return comuneNascita;
	}


	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}


	public String getProvNascita() {
		return provNascita;
	}


	public void setProvNascita(String provNascita) {
		this.provNascita = provNascita;
	}


	public String getStatoNascita() {
		return statoNascita;
	}


	public void setStatoNascita(String statoNascita) {
		this.statoNascita = statoNascita;
	}


	public String getCodiceFiscalePF() {
		return codiceFiscalePF;
	}


	public void setCodiceFiscalePF(String codiceFiscalePF) {
		this.codiceFiscalePF = codiceFiscalePF;
	}


	public Character getRegime() {
		return regime;
	}


	public void setRegime(Character regime) {
		this.regime = regime;
	}


	public String getDescRegime() {
		return descRegime;
	}


	public void setDescRegime(String descRegime) {
		this.descRegime = descRegime;
	}


	public Integer getCodProgrSoggC() {
		return codProgrSoggC;
	}


	public void setCodProgrSoggC(Integer codProgrSoggC) {
		this.codProgrSoggC = codProgrSoggC;
	}


	public String getCodiceComuneNasc() {
		return codiceComuneNasc;
	}


	public void setCodiceComuneNasc(String codiceComuneNasc) {
		this.codiceComuneNasc = codiceComuneNasc;
	}


	public String getDenominazione() {
		return denominazione;
	}


	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}


	public String getStatoSede() {
		return statoSede;
	}


	public void setStatoSede(String statoSede) {
		this.statoSede = statoSede;
	}


	public String getComuneSede() {
		return comuneSede;
	}


	public void setComuneSede(String comuneSede) {
		this.comuneSede = comuneSede;
	}


	public String getProvSede() {
		return provSede;
	}


	public void setProvSede(String provSede) {
		this.provSede = provSede;
	}


	public String getPartitaIva() {
		return partitaIva;
	}


	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}


	public String getCodiceComuneSede() {
		return codiceComuneSede;
	}


	public void setCodiceComuneSede(String codiceComuneSede) {
		this.codiceComuneSede = codiceComuneSede;
	}


	public Long getProgSoggetto() {
		return progSoggetto;
	}


	public void setProgSoggetto(Long progSoggetto) {
		this.progSoggetto = progSoggetto;
	}


	public Long getProgUnitaNegoziale() {
		return progUnitaNegoziale;
	}


	public void setProgUnitaNegoziale(Long progUnitaNegoziale) {
		this.progUnitaNegoziale = progUnitaNegoziale;
	}


	public Character getQualificaUnitaNegoziale() {
		return qualificaUnitaNegoziale;
	}


	public void setQualificaUnitaNegoziale(Character qualificaUnitaNegoziale) {
		this.qualificaUnitaNegoziale = qualificaUnitaNegoziale;
	}


	public Long getNumeQuotaIntUnitaNegoziale() {
		return numeQuotaIntUnitaNegoziale;
	}


	public void setNumeQuotaIntUnitaNegoziale(Long numeQuotaIntUnitaNegoziale) {
		this.numeQuotaIntUnitaNegoziale = numeQuotaIntUnitaNegoziale;
	}


	public Long getNumeQuotaDecUnitaNegoziale() {
		return numeQuotaDecUnitaNegoziale;
	}


	public void setNumeQuotaDecUnitaNegoziale(Long numeQuotaDecUnitaNegoziale) {
		this.numeQuotaDecUnitaNegoziale = numeQuotaDecUnitaNegoziale;
	}


	public Long getNumeQuotaDenomUnitaNegoziale() {
		return numeQuotaDenomUnitaNegoziale;
	}


	public void setNumeQuotaDenomUnitaNegoziale(Long numeQuotaDenomUnitaNegoziale) {
		this.numeQuotaDenomUnitaNegoziale = numeQuotaDenomUnitaNegoziale;
	}


	public Long getTitoloInteresseUnitaNegoziale() {
		return titoloInteresseUnitaNegoziale;
	}


	public void setTitoloInteresseUnitaNegoziale(Long titoloInteresseUnitaNegoziale) {
		this.titoloInteresseUnitaNegoziale = titoloInteresseUnitaNegoziale;
	}


	public String getDescrInteresseUnitaNegoziale() {
		return descrInteresseUnitaNegoziale;
	}


	public void setDescrInteresseUnitaNegoziale(String descrInteresseUnitaNegoziale) {
		this.descrInteresseUnitaNegoziale = descrInteresseUnitaNegoziale;
	}


	public Character getIdregimeUnitaNegoziale() {
		return idregimeUnitaNegoziale;
	}


	public void setIdregimeUnitaNegoziale(Character idregimeUnitaNegoziale) {
		this.idregimeUnitaNegoziale = idregimeUnitaNegoziale;
	}


	public Long getRegimeConiugiUnitaNegoziale() {
		return regimeConiugiUnitaNegoziale;
	}


	public void setRegimeConiugiUnitaNegoziale(Long regimeConiugiUnitaNegoziale) {
		this.regimeConiugiUnitaNegoziale = regimeConiugiUnitaNegoziale;
	}


    public String getStileDisplay() {
        if (! this.flagErrore.equals('0')){
               stileDisplay = "testoRosso";
        }
        return stileDisplay;
  }

	public void setStileDisplay(String stileDisplay) {
		this.stileDisplay = stileDisplay;
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
