package it.giustizia.smp.multidb;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


public class UserRoutingDataSource extends AbstractRoutingDataSource {

	private String schemaDocumentale = "";
	
    public void setSchemaDocumentale(String schemaDocumentale) {
		this.schemaDocumentale = schemaDocumentale;
	}
    
    public java.util.logging.Logger getParentLogger() {
    	// Aggiunto per gestire la compilazione con jdk 7
    	return null;
    }

	@Override
    protected Object determineCurrentLookupKey() {
    	String userType = UserContextHolder.getUserType();
    	
    	if(userType == null){
    		//System.out.println("Lettura da datasource:" + schemaDocumentale );
    		userType = schemaDocumentale;
    	}
    	else{
    		userType = UserContextHolder.getUserType();
    		//System.out.println("Lettura da datasource:" + userType );
	        //System.out.println("*** AbstractRoutingDataSource CurrentLookupKey: " + userType);
	        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	        if (attr != null) {
	            if (attr.getRequest() != null) {
	                userType = (String) attr.getRequest().getSession(true).getAttribute("CONN");
	            }
	        }
    	}
        return userType;
    }
    
    public void selectConnection(String schema){
    	this.schemaDocumentale = schema;
    	super.resolveSpecifiedLookupKey(schema);
    	//determineCurrentLookupKey();
    }
}
