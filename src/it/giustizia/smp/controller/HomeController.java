package it.giustizia.smp.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import it.giustizia.smp.model.TAnMenu;
import it.giustizia.smp.model.TTpTipoUfficio;
import it.giustizia.smp.model.TUtente;
import it.giustizia.smp.service.HomeService;
import it.giustizia.smp.utility.Constants;

/**
 * HomeController
 * 
 * @author Almaviva S.p.A.
 *         Classe Controller per la gestione del flusso
 *         inerente alla generazione dellapagina di Home
 */
@Controller
public class HomeController {
	
	@Autowired
	private HomeService homeService;
	
	private static Logger logger = Logger.getLogger(HomeController.class);
	
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView showHome(HttpServletRequest request) {

		if (request.getParameter("idPadreMenu") != null) {
			request.getSession().setAttribute("disabledViewProc", null);
			request.getSession().setAttribute("disabledViewMenu", null);
					
			//DG Mev Marzo-Giugno Prog 20 - MEV AMM - atti in archivio
			ottieniECaricaMenu((TUtente)request.getSession().getAttribute("utente"), (TTpTipoUfficio)request.getSession().getAttribute("tipoUfficioObj"), request);
			
			MenuController.caricaMenu(request.getParameter("idPadreMenu"), request);
			if ((request.getParameter("idPadreMenu") != null) && request.getParameter("idPadreMenu").equals("1")) {
				request.getSession().setAttribute("procedimento", null);
				request.getSession().setAttribute("soggettiTemp", null);
			} else if ((request.getParameter("idPadreMenu") != null) && request.getParameter("idPadreMenu").equals("2")) {
			    request.getSession().setAttribute("descr", "Scambi informativi");
				return new ModelAndView("redirect: flussi/flussiDaElaborare.htm");
			} else if  ((request.getParameter("idPadreMenu") != null) && request.getParameter("idPadreMenu").equals("3")) {
			    request.getSession().setAttribute("descr", "Statistiche");
				return new ModelAndView("redirect:statistiche.htm?descr=elenco&idPadre=102&idMenuAttuale=103&livello=3");
			} else if ((request.getParameter("idPadreMenu") != null) && 
					(request.getParameter("idPadreMenu").equals("4") || request.getParameter("idPadreMenu").equals("5"))  
				) {
			    request.getSession().setAttribute("descr", "Notifiche e Comunicazioni");
				return new ModelAndView("redirect:notifiche/notifiche.htm");
			}
		}
		request.getSession().removeAttribute("clickmenu1");
		request.getSession().removeAttribute("clickmenu2");
		
		request.getSession().removeAttribute(Constants.TIPO_FASCICOLO);
		
		return new ModelAndView("home", "login", new TUtente());
		
	}
    //DG Mev Marzo-Giugno Prog 20 - MEV AMM - atti in archivio
    private final ArrayList<TAnMenu> ottieniECaricaMenu(final TUtente utente,
			final TTpTipoUfficio tipoUfficio, HttpServletRequest request) {

		ArrayList<TAnMenu> listaMenu = homeService.getMenuUtente(utente, tipoUfficio.getCodiKIdTipoUff());
		logger.debug("carica menu ************ ");
		logger.debug("carica menu ************ " + utente.getTTpProfilo().getDescProfilo() + " e lunghezza "
				+ utente.getTTpProfilo().getDescProfilo().length());
		logger.debug("tipo ufficio ************ " + tipoUfficio.getCodiKIdTipoUff());

		request.getSession().setAttribute("menu", listaMenu);
		if (listaMenu == null || listaMenu.size() == 0)
//			logger.debug("************Attenzione *********** Il menu è vuoto***********");
		request.getSession().setAttribute("path", "Home");
		return listaMenu;
	}

    
}
