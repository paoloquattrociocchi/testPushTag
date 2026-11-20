package it.giustizia.smp.form;

import it.giustizia.smp.validation.date.ModernDate;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class ComunicazioneForm  {
	private Long idComunicazione;
	private Long idSoggetto;
 	private Long idAtto;
	private Long idAttoEsecutivo;
	private Date dataDefinitivita;
	private String flagStatoInvioComunicazione;
	private String descInvioComunicazione;
	private String descSoggetto;
	private Long idMisuraPersonale;
	
	private Boolean hasModuloElettorale;
	
	public Boolean getHasModuloElettorale() {
		return hasModuloElettorale;
	}
	public void setHasModuloElettorale(Boolean hasModuloElettorale) {
		this.hasModuloElettorale = hasModuloElettorale;
	}
	public Long getIdMisuraPersonale() {
		return idMisuraPersonale;
	}
	public void setIdMisuraPersonale(Long idMisura) {
		this.idMisuraPersonale = idMisura;
	}
	public String getDescSoggetto() {
		return descSoggetto;
	}
	public void setDescSoggetto(String descSoggetto) {
		this.descSoggetto = descSoggetto;
	}
	public String getDescInvioComunicazione() {
		return descInvioComunicazione;
	}
	public void setDescInvioComunicazione(String descInvioComunicazione) {
		this.descInvioComunicazione = descInvioComunicazione;
	}
	@ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataRedazioneScheda;
	
	
	public Date getDataRedazioneScheda() {
		return dataRedazioneScheda;
	}
	public void setDataRedazioneScheda(Date dataRedazioneScheda) {
		this.dataRedazioneScheda = dataRedazioneScheda;
	}
	private Date dataInvio;
	public Date getDataInvio() {
		return dataInvio;
	}
	public void setDataInvio(Date dataInvio) {
		this.dataInvio = dataInvio;
	}
	
	public String getFlagStatoInvioComunicazione() {
		return flagStatoInvioComunicazione;
	}
	public void setFlagStatoInvioComunicazione(String flagStatoInvioComunicazione) {
		this.flagStatoInvioComunicazione = flagStatoInvioComunicazione;
	}
	public Date getDataDefinitivita() {
		return dataDefinitivita;
	}
	public void setDataDefinitivita(Date dataDefinitivita) {
		this.dataDefinitivita = dataDefinitivita;
	}
	public Long getIdAttoEsecutivo() {
		return idAttoEsecutivo;
	}
	public void setIdAttoEsecutivo(Long idAttoEsecutivo) {
		this.idAttoEsecutivo = idAttoEsecutivo;
	}
	public Long getIdComunicazione() {
		return idComunicazione;
	}
	public void setIdComunicazione(Long idComunicazione) {
		this.idComunicazione = idComunicazione;
	}
	public Long getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	
	public Long getIdAtto() {
		return idAtto;
	}
	public void setIdAtto(Long idAtto) {
		this.idAtto = idAtto;
	}
	
	
}
