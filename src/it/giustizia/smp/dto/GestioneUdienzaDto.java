package it.giustizia.smp.dto;


public class GestioneUdienzaDto {
	
	private static final long DEFAULT_VALUE =0;
	
	private long codUdienzaFiss=DEFAULT_VALUE;
	private long codUdienzaInFaseDiRip=DEFAULT_VALUE;
    private long codUdienzaInFaseDiRim=DEFAULT_VALUE;
    private long statoUdienzaPrec=DEFAULT_VALUE;
    
    private String urlRitorno;

	public GestioneUdienzaDto() {
	}

	public long getCodUdienzaFiss() {
		return codUdienzaFiss;
	}

	public void setCodUdienzaFiss(long codUdienzaFiss) {
		this.codUdienzaFiss = codUdienzaFiss;
	}

	public long getCodUdienzaInFaseDiRip() {
		return codUdienzaInFaseDiRip;
	}

	public void setCodUdienzaInFaseDiRip(long codUdienzaInFaseDiRip) {
		this.codUdienzaInFaseDiRip = codUdienzaInFaseDiRip;
	}

	public long getStatoUdienzaPrec() {
		return statoUdienzaPrec;
	}

	public void setStatoUdienzaPrec(long statoUdienzaPrec) {
		this.statoUdienzaPrec = statoUdienzaPrec;
	}

	public long getCodUdienzaInFaseDiRim() {
		return codUdienzaInFaseDiRim;
	}

	public void setCodUdienzaInFaseDiRim(long codUdienzaInFaseDiRim) {
		this.codUdienzaInFaseDiRim = codUdienzaInFaseDiRim;
	}

	public String getUrlRitorno() {
		return urlRitorno;
	}

	public void setUrlRitorno(String urlRitorno) {
		this.urlRitorno = urlRitorno;
	}


}

