package it.giustizia.smp.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.giustizia.smp.dto.pg_report.ReportMispatrBeneSogDto;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.MagistratoForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.ReportForm;
import it.giustizia.smp.service.AttoService;
import it.giustizia.smp.service.ReportService;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.report.ReportFactory;
import it.giustizia.smp.utility.report.ReportUtility;



@Controller
public class ReportController extends BaseController{
	
	@Autowired
	private ReportService		reportService;
	@Autowired
	private AttoService			attoService;	
	
	private ReportFactory 		reportFactory;

	/**
	 * Generazione del report in rtf
	 * 
	 * @param attoForm
	 * @param result
	 * @param request
	 * @param response
	 * @param modelMap
	 * 
	 * @author Almaviva S.p.A.
	 * 
	 */
	
	
	@RequestMapping(value = "/procedimento/atto/report.htm", method = RequestMethod.GET)
	public void redigiAtto( HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		
		reportFactory = new ReportFactory();

		int idUffOwn = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		
		ProcedimentoForm procedimentoForm = (ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO));
		AttoForm attoForm = (AttoForm) (request.getSession().getAttribute(Constants.ATTO));

		//istanzio la lista che conterrà i report form
		List<ReportForm> dati = new ArrayList<ReportForm>();
		 
		//cerco il soggetto	
		List<ReportMispatrBeneSogDto> listaMispatrBeneSogDto = reportService.getMisPatrBeneSog(attoForm.getCodAtto(), idUffOwn, procedimentoForm.getIdProcedimento());
		List<MagistratoForm> listaMagistrati = attoService.listaMagistratiPerAtto(idUffOwn, attoForm.getCodAtto(), 0);
		
		ReportForm report = new ReportForm();
		report.setDataSourceMagistrati(ReportUtility.createDataSource(listaMagistrati));
		report.setSubreportMagistrati(ReportUtility.createSubreport("magistrati", request));
		
		report.setDataSourceMispatrBeneSog(ReportUtility.createDataSource(listaMispatrBeneSogDto));
		report.setSubreportMispatrBeneSog(ReportUtility.createSubreport("MispatrBeneSog", request));
		
		report.setProcedimento(procedimentoForm);
		report.setAtto(attoForm);
		
		dati.add(report);

    	/* 
    	 * Creo un HashMap che racchiude i parametri che voglio passare al report.
    	 * In questa sezione imposto anche i parametri che servono agli eventuali subreport
		 */
        
    	Map<String, Object> parametri = new HashMap<String, Object>(); 
    	parametri.put("P_URL_LOGO_MIN", ReportUtility.getLogoMinistero(request));
    	ReportUtility.impostaSubreport(parametri, listaMispatrBeneSogDto, "MispatrBeneSog", request);
  
        /*
         * Scelgo il nome con cui salvare il file, senza specificare
         * l'estensione.
         */
      
        String nomeFile = "DecretoSequestro";
        
        /*
         * Indico il nome del file .jrxml che rappresenta il template del report
         * da riempire con i dati. Da notare che il file si trova nella cartella
         * "template" dentro "WebContent" e, in questo caso, il file si chiama
         * "ReportProva.jrxml".
         */
        String templateName = "DecretoSequestro";    
        
    
        
        /*
         * Creo un oggetto di tipo .Formato che indica la mia
         * preferenza riguardo il formato in cui salvare il report.
         */
        ReportUtility.Formato formato = ReportUtility.Formato.RTF;

        /*
         * Mi limito a invocare il metodo statico "stampa" della classe
         * "ReportUtility", passandogli in input i dati generati. Da notare che
         * il metodo lancia una IOException qualora non riesce a trovare il
         * template o non abbia permesso di scrittura in quella cartella.
         */
        try {
        	
        	
        	//JasperReportBuilder jrb = reportFactory.creaReportDecretoSequestro(templateName, request, listaMispatrBeneSogDto);
            ReportUtility.stampa(nomeFile, formato, templateName, dati, parametri, request, response);
        } catch (IOException exc) {
            exc.printStackTrace();
        } catch (Exception exc) {
        	exc.printStackTrace();
        }
        //return new ModelAndView("/procedimento/atto/confermaInviaAtti", "attoForm", attoForm);
    }   
	
	
	

	


}
