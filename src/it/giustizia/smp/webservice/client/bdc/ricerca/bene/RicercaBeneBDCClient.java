package it.giustizia.smp.webservice.client.bdc.ricerca.bene;

import java.rmi.RemoteException;


public class RicercaBeneBDCClient extends RicercaBDCClient {      
    
    public BeneBDC[] ricercaBeniBDC(InputRicercaBeneBDC input) {      
       
        RicercaBeneBDCRequest ricercaBeneBDCRequest = new RicercaBeneBDCRequest();
        ricercaBeneBDCRequest.setInputRicercaBeneBDC(input); 
        RicercaBeneBDCResponse response = null;
        // invoca il servizio remoto
        try {
            response = super.service.ricercaBeneBDC(ricercaBeneBDCRequest );
        } catch (RemoteException e) {           
            e.printStackTrace();
            logger.error(e);
            throw new RuntimeException(e);
        }
        return response.getOutputRicercaBeneBDC();
    }    
    
    public BeneBDC dettaglioBeneBDC(InputDettaglioBeneBDC input) {       
        DettaglioBeneBDCRequest request = new DettaglioBeneBDCRequest();
        request.setInputDettaglioBeneBDC(input);
        DettaglioBeneBDCResponse response = null;
        try {
            response = super.service.dettaglioBeneBDC(request);
        } catch (RemoteException e) {           
            e.printStackTrace();
            logger.error(e);
            throw new RuntimeException(e);
        }
        return response.getOutputDettaglioBeneBDC().getBene();
    }
}
