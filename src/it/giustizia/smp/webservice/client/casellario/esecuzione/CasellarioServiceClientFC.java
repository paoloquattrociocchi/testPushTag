package it.giustizia.smp.webservice.client.casellario.esecuzione;

import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.webservice.client.documentale.stub.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.WebServiceTemplate;

public class CasellarioServiceClientFC {
	@Autowired
	private WebServiceTemplate casellarioServiceTemplateFC;

	public DATIRISPOSTATRASFERIMENTO inviaProvvedimento(DATICHIAMATATRASFERIMENTO request) {
		/*
		request.getDATIUTENTE().setCOGNOMEUTENTE("Datya Entry");
		request.getDATIUTENTE().setNOMEUTENTE("Sippi");
		request.getDATIUTENTE().setUSERNAMESIPPI("sippidataentry03");
		DATIUFFICIO datiufficio = request.getDATIUTENTE().getDATIUFFICIO();
		datiufficio.setCODICETIPOUFFICIO("405");
		datiufficio.setCODICESEDEUFFICIO("048017");
		request.getDATIUTENTE().setDATIUFFICIO(datiufficio);
		*/
		
		/* 
		request.getDATIUTENTE().setCOGNOMEUTENTE("tribunale");
		request.getDATIUTENTE().setNOMEUTENTE("sitmp");
		request.getDATIUTENTE().setUSERNAMESIPPI("sitmp.tribunale");
		DATIUFFICIO datiufficio = request.getDATIUTENTE().getDATIUFFICIO();
		datiufficio.setCODICETIPOUFFICIO("405");
		datiufficio.setCODICESEDEUFFICIO("082053");
		request.getDATIUTENTE().setDATIUFFICIO(datiufficio);
		 	
		request.getDATIUTENTE().setUSERNAMESIPPI("sitmp.tribunale");
		*/
		try {
			DATIRISPOSTATRASFERIMENTO response = (DATIRISPOSTATRASFERIMENTO) sendAndReceive(request);
			return response;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Object sendAndReceive(Object in) throws Exception {
		if(Configuration.isAbilitaCasellario())
			return Utility.sendAndReceive(casellarioServiceTemplateFC, Constants.URI_WS_CASELLARIO_FC, in);
		else
			return null;
	}
}
