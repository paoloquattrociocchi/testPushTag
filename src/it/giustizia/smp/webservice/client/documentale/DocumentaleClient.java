package it.giustizia.smp.webservice.client.documentale;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
//import java.net.Authenticator;
//import java.net.PasswordAuthentication;
import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.axiom.attachments.ByteArrayDataSource;
import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;

import it.giustizia.smp.model.TAnUtenzePdoc;
import it.giustizia.smp.model.TAtto;
import it.giustizia.smp.service.AttoService;
import it.giustizia.smp.service.webservices.IDocumentaleService;
import it.giustizia.smp.webservice.client.documentale.model.general.Autenticazione;
import it.giustizia.smp.webservice.client.documentale.model.general.Documento;
import it.giustizia.smp.webservice.client.documentale.model.general.Fascicolo;
import it.giustizia.smp.webservice.client.documentale.model.general.Render;
import it.giustizia.smp.webservice.client.documentale.model.general.Risposta;
import it.giustizia.smp.webservice.client.documentale.model.general.Security;
import it.giustizia.smp.webservice.client.documentale.model.request.AggiuntaFascicoloRequest;
import it.giustizia.smp.webservice.client.documentale.model.request.CreaFascicoloRequest;
import it.giustizia.smp.webservice.client.documentale.model.request.EliminaDocumentoRequest;
import it.giustizia.smp.webservice.client.documentale.model.request.FirmaRemotaRequest;
import it.giustizia.smp.webservice.client.documentale.model.request.InserimentoDocumentoRequest;
import it.giustizia.smp.webservice.client.documentale.model.request.MonitorJobRequest;
import it.giustizia.smp.webservice.client.documentale.model.request.RecuperoDocumentoRequest;
import it.giustizia.smp.webservice.client.documentale.model.request.RenderRequest;
import it.giustizia.smp.webservice.client.documentale.model.request.RiunioneRequest;
import it.giustizia.smp.webservice.client.documentale.model.request.StralcioRequest;
import it.giustizia.smp.webservice.client.documentale.model.request.TestConnessioneRequest;
import it.giustizia.smp.webservice.client.documentale.model.request.UpdateDocumentoRequest;
import it.giustizia.smp.webservice.client.documentale.model.response.AggiuntaFascicoloResponse;
import it.giustizia.smp.webservice.client.documentale.model.response.EliminaDocumentoResponse;
import it.giustizia.smp.webservice.client.documentale.model.response.InserimentoDocumentoResponse;
import it.giustizia.smp.webservice.client.documentale.model.response.MonitorJobResponse;
import it.giustizia.smp.webservice.client.documentale.model.response.RecuperoDocumentoResponse;
import it.giustizia.smp.webservice.client.documentale.model.response.RenderResponse;
import it.giustizia.smp.webservice.client.documentale.model.response.RiunioneFascicoliResponse;
import it.giustizia.smp.webservice.client.documentale.model.response.StralcioFascicoloResponse;
import it.giustizia.smp.webservice.client.documentale.model.response.UpdateDocumentoResponse;
import it.giustizia.smp.webservice.client.documentale.stub.Configuration;
import it.giustizia.smp.webservice.client.documentale.stub.EfGiustiziaServiceStub;



@Component
public class DocumentaleClient implements IDocumentaleClient{
	
	@Autowired
	private AttoService attoService;
	
	@Autowired
	private IDocumentaleService	documentaleService;

	
	private final static String URL_DOCUMENTALE;
	private static EfGiustiziaServiceStub stub = null;
	static{
		URL_DOCUMENTALE = Configuration.getURL();
//		if(Configuration.isProxyAbilitato())
//			setCredenzialiProxy();
		try {
			stub = new EfGiustiziaServiceStub();
		} catch (AxisFault e) {
			e.printStackTrace();
		}
		ServiceClient sc = stub._getServiceClient();
		Options options = sc.getOptions();
		options.setProperty(HTTPConstants.CHUNKED, false);
		stub._getServiceClient().setOptions(options);
	}
	

	/**
	 * Metodo invocato per testConnessione
	 * @return oggetto contenente tutte le informazioni del messaggio di risposta
	 */
	public Risposta testConnessione(TAnUtenzePdoc utenzaPdoc){
		TestConnessioneRequest request = new TestConnessioneRequest();
    	Autenticazione autenticazione = generaAutenticazioneFirma(utenzaPdoc);
    	request.setAutenticazione(autenticazione);
    	
    	EfGiustiziaServiceStub.TestConnessione requestStub = new EfGiustiziaServiceStub.TestConnessione();
	   	DataHandler da = new DataHandler(marshaller(request),"base64");
	   	requestStub.setFirma(da);
	   	EfGiustiziaServiceStub.TestConnessioneResponse response = null;
		try {
			response = stub.testConnessione(requestStub);
		} catch (RemoteException e) {
			System.out.println("Errore DocumentaleClient.testConnessione:" + e);
			return null;
		}
	   	DataHandler responseHandler = response.get_return();
	    Risposta result = (Risposta) unmarhaller(responseHandler, Risposta.class);
	    
	    return result;
    }
	
