/**
 * 
 */
package it.giustizia.smp.utility.notifiche;

import it.giustizia.smp.dao.NotificheDao;
import it.giustizia.smp.utility.RequestUtil;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

/**
 * Classe della Tag Lib smp:notificheButtons
 * Costruisce i pulsanti di notifiche (Invio Nofiche e Lista Notifiche) se necassari sulla base delle loginche
 * presenti nella tabella T_TP_DEST_ATTO e sullo stato dell'atto e sulla presenza di notifiche già inviate.
 * 
 * Il controllo oltre a costruire i pulsanti scrive un parte javascript che intercetta il click sui
 * pulsanti, modifica al action del form con quella del pulsante relativo ed esegue il submit ai form di 
 * Invio Notifiche o Lista Notifiche
 * 
 * @author m.prisco
 * 
 */

public class NotificheButtons extends RequestContextAwareTag {
	private static final long serialVersionUID = 5013154471198454846L;
	private Logger logger = Logger.getLogger(getClass());
	private String params;
	private String formId;
	private String codAtto;
	
	@Autowired
	private NotificheDao notificheDao;
	
	@Override
	protected int doStartTagInternal() throws Exception {
		try {
			// Autowire service in tag context
		    WebApplicationContext wac = getRequestContext().getWebApplicationContext();
            AutowireCapableBeanFactory acbf = wac.getAutowireCapableBeanFactory();
            acbf.autowireBean(this);
        
			JspWriter jspOut = pageContext.getOut();
			//HttpSession session = pageContext.getSession();
			String fragment = "";
			if(codAtto != null && !codAtto.equalsIgnoreCase(""))
			{
				String isAttoInviabile = notificheDao.isAttoInviabile(Long.valueOf(codAtto),RequestUtil.recuperaIdProcedimento(pageContext.getSession()));
				if ("S".equals(isAttoInviabile) || "D".equals(isAttoInviabile) ) {
					String disabled = ("D".equals(isAttoInviabile)? " disabled ":"");
					String title = ("D".equals(isAttoInviabile)? " Non inviabile fino a quando non sarà acquisito il documento sulla piattaforma documentale  ": "Invia Notifica");
					String buttonInvio = "<td class=\"td_tasto\"><input type=\"button\" " + disabled + " value=\"Invia Notifica\" onclick=\"gotoInviaNotifica()\" title=\"" + title + "\" class=\"nowait\" /></td>\n";
					StringBuilder sb = new StringBuilder(buttonInvio);
					sb.append("<script type=\"text/javascript\">\n");
					sb.append("function gotoInviaNotifica() {\n");
					sb.append("var oldAction = $(\"#" + formId + "\").attr(\"action\");\n");
					sb.append("$(\"#" + formId + "\").attr(\"action\", contextPath+\"/procedimento/notifiche/apriInviaNotifica.htm\");\n");
					sb.append("$(\"#" + formId + "\").submit();\n");
					sb.append("$(\"#" + formId + "\").attr(\"action\", oldAction );\n");
					sb.append("}\n");
					sb.append("</script>\n");
					fragment += sb.toString();
				}
				if (notificheDao.hasNotifiche(Long.valueOf(codAtto),RequestUtil.recuperaIdUfficioUtente(pageContext.getSession()))) {
					String buttonListaNotifiche =  "<td class=\"td_tasto\"><input type=\"button\" value=\"Lista Notifiche\" onclick=\"gotoListaNotifica()\" title=\"Lista Notifiche\" class=\"nowait\" /></td>\n";
					StringBuilder sb = new StringBuilder(buttonListaNotifiche);
					sb.append("<script type=\"text/javascript\">\n");
					sb.append("function gotoListaNotifica() {\n");
					sb.append("var oldAction = $(\"#" + formId + "\").attr(\"action\");\n");
					sb.append("$(\"#" + formId + "\").attr(\"action\", contextPath+\"/procedimento/notifiche/apriListaNotifica.htm\");\n");
					sb.append("$(\"#" + formId + "\").submit();\n");
					sb.append("$(\"#" + formId + "\").attr(\"action\", oldAction );\n");
					sb.append("}\n");
					sb.append("</script>\n");
					fragment += sb.toString();
				}
			}

			jspOut.println(fragment);

		} catch (IOException e) {
			logger.error("Errore tagLib NotificheButtons", e);
		}
		return SKIP_BODY;
	}
	
	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}


	public String getFormId() {
		return formId;
	}


	public void setFormId(String formId) {
		this.formId = formId;
	}


	public String getCodAtto() {
		return codAtto;
	}


	public void setCodAtto(String codAtto) {
		this.codAtto = codAtto;
	}

	
	

}
