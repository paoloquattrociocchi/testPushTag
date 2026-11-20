package it.giustizia.smp.webservice.utility;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.ValidationEventLocator;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import it.giustizia.smp.controller.procedimento.FugController;
//F.C. 18/10/2023 Ticket#20231011013 — SITMP - Invio FUG (RITM0705401)
//Creazione classe Handler che scatta quando si valida il FUG
public class FugValidationEventHandler implements ValidationEventHandler{
	protected Logger logger			= Logger.getLogger(getClass());
	boolean ret = false;
    public boolean handleEvent(ValidationEvent ve)  {            
        if (ve.getSeverity()==ve.FATAL_ERROR ||  
                ve .getSeverity()==ve.ERROR){
            ValidationEventLocator  locator = ve.getLocator();
            //print message from valdation event
            logger.error("************************VALIDAZIONE FUG************************");
            logger.error("Messaggio: " + ve.getMessage());
            //output line and column number
            logger.error("Colonna: " + 
                    locator.getColumnNumber() + 
                    " numero linea " + locator.getLineNumber());
            logger.error("Nodo: " + locator.toString());
            logger.error("**********************FINE VALIDAZIONE FUG*********************");
            ret = true;
            
        }
        return ret;
    }

}
