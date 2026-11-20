package it.giustizia.smp.form.conservatorie;

import it.giustizia.smp.form.ComboForm;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

/**
 * ConservatoriaForm
 * 
 * @author Almaviva S.p.A.
 *         classe form per la gestione della conservatoria
 */
public class ComunicazioneRicercaForm {


	private String notaSelezionata;
	
	
    private   ComboForm      tipoNota;
    private   ComboForm      categorieAtti;
  
    @NumberFormat(style = Style.NUMBER, pattern = "####")
    private Short          anno;
    private Long  dalNumero;
    private Long alNumero;
    
    private   ComboForm      conservatoria;
    
    private boolean tipoPersona;
    private String cognome;
    private String nome;
    private String denominazione;
    
	public String getNotaSelezionata()
	{
		return notaSelezionata;
	}
	public void setNotaSelezionata(String notaSelezionata)
	{
		this.notaSelezionata = notaSelezionata;
	}
	public ComboForm getCategorieAtti() {
		return categorieAtti;
	}
	public void setCategorieAtti(ComboForm categorieAtti) {
		this.categorieAtti = categorieAtti;
	}
	public Short getAnno() {
		return anno;
	}
	public void setAnno(Short anno) {
		this.anno = anno;
	}
	public Long getDalNumero() {
		return dalNumero;
	}
	public void setDalNumero(Long dalNumero) {
		this.dalNumero = dalNumero;
	}
	public Long getAlNumero() {
		return alNumero;
	}
	public void setAlNumero(Long alNumero) {
		this.alNumero = alNumero;
	}
	public ComboForm getConservatoria() {
		return conservatoria;
	}
	public void setConservatoria(ComboForm conservatoria) {
		this.conservatoria = conservatoria;
	}
	public boolean isTipoPersona() {
		return tipoPersona;
	}
	public void setTipoPersona(boolean tipoPersona) {
		this.tipoPersona = tipoPersona;
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
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public ComboForm getTipoNota()
    {
	    return tipoNota;
    }
	public void setTipoNota( ComboForm tipoNota )
    {
	    this.tipoNota = tipoNota;
    }
}
