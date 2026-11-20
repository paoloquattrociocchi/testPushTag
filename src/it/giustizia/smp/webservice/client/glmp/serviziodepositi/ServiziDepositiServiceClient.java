package it.giustizia.smp.webservice.client.glmp.serviziodepositi;

import it.giustizia.gestorelocale.accettazione.EventoDeposito;
import it.giustizia.gestorelocale.accettazione.esiti.AccettazioneException;
import it.giustizia.gestorelocale.accettazione.esiti.CercaRiepiloghi;
import it.giustizia.gestorelocale.accettazione.esiti.CercaRiepiloghiResponse;
import it.giustizia.gestorelocale.accettazione.esiti.RiepilogoEsiti;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;

import java.util.List;

import javax.xml.bind.JAXBElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.WebServiceTemplate;

public class ServiziDepositiServiceClient {
	@Autowired
	private WebServiceTemplate serviziDepositiServiceTemplate;

	@SuppressWarnings("unchecked")
	public List<RiepilogoEsiti> cercaRiepiloghi( CercaRiepiloghi cercaRiepiloghi,String codugneUffCurr) {
		try {
			
			it.giustizia.gestorelocale.accettazione.esiti.ObjectFactory factory = new it.giustizia.gestorelocale.accettazione.esiti.ObjectFactory();
			JAXBElement<CercaRiepiloghiResponse> jabe = (JAXBElement<CercaRiepiloghiResponse>)sendAndReceive(factory.createCercaRiepiloghi(cercaRiepiloghi),codugneUffCurr);
			CercaRiepiloghiResponse response = jabe.getValue();
			return response.getReturn();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
}
	
	public List<EventoDeposito> queryEventiDeposito(
	        String idRepository) throws AccettazioneException  {
		// TODO Da implementare se serve...
		return null;
	}
	
	
	public List<EventoDeposito> queryRiepologhi(
	       List<String> catId)
	        throws AccettazioneException {
		// TODO Da implementare se serve...
		return null;
	}

	private Object sendAndReceive(Object in,String codugne) throws Exception {
		// Modificicato per gestire la soap header 
		return Utility.sendAndReceiveGL(serviziDepositiServiceTemplate, Constants.URI_WS_SERVIZI_DEPOSITI, in,codugne);
	}
}
