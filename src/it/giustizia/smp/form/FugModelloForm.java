package it.giustizia.smp.form;

import it.giustizia.smp.webservice.client.fug.BeneficiariTipo;

import java.util.List;

public class FugModelloForm extends FugForm{
	
	private String codUffMittente;
	
	private ComboForm     tipologia;
	private ComboForm     categoria;
	private ComboForm     sottocategoria;
	
	private String descModello;
	private String descProtocollo;
	
	private String cancelliere;
	private String codiceOperatore;
	private String tipoRisorsa;
	private String numeroRisorsa;
	private String abi;
	private String cab;
	private String numeroRegistro;
	private String annoRegistro;
	private String importo;
	private String divisa;
	private List<BeneficiariTipo> beneficiari;
	private String richiestaXML;
	private String esitoXML;
	
	public String getCodUffMittente() {
		return codUffMittente;
	}
	public void setCodUffMittente(String codUffMittente) {
		this.codUffMittente = codUffMittente;
	}
	public ComboForm getTipologia() {
		return tipologia;
	}
	public void setTipologia(ComboForm tipologia) {
		this.tipologia = tipologia;
	}
	public ComboForm getCategoria() {
		return categoria;
	}
	public void setCategoria(ComboForm categoria) {
		this.categoria = categoria;
	}
	public ComboForm getSottocategoria() {
		return sottocategoria;
	}
	public void setSottocategoria(ComboForm sottocategoria) {
		this.sottocategoria = sottocategoria;
	}
	public String getDescModello() {
		return descModello;
	}
	public void setDescModello(String descModello) {
		this.descModello = descModello;
	}
	public String getDescProtocollo() {
		return descProtocollo;
	}
	public void setDescProtocollo(String descProtocollo) {
		this.descProtocollo = descProtocollo;
	}
	public String getCancelliere() {
		return cancelliere;
	}
	public void setCancelliere(String cancelliere) {
		this.cancelliere = cancelliere;
	}
	public String getCodiceOperatore() {
		return codiceOperatore;
	}
	public void setCodiceOperatore(String codiceOperatore) {
		this.codiceOperatore = codiceOperatore;
	}
	public String getTipoRisorsa() {
		return tipoRisorsa;
	}
	public void setTipoRisorsa(String tipoRisorsa) {
		this.tipoRisorsa = tipoRisorsa;
	}
	public String getNumeroRisorsa() {
		return numeroRisorsa;
	}
	public void setNumeroRisorsa(String numeroRisorsa) {
		this.numeroRisorsa = numeroRisorsa;
	}
	public String getAbi() {
		return abi;
	}
	public void setAbi(String abi) {
		this.abi = abi;
	}
	public String getCab() {
		return cab;
	}
	public void setCab(String cab) {
		this.cab = cab;
	}
	public String getNumeroRegistro() {
		return numeroRegistro;
	}
	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}
	public String getAnnoRegistro() {
		return annoRegistro;
	}
	public void setAnnoRegistro(String annoRegistro) {
		this.annoRegistro = annoRegistro;
	}
	public String getImporto() {
		return importo;
	}
	public void setImporto(String importo) {
		this.importo = importo;
	}
	public String getDivisa() {
		return divisa;
	}
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}
	public List<BeneficiariTipo> getBeneficiari() {
		return beneficiari;
	}
	public void setBeneficiari(List<BeneficiariTipo> beneficiari) {
		this.beneficiari = beneficiari;
	}
	public String getRichiestaXML() {
		return richiestaXML;
	}
	public void setRichiestaXML(String richiestaXML) {
		this.richiestaXML = richiestaXML;
	}
	public String getEsitoXML() {
		return esitoXML;
	}
	public void setEsitoXML(String esitoXML) {
		this.esitoXML = esitoXML;
	}

}
