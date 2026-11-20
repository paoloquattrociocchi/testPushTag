package it.giustizia.smp.service.utility;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import it.giustizia.smp.controller.BreadcrumbsController;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.model.TTpCatAtto;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.RequestUtil;
import it.giustizia.smp.validation.AllegatoValidation;

@Service
public class AllegatoFirmaUtilService extends BreadcrumbsController {

	@Autowired
	private AllegatoValidation allegatoValidation;
	
	public void validaAllegatoFirmaPosticipata(AttoForm attoForm, BindingResult result, HttpServletRequest request) {
		long idCatAtto = RequestUtil.recuperaIdCategoriaAtto(request);
		if (idCatAtto <= 0)
			idCatAtto = attoForm.getCategoriaAtto() != null ? Long.valueOf(attoForm.getCategoriaAtto().getId()).longValue() : 0;

		TTpCatAtto tTpCatAtto = commonService.getCategoriaAtto(idCatAtto);
		allegatoValidation.validateAllegatoFirmaPosticipata(attoForm.getAllegato(), result, request, tTpCatAtto);
	}
	
	public void validaAllegatoEFirma(AttoForm attoForm, BindingResult result, HttpServletRequest request) {
		long idCatAtto = RequestUtil.recuperaIdCategoriaAtto(request);
		if (idCatAtto <= 0)
			idCatAtto = attoForm.getCategoriaAtto() != null ? Long.valueOf(attoForm.getCategoriaAtto().getId()).longValue() : 0;

		TTpCatAtto tTpCatAtto = commonService.getCategoriaAtto(idCatAtto);
//		Segnalazione EMS-80
		ProcedimentoForm procedimento = recuperaProcedimento();
		if( procedimento.getTipoProcedimento().getId().equals(""+Constants.TIPOPROC_PERMESSI_CAM) 
				&& tTpCatAtto.getCodiKIdCatAtto() == Integer.parseInt(Constants.PARERI_REVOCA_MODIFICA_MISURE_PERSONALI)) {
			tTpCatAtto.setFlgAllegObbl('N');
		}
		allegatoValidation.validateAllegatoEFirma(attoForm.getAllegato(), result, request, tTpCatAtto);

	}

	public void validaAllegatoEFirmaAtto(AttoForm attoForm, BindingResult result, HttpServletRequest request) {
		long idCatAtto = RequestUtil.recuperaIdCategoriaAtto(request);
		if (idCatAtto <= 0)
			idCatAtto = attoForm.getCategoriaAtto() != null ? Long.valueOf(attoForm.getCategoriaAtto().getId()).longValue() : 0;

		TTpCatAtto tTpCatAtto = commonService.getCategoriaAtto(idCatAtto);
		allegatoValidation.validateAllegatoEFirmaAtto(attoForm.getAllegato(), result, request, tTpCatAtto, attoForm);
	}

	public void validaAllegatoEFirmaAttoCorrezioneErroreMateriale(AttoForm attoForm, BindingResult result, HttpServletRequest request) {
		long idCatAtto = RequestUtil.recuperaIdCategoriaAtto(request);
		if (idCatAtto <= 0)
			idCatAtto = attoForm.getCategoriaAtto() != null ? Long.valueOf(attoForm.getCategoriaAtto().getId()).longValue() : 0;

		TTpCatAtto tTpCatAtto = commonService.getCategoriaAtto(idCatAtto);
		allegatoValidation.validateAllegatoEFirmaAttoCorrezioneErroreMateriale(attoForm.getAllegato(), result, request, tTpCatAtto, attoForm);
	}
	
	public boolean validaAllegatoEDateIncompleti(AttoForm attoForm, BindingResult result, HttpServletRequest request) {
		return allegatoValidation.validateAllegatoEDateIncompleti(attoForm.getAllegato(), result, request, attoForm);
	}

	public boolean validaAllegatoEDateIncompletiCorrezioneErroreMateriale(AttoForm attoForm, BindingResult result, HttpServletRequest request) {
		return allegatoValidation.validateAllegatoEDateIncompletiCorrezioneErroreMateriale(attoForm.getAllegato(), result, request, attoForm);
	}
}
