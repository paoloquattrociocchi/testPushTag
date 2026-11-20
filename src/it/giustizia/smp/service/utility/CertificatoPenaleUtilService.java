package it.giustizia.smp.service.utility;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.CertificatoPenaleForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.utility.Constants.AuthRichiestaCertificatoPenale;

@Service
public class CertificatoPenaleUtilService extends AttoUtilService {
	
	public void predisponiDettaglioCertificatoPenale(long idSoggetto, long idVersione, AttoForm attoForm, BindingResult result, HttpServletRequest request,  ModelMap modelMap) {
		int idUffOwn = recuperaIdUfficioUtente();
		String codUtente = recuperaIdCodiceUtente();
		ProcedimentoForm procedimento = recuperaProcedimento();
		if (attoForm.getCodAtto()!=0l)
			attoForm = caricaAttoInSessione(idUffOwn, attoForm.getCodAtto(), codUtente, procedimento.getIdProcedimento(), request, attoForm);
		// Verifica se è richiesta l'autenticazione
		if (request.getSession().getAttribute(AuthRichiestaCertificatoPenale.AUTH_USERNAME)!=null && ((String)request.getSession().getAttribute(AuthRichiestaCertificatoPenale.AUTH_USERNAME)).length()>0)
			// Se già ho eseguito l'autenticazione con successo imposto evito di richiederla
			modelMap.addAttribute("isRequireAuthentication", "FALSE");
		else
			modelMap.addAttribute("isRequireAuthentication", "TRUE");
		
		//Recupero i dati del soggetto
		SoggettoForm soggettoForm = procedimentoService.caricaValoriDettaglioSoggettoFisico(idSoggetto,idVersione,idUffOwn);
		soggettoForm.setIdProcedimento(attoForm.getIdProcedimento());
		soggettoForm.setIdAtto(attoForm.getIdAtto());
		
		//Imposto il soggetto nell'atto form
		attoForm.setSoggetti(new ArrayList<SoggettoForm>());
		attoForm.getSoggetti().add(soggettoForm);
		modelMap.addAttribute("soggettoForm", soggettoForm);
		modelMap.addAttribute("attoForm", attoForm);
	}
	
	/***
	 * Esegue il render del dettagli della scheda di richiesta del certificato penale
	 * @param attoForm
	 * @param result
	 * @param request
	 * @param modelMap
	 * @return
	 */
	public String certificatoPenaleDettaglioVisualizza(AttoForm attoForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {
		ProcedimentoForm procedimento = recuperaProcedimento();
		int idUffOwn = recuperaIdUfficioUtente();
		CertificatoPenaleForm certificatoPenaleForm =  attoService.getCertificatoPenaleByIdAtto(procedimento.getIdProcedimento(), attoForm.getCodAtto(), idUffOwn);
		predisponiDettaglioCertificatoPenale(certificatoPenaleForm.getIdSoggetto(),certificatoPenaleForm.getIdVersioneSoggetto(), attoForm, result, request, modelMap);
		modelMap.addAttribute("isInviata", true); // Imposto segnalazione presenza omonimi
		
		addBreadcrumbs();
		return "/procedimento/atto/certificatoPenale";
	}
}
