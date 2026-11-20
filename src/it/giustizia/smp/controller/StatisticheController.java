package it.giustizia.smp.controller;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.giustizia.smp.dao.StatisticheDao;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.StatisticheForm;
import it.giustizia.smp.model.TStampa;
import it.giustizia.smp.service.StatisticheService;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.statistiche.StatisticheFactory;
import it.giustizia.smp.validation.StatisticheValidation;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterConfiguration;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

/***
 * Controller delle statistiche
 * @author m.prisco
 *
 */
@Controller
public class StatisticheController  extends BreadcrumbsController {

	@Autowired
	private StatisticheService statisticheService;
	
	@Autowired
	private StatisticheDao statisticheDao;
	
	@Autowired
	private StatisticheValidation statisticheValidation;
	
	private static final String SESSION_ATTRIB_STATISTICHE_FORM = "statisticheForm";
	
	/***
	 * Lista delle statistiche disponibili per il tipo ufficio connesso
	 * @param statisticheForm
	 * @param result
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = {"/statistiche.htm","/statistiche/elenco.htm"}, method = RequestMethod.GET)
    public String elencoStatistiche(StatisticheForm statisticheForm, HttpServletRequest request, ModelMap modelMap) {
		String paramFiltro = request.getParameter("filtro");
		if (paramFiltro!=null) {
			if ("1".equals(paramFiltro)) {
				statisticheForm.getFiltro2().setId("-1");
				statisticheForm.getFiltro3().setId("-1");
			} else if ("2".equals(paramFiltro)) {
				statisticheForm.getFiltro3().setId("-1");
			}
			request.getParameter("filtro");
		}
		
		int idTipoUff = recuperaIdTipoUfficio();
		
		List<TStampa> listaStampe = statisticheService.getListaStampeByTipoUfficio(idTipoUff,statisticheForm);
		statisticheForm.setListaStampe(listaStampe);
		modelMap.addAttribute("listaStampe",listaStampe);
		
		
		modelMap.addAttribute("valoriFiltro1", statisticheService.getValoriFiltro1(statisticheForm));
		modelMap.addAttribute("valoriFiltro2", statisticheService.getValoriFiltro2(statisticheForm));
		modelMap.addAttribute("valoriFiltro3", statisticheService.getValoriFiltro3(statisticheForm));
		
		
		addBreadcrumbs("GET", "/statistiche/elenco.htm", null);
		return "/statistiche/elenco";
	}
	
	/***
	 * Controller della selezione di una statistica dalla lista delle statistiche.
	 * @param statisticheForm
	 * @param result
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = {"/statistiche/elenco.htm"}, method = RequestMethod.POST)
    public String elencoStatisticheVisualizza(StatisticheForm statisticheForm, HttpServletRequest request, ModelMap modelMap) {
		if (statisticheForm.getIdStampa()==0) {
			// Se non è stato selezionato il filtro 
			super.setMessage(Constants.MESSAGE, "statistiche.errore.selezionareReport", null, false);
			return elencoStatistiche(statisticheForm,  request, modelMap);
		} else {
			return filtriStatistiche(statisticheForm, request, modelMap);
		}
	}
	
	@RequestMapping(value = {"/statistiche/elencoFiltrato.htm"}, method = RequestMethod.POST)
    public String elencoStatisticheVisualizzaRefresh(StatisticheForm statisticheForm, HttpServletRequest request, ModelMap modelMap) {
		return elencoStatistiche(statisticheForm, request, modelMap);
	}
	
	/***
	 * Controller dell'intefaccia per la selezione di filtri sulla statistica selezionata
	 * @param statisticheForm
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = {"/statistiche/filtriStatistica.htm"}, method = {RequestMethod.POST,RequestMethod.GET})
    public String filtriStatistiche(StatisticheForm statisticheForm,  HttpServletRequest request, ModelMap modelMap) {
		TStampa stampa = statisticheService.getStampaByID(statisticheForm.getIdStampa());
		//Per ogni campo filtro necessario imposto l'attributo per la visibilità e carico i dati se necessario
		String[] campiFiltro = stampa.getDescCampiFiltro().split(",");
		if (request.getSession().getAttribute(SESSION_ATTRIB_STATISTICHE_FORM)!=null && ((StatisticheForm)request.getSession().getAttribute(SESSION_ATTRIB_STATISTICHE_FORM)).getIdStampa() == statisticheForm.getIdStampa()) {
			//Remposto i valori di statistiche form con i dati provenienti dal dal ben di sessione se disponibile e non stata cambiata la stampa
			//L'operazione viene eseguità perche sembra che l'indietro tramite addBreadcrumbs non posto tutti i valori.
			statisticheForm = rempostaValoriDaSessione(statisticheForm,request);
		}
		for (String filtro : campiFiltro) {
			filtro = filtro.replaceAll(" ","");
			modelMap.addAttribute("has"+filtro,"true");
			if ("Magistrato".equalsIgnoreCase(filtro) ) {
				caricaComboMagistrati(modelMap);
			} else if ("Anno".equalsIgnoreCase(filtro) ) {
				caricaComboAnno(modelMap);
			} else if ("Semestre".equalsIgnoreCase(filtro) ) {
				caricaComboSemestre(modelMap);
			} else if ("Procedente".equalsIgnoreCase(filtro) ) {
				caricaComboProcedente(modelMap);
			}
			
		}
		modelMap.addAttribute(SESSION_ATTRIB_STATISTICHE_FORM,statisticheForm);
			
		setMessage("formTitle", "statistiche.filtri.titolo", stampa.getDescStampa(), false);
		addBreadcrumbs("POST", "/statistiche/filtriStatistica.htm", null);
		return "/statistiche/filtriStatistica";
	}
	
	/***
	 * Remposto i volori di statistiche form con i dati provenienti dal dal ben di sessione se disponibile
	 * @param statisticheForm
	 */
	private StatisticheForm rempostaValoriDaSessione(StatisticheForm statisticheForm, HttpServletRequest request) {
		StatisticheForm statisticheFormSessione = (StatisticheForm)request.getSession().getAttribute(SESSION_ATTRIB_STATISTICHE_FORM);
		if (statisticheFormSessione!=null) {
			return statisticheFormSessione;
		} else {
			return statisticheForm;
		}
	}

