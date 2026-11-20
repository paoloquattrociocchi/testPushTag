package it.giustizia.smp.webservice.utility;

import org.apache.axis.AxisFault;
import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.handlers.BasicHandler;
import org.apache.log4j.Logger;


public class OutcomingHandler extends BasicHandler {
    
    private Logger logger = Logger.getLogger(OutcomingHandler.class);   
    
    public void invoke(MessageContext msgContext) throws AxisFault {
        Message outMsg = msgContext.getResponseMessage();       
        if( outMsg != null ) {
            //System.out.println(outMsg.getSOAPPartAsString());
            logger.info("Messaggio SOAP di risposta"+"\n");
            logger.info(outMsg.getSOAPPartAsString());
        }            
    }       
}
