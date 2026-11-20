package it.giustizia.smp.controller.conservatorie;

import it.giustizia.smp.controller.BreadcrumbsController;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.conservatorie.ComunicazioneInsertForm;
import it.giustizia.smp.form.conservatorie.ComunicazioneListaForm;
import it.giustizia.smp.service.CommonService;
import it.giustizia.smp.service.ConservatorieService;
import it.giustizia.smp.utility.Constants;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PredisposizioneComunicazioniController extends BreadcrumbsController
{
    @Autowired
    private CommonService        commonService;

    @Autowired
    private ConservatorieService conservatorieService;


    @ModelAttribute( "categorieAtti" )
    public List<ComboForm> getCategorieAtti( HttpServletRequest request )
    {
        return conservatorieService.getCategorieAtti( "inserimento",(Integer) request.getSession().getAttribute("idTipoUfficio") );
    }

    @ModelAttribute( "tipoComunic" )
    public List<ComboForm> getTipoComunic( HttpServletRequest request )
    {
        return conservatorieService.getTipoComunic();
    }

    @ModelAttribute( "tipoNota" )
    public List<ComboForm> getTipoNota( HttpServletRequest request )
    {
        return commonService.getTipoNota();
    }

    @ModelAttribute( "tipoComunicAnn" )
    public List<ComboForm> getTipoComunicAnn( HttpServletRequest request )
    {
        return conservatorieService.getTipoComunicAnn();
    }

    @ModelAttribute( "tipoComunicTrasc" )
    public List<ComboForm> getTipoComunicTrasc( HttpServletRequest request )
    {
        return conservatorieService.getTipoComunicTrasc();
    }

    @RequestMapping( value = "/conservatorie/predispComunicazioni.htm", method = RequestMethod.GET )
    public ModelAndView predisposizioneComunicazioniRicerca(
        @ModelAttribute( "attoForm" ) AttoForm attoForm, BindingResult result, ModelMap map, HttpServletRequest request )
    {
        attoForm = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
        ComunicazioneInsertForm form = new ComunicazioneInsertForm(); 
        ModelAndView mnv = new ModelAndView( "conservatorie/predisposizioneComunicazioni" );
        mnv.addObject( "comunicazioneInsertForm", form );
        //setto valore tipoComunicT
        form.setTipoComunicT("ATTO GIUDIZIARIO");
        addBreadcrumbs( "GET", "procedimento/atto/copertinaAtto.htm", null );
        map.addAttribute( "decretoDecisorio", true );
        ComboForm comboTipoAtto = attoForm.getTipoAtto();  
        ComboForm comboCatAtto = attoForm.getCategoriaAtto();
        Character flgDecisorio = conservatorieService.getFlagDecisorio( 
            Integer.valueOf( comboTipoAtto.getId()), Integer.valueOf( comboCatAtto.getId() ) );
        form.setFlgDecisorio( flgDecisorio );
        return mnv;
    }

    @RequestMapping( value = "/conservatorie/predisposizioneComunicazioni.htm", method = RequestMethod.POST, params = { "Procedi" } )
    public ModelAndView predisposizioneComunicazioniProcedi(
        @ModelAttribute( "attoForm" ) AttoForm attoForm, ComunicazioneInsertForm form, BindingResult result, ModelMap map , HttpServletRequest request)
    {
        
    	ModelAndView mnv = new ModelAndView( "conservatorie/predisposizioneComunicazioni" );
        attoForm = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
		Integer catAtto = Integer.valueOf( attoForm.getCategoriaAtto().getId());
		Integer tipoAtto = Integer.valueOf(attoForm.getTipoAtto().getId());
		String tipoNota = form.getTipoNota().getId();
		int idUffOwn = recuperaIdUfficioUtente();
		ProcedimentoForm proc = recuperaProcedimento();
		long idProced = recuperaIdProcedimento();
		Short repAnno=null;
        Long repNumero=null;
        try
        {
            repAnno = Short.valueOf(attoForm.getNumeAnnoAtto());
            repNumero = Long.valueOf(attoForm.getNumeAtto());
        } catch( NumberFormatException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		form.getTenoreMisura();                                       // ??????????????????????

		if (tipoNota.equals("-1")) {
			super.setMessage( Constants.MESSAGE, "conservatoria.error.tipoNotaObb", null, true );
			return mnv;
		}
		
		valorizzaCampiSelezione(tipoNota,form, request);
		
		// controllo obbligatorietà selezioni 
		if(tipoNota == null || "-1".equals(tipoNota) || 
		   form.getAttoSpecie() == null || "-1".equals(form.getAttoSpecie().getId())  || 
		   	(("ANNOTAZIONE A TRASCRIZIONE".equals(form.getAttoSpecie().getId()) && form.getTipoComunicTrasc().getId().equals("-1")) )  ||
		   	(("ANNOTAZIONE AD ANNOTAZIONE".equals(form.getAttoSpecie().getId()) && form.getTipoComunicAnn().getId().equals("-1")) )    || 
		   	form.getTipoComunicT() == null ) {
			super.setMessage( Constants.MESSAGE, "conservatoria.error.datiComunicazioneObb", null, true );
			return mnv;
		}
		
		String descrizione = form.getDescAtto().replaceAll("'", "%");
        form.setDescAtto(descrizione);
        
        List<ComunicazioneListaForm> listaComunicazioniP = caricaLista( idUffOwn, tipoNota,
	    		tipoAtto, catAtto, repAnno, repNumero, form.getAttoSpecie().getId().toUpperCase(), form.getDescAtto().toUpperCase());


        if( listaComunicazioniP != null && listaComunicazioniP.size() > 0 )
		{
            form.setListaPredisp(listaComunicazioniP);
		}  else {
	        form.setListaPredisp(null);
		}
        form.setListaRichiesta( true );
        request.setAttribute("listaPredisp", "listaPredisp");
       
    	map.addAttribute( "listaComunicazioniP", listaComunicazioniP );
        addBreadcrumbs( "GET", "procedimento/atto/copertinaAtto.htm", null );
        map.addAttribute( "decretoDecisorio", true );
        return mnv;
    }

	private void valorizzaCampiSelezione(String tipoNota,ComunicazioneInsertForm form, HttpServletRequest request) 
	{
		String attoSpecie = null;
		String descSpecie = null;
		
		if ("T".equals(tipoNota) ) {
			form.setAttoSpecie(form.getAttoSpecieT());
		}
		if ("A".equals(tipoNota)) {
			if (form.getAttoSpecieA() ==null || form.getAttoSpecieA().equals("-1")) {
			    form.setAttoSpecie(null);
			} else {
				form.setAttoSpecie(form.getAttoSpecieA());
			}
		}
		
		attoSpecie = form.getAttoSpecie().getId();
		
		if (tipoNota.equals("A") ) {
			ComboForm tipoComunic = new ComboForm();
			tipoComunic.setId("-1");
			form.setTipoComunicA(tipoComunic);
			if (!form.getTipoComunicAnn().getId().equals("-1")) {
				form.setTipoComunicA(form.getTipoComunicAnn());
			} else if (!form.getTipoComunicTrasc().getId().equals("-1")) {
				form.setTipoComunicA(form.getTipoComunicTrasc());
			}
			if ("ANNOTAZIONE A TRASCRIZIONE".equals(attoSpecie.toUpperCase())) {
				form.setTipoComunicA(form.getTipoComunicTrasc());
			} else {
				form.setTipoComunicA(form.getTipoComunicAnn());
			}
			form.setTipoComunic(form.getTipoComunicA());
		}
		
		if (tipoNota.equals("A") ) {
			if (form.getTipoComunicA().getId().equals("-1") || form.getTipoComunicA() == null)
			{
				descSpecie = null;
			} else {
				descSpecie = cercaDescrTipoComun( getTipoComunic(request),form.getTipoComunicA().getId());
			}
		}

		if (tipoNota.equals("T") ) {
			 descSpecie = form.getTipoComunicT();
		}
		if (descSpecie != null) {
			form.setDescAtto(descSpecie.toUpperCase());
			
		}
		
	}

	private String cercaDescrTipoComun(List<ComboForm> tipoComunic,String key) {
    	String ret = null;
    	if (key == null) return ret;
		
		if (tipoComunic != null && tipoComunic.size() > 0 ){
			for (int i = 0; i < tipoComunic.size() && ret == null;i++ ){
				if(key.equals(tipoComunic.get(i).getId())){
					ret = tipoComunic.get(i).getDescrizione();
				}
			}
		}
		return ret;
	}

//  @RequestMapping( value = "/conservatorie/predisposizioneComunicazioni.htm", method = RequestMethod.GET )
    @RequestMapping( value = "/conservatorie/predisposizioneComunicazioni.htm", method = RequestMethod.GET )
   	public ModelAndView paginazione( AttoForm attoForm, ComunicazioneInsertForm form, BindingResult result, ModelMap map, HttpServletRequest request  )
   	{
   		return predisposizioneComunicazioniProcedi( attoForm, form, result, map, request );
   	}


	@RequestMapping( value = "/conservatorie/predisposizioneComunicazioni.htm", method = RequestMethod.POST, params = { "Nuova" } )
    public ModelAndView predisposizioneComunicazioniNuova(
        @ModelAttribute( "attoForm" ) AttoForm attoForm, ComunicazioneInsertForm form,BindingResult result, ModelMap map, HttpServletRequest request )
    {
        
        ModelAndView mnv = new ModelAndView( "conservatorie/predisposizioneComunicazioni" );
    	attoForm = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
		int idUffOwn = recuperaIdUfficioUtente();
		ProcedimentoForm proc = recuperaProcedimento();
		long idProced = recuperaIdProcedimento();
		Short repAnno = null;
        Long repNumero = null;
        try
        {
            repAnno = Short.valueOf(attoForm.getNumeAnnoAtto());
            repNumero = Long.valueOf(attoForm.getNumeAtto());
        } catch( NumberFormatException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Integer catAtto = Integer.valueOf( attoForm.getCategoriaAtto().getId());
        Integer tipoAtto = Integer.valueOf(attoForm.getTipoAtto().getId());
		String descUtente = (String)request.getSession().getAttribute( "codUtente" ) ;
		String tipoNota = form.getTipoNota().getId();
		String descAtto = null;
		String descrizione = null;
		
		if (tipoNota.equals("-1")) {
			super.setMessage( Constants.MESSAGE, "conservatoria.error.tipoNotaObb", null, true );
			return mnv;
		}
		
		valorizzaCampiSelezione(tipoNota,form, request);
		
		// controllo obbligatorietà selezioni 
		if(tipoNota == null || "-1".equals(tipoNota) || 
		   form.getAttoSpecie() == null || "-1".equals(form.getAttoSpecie().getId())  || 
		   	(("ANNOTAZIONE A TRASCRIZIONE".equals(form.getAttoSpecie().getId()) && form.getTipoComunicTrasc().getId().equals("-1")) )  ||
		   	(("ANNOTAZIONE AD ANNOTAZIONE".equals(form.getAttoSpecie().getId()) && form.getTipoComunicAnn().getId().equals("-1")) )    || 
		   	form.getTipoComunicT() == null ) {
			super.setMessage( Constants.MESSAGE, "conservatoria.error.datiComunicazioneObb", null, true );
			return mnv;
		}

		descrizione = form.getDescAtto().replaceAll("'", "%");
		
		descAtto = form.getDescAtto().toUpperCase();
		form.setDescAtto(descAtto);
		form.setDescUtente(descUtente);

		if (tipoNota.equals("A")) {
			if (form.getTenoreMisura() == null ) {
		        form.setDescAtto(descrizione);

		        List<ComunicazioneListaForm> listaComunicazioniP = caricaLista( idUffOwn, tipoNota,
			    		tipoAtto, catAtto, repAnno, repNumero, form.getAttoSpecie().getId().toUpperCase(), form.getDescAtto().toUpperCase());

		        if( listaComunicazioniP != null && listaComunicazioniP.size() > 0 )
				{
		            form.setListaPredisp(listaComunicazioniP);
				}  else {
			        form.setListaPredisp(null);
				}
		        form.setListaRichiesta( true );
		        request.setAttribute("listaPredisp", "listaPredisp");
		    	map.addAttribute( "listaComunicazioniP", listaComunicazioniP );
		    	map.addAttribute( "risultatiRicerca", listaComunicazioniP );
				super.setMessage( Constants.MESSAGE, "conservatoria.error.tenoreMisuraObb", null, true );
				return mnv;
			}
		}
        String esito = null;
        String errore = null;
        Short idCons = 0;
        esito = conservatorieService.nuovaComunicazione(idUffOwn,tipoAtto, catAtto,repAnno, repNumero,idProced,form, idCons );
        if ("OK".equals(esito)) {
    		super.setMessage( Constants.SUCCESS_MESSAGE, "success.insersciNota", " ", true );
    		
        } else 
        {
        	if ("KO_NOTA".equals(esito)) {
        		errore = " A causa di errori nell'inserimento ";
        	} else if ("KO_BENI".equals(esito)) {
        		errore = " Non ci sono beni da associare ";
        	} else if("KO_NOTA_BENE".equals(esito)) {
        		errore = " Errore nell'associazione dei beni  ";
        	} else if ("KO_NO_SOGGETTI".equals(esito)) {
        		errore = " Non ci sono soggetti da associare  ";
        	} else if("KO_ASSOCIA_SOGGETTO".equals(esito)) {
        		errore = " Inserimento dei Soggetti non eseguita a causa di errori  ";
        	} else if("KO_NOTA_SOGG_BENE".equals(esito)) {
        		errore = " Inserimento relazione Soggetto Bene non eseguito a causa di errori  ";
        	} else if("KO_DELETE_NOTA".equals(esito)) {
        		errore = " Errore in fase di cancellazione della nota  ";
        	} else if ("KO_PUBBL_UFF".equals(esito)) {
        		errore = " dati incompleti del pubblico ufficiale  ";
        	} else if ("KO_SEQUESTRO".equals(esito)) {
        		errore = " La tipologia di comunicazione è incongruente con la tipologia dell'atto selezionato  ";
        	}
        	super.setMessage( Constants.MESSAGE, "conservatoria.error.rigeneraNota", "Creazione nuova nota;" + errore , false );
        }
       		        
        form.setDescAtto(descrizione);
        List<ComunicazioneListaForm> listaComunicazioniP = caricaLista( idUffOwn, tipoNota,
	    		tipoAtto, catAtto, repAnno, repNumero, form.getAttoSpecie().getId().toUpperCase(), form.getDescAtto().toUpperCase());

        if( listaComunicazioniP != null && listaComunicazioniP.size() > 0 )
		{
            form.setListaPredisp(listaComunicazioniP);
		}  else {
	        form.setListaPredisp(null);
		}
        form.setListaRichiesta( true );
        request.setAttribute("listaPredisp", "listaPredisp");
    	map.addAttribute( "listaComunicazioniP", listaComunicazioniP );

		
        mnv.addObject( "comunicazioneInsertForm", form );
        addBreadcrumbs( "GET", "procedimento/atto/copertinaAtto.htm", null );
        map.addAttribute( "decretoDecisorio", true );
        return mnv;
    }

	@RequestMapping( value = "/conservatorie/predisposizioneComunicazioni.htm", method = RequestMethod.POST, params = { "Rigenera" } )
    public ModelAndView predisposizioneComunicazioniRigenera(
        @ModelAttribute( "attoForm" ) AttoForm attoForm, ComunicazioneInsertForm form,BindingResult result, ModelMap map, HttpServletRequest request )
    {
        
        ModelAndView mnv = new ModelAndView( "conservatorie/predisposizioneComunicazioni" );
    	attoForm = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
		int idUffOwn = recuperaIdUfficioUtente();
		ProcedimentoForm proc = recuperaProcedimento();
		long idProced = recuperaIdProcedimento();
		Short repAnno = null;
        Long repNumero = null;
        try
        {
            repAnno = Short.valueOf(attoForm.getNumeAnnoAtto());
            repNumero = Long.valueOf(attoForm.getNumeAtto());
        } catch( NumberFormatException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		Integer catAtto = Integer.valueOf( attoForm.getCategoriaAtto().getId());
		Integer tipoAtto = Integer.valueOf(attoForm.getTipoAtto().getId());
		String descUtente = (String)request.getSession().getAttribute( "codUtente" ) ;
		String tipoNota = form.getTipoNota().getId();
		String descAtto = null;
		String descrizione = null;
		
		if (tipoNota.equals("-1")) {
			super.setMessage( Constants.MESSAGE, "conservatoria.error.tipoNotaObb", null, true );
			return mnv;
		}
		
		valorizzaCampiSelezione(tipoNota,form, request);
		
		// controllo obbligatorietà selezioni 
		if(tipoNota == null || "-1".equals(tipoNota) || 
		   form.getAttoSpecie() == null || "-1".equals(form.getAttoSpecie().getId())  || 
		   form.getDescAtto() == null ) 
		{
			super.setMessage( Constants.MESSAGE, "conservatoria.error.datiComunicazioneObb", null, true );
			return mnv;
		}

		descrizione = form.getDescAtto().replaceAll("'", "%");

		if (tipoNota.equals("A")) {
			if (form.getTenoreMisura() == null ) {
				form.setDescAtto(descrizione);
		        List<ComunicazioneListaForm> listaComunicazioniP = caricaLista( idUffOwn, tipoNota,
			    		tipoAtto, catAtto, repAnno, repNumero, form.getAttoSpecie().getId().toUpperCase(), form.getDescAtto().toUpperCase());

		        if( listaComunicazioniP != null && listaComunicazioniP.size() > 0 )
				{
		            form.setListaPredisp(listaComunicazioniP);
				}  else {
			        form.setListaPredisp(null);
				}
		        form.setListaRichiesta( true );
		        request.setAttribute("listaPredisp", "listaPredisp");
		    	map.addAttribute( "listaComunicazioniP", listaComunicazioniP );
		    	map.addAttribute( "risultatiRicerca", listaComunicazioniP );
				super.setMessage( Constants.MESSAGE, "conservatoria.error.tenoreMisuraObb", null, true );
				return mnv;
			}
		}
        
		String notaSel = form.getNotaSelezionata();
		String verifica = null;
		
		for (int i = 0; i < form.getNotaSel().length; i++){
			if (form.getNotaSel()[i] != null) {
				verifica = "OK";
				break;
			}
		}
		
		if( verifica == null )
		{
			form.setDescAtto(descrizione);
	        List<ComunicazioneListaForm> listaComunicazioniP = caricaLista( idUffOwn, tipoNota,
		    		tipoAtto, catAtto, repAnno, repNumero, form.getAttoSpecie().getId().toUpperCase(), form.getDescAtto().toUpperCase());

	        if( listaComunicazioniP != null && listaComunicazioniP.size() > 0 )
			{
	            form.setListaPredisp(listaComunicazioniP);
			}  else {
		        form.setListaPredisp(null);
			}
	        form.setListaRichiesta( true );
	        request.setAttribute("listaPredisp", "listaPredisp");
	    	map.addAttribute( "listaComunicazioniP", listaComunicazioniP );
	    	map.addAttribute( "risultatiRicerca", listaComunicazioniP );
			super.setMessage( Constants.MESSAGE, "table.item.selection", null, false );
			return mnv;
		}
        String esito = null;
        String errore = null;	
		descAtto = form.getDescAtto().toUpperCase();
		form.setDescAtto(descAtto);
		form.setDescUtente(descUtente);
		
        esito = conservatorieService.rigeneraComunicazione(idUffOwn,tipoAtto, catAtto,repAnno, repNumero,idProced,form, form.getNotaSel() );

        if ("OK".equals(esito)) 
        {
    		super.setMessage( Constants.SUCCESS_MESSAGE, "success.rigeneraNota", "Rigenerazione nota ", true );
        } else 
        {
        	if ("KO_NOTA".equals(esito)) {
        		errore = " A causa di errori nell'inserimento ";
        	} else if ("KO_BENI".equals(esito)) {
        		errore = " Non ci sono beni da associare ";
        	} else if("KO_NOTA_BENE".equals(esito)) {
        		errore = " Errore nell'associazione dei beni  ";
        	} else if ("KO_NO_SOGGETTI".equals(esito)) {
        		errore = " Non ci sono soggetti da associare  ";
        	} else if("KO_ASSOCIA_SOGGETTO".equals(esito)) {
        		errore = " Inserimento dei Soggetti non eseguita a causa di errori  ";
        	} else if("KO_NOTA_SOGG_BENE".equals(esito)) {
        		errore = " Inserimento relazione Soggetto Bene non eseguito a causa di errori  ";
        	} else if("KO_DELETE_NOTA".equals(esito)) {
        		errore = " Errore in fase di cancellazione della nota  ";
        	} else if ("KO_PUBBL_UFF".equals(esito)) {
        		errore = " dati incompleti del pubblico ufficiale  ";
        	} else if ("KO_SEQUESTRO".equals(esito)) {
        		errore = " La tipologia di comunicazione è incongruente con la tipologia dell'atto selezionato  ";
        	}
        	super.setMessage( Constants.MESSAGE, "conservatoria.error.rigeneraNota", "Rigenerazione nota;" + errore , false );

        }
        
		form.setDescAtto(descrizione);
        List<ComunicazioneListaForm> listaComunicazioniP = caricaLista( idUffOwn, tipoNota,
	    		tipoAtto, catAtto, repAnno, repNumero, form.getAttoSpecie().getId().toUpperCase(), form.getDescAtto().toUpperCase());

        if( listaComunicazioniP != null && listaComunicazioniP.size() > 0 )
		{
            form.setListaPredisp(listaComunicazioniP);
		}  else {
	        form.setListaPredisp(null);
		}
        form.setListaRichiesta( true );
        request.setAttribute("listaPredisp", "listaPredisp");
    	map.addAttribute( "listaComunicazioniP", listaComunicazioniP );

        mnv.addObject( "comunicazioneInsertForm", form );
        addBreadcrumbs( "GET", "procedimento/atto/copertinaAtto.htm", null );
        map.addAttribute( "decretoDecisorio", true );
        return mnv;
    }


	private List<ComunicazioneListaForm> caricaLista(Integer idUfficioUtente,
			String tipoNota, Integer tipoAtto, Integer catAtto, Short repAnno,
			Long repNumero, String attoSpecie, String descSpecie) 
			{
				List<ComunicazioneListaForm> listaComunicazioniP =
			    conservatorieService.listaComunicazioniPredisp( idUfficioUtente, tipoNota,
			    		tipoAtto, catAtto, repAnno, repNumero, attoSpecie.toUpperCase(), descSpecie.toUpperCase());
		
		return listaComunicazioniP;
	}


}