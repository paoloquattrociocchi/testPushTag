package it.giustizia.smp.webservice.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.giustizia.smp.dao.FugDao;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.webservice.client.fug.AcquisisciModelloA;
import it.giustizia.smp.webservice.client.fug.AcquisisciModelloC;
import it.giustizia.smp.webservice.client.fug.AcquisisciModelloC2;
import it.giustizia.smp.webservice.client.fug.AcquisisciModelloD;
import it.giustizia.smp.webservice.client.fug.AcquisisciModelloH;
import it.giustizia.smp.webservice.client.fug.CodiceEsito;
import it.giustizia.smp.webservice.client.fug.EsitoFUGTipo;
import it.giustizia.smp.webservice.client.fug.RichiestaAnnullamentoModello;
import it.giustizia.smp.webservice.client.fug.RispostaModelloA;
import it.giustizia.smp.webservice.client.fug.RispostaModelloC;
import it.giustizia.smp.webservice.client.fug.RispostaModelloC2;
import it.giustizia.smp.webservice.client.fug.RispostaModelloD;
import it.giustizia.smp.webservice.client.fug.RispostaModelloH;
import it.giustizia.smp.webservice.client.fug.RispostaModelloTipo;
import it.giustizia.smp.webservice.client.fug.RispostaRichiestaAnnullamentoModello;
import it.giustizia.smp.webservice.client.fug.modelli.EsitoFUG;
import it.giustizia.smp.webservice.client.fug.modelli.RispostaModello;
@Service
public class MockResponseFUGUtil {
	
	public final static  String  FUG_DESCRIZIONE_ERRORE	="ERRORE AUTOGENERATO PER PROVA";
	public final static  String  FUG_CODICE_SEZIONE_ERRORE	="O";
	public final static  String  FUG_CODICE_CAMPO_ERRORE		="O2";
	private final static  char RICEVUTO = 'R';
	private final static  char INVIATO = 'I';
	private final static  char SINCRONO = 'S';
	
	private static  Logger logger = Logger.getLogger(MockResponseFUGUtil.class);
	
	@Autowired
	private   FugDao fugDao;

	public RispostaModello getMockModelloAResponse(AcquisisciModelloA request, long idFugInvio) {
		String modello = "A";
		String codUffMittente = request.getRichiestaModelloA().getDatiIdentificativiInvio().getCodUffMittente();
		log(modello, codUffMittente, idFugInvio, null, INVIATO, request);
		RispostaModelloA response = new RispostaModelloA();
		
		String idProtocollo = "";
		try 
		{
			
			idProtocollo = getRandomProtocollo();
		} catch (Exception ex) {
			
		}
		RispostaModelloTipo rispostaModelloTipo = new RispostaModelloTipo();
		rispostaModelloTipo.setIdProtocollo(idProtocollo);
		
		response.setRispostaModelloA(rispostaModelloTipo);
		
		//se esce true inserisco errore
		if(getRandomBoolean()) {
			EsitoFUGTipo esito = new EsitoFUGTipo();
			esito.setDescrizioneErrore(FUG_DESCRIZIONE_ERRORE);
			esito.setCodiceErrore(Constants.FUG_STATO_INVIATO_CON_ERRORI);
			esito.setCodiceSezione(FUG_CODICE_SEZIONE_ERRORE);
			esito.setCodiceCampo(FUG_CODICE_CAMPO_ERRORE);
			rispostaModelloTipo.getEsito().add(esito);
			rispostaModelloTipo.setIdProtocollo(RispostaModello.NO_ID_PROTOCOLLO);
			response.setRispostaModelloA(rispostaModelloTipo );
		} 
		log(modello, codUffMittente, idFugInvio, ((response != null) ? response.getRispostaModelloA().getIdProtocollo() : null), RICEVUTO, response);
		return getRispostaServizio(((response != null) ? response.getRispostaModelloA() : null), modello);
	}
	
	
	public RispostaModello getMockModelloDResponse(AcquisisciModelloD request, long idFugInvio) {
		String modello = "D";
		String codUffMittente = request.getRichiestaModelloD().getDatiIdentificativiInvio().getCodUffMittente();
		log(modello, codUffMittente, idFugInvio, null, INVIATO, request);
		RispostaModelloD response = new RispostaModelloD();
		
		String idProtocollo = "";
		try 
		{
			
			idProtocollo = getRandomProtocollo();
		} catch (Exception ex) {
			
		}
		RispostaModelloTipo rispostaModelloTipo = new RispostaModelloTipo();
		rispostaModelloTipo.setIdProtocollo(idProtocollo);
		response.setRispostaModelloD(rispostaModelloTipo);
		
		//se esce true inserisco errore
		if(getRandomBoolean()) {
			EsitoFUGTipo esito = new EsitoFUGTipo();
			esito.setDescrizioneErrore(FUG_DESCRIZIONE_ERRORE);
			esito.setCodiceErrore(Constants.FUG_STATO_INVIATO_CON_ERRORI);
			esito.setCodiceSezione(FUG_CODICE_SEZIONE_ERRORE);
			esito.setCodiceCampo(FUG_CODICE_CAMPO_ERRORE);
		
			rispostaModelloTipo.getEsito().add(esito);
			rispostaModelloTipo.setIdProtocollo(RispostaModello.NO_ID_PROTOCOLLO);
			response.setRispostaModelloD(rispostaModelloTipo );
		} 
		log(modello, codUffMittente, idFugInvio, ((response != null) ? response.getRispostaModelloD().getIdProtocollo() : null), RICEVUTO, response);
		return getRispostaServizio(((response != null) ? response.getRispostaModelloD() : null), modello);
	}
	
