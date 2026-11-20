package it.giustizia.gestorelocale.accettazione.servizi;

import javax.activation.DataHandler;
import javax.xml.bind.JAXBElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.ws.client.core.WebServiceTemplate;

import it.giustizia.gestorelocale.accettazione.esiti.AccettazioneException;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.webservice.utility.MockResponseGLUtil;

public class ServiziAccettazioneServiceClient {
	@Autowired
	private WebServiceTemplate serviziAccettazioneServiceTemplate;
	
	@Autowired
    private MockResponseGLUtil mockResponseGLUtil;
	
	@Autowired
	private ReloadableResourceBundleMessageSource configParameterSource;

	@SuppressWarnings("unchecked")
	public String accettaDeposito( AccettaDeposito accettaDeposito, String codugneUffCurr) {
		try {
			it.giustizia.gestorelocale.accettazione.servizi.ObjectFactory factory = new it.giustizia.gestorelocale.accettazione.servizi.ObjectFactory();
			
			String mockResponse = configParameterSource.getMessage("MOCK_RESPONSE_SERVIZIO_GL", null, null);
			if(mockResponse != null && mockResponse.equals("S")) {
				//F.C. Implementata mockResponseGLUtil getMockGLResponse
				AccettaDepositoResponse response = mockResponseGLUtil.getMockGLAccettaDeposito(accettaDeposito,codugneUffCurr);
				return response.getReturn();
			}
			else {
				JAXBElement<AccettaDepositoResponse>  jabe = (JAXBElement<AccettaDepositoResponse>)sendAndReceiveGL( factory.createAccettaDeposito(accettaDeposito),codugneUffCurr) ;
				AccettaDepositoResponse response = jabe.getValue();
				return response.getReturn();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	

	@SuppressWarnings("unchecked")
	public void assegnaDeposito( AssegnaDeposito assegnaDeposito, String codugneUffCurr) {
		try {
			it.giustizia.gestorelocale.accettazione.servizi.ObjectFactory factory = new it.giustizia.gestorelocale.accettazione.servizi.ObjectFactory();
			
			String mockResponse = configParameterSource.getMessage("MOCK_RESPONSE_SERVIZIO_GL", null, null);
			if(mockResponse != null && mockResponse.equals("S")) {
				AssegnaDepositoResponse response = mockResponseGLUtil.getMockGLAssegnaDeposito(assegnaDeposito, codugneUffCurr);
				
			}
			else {
				JAXBElement<AssegnaDepositoResponse>  jabe = (JAXBElement<AssegnaDepositoResponse>)sendAndReceiveGL( factory.createAssegnaDeposito(assegnaDeposito),codugneUffCurr) ;
				AssegnaDepositoResponse response = jabe.getValue();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public DataHandler downloadContenutoDeposito( DownloadContenutoDeposito downloadContenutoDeposito,String codugneUffCurr) throws AccettazioneException {
		try {
			it.giustizia.gestorelocale.accettazione.servizi.ObjectFactory factory = new it.giustizia.gestorelocale.accettazione.servizi.ObjectFactory();
			JAXBElement<DownloadContenutoDepositoResponse>  jabe = (JAXBElement<DownloadContenutoDepositoResponse>)sendAndReceive( factory.createDownloadContenutoDeposito(downloadContenutoDeposito)) ;
			DownloadContenutoDepositoResponse response = jabe.getValue();
			return response.getReturn();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private Object sendAndReceive(Object in) throws Exception {
		return Utility.sendAndReceive(serviziAccettazioneServiceTemplate, Constants.URI_WS_SERVIZI_ACCETTAZIONE, in);
	}
	private Object sendAndReceiveGL(Object in, final String codugne) throws Exception {
		return Utility.sendAndReceiveGL(serviziAccettazioneServiceTemplate, Constants.URI_WS_SERVIZI_ACCETTAZIONE, in,codugne);
	}
}
