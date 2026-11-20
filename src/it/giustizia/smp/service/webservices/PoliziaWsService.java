package it.giustizia.smp.service.webservices;


import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;
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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.WebServiceTemplate;

public class PoliziaWsService implements IPoliziaWsService {

	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
    private WebServiceTemplate poliziaServiceTemplate;

	public IndaginiSoggettiPropostiResponse inviaIndaginiSoggettiProposti(IndaginiSoggettiPropostiRequest request) {
		try {
			//IndaginiSoggettiPropostiResponse response = new IndaginiSoggettiPropostiResponse();
			return (IndaginiSoggettiPropostiResponse) sendAndReceive(request);
		} catch(Exception ex) {
			logger.error("Errore in poliziaServiceTemplate.IndaginiSoggettiPropostiResponse" + ex);
			return null;
		}
	}
	
	public ProposteMisurePersonaliResponse inviaProposteMisurePersonali(ProposteMisurePersonaliRequest request) {
		try {
			//IndaginiSoggettiPropostiResponse response = new IndaginiSoggettiPropostiResponse();
			return (ProposteMisurePersonaliResponse) sendAndReceive(request);
		} catch(Exception ex) {
			logger.error("Errore in poliziaServiceTemplate.ProposteMisurePersonaliResponse" + ex);
			return null;
		}
	}
	
	public ProposteMisurePatrimonialiResponse inviaProposteMisurePatrimoniali(ProposteMisurePatrimonialiRequest request) {
		try {
			return (ProposteMisurePatrimonialiResponse) sendAndReceive(request);
		} catch(Exception ex) {
			logger.error("Errore in poliziaServiceTemplate.ProposteMisurePatrimonialiResponse" + ex);
			return null;
		}
	}

	public IndaginiSoggettiPropostiTrCaResponse inviaIndaginiSoggettiPropostiTrCa(
			IndaginiSoggettiPropostiTrCaRequest request) {
		try {
			return (IndaginiSoggettiPropostiTrCaResponse) sendAndReceive(request);
		} catch(Exception ex) {
			logger.error("Errore in poliziaServiceTemplate.IndaginiSoggettiPropostiTrCaResponse" + ex);
			return null;
		}
	}

	public ParereRevocaModificaMisurePersonaliResponse inviaParereRevocaModificaMisurePersonali(
			ParereRevocaModificaMisurePersonaliRequest request) {
		try {
			return (ParereRevocaModificaMisurePersonaliResponse) sendAndReceive(request);
		} catch(Exception ex) {
			logger.error("Errore in poliziaServiceTemplate.ParereRevocaModificaMisurePersonaliResponse" + ex);
			return null;
		}
	}

	public VariazioneAnagraficheSoggettiInteressatiResponse inviaVariazioneAnagraficheSoggettiInteressati(
			VariazioneAnagraficheSoggettiInteressatiRequest request) {
		try {
			return (VariazioneAnagraficheSoggettiInteressatiResponse) sendAndReceive(request);
		} catch(Exception ex) {
			logger.error("Errore in poliziaServiceTemplate.VariazioneAnagraficheSoggettiInteressatiResponse" + ex);
			return null;
		}
	}

	public SequestroResponse inviaSequestro(SequestroRequest request) {
		try {
			return (SequestroResponse) sendAndReceive(request);
		} catch(Exception ex) {
			logger.error("Errore in poliziaServiceTemplate.SequestroResponse" + ex);
			return null;
		}
	}

	public RevocaSequestroResponse inviaRevocaSequestro(RevocaSequestroRequest request) {
		try {
			return (RevocaSequestroResponse) sendAndReceive(request);
		} catch(Exception ex) {
			logger.error("Errore in poliziaServiceTemplate.RevocaSequestroResponse" + ex);
			return null;
		}
	}

	public DecretiSospensioneRestituzioneBeniResponse inviaDecretiSospensioneRestituzioneBeni(
			DecretiSospensioneRestituzioneBeniRequest request) {
		try {
			return (DecretiSospensioneRestituzioneBeniResponse) sendAndReceive(request);
		} catch(Exception ex) {
			logger.error("Errore in poliziaServiceTemplate.DecretiSospensioneRestituzioneBeniResponse" + ex);
			return null;
		}
	}

