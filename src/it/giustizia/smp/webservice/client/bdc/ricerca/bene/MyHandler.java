package it.giustizia.smp.webservice.client.bdc.ricerca.bene;



import org.apache.axis.AxisFault;
import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.handlers.BasicHandler;
import org.apache.log4j.Logger;

public class MyHandler extends BasicHandler {
    
    private Logger logger = Logger.getLogger(MyHandler.class);
    private static boolean flagIncoming = true;
    
    public void invoke(MessageContext msgContext) throws AxisFault {
        Message inMsg = msgContext.getRequestMessage();
        Message outMsg = msgContext.getResponseMessage();
       
        if( flagIncoming && inMsg != null ) {
            System.out.println(inMsg.getSOAPPartAsString());
            flagIncoming = false;
        }
            //logger.debug("Messaggio SOAP in uscita"+"\n"+inMsg.getSOAPPartAsString());
        if( outMsg != null )
            //logger.debug("Messaggio SOAP in ingresso"+"\n"+outMsg.getSOAPPartAsString());
            System.out.println(outMsg.getSOAPPartAsString());        
    } 
    
}
