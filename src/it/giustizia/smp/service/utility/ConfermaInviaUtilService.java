package it.giustizia.smp.service.utility;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.model.TTpCatAtto;
import it.giustizia.smp.service.msg.AnbscMsgService;
import it.giustizia.smp.service.utility.component.UfficiUtilComponent;
import it.giustizia.smp.utility.AnbscEnum;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.RequestUtil;
import it.giustizia.smp.webservice.client.anbsc.RevocaSequestroResponse;

@Service
public class ConfermaInviaUtilService extends AttoUtilService {

	@Autowired
	private UfficiUtilComponent ufficioUtilComponent;
	
	@Autowired
	private AnbscMsgService anbscMsgService;
	
	public String includeConfermaInviaDecretoRigettoTotaleSequestro(AttoForm attoForm, BindingResult result, HttpServletRequest request,
			ModelMap modelMap) {

		/* 
		 * ISSUE MEV : Specifiche Intervento-006-SpeseProces-NumAtto
		 * Numero MEV : 006
		 * Autore    : Gioggi
		 * Data      : 20 apr 2020
		 * Branch    : MEV_006
		 */
		caricaDatiDaAttoInSessione(attoForm, modelMap);
		//***** FINE INTERVENTO MEV_006 *****//

		ufficioUtilComponent.caricaUfficiCompetenzaEProcuraInUdienza(modelMap);
		modelMap.addAttribute("attoForm", attoForm);
		return "/procedimento/atto/confermaInviaAtti";
	}

