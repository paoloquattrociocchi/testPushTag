package it.giustizia.smp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import it.giustizia.smp.dao.StaticListLoader;
import it.giustizia.smp.form.Credenziali;
import it.giustizia.smp.form.LoginForm;
import it.giustizia.smp.form.ProfiloForm;
import it.giustizia.smp.init.SmpListener;
import it.giustizia.smp.model.TAnMenu;
import it.giustizia.smp.model.TAnUfficio;
import it.giustizia.smp.model.TTpTipoUfficio;
import it.giustizia.smp.model.TUtente;
import it.giustizia.smp.multidb.UserContextHolder;
import it.giustizia.smp.service.CommonService;
import it.giustizia.smp.service.LoginService;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.UserProfiles;
import it.giustizia.smp.utility.Utility;

/**
 * LoginController
 * 
 * @author Almaviva S.p.A. Controller per la gestione del Login degli utenti
 */
@Controller
public final class LoginController extends BaseController {

	private static final String SEPARATORE_CAMPI_IN_STRINGA_PROFILO = "|";
	private static final String SEPARATORE_PROFILI_IN_STRINGA = "$";

	@Autowired
	private CommonService commonService;

	@Autowired
	private ReloadableResourceBundleMessageSource configParameterSource;
	private Logger logger = Logger.getLogger(LoginController.class);
	@Autowired
	private LoginService loginService;

	@RequestMapping(value = "/login.htm", method = RequestMethod.POST, params = "INVIA")
	public final ModelAndView ingressoSMP(final LoginForm loginForm, HttpServletRequest request) {

		ProfiloForm profilo = Utility.parseProfiloFromString(loginForm.getProfilo().getProfiloString(),
				SEPARATORE_CAMPI_IN_STRINGA_PROFILO);
		logger.debug("Login profilo " + loginForm.getProfilo().getProfiloString());
		request.getSession().setAttribute(Constants.PROFILO, profilo);
		logger.debug("profilo getRuolo " + profilo.getRuolo());
		// gestione versionamento
		// recupero versione quarto parametro dal db - novembre 2019
		// StaticListLoader prova = new StaticListLoader();

		// String softwareVersion = StaticListLoader.getSoftwareVersion();
		// String softwareVersion_db = commonService.getVersioneDb(softwareVersion);
		//
		// if (softwareVersion_db != null ){
		//
		// softwareVersion = softwareVersion +"." + softwareVersion_db;
		// }else{
		// softwareVersion = softwareVersion +".nd" ;
		// }
		//
		// StaticListLoader.setSoftwareVersion(softwareVersion);
		// //

		if (profilo.getRuolo().equalsIgnoreCase("null")) {
			return login(request);
		}
		TTpTipoUfficio tipoUfficio = ottieniECaricaUfficio(profilo, request);
		// DG Mev Marzo-Giugno Prog 20 - MEV AMM - atti in archivio
		request.getSession().setAttribute("tipoUfficioObj", tipoUfficio);
		caricaTipoDiDatabase(tipoUfficio, request);
		Credenziali credenziali = acquisisciCredenziali(request);
		TUtente utente = ottieniECaricaUtente(profilo, credenziali, request);
		ArrayList<TAnMenu> listaMenu = ottieniECaricaMenu(utente, tipoUfficio, request);
		if ((listaMenu == null) || (listaMenu.size() == 0)) {
			super.setMessage(Constants.MESSAGE, "login.error.nomenu", null, false);
			return new ModelAndView("login", "loginForm", new LoginForm());
		}
		logger.debug("Login effettuato per l'utente " + credenziali.getCommonName());

		// Ciclo sul menu per vedere se esiste la voce ricerca per prevenzione.
		boolean hasRicercaPrevenzione = false;
		String link = "";
		for (TAnMenu menu : listaMenu) {
			if (menu.getDescPercorso() != null
					&& menu.getDescPercorso().contains("visualizzaRicercaProcedimentoPrevenzione.htm")) {

				// System.out.println( menu.getDescGruppo() + menu.getDescPercorso() +
				// menu.getDescVoce() +
				// menu.getNumePadre() + menu.getCodiKIdMenu() + menu.getNumeLivello() );
				if (menu.getNumePadre() != null) {
					link += "idPadre=" + menu.getNumePadre() + "&";
					request.getSession().setAttribute("clickmenu1", menu.getNumePadre() + "");
				}
				if (menu.getNumeLivello() != null) {
					link += "livello=" + menu.getNumeLivello() + "&";
				}
				link += "idMenuAttuale=" + menu.getCodiKIdMenu() + "&";
				hasRicercaPrevenzione = true;
				request.getSession().setAttribute("clickmenu2", menu.getCodiKIdMenu() + "");
				MenuController.caricaMenu("1", request);
				// MenuController.caricaMenu(menu.getCodiKIdMenu()+"", request);

				/*
				 * MenuController.caricaMenuSinistra1Liv(null, request);
				 * MenuController.caricaMenuSinistra2Liv(idPadre, request, false);
				 */

				break;
			}
		}
		ModelAndView mnv = null;
		// Se la voce esiste, costruisco il link ed eseguo la redirect come se avessi
		// cliccato sul menu'
		if (hasRicercaPrevenzione)
			mnv = new ModelAndView(
					"redirect:/menu.htm?path=/procedimento/apri/visualizzaRicercaProcedimentoPrevenzione.htm&descr=Ricerca&"
							+ link);
		// mnv = new ModelAndView("/procedimento/apri/ricercaProcedimento",
		// "procedimento", new
		// ProcedimentoForm());
		else {
			mnv = new ModelAndView("home");
		}
		return mnv;
	}

