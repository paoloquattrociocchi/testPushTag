package it.giustizia.smp.validation;

import it.giustizia.smp.controller.procedimento.ApriController;
import it.giustizia.smp.dao.BeneDao;
import it.giustizia.smp.form.MagistratoForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.model.TMisuraPers;
import it.giustizia.smp.model.TRelAttoMisPatr;
import it.giustizia.smp.service.BeneService;
import it.giustizia.smp.service.MisuraService;
import it.giustizia.smp.service.ProcedimentoService;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Message;
import it.giustizia.smp.utility.RequestUtil;
import it.giustizia.smp.utility.Utility;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("procedimentoValidation")
public class ProcedimentoValidation extends BaseValidation implements Validator {

	@Autowired
	private BeneService			beneService;
	@Autowired
	private MisuraService		misuraService;
	@Autowired
	private BeneDao				beneDao;
	@Autowired
	private ProcedimentoService	procedimentoService;

	public void setProcedimentoService(ProcedimentoService procedimentoService) {

		this.procedimentoService = procedimentoService;
	}

	public void setBeneDao(BeneDao beneDao) {

		this.beneDao = beneDao;
	}

	public void setMisuraService(MisuraService misuraService) {

		this.misuraService = misuraService;
	}

	public void setBeneService(BeneService beneService) {

		this.beneService = beneService;
	}

	// protected static String MESSAGE = "customMessage";
	public boolean supports(Class<?> clazz) {

		return ApriController.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {

		// Necessario per implementare Validator
	}

	public void validateCriteriRicercaPuntuale(ProcedimentoForm procedimento, Errors errors) {

		if (procedimento.getAnno() == null) {
			errors.rejectValue("anno", "NotNull");
		}
		if (procedimento.getNumero() == null) {
			errors.rejectValue("numero", "NotNull");
		}
	}
	
	public void validateCriteriRicercaDifensore(ProcedimentoForm procedimento, Errors errors) {

		/*
		 * 
		 * l’utente dovrà inserire almeno il nome e il cognome oppure il solo codice fiscale e, nel caso in cui tale combinazione non sia rispettata, il sistema invierà a video il seguente messaggio: 
		“Indicare per il Difensore Nome e Cognome oppure il solo Codice Fiscale”

		 */
		
		if (procedimento.getCodiceFiscaleDifensore() != null) {
			if (procedimento.getCodiceFiscaleDifensore().trim().length() > 0) {
				if (procedimento.getNomeDifensore() != null ) {
					errors.reject(Constants.MESSAGE);
					super.setMessage(Constants.MESSAGE, "procedimento.error.difensore.filtro", null, true);
					return;
				}
				
				if (procedimento.getCognomeDifensore()!= null ) {
					errors.reject(Constants.MESSAGE);
					super.setMessage(Constants.MESSAGE, "procedimento.error.difensore.filtro", null, true);
					return;
				}
			}
			
		}
		
		if (procedimento.getNomeDifensore() != null ) {
			if (procedimento.getNomeDifensore().trim().length() > 0) {
				if (procedimento.getCognomeDifensore() == null ) {
					errors.reject(Constants.MESSAGE);
					super.setMessage(Constants.MESSAGE, "procedimento.error.difensore.filtro", null, true);
					return;
				}
			}
		}
		
		if (procedimento.getCognomeDifensore() != null ) {
			if (procedimento.getCognomeDifensore().trim().length() > 0) {
				if (procedimento.getNomeDifensore() == null ) {
					errors.reject(Constants.MESSAGE);
					super.setMessage(Constants.MESSAGE, "procedimento.error.difensore.filtro", null, true);
					return;
				}
			}
		}
	}
	
	public void validateRegistrazioneAggravamento(ProcedimentoForm form, HttpServletRequest request, Errors errors) {

		if (form.getIdSelezionato() == null) {
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "table.item.selection", null, true);
		}
		if (!Utility.isDefinito(form.getProcedente())) {
			super.setMessage("procedimento.aggravamento.error.procedente.undefined", true, errors);
		}
	}

