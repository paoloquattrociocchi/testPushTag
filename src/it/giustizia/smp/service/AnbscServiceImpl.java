package it.giustizia.smp.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.bind.JAXBException;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.xml.sax.SAXException;

import it.giustizia.smp.dao.AnbscDao;
import it.giustizia.smp.form.MagistratoForm;
import it.giustizia.smp.model.TAmministratori;
import it.giustizia.smp.model.TAnOperFinanz;
import it.giustizia.smp.model.TAnbscMsg;
import it.giustizia.smp.model.TAnimali;
import it.giustizia.smp.model.TAzienda;
import it.giustizia.smp.model.TBene;
import it.giustizia.smp.model.TBeneFin;
import it.giustizia.smp.model.TBeneImmat;
import it.giustizia.smp.model.TBeneImmobile;
import it.giustizia.smp.model.TCollezioni;
import it.giustizia.smp.model.TContante;
import it.giustizia.smp.model.TDecreto;
import it.giustizia.smp.model.TDefPatr;
import it.giustizia.smp.model.TDenaro;
import it.giustizia.smp.model.TPersonaFisica;
import it.giustizia.smp.model.TPersonaGiur;
import it.giustizia.smp.model.TProcedimento;
import it.giustizia.smp.model.TRelAttoMisPatr;
import it.giustizia.smp.model.TRelBeneAtto;
import it.giustizia.smp.model.TRelBeneProcDisp;
import it.giustizia.smp.model.TRelImmProcVinc;
import it.giustizia.smp.model.TRelMagProc;
import it.giustizia.smp.model.TRelProcBene;
import it.giustizia.smp.model.TRelProcBeneAttoAmm;
import it.giustizia.smp.model.TRelProcProc;
import it.giustizia.smp.model.TRelProcSogint;
import it.giustizia.smp.model.TRelUdienzaStatoUd;
import it.giustizia.smp.model.TUdienza;
import it.giustizia.smp.model.TVeicolo;
import it.giustizia.smp.service.webservices.AnbscWsService;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.DateUtils;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.webservice.client.anbsc.AltriOggetti;
import it.giustizia.smp.webservice.client.anbsc.Amministratore;
import it.giustizia.smp.webservice.client.anbsc.Amministrazione;
import it.giustizia.smp.webservice.client.anbsc.Animali;
import it.giustizia.smp.webservice.client.anbsc.Azienda;
import it.giustizia.smp.webservice.client.anbsc.Bene;
import it.giustizia.smp.webservice.client.anbsc.Bene.DatiSpecificiBene;
import it.giustizia.smp.webservice.client.anbsc.Collezioni;
import it.giustizia.smp.webservice.client.anbsc.ConfiscaDefinitivaRequest;
import it.giustizia.smp.webservice.client.anbsc.ConfiscaDefinitivaResponse;
import it.giustizia.smp.webservice.client.anbsc.ConfiscaPrimoGradoRequest;
import it.giustizia.smp.webservice.client.anbsc.ConfiscaPrimoGradoResponse;
import it.giustizia.smp.webservice.client.anbsc.ConfiscaSecondoGradoRequest;
import it.giustizia.smp.webservice.client.anbsc.ConfiscaSecondoGradoResponse;
import it.giustizia.smp.webservice.client.anbsc.Denaro;
import it.giustizia.smp.webservice.client.anbsc.DenaroContante;
import it.giustizia.smp.webservice.client.anbsc.Esito;
import it.giustizia.smp.webservice.client.anbsc.Finanziario;
import it.giustizia.smp.webservice.client.anbsc.Immateriale;
import it.giustizia.smp.webservice.client.anbsc.Immobile;
import it.giustizia.smp.webservice.client.anbsc.Immobile.DatiCatastali;
import it.giustizia.smp.webservice.client.anbsc.Misura;
import it.giustizia.smp.webservice.client.anbsc.NuovaUdienza;
import it.giustizia.smp.webservice.client.anbsc.Procedimenti;
import it.giustizia.smp.webservice.client.anbsc.Procedimento;
import it.giustizia.smp.webservice.client.anbsc.ProvvedimentiOld;
import it.giustizia.smp.webservice.client.anbsc.Provvedimento;
import it.giustizia.smp.webservice.client.anbsc.RevocaConfiscaRequest;
import it.giustizia.smp.webservice.client.anbsc.RevocaConfiscaResponse;
import it.giustizia.smp.webservice.client.anbsc.RevocaSequestroRequest;
import it.giustizia.smp.webservice.client.anbsc.RevocaSequestroResponse;
import it.giustizia.smp.webservice.client.anbsc.RiunioneRequest;
import it.giustizia.smp.webservice.client.anbsc.RiunioneResponse;
import it.giustizia.smp.webservice.client.anbsc.SequestroRequest;
import it.giustizia.smp.webservice.client.anbsc.SequestroResponse;
import it.giustizia.smp.webservice.client.anbsc.Soggetto;
import it.giustizia.smp.webservice.client.anbsc.SoggettoPersonaFisica;
import it.giustizia.smp.webservice.client.anbsc.SoggettoPersonaFisica.LuogoNascita;
import it.giustizia.smp.webservice.client.anbsc.SoggettoPersonaGiuridica;
import it.giustizia.smp.webservice.client.anbsc.SoggettoPersonaGiuridica.LuogoSedeLegale;
import it.giustizia.smp.webservice.client.anbsc.StralcioRequest;
import it.giustizia.smp.webservice.client.anbsc.StralcioResponse;
import it.giustizia.smp.webservice.client.anbsc.Ubicazione;
import it.giustizia.smp.webservice.client.anbsc.Ubicazione.Estero;
import it.giustizia.smp.webservice.client.anbsc.Ubicazione.Italia;
import it.giustizia.smp.webservice.client.anbsc.VecchiaUdienza;
import it.giustizia.smp.webservice.client.anbsc.Veicolo;
import it.giustizia.smp.webservice.client.anbsc.Veicolo.Identificativo;
import it.giustizia.smp.webservice.utility.JAXBUtil;
import it.giustizia.smp.webservice.utility.MockResponseAnbscUtil;

//@Transactional(rollbackFor = Exception.class)
public class AnbscServiceImpl implements AnbscService {

	@Autowired
	private Mapper mapperBean;
	
	@Autowired
    private AnbscWsService anbscServiceWS;
	
	@Autowired
	private ReloadableResourceBundleMessageSource configParameterSource;
	
	private AnbscDao anbscDao;
	
	public AnbscDao getAnbscDao() {
		return anbscDao;
	}
	public void setAnbscDao(AnbscDao anbscDao) {
		this.anbscDao = anbscDao;
	}

	
	public long insertMsgDatabase(Object msg, long idProcedimento,long idAtto, long idTipoAnbscMsg, String descUtente)
	{
		byte[] xml = JAXBUtil.printXML(msg);
		return anbscDao.insertMsg(idProcedimento, idAtto, idTipoAnbscMsg, Utility.serializeInBlob(xml), descUtente).longValue();		
	}
	
	public void updateRispostaMsgDatabase(long idMsg, Esito responseAnbsc, Object xmlMsgOut)
	{
		String descRisposta = "";
		if(responseAnbsc != null)
		{
			char esito;
			if(responseAnbsc.isEsito()) {
				esito = 'T';
				descRisposta = "Operazione correttamente eseguita";
			}				
			else {
				esito = 'F';
				descRisposta = "";
				if(responseAnbsc.getErrordescription() != null)
				{
					descRisposta += responseAnbsc.getErrordescription();
					if(responseAnbsc.getErrordetails() != null)
					{
						List<String> errorDescription = responseAnbsc.getErrordetails().getItem();
						Iterator<String> iter = errorDescription.iterator();
						while (iter.hasNext()) {
							descRisposta += "|" + iter.next();
						}
					}
				}
			}
				
			byte[] xml = JAXBUtil.printXML(xmlMsgOut);
			anbscDao.updateRispostaMsgDatabase(idMsg, esito, descRisposta, Utility.serializeInBlob(xml));
		}
	}
	
	public void updateRispostaMsgDatabase(long idMsg, char esito, String errore) {
		anbscDao.updateRispostaMsgDatabase(idMsg, esito, errore, null);		
	}
	
	
	/************************************************ SEQUESTRO WS1 ********************************************/
	
	public SequestroRequest createSequestroRequest(long idProcedimento, long idAtto, Integer idUfficio, String tipoSequestro) {
		SequestroRequest request = new SequestroRequest();
		
		if(anbscDao.getTipoUfficio(idUfficio) == 19)
			request.setProcedimento(getProcedimento(idProcedimento, idUfficio));
		else
			request.setProcedimento(getProcedimentoIIGrado(idProcedimento, idUfficio));		
		
		request.setProvvedimento(getProvvedimento(1, idProcedimento, idAtto, idUfficio, false, false));
		List<Bene> beniList = getBeni(idProcedimento, idAtto);
		request.setBene(beniList);
		request.setSoggetto(getSoggetti(idUfficio, idProcedimento,idAtto,beniList, null));
		request.setMisura(getMisure(idProcedimento, idAtto, beniList, idUfficio, null));
		request.setAmministrazione(getAmministrazioni(idProcedimento, idAtto, idUfficio,false));
		if(request.getAmministrazione() != null)
			request.setAmministratore(getAmministratori(request.getAmministrazione()));
//		request.setAmministratore(getAmministratori(idProcedimento, idAtto, idUfficio));
		request.setNuovaUdienza(getNuovaUdienza(idProcedimento, idAtto, idUfficio));
		request.setVecchiaUdienza(getVecchiaUdienza(idAtto, idUfficio));
		return request;
	}
	
	public boolean verificaInvioSequestroAnbsc(long idProcedimento, long idAtto){
		
		boolean result = anbscDao.verificaInvioSequestroAnbsc(idProcedimento, idAtto);
		return result;
	}
	
	public SequestroResponse inviaSequestro(SequestroRequest request)
	{
		SequestroResponse response = null;
		String mockResponse = configParameterSource.getMessage("MOCK_RESPONSE_SERVIZIO_ANBSC", null, null);
		if(mockResponse != null && mockResponse.equals("S")) {
			response = MockResponseAnbscUtil.getMockSequestroResponse();
		} else {
			response = anbscServiceWS.Sequestro(request);
		}
		return response;
	}
	
	/************************************************ DECRETO CONVALIDA SEQUESTRO *************************************/
	public SequestroRequest createDecretoConvalidaRequest(long idProcedimento, long idAttoOr, long idAttoFi, Integer idUfficio, String tipoSequestro) {
		SequestroRequest request = new SequestroRequest();
		int tipoProvvedimento = 1;
		if(tipoSequestro.equals(Constants.DECRETO_CONVALIDA_SEQUESTRO_URGENTE))
			tipoProvvedimento = 13;
		else if(tipoSequestro.equals(Constants.DECRETO_CONVALIDA_SEQUESTRO_ANTICIPATO))
			tipoProvvedimento = 14;
				
		if(anbscDao.getTipoUfficio(idUfficio) == 19)
			request.setProcedimento(getProcedimento(idProcedimento, idUfficio));
		else
			request.setProcedimento(getProcedimentoIIGrado(idProcedimento, idUfficio));		
		
		request.setProvvedimento(getProvvedimento(tipoProvvedimento, idProcedimento, idAttoOr, idUfficio, false, false));
		List<Bene> beniList = getBeniDecretoConvalida(idProcedimento, idAttoFi);
		request.setBene(beniList);
		request.setSoggetto(getSoggetti(idUfficio, idProcedimento,idAttoFi,beniList, null));
		request.setMisura(getMisure(idProcedimento, idAttoFi, beniList, idUfficio, null));
		request.setAmministrazione(getAmministrazioni(idProcedimento, idAttoOr, idUfficio,false));
		if(request.getAmministrazione() != null)
			request.setAmministratore(getAmministratori(request.getAmministrazione()));
		request.setNuovaUdienza(getNuovaUdienza(idProcedimento, idAttoFi, idUfficio));
		request.setVecchiaUdienza(getVecchiaUdienza(idAttoFi, idUfficio));
		return request;
	}
	
