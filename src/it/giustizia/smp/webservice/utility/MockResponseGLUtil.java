package it.giustizia.smp.webservice.utility;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.giustizia.gestorelocale.accettazione.servizi.AccettaDeposito;
import it.giustizia.gestorelocale.accettazione.servizi.AccettaDepositoResponse;
import it.giustizia.gestorelocale.accettazione.servizi.AssegnaDeposito;
import it.giustizia.gestorelocale.accettazione.servizi.AssegnaDepositoResponse;
import it.giustizia.smp.dao.FugDao;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.webservice.client.fug.CodiceEsito;
import it.giustizia.smp.webservice.client.glmp.servizitrasmissione.InfoDestinatario;
import it.giustizia.smp.webservice.client.glmp.servizitrasmissione.InfoTrasmissione;
import it.giustizia.smp.webservice.client.glmp.servizitrasmissione.InviaComunicazione;
import it.giustizia.smp.webservice.client.glmp.servizitrasmissione.InviaComunicazioneResponse;
import it.giustizia.smp.webservice.client.glmp.servizitrasmissione.Notifica;
import it.giustizia.smp.webservice.client.glmp.servizitrasmissione.RisultatoComunicazione;
@Service
public class MockResponseGLUtil {
	
	public final static  String  GL_DESCRIZIONE_ERRORE	="ERRORE AUTOGENERATO PER PROVA";
	public final static  String  GL_MAIL_TEST	="GL_TEST@SITMP.IT";
	public final static  String  GL_NOME_ELENCO	="GL_TEST";
	public final static  String  GL_ACK	="ACK";
	private final static  char SINCRONO = 'S';
	
	private static  Logger logger = Logger.getLogger(MockResponseGLUtil.class);
	
	@Autowired
	private   FugDao fugDao;
	
	public AccettaDepositoResponse getMockGLAccettaDeposito(AccettaDeposito accettaDeposito, String codugneUffCurr) {
		AccettaDepositoResponse response = new AccettaDepositoResponse();
		
		String ret = GL_ACK;
		response.setReturn(ret );
		
		//TODO da implementare!!!
		
		return response;
	}
	
	
	public AssegnaDepositoResponse  getMockGLAssegnaDeposito(AssegnaDeposito assegnaDeposito, String codugneUffCurr)
	{
		AssegnaDepositoResponse response = new AssegnaDepositoResponse();
		
		return response;
	}

	public InviaComunicazioneResponse getMockGLInviaComunicazioneResponse(InviaComunicazione request, String codugeUffMittente, Notifica notifica) {
		
		
		InviaComunicazioneResponse ret = new InviaComunicazioneResponse();
		RisultatoComunicazione response = new RisultatoComunicazione();
		
		
		
		for (InfoDestinatario infoDest : notifica.getDestinatari()) {
			
			InfoDestinatario destinatario = new InfoDestinatario();
			
			destinatario.setCodice(infoDest.getCodice());
			destinatario.setRuolo(infoDest.getRuolo());
			/*
			 * if (getRandomBoolean()) {
			 * 
			 * InfoEccezione infoErr = new InfoEccezione();
			 * infoErr.setDestinatario(destinatario);
			 * infoErr.setMessaggioEccezione(GL_DESCRIZIONE_ERRORE);
			 * response.getEccezioni().add(infoErr ); } else {
			 */
				InfoTrasmissione info = new InfoTrasmissione();				
				destinatario.setCodice(infoDest.getCodice());
				destinatario.setRuolo(infoDest.getRuolo());
				info.setDestinatario(destinatario );
			
				info.setIdTrasmissione(this.getRandomIdTrasmissione());
				info.setMail(GL_MAIL_TEST);
				info.setNomeElenco(GL_NOME_ELENCO);
				response.getTrasmissioni().add(info);
			//}
			
			
		}
		
		ret.setReturn(response);
		return ret;
	}
	
	
	
	
	
	
	private  boolean getRandomBoolean() {
		Random randomBoolean = new Random();
		
		return randomBoolean.nextBoolean();
	}
	
	private String getRandomIdTrasmissione() {
		UUID uuid = UUID.randomUUID();
		//DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		// idTrasmissione = df.format(new Date());		
		String idTrasmissione = uuid.toString().replaceAll("-", "");
		return idTrasmissione;
	}
	
	private   void insertMessaggi(String codUffMittente, long idInvio, String idProtocollo, char direzione, Object in) {
		byte[] xml = JAXBUtil.printXML(in);
		Map<String, String> esito = fugDao.insertFugMessaggi(new Long(idInvio), idProtocollo, direzione, SINCRONO, Utility.serializeInBlob(xml));
		String key = esito.keySet().iterator().next();
		if (esito.containsKey(CodiceEsito.OK.value()))
			logger.debug(key + "-" + esito.get(key));
		else {
			logger.error(key + "-" + esito.get(key));
		}
	}
	
	private  void log(String modello, String codUffMittente, long idInvio, String idProtocollo, char direzione, Object in) {
		if (!modello.equals(""))
			logger.info("Invio Modello '" + modello + "'...");
		insertMessaggi(codUffMittente, idInvio, idProtocollo, direzione, in);
	}
	
	
}
