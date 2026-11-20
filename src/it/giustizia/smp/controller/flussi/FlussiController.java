package it.giustizia.smp.controller.flussi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import it.giustizia.smp.controller.BreadcrumbsController;
import it.giustizia.smp.controller.MenuController;
import it.giustizia.smp.dto.KeyMessageDto;
import it.giustizia.smp.form.AllegatoForm;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.FlussiForm;
import it.giustizia.smp.form.FlussiPoliziaForm;
import it.giustizia.smp.form.FlussiToolAmministratoriForm;
import it.giustizia.smp.form.NumeroRegistroForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.ProfiloForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.model.TAnProcedente;
import it.giustizia.smp.model.TFlussiIn;
import it.giustizia.smp.model.TPoliziaMsg;
import it.giustizia.smp.model.TProcedimento;
import it.giustizia.smp.model.TRelAtti;
import it.giustizia.smp.model.TTpCatAtto;
import it.giustizia.smp.service.AttoService;
import it.giustizia.smp.service.CommonService;
import it.giustizia.smp.service.FlussiService;
import it.giustizia.smp.service.PoliziaService;
import it.giustizia.smp.service.ProcedimentoService;
import it.giustizia.smp.service.msg.AnbscMsgService;
import it.giustizia.smp.service.msg.AnbscMsgService.EsitoFromResponse;
import it.giustizia.smp.utility.AnbscEnum;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Message;
import it.giustizia.smp.validation.AllegatoValidation;
import it.giustizia.smp.validation.FlussiValidation;
import it.giustizia.smp.webservice.client.anbsc.ConfiscaDefinitivaResponse;
import it.giustizia.smp.webservice.client.anbsc.RevocaConfiscaResponse;
import it.giustizia.smp.webservice.client.anbsc.RevocaSequestroResponse;
import it.giustizia.smp.webservice.client.polizia.ProposteMisurePatrimonialiRequest;
import it.giustizia.smp.webservice.client.polizia.ProposteMisurePersonaliRequest;

