package it.giustizia.smp.service.utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import it.giustizia.smp.controller.BreadcrumbsController;
import it.giustizia.smp.dto.pg_cameralini.IntestatarioDto;
import it.giustizia.smp.form.AmministratoreForm;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.BeneForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.DifensoreForm;
import it.giustizia.smp.form.NumeroRegistroForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.form.SoggettoStralcioForm;
import it.giustizia.smp.model.TAvvocati;
import it.giustizia.smp.service.BeneService;
import it.giustizia.smp.service.cameralini.ProcedimentoServiceCam;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;

@Service
public class IstanzaUtilService extends BreadcrumbsController {

	@Autowired
	private ProcedimentoServiceCam procedimentoServiceCam;
	@Autowired
	private BeneService beneService;
	@Autowired
	private Utility utility;
	
	public String correzioneErroriModIstanza(AttoForm attoForm, Long idProcedimento) {
		String errore = null;
		
		if(attoForm.getCategoriaAtto()!=null && attoForm.getCategoriaAtto().getId()!=null && attoForm.getCategoriaAtto().getId().equals(Constants.ISTANZA_CORREZIONE_ERRORI_MATERIALI)) {
			int idUffOwn = recuperaIdUfficioUtente();
			List<IntestatarioDto> listaIntestatari = procedimentoServiceCam.getIntestatari(idUffOwn, idProcedimento.longValue());
			IntestatarioDto intestatario = listaIntestatari.get(0);
			String nome = null;
			String cognome = null;
			String sesso = null;
			Date dataNasc = null;
			String idStatoNasc = null;
			Long   idComNasc = null;
			String codiceFiscale = null;
			String ragioneSociale = null;
			String comuneEsteroNasc = null;
			String comuneEstero = null;
			String partitaIva = null;
			Long   idStatoSede  = null;
			Long   idComSed = null;
			if ("F".equals(intestatario.getCODI_FK_ID_TIPO_SOGG().toString())) {
				nome = attoForm.getSoggetto().getNome();
				cognome = attoForm.getSoggetto().getCognome();
				sesso = attoForm.getSoggetto().getSesso();
				dataNasc = attoForm.getSoggetto().getDataNascita();
				idStatoNasc = attoForm.getSoggetto().getStatoNascita().getId();
				codiceFiscale = attoForm.getSoggetto().getCodiceFiscale();

				if(!"-1".equalsIgnoreCase(idStatoNasc)){ 
					if("1".equalsIgnoreCase(idStatoNasc)){
						idComNasc = attoForm.getSoggetto().getComuneNascita().getId() ==null ? null : new Long(attoForm.getSoggetto().getComuneNascita().getId());
					}else {
						comuneEsteroNasc = attoForm.getSoggetto().getDescrizioneComuneEsteroNascita();
					}
				}
				
			} else {
				ragioneSociale = attoForm.getSoggetto().getRagioneSociale();
				partitaIva = attoForm.getSoggetto().getPartitaIva();
				idStatoSede  = attoForm.getSoggetto().getStatoSedeLegale().getId()==null ? null : new Long(attoForm.getSoggetto().getStatoSedeLegale().getId());

				if(idStatoSede>0){ 
					if(idStatoSede == 1l){
						idComSed =  attoForm.getSoggetto().getComuneSedeLegale().getId() ==null ? null : new Long(attoForm.getSoggetto().getComuneSedeLegale().getId());
					}else if(attoForm.getSoggetto() != null && attoForm.getSoggetto().getDescrizioneComuneEsteroSedeLegale() != null) {
						comuneEstero = attoForm.getSoggetto().getDescrizioneComuneEsteroSedeLegale();
					}
				}
				
			}
			Long	tipoStrada   = intestatario.getCODI_FK_ID_TIPO_STRADA()==null ? null : new Long(intestatario.getCODI_FK_ID_TIPO_STRADA().toString());
			Long	idTipoSoc	 = intestatario.getCODI_FK_ID_TIPO_SOC()==null ? null : new Long(intestatario.getCODI_FK_ID_TIPO_SOC().toString());
			Long	idSoggInt	 = intestatario.getCODI_FK_ID_SOGG_INT()==null ? null : new Long(intestatario.getCODI_FK_ID_SOGG_INT().toString());
			Long	idVer		 = intestatario.getCODI_FK_ID_VER()==null ? null : new Long(intestatario.getCODI_FK_ID_VER().toString());
				
			procedimentoServiceCam.updateIntestatario(idProcedimento.longValue(), idUffOwn, 19, 
					intestatario.getCODI_K_ID_INTESTATARIO(), intestatario.getCODI_FK_ID_TIPO_SOGG().toString(), nome, cognome, sesso, 
					dataNasc, comuneEsteroNasc, codiceFiscale, intestatario.getDESC_COD_FISC(),  
					ragioneSociale, comuneEstero, intestatario.getDESC_INDIRIZZO_SEDE(), 
					partitaIva, idUffOwn, intestatario.getDESC_NUME_CCIAA(), intestatario.getDESC_CIVICO(), intestatario.getDESC_CAP(),
					tipoStrada,idComSed,idComNasc,idStatoSede,idTipoSoc,idTipoSoc,idSoggInt,idVer,recuperaIdCodiceUtente());
		}
		
		return errore;
	}
	