	public void validateRicercaProcedimento(ProcedimentoForm procedimento, Errors errors, HttpServletRequest request) {

		if (procedimento.getIdProcedimento() == null) {
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "no.item.found", null, false);
			// request.setAttribute(MESSAGE, "no.item.found");
		}
	}

	public void validateDettaglioProcedimento(ProcedimentoForm procedimento, Errors errors, HttpServletRequest request) {

        if (procedimento.getIdProcedimento() == null) {
            errors.reject(Constants.MESSAGE);
            super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
        }
    }
	
	public void validateSoggettiInteressatiPerEligibilitaPerAggravamento(List<SoggettoForm> soggettiDelProcedimento, Errors errors) {

		if (!Utility.isDefinito(soggettiDelProcedimento)) {
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "procedimento.aggravamento.error.nosoggetti", null, false);
		}
	}

	public void validateCancellazioneSoggettoInteressato(Long idProcedimento, Long idSoggetto, int idUfficioUtente, Errors errors) {

		List<Message> customMessageList = new ArrayList<Message>();
		String ruolo = (String)request.getSession().getAttribute("RuoloSession");
		if(!(ruolo!=null && ruolo.equalsIgnoreCase("TERZO"))) {
			List<SoggettoForm> soggettiAssociati = procedimentoService.soggettiDirettiInteressatiDelProcedimento(idProcedimento, Constants.RUOLO_DIRETTO, Constants.Flag.S);
			if (soggettiAssociati != null && soggettiAssociati.size() == 1){
				super.addMessageIntoList(customMessageList, Constants.MESSAGE, "soggetto.cancella.error.unique", null);
			}
			List<TMisuraPers> misurePersonali = misuraService.listaMisurePersByIdSoggetto(idSoggetto, idProcedimento, idUfficioUtente);
			if (Utility.isDefinito(misurePersonali)) {
				super.addMessageIntoList(customMessageList, Constants.MESSAGE, "soggetto.cancella.error.misuraPersonale", null);
			}
			boolean presenzaMisurePatrimoniali = controllaPresenzaSoggettoInMisuraPatrimoniale(idSoggetto, idProcedimento, idUfficioUtente);
			if (presenzaMisurePatrimoniali) {
				super.addMessageIntoList(customMessageList, Constants.MESSAGE, "soggetto.cancella.error.misuraPatrimoniale", null);
			}
		}			
		//verifico se il soggetto è anche titolare di diritto su un bene del procedimento
		boolean presenzaTitoliDiritto = controllaTitoliDirittoSoggetto(idSoggetto, idProcedimento, idUfficioUtente);
		if(presenzaTitoliDiritto){
			super.addMessageIntoList(customMessageList, Constants.MESSAGE, "soggetto.cancella.error.titoliDiritto", null);
		}

		if (!customMessageList.isEmpty()) {
			super.setMessages(customMessageList, true, errors);
		}
	}

	public boolean controllaTitoliDirittoSoggetto(long idSoggetto, long idProcedimento, Integer idUfficioUtente) {
		
		int titoliDirittoSize = beneDao.countRelBeneAtto(idSoggetto, idProcedimento, idUfficioUtente);
		return (titoliDirittoSize != 0);
	}

	public boolean controllaPresenzaSoggettoInMisuraPatrimoniale(long idPersona, long idProcedimento, Integer idUfficioUtente) {

		List<TRelAttoMisPatr> relazioni = beneDao.listaRelAttoMisPatr(idPersona, idProcedimento, idUfficioUtente);
		return ((relazioni != null) && !relazioni.isEmpty());
	}
	
	public boolean validateAbilitaOperazioniProcura(ProcedimentoForm procedimento, HttpServletRequest request){
		Integer idTipoUfficio = RequestUtil.recuperaIdTipoUfficio(request);
		if (idTipoUfficio.intValue() == Integer.parseInt(Constants.TIPO_UFFICIO_PROCURA) && ((procedimento == null) || (procedimento.getStatoProcedimento() == null) || procedimento.getStatoProcedimento().getId().equals(String.valueOf(Constants.STATOPROC_IN_LAVORAZIONE)))) {
				super.setMessage(Constants.MESSAGE, "operazione.no.possibile.statoProcedimentoInLavorazione", null, true);
				return false;
		}
		
		if ((procedimento.getStatoProcedimento() != null) && procedimento.getStatoProcedimento().getId().equals(Constants.STATOPROC_ISCRITTO)) {
			Integer idUffOwner = RequestUtil.recuperaIdUfficioUtente(request);
			
			List<MagistratoForm> magistratiAssociati = procedimentoService.listaMagistratiPerProcedimento(idUffOwner, procedimento.getIdProcedimento(), 0, true);
			if(idTipoUfficio.intValue() == Integer.parseInt(Constants.TIPO_UFFICIO_PROCURA) && magistratiAssociati==null || (magistratiAssociati!=null && magistratiAssociati.size()==0)){
				super.setMessage(Constants.MESSAGE, "operazione.no.possibile.statoProcedimentoInIscritto", null, true);
				return false;
			}
		}
		
		return true;
	}
	
	 public void validaAssociaSoggetto(ProcedimentoForm procedimento, Errors errors, HttpServletRequest request) {
	        if(procedimento.getProcedente().getId().equals(Constants.NO_ITEM_SELECTED)){
	            errors.rejectValue("procedente.id", "NotNull");
	        }
	    }
}
