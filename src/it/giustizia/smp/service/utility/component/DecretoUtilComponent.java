package it.giustizia.smp.service.utility.component;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import it.giustizia.smp.controller.BreadcrumbsController;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.MisuraForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.service.MisuraService;
import it.giustizia.smp.utility.Constants;

@Service
public class DecretoUtilComponent extends BreadcrumbsController {

	@Autowired
	private MisuraService misuraService;
	@Autowired
	private SoggettiUtilComponent soggettiUtilComponent;
	@Autowired
	private AttoUtilComponent attoUtilComponent;
	
	/**
	 * getMisuraDecretoApplicazioneProvvisoria
	 * @param soggettoForm
	 * @param attoForm
	 * @param modelMap
	 * @return
	 */
	public String getMisuraDecretoApplicazioneProvvisoria(SoggettoForm soggettoForm, AttoForm attoForm , ModelMap modelMap,boolean ricarica, HttpServletRequest request){		
		ProcedimentoForm procedimento = recuperaProcedimento();
		recuperaIdUfficioUtente();
        Integer idUfficio = recuperaIdUfficioUtente();     
		modelMap.addAttribute("soggettoForm", soggettoForm);
		String codiceUtente = recuperaIdCodiceUtente();
		Long idProcedimento = procedimento.getIdProcedimento();
		long idAtto = 0l;
		if (attoForm != null && attoForm.getCodAtto() > 0)
			idAtto = attoForm.getCodAtto();
		else
			idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();

		Long idProcedimentoCda = attoForm != null ? attoForm.getIdProcedimentoCda() : null;
		// vado a controllare se c'e' nell'atto
		if (idProcedimentoCda == null || idProcedimentoCda == 0) {
			attoForm.setIdProcedimentoCda(((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getIdProcedimentoCda());
			idProcedimentoCda = attoForm.getIdProcedimentoCda();
			AttoForm atto = recuperaAtto();
			if (atto != null) {
				atto.setIdProcedimentoCda(null);
				request.getSession().setAttribute("atto", atto);
			}
		}
		if (attoForm.getIdProcedimentoCda() != null && attoForm.getIdProcedimentoCda() > 0) {
			idUfficio = Integer.parseInt(commonService.getUfficiCompetenza(idProcedimento, 19L).get(0).getId());
			idProcedimento = attoForm.getIdProcedimentoCda();
		}
		attoForm = attoUtilComponent.caricaAttoInSessione(idUfficio, idAtto, codiceUtente, idProcedimento, request, attoForm);
		if (idProcedimentoCda != null && idProcedimentoCda > 0)
			attoForm.setIdProcedimentoCda(idProcedimentoCda);
		
		soggettoForm.setIdUfficio(idUfficio);
		if (!ricarica){
			soggettoForm.setMisura(new MisuraForm());	
			soggettoForm.setIdProcedimento(idProcedimento);
			soggettoForm.setIdAtto(idAtto);

			soggettoForm = misuraService.caricaMisuraPersonale(soggettoForm);
			
			soggettoForm = procedimentoService.caricaValoriDettaglioSoggettoFisicoProcedimento(soggettoForm);
			soggettoForm.setTipoSoggetto(Constants.SOGGETTO_FISICO);
			
	        soggettoForm.setTipologiaPrevenzioneDb(soggettoForm.getTipologiaPrevenzione());
	        //Carico nel ModelAndView le Normative
		}
        attoForm.setSoggetto(soggettoForm);
        modelMap.addAttribute("attoForm", attoForm);
        soggettiUtilComponent.caricaNormative(soggettoForm, null, modelMap);
        List<ComboForm> list = commonService.listaPrescrizioniMisuraPersonale();
		modelMap.addAttribute("listaPrescrizioni", list);
		return "procedimento/atto/decretoApplicazioneProvvisoria";		
	}
}
