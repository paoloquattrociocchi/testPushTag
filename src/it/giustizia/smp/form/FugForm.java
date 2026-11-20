package it.giustizia.smp.form;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

public class FugForm extends ProcedimentoForm{
	 	private long   idInvio;
	 	private String descModello;
	 	private String flgEsitoAnnull;
	 	private String statoInvio;
	    @DateTimeFormat(pattern = "dd/MM/yyyy")
		private Date   dataInvio;
		private String descProtocollo;
		private String descProtocolloRif;
		private String descCodiceSezione;
		private String descCampoTipo;
		private String descErroreTipo;
		private String descErrore;
		private String descUtente;
	    @DateTimeFormat(pattern = "dd/MM/yyyy")
		private Date   dataUltAgg;
	    @DateTimeFormat(pattern = "dd/MM/yyyy")
		private Date   dataDefinitaPosizione;
		
		private int		idUfficio;
		private Long  	idProcedimento;
		private Long  	idBene;
		private Long  	idAtto; 
		private String  numeAnnoAtto;
		private String	numeAtto;
		private String	descBene;
		protected ComboForm   categoriaAtto;
        private List<SoggettoForm> 	soggetti;

		private String numeRisorsaEqui;
		private String numeProvvEqui;
		private String descNotifEqui;
		private String descNoteEqui;
		private Character flgDefinitaPosizione;

	// Campi per il filtro
		private boolean  visualizzaFiltro;
		private ComboForm  modelloFugSelected;
	    @DateTimeFormat(pattern = "dd/MM/yyyy")
		private Date   dataInvioDa;
	    @DateTimeFormat(pattern = "dd/MM/yyyy")
		private Date   dataInvioA;
	private String importoEuro;

	public String getImportoEuro() {
		return importoEuro;
	}

	public void setImportoEuro(String importoEuro) {
		this.importoEuro = importoEuro;
	}
				
		public long getIdInvio() {
			return idInvio;
		}
		public void setIdInvio(long idInvio) {
			this.idInvio = idInvio;
		}
		public String getDescModello() {
			return descModello;
		}
		public void setDescModello(String descModello) {
			this.descModello = getString(descModello, getDescModello());
		}
		public Date getDataInvio() {
			return dataInvio;
		}
		public void setDataInvio(Date dataInvio) {
			this.dataInvio = dataInvio;
		}
		public String getDescProtocollo() {
			return descProtocollo;
		}
		public void setDescProtocollo(String descProtocollo) {
			this.descProtocollo = descProtocollo;
		}
		
