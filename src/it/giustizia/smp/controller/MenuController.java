package it.giustizia.smp.controller;

import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.BeneForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.ProfiloForm;
import it.giustizia.smp.model.TAnMenu;
import it.giustizia.smp.model.TRelProcBeneAttoAmm;
import it.giustizia.smp.service.AttoService;
import it.giustizia.smp.service.BeneService;
import it.giustizia.smp.service.CommonService;
import it.giustizia.smp.utility.ComparatorUtil;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.RequestUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * MenuController
 * 
 * @author Almaviva S.p.A. Classe Controller per la gestione dei menu dell' applicazione. Il menu principale, il menu di sinistra diviso in due
 *         livelli ed il quarto menu rappresentato dai pulsanti inerenti al Menù
 */
@SuppressWarnings("unchecked")
@Controller
public class MenuController extends BreadcrumbsController {

	@Autowired
	private CommonService cs;
	
	@Autowired
    private BeneService bs;

	@Autowired
    private AttoService as;	
	
	@PostConstruct
	private void init() {
	     commonService = this.cs;
		 beneService = this.bs;
		 attoService = this.as;
	   }
	private static CommonService commonService;	
	private static BeneService beneService;
	private static AttoService attoService;
	

	//private static ArrayList<TAnMenu> menu;
	//private static ArrayList<TAnMenu> menuSinistra1Liv;
	//private static ArrayList<TAnMenu> menuSinistra2Liv;
	//private static ArrayList<TAnMenu> menuSinistra3Liv;
	private static final String  SESSION_MENU = "exStaticMenu";
	private static final String  SESSION_1LIV = "exStaticMenuSinistra1Liv";
	private static final String  SESSION_2LIV = "exStaticMenuSinistra2Liv";
	private static final String  SESSION_3LIV = "exStaticMenuSinistra3Liv";
	
	public static void caricaMenu(String idPadre, HttpServletRequest request) {
		
		//MenuController.menu = (ArrayList<TAnMenu>) request.getSession().getAttribute("menu");

		request.getSession().setAttribute(SESSION_MENU, (ArrayList<TAnMenu>) request.getSession().getAttribute("menu"));
		caricaMenuSinistra1Liv(idPadre, request);
		caricaMenuSinistra2Liv(idPadre, request, false);
		caricaMenuSinistra3Liv(request);
	}

	public static void caricaMenu(String idPadre, HttpServletRequest request, boolean cameralini) {
		
		//MenuController.menu = (ArrayList<TAnMenu>) request.getSession().getAttribute("menu");
		request.getSession().setAttribute(SESSION_MENU, (ArrayList<TAnMenu>) request.getSession().getAttribute("menu"));
		caricaMenuSinistra1Liv(idPadre, request, cameralini);
		caricaMenuSinistra2Liv(idPadre, request, cameralini);
		caricaMenuSinistra3Liv(request);
	}
	
	
	public static ArrayList<TAnMenu> getMenu(HttpServletRequest request) {
		return (ArrayList<TAnMenu>)request.getSession().getAttribute(SESSION_MENU);
	}
	
	public static ArrayList<TAnMenu> getMenuController1Liv(HttpServletRequest request) {
		return (ArrayList<TAnMenu>)request.getSession().getAttribute(SESSION_1LIV);
	}
	public static ArrayList<TAnMenu> getMenuController2Liv(HttpServletRequest request) {
		return (ArrayList<TAnMenu>)request.getSession().getAttribute(SESSION_2LIV);
	}
	public static ArrayList<TAnMenu> getMenuController3Liv(HttpServletRequest request) {
		return (ArrayList<TAnMenu>)request.getSession().getAttribute(SESSION_3LIV);
	}
	
	private static void caricaMenuSinistra1Liv(String idPadre, HttpServletRequest request) {
		// Inserito per evitare il null pointer sulle righe successive.
		// il codice deve essere comunque rivisto perchè il menu è static (Non mi sembra corretto).
		//if  (MenuController.menu == null)
		if  (getMenu(request) == null)
			caricaMenu(idPadre,request);
		
		request.getSession().setAttribute(SESSION_1LIV,  new ArrayList<TAnMenu>());
		//MenuController.menuSinistra1Liv = new ArrayList<TAnMenu>();
		
		//for (int i = 0; i < MenuController.menu.size(); i++) {
		for (int i = 0; i < getMenu(request).size(); i++) {
			if (idPadre == null) {
				continue;
			}
			int codicePadre = Integer.parseInt(idPadre);
			
			//if (MenuController.menu.get(i).getNumePadre() == (codicePadre) && (MenuController.menu.get(i).getNumeLivello().intValue() == 2)) {
			if (getMenu(request).get(i).getNumePadre() == (codicePadre) && (getMenu(request).get(i).getNumeLivello().intValue() == 2)) {
				//MenuController.menuSinistra1Liv.add(MenuController.menu.get(i));
				//getMenuController1Liv(request).add(MenuController.menu.get(i));
				getMenuController1Liv(request).add(getMenu(request).get(i));
			}
		}

		if (isProcedimentoRevoca(request)) {
			removeVociMenuProcedimentoRevocaSequestro(request);
			
			if(isCorteDAppello(request)){
				removeVoceMenuAmministratori(request);
			}
			
		}

		if (isTribunale(request)) {
			removeVoceMenuUdienza(request);
		}
		
		//Segnalazione CFPG04, EMS-157
		if (isCorteDAppello(request) && isProcedimentoOpposizioneRestituzione(request)) {
			removeVociMenuProcedimentoRevocaSequestro(request);
		}	
				
		//request.getSession().setAttribute("menuSinistra1Liv", MenuController.menuSinistra1Liv);
		request.getSession().setAttribute("menuSinistra1Liv", getMenuController1Liv(request));
		
	}

	private static void removeVoceMenuAmministratori(HttpServletRequest request) {
		//for (int k = 0; k < MenuController.menuSinistra1Liv.size(); k++) {
		for (int k = 0; k < getMenuController1Liv(request).size(); k++) {
			//int codiKIdMenu = MenuController.menuSinistra1Liv.get(k).getCodiKIdMenu();
			int codiKIdMenu = getMenuController1Liv(request).get(k).getCodiKIdMenu();
			if (codiKIdMenu == VociMenu.AMMINISTRATORI_MENU_PROCEDIMENTO) {
				//MenuController.menuSinistra1Liv.remove(MenuController.menuSinistra1Liv.get(k));
				getMenuController1Liv(request).remove(getMenuController1Liv(request).get(k));
			}
		}
	}
	
	private static void removeVoceMenuUdienza(HttpServletRequest request) {
		//for (int k = 0; k < MenuController.menuSinistra1Liv.size(); k++) {
		for (int k = 0; k < getMenuController1Liv(request).size(); k++) {
			//int codiKIdMenu = MenuController.menuSinistra1Liv.get(k).getCodiKIdMenu();
			int codiKIdMenu = getMenuController1Liv(request).get(k).getCodiKIdMenu();
			if (codiKIdMenu == VociMenu.UDIENZA) {
				//MenuController.menuSinistra1Liv.remove(MenuController.menuSinistra1Liv.get(k));
				getMenuController1Liv(request).remove(getMenuController1Liv(request).get(k));
			}
		}
	}

	private static boolean isTribunale(HttpServletRequest request) {
		return (Integer) request.getSession().getAttribute("idTipoUfficio") == 19;
	}
	
	private static boolean isProcuraGenerale(HttpServletRequest request) {
		Object attribute = request.getSession().getAttribute("idTipoUfficio");
		return attribute != null && (Integer) attribute == Constants.TIPO_UFFICIO_PROCURA_GENERALE;
	}

	private static void caricaMenuSinistra1Liv(String idPadre, HttpServletRequest request, boolean cameralini) {
		if(request.getSession().getAttribute(Constants.ID_PROCEDIMENTO)!=null) {
			ProcedimentoForm procedimento = (ProcedimentoForm)request.getSession().getAttribute(Constants.ID_PROCEDIMENTO);
			procedimento.getTipoProcedimento().getId();
		}
		
		//MenuController.menuSinistra1Liv = new ArrayList<TAnMenu>();
		request.getSession().setAttribute(SESSION_1LIV,  new ArrayList<TAnMenu>());
		//for (int i = 0; i < MenuController.menu.size(); i++) {
		for (int i = 0; i < getMenu(request).size(); i++) {
			if (idPadre == null) {
				continue;
			}
			int codicePadre = Integer.parseInt(idPadre);
			
			
			//if (MenuController.menu.get(i).getNumePadre() == (codicePadre) && (MenuController.menu.get(i).getNumeLivello().intValue() == 2)
			//		&& (MenuController.menu.get(i).getCodiKIdMenu() != 104 
			//		&& MenuController.menu.get(i).getCodiKIdMenu() != 105 
			//		&& MenuController.menu.get(i).getCodiKIdMenu() != 87 
			//		&& MenuController.menu.get(i).getCodiKIdMenu() != 88 
			//		&& MenuController.menu.get(i).getCodiKIdMenu() != 107))
			if (getMenu(request).get(i).getNumePadre() == (codicePadre) && (getMenu(request).get(i).getNumeLivello().intValue() == 2)
				&& (getMenu(request).get(i).getCodiKIdMenu() != 104 
				&& getMenu(request).get(i).getCodiKIdMenu() != 105 
				&& getMenu(request).get(i).getCodiKIdMenu() != 87 
				&& getMenu(request).get(i).getCodiKIdMenu() != 88 
				&& getMenu(request).get(i).getCodiKIdMenu() != 107))
		
				{
				ArrayList<TAnMenu> menu = getMenu(request);
				if (!cameralini){
					//MenuController.menuSinistra1Liv.add(MenuController.menu.get(i));
					//getMenuController1Liv(request).add(MenuController.menu.get(i));
					getMenuController1Liv(request).add(getMenu(request).get(i));
				}
				else if ( 
						menu.get(i).getCodiKIdMenu() != 98 
						&&  
						( 
							menu.get(i).getCodiKIdMenu() != 35 || 
							( 
								menu.get(i).getCodiKIdMenu() == 35 && 
								(
									request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).equals(""+Constants.TIPOPROC_MODIFICA_PATRIMONIALE_CAM) 
									|| 
									request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).equals(""+Constants.TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM)
									|| request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).equals("" + Constants.TIPOPROC_TERZO_INTERVENIENTE_CAM)
								)
							)
						) 
						&& menu.get(i).getCodiKIdMenu() != 50
//						&& 
//						menu.get(i).getCodiKIdMenu() != 21 
						&& 
						menu.get(i).getCodiKIdMenu() != 71 
				) {
					//MenuController.menuSinistra1Liv.add(MenuController.menu.get(i));
					//getMenuController1Liv(request).add(MenuController.menu.get(i));
					getMenuController1Liv(request).add(getMenu(request).get(i));
				}
			}
		}
		