	@RequestMapping(value = { "/", "/login.htm" }, method = RequestMethod.GET)
	public final ModelAndView login(HttpServletRequest request) {

		request.getSession().invalidate();

		Credenziali credenziali = acquisisciCredenziali(request);
		if (credenziali == null) {
			super.setMessage(Constants.MESSAGE, "login.error.credenziali", null, false);
			return new ModelAndView("login", "loginForm", new LoginForm());
		}
		if (credenziali.hasUnicoProfilo()) {
			return saltaSelezioneDelProfilo(credenziali, request);
		}

		ModelAndView mnv = new ModelAndView("login", "loginForm", new LoginForm());
		mnv.addObject("profili", credenziali.getProfili());
		return mnv;
	}

	@RequestMapping(value = { "/logout.htm" }, method = RequestMethod.GET)
	public final ModelAndView logout(HttpSession session) {

		session.invalidate();
		// modifica maggio 2018 per esternalizzare la configurazione di logout
		// String urlLogoutIAA = configParameterSource.getMessage("IAA_LOGOUT_URL",
		// null, null);
		String urlLogoutIAA = Utility.getSystemProperty(Constants.IAA_LOGOUT_URL, "");
		ModelAndView mnv = new ModelAndView("redirect:" + urlLogoutIAA);
		return mnv;
	}

	private final Credenziali acquisisciCredenziali(HttpServletRequest request) {

		Credenziali credenziali = null;
		// modifica maggio 2018 per esternalizzare la configurazione di logout
		// request.getSession().setAttribute("logoutFromIAA",
		// configParameterSource.getMessage("IAA_LOGOUT_URL", null, null));
		// gestione versionamento
		// recupero versione quarto parametro dal db - novembre 2019
		// StaticListLoader prova = new StaticListLoader();

		String softwareVersion = configParameterSource.getMessage("SOFTWARE_VER", null, null);
		// StaticListLoader.getSoftwareVersion();
		String softwareVersion_db = commonService.getVersioneDb(softwareVersion);

		if (softwareVersion_db != null) {

			softwareVersion = softwareVersion + "." + softwareVersion_db;
		} else {
			softwareVersion = softwareVersion + ".nd";
		}

		// StaticListLoader.setSoftwareVersion(softwareVersion);

		ServletContext sc = request.getSession().getServletContext();
		sc.setAttribute("softwareVersion", softwareVersion);
		// fine recupero versione

		String in = Utility.getSystemProperty(Constants.IAA_LOGOUT_URL, "");
		request.getSession().setAttribute("logoutFromIAA", in);
		logger.debug("acquisisciCredenziali() " + configParameterSource.getMessage("INTEGRAZIONE_IAA", null, null));

		if (configParameterSource.getMessage("INTEGRAZIONE_IAA", null, null).equals("N")) {

			credenziali = acquisisciCredenzialiMock();
		} else {
			credenziali = acquisisciCredenzialiDaIAA(request);
		}
		return credenziali;
	}

