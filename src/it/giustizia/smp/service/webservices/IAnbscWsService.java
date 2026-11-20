package it.giustizia.smp.service.webservices;

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

public interface IAnbscWsService {
	
	public SequestroResponse Sequestro (SequestroRequest request);
	
	public RevocaSequestroResponse RevocaSequestro (RevocaSequestroRequest request);
	
	public ConfiscaPrimoGradoResponse ConfiscaPrimoGrado (ConfiscaPrimoGradoRequest request); 
	
	public ConfiscaSecondoGradoResponse ConfiscaSecondoGrado (ConfiscaSecondoGradoRequest request);
	
	/*public ConfiscaDefinitivaResponse ConfiscaDefinitiva (ConfiscaDefinitivaRequest request);
	
	public RevocaConfiscaResponse RevocaConfisca (RevocaConfiscaRequest request);
	
	public RiunioneResponse Riunione (RiunioneRequest request);
	
	public StralcioResponse Stralcio (StralcioRequest request);*/
}
