package it.giustizia.smp.service.utility;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import it.giustizia.smp.controller.BreadcrumbsController;
import it.giustizia.smp.dto.KeyMessageDto;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.MagistratoForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.service.AttoService;
import it.giustizia.smp.service.SoggettoService;
import it.giustizia.smp.service.utility.component.AttoUtilComponent;
import it.giustizia.smp.utility.Constants;

@Service
public class AttoUtilService extends BreadcrumbsController {

	@Autowired
	protected AttoService attoService;
	@Autowired
	protected SoggettoService soggettoService;
	@Autowired
	protected AttoUtilComponent attoUtilComponent;
	
	public AttoForm caricaAttoInSessione(int idUffOwn, long idAtto, String codiceUtente, long idProcedimento, HttpServletRequest request,
			AttoForm attoForm) {
		return attoUtilComponent.caricaAttoInSessione(idUffOwn, idAtto, codiceUtente, idProcedimento, request, attoForm);
	}
	
	public void caricaDatiDaAttoInSessione(AttoForm attoForm, ModelMap modelMap) {
		attoUtilComponent.caricaDatiDaAttoInSessione(attoForm, modelMap);
	}
	
	/**
	 * metodo privato per la copertina atto Versionamento
	 * @param attoForm
	 * @param idUfficio
	 * @param currentProcedimento
	 * @param modelMap
	 * @param result
	 * @return String
	 */
	public String copertinaAttoVersionamento(AttoForm attoForm, BindingResult result, HttpServletRequest request, ModelMap modelMap,int idUfficio){
		SoggettoForm soggetto = new SoggettoForm();
		SoggettoForm soggettoReturn = new SoggettoForm();
		String codUtente = recuperaIdCodiceUtente();
		soggetto = attoService.getTRelProcSogintVerByAtto(attoForm.getCodAtto(),idUfficio);
		soggetto.setIdUfficio((Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE));
		soggetto.setIdProcedimento(((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento());
		if(Constants.SOGGETTO_FISICO.equalsIgnoreCase(soggetto.getTipoSoggetto())){
			 soggettoReturn = soggettoService.caricaValoriDettaglioSoggFisVerCopertinaAtto(soggetto);
			 soggettoReturn.setTipoSoggetto(Constants.SOGGETTO_FISICO);
		}else if(Constants.SOGGETTO_GIURIDICO.equalsIgnoreCase(soggetto.getTipoSoggetto())){ 
			 soggettoReturn = soggettoService.caricaValoriDettaglioSoggGiuVerCopertinaAtto(soggetto);
			 soggettoReturn.setTipoSoggetto(Constants.SOGGETTO_GIURIDICO);
		}
		caricaAttoInSessione(idUfficio, attoForm.getCodAtto(), codUtente, soggetto.getIdProcedimento(), request, attoForm);
	    int ruoloMagistrato = 5;
	    MagistratoForm magistratoForm = attoService.getMagistratoPerAtto(idUfficio, attoForm.getCodAtto(), ruoloMagistrato);
	    ComboForm magistrato = new ComboForm();
	    if(magistratoForm != null && magistratoForm.getNome()!= null)
			magistrato.setDescrizione(magistratoForm.getNome() + " "+ magistratoForm.getCognome());
	    
	    soggettoReturn.setMagistrato(magistrato);
	    attoForm.setSoggetto(soggettoReturn);    
		inizializzaDocumento(attoForm, request);
		// INIZIA GESTIONE DOCUMENTALE
		gestionePiattaformaDocumentale(attoForm, request, idUfficio, result);
		// FINE GESTIONE DOCUMENTALE
		modelMap.addAttribute("attoForm", attoForm);
		return "/procedimento/atto/copertinaAttoVersionamento";	
	}
	
	public boolean controllaCollegioInvioAtto(String printMethodName, BindingResult result) {
		boolean errore = false;
		ProcedimentoForm procedimento = recuperaProcedimento();
		AttoForm atto = recuperaAtto();
		// AttoForm attoFormSession = ((AttoForm)
		// (request.getSession().getAttribute(Constants.ATTO)));
		long idAtto = atto.getCodAtto();
		String tipoAtto = atto.getTipoAtto().getId();
		String descUtente = recuperaIdCodiceUtente().toString();
		int idUfficio = recuperaIdUfficioUtente();
		if (tipoAtto.equals(Constants.TIPO_ATTO_DECRETO) || tipoAtto.equals(Constants.TIPO_ATTO_DECRETO_II_GRADO)) {
			KeyMessageDto ritornoDb = attoService.controllaCollegioAttoInvio(procedimento.getIdProcedimento(), idAtto, descUtente, idUfficio,
					printMethodName);
			if (ritornoDb.isError()) {
				errore = true;
				super.setMessage(Constants.MESSAGE, "messaggio.gestito.db", ritornoDb.getMessage(), false);
				result.reject(Constants.MESSAGE);				
			}
		}
		return errore;
	}
	
	public String copertinaDecretoApplicazioneProvvisoria(AttoForm attoForm,List<SoggettoForm> soggetti, ModelMap modelMap, HttpServletRequest request) {
		return attoUtilComponent.copertinaDecretoApplicazioneProvvisoria(attoForm, soggetti, modelMap, request);
	}	
}
