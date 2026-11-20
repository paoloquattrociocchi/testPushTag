package it.giustizia.smp.form;

import it.giustizia.smp.dto.pg_notifiche.DestinatarioDto;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;


/**
 * AttoForm
 * 
 * @author Almaviva S.p.A.
 *         classe form per la gestione degli atti del procedimento
 */

public class NotificaAttoForm extends AttoForm {

	@NotEmpty
	String oggetto;
	@NotEmpty
	String testo;
	
	Boolean flgDatiSensibili; 
	
	Boolean hasAmministratori = false;
	

	List<DestinatarioDto> listaDestinatari = new ArrayList<DestinatarioDto>();
	Long numDocumentoCopia;
	private ComboForm  tipoDestinatario = new ComboForm();
	private ComboForm  comuneDestinatario = new ComboForm();
	private ComboForm  provinciaDestinatario = new ComboForm();
	private ComboForm  ufficioDestinatario = new ComboForm();
	
	List<String> listaIdCheckboxDestinatari = new ArrayList<String>();
	
	List<NotificaDettaglioForm> listaNotifiche = new ArrayList<NotificaDettaglioForm>();
	
	Long idAttoRicevuta;
	Long idNotifAmm;
	
	
	Integer idUffRicevuta;
	 
	public Integer getIdUffRicevuta() {
		return idUffRicevuta;
	}
	public void setIdUffRicevuta(Integer idUffRicevuta) {
		this.idUffRicevuta = idUffRicevuta;
	}
	public Long getIdAttoRicevuta() {
		return idAttoRicevuta;
	}
	public void setIdAttoRicevuta(Long idAttoRicevuta) {
		this.idAttoRicevuta = idAttoRicevuta;
	}
	public List<NotificaDettaglioForm> getListaNotifiche() {
		return listaNotifiche;
	}
	public void setListaNotifiche(List<NotificaDettaglioForm> listaNotifiche) {
		this.listaNotifiche = listaNotifiche;
	}
	public ComboForm getUfficioDestinatario() {
		return ufficioDestinatario;
	}
	public void setUfficioDestinatario(ComboForm ufficioDestinatario) {
		this.ufficioDestinatario = ufficioDestinatario;
	}
	public ComboForm getComuneDestinatario() {
		return comuneDestinatario;
	}
	public void setComuneDestinatario(ComboForm comuneDestinatario) {
		this.comuneDestinatario = comuneDestinatario;
	}
	public ComboForm getProvinciaDestinatario() {
		return provinciaDestinatario;
	}
	public void setProvinciaDestinatario(ComboForm provinciaDestinatario) {
		this.provinciaDestinatario = provinciaDestinatario;
	}
	public ComboForm getTipoDestinatario() {
		return tipoDestinatario;
	}
	public void setTipoDestinatario(ComboForm tipoDestinatario) {
		this.tipoDestinatario = tipoDestinatario;
	}
	public List<String> getListaIdCheckboxDestinatari() {
		return listaIdCheckboxDestinatari;
	}
	public void setListaIdCheckboxDestinatari(List<String> listaIdCheckboxDestinatari) {
		this.listaIdCheckboxDestinatari = listaIdCheckboxDestinatari;
	}
	public Long getNumDocumentoCopia() {
		return numDocumentoCopia;
	}
	public void setNumDocumentoCopia(Long numDocumentoCopia) {
		this.numDocumentoCopia = numDocumentoCopia;
	}
	public Boolean getFlgDatiSensibili() {
		return flgDatiSensibili;
	}
	public void setFlgDatiSensibili(Boolean flgDatiSensibili) {
		this.flgDatiSensibili = flgDatiSensibili;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public String getTesto() {
		return testo;
	}
	public void setTesto(String testo) {
		this.testo = testo;
	}
	public List<DestinatarioDto> getListaDestinatari() {
		return listaDestinatari;
	}
	public void setListaDestinatari(List<DestinatarioDto> listaDestinatari) {
		this.listaDestinatari = listaDestinatari;
	}
	public Boolean getHasAmministratori() {
		return hasAmministratori;
	}
	public void setHasAmministratori(Boolean hasAmministratori) {
		this.hasAmministratori = hasAmministratori;
	}
	public Long getIdNotifAmm() {
		return idNotifAmm;
	}
	public void setIdNotifAmm(Long idNotifAmm) {
		this.idNotifAmm = idNotifAmm;
	}
	
}
