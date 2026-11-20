package it.giustizia.smp.dto.pg_report;

import java.math.BigDecimal;
import java.util.Date;


public class ReportMispatrBeneSogDto {

	private Character TIPO_PERSONA_INTERESSATO;
    private BigDecimal ID_SOGGETTO_INTERESSATO;
    private String COGNOME_INTERESSATO;
    private String NOME_INTERESSATO;
    private String SESSO_INTERESSATO;
    private Date DATA_NASCITA_INTERESSATO;
    private String DESC_STATO_NASC_INTERESSATO;
    private String DESC_COMUNE_NASC_INTERESSATO;
    private String SIGLA_PROV_NASC_INTERESSATO;
    private String COM_EST_NASC_INTERESSATO;          
    private String RAG_SOC_INTERESSATO;
    private BigDecimal PARTITA_IVA_INTERESSATO;      
    private String DESC_STATO_SEDE_INTERESSATO;
    private String DESC_COMUNE_SEDE_INTERESSATO; 
    private String SIGLA_PROV_SEDE_INTERESSATO;
    private String COM_EST_SEDE_INTERESSATO;

    private BigDecimal ID_BENE;
    private String DESCRIZIONE_BENE;
    private BigDecimal NUM_QUOTA;
    private BigDecimal NUME_FRAZIONE_DEN;
    private BigDecimal NUME_FRAZIONE_NUM;  
    
    private Character TIPO_PERSONA_TITOLARE;
    private BigDecimal ID_SOGGETTO_TITOLARE;
    private String COGNOME_TITOLARE;
    private String NOME_TITOLARE;
    private String SESSO_TITOLARE;
    private Date DATA_NASCITA_TITOLARE;
    private String DESC_STATO_NASC_TITOLARE;
    private String DESC_COMUNE_NASC_TITOLARE;
    private String SIGLA_PROV_NASC_TITOLARE;        
    private String COM_EST_NASC_TITOLARE;          
    private String RAG_SOC_TITOLARE;
    private BigDecimal PARTITA_IVA_TITOLARE;        
    private String DESC_STATO_SEDE_TITOLARE;
    private String DESC_COMUNE_SEDE_TITOLARE; 
    private String SIGLA_PROV_SEDE_TITOLARE;
    private String COM_EST_SEDE_TITOLARE;
    
