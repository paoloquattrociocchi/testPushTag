package it.giustizia.smp.controller.procedimento;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.AutoPopulatingList;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import it.giustizia.smp.controller.BaseController;
import it.giustizia.smp.dao.StaticListLoader;
import it.giustizia.smp.form.AllegatoForm;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.MagistratoForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.ProfiloForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.form.UdienzaForm;
import it.giustizia.smp.model.TAtto;
import it.giustizia.smp.model.TTpCatAtto;
import it.giustizia.smp.service.CommonService;
import it.giustizia.smp.service.UdienzaService;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.RequestUtil;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.validation.AllegatoValidation;
import it.giustizia.smp.validation.ProcedimentoValidation;
import it.giustizia.smp.validation.UdienzaValidation;

/**
 * UdienzaController
 * 
 * @author Almaviva S.p.A.
 *         Classe Controller per la gestione dell'udienza
 */
@Controller
public class UdienzaController extends BaseController {

    private List<ComboForm>     esiti;
    private List<ComboForm>     minuti;
    private List<ComboForm>     ore;
    @Autowired
    private CommonService       commonService;
    @Autowired(required = true)
    private HttpServletRequest  request;
    @Autowired
    private UdienzaService      udienzaService;
    @Autowired
    private UdienzaValidation   udienzaValidation;
    @Autowired
    private ProcedimentoValidation  procedimentoValidation;
	@Autowired
	private AllegatoValidation	allegatoValidation;	
    
    public static long STATO_UDIENZA_FISSATA = 1;
    public static long STATO_UDIENZA_VERBALIZZATA = 3;
    public static long STATO_UDIENZA_IN_FASE_DI_ANTICIPAZIONE = 4;
    public static long STATO_UDIENZA_ANTICIPATA_CON_DECRETO = 5;
    public static long STATO_UDIENZA_RINVIATA_CON_DECRETO = 7;
    public static long STATO_UDIENZA_IN_ASSEGNAZIONE_NUOVO_RUOLO = 8;
    public static long STATO_UDIENZA_ASSEGNATA_NUOVO_RUOLO = 9;
    public static long STATO_UDIENZA_IN_REMISSIONE_SU_NUOVO_RUOLO = 10;
    public static long STATO_UDIENZA_RIMESSA_SU_RUOLO = 11;
    public static long STATO_UDIENZA_IN_FASE_DI_RIPIANIFICAZIONE = 12;   

