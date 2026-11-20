package it.giustizia.smp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import it.giustizia.smp.controller.procedimento.ApriController;
import it.giustizia.smp.controller.procedimento.NotificheController;
import it.giustizia.smp.form.NotificaAttoForm;
import it.giustizia.smp.form.NotificheEComunicazioniForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.model.VNotifiche;
import it.giustizia.smp.service.NotificheService;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.validation.NotificheEComunicazioniValidation;

/***
 * Controller delle voce di menu notifiche e comunicazioni
 * @author m.prisco
 *
 */
@Controller
public class NotificheEComunicazioniController  extends BreadcrumbsController {

	
	@Autowired
	private NotificheService notificheService;
	
	@Autowired
	private ApriController apriController;
	
	@Autowired
	private NotificheController notificheController;
	
	@Autowired
	private NotificheEComunicazioniValidation  notificheEComunicazioniValidation;
	
	/***
	 * Lista delle statistiche disponibili per il tipo ufficio connesso
	 * @param statisticheForm
	 * @param result
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = {"notifiche/notifiche.htm"}, method = RequestMethod.POST)
    public String elencoPost(@Valid  NotificheEComunicazioniForm notificheEComunicazioniForm,BindingResult result, HttpServletRequest request, ModelMap modelMap) {
		notificheEComunicazioniValidation.validateFiltri(notificheEComunicazioniForm, result, request);
		if (!result.hasErrors()) {
			int idTipoUff = recuperaIdTipoUfficio();
			int idUff = recuperaIdUfficioUtente();
			List<VNotifiche> listaNotifiche = notificheService.getListaNotifichePerData(idUff, idTipoUff,notificheEComunicazioniForm);
			notificheEComunicazioniForm.setListaNotifiche(listaNotifiche);
			modelMap.addAttribute("listaNotifiche",listaNotifiche);
			modelMap.addAttribute("valoriComboTipoDestinatario", notificheService.getListaTipoRuoloDesinatario());
			
		}
		return "/notifiche/elencoNotifiche";
	}
	
	@RequestMapping(value = {"/notifiche.htm","notifiche/notifiche.htm"}, method = RequestMethod.GET) 
	public String elenco(NotificheEComunicazioniForm notificheEComunicazioniForm,BindingResult result, HttpServletRequest request, ModelMap modelMap) {
		addBreadcrumbs("GET", "/notifiche/notifiche.htm", null);
		return elencoPost(notificheEComunicazioniForm, result, request, modelMap);
	}
    
	@RequestMapping(value = {"/notifiche.htm","notifiche/notifiche.htm"}, method = RequestMethod.POST, params = { "Dettaglio" })
    public ModelAndView vaiAlProcedimento(@Valid  NotificheEComunicazioniForm notificheEComunicazioniForm,BindingResult result, HttpServletRequest request, ModelMap modelMap) {
		
		if (notificheEComunicazioniForm.getAnnoProcedimentoSel()== null) {
			super.setMessage(Constants.MESSAGE, "notificheEComunicazioni.selezionaNotifica", null, false);
			elencoPost(notificheEComunicazioniForm, result, request, modelMap);
			return new ModelAndView("notifiche/elencoNotifiche", "notificheEComunicazioniForm", notificheEComunicazioniForm);
		} else {
			ProcedimentoForm procedimento = new ProcedimentoForm();
			procedimento.setAnno(notificheEComunicazioniForm.getAnnoProcedimentoSel());
			procedimento.setNumero(notificheEComunicazioniForm.getNumProcedimentoSel());
			request.getSession().setAttribute(Constants.TIPO_FASCICOLO, "1");
			return apriController.dettaglioProcedimento(procedimento, result, "/procedimento/apri/ricercaPuntuale", request); //"/procedimento/apri/ricercaPuntuale"
		}
	}
	
	@RequestMapping(value = {"/notifiche.htm","notifiche/notifiche.htm"}, method = RequestMethod.POST, params = { "ListaNotifiche" })
    public ModelAndView listaDelleNotifiche(@Valid  NotificheEComunicazioniForm notificheEComunicazioniForm,BindingResult result, HttpServletRequest request, ModelMap modelMap) {
		
		if (notificheEComunicazioniForm.getAnnoProcedimentoSel()== null) {
			super.setMessage(Constants.MESSAGE, "notificheEComunicazioni.selezionaNotifica", null, false);
			elencoPost(notificheEComunicazioniForm, result, request, modelMap);
			return new ModelAndView("notifiche/elencoNotifiche", "notificheEComunicazioniForm", notificheEComunicazioniForm);
		} else {
			ProcedimentoForm procedimento = new ProcedimentoForm();
			procedimento.setAnno(notificheEComunicazioniForm.getAnnoProcedimentoSel());
			procedimento.setNumero(notificheEComunicazioniForm.getNumProcedimentoSel());
			request.getSession().setAttribute(Constants.TIPO_FASCICOLO, "1");
			apriController.dettaglioProcedimento(procedimento, result, "/procedimento/apri/ricercaPuntuale", request,false); //"/procedimento/apri/ricercaPuntuale"
			NotificaAttoForm attoForm = new NotificaAttoForm();
			attoForm.setCodAtto(notificheEComunicazioniForm.getCodAttoSel());
			return notificheController.apriListaNotifica(attoForm, result, request, modelMap);
		}
	}
	
	
}
