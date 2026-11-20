package it.giustizia.smp.service;

import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.model.TPoliziaMsg;
import it.giustizia.smp.model.TTpCatAtto;
import it.giustizia.smp.model.TTpPoliziaServizi;
import it.giustizia.smp.webservice.client.polizia.DecisorioAggravamentoMisuraPersonaleRequest;
import it.giustizia.smp.webservice.client.polizia.DecisorioAggravamentoMisuraPersonaleResponse;
import it.giustizia.smp.webservice.client.polizia.DecisorioRevocaModificaMisuraPersonaleRequest;
import it.giustizia.smp.webservice.client.polizia.DecisorioRevocaModificaMisuraPersonaleResponse;
import it.giustizia.smp.webservice.client.polizia.DecretiDecisoriIGradoPatrimonialiRequest;
import it.giustizia.smp.webservice.client.polizia.DecretiDecisoriIGradoPatrimonialiResponse;
import it.giustizia.smp.webservice.client.polizia.DecretiDecisoriIGradoPersonaliRequest;
import it.giustizia.smp.webservice.client.polizia.DecretiDecisoriIGradoPersonaliResponse;
import it.giustizia.smp.webservice.client.polizia.DecretiDecisoriIIGradoPatrimonialiRequest;
import it.giustizia.smp.webservice.client.polizia.DecretiDecisoriIIGradoPatrimonialiResponse;
import it.giustizia.smp.webservice.client.polizia.DecretiDecisoriIIGradoPersonaliRequest;
import it.giustizia.smp.webservice.client.polizia.DecretiDecisoriIIGradoPersonaliResponse;
import it.giustizia.smp.webservice.client.polizia.DecretiProvvisoriApplicazioneRequest;
import it.giustizia.smp.webservice.client.polizia.DecretiProvvisoriApplicazioneResponse;
import it.giustizia.smp.webservice.client.polizia.DecretiProvvisoriRevocaRequest;
import it.giustizia.smp.webservice.client.polizia.DecretiProvvisoriRevocaResponse;
import it.giustizia.smp.webservice.client.polizia.DecretiSospensioneRestituzioneBeniRequest;
import it.giustizia.smp.webservice.client.polizia.DecretiSospensioneRestituzioneBeniResponse;
import it.giustizia.smp.webservice.client.polizia.DefinitivitaAggravamentoMisuraPersonaleRequest;
import it.giustizia.smp.webservice.client.polizia.DefinitivitaAggravamentoMisuraPersonaleResponse;
import it.giustizia.smp.webservice.client.polizia.DefinitivitaCorrezioneErroreDatiSoggettoRequest;
import it.giustizia.smp.webservice.client.polizia.DefinitivitaCorrezioneErroreDatiSoggettoResponse;
import it.giustizia.smp.webservice.client.polizia.DefinitivitaPatrimonialiRequest;
import it.giustizia.smp.webservice.client.polizia.DefinitivitaPatrimonialiResponse;
import it.giustizia.smp.webservice.client.polizia.DefinitivitaPersonaliRequest;
import it.giustizia.smp.webservice.client.polizia.DefinitivitaPersonaliResponse;
import it.giustizia.smp.webservice.client.polizia.DefinitivitaRevocaModificaMisuraPersonaleRequest;
import it.giustizia.smp.webservice.client.polizia.DefinitivitaRevocaModificaMisuraPersonaleResponse;
import it.giustizia.smp.webservice.client.polizia.Esito;
import it.giustizia.smp.webservice.client.polizia.IndaginiSoggettiPropostiRequest;
import it.giustizia.smp.webservice.client.polizia.IndaginiSoggettiPropostiResponse;
import it.giustizia.smp.webservice.client.polizia.IndaginiSoggettiPropostiTrCaRequest;
import it.giustizia.smp.webservice.client.polizia.IndaginiSoggettiPropostiTrCaResponse;
import it.giustizia.smp.webservice.client.polizia.ParereRevocaModificaMisurePersonaliRequest;
import it.giustizia.smp.webservice.client.polizia.ParereRevocaModificaMisurePersonaliResponse;
import it.giustizia.smp.webservice.client.polizia.PoliziaMsgErrorRequest;
import it.giustizia.smp.webservice.client.polizia.PoliziaMsgErrorResponse;
import it.giustizia.smp.webservice.client.polizia.ProposteMisurePatrimonialiRequest;
import it.giustizia.smp.webservice.client.polizia.ProposteMisurePatrimonialiResponse;
import it.giustizia.smp.webservice.client.polizia.ProposteMisurePersonaliRequest;
import it.giustizia.smp.webservice.client.polizia.ProposteMisurePersonaliResponse;
import it.giustizia.smp.webservice.client.polizia.RevocaSequestroRequest;
import it.giustizia.smp.webservice.client.polizia.RevocaSequestroResponse;
import it.giustizia.smp.webservice.client.polizia.RichiestaAggravamentoMisuraPersonaleRequest;
import it.giustizia.smp.webservice.client.polizia.RichiestaAggravamentoMisuraPersonaleResponse;
import it.giustizia.smp.webservice.client.polizia.RichiestaModificaRevocaMisuraPersonaleRequest;
import it.giustizia.smp.webservice.client.polizia.RichiestaModificaRevocaMisuraPersonaleResponse;
import it.giustizia.smp.webservice.client.polizia.SentenzaCassazionePatrimonialiRequest;
import it.giustizia.smp.webservice.client.polizia.SentenzaCassazionePatrimonialiResponse;
import it.giustizia.smp.webservice.client.polizia.SentenzaCassazionePersonaliRequest;
import it.giustizia.smp.webservice.client.polizia.SentenzaCassazionePersonaliResponse;
import it.giustizia.smp.webservice.client.polizia.SequestroRequest;
import it.giustizia.smp.webservice.client.polizia.SequestroResponse;
import it.giustizia.smp.webservice.client.polizia.VariazioneAnagraficheSoggettiInteressatiRequest;
import it.giustizia.smp.webservice.client.polizia.VariazioneAnagraficheSoggettiInteressatiResponse;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface PoliziaService {
	
	//restituisce la lista dei messaggi da inviare
	public List<TPoliziaMsg> getDaInviare();
	
	//restituisce un record della tabella TPoliziaMsg
	public TPoliziaMsg getTPoliziaMsgById(final long codiKIdMsg);
	public TTpPoliziaServizi getTtpPoliziaServiziById(final long codiKIdServizi);
	
	//inserisce nella tabella TPoliziaMsg
	public long insertPoliziaMsgDatabase(int idTipoServizio, long idProcedimento, long idAtto, Object msg, int statoMsg, long idAutorita, int ufficio, String descUtente);

	//aggiorna la TPoliziaMsg con l'esito ricevuto in risposta al messaggio
	public void updateRispostaMsgDatabase(long idMsg, Esito response);
	
	public Long getIdUfficioDest(long idUffOwner);
	public Long getIdUfficioDestByIdProcedente(int idProcedente);
	
	//7.1 id_TipoServizio = 5
	public IndaginiSoggettiPropostiRequest createIndaginiSoggettiPropostiRequest(long idProcedimento, long idAtto, int uffOwner, int uffDest);
	public IndaginiSoggettiPropostiResponse inviaIndaginiSoggettiProposti(IndaginiSoggettiPropostiRequest request);

	//8.1 id_TipoServizio = 3
	public ProposteMisurePersonaliRequest createProposteMisurePersonaliRequest(long idProcedimento, long idAtto, int uffOwner, int uffDest);
	public ProposteMisurePersonaliResponse inviaProposteMisurePersonali(ProposteMisurePersonaliRequest request);
	
	//8.2 IdTipoServizio 4		
	public ProposteMisurePatrimonialiRequest createProposteMisurePatrimonialiRequest(long idProcedimento, long idAtto, int uffOwner, int uffDest);
	public ProposteMisurePatrimonialiResponse inviaProposteMisurePatrimoniali(ProposteMisurePatrimonialiRequest request);
	
	//8.3 IdTipoServizio 26
	public IndaginiSoggettiPropostiTrCaRequest createIndaginiSoggettiPropostiTrCaRequest(long idProcedimento, long idAtto, int uffOwner, int uffDest);
	public IndaginiSoggettiPropostiTrCaResponse inviaIndaginiSoggettiPropostiTrCa(IndaginiSoggettiPropostiTrCaRequest request);
	
	//8.4 IdTipoServizio 27
	public ParereRevocaModificaMisurePersonaliRequest createParereRevocaModificaMisurePersonaliRequest(long idProcedimento, long idAtto, int uffOwner, int uffDest);
	public ParereRevocaModificaMisurePersonaliResponse inviaParereRevocaModificaMisurePersonali(ParereRevocaModificaMisurePersonaliRequest request);
	
	//8.5 IdTipoServizio 28	
	public VariazioneAnagraficheSoggettiInteressatiRequest createVariazioneAnagraficheSoggettiInteressatiRequest(long idProcedimento, long idSoggetto, int idVerOld, int idVerNew, int uffOwner, int uffDest);
	public VariazioneAnagraficheSoggettiInteressatiResponse inviaVariazioneAnagraficheSoggettiInteressati(VariazioneAnagraficheSoggettiInteressatiRequest request);
	
	//8.6 Sequestro IdTipoServizio 1
	public it.giustizia.smp.webservice.client.polizia.SequestroRequest createSequestroRequest(long idProcedimento, long idAtto, Integer idUfficioUtente,Integer uffDestinatario);
	public SequestroResponse inviaSequestro(SequestroRequest request);
	
	//8.6 RevocaSequestro IdTipoServizio 2
	public it.giustizia.smp.webservice.client.polizia.RevocaSequestroRequest createRevocaSequestroRequest(long idProcedimento, long idAtto, Integer idUfficioUtente,Integer uffDestinatario);
	public RevocaSequestroResponse inviaRevocaSequestro(RevocaSequestroRequest request);

	//8.7 IdTipoServizio 6
	public DecretiSospensioneRestituzioneBeniRequest createDecretiSospensioneRestituzioneBeniRequest(long idProcedimento, long idAtto, int uffOwner, int uffDest);
	public DecretiSospensioneRestituzioneBeniResponse inviaDecretiSospensioneRestituzioneBeni(DecretiSospensioneRestituzioneBeniRequest request);

	//8.8 - 1 Applicazione IdTipoServizio 7
	public DecretiProvvisoriApplicazioneRequest createDecretiProvvisoriApplicazioneRequest(long idProcedimento, long idAtto, int uffOwner, int uffDest);
	public DecretiProvvisoriApplicazioneResponse inviaDecretiProvvisoriApplicazione(DecretiProvvisoriApplicazioneRequest request);

	//8.8 -2 Revoca IdTipoServizio 8
	public DecretiProvvisoriRevocaRequest createDecretiProvvisoriRevocaRequest(long idProcedimento, long idAtto, int uffOwner, int uffDest);
	public DecretiProvvisoriRevocaResponse inviaDecretiProvvisoriRevoca(DecretiProvvisoriRevocaRequest request);
	
	//8.9 Revoca IdTipoServizio 9
	public DecretiDecisoriIGradoPersonaliRequest createDecretiDecisoriIGradoPersonaliRequest(long idProcedimento, long idAtto, int uffOwner, int uffDest);
	public DecretiDecisoriIGradoPersonaliResponse inviaDecretiDecisoriIGradoPersonali(DecretiDecisoriIGradoPersonaliRequest request);
	
	//8.10 IdTipoServizio 10	
	public DecretiDecisoriIGradoPatrimonialiRequest createDecretiDecisoriIGradoPatrimonialiRequest(long idProcedimento, long idAtto, int uffOwner, int uffDest);
	public DecretiDecisoriIGradoPatrimonialiResponse inviaDecretiDecisoriIGradoPatrimoniali(DecretiDecisoriIGradoPatrimonialiRequest request);

	//8.11 IdTipoServizio 11	
	public DecretiDecisoriIIGradoPersonaliRequest createDecretiDecisoriIIGradoPersonaliRequest(long idProcedimento, long idAtto, int uffOwner, int uffDest);
	public DecretiDecisoriIIGradoPersonaliResponse inviaDecretiDecisoriIIGradoPersonali(DecretiDecisoriIIGradoPersonaliRequest request);

	//8.12 IdTipoServizio 12	
	public DecretiDecisoriIIGradoPatrimonialiRequest createDecretiDecisoriIIGradoPatrimonialiRequest(long idProcedimento, long idAtto, int uffOwner, int uffDest);
	public DecretiDecisoriIIGradoPatrimonialiResponse inviaDecretiDecisoriIIGradoPatrimoniali(DecretiDecisoriIIGradoPatrimonialiRequest request);

	//8.13 IdTipoServizio 13
	public SentenzaCassazionePersonaliRequest createSentenzaCassazionePersonaliRequest(long idProcedimento, long idAtto, int uffOwner, int uffDest);
	public SentenzaCassazionePersonaliResponse inviaSentenzaCassazionePersonali(SentenzaCassazionePersonaliRequest request);

	//8.14 IdTipoServizio 14
	public SentenzaCassazionePatrimonialiRequest createSentenzaCassazionePatrimonialiRequest(long idProcedimento, long idAtto, int uffOwner, int uffDest);
	public SentenzaCassazionePatrimonialiResponse inviaSentenzaCassazionePatrimoniali(SentenzaCassazionePatrimonialiRequest request);
	public void elaboraSentenze();
	
	//8.15 IdTipoServizio 15
	public DefinitivitaPersonaliRequest createDefinitivitaPersonaliRequest(long idProcedimento, long idAtto, int uffOwner, int uffDest, HashMap <Long, Date> misureDateDefinitive);
	public DefinitivitaPersonaliResponse inviaDefinitivitaPersonali(DefinitivitaPersonaliRequest request);
	
	//8.16 IdTipoServizio 16
	public DefinitivitaPatrimonialiRequest createDefinitivitaPatrimonialiRequest(long idProcedimento, long idAtto, int uffOwner, int uffDest, HashMap <Long, Date> misureDateDefinitive);
	public DefinitivitaPatrimonialiResponse inviaDefinitivitaPatrimoniali(DefinitivitaPatrimonialiRequest request);
	
	//8.17 IdTipoServizio 29
	public RichiestaAggravamentoMisuraPersonaleRequest createRichiestaAggravamentoMisuraPersonale(long idProcedimento, long idAtto, int uffOwner, int uffDest, long idProcedimentoOld, int ufficioProcOld);
	public RichiestaAggravamentoMisuraPersonaleResponse inviaRichiestaAggravamentoMisuraPersonale(RichiestaAggravamentoMisuraPersonaleRequest request);

	//8.18 IdTipoServizio 30
	public DecisorioAggravamentoMisuraPersonaleRequest createDecisorioAggravamentoMisuraPersonaleRequest(long idProcedimento, long idAtto, int uffOwner, int uffDest);
	public DecisorioAggravamentoMisuraPersonaleResponse inviaDecisorioAggravamentoMisuraPersonale(DecisorioAggravamentoMisuraPersonaleRequest request);

	//8.19 IdTipoServizio 31
	public DefinitivitaAggravamentoMisuraPersonaleRequest createDefinitivitaAggravamentoMisuraPersonaleRequest(long idProcedimento, long idAtto, int uffOwner, int uffDest,	HashMap<Long, Date> misureDateDefinitive);
	public DefinitivitaAggravamentoMisuraPersonaleResponse inviaDefinitivitaAggravamentoMisuraPersonale(DefinitivitaAggravamentoMisuraPersonaleRequest request);
	
	//8.20 IdTipoServizio 32
	public RichiestaModificaRevocaMisuraPersonaleRequest createRichiestaModificaRevocaMisuraPersonaleRequest(long idProcedimento, long idAtto, int uffOwner, int uffDest, long idSoggetto, long idMisura, long idProcedimentoOld, int ufficioProcOld);
	public RichiestaModificaRevocaMisuraPersonaleResponse inviaRichiestaModificaRevocaMisuraPersonale(RichiestaModificaRevocaMisuraPersonaleRequest request);
	
	//8.21 IdTipoServizio 33
	public DecisorioRevocaModificaMisuraPersonaleRequest createDecisorioRevocaModificaMisuraPersonaleRequest(long idProcedimento, long idAtto, int uffOwner, int uffDest);
	public DecisorioRevocaModificaMisuraPersonaleResponse inviaDecisorioRevocaModificaMisuraPersonale(DecisorioRevocaModificaMisuraPersonaleRequest request);

	//8.22 IdTipoServizio 34
	public DefinitivitaRevocaModificaMisuraPersonaleRequest createDefinitivitaRevocaModificaMisuraPersonaleRequest(long idProcedimento, long idAtto, int uffOwner, int uffDest,	HashMap<Long, Date> misureDateDefinitive);
	public DefinitivitaRevocaModificaMisuraPersonaleResponse inviaDefinitivitaRevocaModificaMisuraPersonale(DefinitivitaRevocaModificaMisuraPersonaleRequest request);

	//8.23 IdTipoServizio 35
	public DefinitivitaCorrezioneErroreDatiSoggettoRequest createDefinitivitaCorrezioneErroreDatiSoggettoRequest(long idProcedimento, long idSoggetto, int idVerOld, int idVerNew, int uffOwner, int uffDest, long idAtto);
	public DefinitivitaCorrezioneErroreDatiSoggettoResponse inviaDefinitivitaCorrezioneErroreDatiSoggetto(DefinitivitaCorrezioneErroreDatiSoggettoRequest request);
	
	//chiamata alle procedure per inserire i flussi di ingresso
	public String registraFlussiIngressoPolizia(long idServizio, long idUfficio, long idProcedimento, long idAutorita, java.sql.Date dataEmissione, String descrNote, String descrProcedura);
    public String registraFlussiIngressoPoliziaAgg(long idServizio, long idUfficio, long idAutorita, String descrNumAccert, java.sql.Date dataEmissione, String descrNote, String descrProcedura);
    
    //Invio messaggi di errore avuti durante la registrazione in SITMP di messaggi spediti da MPS
    public PoliziaMsgErrorRequest createPoliziaMsgErrorRequest(long idMsg);
    public PoliziaMsgErrorResponse inviaMessaggiDiErrore(PoliziaMsgErrorRequest messaggio);

    public List<Integer> getUffDestinatario(int procedente,int uffOwner, long idProcedimento, String tipoProcedimento);
    
    public int getIdProcedente(int idUfficio);
    
    public void inserisciDocumentoAllegatoAtto(AttoForm attoForm, long idProc, int idUfficio);
    
    public TTpCatAtto getTtpCatAttoById(final long codiKIdCatAtto);
}
