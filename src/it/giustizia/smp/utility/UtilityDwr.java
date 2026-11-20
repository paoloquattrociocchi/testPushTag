package it.giustizia.smp.utility;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;


public class UtilityDwr {    
    
    @Autowired(required = true)
    private HttpServletRequest request;
    
    public String existsError() {
        return "false";
    } 
    
    public boolean removeFromHashMapSession(String id) {
        boolean flagOk = true;
        try {
            HashMap<String,Object> map = (HashMap)request.getSession().getAttribute(Constants.GenericConstants.ID_HASHMAP_SESSION);
            if( map != null )
                map.remove(id);
            else
                flagOk = false;
        } catch(Exception e) { flagOk = false; }
        finally {
            return flagOk;
        }
    }

}