	public boolean verificaInvioDecretoConvalidaAnbsc(long idProcedimento, long idAtto){
		
		boolean result = anbscDao.verificaInvioDecretoConvalidaAnbsc(idProcedimento, idAtto);
		return result;
	}
	
	/************************************************ REVOCA SEQUESTRO WS2 ********************************************/
	public RevocaSequestroRequest createRevocaSequestroRequest (long idProcedimento, long idAtto, Integer idUfficio)
	{
		RevocaSequestroRequest request = new RevocaSequestroRequest();
		
		if(anbscDao.getTipoUfficio(idUfficio) == 19)
			request.setProcedimento(getProcedimento(idProcedimento, idUfficio));
		else
			request.setProcedimento(getProcedimentoIIGrado(idProcedimento, idUfficio));		
		
		request.setProvvedimento(getProvvedimento(5, idProcedimento, idAtto, idUfficio, false, false));
		List<Bene> beniList = getBeni(idProcedimento, idAtto);
		request.setBene(beniList);
		return request;
	}
	
	public RevocaSequestroResponse inviaRevocaSequestro(RevocaSequestroRequest request)
	{
		RevocaSequestroResponse response = null;
		String mockResponse = configParameterSource.getMessage("MOCK_RESPONSE_SERVIZIO_ANBSC", null, null);
		if(mockResponse != null && mockResponse.equals("S")) {
			response = MockResponseAnbscUtil.getMockRevocaSequestroResponse();
		} else {
			response = anbscServiceWS.RevocaSequestro(request);
		}
		return response;
	}
	
	/************************************************ CONFISCA I GRADO WS3 ********************************************/
	public ConfiscaPrimoGradoRequest createConfiscaPrimoGradoRequest (long idProcedimento, long idAtto, Integer idUfficio)
	{
		ConfiscaPrimoGradoRequest request = new ConfiscaPrimoGradoRequest();
		
		if(anbscDao.getTipoUfficio(idUfficio) == 19)
			request.setProcedimento(getProcedimento(idProcedimento, idUfficio));
		else
			request.setProcedimento(getProcedimentoIIGrado(idProcedimento, idUfficio));
		
		request.setProvvedimento(getProvvedimento(2, idProcedimento, idAtto, idUfficio, false, false));
		List<Bene> beniList = getBeni(idProcedimento, idAtto);
		request.setBene(beniList);
		request.setSoggetto(getSoggetti(idUfficio, idProcedimento,idAtto,beniList, null));
		request.setMisura(getMisure(idProcedimento, idAtto, beniList, idUfficio, null));
		request.setAmministrazione(getAmministrazioni(idProcedimento, idAtto, idUfficio, true));
//		request.setAmministratore(getAmministratori(idProcedimento, idAtto, idUfficio));
		if(request.getAmministrazione() != null)
			request.setAmministratore(getAmministratori(request.getAmministrazione()));
		request.setNuovaUdienza(getNuovaUdienza(idProcedimento, idAtto, idUfficio));
		request.setVecchiaUdienza(getVecchiaUdienza(idAtto, idUfficio));
		return request;
	}
	
	public ConfiscaPrimoGradoResponse inviaConfiscaPrimoGrado(ConfiscaPrimoGradoRequest request)
	{
		ConfiscaPrimoGradoResponse response = null;
		String mockResponse = configParameterSource.getMessage("MOCK_RESPONSE_SERVIZIO_ANBSC", null, null);
		if(mockResponse != null && mockResponse.equals("S")) {
			response = MockResponseAnbscUtil.getMockConfiscaPrimoGradoResponse();
		} else {
			response = anbscServiceWS.ConfiscaPrimoGrado(request);
		}
		return response;
	}
	
	/************************************************ CONFISCA II GRADO WS4 ********************************************/
	public ConfiscaSecondoGradoRequest createConfiscaSecondoGradoRequest (long idProcedimento, long idAtto, Integer idUfficio)
	{
		ConfiscaSecondoGradoRequest request = new ConfiscaSecondoGradoRequest();
		
		if(anbscDao.getTipoUfficio(idUfficio) == 19)
			request.setProcedimento(getProcedimento(idProcedimento, idUfficio));
		else
			request.setProcedimento(getProcedimentoIIGrado(idProcedimento, idUfficio));		
		
		request.setProvvedimento(getProvvedimento(3, idProcedimento, idAtto, idUfficio, false, false));
		List<Bene> beniList = getBeni(idProcedimento, idAtto);
		request.setBene(beniList);
		request.setSoggetto(getSoggetti(idUfficio, idProcedimento,idAtto,beniList, null));
		request.setMisura(getMisure(idProcedimento, idAtto, beniList, idUfficio, null));
		request.setAmministrazione(getAmministrazioni(idProcedimento, idAtto, idUfficio, true));
//		request.setAmministratore(getAmministratori(idProcedimento, idAtto, idUfficio));
		if(request.getAmministrazione() != null)
			request.setAmministratore(getAmministratori(request.getAmministrazione()));
		request.setNuovaUdienza(getNuovaUdienza(idProcedimento, idAtto, idUfficio));
		request.setVecchiaUdienza(getVecchiaUdienza(idAtto, idUfficio));
		return request;
	}
	
	public ConfiscaSecondoGradoResponse inviaConfiscaSecondoGrado(ConfiscaSecondoGradoRequest request)
	{
		ConfiscaSecondoGradoResponse response = null;
		String mockResponse = configParameterSource.getMessage("MOCK_RESPONSE_SERVIZIO_ANBSC", null, null);
		if(mockResponse != null && mockResponse.equals("S")) {
			response = MockResponseAnbscUtil.getMockConfiscaSecondoGradoResponse();
		} else {
			response = anbscServiceWS.ConfiscaSecondoGrado(request);
		}
		return response;
	}
	
	/************************************************ CONFISCA DEFINITIVA ********************************************/
	public ConfiscaDefinitivaRequest createConfiscaDefinitivaRequest (final long idProcedimento, final long idAtto, final Integer idUfficio, final int tipoProvvedimento, List<TDefPatr> listaMisPatrDaInviareANBSC)
	{
		ConfiscaDefinitivaRequest request = new ConfiscaDefinitivaRequest();

		if(anbscDao.getTipoUfficio(idUfficio) == 19)
			request.setProcedimento(getProcedimento(idProcedimento, idUfficio));
		else
			request.setProcedimento(getProcedimentoIIGrado(idProcedimento, idUfficio));
				
		request.setProvvedimento(getProvvedimento(tipoProvvedimento, idProcedimento, idAtto, idUfficio, true, false));
		request.getProvvedimento().setUfficioEmittente("05809100100");
		List<Bene> beniList = getBeni(idProcedimento, idAtto);
		//La data di definitività e uguale per tutti i record di listaMisPatrDaInviareANBSC , ne prendo una qualsiasi e la setto in data provvedimento
		Date dataDefinitivita = null;
		// devo filtrare i beni con la lista che passo come parametro
		if(listaMisPatrDaInviareANBSC != null && !listaMisPatrDaInviareANBSC.isEmpty()) {
			// includo solamente i beni che passo
			List<Bene> tempBeniList = new ArrayList<Bene>();
			for (Bene recordBene : beniList) {
				for (TDefPatr recordTdefPatr : listaMisPatrDaInviareANBSC) {
					if(recordTdefPatr.getTRelBeneAtto().getId().getCodiKIdBene() == recordBene.getIdentificativoBene().longValue()) {
						tempBeniList.add(recordBene);
						dataDefinitivita = recordTdefPatr.getDataDefinitivita();
						break;
					}
				}
			}
			
			beniList = new ArrayList<Bene>(tempBeniList);
		}		
		
		request.setBene(beniList);
		if(dataDefinitivita != null) 
			request.getProvvedimento().setDataDefinitivita(DateUtils.convert(dataDefinitivita));	
		
		request.setSoggetto(getSoggetti(idUfficio, idProcedimento,idAtto,beniList, listaMisPatrDaInviareANBSC));
		request.setMisura(getMisure(idProcedimento, idAtto, beniList, idUfficio, listaMisPatrDaInviareANBSC));
		request.setAmministrazione(getAmministrazioni(idProcedimento, idAtto, idUfficio, true));
		if(request.getAmministrazione() != null)
			request.setAmministratore(getAmministratori(request.getAmministrazione()));
//		request.setAmministratore(getAmministratori(idProcedimento, idAtto, idUfficio));
		request.setNuovaUdienza(getNuovaUdienza(idProcedimento, idAtto, idUfficio));
		request.setVecchiaUdienza(getVecchiaUdienza(idAtto, idUfficio));
		return request;
	}
	
	
	public ConfiscaDefinitivaResponse inviaConfiscaDefinitiva(ConfiscaDefinitivaRequest request)
	{
		ConfiscaDefinitivaResponse response = null;
		String mockResponse = configParameterSource.getMessage("MOCK_RESPONSE_SERVIZIO_ANBSC", null, null);
		if(mockResponse != null && mockResponse.equals("S")) {
			response = MockResponseAnbscUtil.getMockConfiscaDefinitivaResponse();
		} else {
			response = anbscServiceWS.ConfiscaDefinitiva(request);
		}
		return response;
	}

	/************************************************ REVOCA CONFISCA ********************************************/
	public RevocaConfiscaRequest createRevocaConfiscaRequest (long idProcedimento, long idAtto, Integer idUfficio)
	{
		RevocaConfiscaRequest request = new RevocaConfiscaRequest();
		if(anbscDao.getTipoUfficio(idUfficio) == 19)
			request.setProcedimento(getProcedimento(idProcedimento, idUfficio));
		else
			request.setProcedimento(getProcedimentoIIGrado(idProcedimento, idUfficio));
		request.setProvvedimento(getProvvedimento(6, idProcedimento, idAtto, idUfficio, false, true));
		List<Bene> beniList = getBeni(idProcedimento, idAtto);
		request.setBene(beniList);
		return request;
	}
	
	public RevocaConfiscaResponse inviaRevocaConfisca(RevocaConfiscaRequest request)
	{
		RevocaConfiscaResponse response = null;
		String mockResponse = configParameterSource.getMessage("MOCK_RESPONSE_SERVIZIO_ANBSC", null, null);
		if(mockResponse != null && mockResponse.equals("S")) {
			response = MockResponseAnbscUtil.getMockRevocaConfiscaResponse();
		} else {
			response = anbscServiceWS.RevocaConfisca(request);
		}
		return response;
	}
	
/************************************************ STRALCIO ********************************************/
	
