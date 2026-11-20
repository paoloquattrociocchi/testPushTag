package it.giustizia.smp.xml;

import it.giustizia.smp.xml.amministratori.DatiAziendaGestType;

public class DatiBeneAziendaGest extends DatiAziendaGestType{
	 
	public Boolean getAddettiIrregolari() {
		return super.isAddettiIrregolari();
	}
	
	 
	public Boolean getContenziosi() {
		return super.isContenziosi();
	}
	
	public Boolean getDirittiTerzi() {
		return super.isDirittiTerzi();
	}
	
	public Boolean getLavoratoriFormazione() {
		return super.isLavoratoriFormazione();
	}
	
	public Boolean getLavoratoriStranieri() {
		return super.isLavoratoriStranieri();
	}
	
	public Boolean getRispettoNormeSicurezza() {
		return super.isRispettoNormeSicurezza();
	}
	

	public Boolean getTrascrizioneSequestro() {
		return super.isTrascrizioneSequestro();
	}
	
	
/*
    private BigDecimal stima;
    private XMLGregorianCalendar dataStima;
    private Boolean dirittiTerzi;
    private String descDirittiTerzi;
    private Boolean trascrizioneSequestro;
    private XMLGregorianCalendar dataTrascrizioneSequestro;
    private String descStatoAttivitaAziendale;
    private String prospettivaProsecuzioneAttivita;
    private String descDestinazioneImpresa;
    private String tipoOrganoAmministrativo;
    private String nominativoAmministratore;
    private XMLGregorianCalendar dataNominaAmministratore;
    private String nominativoRappresentanteLegale;
    private XMLGregorianCalendar dataNominaRappresentante;
    private String liquidazioneSociale;
    private XMLGregorianCalendar dataLiquidazione;
    private String nominativoLiquidatore;
    private String tipoOrganoControllo;
    private String revisioneLegaleConti;
    private String nominativoRevisore;
    private XMLGregorianCalendar dataUltimoBilancioApprovato;
    private BigDecimal saldoUltimoBilancio;
    private String luogoScrittureContabili;
    private String nominativaDetentoreScritture;
    private BigInteger numeroDipendenti;
    private String ccnl;
    private String tipoContrattoLavoro;
    private String ammortizzatoreSociale;
    private Boolean contenziosi;
    private Boolean rispettoNormeSicurezza;
    private Boolean lavoratoriStranieri;
    private Boolean addettiIrregolari;
    private Boolean lavoratoriFormazione;
    private String posizioneSocio;
	public BigDecimal getStima() {
		return stima;
	}
	public XMLGregorianCalendar getDataStima() {
		return dataStima;
	}
	public Boolean getDirittiTerzi() {
		return dirittiTerzi;
	}
	public String getDescDirittiTerzi() {
		return descDirittiTerzi;
	}
	public Boolean getTrascrizioneSequestro() {
		return trascrizioneSequestro;
	}
	public XMLGregorianCalendar getDataTrascrizioneSequestro() {
		return dataTrascrizioneSequestro;
	}
	public String getDescStatoAttivitaAziendale() {
		return descStatoAttivitaAziendale;
	}
	public String getProspettivaProsecuzioneAttivita() {
		return prospettivaProsecuzioneAttivita;
	}
	public String getDescDestinazioneImpresa() {
		return descDestinazioneImpresa;
	}
	public String getTipoOrganoAmministrativo() {
		return tipoOrganoAmministrativo;
	}
	public String getNominativoAmministratore() {
		return nominativoAmministratore;
	}
	public XMLGregorianCalendar getDataNominaAmministratore() {
		return dataNominaAmministratore;
	}
	public String getNominativoRappresentanteLegale() {
		return nominativoRappresentanteLegale;
	}
	public XMLGregorianCalendar getDataNominaRappresentante() {
		return dataNominaRappresentante;
	}
	public String getLiquidazioneSociale() {
		return liquidazioneSociale;
	}
	public XMLGregorianCalendar getDataLiquidazione() {
		return dataLiquidazione;
	}
	public String getNominativoLiquidatore() {
		return nominativoLiquidatore;
	}
	public String getTipoOrganoControllo() {
		return tipoOrganoControllo;
	}
	public String getRevisioneLegaleConti() {
		return revisioneLegaleConti;
	}
	public String getNominativoRevisore() {
		return nominativoRevisore;
	}
	public XMLGregorianCalendar getDataUltimoBilancioApprovato() {
		return dataUltimoBilancioApprovato;
	}
	public BigDecimal getSaldoUltimoBilancio() {
		return saldoUltimoBilancio;
	}
	public String getLuogoScrittureContabili() {
		return luogoScrittureContabili;
	}
	public String getNominativaDetentoreScritture() {
		return nominativaDetentoreScritture;
	}
	public BigInteger getNumeroDipendenti() {
		return numeroDipendenti;
	}
	public String getCcnl() {
		return ccnl;
	}
	public String getTipoContrattoLavoro() {
		return tipoContrattoLavoro;
	}
	public String getAmmortizzatoreSociale() {
		return ammortizzatoreSociale;
	}
	public Boolean getContenziosi() {
		return contenziosi;
	}
	public Boolean getRispettoNormeSicurezza() {
		return rispettoNormeSicurezza;
	}
	public Boolean getLavoratoriStranieri() {
		return lavoratoriStranieri;
	}
	public Boolean getAddettiIrregolari() {
		return addettiIrregolari;
	}
	public Boolean getLavoratoriFormazione() {
		return lavoratoriFormazione;
	}
	public String getPosizioneSocio() {
		return posizioneSocio;
	}
	public void setStima(BigDecimal stima) {
		this.stima = stima;
	}
	public void setDataStima(XMLGregorianCalendar dataStima) {
		this.dataStima = dataStima;
	}
	public void setDirittiTerzi(Boolean dirittiTerzi) {
		this.dirittiTerzi = dirittiTerzi;
	}
	public void setDescDirittiTerzi(String descDirittiTerzi) {
		this.descDirittiTerzi = descDirittiTerzi;
	}
	public void setTrascrizioneSequestro(Boolean trascrizioneSequestro) {
		this.trascrizioneSequestro = trascrizioneSequestro;
	}
	public void setDataTrascrizioneSequestro(
			XMLGregorianCalendar dataTrascrizioneSequestro) {
		this.dataTrascrizioneSequestro = dataTrascrizioneSequestro;
	}
	public void setDescStatoAttivitaAziendale(String descStatoAttivitaAziendale) {
		this.descStatoAttivitaAziendale = descStatoAttivitaAziendale;
	}
	public void setProspettivaProsecuzioneAttivita(
			String prospettivaProsecuzioneAttivita) {
		this.prospettivaProsecuzioneAttivita = prospettivaProsecuzioneAttivita;
	}
	public void setDescDestinazioneImpresa(String descDestinazioneImpresa) {
		this.descDestinazioneImpresa = descDestinazioneImpresa;
	}
	public void setTipoOrganoAmministrativo(String tipoOrganoAmministrativo) {
		this.tipoOrganoAmministrativo = tipoOrganoAmministrativo;
	}
	public void setNominativoAmministratore(String nominativoAmministratore) {
		this.nominativoAmministratore = nominativoAmministratore;
	}
	public void setDataNominaAmministratore(
			XMLGregorianCalendar dataNominaAmministratore) {
		this.dataNominaAmministratore = dataNominaAmministratore;
	}
	public void setNominativoRappresentanteLegale(
			String nominativoRappresentanteLegale) {
		this.nominativoRappresentanteLegale = nominativoRappresentanteLegale;
	}
	public void setDataNominaRappresentante(
			XMLGregorianCalendar dataNominaRappresentante) {
		this.dataNominaRappresentante = dataNominaRappresentante;
	}
	public void setLiquidazioneSociale(String liquidazioneSociale) {
		this.liquidazioneSociale = liquidazioneSociale;
	}
	public void setDataLiquidazione(XMLGregorianCalendar dataLiquidazione) {
		this.dataLiquidazione = dataLiquidazione;
	}
	public void setNominativoLiquidatore(String nominativoLiquidatore) {
		this.nominativoLiquidatore = nominativoLiquidatore;
	}
	public void setTipoOrganoControllo(String tipoOrganoControllo) {
		this.tipoOrganoControllo = tipoOrganoControllo;
	}
	public void setRevisioneLegaleConti(String revisioneLegaleConti) {
		this.revisioneLegaleConti = revisioneLegaleConti;
	}
	public void setNominativoRevisore(String nominativoRevisore) {
		this.nominativoRevisore = nominativoRevisore;
	}
	public void setDataUltimoBilancioApprovato(
			XMLGregorianCalendar dataUltimoBilancioApprovato) {
		this.dataUltimoBilancioApprovato = dataUltimoBilancioApprovato;
	}
	public void setSaldoUltimoBilancio(BigDecimal saldoUltimoBilancio) {
		this.saldoUltimoBilancio = saldoUltimoBilancio;
	}
	public void setLuogoScrittureContabili(String luogoScrittureContabili) {
		this.luogoScrittureContabili = luogoScrittureContabili;
	}
	public void setNominativaDetentoreScritture(String nominativaDetentoreScritture) {
		this.nominativaDetentoreScritture = nominativaDetentoreScritture;
	}
	public void setNumeroDipendenti(BigInteger numeroDipendenti) {
		this.numeroDipendenti = numeroDipendenti;
	}
	public void setCcnl(String ccnl) {
		this.ccnl = ccnl;
	}
	public void setTipoContrattoLavoro(String tipoContrattoLavoro) {
		this.tipoContrattoLavoro = tipoContrattoLavoro;
	}
	public void setAmmortizzatoreSociale(String ammortizzatoreSociale) {
		this.ammortizzatoreSociale = ammortizzatoreSociale;
	}
	public void setContenziosi(Boolean contenziosi) {
		this.contenziosi = contenziosi;
	}
	public void setRispettoNormeSicurezza(Boolean rispettoNormeSicurezza) {
		this.rispettoNormeSicurezza = rispettoNormeSicurezza;
	}
	public void setLavoratoriStranieri(Boolean lavoratoriStranieri) {
		this.lavoratoriStranieri = lavoratoriStranieri;
	}
	public void setAddettiIrregolari(Boolean addettiIrregolari) {
		this.addettiIrregolari = addettiIrregolari;
	}
	public void setLavoratoriFormazione(Boolean lavoratoriFormazione) {
		this.lavoratoriFormazione = lavoratoriFormazione;
	}
	public void setPosizioneSocio(String posizioneSocio) {
		this.posizioneSocio = posizioneSocio;
	}
*/
}
