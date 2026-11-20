package it.giustizia.smp.service.utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.AutoPopulatingList;

import it.giustizia.smp.controller.BreadcrumbsController;
import it.giustizia.smp.controller.procedimento.UdienzaController;
import it.giustizia.smp.dao.StaticListLoader;
import it.giustizia.smp.dto.GestioneUdienzaDto;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.MagistratoForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.form.UdienzaForm;
import it.giustizia.smp.model.TProcedimento;
import it.giustizia.smp.service.UdienzaService;
import it.giustizia.smp.service.utility.component.ComboUtilComponent;
import it.giustizia.smp.service.utility.component.UfficiUtilComponent;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;

@Service
public class UdienzaUtilService extends BreadcrumbsController {

	@Autowired
	private ComboUtilComponent comboUtilComponent;
	@Autowired
	private UdienzaService udienzaService;
	@Autowired
	private UfficiUtilComponent ufficioUtilComponent;
	
	@SuppressWarnings("unchecked")
	public void salvaModificaUdienza(UdienzaForm udienzaForm, long idUfficio, String codiceUtente, long idProcedimento, HttpServletRequest request) {

		if (udienzaForm.getCodUdienza() == 0) {
			// salvataggio di una nuova udienza
			if (udienzaForm.getAllegato().getFileData() != null && udienzaForm.getAllegato().getFileData().getSize() > 0) {
				udienzaForm.getAllegato().setFileName(udienzaForm.getAllegato().getFileData().getOriginalFilename());
				udienzaForm.getAllegato().setContentType(udienzaForm.getAllegato().getFileData().getContentType());
			}
			Map<String, Long> resultMap;
			if ((Integer) request.getSession().getAttribute("idTipoUfficio") == Constants.TIPO_UFFICIO_CORTE_D_APPELLO)
				resultMap = attoService.salvaUdienza(idUfficio, codiceUtente, idProcedimento, Long.parseLong(Constants.TIPO_ATTO_DECRETO_II_GRADO),
						Long.parseLong(Constants.DECRETO_FISSAZIONE_UDIENZA_II_GRADO), udienzaForm);
			else
				resultMap = attoService.salvaUdienza(idUfficio, codiceUtente, idProcedimento, Long.parseLong(Constants.TIPO_ATTO_DECRETO),
						Long.parseLong(Constants.DECRETO_FISSAZIONE_UDIENZA), udienzaForm);
			udienzaForm.setCodUdienza(resultMap.get("ID_UDIENZA"));
			udienzaForm.setCodAtto(resultMap.get("ID_ATTO"));
			// super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation",
			// "INSERIMENTO", false);
		} else {
			// modifica di un'udienza già presente in base dati
			this.inizializzaDocumento(udienzaForm, request);
			attoService.modificaUdienza(idUfficio, udienzaForm);
			super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", null, false);
		}
	}

	public void salvaModificaUdienzaDecretoSeqConv(UdienzaForm udienzaForm, AttoForm attoForm, long idUfficio, String codiceUtente,
			long idProcedimento, long codUdienzaFiss, long codUdienzaVerbRis, HttpServletRequest request) {

		if (udienzaForm.getCodUdienza() == 0) {
			// salvataggio di una nuova udienza
			Map<String, Long> resultMap = attoService.salvaUdienzaDecretoSeqConv(idUfficio, codiceUtente, idProcedimento, attoForm.getCodAtto(),
					codUdienzaFiss, codUdienzaVerbRis, udienzaForm);
			udienzaForm.setCodUdienza(resultMap.get("ID_UDIENZA"));
			udienzaForm.setCodAtto(attoForm.getCodAtto());
			super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation", "INSERIMENTO", false);
		} else {
			// modifica di un'udienza già presente in base dati
			udienzaForm.setCodAtto(attoForm.getCodAtto());
			attoService.modificaUdienza(idUfficio, udienzaForm);
			super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", null, false);
		}
	}
	
