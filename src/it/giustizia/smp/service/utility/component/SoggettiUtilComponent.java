package it.giustizia.smp.service.utility.component;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import it.giustizia.smp.controller.BreadcrumbsController;
import it.giustizia.smp.form.BeneSoggettoForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;

@Service
public class SoggettiUtilComponent extends BreadcrumbsController {

	public void caricaNormative(SoggettoForm soggettoForm, ModelAndView mnv, ModelMap map) {
		// Inizio Valorizzazione combo delle Normative
		List<ComboForm> normativeTipoPrev = new ArrayList<ComboForm>();
		List<ComboForm> normativeTipoMisura = new ArrayList<ComboForm>();
		if (soggettoForm.getTipologiaPrevenzione() != null) {
			if (soggettoForm.getTipologiaPrevenzione().getId() != null
					&& !Constants.NO_ITEM_SELECTED.equals(soggettoForm.getTipologiaPrevenzione().getId())) {
				if (soggettoForm.getIpotesiMisura() != null) {
					// Imposto manualmente ad 1 il Tipo di Procedimento
					soggettoForm.getIpotesiMisura().setId("1");
				} else {
					// Imposto manualmente ad 1 il Tipo di Procedimento
					soggettoForm.setIpotesiMisura(new ComboForm("1", ""));
				}
				normativeTipoPrev = commonService
						.getNormativaByTipoProcedAndTipologiaPrevenzione(soggettoForm);
			}
		}
		if (soggettoForm.getMisura().getTipoMisura() != null) {
			if (soggettoForm.getMisura().getTipoMisura().getId() != null
					&& !Constants.NO_ITEM_SELECTED.equals(soggettoForm.getMisura().getTipoMisura().getId())) {
				normativeTipoMisura = getNormativeMisuraPersonaleNew(soggettoForm, null);
			}
		}
		if (mnv != null) {
			mnv.addObject("normativeTipoPrev", normativeTipoPrev);
			mnv.addObject("normativeMisPers", normativeTipoMisura);
		}
		if (map != null) {
			map.addAttribute("normativeTipoPrev", normativeTipoPrev);
			map.addAttribute("normativeMisPers", normativeTipoMisura);
		}
		// Fine Valorizzazione combo delle Normative
	}
	
	public List<ComboForm> getNormativeMisuraPersonaleNew(@Valid SoggettoForm soggetto, BindingResult result) {
		if (soggetto.getMisura().getTipoMisura() != null) {
			ComboForm tipoMisura = soggetto.getMisura().getTipoMisura();
			if (Utility.isDefinito(tipoMisura)) {
				return commonService.getNormativaByTipoPrevenzioneAndTipoMisura(soggetto);
			}
		}
		return null;
	}
	
	public List<SoggettoForm> filtraSoloSoggettiMisPers(List<SoggettoForm> soggettiDirettiPers, long idProcedimento, long idAtto, int idUffOwn) {
		List<SoggettoForm> soggettiFiltrati = new ArrayList<SoggettoForm>();
		for (int i = 0; soggettiDirettiPers != null && i < soggettiDirettiPers.size(); i++) {
			SoggettoForm sogg = soggettiDirettiPers.get(i);
			if (sogg != null) {
		        List<Long> risultatiRicercaSogg = procedimentoService.listaSoggettiMisurePersAtto(idProcedimento, idAtto, idUffOwn);
		        for(int j=0;j<risultatiRicercaSogg.size();j++) {
		        	Long idSoggetto = risultatiRicercaSogg.get(j);
		        	if(sogg.getIdSoggetto().longValue() == idSoggetto.longValue()) 
		        		soggettiFiltrati.add(sogg);
		        }
			}
		}
		return soggettiFiltrati;
	}

	public List<SoggettoForm> filtraSoloSoggettiMisPatr(List<SoggettoForm> soggettiDiretti, long idProcedimento, long idAtto, int idUffOwn) {
		List<SoggettoForm> soggettiFiltrati = new ArrayList<SoggettoForm>();
		for (int i = 0; soggettiDiretti != null && i < soggettiDiretti.size(); i++) {
			SoggettoForm sogg = soggettiDiretti.get(i);
			long idSoggetto = sogg.getIdSoggetto();
			if (sogg != null) {
				List<BeneSoggettoForm> risultatiRicerca = procedimentoService.listaBeniConSoggettiAssociatiAllaMisuraPatrimonialeAtto(idSoggetto, idProcedimento, idAtto, idUffOwn);
		        if (!risultatiRicerca.isEmpty()) {
	        		soggettiFiltrati.add(sogg);
		        }
			}
		}
		return soggettiFiltrati;
	}
	
}
