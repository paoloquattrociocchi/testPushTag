package it.giustizia.smp.service.webservices;

import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.webservice.client.reginde.DettagliSoggettoPerCodice;
import it.giustizia.smp.webservice.client.reginde.DettagliSoggettoPerCodiceResponse;
import it.giustizia.smp.webservice.client.reginde.RicercaSoggetto;
import it.giustizia.smp.webservice.client.reginde.RicercaSoggettoResponse;
import it.giustizia.smp.webservice.client.reginde.Soggetto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.WebServiceTemplate;

public class RegindeService implements IRegindeService {

	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
  private WebServiceTemplate regindeServiceTemplate;
	   
	public Soggetto dettagliSoggettoPerCodice(String codiceFiscale) {
			DettagliSoggettoPerCodice request = new DettagliSoggettoPerCodice();
			request.setCodiceFiscale(codiceFiscale);
			try {
				@SuppressWarnings("unchecked")
				JAXBElement<DettagliSoggettoPerCodiceResponse> responseObj = (JAXBElement<DettagliSoggettoPerCodiceResponse>) sendAndReceive(request);
				DettagliSoggettoPerCodiceResponse response = responseObj.getValue();
				return (response == null || response.getReturn() == null) ? new Soggetto() : response.getReturn();
			} catch(Exception ex) {
				logger.error("Errore in regindeServiceTemplate.dettagliSoggettoPerCodice" + ex);
				return null;
			}
	}

	public List<Soggetto> ricercaSoggetto(String cognome) {
		if (!StringUtils.isNotBlank(cognome)) return null;
		RicercaSoggetto request = new RicercaSoggetto();
		request.setCognome(cognome);
		try{
			@SuppressWarnings("unchecked")
			JAXBElement<RicercaSoggettoResponse> responseObj = (JAXBElement<RicercaSoggettoResponse>) sendAndReceive(request);
			RicercaSoggettoResponse response = responseObj.getValue();
			return (response == null || response.getReturn() == null) ? new ArrayList<Soggetto>() : response.getReturn();
		} catch(Exception ex) {
			logger.error("Errore in regindeServiceTemplate.ricercaSoggetto" + ex);
			return null;
		}
	}

	private Object sendAndReceive(Object in) throws Exception {
		return Utility.sendAndReceive(regindeServiceTemplate, Constants.URI_WS_REGINDE, in);
	}
}
