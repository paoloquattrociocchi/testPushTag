package it.giustizia.smp.scheduler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;

import it.giustizia.smp.model.TAnbscMsg;
import it.giustizia.smp.multidb.UserRoutingDataSource;
import it.giustizia.smp.service.AnbscService;
import it.giustizia.smp.service.msg.AnbscMsgService;
import it.giustizia.smp.utility.AnbscEnum;
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
import it.giustizia.smp.webservice.utility.JAXBUtil;


public class AnbscJob{
	
	private Logger logger = Logger.getLogger(getClass());
	private static char ERRORE_VALIDAZIONE_XML = 'E';
	
	@Autowired
	private UserRoutingDataSource	myDataSource;
	@Autowired
	private AnbscService anbscService;
	@Autowired
	private AnbscMsgService anbscMsgService;
	private static final String mancataRisposta = "Mancata risposta del  webservice : Response = Null. Per maggiori dettagli verificare il log applicativo.";
	
	    
//	 @Scheduled(cron="0 0/5 8-17 * * ?")//scheduler ogni giorno dalle 8 alle 17 ogni 2 minuti
//	 @Scheduled(cron="0 0/45 3-4 * * ?")//scheduler ogni giorno dalle 3 alle 4 ogni 45 minuti
    public void scheduledMethod()
    {
	 	System.out.println("Inizio Anbsc Job...");
	 	
	 	elaboraJob("SMP_GIU");

	 	myDataSource.setSchemaDocumentale("");
	 	System.out.println("Fine Anbsc Job");
    }
	 
	 private void elaboraJob(String schema){
		 myDataSource.selectConnection(schema);
		 anbscMsgService.invioSentenze();
	 	 invioDati();
	 }
	 
