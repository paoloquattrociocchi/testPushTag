package it.giustizia.smp.service.utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import it.giustizia.smp.controller.MenuController;
import it.giustizia.smp.controller.procedimento.UdienzaController;
import it.giustizia.smp.dto.KeyMessageDto;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.ProfiloForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.form.UdienzaForm;
import it.giustizia.smp.model.TDecreto;
import it.giustizia.smp.service.AttoServiceImpl;
import it.giustizia.smp.service.MisuraService;
import it.giustizia.smp.service.UdienzaService;
import it.giustizia.smp.service.utility.component.DecretoUtilComponent;
import it.giustizia.smp.service.utility.component.ProcuraUdienzaUtilComponent;
import it.giustizia.smp.service.utility.component.UfficiUtilComponent;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;

@Service
public class DecretoUtilService extends AttoUtilService {

	@Autowired
	private ProcuraUdienzaUtilComponent procuraUdienzaUtilComponent;
	@Autowired
	private UfficiUtilComponent ufficioUtilComponent;
	@Autowired
	private UdienzaService udienzaService;
	@Autowired
	private MisuraService misuraService;
	@Autowired
	private DecretoUtilComponent decretoUtilComponent;
	
	public String apriDecretoIncompetenza(String tipoDecreto, AttoForm attoForm, HttpServletRequest request, BindingResult result, ModelMap modelMap) {

		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		List<SoggettoForm> soggettiDirettiInteressati = procedimentoService.soggettiDirettiInteressatiDelProcedimento(idProcedimento,
				Constants.RUOLO_DIRETTO, Constants.Flag.S);
		modelMap.addAttribute("soggetti", soggettiDirettiInteressati);
		modelMap.addAttribute("tipoDecreto", tipoDecreto);
		modelMap.addAttribute("soggettoForm", new SoggettoForm());

		boolean attoRegistrato = false;
		if (attoForm != null && attoForm.getStatoAtto() != null
				&& Long.valueOf(attoForm.getStatoAtto().getId()).equals(Constants.STATOATTO_REGISTRATO))
			attoRegistrato = true;

		String page = null;
		if (tipoDecreto.equals(Constants.DECRETO_INCOMPETENZA_RESTITUZIONE_ATTI_PROCURA)
				|| tipoDecreto.equals(Constants.DECRETO_INCOMPETENZA_PROCURA)) {
			String response = commonService.controlloIncompetenzaProcura(idProcedimento);
			if (!(response != null && response.equalsIgnoreCase("OK"))) {
				super.setMessage(Constants.MESSAGE, "atto.decreto.incompetenza.procura.error", null, false);
				result.reject(Constants.MESSAGE);
				return "/procedimento/atto/nuovoAtto";
			}
		}

		if (tipoDecreto.equals(Constants.DECRETO_INCOMPETENZA_RESTITUZIONE_ATTI_PROCURA)
				|| tipoDecreto.equals(Constants.DECRETO_INCOMPETENZA_TRASF_ALTRO_TRIBUNALE)) {
			if (!attoRegistrato)
				procuraUdienzaUtilComponent.caricaProcuraInUdienzaProcuraGeneraleProcuraProponente(modelMap);
			page = "/procedimento/atto/nuovoDecretoIncompetenza";
		} else if (!attoRegistrato) {
			List<ComboForm> uffDestinatari = ufficioUtilComponent.getUfficiDestinatariIncompetenzaProcura(request);
			modelMap.addAttribute("ufficiList", uffDestinatari);
			page = "/procedimento/atto/nuovoDecretoIncompetenzaProcura";
		} else
			page = "/procedimento/atto/nuovoDecretoIncompetenzaProcura";

		return page;
	}
	
