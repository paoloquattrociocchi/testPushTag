package it.giustizia.smp.service.utility;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import it.giustizia.smp.controller.BreadcrumbsController;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.BeneSoggettoForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.service.utility.component.SoggettiUtilComponent;
import it.giustizia.smp.utility.Constants;

@Service
public class SoggettiUtilService extends BreadcrumbsController {

	@Autowired
	private SoggettiUtilComponent soggettiUtilComponent;
	
	public void soggettiMisurePersonali(HttpServletRequest request, ModelMap modelMap) {

		int idUffOwn = recuperaIdUfficioUtente();
		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		long idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
		List<BeneSoggettoForm> risultatiRicercaSogg = procedimentoService.listaSoggettiMisuraPersonaleAssociatiAdAttoEProcedimento(idProcedimento,
				idAtto, idUffOwn);
		modelMap.addAttribute("risultatiRicercaSogg", risultatiRicercaSogg);
	}

	public void soggettiMisurePersonaliFiltrati(HttpServletRequest request, ModelMap modelMap, long idSoggettoDiretto) {

		int idUffOwn = recuperaIdUfficioUtente();
		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		long idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
		List<BeneSoggettoForm> risultatiRicercaSogg = procedimentoService.listaSoggettiMisuraPersonaleAssociatiAdAttoEProcedimentoFiltrati(idProcedimento,
				idAtto, idUffOwn, idSoggettoDiretto);
		
		modelMap.addAttribute("risultatiRicercaSogg", risultatiRicercaSogg);
	}
	
	public void soggettiMisurePersonaliRevocaDefinitivitaIIGrado(HttpServletRequest request, ModelMap modelMap) {

		int idUffOwn = recuperaIdUfficioUtente();
		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		long idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
		String codiceUtente = recuperaIdCodiceUtente();
		List<BeneSoggettoForm> risultatiRicercaSogg = procedimentoService.listaSoggettiMisuraPersonaleRevocaDefinitiva(idProcedimento, idAtto,
				idUffOwn, codiceUtente, "II_GRADO", super.printMethodName());
		modelMap.addAttribute("risultatiRicercaSogg", risultatiRicercaSogg);
	}

	public void soggettiMisurePersonaliRevocaDefinitivitaIIGradoFiltrati(HttpServletRequest request, ModelMap modelMap, long idSoggettoDiretto) {

		int idUffOwn = recuperaIdUfficioUtente();
		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		long idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
		String codiceUtente = recuperaIdCodiceUtente();
		List<BeneSoggettoForm> risultatiRicercaSogg = procedimentoService.listaSoggettiMisuraPersonaleRevocaDefinitivaFiltrati(idProcedimento, idAtto,
				idUffOwn, codiceUtente, "II_GRADO", super.printMethodName(), idSoggettoDiretto);
		modelMap.addAttribute("risultatiRicercaSogg", risultatiRicercaSogg);
	}
	
	public void soggettiMisurePersonaliRevocaDefinitivitaFiltrati(HttpServletRequest request, ModelMap modelMap, long idSoggettoDiretto) {

		int idUffOwn = recuperaIdUfficioUtente();
		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		long idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
		String codiceUtente = recuperaIdCodiceUtente();
		List<BeneSoggettoForm> risultatiRicercaSogg = procedimentoService.listaSoggettiMisuraPersonaleRevocaDefinitivaFiltrati(idProcedimento, idAtto,
				idUffOwn, codiceUtente, "I_GRADO", super.printMethodName() , idSoggettoDiretto);
		modelMap.addAttribute("risultatiRicercaSogg", risultatiRicercaSogg);
	}

	public void soggettiMisurePersonaliRevocaDefinitivita(HttpServletRequest request, ModelMap modelMap) {

		int idUffOwn = recuperaIdUfficioUtente();
		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		long idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
		String codiceUtente = recuperaIdCodiceUtente();
		List<BeneSoggettoForm> risultatiRicercaSogg = procedimentoService.listaSoggettiMisuraPersonaleRevocaDefinitiva(idProcedimento, idAtto,
				idUffOwn, codiceUtente, "I_GRADO", super.printMethodName());
		modelMap.addAttribute("risultatiRicercaSogg", risultatiRicercaSogg);
	}
	
