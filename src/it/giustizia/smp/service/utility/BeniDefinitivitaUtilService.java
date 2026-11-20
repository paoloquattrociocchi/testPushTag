package it.giustizia.smp.service.utility;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import it.giustizia.smp.controller.BreadcrumbsController;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.BeneSoggettoForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.utility.Constants;

@Service
public class BeniDefinitivitaUtilService extends BreadcrumbsController {

	public void beniDefinitiva(HttpServletRequest request, ModelMap modelMap, BindingResult result, String nomeMetodo) {

		int idUffOwn = recuperaIdUfficioUtente();
		String descUtente = recuperaIdCodiceUtente();
		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		long idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
		List<BeneSoggettoForm> risultatiRicerca = procedimentoService.listaSoggettiMisPatrDefinitivita(idProcedimento, idAtto, idUffOwn,
				descUtente, "I_GRADO", super.printMethodName());
		modelMap.addAttribute("risultatiRicerca", risultatiRicerca);
	}

	public void beniDefinitivaFiltrati(HttpServletRequest request, ModelMap modelMap, BindingResult result, String nomeMetodo, long idSoggettoDiretto, long idSoggettoTitolare, long idTipologia, long idCategoria, long idSottocategoria) {

		int idUffOwn = recuperaIdUfficioUtente();
		String descUtente = recuperaIdCodiceUtente();
		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		long idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
		List<BeneSoggettoForm> risultatiRicerca = procedimentoService.listaSoggettiMisPatrDefinitivitaFiltrati(idProcedimento, idAtto, idUffOwn,
				descUtente, "I_GRADO", super.printMethodName(), idSoggettoDiretto, idSoggettoTitolare, idTipologia, idCategoria, idSottocategoria);
		modelMap.addAttribute("risultatiRicerca", risultatiRicerca);
	}

	public void beniDefinitivaIIGrado(HttpServletRequest request, ModelMap modelMap, BindingResult result, String nomeMetodo) {

		int idUffOwn = recuperaIdUfficioUtente();
		String descUtente = recuperaIdCodiceUtente();
		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		long idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
		List<BeneSoggettoForm> risultatiRicerca = procedimentoService.listaSoggettiMisPatrDefinitivita(idProcedimento, idAtto, idUffOwn,
				descUtente, "II_GRADO", super.printMethodName());
		modelMap.addAttribute("risultatiRicerca", risultatiRicerca);
	}

	public void beniDefinitivaIIGradoFiltrati(HttpServletRequest request, ModelMap modelMap, BindingResult result, String nomeMetodo, long idSoggettoDiretto, long idSoggettoTitolare, long idTipologia, long idCategoria, long idSottocategoria) {

		int idUffOwn = recuperaIdUfficioUtente();
		String descUtente = recuperaIdCodiceUtente();
		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		long idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
		List<BeneSoggettoForm> risultatiRicerca = procedimentoService.listaSoggettiMisPatrDefinitivitaFiltrati(idProcedimento, idAtto, idUffOwn,
				descUtente, "II_GRADO", super.printMethodName(), idSoggettoDiretto, idSoggettoTitolare, idTipologia, idCategoria, idSottocategoria);
		modelMap.addAttribute("risultatiRicerca", risultatiRicerca);
	}

	public void beniRevocaDefinitiva(HttpServletRequest request, ModelMap modelMap, BindingResult result, String nomeMetodo) {

		int idUffOwn = recuperaIdUfficioUtente();
		String descUtente = recuperaIdCodiceUtente();
		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		long idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
		// List<BeneSoggettoForm> risultatiRicerca =
		// procedimentoService.listaBeniConSoggettiAssociatiAdAttoEProcedimentoPerRevoca(idProcedimento,
		// idAtto, idUffOwn, descUtente, nomeMetodo);
		List<BeneSoggettoForm> risultatiRicerca = procedimentoService.listaSoggettiMisPatrRevocaDefinitivita(idProcedimento, idAtto, idUffOwn,
				descUtente, "I_GRADO", super.printMethodName());
		modelMap.addAttribute("risultatiRicerca", risultatiRicerca);

	}

	public void beniRevocaDefinitivaFiltrati(HttpServletRequest request, ModelMap modelMap, BindingResult result, String nomeMetodo, long idSoggettoDiretto, long idSoggettoTitolare, long idTipologia, long idCategoria, long idSottocategoria) {

		int idUffOwn = recuperaIdUfficioUtente();
		String descUtente = recuperaIdCodiceUtente();
		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		long idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
		// List<BeneSoggettoForm> risultatiRicerca =
		// procedimentoService.listaBeniConSoggettiAssociatiAdAttoEProcedimentoPerRevoca(idProcedimento,
		// idAtto, idUffOwn, descUtente, nomeMetodo);
		List<BeneSoggettoForm> risultatiRicerca = procedimentoService.listaSoggettiMisPatrRevocaDefinitivitaFiltrati(idProcedimento, idAtto, idUffOwn,
				descUtente, "I_GRADO", super.printMethodName(), idSoggettoDiretto, idSoggettoTitolare, idTipologia, idCategoria, idSottocategoria);
		modelMap.addAttribute("risultatiRicerca", risultatiRicerca);

	}
	
	public void beniRevocaDefinitivaIIGrado(HttpServletRequest request, ModelMap modelMap, BindingResult result, String nomeMetodo) {

		int idUffOwn = recuperaIdUfficioUtente();
		String descUtente = recuperaIdCodiceUtente();
		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		long idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
		List<BeneSoggettoForm> risultatiRicerca = procedimentoService.listaSoggettiMisPatrRevocaDefinitivita(idProcedimento, idAtto, idUffOwn,
				descUtente, "II_GRADO", super.printMethodName());
		modelMap.addAttribute("risultatiRicerca", risultatiRicerca);

	}

	public void beniRevocaDefinitivaIIGradoFiltrati(HttpServletRequest request, ModelMap modelMap, BindingResult result, String nomeMetodo, long idSoggettoDiretto, long idSoggettoTitolare, long idTipologia, long idCategoria, long idSottocategoria) {

		int idUffOwn = recuperaIdUfficioUtente();
		String descUtente = recuperaIdCodiceUtente();
		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		long idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
		List<BeneSoggettoForm> risultatiRicerca = procedimentoService.listaSoggettiMisPatrRevocaDefinitivitaFiltrati(idProcedimento, idAtto, idUffOwn,
				descUtente, "II_GRADO", super.printMethodName(), idSoggettoDiretto, idSoggettoTitolare, idTipologia, idCategoria, idSottocategoria);
		modelMap.addAttribute("risultatiRicerca", risultatiRicerca);

	}
}
