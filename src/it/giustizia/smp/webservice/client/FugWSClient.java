package it.giustizia.smp.webservice.client;

import it.giustizia.smp.dao.FugDao;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.webservice.client.documentale.stub.Configuration;
import it.giustizia.smp.webservice.client.fug.AcquisisciModelloA;
import it.giustizia.smp.webservice.client.fug.AcquisisciModelloC;
import it.giustizia.smp.webservice.client.fug.AcquisisciModelloC2;
import it.giustizia.smp.webservice.client.fug.AcquisisciModelloD;
import it.giustizia.smp.webservice.client.fug.AcquisisciModelloE2;
import it.giustizia.smp.webservice.client.fug.AcquisisciModelloH;
import it.giustizia.smp.webservice.client.fug.CodiceEsito;
import it.giustizia.smp.webservice.client.fug.EsitoFUGTipo;
import it.giustizia.smp.webservice.client.fug.RichiestaAnnullamentoModello;
import it.giustizia.smp.webservice.client.fug.RispostaModelloA;
import it.giustizia.smp.webservice.client.fug.RispostaModelloC;
import it.giustizia.smp.webservice.client.fug.RispostaModelloC2;
import it.giustizia.smp.webservice.client.fug.RispostaModelloD;
import it.giustizia.smp.webservice.client.fug.RispostaModelloE2;
import it.giustizia.smp.webservice.client.fug.RispostaModelloH;
import it.giustizia.smp.webservice.client.fug.RispostaModelloTipo;
import it.giustizia.smp.webservice.client.fug.RispostaRichiestaAnnullamentoModello;
import it.giustizia.smp.webservice.client.fug.modelli.EsitoFUG;
import it.giustizia.smp.webservice.client.fug.modelli.RispostaModello;
import it.giustizia.smp.webservice.utility.JAXBUtil;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;

public class FugWSClient {
	private static char RICEVUTO = 'R';
	private static char INVIATO = 'I';
	private static char SINCRONO = 'S';
	private static int MAX_CODE_ERRORE_LEN = 3;
	private static int MAX_DESC_ERRORE_LEN = 300;

	@Autowired
	private WebServiceTemplate fugServiceTemplate;
	@Autowired
	private FugDao fugDao;

	private Logger logger = Logger.getLogger(getClass());

