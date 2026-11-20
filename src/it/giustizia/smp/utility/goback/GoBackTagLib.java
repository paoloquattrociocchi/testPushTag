/**
 * 
 */
package it.giustizia.smp.utility.goback;

import it.giustizia.smp.utility.Constants;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Classe della Tag Lib smp:back<br>
 * Costruisce il corpo della pagina jsp del tasto indietro
 * 
 * @author g.gabriele
 * 
 */
public class GoBackTagLib extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6420704540486115046L;

	private Logger logger = Logger.getLogger(getClass());

	private String buttonId;

	private String target;
	private String commandName;

//	private boolean useJavaScript = false;

	@Override
	public int doStartTag() throws JspException {
		try {
			JspWriter jspOut = pageContext.getOut();
			HttpSession session = pageContext.getSession();

			BreadcrumbsList breadcrumbsList = recuperaBreadCrumbs(session);
			Breadcrumbs crumbs = breadcrumbsList.getLast();

			if (crumbs == null || breadcrumbsList.getList().size()==1) {
				jspOut.println(disableBackButton());
				return SKIP_BODY;
			}

			String fragment = "";

			if (StringUtils.equalsIgnoreCase("POST", crumbs.getMethod())) {
				fragment = generatePOSTRequest(crumbs);
			} else {
				fragment = generateGETRequest(crumbs);
			}

			if (logger.isDebugEnabled()) {
				logger.debug("Fragment -> " + fragment);
			}

			jspOut.println(fragment);

		} catch (IOException e) {
			logger.error("Errore tagLib back", e);
		}

		return SKIP_BODY;
	}

	/**
	 * Genera il fragmento di codice quando il back è una chiamata POST
	 * 
	 * @param crumbs
	 * @return
	 */
	private String generatePOSTRequest(Breadcrumbs crumbs) {
		String action = crumbs.getAction();
		long idTimestamp = System.currentTimeMillis();

		StringBuilder sb = new StringBuilder();
		String stringFormat = null;


		String idForm = "id_back_form_" + idTimestamp;
		stringFormat = "<form id=\"%s\" method=\"%s\" action=\"%s\">\n";
		sb.append(String.format(stringFormat, idForm, crumbs.getMethod(), action));

		// add the parameter
		sb.append(buildParameters(crumbs));
		
		sb.append("</form>");
		
		sb.append(bindButtonClick(idTimestamp,idForm,crumbs.getAction(), crumbs));

		return sb.toString();
	}
	/**
	 * Aggancia al button la funzione di back
	 * @param idForm
	 * @param action
	 * @param crumbs
	 * @return il frammento di codice
	 */
	private String bindButtonClick(long idTimestamp,String idForm,String action,Breadcrumbs crumbs){
		
		String stringFormat = "\t\tgoBack_('%s','%s',this.id,'%s','%s','%s','%s','%s');\n";
		
		
		StringBuilder sb = new StringBuilder();
		sb.append("<script type=\"text/javascript\">\n");
		sb.append("$(document).ready(function() {\n");

		// unbind onclick
		//Giacomo 18/07/2013 Commentato per il wait modale
//		sb.append("\t$('#" + buttonId + "').attr('onclick','').unbind('click');\n");
		sb.append("\t$('#" + buttonId + "').show();\n");
		sb.append("\t$('#" + buttonId + "').click(function() {\n");
		
		
		sb.append(String.format(stringFormat, idTimestamp, idForm, crumbs.getMethod(), action, crumbs.getParams(), target, commandName));
		sb.append("\t});\n");// fine click

		sb.append("});\n");// fine ready
		sb.append("</script>\n");
		return sb.toString();
	}
	
	
	private String disableBackButton(){
		StringBuilder sb = new StringBuilder();
		sb.append("<script type=\"text/javascript\">\n");
		sb.append("$(document).ready(function() {\n");

		// disable button
		sb.append("\t$('#" + buttonId + "').hide();\n");
		//disabilito anche il parent
		sb.append("\t$('#" + buttonId + "').parent('td').hide();\n");

		sb.append("});\n");// fine ready
		sb.append("</script>\n");
		return sb.toString();
	}

	/**
	 * Costruisce i parametri come tanti campi hidden
	 * @param crumbs
	 * @return il fragmento di codice
	 */
	private String buildParameters(Breadcrumbs crumbs) {
		StringBuilder sb = new StringBuilder();
		String stringFormat = "<input type=\"hidden\" id=\"%s\" name=\"%s\" value=\"%s\">\n";

		Map<String, String> parameters = crumbs.getParameters();
		for (String key : parameters.keySet()) {

			String value = parameters.get(key);
			
			sb.append(String.format(stringFormat, key, key, value));
		}

		return sb.toString();
	}


	/**
	 * Genera il fragmento di codice quando il back è una chiamata GET
	 * 
	 * @param crumbs
	 * @return
	 */
	private String generateGETRequest(Breadcrumbs crumbs) {

		String action = crumbs.getAction();

		if (!crumbs.getParameters().isEmpty()) {
			action = action + "?" + crumbs.getUrlEncoded();
		}

		return bindButtonClick(0,null,action, crumbs);

	}

	/**
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * @param target
	 *            the target to set
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * @return the commandName
	 */
	public String getCommandName() {
		return commandName;
	}

	/**
	 * @param commandName
	 *            the commandName to set
	 */
	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}

	/**
	 * @return the buttonId
	 */
	public String getButtonId() {
		return buttonId;
	}

	/**
	 * @param buttonId
	 *            the buttonId to set
	 */
	public void setButtonId(String buttonId) {
		this.buttonId = buttonId;
	}

	/**
	 * Recupera la {@link BreadcrumbsList} dalla sessione
	 * 
	 * @param session
	 * @return la {@link BreadcrumbsList} dalla sessione
	 */
	private BreadcrumbsList recuperaBreadCrumbs(HttpSession session) {
		if (session.getAttribute(Constants.KEY_BREADCRUMB_MANUAL) == null) {
			session.setAttribute(Constants.KEY_BREADCRUMB_MANUAL, new BreadcrumbsList());
		}
		return (BreadcrumbsList) session.getAttribute(Constants.KEY_BREADCRUMB_MANUAL);
	}
}
