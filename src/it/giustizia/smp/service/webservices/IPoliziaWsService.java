package it.giustizia.smp.service.webservices;

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
import it.giustizia.smp.webservice.client.polizia.IndaginiSoggettiPropostiRequest;
import it.giustizia.smp.webservice.client.polizia.IndaginiSoggettiPropostiResponse;
import it.giustizia.smp.webservice.client.polizia.IndaginiSoggettiPropostiTrCaRequest;
import it.giustizia.smp.webservice.client.polizia.IndaginiSoggettiPropostiTrCaResponse;
import it.giustizia.smp.webservice.client.polizia.ParereRevocaModificaMisurePersonaliRequest;
import it.giustizia.smp.webservice.client.polizia.ParereRevocaModificaMisurePersonaliResponse;
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


public interface IPoliziaWsService {
	
	//7.1 IdTipoServizio 5
	public IndaginiSoggettiPropostiResponse inviaIndaginiSoggettiProposti(IndaginiSoggettiPropostiRequest request);
	//8.1 IdTipoServizio 3
	public ProposteMisurePersonaliResponse inviaProposteMisurePersonali(ProposteMisurePersonaliRequest request);
	//8.2 IdTipoServizio 4	
	public ProposteMisurePatrimonialiResponse inviaProposteMisurePatrimoniali(ProposteMisurePatrimonialiRequest request);
	//8.3 IdTipoServizio 26
	public IndaginiSoggettiPropostiTrCaResponse inviaIndaginiSoggettiPropostiTrCa(IndaginiSoggettiPropostiTrCaRequest request);
	//8.4 IdTipoServizio 27
	public ParereRevocaModificaMisurePersonaliResponse inviaParereRevocaModificaMisurePersonali(ParereRevocaModificaMisurePersonaliRequest request);
	//8.5 IdTipoServizio 28	
	public VariazioneAnagraficheSoggettiInteressatiResponse inviaVariazioneAnagraficheSoggettiInteressati(VariazioneAnagraficheSoggettiInteressatiRequest request);
	//8.6 Sequestro IdTipoServizio 1
	public SequestroResponse inviaSequestro(SequestroRequest request);
	//8.6 RevocaSequestro IdTipoServizio 2
	public RevocaSequestroResponse inviaRevocaSequestro(RevocaSequestroRequest request);
	//8.7 IdTipoServizio 6
	public DecretiSospensioneRestituzioneBeniResponse inviaDecretiSospensioneRestituzioneBeni(DecretiSospensioneRestituzioneBeniRequest request);
	//8.8 Applicazione IdTipoServizio 7
	public DecretiProvvisoriApplicazioneResponse inviaDecretiProvvisoriApplicazione(DecretiProvvisoriApplicazioneRequest request);
	//8.8 Revoca IdTipoServizio 8
	public DecretiProvvisoriRevocaResponse inviaDecretiProvvisoriRevoca(DecretiProvvisoriRevocaRequest request);
	//8.9 Revoca IdTipoServizio 9	
	public DecretiDecisoriIGradoPersonaliResponse inviaDecretiDecisoriIGradoPersonali(DecretiDecisoriIGradoPersonaliRequest request);
	//8.10 IdTipoServizio 10	
	public DecretiDecisoriIGradoPatrimonialiResponse inviaDecretiDecisoriIGradoPatrimoniali(DecretiDecisoriIGradoPatrimonialiRequest request);
	//8.11 IdTipoServizio 11	
	public DecretiDecisoriIIGradoPersonaliResponse inviaDecretiDecisoriIIGradoPersonali(DecretiDecisoriIIGradoPersonaliRequest request);
	//8.12 IdTipoServizio 12	
	public DecretiDecisoriIIGradoPatrimonialiResponse inviaDecretiDecisoriIIGradoPatrimoniali(DecretiDecisoriIIGradoPatrimonialiRequest request);
	//8.13 IdTipoServizio 13
	public SentenzaCassazionePersonaliResponse inviaSentenzaCassazionePersonali(SentenzaCassazionePersonaliRequest request);
	//8.14 IdTipoServizio 14
	public SentenzaCassazionePatrimonialiResponse inviaSentenzaCassazionePatrimoniali(SentenzaCassazionePatrimonialiRequest request);
	//8.15 IdTipoServizio 15
	public DefinitivitaPersonaliResponse inviaDefinitivitaPersonali(DefinitivitaPersonaliRequest request);
	//8.16 IdTipoServizio 16
	public DefinitivitaPatrimonialiResponse inviaDefinitivitaPatrimoniali(DefinitivitaPatrimonialiRequest request);
	//8.17 IdTipoServizio 29
	public RichiestaAggravamentoMisuraPersonaleResponse inviaRichiestaAggravamentoMisuraPersonale(RichiestaAggravamentoMisuraPersonaleRequest request);
	//8.18 IdTipoServizio 30
	public DecisorioAggravamentoMisuraPersonaleResponse inviaDecisorioAggravamentoMisuraPersonale(DecisorioAggravamentoMisuraPersonaleRequest request);
	//8.19 IdTipoServizio 31
	public DefinitivitaAggravamentoMisuraPersonaleResponse inviaDefinitivitaAggravamentoMisuraPersonale(DefinitivitaAggravamentoMisuraPersonaleRequest request);
	//8.20 IdTipoServizio 32
	public RichiestaModificaRevocaMisuraPersonaleResponse inviaRichiestaModificaRevocaMisuraPersonale(RichiestaModificaRevocaMisuraPersonaleRequest request);
	//8.21 IdTipoServizio 33
	public DecisorioRevocaModificaMisuraPersonaleResponse inviaDecisorioRevocaModificaMisuraPersonale(DecisorioRevocaModificaMisuraPersonaleRequest request);
	//8.22 IdTipoServizio 34
	public DefinitivitaRevocaModificaMisuraPersonaleResponse inviaDefinitivitaRevocaModificaMisuraPersonale(DefinitivitaRevocaModificaMisuraPersonaleRequest request);
	//8.23 IdTipoServizio 35
	public DefinitivitaCorrezioneErroreDatiSoggettoResponse inviaDefinitivitaCorrezioneErroreDatiSoggetto(DefinitivitaCorrezioneErroreDatiSoggettoRequest request);
	
}
