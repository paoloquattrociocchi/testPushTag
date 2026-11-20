package it.giustizia.smp.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.giustizia.smp.controller.flussi.FlussiController;
import it.giustizia.smp.dto.flussi.T_REL_UDIENZA_STATO_UDDto;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.FlussiForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.ProfiloForm;
import it.giustizia.smp.form.UdienzaForm;
import it.giustizia.smp.model.TRelAttoProc;
import it.giustizia.smp.model.TRelIterProc;
import it.giustizia.smp.model.TRelProcProc;
import it.giustizia.smp.service.AttoService;
import it.giustizia.smp.service.FlussiService;
import it.giustizia.smp.service.ProcedimentoService;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Message;

@Component("flussiValidation")
public class FlussiValidation extends BaseValidation implements Validator {

	@Autowired
	private AttoService attoService;
	@Autowired
	private ProcedimentoService procedimentoService;
	// P.Q. 17/06/2021 MEV 9 Inizio
	@Autowired
	private FlussiService flussiService;
	// P.Q. 17/06/2021 MEV 9 Fine	
	
	public boolean supports(Class<?> clazz) {

		return FlussiController.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
				
		FlussiForm flussiForm = (FlussiForm) target;
		ProfiloForm profilo = (ProfiloForm) request.getSession().getAttribute(Constants.PROFILO);
		
		if (flussiForm.getInclude().equals(Constants.DECRETO_FISSAZIONE_UDIENZA_MSG)
				|| flussiForm.getInclude().equals(Constants.DECRETO_ANTICIPAZIONE_UDIENZA_MSG)
				|| flussiForm.getInclude().equals(Constants.DECRETO_RINVIO_UDIENZA_MSG)
				|| flussiForm.getInclude().equals(Constants.DECRETO_REMISSIONE_SU_RUOLO_MSG)
				|| flussiForm.getInclude().equals(Constants.DECRETO_ASSEGNAZIONE_NUOVO_RUOLO_MSG)
				|| flussiForm.getInclude().equals(Constants.TIPO_MSG_RICHIESTA_PARERE)
				|| flussiForm.getInclude().equals(Constants.TIPO_MSG_PARERE)
				|| flussiForm.getInclude().equals(Constants.REVOCA_OPPOSIZIONE_AL_DISSEQUESTRO_MSG)
				|| flussiForm.getInclude().equals(Constants.REVOCAZIONE_CONFISCA_MSG)) {
			validateDataDeposito(flussiForm, errors);
		} else if (flussiForm.getInclude().equals(Constants.PROPOSTA_MSG)
				|| flussiForm.getInclude().equals(Constants.PROPOSTA_DA_TRIBUNALE_MSG)
				|| flussiForm.getInclude().equals(Constants.PROPOSTA_AGGRAVAMENTO_MSG)
				|| flussiForm.getInclude().equals(Constants.PROPOSTA_AGGRAVAMENTO_DA_TRIBUNALE_MSG)) {
			// Procura generale della repubblica presso la CdA (8) o presso la
			// Cassazione (2)?
			// P.Q. 17/06/2021 MEV 9 Se proposta arrivata dalla procura non distrettuale deve essere validata la data di ricezione Inizio
			// Carico nella variabile tempFlussiForm i dati del flusso , getCODI_FK_ID_UFF_OWNER2 restituisce id procura proponente
			FlussiForm tempFlussiForm = new FlussiForm();
			tempFlussiForm.setIdFlusso(flussiForm.getIdFlusso());
			flussiService.dettaglioFlusso(tempFlussiForm);					
			if (profilo.getIdTipoUfficio() != Constants.TIPO_UFFICIO_PROCURA_GENERALE && !(profilo.getIdTipoUfficio() == Constants.TIPO_UFFICIO_PROCURA_L && (tempFlussiForm.getRelProcProcDto() !=null && tempFlussiForm.getRelProcProcDto().getCODI_FK_ID_UFF_OWNER2() != null))) {
			// P.Q. 17/06/2021 MEV 9 Fine				
				validateDataDeposito(flussiForm, errors);
				// validateFileData(flussiForm, errors);
			} else if (profilo.getIdTipoUfficio() == Constants.TIPO_UFFICIO_PROCURA_GENERALE || (profilo.getIdTipoUfficio() == Constants.TIPO_UFFICIO_PROCURA_L && (tempFlussiForm.getRelProcProcDto() !=null && tempFlussiForm.getRelProcProcDto().getCODI_FK_ID_UFF_OWNER2() != null))) {
				validateDataRicezione(flussiForm, errors);
			}
		} else if (flussiForm.getInclude().equals(Constants.TIPO_MSG_PER_EFFI_SEQ_A_PU_PG)) {
			// Procura generale della repubblica presso la CdA (8) o presso la Cassazione (2)?
			if (profilo.getIdTipoUfficio() != Constants.TIPO_UFFICIO_PROCURA_GENERALE) {
				validateDataDeposito(flussiForm, errors);
				// validateFileData(flussiForm, errors);
			} else if (profilo.getIdTipoUfficio() == Constants.TIPO_UFFICIO_PROCURA_GENERALE) {
				validateDataRicezione(flussiForm, errors);
			}
		} else if (profilo.getIdTipoUfficio() == Constants.TIPO_UFFICIO_PROCURA_GENERALE
				&& flussiForm.getInclude().equals(Constants.RICORSO_IN_CASSAZIONE_CDA_A_PG_MSG)) {
			validateDataDeposito(flussiForm, errors);
		} else if (flussiForm.getInclude().equals(Constants.ISTANZA_DIFFIDA_SOGGETTO_MSG)) {
			validateDataDeposito(flussiForm, errors);
		} else if (flussiForm.getInclude().equals(Constants.DECRETO_DECISORIO_I_GRADO_PU_MSG)
				|| flussiForm.getInclude().equals(Constants.DECRETO_DECISORIO_I_GRADO_PP_MSG)
				|| flussiForm.getInclude().equals(Constants.DECRETO_DECISORIO_I_GRADO_PG_MSG)) {
			validateDataDeposito(flussiForm, errors);
		} else if (flussiForm.getInclude().equals(Constants.SEQUESTRO_MSG_PU)
				|| flussiForm.getInclude().equals(Constants.SEQUESTRO_MSG_PG)
				|| flussiForm.getInclude().equals(Constants.SEQUESTRO_MSG_CDA)
				|| flussiForm.getInclude().equals(Constants.SEQUESTRO_ANT_MSG_PU)
				|| flussiForm.getInclude().equals(Constants.SEQUESTRO_ANT_MSG_PG)
				|| flussiForm.getInclude().equals(Constants.SEQUESTRO_URG_MSG_PU)
				|| flussiForm.getInclude().equals(Constants.SEQUESTRO_URG_MSG_PG)
				|| flussiForm.getInclude().equals(Constants.DECRETO_APPLICAZIONE_PROVV_MSG)
				|| flussiForm.getInclude().equals(Constants.REVOCA_APPLICAZIONE_PROVV_MSG)) {
			// Procura generale della repubblica presso la CdA (8) o presso la Cassazione (2)?
			if (profilo.getIdTipoUfficio() != Constants.TIPO_UFFICIO_PROCURA_GENERALE) {
				validateDataDeposito(flussiForm, errors);
			} else if (profilo.getIdTipoUfficio() == Constants.TIPO_UFFICIO_PROCURA_GENERALE) {
				validateDataRicezione(flussiForm, errors);
			}
		} else if (flussiForm.getInclude().equals(Constants.DECRETO_CONVALIDA_SEQ_MSG)) {
			// || flussiForm.getInclude().equals(Constants.DECRETO_CONVALIDA_SEQ_PP_MSG))
			// Procura generale della repubblica presso la CdA (8) o presso la Cassazione (2)?
			if (profilo.getIdTipoUfficio() != Constants.TIPO_UFFICIO_PROCURA_GENERALE) {
				validateDataDeposito(flussiForm, errors);
			} else if (profilo.getIdTipoUfficio() == Constants.TIPO_UFFICIO_PROCURA_GENERALE) {
				validateDataRicezione(flussiForm, errors);
			}
		} else if (flussiForm.getInclude().equals(Constants.DECRETO_REVOCA_SEQUESTRO_MSG)) {
			// Procura generale della repubblica presso la CdA (8) o presso la
			// Cassazione (2)?
			if (profilo.getIdTipoUfficio() != Constants.TIPO_UFFICIO_PROCURA_GENERALE) {
				validateDataDeposito(flussiForm, errors);
			} else if (profilo.getIdTipoUfficio() == Constants.TIPO_UFFICIO_PROCURA_GENERALE) {
				validateDataRicezione(flussiForm, errors);
			}
		} else if (flussiForm.getInclude().equals(Constants.RICORSO_IN_APPELLO_PU_A_TRIB_MSG)
				|| flussiForm.getInclude().equals(Constants.RICORSO_IN_APPELLO_PG_A_TRIB_MSG)
				|| flussiForm.getInclude().equals(Constants.RICORSO_IN_APPELLO_TRIB_A_PU_MSG)
				|| flussiForm.getInclude().equals(Constants.RICORSO_IN_APPELLO_TRIB_A_PG_MSG)
				|| flussiForm.getInclude().equals(Constants.TIPO_MSG_RIC_AP_SUCCESSIVO)
				|| flussiForm.getInclude().equals(Constants.TIPO_MSG_RIC_AP_TARDIVO)
				|| flussiForm.getInclude().equals(Constants.RICORSO_IN_APPELLO_CDA_MSG)) {
			validateDataDeposito(flussiForm, errors);
		} else if (flussiForm.getInclude().equals(Constants.PROROGA_SEQUESTRO_MSG)) {
			validateDataRicezione(flussiForm, errors);
		} else if (flussiForm.getInclude().equals(Constants.TIPO_MSG_VERSIONAMENTO)) {
			validateDataRicezione(flussiForm, errors);
		} else if (flussiForm.getInclude().equals(Constants.SOSPENSIONE_TEMP_MSG)) {
			if (profilo.getIdTipoUfficio() != Constants.TIPO_UFFICIO_PROCURA_GENERALE) {
				validateDataDeposito(flussiForm, errors);
			} else {
				validateDataRicezione(flussiForm, errors);
			}
		} else if (flussiForm.getInclude().equals(Constants.PROROGA_SOSPENSIONE_MSG)) {
			validateDataRicezione(flussiForm, errors);
		} else if (flussiForm.getInclude().equals(Constants.SEQUESTRO_SOSPENSIONE_MSG)) {
			validateDataRicezione(flussiForm, errors);
		}
		// DA PROVARE
		else if (flussiForm.getInclude().equals(Constants.TIPO_MSG_DECRETO_DECISORIO_II_GRADO)
				|| flussiForm.getInclude().equals(Constants.TIPO_MSG_PROVV_ISTANZA_CDA_CC)
				|| flussiForm.getInclude().equals(Constants.TIPO_MSG_DECRETO_DECISORIO_II_GRADO_CAM)
				|| flussiForm.getInclude()
						.equals(Constants.TIPO_MSG_DECRETO_DECISORIO_II_GRADO_AGGRAVAMENTO)) {
			// Procura generale della repubblica presso la CdA (8) o presso la
			// Cassazione (2)?
			if (profilo.getIdTipoUfficio() == Constants.TIPO_UFFICIO_PROCURA_GENERALE) {
				validateDataDeposito(flussiForm, errors);
			} else if (profilo.getIdTipoUfficio() != Constants.TIPO_UFFICIO_PROCURA_GENERALE) {
				validateDataRicezione(flussiForm, errors);
			}
		}
	}