	// metodo mutuato da UdienzaController.caricaInformazioniUdienza
	public ModelMap caricaUdienzaVerbale(String detMod, UdienzaForm udienzaForm, long idAtto, ModelMap modelMap, HttpServletRequest request,AttoForm attoForm) {

		List<MagistratoForm> collegio = new ArrayList<MagistratoForm>();
		List<MagistratoForm> listaPmUdienza = new ArrayList<MagistratoForm>();
		List<SoggettoForm> soggettiConvocati = new ArrayList<SoggettoForm>();
		List<SoggettoForm> difensori = new ArrayList<SoggettoForm>();
		List<String> flgSogConv = new AutoPopulatingList<String>(String.class);
		List<String> flgDif = new AutoPopulatingList<String>(String.class);
		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		long idUfficioUtente = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		Integer idUffOwn = (Integer) request.getSession().getAttribute("idUffOwner");
		long udienzaSelected = udienzaForm.getCodUdienza();
		String codiceUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);

		if (attoForm != null && attoForm.getIdProcedimentoCdatrib() != null && attoForm.getIdProcedimentoCdatrib() > 0){
			TProcedimento procedi= procedimentoService.getProcedimentoById(attoForm.getIdProcedimentoCdatrib());
			idUffOwn=procedi.getTAnUfficioByCodiFkIdUffOwner().getCodiKIdUfficio();
			idUfficioUtente=idUffOwn;
			idProcedimento=attoForm.getIdProcedimentoCdatrib();
			AttoForm attoForm1 = attoService.dettaglioAtto(idUffOwn, idAtto, idProcedimento, codiceUtente);
			if (attoForm1!= null && attoForm1.getCategoriaAtto() != null  && attoForm1.getCategoriaAtto().getId() != null){
				attoForm = attoForm1;
				attoForm.setIdProcedimentoCdatrib(idProcedimento);
			}
		
		}		
		