//		Segnalazione EMS-85
		if(isFascicoloCameraConsiglio(request)){
			if(isProcura(request) || isProcuraGenerale(request))
				removeVoceElencoBeni(request);
		}
		
		//request.getSession().setAttribute("menuSinistra1Liv", MenuController.menuSinistra1Liv);
		request.getSession().setAttribute("menuSinistra1Liv", getMenuController1Liv(request));
	}

	
	private static void caricaMenuSinistra2Liv(String idPadre, HttpServletRequest request, boolean cameralini) {
		
		int codicePadre = Integer.parseInt(idPadre);
		
		ProfiloForm profilo=(ProfiloForm) request.getSession().getAttribute("profilo");
		String tipoUtente = (String) request.getSession().getAttribute(Constants.TIPO_UTENTE);
		
		if (profilo.getRuolo().trim().equals("MAGISTRATO")){
			request.getSession().setAttribute("disabledViewMenu","all");
		    request.getSession().setAttribute("disabledViewProc", "true");
		}
		//MenuController.menuSinistra2Liv = new ArrayList<TAnMenu>();
		request.getSession().setAttribute(SESSION_2LIV,  new ArrayList<TAnMenu>());
		ArrayList<TAnMenu> exStaticMenu = getMenu(request);
		
		//for (int i = 0; i < MenuController.menuSinistra1Liv.size(); i++) {
		for (int i = 0; i < getMenuController1Liv(request).size(); i++) {
			//for (int j = 0; j < MenuController.menu.size(); j++) {
			for (int j = 0; j < getMenu(request).size(); j++) {

				//if (MenuController.menu.get(j).getNumePadre() == (MenuController.menuSinistra1Liv.get(i).getCodiKIdMenu())
				//if (MenuController.menu.get(j).getNumePadre() == (getMenuController1Liv(request).get(i).getCodiKIdMenu())
				//		&& (MenuController.menu.get(j).getNumeLivello().intValue() == 3)) {
				if (MenuController.getMenu(request).get(j).getNumePadre() == (getMenuController1Liv(request).get(i).getCodiKIdMenu())
						&& (MenuController.getMenu(request).get(j).getNumeLivello().intValue() == 3)) {

					//se cameralini elimino la voce "Soggetti Duplicati" e "Nuovo soggetto fisico" e "Nuovo soggetto giuridico"
//					if(cameralini && (menu.get(j).getCodiKIdMenu() == 91 || (tipoUtente.equals(Constants.TIPO_UTENTE_SMP) && (menu.get(j).getCodiKIdMenu() == 60 || menu.get(j).getCodiKIdMenu() == 61 || menu.get(j).getCodiKIdMenu() == 79 || menu.get(j).getCodiKIdMenu() == 80)))) {
//						int codiMenu = MenuController.menu.get(j).getCodiKIdMenu();
//					} else if(cameralini && 
					if(cameralini &&
										(request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).equals(""+Constants.TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM) 
										||
										(request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).equals(""+Constants.TIPOPROC_TERZO_INTERVENIENTE_CAM))
										||
										(request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).equals(""+Constants.TIPOPROC_CORREZIONE_ERRORI_CAM))
										||
										(request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).equals(""+Constants.TIPOPROC_MODIFICA_PATRIMONIALE_CAM))
										||
										(request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).equals(""+Constants.TIPOPROC_MODIFICA_PERSONALE_CAM))
										||
										(request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).equals(""+Constants.TIPOPROC_CAUZIONI_CAM))
										||
										(request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).equals(""+Constants.TIPOPROC_PERMESSI_CAM))
										)
											&& (
													(tipoUtente.equals(Constants.TIPO_UTENTE_SMP_GIU) &&
															//(menu.get(j).getCodiKIdMenu() == 82 || menu.get(j).getCodiKIdMenu() == 96 || menu.get(j).getCodiKIdMenu() == 94 || menu.get(j).getCodiKIdMenu() == 189 || menu.get(j).getCodiKIdMenu() == 40))
															(getMenu(request).get(j).getCodiKIdMenu() == 82 || getMenu(request).get(j).getCodiKIdMenu() == 96 || getMenu(request).get(j).getCodiKIdMenu() == 94 || getMenu(request).get(j).getCodiKIdMenu() == 189 || getMenu(request).get(j).getCodiKIdMenu() == 40))
															
															||
													(tipoUtente.equals(Constants.TIPO_UTENTE_SMP) &&
															(getMenu(request).get(j).getCodiKIdMenu() == 94 || getMenu(request).get(j).getCodiKIdMenu() == 25))
												)
					) {
						
						//int codiMenu = MenuController.menu.get(j).getCodiKIdMenu();
						int codiMenu = MenuController.getMenu(request).get(j).getCodiKIdMenu();
						
						if ( 
								(tipoUtente.equals(Constants.TIPO_UTENTE_SMP_GIU) &&
									(
										(96 == codiMenu && 
											(
												request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).equals(""+Constants.TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM) ||
												request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).equals(""+Constants.TIPOPROC_CORREZIONE_ERRORI_CAM) ||
												request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).equals(""+Constants.TIPOPROC_TERZO_INTERVENIENTE_CAM)
											) 
										)
										|| 94 == codiMenu 
										|| (189 == codiMenu && !(request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).equals(""+Constants.TIPOPROC_TERZO_INTERVENIENTE_CAM)))  	
										|| 
										(82 == codiMenu &&
											!(request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).equals(""+Constants.TIPOPROC_MODIFICA_PATRIMONIALE_CAM) || 
												request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).equals(""+Constants.TIPOPROC_TERZO_INTERVENIENTE_CAM)
											 )
										)
									)
								) ||
								(tipoUtente.equals(Constants.TIPO_UTENTE_SMP) &&
										(
											(94 == codiMenu &&
												!(request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).equals(""+Constants.TIPOPROC_MODIFICA_PATRIMONIALE_CAM) || 
													request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).equals(""+Constants.TIPOPROC_TERZO_INTERVENIENTE_CAM)
												 )
											)
										)
								) 								
						) {
							
							//MenuController.menuSinistra2Liv.remove(menu.get(j));
							//getMenuController2Liv(request).remove(menu.get(j));
							getMenuController2Liv(request).remove(getMenu(request).get(j));
							
							continue;
						}
						
						if( ( (40 == codiMenu && tipoUtente.equals(Constants.TIPO_UTENTE_SMP_GIU)) || 25 == codiMenu && tipoUtente.equals(Constants.TIPO_UTENTE_SMP)) && request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).equals(""+Constants.TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM)  ) {
							//TAnMenu menu = MenuController.menu.get(j);
							TAnMenu menu = MenuController.getMenu(request).get(j);
							TAnMenu menuN = new TAnMenu(menu.getCodiKIdMenu(), menu.getNumePadre(), "Amministratore", menu.getDescPercorso(), menu.getNumeLivello(), menu.getDescGruppo(), menu.getNumeOrdineVisual(), menu.getDescUtente(), menu.getDataUltAgg(), menu.getTAnMenuProfilos()); 
							
							//MenuController.menuSinistra2Liv.remove(menu);
							//MenuController.menuSinistra2Liv.add(menuN);
							
							getMenuController2Liv(request).remove(menu);
							getMenuController2Liv(request).add(menuN);
							
							
							continue;
						} else if( 189 == codiMenu && request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).equals(""+Constants.TIPOPROC_TERZO_INTERVENIENTE_CAM)  ) {
							//TAnMenu menu = MenuController.menu.get(j);
							TAnMenu menu = MenuController.getMenu(request).get(j);
							
							//MenuController.menuSinistra2Liv.remove(menu);
							getMenuController2Liv(request).remove(menu);
							
							continue;
						}
						
						//MenuController.menuSinistra2Liv.add(MenuController.menu.get(j));
						//getMenuController2Liv(request).add(MenuController.menu.get(j));
						getMenuController2Liv(request).add(MenuController.getMenu(request).get(j));

					} else {
						// ulteriore controllo per la gestione della visualizzazione delle pagine
						if ((request.getSession().getAttribute("disabledViewMenu") == null))
							//MenuController.menuSinistra2Liv.add(MenuController.menu.get(j));
							//getMenuController2Liv(request).add(MenuController.menu.get(j));
							getMenuController2Liv(request).add(MenuController.getMenu(request).get(j));
						else if ((request.getSession().getAttribute("disabledViewMenu").equals("all"))
								&&
								// TIPO_UFFICIO_PROCURA
								(exStaticMenu.get(j)
										.getCodiKIdMenu() != Constants.ID_K_MENU_NUOVO_BENE_PROCURA
										&& exStaticMenu.get(j).getCodiKIdMenu() != Constants.ID_K_MENU_NUOVO_SOGGETTO_FISICO_PROCURA
										&& exStaticMenu.get(j).getCodiKIdMenu() != Constants.ID_K_MENU_NUOVO_SOGGETTO_GIURIDICO_PROCURA
										&&
										
										// TIPO_UFFICIO_TRIBUNALE
										exStaticMenu.get(j).getCodiKIdMenu() != Constants.ID_K_MENU_NUOVO_SOGGETTO_FISICO_TRIBUNALE
										&& exStaticMenu.get(j).getCodiKIdMenu() != Constants.ID_K_MENU_NUOVO_SOGGETTO_GIURIDICO_TRIBUNALE && exStaticMenu
										.get(j).getCodiKIdMenu() != Constants.ID_K_MENU_NUOVO_BENE_TRIBUNALE ))
						
							//MenuController.menuSinistra2Liv.add(MenuController.menu.get(j));
							getMenuController2Liv(request).add(exStaticMenu.get(j));
						
						else if ((request.getSession().getAttribute("disabledViewMenu").equals("notAll_Procura_noBene")) &&
						// TIPO_UFFICIO_PROCURA
								(exStaticMenu.get(j).getCodiKIdMenu() != Constants.ID_K_MENU_NUOVO_BENE_PROCURA))
							//MenuController.menuSinistra2Liv.add(MenuController.menu.get(j));
							getMenuController2Liv(request).add(exStaticMenu.get(j));
						else if ((request.getSession().getAttribute("disabledViewMenu").equals("notAll_Procura")) &&
						// TIPO_UFFICIO_PROCURA
								(exStaticMenu.get(j).getCodiKIdMenu() != Constants.ID_K_MENU_NUOVO_SOGGETTO_FISICO_PROCURA
										&& exStaticMenu.get(j).getCodiKIdMenu() != Constants.ID_K_MENU_NUOVO_SOGGETTO_GIURIDICO_PROCURA && exStaticMenu
										.get(j).getCodiKIdMenu() != Constants.ID_K_MENU_NUOVO_BENE_PROCURA))
							//MenuController.menuSinistra2Liv.add(MenuController.menu.get(j));
							getMenuController2Liv(request).add(exStaticMenu.get(j));
						else if ((request.getSession().getAttribute("disabledViewMenu").equals("notAll_Tribunale")) &&
						// TIPO_UFFICIO_TRIBUNALE
								(exStaticMenu.get(j).getCodiKIdMenu() != Constants.ID_K_MENU_NUOVO_SOGGETTO_FISICO_TRIBUNALE 
										&& exStaticMenu.get(j).getCodiKIdMenu() != Constants.ID_K_MENU_NUOVO_SOGGETTO_GIURIDICO_TRIBUNALE && exStaticMenu
										.get(j).getCodiKIdMenu() != Constants.ID_K_MENU_NUOVO_BENE_TRIBUNALE))
							//MenuController.menuSinistra2Liv.add(MenuController.menu.get(j));
							getMenuController2Liv(request).add(exStaticMenu.get(j));
						else if ((request.getSession().getAttribute("disabledViewMenu").equals("atto_Accertamento")) &&
						// TIPO_PROCEDIMENTO ACCERTAMENTO
								(exStaticMenu.get(j).getCodiKIdMenu() != Constants.ID_K_MENU_ATTO_BENI && exStaticMenu.get(j)
										.getCodiKIdMenu() != Constants.ID_K_MENU_ATTO_COLLEGIO))
							//MenuController.menuSinistra2Liv.add(MenuController.menu.get(j));
							getMenuController2Liv(request).add(exStaticMenu.get(j));
						else if ((request.getSession().getAttribute("disabledViewMenu").equals("atto_DecisorioIGrado")) &&
						// TIPO_PROCEDIMENTO DECISORIO I GRADO
								(exStaticMenu.get(j).getCodiKIdMenu() != Constants.ID_K_MENU_ATTO_COLLEGIO && exStaticMenu.get(j)
										.getCodiKIdMenu() != Constants.ID_K_MENU_UDIENZE_DECRETO_SEQUESTRO_CONVALIDA))
							//MenuController.menuSinistra2Liv.add(MenuController.menu.get(j));
							getMenuController2Liv(request).add(exStaticMenu.get(j));
						else if ((request.getSession().getAttribute("disabledViewMenu").equals("atto_decretiUdienza")) &&
						// TIPO_PROCEDIMENTO ACCERTAMENTO
								(exStaticMenu.get(j).getCodiKIdMenu() != Constants.ID_K_MENU_ATTO_SOGGETTI_TRIBUNALE
										&& exStaticMenu.get(j).getCodiKIdMenu() != Constants.ID_K_MENU_ATTO_BENI_TRIBUNALE &&  exStaticMenu
										.get(j).getCodiKIdMenu() != Constants.ID_K_MENU_UDIENZE_DECRETO_SEQUESTRO_CONVALIDA &&
												exStaticMenu.get(j).getCodiKIdMenu() != Constants.ID_K_MENU_ATTO_AMMINISTRATORI
										))
							//MenuController.menuSinistra2Liv.add(MenuController.menu.get(j));
							getMenuController2Liv(request).add(exStaticMenu.get(j));
						else if ((request.getSession().getAttribute("disabledViewMenu").equals("atto_decretiConvalida")) &&
						// TIPO_PROCEDIMENTO ACCERTAMENTO
								(//MenuController.menu.get(j).getCodiKIdMenu() != Constants.ID_K_MENU_ATTO_SOGGETTI_TRIBUNALE &&
										exStaticMenu.get(j)
										.getCodiKIdMenu() != Constants.ID_K_MENU_ATTO_BENI_NUOVO_TRIBUNALE))
							//MenuController.menuSinistra2Liv.add(MenuController.menu.get(j));
							getMenuController2Liv(request).add(exStaticMenu.get(j));
						else if ((request.getSession().getAttribute("disabledViewMenu").equals("atto_decretiSequestro")))
							//MenuController.menuSinistra2Liv.add(MenuController.menu.get(j));
							getMenuController2Liv(request).add(exStaticMenu.get(j));
						else if ((request.getSession().getAttribute("disabledViewMenu").equals("atto_decretiAssRem_ruolo"))
								&& (exStaticMenu.get(j).getCodiKIdMenu() != Constants.ID_K_MENU_ATTO_BENI_TRIBUNALE && exStaticMenu.get(j)
										.getCodiKIdMenu() != Constants.ID_K_MENU_UDIENZE_DECRETO_SEQUESTRO_CONVALIDA))
							//MenuController.menuSinistra2Liv.add(MenuController.menu.get(j));
							getMenuController2Liv(request).add(exStaticMenu.get(j));
					}
				}
			}
		}

		// Simone: tolgo delle voci di menù se lo stato del procedimento è annullato
		// PROCURA
		if ((Integer) request.getSession().getAttribute("idTipoUfficio") == 17) {
			// PREVENZIONE
			if 		(request.getSession().getAttribute("statoProcedimento") != null 
					&& ( request.getSession().getAttribute("statoProcedimento").equals("19") ||
						 request.getSession().getAttribute("statoProcedimento").equals("14")||
						 request.getSession().getAttribute("statoProcedimento").equals("24")
					   )	
					&& idPadre.equals("14")) {

				for (int k = 0; k < exStaticMenu.size(); k++) {

					if (exStaticMenu.get(k).getCodiKIdMenu() == Constants.ID_K_MENU_NUOVO_SOGGETTO_FISICO_PROCURA
							|| exStaticMenu.get(k).getCodiKIdMenu() == Constants.ID_K_MENU_NUOVO_SOGGETTO_GIURIDICO_PROCURA
							|| exStaticMenu.get(k).getCodiKIdMenu() == Constants.ID_K_MENU_NUOVO_BENE_PROCURA
							|| exStaticMenu.get(k).getCodiKIdMenu() == Constants.ID_K_MENU_NUOVO_ATTO_PROCURA) {
						exStaticMenu.remove(exStaticMenu.get(k));

					}
				}
			}
			
			// ACCERTAMENTO
			if (request.getSession().getAttribute("statoProcedimento") != null 
					&& ( request.getSession().getAttribute("statoProcedimento").equals("19") ||
							 request.getSession().getAttribute("statoProcedimento").equals("14")||
							 request.getSession().getAttribute("statoProcedimento").equals("24")
						)
					&& idPadre.equals("17")) {

				for (int k = 0; k < exStaticMenu.size(); k++) {

					if (exStaticMenu.get(k).getCodiKIdMenu() == 79 || exStaticMenu.get(k).getCodiKIdMenu() == 80
							|| exStaticMenu.get(k).getCodiKIdMenu() == Constants.ID_K_MENU_NUOVO_BENE_PROCURA
							|| exStaticMenu.get(k).getCodiKIdMenu() == Constants.ID_K_MENU_NUOVO_ATTO_PROCURA) {
						exStaticMenu.remove(exStaticMenu.get(k));

					}
				}
			}
			// AGGRAVAMENTO
			if (	request.getSession().getAttribute("statoProcedimento") != null 
					&& ( request.getSession().getAttribute("statoProcedimento").equals("19") ||
							 request.getSession().getAttribute("statoProcedimento").equals("14")||
							 request.getSession().getAttribute("statoProcedimento").equals("24")
						)
					&& idPadre.equals("45")) {

				for (int k = 0; k < exStaticMenu.size(); k++) {

					if (exStaticMenu.get(k).getCodiKIdMenu() == Constants.ID_K_MENU_NUOVO_ATTO_AGGRAVAMENTO_PROCURA
							|| exStaticMenu.get(k).getCodiKIdMenu() == 80
							|| exStaticMenu.get(k).getCodiKIdMenu() == Constants.ID_K_MENU_NUOVO_BENE_PROCURA
							|| exStaticMenu.get(k).getCodiKIdMenu() == Constants.ID_K_MENU_NUOVO_ATTO_PROCURA) {
						exStaticMenu.remove(exStaticMenu.get(k));
					}
				}
			}
		}

		// TRIBUNALE E CORTE D'APPELLO

		if ((Integer) request.getSession().getAttribute("idTipoUfficio") == 19 || (Integer) request.getSession().getAttribute("idTipoUfficio") == 6) {

			// PREVENZIONE
			if (	request.getSession().getAttribute("statoProcedimento") != null 
					&& ( request.getSession().getAttribute("statoProcedimento").equals("19") ||
							 request.getSession().getAttribute("statoProcedimento").equals("14")||
							 request.getSession().getAttribute("statoProcedimento").equals("24")
						)
					&& idPadre.equals("14")) {
				for (int k = 0; k < exStaticMenu.size(); k++) {

					if (exStaticMenu.get(k).getCodiKIdMenu() == Constants.ID_K_MENU_SOGGETTI_DUPLICATI
/* /DG Mev Marzo-Giugno Prog 20 - MEV AMM - CDA Napoli - atti in archivio, voce menu nuovo ripristinata						
|| exStaticMenu.get(k).getCodiKIdMenu() == Constants.ID_K_MENU_NUOVO_ATTO_TRIBUNALE */
							|| exStaticMenu.get(k).getCodiKIdMenu() == Constants.ID_K_MENU_ATTO_UDIENZE_PROCURA) {
						exStaticMenu.remove(exStaticMenu.get(k));

					}
				}
			}
			// AGGRAVAMENTO
			if (	request.getSession().getAttribute("statoProcedimento") != null 
					&& ( request.getSession().getAttribute("statoProcedimento").equals("19") ||
							 request.getSession().getAttribute("statoProcedimento").equals("14")||
							 request.getSession().getAttribute("statoProcedimento").equals("24")
						)
					&& idPadre.equals("45")) {
				for (int k = 0; k < exStaticMenu.size(); k++) {

					if (exStaticMenu.get(k).getCodiKIdMenu() == 74 ||exStaticMenu.get(k).getCodiKIdMenu() == 96
							|| exStaticMenu.get(k).getCodiKIdMenu() == Constants.ID_K_MENU_ATTO_UDIENZE_PROCURA) {
						exStaticMenu.remove(exStaticMenu.get(k));

					}
				}
			}
			// AGGRAVAMENTO
			if (	request.getSession().getAttribute("statoProcedimento") != null 
					&& ( request.getSession().getAttribute("statoProcedimento").equals("19") ||
							 request.getSession().getAttribute("statoProcedimento").equals("14")||
							 request.getSession().getAttribute("statoProcedimento").equals("24")
						)
					&& idPadre.equals("45")) {
				for (int k = 0; k < exStaticMenu.size(); k++) {

					if (exStaticMenu.get(k).getCodiKIdMenu() == 74 || exStaticMenu.get(k).getCodiKIdMenu() == 96
							|| exStaticMenu.get(k).getCodiKIdMenu() == Constants.ID_K_MENU_ATTO_UDIENZE_PROCURA) {
						exStaticMenu.remove(exStaticMenu.get(k));

					}
				}

			}

			// OPP DIVIETI QUESTORE
			if (	request.getSession().getAttribute("statoProcedimento") != null 
					&& ( request.getSession().getAttribute("statoProcedimento").equals("19") ||
							 request.getSession().getAttribute("statoProcedimento").equals("14")||
							 request.getSession().getAttribute("statoProcedimento").equals("24")
						)
					&& idPadre.equals("16")) {
				for (int k = 0; k < exStaticMenu.size(); k++) {

					if (exStaticMenu.get(k).getCodiKIdMenu() == 64 || exStaticMenu.get(k).getCodiKIdMenu() == 96
							|| exStaticMenu.get(k).getCodiKIdMenu() == Constants.ID_K_MENU_ATTO_UDIENZE_PROCURA) {
						exStaticMenu.remove(exStaticMenu.get(k));

					}
				}

			}

		}

		String idCategoriaAtto = RequestUtil.recuperaIdCategoriaAtto(request) + "";

		// Andrea (AS): elimina le voci di menu non utilizzabili per i decreti di
		// rigetto totale del sequestro e allo stato della richiesta di sequesto
		if (ComparatorUtil.containsValue(idCategoriaAtto, Constants.DECRETO_RIGETTO_TOTALE_SEQUESTRO,
				Constants.DECRETO_RIGETTO_STATO_RICHIESTA_SEQUESTRO, Constants.DECRETO_PROROGA_SEQUESTRO, Constants.DECRETO_PROROGA_SOSPENSIONE_CDA)) {
			//ArrayList<TAnMenu> myMeny = MenuController.menuSinistra2Liv;
			ArrayList<TAnMenu> myMeny = getMenuController2Liv(request);
			
			for (int k = 0; k < myMeny.size(); k++) {
				TAnMenu voceMenu = myMeny.get(k);
				int codiMenu = voceMenu.getCodiKIdMenu();
				if (codiMenu == Constants.ID_K_MENU_ATTO_AMMINISTRATORI) {
					myMeny.remove(voceMenu);
				}
			}
		}

		// GIUDICE DELEGATO e AMMINISTRATORI: elimina la voce :
		// 1. se è Procura Generale
		// 2. se il procedimento non è PATRIMONIALE o PERSONALE E PATRIMONIALE
		// 3. se l'atto non è un DECRETO di SEQUESTRO O UN DECISORIO di I o di II grado
		// 4. se il procedimento è di tipo AGGRAVAMENTO e l'atto è di tipo DECISORIO di I grado
		if ((Integer) request.getSession().getAttribute("idTipoUfficio") == Constants.TIPO_UFFICIO_PROCURA_GENERALE) {
			//for (int k = 0; k < MenuController.menuSinistra2Liv.size(); k++) {
			for (int k = 0; k < getMenuController2Liv(request).size(); k++) {
				//if (MenuController.menuSinistra2Liv.get(k).getCodiKIdMenu() == 53 || MenuController.menuSinistra2Liv.get(k).getCodiKIdMenu() == 54) {
				//	MenuController.menuSinistra2Liv.remove(MenuController.menuSinistra2Liv.get(k));
				if (getMenuController2Liv(request).get(k).getCodiKIdMenu() == 53 || getMenuController2Liv(request).get(k).getCodiKIdMenu() == 54) {
					getMenuController2Liv(request).remove(getMenuController2Liv(request).get(k));
				}
			}
		}
		if (request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO) != null
				&& (!request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).equals(Constants.TIPOPROC_PATRIMONIALE) && !request
						.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).equals(Constants.TIPOPROC_PERSONALE_E_PATRIMONIALE) &&
							!request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).equals(Constants.TIPOPROC_GESTIONE_BENI))) {
			//for (int k = 0; k < MenuController.menuSinistra2Liv.size(); k++) {
			//	if (MenuController.menuSinistra2Liv.get(k).getCodiKIdMenu() == 53 
			//			|| MenuController.menuSinistra2Liv.get(k).getCodiKIdMenu() == 54) {
			//		MenuController.menuSinistra2Liv.remove(MenuController.menuSinistra2Liv.get(k));
			for (int k = 0; k < getMenuController2Liv(request).size(); k++) {
				if (getMenuController2Liv(request).get(k).getCodiKIdMenu() == 53 
						|| getMenuController2Liv(request).get(k).getCodiKIdMenu() == 54) {
					getMenuController2Liv(request).remove(getMenuController2Liv(request).get(k));
					
					
				}
			}
		}
		if (request.getSession().getAttribute(Constants.ATTO) != null) {
			AttoForm attoForm1 = ((AttoForm) request.getSession().getAttribute(Constants.ATTO));
			if (attoForm1.getCategoriaAtto() != null && !attoForm1.getCategoriaAtto().getId().equals(Constants.DECRETO_SEQUESTRO_ANTICIPATO)
					&& !attoForm1.getCategoriaAtto().getId().equals(Constants.DECRETO_SEQUESTRO_URGENTE)
					&& !attoForm1.getCategoriaAtto().getId().equals(Constants.DECRETO_SEQUESTRO_ORDINARIO)
					&& !attoForm1.getCategoriaAtto().getId().equals(Constants.DECRETO_SEQUESTRO_BENI)
					&& !attoForm1.getCategoriaAtto().getId().equals(Constants.DECRETO_SOSPENSIONE_TEMP_TRIB)
					&& !attoForm1.getCategoriaAtto().getId().equals(Constants.DECRETO_SOSPENSIONE_TEMP_CDA)
					&& !attoForm1.getCategoriaAtto().getId().equals(Constants.DECRETO_SEQUESTRO_SOSPENSIONE_TEMPORANEA)
					&& !attoForm1.getCategoriaAtto().getId().equals(Constants.DECRETO_SEQUESTRO_ORDINARIO_II_GRADO)
					&& !attoForm1.getCategoriaAtto().getId().equals(Constants.DECRETO_DECISORIO_I_GRADO)
					&& !attoForm1.getCategoriaAtto().getId().equals(Constants.DECRETO_DECISORIO_II_GRADO)
					&& !attoForm1.getCategoriaAtto().getId().equals(Constants.DECRETO_REVOCA_SEQUESTRO)) {
				//for (int k = 0; k < MenuController.menuSinistra2Liv.size(); k++) {
				//	if (MenuController.menuSinistra2Liv.get(k).getCodiKIdMenu() == 53
				//			|| MenuController.menuSinistra2Liv.get(k).getCodiKIdMenu() == 54) {
				//		MenuController.menuSinistra2Liv.remove(MenuController.menuSinistra2Liv.get(k));
				for (int k = 0; k < getMenuController2Liv(request).size(); k++) {
					if (getMenuController2Liv(request).get(k).getCodiKIdMenu() == 53
							|| getMenuController2Liv(request).get(k).getCodiKIdMenu() == 54) {
						getMenuController2Liv(request).remove(getMenuController2Liv(request).get(k));
					}
				}
			} 
		}
		if(request.getSession().getAttribute(Constants.ATTO) != null
				&& request.getSession().getAttribute(Constants.TIPO_FASCICOLO) != null
				&& request.getSession().getAttribute(Constants.TIPO_FASCICOLO).equals(Constants.TIPO_FASCICOLO_AGGRAVAMENTO)){
			AttoForm attoForm1 = ((AttoForm) request.getSession().getAttribute(Constants.ATTO));		
			if(attoForm1.getCategoriaAtto() != null
				&& attoForm1.getCategoriaAtto().getId().equals(Constants.DECRETO_DECISORIO_I_GRADO)) {
				//for (int k = 0; k < MenuController.menuSinistra2Liv.size(); k++) {
				//	if (MenuController.menuSinistra2Liv.get(k).getCodiKIdMenu() == 54) {
				//		MenuController.menuSinistra2Liv.remove(MenuController.menuSinistra2Liv.get(k));
				for (int k = 0; k < getMenuController2Liv(request).size(); k++) {
					if (getMenuController2Liv(request).get(k).getCodiKIdMenu() == 54) {
						getMenuController2Liv(request).remove(getMenuController2Liv(request).get(k));
					}
				}
			}
										
		}
		// FINE GIUDICE DELEGATO e AMMINISTRATORI

		// FUG Elimina la voce di menu Comunicazioni Equitalia nel caso di fascicolo di Misura personale e Aggravamento
		if (request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO) != null
				&& (request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).equals(Constants.TIPOPROC_PERSONALE) 
						|| request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).equals(Constants.TIPO_FASCICOLO_AGGRAVAMENTO)
						|| request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).equals("" + Constants.TIPOPROC_MODIFICA_PATRIMONIALE_CAM))) {
		
			//for (int k = 0; k < MenuController.menuSinistra2Liv.size(); k++) {
			//	if (MenuController.menuSinistra2Liv.get(k).getCodiKIdMenu() == 98) {
			//		MenuController.menuSinistra2Liv.remove(MenuController.menuSinistra2Liv.get(k));
			for (int k = 0; k < getMenuController2Liv(request).size(); k++) {
				if (getMenuController2Liv(request).get(k).getCodiKIdMenu() == 98) {
					getMenuController2Liv(request).remove(getMenuController2Liv(request).get(k));
					break;
				}
			}
		}

		// Elimina la voce di menu Atto-Bene nel caso di fascicolo di Aggravamento
		//String tipoProc=request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).toString();
		
		if (request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO) != null
				&& (request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).toString().equals(Constants.TIPOPROC_ACCERTAMENTO) ||
						request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).toString()
						.equals("" + Constants.TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM))) {
			if (codicePadre == 15 || codicePadre == 14) {
				if (tipoUtente.equals(Constants.TIPO_UTENTE_SMP_GIU)) {
					//for (int k = 0; k < MenuController.menuSinistra2Liv.size(); k++) {
					//	if (MenuController.menuSinistra2Liv.get(k).getCodiKIdMenu() == 50) {
					//		MenuController.menuSinistra2Liv.remove(MenuController.menuSinistra2Liv.get(k));
					for (int k = 0; k < getMenuController2Liv(request).size(); k++) {
						if (getMenuController2Liv(request).get(k).getCodiKIdMenu() == 50) {
							getMenuController2Liv(request).remove(getMenuController2Liv(request).get(k));
							break;
						}
					}
				} else if (tipoUtente.equals(Constants.TIPO_UTENTE_SMP)) {
					//for (int k = 0; k < MenuController.menuSinistra2Liv.size(); k++) {
					//	if (MenuController.menuSinistra2Liv.get(k).getCodiKIdMenu() == 71) {
					//		MenuController.menuSinistra2Liv.remove(MenuController.menuSinistra2Liv.get(k));
					for (int k = 0; k < getMenuController2Liv(request).size(); k++) {
						if (getMenuController2Liv(request).get(k).getCodiKIdMenu() == 71) {
							getMenuController2Liv(request).remove(getMenuController2Liv(request).get(k));
							break;
						}
					}
				}
			}
		}
		// END Elimina la voce di menu Atto-Bene nel caso di fascicolo di Aggravamento

		// Elimina la voce di menu Atto-Udienze nel caso di fascicolo di Accertamento per l'atto di Accertamento Questura
		// Elimina la voce Atto-Nuovo ed Elenco Terzi
		if (request.getSession().getAttribute(Constants.TIPO_FASCICOLO) != null
				&& request.getSession().getAttribute(Constants.TIPO_FASCICOLO).equals(Constants.TIPO_FASCICOLO_ACCERTAMENTO)) {
			//for (int k = 0; k < MenuController.menuSinistra2Liv.size(); k++) {
			//	if (MenuController.menuSinistra2Liv.get(k).getCodiKIdMenu() == Constants.ID_K_MENU_ATTO_UDIENZE_PROCURA) {
			for (int k = 0; k < getMenuController2Liv(request).size(); k++) {
				if (getMenuController2Liv(request).get(k).getCodiKIdMenu() == Constants.ID_K_MENU_ATTO_UDIENZE_PROCURA) {
					if (request.getSession().getAttribute(Constants.ATTO) != null) {
						AttoForm attoForm = ((AttoForm) request.getSession().getAttribute(Constants.ATTO));
						if (attoForm.getCategoriaAtto().getId().equals(Constants.ACCERTAMENTO_QUESTURA)) {
							//MenuController.menuSinistra2Liv.remove(MenuController.menuSinistra2Liv.get(k));
							getMenuController2Liv(request).remove(getMenuController2Liv(request).get(k));
							break;
						}
					}
					// nuovo atto e Elenco Terzi in SMP...
				} else if (tipoUtente.equals(Constants.TIPO_UTENTE_SMP) &&
				// MARCO Elimita la voce 84 per far riapparire il menu nuovo atto
				// (MenuController.menuSinistra2Liv.get(k).getCodiKIdMenu() == 84) || (MenuController.menuSinistra2Liv.get(k).getCodiKIdMenu() == 95))
						
					//	(MenuController.menuSinistra2Liv.get(k).getCodiKIdMenu() == 95)) {
					//MenuController.menuSinistra2Liv.remove(MenuController.menuSinistra2Liv.get(k));
					
					(getMenuController2Liv(request).get(k).getCodiKIdMenu() == 95)) {
					getMenuController2Liv(request).remove(getMenuController2Liv(request).get(k));
				}
			}
		}
		// END Elimina la voce di menu Atto-Udienze nel caso di fascicolo di Accertamento per l'atto di Accertamento Questura
		
		//Fascicolo di Gestione Beni
		if (request.getSession().getAttribute(Constants.TIPO_FASCICOLO) != null
				&& request.getSession().getAttribute(Constants.TIPO_FASCICOLO).equals(Constants.TIPO_FASCICOLO_GESTIONE)) {
			
			request.getSession().setAttribute("disabledViewMenu","all");
		    request.getSession().setAttribute("disabledViewProc", "true");		

			if (tipoUtente.equals(Constants.TIPO_UTENTE_SMP_GIU)) {

				//for (int k = 0; k < MenuController.menuSinistra2Liv.size(); k++) {
				//	TAnMenu voceMenu =  MenuController.menuSinistra2Liv.get(k);
				for (int k = 0; k < getMenuController2Liv(request).size(); k++) {
					TAnMenu voceMenu =  getMenuController2Liv(request).get(k);
					
					int codiMenu = voceMenu.getCodiKIdMenu();
					
					//elimino la voce atti-elenco per tutte le categorie atti
					if (codiMenu == 148) {
						//MenuController.menuSinistra2Liv.remove(voceMenu);
						getMenuController2Liv(request).remove(voceMenu);
						k = k-1;						
					}
					
					//elimino la voce atto-udienza per tutte le categorie atti
					if (codiMenu == 83) {
						//MenuController.menuSinistra2Liv.remove(voceMenu);
						getMenuController2Liv(request).remove(voceMenu);
						k = k-1;						
					}
					
					if (codiMenu == 51) {
						//MenuController.menuSinistra2Liv.remove(voceMenu);
						getMenuController2Liv(request).remove(voceMenu);
						k = k-1;						
					}

				    //se l'atto visualizzato è un provvedimento su istanza delle parti e amministratore, eliminiamo le voci Soggetti e Beni
					if (idCategoriaAtto.equals(Constants.PROVVEDIMENTO_SU_ISTANZA_DELLE_PARTI_E_AMMINISTRATORE) ||
							idCategoriaAtto.equals(Constants.PROVVEDIMENTO_CONTO_GESTIONE)) {
	
							if (codiMenu == 50 || codiMenu == 49 || codiMenu == 83) {
								//MenuController.menuSinistra2Liv.remove(voceMenu);
								getMenuController2Liv(request).remove(voceMenu);
								k = k-1;
							}
					}
					
					if(idCategoriaAtto.equals(Constants.PROVVEDIMENTO_NOMINA_AMMINISTRATORI)) {

						if (codiMenu == 49 || codiMenu == 50 || codiMenu == 83) {
							//MenuController.menuSinistra2Liv.remove(voceMenu);
							getMenuController2Liv(request).remove(voceMenu);
							k = k-1;						
						}
						
					} else if(idCategoriaAtto.equals(Constants.PROVVEDIMENTO_REVOCA_AMMINISTRATORI) || 
							idCategoriaAtto.equals(Constants.PROVVEDIMENTO_RINUNCIA_AMMINISTRATORI)) {

						if (codiMenu == 49 || codiMenu == 50 || codiMenu ==  54 ||codiMenu == 83) {
							//MenuController.menuSinistra2Liv.remove(voceMenu);
							getMenuController2Liv(request).remove(voceMenu);
							k = k-1;						
						}
					}					
				}
			}
		} else if (request.getSession().getAttribute(Constants.TIPO_FASCICOLO) != null
				&& request.getSession().getAttribute(Constants.TIPO_FASCICOLO).equals(Constants.TIPO_FASCICOLO_MERITO) 
				&& tipoUtente.equals(Constants.TIPO_UTENTE_SMP_GIU)) {

			//for (int k = 0; k < MenuController.menuSinistra2Liv.size(); k++) {
			//	TAnMenu voceMenu =  MenuController.menuSinistra2Liv.get(k);
			for (int k = 0; k < getMenuController2Liv(request).size(); k++) {
				TAnMenu voceMenu =  getMenuController2Liv(request).get(k);
			
				
				int codiMenu = voceMenu.getCodiKIdMenu();

				if(idCategoriaAtto.equals(Constants.PROVVEDIMENTO_NOMINA_AMMINISTRATORI)) {

					if (codiMenu == 49 || codiMenu == 50 || codiMenu == 83) {
						//MenuController.menuSinistra2Liv.remove(voceMenu);
						getMenuController2Liv(request).remove(voceMenu);
						k = k-1;						
					}
					
				} else if(idCategoriaAtto.equals(Constants.PROVVEDIMENTO_REVOCA_AMMINISTRATORI) || 
						idCategoriaAtto.equals(Constants.PROVVEDIMENTO_RINUNCIA_AMMINISTRATORI)) {

					if (codiMenu == 49 || codiMenu == 50 || codiMenu ==  54 ||codiMenu == 83) {
						//MenuController.menuSinistra2Liv.remove(voceMenu);
						getMenuController2Liv(request).remove(voceMenu);
						k = k-1;						
					}
				}
			}
		} 

		// elimina la voce Beni del menu ATTO per il decisorio I grado istanza camerale personale
			if (request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO) != null 
				&&	(request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).equals(""+Constants.TIPOPROC_MODIFICA_PERSONALE_CAM)
					 || request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).equals(""+Constants.TIPOPROC_CORREZIONE_ERRORI_CAM))
				&&	ComparatorUtil.containsValue(idCategoriaAtto,Constants.DECRETO_DECISORIO_I_GRADO_CAMERE) ) 
			{
				//for (int k = 0; k < MenuController.menuSinistra2Liv.size(); k++) {
				//	TAnMenu voceMenu = MenuController.menuSinistra2Liv.get(k);
				for (int k = 0; k < getMenuController2Liv(request).size(); k++) {
					TAnMenu voceMenu = getMenuController2Liv(request).get(k);
					int codiMenu = voceMenu.getCodiKIdMenu();
					if (codiMenu == Constants.ID_K_MENU_ATTO_BENI_TRIBUNALE) {
						//MenuController.menuSinistra2Liv.remove(voceMenu);
						getMenuController2Liv(request).remove(voceMenu);
						break;
					}
				}
			}
			
			// elimina la voce del menu UDIENZA per il decisorio I grado istanza camerale patrimoniale
		if (request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO) != null
				&& ((request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).equals("" + Constants.TIPOPROC_MODIFICA_PERSONALE_CAM)
							 || request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).equals(""+Constants.TIPOPROC_CORREZIONE_ERRORI_CAM)
							 || request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).equals(""+Constants.TIPOPROC_TERZO_INTERVENIENTE_CAM))
						&& ComparatorUtil.containsValue(idCategoriaAtto, Constants.DECRETO_DECISORIO_I_GRADO_CAMERE) || request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO)
						.equals("" + Constants.TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM)))
				{
					//for (int k = 0; k < MenuController.menuSinistra2Liv.size(); k++) {
					//	TAnMenu voceMenu = MenuController.menuSinistra2Liv.get(k);

					for (int k = 0; k < getMenuController2Liv(request).size(); k++) {
						TAnMenu voceMenu = getMenuController2Liv(request).get(k);
	
						int codiMenu = voceMenu.getCodiKIdMenu();
						if (codiMenu == Constants.ID_K_MENU_UDIENZE_DECRETO_SEQUESTRO_CONVALIDA) {
							//MenuController.menuSinistra2Liv.remove(voceMenu);
							getMenuController2Liv(request).remove(voceMenu);
							break;
						}
					}

				}

			if( !(request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO)!=null && 
					(request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).equals(""+Constants.TIPOPROC_PATRIMONIALE) || 
							request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).equals(""+Constants.TIPOPROC_PERSONALE_E_PATRIMONIALE) || 
								request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).equals(""+Constants.TIPOPROC_GESTIONE_BENI)) &&		
				request.getSession().getAttribute("statoProcedimento")!=null && 
						(request.getSession().getAttribute("statoProcedimento").equals(Constants.STATOPROC_TRATTAZIONE_I_GRADO) || 
								request.getSession().getAttribute("statoProcedimento").equals(Constants.STATOPROC_TRATTAZIONE_II_GRADO) || 
									request.getSession().getAttribute("statoProcedimento").equals(Constants.STATOPROC_TRATTAZIONE_GESTIONE)) )) {
									
					//for (int k = 0; k < MenuController.menuSinistra2Liv.size(); k++) { 
					//	TAnMenu voceMenu = MenuController.menuSinistra2Liv.get(k);
					for (int k = 0; k < getMenuController2Liv(request).size(); k++) { 
						TAnMenu voceMenu = getMenuController2Liv(request).get(k);
					
						int codiMenu = voceMenu.getCodiKIdMenu();
						if(//codiMenu == Constants.ID_K_MENU_SOGGETTI_ELENCO_INTERVENIENTI ||
								codiMenu == Constants.ID_K_MENU_SOGGETTI_NUOVO_INTERVENIENTE_FISICO ||
									codiMenu == Constants.ID_K_MENU_SOGGETTI_NUOVO_INTERVENIENTE_GIUR) {
							//MenuController.menuSinistra2Liv.remove(voceMenu);
							getMenuController2Liv(request).remove(voceMenu);
							k = k-1;	
						}
						if (request.getSession().getAttribute("statoProcedimento")!=null &&
							request.getSession().getAttribute("statoProcedimento").equals(Constants.STATOPROC_ISCRITTO) &&
							codiMenu == Constants.ID_K_MENU_SOGGETTI_ELENCO_INTERVENIENTI &&
							! isCorteDAppello(request) //Segnalazione SMP-05
							)	{
							getMenuController2Liv(request).remove(voceMenu);
							k = k-1;
						}
					}
			}

		if ((isProcura(request) || isProcuraGenerale(request)) && isCauzioni(request)) {
			removeVoceElencoIntervenienti(request);
		}	
			
	   if(isFascicoloCameraConsiglio(request) || isFascicoloFascgestione(request) || isFascicoloAggravamento(request)){
		   if(isTribunale(request)){
			   removeVoceAttiDellaCda(request);
		   }
	   }
		
		if(isFascicoloCameraConsiglio(request)){
			removeVoceSoggettiDuplicati(request); //Segnalazione EMS-03
			
			//Segnalazione EMS-89, 86, 112, 115 
			if((isProcura(request) || isProcuraGenerale(request)) && (isPermesso(request) || isCorrezioneErroriMateriali(request))){ //&& request.getSession().getAttribute("statoProcedimento").equals(Constants.STATOPROC_IMPUGNATO_CASSAZIONE)){
				removeVoceSoggetti(request);
			}
			//Segnalazione EMS-10
			if(isModRevPersonale(request) || (request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).equals("" + Constants.TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM))){
				removeVoceElencoIntervenienti(request);
			}
			
			if(isTribunale(request) && isPermesso(request)){
				removeVoceInvioRicorsoInCda(request);
				
				if(isDecisorioIGradoCameralino(request)){
					removeVoceAttoBeni(request);
					removeVoceUdienzaMenuAtto(request);
				}
			}
			
			if (isCorteDAppello(request)) {
				removeVoceAttidelTribunale(request);
			}						
		}
