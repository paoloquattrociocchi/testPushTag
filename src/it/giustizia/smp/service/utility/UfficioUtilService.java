package it.giustizia.smp.service.utility;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import it.giustizia.smp.controller.BreadcrumbsController;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.UfficioForm;
import it.giustizia.smp.service.utility.component.UfficiUtilComponent;
import it.giustizia.smp.utility.Constants;

@Service
public class UfficioUtilService extends BreadcrumbsController {

	@Autowired
	private UfficiUtilComponent ufficiUtilComponent;
	
	public void caricaUfficiCompetenzaEProcuraInUdienza(ModelMap modelMap) {
		ufficiUtilComponent.caricaUfficiCompetenzaEProcuraInUdienza(modelMap);
	}
	
	public void caricaUfficiRevocazioneConfisca(ModelMap modelMap, HttpServletRequest request) {
		ufficiUtilComponent.caricaUfficiRevocazioneConfisca(modelMap, request);
	}
	
	public List<ComboForm> caricaUfficiSentenzaOpposizioneDissequestro() {

		recuperaIdUfficioUtente();
		ProcedimentoForm procedimento = recuperaProcedimento();
		List<ComboForm> ufficiList = new ArrayList<ComboForm>();

		// ricerco l'id dell'ufficio tribunale associato del procedimento
		// corrente
		List<UfficioForm> ufficiTrib = commonService.getAllUfficiCompetenza(procedimento.getIdProcedimento(),
				Long.valueOf(Constants.TIPO_UFFICIO_TRIBUNALE));
		ComboForm tribunale = null;
		if (ufficiTrib != null && ufficiTrib.get(0) != null)
			tribunale = ufficiTrib.get(0);

		// ricerco l'id del fascicolo padre (fascicolo di tribunale) associato
		// al procedimento corrente
		long idProcTribunale = procedimentoService.getIdPadreAggravamento(procedimento.getIdProcedimento());

		ComboForm procInUdienza = null;
		procInUdienza = commonService.getProcuraInUdienzaAssociata(idProcTribunale);
		
		ComboForm procGenerale = commonService.getProcuraInUdienzaAssociata(procedimento.getIdProcedimento());
		// ricerco l'ufficio di procura associata al fascicolo di tribunale
		/*
		List<UfficioForm> ufficiProcura = commonService.getAllUfficiCompetenza(idProcTribunale, Long.valueOf(Constants.TIPO_UFFICIO_PROCURA));
		
		ComboForm procInUdienza = null;
		if (ufficiProcura != null && !ufficiProcura.isEmpty() && ufficiProcura.get(0) != null)
			procInUdienza = ufficiProcura.get(0);
		
		ComboForm procGenerale = commonService.getProcuraGenerale(idUffOwn);
		*/
		
		if (procInUdienza != null)
			ufficiList.add(procInUdienza);
		else
			throw new RuntimeException("Procura in udienza non trovata");

		if (procGenerale != null)
			ufficiList.add(procGenerale);
		else
			throw new RuntimeException("Procura generale non trovata");

		if (tribunale != null)
			ufficiList.add(tribunale);
		else
			throw new RuntimeException("Tribunale non trovato");

		return ufficiList;
	}
	
	public List<ComboForm> caricaUfficiSentenzaRevocazioneConfisca() {

		long idUffOwn = recuperaIdUfficioUtente();
		ProcedimentoForm procedimento = recuperaProcedimento();
		List<ComboForm> ufficiList = new ArrayList<ComboForm>();

		// ricerco l'id dell'ufficio tribunale associato del procedimento
		// corrente
		List<UfficioForm> ufficiTrib = commonService.getAllUfficiCompetenza(procedimento.getIdProcedimento(),
				Long.valueOf(Constants.TIPO_UFFICIO_TRIBUNALE));
		ComboForm tribunale = null;
		if (ufficiTrib != null && ufficiTrib.size() > 0 && ufficiTrib.get(0) != null)
			tribunale = ufficiTrib.get(0);

		ComboForm procGenerale = commonService.getProcuraGenerale(idUffOwn);

		if (procGenerale != null)
			ufficiList.add(procGenerale);
		else
			throw new RuntimeException("Procura generale non trovata");

		if (tribunale != null)
			ufficiList.add(tribunale);
		else
			throw new RuntimeException("Tribunale non trovato");

		return ufficiList;
	}
	
	public List<ComboForm> getUfficiDestinatariIncompetenzaProcura(HttpServletRequest request) {
		return ufficiUtilComponent.getUfficiDestinatariIncompetenzaProcura(request);
	}
}
