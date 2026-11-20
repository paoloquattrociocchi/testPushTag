package it.giustizia.smp.webservice.client.albo;

import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.WebServiceTemplate;

public class AlboWSService {
	@Autowired
	private WebServiceTemplate alboServiceTemplate;

	/**
	 * Gets person list.
	 */
	public ElencoResponse elenco(ElencoRequest request) {
		try {
			ElencoResponse response = (ElencoResponse) sendAndReceive(request);
			return response;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Object sendAndReceive(Object in) throws Exception {
		return Utility.sendAndReceive(alboServiceTemplate, Constants.URI_WS_ALBO, in);
	}
}
