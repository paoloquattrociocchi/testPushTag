package it.giustizia.smp.webservice.client.casellario.ricerca;



import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;

import javax.xml.namespace.QName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapHeaderElement;
import org.springframework.ws.soap.SoapMessage;

public class CasellarioRicercaServiceClient {
	@Autowired
	private WebServiceTemplate casellarioRicercaServiceTemplate;

	public DATIRISPOSTACERTIFICATO ricercaCertificato(DATIRICHIESTACERTIFICATO request,final String authUsername,final  String authPassword) {
		
		/*
		// SIPPI 
		request.getDATIUTENTE().setCOGNOMEUTENTE("Datya Entry");
		request.getDATIUTENTE().setNOMEUTENTE("Sippi");
		request.getDATIUTENTE().setUSERNAMESIPPI("sippidataentry03");
		DATIUFFICIO datiufficio = request.getDATIUTENTE().getDATIUFFICIO();
		datiufficio.setCODICETIPOUFFICIO("408");
		datiufficio.setCODICESEDEUFFICIO("048017");
		request.getDATIUTENTE().setDATIUFFICIO(datiufficio);
		
		// NUOVI
		request.getDATIUTENTE().setCOGNOMEUTENTE("tribunale");
		request.getDATIUTENTE().setNOMEUTENTE("sitmp");
		request.getDATIUTENTE().setUSERNAMESIPPI("sitmp.tribunale");
		DATIUFFICIO datiufficio = request.getDATIUTENTE().getDATIUFFICIO();
		datiufficio.setCODICETIPOUFFICIO("405");
		datiufficio.setCODICESEDEUFFICIO("082053");
		request.getDATIUTENTE().setDATIUFFICIO(datiufficio);
		*/
		/*
		request.getDATIUTENTE().setUSERNAMESIPPI("sitmp.tribunale");
		*/

		Utility.setServiceUri(casellarioRicercaServiceTemplate, Constants.URI_WS_CASELLARIO_RICERCA);
		DATIRISPOSTACERTIFICATO response = (DATIRISPOSTACERTIFICATO) casellarioRicercaServiceTemplate.marshalSendAndReceive(request, new WebServiceMessageCallback() {
		    public void doWithMessage(WebServiceMessage message) {
		        SoapMessage soapMessage = (SoapMessage) message;
		    	 QName datiAutenticazione = new QName("http://it/mig/sippi/service/richiestaCertificato/type","DATI_AUTENTICAZIONE");
		    	 //QName datiAutenticazione = new QName("","DATI_AUTENTICAZIONE");
		    	 SoapHeaderElement e = soapMessage.getSoapHeader().addHeaderElement(datiAutenticazione);
		    	 e.addAttribute(new QName("username"), authUsername);
		    	 e.addAttribute(new QName("password"), authPassword);
		    	 
		    }
		});
		return response;
	}
}
