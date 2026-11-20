package it.giustizia.smp.service.utility.component;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import it.giustizia.smp.controller.BreadcrumbsController;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.utility.Constants;

@Service
public class UfficiUtilComponent extends BreadcrumbsController {

	public List<ComboForm> getUfficiDestinatariIncompetenzaProcura(HttpServletRequest request) {
		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		List<ComboForm> ufficiList = this.commonService.getUfficiDestinatariIncompetenzaProcura(idProcedimento);
		Integer idUffOwn = recuperaIdUfficioUtente();
		ComboForm procGenerale = commonService.getProcuraGenerale(idUffOwn);
		if (procGenerale != null)
			ufficiList.add(procGenerale);

		return ufficiList;
	}
	
	public void caricaUfficiCompetenzaEProcuraInUdienza(ModelMap modelMap) {
		int idUffOwn = recuperaIdUfficioUtente();
		ProcedimentoForm procedimentoForm = recuperaProcedimento();
		List<ComboForm> ufficiList = commonService.getUfficiCompetenzaEProcuraInUdienza(idUffOwn, Long.parseLong(Constants.TIPO_UFFICIO_PROCURA),
				procedimentoForm.getIdProcedimento());
		modelMap.addAttribute("ufficiList", ufficiList);
	}
	
	public void caricaUfficiRevocazioneConfisca(ModelMap modelMap, HttpServletRequest request) {
		long idUffOwn = recuperaIdUfficioUtente();
		int idTipoUfficio = (Integer) request.getSession().getAttribute("idTipoUfficio");
		ProcedimentoForm procedimentoForm = recuperaProcedimento();
		//Fabio NGG: l'ufficio CDA deve essere dello stesso distretto del tribunale competente
		//List<ComboForm> ufficiList = commonService.getUfficiRevocazioneConfisca(idUffOwn, idTipoUfficio);		
		long uffTrib;
		ComboForm combTrib = null;
		if(Constants.TIPO_UFFICIO_TRIBUNALE.equals(String.valueOf(idTipoUfficio)))
			uffTrib = idUffOwn;
		else  {
			combTrib = commonService.getTribunaleCompetente(procedimentoForm.getIdProcedimento());
			uffTrib = Long.valueOf(combTrib.getId());
		}
		List<ComboForm> ufficiList = commonService.getUfficiRevocazioneConfisca(uffTrib, Long.valueOf(Constants.TIPO_UFFICIO_TRIBUNALE));		
		if (idTipoUfficio != Integer.valueOf(Constants.TIPO_UFFICIO_TRIBUNALE))
			// ricerco l'id dell'ufficio tribunale associato del procedimento
			// corrente
			/*
			List<UfficioForm> ufficiTrib = commonService.getAllUfficiCompetenza(procedimentoForm.getIdProcedimento(),
					Long.valueOf(Constants.TIPO_UFFICIO_TRIBUNALE));
			ComboForm tribunale = null;
			if (ufficiTrib != null && ufficiTrib.get(0) != null) {
				tribunale = ufficiTrib.get(0);
				ufficiList.add(tribunale);
			}
			*/
			ufficiList.add(combTrib);
		modelMap.addAttribute("ufficiList", ufficiList);
	}
}