    @RequestMapping(value = "/procedimento/udienza/modificaUdienza.htm", method = RequestMethod.POST, params = { "ConfInvia" })
    public String aggiornaDatiConfUdienza(UdienzaForm udienzaForm, BindingResult result, ModelMap modelMap, HttpServletRequest request) {

        boolean errore = udienzaValidation.validaUdienza(udienzaForm, result, request);
        if (errore) {
            udienzaForm.setFlagModMag(false);
            udienzaForm.setFlagNuovoMag(false);
            udienzaForm.setFlagAssociaMag(false);
            return aggiornaModelMap(modelMap, udienzaForm);
        }
        Integer idUfficioUtente = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
        long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
        String idUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);
        List<SoggettoForm> soggettiConvocati = (List<SoggettoForm>) request.getSession().getAttribute("lista_SoggConv_Udienza");
        List<SoggettoForm> difensori = (List<SoggettoForm>) request.getSession().getAttribute("lista_Dif_Udienza");
        List<String> flgSogConv = udienzaForm.getAllFlgPresSogConv();
        List<String> flgDif = udienzaForm.getAllFlgPresDif();
        // AGGIORNO LA LISTA DEI SOGGETTI CONVOCATI NEL FORM
        List<SoggettoForm> soggettiConvocatiNew = listaAggFlag(soggettiConvocati, flgSogConv);
        udienzaForm.setListaSoggettiConvocatiForm(soggettiConvocatiNew);
        // AGGIORNO LA LISTA DEI DIFENSORI NEL FORM
        List<SoggettoForm> difensoriNew = listaAggFlag(difensori, flgDif);
        udienzaForm.setListaDifensoriForm(difensoriNew);
        ComboForm procInUdienza = commonService.getProcuraInUdienzaAssociata(idProcedimento);
        long idProcUdi = 0l;
        if (procInUdienza != null)
            idProcUdi = Long.parseLong(procInUdienza.getId());
//  nuova gestione verbale udienza
        long tipoUfficio = recuperaIdTipoUfficio();
        long idAtto = 0l;
        if((Integer)request.getSession().getAttribute("idTipoUfficio") == Constants.TIPO_UFFICIO_CORTE_D_APPELLO || String.valueOf(request.getSession().getAttribute("idTipoUfficio")).equals(Constants.TIPO_UFFICIO_TRIBUNALE)){
			if (udienzaForm != null && udienzaForm.getCodAtto() > 0) {
				idAtto = udienzaForm.getCodAtto();
			} else {
				idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
			}
		}
        udienzaForm.setCodAtto(idAtto);
        inizializzaDocumentoUdi(udienzaForm, request);
      //INIZIA GESTIONE DOCUMENTALE
        validaAllegatoEFirma(udienzaForm, result, request);
   	 //FINE GESTIONE DOCUMENTALE
        logger.debug("udienzaForm.getAllegato: "+udienzaForm.getAllegato().getFileName() + "tipo di contenuto:   "+udienzaForm.getAllegato().getContentType());
        udienzaService.aggiornaDatiConfUdienza(idProcedimento, idUfficioUtente, idUtente, udienzaForm, "ConfInvia", idProcUdi, idAtto, tipoUfficio);
        pulisciSession();
        request.getSession().setAttribute("fromMenuAtti", "ordine");
        //INIZIA GESTIONE DOCUMENTALE
  		gestionePiattaformaDocumentale(udienzaForm, request, idUfficioUtente, result);
  		//FINE GESTIONE DOCUMENTALE
        super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", "MODIFICA", false);
     // nuova gestione verbale udienza.   
        if((Integer)request.getSession().getAttribute("idTipoUfficio") == Constants.TIPO_UFFICIO_CORTE_D_APPELLO || String.valueOf(request.getSession().getAttribute("idTipoUfficio")).equals(Constants.TIPO_UFFICIO_TRIBUNALE)){
        	AttoForm attoForm = new AttoForm();
            return elencoAttiOrdineCronologicoDaUdienza(attoForm, request, modelMap);	
        }else{
        	return visualizzaUdienze(new UdienzaForm(), modelMap);
        }
    }

	private String elencoAttiOrdineCronologicoDaUdienza(AttoForm attoForm, HttpServletRequest request, ModelMap modelMap) {

		request.getSession().setAttribute("fromMenuAtti", "ordine");
		ProcedimentoForm procedimento = recuperaProcedimento();
		long idProcedimento = procedimento.getIdProcedimento();
		List<AttoForm> listaAtti = attoService.getAttiOrdineCronologico(idProcedimento, recuperaIdCodiceUtente(), super.printMethodName(),(Boolean)request.getSession().getAttribute("flgNascondiRicevute"));
		modelMap.addAttribute("attoForm", attoForm);
		modelMap.addAttribute("listaAtti", listaAtti);
		this.updateViewAndMenu(procedimento, request);
		return "/procedimento/atto/elencoAttiOrdineCronologico";
	}
    
    @RequestMapping(value = "/procedimento/udienza/modificaUdienza.htm", method = RequestMethod.POST, params = { "Salva" })
    public String aggiornaDatiUdienza(UdienzaForm udienzaForm, BindingResult result, ModelMap modelMap, HttpServletRequest request) {

        Integer idUfficioUtente = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
        long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
        String idUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);
        
        udienzaValidation.validaUdienzaOre(udienzaForm, result, request);
        if (result.hasErrors()) {
        	 return caricaInformazioniUdienza("ModificaUdi", udienzaForm, request, result, modelMap);
        }
        if (!udienzaForm.getOraFineUdienza().getId().equals("-1") && udienzaForm.getMinutiFineUdienza().getId().equals("-1")) {
        	 ComboForm comboFormMinutiFin = new ComboForm(); 
        	 comboFormMinutiFin.setId("00");
        	 comboFormMinutiFin.setDescrizione(null);
        	 udienzaForm.setMinutiFineUdienza(comboFormMinutiFin);
        }
        
        List<SoggettoForm> soggettiConvocati = (List<SoggettoForm>) request.getSession().getAttribute("lista_SoggConv_Udienza");
        List<SoggettoForm> difensori = (List<SoggettoForm>) request.getSession().getAttribute("lista_Dif_Udienza");
        List<String> flgSogConv = udienzaForm.getAllFlgPresSogConv();
        List<String> flgDif = udienzaForm.getAllFlgPresDif();
        // AGGIORNO LA LISTA DEI SOGGETTI CONVOCATI NEL FORM
        List<SoggettoForm> soggettiConvocatiNew = listaAggFlag(soggettiConvocati, flgSogConv);
        udienzaForm.setListaSoggettiConvocatiForm(soggettiConvocatiNew);
        // AGGIORNO LA LISTA DEI DIFENSORI NEL FORM
        List<SoggettoForm> difensoriNew = listaAggFlag(difensori, flgDif);
        udienzaForm.setListaDifensoriForm(difensoriNew);
        long tipoUfficio = recuperaIdTipoUfficio();
        long idAtto = 0l;
      //nuova gestione verbale udienza 
        if((Integer)request.getSession().getAttribute("idTipoUfficio") == Constants.TIPO_UFFICIO_CORTE_D_APPELLO || String.valueOf(request.getSession().getAttribute("idTipoUfficio")).equals(Constants.TIPO_UFFICIO_TRIBUNALE)){
        	if (udienzaForm != null && udienzaForm.getCodAtto() > 0) {
    			idAtto = udienzaForm.getCodAtto();
    		} else {
    			idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
    		}	
        }
        udienzaForm.setCodAtto(idAtto);
        inizializzaDocumentoUdi(udienzaForm, request);
      //INIZIA GESTIONE DOCUMENTALE
       validaAllegatoEFirma(udienzaForm, result, request);
  	  //FINE GESTIONE DOCUMENTALE
       udienzaService.aggiornaDatiConfUdienza(idProcedimento, idUfficioUtente, idUtente, udienzaForm, "Salva", 0L, idAtto, tipoUfficio);
        pulisciSession();
        request.getSession().setAttribute("fromMenuAtti", "ordine");
      //INIZIA GESTIONE DOCUMENTALE
		gestionePiattaformaDocumentale(udienzaForm, request, idUfficioUtente, result);
	  //FINE GESTIONE DOCUMENTALE
        super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation.generic", "MODIFICA", false);
        return caricaInformazioniUdienza("ModificaUdi", udienzaForm, request, result, modelMap);
        
    }

    @RequestMapping(value = "/procedimento/udienza/modificaUdienza.htm", method = RequestMethod.POST, params = { "AggiungiPM" })
    public String aggiungiPmUdienza(UdienzaForm udienzaForm, BindingResult result, ModelMap modelMap, HttpServletRequest request) {

    	udienzaValidation.validateSceltaPm(udienzaForm, result, request);
        if (!result.hasErrors()) {
            MagistratoForm form = new MagistratoForm();
            long udienzaSelected = udienzaForm.getCodUdienza();            
            long idUffOwn = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
            String idUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);
            long idPm = Long.valueOf(udienzaForm.getListaPm().getId());
            form.setId(idPm);
//            form.setRuolo("PM");
            //TODO DA VERIFICARE SE é SEMPRE PM
            form.setRuolo(new ComboForm("1", "PM"));
            udienzaService.gestioneMagistrati(udienzaSelected, idUffOwn, idUtente, form, "inserisci");
            // Aggiorno la lista dei PM relativi all'Udienza
            request.getSession().removeAttribute("lista_Pm_Udienza");
            List<MagistratoForm> listaPmUdienza = udienzaService.listaMagistratiPmUdienza(idUffOwn, udienzaSelected);
            request.getSession().setAttribute("lista_Pm_Udienza", listaPmUdienza);            
            // *** INIZIO MODIFICA NUOVA GESTIONE PM CON IDUFFOWN DELLA PROCURA IN UDIENZA ***
            List<ComboForm> listaPmSession = (List<ComboForm>)request.getSession().getAttribute("lista_Combo_Pm");
            request.getSession().removeAttribute("lista_Combo_Pm");
            List<MagistratoForm> listaEsclusione = new ArrayList<MagistratoForm>();
            listaEsclusione.add(form);
            // Aggiorno la lista dei ComboForm relativa ai PM associabili all'Udienza
            // Escludendo dalla lista già presente nella Session il PM appena selezionato
            List<ComboForm> listPmUdi = listaEsclusioneGiaAssociati(listaPmSession, listaEsclusione);
            request.getSession().setAttribute("lista_Combo_Pm", listPmUdi);
            // *** FINE MODIFICA NUOVA GESTIONE PM CON IDUFFOWN DELLA PROCURA IN UDIENZA ***
        }
        udienzaForm.setFlagModMag(false);
        udienzaForm.setFlagNuovoMag(false);
        udienzaForm.setFlagAssociaMag(false);
        return aggiornaModelMap(modelMap, udienzaForm);
    }

    @RequestMapping(value = "/procedimento/udienza/visualizzaUdienze.htm", method = RequestMethod.POST, params = { "DettaglioUdi" })
    public String dettaglioUdienza(UdienzaForm udienzaForm, BindingResult result, ModelMap modelMap, HttpServletRequest request) {

        udienzaValidation.validateListaUdienze(udienzaForm, request, result);
        if (result.hasErrors()) {
            return visualizzaUdienze(udienzaForm, modelMap);
        } else {
        	return caricaInformazioniUdienza("DettaglioUdi", udienzaForm, request, result, modelMap);
        }
    }

    @RequestMapping(value = "/procedimento/udienza/modificaUdienza.htm", method = RequestMethod.POST, params = { "EliminaMag" })
    public String eliminaCollegioUdienza(UdienzaForm udienzaForm, BindingResult result, ModelMap modelMap, HttpServletRequest request) {

        MagistratoForm form = udienzaForm.getMagistratoForm();
        udienzaValidation.validateSceltaRadioButtonMagistrato(form, result, request, "Elimina");
        if (!result.hasErrors()) {
            long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
            long idUffOwn = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
            String idUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);
            long udienzaSelected = udienzaForm.getCodUdienza();
            long idSelected = form.getId();
            int id = (int) idSelected;
            List<MagistratoForm> collegioUdienza = (List<MagistratoForm>) request.getSession().getAttribute("collegio_Udienza");
            MagistratoForm Magform = (MagistratoForm) collegioUdienza.get(id);
            request.getSession().removeAttribute("collegio_Udienza");
            udienzaService.gestioneMagistrati(udienzaSelected, idUffOwn, idUtente, Magform, "elimina");
            collegioUdienza = udienzaService.listaMagistratiCollegioUdienza(idProcedimento, idUffOwn, udienzaSelected);
            request.getSession().setAttribute("collegio_Udienza", collegioUdienza);
        }
        udienzaForm.setFlagModMag(false);
        udienzaForm.setMagistratoForm(null);
        return aggiornaModelMap(modelMap, udienzaForm);
    }

    @RequestMapping(value = "/procedimento/udienza/modificaUdienza.htm", method = RequestMethod.POST, params = { "EliminaPM" })
    public String eliminaPmUdienza(UdienzaForm udienzaForm, BindingResult result, ModelMap modelMap, HttpServletRequest request) {

        MagistratoForm pmForm = udienzaForm.getPmForm();
        udienzaValidation.validateSceltaEliminaPM(pmForm, result, request);
        if (!result.hasErrors()) {
            long idUffOwn = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
            String idUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);
            long udienzaSelected = udienzaForm.getCodUdienza();
            long idSelected = pmForm.getId();
            int id = (int) idSelected;
            List<MagistratoForm> listaPmUdienza = (List<MagistratoForm>) request.getSession().getAttribute("lista_Pm_Udienza");
            MagistratoForm form = (MagistratoForm) listaPmUdienza.get(id);
            udienzaService.gestioneMagistrati(udienzaSelected, idUffOwn, idUtente, form, "elimina");
            // Aggiorno la lista dei PM relativi all'Udienza
            request.getSession().removeAttribute("lista_Pm_Udienza");
            listaPmUdienza = udienzaService.listaMagistratiPmUdienza(idUffOwn, udienzaSelected);
            request.getSession().setAttribute("lista_Pm_Udienza", listaPmUdienza);            
            // *** INIZIO MODIFICA NUOVA GESTIONE PM CON IDUFFOWN DELLA PROCURA IN UDIENZA ***
            List<ComboForm> listaPmSession = (List<ComboForm>)request.getSession().getAttribute("lista_Combo_Pm");
            request.getSession().removeAttribute("lista_Combo_Pm");
            List<ComboForm> listaAllPmSession = (List<ComboForm>)request.getSession().getAttribute("lista_All_Pm");
            // Aggiorno la lista dei ComboForm relativa ai PM associabili all'Udienza
            // aggiungendo alla lista già presente nella Session il PM appena eliminato
            List<ComboForm> listaPm = new ArrayList<ComboForm>();
            listaPm = listaEsclusioneGiaAssociati(listaAllPmSession, listaPmUdienza);
            request.getSession().setAttribute("lista_Combo_Pm", listaPm);
            // *** FINE MODIFICA NUOVA GESTIONE PM CON IDUFFOWN DELLA PROCURA IN UDIENZA ***
        }
        udienzaForm.setFlagModMag(false);
        udienzaForm.setFlagNuovoMag(false);
        udienzaForm.setFlagAssociaMag(false);
        udienzaForm.setMagistratoForm(null);
        udienzaForm.setPmForm(null);
        return aggiornaModelMap(modelMap, udienzaForm);
    }

    @ModelAttribute("esiti")
    public List<ComboForm> getEsiti() {

        esiti = StaticListLoader.getTipiEsitoUdienza();
        return esiti;
    }

    @ModelAttribute("minuti")
    public List<ComboForm> getMinuti() {

        minuti = StaticListLoader.getMinuti();
        return minuti;
    }

    @ModelAttribute("ore")
    public List<ComboForm> getOre() {

        ore = StaticListLoader.getOre();
        return ore;
    }

    @RequestMapping(value = "/procedimento/udienza/modificaUdienza.htm", method = RequestMethod.POST)
    public String impostaEsitoUdienza(UdienzaForm udienzaForm, BindingResult result, ModelMap modelMap, HttpServletRequest request) {

        udienzaForm.setFlagModMag(false);
        udienzaForm.setFlagNuovoMag(false);
        udienzaForm.setFlagAssociaMag(false);
        if(udienzaForm.getIndirizzoAulaUdienzaRinvio()==null){
            ProfiloForm profiloUtente = (ProfiloForm) request.getSession().getAttribute("profilo");
            udienzaForm.setIndirizzoAulaUdienzaRinvio(profiloUtente.getIndirizzoUfficio());
        }
        return aggiornaModelMap(modelMap, udienzaForm);
    }

    @RequestMapping(value = "/procedimento/udienza/dettaglioUdienza.htm", method = RequestMethod.POST, params = { "Indietro" })
    public String indietroDettaglioUdienza(UdienzaForm udienzaForm, BindingResult result, ModelMap modelMap, HttpServletRequest request) {

        udienzaForm.setCodUdienza(0);
        return visualizzaUdienze(udienzaForm, modelMap);
    }

    @RequestMapping(value = "/procedimento/udienza/modificaUdienza.htm", method = RequestMethod.POST, params = { "Indietro" })
    public String indietroModificaUdienza(UdienzaForm udienzaForm, BindingResult result, ModelMap modelMap, HttpServletRequest request) {

        pulisciSession();
        udienzaForm.setCodUdienza(0);
        return visualizzaUdienze(udienzaForm, modelMap);
    }

    @RequestMapping(value = "/procedimento/udienza/modificaUdienza.htm", method = RequestMethod.POST, params = { "Associa" })
    public String inserisciMagUdienza(UdienzaForm udienzaForm, BindingResult result, ModelMap modelMap, HttpServletRequest request) {

        boolean errore = false;
        MagistratoForm form = udienzaForm.getMagistratoForm();
        List<MagistratoForm> collegioUdienza = (List<MagistratoForm>) request.getSession().getAttribute("collegio_Udienza");
        udienzaValidation.validateSceltaRadioButtonMagistrato(form, result, request, "Associa");
        if (!result.hasErrors()) {
            udienzaValidation.validateGestioneMagistratiCollegio(form, collegioUdienza, result, request, false);
            if (!result.hasErrors()) {
                long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
                long udienzaSelected = udienzaForm.getCodUdienza();
                long idUffOwn = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
                String idUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);
                request.getSession().removeAttribute("collegio_Udienza");
                udienzaService.gestioneMagistrati(udienzaSelected, idUffOwn, idUtente, form, "inserisci");
                collegioUdienza = udienzaService.listaMagistratiCollegioUdienza(idProcedimento, idUffOwn, udienzaSelected);
                request.getSession().setAttribute("collegio_Udienza", collegioUdienza);
            } else {
                errore = true;
            }
        } else {
            errore = true;
        }
        if (errore) {
            udienzaForm.setFlagModMag(false);
            udienzaForm.setFlagNuovoMag(true);
            udienzaForm.setFlagAssociaMag(true);
        } else {
            udienzaForm.setFlagModMag(false);
            udienzaForm.setFlagNuovoMag(false);
            udienzaForm.setFlagAssociaMag(false);
        }
        if (errore) {
            List<MagistratoForm> risultatiRicerca = (List<MagistratoForm>) request.getSession().getAttribute("lista_RicercaMag_Udienza");
            modelMap.addAttribute("risultati", risultatiRicerca);
        }
        return aggiornaModelMap(modelMap, udienzaForm);
    }

    @RequestMapping(value = "/procedimento/udienza/modificaUdienza.htm", method = RequestMethod.POST, params = { "ModificaMag" })
    public String modificaCollegioUdienza(UdienzaForm udienzaForm, BindingResult result, ModelMap modelMap, HttpServletRequest request) {

        MagistratoForm form = udienzaForm.getMagistratoForm();
        udienzaValidation.validateSceltaRadioButtonMagistrato(form, result, request, "Modifica");
        if (!result.hasErrors()) {
            long idSelected = form.getId();
            int id = (int) idSelected;
            List<MagistratoForm> collegioUdienza = (List<MagistratoForm>) request.getSession().getAttribute("collegio_Udienza");
            MagistratoForm magForm = (MagistratoForm) collegioUdienza.get(id);
            boolean errore = udienzaValidation.validateModificaRuoloMagistrato(magForm, collegioUdienza, result, request);
            if (!errore) {
                udienzaForm.setMagistratoForm(magForm);
                udienzaForm.setFlagModMag(true);
            } else {
                udienzaForm.setFlagModMag(false);
            }
        } else {
            udienzaForm.setFlagModMag(false);
        }
        modelMap.addAttribute("udienzaForm", udienzaForm);
        return aggiornaModelMap(modelMap, udienzaForm);
    }

    @RequestMapping(value = "/procedimento/udienza/modificaUdienza.htm", method = RequestMethod.POST, params = { "ConfModificaMag" })
    public String modificaRuoloMagUdienza(UdienzaForm udienzaForm, BindingResult result, ModelMap modelMap, HttpServletRequest request) {

        // Ho il MagistratoForm in precedenza selezionato
        MagistratoForm form = udienzaForm.getMagistratoForm();
        List<MagistratoForm> collegioUdienza = (List<MagistratoForm>) request.getSession().getAttribute("collegio_Udienza");
        udienzaValidation.validateGestioneMagistratiCollegio(form, collegioUdienza, result, request, false);
        if (!result.hasErrors()) {
            long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
            long idUfficioUtente = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
            String idUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);
            long udienzaSelected = udienzaForm.getCodUdienza();
            udienzaService.gestioneMagistrati(udienzaSelected, idUfficioUtente, idUtente, form, "aggiorna");
            request.getSession().removeAttribute("collegio_Udienza");
            collegioUdienza = udienzaService.listaMagistratiCollegioUdienza(idProcedimento, idUfficioUtente, udienzaSelected);
            request.getSession().setAttribute("collegio_Udienza", collegioUdienza);
            udienzaForm.setFlagModMag(false);
        } else {
            udienzaForm.setFlagModMag(true);
        }
        modelMap.addAttribute("udienzaForm", udienzaForm);
        return aggiornaModelMap(modelMap, udienzaForm);
    }

    @RequestMapping(value = "/procedimento/udienza/visualizzaUdienze.htm", method = RequestMethod.POST, params = { "ModificaUdi" })
    public String modificaUdienza(@ModelAttribute UdienzaForm udienzaForm, BindingResult result, ModelMap modelMap, HttpServletRequest request) {

    	if (!"FISSATA".equals(udienzaForm.getStato().getDescrizione().toUpperCase())) {
    		 super.setMessage(Constants.MESSAGE, "udienza.verbalizzata.error", udienzaForm.getStato().getDescrizione(), false);
             return visualizzaUdienze(new UdienzaForm(), modelMap);
        }
    	udienzaValidation.validateListaUdienze(udienzaForm, request, result);
        if (result.hasErrors()) {
            return visualizzaUdienze(new UdienzaForm(), modelMap);
        } else {
            return caricaInformazioniUdienza("ModificaUdi", udienzaForm, request, result, modelMap);
        }
    }

    @RequestMapping(value = "/procedimento/udienza/modificaUdienza.htm", method = RequestMethod.POST, params = { "NuovoMag" })
    public String nuovoCollegioUdienza(UdienzaForm udienzaForm, BindingResult result, ModelMap modelMap, HttpServletRequest request) {

        List<MagistratoForm> collegioUdienza = (List<MagistratoForm>) request.getSession().getAttribute("collegio_Udienza");
        udienzaValidation.validateGestioneMagistratiCollegio(null, collegioUdienza, result, request, true);
        if (!result.hasErrors()) {
            udienzaForm.setFlagNuovoMag(true);
        } else {
            udienzaForm.setFlagNuovoMag(false);
        }
        udienzaForm.setMagistratoForm(null);
        return aggiornaModelMap(modelMap, udienzaForm);
    }

    @RequestMapping(value = "/procedimento/udienza/modificaUdienza.htm", method = RequestMethod.POST, params = { "Ricerca" })
    public String ricercaMagUdienza(UdienzaForm udienzaForm, BindingResult result, ModelMap modelMap, HttpServletRequest request) {

        MagistratoForm magForm = udienzaForm.getMagistratoForm();
        request.getSession().setAttribute("magistratoSA", magForm);
        long idUfficioUtente = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
        // Lista dei Magistrati da poter inserire nel Collegio - Filtrati
        // eventualmente per Cognome e Nome
        List<MagistratoForm> risultatiRicerca = new ArrayList<MagistratoForm>();
        List<MagistratoForm> risultati = new ArrayList<MagistratoForm>();
        List<MagistratoForm> collegioUdienza = (List<MagistratoForm>) request.getSession().getAttribute("collegio_Udienza");
        Date dataUdienzaSelected = udienzaForm.getDataUdienza();
        String dataSring = Utility.convertDateToString(dataUdienzaSelected);
        risultati = udienzaService.ricercaMagistratiUff(magForm, idUfficioUtente, dataSring);
        risultatiRicerca = listaMagistratiDaAssociare(risultati, collegioUdienza);
        udienzaForm.setFlagModMag(false);
        udienzaForm.setFlagNuovoMag(true);
        udienzaForm.setFlagAssociaMag(true);
        modelMap.addAttribute("risultati", risultatiRicerca);
        request.getSession().setAttribute("lista_RicercaMag_Udienza", risultatiRicerca);
        return aggiornaModelMap(modelMap, udienzaForm);
    }

    @RequestMapping(value = "/procedimento/udienza/modificaUdienza.htm", method = RequestMethod.GET)
    public String ricercaOrdinamentoMagUdienza(UdienzaForm udienzaForm, BindingResult result, ModelMap modelMap, HttpServletRequest request) {

        MagistratoForm magForm = (MagistratoForm) request.getSession().getAttribute("magistratoSA");
        udienzaForm.setMagistratoForm(magForm);
        request.getSession().removeAttribute("magistratoSA");
        long idUfficioUtente = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
        List<MagistratoForm> collegioUdienza = (List<MagistratoForm>) request.getSession().getAttribute("collegio_Udienza");
        List<MagistratoForm> risultati = new ArrayList<MagistratoForm>();
        List<MagistratoForm> risultatiRicerca = new ArrayList<MagistratoForm>();
        request.getSession().setAttribute("magistratoSA", udienzaForm.getMagistratoForm());
        Date dataUdienzaSelected = udienzaForm.getDataUdienza();
        String dataSring = Utility.convertDateToString(dataUdienzaSelected);
        risultati = udienzaService.ricercaMagistratiUff(udienzaForm.getMagistratoForm(), idUfficioUtente, dataSring);
        request.getSession().removeAttribute("lista_RicercaMag_Udienza");
        risultatiRicerca = listaMagistratiDaAssociare(risultati, collegioUdienza);
        request.getSession().setAttribute("lista_RicercaMag_Udienza", risultatiRicerca);
        udienzaForm.setFlagModMag(false);
        udienzaForm.setFlagNuovoMag(true);
        udienzaForm.setFlagAssociaMag(true);
        modelMap.addAttribute("risultati", risultatiRicerca);
        return aggiornaModelMap(modelMap, udienzaForm);
    }

    public void setUdienzaService(UdienzaService udienzaService) {

        this.udienzaService = udienzaService;
    }

    @RequestMapping(value = "/procedimento/udienza/visualizzaUdienze.htm", method = RequestMethod.GET)
    public String visualizzaUdienze(UdienzaForm udienzaForm, ModelMap modelMap) {

        long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
        Integer idUfficioUtente = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
        ProcedimentoForm procedimento = recuperaProcedimento();
		boolean abilitazione = procedimentoValidation.validateAbilitaOperazioniProcura(procedimento, request);
		if (!abilitazione) {
			return "redirect:/procedimento/apri/copertinaProcedimento.htm";
		}
        List<UdienzaForm> udienza = udienzaService.listaUdienzePerProcedimento(idProcedimento, idUfficioUtente);
        modelMap.addAttribute("risultatiRicerca", udienza);
        return "/procedimento/udienza/visualizzaUdienze";
    }

    private String aggiornaModelMap(ModelMap modelMap, UdienzaForm udienzaForm) {

        List<MagistratoForm> collegio = (List<MagistratoForm>) request.getSession().getAttribute("collegio_Udienza");
        List<MagistratoForm> listaPm = (List<MagistratoForm>) request.getSession().getAttribute("lista_Pm_Udienza");
        List<SoggettoForm> soggettiConvocati = (List<SoggettoForm>) request.getSession().getAttribute("lista_SoggConv_Udienza");
        List<SoggettoForm> difensori = (List<SoggettoForm>) request.getSession().getAttribute("lista_Dif_Udienza");
        List<ComboForm> attribute = (List<ComboForm>) request.getSession().getAttribute("lista_Combo_Pm");
        List<ComboForm> listPmUdi = attribute;
        modelMap.addAttribute("collegioUdienza", collegio);
        modelMap.addAttribute("listaPmUdienza", listaPm);
        modelMap.addAttribute("soggConvUdienza", soggettiConvocati);
        modelMap.addAttribute("difUdienza", difensori);
        modelMap.addAttribute("elMagPmUdienza", listPmUdi);
        modelMap.addAttribute("udienzaForm", udienzaForm);
        return "/procedimento/udienza/modificaUdienza";
    }

    private String caricaInformazioniUdienza(String detMod, UdienzaForm udienzaForm, HttpServletRequest request, BindingResult result, ModelMap modelMap) {

        String ritorno = null;
        // Lista dei Magistrati
        List<MagistratoForm> collegio = new ArrayList<MagistratoForm>();
        // Lista dei PM
        List<MagistratoForm> listaPmUdienza = new ArrayList<MagistratoForm>();
        // Lista dei Soggetti Convocati
        List<SoggettoForm> soggettiConvocati = new ArrayList<SoggettoForm>();
        // Lista dei Difensori
        List<SoggettoForm> difensori = new ArrayList<SoggettoForm>();
        List<String> flgSogConv = new AutoPopulatingList<String>(String.class);
        List<String> flgDif = new AutoPopulatingList<String>(String.class);
        long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
        long idUfficioUtente = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
        Integer idUffOwn = (Integer) request.getSession().getAttribute("idUffOwner");
        long udienzaSelected = udienzaForm.getCodUdienza();
        String codiceUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);
        
        udienzaForm = udienzaService.dettaglioUdienzaSelezionata(idProcedimento, idUfficioUtente, udienzaSelected);
       //nuova gestione verbale udienza 
        if((Integer)request.getSession().getAttribute("idTipoUfficio") == Constants.TIPO_UFFICIO_CORTE_D_APPELLO || String.valueOf(request.getSession().getAttribute("idTipoUfficio")).equals(Constants.TIPO_UFFICIO_TRIBUNALE)){
	    	long idAtto = 0l;
			if (udienzaForm != null && udienzaForm.getCodAtto() > 0) {
				idAtto = udienzaForm.getCodAtto();
			} else {
				idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
			}
	        udienzaForm.setCodAtto(idAtto);
	        //carico l'allegato nel form
	        AttoForm tmp = attoService.dettaglioAtto(idUffOwn,udienzaForm.getCodAtto(), idProcedimento, codiceUtente);
			if (tmp.getAllegato().getFileName() != null) {
				udienzaForm.getAllegato().setDocumento(tmp.getAllegato().getDocumento());
				udienzaForm.getAllegato().setFileName(tmp.getAllegato().getFileName());
				udienzaForm.getAllegato().setContentType(tmp.getAllegato().getContentType());
			}
		// metto il tipo Atto nel Form per la gestione dell'allegato
			udienzaForm.setTipoAtto(tmp.getTipoAtto());
			udienzaForm.setStatoAtto(tmp.getStatoAtto());
			udienzaForm.getAllegato().setFirmaRemota(tmp.getAllegato().getFirmaRemota());
			udienzaForm.getAllegato().setAllegatoRimosso(tmp.getAllegato().isAllegatoRimosso());
		}
        if (detMod.equals("ModificaUdi")){
           
            	udienzaService.copiaDatiProcPerUdienza(udienzaSelected, idUfficioUtente, codiceUtente, idProcedimento);
        }        
        // Carico la lista dei non PM già associati all'Udienza (Presenti in T_REL_MAG_UDIENZA)
        collegio = udienzaService.listaMagistratiCollegioUdienza(idProcedimento, idUfficioUtente, udienzaSelected);
        // Carico la lista dei PM già associati all'Udienza (Presenti in T_REL_MAG_UDIENZA)
        listaPmUdienza = udienzaService.listaMagistratiPmUdienza(idUfficioUtente, udienzaSelected);
        // Carico la lista dei Soggetti convocati associati all'Udienza (Presenti in T_REL_SOGINT_UDIENZA)
        soggettiConvocati = udienzaService.listaSoggettiConvocatiUdienza(idProcedimento, idUfficioUtente, udienzaSelected);
        // Carico la lista dei Difensori associati all'Udienza (Presenti in T_REL_AVV_UDIENZA)
        difensori = udienzaService.listaDifensoriUdienza(idUfficioUtente, udienzaSelected);
        List<SoggettoForm> soggettiConvocatiNew = new ArrayList<SoggettoForm>();
        List<SoggettoForm> difensoriNew = new ArrayList<SoggettoForm>();
        for (int i = 0; i < soggettiConvocati.size(); i++) {
            SoggettoForm soggForm = (SoggettoForm) soggettiConvocati.get(i);
            String flgpres = soggForm.getFlgPresenza();
            if (detMod.equals("DettaglioUdi")) {
                if (flgpres!=null && flgpres.equalsIgnoreCase("S")) {
                    soggForm.setFlgPresenza("Si");
                } else if (flgpres!=null && flgpres.equalsIgnoreCase("N")) {
                        soggForm.setFlgPresenza("No");
                } else {
                    soggForm.setFlgPresenza("");
                }
                soggettiConvocatiNew.add(soggForm);
            }
            flgSogConv.add(flgpres);
        }
        udienzaForm.setAllFlgPresSogConv(flgSogConv);
        for (int i = 0; i < difensori.size(); i++) {
            SoggettoForm soggForm = (SoggettoForm) difensori.get(i);
            String flgpres = soggForm.getFlgPresenza();
            if (detMod.equals("DettaglioUdi")) {
                if (flgpres!=null && flgpres.equalsIgnoreCase("S")) {
                    soggForm.setFlgPresenza("Si");
                } else if (flgpres!=null && flgpres.equalsIgnoreCase("N")) {
                        soggForm.setFlgPresenza("No");
                    } else {
                        soggForm.setFlgPresenza("");
                    }
                difensoriNew.add(soggForm);
            }
            flgDif.add(flgpres);
        }
        udienzaForm.setAllFlgPresDif(flgDif);
        if (detMod.equals("DettaglioUdi")) {
            soggettiConvocati = soggettiConvocatiNew;
            difensori = difensoriNew;
            ritorno = "/procedimento/udienza/dettaglioUdienza";
        } else { // "ModificaUdi"
            udienzaForm.setDataUdienzaStr(Utility.convertDateToString(udienzaForm.getDataUdienza()));
            request.getSession().setAttribute("collegio_Udienza", collegio);
            request.getSession().setAttribute("lista_Pm_Udienza", listaPmUdienza);
            request.getSession().setAttribute("lista_SoggConv_Udienza", soggettiConvocati);
            request.getSession().setAttribute("lista_Dif_Udienza", difensori);
            // *** INIZIO NUOVA GESTIONE PM CON IDUFFOWN DELLA PROCURA IN UDIENZA ***
            Date dataUdienzaSelected = udienzaForm.getDataUdienza();
            String dataSring = Utility.convertDateToString(dataUdienzaSelected);
            ComboForm procInUdienza = commonService.getProcuraInUdienzaAssociata(idProcedimento);
            long idProcUdi = 0l;
            if (procInUdienza != null)
                idProcUdi = Long.parseLong(procInUdienza.getId());
            List<ComboForm> listAllPmProcUdi = udienzaService.elencoPmProcUdi(dataSring, idProcUdi);
            request.getSession().setAttribute("lista_All_Pm", listAllPmProcUdi);
            List<ComboForm> listPmUdi = new ArrayList<ComboForm>();
            // Costruisco la lista dei PM selezionabili, escludendo quelli già presenti per l'Udienza
            listPmUdi = listaEsclusioneGiaAssociati(listAllPmProcUdi, listaPmUdienza);
            request.getSession().setAttribute("lista_Combo_Pm", listPmUdi);
            // *** FINE NUOVA GESTIONE PM CON IDUFFOWN DELLA PROCURA IN UDIENZA ***
            modelMap.addAttribute("elMagPmUdienza", listPmUdi);
            ritorno = "/procedimento/udienza/modificaUdienza";
        }
        modelMap.addAttribute("collegioUdienza", collegio);
        modelMap.addAttribute("listaPmUdienza", listaPmUdienza);
        modelMap.addAttribute("soggConvUdienza", soggettiConvocati);
        modelMap.addAttribute("difUdienza", difensori);
        modelMap.addAttribute("udienzaForm", udienzaForm);
        return ritorno;
    }

    // **************** DEFINIZIONE METODI UTILI PRIVATI *****************    
    private List<ComboForm> listaEsclusioneGiaAssociati(List<ComboForm> listAll, List<MagistratoForm> listaPresenti) {

        List<ComboForm> listaFinale = new ArrayList<ComboForm>();
        boolean esiste = false;
        // LISTA DEGLI ID DA ESCLUDERE DI QUELLI GIA' PRESENTI
        ArrayList<String> idEsclusi = new ArrayList<String>();
        for (int i = 0; i < listaPresenti.size(); i++) {
            idEsclusi.add(String.valueOf(listaPresenti.get(i).getId()));
        }
        for (ComboForm comboForm2 : listAll) {
            ComboForm comboForm = (ComboForm) comboForm2;
            String id = comboForm.getId();
            for (int i = 0; i < idEsclusi.size(); i++) {
                String id_escluso = idEsclusi.get(i);
                if (id.equals(id_escluso)) {
                    esiste = true;
                    break;
                }
            }
            if (!esiste) {
                listaFinale.add(comboForm);
            }
            esiste = false;
        }
        return listaFinale;
    }

    private List<SoggettoForm> listaAggFlag(List<SoggettoForm> sogConv_Dif, List<String> presSogConv_Dif) {

        List<SoggettoForm> listaAggiornata = new ArrayList<SoggettoForm>();
        for (int i = 0; i < sogConv_Dif.size(); i++) {
            SoggettoForm sog = sogConv_Dif.get(i);
            sog.setFlgPresenza(presSogConv_Dif.get(i));
            listaAggiornata.add(sog);
        }
        return listaAggiornata;
    }
    
    private List<MagistratoForm> listaMagistratiDaAssociare(List<MagistratoForm> risultati, List<MagistratoForm> collegioUdienza) {

        // LISTA DI MAGISTRATI DA POTER ASSOCIARE, ESCLUSI QUELLI GIA' INSERITI
        List<MagistratoForm> risultatiRicerca = new ArrayList<MagistratoForm>();
        // LISTA DI MAGISTRATI GIA' ASSOCIATI ALL'UDIENZA
        List<MagistratoForm> magistratiAssociati = new ArrayList<MagistratoForm>();
        if (risultati != null) {
            for (MagistratoForm magistratoForm2 : collegioUdienza) {
                MagistratoForm magistratoForm = (MagistratoForm) magistratoForm2;
                magistratiAssociati.add(magistratoForm);
            }
            risultatiRicerca = listaRicercaMagistrati(risultati, magistratiAssociati);
        }
        return risultatiRicerca;
    }

    private List<MagistratoForm> listaRicercaMagistrati(List<MagistratoForm> risultati, List<MagistratoForm> magistratiAssociati) {

        List<MagistratoForm> listaFinale = new ArrayList<MagistratoForm>();
        boolean esiste = false;
        for (MagistratoForm magistratoForm : risultati) {
            MagistratoForm magForm = (MagistratoForm) magistratoForm;
            long id = magForm.getId();
            for (int i = 0; i < magistratiAssociati.size(); i++) {
                long id_escluso = magistratiAssociati.get(i).getId();
                if (id == id_escluso) {
                    esiste = true;
                    break;
                }
            }
            if (!esiste) {
                listaFinale.add(magForm);
            }
            esiste = false;
        }
        return listaFinale;
    }
    
    private void pulisciSession() {

        request.getSession().removeAttribute("collegio_Udienza");
        request.getSession().removeAttribute("lista_All_Pm");
        request.getSession().removeAttribute("lista_Pm_Udienza");
        request.getSession().removeAttribute("lista_SoggConv_Udienza");
        request.getSession().removeAttribute("lista_Dif_Udienza");
        request.getSession().removeAttribute("lista_Combo_MagistratiUff");
        request.getSession().removeAttribute("lista_Combo_Pm");
        request.getSession().removeAttribute("lista_RicercaMag_Udienza");
        MagistratoForm magForm = (MagistratoForm) request.getSession().getAttribute("magistratoSA");
        if (magForm != null) {
            request.getSession().removeAttribute("magistratoSA");
        }
    }
    
    private void validaAllegatoEFirma(UdienzaForm udienzaForm, BindingResult result,
			HttpServletRequest request) {
		
		long idCatAtto = RequestUtil.recuperaIdCategoriaAtto(request);
		if(idCatAtto <= 0){
			idCatAtto = udienzaForm.getCategoriaAtto() != null ? Long.valueOf(udienzaForm.getCategoriaAtto().getId()).longValue() : 0;
		}

		TTpCatAtto tTpCatAtto = commonService.getCategoriaAtto(idCatAtto);			
		
		allegatoValidation.validateAllegatoEFirma(udienzaForm.getAllegato(), result, request, tTpCatAtto);
	
	}
	
    @RequestMapping(value = {"/procedimento/atto/rimuoviAllegatoDaMemoriaDocumentaleUdi.htm"}, method = RequestMethod.POST, params = { "rimuoviAllegatoDaMemoriaDocumentale" })
	public ModelAndView rimuoviAllegatoDaMemoriaDocumentale(@Valid UdienzaForm udienzaForm, BindingResult result, HttpServletRequest request, ModelMap map) throws IOException, SQLException {
		
			udienzaForm.getAllegato().setAllegatoRimosso(true);
			udienzaForm.getAllegato().setDocumentoFirmato("N");
			udienzaForm.getAllegato().getFirmaRemota().setFirmaImmediata(false);
			udienzaForm.getAllegato().setFileName(null);
			return new ModelAndView(caricaInformazioniUdienza("ModificaUdi", udienzaForm, request, result, map), "udienzaForm", udienzaForm);
	}
    
    @RequestMapping(value = {"/procedimento/atto/downloadDocumentaleUdi.htm"}, method = RequestMethod.POST, params = { "downloadDocumentale" })
	public File downloadDocumentale(@Valid UdienzaForm udienzaForm, BindingResult result, HttpServletRequest request, ModelMap map, HttpServletResponse response) throws IOException, SQLException {
    	return caricaDocumento(udienzaForm, result, request, response);
	}
    
    private File caricaDocumento(UdienzaForm udienza, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws IOException {

		long idUfficio = recuperaIdUfficioUtente();
		
        AttoForm attoInSessione = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
        udienza.setCodAtto(attoInSessione.getCodAtto());
        udienza.setIdAtto(attoInSessione.getIdAtto());
		udienza.setTipoAtto(attoInSessione.getTipoAtto());
		udienza.setStatoAtto(attoInSessione.getStatoAtto());
		
		AllegatoForm allegato = new AllegatoForm();
		
		TAtto tAtto = attoService.getTAttoById(udienza.getCodAtto(), idUfficio);
		if(tAtto.getNumeDocumentoPdoc() != null){
			//lo carico dalla PIATT DOC
			//P.Q. 05/07/2022 Ticket 20220627018 Inizio			
			allegato = attoService.extractBlobFromDocumentazione(tAtto.getNumeDocumentoPdoc(), tAtto.getTAnUfficio().getCodiKIdUfficio());
			//P.Q. 05/07/2022 Ticket 20220627018 Fine			
		} else {
			//lo carico da T_ATTO
			allegato.setDocumento(tAtto.getFileDocumento());
		}
		response.setContentType(tAtto.getDescTipoDocumento());
        response.setHeader("Content-Disposition", "attachment;filename=" + tAtto.getDescNomeDocumento());
        OutputStream out = response.getOutputStream();

        try {
        	
        	ByteArrayOutputStream baos = new ByteArrayOutputStream();
        	byte[] buf = new byte[1024];

        	InputStream in = allegato.getDocumento().getBinaryStream();

        	int n = 0;
        	while ((n=in.read(buf))>=0)
        	{
        	   baos.write(buf, 0, n);

        	}

        	in.close();
        	byte[] bytes = baos.toByteArray(); 
        	
        	
			out.write(bytes);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        out.flush();
        return null;

	}
    @RequestMapping(value = {"/procedimento/atto/firmaPosticipataDocumentaleUdi.htm"}, method = RequestMethod.POST, params = { "firmaPosticipataDocumentale" })
	public String firmaPosticipataDocumentale(@Valid UdienzaForm udienzaForm, BindingResult result, HttpServletRequest request, ModelMap map, HttpServletResponse response) throws IOException, SQLException {
	
    	AttoForm attoInSessione = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
    	udienzaForm.setCodAtto(attoInSessione.getCodAtto());
    	udienzaForm.setIdAtto(attoInSessione.getIdAtto());
    	udienzaForm.setTipoAtto(attoInSessione.getTipoAtto());
    	udienzaForm.setStatoAtto(attoInSessione.getStatoAtto());
    	
    	Integer idUfficio = recuperaIdUfficioUtente();
		BigInteger idAttoDocumentale= new BigInteger("-1");
		
		//INIZIA GESTIONE DOCUMENTALE
		validaAllegatoEFirma(udienzaForm, result, request);
		//FINE GESTIONE DOCUMENTALE
		
		if(udienzaForm !=null && udienzaForm.getCodAtto()>0){
		TAtto tAtto = attoService.getTAttoById(udienzaForm.getCodAtto(), idUfficio);
			if(tAtto.getNumeDocumentoPdoc()!=null){
				idAttoDocumentale = Utility.longToBigInteger(tAtto.getNumeDocumentoPdoc());
			}
		}
	    //INIZIA GESTIONE DOCUMENTALE
		gestioneFirmaRemotaDocumento(udienzaForm, idUfficio, result, idAttoDocumentale);
	    //FINE GESTIONE DOCUMENTALE
	
		//url = copertinaAtto(attoForm, result, request, map);
		return caricaInformazioniUdienza("ModificaUdi", udienzaForm, request, result, map);
	//return url;
	}
    
    @RequestMapping(value = {"/procedimento/udienza/modificaUdienza.htm","/procedimento/udienza/dettaglioUdienza.htm"}, method = RequestMethod.POST, params = { "IndietroListaAtti" })
    public String indietroDettaglioUdienza(AttoForm attoForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {
    	request.getSession().setAttribute("fromMenuAtti", "ordine");
		ProcedimentoForm procedimento = recuperaProcedimento();
		long idProcedimento = procedimento.getIdProcedimento();
		List<AttoForm> listaAtti = attoService.getAttiOrdineCronologico(idProcedimento, recuperaIdCodiceUtente(), super.printMethodName(),(Boolean)request.getSession().getAttribute("flgNascondiRicevute"));
		modelMap.addAttribute("listaAtti", listaAtti);
		this.updateViewAndMenu(procedimento, request);
		return "/procedimento/atto/elencoAttiOrdineCronologico";
	} 
    
}