	/***
	 * Controllo della valorizzazione dei filtri della statistica richiesta
	 * @param statisticheForm
	 * @param result
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = {"/statistiche/filtriStatistica.htm"}, method = {RequestMethod.POST}, params= { "VisualizzaReport" })
    public String validateFiltriStatistiche(StatisticheForm statisticheForm,BindingResult result, HttpServletRequest request, ModelMap modelMap) {
		statisticheValidation.validateFiltri(statisticheForm, result, request);
		if (result.hasErrors()) {
			return filtriStatistiche( statisticheForm, request, modelMap) ;
		}
		// Se non ci sono errori visualizzo la statistica
		return visualizzaStatistica( statisticheForm, request) ;
	}
	
	/***
	 * Visualizzazione della statistica 
	 * @param statisticheForm
	 * @return
	 */
	@RequestMapping(value = {"/statistiche/visualizzaStatistica.htm"}, method = {RequestMethod.GET,RequestMethod.POST})
    public String visualizzaStatistica(StatisticheForm statisticheForm, HttpServletRequest request ) {
		TStampa stampa = statisticheService.getStampaByID(statisticheForm.getIdStampa());
		setMessage("formTitle", "statistiche.filtri.titolo", stampa.getDescStampa(), false);
		addBreadcrumbs("POST", "/statistiche/visualizzaStatistica.htm",null);
		// Imposto il sessione l'oggetto statistica per poter rilegere i filtri degli exporter
		request.getSession().setAttribute(SESSION_ATTRIB_STATISTICHE_FORM, statisticheForm);
		return "/statistiche/visualizzaStatistica";
	}
	
	
	/***
	 * Exporter della statistica in sessione in formato HTML
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = {"/statistiche/statisticaExporterHTML.htm"}, method = {RequestMethod.GET,RequestMethod.POST})
    public String statisticaExporterHtml(HttpServletRequest request, HttpServletResponse response) {
		Connection connection = null;	
		String nomeStatistica="";
		try {
			StatisticheForm statisticheForm = (StatisticheForm)request.getSession().getAttribute(SESSION_ATTRIB_STATISTICHE_FORM);
			TStampa stampa = statisticheService.getStampaByID(statisticheForm.getIdStampa());
			nomeStatistica = stampa.getDescReportView();
			logger.info("INIZIO - generazione del report " + nomeStatistica + " in formato HTML");
			JasperReport report = StatisticheFactory.createJasperReport(nomeStatistica, request);
			Map<String,Object> parameterMap = createParamMap(statisticheForm);
			
			connection = statisticheDao.getSqlConnection();
			JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameterMap, connection);
			HtmlExporter exporter = new HtmlExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			SimpleHtmlExporterConfiguration htmlConfiguration = new  SimpleHtmlExporterConfiguration();
			int zoomValue  = getCookieZoom(request);
			if (zoomValue>300) {
				zoomValue = 200;
			}
			
			
			htmlConfiguration.setHtmlHeader(
					"<html>"+
						    "<head>"+
						    "  <title></title>"+
						    "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>"+
						    "  <link rel=\"stylesheet\" type=\"text/css\" href=\"css/jasper.css\" />"+
						    "  <style type=\"text/css\">"+
						    "    a {text-decoration: none}"+
						    "    body {zoom: " + zoomValue+ "%;}"+
						    "  </style>"+
						    "</head>"+
						    "<body text=\"#000000\" link=\"#000000\" alink=\"#000000\" vlink=\"#000000\">" +
						    "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">" +
						    "<tr><td width=\"50%\">&nbsp;</td><td align=\"center\">"
						    
			);
			exporter.setConfiguration(htmlConfiguration);
			SimpleHtmlExporterOutput exporterHtml = new SimpleHtmlExporterOutput(response.getOutputStream());
			
			exporter.setExporterOutput(exporterHtml);
			exporter.exportReport();
			
		} catch (Exception e) {
			logger.error("Errore:" + e.getMessage(),e);
			request.getSession().setAttribute("lastError", "Errore:" + e.getMessage() );
			return "/statistiche/visualizzaErroreStatistica";
		} finally {
			try {
				if (connection!=null && !connection.isClosed()) { 
					connection.close(); 
				} 
			} catch (SQLException e) {
				logger.error("Errore:" + e.getMessage(),e);
			}
		}
		logger.info("FINE - generazione del report " + nomeStatistica+ " in formato HTML");
		return null;
	}
	
	
	public int getCookieZoom(HttpServletRequest request) {
		try {
			Cookie[] cookies = request.getCookies();
			for(Cookie cookie : cookies){
			    if("zoomValue".equals(cookie.getName())) {
			    	try {
			    		return Integer.parseInt(cookie.getValue());	
			    	} catch (Exception e) {
			    		logger.error("Errore:" + e.getMessage(),e);
			    	}
			    }
			}
		} catch (Exception e) {
			logger.error("Errore:" + e.getMessage(),e);
		}
		return 100;
	}
	
	/***
	 * Exporter della statistica in sessione in formato PDF
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = {"/statistiche/statisticaExporterPDF.htm"}, method = {RequestMethod.GET,RequestMethod.POST})
    public File statisticaExporterPDF(HttpServletRequest request,HttpServletResponse response) {
		
		StatisticheForm statisticheForm = (StatisticheForm)request.getSession().getAttribute(SESSION_ATTRIB_STATISTICHE_FORM);
		TStampa stampa = statisticheService.getStampaByID(statisticheForm.getIdStampa());
		String nomeStatistica = stampa.getDescReportView();
		logger.info("INIZIO - generazione del report " + nomeStatistica + " in formato PDF");
		Connection connection = null;
		try {
			JasperReport report = StatisticheFactory.createJasperReport(nomeStatistica, request);
			Map<String,Object> parameterMap = createParamMap(statisticheForm);
			response.setHeader("Cache-control", "");
	        response.setContentType("application/pdf");
	        response.setHeader("Content-Disposition", "attachment;filename=" + stampa.getDescReportView() + ".pdf");
			connection = statisticheDao.getSqlConnection();
			JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameterMap, connection);
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
			exporter.exportReport();
		} catch (Exception e) {
			logger.error("Errore:" + e.getMessage(),e);
		} finally {
			try {
				if (connection!=null && !connection.isClosed()) {
					connection.close(); 
				} 
			} catch (SQLException e) {
				logger.error("Errore: " + e.getMessage(),e);
			}
		}
		logger.info("FINE - generazione del report " + nomeStatistica  + " in formato PDF");
		return null;
	}
	
	
	/***
	 * Exporter della statistica in sessione in formato EXCEL
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = {"/statistiche/statisticaExporterEXCEL.htm"}, method = {RequestMethod.GET,RequestMethod.POST})
    public File statisticaExporterEXCEL(HttpServletRequest request,HttpServletResponse response) {
		StatisticheForm statisticheForm = (StatisticheForm)request.getSession().getAttribute(SESSION_ATTRIB_STATISTICHE_FORM);
		TStampa stampa = statisticheService.getStampaByID(statisticheForm.getIdStampa());
		String nomeStatistica = stampa.getDescReportView();
		logger.info("INIZIO - generazione del report " + nomeStatistica + " in formato EXCEL");
		Connection connection = null;
		try {
			JasperReport report = StatisticheFactory.createJasperReport(nomeStatistica, request);
			Map<String,Object> parameterMap = createParamMap(statisticheForm);
			response.setHeader("Cache-control", "");
	        response.setContentType("application/vnd.ms-excel");
	        report.setProperty("net.sf.jasperreports.export.xls.exclude.origin.band.0", "pageFooter");
 	        report.setProperty("net.sf.jasperreports.crosstab.bucket.measure.limit", "100000");
	        report.setProperty("net.sf.jasperreports.export.xls.exclude.origin.band.1", "pageHeader");
	        report.setProperty("net.sf.jasperreports.export.xls.exclude.origin.keep.first.band.2", "pageHeader");
	        report.setProperty("net.sf.jasperreports.export.xls.exclude.origin.band.3", "columnHeader");
	        report.setProperty("net.sf.jasperreports.export.xls.exclude.origin.keep.first.band.4", "columnHeader");
	        response.setHeader("Content-Disposition", "attachment;filename=" + stampa.getDescReportView() + ".xls");
			connection = statisticheDao.getSqlConnection();
			JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameterMap, connection);
			JRXlsExporter exporter = new JRXlsExporter();
			SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
			configuration.setOnePagePerSheet(false);
			configuration.setDetectCellType(true);
			configuration.setCollapseRowSpan(false);
			configuration.setWhitePageBackground(false);
			configuration.setRemoveEmptySpaceBetweenRows(true);
			exporter.setConfiguration(configuration);
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
			exporter.exportReport();
		} catch (Exception e) {
			logger.error("Errore:" + e.getMessage(),e);
		} finally {
			try {
				if (connection!=null && !connection.isClosed()) { 
					connection.close(); 
				} 
			} catch (SQLException e) {
				logger.error("Errore:" + e.getMessage(),e);
			}
		}
		logger.info("FINE - generazione del report " + nomeStatistica + " in formato EXCEL");
		return null;
	}
	
	/***
	 * Restituisce la mappa dei parametri da passare a jasper
	 * @param statisticheForm
	 * @return
	 */
	public Map<String,Object> createParamMap(StatisticheForm statisticheForm) {
		Map<String,Object> parameterMap = new HashMap<String, Object>();
		// Valorizzo la paramMap data passare a jasper
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		SimpleDateFormat sdfShort =  new SimpleDateFormat("yyyy-MM-dd");
		Date dataDal  = null;
		try {
			dataDal  = sdf.parse(sdfShort.format(statisticheForm.getFiltroDataDal()) + " 00:00:00") ;
		} catch (Exception e) {
			logger.error("Errore:" + e.getMessage(),e);
		}
		Date dataAl  = null;
		try {
			dataAl  = sdf.parse(sdfShort.format(statisticheForm.getFiltroDataAl()) + " 23:59:59") ;
		} catch (Exception e) {
			logger.error("Errore:" + e.getMessage(),e);
		}
		
		parameterMap.put("DATA_INIZIO", dataDal);
		parameterMap.put("DATA_FINE", dataAl);
		parameterMap.put("MAGISTRATO", statisticheForm.getMagistrato()!=null?Integer.parseInt(statisticheForm.getMagistrato().getId()):0);
		parameterMap.put("ANNO", statisticheForm.getAnno()!=null?Integer.parseInt(statisticheForm.getAnno().getId()):0);
		parameterMap.put("SEMESTRE", statisticheForm.getSemestre()!=null?Integer.parseInt(statisticheForm.getSemestre().getId()):0);
		parameterMap.put("PROCEDENTE", statisticheForm.getProcedente()!=null?Integer.parseInt(statisticheForm.getProcedente().getId()):0);
		parameterMap.put("UFFICIO", recuperaIdUfficioUtente());
		parameterMap.put("TIPO_UFFICIO", recuperaIdTipoUfficio());
		parameterMap.put("UTENTE", recuperaIdCodiceUtente());
		for (Map.Entry<String,Object> entry : parameterMap.entrySet()) {
			logger.info("Parametro " + entry.getKey() + " : " + entry.getValue());
		}
		return parameterMap;
	}
	
	
	/***
	 * Carica la combo degli anni a partire dell'anno corrente per 5 anni precedenti
	 * @param statisticheForm
	 * @param modelMap
	 */
	private void caricaComboAnno(ModelMap modelMap) {
		List<ComboForm> anni = new ArrayList<ComboForm>();
		int yearEnd = Calendar.getInstance().get(Calendar.YEAR);
		int yearStart = yearEnd-5; 
		for (int i = yearEnd; i > yearStart; i--) {
			anni.add(new ComboForm(Integer.toString(i), Integer.toString(i)));
		}
		modelMap.addAttribute("anni", anni);
		
	}

	/***
	 * Carica la combo dei semestri con i valori fissi "Primo","Secondo"
	 * @param statisticheForm
	 * @param modelMap
	 */
	private void caricaComboSemestre(ModelMap modelMap) {
		List<ComboForm> semestri = new ArrayList<ComboForm>();
		semestri.add(new ComboForm("1","Primo"));
		semestri.add(new ComboForm("2","Secondo"));
		modelMap.addAttribute("semestri", semestri);
	}

	/***
	 * Carica la combo dei magistrati
	 * @param statisticheForm
	 * @param modelMap
	 */
	public void caricaComboMagistrati(ModelMap modelMap) {
		modelMap.addAttribute("magistrati", statisticheService.getComboMagistrati(recuperaIdUfficioUtente()));
	
	}
	
	/***
	 * Carica la combo dei procedenti
	 * @param statisticheForm
	 * @param modelMap
	 */
	public void caricaComboProcedente(ModelMap modelMap) {
		modelMap.addAttribute("procedenti", statisticheService.getComboProcedente(recuperaIdUfficioUtente()));
		
	}


	
}
