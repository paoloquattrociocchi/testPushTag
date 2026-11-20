package it.giustizia.smp.service.utility.component;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import it.giustizia.smp.controller.BreadcrumbsController;
import it.giustizia.smp.controller.MenuController;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.model.TAttoGenerico;
import it.giustizia.smp.model.TRelAtti;
import it.giustizia.smp.utility.Constants;

@Service
public class AttoUtilComponent extends BreadcrumbsController {

	@Autowired
	private DecretoUtilComponent decretoUtilComponent;
	
	public String copertinaDecretoApplicazioneProvvisoria(AttoForm attoForm,List<SoggettoForm> soggetti, ModelMap modelMap, HttpServletRequest request){
		ProcedimentoForm procedimentoForm = (ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO));
		int idUffOwn = recuperaIdUfficioUtente();
		long idProcedimento = procedimentoForm.getIdProcedimento();
		String codiceUtente = recuperaIdCodiceUtente();	
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
			MenuController.caricaMenu("14", request);
			addBreadcrumbs("GET", "/procedimento/atti/elencoAttiTribunale.htm", null);
			addBreadcrumbs("POST", "/procedimento/atto/copertinaAtto.htm", null);
		}
		else{
			updateViewAndMenu(procedimentoForm, request);
			MenuController.caricaMenu("124", request);
		}
        List<Long> risultatiRicercaSogg = procedimentoService.listaSoggettiMisurePersAtto(idProcedimento,attoForm.getCodAtto(), idUffOwn);		
		String lPagina = null; 
		//non esiste neanche una misura personale
		if(risultatiRicercaSogg == null ||(risultatiRicercaSogg != null && risultatiRicercaSogg.isEmpty())){
			if(soggetti == null ||(soggetti != null  && soggetti.isEmpty()))
				soggetti = procedimentoService.soggettiDirettiInteressatiApplicazioneProvvisoria(idProcedimento, idUffOwn, codiceUtente);

			modelMap.addAttribute("soggetti", soggetti);
			modelMap.addAttribute("soggettoForm", new SoggettoForm());
			modelMap.addAttribute("attoForm", attoForm);
			addBreadcrumbs("POST", "/procedimento/atti/elencoAttiOrdineCronologico.htm", null);
			addBreadcrumbs("POST", "/procedimento/atto/copertinaAtto.htm", null);	
			lPagina =  "/procedimento/atto/copertinaDecretoApplicazioneProvvisoria";   			
		}else{ 
			//esiste almeno una misura personale
			SoggettoForm soggettoForm = new SoggettoForm();
            Long idSoggetto = risultatiRicercaSogg.get(0);
	        List<SoggettoForm> lSoggetti = procedimentoService.soggettiDirettiInteressatiDelProcedimento(idProcedimento);
	        for(int i=0;i<lSoggetti.size();i++) {
	        	SoggettoForm soggTemp = lSoggetti.get(i);
	        	if(soggTemp.getIdSoggetto().longValue() == idSoggetto.longValue()) soggettoForm = soggTemp;
	        }		
	        if(Long.toString(Constants.STATOATTO_REGISTRATO).equalsIgnoreCase(attoForm.getStatoAtto().getId()))
	           modelMap.addAttribute("disabledObject","true");
		
			lPagina = decretoUtilComponent.getMisuraDecretoApplicazioneProvvisoria(soggettoForm, attoForm , modelMap,false, request);
		}
 	  return lPagina;
    }	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AttoForm caricaAttoInSessione(int idUffOwn, long idAtto, String codiceUtente, long idProcedimento, HttpServletRequest request,
			AttoForm attoForm) {
		attoForm = attoService.dettaglioAtto(idUffOwn, idAtto, idProcedimento, codiceUtente);
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
		String numProcedOrigine = attoService.getNumeroProcedimentoOrigine(recuperaIdProcedimento(), idAtto, idUffOwn, recuperaIdCodiceUtente(),
				super.printMethodName());
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
		//INIZIO aggiunta per il dettaglio flusso di MPS
		if (attoForm!= null && attoForm.getCategoriaAtto() != null  && attoForm.getCategoriaAtto().getId() != null) { 
			if (attoForm.getCategoriaAtto().getId().equalsIgnoreCase(Constants.COMUNICAZIONE_COMMISSIONE_DI_REATO) || 
				attoForm.getCategoriaAtto().getId().equalsIgnoreCase(Constants.COMUNICAZIONE_SOTTOPOSIZIONE_MISURE_SICUREZZA) || 
				attoForm.getCategoriaAtto().getId().equalsIgnoreCase(Constants.COMUNICAZIONE_SOTTOPOSIZIONE_LIBERTA_VIGILATA) ||
				attoForm.getCategoriaAtto().getId().equalsIgnoreCase(Constants.COMUNICAZIONE_TERMINE_ESECUZIONE_MISPERS) ||
				attoForm.getCategoriaAtto().getId().equalsIgnoreCase(Constants.COMUNICAZIONE_RIPRESA_SOTTOPOSIZIONE_MISPERS) ||
				attoForm.getCategoriaAtto().getId().equalsIgnoreCase(Constants.COMUNICAZIONE_SOSPENSIONE_SOTTOPOSIZIONE_MISPERS) ||
				attoForm.getCategoriaAtto().getId().equalsIgnoreCase(Constants.VERBALE_INIZIO_SOTTOPOSIZIONE_MISPERS) ||
				attoForm.getCategoriaAtto().getId().equalsIgnoreCase(Constants.COMUNICAZIONE_SOSPENSIONE_OBBLIGO_SOGGIORNO) ||
				attoForm.getCategoriaAtto().getId().equalsIgnoreCase(Constants.COMUNICAZIONE_CESSAZIONE_OBBLIGO_SOGGIORNO)  ||
				attoForm.getCategoriaAtto().getId().equalsIgnoreCase(Constants.PARERI_REVOCA_MODIFICA_MISURE_PERSONALI) || 
				attoForm.getCategoriaAtto().getId().equalsIgnoreCase(Constants.ESITO_INDAGINE) || 
				attoForm.getCategoriaAtto().getId().equalsIgnoreCase(Constants.VIOLAZIONE_SOTTOPOSIZIONE_MISURE_PERSONALI))
			{
				AttoForm tmpAtto = new AttoForm();
				tmpAtto = attoService.getTRelProcSogintAttoById(idAtto, idUffOwn, idProcedimento);
				if(tmpAtto != null)
					attoForm.setDataCessazioneMisura(tmpAtto.getDataCessazioneMisura());
				
				TAttoGenerico tAttoGen = attoService.getTAttoGenerico(idAtto, idUffOwn);
				if(tAttoGen!=null)
					attoForm.setFlgParere(tAttoGen.getFlgParere()!=null && tAttoGen.getFlgParere()=='F' ? "Favorevole" : (tAttoGen.getFlgParere()!=null && tAttoGen.getFlgParere()=='C' ? "Contrario" : ""));
			}
		}
		if (request.getSession().getAttribute(Constants.ATTO) != null)
			request.getSession().removeAttribute(Constants.ATTO);
		request.getSession().setAttribute(Constants.ATTO, attoForm);
		return attoForm;
	}
	
	public void caricaDatiDaAttoInSessione(AttoForm attoForm, ModelMap modelMap) {
		AttoForm attoInSessione = recuperaAtto();
		if (attoInSessione != null) {
			if (attoInSessione.getCategoriaAtto() != null && attoForm.getCategoriaAtto() == null)
					attoForm.setCategoriaAtto(attoInSessione.getCategoriaAtto());
			if ((attoInSessione.getNumeAtto() != null && attoInSessione.getNumeAtto().length() > 0)
					&& (attoInSessione.getNumeAnnoAtto() != null && attoInSessione.getNumeAnnoAtto().length() > 0)) {
				attoForm.setNumeAnnoAtto(attoInSessione.getNumeAnnoAtto());
				attoForm.setNumeAtto(attoInSessione.getNumeAtto());
				if (attoInSessione.getCategoriaAtto() != null
						&& (Constants.DECRETO_DECISORIO_I_GRADO.equals(attoInSessione.getCategoriaAtto().getId())
								|| Constants.DECRETO_DECISORIO_I_GRADO_CAMERE.equals(attoInSessione.getCategoriaAtto().getId())
								|| Constants.DECRETO_DECISORIO_II_GRADO.equals(attoInSessione.getCategoriaAtto().getId())
								|| Constants.DECRETO_DECISORIO_II_GRADO_CAM.equals(attoInSessione.getCategoriaAtto().getId())
								|| Constants.SENTENZA_OPPOSIZIONE_AL_DISSEQUESTRO.equals(attoInSessione.getCategoriaAtto().getId())
								|| Constants.SENTENZA_REVOCAZIONE_DELLA_CONFISCA.equals(attoInSessione.getCategoriaAtto().getId()))
						&& attoInSessione.getDataDeposito() != null)
					attoForm.setDataDeposito(attoInSessione.getDataDeposito());
				modelMap.addAttribute("attoForm", attoForm);
			}
		}
	}
}
