package it.giustizia.smp.utility.flussi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import it.giustizia.smp.dao.CommonDao;
import it.giustizia.smp.dto.flussi.T_INTESTATARIODto;
import it.giustizia.smp.dto.flussi.T_MISURA_PERSDto;
import it.giustizia.smp.dto.flussi.T_PERSONA_FISICADto;
import it.giustizia.smp.dto.flussi.T_PERSONA_GIURDto;
import it.giustizia.smp.dto.flussi.T_PROCEDIMENTODto;
import it.giustizia.smp.dto.flussi.T_REL_ATTO_MIS_PATRDto;
import it.giustizia.smp.dto.flussi.T_REL_PROC_PROCDto;
import it.giustizia.smp.dto.flussi.T_SOGG_INT_PROCDto;
import it.giustizia.smp.form.FlussiForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.model.TAnUfficio;
import it.giustizia.smp.model.TProcedimento;
import it.giustizia.smp.service.FlussiService;
import it.giustizia.smp.service.ProcedimentoService;
import it.giustizia.smp.utility.Constants;

public class FlussiDaElaborareAggiunte extends RequestContextAwareTag {
	private static final long serialVersionUID = 5013154471198454846L;
	private Logger logger = Logger.getLogger(getClass());
	private String flussoId;
	private String soggetto;
	private String proposta;	
	private FlussiForm formFlusso;
	
