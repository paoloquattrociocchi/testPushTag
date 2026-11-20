package it.giustizia.smp.service.utility.component;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import it.giustizia.smp.controller.BreadcrumbsController;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.BeneForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.MagistratoForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;

@Service
public class ComboUtilComponent extends BreadcrumbsController {

	@Autowired
	private Utility utility;
	
	public List<ComboForm> getCategorieBene(AttoForm attoForm) {
		attoForm.setSottoCategoriaBene(null);
		if ((attoForm == null) || (attoForm.getTipologiaBene() == null) || (attoForm.getTipologiaBene().getId() == null))
			return null;
		BeneForm bene = new BeneForm();
		ComboForm tipologia = attoForm.getTipologiaBene();
		bene.setTipologia(tipologia);
		return commonService.listaCategorieDelBene(bene);
	}
	
	public List<ComboForm> getSottoCategorieBene(AttoForm attoForm) {
		attoForm.setSottoCategoriaBene(null);
		if ((attoForm == null) || (attoForm.getCategoriaBene() == null) || (attoForm.getCategoriaBene().getId() == null) ||
				(attoForm.getTipologiaBene() == null) || (attoForm.getTipologiaBene().getId() == null))
			return null;
		BeneForm bene = new BeneForm();
		ComboForm categoria = attoForm.getCategoriaBene();
		bene.setCategoria(categoria);
		return commonService.listaSottocategorieDelBene(bene);
	}
	
	public String impostaCombo(AttoForm attoForm, BindingResult result, HttpServletRequest request, ModelMap modelMap,String lTipoRicorso) {
		ProcedimentoForm procedimento = recuperaProcedimento();
		long idProcedimento = procedimento.getIdProcedimento().longValue();
		int idUffOwn = recuperaIdUfficioUtente();
		attoForm.setIdUfficio(idUffOwn);
		long IdAtto = attoForm.getCodAtto();
		List<SoggettoForm> soggettiDirettiInteressati = attoService.getRicercaSoggettoAtto(idProcedimento, IdAtto, idUffOwn,
				Constants.ID_RUOLO_DIRETTO, Constants.Flag.S);
		List<SoggettoForm> soggettiTitolari = attoService.getRicercaSoggettoAtto(idProcedimento, IdAtto, idUffOwn, Constants.ID_RUOLO_TITOLARI,
				Constants.Flag.S);
		modelMap.addAttribute("soggettiDiretti", utility.creaComboSoggetti(soggettiDirettiInteressati));
		modelMap.addAttribute("soggettiTitolari", utility.creaComboSoggetti(soggettiTitolari));
		if (attoForm.getTipologiaBene() != null)
			modelMap.addAttribute("categorie", utility.caricaComboCategoria(attoForm.getTipologiaBene().getId()));
		if (attoForm.getCategoriaBene() != null)
			modelMap.addAttribute("sottoCategorieBene", utility.caricaComboSottoCategoria(attoForm.getCategoriaBene().getId()));

		List<ComboForm> tipoMisure = new ArrayList<ComboForm>();
		tipoMisure.add(new ComboForm(Constants.TipoMisura.PATRIMONIALE_ID, Constants.TipoMisura.PATRIMONIALE_DESCR));
		tipoMisure.add(new ComboForm(Constants.TipoMisura.PERSONALE_ID, Constants.TipoMisura.PERSONALE_DESCR));
		modelMap.addAttribute("tipoMisure", tipoMisure);
		modelMap.addAttribute("attoForm", attoForm);
		addBreadcrumbs();
		String lPagina=null; 
		if("CASSAZIONE".equalsIgnoreCase(lTipoRicorso))
			lPagina =  "/procedimento/atto/nuovoRicorsoInCassazione";
		else if("APPELLO".equalsIgnoreCase(lTipoRicorso))
			lPagina = "/procedimento/atto/nuovoRicorsoInAppello";
		
		return lPagina;
	}
	
	public List<ComboForm> listaEsclusioneGiaAssociati(List<ComboForm> listAll, List<MagistratoForm> listaPresenti) {
		List<ComboForm> listaFinale = new ArrayList<ComboForm>();
		boolean esiste = false;
		// LISTA DEGLI ID DA ESCLUDERE DI QUELLI GIA' PRESENTI
		ArrayList<String> idEsclusi = new ArrayList<String>();
		for (int i = 0; i < listaPresenti.size(); i++)
			idEsclusi.add(String.valueOf(listaPresenti.get(i).getId()));
		for (ComboForm comboForm2 : listAll) {
			ComboForm comboForm = comboForm2;
			String id = comboForm.getId();
			for (int i = 0; i < idEsclusi.size(); i++) {
				String id_escluso = idEsclusi.get(i);
				if (id.equals(id_escluso)) {
					esiste = true;
					break;
				}
			}
			if (!esiste)
				listaFinale.add(comboForm);
			esiste = false;
		}
		return listaFinale;
	}
	
	public List<ComboForm> getTipoAttoFonteAttoGenerico(AttoForm atto, BindingResult result, ModelMap map) {
		List<ComboForm> fontiAttoGenerico = procedimentoService.getAllFonte();
		for(int i=0; fontiAttoGenerico!=null && i<fontiAttoGenerico.size();i++) {
			ComboForm fonte = fontiAttoGenerico.get(i);
			if (fonte.getId().equals("9"))  fonte.setDescrizione("Polizia Giudiziaria");
			if (fonte.getId().equals("10")) fonte.setDescrizione("Autorità Giudiziaria");
			if (fonte.getId().equals("12")) fonte.setDescrizione("Altro");	
		}
		map.addAttribute("fontiAttoGenerico", fontiAttoGenerico);
		return fontiAttoGenerico;
	}
	
	public List<ComboForm> getTipoAttoFonteAttoAntimafia(AttoForm atto, BindingResult result, ModelMap map) {
		List<ComboForm> fontiAttoGenerico = new ArrayList<ComboForm>();
		List<ComboForm> fonti = procedimentoService.getAllFonte();
		for(int i=0; fonti!=null && i<fonti.size();i++) {
			ComboForm fonte = fonti.get(i);
			if (fonte.getId().equals("9")) {
               fonte.setDescrizione("Polizia Giudiziaria");	
               fontiAttoGenerico.add(fonte);
			}
		}
		map.addAttribute("fontiAttoGenerico", fontiAttoGenerico);
		return fontiAttoGenerico;
	}	
}
