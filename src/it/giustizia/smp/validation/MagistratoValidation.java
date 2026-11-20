package it.giustizia.smp.validation;

import it.giustizia.smp.controller.procedimento.MagistratoController;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.MagistratoForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.model.TRelMagProc;
import it.giustizia.smp.service.AttoService;
import it.giustizia.smp.service.ProcedimentoService;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Message;
import it.giustizia.smp.utility.Utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("magistratoValidation")
public class MagistratoValidation extends BaseValidation implements Validator {

	private static final String	ID_STATO_PROCEDIMENTO_IN_LAVORAZIONE	= "34";
	@Autowired
	private AttoService			attoService;
	@Autowired
	private ProcedimentoService	procedimentoService;

	public void setProcedimentoService(ProcedimentoService procedimentoService) {

		this.procedimentoService = procedimentoService;
	}

	public boolean supports(Class<?> clazz) {

		return MagistratoController.class.isAssignableFrom(clazz);
	}

	public void validate(Object arg0, Errors arg1) {

		// inutilizzato
	}

	public List<Message> validaCompletezzaMagistratiProcura(List<MagistratoForm> magistratiAssociati) {

		int numeroMagistratiAttivi = 0;
		if (Utility.isDefinito(magistratiAssociati)) {
			for (MagistratoForm tmp : magistratiAssociati) {
				if (tmp.getDataFineAssegnazione() == null) {
					numeroMagistratiAttivi++;
				}
			}
		}
		if (numeroMagistratiAttivi < 1) {
			List<Message> messaggiErrore = new ArrayList<Message>();
			Message messaggio = new Message(null, "magistrato.error.nessunAssociato", null, Message.SEPARATOR);
			messaggiErrore.add(messaggio);
			return messaggiErrore;
		}
		return null;
	}

	public List<Message> validaCompletezzaMagistratiTribunale(List<MagistratoForm> magistratiAssociati, boolean aggravamento) {

		int numeroMagistratiAttivi = 0;
		boolean esistePresidente = false;
		boolean esisteRelatore = false;
		List<Message> messaggiErrore = new ArrayList<Message>();
		if (Utility.isDefinito(magistratiAssociati)) {
			for (MagistratoForm tmp : magistratiAssociati) {
				if (tmp.getDataFineAssegnazione() == null) {
					numeroMagistratiAttivi++;
					if ((tmp.getRuolo() != null) && tmp.getRuolo().equals("PRESIDENTE")) {
						esistePresidente = true;
					}
					if ((tmp.getRelatore() != null) && tmp.getRelatore().equals("S")) {
						esisteRelatore = true;
					}
				}
			}
		}
		if (!aggravamento) {
			if (numeroMagistratiAttivi != 3) {
				Message messaggio = new Message(null, "magistrato.error.collegio.dimensioneErrata", null, Message.SEPARATOR);
				messaggiErrore.add(messaggio);
			}
			if (!esisteRelatore) {
				Message messaggio = new Message(null, "magistrato.error.collegio.nonEsisteRelatore", null, Message.SEPARATOR);
				messaggiErrore.add(messaggio);
			}
		}
		if (!esistePresidente) {
			Message messaggio = new Message(null, "magistrato.error.collegio.nonEsistePresidente", null, Message.SEPARATOR);
			messaggiErrore.add(messaggio);
		}
		if (!messaggiErrore.isEmpty()) {
			return messaggiErrore;
		}
		return null;
	}

