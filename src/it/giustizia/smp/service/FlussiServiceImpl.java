package it.giustizia.smp.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.hibernate.Hibernate;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.DOMBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import it.giustizia.gestorelocale.accettazione.servizi.AccettaDeposito;
import it.giustizia.gestorelocale.accettazione.servizi.AssegnaDeposito;
import it.giustizia.gestorelocale.accettazione.servizi.ServiziAccettazioneServiceClient;
import it.giustizia.smp.controller.LoginController;
import it.giustizia.smp.dao.AmministrazioneDao;
import it.giustizia.smp.dao.AttoDao;
import it.giustizia.smp.dao.BeneDao;
import it.giustizia.smp.dao.CommonDao;
import it.giustizia.smp.dao.FlussiDao;
import it.giustizia.smp.dto.flussi.SENTENZA_CASSAZIONEDto;
import it.giustizia.smp.dto.flussi.T_ATTODto;
import it.giustizia.smp.dto.flussi.T_DECRETODto;
import it.giustizia.smp.dto.flussi.T_IMPUGNAZIONEDto;
import it.giustizia.smp.dto.flussi.T_INTESTATARIODto;
import it.giustizia.smp.dto.flussi.T_ISTANZADto;
import it.giustizia.smp.dto.flussi.T_MISURA_PERSDto;
import it.giustizia.smp.dto.flussi.T_PERSONA_FISICADto;
import it.giustizia.smp.dto.flussi.T_PERSONA_GIURDto;
import it.giustizia.smp.dto.flussi.T_PROCEDIMENTODto;
import it.giustizia.smp.dto.flussi.T_REL_ATTIDto;
import it.giustizia.smp.dto.flussi.T_REL_ATTO_MIS_PATRDto;
import it.giustizia.smp.dto.flussi.T_REL_ATTO_PROCDto;
import it.giustizia.smp.dto.flussi.T_REL_PROC_PROCDto;
import it.giustizia.smp.dto.flussi.T_REL_UDIENZA_STATO_UDDto;
import it.giustizia.smp.dto.flussi.T_SOGG_INT_PROCDto;
import it.giustizia.smp.dto.flussi.T_UDIENZADto;
import it.giustizia.smp.dto.pg_flussi.FlussiInDto;
import it.giustizia.smp.dto.pg_flussi.FlussiPoliziaDto;
import it.giustizia.smp.dto.pg_flussi.FlussiToolAmministratoriDto;
import it.giustizia.smp.form.AllegatoForm;
import it.giustizia.smp.form.AmministratoreForm;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.BeneForm;
import it.giustizia.smp.form.BeneSoggettoPoliziaForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.FlussiForm;
import it.giustizia.smp.form.FlussiPoliziaForm;
import it.giustizia.smp.form.FlussiToolAmministratoriForm;
import it.giustizia.smp.form.IndirizzoForm;
import it.giustizia.smp.form.MisuraForm;
import it.giustizia.smp.form.MisuraPatrimonialeFlussiForm;
import it.giustizia.smp.form.MisuraPersonaleFlussiForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.form.UfficiForm;
import it.giustizia.smp.model.TAmministratori;
import it.giustizia.smp.model.TAnComuni;
import it.giustizia.smp.model.TAnNormative;
import it.giustizia.smp.model.TAnOperFinanz;
import it.giustizia.smp.model.TAnOperFinanzId;
import it.giustizia.smp.model.TAnProvincia;
import it.giustizia.smp.model.TAnStato;
import it.giustizia.smp.model.TAnUfficio;
import it.giustizia.smp.model.TAnimali;
import it.giustizia.smp.model.TAtto;
import it.giustizia.smp.model.TAttoId;
import it.giustizia.smp.model.TAzienda;
import it.giustizia.smp.model.TBene;
import it.giustizia.smp.model.TBeneFin;
import it.giustizia.smp.model.TBeneImmat;
import it.giustizia.smp.model.TBeneImmobile;
import it.giustizia.smp.model.TCollezioni;
import it.giustizia.smp.model.TCollezioniId;
import it.giustizia.smp.model.TContante;
import it.giustizia.smp.model.TDenaro;
import it.giustizia.smp.model.TDepositi;
import it.giustizia.smp.model.TDepositiAmm;
import it.giustizia.smp.model.TFlussiIn;
import it.giustizia.smp.model.TMisuraPers;
import it.giustizia.smp.model.TNotifPdoc;
import it.giustizia.smp.model.TProcedimento;
import it.giustizia.smp.model.TTpCatAtto;
import it.giustizia.smp.model.TTpCatBene;
import it.giustizia.smp.model.TTpCatCatasto;
import it.giustizia.smp.model.TTpSettoreAtt;
import it.giustizia.smp.model.TTpSottocatBene;
import it.giustizia.smp.model.TTpTipoBene;
import it.giustizia.smp.model.TTpTipoCatasto;
import it.giustizia.smp.model.TTpTipoFondo;
import it.giustizia.smp.model.TTpTipoMisura;
import it.giustizia.smp.model.TTpTipoPrevProc;
import it.giustizia.smp.model.TTpTipoProc;
import it.giustizia.smp.model.TTpTipoStrada;
import it.giustizia.smp.model.TTpValuta;
import it.giustizia.smp.model.TVeicolo;
import it.giustizia.smp.service.webservices.DocumentaleService;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.webservice.server.polizia.DatiAutoritaComunicazioneType;
import it.giustizia.smp.webservice.server.polizia.DatiAutoritaMittenteType;
import it.giustizia.smp.webservice.server.polizia.DatiAutoritaProcedenteType;
import it.giustizia.smp.webservice.server.polizia.DatiAutoritaProponenteType;
import it.giustizia.smp.webservice.server.polizia.DatiComunicazioneType;
import it.giustizia.smp.webservice.server.polizia.DatiEsitoIndagatiType;
import it.giustizia.smp.webservice.server.polizia.DatiMisuraPatrimonialeType;
import it.giustizia.smp.webservice.server.polizia.DatiMisuraPersonalePFType;
import it.giustizia.smp.webservice.server.polizia.DatiParereType;
import it.giustizia.smp.webservice.server.polizia.DatiRichiestaAggravamentoType;
import it.giustizia.smp.webservice.server.polizia.DatiSoggettiIndagatiType;
import it.giustizia.smp.webservice.server.polizia.DatiSoggettiSottopostoType;
import it.giustizia.smp.webservice.server.polizia.DatiTribunaleCompetenteType;
import it.giustizia.smp.webservice.server.polizia.DatiUffGiuRichiedenteType;
import it.giustizia.smp.webservice.server.polizia.DatiUffGiuSottoposizioneType;
import it.giustizia.smp.webservice.server.polizia.DatiUfficioDestinatarioType;
import it.giustizia.smp.webservice.server.polizia.DatiUfficioGiudiziarioType;
import it.giustizia.smp.webservice.server.polizia.IdSoggettoSottopostoType;
import it.giustizia.smp.webservice.server.polizia.TitolareType;
import it.giustizia.smp.xml.amministratori.AltriOggettiExtType;
import it.giustizia.smp.xml.amministratori.AltriOggettiType;
import it.giustizia.smp.xml.amministratori.AnimaliExtType;
import it.giustizia.smp.xml.amministratori.AnimaliType;
import it.giustizia.smp.xml.amministratori.AziendaExtType;
import it.giustizia.smp.xml.amministratori.AziendaType;
import it.giustizia.smp.xml.amministratori.BeneExtType;
import it.giustizia.smp.xml.amministratori.BeneExtType.DatiSpecificiBene;
import it.giustizia.smp.xml.amministratori.BeneExtType.DatiSpecificiBene.Finanziario;
import it.giustizia.smp.xml.amministratori.CollezioniExtType;
import it.giustizia.smp.xml.amministratori.CollezioniType;
import it.giustizia.smp.xml.amministratori.FinanziarioType;
import it.giustizia.smp.xml.amministratori.ImmaterialeExtType;
import it.giustizia.smp.xml.amministratori.ImmaterialeType;
import it.giustizia.smp.xml.amministratori.ImmobileExtType;
import it.giustizia.smp.xml.amministratori.ImmobileType;
import it.giustizia.smp.xml.amministratori.ImmobileType.DatiCatastali;
import it.giustizia.smp.xml.amministratori.ProcedimentoType;
import it.giustizia.smp.xml.amministratori.ProvvedimentoRicezioneType;
import it.giustizia.smp.xml.amministratori.RicezioneRelazione;
import it.giustizia.smp.xml.amministratori.TipoFinanziarioType;
import it.giustizia.smp.xml.amministratori.VeicoloExtType;
import it.giustizia.smp.xml.amministratori.VeicoloType;


public class FlussiServiceImpl implements FlussiService {

	private Logger logger = Logger.getLogger(LoginController.class);

	private FlussiDao flussiDao;
	@Autowired
	private ProcedimentoService procedimentoService;
	@Autowired
	private Mapper mapperBean;
	@Autowired
	private DocumentaleService documentaleService;
	//@Autowired(required = true)
	//private HttpServletRequest request;
	//private String tipoMessaggio;
	
	@Autowired
	private ServiziAccettazioneServiceClient serviziAccettazioneServiceClient ;
	@Autowired
	private CommonService commonService;
	@Autowired
	private MisuraService misuraService;
	@Autowired
	private ConservatorieService conservatorieService;
	@Autowired
	private AttoService attoService;
	@Autowired
	private PoliziaService poliziaService;

	@Autowired
	private AmministrazioneService amministrazioneService;
	@Autowired
	private AmministrazioneDao amministrazioneDao;

	@Autowired
	private AttoDao attoDao;
	@Autowired
	private BeneDao beneDao;
	@Autowired
	private CommonDao commonDao;
	
	private static Object createObject(Constructor constructor,
			Object[] arguments) {

		Object object = null;
		try {
			object = constructor.newInstance(arguments);
			return object;
		} catch (InstantiationException e) {
			System.out.println(e);
		} catch (IllegalAccessException e) {
			System.out.println(e);
		} catch (IllegalArgumentException e) {
			System.out.println(e);
		} catch (InvocationTargetException e) {
			System.out.println(e);
		}
		return object;
	}

	public void dettaglioFlusso(FlussiForm flussiForm) {

		List<BigDecimal> listSoggIntProc = new ArrayList<BigDecimal>();
		List<String> listaSoggetti=new ArrayList<String>();
		List<Object> listaDto = leggiXML(flussiForm);
		String idPadre=null;
		long idProc =0L;
		Iterator<Object> iteratoreDto = listaDto.iterator();
		// try {
		while (iteratoreDto.hasNext()) {
			Object obj = iteratoreDto.next();
			String idSoggetto ="";
			String idUffOwner="";
			if (obj instanceof T_PROCEDIMENTODto) {
				flussiForm.setProcedimentoDto((T_PROCEDIMENTODto) obj);
				idProc = Long.parseLong(flussiForm.getProcedimentoDto().getCODI_K_ID_PROC());	
			}
			if (obj instanceof T_PERSONA_FISICADto) {
				// flussiForm.getListaT_PERSONA_FISICADto().add((T_PERSONA_FISICADto)
				// obj);
				T_SOGG_INT_PROCDto dto = new T_SOGG_INT_PROCDto();
				SoggettoForm soggettoForm = new SoggettoForm();
				mapperBean.map(obj, dto);
				mapperBean.map(dto, soggettoForm);
				soggettoForm.setIdVersione(null); // nella schermata dei
													// flussi questa
													// informazione non deve
													// apparire
				flussiForm.getListaSoggettoForm().add(soggettoForm);
				listaSoggetti.add(""+soggettoForm.getIdSoggetto());
			}
			if (obj instanceof T_PERSONA_GIURDto) {
				// flussiForm.getListaT_PERSONA_GIURDto().add((T_PERSONA_GIURDto)
				// obj);
				T_SOGG_INT_PROCDto dto = new T_SOGG_INT_PROCDto();
				SoggettoForm soggettoForm = new SoggettoForm();
				mapperBean.map(obj, dto);
				mapperBean.map(dto, soggettoForm);
				soggettoForm.setIdVersione(null); // nella schermata dei
													// flussi questa
													// informazione non deve
													// apparire
				flussiForm.getListaSoggettoForm().add(soggettoForm);
				listaSoggetti.add(""+soggettoForm.getIdSoggetto());
			}
			
			//INIZIO tkt EMS-08
			if (obj instanceof T_INTESTATARIODto) {
				T_SOGG_INT_PROCDto dto = new T_SOGG_INT_PROCDto();
				SoggettoForm soggettoForm = new SoggettoForm();
				mapperBean.map(obj, dto);
				mapperBean.map(dto, soggettoForm);
				soggettoForm.setIdVersione(null); 
				flussiForm.getListaSoggettoForm().add(soggettoForm);
				listaSoggetti.add(""+soggettoForm.getIdSoggetto());
			}
			//FINE tkt EMS-08
			
			if (obj instanceof T_ATTODto) {
				flussiForm.setAttoDto((T_ATTODto) obj);
			}
			if (obj instanceof T_ISTANZADto) {
				flussiForm.setIstanzaDto((T_ISTANZADto) obj);
			}
			if (obj instanceof T_DECRETODto) {
				flussiForm.setDecretoDto((T_DECRETODto) obj);
			}
			if (obj instanceof T_IMPUGNAZIONEDto) {
				flussiForm.setImpugnazioneDto((T_IMPUGNAZIONEDto) obj);
			}
			if (obj instanceof T_SOGG_INT_PROCDto) {
				if (!listSoggIntProc.contains(((T_SOGG_INT_PROCDto) obj)
						.getID_PERS())) {
					listSoggIntProc
							.add(((T_SOGG_INT_PROCDto) obj).getID_PERS());
					SoggettoForm soggettoForm = new SoggettoForm();
					mapperBean.map(obj, soggettoForm);
					soggettoForm.setIdVersione(null); // nella schermata dei
														// flussi questa
														// informazione non
														// deve apparire
					flussiForm.getListaSoggettoForm().add(soggettoForm);
					listaSoggetti.add(""+soggettoForm.getIdSoggetto());
				}
			}
			if (obj instanceof T_REL_ATTO_PROCDto) {
				flussiForm.setAttoProcDto((T_REL_ATTO_PROCDto) obj);
			}
			if (obj instanceof T_REL_PROC_PROCDto) {
				flussiForm.setRelProcProcDto((T_REL_PROC_PROCDto) obj);
			if 	(flussiForm.getRelProcProcDto().getCODI_K_ID_PROC_PADRE() != null && !flussiForm.getRelProcProcDto().getCODI_K_ID_PROC_PADRE().equals(""))
				idPadre=flussiForm.getRelProcProcDto().getCODI_K_ID_PROC_PADRE();
			   if ( idPadre != null && flussiForm.getProcedimentoDto().getCODI_FK_ID_TIPO_FASC() != null 
						&& flussiForm.getProcedimentoDto().getCODI_FK_ID_TIPO_FASC().equals("6"))
			   {
						TProcedimento procedimento = procedimentoService.getIdProcedimentoDestinatario(Long.parseLong(idPadre),flussiForm.getIdUffMittente().intValue(),0,Integer.parseInt(flussiForm.getProcedimentoDto().getCODI_FK_ID_UFF_OWNER_NEW()));
			          //padre
			          if (procedimento!=null) 
			          {
			        	  idProc=procedimento.getCodiKIdProc();
			        	  //Segnalazione EMS-53
			        	  if(flussiForm.getInclude().equals(Constants.ISTANZA_DA_TRIBUNALE_MSG))
			        	  {
				        	  if(procedimento.getNumeProc() != null)
				        		  flussiForm.setNumeroPadre(procedimento.getNumeProc());
				        	  if(procedimento.getNumeAnnoProc() != null)
				        		  flussiForm.setAnnoPadre(procedimento.getNumeAnnoProc());
				        	  if(procedimento.getFlgAntimafia() != null)
				        		  flussiForm.getProcedimentoDto().setFLG_ANTIMAFIA(procedimento.getFlgAntimafia().toString());
				        	  if(procedimento.getFlgOrdinaria() != null)
				        		  flussiForm.getProcedimentoDto().setFLG_ORDINARIA(procedimento.getFlgOrdinaria().toString());
				        	  if(procedimento.getFlgAntiterrorismo() != null)
				        		  flussiForm.getProcedimentoDto().setFLG_ANTITERRORISMO(procedimento.getFlgAntiterrorismo().toString());
			        	  }
			          }
			   }
			}
			if (obj instanceof T_REL_ATTIDto) {
				flussiForm.setRelAttiDto((T_REL_ATTIDto) obj);
				
			}
			if (obj instanceof T_UDIENZADto) {
				flussiForm.getListaT_UDIENZADto().add((T_UDIENZADto) obj);
			}
			if (obj instanceof T_REL_UDIENZA_STATO_UDDto) {
				flussiForm.getListaT_REL_UDIENZA_STATO_UDDto().add(
						(T_REL_UDIENZA_STATO_UDDto) obj);
			}
			if (obj instanceof T_MISURA_PERSDto) {
				idSoggetto = ((T_MISURA_PERSDto) obj)
						.getCODI_FK_ID_SOGG_INT();
				idUffOwner = ((T_MISURA_PERSDto) obj)
						.getCODI_K_ID_UFF_OWNER();
			
			}
			if (obj instanceof T_REL_ATTO_MIS_PATRDto) {
				idSoggetto = ((T_REL_ATTO_MIS_PATRDto) obj)
						.getCODI_K_ID_SOGG_INT();
				idUffOwner = ((T_REL_ATTO_MIS_PATRDto) obj)
						.getCODI_K_ID_UFF_OWNER();
			
			}
			
			if (idSoggetto!= null && !idSoggetto.equals("") &&
					idUffOwner!= null && !idUffOwner.equals("")) 		
					{
				SoggettoForm soggetto=new SoggettoForm();
			    soggetto.setIdSoggetto(Long.parseLong(idSoggetto));
			    soggetto.setIdUfficio(Integer.parseInt(flussiForm.getProcedimentoDto().getCODI_FK_ID_UFF_OWNER_NEW()));
			    soggetto.setIdVersione(-1L);
			    soggetto.setIdProcedimento(idProc);
				soggetto=procedimentoService.caricaValoriDettaglioSoggettoFisicoProcedimento(soggetto);
				if (soggetto.getNome()!= null && !soggetto.getNome().equals("")){ 
					if (!listaSoggetti.contains(""+soggetto.getIdSoggetto())){
					flussiForm.getListaSoggettoForm().add(soggetto);
					listaSoggetti.add(""+soggetto.getIdSoggetto());
					}
				}else {
					soggetto=new SoggettoForm();
					soggetto.setIdSoggetto(Long.parseLong(idSoggetto));
				    soggetto.setIdUfficio(Integer.parseInt(flussiForm.getProcedimentoDto().getCODI_FK_ID_UFF_OWNER_NEW()));
				    soggetto.setIdVersione(-1L);
				    soggetto.setIdProcedimento(idProc);
					soggetto=procedimentoService.caricaValoriDettaglioSoggettoGiuridicoProcedimento(soggetto);
				if (soggetto!= null && soggetto.getRagioneSociale() != null && !soggetto.getRagioneSociale().equals(""))
					if (!listaSoggetti.contains(""+soggetto.getIdSoggetto())){
						flussiForm.getListaSoggettoForm().add(soggetto);
						listaSoggetti.add(""+soggetto.getIdSoggetto());
						}
				}
			
				
					}
		}
	}

	public void dettaglioFlussoSentenza(FlussiForm flussiForm, int idUffOwn,
			String descUtente) {

		List<Object> listaDto = flussiDao.dettaglioFlussoSentenza(
				flussiForm.getIdFlusso(), idUffOwn, descUtente);

		Iterator<Object> iteratoreDto = listaDto.iterator();

		flussiForm.setAttoDto(new T_ATTODto());
		flussiForm.setImpugnazioneDto(new T_IMPUGNAZIONEDto());
		flussiForm.setProcedimentoDto(new T_PROCEDIMENTODto());

		while (iteratoreDto.hasNext()) {
			Object obj = iteratoreDto.next();
			SENTENZA_CASSAZIONEDto sentenzaCass = (SENTENZA_CASSAZIONEDto) obj;

			flussiForm.getProcedimentoDto().setCODI_K_ID_PROC(
					sentenzaCass.getID_PROCEDIMENTO());
			flussiForm.getAttoDto().setCODI_K_ID_ATTO(
					sentenzaCass.getCODI_K_ID_ATTO());
			if (sentenzaCass != null
					&& sentenzaCass.getDATA_EMISSIONE() != null)
				flussiForm.getAttoDto().setDATA_EMISSIONE(
						sentenzaCass.getDATA_EMISSIONE());

			if (sentenzaCass != null && sentenzaCass.getDATA_DEPOSITO() != null)
				flussiForm.getAttoDto().setDATA_DEPOSITO(
						sentenzaCass.getDATA_DEPOSITO());

			flussiForm.setDescSedeUffMittente(sentenzaCass.getDESC_UFFICIO());
			flussiForm.getImpugnazioneDto().setNUME_ANNO_ATTO(
					sentenzaCass.getNUME_ANNO_ATTO());
			flussiForm.getImpugnazioneDto().setNUME_ATTO(
					sentenzaCass.getNUME_ATTO());
			flussiForm.setNumeroSezionale(sentenzaCass.getNUMERO_SEZIONALE());
			flussiForm.getAttoDto().setCODI_FK_ID_CAT_ATTO(
					sentenzaCass.getCODI_FK_ID_CAT_ATTO());
			flussiForm.getProcedimentoDto().setDESC_TIPO_PROC(
					sentenzaCass.getDESC_TIPO_PROC());
		}

	}

	/**
	 * @return the flussiDao
	 */
	public FlussiDao getFlussiDao() {

		return flussiDao;
	}

	public TFlussiIn getFlussoIn(long codiKIdFlussoIn) {

		return flussiDao.getFlussoIn(codiKIdFlussoIn);
	}

	/**
	 * Dato un codice flusso viene generata una lista di oggetti DTO contenenti
	 * le informazioni del xml
	 * 
	 * @param codiKIdFlussoIn
	 *            codice del flusso da esaminare
	 * @return lista di oggetti dto
	 */
	public ArrayList<Object> leggiXML(FlussiForm flussiForm) {

		ArrayList<Object> listaObj = new ArrayList<Object>();
		TFlussiIn flusso = flussiDao.getFlussoIn(flussiForm.getIdFlusso());
		String codInclude = Long.toString(flusso.getTTpTipoFlusso()
				.getCodiKIdTipoFlusso());
		String desInclude = flusso.getTTpTipoFlusso().getDescTipoFlusso();
		flussiForm.setInclude(codInclude);
		flussiForm.setDescTipoMessaggio(desInclude);
		Clob xml = flusso.getXmlFlusso();
		org.w3c.dom.Document documentw3c = null;

		try {
			documentw3c = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(xml.getAsciiStream());
		} catch (Exception e) {
			logger.error("Errore " + e);
		}
		DOMBuilder builder = new DOMBuilder();
		Document document = builder.build(documentw3c);
		// Prendo la radice
		Element root = document.getRootElement();
		// tipoMessaggio = root.getName();
		// Estraggo i figli dalla radice
		List children = root.getChildren();
		Iterator iterator = children.iterator();
		String nomeTabella = "";
		// esamino tutti i figli della radice
		while (iterator.hasNext()) {

			try {
				Element item = (Element) iterator.next();
				Class[] argsClass = new Class[] {};
				Class classDefinition = null;
				nomeTabella = item.getName();
				classDefinition = Class.forName("it.giustizia.smp.dto.flussi."
						+ item.getName() + "Dto");
				Object[] args = new Object[] {};
				Constructor constructor = classDefinition
						.getConstructor(argsClass);
				Object obj = createObject(constructor, args);
				List childList = item.getChildren();
				Iterator itElem = childList.iterator();
				// estraggo i valori da inserire in ogni dto
				while (itElem.hasNext()) {
					Element elem = (Element) itElem.next();
					setProperty(elem.getName(), obj, elem.getValue());
				}
				listaObj.add(obj);
			} catch (ClassNotFoundException e) {
				logger.error("FlussiServiceImpl.leggiXML.ClassNotFoundException Errore durante la lettura del documento xml");
				logger.error("Tabella Non utilizzata in visualizzazione: "
						+ nomeTabella+ " - " + flusso.getCodiKIdFlussoIn());
			} catch (NoSuchMethodException e) {
				logger.error("FlussiServiceImpl.leggiXML.NoSuchMethodException Errore durante la lettura del documento xml");
			}
		}
		return listaObj;
	}

	public List<FlussiForm> listaFlussiDaElaborare(int idUffOwn,
			FlussiForm flussiForm) {

		List<FlussiInDto> queryResults = null;
		List<FlussiForm> risultati = new ArrayList<FlussiForm>();
		queryResults = flussiDao.flussiDaElaborare(idUffOwn);
		Iterator<FlussiInDto> iterator = queryResults.iterator();
		while (iterator.hasNext()) {
			FlussiInDto flusso = iterator.next();
			FlussiForm form = new FlussiForm();
			if (flusso != null) {
				mapperBean.map(flusso, form);
			}
			risultati.add(form);
		}
		return risultati;
	}

