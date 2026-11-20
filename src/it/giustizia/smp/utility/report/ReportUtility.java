package it.giustizia.smp.utility.report;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import static net.sf.dynamicreports.report.builder.DynamicReports.* ;
import net.sf.dynamicreports.report.base.DRReport;
import net.sf.dynamicreports.report.builder.ParameterBuilder;
import net.sf.dynamicreports.report.builder.ReportTemplateBuilder;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.component.SubreportBuilder;
import net.sf.dynamicreports.report.builder.style.ReportStyleBuilder;
import net.sf.dynamicreports.report.constant.ComponentPositionType;
import net.sf.dynamicreports.report.definition.expression.DRIExpression;
import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;

public class ReportUtility {

    public static enum Formato {
        CSV("csv", new JRCsvExporter(), "text/csv"), DOCX("docx", new JRDocxExporter(), "application/vnd.ms-word"), HTML("html", new JRHtmlExporter(), "text/html"), PDF("pdf", new JRPdfExporter(), "application/pdf"), RTF("rtf", new JRRtfExporter(), "application/rtf"), TXT("txt", new JRTextExporter(), "text/plain"), XLS("xls", new JRXlsExporter(), "application/vnd.ms-excel"), XML("xml", new JRXmlExporter(), "application/xml");

        private final String             contentType;
        private final String             estensione;
        private final JRAbstractExporter exporter;

        Formato(String estensione, JRAbstractExporter exporter, String contentType) {

            this.estensione = estensione;
            this.exporter = exporter;
            this.contentType = contentType;
        }

        public String getContentType() {

            return contentType;
        }

        public String getEstensione() {

            return estensione;
        }

        public JRAbstractExporter getExporter() {

            return exporter;
        }
    }

