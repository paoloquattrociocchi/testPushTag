package it.giustizia.smp.service.utility.component;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import it.giustizia.smp.controller.BreadcrumbsController;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.utility.Constants;

@Service
public class ProcuraUdienzaUtilComponent extends BreadcrumbsController {
	
	public void caricaProcuraInUdienzaProcuraGeneraleProcuraProponente(ModelMap modelMap) {
		long idUffOwn = recuperaIdUfficioUtente();
		ProcedimentoForm procedimento = recuperaProcedimento();
		List<ComboForm> ufficiList = new ArrayList<ComboForm>();
		ComboForm procInUdienza = commonService.getProcuraInUdienzaAssociata(procedimento.getIdProcedimento());
		ComboForm procGenerale = commonService.getProcuraGenerale(idUffOwn);
		if (procInUdienza != null)
			ufficiList.add(procInUdienza);
		if (procGenerale != null)
			ufficiList.add(procGenerale);
		// se non siamo nel caso del riesame (opposizione) aggiunge procura
		// proponente se diversa da procura in udienza
		if (!Constants.TIPOPROC_RIESAME_PROVV_QUESTURA.equals(procedimento.getTipoProcedimento().getId())) {
			ComboForm procProponente = commonService.getProcuraProponente(procedimento.getIdProcedimento());
			if (procProponente != null && !procProponente.getId().equals(procInUdienza.getId()))
				ufficiList.add(procProponente);
		}
		modelMap.addAttribute("ufficiList", ufficiList);
	}
	
	public void caricaProcuraInUdienzaInSessione(ModelMap modelMap) {
		// int idUffOwn = recuperaIdUfficioUtente();
		ProcedimentoForm procedimentoForm = recuperaProcedimento();
		List<ComboForm> ufficiList = new ArrayList<ComboForm>();
		ComboForm procInUdienza = null;
		// if
		// (request.getSession().getAttribute(Constants.ID_TIPO_UFFICIO).equals(Constants.TIPO_UFFICIO_CORTE_D_APPELLO))
		// {
		// List<ComboForm> uffici =
		// commonService.getProcureDistrettualiPerCdA(idUffOwn);
		// procInUdienza = uffici.get(0);
		// } else {
		procInUdienza = commonService.getProcuraInUdienzaAssociata(procedimentoForm.getIdProcedimento());
		// }
		if (procInUdienza != null)
			ufficiList.add(procInUdienza);
		modelMap.addAttribute("ufficiList", ufficiList);
	}
	
	public void caricaProcuraInUdienzaProcuraGeneraleInSessione(ModelMap modelMap) {
		long idUffOwn = recuperaIdUfficioUtente();
		ProcedimentoForm procedimentoForm = recuperaProcedimento();
		List<ComboForm> ufficiList = new ArrayList<ComboForm>();
		ComboForm procInUdienza = commonService.getProcuraInUdienzaAssociata(procedimentoForm.getIdProcedimento());
		ComboForm procGenerale = commonService.getProcuraGenerale(idUffOwn);
		if (procInUdienza != null)
			ufficiList.add(procInUdienza);
		if (procGenerale != null)
			ufficiList.add(procGenerale);
		modelMap.addAttribute("ufficiList", ufficiList);
	}

}