	private void invioDati(){
		byte[] out = null;
		List<TAnbscMsg> elementList = anbscService.getDaInviare();
		if(elementList != null) {
			for(TAnbscMsg anbsc : elementList) {
				try {
					AnbscEnum anbscEnum = AnbscEnum.getById(anbsc.gettTpAnbscMsg().getCodiKIdTipoAnbscMsg());
					switch(anbscEnum) {
					case ANBSC_SEQUESTRO:
					case ANBSC_SEQUESTRO_ANTICIPATO:
					case ANBSC_SEQUESTRO_URGENTE:
						SequestroRequest sequestroRequest = JAXBUtil.unmarshall(SequestroRequest.class, anbsc.getXmlMessaggio().getBinaryStream());						
						// Controllo se XML che sto inviando e' corretto 						
						out = JAXBUtil.validationAndMarshal(sequestroRequest);
												
						SequestroResponse sequestroResponse = anbscService.inviaSequestro(sequestroRequest);
						if (sequestroResponse != null) 
							anbscService.updateRispostaMsgDatabase(anbsc.getCodiKIdMsg(), sequestroResponse.getEsito(), sequestroResponse);
						else
							anbscService.updateRispostaMsgDatabase(anbsc.getCodiKIdMsg(), ERRORE_VALIDAZIONE_XML, mancataRisposta);							
						break;
					case ANBSC_CONFISCA_DEFINITIVA_CASSAZIONE:
					case ANBSC_CONFISCA_DEFINITIVA_I_GRADO:
					case ANBSC_CONFISCA_DEFINITIVA_II_GRADO:
						ConfiscaDefinitivaRequest definitivaRequest = JAXBUtil.unmarshall(ConfiscaDefinitivaRequest.class, anbsc.getXmlMessaggio().getBinaryStream());
						// Controllo se XML che sto inviando e' corretto 						
						out = JAXBUtil.validationAndMarshal(definitivaRequest);
						
						ConfiscaDefinitivaResponse definitivaResponse = anbscService.inviaConfiscaDefinitiva(definitivaRequest);
						if (definitivaResponse != null)
							anbscService.updateRispostaMsgDatabase(anbsc.getCodiKIdMsg(), definitivaResponse.getEsito(), definitivaResponse);
						else
							anbscService.updateRispostaMsgDatabase(anbsc.getCodiKIdMsg(), ERRORE_VALIDAZIONE_XML, mancataRisposta);						
						break;
					case ANBSC_REVOCA_SEQUESTRO:
					case ANBSC_REVOCA_SEQUESTRO_I_GRADO:
					case ANBSC_REVOCA_SEQUESTRO_II_GRADO:
						RevocaSequestroRequest revocaSequestroRequest = JAXBUtil.unmarshall(RevocaSequestroRequest.class, anbsc.getXmlMessaggio().getBinaryStream());
						// Controllo se XML che sto inviando e' corretto 						
						out = JAXBUtil.validationAndMarshal(revocaSequestroRequest);						
						
						RevocaSequestroResponse revocaSequestroResponse = anbscService.inviaRevocaSequestro(revocaSequestroRequest);
						if (revocaSequestroResponse != null)
							anbscService.updateRispostaMsgDatabase(anbsc.getCodiKIdMsg(), revocaSequestroResponse.getEsito(), revocaSequestroResponse);
						else
							anbscService.updateRispostaMsgDatabase(anbsc.getCodiKIdMsg(), ERRORE_VALIDAZIONE_XML, mancataRisposta);						
						break;
					case ANBSC_CONFISCA_I_GRADO:
						ConfiscaPrimoGradoRequest confiscaIGradoRequest = JAXBUtil.unmarshall(ConfiscaPrimoGradoRequest.class, anbsc.getXmlMessaggio().getBinaryStream());
						// Controllo se XML che sto inviando e' corretto 						
						out = JAXBUtil.validationAndMarshal(confiscaIGradoRequest);
						
						ConfiscaPrimoGradoResponse confiscaIGradoResponse = anbscService.inviaConfiscaPrimoGrado(confiscaIGradoRequest);
						if (confiscaIGradoResponse != null)
							anbscService.updateRispostaMsgDatabase(anbsc.getCodiKIdMsg(), confiscaIGradoResponse.getEsito(), confiscaIGradoResponse);
						else
							anbscService.updateRispostaMsgDatabase(anbsc.getCodiKIdMsg(), ERRORE_VALIDAZIONE_XML, mancataRisposta);						
						break;
					case ANBSC_CONFISCA_II_GRADO:
						ConfiscaSecondoGradoRequest confiscaIIGradoRequest = JAXBUtil.unmarshall(ConfiscaSecondoGradoRequest.class, anbsc.getXmlMessaggio().getBinaryStream());
						// Controllo se XML che sto inviando e' corretto 						
						out = JAXBUtil.validationAndMarshal(confiscaIIGradoRequest);
						
						ConfiscaSecondoGradoResponse confiscaIIGradoResponse = anbscService.inviaConfiscaSecondoGrado(confiscaIIGradoRequest);
						if (confiscaIIGradoResponse != null)
							anbscService.updateRispostaMsgDatabase(anbsc.getCodiKIdMsg(), confiscaIIGradoResponse.getEsito(), confiscaIIGradoResponse);
						else
							anbscService.updateRispostaMsgDatabase(anbsc.getCodiKIdMsg(), ERRORE_VALIDAZIONE_XML, mancataRisposta);						
						break;
					case ANBSC_REVOCA_CONFISCA:
						RevocaConfiscaRequest revocaConfiscaRequest = JAXBUtil.unmarshall(RevocaConfiscaRequest.class, anbsc.getXmlMessaggio().getBinaryStream());
						// Controllo se XML che sto inviando e' corretto 						
						out = JAXBUtil.validationAndMarshal(revocaConfiscaRequest);
						
						RevocaConfiscaResponse revocaConfiscaResponse = anbscService.inviaRevocaConfisca(revocaConfiscaRequest);
						if (revocaConfiscaResponse != null)
							anbscService.updateRispostaMsgDatabase(anbsc.getCodiKIdMsg(), revocaConfiscaResponse.getEsito(), revocaConfiscaResponse);
						else
							anbscService.updateRispostaMsgDatabase(anbsc.getCodiKIdMsg(), ERRORE_VALIDAZIONE_XML, mancataRisposta);						
						break;
					case ANBSC_RIUNIONE:
						RiunioneRequest riunioneRequest = JAXBUtil.unmarshall(RiunioneRequest.class, anbsc.getXmlMessaggio().getBinaryStream());
						// Controllo se XML che sto inviando e' corretto 						
						out = JAXBUtil.validationAndMarshal(riunioneRequest);
						
						RiunioneResponse riunioneResponse = anbscService.inviaRiunione(riunioneRequest);
						if (riunioneResponse != null)
							anbscService.updateRispostaMsgDatabase(anbsc.getCodiKIdMsg(), riunioneResponse.getEsito(), riunioneResponse);
						else
							anbscService.updateRispostaMsgDatabase(anbsc.getCodiKIdMsg(), ERRORE_VALIDAZIONE_XML, mancataRisposta);						
						break;
					case ANBSC_STRALCIO:
						StralcioRequest stralcioRequest = JAXBUtil.unmarshall(StralcioRequest.class, anbsc.getXmlMessaggio().getBinaryStream());
						// Controllo se XML che sto inviando e' corretto 						
						out = JAXBUtil.validationAndMarshal(stralcioRequest);
						
						StralcioResponse stralcioResponse = anbscService.inviaStralcio(stralcioRequest);
						if (stralcioResponse != null)
							anbscService.updateRispostaMsgDatabase(anbsc.getCodiKIdMsg(), stralcioResponse.getEsito(), stralcioResponse);
						else
							anbscService.updateRispostaMsgDatabase(anbsc.getCodiKIdMsg(), ERRORE_VALIDAZIONE_XML, mancataRisposta);						
						break;
					default:
						break;
					}
				} catch(JAXBException ex) {
					anbscService.updateRispostaMsgDatabase(anbsc.getCodiKIdMsg(), ERRORE_VALIDAZIONE_XML, ex.getCause().getLocalizedMessage().length() > 4000 ? ex.getCause().getLocalizedMessage().substring(0, 4000) : ex.getCause().getLocalizedMessage());
					logger.error("Errore in AnbscJob.invioDati: XML non validato correttamente : " + anbsc.getCodiKIdMsg() + " - " + ex.getCause().getLocalizedMessage());
				} catch(IOException ex) {
					logger.error("Errore in AnbscJob.invioDati: " + ex);
				} catch(SQLException ex) {
					logger.error("Errore in AnbscJob.invioDati: " + ex);
				} catch (SAXException ex) {
					anbscService.updateRispostaMsgDatabase(anbsc.getCodiKIdMsg(), ERRORE_VALIDAZIONE_XML, ex.getCause().getLocalizedMessage().length() > 4000 ? ex.getCause().getLocalizedMessage().substring(0, 4000) : ex.getCause().getLocalizedMessage());
					logger.error("Errore in AnbscJob.invioDati: XML non validato correttamente : " + anbsc.getCodiKIdMsg() + " - " + ex.getCause().getLocalizedMessage());
				}											
			}
			
		}
		
	}
	
}
