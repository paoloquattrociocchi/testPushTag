package it.giustizia.smp.form;


/**
 * SoggettoForm
 * 
 * @author Almaviva S.p.A.
 *         classe form per la gestione del soggetto
 */
public class SoggettoTitolareForm extends SoggettoForm {

	private String 					  descrizioneFrazioneOQuota;
	private String 					  descCatAtto;
    
	public void setDescrizioneFrazioneOQuota(String descrizioneFrazioneOQuota) {
		this.descrizioneFrazioneOQuota = descrizioneFrazioneOQuota;
	}

	public String getDescCatAtto() {
		return descCatAtto;
	}

	public void setDescCatAtto(String descCatAtto) {
		this.descCatAtto = descCatAtto;
	}
	
    
}
