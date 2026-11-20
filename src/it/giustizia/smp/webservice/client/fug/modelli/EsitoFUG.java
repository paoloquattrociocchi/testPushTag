package it.giustizia.smp.webservice.client.fug.modelli;

public class EsitoFUG {

//private String esitoAnnullamento;
	private String codiceSezione;
	private String codiceCampo; // campoTipo;
	private String codiceErrore; //descErroreTipo;
	private String descrizioneErrore; //descErrore;

	private String getValidString(String in){
		return (in != null) ? in : "";
	}

	public String getCodiceSezione() {
		return getValidString(codiceSezione);
	}
	public void setCodiceSezione(String codiceSezione) {
		this.codiceSezione = codiceSezione;
	}
	public String getCodiceCampo() {
		return getValidString(codiceCampo);
	}
	public void setCodiceCampo(String codiceCampo) {
		this.codiceCampo = codiceCampo;
	}
	public String getCodiceErrore() {
		return getValidString(codiceErrore);
	}
	public void setCodiceErrore(String codiceErrore) {
		this.codiceErrore = codiceErrore;
	}
	public String getDescrizioneErrore() {
		return getValidString(descrizioneErrore);
	}
	public void setDescrizioneErrore(String descrizioneErrore) {
		this.descrizioneErrore = descrizioneErrore;
	}

}
