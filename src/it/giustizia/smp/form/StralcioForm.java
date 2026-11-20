package it.giustizia.smp.form;

import java.util.List;


/**
 * StralcioForm
 * 
 * @author Almaviva S.p.A.
 *         classe form per la gestione dello stralcio
 */
public class StralcioForm extends AttoForm {

	//FILTRI
    protected ComboForm            	soggettiDiretti;
    protected ComboForm            	soggettiTitolari;
    protected ComboForm            	tipoMisure;
    //filtri del bene
    protected ComboForm            	tipologiaBene;
    protected ComboForm            	categoriaBene;
    protected ComboForm            	sottoCategoriaBene;
    
    
//    protected Long         			idProcedimento;
//    protected Long         			idUffOwner;
    protected Character    			flagSoggettoValido;
   
    private List<SoggettoStralcioForm> 	listaSoggettoStralcioForm;
    
    protected ComboForm            	magistrato;
    protected String           		dataStralcio;
    
    
    public String getDataStralcio() {
		return dataStralcio;
	}
	public void setDataStralcio(String dataStralcio) {
		this.dataStralcio = dataStralcio;
	}
	public List<SoggettoStralcioForm> getListaSoggettoStralcioForm() {
		return listaSoggettoStralcioForm;
	}
	public void setListaSoggettoStralcioForm(
			List<SoggettoStralcioForm> listaSoggettoStralcioForm) {
		this.listaSoggettoStralcioForm = listaSoggettoStralcioForm;
	}
//	public Long getIdProcedimento() {
//		return idProcedimento;
//	}
//	public void setIdProcedimento(Long idProcedimento) {
//		this.idProcedimento = idProcedimento;
//	}
	public ComboForm getSoggettiDiretti() {
		return soggettiDiretti;
	}
	public void setSoggettiDiretti(ComboForm soggettiDiretti) {
		this.soggettiDiretti = soggettiDiretti;
	}
	public ComboForm getSoggettiTitolari() {
		return soggettiTitolari;
	}
	public void setSoggettiTitolari(ComboForm soggettiTitolari) {
		this.soggettiTitolari = soggettiTitolari;
	}
	public ComboForm getTipoMisure() {
		return tipoMisure;
	}
	public void setTipoMisure(ComboForm tipoMisure) {
		this.tipoMisure = tipoMisure;
	}
	public ComboForm getTipologiaBene() {
		return tipologiaBene;
	}
	public void setTipologiaBene(ComboForm tipologiaBene) {
		this.tipologiaBene = tipologiaBene;
	}
	public ComboForm getCategoriaBene() {
		return categoriaBene;
	}
	public void setCategoriaBene(ComboForm categoriaBene) {
		this.categoriaBene = categoriaBene;
	}
	public ComboForm getSottoCategoriaBene() {
		return sottoCategoriaBene;
	}
	public void setSottoCategoriaBene(ComboForm sottoCategoriaBene) {
		this.sottoCategoriaBene = sottoCategoriaBene;
	}
//	public String getTipoMisura() {
//		return tipoMisura;
//	}
//	public void setTipoMisura(String tipoMisura) {
//		this.tipoMisura = tipoMisura;
//	}
//	public Long getIdUffOwner() {
//		return idUffOwner;
//	}
//	public void setIdUffOwner(Long idUffOwner) {
//		this.idUffOwner = idUffOwner;
//	}
	public Character getFlagSoggettoValido() {
		return flagSoggettoValido;
	}
	public void setFlagSoggettoValido(Character flagSoggettoValido) {
		this.flagSoggettoValido = flagSoggettoValido;
	}
	public ComboForm getMagistrato() {
		return magistrato;
	}
	public void setMagistrato(ComboForm magistrato) {
		this.magistrato = magistrato;
	}
	
}
