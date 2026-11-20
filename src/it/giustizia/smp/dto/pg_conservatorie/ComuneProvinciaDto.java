package it.giustizia.smp.dto.pg_conservatorie;

public class ComuneProvinciaDto
{
	private String	  chiaveCombo;
    private Long      idComune;
	private String    COMUNE;
    private String    PROVINCIA;
    private String    codiceCatasto;

	public ComuneProvinciaDto()
	{}

    public Long getIdComune() {
		return idComune;
	}

	public void setIdComune(Long idComune) {
		this.idComune = idComune;
	}

	
	public String getCOMUNE() {
		return COMUNE;
	}

	public void setCOMUNE(String cOMUNE) {
		COMUNE = cOMUNE;
	}

	public String getPROVINCIA() {
		return PROVINCIA;
	}

	public void setPROVINCIA(String pROVINCIA) {
		PROVINCIA = pROVINCIA;
	}

	public String getCodiceCatasto() {
		return codiceCatasto;
	}

	public void setCodiceCatasto(String codiceCatasto) {
		this.codiceCatasto = codiceCatasto;
	}

	public String getChiaveCombo() {
		return chiaveCombo;
	}

	public void setChiaveCombo(String chiaveCombo) {
		this.chiaveCombo = chiaveCombo;
	}
	
	

    
}