	/**
	 * Creazione fascicolo sulla piattaforma Documentale
	 * @param codiceFascicolo codice del fascicolo da creare
	 * @param codiceFascicoloPadre codice del fascicolo padre in cui creare un sottofascicolo
	 *  		con codice specificato in codiceFascicolo, null se non si crea un sotto fascicolo
	 * @param oggettoFascicolo oggetto del fascicolo
	 * @param GL codice del GL da abilitare sul fascicolo per consentire al GL di depositare la ricevuta
	 * @return oggetto contenente tutte le informazioni del messaggio di risposta
	 */
	public Risposta creaFascicolo(String codiceFascicolo, String codiceFascicoloPadre, String oggettoFascicolo, List<BigInteger> GL, TAnUtenzePdoc utenzaPdoc){
		CreaFascicoloRequest request = new CreaFascicoloRequest();
    	Autenticazione autenticazione = generaAutenticazioneFirma(utenzaPdoc);
    	request.setAutenticazione(autenticazione);
    	Fascicolo fascicolo = new Fascicolo();
    	fascicolo.setCodicefascicolo(codiceFascicolo);
    	fascicolo.setCodicefascicolopadre(codiceFascicoloPadre);
    	fascicolo.setOggettofascicolo(oggettoFascicolo);
    	Security security = new Security();
		if(GL!=null && GL.size()>0)
			security.setUfficioabilitato(GL);
    	fascicolo.setSecurity(security);
    	request.setFascicolo(fascicolo);
    	
    	EfGiustiziaServiceStub.CreaFascicolo requestStub = new EfGiustiziaServiceStub.CreaFascicolo();
	   	DataHandler da = new DataHandler(marshaller(request),"base64");
	   	requestStub.setFirma(da);
	   	EfGiustiziaServiceStub.CreaFascicoloResponse response = null;
		try {
			response = stub.creaFascicolo(requestStub);
		} catch (RemoteException e) {
			System.out.println("Errore DocumentaleClient.creaFascicolo:" + e);
			return null;
		}
	   	DataHandler responseHandler = response.get_return();
	    Risposta result = (Risposta) unmarhaller(responseHandler, Risposta.class);
	    //GC 10/07/2020: Modifica per miglioramento log pdoc
	    if (result != null && result.getMessaggio() != null) {
			if (result.getMessaggio().isEsito())
				System.out.println("  2.1.1)" + " Fascicolo " + codiceFascicolo + " creato con successo");
			else
				System.out.println("  2.1.1)" + " Fascicolo " + codiceFascicolo
						+ " non creato; Messaggio PDOC: " + result.getMessaggio().getMessagebody());
		} else
			System.out.println("RISPOSTA NON PERVENUTA! L'oggetto 'result' è nullo!");
	    //GC 10/07/2020: Fine Modifica per miglioramento log pdoc

    	return result;
    }
	
	/**
	 * Metodo per caricare un documento sulla piattaforma documentale
	 * @param oggetto del documento
	 * @param tipoDocumento la tipologia di file da caricare (txt,pdf,jpg,zip,...)
	 * @param fileName il nome del file comprensivo di estensione
	 * @param note eventuali note da inserire nel caricamento
	 * @param blobFile contenuto in formato Blob del file da caricare
	 * @return dati della risposta. Comprensivo del messaggio e dell'id del documento nel caso vada a buon fine l'operazione
	 */
	public InserimentoDocumentoResponse inserimentoDocumento(String oggetto, String tipoDocumento, String fileName, String note, Blob blobFile, List<BigInteger> codugnaList, TAnUtenzePdoc utenzaPdoc){
		if(tipoDocumento==null || tipoDocumento.length()==0)
			return null;
		InserimentoDocumentoRequest request = new InserimentoDocumentoRequest();
		Autenticazione aut = generaAutenticazioneFirma(utenzaPdoc);
		request.setAutenticazione(aut);
		Documento documento = new Documento();
		documento.setOggetto(oggetto);
		documento.setTipodocumento(tipoDocumento);
		documento.setFilename(fileName);
		documento.setNote(note);
		Security security = new Security();
		if(codugnaList!=null && codugnaList.size()>0)
			security.setUfficioabilitato(codugnaList);
		documento.setSecurity(security);
		documento.setVoluminoso(false);
		documento.setStreaming(false);
		request.setDocumento(documento);
		
		EfGiustiziaServiceStub.InserimentoDocumento requestStub = new EfGiustiziaServiceStub.InserimentoDocumento();
	   	DataHandler da = new DataHandler(marshaller(request),"base64");
	   	requestStub.setFirma(da);
	   	requestStub.setFilecontent(serializzaFile(blobFile));
	   	EfGiustiziaServiceStub.InserimentoDocumentoResponse response = null;
		try {
			response = stub.inserimentoDocumento(requestStub);
		} catch (RemoteException e) {
			System.out.println("Errore DocumentaleClient.inserimentoDocumento:" + e);
			return null;
		}
	   	DataHandler responseHandler = response.get_return();
	   	InserimentoDocumentoResponse result = (InserimentoDocumentoResponse) unmarhaller(responseHandler, InserimentoDocumentoResponse.class);
	   
	    //GC 10/07/2020: Modifica per miglioramento log pdoc
	    //System.out.println("IDDOCUMENTO:" +);
	    if (result.getMessaggio().isEsito())
	    {
	    	System.out.println("  3.1.1)" + " IDDOCUMENTO: " + result.getMessaggio().getMessagebody() + " creato con successo");
	    }
	    else
	    {
	    	System.out.println("  3.1.1)" + " ERRORE: Documento " + fileName + " non inserito nel sistema; Messaggio PDOC: " +  result.getMessaggio().getMessagebody() );
	    }
	    //GC 10/07/2020: Fine Modifica per miglioramento log pdoc
	    
		return result;
	}
	