	public ModelAndView visualizzaSoggettiDirettiInteressati(SoggettoForm soggettoForm, HttpServletRequest request) {

		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		ModelAndView mnv = new ModelAndView("/procedimento/atto/copertinaDecretoDecisorioIGrado", "soggettoForm", soggettoForm);
		List<SoggettoForm> soggetti = procedimentoService.soggettiDirettiInteressatiDelProcedimento(idProcedimento, Constants.RUOLO_DIRETTO,
				Constants.Flag.S);
		mnv.addObject("soggetti", soggetti);
		if (request.getSession().getAttribute(Constants.ATTO) != null) {
			AttoForm currentAtto = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
			mnv.addObject("attoForm", currentAtto);
		}
		return mnv;
	}
	
	public ModelAndView visualizzaSoggettiDirettiInteressatiIIGrado(SoggettoForm soggettoForm, HttpServletRequest request) {

		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		ModelAndView mnv = new ModelAndView("/procedimento/atto/copertinaDecretoDecisorioIIGrado", "soggettoForm", soggettoForm);
		List<SoggettoForm> soggetti = procedimentoService.soggettiDirettiInteressatiDelProcedimento(idProcedimento, Constants.RUOLO_DIRETTO,
				Constants.Flag.S);
		mnv.addObject("soggetti", soggetti);
		if (request.getSession().getAttribute(Constants.ATTO) != null) {
			AttoForm currentAtto = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
			mnv.addObject("attoForm", currentAtto);
		}
		return mnv;
	}
	
	public ModelAndView visualizzaSoggettiDirettiInteressatiSentenzaCassazioneCDA(SoggettoForm soggettoForm, HttpServletRequest request) {

		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		ModelAndView mnv = new ModelAndView("/procedimento/atto/copertinaSentenzaRicorsoIIGrado", "soggettoForm", soggettoForm);
		List<SoggettoForm> soggetti = procedimentoService.soggettiDirettiInteressatiDelProcedimento(idProcedimento, Constants.RUOLO_DIRETTO,
				Constants.Flag.S);
		mnv.addObject("soggetti", soggetti);
		if (request.getSession().getAttribute(Constants.ATTO) != null) {
			AttoForm currentAtto = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
			mnv.addObject("attoForm", currentAtto);
		}
		return mnv;
	}
	
	public String listaSoggettiVerSogg(BindingResult result, HttpServletRequest request, ModelMap modelMap) {
		
		ProcedimentoForm procedimento = recuperaProcedimento();

		long idProcedimento = procedimento.getIdProcedimento().longValue();
		recuperaIdUfficioUtente();

		List<SoggettoForm> listaSoggetti = procedimentoService.soggettiDirettiInteressatiDelProcedimento(idProcedimento,null,'S');

		modelMap.addAttribute("listaSoggetti", listaSoggetti);
		
		addBreadcrumbs();
		return "/procedimento/atto/nuovoVersionamentoSoggetto";
	}
	
	/**
	 * metodo che controlla se gli attributi propedeudici al versionamento sono cambiati
	 * @param soggettoForm
	 * @param soggettoOld
	 * @return boolean
	 */
	public boolean controlloSoggGiuVer(SoggettoForm soggettoForm,SoggettoForm soggettoOld) {
		
		boolean change = true;
		if(soggettoOld.getRagioneSociale().equalsIgnoreCase(soggettoForm.getRagioneSociale()))
			  if(soggettoOld.getStatoSedeLegale().getId().equals(soggettoForm.getStatoSedeLegale().getId()))	
						if(soggettoOld.getIdTipoSocieta() == soggettoForm.getIdTipoSocieta()) 
							change = false;
	
	     if(!change){
	    	 change = true;
	      //controllo per comune italiano o estero
			   	  if((soggettoOld.getDescrizioneComuneEsteroSedeLegale() != null && soggettoOld.getDescrizioneComuneEsteroSedeLegale().equalsIgnoreCase(soggettoForm.getDescrizioneComuneEsteroSedeLegale())) ||
				     (soggettoForm.getDescrizioneComuneEsteroSedeLegale() != null && soggettoForm.getDescrizioneComuneEsteroSedeLegale().equalsIgnoreCase(soggettoOld.getDescrizioneComuneEsteroSedeLegale()))||
					 (soggettoOld.getDescrizioneComuneEsteroSedeLegale() == null && soggettoForm.getDescrizioneComuneEsteroSedeLegale() == null))
					change = false;
	     }
	  
	    //controllo per la partita iva 
		if(!change){
			change = true;
			if((soggettoOld.getPartitaIva() != null && soggettoOld.getPartitaIva().equalsIgnoreCase(soggettoForm.getPartitaIva())) ||
					   (soggettoForm.getPartitaIva() != null && soggettoForm.getPartitaIva().equalsIgnoreCase(soggettoOld.getPartitaIva()))||
					   (soggettoOld.getPartitaIva() == null && soggettoForm.getPartitaIva() == null))
				change = false;
		}
	     
		return change;
	}
	