    /**
     * Produce un report contenente i dati in input, nel formato specificato. Da
     * notare che esiste una
     * corrispondenza biunivoca e precisa tra la versione di
     * JasperReport con cui è stato creato il file .jrxml di template e la
     * versione delle librerie jasperreports.jar e iText.jar, in assenza di
     * match la funzionalità cessa di operare correttamente.
     * 
     * @param nomeFile
     *            è il nome, senza estensione, con cui si vuole salvare il file.
     * @param formato
     *            è il formato col quale si vuole salvare il report.
     * @param templateName
     *            è il nome del file .jrxml con funzione di template vuoto del
     *            report da riempire con i dati.
     * @param dati
     *            è una lista di oggetti, eventualmente indentati, contenente i
     *            dati da visualizzare nel report.
     * @param request
     *            è la request associata alla call in atto.
     * @param response
     *            è la response associata alla call in atto.
     * @throws IOException
     *             Lancia un'eccezione di Input/Output se non riesce a creare il
     *             file nel path specificato.
     */
    @SuppressWarnings("rawtypes")
    public static void stampa(String nomeFile, Formato formato, String templateName, List dati, Map<String, Object> parametri, HttpServletRequest request, HttpServletResponse response) throws IOException {

    	
    	
        ReportDataSource dataSource = new ReportDataSource(dati);
        OutputStream os = response.getOutputStream();
        String path = componiPath(templateName, request);
        try {
        	File file = new File(path);
    		    		
            JasperReport jasperReport = JasperCompileManager.compileReport(path);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, creaOpzioniStatiche(file, parametri), dataSource);
//            if (jasperPrint.getPages().size() <= 0) {
//                throw new Exception("Errore nel data source del report");
//            }
 
            JRAbstractExporter exporter = formato.getExporter();
            
            if (exporter == null) {
                throw new Exception("Formato non supportato.");
            }
            impostaResponse(nomeFile, formato, response);
            impostaParametriStatici(exporter, jasperPrint, os);
            impostaParametriPerFormato(exporter, formato);
            exporter.exportReport();
        } catch (Throwable e) {
            Formato formatoPerEccezione = Formato.TXT;
            impostaResponse(nomeFile, formatoPerEccezione, response);
            e.printStackTrace(new PrintStream(os));
        } finally {
            os.flush();
            os.close();
        }
    }
    
    /**
     * Produce un report contenente i dati in input, nel formato specificato. Da
     * notare che esiste una
     * corrispondenza biunivoca e precisa tra la versione di
     * JasperReport con cui è stato creato il file .jrxml di template e la
     * versione delle librerie jasperreports.jar e iText.jar, in assenza di
     * match la funzionalità cessa di operare correttamente.
     * 
     * @param nomeFile
     *            è il nome, senza estensione, con cui si vuole salvare il file.
     * @param formato
     *            è il formato col quale si vuole salvare il report.
     * @param templateName
     *            è il nome del file .jrxml con funzione di template vuoto del
     *            report da riempire con i dati.
     * @param dati
     *            è una lista di oggetti, eventualmente indentati, contenente i
     *            dati da visualizzare nel report.
     * @param request
     *            è la request associata alla call in atto.
     * @param response
     *            è la response associata alla call in atto.
     * @throws IOException
     *             Lancia un'eccezione di Input/Output se non riesce a creare il
     *             file nel path specificato.
     */
    @SuppressWarnings("rawtypes")
    public static void stampaDinamico(String nomeFile, Formato formato, List dati, JasperReportBuilder jrb, HttpServletRequest request, HttpServletResponse response) throws IOException {

    	
    	
        ReportDataSource dataSource = new ReportDataSource(dati);
        OutputStream os = response.getOutputStream();

        try {
    		
    		//PROVA DYNAMIC REPORTS
    		jrb.setDataSource(dataSource);
    			
    		
//    		JasperReport jasperReport = jrb.toJasperReport();
    		JasperPrint jasperPrint = jrb.toJasperPrint();
    		
    		
    		//PROVA DYNAMIC REPORTS
    		
//Commento questa parte per le prove con DynamicJasper    		
//            JasperReport jasperReport = JasperCompileManager.compileReport(path);
//            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, creaOpzioniStatiche(file, parametri), dataSource);
//            if (jasperPrint.getPages().size() <= 0) {
//                throw new Exception("Errore nel data source del report");
//            }
 
            JRAbstractExporter exporter = formato.getExporter();
            
            if (exporter == null) {
                throw new Exception("Formato non supportato.");
            }
            impostaResponse(nomeFile, formato, response);
            impostaParametriStatici(exporter, jasperPrint, os);
            impostaParametriPerFormato(exporter, formato);
            exporter.exportReport();
        } catch (Throwable e) {
            Formato formatoPerEccezione = Formato.TXT;
            impostaResponse(nomeFile, formatoPerEccezione, response);
            e.printStackTrace(new PrintStream(os));
        } finally {
            os.flush();
            os.close();
        }
    }
    
    public static void impostaSubreport(Map<String, Object> parametri, List dati, String templateName, HttpServletRequest request)  {
   	 String path = componiPath(templateName, request);
   	 try {
   		 //File file = new File(path);
   		ReportDataSource dataSourceListaMagistrati = new ReportDataSource(dati);
        JasperReport jasperSubreport = JasperCompileManager.compileReport(path);
        parametri.put("P_SUB_" + templateName, jasperSubreport);
        parametri.put("P_SUB_DATA_SOURCE_" + templateName, dataSourceListaMagistrati);
   	 } catch(Exception e) {
   		 e.printStackTrace();
   	 }
   }
    
    public static ReportDataSource createDataSource(List dati) {
    	ReportDataSource dataSource = new ReportDataSource(dati);
    	return dataSource;
    }
    
    public static JasperReport createSubreport(String templateName, HttpServletRequest request) {
    	JasperReport jasperReport = null;
    	try {
	    	String path = componiPath(templateName, request);
	    	jasperReport = JasperCompileManager.compileReport(path);	    
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	return jasperReport;
    }

    private static String componiPath(String templateName, HttpServletRequest request) {

        String path = request.getSession().getServletContext().getRealPath("template") + "\\" + templateName + ".jrxml";
        return path;
    }
    

    public static String getLogoMinistero(HttpServletRequest request) {
        String immagine = request.getSession().getServletContext().getRealPath("images") + "\\Report_Logo_Min_.jpg";
        return immagine;
    }
    

    private static Map<String, Object> creaOpzioniStatiche(File file, Map<String, Object> parametri) {

        //Map<String, Object> opzioni = new HashMap<String, Object>();
        parametri.put("SUBREPORT_DIR", file.getParent() + '/');
        parametri.put("REPORT_LOCALE", Locale.ITALIAN);
        return parametri;
    }

    private static void impostaParametriPerFormato(JRAbstractExporter exporter, Formato formato) {

        switch (formato) {
            case XLS:
                exporter.setParameter(JRXlsAbstractExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
                break;
            case HTML:
                exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
                break;
        }
    }

    private static void impostaParametriStatici(JRAbstractExporter exporter, JasperPrint jasperPrint, OutputStream os) {

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, os);
    }

    private static void impostaResponse(String nomeFile, Formato formato, HttpServletResponse response) {

        response.setHeader("Cache-control", "");
        response.setContentType(formato.getContentType());
        response.setHeader("Content-Disposition", "attachment;filename=" + nomeFile + "." + formato.getEstensione());
    }
    
    public static JasperReportBuilder createJasperReportBuilder(String templateName, HttpServletRequest request) throws Exception {
    	
    	JasperReportBuilder jrb = null;
    	
    	try {
    		String path = componiPath(templateName, request);
    		File file = new File(path);
        	
    		jrb =  report();
    		jrb.setTemplate(template())
    			.setTemplateDesign(file);
    	} catch(Exception e) {
    		e.printStackTrace();
    		throw e;
    	}

		return jrb;
    }
    
    public static JasperReportBuilder createJasperReportBuilder() {
		
		JasperReportBuilder jrb =  report();
		jrb.setTemplate(template());
		
		return jrb;
    }

}