	/**
	 * Rilettura dei documenti inseriti sulla piattaforma documentale
	 * @param idDocumento identificativo del documento da recuperare
	 * @param nomeFile nome del file da recuperare comprensivo di estensione
	 * @return true se il documento viene recuperato in modo corretto, false altrimenti
	 */
	public byte[] recuperoDocumento(BigInteger idDocumento, TAnUtenzePdoc utenzaPdoc){
		RecuperoDocumentoRequest request = new RecuperoDocumentoRequest();
		Autenticazione aut = generaAutenticazioneFirma(utenzaPdoc);
		request.setAutenticazione(aut);
		request.setIddocumento(idDocumento);
		request.setRecuperacontent(true);
		request.setRecuperadascs(false);
//		request.setVersione(new BigInteger("1"));
		request.setVersione(null);
		
		EfGiustiziaServiceStub.RecuperaDocumento requestStub = new EfGiustiziaServiceStub.RecuperaDocumento();
	   	DataHandler da = new DataHandler(marshaller(request),"base64");
	   	requestStub.setFirma(da);
	   	EfGiustiziaServiceStub.RecuperaDocumentoResponseE response = null;
		try {
			response = stub.recuperaDocumento(requestStub);
		} catch (RemoteException e) {
			System.out.println("Errore DocumentaleClient.recuperoDocumento:" + e);
			return null;
		}
		DataHandler responseHandler = response.get_return().getRisposta();
		RecuperoDocumentoResponse result = (RecuperoDocumentoResponse) unmarhaller(responseHandler, RecuperoDocumentoResponse.class);
		System.out.println("Risposta recuperoDocumento:" + result.getMessaggio().getMessagebody());
		return deserializzaFile(response.get_return().getDoccontent());
	}
	
	/**
	 * Associazione di un documento con un fascicolo presenti entrambi sulla Piattaforma Documentale
	 * @param idDocumento identificativo del documento
	 * @param idFascicolo codice del fascicolo
	 * @return true se l'associazione viene effettuata in modo corretto, false altrimenti
	 */
	public boolean associaDocumentoFascicolo(BigInteger idDocumento, String idFascicolo, TAnUtenzePdoc utenzaPdoc){
		AggiuntaFascicoloRequest request = new AggiuntaFascicoloRequest();
		Autenticazione aut = generaAutenticazioneFirma(utenzaPdoc);
		request.setAutenticazione(aut);
		request.setIddocumento(idDocumento);
		request.setCodicefascicolo(idFascicolo);
		request.setSecurity(new Security());
		
		EfGiustiziaServiceStub.AggiuntaFascicolo requestStub = new EfGiustiziaServiceStub.AggiuntaFascicolo();
		DataHandler da = new DataHandler(marshaller(request),"base64");
	   	requestStub.setFirma(da);
	   	EfGiustiziaServiceStub.AggiuntaFascicoloResponse response = null;
	   	try{
	   		response = stub.aggiuntaFascicolo(requestStub);
	   	} catch (RemoteException e) {
			System.out.println("Errore DocumentaleClient.associaDocumentoFascicolo:" + e);
			return false;
	   	}
	   	DataHandler responseHandler = response.get_return();
	   	AggiuntaFascicoloResponse result = (AggiuntaFascicoloResponse) unmarhaller(responseHandler, AggiuntaFascicoloResponse.class);
	    //GC 10/07/2020: Modifica per miglioramento log pdoc
	    if (result.getMessaggio().isEsito())
	    {
	    	System.out.println("  4.1.1)" + " Associazione IDDOC: " + idDocumento.toString() + "/Fascicolo: " + idFascicolo + " creata con successo");
	    }
	    else
	    {
	    	System.out.println("  4.1.1)" + " ERRORE Associazione IDDOC: " + idDocumento.toString() + "/Fascicolo: " + idFascicolo + ";  Messaggio PDOC: " +  result.getMessaggio().getMessagebody() );
	    }
	    //GC 10/07/2020: Fine Modifica per miglioramento log pdoc
	   	return result.getMessaggio().isEsito();
	}
	
