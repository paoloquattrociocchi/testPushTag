package it.giustizia.smp.form;

import it.giustizia.smp.model.TStampa;
import it.giustizia.smp.model.VNotifiche;
import it.giustizia.smp.validation.date.ModernDate;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;


/**
 * NotificheEComunicazioniForm
 * 
 * @author Almaviva S.p.A.
 *         classe form per la gestione delle voce di menu notifiche e comunicazioni
 */
public class NotificheEComunicazioniForm  {

	private Long         idNotifica;
	
	@NumberFormat(style = Style.NUMBER )
	private Long         numProcedimento;
    @NumberFormat(style = Style.NUMBER, pattern = "####")
    private Short          annoProcedimento;
	
	private List<VNotifiche> listaNotifiche ;
	
	@ModernDate
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date filtroDataDal;
	
	@ModernDate
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date filtroDataAl;

	private ComboForm tipoDestinatario;

	
	private Long         numProcedimentoSel;
    private Short        annoProcedimentoSel;
    private Long         codAttoSel;
    
    
	 

	public Long getCodAttoSel() {
		return codAttoSel;
	}

	public void setCodAttoSel(Long codAttoSel) {
		this.codAttoSel = codAttoSel;
	}

	public Long getNumProcedimentoSel() {
		return numProcedimentoSel;
	}

	public void setNumProcedimentoSel(Long numProcedimentoSel) {
		this.numProcedimentoSel = numProcedimentoSel;
	}

	public Short getAnnoProcedimentoSel() {
		return annoProcedimentoSel;
	}

	public void setAnnoProcedimentoSel(Short annoProcedimentoSel) {
		this.annoProcedimentoSel = annoProcedimentoSel;
	}

	public Long getIdNotifica() {
		return idNotifica;
	}

	public void setIdNotifica(Long idNotifica) {
		this.idNotifica = idNotifica;
	}

	public Long getNumProcedimento() {
		return numProcedimento;
	}

	public void setNumProcedimento(Long numProcedimento) {
		this.numProcedimento = numProcedimento;
	}

	public Short getAnnoProcedimento() {
		return annoProcedimento;
	}

	public void setAnnoProcedimento(Short annoProcedimento) {
		this.annoProcedimento = annoProcedimento;
	}

	public ComboForm getTipoDestinatario() {
		return tipoDestinatario;
	}

	public void setTipoDestinatario(ComboForm tipoDestinatario) {
		this.tipoDestinatario = tipoDestinatario;
	}

	public List<VNotifiche> getListaNotifiche() {
		return listaNotifiche;
	}

	public void setListaNotifiche(List<VNotifiche> listaNotifiche) {
		this.listaNotifiche = listaNotifiche;
	}

	public Date getFiltroDataDal() {
		return filtroDataDal;
	}

	public void setFiltroDataDal(Date filtroDataDal) {
		this.filtroDataDal = filtroDataDal;
	}

	public Date getFiltroDataAl() {
		return filtroDataAl;
	}

	public void setFiltroDataAl(Date filtroDataAl) {
		this.filtroDataAl = filtroDataAl;
	}

	
}
