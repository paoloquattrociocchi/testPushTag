package it.giustizia.smp.service;

import java.util.List;

import it.giustizia.smp.model.TAnbscMsg;
import it.giustizia.smp.model.TDefPatr;
import it.giustizia.smp.webservice.client.anbsc.Bene;
import it.giustizia.smp.webservice.client.anbsc.ConfiscaDefinitivaRequest;
import it.giustizia.smp.webservice.client.anbsc.ConfiscaDefinitivaResponse;
import it.giustizia.smp.webservice.client.anbsc.ConfiscaPrimoGradoRequest;
import it.giustizia.smp.webservice.client.anbsc.ConfiscaPrimoGradoResponse;
import it.giustizia.smp.webservice.client.anbsc.ConfiscaSecondoGradoRequest;
import it.giustizia.smp.webservice.client.anbsc.ConfiscaSecondoGradoResponse;
import it.giustizia.smp.webservice.client.anbsc.Esito;
import it.giustizia.smp.webservice.client.anbsc.Misura;
import it.giustizia.smp.webservice.client.anbsc.RevocaConfiscaRequest;
import it.giustizia.smp.webservice.client.anbsc.RevocaConfiscaResponse;
import it.giustizia.smp.webservice.client.anbsc.RevocaSequestroRequest;
import it.giustizia.smp.webservice.client.anbsc.RevocaSequestroResponse;
import it.giustizia.smp.webservice.client.anbsc.RiunioneRequest;
import it.giustizia.smp.webservice.client.anbsc.RiunioneResponse;
import it.giustizia.smp.webservice.client.anbsc.SequestroRequest;
import it.giustizia.smp.webservice.client.anbsc.SequestroResponse;
import it.giustizia.smp.webservice.client.anbsc.Soggetto;
import it.giustizia.smp.webservice.client.anbsc.StralcioRequest;
import it.giustizia.smp.webservice.client.anbsc.StralcioResponse;


public interface AnbscService {
	
	public boolean verificaInvioSequestroAnbsc(long idProcedimento, long idAtto);
	
	public long insertMsgDatabase(Object msg, long idProcedimento,long idAtto, long idTipoAnbscMsg, String descUtente);
	
	public void updateRispostaMsgDatabase(long idMsg, Esito response, Object xmlMsgOut);
	
	// Nel caso di mancata validazione del file XML chiamo questo metodo
	public void updateRispostaMsgDatabase(long idMsg, char esito, String errore);
	
	public List<TAnbscMsg> getDaInviare();
	
	public List<Bene> getBeni(long idProcedimento, long idAtto);
	
	public List<Soggetto> getSoggetti(int uffOwner, long idProc, long idAtto, List<Bene> beni, List<TDefPatr> listaMisPatrDaInviareANBSC);
	
	public List<Misura> getMisure(long idProc, long idAtto, List<Bene> beni, Integer uffOwner, List<TDefPatr> listaMisPatrDaInviareANBSC);
	
	public SequestroRequest createSequestroRequest(long idProcedimento, long idAtto, Integer idUfficio, String tipoSequestro);
	
	public SequestroResponse inviaSequestro(SequestroRequest request);
	
	public RevocaSequestroRequest createRevocaSequestroRequest (long idProcedimento, long idAtto, Integer idUfficio);
	
	public RevocaSequestroResponse inviaRevocaSequestro(RevocaSequestroRequest request);
	
	public StralcioResponse inviaStralcio(StralcioRequest request);
	
	public StralcioRequest createStralcioRequest(long idProcedimento, long idAtto, Integer idUfficio);
	
	public boolean verificaInvioStralcioRiunione(long idProcedimento);
	
	public RiunioneResponse inviaRiunione(RiunioneRequest request);
	
	public RiunioneRequest createRiunioneRequest(long idProcedimentoRiunito, long idProcedimentoRiunente, Integer idUfficio);
	
	public ConfiscaPrimoGradoRequest createConfiscaPrimoGradoRequest (long idProcedimento, long idAtto, Integer idUfficio); 
	
	public ConfiscaPrimoGradoResponse inviaConfiscaPrimoGrado(ConfiscaPrimoGradoRequest request);
	
	public ConfiscaSecondoGradoRequest createConfiscaSecondoGradoRequest (long idProcedimento, long idAtto, Integer idUfficio);
	
	public ConfiscaSecondoGradoResponse inviaConfiscaSecondoGrado(ConfiscaSecondoGradoRequest request);
	
	public ConfiscaDefinitivaRequest createConfiscaDefinitivaRequest (long idProcedimento, long idAtto, Integer idUfficio, final int tipoProvvedimento, List<TDefPatr> listaMisPatrDaInviareANBSC);
	
	public ConfiscaDefinitivaResponse inviaConfiscaDefinitiva(ConfiscaDefinitivaRequest request);
	
	public boolean verificaInvioDecretoConvalidaAnbsc(long idProcedimento, long idAtto);
	
	public SequestroRequest createDecretoConvalidaRequest(long idProcedimento, long idAttoOr, long idAttoFi, Integer idUfficio, String tipoSequestro);
	
	public long recuperaAttoOrig(long idAtto, Integer idUfficioUtente);
	
	public RevocaConfiscaResponse inviaRevocaConfisca(RevocaConfiscaRequest request);
	
	public RevocaConfiscaRequest createRevocaConfiscaRequest (long idProcedimento, long idAtto, Integer idUfficio);
}
