package it.giustizia.smp.webservice.client.ricorsocassazione;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.WebServiceTemplate;

import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.webservice.client.documentale.stub.Configuration;

public class RicorsoCassazioneServiceClient {
	@Autowired
	private WebServiceTemplate ricorsoCassazioneServiceTemplate;
	
	private Logger logger = Logger.getLogger(getClass());

	public RicorsoResponse inviaRicorso(RicorsoRequest request) {
		try {
			logger.info("Chiamata servizio Cassazione RicorsoCassazioneServiceClient.inviaRicorso");
			RicorsoResponse response = (RicorsoResponse) sendAndReceive(request);
			if(response != null)
				logger.info("ESITO:" + response.getCodice() + " DESC:" + response.getDescrizione());
			logger.info("End chiamata servizio Cassazione RicorsoCassazioneServiceClient.inviaRicorso");
			return response;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Object sendAndReceive(Object in) throws Exception {
		return Utility.sendAndReceive(ricorsoCassazioneServiceTemplate, Constants.URI_WS_RICORSO_CASSAZIONE, in);
	}
}
