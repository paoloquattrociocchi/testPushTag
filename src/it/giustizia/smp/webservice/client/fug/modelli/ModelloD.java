package it.giustizia.smp.webservice.client.fug.modelli;

import it.giustizia.smp.webservice.client.fug.AcquisisciModelloD;
import it.giustizia.smp.webservice.client.fug.DatiRichiestaInvioModelloD;
import it.giustizia.smp.webservice.client.fug.RichiestaModelloD;
import it.giustizia.smp.webservice.client.fug.TipoRegistroTipo;
import it.giustizia.smp.webservice.utility.JAXBUtil;

import java.util.Date;

public class ModelloD extends ModelloBase {

	private String flagSentDecr;
	private String numeroProvvedimento;
	private String annoProvvedimento;
	private Date dataDecreto;
	private String numeroDecreto;
	private String annoDecreto;
	private Date dataEsecutivitaProvvedimentoConfisca;

	// Informazioni processo
	private String codUffEmittente; //codice ufficio che ha emesso il procedimento: deve esistere nella tabella uffici giudiziari pubblicata su sito di equitalia giustizia
	// Per i campi tipo, numero e anno registro vengono utilizzati quelli del modello base

	public ModelloD() {
		this.setTipoRegistro(FIXED_VALUE);
	}

	public void setTipoRegistro(String tipoRegistro) {
		super.setTipoRegistro(TipoRegistroTipo.RPRO_3.value());
	}

	public String getFlagSentDecr() {
		return flagSentDecr;
	}

	public void setFlagSentDecr(String flagSentDecr) {
		this.flagSentDecr = flagSentDecr;
	}

	public String getNumeroProvvedimento() {
		return numeroProvvedimento;
	}

	public void setNumeroProvvedimento(String numeroProvvedimento) {
		this.numeroProvvedimento = numeroProvvedimento;
	}

	public String getAnnoProvvedimento() {
		return annoProvvedimento;
	}

	public void setAnnoProvvedimento(String annoProvvedimento) {
		this.annoProvvedimento = annoProvvedimento;
	}

	
	public Date getDataDecreto() {
		return dataDecreto;
	}

	public void setDataDecreto(Date dataDecreto) {
		this.dataDecreto = dataDecreto;
	}

	public String getNumeroDecreto() {
		return numeroDecreto;
	}

	public void setNumeroDecreto(String numeroDecreto) {
		this.numeroDecreto = numeroDecreto;
	}

	public String getAnnoDecreto() {
		return annoDecreto;
	}

	public void setAnnoDecreto(String annoDecreto) {
		this.annoDecreto = annoDecreto;
	}

	public Date getDataEsecutivitaProvvedimentoConfisca() {
		return dataEsecutivitaProvvedimentoConfisca;
	}

	public void setDataEsecutivitaProvvedimentoConfisca(Date dataEsecutivitaProvvedimentoConfisca) {
		this.dataEsecutivitaProvvedimentoConfisca = dataEsecutivitaProvvedimentoConfisca;
	}

	public String getCodUffEmittente() {
		return codUffEmittente;
	}

	public void setCodUffEmittente(String codUffEmittente) {
		this.codUffEmittente = codUffEmittente;
	}

	private DatiRichiestaInvioModelloD.InfoProvvedimento getInfoProvvedimento() throws Exception {
		DatiRichiestaInvioModelloD.InfoProvvedimento out = new DatiRichiestaInvioModelloD.InfoProvvedimento();
		out.setFlagSentDecr(getFlagSentDecr());
		out.setNumeroProvvedimento(getNumeroProvvedimento());
		out.setAnnoProvvedimento(getAnnoProvvedimento());
		out.setDataDecreto(getData(getDataDecreto()));
		out.setNumeroDecreto(getNumeroDecreto());
		out.setAnnoDecreto(getAnnoDecreto());
		out.setDataEsecutivitaProvvedimentoConfisca(getData(getDataEsecutivitaProvvedimentoConfisca()));
		out.setDivisa(getDivisa());
		out.setImporto(getImporto());
		out.setFlagParziale(getFlagParzialeTipo());
		return out;
	}

	private DatiRichiestaInvioModelloD getDatiRichiestaInvioModello() throws Exception {
		DatiRichiestaInvioModelloD out = new DatiRichiestaInvioModelloD();
		out.setDatiRisorsa(getDatiRisorsaTipo());
		out.setDatiRegistroFUG(getDatiRegistroFUGTipo());
		out.setInfoProcesso(getInfoProcessoTipo());
		out.setInfoProvvedimento(getInfoProvvedimento());
		return out;
	}

	private RichiestaModelloD getRichiestaModello() throws Exception {
		RichiestaModelloD out = new RichiestaModelloD();
		out.setDatiIdentificativiInvio(getDatiIdentificativiInvioTipo());
		out.setDatiRichiestaInvioModelloD(getDatiRichiestaInvioModello());
		return out;
	}

	public AcquisisciModelloD creaModello() throws Exception {
		AcquisisciModelloD out = new AcquisisciModelloD();
		out.setRichiestaModelloD(getRichiestaModello());
		JAXBUtil.printXML(out);
		return out;
	}
}
