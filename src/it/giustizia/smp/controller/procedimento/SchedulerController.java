package it.giustizia.smp.controller.procedimento;

import it.giustizia.smp.controller.BreadcrumbsController;
import it.giustizia.smp.scheduler.AnbscJob;
import it.giustizia.smp.scheduler.DocumentaleJob;
import it.giustizia.smp.scheduler.InvioCassazioneJob;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SchedulerController extends BreadcrumbsController {

	@Autowired
	private InvioCassazioneJob icj;
	@Autowired
	private DocumentaleJob docJ;
	@Autowired
	private AnbscJob anJ;

	@RequestMapping(value = "/scheduler/invioCassazioneJob.htm", method = RequestMethod.GET)
	public String invioCassazioneJob(ModelMap modelMap, HttpServletRequest request) {

		icj.scheduledMethod();

		return "/scheduler/invioCassazioneJob";
	}
	
	@RequestMapping(value = "/scheduler/invioDocumentaleJob.htm", method = RequestMethod.GET)
	public String documentaleJob(ModelMap modelMap, HttpServletRequest request) {

		docJ.scheduledMethod();

		return "/scheduler/invioDocumentaleJob";
	}
	
	@RequestMapping(value = "/scheduler/invioAnbscJob.htm", method = RequestMethod.GET)
	public String anbscJob(ModelMap modelMap, HttpServletRequest request) {

		anJ.scheduledMethod();

		return "/scheduler/invioAnbscJob";
	}

}
