package it.giustizia.smp.jms;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class JmsMessage implements Serializable {

	protected final Log logger = LogFactory.getLog(getClass());
	
	public final static String MY_DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
//	public final static String MY_DATE_FORMAT = "yy-MMM-dd";
	public final static String SEP = "|";

	private String idFascicolo = null;
	private String annoFascicolo = null;
	private String codIdUfficio = null;
	private String descUfficio = null;
	private String tipoFascicolo = null;
	private Date data = null;
	private String schema = null;
	private String idUtente = null;
  private JmsPayloadFieldsLength jmsPayloadFieldsLength;

	private String getValidString(String in) {
		return (in != null) ? in : "";
	}

	private int min(int a, int b){
		return (a < b) ? a : b;
	}
	private String getValidField(String in, int maxLen){
		return in.substring(0, min(in.length(), maxLen));
	}
	public String getIdFascicolo(boolean trunk) {
		String out = getValidString(idFascicolo);
		return (trunk) ? getValidField(out, jmsPayloadFieldsLength.getIdFascicoloLen()) : out;
	}
	public String getIdFascicolo() {
		return getIdFascicolo(false);
	}
	public void setIdFascicolo(String idFascicolo) {
		this.idFascicolo = idFascicolo;
	}
	public String getCodIdUfficio(boolean trunk) {
		String out = getValidString(codIdUfficio);
		return (trunk) ? getValidField(out, jmsPayloadFieldsLength.getCodIdUfficioLen()) : out;
	}
	public String getCodIdUfficio() {
		return getCodIdUfficio(false);
	}
	public void setCodIdUfficio(String codIdUfficio) {
		this.codIdUfficio = codIdUfficio;
	}
	public String getDescUfficio(boolean trunk) {
		String out = getValidString(descUfficio);
		return (trunk) ? getValidField(out, jmsPayloadFieldsLength.getDescUfficioLen()) : out;
	}
	public String getDescUfficio() {
		return getDescUfficio(false);
	}
	public void setDescUfficio(String descUfficio) {
		this.descUfficio = descUfficio;
	}

	public String getDataStr() {
		String out = "";
		if (getData() != null){
			SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(MY_DATE_FORMAT);
			out = DATE_FORMAT.format(getData());
		}
		return out;
	}

	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getSchema(boolean trunk) {
		String out = getValidString(schema);
		return (trunk) ? getValidField(out, jmsPayloadFieldsLength.getSchemaLen()) : out;
	}
	public String getSchema() {
		return getSchema(false);
	}
	public void setSchema(String schema) {
		this.schema = schema;
	}
	public String getIdUtente(boolean trunk) {
		String out = getValidString(idUtente);
		return (trunk) ? getValidField(out, jmsPayloadFieldsLength.getIdUtenteLen()) : out;
	}
	public String getIdUtente() {
		return getIdUtente(false);
	}
	public void setIdUtente(String idUtente) {
		this.idUtente = idUtente;
	}
	public String getAnnoFascicolo() {
		return annoFascicolo;
	}
	public String getAnnoFascicolo(boolean trunk) {
		String out = getValidString(annoFascicolo);
		return (trunk) ? getValidField(out, jmsPayloadFieldsLength.getAnnoFascicoloLen()) : out;
	}
	public void setAnnoFascicolo(String annoFascicolo) {
		this.annoFascicolo = annoFascicolo;
	}
	
	public String getTipoFascicolo() {
		return tipoFascicolo;
	}
	public String getTipoFascicolo(boolean trunk) {
		String out = getValidString(tipoFascicolo);
		return (trunk) ? getValidField(out, jmsPayloadFieldsLength.getTipoFascicoloLen()) : out;
	}
	public void setTipoFascicolo(String tipoFascicolo) {
		this.tipoFascicolo = tipoFascicolo;
	}

	public JmsPayloadFieldsLength getJmsPayloadFieldsLength() {
		return jmsPayloadFieldsLength;
	}

	public void setJmsPayloadFieldsLength(JmsPayloadFieldsLength jmsPayloadFieldsLength) {
		this.jmsPayloadFieldsLength = jmsPayloadFieldsLength;
	}

	public void clearMessage(){
		this.setIdFascicolo(null);
		this.setAnnoFascicolo(null);
		this.setCodIdUfficio(null);
		this.setData(null);
		this.setDescUfficio(null);
		this.setTipoFascicolo(null);
		this.setIdUtente(null);
		this.setSchema(null);
	}

	public String getTextMessage() {
  	/*
  	"SITMPLOG"."CENTRAL_LOG" 
  	"ID_FASCICOLO" VARCHAR2(17 BYTE), 
  	"CODI_ID_UFFICIO" VARCHAR2(11 BYTE), 
  	"DESC_UFFICIO" VARCHAR2(255 BYTE), 
  	"DATA_VIS" DATE, 
  	"SCHEMA_VIS" VARCHAR2(7 BYTE), 
  	"ID_UTENTE" VARCHAR2(16 BYTE)
    */
  	// ID_FASCICOLO|CODI_ID_UFFICIO|DESC_UFFICIO|DATA|SCHEMA|ID_UTENTE
		StringBuilder out = new StringBuilder();
		out.append(this.getIdFascicolo(true)).append(SEP);
		out.append(this.getAnnoFascicolo(true)).append(SEP);
		out.append(this.getCodIdUfficio(true)).append(SEP);
//		out.append(this.getDescUfficio(true)).append(SEP);
//		out.append(this.getDataStr()).append(SEP);
		out.append(this.getSchema(true)).append(SEP);
		out.append(this.getIdUtente(true)).append(SEP);
		out.append(this.getTipoFascicolo(true));
		return out.toString();
	}
}
