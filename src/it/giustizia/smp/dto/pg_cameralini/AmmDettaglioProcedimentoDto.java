package it.giustizia.smp.dto.pg_cameralini;

import java.math.BigDecimal;
import java.util.Date;

public class AmmDettaglioProcedimentoDto {

	
    protected BigDecimal ID_PROC;								
    protected BigDecimal ID_UFF_OWNER;							
    protected BigDecimal ID_ATTO;								
    protected BigDecimal ID_AMMIN;								 
    protected String     DESC_COGNOME ;                         
    protected String     DESC_NOME ;                            
    protected String     PARTITA_IVA ;                                  
    protected Date       DATA_NASCITA ;                         
    protected String     DESC_CODICE_FISCALE ;     
    protected BigDecimal ID_STATO_DL;
    protected BigDecimal ID_COM_DL;
    protected String     COM_EST_DL;
    protected String     DESC_COM_ESTERO_DL;
    protected String     DESC_LUOGO_NASCITA;
    protected String     DESC_STUDIO;
    protected String     DESC_FAX;
    protected String     DESC_TEL;
    protected String     DESC_PEC;
    protected String     DESC_ISCR_ALBO;
    protected Date       DATA_ISCR_ALBO;
    protected String     DESC_STATO_ISCR_ALBO;
    protected BigDecimal ID_TIPO_PROC;
    protected String     MISURA;
    protected String     CAUZIONE;
    protected String     ART67;
    protected String     DESC_COMUNE;
    
    
	public String getDESC_COMUNE() {
		return DESC_COMUNE;
	}
	public void setDESC_COMUNE(String dESC_COMUNE) {
		DESC_COMUNE = dESC_COMUNE;
	}
	public BigDecimal getID_PROC() {
		return ID_PROC;
	}
	public void setID_PROC(BigDecimal iD_PROC) {
		ID_PROC = iD_PROC;
	}
	public BigDecimal getID_UFF_OWNER() {
		return ID_UFF_OWNER;
	}
	public void setID_UFF_OWNER(BigDecimal iD_UFF_OWNER) {
		ID_UFF_OWNER = iD_UFF_OWNER;
	}
	public BigDecimal getID_ATTO() {
		return ID_ATTO;
	}
	public void setID_ATTO(BigDecimal iD_ATTO) {
		ID_ATTO = iD_ATTO;
	}
	public BigDecimal getID_AMMIN() {
		return ID_AMMIN;
	}
	public void setID_AMMIN(BigDecimal iD_AMMIN) {
		ID_AMMIN = iD_AMMIN;
	}
	public String getDESC_COGNOME() {
		return DESC_COGNOME;
	}
	public void setDESC_COGNOME(String dESC_COGNOME) {
		DESC_COGNOME = dESC_COGNOME;
	}
	public String getDESC_NOME() {
		return DESC_NOME;
	}
	public void setDESC_NOME(String dESC_NOME) {
		DESC_NOME = dESC_NOME;
	}
	public String getPARTITA_IVA() {
		return PARTITA_IVA;
	}
	public void setPARTITA_IVA(String pARTITA_IVA) {
		PARTITA_IVA = pARTITA_IVA;
	}
	public Date getDATA_NASCITA() {
		return DATA_NASCITA;
	}
	public void setDATA_NASCITA(Date dATA_NASCITA) {
		DATA_NASCITA = dATA_NASCITA;
	}
	public String getDESC_CODICE_FISCALE() {
		return DESC_CODICE_FISCALE;
	}
	public void setDESC_CODICE_FISCALE(String dESC_CODICE_FISCALE) {
		DESC_CODICE_FISCALE = dESC_CODICE_FISCALE;
	}
	public BigDecimal getID_STATO_DL() {
		return ID_STATO_DL;
	}
	public void setID_STATO_DL(BigDecimal iD_STATO_DL) {
		ID_STATO_DL = iD_STATO_DL;
	}
	public BigDecimal getID_COM_DL() {
		return ID_COM_DL;
	}
	public void setID_COM_DL(BigDecimal iD_COM_DL) {
		ID_COM_DL = iD_COM_DL;
	}
	public String getCOM_EST_DL() {
		return COM_EST_DL;
	}
	public void setCOM_EST_DL(String cOM_EST_DL) {
		COM_EST_DL = cOM_EST_DL;
	}
	public String getDESC_COM_ESTERO_DL() {
		return DESC_COM_ESTERO_DL;
	}
	public void setDESC_COM_ESTERO_DL(String dESC_COM_ESTERO_DL) {
		DESC_COM_ESTERO_DL = dESC_COM_ESTERO_DL;
	}
	public String getDESC_LUOGO_NASCITA() {
		return DESC_LUOGO_NASCITA;
	}
	public void setDESC_LUOGO_NASCITA(String dESC_LUOGO_NASCITA) {
		DESC_LUOGO_NASCITA = dESC_LUOGO_NASCITA;
	}
	public String getDESC_STUDIO() {
		return DESC_STUDIO;
	}
	public void setDESC_STUDIO(String dESC_STUDIO) {
		DESC_STUDIO = dESC_STUDIO;
	}
	public String getDESC_FAX() {
		return DESC_FAX;
	}
	public void setDESC_FAX(String dESC_FAX) {
		DESC_FAX = dESC_FAX;
	}
	public String getDESC_TEL() {
		return DESC_TEL;
	}
	public void setDESC_TEL(String dESC_TEL) {
		DESC_TEL = dESC_TEL;
	}
	public String getDESC_PEC() {
		return DESC_PEC;
	}
	public void setDESC_PEC(String dESC_PEC) {
		DESC_PEC = dESC_PEC;
	}
	public String getDESC_ISCR_ALBO() {
		return DESC_ISCR_ALBO;
	}
	public void setDESC_ISCR_ALBO(String dESC_ISCR_ALBO) {
		DESC_ISCR_ALBO = dESC_ISCR_ALBO;
	}
	public Date getDATA_ISCR_ALBO() {
		return DATA_ISCR_ALBO;
	}
	public void setDATA_ISCR_ALBO(Date dATA_ISCR_ALBO) {
		DATA_ISCR_ALBO = dATA_ISCR_ALBO;
	}
	public String getDESC_STATO_ISCR_ALBO() {
		return DESC_STATO_ISCR_ALBO;
	}
	public void setDESC_STATO_ISCR_ALBO(String dESC_STATO_ISCR_ALBO) {
		DESC_STATO_ISCR_ALBO = dESC_STATO_ISCR_ALBO;
	}
	public BigDecimal getID_TIPO_PROC() {
		return ID_TIPO_PROC;
	}
	public void setID_TIPO_PROC(BigDecimal iD_TIPO_PROC) {
		ID_TIPO_PROC = iD_TIPO_PROC;
	}
	public String getMISURA() {
		return MISURA;
	}
	public void setMISURA(String mISURA) {
		MISURA = mISURA;
	}
	public String getCAUZIONE() {
		return CAUZIONE;
	}
	public void setCAUZIONE(String cAUZIONE) {
		CAUZIONE = cAUZIONE;
	}
	public String getART67() {
		return ART67;
	}
	public void setART67(String aRT67) {
		ART67 = aRT67;
	}

    
}
    
   