	public DecretiProvvisoriApplicazioneResponse inviaDecretiProvvisoriApplicazione(
			DecretiProvvisoriApplicazioneRequest request) {
		try {
			return (DecretiProvvisoriApplicazioneResponse) sendAndReceive(request);
		} catch(Exception ex) {
			logger.error("Errore in poliziaServiceTemplate.DecretiProvvisoriApplicazioneResponse" + ex);
			return null;
		}
	}

	public DecretiProvvisoriRevocaResponse inviaDecretiProvvisoriRevoca(
			DecretiProvvisoriRevocaRequest request) {
		try {
			return (DecretiProvvisoriRevocaResponse) sendAndReceive(request);
		} catch(Exception ex) {
			logger.error("Errore in poliziaServiceTemplate.DecretiProvvisoriRevocaResponse" + ex);
			return null;
		}
	}

	public DecretiDecisoriIGradoPersonaliResponse inviaDecretiDecisoriIGradoPersonali(
			DecretiDecisoriIGradoPersonaliRequest request) {
		try {
			return (DecretiDecisoriIGradoPersonaliResponse) sendAndReceive(request);
		} catch(Exception ex) {
			logger.error("Errore in poliziaServiceTemplate.DecretiDecisoriIGradoPersonaliResponse" + ex);
			return null;
		}
	}

	public DecretiDecisoriIGradoPatrimonialiResponse inviaDecretiDecisoriIGradoPatrimoniali(
			DecretiDecisoriIGradoPatrimonialiRequest request) {
		try {
			return (DecretiDecisoriIGradoPatrimonialiResponse) sendAndReceive(request);
		} catch(Exception ex) {
			logger.error("Errore in poliziaServiceTemplate.DecretiDecisoriIGradoPatrimonialiResponse" + ex);
			return null;
		}
	}

	public DecretiDecisoriIIGradoPersonaliResponse inviaDecretiDecisoriIIGradoPersonali(
			DecretiDecisoriIIGradoPersonaliRequest request) {
		try {
			return (DecretiDecisoriIIGradoPersonaliResponse) sendAndReceive(request);
		} catch(Exception ex) {
			logger.error("Errore in poliziaServiceTemplate.DecretiDecisoriIIGradoPersonaliResponse" + ex);
			return null;
		}
	}

	public DecretiDecisoriIIGradoPatrimonialiResponse inviaDecretiDecisoriIIGradoPatrimoniali(
			DecretiDecisoriIIGradoPatrimonialiRequest request) {
		try {
			return (DecretiDecisoriIIGradoPatrimonialiResponse) sendAndReceive(request);
		} catch(Exception ex) {
			logger.error("Errore in poliziaServiceTemplate.DecretiDecisoriIIGradoPatrimonialiResponse" + ex);
			return null;
		}
	}

	public SentenzaCassazionePersonaliResponse inviaSentenzaCassazionePersonali(
			SentenzaCassazionePersonaliRequest request) {
		try {
			return (SentenzaCassazionePersonaliResponse) sendAndReceive(request);
		} catch(Exception ex) {
			logger.error("Errore in poliziaServiceTemplate.SentenzaCassazionePersonaliResponse" + ex);
			return null;
		}
	}

	public SentenzaCassazionePatrimonialiResponse inviaSentenzaCassazionePatrimoniali(
			SentenzaCassazionePatrimonialiRequest request) {
		try {
			return (SentenzaCassazionePatrimonialiResponse) sendAndReceive(request);
		} catch(Exception ex) {
			logger.error("Errore in poliziaServiceTemplate.SentenzaCassazionePatrimonialiResponse" + ex);
			return null;
		}
	}

	public DefinitivitaPersonaliResponse inviaDefinitivitaPersonali(
			DefinitivitaPersonaliRequest request) {
		try {
			return (DefinitivitaPersonaliResponse) sendAndReceive(request);
		} catch(Exception ex) {
			logger.error("Errore in poliziaServiceTemplate.DefinitivitaPersonaliResponse" + ex);
			return null;
		}
	}

	public DefinitivitaPatrimonialiResponse inviaDefinitivitaPatrimoniali(
			DefinitivitaPatrimonialiRequest request) {
		try {
			return (DefinitivitaPatrimonialiResponse) sendAndReceive(request);
		} catch(Exception ex) {
			logger.error("Errore in poliziaServiceTemplate.DefinitivitaPatrimonialiResponse" + ex);
			return null;
		}
	}

