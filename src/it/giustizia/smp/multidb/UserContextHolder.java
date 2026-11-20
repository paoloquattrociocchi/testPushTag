package it.giustizia.smp.multidb;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class UserContextHolder {

    public static final String SMP     = "SMP";
    public static final String SMP_GIU = "SMP_GIU";

    public static String getUserType() {
    	
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String userType = null;
        if (attr != null) {
            if (attr.getRequest() != null) {
                userType = (String) attr.getRequest().getSession(true).getAttribute("CONN");
            }
        }
        return userType;
    }

    public static void setUserType(String userType) {

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        // true == allow create
        HttpSession httpsession = attr.getRequest().getSession(true);
        httpsession.setAttribute("CONN", userType);
    }
}