/**
 * FlussiController
 * 
 * @author Almaviva S.p.A. Classe Controller per la gestione dei beni inerenti
 *         al procedimento
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Controller
public class FlussiController extends BreadcrumbsController {

	@Autowired
	private FlussiService flussiService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private ProcedimentoService procedimentoService;
	@Autowired
	private AttoService attoService;
	@Autowired
	private FlussiValidation flussiValidation;
	@Autowired
	private AllegatoValidation allegatoValidation;
	@Autowired
	private AnbscMsgService anbscMsgService;
	@Autowired
	private PoliziaService poliziaService;
	
	@RequestMapping(value = "/flussi/associaFlussoProposta.htm", method = RequestMethod.POST, params = { "Registra" })
	public String associaFlussoProposta(@Valid FlussiForm flussiForm,
			BindingResult result, ModelMap modelMap, HttpServletRequest request) {

		String codUtente = (String) request.getSession().getAttribute(
				Constants.ID_CODICE_UTENTE);
		Integer idUffOwn = (Integer) request.getSession().getAttribute(
				Constants.ID_UFFICIO_UTENTE);
		ProfiloForm profilo = (ProfiloForm) request.getSession().getAttribute(
				Constants.PROFILO);

		flussiValidation.validate(flussiForm, result);

		/******************** gestione documentale ****************************************/

		// Procura generale della repubblica presso la CdA (8)
		// if (profilo.getIdTipoUfficio() !=
		// Constants.TIPO_UFFICIO_PROCURA_GENERALE) {
		if (flussiForm != null && flussiForm.getAllegato() != null
				&& flussiForm.getAllegato().getFileData() != null) {
			long idCatAtto = 0l;
			if (flussiForm != null && flussiForm.getAttoDto() != null
					&& flussiForm.getAttoDto().getCODI_FK_ID_CAT_ATTO() != null) {
				idCatAtto = Long.valueOf(
						flussiForm.getAttoDto().getCODI_FK_ID_CAT_ATTO())
						.longValue();
			}

			TTpCatAtto tTpCatAtto = commonService.getCategoriaAtto(idCatAtto);
			allegatoValidation.validateAllegatoEFirma(flussiForm.getAllegato(),
					result, request, tTpCatAtto);
		}

		/******************** gestione documentale ****************************************/

		if (result.hasErrors()) {
			return visualizzaFlussoDaElaborare(flussiForm, null, request,
					modelMap, false,false);
		}
		
		//Accettazione Deposito - invocazione WS e download dal documentale del documento con id = nume_copia_pdoc
		//Se l'invocazione o il download dal documentale fallisce viene ricaricata la pagina con i campi liberi secondo la vecchia modalità
		if (flussiForm.isAccettazioneDeposito()) {
			try {
				processAccettazioneDeposito(flussiForm, request);
			} catch (Exception ex) {
				super.setMessage(Constants.MESSAGE, "flussi.accettazionedeposito.error", ex.getMessage(), false);
				result.reject(Constants.MESSAGE);
				flussiForm.setAccettazioneDeposito(false);
				return visualizzaFlussoDaElaborare(flussiForm, null, request,modelMap, false,false);
			}
		}

		List l = null;

		if (flussiForm.getInclude().equals(Constants.PROPOSTA_MSG)
				|| flussiForm.getInclude().equals(
						Constants.PROPOSTA_DA_TRIBUNALE_MSG)
				|| flussiForm.getInclude().equals(
						Constants.PROPOSTA_AGGRAVAMENTO_MSG)
				|| flussiForm.getInclude().equals(
						Constants.PROPOSTA_AGGRAVAMENTO_DA_TRIBUNALE_MSG)) {
			getDocumentoAllegato(flussiForm);
			l = flussiService.processFlussoProposta(flussiForm, codUtente,
					idUffOwn);
		} else if (flussiForm.getInclude().equals(Constants.ISTANZA_DA_TRIBUNALE_MSG)) {
			getDocumentoAllegato(flussiForm);
			l = flussiService.processFlussoFascicoloCauzione(flussiForm, codUtente,
					idUffOwn);
		} else if (flussiForm.getInclude().equals(Constants.SEQUESTRO_MSG_PU)
				|| flussiForm.getInclude().equals(Constants.SEQUESTRO_MSG_PG)
				|| flussiForm.getInclude().equals(Constants.SEQUESTRO_MSG_CDA)
				|| flussiForm.getInclude().equals(
						Constants.SEQUESTRO_ANT_MSG_PU)
				|| flussiForm.getInclude().equals(
						Constants.SEQUESTRO_ANT_MSG_PG)
				|| flussiForm.getInclude().equals(
						Constants.SEQUESTRO_URG_MSG_PU)
				|| flussiForm.getInclude().equals(
						Constants.SEQUESTRO_URG_MSG_PG)) {
			getDocumentoAllegato(flussiForm);
			l = flussiService.processFlussoSequestro(flussiForm, codUtente,
					idUffOwn);
		} else if (flussiForm.getInclude().equals(
				Constants.DECRETO_INCOMPETENZA_RESTITUZIONE_ATTI_MSG)
				|| flussiForm
						.getInclude()
						.equals(Constants.DECRETO_INCOMPETENZA_TRASF_ALTRO_TRIBUNALE_MSG)
				|| flussiForm.getInclude().equals(
						Constants.DECRETO_INCOMPETENZA_PROCURA_MSG)) {
			l = flussiService.processFlussoIncompRestAtti(
					flussiForm.getIdFlusso(), codUtente, idUffOwn);
		} else if (flussiForm.getInclude()
				.equals(Constants.DEFINITIVITA_PU_MSG)
				|| flussiForm.getInclude()
						.equals(Constants.DEFINITIVITA_PP_MSG)
				|| flussiForm.getInclude()
						.equals(Constants.DEFINITIVITA_PG_MSG)
				|| flussiForm.getInclude().equals(
						Constants.DEFINITIVITA_REVOCAZIONE_CONFISCA_MSG)
				|| flussiForm.getInclude().equals(
						Constants.DEFINITIVITA_CDA_MSG)
				|| flussiForm.getInclude().equals(
						Constants.DEFINITIVITA_II_GRADO_MSG)) {
			l = flussiService.processFlussoDefinitivita(
					flussiForm.getIdFlusso(), codUtente, idUffOwn);
		} else if (flussiForm.getInclude().equals(
				Constants.ISTANZA_DIFFIDA_SOGGETTO_MSG)) {
			getDocumentoAllegato(flussiForm);
			l = flussiService.processFlussoIstanzaSoggDiffidato(flussiForm,
					codUtente, idUffOwn);
		} else if (flussiForm.getInclude().equals(
				Constants.DECRETO_DECISORIO_I_GRADO_PU_MSG)
				|| flussiForm.getInclude().equals(
						Constants.DECRETO_DECISORIO_I_GRADO_PP_MSG)
				|| flussiForm.getInclude().equals(
						Constants.DECRETO_DECISORIO_I_GRADO_PG_MSG)) {
			getDocumentoAllegato(flussiForm);
			l = flussiService.processFlussoDecisorioIGrado(flussiForm,
					codUtente, idUffOwn);
		} else if (flussiForm.getInclude().equals(
				Constants.DECRETO_DECISORIO_I_GRADO_PU_CAM_CON_FASCICOLO)
				|| flussiForm.getInclude().equals(
						Constants.DECRETO_DECISORIO_I_GRADO_PG_CAM_CON_FASCICOLO)
						|| flussiForm.getInclude().equals(
								Constants.DECRETO_DECISORIO_I_GRADO_AGGR_CON_FASCICOLO)){
			getDocumentoAllegato(flussiForm);
			l = flussiService.processFlussoDecisorioIGradoFascicolo(flussiForm,
					codUtente, idUffOwn);
		} else if (flussiForm.getInclude().equals(
				Constants.DECRETO_FISSAZIONE_UDIENZA_MSG)
				|| flussiForm.getInclude().equals(
						Constants.DECRETO_ANTICIPAZIONE_UDIENZA_MSG)
				|| flussiForm.getInclude().equals(
						Constants.DECRETO_RINVIO_UDIENZA_MSG)) {
			getDocumentoAllegato(flussiForm);
			l = flussiService.processFlussoDecretoUdienza(flussiForm,
					codUtente, idUffOwn.longValue());
		} else if (flussiForm.getInclude().equals(
				Constants.DECRETO_UDIENZA_CON_FASCICOLO)) {
            getDocumentoAllegato(flussiForm);
			l = flussiService.processFlussoUdienzaFascicolo(flussiForm,
					codUtente, idUffOwn.longValue());
		} else if (flussiForm.getInclude().equals(Constants.VERBALE_RINVIO_UDIENZA_MSG)
				/* 
				 * ISSUE MEV : aggiunta tipologia per visualizzare verbale di udienza di riserva
				 * Numero MEV : 15b
				 * Autore    : Gioggi
				 * Data      : 07 ago 2018
				 * Branch    : MEV_15b
				 */
				|| flussiForm.getInclude().equals(Constants.VERBALE_UDIENZA_RISERVA_MSG)) {
				//***** FINE INTERVENTO MEV_15b *****//
			getDocumentoAllegato(flussiForm);
			l = flussiService.processFlussoVerbaleUdienza(flussiForm,
					codUtente, idUffOwn.longValue());
		} else if (flussiForm.getInclude().equals(
				Constants.DECRETO_REMISSIONE_SU_RUOLO_MSG)
				|| flussiForm.getInclude().equals(
						Constants.DECRETO_ASSEGNAZIONE_NUOVO_RUOLO_MSG)) {
			getDocumentoAllegato(flussiForm);
			l = flussiService.processFlussoRemAssRuoloUdienza(flussiForm,
					codUtente, idUffOwn.longValue());
		} else if (flussiForm.getInclude().equals(
				Constants.DECRETO_CONVALIDA_SEQ_MSG)) {
			// ||
			// flussiForm.getInclude().equals(Constants.DECRETO_CONVALIDA_SEQ_PP_MSG))
			// {
			getDocumentoAllegato(flussiForm);
			l = flussiService.processFlussoDecretoConvalidaSequestro(
					flussiForm, codUtente, idUffOwn.longValue());
		} else if (flussiForm.getInclude().equals(
				Constants.DECRETO_REVOCA_SEQUESTRO_MSG)) {
			getDocumentoAllegato(flussiForm);
			l = flussiService.processFlussoDecretoRevocaSequestro(flussiForm,
					codUtente, idUffOwn.longValue());
		} else if (flussiForm.getInclude().equals(
				Constants.DECRETO_RIGETTO_TOTALE_MSG)) {
			getDocumentoAllegato(flussiForm);
			l = flussiService.processFlussoDecretoRigettoTotaleSequestro(
					flussiForm, codUtente, idUffOwn);
		} else if (flussiForm.getInclude().equals(
				Constants.OPPOSIZIONE_AL_DISSEQUESTRO_MSG)
				|| flussiForm.getInclude().equals(
						Constants.OPPOSIZIONE_AL_DISSEQUESTRO_ATTO_MSG)) {
			getDocumentoAllegato(flussiForm);
			l = flussiService.processFlussoOpposizioneDissequestro(flussiForm,
					codUtente, idUffOwn);
		} else if (flussiForm.getInclude().equals(
				Constants.REVOCAZIONE_CONFISCA_MSG)
				|| flussiForm.getInclude().equals(
						Constants.REVOCAZIONE_CONFISCA_ATTO_MSG)) {
			getDocumentoAllegato(flussiForm);
			l = flussiService.processFlussoRevocazioneConfisca(flussiForm,
					codUtente, idUffOwn);
		} else if (flussiForm.getInclude().equals(
				Constants.SENTENZA_OPPOSIZIONE_AL_DISSEQUESTRO_MSG)) {
			getDocumentoAllegato(flussiForm);
			l = flussiService.processFlussoSentenzaOpposizioneDissequestro(
					flussiForm, codUtente, idUffOwn);
		} else if (flussiForm.getInclude().equals(
				Constants.SENTENZA_REVOCAZIONE_CONFISCA_MSG)) {
			getDocumentoAllegato(flussiForm);
			l = flussiService.processFlussoSentenzaRevocazioneConfisca(
					flussiForm, codUtente, idUffOwn);
		} else if (flussiForm.getInclude().equals(
				Constants.RICORSO_IN_APPELLO_CDA_MSG)
				|| flussiForm.getInclude().equals(
						Constants.RICORSO_IN_APPELLO_PU_A_TRIB_MSG)
				|| flussiForm.getInclude().equals(
						Constants.RICORSO_IN_APPELLO_PG_A_TRIB_MSG)
				|| flussiForm.getInclude().equals(
						Constants.RICORSO_IN_APPELLO_TRIB_A_PU_MSG)
				|| flussiForm.getInclude().equals(
						Constants.RICORSO_IN_APPELLO_TRIB_A_PG_MSG)
				|| flussiForm.getInclude().equals(
						Constants.TIPO_MSG_RIC_AP_SUCCESSIVO)
				|| flussiForm.getInclude().equals(
						Constants.TIPO_MSG_RIC_AP_TARDIVO)
				|| flussiForm.getInclude().equals(
						Constants.RICORSO_IN_CASSAZIONE_DA_TRIB_MSG)) {
			getDocumentoAllegato(flussiForm);
			long idProcedimentoCda = 0l;
			int tipoUfficio = Integer.parseInt(profilo.getIdTipoUfficio() + "");
			if (flussiForm.getInclude().equals(
					Constants.TIPO_MSG_RIC_AP_SUCCESSIVO)
					&& tipoUfficio == Constants.TIPO_UFFICIO_CORTE_D_APPELLO) {
				flussiValidation.validateSceltaProcedimentoInCDA(flussiForm,
						result);
				if (result.hasErrors()) {
					return visualizzaFlussoDaElaborare(flussiForm, result, request, modelMap, false,false);
				}
				idProcedimentoCda = flussiForm.getIdProcedimentoCda();
			}
			l = flussiService.processFlussoRicorsoInAppello(flussiForm,
					codUtente, idUffOwn, idProcedimentoCda);
		} else if (flussiForm.getInclude().equals(
				Constants.TIPO_MSG_ATTO_RIFIUTATO)) {
			l = flussiService.processFlussoAttoRifiutato(flussiForm, codUtente,
					idUffOwn);
		} else if (flussiForm.getInclude().equals(
				Constants.TIPO_MSG_DECRETO_DECISORIO_II_GRADO) ||
				flussiForm.getInclude().equals(
						Constants.TIPO_MSG_DECRETO_DECISORIO_II_GRADO_CAM)
						||
				flussiForm.getInclude().equals(
						Constants.TIPO_MSG_DECRETO_DECISORIO_II_GRADO_AGGRAVAMENTO)
						|| flussiForm.getInclude().equals(
								Constants.TIPO_MSG_PROVV_ISTANZA_CDA_CC)) {
			getDocumentoAllegato(flussiForm);
			l = flussiService.processFlussoDecDecisorioIIGrado(flussiForm,
					codUtente, idUffOwn);
		}else if( flussiForm.getInclude().equals(Constants.TIPO_MSG_PROVV_ISTANZA_FASCICOLO)) {
			getDocumentoAllegato(flussiForm);
			l = flussiService.processFlussoProvvIstFascicolo(flussiForm,
					codUtente, idUffOwn);			
				
		}else if (flussiForm.getInclude().equals(
				Constants.TIPO_MSG_RICHIESTA_PARERE)
				|| flussiForm.getInclude().equals(Constants.TIPO_MSG_PARERE)) {
			getDocumentoAllegato(flussiForm);
			l = flussiService.processFlussoParere(flussiForm, codUtente,
					idUffOwn);
		}else if (flussiForm.getInclude().equals(
				Constants.TIPO_MSG_PARERE_CON_FASCICOLO)) {
			getDocumentoAllegato(flussiForm);
			l = flussiService.processFlussoParereFascicolo(flussiForm, codUtente,
					idUffOwn);
		}else if (flussiForm.getInclude().equals(
				Constants.REVOCA_OPPOSIZIONE_AL_DISSEQUESTRO_MSG)) {
			getDocumentoAllegato(flussiForm);
			l = flussiService.processFlussoRevocaOpposizioneDissequestro(
					flussiForm, codUtente, idUffOwn);
		} else if (flussiForm.getInclude().equals(
				Constants.TIPO_MSG_VERSIONAMENTO)) {
			getDocumentoAllegato(flussiForm);
			l = flussiService.processFlussoVersionamento(
					flussiForm, codUtente, idUffOwn);
		} else if (flussiForm.getInclude().equals(
						Constants.RICORSO_IN_CASSAZIONE_PG_A_CDA_MSG)
				|| flussiForm.getInclude().equals(
						Constants.RICORSO_IN_CASSAZIONE_CDA_A_PG_MSG)				
				|| flussiForm.getInclude().equals(
						Constants.TIPO_MSG_RIC_TARD_CASS_DA_CDA)				
				|| flussiForm.getInclude().equals(
						Constants.TIPO_MSG_RIC_TARD_CASS_DA_PG)) {
			getDocumentoAllegato(flussiForm);
			l = flussiService.processFlussoRicorsoInCassazione(flussiForm,codUtente, idUffOwn);
		} else if (flussiForm.getInclude().equals(
				Constants.PROROGA_SEQUESTRO_MSG)) {
			getDocumentoAllegato(flussiForm);
			l = flussiService.processFlussoProrogaSequestro(
					flussiForm, codUtente, idUffOwn);
		} else if (flussiForm.getInclude().equals(
				Constants.SOSPENSIONE_TEMP_MSG)) {
			getDocumentoAllegato(flussiForm);
			l = flussiService.processFlussoSospensioneTemporanea(
					flussiForm, codUtente, idUffOwn);
		} else if (flussiForm.getInclude().equals(
				Constants.PROROGA_SOSPENSIONE_MSG)) {
			getDocumentoAllegato(flussiForm);
			l = flussiService.processFlussoProrogaSospensioneTemporanea(
					flussiForm, codUtente, idUffOwn);
//		Segnalazione 001
		} else if (flussiForm.getInclude().equals(
				Constants.NRG_DELLA_CASSAZIONE_MSG)) {
			l = flussiService.processFlussoNRGCassazione(
					flussiForm, codUtente, idUffOwn);
		} else if (flussiForm.getInclude().equals(
				Constants.SEQUESTRO_SOSPENSIONE_MSG)) {
			getDocumentoAllegato(flussiForm);
			l = flussiService.processFlussoSequestroSospensioneTemporanea(
					flussiForm, codUtente, idUffOwn);
		} else if (flussiForm.getInclude().equals(
				Constants.DECRETO_APPLICAZIONE_PROVV_MSG) ||
				flussiForm.getInclude().equals(
						Constants.REVOCA_APPLICAZIONE_PROVV_MSG)) {
			getDocumentoAllegato(flussiForm);
			l = flussiService.processFlussoApplicazioneProvv(
					flussiForm, codUtente, idUffOwn);
		} else if (flussiForm.getInclude().equals(Constants.SENTENZA_CASSAZIONE_DA_CDA_MSG) ||
				  flussiForm.getInclude().equals(Constants.SENTENZA_CASSAZIONE_DA_CASS_A_CDA_MSG) ||
				  flussiForm.getInclude().equals(Constants.SENTENZA_CASSAZIONE_DA_CASS_A_TRIB_MSG)||
				  flussiForm.getInclude().equals(Constants.SENTENZA_CASSAZIONE_TRIB_CC) ||
				  flussiForm.getInclude().equals(Constants.SENTENZA_CASSAZIONE_CDA_CC ) ||
				  flussiForm.getInclude().equals(Constants.SENTENZA_CASSAZIONE_REVOCAZIONE_CONFISCA)) {
			if ( flussiForm.getInclude().equals(Constants.SENTENZA_CASSAZIONE_DA_CASS_A_CDA_MSG) ) {				
				long idProcedimento = Long.parseLong(flussiForm.getProcedimentoDto().getCODI_K_ID_PROC());
				long idAtto = Long.parseLong(flussiForm.getAttoDto().getCODI_K_ID_ATTO());
			 	// controllo sulle definitività di secondo grado
				KeyMessageDto ritornoDb = attoService.controllaDefinitivitaIIGradoSuMisure(idProcedimento, idAtto, idUffOwn, codUtente, "SENTENZA",
						super.printMethodName());
				if (ritornoDb.isError()) {
					super.setMessage(Constants.MESSAGE, "messaggio.gestito.db", ritornoDb.getMessage(), false);
					result.reject(Constants.MESSAGE);
					
					return visualizzaFlussoDaElaborare(flussiForm, result, request, modelMap, false,true);
				}
			}
			getDocumentoAllegato(flussiForm);
			l = flussiService.processFlussoSentenzaCassazione(flussiForm, codUtente, idUffOwn);							
		} else if (flussiForm.getInclude().equals(Constants.TIPO_MSG_PER_EFFI_SEQ_A_PU_PG)) {
			getDocumentoAllegato(flussiForm);
			l = flussiService.processFlussoPerditaEfficaciaSequestro(flussiForm, codUtente, idUffOwn.longValue());
		}
		
		Long idProc = null;
		Long idAtto = null;
		Long idAttoSentenza = null;
        String messaggio="";
		if (l != null) {
			HashMap first = (HashMap) l.get(0);
			idProc = (Long) first.get("CODI_K_ID_PROC");
			idAtto = (Long) first.get("CODI_K_ID_ATTO");
			
			// P.Q. MEV 2021/10 Inizio
			if 	(flussiForm.getInclude().equals(Constants.NRG_DELLA_CASSAZIONE_MSG)) {
				idAttoSentenza = (Long) first.get("CODI_K_ID_ATTO_SENTENZA");
			}
			// P.Q. MEV 2021/10 Fine
			
			if 	(flussiForm.getInclude().equals(Constants.DECRETO_UDIENZA_CON_FASCICOLO) ||
				 flussiForm.getInclude().equals(Constants.DECRETO_FISSAZIONE_UDIENZA_MSG)|| 
				 flussiForm.getInclude().equals(Constants.DECRETO_ANTICIPAZIONE_UDIENZA_MSG)|| 
				 flussiForm.getInclude().equals(Constants.DECRETO_RINVIO_UDIENZA_MSG) ||	
				 flussiForm.getInclude().equals(Constants.TIPO_MSG_PARERE_CON_FASCICOLO) ||
				 flussiForm.getInclude().equals(Constants.TIPO_MSG_RICHIESTA_PARERE)||
				 flussiForm.getInclude().equals(Constants.TIPO_MSG_PARERE) ||
				 flussiForm.getInclude().equals(Constants.DECRETO_DECISORIO_I_GRADO_PU_CAM_CON_FASCICOLO)||
				 flussiForm.getInclude().equals(Constants.DECRETO_DECISORIO_I_GRADO_PG_CAM_CON_FASCICOLO) ||
				 flussiForm.getInclude().equals(Constants.DECRETO_DECISORIO_I_GRADO_PU_MSG)||
				 flussiForm.getInclude().equals(Constants.DECRETO_DECISORIO_I_GRADO_PP_MSG)|| 
				 flussiForm.getInclude().equals(Constants.DECRETO_DECISORIO_I_GRADO_PG_MSG)) {
			   messaggio=(String)first.get("MESSAGGIO");   
		   }

		}
     if (messaggio!=null && !messaggio.trim().equals(""))
     {
       super.setMessage(Constants.MESSAGE, messaggio, null, false);
	   result.reject(Constants.MESSAGE);
	   return  listaFlussiDaElaborare(new FlussiForm(), request, modelMap);
     }
     
     	List<Message> customMessageListSuccess = new ArrayList<Message>();
		List<Message> customMessageListError = new ArrayList<Message>();

		ProcedimentoForm procedimentoForm = new ProcedimentoForm();
		procedimentoForm.setIdProcedimento(idProc);
		request.getSession().setAttribute(Constants.ID_PROCEDIMENTO,
				procedimentoForm);
		// modelMap.addAttribute(MESSAGE, "success");

		//super.setMessage(Constants.SUCCESS_MESSAGE, "success", null, true);
		super.addMessageIntoList(customMessageListSuccess, Constants.SUCCESS_MESSAGE, "success", null);
		addBreadcrumbs("GET", "/flussi/flussiDaElaborare.htm", null);

		// INIZIA GESTIONE DOCUMENTALE
		// if(idAtto!= null && profilo.getIdTipoUfficio() !=
		// Constants.TIPO_UFFICIO_PROCURA_GENERALE)
		if (flussiForm != null && flussiForm.getAllegato() != null
				&& flussiForm.getAllegato().getFileData() != null) {
			AttoForm attoForm = new AttoForm();
			attoForm = attoService.getAttoById(idAtto, idUffOwn);
			attoForm.setAllegato(flussiForm.getAllegato());
			gestionePiattaformaDocumentale(attoForm, request, idUffOwn, result);
		}
		// FINE GESTIONE DOCUMENTALE

		//Invio Revoca Confisca ad ANBSC		
		if(flussiForm.getInclude().equals(Constants.SENTENZA_REVOCAZIONE_CONFISCA_MSG))
		{
			// Mev 2020/22 Inizio
			//confermaInviaUtilService.inviaRevocaSequestroAnbsc(procedimento, attoForm, idProcedimento, idAtto, idUffOwn, codiceUtente);
			RevocaConfiscaResponse responseAnbsc = anbscMsgService.inviaRevocaConfisca(idProc, idAtto, idUffOwn);
			
			EsitoFromResponse esitoFromResponse = anbscMsgService.new EsitoFromResponse('0', "");
			
			if(responseAnbsc != null) {
				if(responseAnbsc.getEsito() != null) {
					esitoFromResponse = anbscMsgService.getEsitoFromResponse(responseAnbsc.getEsito());
				}
			}
			
			switch (esitoFromResponse.getEsito()) {
			case 'T':
				super.addMessageIntoList(customMessageListSuccess, Constants.SUCCESS_MESSAGE, "anbsc.invio.ok", null);
				break;
				
			case 'F':
				super.addMessageIntoList(customMessageListError, Constants.MESSAGE, "anbsc.invio.ko", esitoFromResponse.getMessaggio());
				break;
				
			default:				
				super.addMessageIntoList(customMessageListError, Constants.MESSAGE, "anbsc.invio.no.risposta", null);	
				break;

			}
			// Mev 2020/22 fine
		}
		/*
		 * MEV
		 * Modalita sincrona invio confisca definitiva in cassazione
		 */
		// TODO: scrivere bene i commenti
		// P.Q. MEV 2021/10 Inizio
		// L'invio verso ANBSC avviene solo se l'ufficio che acquisisce il flusso e' CDA
		int tipoUfficio = Integer.parseInt(profilo.getIdTipoUfficio() + "");		
		if(flussiForm.getInclude().equals(Constants.NRG_DELLA_CASSAZIONE_MSG)) {
			if (tipoUfficio == Constants.TIPO_UFFICIO_CORTE_D_APPELLO) {
				Map<String, Object> mapProcedimento = procedimentoService.getDettaglioProcedimentoById(idProc.longValue());
				TProcedimento procedimento = (TProcedimento) mapProcedimento.get("proced");
				if(procedimento.getTTpTipoProc().getCodiKIdTipoProc() == Integer.parseInt(Constants.TIPOPROC_PATRIMONIALE) || procedimento.getTTpTipoProc().getCodiKIdTipoProc() == Integer.parseInt(Constants.TIPOPROC_PERSONALE_E_PATRIMONIALE)){
					//Mev 2020/22 Inizio 
					//anbscMsgService.invioConfiscaDefinitiva(idProc, idAtto, idUffOwn, codUtente, AnbscEnum.ANBSC_CONFISCA_DEFINITIVA_CASSAZIONE.getId());
					ConfiscaDefinitivaResponse responseAnbsc = anbscMsgService.invioConfiscaDefinitiva(idProc, idAttoSentenza, idUffOwn, codUtente, AnbscEnum.ANBSC_CONFISCA_DEFINITIVA_CASSAZIONE.getId(), null, true);
					
					EsitoFromResponse esitoFromResponse = anbscMsgService.new EsitoFromResponse('0', "");
					
					if(responseAnbsc != null) {
						if(responseAnbsc.getEsito() != null) {
							esitoFromResponse = anbscMsgService.getEsitoFromResponse(responseAnbsc.getEsito());
						}
					}
					
					switch (esitoFromResponse.getEsito()) {
					case 'T':
						super.addMessageIntoList(customMessageListSuccess, Constants.SUCCESS_MESSAGE, "anbsc.invio.ok", null);
						break;
						
					case 'F':
						super.addMessageIntoList(customMessageListError, Constants.MESSAGE, "anbsc.invio.ko", esitoFromResponse.getMessaggio());
						break;
						
					default:				
						super.addMessageIntoList(customMessageListError, Constants.MESSAGE, "anbsc.invio.no.risposta", null);	
						break;
		
					}	
					
				}
								
			}
			
		}
		//INIZIO INVIO DATI MPS - 8.1 e 8.2
		if (flussiForm.getInclude().equals(Constants.PROPOSTA_MSG)){
			Map<String, Object> mapProcedimento = procedimentoService.getDettaglioProcedimentoById(idProc.longValue());
			TProcedimento procedimento = (TProcedimento) mapProcedimento.get("proced");
			TAnProcedente procedente = (TAnProcedente) mapProcedimento.get("procedente");
    		int idProcedente = procedente.getCodiKIdProced();
    		
			if(procedimento.getTTpTipoProc().getCodiKIdTipoProc() == Integer.parseInt(Constants.TIPOPROC_PERSONALE) || procedimento.getTTpTipoProc().getCodiKIdTipoProc() == Integer.parseInt(Constants.TIPOPROC_PERSONALE_E_PATRIMONIALE)){
				List<Integer> listaDest = poliziaService.getUffDestinatario(idProcedente, idUffOwn.intValue(), procedimento.getCodiKIdProc(), Constants.TIPOPROC_PERSONALE);
	    		if(listaDest!=null){
	    			for(Iterator<Integer> itr = listaDest.iterator(); itr.hasNext();){
	    				int idUfficioDest = itr.next();
						ProposteMisurePersonaliRequest propostaPersonale = poliziaService.createProposteMisurePersonaliRequest(idProc.longValue(), idAtto.longValue(), idUffOwn.intValue(), idUfficioDest);
						/*long idMsg = */poliziaService.insertPoliziaMsgDatabase(Constants.POLIZIA_PROPOSTE_MIS_PERS, idProc, idAtto, propostaPersonale, Constants.POLIZIA_STATO_SERVIZIO_INSERITO, idUffOwn, idUfficioDest, codUtente);
	    			}
	    		}
			}
			if(procedimento.getTTpTipoProc().getCodiKIdTipoProc() == Integer.parseInt(Constants.TIPOPROC_PATRIMONIALE) || procedimento.getTTpTipoProc().getCodiKIdTipoProc() == Integer.parseInt(Constants.TIPOPROC_PERSONALE_E_PATRIMONIALE)){
				List<Integer> listaDest = poliziaService.getUffDestinatario(idProcedente, idUffOwn.intValue(), procedimento.getCodiKIdProc(), Constants.TIPOPROC_PATRIMONIALE);
	    		if(listaDest!=null){
	    			for(Iterator<Integer> itr = listaDest.iterator(); itr.hasNext();){
	    				int idUfficioDest = itr.next();
						ProposteMisurePatrimonialiRequest propostaPatrimoniale = poliziaService.createProposteMisurePatrimonialiRequest(idProc.longValue(), idAtto.longValue(), idUffOwn.intValue(), idUfficioDest);
						/*long idMsg = */poliziaService.insertPoliziaMsgDatabase(Constants.POLIZIA_PROPOSTE_MIS_PATR, idProc, idAtto, propostaPatrimoniale, Constants.POLIZIA_STATO_SERVIZIO_INSERITO, idUffOwn, idUfficioDest, codUtente);
	    			}
	    		}
			}
		}
		//FINE   INVIO DATI MPS - 8.1 e 8.2
		
		super.setMessaggiErrori(customMessageListError, true);
		super.setMessagesSuccess(customMessageListSuccess, true);
		
		return "redirect:/procedimento/apri/copertinaProcedimento.htm";
		// return listaFlussiDaElaborare(flussiForm, request, modelMap);
	}

	@RequestMapping(value = "/flussi/flussiDaElaborare.htm", method = RequestMethod.GET)
	public String listaFlussiDaElaborare(FlussiForm flussiForm,
			HttpServletRequest request, ModelMap modelMap) {

		MenuController.caricaMenu("2", request);
		modelMap.addAttribute("flussiForm", flussiForm);
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
			System.out.println(" -----> flussi da eleborare 1" + " - " + idUfficioUtente);
		   List<FlussiForm> listaFlussi = flussiService.listaFlussiDaElaborare(idUfficioUtente, flussiForm);
		   System.out.println(" 	 -> Totale flussi da eleborare 1" + " =  " + listaFlussi.size());
			if (listaFlussi != null) {
				for (int i = 0; i < listaFlussi.size(); i++) {
					String descUfficioUnita = "";
					FlussiForm flusso = listaFlussi.get(i);
					System.out.println(" ----> id Flusso 1" + " = " + flusso.getIdFlusso());
					if (flusso.getDescSedeUffMittente() != null) {
						descUfficioUnita = flusso.getDescUffMittente()
								.concat(" di ")
								.concat(flusso.getDescSedeUffMittente());
					}
					flusso.setDescSedeUffMittenteUnita(descUfficioUnita);
				}
			}

			PagedListHolder pagedListHolder = new PagedListHolder(listaFlussi);
				
    		   pagedListHolder.setPage(0);
    		   int pageSize = 10;
    		   pagedListHolder.setPageSize(pageSize);
                   	
        	
            String page = "1";
            String npage = "1";
           
            if(request.getParameter("page") != null) {
                page = (String) request.getParameter("page");
            }
            if(request.getParameter("n") != null) {
            	npage = (String) request.getParameter("n");
            }
          

            if("first".equals(page)){
                 pagedListHolder.setPage(0);
            }
            else if("next".equals(page)){
            	 pagedListHolder.setPage(Integer.parseInt(npage)-1);            	          	
                 pagedListHolder.nextPage();
            }           
            else if("previous".equals(page)){   
            	 pagedListHolder.setPage(Integer.parseInt(npage)-1);            	          	           	
                 pagedListHolder.previousPage();
            }
            else if("last".equals(page)){
                 pagedListHolder.setPage(pagedListHolder.getPageCount());
            }
            else {
                 pagedListHolder.setPage(Integer.parseInt(page)-1);            
            }

            //in caso di messaggio di errore
            if(request.getParameter("id_hiddenPagina") != null) {
            	String pageHidden = (String) request.getParameter("id_hiddenPagina");
            	pagedListHolder.setPage(Integer.parseInt(pageHidden)-1); 
            }

 
		modelMap.addAttribute("risultatiRicercaHolder", pagedListHolder);
		addBreadcrumbs("GET", "/flussi/flussiDaElaborare.htm", null);
		return "/flussi/flussiDaElaborare";
	}

	/**
	 * @param flussiService
	 *            the flussiService to set
	 */
	public void setFlussiService(FlussiService flussiService) {

		this.flussiService = flussiService;
	}

	@RequestMapping(value = "/flussi/visualizzaFlussoDaElaborare.htm", method = RequestMethod.POST, params = { "Visualizza" })
	public String visualizzaFlussoDaElaborare(FlussiForm flussiForm,
			BindingResult result, HttpServletRequest request,
			ModelMap modelMap, boolean ricaricaForm,boolean sentenzaCassazione) {

//        String page = (String) request.getParameter("id_hiddenPagina");
		
		String contextPath = request.getContextPath();
		String numProcDest = null;
		int idUffOwn = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		String descUtente = (recuperaIdCodiceUtente().toString());
		ProfiloForm profilo = (ProfiloForm) request.getSession().getAttribute(Constants.PROFILO);

		if (result != null )
         {
			flussiValidation.validateSceltaCheckbox(flussiForm, result, request);
			if (result.hasErrors() && !sentenzaCassazione) {
				return listaFlussiDaElaborare(flussiForm, request, modelMap);
			}
		}
		
		if (!ricaricaForm) {
			long idProc =0l;
			
			
			TFlussiIn flussiTest = flussiService.getFlussoIn(flussiForm.getIdFlusso());
	
			if(flussiTest != null && flussiTest.getTTpTipoFlusso() != null && 
					  (flussiTest.getTTpTipoFlusso().getCodiKIdTipoFlusso() == Integer.parseInt(Constants.SENTENZA_CASSAZIONE_DA_CASS_A_CDA_MSG) ||
							  flussiTest.getTTpTipoFlusso().getCodiKIdTipoFlusso() == Integer.parseInt(Constants.SENTENZA_CASSAZIONE_DA_CASS_A_TRIB_MSG)))
			{				
				String codInclude = Long.toString(flussiTest.getTTpTipoFlusso().getCodiKIdTipoFlusso());					
				flussiService.dettaglioFlussoSentenza(flussiForm,idUffOwn,descUtente);				
				flussiForm.setInclude(codInclude);
				
				 idProc = Long.parseLong(flussiForm.getProcedimentoDto().getCODI_K_ID_PROC());	
				 numProcDest = procedimentoService.getNumeroProcByIdProcedimento(idProc);
			
			}else{
							
				 
				 flussiService.dettaglioFlusso(flussiForm);
            
				 idProc = Long.parseLong(flussiForm.getProcedimentoDto().getCODI_K_ID_PROC());	
			     
				 int idTipoFasc = 0;
				 if(flussiForm!=null && flussiForm.getProcedimentoDto()!=null && flussiForm.getProcedimentoDto().getCODI_FK_ID_TIPO_FASC()!=null) {
					 idTipoFasc = Integer.parseInt(flussiForm.getProcedimentoDto().getCODI_FK_ID_TIPO_FASC());
				 }
				 
				 TProcedimento procedimento = procedimentoService.
						       getIdProcedimentoDestinatario(idProc,flussiForm.getIdUffMittente().intValue(),idTipoFasc,idUffOwn);
				//per la visualizzazione del numero del procedimento
				 if (procedimento!= null) numProcDest=procedimento.getNumeAnnoProc()+"/"+procedimento.getNumeProc();
			    //per la visualizzazione dei Soggetti
				 if ((flussiForm.getListaSoggettoForm()== null || 
					flussiForm.getListaSoggettoForm().isEmpty()) && (procedimento != null))
					 flussiForm.setListaSoggettoForm(procedimentoService.soggettiDirettiInteressatiDelProcedimento(procedimento.getCodiKIdProc(), Constants.RUOLO_DIRETTO, Constants.Flag.S));
			}
			flussiForm.setNumProcDest(numProcDest);
		}

		if (result != null) {
			if (!flussiValidation.validateApriFlusso(flussiForm, request,
					result)) {
				return listaFlussiDaElaborare(flussiForm, request, modelMap);
			}
		}
		flussiForm.setDataRegistrazione(new Date());
		modelMap.addAttribute("azione", contextPath
				+ "/flussi/associaFlussoProposta.htm");

		// se è una proposta da tribunale carica gli accertamenti che hanno dei
		// soggetti in comune
		if (flussiForm.getInclude().equals(Constants.PROPOSTA_DA_TRIBUNALE_MSG)) {
			String listaIdSoggetti = getIdSoggettiProcedimento(flussiForm, request);
			String idProced = flussiForm.getProcedimentoDto()
					.getCODI_FK_ID_PROCED();
			List<ProcedimentoForm> listaAccertamenti = procedimentoService
					.listaAccertamentiConSoggettiProposta(idUffOwn, idProced,
							listaIdSoggetti);
			modelMap.addAttribute("listaAccertamenti", listaAccertamenti);
		}

		// se è un ricorso successivo al primo e sono in corte di appello devo
		// scegliere il procedimento a cui è destinato
		int tipoUfficio = Integer.parseInt(profilo.getIdTipoUfficio() + "");
		if (flussiForm.getInclude()
				.equals(Constants.TIPO_MSG_RIC_AP_SUCCESSIVO)
				&& tipoUfficio == Constants.TIPO_UFFICIO_CORTE_D_APPELLO) {
			List<ProcedimentoForm> listaProcedimenti = procedimentoService
					.getListaProcedimentiRelProcProc(Long.parseLong(flussiForm
							.getProcedimentoDto().getCODI_K_ID_PROC()),
							idUffOwn, Integer.parseInt(flussiForm
									.getProcedimentoDto()
									.getCODI_FK_ID_UFF_OWNER()),
							"RICORSOSUCCESSIVO");
			if (listaProcedimenti != null && !listaProcedimenti.isEmpty()
					&& listaProcedimenti.size() == 1) {

				flussiForm.setIdProcedimentoCda((listaProcedimenti.get(0))
						.getIdProcedimento());
				listaProcedimenti = null;
			}

			modelMap.addAttribute("listaProcedimenti", listaProcedimenti);
		}

		long idCatAtto = 0l;
		if (flussiForm != null && flussiForm.getAttoDto() != null
				&& flussiForm.getAttoDto().getCODI_FK_ID_CAT_ATTO() != null) {
			idCatAtto = Long.valueOf(
					flussiForm.getAttoDto().getCODI_FK_ID_CAT_ATTO())
					.longValue();
		}
//		Segnalazione 001
		if (flussiForm.getInclude().equals(Constants.NRG_DELLA_CASSAZIONE_MSG))
			modelMap.addAttribute("AllegatoObbligatorio",null);
		else
		{
			TTpCatAtto tTpCatAtto = commonService.getCategoriaAtto(idCatAtto);
			modelMap.addAttribute("AllegatoObbligatorio",tTpCatAtto.getFlgAllegObbl());
		}

		addBreadcrumbs("POST", "/flussi/visualizzaFlussoDaElaborare.htm","Visualizza");
		
		//Set dei dati per accettazione deposito
		//Il valore della variabile accettazioneDeposito è == true solo quando si clicca su Visualizza nell'elenco dei flussi
		//Se non ci sono errori accettazioneDeposito resta == true
		//Se ci sono errori accettazioneDeposito diventa == false e la visualizzazione segue la precedente gestione
		if(flussiForm.isAccettazioneDeposito()){
			//Il valore di accettazioneDeposito è == true solo al primo caricamento per consentire il passaggio in questo controllo
			//Per consentire tutte le altre verifiche è impostato subito a false
			flussiForm.setAccettazioneDeposito(false);
			getDatiAccetazioneDeposito(flussiForm, request);
		}
		
		return "/flussi/visualizzaFlussoDaElaborare";
	}

	@RequestMapping(value = "/flussi/associaFlussoProposta.htm", method = RequestMethod.POST, params = { "Riconcilia" })
	public String riconciliaAccertamento(FlussiForm flussiForm,
			BindingResult result, HttpServletRequest request, ModelMap modelMap) {

		String codUtente = (String) request.getSession().getAttribute(
				Constants.ID_CODICE_UTENTE);
		Integer idUffOwn = (Integer) request.getSession().getAttribute(
				Constants.ID_UFFICIO_UTENTE);

		flussiValidation.validateSceltaAccertamento(flussiForm, result);
		flussiValidation.validate(flussiForm, result);
		if (result.hasErrors()) {
			return visualizzaFlussoDaElaborare(flussiForm, null, request, modelMap, false,false);
		}

		long idProcAccertamento = flussiForm.getIdAccertamento();

		// Se il numero di soggetti è diverso non deve consentire la
		// riconciliazione
		flussiService.dettaglioFlusso(flussiForm);
		
		// modifica per prendere solo i diretti   16 gennaio 2018
		List<SoggettoForm> listaSoggForm = flussiForm
				.getListaSoggettoForm();
		List<SoggettoForm> listaSoggFormDir = new ArrayList<SoggettoForm>();
		
		Iterator<SoggettoForm> iter = listaSoggForm.iterator();
		while (iter.hasNext()) {
			SoggettoForm soggettoForm = iter.next();
			if (soggettoForm.getIdRuolo() ==1 ) {
				listaSoggFormDir.add(soggettoForm);
		
			}
			
		}
		
		int soggDirXML = listaSoggFormDir.size();
//		int soggXML = flussiForm.getListaSoggettoForm().size();
		int soggAcc = procedimentoService.getNumSogInt(idProcAccertamento,
				idUffOwn);
		//if (soggXML != soggAcc) {
		if (soggDirXML != soggAcc) {
			super.setMessage(Constants.MESSAGE,
					"flussi.riconcilia.numeroSoggettiDiverso", null, false);
			return visualizzaFlussoDaElaborare(flussiForm, null, request, modelMap, true,false);
		}

		// long idProcProposta = flussiService.processFlussoProposta(flussiForm,
		// codUtente, idUffOwn);
		List l = flussiService.processFlussoProposta(flussiForm, codUtente,
				idUffOwn);
		HashMap lista = (HashMap) l.get(0);
		long idProcProposta = (Long) lista.get("CODI_K_ID_PROC");

		// Inserisce la relazione fra i due procedimenti
		procedimentoService.inserisciTRelProcProc(idProcProposta,
				idProcAccertamento, idUffOwn, codUtente, 9L, 2L, "A");
		procedimentoService.inserisciTRelProcProc(idProcAccertamento,
				idProcProposta, idUffOwn, codUtente, 26L, 312L, "A");
		procedimentoService.copiaAttoAccertamentoInProcMerito(
				idProcAccertamento, idProcProposta, idUffOwn, codUtente);

		// Aggiorna lo stato dell'accertamento a 'Riconciliato'
		procedimentoService.aggiornaStatoDelProcedimentoRiconcilia(
				idProcAccertamento, idUffOwn, codUtente);

		ProcedimentoForm procedimentoForm = new ProcedimentoForm();
		procedimentoForm.setIdProcedimento(idProcProposta);
		request.getSession().setAttribute(Constants.ID_PROCEDIMENTO,
				procedimentoForm);
		// modelMap.addAttribute(MESSAGE, "success");
		super.setMessage(Constants.SUCCESS_MESSAGE, "success", null, true);
		return "redirect:/procedimento/apri/copertinaProcedimento.htm";
	}

	@RequestMapping(value = "/flussi/visualizzaFlussoDaElaborare.htm", method = RequestMethod.GET)
	public String paginazioneFlussiDaElaborare(FlussiForm flussiForm,
			BindingResult result, HttpServletRequest request, ModelMap modelMap) {
       		
		return listaFlussiDaElaborare(flussiForm, request, modelMap);
	}

	private String getIdSoggettiProcedimento(FlussiForm flussiForm, HttpServletRequest request) {

		Integer idUffOwn = (Integer) request.getSession().getAttribute(
				Constants.ID_UFFICIO_UTENTE);
		String stringaIdSoggetti = "";
		List<SoggettoForm> listaSoggettoForm = flussiForm
				.getListaSoggettoForm();
		Iterator<SoggettoForm> iter = listaSoggettoForm.iterator();
		while (iter.hasNext()) {
			SoggettoForm soggettoForm = iter.next();
			 if (soggettoForm.getIdRuolo() == 1){
				
			
			  if ("F".equals(soggettoForm.getTipoSoggetto())) {
				  soggettoForm = procedimentoService
						.getPersonaFisicaByCampiChiave(soggettoForm, idUffOwn);
			    } else if ("G".equals(soggettoForm.getTipoSoggetto())) {
				   soggettoForm = procedimentoService
						.getPersonaGiuridicaByCampiChiave(soggettoForm,
								idUffOwn);
		    	}
			

			  if (soggettoForm != null)
				  stringaIdSoggetti += soggettoForm.getIdSoggetto() + ",";
			}
		}

		if (stringaIdSoggetti.length() > 0)
			stringaIdSoggetti = stringaIdSoggetti.substring(0,
					stringaIdSoggetti.length() - 1);

		return stringaIdSoggetti;
	}

	//Il metodo recupera l'allegato
	//Metodo modificato in seguito all'introduzione del servizio "Accettazione Deposito"
	//Se accettazioneDeposito == false --> sulla pagina è stato utilizzato il tasto "Sfoglia" per selezionare il documento --> funzionamento precedente alla modifica
	//Se accettazioneDeposito == true --> il documento è stato recuperato dal documentale
	private void getDocumentoAllegato(FlussiForm flussiForm) {

		if (!flussiForm.isAccettazioneDeposito() && flussiForm.getAllegato() != null && flussiForm.getAllegato().getFileData() != null) {			
			flussiForm.getAllegato().setFileName(flussiForm.getAllegato().getFileData().getOriginalFilename());
			flussiForm.getAllegato().setContentType(flussiForm.getAllegato().getFileData().getContentType());
			byte b[] = flussiForm.getAllegato().getFileData().getBytes();
			if (b.length > 0) {
				Blob blob = Hibernate.createBlob(b);
				flussiForm.getAllegato().setDocumento(blob);
			} else {
				flussiForm.getAllegato().setDocumento(null);
			}
		}
	}
	
	@RequestMapping(value = "/flussi/flussiPoliziaIngresso.htm", method = RequestMethod.GET)
	public String listaFlussiPoliziaIngresso(FlussiPoliziaForm flussiForm,	HttpServletRequest request, ModelMap modelMap) {
		
		MenuController.caricaMenu("2", request);
		modelMap.addAttribute("flussiForm", flussiForm);
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
		System.out.println(" -----> flussi da eleborare flusso polizia ingresso 2" );
		List<FlussiPoliziaForm> listaFlussi = flussiService.getListFlussiPolizia(idUfficioUtente.intValue(), flussiForm, "R");
		
		PagedListHolder pagedListHolder = new PagedListHolder(listaFlussi);
			
		pagedListHolder.setPage(0);
		int pageSize = 10;
		pagedListHolder.setPageSize(pageSize);
		
		String page = "1";
		String npage = "1";
		   
		if(request.getParameter("page") != null) {
			page = (String) request.getParameter("page");
		}
		if(request.getParameter("n") != null) {
			npage = (String) request.getParameter("n");
		}
		  
		
		if("first".equals(page)){
		     pagedListHolder.setPage(0);
		}
		else if("next".equals(page)){
			 pagedListHolder.setPage(Integer.parseInt(npage)-1);            	          	
		     pagedListHolder.nextPage();
		}           
		else if("previous".equals(page)){   
			 pagedListHolder.setPage(Integer.parseInt(npage)-1);            	          	           	
		     pagedListHolder.previousPage();
		}
		else if("last".equals(page)){
		     pagedListHolder.setPage(pagedListHolder.getPageCount());
		}
		else {
		     pagedListHolder.setPage(Integer.parseInt(page)-1);            
		}
		
		//in caso di messaggio di errore
		if(request.getParameter("id_hiddenPagina") != null) {
			String pageHidden = (String) request.getParameter("id_hiddenPagina");
		    pagedListHolder.setPage(Integer.parseInt(pageHidden)-1); 
		}
		
		modelMap.addAttribute("risultatiRicercaHolder", pagedListHolder);
		addBreadcrumbs("GET", "/flussi/flussiPoliziaIngresso.htm", null);
		return "/flussi/flussiPoliziaIngresso";
	}
	
	@RequestMapping(value = "/flussi/flussiPoliziaUscita.htm", method = RequestMethod.GET)
	public String listaFlussiPoliziaUscita(FlussiPoliziaForm flussiForm, HttpServletRequest request, ModelMap modelMap) {

		MenuController.caricaMenu("2", request);
		modelMap.addAttribute("flussiForm", flussiForm);
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
		System.out.println(" -----> flussi da eleborare flusso polizia uscita 3" );
		List<FlussiPoliziaForm> listaFlussi = flussiService.getListFlussiPolizia(idUfficioUtente.intValue(), flussiForm, "I");
		
		PagedListHolder pagedListHolder = new PagedListHolder(listaFlussi);
		pagedListHolder.setPage(0);
		int pageSize = 10;
		pagedListHolder.setPageSize(pageSize);
		
		String page = "1";
		String npage = "1";
		   
		if(request.getParameter("page") != null) {
			page = (String) request.getParameter("page");
		}
		if(request.getParameter("n") != null) {
			npage = (String) request.getParameter("n");
		}
		
		if("first".equals(page)){
		     pagedListHolder.setPage(0);
		}
		else if("next".equals(page)){
			 pagedListHolder.setPage(Integer.parseInt(npage)-1);            	          	
		     pagedListHolder.nextPage();
		}           
		else if("previous".equals(page)){   
			 pagedListHolder.setPage(Integer.parseInt(npage)-1);            	          	           	
		     pagedListHolder.previousPage();
		}
		else if("last".equals(page)){
		     pagedListHolder.setPage(pagedListHolder.getPageCount());
		}
		else {
		     pagedListHolder.setPage(Integer.parseInt(page)-1);            
		}
		
		//in caso di messaggio di errore
		if(request.getParameter("id_hiddenPagina") != null) {
			String pageHidden = (String) request.getParameter("id_hiddenPagina");
		    pagedListHolder.setPage(Integer.parseInt(pageHidden)-1); 
		}
		
		modelMap.addAttribute("risultatiRicercaHolder", pagedListHolder);
		addBreadcrumbs("GET", "/flussi/flussiPoliziaUscita.htm", null);
		return "/flussi/flussiPoliziaUscita";
	}
	
	@RequestMapping(value = "/flussi/visualizzaFlussoPolizia.htm", method = RequestMethod.POST, params = { "Visualizza" })
	public String visualizzaFlussoPolizia(FlussiPoliziaForm flussiForm, BindingResult result, HttpServletRequest request, ModelMap modelMap, String reload) {

		if (result != null ) {
			if (flussiForm.getIdFlusso() == null) {
				super.setMessage(Constants.MESSAGE, "table.item.selection", null,
						false);
				result.reject(Constants.MESSAGE);
			}
			if (result.hasErrors() && reload==null) {
				if(flussiForm.getFlgDirezione() == 'R')
					return listaFlussiPoliziaIngresso(flussiForm, request, modelMap);
				else
					return listaFlussiPoliziaUscita(flussiForm, request, modelMap);
			}
		}
		
		if(flussiForm.getFlgDirezione() == 'I'){
			Long idProcedimento = flussiForm.getIdProcedimento();
			Long idUfficio = flussiForm.getIdUffMittente();
			Long idAtto = flussiForm.getIdAtto();
			
			ProcedimentoForm procedimentoForm = procedimentoService.getDettaglioProcedimento(idProcedimento.longValue());
			
			int idTipoFascicolo = new Integer(procedimentoForm.getTipoFascicolo().getId());
			if (idTipoFascicolo==Constants.TIPO_FASCICOLO_AGGRAVAMENTO ||
					idTipoFascicolo==Constants.TIPO_FASCICOLO_CAMERA_DI_CONSIGLIO	) {
				Long idPadre = procedimentoService.getIdPadreAggravamento(idProcedimento);
				ProcedimentoForm padre = procedimentoService.getDettaglioProcedimento(idPadre);
				procedimentoForm.setAnnoPadre(padre.getAnno());
				procedimentoForm.setNumeroPadre(padre.getNumero());
				NumeroRegistroForm formPadre = procedimentoService.getNumeroRegistroPadre(idProcedimento);
				if(formPadre!=null) 	
					procedimentoForm.setUfficioPadre(formPadre.getUfficio());
			} else {
				NumeroRegistroForm formPadre = procedimentoService.getNumeroRegistroPadre(idProcedimento);
				if(formPadre!=null) {
					if(formPadre.getAnnoProcedimento()!=null) procedimentoForm.setAnnoPadre(new Short(formPadre.getAnnoProcedimento()));
					if(formPadre.getNumeroProcedimento()!=null) procedimentoForm.setNumeroPadre(new Long(formPadre.getNumeroProcedimento()));
					procedimentoForm.setUfficioPadre(formPadre.getUfficio());
				}
			}
			
			List<SoggettoForm> listSoggetto = procedimentoService.soggettiDirettiInteressatiDelProcedimento(idProcedimento, Constants.RUOLO_DIRETTO, Constants.Flag.S);
			modelMap.addAttribute("direttiInteressati", listSoggetto);
			
			AttoForm attoForm = attoService.getAttoById(idAtto.longValue(), idUfficio.longValue());
			
			String codiceUtente = recuperaIdCodiceUtente();
			attoForm = caricaAttoInSessione(idUfficio.intValue(), idAtto, codiceUtente, idProcedimento, request, attoForm);
			
			modelMap.addAttribute("procedimento", procedimentoForm);
			modelMap.addAttribute("atto", attoForm);
			
			flussiForm.setFlgDirezione("I".charAt(0));
			flussiForm.setDescTipoFlusso(poliziaService.getTtpPoliziaServiziById(flussiForm.getIdServizio()).getDescrizione());
			if(flussiForm.getIdCatAtto()!=null && flussiForm.getIdCatAtto().longValue()!=0){
				flussiForm.setIdCatAtto(flussiForm.getIdCatAtto());
				flussiForm.setDescCatAtto(poliziaService.getTtpCatAttoById(flussiForm.getIdCatAtto()).getDescCatAtto());
			}
			
			modelMap.addAttribute("azione", request.getContextPath()+"/flussi/flussiPoliziaUscita.htm");
			modelMap.addAttribute("flussiForm", flussiForm);
			addBreadcrumbs("POST", "/flussi/visualizzaFlussoPolizia.htm","Indietro");
			return "/flussi/visualizzaFlussoPolizia";

		}else{
			flussiForm = flussiService.caricaDettaglioFlussoIngresso(flussiForm);
			if(flussiForm!=null){
				if(flussiForm.getUfficioMittente()!=null){
					modelMap.addAttribute("ufficioMittente", flussiForm.getUfficioMittente());
				}
				if(flussiForm.getUfficioDestinatario()!=null){
					modelMap.addAttribute("ufficioDestinatario", flussiForm.getUfficioDestinatario());
				}
				
				if(flussiForm.getProcedimento()!=null)
					modelMap.addAttribute("procedimento", flussiForm.getProcedimento());
				
				if(flussiForm.getAtto()!=null){
					String codiceUtente = recuperaIdCodiceUtente();
					AttoForm atto = caricaAttoInSessione(flussiForm.getIdUffDestinatario().intValue(), flussiForm.getIdAtto().longValue(), codiceUtente, flussiForm.getIdProcedimento().longValue(), request, flussiForm.getAtto());
					flussiForm.setAtto(atto);
					modelMap.addAttribute("atto", atto);
				}
				modelMap.addAttribute("allegatoObbligatorio", 'S');
				
				List<SoggettoForm> listSoggetti = new ArrayList<SoggettoForm>();
				if(flussiForm.getListSoggettoFis()!=null && !flussiForm.getListSoggettoFis().isEmpty())
					listSoggetti.addAll(flussiForm.getListSoggettoFis());
				if(flussiForm.getListSoggettoGiu()!=null && !flussiForm.getListSoggettoGiu().isEmpty())
					listSoggetti.addAll(flussiForm.getListSoggettoGiu());
				if(flussiForm.getListSoggetto()!=null && !flussiForm.getListSoggetto().isEmpty())
					listSoggetti.addAll(flussiForm.getListSoggetto());
				if(!listSoggetti.isEmpty()){
					if(flussiForm.getListSoggetto().isEmpty())
						flussiForm.getListSoggetto().addAll(listSoggetti);
					
					modelMap.addAttribute("direttiInteressati", listSoggetti);
				}
				
				if(flussiForm.getListMisurePersonali()!=null && !flussiForm.getListMisurePersonali().isEmpty()){
					modelMap.addAttribute("listMisurePersonali", flussiForm.getListMisurePersonali());
				}
				if(flussiForm.getListMisurePatrimoniali()!=null && !flussiForm.getListMisurePatrimoniali().isEmpty()){
					modelMap.addAttribute("listMisurePatrimoniali", flussiForm.getListMisurePatrimoniali());
				}
				
				if(flussiForm.getMisureSoggetto()!=null && !flussiForm.getMisureSoggetto().isEmpty())
                    modelMap.addAttribute("misureSoggetto", flussiForm.getMisureSoggetto());

				if(flussiForm.getBeneSoggetto()!=null && !flussiForm.getBeneSoggetto().isEmpty())
                    modelMap.addAttribute("beneSoggetto", flussiForm.getBeneSoggetto());
				
				TPoliziaMsg msg = poliziaService.getTPoliziaMsgById(flussiForm.getIdFlusso());
				flussiForm.setIdStato(new Long(msg.getNumeStato()));
				if(!flussiForm.getIdStato().equals(new Long(5)))
					flussiForm.setEnableRegistra("S");
				
				flussiForm.setFlgDirezione("R".charAt(0));
				flussiForm.setDescTipoFlusso(poliziaService.getTtpPoliziaServiziById(flussiForm.getIdServizio()).getDescrizione());
				if(flussiForm.getIdCatAtto()!=null && flussiForm.getIdCatAtto().longValue()!=0){
					flussiForm.setIdCatAtto(flussiForm.getIdCatAtto());
					flussiForm.setDescCatAtto(poliziaService.getTtpCatAttoById(flussiForm.getIdCatAtto()).getDescCatAtto());
				}
				
				request.getSession().setAttribute("flussoPolizia", flussiForm);
			}
			modelMap.addAttribute("azione", request.getContextPath()+"/flussi/registraFlussoIngressoPolizia.htm");
			modelMap.addAttribute("flussiForm", flussiForm);
			addBreadcrumbs("POST", "/flussi/visualizzaFlussoPolizia.htm","Indietro");
			return "/flussi/visualizzaFlussoIngressoPolizia";
		}
	}
	
	@RequestMapping(value = "/flussi/registraFlussoIngressoPolizia.htm", method = RequestMethod.POST, params = { "Registra" })
	public String registraFlussoIngressoPolizia(@Valid FlussiPoliziaForm flussiForm, BindingResult result, ModelMap modelMap, HttpServletRequest request) {

		Integer idUffOwn = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		FlussiPoliziaForm flussoPolizia = (FlussiPoliziaForm) request.getSession().getAttribute("flussoPolizia");
		
		/******************** gestione allegato ****************************************/
		allegatoValidation.validaFlussiFormPolizia(flussiForm, result, request);
		
		if (result.hasErrors()) {
			super.setMessage(Constants.MESSAGE, "flussi.registraPolizia.allegato.error", null, false);
			result.reject(Constants.MESSAGE);
			modelMap.addAttribute("flussiForm", flussoPolizia);
			return gestioneReloadVisualizzaDettagli(flussoPolizia, result, modelMap, request);
		}
		/******************** gestione allegato ****************************************/
		
		//INIZIO CHIAMA ALLA PROCEDURA
		try {
			flussoPolizia.setDataRicezione(flussiForm.getDataRicezione());
			boolean esito = flussiService.registraFlussoIngressoPolizia(flussoPolizia);
			if(!esito){
				super.setMessage(Constants.MESSAGE, "flussi.registraPolizia.error", null, false);
				result.reject(Constants.MESSAGE);
				modelMap.addAttribute("flussiForm", flussoPolizia);
				return gestioneReloadVisualizzaDettagli(flussoPolizia, null, modelMap, request);
			}
		} catch (Exception e) {
			super.setMessage(Constants.MESSAGE, "flussi.registraPolizia.error", null, false);
			result.reject(Constants.MESSAGE);
			modelMap.addAttribute("flussiForm", flussoPolizia);
			return gestioneReloadVisualizzaDettagli(flussoPolizia, null, modelMap, request);
		}
		super.setMessage(Constants.SUCCESS_MESSAGE, "success", null, true);
		//FINE   CHIAMATA PROCEDURA
		
		TPoliziaMsg tPolizia = poliziaService.getTPoliziaMsgById(flussoPolizia.getIdFlusso().longValue());
		if(tPolizia==null){
			super.setMessage(Constants.MESSAGE, "flussi.registraPolizia.error", null, false);
			result.reject(Constants.MESSAGE);
			modelMap.addAttribute("flussiForm", flussoPolizia);
			return gestioneReloadVisualizzaDettagli(flussoPolizia, null, modelMap, request);
		}
		
		Long idProc = tPolizia.getCodiIdProc()!=null ? tPolizia.getCodiIdProc() : null;
		long idAtto = tPolizia.getCodiIdAtto()!=null ? tPolizia.getCodiIdAtto().longValue() : 0;
		if(idProc==null || idProc.longValue()==0)
			idProc = flussoPolizia.getIdProcedimento();
			
		if(idAtto==0)
			idAtto = flussoPolizia.getIdAtto().longValue();
		
		if(idProc==null || idAtto==0 || idProc.longValue()==0 ){
			flussoPolizia.setEnableRegistra("N");
			modelMap.addAttribute("flussiForm", flussoPolizia);
			return gestioneReloadVisualizzaDettagli(flussoPolizia, null, modelMap, request);
		}
		
		ProcedimentoForm procedimentoForm = new ProcedimentoForm();
		procedimentoForm.setIdProcedimento(idProc);
		request.getSession().setAttribute(Constants.ID_PROCEDIMENTO, procedimentoForm);
		
		// INIZIA GESTIONE DOCUMENTALE
		if (flussiForm.getAllegato() != null && flussiForm.getAllegato().getFileData() != null) {			
			flussiForm.getAllegato().setFileName(flussiForm.getAllegato().getFileData().getOriginalFilename());
			flussiForm.getAllegato().setContentType(flussiForm.getAllegato().getFileData().getContentType());
			byte b[] = flussiForm.getAllegato().getFileData().getBytes();
			if (b.length > 0) {
				Blob blob = Hibernate.createBlob(b);
				flussiForm.getAllegato().setDocumento(blob);
			} else {
				flussiForm.getAllegato().setDocumento(null);
			}
			AttoForm attoForm = new AttoForm();
			attoForm = attoService.getAttoById(idAtto, idUffOwn);
			attoForm.setAllegato(flussiForm.getAllegato());
			poliziaService.inserisciDocumentoAllegatoAtto(attoForm, idProc, idUffOwn.intValue());
			gestionePiattaformaDocumentale(attoForm, request, idUffOwn, result);
		}
		// FINE GESTIONE DOCUMENTALE

		return "redirect:/procedimento/apri/copertinaProcedimento.htm";
	}
	
	private String gestioneReloadVisualizzaDettagli(FlussiPoliziaForm flussiForm, BindingResult result, ModelMap modelMap, HttpServletRequest request){

		if(flussiForm.getUfficioMittente()!=null)
			modelMap.addAttribute("ufficioMittente", flussiForm.getUfficioMittente());

		if(flussiForm.getUfficioDestinatario()!=null)
			modelMap.addAttribute("ufficioDestinatario", flussiForm.getUfficioDestinatario());
		
		if(flussiForm.getProcedimento()!=null)
			modelMap.addAttribute("procedimento", flussiForm.getProcedimento());
		
		if(flussiForm.getAtto()!=null){
			String codiceUtente = recuperaIdCodiceUtente();
			AttoForm atto = caricaAttoInSessione(flussiForm.getIdUffDestinatario().intValue(), flussiForm.getIdAtto().longValue(), codiceUtente, flussiForm.getIdProcedimento().longValue(), request, flussiForm.getAtto());
			flussiForm.setAtto(atto);
			modelMap.addAttribute("atto", atto);
		}
		modelMap.addAttribute("allegatoObbligatorio", 'S');
		
		if(!flussiForm.getListSoggetto().isEmpty())
			modelMap.addAttribute("direttiInteressati", flussiForm.getListSoggetto());
		
		if(flussiForm.getListMisurePersonali()!=null && !flussiForm.getListMisurePersonali().isEmpty())
			modelMap.addAttribute("listMisurePersonali", flussiForm.getListMisurePersonali());
		
		if(flussiForm.getListMisurePatrimoniali()!=null && !flussiForm.getListMisurePatrimoniali().isEmpty())
			modelMap.addAttribute("listMisurePatrimoniali", flussiForm.getListMisurePatrimoniali());
		
		if(flussiForm.getMisureSoggetto()!=null && !flussiForm.getMisureSoggetto().isEmpty())
            modelMap.addAttribute("misureSoggetto", flussiForm.getMisureSoggetto());

		if(flussiForm.getBeneSoggetto()!=null && !flussiForm.getBeneSoggetto().isEmpty())
            modelMap.addAttribute("beneSoggetto", flussiForm.getBeneSoggetto());
		
		TPoliziaMsg msg = poliziaService.getTPoliziaMsgById(flussiForm.getIdFlusso());
		flussiForm.setIdStato(new Long(msg.getNumeStato()));
		if(!flussiForm.getIdStato().equals(new Long(5)))
			flussiForm.setEnableRegistra("S");
		
		flussiForm.setFlgDirezione("R".charAt(0));
		flussiForm.setDescTipoFlusso(poliziaService.getTtpPoliziaServiziById(flussiForm.getIdServizio()).getDescrizione());
		request.getSession().setAttribute("flussoPolizia", flussiForm);
		modelMap.addAttribute("azione", request.getContextPath()+"/flussi/registraFlussoIngressoPolizia.htm");
		modelMap.addAttribute("flussiForm", flussiForm);
		addBreadcrumbs("POST", "/flussi/visualizzaFlussoPolizia.htm","Indietro");
		return "/flussi/visualizzaFlussoIngressoPolizia";
	}
	
	private AttoForm caricaAttoInSessione(int idUffOwn, long idAtto, String codiceUtente, long idProcedimento, HttpServletRequest request, AttoForm attoForm) {

		attoForm = attoService.dettaglioAtto(idUffOwn, idAtto, idProcedimento, codiceUtente);
		if (attoForm.getStatoAtto() != null
				&& attoForm.getStatoAtto().getId()!= null
				&& attoForm.getStatoAtto().getId().equals("1")) {
			ComboForm cf = new ComboForm();
			cf.setId("-1");	
			attoForm.setFonteAttoGenerico(cf);
		}
		
		// nel caso di decreto di Revoca o Convalida va popolato anche l'atto di
		// origine
		String numeroAttoOrigine = attoService.numeroAttoOrigine(idProcedimento, idAtto, idUffOwn, codiceUtente, super.printMethodName());
		if (numeroAttoOrigine != null)
			attoForm.setNumeroAttoOrigine(numeroAttoOrigine);
		else
			attoForm.setNumeroAttoOrigine("");
		if (attoForm.getCodAttoOrigine() == 0) { //cerco l'id atto origine solo se sono in modifica del decreto
			long idAttoOrigine = attoService.idAttoOrigine(idProcedimento, idAtto, idUffOwn, codiceUtente, super.printMethodName());
			attoForm.setCodAttoOrigine(idAttoOrigine);
		}

		String numProcedOrigine = attoService.getNumeroProcedimentoOrigine(recuperaIdProcedimento(), idAtto, idUffOwn, recuperaIdCodiceUtente(), super.printMethodName());
		attoForm.setNumeroProcedimentoOrigine(numProcedOrigine);

		//provvedimenti fascicolo gestione
		if(attoForm!=null && attoForm.getTipoAtto()!=null && attoForm.getTipoAtto().getId().equals(Constants.TIPO_ATTO_PROVVEDIMENTO)) {
			List listaAtti = attoService.getRelAtti(0, 0, idAtto, idUffOwn, recuperaIdCodiceUtente(), super.printMethodName());
			if(listaAtti!=null && !listaAtti.isEmpty()) {
				Map<String, Object> row = (Map<String, Object>) listaAtti.get(0);
				TRelAtti relAtti = (TRelAtti) row.get("relAtti");
				int idUffOwnOrigine = relAtti.getId().getCodiKIdUffOwnerOr();
				long idAttoOrigine = relAtti.getId().getCodiKIdAttoOr();
				AttoForm dettaglioAttoOrigine = attoService.dettaglioAtto(idUffOwnOrigine, idAttoOrigine, idProcedimento, recuperaIdCodiceUtente());
				if(dettaglioAttoOrigine!=null) {
					attoForm.setDataRicezioneAttoOrigine(dettaglioAttoOrigine.getDataRicezione());
					if(dettaglioAttoOrigine.getCategoriaAtto()!=null) {
						attoForm.setDescrizioneCategoriaAttoOrigine(dettaglioAttoOrigine.getCategoriaAtto().getDescrizione());
					}
				}
			}
		}

		if (request.getSession().getAttribute(Constants.ATTO) != null)
			request.getSession().removeAttribute(Constants.ATTO);
		request.getSession().setAttribute(Constants.ATTO, attoForm);

		return attoForm;
	}
	
	@RequestMapping(value = { "/flussi/associaFlussoProposta.htm" }, method = RequestMethod.POST, params = { "downloadDocumentale" })
	public String downloadDocumentale(FlussiForm flussiForm,HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws IOException, SQLException {

		try {
			downloadDocumento(flussiForm, request, response);
			return null;
		} catch (Exception ex) {
			super.setMessage(Constants.MESSAGE, "flussi.accettazionedeposito.documentale.error","", false);
			System.out.println("Errore download documentale:" + ex.getMessage());
			//Viene ricaricata la stessa pagina precedente visualizzando solo il messaggio di errore sul download 
			flussiForm.setAccettazioneDeposito(true);
			return visualizzaFlussoDaElaborare(flussiForm, null, request,modelMap, false,false);
		}
	}
	
	private void getDatiAccetazioneDeposito(FlussiForm flussiForm, HttpServletRequest request) {
		
		int idUffDest = 0;
		int idUffMit = 0;
		long idAtto = 0L;
		
		if(request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE) != null){
			idUffDest = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		}
		if(flussiForm.getAttoDto() != null && flussiForm.getAttoDto().getCODI_K_ID_ATTO() != null){
			idAtto = Long.parseLong(flussiForm.getAttoDto().getCODI_K_ID_ATTO());
		}
		if(flussiForm.getIdUffMittente() != null){
			idUffMit = flussiForm.getIdUffMittente().intValue();
		}
		
		//Se i 3 campi sono valorizzati avvio il processo di verifica, altrimenti resta valido il flusso di precedente
		if(idUffDest != 0 && idUffMit != 0 && idAtto != 0L) {
			flussiService.getDatiAccetazioneDeposito(flussiForm, idUffDest, idUffMit, idAtto);
		}
	}
	
	private void downloadDocumento(FlussiForm flussiForm, HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {

		int idUffDest = 0;
		int idUffMit = 0;
		long idAtto = 0L;
		
		AllegatoForm allegato = new AllegatoForm();
		
		if(request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE) != null){
			idUffDest = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		}
		if(flussiForm.getAttoDto() != null && flussiForm.getAttoDto().getCODI_K_ID_ATTO() != null){
			idAtto = Long.parseLong(flussiForm.getAttoDto().getCODI_K_ID_ATTO());
		}
		if(flussiForm.getIdUffMittente() != null){
			idUffMit = flussiForm.getIdUffMittente().intValue();
		}
		
		if(idUffDest != 0 && idUffMit != 0 && idAtto != 0L) {
			
			OutputStream out = response.getOutputStream();			
			allegato =  flussiService.downloadDocumentoAllegato(flussiForm, idUffDest, idUffMit, idAtto);
			response.setContentType(allegato.getContentType());
			response.setHeader("Content-Disposition", "attachment;filename=" + allegato.getFileName());

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];

			InputStream in = allegato.getDocumento().getBinaryStream();

			int n = 0;
			while ((n = in.read(buf)) >= 0)
				baos.write(buf, 0, n);

			in.close();
			byte[] bytes = baos.toByteArray();

			out.write(bytes);
			out.flush();
			
		}
	}
	
	private void processAccettazioneDeposito(FlussiForm flussiForm, HttpServletRequest request) throws Exception {

		int idUffDest = 0;
		int idUffMit = 0;
		long idAtto = 0L;
		
		if(request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE) != null){
			idUffDest = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		}
		if(flussiForm.getAttoDto() != null && flussiForm.getAttoDto().getCODI_K_ID_ATTO() != null){
			idAtto = Long.parseLong(flussiForm.getAttoDto().getCODI_K_ID_ATTO());
		}
		if(flussiForm.getIdUffMittente() != null){
			idUffMit = flussiForm.getIdUffMittente().intValue();
		}
				
		if(idUffDest != 0 && idUffMit != 0 && idAtto != 0L) {

			AllegatoForm allegato = flussiService.downloadDocumentoAllegato(flussiForm, idUffDest, idUffMit, idAtto);
			flussiForm.setAllegato(allegato);
			flussiService.processAccettazioneDeposito(idUffDest, idUffMit, idAtto,recuperaIdCodiceUtente().toString());			
		}		
	}
	
	
	@RequestMapping(value = "/flussi/flussiToolAmministratori.htm", method = RequestMethod.GET)
	public String listaFlussiToolAmministratori(FlussiToolAmministratoriForm flussoForm,
			HttpServletRequest request, ModelMap modelMap) {

		MenuController.caricaMenu("2", request);
		modelMap.addAttribute("flussoForm", flussoForm);
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
		
		List<FlussiToolAmministratoriForm> listaFlussi = flussiService.listaFlussiToolAmministratori(idUfficioUtente, flussoForm);		   
		
		if (listaFlussi != null) {
			for (int i = 0; i < listaFlussi.size(); i++) {
                 
				FlussiToolAmministratoriForm flusso = listaFlussi.get(i);
				String amministratore = flusso.getDescCognome() + " " + flusso.getDescNome();
				flusso.setAmministratore(amministratore);				
			}
		}
		modelMap.addAttribute("risultatiRicerca", listaFlussi);
		addBreadcrumbs("GET", "/flussi/flussiToolAmministratori.htm", null);
		return "/flussi/flussiToolAmministratori";
	}	
	
	@RequestMapping(value = "/flussi/dettaglioDeposito.htm", method = RequestMethod.POST, params = { "Dettaglio" })
	public String visualizzaDettaglioDeposito(FlussiToolAmministratoriForm flussoForm, BindingResult result, HttpServletRequest request, ModelMap modelMap) {

		if (result != null ) {
			if (flussoForm.getIdDeposito() == null) {
				super.setMessage(Constants.MESSAGE, "table.item.selection", null,
						false);
				result.reject(Constants.MESSAGE);
			}
			if (result.hasErrors()) {
				return listaFlussiToolAmministratori(flussoForm, request, modelMap);
			}
		}
		
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
		long idDeposito = flussoForm.getIdDeposito();
		
		flussoForm.setFlag('N');
		
		flussoForm.setIdUffOwner(idUfficioUtente);
			
		flussiService.getDettaglioDeposito(flussoForm, idDeposito, idUfficioUtente);

		flussiService.getDettaglioAmministratore(flussoForm);

		flussiService.getDettaglioDepositoAmm(flussoForm, idDeposito, 'N');

		List listaBeni = flussiService.getListaBeniDeposito(flussoForm,idDeposito);	
		modelMap.addAttribute("listaBeni", listaBeni);
		
		modelMap.addAttribute("flussoForm", flussoForm);
		
		return "/flussi/visualizzaDettaglioDeposito";

	}

	@RequestMapping(value = { "/flussi/accettaDeposito.htm" }, method = RequestMethod.POST, params = { "Conferma" })
	public ModelAndView accettaDeposito(FlussiToolAmministratoriForm flussoForm, BindingResult result, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		
		  String idCodiceUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);
		  int idUffOwn = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
