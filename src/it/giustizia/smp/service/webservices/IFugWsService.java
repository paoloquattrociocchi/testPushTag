package it.giustizia.smp.service.webservices;

import it.giustizia.smp.webservice.client.fug.AcquisisciModelloA;
import it.giustizia.smp.webservice.client.fug.AcquisisciModelloC;
import it.giustizia.smp.webservice.client.fug.AcquisisciModelloC2;
import it.giustizia.smp.webservice.client.fug.AcquisisciModelloD;
import it.giustizia.smp.webservice.client.fug.AcquisisciModelloE2;
import it.giustizia.smp.webservice.client.fug.AcquisisciModelloH;
import it.giustizia.smp.webservice.client.fug.RichiestaAnnullamentoModello;
import it.giustizia.smp.webservice.client.fug.modelli.RispostaModello;

public interface IFugWsService {

	public RispostaModello invioModello(AcquisisciModelloA modelloA, long idInvio);
	
	public RispostaModello invioModello(AcquisisciModelloC modelloC, long idInvio);
	
	public RispostaModello invioModello(AcquisisciModelloC2 modelloC2, long idInvio);
	
	public RispostaModello invioModello(AcquisisciModelloD modelloD, long idInvio);
	
	public RispostaModello invioModello(AcquisisciModelloE2 modelloE2, long idInvio);
	
	public RispostaModello invioModello(AcquisisciModelloH modelloH, long idInvio);
	
	public RispostaModello annullaModello(RichiestaAnnullamentoModello in, long idInvio);
}
