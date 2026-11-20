package it.giustizia.smp.webservice.client.glmp.servizitrasmissione;

import javax.xml.bind.JAXBElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.ws.client.core.WebServiceTemplate;

import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.webservice.utility.MockResponseGLUtil;

public class ServiziTrasmissioneServiceClient {
	@Autowired
	private WebServiceTemplate serviziTrasmissioneServiceTemplate;
	
	@Autowired
	private ReloadableResourceBundleMessageSource configParameterSource;
	
	@Autowired
    private MockResponseGLUtil mockResponseGLUtil;

	public InviaComunicazioneResponse inviaComunicazione(InviaComunicazione request,Notifica notifica) {
		
		/*
		HttpClientParams params=new HttpClientParams();
		params.setSoTimeout(2000);
		HttpClient httpClient=new HttpClient();
		httpClient.setParams(params);
		WebServiceMessageSender messageSender=new CommonsHttpMessageSender(httpClient);
		serviziTrasmissioneServiceTemplate.setMessageSender(messageSender);
		*/
		
		InviaComunicazioneResponse response = null;
		
		
		try {
			//Modificata perchè non gestiva _InviaComunicazione_QNAME
			@SuppressWarnings("unchecked")
			String codugeUffMittente = request.getFascicolo().getUfficio();
			
			String mockResponse = configParameterSource.getMessage("MOCK_RESPONSE_SERVIZIO_GL", null, null);
			if(mockResponse != null && mockResponse.equals("S")) {
				//F.C. Implementata mockResponseGLUtil getMockGLResponse
				response = mockResponseGLUtil.getMockGLInviaComunicazioneResponse(request,codugeUffMittente,notifica);
			}
			else {
				JAXBElement<InviaComunicazioneResponse> jabe = (JAXBElement<InviaComunicazioneResponse>)sendAndReceiveGL(new ObjectFactory().createInviaComunicazione(request),codugeUffMittente);
				response = jabe.getValue();
			}
			
			
			
			
			return response;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public QueryStatoTrasmissioneResponse queryStatoTrasmissione(String idTrasmissione) {
		try {
			QueryStatoTrasmissione queryStatoTrasmissione = new QueryStatoTrasmissione();
			queryStatoTrasmissione.setIdTrasmissione(idTrasmissione);
			@SuppressWarnings("unchecked")
			JAXBElement<QueryStatoTrasmissioneResponse> jabe = (JAXBElement<QueryStatoTrasmissioneResponse>)sendAndReceive(new ObjectFactory().createQueryStatoTrasmissione(queryStatoTrasmissione));
			QueryStatoTrasmissioneResponse response = jabe.getValue();
			return response;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public DownloadRicevutaResponse downloadRicevuta(String idRicevuta) {
		try {
			DownloadRicevuta downloadRicevuta = new DownloadRicevuta();
			downloadRicevuta.setIdRicevuta(idRicevuta);
			@SuppressWarnings("unchecked")
			JAXBElement<DownloadRicevutaResponse> jabe = (JAXBElement<DownloadRicevutaResponse>)sendAndReceive(new ObjectFactory().createDownloadRicevuta(downloadRicevuta));
			DownloadRicevutaResponse response = jabe.getValue();
			return response;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public RecuperaRapportoTrasmissioneResponse recuperaRapportoTrasmissione(String idTrasmissione) {
		try {
			RecuperaRapportoTrasmissione recuperaRapportoTrasmissione = new RecuperaRapportoTrasmissione();
			recuperaRapportoTrasmissione.setIdTrasmissione(idTrasmissione);
			@SuppressWarnings("unchecked")
			JAXBElement<RecuperaRapportoTrasmissioneResponse> jabe = (JAXBElement<RecuperaRapportoTrasmissioneResponse>)sendAndReceive(new ObjectFactory().createRecuperaRapportoTrasmissione(recuperaRapportoTrasmissione));
			RecuperaRapportoTrasmissioneResponse response = jabe.getValue();
			return response;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	private Object sendAndReceive(Object in) throws Exception {
		return Utility.sendAndReceive(serviziTrasmissioneServiceTemplate, Constants.URI_WS_SERVIZI_TRASMISSIONE, in);
	}


	private Object sendAndReceiveGL(Object in,String codugeUffMittente) throws Exception {
		return Utility.sendAndReceiveGL(serviziTrasmissioneServiceTemplate, Constants.URI_WS_SERVIZI_TRASMISSIONE, in,codugeUffMittente);
	}

}