	/**
	 * metodo che controlla se gli attributi propedeudici al versionamento sono cambiati
	 * @param soggettoForm
	 * @param soggettoOld
	 * @return boolean
	 */
	public boolean controlloSoggFisVer(SoggettoForm soggettoForm,SoggettoForm soggettoOld) {
		
		boolean change = true;
		if(soggettoOld.getCognome().equalsIgnoreCase(soggettoForm.getCognome()))
			if(soggettoOld.getNome().equalsIgnoreCase(soggettoForm.getNome()))
				if(soggettoOld.getDataNascita().equals(soggettoForm.getDataNascita()))
					if(soggettoOld.getStatoNascita().getId().equals(soggettoForm.getStatoNascita().getId()))						    	
						if((soggettoOld.getCodiceFiscale() != null && soggettoOld.getCodiceFiscale().equalsIgnoreCase(soggettoForm.getCodiceFiscale())) ||
						   (soggettoForm.getCodiceFiscale() != null && soggettoForm.getCodiceFiscale().equalsIgnoreCase(soggettoOld.getCodiceFiscale()))||
						   (soggettoOld.getCodiceFiscale() == null && soggettoForm.getCodiceFiscale() == null))	
							if((soggettoOld.getCui() != null && soggettoOld.getCui().equalsIgnoreCase(soggettoForm.getCui())) ||
							   (soggettoForm.getCui() != null && soggettoForm.getCui().equalsIgnoreCase(soggettoOld.getCui()))||
							   (soggettoOld.getCui() == null && soggettoForm.getCui() == null))
									change = false;
		
	
	     if(!change){
	    	 change = true;
	      //controllo per comune italiano o estero
			if(soggettoOld.getStatoNascita().getId().equals("1")){
			   	  if((soggettoOld.getComuneNascita() != null && soggettoOld.getComuneNascita().getId() != null && soggettoOld.getComuneNascita().getId().equalsIgnoreCase(soggettoForm.getComuneNascita().getId())) ||
				     (soggettoForm.getComuneNascita() != null && soggettoForm.getComuneNascita().getId() != null && soggettoForm.getComuneNascita().getId().equalsIgnoreCase(soggettoOld.getComuneNascita().getId()))||
					 (soggettoOld.getComuneNascita().getId() == null && soggettoForm.getComuneNascita().getId() == null))
					change = false;
			 } else if((soggettoOld.getDescrizioneComuneEsteroNascita() != null && soggettoOld.getDescrizioneComuneEsteroNascita().equalsIgnoreCase(soggettoForm.getDescrizioneComuneEsteroNascita())) ||
			   (soggettoForm.getDescrizioneComuneEsteroNascita() != null && soggettoForm.getDescrizioneComuneEsteroNascita().equalsIgnoreCase(soggettoOld.getDescrizioneComuneEsteroNascita()))||
			   (soggettoOld.getDescrizioneComuneEsteroNascita() == null && soggettoForm.getDescrizioneComuneEsteroNascita() == null))
				change = false;		
		 }
		return change;
	}
	
	public List<SoggettoForm> filtraSoloSoggettiMisPers(List<SoggettoForm> soggettiDirettiPers, long idProcedimento, long idAtto, int idUffOwn) {
		return soggettiUtilComponent.filtraSoloSoggettiMisPers(soggettiDirettiPers, idProcedimento, idAtto, idUffOwn);
	}

	public List<SoggettoForm> filtraSoloSoggettiMisPatr(List<SoggettoForm> soggettiDiretti, long idProcedimento, long idAtto, int idUffOwn) {
		return soggettiUtilComponent.filtraSoloSoggettiMisPatr(soggettiDiretti, idProcedimento, idAtto, idUffOwn);
	}
}
