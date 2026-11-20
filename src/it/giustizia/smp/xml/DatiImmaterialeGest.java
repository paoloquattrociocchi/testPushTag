package it.giustizia.smp.xml;

import java.util.Date;

public class DatiImmaterialeGest extends DatiGest {

	private Boolean trascrizioneSequestro;
	private Date dataTrascrizioneSequestro;
	private String descCustode;

	public Boolean getTrascrizioneSequestro() {
		return trascrizioneSequestro;
	}

	public void setTrascrizioneSequestro(Boolean trascrizioneSequestro) {
		this.trascrizioneSequestro = trascrizioneSequestro;
	}

	public Date getDataTrascrizioneSequestro() {
		return dataTrascrizioneSequestro;
	}

	public void setDataTrascrizioneSequestro(Date dataTrascrizioneSequestro) {
		this.dataTrascrizioneSequestro = dataTrascrizioneSequestro;
	}

	public String getDescCustode() {
		return descCustode;
	}

	public void setDescCustode(String descCustode) {
		this.descCustode = descCustode;
	}
}
