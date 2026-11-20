package it.giustizia.smp.controller;

import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.utility.report.ReportUtility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EsempioStampa {

    @RequestMapping(value = "/prova.htm", method = RequestMethod.POST, params = { "Invia" })
    public ModelAndView invia(ProcedimentoForm form, BindingResult result, HttpServletRequest request, HttpServletResponse response) {

        /*
         * Creo una lista (non necessariamente parametrizzata, eventualmente con
         * altri oggetti innestati)
         * che racchiude i dati che voglio passare al report. Da notare che nel
         * report devo conoscere e specificare il nome dei campi che ho negli
         * oggetti presenti
         * nella lista: in questo caso "id" e "descrizione".
         */
        List<ComboForm> dati = new ArrayList<ComboForm>();
        dati.add(new ComboForm("Sollecito", "Michele"));
        dati.add(new ComboForm("Cesarini", "Fabiola"));
    	/* 
    	 * Creo un HashMap che racchiude i parametri che voglio passare al report. 
    	 * In questa sezione imposto anche i parametri che servono agli eventuali subreport
		 */
    	Map<String, Object> parametri = new HashMap<String, Object>();
    	parametri.put("Azienda", new ComboForm("1","Almaviva"));
    	parametri.put("Sede", new ComboForm("6","Casal Boccone"));
        /*
         * Scelgo il nome con cui salvare il file, senza specificare
         * l'estensione.
         */
        String nomeFile = "Report di prova";
        /*
         * Creo un oggetto di tipo ReportUtility.Formato che indica la mia
         * preferenza riguardo il formato in cui salvare il report.
         */
        ReportUtility.Formato formato = ReportUtility.Formato.DOCX;
        /*
         * Indico il nome del file .jrxml che rappresenta il template del report
         * da riempire con i dati. Da notare che il file si trova nella cartella
         * "template" dentro "WebContent" e, in questo caso, il file si chiama
         * "ReportProva.jrxml".
         */
        String templateName = "ReportProva";
        /*
         * Mi limito a invocare il metodo statico "stampa" della classe
         * "ReportUtility", passandogli in input i dati generati. Da notare che
         * il metodo lancia una IOException qualora non riesce a trovare il
         * template o non abbia permesso di scrittura in quella cartella.
         */
        try {
            ReportUtility.stampa(nomeFile, formato, templateName, dati, parametri, request, response);
        } catch (IOException exc) {
            exc.printStackTrace();
        }
        return new ModelAndView("prova", "prova", form);
    }
}