//		Segnalazione EMS-157
		if(isCorteDAppello(request) && isProcedimentoOpposizioneRestituzione(request))
			removeVoceAttidelTribunale(request);
		
		if (isDecisorioIGrado(request) && isPersonale(request)) {
			removeVoceAttoBeni(request);
		}

		if (isCorteDAppello(request) && isFascicoloCameraConsiglio(request) && isIstanzaModificaRevocaPersonale(request)) {
			removeVoceAttoBeni(request);
		}

		if (isAttoRevocazioneConfisca(request) && isTipoFascicolo(request, Constants.TIPO_FASCICOLO_MERITO)) {
			removeVoceCollegio(request);
		}
		
		//Segnalazione CO50
		if (isTribunale(request) && isDecretoOrdinanzaRestituzioneBeni(request)){
			removeUdienzaAmministratori(request);
		}
				
		//J.C. SITMP 296 - J.C. SITMP 297 visualizzare la voce amministratori: elenco e nuovo nel Decisorio I Grado.
		//Segnalazione EMS-28
		//if (isTribunale(request) && isDecisorioIGrado(request)){
		//	removeAmministratori(request);
		//}
		
		//J.C. SITMP 300 visualizzare la voce amministratori: elenco e nuovo nella CDA: Decreto Decisorio II grado
		//J.C. SITMP 301 visualizzare la voce amministratori: elenco e nuovo nella CDA: Sequestro Ordinario II Grado
		if (isCorteDAppello(request) == true && isSequestroOrdinarioIIGrado(request) == false && isDecretoDecisorioIIGrado(request) == false){
			removeAmministratori(request);
		}
		
		
		/*
				if (isCorteDAppello(request) == true && controlloBeniSenzaAmministratori(request) == false) { 
				removeAmministratoriVoceNuovo(request);
			}
		}
		*/
		
		//J.C. SITMP 295 visualizzare la voce amministratori: elenco e nuovo nel sequestro II Grado.
		if (isCorteDAppello(request) == true && controlloBeniSenzaAmministratori(request) == false) { 
			//Controllo SE stato atto è in lavorazione o registrato	
			
			AttoForm attoForm = ((AttoForm) request.getSession().getAttribute(Constants.ATTO));
			if(attoForm != null && attoForm.getCodAtto() != 0){
										
				if (attoForm.getStatoAtto() != null && attoForm.getStatoAtto().getId() != null && attoForm.getStatoAtto().getId().equals(String.valueOf(Constants.STATOATTO_REGISTRATO)))
				removeAmministratoriVoceNuovo(request);
				
				if (attoForm.getStatoAtto() != null && attoForm.getStatoAtto().getId() != null && attoForm.getStatoAtto().getId().equals(String.valueOf(Constants.STATOATTO_IN_LAVORAZIONE)))
				removeAmministratori(request);
	
			}
		}
		
		if (isCorteDAppello(request) == true && controlloBeniSenzaAmministratori(request) == true) { 
			//Controllo SE stato atto è in lavorazione o registrato	
			
			AttoForm attoForm = ((AttoForm) request.getSession().getAttribute(Constants.ATTO));
			if(attoForm != null && attoForm.getCodAtto() != 0){
										
				if (attoForm.getStatoAtto() != null && attoForm.getStatoAtto().getId() != null && attoForm.getStatoAtto().getId().equals(String.valueOf(Constants.STATOATTO_REGISTRATO)))
				removeAmministratoriVoceNuovo(request);
				
			}
		}
		//J.C. SITMP 295 END
	
		//Segnalazione EMS-60
		if (isTribunale(request) && isCauzioni(request) && isDecisorioIGradoCameralino(request)){
			removeVoceAttoBeni(request);
		}
		
