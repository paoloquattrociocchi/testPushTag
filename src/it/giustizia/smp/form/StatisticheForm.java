package it.giustizia.smp.form;

import it.giustizia.smp.model.TStampa;
import it.giustizia.smp.validation.date.ModernDate;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;


/**
 * StatisticheForm
 * 
 * @author Almaviva S.p.A.
 *         classe form per la gestione delle statistiche
 */
public class StatisticheForm implements Serializable {
	
	private static final long serialVersionUID = -1759245562648129442L;
	
	private int idStampa;
	private List<TStampa> 	listaStampe;
	
	@ModernDate
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date filtroDataDal;
	
	@ModernDate
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date filtroDataAl;

	private ComboForm magistrato;
	
	private ComboForm anno;
	
	private ComboForm semestre;
	
	private ComboForm procedente;
	
	private ComboForm filtro1;
	
	private ComboForm filtro2;
	
	private ComboForm filtro3;
	
	
	
	
	public ComboForm getFiltro1() {
		return filtro1;
	}

	public void setFiltro1(ComboForm filtro1) {
		this.filtro1 = filtro1;
	}

	public ComboForm getFiltro2() {
		return filtro2;
	}

	public void setFiltro2(ComboForm filtro2) {
		this.filtro2 = filtro2;
	}

	public ComboForm getFiltro3() {
		return filtro3;
	}

	public void setFiltro3(ComboForm filtro3) {
		this.filtro3 = filtro3;
	}

	public ComboForm getMagistrato() {
		return magistrato;
	}

	public void setMagistrato(ComboForm magistrato) {
		this.magistrato = magistrato;
	}

	public ComboForm getAnno() {
		return anno;
	}

	public void setAnno(ComboForm anno) {
		this.anno = anno;
	}

	public ComboForm getSemestre() {
		return semestre;
	}

	public void setSemestre(ComboForm semestre) {
		this.semestre = semestre;
	}

	public ComboForm getProcedente() {
		return procedente;
	}

	public void setProcedente(ComboForm procedente) {
		this.procedente = procedente;
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

	public int getIdStampa() {
		return idStampa;
	}

	public void setIdStampa(int idStampa) {
		this.idStampa = idStampa;
	}

	public List<TStampa> getListaStampe() {
		return listaStampe;
	}

	public void setListaStampe(List<TStampa> listaStampe) {
		this.listaStampe = listaStampe;
	}
    
	
}