	public StralcioRequest createStralcioRequest(long idProcedimentoOrigine, long idProcedimentoDestinazione, Integer idUfficio) {
		
		StralcioRequest request = new StralcioRequest();
		
		Procedimenti procedimenti = new Procedimenti();
		procedimenti.setIdentificativoProcedimentoOrigine(BigInteger.valueOf(idProcedimentoOrigine));
		procedimenti.setIdentificativoProcedimentoDestinazione(BigInteger.valueOf(idProcedimentoDestinazione));
		request.setProcedimenti(procedimenti);
		
		List<TRelBeneAtto> listTRelBeneAtto = getListaBeneAttoStralcio(idProcedimentoOrigine,idProcedimentoDestinazione,idUfficio);
		
		request.getAmministratore().addAll(getAmministratoriStralcio(listTRelBeneAtto,idProcedimentoOrigine, idUfficio));
		request.getProvvedimentiOld().addAll(getListaProvvedimentiOld(listTRelBeneAtto,idProcedimentoOrigine, idProcedimentoDestinazione, idUfficio));		
		
		if(anbscDao.getTipoUfficio(idUfficio) == 19)
			request.setProcedimento(getProcedimento(idProcedimentoDestinazione, idUfficio));
		else
			request.setProcedimento(getProcedimentoIIGrado(idProcedimentoDestinazione, idUfficio));		
		
		return request;
	}
		
	public StralcioResponse inviaStralcio(StralcioRequest request)
	{
		StralcioResponse response = null;
		String mockResponse = configParameterSource.getMessage("MOCK_RESPONSE_SERVIZIO_ANBSC", null, null);
		if(mockResponse != null && mockResponse.equals("S")) {
			response = MockResponseAnbscUtil.getMockStralcioResponse();
		} else {
			response = anbscServiceWS.Stralcio(request);
		}
		return response;
	}
	
	/************************************************ RIUNIONE ********************************************/
	public RiunioneRequest createRiunioneRequest(long idProcedimentoRiunito, long idProcedimentoRiunente, Integer idUfficio) {
		
		RiunioneRequest request = new RiunioneRequest();
		
		Procedimenti procedimenti = new Procedimenti();
		procedimenti.setIdentificativoProcedimentoOrigine(BigInteger.valueOf(idProcedimentoRiunito));
		procedimenti.setIdentificativoProcedimentoDestinazione(BigInteger.valueOf(idProcedimentoRiunente));
		
		request.setProcedimenti(procedimenti);
		
		return request;
	}
	
	public boolean verificaInvioStralcioRiunione(long idProcedimento){
		boolean result = anbscDao.verificaInvioStralcioRiunione(idProcedimento);
		return result;
	}
	
	public RiunioneResponse inviaRiunione(RiunioneRequest request) {
		RiunioneResponse response = null;
		String mockResponse = configParameterSource.getMessage("MOCK_RESPONSE_SERVIZIO_ANBSC", null, null);
		if(mockResponse != null && mockResponse.equals("S")) {
			response = MockResponseAnbscUtil.getMockRiunioneResponse();
		} else {
			response = anbscServiceWS.Riunione(request);
		}
		return response;
	}
	
	
	/*************************** PROCEDIMENTO *****************************************/
	private Procedimento getProcedimento(long idProcedimento, int uffOwner) {
		Procedimento procedimento = new Procedimento();
		TProcedimento procedimentoModel = anbscDao.getProcedimento(idProcedimento,uffOwner);
		if(procedimentoModel != null)
		{
			procedimento.setIdentificativoProcedimento(BigInteger.valueOf(idProcedimento));
			procedimento.setUfficioCompetente(procedimentoModel.getTAnUfficioByCodiFkIdUffOwner().getDescUffGiudiz());//codugna
			procedimento.setUfficioRiferimento(procedimentoModel.getTAnUfficioByCodiFkIdUffOwner().getDescUffGiudiz());//codugna
			if(procedimentoModel.getDataIscrizione() != null)
				procedimento.setDataIscrizione(DateUtils.convert(procedimentoModel.getDataIscrizione()));
			else
				procedimento.setDataIscrizione(null);
			if(procedimentoModel.getNumeAnnoProc() != null)
				procedimento.setAnnoProcedimento(BigInteger.valueOf(procedimentoModel.getNumeAnnoProc()));
			else
				procedimento.setAnnoProcedimento(null);
			if(procedimentoModel.getNumeProc() != null)
				procedimento.setNumeroProcedimento(BigInteger.valueOf(procedimentoModel.getNumeProc()));
			else
				procedimento.setNumeroProcedimento(null);
			Date dataPropostaSequestro = anbscDao.getDataPropostaSequestro(idProcedimento);
			if(dataPropostaSequestro != null)
				procedimento.setDataPropostaSequestro(DateUtils.convert(dataPropostaSequestro));
			else
				procedimento.setDataPropostaSequestro(null);
			TRelMagProc magistratoModel = anbscDao.getGiudiceDelegato(idProcedimento);
			if(magistratoModel !=null)
			{
				MagistratoForm magistratoForm = new MagistratoForm();
				mapperBean.map(magistratoModel, magistratoForm);
				procedimento.setCognomeGiudiceDelegato(magistratoForm.getCognome());
				procedimento.setNomeGiudiceDelegato(magistratoForm.getNome());
				procedimento.setDataAssegnazioneGiudiceDelegato(DateUtils.convert(magistratoForm.getDataAssegnazioneFascicolo()));
			}
			else
			{
				procedimento.setCognomeGiudiceDelegato(null);
				procedimento.setNomeGiudiceDelegato(null);
				procedimento.setDataAssegnazioneGiudiceDelegato(null);
			}
		}
		return procedimento;
	}
	
	/*************************** PROCEDIMENTO per Confisca di II grado *****************************************/
	private Procedimento getProcedimentoIIGrado(long idProcedimento, int uffOwner) {
		Procedimento procedimento = new Procedimento();
		TRelProcProc relProcProcModel = anbscDao.getProcedimentoConfiscaIIGrado(idProcedimento);
		if(relProcProcModel != null)
		{
			procedimento.setIdentificativoProcedimento(BigInteger.valueOf(idProcedimento));
			procedimento.setUfficioCompetente(anbscDao.getUfficio(uffOwner));//codugna
			procedimento.setUfficioRiferimento(anbscDao.getUfficio(relProcProcModel.getTAnUfficio().getCodiKIdUfficio()));//codugna
			if(relProcProcModel.getDataApeProc2() != null)
				procedimento.setDataIscrizione(DateUtils.convert(relProcProcModel.getDataApeProc2()));
			else
				procedimento.setDataIscrizione(null);
			procedimento.setAnnoProcedimento(BigInteger.valueOf(relProcProcModel.getNumeAnnoProc2()));
			procedimento.setNumeroProcedimento(BigInteger.valueOf(relProcProcModel.getNumeProcedimento2()));
			Date dataPropostaSequestro = anbscDao.getDataPropostaSequestro(relProcProcModel.getId().getCodiKIdProc2());
			if(dataPropostaSequestro != null)
				procedimento.setDataPropostaSequestro(DateUtils.convert(dataPropostaSequestro));
			else
				procedimento.setDataPropostaSequestro(null);
			TRelMagProc magistratoModel = anbscDao.getGiudiceDelegato(idProcedimento);
			if(magistratoModel !=null)
			{
				MagistratoForm magistratoForm = new MagistratoForm();
				mapperBean.map(magistratoModel, magistratoForm);
				procedimento.setCognomeGiudiceDelegato(magistratoForm.getCognome());
				procedimento.setNomeGiudiceDelegato(magistratoForm.getNome());
				procedimento.setDataAssegnazioneGiudiceDelegato(DateUtils.convert(magistratoForm.getDataAssegnazioneFascicolo()));
			}
			else
			{
				procedimento.setCognomeGiudiceDelegato(null);
				procedimento.setNomeGiudiceDelegato(null);
				procedimento.setDataAssegnazioneGiudiceDelegato(null);
			}
		}
		return procedimento;
	}
	
	
	
	
	/**************************************************** PROVVEDIMENTO *****************************************/
	/**
	 * Metodo per creare la struttura del Provvedimento
	 * @param tipo indica la tipologia di servizio da inviare:
	 * 				1: sequestro ordinario
	 * 				2: confisca I grado
	 * 				3: confisca II grado
	 * 				4: confisca definitiva
	 * 				5: revoca sequestro
	 * @param idAtto
	 * @return
	 */
	private Provvedimento getProvvedimento(int tipo, long idProcedimento, long idAtto, Integer idUfficio, boolean cercaDataDefinitivita, boolean uffEmittenteDiverso) {
		Provvedimento provvedimento = new Provvedimento();
		provvedimento.setIdentificativoProvvedimento(BigInteger.valueOf(idAtto));
		provvedimento.setUfficioEmittente(getUfficio(idUfficio));
		TProcedimento procedimentoModel;
		if(uffEmittenteDiverso)
			 procedimentoModel = anbscDao.getProcedimentoEmittente(idProcedimento);
		else
			 procedimentoModel = anbscDao.getProcedimento(idProcedimento, idUfficio);
		if(procedimentoModel != null)
		{
			if(procedimentoModel.getNumeAnnoProc() != null)
				provvedimento.setAnnoProcedimentoEmittente(BigInteger.valueOf(procedimentoModel.getNumeAnnoProc()));
			else
				provvedimento.setAnnoProcedimentoEmittente(null);
			if(procedimentoModel.getNumeProc() != null)
				provvedimento.setNumProcedimentoEmittente(BigInteger.valueOf(procedimentoModel.getNumeProc()));
			else
				provvedimento.setNumProcedimentoEmittente(null);
		}
		provvedimento.setDataDepositoProvv(DateUtils.convert(getDataDepositoProvv(idAtto)));
		TDecreto decreto = getDecreto(idAtto);
		if(decreto != null && decreto.getNumeAnnoAtto() != null)
			provvedimento.setAnnoProvvedimento(BigInteger.valueOf(decreto.getNumeAnnoAtto()));
		else
			provvedimento.setAnnoProvvedimento(null);
		if(decreto != null && decreto.getNumeAtto() != null)
			provvedimento.setNumeroProvvedimento(BigInteger.valueOf(decreto.getNumeAtto()));
		else
			provvedimento.setNumeroProvvedimento(null);
		provvedimento.setTipoProvvedimento(BigInteger.valueOf(tipo));
		if(cercaDataDefinitivita)
			provvedimento.setDataDefinitivita(DateUtils.convert(getDataDefinitivita(idProcedimento, idAtto, idUfficio)));
		else
			provvedimento.setDataDefinitivita(null);

		
		return provvedimento;
	}
	