//		Segnalazione EMS-162
		if(isTribunale(request) && isAttoDecretoSuOpposizioneDissequestro(request))
			removeVoceMenuBeniSMP_GIU(request);
		
//		Segnalazione EMS-133
		if(isProcura(request) && isModRevPatrimoniale(request))
			removeVoceSoggettiIntervenientiTerzi(request);
		
//		Segnalazione EMS-132
		if(isTribunale(request) && isModRevPatrimoniale(request) && isDecisorioIGradoCameralino(request))
			removeVoceUdienzaMenuAtto(request);
		
		request.getSession().setAttribute("menuSinistra2Liv", getMenuController2Liv(request));
		
	}


	private static boolean isCauzioni(HttpServletRequest request) {
		Object attribute = request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO);
		return attribute != null && attribute.equals("" + Constants.TIPOPROC_CAUZIONI_CAM);
	}
	
	private static boolean isCorrezioneErroriMateriali(HttpServletRequest request) {
		Object attribute = request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO);
		return attribute != null && attribute.equals("" + Constants.TIPOPROC_CORREZIONE_ERRORI_CAM);
	}
	
	private static boolean isPermesso(HttpServletRequest request) {
		Object attribute = request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO);
		return attribute != null && attribute.equals("" + Constants.TIPOPROC_PERMESSI_CAM);
	}
	
	private static boolean isModRevPersonale(HttpServletRequest request) {
		Object attribute = request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO);
		return attribute != null && attribute.equals("" + Constants.TIPOPROC_MODIFICA_PERSONALE_CAM);
	}
	
	private static boolean isModRevPatrimoniale(HttpServletRequest request) {
		Object attribute = request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO);
		return attribute != null && attribute.equals("" + Constants.TIPOPROC_MODIFICA_PATRIMONIALE_CAM);
	}
		

	private static boolean isProcura(HttpServletRequest request) {
		Object attribute = request.getSession().getAttribute("idTipoUfficio");
		return attribute != null && (Integer) attribute == Constants.TIPO_UFFICIO_PROCURA_L.intValue();
	}
	
	private static void removeVoceElencoIntervenienti(HttpServletRequest request) {
		for (int k = 0; k < getMenuController2Liv(request).size(); k++) {
			int codiKIdMenu = getMenuController2Liv(request).get(k).getCodiKIdMenu();
			if (codiKIdMenu == VociMenu.ELENCO_INTERVENIENTI_CAUZIONI) {
				getMenuController2Liv(request).remove(getMenuController2Liv(request).get(k));		
				
			}
		}
	}	
	
	private static void removeVoceInvioRicorsoInCda(HttpServletRequest request) {
		for (int k = 0; k < getMenuController2Liv(request).size(); k++) {
			int codiKIdMenu = getMenuController2Liv(request).get(k).getCodiKIdMenu();
			if (codiKIdMenu == VociMenu.INVIO_RICORSO_IN_CDA) {
				getMenuController2Liv(request).remove(getMenuController2Liv(request).get(k));
	
			}
		}
	}	
	
	private static void removeVoceElencoBeni(HttpServletRequest request) {
		if(request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO) != null 
				&& (request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).toString().equals("" + Constants.TIPOPROC_MODIFICA_PERSONALE_CAM)
					|| request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).toString().equals("" + Constants.TIPOPROC_CORREZIONE_ERRORI_CAM)
					|| request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).toString().equals("" + Constants.TIPOPROC_CAUZIONI_CAM)
					|| request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).toString().equals("" + Constants.TIPOPROC_PERMESSI_CAM)))
		{
			for (int k = 0; k < getMenuController1Liv(request).size(); k++) {
				int codiKIdMenu = getMenuController1Liv(request).get(k).getCodiKIdMenu();
				if (codiKIdMenu == VociMenu.ELENCO_BENI) {
					getMenuController1Liv(request).remove(getMenuController1Liv(request).get(k));
				}
			}
		}
	}

	private static void removeVoceAttoBeni(HttpServletRequest request) {
		for (int k = 0; k < getMenuController2Liv(request).size(); k++) {
			int codiKIdMenu = getMenuController2Liv(request).get(k).getCodiKIdMenu();
			if (codiKIdMenu == VociMenu.ATTO_BENI) {
				getMenuController2Liv(request).remove(getMenuController2Liv(request).get(k));
			
			}
		}
	}
	
	private static void removeVoceAttiDellaCda(HttpServletRequest request) {
		for (int k = 0; k < getMenuController2Liv(request).size(); k++) {
			int codiKIdMenu = getMenuController2Liv(request).get(k).getCodiKIdMenu();
			if (codiKIdMenu == VociMenu.ATTI_CDA) {
				getMenuController2Liv(request).remove(getMenuController2Liv(request).get(k));
			}
		}
	}	
	