	public long salvaModificaDecretoIncompetenza(AttoForm attoForm, long idUfficio, String codiceUtente, long idProcedimento,
			HttpServletRequest request) {

		if (attoForm.getAllegato() != null && attoForm.getAllegato().getFileData() != null && attoForm.getAllegato().getFileData().getSize() > 0) {
			attoForm.getAllegato().setFileName(attoForm.getAllegato().getFileData().getOriginalFilename());
			attoForm.getAllegato().setContentType(attoForm.getAllegato().getFileData().getContentType());
		}

		if (attoForm.getCodAtto() == 0) {
			// inserimento
			if (attoForm.getCategoriaAtto().getId().equals(Constants.DECRETO_INCOMPETENZA_PROCURA)) {
				ProcedimentoForm procedimento = recuperaProcedimento();
				procedimento.setAntimafia(true);
				this.procedimentoService.aggiornaProcedimento(idUfficio, codiceUtente, procedimento);
			}

			attoForm.setCodAtto(this.attoService.inserisciDecretoIncompetenza((int) idUfficio, idProcedimento, codiceUtente, attoForm,
					Constants.STATOATTO_IN_LAVORAZIONE, super.printMethodName()));

		} else {
			// modifica
			inizializzaDocumento(attoForm, request);
			// Specifiche Intervento-006-SpeseProces-NumAtto: aggiunto parametro di passaggio
			attoForm.setCodAtto(this.attoService.modificaAtto((int) idUfficio, attoForm, Constants.STATOATTO_IN_LAVORAZIONE, false));
		}
		return attoForm.getCodAtto();
	}
	
	public String caricaDecretoAssNuovoRuoloRemSuRuolo(AttoForm attoForm, String categoriaAtto, BindingResult result, HttpServletRequest request,
			ModelMap modelMap) {

		long idUffOwn = recuperaIdUfficioUtente();
		ProcedimentoForm procedimento = recuperaProcedimento();
		UdienzaForm udienzaForm = new UdienzaForm();
		if (attoForm.getDataRegistrazione() == null) {
			List<UdienzaForm> udienzaInFaseDi = new ArrayList<UdienzaForm>();
			if (categoriaAtto.equals(Constants.DECRETO_ASSEGNAZIONE_NUOVO_RUOLO)
					|| categoriaAtto.equals(Constants.DECRETO_ASSEGNAZIONE_NUOVO_RUOLO_II_GRADO))
				udienzaInFaseDi = udienzaService.listaUdienzePerProcedimentoEStatoUdienza(procedimento.getIdProcedimento().longValue(), idUffOwn,
						String.valueOf(UdienzaController.STATO_UDIENZA_IN_ASSEGNAZIONE_NUOVO_RUOLO));
			else if (categoriaAtto.equals(Constants.DECRETO_REMISSIONE_SU_RUOLO)
					|| categoriaAtto.equals(Constants.DECRETO_REMISSIONE_SU_RUOLO_II_GRADO)) {
				if (attoForm.getFkUdienzaDecreto() > 0)
					// Carico i dati della nuova Udienza già presente
					udienzaForm = attoService.getUdienzaById(attoForm.getFkUdienzaDecreto(), idUffOwn);
				else {
					ProfiloForm profiloUtente = (ProfiloForm) request.getSession().getAttribute("profilo");
					udienzaForm.setIndirizzoAulaUdienza(profiloUtente.getIndirizzoUfficio());
				}
				// Carico i dati dell'Udienza precedente in stato 'Remissione su
				// Ruolo'
				udienzaInFaseDi = udienzaService.listaUdienzePerProcedimentoEStatoUdienza(procedimento.getIdProcedimento().longValue(), idUffOwn,
						String.valueOf(UdienzaController.STATO_UDIENZA_IN_REMISSIONE_SU_NUOVO_RUOLO));
			}
			UdienzaForm udienzaRiserva = udienzaInFaseDi.get(0);
			udienzaRiserva = attoService.getUdienzaById(udienzaInFaseDi.get(0).getCodUdienza(), idUffOwn);
			udienzaRiserva.setEsito(new ComboForm("10", "RISERVA"));
			udienzaForm.setUdienzaPrecedente(udienzaRiserva);
		} else {
			List<UdienzaForm> udienza = new ArrayList<UdienzaForm>();
			UdienzaForm udienzaRiserva = new UdienzaForm();
			if (categoriaAtto.equals(Constants.DECRETO_ASSEGNAZIONE_NUOVO_RUOLO)
					|| categoriaAtto.equals(Constants.DECRETO_ASSEGNAZIONE_NUOVO_RUOLO_II_GRADO)) {
				udienza = udienzaService.getUdienzaByCodAttoEStatoUdi(attoForm.getCodAtto(),
						String.valueOf(UdienzaController.STATO_UDIENZA_ASSEGNATA_NUOVO_RUOLO), idUffOwn);
				udienzaRiserva = udienza.get(0);
				udienzaRiserva = attoService.getUdienzaById(udienza.get(0).getCodUdienza(), idUffOwn);
				udienzaRiserva.setEsito(new ComboForm("10", "RISERVA"));
			} else {
				// Sull'attoForm ho la FkUdienza relativa all'Udienza nuova in
				// stato 'Fissata'
				udienzaForm = attoService.getUdienzaById(attoForm.getFkUdienzaDecreto(), idUffOwn);
				// Carico i dati dell'Udienza precedentemente in stato RISERVA
				long idUdienzaRimessa = udienzaService.getUdienzaPrecedente(udienzaForm.getCodUdienza(), (int) idUffOwn);
				udienzaRiserva = attoService.getUdienzaById(idUdienzaRimessa, idUffOwn);
				udienzaRiserva.setEsito(new ComboForm("10", "RISERVA"));
			}
			udienzaForm.setUdienzaPrecedente(udienzaRiserva);
			udienzaForm.setStatoAtto(attoForm.getStatoAtto());
			udienzaForm.setDataRegistrazione(attoForm.getDataRegistrazione());
			udienzaForm.setDataEmissione(attoForm.getDataEmissione());
			udienzaForm.setDataDeposito(attoForm.getDataDeposito());
		}
		udienzaForm.setCodAtto(attoForm.getCodAtto());
		if (attoForm.getAllegato().getFileName() != null) {
			udienzaForm.getAllegato().setFileName(attoForm.getAllegato().getFileName());
			udienzaForm.getAllegato().setContentType(attoForm.getAllegato().getContentType());
		}
		
		if (attoForm.getDataEmissione() != null)
			udienzaForm.setDataEmissione(attoForm.getDataEmissione());
		if (attoForm.getDataDeposito() != null)
			udienzaForm.setDataDeposito(attoForm.getDataDeposito());
		
		
		procuraUdienzaUtilComponent.caricaProcuraInUdienzaInSessione(modelMap);
		modelMap.addAttribute("udienzaForm", udienzaForm);
		updateViewAndMenu(procedimento, request);
		MenuController.caricaMenu("15", request);
		if (categoriaAtto.equals(Constants.DECRETO_ASSEGNAZIONE_NUOVO_RUOLO)
				|| categoriaAtto.equals(Constants.DECRETO_ASSEGNAZIONE_NUOVO_RUOLO_II_GRADO))
			return "/procedimento/atto/copertinaDecretoAssegnazioneNuovoRuolo";
		else
			return "/procedimento/atto/copertinaDecretoRemissioneRuolo";
	}
	
