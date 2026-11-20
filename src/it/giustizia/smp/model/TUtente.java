package it.giustizia.smp.model;


public class TUtente{

    private char     attivo;
    private String   codiceFiscale;
    private String   cognome;
    private String   nome;
    private String   password;
    private TTpProfilo TTpProfilo;
    private String   userid;
    
    
    public char getAttivo() {
		return attivo;
	}
	public void setAttivo(char attivo) {
		this.attivo = attivo;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public TTpProfilo getTTpProfilo() {
		return TTpProfilo;
	}
	public void setTTpProfilo(TTpProfilo tTpProfilo) {
		TTpProfilo = tTpProfilo;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	


}
