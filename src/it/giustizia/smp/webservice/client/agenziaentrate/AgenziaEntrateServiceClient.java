package it.giustizia.smp.webservice.client.agenziaentrate;



import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;

import javax.xml.bind.JAXBElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.WebServiceTemplate;

public class AgenziaEntrateServiceClient {
	@Autowired
	private WebServiceTemplate agenziaEntrateServiceTemplate;

	/*
	public DatiPersonaFisicaType interrogazioneDatiAnagraficiPersonaFisica(InterrogazioneDatiAnagraficiPersonaFisicaType request) {
		DatiPersonaFisicaType response = (DatiPersonaFisicaType) agenziaEntrateServiceTemplate.marshalSendAndReceive(request);
		System.out.println("ESITO:" + response.getMessaggio() );
		return response;
	}
	*/
	public DatiPersonaFisicaType interrogazioneDatiAnagraficiPersonaFisica(InterrogazioneDatiAnagraficiPersonaFisicaType request) throws Exception {
		//Modificata perchè non gestiva _InviaComunicazione_QNAME
		@SuppressWarnings("unchecked")
		JAXBElement<DatiPersonaFisicaType> jabe = (JAXBElement<DatiPersonaFisicaType>)sendAndReceive(new ObjectFactory().createInterrogazioneDatiAnagraficiPersonaFisica(request));
		DatiPersonaFisicaType response = jabe.getValue();
		return response;
	}
	
	public DatiSoggettoDiversoType interrogazioneDatiAnagraficiSoggettiDiversi(InterrogazioneDatiAnagraficiSoggettiDiversiType request) throws Exception {
		//Modificata perchè non gestiva _InviaComunicazione_QNAME
		@SuppressWarnings("unchecked")
		JAXBElement<DatiSoggettoDiversoType> jabe = (JAXBElement<DatiSoggettoDiversoType>)sendAndReceive(new ObjectFactory().createInterrogazioneDatiAnagraficiSoggettiDiversi(request));
		DatiSoggettoDiversoType response = jabe.getValue();
		return response;
	}

	private Object sendAndReceive(Object in) throws Exception {
		return Utility.sendAndReceive(agenziaEntrateServiceTemplate, Constants.URI_WS_AGENZIA_ENTRATE, in);
	}
}
