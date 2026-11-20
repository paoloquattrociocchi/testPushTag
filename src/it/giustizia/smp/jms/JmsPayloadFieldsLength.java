package it.giustizia.smp.jms;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JmsPayloadFieldsLength implements Serializable {

	/** Logger for this class and subclasses */
	protected final Log logger = LogFactory.getLog(getClass());
	
	private int MAX_LEN_ID_FASCICOLO = 15;
	private int MAX_LEN_ANNO_FASCICOLO = 4;
	private int MAX_LEN_CODI_ID_UFFICIO = 6;
	private int MAX_LEN_DESC_UFFICIO = 255;
	private int MAX_LEN_TIPO_FASCICOLO = 100;
	private int MAX_LEN_SCHEMA_VIS = 7;
	private int MAX_LEN_ID_UTENTE = 16;
	
	private String idFascicolo;
	private String annoFascicolo;
	private String codIdUfficio;
	private String descUfficio;
	private String tipoFascicolo;
	private String schema;
	private String idUtente;

	public String getIdFascicolo() {
		return idFascicolo;
	}

	public void setIdFascicolo(String idFascicolo) {
		printLog("idFascicolo", idFascicolo, MAX_LEN_ID_FASCICOLO);
		this.idFascicolo = idFascicolo;
	}
	public String getAnnoFascicolo() {
		return annoFascicolo;
	}
	public void setAnnoFascicolo(String annoFascicolo) {
		this.annoFascicolo = annoFascicolo;
	}

	public String getCodIdUfficio() {
		return codIdUfficio;
	}

	public void setCodIdUfficio(String codIdUfficio) {
		printLog("codIdUfficio", codIdUfficio, MAX_LEN_CODI_ID_UFFICIO);
		this.codIdUfficio = codIdUfficio;
	}

	public String getDescUfficio() {
		return descUfficio;
	}

	public void setDescUfficio(String descUfficio) {
		printLog("descUfficio", descUfficio, MAX_LEN_DESC_UFFICIO);
		this.descUfficio = descUfficio;
	}

	public String getTipoFascicolo() {
		return tipoFascicolo;
	}

	public void setTipoFascicolo(String tipoFascicolo) {
		this.tipoFascicolo = tipoFascicolo;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		printLog("schema", schema, MAX_LEN_SCHEMA_VIS);
		this.schema = schema;
	}

	public String getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(String idUtente) {
		printLog("idUtente", idUtente, MAX_LEN_ID_UTENTE);
		this.idUtente = idUtente;
	}

	private void printLog(String key, String value, int def){
		String keyStr = String.format("%-13s", getValidStr(key)).replace(' ', '.');
		logger.info(String.format("%s=%3s, def=%3d", keyStr, getValidStr(value), def));
	}
	private String getValidStr(String in){
		return (in != null) ? in : "";
	}
	public int getIdFascicoloLen() {
		return getSystemPropertyAsInt("len.idFascicolo", getIdFascicolo(), MAX_LEN_ID_FASCICOLO);
	}
	public int getAnnoFascicoloLen() {
		return getSystemPropertyAsInt("len.annoFascicolo", getAnnoFascicolo(), MAX_LEN_ANNO_FASCICOLO);
	}
	public int getCodIdUfficioLen() {
		return getSystemPropertyAsInt("len.codIdUfficio", getCodIdUfficio(), MAX_LEN_CODI_ID_UFFICIO);
	}
	public int getTipoFascicoloLen() {
		return getSystemPropertyAsInt("len.tipoFascicolo", getTipoFascicolo(), MAX_LEN_TIPO_FASCICOLO);
	}
	public int getDescUfficioLen() {
		return getSystemPropertyAsInt("len.descUfficio", getDescUfficio(), MAX_LEN_DESC_UFFICIO);
	}
	public int getSchemaLen() {
		return getSystemPropertyAsInt("len.schema", getSchema(), MAX_LEN_SCHEMA_VIS);
	}
	public int getIdUtenteLen() {
		return getSystemPropertyAsInt("len.idUtente", getIdUtente(), MAX_LEN_ID_UTENTE);
	}
	private int getSystemPropertyAsInt(String key, String value, int def){
		logger.info(key + "=" + value);
		int out = def;
		try{
			out = Integer.parseInt(value);
		}
		catch (Exception e){
			logger.error(e.getMessage());
		}
		return out;
	}
}
