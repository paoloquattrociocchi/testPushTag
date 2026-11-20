package it.giustizia.smp.dto.pg_soggetto;

import it.giustizia.smp.dto.pg_procedimento.MisurePatrSoggDto;
import it.giustizia.smp.dto.pg_procedimento.MisurePersSoggDto;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.SoggettoForm;

import java.util.List;

public class DettaglioSoggettoDto {

	private SoggettoForm soggetto;
	private ProcedimentoForm procedimento;
	private List<MisurePersSoggDto> misurePersonali;
	private List<MisurePersSoggDto> cauzioniSoggetto;
	private List<MisurePersSoggDto> art67Soggetto;
	private List<MisurePatrSoggDto> misurePatrimoniali;
	
	public ProcedimentoForm getProcedimento() {
		return procedimento;
	}
	public void setProcedimento(ProcedimentoForm procedimento) {
		this.procedimento = procedimento;
	}
	public List<MisurePersSoggDto> getMisurePersonali() {
		return misurePersonali;
	}
	public void setMisurePersonali(List<MisurePersSoggDto> misurePersonali) {
		this.misurePersonali = misurePersonali;
	}
	public List<MisurePersSoggDto> getCauzioniSoggetto() {
		return cauzioniSoggetto;
	}
	public void setCauzioniSoggetto(List<MisurePersSoggDto> cauzioniSoggetto) {
		this.cauzioniSoggetto = cauzioniSoggetto;
	}
	public List<MisurePersSoggDto> getArt67Soggetto() {
		return art67Soggetto;
	}
	public void setArt67Soggetto(List<MisurePersSoggDto> art67Soggetto) {
		this.art67Soggetto = art67Soggetto;
	}
	public List<MisurePatrSoggDto> getMisurePatrimoniali() {
		return misurePatrimoniali;
	}
	public void setMisurePatrimoniali(List<MisurePatrSoggDto> misurePatrimoniali) {
		this.misurePatrimoniali = misurePatrimoniali;
	}
	public SoggettoForm getSoggetto() {
		return soggetto;
	}
	public void setSoggetto(SoggettoForm soggetto) {
		this.soggetto = soggetto;
	}

	
}