	public String includeConfermaInviaDecretoRevocaSequestro(AttoForm attoForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {
		long idCatAtto = RequestUtil.recuperaIdCategoriaAtto(request);
		if (idCatAtto <= 0)
			idCatAtto = attoForm.getCategoriaAtto() != null ? Long.valueOf(attoForm.getCategoriaAtto().getId()).longValue() : 0;
		TTpCatAtto tTpCatAtto = commonService.getCategoriaAtto(idCatAtto);	
		modelMap.addAttribute("AllegatoObbligatorio", tTpCatAtto.getFlgAllegObbl());
					
		if (logger.isDebugEnabled())
		 logger.debug("--- inizio inviaDecretoRevocaSequestro -> modelMap get ->AllegatoObbligatorio :[" + modelMap.get("AllegatoObbligatorio") + "]");
		
		ufficioUtilComponent.caricaUfficiCompetenzaEProcuraInUdienza(modelMap);
		modelMap.addAttribute("attoForm", attoForm);
		return "/procedimento/atto/confermaInviaRevocaSequestro";
	}

	public String includeConfermaInviaDecretoRestBeni(AttoForm attoForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {

		int idUffOwn = recuperaIdUfficioUtente();
		long idProcedimento = recuperaIdProcedimento();
		List<ComboForm> ufficiList = new ArrayList<ComboForm>();

		//Fabio NGG: l'ufficio CDA deve essere dello stesso distretto del tribunale competente
		//ComboForm cda = commonService.getCDA(idUffOwn);
		long uffTrib;
		Integer idTipoUfficio = recuperaIdTipoUfficio();
		if(idTipoUfficio!=null && Constants.TIPO_UFFICIO_TRIBUNALE.equals(idTipoUfficio.toString()))
			uffTrib = idUffOwn;
		else  {
			ComboForm combTrib = commonService.getTribunaleCompetente(idProcedimento);
			uffTrib = Long.valueOf(combTrib.getId());
		}
		ComboForm cda = commonService.getCDA(uffTrib);
		if (cda != null)
			ufficiList.add(cda);

		modelMap.addAttribute("ufficiList", ufficiList);
		
		if(attoForm!= null && attoForm.getNote() != null && !"".equalsIgnoreCase(attoForm.getNote())){
		
			attoForm.setRestituzionePerEquivalente(true);
		}
		
		modelMap.addAttribute("attoForm", attoForm);

		return "/procedimento/atto/confermaInviaDecretoRestBeni";
	}

	public String includeConfermaInviaDecretoOpposizioneDissequestro(AttoForm attoForm, BindingResult result, HttpServletRequest request,
			ModelMap modelMap) {

		caricaTribunale_CDA(modelMap, request);
		modelMap.addAttribute("attoForm", attoForm);
		return "/procedimento/atto/confermaInviaOpposizioneDissequestro";
	}

	public String includeConfermaInviaDecretoRevocaOpposizioneDissequestro(AttoForm attoForm, BindingResult result, HttpServletRequest request,
			ModelMap modelMap) {

		List<ComboForm> ufficiList = new ArrayList<ComboForm>();
		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		ComboForm procInUdienza = commonService.getProcuraInUdienzaAssociata(idProcedimento);
		ufficiList.add(procInUdienza);
		modelMap.addAttribute("ufficiList", ufficiList);
		modelMap.addAttribute("attoForm", attoForm);
		return "/procedimento/atto/confermaInviaRevocaOpposizioneDissequestro";
	}

	public String includeConfermaInviaRevocazioneConfisca(AttoForm attoForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {

		ufficioUtilComponent.caricaUfficiRevocazioneConfisca(modelMap, request);
		modelMap.addAttribute("attoForm", attoForm);
		return "/procedimento/atto/confermaInviaRevocazioneConfisca";
	}
	
	public void caricaTribunale_CDA(ModelMap modelMap, HttpServletRequest request) {

		long idUffOwn = recuperaIdUfficioUtente();
		AttoForm atto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO)));
		long idAtto = atto.getCodAtto();
		ProcedimentoForm procedimentoForm = recuperaProcedimento();
		List<ComboForm> ufficiList = null;

		// se l'atto in sessione è opposizione al dissequestro e l'ufficio
		// connesso è procura
		// bisogna eliminare dalla lista dei destinatari la PG
		String catAtto = null;
		if (atto.getCategoriaAtto() != null && atto.getCategoriaAtto().getId() != null)
			catAtto = atto.getCategoriaAtto().getId();
		if (Constants.DECRETO_OPPOSIZIONE_AL_DISSEQUESTRO.equals(catAtto)
				&& Integer.valueOf(recuperaIdTipoUfficio()).equals(Integer.valueOf(Constants.TIPO_UFFICIO_PROCURA)))
			ufficiList = commonService.getUfficiCompetenzaDistinctTipoUff(procedimentoForm.getIdProcedimento(), idAtto, idUffOwn,
					Constants.TIPO_UFFICIO_PROCURA_GENERALE);
		else
			ufficiList = commonService.getUfficiCompetenzaDistinctTipoUff(procedimentoForm.getIdProcedimento(), idAtto, idUffOwn, 0);
		//Fabio NGG: l'ufficio CDA deve essere dello stesso distretto del tribunale competente
		//ComboForm cda = commonService.getCDA(idUffOwn);
		long uffTrib;
		Integer idTipoUfficio = recuperaIdTipoUfficio();
		if(idTipoUfficio!=null && Constants.TIPO_UFFICIO_TRIBUNALE.equals(idTipoUfficio.toString()))
			uffTrib = idUffOwn;
		else  {
			ComboForm combTrib = commonService.getTribunaleCompetente(procedimentoForm.getIdProcedimento());
			uffTrib = Long.valueOf(combTrib.getId());
		}
		ComboForm cda = commonService.getCDA(uffTrib);
		//
		ufficiList.add(cda);

		if (Long.valueOf(recuperaIdTipoUfficio()) == Constants.TIPO_UFFICIO_PROCURA_GENERALE) {
			ComboForm procInUdienza = commonService.getProcuraInUdienzaAssociata(procedimentoForm.getIdProcedimento());
			if (!ufficiList.contains(procInUdienza))
			 ufficiList.add(procInUdienza);
		}

		modelMap.addAttribute("ufficiList", ufficiList);
	}
	
