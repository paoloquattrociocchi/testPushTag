package it.giustizia.smp.form;

import it.giustizia.smp.utility.Constants;



import javax.validation.constraints.Pattern;

public class AmministratoreAlboForm {

	
	private int         idAmministratore;
    @Pattern(regexp = Constants.RegExp.COGNOME)
    private String      cognome;
    
    @Pattern(regexp = Constants.RegExp.NOME)
    private String      nome;
    
    private String      codiceFiscale;   
	private String      sezione;
	private String		dataNascita;
	private String   	luogoNascita;
	private String 		dataIscrizione;
	private String      sospeso;
	private String      descrSezione;
	private ComboForm	alboProfessionale;
	private ComboForm	ambitoTerritoriale;
	private ComboForm	provinciaDomProf;
	private ComboForm   comuneDomProf;
	
	
	/**
	 * @return the idAmministratore
	 */
	public int getIdAmministratore() {
		return idAmministratore;
	}
	/**
	 * @param idAmministratore the idAmministratore to set
	 */
	public void setIdAmministratore(int idAmministratore) {
		this.idAmministratore = idAmministratore;
	}
	/**
	 * @return the cognome
	 */
	public String getCognome() {
		return cognome;
	}
	/**
	 * @param cognome the cognome to set
	 */
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	/**
	 * @return the codiceFiscale
	 */
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	/**
	 * @param codiceFiscale the codiceFiscale to set
	 */
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	/**
	 * @return the sezione
	 */
	public String getSezione() {
		return sezione;
	}
	/**
	 * @param sezione the sezione to set
	 */
	public void setSezione(String sezione) {
		this.sezione = sezione;
	}
	/**
	 * @return the dataNascita
	 */
	public String getDataNascita() {
		return dataNascita;
	}
	/**
	 * @param dataNascita the dataNascita to set
	 */
	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}
	/**
	 * @return the luogoNascita
	 */
	public String getLuogoNascita() {
		return luogoNascita;
	}
	/**
	 * @param luogoNascita the luogoNascita to set
	 */
	public void setLuogoNascita(String luogoNascita) {
		this.luogoNascita = luogoNascita;
	}
	/**
	 * @return the dataIscrizione
	 */
	public String getDataIscrizione() {
		return dataIscrizione;
	}
	/**
	 * @param dataIscrizione the dataIscrizione to set
	 */
	public void setDataIscrizione(String dataIscrizione) {
		this.dataIscrizione = dataIscrizione;
	}
	/**
	 * @return the sospeso
	 */
	public String getSospeso() {
		return sospeso;
	}
	/**
	 * @param sospeso the sospeso to set
	 */
	public void setSospeso(String sospeso) {
		this.sospeso = sospeso;
	}
	/**
	 * @return the descrSezione
	 */
	public String getDescrSezione() {
		return descrSezione;
	}
	/**
	 * @param descrSezione the descrSezione to set
	 */
	public void setDescrSezione(String descrSezione) {
		this.descrSezione = descrSezione;
	}
	/**
	 * @return the alboProfessionale
	 */
	public ComboForm getAlboProfessionale() {
		return alboProfessionale;
	}
	/**
	 * @param alboProfessionale the alboProfessionale to set
	 */
	public void setAlboProfessionale(ComboForm alboProfessionale) {
		this.alboProfessionale = alboProfessionale;
	}
	/**
	 * @return the ambitoTerritoriale
	 */
	public ComboForm getAmbitoTerritoriale() {
		return ambitoTerritoriale;
	}
	/**
	 * @param ambitoTerritoriale the ambitoTerritoriale to set
	 */
	public void setAmbitoTerritoriale(ComboForm ambitoTerritoriale) {
		this.ambitoTerritoriale = ambitoTerritoriale;
	}
	/**
	 * @return the provinciaDomProf
	 */
	public ComboForm getProvinciaDomProf() {
		return provinciaDomProf;
	}
	/**
	 * @param provinciaDomProf the provinciaDomProf to set
	 */
	public void setProvinciaDomProf(ComboForm provinciaDomProf) {
		this.provinciaDomProf = provinciaDomProf;
	}
	/**
	 * @return the comuneDomProf
	 */
	public ComboForm getComuneDomProf() {
		return comuneDomProf;
	}
	/**
	 * @param comuneDomProf the comuneDomProf to set
	 */
	public void setComuneDomProf(ComboForm comuneDomProf) {
		this.comuneDomProf = comuneDomProf;
	}
  
}