	/**
	 * Cancellazione logica di un documento dalla Piattaforma Documentale
	 * @param idDocumento identificativo del documento da cancellare
	 * @return true se l'operazione di cancellazione viene effettuata correttamente, false altrimenti
	 */
	public boolean eliminaDocumento(BigInteger idDocumento, TAnUtenzePdoc utenzaPdoc){
		EliminaDocumentoRequest request = new EliminaDocumentoRequest();
		Autenticazione aut = generaAutenticazioneFirma(utenzaPdoc);
		request.setAutenticazione(aut);
		request.setIddocumento(idDocumento);
		
		EfGiustiziaServiceStub.EliminaDocumento requestStub = new EfGiustiziaServiceStub.EliminaDocumento();
		DataHandler da = new DataHandler(marshaller(request),"base64");
	   	requestStub.setFirma(da);
	   	EfGiustiziaServiceStub.EliminaDocumentoResponse response = null;
	   	try{
	   		response = stub.eliminaDocumento(requestStub);
	   	} catch (RemoteException e) {
			System.out.println("Errore DocumentaleClient.eliminaDocumento:" + e);
			return false;
	   	}
	   	DataHandler responseHandler = response.get_return();
	   	EliminaDocumentoResponse result = (EliminaDocumentoResponse) unmarhaller(responseHandler, EliminaDocumentoResponse.class);
	   	return result.getMessaggio().isEsito();
	}
	
	public boolean stralcio(String codiceFascicoloOrigine, String codiceFascicoloDestinazione, ArrayList<it.giustizia.smp.webservice.client.documentale.model.general.stralcio.Documento> documenti, TAnUtenzePdoc utenzaPdoc){
		if(codiceFascicoloOrigine == null || codiceFascicoloDestinazione == null)
			return false;
		StralcioRequest request = new StralcioRequest();
		Autenticazione aut = generaAutenticazioneFirma(utenzaPdoc);
		request.setAutenticazione(aut);
		request.setEsistente(true);
		it.giustizia.smp.webservice.client.documentale.model.general.stralcio.Fascicolo fascicolo = new it.giustizia.smp.webservice.client.documentale.model.general.stralcio.Fascicolo();
		fascicolo.setCodfascicoloorigine(codiceFascicoloOrigine);
		fascicolo.setCodfascicolostralcio(codiceFascicoloDestinazione);
		fascicolo.setCodfascicolostralciopadre(new String());
		fascicolo.setDocumento(documenti);
		request.setFascicolo(fascicolo);
		
		EfGiustiziaServiceStub.StralcioFascicolo requestStub = new EfGiustiziaServiceStub.StralcioFascicolo();
		DataHandler da = new DataHandler(marshaller(request),"base64");
	   	requestStub.setFirma(da);
	   	
		EfGiustiziaServiceStub.StralcioFascicoloResponse response = null;
		try{
	   		response = stub.stralcioFascicolo(requestStub);
	   	} catch (RemoteException e) {
			System.out.println("Errore DocumentaleClient.stralcio:" + e);
			return false;
	   	}
	   	DataHandler responseHandler = response.get_return();
	   	StralcioFascicoloResponse result = (StralcioFascicoloResponse) unmarhaller(responseHandler, StralcioFascicoloResponse.class);
	   	return result.getMessaggio().isEsito();
		
	}
	
	public boolean riunione(String codiceFascicoloOrigine, String codiceFascicoloRiunione, ArrayList<BigInteger> documenti, TAnUtenzePdoc utenzaPdoc){
		if(codiceFascicoloOrigine == null || codiceFascicoloRiunione == null)
			return false;
		RiunioneRequest request = new RiunioneRequest();
		Autenticazione aut = generaAutenticazioneFirma(utenzaPdoc);
		request.setAutenticazione(aut);
		it.giustizia.smp.webservice.client.documentale.model.general.riunione.Fascicolo fascicolo = new it.giustizia.smp.webservice.client.documentale.model.general.riunione.Fascicolo();
		fascicolo.setCodfascicoloorigine(codiceFascicoloOrigine);
		fascicolo.setCodfascicoloriunione(codiceFascicoloRiunione);
		fascicolo.setCodfascicoloriunionepadre(new String());
		fascicolo.setIddocumento(documenti);
		request.setFascicolo(fascicolo);
		
		EfGiustiziaServiceStub.RiunioneFascicoli requestStub = new EfGiustiziaServiceStub.RiunioneFascicoli();
		DataHandler da = new DataHandler(marshaller(request),"base64");
	   	requestStub.setFirma(da);
	   	
		EfGiustiziaServiceStub.RiunioneFascicoliResponse response = null;
		try{
	   		response = stub.riunioneFascicoli(requestStub);
	   	} catch (RemoteException e) {
			System.out.println("Errore DocumentaleClient.riunione:" + e);
			return false;
	   	}
	   	DataHandler responseHandler = response.get_return();
	   	RiunioneFascicoliResponse result = (RiunioneFascicoliResponse) unmarhaller(responseHandler, RiunioneFascicoliResponse.class);
	   	return result.getMessaggio().isEsito();
		
	}
	
