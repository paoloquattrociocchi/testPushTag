package it.giustizia.smp.xml;

public class Denaro extends DenaroBase {

	private String descrizioneFiliale;
	private String descrizioneDirettore;
	private String numeroRegistrazioneEnte;

	public String getDescrizioneFiliale() {
		return descrizioneFiliale;
	}

	public void setDescrizioneFiliale(String descrizioneFiliale) {
		this.descrizioneFiliale = descrizioneFiliale;
	}

	public String getDescrizioneDirettore() {
		return descrizioneDirettore;
	}

	public void setDescrizioneDirettore(String descrizioneDirettore) {
		this.descrizioneDirettore = descrizioneDirettore;
	}

	public String getNumeroRegistrazioneEnte() {
		return numeroRegistrazioneEnte;
	}

	public void setNumeroRegistrazioneEnte(String numeroRegistrazioneEnte) {
		this.numeroRegistrazioneEnte = numeroRegistrazioneEnte;
	}

}
