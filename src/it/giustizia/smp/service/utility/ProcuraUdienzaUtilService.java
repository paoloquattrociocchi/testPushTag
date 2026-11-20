package it.giustizia.smp.service.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import it.giustizia.smp.controller.BreadcrumbsController;
import it.giustizia.smp.service.utility.component.ProcuraUdienzaUtilComponent;

@Service
public class ProcuraUdienzaUtilService extends BreadcrumbsController {
	
	@Autowired
	private ProcuraUdienzaUtilComponent procuraUdienzaUtilComponent;
	
	public void caricaProcuraInUdienzaProcuraGeneraleProcuraProponente(ModelMap modelMap) {
		procuraUdienzaUtilComponent.caricaProcuraInUdienzaProcuraGeneraleProcuraProponente(modelMap);
	}
	
	public void caricaProcuraInUdienzaInSessione(ModelMap modelMap) {
		procuraUdienzaUtilComponent.caricaProcuraInUdienzaInSessione(modelMap);
	}
}
