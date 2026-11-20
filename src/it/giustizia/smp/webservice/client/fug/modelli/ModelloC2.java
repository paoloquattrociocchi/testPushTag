package it.giustizia.smp.webservice.client.fug.modelli;

import it.giustizia.smp.webservice.client.fug.AcquisisciModelloC2;
import it.giustizia.smp.webservice.client.fug.DatiRichiestaInvioModelloC2;
import it.giustizia.smp.webservice.client.fug.InfoProcessoTipo;
import it.giustizia.smp.webservice.client.fug.RichiestaModelloC2;
import it.giustizia.smp.webservice.client.fug.TipoSoggettoTipo;
import it.giustizia.smp.webservice.utility.JAXBUtil;

import java.util.Date;

public class ModelloC2 extends ModelloCBase {

	private String tipoAcquisizione; // valori ammessi: 1, 2
	private InfoProcessoTipo infoProcessoModelloC;
	private String tipoSoggetto; // valori ammessi: PF, PNF
	private String cf;
	private String piva;
	private String cognome;
	private String nome;
	private String denominazione;
	private Date dataNascitaCostituzione;
	private Date dataNotifica;
	private Date dataPassaggioGiudicatroProvvedimentoRestituzione;

	public String getTipoAcquisizione() {
		return tipoAcquisizione;
	}

	public void setTipoAcquisizione(String tipoAcquisizione) {
		this.tipoAcquisizione = tipoAcquisizione;
	}

	public InfoProcessoTipo getInfoProcessoModelloC() {
		return infoProcessoModelloC;
	}

	public void setInfoProcessoModelloC(InfoProcessoTipo infoProcessoModelloC) {
		this.infoProcessoModelloC = infoProcessoModelloC;
	}

	private TipoSoggettoTipo getTipoSoggettoTipo() throws Exception {
		String vs = getValidUpperCaseString(getTipoSoggetto());
		if (!vs.equals(TipoSoggettoTipo.PF.value()) && !vs.equals(TipoSoggettoTipo.PNF.value()))
			throw new Exception("Tipo soggetto non valido. Valori ammessi: 'PF', 'PNF'");
		return TipoSoggettoTipo.valueOf(vs);
//		return vs.equals(TipoSoggettoTipo.PF.value()) ? TipoSoggettoTipo.PF : TipoSoggettoTipo.PNF;
	}

	public String getTipoSoggetto() {
		return tipoSoggetto;
	}

	public void setTipoSoggetto(String tipoSoggetto) {
		this.tipoSoggetto = tipoSoggetto;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getPiva() {
		return piva;
	}

	public void setPiva(String piva) {
		this.piva = piva;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public Date getDataNascitaCostituzione() {
		return dataNascitaCostituzione;
	}

	public void setDataNascitaCostituzione(Date dataNascitaCostituzione) {
		this.dataNascitaCostituzione = dataNascitaCostituzione;
	}

	public Date getDataNotifica() {
		return dataNotifica;
	}

	public void setDataNotifica(Date dataNotifica) {
		this.dataNotifica = dataNotifica;
	}

	public Date getDataPassaggioGiudicatroProvvedimentoRestituzione() {
		return dataPassaggioGiudicatroProvvedimentoRestituzione;
	}

	public void setDataPassaggioGiudicatroProvvedimentoRestituzione(Date dataPassaggioGiudicatroProvvedimentoRestituzione) {
		this.dataPassaggioGiudicatroProvvedimentoRestituzione = dataPassaggioGiudicatroProvvedimentoRestituzione;
	}

	private DatiRichiestaInvioModelloC2.InfoProvvedimentoModelloC getInfoProvvedimentoModelloC(){
		DatiRichiestaInvioModelloC2.InfoProvvedimentoModelloC out = new DatiRichiestaInvioModelloC2.InfoProvvedimentoModelloC();
		out.setDataProvvedimentoSequestro(getData(getDataProvvedimentoSequestro()));
		out.setDataProvvedimentoRestituzione(getData(this.getDataProvvedimentoRestituzione()));
		return out;
	}

	private DatiRichiestaInvioModelloC2.InfoBeneficiarioModelloC getInfoBeneficiarioModelloC() throws Exception {
		DatiRichiestaInvioModelloC2.InfoBeneficiarioModelloC out = new DatiRichiestaInvioModelloC2.InfoBeneficiarioModelloC();
		out.setTipoSoggetto(getTipoSoggettoTipo());
		out.setCF(getCf());
		out.setPIVA(getPiva());
		out.setCognome(getCognome());
		out.setNome(getNome());
		out.setDenominazione(getDenominazione());
		out.setDataNascitaCostituzione(getData(getDataNascitaCostituzione()));
		return out;
	}

	private DatiRichiestaInvioModelloC2.InfoNotifica getInfoNotifica(){
		DatiRichiestaInvioModelloC2.InfoNotifica out = new DatiRichiestaInvioModelloC2.InfoNotifica();
		if(getDataNotifica() != null)
			out.setDataNotifica(getData(getDataNotifica()));
		else
			out.setDataNotifica(null);
		if(getDataPassaggioGiudicatroProvvedimentoRestituzione() != null)
			out.setDataPassaggioGiudicatroProvvedimentoRestituzione(getData(getDataPassaggioGiudicatroProvvedimentoRestituzione()));
		else
			out.setDataPassaggioGiudicatroProvvedimentoRestituzione(null);
		return out;
	}

	private DatiRichiestaInvioModelloC2 getDatiRichiestaInvioModello() throws Exception {
		DatiRichiestaInvioModelloC2 out = new DatiRichiestaInvioModelloC2();
		out.setTipoAcquisizione(getTipoAcquisizione());
		out.setProtocolloRicevutaModelloC(getProtocolloRicevutaModello());
		out.setDatiRisorsa(getDatiRisorsaTipo());
		out.setInfoProcessoModelloC(getInfoProcessoModelloC());
		out.setInfoProvvedimentoModelloC(getInfoProvvedimentoModelloC());
		out.setInfoBeneficiarioModelloC(getInfoBeneficiarioModelloC());
		out.setDatiRegistroFUG(getDatiRegistroFUGTipo());
		out.setInfoProcesso(getInfoProcessoTipo());
		out.setInfoNotifica(getInfoNotifica());		
		return out;
	}

	private RichiestaModelloC2 getRichiestaModello() throws Exception {
		RichiestaModelloC2 out = new RichiestaModelloC2();
		out.setDatiIdentificativiInvio(getDatiIdentificativiInvioTipo());
		out.setDatiRichiestaInvioModelloC2(getDatiRichiestaInvioModello());
		return out;
	}

	public AcquisisciModelloC2 creaModello() throws Exception {
		AcquisisciModelloC2 out = new AcquisisciModelloC2();
		out.setRichiestaModelloC2(getRichiestaModello());
		JAXBUtil.printXML(out);
		return out;
	}
}