    private String testoSoggettoInteressato;
    private String testoBene;
    private String testoSoggettoTitolare;
	/**
	 * @return the tIPO_PERSONA_INTERESSATO
	 */
	public Character getTIPO_PERSONA_INTERESSATO() {
		return TIPO_PERSONA_INTERESSATO;
	}
	/**
	 * @param tIPO_PERSONA_INTERESSATO the tIPO_PERSONA_INTERESSATO to set
	 */
	public void setTIPO_PERSONA_INTERESSATO(Character tIPO_PERSONA_INTERESSATO) {
		TIPO_PERSONA_INTERESSATO = tIPO_PERSONA_INTERESSATO;
	}
	/**
	 * @return the iD_SOGGETTO_INTERESSATO
	 */
	public BigDecimal getID_SOGGETTO_INTERESSATO() {
		return ID_SOGGETTO_INTERESSATO;
	}
	/**
	 * @param iD_SOGGETTO_INTERESSATO the iD_SOGGETTO_INTERESSATO to set
	 */
	public void setID_SOGGETTO_INTERESSATO(BigDecimal iD_SOGGETTO_INTERESSATO) {
		ID_SOGGETTO_INTERESSATO = iD_SOGGETTO_INTERESSATO;
	}
	/**
	 * @return the cOGNOME_INTERESSATO
	 */
	public String getCOGNOME_INTERESSATO() {
		return COGNOME_INTERESSATO;
	}
	/**
	 * @param cOGNOME_INTERESSATO the cOGNOME_INTERESSATO to set
	 */
	public void setCOGNOME_INTERESSATO(String cOGNOME_INTERESSATO) {
		COGNOME_INTERESSATO = cOGNOME_INTERESSATO;
	}
	/**
	 * @return the nOME_INTERESSATO
	 */
	public String getNOME_INTERESSATO() {
		return NOME_INTERESSATO;
	}
	/**
	 * @param nOME_INTERESSATO the nOME_INTERESSATO to set
	 */
	public void setNOME_INTERESSATO(String nOME_INTERESSATO) {
		NOME_INTERESSATO = nOME_INTERESSATO;
	}
	/**
	 * @return the sESSO_INTERESSATO
	 */
	public String getSESSO_INTERESSATO() {
		return SESSO_INTERESSATO;
	}
	/**
	 * @param sESSO_INTERESSATO the sESSO_INTERESSATO to set
	 */
	public void setSESSO_INTERESSATO(String sESSO_INTERESSATO) {
		SESSO_INTERESSATO = sESSO_INTERESSATO;
	}
	/**
	 * @return the dATA_NASCITA_INTERESSATO
	 */
	public Date getDATA_NASCITA_INTERESSATO() {
		return DATA_NASCITA_INTERESSATO;
	}
	/**
	 * @param dATA_NASCITA_INTERESSATO the dATA_NASCITA_INTERESSATO to set
	 */
	public void setDATA_NASCITA_INTERESSATO(Date dATA_NASCITA_INTERESSATO) {
		DATA_NASCITA_INTERESSATO = dATA_NASCITA_INTERESSATO;
	}
	/**
	 * @return the dESC_STATO_NASC_INTERESSATO
	 */
	public String getDESC_STATO_NASC_INTERESSATO() {
		return DESC_STATO_NASC_INTERESSATO;
	}
	/**
	 * @param dESC_STATO_NASC_INTERESSATO the dESC_STATO_NASC_INTERESSATO to set
	 */
	public void setDESC_STATO_NASC_INTERESSATO(String dESC_STATO_NASC_INTERESSATO) {
		DESC_STATO_NASC_INTERESSATO = dESC_STATO_NASC_INTERESSATO;
	}
	/**
	 * @return the dESC_COMUNE_NASC_INTERESSATO
	 */
	public String getDESC_COMUNE_NASC_INTERESSATO() {
		return DESC_COMUNE_NASC_INTERESSATO;
	}
	/**
	 * @param dESC_COMUNE_NASC_INTERESSATO the dESC_COMUNE_NASC_INTERESSATO to set
	 */
	public void setDESC_COMUNE_NASC_INTERESSATO(String dESC_COMUNE_NASC_INTERESSATO) {
		DESC_COMUNE_NASC_INTERESSATO = dESC_COMUNE_NASC_INTERESSATO;
	}
	/**
	 * @return the sIGLA_PROV_NASC_INTERESSATO
	 */
	public String getSIGLA_PROV_NASC_INTERESSATO() {
		return SIGLA_PROV_NASC_INTERESSATO;
	}
	/**
	 * @param sIGLA_PROV_NASC_INTERESSATO the sIGLA_PROV_NASC_INTERESSATO to set
	 */
	public void setSIGLA_PROV_NASC_INTERESSATO(String sIGLA_PROV_NASC_INTERESSATO) {
		SIGLA_PROV_NASC_INTERESSATO = sIGLA_PROV_NASC_INTERESSATO;
	}
	/**
	 * @return the cOM_EST_NASC_INTERESSATO
	 */
	public String getCOM_EST_NASC_INTERESSATO() {
		return COM_EST_NASC_INTERESSATO;
	}
	/**
	 * @param cOM_EST_NASC_INTERESSATO the cOM_EST_NASC_INTERESSATO to set
	 */
	public void setCOM_EST_NASC_INTERESSATO(String cOM_EST_NASC_INTERESSATO) {
		COM_EST_NASC_INTERESSATO = cOM_EST_NASC_INTERESSATO;
	}
	/**
	 * @return the rAG_SOC_INTERESSATO
	 */
	public String getRAG_SOC_INTERESSATO() {
		return RAG_SOC_INTERESSATO;
	}
	/**
	 * @param rAG_SOC_INTERESSATO the rAG_SOC_INTERESSATO to set
	 */
	public void setRAG_SOC_INTERESSATO(String rAG_SOC_INTERESSATO) {
		RAG_SOC_INTERESSATO = rAG_SOC_INTERESSATO;
	}
	/**
	 * @return the pARTITA_IVA_INTERESSATO
	 */
	public BigDecimal getPARTITA_IVA_INTERESSATO() {
		return PARTITA_IVA_INTERESSATO;
	}
	/**
	 * @param pARTITA_IVA_INTERESSATO the pARTITA_IVA_INTERESSATO to set
	 */
	public void setPARTITA_IVA_INTERESSATO(BigDecimal pARTITA_IVA_INTERESSATO) {
		PARTITA_IVA_INTERESSATO = pARTITA_IVA_INTERESSATO;
	}
	/**
	 * @return the dESC_STATO_SEDE_INTERESSATO
	 */
	public String getDESC_STATO_SEDE_INTERESSATO() {
		return DESC_STATO_SEDE_INTERESSATO;
	}
	/**
	 * @param dESC_STATO_SEDE_INTERESSATO the dESC_STATO_SEDE_INTERESSATO to set
	 */
	public void setDESC_STATO_SEDE_INTERESSATO(String dESC_STATO_SEDE_INTERESSATO) {
		DESC_STATO_SEDE_INTERESSATO = dESC_STATO_SEDE_INTERESSATO;
	}
	/**
	 * @return the dESC_COMUNE_SEDE_INTERESSATO
	 */
	public String getDESC_COMUNE_SEDE_INTERESSATO() {
		return DESC_COMUNE_SEDE_INTERESSATO;
	}
	/**
	 * @param dESC_COMUNE_SEDE_INTERESSATO the dESC_COMUNE_SEDE_INTERESSATO to set
	 */
	public void setDESC_COMUNE_SEDE_INTERESSATO(String dESC_COMUNE_SEDE_INTERESSATO) {
		DESC_COMUNE_SEDE_INTERESSATO = dESC_COMUNE_SEDE_INTERESSATO;
	}
	/**
	 * @return the sIGLA_PROV_SEDE_INTERESSATO
	 */
	public String getSIGLA_PROV_SEDE_INTERESSATO() {
		return SIGLA_PROV_SEDE_INTERESSATO;
	}
	/**
	 * @param sIGLA_PROV_SEDE_INTERESSATO the sIGLA_PROV_SEDE_INTERESSATO to set
	 */
	public void setSIGLA_PROV_SEDE_INTERESSATO(String sIGLA_PROV_SEDE_INTERESSATO) {
		SIGLA_PROV_SEDE_INTERESSATO = sIGLA_PROV_SEDE_INTERESSATO;
	}
	/**
	 * @return the cOM_EST_SEDE_INTERESSATO
	 */
	public String getCOM_EST_SEDE_INTERESSATO() {
		return COM_EST_SEDE_INTERESSATO;
	}
	/**
	 * @param cOM_EST_SEDE_INTERESSATO the cOM_EST_SEDE_INTERESSATO to set
	 */
	public void setCOM_EST_SEDE_INTERESSATO(String cOM_EST_SEDE_INTERESSATO) {
		COM_EST_SEDE_INTERESSATO = cOM_EST_SEDE_INTERESSATO;
	}
	/**
	 * @return the iD_BENE
	 */
	public BigDecimal getID_BENE() {
		return ID_BENE;
	}
	/**
	 * @param iD_BENE the iD_BENE to set
	 */
	public void setID_BENE(BigDecimal iD_BENE) {
		ID_BENE = iD_BENE;
	}
	/**
	 * @return the dESCRIZIONE_BENE
	 */
	public String getDESCRIZIONE_BENE() {
		return DESCRIZIONE_BENE;
	}
	/**
	 * @param dESCRIZIONE_BENE the dESCRIZIONE_BENE to set
	 */
	public void setDESCRIZIONE_BENE(String dESCRIZIONE_BENE) {
		DESCRIZIONE_BENE = dESCRIZIONE_BENE;
	}
	/**
	 * @return the nUM_QUOTA
	 */
	public BigDecimal getNUM_QUOTA() {
		return NUM_QUOTA;
	}
	/**
	 * @param nUM_QUOTA the nUM_QUOTA to set
	 */
	public void setNUM_QUOTA(BigDecimal nUM_QUOTA) {
		NUM_QUOTA = nUM_QUOTA;
	}
	/**
	 * @return the nUME_FRAZIONE_DEN
	 */
	public BigDecimal getNUME_FRAZIONE_DEN() {
		return NUME_FRAZIONE_DEN;
	}
	/**
	 * @param nUME_FRAZIONE_DEN the nUME_FRAZIONE_DEN to set
	 */
	public void setNUME_FRAZIONE_DEN(BigDecimal nUME_FRAZIONE_DEN) {
		NUME_FRAZIONE_DEN = nUME_FRAZIONE_DEN;
	}
	/**
	 * @return the nUME_FRAZIONE_NUM
	 */
	public BigDecimal getNUME_FRAZIONE_NUM() {
		return NUME_FRAZIONE_NUM;
	}
	/**
	 * @param nUME_FRAZIONE_NUM the nUME_FRAZIONE_NUM to set
	 */
	public void setNUME_FRAZIONE_NUM(BigDecimal nUME_FRAZIONE_NUM) {
		NUME_FRAZIONE_NUM = nUME_FRAZIONE_NUM;
	}
	/**
	 * @return the tIPO_PERSONA_TITOLARE
	 */
	public Character getTIPO_PERSONA_TITOLARE() {
		return TIPO_PERSONA_TITOLARE;
	}
	/**
	 * @param tIPO_PERSONA_TITOLARE the tIPO_PERSONA_TITOLARE to set
	 */
	public void setTIPO_PERSONA_TITOLARE(Character tIPO_PERSONA_TITOLARE) {
		TIPO_PERSONA_TITOLARE = tIPO_PERSONA_TITOLARE;
	}
	/**
	 * @return the iD_SOGGETTO_TITOLARE
	 */
	public BigDecimal getID_SOGGETTO_TITOLARE() {
		return ID_SOGGETTO_TITOLARE;
	}
	/**
	 * @param iD_SOGGETTO_TITOLARE the iD_SOGGETTO_TITOLARE to set
	 */
	public void setID_SOGGETTO_TITOLARE(BigDecimal iD_SOGGETTO_TITOLARE) {
		ID_SOGGETTO_TITOLARE = iD_SOGGETTO_TITOLARE;
	}
	/**
	 * @return the cOGNOME_TITOLARE
	 */
	public String getCOGNOME_TITOLARE() {
		return COGNOME_TITOLARE;
	}
	/**
	 * @param cOGNOME_TITOLARE the cOGNOME_TITOLARE to set
	 */
	public void setCOGNOME_TITOLARE(String cOGNOME_TITOLARE) {
		COGNOME_TITOLARE = cOGNOME_TITOLARE;
	}
	/**
	 * @return the nOME_TITOLARE
	 */
	public String getNOME_TITOLARE() {
		return NOME_TITOLARE;
	}
	/**
	 * @param nOME_TITOLARE the nOME_TITOLARE to set
	 */
	public void setNOME_TITOLARE(String nOME_TITOLARE) {
		NOME_TITOLARE = nOME_TITOLARE;
	}
	/**
	 * @return the sESSO_TITOLARE
	 */
	public String getSESSO_TITOLARE() {
		return SESSO_TITOLARE;
	}
	/**
	 * @param sESSO_TITOLARE the sESSO_TITOLARE to set
	 */
	public void setSESSO_TITOLARE(String sESSO_TITOLARE) {
		SESSO_TITOLARE = sESSO_TITOLARE;
	}
	/**
	 * @return the dATA_NASCITA_TITOLARE
	 */
	public Date getDATA_NASCITA_TITOLARE() {
		return DATA_NASCITA_TITOLARE;
	}
	/**
	 * @param dATA_NASCITA_TITOLARE the dATA_NASCITA_TITOLARE to set
	 */
	public void setDATA_NASCITA_TITOLARE(Date dATA_NASCITA_TITOLARE) {
		DATA_NASCITA_TITOLARE = dATA_NASCITA_TITOLARE;
	}
	/**
	 * @return the dESC_STATO_NASC_TITOLARE
	 */
	public String getDESC_STATO_NASC_TITOLARE() {
		return DESC_STATO_NASC_TITOLARE;
	}
	/**
	 * @param dESC_STATO_NASC_TITOLARE the dESC_STATO_NASC_TITOLARE to set
	 */
	public void setDESC_STATO_NASC_TITOLARE(String dESC_STATO_NASC_TITOLARE) {
		DESC_STATO_NASC_TITOLARE = dESC_STATO_NASC_TITOLARE;
	}
	/**
	 * @return the dESC_COMUNE_NASC_TITOLARE
	 */
	public String getDESC_COMUNE_NASC_TITOLARE() {
		return DESC_COMUNE_NASC_TITOLARE;
	}
	/**
	 * @param dESC_COMUNE_NASC_TITOLARE the dESC_COMUNE_NASC_TITOLARE to set
	 */
	public void setDESC_COMUNE_NASC_TITOLARE(String dESC_COMUNE_NASC_TITOLARE) {
		DESC_COMUNE_NASC_TITOLARE = dESC_COMUNE_NASC_TITOLARE;
	}
	/**
	 * @return the sIGLA_PROV_NASC_TITOLARE
	 */
	public String getSIGLA_PROV_NASC_TITOLARE() {
		return SIGLA_PROV_NASC_TITOLARE;
	}
	/**
	 * @param sIGLA_PROV_NASC_TITOLARE the sIGLA_PROV_NASC_TITOLARE to set
	 */
	public void setSIGLA_PROV_NASC_TITOLARE(String sIGLA_PROV_NASC_TITOLARE) {
		SIGLA_PROV_NASC_TITOLARE = sIGLA_PROV_NASC_TITOLARE;
	}
	/**
	 * @return the cOM_EST_NASC_TITOLARE
	 */
	public String getCOM_EST_NASC_TITOLARE() {
		return COM_EST_NASC_TITOLARE;
	}
	/**
	 * @param cOM_EST_NASC_TITOLARE the cOM_EST_NASC_TITOLARE to set
	 */
	public void setCOM_EST_NASC_TITOLARE(String cOM_EST_NASC_TITOLARE) {
		COM_EST_NASC_TITOLARE = cOM_EST_NASC_TITOLARE;
	}
	/**
	 * @return the rAG_SOC_TITOLARE
	 */
	public String getRAG_SOC_TITOLARE() {
		return RAG_SOC_TITOLARE;
	}
	/**
	 * @param rAG_SOC_TITOLARE the rAG_SOC_TITOLARE to set
	 */
	public void setRAG_SOC_TITOLARE(String rAG_SOC_TITOLARE) {
		RAG_SOC_TITOLARE = rAG_SOC_TITOLARE;
	}
	/**
	 * @return the pARTITA_IVA_TITOLARE
	 */
	public BigDecimal getPARTITA_IVA_TITOLARE() {
		return PARTITA_IVA_TITOLARE;
	}
	/**
	 * @param pARTITA_IVA_TITOLARE the pARTITA_IVA_TITOLARE to set
	 */
	public void setPARTITA_IVA_TITOLARE(BigDecimal pARTITA_IVA_TITOLARE) {
		PARTITA_IVA_TITOLARE = pARTITA_IVA_TITOLARE;
	}
	/**
	 * @return the dESC_STATO_SEDE_TITOLARE
	 */
	public String getDESC_STATO_SEDE_TITOLARE() {
		return DESC_STATO_SEDE_TITOLARE;
	}
	/**
	 * @param dESC_STATO_SEDE_TITOLARE the dESC_STATO_SEDE_TITOLARE to set
	 */
	public void setDESC_STATO_SEDE_TITOLARE(String dESC_STATO_SEDE_TITOLARE) {
		DESC_STATO_SEDE_TITOLARE = dESC_STATO_SEDE_TITOLARE;
	}
	/**
	 * @return the dESC_COMUNE_SEDE_TITOLARE
	 */
	public String getDESC_COMUNE_SEDE_TITOLARE() {
		return DESC_COMUNE_SEDE_TITOLARE;
	}
	/**
	 * @param dESC_COMUNE_SEDE_TITOLARE the dESC_COMUNE_SEDE_TITOLARE to set
	 */
	public void setDESC_COMUNE_SEDE_TITOLARE(String dESC_COMUNE_SEDE_TITOLARE) {
		DESC_COMUNE_SEDE_TITOLARE = dESC_COMUNE_SEDE_TITOLARE;
	}
	/**
	 * @return the sIGLA_PROV_SEDE_TITOLARE
	 */
	public String getSIGLA_PROV_SEDE_TITOLARE() {
		return SIGLA_PROV_SEDE_TITOLARE;
	}
	/**
	 * @param sIGLA_PROV_SEDE_TITOLARE the sIGLA_PROV_SEDE_TITOLARE to set
	 */
	public void setSIGLA_PROV_SEDE_TITOLARE(String sIGLA_PROV_SEDE_TITOLARE) {
		SIGLA_PROV_SEDE_TITOLARE = sIGLA_PROV_SEDE_TITOLARE;
	}
	/**
	 * @return the cOM_EST_SEDE_TITOLARE
	 */
	public String getCOM_EST_SEDE_TITOLARE() {
		return COM_EST_SEDE_TITOLARE;
	}
	/**
	 * @param cOM_EST_SEDE_TITOLARE the cOM_EST_SEDE_TITOLARE to set
	 */
	public void setCOM_EST_SEDE_TITOLARE(String cOM_EST_SEDE_TITOLARE) {
		COM_EST_SEDE_TITOLARE = cOM_EST_SEDE_TITOLARE;
	}
	/**
	 * @return the testoSoggettoInteressato
	 */
	public String getTestoSoggettoInteressato() {
		return testoSoggettoInteressato;
	}
	/**
	 * @param testoSoggettoInteressato the testoSoggettoInteressato to set
	 */
	public void setTestoSoggettoInteressato(String testoSoggettoInteressato) {
		this.testoSoggettoInteressato = testoSoggettoInteressato;
	}
	/**
	 * @return the testoBene
	 */
	public String getTestoBene() {
		return testoBene;
	}
	/**
	 * @param testoBene the testoBene to set
	 */
	public void setTestoBene(String testoBene) {
		this.testoBene = testoBene;
	}
	/**
	 * @return the testoSoggettoTitolare
	 */
	public String getTestoSoggettoTitolare() {
		return testoSoggettoTitolare;
	}
	/**
	 * @param testoSoggettoTitolare the testoSoggettoTitolare to set
	 */
	public void setTestoSoggettoTitolare(String testoSoggettoTitolare) {
		this.testoSoggettoTitolare = testoSoggettoTitolare;
	}
	
	
	
	
}