	public  RispostaModello getMockModelloCResponse (AcquisisciModelloC request, long idFugInvio) {
		String modello = "C";
		String codUffMittente = request.getRichiestaModelloC().getDatiIdentificativiInvio().getCodUffMittente();
		log(modello, codUffMittente, idFugInvio, null, INVIATO, request);
		RispostaModelloC response = new RispostaModelloC();
		
		String idProtocollo = "";
		try 
		{
			
			idProtocollo = getRandomProtocollo();
		} catch (Exception ex) {
			
		}
		RispostaModelloTipo rispostaModelloTipo = new RispostaModelloTipo();
		rispostaModelloTipo.setIdProtocollo(idProtocollo);
		response.setRispostaModelloC(rispostaModelloTipo);
		
		//se esce true inserisco errore
		if(getRandomBoolean()) {
			EsitoFUGTipo esito = new EsitoFUGTipo();
			esito.setDescrizioneErrore(FUG_DESCRIZIONE_ERRORE);
			esito.setCodiceErrore(Constants.FUG_STATO_INVIATO_CON_ERRORI);
			esito.setCodiceSezione(FUG_CODICE_SEZIONE_ERRORE);
			esito.setCodiceCampo(FUG_CODICE_CAMPO_ERRORE);
			
			rispostaModelloTipo.getEsito().add(esito);
			rispostaModelloTipo.setIdProtocollo(RispostaModello.NO_ID_PROTOCOLLO);
			response.setRispostaModelloC(rispostaModelloTipo );
		} 
		log(modello, codUffMittente, idFugInvio, ((response != null) ? response.getRispostaModelloC().getIdProtocollo() : null), RICEVUTO, response);
		return getRispostaServizio(((response != null) ? response.getRispostaModelloC() : null), modello);
	}

	public  RispostaModello getMockModelloC2Response(AcquisisciModelloC2 request, long idFugInvio) {
		String modello = "C2";
		String codUffMittente = request.getRichiestaModelloC2().getDatiIdentificativiInvio().getCodUffMittente();
		log(modello, codUffMittente, idFugInvio, null, INVIATO, request);
		RispostaModelloC2 response = new RispostaModelloC2();
		
		String idProtocollo = "";
		try 
		{
			
			idProtocollo = getRandomProtocollo();
		} catch (Exception ex) {
			
		}
		RispostaModelloTipo rispostaModelloTipo = new RispostaModelloTipo();
		rispostaModelloTipo.setIdProtocollo(idProtocollo);
		response.setRispostaModelloC2(rispostaModelloTipo);
		
		//se esce true inserisco errore
		if(getRandomBoolean()) {
			EsitoFUGTipo esito = new EsitoFUGTipo();
			esito.setDescrizioneErrore(FUG_DESCRIZIONE_ERRORE);
			esito.setCodiceErrore(Constants.FUG_STATO_INVIATO_CON_ERRORI);
			esito.setCodiceSezione(FUG_CODICE_SEZIONE_ERRORE);
			esito.setCodiceCampo(FUG_CODICE_CAMPO_ERRORE);
			
			rispostaModelloTipo.getEsito().add(esito);
			rispostaModelloTipo.setIdProtocollo(RispostaModello.NO_ID_PROTOCOLLO);
			response.setRispostaModelloC2(rispostaModelloTipo );
		} 
		log(modello, codUffMittente, idFugInvio, ((response != null) ? response.getRispostaModelloC2().getIdProtocollo() : null), RICEVUTO, response);
		return getRispostaServizio(((response != null) ? response.getRispostaModelloC2() : null), modello);
	}
	
