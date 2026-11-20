package it.giustizia.smp.service.webservices;

import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.webservice.client.anbsc.ConfiscaDefinitivaRequest;
import it.giustizia.smp.webservice.client.anbsc.ConfiscaDefinitivaResponse;
import it.giustizia.smp.webservice.client.anbsc.ConfiscaPrimoGradoRequest;
import it.giustizia.smp.webservice.client.anbsc.ConfiscaPrimoGradoResponse;
import it.giustizia.smp.webservice.client.anbsc.ConfiscaSecondoGradoRequest;
import it.giustizia.smp.webservice.client.anbsc.ConfiscaSecondoGradoResponse;
import it.giustizia.smp.webservice.client.anbsc.RevocaConfiscaRequest;
import it.giustizia.smp.webservice.client.anbsc.RevocaConfiscaResponse;
import it.giustizia.smp.webservice.client.anbsc.RevocaSequestroRequest;
import it.giustizia.smp.webservice.client.anbsc.RevocaSequestroResponse;
import it.giustizia.smp.webservice.client.anbsc.RiunioneRequest;
import it.giustizia.smp.webservice.client.anbsc.RiunioneResponse;
import it.giustizia.smp.webservice.client.anbsc.SequestroRequest;
import it.giustizia.smp.webservice.client.anbsc.SequestroResponse;
import it.giustizia.smp.webservice.client.anbsc.StralcioRequest;
import it.giustizia.smp.webservice.client.anbsc.StralcioResponse;
import it.giustizia.smp.webservice.client.documentale.stub.Configuration;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.WebServiceTemplate;

public class AnbscWsService implements IAnbscWsService {

	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
    private WebServiceTemplate anbscServiceTemplate;

	public SequestroResponse Sequestro(SequestroRequest request) {
		try{
			return (!Configuration.isAbilitaANBSC()) ? null : (SequestroResponse) sendAndReceive(request);
		} catch(Exception ex) {
			logger.error("Errore in AnbscWsService.Sequestro" + ex);
			return null;
		}
	}
	
	public RevocaSequestroResponse RevocaSequestro(RevocaSequestroRequest request) {
		try{
			return (!Configuration.isAbilitaANBSC()) ? null : (RevocaSequestroResponse) sendAndReceive(request);
		} catch(Exception ex) {
			logger.error("Errore in AnbscWsService.RevocaSequestro" + ex);
			return null;
		}
	}
	
	public StralcioResponse Stralcio(StralcioRequest request) {
		try {
			return (!Configuration.isAbilitaANBSC()) ? null : (StralcioResponse) sendAndReceive(request);
		} catch(Exception ex) {
			logger.error("Errore in AnbscWsService.StralcioResponse" + ex);
			return null;
		}
	}
	
	public RiunioneResponse Riunione(RiunioneRequest request) {
		try {
			return (!Configuration.isAbilitaANBSC()) ? null : (RiunioneResponse) sendAndReceive(request);
		} catch(Exception ex) {
			logger.error("Errore in AnbscWsService.RiunioneResponse" + ex);
			return null;
		}
	}

	public ConfiscaPrimoGradoResponse ConfiscaPrimoGrado(ConfiscaPrimoGradoRequest request) {
		try{
			return (!Configuration.isAbilitaANBSC()) ? null : (ConfiscaPrimoGradoResponse) sendAndReceive(request);
		} catch(Exception ex) {
			logger.error("Errore in AnbscWsService.ConfiscaPrimoGrado" + ex);
			return null;
		}
	}

	public ConfiscaSecondoGradoResponse ConfiscaSecondoGrado(ConfiscaSecondoGradoRequest request) {
		try{
			return (!Configuration.isAbilitaANBSC()) ? null : (ConfiscaSecondoGradoResponse) sendAndReceive(request);
		} catch(Exception ex) {
			logger.error("Errore in AnbscWsService.ConfiscaSecondoGrado" + ex);
			return null;
		}
	}

	public ConfiscaDefinitivaResponse ConfiscaDefinitiva(ConfiscaDefinitivaRequest request) {
		try{
			return (!Configuration.isAbilitaANBSC()) ? null : (ConfiscaDefinitivaResponse) sendAndReceive(request);
		} catch(Exception ex) {
			logger.error("Errore in AnbscWsService.ConfiscaDefinitiva" + ex);
			return null;
		}
	}

	public RevocaConfiscaResponse RevocaConfisca(RevocaConfiscaRequest request) {
		try{
			return (!Configuration.isAbilitaANBSC()) ? null : (RevocaConfiscaResponse) sendAndReceive(request);
		} catch(Exception ex) {
			logger.error("Errore in AnbscWsService.RevocaConfisca" + ex);
			return null;
		}
	}

	private Object sendAndReceive(Object in) throws Exception {
		return Utility.sendAndReceive(anbscServiceTemplate, Constants.URI_WS_ANBSC, in);
	}
}