	public String includeConfermaInviaDecretoSequestro(AttoForm attoForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {

		/* 
		 * ISSUE MEV : Specifiche Intervento-006-SpeseProces-NumAtto
		 * Numero MEV : 006
		 * Autore    : Gioggi
		 * Data      : 20 apr 2020
		 * Branch    : MEV_006
		 */
		caricaDatiDaAttoInSessione(attoForm, modelMap);
		//***** FINE INTERVENTO MEV_006 *****//

		ufficioUtilComponent.caricaUfficiCompetenzaEProcuraInUdienza(modelMap);
		modelMap.addAttribute("attoForm", attoForm);
		return "/procedimento/atto/confermaInviaAtti";
	}
	
	public String includeConfermaInviaDecretoSequestroSospensioneTemporanea(AttoForm attoForm, BindingResult result, HttpServletRequest request,
			ModelMap modelMap) {

		modelMap.addAttribute("attoForm", attoForm);
		return "/procedimento/atto/confermaInviaDecretoSequestroSospensioneTemporanea";
	}

	/* Mev 2020/22 Inizio
	public void inviaRevocaSequestroAnbsc(ProcedimentoForm procedimentoForm, AttoForm attoForm, Long idProcedimento, Long idAtto, Integer idUfficioUtente, String codUtente) {
		if(procedimentoForm.getTipoProcedimento()!=null) {
			if(procedimentoForm.getTipoProcedimento().getId().equals(Constants.TIPOPROC_PERSONALE_E_PATRIMONIALE) 
					|| (procedimentoForm.getTipoFascicolo()!=null && procedimentoForm.getTipoFascicolo().getId().equals("3")) 
					|| procedimentoForm.getTipoProcedimento().getId().equals(String.valueOf(Constants.TIPOPROC_MODIFICA_PERSONALE_CAM))
					|| procedimentoForm.getTipoProcedimento().getId().equals(Constants.TIPOPROC_PATRIMONIALE)) {
				if (attoForm.getCategoriaAtto().getId().equals(Constants.DECRETO_DECISORIO_II_GRADO) ||
					    attoForm.getCategoriaAtto().getId().equals(Constants.DECRETO_DECISORIO_II_GRADO_CAM) ||
						attoForm.getCategoriaAtto().getId().equals(Constants.DECRETO_DECISORIO_PROVV_ISTANZA)) {
					// TODO: Revoca Sequestro II grado
					if(!anbscMsgService.invioRevocaSequestro(idProcedimento, idAtto, idUfficioUtente, codUtente, AnbscEnum.ANBSC_REVOCA_SEQUESTRO_II_GRADO.getId())) {
						super.setMessage(Constants.MESSAGE, "anbsc.invio.error", null, false);
					}
				} else {
					// TODO: Revoca Sequestro II grado
					if(!anbscMsgService.invioRevocaSequestro(idProcedimento, idAtto, idUfficioUtente, codUtente, AnbscEnum.ANBSC_REVOCA_SEQUESTRO_I_GRADO.getId())) {
						super.setMessage(Constants.MESSAGE, "anbsc.invio.error", null, false);
					}
				}
			}
		} 
	}
	Mev 2020/22 Fine */
	
	/* Mev 2020/22 Inizio */
	public RevocaSequestroResponse inviaRevocaSequestroAnbsc(ProcedimentoForm procedimentoForm, AttoForm attoForm, Long idProcedimento, Long idAtto, Integer idUfficioUtente, String codUtente, int grado) {
		RevocaSequestroResponse revocaSequestroResponse = null;
		
		if(procedimentoForm.getTipoProcedimento()!=null) {
			if(procedimentoForm.getTipoProcedimento().getId().equals(Constants.TIPOPROC_PERSONALE_E_PATRIMONIALE) 
					|| (procedimentoForm.getTipoFascicolo()!=null && procedimentoForm.getTipoFascicolo().getId().equals("3")) 
					|| procedimentoForm.getTipoProcedimento().getId().equals(String.valueOf(Constants.TIPOPROC_MODIFICA_PERSONALE_CAM))
					|| procedimentoForm.getTipoProcedimento().getId().equals(Constants.TIPOPROC_PATRIMONIALE)) {
				if (grado == 2) {
					// TODO: Revoca Sequestro II grado
					revocaSequestroResponse = anbscMsgService.invioRevocaSequestro(idProcedimento, idAtto, idUfficioUtente, codUtente, AnbscEnum.ANBSC_REVOCA_SEQUESTRO_II_GRADO.getId());
				} else {
					// TODO: Revoca Sequestro II grado
					revocaSequestroResponse = anbscMsgService.invioRevocaSequestro(idProcedimento, idAtto, idUfficioUtente, codUtente, AnbscEnum.ANBSC_REVOCA_SEQUESTRO_I_GRADO.getId());
				}
			}
		} 
		
		return revocaSequestroResponse;
	}	
	/* Mev 2020/22 Fine */
	
	
}