	private RispostaModello getRispostaServizio(RispostaModelloTipo in, String modello) {
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

	private void log(String modello, String codUffMittente, long idInvio, String idProtocollo, char direzione, Object in) {
		if (!modello.equals(""))
			logger.info("Invio Modello '" + modello + "'...");
		insertMessaggi(codUffMittente, idInvio, idProtocollo, direzione, in);
	}

	private void insertMessaggi(String codUffMittente, long idInvio, String idProtocollo, char direzione, Object in) {
		byte[] xml = JAXBUtil.printXML(in);
		Map<String, String> esito = fugDao.insertFugMessaggi(new Long(idInvio), idProtocollo, direzione, SINCRONO, Utility.serializeInBlob(xml));
		String key = esito.keySet().iterator().next();
		if (esito.containsKey(CodiceEsito.OK.value()))
			logger.debug(key + "-" + esito.get(key));
		else {
			logger.error(key + "-" + esito.get(key));
		}
	}

	private RispostaModelloTipo gestioneEccezione(Exception e) {
		logger.error(e.getMessage(), e);
		String codiceErrore = "X01";
		String codiceCampo = "X1";
		String descrizioneErrore = StringUtils.defaultString(e.getMessage(), "---");
		if (e instanceof SoapFaultClientException) {
			SoapFaultClientException se = (SoapFaultClientException) e;
			codiceCampo = "X2";
			codiceErrore = StringUtils.defaultString(se.getFaultCode().toString(), "X02");
			descrizioneErrore = StringUtils.defaultString(se.getFaultStringOrReason(), "...");
		} else if (e instanceof URISyntaxException) {
			URISyntaxException se = (URISyntaxException) e;
			codiceCampo = "X3";
			codiceErrore = StringUtils.defaultString(se.getInput(), "X03");
			descrizioneErrore = StringUtils.defaultString(se.getReason(), "___");
		}
		RispostaModelloTipo out = new RispostaModelloTipo();
		EsitoFUGTipo eft = new EsitoFUGTipo();
		eft.setCodiceSezione("X");
		eft.setCodiceCampo(codiceCampo);
		eft.setCodiceErrore(StringUtils.left(codiceErrore, MAX_CODE_ERRORE_LEN));
		eft.setDescrizioneErrore(StringUtils.left(descrizioneErrore, MAX_DESC_ERRORE_LEN));
		out.getEsito().add(eft);
		
		logger.error(eft.getCodiceSezione() + "-" + eft.getCodiceCampo());
		logger.error(codiceErrore + "-" + descrizioneErrore);

		return out;
	}

	public RispostaModello invioModello(AcquisisciModelloA in, long idInvio) {
		String modello = "A";
		String codUffMittente = in.getRichiestaModelloA().getDatiIdentificativiInvio().getCodUffMittente();
		log(modello, codUffMittente, idInvio, null, INVIATO, in);
		RispostaModelloA response = new RispostaModelloA();
		try {
			response = (RispostaModelloA) sendAndReceive(in);
		} catch (Exception e) {
			response.setRispostaModelloA(gestioneEccezione(e));
		}
		log(modello, codUffMittente, idInvio, ((response != null) ? response.getRispostaModelloA().getIdProtocollo() : null), RICEVUTO, response);
		return getRispostaServizio(((response != null) ? response.getRispostaModelloA() : null), modello);
	}

	public RispostaModello invioModello(AcquisisciModelloC in, long idInvio) {
		String modello = "C";
		String codUffMittente = in.getRichiestaModelloC().getDatiIdentificativiInvio().getCodUffMittente();
		log(modello, codUffMittente, idInvio, null, INVIATO, in);
		RispostaModelloC response = new RispostaModelloC();
		try {
			response = (RispostaModelloC) sendAndReceive(in);
		} catch (Exception e) {
			response.setRispostaModelloC(gestioneEccezione(e));
		}
		log(modello, codUffMittente, idInvio, ((response != null) ? response.getRispostaModelloC().getIdProtocollo() : null), RICEVUTO, response);
		return getRispostaServizio(((response != null) ? response.getRispostaModelloC() : null), modello);
	}

	public RispostaModello invioModello(AcquisisciModelloC2 in, long idInvio) {
		String modello = "C2";
		String codUffMittente = in.getRichiestaModelloC2().getDatiIdentificativiInvio().getCodUffMittente();
		log(modello, codUffMittente, idInvio, null, INVIATO, in);
		RispostaModelloC2 response = new RispostaModelloC2();
		try {
			response = (RispostaModelloC2) sendAndReceive(in);
		} catch (Exception e) {
			response.setRispostaModelloC2(gestioneEccezione(e));
		}
		log(modello, codUffMittente, idInvio, ((response != null) ? response.getRispostaModelloC2().getIdProtocollo() : null), RICEVUTO, response);
		return getRispostaServizio(((response != null) ? response.getRispostaModelloC2() : null), modello);
	}

	public RispostaModello invioModello(AcquisisciModelloD in, long idInvio) {
		String modello = "D";
		String codUffMittente = in.getRichiestaModelloD().getDatiIdentificativiInvio().getCodUffMittente();
		log(modello, codUffMittente, idInvio, null, INVIATO, in);
		RispostaModelloD response = new RispostaModelloD();
		try {
			response = (RispostaModelloD) sendAndReceive(in);
		} catch (Exception e) {
			response.setRispostaModelloD(gestioneEccezione(e));
		}
		log(modello, codUffMittente, idInvio, ((response != null) ? response.getRispostaModelloD().getIdProtocollo() : null), RICEVUTO, response);
		return getRispostaServizio(((response != null) ? response.getRispostaModelloD() : null), modello);
	}

	public RispostaModello invioModello(AcquisisciModelloE2 in, long idInvio) {
		String modello = "E2";
		String codUffMittente = in.getRichiestaModelloE2().getDatiIdentificativiInvio().getCodUffMittente();
		log(modello, codUffMittente, idInvio, null, INVIATO, in);
		RispostaModelloE2 response = new RispostaModelloE2();
		try {
			response = (RispostaModelloE2) sendAndReceive(in);
		} catch (Exception e) {
			response.setRispostaModelloE2(gestioneEccezione(e));
		}
		log(modello, codUffMittente, idInvio, ((response != null) ? response.getRispostaModelloE2().getIdProtocollo() : null), RICEVUTO, response);
		return getRispostaServizio(((response != null) ? response.getRispostaModelloE2() : null), modello);
	}

	public RispostaModello invioModello(AcquisisciModelloH in, long idInvio) {
		String modello = "H";
		String codUffMittente = in.getRichiestaModelloH().getDatiIdentificativiInvio().getCodUffMittente();
		log(modello, codUffMittente, idInvio, null, INVIATO, in);
		RispostaModelloH response = new RispostaModelloH();
		try {
			response = (RispostaModelloH) sendAndReceive(in);
		} catch (Exception e) {
			response.setRispostaModelloH(gestioneEccezione(e));
		}
		log(modello, codUffMittente, idInvio, ((response != null) ? response.getRispostaModelloH().getIdProtocollo() : null), RICEVUTO, response);
		return getRispostaServizio(((response != null) ? response.getRispostaModelloH() : null), modello);
	}

	public RispostaModello annullaModello(RichiestaAnnullamentoModello in, long idInvio) {
		String modello = "";
		logger.info("Invio richiesta annullamento modello...");
		String codUffMittente = in.getRichiestaRichiestaAnnullamentoModello().getDatiIdentificativiInvio().getCodUffMittente();
		log(modello, codUffMittente, idInvio, null, INVIATO, in);
		RispostaRichiestaAnnullamentoModello response = new RispostaRichiestaAnnullamentoModello();
		try {
			response = (RispostaRichiestaAnnullamentoModello) sendAndReceive(in);
		} catch (Exception e) {
			response.setRispostaRichiestaAnnullamentoModello(gestioneEccezione(e));
		}
		log(modello, codUffMittente, idInvio, ((response != null) ? response.getRispostaRichiestaAnnullamentoModello().getIdProtocollo() : null), RICEVUTO, response);
		return getRispostaServizio(((response != null) ? response.getRispostaRichiestaAnnullamentoModello() : null), modello);
	}

	private Object sendAndReceive(Object in) throws Exception {
		if(Configuration.isAbilitaFug())
			return Utility.sendAndReceive(fugServiceTemplate, Constants.URI_WS_EQUITALIA_FUG, in);
		else
			return null;
	}
}
