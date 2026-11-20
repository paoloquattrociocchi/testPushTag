package it.giustizia.smp.service;

import it.giustizia.smp.dto.TRelBeneBeneDto;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.BeneForm;
import it.giustizia.smp.form.FugForm;
import it.giustizia.smp.form.FugModelloForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.model.TFugInvio;
import it.giustizia.smp.webservice.client.fug.AcquisisciModelloA;
import it.giustizia.smp.webservice.client.fug.AcquisisciModelloC;
import it.giustizia.smp.webservice.client.fug.AcquisisciModelloC2;
import it.giustizia.smp.webservice.client.fug.AcquisisciModelloD;
import it.giustizia.smp.webservice.client.fug.AcquisisciModelloH;
import it.giustizia.smp.webservice.client.fug.RichiestaAnnullamentoModello;
import it.giustizia.smp.webservice.client.fug.modelli.RispostaModello;

import java.util.List;

public interface FugService {

	public List<BeneForm> listaBeniFug(int idUffOwn, long idProcedimento, BeneForm beneAttoForm);
	
	public List<AttoForm> elencoQuoteModello(int idUffOwner, long idProcedimento, BeneForm beneForm, String codiceUtente, String nomeMetodo);
	
	public List<FugForm> elencoInvioFug(int idUffOwner, FugForm fugForm, String codiceUtente, String nomeMetodo);
	
	public List<FugForm> elencoModelliC(int idUffOwner, FugForm fugForm, String codiceUtente, String nomeMetodo);
	public TRelBeneBeneDto datiModelloH(int idUffOwner, long idProcedimento, BeneForm beneForm, String codiceUtente, String nomeMetodo);
	
	public AcquisisciModelloA riempiModelloA(BeneForm bene, long idProcedimento, int idUffOwner, String importoBeneTotale) throws Exception;
	public AcquisisciModelloD riempiModelloD(BeneForm bene, long idProcedimento, int idUffOwner, String importoBeneTotale) throws Exception;
	public AcquisisciModelloC riempiModelloC(BeneForm bene, long idProcedimento, int idUffOwner) throws Exception;
	public AcquisisciModelloC2 riempiModelloC2(BeneForm bene, long idProcedimento, int idUffOwner, String importoBeneTotale) throws Exception;
	public AcquisisciModelloH riempiModelloH(BeneForm bene, long idProcedimento, int idUffOwner, String importoBeneTotale) throws Exception;
	
	public RispostaModello inviaModello(AcquisisciModelloA acquisisciModelloA, long idInvio, String descUtente);
	public RispostaModello inviaModello(AcquisisciModelloD acquisisciModelloD, long idInvio, String descUtente);
	public RispostaModello inviaModello(AcquisisciModelloC acquisisciModelloC, long idInvio, String descUtente);
	public RispostaModello inviaModello(AcquisisciModelloC2 acquisisciModelloC2, long idInvio, String descUtente);
	public RispostaModello inviaModello(AcquisisciModelloH acquisisciModelloH, long idInvio, String descUtente);
	
	public void insertFugErrori(RispostaModello rispostaModello, long idInvio, String descUtente);
	
	public RichiestaAnnullamentoModello richiestaAnnullamentoModello(int idUffOwner, TFugInvio tFugInvio)  throws Exception;
	public RispostaModello annullaModello(RichiestaAnnullamentoModello richiestaAnnullamento, long idInvio, Long idInvioSelezionato, String descUtente);
	
	public List<SoggettoForm> getListaSoggettiBeneficiari(int idUffOwner, long idProcedimento, BeneForm beneForm, Long idAtto, Long idSoggetto, String provenienza);

	public TFugInvio gatInvioFUGCollegato(int idUffOwn, String protRif);
	public void getRispostaEdEsitoXML(FugModelloForm fugModelloForm);
	public boolean isBeneDecretoRev(long idProc, Long idAtto, Long idBene, int idUffOwner);
	public boolean isBeneDecretoConf(long idProc, Long idAtto, Long idBene, int idUffOwner);
}
