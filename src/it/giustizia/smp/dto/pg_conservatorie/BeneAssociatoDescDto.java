package it.giustizia.smp.dto.pg_conservatorie;

import java.math.BigDecimal;


public class BeneAssociatoDescDto
{

	private BigDecimal	PROGRESSIVO;     /* CODI_K_PROGR_BENE ;  	*/    
	private BigDecimal 	PROGUNEG;        /* CODI_FK_PROGR_UNEG;   	*/     
	private Long 		progUnegBene;    /* NUME_PROGR_UNEG_BENE;  	*/    
	private String 	    catasto;         /* DESC_CATASTO;    	 	*/     
	private String 	    comune;          /* DESC_SEDE_IMMOBILE;  	*/      
	private Long 		beneSMP;         /* CODI_FK_ID_BENE;      	*/     
	private Character	flagErrore;      /* FLG_ERR;              	*/     
	private String   	descrizione;     /* DESCR_BENE; 			*/
	
	// Attributi per esportazione dei dati

	private String     	provincia;	     /* */
    private Character   codTipoCatasto;  /* CODI_FK_ID_TIPO_CAT     */
    private Character   codTipoCatastoP;  /* CODI_FK_ID_TIPO_CAT_P     */
	private String     	descSezione;	 /* DESC_SEZIONE			*/
	private String      descFoglio;		 /* DESC_FOGLIO				*/
	private String      descParticella1; /* DESC_PARTICELLA_1		*/
	private String      descParticella2; /* DESC_PARTICELLA_2		*/
	private String      descSubalterno1; /* DESC_SUBALTERNO_1		*/
	private String      descSubalterno2; /* DESC_SUBALTERNO_2		*/
	private Character   codiFkIdFondo;   /* CODI_FK_ID_FONDO		*/
	
	private String     descFoglioP;      /* DESC_FOGLIO_P			*/
	private String     descParticella1P; /* DESC_PARTICELLA_1_P		*/
	
	private String     descCatastoP;	 /* DESC_CATASTO_P			*/
	
	private Character   descTipoDenuncia;/* DESC_TIPO_DENUNCIA		*/
	private BigDecimal  numeDenuncia1;   /* NUME_DENUNCIA_1			*/
	private BigDecimal  numeDenuncia2;   /* NUME_DENUNCIA_2			*/
	private BigDecimal  numeAnnoDenuncia;/* NUME_ANNO_DENUNCIA		*/
	
	private BigDecimal  numeGraffati;    /*NUME_GRAFFATI			*/
	
	private BigDecimal  numeVaniInt; 	 /* NUME_VANI_INT			*/
	private BigDecimal  numeVaniDec; 	 /* NUME_VANI_DEC			*/
	private BigDecimal  numeMcInt; 		 /* NUME_MC_INT				*/
	private BigDecimal  numeMcDec; 		 /* NUME_MC_DEC				*/
	private BigDecimal  numeSupMqInt;    /* NUME_SUP_MQ_INT			*/
	private BigDecimal  numeSupMqDec; 	 /* NUME_SUP_MQ_DEC			*/
	
	private BigDecimal numeSupEttari;	 /* NUME_SUP_ETTARI			*/
	private BigDecimal numeSupAre;		 /* NUME_SUP_ARE			*/
	private BigDecimal numeSupCentiare;  /* NUME_SUP_CENTIARE		*/
	
	private BigDecimal numeCapitaleInt;  /* NUME_CAPITALE_INT		*/
	private BigDecimal numeCapitaleDec;  /* NUME_CAPITALE_DEC		*/
	private String     descIndirizzo;	 /* DESC_INDIRIZZO  		*/
	private String     descCivico;		 /* DESC_CIVICO				*/
	private String     descLotto;		 /* DESC_LOTTO			    */
	private String     descEdificio;	 /* DESC_EDIFICIO			*/
	private String     descScala;		 /* DESC_SCALA				*/
	private String     descInterno;		 /* DESC_INTERNO			*/
	private String     descPiano;		 /* DESC_PIANO				*/
	
	private String     tipoTerreno;		/* T_BENE. CODI_FK_ID_SCTG_BENE */
	private Long 	   idSoggetto;    	 /* CODI_K_PROGR_SOGG;  	*/
	private String 	   descrBreveCat;   /* T_TP_CAT_CATASTO.DESC_BREVE_CATEGORIA_CAT */
	