	/***************************************** BENI ********************************************/
	public List<Bene> getBeni(long idProcedimento, long idAtto) {
		List<Bene> beni = new ArrayList<Bene>();
		List<Long> idBeni = getListaIdBeni(idProcedimento, idAtto);
		Iterator<Long> iter = idBeni.iterator();
		while( iter.hasNext() ) {
			Bene bene = new Bene();
			Long currentIdBene = ((Long)iter.next());
			bene = getBene(idProcedimento,currentIdBene);
			beni.add(bene);
		}
		return beni;
	}

	
	private List<Bene> getBeniDecretoConvalida(long idProcedimento, long idAtto) {
		List<Bene> beni = new ArrayList<Bene>();
		List<Long> idBeni = getListaIdBeniDecretoConvalida(idProcedimento, idAtto);
		Iterator<Long> iter = idBeni.iterator();
		while( iter.hasNext() ) {
			Bene bene = new Bene();
			Long currentIdBene = ((Long)iter.next());
			bene = getBene(idProcedimento,currentIdBene);
			beni.add(bene);
		}
		return beni;
	}
	
	
	private Bene getBene(long idProc, long idBene){
		TBene beneModel = anbscDao.getBene(idBene);
		Bene bene = new Bene();
		bene.setIdentificativoBene(BigInteger.valueOf(idBene));
		bene.setTipologia(anbscDao.getTipologiaBene(idBene));
		bene.setCategoria(anbscDao.getCategoriaBene(idBene));
		bene.setSottocategoria(anbscDao.getSottocategoriaBene(idBene));
		bene.setUbicazione(getUbicazione(beneModel));
		if(beneModel.getFlgGravame() != null)
		{
			if(beneModel.getFlgGravame().equals("S"))
				bene.setGravatoDaPegnoIpoteca("1");
			if(beneModel.getFlgGravame().equals("N"))
				bene.setGravatoDaPegnoIpoteca("0");
		}
		/*************** Dati Specifici del Bene **********************/
		bene.setDatiSpecificiBene(getDatiSpecificiBene(idProc, idBene, beneModel));
		
		return bene;
	}
	
	private Ubicazione getUbicazione(TBene beneModel){
		Ubicazione ubicazione = new Ubicazione();
		if(beneModel.getTAnComuni() != null)
		{
			Italia italia = new Italia();
			italia.setIdentificativoComuneUbicazione(beneModel.getTAnComuni().getDescBelfiore());
			italia.setIdentificativoProvinciaUbicazione(beneModel.getTAnComuni().getTAnProvincia().getCodiKIdIstatProv());
			ubicazione.setItalia(italia);
		}
		else if(beneModel.getTAnStato() != null && !beneModel.getTAnStato().getDescIstatStato().equalsIgnoreCase("1"))
		{
			Estero estero = new Estero();
			estero.setIdentificativoStatoEsteroUbicazione(BigInteger.valueOf(beneModel.getTAnStato().getCodiKIdStato()));//MAC (beneModel.getTAnStato().getDescBelfiore());
			estero.setDescrizioneComuneEstero(beneModel.getTAnStato().getDescStato());
			ubicazione.setEstero(estero);
		}
		return ubicazione;
	}
	
	private DatiSpecificiBene getDatiSpecificiBene(long idProc, long idBene, TBene model){
//		BigInteger tipologiaBene = anbscDao.getTipologiaBene(idBene);
		BigInteger categoriaBene = anbscDao.getCategoriaBene(idBene);
		BigInteger sottocategoriaBene = anbscDao.getSottocategoriaBene(idBene);
		DatiSpecificiBene datiSpecificiBene = new DatiSpecificiBene();
		
		if(categoriaBene != null && categoriaBene.intValue()>=1 && categoriaBene.intValue()<=5)//IMMOBILI
		{
			datiSpecificiBene.setImmobile(getImmobile(model,getProcBene(idProc, idBene),
					getDisponibilita(idProc, idBene),getVincolo(idProc, idBene)));
		}
		else if(categoriaBene != null && categoriaBene.intValue()==6 && (sottocategoriaBene==null || (sottocategoriaBene.intValue()>=36 && sottocategoriaBene.intValue()<=39)))//DENARO
		{
			datiSpecificiBene.setDenaro(getDenaro(model));
		}
		else if(categoriaBene != null && categoriaBene.intValue()==6 && sottocategoriaBene!=null && sottocategoriaBene.intValue()==35)//DENARO CONTANTE
		{
			datiSpecificiBene.setDenaroContante(getContante(model));
		}
		else if(categoriaBene != null && categoriaBene.intValue()==7)//COLLEZIONI
		{
			datiSpecificiBene.setCollezioni(getCollezioni(model));
		}
		else if(categoriaBene != null && categoriaBene.intValue()==8)//ANIMALI
		{
			datiSpecificiBene.setAnimali(getAnimali(model));
		}
		else if(categoriaBene != null && categoriaBene.intValue()==9)//ALTRI OGGETTI
		{
			datiSpecificiBene.setAltriOggetti(getAltriOggetti(model));
		}
		else if(categoriaBene != null && categoriaBene.intValue()==10)//VEICOLO
		{
			datiSpecificiBene.setVeicolo(getVeicoli(idProc,model));
		}
		else if(categoriaBene != null && categoriaBene.intValue()==11)//IMMATERIALI
		{
			datiSpecificiBene.setImmateriale(getImmateriali(model));
		}
		
		// P.Q. 21/01/2022 20211220013 
		else if((categoriaBene != null && categoriaBene.intValue()>=12 && categoriaBene.intValue()<=16 ) || (categoriaBene.intValue()>=31 && categoriaBene.intValue()<=32)) //FINANZIARI
		{
			datiSpecificiBene.setFinanziario(getFinanziario(model));
		}
		else if(categoriaBene != null && categoriaBene.intValue()>=17 && categoriaBene.intValue()<=30)//AZIENDA
		{
			datiSpecificiBene.setAzienda(getAzienda(model,getProcBene(idProc, idBene)));
		}
		return datiSpecificiBene;
	}
	
	private AltriOggetti getAltriOggetti(TBene model){
		AltriOggetti altriOggetti = new AltriOggetti();
		if(model.getDescIndirizzo() != null)
			altriOggetti.setIndirizzo(model.getDescIndirizzo());
		else
			altriOggetti.setIndirizzo(null);
		if(model.getDescCivico() != null)
			altriOggetti.setNumeroCivico(model.getDescCivico());
		else
			altriOggetti.setNumeroCivico(null);
		if(model.getDescrBene() != null)
			altriOggetti.setDescrizione(model.getDescrBene());
		else
			altriOggetti.setDescrizione(null);
		return altriOggetti;
	}
	
	private Animali getAnimali(TBene model){
		Animali animali = new Animali();
		if(model.getDescIndirizzo() != null)
			animali.setIndirizzo(model.getDescIndirizzo());
		if(model.getDescCivico() != null)
			animali.setNumeroCivico(model.getDescCivico());
		TAnimali animaliModel = anbscDao.getAnimali(model.getId().getCodiKIdBene());
		if(animaliModel != null)
		{
			if(animaliModel.getDescCapo() != null)
				animali.setDescrizione(animaliModel.getDescCapo());
			if(animaliModel.getNumeAnimali() != null)
				animali.setNumeroAnimali(BigInteger.valueOf(animaliModel.getNumeAnimali()));
		}
		return animali;
	}
	
	private Collezioni getCollezioni(TBene model){
		Collezioni collezioni = new Collezioni();
		if(model.getDescIndirizzo() != null)
			collezioni.setIndirizzo(model.getDescIndirizzo());
		if(model.getDescCivico() != null)
			collezioni.setNumeroCivico(model.getDescCivico());
		TCollezioni collezioniModel = anbscDao.getCollezioni(model.getId().getCodiKIdBene());
		if(collezioniModel != null)
		{
			if(collezioniModel.getDescCollezione() != null)
				collezioni.setDescrizioneCollezione(collezioniModel.getDescCollezione());
			collezioni.setNumeroPezzi(BigInteger.valueOf(collezioniModel.getNumePezzi()));
		}
		return collezioni;
	}
	
	private Immateriale getImmateriali(TBene model){
		Immateriale immateriale = new Immateriale();
		TBeneImmat immaterialeModel = anbscDao.getImmateriali(model.getId().getCodiKIdBene());
		if(immaterialeModel != null)
		{
			immateriale.setNumeroRegistrazione(immaterialeModel.getDescRegistrazione());
			if(immaterialeModel.getDataRegistrazione() != null)
				immateriale.setDataRegistrazione(DateUtils.convert(immaterialeModel.getDataRegistrazione()));
			if(immaterialeModel.getDataScadenza() != null)
				immateriale.setDataScadenza(DateUtils.convert(immaterialeModel.getDataScadenza()));
		}
		return immateriale;
	}
	
	private Veicolo getVeicoli(long idProc, TBene model){
		Veicolo veicolo = new Veicolo();
		TVeicolo veicoloModel = anbscDao.getVeicoli(model.getId().getCodiKIdBene());
		if(veicoloModel != null)
		{
			veicolo.setNumeroRegistro(veicoloModel.getDescReg());
			if(veicoloModel.getDataReg() != null)
				veicolo.setDataRegistrazione(DateUtils.convert(veicoloModel.getDataReg()));
			if(veicoloModel.getDescIdentif() != null && veicoloModel.getDescIdentif().length() > 0)
				veicolo.setNumeroIdentificativo(new BigInteger(veicoloModel.getDescIdentif()));
			veicolo.setCodiceImmatricolazione(veicoloModel.getDescImmatricolaz());
			veicolo.setDescrizioneModello(veicoloModel.getDescModello());
			TRelBeneProcDisp disponibilita = getDisponibilita(idProc,model.getId().getCodiKIdBene());
			if(disponibilita!=null && disponibilita.getTTpTipoDisp() != null)
				veicolo.setIdentificativoTipoDisponibilita(BigInteger.valueOf(disponibilita.getTTpTipoDisp().getCodiKIdTipoDisp()));
			Identificativo identificativo = new Identificativo();
			identificativo.setNumeroTarga(veicoloModel.getDescTarga());
			identificativo.setNumeroTelaio(veicoloModel.getDescTelaio());
			veicolo.setIdentificativo(identificativo);
			veicolo.setDescrizioneColore(veicoloModel.getDescColore());
		}
		return veicolo;
	}
	
	private Azienda getAzienda(TBene model, TRelProcBene procBene){
		Azienda azienda = new Azienda();
		TAzienda aziendaModel = anbscDao.getAzienda(model.getId().getCodiKIdBene());
		if(aziendaModel != null)
		{
			azienda.setRagioneSociale(aziendaModel.getDescRagioneSoc());
			if(aziendaModel.getDescPIva() != null)
			{
				if(aziendaModel.getDescPIva().length() > 11)
					azienda.setPartitaIva(riduciPartitaIva(aziendaModel.getDescPIva()));
				else
					azienda.setPartitaIva(aziendaModel.getDescPIva());
			}
			azienda.setNumerocciaa(aziendaModel.getDescCciaa());
			azienda.setCodiceFiscale(aziendaModel.getDescCodFisc());
			azienda.setIndirizzo(model.getDescIndirizzo());
			azienda.setNumeroCivico(model.getDescCivico());
			if(aziendaModel.getImpoCapitaleSoc() != null)
				azienda.setCapitaleSociale(aziendaModel.getImpoCapitaleSoc());
			if(procBene!=null && procBene.getTTpStatoConserv()!=null)
				azienda.setIdentificativoStatoManutenzione(BigInteger.valueOf(procBene.getTTpStatoConserv().getCodiKIdStatoConserv()));
			azienda.setDescrizioneRappresentanteSocieta(aziendaModel.getDescRappresSoc());
			azienda.setDescrizioneAmministratore(aziendaModel.getDescAmministratore());
			if(aziendaModel.getNumeDipendenti() != null)
				azienda.setNumeroDipendenti(BigInteger.valueOf(aziendaModel.getNumeDipendenti()));
			if(aziendaModel.getTTpSettoreAtt() != null)
				azienda.setIdentificativoSettoreAttivita(BigInteger.valueOf(aziendaModel.getTTpSettoreAtt().getCodiKIdSettoreAtt()));
		}
		return azienda;
	}
	