	public Risposta firmaRemota(BigInteger idDocumento, String username, String password, String pin, TAnUtenzePdoc utenzaPdoc){
	   	FirmaRemotaRequest request = new FirmaRemotaRequest();
    	Autenticazione autenticazione = generaAutenticazioneFirma(utenzaPdoc);
    	request.setAutenticazione(autenticazione);
    	request.setIddocumento(idDocumento);
    	request.setUsername(username);
    	request.setPassword(password);
    	request.setPin(pin);
    	
    	EfGiustiziaServiceStub.FirmaRemotaHSM requestStub = new EfGiustiziaServiceStub.FirmaRemotaHSM();
	   	DataHandler da = new DataHandler(marshaller(request),"base64");
	   	requestStub.setFirma(da);
	   	EfGiustiziaServiceStub.FirmaRemotaHSMResponse response = null;
		try {
			response = stub.firmaRemotaHSM(requestStub);
		} catch (RemoteException e) {
			System.out.println("Errore DocumentaleClient.:" + e);
			return null;
		}
	   	DataHandler responseHandler = response.get_return();
	    Risposta result = (Risposta) unmarhaller(responseHandler, Risposta.class);
	    System.out.println("firmaRemota:" + result.getMessaggio().getMessagebody());
	    return result;
    }
	
	public BigInteger renderPdf(BigInteger idDocumento, TAnUtenzePdoc utenzaPdoc) throws ConnectException {
		BigInteger idTransazione = null;
		if(idDocumento == null)
			return null;
		RenderRequest request = new RenderRequest();
		Render render = new Render();
		render.setIddocumento(idDocumento);
		render.setFormato("PDF");
		//render.setEtichetta("etichetta");  modifica Documentale 13 aprile 2018
		render.setEtichetta("");           //modifica Documentale 13 aprile 2018
		request.setRender(render);
		Autenticazione aut = generaAutenticazioneFirma(utenzaPdoc);
		request.setAutenticazione(aut);
		
		EfGiustiziaServiceStub.RenderDocumento requestStub = new EfGiustiziaServiceStub.RenderDocumento();
		DataHandler da = new DataHandler(marshaller(request),"base64");
	   	requestStub.setFirma(da);
	   	
		EfGiustiziaServiceStub.RenderDocumentoResponse response = null;
		try{
	   		response = stub.renderDocumento(requestStub);
	   	} catch (RemoteException e) {
			System.out.println("Errore DocumentaleClient.render:" + e);
			return null;
	   	}
	   	DataHandler responseHandler = response.get_return();
	   	RenderResponse result = (RenderResponse) unmarhaller(responseHandler, RenderResponse.class);
	   	if(result!=null && result.getMessaggio().isEsito() && result.getIdtransazione()!=null)
	   		idTransazione = result.getIdtransazione();
	   	else
	   		return null;
	   	return idTransazione;
	}
	
