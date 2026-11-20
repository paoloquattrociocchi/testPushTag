package it.giustizia.smp.utility.statistiche;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.query.JRQueryExecuterFactory;
import net.sf.jasperreports.engine.util.JRProperties;

/***
 * Factory delle statistiche jasper in base al nome 
 * @author m.prisco
 *
 */
public class StatisticheFactory {

	public static JasperReport createJasperReport(String nomeStatistica, HttpServletRequest request) throws Exception {
    	JasperReport jasperReport = null;
    	String path = componiPath(nomeStatistica, request);
    	//String path = componiPath("ProvaOracle1", request);
    	JRProperties.setProperty( JRQueryExecuterFactory.QUERY_EXECUTER_FACTORY_PREFIX+"plsql"
               				  ,"com.jaspersoft.jrx.query.PlSqlQueryExecuterFactory");
    	jasperReport = JasperCompileManager.compileReport(path);	    
    	jasperReport.setProperty( "net.sf.jasperreports.query.executer.factory.plsql"
    							  ,"com.jaspersoft.jrx.query.PlSqlQueryExecuterFactory");
    	
    	return jasperReport;
    }
	
	private static String componiPath(String templateName, HttpServletRequest request) {
        String path = request.getSession().getServletContext().getRealPath("statistiche") + File.separator + templateName + ".jrxml";
        return path;
    }
}
