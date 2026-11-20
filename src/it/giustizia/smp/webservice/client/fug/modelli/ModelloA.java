package it.giustizia.smp.webservice.client.fug.modelli;

import it.giustizia.smp.webservice.client.fug.AcquisisciModelloA;
import it.giustizia.smp.webservice.client.fug.DatiRichiestaInvioModelloA;
import it.giustizia.smp.webservice.client.fug.RichiestaModelloA;
import it.giustizia.smp.webservice.client.fug.TipoRegistroTipo;
import it.giustizia.smp.webservice.utility.JAXBUtil;

import java.util.Date;

public class ModelloA extends ModelloBase {

	private Date dataProvvedimentoSequestro;
	private String flagSequestro;

	// Informazioni processo
	private String codUffEmittente; //codice ufficio che ha emesso il procedimento: deve esistere nella tabella uffici giudiziari pubblicata su sito di equitalia giustizia
	// Per i campi tipo, numero e anno registro vengono utilizzati quelli del modello base

	public ModelloA() {
		this.setTipoRegistro(FIXED_VALUE);
	}

	public void setTipoRegistro(String tipoRegistro) {
		super.setTipoRegistro(TipoRegistroTipo.RPRO_3.value());
	}

	public Date getDataProvvedimentoSequestro() {
		return dataProvvedimentoSequestro;
	}

	public void setDataProvvedimentoSequestro(Date dataProvvedimentoSequestro) {
		this.dataProvvedimentoSequestro = dataProvvedimentoSequestro;
	}

	public String getFlagSequestro() {
		return flagSequestro;
	}

	public void setFlagSequestro(String flagSequestro) {
		this.flagSequestro = flagSequestro;
	}

	public String getCodUffEmittente() {
		return codUffEmittente;
	}

	public void setCodUffEmittente(String codUffEmittente) {
		this.codUffEmittente = codUffEmittente;
	}

	private DatiRichiestaInvioModelloA.InfoProvvedimento getInfoProvvedimento(){
		DatiRichiestaInvioModelloA.InfoProvvedimento out = new DatiRichiestaInvioModelloA.InfoProvvedimento();
		out.setDataProvvedimentoSequestro(getData(getDataProvvedimentoSequestro()));
		out.setFlagSequestro(getFlagSequestro());
		out.setImporto(getImporto());
		out.setDivisa(getDivisa());
		return out;
	}

	private DatiRichiestaInvioModelloA getDatiRichiestaInvioModello(){
		DatiRichiestaInvioModelloA out = new DatiRichiestaInvioModelloA();
		out.setDatiRisorsa(getDatiRisorsaTipo());
		out.setDatiRegistroFUG(getDatiRegistroFUGTipo());
		out.setInfoProcesso(getInfoProcessoTipo());
		out.setInfoProvvedimento(getInfoProvvedimento());
		return out;
	}

	private RichiestaModelloA getRichiestaModello(){
		RichiestaModelloA out = new RichiestaModelloA();
		out.setDatiIdentificativiInvio(getDatiIdentificativiInvioTipo());
		out.setDatiRichiestaInvioModelloA(getDatiRichiestaInvioModello());
		return out;
	}

	public AcquisisciModelloA creaModello() {
		AcquisisciModelloA out = new AcquisisciModelloA();
		out.setRichiestaModelloA(getRichiestaModello());
		JAXBUtil.printXML(out);
		return out;
	}
}
