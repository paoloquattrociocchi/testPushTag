package it.giustizia.smp.webservice.client.allineamentoFascicoliPDoc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.rmi.RemoteException;

import javax.activation.DataHandler;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;
import org.springframework.stereotype.Component;

import it.giustizia.smp.model.TAnUtenzePdoc;
import it.giustizia.smp.webservice.client.allineamentoFascicoliPDoc.model.general.recuperoFascicoloRequest.Autenticazione;
import it.giustizia.smp.webservice.client.allineamentoFascicoliPDoc.model.general.recuperoFascicoloRequest.Firma;
import it.giustizia.smp.webservice.client.allineamentoFascicoliPDoc.model.general.recuperoFascicoloResponse.Risposta;
import it.giustizia.smp.webservice.client.documentale.stub.Configuration;
import it.giustizia.smp.webservice.client.documentale.stub.EfGiustiziaServiceStub;
import it.giustizia.smp.webservice.client.documentale.stub.EfGiustiziaServiceStub.RecuperaFascicolo;
import it.giustizia.smp.webservice.client.documentale.stub.EfGiustiziaServiceStub.RecuperaFascicoloResponse;

//GC 15/07/2020: Aggiunto il logger
import org.apache.log4j.Logger;


@Component
public class AllineamentoFascicoliPDocClient implements IAllineamentoFascicoliPDocClient {

	private final static String URL_DOCUMENTALE;
	private static EfGiustiziaServiceStub stubDoc = null;

	static {
		URL_DOCUMENTALE = Configuration.getURL();
		System.out.println(URL_DOCUMENTALE);
		try {
			stubDoc = new it.giustizia.smp.webservice.client.documentale.stub.EfGiustiziaServiceStub();
		} catch (AxisFault e) {
			e.printStackTrace();
		}
		ServiceClient scDoc = stubDoc._getServiceClient();
		Options optionsDoc = scDoc.getOptions();
		optionsDoc.setProperty(HTTPConstants.CHUNKED, false);
		stubDoc._getServiceClient().setOptions(optionsDoc);
	}

	private Logger logger = Logger.getLogger(getClass());
	
	public Risposta recuperaFascicolo(String codiceFascicolo, TAnUtenzePdoc utenzaPdoc) {

		Firma firma = new Firma();
		Autenticazione autenticazione = generaAutenticazioneFirma(utenzaPdoc);
		firma.setAutenticazione(autenticazione);
		firma.setCodicefascicolo(codiceFascicolo);

		DataHandler dhReq = new DataHandler(marshaller(firma), "base64");
		RecuperaFascicolo rf = new RecuperaFascicolo();
		rf.setFirma(dhReq);

		RecuperaFascicoloResponse rfr = null;

		try {
			rfr = stubDoc.recuperaFascicolo(rf);
		} catch (RemoteException e) {
			System.out.println("Errore AllineamentoFascicoliPDocClient.recuperaFascicolo:" + e);
			return null;
		}

		DataHandler dhRes = rfr.get_return();
		Risposta risposta = (Risposta) unmarhaller(dhRes, Risposta.class);

		if (risposta != null && risposta.getMessaggio() != null) {
			if (logger.isDebugEnabled()) {
				if (risposta.getMessaggio().isEsito())
					logger.debug("  	2.2.1) " + " Fascicolo " + codiceFascicolo + " recuperato con successo");
				else
					logger.debug("  	2.2.1) " + codiceFascicolo + " " + risposta.getMessaggio().getMessagebody());
			}
		} else
			System.out.println("RISPOSTA NON PERVENUTA! L'oggetto 'risposta' è nullo!");

		return risposta;
	}

	/**
	 * Genera l'oggetto Autenticazione con i valori corretti,comune a tutte le firme
	 *
	 * @return
	 */
	private Autenticazione generaAutenticazioneFirma(TAnUtenzePdoc utenzaPdoc) {

		Autenticazione autenticazione = new Autenticazione();
		// modifica per gestire casio in cui non è assente sul db l'utenza pdoc!
		if (utenzaPdoc.getNumeUtenzaPdoc() != null) {
			autenticazione.setCodiceapplicazione(utenzaPdoc.getDescCodiceApplicazione());
			autenticazione.setPassword(utenzaPdoc.getDescPassword());
			autenticazione.setIdutente(BigInteger.valueOf(Long.valueOf(utenzaPdoc.getDescUtentePdoc())));
			autenticazione.setIdufficio(
					BigInteger.valueOf(Long.valueOf(utenzaPdoc.getNumeUtenzaPdoc().toString())));
		} else {
			System.out.println(
					"---->ERRORE UTENZA PDOC <----  Verificare la corretta associazione in tabella !!!!");
		}

		return autenticazione;
	}

	private byte[] marshaller(Object request) {

		byte[] firmaBytes = new byte[1000];
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			JAXBContext contextObj = JAXBContext.newInstance(request.getClass());
			Marshaller marshallerObj = contextObj.createMarshaller();
			marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshallerObj.marshal(request, bos);
			firmaBytes = bos.toByteArray();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return firmaBytes;
	}

	/**
	 * Restituisce l'oggetto generale di risposta per ogni operation
	 *
	 * @param dh
	 *            elemento DataHandler da analizzare contenente i dati di risposta
	 * @param responseClass
	 *            specifica la classe in cui deve essere formalizzata la risposta
	 * @return oggetto che contiene tutte le informazioni da estrapolare in modo specializzato per ogni
	 *         operation
	 */
	private Object unmarhaller(DataHandler dh, Class<?> responseClass) {

		Object risposta = null;
		try {
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			dh.writeTo(bout);
			JAXBContext jc = JAXBContext.newInstance(responseClass);
			ByteArrayInputStream bis = new ByteArrayInputStream(bout.toByteArray());

			Unmarshaller u = jc.createUnmarshaller();
			Class<?> appoClass = Class.forName(responseClass.getName());
			risposta = appoClass.cast(u.unmarshal(bis));
		} catch (JAXBException e) {
			System.out.println("Errore in unmarhaller:" + e);
		} catch (ClassNotFoundException e) {
			System.out.println("Errore in unmarhaller:" + e);
		} catch (IOException e) {
			System.out.println("Errore unmarhaller:" + e);
		}
		return risposta;
	}

}