	private final Credenziali acquisisciCredenzialiDaIAA(HttpServletRequest request) {

		String nome = request.getHeader("http_givenname");
		String cognome = request.getHeader("http_sn");
		String commonName = request.getHeader("http_cn");
		String codiceFiscale = request.getHeader("http_cf");
		// System.out.println("Codice Fiscale IAA:" + codiceFiscale);
		String multiProfilo = request.getHeader("http_profilo");
		if (nome == null || nome.trim().isEmpty() || cognome == null || cognome.trim().isEmpty() || multiProfilo == null
				|| multiProfilo.trim().isEmpty()) {
			return null;
		}
		List<ProfiloForm> profili = parseProfilo(multiProfilo, commonName);
		return new Credenziali(nome, cognome, codiceFiscale, commonName, multiProfilo, profili);
	}

	private final Credenziali acquisisciCredenzialiMock() {

		String nome = "Eng";
		String cognome = "Test";
		String codiceFiscale = "AAABBB56D34K546K";
		// String profiliStringa = "Palermo|Palermo|Tribunale|Cancelliere";

		// GC 03/12/2020: Esternalizzazione multiprofilo per utilizzo su server diversi
		// (SITMP-285)
		// inizio modifica
		String multiProfilo = UserProfiles.getString("USER_PROFILE");
		
		String commonName = "Eng.Test"; // Ag
		List<ProfiloForm> profili = parseProfilo(multiProfilo, commonName);
		return new Credenziali(nome, cognome, codiceFiscale, commonName, multiProfilo, profili);

		/**
		 * vecchio_profilo multiProfilo = "Napoli|Napoli|DGSIA|Amministratore$"
		 * ESERCIZIO ENG
		 * 
		 * + "Napoli|Napoli|Procura della Repubblica|Segretario$" +
		 * "Napoli|Napoli|Procura della Repubblica|Magistrato$" +
		 * "Napoli|Napoli|Tribunale|Cancelliere$" +
		 * "Napoli|Napoli|Tribunale|Magistrato$" + "Napoli|Napoli|Procura Generale della
		 * Repubblica presso la CdA|Segretario$" + "Napoli|Napoli|Procura Generale della
		 * Repubblica presso la CdA|Magistrato$" + "Napoli|Napoli|Corte di
		 * Appello|Cancelliere$" + "Napoli|Napoli|Corte di Appello|Magistrato$"
		 * 
		 * + "Napoli|Santa Maria Capua Vetere|Procura della Repubblica|Segretario$" +
		 * "Napoli|Santa Maria Capua Vetere|Tribunale|Cancelliere$"
		 * 
		 * ; COLLAUDO ENG + "Palermo|Palermo|Procura della Repubblica|Segretario$" +
		 * "Palermo|Palermo|Procura della Repubblica|Magistrato$" +
		 * "Palermo|Palermo|Tribunale|Cancelliere$" +
		 * "Palermo|Palermo|Tribunale|Magistrato$" + "Palermo|Palermo|Procura Generale
		 * della Repubblica presso la CdA|Segretario$" + "Palermo|Palermo|Procura
		 * Generale della Repubblica presso la CdA|Magistrato$" + "Palermo|Palermo|Corte
		 * di Appello|Cancelliere$" + "Palermo|Palermo|Corte di Appello|Magistrato$"
		 * 
		 * + "Palermo|Agrigento|Procura della Repubblica|Segretario$" +
		 * "Palermo|Agrigento|Tribunale|Cancelliere$";
		 * 
		 * 
		 * + "Palermo|Termini Imerese|Procura della Repubblica|Segretario$"
		 *
		 * + "Caltanissetta|Caltanissetta|Procura Generale della Repubblica presso la
		 * CdA|Segretario$" + "Caltanissetta|Caltanissetta|Corte di
		 * Appello|Cancelliere$" + "Caltanissetta|Caltanissetta|Procura della
		 * Repubblica|Segretario$" +
		 * "Caltanissetta|Caltanissetta|Tribunale|Cancelliere$"
		 * 
		 * + "Catania|Catania|Procura Generale della Repubblica presso la
		 * CdA|Segretario$" + "Catania|Catania|Corte di Appello|Cancelliere$" +
		 * "Catania|Catania|Procura della Repubblica|Segretario$" +
		 * "Catania|Catania|Tribunale|Cancelliere$"
		 * 
		 * + "Messina|Messina|Procura Generale della Repubblica presso la
		 * CdA|Segretario$" + "Messina|Messina|Corte di Appello|Cancelliere$" +
		 * "Messina|Messina|Procura della Repubblica|Segretario$" +
		 * "Messina|Messina|Tribunale|Cancelliere$"
		 * 
		 * + "Potenza|Lxxxxxxxx|Procura della Repubblica|Segretario$" +
		 * "Potenza|Pxxxxxx|Procura della Repubblica|Segretario$" +
		 * "Potenza|Pxxxxxx|Tribunale|Cancelliere$" + "Potenza|Pxxxxxx|Procura Generale
		 * della Repubblica presso la CdA|Segretario$"
		 * 
		 * + "Salerno|Salerno|Procura della Repubblica|Segretario$" +
		 * "Salerno|Salerno|Tribunale|Cancelliere$" + "Salerno|Salerno|Procura Generale
		 * della Repubblica presso la CdA|Segretario$" + "Salerno|Salerno|Corte di
		 * Appello|Cancelliere$"
		 * 
		 * + "Catanzaro|Catanzaro|Procura della Repubblica|Segretario$"
		 * 
		 * + "Reggio Di Calabria|Reggio Di Calabria|Tribunale|Cancelliere$" + "Reggio Di
		 * Calabria|Reggio Di Calabria|Tribunale|Segretario$" + "Reggio Di
		 * Calabria|Reggio Di Calabria|Corte di Appello|Cancelliere$" + "Reggio Di
		 * Calabria|Reggio Di Calabria|Corte di Appello|Segretario$" + "Reggio Di
		 * Calabria|Reggio Di Calabria|Corte di Appello|Magistrato$" + "Reggio Di
		 * Calabria|Reggio Di Calabria|Procura della Repubblica|Segretario$"
		 * 
		 * + "Potenza|Matera|Procura della Repubblica|Segretario$" +
		 * "Potenza|Matera|Tribunale|Cancelliere$" + "Potenza|Melfi|Procura della
		 * Repubblica|Segretario$" + "Potenza|Melfi|Tribunale|Cancelliere$"
		 * 
		 * + "Salerno|Nocera Inferiore|Procura della Repubblica|Segretario$" +
		 * "Salerno|Nocera Inferiore|Tribunale|Cancelliere$" + "Salerno|Vallo della
		 * Lucania|Procura della Repubblica|Segretario$" + "Salerno|Vallo della
		 * Lucania|Tribunale|Cancelliere$"
		 * 
		 * + "Lecce|Lecce|Procura Generale della Repubblica presso la CdA|Segretario$" +
		 * "Lecce|Lecce|Corte di Appello|Cancelliere$" + "Lecce|Brindisi|Procura della
		 * Repubblica|Segretario$" + "Lecce|Brindisi|Tribunale|Cancelliere$" +
		 * "Lecce|Lecce|Procura della Repubblica|Segretario$" +
		 * "Lecce|Lecce|Tribunale|Cancelliere$"
		 * 
		 * + "Napoli|Napoli|Procura della Repubblica|Segretario$" +
		 * "Napoli|Napoli|Procura della Repubblica|Magistrato$" +
		 * "Napoli|Napoli|Tribunale|Cancelliere$" +
		 * "Napoli|Napoli|Tribunale|Magistrato$" + "Napoli|Napoli|Procura Generale della
		 * Repubblica presso la CdA|Segretario$" + "Napoli|Napoli|Procura Generale della
		 * Repubblica presso la CdA|Magistrato$" + "Napoli|Napoli|Corte di
		 * Appello|Cancelliere$" + "Napoli|Napoli|Corte di Appello|Magistrato$"
		 *
		 * + "Napoli|Santa Maria Capua Vetere|Procura della Repubblica|Segretario$" +
		 * "Napoli|Santa Maria Capua Vetere|Tribunale|Cancelliere$"
		 * 
		 * + "Napoli|Napoli Nord|Procura della Repubblica|Segretario"
		 * 
		 * + "Roma|Roma|Procura Generale della Repubblica presso la CdA|Segretario$" +
		 * "Roma|Roma|Corte di Appello|Cancelliere$"
		 * 
		 * + "Bari|Bari|Procura della Repubblica|Segretario$" + "Bari|Bari|Procura della
		 * Repubblica|Magistrato$" + "Bari|Bari|Procura Generale della Repubblica presso
		 * la CdA|Segretario$" + "Bari|Bari|Procura Generale della Repubblica presso la
		 * CdA|Magistrato$" + "Bari|Bari|Corte di Appello|Cancelliere$" +
		 * "Bari|Bari|Corte di Appello|Magistrato$" + "Bari|Bari|Tribunale|Cancelliere$"
		 * + "Bari|Bari|Tribunale|Magistrato";
		 * 
		 * String multiProfilo =
		 * "Palermo|Palermo|Tribunale|Cancelliere$Palermo|Palermo|Procura della
		 * Repubblica|Segretario$Palermo|Palermo|Corte di
		 * Appello|Cancelliere$Palermo|Palermo|Procura Generale della Repubblica presso
		 * la
		 * CdA|Segretario$Palermo|Agrigento|Tribunale|Cancelliere$Palermo|Agrigento|Procura
		 * della Repubblica|Segretario$Caltanissetta|Caltanissetta|Corte di
		 * Appello|Cancelliere$Caltanissetta|Caltanissetta|Procura Generale della
		 * Repubblica presso la CdA|Segretario$Palermo|Termini Imerese|Procura della
		 * Repubblica|Segretario"; String profiliStringa = "Palermo|Termini
		 * Imerese|Procura della
		 * Repubblica|Segretario$Palermo|Palermo|Tribunale|Cancelliere$Palermo|Palermo|Procura
		 * della Repubblica|Segretario"; endregion
		 * 
		 * fine modifica
		 */

	}