	/**
	 * getMisuraDecretoApplicazioneProvvisoria
	 * @param soggettoForm
	 * @param attoForm
	 * @param modelMap
	 * @return
	 */
	public String getMisuraDecretoApplicazioneProvvisoria(SoggettoForm soggettoForm, AttoForm attoForm , ModelMap modelMap,boolean ricarica, HttpServletRequest request)
	{
		return decretoUtilComponent.getMisuraDecretoApplicazioneProvvisoria(soggettoForm, attoForm, modelMap, ricarica, request);
	}
	
	public String inserisciDecretoApplicazioneProvvisoria(AttoForm attoForm, HttpServletRequest request, ModelMap modelMap, BindingResult result, List<ComboForm> categoria) {

		ProcedimentoForm procedimentoForm = (ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO));
		int idUffOwn = recuperaIdUfficioUtente();
		long idProcedimento = procedimentoForm.getIdProcedimento();
		String codiceUtente = recuperaIdCodiceUtente();

		List<SoggettoForm> soggetti = procedimentoService.soggettiDirettiInteressatiApplicazioneProvvisoria(idProcedimento, idUffOwn, codiceUtente);
        
		if(soggetti == null ||(soggetti != null  && soggetti.isEmpty())){
			super.setMessage(Constants.MESSAGE, "error.apri.applica.provv", null, false);
			modelMap.addAttribute("categoria", categoria);
			return "/procedimento/atto/nuovoAtto";
		}
		