	public boolean monitorRenderPdf(BigInteger idTransazione, TAnUtenzePdoc utenzaPdoc, TAtto atto)throws ConnectException {
		if(idTransazione == null)
			return false;
		
		MonitorJobRequest request = new MonitorJobRequest();
		request.setIdtransazione(idTransazione);
		Autenticazione aut = generaAutenticazioneFirma(utenzaPdoc);
		request.setAutenticazione(aut);
		
		
		
		EfGiustiziaServiceStub.MonitorJob requestStub = new EfGiustiziaServiceStub.MonitorJob();
		DataHandler da = new DataHandler(marshaller(request),"base64");
	   	requestStub.setFirma(da);
	   	
		EfGiustiziaServiceStub.MonitorJobResponse response = null;
		try{
	   		response = stub.monitorJob(requestStub);
	   	} catch (RemoteException e) {
			System.out.println("Errore DocumentaleClient.monitorRenderPdf:" + e);
			return false;
	   	}
	   	DataHandler responseHandler = response.get_return();
	   	MonitorJobResponse result = (MonitorJobResponse) unmarhaller(responseHandler, MonitorJobResponse.class);
	   	if(result!= null && result.getTransazione()!=null && result.getTransazione().getStato()!=null)
	   	{
	   		if(result.getTransazione().getStato().equalsIgnoreCase("completed"))
	   		{
	   			return true;
	   		}
	   		else{
	   			if (result.getTransazione().getStato().equalsIgnoreCase("pending")){
	   				System.out.println("--->Pending !! Valore di ritorno Verifica Conversione:" + result.getTransazione().getStato() + " Id Transazione  =  " + idTransazione);
	   			}else{
	   				
	   				System.out.println("--->Anomalia !! Valore di ritorno Verifica Conversione:" + result.getTransazione().getStato() + " Id Transazione  =  " + idTransazione);
	   				Long idAtt = (atto.getId().getCodiKIdAtto());		
	   				Long iddocPdoc = atto.getNumeDocumentoPdoc();
	   			    // Convert Long to String.
	   				String stringVar =iddocPdoc.toString();

	   				// Convert to BigInteger. The BigInteger(byte[] val) expects a binary representation of 
	   				// the number, whereas the BigInteger(string val) expects a decimal representation.
	   				BigInteger bigIntVar = new BigInteger( stringVar );
	
	   				Long idUff =  (long) atto.getTAnUfficio().getCodiKIdUfficio();
	   				//AttoDao attoDao = new AttoDao();
	   				attoService.cancelIdTransazioneDocumentale(idAtt, (int)atto.getTAnUfficio().getCodiKIdUfficio());
	   				
	   				//documentaleService.renderPdf(atto.getDescTipoDocumento() , atto.getDescNomeDocumento(), idAtt, bigIntVar, idUff, utenzaPdoc);
	   				
	   				System.out.println("--->Fine Cancellazione NUME_CONV_PDOC  !! " );
	   			}
	   			
	   			return false;
	   		}
	   	}
	   	else
	   		return false;
	}
	
	public boolean estendiSicurezza(BigInteger idDocumento, BigInteger codicePdoc, TAnUtenzePdoc utenzaPdoc)throws ConnectException {
		if(codicePdoc == null)
			return false;
		UpdateDocumentoRequest request = new UpdateDocumentoRequest();
		request.setIddocumento(idDocumento);
		Security security = new Security();
		List<BigInteger> autorizzazione = new ArrayList<BigInteger>();
		autorizzazione.add(codicePdoc);
		security.setUfficioabilitato(autorizzazione);
		request.setSecurity(security);
		//F.C. Ticket#202202240113 SIT-MP - errore download pdf e registrazione scambio INIZIO
		request.setAppend(true);
		//F.C. Ticket#202202240113 SIT-MP - errore download pdf e registrazione scambio END
		
		
		Autenticazione aut = generaAutenticazioneFirma(utenzaPdoc);
		request.setAutenticazione(aut);
		
		EfGiustiziaServiceStub.UpdateDocumento requestStub = new EfGiustiziaServiceStub.UpdateDocumento();
		DataHandler da = new DataHandler(marshaller(request),"base64");
	   	requestStub.setFirma(da);
	   	
		EfGiustiziaServiceStub.UpdateDocumentoResponse response = null;
		try{
	   		response = stub.updateDocumento(requestStub);
	   	} catch (RemoteException e) {
			System.out.println("Errore DocumentaleClient.estendiSicurezza:" + e);
			return false;
	   	}
	   	DataHandler responseHandler = response.get_return();
	   	UpdateDocumentoResponse result = (UpdateDocumentoResponse) unmarhaller(responseHandler, UpdateDocumentoResponse.class);
	   	return result.getMessaggio().isEsito();
	}