	public RispostaModello getMockModelloHResponse(AcquisisciModelloH request, long idFugInvio) {
		String modello = "H";
		String codUffMittente = request.getRichiestaModelloH().getDatiIdentificativiInvio().getCodUffMittente();
		log(modello, codUffMittente, idFugInvio, null, INVIATO, request);
		RispostaModelloH response = new RispostaModelloH();
		
		String idProtocollo = "";
		try 
		{
			
			idProtocollo = getRandomProtocollo();
		} catch (Exception ex) {
			
		}
		RispostaModelloTipo rispostaModelloTipo = new RispostaModelloTipo();
		rispostaModelloTipo.setIdProtocollo(idProtocollo);
		response.setRispostaModelloH(rispostaModelloTipo);
		
		//se esce true inserisco errore
		if(getRandomBoolean()) {
			EsitoFUGTipo esito = new EsitoFUGTipo();
			esito.setDescrizioneErrore(FUG_DESCRIZIONE_ERRORE);
			esito.setCodiceErrore(Constants.FUG_STATO_INVIATO_CON_ERRORI);
			esito.setCodiceSezione(FUG_CODICE_SEZIONE_ERRORE);
			esito.setCodiceCampo(FUG_CODICE_CAMPO_ERRORE);
			
			rispostaModelloTipo.getEsito().add(esito);
			rispostaModelloTipo.setIdProtocollo(RispostaModello.NO_ID_PROTOCOLLO);
			response.setRispostaModelloH(rispostaModelloTipo );
		} 
		log(modello, codUffMittente, idFugInvio, ((response != null) ? response.getRispostaModelloH().getIdProtocollo() : null), RICEVUTO, response);
		return getRispostaServizio(((response != null) ? response.getRispostaModelloH() : null), modello);
	}
	
	public  RispostaModello  getMockAnnullamentoModelloResponse(RichiestaAnnullamentoModello request, long idFugInvio) {
		String modello = "";
		String codUffMittente = request.getRichiestaRichiestaAnnullamentoModello().getDatiIdentificativiInvio().getCodUffMittente();
		log(modello, codUffMittente, idFugInvio, null, INVIATO, request);
		RispostaRichiestaAnnullamentoModello response = new RispostaRichiestaAnnullamentoModello();
		
		String idProtocollo = "";
		try 
		{
			
			idProtocollo = getRandomProtocollo();
		} catch (Exception ex) {
			
		}
		RispostaModelloTipo rispostaModelloTipo = new RispostaModelloTipo();
		rispostaModelloTipo.setIdProtocollo(idProtocollo);
		response.setRispostaRichiestaAnnullamentoModello(rispostaModelloTipo);
		
		//se esce true inserisco errore
		if(getRandomBoolean()) {
			EsitoFUGTipo esito = new EsitoFUGTipo();
			esito.setDescrizioneErrore(FUG_DESCRIZIONE_ERRORE);
			esito.setCodiceErrore(Constants.FUG_STATO_INVIATO_CON_ERRORI);
			esito.setCodiceSezione(FUG_CODICE_SEZIONE_ERRORE);
			esito.setCodiceCampo(FUG_CODICE_CAMPO_ERRORE);
			
			rispostaModelloTipo.getEsito().add(esito);
			rispostaModelloTipo.setIdProtocollo(RispostaModello.NO_ID_PROTOCOLLO);
			response.setRispostaRichiestaAnnullamentoModello(rispostaModelloTipo );
		} 
		log(modello, codUffMittente, idFugInvio, ((response != null) ? response.getRispostaRichiestaAnnullamentoModello().getIdProtocollo() : null), RICEVUTO, response);
		return getRispostaServizio(((response != null) ? response.getRispostaRichiestaAnnullamentoModello() : null), modello);
	}
	
	private  boolean getRandomBoolean() {
		Random randomBoolean = new Random();
		
		return randomBoolean.nextBoolean();
	}
	
	private String getRandomProtocollo() {
		
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String protocollo = df.format(new Date());
	
		return protocollo;
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
	
	private  RispostaModello getRispostaServizio(RispostaModelloTipo in, String modello) {
		RispostaModello risposta = new RispostaModello();
		if (in != null) {
			risposta.setIdProtocollo(in.getIdProtocollo());
			if (Utility.isValid(in.getEsito())) {
				List<EsitoFUG> esiti = new ArrayList<EsitoFUG>();
				for (EsitoFUGTipo item : in.getEsito()) {
					EsitoFUG e = new EsitoFUG();
					e.setCodiceCampo(item.getCodiceCampo());
					e.setCodiceErrore(item.getCodiceErrore());
					e.setCodiceSezione(item.getCodiceSezione());
					e.setDescrizioneErrore(item.getDescrizioneErrore());
					esiti.add(e);
				}
				risposta.setEsito(esiti);
			}
		}
		String msg = (modello.equals("") ? "richiesta annullamento modello" : "invio Modello '" + modello + "'");
		logger.info("Risposta " + msg + ": IDProt='" + risposta.getIdProtocollo() + "'");
		return risposta;
	}
}