//	Segnalazione EMS-03
	private static void removeVoceSoggettiDuplicati(HttpServletRequest request) {
		for (int k = 0; k < getMenuController2Liv(request).size(); k++) {
			int codiKIdMenu = getMenuController2Liv(request).get(k).getCodiKIdMenu();
			if (codiKIdMenu == Constants.ID_K_MENU_SOGGETTI_DUPLICATI) {
				getMenuController2Liv(request).remove(getMenuController2Liv(request).get(k));
			}
		}
	}	
	
	private static void removeVoceUdienzaMenuAtto(HttpServletRequest request) {
		//for (int k = 0; k < MenuController.menuSinistra2Liv.size(); k++) {
		//	int codiKIdMenu = MenuController.menuSinistra2Liv.get(k).getCodiKIdMenu();
		//	if (codiKIdMenu == VociMenu.UDIENZA_MENU_ATTO) {
		//		MenuController.menuSinistra2Liv.remove(MenuController.menuSinistra2Liv.get(k));
		for (int k = 0; k < getMenuController2Liv(request).size(); k++) {
			int codiKIdMenu = getMenuController2Liv(request).get(k).getCodiKIdMenu();
			if (codiKIdMenu == VociMenu.UDIENZA_MENU_ATTO) {
				getMenuController2Liv(request).remove(getMenuController2Liv(request).get(k));
			}
		}
	}	
	
	private static void removeVoceCollegio(HttpServletRequest request) {
		//for (int k = 0; k < MenuController.menuSinistra2Liv.size(); k++) {
		//	int codiKIdMenu = MenuController.menuSinistra2Liv.get(k).getCodiKIdMenu();
		//	if (codiKIdMenu == VociMenu.COLLEGIO) {
		//		MenuController.menuSinistra2Liv.remove(MenuController.menuSinistra2Liv.get(k));
		for (int k = 0; k < getMenuController2Liv(request).size(); k++) {
			int codiKIdMenu = getMenuController2Liv(request).get(k).getCodiKIdMenu();
			if (codiKIdMenu == VociMenu.COLLEGIO) {
				getMenuController2Liv(request).remove(getMenuController2Liv(request).get(k));
			}
		}
	}	
	
	private static void removeUdienzaAmministratori(HttpServletRequest request){
		//for (int k = 0; k < MenuController.menuSinistra2Liv.size(); k++) {
		//	TAnMenu voceMenu =  MenuController.menuSinistra2Liv.get(k);
		for (int k = 0; k < getMenuController2Liv(request).size(); k++) {
			TAnMenu voceMenu =  getMenuController2Liv(request).get(k);
			
			int codiKIdMenu =voceMenu.getCodiKIdMenu();
			if ((codiKIdMenu == VociMenu.UDIENZA_MENU_ATTO) || (codiKIdMenu == VociMenu.AMMINISTRATORI_MENU_ATTO)) {
				//MenuController.menuSinistra2Liv.remove(voceMenu);
				getMenuController2Liv(request).remove(voceMenu);
				k--;
			}
		}
	}
	
	private static void removeAmministratori(HttpServletRequest request){
		for (int k = 0; k < getMenuController2Liv(request).size(); k++) {
			TAnMenu voceMenu =  getMenuController2Liv(request).get(k);
			int codiKIdMenu =voceMenu.getCodiKIdMenu();
			if (codiKIdMenu == VociMenu.AMMINISTRATORI_MENU_ATTO) {
				getMenuController2Liv(request).remove(voceMenu);
				k--;
			}
		}
	}
	
	// J.C. SITMP 294 - SITMP 295 - SITMP 306 - SITMP 307
	
	private static void removeAmministratoriVoceNuovo(HttpServletRequest request){
		for (int k = 0; k < getMenuController2Liv(request).size(); k++) {
			TAnMenu voceMenu =  getMenuController2Liv(request).get(k);
			int codiKIdMenu =voceMenu.getCodiKIdMenu();
			if (codiKIdMenu == VociMenu.AMMINISTRATORI_MENU_ATTO && getMenuController2Liv(request).get(k).getCodiKIdMenu() == 65 ) {
				
						getMenuController2Liv(request).remove(getMenuController2Liv(request).get(k));						

			}
		}
	}
	
	// J.C. SITMP 294 - SITMP 295 - SITMP 306 - SITMP 307 END
	
	
	private static void removeVoceSoggetti(HttpServletRequest request){
		for (int k = 0; k < getMenuController2Liv(request).size(); k++) {
			TAnMenu voceMenu =  getMenuController2Liv(request).get(k);
			int codiKIdMenu =voceMenu.getCodiKIdMenu();
			if (codiKIdMenu == VociMenu.ELENCO_INTERVENIENTI_CAUZIONI || codiKIdMenu == VociMenu.NUOVO_SOGGETTO_FISICO || codiKIdMenu == VociMenu.NUOVO_SOGGETTO_GIURIDICO) {
				getMenuController2Liv(request).remove(voceMenu);
				k--;
			}
		}
	}
	
	private static void removeVoceSoggettiIntervenientiTerzi(HttpServletRequest request){
		for (int k = 0; k < getMenuController2Liv(request).size(); k++) {
			TAnMenu voceMenu =  getMenuController2Liv(request).get(k);
			int codiKIdMenu =voceMenu.getCodiKIdMenu();
			if (codiKIdMenu == VociMenu.ELENCO_INTERVENIENTI_CAUZIONI || codiKIdMenu == VociMenu.ELENCO_TERZI) {
				getMenuController2Liv(request).remove(voceMenu);
				k--;
			}
		}
	}
	
	
	private static boolean isIstanzaModificaRevocaPersonale(HttpServletRequest request) {
		String idCategoriaAtto = RequestUtil.recuperaIdCategoriaAtto(request) + "";
		return (idCategoriaAtto.equals(Constants.DECRETO_DECISORIO_PROVV_ISTANZA)|| idCategoriaAtto.equals(Constants.DECRETO_DECISORIO_II_GRADO_CAM));
	}

	private static boolean isPersonale(HttpServletRequest request) {
		return request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO) != null && (request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).equals("" + Constants.TIPOPROC_PERSONALE));
	}

	private static boolean isDecisorioIGrado(HttpServletRequest request) {
		String idCategoriaAtto = RequestUtil.recuperaIdCategoriaAtto(request) + "";
		return idCategoriaAtto.equals(Constants.DECRETO_DECISORIO_I_GRADO);
	}
	private static boolean isDecisorioIGradoCameralino(HttpServletRequest request) {
		String idCategoriaAtto = RequestUtil.recuperaIdCategoriaAtto(request) + "";
		return idCategoriaAtto.equals(Constants.DECRETO_DECISORIO_I_GRADO_CAMERE);
	}
	
	private static boolean isSequestroOrdinarioIIGrado(HttpServletRequest request) {
		String idCategoriaAtto = RequestUtil.recuperaIdCategoriaAtto(request) + "";
		return idCategoriaAtto.equals(Constants.DECRETO_SEQUESTRO_ORDINARIO_II_GRADO);
	}
	
	private static boolean isDecretoDecisorioIIGrado(HttpServletRequest request) {
		String idCategoriaAtto = RequestUtil.recuperaIdCategoriaAtto(request) + "";
		return idCategoriaAtto.equals(Constants.DECRETO_DECISORIO_II_GRADO);
	}
	
	
	private static boolean isAttoRevocazioneConfisca(HttpServletRequest request) {
		String idCategoriaAtto = RequestUtil.recuperaIdCategoriaAtto(request) + "";
		return idCategoriaAtto.equals(Constants.REVOCAZIONE_DELLA_CONFISCA);
	}	
	
	private static boolean isDecretoOrdinanzaRestituzioneBeni(HttpServletRequest request) {
		String idCategoriaAtto = RequestUtil.recuperaIdCategoriaAtto(request) + "";
		return idCategoriaAtto.equals(Constants.DECRETO_ORDINANZA_REST_BENI);
	}
	
	private static boolean isProcedimentoOpposizioneRestituzione(HttpServletRequest request) {
		if(request.getSession().getAttribute(Constants.TIPO_FASCICOLO) != null && request.getSession().getAttribute(Constants.TIPO_FASCICOLO).toString().equals(Constants.TIPO_FASCICOLO_OPP_REVOCA_SEQUESTRO_S))
			return true;
		else
			return false;
	}
	
	private static boolean isAttoOpposizioneDissequestro(HttpServletRequest request) {
		String idCategoriaAtto = RequestUtil.recuperaIdCategoriaAtto(request) + "";
		return idCategoriaAtto.equals(Constants.DECRETO_OPPOSIZIONE_AL_DISSEQUESTRO);
	}
	
	private static boolean isAttoDecretoSuOpposizioneDissequestro(HttpServletRequest request) {
		String idCategoriaAtto = RequestUtil.recuperaIdCategoriaAtto(request) + "";
		return idCategoriaAtto.equals(Constants.SENTENZA_OPPOSIZIONE_AL_DISSEQUESTRO);
	}
	

	private static void caricaMenuSinistra3Liv(HttpServletRequest request) {

		String tipoUtente = (String) request.getSession().getAttribute(Constants.TIPO_UTENTE);
		String idCategoriaAtto = RequestUtil.recuperaIdCategoriaAtto(request) + "";
		
		//MenuController.menuSinistra3Liv = new ArrayList<TAnMenu>();
		request.getSession().setAttribute(SESSION_3LIV,  new ArrayList<TAnMenu>());
		
		ArrayList<TAnMenu>  exMenuStatic = getMenu(request);
		
		//for (int i = 0; i < MenuController.menuSinistra2Liv.size(); i++) {
		for (int i = 0; i < getMenuController2Liv(request).size(); i++) {
			for (int j = 0; j < exMenuStatic.size(); j++) {
				//if (MenuController.menu.get(j).getNumePadre() == (MenuController.menuSinistra2Liv.get(i).getCodiKIdMenu())
				if (exMenuStatic.get(j).getNumePadre() == (getMenuController2Liv(request).get(i).getCodiKIdMenu())						
						&& (exMenuStatic.get(j).getNumeLivello().intValue() == 4)) {
						
					if ((tipoUtente.equals(Constants.TIPO_UTENTE_SMP_GIU) && exMenuStatic.get(j).getCodiKIdMenu() == Constants.ID_K_MENU_ATTO_BENI_NUOVO_TRIBUNALE)
							|| (tipoUtente.equals(Constants.TIPO_UTENTE_SMP) && exMenuStatic.get(j).getCodiKIdMenu() == Constants.ID_K_MENU_ATTO_BENI_NUOVO_PROCURA)) {
						if (request.getSession().getAttribute(Constants.ATTO) != null) {
							AttoForm attoForm = ((AttoForm) request.getSession().getAttribute(Constants.ATTO));
							if (attoForm.getStatoAtto() != null
									&& attoForm.getStatoAtto().getId() != null
									&& (Long.valueOf(attoForm.getStatoAtto().getId()) != Constants.STATOATTO_REGISTRATO && Long.valueOf(attoForm
											.getStatoAtto().getId()) != Constants.STATOATTO_INOLTRATO)) {

								//MenuController.menuSinistra3Liv.add(MenuController.menu.get(j));
								getMenuController3Liv(request).add(exMenuStatic.get(j));

							}
						}
					} else if (!((request.getSession().getAttribute("disabledViewMenu") != null && request.getSession()
							.getAttribute("disabledViewMenu").equals("atto_decretiConvalida")) && exMenuStatic.get(j).getCodiKIdMenu() == Constants.ID_K_MENU_ATTO_BENI_NUOVO_TRIBUNALE)) {
						//MenuController.menuSinistra3Liv.add(MenuController.menu.get(j));
						getMenuController3Liv(request).add(exMenuStatic.get(j));
					}

				}
			}
		}

		// Andrea (AS): elimina le voci di menu non utilizzabili per i decreti di
		// rigetto totale
		if (ComparatorUtil.containsValue(idCategoriaAtto, Constants.DECRETO_RIGETTO_TOTALE_SEQUESTRO,
				Constants.DECRETO_RIGETTO_STATO_RICHIESTA_SEQUESTRO,Constants.DECRETO_CONVALIDA_SEQUESTRO_ANTICIPATO,Constants.DECRETO_CONVALIDA_SEQUESTRO_URGENTE,Constants.DECRETO_REVOCA_SEQUESTRO)) {
			//for (int k = 0; k < MenuController.menuSinistra3Liv.size(); k++) {
			//	TAnMenu voceMenu = MenuController.menuSinistra3Liv.get(k);
			for (int k = 0; k < getMenuController3Liv(request).size(); k++) {
				TAnMenu voceMenu = getMenuController3Liv(request).get(k);
				int codiMenu = voceMenu.getCodiKIdMenu();
				if (codiMenu == Constants.ID_K_MENU_ATTO_BENI_NUOVO_TRIBUNALE) {
					//MenuController.menuSinistra3Liv.remove(voceMenu);
					getMenuController3Liv(request).remove(voceMenu);
					break;
				}
			}
		}
	// elimina la voce nuovo AMMINISTRATORE per gli atti non IN LAVORAZIONE
		if (request.getSession().getAttribute(Constants.ATTO) != null) {
			AttoForm attoForm = ((AttoForm) request.getSession().getAttribute(Constants.ATTO));
			if (attoForm.getStatoAtto() != null
					&& attoForm.getStatoAtto().getId() != null
					&& ((Long.valueOf(attoForm.getStatoAtto().getId()) != Constants.STATOATTO_IN_LAVORAZIONE)
					     ||(ComparatorUtil.containsValue(idCategoriaAtto,Constants.DECRETO_REVOCA_SEQUESTRO))) ) {
				//for (int k = 0; k < MenuController.menuSinistra3Liv.size(); k++) {
				//	TAnMenu voceMenu = MenuController.menuSinistra3Liv.get(k);
				for (int k = 0; k < getMenuController3Liv(request).size(); k++) {
					TAnMenu voceMenu = getMenuController3Liv(request).get(k);
					int codiMenu = voceMenu.getCodiKIdMenu();
					String descMenu = voceMenu.getDescVoce();
					System.err.println(codiMenu + " - " + descMenu);
					if (codiMenu == Constants.ID_K_MENU_ATTO_AMMINISTRATORE_NUOVO) {
						//MenuController.menuSinistra3Liv.remove(voceMenu);
						getMenuController3Liv(request).remove(voceMenu);
						break;
					}
				}

			}
		}
		 
			
		// elimina la voce nuvo Bene del menu ATTO-BENE per il decisorio I grado istanza camerale patrimoniale
		if (request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO) != null 
				&& (request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).equals("" + Constants.TIPOPROC_MODIFICA_PATRIMONIALE_CAM) || request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO)
						.equals("" + Constants.TIPOPROC_TERZO_INTERVENIENTE_CAM))
			&&	ComparatorUtil.containsValue(idCategoriaAtto,Constants.DECRETO_DECISORIO_I_GRADO_CAMERE) ) 
		{
			//for (int k = 0; k < MenuController.menuSinistra3Liv.size(); k++) {
			//	TAnMenu voceMenu = MenuController.menuSinistra3Liv.get(k);
			for (int k = 0; k < getMenuController3Liv(request).size(); k++) {
				TAnMenu voceMenu = getMenuController3Liv(request).get(k);

				int codiMenu = voceMenu.getCodiKIdMenu();
				if (codiMenu == Constants.ID_K_MENU_ATTO_BENI_NUOVO_TRIBUNALE) {
					//MenuController.menuSinistra3Liv.remove(voceMenu);
					getMenuController3Liv(request).remove(voceMenu);
					break;
				}
			}

		}		
		
		
		//rimozione atti del tribunale per cameralino
		ProcedimentoForm procedimento = (ProcedimentoForm)request.getSession().getAttribute(Constants.ID_PROCEDIMENTO);
		
		if (procedimento != null && procedimento.getTipoFascicolo()!= null && 
			procedimento.getTipoFascicolo().getId()!= null 
			&& (procedimento.getTipoFascicolo().getId().equals(String.valueOf(Constants.TIPO_FASCICOLO_CAMERA_DI_CONSIGLIO_S)))){
			for (int k = 0; k < getMenuController3Liv(request).size(); k++) {
				TAnMenu voceMenu = getMenuController3Liv(request).get(k);
			
				int codiMenu = voceMenu.getCodiKIdMenu();
				if (codiMenu == VociMenu.ATTI_DEL_TRIBUNALE) {
					//MenuController.menuSinistra3Liv.remove(voceMenu);
					getMenuController3Liv(request).remove(voceMenu);
					break;
				}
			}		
		}
		
		//Segnalazione CFPG05
		if(isProcuraGenerale(request) && isAttoOpposizioneDissequestro(request)){
			removeVoceMenuBeniNuovo(request);
		}
		
		//request.getSession().setAttribute("menuSinistra3Liv", MenuController.menuSinistra3Liv);
		request.getSession().setAttribute("menuSinistra3Liv",getMenuController3Liv(request));
	}

	
	@RequestMapping(value = "/menu.htm", method = RequestMethod.GET)
	public String analizzaMenu(HttpServletRequest request) throws Exception {

		String path = ServletRequestUtils.getStringParameter(request, "path");
		String descr = ServletRequestUtils.getStringParameter(request, "descr");
		String idPadre = ServletRequestUtils.getStringParameter(request, "idPadre");
		String livello = ServletRequestUtils.getStringParameter(request, "livello");
		String idMenuAttuale = ServletRequestUtils.getStringParameter(request, "idMenuAttuale");
		request.getSession().setAttribute("idMenuAttuale", idMenuAttuale);
		
		//MenuController.menu = (ArrayList<TAnMenu>) request.getSession().getAttribute("menu");
		request.getSession().setAttribute(SESSION_MENU, (ArrayList<TAnMenu>) request.getSession().getAttribute("menu"));
		
		costruisciPath(descr, idPadre,request);
		
		calcolaClick(ServletRequestUtils.getStringParameter(request, "idMenuAttuale"), idPadre, livello,request);
		

		// Giacomo 15/07/2013 Pulisco la breadCrumbs se e solo se il livello è 4
		if (StringUtils.equals("4", livello)) {
			cleanBreadcrumb(request.getSession());
			for (TAnMenu tmenu : getMenu(request)) {
				if (tmenu.getCodiKIdMenu() == NumberUtils.toInt(idMenuAttuale, -1) && StringUtils.isEmpty(tmenu.getDescGruppo())) {
					request.getSession().removeAttribute(Constants.ID_PROCEDIMENTO);
					request.getSession().removeAttribute("soggettiTemp");
					break;
				}
			}

		}

		return "redirect:" + path;
	}

	/**
	 * Metodo per calcolare i menu di sinistra da mantenere aperti
	 * 
	 * @param id
	 * @param livello
	 */
	private void calcolaClick(String id, String idPadre, String livello, HttpServletRequest request) {
		String clickmenu1 = null;
		String clickmenu2 = null;
		
		if (livello.equals("4")) {
			String padre = "";
			//if (MenuController.menuSinistra2Liv !=null) {
			//	for (int i = 0; i < MenuController.menuSinistra2Liv.size(); i++) {
			//		if (Integer.toString(MenuController.menuSinistra2Liv.get(i).getCodiKIdMenu()).equals(idPadre)) {
			//			padre = MenuController.menuSinistra2Liv.get(i).getNumePadre().toString();
			//			clickmenu2 = Integer.toString(MenuController.menuSinistra2Liv.get(i).getCodiKIdMenu());
			if (getMenuController2Liv(request) !=null) {
				for (int i = 0; i < getMenuController2Liv(request).size(); i++) {
					if (Integer.toString(getMenuController2Liv(request).get(i).getCodiKIdMenu()).equals(idPadre)) {
						padre = getMenuController2Liv(request).get(i).getNumePadre().toString();
						clickmenu2 = Integer.toString(getMenuController2Liv(request).get(i).getCodiKIdMenu());
						break;
					}
				}
			}
			//if (MenuController.menuSinistra1Liv!=null) {
			//	for (int i = 0; i < MenuController.menuSinistra1Liv.size(); i++) {
			//		if (Integer.toString(MenuController.menuSinistra1Liv.get(i).getCodiKIdMenu()).equals(padre)) {
			//			clickmenu1 = Integer.toString(MenuController.menuSinistra1Liv.get(i).getCodiKIdMenu());
			if (getMenuController1Liv(request)!=null) {
				for (int i = 0; i < getMenuController1Liv(request).size(); i++) {
					if (Integer.toString(getMenuController1Liv(request).get(i).getCodiKIdMenu()).equals(padre)) {
						clickmenu1 = Integer.toString(getMenuController1Liv(request).get(i).getCodiKIdMenu());
						break;
					}
				}
				
			}
		}
		if (livello.equals("3")) {
			clickmenu2 = null;
			//for (int i = 0; i < MenuController.menuSinistra1Liv.size(); i++) {
			//	if (Integer.toString(MenuController.menuSinistra1Liv.get(i).getCodiKIdMenu()).equals(idPadre)) {
			//		clickmenu1 = Integer.toString(MenuController.menuSinistra1Liv.get(i).getCodiKIdMenu());
			for (int i = 0; i < getMenuController1Liv(request).size(); i++) {
				if (Integer.toString(getMenuController1Liv(request).get(i).getCodiKIdMenu()).equals(idPadre)) {
					clickmenu1 = Integer.toString(getMenuController1Liv(request).get(i).getCodiKIdMenu());
					break;
				}
			}
		}
		if (livello.equals("2")) {
			clickmenu2 = null;
			clickmenu1 = id;
		}
		request.getSession().setAttribute("clickmenu1", clickmenu1);
		request.getSession().setAttribute("clickmenu2", clickmenu2);
	}

	/**
	 * Metodo per la costruzione dinamica della barra di navigazione
	 * 
	 * @param descr
	 *            descrizione del singolo elemento della barra
	 * @param id
	 *            identificativo del padre del singolo elemento
	 */
	private void costruisciPath(String descr, String id, HttpServletRequest request) {
		String navigazione = null;
		Iterator<TAnMenu> itr = getMenu(request).iterator();
		int bdId = 0;
		if (id != null) {
			bdId = Integer.parseInt(id);
		}
		String idPadre = null;
		while (itr.hasNext()) {
			TAnMenu menuRow = itr.next();
			if (menuRow.getCodiKIdMenu() == bdId) {
				navigazione = menuRow.getDescVoce() + " > " + descr;
				idPadre = menuRow.getNumePadre().toString();
				break;
			}
		}
		if ((idPadre != null) && !idPadre.equals("0")) {
			costruisciPath(navigazione, idPadre,request);
		}
		request.getSession().setAttribute("path", navigazione);
		
	}

	private static boolean isProcedimentoRevoca(HttpServletRequest request) {
		return request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO) != null && (request.getSession().getAttribute(Constants.ID_TIPO_PROCEDIMENTO).equals("" + Constants.TIPOPROC_REVOCAZIONE_CONFISCA));
	}

	private static void removeVociMenuProcedimentoRevocaSequestro(HttpServletRequest request) {
		ArrayList<TAnMenu> appoMenu = getMenuController1Liv(request);
		for (int k = 0; k < appoMenu.size(); k++) {
			int codiKIdMenu = appoMenu.get(k).getCodiKIdMenu();
			if (codiKIdMenu == VociMenu.STRALCIO || codiKIdMenu == VociMenu.RIUNIONE || codiKIdMenu == VociMenu.COMUNICAZIONI_EQUITALIA
					|| codiKIdMenu == VociMenu.GIUDICE_DELEGATO || codiKIdMenu == VociMenu.AMMINISTRATORI_MENU_PROCEDIMENTO) {
				getMenuController1Liv(request).remove(getMenuController1Liv(request).get(k));
				k--;
			}
		}
	}

	static class VociMenu {
		final static int GIUDICE_DELEGATO = 104;
		final static int COMUNICAZIONI_EQUITALIA = 98;
		final static int RIUNIONE = 88;
		final static int STRALCIO = 87;
		final static int UDIENZA = 61;
		final static int AMMINISTRATORI_MENU_PROCEDIMENTO= 107;
		final static int ATTI_DEL_TRIBUNALE = 188;
		final static int ATTO_BENI = 50;
		final static int ATTI_CDA = 194;
		final static int COLLEGIO = 51;
		final static int ELENCO_INTERVENIENTI_CAUZIONI = 189;
		final static int INVIO_RICORSO_IN_CDA = 96;
		final static int ELENCO_BENI = 21;
		final static int UDIENZA_MENU_ATTO = 83;
		final static int AMMINISTRATORI_MENU_ATTO = 54;
		final static int BENI_NUOVO_PG = 74;
		final static int NUOVO_SOGGETTO_FISICO = 60;
		final static int NUOVO_SOGGETTO_GIURIDICO = 61;
		final static int ATTO_SMP_GIU = 48;
		final static int ELENCO_TERZI = 94;
		
	}

	private static boolean isCorteDAppello(HttpServletRequest request) {
		return (Integer) request.getSession().getAttribute("idTipoUfficio") == 6;
	}
	


	private static boolean isFascicoloCameraConsiglio(HttpServletRequest request) {
		return isTipoFascicolo(request, Constants.TIPO_FASCICOLO_CAMERA_DI_CONSIGLIO);
	}
	
	private static boolean isFascicoloAggravamento(HttpServletRequest request) {
		return isTipoFascicolo(request, Constants.TIPO_FASCICOLO_AGGRAVAMENTO);
	}
	
	private static boolean isFascicoloFascgestione(HttpServletRequest request) {
		return isTipoFascicolo(request, Constants.TIPO_FASCICOLO_GESTIONE);
	}

	private static boolean isTipoFascicolo(HttpServletRequest request, int tipoFascicolo) {
		return request.getSession().getAttribute(Constants.TIPO_FASCICOLO) != null && request.getSession().getAttribute(Constants.TIPO_FASCICOLO).toString().equals(""+tipoFascicolo);
	}

	private static void removeVoceAttidelTribunale(HttpServletRequest request) {
		for (int k = 0; k < getMenuController2Liv(request).size(); k++) {
			int codiKIdMenu = getMenuController2Liv(request).get(k).getCodiKIdMenu();
			if (codiKIdMenu == VociMenu.ATTI_DEL_TRIBUNALE) {
				getMenuController2Liv(request).remove(getMenuController2Liv(request).get(k));
			}
		}
	}
	
	private static void removeVoceMenuBeniNuovo(HttpServletRequest request) {
		for (int k = 0; k < getMenuController3Liv(request).size(); k++) {
			int codiKIdMenu = getMenuController3Liv(request).get(k).getCodiKIdMenu();
			if (codiKIdMenu == VociMenu.BENI_NUOVO_PG)
				getMenuController3Liv(request).remove(k);
		}
	}
	
	private static void removeVoceMenuBeniSMP_GIU(HttpServletRequest request) {
		for (int k = 0; k < getMenuController1Liv(request).size(); k++) {
			int codiKIdMenu = getMenuController1Liv(request).get(k).getCodiKIdMenu();
			if (codiKIdMenu == VociMenu.ATTO_SMP_GIU)
				getMenuController1Liv(request).remove(k);
		}
	}
	
	
