package it.giustizia.smp.form;

public class ProfiloForm {

	private String	citta;
	private String	commonName;
	private boolean	DDA;
	private String	descrizioneUfficio;
	private String	distretto;
	private int		idProfilo;
	private long	idTipoUfficio;
	private String	indirizzoUfficio;
	private String	profiloString;
	private String	ruolo;
	private String  descDettaglioUfficio;

	/**
	 * @return the citta
	 */
	public String getCitta() {

		return citta;
	}

	/**
	 * @return the commonName
	 */
	public String getCommonName() {

		return commonName;
	}

	/**
	 * @return the descrizioneUfficio
	 */
	public String getDescrizioneUfficio() {

		return descrizioneUfficio;
	}

	/**
	 * @return the distretto
	 */
	public String getDistretto() {

		return distretto;
	}

	public int getIdProfilo() {

		return idProfilo;
	}

	/**
	 * @return the idTipoUfficio
	 */
	public long getIdTipoUfficio() {

		return idTipoUfficio;
	}

	public String getIndirizzoUfficio() {

		return indirizzoUfficio;
	}

	public String getProfiloString() {

		if (profiloString != null) {
			return profiloString;
		}
		return distretto + "|" + citta + "|" + descrizioneUfficio + "|" + ruolo + "|" + commonName;
	}

	/**
	 * @return the ruolo
	 */
	public String getRuolo() {

		return ruolo;
	}

	public boolean isDDA() {

		return DDA;
	}

	/**
	 * @param citta
	 *            the citta to set
	 */
	public void setCitta(String citta) {

		this.citta = citta;
	}

	/**
	 * @param commonName
	 *            the commonName to set
	 */
	public void setCommonName(String commonName) {

		this.commonName = commonName;
	}

	public void setDDA(boolean dDA) {

		DDA = dDA;
	}

	/**
	 * @param descrizioneUfficio
	 *            the descrizioneUfficio to set
	 */
	public void setDescrizioneUfficio(String descrizioneUfficio) {

		this.descrizioneUfficio = descrizioneUfficio;
	}

	/**
	 * @param distretto
	 *            the distretto to set
	 */
	public void setDistretto(String distretto) {

		this.distretto = distretto;
	}

	public void setIdProfilo(int idProfilo) {

		this.idProfilo = idProfilo;
	}

	/**
	 * @param idTipoUfficio
	 *            the idTipoUfficio to set
	 */
	public void setIdTipoUfficio(long idTipoUfficio) {

		this.idTipoUfficio = idTipoUfficio;
	}

	public void setIndirizzoUfficio(String indirizzoUfficio) {

		this.indirizzoUfficio = indirizzoUfficio;
	}

	public void setProfiloString(String profiloString) {

		this.profiloString = profiloString;
	}

	/**
	 * @param ruolo
	 *            the ruolo to set
	 */
	public void setRuolo(String ruolo) {

		this.ruolo = ruolo;
	}
	
	public String getDescDettaglioUfficio() {
		return descDettaglioUfficio;
	}

	public void setDescDettaglioUfficio(String descDettaglioUfficio) {
		this.descDettaglioUfficio = descDettaglioUfficio;
	}
}