	public boolean validateAbilitazioneCollegioNuovo(ProcedimentoForm procedimento, HttpServletRequest request) {

		// Se lo stato del procedimento non è definito oppure è
		// "IN ACQUISIZIONE"
		if ((procedimento == null) || (procedimento.getStatoProcedimento() == null) || procedimento.getStatoProcedimento().getId().equals(Constants.STATOPROC_IN_ACQUISIZ_PROPOSTA)) {
			super.setMessage(Constants.MESSAGE, "magistrato.error.collegio.procedimentoInAcquisizione", null, true);
			return false;
		}
		if ((procedimento == null) || (procedimento.getStatoProcedimento() == null) 
				|| (procedimento.getStatoProcedimento().getId().equals(String.valueOf(Constants.STATOPROC_IN_LAVORAZIONE) )
				&&!procedimento.getTipoFascicolo().getId().equals(String.valueOf(Constants.TIPO_FASCICOLO_OPP_DIVIETI_QUESTORE)))) {
			super.setMessage(Constants.MESSAGE, "operazione.no.possibile.statoProcedimentoInLavorazione", null, true);
			return false;
		}
		
		if ((procedimento == null) || (procedimento.getStatoProcedimento() == null) 
				|| (procedimento.getStatoProcedimento().getId().equals(String.valueOf(Constants.STATOPROC_IN_LAVORAZIONE) )
				&&procedimento.getTipoFascicolo().getId().equals(String.valueOf(Constants.TIPO_FASCICOLO_OPP_DIVIETI_QUESTORE)))) {
			super.setMessage(Constants.MESSAGE, "operazione.no.possibile.statoProcedimentoInLavorazioneOpp", null, true);
			return false;
		}
		
		if(procedimento != null && procedimento.getTipoProcedimento() != null) 
		{
//			Segnalazione EMS-75, 145
			if((procedimento.getTipoProcedimento().getId().equals(String.valueOf(Constants.TIPOPROC_PERMESSI_CAM))
					|| procedimento.getTipoProcedimento().getId().equals(String.valueOf(Constants.TIPOPROC_MODIFICA_PERSONALE_CAM)))
					&& procedimento.getStatoProcedimento().getId().equals(Constants.STATOPROC_IN_ACQUISIZ_ISTANZA))
			{
				super.setMessage(Constants.MESSAGE, "operazione.no.possibile.istanzaInLavorazione", null, true);
				return false;
			}
			
			if(procedimento.getTipoProcedimento().getId().equals(String.valueOf(Constants.TIPOPROC_CAUZIONI_CAM)))
			{
				List<AttoForm> listaAtti = attoService.getAttiByProcedimento(procedimento.getIdProcedimento(), 5l, Constants.ISTANZA_CAUZIONE);
				if(listaAtti != null && !listaAtti.isEmpty())
				{
					AttoForm istanzaDiCauzione = listaAtti.get(0);
					if(istanzaDiCauzione.getStatoAtto().getId().equals(Constants.STATOATTO_IN_LAVORAZIONE_S))
					{
						super.setMessage(Constants.MESSAGE, "operazione.no.possibile.istanzaInLavorazione", null, true);
						return false;
					}
				}
			}
		}
		
		return true;
	}

	public boolean validateAbilitazioneNuovo(ProcedimentoForm procedimento, HttpServletRequest request) {

		// Se lo stato del procedimento non è definito oppure è "IN LAVORAZIONE"
		if ((procedimento == null) || (procedimento.getStatoProcedimento() == null) || procedimento.getStatoProcedimento().getId().equals(MagistratoValidation.ID_STATO_PROCEDIMENTO_IN_LAVORAZIONE)) {
			if(procedimento.getTipoProcedimento()!=null && procedimento.getTipoProcedimento().getId()!=null && procedimento.getTipoProcedimento().getId().equals("" + Constants.TIPOPROC_CAUZIONI_CAM))
//				super.setMessage(Constants.MESSAGE, "operazione.no.possibile.statoProcedimentoInLavorazione.cauzione", null, true);
				return true;
			else
				super.setMessage(Constants.MESSAGE, "operazione.no.possibile.statoProcedimentoInLavorazione", null, true);
			return false;
		}
		return true;
	}

