package it.giustizia.smp.webservice.client.fug.modelli;

import it.giustizia.smp.webservice.client.fug.DatiIdentificativiInvioTipo;
import it.giustizia.smp.webservice.client.fug.DatiRegistroFUGTipo;
import it.giustizia.smp.webservice.client.fug.DatiRisorsaTipo;
import it.giustizia.smp.webservice.client.fug.FlagParzialeTipo;
import it.giustizia.smp.webservice.client.fug.IdRicevutaTipo;
import it.giustizia.smp.webservice.client.fug.InfoProcessoTipo;
import it.giustizia.smp.webservice.client.fug.ProtocolloRicevutaTipo;
import it.giustizia.smp.webservice.client.fug.TipoRegistroFUGTipo;
import it.giustizia.smp.webservice.client.fug.TipoRegistroTipo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public abstract class ModelloBase {

	// Dati identificativo invio
	private String codUffMittente;
	private String cancelliere;

	// Dati risorsa
	private String codiceOperatore;
	private String tipoRisorsa;
	private String numeroRisorsa;
	private String abi;
	private String cab;
	
	private String tipoRegistro;
	private String numeroRegistro;
	private String annoRegistro;
	private String importo;
	private String divisa;
	
	private String flagParziale = "T"; // valori ammessi: P, T (predefinito)

	// Dati protocoolo ricevuta Equitalia
	private String idProtocollo;
	private String idRisorsa;
	private String idProvvedimento;
	private String idNotifica;

	public static String FIXED_VALUE = "valore prefissato";
	private static String NON_CONFORME = " non conforme alle specifiche";
	private static int COD_UFF_MITTENTE_LEN = 11;
	private static int CANCELLIERE_LEN = 16;
	private static int ABI_CAB_LEN = 5;
	protected static int MAX_NUM_REGISTRO_LEN_LEN = 7;
	public static String GGMMAAAA = "ddMMyyyy";

	private String checkCodUffMittente(String codUffMittente) {
		return checkCodUffMittente(codUffMittente, "");
	}

	private String checkCodUffMittente(String in, String e) {
		if (!StringUtils.isNumeric(in) || (in.length() != COD_UFF_MITTENTE_LEN))
			throw new IllegalArgumentException("Codice ufficio " + e + "mittente specificato ('" + in + "')" + NON_CONFORME);
		return in;
	}

	public String getIdProtocollo() {
		return idProtocollo;
	}

	public void setIdProtocollo(String idProtocollo) {
		this.idProtocollo = idProtocollo;
	}

	public String getIdRisorsa() {
		return idRisorsa;
	}

	public void setIdRisorsa(String idRisorsa) {
		this.idRisorsa = idRisorsa;
	}

	public String getIdProvvedimento() {
		return idProvvedimento;
	}

	public void setIdProvvedimento(String idProvvedimento) {
		this.idProvvedimento = idProvvedimento;
	}

	public String getIdNotifica() {
		return idNotifica;
	}

	public void setIdNotifica(String idNotifica) {
		this.idNotifica = idNotifica;
	}

	private IdRicevutaTipo getIdRicevutaTipo(){
		IdRicevutaTipo out = new IdRicevutaTipo();
		out.setIdRisorsa(getIdRisorsa());
		out.setIdProvvedimento(getIdProvvedimento());
		out.setIdNotifica(getIdNotifica());
		return out;
	}

	public ProtocolloRicevutaTipo getProtocolloRicevutaModello(){
		ProtocolloRicevutaTipo out = new ProtocolloRicevutaTipo();
		out.setIdProtocollo(getIdProtocollo());
		out.setIdRicevuta(getIdRicevutaTipo());
		return out;
	}

	public String getCodUffMittente() {
		return checkCodUffMittente(this.codUffMittente);
	}

	public void setCodUffMittente(String codUffMittente) {
		this.codUffMittente = checkCodUffMittente(codUffMittente);
	}

	private String checkCancelliere(String cancelliere) {
		if (cancelliere.length() != CANCELLIERE_LEN)
			throw new IllegalArgumentException("iIdentificativo cancelliere specificato ('" + cancelliere + "')" + NON_CONFORME);
		return cancelliere;
	}

	public String getCancelliere() {
		return checkCancelliere(this.cancelliere);
	}

	public void setCancelliere(String cancelliere) {
		this.cancelliere = checkCancelliere(cancelliere);
	}

	public String getCodiceOperatore() {
		return codiceOperatore;
	}

	public void setCodiceOperatore(String codiceOperatore) {
		this.codiceOperatore = codiceOperatore;
	}

	public String getTipoRisorsa() {
		return tipoRisorsa;
	}

	public void setTipoRisorsa(String tipoRisorsa) {
		this.tipoRisorsa = tipoRisorsa;
	}

	public String getNumeroRisorsa() {
		return numeroRisorsa;
	}

	public void setNumeroRisorsa(String numeroRisorsa) {
		this.numeroRisorsa = numeroRisorsa;
	}

	private String checkAbiCab(String in, String cod) {
		String codice = StringUtils.leftPad(StringUtils.defaultString(in).trim(), ABI_CAB_LEN, '0');
		if (!StringUtils.isNumeric(codice)) 
			throw new IllegalArgumentException("Codice '" + cod + "' specificato ('" + in + "')" + NON_CONFORME);
		return codice;
	}

	public String getAbi() {
		return checkAbiCab(this.abi, "ABI");
	}

	public void setAbi(String abi) {
		this.abi = checkAbiCab(abi, "ABI");
	}

	public String getCab() {
		return checkAbiCab(this.cab, "CAB");
	}

	public void setCab(String cab) {
		this.cab = checkAbiCab(cab, "CAB");
	}

	private String checkTipoRegistro(String tipoRegistro) {
		try{
			TipoRegistroTipo.fromValue(tipoRegistro);
		} catch(IllegalArgumentException e) {
			StringBuffer msg = new StringBuffer("Parametro '" + tipoRegistro + "' non riconosciuto. Valori ammessi: ");
			boolean primo = true;
			for (TipoRegistroTipo c: TipoRegistroTipo.values()) {
				msg.append(!primo ? ", '" : "'").append(c.value()).append("'");
				primo = false;
			}
			throw new IllegalArgumentException(msg.toString());
		}
		return tipoRegistro;
	}

	public String getTipoRegistro() {
		return checkTipoRegistro(this.tipoRegistro);
	}

	public void setTipoRegistro(String tipoRegistro) {
		this.tipoRegistro = checkTipoRegistro(tipoRegistro);
	}

	private String checkNumeroRegistro(String numeroRegistro) {
		return checkNumeroRegistro(numeroRegistro, "");
	}

	public String checkNumeroRegistro(String in, String fug) {
		if (!StringUtils.isNumeric(in) || (in.length() < 1) || (in.length() > MAX_NUM_REGISTRO_LEN_LEN) )
			throw new IllegalArgumentException("Numero registro" + fug + " specificato ('" + in + "')" + NON_CONFORME);
		return in;
	}

	public String getNumeroRegistro() {
		return checkNumeroRegistro(this.numeroRegistro);
	}

	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = checkNumeroRegistro(numeroRegistro);
	}

	public String getAnnoRegistro() {
		return annoRegistro;
	}

	public void setAnnoRegistro(String annoRegistro) {
		this.annoRegistro = annoRegistro;
	}

	public String getImporto() {
		return importo;
	}

	public void setImporto(String importo) {
		this.importo = importo;
	}

	public String getDivisa() {
		return divisa;
	}

	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}

	protected String checkFlagParziale(String flagParziale) {
		String vs = getValidUpperCaseString(flagParziale);
		if (!vs.equals(FlagParzialeTipo.P.value()) && !vs.equals(FlagParzialeTipo.T.value()))
			throw new IllegalArgumentException("Valore del campo 'FlagParziale' non valido ('" + vs + "'). Valori ammessi: 'T', 'P'");
		return vs;
	}

	public String getFlagParziale() {
		return checkFlagParziale(this.flagParziale);
	}

	public void setFlagParziale(String flagParziale) {
		this.flagParziale = checkFlagParziale(flagParziale);
	}

	protected FlagParzialeTipo getFlagParzialeTipo() {
		return FlagParzialeTipo.valueOf(getFlagParziale());
}

	protected String getValidUpperCaseString(String in){
		return (in != null) ? in.trim().toUpperCase() : "";
	}

	public String getTipoRegistroFUG() {return TipoRegistroFUGTipo.RFUG_1.value();};
	public int getNumeroRegistroFUG() {return 0;};
	public int getAnnoRegistroFUG() {return 1900;};
	public String getCodUffEmittente() {return "";};
	
	public static String getData(Date in){
		return getData(in, GGMMAAAA, "01011990");
	}

	public static String getData(Date in, String format, String def){
		String date = def;
		if (in != null){
			SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(format);
			date = DATE_FORMAT.format(in);
		}
		return date;
	}

	protected DatiIdentificativiInvioTipo getDatiIdentificativiInvioTipo() {
		DatiIdentificativiInvioTipo out = new DatiIdentificativiInvioTipo();
		out.setCodUffMittente(getCodUffMittente());
		out.setCancelliere(getCancelliere());
		return out;
	}

	protected DatiRisorsaTipo getDatiRisorsaTipo() {
		DatiRisorsaTipo out = new DatiRisorsaTipo();
		out.setCodiceOperatore(getCodiceOperatore());
		out.setTipoRisorsa(getTipoRisorsa());
		out.setNumeroRisorsa(getNumeroRisorsa());
		out.setABI(getAbi());
		out.setCAB(getCab());
		return out;
	}

	protected DatiRegistroFUGTipo getDatiRegistroFUGTipo() {
		DatiRegistroFUGTipo out = new DatiRegistroFUGTipo();
		out.setTipoRegistroFUG(TipoRegistroFUGTipo.fromValue(getTipoRegistroFUG()));

       if(!"0".equalsIgnoreCase(String.valueOf(getNumeroRegistroFUG()) )){
		   out.setNumeroRegistroFUG(String.valueOf(getNumeroRegistroFUG()));
       }else{
    	   out.setNumeroRegistroFUG(null);
       }
		
	
		if(!"1900".equalsIgnoreCase(String.valueOf(getAnnoRegistroFUG()) )){
		   out.setAnnoRegistroFUG(String.valueOf(getAnnoRegistroFUG()));
		}else{
	    	   out.setAnnoRegistroFUG(null);
	      }
			
		
		return out;
	}

	protected InfoProcessoTipo getInfoProcessoTipo() {
		InfoProcessoTipo out = new InfoProcessoTipo();
		out.setCodUffEmittente(getCodUffEmittente());
		out.setTipoRegistro(TipoRegistroTipo.fromValue(getTipoRegistro()));
		out.setNumeroRegistro(getNumeroRegistro());
		out.setAnnoRegistro(getAnnoRegistro());
		return out;
	}
}
