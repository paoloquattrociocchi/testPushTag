package it.giustizia.smp.controller.procedimento;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.giustizia.smp.controller.BreadcrumbsController;
import it.giustizia.smp.scheduler.DocumentaleJob;
import it.giustizia.smp.scheduler.InvioCassazioneJob;

@Controller
public class SchedulerController extends BreadcrumbsController {

	@Autowired
	private InvioCassazioneJob icj;
	@Autowired
	private DocumentaleJob docJ;

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
	
}