	private Denaro getDenaro(TBene model){
		Denaro denaro = new Denaro();
		TDenaro denaroModel = anbscDao.getDenaro(model.getId().getCodiKIdBene());
		if(denaroModel != null)
		{
			if(denaroModel.getDescEnteDepos() != null)
				denaro.setDescrizioneEnteDepositario(denaroModel.getDescEnteDepos());
			else
				denaro.setDescrizioneEnteDepositario(Constants.NON_DISPONIBILE);
			denaro.setIndirizzo(model.getDescIndirizzo());
			denaro.setNumeroCivico(model.getDescCivico());
			denaro.setDescrizioneComuneEsteroEnteDepositario(model.getDescComuneEstero());
			TAnOperFinanz operatoreFinanziario = denaroModel.getTAnOperFinanz();
			if(denaroModel.getDescFiliale() != null)
				denaro.setDescrizioneFiliale(denaroModel.getDescFiliale());
			else
			{
				if(operatoreFinanziario.getId().getCodiKIdAbi()!=null && operatoreFinanziario.getId().getCodiKIdCab()!=null)
				{
					TAnOperFinanz operatoreFinanziarioModel = anbscDao.getOperatoreFinanziario(operatoreFinanziario.getId().getCodiKIdAbi(), operatoreFinanziario.getId().getCodiKIdCab());
					denaro.setDescrizioneFiliale(getDescFiliale(operatoreFinanziarioModel));
					denaro.setAbi(operatoreFinanziario.getId().getCodiKIdAbi()); 
					denaro.setCab(operatoreFinanziario.getId().getCodiKIdCab());
				}
			}
			denaro.setDescrizioneDirettore(denaroModel.getDescDirettore());
			if(denaroModel.getDescIdentEnte() != null)
				denaro.setNumeroEnte(denaroModel.getDescIdentEnte());
			else
				denaro.setNumeroEnte(Constants.NON_DISPONIBILE);
//			denaro.setDescrizioneIntestatario(denaroModel.get)//da ricavare
			if(denaroModel.getTTpValuta() != null)
				denaro.setIdentificativoTipoValuta(denaroModel.getTTpValuta().getCodiKIdValuta());
			denaro.setImportoValuta(denaroModel.getImpoValuta()); 
			denaro.setImportoEuro(denaroModel.getImpoEuro()); 
//			denaro.setVolturatoFug(value) ???
			denaro.setIban(denaroModel.getDescIban());
		}
		return denaro;
	}
	
	private DenaroContante getContante(TBene model){
		DenaroContante contante = new DenaroContante();
		TContante contanteModel = anbscDao.getContante(model.getId().getCodiKIdBene(),model.getId().getCodiKIdUffOwner());
		if(contanteModel != null)
		{
			if(contanteModel.getDescEnteDepos() != null)
				contante.setDescrizioneEnteDepositario(contanteModel.getDescEnteDepos());
			else
				contante.setDescrizioneEnteDepositario(Constants.NON_DISPONIBILE);
			contante.setNumeroLibretto(contanteModel.getDescIdentEnte());
			contante.setNumeroIdentificativoEnte(contanteModel.getDescIdentEnte());
			contante.setDescrizioneIntestatario(contanteModel.getDescIntestatario());
			if(contanteModel.getTTpValuta() != null)
				contante.setIdentificativoTipoValuta(contanteModel.getTTpValuta().getCodiKIdValuta());
			contante.setImportoValuta(contanteModel.getImpoValuta());
			contante.setImportoEuro(contanteModel.getImpoEuro());
			if(contanteModel.getNumeIdentModTrib() != null)
				contante.setNumeroModelloTribunale(BigInteger.valueOf(contanteModel.getNumeIdentModTrib()));
//			contante.setVolturatoFug(value); ???
			if(contanteModel.getTAnOperFinanz() != null)
			{
				contante.setAbi(contanteModel.getTAnOperFinanz().getId().getCodiKIdAbi());
				contante.setCab(contanteModel.getTAnOperFinanz().getId().getCodiKIdCab());
			}
			contante.setIban(contanteModel.getDescIban());
			
		}
		return contante;
	}
	
	private Finanziario getFinanziario(TBene model){
		Finanziario finanziario = new Finanziario();
		TBeneFin finanziarioModel = anbscDao.getFinanziario(model.getId().getCodiKIdBene(),model.getId().getCodiKIdUffOwner());
		if(finanziarioModel != null)
		{
			finanziario.setDescrizioneBene(model.getDescrBene());
			if(finanziarioModel.getTAnOperFinanz() != null)
				finanziario.setDescrizioneEnteDepositario(finanziarioModel.getTAnOperFinanz().getDescBanca());
			else
				finanziario.setDescrizioneEnteDepositario(Constants.NON_DISPONIBILE);
		    finanziario.setIndirizzo(model.getDescIndirizzo());
		    finanziario.setNumeroCivico(model.getDescCivico());
		    finanziario.setDescrizioneFilialeEnte(finanziarioModel.getDescFilialeEnte());
		    finanziario.setDescrizioneDirettoreEnte(finanziarioModel.getDescDirettoreEnte());
//		    finanziario.setQuota(value) ???
			TAnOperFinanz operatoreFinanziario = finanziarioModel.getTAnOperFinanz();
			if(finanziarioModel.getDescFilialeEnte() != null)
				finanziario.setDescrizioneFilialeEnte(finanziarioModel.getDescFilialeEnte());
			else
			{
				if(operatoreFinanziario!=null && operatoreFinanziario.getId().getCodiKIdAbi()!=null && operatoreFinanziario.getId().getCodiKIdCab()!=null)
				{
					TAnOperFinanz operatoreFinanziarioModel = anbscDao.getOperatoreFinanziario(operatoreFinanziario.getId().getCodiKIdAbi(), operatoreFinanziario.getId().getCodiKIdCab());
					finanziario.setDescrizioneFilialeEnte(getDescFiliale(operatoreFinanziarioModel));
					finanziario.setAbi(operatoreFinanziario.getId().getCodiKIdAbi()); 
					finanziario.setCab(operatoreFinanziario.getId().getCodiKIdCab());
				}
			}
//		    finanziario.setAbi(finanziarioModel.getTAnOperFinanz().getId().getCodiKIdAbi());
//		    finanziario.setCab(finanziarioModel.getTAnOperFinanz().getId().getCodiKIdCab());
		    finanziario.setNumConto(finanziarioModel.getDescRegistrazione());
		    finanziario.setIban(finanziarioModel.getDescIban());
		    if(finanziarioModel.getTTpValuta() != null)
		    	finanziario.setIdentificativoTipoValuta(finanziarioModel.getTTpValuta().getCodiKIdValuta());
		    finanziario.setImportoValuta(finanziarioModel.getImpoValuta());
		    finanziario.setImportoEuro(finanziarioModel.getImpoEuro());
		}
		return finanziario;
	}
	
	private Immobile getImmobile(TBene model,TRelProcBene procBene,TRelBeneProcDisp disponibilita,TRelImmProcVinc vincolo){
		Immobile immobile = new Immobile();
		TBeneImmobile immobileModel = anbscDao.getImmobile(model.getId().getCodiKIdBene(),model.getId().getCodiKIdUffOwner());
		if(immobileModel != null)
		{
			immobile.setIndirizzo(model.getDescIndirizzo());
		    immobile.setNumeroCivico(model.getDescCivico());
		    immobile.setEdificio(immobileModel.getDescEdificio());
		    immobile.setScala(immobileModel.getDescScala());
		    immobile.setPiano(immobileModel.getDescPiano());
		    immobile.setInterno(immobileModel.getDescInterno());
		    if(immobileModel.getNumeMcInt()!=null)
		    {
		    	String volumeStr = String.valueOf(immobileModel.getNumeMcInt());
		    	if(immobileModel.getNumeMcDec() != null)
		    		// P.Q. Ticket 20210407019
		    		volumeStr += "." + immobileModel.getNumeMcDec();
		    	BigDecimal volume = new BigDecimal(volumeStr);
		    	immobile.setVolume(volume);
		    } 
		    String vaniStr = null;
		    if(immobileModel.getNumeVaniInt() != null)
		    	vaniStr = immobileModel.getNumeVaniInt().toString();
		    if(immobileModel.getNumeVaniDec() != null)
	    		// P.Q. Ticket 20210407019
		    	vaniStr += "."  + immobileModel.getNumeVaniDec();
		    if(vaniStr != null)
		    	immobile.setVani(new BigDecimal(vaniStr));
		    
		    immobile.setLotto(immobileModel.getDescLotto());
		    if(immobileModel.getNumeSupMqInt()!=null)
		    {
		    	String superficieStr = String.valueOf(immobileModel.getNumeSupMqInt());
		    	if(immobileModel.getNumeSupMqDec() != null)
		    		// P.Q. Ticket 20210407019
		    		superficieStr += "." + immobileModel.getNumeSupMqDec();
		    	BigDecimal superficie = new BigDecimal(superficieStr);
		    immobile.setSuperficie(superficie);
		    }
		    if(immobileModel.getNumeSupEttari() != null)
		    	immobile.setEttari(BigInteger.valueOf(immobileModel.getNumeSupEttari()));
		    if(immobileModel.getNumeSupAre() != null)
		    	immobile.setAre(BigInteger.valueOf(immobileModel.getNumeSupAre()));
		    if(immobileModel.getNumeSupCentiare() != null)
		    	immobile.setCentiare(BigInteger.valueOf(immobileModel.getNumeSupCentiare()));
		    
		    DatiCatastali datiCatastali = new DatiCatastali();
		    if(immobileModel.getTTpTipoCatastoByCodiFkIdTipoCat()!= null)
		    	datiCatastali.setTipoCatasto(Character.toString(immobileModel.getTTpTipoCatastoByCodiFkIdTipoCat().getCodiKIdTipoCat()));
		    if(immobileModel.getTAnComuniByCodiFkIdComuneCat()!=null && immobileModel.getTAnComuniByCodiFkIdComuneCat().getDescBelfiore()!=null)
		    	datiCatastali.setIdentificativoComuneCatasto(immobileModel.getTAnComuniByCodiFkIdComuneCat().getDescBelfiore());
			datiCatastali.setSezioneCatasto(immobileModel.getDescSezione());
			datiCatastali.setFoglioCatasto(immobileModel.getDescFoglio());
			datiCatastali.setParticellaCatasto(immobileModel.getDescParticella1());
			datiCatastali.setSubparticellaCatasto(immobileModel.getDescSubParticella1());
			if(immobileModel.getTTpCatCatasto()!=null)
				datiCatastali.setIdentificativoCategoriaCatastale(BigInteger.valueOf(immobileModel.getTTpCatCatasto().getCodiKIdCategCat()));
			datiCatastali.setImportoRenditaCatastale(immobileModel.getImpoRenditaCat());
			datiCatastali.setImportoRenditaDominicale(immobileModel.getImpoRenditaDom());
	 
			immobile.setDatiCatastali(datiCatastali);
			
//		    immobile.setTipoDomandaAccatastamento(immobileModel.get); ???
		    immobile.setNumeroDomandaAccatastamento(immobileModel.getDescSchedaAccatast());
			if(immobileModel.getDataDomandaAccatast() != null)
			{
			    immobile.setAnnoDomandaAccatastamento(BigInteger.valueOf(immobileModel.getDataDomandaAccatast().getYear()));
			    immobile.setDataDomandaAccatastamento(DateUtils.convert(immobileModel.getDataDomandaAccatast()));
			}
			if(immobileModel.getFlgAppFabConf() != null)
				immobile.setAppartenenzaaFabbricatoConfiscato(String.valueOf(immobileModel.getFlgAppFabConf()));
		    if(immobileModel.getTTpTipoFondo() != null)
		    	immobile.setTipoFondo("" + immobileModel.getTTpTipoFondo().getCodiKIdFondo());
		    if(vincolo!=null && vincolo.getTTpTipoVincolo()!=null)
		    	immobile.setIdentificativoTipoVincolo(BigInteger.valueOf(vincolo.getTTpTipoVincolo().getCodiKIdTipoVinc()));
		    if(disponibilita != null)
		    {
		    	if(disponibilita.getTTpTipoDisp() != null)
		    		immobile.setIdentificativoTipoDisponibilita(BigInteger.valueOf(disponibilita.getTTpTipoDisp().getCodiKIdTipoDisp()));
		    	if(disponibilita.getTTpTipoOccupanti() != null)
		    		immobile.setIdentificativoTipoOccupante(BigInteger.valueOf(disponibilita.getTTpTipoOccupanti().getCodiKIdOcc()));
		    }
		    if(procBene!=null && procBene.getTTpStatoConserv()!=null)
		    	immobile.setIdentificativoStatoManutenzione(BigInteger.valueOf(procBene.getTTpStatoConserv().getCodiKIdStatoConserv()));
		}
		return immobile;
	}
	