	public List processFlussoDefinitivita(long idFlusso, String codUtente,
			long idUffOwn) {

		return flussiDao.processFlussoDefinitivita(idFlusso, codUtente,
				idUffOwn);
	}

	public List processFlussoIncompRestAtti(long idFlusso, String codUtente,
			long idUffOwn) {

		return flussiDao.processFlussoIncompRestAtti(idFlusso, codUtente,
				idUffOwn);
	}

	public List processFlussoIstanzaSoggDiffidato(FlussiForm form,
			String codUtente, long idUffOwn) {

		return flussiDao.processFlussoIstanzaSoggDiffidato(form.getIdFlusso(),
				codUtente, idUffOwn, form.getAttoDto()
						.getDATA_DEPOSITO_ASSOCIA(), form.getAllegato()
						.getDocumento(), form.getAllegato().getContentType(),
				form.getAllegato().getFileName());
	}

	public List processFlussoProposta(FlussiForm form, String codUtente,
			long idUffOwn) {

		return flussiDao.processFlussoProposta(form.getIdFlusso(), codUtente,
				idUffOwn, form.getAttoDto().getDATA_DEPOSITO_ASSOCIA(), form
						.getAttoDto().getDATA_RICEZIONE_ASSOCIA(), form
						.getAllegato().getDocumento(), form.getAllegato()
						.getContentType(), form.getAllegato().getFileName());
	}
	
	public List processFlussoFascicoloCauzione(FlussiForm form, String codUtente,
			long idUffOwn) {

		return flussiDao.processFlussoFascicoloCauzione(form.getIdFlusso(), codUtente,
				idUffOwn, form.getAttoDto().getDATA_DEPOSITO_ASSOCIA(), form
						.getAttoDto().getDATA_RICEZIONE_ASSOCIA(), form
						.getAllegato().getDocumento(), form.getAllegato()
						.getContentType(), form.getAllegato().getFileName());
	}

	public List processFlussoSequestro(FlussiForm form, String codUtente,
			long idUffOwn) {

		return flussiDao.processFlussoSequestro(form.getIdFlusso(), codUtente,
				idUffOwn, form.getAttoDto().getDATA_DEPOSITO_ASSOCIA(), form
						.getAttoDto().getDATA_RICEZIONE_ASSOCIA(), form
						.getAllegato().getDocumento(), form.getAllegato()
						.getContentType(), form.getAllegato().getFileName());
	}

	public List processFlussoDecisorioIGrado(FlussiForm form, String codUtente,
			long idUffOwn) {

		return flussiDao.processFlussoDecisorioIGrado(form.getIdFlusso(),
				codUtente, idUffOwn, form.getAttoDto()
						.getDATA_DEPOSITO_ASSOCIA(), form.getAllegato()
						.getDocumento(), form.getAllegato().getContentType(),
				form.getAllegato().getFileName());
	}

	public List processFlussoDecretoUdienza(FlussiForm form, String codUtente,
			long idUffOwn) {

		return flussiDao.processFlussoDecretoUdienza(form.getIdFlusso()
				.longValue(), codUtente, idUffOwn, form.getAttoDto()
				.getDATA_DEPOSITO_ASSOCIA(), form.getAllegato().getDocumento(),
				form.getAllegato().getContentType(), form.getAllegato()
						.getFileName());
	}

	public List processFlussoVerbaleUdienza(FlussiForm form, String codUtente,
			long idUffOwn) {

		return flussiDao.processFlussoVerbaleUdienza(form.getIdFlusso()
				.longValue(), codUtente, idUffOwn, form.getAttoDto()
				.getDATA_DEPOSITO_ASSOCIA(), form.getAllegato().getDocumento(),
				form.getAllegato().getContentType(), form.getAllegato()
						.getFileName());
	}

	public List processFlussoRemAssRuoloUdienza(FlussiForm form,
			String codUtente, long idUffOwn) {

		return flussiDao.processFlussoRemAssRuoloUdienza(form.getIdFlusso()
				.longValue(), codUtente, idUffOwn, form.getAttoDto()
				.getDATA_DEPOSITO_ASSOCIA(), form.getAllegato().getDocumento(),
				form.getAllegato().getContentType(), form.getAllegato()
						.getFileName());
	}

	public List processFlussoDecretoConvalidaSequestro(FlussiForm form,
			String codUtente, long idUffOwn) {

		return flussiDao.processFlussoDecretoConvalidaSequestro(form
				.getIdFlusso().longValue(), codUtente, idUffOwn, form
				.getAttoDto().getDATA_DEPOSITO_ASSOCIA(), form.getAttoDto()
				.getDATA_RICEZIONE_ASSOCIA(),
				form.getAllegato().getDocumento(), form.getAllegato()
						.getContentType(), form.getAllegato().getFileName());
	}

	public List processFlussoPerditaEfficaciaSequestro(FlussiForm form,
			String codUtente, long idUffOwn) {

		return flussiDao.processFlussoPerditaEfficaciaSequestro(form
				.getIdFlusso().longValue(), codUtente, idUffOwn, form
				.getAttoDto().getDATA_DEPOSITO_ASSOCIA(), form.getAttoDto()
				.getDATA_RICEZIONE_ASSOCIA(),
				form.getAllegato().getDocumento(), form.getAllegato()
						.getContentType(), form.getAllegato().getFileName());
	}

	public List processFlussoDecretoRevocaSequestro(FlussiForm form,
			String codUtente, long idUffOwn) {

		return flussiDao.processFlussoDecretoRevocaSequestro(form.getIdFlusso()
				.longValue(), codUtente, idUffOwn, form.getAttoDto()
				.getDATA_DEPOSITO_ASSOCIA(), form.getAttoDto()
				.getDATA_RICEZIONE_ASSOCIA(),
				form.getAllegato().getDocumento(), form.getAllegato()
						.getContentType(), form.getAllegato().getFileName());
	}

	/**
	 * @param flussiDao
	 *            the flussiDao to set
	 */
	public void setFlussiDao(FlussiDao flussiDao) {

		this.flussiDao = flussiDao;
	}

	/**
	 * Si ricava elenco dei fields della classe
	 * 
	 * @param aClass
	 *            oggetto dto
	 * @return array di stringhe contenente i nomi dei fields della classe
	 */
	private String[] getClassFields(Object aClass) {

		Field[] campi = aClass.getClass().getDeclaredFields();
		String[] campiStringa = new String[campi.length];
		for (int i = 0; i < campi.length; i++) {
			campiStringa[i] = campi[i].getName();
		}
		return campiStringa;
	}

	/**
	 * Attraverso la reflection si ottengono le informazioni e i dati degli
	 * oggetti DTO
	 * 
	 * @param aClass
	 *            oggetto dto
	 * @return nome della classe dto
	 */
	private String getClassName(Object aClass) {

		return aClass.getClass().getSimpleName();
	}

	/**
	 * Permette di invocare le property di tipo get sull'oggetto
	 * 
	 * @param name
	 *            nome del field
	 * @param target
	 *            oggetto dto da elaborare
	 * @return valore del field
	 */
	private String getProperty(String name, Object target) {

		String output = new String();
		Method metodo = null;
		String nameToUpperCase = name.toUpperCase();

		try {
			metodo = target.getClass().getMethod("get" + nameToUpperCase, null);
		} catch (NoSuchMethodException exc) {
			logger.error("Errore in ElaboraFlussiService.getProperty method");
		}
		if (metodo != null) {

			try {
				output = (String) metodo.invoke(target, new Object[0]);
			} catch (Exception e) {
				logger.error("FlussiServiceImpl.getProperty Errore durante l'invocazione del metodo getter:"
						+ e);
			}
		}
		return output;
	}

	private void setProperty(String name, Object obj, String value) {

		Class<?> parametri[] = { String.class };

		try {
			obj.getClass().getMethod("set" + name, parametri)
					.invoke(obj, value);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// e.printStackTrace();
			logger.error("FlussiServiceImpl.setProperty Errore durante l'invocazione del metodo setter per "
					+ name);
		}
	}

	private void setPropertyObj(String name, Object obj, Object value) {

		Class<?> parametri[] = { String.class };

		try {
			obj.getClass().getMethod("set" + name, parametri)
					.invoke(obj, value);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// e.printStackTrace();
			logger.error("FlussiServiceImpl.setPropertyObj Errore durante l'invocazione del metodo setter per "
					+ name);
		}
	}
	
	public List processFlussoDecretoRigettoTotaleSequestro(FlussiForm form,
			String codUtente, long idUffOwn) {
		return flussiDao.processFlussoDecretoRigettoTotaleSequestro(form
				.getIdFlusso().longValue(), codUtente, idUffOwn, form
				.getAttoDto().getDATA_DEPOSITO_ASSOCIA(), form.getAllegato()
				.getDocumento(), form.getAllegato().getContentType(), form
				.getAllegato().getFileName());
	}

	public List processFlussoOpposizioneDissequestro(FlussiForm form,
			String codUtente, long idUffOwn) {

		return flussiDao.processFlussoOpposizioneDissequestro(form
				.getIdFlusso(), codUtente, idUffOwn, form.getAttoDto()
				.getDATA_DEPOSITO_ASSOCIA(), form.getAllegato().getDocumento(),
				form.getAllegato().getContentType(), form.getAllegato()
						.getFileName());
	}

	public List processFlussoRevocaOpposizioneDissequestro(FlussiForm form,
			String codUtente, long idUffOwn) {

		return flussiDao.processFlussoRevocaOpposizioneDissequestro(form
				.getIdFlusso(), codUtente, idUffOwn, form.getAttoDto()
				.getDATA_DEPOSITO_ASSOCIA(), form.getAllegato().getDocumento(),
				form.getAllegato().getContentType(), form.getAllegato()
						.getFileName());
	}

	public List processFlussoRevocazioneConfisca(FlussiForm form,
			String codUtente, long idUffOwn) {

		return flussiDao.processFlussoRevocazioneConfisca(form.getIdFlusso(),
				codUtente, idUffOwn, form.getAttoDto()
						.getDATA_DEPOSITO_ASSOCIA(), form.getAllegato()
						.getDocumento(), form.getAllegato().getContentType(),
				form.getAllegato().getFileName());
	}

	public List processFlussoSentenzaOpposizioneDissequestro(FlussiForm form,
			String codUtente, long idUffOwn) {

		return flussiDao.processFlussoSentenzaOpposizioneDissequestro(form
				.getIdFlusso(), codUtente, idUffOwn, form.getAttoDto()
				.getDATA_DEPOSITO_ASSOCIA(), form.getAllegato().getDocumento(),
				form.getAllegato().getContentType(), form.getAllegato()
						.getFileName());
	}

	public List processFlussoSentenzaRevocazioneConfisca(FlussiForm form,
			String codUtente, long idUffOwn) {

		return flussiDao.processFlussoSentenzaRevocazioneConfisca(form
				.getIdFlusso(), codUtente, idUffOwn, form.getAttoDto()
				.getDATA_DEPOSITO_ASSOCIA(), form.getAllegato().getDocumento(),
				form.getAllegato().getContentType(), form.getAllegato()
						.getFileName());
	}

	public List processFlussoRicorsoInAppello(FlussiForm form,
			String codUtente, long idUffOwn, long idProcedimentoCda) {

		return flussiDao.processFlussoRicorsoInAppello(form.getIdFlusso(),
				codUtente, idUffOwn, form.getAttoDto()
						.getDATA_DEPOSITO_ASSOCIA(), form.getAllegato()
						.getDocumento(), form.getAllegato().getContentType(),
				form.getAllegato().getFileName(), idProcedimentoCda);
	}

	public List processFlussoAttoRifiutato(FlussiForm form, String codUtente,
			long idUffOwn) {

		return flussiDao.processFlussoAttoRifiutato(form.getIdFlusso(),
				codUtente, idUffOwn);
	}

	public List processFlussoDecDecisorioIIGrado(FlussiForm form,
			String codUtente, long idUffOwn) {

		return flussiDao.processFlussoDecDecisorioIIGrado(form.getIdFlusso(),
				codUtente, idUffOwn, form.getAttoDto()
						.getDATA_DEPOSITO_ASSOCIA(), form.getAttoDto()
						.getDATA_RICEZIONE_ASSOCIA(), form.getAllegato()
						.getDocumento(), form.getAllegato().getContentType(),
				form.getAllegato().getFileName());
	}
	
	
	public List processFlussoProvvIstFascicolo(FlussiForm form,
			String codUtente, long idUffOwn) {

		return flussiDao.processFlussoProvvIstFascicolo(form.getIdFlusso(),
				codUtente, idUffOwn, form.getAttoDto()
						.getDATA_DEPOSITO_ASSOCIA(), form.getAttoDto()
						.getDATA_RICEZIONE_ASSOCIA(), form.getAllegato()
						.getDocumento(), form.getAllegato().getContentType(),
				form.getAllegato().getFileName());
	}	

	public List processFlussoParere(FlussiForm form, String codUtente,
			long idUffOwn) {

		return flussiDao.processFlussoParere(form.getIdFlusso(), codUtente,
				idUffOwn, form.getAttoDto().getDATA_DEPOSITO_ASSOCIA(), form
						.getAllegato().getDocumento(), form.getAllegato()
						.getContentType(), form.getAllegato().getFileName());
	}

	public List processFlussoVersionamento(FlussiForm form, String codUtente,
			long idUffOwn) {

		return flussiDao.processFlussoVersionamento(form.getIdFlusso(),
				codUtente, idUffOwn, form.getAttoDto()
						.getDATA_RICEZIONE_ASSOCIA(), form.getAllegato()
						.getDocumento(), form.getAllegato().getContentType(),
				form.getAllegato().getFileName());
	}

	public List processFlussoRicorsoInCassazione(FlussiForm form,
			String codUtente, long idUffOwn) {

		return flussiDao.processFlussoRicorsoInCassazione(form.getIdFlusso(),
				codUtente, idUffOwn, form.getAttoDto()
						.getDATA_DEPOSITO_ASSOCIA(), form.getAllegato()
						.getDocumento(), form.getAllegato().getContentType(),
				form.getAllegato().getFileName());
	}

	public List processFlussoProrogaSequestro(FlussiForm form,
			String codUtente, long idUffOwn) {

		return flussiDao.processFlussoProrogaSequestro(form.getIdFlusso(),
				codUtente, idUffOwn, form.getAttoDto()
						.getDATA_RICEZIONE_ASSOCIA(), form.getAllegato()
						.getDocumento(), form.getAllegato().getContentType(),
				form.getAllegato().getFileName());
	}

	public List processFlussoSospensioneTemporanea(FlussiForm form,
			String codUtente, long idUffOwn) {

		return flussiDao.processFlussoSospensioneTemporanea(form.getIdFlusso(),
				codUtente, idUffOwn, form.getAttoDto()
						.getDATA_DEPOSITO_ASSOCIA(), form.getAttoDto()
						.getDATA_RICEZIONE_ASSOCIA(), form.getAllegato()
						.getDocumento(), form.getAllegato().getContentType(),
				form.getAllegato().getFileName());
	}

	public List processFlussoProrogaSospensioneTemporanea(FlussiForm form,
			String codUtente, long idUffOwn) {

		return flussiDao.processFlussoProrogaSospensioneTemporanea(form
				.getIdFlusso(), codUtente, idUffOwn, form.getAttoDto()
				.getDATA_DEPOSITO_ASSOCIA(), form.getAttoDto()
				.getDATA_RICEZIONE_ASSOCIA(),
				form.getAllegato().getDocumento(), form.getAllegato()
						.getContentType(), form.getAllegato().getFileName());
	}

	public List processFlussoSequestroSospensioneTemporanea(FlussiForm form,
			String codUtente, long idUffOwn) {

		return flussiDao.processFlussoSequestroSospensioneTemporanea(form
				.getIdFlusso(), codUtente, idUffOwn, form.getAttoDto()
				.getDATA_DEPOSITO_ASSOCIA(), form.getAttoDto()
				.getDATA_RICEZIONE_ASSOCIA(),
				form.getAllegato().getDocumento(), form.getAllegato()
						.getContentType(), form.getAllegato().getFileName());
	}

	public List processFlussoApplicazioneProvv(FlussiForm form,
			String codUtente, long idUffOwn) {

		return flussiDao.processFlussoApplicazioneProvv(form.getIdFlusso(),
				codUtente, idUffOwn, form.getAttoDto()
						.getDATA_DEPOSITO_ASSOCIA(), form.getAttoDto()
						.getDATA_RICEZIONE_ASSOCIA(), form.getAllegato()
						.getDocumento(), form.getAllegato().getContentType(),
				form.getAllegato().getFileName());
	}

	public List processFlussoSentenzaCassazione(FlussiForm form,
			String codUtente, long idUffOwn) {

		return flussiDao.processFlussoSentenzaCassazione(form.getIdFlusso(),
				codUtente, idUffOwn, form.getAttoDto()
						.getDATA_DEPOSITO_ASSOCIA(), form.getAttoDto()
						.getDATA_RICEZIONE_ASSOCIA(), form.getAllegato()
						.getDocumento(), form.getAllegato().getContentType(),
				form.getAllegato().getFileName());
	}