	public boolean validateApriFlusso(FlussiForm flussiForm, HttpServletRequest request, Errors errors) {

		ProfiloForm profilo = (ProfiloForm) request.getSession().getAttribute(Constants.PROFILO);
		if (flussiForm.getProcedimentoDto() != null
				&& flussiForm.getProcedimentoDto().getCODI_FK_ID_TIPO_PROC() != null
				&& flussiForm.getProcedimentoDto().getCODI_FK_ID_TIPO_PROC()
						.equals(Constants.TIPOPROC_AGGRAVAMENTO_PERSONALE)) {
			return validateApriFlussoPerAggravamento(flussiForm, errors, profilo);
		}
		if (flussiForm.getInclude().equals(Constants.DEFINITIVITA_PU_MSG)
				|| flussiForm.getInclude().equals(Constants.DEFINITIVITA_PP_MSG)
				|| flussiForm.getInclude().equals(Constants.DEFINITIVITA_PG_MSG)
				|| flussiForm.getInclude().equals(Constants.DEFINITIVITA_REVOCAZIONE_CONFISCA_MSG)
				|| flussiForm.getInclude().equals(Constants.DEFINITIVITA_II_GRADO_MSG)) {
			return validateApriDefinitivita(flussiForm, request, errors);
		} else if (flussiForm.getInclude().equals(Constants.DECRETO_DECISORIO_I_GRADO_PU_MSG)
				|| flussiForm.getInclude().equals(Constants.DECRETO_DECISORIO_I_GRADO_PP_MSG)
				|| flussiForm.getInclude().equals(Constants.DECRETO_DECISORIO_I_GRADO_PG_MSG)
				|| flussiForm.getInclude().equals(Constants.DECRETO_FISSAZIONE_UDIENZA_MSG)
				|| flussiForm.getInclude().equals(Constants.DECRETO_INCOMPETENZA_PROCURA_MSG)
				|| flussiForm.getInclude().equals(Constants.DECRETO_INCOMPETENZA_RESTITUZIONE_ATTI_MSG)
				|| flussiForm.getInclude().equals(Constants.DECRETO_INCOMPETENZA_TRASF_ALTRO_TRIBUNALE_MSG)) {
			return validateApriDecreto(flussiForm, errors, profilo);
		} else if (flussiForm.getInclude().equals(Constants.SEQUESTRO_MSG_PU)
				|| flussiForm.getInclude().equals(Constants.SEQUESTRO_MSG_PG)
				|| flussiForm.getInclude().equals(Constants.SEQUESTRO_MSG_CDA)
				|| flussiForm.getInclude().equals(Constants.SEQUESTRO_ANT_MSG_PU)
				|| flussiForm.getInclude().equals(Constants.SEQUESTRO_ANT_MSG_PG)
				|| flussiForm.getInclude().equals(Constants.SEQUESTRO_URG_MSG_PU)
				|| flussiForm.getInclude().equals(Constants.SEQUESTRO_URG_MSG_PG)) {
			return validateApriDecretoSequestro(flussiForm, errors);
		} else if (flussiForm.getInclude().equals(Constants.DECRETO_CONVALIDA_SEQ_MSG)) {
			// || flussiForm.getInclude().equals(Constants.DECRETO_CONVALIDA_SEQ_PP_MSG))
			return validateApriDecretoConvalidaSequestro(flussiForm, errors);
		} else if (flussiForm.getInclude().equals(Constants.DECRETO_ANTICIPAZIONE_UDIENZA_MSG)
				|| flussiForm.getInclude().equals(Constants.DECRETO_RINVIO_UDIENZA_MSG)
				|| flussiForm.getInclude().equals(Constants.VERBALE_RINVIO_UDIENZA_MSG)
				/*
				 * ISSUE MEV : aggiunta tipologia per visualizzare verbale di udienza di riserva Numero MEV :
				 * 15b Autore : Gioggi Data : 07 ago 2018 Branch : MEV_15b
				 */
				|| flussiForm.getInclude().equals(Constants.VERBALE_UDIENZA_RISERVA_MSG)) {
			// ***** FINE INTERVENTO MEV_15b *****//
			return validateApriDecretoAntRinvUdienzaoVerbaleRinvioUdienza(flussiForm, errors,
					flussiForm.getInclude());
		} else if (flussiForm.getInclude().equals(Constants.DECRETO_REMISSIONE_SU_RUOLO_MSG)
				|| flussiForm.getInclude().equals(Constants.DECRETO_ASSEGNAZIONE_NUOVO_RUOLO_MSG)) {
			return validateApriDecretoRemAmmRuoloUdienza(flussiForm, errors);
		} else if (flussiForm.getInclude().equals(Constants.OPPOSIZIONE_AL_DISSEQUESTRO_ATTO_MSG)) {
			return validateApriOpposizioneDissequestro(flussiForm, errors);
		} else if (flussiForm.getInclude().equals(Constants.SENTENZA_OPPOSIZIONE_AL_DISSEQUESTRO_MSG)) {
			return validateApriSentenzaOpposizioneDissequestro(flussiForm, errors);
		} else if (flussiForm.getInclude().equals(Constants.SENTENZA_REVOCAZIONE_CONFISCA_MSG)) {
			return validateApriSentenzaRevocazioneConfisca(flussiForm, errors);
		} else if (flussiForm.getInclude().equals(Constants.RICORSO_IN_APPELLO_PU_A_TRIB_MSG)
				|| flussiForm.getInclude().equals(Constants.RICORSO_IN_APPELLO_PG_A_TRIB_MSG)
				|| flussiForm.getInclude().equals(Constants.RICORSO_IN_APPELLO_TRIB_A_PU_MSG)
				|| flussiForm.getInclude().equals(Constants.RICORSO_IN_APPELLO_TRIB_A_PG_MSG)) {
			return validateApriRicorso(flussiForm, errors, Constants.STATOPROC_DEFINITO_I_GRADO);
		} else if (flussiForm.getInclude().equals(Constants.DECRETO_REVOCA_SEQUESTRO_MSG)) {
			return validateApriDecretoRevocaSequestro(flussiForm, errors);
		} else if (flussiForm.getInclude().equals(Constants.REVOCA_OPPOSIZIONE_AL_DISSEQUESTRO_MSG)) {
			return validateApriRevocaOpposizioneDissequestro(flussiForm, errors);
		} else if (flussiForm.getInclude().equals(Constants.TIPO_MSG_VERSIONAMENTO)) {
			return validateApriVersionamento(flussiForm, errors);
		} else if (flussiForm.getInclude().equals(Constants.PROROGA_SEQUESTRO_MSG)) {
			return validateApriProrogaSequestro(flussiForm, errors);
		} else if (flussiForm.getInclude().equals(Constants.SOSPENSIONE_TEMP_MSG)) {
			return validateApriSospensioneTemporanea(flussiForm, errors);
		} else if (flussiForm.getInclude().equals(Constants.PROROGA_SOSPENSIONE_MSG)) {
			return validateApriProrogaSospensioneTemporanea(flussiForm, errors);
		} else if (flussiForm.getInclude().equals(Constants.SEQUESTRO_SOSPENSIONE_MSG)) {
			return validateApriSequestroSospensioneTemporanea(flussiForm, errors);
		} else if (flussiForm.getInclude().equals(Constants.DECRETO_APPLICAZIONE_PROVV_MSG)) {
			return validateApriApplicazioneProvv(flussiForm, errors);
		} else if (flussiForm.getInclude().equals(Constants.REVOCA_APPLICAZIONE_PROVV_MSG)) {
			return validateApriRevocaApplicazioneProvv(flussiForm, errors);
		} else if (flussiForm.getInclude().equals(Constants.RICORSO_IN_CASSAZIONE_DA_TRIB_MSG)) {
			return validateApriRicorsoInCassDaTrib(flussiForm, errors);
		} else if (flussiForm.getInclude().equals(Constants.NRG_DELLA_CASSAZIONE_MSG)) {
			return validateApriNRGDaCassazione(flussiForm, errors);
		} else if (flussiForm.getInclude().equals(Constants.RICORSO_IN_CASSAZIONE_CDA_A_PG_MSG)) {
			return validateApriRicorsoInCassDaPG(flussiForm, errors);		
			
		//P.Q. 10/05/2023 Ticket 202303270120
		// Se il flusso che sto acquisendo è 1595 dsProvvedimentoSuIstanzaDaCdA (senza fascicolo),
		// devo verificare l'esistenza sulla t_rel_proc_proc del procedimento di PG			
		} else if (flussiForm.getInclude().equals(Constants.TIPO_MSG_PROVV_ISTANZA_CDA_CC))
			return validateProvvedimentoSuIstanza(flussiForm, errors);
		//P.Q. 10/05/2023 Ticket 202303270120
		
		/*
		 * controllare da qui per i ricorsi successivi
		 * 
		 * else if (flussiForm.getInclude().equals(Constants.TIPO_MSG_RIC_AP_SUCCESSIVO) ||
		 * flussiForm.getInclude().equals(Constants.TIPO_MSG_RIC_AP_TARDIVO)) { return
		 * validateApriRicorso(flussiForm, errors,Constants.STATOPROC_INVIATO_IN_CDA); }
		 */else {
			return true;
		}
	}