	private final void caricaTipoDiDatabase(final TTpTipoUfficio tipoUfficio, HttpServletRequest request) {

		if (loginService.isOfficeTypeTribunale(tipoUfficio)) {
			logger.debug("profilo tribunale yes ");
			request.getSession().setAttribute(Constants.TIPO_UTENTE, Constants.TIPO_UTENTE_SMP_GIU);
			UserContextHolder.setUserType(UserContextHolder.SMP_GIU);
		} else {
			logger.debug("profilo tribunale no ");
			request.getSession().setAttribute(Constants.TIPO_UTENTE, Constants.TIPO_UTENTE_SMP);
			UserContextHolder.setUserType(UserContextHolder.SMP);
		}
	}

	private final ArrayList<TAnMenu> ottieniECaricaMenu(final TUtente utente, final TTpTipoUfficio tipoUfficio,
			HttpServletRequest request) {

		ArrayList<TAnMenu> listaMenu = loginService.getMenuUtente(utente, tipoUfficio.getCodiKIdTipoUff());
		logger.debug("carica menu ************ ");
		logger.debug("carica menu ************ " + utente.getTTpProfilo().getDescProfilo() + " e lunghezza "
				+ utente.getTTpProfilo().getDescProfilo().length());
		logger.debug("tipo ufficio ************ " + tipoUfficio.getCodiKIdTipoUff());

		request.getSession().setAttribute("menu", listaMenu);
		if (listaMenu == null || listaMenu.size() == 0)
			logger.debug("************Attenzione *********** Il menu è vuoto***********");
		request.getSession().setAttribute("path", "Home");
		return listaMenu;
	}

