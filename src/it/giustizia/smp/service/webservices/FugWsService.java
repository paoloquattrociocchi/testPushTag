package it.giustizia.smp.service.webservices;

import it.giustizia.smp.webservice.client.FugWSClient;
import it.giustizia.smp.webservice.client.fug.AcquisisciModelloA;
import it.giustizia.smp.webservice.client.fug.AcquisisciModelloC;
import it.giustizia.smp.webservice.client.fug.AcquisisciModelloC2;
import it.giustizia.smp.webservice.client.fug.AcquisisciModelloD;
import it.giustizia.smp.webservice.client.fug.AcquisisciModelloE2;
import it.giustizia.smp.webservice.client.fug.AcquisisciModelloH;
import it.giustizia.smp.webservice.client.fug.RichiestaAnnullamentoModello;
import it.giustizia.smp.webservice.client.fug.modelli.RispostaModello;

import org.springframework.beans.factory.annotation.Autowired;

public class FugWsService implements IFugWsService {

	@Autowired
	private FugWSClient fugServiceClient;

	/**
	 * I Metodi forniscono in risposta il numero di protocollo fornito dal FUG se
	 * presente, null altrimenti Il servizio invoca un simulatore di web service
	 * deployato tramite SoapUI all'indirizzo
	 * 
	 * http://ac-rcatalani:8088/mockinvioFUGbinding?wsdl
	 * 
	 * Il servizio è invocato attraverso un SOAP Proxy tramite ESB, quindi nel
	 * file applicationContext.xml e` definito l'url del proxy sul NodoCentrale
	 * all'indirizzo
	 * 
	 * http://localhost:8080/NodoCentrale/http/Proxy_Routed/FugWS
	 * 
	 */

	public RispostaModello invioModello(AcquisisciModelloA in, long idInvio) {
		return fugServiceClient.invioModello(in, idInvio);
	}

	public RispostaModello invioModello(AcquisisciModelloC in, long idInvio) {
		return fugServiceClient.invioModello(in, idInvio);
	}

	public RispostaModello invioModello(AcquisisciModelloC2 in, long idInvio) {
		return fugServiceClient.invioModello(in, idInvio);
	}

	public RispostaModello invioModello(AcquisisciModelloD in, long idInvio) {
		return fugServiceClient.invioModello(in, idInvio);
	}

	public RispostaModello invioModello(AcquisisciModelloE2 in, long idInvio) {
		return fugServiceClient.invioModello(in, idInvio);
	}

	public RispostaModello invioModello(AcquisisciModelloH in, long idInvio) {
		return fugServiceClient.invioModello(in, idInvio);
	}
	
	public RispostaModello annullaModello(RichiestaAnnullamentoModello in, long idInvio) {
		return fugServiceClient.annullaModello(in, idInvio);
	}
}