		udienzaForm = udienzaService.dettaglioUdienzaSelezionata(idProcedimento, idUfficioUtente, udienzaSelected);
		// nuova gestione verbale udienza
		udienzaForm.setCodAtto(idAtto);
		if ((Integer) request.getSession().getAttribute("idTipoUfficio") == Constants.TIPO_UFFICIO_CORTE_D_APPELLO
				|| String.valueOf(request.getSession().getAttribute("idTipoUfficio")).equals(Constants.TIPO_UFFICIO_TRIBUNALE)) {
			// carico l'allegato nel formUdienza più gli altri dati dell'atto
			AttoForm tmp = attoService.dettaglioAtto(idUffOwn, idAtto, idProcedimento, codiceUtente);
			if (request.getSession().getAttribute(Constants.ATTO) != null)
				request.getSession().removeAttribute(Constants.ATTO);
			request.getSession().setAttribute(Constants.ATTO, tmp);
			if (tmp.getAllegato().getFileName() != null) {
				udienzaForm.getAllegato().setDocumento(tmp.getAllegato().getDocumento());
				udienzaForm.getAllegato().setFileName(tmp.getAllegato().getFileName());
				udienzaForm.getAllegato().setContentType(tmp.getAllegato().getContentType());
			}
			udienzaForm.getAllegato().setFirmaRemota(tmp.getAllegato().getFirmaRemota());
			udienzaForm.getAllegato().setAllegatoRimosso(tmp.getAllegato().isAllegatoRimosso());
			udienzaForm.setTipoAtto(tmp.getTipoAtto());
			udienzaForm.setStatoAtto(tmp.getStatoAtto());
			udienzaForm.setDataRegistrazione(tmp.getDataRegistrazione());
		}
		if (detMod.equals("ModificaUdi"))
			udienzaService.copiaDatiProcPerUdienza(udienzaSelected, idUfficioUtente, codiceUtente, idProcedimento);
		collegio = udienzaService.listaMagistratiCollegioUdienza(idProcedimento, idUfficioUtente, udienzaSelected);
		listaPmUdienza = udienzaService.listaMagistratiPmUdienza(idUfficioUtente, udienzaSelected);
		soggettiConvocati = udienzaService.listaSoggettiConvocatiUdienza(idProcedimento, idUfficioUtente, udienzaSelected);
		difensori = udienzaService.listaDifensoriUdienza(idUfficioUtente, udienzaSelected);
		List<SoggettoForm> soggettiConvocatiNew = new ArrayList<SoggettoForm>();
		List<SoggettoForm> difensoriNew = new ArrayList<SoggettoForm>();
		for (int i = 0; i < soggettiConvocati.size(); i++) {
			SoggettoForm soggForm = soggettiConvocati.get(i);
			String flgpres = soggForm.getFlgPresenza();
			if (detMod.equals("DettaglioUdi")) {
				if (flgpres != null && flgpres.equalsIgnoreCase("S"))
					soggForm.setFlgPresenza("Si");
				else if (flgpres != null && flgpres.equalsIgnoreCase("N"))
					soggForm.setFlgPresenza("No");
				else
					soggForm.setFlgPresenza("");
				soggettiConvocatiNew.add(soggForm);
			}
			flgSogConv.add(flgpres);
		}
		udienzaForm.setAllFlgPresSogConv(flgSogConv);
		for (int i = 0; i < difensori.size(); i++) {
			SoggettoForm soggForm = difensori.get(i);
			String flgpres = soggForm.getFlgPresenza();
			if (detMod.equals("DettaglioUdi")) {
				if (flgpres != null && flgpres.equalsIgnoreCase("S"))
					soggForm.setFlgPresenza("Si");
				else if (flgpres != null && flgpres.equalsIgnoreCase("N"))
					soggForm.setFlgPresenza("No");
				else
					soggForm.setFlgPresenza("");
				difensoriNew.add(soggForm);
			}
			flgDif.add(flgpres);
		}
		udienzaForm.setAllFlgPresDif(flgDif);
		if (detMod.equals("DettaglioUdi")) {
			soggettiConvocati = soggettiConvocatiNew;
			difensori = difensoriNew;
		} else { // "ModificaUdi"
			udienzaForm.setDataUdienzaStr(Utility.convertDateToString(udienzaForm.getDataUdienza()));
			// non si possono levare dalla sessione
			request.getSession().setAttribute("collegio_Udienza", collegio);
			request.getSession().setAttribute("lista_Pm_Udienza", listaPmUdienza);
			request.getSession().setAttribute("lista_SoggConv_Udienza", soggettiConvocati);
			request.getSession().setAttribute("lista_Dif_Udienza", difensori);
			Date dataUdienzaSelected = udienzaForm.getDataUdienza();
			String dataSring = Utility.convertDateToString(dataUdienzaSelected);
			ComboForm procInUdienza = commonService.getProcuraInUdienzaAssociata(idProcedimento);
			long idProcUdi = 0l;
			if (procInUdienza != null)
				idProcUdi = Long.parseLong(procInUdienza.getId());
			List<ComboForm> listAllPmProcUdi = udienzaService.elencoPmProcUdi(dataSring, idProcUdi);
			request.getSession().setAttribute("lista_All_Pm", listAllPmProcUdi);
			List<ComboForm> listPmUdi = new ArrayList<ComboForm>();
			listPmUdi = comboUtilComponent.listaEsclusioneGiaAssociati(listAllPmProcUdi, listaPmUdienza);
			request.getSession().setAttribute("lista_Combo_Pm", listPmUdi);
			modelMap.addAttribute("elMagPmUdienza", listPmUdi);
		}
		modelMap.addAttribute("collegioUdienza", collegio);
		modelMap.addAttribute("listaPmUdienza", listaPmUdienza);
		modelMap.addAttribute("soggConvUdienza", soggettiConvocati);
		modelMap.addAttribute("difUdienza", difensori);
		modelMap.addAttribute("esiti", StaticListLoader.getTipiEsitoUdienza());
		modelMap.addAttribute("udienzaForm", udienzaForm);
		return modelMap;

	}
	
	public GestioneUdienzaDto gestioneUdienzaPerInvioDecreto(AttoForm attoForm, ModelMap modelMap, int idUffOwn, int idUfficio, long idProcedimento,
			AttoForm attoTmp) {
		GestioneUdienzaDto gestUdienza = new GestioneUdienzaDto();
		// *** INIZIO GESTIONE UDIENZA DEL DECRETO ***
		if (attoTmp.getFkUdienzaDecreto() > 0L) {// Se l'Atto ha un'Udienza
			// Verifico se presente un'Udienza in stato FISSATA
			List<UdienzaForm> udienzaFiss = udienzaService.listaUdienzePerProcedimentoEStatoUdienza(idProcedimento, idUfficio,
					String.valueOf(UdienzaController.STATO_UDIENZA_FISSATA));
			if (udienzaFiss.size() > 0) {
				gestUdienza.setCodUdienzaFiss(udienzaFiss.get(0).getCodUdienza());
				gestUdienza.setStatoUdienzaPrec(confrontoDateUdienze(attoTmp.getFkUdienzaDecreto(), udienzaFiss, idUffOwn));
				if (gestUdienza.getStatoUdienzaPrec() == 0L) {// Vuol dire che
																// le Date delle
																// Udienze sono
																// identiche
					attoForm.setDataRegistrazione(null);
					ufficioUtilComponent.caricaUfficiCompetenzaEProcuraInUdienza(modelMap);
					modelMap.addAttribute("attoForm", attoForm);
					super.setMessage(Constants.MESSAGE, "udienza.sequestro.presente.fissata.data.error", null, false);
					gestUdienza.setUrlRitorno("/procedimento/atto/confermaInviaAtti");
					return gestUdienza;
				}
			} else {
				List<UdienzaForm> udienzaInLav = new ArrayList<UdienzaForm>();
				// Verifico se presente un'Udienza In fase di ripianificazione
				// con decreto (12)
				udienzaInLav = udienzaService.listaUdienzePerProcedimentoEStatoUdienza(idProcedimento, Long.valueOf(idUffOwn).longValue(),
						String.valueOf(UdienzaController.STATO_UDIENZA_IN_FASE_DI_RIPIANIFICAZIONE));
				if (udienzaInLav.size() > 0) {
					gestUdienza.setCodUdienzaInFaseDiRip(udienzaInLav.get(0).getCodUdienza());
					gestUdienza.setStatoUdienzaPrec(confrontoDateUdienze(attoTmp.getFkUdienzaDecreto(), udienzaInLav, idUffOwn));
				} else {
					// Verifico se presente un'Udienza In remissione su nuovo
					// ruolo (10)
					udienzaInLav = new ArrayList<UdienzaForm>();
					udienzaInLav = udienzaService.listaUdienzePerProcedimentoEStatoUdienza(idProcedimento, Long.valueOf(idUffOwn).longValue(),
							String.valueOf(UdienzaController.STATO_UDIENZA_IN_REMISSIONE_SU_NUOVO_RUOLO));
					if (udienzaInLav.size() > 0) {
						gestUdienza.setCodUdienzaInFaseDiRim(udienzaInLav.get(0).getCodUdienza());
						gestUdienza.setStatoUdienzaPrec(UdienzaController.STATO_UDIENZA_RIMESSA_SU_RUOLO);
					}
				}
			}
		}
		// *** FINE GESTIONE UDIENZA DEL DECRETO ***
		return gestUdienza;
	}
	
	private long confrontoDateUdienze(long fkIdUdienza, List<UdienzaForm> udienzaUltima, int idUffOwn) {

		String dataUdienzaPrecStr = null;
		String dataUdienzaPrecCompl = null;
		String dataUdienzaAttoStr = null;
		String dataUdienzaAttoCompl = null;
		String oraUdienzaPrecStr = null;
		String oraUdienzaAttoStr = null;
		Date dataUdienzaPrec = null;
		Date dataUdienzaAtto = null;
		long statoUdienzaPrec = 0L;

		// Recupero i dati dell'Udienza dell'Atto
		UdienzaForm udienzaForm = attoService.getUdienzaById(fkIdUdienza, idUffOwn);
		if (udienzaForm != null) {
			dataUdienzaPrecStr = Utility.convertDateToString(udienzaUltima.get(0).getDataUdienza());
			oraUdienzaPrecStr = udienzaUltima.get(0).getOraInizioUdienzaStr() + ":00";
			dataUdienzaPrecCompl = dataUdienzaPrecStr + " " + oraUdienzaPrecStr;
			dataUdienzaAttoStr = Utility.convertDateToString(udienzaForm.getDataUdienza());
			oraUdienzaAttoStr = udienzaForm.getOraInizioUdienza().getId() + ":" + udienzaForm.getMinutiInizioUdienza().getId() + ":00";
			dataUdienzaAttoCompl = dataUdienzaAttoStr + " " + oraUdienzaAttoStr;
			dataUdienzaPrec = Utility.stringToDate(dataUdienzaPrecCompl, "dd/MM/yyyy HH:mm:ss");
			dataUdienzaAtto = Utility.stringToDate(dataUdienzaAttoCompl, "dd/MM/yyyy HH:mm:ss");

			if (dataUdienzaAtto.after(dataUdienzaPrec))
				statoUdienzaPrec = UdienzaController.STATO_UDIENZA_RINVIATA_CON_DECRETO;
			else if (dataUdienzaAtto.before(dataUdienzaPrec))
				statoUdienzaPrec = UdienzaController.STATO_UDIENZA_ANTICIPATA_CON_DECRETO;
		}
		return statoUdienzaPrec;
	}
}
