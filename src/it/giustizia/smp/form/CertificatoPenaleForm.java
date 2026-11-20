package it.giustizia.smp.form;


public class CertificatoPenaleForm {

	private Long idCertificatoPenale;
	private Long idSoggetto;
	private Short idVersioneSoggetto;
 	private Long idAtto;
 	private String descSoggetto;
 	
	public String getDescSoggetto() {
		return descSoggetto;
	}
	public void setDescSoggetto(String descSoggetto) {
		this.descSoggetto = descSoggetto;
	}
	public Long getIdCertificatoPenale() {
		return idCertificatoPenale;
	}
	public void setIdCertificatoPenale(Long idCertificatoPenale) {
		this.idCertificatoPenale = idCertificatoPenale;
	}
	public Long getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public Long getIdAtto() {
		return idAtto;
	}
	public void setIdAtto(Long idAtto) {
		this.idAtto = idAtto;
	}
	public Short getIdVersioneSoggetto() {
		return idVersioneSoggetto;
	}
	public void setIdVersioneSoggetto(Short idVersioneSoggetto) {
		this.idVersioneSoggetto = idVersioneSoggetto;
	}
	
 	
}