	/******************************* SOGGETTI *****************************************/
	public List<Soggetto> getSoggetti(int uffOwner, long idProc, long idAtto, List<Bene> beni, List<TDefPatr> listaMisPatrDaInviareANBSC) {
		boolean soggettoTrovato = false;
		
		List<Soggetto> soggetti = new ArrayList<Soggetto>();
		Iterator<Bene> iterBeni = beni.iterator();
		while( iterBeni.hasNext() ) {
			Bene bene = ((Bene)iterBeni.next());
			List<Long> listMisPatr = anbscDao.getRelAttoMisPatr(idProc,idAtto,bene.getIdentificativoBene().longValue());
			if(listMisPatr != null)
			{
				Iterator<Long> iterTRelAttoMisPatr = listMisPatr.iterator();
				while(iterTRelAttoMisPatr.hasNext())
				{
					Long idSoggetto = (Long)iterTRelAttoMisPatr.next();
					
					// Cerco il soggetto tra quelli che hanno una confisca
					soggettoTrovato = false;
					if(listaMisPatrDaInviareANBSC != null && !listaMisPatrDaInviareANBSC.isEmpty()) {
						for (TDefPatr recordTdefPatr : listaMisPatrDaInviareANBSC) {
							if (recordTdefPatr.getTRelBeneAtto().getId().getCodiKIdSoggTit() == idSoggetto.longValue()) {
								soggettoTrovato = true;
								break;
							}
						}
					}
					
					Soggetto soggetto = new Soggetto();
					soggetto.setIdentificativoSoggetto(BigInteger.valueOf(idSoggetto));
					TRelProcSogint procSogint = getTRelProcSogint(idProc,idSoggetto,uffOwner);
					short versioneSoggetto = 0;
					if(procSogint != null)
					{
						if(procSogint.getTRelProcSogintVer() != null)
						{
							versioneSoggetto = procSogint.getTRelProcSogintVer().getId().getCodiKIdVer();
							soggetto.setNumeroVersione(BigInteger.valueOf(versioneSoggetto));
						}
						if(procSogint.getTTpTipoRuolo() != null)
						{
							if(procSogint.getTTpTipoRuolo().getCodiKIdRuolo() == 1)
								soggetto.setTipoSoggetto(BigInteger.ZERO);
							else if(procSogint.getTTpTipoRuolo().getCodiKIdRuolo() == 2)
								soggetto.setTipoSoggetto(BigInteger.ONE);
							
						}
					}
					String tipoPersona = getTipoSoggetto(idSoggetto);
					soggetto.setTipoPersona(tipoPersona);
					if(tipoPersona.equalsIgnoreCase("F"))
					{
						TPersonaFisica personaFisica = getPersonaFisica(idSoggetto,versioneSoggetto,uffOwner);
						if(personaFisica != null)
						{
							SoggettoPersonaFisica pf = new SoggettoPersonaFisica();
							pf.setCognome(personaFisica.getDescCognome());
							pf.setNome(personaFisica.getDescNome());
							pf.setSesso(String.valueOf(personaFisica.getFlgSesso()));
							pf.setDataNascita(DateUtils.convert(personaFisica.getDataNascita()));
							if(personaFisica.getTAnStatoByCodiFkIdStatoNasc()!=null)
							{
								LuogoNascita luogoNascita = new LuogoNascita();
								if(personaFisica.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato() == 1)//ITALIA
								{
									if(personaFisica.getTAnComuniByCodiFkIdComNasc() != null)
										luogoNascita.setComuneNascitaItalia(personaFisica.getTAnComuniByCodiFkIdComNasc().getDescBelfiore());
								}
								else //ESTERO
									luogoNascita.setStatoNascitaEstero(personaFisica.getTAnStatoByCodiFkIdStatoNasc().getDescBelfiore());
								
								pf.setLuogoNascita(luogoNascita);
							}
//							pf.setLuogoresidenza()
//							pf.setIndirizzo()
							pf.setCodiceFiscale(personaFisica.getDescCodiceFiscale());
							pf.setCUI(personaFisica.getDescCui());
							soggetto.setSoggettoPersonaFisica(pf);
						}
					}
					else if(tipoPersona.equalsIgnoreCase("G"))
					{
						TPersonaGiur personaGiuridica = getPersonaGiuridica(idSoggetto,versioneSoggetto,uffOwner);
						SoggettoPersonaGiuridica pg = new SoggettoPersonaGiuridica();
						pg.setDenominazione(personaGiuridica.getDescRagioneSoc());
						//Roberto aggiunto controllo partita IVA assente
						if(personaGiuridica.getDescPartitaIva()!=null && personaGiuridica.getDescPartitaIva().length() > 11)
							pg.setPartitaIVA(riduciPartitaIva(personaGiuridica.getDescPartitaIva()));
						else
							pg.setPartitaIVA(personaGiuridica.getDescPartitaIva());
						if(personaGiuridica.getTAnStato()!=null)
						{
							LuogoSedeLegale luogoSedeLegale = new LuogoSedeLegale();
							if(personaGiuridica.getTAnStato().getCodiKIdStato() == 1)//ITALIA
							{
								if(personaGiuridica.getTAnComuni() != null)
									luogoSedeLegale.setComuneSedeLegaleItalia(personaGiuridica.getTAnComuni().getDescBelfiore());
							}
							else //ESTERO
								luogoSedeLegale.setStatoSedeLegaleEstero(personaGiuridica.getTAnStato().getDescBelfiore());
							
							pg.setLuogoSedeLegale(luogoSedeLegale);
						}
						pg.setIndirizzoSedeLegale(personaGiuridica.getDescIndirizzoSede());
						pg.setTipologia(allineaTipoSocieta(personaGiuridica.getTTpTipoSocieta().getCodiKIdTipoSoc()));
						soggetto.setSoggettoPersonaGiuridica(pg);
					}
					
					if(listaMisPatrDaInviareANBSC == null || (listaMisPatrDaInviareANBSC != null && !listaMisPatrDaInviareANBSC.isEmpty() && soggettoTrovato == true)) {
						soggetti.add(soggetto);	
					}					
				}
			}
		}
		
		if(soggetti.size() > 1)
		{
			Collections.sort(soggetti);
	    	Soggetto temp1 = soggetti.get(0);
	    	for(int i=1; i<soggetti.size(); i++)
	    	{
	    		Soggetto temp2 = soggetti.get(i);
	    		if(temp1.getIdentificativoSoggetto().equals(temp2.getIdentificativoSoggetto())
	        			&& temp1.getTipoPersona().equals(temp2.getTipoPersona()))
	    		{
	    			soggetti.remove(i);
	    			i--;
	    		}
	    		temp1 = temp2;
	    	}
		}

		return soggetti;
		
	}
	