	private final TTpTipoUfficio ottieniECaricaUfficio(final ProfiloForm profilo, HttpServletRequest request) {

		TTpTipoUfficio tipoUfficio = loginService.getTTipoUfficio(profilo.getDescrizioneUfficio());
		logger.debug("profilo.getDescrizioneUfficio() " + profilo.getDescrizioneUfficio());
		TAnUfficio ufficio = loginService.getUfficioByTipoUfficioAndCitta(tipoUfficio,
				profilo.getCitta().toUpperCase());
		logger.debug("profilo.getCitta().toUpperCase() " + profilo.getCitta().toUpperCase());
		profilo.setIndirizzoUfficio(ufficio.getDescIndirizzoSede());
		profilo.setDescDettaglioUfficio(ufficio.getDescUfficio());
		Character flagDDA = ufficio.getFlgDda();
		if (Character.valueOf('S').equals(flagDDA)) {
			profilo.setDDA(true);
		} else {
			profilo.setDDA(false);
		}
		request.getSession().setAttribute(Constants.ID_UFFICIO_UTENTE, ufficio.getCodiKIdUfficio());
		logger.debug("ufficio.getCodiKIdUfficio() " + ufficio.getCodiKIdUfficio());
		request.getSession().setAttribute(Constants.ID_TIPO_UFFICIO, tipoUfficio.getCodiKIdTipoUff());
		logger.debug("tipoUfficio.getCodiKIdTipoUff() " + tipoUfficio.getCodiKIdTipoUff());
		return tipoUfficio;
	}