	public TFlussiIn ricercaTFlussiIn(long idAtto, int idUffOwn, long tipoFlusso) {

		List queryResults = flussiDao.ricercaTFlussiIn(idAtto, idUffOwn,
				tipoFlusso);
		TFlussiIn flusso = null;

		if ((queryResults == null) || queryResults.isEmpty()) {
			return null;
		}

		Iterator iterator = queryResults.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			flusso = (TFlussiIn) row.get("FLUSSIIN");
		}
		return flusso;

	}

	public List processFlussoProcCDAdaCass(long idFlussoIn, String codUtente,
			long idUffOwn) {

		return flussiDao.processFlussoProcCDAdaCass(idFlussoIn, codUtente,
				idUffOwn);
	}
	
	public List processFlussoProcTridaCass(long idFlussoIn, String codUtente,
			long idUffOwn) {

		return flussiDao.processFlussoProcTridaCass(idFlussoIn, codUtente,
				idUffOwn);
	}
	
	public List processFlussoProcTridaCdA(long idFlussoIn, String codUtente,
			long idUffOwn) {

		return flussiDao.processFlussoProcTridaCdA(idFlussoIn, codUtente,
				idUffOwn);
	}	
	

	public List processFlussoParereFascicolo(FlussiForm form, String codUtente,
			long idUffOwn) {

		return flussiDao.processFlussoParereFascicolo(form.getIdFlusso(),
				codUtente, idUffOwn, form.getAttoDto()
						.getDATA_DEPOSITO_ASSOCIA(), form.getAllegato()
						.getDocumento(), form.getAllegato().getContentType(),
				form.getAllegato().getFileName());
	}

	public List processFlussoUdienzaFascicolo(FlussiForm form,
			String codUtente, long idUffOwn) {

		return flussiDao.processFlussoUdienzaFascicolo(form.getIdFlusso()
				.longValue(), codUtente, idUffOwn, form.getAttoDto()
				.getDATA_DEPOSITO_ASSOCIA(), form.getAllegato().getDocumento(),
				form.getAllegato().getContentType(), form.getAllegato()
						.getFileName());
	}

	public List processFlussoDecisorioIGradoFascicolo(FlussiForm form,
			String codUtente, long idUffOwn) {

		return flussiDao.processFlussoDecisorioIGradoFascicolo(form
				.getIdFlusso(), codUtente, idUffOwn, form.getAttoDto()
				.getDATA_DEPOSITO_ASSOCIA(), form.getAllegato().getDocumento(),
				form.getAllegato().getContentType(), form.getAllegato()
						.getFileName());
	}
	
	public void getDatiAccetazioneDeposito(FlussiForm flussiForm, int idUffDest, int idUffMit, long idAtto){
						
		TNotifPdoc notifPdoc = flussiDao.getNotificaPDoc(idUffDest,idAtto,idUffMit);
				
		if(notifPdoc != null && notifPdoc.getNumeCopiaPdoc() != null) {
						
			long numeCopiaPdoc = notifPdoc.getNumeCopiaPdoc();
			
			TDepositi deposito = flussiDao.getDeposito(idUffMit, idUffDest, numeCopiaPdoc);
			
			if(deposito != null && deposito.getDataRicezione() != null) {
								
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");				
				
				AllegatoForm allegato = new AllegatoForm();
				allegato.setFileName(notifPdoc.getDescNomeDocumento());
				allegato.setContentType(notifPdoc.getDescTipoDocumento());
				
				flussiForm.setAllegato(allegato);
				flussiForm.getAttoDto().setDATA_DEPOSITO_ASSOCIA(formatter.format(deposito.getDataRicezione()));
				flussiForm.setAccettazioneDeposito(true);
			}
		}
	}
	
	public AllegatoForm downloadDocumentoAllegato(FlussiForm flussiForm, int idUffDest, int idUffMit, long idAtto) throws IOException {
		
		AllegatoForm result = null;		
		TNotifPdoc notifPdoc = flussiDao.getNotificaPDoc(idUffDest,idAtto,idUffMit);
		
		if(notifPdoc != null && notifPdoc.getNumeCopiaPdoc() != null) {
			long numeCopiaPdoc = notifPdoc.getNumeCopiaPdoc();			
			byte[] documento = documentaleService.recuperoDocumento(Utility.longToBigInteger(numeCopiaPdoc),idUffDest);
			
			if(documento == null)
				throw new IOException("Errore durante la lettura dal documentale");
			
			result = new AllegatoForm();
			result.setDocumento(Hibernate.createBlob(documento));
			result.setFileName(notifPdoc.getDescNomeDocumento());
			result.setContentType(notifPdoc.getDescTipoDocumento());
		}
		
		return result;
	}
	
	public void processAccettazioneDeposito(int idUffDest, int idUffMit, long idAtto, String descUtente) throws IOException {
			
		try {
			
			TNotifPdoc notifPdoc = flussiDao.getNotificaPDoc(idUffDest,idAtto,idUffMit);
			
			if(notifPdoc != null && notifPdoc.getNumeCopiaPdoc() != null) {
				
				long numeCopiaPdoc = notifPdoc.getNumeCopiaPdoc();
				TDepositi deposito = flussiDao.getDeposito(idUffMit, idUffDest, numeCopiaPdoc);
				
				if(deposito != null && deposito.getDescIdDepositoGl() != null && !"".equals(deposito.getDescIdDepositoGl())) {
					
					//Invoke del servizio assegnaDeposito()
					
					AssegnaDeposito assegnaDeposito = new AssegnaDeposito();
					assegnaDeposito.setRegistro("MP");
					assegnaDeposito.setIdFascicolo(idAtto + "");
					assegnaDeposito.setIdBusta(deposito.getDescIdDepositoGl());		
					serviziAccettazioneServiceClient.assegnaDeposito(assegnaDeposito,deposito.getDescIdDestinatario());
					
					//Invoke del servizio accettaDeposito()
					AccettaDeposito accettaDeposito = new AccettaDeposito();
					accettaDeposito.setIdBusta(deposito.getDescIdDepositoGl());					
					serviziAccettazioneServiceClient.accettaDeposito(accettaDeposito,deposito.getDescIdDestinatario());
					
					//Se l'invocazione non va in errore (eccezione) viene effettuata la registrazione dell'avvenuto deposito
					deposito.setCodiFkIdAtto(idAtto);
					deposito.setFlgAccettato('S');	
					deposito.setDataAccettazione(new Date());
					deposito.setDescUtente(descUtente);
					flussiDao.updateDeposito(deposito);
				}
			}			
		} catch (Exception ex) {
			throw new IOException("Errore durante la comunicazione di accettazione deposito" + ex.getMessage());
		}		 
	}
	
	@SuppressWarnings({ "unchecked", "unchecked" })
	public List<FlussiPoliziaForm> getListFlussiPolizia(int idUffOwn, FlussiPoliziaForm flussiForm, String flgDirezione) {
		List<FlussiPoliziaDto> queryResults = null;
		List<FlussiPoliziaForm> risultati = new ArrayList<FlussiPoliziaForm>();
		queryResults = flussiDao.getListFlussiPolizia(new Long(idUffOwn).longValue(), flgDirezione);
		Iterator<FlussiPoliziaDto> iterator = queryResults.iterator();
		while (iterator.hasNext()) {
			FlussiPoliziaDto flusso = iterator.next();
			FlussiPoliziaForm form = new FlussiPoliziaForm();
			if (flusso != null) {
				mapperBean.map(flusso, form);
			}
			risultati.add(form);
		}
		return risultati;
	}
	
	public FlussiPoliziaForm caricaDettaglioFlussoIngresso(FlussiPoliziaForm flussiForm){
		ArrayList<Object> objList = leggiPoliziaXML(flussiForm);
		if(objList!=null){
			List<SoggettoForm> listSoggettoFis = new ArrayList<SoggettoForm>();
			List<SoggettoForm> listSoggettoGiu = new ArrayList<SoggettoForm>();
			List<SoggettoForm> listSoggetto = new ArrayList<SoggettoForm>();
			List<MisuraPersonaleFlussiForm> listMisuraPersonale = new ArrayList<MisuraPersonaleFlussiForm>();
			List<MisuraPatrimonialeFlussiForm> listMisurePatrimoniali = new ArrayList<MisuraPatrimonialeFlussiForm>();
			HashMap<String, SoggettoForm> mapPf = new HashMap<String, SoggettoForm>();
			HashMap<String, SoggettoForm> mapPg = new HashMap<String, SoggettoForm>();
			List<BeneSoggettoPoliziaForm> listBeneSoggPatr = new ArrayList<BeneSoggettoPoliziaForm>();
			switch (flussiForm.getIdServizio().intValue()) {
			case Constants.POLIZIA_ISCRIZIONE_INDAGINI:	//17
				//IscrizioniIndaginiType
				for(Iterator<Object> itr = objList.iterator(); itr.hasNext();){
					Object obj = (Object)itr.next();
					if (obj instanceof DatiAutoritaProcedenteType) {
						DatiAutoritaProcedenteType elem = (DatiAutoritaProcedenteType) obj;
						TAnUfficio tUfficio = commonService.getTAnUfficioByIdUff(new Long(elem.getID_AUTORITA()).longValue());
						UfficiForm ufficioForm = new UfficiForm();
						ufficioForm.setItemTipoUfficio(new ComboForm());
						ufficioForm.getItemTipoUfficio().setId(""+tUfficio.getTTpTipoUfficio().getCodiKIdTipoUff());
						ufficioForm.getItemTipoUfficio().setDescrizione(tUfficio.getTTpTipoUfficio().getDescTipoUff());
						ufficioForm.setDescUfficio(tUfficio.getDescUfficio());
						ufficioForm.setDescIndirizzoSede(tUfficio.getDescIndirizzoSede());
						flussiForm.setDataEmissione(elem.getDATA_EMISSIONE());
						flussiForm.setDescrNumAccert(elem.getDESCR_NUM_ACCERT());
						ufficioForm.setCodiKIdUfficio(new Long(elem.getID_AUTORITA()).intValue());
						flussiForm.setUfficioMittente(ufficioForm);
						flussiForm.setIdUffMittente(new Long(elem.getID_AUTORITA()));
						flussiForm.setDescrNote(elem.getDESCR_NOTE());
					}else if(obj instanceof DatiUfficioDestinatarioType){
						DatiUfficioDestinatarioType elem = (DatiUfficioDestinatarioType)obj;
						TAnUfficio tUfficio = commonService.getTAnUfficioByIdUff(new Long(elem.getID_UFFICIO()).longValue());
						UfficiForm ufficioForm = new UfficiForm();
						ufficioForm.setItemTipoUfficio(new ComboForm());
						ufficioForm.getItemTipoUfficio().setId(""+tUfficio.getTTpTipoUfficio().getCodiKIdTipoUff());
						ufficioForm.getItemTipoUfficio().setDescrizione(tUfficio.getTTpTipoUfficio().getDescTipoUff());
						ufficioForm.setDescUfficio(tUfficio.getDescUfficio());
						ufficioForm.setDescIndirizzoSede(tUfficio.getDescIndirizzoSede());
						ufficioForm.setCodiKIdUfficio(new Long(elem.getID_UFFICIO()).intValue());
						flussiForm.setUfficioDestinatario(ufficioForm);
						flussiForm.setIdUffDestinatario(new Long(elem.getID_UFFICIO()));
					} else if(obj instanceof it.giustizia.smp.webservice.server.polizia.DatiSoggettoFisicoType){
						it.giustizia.smp.webservice.server.polizia.DatiSoggettoFisicoType elem = (it.giustizia.smp.webservice.server.polizia.DatiSoggettoFisicoType)obj;
						SoggettoForm soggettoForm = new SoggettoForm();
						mapperBean.map(elem, soggettoForm);
						String descrRuolo = (!elem.getID_RUOLO().equalsIgnoreCase("1") ? (elem.getID_RUOLO().equals("2") ? "TERZO" : "INTERVENIENTE") : "DIRETTO"); 
						soggettoForm.setDescrRuolo(descrRuolo);
						String descrPrev = (!elem.getID_TIPO_PREV().equalsIgnoreCase("1") ? (elem.getID_RUOLO().equals("2") ? "Antimafia" : "Antiterrorismo") : "Ordinaria"); 
						soggettoForm.setTipologiaPrevenzione(new ComboForm());
						soggettoForm.getTipologiaPrevenzione().setDescrizione(descrPrev);
						try {
							//TTpTipoMisura tipoMis = commonService.getTTpTipoMisuraById(new Long(elem.getID_IPOTESI_MIS()).longValue());
							TTpTipoProc tipoProc = commonService.getTTpTipoProcById(new Long(elem.getID_IPOTESI_MIS()).longValue());
							soggettoForm.setMisura(new MisuraForm());
							soggettoForm.getMisura().setDescrizioneMisura(tipoProc.getDescTipoProc());
						} catch (Exception e) {
							System.out.println("Errore nel recupero del tipo misura per il servizio "+flussiForm.getIdServizio());
							soggettoForm.setMisura(new MisuraForm());
							soggettoForm.getMisura().setDescrizioneMisura("");
						}
						try {
							//TAnNormative tNormative = commonService.getTAnNormativeById(new Long(elem.getID_NORMATIVA_RIF()).longValue());
							TTpTipoPrevProc tipoPrevProc = commonService.getTTpTipoPrevProc(new Long(elem.getID_NORMATIVA_RIF()).longValue());
							soggettoForm.setNormativa(new ComboForm());
							soggettoForm.getNormativa().setDescrizione(tipoPrevProc.getDescTipoNorm());
						} catch (Exception e) {
							System.out.println("Errore nel recupero del tipo normativa di riferiemnto per il servizio "+flussiForm.getIdServizio());
							soggettoForm.setNormativa(new ComboForm());
							soggettoForm.getNormativa().setDescrizione("");
						}
						soggettoForm.setTipoSoggetto(Constants.SOGGETTO_FISICO);
						
						String idComune = elem.getID_ISTAT_COMUNE_NAS();
						if(idComune.length()!=0){
							TAnComuni comune = commonService.getComuneByDescIstatComune(idComune);
							soggettoForm.setComuneNascita(new ComboForm());
							soggettoForm.getComuneNascita().setDescrizione(comune.getDescComune());
							soggettoForm.getComuneNascita().setId(idComune);
						}
						soggettoForm.setDataNascita(converterStringToDate(elem.getDATA_NASCITA()));
						
						listSoggettoFis.add(soggettoForm);
					} else if(obj instanceof it.giustizia.smp.webservice.server.polizia.DatiSoggettoGiuridicoType){
						it.giustizia.smp.webservice.server.polizia.DatiSoggettoGiuridicoType elem = (it.giustizia.smp.webservice.server.polizia.DatiSoggettoGiuridicoType)obj;
						SoggettoForm soggettoForm = new SoggettoForm();
						String descrRuolo = (!elem.getID_RUOLO().equalsIgnoreCase("1") ? (elem.getID_RUOLO().equals("2") ? "TERZO" : "INTERVENIENTE") : "DIRETTO"); 
						soggettoForm.setDescrRuolo(descrRuolo);
						String descrPrev = (!elem.getID_TIPO_PREV().equalsIgnoreCase("1") ? (elem.getID_RUOLO().equals("2") ? "Antimafia" : "Antiterrorismo") : "Ordinaria"); 
						soggettoForm.setTipologiaPrevenzione(new ComboForm());
						soggettoForm.getTipologiaPrevenzione().setDescrizione(descrPrev);
						try {
							//TTpTipoMisura tipoMis = commonService.getTTpTipoMisuraById(new Long(elem.getID_IPOTESI_MIS()).longValue());
							TTpTipoProc tipoProc = commonService.getTTpTipoProcById(new Long(elem.getID_IPOTESI_MIS()).longValue());
							soggettoForm.setMisura(new MisuraForm());
							soggettoForm.getMisura().setDescrizioneMisura(tipoProc.getDescTipoProc());
						} catch (Exception e) {
							System.out.println("Errore nel recupero del tipo misura per il servizio "+flussiForm.getIdServizio());
							soggettoForm.setMisura(new MisuraForm());
							soggettoForm.getMisura().setDescrizioneMisura("");
						}
						try {
							//TAnNormative tNormative = commonService.getTAnNormativeById(new Long(elem.getID_NORMATIVA_RIF()).longValue());
							TTpTipoPrevProc tipoPrevProc = commonService.getTTpTipoPrevProc(new Long(elem.getID_NORMATIVA_RIF()).longValue());
							soggettoForm.setNormativa(new ComboForm());
							soggettoForm.getNormativa().setDescrizione(tipoPrevProc.getDescTipoNorm());
						} catch (Exception e) {
							System.out.println("Errore nel recupero del tipo normativa di riferiemnto per il servizio "+flussiForm.getIdServizio());
							soggettoForm.setNormativa(new ComboForm());
							soggettoForm.getNormativa().setDescrizione("");
						}
						soggettoForm.setTipoSoggetto(Constants.SOGGETTO_GIURIDICO);
						mapperBean.map(elem, soggettoForm);
						listSoggettoGiu.add(soggettoForm);
					}
				}
				flussiForm.setListSoggettoFis(!listSoggettoFis.isEmpty() ? listSoggettoFis : null);
				flussiForm.setListSoggettoGiu(!listSoggettoGiu.isEmpty() ? listSoggettoGiu : null);
				break;
			case Constants.POLIZIA_ESITO_INDAGINE://18
				//EsitoIndaginiType
				for(Iterator<Object> itr = objList.iterator(); itr.hasNext();){
					Object obj = (Object)itr.next();
					if (obj instanceof DatiUfficioGiudiziarioType) {
						DatiUfficioGiudiziarioType elem = (DatiUfficioGiudiziarioType) obj;
						long idAtto = new Long(elem.getID_ATTO()).longValue();
						long idUff = new Long(elem.getID_UFFICIO()).longValue();
						long idProcedimento = elem.getID_PROCEDIMENTO().length()!=0? new Long(elem.getID_PROCEDIMENTO()).longValue() : 0;
						
						if(idProcedimento!=0){
							ProcedimentoForm procedimentoForm = procedimentoService.getDettaglioProcedimento(idProcedimento);
							flussiForm.setProcedimento(procedimentoForm);
							flussiForm.setIdProcedimento(procedimentoForm.getIdProcedimento());
						}
						
						if(idUff!=0){
							flussiForm.setIdUffDestinatario(new Long(elem.getID_UFFICIO()));
							AttoForm attoForm = attoService.getAttoById(idAtto, idUff);
							flussiForm.setAtto(attoForm);
							flussiForm.setIdAtto(idAtto);
						}
					} else if(obj instanceof DatiAutoritaMittenteType){//DatiAutoritaMittente
						DatiAutoritaMittenteType elem = (DatiAutoritaMittenteType) obj;
						try {
							Long idAutorita = new Long(elem.getID_AUTORITA()).longValue();
							TAnUfficio tUfficio = commonService.getTAnUfficioByIdUff(new Long(idAutorita).longValue());
							UfficiForm ufficioForm = new UfficiForm();
							ufficioForm.setItemTipoUfficio(new ComboForm());
							ufficioForm.getItemTipoUfficio().setId(""+tUfficio.getTTpTipoUfficio().getCodiKIdTipoUff());
							ufficioForm.getItemTipoUfficio().setDescrizione(tUfficio.getTTpTipoUfficio().getDescTipoUff());
							ufficioForm.setDescUfficio(tUfficio.getDescUfficio());
							ufficioForm.setDescIndirizzoSede(tUfficio.getDescIndirizzoSede());
							ufficioForm.setCodiKIdUfficio(idAutorita.intValue());
							flussiForm.setUfficioMittente(ufficioForm);
						} catch (Exception e) {
							System.out.println("Errore nel recupero idAutorita idServizio 17");
						}
					} else if(obj instanceof DatiEsitoIndagatiType){
						DatiEsitoIndagatiType elem = (DatiEsitoIndagatiType) obj;
						mapperBean.map(elem, flussiForm);
						try {
							/*TTpEsito esito = procedimentoService.getTTpEsito(new Long(elem.getDESCR_NUM_ESITO()));
							flussiForm.setDescrEsito(esito.getDescEsito());*/
							flussiForm.setDescrEsito(elem.getDESCR_NUM_ESITO());
						} catch (Exception e) {
							System.out.println("Errore per la ricerca del dettaglio dell'esito");
						}
					} else if(obj instanceof DatiSoggettiIndagatiType){
						try {
							DatiSoggettiIndagatiType elem = (DatiSoggettiIndagatiType) obj;
							long idSoggetto = new Long(elem.getID_SOGGETTO()).longValue();
							int idUff = flussiForm.getIdUffDestinatario().intValue();
							long idProc = flussiForm.getIdProcedimento().longValue();
							
							if(idUff!=0 && idProc!=0){
								SoggettoForm soggettoForm = procedimentoService.getPersonaById(idSoggetto, idUff, idProc);
								listSoggetto.add(soggettoForm);
							}
						} catch (Exception e) {
							System.out.println("Errore nel caricamento della lista dei soggetti indagati: idservizio 18");
						}
					}
				}
				flussiForm.setListSoggetto(!listSoggetto.isEmpty() ? listSoggetto : null);
				break;
			case Constants.POLIZIA_PROPOSTA_MIS_PERS_PATR://19
				//PropostaMisuraPatrPersType
				for(Iterator<Object> itr = objList.iterator(); itr.hasNext();){
					Object obj = (Object)itr.next();
					if (obj instanceof DatiAutoritaProponenteType) {
						DatiAutoritaProponenteType elem = (DatiAutoritaProponenteType) obj;
						TAnUfficio tUfficio = commonService.getTAnUfficioByIdUff(new Long(elem.getID_AUTORITA()).longValue());
						UfficiForm ufficioForm = new UfficiForm();
						ufficioForm.setItemTipoUfficio(new ComboForm());
						ufficioForm.getItemTipoUfficio().setId(""+tUfficio.getTTpTipoUfficio().getCodiKIdTipoUff());
						ufficioForm.getItemTipoUfficio().setDescrizione(tUfficio.getTTpTipoUfficio().getDescTipoUff());
						ufficioForm.setDescUfficio(tUfficio.getDescUfficio());
						ufficioForm.setDescIndirizzoSede(tUfficio.getDescIndirizzoSede());
						flussiForm.setDataEmissione(elem.getDATA_EMISSIONE());
						flussiForm.setDescrNumAccert(elem.getDESCR_NUM_ACCERT());
						flussiForm.setDescrNumProposta(elem.getDESCR_NUM_PROPOSTA());
						ufficioForm.setCodiKIdUfficio(new Long(elem.getID_AUTORITA()).intValue());
						flussiForm.setUfficioMittente(ufficioForm);
						flussiForm.setIdUffMittente(new Long(elem.getID_AUTORITA()));
						flussiForm.setDescrNote(elem.getDESCR_NOTE());
					}
					else if(obj instanceof DatiTribunaleCompetenteType){
						DatiTribunaleCompetenteType elem = (DatiTribunaleCompetenteType) obj;
						TAnUfficio tUfficio = commonService.getTAnUfficioByIdUff(new Long(elem.getID_UFFICIO()).longValue());
						UfficiForm ufficioForm = new UfficiForm();
						ufficioForm.setItemTipoUfficio(new ComboForm());
						ufficioForm.getItemTipoUfficio().setId(""+tUfficio.getTTpTipoUfficio().getCodiKIdTipoUff());
						ufficioForm.getItemTipoUfficio().setDescrizione(tUfficio.getTTpTipoUfficio().getDescTipoUff());
						ufficioForm.setDescUfficio(tUfficio.getDescUfficio());
						ufficioForm.setDescIndirizzoSede(tUfficio.getDescIndirizzoSede());
						ufficioForm.setCodiKIdUfficio(new Long(elem.getID_UFFICIO()).intValue());
						flussiForm.setUfficioDestinatario(ufficioForm);
						flussiForm.setIdUffDestinatario(new Long(elem.getID_UFFICIO()));
					}
					else if(obj instanceof it.giustizia.smp.webservice.server.polizia.DatiSoggettoFisicoType){
						it.giustizia.smp.webservice.server.polizia.DatiSoggettoFisicoType elem = (it.giustizia.smp.webservice.server.polizia.DatiSoggettoFisicoType)obj;
						SoggettoForm soggettoForm = new SoggettoForm();
						String descrRuolo = (!elem.getID_RUOLO().equalsIgnoreCase("1") ? (elem.getID_RUOLO().equals("2") ? "TERZO" : "INTERVENIENTE") : "DIRETTO"); 
						soggettoForm.setDescrRuolo(descrRuolo);
						String descrPrev = (!elem.getID_TIPO_PREV().equalsIgnoreCase("1") ? (elem.getID_RUOLO().equals("2") ? "Antimafia" : "Antiterrorismo") : "Ordinaria"); 
						soggettoForm.setTipologiaPrevenzione(new ComboForm());
						soggettoForm.getTipologiaPrevenzione().setDescrizione(descrPrev);
						//TTpTipoMisura tipoMis = commonService.getTTpTipoMisuraById(new Long(elem.getID_IPOTESI_MIS()).longValue());
						TTpTipoProc tipoProc = commonService.getTTpTipoProcById(new Long(elem.getID_IPOTESI_MIS()).longValue());
						soggettoForm.setMisura(new MisuraForm());
						soggettoForm.getMisura().setDescrizioneMisura(tipoProc.getDescTipoProc());
						//TAnNormative tNormative = commonService.getTAnNormativeById(new Long(elem.getID_NORMATIVA_RIF()).longValue());
						TTpTipoPrevProc tipoPrevProc = commonService.getTTpTipoPrevProc(new Long(elem.getID_NORMATIVA_RIF()).longValue());
						soggettoForm.setNormativa(new ComboForm());
						soggettoForm.getNormativa().setDescrizione(tipoPrevProc.getDescTipoNorm());
						soggettoForm.setTipoSoggetto(Constants.SOGGETTO_FISICO);
						soggettoForm.setDataNascita(converterStringToDate(elem.getDATA_NASCITA()));
						mapperBean.map(elem, soggettoForm);
						listSoggettoFis.add(soggettoForm);
						if(elem.getID_RUOLO().equals("1"))
							mapPf.put(elem.getNUME_PROG(), soggettoForm);
					}
					else if(obj instanceof it.giustizia.smp.webservice.server.polizia.DatiSoggettoGiuridicoType){
						it.giustizia.smp.webservice.server.polizia.DatiSoggettoGiuridicoType elem = (it.giustizia.smp.webservice.server.polizia.DatiSoggettoGiuridicoType)obj;
						SoggettoForm soggettoForm = new SoggettoForm();
						String descrRuolo = (!elem.getID_RUOLO().equalsIgnoreCase("1") ? (elem.getID_RUOLO().equals("2") ? "TERZO" : "INTERVENIENTE") : "DIRETTO"); 
						soggettoForm.setDescrRuolo(descrRuolo);
						String descrPrev = (!elem.getID_TIPO_PREV().equalsIgnoreCase("1") ? (elem.getID_RUOLO().equals("2") ? "Antimafia" : "Antiterrorismo") : "Ordinaria"); 
						soggettoForm.setTipologiaPrevenzione(new ComboForm());
						soggettoForm.getTipologiaPrevenzione().setDescrizione(descrPrev);
						//TTpTipoMisura tipoMis = commonService.getTTpTipoMisuraById(new Long(elem.getID_IPOTESI_MIS()).longValue());
						TTpTipoProc tipoProc = commonService.getTTpTipoProcById(new Long(elem.getID_IPOTESI_MIS()).longValue());
						soggettoForm.setMisura(new MisuraForm());
						soggettoForm.getMisura().setDescrizioneMisura(tipoProc.getDescTipoProc());
						//TAnNormative tNormative = commonService.getTAnNormativeById(new Long(elem.getID_NORMATIVA_RIF()).longValue());
						TTpTipoPrevProc tipoPrevProc = commonService.getTTpTipoPrevProc(new Long(elem.getID_NORMATIVA_RIF()).longValue());
						soggettoForm.setNormativa(new ComboForm());
						soggettoForm.getNormativa().setDescrizione(tipoPrevProc.getDescTipoNorm());
						soggettoForm.setTipoSoggetto(Constants.SOGGETTO_GIURIDICO);
						mapperBean.map(elem, soggettoForm);
						listSoggettoGiu.add(soggettoForm);
						if(elem.getID_RUOLO().equalsIgnoreCase("1"))
							mapPg.put(elem.getNUME_PROG(), soggettoForm);
					}
					else if(obj instanceof DatiMisuraPersonalePFType) {
						DatiMisuraPersonalePFType elem = (DatiMisuraPersonalePFType) obj;
						MisuraPersonaleFlussiForm misuraPersonale = new MisuraPersonaleFlussiForm();
						String descrPrev = (!elem.getID_TIPO_PREV().equalsIgnoreCase("1") ? (elem.getID_TIPO_PREV().equals("2") ? "Antimafia" : "Antiterrorismo") : "Ordinaria"); 
						misuraPersonale.setIdTipoPrevenzione(elem.getID_TIPO_PREV());
						misuraPersonale.setTipoPrevenzione(descrPrev);
						
						long itTipoMisPers = elem.getID_TIPO_MIS_PERS().length()!=0 ? new Long(elem.getID_TIPO_MIS_PERS()).longValue() : 0;
						if(itTipoMisPers!=0){
							TTpTipoMisura tipoMis = commonService.getTTpTipoMisuraById(new Long(elem.getID_TIPO_MIS_PERS()).longValue());
							misuraPersonale.setIdTipoMisura(elem.getID_TIPO_MIS_PERS());
							misuraPersonale.setTipoMisura(tipoMis.getDescTipoMisura());
						}
						
						//TAnNormative tNormative = commonService.getTAnNormativeById(new Long(elem.getID_NORMATIVA_RIF()).longValue());
						long idNormativaRif = elem.getID_NORMATIVA_RIF().length()!=0 ? new Long(elem.getID_NORMATIVA_RIF()).longValue() : 0;
						if(idNormativaRif!=0){
							TTpTipoPrevProc tipoPrevProc = commonService.getTTpTipoPrevProc(new Long(elem.getID_NORMATIVA_RIF()).longValue());
							misuraPersonale.setIdNormativaPrevenzione(elem.getID_NORM_MIS_PERS());
							misuraPersonale.setNormativaPrevenzione(tipoPrevProc.getDescTipoNorm());
						}
						//tipoPrevProc = commonService.getTTpTipoPrevProc(new Long(elem.getID_NORM_MIS_PERS()).longValue());
						long idNormMisPers = elem.getID_NORM_MIS_PERS().length()!=0 ? new Long(elem.getID_NORM_MIS_PERS()).longValue() : 0;
						if(idNormMisPers!=0){
							TAnNormative tnormativa = commonService.getTAnNormativeById(new Long(elem.getID_NORM_MIS_PERS()).longValue());
							misuraPersonale.setIdNormativaMisura(elem.getID_NORM_MIS_PERS());
							misuraPersonale.setNormativaMisura(tnormativa.getDescNormativa());
						}
						misuraPersonale.setNumeProgMisPers(elem.getNUME_PROG_MIS_PERS());
						misuraPersonale.setNumeProgProp(elem.getNUME_PROG_PROP());
						
						misuraPersonale.setDescrMisPers(elem.getDESCR_MIS_PERS());
						
						listMisuraPersonale.add(misuraPersonale);
					}
					else if(obj instanceof DatiMisuraPatrimonialeType){
						DatiMisuraPatrimonialeType elem = (DatiMisuraPatrimonialeType) obj;
						MisuraPatrimonialeFlussiForm misuraPatrimoniale = new MisuraPatrimonialeFlussiForm();
						/*DatiSpecificiBeneType datiSpec = elem.getSPECIFICA_BENE();
						if(datiSpec!=null){
							String descrPrev = (!datiSpec.get.equalsIgnoreCase("1") ? (datiSpec.getID_TIPO_PREV().equals("2") ? "Antimafia" : "Antiterrorismo") : "Ordinaria"); 
							misuraPatrimoniale.setIdTipoPrevenzione(datiSpec.getID_TIPO_PREV());
							misuraPatrimoniale.setTipoPrevenzione(descrPrev);
							//TTpTipoMisura tipoMis = commonService.getTTpTipoMisuraById(new Long(datiSpec.getID_TIPO_MIS_PATR()).longValue());
							TTpTipoProc tipoProc = commonService.getTTpTipoProcById(new Long(datiSpec.getID_TIPO_MIS_PATR()).longValue());misuraPatrimoniale.setIdTipoMisura(datiSpec.getID_TIPO_MIS_PATR());
							misuraPatrimoniale.setTipoMisura(tipoProc.getDescTipoProc());
							//TAnNormative tNormative = commonService.getTAnNormativeById(new Long(datiSpec.getID_NORMATIVA_RIF()).longValue());
							TTpTipoPrevProc tipoPrevProc = commonService.getTTpTipoPrevProc(new Long(datiSpec.getID_NORMATIVA_RIF()).longValue());
							misuraPatrimoniale.setIdNormativaPrevenzione(datiSpec.getID_NORM_MIS_PATR());
							misuraPatrimoniale.setNormativaPrevenzione(tipoPrevProc.getDescTipoNorm());
							tipoPrevProc = commonService.getTTpTipoPrevProc(new Long(datiSpec.getID_NORM_MIS_PATR()).longValue());
							misuraPatrimoniale.setIdNormativaMisura(datiSpec.getID_NORM_MIS_PATR());
							misuraPatrimoniale.setNormativaMisura(tipoPrevProc.getDescTipoNorm());
						}*/
						ComboForm combo = new ComboForm();
						if(elem.getID_TIPO_BENE().length()!=0 && !elem.getID_TIPO_BENE().equals("?")){
							combo = commonService.getTipologiaBene(elem.getID_TIPO_BENE());
							if(combo!=null){
							misuraPatrimoniale.setIdTipoBene(combo.getId());
							misuraPatrimoniale.setDescrBene(combo.getDescrizione());
							}
						}
						if(elem.getID_CAT_BENE().length()!=0 && !elem.getID_CAT_BENE().equals("?")){
							combo = commonService.getCategoriaBene(elem.getID_CAT_BENE());
							if(combo!=null){
							misuraPatrimoniale.setIdCategoriaBene(combo.getId());
							misuraPatrimoniale.setDescrCategoriaBene(combo.getDescrizione());
							}
						}
						if(elem.getID_SUBCAT_BENE().length()!=0 && !elem.getID_SUBCAT_BENE().equals("?")){
							combo = commonService.getSottoCategoriaBene(elem.getID_SUBCAT_BENE());
							if(combo!=null){
							misuraPatrimoniale.setIdSubCatBene(combo.getId());
							misuraPatrimoniale.setDescrSubCatBene(combo.getDescrizione());
							}
						}
						listMisurePatrimoniali.add(misuraPatrimoniale);
						HashMap<String, SoggettoForm> mappaSoggetti = new HashMap<String, SoggettoForm>();
						mappaSoggetti.putAll(mapPf);
						mappaSoggetti.putAll(mapPg);
						List<TitolareType> listTitolare = elem.getTITOLARE();
						for (TitolareType titolareType : listTitolare) {
							List<String> listProp = titolareType.getNUME_PROG_PROP();
							for (String proposto : listProp) {
								if(listBeneSoggPatr.size()==0){
									BeneSoggettoPoliziaForm newbeneSogg = new BeneSoggettoPoliziaForm();
									newbeneSogg.setSoggetto(mapPf.get(proposto));
									newbeneSogg.setNumeProgPersona(proposto);
									newbeneSogg.getMisurePatrimoniali().add(misuraPatrimoniale);
									listBeneSoggPatr.add(newbeneSogg);
								}else{
									boolean collSoggBene=false;
									for (BeneSoggettoPoliziaForm beneSogg : listBeneSoggPatr) {
										if(mappaSoggetti.get(proposto)!=null){
											if(beneSogg.getNumeProgPersona().equals(proposto)){
												if(!beneSogg.getMisurePatrimoniali().contains(misuraPatrimoniale))
													beneSogg.getMisurePatrimoniali().add(misuraPatrimoniale);
												collSoggBene = true;
											}
										}
									}
									if(!collSoggBene && mappaSoggetti.get(proposto)!=null){
										BeneSoggettoPoliziaForm newBeneSogg = new BeneSoggettoPoliziaForm();
										newBeneSogg.setSoggetto(mappaSoggetti.get(proposto));
										newBeneSogg.setNumeProgPersona(proposto);
										newBeneSogg.getMisurePatrimoniali().add(misuraPatrimoniale);
										listBeneSoggPatr.add(newBeneSogg);
									}
								}
							}
						}
					}
				}
				flussiForm.setListSoggettoFis(!listSoggettoFis.isEmpty() ? listSoggettoFis : null);
				flussiForm.setListSoggettoGiu(!listSoggettoGiu.isEmpty() ? listSoggettoGiu : null);
				flussiForm.setListMisurePersonali(!listMisuraPersonale.isEmpty() ? listMisuraPersonale : null);
				flussiForm.setListMisurePatrimoniali(!listMisurePatrimoniali.isEmpty() ? listMisurePatrimoniali : null);
				//riempio la lista delle misure personali associate ai sogetti
				List<BeneSoggettoPoliziaForm> listBeneSogg = new ArrayList<BeneSoggettoPoliziaForm>();
				if(!listMisuraPersonale.isEmpty() && !listSoggettoFis.isEmpty()){
					ArrayList<MisuraForm> listMisure = new ArrayList<MisuraForm>();
					//itero i soggetti
					for (String key : mapPf.keySet()) {
						BeneSoggettoPoliziaForm beneSogg = new BeneSoggettoPoliziaForm();
						beneSogg.setSoggetto(mapPf.get(key));
						beneSogg.setNumeProgPersona(key);
						ArrayList<MisuraPersonaleFlussiForm> listMisPers = new ArrayList<MisuraPersonaleFlussiForm>();
						for(Iterator<MisuraPersonaleFlussiForm> itr = listMisuraPersonale.iterator(); itr.hasNext();){
							MisuraPersonaleFlussiForm elem = itr.next();
							if(elem.getNumeProgProp().equals(key)){
								listMisPers.add(elem);
							}
						}	
						beneSogg.setMisurePersonali(listMisPers);
						listBeneSogg.add(beneSogg);
					}
				}
				flussiForm.setMisureSoggetto(!listBeneSogg.isEmpty() ? listBeneSogg : null);
				flussiForm.setBeneSoggetto(!listBeneSoggPatr.isEmpty() ? listBeneSoggPatr: null);
				break;
			case Constants.POLIZIA_STATO_SOTTOP_MIS_PERS://21
			case Constants.POLIZIA_APPL_MIS_SIC_LIB_VIG://20
			case Constants.POLIZIA_VIOLAZIONE_SOTT_MIS_PERS://22
				//StatoSottoposizioneMisurePersonaliType		- 20
				//ApplicazioneMisureSicurezzaLibertaVigType 	- 21 
				//ViolazioniSottoposizioneMisurePersonaliType 	- 22
				for(Iterator<Object> itr = objList.iterator(); itr.hasNext();){
					Object obj = (Object)itr.next();
					if (obj instanceof DatiAutoritaComunicazioneType) {
						DatiAutoritaComunicazioneType elem = (DatiAutoritaComunicazioneType) obj;
						TAnUfficio tUfficio = commonService.getTAnUfficioByIdUff(new Long(elem.getID_AUTORITA()).longValue());
						UfficiForm ufficioForm = new UfficiForm();
						ufficioForm.setItemTipoUfficio(new ComboForm());
						ufficioForm.getItemTipoUfficio().setId(""+tUfficio.getTTpTipoUfficio().getCodiKIdTipoUff());
						ufficioForm.getItemTipoUfficio().setDescrizione(tUfficio.getTTpTipoUfficio().getDescTipoUff());
						ufficioForm.setDescUfficio(tUfficio.getDescUfficio());
						ufficioForm.setDescIndirizzoSede(tUfficio.getDescIndirizzoSede());
						ufficioForm.setCodiKIdUfficio(new Long(elem.getID_AUTORITA()).intValue());
						flussiForm.setUfficioMittente(ufficioForm);
						flussiForm.setIdUffMittente(new Long(elem.getID_AUTORITA()));
					}else if(obj instanceof DatiUffGiuSottoposizioneType){
						try{
							DatiUffGiuSottoposizioneType elem = (DatiUffGiuSottoposizioneType) obj;
							long idProcedimento = elem.getID_PROCEDIMENTO().length()!=0 ? new Long(elem.getID_PROCEDIMENTO()).longValue() : 0;
							if(idProcedimento!=0){
								ProcedimentoForm pricedimentoForm = procedimentoService.getDettaglioProcedimento(idProcedimento);
								flussiForm.setProcedimento(pricedimentoForm);
								flussiForm.setIdUffDestinatario(new Long(elem.getID_UFFICIO()));
								flussiForm.setIdProcedimento(idProcedimento);
							}else{
								flussiForm.setIdUffDestinatario(new Long(elem.getID_UFFICIO()));
								flussiForm.setNumProc(elem.getDESCR_NUM_PROC());
							}
						} catch (Exception e) {
							System.out.println("Eccezione nel recupereare i dettagli del procedimento: idServizio "+flussiForm.getIdServizio());
						}
					}else if(obj instanceof DatiSoggettiSottopostoType){
						DatiSoggettiSottopostoType elem = (DatiSoggettiSottopostoType) obj;
						popolaFlussiFormFromDatiSoggettoSottoposto(flussiForm, elem, listSoggetto);
					}else if(obj instanceof DatiComunicazioneType){
						DatiComunicazioneType elem = (DatiComunicazioneType) obj;
						long idCatAtto = elem.getID_TIPO_COMUNIC().length()!=0 && !elem.getID_TIPO_COMUNIC().equals("?") ? new Long(elem.getID_TIPO_COMUNIC()).longValue() : 0;
						if(idCatAtto!=0){
							TTpCatAtto tTpCatAtto = commonService.getCategoriaAtto(idCatAtto);
							flussiForm.setDescrTipoComunic(tTpCatAtto.getDescCatAtto());
						}
						mapperBean.map(elem, flussiForm);
					}						
				}
				flussiForm.setListSoggetto(!listSoggetto.isEmpty() ? listSoggetto : null);
				break;
			case Constants.POLIZIA_AGGRAVAMENTO_MIS_PER://23
				//AggravamentoMisurePersonaliType
				for(Iterator<Object> itr = objList.iterator(); itr.hasNext();){
					Object obj = (Object)itr.next();
					if (obj instanceof DatiAutoritaComunicazioneType) {
						DatiAutoritaComunicazioneType elem = (DatiAutoritaComunicazioneType) obj;
						TAnUfficio tUfficio = commonService.getTAnUfficioByIdUff(new Long(elem.getID_AUTORITA()).longValue());
						UfficiForm ufficioForm = new UfficiForm();
						ufficioForm.setItemTipoUfficio(new ComboForm());
						ufficioForm.getItemTipoUfficio().setId(""+tUfficio.getTTpTipoUfficio().getCodiKIdTipoUff());
						ufficioForm.getItemTipoUfficio().setDescrizione(tUfficio.getTTpTipoUfficio().getDescTipoUff());
						ufficioForm.setDescUfficio(tUfficio.getDescUfficio());
						ufficioForm.setDescIndirizzoSede(tUfficio.getDescIndirizzoSede());
						ufficioForm.setCodiKIdUfficio(new Long(elem.getID_AUTORITA()).intValue());
						flussiForm.setUfficioMittente(ufficioForm);
					}
					if(obj instanceof DatiUffGiuSottoposizioneType){
						try {
							DatiUffGiuSottoposizioneType elem = (DatiUffGiuSottoposizioneType)obj;
							long idProcedimento = elem.getID_PROCEDIMENTO().length()!=0? new Long(elem.getID_PROCEDIMENTO()).longValue() : 0;
							if(idProcedimento!=0){
								ProcedimentoForm pricedimentoForm = procedimentoService.getDettaglioProcedimento(idProcedimento);
								flussiForm.setProcedimento(pricedimentoForm);
								flussiForm.setIdUffDestinatario(new Long(elem.getID_UFFICIO()));
								flussiForm.setIdProcedimento(idProcedimento);
							}else {
								flussiForm.setIdUffDestinatario(new Long(elem.getID_UFFICIO()));
								flussiForm.setNumProc(elem.getDESCR_NUM_PROC());
							}
						} catch (Exception e) {
							System.out.println("Eccezione nel recupereare i dettagli del procedimento: idServizio "+flussiForm.getIdServizio());
						}
					}
					if(obj instanceof DatiSoggettiSottopostoType){
						DatiSoggettiSottopostoType elem = (DatiSoggettiSottopostoType) obj;
						popolaFlussiFormFromDatiSoggettoSottoposto(flussiForm, elem, listSoggetto);
					}
					if(obj instanceof DatiRichiestaAggravamentoType){
						DatiRichiestaAggravamentoType elem = (DatiRichiestaAggravamentoType)obj;
						mapperBean.map(elem, flussiForm);
					}
				}
				flussiForm.setListSoggetto(!listSoggetto.isEmpty() ? listSoggetto : null);
				break;
			case Constants.POLIZIA_REVOCA_MODIFICA_MIS_PERS://24
				//PareriRevocaModificaMisurePersonaliType
				for(Iterator<Object> itr = objList.iterator(); itr.hasNext();){
					Object obj = (Object)itr.next();
					if (obj instanceof DatiAutoritaComunicazioneType) {
						DatiAutoritaComunicazioneType elem = (DatiAutoritaComunicazioneType) obj;
						TAnUfficio tUfficio = commonService.getTAnUfficioByIdUff(new Long(elem.getID_AUTORITA()).longValue());
						UfficiForm ufficioForm = new UfficiForm();
						ufficioForm.setItemTipoUfficio(new ComboForm());
						ufficioForm.getItemTipoUfficio().setId(""+tUfficio.getTTpTipoUfficio().getCodiKIdTipoUff());
						ufficioForm.getItemTipoUfficio().setDescrizione(tUfficio.getTTpTipoUfficio().getDescTipoUff());
						ufficioForm.setDescUfficio(tUfficio.getDescUfficio());
						ufficioForm.setDescIndirizzoSede(tUfficio.getDescIndirizzoSede());
						ufficioForm.setCodiKIdUfficio(new Long(elem.getID_AUTORITA()).intValue());
						flussiForm.setUfficioMittente(ufficioForm);
					} else if (obj instanceof DatiUffGiuRichiedenteType) {
						try {
							DatiUffGiuRichiedenteType elem = (DatiUffGiuRichiedenteType) obj;
							long idProcedimento = new Long(elem.getID_PROCEDIMENTO()).longValue();
							long idAtto = new Long(elem.getID_ATTO()).longValue();
							long idUff = new Long(elem.getID_UFFICIO()).longValue();
							
							ProcedimentoForm pricedimentoForm = procedimentoService.getDettaglioProcedimento(idProcedimento);
							flussiForm.setProcedimento(pricedimentoForm);
							
							if(idUff!=0){
								flussiForm.setIdUffDestinatario(idUff);
								AttoForm attoForm = attoService.getAttoById(idAtto, idUff);
								TAtto tatto = attoService.getTAttoById(idAtto, idUff);
								mapperBean.map(tatto, attoForm);
								flussiForm.setAtto(attoForm);
								flussiForm.setIdAtto(idAtto);
							}
							flussiForm.setIdProcedimento(idProcedimento);
						} catch (Exception e) {
							System.out.println("Eccezione nel recupereare i dettagli del procedimento e atto: idServizio "+flussiForm.getIdServizio());
						}
					} else if (obj instanceof IdSoggettoSottopostoType) {
						try {
							IdSoggettoSottopostoType elem = (IdSoggettoSottopostoType) obj;
							long idSoggetto = new Long(elem.getID_SOGGETTO()).longValue();
							int idUff = 0;
							long idProc = 0;
							if(flussiForm.getIdUffDestinatario().longValue()!=0)
								idUff = flussiForm.getIdUffDestinatario().intValue();
							if(flussiForm.getIdProcedimento().longValue()!=0)
								idProc = flussiForm.getProcedimento().getIdProcedimento().longValue();
							if(idUff!=0 && idProc!=0){
								SoggettoForm soggettoForm = procedimentoService.getPersonaById(idSoggetto, idUff, idProc);
								listSoggetto.add(soggettoForm);
							}
						} catch (Exception e) {
							System.out.println("Eccezione nel recupero dei dati del soggetto sottoposto: idServizio "+flussiForm.getIdServizio());
						}
					} else if (obj instanceof DatiParereType) {
						DatiParereType elem = (DatiParereType)obj;
						mapperBean.map(elem, flussiForm);
						flussiForm.setDescrFlgParere((flussiForm.getFlgParere()!=null && flussiForm.getFlgParere().equalsIgnoreCase("F")) ? "FAVOREVOLE" : "CONTRARIO");
					}
				}
				flussiForm.setListSoggetto(!listSoggetto.isEmpty() ? listSoggetto : null);
				break;
			case Constants.POLIZIA_TERMINE_CESSAZIONE://25
				//TerminiCessazioniMisurePersonaliType
				for(Iterator<Object> itr = objList.iterator(); itr.hasNext();){
					Object obj = (Object)itr.next();
					if(obj instanceof DatiAutoritaComunicazioneType){
						DatiAutoritaComunicazioneType elem = (DatiAutoritaComunicazioneType) obj;
						TAnUfficio tUfficio = commonService.getTAnUfficioByIdUff(new Long(elem.getID_AUTORITA()).longValue());
						UfficiForm ufficioForm = new UfficiForm();
						ufficioForm.setItemTipoUfficio(new ComboForm());
						ufficioForm.getItemTipoUfficio().setId(""+tUfficio.getTTpTipoUfficio().getCodiKIdTipoUff());
						ufficioForm.getItemTipoUfficio().setDescrizione(tUfficio.getTTpTipoUfficio().getDescTipoUff());
						ufficioForm.setDescUfficio(tUfficio.getDescUfficio());
						ufficioForm.setDescIndirizzoSede(tUfficio.getDescIndirizzoSede());
						ufficioForm.setCodiKIdUfficio(new Long(elem.getID_AUTORITA()).intValue());
						flussiForm.setUfficioMittente(ufficioForm);
					} else if(obj instanceof DatiUffGiuSottoposizioneType){
						try {
							DatiUffGiuSottoposizioneType elem = (DatiUffGiuSottoposizioneType) obj;
							long idProcedimento = elem.getID_PROCEDIMENTO().length()!=0? new Long(elem.getID_PROCEDIMENTO()).longValue() : 0;
							if(idProcedimento!=0){
								ProcedimentoForm pricedimentoForm = procedimentoService.getDettaglioProcedimento(idProcedimento);
								flussiForm.setProcedimento(pricedimentoForm);
								flussiForm.setIdUffDestinatario(new Long(elem.getID_UFFICIO()));
								flussiForm.setIdProcedimento(idProcedimento);
							} else{
								flussiForm.setIdUffDestinatario(new Long(elem.getID_UFFICIO()));
								flussiForm.setNumProc(elem.getDESCR_NUM_PROC());
							}
						} catch (Exception e) {
							System.out.println("Eccesione nel recupero del dettaglio del procedimento: idServizio "+flussiForm.getIdServizio());
						}
					} else if(obj instanceof DatiSoggettiSottopostoType){
						DatiSoggettiSottopostoType elem = (DatiSoggettiSottopostoType) obj;
						popolaFlussiFormFromDatiSoggettoSottoposto(flussiForm, elem, listSoggetto);
					} else if(obj instanceof DatiComunicazioneType){
						DatiComunicazioneType elem = (DatiComunicazioneType) obj;
						long idCatAtto = elem.getID_TIPO_COMUNIC().length()!=0 && !elem.getID_TIPO_COMUNIC().equals("?") ? new Long(elem.getID_TIPO_COMUNIC()).longValue() : 0;
						if(idCatAtto!=0){
							TTpCatAtto tTpCatAtto = commonService.getCategoriaAtto(idCatAtto);
							flussiForm.setDescrTipoComunic(tTpCatAtto.getDescCatAtto());
						}
						mapperBean.map(elem, flussiForm);
					}
				}
				flussiForm.setListSoggetto(!listSoggetto.isEmpty() ? listSoggetto : null);
				break;
			default:
				break;
			}
		}
		return flussiForm;
	}
	
	private ArrayList<Object> leggiPoliziaXML(FlussiPoliziaForm flussiForm) {
		ArrayList<Object> listaObj = new ArrayList<Object>();
		Blob blob = flussiDao.getXmlFlussoPolizia(flussiForm.getIdFlusso());
		try {
			if(blob==null)
				return null;

			InputStream is = removeAllXmlNamespace(blob);
			org.w3c.dom.Document documentw3c = null;
			try {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				documentw3c =  builder.parse(is);
				documentw3c.getDocumentElement().normalize();
			} catch (Exception e) {
				logger.error("Errore " + e);
				return null;
			}
			DOMBuilder builder = new DOMBuilder();
			Document document = builder.build(documentw3c);
			// Prendo la radice
			Element root = document.getRootElement();
			// tipoMessaggio = root.getName();
			// Estraggo i figli dalla radice
			List children = root.getChildren();
			Iterator iterator = children.iterator();
			String nomeTabella = "";
			// esamino tutti i figli della radice
			while (iterator.hasNext()) {
				try {
					org.jdom.Element item = (org.jdom.Element) iterator.next();
					Class[] argsClass = new Class[] {};
					Class classDefinition = null;
					nomeTabella = item.getName();
					
					if(nomeTabella.equalsIgnoreCase("personaGiuridica") || nomeTabella.equalsIgnoreCase("DatiSoggettoDirettoTerzoGiuridico"))
						nomeTabella = "DatiSoggettoGiuridico";
					if(nomeTabella.equalsIgnoreCase("personaFisica") || nomeTabella.equalsIgnoreCase("DatiSoggettoDirettoTerzoFisico"))
						nomeTabella = "DatiSoggettoFisico";
					if(nomeTabella.equalsIgnoreCase("DatiSoggettiSottoposti"))
						nomeTabella = "DatiSoggettiSottoposto";
					if(nomeTabella.equalsIgnoreCase("DatiSoggettoSottoposto"))
						nomeTabella = "IdSoggettoSottoposto";
					if(nomeTabella.equalsIgnoreCase("DatiEsitoIndagini"))
						nomeTabella = "DatiEsitoIndagati";
					
					classDefinition = Class.forName("it.giustizia.smp.webservice.server.polizia." + nomeTabella + "Type");
					Object[] args = new Object[] {};
					Constructor constructor = classDefinition.getConstructor(argsClass);
					Object obj = createObject(constructor, args);
					List childList = item.getChildren();
					Iterator itElem = childList.iterator();
					
					if(nomeTabella.equalsIgnoreCase("DatiAutoritaMittente")){
						setProperty("ID_AUTORITA", obj, item.getValue());
					}
					// estraggo i valori da inserire in ogni dto
					while (itElem.hasNext()) {
						org.jdom.Element elem = (org.jdom.Element) itElem.next();
						/*if(elem.getName().equals("SPECIFICA_BENE")){
							Class[] argsClass2 = new Class[] {};
							Class classDefinition2 = Class.forName("it.giustizia.smp.webservice.server.polizia.DatiSpecificiBeneType");
							Object[] args2 = new Object[] {};
							Constructor constructor2 = classDefinition2.getConstructor(argsClass2);
							Object obj2 = createObject(constructor2, args2);
							List childList2 = elem.getChildren();
							Iterator itElem2 = childList2.iterator();
							while (itElem2.hasNext()) {
								org.jdom.Element elem2 = (org.jdom.Element) itElem2.next();
								setProperty(elem2.getName(), obj2, elem2.getValue());
							}
							setPropertyObj(elem.getName(), obj, obj2);
						}else */
						if(nomeTabella.equalsIgnoreCase("DatiSoggettiIndagine")) {
							if(elem.getName().equalsIgnoreCase("personaGiuridica")){
								Class[] argsClass2 = new Class[] {};
								Class classDefinition2 = Class.forName("it.giustizia.smp.webservice.server.polizia.DatiSoggettoGiuridicoType");
								Object[] args2 = new Object[] {};
								Constructor constructor2 = classDefinition2.getConstructor(argsClass2);
								Object obj2 = createObject(constructor2, args2);
								List childList2 = elem.getChildren();
								Iterator itElem2 = childList2.iterator();
								while (itElem2.hasNext()) {
									org.jdom.Element elem2 = (org.jdom.Element) itElem2.next();
									setProperty(elem2.getName(), obj2, elem2.getValue());
								}
								listaObj.add(obj2);
							}else if(elem.getName().equalsIgnoreCase("personaFisica")){
								Class[] argsClass2 = new Class[] {};
								Class classDefinition2 = Class.forName("it.giustizia.smp.webservice.server.polizia.DatiSoggettoFisicoType");
								Object[] args2 = new Object[] {};
								Constructor constructor2 = classDefinition2.getConstructor(argsClass2);
								Object obj2 = createObject(constructor2, args2);
								List childList2 = elem.getChildren();
								Iterator itElem2 = childList2.iterator();
								while (itElem2.hasNext()) {
									org.jdom.Element elem2 = (org.jdom.Element) itElem2.next();
									setProperty(elem2.getName(), obj2, elem2.getValue());
								}
								listaObj.add(obj2);
							}
						}else if(elem.getName().equals("TITOLARE")){
							List<TitolareType> listTitolare = new ArrayList<TitolareType>();
							//controllo quanti element di TITOLARE ci sono
							int countTit = 0;
							for(Iterator<org.jdom.Element> itr = childList.iterator(); itr.hasNext();){
								org.jdom.Element elemCountTit = (org.jdom.Element) itr.next();
								if(elemCountTit.getName().equals("TITOLARE"))
									countTit++;
							}
							Class[] argsClass2 = new Class[] {};
							Class classDefinition2 = Class.forName("it.giustizia.smp.webservice.server.polizia.TitolareType");
							Object[] args2 = new Object[] {};
							Object[] argsProp = new Object[] {};
							Constructor constructor2 = classDefinition2.getConstructor(argsClass2);
							for(int i=1; i<=countTit; i++){
								
								List childList2 = elem.getChildren();
								Iterator itElem2 = childList2.iterator();
								Object obj2 = createObject(constructor2, args2);
								while (itElem2.hasNext()) {
									List<String> listProp = new ArrayList<String>();
									int countProp = 0;
									org.jdom.Element elem2 = (org.jdom.Element) itElem2.next();
									if(elem2.getName().equals("NUME_PROG_PROP")){
										for(Iterator<org.jdom.Element> itr2 = childList2.iterator(); itr2.hasNext();){
											org.jdom.Element elemCountProp = (org.jdom.Element) itr2.next();
											if(elemCountProp.getName().equals("NUME_PROG_PROP"))
												countProp++;
										}
										listProp.add(elem2.getValue());
										for(int j=1; j<=countProp; j++){
											while (itElem2.hasNext()) {
												elem2 = (org.jdom.Element) itElem2.next();
												listProp.add(elem2.getValue());
											}
										}
										((TitolareType)obj2).setNUME_PROG_PROP(listProp);
										if(itElem.hasNext())
											elem = (org.jdom.Element) itElem.next();
									}else{
										setProperty(elem2.getName(), obj2, elem2.getValue());
									}
								}
								listTitolare.add((TitolareType)obj2);
								
							}
							((DatiMisuraPatrimonialeType)obj).setTITOLARE(listTitolare);
						}else {
							setProperty(elem.getName(), obj, elem.getValue());
						}
					}
					listaObj.add(obj);
				} catch (ClassNotFoundException e) {
					logger.error("FlussiServiceImpl.leggiXML.ClassNotFoundException Errore durante la lettura del documento xml");
					logger.error("Leggi Polizia XML !! Tabella Non utilizzata in visualizzazione: "
							+ nomeTabella );
				} catch (NoSuchMethodException e) {
					logger.error("FlussiServiceImpl.leggiXML.NoSuchMethodException Errore durante la lettura del documento xml");
				}
			}
			return listaObj;
		} catch (Exception e) {
			logger.error("Errore " + e);
			return null;
		}
	}
	
	private void popolaFlussiFormFromDatiSoggettoSottoposto(FlussiPoliziaForm flussiForm, DatiSoggettiSottopostoType elem, List<SoggettoForm> listSoggetto){
		try {
			long idSoggetto = elem.getID_SOGGETTO().length()!=0 ? new Long(elem.getID_SOGGETTO()).longValue() : 0;
			SoggettoForm soggettoForm = null;
			if(idSoggetto==0){
				soggettoForm = new SoggettoForm();
				mapperBean.map(elem, soggettoForm);
				String idComune = elem.getID_ISTAT_COMUNE_NAS();
				if(idComune.length()!=0){
					TAnComuni comune = commonService.getComuneByDescIstatComune(idComune);
					soggettoForm.setComuneNascita(new ComboForm());
					soggettoForm.getComuneNascita().setDescrizione(comune.getDescComune());
					soggettoForm.getComuneNascita().setId(idComune);
				}
				soggettoForm.setDataNascita(converterStringToDate(elem.getDATA_NASCITA()));
			} else {
				if(flussiForm.getIdUffDestinatario()!=0 && flussiForm.getIdProcedimento()!=0){
					soggettoForm = new SoggettoForm();
					soggettoForm = procedimentoService.getPersonaById(idSoggetto, flussiForm.getIdUffDestinatario().intValue(), flussiForm.getIdProcedimento());
					//soggettoForm.setDataNascita(converterStringToDate(elem.getDATA_NASCITA()));
					List<TMisuraPers> misura = misuraService.listaMisurePersByIdSoggetto(idSoggetto, flussiForm.getIdUffDestinatario().intValue());
					for (TMisuraPers tMisuraPers : misura) {
						if(tMisuraPers.getTTpCaratMisura().equals("P")){
							soggettoForm.setMisura(new MisuraForm());
							soggettoForm.getMisura().setDescrizioneMisura(tMisuraPers.getTTpCaratMisura().getDescCaratMisura());
							soggettoForm.getMisura().setIdMisura(tMisuraPers.getId().getCodiKIdMisura());
							break;
						}
					}
				}else if(flussiForm.getIdUffDestinatario()!=0 && flussiForm.getNumProc().length()!=0){
					soggettoForm = new SoggettoForm();
					//soggettoForm = procedimentoService.getPersonaById(idSoggetto, flussiForm.getIdUffDestinatario().intValue());
					mapperBean.map(elem, soggettoForm);
					soggettoForm.setDataNascita(converterStringToDate(elem.getDATA_NASCITA()));
					soggettoForm = commonService.getSoggettoById(idSoggetto, flussiForm.getIdUffDestinatario().longValue());
				}
			}
			listSoggetto.add(soggettoForm!=null ? soggettoForm : null);
		} catch (Exception e) {
			System.out.println("Errore nella creazione del dettaglio del soggetto per il servizio "+flussiForm.getIdServizio());
			SoggettoForm soggettoForm = new SoggettoForm();
			mapperBean.map(elem, soggettoForm);
			listSoggetto.add(soggettoForm);
		}
	}

	public boolean registraFlussoIngressoPolizia(FlussiPoliziaForm flussoPolizia){
		boolean esito = false;
		String insert = "";
		switch (flussoPolizia.getIdServizio().intValue()) {
			case Constants.POLIZIA_ISCRIZIONE_INDAGINI://17
				insert = poliziaService.registraFlussiIngressoPolizia(flussoPolizia.getIdFlusso(), flussoPolizia.getIdUffDestinatario(), flussoPolizia.getIdProcedimento(), 
						flussoPolizia.getIdUffMittente(), convertStringToSqlDate(flussoPolizia.getDataRicezione()), flussoPolizia.getDescrNoteAggr(), Constants.PROCEDURE_POLIZIA_ISCRIZIONE_INDAGINI);
				break;
			case Constants.POLIZIA_ESITO_INDAGINE://18
				insert = poliziaService.registraFlussiIngressoPolizia(flussoPolizia.getIdFlusso(), flussoPolizia.getIdUffDestinatario(), flussoPolizia.getIdProcedimento(), 
						flussoPolizia.getIdUffMittente(), convertStringToSqlDate(flussoPolizia.getDataRicezione()), flussoPolizia.getDescrNoteEsito(), Constants.PROCEDURE_POLIZIA_ESITO_INDAGINE);
				break;
			case Constants.POLIZIA_STATO_SOTTOP_MIS_PERS://21
				insert = poliziaService.registraFlussiIngressoPolizia(flussoPolizia.getIdFlusso(), flussoPolizia.getIdUffDestinatario(), flussoPolizia.getIdProcedimento(), 
						flussoPolizia.getIdUffMittente(), convertStringToSqlDate(flussoPolizia.getDataRicezione()), flussoPolizia.getDescrNoteComunic(), Constants.PROCEDURE_POLIZIA_STATO_SOTTOP_MIS_PERS);
				break;
			case Constants.POLIZIA_APPL_MIS_SIC_LIB_VIG://20
				insert = poliziaService.registraFlussiIngressoPolizia(flussoPolizia.getIdFlusso(), flussoPolizia.getIdUffDestinatario(), flussoPolizia.getIdProcedimento(), 
						flussoPolizia.getIdUffMittente(), convertStringToSqlDate(flussoPolizia.getDataRicezione()), flussoPolizia.getDescrNoteComunic(), Constants.PROCEDURE_POLIZIA_APPL_MIS_SIC_LIB_VIG);
				break;
			case Constants.POLIZIA_VIOLAZIONE_SOTT_MIS_PERS://22
				insert = poliziaService.registraFlussiIngressoPolizia(flussoPolizia.getIdFlusso(), flussoPolizia.getIdUffDestinatario(), flussoPolizia.getIdProcedimento(), 
						flussoPolizia.getIdUffMittente(), convertStringToSqlDate(flussoPolizia.getDataRicezione()), flussoPolizia.getDescrNoteComunic(), Constants.PROCEDURE_POLIZIA_VIOLAZIONE_SOTT_MIS_PERS);
				break;
			case Constants.POLIZIA_TERMINE_CESSAZIONE://25
				insert = poliziaService.registraFlussiIngressoPolizia(flussoPolizia.getIdFlusso(), flussoPolizia.getIdUffDestinatario(), flussoPolizia.getIdProcedimento(), 
						flussoPolizia.getIdUffMittente(), convertStringToSqlDate(flussoPolizia.getDataRicezione()), flussoPolizia.getDescrNoteComunic(), Constants.PROCEDURE_POLIZIA_TERMINE_CESSAZIONE);
				break;
			case Constants.POLIZIA_PROPOSTA_MIS_PERS_PATR://19
				insert = poliziaService.registraFlussiIngressoPoliziaAgg(flussoPolizia.getIdFlusso(), flussoPolizia.getIdUffDestinatario(), flussoPolizia.getIdUffMittente(), 
						flussoPolizia.getDescrNumAccert(), convertStringToSqlDate(flussoPolizia.getDataRicezione()), flussoPolizia.getDescrNoteAggr(), Constants.PROCEDURE_POLIZIA_PROPOSTA_MIS_PERS_PATR);
				break;
			case Constants.POLIZIA_AGGRAVAMENTO_MIS_PER://23
				insert = poliziaService.registraFlussiIngressoPoliziaAgg(flussoPolizia.getIdFlusso(), flussoPolizia.getIdUffDestinatario(), flussoPolizia.getIdUffMittente(), 
						flussoPolizia.getDescrNumProposta(), convertStringToSqlDate(flussoPolizia.getDataRicezione()), flussoPolizia.getDescrNoteAggr(), Constants.PROCEDURE_POLIZIA_AGGRAVAMENTO_MIS_PER);
				break;
			case Constants.POLIZIA_REVOCA_MODIFICA_MIS_PERS://24
				insert = poliziaService.registraFlussiIngressoPoliziaAgg(flussoPolizia.getIdFlusso(), flussoPolizia.getIdUffDestinatario(), flussoPolizia.getIdUffMittente(), 
						flussoPolizia.getDescrNumParere(), convertStringToSqlDate(flussoPolizia.getDataRicezione()), flussoPolizia.getDescrNoteParere(), Constants.PROCEDURE_POLIZIA_REVOCA_MODIFICA_MIS_PERS);
				break;
			default:
				break;
		}
		if(insert!=null&& !insert.isEmpty()){
			if(insert.equals("OK"))
				esito = true;
		}
		return esito;
	}
	
	private InputStream removeAllXmlNamespace(Blob blob) {
		try {
			byte[] bdata = null;
			bdata = blob.getBytes(1, (int)blob.length());
			String outputXml = new String(bdata);
			System.out.println(outputXml); 
			String xmlnsPattern = "xmlns:ns2=\"it.giustizia.smp.webservice.server.polizia\" xmlns=\"http://webservice.smp.giustizia.it/server/polizia/type\"";
			outputXml = outputXml.replaceAll(xmlnsPattern, "");
			String namespace = "ns2:";
			outputXml = outputXml.replaceAll(namespace, "");
			outputXml.trim();
			InputStream is = new ByteArrayInputStream(outputXml.getBytes());
			return is;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	private java.sql.Date convertStringToSqlDate(String data){
		try {
			String format = "dd/MM/yyyy";
			SimpleDateFormat sd = new SimpleDateFormat(format);
			java.util.Date dataOut = sd.parse(data);
			return new java.sql.Date(dataOut.getTime());
		} catch (Exception e) {
			return null;
		}
	}
	
	private static java.util.Date converterStringToDate(String dataInput){
		try {
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date date;
			date = formatter.parse(dataInput);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//*** Tool Amministratori ***//
	
	public List<FlussiToolAmministratoriForm> listaFlussiToolAmministratori(int idUffOwn,
			FlussiToolAmministratoriForm flussiForm) {

		List<FlussiToolAmministratoriDto> queryResults = null;
		List<FlussiToolAmministratoriForm> risultati = new ArrayList<FlussiToolAmministratoriForm>();
		queryResults = flussiDao.listaFlussiToolAmministratori(idUffOwn);
		Iterator<FlussiToolAmministratoriDto> iterator = queryResults.iterator();
		while (iterator.hasNext()) {
			FlussiToolAmministratoriDto flusso = iterator.next();
			FlussiToolAmministratoriForm form = new FlussiToolAmministratoriForm();
			if (flusso != null) {
				mapperBean.map(flusso, form);
			}
			risultati.add(form);
		}
		return risultati;
	}

	
	public void getDettaglioDeposito(FlussiToolAmministratoriForm flussoForm, long idDeposito, int idUffOwn){
					
			TDepositi deposito = flussiDao.getDettaglioDeposito(idDeposito, idUffOwn);
			
			if(deposito != null) {								
				flussoForm.setDataRicezione(deposito.getDataRicezione());
				flussoForm.setDescCodiceFiscale(deposito.getDescIdMittente());	
				//TAnUfficio ufficioMitt = deposito.getTAnUfficioByCodiFkIdUffMit();
				//flussoForm.setIdUffMitt(ufficioMitt.getCodiKIdUfficio());
				//flussoForm.setDescUffMitt(ufficioMitt.getDescUfficio());						
			}
			
	}
	
	public void getDettaglioDepositoAmm(FlussiToolAmministratoriForm flussoForm, long idDeposito, char flag){
		
		TDepositiAmm depositoAmm = flussiDao.getDettaglioDepositoAmm(idDeposito, flag);
		
		if(depositoAmm != null) {	
			            
			flussoForm.setNomeAllegato(depositoAmm.getDescNomeDocumento());
			flussoForm.setTipoAllegato(depositoAmm.getDescTipoDocumento());
			
			TProcedimento procFM = depositoAmm.getTProcedimentoByCodiFkIdProc();
			flussoForm.setFormProcedimentoFM(new ProcedimentoForm());
            flussoForm.getFormProcedimentoFM().setIdProcedimento(procFM.getCodiKIdProc());
            flussoForm.setIdProc(procFM.getCodiKIdProc());            
            flussoForm.getFormProcedimentoFM().setAnno(procFM.getNumeAnnoProc());
            flussoForm.getFormProcedimentoFM().setNumero(procFM.getNumeProc());
            flussoForm.setDescProc(procFM.getNumeAnnoProc()+"/"+procFM.getNumeProc());
            flussoForm.getFormProcedimentoFM().setDataIscrizione(procFM.getDataIscrizione());
            ComboForm tipoProcFM = new ComboForm();
            ComboForm statoProcFM = new ComboForm();
            tipoProcFM.setId(Integer.toString(procFM.getTTpTipoProc().getCodiKIdTipoProc()));
            tipoProcFM.setDescrizione(procFM.getTTpTipoProc().getDescTipoProc());       
            flussoForm.getFormProcedimentoFM().setTipoProcedimento(tipoProcFM);            
            statoProcFM.setId(Integer.toString(procFM.getTTpStatoProc().getCodiKIdStatoProc()));
            statoProcFM.setDescrizione(procFM.getTTpStatoProc().getDescStatoProc());
            flussoForm.getFormProcedimentoFM().setStatoProcedimento(statoProcFM);            
           
			TProcedimento procFG = depositoAmm.getTProcedimentoByCodiFkIdProcFg();
			flussoForm.setFormProcedimentoFG(new ProcedimentoForm());
            flussoForm.getFormProcedimentoFG().setIdProcedimento(procFG.getCodiKIdProc());
            flussoForm.setIdProcFG(procFG.getCodiKIdProc());
            flussoForm.getFormProcedimentoFG().setAnno(procFG.getNumeAnnoProc());
            flussoForm.getFormProcedimentoFG().setNumero(procFG.getNumeProc());
            flussoForm.setDescProc(procFG.getNumeAnnoProc()+"/"+procFG.getNumeProc());            
            flussoForm.getFormProcedimentoFG().setDataIscrizione(procFG.getDataIscrizione());
            ComboForm tipoProcFG = new ComboForm();
            ComboForm statoProcFG = new ComboForm();            
            tipoProcFG.setId(Integer.toString(procFG.getTTpTipoProc().getCodiKIdTipoProc()));
            tipoProcFG.setDescrizione(procFG.getTTpTipoProc().getDescTipoProc());       
            flussoForm.getFormProcedimentoFG().setTipoProcedimento(tipoProcFG);            
            statoProcFG.setId(Integer.toString(procFG.getTTpStatoProc().getCodiKIdStatoProc()));
            statoProcFG.setDescrizione(procFG.getTTpStatoProc().getDescStatoProc());
            flussoForm.getFormProcedimentoFG().setStatoProcedimento(statoProcFG);            
			
		}
		
   }
	
   public void getDettaglioAmministratore(FlussiToolAmministratoriForm flussoForm){
		
		TAmministratori tAmministratore = flussiDao.getDettaglioAmministratore(flussoForm.getDescCodiceFiscale());
		flussoForm.setFormAmministratore(new AmministratoreForm());
		flussoForm.getFormAmministratore().setId(tAmministratore.getCodiKIdAmmin());
		flussoForm.getFormAmministratore().setCodiceFiscale(tAmministratore.getDescCodiceFiscale());
		flussoForm.getFormAmministratore().setCognome(tAmministratore.getDescCognome());
		flussoForm.getFormAmministratore().setNome(tAmministratore.getDescNome());
		flussoForm.getFormAmministratore().setDataNascita(tAmministratore.getDataNascita());
		flussoForm.getFormAmministratore().setLuogoNascita(tAmministratore.getDescLuogoNascita());		
   }
	
	public void downloadAllegatoRelazione(FlussiToolAmministratoriForm flussoForm,HttpServletResponse response) 
	throws Exception {
		 			 
			Blob blob =  flussiDao.getAllegatoDeposito(flussoForm.getIdDeposito());
			
			if(blob == null)
			  throw new Exception("Errore durante la lettura della relazione");
					
			response.setContentType(flussoForm.getTipoAllegato()); 
			response.setHeader("Content-Disposition", "attachment;filename=" + flussoForm.getNomeAllegato());
	        //response.setHeader("Cache-control", "");
	        //response.setHeader("Pragma", "");

	        try 
	        {
	        	
			  OutputStream out = response.getOutputStream();			

			  ByteArrayOutputStream baos = new ByteArrayOutputStream();
			  byte[] buf = new byte[1024];

			  InputStream in = blob.getBinaryStream();

			  int n = 0;
			  while ((n = in.read(buf)) >= 0)
				     baos.write(buf, 0, n);

			  in.close();
			  byte[] bytes = baos.toByteArray();

			  out.write(bytes);
			  out.flush();

	        }
	        catch(SQLException ex) {
				throw new Exception("Errore: Il file della relazione non presente.");
	   		}	        
         
	}
	
	
	
	public List getListaBeniDeposito(FlussiToolAmministratoriForm flussoForm,long idDeposito) {
		ArrayList<BeneForm> listaBeni = leggiXMLFileDeposito(flussoForm,idDeposito);
		//if(listaBeni != null){
		//  	flussoForm.setListaFormBeni(listaBeni);
		//}
		return listaBeni;
	}	
	
	//Lista beni 
	//se idBene == null la lista conterrà solo beni senza identificativo e può essere utilizzata x NELL’AMBITO DEL MENU “BENI”
	public ArrayList<BeneForm> leggiXMLFileDeposito(FlussiToolAmministratoriForm flussoForm, long idDeposito) {

		ArrayList<BeneForm> listaBeni = new ArrayList<BeneForm>();
		ArrayList<BeneForm> listaBeniNoId = new ArrayList<BeneForm>();
		
		Blob blob = flussiDao.getXMLFileDeposito(idDeposito);
			if(blob==null)
				return null;

		try {
	
			InputStream inStream = blob.getBinaryStream(); 
			InputStreamReader inStreamReader = new InputStreamReader(inStream); 
			Reader reader = new BufferedReader(inStreamReader); 
 
			JAXBContext jaxbContext = JAXBContext.newInstance(RicezioneRelazione.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			RicezioneRelazione relazione = (RicezioneRelazione) unmarshaller.unmarshal(reader);
			
			//procedimento
			ProcedimentoType proc = relazione.getProcedimento();
			
			//provvedimento
			ProvvedimentoRicezioneType prov = relazione.getProvvedimento();
            if(flussoForm != null) { 
			   flussoForm.setDataInizioPeriodo(prov.getDataInizioPeriodo().toGregorianCalendar().getTime());
               flussoForm.setDataFinePeriodo(prov.getDataFinePeriodo().toGregorianCalendar().getTime());
               //flussoForm.setDataRicezione(prov.getDataPresentazioneRelazione().toGregorianCalendar().getTime());
               flussoForm.setNumeroRiferimento(prov.getNumeroRiferimento());
            }
             
            //beni
            List<BeneExtType> beni = relazione.getBene();
			for (BeneExtType bene :beni) {
				
				BeneForm beneForm = new BeneForm();
	
				beneForm.setIdDeposito(idDeposito);
				
				if(bene.getIdentificativoBene() != null)
				   beneForm.setIdBene(bene.getIdentificativoBene().longValue());
				
				try {

				TTpTipoBene tTipologia = null;	
				TTpCatBene tCategoria = null;
				TTpSottocatBene tSottoCategoria = null;				
				if(bene.getTipologia() != null) {
				   tTipologia = commonService.getIdTipologiaBene(bene.getTipologia().value());
				   if(tTipologia != null)  {
				      ComboForm tipologia = commonService.getTipologiaBene(Integer.toString(tTipologia.getCodiKIdTipoBene()));
				      beneForm.setTipologia(tipologia);
				   }
				}
				if(bene.getCategoria() != null && tTipologia != null) {
				   tCategoria = commonService.getIdCategoriaBene(tTipologia,bene.getCategoria().value());
				   if(tCategoria != null)  {
				      ComboForm categoria = commonService.getCategoriaBene(Integer.toString(tCategoria.getCodiKIdCatBene()));
				      beneForm.setCategoria(categoria);
				   }   
				}
				if(bene.getSottoCategoria() != null && tCategoria != null) {
 				   tSottoCategoria = commonService.getIdSottoCategoriaBene(tCategoria, bene.getSottoCategoria().value());
				   if(tSottoCategoria!= null)  {
  				      ComboForm sottoCategoria = commonService.getSottoCategoriaBene(Integer.toString(tSottoCategoria.getCodiKIdSottocat()));
				      beneForm.setSottocategoria(sottoCategoria);
				   }   
				}
				
		        String descrBene = "";
		         /*
		        if(bene.getSottoCategoria() != null && !bene.getSottoCategoria().value().isEmpty())							
		           descrBene = bene.getSottoCategoria().value();
		        else if (bene.getCategoria() != null && !bene.getCategoria().value().isEmpty())	
		        	descrBene = bene.getCategoria().value();
		        else if (bene.getTipologia() != null && !bene.getTipologia().value().isEmpty())		
		        	descrBene = bene.getTipologia().value();
		        */
				String luogo = "";
			    if(bene.getUbicazione() != null) {	
		    	   TAnStato stato = null;
		           if(bene.getUbicazione().getIdStato() != null) {
        		      BigInteger idStato = bene.getUbicazione().getIdStato();
        		      stato = commonService.getStatoByIdIstat(idStato.intValue());
        		      if(stato != null) {
        		         luogo = stato.getDescStato();
        		         ComboForm comboStato = new ComboForm();
        		         comboStato.setId(idStato.toString());
        		         comboStato.setDescrizione(stato.getDescStato());
        		         beneForm.setStato(comboStato);
        		      }
		           }
		           if(bene.getUbicazione().getIdComuneItaliano() != null && !bene.getUbicazione().getIdComuneItaliano().isEmpty()) {
		        	  String idComune = bene.getUbicazione().getIdComuneItaliano(); 
		        	  TAnComuni comune = null;
		        	  //comune = commonService.getComuneById(Long.parseLong(idComune));
		        	  comune = commonService.getComuneByDescCodiNsc(idComune);
		        	  if(comune == null)
		        		 comune = commonService.getComuneByDescIstatComune(idComune);
		        	  if(comune != null) {
		        	     String descrComune = comune.getDescComune();
		        	     TAnProvincia provincia = comune.getTAnProvincia();
		        	     String descrProvincia = provincia.getDescProvincia();
		        	     String descrSiglaProvincia = provincia.getDescSiglaProvincia();	
		        	     luogo = descrComune.concat(" (").concat(descrSiglaProvincia).concat(")");
		        	     ComboForm comboComune = new ComboForm();
		        	     comboComune.setId(Integer.toString(comune.getCodiKIdComune()));
		        	     comboComune.setDescrizione(descrComune);
		        	     beneForm.setComune(comboComune);	
		        	  }    
		           }
		           else { 			        	   
		        	   if(bene.getUbicazione().getDescrizioneComuneEstero() != null && !bene.getUbicazione().getDescrizioneComuneEstero().isEmpty()) {
		        		   luogo = bene.getUbicazione().getDescrizioneComuneEstero();
		        		   if(stato != null)
			        		   luogo = luogo.concat(" - ").concat(stato.getDescStato());
		        	       beneForm.setDesComuneEstero(bene.getUbicazione().getDescrizioneComuneEstero());	
		        	   }    
		            }   
		           IndirizzoForm indirizzoForm = new IndirizzoForm();
		           if (bene.getUbicazione().getTipoStrada() != null) {
		        	   ComboForm toponimo = new ComboForm();
		        	   TTpTipoStrada tTpTipoStrada = commonDao.getTTpTipoStradaByDesc(bene.getUbicazione().getTipoStrada().value());
		        	   toponimo.setId(tTpTipoStrada.getCodiKIdTipoStrada() + "");
		        	   toponimo.setDescrizione(tTpTipoStrada.getDescTipoStrada());
		        	   indirizzoForm.setToponimo(toponimo);
		           }
		           if (bene.getUbicazione().getIndirizzo() != null) {
		        	   indirizzoForm.setDenominazione(bene.getUbicazione().getIndirizzo());
		           }
		           if (bene.getUbicazione().getCivico() != null) {
		        	   indirizzoForm.setCivico(bene.getUbicazione().getCivico());
		           }
		           if (bene.getUbicazione().getCap() != null) {
		        	   indirizzoForm.setCap(bene.getUbicazione().getCap());
		           }
		           beneForm.setIndirizzo(indirizzoForm);
		        }
			        
			        if(bene.isGravatoDaPegnoIpoteca() != null)
			           beneForm.setGravame(bene.isGravatoDaPegnoIpoteca().toString());
				 
				    DatiSpecificiBene dati = bene.getDatiSpecificiBene();

				    if(dati.getAltriOggetti() != null) {
				    	AltriOggettiExtType altriOggettiType = dati.getAltriOggetti();
				    	AltriOggettiType altriOggetti = altriOggettiType.getAltriOggetti();
				        if(altriOggetti.getDescrizione() != null && !altriOggetti.getDescrizione().isEmpty()) {
					        descrBene = descrBene.concat(" - ").concat(altriOggetti.getDescrizione());
 				            beneForm.setDescrizioneCollezione(altriOggetti.getDescrizione());					        
					    }   
				    }
				    if(dati.getAnimali() != null) {
				           AnimaliExtType animaliType = dati.getAnimali();
				           AnimaliType animali = animaliType.getAnimali();	
				           if(animali.getDescrizione() != null && !animali.getDescrizione().isEmpty()) {
				              descrBene = descrBene.concat(" - ").concat(animali.getDescrizione());
				              beneForm.setDescrizioneCapo(animali.getDescrizione());
				           }   
				           if(animali.getNumeroAnimali() != null)
				        	  beneForm.setNumeroAnimali(animali.getNumeroAnimali().longValue());				           
				    } 
				    if(dati.getAzienda() != null) {
					       AziendaExtType aziendaType = dati.getAzienda();
					       AziendaType  azienda = aziendaType.getAzienda();	
					       if(azienda.getRagioneSociale() != null && !azienda.getRagioneSociale().isEmpty()) {
					          descrBene = descrBene.concat(" - ").concat(azienda.getRagioneSociale());
					          beneForm.setRagioneSociale(azienda.getRagioneSociale());
					       }
					       if(azienda.getPartitaIva() != null && !azienda.getPartitaIva().isEmpty()) {
				        	  if(azienda.getRagioneSociale() == null || azienda.getRagioneSociale().isEmpty())			    	   
						         descrBene = descrBene.concat(" - PIVA: ").concat(azienda.getPartitaIva());
						      beneForm.setPartitaIva(azienda.getPartitaIva());
					       }
						   if(azienda.getCodiceFiscale() != null && !azienda.getCodiceFiscale().isEmpty()) {
					          if((azienda.getRagioneSociale() == null || azienda.getRagioneSociale().isEmpty()) &&
								 (azienda.getPartitaIva() != null && !azienda.getPartitaIva().isEmpty())) 
 							      descrBene = descrBene.concat(" - CF: ").concat(azienda.getCodiceFiscale());
						      beneForm.setCodiceFiscale(azienda.getCodiceFiscale());
						   }
				    	   if(!luogo.isEmpty())
						      descrBene = descrBene.concat(" - ").concat(luogo);
						   if(azienda.getDescrizioneAmministratore() != null && !azienda.getDescrizioneAmministratore().isEmpty())
							  beneForm.setAmministratore(azienda.getDescrizioneAmministratore());
						   if(azienda.getDescrizioneRappresentanteSocieta() != null && !azienda.getDescrizioneRappresentanteSocieta().isEmpty())
							  beneForm.setRappresentanteSociale(azienda.getDescrizioneRappresentanteSocieta());
					       if(azienda.getNumerocciaa() != null && !azienda.getNumerocciaa().isEmpty())
							  beneForm.setNumeroCCIAA(azienda.getNumerocciaa());
					       if(azienda.getNumeroDipendenti() != null)
							  beneForm.setNumeroDipendenti(azienda.getNumeroDipendenti().longValue());
					       if(azienda.getCapitaleSociale() != null)
					    	  beneForm.setCapitaleSociale(new BigDecimal(azienda.getCapitaleSociale()));
					       if(azienda.getSettoreAttivita() != null)	 {
					    	  List<ComboForm> listaSettoreAttivita = commonService.listaSettoriAttivita();
					    	  for (int i=0; i<listaSettoreAttivita.size(); i++) {
					    		  ComboForm settoreAttivita = listaSettoreAttivita.get(i);
					    		  if(settoreAttivita.getDescrizione().equals(azienda.getSettoreAttivita())) {
					    			 beneForm.setSettoreAttivita(settoreAttivita);
					    			 break;
					    		  }   		  
					    	  }
				   	       }					       
				    } 
				    if(dati.getCollezioni() != null) {
					       CollezioniExtType collezioniType = dati.getCollezioni();
					       CollezioniType collezione = collezioniType.getCollezioni();	
					       if(collezione.getDescrizioneCollezione() != null && !collezione.getDescrizioneCollezione().isEmpty()) {
					          descrBene = descrBene.concat(" - ").concat(collezione.getDescrizioneCollezione());
					          beneForm.setDescrizioneCollezione(collezione.getDescrizioneCollezione());
					       } 
					       if(collezione.getNumeroPezzi() != null)
					    	  beneForm.setNumeroPezzi(collezione.getNumeroPezzi().longValue());
					} 
				    if(dati.getFinanziario() != null) {
				    	   Finanziario finanziario = dati.getFinanziario();
				    	   TipoFinanziarioType tipo = finanziario.getTipo();
					       if(tipo!=null && tipo.value() != null) {
					    	  FinanziarioType finanzTipo = finanziario.getFinanziario();
					    	  if(finanzTipo.getAssicurazione() != null) {
					    	      if(finanzTipo.getAssicurazione().getCodFiscaleEnte() != null && finanzTipo.getAssicurazione().getCodFiscaleEnte().isEmpty()) {
					    	    	  beneForm.setCodiceIban(finanzTipo.getAssicurazione().getCodFiscaleEnte());
					    	    	 
					    	    	  descrBene = descrBene.concat(" - ASSICUR.: ").concat(finanzTipo.getAssicurazione().getCodFiscaleEnte());
					    	      }
				    	    	  if(finanzTipo.getAssicurazione().getNumeroPolizza() != null && !finanzTipo.getAssicurazione().getNumeroPolizza().isEmpty()) {
				    	    		  
					  				  beneForm.setNumeroPolizza(finanzTipo.getAssicurazione().getNumeroPolizza());					      		  
						    	      beneForm.setNumBollettino(finanzTipo.getAssicurazione().getNumeroPolizza()); 
						    	      
						    	      if (beneForm.getDescOperFinanz()==null) {
					    	    		  beneForm.setDescOperFinanz(new ComboForm()); 
					    	    		  beneForm.getDescOperFinanz().setId(finanzTipo.getAssicurazione().getCodFiscaleEnte());
					    	    		  beneForm.getDescOperFinanz().setDescrizione(finanzTipo.getDescEnte());
					    	    	  }
						    	      
						    	      descrBene = descrBene.concat(" - POLIZZA: ").concat(finanzTipo.getAssicurazione().getNumeroPolizza());
						      	  }	
				    	    	  if(finanzTipo.getDescEnte() != null && !finanzTipo.getDescEnte().isEmpty()) {
				    	    		  beneForm.setDescrizioneEnte(finanzTipo.getDescEnte()); 
				    	    		  if (beneForm.getDescOperFinanz()==null) beneForm.setDescOperFinanz(new ComboForm()); 
				    	    		  beneForm.getDescOperFinanz().setDescrizione(finanzTipo.getDescEnte());
							    	  //descrBene = descrBene.concat(" - ").concat();		            	
	 					          }		    		  
					    	      
					    	  }						    	  					       					    	  
					    	  if(finanzTipo.getEnte() != null) {
 					            if(finanzTipo.getDescEnte() != null && !finanzTipo.getDescEnte().isEmpty()) {
 					               descrBene = descrBene.concat(" - ").concat(finanzTipo.getDescEnte());		            	
 					            }		    		  
				    	        if(finanzTipo.getEnte().getAbi() != null && finanzTipo.getEnte().getCab() != null && finanzTipo.getEnte().getNumeroContoCorrente() != null) {
				    	    	  String abi = finanzTipo.getEnte().getAbi().toString();
				    	    	  String cab = finanzTipo.getEnte().getCab().toString();
				    	    	  String cc = finanzTipo.getEnte().getNumeroContoCorrente().toString();	   
					    	      if(finanzTipo.getEnte().getIban() == null || finanzTipo.getEnte().getIban().isEmpty()) 	    	         				    	    	  
				    	    	     descrBene = descrBene.concat(" - ABI: ").concat(abi).concat(" - CAB: ").concat(cab).concat(" - N: ").concat(cc);
				    	    	  beneForm.setAbi(abi); 
				    	    	  beneForm.setCodiceAbi(abi);
				  				  beneForm.setCodiceCab(new ComboForm(cab, cab));
				  				 /// AAAAAAAA
				  				  beneForm.setNumeroRegistrazione(cc);
				    	    	  beneForm.setNumBollettino(cc);
				    	        }					    	  
				    	        if(finanzTipo.getEnte().getIban() != null && !finanzTipo.getEnte().getIban().isEmpty()) {
				    	          descrBene = descrBene.concat(" - IBAN: ").concat(finanzTipo.getEnte().getIban());
				        	      beneForm.setCodiceIban(finanzTipo.getEnte().getIban());			        	      
				    	        }
 					            if(finanzTipo.getEnte().getSwift() != null && !finanzTipo.getEnte().getSwift().isEmpty())
						           beneForm.setCodiceSwift(finanzTipo.getEnte().getSwift());

					    	  }  
					          if(finanzTipo.getDescEnte() != null && !finanzTipo.getDescEnte().isEmpty())
					        	 beneForm.setDescrizioneEnte(finanzTipo.getDescEnte());
 
					          if(finanzTipo.getFilialeModulo() != null && !finanzTipo.getFilialeModulo().isEmpty()) 
					        	 beneForm.setDescrizioneFiliale(finanzTipo.getFilialeModulo());
					          if(finanzTipo.getDirettoreIntestatario() != null && !finanzTipo.getDirettoreIntestatario().isEmpty()) 
					        	 beneForm.setDirettoreProtempore(finanzTipo.getDirettoreIntestatario());
					          if(finanzTipo.getImportoValuta() != null)
					        	  //beneForm.setImporto(finanzTipo.getImportoValuta().toString());
					        	  beneForm.setValutaEst(finanzTipo.getImportoValuta()); 
					          if(finanzTipo.getImportoEuro() != null)
					        	  beneForm.setValutaEur(finanzTipo.getImportoEuro()); 					          
					          if(finanzTipo.getTipoValuta() != null) {
						    	  List<ComboForm> listaTipiValuta = commonService.listaTipiValuta();
						    	  for (int i=0; i<listaTipiValuta.size(); i++) {
						    		  ComboForm tipoValuta = listaTipiValuta.get(i);
						    		  if(tipoValuta.getId()!=null && finanzTipo.getTipoValuta().value().equals(tipoValuta.getId())) {
						    			 beneForm.setTipoValuta(tipoValuta);
						    			 break;
						    		  }   		  
						    	  }
					          }
					       }
					       
					} 
				    if(dati.getImmateriale() != null) {
				    	   ImmaterialeExtType immateriale = dati.getImmateriale();
				    	   ImmaterialeType immateria = immateriale.getImmateriale();
					       if(immateria.getDataRegistrazione() != null) {
					    	  descrBene = descrBene.concat(" - ").concat((immateria.getDataRegistrazione().toGregorianCalendar().getTime()).toString());
					    	  beneForm.setDataRegistrazione(immateria.getDataRegistrazione().toGregorianCalendar().getTime());
					       }    
					       if(immateria.getNumeroRegistrazione() != null && !immateria.getNumeroRegistrazione().isEmpty()) {
					          descrBene = descrBene.concat(" - ").concat(immateria.getNumeroRegistrazione());
					          beneForm.setNumeroRegistrazione(immateria.getNumeroRegistrazione());
					       }
					       if(immateria.getDataScadenza() != null) {
					    	   beneForm.setDataScadenza(immateria.getDataScadenza().toGregorianCalendar().getTime());
					       }
					} 
				    if(dati.getImmobile() != null) {
				    	   ImmobileExtType immobile = dati.getImmobile();
				    	   ImmobileType immobileTipo = immobile.getImmobile();
				    	   if(!luogo.isEmpty())
					          descrBene = descrBene.concat(" - ").concat(luogo);
                           if(immobileTipo.getDatiCatastali() != null) {
                        	  DatiCatastali datiCatastali = immobileTipo.getDatiCatastali();
                        	  if(datiCatastali.getTipoCatasto() != null && !datiCatastali.getTipoCatasto().value().isEmpty()) {
    					    	 beneForm.setTipoCatasto(datiCatastali.getTipoCatasto().value());	 
                              } 
                        	  if(datiCatastali.getIdentificativoComuneCatasto() != null && !datiCatastali.getIdentificativoComuneCatasto().isEmpty()) {
                                 TAnComuni comune = null;
                                 comune = commonService.getComuneByDescBelfiore(datiCatastali.getIdentificativoComuneCatasto());                                
                                 if(comune == null) 
                                    comune = commonService.getComuneById(Long.valueOf(datiCatastali.getIdentificativoComuneCatasto()));
                                 if(comune != null) {
                                    ComboForm comuneCatastale = new ComboForm();
                        		    comuneCatastale.setId(Integer.toString(comune.getCodiKIdComune()));
                        		    comuneCatastale.setDescrizione(comune.getDescComune());
                        		    beneForm.setComune(comuneCatastale);
                                 }   
                        	  }
                        	  if(datiCatastali.getSezioneCatasto() != null && !datiCatastali.getSezioneCatasto().isEmpty()) 
                                 beneForm.setSezione(datiCatastali.getSezioneCatasto()); 
                        	  
                        	  //forzo il campo sottostante a I per far vedere i dati catastali foglioP,particellaP,subparticellaP 
                        	  beneForm.setSwitchDenunciaIdentifImmobile("I"); 
                        	  
                        	  String foglio = "";
                        	  String particella1 = "";
                        	  String subParticella1 = "";
                        	  String particella2 = "";
                        	  String subParticella2 = "";                       	  
                        	  if(datiCatastali.getFoglioCatasto() != null && !datiCatastali.getFoglioCatasto().isEmpty()) {
                        	     foglio = datiCatastali.getFoglioCatasto();
                        	     beneForm.setFoglio(foglio);
                        	     descrBene = descrBene.concat(" - f.").concat(foglio);
                        	  }
                        	  if(datiCatastali.getParticellaCatasto1() != null && !datiCatastali.getParticellaCatasto1().isEmpty()) {              	  
                        	     particella1 = datiCatastali.getParticellaCatasto1();
                        	     beneForm.setParticella1(particella1);
                        	     descrBene = descrBene.concat(" - p.").concat(particella1);
                        	  }
                        	  if(datiCatastali.getParticellaCatasto2() != null && !datiCatastali.getParticellaCatasto2().isEmpty()) {              	  
                         	     particella2 = datiCatastali.getParticellaCatasto2();
                         	     beneForm.setParticella2(particella2);
                         	     if(!particella1.isEmpty())
                         	        descrBene = descrBene.concat("/").concat(particella2);
                        	  }
                        	  if(datiCatastali.getSubparticellaCatasto1() != null && !datiCatastali.getSubparticellaCatasto1().isEmpty()) {               	                         		  
                         	     subParticella1 = datiCatastali.getSubparticellaCatasto1();
                          	     beneForm.setSub1(subParticella1);
                         	     descrBene = descrBene.concat(" - s.").concat(subParticella1);
                         	  }
                        	  if(datiCatastali.getSubparticellaCatasto2() != null && !datiCatastali.getSubparticellaCatasto2().isEmpty()) {              	                         		  
                         	     subParticella2 = datiCatastali.getSubparticellaCatasto2();
                          	     beneForm.setSub2(subParticella2);
                          	     if(!subParticella1.isEmpty())
                           	        descrBene = descrBene.concat("/").concat(subParticella2);
                         	  }
                        	  if(datiCatastali.getIdentificativoCategoriaCatastale() != null && !datiCatastali.getIdentificativoCategoriaCatastale().value().isEmpty()) {
   					             List<ComboForm> categorieCatastali = commonService.getCategorieCatastali();
   						         for (ComboForm categoriaCatastale : categorieCatastali) {
   								     if(categoriaCatastale.getId().equalsIgnoreCase(datiCatastali.getIdentificativoCategoriaCatastale().value())){
   									    beneForm.setCategoriaCatastale(categoriaCatastale);
   									    break;
   								     }
   							     }
                        	  }
                        	  if(datiCatastali.getImportoRenditaCatastale() != null)
                        		 beneForm.setRenditaCatastale(datiCatastali.getImportoRenditaCatastale()); 
                        	  if(datiCatastali.getImportoRenditaDominicale() != null)
                        		 beneForm.setRenditaDominicale(datiCatastali.getImportoRenditaDominicale()); 
                        	  
                           }
					       if(immobileTipo.getEdificio() != null && !immobileTipo.getEdificio().isEmpty()) 
					    	  beneForm.setEdificio(immobileTipo.getEdificio());
					       if(immobileTipo.getPiano() != null && !immobileTipo.getPiano().isEmpty()) 
					       	  beneForm.setPiano(immobileTipo.getPiano()); 
						   if(immobileTipo.getScala() != null && !immobileTipo.getScala().isEmpty()) 
						  	  beneForm.setScala(immobileTipo.getScala());  
						   if(immobileTipo.getInterno() != null && !immobileTipo.getInterno().isEmpty()) 
						      beneForm.setInterno(immobileTipo.getInterno()); 
						   if(immobileTipo.getVolumeInt() != null)        
							  beneForm.setMetriCubiIntero(immobileTipo.getVolumeInt().shortValue());   					    	      
						   if(immobileTipo.getVolumeDec() != null)        
							  beneForm.setMetriCubiDecimale(immobileTipo.getVolumeDec().shortValue());   					    	      					             
						   if(immobileTipo.getVaniInt() != null)        
							  beneForm.setVaniIntero(immobileTipo.getVaniInt().shortValue());   
						   if(immobileTipo.getVaniDec() != null)        
							  beneForm.setVaniDecimale(immobileTipo.getVaniDec().shortValue());   						   
					       if(immobileTipo.getLotto() != null && !immobileTipo.getLotto().isEmpty()) 
	                          beneForm.setLotto(immobileTipo.getLotto());
					       if(immobileTipo.getSuperficieInt() != null) 
		                      beneForm.setMetriQuadriIntero(immobileTipo.getSuperficieInt().shortValue());
					       if(immobileTipo.getSuperficieDec() != null) 
		                      beneForm.setMetriQuadriDecimale(immobileTipo.getSuperficieDec().byteValue());
					       if(immobileTipo.getEttari() != null) 
			                  beneForm.setEttari(immobileTipo.getEttari().intValue());
					       if(immobileTipo.getAre() != null) 
				              beneForm.setAre(immobileTipo.getAre().intValue());
					       if(immobileTipo.getCentiare() != null) 
					          beneForm.setCentiare(immobileTipo.getCentiare().intValue());			       
					       if(immobileTipo.getNumeroDomandaAccatastamento() != null && !immobileTipo.getNumeroDomandaAccatastamento().isEmpty())
					    	  beneForm.setSchedaAccatastamento(immobileTipo.getNumeroDomandaAccatastamento());
					       if(immobileTipo.getDataDomandaAccatastamento() != null)
					    	  beneForm.setDataAccatastamento(immobileTipo.getDataDomandaAccatastamento().toGregorianCalendar().getTime());
					       if(immobileTipo.getAppartenenzaaFabbricatoConfiscato() != null && !immobileTipo.getAppartenenzaaFabbricatoConfiscato().isEmpty())
					    	  beneForm.setAppartenenza(immobileTipo.getAppartenenzaaFabbricatoConfiscato());
					       //if(immobileTipo.getTipoFondo() != null)
		      					       
					}
				    if(dati.getVeicolo() != null) {
				    	    VeicoloExtType veicolo = dati.getVeicolo();
				    	    VeicoloType veicoloTipo = veicolo.getVeicolo();					    	    
				    	    if(veicoloTipo.getDescrizioneModello() != null && !veicoloTipo.getDescrizioneModello().isEmpty()) {
				    	       descrBene = descrBene.concat(" - ").concat(veicoloTipo.getDescrizioneModello());
 					        }
						    if(veicoloTipo.getNumeroTarga() != null && !veicoloTipo.getNumeroTarga().isEmpty()) {
							    descrBene = descrBene.concat(" - TARGA: ").concat(veicoloTipo.getNumeroTarga());
						    }   
						    else if(veicoloTipo.getNumeroTelaio() != null && !veicoloTipo.getNumeroTelaio().isEmpty()) 
							    descrBene = descrBene.concat(" - TELAIO: ").concat(veicoloTipo.getNumeroTelaio());

					       if(veicoloTipo.getNumeroRegistro() != null && !veicoloTipo.getNumeroRegistro().isEmpty())  {
					    	  beneForm.setNumRegBeneMobileRegistrato(Long.valueOf(veicoloTipo.getNumeroRegistro())); 
					    	  beneForm.setNumeroRegistrazione(veicoloTipo.getNumeroRegistro()); 
					       } 
					       if(veicoloTipo.getDataRegistrazione() != null)
					    	  beneForm.setDataRegistrazione(veicoloTipo.getDataRegistrazione().toGregorianCalendar().getTime()); 
					       if(veicoloTipo.getNumeroIdentificativo() != null && !veicoloTipo.getNumeroIdentificativo().isEmpty()) 
					    	  beneForm.setNumeroIdentificazione(veicoloTipo.getNumeroIdentificativo()); 
					       if(veicoloTipo.getCodiceImmatricolazione() != null && !veicoloTipo.getCodiceImmatricolazione().isEmpty()) 
					    	  beneForm.setCodImmatricolazioneVeicolo(Long.valueOf(veicoloTipo.getCodiceImmatricolazione())); 
					       if(veicoloTipo.getDescrizioneModello() != null && !veicoloTipo.getDescrizioneModello().isEmpty()) 
					    	  beneForm.setModelloVeicolo(veicoloTipo.getDescrizioneModello()); 
					       if(veicoloTipo.getNumeroTarga() != null && !veicoloTipo.getNumeroTarga().isEmpty()) 
					    	  beneForm.setTarga(veicoloTipo.getNumeroTarga()); 
					       if(veicoloTipo.getNumeroTelaio() != null && !veicoloTipo.getNumeroTelaio().isEmpty()) 
					    	  beneForm.setTelaio(veicoloTipo.getNumeroTelaio()); 
					       if(veicoloTipo.getDescrizioneColore() != null && !veicoloTipo.getDescrizioneColore().isEmpty()) 
					    	  beneForm.setColoreVeicolo(veicoloTipo.getDescrizioneColore()); 					       
				    }
				    //descrBene = getBeneDescr(bene);			
					beneForm.setDescrizioneBene(bene.getDescBene());
					
				       
					if(flussoForm == null) {
					   if(bene.getIdentificativoBene() == null)	{
						  //System.out.println(beneForm.getCategoria().getDescrizione());
  				          listaBeniNoId.add(beneForm);
					   }
				    }
					else
					   listaBeni.add(beneForm);
					
 				} catch (Exception e) {
 					e.printStackTrace();
					logger.error("Errore x Tipologia: "+bene.getTipologia().value()+", Categoria: "+bene.getCategoria().value()+", SottoCategoria: "+bene.getSottoCategoria().value()+" : " + e);					
				}

			}    
	
			if(flussoForm == null)
			   return listaBeniNoId;
			else
			   return listaBeni;
	
		} catch (Exception e) {
			logger.error("Errore " + e);
			return null;
		}
	}

	public String registraAttoRelazione(FlussiToolAmministratoriForm flussoForm,int idUffOwn,String idCodiceUtente) 
	throws SQLException, Exception {
		
	    long idDeposito = flussoForm.getIdDeposito();

	    long idProc = flussoForm.getIdProc();
	    long idProcFG = flussoForm.getIdProcFG();

	    String tipoAllegato = flussoForm.getTipoAllegato();
	    String nomeAllegato = flussoForm.getNomeAllegato();
	    long idAmm = flussoForm.getFormAmministratore().getId();    
	    String cognomeAmm = flussoForm.getFormAmministratore().getCognome();
	    String nomeAmm = flussoForm.getFormAmministratore().getNome();

	    Date dataInizio = flussoForm.getDataInizioPeriodo();
	    Date dataFine = flussoForm.getDataFinePeriodo();
	    Date dataRicezione = flussoForm.getDataRicezione();
	    String numeroRiferimento = flussoForm.getNumeroRiferimento();

		BigInteger idPDOC = BigInteger.valueOf(0);
		Blob blob =  flussiDao.getAllegatoDeposito(idDeposito);		
		
		//Inserimento PDF in Documentale
        if(blob != null) {
        	
    	   try {
                 String ufficioMitt = "";
                 TAnUfficio tUfficio = commonService.getTAnUfficioByIdUff(idUffOwn);   
                 ufficioMitt = tUfficio.getDescUffGiudiz();
                 idPDOC = documentaleService.inserimentoDocumento("ToolAmministratori", tipoAllegato, nomeAllegato,  "Tool Amministratori", blob, ufficioMitt);        	
    	   }
           catch(Throwable ex) {
	    	    logger.error("Tool Amm. - Errore in fase inserimento documentale: " + ex);
 	       }	 

    	   //Inserimento Atto - Relazione dell'Amministratore 	
		  long idStatoAtto = 3;
		  long idTipoAtto = 29;
		  
		  long numPDOC = idPDOC.longValue();
		  long idAtto = 0;
		  
		  if(numPDOC > 0)
			 idAtto = attoDao.inserisciAttoRelazioneAmm(idUffOwn,idCodiceUtente,idProcFG,idTipoAtto,Long.parseLong(Constants.RELAZIONE_AMMINISTRATORE),idStatoAtto,dataRicezione,idAmm,cognomeAmm,nomeAmm,dataInizio,dataFine,numPDOC,null,tipoAllegato,nomeAllegato,numeroRiferimento);
		  else
			 idAtto = attoDao.inserisciAttoRelazioneAmm(idUffOwn,idCodiceUtente,idProcFG,idTipoAtto,Long.parseLong(Constants.RELAZIONE_AMMINISTRATORE),idStatoAtto,dataRicezione,idAmm,cognomeAmm,nomeAmm,dataInizio,dataFine,numPDOC,blob,tipoAllegato,nomeAllegato,numeroRiferimento);


		  //Accettazione Deposito 			  
		  TDepositi deposito = flussiDao.getDettaglioDeposito(idDeposito, idUffOwn);
		  if(deposito != null && deposito.getDescIdDepositoGl() != null && !"".equals(deposito.getDescIdDepositoGl())) {
				
				AccettaDeposito accettaDeposito = new AccettaDeposito();
				accettaDeposito.setIdBusta(deposito.getDescIdDepositoGl());					
				//Invoke del servizio accettaDeposito()
				serviziAccettazioneServiceClient.accettaDeposito(accettaDeposito,deposito.getDescIdDestinatario());
				
				//Se l'invocazione non va in errore (eccezione) viene effettuata la registrazione dell'avvenuto deposito
				deposito.setCodiFkIdAtto(idAtto);
				deposito.setFlgAccettato('S');	
				deposito.setDataAccettazione(new Date());
				deposito.setDescUtente(idCodiceUtente);
				flussiDao.updateDeposito(deposito);
		 }
		  
		 //Aggiornamento TDepositoAmm 
		 TAtto atto = new TAtto(); 
		 TAttoId attoId = new TAttoId();
		 attoId.setCodiKIdAtto(idAtto);
		 attoId.setCodiKIdUffOwner(idUffOwn);
		 atto.setId(attoId);
		 TDepositiAmm depositoAmm = flussiDao.getDettaglioDepositoAmm(idDeposito, 'N');
		 depositoAmm.setTAtto(atto);
		 depositoAmm.setFlgAcquisito('S');	
		 flussiDao.updateDepositoAmm(depositoAmm);

		 //Aggiornamento Tabelle Beni
		 List listaBeni = updateTabelleFromDeposito(idDeposito,idUffOwn,idCodiceUtente,idProcFG, idAtto, idAmm);
		
    	}
  
		return "ok";

	}
	
	public List updateTabelleFromDeposito(long idDeposito, int idUffOwner, String idCodiceUtente, long idProcFG, long idAtto, long idAmm) 
	{
		
		ArrayList<TBene> listaBeni = new ArrayList<TBene>();
				
		Blob blob = flussiDao.getXMLFileDeposito(idDeposito);
		
		try {
			
			if(blob==null)
				return null;

			InputStream inStream = blob.getBinaryStream(); 
			InputStreamReader inStreamReader = new InputStreamReader(inStream); 
			Reader reader = new BufferedReader(inStreamReader); 
 
			JAXBContext jaxbContext = JAXBContext.newInstance(RicezioneRelazione.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			RicezioneRelazione relazione = (RicezioneRelazione) unmarshaller.unmarshal(reader);
			
            //beni
            List<BeneExtType> beni = relazione.getBene();
			for (BeneExtType bene :beni) {

				if(bene.getIdentificativoBene() != null) {
				  
				  try
				  {					
                   
				   TBene tbene = beneDao.getTBeneById(bene.getIdentificativoBene().longValue(), idUffOwner);
                   /*
				   TBeneId tbeneId = new TBeneId();
                   tbeneId.setCodiKIdBene(bene.getIdentificativoBene().longValue());
                   tbeneId.setCodiKIdUffOwner(idUffOwner);
                   tbene.setId(tbeneId);
                   */
                   tbene.setDataUltAgg(new Date());              
                  
		           String descrBene = "";
		           if(bene.getSottoCategoria() != null && !bene.getSottoCategoria().value().isEmpty())							
		               descrBene = bene.getSottoCategoria().value();
		           else if (bene.getCategoria() != null && !bene.getCategoria().value().isEmpty())	
		        	   descrBene = bene.getCategoria().value();
		           else if (bene.getTipologia() != null && !bene.getTipologia().value().isEmpty())	
		        	   descrBene = bene.getTipologia().value();

		           TTpTipoBene tTipologia = null;
				   if(bene.getTipologia() != null) {					  
				      tTipologia = commonService.getIdTipologiaBene(bene.getTipologia().value());
				      if(tTipologia != null)
				         tbene.setTTpTipoBene(tTipologia);	
				   }
				   TTpCatBene tCategoria = null;
				   if(bene.getCategoria() != null && tTipologia != null) {
				      tCategoria = commonService.getIdCategoriaBene(tTipologia,bene.getCategoria().value());
				      if(tCategoria != null)				      
				         tbene.setTTpCatBene(tCategoria);
				   }
				   TTpSottocatBene tSottoCategoria = null;
				   if(bene.getSottoCategoria() != null && tCategoria != null) {
				      tSottoCategoria = commonService.getIdSottoCategoriaBene(tCategoria,bene.getSottoCategoria().value());	  
				      if(tSottoCategoria != null)
				         tbene.setTTpSottocatBene(tSottoCategoria);
				   }
				   
		           String luogo = "";
			       if(bene.getUbicazione() != null) {	
			    	   
				    	  TAnStato stato = null;
			    	      if(bene.getUbicazione().getIdStato() != null) {
			        	     BigInteger idStato = bene.getUbicazione().getIdStato();
		        		     stato = commonService.getStatoByIdIstat(idStato.intValue());
		        		     if(stato != null)
		        		        tbene.setTAnStato(stato);
			        	  } 
			        	  if(bene.getUbicazione().getIdComuneItaliano() != null) {
			        	     String idComune = bene.getUbicazione().getIdComuneItaliano(); 
			        	     //TAnComuni comune = commonService.getComuneById(Long.parseLong(idComune));
				        	  TAnComuni comune = null;
				        	  comune = commonService.getComuneByDescIstatComune(idComune);
				        	  if(comune != null) {
			        	         tbene.setTAnComuni(comune);
  	        		             stato = commonService.getStatoById(1);
		        		         tbene.setTAnStato(stato);
		        		         
			        	         String descrComune = comune.getDescComune();
			        	         TAnProvincia provincia = comune.getTAnProvincia();
			        	         String descrProvincia = provincia.getDescProvincia();
			        	         String descrSiglaProvincia = provincia.getDescSiglaProvincia();	
			        	         luogo = descrComune.concat(" (").concat(descrSiglaProvincia).concat(")");
				        	  }   
			        	  }   
 			              else	
			              { 			        	   
			        	     if(bene.getUbicazione().getDescrizioneComuneEstero() != null && !bene.getUbicazione().getDescrizioneComuneEstero().isEmpty()) {
			        	    	luogo = bene.getUbicazione().getDescrizioneComuneEstero();
			        		    if(stato != null)
			        		       luogo = luogo.concat(" - ").concat(stato.getDescStato());
			        	    	tbene.setDescComuneEstero(bene.getUbicazione().getDescrizioneComuneEstero());	
			        	     }
			              }   
				           if (bene.getUbicazione().getTipoStrada() != null) {
				        	   TTpTipoStrada tTpTipoStrada = commonDao.getTTpTipoStradaByDesc(bene.getUbicazione().getTipoStrada().value());
				        	   tbene.setTTpTipoStrada(tTpTipoStrada);
				           }
				           if (bene.getUbicazione().getIndirizzo() != null) {
				        	   tbene.setDescIndirizzo(bene.getUbicazione().getIndirizzo());
				           }
				           if (bene.getUbicazione().getCivico() != null) {
				        	   tbene.setDescCivico(bene.getUbicazione().getCivico());
				           }
				           if (bene.getUbicazione().getCap() != null) {
				        	   tbene.setDescCap(bene.getUbicazione().getCap());
				           }
			        }
			        
			        if(bene.isGravatoDaPegnoIpoteca() != null) {
			        	if(bene.isGravatoDaPegnoIpoteca().equals("true") || bene.isGravatoDaPegnoIpoteca().equals("TRUE"))
			               tbene.setFlgGravame('S');
			        	if(bene.isGravatoDaPegnoIpoteca().equals("false") || bene.isGravatoDaPegnoIpoteca().equals("FALSE"))
			        	   tbene.setFlgGravame('N');
			        }
			        
				    DatiSpecificiBene dati = bene.getDatiSpecificiBene();

				    if(dati.getAltriOggetti() != null) {
				    	TCollezioni tcollezioni = new TCollezioni();
				    	TCollezioniId tcollezioniId = new TCollezioniId();
				    	tcollezioniId.setCodiKIdColl(bene.getIdentificativoBene().longValue());
			    	    tcollezioniId.setCodiKIdUffOwner(idUffOwner);
			    	    tcollezioni.setId(tcollezioniId);
			    	    /*
			    	    tcollezioniId.setCodiKIdColl(bene.getIdentificativoBene().longValue());
			    	    tcollezioniId.setCodiKIdUffOwner(idUffOwner);
			    	    tcollezioni.setId(tcollezioniId);
			    	     */
			    	    //tcollezioni.setDataUltAgg(new Date());				    	   
				    	
				    	AltriOggettiExtType altriOggettiType = dati.getAltriOggetti();
				    	AltriOggettiType altriOggetti = altriOggettiType.getAltriOggetti();
				        if(altriOggetti.getDescrizione() != null && !altriOggetti.getDescrizione().isEmpty()) {
					        descrBene = descrBene.concat(" - ").concat(altriOggetti.getDescrizione());
						    //tcollezioni.setDescCollezione(altriOggetti.getDescrizione());
					    }   
					    tbene.setDescrBene(descrBene);
                        beneDao.modificaMobile((long)idUffOwner, idCodiceUtente, idProcFG, 0L, tbene, null, tcollezioni, null, null, null, null);		           
                        //beneDao.modificaMobile((long)idUffOwner, idCodiceUtente, idProcFG, 0L, tbene, null, null, null, null, null, null);
				    
				    }				    
				    if(dati.getAnimali() != null) {
                           
				    	   TAnimali tanimali = beneDao.getTAnimaliById(bene.getIdentificativoBene().longValue(), idUffOwner);
/*				    	   TAnimaliId tanimaliId = new TAnimaliId();
				    	   tanimaliId.setCodiKIdAnimale(bene.getIdentificativoBene().longValue());
				    	   tanimaliId.setCodiKIdUffOwner(idUffOwner);
				    	   tanimali.setId(tanimaliId);
				    	   */
				    	   tanimali.setDataUltAgg(new Date());				    	   
				    	   
				           AnimaliExtType animaliType = dati.getAnimali();
				           AnimaliType animali = animaliType.getAnimali();	
				           if(animali.getDescrizione() != null && !animali.getDescrizione().isEmpty()) {
				              descrBene = descrBene.concat(" - ").concat(animali.getDescrizione());
				              tanimali.setDescCapo(animali.getDescrizione());
				           }   
				           if(animali.getNumeroAnimali() != null)
				        	  tanimali.setNumeAnimali(animali.getNumeroAnimali().longValue());	
		
						   tbene.setDescrBene(bene.getDescBene());

                           beneDao.modificaMobile((long)idUffOwner, idCodiceUtente, idProcFG, 0L, tbene, tanimali, null, null, null, null, null);		           
                                                                
				    } 
				    if(dati.getAzienda() != null) {
				    	
				    	   TAzienda tazienda = beneDao.getTAziendaById(bene.getIdentificativoBene().longValue(), idUffOwner);
/*				    	   TAziendaId taziendaId = new TAziendaId();
				    	   taziendaId.setCodiKIdAzienda(bene.getIdentificativoBene().longValue());
				    	   taziendaId.setCodiKIdUffOwner(idUffOwner);
				    	   tazienda.setId(taziendaId);
*/
				    	   tazienda.setDataUltAgg(new Date());				    	   

				    	   AziendaExtType aziendaType = dati.getAzienda();
					       AziendaType  azienda = aziendaType.getAzienda();	
					       if(azienda.getRagioneSociale() != null && !azienda.getRagioneSociale().isEmpty()) {
					          descrBene = descrBene.concat(" - ").concat(azienda.getRagioneSociale());
					          tazienda.setDescRagioneSoc(azienda.getRagioneSociale());
					       }
					       if(azienda.getPartitaIva() != null && !azienda.getPartitaIva().isEmpty()) {
					          if(azienda.getRagioneSociale() == null || azienda.getRagioneSociale().isEmpty())
					    	     descrBene = descrBene.concat(" - PIVA: ").concat(azienda.getPartitaIva());
						      tazienda.setDescPIva(azienda.getPartitaIva());
					       }
						   if(azienda.getCodiceFiscale() != null && !azienda.getCodiceFiscale().isEmpty()) {
				        	  if((azienda.getRagioneSociale() == null || azienda.getRagioneSociale().isEmpty()) &&
					 	         (azienda.getPartitaIva() != null && !azienda.getPartitaIva().isEmpty())) 
							      descrBene = descrBene.concat(" - CF: ").concat(azienda.getCodiceFiscale());
						      tazienda.setDescCodFisc(azienda.getCodiceFiscale());
						   }
				    	   if(!luogo.isEmpty())						   
						       descrBene = descrBene.concat(" - ").concat(luogo);
						   if(azienda.getDescrizioneAmministratore() != null && !azienda.getDescrizioneAmministratore().isEmpty())
							  tazienda.setDescAmministratore(azienda.getDescrizioneAmministratore());
						   if(azienda.getDescrizioneRappresentanteSocieta() != null && !azienda.getDescrizioneRappresentanteSocieta().isEmpty())
							  tazienda.setDescRappresSoc(azienda.getDescrizioneRappresentanteSocieta());
					       if(azienda.getNumerocciaa() != null && !azienda.getNumerocciaa().isEmpty())
					    	  tazienda.setDescCciaa(azienda.getNumerocciaa());
					       if(azienda.getNumeroDipendenti() != null)
					    	  tazienda.setNumeDipendenti(azienda.getNumeroDipendenti().longValue());
					       if(azienda.getCapitaleSociale() != null)
					    	  tazienda.setImpoCapitaleSoc(new BigDecimal(azienda.getCapitaleSociale()));
					       if(azienda.getSettoreAttivita() != null)	 {
					    	   
							   TTpSettoreAtt settoreAttivita = amministrazioneService.getTpSettoreAttivitaByDesc(azienda.getSettoreAttivita().value());
				    		   tazienda.setTTpSettoreAtt(settoreAttivita);
				   	       }
					       
					       tbene.setDescrBene(bene.getDescBene());
						   //tbene.setDescrBene(descrBene);
					       
				           beneDao.modificaAzienda((long)idUffOwner, idCodiceUtente, idProcFG, 0L, tbene, tazienda, null, null, null);
					       
				    } 
				    if(dati.getCollezioni() != null) {
				    	
			    	    TCollezioni tcollezioni = beneDao.getTCollezioniById(bene.getIdentificativoBene().longValue(), idUffOwner);
/*			    	    TCollezioniId tcollezioniId = new TCollezioniId();
			    	    tcollezioniId.setCodiKIdColl(bene.getIdentificativoBene().longValue());
			    	    tcollezioniId.setCodiKIdUffOwner(idUffOwner);
			    	    tcollezioni.setId(tcollezioniId);
*/
			    	    tcollezioni.setDataUltAgg(new Date());				    	   

				    	CollezioniExtType collezioniType = dati.getCollezioni();
					    CollezioniType collezione = collezioniType.getCollezioni();	
					    if(collezione.getDescrizioneCollezione() != null && !collezione.getDescrizioneCollezione().isEmpty()) {
					       descrBene = descrBene.concat(" - ").concat(collezione.getDescrizioneCollezione());
					       tcollezioni.setDescCollezione(collezione.getDescrizioneCollezione());
					    } 
					    if(collezione.getNumeroPezzi() != null)
					  	   tcollezioni.setNumePezzi(collezione.getNumeroPezzi().longValue());

					    tbene.setDescrBene(bene.getDescBene());
					       
                        beneDao.modificaMobile((long)idUffOwner, idCodiceUtente, idProcFG, 0L, tbene, null, tcollezioni, null, null, null, null);		           
					       
					} 
				    if(dati.getFinanziario() != null) {
				    	   TBeneFin tbeneFin =	null;	
				    	   TDenaro tdenaro = null;
				    	   TContante tcontante = null;
				    	   Finanziario finanziario = dati.getFinanziario();
				    	   TipoFinanziarioType tipo = finanziario.getTipo();
					       if(tipo.value() != null) {                    
					    	 if(tipo.value().equalsIgnoreCase("Finanziario")) {
					    	    tbeneFin = beneDao.getTBeneFinanziarioById(bene.getIdentificativoBene().longValue(), idUffOwner);
/*					    	    TBeneFinId tbeneFinId = new TBeneFinId();
					    	    tbeneFinId.setCodiKIdBeneFin(bene.getIdentificativoBene().longValue());
					    	    tbeneFinId.setCodiKIdUffOwner(idUffOwner);
					    	    tbeneFin.setId(tbeneFinId);
*/
					    	    tbeneFin.setDataUltAgg(new Date());				    	   
					    	 }
					    	 if(tipo.value().equalsIgnoreCase("Denaro")) {
						    	tdenaro = beneDao.getTDenaroById(bene.getIdentificativoBene().longValue(), idUffOwner);
/*						    	TDenaroId tdenaroId = new TDenaroId();
						    	tdenaroId.setCodiKIdDenaro(bene.getIdentificativoBene().longValue());
						    	tdenaroId.setCodiKIdUffOwner(idUffOwner);
						    	tdenaro.setId(tdenaroId);
*/
						    	tdenaro.setDataUltAgg(new Date());				    	   
					    	 }
					    	 if(tipo.value().equalsIgnoreCase("DenaroContante")) {
						    	tcontante = beneDao.getTContanteById(bene.getIdentificativoBene().longValue(), idUffOwner);
/*						    	TContanteId tcontanteId = new TContanteId();
						    	tcontanteId.setCodiKIdContante(bene.getIdentificativoBene().longValue());
						    	tcontanteId.setCodiKIdUffOwner(idUffOwner);
						    	tcontante.setId(tcontanteId);
*/
						    	tcontante.setDataUltAgg(new Date());				    	   
						      }
					    	  FinanziarioType finanzTipo = finanziario.getFinanziario();
					    	  if(finanzTipo.getAssicurazione() != null) {
					    	      if(finanzTipo.getAssicurazione().getCodFiscaleEnte() != null && finanzTipo.getAssicurazione().getCodFiscaleEnte().isEmpty()) {
					    	    	 tbeneFin.setDescIban(finanzTipo.getAssicurazione().getCodFiscaleEnte());	
			    	    	         descrBene = descrBene.concat(" - ASSICUR.: ").concat(finanzTipo.getAssicurazione().getCodFiscaleEnte());
					    	      }
						      	  if(finanzTipo.getAssicurazione().getNumeroPolizza() != null && finanzTipo.getAssicurazione().getNumeroPolizza().isEmpty()) {
						      		 tbeneFin.setDescRegistrazione(finanzTipo.getAssicurazione().getNumeroPolizza());		
			    	    	         descrBene = descrBene.concat(" - POLIZZA: ").concat(finanzTipo.getAssicurazione().getNumeroPolizza());
						      	  }
						      }						    	  					          				    	      					    	  
 				              if(finanzTipo.getDescEnte() != null && !finanzTipo.getDescEnte().isEmpty()) {
	 					          descrBene = descrBene.concat(" - ").concat(finanzTipo.getDescEnte());		            	
	 					      }		
 				              if (finanzTipo.getEnte()!=null) {
					    	      if(finanzTipo.getEnte().getAbi() != null && finanzTipo.getEnte().getCab() != null && finanzTipo.getEnte().getNumeroContoCorrente() != null) {
					    	    	  String abi = finanzTipo.getEnte().getAbi().toString();
					    	    	  String cab = finanzTipo.getEnte().getCab().toString();
					    	    	  String cc = finanzTipo.getEnte().getNumeroContoCorrente().toString();	   
						    	      if(finanzTipo.getEnte().getIban() == null || finanzTipo.getEnte().getIban().isEmpty()) 	    	         				    	    	  
					    	    	     descrBene = descrBene.concat(" - ABI: ").concat(abi).concat(" - CAB: ").concat(cab).concat(" - N: ").concat(cc);
	
				    			      TAnOperFinanz of = new TAnOperFinanz();
				    			      TAnOperFinanzId ofId = new TAnOperFinanzId();
				    			      ofId.setCodiKIdAbi(abi);
				    			      ofId.setCodiKIdCab(cab);
					    	    	  of.setId(ofId);
					    	    	  if(tbeneFin != null)  {
					    	    		 tbeneFin.setTAnOperFinanz(of);
							      		 tbeneFin.setDescRegistrazione(cc);						      		  
					    	    	  }	 
					    	    	  if(tdenaro != null)  {
				    			         tdenaro.setTAnOperFinanz(of);	
					    	    	  }
				    			      if(tcontante != null)	 {
					    	    		 tcontante.setTAnOperFinanz(of);
					    	    		 tcontante.setDescBollettino(cc);
				    			      }
					    	    	  
					    	      }		
 				              }
				    	      if(finanzTipo.getEnte() != null) {
				    	        if(finanzTipo.getEnte().getIban() != null && !finanzTipo.getEnte().getIban().isEmpty()) { 
				    	           descrBene = descrBene.concat(" - IBAN: ").concat(finanzTipo.getEnte().getIban());
				    	           if(tbeneFin != null) tbeneFin.setDescIban(finanzTipo.getEnte().getIban());
				    	           if(tdenaro != null) tdenaro.setDescIban(finanzTipo.getEnte().getIban());
				    	           if(tcontante != null) tcontante.setDescIban(finanzTipo.getEnte().getIban());
				    	        }
					            if(finanzTipo.getEnte().getSwift() != null && !finanzTipo.getEnte().getSwift().isEmpty()) {
						    	   if(tbeneFin != null) tbeneFin.setDescBicSwift(finanzTipo.getEnte().getSwift());
						    	   if(tdenaro != null) tdenaro.setDescBicSwift(finanzTipo.getEnte().getSwift());
						    	   if(tcontante != null) tcontante.setDescBicSwift(finanzTipo.getEnte().getSwift());
						        }
				    	      }	 
					          if(finanzTipo.getDescEnte() != null && !finanzTipo.getDescEnte().isEmpty()) {
					    	     if(tbeneFin != null) tbeneFin.setDescEnteCreditizio(finanzTipo.getDescEnte());
					    	     if(tdenaro != null) tdenaro.setDescEnteDepos(finanzTipo.getDescEnte());
					    	     if(tcontante != null) tcontante.setDescEnteDepos(finanzTipo.getDescEnte());
					          }
					          if(finanzTipo.getFilialeModulo() != null && !finanzTipo.getFilialeModulo().isEmpty()) {
						    	 if(tbeneFin != null) tbeneFin.setDescFilialeEnte(finanzTipo.getFilialeModulo());
						    	 if(tdenaro != null) tdenaro.setDescFiliale(finanzTipo.getFilialeModulo());
						    	 // TODO RIVEDERE come d
						    	 //if(tcontante != null) tcontante.setNumeIdentModTrib(finanzTipo.getgetFilialeModulo()));						    	 
				  		      }
					          if(finanzTipo.getDirettoreIntestatario() != null && !finanzTipo.getDirettoreIntestatario().isEmpty()) {
						    	 if(tbeneFin != null) tbeneFin.setDescDirettoreEnte(finanzTipo.getDirettoreIntestatario());
						    	 if(tdenaro != null) tdenaro.setDescDirettore(finanzTipo.getDirettoreIntestatario());
						    	 if(tcontante != null) tcontante.setDescIntestatario(finanzTipo.getDirettoreIntestatario());						    	 						    	 
						      }
					          if(finanzTipo.getIdent() != null && !finanzTipo.getIdent().isEmpty()) {
						    	 if(tdenaro != null) tdenaro.setDescIdentEnte(finanzTipo.getIdent());
						    	 if(tcontante != null) tcontante.setDescIdentEnte(finanzTipo.getIdent());
						      }		
					          if(finanzTipo.getImportoValuta() != null) {
							  	 if(tbeneFin != null) tbeneFin.setImpoValuta(finanzTipo.getImportoValuta());
							   	 if(tdenaro != null) tdenaro.setImpoValuta(finanzTipo.getImportoValuta());
							   	 if(tcontante != null) tcontante.setImpoValuta(finanzTipo.getImportoValuta());
							  }
					          if(finanzTipo.getImportoEuro() != null) {
							  	 if(tbeneFin != null) tbeneFin.setImpoEuro(finanzTipo.getImportoEuro());
								 if(tdenaro != null) tdenaro.setImpoEuro(finanzTipo.getImportoEuro());
								 if(tcontante != null) tcontante.setImpoEuro(finanzTipo.getImportoEuro());
							  }	
					          if(finanzTipo.getTipoValuta() != null) {
					        	  TTpValuta tipovaluta = commonService.getTpValuta(finanzTipo.getTipoValuta().value());
					        	  if(tbeneFin != null) tbeneFin.setTTpValuta(tipovaluta);
					        	  if(tdenaro != null) tdenaro.setTTpValuta(tipovaluta);
					        	  if(tcontante != null) tcontante.setTTpValuta(tipovaluta);
					          }
					       }
					       
						   tbene.setDescrBene(bene.getDescBene());
					       
					       if(tbeneFin != null) 
					    	  beneDao.modificaFinanziario((long)idUffOwner, idCodiceUtente, idProcFG, 0L, tbene, tbeneFin);

					       if(tdenaro != null) 
						      beneDao.modificaDenaroContante((long)idUffOwner, idCodiceUtente, idProcFG, 0L, tbene, tdenaro, null);
					    	   
					       if(tcontante != null) 
					          beneDao.modificaDenaroContante((long)idUffOwner, idCodiceUtente, idProcFG, 0L, tbene, null, tcontante);

					} 
				    if(dati.getImmateriale() != null) {
				    	
				    	   TBeneImmat tbeneImmat = beneDao.getTBeneImmaterialeById(bene.getIdentificativoBene().longValue(), idUffOwner);
/*				    	   TBeneImmatId tbeneImmatId = new TBeneImmatId();
				    	   tbeneImmatId.setCodiKIdBeneImm(bene.getIdentificativoBene().longValue());
				    	   tbeneImmatId.setCodiKIdUffOwner(idUffOwner);
				    	   tbeneImmat.setId(tbeneImmatId);
*/				    	   
				    	   tbeneImmat.setDataUltAgg(new Date());				    	   
				  
				    	   ImmaterialeExtType immateriale = dati.getImmateriale();
				    	   ImmaterialeType immateria = immateriale.getImmateriale();
					       if(immateria.getDataRegistrazione() != null) {
					    	  descrBene = descrBene.concat(" - ").concat((immateria.getDataRegistrazione().toGregorianCalendar().getTime()).toString());
					    	  tbeneImmat.setDataRegistrazione(immateria.getDataRegistrazione().toGregorianCalendar().getTime());
					       }    
					       if(immateria.getNumeroRegistrazione() != null && !immateria.getNumeroRegistrazione().isEmpty()) {
					          descrBene = descrBene.concat(" - ").concat(immateria.getNumeroRegistrazione());
					          tbeneImmat.setDescRegistrazione(immateria.getNumeroRegistrazione());
					       }
					       if(immateria.getDataScadenza() != null) {
					    	  tbeneImmat.setDataScadenza(immateria.getDataScadenza().toGregorianCalendar().getTime());
					       }
					       
						   tbene.setDescrBene(bene.getDescBene());
					       
					       beneDao.modificaMobileRegistrato((long)idUffOwner, idCodiceUtente, idProcFG, 0L, tbene, null, tbeneImmat, null, null, null, null, 0L); 
					       
					} 
				    if(dati.getImmobile() != null) {
				    	
				    	   TBeneImmobile tbeneImmobile = beneDao.getTBeneImmobileById(bene.getIdentificativoBene().longValue(), idUffOwner);
/*				    	   TBeneImmobileId tbeneImmobileId = new TBeneImmobileId();
				    	   tbeneImmobileId.setCodiKIdBeneImm(bene.getIdentificativoBene().longValue());
				    	   tbeneImmobileId.setCodiKIdUffOwner(idUffOwner);
				    	   tbeneImmobile.setId(tbeneImmobileId);
*/				    	   
				    	   tbeneImmobile.setDataUltAgg(new Date());				    	   

				    	   ImmobileExtType immobile = dati.getImmobile();
				    	   ImmobileType immobileTipo = immobile.getImmobile();
                           if(immobileTipo.getDatiCatastali() != null) {
                        	  DatiCatastali datiCatastali = immobileTipo.getDatiCatastali();
                        	  if(datiCatastali.getTipoCatasto() != null && !datiCatastali.getTipoCatasto().value().isEmpty()) {
   					              List<TTpTipoCatasto> listaTipoCatasto = commonService.listaTpTipoCatasto();		              
    						      for (TTpTipoCatasto tipoCatasto : listaTipoCatasto) {
    							       if(tipoCatasto.getCodiKIdTipoCat() == datiCatastali.getTipoCatasto().value().charAt(0)) {
    		                        	  tbeneImmobile.setTTpTipoCatastoByCodiFkIdTipoCat(tipoCatasto);
    									  break;
    								    }
    							  }
                              } 
                        	  if(datiCatastali.getIdentificativoComuneCatasto() != null && !datiCatastali.getIdentificativoComuneCatasto().isEmpty()) {
                                  TAnComuni comune = null;
                                  comune = commonService.getComuneByDescBelfiore(datiCatastali.getIdentificativoComuneCatasto());                                
                                  if(comune == null)
                                     comune = commonService.getComuneById(Long.valueOf(datiCatastali.getIdentificativoComuneCatasto()));
                              	  tbeneImmobile.setTAnComuniByCodiFkIdComuneCat(comune);
                        	  }
                        	  if(datiCatastali.getSezioneCatasto() != null && !datiCatastali.getSezioneCatasto().isEmpty()) 
                        		  tbeneImmobile.setDescSezione(datiCatastali.getSezioneCatasto());                                 
                        	  String foglio = "";
                        	  String particella1 = "";
                        	  String subParticella1 = "";
                        	  String particella2 = "";
                        	  String subParticella2 = "";                       	  
                        	  if(datiCatastali.getFoglioCatasto() != null && !datiCatastali.getFoglioCatasto().isEmpty()) {
                        	     foglio = datiCatastali.getFoglioCatasto();
                        	     tbeneImmobile.setDescFoglio(foglio);
                        	     descrBene = descrBene.concat(" - f.").concat(foglio);
                        	  }
                        	  if(datiCatastali.getParticellaCatasto1() != null && !datiCatastali.getParticellaCatasto1().isEmpty()) {              	  
                        	     particella1 = datiCatastali.getParticellaCatasto1();
                        	     tbeneImmobile.setDescParticella1(particella1);
                        	     descrBene = descrBene.concat(" - p.").concat(particella1);
                        	  }
                        	  if(datiCatastali.getParticellaCatasto2() != null && !datiCatastali.getParticellaCatasto2().isEmpty()) {              	  
                         	     particella2 = datiCatastali.getParticellaCatasto2();
                         	     tbeneImmobile.setDescParticella2(particella2);
                         	     if(!particella1.isEmpty())
                         	        descrBene = descrBene.concat("/").concat(particella2);
                        	  }
                        	  if(datiCatastali.getSubparticellaCatasto1() != null && !datiCatastali.getSubparticellaCatasto1().isEmpty()) {               	                         		  
                         	     subParticella1 = datiCatastali.getSubparticellaCatasto1();
                         	     tbeneImmobile.setDescSubParticella1(subParticella1);
                         	     
                         	     descrBene = descrBene.concat(" - s.").concat(subParticella1);
                         	  }
                        	  if(datiCatastali.getSubparticellaCatasto2() != null && !datiCatastali.getSubparticellaCatasto2().isEmpty()) {              	                         		  
                         	     subParticella2 = datiCatastali.getSubparticellaCatasto2();
                         	     tbeneImmobile.setDescSubParticella2(subParticella2);
                          	     if(!subParticella1.isEmpty())
                           	        descrBene = descrBene.concat("/").concat(subParticella2);
                         	  }
                        	  if(datiCatastali.getIdentificativoCategoriaCatastale() != null && !datiCatastali.getIdentificativoCategoriaCatastale().value().isEmpty()) {
                        		  TTpCatCatasto categoriaCatasto = amministrazioneService.getTpCategoriaCatastale(Integer.parseInt(datiCatastali.getIdentificativoCategoriaCatastale().value()));
						    	  tbeneImmobile.setTTpCatCatasto(categoriaCatasto);
                        	  }
                        	  if(datiCatastali.getImportoRenditaCatastale() != null)
                        		 tbeneImmobile.setImpoRenditaCat(datiCatastali.getImportoRenditaCatastale()); 
                        	  if(datiCatastali.getImportoRenditaDominicale() != null)
                        		 tbeneImmobile.setImpoRenditaDom(datiCatastali.getImportoRenditaDominicale()); 
                        	  
                           }
				    	   if(!luogo.isEmpty())                           
					          descrBene = descrBene.concat(" - ").concat(luogo);
					       if(immobileTipo.getEdificio() != null && !immobileTipo.getEdificio().isEmpty()) 
					    	  tbeneImmobile.setDescEdificio(immobileTipo.getEdificio());
					       if(immobileTipo.getPiano() != null && !immobileTipo.getPiano().isEmpty()) 
					    	  tbeneImmobile.setDescPiano(immobileTipo.getPiano()); 
						   if(immobileTipo.getScala() != null && !immobileTipo.getScala().isEmpty()) 
							  tbeneImmobile.setDescScala(immobileTipo.getScala());  
						   if(immobileTipo.getInterno() != null && !immobileTipo.getInterno().isEmpty()) 
							  tbeneImmobile.setDescInterno(immobileTipo.getInterno()); 
						   if(immobileTipo.getVolumeInt() != null)        
							  tbeneImmobile.setNumeMcInt(immobileTipo.getVolumeInt().shortValue());   					    	      
						   if(immobileTipo.getVolumeDec() != null)        
							  tbeneImmobile.setNumeMcDec(immobileTipo.getVolumeDec().byteValue());   					    	      					             
						   if(immobileTipo.getVaniInt() != null)        
							  tbeneImmobile.setNumeVaniInt(immobileTipo.getVaniInt().shortValue());   
						   if(immobileTipo.getVaniDec() != null)        
							  tbeneImmobile.setNumeVaniDec(immobileTipo.getVaniDec().shortValue());   						   
					       if(immobileTipo.getLotto() != null && !immobileTipo.getLotto().isEmpty()) 
					    	  tbeneImmobile.setDescLotto(immobileTipo.getLotto());
					       if(immobileTipo.getSuperficieInt() != null) 
					    	  tbeneImmobile.setNumeSupMqInt(immobileTipo.getSuperficieInt().shortValue());
					       if(immobileTipo.getSuperficieDec() != null) 
					    	  tbeneImmobile.setNumeSupMqDec(immobileTipo.getSuperficieDec().byteValue());
					       if(immobileTipo.getEttari() != null) 
					    	  tbeneImmobile.setNumeSupEttari(immobileTipo.getEttari().intValue());
					       if(immobileTipo.getAre() != null) 
					    	  tbeneImmobile.setNumeSupAre(immobileTipo.getAre().intValue());
					       if(immobileTipo.getCentiare() != null) 
					    	  tbeneImmobile.setNumeSupCentiare(immobileTipo.getCentiare().intValue());			       
					       if(immobileTipo.getNumeroDomandaAccatastamento() != null && !immobileTipo.getNumeroDomandaAccatastamento().isEmpty())
					    	  tbeneImmobile.setDescSchedaAccatast(immobileTipo.getNumeroDomandaAccatastamento());
					       if(immobileTipo.getDataDomandaAccatastamento() != null)
					    	  tbeneImmobile.setDataDomandaAccatast(immobileTipo.getDataDomandaAccatastamento().toGregorianCalendar().getTime());
					       if(immobileTipo.getAppartenenzaaFabbricatoConfiscato() != null && !immobileTipo.getAppartenenzaaFabbricatoConfiscato().isEmpty())
					    	  tbeneImmobile.setFlgAppFabConf(immobileTipo.getAppartenenzaaFabbricatoConfiscato().charAt(0));
					       if(immobileTipo.getTipoFondo() != null) {
				              List<TTpTipoFondo> listaTipoFondo = commonService.listaTpTipoFondo();		              
 						      for (TTpTipoFondo tipoFondo : listaTipoFondo) {
 							       if(tipoFondo.getCodiKIdFondo() == immobileTipo.getTipoFondo().value().charAt(0)) {
 		                        	  tbeneImmobile.setTTpTipoFondo(tipoFondo);
 									  break;
 								   }
 							  }					    	   
 						     /*
					       } else {
					    	   
					    	   TTpTipoFondo tipoFondo = new TTpTipoFondo();
					           Character tfondo = '\u0000';
					           tipoFondo.setCodiKIdFondo(tfondo);
					    	   tbeneImmobile.setTTpTipoFondo(tipoFondo);
					    	   */
					       }

						   tbene.setDescrBene(bene.getDescBene());

                           beneDao.modificaImmobile((long)idUffOwner, idCodiceUtente, idProcFG, 0L, tbene, tbeneImmobile, 0L, 0L, null,0L); 					       
					    	  				      					       
					}
				    if(dati.getVeicolo() != null) {
				    	
                       TVeicolo tveicolo = beneDao.getTVeicoloById(bene.getIdentificativoBene().longValue(), idUffOwner);
/*                     TVeicoloId tveicoloId = new TVeicoloId();
                       tveicoloId.setCodiKIdVeicolo(bene.getIdentificativoBene().longValue());
                       tveicoloId.setCodiKIdUffOwner(idUffOwner);
                       tveicolo.setId(tveicoloId);
*/
                       tveicolo.setDataUltAgg(new Date());				    	   
                            
			    	   VeicoloExtType veicolo = dati.getVeicolo();
				       VeicoloType veicoloTipo = veicolo.getVeicolo();					    	    
					   if(veicoloTipo.getDescrizioneModello() != null && !veicoloTipo.getDescrizioneModello().isEmpty()) {
					      descrBene = descrBene.concat(" - ").concat(veicoloTipo.getDescrizioneModello());
					      tveicolo.setDescModello(veicoloTipo.getDescrizioneModello());
					   }
					   if(veicoloTipo.getNumeroTarga() != null && !veicoloTipo.getNumeroTarga().isEmpty()) {
						   descrBene = descrBene.concat(" - TARGA: ").concat(veicoloTipo.getNumeroTarga());
						   tveicolo.setDescTarga(veicoloTipo.getNumeroTarga());
					   }   

					  if(veicoloTipo.getNumeroRegistro() != null && !veicoloTipo.getNumeroRegistro().isEmpty()) 
					     tveicolo.setDescReg(veicoloTipo.getNumeroRegistro());
					  if(veicoloTipo.getDataRegistrazione() != null)
					     tveicolo.setDataReg(veicoloTipo.getDataRegistrazione().toGregorianCalendar().getTime()); 
					  if(veicoloTipo.getNumeroIdentificativo() != null && !veicoloTipo.getNumeroIdentificativo().isEmpty()) 
					  	 tveicolo.setDescIdentif(veicoloTipo.getNumeroIdentificativo()); 
					  if(veicoloTipo.getCodiceImmatricolazione() != null && !veicoloTipo.getCodiceImmatricolazione().isEmpty()) 
					     tveicolo.setDescImmatricolaz(veicoloTipo.getCodiceImmatricolazione()); 
					  if(veicoloTipo.getNumeroTelaio() != null && !veicoloTipo.getNumeroTelaio().isEmpty()) {
						 if(veicoloTipo.getNumeroTarga() == null || veicoloTipo.getNumeroTarga().isEmpty()) {
						    descrBene = descrBene.concat(" - TELAIO: ").concat(veicoloTipo.getNumeroTelaio());
   					        tveicolo.setDescTelaio(veicoloTipo.getNumeroTelaio());
						 }    
					  }
   					  if(veicoloTipo.getDescrizioneColore() != null && !veicoloTipo.getDescrizioneColore().isEmpty()) 
					     tveicolo.setDescColore(veicoloTipo.getDescrizioneColore()); 

					   tbene.setDescrBene(bene.getDescBene());

				       beneDao.modificaMobileRegistrato((long)idUffOwner, idCodiceUtente, idProcFG, 0L, tbene, tveicolo, null, null, null, null, null, 0L); 
					  					  
				   }
    			    				    
				   AttoForm attoForm = new  AttoForm();
				   attoForm.setCodAtto(idAtto);
				   attoForm.setIdProcedimento(idProcFG);
				   attoForm.setDataDeposito(null);
                   beneDao.inserisciRelAmministratoreBeneAtto(idUffOwner, idAmm, attoForm, tbene.getId().getCodiKIdBene(), idCodiceUtente, "ToolAmministratori" );		           
				     
	   			   listaBeni.add(tbene);
				    
   				 } catch (Exception e) {
   					 e.printStackTrace();
					logger.error("Errore x Tipologia: "+bene.getTipologia().value()+", Categoria: "+bene.getCategoria().value()+", SottoCategoria: "+bene.getSottoCategoria().value()+" : " + e);					
				 }				    
				  
			   } //fine if identificativo = true   			  
			   
			} // fine ciclo 
			 
			return listaBeni;
			
		} catch (Exception e) {
			logger.error("Errore " + e);
			return null;
		}
		
	}
	
	public List<Map> getListaDepositoByIdProcFG(long idProcedimento, int idUffOwner, char flag) {
		List<Map> listaIdDepositi = flussiDao.getListaDepositoByIdProc(idProcedimento,idUffOwner,flag);
		return listaIdDepositi;
	}	

	
	public List<BeneForm> getListaNuoviBeniByIdProcFG(long idProcedimento, int idUffOwner, char flag) {
		ArrayList<BeneForm> listaBeni = new ArrayList<BeneForm>();
		List<Map> listaIdDepositi = getListaDepositoByIdProcFG(idProcedimento,idUffOwner,flag);
		if(listaIdDepositi != null && listaIdDepositi.size() > 0) {			
		   for (int i=0; i < listaIdDepositi.size(); i++) {	
			   long idDeposito = ((BigDecimal)listaIdDepositi.get(i).get("ID_DEPOSITO_AMM")).longValue();
			   //long idProc = ((BigDecimal)listaIdDepositi.get(i).get("ID_PROC")).longValue();
			   //long idAtto = ((BigDecimal)listaIdDepositi.get(i).get("ID_ATTO")).longValue();

			   ArrayList<BeneForm> lista = leggiXMLFileDeposito(null,idDeposito);
			   if (lista!=null) {
				   listaBeni.addAll(lista);
			   }
		   }     
		}
		return listaBeni;
	}	

	
	
	public Blob getXMLFileDeposito(long idDeposito) {

	      Blob blob = flussiDao.getXMLFileDeposito(idDeposito);	
	      
  	  	  return blob;
  	}	
      
	public void aggiornaXML(BeneForm beneForm,long idBeneInserito) {

	        long idDeposito = beneForm.getIdDeposito();
	        
		    Blob blobInput = getXMLFileDeposito(idDeposito);	
	        Blob blobOutput = null;
	        
			String tipo = "";
			String cat = "";
			String sottoCat = "";
			String decrizioneBene = "";

			if(beneForm.getTipologia() != null && beneForm.getTipologia().getDescrizione() != null) tipo = beneForm.getTipologia().getDescrizione();
		    if(beneForm.getCategoria() != null && beneForm.getCategoria().getDescrizione() != null) cat = beneForm.getCategoria().getDescrizione();
		    if(beneForm.getSottocategoria() != null && beneForm.getSottocategoria().getDescrizione() != null) sottoCat = beneForm.getSottocategoria().getDescrizione();
		    if(beneForm.getDescrizioneBene() != null && !beneForm.getDescrizioneBene().isEmpty()) decrizioneBene = beneForm.getDescrizioneBene();
		
			try {

				ByteArrayOutputStream bos = new ByteArrayOutputStream();	

				InputStream inStream = blobInput.getBinaryStream(); 
				InputStreamReader inStreamReader = new InputStreamReader(inStream); 
				Reader reader = new BufferedReader(inStreamReader); 
	 
				JAXBContext jaxbContext = JAXBContext.newInstance(RicezioneRelazione.class);
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				RicezioneRelazione relazione = (RicezioneRelazione) unmarshaller.unmarshal(reader);

	            List<BeneExtType> beni = relazione.getBene();
				for (BeneExtType bene :beni) {
					/*
					String tipologia = "";
					String categoria = "";
					String sottocategoria = "";
					
					if(bene.getTipologia() != null)
					   tipologia = bene.getTipologia().value();
					if(bene.getCategoria() != null)
					   categoria = bene.getCategoria().value();
					if(bene.getSottoCategoria() != null)
						sottocategoria = bene.getSottoCategoria().value();
				   
					if(tipologia.equalsIgnoreCase(tipo) && categoria.equalsIgnoreCase(cat) && sottocategoria.equalsIgnoreCase(sottoCat)) {
						*/
						String descrBene = bene.getDescBene(); //getBeneDescr(bene);
						if(decrizioneBene.equalsIgnoreCase(descrBene)) {
						   if(bene.getIdentificativoBene() == null)	{
					          bene.setIdentificativoBene(BigInteger.valueOf(idBeneInserito));
					          break;
						   }    
						}
					//}
				}
			
				Marshaller marshallerObj = jaxbContext.createMarshaller();
				marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				marshallerObj.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");			
				marshallerObj.marshal(relazione, bos);
				byte[] byteArray = bos.toByteArray();
				
				blobOutput = Hibernate.createBlob(byteArray);

			} catch (JAXBException e) {
				logger.error("ToolAmministratori - Errore nell'aggiornamento del file Blob del Deposito: "+idDeposito);
			} catch (Exception e) {
				logger.error("ToolAmministratori - Errore nell'aggiornamento del file Blob del Deposito: "+idDeposito);
			}
	      
			if(blobOutput != null) {
			   TDepositiAmm depositoAmm = flussiDao.getDettaglioDepositoAmm(idDeposito, 'S');
			   depositoAmm.setXmlToolAmm(blobOutput);
  			   flussiDao.updateDepositoAmm(depositoAmm);
			}	

	}
	
	public String getBeneDescr(BeneExtType bene) {
		return bene.getDescBene();
	}
	
	public TDepositiAmm getDepositoAmmByIdAtto(long codAtto, int idUffOwner){
	      return flussiDao.getDepositoAmmByIdAtto(codAtto, idUffOwner);
   }

	public List processFlussoNRGCassazione(FlussiForm form, String codUtente,long idUffOwn) {

		return flussiDao.processFlussoNRGCassazione(form.getIdFlusso(),	codUtente, idUffOwn);
	}
	
}
