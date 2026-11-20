package it.giustizia.smp.form.conservatorie;

import it.giustizia.smp.validation.date.ModernDate;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

/**
 * ConservatoriaForm
 * 
 * @author Almaviva S.p.A.
 *         classe form per la gestione della conservatoria
 */
public class ComunicazioneListaForm {

	private Long    idNota;
	private Integer idUffOwner;
    private String  descTipoCatAtto;
    private String  repertorio;
    private String  annoNumero;
    private String  statoComunicazione;
    private String  tipoComunicazione;
    private String  descConservatoria;
    private Integer codConservatoria;
    private Long progrNota;
    @ModernDate
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date	dataComunicazione;
    
    
    
	
	public String getDescConservatoria() {
		return descConservatoria;
	}
	public void setDescConservatoria(String descConservatoria) {
		this.descConservatoria = descConservatoria;
	}
	public Integer getCodConservatoria() {
		return codConservatoria;
	}
	public void setCodConservatoria(Integer codConservatoria) {
		this.codConservatoria = codConservatoria;
	}
	public Long getIdNota() {
		return idNota;
	}
	public void setIdNota(Long idNota) {
		this.idNota = idNota;
	}
	public Integer getIdUffOwner() {
		return idUffOwner;
	}
	public void setIdUffOwner(Integer idUffOwner) {
		this.idUffOwner = idUffOwner;
	}
	public String getDescTipoCatAtto() {
		return descTipoCatAtto;
	}
	public void setDescTipoCatAtto(String descTipoCatAtto) {
		this.descTipoCatAtto = descTipoCatAtto;
	}
	public String getRepertorio() {
		return repertorio;
	}
	public void setRepertorio(String repertorio) {
		this.repertorio = repertorio;
	}
	public String getStatoComunicazione() {
		return statoComunicazione;
	}
	public void setStatoComunicazione(String statoComunicazione) {
		this.statoComunicazione = statoComunicazione;
	}
	public String getTipoComunicazione() {
		return tipoComunicazione;
	}
	public void setTipoComunicazione(String tipoComunicazione) {
		this.tipoComunicazione = tipoComunicazione;
	}
	public Date getDataComunicazione() {
		return dataComunicazione;
	}
	public void setDataComunicazione(Date dataComunicazione) {
		this.dataComunicazione = dataComunicazione;
	}
	public Long getProgrNota() {
		return progrNota;
	}
	public void setProgrNota(Long progrNota) {
		this.progrNota = progrNota;
	}
    public String getAnnoNumero()
    {
        return this.annoNumero;
    }
    public void setAnnoNumero( String annoNumero )
    {
        this.annoNumero = annoNumero;
    }
    
    
}