	/*********************************** MISURE *******************************************/
	public List<Misura> getMisure(long idProc, long idAtto, List<Bene> beni, Integer uffOwner, List<TDefPatr> listaMisPatrDaInviareANBSC){
		boolean trovato = false;
		
		List<Misura> misureList = new ArrayList<Misura>();
		Iterator<Bene> iter = beni.iterator();
		while(iter.hasNext() ) {
			Bene bene = (Bene)iter.next();
			List<TRelAttoMisPatr> relAttoMisPatrList = getSoggettoTitolare(idProc, idAtto, bene.getIdentificativoBene().longValue(), uffOwner);
//			List<Long> soggettiTitPerBene = getIdSoggettoTitolare(relAttoMisPatrList);
//			Iterator<Long> iterSoggTit = soggettiTitPerBene.iterator();
			Iterator<TRelAttoMisPatr> iterRelAttoMisPatr = relAttoMisPatrList.iterator();
			while(iterRelAttoMisPatr.hasNext() ) {
				TRelAttoMisPatr relAttoMisPatr = (TRelAttoMisPatr)iterRelAttoMisPatr.next();
				Misura misura = new Misura();
				misura.setIdentificativoBene(bene.getIdentificativoBene());
				misura.setIdentificativoSoggettoTitolare(BigInteger.valueOf(relAttoMisPatr.getId().getCodiKIdSoggTit()));
				
				TRelProcSogint tmpProcSogint = getTRelProcSogint(idProc,relAttoMisPatr.getId().getCodiKIdSoggTit(),uffOwner);
				if(tmpProcSogint!=null && tmpProcSogint.getTTpTipoRuolo() != null)
				{
					if(tmpProcSogint.getTTpTipoRuolo().getCodiKIdRuolo() == 1)
						misura.setTipoSoggetto(BigInteger.ZERO);
					else if(tmpProcSogint.getTTpTipoRuolo().getCodiKIdRuolo() == 2)
						misura.setTipoSoggetto(BigInteger.ONE);	
				}

				misura.setTipoInteresse(BigInteger.valueOf(relAttoMisPatr.getId().getCodiKIdTipoInt()));
				TRelBeneAtto relBeneAtto = getTRelBeneAtto(idProc, idAtto, bene.getIdentificativoBene().longValue(), relAttoMisPatr.getId().getCodiKIdSoggTit(), relAttoMisPatr.getId().getCodiKIdTipoInt(), uffOwner);
				if(relBeneAtto != null)
				{
					
					// il record relBeneAtto e' presente nella lista da inviare a ANBSC ?
					// Se si metto la misura, altrimenti non la metto					
					trovato = false;
					if(listaMisPatrDaInviareANBSC != null && !listaMisPatrDaInviareANBSC.isEmpty()) {
							for (TDefPatr recordTdefPatr : listaMisPatrDaInviareANBSC) {
								if(recordTdefPatr.getTRelBeneAtto().getId().getCodiKIdProc() == relBeneAtto.getId().getCodiKIdProc() && 
								   recordTdefPatr.getTRelBeneAtto().getId().getCodiKIdAtto() == relBeneAtto.getId().getCodiKIdAtto() &&
								   recordTdefPatr.getTRelBeneAtto().getId().getCodiKIdBene() == relBeneAtto.getId().getCodiKIdBene() &&
								   recordTdefPatr.getTRelBeneAtto().getId().getCodiKIdSoggTit() == relBeneAtto.getId().getCodiKIdSoggTit() ) {
									 trovato = true;
									 break;
								}
							}											
					}					
					
					//QUOTA
					Misura.Quota quota = new Misura.Quota();
					if(relBeneAtto.getNumeQuota() != null)
						quota.setQuotaIntera(relBeneAtto.getNumeQuota());
					else if(relBeneAtto.getNumeFrazioneNum()!=null && relBeneAtto.getNumeFrazioneDen()!=null)
					{
						Misura.Quota.QuotaFrazione quotaFrazione = new Misura.Quota.QuotaFrazione();
						quotaFrazione.setNumFrazioneInteresse(BigInteger.valueOf(relBeneAtto.getNumeFrazioneNum()));
						quotaFrazione.setDenomFrazioneInteresse(BigInteger.valueOf(relBeneAtto.getNumeFrazioneDen()));
						quota.setQuotaFrazione(quotaFrazione);
					}
					misura.setQuota(quota);
					//SOGGETTI PROPOSTI
					List<Long> soggettiProposti = getSoggettiProposti(idProc, idAtto, bene.getIdentificativoBene().longValue(),relAttoMisPatr.getId().getCodiKIdSoggTit(),uffOwner);
					if(soggettiProposti != null)
					{
						List<BigInteger> listSoggProposti = new ArrayList<BigInteger>();
						for (Long idSoggInt : soggettiProposti)
						{
							listSoggProposti.add(BigInteger.valueOf(idSoggInt));
						}
						misura.setIdentificativoSoggettoProposto(listSoggProposti);
					}
					if(relBeneAtto.getTAnNormative() != null)
						misura.setIdentificativoNormativa(BigInteger.valueOf(relBeneAtto.getTAnNormative().getCodiKIdNormativa()));
					else
						misura.setIdentificativoNormativa(BigInteger.valueOf(0));
					
					// Pre imposto il valore a X perche' il campo e' obbligatorio, ma nella proposta questo campo non e' valorizzato!
					// P.Q. 20/01/2021 modifica per collaudo , da togliere successivamente!
					misura.setDecisione("X");
					
					if(relBeneAtto.getTTpTipoMisura() != null)
					{
						if(relBeneAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 5)
							misura.setDecisione("S");//Sequestro
						else if(relBeneAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 20)
							misura.setDecisione("R");//Revoca
					}
					if(relBeneAtto.getTTpEsitoFinale() != null)
					{
						if(relBeneAtto.getTTpEsitoFinale().getCodiKIdEsitoFin() == 1)
							misura.setDecisione("C");//Sequestro
						else if(relBeneAtto.getTTpEsitoFinale().getCodiKIdEsitoFin() == 2)
							misura.setDecisione("R");
					}
				}
				
				if(listaMisPatrDaInviareANBSC == null || (listaMisPatrDaInviareANBSC != null && !listaMisPatrDaInviareANBSC.isEmpty() && trovato == true)) {
					misureList.add(misura);					
				}
					
			}
		}
		
		if(misureList.size()>1)
		{
			Collections.sort(misureList);
	    	Misura temp1 = misureList.get(0);
	    	for(int i=1; i<misureList.size(); i++)
	    	{
	    		Misura temp2 = misureList.get(i);
	    		if(temp1.getIdentificativoBene().equals(temp2.getIdentificativoBene())
	    				&& ((temp1.getIdentificativoNormativa()!=null && temp2.getIdentificativoNormativa()!=null)
	    					&&
	    					(temp1.getIdentificativoNormativa().equals(temp2.getIdentificativoNormativa()))
	    					)
	        			&& temp1.getIdentificativoSoggettoTitolare().equals(temp2.getIdentificativoSoggettoTitolare())
	        			&& temp1.getTipoInteresse().equals(temp2.getTipoInteresse()))
	    		{
	    			misureList.remove(i);
	    			i--;
	    		}
	    		temp1 = temp2;
	    	}
		}
    	
		return misureList;
	}
	
	/********************************** AMMINISTRATORE *************************************/
	private List<Amministratore> getAmministratori(long idProcedimento, long idAtto, Integer uffOwner) {
		List<Long> idAmmList = anbscDao.getIdAmministratore(idProcedimento, idAtto, uffOwner);
		if(idAmmList!=null && idAmmList.size()!=0)
		{
			List<Amministratore> amministratoriList = new ArrayList<Amministratore>();
			Iterator<Long> iter = idAmmList.iterator();
			while(iter.hasNext() ) {
				Long idAmm = (Long)iter.next();
				TAmministratori amministratoreModel = anbscDao.getAmministratore(idAmm);
				Amministratore amministratore = new Amministratore();
				amministratore.setIdentificativoAmministratore(BigInteger.valueOf(idAmm));
				amministratore.setCognome(amministratoreModel.getDescCognome());
				amministratore.setNome(amministratoreModel.getDescNome());
				if(amministratoreModel.getDescCodiceFiscale() != null)
					amministratore.setCodiceFiscale(amministratoreModel.getDescCodiceFiscale());
				if(amministratoreModel.getDescPartitaIva() != null)
				{
					if(amministratoreModel.getDescPartitaIva().length() > 11)
						amministratore.setPartitaIVA(riduciPartitaIva(amministratoreModel.getDescPartitaIva()));
					else
						amministratore.setPartitaIVA(amministratoreModel.getDescPartitaIva());
				}
				if(amministratoreModel.getDataNascita() != null)
					amministratore.setDataNascita(DateUtils.convert(amministratoreModel.getDataNascita()));
//				Amministratore.LuogoNascita luogoNascita = new Amministratore.LuogoNascita();
//				amministratore.setIndirizzoStudio(value)
				if(amministratoreModel.getDescIscrizioneAlbo() != null)
					amministratore.setNumeroIscrizioneAlbo(amministratoreModel.getDescIscrizioneAlbo());
//				amministratore.setSpecializzazioneAlbo(value)
				if(amministratoreModel.getDescPec() != null)
					amministratore.setPec(amministratoreModel.getDescPec());
				
				amministratoriList.add(amministratore);
			}
			return amministratoriList;
		}
		else
			return null;
	}
	
	private List<Amministratore> getAmministratori(	List<Amministrazione> amministrazioneList) {
		List<Amministratore> amministratoriList = new ArrayList<Amministratore>();
		Iterator<Amministrazione> iter = amministrazioneList.iterator();
		while(iter.hasNext() ) {
			Amministrazione amministrazione = (Amministrazione)iter.next();
			if(amministrazione.getIdentificativoAmministratore() != null)
			{
				long idAmministratore = amministrazione.getIdentificativoAmministratore().longValue();
				TAmministratori amministratoreModel = anbscDao.getAmministratore(idAmministratore);
				Amministratore amministratore = new Amministratore();
				amministratore.setIdentificativoAmministratore(BigInteger.valueOf(idAmministratore));
				amministratore.setCognome(amministratoreModel.getDescCognome());
				amministratore.setNome(amministratoreModel.getDescNome());
				if(amministratoreModel.getDescCodiceFiscale() != null)
					amministratore.setCodiceFiscale(amministratoreModel.getDescCodiceFiscale());
				if(amministratoreModel.getDescPartitaIva() != null)
					amministratore.setPartitaIVA(amministratoreModel.getDescPartitaIva());
				if(amministratoreModel.getDataNascita() != null)
					amministratore.setDataNascita(DateUtils.convert(amministratoreModel.getDataNascita()));
				if(amministratoreModel.getDescIscrizioneAlbo() != null)
					amministratore.setNumeroIscrizioneAlbo(amministratoreModel.getDescIscrizioneAlbo());
				if(amministratoreModel.getDescPec() != null)
					amministratore.setPec(amministratoreModel.getDescPec());
				
				amministratoriList.add(amministratore);
			}
		}
		if(amministratoriList!=null && amministratoriList.size()>0)
			return amministratoriList;
		else
			return null;
	}
	
	/********************************** AMMINISTRAZIONE *************************************/
	private List<Amministrazione> getAmministrazioni(long idProcedimento, long idAtto, Integer uffOwner, boolean escludiAgenzia) {
		List<TRelProcBeneAttoAmm> amministrazioniModel = anbscDao.getAmministrazioni(idProcedimento, idAtto, uffOwner, escludiAgenzia);
		if(amministrazioniModel != null)
		{
			List<Amministrazione> amministrazioniResult = new ArrayList<Amministrazione>();
			Iterator<TRelProcBeneAttoAmm> iter = amministrazioniModel.iterator();
			Amministrazione amministrazione = null;
			long amministratoreTmp = 0L;
			List<BigInteger> idBeniList = new ArrayList<BigInteger>();
			while(iter.hasNext() ) {
				TRelProcBeneAttoAmm amm = iter.next();
				if(amministratoreTmp != amm.getId().getCodiKIdAmmin())
				{
					if(amministratoreTmp!=0L && amministratoreTmp!=amm.getId().getCodiKIdAmmin())
					{
						amministrazione.setIdentificativoBene(idBeniList);
						amministrazioniResult.add(amministrazione);
					}
					amministrazione = new Amministrazione();
					amministrazione.setIdentificativoAmministratore(BigInteger.valueOf(amm.getId().getCodiKIdAmmin()));
					if(amm.getDataInizioIncarico() != null)
						amministrazione.setDataInizioIncarico(DateUtils.convert(amm.getDataInizioIncarico()));
					if(amm.getDataFineIncarico() != null)
						amministrazione.setDataFineIncarico(DateUtils.convert(amm.getDataFineIncarico()));
					idBeniList = new ArrayList<BigInteger>();
					idBeniList.add(BigInteger.valueOf(amm.getId().getCodiKIdBene()));
					amministratoreTmp = amm.getId().getCodiKIdAmmin();
				}
				else
				{
					idBeniList.add(BigInteger.valueOf(amm.getId().getCodiKIdBene()));
					amministratoreTmp = amm.getId().getCodiKIdAmmin();
				}
			}
			amministrazione.setIdentificativoBene(idBeniList);
			amministrazioniResult.add(amministrazione);
			return amministrazioniResult;
		}
		else
			return null;
	}
	
	/********************************** NUOVA UDIENZA ***************************************/
	private NuovaUdienza getNuovaUdienza(long idProcedimento, long idAtto, Integer idUfficio) {
		long idUdienza = anbscDao.getIdUdienza(idAtto, idUfficio);
		if(idUdienza != 0)
		{
			TUdienza udienza = anbscDao.getUdienza(idProcedimento, idAtto, idUfficio);
			if(udienza != null){
				NuovaUdienza nuovaUdienza = new NuovaUdienza();
				nuovaUdienza.setIdUdienza(BigInteger.valueOf(idAtto));
				nuovaUdienza.setStatoUdienza(getStatoUdienza(idAtto, idUfficio));
				if(udienza.getDataUdienza() != null)
					nuovaUdienza.setDataFissazione(DateUtils.convert(udienza.getDataUdienza()));
				nuovaUdienza.setIndirizzo(udienza.getDescIndirAula());
				nuovaUdienza.setAula(udienza.getDescAula());
				return nuovaUdienza;
			}
		}
		return null;
	}
	