	private final TUtente ottieniECaricaUtente(final ProfiloForm profilo, final Credenziali credenziali,
			HttpServletRequest request) {

		TUtente utente = loginService.getUtenteByProfilo(profilo.getProfiloString(), profilo);
		utente.setNome(credenziali.getNome());
		utente.setCognome(credenziali.getCognome());
		utente.setCodiceFiscale(credenziali.getCodiceFiscale());
		request.getSession().setAttribute(Constants.ID_UTENTE, utente);
		// session.setAttribute(Constants.ID_CODICE_UTENTE, 1);
		request.getSession().setAttribute(Constants.ID_CODICE_UTENTE, credenziali.getCommonName());

		logger.debug("credenziali.getNome() " + credenziali.getNome());
		logger.debug("credenziali.getCognome() " + credenziali.getCognome());
		logger.debug("credenziali.getCodiceFiscale() " + credenziali.getCodiceFiscale());
		logger.debug("credenziali.getCommonName() " + credenziali.getCommonName());

		return utente;
	}

	private final ProfiloForm ottieniNextProfilo(final StringTokenizer profiliTokenizer, final int indiceProfiloAttuale,
			final String commonName) {

		String tmpStringaProfilo = profiliTokenizer.nextToken();
		ProfiloForm tmpProfilo = Utility.parseProfiloFromString(tmpStringaProfilo, SEPARATORE_CAMPI_IN_STRINGA_PROFILO);
		tmpProfilo.setCommonName(commonName);
		tmpProfilo.setIdProfilo(indiceProfiloAttuale);
		return tmpProfilo;
	}

	private final List<ProfiloForm> parseProfilo(final String stringaProfili, final String commonName) {

		StringTokenizer profiliTokenizer = new StringTokenizer(stringaProfili, SEPARATORE_PROFILI_IN_STRINGA);

		logger.debug("parseProfilo " + stringaProfili);
		List<ProfiloForm> profili = new ArrayList<ProfiloForm>();
		int indiceProfiloAttuale = 1;
		while (profiliTokenizer.hasMoreTokens()) {
			ProfiloForm tmpProfilo = ottieniNextProfilo(profiliTokenizer, indiceProfiloAttuale, commonName);
			profili.add(tmpProfilo);
			indiceProfiloAttuale++;
		}
		return profili;
	}

	private final ModelAndView saltaSelezioneDelProfilo(final Credenziali credenziali, HttpServletRequest request) {

		ProfiloForm profilo = credenziali.getProfilo(0);
		LoginForm loginForm = new LoginForm();
		loginForm.setProfilo(profilo);
		return ingressoSMP(loginForm, request);
	}
}