    private String stileDisplay = "";


	public Long getProgUnegBene() {
		return progUnegBene;
	}
	public void setProgUnegBene(Long progUnegBene) {
		this.progUnegBene = progUnegBene;
	}
	public String getCatasto() {
		return catasto;
	}
	public void setCatasto(String catasto) {
		this.catasto = catasto;
	}
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
	}
	public Long getBeneSMP() {
		return beneSMP;
	}
	public void setBeneSMP(Long beneSMP) {
		this.beneSMP = beneSMP;
	}
	public Character getFlagErrore() {
		return flagErrore;
	}
	public void setFlagErrore(Character flagErrore) {
		this.flagErrore = flagErrore;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public Character getCodTipoCatasto() {
		return codTipoCatasto;
	}
	public void setCodTipoCatasto(Character codTipoCatasto) {
		this.codTipoCatasto = codTipoCatasto;
	}
	public String getDescSezione() {
		return descSezione;
	}
	public void setDescSezione(String descSezione) {
		this.descSezione = descSezione;
	}
	public String getDescFoglio() {
		return descFoglio;
	}
	public void setDescFoglio(String descFoglio) {
		this.descFoglio = descFoglio;
	}
	public String getDescParticella1() {
		return descParticella1;
	}
	public void setDescParticella1(String descParticella1) {
		this.descParticella1 = descParticella1;
	}
	public String getDescParticella2() {
		return descParticella2;
	}
	public void setDescParticella2(String descParticella2) {
		this.descParticella2 = descParticella2;
	}
	public String getDescSubalterno1() {
		return descSubalterno1;
	}
	public void setDescSubalterno1(String descSubalterno1) {
		this.descSubalterno1 = descSubalterno1;
	}
	public String getDescSubalterno2() {
		return descSubalterno2;
	}
	public void setDescSubalterno2(String descSubalterno2) {
		this.descSubalterno2 = descSubalterno2;
	}
	public Character getDescTipoDenuncia() {
		return descTipoDenuncia;
	}
	public void setDescTipoDenuncia(Character descTipoDenuncia) {
		this.descTipoDenuncia = descTipoDenuncia;
	}
	public BigDecimal getNumeDenuncia1() {
		return numeDenuncia1;
	}
	public void setNumeDenuncia1(BigDecimal numeDenuncia1) {
		this.numeDenuncia1 = numeDenuncia1;
	}
	public BigDecimal getNumeDenuncia2() {
		return numeDenuncia2;
	}
	public void setNumeDenuncia2(BigDecimal numeDenuncia2) {
		this.numeDenuncia2 = numeDenuncia2;
	}
	public BigDecimal getNumeAnnoDenuncia() {
		return numeAnnoDenuncia;
	}
	public void setNumeAnnoDenuncia(BigDecimal numeAnnoDenuncia) {
		this.numeAnnoDenuncia = numeAnnoDenuncia;
	}
	public BigDecimal getNumeGraffati() {
		return numeGraffati;
	}
	public void setNumeGraffati(BigDecimal numeGraffati) {
		this.numeGraffati = numeGraffati;
	}
	public BigDecimal getNumeVaniInt() {
		return numeVaniInt;
	}
	public void setNumeVaniInt(BigDecimal numeVaniInt) {
		this.numeVaniInt = numeVaniInt;
	}
	public BigDecimal getNumeVaniDec() {
		return numeVaniDec;
	}
	public void setNumeVaniDec(BigDecimal numeVaniDec) {
		this.numeVaniDec = numeVaniDec;
	}
	public BigDecimal getNumeMcInt() {
		return numeMcInt;
	}
	public void setNumeMcInt(BigDecimal numeMcInt) {
		this.numeMcInt = numeMcInt;
	}
	public BigDecimal getNumeMcDec() {
		return numeMcDec;
	}
	public void setNumeMcDec(BigDecimal numeMcDec) {
		this.numeMcDec = numeMcDec;
	}
	public BigDecimal getNumeSupMqInt() {
		return numeSupMqInt;
	}
	public void setNumeSupMqInt(BigDecimal numeSupMqInt) {
		this.numeSupMqInt = numeSupMqInt;
	}
	public BigDecimal getNumeSupMqDec() {
		return numeSupMqDec;
	}
	public void setNumeSupMqDec(BigDecimal numeSupMqDec) {
		this.numeSupMqDec = numeSupMqDec;
	}
	public Long getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public String getDescrBreveCat() {
		return descrBreveCat;
	}
	public void setDescrBreveCat(String descrBreveCat) {
		this.descrBreveCat = descrBreveCat;
	}
	public BigDecimal getNumeSupEttari() {
		return numeSupEttari;
	}
	public void setNumeSupEttari(BigDecimal numeSupEttari) {
		this.numeSupEttari = numeSupEttari;
	}
	public BigDecimal getNumeSupAre() {
		return numeSupAre;
	}
	public void setNumeSupAre(BigDecimal numeSupAre) {
		this.numeSupAre = numeSupAre;
	}
	public BigDecimal getNumeSupCentiare() {
		return numeSupCentiare;
	}
	public void setNumeSupCentiare(BigDecimal numeSupCentiare) {
		this.numeSupCentiare = numeSupCentiare;
	}
	public BigDecimal getNumeCapitaleInt() {
		return numeCapitaleInt;
	}
	public void setNumeCapitaleInt(BigDecimal numeCapitaleInt) {
		this.numeCapitaleInt = numeCapitaleInt;
	}
	public BigDecimal getNumeCapitaleDec() {
		return numeCapitaleDec;
	}
	public void setNumeCapitaleDec(BigDecimal numeCapitaleDec) {
		this.numeCapitaleDec = numeCapitaleDec;
	}
	public String getDescIndirizzo() {
		return descIndirizzo;
	}
	public void setDescIndirizzo(String descIndirizzo) {
		this.descIndirizzo = descIndirizzo;
	}
	public String getDescCivico() {
		return descCivico;
	}
	public void setDescCivico(String descCivico) {
		this.descCivico = descCivico;
	}
	public String getDescLotto() {
		return descLotto;
	}
	public void setDescLotto(String descLotto) {
		this.descLotto = descLotto;
	}
	public String getDescEdificio() {
		return descEdificio;
	}
	public void setDescEdificio(String descEdificio) {
		this.descEdificio = descEdificio;
	}
	public String getDescScala() {
		return descScala;
	}
	public void setDescScala(String descScala) {
		this.descScala = descScala;
	}
	public String getDescInterno() {
		return descInterno;
	}
	public void setDescInterno(String descInterno) {
		this.descInterno = descInterno;
	}
	public String getDescPiano() {
		return descPiano;
	}
	public void setDescPiano(String descPiano) {
		this.descPiano = descPiano;
	}
	public String getTipoTerreno() {
		return tipoTerreno;
	}
	public void setTipoTerreno(String tipoTerreno) {
		this.tipoTerreno = tipoTerreno;
	}
	public String getDescParticella1P() {
		return descParticella1P;
	}
	public void setDescParticella1P(String descParticella1P) {
		this.descParticella1P = descParticella1P;
	}
	public String getDescFoglioP() {
		return descFoglioP;
	}
	public void setDescFoglioP(String descFoglioP) {
		this.descFoglioP = descFoglioP;
	}
	public String getDescCatastoP() {
		return descCatastoP;
	}
	public void setDescCatastoP(String descCatastoP) {
		this.descCatastoP = descCatastoP;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public Character getCodiFkIdFondo() {
		return codiFkIdFondo;
	}
	public void setCodiFkIdFondo(Character codiFkIdFondo) {
		this.codiFkIdFondo = codiFkIdFondo;
	}
    public BigDecimal getPROGRESSIVO()
    {
        return PROGRESSIVO;
    }
    public void setPROGRESSIVO( BigDecimal pROGRESSIVO )
    {
        PROGRESSIVO = pROGRESSIVO;
    }
    public BigDecimal getPROGUNEG()
    {
        return PROGUNEG;
    }
    public void setPROGUNEG( BigDecimal pROGUNEG )
    {
        PROGUNEG = pROGUNEG;
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
    public Character getCodTipoCatastoP()
    {
        return codTipoCatastoP;
    }
    public void setCodTipoCatastoP( Character codTipoCatastoP )
    {
        this.codTipoCatastoP = codTipoCatastoP;
    }
    
}

