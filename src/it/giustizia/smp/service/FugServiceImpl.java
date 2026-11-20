package it.giustizia.smp.service;
import it.giustizia.smp.dao.BeneDao;
import it.giustizia.smp.dao.CommonDao;
import it.giustizia.smp.dao.FugDao;
import it.giustizia.smp.dao.ProcedimentoDao;
import it.giustizia.smp.dto.TRelBeneBeneDto;
import it.giustizia.smp.dto.fug.DettaglioAttoFugDto;
import it.giustizia.smp.exception.EccezioneGenerica;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.BeneForm;
import it.giustizia.smp.form.BeneSoggettoForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.FugForm;
import it.giustizia.smp.form.FugModelloForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.model.TAnComuni;
import it.giustizia.smp.model.TAnProvincia;
import it.giustizia.smp.model.TAnStato;
import it.giustizia.smp.model.TAnUfficio;
import it.giustizia.smp.model.TBene;
import it.giustizia.smp.model.TDefPatr;
import it.giustizia.smp.model.TFugInvio;
import it.giustizia.smp.model.TFugMsg;
import it.giustizia.smp.model.TPersonaFisica;
import it.giustizia.smp.model.TPersonaGiur;
import it.giustizia.smp.model.TProcedimento;
import it.giustizia.smp.model.TRelBeneAtto;
import it.giustizia.smp.model.TRelProcSogint;
import it.giustizia.smp.model.TTpCatAtto;
import it.giustizia.smp.model.TUtente;
import it.giustizia.smp.service.webservices.FugWsService;
import it.giustizia.smp.utility.ComparatorUtil;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.validation.FugValidation;
import it.giustizia.smp.webservice.client.fug.AcquisisciModelloA;
import it.giustizia.smp.webservice.client.fug.AcquisisciModelloC;
import it.giustizia.smp.webservice.client.fug.AcquisisciModelloC2;
import it.giustizia.smp.webservice.client.fug.AcquisisciModelloD;
import it.giustizia.smp.webservice.client.fug.AcquisisciModelloH;
import it.giustizia.smp.webservice.client.fug.BeneficiariTipo;
import it.giustizia.smp.webservice.client.fug.InfoProcessoTipo;
import it.giustizia.smp.webservice.client.fug.RichiestaAnnullamentoModello;
import it.giustizia.smp.webservice.client.fug.TipoRegistroTipo;
import it.giustizia.smp.webservice.client.fug.TipoSoggettoTipo;
import it.giustizia.smp.webservice.client.fug.modelli.AnnullaModello;
import it.giustizia.smp.webservice.client.fug.modelli.EsitoFUG;
import it.giustizia.smp.webservice.client.fug.modelli.ModelloA;
import it.giustizia.smp.webservice.client.fug.modelli.ModelloC;
import it.giustizia.smp.webservice.client.fug.modelli.ModelloC2;
import it.giustizia.smp.webservice.client.fug.modelli.ModelloCBase;
import it.giustizia.smp.webservice.client.fug.modelli.ModelloD;
import it.giustizia.smp.webservice.client.fug.modelli.ModelloH;
import it.giustizia.smp.webservice.client.fug.modelli.RispostaModello;
import it.giustizia.smp.webservice.utility.JAXBUtil;
import it.giustizia.smp.webservice.utility.MockResponseAnbscUtil;
import it.giustizia.smp.webservice.utility.MockResponseFUGUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
public class FugServiceImpl implements FugService{
	@Autowired
    private Mapper 	mapperBean;
	@Autowired
	private BeneDao beneDao;
	@Autowired
	private FugDao fugDao;
	@Autowired
	private ProcedimentoDao procedimentoDao;
    @Autowired
	private FugWsService fugWsService;
    @Autowired
    private MockResponseFUGUtil mockResponseFUGUtil;
    @Autowired
	private ProcedimentoService	procedimentoService;
    @Autowired
	private AttoService	attoService;
    @Autowired
	private CommonDao commonDao;
    @Autowired(required = true)
   	private HttpServletRequest	request;
    @Autowired
	private ReloadableResourceBundleMessageSource configParameterSource;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<BeneForm> listaBeniFug(int idUffOwn, long idProcedimento, BeneForm BeneForm) {

		Long idBene = null;
		List queryResults = null;
		if ((BeneForm.getIdBene() != null) && !BeneForm.getIdBene().equals("-1")) {
			idBene = BeneForm.getIdBene();
		}
		queryResults = fugDao.listaBeniFug(idUffOwn, idProcedimento, idBene);
		
		List<BeneForm> risultati = new ArrayList<BeneForm>();
		if ((queryResults == null) || queryResults.isEmpty()) {
			return risultati;
		}
		Iterator iterator = queryResults.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			TBene bene = (TBene) row.get("bene");
			BeneForm form = new BeneForm();
			if (bene != null) {
				mapperBean.map(bene, form);
			}
			risultati.add(form);
		}
		return risultati;
	}

	public List<AttoForm> elencoQuoteModello(int idUffOwner, long idProcedimento, BeneForm beneForm, String codiceUtente, String nomeMetodo) {
		List<AttoForm> listaQuote = new ArrayList<AttoForm>(); 
		
		if ((Constants.FUG_MODELLO_A).equals(beneForm.getModelloFugSelected().getId()) || (Constants.FUG_MODELLO_D).equals(beneForm.getModelloFugSelected().getId())){
			listaQuote = listaQuoteModelliA_D(idUffOwner, idProcedimento, beneForm, codiceUtente, nomeMetodo);	
		}
		
		if ((Constants.FUG_MODELLO_C).equals(beneForm.getModelloFugSelected().getId())){
			listaQuote = listaQuoteModelliC(idUffOwner, idProcedimento, beneForm, codiceUtente, nomeMetodo);
		}
		return listaQuote;
	}
	
	private List<AttoForm> listaQuoteModelliA_D(int idUffOwner, long idProcedimento, BeneForm beneForm, String codiceUtente, String nomeMetodo) {
		
		List<DettaglioAttoFugDto> daoLista = fugDao.quoteModelliA_D(idUffOwner, idProcedimento, beneForm.getIdBene(), beneForm.getModelloFugSelected().getId());
		List<AttoForm> results = new ArrayList<AttoForm>();

		boolean esistonoSequestri = hasSequestri(daoLista);

		for (DettaglioAttoFugDto currentAtto : daoLista) {
			boolean daVisualizzare = true;
			boolean isDecisorio = isDecisorio(currentAtto.getCODI_FK_ID_CAT_ATTO().toString());
			if (esistonoSequestri && isDecisorio) {
				daVisualizzare = false;
			}
			if (daVisualizzare) {
				// Il modello in questione non deve essere gia` stato inviato
				daVisualizzare = !hasInvii(idUffOwner, idProcedimento, currentAtto.getCODI_K_ID_ATTO().longValue(), beneForm, "N"); 
				// Se CdA devo leggere anche con l'id del Procedimento d'origine (se
				// c'e`) per controllare che il modello in questione non sia stato gia`
				// inviato dal Tribunale
				// ProfiloForm profilo = (ProfiloForm) request.getSession().getAttribute("profilo");
				// if (profilo.getIdTipoUfficio() == new Long(Constants.TIPO_UFFICIO_CORTE_D_APPELLO)) {
				// DA IMPLEMENTARE
				// }
			}
			// Visualizzo l'atto
			if (daVisualizzare &&
					daVisualizzare(isDecisorio, currentAtto.getCODI_K_ID_ATTO().longValue(), idUffOwner, idProcedimento, beneForm.getIdBene(), beneForm.getModelloFugSelected().getId().equals(Constants.FUG_MODELLO_D))) {
				beneForm.setCodAtto(currentAtto.getCODI_K_ID_ATTO().longValue());
				AttoForm currentForm = calcolaImportoQuote(idUffOwner, idProcedimento, beneForm);
				currentForm.setCodAtto(currentAtto.getCODI_K_ID_ATTO().longValue());
				if (currentAtto.getNUME_ANNO_ATTO() == null) {
					currentForm.setNumeroAtto(Constants.FormattazioneAnnoNumeroAtto.NUMERO_ATTO_NON_DISP);
				} else {
					currentForm.setNumeAtto(currentAtto.getNUME_ANNO_ATTO() + Constants.FormattazioneAnnoNumeroAtto.DIVISORE_ANNO_NUMERO_ATTO + currentAtto.getNUME_ATTO());
				}
				currentForm.setCategoriaAtto(new ComboForm(null, currentAtto.getDESC_CAT_ATTO()));
				currentForm.setDataDeposito(currentAtto.getDATA_DEPOSITO());
				currentForm.setDataDefinitivita(currentAtto.getDATA_DEFINITIVITA());

				results.add(currentForm);
			}
		}
		return results;
	}

	/**
	 * Verifica visualizzazione atto nel caso di definitivita` (Modello D)
	 * @param isDecisorio
	 * @param idAtto
	 * @param idUffOwner
	 * @param idProcedimento
	 * @param idBene
	 * @param verificaDefinitivita <true> = esegue verifica
	 * @return
	 */
	private boolean daVisualizzare(boolean isDecisorio, long idAtto, int idUffOwner, long idProcedimento, Long idBene, boolean verificaDefinitivita) {
		return (verificaDefinitivita ?
					//(isBeneDecretoConf(idProcedimento, idAtto, idBene, idUffOwner) && isDecretoInviabile(isDecisorio, idProcedimento, idAtto, idBene, idUffOwner, Constants.ESITO_CONFISCA)) :
					isBeneDecretoConf(idProcedimento, idAtto, idBene, idUffOwner) :
						true);
	}

	private boolean hasInvii(int idUffOwner, long idProcedimento, Long idAtto, BeneForm beneForm) {
		return hasInvii(idUffOwner, idProcedimento, idAtto, beneForm, "Tutti");
	}

	private boolean hasInvii(int idUffOwner, long idProcedimento, Long idAtto, BeneForm beneForm, String seAnnull) {
		List<DettaglioAttoFugDto> elenco = fugDao.listaInvioFug(idUffOwner, idProcedimento, beneForm.getIdBene(), idAtto.longValue(), null,
																														beneForm.getModelloFugSelected().getId(), seAnnull, null, null);
		return hasInviiValidi(elenco);
	}

	private boolean hasInviiValidi(List<DettaglioAttoFugDto> elenco) {
		List<DettaglioAttoFugDto> elencoInviiValidi = new ArrayList<DettaglioAttoFugDto>();
		for (DettaglioAttoFugDto el : elenco) {
			Character flagEsitoAnnullamento = Utility.getValid(el.getFLG_ESITO_ANNULL());
			if ((Character.valueOf('0').compareTo(flagEsitoAnnullamento) == 0) || (Character.valueOf('2').compareTo(flagEsitoAnnullamento) == 0)){
				elencoInviiValidi.add(el);
			} else {
				if (Character.valueOf('1').compareTo(flagEsitoAnnullamento) != 0) {
					Character statoInvio = Utility.getValid(el.getFLG_STATO_INVIO());
					if ((Character.valueOf('A').compareTo(statoInvio) == 0) || (Character.valueOf('I').compareTo(statoInvio) == 0)) {
						elencoInviiValidi.add(el);
					}
				}
			}
		}
		return (elencoInviiValidi.size() > 0);
	}

	/**
	 * Ciclo di verifica esistenza DECRETI DI SEQUESTRO:
	 * in caso positivo NON faccio vedere la CONFISCA (decisorio) ALTRIMENTI SI.
	 * @param daoLista
	 * @return
	 */
	private boolean hasSequestri(List<DettaglioAttoFugDto> daoLista) {
		boolean esistonoSequestri = false;
		for (DettaglioAttoFugDto currentAtto : daoLista) {
			if (!isDecisorio(currentAtto.getCODI_FK_ID_CAT_ATTO().toString())) {
				esistonoSequestri = true;
				break;
			}
		}
		return esistonoSequestri;
	}

	private List<AttoForm> listaQuoteModelliC(int idUffOwner, long idProcedimento, BeneForm beneForm, String codiceUtente, String nomeMetodo) {
		
		List<DettaglioAttoFugDto> daoLista = fugDao.quoteModelliC(idUffOwner, idProcedimento, beneForm.getIdBene(), beneForm.getModelloFugSelected().getId());
		List<AttoForm> results = new ArrayList<AttoForm>();

		boolean esistonoSequestri = hasSequestri(daoLista);

		for (DettaglioAttoFugDto currentAtto : daoLista) {
			boolean daVisualizzare = true;
			boolean isDecisorio = isDecisorio(currentAtto.getCODI_FK_ID_CAT_ATTO().toString());
			if (esistonoSequestri && isDecisorio) {
				daVisualizzare = false;
			}
			if (daVisualizzare) {
				// Il modello in questione non deve essere gia` stato inviato
				daVisualizzare = !hasInvii(idUffOwner, idProcedimento, currentAtto.getCODI_K_ID_ATTO().longValue(), beneForm);
				
	     //Se CdA devo leggere anche con l'id del Procedimento d'origine (se c'è) per controllare che il modello in questione non sia stato già inviato dal Tribunale 
	        	//ProfiloForm profilo = (ProfiloForm) request.getSession().getAttribute("profilo");
	    		//if (profilo.getIdTipoUfficio() == new Long(Constants.TIPO_UFFICIO_CORTE_D_APPELLO)) {
	     // DA IMPLEMENTARE ///
	    		//}
			}
			// Visualizzo l'atto
			if (daVisualizzare &&
					(!isDecisorio || 
					isBeneDecretoRev(idProcedimento, currentAtto.getCODI_K_ID_ATTO().longValue(), beneForm.getIdBene(), idUffOwner))) {
//					&& 
//					isDecretoInviabile(isDecisorio, idProcedimento, currentAtto.getCODI_K_ID_ATTO().longValue(), beneForm.getIdBene(), idUffOwner, Constants.ESITO_RIGETTA_CONFISCA)) {
				beneForm.setCodAtto(currentAtto.getCODI_K_ID_ATTO().longValue());
				AttoForm currentForm = new AttoForm();
				currentForm.setCodAtto(currentAtto.getCODI_K_ID_ATTO().longValue());
				if (currentAtto.getNUME_ANNO_ATTO() == null) {
					currentForm.setNumeroAtto(Constants.FormattazioneAnnoNumeroAtto.NUMERO_ATTO_NON_DISP);
				} else {
					currentForm.setNumeAtto(currentAtto.getNUME_ANNO_ATTO() + Constants.FormattazioneAnnoNumeroAtto.DIVISORE_ANNO_NUMERO_ATTO + currentAtto.getNUME_ATTO());
				}
				currentForm.setCategoriaAtto(new ComboForm(null, currentAtto.getDESC_CAT_ATTO()));
				currentForm.setDataDeposito(currentAtto.getDATA_DEPOSITO());
				currentForm.setDataDefinitivita(currentAtto.getDATA_DEFINITIVITA());

				currentForm.setSoggetti(getListaSoggettiBeneficiari(idUffOwner, idProcedimento, beneForm, currentAtto.getCODI_K_ID_ATTO().longValue(), null, null));

				results.add(currentForm);
			}
		}
		return results;
	}
	
	public AcquisisciModelloA riempiModelloA(BeneForm form, long idProcedimento, int idUffOwner, String importoBeneTotale) throws Exception {

		ModelloA modelloA = new ModelloA();
		AcquisisciModelloA acquisisciModelloA = null;
		
		try {
			modelloA.setCodUffMittente(form.getUffMittente());
			modelloA.setCodUffEmittente(form.getUffMittente());
			modelloA.setCancelliere(form.getCancelliere());
			modelloA.setCodiceOperatore(getCodiceOperatore(form));
			modelloA.setTipoRisorsa(getTipoRisorsa(form));
			modelloA.setNumeroRisorsa(getNumeroRisorsa(form));
			modelloA.setAbi(form.getCodiceAbi());	
			modelloA.setCab(form.getCodiceCab().getId());	
			modelloA.setTipoRegistro(Constants.FUG_TIPO_REGISTRO_RPRO3);
			TProcedimento procedimento = procedimentoService.getProcedimentoById(idProcedimento);
			modelloA.setAnnoRegistro(procedimento.getNumeAnnoProc() + ""); 
			modelloA.setNumeroRegistro(procedimento.getNumeProc() + ""); 
			modelloA.setDataProvvedimentoSequestro(form.getDataDeposito());

			String importoDaInviare = Utility.getImporto(form);
			modelloA.setFlagSequestro(Constants.FUG_FLAG_PREVENTIVO);
			modelloA.setImporto(importoDaInviare);
			modelloA.setDivisa(getDivisa(form));

			acquisisciModelloA = modelloA.creaModello();
		
		} catch (Exception e) {
			throw e; 
		}	
		
		return acquisisciModelloA;
	}
	
	public AcquisisciModelloD riempiModelloD(BeneForm form, long idProcedimento, int idUffOwner, String importoBeneTotale) throws Exception{

		ModelloD modelloD = new ModelloD();
		AcquisisciModelloD acquisisciModelloD = null;
		
		try {
			modelloD.setCodUffMittente(form.getUffMittente());
			modelloD.setCodUffEmittente(form.getUffMittente());
			modelloD.setCancelliere(form.getCancelliere());
			modelloD.setCodiceOperatore(getCodiceOperatore(form));
			modelloD.setTipoRisorsa(getTipoRisorsa(form));
			modelloD.setNumeroRisorsa(getNumeroRisorsa(form));
			modelloD.setAbi(form.getCodiceAbi());	
			modelloD.setCab(form.getCodiceCab().getId());	
	
			modelloD.setFlagSentDecr("2");
			
			String annoProvvedimento = "";
			String numeroProvvedimento = "";
			StringTokenizer st = new StringTokenizer(form.getNumeAtto(), "/");
			while (st.hasMoreTokens()) {
				annoProvvedimento = st.nextToken();
				numeroProvvedimento = st.nextToken();
			}
			modelloD.setAnnoProvvedimento(annoProvvedimento.trim());
			modelloD.setNumeroProvvedimento(numeroProvvedimento.trim());
			
			modelloD.setAnnoDecreto(annoProvvedimento.trim());
			modelloD.setNumeroDecreto(numeroProvvedimento.trim());
			
			TProcedimento procedimento = procedimentoService.getProcedimentoById(idProcedimento);
			modelloD.setAnnoRegistro(procedimento.getNumeAnnoProc() + "");
			modelloD.setNumeroRegistro(procedimento.getNumeProc() + "");
		
			modelloD.setDataDecreto(form.getDataDeposito());
			modelloD.setDataEsecutivitaProvvedimentoConfisca(form.getDataDefinitivita());
	
			String importoDaInviare = Utility.getImporto(form);
			modelloD.setFlagParziale(Utility.importoTotaleParziale(importoDaInviare, importoBeneTotale));
			modelloD.setImporto(importoDaInviare);
			modelloD.setDivisa(getDivisa(form));
				
			acquisisciModelloD = modelloD.creaModello();
				
		} catch (Exception e) {
			throw e; 
		}	
		
		return acquisisciModelloD;
	}

	private String add2String(String str, String add) {
		return add2String(str, add, " ");
	}

	private String add2String(String str, String add, String sep) {
		String out = StringUtils.defaultString(str).trim();
		if (StringUtils.isNotBlank(out)) {
			if (StringUtils.isNotBlank(add)) {out += sep + add;}
		} else {out = StringUtils.defaultString(add);}
		return out.trim();	
	}

	public AcquisisciModelloC riempiModelloC(BeneForm form, long idProcedimento, int idUffOwner) throws Exception {

		ModelloC modelloC = new ModelloC();
		AcquisisciModelloC acquisisciModelloC = null;
		
		try {
			modelloC.setCodUffMittente(form.getUffMittente());
			modelloC.setCodUffEmittente(form.getUffMittente());
			modelloC.setCancelliere(form.getCancelliere());
			modelloC.setCodiceOperatore(getCodiceOperatore(form));
			modelloC.setTipoRisorsa(getTipoRisorsa(form));
			modelloC.setNumeroRisorsa(getNumeroRisorsa(form));
			modelloC.setAbi(form.getCodiceAbi());	
			modelloC.setCab(form.getCodiceCab().getId());
	
			TProcedimento procedimento = procedimentoService.getProcedimentoById(idProcedimento);
			modelloC.setAnnoRegistro(procedimento.getNumeAnnoProc() + "");
			modelloC.setNumeroRegistro(procedimento.getNumeProc() + "");

			modelloC.setImporto(Utility.getImporto(form));
			modelloC.setDivisa(getDivisa(form));
			
			AttoForm attoForm = attoService.getAttoById(form.getIdAtto(), idUffOwner);
			//modelloC.setDataProvvedimentoSequestro(attoForm.getDataRegistrazione());
			
			//recupero la data del decreto di convalida collegato
			getDataSequestro(idUffOwner, form.getIdAtto(), attoForm.getDataDeposito(), modelloC);
			getDataRestituzione(idUffOwner, form.getIdAtto(), attoForm.getDataDeposito(), modelloC);
//			modelloC.setDataProvvedimentoRestituzione(attoForm.getDataDeposito());
			
			List<SoggettoForm> listaSoggetti = fugDao.listaSoggettiBeneficiari(idUffOwner, idProcedimento, form.getIdBene(), form.getIdAtto(), form.getIdSoggetto());
//			List<SoggettoForm> listaSoggettiBeneficiari = new ArrayList<SoggettoForm>();
			List<BeneficiariTipo> beneficiari = new ArrayList<BeneficiariTipo>();
			double totImportoRestituito = 0;
			if (Utility.isValid(listaSoggetti)) {
				Iterator iterSogg = listaSoggetti.iterator();
				while (iterSogg.hasNext()) {
					Map<String, Object> row = (Map<String, Object>) iterSogg.next();
					TRelBeneAtto currentRel = (TRelBeneAtto) row.get("rel");
					TPersonaFisica currentPersonaF = (TPersonaFisica) row.get("pers_fisica");
					TPersonaGiur currentPersonaG = (TPersonaGiur) row.get("pers_giuridica");
					TAnStato currentStato = (TAnStato) row.get("stato");
					TAnComuni currentComune = (TAnComuni) row.get("comuni");
					TAnProvincia currentProvincia = (TAnProvincia) row.get("provincia");
					TRelProcSogint currentSogint = (TRelProcSogint) row.get("sogInt");
					SoggettoForm sForm = new SoggettoForm();
					
					BeneficiariTipo beneficiario = new BeneficiariTipo(); 
					
					boolean isPF = (currentPersonaF != null);
					String statoNascita = "";
					String recapitoDomicilio = "";
					char nonDisponibile = '_';
					char statoNascitaFiller = ' ';
					char statoRecapitoFiller = nonDisponibile;
					char provinciaRecapitoFiller = nonDisponibile;

					beneficiario.setTipoSoggetto((isPF ? TipoSoggettoTipo.PF : TipoSoggettoTipo.PNF));

					//Persona Fisica
					if (isPF) {
						mapperBean.map(currentPersonaF, sForm);
						beneficiario.setDataNascitaCostituzione(ModelloC.getData(sForm.getDataNascita()));
						beneficiario.setCF(sForm.getCodiceFiscale());
						beneficiario.setNome(sForm.getNome());
						beneficiario.setCognome(sForm.getCognome());
						beneficiario.setProvinciaNascita(sForm.getSiglaProvinciaNascita());
						String comuneNascita = (sForm.getComuneNascita() != null) ? sForm.getComuneNascita().getDescrizione() : sForm.getDescrizioneComuneEsteroNascita();
						beneficiario.setComuneNascita(StringUtils.defaultString(comuneNascita));
						if ((currentStato != null) && isPF) {
							mapperBean.map(currentStato, sForm);
							statoNascita = sForm.getDescSiglaStato();
						}
						if(currentSogint.getTTpTipoStradaByCodiFkIdTipostraDom() != null)
							recapitoDomicilio = add2String(add2String(currentSogint.getTTpTipoStradaByCodiFkIdTipostraDom().getDescTipoStrada(),currentSogint.getDescIndirDom()), currentSogint.getDescCivicoDom(), ", ");
						else
							recapitoDomicilio = add2String(currentSogint.getDescIndirDom(), currentSogint.getDescCivicoDom(), ", ");
						//recapitoDomicilio = (Utility.isValid(currentSogint.getDescIndirDom())) ? (currentSogint.getDescIndirDom() + (Utility.isValid(currentSogint.getDescCivicoDom()) ? (", " + currentSogint.getDescCivicoDom()) : "")) : "";
						String comuneRecapito = "";
						if (currentSogint!=null && (currentSogint.getTAnComuniByCodiFkIdComDom()!=null || currentSogint.getDescComEsteroDom()!=null)) {
	//						mapperBean.map(currentComune, sForm);
	//						comuneRecapito = sForm.getDescrizioneComuneDomicilioItalianoEstero();
							if(currentSogint.getTAnComuniByCodiFkIdComDom() != null)
								comuneRecapito = currentSogint.getTAnComuniByCodiFkIdComDom().getDescComune();
							else
								comuneRecapito = currentSogint.getDescComEsteroDom();
							
							beneficiario.setComuneRecapito(StringUtils.defaultString(comuneRecapito));
						}
						else
							return null;
						
						String provinciaRecapito = "";
						if (currentSogint!=null && currentSogint.getTAnComuniByCodiFkIdComDom()!=null)
						{
							provinciaRecapito = currentSogint.getTAnComuniByCodiFkIdComDom().getTAnProvincia().getDescSiglaProvincia().trim();
						}
						if (currentSogint!=null && currentSogint.getDescComEsteroDom()!=null)
							provinciaRecapito = "" + provinciaRecapitoFiller;
						beneficiario.setProvinciaRecapito(StringUtils.rightPad(StringUtils.defaultString(provinciaRecapito), 2, provinciaRecapitoFiller));
						if(provinciaRecapito == null || provinciaRecapito.length() == 0)
							return null;
						
						if (currentSogint!=null && currentSogint.getTAnStatoByCodiFkIdStatoDom()!=null){
//							beneficiario.setStatoRecapito(currentSogint.getTAnStatoByCodiFkIdStatoDom().getDescStato());
							beneficiario.setStatoRecapito(currentSogint.getTAnStatoByCodiFkIdStatoDom().getDescSiglaStato());
						}
						else
							return null;
					}
					//Persona Giuridica
					if (currentPersonaG != null) {
						mapperBean.map(currentPersonaG, sForm);
						beneficiario.setDataNascitaCostituzione(ModelloC.getData(null));
						statoNascitaFiller = nonDisponibile;
						beneficiario.setPIVA(sForm.getPartitaIva());
						beneficiario.setDenominazione(sForm.getRagioneSociale());
//						recapitoDomicilio = add2String(add2String(currentPersonaG.getDescIndirizzoSede(), currentPersonaG.getDescCivico()), currentPersonaG.getDescCap());
						if(currentSogint!=null && currentSogint.getDescIndirDomel()!=null && currentSogint.getDescCivicoDomel()!=null)
						{
							if(currentSogint.getTTpTipoStradaByCodiFkIdTipostraDomel() != null)
								recapitoDomicilio = add2String(add2String(currentSogint.getTTpTipoStradaByCodiFkIdTipostraDomel().getDescTipoStrada(),currentSogint.getDescIndirDomel()), currentSogint.getDescCivicoDomel());
							else
								recapitoDomicilio = add2String(currentSogint.getDescIndirDomel(), currentSogint.getDescCivicoDomel());
						}
						String comuneRecapito = "";
						if (currentSogint!=null && (currentSogint.getTAnComuniByCodiFkIdComDomel()!=null || currentSogint.getDescComEsteroDomel()!=null)) {
							if(currentSogint.getTAnComuniByCodiFkIdComDomel() != null)
								comuneRecapito = currentSogint.getTAnComuniByCodiFkIdComDomel().getDescComune();
							else
								comuneRecapito = currentSogint.getDescComEsteroDomel();
							
							beneficiario.setComuneRecapito(StringUtils.defaultString(comuneRecapito));
						}
						else
							return null;
						
						String provinciaRecapito = "";
						if (currentSogint!=null && currentSogint.getTAnComuniByCodiFkIdComDomel()!=null)
						{
							provinciaRecapito = currentSogint.getTAnComuniByCodiFkIdComDomel().getTAnProvincia().getDescSiglaProvincia().trim();
						}
						if (currentSogint!=null && currentSogint.getDescComEsteroDomel()!=null)
							provinciaRecapito = "" + provinciaRecapitoFiller;
						beneficiario.setProvinciaRecapito(StringUtils.rightPad(StringUtils.defaultString(provinciaRecapito), 2, provinciaRecapitoFiller));
						if(provinciaRecapito == null || provinciaRecapito.length() == 0)
							return null;
						
						if (currentSogint!=null && currentSogint.getTAnStatoByCodiFkIdStatoDomel()!=null){
//							beneficiario.setStatoRecapito(currentSogint.getTAnStatoByCodiFkIdStatoDomel().getDescStato());
							beneficiario.setStatoRecapito(currentSogint.getTAnStatoByCodiFkIdStatoDomel().getDescSiglaStato());
						}
						else
							return null;
					}
					
					beneficiario.setStatoNascita(StringUtils.rightPad(StringUtils.defaultString(statoNascita), 3, statoNascitaFiller));
					beneficiario.setRecapitoDomicilio(StringUtils.defaultString(recapitoDomicilio));
					if(recapitoDomicilio == null || recapitoDomicilio.length() == 0)
						return null;
					
					double importoQuota = 0;
					double importoValuta = 0;
					if(form.getValutaEur() != null)
						importoValuta = form.getValutaEur().doubleValue();
					else if(form.getValutaEst() != null)
						importoValuta = form.getValutaEst().doubleValue();
					if (currentRel.getNumeQuota() != null) {
						sForm.setQuotaInteresse(currentRel.getNumeQuota().doubleValue());
						importoQuota = importoValuta * currentRel.getNumeQuota().doubleValue() / 100;
					}else
					if (currentRel.getNumeFrazioneNum() != null) {
							sForm.setNumeratoreFrazioneQuota(currentRel.getNumeFrazioneNum().intValue());
						if (currentRel.getNumeFrazioneDen() != null) {
							sForm.setDenominatoreFrazioneQuota(currentRel.getNumeFrazioneDen().intValue());	
						}
						importoQuota = importoValuta * currentRel.getNumeFrazioneNum().doubleValue() / currentRel.getNumeFrazioneDen().doubleValue();
					}
					double importoSogg = new BigDecimal(importoQuota).setScale(2 , BigDecimal.ROUND_UP).doubleValue();
	
					totImportoRestituito += importoSogg;
					beneficiario.setImporto(Utility.getImporto(importoSogg));
					if(form.getValutaEur() != null)
						beneficiario.setDivisa(Constants.EUR);
					else
						beneficiario.setDivisa(form.getTipoValuta().getId());
					
					beneficiari.add(beneficiario);
				}
			}
			
			modelloC.setBeneficiari(beneficiari);
			
			double importoConfrontoTP = Utility.getImporto(Utility.getImporto(form)).doubleValue();
			modelloC.setFlagParziale((importoConfrontoTP > totImportoRestituito) ? "P" : "T");
			
			acquisisciModelloC = modelloC.creaModello();
			form.setImporto(Utility.getImporto(totImportoRestituito));
		
		} catch (Exception e) {
			throw e; 
		}	
		
		return acquisisciModelloC;
	}

	private void getDataSequestro(int idUffOwner, Long idAtto, Date def, ModelloCBase modello) {
		List<DettaglioAttoFugDto> dtoLista = fugDao.getDataSequestro(idUffOwner, idAtto);
		if (Utility.isValid(dtoLista)) {
			DettaglioAttoFugDto dto = dtoLista.get(0);
			modello.setDataProvvedimentoSequestro(dto.getDATA_DEPOSITO());
		}
		if (modello.getDataProvvedimentoSequestro() == null) modello.setDataProvvedimentoSequestro(def);
	}

	private void getDataRestituzione(int idUffOwner, Long idAtto, Date def, ModelloCBase modello) {
		List<DettaglioAttoFugDto> dtoLista = fugDao.getDataRestituzione(idUffOwner, idAtto);
		if (Utility.isValid(dtoLista)) {
			DettaglioAttoFugDto dto = dtoLista.get(0);
			modello.setDataProvvedimentoRestituzione(dto.getDATA_DEPOSITO());
		}
		if (modello.getDataProvvedimentoRestituzione() == null) modello.setDataProvvedimentoRestituzione(def);
	}

	public AcquisisciModelloC2 riempiModelloC2(BeneForm form, long idProcedimento, int idUffOwner, String importoBeneTotale) throws Exception {
		ModelloC2 modelloC2 = new ModelloC2();
		AcquisisciModelloC2 acquisisciModelloC2 = null;
		
		try {
			// Recupero il modello C riferimento del presente C2 (C-Bis)
			long idInvioSelezinato = form.getIdInvio();
			TFugInvio daf = fugDao.getTFugInvioById(idInvioSelezinato);

			// Verifica esistenza del modello C corrispondente
			if (daf == null) {
				throw new EccezioneGenerica("error.modelloCCorrispondenteMancante");
			}
			// Verifica che il modello C corrispondente sia stato acquisito da Equitalia
			if (!"A".equals(getValue(daf.getFlgStatoInvio())) || (!Utility.isValid(daf.getNumeRisorsaEqui()) || !Utility.isValid(daf.getNumeProvvEqui())))
				throw new EccezioneGenerica("error.modelloCSelezionatoSenzaIdRicevutaEquitalia");

			form.setProtocolloRif(daf.getDescProtocollo());
			modelloC2.setTipoAcquisizione("1"); // verificare con Equitalia Giustizia il valore da impostare
			modelloC2.setIdProtocollo(daf.getDescProtocollo());
			modelloC2.setIdRisorsa(daf.getNumeRisorsaEqui());
			modelloC2.setIdProvvedimento(daf.getNumeProvvEqui());
//			modelloC2.setIdNotifica(daf.getDescNotifEqui());
			
			modelloC2.setCodUffMittente(form.getUffMittente());
			modelloC2.setCodUffEmittente(form.getUffMittente());
			modelloC2.setCancelliere(form.getCancelliere());
			modelloC2.setCodiceOperatore(getCodiceOperatore(form));
			modelloC2.setTipoRisorsa(getTipoRisorsa(form));
			modelloC2.setNumeroRisorsa(getNumeroRisorsa(form));
			modelloC2.setAbi(form.getCodiceAbi());
			modelloC2.setCab(form.getCodiceCab().getId());
	
			TProcedimento procedimento = procedimentoService.getProcedimentoById(idProcedimento);
			modelloC2.setAnnoRegistro(procedimento.getNumeAnnoProc() + "");
			modelloC2.setNumeroRegistro(procedimento.getNumeProc() + "");
		
			InfoProcessoTipo ipt = new InfoProcessoTipo();
			ipt.setAnnoRegistro(procedimento.getNumeAnnoProc() + "");
			ipt.setNumeroRegistro(procedimento.getNumeProc() + "");
			ipt.setCodUffEmittente(form.getUffMittente());
			ipt.setTipoRegistro(TipoRegistroTipo.RPRO_3);
			modelloC2.setInfoProcessoModelloC(ipt);

			modelloC2.setDataNotifica(form.getDataNotifica());
			modelloC2.setDataPassaggioGiudicatroProvvedimentoRestituzione(form.getDataIrrevocabileRevoca());
			
			AttoForm attoForm = attoService.getAttoById(form.getIdAtto(), idUffOwner);
//			modelloC2.setDataProvvedimentoSequestro(attoForm.getDataRegistrazione());
			
			//recupero la data del decreto di convalida collegato
			getDataSequestro(idUffOwner, form.getIdAtto(), attoForm.getDataDeposito(), modelloC2);
			getDataRestituzione(idUffOwner, form.getIdAtto(), attoForm.getDataDeposito(), modelloC2);
//			modelloC2.setDataProvvedimentoRestituzione(attoForm.getDataDeposito());
			
			modelloC2.setImporto(Utility.getImporto(form));
			modelloC2.setDivisa(getDivisa(form));
			
			List<SoggettoForm> listaSoggetti = fugDao.listaSoggettiBeneficiari(idUffOwner, idProcedimento, form.getIdBene(), form.getIdAtto(), form.getIdSoggetto());
			double importoDaInviare = 0d;
			if (listaSoggetti != null) {
				Iterator iterSogg = listaSoggetti.iterator();
				while (iterSogg.hasNext()) {
					Map<String, Object> row = (Map<String, Object>) iterSogg.next();
					TRelBeneAtto currentRel = (TRelBeneAtto) row.get("rel");
					TPersonaFisica currentPersonaF = (TPersonaFisica) row.get("pers_fisica");
					TPersonaGiur currentPersonaG = (TPersonaGiur) row.get("pers_giuridica");
					TAnStato currentStato = (TAnStato) row.get("stato");
					TAnComuni currentComune = (TAnComuni) row.get("comuni");
					TAnProvincia currentProvincia = (TAnProvincia) row.get("provincia");
					TRelProcSogint currentSogint = (TRelProcSogint) row.get("sogInt");
					SoggettoForm sForm = new SoggettoForm();
					
					SoggettoForm sFormInt = new SoggettoForm();
					if (currentSogint != null) {
						mapperBean.map(currentSogint, sFormInt);
					}
					
					boolean isPF = true;
					if (currentPersonaF != null) {
						isPF = true;
						mapperBean.map(currentPersonaF, sForm);
						modelloC2.setTipoSoggetto("PF");
						modelloC2.setCf(sForm.getCodiceFiscale());
						modelloC2.setNome(sForm.getNome());
						modelloC2.setCognome(sForm.getCognome());
						modelloC2.setDataNascitaCostituzione(sForm.getDataNascita());
					}
					if (currentPersonaG != null) {
						isPF = false;
						mapperBean.map(currentPersonaG, sForm);
						modelloC2.setTipoSoggetto("PNF");
						modelloC2.setPiva(sForm.getPartitaIva());
						modelloC2.setDenominazione(sForm.getRagioneSociale());
					}
					if (currentSogint != null) {
						//modelloC2.setRecapitoDomicilio(currentSogint.getDescIndirDom() +", "+ currentSogint.getDescCivicoDom());
					}
					if (currentStato != null) {
						mapperBean.map(currentStato, sForm);
						if(isPF){
							//modelloC2.setStatoNascita(sForm.getDescSiglaStato());
						}
					}
					if (currentComune != null) {
						mapperBean.map(currentComune, sForm);
						//modelloC2.setComuneRecapito(sForm.getDescrizioneComuneDomicilioItalianoEstero());
					}
					if (currentProvincia != null) {
						mapperBean.map(currentProvincia, sForm);
						//modelloC2.setProvinciaRecapito(sForm.getSiglaProvinciaDomicilio());
					}
					double importoQuota = 0;
					double importoValuta = 0;
					if(form.getValutaEur() != null)
						importoValuta = form.getValutaEur().doubleValue();
					else if(form.getValutaEst() != null)
						importoValuta = form.getValutaEst().doubleValue();
					if (currentRel.getNumeQuota() != null) {
						sForm.setQuotaInteresse(currentRel.getNumeQuota().doubleValue());
						importoQuota = importoValuta * currentRel.getNumeQuota().doubleValue() / 100;
					}else
					if (currentRel.getNumeFrazioneNum() != null) {
							sForm.setNumeratoreFrazioneQuota(currentRel.getNumeFrazioneNum().intValue());
						if (currentRel.getNumeFrazioneDen() != null) {
							sForm.setDenominatoreFrazioneQuota(currentRel.getNumeFrazioneDen().intValue());	
						}
						importoQuota = importoValuta * currentRel.getNumeFrazioneNum().doubleValue() / currentRel.getNumeFrazioneDen().doubleValue();
					}
					importoDaInviare += new BigDecimal(importoQuota).setScale(2 , BigDecimal.ROUND_UP).doubleValue();
					modelloC2.setIdNotifica(estraiSoggetto(idInvioSelezinato, sForm)); //Rileva il codice sequanziale del soggetto rispetto al modello C corrispondente
				}
				modelloC2.setImporto(Utility.getImporto(importoDaInviare));
				modelloC2.setFlagParziale(Utility.importoTotaleParziale(Utility.getImporto(importoDaInviare), importoBeneTotale));
			}
			
			acquisisciModelloC2 = modelloC2.creaModello();
			form.setImporto(Utility.getImporto(importoDaInviare));

		} catch (EccezioneGenerica e) {
			throw e; 
		} catch (Exception e) {
			throw e; 
		}	
		return acquisisciModelloC2;
	}
	
	public AcquisisciModelloH riempiModelloH(BeneForm form, long idProcedimento, int idUffOwner, String importoBeneTotale) throws Exception {

		ModelloH modelloH = new ModelloH();
		AcquisisciModelloH acquisisciModelloH = null;
		
		try {
			modelloH.setCodUffMittente(form.getUffMittente());
			modelloH.setCodUffEmittente(form.getUffMittente());
			modelloH.setCancelliere(form.getCancelliere());
			modelloH.setCodiceOperatore(getCodiceOperatore(form));
			modelloH.setTipoRisorsa(getTipoRisorsa(form));
			modelloH.setNumeroRisorsa(getNumeroRisorsa(form));
			modelloH.setAbi(form.getCodiceAbi());	
			modelloH.setCab(form.getCodiceCab().getId());	
			modelloH.setTipoRegistro(Constants.FUG_TIPO_REGISTRO_RPRO3);
			TProcedimento procedimento = procedimentoService.getProcedimentoById(idProcedimento);
			modelloH.setAnnoRegistro(procedimento.getNumeAnnoProc() + ""); 
			modelloH.setNumeroRegistro(procedimento.getNumeProc() + ""); 
			modelloH.setDataVendita(form.getDataTrasferimento());
			modelloH.setDataVersamento(form.getDataTrasferimento());
			modelloH.setBollettino(form.getNumBollettino());

			// definizione tipo pagamento su invio al FUG (PLO 201711A-10)
			String idTipoVersamento = form.getFlgVersamento().getId();
			modelloH.setTipoPagamento(FugValidation.getTagTipoBollettino(idTipoVersamento));

			String importoDaInviare = Utility.getImporto(form);
			modelloH.setFlagConfiscaSequestro(Constants.FLAG_SEQUESTRO);
			modelloH.setFlagParziale(Utility.importoTotaleParziale(importoDaInviare, importoBeneTotale));
			modelloH.setImporto(importoDaInviare);
			modelloH.setDivisa(getDivisa(form));

			acquisisciModelloH = modelloH.creaModello();
		
		} catch (Exception e) {
			throw e; 
		}	
		
		return acquisisciModelloH;
	}
	
	public RispostaModello inviaModello(AcquisisciModelloA in, long idInvio, String descUtente) {
		RispostaModello rispostaModello = null;
				
		String mockResponse = configParameterSource.getMessage("MOCK_RESPONSE_SERVIZIO_FUG", null, null);
		if(mockResponse != null && mockResponse.equals("S")) {
			//Implementata MockResponseFUGUtil getMockModelloAResponse
			rispostaModello = mockResponseFUGUtil.getMockModelloAResponse(in, idInvio);
		}
		else {
			rispostaModello = fugWsService.invioModello(in, idInvio);
		}
		
		rispostaModello = aggiornaProtocollo(rispostaModello, idInvio, descUtente);
		 
		return rispostaModello;
	}
	
	public RispostaModello inviaModello(AcquisisciModelloD in, long idInvio, String descUtente) {
		RispostaModello rispostaModello = null;
		String mockResponse = configParameterSource.getMessage("MOCK_RESPONSE_SERVIZIO_FUG", null, null);
		if(mockResponse != null && mockResponse.equals("S")) {
			//Implementata MockResponseFUGUtil getMockModelloDResponse
			rispostaModello = mockResponseFUGUtil.getMockModelloDResponse(in, idInvio);
		}
		else {
			rispostaModello = fugWsService.invioModello(in, idInvio);
		}
		
		rispostaModello = aggiornaProtocollo(rispostaModello, idInvio, descUtente);
		 
		return rispostaModello;
	}
	
	public RispostaModello inviaModello(AcquisisciModelloC in, long idInvio, String descUtente) {
		RispostaModello rispostaModello = null;
		String mockResponse = configParameterSource.getMessage("MOCK_RESPONSE_SERVIZIO_FUG", null, null);
		if(mockResponse != null && mockResponse.equals("S")) {
			//Implementata MockResponseFUGUtil getMockModelloCResponse
			rispostaModello = mockResponseFUGUtil.getMockModelloCResponse(in, idInvio);
		}
		else {
			rispostaModello = fugWsService.invioModello(in, idInvio);
		}
		
		rispostaModello = aggiornaProtocollo(rispostaModello, idInvio, descUtente);
		 
		return rispostaModello;
	}
	
	public RispostaModello inviaModello(AcquisisciModelloC2 in, long idInvio, String descUtente) {
		RispostaModello rispostaModello = null;
		String mockResponse = configParameterSource.getMessage("MOCK_RESPONSE_SERVIZIO_FUG", null, null);
		if(mockResponse != null && mockResponse.equals("S")) {
			//Implementata MockResponseFUGUtil getMockModelloC2Response
			rispostaModello = mockResponseFUGUtil.getMockModelloC2Response(in, idInvio);
		}
		else {
			rispostaModello = fugWsService.invioModello(in, idInvio);
		}
		
		rispostaModello = aggiornaProtocollo(rispostaModello, idInvio, descUtente);
		 
		return rispostaModello;
	}
	
	public RispostaModello inviaModello(AcquisisciModelloH in, long idInvio, String descUtente) {
		RispostaModello rispostaModello = null;
		String mockResponse = configParameterSource.getMessage("MOCK_RESPONSE_SERVIZIO_FUG", null, null);
		if(mockResponse != null && mockResponse.equals("S")) {
			//Implementata MockResponseFUGUtil getMockModelloHResponse
			rispostaModello = mockResponseFUGUtil.getMockModelloHResponse(in, idInvio);
		}
		else {
			rispostaModello = fugWsService.invioModello(in, idInvio);
		}
		
		rispostaModello = aggiornaProtocollo(rispostaModello, idInvio, descUtente);
		 
		return rispostaModello;
	}
	
	public RispostaModello annullaModello(RichiestaAnnullamentoModello in, long idInvio, Long idInvioSelezionato, String descUtente) {
		RispostaModello rispostaModello = null;
		String mockResponse = configParameterSource.getMessage("MOCK_RESPONSE_SERVIZIO_FUG", null, null);
		if(mockResponse != null && mockResponse.equals("S")) {
			//Implementata MockResponseFUGUtil getMockAnnullamentoModelloResponse
			rispostaModello = mockResponseFUGUtil.getMockAnnullamentoModelloResponse(in, idInvio);
		}
		else {
			rispostaModello = fugWsService.annullaModello(in, idInvio); 
		}
		rispostaModello =aggiornaProtocollo(rispostaModello, idInvio, descUtente, idInvioSelezionato);
		return rispostaModello;
	}
	
	private RispostaModello aggiornaProtocollo(RispostaModello rispostaModello, long idInvio, String descUtente) {
		return aggiornaProtocollo(rispostaModello, idInvio, descUtente, null);
	}

	private RispostaModello aggiornaProtocollo(RispostaModello rispostaModello, long idInvio, String descUtente, Long idInvioSelezionato) {
		String esito = Utility.isValid(rispostaModello.getEsito()) ? Constants.FUG_STATO_INVIATO_CON_ERRORI : Constants.FUG_STATO_INVIATO;

		FugForm fugForm = new FugForm();
	
		fugForm.setIdInvio(idInvio);
		if(idInvioSelezionato != null){
			fugForm.setFlgEsitoAnnull(Constants.FUG_RICHIESTO_ANNULLAMENTO);		
		}else{
			fugForm.setFlgEsitoAnnull(null);
		}
		if (!rispostaModello.getIdProtocollo().equals(RispostaModello.NO_ID_PROTOCOLLO)) {
			fugForm.setDescProtocollo(rispostaModello.getIdProtocollo());
		}
		fugForm.setStatoInvio(esito);
		
		fugDao.updateFugInvio(fugForm);
	
		// Se sto inviando una richiesta di annullamento di una richiesta di
		// acquisizione e la richiesta non e` andata in errore
		// aggiorno anche il record che viene annullato impostando flag_annull = 1
		if((idInvioSelezionato != null) && (!esito.equals(Constants.FUG_STATO_INVIATO_CON_ERRORI))){
			fugForm = new FugForm();
			fugForm.setIdInvio(idInvioSelezionato);
			fugForm.setFlgEsitoAnnull(Constants.FUG_RICHIESTO_ANNULLAMENTO);
			fugDao.updateFugInvio(fugForm);	
		}
		return rispostaModello;
	}
	
	public void insertFugErrori(RispostaModello rispostaModello, long idInvio, String descUtente){
		if (rispostaModello != null) {
			int progressivoErrore = 0;
			if(rispostaModello.getEsito() == null)
			{
				EsitoFUG errore = new EsitoFUG();
				fugDao.insertFugErrori(idInvio, ++progressivoErrore, errore, descUtente);
			}
			else
			{
				for (EsitoFUG errore : rispostaModello.getEsito()) {
					fugDao.insertFugErrori(idInvio, ++progressivoErrore, errore, descUtente);
				}
			}
//			for(int i=0;i<rispostaModello.getEsito().size();i++) {
//				EsitoFUG errore = (EsitoFUG) rispostaModello.getEsito().get(i);	
//				progressivoErrore = progressivoErrore +1;
//				fugDao.insertFugErrori(idInvio, progressivoErrore, errore, descUtente);
//			}
		}
	}
	
	public List<FugForm> elencoInvioFug(int idUffOwner, FugForm fugForm, String codiceUtente, String nomeMetodo) {

		List<DettaglioAttoFugDto> invii = fugDao.listaInvioFug(idUffOwner, fugForm.getIdProcedimento(), null, null, null, 
				fugForm.getModelloFugSelected() != null ? fugForm.getModelloFugSelected().getId() : null, "Tutti",
				fugForm.getDataInvioDa(), fugForm.getDataInvioA());

		List<FugForm> results = new ArrayList<FugForm>();
		Iterator<DettaglioAttoFugDto> iter = null;
		iter = invii.iterator();
		FugForm currentForm = null;
		while (iter.hasNext()) {
			currentForm = new FugForm();
			DettaglioAttoFugDto currentAttoFug = (DettaglioAttoFugDto) iter.next();
			currentForm.setIdInvio(currentAttoFug.getCODI_K_ID_INVIO().longValue());
			currentForm.setIdBene(currentAttoFug.getCODI_FK_ID_BENE().longValue());
			currentForm.setDescModello(String.valueOf(currentAttoFug.getDESC_MODELLO()));
			currentForm.setDescModello(Constants.getModello(currentForm.getDescModello().trim()));
			currentForm.setDescProtocollo(currentAttoFug.getDESC_PROTOCOLLO());
			currentForm.setDescBene(currentAttoFug.getDESCR_BENE());
			currentForm.setDataInvio(currentAttoFug.getDATA_INVIO());
			currentForm.setDataDefinitaPosizione(currentAttoFug.getDATA_DEF_POS());
			if (currentAttoFug.getNUME_ANNO_ATTO() == null) {
				currentForm.setNumeAtto(Constants.FormattazioneAnnoNumeroAtto.NUMERO_ATTO_NON_DISP);
			} else {
				currentForm.setNumeAtto(currentAttoFug.getNUME_ANNO_ATTO() + Constants.FormattazioneAnnoNumeroAtto.DIVISORE_ANNO_NUMERO_ATTO + currentAttoFug.getNUME_ATTO());
			}

			currentForm.setStatoInvio(Constants.getStatoInvio(currentAttoFug.getFLG_STATO_INVIO()));
			currentForm.setStatoInvio((Utility.isValid(currentAttoFug.getFLG_ESITO_ANNULL())) ? Constants.getStatoRichiesta(currentAttoFug.getFLG_ESITO_ANNULL()) : currentForm.getStatoInvio());

//			if (currentAttoFug.getDATA_DEF_POS() != null) {
//				currentForm.setStatoInvio(Constants.FUG_DEFINITA_POSIZIONE_STR);
//			}
			currentForm.setDescProtocolloRif(currentAttoFug.getDESC_PROTOCOLLO_RIF());
			currentForm.setFlgDefinitaPosizione(currentAttoFug.getFLG_DEF_POS());
			currentForm.setNumeRisorsaEqui(currentAttoFug.getNUME_RISORSA_EQUI());
			currentForm.setNumeProvvEqui(currentAttoFug.getNUME_PROVV_EQUI());
			currentForm.setDescNotifEqui(currentAttoFug.getDESC_NOTIF_EQUI());
			currentForm.setDescNoteEqui(currentAttoFug.getDESC_NOTE_EQUI());
			if (currentAttoFug.getIMPO_EURO() != null) {
				currentForm.setImportoEuro(Utility.formattaImporto(currentAttoFug.getIMPO_EURO()));
			}
			results.add(currentForm);
		}

		return results;
	}
	
	public List<FugForm> elencoModelliC(int idUffOwner, FugForm fugForm, String codiceUtente, String nomeMetodo) {

		List<DettaglioAttoFugDto> invii = fugDao.listaInvioFug(idUffOwner, fugForm.getIdProcedimento(), null, null, null, Constants.FUG_MODELLO_C, "N", fugForm.getDataInvioDa(), fugForm.getDataInvioA());

		List<FugForm> results = new ArrayList<FugForm>();
		for (DettaglioAttoFugDto currentAttoFug : invii) {
			FugForm currentForm = new FugForm();
			currentForm.setIdInvio(currentAttoFug.getCODI_K_ID_INVIO().longValue());
			currentForm.setIdBene(currentAttoFug.getCODI_FK_ID_BENE().longValue());
			currentForm.setDescModello(String.valueOf(currentAttoFug.getDESC_MODELLO()));
			currentForm.setDescProtocollo(currentAttoFug.getDESC_PROTOCOLLO());
			currentForm.setDescBene(currentAttoFug.getDESCR_BENE());
			currentForm.setDataInvio(currentAttoFug.getDATA_INVIO());
			if (currentAttoFug.getNUME_ANNO_ATTO() == null) {
				currentForm.setNumeAtto(Constants.FormattazioneAnnoNumeroAtto.NUMERO_ATTO_NON_DISP);
			} else {
				currentForm.setNumeAtto(currentAttoFug.getNUME_ANNO_ATTO() + Constants.FormattazioneAnnoNumeroAtto.DIVISORE_ANNO_NUMERO_ATTO + currentAttoFug.getNUME_ATTO());
			}
			AttoForm attoForm = attoService.getAttoById(currentAttoFug.getCODI_K_ID_ATTO().longValue(), idUffOwner);
			TTpCatAtto catAtto = new TTpCatAtto();
			catAtto = attoService.getCatAttoById(Integer.parseInt(attoForm.getCategoriaAtto().getId()));
			currentForm.setCategoriaAtto(new ComboForm(null, catAtto.getDescCatAtto()));

			currentForm.setStatoInvio(getValue(currentAttoFug.getFLG_STATO_INVIO()));
			currentForm.setNumeProvvEqui(currentAttoFug.getNUME_PROVV_EQUI());
			currentForm.setNumeRisorsaEqui(currentAttoFug.getNUME_RISORSA_EQUI());
			currentForm.setDescNotifEqui(currentAttoFug.getDESC_NOTIF_EQUI());
			currentForm.setDescNoteEqui(currentAttoFug.getDESC_NOTE_EQUI());

			results.add(currentForm);
		}

		return results;
	}

	public TRelBeneBeneDto datiModelloH(int idUffOwner, long idProcedimento, BeneForm beneForm, String codiceUtente, String nomeMetodo) {
		List<TRelBeneBeneDto> daoLista = fugDao.datiModelliH(idUffOwner, idProcedimento, beneForm.getIdBene(), beneForm.getModelloFugSelected().getId());
		return Utility.isValid(daoLista) ? daoLista.get(0) : new TRelBeneBeneDto();
	}

	private String getValue(Character in) {
		return ((in != null) ? String.valueOf(in.charValue()) : null);
	}

	public RichiestaAnnullamentoModello richiestaAnnullamentoModello(int idUffOwner, TFugInvio tFugInvio)  throws Exception {
		
		RichiestaAnnullamentoModello richiestaAnnullamento = null;
		
		try {
			AnnullaModello richiesta = new AnnullaModello();
			TUtente utente = (TUtente) (request.getSession().getAttribute(Constants.ID_UTENTE));
			TAnUfficio ufficioCorrente = commonDao.getUfficioById((int)idUffOwner);
	
			richiesta.setCancelliere(utente.getCodiceFiscale());
			richiesta.setCodUffMittente(ufficioCorrente.getDescUffGiudiz());
			richiesta.setIdProtocollo(tFugInvio.getDescProtocollo());
			richiesta.setIdRisorsa(tFugInvio.getNumeRisorsaEqui());
			richiesta.setIdProvvedimento(tFugInvio.getNumeProvvEqui());
			richiesta.setIdNotifica(tFugInvio.getDescNotifEqui());
			
			richiestaAnnullamento = richiesta.creaRichiesta();
			
		} catch (Exception e) {
			throw e; 
		}	
		return richiestaAnnullamento;
	}
	
	public List<SoggettoForm> getListaSoggettiBeneficiari(int idUffOwner, long idProcedimento, BeneForm beneForm, Long idAtto, Long idSoggetto, String provenienza){
		List<SoggettoForm> listaSoggettiBeneficiari = new ArrayList<SoggettoForm>();
		List<SoggettoForm> listaSoggetti = fugDao.listaSoggettiBeneficiari(idUffOwner, idProcedimento, beneForm.getIdBene(), idAtto, idSoggetto);
		if (Utility.isValid(listaSoggetti)) {
			Iterator iterSogg = listaSoggetti.iterator();
			while (iterSogg.hasNext()) {
				SoggettoForm sForm = new SoggettoForm();
				Map<String, Object> row = (Map<String, Object>) iterSogg.next();
				TRelBeneAtto currentRel = (TRelBeneAtto) row.get("rel");
				TPersonaGiur currentPersonaG = (TPersonaGiur) row.get("pers_giuridica");
				if (currentPersonaG != null) {
					mapperBean.map(currentPersonaG, sForm);
				}
				TPersonaFisica currentPersonaF = (TPersonaFisica) row.get("pers_fisica");
				if (currentPersonaF != null) {
					mapperBean.map(currentPersonaF, sForm);
				}
				if (currentRel != null) {
					double importoQuota = 0;
					double importoValuta = 0;
					if(beneForm.getValutaEur() != null)
						importoValuta = beneForm.getValutaEur().doubleValue();
					else if(beneForm.getValutaEst() != null)
						importoValuta = beneForm.getValutaEst().doubleValue();
					if (currentRel.getNumeQuota() != null) {
						sForm.setQuotaInteresse(currentRel.getNumeQuota().doubleValue());
						importoQuota = importoValuta * currentRel.getNumeQuota().doubleValue() / 100;
					} else if (currentRel.getNumeFrazioneNum() != null) {
						sForm.setNumeratoreFrazioneQuota(currentRel.getNumeFrazioneNum().intValue());
						if (currentRel.getNumeFrazioneDen() != null) {
							sForm.setDenominatoreFrazioneQuota(currentRel.getNumeFrazioneDen().intValue());
						}
						importoQuota = beneForm.getValutaEur().doubleValue() * currentRel.getNumeFrazioneNum().doubleValue() / currentRel.getNumeFrazioneDen().doubleValue();
					}
					double importoFUG = new BigDecimal(importoQuota).setScale(2, BigDecimal.ROUND_UP).doubleValue();
					sForm.setImportoQuota(Utility.formatDecimalNumber(importoFUG));
				}
				// nel caso del modello CBis escludo dalla lista il soggetto se per esso
				// ho già inviato il modello
				boolean daVisualizzare = true;
				if (provenienza != null) {
					List<DettaglioAttoFugDto> invii = fugDao.listaInvioFug(idUffOwner, idProcedimento, beneForm.getIdBene(), idAtto, sForm.getIdSoggetto(), Constants.FUG_MODELLO_C_BIS, "N", null, null);
					daVisualizzare = !hasInviiValidi(invii);
				}
				if (daVisualizzare) {
					listaSoggettiBeneficiari.add(sForm);
				}
			}
		}
		return listaSoggettiBeneficiari;
	}
	
	private AttoForm calcolaImportoQuote(int idUffOwner, long idProcedimento, BeneForm beneForm) {
		
		List<TRelBeneAtto> relBeneAtto = beneDao.getTRelBeneAtto(idProcedimento, beneForm.getIdBene(), idUffOwner, beneForm.getCodAtto());

		AttoForm attoForm = attoService.getAttoById(beneForm.getCodAtto(), idUffOwner);
		double importo = 0;
		if (relBeneAtto.size() > 0) {
			Iterator<TRelBeneAtto> iterator = relBeneAtto.iterator();
			while (iterator.hasNext()) {
				TRelBeneAtto quota = (TRelBeneAtto) iterator.next();
				double importoQuota = 0;
				double importoValuta = 0;
				if(beneForm.getValutaEur() != null)
					importoValuta = beneForm.getValutaEur().doubleValue();
				else if(beneForm.getValutaEst() != null)
					importoValuta = beneForm.getValutaEst().doubleValue();
				if (quota.getNumeQuota() != null) {
					importoQuota = importoValuta * quota.getNumeQuota().doubleValue() / 100;
				} else if (quota.getNumeFrazioneNum() != null) {
					importoQuota = importoValuta * quota.getNumeFrazioneNum().doubleValue() / quota.getNumeFrazioneDen().doubleValue();
				}
				if (quota.getTTpTipoInteresse().getCodiKIdTipoInt() == 1) {
					// per i decisori sommo solo le quote con esito finale = 1 e la data di Definitivita` impostata
					if ((Constants.FUG_MODELLO_D).equals(beneForm.getModelloFugSelected().getId()) && (
							attoForm.getCategoriaAtto().getId().equals(Constants.DECRETO_DECISORIO_I_GRADO) ||
							attoForm.getCategoriaAtto().getId().equals(Constants.DECRETO_DECISORIO_II_GRADO))) {
						// leggo la data Definitivita` da T_DEF_PATR
						List<TDefPatr> defPatr = procedimentoDao.getTDefPatrById(idProcedimento, attoForm.getCodAtto(), Integer.valueOf(idUffOwner), beneForm.getIdBene(), 1, quota.getId().getCodiKIdSoggTit(), null);
						TDefPatr definitivita = (defPatr.size() > 0) ? defPatr.get(0) : null;
						if ((definitivita != null) && ((definitivita.getDataDefinitivita() != null) && (quota.getTTpEsitoFinale().getCodiKIdEsitoFin() == 1))) {
							importo += Double.parseDouble(String.valueOf(importoQuota));
						}
					} else {
						importo += Double.parseDouble(String.valueOf(importoQuota));
					}
				}
			}
		}
		double importoFUG = new BigDecimal(importo).setScale(2, BigDecimal.ROUND_UP).doubleValue();
		attoForm.setImporto(Utility.formatDecimalNumber(importoFUG));
		return attoForm;
	}

	private String getFlagVersamento(BeneForm in) {
		return (Utility.isValid(in) && Utility.isValid(in.getFlgVersamento())) ? in.getFlgVersamento().getId() : null;
	}

	private String getTipoRisorsa(BeneForm form) {
		String sottocategoriaBene = form.getSottocategoria().getId();
		String flagVersamento = getFlagVersamento(form);
		String tipoRisorsa = "";
		if(ComparatorUtil.containsValue(sottocategoriaBene, Constants.SottoCategoriaBeni.CONTANTE, Constants.SottoCategoriaBeni.DENARO_RICAVATO_DA_VENDITA)){
			if(ComparatorUtil.containsValue(flagVersamento, 1)){
				tipoRisorsa = Constants.TipoRisorseEquitalia.CONTO_CORRENTE;
			}
			if(ComparatorUtil.containsValue(flagVersamento, 3)){
				tipoRisorsa = Constants.TipoRisorseEquitalia.LIBRETTO;
			}
			if(ComparatorUtil.containsValue(flagVersamento, 98)){
				tipoRisorsa = Constants.TipoRisorseEquitalia.LIBRETTO_DEPOSITO_GIUDIZIARIO;
			}
		}
//		if(ComparatorUtil.containsValue(sottocategoriaBene, 36, 37)){tipoRisorsa = Constants.TipoRisorseEquitalia.LIBRETTO;}
//		if(ComparatorUtil.containsValue(sottocategoriaBene, 38, 39)){tipoRisorsa = Constants.TipoRisorseEquitalia.CONTO_CORRENTE;}
//		if(ComparatorUtil.containsValue(sottocategoriaBene, 86)){tipoRisorsa = Constants.TipoRisorseEquitalia.TITOLO_DI_STATO;}
//		if(ComparatorUtil.containsValue(sottocategoriaBene, 87)){tipoRisorsa = Constants.TipoRisorseEquitalia.CERTIFICATO_DI_DEPOSITO;}
//		if(ComparatorUtil.containsValue(sottocategoriaBene, 88)){tipoRisorsa = Constants.TipoRisorseEquitalia.OBBLIGAZIONE;}
//		if(ComparatorUtil.containsValue(sottocategoriaBene, 89)){tipoRisorsa = Constants.TipoRisorseEquitalia.AZIONI;}
//		if(ComparatorUtil.containsValue(sottocategoriaBene, 90)){tipoRisorsa = Constants.TipoRisorseEquitalia.STRUMENTI_FINANZIARI_PARTECIPATIVI;}
//		if(ComparatorUtil.containsValue(sottocategoriaBene, 91)){tipoRisorsa = Constants.TipoRisorseEquitalia.TITOLI_ATIPICI;}
//		if(ComparatorUtil.containsValue(sottocategoriaBene, 95)){tipoRisorsa = Constants.TipoRisorseEquitalia.CONTRATTO_LEASING;}
//		if(ComparatorUtil.containsValue(sottocategoriaBene, 96)){tipoRisorsa = Constants.TipoRisorseEquitalia.CREDITI_VARI;}
//		if(ComparatorUtil.containsValue(sottocategoriaBene, 97, 123)){tipoRisorsa = Constants.TipoRisorseEquitalia.POLIZZA_ASSICURATIVA_GESTITA_DA_BANCA;}
//		if(ComparatorUtil.containsValue(sottocategoriaBene, 181)){tipoRisorsa = Constants.TipoRisorseEquitalia.POLIZZA_ASSICURATIVA_VITA_CASO_MORTE;}
//		if(ComparatorUtil.containsValue(sottocategoriaBene, 182)){tipoRisorsa = Constants.TipoRisorseEquitalia.POLIZZA_ASSICURATIVA_VITA_CASO_VITA;}
//		if(ComparatorUtil.containsValue(sottocategoriaBene, 183)){tipoRisorsa = Constants.TipoRisorseEquitalia.POLIZZA_ASSICURATIVA_VITA_MVI;}
//		if(ComparatorUtil.containsValue(sottocategoriaBene, 184)){tipoRisorsa = Constants.TipoRisorseEquitalia.POLIZZA_ASSICURATIVA_VITA_C;}
//		if(ComparatorUtil.containsValue(sottocategoriaBene, 185)){tipoRisorsa = Constants.TipoRisorseEquitalia.POLIZZA_ASSICURATIVA_VITA_MISTA;}
//		if(ComparatorUtil.containsValue(sottocategoriaBene, 186)){tipoRisorsa = Constants.TipoRisorseEquitalia.CONTRATTO_DI_CAPITALIZZAZIONE;}
//		if(ComparatorUtil.containsValue(sottocategoriaBene, 187)){tipoRisorsa = Constants.TipoRisorseEquitalia.CONTRATTO_DI_ADESIONE_A_FONDI_PENSIONE_APERTI;}
//		if(ComparatorUtil.containsValue(sottocategoriaBene, 188)){tipoRisorsa = Constants.TipoRisorseEquitalia.ALTRA_POLIZZA_CONTRATTI;}
//		if(ComparatorUtil.containsValue(sottocategoriaBene, 200)){tipoRisorsa = Constants.TipoRisorseEquitalia.CARTE_DI_CREDITO;}
//		if(ComparatorUtil.containsValue(sottocategoriaBene, 201)){tipoRisorsa = Constants.TipoRisorseEquitalia.CASSETTE_DI_SICUREZZA;}
//		if(ComparatorUtil.containsValue(sottocategoriaBene, 202)){tipoRisorsa = Constants.TipoRisorseEquitalia.CONTRATTI_DERIVATI;}
//		if(ComparatorUtil.containsValue(sottocategoriaBene, 203)){tipoRisorsa = Constants.TipoRisorseEquitalia.FONDI_PENSIONE;}
//		if(ComparatorUtil.containsValue(sottocategoriaBene, 204)){tipoRisorsa = Constants.TipoRisorseEquitalia.GESTIONE_COLLETTIVA_DEL_RISPARMIO;}
//		if(ComparatorUtil.containsValue(sottocategoriaBene, 205)){tipoRisorsa = Constants.TipoRisorseEquitalia.GESTIONE_PATRIMONIALE;}
//		if(ComparatorUtil.containsValue(sottocategoriaBene, 206)){tipoRisorsa = Constants.TipoRisorseEquitalia.DENARO_RICAVATO_DA_VENDITA;}

		tipoRisorsa = getTipoRisorsa(tipoRisorsa, Constants.TipoRisorseEquitalia.LIBRETTO, sottocategoriaBene, Constants.SottoCategoriaBeni.LIBRETTO_POSTALE, Constants.SottoCategoriaBeni.LIBRETTO_BANCARIO);
		tipoRisorsa = getTipoRisorsa(tipoRisorsa, Constants.TipoRisorseEquitalia.CONTO_CORRENTE, sottocategoriaBene, Constants.SottoCategoriaBeni.CC_POSTALE, Constants.SottoCategoriaBeni.CC_BANCARIO);
		tipoRisorsa = getTipoRisorsa(tipoRisorsa, Constants.TipoRisorseEquitalia.TITOLO_DI_STATO, sottocategoriaBene, Constants.SottoCategoriaBeni.TITOLO_DI_STATO);
		tipoRisorsa = getTipoRisorsa(tipoRisorsa, Constants.TipoRisorseEquitalia.CERTIFICATO_DI_DEPOSITO, sottocategoriaBene, Constants.SottoCategoriaBeni.CERTIFICATO_DI_DEPOSITO);
		tipoRisorsa = getTipoRisorsa(tipoRisorsa, Constants.TipoRisorseEquitalia.OBBLIGAZIONE, sottocategoriaBene, Constants.SottoCategoriaBeni.OBBLIGAZIONE);
		tipoRisorsa = getTipoRisorsa(tipoRisorsa, Constants.TipoRisorseEquitalia.AZIONI, sottocategoriaBene, Constants.SottoCategoriaBeni.AZIONI);
		tipoRisorsa = getTipoRisorsa(tipoRisorsa, Constants.TipoRisorseEquitalia.STRUMENTI_FINANZIARI_PARTECIPATIVI, sottocategoriaBene, Constants.SottoCategoriaBeni.STRUMENTI_FINANZIARI_PARTECIPATIVI);
		tipoRisorsa = getTipoRisorsa(tipoRisorsa, Constants.TipoRisorseEquitalia.TITOLI_ATIPICI, sottocategoriaBene, Constants.SottoCategoriaBeni.TITOLI_ATIPICI);
		tipoRisorsa = getTipoRisorsa(tipoRisorsa, Constants.TipoRisorseEquitalia.CONTRATTO_LEASING, sottocategoriaBene, Constants.SottoCategoriaBeni.CONTRATTO_LEASING);
		tipoRisorsa = getTipoRisorsa(tipoRisorsa, Constants.TipoRisorseEquitalia.CREDITI_VARI, sottocategoriaBene, Constants.SottoCategoriaBeni.CREDITI_VARI);
		tipoRisorsa = getTipoRisorsa(tipoRisorsa, Constants.TipoRisorseEquitalia.POLIZZA_ASSICURATIVA_GESTITA_DA_BANCA, sottocategoriaBene, Constants.SottoCategoriaBeni.POLIZZA_ASSICURATIVA, Constants.SottoCategoriaBeni.POLIZZA_ASSICURATIVA_GESTITA_DA_BANCA);
		tipoRisorsa = getTipoRisorsa(tipoRisorsa, Constants.TipoRisorseEquitalia.POLIZZA_ASSICURATIVA_VITA_CASO_MORTE, sottocategoriaBene, Constants.SottoCategoriaBeni.POLIZZA_TEMPORANEA_CASO_MORTE);
		tipoRisorsa = getTipoRisorsa(tipoRisorsa, Constants.TipoRisorseEquitalia.POLIZZA_ASSICURATIVA_VITA_CASO_VITA, sottocategoriaBene, Constants.SottoCategoriaBeni.POLIZZA_CASO_VITA);
		tipoRisorsa = getTipoRisorsa(tipoRisorsa, Constants.TipoRisorseEquitalia.POLIZZA_ASSICURATIVA_VITA_MVI, sottocategoriaBene, Constants.SottoCategoriaBeni.POLIZZA_CASO_MORTE_A_VITA_INTERA);
		tipoRisorsa = getTipoRisorsa(tipoRisorsa, Constants.TipoRisorseEquitalia.POLIZZA_ASSICURATIVA_VITA_C, sottocategoriaBene, Constants.SottoCategoriaBeni.POLIZZA_CASO_VITA_CON_CONTROASSICURAZIONE);
		tipoRisorsa = getTipoRisorsa(tipoRisorsa, Constants.TipoRisorseEquitalia.POLIZZA_ASSICURATIVA_VITA_MISTA, sottocategoriaBene, Constants.SottoCategoriaBeni.POLIZZA_VITA_MISTA);
		tipoRisorsa = getTipoRisorsa(tipoRisorsa, Constants.TipoRisorseEquitalia.CONTRATTO_DI_CAPITALIZZAZIONE, sottocategoriaBene, Constants.SottoCategoriaBeni.POLIZZA_CONTRATTO_CAPITALIZZAZIONE);
		tipoRisorsa = getTipoRisorsa(tipoRisorsa, Constants.TipoRisorseEquitalia.CONTRATTO_DI_ADESIONE_A_FONDI_PENSIONE_APERTI, sottocategoriaBene, Constants.SottoCategoriaBeni.POLIZZA_ADESIONE_FONDI_PENSIONE);
		tipoRisorsa = getTipoRisorsa(tipoRisorsa, Constants.TipoRisorseEquitalia.ALTRA_POLIZZA_CONTRATTI, sottocategoriaBene, Constants.SottoCategoriaBeni.POLIZZA_ALTRA_POLIZZA);
		tipoRisorsa = getTipoRisorsa(tipoRisorsa, Constants.TipoRisorseEquitalia.CARTE_DI_CREDITO, sottocategoriaBene, Constants.SottoCategoriaBeni.CARTE_DI_CREDITO);
		tipoRisorsa = getTipoRisorsa(tipoRisorsa, Constants.TipoRisorseEquitalia.CASSETTE_DI_SICUREZZA, sottocategoriaBene, Constants.SottoCategoriaBeni.CASSETTE_DI_SICUREZZA);
		tipoRisorsa = getTipoRisorsa(tipoRisorsa, Constants.TipoRisorseEquitalia.CONTRATTI_DERIVATI, sottocategoriaBene, Constants.SottoCategoriaBeni.CONTRATTI_DERIVATI);
		tipoRisorsa = getTipoRisorsa(tipoRisorsa, Constants.TipoRisorseEquitalia.FONDI_PENSIONE, sottocategoriaBene, Constants.SottoCategoriaBeni.POLIZZA_FONDI_PENSIONE);
		tipoRisorsa = getTipoRisorsa(tipoRisorsa, Constants.TipoRisorseEquitalia.GESTIONE_COLLETTIVA_DEL_RISPARMIO, sottocategoriaBene, Constants.SottoCategoriaBeni.GESTIONE_COLLETTIVA_DEL_RISPARMIO);
		tipoRisorsa = getTipoRisorsa(tipoRisorsa, Constants.TipoRisorseEquitalia.GESTIONE_PATRIMONIALE, sottocategoriaBene, Constants.SottoCategoriaBeni.GESTIONE_PATRIMONIALE);
		tipoRisorsa = getTipoRisorsa(tipoRisorsa, Constants.TipoRisorseEquitalia.DENARO_RICAVATO_DA_VENDITA, sottocategoriaBene, Constants.SottoCategoriaBeni.DENARO_RICAVATO_DA_VENDITA);
		
		return StringUtils.leftPad(StringUtils.defaultString(tipoRisorsa).trim(), 2, '0');		
	}

	private String getTipoRisorsa(String tipoRisorsa, String codTipoRisorsa, String sottocategoriaBene, long... stati) {
		return Utility.isValid(tipoRisorsa) ? tipoRisorsa : (ComparatorUtil.containsValue(sottocategoriaBene, stati) ? codTipoRisorsa : tipoRisorsa);
	}

	public FugDao getFugDao() {
		return fugDao;
	}

	public void setFugDao(FugDao fugDao) {
		this.fugDao = fugDao;
	}

	private String getDivisa(BeneForm in) {
		return ((in != null) && !Utility.isValid(in.getValutaEur())) ? getDivisa(in.getTipoValuta()) : Constants.EUR;
	}

	private String getDivisa(ComboForm in) {
		return (Utility.hasValidId(in) ? in.getId() : Constants.EUR);
	}

	private String getNumeroRisorsa(BeneForm in) {
		String out = (!Utility.isPolizzaNonBancaria(in)) ? (isValidCodiceIban(in) ? getValidCodiceIban(in) : getValidNumeroRegistrazione(in)) : "";
		if(isValidCodiceIban(in))
			return StringUtils.substring(out, 0, 27);
		else
			return StringUtils.substring(out, 0, 24);
	}
	
	private boolean isValidCodiceIban(BeneForm in) {
//		return Utility.isValid(in) && Utility.isValid(in.getCodiceOperatore());
		return Utility.isValid(in) && Utility.isValid(in.getCodiceIban());
	}

	private String getCodiceOperatore(BeneForm in) {
		String out = Utility.isPolizzaNonBancaria(in) ? getValidCodiceIban(in) : getValidCodiceOperatore(in);
		return StringUtils.substring(out, 0, 16);
	}

	private String getValidNumeroRegistrazione(BeneForm in) {
		return Utility.isValid(in) ? StringUtils.defaultString(in.getNumeroRegistrazione()) : "";
	}

	private String getValidCodiceOperatore(BeneForm in) {
		return Utility.isValid(in) ? StringUtils.defaultString(in.getCodiceOperatore()) : "";
	}

	private String getValidCodiceIban(BeneForm in) {
		return Utility.isValid(in) ? StringUtils.defaultString(in.getCodiceIban()) : "";
	}

	public TFugInvio gatInvioFUGCollegato(int idUffOwn, String protRif) {
		TFugInvio out = new TFugInvio(); 
		List<TFugInvio> lista = fugDao.gatInvioFUGCollegato(idUffOwn, protRif);
		if (Utility.isValid(lista)) {
			Map<String, Object> row = (Map<String, Object>) lista.iterator().next();
			out = (TFugInvio) row.get("fug");
		}
		return out;
	}

	public void getRispostaEdEsitoXML(FugModelloForm form) {
		Map<String, String> xmls = fugDao.getRispostaEdEsitoXML(form.getIdUfficio(), form.getIdInvio());
		if (Utility.isValid(xmls)) {
			form.setRichiestaXML(xmls.get(Constants.FUG_RICHIESTA));
			form.setEsitoXML(xmls.get(Constants.FUG_ESITO));
		}
	}

	public boolean isBeneDecretoRev(long idProc, Long idAtto, Long idBene, int idUffOwner) {
		return fugDao.isBeneDecretoRev(idProc, idAtto, idBene, idUffOwner);
	}

	public boolean isBeneDecretoConf(long idProc, Long idAtto, Long idBene, int idUffOwner) {
		return fugDao.isBeneDecretoConf(idProc, idAtto, idBene, idUffOwner);
	}

	private boolean isDecisorio(String catAtto) {
		return (catAtto.equals(Constants.DECRETO_DECISORIO_I_GRADO) || catAtto.equals(Constants.DECRETO_DECISORIO_II_GRADO));
	}

	private boolean isDecretoInviabile(boolean isDecisorio, long idProc, Long idAtto, Long idBene, int idUffOwner, String tipoEsito) {
		boolean out = true;
		if (isDecisorio) {
			out = isBeneDecretoInviabile(getBeneDecretoInviabile(procedimentoService.listaBeniConSoggettiAssociatiAdAttoEProcedimento(idProc, idAtto, idUffOwner), idBene), tipoEsito);
		}
		return out;
	}

	private List<SoggettoForm> getBeneDecretoInviabile(List<BeneSoggettoForm> risultatiRicerca, Long idBene) {
		List<SoggettoForm> out = new ArrayList<SoggettoForm>();
		for (BeneSoggettoForm beneSoggetto : risultatiRicerca) {
			if (beneSoggetto.getBene().getIdBene().compareTo(idBene) == 0) {
				out = beneSoggetto.getSoggetti();
				break;
			}
		}
		return out;
	}

	private boolean isBeneDecretoInviabile(List<SoggettoForm> listaSoggettiBene, String tipoEsito) {
		boolean out = false;
		int numSoggettiConTipoEsito = 0;
		int numSoggettiConDataDefinitivitaConfermata = 0;
		if (Utility.isValid(listaSoggettiBene)) {
			for (SoggettoForm soggetto : listaSoggettiBene) {
				numSoggettiConTipoEsito += soggetto.getMisura().getDecisioneMisuraPersonale().getId().equals(tipoEsito) ? 1 : 0;
				numSoggettiConDataDefinitivitaConfermata += (soggetto.isDisabledCheckDefinitivita()) ? 1 : 0;
			}
			//out = (numSoggettiConTipoEsito == numSoggettiConDataDefinitivitaConfermata) && (numSoggettiConTipoEsito > 0);
			out = (numSoggettiConTipoEsito > 0);
		}
		return out;
	}
	
	private String estraiSoggetto(long idTFugInvio, SoggettoForm soggettoForm){
		String result = new String();
		TFugMsg messaggio = fugDao.getTFugMsgFromTFugInvio(idTFugInvio);
		if(messaggio != null)
		{
			AcquisisciModelloC modelloC = (AcquisisciModelloC)JAXBUtil.convertBlobToObject(messaggio.getMsg(),AcquisisciModelloC.class);
			if(modelloC != null)
			{
				List<BeneficiariTipo> beneficiari = modelloC.getRichiestaModelloC().getDatiRichiestaInvioModelloC().getBeneficiari();
				Iterator iterSogg = beneficiari.iterator();
				int i=0;
				while (iterSogg.hasNext()) {
					i++;
					BeneficiariTipo beneficiario = (BeneficiariTipo) iterSogg.next();
					if(beneficiario != null)
					{
						if (beneficiario.getTipoSoggetto().equals(TipoSoggettoTipo.PF)) {
							if(beneficiario.getCF()!=null && beneficiario.getCF().length()>0 &&
									beneficiario.getCF().equalsIgnoreCase(soggettoForm.getCodiceFiscale()))
								return "" + i;
							if(beneficiario.getCognome()!=null && beneficiario.getCognome().length()>0 &&
									beneficiario.getCognome().equalsIgnoreCase(soggettoForm.getCognome()) &&
									beneficiario.getNome()!=null && beneficiario.getNome().length()>0 &&
									beneficiario.getNome().equalsIgnoreCase(soggettoForm.getNome()) &&
									beneficiario.getDataNascitaCostituzione()!=null && beneficiario.getDataNascitaCostituzione().length()>0 &&
									(beneficiario.getDataNascitaCostituzione().equals(ModelloC.getData(soggettoForm.getDataNascita()))))
								return "" + i;
						}
						if (beneficiario.getTipoSoggetto().value().equals(beneficiario.getTipoSoggetto().PNF)) {
							if(beneficiario.getPIVA()!=null && beneficiario.getPIVA().length()>0 &&
									beneficiario.getPIVA().equalsIgnoreCase(soggettoForm.getPartitaIva()))
								return "" + i;
							else
								if(beneficiario.getDenominazione()!=null && beneficiario.getDenominazione().length()>0 &&
									beneficiario.getDenominazione().equalsIgnoreCase(soggettoForm.getRagioneSociale()))
									return "" + i;
						}
					}
				}
			}
		}
		return result;
	}

}