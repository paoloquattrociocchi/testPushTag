package it.giustizia.smp.webservice.utility;

import org.apache.axis.AxisFault;
import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.handlers.BasicHandler;
import org.apache.log4j.Logger;


public class IncomingHandler extends BasicHandler {
    
    private Logger logger = Logger.getLogger(IncomingHandler.class);   
    
    public void invoke(MessageContext msgContext) throws AxisFault {
        Message inMsg = msgContext.getRequestMessage();       
        if( inMsg != null ) {
            //System.out.println(inMsg.getSOAPPartAsString());  
            logger.info("Messaggio SOAP di richiesta"+"\n");
            logger.info(inMsg.getSOAPPartAsString());
        }            
    }     
}
