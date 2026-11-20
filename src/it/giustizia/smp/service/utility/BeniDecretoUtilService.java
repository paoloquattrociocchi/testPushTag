package it.giustizia.smp.service.utility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.BeneForm;
import it.giustizia.smp.form.BeneSoggettoForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.model.TProcedimento;
import it.giustizia.smp.model.TRelBeneAtto;
import it.giustizia.smp.service.BeneService;
import it.giustizia.smp.service.FlussiService;
import it.giustizia.smp.service.MisuraService;
import it.giustizia.smp.service.utility.component.ComboUtilComponent;
import it.giustizia.smp.utility.ComparatorUtil;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Constants.CategoriaAtto;
import it.giustizia.smp.utility.Constants.TipoAtto;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.validation.BeneValidation;

@Service
public class BeniDecretoUtilService extends AttoUtilService {

	@Autowired
	private Utility utility;
	@Autowired
	private MisuraService misuraService;
	@Autowired
	private BeneService beneService;
	@Autowired
	private ComboUtilComponent comboUtilComponent;
	@Autowired
	private FlussiService flussiService;
	@Autowired
	private BeneValidation beneValidation;
	
	@SuppressWarnings("rawtypes")
	public void beniDecretoSequestro(AttoForm attoForm, HttpServletRequest request, ModelMap modelMap, BindingResult result, boolean aggiornaLimiteBeneSequestrati,
			String nomeMetodo) {

		int idUffOwn = recuperaIdUfficioUtente();
		String codUtente = recuperaIdCodiceUtente();
		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		long idAtto = 0l;

		if (attoForm != null && attoForm.getCodAtto() > 0)
			idAtto = attoForm.getCodAtto();
		else
			idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
		
		if ( attoForm != null && attoForm.getIdProcedimentoCdatrib() != null && attoForm.getIdProcedimentoCdatrib() > 0){
			
			TProcedimento procedi= procedimentoService.getProcedimentoById(attoForm.getIdProcedimentoCdatrib());
			idUffOwn=procedi.getTAnUfficioByCodiFkIdUffOwner().getCodiKIdUfficio();			
			idProcedimento=attoForm.getIdProcedimentoCdatrib();
			AttoForm attoForm1 = attoService.dettaglioAtto(idUffOwn, idAtto, idProcedimento, codUtente);
			if (attoForm1!= null && attoForm1.getCategoriaAtto() != null  && attoForm1.getCategoriaAtto().getId() != null){
				attoForm = attoForm1;
				attoForm.setIdProcedimentoCdatrib(idProcedimento);
			}
		
		}
		

		Long idProcedimentoCda = attoForm.getIdProcedimentoCda();

		// vado a controllare se c'e' nell'atto
		if (idProcedimentoCda == null || idProcedimentoCda == 0) {

			attoForm.setIdProcedimentoCda(((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getIdProcedimentoCda());
			idProcedimentoCda = attoForm.getIdProcedimentoCda();

			AttoForm atto = recuperaAtto();
			if (atto != null) {
				atto.setIdProcedimentoCda(null);
				request.getSession().setAttribute("atto", atto);
			}
		}

		if (attoForm.getIdProcedimentoCda() != null && attoForm.getIdProcedimentoCda() > 0) {
			idUffOwn = Integer.parseInt(commonService.getUfficiCompetenza(idProcedimento, 19L).get(0).getId());
			idProcedimento = attoForm.getIdProcedimentoCda();
		}

		 if (!attoForm.isVisualizzaFiltro()) {
	        	attoForm.setSoggettiDiretti(null);
	        	attoForm.setSoggettiTitolari(null);
	        	attoForm.setTipologiaBene(null);
	        	attoForm.setCategoriaBene(null);
	        	attoForm.setSottoCategoriaBene(null);
			} 
	
		long idSoggettoDiretto = attoForm.getIdSoggettoDiretto();
		long idSoggettoTitolare = attoForm.getIdSoggettoTitolare();
		long idTipologia = attoForm.getIdTipologia();
		long idCategoria = attoForm.getIdCategoria();
		long idSottocategoria = attoForm.getIdSottocategoria();

		List<BeneSoggettoForm> risultatiRicerca;
		if (idProcedimentoCda != null || (attoForm != null && attoForm.getIdProcedimentoCdatrib() != null && attoForm.getIdProcedimentoCdatrib() > 0)) {
			risultatiRicerca = procedimentoService.listaBeniConSoggettiAssociatiAdAttoEProcedimento(idProcedimento, idAtto, idUffOwn, true);
		} else {
			risultatiRicerca = procedimentoService.listaBeniConSoggettiAssociatiAdAttoEProcedimento(idProcedimento, idAtto, idUffOwn);
		}

		// inizio P.Q. 07/08/2020 Controllo se per un determinato bene ho eseguito tutte le misure 
		
		if (risultatiRicerca.size() > 0)
		{
			for (BeneSoggettoForm beneSoggettoForm : risultatiRicerca) {
				int totale = beneSoggettoForm.getSoggetti().size();
				int count = 0;
				for (SoggettoForm soggettoForm : beneSoggettoForm.getSoggetti()) {
					if (soggettoForm.getDataEsecuzione()!=null) 
						count++;
				}
				if(count == totale) {
					beneSoggettoForm.setDisattivaCheckSinistra(true);
				} else {
					beneSoggettoForm.setDisattivaCheckSinistra(false);
				}
			}
		}
				
		// fine P.Q. 07/08/2020 Controllo se per un determinato bene ho eseguito tutte le misure
		
		
		modelMap.addAttribute("risultatiRicerca", risultatiRicerca);

		List<BeneForm> beni = beneService.listaBeniPerProcedimentoNoAtto(idUffOwn, idProcedimento, idAtto, -1l,  
							                 idSoggettoDiretto, idSoggettoTitolare, idTipologia, idCategoria, idSottocategoria);
		modelMap.addAttribute("beni", beni);

		modelMap.addAttribute("categorie", comboUtilComponent.getCategorieBene(attoForm));
		modelMap.addAttribute("sottocategorie", comboUtilComponent.getSottoCategorieBene(attoForm));
		if (attoForm.isVisualizzaFiltro()) {
	        // carica combo soggetti diretti e titolari se filtro = true
			List<SoggettoForm> soggettiDiretti = attoService.getRicercaSoggettoAtto(idProcedimento, idAtto, idUffOwn, Constants.ID_RUOLO_DIRETTO, Constants.Flag.S);
   			if (soggettiDiretti != null )
				modelMap.addAttribute("soggettiDiretti", utility.creaComboSoggetti(soggettiDiretti));
			List<SoggettoForm> soggettiTitolari = attoService.getRicercaSoggettoAtto(idProcedimento, idAtto, idUffOwn, Constants.ID_RUOLO_TITOLARI, Constants.Flag.S);
   			if (soggettiTitolari != null )
				modelMap.addAttribute("soggettiTitolari", utility.creaComboSoggetti(soggettiTitolari));
        }

		if (aggiornaLimiteBeneSequestrati)
			for (Iterator iterator = risultatiRicerca.iterator(); iterator.hasNext();) {
				BeneSoggettoForm beneSoggetto = (BeneSoggettoForm) iterator.next();
				List<SoggettoForm> soggettiDelBene = beneSoggetto.getSoggetti();

				for (Iterator iterator2 = soggettiDelBene.iterator(); iterator2.hasNext();) {
					SoggettoForm soggetto = (SoggettoForm) iterator2.next();
					beneSoggetto.setSoggetto(soggetto);
					beneSoggetto.setIdTipoInteresse(Integer.parseInt(soggetto.getTipoInteresse().getId()));
					beneSoggetto.setQuotaInteresse(soggetto.getQuotaInteresse());
					beneSoggetto.setNumeratoreFrazioneQuota(soggetto.getNumeratoreFrazioneQuota());
					beneSoggetto.setDenominatoreFrazioneQuota(soggetto.getDenominatoreFrazioneQuota());

					long idTipoMisura = NumberUtils.toLong(soggetto.getMisura().getTipoMisura().getId());

					// Nel caso di non convalida del sequestro si storna la
					// quota su t_rel_proc_bene_Tit
					if (idTipoMisura == Constants.TIPO_MISURA_SEQUESTRO_NON_CONVALIDATO) {

						// Carico le percentuali o frazioni per
						// T_REL_PROC_BENE_TIT
						double quotaSequestro = 0;
						double numeratoreFrazioneSequestro = 0;
						double denominatoreFrazioneSequestro = 0;

						long idBene = beneSoggetto.getBene().getIdBene();
						long idSoggetto = beneSoggetto.getSoggetto().getIdSoggetto();
						int idTipoInteresse = Integer.parseInt(soggetto.getTipoInteresse().getId());

						if (soggetto.getQuotaInteresse() != null)
							quotaSequestro = soggetto.getQuotaInteresse().doubleValue();
						if (soggetto.getNumeratoreFrazioneQuota() != null)
							numeratoreFrazioneSequestro = soggetto.getNumeratoreFrazioneQuota().doubleValue();
						if (soggetto.getDenominatoreFrazioneQuota() != null)
							denominatoreFrazioneSequestro = soggetto.getDenominatoreFrazioneQuota().doubleValue();
						beneService.modificaRelBeneTit(beneSoggetto, idProcedimento, idAtto, idUffOwn, codUtente, false, quotaSequestro,
								numeratoreFrazioneSequestro, denominatoreFrazioneSequestro, Constants.TipiOperazioneCRUD.DELETE, nomeMetodo);
						// Richiamo la procedura di aggiornamento di
						// T_REL_BENE_ATTI per allienare lo stato del bene in
						// caso di eventuali esecuzioni successive. Sabino
						misuraService.convalidaNonConvalidaSequestro(idProcedimento, idAtto, idBene, idTipoInteresse, idSoggetto, idTipoMisura,
								idUffOwn, codUtente, Constants.TipiOperazioneConvalidaNonConvalida.NON_CONVALIDA, nomeMetodo);
					}
				}
			}
		
		attoForm = caricaAttoInSessione(idUffOwn, idAtto, codUtente, idProcedimento, request, attoForm);

		if (idProcedimentoCda != null && idProcedimentoCda > 0)
			attoForm.setIdProcedimentoCda(idProcedimentoCda);		

	}
	
	
	public void beniDecretoSequestroSospensioneTemporanea(AttoForm attoForm, HttpServletRequest request, ModelMap modelMap, BindingResult result, String nomeMetodo) {

		int idUffOwn = recuperaIdUfficioUtente();

		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		long idAttoOrigine = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAttoOrigine();

        String codUtente = this.recuperaIdCodiceUtente();
		long idAtto = 0l;

		if (attoForm != null && attoForm.getCodAtto() > 0)
			idAtto = attoForm.getCodAtto();
		else
			idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
		
		if (attoForm != null && attoForm.getIdProcedimentoCdatrib() != null && attoForm.getIdProcedimentoCdatrib() > 0){
			
			TProcedimento procedi= procedimentoService.getProcedimentoById(attoForm.getIdProcedimentoCdatrib());
			idUffOwn=procedi.getTAnUfficioByCodiFkIdUffOwner().getCodiKIdUfficio();			
			idProcedimento=attoForm.getIdProcedimentoCdatrib();
			AttoForm attoForm1 = attoService.dettaglioAtto(idUffOwn, idAtto, idProcedimento, codUtente);
			if (attoForm1!= null && attoForm1.getCategoriaAtto() != null  && attoForm1.getCategoriaAtto().getId() != null){
				attoForm = attoForm1;
				attoForm.setIdProcedimentoCdatrib(idProcedimento);
			}
		
		}
		
		List<BeneSoggettoForm> risultatiRicerca;
		if (attoForm != null && attoForm.getIdProcedimentoCdatrib() != null && attoForm.getIdProcedimentoCdatrib() > 0) {
			risultatiRicerca = procedimentoService.listaBeniConSoggettiAssociatiAdAttoEProcedimento(idProcedimento, idAtto, idUffOwn, true);
		} else {
			risultatiRicerca = procedimentoService.listaBeniConSoggettiAssociatiAdAttoEProcedimento(idProcedimento, idAtto, idUffOwn);
		}		
		
		modelMap.addAttribute("risultatiRicerca", risultatiRicerca);


		if (!attoForm.isVisualizzaFiltro()) {
        	attoForm.setSoggettiDiretti(null);
        	attoForm.setSoggettiTitolari(null);
        	attoForm.setTipologiaBene(null);
        	attoForm.setCategoriaBene(null);
        	attoForm.setSottoCategoriaBene(null);
		} 

		long idSoggettoDiretto = attoForm.getIdSoggettoDiretto();
		long idSoggettoTitolare = attoForm.getIdSoggettoTitolare();
		long idTipologia = attoForm.getIdTipologia();
		long idCategoria = attoForm.getIdCategoria();
		long idSottocategoria = attoForm.getIdSottocategoria();

		List<BeneForm> beniDaAssociare = beneService.listaBeniProcedimentoFiltrata(idProcedimento, idUffOwn, idAttoOrigine, // filtra sull'atto di origine ....
				idSoggettoDiretto, idSoggettoTitolare, idTipologia, idCategoria, idSottocategoria);
		modelMap.addAttribute("beni", beniDaAssociare);

		modelMap.addAttribute("categorie", comboUtilComponent.getCategorieBene(attoForm));
		modelMap.addAttribute("sottocategorie", comboUtilComponent.getSottoCategorieBene(attoForm));
		if (attoForm.isVisualizzaFiltro()) {
	        // carica combo soggetti diretti e titolari se filtro = true
			List<SoggettoForm> soggettiDiretti = attoService.getRicercaSoggettoAtto(idProcedimento, idAtto, idUffOwn, Constants.ID_RUOLO_DIRETTO, Constants.Flag.S);
   			if (soggettiDiretti != null )
				modelMap.addAttribute("soggettiDiretti", utility.creaComboSoggetti(soggettiDiretti));
			List<SoggettoForm> soggettiTitolari = attoService.getRicercaSoggettoAtto(idProcedimento, idAtto, idUffOwn, Constants.ID_RUOLO_TITOLARI, Constants.Flag.S);
   			if (soggettiTitolari != null )
				modelMap.addAttribute("soggettiTitolari", utility.creaComboSoggetti(soggettiTitolari));
        }
	}
	
	public void beniDecretoRevocaSequestro(AttoForm attoForm, HttpServletRequest request, ModelMap modelMap, BindingResult result, String nomeMetodo) {

		int idUffOwn = recuperaIdUfficioUtente();
		String descUtente = recuperaIdCodiceUtente();
		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		long idAtto = 0l;

		if (attoForm != null && attoForm.getCodAtto() > 0)
			idAtto = attoForm.getCodAtto();
		else
			idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
		if (attoForm != null && attoForm.getIdProcedimentoCdatrib() != null && attoForm.getIdProcedimentoCdatrib() > 0){
			
			TProcedimento procedi= procedimentoService.getProcedimentoById(attoForm.getIdProcedimentoCdatrib());
			idUffOwn=procedi.getTAnUfficioByCodiFkIdUffOwner().getCodiKIdUfficio();			
			idProcedimento=attoForm.getIdProcedimentoCdatrib();
			AttoForm attoForm1 = attoService.dettaglioAtto(idUffOwn, idAtto, idProcedimento, descUtente);
			if (attoForm1!= null && attoForm1.getCategoriaAtto() != null  && attoForm1.getCategoriaAtto().getId() != null){
				attoForm = attoForm1;
				attoForm.setIdProcedimentoCdatrib(idProcedimento);
			}
		
		}

        if (!attoForm.isVisualizzaFiltro()) {
        	attoForm.setSoggettiDiretti(null);
        	attoForm.setSoggettiTitolari(null);
        	attoForm.setTipologiaBene(null);
        	attoForm.setCategoriaBene(null);
        	attoForm.setSottoCategoriaBene(null);
		} 
		long idSoggettoDiretto = attoForm.getIdSoggettoDiretto();
		long idSoggettoTitolare = attoForm.getIdSoggettoTitolare();
		long idTipologia = attoForm.getIdTipologia();
		long idCategoria = attoForm.getIdCategoria();
		long idSottocategoria = attoForm.getIdSottocategoria();
        
		List<BeneSoggettoForm> risultatiRicerca;
		if (attoForm != null && attoForm.getIdProcedimentoCdatrib() != null && attoForm.getIdProcedimentoCdatrib() > 0) {
			risultatiRicerca = procedimentoService.listaBeniConSoggettiAssociatiAdAttoEProcedimento(idProcedimento, idAtto, idUffOwn, true);
		} else {
			risultatiRicerca = procedimentoService.listaBeniConSoggettiAssociatiAdAttoEProcedimento(idProcedimento, idAtto, idUffOwn);
		}		
		
		modelMap.addAttribute("risultatiRicerca", risultatiRicerca);
		List<BeneForm> beni = beneService.listaBeniPerProcedimentoNoAttoPerRevoca(idAtto, idProcedimento, descUtente, idUffOwn,
				super.printMethodName(), idSoggettoDiretto, idSoggettoTitolare, idTipologia, idCategoria, idSottocategoria);
		modelMap.addAttribute("beni", beni);

		modelMap.addAttribute("categorie", comboUtilComponent.getCategorieBene(attoForm));
		modelMap.addAttribute("sottocategorie", comboUtilComponent.getSottoCategorieBene(attoForm));
		if (attoForm.isVisualizzaFiltro()) {
	        // carica combo soggetti diretti e titolari se filtro = true
			List<SoggettoForm> soggettiDiretti = attoService.getRicercaSoggettoAtto(idProcedimento, idAtto, idUffOwn, Constants.ID_RUOLO_DIRETTO, Constants.Flag.S);
   			if (soggettiDiretti != null )
				modelMap.addAttribute("soggettiDiretti", utility.creaComboSoggetti(soggettiDiretti));
			List<SoggettoForm> soggettiTitolari = attoService.getRicercaSoggettoAtto(idProcedimento, idAtto, idUffOwn, Constants.ID_RUOLO_TITOLARI, Constants.Flag.S);
   			if (soggettiTitolari != null )
				modelMap.addAttribute("soggettiTitolari", utility.creaComboSoggetti(soggettiTitolari));
        }
	}
	
	public void beniDecretoOpposizioneDissequestro(AttoForm attoForm, HttpServletRequest request, ModelMap modelMap, BindingResult result, String nomeMetodo) {

		int idUffOwn = recuperaIdUfficioUtente();
		String descUtente = recuperaIdCodiceUtente();
		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		long idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();

        if (!attoForm.isVisualizzaFiltro()) {
        	attoForm.setSoggettiDiretti(null);
        	attoForm.setSoggettiTitolari(null);
        	attoForm.setTipologiaBene(null);
        	attoForm.setCategoriaBene(null);
        	attoForm.setSottoCategoriaBene(null);
		} 
		long idSoggettoDiretto = attoForm.getIdSoggettoDiretto();
		long idSoggettoTitolare = attoForm.getIdSoggettoTitolare();
		long idTipologia = attoForm.getIdTipologia();
		long idCategoria = attoForm.getIdCategoria();
		long idSottocategoria = attoForm.getIdSottocategoria();
        
		modelMap.addAttribute("categorie", comboUtilComponent.getCategorieBene(attoForm));
		modelMap.addAttribute("sottocategorie", comboUtilComponent.getSottoCategorieBene(attoForm));
		if (attoForm.isVisualizzaFiltro()) {
	        // carica combo soggetti diretti e titolari se filtro = true
			List<SoggettoForm> soggettiDiretti = attoService.getRicercaSoggettoAtto(idProcedimento, idAtto, idUffOwn, Constants.ID_RUOLO_DIRETTO, Constants.Flag.S);
   			if (soggettiDiretti != null )
				modelMap.addAttribute("soggettiDiretti", utility.creaComboSoggetti(soggettiDiretti));
			List<SoggettoForm> soggettiTitolari = attoService.getRicercaSoggettoAtto(idProcedimento, idAtto, idUffOwn, Constants.ID_RUOLO_TITOLARI, Constants.Flag.S);
   			if (soggettiTitolari != null )
				modelMap.addAttribute("soggettiTitolari", utility.creaComboSoggetti(soggettiTitolari));
        }

		List<BeneSoggettoForm> risultatiRicerca = procedimentoService.listaBeniConSoggettiAssociatiAdAttoEProcedimento(idProcedimento, idAtto,
				idUffOwn);
		modelMap.addAttribute("risultatiRicerca", risultatiRicerca);
		List<BeneForm> beni = beneService.listaBeniPerProcedimentoNoAttoPerOpposizione(idAtto, idProcedimento, descUtente, idUffOwn,
							super.printMethodName(), idSoggettoDiretto, idSoggettoTitolare, idTipologia, idCategoria, idSottocategoria);
		modelMap.addAttribute("beni", beni);
	}
	
	public void beniDecretoRevocaOpposizioneDissequestro(HttpServletRequest request, ModelMap modelMap, BindingResult result, String nomeMetodo) {

		int idUffOwn = recuperaIdUfficioUtente();
		String descUtente = recuperaIdCodiceUtente();
		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		long idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
		List<BeneSoggettoForm> risultatiRicerca = procedimentoService.listaBeniConSoggettiAssociatiAdAttoEProcedimento(idProcedimento, idAtto,
				idUffOwn);
		modelMap.addAttribute("risultatiRicerca", risultatiRicerca);
		List<BeneForm> beni = beneService.listaBeniPerProcedimentoNoAttoPerRevocaOpposizione(idAtto, idProcedimento, descUtente, idUffOwn,
				super.printMethodName());
		modelMap.addAttribute("beni", beni);
	}
	
	public void beniDecretoOrdinanzaRestituzioneBeni(AttoForm attoForm, HttpServletRequest request, ModelMap modelMap, BindingResult result, String nomeMetodo) {

		int idUffOwn = recuperaIdUfficioUtente();
		String descUtente = recuperaIdCodiceUtente();
		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		long idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();

        if (!attoForm.isVisualizzaFiltro()) {
        	attoForm.setSoggettiDiretti(null);
        	attoForm.setSoggettiTitolari(null);
        	attoForm.setTipologiaBene(null);
        	attoForm.setCategoriaBene(null);
        	attoForm.setSottoCategoriaBene(null);
		} 
		long idSoggettoDiretto = attoForm.getIdSoggettoDiretto();
		long idSoggettoTitolare = attoForm.getIdSoggettoTitolare();
		long idTipologia = attoForm.getIdTipologia();
		long idCategoria = attoForm.getIdCategoria();
		long idSottocategoria = attoForm.getIdSottocategoria();
        
		modelMap.addAttribute("categorie", comboUtilComponent.getCategorieBene(attoForm));
		modelMap.addAttribute("sottocategorie", comboUtilComponent.getSottoCategorieBene(attoForm));
		if (attoForm.isVisualizzaFiltro()) {
	        // carica combo soggetti diretti e titolari se filtro = true
			List<SoggettoForm> soggettiDiretti = attoService.getRicercaSoggettoAtto(idProcedimento, idAtto, idUffOwn, Constants.ID_RUOLO_DIRETTO, Constants.Flag.S);
   			if (soggettiDiretti != null )
				modelMap.addAttribute("soggettiDiretti", utility.creaComboSoggetti(soggettiDiretti));
			List<SoggettoForm> soggettiTitolari = attoService.getRicercaSoggettoAtto(idProcedimento, idAtto, idUffOwn, Constants.ID_RUOLO_TITOLARI, Constants.Flag.S);
   			if (soggettiTitolari != null )
				modelMap.addAttribute("soggettiTitolari", utility.creaComboSoggetti(soggettiTitolari));
        }

		// beni associati al decreto di revocazione della confisca
		List<BeneSoggettoForm> risultatiRicerca = procedimentoService.listaBeniConSoggettiAssociatiAdAttoEProcedimentoPerDecRestBeni(idProcedimento,
				idAtto, idUffOwn, descUtente, nomeMetodo);
		modelMap.addAttribute("risultatiRicerca", risultatiRicerca);

		// escluso i beni già presenti nel Decreto/Ordinanza Restituzione Beni
		List<BeneForm> beni = beneService.listaBeniPerProcedimentoNoAttoPerDecRestBeni(idAtto, idProcedimento, descUtente, idUffOwn,
		 				super.printMethodName(), idSoggettoDiretto, idSoggettoTitolare, idTipologia, idCategoria, idSottocategoria);
		modelMap.addAttribute("beni", beni);
	}
	
	public void beniRevocazioneConfisca(AttoForm attoForm, HttpServletRequest request, ModelMap modelMap, BindingResult result, String nomeMetodo) {

		int idUffOwn = recuperaIdUfficioUtente();
		String descUtente = recuperaIdCodiceUtente();
		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		long idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();

        if (!attoForm.isVisualizzaFiltro()) {
        	attoForm.setSoggettiDiretti(null);
        	attoForm.setSoggettiTitolari(null);
        	attoForm.setTipologiaBene(null);
        	attoForm.setCategoriaBene(null);
        	attoForm.setSottoCategoriaBene(null);
		} 

		long idSoggettoDiretto = attoForm.getIdSoggettoDiretto();
		long idSoggettoTitolare = attoForm.getIdSoggettoTitolare();
		long idTipologia = attoForm.getIdTipologia();
		long idCategoria = attoForm.getIdCategoria();
		long idSottocategoria = attoForm.getIdSottocategoria();

		List<BeneSoggettoForm> risultatiRicerca = procedimentoService.listaBeniConSoggettiAssociatiAdAttoEProcedimento(idProcedimento, idAtto, idUffOwn);
		modelMap.addAttribute("risultatiRicerca", risultatiRicerca);
		List<BeneForm> beni = beneService.listaBeniPerProcedimentoNoAttoPerRevocazione(idAtto, idProcedimento, descUtente, idUffOwn, super.printMethodName(),
                							idSoggettoDiretto, idSoggettoTitolare, idTipologia, idCategoria, idSottocategoria);
		modelMap.addAttribute("beni", beni);

		modelMap.addAttribute("categorie", comboUtilComponent.getCategorieBene(attoForm));
		modelMap.addAttribute("sottocategorie", comboUtilComponent.getSottoCategorieBene(attoForm));
		if (attoForm.isVisualizzaFiltro()) {
	        // carica combo soggetti diretti e titolari se filtro = true
			List<SoggettoForm> soggettiDiretti = attoService.getRicercaSoggettoAtto(idProcedimento, idAtto, idUffOwn, Constants.ID_RUOLO_DIRETTO, Constants.Flag.S);
   			if (soggettiDiretti != null )
				modelMap.addAttribute("soggettiDiretti", utility.creaComboSoggetti(soggettiDiretti));
			List<SoggettoForm> soggettiTitolari = attoService.getRicercaSoggettoAtto(idProcedimento, idAtto, idUffOwn, Constants.ID_RUOLO_TITOLARI, Constants.Flag.S);
   			if (soggettiTitolari != null )
				modelMap.addAttribute("soggettiTitolari", utility.creaComboSoggetti(soggettiTitolari));
        }

	}
	
	public String beneCorrezioneErroreMateriale(HttpServletRequest request, ModelMap modelMap, BindingResult result, String nomeMetodo) {

		int idUffOwn = recuperaIdUfficioUtente();
		String descUtente = recuperaIdCodiceUtente();
		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		long idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
		
		List<BeneForm> beni = beneService.listaBeneCorrezioneErroreMateriale(idAtto, idProcedimento, descUtente, idUffOwn,
				super.printMethodName());
		
		if (!beni.isEmpty()){
		modelMap.addAttribute("beni", beni);
		
		return "/procedimento/atto/copertinaCorrezioneErroreMateriale";
		}
		else {
			ProcedimentoForm procedimentoForm = recuperaProcedimento();
			procedimentoForm.setCallbackUrl("redirect:/procedimento/atti/elencoAttiOrdineCronologico.htm");
			
		    BeneForm beneForm=new BeneForm();
		    modelMap.addAttribute("beneForm",beneForm);
		    return visualizzaRicercaDelBene(beneForm,request,modelMap);
		}
	}
	
	public void beniDecretoProrogaSequestro(AttoForm attoForm,HttpServletRequest request, ModelMap modelMap, BindingResult result, String nomeMetodo) {

		String codUtente = recuperaIdCodiceUtente();
		int idUffOwn = recuperaIdUfficioUtente();
		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		long idAtto = 0l;
		

		if (attoForm != null && attoForm.getCodAtto() > 0)
			idAtto = attoForm.getCodAtto();
		else
			idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
		
		if (attoForm != null && attoForm.getIdProcedimentoCdatrib() != null && attoForm.getIdProcedimentoCdatrib() > 0){
			
			TProcedimento procedi= procedimentoService.getProcedimentoById(attoForm.getIdProcedimentoCdatrib());
			idUffOwn=procedi.getTAnUfficioByCodiFkIdUffOwner().getCodiKIdUfficio();			
			idProcedimento=attoForm.getIdProcedimentoCdatrib();
			AttoForm attoForm1 = attoService.dettaglioAtto(idUffOwn, idAtto, idProcedimento, codUtente);
			if (attoForm1!= null && attoForm1.getCategoriaAtto() != null  && attoForm1.getCategoriaAtto().getId() != null){
				attoForm = attoForm1;
				attoForm.setIdProcedimentoCdatrib(idProcedimento);
			}
		
		}		
		
		List<BeneSoggettoForm> risultatiRicerca;
		if (attoForm != null && attoForm.getIdProcedimentoCdatrib() != null && attoForm.getIdProcedimentoCdatrib() > 0) {
			risultatiRicerca = procedimentoService.listaBeniConSoggettiAssociatiAdAttoEProcedimento(idProcedimento, idAtto, idUffOwn, true);
		} else {
			risultatiRicerca = procedimentoService.listaBeniConSoggettiAssociatiAdAttoEProcedimento(idProcedimento, idAtto, idUffOwn);
		}			
		modelMap.addAttribute("risultatiRicerca", risultatiRicerca);
	}
	
	public void beniDecretoSospensioneTemporanea(AttoForm attoForm,HttpServletRequest request, ModelMap modelMap, BindingResult result, String nomeMetodo) {

		String codUtente = this.recuperaIdCodiceUtente();
		int idUffOwn = recuperaIdUfficioUtente();
		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		long idAtto = 0l;
		

		if (attoForm != null && attoForm.getCodAtto() > 0)
			idAtto = attoForm.getCodAtto();
		else
			idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
		
		if (attoForm != null && attoForm.getIdProcedimentoCdatrib() != null && attoForm.getIdProcedimentoCdatrib() > 0){
			
			TProcedimento procedi= procedimentoService.getProcedimentoById(attoForm.getIdProcedimentoCdatrib());
			idUffOwn=procedi.getTAnUfficioByCodiFkIdUffOwner().getCodiKIdUfficio();			
			idProcedimento=attoForm.getIdProcedimentoCdatrib();
			AttoForm attoForm1 = attoService.dettaglioAtto(idUffOwn, idAtto, idProcedimento, codUtente);
			if (attoForm1!= null && attoForm1.getCategoriaAtto() != null  && attoForm1.getCategoriaAtto().getId() != null){
				attoForm = attoForm1;
				attoForm.setIdProcedimentoCdatrib(idProcedimento);
			}
		
		}		
		
		List<BeneSoggettoForm> risultatiRicerca;
		if (attoForm != null && attoForm.getIdProcedimentoCdatrib() != null && attoForm.getIdProcedimentoCdatrib() > 0) {
			risultatiRicerca = procedimentoService.listaBeniConSoggettiAssociatiAdAttoEProcedimento(idProcedimento, idAtto, idUffOwn, true);
		} else {
			risultatiRicerca = procedimentoService.listaBeniConSoggettiAssociatiAdAttoEProcedimento(idProcedimento, idAtto, idUffOwn);
		}		

		modelMap.addAttribute("risultatiRicerca", risultatiRicerca);
	}	
	
	public void beniDecretoProrogaSospensioneTemporanea(AttoForm attoForm,HttpServletRequest request, ModelMap modelMap, BindingResult result, String nomeMetodo) {

		String codUtente = this.recuperaIdCodiceUtente();
		int idUffOwn = recuperaIdUfficioUtente();
		long idProcedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();

		long idAtto = 0l;
		
		if (attoForm != null && attoForm.getCodAtto() > 0)
			idAtto = attoForm.getCodAtto();
		else
			idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
		
		if (attoForm != null && attoForm.getIdProcedimentoCdatrib() != null && attoForm.getIdProcedimentoCdatrib() > 0){
			
			TProcedimento procedi= procedimentoService.getProcedimentoById(attoForm.getIdProcedimentoCdatrib());
			idUffOwn=procedi.getTAnUfficioByCodiFkIdUffOwner().getCodiKIdUfficio();			
			idProcedimento=attoForm.getIdProcedimentoCdatrib();
			AttoForm attoForm1 = attoService.dettaglioAtto(idUffOwn, idAtto, idProcedimento, codUtente);
			if (attoForm1!= null && attoForm1.getCategoriaAtto() != null  && attoForm1.getCategoriaAtto().getId() != null){
				attoForm = attoForm1;
				attoForm.setIdProcedimentoCdatrib(idProcedimento);
			}
		
		}		
		
		List<BeneSoggettoForm> risultatiRicerca;
		if (attoForm != null && attoForm.getIdProcedimentoCdatrib() != null && attoForm.getIdProcedimentoCdatrib() > 0) {
			risultatiRicerca = procedimentoService.listaBeniConSoggettiAssociatiAdAttoEProcedimento(idProcedimento, idAtto,idUffOwn,true);
		} else {
			risultatiRicerca = procedimentoService.listaBeniConSoggettiAssociatiAdAttoEProcedimento(idProcedimento, idAtto,idUffOwn);
		}			
		modelMap.addAttribute("risultatiRicerca", risultatiRicerca);
	}
	
	public void beniDecretoRigettoSequestro(long idAtto, ModelMap modelMap) {

		int idUffOwn = recuperaIdUfficioUtente();
		String codiceUtente = recuperaIdCodiceUtente();
		long idProcedimento = recuperaIdProcedimento();
		
		List<BeneForm> beni = beneService.listaBeniPerProcedimentoNoAtto(idUffOwn, idProcedimento, idAtto, -1l, 0, 0, 0, 0, 0);
		modelMap.addAttribute("beni", beni);
		
		Iterator<BeneForm> itBeni = beni.iterator();
		while (itBeni.hasNext()){
			long idBene = (itBeni.next()).getIdBene();
			// passo come idAttoSequestro 0L poichè sulla procedura si aspetta
			// quel valore >0 solo in caso
			// di revoca sequestro
			attoService.copiaMisBeneAtto(idProcedimento, idAtto, idBene, idUffOwn, codiceUtente, Constants.TipiFlagDecretoRevoca.DECRETO, super.printMethodName());
		}
	}
	
	public String visualizzaRicercaDelBene(BeneForm beneForm, HttpServletRequest request, ModelMap modelMap) {

		// tolgo l'eventuale atto in sessione
		if (!isCorrezioneErroreMateriale())
			if (request.getSession().getAttribute(Constants.ATTO) != null)
				request.getSession().removeAttribute(Constants.ATTO);

		if (request.getSession().getAttribute(Constants.SUCCESS_MESSAGE) != null) {
			modelMap.addAttribute(Constants.SUCCESS_MESSAGE,
					request.getSession().getAttribute(Constants.SUCCESS_MESSAGE));
			request.getSession().removeAttribute(Constants.SUCCESS_MESSAGE);
		}
		if (request.getSession().getAttribute(Constants.SUCCESS_MESSAGE_LIST) != null) {
			modelMap.addAttribute(Constants.SUCCESS_MESSAGE_LIST,
					request.getSession().getAttribute(Constants.SUCCESS_MESSAGE_LIST));
			request.getSession().removeAttribute(Constants.SUCCESS_MESSAGE_LIST);
		}
		// ProcedimentoForm procedimento = recuperaProcedimento();
		// commentato su richiesta di stefania in data 06/11/2014
		/*
		 * boolean abilitazione = procedimentoValidation.validateAbilitaOperazioniProcura(procedimento,
		 * request); if (!abilitazione) { return "redirect:/procedimento/apri/copertinaProcedimento.htm"; }
		 */
		List<BeneForm> beniAssociati = new ArrayList<BeneForm>();
		long idProcedimento = ((ProcedimentoForm) (request.getSession()
				.getAttribute(Constants.ID_PROCEDIMENTO))).getIdProcedimento();
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");

		Object objTipoFascicolo = request.getSession().getAttribute(Constants.TIPO_FASCICOLO);
		int tipoFascicolo = 0;

		if (objTipoFascicolo != null) {
			if (objTipoFascicolo instanceof Integer) {
				tipoFascicolo = (Integer) objTipoFascicolo;
			} else if (objTipoFascicolo instanceof String) {
				tipoFascicolo = Integer.valueOf((String) objTipoFascicolo);
			}
		}

		// rimuovo eventuale lista nuovi beni (provenienti da Relazione amministratore)
		if (request.getSession().getAttribute("listaNuoviBeni") != null) {
			request.getSession().removeAttribute("listaNuoviBeni");
		}

		if (tipoFascicolo == Constants.TIPO_FASCICOLO_GESTIONE) {
			beniAssociati = beneService.listaBeniPerProcedimentoFascicoloGestione(0, idUfficioUtente,
					idProcedimento, beneForm);

			// beni senza idBene letti dall'xml della relazione dell'Amministratore
			List<BeneForm> beniNuovi = new ArrayList<BeneForm>();
			beniNuovi = flussiService.getListaNuoviBeniByIdProcFG(idProcedimento, idUfficioUtente, 'S');
			modelMap.addAttribute("listaNuoviBeni", beniNuovi);
			request.getSession().setAttribute("listaNuoviBeni", beniNuovi);

		} else {
			beniAssociati = beneService.listaBeniPerProcedimento(0, idUfficioUtente, idProcedimento,
					beneForm);
		}
		if (beniAssociati != null) {
			for (int i = 0; i < beniAssociati.size(); i++) {

				// List<BeneSoggettoForm> beniSoggetti =
				// beneService.listaSoggettiBeniProcSenzaIdAtto(idProcedimento, idUfficioUtente,
				// beniAssociati.get(i).getIdBene());
				// if (!((beniSoggetti == null) || beniSoggetti.isEmpty()))
				// beniAssociati.get(i).setTitoliDiDiritto("SI");

				if (tipoFascicolo == Constants.TIPO_FASCICOLO_GESTIONE) {
					String isBeneInIstanzaCamerale = beneService.isBeneInIstanzaCamerale(idProcedimento,
							idUfficioUtente, beniAssociati.get(i).getIdBene());
					beniAssociati.get(i).setIsBeneInIstanzaCamerale(isBeneInIstanzaCamerale);
				}

			}
		}
		modelMap.addAttribute("risultatiRicerca", beniAssociati);

		// Filippo non aggiunge il segnaposto se è un cambiopagina
		String cambiopagina = request.getParameter("cambiopagina");
		if (cambiopagina == null || "".equals(cambiopagina)) {
			// Giacomo 11/07/2013 Aggiunta Breadcrums
			addBreadcrumbs("GET", "/procedimento/bene/beniCollegati.htm", null);
		}

		return "/procedimento/bene/beniDelProcedimento";
	}
	
	public void controlloSuiBeni(long idProcedimento, long idCurrentAtto, int idUffOwn, HttpServletRequest request, BindingResult result, boolean confermaInvia) {
		List<BeneSoggettoForm> beni = procedimentoService.listaBeniAttoDecisorio(idProcedimento, idCurrentAtto, idUffOwn, 0, 0, 0, 0, 0);
		String categoriaAtto=((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCategoriaAtto().getId();
		if (categoriaAtto.equals(Constants.DECRETO_DECISORIO_I_GRADO))beneValidation.controlloDecisioneBeniNonDefiniti(beni, idUffOwn, idProcedimento, result, request, confermaInvia);
	}
	
	private boolean isCorrezioneErroreMateriale() {
		long idTipoUfficio = recuperaIdTipoUfficio();
		// se non sono tribunale
		if (!ComparatorUtil.containsValue(idTipoUfficio, Constants.TIPO_UFFICIO_TRIBUNALE)) {
			return false;
		}
		// se non ho l'atto oppure la categoria è diverso da ATTo_GENERICO e sottocategoria atto è diverso da
		// Correzione errore materiale
		// vuol dire che non sono nell'atto di correzione errore materiale
		AttoForm attoForm = recuperaAtto();
		if (attoForm == null) {
			return false;
		}
		if (attoForm.getTipoAtto() == null || !ComparatorUtil.containsValue(attoForm.getTipoAtto().getId(),
				TipoAtto.TIPO_ATTO_GENERICO)) {
			return false;
		}

		if (attoForm.getCategoriaAtto() == null
				|| !ComparatorUtil.containsValue(attoForm.getCategoriaAtto().getId(),
						CategoriaAtto.CAT_CORREZIONE_ERRORE_MATERIALE)) {
			return false;
		}

		return true;
	}
}
