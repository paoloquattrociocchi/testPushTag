package it.giustizia.smp.init;

import it.giustizia.smp.dao.StaticListLoader;
import it.giustizia.smp.service.CommonService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Classe per la gestione del context ed operazioni di avvio
 * 
 * @author Almaviva SpA
 */
public class SmpListener implements ServletContextListener {

    static ApplicationContext context;

    public static ApplicationContext getContext() {

        return SmpListener.context;
    }

    public void contextDestroyed(ServletContextEvent event) {

        event.getServletContext();
    }

    public void contextInitialized(ServletContextEvent event) {

        ServletContext sc = event.getServletContext();
        ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
        CommonService commonService = (CommonService) ctx.getBean("commonService");
        
		ReloadableResourceBundleMessageSource configParameterSource = (ReloadableResourceBundleMessageSource) ctx.getBean("configParameterSource");
		StaticListLoader.setConfigParameterSource(configParameterSource);
		
        StaticListLoader.setCommonService(commonService);
        caricaListeStatiche(sc);
        SmpListener.context = ctx;
    }

    private static void caricaListeStatiche(ServletContext sc) {

        sc.setAttribute("tipiMisuraTipoT", StaticListLoader.getTipiMisuraTipoT());
        sc.setAttribute("tipiPrevenzione", StaticListLoader.getTipiPrevenzione());
        sc.setAttribute("tipiMisuraTipoP", StaticListLoader.getTipiMisuraTipoP());
        sc.setAttribute("tipiMisuraTipoAP", StaticListLoader.getTipiMisuraTipoAP());
        sc.setAttribute("tipiInteresse", StaticListLoader.getTipiInteresse());
        sc.setAttribute("settoriAttivita", StaticListLoader.getSettoriAttivita());
        sc.setAttribute("tipiStrada", StaticListLoader.getTipiStrada());
        sc.setAttribute("tipoCatasto", StaticListLoader.getTipoCatasto());
        sc.setAttribute("tipoFondo", StaticListLoader.getTipoFondo());
        sc.setAttribute("tipoDenuncia", StaticListLoader.getTipoDenuncia());
        sc.setAttribute("tipoOccupanti", StaticListLoader.getTipoOccupanti());
        sc.setAttribute("stati", StaticListLoader.getStati());
        sc.setAttribute("statiPiva", StaticListLoader.getStatiPiva());
        sc.setAttribute("statiNascita", StaticListLoader.getStati());
        sc.setAttribute("statiMorte", StaticListLoader.getStati());
        sc.setAttribute("statiResidenza", StaticListLoader.getStati());
        sc.setAttribute("statiDomicilio", StaticListLoader.getStati());
        sc.setAttribute("statiDimora", StaticListLoader.getStati());
        sc.setAttribute("statiDomicilioEletto", StaticListLoader.getStati());
        sc.setAttribute("tipologie", StaticListLoader.getTipiBene());
        sc.setAttribute("statiProcedimento", StaticListLoader.getStatiProcedimento());
        sc.setAttribute("tipiProcedimento", StaticListLoader.getTipiProcedimento());
        sc.setAttribute("tipiAtto", StaticListLoader.getTipiAtto());
        sc.setAttribute("tipiNota", StaticListLoader.getTipiNota());
        sc.setAttribute("professioni", StaticListLoader.getProfessioni());
        sc.setAttribute("condizioniGiuridiche", StaticListLoader.getCondizioniGiuridiche());
        sc.setAttribute("tipiSocieta", StaticListLoader.getTipiSocieta());
        sc.setAttribute("categorieCatastali", StaticListLoader.getCategorieCatastali());
        sc.setAttribute("istitutiDetenzione", StaticListLoader.getIstitutiDetenzione());
        sc.setAttribute("vincoli", StaticListLoader.getVincoli());
        sc.setAttribute("tipiTenore", StaticListLoader.getTipiTenore());
        sc.setAttribute("disponibilita", StaticListLoader.getDisponibilita());
        sc.setAttribute("tipiSostituzioneMagistrato", StaticListLoader.getTipiSostituzioneMagistrato());
        sc.setAttribute("softwareVersion", StaticListLoader.getSoftwareVersion());
        sc.setAttribute("tipoRuoliMagistrati", StaticListLoader.getTipoRuoliMagistrati());
        sc.setAttribute("tipoModalitaPervenimento", StaticListLoader.getTipoModalitaPervenimento());
    }
}