	/**
	 * Effettua l'inserimento sulla piattaforma documentale di un nuovo documento
	 * @param idDocuemto = codice del PDOC documento da recuperare
	 * @return identificativo sulla Piattaforma documentale del documento recuperato
	 */
	public BigInteger copiaDocumento(BigInteger idDocumento, String oggetto, String tipoDocumento, String fileName, String note, List<BigInteger> codugnaList, TAnUtenzePdoc utenzaPdoc)throws ConnectException {
		byte[] doc = recuperoDocumento(idDocumento, utenzaPdoc);
		InserimentoDocumentoRequest request = new InserimentoDocumentoRequest();
		Autenticazione aut = generaAutenticazioneFirma(utenzaPdoc);
		request.setAutenticazione(aut);
		Documento documento = new Documento();
		documento.setOggetto(oggetto);
		documento.setTipodocumento(tipoDocumento);
		documento.setFilename(fileName);
		documento.setNote(note);
		Security security = new Security();
		if(codugnaList!=null && codugnaList.size()>0)
			security.setUfficioabilitato(codugnaList);
		documento.setSecurity(security);
		documento.setVoluminoso(false);
		documento.setStreaming(false);
		request.setDocumento(documento);
		
		EfGiustiziaServiceStub.InserimentoDocumento requestStub = new EfGiustiziaServiceStub.InserimentoDocumento();
	   	DataHandler da = new DataHandler(marshaller(request),"base64");
	   	requestStub.setFirma(da);
		ByteArrayDataSource rawData= new ByteArrayDataSource(doc);
	   	DataHandler dhFile= new DataHandler(rawData);
	   	requestStub.setFilecontent(dhFile);

	   	EfGiustiziaServiceStub.InserimentoDocumentoResponse response = null;
		try {
			response = stub.inserimentoDocumento(requestStub);
			
			
		} catch (RemoteException e) {
			System.out.println("Errore DocumentaleClient.copiaDocumento:" + e);
			return null;
		}
	   	DataHandler responseHandler = response.get_return();
	   	InserimentoDocumentoResponse result = (InserimentoDocumentoResponse) unmarhaller(responseHandler, InserimentoDocumentoResponse.class);
	   	try {
	   		//**** DA ELIMINARE DOPO IL TEST (UTILIZZATO PERCHE' IL DOCUMENTALE VA IN ERRORE)
	   		// if (result.getIddocumento() ==null) result.setIddocumento(idDocumento);
	   		//**** FINE
	   		//result.setIddocumento(idDocumento);
	   		
	   		
	   		return  result.getIddocumento();
	   	}catch (Exception e) {
	   		throw new ConnectException("Errore:" + result.getMessaggio().getMessagebody(),e);
	   	}
	    
	}

	
	/**
	 * Genera l'oggetto Autenticazione con i valori corretti,comune a tutte le firme 
	 * @return
	 */
	private Autenticazione generaAutenticazioneFirma(TAnUtenzePdoc utenzaPdoc) {
	    	Autenticazione autenticazione = new Autenticazione();
	    	// modifica per gestire casio in cui non è assente sul db l'utenza pdoc!
	    	if (utenzaPdoc.getNumeUtenzaPdoc() != null ){
	    		autenticazione.setCodiceapplicazione(utenzaPdoc.getDescCodiceApplicazione());
		    	autenticazione.setPassword(utenzaPdoc.getDescPassword());
		    	autenticazione.setIdutente(BigInteger.valueOf(Long.valueOf(utenzaPdoc.getDescUtentePdoc())));
		    	autenticazione.setIdufficio(BigInteger.valueOf(Long.valueOf(utenzaPdoc.getNumeUtenzaPdoc().toString())));
//		    	autenticazione.setIdufficio(BigInteger.valueOf(utenzaPdoc.getNumeUtenzaPdoc().intValue()));
		    	/*autenticazione.setCodiceapplicazione("SIT_MP");
		    	autenticazione.setPassword("SIT_MP_TEST");
		    	autenticazione.setIdutente(new BigInteger("103"));
		    	autenticazione.setIdufficio(new BigInteger("103000001"));*/
	    	}else{
	    		System.out.println("---->ERRORE UTENZA PDOC <----  Verificare la corretta associazione in tabella !!!!");
	    	}
	    	
			return autenticazione;
//		return Configuration.getAutenticazione();
	}
	

	/**
	 * Crea l'oggetto di invocazione per ogni operation
	 * @param request contiene le informazioni da inviare nella richiesta della operation
	 * @return array di byte da utilizzare,dopo la conversione in Base64, nella chiamata al web service
	 * @throws Base64DecodingException 
	 */
	 private byte[] marshaller(Object request){
	   	byte[] firmaBytes = new byte[1000];
	   	ByteArrayInputStream bis = null;
	   	ByteArrayOutputStream bos = new ByteArrayOutputStream();
	   	try {
			JAXBContext contextObj = JAXBContext.newInstance(request.getClass());
			Marshaller marshallerObj = contextObj.createMarshaller();
		    marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		    marshallerObj.marshal(request, bos);
			firmaBytes = bos.toByteArray();
			bis = new ByteArrayInputStream(bos.toByteArray());
				} catch (JAXBException e) {
				e.printStackTrace();
			} 
	   	return firmaBytes;
	   }
	 