	/*********************************** VECCHIA UDIENZA **************************************/
	private VecchiaUdienza getVecchiaUdienza(long idAtto, Integer idUfficio) {
		VecchiaUdienza result = null;
		List<TRelUdienzaStatoUd> udienzaList = anbscDao.getStatoUdienza(idAtto, idUfficio);
		if(udienzaList != null)
		{
			result = new VecchiaUdienza();
			Iterator<TRelUdienzaStatoUd> iter = udienzaList.iterator();
			long idVecchiaUdienza=0L;
			while(iter.hasNext() ) {
				TRelUdienzaStatoUd rel = (TRelUdienzaStatoUd)iter.next();
				if(rel.getTUdienzaByTUdienzaStatoudFkUdienza2()!=null && rel.getTUdienzaByTUdienzaStatoudFkUdienza2().getId()!=null)
				{
					idVecchiaUdienza = rel.getTUdienzaByTUdienzaStatoudFkUdienza2().getId().getCodiKIdUdienza();
					break;
				}
			}
			if(idVecchiaUdienza != 0L)
			{
				result.setIdUdienza(BigInteger.valueOf(idVecchiaUdienza));
				List<TRelUdienzaStatoUd> udienzaVecchia = anbscDao.getStatoUdienza(idVecchiaUdienza, idUfficio);
				if(udienzaVecchia!= null && udienzaVecchia.size()!=0)
				{
					TRelUdienzaStatoUd element = udienzaVecchia.get(0);
					result.setStatoUdienza(element.getTTpStatoUdienza().getDescStatoUdienza());
				}
			}
		}
		return result;
	}
	
	private String getUfficio (Integer idUfficio){//(Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE))
		return anbscDao.getUfficio(idUfficio);
	}
	
	private TDecreto getDecreto(long idAtto){
		return anbscDao.getDecreto(idAtto);
	}
	
	private Date getDataDepositoProvv(long idAtto){
		return anbscDao.getDataDepositoProvv(idAtto);
	}
	
	private List<Long> getListaIdBeni(long idProcedimento, long idAtto) {
		return anbscDao.getListaIdBeni(idProcedimento, idAtto);
	}
	
	private List<Long> getListaIdBeniDecretoConvalida(long idProcedimento, long idAtto) {
		return anbscDao.getListaIdBeniDecretoConvalida(idProcedimento, idAtto);
	}
	
	private TRelBeneProcDisp getDisponibilita(long idProc, long idBene){
		TRelBeneProcDisp disponibilita = anbscDao.getDisponibilita(idProc, idBene);
		return disponibilita;
	}
	
	private TRelProcBene getProcBene(long idProc, long idBene){
		TRelProcBene procBene = anbscDao.getProcBene(idProc, idBene);
		return procBene;
	}
	
	private TRelImmProcVinc getVincolo(long idProc, long idBene){
		TRelImmProcVinc vincolo = anbscDao.getVincolo(idProc,idBene); 
		return vincolo;
	}
	
	private TAnOperFinanz getOperatoreFinanziario(Class clazz, long idBene){
		TAnOperFinanz operatoreFinanziario = anbscDao.getOperatoreFinanziario(clazz, idBene);
		return operatoreFinanziario;
	}
	
	private TRelProcSogint getTRelProcSogint(long idProc, long idSogg, Integer uffOwner){
		TRelProcSogint soggetto = anbscDao.getTRelProcSogint(idProc, idSogg, uffOwner);
		return soggetto;
	}
	
	private List<TRelProcSogint> getTRelProcSogintList(long idProc, long idSogg, Integer uffOwner){
		List<TRelProcSogint> soggetti = anbscDao.getTRelProcSogintList(idProc, idSogg, uffOwner);
		return soggetti;
	}
	
	private String getTipoSoggetto(long idSoggetto){
		return anbscDao.getTipoSoggetto(idSoggetto);
	}
	
	private TPersonaFisica getPersonaFisica(long idSoggetto, short versioneSoggetto, int uffOwner){
		return anbscDao.getPersonaFisica(idSoggetto,versioneSoggetto,uffOwner);
	}
	
	private TPersonaGiur getPersonaGiuridica(long idSoggetto, short versioneSoggetto, int uffOwner){
		return anbscDao.getPersonaGiuridica(idSoggetto,versioneSoggetto,uffOwner);
	}
	
	private List<TRelAttoMisPatr> getSoggettoTitolare(long idProc, long idAtto, long idBene, int uffOwner){
		return anbscDao.getSoggettoTitolare(idProc, idAtto, idBene, uffOwner);
	}
	
	private List<Long> getIdSoggettoTitolare(List<TRelAttoMisPatr> relAttoMisPatrList){
		List<TRelAttoMisPatr> lista = relAttoMisPatrList;
		List<Long> result = new ArrayList<Long>();
		if(lista.size() != 0 && lista.size() > 1)
		{
			long tmp1 = lista.get(0).getId().getCodiKIdSoggTit();;
			for(int i=1;i<lista.size();i++)
			{
				long tmp2 = lista.get(i).getId().getCodiKIdSoggTit();
				if(tmp1 == tmp2)
					lista.remove(i);
				else
					tmp1 = lista.get(i).getId().getCodiKIdSoggTit();
			}
			for (TRelAttoMisPatr relAttoMisPatr : lista)
				result.add(relAttoMisPatr.getId().getCodiKIdSoggTit());
		}
		return result;
	}
	
	private TRelBeneAtto getTRelBeneAtto(long idProc, long idAtto, long idBene, long idSoggTit, int tipoInteresse, int uffOwner){
		return anbscDao.getTRelBeneAtto(idProc, idAtto, idBene, idSoggTit, tipoInteresse, uffOwner);
	}
	
	private List<String> getStatoUdienza(long idAtto, Integer idUfficio){
		List<TRelUdienzaStatoUd> udienzaList = anbscDao.getStatoUdienza(idAtto, idUfficio);
		List<String> result = null;
		if(udienzaList != null)
		{
			result = new ArrayList<String>();
			Iterator<TRelUdienzaStatoUd> iter = udienzaList.iterator();
			while(iter.hasNext() ) {
				TRelUdienzaStatoUd rel = (TRelUdienzaStatoUd)iter.next();
				result.add(Long.toString(rel.getId().getCodiKIdStatoUd()));
			}
		}
		return result;
	}

	private List<Long> getSoggettiProposti(long idProc, long idAtto, long idBene, long idSoggTit, Integer uffOwner){
		return anbscDao.getSoggettiProposti(idProc, idAtto, idBene, idSoggTit, uffOwner);
	}
	
	private String getDescFiliale(TAnOperFinanz model){
		String result = "";
		if(model.getDescCap() != null)
			result += model.getDescCap() + "- ";
		if(model.getDescComune() != null)
			result += model.getDescComune() + "-";
		if(model.getDescProv() != null)
			result += model.getDescProv() + "-";
		if(model.getDescIndirizzo() != null)
			result += model.getDescIndirizzo();
		return result;
	}
	
	public List<TAnbscMsg> getDaInviare(){
		return anbscDao.getDaInviare();
	}
	
	private BigInteger allineaTipoSocieta(int tipoSocietaSitmp){
		BigInteger result;
		switch (tipoSocietaSitmp) {
	    case 1:  result = new BigInteger("17");
	        break;
	    case 2:  result = new BigInteger("21");
	    	break;
	    case 3:  result = new BigInteger("22");
			break;
	    case 4:  result = new BigInteger("23");
			break;
	    case 5:  result = new BigInteger("25");
			break;
	    case 6:  result = new BigInteger("24");
			break;
	    case 7:  result = new BigInteger("18");
			break;
	    case 8:  result = new BigInteger("19");
			break;
	    case 9:  result = new BigInteger("20");
			break;
	    case 10:  result = new BigInteger("26");
			break;
	    case 11:  result = new BigInteger("19");
			break;
		default:
			result = null;
		}
		return result;
	}
	
	private List<Object> removeDuplicates(List<Object> l) {
	    Set<Object> s = new TreeSet<Object>(new Comparator<Object>() {

	        public int compare(Object o1, Object o2) {
	        	Soggetto sogg1 = (Soggetto)o1;
	        	Soggetto sogg2 = (Soggetto)o2;
	        	if(sogg1.getIdentificativoSoggetto().equals(sogg2.getIdentificativoSoggetto())
	        			&& sogg1.getTipoPersona().equals(sogg2.getTipoPersona()))
	        		return 0;
	        	else
	        		return -1;
	        }
	    });
	            s.addAll(l);
	    List<Object> result = Arrays.asList(s.toArray());
	    return result;
	}
	
	private List<TRelBeneAtto> getListaBeneAttoStralcio(Long idProcedimentoOrigine, Long idProcedimentoDestinazione, Integer idUfficio){
		
		//Estrare la lista dei provvedimenti (=atti) da processare
		List<TRelBeneAtto> result = anbscDao.getListaTRelBeneAtto(idProcedimentoOrigine,idProcedimentoDestinazione,idUfficio);
		
		return result;
	}
	
	
	private List<ProvvedimentiOld> getListaProvvedimentiOld(List<TRelBeneAtto> listTRelBeneAtto, Long idProcedimentoOrigine, Long idProcedimentoDestinazione, Integer idUfficio){
							
		//Per ogni TRelBeneAtto trovata bisogna estrarre le misure
		List<ProvvedimentiOld> result = new ArrayList<ProvvedimentiOld>();
		for (TRelBeneAtto tRelBeneAtto : listTRelBeneAtto) {
			
			Long idAtto = tRelBeneAtto.getId().getCodiKIdAtto();
			ProvvedimentiOld provOdl = new ProvvedimentiOld();
			provOdl.setIdentificativoProvvedimento(BigInteger.valueOf(idAtto));
			List<Bene> beniList = getBeni(idProcedimentoOrigine, idAtto);
			provOdl.getMisura().addAll(getMisure(idProcedimentoOrigine, idAtto, beniList, idUfficio, null));
			
			result.add(provOdl);
		}
		
		return result;
	}
	
	private List<Amministratore> getAmministratoriStralcio(List<TRelBeneAtto> listTRelBeneAtto, Long idProcedimentoOrigine, Integer idUfficio){
		
		Map<BigInteger,Amministratore> mapAmministratori = new HashMap<BigInteger,Amministratore>();
		
		for (TRelBeneAtto tRelBeneAtto : listTRelBeneAtto) {
			
			List<Amministratore> localList = getAmministratori(idProcedimentoOrigine, tRelBeneAtto.getId().getCodiKIdAtto(), idUfficio);
			if(localList!=null && localList.size() > 0){
				//Inserire ogni item nella List<Amministratore> result in modo che non ci siano duplicati
				for (Amministratore amministratore : localList) {
					//Se la mapAmministratori non contiente l'idAmministratore allora l'item può essere inserito in result
					if(!mapAmministratori.containsKey(amministratore.getIdentificativoAmministratore())){
						mapAmministratori.put(amministratore.getIdentificativoAmministratore(), amministratore);
					}
				}
			}
		}
		
		List<Amministratore> result = mapAmministratori.values().isEmpty() ? new ArrayList<Amministratore>() : new ArrayList<Amministratore>(mapAmministratori.values());
		return result;
	}
	
	private Date getDataDefinitivita(long idProcedimento, long idAtto, Integer idUfficiolong ){
		return anbscDao.getDataDefinitivita(idProcedimento, idAtto, idUfficiolong);
	}
	public long recuperaAttoOrig(long idAtto, Integer idUfficioUtente) {
		return anbscDao.recuperaAttoOrig(idAtto, idUfficioUtente);
	}
	
	private String riduciPartitaIva(String partitaIvaOrig)
	{
		int len = partitaIvaOrig.length();
		int beginIndex = len - 11;
		String partitaIva = partitaIvaOrig.substring(beginIndex);
		return partitaIva;
	}
	
	
	
}