		//gennaroeventualmente
		KeyMessageDto ritornoDb = attoService
				.controllaCollegioAtto(idProcedimento, attoForm.getCategoriaAtto().getId(), codiceUtente, idUffOwn, super.printMethodName());
		if (ritornoDb.isError()) {
			super.setMessage(Constants.MESSAGE, "messaggio.gestito.db", ritornoDb.getMessage(), false);
			result.reject(Constants.MESSAGE);
			modelMap.addAttribute("categoria", categoria);
			return "/procedimento/atto/nuovoAtto";
		}
		
		long idAtto = attoService.salvaAttoDecretoConMagistrato(idUffOwn, idProcedimento, codiceUtente, attoForm, Constants.STATOATTO_IN_LAVORAZIONE);
	
		caricaAttoInSessione(idUffOwn, idAtto, codiceUtente, procedimentoForm.getIdProcedimento(), request, attoForm);
		
        return attoUtilComponent.copertinaDecretoApplicazioneProvvisoria(attoForm, soggetti, modelMap, request);
	
	}
	
	public String dettaglioDecisorioIGradoRiesame(AttoForm attoForm, ModelMap modelMap, BindingResult result, HttpServletRequest request) {

		int idUffOwn = recuperaIdUfficioUtente();
		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		long IdAtto = attoForm.getCodAtto();
		String statoAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getStatoAtto().getId();
		procuraUdienzaUtilComponent.caricaProcuraInUdienzaProcuraGeneraleInSessione(modelMap);
		ProfiloForm profilo = (ProfiloForm) request.getSession().getAttribute("profilo");
		List<ComboForm> esiti;
		if (statoAtto.equals(Constants.STATOATTO_IN_LAVORAZIONE_S))
			esiti = procedimentoService.getEsiti("OP", Long.parseLong(Constants.DECRETO_DECISORIO_I_GRADO), profilo.getIdTipoUfficio());
		else
			esiti = procedimentoService.getEsiti("OP", Long.parseLong(Constants.DECRETO_DECISORIO_I_GRADO), 0);
		modelMap.addAttribute("listaEsiti", esiti);
		List<SoggettoForm> soggetti = procedimentoService.soggettiDirettiInteressatiDelProcedimento(idProcedimento, Constants.RUOLO_DIRETTO,
				Constants.Flag.S);
		SoggettoForm soggettoForm = soggetti.get(0);
		soggettoForm.setIdProcedimento(idProcedimento);
		soggettoForm.setIdUfficio(idUffOwn);
		soggettoForm.setIdAtto(IdAtto);
		soggettoForm = misuraService.caricaMisuraPersonale(soggettoForm);
		if (soggettoForm.getMisura().getDecisioneMisuraPersonale() != null && (result == null || !result.hasErrors())) {
			ComboForm esitoOpposizione = new ComboForm();
			esitoOpposizione.setId(soggettoForm.getMisura().getDecisioneMisuraPersonale().getId());
			esitoOpposizione.setDescrizione(soggettoForm.getMisura().getDecisioneMisuraPersonale().getDescrizione());
			attoForm.setEsitoOpposizione(esitoOpposizione);
		}

		Date dataDefinitivita = null;
		if (soggettoForm.getMisura() != null && soggettoForm.getMisura().getIdMisureCaricate() != null)
			dataDefinitivita = misuraService.getDataDefinitivitaDecisorioIGradoRiesame(
					Long.parseLong(soggettoForm.getMisura().getIdMisureCaricate()), idProcedimento, IdAtto, idUffOwn);
		attoForm.setDataDefinitivita(dataDefinitivita);

		if (statoAtto.equals(Constants.STATOATTO_IN_LAVORAZIONE_S)) {
			ComboForm procInUdienza = commonService.getProcuraInUdienzaAssociata(idProcedimento);
			List<ComboForm> ListaUfficiDestinatari = new ArrayList<ComboForm>();
			ListaUfficiDestinatari.add(procInUdienza);
			modelMap.addAttribute("ufficiList", ListaUfficiDestinatari);
		}

		modelMap.addAttribute("soggetti", soggetti);
		modelMap.addAttribute("attoForm", attoForm);
		MenuController.caricaMenu(Constants.ID_PER_MENU_DETTAGLIO_RIESAME, request);
		return "/procedimento/atto/copertinaDecretoDecisorioIGradoRiesame";
	}
	

	public String nuovoDecretoOpposizioneDissequestro(@Valid AttoForm attoForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {

		ProcedimentoForm procedimento = recuperaProcedimento();
		int idUffOwn = recuperaIdUfficioUtente();
		List<AttoForm> listaDecretiDissequestro = attoService.getAttiOpposizioneDissequestro(procedimento.getIdProcedimento());

		List<ComboForm> listaDecretiCombo = new ArrayList<ComboForm>();
		Iterator<AttoForm> iterSeq = listaDecretiDissequestro.iterator();
		while (iterSeq.hasNext()) {
			String descrizione = "";
			ComboForm combo = new ComboForm();
			AttoForm decreto = iterSeq.next();
			String numAttoView = "";

			if (Utility.isTDecretoRecord(decreto.getTipoAtto().getId())) {

				Criterion c1 = Restrictions.eq("id.codiKIdDecreto", decreto.getCodAtto());
				Criterion c2 = Restrictions.eq("id.codiKIdUffOwner", idUffOwn);
				Object obj = attoService.getAttoByPrimaryKey(TDecreto.class, c1, c2);
				if (obj != null) {
					TDecreto ist = (TDecreto) obj;
					numAttoView = AttoServiceImpl.builNumeroAttoView(ist);
					decreto.setNumeroAtto(numAttoView);
				}
			}

			if (decreto.getStatoAtto().getId().equals("" + Constants.STATOATTO_REGISTRATO)) {
				descrizione = numAttoView + " depositato il " + Utility.convertDateToString(decreto.getDataDeposito());
				combo.setId("" + decreto.getCodAtto());
				combo.setDescrizione(descrizione);
				if (decreto.getCategoriaAtto().getId().equals(Constants.DECRETO_DECISORIO_I_GRADO))
				{
					listaDecretiCombo= new ArrayList<ComboForm>();
					listaDecretiCombo.add(combo);
					break;
				}
				else listaDecretiCombo.add(combo);
			}
		}

		if (!Utility.isDefinito(listaDecretiCombo)) {
			super.setMessage(Constants.MESSAGE, "atto.decreto.opposizioneDissequestro.noDecretiDissequestro", null, false);
			return "/procedimento/atto/nuovoAtto";
		}

		modelMap.addAttribute("attoForm", attoForm);
		modelMap.addAttribute("listaDecreti", listaDecretiCombo);
		return "/procedimento/atto/copertinaDecretoOpposizioneDissequestro";
	}

	public String nuovoRevocaDecretoOpposizioneDissequestro(@Valid AttoForm attoForm, BindingResult result, HttpServletRequest request,
			ModelMap modelMap, String categoriaAtto) {

		ProcedimentoForm procedimento = recuperaProcedimento();
		int idUffOwn = recuperaIdUfficioUtente();
		List<AttoForm> listaDecretiOpposizione = attoService.getAttiRevocaOpposizioneDissequestro(procedimento.getIdProcedimento());

		List<ComboForm> listaDecretiCombo = new ArrayList<ComboForm>();
		Iterator<AttoForm> iterSeq = listaDecretiOpposizione.iterator();
		while (iterSeq.hasNext()) {
			String descrizione = "";
			ComboForm combo = new ComboForm();
			AttoForm decreto = iterSeq.next();
			String numAttoView = "";

			if (Utility.isTDecretoRecord(decreto.getTipoAtto().getId())) {

				Criterion c1 = Restrictions.eq("id.codiKIdDecreto", decreto.getCodAtto());
				Criterion c2 = Restrictions.eq("id.codiKIdUffOwner", idUffOwn);
				Object obj = attoService.getAttoByPrimaryKey(TDecreto.class, c1, c2);
				if (obj != null) {
					TDecreto ist = (TDecreto) obj;
					numAttoView = AttoServiceImpl.builNumeroAttoView(ist);
					decreto.setNumeroAtto(numAttoView);
				}
			}

			if (decreto.getStatoAtto().getId().equals("" + Constants.STATOATTO_REGISTRATO)) {
				descrizione = numAttoView + " depositato il " + Utility.convertDateToString(decreto.getDataDeposito());
				combo.setId("" + decreto.getCodAtto());
				combo.setDescrizione(descrizione);
				listaDecretiCombo.add(combo);
			}
		}

		if (!Utility.isDefinito(listaDecretiCombo)) {
			super.setMessage(Constants.MESSAGE, "atto.decreto.revocaOpposizioneDissequestro.noDecretiOpposizione", null, false);
			return "/procedimento/atto/nuovoAtto";
		}

		modelMap.addAttribute("attoForm", attoForm);
		modelMap.addAttribute("listaDecreti", listaDecretiCombo);
		return "/procedimento/atto/copertinaRevocaDecretoOpposizioneDissequestro";
	}

	public String nuovaRevocazioneConfisca(@Valid AttoForm attoForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {

		ProcedimentoForm procedimento = recuperaProcedimento();
		int idUffOwn = recuperaIdUfficioUtente();
		List<AttoForm> listaDecreti = attoService.getAttiRevocazioneConfisca(procedimento.getIdProcedimento());

		List<ComboForm> listaDecretiCombo = new ArrayList<ComboForm>();
		Iterator<AttoForm> iterSeq = listaDecreti.iterator();
		while (iterSeq.hasNext()) {
			String descrizione = "";
			ComboForm combo = new ComboForm();
			AttoForm decreto = iterSeq.next();
			String numAttoView = "";

			if (Utility.isTDecretoRecord(decreto.getTipoAtto().getId())) {

				Criterion c1 = Restrictions.eq("id.codiKIdDecreto", decreto.getCodAtto());
				Criterion c2 = Restrictions.eq("id.codiKIdUffOwner", idUffOwn);
				Object obj = attoService.getAttoByPrimaryKey(TDecreto.class, c1, c2);
				if (obj != null) {
					TDecreto ist = (TDecreto) obj;
					numAttoView = AttoServiceImpl.builNumeroAttoView(ist);
					decreto.setNumeroAtto(numAttoView);
				}
			}

			if (decreto.getStatoAtto().getId().equals("" + Constants.STATOATTO_REGISTRATO)) {
				descrizione = numAttoView + " depositato il " + Utility.convertDateToString(decreto.getDataDeposito());
				combo.setId("" + decreto.getCodAtto());
				combo.setDescrizione(descrizione);
				listaDecretiCombo.add(combo);
			}
		}

		if (!Utility.isDefinito(listaDecretiCombo)) {
			super.setMessage(Constants.MESSAGE, "atto.decreto.revocazione.noDecretiConfisca", null, false);
			return "/procedimento/atto/nuovoAtto";
		}

		modelMap.addAttribute("attoForm", attoForm);
		modelMap.addAttribute("listaDecreti", listaDecretiCombo);
		return "/procedimento/atto/copertinaRevocazioneConfisca";
	}
	
	public ModelAndView misuraDecretoApplicazioneProvvisoriaRefresh(AttoForm attoForm, BindingResult result, ModelMap modelMap,HttpServletRequest request) {
        SoggettoForm soggettoForm=attoForm.getSoggetto();
		String lPagina = getMisuraDecretoApplicazioneProvvisoria(soggettoForm, attoForm , modelMap,true, request);
        attoForm.setSoggetto(soggettoForm);
        ModelAndView mnv = new ModelAndView(lPagina, "attoForm", attoForm);    
		// variabile booleana per la gestione della funzionalità di 'Indietro'
		addBreadcrumbs("POST", "/procedimento/atti/elencoAttiOrdineCronologico.htm", null);
		addBreadcrumbs("POST", "procedimento/atto/copertinaAtto.htm", null);
		return mnv;
	}
}
