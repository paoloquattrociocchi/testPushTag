package it.giustizia.smp.webservice.client.fug.modelli;

import it.giustizia.smp.webservice.client.fug.TipoRegistroTipo;

import java.util.Date;

public abstract class ModelloCBase extends ModelloBase {

	private Date dataProvvedimentoSequestro;
	private Date dataProvvedimentoRestituzione;

	// Informazioni processo
	private String codUffEmittente; //codice ufficio che ha emesso il procedimento: deve esistere nella tabella uffici giudiziari pubblicata su sito di equitalia giustizia
	// Per i campi tipo, numero e anno registro vengono utilizzati quelli del modello base

	public ModelloCBase() {
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

	public Date getDataProvvedimentoRestituzione() {
		return dataProvvedimentoRestituzione;
	}

	public void setDataProvvedimentoRestituzione(Date dataProvvedimentoRestituzione) {
		this.dataProvvedimentoRestituzione = dataProvvedimentoRestituzione;
	}

	public String getCodUffEmittente() {
		return codUffEmittente;
	}

	public void setCodUffEmittente(String codUffEmittente) {
		this.codUffEmittente = codUffEmittente;
	}

}
