package it.giustizia.smp.dto.pg_conservatorie;

import java.math.BigDecimal;
import java.util.Date;

public class BeneDaAssociareDto {
	
	private String     DESC_COMUNE 				;	//T_AN_COMUNI.DESC_COMUNE
	private String     DESC_TIPO_STRADA			;	//T_TP_TIPO_STRADA.DESC_TIPO_STRADA
	private String     DESC_INDIRIZZO			;	//T_BENE.DESC_INDIRIZZO
	private String     DESC_CIVICO				;	//T_BENE.DESC_CIVICO
	private BigDecimal CODI_K_ID_BENE_IMM		;	// da qui partono i campi della tabella T_BENE_IMMOBILE
	private BigDecimal CODI_K_ID_UFF_OWNER		;
	private String     DESC_SCALA				;
	private String     DESC_INTERNO				;
	private String     DESC_PIANO				;
	private Character  CODI_FK_ID_TIPO_CAT		;
	private BigDecimal CODI_FK_ID_COMUNE_CAT	;
	private String     DESC_SEZIONE				;
	private String     DESC_FOGLIO				;
	private String     DESC_PARTICELLA_1		;
	private String     DESC_PARTICELLA_2		;
	private String     DESC_SUB_PARTICELLA_1	;
	private String     DESC_SUB_PARTICELLA_2	;
	private String     DESC_SCHEDA_ACCATAST		;
    private Date       DATA_DOMANDA_ACCATAST	;
	private String     DESC_ZONA				;
	private BigDecimal IMPO_RENDITA_CAT			;
	private BigDecimal IMPO_RENDITA_DOM			;
	private Character  FLG_PRES_CONC_EDIL		;
	private Character  FLG_APP_FAB_CONF			;
	private BigDecimal CODI_FK_ID_CATEGORIA_CAT	;
	private String     DESC_CATASTO				;
	private String     DESC_CATASTO_COMUNE		;
	private String     DESC_CATASTO_P			;
	private BigDecimal CODI_FK_ID_COMUNE_CAT_P	;
	private String     DESC_COMUNE_P 				;	//T_AN_COMUNI.DESC_COMUNE
	private String     DESC_SEZIONE_P			;
	private Character  CODI_FK_ID_TIPO_CAT_P	;
	private String     DESC_FOGLIO_P			;
	private String     DESC_PARTICELLA_1_P		;
	private String     DESC_PARTICELLA_2_P		;
	private String     DESC_SUBALTERNO_1_P		;
	private String     DESC_SUBALTERNO_2_P		;
	private Character  CODI_FK_ID_TIPO_DENUNCIA	;
	private BigDecimal NUME_DENUNCIA_1			;
	private BigDecimal NUME_DENUNCIA_2			;
	private BigDecimal NUME_ANNO_DENUNCIA		;
	private Character  CODI_FK_ID_TIPO_DENUNCIA_P;
	private BigDecimal NUME_DENUNCIA_1_P		;
	private BigDecimal NUME_DENUNCIA_2_P		;
	private BigDecimal NUME_ANNO_DENUNCIA_P		;
	private BigDecimal NUME_GRAFFATI			;
	private Character  CODI_FK_ID_FONDO			;
	private BigDecimal NUME_SUP_ETTARI			;
	private BigDecimal NUME_SUP_ARE				;
	private BigDecimal NUME_SUP_CENTIARE		;
	private BigDecimal NUME_SUP_MQ_INT			;
	private BigDecimal NUME_SUP_MQ_DEC			;
	private BigDecimal NUME_VANI_INT			;
	private BigDecimal NUME_VANI_DEC			;
	private BigDecimal NUME_MC_INT				;
	private BigDecimal NUME_MC_DEC				;
	private String     DESC_EDIFICIO			;
	private String     DESC_LOTTO				;
	private String     DESC_UTENTE				;
    private Date       DATA_ULT_AGG				;
    
