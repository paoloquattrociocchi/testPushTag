package it.giustizia.smp.controller.cameralini;

import it.giustizia.smp.controller.BreadcrumbsController;
import it.giustizia.smp.controller.MenuController;
import it.giustizia.smp.controller.procedimento.SoggettoController;
import it.giustizia.smp.dto.pg_cameralini.ProcedimentoDto;
import it.giustizia.smp.form.AmministratoreForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.NumeroRegistroForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.form.cameralini.BaseForm;
import it.giustizia.smp.service.BeneService;
import it.giustizia.smp.service.CommonService;
import it.giustizia.smp.service.ProcedimentoService;
import it.giustizia.smp.service.cameralini.ProcedimentoServiceCam;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.validation.CameraliniValidation;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RicercaBaseController extends BreadcrumbsController
{
	
	@Autowired
	protected CommonService       commonService;
	
	@Autowired
	protected ProcedimentoServiceCam 	procedimentoServiceCam;
	
	@Autowired
	protected ProcedimentoService 	    procedimentoService;	
	
	@Autowired
	private BeneService					beneService;
	
    @Autowired
    protected CameraliniValidation   cameraliniValidation;
	
    @Autowired
    protected SoggettoController 	soggettoController;

	
	@ModelAttribute( "ufficio" )
	public List<ComboForm> getUfficiCompetenzaCdaTrib( HttpServletRequest request )
	{
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
		return commonService.getUfficiCompetenzaCdaTrib(idUfficioUtente);
	}
	
	@ModelAttribute( "ufficioSelezionatoDesc" )
	public String getDescUfficioCompetenza( HttpServletRequest request )
	{
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute("idUffOwner");
		return commonService.getDescUfficioCompetenza(idUfficioUtente);
	}

	@ModelAttribute( "trasmissione" )
	public List<ComboForm> getAllTipoModalitaPervenimento ( HttpServletRequest request )
	{
		ComboForm c = new ComboForm();
		c.setId("-1");
		c.setDescrizione("--Seleziona Mod Trasmissione--");
		List<ComboForm> lista = new ArrayList<ComboForm>();
		lista.add(c);
		lista.addAll(commonService.getAllTipoModalitaPervenimento());
		return lista;
	}
	
    protected  ModelAndView ricercaBase ( Model model , String funzione, Long tipoProcedimentoCameralino, HttpServletRequest request)
        {
            MenuController.caricaMenu( Constants.ID_K_MENU_PROCEDIMENTO, request );
            BaseForm baseForm = new BaseForm();
            baseForm.setFunzioneCameralini(funzione);
            ModelAndView mnv = new ModelAndView( "/cameralini/ricerca", "ricerca", baseForm );
            cleanBreadcrumb(request.getSession());
            addBreadcrumbs();
            return mnv;
        }
    
	 protected ProcedimentoForm preparaNuovoProcedimento(BaseForm form,Long idTipoProc){
	    	// si prepara il nuovo procedimento a
	    	// partire dal procedimento padre
	    	ProcedimentoForm procedimentoForm = new ProcedimentoForm();
			procedimentoForm = procedimentoServiceCam.getDettaglioProcedimento(form.getProcedPadre().getIdProcedimento());
			
			procedimentoForm.getTipoProcedimento().setId(""+idTipoProc);
			if (procedimentoForm.getProcedente() != null) {
				procedimentoForm.getProcedente().setId(null);
				procedimentoForm.getProcedente().setDescrizione(null);
			};
			
			return procedimentoForm;
	    }
		
	  protected List<SoggettoForm> caricalista(Integer idUffOwn, Long idProcedimento,Long idTipoProc) {
			 List<SoggettoForm> lista = procedimentoServiceCam.soggettiDirettiInteressatiDelProcedimento(idProcedimento, null, Constants.Flag.S);
 
			 //TODO kkk
			 // in base al tipo prodcedimento va estratta una lista di sogg diversa 
			 // getMisurePatrProcedimento estrae quella che serve per filtrare le patrimonali
			 List<SoggettoForm> listaId = null;
			 if (idTipoProc.intValue() == 1)  {
				 listaId = procedimentoServiceCam.getSoggettiMisurePatrProcedimento(idUffOwn, idProcedimento);
			 }
			 
			 if (listaId == null || lista.isEmpty()) {
				 return null;
			 }
			 //nel caso delle misure patrimoniali
			 // si eliminano dalla lista dei soggetti
			 // quelli che non hanno misure patrim definitive  sul procedimento 
			
			 //TODO : il valore effettivo del ruolo ci deve essere comunicato.
			 // e per i restanti si pone il ruolo ad 1
			 
			 
			 if (lista != null && lista.size() > 0) {
				 for (int i = lista.size() - 1; i >= 0 ; i--){
					 SoggettoForm s = lista.get(i);
					 boolean trovato = false;
					 for (int j = 0; j < listaId.size()  && !trovato; j++) {
						 if(s.getIdSoggetto().compareTo(listaId.get(j).getIdSoggetto()) == 0) {
							 trovato = true;
						 }
					 }
					 if(!trovato){
						 lista.remove(i);
					 }else {
						 s.setIdRuolo(1);
					 }
				 }
			 }
			 return lista;
	    }
	  
	  protected ProcedimentoForm getProcedimentoPadre(Integer ufficioSel,Long numero,Long anno,Long tipoProc){
		  ProcedimentoDto procDto =procedimentoServiceCam.getProcedimentoByKeys(ufficioSel, numero, anno,tipoProc);
		  if (procDto  == null || procDto.getCODI_K_ID_PROC() == null){
			  return null;
		  }else {
			  return procedimentoServiceCam.getDettaglioProcedimento(procDto.getCODI_K_ID_PROC().longValue());
		  }
	  }
	  
	  protected String checkMisuraPatrApplicata(ProcedimentoForm p,Integer ufficioSel,ProcedimentoDto solop){
		  // si verifica se il fascicolo esiste e
		  // se trovato si verifica coerenza tipologia fascicolo e procedimento 
		  //e la presenza di dataDdefinitiva
		  String  ret = null;
		  if (p == null) {
			  if (solop == null){
				  return "cameralini.procedimentoInesistente"; 
			  }else{
				  return "cameralini.fascicoloNoMisureDef"; 
			  }
			 // return "cameralini.fascicoloInesistente";
		  	  
		  }else if(!("1".equals(p.getTipoFascicolo().getId()) && 
					("1".equals(p.getTipoFascicolo().getId())  || "2".equals(p.getTipoFascicolo().getId())))){
			  return "cameralini.fascicoloIncoerente";
		  }else if (procedimentoServiceCam.getSoggettiMisurePatrProcedimento(ufficioSel, p.getIdProcedimento()) == null){
			  return "cameralini.fascicoloIncoerente";
		  }
		  
		  return ret;
	  }
	  protected String checkMisuraPersApplicata(ProcedimentoForm p,Integer ufficioSel,ProcedimentoDto solop){
		  // si verifica se il fascicolo esiste e
		  // se trovato si verifica coerenza tipologia fascicolo e procedimento 
		  //e la presenza di dataDdefinitiva
		  
		  String  ret = null;
		  if (p == null) {
			  if (solop == null){
				  return "cameralini.procedimentoInesistente"; 
			  }else{
				  return "cameralini.fascicoloNoMisureDef"; 
			  }
			 // return "cameralini.fascicoloInesistente";
		  }else if(!("1".equals(p.getTipoFascicolo().getId()) && 
					("1".equals(p.getTipoFascicolo().getId())  || "2".equals(p.getTipoFascicolo().getId())))){
			  return "cameralini.fascicoloIncoerente";
		  }else if (procedimentoServiceCam.getSoggettiMisurePersProcedimento(ufficioSel, p.getIdProcedimento(),"P") == null){
			  return "cameralini.fascicoloIncoerente";
		  }
		  
		  return ret;
	  }
	  protected String checkCauzApplicata(ProcedimentoForm p,Integer ufficioSel){
		  // si verifica se il fascicolo esiste e
		  // se trovato si verifica coerenza tipologia fascicolo e procedimento 
		  //e la presenza di dataDdefinitiva
		  
		  String  ret = null;
		  if (p == null) {
			  return "cameralini.fascicoloInesistente";
		  }else if(!("1".equals(p.getTipoFascicolo().getId()) && 
					("1".equals(p.getTipoFascicolo().getId())  || "2".equals(p.getTipoFascicolo().getId())))){
			  return "cameralini.fascicoloIncoerente";
		  }else if (procedimentoServiceCam.getSoggettiMisurePersProcedimento(ufficioSel, p.getIdProcedimento(),"C") == null){
			  return "cameralini.fascicoloIncoerente";
		  }
		  
		  return ret;
	  }
	  
	  
	  protected String checkMisuraLiqAmmApplicata(ProcedimentoForm p,Integer ufficioSel, HttpServletRequest request){
		  // si verifica se il fascicolo esiste e
		  // se trovato si verifica coerenza tipologia fascicolo e procedimento 
		  // e la presenza di amministratori
		  String  ret = null;
		  if (p == null) {
			  return "cameralini.fascicoloInesistente";
		  }else if(p.getTipoProcedimento()==null || !("1".equals(p.getTipoFascicolo().getId()) && 
					("1".equals(p.getTipoProcedimento().getId())  || "2".equals(p.getTipoProcedimento().getId())))){
			  return "cameralini.fascicoloIncoerente";
		  }else {
			    List<AmministratoreForm> listaAmministratori = new ArrayList<AmministratoreForm>();
				if ((Integer) request.getSession().getAttribute("idTipoUfficio") == Constants.TIPO_UFFICIO_CORTE_D_APPELLO) {
					List<NumeroRegistroForm> fascColl = procedimentoService.getNumeriRegistro(p.getIdProcedimento());
					if(fascColl!=null && !fascColl.isEmpty()) {
						for(int i=0;i<fascColl.size();i++) {
							NumeroRegistroForm fascCollRec = fascColl.get(i);
							if (fascCollRec.getIdTipoUfficio2() == Integer.valueOf(Constants.TIPO_UFFICIO_TRIBUNALE)) {
								List<AmministratoreForm> listaAmminTrib = beneService.getAmministratori(fascCollRec.getIdUfficio2(), fascCollRec.getIdProc2(), null, null, true);
								if(listaAmminTrib!=null && !listaAmminTrib.isEmpty()) {
									boolean aggiungiAmm = true;
									for(int j=0;j<listaAmminTrib.size();j++) {
										AmministratoreForm ammTmp = listaAmminTrib.get(j);
										for(int k=0;k<listaAmministratori.size();k++) {
											AmministratoreForm ammSel = listaAmministratori.get(k);
											if(ammTmp.getId().equals(ammSel.getId())) aggiungiAmm = false;
										}
										if(aggiungiAmm) listaAmministratori.add(ammTmp);
									}
								}								
							}
						}
					}
				} else {
					listaAmministratori = beneService.getAmministratori(ufficioSel, p.getIdProcedimento(), null, null, true);
				}			  
			  
			  if(listaAmministratori==null || listaAmministratori.size()==0) return "cameralini.fascicoloNoAmministratori";
		  }
		  return ret;
	  }
}