/**
 * 
 */
package it.giustizia.smp.dto.flussi;

/**
 * @author an.delia
 *
 */
public class T_INTESTATARIODto {

	private String CODI_K_ID_INTESTATARIO;
	private String CODI_K_ID_UFF_OWNER;
	private String CODI_FK_ID_TIPO_SOGG;
	private String DESC_COGNOME;
	private String DESC_NOME;
	private String DATA_NASCITA;
	private String DESC_CODICE_FISCALE;
	
	public String getCODI_K_ID_INTESTATARIO() {
		return CODI_K_ID_INTESTATARIO;
	}
	public void setCODI_K_ID_INTESTATARIO(String cODI_K_ID_INTESTATARIO) {
		CODI_K_ID_INTESTATARIO = cODI_K_ID_INTESTATARIO;
	}
	public String getCODI_K_ID_UFF_OWNER() {
		return CODI_K_ID_UFF_OWNER;
	}
	public void setCODI_K_ID_UFF_OWNER(String cODI_K_ID_UFF_OWNER) {
		CODI_K_ID_UFF_OWNER = cODI_K_ID_UFF_OWNER;
	}
	public String getCODI_FK_ID_TIPO_SOGG() {
		return CODI_FK_ID_TIPO_SOGG;
	}
	public void setCODI_FK_ID_TIPO_SOGG(String cODI_FK_ID_TIPO_SOGG) {
		CODI_FK_ID_TIPO_SOGG = cODI_FK_ID_TIPO_SOGG;
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
	public String getDATA_NASCITA() {
		return DATA_NASCITA;
	}
	public void setDATA_NASCITA(String dATA_NASCITA) {
		DATA_NASCITA = dATA_NASCITA;
	}
	public String getDESC_CODICE_FISCALE() {
		return DESC_CODICE_FISCALE;
	}
	public void setDESC_CODICE_FISCALE(String dESC_CODICE_FISCALE) {
		DESC_CODICE_FISCALE = dESC_CODICE_FISCALE;
	}
	
}