	public ModelAndView istanzaModificaPersonali(@Valid @ModelAttribute("attoForm") AttoForm attoForm, BindingResult result, HttpServletRequest request,
			ModelMap modelMap) {
		// addBreadcrumbs("POST", "/procedimento/atto/proposta.htm", null);
		ModelAndView mv = new ModelAndView("/procedimento/atto/istanzaModificaPersonali");
		int idUffOwn = recuperaIdUfficioUtente();
		long idAtto = attoForm.getCodAtto();
		String descUtente = (recuperaIdCodiceUtente().toString());
		ProcedimentoForm procedimento = recuperaProcedimento();
		
		//cambio i valori della tipologia di prevenzione in sessione
		updateProcedimentoIntoSession(procedimento.getIdProcedimento());
		procedimento = recuperaProcedimento();
		//
		
		long idProcedimento = procedimento.getIdProcedimento();
		recuperaProfilo();

		if (procedimento.getTipoProcedimento() != null)
			Long.valueOf(procedimento.getTipoProcedimento().getId());
		if (result==null || !result.hasErrors())
			attoForm = procedimentoService.dettaglioPropostaAtto(idUffOwn, idProcedimento, attoForm.getCodAtto(), false);
		
		boolean aggravamento = false;
		
		getIdTipoFascicolo(false);
		request.getSession().getAttribute(Constants.ID_TIPO_UFFICIO);
		
		attoForm.setIdProcedimento(idProcedimento);
		attoForm.setIdUfficio(idUffOwn);
		
		//	soggetti = procedimentoService.soggettiDirettiInteressatiDelProcedimento(idProcedimento, null, Constants.Flag.S);
		//	modelMap.addAttribute("soggettiInteressati", soggetti);
			aggravamento = true;

		attoForm.setAttoPerAggravamento(aggravamento);
		
		
		
		
		
		//if (attoForm != null && attoForm.getDataRegistrazione() != null
		//		&& !attoForm.getStatoAtto().getId().equals(Constants.STATOATTO_IN_LAVORAZIONE_S))
			
		//	attoForm = attoUtilService.caricaAttoInSessione(idUffOwn, attoForm.getCodAtto(), recuperaIdCodiceUtente(), idProcedimento, request, attoForm);

		//attoValidation.validateProposta(attoForm, procedimento, null, null, null, soggetti, result, aggravamento, request,
		//		modelMap, idUffOwn);
		modelMap.addAttribute("attoForm", attoForm);

		attoForm.setAttoPerAggravamento(aggravamento);
		
        List<SoggettoStralcioForm> listaRicorso = null;		
		int catAtto = 0;
		if (attoForm.getCategoriaAtto() != null && attoForm.getCategoriaAtto().getId() != null)
			catAtto = Integer.parseInt(attoForm.getCategoriaAtto().getId());
		else
			catAtto = Integer.parseInt(((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCategoriaAtto().getId());

		
		if(attoForm.getCategoriaAtto()!=null && attoForm.getCategoriaAtto().getId()!=null && !attoForm.getCategoriaAtto().getId().equals(Constants.ISTANZA_LIQUIDAZIONE_AMM))
			listaRicorso = attoService.getRicercaMisureIstanza(idProcedimento, idUffOwn, attoForm, null, catAtto);
		else if (attoForm.getStatoAtto().getId().equals("" + Constants.STATOATTO_REGISTRATO) ||
				attoForm.getStatoAtto().getId().equals("" + Constants.STATOATTO_ARCHIVIATO)	) {

			AmministratoreForm amministratore = procedimentoService.getAmministratoreByIstanza(idProcedimento, attoForm.getCodAtto(), idUffOwn);
			List<BeneForm>beniInAmministrazione = beneService.listaBeniInAmministrazione(idUffOwn, idProcedimento, attoForm.getCodAtto(), amministratore.getId());
			modelMap.addAttribute("beniInAmministrazione", beniInAmministrazione);
			
		} else {
			Long idProcedimentoPadre = procedimentoService.getIdPadreAggravamento(procedimento.getIdProcedimento());
			AmministratoreForm amministratore = procedimentoService.getAmministratoreByIstanza(idProcedimento, attoForm.getCodAtto(), idUffOwn);
			if (amministratore != null) {
				//
				List<BeneForm> beniInAmministrazione = new ArrayList<BeneForm>();
				if ((Integer) request.getSession().getAttribute("idTipoUfficio") == Constants.TIPO_UFFICIO_CORTE_D_APPELLO) {
					List<NumeroRegistroForm> fascColl = procedimentoService.getNumeriRegistro(idProcedimentoPadre);
					if(fascColl!=null && !fascColl.isEmpty()) {
						for(int i=0;i<fascColl.size();i++) {
							NumeroRegistroForm fascCollRec = fascColl.get(i);
							if (fascCollRec.getIdTipoUfficio2() == Integer.valueOf(Constants.TIPO_UFFICIO_TRIBUNALE)) {
								List<BeneForm>beniInAmminTrib = beneService.listaBeniInAmministrazione(fascCollRec.getIdUfficio2(), fascCollRec.getIdProc2(), null, amministratore.getId());
								if(beniInAmminTrib!=null && !beniInAmminTrib.isEmpty()) {
									boolean aggiungiBene = true;
									for(int j=0;j<beniInAmminTrib.size();j++) {
										BeneForm beneTmp = beniInAmminTrib.get(j);
										for(int k=0;k<beniInAmministrazione.size();k++) {
											BeneForm beneSel = beniInAmministrazione.get(k);
											if(beneTmp.getIdBene().equals(beneSel.getIdBene())) aggiungiBene = false;
										}
										if(aggiungiBene) beniInAmministrazione.add(beneTmp);
									}
								}								
							}
						}
					}
				} else {			
					beniInAmministrazione = beneService.listaBeniInAmministrazione(idUffOwn, idProcedimentoPadre, null, amministratore.getId());
				}
				//

				modelMap.addAttribute("beniInAmministrazione", beniInAmministrazione);
				List<BeneForm> beniInAmministrazioneSel = beneService.listaBeniInAmministrazione(idUffOwn, idProcedimento, attoForm.getCodAtto(), amministratore.getId());
				if (beniInAmministrazioneSel != null) {
					List<Long> listaIdCheckbox_associati = new ArrayList<Long>();
					for (int i = 0; i < beniInAmministrazioneSel.size(); i++) {
						BeneForm beneSel = beniInAmministrazioneSel.get(i);
						listaIdCheckbox_associati.add(beneSel.getIdBene());
					}
					attoForm.setListaIdCheckbox_associati(listaIdCheckbox_associati);
				}
			}
			
		}
		
		modelMap.addAttribute("modalitaPervenimento", commonService.listaTipoModPerv());

		// Modifiche CS
		if (attoForm.getCategoriaAtto().getId().equals(Constants.ISTANZA_CORREZIONE_ERRORI_MATERIALI))  {
			// Recupero informazione del soggetto intestatario
			List<IntestatarioDto> listaIntestatariCorr = procedimentoServiceCam.getIntestatari(idUffOwn, procedimento.getIdProcedimento());
			if(listaIntestatariCorr!=null)
				for(int i=0;i<listaIntestatariCorr.size();i++) {
					IntestatarioDto intestatario = listaIntestatariCorr.get(i);
					SoggettoForm soggettoInt = new SoggettoForm(); 
					soggettoInt.setTipoSoggetto(intestatario.getCODI_FK_ID_TIPO_SOGG().toString());
					// Persona Fisica
					if ("F".equals(intestatario.getCODI_FK_ID_TIPO_SOGG().toString())) {
						soggettoInt.setCognome(intestatario.getDESC_COGNOME());
						soggettoInt.setNome(intestatario.getDESC_NOME());
						soggettoInt.setDataNascita(intestatario.getDATA_NASCITA());
						
						ComboForm statoNascita = new ComboForm();
						if (intestatario.getCODI_FK_ID_STATO_NASC() != null)
							statoNascita.setId(intestatario.getCODI_FK_ID_STATO_NASC().toString());
						statoNascita.setDescrizione(intestatario.getDESC_STATO());
						soggettoInt.setStatoNascita(statoNascita);
						
						ComboForm comune = new ComboForm();
						if (intestatario.getCODI_FK_ID_COM_NASC() != null)
							comune.setId(intestatario.getCODI_FK_ID_COM_NASC().toString());
						comune.setDescrizione(intestatario.getDESC_COMUNE());
						soggettoInt.setComuneNascita(comune);
						
						if(intestatario.getDESC_COM_ESTERO_NASC() != null && !intestatario.getDESC_COM_ESTERO_NASC().equals("")){
							soggettoInt.setDescrizioneComuneEsteroNascita(intestatario.getDESC_COM_ESTERO_NASC());
							soggettoInt.getComuneNascita().setDescrizione(intestatario.getDESC_COM_ESTERO_NASC());
						}						
						
						soggettoInt.setSesso(intestatario.getFLG_SESSO().toString());
						soggettoInt.setCodiceFiscale(intestatario.getDESC_CODICE_FISCALE());
					
					} else {
					
						// Persona Giuridica
						soggettoInt.setPartitaIva(intestatario.getDESC_PARTITA_IVA());
						soggettoInt.setRagioneSociale(intestatario.getDESC_RAGIONE_SOC());
						
						ComboForm statoSedeLegale = new ComboForm();
						if (intestatario.getCODI_FK_ID_STATO_SEDE() != null)
							statoSedeLegale.setId(intestatario.getCODI_FK_ID_STATO_SEDE().toString());
						statoSedeLegale.setDescrizione(intestatario.getDESC_STATO_SEDE());
						soggettoInt.setStatoSedeLegale(statoSedeLegale);
						
						ComboForm comuneSedeLegale = new ComboForm();
						if (intestatario.getCODI_FK_ID_COM_SEDE() != null)
							comuneSedeLegale.setId(intestatario.getCODI_FK_ID_COM_SEDE().toString());
						comuneSedeLegale.setDescrizione(intestatario.getDESC_COMUNE_SEDE());
						soggettoInt.setComuneSedeLegale(comuneSedeLegale);
						
						
						if(intestatario.getDESC_COM_ESTERO_SEDE() != null && !intestatario.getDESC_COM_ESTERO_SEDE().equals("")){
							comuneSedeLegale.setDescrizione(intestatario.getDESC_COM_ESTERO_SEDE());
							comuneSedeLegale.setId(null);
							soggettoInt.setComuneSedeLegale(comuneSedeLegale);
						}	
						
					}	
					
					attoForm.setSoggetto(soggettoInt);
				}
		}
		
		if (attoForm.getCategoriaAtto().getId().equals(Constants.ISTANZA_TERZO_INTERVENIENTE))  {
			List<SoggettoForm> soggettiIntervenienti = procedimentoService.soggettiDirettiInteressatiDelProcedimento(procedimento.getIdProcedimento(), Constants.RUOLO_INTERVENTIENTE, Constants.Flag.S);
			modelMap.addAttribute("intervenienti", soggettiIntervenienti);
		}			

		
		List<SoggettoForm> soggettiDirettiIstanza=attoService.getRicercaSoggettoIstanza(idProcedimento, idAtto, idUffOwn, Constants.ID_RUOLO_DIRETTO);
		List<SoggettoForm> soggettiTerziIstanza=attoService.getRicercaSoggettoIstanza(idProcedimento, idAtto, idUffOwn, Constants.ID_RUOLO_TERZO);
		List<DifensoreForm> soggettiAvvocatiIstanza = attoService.getRicercaAvvIstanza(idProcedimento,idAtto, idUffOwn);
		boolean giaSalvatoDiretti= false ;
		boolean giaSalvatoTerzi= false;
		boolean giaSalvatoAvvocati= false;
		
		
				if (soggettiDirettiIstanza!= null && soggettiDirettiIstanza.size() > 0 )
					giaSalvatoDiretti=true;
				if	(soggettiTerziIstanza!= null && soggettiTerziIstanza.size() > 0) 
					giaSalvatoTerzi=true;
				if   (soggettiAvvocatiIstanza != null && soggettiAvvocatiIstanza.size() > 0 ) 
					giaSalvatoAvvocati= true;
		
		
		if (attoForm.getStatoAtto().getId().equals("" + Constants.STATOATTO_REGISTRATO) ||
			attoForm.getStatoAtto().getId().equals("" + Constants.STATOATTO_ARCHIVIATO)	) 
			modelMap.addAttribute("riepilogoRicorso", "disabled");
		
		
		modelMap.addAttribute("modalitaPervenimento", commonService.listaTipoModPerv());

		// tipo deposito
		if (attoForm.getImpugnazione().getTipoDeposito()!= null && "A".equals(attoForm.getImpugnazione().getTipoDeposito().getId()))
			modelMap.addAttribute("altriUffici", commonService.getElencoUfficiDistretto(idUffOwn));
        
		
		
		// per la tendina dei diretti interessati
		if ((attoForm.getImpugnazione().getParte() != null) && (attoForm.getImpugnazione().getParte().getId() != null )) {
		if ((attoForm.getImpugnazione().getParte() != null) && (attoForm.getImpugnazione().getParte().getId().equals("D"))) {
			List<SoggettoForm> soggettiDirettiInteressati = attoService.getRicercaSoggettoAtto(idProcedimento, idAtto, idUffOwn,
					Constants.ID_RUOLO_DIRETTO, Constants.Flag.S);
			modelMap.addAttribute("soggettiDiretti", soggettiDirettiInteressati);
		
		if (giaSalvatoDiretti){
			List<ComboForm> comboSoggettiDiretti =utility.creaComboSoggetti(soggettiDirettiIstanza);
			List<String> comboSoggettiDirettiString=new ArrayList<String>();
			for (int i=0;i<comboSoggettiDiretti.size();i++) comboSoggettiDirettiString.add(comboSoggettiDiretti.get(i).getId()) ;
			attoForm.getImpugnazione().setIdsDirettiInteressati(comboSoggettiDirettiString);
		}
			
		}
		// per la tendina dei terzi
		if ((attoForm.getImpugnazione().getParte() != null) && (attoForm.getImpugnazione().getParte().getId().equals("T"))) {
			List<SoggettoForm> soggettiTitolari = attoService.getRicercaSoggettoAtto(idProcedimento, idAtto, idUffOwn,Constants.ID_RUOLO_TERZO,
					Constants.Flag.S);
			modelMap.addAttribute("soggettiTitolari", soggettiTitolari);
			if (giaSalvatoTerzi){
			List<ComboForm> comboSoggettiDiretti =utility.creaComboSoggetti(soggettiTerziIstanza);
			List<String> comboSoggettiDirettiString=new ArrayList<String>();
			for (int i=0;i<comboSoggettiDiretti.size();i++) comboSoggettiDirettiString.add(comboSoggettiDiretti.get(i).getId()) ;
			attoForm.getImpugnazione().setIdsSoggettiTitolari(comboSoggettiDirettiString);
			}
			}
		
			if ((attoForm.getImpugnazione().getParte() != null) && ((attoForm.getImpugnazione().getParte().getId().equals("A"))
					                                                || (attoForm.getImpugnazione().getParte().getId().equals("E")))
					
					) {

					 List<TAvvocati> listaAvvocati = attoService.getAvvocatiDifensori(idProcedimento, idUffOwn, 0);
						List<ComboForm> avvocatiDifensori = utility.creaComboAvvocati(listaAvvocati);
						modelMap.addAttribute("avvocatiDifensori", avvocatiDifensori);
				if ((attoForm.getImpugnazione().getAvvocatiDifensori() != null)
						&& (!attoForm.getImpugnazione().getAvvocatiDifensori().getId().equals("-1"))) {
					List<SoggettoForm> soggetti = attoService.getSoggettiDifensori(idUffOwn, idProcedimento,
							Long.parseLong(attoForm.getImpugnazione().getAvvocatiDifensori().getId()));
					if ((soggetti != null) && (soggetti.size() > 0)) {
						List<ComboForm> soggettiDifensori = utility.creaComboSoggetti(soggetti);
						modelMap.addAttribute("soggettiDifensori", soggettiDifensori);
	                    if (giaSalvatoAvvocati){
	                    	List<SoggettoForm> soggettiAvvocati = attoService.getSoggettiDifensoriIstanza(attoForm.getCodAtto(), idUffOwn, Long.parseLong(attoForm.getImpugnazione().getAvvocatiDifensori().getId()), null, null, descUtente);
	                    	List<ComboForm> soggettiDifensoriAvvocati = utility.creaComboSoggetti(soggettiAvvocati);
							List<String> comboSoggettiDirettiString=new ArrayList<String>();
							for (int i=0;i<soggettiDifensori.size();i++) comboSoggettiDirettiString.add(soggettiDifensoriAvvocati.get(i).getId()) ;
							attoForm.getImpugnazione().setIdsSoggettiDifensori(comboSoggettiDirettiString);
	                    	
	                    }
					}
				} else // nel caso di procura in cui non c'e' l'id avvocato
				if (attoForm.getImpugnazione().getDescCognAvv() != null && !attoForm.getImpugnazione().getDescCognAvv().equals("")) {
					List<SoggettoForm> soggetti = attoService.getSoggettiDifensoriProcura(idUffOwn, idProcedimento);
					if ((soggetti != null) && (soggetti.size() > 0)) {
						List<ComboForm> soggettiDifensori = utility.creaComboSoggetti(soggetti);
						modelMap.addAttribute("soggettiDifensori", soggettiDifensori);

					}
				}

			}
			
			if (attoForm.getImpugnazione().getParte() != null && attoForm.getImpugnazione().getParte().getId().equals("I")) {
				
				List<IntestatarioDto> listaIntestatari = procedimentoServiceCam.getIntestatari(idUffOwn, idProcedimento);
				modelMap.addAttribute("listaIntestatari", listaIntestatari);
				
			}		
			
		}
		
	
		modelMap.addAttribute("listaRicorso", listaRicorso);
		
		if (attoForm.getCategoriaAtto().getId().equals(Constants.ISTANZA_CORREZIONE_ERRORI_MATERIALI))  {
			List<IntestatarioDto> listaIntestatari = procedimentoServiceCam.getIntestatari(idUffOwn, idProcedimento);
			SoggettoForm soggettoDaCorregere = null;
			if(listaIntestatari != null && !listaIntestatari.isEmpty()){
				 Iterator<IntestatarioDto> iter = listaIntestatari.iterator(); 
				 IntestatarioDto intestatario = iter.next();
				 soggettoDaCorregere = procedimentoServiceCam.getSoggettoDaCorregere(intestatario.getCODI_FK_ID_SOGG_INT().longValue(), intestatario.getCODI_FK_ID_VER().longValue(), intestatario.getCODI_FK_ID_UFF_OWNER().intValue());
			}
			modelMap.addAttribute("soggettoDaCorregere", soggettoDaCorregere);
		}
        
		inizializzaDocumento(attoForm, request);
		
		if (attoForm.getStatoAtto().getId().equals("" + Constants.STATOATTO_REGISTRATO) ||
			attoForm.getStatoAtto().getId().equals("" + Constants.STATOATTO_ARCHIVIATO)){ 
			modelMap.addAttribute("riepilogoRicorso", "disabled");
			if (Constants.ISTANZA_CAUZIONE.equals(attoForm.getCategoriaAtto().getId()) &&
				(	attoForm.getImpugnazione() !=null && 
					attoForm.getImpugnazione().getDescTipoProp()!=null &&
					!attoForm.getImpugnazione().getDescTipoProp().equals("")
				))  {
				modelMap.addAttribute("IsIstanzaCauzioneDaProc", "true");
			}
			mv = new ModelAndView("/procedimento/atto/istanzaModificaPersonaliReadOnly");
			
		}
		return mv;
	}
}
