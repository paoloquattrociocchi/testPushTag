package it.giustizia.smp.controller.cameralini;

import it.giustizia.smp.controller.MenuController;
import it.giustizia.smp.dto.pg_cameralini.IntestatarioDto;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.form.cameralini.BaseForm;
import it.giustizia.smp.service.ProcedimentoService;
import it.giustizia.smp.utility.Constants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CopertinaController extends RicercaBaseController 
{
	@Autowired
	private ProcedimentoService		procedimentoService;
	
	@RequestMapping( value = "/procedimento/nuovo/cameralini/copertinaAmministratore.htm" , method = RequestMethod.GET)
	
	public ModelAndView caricaDettaglio(BindingResult result, ModelMap modelMap, BaseForm form, HttpServletRequest request)
	{
		addBreadcrumbs();
		return caricaDettaglioProcedimento(result, modelMap, form, request);
	}
	
	public ModelAndView caricaDettaglioProcedimento(BindingResult result, ModelMap modelMap, BaseForm form, HttpServletRequest request)
	{
		MenuController.caricaMenu( Constants.ID_PER_MENU_DETTAGLIO_AGGRAVAMENTO, request );
		
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
		
		Long idProcPadre  = (Long) request.getSession().getAttribute("idProcPadre");
		if (idProcPadre != null)
			form.setProcedPadre(procedimentoServiceCam.getDettaglioProcedimento(idProcPadre.longValue()));	
		
		Long idProcFiglio = (Long) request.getSession().getAttribute("idProcFiglio");
		if (idProcFiglio != null)
			form.setProcedCameralini(procedimentoServiceCam.getDettaglioProcedimentoCam(idProcFiglio.longValue()));
		
		List<IntestatarioDto> listaIntestatari = procedimentoServiceCam.getIntestatari(idUfficioUtente.intValue(), form.getProcedCameralini().getIdProcedimento());
		List<SoggettoForm> listaSoggetti = new ArrayList<SoggettoForm>();
		if(form.getProcedCameralini().getTipoProcedimento().getId().equals(""+Constants.TIPOPROC_CORREZIONE_ERRORI_CAM))
		{	
			
			Iterator<IntestatarioDto> iter = listaIntestatari.iterator();
			while (iter.hasNext()) {
				IntestatarioDto currentDto = iter.next();
				SoggettoForm sForm = procedimentoServiceCam.getSogIntById(currentDto.getCODI_FK_ID_SOGG_INT().longValue(), currentDto.getCODI_FK_ID_UFF_OWNER().longValue(), idProcPadre.longValue());
				listaSoggetti.add(sForm);
			}
		}
				
		ModelAndView mnv = new ModelAndView( "/cameralini/copertinaCameralini", "form", form );
		modelMap.addAttribute("numeriRegistro", procedimentoService.getNumeriRegistro(idProcFiglio));
		modelMap.addAttribute("numeriRegistroAccertamento", procedimentoService.getNumeriRegistroAccertamento(idProcFiglio));
		modelMap.addAttribute("listaIntestatari", listaIntestatari);
		modelMap.addAttribute("listaSoggetti", listaSoggetti);
		cleanBreadcrumb(request.getSession());
		addBreadcrumbs();
		return mnv;
	}
	
	@RequestMapping(value = "/procedimento/nuovo/cameralini/copertinaCameralini.htm", method = RequestMethod.POST, params = { "annullaProcedimento" })
	public ModelAndView annullaProcedimento(BaseForm procedimento, BindingResult result, ModelMap modelMap, HttpServletRequest request) {
		int idUffOwn = recuperaIdUfficioUtente();
		if (procedimentoService.hasAttiInLavorazioni(recuperaProcedimento().getIdProcedimento(), idUffOwn)) {
			// Se esistono atti in lavorazione diversi dalla proposta blocco l'utente con un messaggio
			super.setMessage(Constants.MESSAGE,	"archiviazione.errore.presenzaattiinlavorazione", null, false);
		} else {
			super.setMessage(Constants.MESSAGE, "procedimento.canferma.annulla", " ", true);
			request.setAttribute("pulsanteAnnulla", "si");
		}
		return caricaDettaglio(result, modelMap, procedimento,request);
	}

	@RequestMapping(value = "/procedimento/nuovo/cameralini/copertinaCameralini.htm", method = RequestMethod.POST, params = { "confermaAnnullaProcedimento" })
	public ModelAndView confermaAnnullaProcedimento(BaseForm procedimento, BindingResult result, ModelMap modelMap, HttpServletRequest request) {

		// super.setMessage(Constants.SUCCESS_MESSAGE, "procedimento.canferma.annulla", " ", true);
		ProcedimentoForm proced = recuperaProcedimento();
		long idProcedimento = proced.getIdProcedimento();
		Integer idUfficioUtente = recuperaIdUfficioUtente();
		String codUtente = recuperaIdCodiceUtente();
		String tipoUtente = (String) request.getSession().getAttribute(Constants.TIPO_UTENTE);
		super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation", "ANNULLAMENTO", true);
		procedimentoService.annullaStatoDelProcedimento(idProcedimento, idUfficioUtente, codUtente, tipoUtente, 0);

		return caricaDettaglio(result, modelMap, procedimento, request);
	}
		
	
	@RequestMapping(value = "/procedimento/nuovo/cameralini/copertinaCameralini.htm", method = RequestMethod.POST, params = { "indietro" })
	public ModelAndView indietro(BaseForm procedimento, BindingResult result, ModelMap modelMap, HttpServletRequest request) {
		return caricaDettaglio(result, modelMap, procedimento, request);
	}

	
}