	public void validateAssociazioneMagistrato(MagistratoForm form, Errors errors, HttpServletRequest request) {

		List<Message> customMessageList = new ArrayList<Message>();
		if (form.getDataAssegnazioneFascicolo() == null) {
			super.addMessageIntoList(customMessageList, Constants.MESSAGE, "magistrato.error.dataAssegnazioneNotNull", null);
			errors.reject("magistrato.error.dataAssegnazioneNotNull");
		}
		if (form.getDataAssegnazioneFascicolo()!=null && form.getDataAssegnazioneFascicolo().compareTo(new Date()) > 0) {
			super.addMessageIntoList(customMessageList, Constants.MESSAGE, "magistrato.error.dataAssegnazioneFascicolo", null);
			errors.reject("magistrato.error.dataAssegnazioneFascicolo");
		}
		// Se è stato impostato l'ordine di Visualizzazione
		if (form.getOrdineDiVisualizzazione() != null) {
			byte numOrdine = form.getOrdineDiVisualizzazione();
			// Verifico che non esista un altro magistrato, con data fine
			// assegnazione non impostata,
			// associato al procedimento con lo stesso ordine di visualizzazione
			ProcedimentoForm proced = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO)));
			long idProcedimento = proced.getIdProcedimento();
			int idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
			long idMagistrato = form.getId();
			List<TRelMagProc> magProc = procedimentoService.verificaOrdineMagistrati(idProcedimento, idUfficioUtente, numOrdine, idMagistrato);
			if (magProc.size() > 0) {
				super.addMessageIntoList(customMessageList, Constants.MESSAGE, "magistrato.error.ordineVisualizzazionePresente", null);
				errors.reject("magistrato.error.ordineVisualizzazionePresente");
			}
		}
		if ((form.getDataFineAssegnazione() != null) && (form.getDataFineAssegnazione().compareTo(form.getDataAssegnazioneFascicolo()) < 0)) {
			super.addMessageIntoList(customMessageList, Constants.MESSAGE, "magistrato.error.dataFineAssegnazione", null);
			errors.reject("magistrato.error.dataFineAssegnazione");
		}
		if ((form.getDataFineAssegnazione() != null) && !Utility.isDefinito(form.getTipoSostituzione())) {
			super.addMessageIntoList(customMessageList, Constants.MESSAGE, "magistrato.error.tipoSostituzioneObbligatorio", null);
			errors.reject("magistrato.error.tipoSostituzioneObbligatorio");
		}
		if ((form.getDataFineAssegnazione() == null) && Utility.isDefinito(form.getTipoSostituzione())) {
			super.addMessageIntoList(customMessageList, Constants.MESSAGE, "magistrato.error.tipoSostituzioneObbligatorioData", null);
			errors.reject("magistrato.error.tipoSostituzioneObbligatorioData");
		}
		super.setMessages(customMessageList, false);
	}

	public void validateAssociazioneMagistratoProc(MagistratoForm form, Errors errors, HttpServletRequest request) {

		List<Message> customMessageList = new ArrayList<Message>();
		if (form.getDataAssegnazioneFascicolo() == null) {
			super.addMessageIntoList(customMessageList, Constants.MESSAGE, "magistrato.error.dataAssegnazioneNotNull", null);
			errors.reject("magistrato.error.dataAssegnazioneNotNull");
		} else {
			if (form.getDataAssegnazioneFascicolo().compareTo(new Date()) > 0) {
				super.addMessageIntoList(customMessageList, Constants.MESSAGE, "magistrato.error.dataAssegnazioneFascicolo", null);
				errors.reject("magistrato.error.dataAssegnazioneFascicolo");
			}
		}
		// Se è stato impostato l'ordine di Visualizzazione
		if (form.getOrdineDiVisualizzazione() != null) {
			byte numOrdine = form.getOrdineDiVisualizzazione();
			// Verifico che non esista un altro magistrato, con data fine
			// assegnazione non impostata,
			// associato al procedimento con lo stesso ordine di visualizzazione
			ProcedimentoForm proced = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO)));
			long idProcedimento = proced.getIdProcedimento();
			int idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
			long idMagistrato = form.getId();
			List<TRelMagProc> magProc = procedimentoService.verificaOrdineMagistrati(idProcedimento, idUfficioUtente, numOrdine, idMagistrato);
			if (magProc.size() > 0) {
				super.addMessageIntoList(customMessageList, Constants.MESSAGE, "magistrato.error.ordineVisualizzazionePresente", null);
				errors.reject("magistrato.error.ordineVisualizzazionePresente");
			}
		}
		if ((form.getDataFineAssegnazione() != null) && (form.getDataFineAssegnazione().compareTo(form.getDataAssegnazioneFascicolo()) < 0)) {
			super.addMessageIntoList(customMessageList, Constants.MESSAGE, "magistrato.error.dataFineAssegnazione", null);
			errors.reject("magistrato.error.dataFineAssegnazione");
		}
		if ((form.getDataFineAssegnazione() != null) && !Utility.isDefinito(form.getTipoSostituzione())) {
			super.addMessageIntoList(customMessageList, Constants.MESSAGE, "magistrato.error.tipoSostituzioneObbligatorio", null);
			errors.reject("magistrato.error.tipoSostituzioneObbligatorio");
		}
		if ((form.getDataFineAssegnazione() == null) && Utility.isDefinito(form.getTipoSostituzione())) {
			super.addMessageIntoList(customMessageList, Constants.MESSAGE, "magistrato.error.tipoSostituzioneObbligatorioData", null);
			errors.reject("magistrato.error.tipoSostituzioneObbligatorioData");
		}
		
		super.setMessages(customMessageList, false);
	}

	//Nuova Versione 01/2014
	public MagistratoForm validateCorrettezzaInserimentoMagistratoDelCollegio(MagistratoForm magistrato, Errors errors, HttpServletRequest request, long idProcedimento, Integer idUfficioUtente, boolean operazioneDiInserimento) {
		
		//*************************************************************
		//CONTROLLO PER L'"ASSOCIA" (IN FASE DI INSERIMENTO DI UN NUOVO MAGISTRATO)
		//NEL "CONFERMA" DEL MODIFICA MAGISTRATO SONO DATI GIA' PRESENTI
		List<Message> customMessageList = new ArrayList<Message>();
		//Controllo Ruolo obbligatorio
		if (!Utility.isDefinito(magistrato.getRuolo())) {
			super.addMessageIntoList(customMessageList, Constants.MESSAGE, "magistrato.error.ruoloObbligatorio", null);
			errors.reject("magistrato.error.ruoloObbligatorio");
		}
		//Controllo Relatore obbligatorio
		if (!Utility.isDefinito(magistrato.getRelatore())) {
			super.addMessageIntoList(customMessageList, Constants.MESSAGE, "magistrato.error.relatoreObbligatorio", null);
			errors.reject("magistrato.error.relatoreObbligatorio");
		}
		super.setMessages(customMessageList, false);
		//*************************************************************
		
		//Lista dei magistrati attivi già presenti nel collegio
		List<MagistratoForm> magistratiAssociati = procedimentoService.listaMagistratiPerProcedimento(idUfficioUtente, idProcedimento, 0);
		int numeroTotaleMagistratiAttivi = 0;
		int numeroGiudiciLatere = 0;
		int numeroNonRelatori = 0;
		Long idPresidenteAttivo = 0L;
		MagistratoForm presidenteAttivo = null;
		Map<Long, Long> giudiciLatere = new HashMap<Long,Long>();
		Long idRelatore = 0L;
		boolean esistePresidenteAttivo = false;
		boolean esisteRelatore = false;
		
		if(Utility.isDefinito(magistratiAssociati)) {
			for (MagistratoForm tmp : magistratiAssociati) {
				//Controllo solo quelli attivi
				if (tmp.getDataFineAssegnazione() == null) {
					numeroTotaleMagistratiAttivi++;
					if ((tmp.getRuolo() != null) && Constants.ID_TIPO_RUOLO_PRES.equalsIgnoreCase(tmp.getRuolo().getId())) {
						esistePresidenteAttivo = true;
						idPresidenteAttivo = tmp.getId();
						presidenteAttivo = tmp;
					}
					if ((tmp.getRuolo() != null) && Constants.ID_TIPO_RUOLO_GIU_LAT.equalsIgnoreCase(tmp.getRuolo().getId())) {
						numeroGiudiciLatere++;
						giudiciLatere.put(tmp.getId(), tmp.getId());
					}
					if ((tmp.getRelatore() != null)) {
						if(tmp.getRelatore().equals("S")){
							esisteRelatore = true;
							idRelatore = tmp.getId();
						}
						else{
							numeroNonRelatori++;
						}
					}
				}
			}
			
			//Gestione Relatore (ALMENO E AL MASSIMO UNO)
			if(Utility.isDefinito(magistrato.getRelatore())){
				//Se esiste un relatore
				if(esisteRelatore){
					//Se sto inserendo un relatore nuovo ---> ERRORE AL MASSIMO UN RELATORE
					//if (!Constants.ID_TIPO_RUOLO_PRES.equalsIgnoreCase(magistrato.getRuolo().getId())) {
						if(magistrato.getRelatore().equals("S")){
							super.setMessage(Constants.MESSAGE, "magistrato.error.collegio.esisteRelatore", null, false);
							errors.reject(Constants.MESSAGE);
							return null;
						}
					//}
				}
			}
			
			/**
			 * almeno un magistrato deve essere relatore
			 */
			if (!CollectionUtils.isEmpty(magistratiAssociati) && magistratiAssociati.size() == 2 && !esisteRelatore) {
				if (magistrato.getRelatore().equals("N")) {
					super.setMessage(Constants.MESSAGE, "magistrato.error.collegio.nonEsisteRelatore", null, false);
					errors.reject(Constants.MESSAGE);
					return null;
				}
			}

			//Se esiste già un presidente attivo nel collegio e sto inserendo un nuovo presidente...
			if (esistePresidenteAttivo && Utility.isDefinito(magistrato.getRuolo()) && magistrato.getRuolo().getId().equals(Constants.ID_TIPO_RUOLO_PRES) 
					&& !(idPresidenteAttivo.equals(magistrato.getId()))) {
				//... devo impostare la data fine assegnazione del presidente precedente con la data assegnazione del nuovo presidente
				Date dataAssPresAtt = presidenteAttivo.getDataAssegnazioneFascicolo();
				Date dataAssPresNew = magistrato.getDataAssegnazioneFascicolo();
				//La data di assegnazione del nuovo presidente deve essere maggiore o uguale alla data di assegnazione del presidente attivo
				if(dataAssPresNew.before(dataAssPresAtt)){
					super.setMessage(Constants.MESSAGE, "magistrato.error.collegio.dataAssegnazioneFascicolo.presidenti", null, false);
					errors.reject(Constants.MESSAGE);
					return null;
				}
				else return presidenteAttivo;//Ritorno le informazioni del presidente precedente
			}
			
			//Se esiste un presidente attivo e sto impostando data fine assegnazione ---> ERRORE PRESIDENTE ATTIVO OBBLIGATORIO
			if(esistePresidenteAttivo && Utility.isDefinito(magistrato.getRuolo()) && magistrato.getRuolo().getId().equals(Constants.ID_TIPO_RUOLO_PRES)
					&& idPresidenteAttivo.equals(magistrato.getId()) && magistrato.getDataFineAssegnazione()!=null) {
				super.setMessage(Constants.MESSAGE, "magistrato.error.collegio.presidente.dataFineAssegnazione", null, false);
				errors.reject(Constants.MESSAGE);
				return null;
			}
			
			//Se esiste un presidente attivo e lo sto modificando in Giudice a Latere ---> ERRORE PRESIDENTE ATTIVO OBBLIGATORIO 
			if(esistePresidenteAttivo && Utility.isDefinito(magistrato.getRuolo()) && magistrato.getRuolo().getId().equals(Constants.ID_TIPO_RUOLO_GIU_LAT)
					&& idPresidenteAttivo.equals(magistrato.getId())) {
				super.setMessage(Constants.MESSAGE, "magistrato.error.collegio.presidente.giudice", null, false);
				errors.reject(Constants.MESSAGE);
				return null;
			}
			
			
			
			//Se sono presenti già due Giudici a Latere e ne sto inserendo un (nuovo) altro ...
			if(numeroGiudiciLatere==2 && Utility.isDefinito(magistrato.getRuolo()) && Constants.ID_TIPO_RUOLO_GIU_LAT.equalsIgnoreCase(magistrato.getRuolo().getId())
					&& !giudiciLatere.containsKey(magistrato.getId())) {		
				//... se non è presente nessun presidente ---> ERRORE PRESIDENTE OBBLIGATORIO
				if(!esistePresidenteAttivo){
					super.setMessage(Constants.MESSAGE, "magistrato.error.collegio.nonEsistePresidente", null, false);
					errors.reject(Constants.MESSAGE);
					return null;
				}
				//... se è presente un presidente attivo ---> ERRORE IL COLLEGIO E' GIA' COMPLETO
				else{
					super.setMessage(Constants.MESSAGE, "magistrato.error.collegio.massimoGiudiciSuperato", null, false);
					errors.reject(Constants.MESSAGE);
					return null;
				}
			}
		}
		return null;
	}
	//Nuova Versione 01/2014

	public void validateDisponibilitaPerMagistratoDelCollegio(MagistratoForm magistrato, Errors errors, HttpServletRequest request, long idProcedimento, Integer idUfficioUtente) {

		List<MagistratoForm> magistratiAssociati = procedimentoService.listaMagistratiPerProcedimento(idUfficioUtente, idProcedimento, 0);
		if (Utility.isDefinito(magistratiAssociati)) {
			int size = 0;
			for (MagistratoForm tmp : magistratiAssociati) {
				if (tmp.getDataFineAssegnazione() == null) {
					size++;
				}
			}
			if (size > 2) {
				super.setMessage(Constants.MESSAGE, "magistrato.error.collegio.massimoGiudiciSuperato", null, true);
				errors.reject(Constants.MESSAGE);
				return;
			}
		}
	}

	//Nuova Versione 01/2014
	public void validateMagistratoDelCollegioPerAtto(MagistratoForm magistratoForm, Errors errors, HttpServletRequest request, long idAtto, Integer idUffOwn, boolean operazioneDiInserimentoPerAtto) {

		//operazioneDiInserimentoPerAtto è true solo per effettuare il controllo che il collegio non sia già costituito da 3 magistrati ("Nuovo")
		//operazioneDiInserimentoPerAtto è false se provengo dall'Associa ("Associa") o Modifica ("Conferma")
		
		List<MagistratoForm> magistratiAssociati = attoService.listaMagistratiPerAtto(idUffOwn, idAtto, 0);
		int numeroTotaleMagistrati = 0;
		boolean esistePresidente = false;
		boolean esisteRelatore = false;
		Long idPresidenteAttivo = 0L;
		Long idRelatore = 0L;
		
		if(!operazioneDiInserimentoPerAtto){
			//*************************************************************
			//CONTROLLO PER L'"ASSOCIA" (IN FASE DI INSERIMENTO DI UN NUOVO MAGISTRATO)
			//E NEL "CONFERMA" IN FASE DI MODIFICA DEL MAGISTRATO SONO DATI GIA' PRESENTI
			List<Message> customMessageList = new ArrayList<Message>();
			//Controllo Ruolo obbligatorio
			if (!Utility.isDefinito(magistratoForm.getRuolo())) {
				super.addMessageIntoList(customMessageList, Constants.MESSAGE, "magistrato.error.ruoloObbligatorio", null);
				errors.reject("magistrato.error.ruoloObbligatorio");
			}
			//Controllo Relatore obbligatorio
			if (!Utility.isDefinito(magistratoForm.getRelatore())) {
				super.addMessageIntoList(customMessageList, Constants.MESSAGE, "magistrato.error.relatoreObbligatorio", null);
				errors.reject("magistrato.error.relatoreObbligatorio");
			}
			super.setMessages(customMessageList, false);
			if(customMessageList.size()>0){
				return;
			}
			//*************************************************************	
		}
		
		if (Utility.isDefinito(magistratiAssociati)) {
			for (MagistratoForm tmp : magistratiAssociati) {
				numeroTotaleMagistrati++;
				if ((tmp.getRuolo() != null) && Constants.ID_TIPO_RUOLO_PRES.equalsIgnoreCase(tmp.getRuolo().getId())) {
					esistePresidente = true;
					idPresidenteAttivo = tmp.getId();
				}
				if ((tmp.getRelatore() != null) && tmp.getRelatore().equals("S")) {
					esisteRelatore = true;
					idRelatore = tmp.getId();
				}
			}
			
			//Se sto inserendo un nuovo magistrato
			if (operazioneDiInserimentoPerAtto) {
				//Se sono presenti già 3 magistrati...
				if (numeroTotaleMagistrati > 2) {
					super.setMessage(Constants.MESSAGE, "magistrato.error.collegio.massimoGiudiciSuperato", null, false);
					errors.reject(Constants.MESSAGE);
					return;
				}
			}
			
			//Gestione al massimo un Relatore
			if(Utility.isDefinito(magistratoForm.getRelatore())){
				//Se esiste un relatore
				if(esisteRelatore){
					//Se sto inserendo un relatore nuovo ---> ERRORE AL MASSIMO UN RELATORE
					if (!Constants.ID_TIPO_RUOLO_PRES.equalsIgnoreCase(magistratoForm.getRuolo().getId())) {
						if(magistratoForm.getRelatore().equals("S")){
							super.setMessage(Constants.MESSAGE, "magistrato.error.collegio.esisteRelatore", null, false);
							errors.reject(Constants.MESSAGE);
							return;
						}	
					}
				}
			}
			
			//Se esiste già un presidente attivo nel collegio e sto inserendo un nuovo presidente...
			if (esistePresidente && Utility.isDefinito(magistratoForm.getRuolo()) && magistratoForm.getRuolo().getId().equals(Constants.ID_TIPO_RUOLO_PRES) 
					&& !(idPresidenteAttivo.equals(magistratoForm.getId()))) {
				super.setMessage(Constants.MESSAGE, "magistrato.error.collegio.esistePresidente", null, false);
				errors.reject(Constants.MESSAGE);
				return;
			}
			
			//Se esiste un presidente attivo e lo sto modificando in Giudice a Latere ---> ERRORE PRESIDENTE ATTIVO OBBLIGATORIO 
			if(esistePresidente && Utility.isDefinito(magistratoForm.getRuolo()) && magistratoForm.getRuolo().getId().equals(Constants.ID_TIPO_RUOLO_GIU_LAT)
					&& idPresidenteAttivo.equals(magistratoForm.getId())) {
				super.setMessage(Constants.MESSAGE, "magistrato.error.collegio.presidente.giudice", null, false);
				errors.reject(Constants.MESSAGE);
				return;
			}
		}
	}
	//Nuova Versione 01/2014
	
	public void validateNuovoRiesame(MagistratoForm magistratoForm, Errors errors, HttpServletRequest request, List listaMagistratiAssociati) {

		if ((listaMagistratiAssociati != null) && (listaMagistratiAssociati.size() > 0)) {
			super.setMessage(Constants.MESSAGE, "magistrato.error.collegio.riesame.massimoGiudiciSuperato", null, false);
			errors.reject(Constants.MESSAGE);
			return;
		}
	}

	public void validateSceltaRadioButton(MagistratoForm form, Errors errors, HttpServletRequest request) {

		if ((form == null) || (form.getId() == null)) {
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
		}
	}
	
	//Se sto effettuando una cancellazione del magistrato deve obbligatoriamente esserne più di uno
	public void validateUnicoMagistratoPresente(List<MagistratoForm> listaMagistrati, Errors errors, HttpServletRequest request) {

		if (listaMagistrati!=null && listaMagistrati.size()<=1) {
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "table.item.selection.delete.magistrato.unico", null, false);
		}
	}
	
	public void validateSceltaRadioButtonIdString(MagistratoForm form, Errors errors, HttpServletRequest request) {

		if ((form == null) || (form.getIdMagString() == null)) {
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
		}
	}

	//Modifica 01/2014
	public void validateDataFineAssegnazione(MagistratoForm magistratoForm, Errors errors, HttpServletRequest request) {
		if (magistratoForm.getDataFineAssegnazione()!=null) {
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "magistrato.error.collegio.modifica", null, false);
		}
	}

	public void validateEliminazione(MagistratoForm magistrato, Errors errors, HttpServletRequest request) {
		//Se cerco di eliminare il Presidente attivo ---> ERRORE
		if(Utility.isDefinito(magistrato.getRuolo()) && magistrato.getRuolo().getId().equals(Constants.ID_TIPO_RUOLO_PRES) && magistrato.getDataFineAssegnazione()==null){
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "magistrato.error.collegio.elimina", null, false);
		}
	}
	//Modifica 01/2014
	
	public void validateInserimento(MagistratoForm magistrato, Errors errors) {
		if(magistrato.getCognome() == null){
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "magistrato.error.cognome", null, false);
			return;
		}
		if(magistrato.getNome() == null){
			errors.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "magistrato.error.nome", null, false);
			return;
		}
	}
	
	public boolean sostituzioneMagistratiConferma(MagistratoForm magistratoForm, Errors errors, String tipoMagistrato) {
		
		if(magistratoForm.getMagistratoDaSostituire()==null || magistratoForm.getMagistratoDaSostituire().getId().equals(Constants.NO_ITEM_SELECTED)) {
			errors.rejectValue("magistratoDaSostituire.id", "NotNull");
		} 
		if(magistratoForm.getMagistratoSubentrante()==null || magistratoForm.getMagistratoSubentrante().getId().equals(Constants.NO_ITEM_SELECTED)) {
			errors.rejectValue("magistratoSubentrante.id", "NotNull");
		}
		if(magistratoForm.getMagistratoDaSostituire().getId().equals(magistratoForm.getMagistratoSubentrante().getId())) {
			super.setMessage(Constants.MESSAGE, "magistrato.error.stessoMagistrato", null, false);
			return true;
		}
		if(magistratoForm.getCheckProcedimenti()!=null && magistratoForm.getCheckProcedimenti().equals("I")) {
			
			if( !(magistratoForm.getProcedimentoDaAnno()!=null && magistratoForm.getProcedimentoAAnno()!=null &&
					magistratoForm.getProcedimentoDaNum()!=null && magistratoForm.getProcedimentoANum()!=null)) {
				super.setMessage(Constants.MESSAGE, "magistrato.error.annoProcedimento", null, false);
				return true;
			} else {
				if(magistratoForm.getProcedimentoDaAnno()>9999) {
					errors.rejectValue("procedimentoDaAnno", "typeMismatch");
				}
				if(magistratoForm.getProcedimentoAAnno()>9999) {
					errors.rejectValue("procedimentoAAnno", "typeMismatch");
				}
				if(!errors.hasErrors()) {
					if(magistratoForm.getProcedimentoDaAnno() > magistratoForm.getProcedimentoAAnno())
					{
						super.setMessage(Constants.MESSAGE, "magistrato.error.annoProcedimento", null, false);
						return true;
					}
					else if((magistratoForm.getProcedimentoDaAnno() == magistratoForm.getProcedimentoAAnno())
							&&(magistratoForm.getProcedimentoDaNum() > magistratoForm.getProcedimentoANum()))
					{
						super.setMessage(Constants.MESSAGE, "magistrato.error.annoProcedimento", null, false);
						return true;
					}
						
				}
			}
		}
		
		return errors.hasErrors();

	}	
}