    /**
     * Restituisce l'oggetto generale di risposta per ogni operation
     * @param dh elemento DataHandler da analizzare contenente i dati di risposta
     * @param responseClass specifica la classe in cui deve essere formalizzata la risposta
     * @return oggetto che contiene tutte le informazioni da estrapolare in modo specializzato per ogni operation
     */
    private Object unmarhaller(DataHandler dh, Class<?> responseClass){
    	Object risposta = null;
    	try {
    		ByteArrayOutputStream bout = new ByteArrayOutputStream();
    		dh.writeTo(bout);
    		JAXBContext jc = JAXBContext.newInstance (responseClass);
    		ByteArrayInputStream bis = new ByteArrayInputStream(bout.toByteArray());

            Unmarshaller u = jc.createUnmarshaller ();
            Class<?> appoClass = Class.forName(responseClass.getName());
            risposta = appoClass.cast(u.unmarshal(bis));
       } catch (JAXBException e) {
			System.out.println("Errore in unmarhaller:" + e);
		} catch (ClassNotFoundException e) {
			System.out.println("Errore in unmarhaller:" + e);
		}
		 catch (IOException e) {
				System.out.println("Errore unmarhaller:" + e);
			 }
		return risposta;
    }
    
    
    /**
     * Metodo per inserire in un oggetto DataHandler il file da inviare al documentale
     * @param filePath percorso completo del file da serializzare
     * @return oggetto di tipo DataHandler caricato con il file in esame
     */
    /*private DataHandler serializzaFile(String filePath){
    	DataHandler dh = null;
    	File file = new File(filePath);
    	FileInputStream fin;
		try {
			fin = new FileInputStream(file);
			byte fileContent[] = new byte[(int)file.length()];
			fin.read(fileContent);
			ByteArrayDataSource rawData= new ByteArrayDataSource(fileContent);
			dh= new DataHandler(rawData);
		}
 	     catch (FileNotFoundException e) {
 	    	e.printStackTrace();
 			return dh;
 		}
 	    catch (IOException e) {
 	    	e.printStackTrace();
 			return dh;
 		}
 	      return dh;
    }*/
    private DataHandler serializzaFile(Blob blobFile){
    	DataHandler dh = null;
    	try {
    	InputStream is = blobFile.getBinaryStream();   
    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
    	int next = is.read();
    	while (next > -1) {
    	    bos.write(next);
    	    next = is.read();
    	}
    	bos.flush();
    	byte[] fileContent = bos.toByteArray();
    	ByteArrayDataSource rawData= new ByteArrayDataSource(fileContent);
		dh= new DataHandler(rawData);
		}
 	     catch (FileNotFoundException e) {
 	    	e.printStackTrace();
 			return dh;
 		}
 	    catch (IOException e) {
 	    	e.printStackTrace();
 			return dh;
 		}
 	   catch (SQLException e) {
	    	e.printStackTrace();
			return dh;
		}
 	      return dh;
    }
    
    /**
     * Metodo per estrarre il file dalla risposta alla operation di recupero file dal documentale.
     * Il file viene salvato in c: con il nome specificato dal parametro nomeFile
     * @param dh contiene in un DataHandler il file da estrarre
     * @return array di byte contenente il file estratto. null in caso di errore  
     */
    private byte[] deserializzaFile(DataHandler dh){
    	try{
    		if(dh==null) return null;
    		ByteArrayOutputStream output = new ByteArrayOutputStream();
	        dh.writeTo(output);
	        byte[] byteArray = output.toByteArray();
	        return byteArray;
    	}
 	   catch (IOException e) {
	    	e.printStackTrace();
	    	return null;
		}
    }
//    private boolean deserializzaFile(DataHandler dh, String nomeFile){
//    	if(dh == null)
//    		return false;
//    	boolean result = false;
//    	try {
//    		ByteArrayOutputStream b = new ByteArrayOutputStream(); 
//			dh.writeTo(b);
//    		JAXBContext jc = JAXBContext.newInstance (FileContent.class);
//    		ByteArrayInputStream bis = new ByteArrayInputStream(b.toByteArray());
//    	
//            Unmarshaller u = jc.createUnmarshaller ();
//            FileContent risposta = (FileContent)u.unmarshal(bis);
//            
//            byte[] byteArray = risposta.getFilecontent();
//            File fileOut = new File("c:\\" + nomeFile);
//            OutputStream stream = new FileOutputStream(fileOut);
//	        stream.write(byteArray);
//	        stream.close();
//	        result = true;
//
//		} catch (JAXBException e) {
//			System.out.println("Errore deserializzaFile:" + e);
//		} catch (FileNotFoundException e) {
//			System.out.println("Errore deserializzaFile:" + e);
//		} catch (IOException e) {
//			System.out.println("Errore deserializzaFile:" + e);
//		}
//    	finally{
//    		return result;
//    	}
//    }
    
   
   /**
    * Setta le properties appropriate nel caso in cui venga utilizzato un proxy
    */
//   private static void setCredenzialiProxy(){
//	   System.setProperty("http.proxyHost", "proxycb.almavivaitalia.it");
//       System.setProperty("http.proxyPort", "8080");
//       Authenticator.setDefault(new Authenticator() {
//           protected PasswordAuthentication getPasswordAuthentication() {
//               return new
//                           PasswordAuthentication(Configuration.getProxyUsername(),Configuration.getProxyPassword().toCharArray());
//   }});
//   }

   
}