	public String getDESC_COMUNE() {
		return DESC_COMUNE;
	}
	public void setDESC_COMUNE(String dESC_COMUNE) {
		DESC_COMUNE = dESC_COMUNE;
	}
	public String getDESC_TIPO_STRADA() {
		return DESC_TIPO_STRADA;
	}
	public void setDESC_TIPO_STRADA(String dESC_TIPO_STRADA) {
		DESC_TIPO_STRADA = dESC_TIPO_STRADA;
	}
	public String getDESC_INDIRIZZO() {
		return DESC_INDIRIZZO;
	}
	public void setDESC_INDIRIZZO(String dESC_INDIRIZZO) {
		DESC_INDIRIZZO = dESC_INDIRIZZO;
	}
	public String getDESC_CIVICO() {
		return DESC_CIVICO;
	}
	public void setDESC_CIVICO(String dESC_CIVICO) {
		DESC_CIVICO = dESC_CIVICO;
	}
	public BigDecimal getCODI_K_ID_BENE_IMM() {
		return CODI_K_ID_BENE_IMM;
	}
	public void setCODI_K_ID_BENE_IMM(BigDecimal cODI_K_ID_BENE_IMM) {
		CODI_K_ID_BENE_IMM = cODI_K_ID_BENE_IMM;
	}
	public BigDecimal getCODI_K_ID_UFF_OWNER() {
		return CODI_K_ID_UFF_OWNER;
	}
	public void setCODI_K_ID_UFF_OWNER(BigDecimal cODI_K_ID_UFF_OWNER) {
		CODI_K_ID_UFF_OWNER = cODI_K_ID_UFF_OWNER;
	}
	public String getDESC_SCALA() {
		return DESC_SCALA;
	}
	public void setDESC_SCALA(String dESC_SCALA) {
		DESC_SCALA = dESC_SCALA;
	}
	public String getDESC_INTERNO() {
		return DESC_INTERNO;
	}
	public void setDESC_INTERNO(String dESC_INTERNO) {
		DESC_INTERNO = dESC_INTERNO;
	}
	public String getDESC_PIANO() {
		return DESC_PIANO;
	}
	public void setDESC_PIANO(String dESC_PIANO) {
		DESC_PIANO = dESC_PIANO;
	}
	public Character getCODI_FK_ID_TIPO_CAT() {
		return CODI_FK_ID_TIPO_CAT;
	}
	public void setCODI_FK_ID_TIPO_CAT(Character cODI_FK_ID_TIPO_CAT) {
		CODI_FK_ID_TIPO_CAT = cODI_FK_ID_TIPO_CAT;
	}
	public BigDecimal getCODI_FK_ID_COMUNE_CAT() {
		return CODI_FK_ID_COMUNE_CAT;
	}
	public void setCODI_FK_ID_COMUNE_CAT(BigDecimal cODI_FK_ID_COMUNE_CAT) {
		CODI_FK_ID_COMUNE_CAT = cODI_FK_ID_COMUNE_CAT;
	}
	public String getDESC_SEZIONE() {
		return DESC_SEZIONE;
	}
	public void setDESC_SEZIONE(String dESC_SEZIONE) {
		DESC_SEZIONE = dESC_SEZIONE;
	}
	public String getDESC_FOGLIO() {
		return DESC_FOGLIO;
	}
	public void setDESC_FOGLIO(String dESC_FOGLIO) {
		DESC_FOGLIO = dESC_FOGLIO;
	}
	public String getDESC_PARTICELLA_1() {
		return DESC_PARTICELLA_1;
	}
	public void setDESC_PARTICELLA_1(String dESC_PARTICELLA_1) {
		DESC_PARTICELLA_1 = dESC_PARTICELLA_1;
	}
	public String getDESC_PARTICELLA_2() {
		return DESC_PARTICELLA_2;
	}
	public void setDESC_PARTICELLA_2(String dESC_PARTICELLA_2) {
		DESC_PARTICELLA_2 = dESC_PARTICELLA_2;
	}
	public String getDESC_SUB_PARTICELLA_1() {
		return DESC_SUB_PARTICELLA_1;
	}
	public void setDESC_SUB_PARTICELLA_1(String dESC_SUB_PARTICELLA_1) {
		DESC_SUB_PARTICELLA_1 = dESC_SUB_PARTICELLA_1;
	}
	public String getDESC_SUB_PARTICELLA_2() {
		return DESC_SUB_PARTICELLA_2;
	}
	public void setDESC_SUB_PARTICELLA_2(String dESC_SUB_PARTICELLA_2) {
		DESC_SUB_PARTICELLA_2 = dESC_SUB_PARTICELLA_2;
	}
	public String getDESC_SCHEDA_ACCATAST() {
		return DESC_SCHEDA_ACCATAST;
	}
	public void setDESC_SCHEDA_ACCATAST(String dESC_SCHEDA_ACCATAST) {
		DESC_SCHEDA_ACCATAST = dESC_SCHEDA_ACCATAST;
	}
	public Date getDATA_DOMANDA_ACCATAST() {
		return DATA_DOMANDA_ACCATAST;
	}
	public void setDATA_DOMANDA_ACCATAST(Date dATA_DOMANDA_ACCATAST) {
		DATA_DOMANDA_ACCATAST = dATA_DOMANDA_ACCATAST;
	}
	public String getDESC_ZONA() {
		return DESC_ZONA;
	}
	public void setDESC_ZONA(String dESC_ZONA) {
		DESC_ZONA = dESC_ZONA;
	}
	public BigDecimal getIMPO_RENDITA_CAT() {
		return IMPO_RENDITA_CAT;
	}
	public void setIMPO_RENDITA_CAT(BigDecimal iMPO_RENDITA_CAT) {
		IMPO_RENDITA_CAT = iMPO_RENDITA_CAT;
	}
	public BigDecimal getIMPO_RENDITA_DOM() {
		return IMPO_RENDITA_DOM;
	}
	public void setIMPO_RENDITA_DOM(BigDecimal iMPO_RENDITA_DOM) {
		IMPO_RENDITA_DOM = iMPO_RENDITA_DOM;
	}
	public Character getFLG_PRES_CONC_EDIL() {
		return FLG_PRES_CONC_EDIL;
	}
	public void setFLG_PRES_CONC_EDIL(Character fLG_PRES_CONC_EDIL) {
		FLG_PRES_CONC_EDIL = fLG_PRES_CONC_EDIL;
	}
	public Character getFLG_APP_FAB_CONF() {
		return FLG_APP_FAB_CONF;
	}
	public void setFLG_APP_FAB_CONF(Character fLG_APP_FAB_CONF) {
		FLG_APP_FAB_CONF = fLG_APP_FAB_CONF;
	}
	public BigDecimal getCODI_FK_ID_CATEGORIA_CAT() {
		return CODI_FK_ID_CATEGORIA_CAT;
	}
	public void setCODI_FK_ID_CATEGORIA_CAT(BigDecimal cODI_FK_ID_CATEGORIA_CAT) {
		CODI_FK_ID_CATEGORIA_CAT = cODI_FK_ID_CATEGORIA_CAT;
	}
	public String getDESC_CATASTO() {
		return DESC_CATASTO;
	}
	public void setDESC_CATASTO(String dESC_CATASTO) {
		DESC_CATASTO = dESC_CATASTO;
	}
	public String getDESC_CATASTO_COMUNE() {
		return DESC_CATASTO_COMUNE;
	}
	public void setDESC_CATASTO_COMUNE(String dESC_CATASTO_COMUNE) {
		DESC_CATASTO_COMUNE = dESC_CATASTO_COMUNE;
	}
	public String getDESC_CATASTO_P() {
		return DESC_CATASTO_P;
	}
	public void setDESC_CATASTO_P(String dESC_CATASTO_P) {
		DESC_CATASTO_P = dESC_CATASTO_P;
	}
	public BigDecimal getCODI_FK_ID_COMUNE_CAT_P() {
		return CODI_FK_ID_COMUNE_CAT_P;
	}
	public void setCODI_FK_ID_COMUNE_CAT_P(BigDecimal cODI_FK_ID_COMUNE_CAT_P) {
		CODI_FK_ID_COMUNE_CAT_P = cODI_FK_ID_COMUNE_CAT_P;
	}
	public String getDESC_COMUNE_P() {
		return DESC_COMUNE_P;
	}
	public void setDESC_COMUNE_P(String dESC_COMUNE_P) {
		DESC_COMUNE_P = dESC_COMUNE_P;
	}
	public String getDESC_SEZIONE_P() {
		return DESC_SEZIONE_P;
	}
	public void setDESC_SEZIONE_P(String dESC_SEZIONE_P) {
		DESC_SEZIONE_P = dESC_SEZIONE_P;
	}
	public Character getCODI_FK_ID_TIPO_CAT_P() {
		return CODI_FK_ID_TIPO_CAT_P;
	}
	public void setCODI_FK_ID_TIPO_CAT_P(Character cODI_FK_ID_TIPO_CAT_P) {
		CODI_FK_ID_TIPO_CAT_P = cODI_FK_ID_TIPO_CAT_P;
	}
	public String getDESC_FOGLIO_P() {
		return DESC_FOGLIO_P;
	}
	public void setDESC_FOGLIO_P(String dESC_FOGLIO_P) {
		DESC_FOGLIO_P = dESC_FOGLIO_P;
	}
	public String getDESC_PARTICELLA_1_P() {
		return DESC_PARTICELLA_1_P;
	}
	public void setDESC_PARTICELLA_1_P(String dESC_PARTICELLA_1_P) {
		DESC_PARTICELLA_1_P = dESC_PARTICELLA_1_P;
	}
	public String getDESC_PARTICELLA_2_P() {
		return DESC_PARTICELLA_2_P;
	}
	public void setDESC_PARTICELLA_2_P(String dESC_PARTICELLA_2_P) {
		DESC_PARTICELLA_2_P = dESC_PARTICELLA_2_P;
	}
	public String getDESC_SUBALTERNO_1_P() {
		return DESC_SUBALTERNO_1_P;
	}
	public void setDESC_SUBALTERNO_1_P(String dESC_SUBALTERNO_1_P) {
		DESC_SUBALTERNO_1_P = dESC_SUBALTERNO_1_P;
	}
	public String getDESC_SUBALTERNO_2_P() {
		return DESC_SUBALTERNO_2_P;
	}
	public void setDESC_SUBALTERNO_2_P(String dESC_SUBALTERNO_2_P) {
		DESC_SUBALTERNO_2_P = dESC_SUBALTERNO_2_P;
	}
	public Character getCODI_FK_ID_TIPO_DENUNCIA() {
		return CODI_FK_ID_TIPO_DENUNCIA;
	}
	public void setCODI_FK_ID_TIPO_DENUNCIA(Character cODI_FK_ID_TIPO_DENUNCIA) {
		CODI_FK_ID_TIPO_DENUNCIA = cODI_FK_ID_TIPO_DENUNCIA;
	}
	public BigDecimal getNUME_DENUNCIA_1() {
		return NUME_DENUNCIA_1;
	}
	public void setNUME_DENUNCIA_1(BigDecimal nUME_DENUNCIA_1) {
		NUME_DENUNCIA_1 = nUME_DENUNCIA_1;
	}
	public BigDecimal getNUME_DENUNCIA_2() {
		return NUME_DENUNCIA_2;
	}
	public void setNUME_DENUNCIA_2(BigDecimal nUME_DENUNCIA_2) {
		NUME_DENUNCIA_2 = nUME_DENUNCIA_2;
	}
	public BigDecimal getNUME_ANNO_DENUNCIA() {
		return NUME_ANNO_DENUNCIA;
	}
	public void setNUME_ANNO_DENUNCIA(BigDecimal nUME_ANNO_DENUNCIA) {
		NUME_ANNO_DENUNCIA = nUME_ANNO_DENUNCIA;
	}
	public Character getCODI_FK_ID_TIPO_DENUNCIA_P() {
		return CODI_FK_ID_TIPO_DENUNCIA_P;
	}
	public void setCODI_FK_ID_TIPO_DENUNCIA_P(Character cODI_FK_ID_TIPO_DENUNCIA_P) {
		CODI_FK_ID_TIPO_DENUNCIA_P = cODI_FK_ID_TIPO_DENUNCIA_P;
	}
	public BigDecimal getNUME_DENUNCIA_1_P() {
		return NUME_DENUNCIA_1_P;
	}
	public void setNUME_DENUNCIA_1_P(BigDecimal nUME_DENUNCIA_1_P) {
		NUME_DENUNCIA_1_P = nUME_DENUNCIA_1_P;
	}
	public BigDecimal getNUME_DENUNCIA_2_P() {
		return NUME_DENUNCIA_2_P;
	}
	public void setNUME_DENUNCIA_2_P(BigDecimal nUME_DENUNCIA_2_P) {
		NUME_DENUNCIA_2_P = nUME_DENUNCIA_2_P;
	}
	public BigDecimal getNUME_ANNO_DENUNCIA_P() {
		return NUME_ANNO_DENUNCIA_P;
	}
	public void setNUME_ANNO_DENUNCIA_P(BigDecimal nUME_ANNO_DENUNCIA_P) {
		NUME_ANNO_DENUNCIA_P = nUME_ANNO_DENUNCIA_P;
	}
	public BigDecimal getNUME_GRAFFATI() {
		return NUME_GRAFFATI;
	}
	public void setNUME_GRAFFATI(BigDecimal nUME_GRAFFATI) {
		NUME_GRAFFATI = nUME_GRAFFATI;
	}
	public Character getCODI_FK_ID_FONDO() {
		return CODI_FK_ID_FONDO;
	}
	public void setCODI_FK_ID_FONDO(Character cODI_FK_ID_FONDO) {
		CODI_FK_ID_FONDO = cODI_FK_ID_FONDO;
	}
	public BigDecimal getNUME_SUP_ETTARI() {
		return NUME_SUP_ETTARI;
	}
	public void setNUME_SUP_ETTARI(BigDecimal nUME_SUP_ETTARI) {
		NUME_SUP_ETTARI = nUME_SUP_ETTARI;
	}
	public BigDecimal getNUME_SUP_ARE() {
		return NUME_SUP_ARE;
	}
	public void setNUME_SUP_ARE(BigDecimal nUME_SUP_ARE) {
		NUME_SUP_ARE = nUME_SUP_ARE;
	}
	public BigDecimal getNUME_SUP_CENTIARE() {
		return NUME_SUP_CENTIARE;
	}
	public void setNUME_SUP_CENTIARE(BigDecimal nUME_SUP_CENTIARE) {
		NUME_SUP_CENTIARE = nUME_SUP_CENTIARE;
	}
	public BigDecimal getNUME_SUP_MQ_INT() {
		return NUME_SUP_MQ_INT;
	}
	public void setNUME_SUP_MQ_INT(BigDecimal nUME_SUP_MQ_INT) {
		NUME_SUP_MQ_INT = nUME_SUP_MQ_INT;
	}
	public BigDecimal getNUME_SUP_MQ_DEC() {
		return NUME_SUP_MQ_DEC;
	}
	public void setNUME_SUP_MQ_DEC(BigDecimal nUME_SUP_MQ_DEC) {
		NUME_SUP_MQ_DEC = nUME_SUP_MQ_DEC;
	}
	public BigDecimal getNUME_VANI_INT() {
		return NUME_VANI_INT;
	}
	public void setNUME_VANI_INT(BigDecimal nUME_VANI_INT) {
		NUME_VANI_INT = nUME_VANI_INT;
	}
	public BigDecimal getNUME_VANI_DEC() {
		return NUME_VANI_DEC;
	}
	public void setNUME_VANI_DEC(BigDecimal nUME_VANI_DEC) {
		NUME_VANI_DEC = nUME_VANI_DEC;
	}
	public BigDecimal getNUME_MC_INT() {
		return NUME_MC_INT;
	}
	public void setNUME_MC_INT(BigDecimal nUME_MC_INT) {
		NUME_MC_INT = nUME_MC_INT;
	}
	public BigDecimal getNUME_MC_DEC() {
		return NUME_MC_DEC;
	}
	public void setNUME_MC_DEC(BigDecimal nUME_MC_DEC) {
		NUME_MC_DEC = nUME_MC_DEC;
	}
	public String getDESC_EDIFICIO() {
		return DESC_EDIFICIO;
	}
	public void setDESC_EDIFICIO(String dESC_EDIFICIO) {
		DESC_EDIFICIO = dESC_EDIFICIO;
	}
	public String getDESC_LOTTO() {
		return DESC_LOTTO;
	}
	public void setDESC_LOTTO(String dESC_LOTTO) {
		DESC_LOTTO = dESC_LOTTO;
	}
	public String getDESC_UTENTE() {
		return DESC_UTENTE;
	}
	public void setDESC_UTENTE(String dESC_UTENTE) {
		DESC_UTENTE = dESC_UTENTE;
	}
	public Date getDATA_ULT_AGG() {
		return DATA_ULT_AGG;
	}
	public void setDATA_ULT_AGG(Date dATA_ULT_AGG) {
		DATA_ULT_AGG = dATA_ULT_AGG;
	}

}
