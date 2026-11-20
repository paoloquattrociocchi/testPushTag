package it.giustizia.smp.dto.pg_procedimento;

import java.util.Date;

import it.giustizia.smp.model.TRelAttoMisPers;

public class MisurePersSoggDto {

    private TRelAttoMisPers relAttoMisPers;
	private Short annoAtto;
	private Long numeroAtto;
	private String richiesta;
	
	public TRelAttoMisPers getRelAttoMisPers() {
		return relAttoMisPers;
	}
	public void setRelAttoMisPers(TRelAttoMisPers relAttoMisPers) {
		this.relAttoMisPers = relAttoMisPers;
	}
	public Short getAnnoAtto() {
		return annoAtto;
	}
	public void setAnnoAtto(Short annoAtto) {
		this.annoAtto = annoAtto;
	}
	public Long getNumeroAtto() {
		return numeroAtto;
	}
	public void setNumeroAtto(Long numeroAtto) {
		this.numeroAtto = numeroAtto;
	}
	public String getRichiesta() {
		return richiesta;
	}
	public void setRichiesta(String richiesta) {
		this.richiesta = richiesta;
	}
	
	
}
    
    