//		  int idTipoUfficio = (Integer)request.getSession().getAttribute("idTipoUfficio");
	      
		  Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
		  long idDeposito = flussoForm.getIdDeposito();
		
		  String ret = "ok";
		  try {
				
				ret = flussiService.registraAttoRelazione(flussoForm,idUffOwn,idCodiceUtente);	

				if (request.getAttribute(Constants.SUCCESS_MESSAGE) == null)
					super.setMessage(Constants.SUCCESS_MESSAGE, "success.operation", "ACQUISIZIONE DEPOSITO", false);
				
			} catch (SQLException ex) {
				super.setMessage(Constants.MESSAGE, "error.defaultError", null, false);
				result.reject(Constants.MESSAGE);
				ret = "ko";
			} catch (Exception ex) {
				super.setMessage(Constants.MESSAGE, "error.defaultError", null, false);
				result.reject(Constants.MESSAGE);
				ret = "ko";
			}
		  
		    char flag = 'S';
		    if(ret.equals("ko")) flag='N';
		    
			flussoForm.setFlag(flag);

			flussiService.getDettaglioDeposito(flussoForm, idDeposito, idUfficioUtente);
			flussiService.getDettaglioAmministratore(flussoForm);
			flussiService.getDettaglioDepositoAmm(flussoForm, idDeposito, flag);
		  
			List listaBeni = flussiService.getListaBeniDeposito(flussoForm,idDeposito);	
			modelMap.addAttribute("listaBeni", listaBeni);
			
			ModelAndView mnv = new ModelAndView("/flussi/visualizzaDettaglioDeposito");
			mnv.addObject("flussoForm", flussoForm);
			return mnv;
	}
	
	@RequestMapping(value = { "/flussi/downloadAllegato.htm" }, method = RequestMethod.POST)
	public String downloadAllegato(FlussiToolAmministratoriForm flussoForm, BindingResult result, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		
		try {
			flussiService.downloadAllegatoRelazione(flussoForm,response);	
			return null;
		} catch (Exception ex) {
			super.setMessage(Constants.MESSAGE, "depositi.nofile", null, false);
			result.reject(Constants.MESSAGE);
			return visualizzaDettaglioDeposito(flussoForm,result,request,modelMap);
		}
	}

}