	@SuppressWarnings("rawtypes")
	private boolean validateApriDecretoRemAmmRuoloUdienza(FlussiForm flussiForm, Errors errors) {

		int tipologia = 0;// Tipologia decreto
		T_REL_UDIENZA_STATO_UDDto udienzaAtto = null;
		List<T_REL_UDIENZA_STATO_UDDto> lista = flussiForm.getListaT_REL_UDIENZA_STATO_UDDto();
		for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
			T_REL_UDIENZA_STATO_UDDto t_REL_UDIENZA_STATO_UDDto = (T_REL_UDIENZA_STATO_UDDto) iterator.next();
			if (!t_REL_UDIENZA_STATO_UDDto.getCODI_K_ID_STATO_UD().equals("1"))
				udienzaAtto = t_REL_UDIENZA_STATO_UDDto;
		}
		// Recupero l'id dell'Udienza legata all'Atto
		if (udienzaAtto != null) {
			String idUdienza = udienzaAtto.getCODI_K_ID_UDIENZA();
			long idUffOwn = ((Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE))
					.longValue();
			return esisteUdienza(idUdienza, idUffOwn, tipologia);
		} else
			return true;
	}

	@SuppressWarnings("rawtypes")
	private boolean validateApriDecretoAntRinvUdienzaoVerbaleRinvioUdienza(FlussiForm flussiForm,
			Errors errors, String decretoVerbale) {

		int tipologia = 0;// Tipologia decreto
		if (flussiForm.getInclude().equals(Constants.VERBALE_RINVIO_UDIENZA_MSG)
				/*
				 * ISSUE MEV : aggiunta tipologia per visualizzare verbale di udienza di riserva Numero MEV :
				 * 15b Autore : Gioggi Data : 07 ago 2018 Branch : MEV_15b
				 */
				|| flussiForm.getInclude().equals(Constants.VERBALE_UDIENZA_RISERVA_MSG))
			// ***** FINE INTERVENTO MEV_15b *****//
			tipologia = 1;// Tipologia verbale di udienza
		// Recupero i dati di T_REL_UDIENZA_STATO_UD relativi alla nuova udienza
		if (flussiForm.getListaT_UDIENZADto().size() > 0) {
			// T_UDIENZADto udienza = flussiForm.getListaT_UDIENZADto().get(0);
			T_REL_UDIENZA_STATO_UDDto udienzaAtto = null;
			List<T_REL_UDIENZA_STATO_UDDto> lista = flussiForm.getListaT_REL_UDIENZA_STATO_UDDto();
			for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
				T_REL_UDIENZA_STATO_UDDto t_REL_UDIENZA_STATO_UDDto = (T_REL_UDIENZA_STATO_UDDto) iterator
						.next();
				if (!t_REL_UDIENZA_STATO_UDDto.getCODI_K_ID_STATO_UD().equals("1"))
					udienzaAtto = t_REL_UDIENZA_STATO_UDDto;
			}
			// Recupero l'id dell'Udienza legata all'Atto
			if (udienzaAtto != null) {
				String idUdienza = udienzaAtto.getCODI_K_ID_UDIENZA();
				long idUffOwn = ((Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE))
						.longValue();
				return esisteUdienza(idUdienza, idUffOwn, tipologia);
			} else
				return true;
		} else
			return true;
	}

	private boolean esisteUdienza(String idUdienza, long idUffOwn, int tipologia) {

		UdienzaForm udienza = attoService.getUdienzaById(Long.valueOf(idUdienza).longValue(), idUffOwn);
		if (udienza != null) {
			return true;
		} else {
			if (tipologia == 0)
				super.setMessage(Constants.MESSAGE, "flussi.decreto.udienzaNonPresente", null, false);
			else if (tipologia == 1)
				super.setMessage(Constants.MESSAGE, "flussi.verbale.udienzaNonPresente", null, false);
			return false;
		}
	}

	private final boolean validateApriFlussoPerAggravamento(final FlussiForm form, Errors errors,
			final ProfiloForm profilo) {

		if (form.getInclude().equals(Constants.DECRETO_DECISORIO_I_GRADO_PU_MSG)
				|| form.getInclude().equals(Constants.DECRETO_DECISORIO_I_GRADO_PP_MSG)
				|| form.getInclude().equals(Constants.DECRETO_DECISORIO_I_GRADO_PG_MSG)) {
			return validateApriDecreto(form, errors, profilo);
		}
		if (form.getInclude().equals(Constants.PROPOSTA_AGGRAVAMENTO_MSG)
				|| form.getInclude().equals(Constants.PROPOSTA_AGGRAVAMENTO_DA_TRIBUNALE_MSG)) {
			return validateRegistraFlussoPropostaAggravamento(form, errors);
		}
		if (form.getInclude().equals(Constants.DECRETO_FISSAZIONE_UDIENZA_MSG)) {
			return validateApriDecreto(form, errors, profilo);
		}
		return true;
	}

	private boolean validateRegistraFlussoPropostaAggravamento(final FlussiForm form, Errors errors) {

		List<Message> customMessageList = new ArrayList<Message>();
		if (form.getRelProcProcDto() == null) {
			super.addMessageIntoList(customMessageList,
					"flussi.apertura.error.aggravamento.padre.notPresent");
			super.setMessages(customMessageList, true, errors);
			return false;
		}
		Short annoPadre = Short.valueOf(form.getRelProcProcDto().getNUME_ANNO_PROC2());
		//P.Q. 04/09/2025 Ticket 20250807017 Inizio
		Integer numeroPadre = Integer.valueOf(form.getRelProcProcDto().getNUME_PROCEDIMENTO2());
		//P.Q. 04/09/2025 Ticket 20250807017 Fine
		Integer idUfficioMittente = Integer.valueOf(form.getProcedimentoDto().getCODI_FK_ID_UFF_OWNER());
		Long idPadreVero = procedimentoService.getIdProcDaRelProcProc(annoPadre, numeroPadre,
				idUfficioMittente);
		if (idPadreVero == null) {
			super.addMessageIntoList(customMessageList,
					"flussi.apertura.error.aggravamento.padre.notPresent");
			super.setMessages(customMessageList, true, errors);
			return false;
		}
		ProcedimentoForm padreVero = procedimentoService.getDettaglioProcedimento(idPadreVero);
		form.setAnnoPadre(padreVero.getAnno());
		form.setNumeroPadre(padreVero.getNumero());
		if (form.getListaSoggettoForm() != null && !form.getListaSoggettoForm().isEmpty()) {
			form.getListaSoggettoForm().get(0).setDescrRuolo("DIRETTO");
		}
		return true;
	}

	private boolean validateApriDefinitivita(FlussiForm flussiForm, HttpServletRequest request,
			Errors errors) {

		if (flussiForm.getInclude().equals(Constants.DEFINITIVITA_PU_MSG)
				|| flussiForm.getInclude().equals(Constants.DEFINITIVITA_PP_MSG)
				|| flussiForm.getInclude().equals(Constants.DEFINITIVITA_PG_MSG)
				|| flussiForm.getInclude().equals(Constants.DEFINITIVITA_REVOCAZIONE_CONFISCA_MSG)
				|| flussiForm.getInclude().equals(Constants.DEFINITIVITA_II_GRADO_MSG)) {
			if ((flussiForm.getAttoProcDto() != null)
					&& (flussiForm.getAttoProcDto().getCODI_K_ID_ATTO() != null)) {
				long idAtto = Long.parseLong(flussiForm.getAttoProcDto().getCODI_K_ID_ATTO());
				Integer idUffOwn = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
				AttoForm attoForm = new AttoForm();
				attoForm = attoService.getAttoById(idAtto, idUffOwn);
				if ((attoForm != null) && (attoForm.getCodAtto() < 1)) {
					List<Message> customMessageList = new ArrayList<Message>();
					super.addMessageIntoList(customMessageList, "flussi.definitivita.attoNonPresente");
					super.setMessages(customMessageList, true, errors);
					return false;
				}
			}
		}
		return true;
	}

	public void validateDataDeposito(FlussiForm flussiForm, Errors result) {
		if (flussiForm.getAttoDto().getDATA_DEPOSITO_ASSOCIA() == null)
			result.rejectValue("attoDto.DATA_DEPOSITO_ASSOCIA", "NotNull");

		if (!validateDataAntecedente1900(flussiForm.getAttoDto().getDATA_DEPOSITO_ASSOCIA()))
			result.rejectValue("attoDto.DATA_DEPOSITO_ASSOCIA", "ModernDate.error.custom");
	}

	public void validateDataRicezione(FlussiForm flussiForm, Errors result) {

		if (flussiForm.getAttoDto().getDATA_RICEZIONE_ASSOCIA() == null) {
			result.rejectValue("attoDto.DATA_RICEZIONE_ASSOCIA", "NotNull");
		}

		if (!validateDataAntecedente1900(flussiForm.getAttoDto().getDATA_RICEZIONE_ASSOCIA())) {
			result.rejectValue("attoDto.DATA_RICEZIONE_ASSOCIA", "ModernDate.error.custom");
		}
	}

	private boolean validateApriDecreto(FlussiForm flussiForm, Errors errors, ProfiloForm profilo) {

		if (esisteProposta(flussiForm))
			return true;

		super.setMessage(Constants.MESSAGE, "flussi.decisorio.attoNonPresente", null, false);
		return false;

	}

	private boolean validateApriDecretoSequestro(FlussiForm flussiForm, Errors errors) {

		if (esisteProposta(flussiForm)) {
			return validateApriDecretoAntRinvUdienzaoVerbaleRinvioUdienza(flussiForm, errors,
					flussiForm.getInclude());
		} else {
			super.setMessage(Constants.MESSAGE, "flussi.sequestroOrdinario.attoNonPresente", null, false);
			return false;
		}
	}

	private boolean validateApriDecretoConvalidaSequestro(FlussiForm flussiForm, Errors errors) {

		if (esisteSequestro(flussiForm)) {
			return validateApriDecretoAntRinvUdienzaoVerbaleRinvioUdienza(flussiForm, errors,
					flussiForm.getInclude());
		} else {
			super.setMessage(Constants.MESSAGE, "flussi.convalidaSequestro.sequestroNonPresente", null,
					false);
			return false;
		}
	}

	private boolean validateApriDecretoRevocaSequestro(FlussiForm flussiForm, Errors errors) {

		if (!esisteSequestro(flussiForm)) {
			super.setMessage(Constants.MESSAGE, "flussi.revocaSequestro.sequestroNonPresente", null, false);
			return false;
		}
		return true;
	}

	public void validateSceltaCheckbox(FlussiForm flussiForm, BindingResult result,
			HttpServletRequest request) {

		if (flussiForm.getIdFlusso() == null) {
			super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
			result.reject(Constants.MESSAGE);
		}
	}

	private boolean esisteProposta(FlussiForm flussiForm) {

		long idUffOwn = ((Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE))
				.longValue();
		if (flussiForm.getProcedimentoDto() != null
				&& flussiForm.getProcedimentoDto().getCODI_K_ID_PROC() != null) {
			long idProc2 = Long.parseLong(flussiForm.getProcedimentoDto().getCODI_K_ID_PROC());
			long idUffMitt = new Long(flussiForm.getIdUffMittente());
			List<TRelProcProc> lista = procedimentoService.getProc1ByIdProc2(idProc2, idUffMitt, idUffOwn);
			if (lista == null || lista.size() == 0) {
				super.setMessage(Constants.MESSAGE, "flussi.apertura.error.noProposta", null, false);
				return false;
			}
			return true;
		}
		super.setMessage(Constants.MESSAGE, "flussi.apertura.error.noProposta", null, false);
		return false;
	}

	private boolean esisteSequestro(FlussiForm flussiForm) {

		int idUffOwn = ((Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE)).intValue();
		long idProc = 0;

		if (flussiForm.getProcedimentoDto() != null
				&& flussiForm.getProcedimentoDto().getCODI_K_ID_PROC() != null) {
			long idProc2 = Long.parseLong(flussiForm.getProcedimentoDto().getCODI_K_ID_PROC());
			long idUffMitt = new Long(flussiForm.getIdUffMittente());
			List<TRelProcProc> lista = procedimentoService.getProc1ByIdProc2(idProc2, idUffMitt, idUffOwn);
			if (lista == null || lista.size() == 0) {
				return false;
			}
			idProc = lista.get(0).getId().getCodiKIdProc1();
		}

		long idAtto = Long.parseLong(flussiForm.getRelAttiDto().getCODI_K_ID_ATTO_OR());
		AttoForm atto = attoService.dettaglioAtto(idUffOwn, idAtto, idProc, "0");
		// codUtente impostato a 0 perché tanto nella stored procedure non è usato più
		if (atto == null || atto.getCodAtto() == 0) {
			return false;
		}

		return true;
	}

	public void validateSceltaAccertamento(FlussiForm flussiForm, BindingResult result) {

		if (flussiForm.getIdAccertamento() == null) {
			super.setMessage(Constants.MESSAGE, "flussi.tableAccertamenti.item.selection", null, false);
			result.reject(Constants.MESSAGE);
		}
	}

	private boolean esisteProcedimento(FlussiForm flussiForm) {

		int idUffOwn = ((Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE)).intValue();
		// long idProc = 0;

		if (flussiForm.getProcedimentoDto() != null
				&& flussiForm.getProcedimentoDto().getCODI_K_ID_PROC() != null) {
			long idProc2 = Long.parseLong(flussiForm.getProcedimentoDto().getCODI_K_ID_PROC());
			long idUffMitt = new Long(flussiForm.getIdUffMittente());
			List<TRelProcProc> lista = procedimentoService.getProc1ByIdProc2(idProc2, idUffMitt, idUffOwn);
			if (lista == null || lista.size() == 0) {
				return false;
			}
			// idProc = lista.get(0).getId().getCodiKIdProc1();
			return true;
		}

		return false;
	}

	private boolean esisteAttoOrigine(FlussiForm flussiForm) {
		return esisteAttoOrigine(flussiForm, 0);
	}

	private boolean esisteAttoOrigine(FlussiForm flussiForm, long idTipoFasc) {

		int idUffOwn = ((Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE)).intValue();
		long idProc = 0;

		if (flussiForm.getProcedimentoDto() != null
				&& flussiForm.getProcedimentoDto().getCODI_K_ID_PROC() != null) {
			long idProc2 = Long.parseLong(flussiForm.getProcedimentoDto().getCODI_K_ID_PROC());
			long idUffMitt = new Long(flussiForm.getIdUffMittente());
			List<TRelProcProc> lista = null;
			if (idTipoFasc > 0) {
				lista = procedimentoService.getProc1ByIdProc2AndTipoFasc(idProc2, idUffMitt, idTipoFasc);
			} else {
				lista = procedimentoService.getProc1ByIdProc2(idProc2, idUffMitt);
			}
			if (lista == null || lista.size() == 0) {
				return false;
			}
			for (int i = 0; i < lista.size(); i++) {
				idProc = lista.get(i).getId().getCodiKIdProc1();
				long idAtto = Long.parseLong(flussiForm.getRelAttiDto().getCODI_K_ID_ATTO_OR());
				AttoForm atto = attoService.dettaglioAtto(idUffOwn, idAtto, idProc, "0");
				// codUtente impostato a 0 perché tanto nella stored procedure non è usato più
				if (atto != null && atto.getCodAtto() != 0) {
					return true;
				}
			}
		}

		return false;
	}

	private boolean esisteAttoOrigineRevocaOpposizioneDissequestroIIGrado(FlussiForm flussiForm,
			long idTipoFasc) {

		int idUffOwn = ((Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE)).intValue();
		long idProc = 0;

		if (esisteProcedimento(flussiForm)) {
			if (flussiForm.getProcedimentoDto() != null
					&& flussiForm.getProcedimentoDto().getCODI_K_ID_PROC() != null) {
				// long idProc2 = Long.parseLong(flussiForm.getProcedimentoDto()
				// .getCODI_K_ID_PROC());
				TRelAttoProc attoProc = null;
				long idAtto = Long.parseLong(flussiForm.getRelAttiDto().getCODI_K_ID_ATTO_OR());
				attoProc = procedimentoService.getProcByIdAttoAndTipoFasc(idAtto, idUffOwn, idTipoFasc);
				if (attoProc == null) {
					return false;
				}
				idProc = attoProc.getId().getCodiKIdProc();
				AttoForm atto = attoService.dettaglioAtto(idUffOwn, idAtto, idProc, "0");
				// codUtente impostato a 0 perché tanto nella stored procedure non è usato più
				if (atto != null && atto.getCodAtto() != 0) {
					return true;
				}
			}

			return false;
		} else {
			super.setMessage(Constants.MESSAGE, "flussi.apertura.error.noProposta", null, false);
			return false;
		}
	}

	private boolean validateApriOpposizioneDissequestro(FlussiForm flussiForm, Errors errors) {

		if (esisteProcedimento(flussiForm)) {
			if (esisteAttoOrigine(flussiForm)) {
				return true;
			} else {
				super.setMessage(Constants.MESSAGE, "flussi.opposizioneDissequestro.attoNonPresente", null,
						false);
				return false;
			}
		} else {
			super.setMessage(Constants.MESSAGE, "flussi.apertura.error.noProposta", null, false);
			return false;
		}
	}

	private boolean validateApriSentenzaOpposizioneDissequestro(FlussiForm flussiForm, Errors errors) {

		ProfiloForm profilo = (ProfiloForm) request.getSession().getAttribute(Constants.PROFILO);
		long idTipoFasc = 0;
		if (profilo.getIdTipoUfficio() == Constants.TIPO_UFFICIO_PROCURA_GENERALE) {
			idTipoFasc = Constants.TIPO_FASCICOLO_OPP_REVOCA_SEQUESTRO;
		}

		if (esisteAttoOrigine(flussiForm, idTipoFasc)) {
			return true;
		} else {
			super.setMessage(Constants.MESSAGE, "flussi.sentenzaOpposizioneDissequestro.attoNonPresente",
					null, false);
			return false;
		}
	}

	private boolean validateApriRevocaOpposizioneDissequestro(FlussiForm flussiForm, Errors errors) {

		boolean result = false;
		if (flussiForm.getAttoDto().getCODI_FK_ID_CAT_ATTO()
				.equals(Constants.REVOCA_DECRETO_OPPOSIZIONE_DISSEQUESTRO)) {
			result = esisteAttoOrigine(flussiForm);
		} else if (flussiForm.getAttoDto().getCODI_FK_ID_CAT_ATTO()
				.equals(Constants.REVOCA_DECRETO_OPPOSIZIONE_DISSEQUESTRO_II_GRADO)) {
			result = esisteAttoOrigineRevocaOpposizioneDissequestroIIGrado(flussiForm,
					Constants.TIPO_FASCICOLO_OPP_REVOCA_SEQUESTRO);
		}

		if (result) {
			return true;
		} else {
			super.setMessage(Constants.MESSAGE, "flussi.revocaOpposizioneDissequestro.attoNonPresente", null,
					false);
			return false;
		}
	}

	private boolean validateApriSentenzaRevocazioneConfisca(FlussiForm flussiForm, Errors errors) {

		ProfiloForm profilo = (ProfiloForm) request.getSession().getAttribute(Constants.PROFILO);

		long idTipoFasc = 0;
		if (profilo.getIdTipoUfficio() == Constants.TIPO_UFFICIO_PROCURA_GENERALE) {
			idTipoFasc = Constants.TIPO_FASCICOLO_REVOCAZIONE;
		}

		if (esisteAttoOrigine(flussiForm, idTipoFasc)) {
			return true;
		} else {
			super.setMessage(Constants.MESSAGE, "flussi.sentenzaRevocazioneConfisca.attoNonPresente", null,
					false);
			return false;
		}
	}

	private boolean validateApriVersionamento(FlussiForm flussiForm, Errors errors) {

		if (esisteProcedimento(flussiForm)) {
			return true;
		} else {
			super.setMessage(Constants.MESSAGE, "flussi.apertura.error.noProposta", null, false);
			return false;
		}
	}

	private boolean validateApriRicorso(FlussiForm flussiForm, Errors errors, String stato) {

		if (esisteDecisorioDiIGrado(flussiForm, stato))
			return true;
		if (stato.equals(Constants.STATOPROC_DEFINITO_I_GRADO)) {
			super.setMessage(Constants.MESSAGE, "flussi.ricorso.decisorioNonPresente", null, false);
		} else {
			super.setMessage(Constants.MESSAGE, "flussi.ricorso.invioCdaNonPresente", null, false);
		}
		return false;

	}

	private boolean validateApriRicorsoInCassDaTrib(FlussiForm flussiForm, Errors errors) {

		if (flussiForm.getNumProcDest() != null) {
			return true;
		} else {
			super.setMessage(Constants.MESSAGE, "flussi.apertura.error.noIstanza", null, false);
		}
		return false;
	}

	private boolean validateApriNRGDaCassazione(FlussiForm flussiForm, Errors errors) {
		// GC 24/06/2019 SITMP-239: Correzione dell'acquisizione NRG
		// il codice precedentemente scritto risulta inutile ed è stato quindi tolto.
		//ProfiloForm profilo = (ProfiloForm) request.getSession().getAttribute(Constants.PROFILO);
		//if (profilo.getIdTipoUfficio() == Constants.TIPO_UFFICIO_TRIBUNALE_L) {
		//	if (!attoService.esisteDecretoConvalidaSequesto(
		//			Long.parseLong(flussiForm.getAttoDto().getCODI_K_ID_ATTO()),
		//			Long.parseLong(flussiForm.getAttoDto().getCODI_K_ID_UFF_OWNER()))) {
				int idCatAtto = 0;
				if (flussiForm.getProcedimentoDto().getCODI_FK_ID_TIPO_FASC().equals("1"))
					idCatAtto = Integer.parseInt(Constants.SENTENZA_SU_RICORSO_II_GRADO);
				if (flussiForm.getProcedimentoDto().getCODI_FK_ID_TIPO_FASC().equals("6"))
					idCatAtto = Integer.parseInt(Constants.SENTENZA_SU_RICORSO_II_GRADO_DA_CDA_CAM);
				
				if (attoService.getRelAttoProcByIdProcAndCat(
						Long.parseLong(flussiForm.getProcedimentoDto().getCODI_K_ID_PROC()), idCatAtto))
					return true;
				else {
					super.setMessage(Constants.MESSAGE, "flussi.ricorso.NRG", null, false);
					return false;
				}
		//	}
		//	return true;
		//} else {
		//	if (attoService.esisteDecretoConvalidaSequesto(
		//			Long.parseLong(flussiForm.getAttoDto().getCODI_K_ID_ATTO()),
		//			Long.parseLong(flussiForm.getAttoDto().getCODI_K_ID_UFF_OWNER())))
		//		return true;
		/*	else {
				super.setMessage(Constants.MESSAGE, "flussi.ricorso.NRG", null, false);
				return false;
			}
			
		}*/
	}

	private boolean validateApriRicorsoInCassDaPG(FlussiForm flussiForm, Errors errors) {

		if (esisteDecisorioDiIIGrado(flussiForm))
			return true;
		else {
			super.setMessage(Constants.MESSAGE, "flussi.ricorso.decisorioIIGradoNonPresente", null, false);
			return false;
		}
	}

	private boolean esisteDecisorioDiIGrado(FlussiForm flussiForm, String stato) {

		int idUffOwn = ((Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE)).intValue();
		long idProc = 0;

		if (flussiForm.getProcedimentoDto() != null
				&& flussiForm.getProcedimentoDto().getCODI_K_ID_PROC() != null) {
			long idProc2 = Long.parseLong(flussiForm.getProcedimentoDto().getCODI_K_ID_PROC());
			long idUffMitt = new Long(flussiForm.getIdUffMittente());

			List<TRelProcProc> lista = procedimentoService.getProc1ByIdProc2(idProc2, idUffMitt, idUffOwn);
			if (lista == null || lista.size() == 0) {
				return false;
			}
			for (int i = 0; i < lista.size(); i++) {
				idProc = lista.get(i).getTProcedimento().getCodiKIdProc();
				List<TRelIterProc> listaTRel = procedimentoService.verificaTRelIterProc(idProc, idUffOwn);
				if (listaTRel == null || listaTRel.size() == 0) {
					return false;
				}
				for (int j = 0; j < listaTRel.size(); j++) {
					TRelIterProc iter = listaTRel.get(j);
					if (iter.getTTpStatoProc().getCodiKIdStatoProc() == Integer.parseInt(stato))
						return true;
				}

			}
		}

		return false;
	}

	public void validateSceltaProcedimentoInCDA(FlussiForm flussiForm, BindingResult result) {

		if (flussiForm.getIdProcedimentoCda() == null) {
			super.setMessage(Constants.MESSAGE, "flussi.ricorsosuccessivo.item.selection", null, false);
			result.reject(Constants.MESSAGE);
		}
	}

	private boolean validateApriProrogaSequestro(FlussiForm flussiForm, Errors errors) {

		if (esisteProcedimento(flussiForm)) {
			if (esisteAttoOrigine(flussiForm)) {
				return true;
			} else {
				super.setMessage(Constants.MESSAGE, "flussi.prorogaSequestro.attoNonPresente", null, false);
				return false;
			}
		} else {
			super.setMessage(Constants.MESSAGE, "flussi.apertura.error.noProposta", null, false);
			return false;
		}

	}

	private boolean validateApriSospensioneTemporanea(FlussiForm flussiForm, Errors errors) {

		return esisteProposta(flussiForm);

	}

	private boolean validateApriProrogaSospensioneTemporanea(FlussiForm flussiForm, Errors errors) {

		if (esisteProcedimento(flussiForm)) {
			if (esisteAttoOrigine(flussiForm)) {
				return true;
			} else {
				super.setMessage(Constants.MESSAGE, "flussi.prorogaSospensioneTemporanea.attoNonPresente",
						null, false);
				return false;
			}
		} else {
			super.setMessage(Constants.MESSAGE, "flussi.apertura.error.noProposta", null, false);
			return false;
		}

	}

	private boolean validateApriSequestroSospensioneTemporanea(FlussiForm flussiForm, Errors errors) {

		if (esisteProcedimento(flussiForm)) {
			if (esisteAttoOrigine(flussiForm)) {
				return true;
			} else {
				super.setMessage(Constants.MESSAGE, "flussi.sequestroSospensioneTemporanea.attoNonPresente",
						null, false);
				return false;
			}
		} else {
			super.setMessage(Constants.MESSAGE, "flussi.apertura.error.noProposta", null, false);
			return false;
		}

	}

	private boolean validateApriApplicazioneProvv(FlussiForm flussiForm, Errors errors) {

		return esisteProposta(flussiForm);
	}

	private boolean validateDataAntecedente1900(String dataDaValidare) {

		if (dataDaValidare == null)
			return true;

		SimpleDateFormat frmt = new SimpleDateFormat("dd/MM/yyyy");
		Date thresholdDate = null;
		Date dataTmp = null;
		try {
			thresholdDate = frmt.parse("01/01/1900");
			dataTmp = frmt.parse(dataDaValidare);

		} catch (ParseException exc) {
			exc.printStackTrace();
		}

		return dataTmp.after(thresholdDate);
	}

	private boolean validateApriRevocaApplicazioneProvv(FlussiForm flussiForm, Errors errors) {

		if (esisteProcedimento(flussiForm)) {
			if (esisteAttoOrigine(flussiForm)) {
				return true;
			} else {
				super.setMessage(Constants.MESSAGE, "flussi.revocaApplicazioneProvvisoria.attoNonPresente",
						null, false);
				return false;
			}
		} else {
			super.setMessage(Constants.MESSAGE, "flussi.apertura.error.noProposta", null, false);
			return false;
		}

	}

	private boolean esisteDecisorioDiIIGrado(FlussiForm flussiForm) {
		int idUffOwn = ((Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE)).intValue();
		if (flussiForm.getRelAttiDto() != null && flussiForm.getRelAttiDto().getCODI_K_ID_ATTO_OR() != null) {
			AttoForm attoForm = attoService
					.getAttoById(Long.parseLong(flussiForm.getRelAttiDto().getCODI_K_ID_ATTO_OR()), idUffOwn);
			if (attoForm == null || (attoForm != null && attoForm.getCodAtto() == 0))
				return false;
			else
				return true;
		}
		return false;
	}
	
	//P.Q. 10/05/2023 Ticket 202303270120
	private boolean validateProvvedimentoSuIstanza(FlussiForm flussiForm, Errors errors) {
		
		if (esisteProcedimento(flussiForm))
			return true;
		else {
			super.setMessage(Constants.MESSAGE, "flussi.provvedimento.istanza", null, false);
			return false;
		}
	}
		
	//P.Q. 10/05/2023 Ticket 202303270120	

}