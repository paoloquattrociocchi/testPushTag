package it.giustizia.smp.service.utility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import it.giustizia.smp.controller.BreadcrumbsController;
import it.giustizia.smp.dto.pg_cameralini.IntestatarioDto;
import it.giustizia.smp.form.AmministratoreForm;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.BeneForm;
import it.giustizia.smp.form.BeneStralcioForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.ImpugnazioneForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.form.SoggettoStralcioForm;
import it.giustizia.smp.model.TRelAttoIstGen;
import it.giustizia.smp.model.TRelAttoIstGenId;
import it.giustizia.smp.service.BeneService;
import it.giustizia.smp.service.cameralini.ProcedimentoServiceCam;
import it.giustizia.smp.service.utility.component.ComboUtilComponent;
import it.giustizia.smp.service.utility.component.SoggettiUtilComponent;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;

@Service
public class RicorsoUtilService extends BreadcrumbsController {

	@Autowired
	private ComboUtilComponent comboUtilComponent;
	@Autowired
	private Utility utility;
	@Autowired
	private SoggettiUtilComponent soggettiUtilComponent;
	@Autowired
	private ProcedimentoServiceCam procedimentoServiceCam;
	@Autowired
	private BeneService beneService;
	
	// MODIFICA 01/2014
	public void caricaRicorsoAppello(long idProcedimento, int idUffOwn, long idAtto, AttoForm attoForm, ModelMap modelMap, BindingResult result, HttpServletRequest request) {

		ProcedimentoForm procedimento = recuperaProcedimento();
		long tipoProc=Long.parseLong(procedimento.getTipoProcedimento().getId());
		
		List<SoggettoStralcioForm> listaRicorso = null;

		String flagValido = "R";
		
		int idTipoUfficio = (Integer) request.getSession().getAttribute("idTipoUfficio");
		String descUtente = recuperaIdCodiceUtente().toString();
		
		List<ComboForm> comboCda = null;
		if(idTipoUfficio == Constants.TIPO_UFFICIO_PROCURA_GENERALE)
			comboCda = commonService.getUfficiCompetenza(idProcedimento, Long.valueOf(Constants.TIPO_UFFICIO_CORTE_D_APPELLO));
		
		if (idTipoUfficio == Constants.TIPO_UFFICIO_CORTE_D_APPELLO || 
			(comboCda!=null && comboCda.size()>0 ))
			flagValido = "S";

		int catAtto = 0;
		if (attoForm.getCategoriaAtto() != null && attoForm.getCategoriaAtto().getId() != null)
			catAtto = Integer.parseInt(attoForm.getCategoriaAtto().getId());
		else
			catAtto = Integer.parseInt(((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCategoriaAtto().getId());

		// filtri
        if (!attoForm.isVisualizzaFiltro()) {
        	attoForm.setTipoMisure(null);
        	attoForm.setSoggettiDirettiFiltro(null);
        	attoForm.setSoggettiTitolariFiltro(null);
        	attoForm.setTipologiaBene(null);
        	attoForm.setCategoriaBene(null);
        	attoForm.setSottoCategoriaBene(null);
		} 

		if (attoForm.isVisualizzaFiltro()) {
			modelMap.addAttribute("categorie", comboUtilComponent.getCategorieBene(attoForm));
			modelMap.addAttribute("sottocategorie", comboUtilComponent.getSottoCategorieBene(attoForm));
			// carica combo soggetti diretti e titolari se filtro = true
			List<SoggettoForm> soggettiDiretti = attoService.getRicercaSoggettoAtto(idProcedimento, idAtto, idUffOwn, Constants.ID_RUOLO_DIRETTO, Constants.Flag.S);
   			if (soggettiDiretti != null ) {
   				if (Utility.isComboFormSelected(attoForm.getTipoMisure())) {
   					String tipoMisura = attoForm.getTipoMisure().getId();
   					if (tipoMisura.equals(Constants.TipoMisura.PERSONALE_ID))
   						soggettiDiretti = soggettiUtilComponent.filtraSoloSoggettiMisPers(soggettiDiretti, idProcedimento, idAtto, idUffOwn);
   					if (tipoMisura.equals(Constants.TipoMisura.PATRIMONIALE_ID))
   						soggettiDiretti = soggettiUtilComponent.filtraSoloSoggettiMisPatr(soggettiDiretti, idProcedimento, idAtto, idUffOwn);
   				}
   				modelMap.addAttribute("soggettiDirettiFiltro", utility.creaComboSoggetti(soggettiDiretti));
   			}
			List<SoggettoForm> soggettiTitolari = attoService.getRicercaSoggettoAtto(idProcedimento, idAtto, idUffOwn, Constants.ID_RUOLO_TITOLARI, Constants.Flag.S);
   			if (soggettiTitolari != null ) {
   				modelMap.addAttribute("soggettiTitolariFiltro", utility.creaComboSoggetti(soggettiTitolari));
   			}
   			List<ComboForm> tipoMisure = new ArrayList<ComboForm>();
   			tipoMisure.add(new ComboForm(Constants.TipoMisura.PATRIMONIALE_ID, Constants.TipoMisura.PATRIMONIALE_DESCR));
   			tipoMisure.add(new ComboForm(Constants.TipoMisura.PERSONALE_ID, Constants.TipoMisura.PERSONALE_DESCR));
   			modelMap.addAttribute("tipoMisure", tipoMisure);
		}

		if (tipoProc == Constants.TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM) {
			SoggettoStralcioForm soggettoStralcio=new SoggettoStralcioForm();
			soggettoStralcio.setAmministratore("pippo");
			listaRicorso=new ArrayList<SoggettoStralcioForm>();	
			listaRicorso.add(soggettoStralcio);
			List<IntestatarioDto> listaIntestatari = procedimentoServiceCam.getIntestatari(idUffOwn, idProcedimento);
			modelMap.addAttribute("listaIntestatari", listaIntestatari);

			List<AttoForm> idsAttoIstanza = null;
			if (catAtto == Integer.valueOf(Constants.RICORSO_IN_CASSAZIONE))
				idsAttoIstanza = attoService.getAttiByProcedimento(idProcedimento, 4l, Constants.RICORSO_IN_APPELLO);
			else
				idsAttoIstanza = attoService.getAttiByProcedimento(idProcedimento, 5l, Constants.ISTANZA_LIQUIDAZIONE_AMM);
			
			
			
			if(idsAttoIstanza!=null && !idsAttoIstanza.isEmpty()) {
				AttoForm af = idsAttoIstanza.get(0);
				AmministratoreForm amministratore = null;
				
				if (catAtto == Integer.valueOf(Constants.RICORSO_IN_CASSAZIONE))
					amministratore = procedimentoService.getAmministratoreByImpugnazione(idProcedimento, af.getCodAtto(), idUffOwn);
				else
					amministratore = procedimentoService.getAmministratoreByIstanza(idProcedimento, af.getCodAtto(), idUffOwn);
				
				List<BeneForm>beniInAmministrazione = beneService.listaBeniInAmministrazione(idUffOwn, idProcedimento, af.getCodAtto(), amministratore.getId());
				modelMap.addAttribute("beniInAmministrazione", beniInAmministrazione);
			    TRelAttoIstGenId id = new TRelAttoIstGenId();
				id.setCodiKIdAtto(idAtto);
				id.setCodiKIdProc(idProcedimento);
				id.setCodiKIdUffOwner(idUffOwn);
				TRelAttoIstGen relAttoIstGen = beneService.getDecisorioBeniIstGen(id);
				if(relAttoIstGen!=null)
					if(relAttoIstGen.getTTpEsitoByCodiFkIdEsito1()!=null) {
						ComboForm cb = new ComboForm();
						cb.setId(""+relAttoIstGen.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito());
						attoForm.setModalitaTrasmissione(cb);
					}
				
				List<ComboForm> esiti = procedimentoService.getEsiti("LA", Long.parseLong(Constants.DECRETO_DECISORIO_I_GRADO_CAMERE), 0);
				modelMap.addAttribute("esitiDecisioneBeni", esiti);
		 }
			
			modelMap.addAttribute("listaRicorso", listaRicorso);
		}else if (tipoProc == Constants.TIPOPROC_CORREZIONE_ERRORI_CAM) {
			SoggettoStralcioForm soggettoStralcio=new SoggettoStralcioForm();
			soggettoStralcio.setAmministratore("mock");
			listaRicorso=new ArrayList<SoggettoStralcioForm>();	
			listaRicorso.add(soggettoStralcio);
			
			List<IntestatarioDto> listaIntestatari = procedimentoServiceCam.getIntestatari(idUffOwn, idProcedimento);
			modelMap.addAttribute("listaIntestatari", listaIntestatari);
	
			
			
			SoggettoForm soggettoDaCorregere = null;
			if(listaIntestatari != null && !listaIntestatari.isEmpty()){
				 Iterator<IntestatarioDto> iter = listaIntestatari.iterator(); 
				 IntestatarioDto intestatario = iter.next();
				 soggettoDaCorregere = procedimentoServiceCam.getSoggettoDaCorregere(intestatario.getCODI_FK_ID_SOGG_INT().longValue(), intestatario.getCODI_FK_ID_VER().longValue(), intestatario.getCODI_FK_ID_UFF_OWNER().intValue());
			}
			
			modelMap.addAttribute("soggettoDaCorregere", soggettoDaCorregere);

			   TRelAttoIstGenId id = new TRelAttoIstGenId();
				id.setCodiKIdAtto(idAtto);
				id.setCodiKIdProc(idProcedimento);
				id.setCodiKIdUffOwner(idUffOwn);
				TRelAttoIstGen relAttoIstGen = beneService.getDecisorioBeniIstGen(id);
				if(relAttoIstGen!=null)
					if(relAttoIstGen.getTTpEsitoByCodiFkIdEsito1()!=null) {
						ComboForm cb = new ComboForm();
						cb.setId(""+relAttoIstGen.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito());
						attoForm.setModalitaTrasmissione(cb);
						
					}
				
					List<ComboForm> esiti = procedimentoService.getEsiti("EM", Long.parseLong(Constants.DECRETO_DECISORIO_I_GRADO_CAMERE), 0);
					modelMap.addAttribute("esitiDecisione", esiti);
					modelMap.addAttribute("listaRicorso", listaRicorso);
			
		} else {		
			listaRicorso = attoService.getRicercaMisureAtto(idProcedimento, idUffOwn, attoForm, flagValido, catAtto);
			modelMap.addAttribute("listaRicorso", listaRicorso);
			
			//verifica aggiunta soggetti intervenienti
			boolean intervenienti = false;
			if( (""+tipoProc).equals(Constants.TIPOPROC_PATRIMONIALE) || (""+tipoProc).equals(Constants.TIPOPROC_PERSONALE_E_PATRIMONIALE) ||
					(""+tipoProc).equals(Constants.TIPOPROC_GESTIONE_BENI)) {
				
				if(listaRicorso!=null && !listaRicorso.isEmpty()) {
					for(int i=0; i<listaRicorso.size(); i++) {
						SoggettoStralcioForm rec = listaRicorso.get(i);
						if(rec.getListaBeneStralcioForm()!=null && !rec.getListaBeneStralcioForm().isEmpty()) {
							intervenienti = true;
							for(int j=0; j<rec.getListaBeneStralcioForm().size();j++) {
								BeneStralcioForm recRec = rec.getListaBeneStralcioForm().get(j);
								String tipoMisura = recRec.getTipoMisura();
								if( !(tipoMisura!=null && tipoMisura.equals("PATR")) ) intervenienti = false;
							}
						}					
					}
				}
			}
			modelMap.addAttribute("intervenienti", intervenienti);
			//			
		}
		// attributo utilizzato per rendere disabled i checkbox nel riepilogo
		// dello stralcio
		modelMap.addAttribute("riepilogoRicorso", "disabled");
		
		if (tipoProc == Constants.TIPOPROC_TERZO_INTERVENIENTE_CAM) {
			List<SoggettoForm> soggettiIntervenienti = procedimentoService.soggettiDirettiInteressatiDelProcedimento(idProcedimento, Constants.RUOLO_INTERVENTIENTE, Constants.Flag.S);
			modelMap.addAttribute("listaIntervenienti", soggettiIntervenienti);
		}		

		if (Long.valueOf(recuperaIdTipoUfficio()) != Constants.TIPO_UFFICIO_PROCURA_GENERALE
				&& Long.valueOf(recuperaIdTipoUfficio()) != Long.valueOf(Constants.TIPO_UFFICIO_PROCURA)
				|| attoForm.getStatoAtto().getId().equals("" + Constants.STATOATTO_REGISTRATO)) {

			ImpugnazioneForm impugnazione = attoService.getImpugnazioneByIdAttoUfficioOwner(idAtto, idUffOwn);
		
			//attoService.getTRelSoggImpByIdImpugnazioneIdOwner(idAtto, idUffOwn, impugnazione);
			
			attoForm.setImpugnazione(impugnazione);

			modelMap.addAttribute("modalitaPervenimento", commonService.listaTipoModPerv());

			// tipo deposito
			if (attoForm.getImpugnazione().getTipoDeposito() != null && attoForm.getImpugnazione().getTipoDeposito().getId() != null
					&& attoForm.getImpugnazione().getTipoDeposito().getId().equals("A"))
				modelMap.addAttribute("altriUffici", commonService.getElencoUfficiDistretto(idUffOwn));

			// per la tendina dei diretti interessati

			if (attoForm.getImpugnazione().getParte() != null && attoForm.getImpugnazione().getParte().getId() != null
					&& attoForm.getImpugnazione().getParte().getId().equals("D")) {
				List<SoggettoForm> soggettiDirettiInteressati = attoService.getRicercaSoggettoRicorso(idProcedimento, idAtto, idUffOwn,
						Constants.ID_RUOLO_DIRETTO);
				modelMap.addAttribute("soggettiDiretti", soggettiDirettiInteressati);
			}
			// per la tendina dei terzi
			if (attoForm.getImpugnazione().getParte() != null && attoForm.getImpugnazione().getParte().getId() != null
					&& attoForm.getImpugnazione().getParte().getId().equals("T")) {
				List<SoggettoForm> soggettiTitolari = attoService.getRicercaSoggettoRicorso(idProcedimento, idAtto, idUffOwn, Constants.ID_RUOLO_TERZO);
				modelMap.addAttribute("soggettiTitolari", soggettiTitolari);
			}
			
			if (attoForm.getImpugnazione().getParte() != null && attoForm.getImpugnazione().getParte().getId() != null
					&& (attoForm.getImpugnazione().getParte().getId().equals("A") || attoForm.getImpugnazione().getParte().getId().equals("E")))
				//List<DifensoreForm> listaAvvocati = attoService.getRicercaAvvRicorso(idProcedimento,idAtto, idUffOwn);	
				//modelMap.addAttribute("avvocatiDifensori", listaAvvocati);
				if ((attoForm.getImpugnazione().getAvvocatiDifensori() != null)
						&& (attoForm.getImpugnazione().getAvvocatiDifensori().getId() != null)
						&& (!attoForm.getImpugnazione().getAvvocatiDifensori().getId().equals("-1"))) {
					//List<SoggettoForm> soggetti = attoService.getSoggettiDifensori(idUffOwn, idProcedimento,
					//		Long.parseLong(attoForm.getImpugnazione().getAvvocatiDifensori().getId()));
					List<SoggettoForm> soggetti = attoService.getSoggettiDifensoriRicorso(idProcedimento,attoForm.getCodAtto(), idUffOwn, Long.parseLong(attoForm.getImpugnazione().getAvvocatiDifensori().getId()), null, null, descUtente);
					if ((soggetti != null) && (soggetti.size() > 0)) {
						List<ComboForm> soggettiDifensori = utility.creaComboSoggetti(soggetti);
						modelMap.addAttribute("soggettiDifensori", soggettiDifensori);

					}
				} else // nel caso di procura in cui non c'e' l'id avvocato
				if (attoForm.getImpugnazione().getDescCognAvv() != null && !attoForm.getImpugnazione().getDescCognAvv().equals("")) {
					//List<SoggettoForm> soggetti = attoService.getSoggettiDifensoriProcura(idUffOwn, idProcedimento);
					List<SoggettoForm> soggetti = attoService.getSoggettiDifensoriRicorso(idProcedimento,attoForm.getCodAtto(), idUffOwn,0l, attoForm.getImpugnazione().getDescCognAvv(), attoForm.getImpugnazione().getDescNomeAvv(), descUtente);

					if ((soggetti != null) && (soggetti.size() > 0)) {
						List<ComboForm> soggettiDifensori = utility.creaComboSoggetti(soggetti);
						

						modelMap.addAttribute("soggettiDifensori", soggettiDifensori);

					}
				}
			
			if (attoForm.getImpugnazione() != null && attoForm.getImpugnazione().getParte() != null && attoForm.getImpugnazione().getParte().getId() != null && attoForm.getImpugnazione().getParte().getId().equals("N")) {
				List<SoggettoForm> soggettiIntervenienti = procedimentoService.soggettiDirettiInteressatiDelProcedimento(idProcedimento, Constants.RUOLO_INTERVENTIENTE, Constants.Flag.S);
				if(soggettiIntervenienti!=null && !soggettiIntervenienti.isEmpty()) {
					for(int j=0;j<soggettiIntervenienti.size();j++) {
						SoggettoForm inv = soggettiIntervenienti.get(j);
						if(inv.getIdSoggetto() != attoForm.getImpugnazione().getIdSoggInv()) {
							soggettiIntervenienti.remove(j);
							j--;
						}
					}
				}
				modelMap.addAttribute("soggettiDiretti", soggettiIntervenienti);
			}	
			
			if (attoForm.getImpugnazione() != null && attoForm.getImpugnazione().getParte() != null &&  attoForm.getImpugnazione().getParte().getId() != null && "I".equals(attoForm.getImpugnazione().getParte().getId())) {
				
				List<IntestatarioDto> listaIntestatari = procedimentoServiceCam.getIntestatari(idUffOwn, idProcedimento);
				modelMap.addAttribute("listaIntestatari", listaIntestatari);
				
			}			
		}
	}

		
    /**
     * caricaRicorsoCassazione
     * @param idProcedimento
     * @param idUffOwn
     * @param idAtto
     * @param attoForm
     * @param modelMap
     */
	public void caricaRicorsoCassazione(long idProcedimento, int idUffOwn, long idAtto, AttoForm attoForm, ModelMap modelMap, HttpServletRequest request) {
		
		ProcedimentoForm procedimento = recuperaProcedimento();
		long tipoProc=Long.parseLong(procedimento.getTipoProcedimento().getId());
		
		List<SoggettoStralcioForm> listaRicorso = null;

		String flagValido = "R";
		request.getSession().getAttribute("idTipoUfficio");
		String descUtente = recuperaIdCodiceUtente().toString();
		//da rivedere nel momento che avremo il servizio ufficio cassazione non esiste 
	//	if (idTipoUfficio == Constants.TIPO_UFFICIO_CORTE_D_APPELLO)
	//		flagValido = "S";

		int catAtto = 0;
		if (attoForm.getCategoriaAtto() != null && attoForm.getCategoriaAtto().getId() != null)
			catAtto = Integer.parseInt(attoForm.getCategoriaAtto().getId());
		else
			catAtto = Integer.parseInt(((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCategoriaAtto().getId());

		// filtri
        if (!attoForm.isVisualizzaFiltro()) {
        	attoForm.setTipoMisure(null);
        	attoForm.setSoggettiDirettiFiltro(null);
        	attoForm.setSoggettiTitolariFiltro(null);
        	attoForm.setTipologiaBene(null);
        	attoForm.setCategoriaBene(null);
        	attoForm.setSottoCategoriaBene(null);
		} 

		if (attoForm.isVisualizzaFiltro()) {
			modelMap.addAttribute("categorie", comboUtilComponent.getCategorieBene(attoForm));
			modelMap.addAttribute("sottocategorie", comboUtilComponent.getSottoCategorieBene(attoForm));
			// carica combo soggetti diretti e titolari se filtro = true
			List<SoggettoForm> soggettiDiretti = attoService.getRicercaSoggettoAtto(idProcedimento, idAtto, idUffOwn, Constants.ID_RUOLO_DIRETTO, Constants.Flag.S);
   			if (soggettiDiretti != null ) {
   				modelMap.addAttribute("soggettiDirettiFiltro", utility.creaComboSoggetti(soggettiDiretti));
   			}
			List<SoggettoForm> soggettiTitolari = attoService.getRicercaSoggettoAtto(idProcedimento, idAtto, idUffOwn, Constants.ID_RUOLO_TITOLARI, Constants.Flag.S);
   			if (soggettiTitolari != null ) {
   				modelMap.addAttribute("soggettiTitolariFiltro", utility.creaComboSoggetti(soggettiTitolari));
   			}
   			List<ComboForm> tipoMisure = new ArrayList<ComboForm>();
   			tipoMisure.add(new ComboForm(Constants.TipoMisura.PATRIMONIALE_ID, Constants.TipoMisura.PATRIMONIALE_DESCR));
   			tipoMisure.add(new ComboForm(Constants.TipoMisura.PERSONALE_ID, Constants.TipoMisura.PERSONALE_DESCR));
   			modelMap.addAttribute("tipoMisure", tipoMisure);
		}

		if (tipoProc == Constants.TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM) {
			SoggettoStralcioForm soggettoStralcio=new SoggettoStralcioForm();
			soggettoStralcio.setAmministratore("pippo");
			listaRicorso=new ArrayList<SoggettoStralcioForm>();	
			listaRicorso.add(soggettoStralcio);
			List<IntestatarioDto> listaIntestatari = procedimentoServiceCam.getIntestatari(idUffOwn, idProcedimento);
			modelMap.addAttribute("listaIntestatari", listaIntestatari);
			
			List<AttoForm> idsAttoIstanza = null;
			if (catAtto == Integer.valueOf(Constants.RICORSO_IN_CASSAZIONE))
				idsAttoIstanza = attoService.getAttiByProcedimento(idProcedimento, 4l, Constants.RICORSO_IN_APPELLO);
			else
				idsAttoIstanza = attoService.getAttiByProcedimento(idProcedimento, 5l, Constants.ISTANZA_LIQUIDAZIONE_AMM);
			if(idsAttoIstanza!=null && !idsAttoIstanza.isEmpty()) {
				AttoForm af = idsAttoIstanza.get(0);
				AmministratoreForm amministratore = null;
				if (catAtto == Integer.valueOf(Constants.RICORSO_IN_CASSAZIONE))
					amministratore = procedimentoService.getAmministratoreByImpugnazione(idProcedimento, af.getCodAtto(), idUffOwn);
				else
					amministratore = procedimentoService.getAmministratoreByIstanza(idProcedimento, af.getCodAtto(), idUffOwn);
				List<BeneForm>beniInAmministrazione = beneService.listaBeniInAmministrazione(idUffOwn, idProcedimento, af.getCodAtto(), amministratore.getId());
				modelMap.addAttribute("beniInAmministrazione", beniInAmministrazione);
				
			    TRelAttoIstGenId id = new TRelAttoIstGenId();
				id.setCodiKIdAtto(idAtto);
				id.setCodiKIdProc(idProcedimento);
				id.setCodiKIdUffOwner(idUffOwn);
				TRelAttoIstGen relAttoIstGen = beneService.getDecisorioBeniIstGen(id);
				if(relAttoIstGen!=null)
					if(relAttoIstGen.getTTpEsitoByCodiFkIdEsito2()!=null) {
						ComboForm cb = new ComboForm();
						cb.setId(""+relAttoIstGen.getTTpEsitoByCodiFkIdEsito2().getCodiKIdEsito());
						attoForm.setModalitaTrasmissione(cb);
					}
				
				List<ComboForm> esiti = null;
				if (catAtto == Integer.valueOf(Constants.RICORSO_IN_CASSAZIONE))
					esiti = procedimentoService.getEsiti("LA", Long.parseLong(Constants.DECRETO_DECISORIO_II_GRADO_CAM), 0);
				else
					esiti = procedimentoService.getEsiti("LA", Long.parseLong(Constants.DECRETO_DECISORIO_I_GRADO_CAMERE), 0);
				modelMap.addAttribute("esitiDecisioneBeni", esiti);
			}
		} else {	
			listaRicorso = attoService.getRicercaMisureAtto(idProcedimento, idUffOwn, attoForm, flagValido, catAtto);
			modelMap.addAttribute("listaRicorso", listaRicorso);
		}

		if (tipoProc == Constants.TIPOPROC_TERZO_INTERVENIENTE_CAM) {
			List<SoggettoForm> soggettiIntervenienti = procedimentoService.soggettiDirettiInteressatiDelProcedimento(idProcedimento, Constants.RUOLO_INTERVENTIENTE, Constants.Flag.S);
			modelMap.addAttribute("listaIntervenienti", soggettiIntervenienti);
		}
		
		//verifica aggiunta soggetti intervenienti
		boolean intervenienti = false;
		if( (""+tipoProc).equals(Constants.TIPOPROC_PATRIMONIALE) || (""+tipoProc).equals(Constants.TIPOPROC_PERSONALE_E_PATRIMONIALE) ||
				(""+tipoProc).equals(Constants.TIPOPROC_GESTIONE_BENI)) {
			
			if(listaRicorso!=null && !listaRicorso.isEmpty()) {
				for(int i=0; i<listaRicorso.size(); i++) {
					SoggettoStralcioForm rec = listaRicorso.get(i);
					if(rec.getListaBeneStralcioForm()!=null && !rec.getListaBeneStralcioForm().isEmpty()) {
						intervenienti = true;
						for(int j=0; j<rec.getListaBeneStralcioForm().size();j++) {
							BeneStralcioForm recRec = rec.getListaBeneStralcioForm().get(j);
							String tipoMisura = recRec.getTipoMisura();
							if( !(tipoMisura!=null && tipoMisura.equals("PATR")) ) intervenienti = false;
						}
					}					
				}
			}
		}
		modelMap.addAttribute("intervenienti", intervenienti);
		//			

		// attributo utilizzato per rendere disabled i checkbox nel riepilogo
		// dello stralcio
		modelMap.addAttribute("riepilogoRicorso", "disabled");

		if (Long.valueOf(recuperaIdTipoUfficio()) != Constants.TIPO_UFFICIO_PROCURA_GENERALE
				|| attoForm.getStatoAtto().getId().equals("" + Constants.STATOATTO_REGISTRATO)) {

			ImpugnazioneForm impugnazione = attoService.getImpugnazioneByIdAttoUfficioOwner(idAtto, idUffOwn);
			
			//attoService.getTRelSoggImpByIdImpugnazioneIdOwner(idAtto, idUffOwn, impugnazione);
			
			
			attoForm.setImpugnazione(impugnazione);

			modelMap.addAttribute("modalitaPervenimento", commonService.listaTipoModPerv());

			// tipo deposito
			if (attoForm.getImpugnazione().getTipoDeposito() != null && attoForm.getImpugnazione().getTipoDeposito().getId() != null
					&& attoForm.getImpugnazione().getTipoDeposito().getId().equals("A"))
				modelMap.addAttribute("altriUffici", commonService.getElencoUfficiDistretto(idUffOwn));

			// per la tendina dei diretti interessati

			if (attoForm.getImpugnazione().getParte() != null && attoForm.getImpugnazione().getParte().getId() != null
					&& attoForm.getImpugnazione().getParte().getId().equals("D")) {
				List<SoggettoForm> soggettiDirettiInteressati = attoService.getRicercaSoggettoRicorso(idProcedimento, idAtto, idUffOwn,Constants.ID_RUOLO_DIRETTO);
				modelMap.addAttribute("soggettiDiretti", soggettiDirettiInteressati);
			}
			// per la tendina dei terzi
			if (attoForm.getImpugnazione().getParte() != null && attoForm.getImpugnazione().getParte().getId() != null
					&& attoForm.getImpugnazione().getParte().getId().equals("T")) {
				List<SoggettoForm> soggettiTitolari = attoService.getRicercaSoggettoRicorso(idProcedimento, idAtto, idUffOwn, Constants.ID_RUOLO_TERZO);
				modelMap.addAttribute("soggettiTitolari", soggettiTitolari);
			}
			
			if (attoForm.getImpugnazione() != null && attoForm.getImpugnazione().getParte() != null && attoForm.getImpugnazione().getParte().getId() != null && attoForm.getImpugnazione().getParte().getId().equals("N")) {
				List<SoggettoForm> soggettiIntervenienti = procedimentoService.soggettiDirettiInteressatiDelProcedimento(idProcedimento, Constants.RUOLO_INTERVENTIENTE, Constants.Flag.S);
				if(soggettiIntervenienti!=null && !soggettiIntervenienti.isEmpty()) {
					for(int j=0;j<soggettiIntervenienti.size();j++) {
						SoggettoForm inv = soggettiIntervenienti.get(j);
						if(inv.getIdSoggetto() != attoForm.getImpugnazione().getIdSoggInv()) {
							soggettiIntervenienti.remove(j);
							j--;
						}
					}
				}
				modelMap.addAttribute("soggettiDiretti", soggettiIntervenienti);
			}
			
			if (attoForm.getImpugnazione().getParte() != null && attoForm.getImpugnazione().getParte().getId() != null
					&& (attoForm.getImpugnazione().getParte().getId().equals("A") || attoForm.getImpugnazione().getParte().getId().equals("E")))
				if ((attoForm.getImpugnazione().getAvvocatiDifensori() != null)
						&& (attoForm.getImpugnazione().getAvvocatiDifensori().getId() != null)
						&& (!attoForm.getImpugnazione().getAvvocatiDifensori().getId().equals("-1"))) {
					//List<SoggettoForm> soggetti = attoService.getSoggettiDifensori(idUffOwn, idProcedimento,
					//		Long.parseLong(attoForm.getImpugnazione().getAvvocatiDifensori().getId()));
					List<SoggettoForm> soggetti = attoService.getSoggettiDifensoriRicorso(idProcedimento,attoForm.getCodAtto(), idUffOwn, Long.parseLong(attoForm.getImpugnazione().getAvvocatiDifensori().getId()), null, null, descUtente);

					if ((soggetti != null) && (soggetti.size() > 0)) {
						List<ComboForm> soggettiDifensori = utility.creaComboSoggetti(soggetti);
						modelMap.addAttribute("soggettiDifensori", soggettiDifensori);

					}
				} else // nel caso di procura in cui non c'e' l'id avvocato
				if (attoForm.getImpugnazione().getDescCognAvv() != null && !attoForm.getImpugnazione().getDescCognAvv().equals("")) {
					//List<SoggettoForm> soggetti = attoService.getSoggettiDifensoriProcura(idUffOwn, idProcedimento);
					List<SoggettoForm> soggetti = attoService.getSoggettiDifensoriRicorso(idProcedimento,attoForm.getCodAtto(), idUffOwn, Long.parseLong(attoForm.getImpugnazione().getAvvocatiDifensori().getId()), null, null, descUtente);

					if ((soggetti != null) && (soggetti.size() > 0)) {
						List<ComboForm> soggettiDifensori = utility.creaComboSoggetti(soggetti);
						modelMap.addAttribute("soggettiDifensori", soggettiDifensori);

					}
				}

		}
	}
}
