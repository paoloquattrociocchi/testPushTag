package it.giustizia.smp.webservice.client.fug.modelli;

import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.webservice.client.fug.AcquisisciModelloH;
import it.giustizia.smp.webservice.client.fug.DatiRichiestaInvioModelloH;
import it.giustizia.smp.webservice.client.fug.ModelliF23Tipo;
import it.giustizia.smp.webservice.client.fug.RichiestaModelloH;
import it.giustizia.smp.webservice.client.fug.TributiTipoF23;
import it.giustizia.smp.webservice.utility.JAXBUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class ModelloH extends ModelloBase {

	// Informazioni provvedimento
	private String flagConfiscaSequestro = Constants.FLAG_SEQUESTRO; // valori ammessi: S, C -- ma deve essere impostato ad "S"
	private Date dataVendita;
	private Date dataVersamento;
	private TipoPagamentoTipo tipoPagamento = TipoPagamentoTipo.CRO;
	private String bollettino;

	// Dati Registro FUG
	//private String tipoRegistroFUG; // valori ammessi: "RFUG1"
	private int numeroRegistroFUG;
	private int annoRegistroFUG;

	// Informazioni processo
	private String codUffEmittente; //codice ufficio che ha emesso il procedimento: deve esistere nella tabella uffici giudiziari pubblicata su sito di equitalia giustizia
	// Per i campi tipo, numero e anno registro vengono utilizzati quelli del modello base
	
	private List<ModelliF23Tipo> listaModelliF23; // max 20 elementi

	public static int MAX_ELEMENTI = 20;
	public static int MAX_BOLLETTINO_LEN = 27;

	public ModelloH() {
		this.setCodiceOperatore("97103880585");
		this.setTipoRisorsa("1");
		this.setNumeroRisorsa("IT77R0760103200001014318867");
		this.setAbi("07601");
		this.setCab("03200");
	}

	public void setCodiceOperatore(String codiceOperatore){
		super.setCodiceOperatore(Utility.isValid(codiceOperatore) ? codiceOperatore : getCodiceOperatore());
	}

	public void setTipoRisorsa(String tipoRisorsa) {
		super.setTipoRisorsa(Utility.isValid(tipoRisorsa) ? tipoRisorsa : getTipoRisorsa());
	}

	public void setNumeroRisorsa(String numeroRisorsa) {
		super.setNumeroRisorsa(Utility.isValid(numeroRisorsa) ? numeroRisorsa : getNumeroRisorsa());
	}

	public void setAbi(String abi) {
		super.setAbi(Utility.isValid(abi) ? abi : getAbi());
	}

	public void setCab(String cab) {
		super.setCab(Utility.isValid(cab) ? cab : getCab());
	}

	public String getFlagConfiscaSequestro() {
		String vs = getValidUpperCaseString(flagConfiscaSequestro);
		return !vs.equals(Constants.FLAG_CONFISCA) ? Constants.FLAG_CONFISCA : Constants.FLAG_SEQUESTRO;
	}

	public void setFlagConfiscaSequestro(String flagConfiscaSequestro) throws Exception {
		this.flagConfiscaSequestro = getFlagConfiscaSequestro();
	}

	public Date getDataVendita() {
		return dataVendita;
	}

	public void setDataVendita(Date dataVendita) {
		this.dataVendita = dataVendita;
	}

	public Date getDataVersamento() {
		return dataVersamento;
	}

	public void setDataVersamento(Date dataVersamento) {
		this.dataVersamento = dataVersamento;
	}

	/**
	 * Valore = BONIFICO (CRO), BONIFICO POSTALE (POYP), BOLLETTINO VCY (VCY), BOLLETTINO VCYL (VCYL),  DISTINTA DI VERSAMENTO VEY (VEY), DISTINTA DI VERSAMENTO LVE (LVE), POSTAGIRO (PGR)
	 * @return
	 */
	public String getTipoPagamento() {
		return tipoPagamento.value();
	}

	/**
	 * Valori ammessi:<br>
	 * <ul>
	 * <li>CRO = BONIFICO</li>
	 * <li>POYP = BONIFICO POSTALE</li>
	 * <li>VCY = BOLLETTINO VCY</li>
	 * <li>VCYL = BOLLETTINO VCYL</li>
	 * <li>VEY = DISTINTA DI VERSAMENTO VEY</li>
	 * <li>LVE = DISTINTA DI VERSAMENTO LVE</li>
	 * <li>PGR = POSTAGIRO</li>
	 * </ul>
	 * @param tipoPagamento valore
	 */
	public void setTipoPagamento(TipoPagamentoTipo tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}

	public void setTipoPagamento(String tipoPagamento) {
		try{
			this.tipoPagamento = TipoPagamentoTipo.fromValue(tipoPagamento);
		} catch(IllegalArgumentException e) {
			StringBuffer msg = new StringBuffer("Parametro '" + tipoPagamento + "' non riconosciuto. Valori ammessi:\n");
			for (TipoPagamentoTipo c: TipoPagamentoTipo.values()) {
				msg.append("'").append(c.value()).append("'='").append(c.desc()).append("'\n");
			}
			throw new IllegalArgumentException(msg.toString());
		}
	}

	/**
	 * Lunghezza massima 27 caratteri: quelli eccedenti la massima lunghezza verranno eliminati
	 */
	public String getBollettino() {
		return StringUtils.left(bollettino, MAX_BOLLETTINO_LEN);
	}

	public void setBollettino(String bollettino) {
		this.bollettino = bollettino;
	}

//	private String checkFlagParziale(String flagParziale) {
//		String vs = getValidUpperCaseString(flagParziale);
//		if (!vs.equals(FlagParzialeTipo.P.value()) && !vs.equals(FlagParzialeTipo.T.value()))
//			throw new IllegalArgumentException("Valore del campo 'FlagParziale' non valido ('" + vs + "'). Valori ammessi: 'T', 'P'");
//		return vs;
//	}
//
	private int checkNumeroRegistroFUG(int numeroRegistro) {
		return Integer.parseInt(checkNumeroRegistro(Integer.toString(numeroRegistro), " FUG"));
	}

	public int getNumeroRegistroFUG() {
		return checkNumeroRegistroFUG(this.numeroRegistroFUG);
	}

	public void setNumeroRegistroFUG(int numeroRegistroFUG) {
		this.numeroRegistroFUG = checkNumeroRegistroFUG(numeroRegistroFUG);
	}

	public String getCodUffEmittente() {
		return codUffEmittente;
	}

	public void setCodUffEmittente(String codUffEmittente) {
		this.codUffEmittente = codUffEmittente;
	}

	public List<ModelliF23Tipo> getListaModelliF23() {
		return listaModelliF23;
	}

	public void setListaModelliF23(List<ModelliF23Tipo> listaModelliF23) {
		this.listaModelliF23 = listaModelliF23;
	}

	public void addModelloF23(ModelliF23Tipo in) throws Exception {
		if (in != null){
			if (listaModelliF23 == null) listaModelliF23 = new ArrayList<ModelliF23Tipo>();
			if (listaModelliF23.size() > MAX_ELEMENTI)
				throw new Exception("Superato il numero massimo di elementi ammesso per la lista di modelli F23 (" + MAX_ELEMENTI + ")");
			listaModelliF23.add(in);
		}
	}

	public void removeModelloF23(ModelliF23Tipo in){
		if ((listaModelliF23 != null) && (listaModelliF23.size() > 0)){
			listaModelliF23.remove(in);
			if (listaModelliF23.size() == 0) listaModelliF23 = null;
		}
	}

	public ModelliF23Tipo creaaModellF23(String importo, List<TributiTipoF23> tributi) throws Exception {
		if ((tributi == null) || (tributi.size() < 1) || (tributi.size() > 9))
			throw new Exception("La lista di tributi deve contenere almeno 1 elemento e non piu` di 9");
		ModelliF23Tipo out = new ModelliF23Tipo();
		out.setImportoF23(importo);
		out.getTributi().addAll(tributi);
		return out;
	}

	private DatiRichiestaInvioModelloH.InfoProvvedimento getInfoProvvedimento() throws Exception {
		DatiRichiestaInvioModelloH.InfoProvvedimento out = new DatiRichiestaInvioModelloH.InfoProvvedimento();
		out.setFlagConfiscaSequestro(getFlagConfiscaSequestro());
		out.setDataVendita(getData(getDataVendita()));
		out.setDataVersamento(getData(getDataVersamento()));
		out.setTipoPagamento(getTipoPagamento());
		out.setBollettino(getBollettino());
		out.setFlagParziale(getFlagParzialeTipo());
		out.setDivisa(getDivisa());
		out.setImporto(getImporto());
		return out;
	}

	private DatiRichiestaInvioModelloH getDatiRichiestaInvioModello() throws Exception {
		DatiRichiestaInvioModelloH out = new DatiRichiestaInvioModelloH();
		out.setDatiRisorsa(getDatiRisorsaTipo());
		out.setDatiRegistroFUG(getDatiRegistroFUGTipo());
		out.setInfoProcesso(getInfoProcessoTipo());
		out.setInfoProvvedimento(getInfoProvvedimento());
		if (Utility.isValid(getListaModelliF23()))
			out.getModelliF23().addAll(getListaModelliF23());
		return out;
	}

	private RichiestaModelloH getRichiestaModello() throws Exception {
		RichiestaModelloH out = new RichiestaModelloH();
		out.setDatiIdentificativiInvio(getDatiIdentificativiInvioTipo());
		out.setDatiRichiestaInvioModelloH(getDatiRichiestaInvioModello());
		return out;
	}

	public AcquisisciModelloH creaModello() throws Exception {
		AcquisisciModelloH out = new AcquisisciModelloH();
		out.setRichiestaModelloH(getRichiestaModello());
		JAXBUtil.printXML(out);
		return out;
	}
}
