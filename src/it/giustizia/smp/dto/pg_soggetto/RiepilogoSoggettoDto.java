package it.giustizia.smp.dto.pg_soggetto;

import it.giustizia.smp.model.TRelProcSogint;

import java.util.List;

public class RiepilogoSoggettoDto {

	private DettaglioSoggettoDto procedimentoMerito;
	private List<DettaglioSoggettoDto> procedimentiAggravamento;
	private List<DettaglioSoggettoDto> procedimentiCamera;
	private TRelProcSogint dettaglioSoggetto;
	
	public DettaglioSoggettoDto getProcedimentoMerito() {
		return procedimentoMerito;
	}
	public void setProcedimentoMerito(DettaglioSoggettoDto procedimentoMerito) {
		this.procedimentoMerito = procedimentoMerito;
	}
	public List<DettaglioSoggettoDto> getProcedimentiAggravamento() {
		return procedimentiAggravamento;
	}
	public void setProcedimentiAggravamento(List<DettaglioSoggettoDto> procedimentiAggravamento) {
		this.procedimentiAggravamento = procedimentiAggravamento;
	}
	public TRelProcSogint getDettaglioSoggetto() {
		return dettaglioSoggetto;
	}
	public void setDettaglioSoggetto(TRelProcSogint dettaglioSoggetto) {
		this.dettaglioSoggetto = dettaglioSoggetto;
	}
	public List<DettaglioSoggettoDto> getProcedimentiCamera() {
		return procedimentiCamera;
	}
	public void setProcedimentiCamera(List<DettaglioSoggettoDto> procedimentiCamera) {
		this.procedimentiCamera = procedimentiCamera;
	}
	
}