		public String getDescProtocolloRif() {
			return descProtocolloRif;
		}
		public void setDescProtocolloRif(String descProtocolloRif) {
			this.descProtocolloRif = descProtocolloRif;
		}
		public String getDescCodiceSezione() {
			return descCodiceSezione;
		}
		public void setDescCodiceSezione(String descCodiceSezione) {
			this.descCodiceSezione = descCodiceSezione;
		}
		public String getDescCampoTipo() {
			return descCampoTipo;
		}
		public void setDescCampoTipo(String descCampoTipo) {
			this.descCampoTipo = descCampoTipo;
		}
		public String getDescErroreTipo() {
			return descErroreTipo;
		}
		public void setDescErroreTipo(String descErroreTipo) {
			this.descErroreTipo = descErroreTipo;
		}
		public String getDescErrore() {
			return descErrore;
		}
		public void setDescErrore(String descErrore) {
			this.descErrore = descErrore;
		}
		public String getDescUtente() {
			return descUtente;
		}
		public void setDescUtente(String descUtente) {
			this.descUtente = descUtente;
		}
		public Date getDataUltAgg() {
			return dataUltAgg;
		}
		public void setDataUltAgg(Date dataUltAgg) {
			this.dataUltAgg = dataUltAgg;
		}
		public int getIdUfficio() {
			return idUfficio;
		}
		public void setIdUfficio(int idUfficio) {
			this.idUfficio = idUfficio;
		}
		public Long getIdProcedimento() {
			return idProcedimento;
		}
		public void setIdProcedimento(Long idProcedimento) {
			this.idProcedimento = idProcedimento;
		}
		public Long getIdBene() {
			return idBene;
		}
		public void setIdBene(Long idBene) {
			this.idBene = idBene;
		}
		public Long getIdAtto() {
			return idAtto;
		}
		public void setIdAtto(Long idAtto) {
			this.idAtto = idAtto;
		}
		public String getNumeAnnoAtto() {
			return numeAnnoAtto;
		}
		public void setNumeAnnoAtto(String numeAnnoAtto) {
			this.numeAnnoAtto = numeAnnoAtto;
		}
		public String getNumeAtto() {
			return numeAtto;
		}
		public void setNumeAtto(String numeAtto) {
			this.numeAtto = numeAtto;
		}
		public String getFlgEsitoAnnull() {
			return flgEsitoAnnull;
		}
		public void setFlgEsitoAnnull(String flgEsitoAnnull) {
			this.flgEsitoAnnull = flgEsitoAnnull;
		}
		public String getStatoInvio() {
			return statoInvio;
		}
		public void setStatoInvio(String statoInvio) {
			this.statoInvio = getString(statoInvio, getStatoInvio());
		}
		public String getDescBene() {
			return descBene;
		}
		public void setDescBene(String descBene) {
			this.descBene = descBene;
		}
		public boolean isVisualizzaFiltro() {
			return visualizzaFiltro;
		}
		public void setVisualizzaFiltro(boolean visualizzaFiltro) {
			this.visualizzaFiltro = visualizzaFiltro;
		}
		public ComboForm getModelloFugSelected() {
			return modelloFugSelected;
		}
		public void setModelloFugSelected(ComboForm modelloFugSelected) {
			this.modelloFugSelected = modelloFugSelected;
		}
		public Date getDataInvioDa() {
			return dataInvioDa;
		}
		public void setDataInvioDa(Date dataInvioDa) {
			this.dataInvioDa = dataInvioDa;
		}
		public Date getDataInvioA() {
			return dataInvioA;
		}
		public void setDataInvioA(Date dataInvioA) {
			this.dataInvioA = dataInvioA;
		}
		public ComboForm getCategoriaAtto() {
			return categoriaAtto;
		}
		public void setCategoriaAtto(ComboForm categoriaAtto) {
			this.categoriaAtto = categoriaAtto;
		}
		public List<SoggettoForm> getSoggetti() {
			return soggetti;
		}
		public void setSoggetti(List<SoggettoForm> soggetti) {
			this.soggetti = soggetti;
		}
		public Date getDataDefinitaPosizione() {
			return dataDefinitaPosizione;
		}
		public void setDataDefinitaPosizione(Date dataDefinitaPosizione) {
			this.dataDefinitaPosizione = dataDefinitaPosizione;
		}
		public String getNumeRisorsaEqui() {
			return numeRisorsaEqui;
		}
		public void setNumeRisorsaEqui(String numeRisorsaEqui) {
			this.numeRisorsaEqui = numeRisorsaEqui;
		}
		public String getNumeProvvEqui() {
			return numeProvvEqui;
		}
		public void setNumeProvvEqui(String numeProvvEqui) {
			this.numeProvvEqui = numeProvvEqui;
		}
		public String getDescNotifEqui() {
			return descNotifEqui;
		}
		public void setDescNotifEqui(String descNotifEqui) {
			this.descNotifEqui = descNotifEqui;
		}
		public String getDescNoteEqui() {
			return descNoteEqui;
		}
		public void setDescNoteEqui(String descNoteEqui) {
			this.descNoteEqui = descNoteEqui;
		}
		public Character getFlgDefinitaPosizione() {
			return flgDefinitaPosizione;
		}
		public void setFlgDefinitaPosizione(Character flgDefinitaPosizione) {
			this.flgDefinitaPosizione = flgDefinitaPosizione;
		}
		
		private String getString(String valore, String def) {
			return ((valore != null) && (!valore.equals("null"))) ? valore : def;
		}

}
