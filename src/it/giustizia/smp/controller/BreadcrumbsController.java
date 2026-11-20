/**
 * 
 */
package it.giustizia.smp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;

import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.RequestUtil;
import it.giustizia.smp.utility.goback.Breadcrumbs;
import it.giustizia.smp.utility.goback.BreadcrumbsList;

/**
 * 
 * Controller Generico relativo alla gestione delle {@link Breadcrumbs} ovvero
 * del tasto Indietro
 * 
 * @author g.gabriele
 * 
 */
@Controller
public class BreadcrumbsController extends BaseController {

	/**
	 * Pulisce tutta la lista della {@link Breadcrumbs}
	 */
	protected void cleanBreadcrumb(HttpSession	session) {
		session.setAttribute(Constants.KEY_BREADCRUMB_MANUAL, new BreadcrumbsList());
	}

	/**
	 * Recupera la {@link BreadcrumbsList} dalla {@link HttpSession}<br>
	 * Se non esiste la crea e la inserisce nella sessione
	 * 
	 * @return {@link BreadcrumbsList}
	 */
	private BreadcrumbsList recuperaBreadCrumbs(HttpSession	session) {
		if (session.getAttribute(Constants.KEY_BREADCRUMB_MANUAL) == null) {
			session.setAttribute(Constants.KEY_BREADCRUMB_MANUAL, new BreadcrumbsList());
		}
		return (BreadcrumbsList) session.getAttribute(Constants.KEY_BREADCRUMB_MANUAL);
	}

	/**
	 * Aggiunge una {@link Breadcrumbs} con parametro action e params
	 * RequestMapping(value = "action.html", method = RequestMethod.POST, params= { "PARAMS" })
	 * 
	 * @param method
	 * @param action
	 * @param params
	 * @return la breadcrumbs appena creata
	 */
	protected Breadcrumbs addBreadcrumbs(String method, String action, String params) {
		BreadcrumbsList breadcrumbsList = recuperaBreadCrumbs(getSession());
		HttpServletRequest request = getRequest();
		if(!action.startsWith(request.getContextPath())){
			action = request.getContextPath()+action;
		}
		
		
		Breadcrumbs b = breadcrumbsList.addBreadcrumbs(method, action, params);
		
		//recupero tutti i parametri dalla request
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.putAll(RequestUtil.recuperaParametri(request));

		b.addParameter(parameters);
		
		return b;
	}

	/**
	 * Aggiunge una {@link Breadcrumbs} con il parametro param
	 * 
	 * @param params
	 * @return la breadcrumbs appena creata
	 */
	protected Breadcrumbs addBreadcrumbs(String params, HttpServletRequest request) {
		String action = request.getRequestURI();
		String methods = request.getMethod();

		return addBreadcrumbs(methods, action, params);
	}

	/**
	 * Aggiunge la {@link Breadcrumbs} senza parametri
	 * 
	 * @return la {@link Breadcrumbs} senza parametri appena creata
	 */
	protected Breadcrumbs addBreadcrumbs() {
		return addBreadcrumbs(null,getRequest());
	}

	/**
	 * Restituisce la penultima {@link Breadcrumbs}
	 * 
	 * @return la penultima {@link Breadcrumbs}
	 */
	public Breadcrumbs getLastBreadCrumbs( HttpSession session) {
		BreadcrumbsList breadcrumbsList = recuperaBreadCrumbs(session);
		return breadcrumbsList.getLast();
	}
	
	protected void removeLast(String method, String action, String params,String remove,HttpSession	session) {
		BreadcrumbsList breadcrumbsList = recuperaBreadCrumbs(session);
        breadcrumbsList.cleanBreadcrumb(method,action,params,remove);
        cleanBreadcrumb(session);
        session.setAttribute(Constants.KEY_BREADCRUMB_MANUAL,breadcrumbsList);
		
	
	}	
}