//J.C. SITMP 295
//return true se ci sono beni senza amministratori
//return false se non ci sono beni senza amministratori
private static boolean controlloBeniSenzaAmministratori ( HttpServletRequest request ){
		
		ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute(Constants.ID_PROCEDIMENTO);
		
		if (procedimento == null){ 
			return false;
		}
		
		long idProcedimento = procedimento.getIdProcedimento();		
		int idUfficio = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);	
		Integer idUffOwner = (Integer) request.getSession().getAttribute("idUffOwner");	
		AttoForm atto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO)));
		
		if (atto == null || atto.getCodAtto() == 0){ 
			return false;
		}
		 
		long idAtto = atto.getCodAtto();		
		List<BeneForm> beniModel = beneService.listaBeniPerProcedimento(0, idUfficio, idProcedimento, new BeneForm());		
	    List<TRelProcBeneAttoAmm> amministrazioniModel = beneService.getAmministratoriBeni(idUffOwner, idProcedimento, idAtto, null, false, true);
	    
	    	    
	    List<Long> listaBeniAmministrati = new ArrayList<Long>();
	    
//	    1) Esiste 1 bene in un decSeqII?
//		SI -> vedo la voce menu
//		NO -> NON vedo la voce menu
//		2) Esiste almeno un bene del fascicolo che NON abbia amministrazioni precedenti (esclusi i seq II) ossia che non abbia relaz amm O che abbia la data fine <> null?
//		SI -> vedo la voce menu
//		NO -> NON vedo la voce menu
	    	    
	    if (atto.getCategoriaAtto().getId().equals(Constants.DECRETO_DECISORIO_II_GRADO)) {
		    List<AttoForm> listaAttiSequestri = attoService.getAttiByProcedimento(idProcedimento, 18l, "322");
			for (TRelProcBeneAttoAmm recordRelProcBeneAttoAmm : amministrazioniModel) {	    	
				for (AttoForm attoRecord : listaAttiSequestri) {	    	
			    	if (recordRelProcBeneAttoAmm.getTAtto().getId().getCodiKIdAtto() ==  attoRecord.getCodAtto()) {
			    			return true;
			    	}
				}
			}	    	    	
	    }
	    
	    for (TRelProcBeneAttoAmm beneAmm : amministrazioniModel) {    	
	    	long idBeneAmm = beneAmm.getTBene().getId().getCodiKIdBene();
	    	
	    	listaBeniAmministrati.add(idBeneAmm);  
	    }
	    
	    for (BeneForm bene : beniModel) {    	
	    	long idBene = bene.getIdBene();
	    	boolean checkIdBene = listaBeniAmministrati.contains(idBene);    	
	    	if(checkIdBene==false){
	    		return true;
	    	}
	    	else{	    		
	    		for (TRelProcBeneAttoAmm beneAmm : amministrazioniModel) {
	    			long idBeneAmm = beneAmm.getTBene().getId().getCodiKIdBene();		    		  
	    			if (idBene == idBeneAmm){
	    				Date dataFineIncarico = beneAmm.getDataFineIncarico();
	    				if(dataFineIncarico != null){
	    					return true;
	    				}	    			
	    			}	    	
	    		}	    	
	    	} 	
	    }
	    
		return false;
	}
	
//J.C. SITMP 295 END
	
}
