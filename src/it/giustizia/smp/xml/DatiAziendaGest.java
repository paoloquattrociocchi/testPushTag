package it.giustizia.smp.xml;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class DatiAziendaGest extends DatiGest {

	private Boolean trascrizioneSequestro;
	private Date dataTrascrizioneSequestro;
	private String descStatoAttivitaAziendale;
	private String prospettivaProsecuzioneAttivita;
	private String descDestinazioneImpresa;
	private String tipoOrganoAmministrativo;
	private String nominativoAmministratore;
	private Date dataNominaAmministratore;
	private String nominativoRappresentanteLegale;
	private Date dataNominaRappresentante;
	private String liquidazione;
	private Date dataLiquidazione;
	private String nominativoLiquidatore;
	private String tipoOrganoControllo;
	private String revisioneConti;
	private String nominativoRevisore;
	private Date dataUltimoBilancioApprovato;
	private BigDecimal saldoUltimoBilancio;
	private String luogoScrittureContabili;
	private String nominativaDetentoreScritture;
	private BigInteger numeroDipendenti;
	private String ccnl;
	private String tipoContrattoLavoro;
	private Boolean ammortizzatoriSociali;
	private String descAmmortizzatoreSociale;
	private Boolean contenziosi;
	private Boolean rispettoNormeSicurezza;
	private Boolean lavoratoriStranieri;
	private Boolean addettiIrregolari;
	private Boolean lavoratoriFormazione;
	private String posizioneSocio;

	public Boolean getTrascrizioneSequestro() {
		return trascrizioneSequestro;
	}

	public void setTrascrizioneSequestro(Boolean trascrizioneSequestro) {
		this.trascrizioneSequestro = trascrizioneSequestro;
	}

	public Date getDataTrascrizioneSequestro() {
		return dataTrascrizioneSequestro;
	}

	public void setDataTrascrizioneSequestro(Date dataTrascrizioneSequestro) {
		this.dataTrascrizioneSequestro = dataTrascrizioneSequestro;
	}

	public String getDescStatoAttivitaAziendale() {
		return descStatoAttivitaAziendale;
	}

	public void setDescStatoAttivitaAziendale(String descStatoAttivitaAziendale) {
		this.descStatoAttivitaAziendale = descStatoAttivitaAziendale;
	}

	public String getProspettivaProsecuzioneAttivita() {
		return prospettivaProsecuzioneAttivita;
	}

	public void setProspettivaProsecuzioneAttivita(String prospettivaProsecuzioneAttivita) {
		this.prospettivaProsecuzioneAttivita = prospettivaProsecuzioneAttivita;
	}

	public String getDescDestinazioneImpresa() {
		return descDestinazioneImpresa;
	}

	public void setDescDestinazioneImpresa(String descDestinazioneImpresa) {
		this.descDestinazioneImpresa = descDestinazioneImpresa;
	}

	public String getDescTipoOrdineAmministrativo() {
		return tipoOrganoAmministrativo;
	}

	public void setDescTipoOrdineAmministrativo(String tipoOrganoAmministrativo) {
		this.tipoOrganoAmministrativo = tipoOrganoAmministrativo;
	}

	public String getNominativoAmministratore() {
		return nominativoAmministratore;
	}

	public void setNominativoAmministratore(String nominativoAmministratore) {
		this.nominativoAmministratore = nominativoAmministratore;
	}

	public Date getDataNominaAmministratore() {
		return dataNominaAmministratore;
	}

	public void setDataNominaAmministratore(Date dataNominaAmministratore) {
		this.dataNominaAmministratore = dataNominaAmministratore;
	}

	public String getNominativoRappresentanteLegale() {
		return nominativoRappresentanteLegale;
	}

	public void setNominativoRappresentanteLegale(String nominativoRappresentanteLegale) {
		this.nominativoRappresentanteLegale = nominativoRappresentanteLegale;
	}

	public Date getDataNominaRappresentante() {
		return dataNominaRappresentante;
	}

	public void setDataNominaRappresentante(Date dataNominaRappresentante) {
		this.dataNominaRappresentante = dataNominaRappresentante;
	}

	public String getLiquidazione() {
		return liquidazione;
	}

	public void setLiquidazione(String liquidazione) {
		this.liquidazione = liquidazione;
	}

	public Date getDataLiquidazione() {
		return dataLiquidazione;
	}

	public void setDataLiquidazione(Date dataLiquidazione) {
		this.dataLiquidazione = dataLiquidazione;
	}

	public String getNominativoLiquidatore() {
		return nominativoLiquidatore;
	}

	public void setNominativoLiquidatore(String nominativoLiquidatore) {
		this.nominativoLiquidatore = nominativoLiquidatore;
	}

	public String getTipoOrdineControllo() {
		return tipoOrganoControllo;
	}

	public void setTipoOrdineControllo(String tipoOrganoControllo) {
		this.tipoOrganoControllo = tipoOrganoControllo;
	}

	public String getRevisioneConti() {
		return revisioneConti;
	}

	public void setRevisioneConti(String revisioneConti) {
		this.revisioneConti = revisioneConti;
	}

	public String getNominativoRevisore() {
		return nominativoRevisore;
	}

	public void setNominativoRevisore(String nominativoRevisore) {
		this.nominativoRevisore = nominativoRevisore;
	}

	public Date getDataUltimoBilancioApprovato() {
		return dataUltimoBilancioApprovato;
	}

	public void setDataUltimoBilancioApprovato(Date dataUltimoBilancioApprovato) {
		this.dataUltimoBilancioApprovato = dataUltimoBilancioApprovato;
	}

	public BigDecimal getSaldoUltimoBilancio() {
		return saldoUltimoBilancio;
	}

	public void setSaldoUltimoBilancio(BigDecimal saldoUltimoBilancio) {
		this.saldoUltimoBilancio = saldoUltimoBilancio;
	}

	public String getLuogoScrittureContabili() {
		return luogoScrittureContabili;
	}

	public void setLuogoScrittureContabili(String luogoScrittureContabili) {
		this.luogoScrittureContabili = luogoScrittureContabili;
	}

	public String getNominativaDetentoreScritture() {
		return nominativaDetentoreScritture;
	}

	public void setNominativaDetentoreScritture(String nominativaDetentoreScritture) {
		this.nominativaDetentoreScritture = nominativaDetentoreScritture;
	}

	public BigInteger getNumeroDipendenti() {
		return numeroDipendenti;
	}

	public void setNumeroDipendenti(BigInteger numeroDipendenti) {
		this.numeroDipendenti = numeroDipendenti;
	}

	public String getCcnl() {
		return ccnl;
	}

	public void setCcnl(String ccnl) {
		this.ccnl = ccnl;
	}

	public String getTipoContrattoLavoro() {
		return tipoContrattoLavoro;
	}

	public void setTipoContrattoLavoro(String tipoContrattoLavoro) {
		this.tipoContrattoLavoro = tipoContrattoLavoro;
	}

	public Boolean getAmmortizzatoriSociali() {
		return ammortizzatoriSociali;
	}

	public void setAmmortizzatoriSociali(Boolean ammortizzatoriSociali) {
		this.ammortizzatoriSociali = ammortizzatoriSociali;
	}

	public String getDescAmmortizzatoreSociale() {
		return descAmmortizzatoreSociale;
	}

	public void setDescAmmortizzatoreSociale(String descAmmortizzatoreSociale) {
		this.descAmmortizzatoreSociale = descAmmortizzatoreSociale;
	}

	public Boolean getContenziosi() {
		return contenziosi;
	}

	public void setContenziosi(Boolean contenziosi) {
		this.contenziosi = contenziosi;
	}

	public Boolean getRispettoNormeSicurezza() {
		return rispettoNormeSicurezza;
	}

	public void setRispettoNormeSicurezza(Boolean rispettoNormeSicurezza) {
		this.rispettoNormeSicurezza = rispettoNormeSicurezza;
	}

	public Boolean getLavoratoriStranieri() {
		return lavoratoriStranieri;
	}

	public void setLavoratoriStranieri(Boolean lavoratoriStranieri) {
		this.lavoratoriStranieri = lavoratoriStranieri;
	}

	public Boolean getAddettiIrregolari() {
		return addettiIrregolari;
	}

	public void setAddettiIrregolari(Boolean addettiIrregolari) {
		this.addettiIrregolari = addettiIrregolari;
	}

	public Boolean getLavoratoriFormazione() {
		return lavoratoriFormazione;
	}

	public void setLavoratoriFormazione(Boolean lavoratoriFormazione) {
		this.lavoratoriFormazione = lavoratoriFormazione;
	}

	public String getPosizioneSocio() {
		return posizioneSocio;
	}

	public void setPosizioneSocio(String posizioneSocio) {
		this.posizioneSocio = posizioneSocio;
	}
}