	public RichiestaAggravamentoMisuraPersonaleResponse inviaRichiestaAggravamentoMisuraPersonale(
			RichiestaAggravamentoMisuraPersonaleRequest request) {
		try {
			return (RichiestaAggravamentoMisuraPersonaleResponse) sendAndReceive(request);
		} catch(Exception ex) {
			logger.error("Errore in poliziaServiceTemplate.RichiestaAggravamentoMisuraPersonaleResponse" + ex);
			return null;
		}
	}

	public DecisorioAggravamentoMisuraPersonaleResponse inviaDecisorioAggravamentoMisuraPersonale(
			DecisorioAggravamentoMisuraPersonaleRequest request) {
		try {
			return (DecisorioAggravamentoMisuraPersonaleResponse) sendAndReceive(request);
		} catch(Exception ex) {
			logger.error("Errore in poliziaServiceTemplate.DecisorioAggravamentoMisuraPersonaleResponse" + ex);
			return null;
		}
	}

	public DefinitivitaAggravamentoMisuraPersonaleResponse inviaDefinitivitaAggravamentoMisuraPersonale(
			DefinitivitaAggravamentoMisuraPersonaleRequest request) {
		try {
			return (DefinitivitaAggravamentoMisuraPersonaleResponse) sendAndReceive(request);
		} catch(Exception ex) {
			logger.error("Errore in poliziaServiceTemplate.DefinitivitaAggravamentoMisuraPersonaleResponse" + ex);
			return null;
		}
	}

	public RichiestaModificaRevocaMisuraPersonaleResponse inviaRichiestaModificaRevocaMisuraPersonale(
			RichiestaModificaRevocaMisuraPersonaleRequest request) {
		try {
			return (RichiestaModificaRevocaMisuraPersonaleResponse) sendAndReceive(request);
		} catch(Exception ex) {
			logger.error("Errore in poliziaServiceTemplate.RichiestaModificaRevocaMisuraPersonaleResponse" + ex);
			return null;
		}
	}

	public DecisorioRevocaModificaMisuraPersonaleResponse inviaDecisorioRevocaModificaMisuraPersonale(
			DecisorioRevocaModificaMisuraPersonaleRequest request) {
		try {
			return (DecisorioRevocaModificaMisuraPersonaleResponse) sendAndReceive(request);
		} catch(Exception ex) {
			logger.error("Errore in poliziaServiceTemplate.DecisorioRevocaModificaMisuraPersonaleResponse" + ex);
			return null;
		}
	}

	public DefinitivitaRevocaModificaMisuraPersonaleResponse inviaDefinitivitaRevocaModificaMisuraPersonale(
			DefinitivitaRevocaModificaMisuraPersonaleRequest request) {
		try {
			return (DefinitivitaRevocaModificaMisuraPersonaleResponse) sendAndReceive(request);
		} catch(Exception ex) {
			logger.error("Errore in poliziaServiceTemplate.DefinitivitaRevocaModificaMisuraPersonaleResponse" + ex);
			return null;
		}
	}

	public DefinitivitaCorrezioneErroreDatiSoggettoResponse inviaDefinitivitaCorrezioneErroreDatiSoggetto(
			DefinitivitaCorrezioneErroreDatiSoggettoRequest request) {
		try {
			return (DefinitivitaCorrezioneErroreDatiSoggettoResponse) sendAndReceive(request);
		} catch(Exception ex) {
			logger.error("Errore in poliziaServiceTemplate.DefinitivitaCorrezioneErroreDatiSoggettoResponse" + ex);
			return null;
		}
	}
	
	public PoliziaMsgErrorResponse inviaMessaggiDiErrore(PoliziaMsgErrorRequest request) {
		try {
			return (PoliziaMsgErrorResponse) sendAndReceive(request);
		} catch(Exception ex) {
			logger.error("Errore in poliziaServiceTemplate.inviaMessaggiDiErrore" + ex);
			return null;
		}
	}

	private Object sendAndReceive(Object in) throws Exception {
		return Utility.sendAndReceive(poliziaServiceTemplate, Constants.URI_WS_POLIZIA, in);
	}
}
