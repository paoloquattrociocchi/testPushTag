package it.giustizia.smp.form;

public class OmonimoForm   {

	String idNSC; 
	String datiSoggetto;
	String codiceFiscale;
	String deceduto;
	String tipo;
	String paternita;
	String maternita;
	

	public OmonimoForm() {
		super();
	}
	
	
	public OmonimoForm(String idNSC, String datiSoggetto, String codiceFiscale, String deceduto) {
		super();
		this.idNSC = idNSC;
		this.datiSoggetto = datiSoggetto;
		this.codiceFiscale = codiceFiscale;
		this.deceduto = deceduto;
	}


	public OmonimoForm(String idNSC, String datiSoggetto, String codiceFiscale) {
		super();
		this.idNSC = idNSC;
		this.datiSoggetto = datiSoggetto;
		this.codiceFiscale = codiceFiscale;
	}
	
	
	
	
	public String getPaternita() {
		return paternita;
	}


	public void setPaternita(String paternita) {
		this.paternita = paternita;
	}


	public String getMaternita() {
		return maternita;
	}


	public void setMaternita(String maternita) {
		this.maternita = maternita;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDeceduto() {
		return deceduto;
	}
	public void setDeceduto(String deceduto) {
		this.deceduto = deceduto;
	}
	public String getIdNSC() {
		return idNSC;
	}
	public void setIdNSC(String idNSC) {
		this.idNSC = idNSC;
	}
	public String getDatiSoggetto() {
		return datiSoggetto;
	}
	public void setDatiSoggetto(String datiSoggetto) {
		this.datiSoggetto = datiSoggetto;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
		
	
}