	@Autowired
	private FlussiService flussiService;
	@Autowired
	private ProcedimentoService procedimentoService;
	@Autowired
	private Mapper mapperBean;	
	@Autowired
	private CommonDao commonDao;
	
	
	@Override
	protected int doStartTagInternal() throws Exception {
		try {
			// Autowire service in tag context
		    WebApplicationContext wac = getRequestContext().getWebApplicationContext();
            AutowireCapableBeanFactory acbf = wac.getAutowireCapableBeanFactory();
            acbf.autowireBean(this);
        
			JspWriter jspOut = pageContext.getOut();
			//HttpSession session = pageContext.getSession();
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest(); 			
			int idOwnUff = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);			
			long idProc = 0L;
            			
			String anno="";
			String num="";
			String nominativo="";
			String annoPadre = "";
			String numeroPadre = "";
			String descrUfficioPadre = "";
			
			List<SoggettoForm> listaSoggetti = new ArrayList<SoggettoForm>();
			
			List<Object> listaDto = flussiService.leggiXML(formFlusso);
			
			Iterator<Object> iteratoreDto = listaDto.iterator();
			while (iteratoreDto.hasNext()) {	
				String idSoggetto = "";
				String idUffOwner="";
				Object obj = iteratoreDto.next();
 			    if (obj instanceof T_PROCEDIMENTODto) {
				   idProc = Long.parseLong(((T_PROCEDIMENTODto) obj).getCODI_K_ID_PROC());	
				   formFlusso.setProcedimentoDto((T_PROCEDIMENTODto) obj);
				   if(flussoId != null && flussoId.equals("si")) {
			  	      anno = ((T_PROCEDIMENTODto) obj).getNUME_ANNO_PROC();
				      num = ((T_PROCEDIMENTODto) obj).getNUME_PROC();	
					  break;
 			       }
				}
				if(soggetto != null && soggetto.equals("si")) {
					   if (obj instanceof T_PERSONA_FISICADto) {
					   	   T_SOGG_INT_PROCDto dto = new T_SOGG_INT_PROCDto();
						   SoggettoForm soggettoForm = new SoggettoForm();
						   mapperBean.map(obj, dto);
						   mapperBean.map(dto, soggettoForm);
						   soggettoForm.setIdVersione(null); 
				    	   if (soggettoForm != null && soggettoForm.getIdRuolo() == 1) { 			    		   
						       listaSoggetti.add(soggettoForm);
				    	   }    
					   }
					   if (obj instanceof T_PERSONA_GIURDto) {
						   T_SOGG_INT_PROCDto dto = new T_SOGG_INT_PROCDto();
						   SoggettoForm soggettoForm = new SoggettoForm();
						   mapperBean.map(obj, dto);
						   mapperBean.map(dto, soggettoForm);
						   soggettoForm.setIdVersione(null); 
				    	   if (soggettoForm != null && soggettoForm.getIdRuolo() == 1) { 
	  					      listaSoggetti.add(soggettoForm);
				    	   }   
					   }
					   
					   //INIZIO tkt EMS-08
					   if (obj instanceof T_INTESTATARIODto) {
						   T_SOGG_INT_PROCDto dto = new T_SOGG_INT_PROCDto();
						   SoggettoForm soggettoForm = new SoggettoForm();
						   mapperBean.map(obj, dto);
						   mapperBean.map(dto, soggettoForm);
						   soggettoForm.setIdVersione(null); 
	  					   listaSoggetti.add(soggettoForm);
					   }
					   //FINE tkt EMS-08
					   
					   if (obj instanceof T_MISURA_PERSDto) {
						   idSoggetto = ((T_MISURA_PERSDto) obj).getCODI_FK_ID_SOGG_INT();
						   idUffOwner = ((T_MISURA_PERSDto) obj).getCODI_K_ID_UFF_OWNER();
					
					   }
					   if (obj instanceof T_REL_ATTO_MIS_PATRDto) {
						   idSoggetto = ((T_REL_ATTO_MIS_PATRDto) obj).getCODI_K_ID_SOGG_INT();
						   idUffOwner = ((T_REL_ATTO_MIS_PATRDto) obj).getCODI_K_ID_UFF_OWNER();
					
					   }
	  				   if (idSoggetto!= null && !idSoggetto.equals("") && idUffOwner!= null && !idUffOwner.equals("")) 		
					   {
						  SoggettoForm soggettoForm=new SoggettoForm();
					      soggettoForm.setIdSoggetto(Long.parseLong(idSoggetto));
					      soggettoForm.setIdUfficio(Integer.parseInt(formFlusso.getProcedimentoDto().getCODI_FK_ID_UFF_OWNER_NEW()));
					      soggettoForm.setIdVersione(-1L);
					      soggettoForm.setIdProcedimento(idProc);
						  soggettoForm=procedimentoService.caricaValoriDettaglioSoggettoFisicoProcedimento(soggettoForm);
						  if (soggettoForm.getNome()!= null && !soggettoForm.getNome().equals("")){ 
					    	  if (soggettoForm.getIdRuolo() == 1) { 
	  						      listaSoggetti.add(soggettoForm);
					    	  }    
						  } else {
							  
							  soggettoForm=new SoggettoForm();
							  soggettoForm.setIdSoggetto(Long.parseLong(idSoggetto));
						      soggettoForm.setIdUfficio(Integer.parseInt(formFlusso.getProcedimentoDto().getCODI_FK_ID_UFF_OWNER_NEW()));
						      soggettoForm.setIdVersione(-1L);
						      soggettoForm.setIdProcedimento(idProc);
							  soggettoForm=procedimentoService.caricaValoriDettaglioSoggettoGiuridicoProcedimento(soggettoForm);
						      if (soggettoForm!= null && soggettoForm.getRagioneSociale() != null && !soggettoForm.getRagioneSociale().equals("")) {
						    	 if (soggettoForm.getIdRuolo() == 1) {
							        if (!listaSoggetti.contains(""+soggettoForm.getIdSoggetto()) ) {
								       listaSoggetti.add(soggettoForm);
							     
							        }
						    	 }   
						      }   
						   }				
						
					    }

				}
				if (obj instanceof T_REL_PROC_PROCDto)  {
				   if (proposta != null && proposta.equals("si") && formFlusso.getInclude().equals("1401")) {
						 formFlusso.setRelProcProcDto((T_REL_PROC_PROCDto) obj);	
						 annoPadre = formFlusso.getRelProcProcDto().getNUME_ANNO_PROC2();
						 numeroPadre = formFlusso.getRelProcProcDto().getNUME_PROCEDIMENTO2();
						 String ufficioPadre = formFlusso.getRelProcProcDto().getCODI_FK_ID_UFF_OWNER2(); 
								
						 TAnUfficio ufficio = commonDao.getDettaglioUfficio(Long.parseLong(ufficioPadre));
		                 if(ufficio.getTTpTipoUfficio().getCodiKIdTipoUff() == 17) {
		                   	descrUfficioPadre = ufficio.getDescSedeUfficio();	
		                 }		   
				  }	  
			   }
				
			}
	
			
			if(soggetto != null && soggetto.equals("si")) {
								
				 
				if (listaSoggetti.isEmpty()) {
					int idTipoFasc = 0;
					if(formFlusso!=null && formFlusso.getProcedimentoDto()!=null && formFlusso.getProcedimentoDto().getCODI_FK_ID_TIPO_FASC()!=null) {
						idTipoFasc = Integer.parseInt(formFlusso.getProcedimentoDto().getCODI_FK_ID_TIPO_FASC());
					}
					TProcedimento procedimento = procedimentoService.
				            getIdProcedimentoDestinatario(idProc,formFlusso.getIdUffMittente().intValue(),idTipoFasc,idOwnUff);
                    if(procedimento != null)
					     listaSoggetti = procedimentoService.soggettiDirettiInteressatiDelProcedimento(procedimento.getCodiKIdProc(), Constants.RUOLO_DIRETTO, Constants.Flag.S);
				}
				if(!listaSoggetti.isEmpty()) {
					String numSoggetti = "";
					if (listaSoggetti.size() > 1)
					    numSoggetti = numSoggetti + " + "+Integer.toString(listaSoggetti.size()-1);
                	SoggettoForm soggettoForm = listaSoggetti.get(0);
                	if( soggettoForm.getCognome() != null && !soggettoForm.getCognome().trim().equals("") && soggettoForm.getNome() != null && !soggettoForm.getNome().trim().equals(""))
                	    nominativo = soggettoForm.getCognome() + " " + soggettoForm.getNome() + numSoggetti;
                	else if (soggettoForm.getRagioneSociale() != null && !soggettoForm.getRagioneSociale().trim().equals(""))
                		nominativo = soggettoForm.getRagioneSociale()  + numSoggetti;	
                	else
                		nominativo = "&nbsp;";               	    
                }
			}
			
			String aggiunta = "";
			
			if(flussoId != null && flussoId.equals("si")) {
		       aggiunta = "<td>" + anno +"/"+ num +"</td>";
			}   
			if(soggetto != null && soggetto.equals("si")) {
			   aggiunta = "<td>" + nominativo + "</td>";
			}   
    	    if(proposta != null && proposta.equals("si")) {
    	    	if(!annoPadre.equals("") && !numeroPadre.equals(""))
    	    	   aggiunta = "<td>" + annoPadre +"/"+ numeroPadre + " - " + descrUfficioPadre + "</td>";   
    	    	else
    	    	   aggiunta = "<td>&nbsp;</td>";   
    	    }

			
			String fragment = "";		

			StringBuilder sb = new StringBuilder(aggiunta);
			fragment += sb.toString();
			jspOut.println(fragment);

		} catch (IOException e) {
			logger.error("Errore tagLib FlussiDaElaborareAggiunte", e);
		}
		return SKIP_BODY;
	}
	
	
	public String getFlussoId() {
		return flussoId;
	}


	public void setFlussoId(String flussoId) {
		this.flussoId = flussoId;
	}
 
	public String getSoggetto() {
		return soggetto;
	}


	public void setSoggetto(String soggetto) {
		this.soggetto = soggetto;
	}

	public String getProposta() {
		return proposta;
	}

	public void setProposta(String proposta) {
		this.proposta = proposta;
	}


	public FlussiForm getFormFlusso() {
		return formFlusso;
	}


	public void setFormFlusso(FlussiForm formFlusso) {
		this.formFlusso = formFlusso;
	}
    
}
