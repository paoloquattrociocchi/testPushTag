package it.giustizia.smp.dto;

import it.giustizia.smp.model.TRelProcBeneAttoAmmId;

import java.util.Date;

public class SchedaAttiAmmDto {

	// TRelProcBeneAttoAmmId
	private TRelProcBeneAttoAmmId id;
	
	private String descRichiesta;
	private String descCognome;		// cognome Amministratore
	private String descNome;		// nome Amministratore
	private String descCodiceFiscale;
	private Date dataSpedizione;  // data Inizio Periodo in T_ISTANZA
	private Date dataPervIst; // data Fine Periodo in T_ISTANZA
	private Date dataDeposito;

	public TRelProcBeneAttoAmmId getId() {
		return id;
	}
	public void setId(TRelProcBeneAttoAmmId id) {
		this.id = id;
	}
	public String getDescRichiesta() {
		return descRichiesta;
	}
	public void setDescRichiesta(String descRichiesta) {
		this.descRichiesta = descRichiesta;
	}
	public String getDescCognome() {
		return descCognome;
	}
	public void setDescCognome(String descCognome) {
		this.descCognome = descCognome;
	}
	public String getDescNome() {
		return descNome;
	}
	public void setDescNome(String descNome) {
		this.descNome = descNome;
	}
	public String getDescCodiceFiscale() {
		return descCodiceFiscale;
	}
	public void setDescCodiceFiscale(String descCodiceFiscale) {
		this.descCodiceFiscale = descCodiceFiscale;
	}
	public Date getDataSpedizione() {
		return dataSpedizione;
	}
	public void setDataSpedizione(Date dataSpedizione) {
		this.dataSpedizione = dataSpedizione;
	}
	public Date getDataPervIst() {
		return dataPervIst;
	}
	public void setDataPervIst(Date dataPervIst) {
		this.dataPervIst = dataPervIst;
	}
	public Date getDataDeposito() {
		return dataDeposito;
	}
	public void setDataDeposito(Date dataDeposito) {
		this.dataDeposito = dataDeposito;
	}

	
}
