package it.giustizia.smp.form;

import it.giustizia.smp.validation.date.ModernDate;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;


public class UfficiForm {

	private int codiKIdUfficio;
    private ComboForm	itemTipoUfficio;
    private ComboForm	itemComuneSede;
    private ComboForm	itemDistretto;
    private ComboForm	itemTribunale;
//	private String descTipoUfficio;
//	private String descSedeUfficio;
	private String descIndirizzoSede;
	private String descUffGiudiz;
	private String flgTribMp;
	private String flgDda;
	private String descUfficio;
	private String descCfPu;
	private String descPubblUff;
	private String flgMP;
	private String flgDNA;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date  dataFineValidita;
	
	public int getCodiKIdUfficio() {
		return codiKIdUfficio;
	}
	public void setCodiKIdUfficio(int codiKIdUfficio) {
		this.codiKIdUfficio = codiKIdUfficio;
	}
	public ComboForm getItemTipoUfficio() {
		return itemTipoUfficio;
	}
	public void setItemTipoUfficio(ComboForm itemTipoUfficio) {
		this.itemTipoUfficio = itemTipoUfficio;
	}
	public ComboForm getItemComuneSede() {
		return itemComuneSede;
	}
	public void setItemComuneSede(ComboForm itemComuneSede) {
		this.itemComuneSede = itemComuneSede;
	}
	public ComboForm getItemDistretto() {
		return itemDistretto;
	}
	public void setItemDistretto(ComboForm itemDistretto) {
		this.itemDistretto = itemDistretto;
	}
	public String getDescIndirizzoSede() {
		return descIndirizzoSede;
	}
	public void setDescIndirizzoSede(String descIndirizzoSede) {
		this.descIndirizzoSede = descIndirizzoSede;
	}
	public String getDescUffGiudiz() {
		return descUffGiudiz;
	}
	public void setDescUffGiudiz(String descUffGiudiz) {
		this.descUffGiudiz = descUffGiudiz;
	}
	public String getFlgTribMp() {
		return flgTribMp;
	}
	public void setFlgTribMp(String flgTribMp) {
		this.flgTribMp = flgTribMp;
	}
	public String getFlgDda() {
		return flgDda;
	}
	public void setFlgDda(String flgDda) {
		this.flgDda = flgDda;
	}
	public String getDescUfficio() {
		return descUfficio;
	}
	public void setDescUfficio(String descUfficio) {
		this.descUfficio = descUfficio;
	}
	public String getDescCfPu() {
		return descCfPu;
	}
	public void setDescCfPu(String descCfPu) {
		this.descCfPu = descCfPu;
	}
	public String getDescPubblUff() {
		return descPubblUff;
	}
	public void setDescPubblUff(String descPubblUff) {
		this.descPubblUff = descPubblUff;
	}
	public ComboForm getItemTribunale() {
		return itemTribunale;
	}
	public void setItemTribunale(ComboForm itemTribunale) {
		this.itemTribunale = itemTribunale;
	}
	public String getFlgDNA() {
		return flgDNA;
	}
	public void setFlgDNA(String flgDNA) {
		this.flgDNA = flgDNA;
	}
	public String getFlgMP() {
		return flgMP;
	}
	public void setFlgMP(String flgMP) {
		this.flgMP = flgMP;
	}
	public Date getDataFineValidita() {
		return dataFineValidita;
	}
	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}

	
}
