package it.giustizia.smp.webservice.client.bdc.ricerca.bene;

import it.giustizia.smp.webservice.utility.IncomingHandler;
import it.giustizia.smp.webservice.utility.OutcomingHandler;

import java.util.ResourceBundle;

import org.apache.axis.Handler;
import org.apache.axis.SimpleChain;
import org.apache.axis.SimpleTargetedChain;
import org.apache.axis.client.AxisClient;
import org.apache.axis.configuration.SimpleProvider;
import org.apache.axis.transport.http.HTTPSender;
import org.apache.axis.transport.http.HTTPTransport;
import org.apache.log4j.Logger;

public class RicercaBDCClient {   
    
    protected Logger logger = Logger.getLogger(RicercaBDCClient.class);    
    protected RicercaServiceLocator locator = new RicercaServiceLocator();       
    protected Ricerca service = null; 
    
    private static final String         BUNDLE_NAME     = "srcConfig";
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
   
    public RicercaBDCClient() {
        try {   
            // ottieni l'URI del web service specificato nel file .properties di configurazione
            String wsAddress = RESOURCE_BUNDLE.getString("RICERCA_BDC_WS_ADDRESS");           
            // registra gli handlers per monitorare i messaggi SOAP in ingresso/uscita
            SimpleProvider clientConfig = new SimpleProvider();   
            // handler per i messaggi in entrata
            IncomingHandler incomingHandler = new IncomingHandler();
            // handler per i messaggi in uscita
            OutcomingHandler outcomingHandler = new OutcomingHandler();
            SimpleChain reqHandler = new SimpleChain();
            SimpleChain respHandler = new SimpleChain();
            reqHandler.addHandler(incomingHandler);
            respHandler.addHandler(outcomingHandler);
            Handler pivot = new HTTPSender();
            Handler transport = new SimpleTargetedChain(reqHandler, pivot, respHandler);
            clientConfig.deployTransport(HTTPTransport.DEFAULT_TRANSPORT_NAME, transport);
            locator.setEngineConfiguration(clientConfig);
            locator.setEngine(new AxisClient(clientConfig));
            locator.setRicercaSoap11EndpointAddress(wsAddress);
            service = locator.getRicercaSoap11();           
        } catch (Exception e) {              
            e.printStackTrace();
            logger.error(e);
        }          
    }    
}
