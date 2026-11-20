package it.giustizia.smp.service;

import it.giustizia.smp.dao.AnbscDao;
import it.giustizia.smp.dao.AttoDao;
import it.giustizia.smp.dao.BeneDao;
import it.giustizia.smp.dao.CommonDao;
import it.giustizia.smp.dao.PoliziaDao;
import it.giustizia.smp.dao.ProcedimentoDao;
import it.giustizia.smp.dao.SoggettoDao;
import it.giustizia.smp.dao.cameralini.ProcedimentoDaoCam;
import it.giustizia.smp.dto.pg_cameralini.IntestatarioDto;
import it.giustizia.smp.dto.pg_soggetto.DettaglioPersonaFisicaDto;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.model.TAnComuni;
import it.giustizia.smp.model.TAnStato;
import it.giustizia.smp.model.TAtto;
import it.giustizia.smp.model.TAttoId;
import it.giustizia.smp.model.TBene;
import it.giustizia.smp.model.TCauzione;
import it.giustizia.smp.model.TCauzioneId;
import it.giustizia.smp.model.TDecreto;
import it.giustizia.smp.model.TIstanza;
import it.giustizia.smp.model.TIstanzaId;
import it.giustizia.smp.model.TMisuraPers;
import it.giustizia.smp.model.TPersonaFisica;
import it.giustizia.smp.model.TPersonaFisicaId;
import it.giustizia.smp.model.TPersonaGiur;
import it.giustizia.smp.model.TPoliziaMsg;
import it.giustizia.smp.model.TProcedimento;
import it.giustizia.smp.model.TRelAttoMisPatr;
import it.giustizia.smp.model.TRelAttoMisPers;
import it.giustizia.smp.model.TRelBeneAtto;
import it.giustizia.smp.model.TRelProcProc;
import it.giustizia.smp.model.TRelProcSogint;
import it.giustizia.smp.model.TTpCatAtto;
import it.giustizia.smp.model.TTpPoliziaServizi;
import it.giustizia.smp.model.TTpTipoPrevProc;
import it.giustizia.smp.model.TTpTipoUfficio;
import it.giustizia.smp.service.webservices.PoliziaWsService;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.webservice.client.anbsc.Bene;
import it.giustizia.smp.webservice.client.anbsc.Soggetto;
import it.giustizia.smp.webservice.client.polizia.DatiAnagraficiPersonaFisicaType;
import it.giustizia.smp.webservice.client.polizia.DatiAnagraficiPersonaGiuridicaType;
import it.giustizia.smp.webservice.client.polizia.DatiAutoritaIdType;
import it.giustizia.smp.webservice.client.polizia.DatiAutoritaProponenteType;
import it.giustizia.smp.webservice.client.polizia.DatiAutoritaRichiedenteType;
import it.giustizia.smp.webservice.client.polizia.DatiBeneType;
import it.giustizia.smp.webservice.client.polizia.DatiDecretoDefinitivitaType;
import it.giustizia.smp.webservice.client.polizia.DatiDecretoType;
import it.giustizia.smp.webservice.client.polizia.DatiMisuraPatrimonialeCassazioneType;
import it.giustizia.smp.webservice.client.polizia.DatiMisuraPatrimonialeDefinitivitaType;
import it.giustizia.smp.webservice.client.polizia.DatiMisuraPatrimonialePrimoGradoType;
import it.giustizia.smp.webservice.client.polizia.DatiMisuraPatrimonialeSecondoGradoType;
import it.giustizia.smp.webservice.client.polizia.DatiMisuraPatrimonialeType;
import it.giustizia.smp.webservice.client.polizia.DatiMisuraPersonaleCassazioneType;
import it.giustizia.smp.webservice.client.polizia.DatiMisuraPersonaleDefinitivitaType;
import it.giustizia.smp.webservice.client.polizia.DatiMisuraPersonalePrimoGradoType;
import it.giustizia.smp.webservice.client.polizia.DatiMisuraPersonaleSecondoGradoType;
import it.giustizia.smp.webservice.client.polizia.DatiMisuraPersonaleType;
import it.giustizia.smp.webservice.client.polizia.DatiMsgErroreType;
import it.giustizia.smp.webservice.client.polizia.DatiParereType;
import it.giustizia.smp.webservice.client.polizia.DatiPersonaFisicaType;
import it.giustizia.smp.webservice.client.polizia.DatiPersonaGiuridicaType;
import it.giustizia.smp.webservice.client.polizia.DatiRichiestaModificaRevocaMisuraType;
import it.giustizia.smp.webservice.client.polizia.DatiSentenzaType;
import it.giustizia.smp.webservice.client.polizia.DatiSoggettoPersonaFisicaType;
import it.giustizia.smp.webservice.client.polizia.DatiSoggettoPersonaGiuridicaType;
import it.giustizia.smp.webservice.client.polizia.DatiSoggettoRichiestaType;
import it.giustizia.smp.webservice.client.polizia.DatiUfficioCompetenteType;
import it.giustizia.smp.webservice.client.polizia.DatiUfficioDecidenteType;
import it.giustizia.smp.webservice.client.polizia.DatiUfficioGiudiziarioType;
import it.giustizia.smp.webservice.client.polizia.DecisorioAggravamentoMisuraPersonaleRequest;
import it.giustizia.smp.webservice.client.polizia.DecisorioAggravamentoMisuraPersonaleResponse;
import it.giustizia.smp.webservice.client.polizia.DecisorioRevocaModificaMisuraPersonaleRequest;
import it.giustizia.smp.webservice.client.polizia.DecisorioRevocaModificaMisuraPersonaleResponse;
import it.giustizia.smp.webservice.client.polizia.DecretiDecisoriIGradoPatrimonialiRequest;
import it.giustizia.smp.webservice.client.polizia.DecretiDecisoriIGradoPatrimonialiResponse;
import it.giustizia.smp.webservice.client.polizia.DecretiDecisoriIGradoPersonaliRequest;
import it.giustizia.smp.webservice.client.polizia.DecretiDecisoriIGradoPersonaliResponse;
import it.giustizia.smp.webservice.client.polizia.DecretiDecisoriIIGradoPatrimonialiRequest;
import it.giustizia.smp.webservice.client.polizia.DecretiDecisoriIIGradoPatrimonialiResponse;
import it.giustizia.smp.webservice.client.polizia.DecretiDecisoriIIGradoPersonaliRequest;
import it.giustizia.smp.webservice.client.polizia.DecretiDecisoriIIGradoPersonaliResponse;
import it.giustizia.smp.webservice.client.polizia.DecretiProvvisoriApplicazioneRequest;
import it.giustizia.smp.webservice.client.polizia.DecretiProvvisoriApplicazioneResponse;
import it.giustizia.smp.webservice.client.polizia.DecretiProvvisoriRevocaRequest;
import it.giustizia.smp.webservice.client.polizia.DecretiProvvisoriRevocaResponse;
import it.giustizia.smp.webservice.client.polizia.DecretiSospensioneRestituzioneBeniRequest;
import it.giustizia.smp.webservice.client.polizia.DecretiSospensioneRestituzioneBeniResponse;
import it.giustizia.smp.webservice.client.polizia.DefinitivitaAggravamentoMisuraPersonaleRequest;
import it.giustizia.smp.webservice.client.polizia.DefinitivitaAggravamentoMisuraPersonaleResponse;
import it.giustizia.smp.webservice.client.polizia.DefinitivitaCorrezioneErroreDatiSoggettoRequest;
import it.giustizia.smp.webservice.client.polizia.DefinitivitaCorrezioneErroreDatiSoggettoResponse;
import it.giustizia.smp.webservice.client.polizia.DefinitivitaPatrimonialiRequest;
import it.giustizia.smp.webservice.client.polizia.DefinitivitaPatrimonialiResponse;
import it.giustizia.smp.webservice.client.polizia.DefinitivitaPersonaliRequest;
import it.giustizia.smp.webservice.client.polizia.DefinitivitaPersonaliResponse;
import it.giustizia.smp.webservice.client.polizia.DefinitivitaRevocaModificaMisuraPersonaleRequest;
import it.giustizia.smp.webservice.client.polizia.DefinitivitaRevocaModificaMisuraPersonaleResponse;
import it.giustizia.smp.webservice.client.polizia.Esito;
import it.giustizia.smp.webservice.client.polizia.IndaginiSoggettiPropostiRequest;
import it.giustizia.smp.webservice.client.polizia.IndaginiSoggettiPropostiResponse;
import it.giustizia.smp.webservice.client.polizia.IndaginiSoggettiPropostiTrCaRequest;
import it.giustizia.smp.webservice.client.polizia.IndaginiSoggettiPropostiTrCaResponse;
import it.giustizia.smp.webservice.client.polizia.ParereRevocaModificaMisurePersonaliRequest;
import it.giustizia.smp.webservice.client.polizia.ParereRevocaModificaMisurePersonaliResponse;
import it.giustizia.smp.webservice.client.polizia.PoliziaMsgErrorRequest;
import it.giustizia.smp.webservice.client.polizia.PoliziaMsgErrorResponse;
import it.giustizia.smp.webservice.client.polizia.ProposteMisurePatrimonialiRequest;
import it.giustizia.smp.webservice.client.polizia.ProposteMisurePatrimonialiResponse;
import it.giustizia.smp.webservice.client.polizia.ProposteMisurePersonaliRequest;
import it.giustizia.smp.webservice.client.polizia.ProposteMisurePersonaliResponse;
import it.giustizia.smp.webservice.client.polizia.RevocaSequestroRequest;
import it.giustizia.smp.webservice.client.polizia.RevocaSequestroResponse;
import it.giustizia.smp.webservice.client.polizia.RichiestaAggravamentoMisuraPersonaleRequest;
import it.giustizia.smp.webservice.client.polizia.RichiestaAggravamentoMisuraPersonaleResponse;
import it.giustizia.smp.webservice.client.polizia.RichiestaModificaRevocaMisuraPersonaleRequest;
import it.giustizia.smp.webservice.client.polizia.RichiestaModificaRevocaMisuraPersonaleResponse;
import it.giustizia.smp.webservice.client.polizia.SentenzaCassazionePatrimonialiRequest;
import it.giustizia.smp.webservice.client.polizia.SentenzaCassazionePatrimonialiResponse;
import it.giustizia.smp.webservice.client.polizia.SentenzaCassazionePersonaliRequest;
import it.giustizia.smp.webservice.client.polizia.SentenzaCassazionePersonaliResponse;
import it.giustizia.smp.webservice.client.polizia.SequestroRequest;
import it.giustizia.smp.webservice.client.polizia.SequestroResponse;
import it.giustizia.smp.webservice.client.polizia.SoggettiCoinvoltiPersonaFisicaType;
import it.giustizia.smp.webservice.client.polizia.SoggettiCoinvoltiType;
import it.giustizia.smp.webservice.client.polizia.VariazioneAnagraficheSoggettiInteressatiRequest;
import it.giustizia.smp.webservice.client.polizia.VariazioneAnagraficheSoggettiInteressatiResponse;
import it.giustizia.smp.webservice.client.polizia.VariazioneAnagraficiType;
import it.giustizia.smp.webservice.utility.JAXBUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author fi237710
 *
 */
@Transactional(rollbackFor = Exception.class)
public class PoliziaServiceImpl implements PoliziaService {

	@Autowired
	private PoliziaDao poliziaDao;
	
	@Autowired
	private PoliziaWsService poliziaServiceWS;

	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private AttoDao attoDao;
	
	@Autowired
	private ProcedimentoDao procedimentoDao;
	
	@Autowired
	private BeneDao beneDao;
	
	@Autowired
	private AnbscDao anbscDao;
	
	@Autowired
	private AnbscService anbscService;

	@Autowired
	private Mapper mapperBean;
	
	@Autowired
	private ProcedimentoDaoCam procedimentoDaoCam;
	
	@Autowired
	private SoggettoDao soggettoDao;
	
	private static DateFormat dfDate = new SimpleDateFormat("dd/MM/yyyy");
	
	public PoliziaDao getPoliziaDao() {
		return poliziaDao;
	}

	public void setPoliziaDao(PoliziaDao poliziaDao) {
		this.poliziaDao = poliziaDao;
	}

	public long insertPoliziaMsgDatabase(int idTipoServizio, long idProcedimento, long idAtto, Object msg, int statoMsg, long idAutorita, int ufficio, String descUtente) {
		byte[] xml = JAXBUtil.printXML(msg);
		
		Long idMessaggio = poliziaDao.insertPoliziaMsgDatabase(idTipoServizio, idProcedimento, idAtto, Utility.serializeInBlob(xml), statoMsg, idAutorita, ufficio, descUtente);

	   return idMessaggio.longValue(); 
	}

	public void updateRispostaMsgDatabase(long idMsg, Esito response) {
		if(response != null)
		{
			if(response.isEsito() == true)
			{
				int stato = 3;
				poliziaDao.updateRispostaMsgDatabase(idMsg, stato, null);
			}
			else
			{
				int stato = 1;
				poliziaDao.updateRispostaMsgDatabase(idMsg, stato, response.getErrordescription());
			}
		}
	}
	
	public TPoliziaMsg getTPoliziaMsgById(final long codiKIdMsg){
		return poliziaDao.getTPoliziaMsgById(codiKIdMsg);
	}
	
	public List<TPoliziaMsg> getDaInviare() {
		List queryResults = poliziaDao.getMessaggiDaInviare();
		List<TPoliziaMsg> list = null;
		if ((queryResults == null) || queryResults.isEmpty()) {
			return null;
		}
		else
			list = new ArrayList<TPoliziaMsg>();
		Iterator iterator = queryResults.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			TPoliziaMsg msg = (TPoliziaMsg) row.get("pol");
			list.add(msg);
		}
		return list;
	}
	
	//7.1
	public IndaginiSoggettiPropostiRequest createIndaginiSoggettiPropostiRequest(long idProcedimento, long idAtto, int uffOwner, int uffDest) {

		IndaginiSoggettiPropostiRequest request = new IndaginiSoggettiPropostiRequest();

		String num = null;
		String anno = null;
		
		DatiAutoritaIdType autoritaType = new DatiAutoritaIdType(); 
		autoritaType.setIDAUTORITA(BigInteger.valueOf(uffDest));

		request.setAutoritaTrasmittente(autoritaType);
		
		TProcedimento procRecord = poliziaDao.getProcedimento(idProcedimento, uffOwner);

		if(procRecord.getNumeAnnoProc() != null){
			num = procRecord.getNumeAnnoProc().toString();
		}
		
		if(procRecord.getNumeProc() != null){
			anno = procRecord.getNumeProc().toString();	
		}
		
		String descrNumProc =  formattaNumeroAnno(num, anno);

		DatiUfficioGiudiziarioType ufficioType = new DatiUfficioGiudiziarioType();
		ufficioType.setIDATTO(BigInteger.valueOf(idAtto));
		ufficioType.setIDUFFICIO(BigInteger.valueOf(uffOwner)); 
		ufficioType.setIDPROCEDIMENTO(BigInteger.valueOf(idProcedimento));
		ufficioType.setDESCRNUMPROC(descrNumProc);

		request.setUfficioGiudiziario(ufficioType);
		
		List<TRelProcSogint> listaSoggettiAtto = getTRelProcSogintList(idProcedimento, uffOwner);//poliziaDao.getTRelProcSogintList(idProcedimento, uffOwner);
		
		DatiPersonaGiuridicaType personaG = null;
		DatiPersonaFisicaType personaF = null;
		
		if(listaSoggettiAtto != null){
			
	    	Iterator<TRelProcSogint> itr = listaSoggettiAtto.iterator();
	        int counter = 0;
	    	while(itr.hasNext()) {
	    		counter ++;
	    		TRelProcSogint rowSogInt = itr.next();
	        	String tipoSogg =  poliziaDao.getTipoSoggetto(rowSogInt.getId().getCodiKIdSoggInt(),uffOwner);
	        	
	        	if(tipoSogg.equalsIgnoreCase(Constants.SOGGETTO_FISICO)){

	        		personaF = new DatiPersonaFisicaType();
	        		personaF.setNUMEPROG(BigInteger.valueOf(counter));
	        		
	        		//richiamo il metodo generalizzato per settare personafisicaType al netto del contatore
	        		setDatiPersonaFisicaType(personaF, rowSogInt, uffOwner);
	        		
	        		//aggiungo persona fisica all'array
	        		request.getPersonaFisicaIndagata().add(personaF);
	        	}
	        	
	        	if(tipoSogg.equalsIgnoreCase(Constants.SOGGETTO_GIURIDICO)){

	        		personaG = new DatiPersonaGiuridicaType();
	        		personaG.setNUMEPROG(BigInteger.valueOf(counter));
	        		
	        		setDatiPersonaGiuridicaType(personaG, rowSogInt, uffOwner);
	        		
	        		//aggiungo persona fisica all'array
	        		request.getPersonaGiuridicaIndagata().add(personaG);
	        	}
	        }
			
		}else{
			//se non ci sono soggetti aggiungo liste vuote per obbligatorietà
			 personaG = new DatiPersonaGiuridicaType();
     		 request.getPersonaGiuridicaIndagata().add(personaG);
			 personaF = new DatiPersonaFisicaType();
     		request.getPersonaFisicaIndagata().add(personaF);
		}
		
		return request;
	}

	public IndaginiSoggettiPropostiResponse inviaIndaginiSoggettiProposti(IndaginiSoggettiPropostiRequest request) {
		IndaginiSoggettiPropostiResponse response = poliziaServiceWS.inviaIndaginiSoggettiProposti(request);
		if(response != null)
			return response;
		else
			return null;
	}

	//8.1
	public ProposteMisurePersonaliRequest createProposteMisurePersonaliRequest(long idProcedimento, long idAtto, int uffOwner, int uffDest) {
		
		ProposteMisurePersonaliRequest request = new ProposteMisurePersonaliRequest();
		//autorità ricevente (questura associata al tribunale uffowner)
		DatiAutoritaIdType autoritaType = new DatiAutoritaIdType(); 
		
		autoritaType.setIDAUTORITA(BigInteger.valueOf(uffDest));		
		request.setAutoritaRicevente(autoritaType);
		
		String num = null;
		String anno = null;
		
		DatiAutoritaProponenteType autoritaPropType = new DatiAutoritaProponenteType(); 
		
		TAttoId attoId = new TAttoId(idAtto, uffOwner);
		TAtto attoRecord = attoDao.getAttoById(attoId);
		TIstanzaId istanzaId = new TIstanzaId(idAtto, uffOwner);
		TIstanza istanzaRecord = poliziaDao.getIstanzabyId(istanzaId);
		
		if(istanzaRecord.getNumeAnnoAtto() != null){
			num = istanzaRecord.getNumeAnnoAtto().toString();
		}
		
		if(istanzaRecord.getNumeAtto() != null){
			anno = istanzaRecord.getNumeAtto().toString();
		}
		
		String numProposta = formattaNumeroAnno(num, anno);
		
		TRelProcProc tRelProcProc = poliziaDao.getIdUffOwnerByProcAtto(idProcedimento, idAtto);
		autoritaPropType.setIDAUTORITA(BigInteger.valueOf(tRelProcProc.getTAnUfficio().getCodiKIdUfficio()));
		//autoritaPropType.setIDAUTORITA(BigInteger.valueOf(attoRecord.getId().getCodiKIdUffOwner()));
		autoritaPropType.setIDPROPOSTA(BigInteger.valueOf(attoRecord.getId().getCodiKIdAtto()));
		
		if(attoRecord.getDataEmissione() != null){
			autoritaPropType.setDATAEMISSIONE(dfDate.format(attoRecord.getDataEmissione()));
		}
		
		if(attoRecord.getDataDeposito() != null){
			autoritaPropType.setDATADEPOSITO(dfDate.format(attoRecord.getDataDeposito()));
		}
		
		autoritaPropType.setDESCRNUMPROPOSTA(numProposta);
		request.setAutoritaProponente(autoritaPropType);
		
		String numP = null;
		String annoP = null;
		
		DatiUfficioCompetenteType ufficioType = new DatiUfficioCompetenteType();
		
		TProcedimento procRecord = poliziaDao.getProcedimento(idProcedimento, uffOwner);
		
		if(procRecord.getNumeAnnoProc() != null){
			numP = procRecord.getNumeAnnoProc().toString();
		}
		
		if(procRecord.getNumeProc() != null){
			annoP = procRecord.getNumeProc().toString();
		}
		
		String descrNumProc = formattaNumeroAnno(numP, annoP);

		ufficioType.setIDUFFICIO(BigInteger.valueOf(uffOwner));
		ufficioType.setDESCRNUMPROC(descrNumProc);
		ufficioType.setIDPROCEDIMENTO(BigInteger.valueOf(idProcedimento));
		
		request.setTribunaleCompetente(ufficioType);
		
		//RECUPERARE PERSONE FISICHE ASSOCIATE AL PROCEDIMENTO/ATTO
		List<TRelProcSogint> listaSoggettiAtto = getTRelProcSogintList(idProcedimento, uffOwner);//poliziaDao.getTRelProcSogintList(idProcedimento, uffOwner);
		
		DatiSoggettoPersonaFisicaType personaF = null;
		DatiMisuraPersonaleType misuraP = null;
		
		if(listaSoggettiAtto != null){
			
	    	Iterator<TRelProcSogint> itr = listaSoggettiAtto.iterator();
	        int counter = 0;
	        
	        //il contatore delle misure deve essere progressivo generale e non per soggetto
	        int contaMisure = 0;
	        while(itr.hasNext()) {
	    		counter ++;
	    		TRelProcSogint rowSogInt = itr.next();
	    		//se idruolo = 1 verificare se il soggetto è persona fisica
	    		if(rowSogInt.getTTpTipoRuolo().getCodiKIdRuolo() == 1){ 
	    
	    			String tipoSogg =  poliziaDao.getTipoSoggetto(rowSogInt.getId().getCodiKIdSoggInt(),uffOwner);
	        	
	        		if(tipoSogg.equalsIgnoreCase(Constants.SOGGETTO_FISICO)){
	        		
		        		TPersonaFisica persF = poliziaDao.getPersonaFisica(rowSogInt.getId().getCodiKIdSoggInt(), rowSogInt.getTRelProcSogintVer().getId().getCodiKIdVer(), uffOwner);
		        		
		        		TTpTipoPrevProc tipoPrevProc = poliziaDao.getTipoPrevenzioneProc(rowSogInt.getTTpTipoPrevProc().getCodiKIdTipoNorm());
		        		
		        		//se la persona è soggetto fisico verifico che abbia misure personali associate
		        		List<TMisuraPers> misureSoggetto = poliziaDao.listaMisurePersonaliByIdSoggetto(persF.getId().getCodiKIdPersonaFis(), idProcedimento, idAtto, uffOwner);
		        		
		        		//se ci sono misure legate al soggetto aggiungo il soggetto e tutte le misure collegate.
		        		if (misureSoggetto !=null && misureSoggetto.size() > 0 ){ 
		        	    	Iterator<TMisuraPers> iter = misureSoggetto.iterator();

		        	    	while(iter.hasNext()) {
		        	    		contaMisure ++;
		        	    		
		        	    		misuraP = new DatiMisuraPersonaleType();
		        	    		
		        	    		TMisuraPers rowMisura = iter.next();
		        	    		
		        	    		misuraP.setNUMEPROGRMISPERS(BigInteger.valueOf(contaMisure));
		        	    		misuraP.setNUMEPROGPROP(BigInteger.valueOf(counter));
		        	    		
		        	    		if(tipoPrevProc.getTTpTipoPrevenzione() != null){
		        	    			misuraP.setIDTIPOPREV(""+tipoPrevProc.getTTpTipoPrevenzione().getCodiKIdTipoPrev());
		        	    		}
		        	    		
		        	    		if (rowSogInt.getTTpTipoPrevProc() != null){
		        	    			misuraP.setIDNORMATIVARIF(BigInteger.valueOf(rowSogInt.getTTpTipoPrevProc().getCodiKIdTipoNorm()));
		        	    		}
		        	    		
		        	    		//devo vedere quale misura personale è relativa al procedimento-atto-uffowner per aggiungere i dati corretti
		        				Set<TRelAttoMisPers> rowRelaz = rowMisura.getTRelAttoMisPerses();
		        				Iterator<TRelAttoMisPers> itRelAtto = rowRelaz.iterator();
		        				
		        				while(itRelAtto.hasNext()) {
		        					TRelAttoMisPers rowRelAtto = itRelAtto.next();
	        						
		        					if (rowRelAtto.getId().getCodiKIdProc() == idProcedimento && 
		        						rowRelAtto.getId().getCodiKIdAtto() == idAtto && 
		        						rowRelAtto.getId().getCodiKIdUffOwner() == uffOwner && 
		        						rowRelAtto.getId().getCodiKIdMisuraPers() == rowMisura.getId().getCodiKIdMisura()
		        						) {
		        						
		        						misuraP.setIDTIPOMISPERS(""+rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura());
		        				
		        						//correzione 19/10/2015
		        						if (rowRelAtto.getTAnNormative() != null){
		        							misuraP.setIDNORMMISPERS(""+rowRelAtto.getTAnNormative().getCodiKIdNormativa());
		        						}
		        						

		        						if(rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 1 || rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 2 || rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 3){
		        							misuraP.setDESCRMISPERS(getMisPersAnniMesiGiorni(rowRelAtto.getNumeDurataAnni(), rowRelAtto.getNumeDurataMesi(), rowRelAtto.getNumeDurataGiorni(), rowRelAtto.getDescLoc()));
		        							
		        						}else if (rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 25){
		        							
		        							if(rowRelAtto.getTCauzione() != null && rowRelAtto.getTCauzione().getId() != null && rowRelAtto.getTCauzione().getId().getCodiKIdCauzione() >0 ){
		        							TCauzioneId idCauzione = new TCauzioneId();
		        							idCauzione.setCodiKIdCauzione(rowRelAtto.getTCauzione().getId().getCodiKIdCauzione());
		        							idCauzione.setCodiKIdUffOwner(uffOwner);
		        							TCauzione cauzione = poliziaDao.getCauzionebyId(idCauzione);
		        							misuraP.setDESCRMISPERS(cauzione.getImpoCauzione().toString());
		        							}
		        						}
		        						request.getMisuraPersonale().add(misuraP);
		        					}
		        				}
		        				//request.getMisuraPersonale().add(misuraP);
		        	    	}
		        	    	
			        		personaF = new DatiSoggettoPersonaFisicaType();
			        		if(rowSogInt.getTTpTipoPrevProc() != null)
			        			personaF.setIDNORMATIVARIF(BigInteger.valueOf(rowSogInt.getTTpTipoPrevProc().getCodiKIdTipoNorm()));
			     
			        		personaF.setIDTIPOPREV(""+tipoPrevProc.getTTpTipoPrevenzione().getCodiKIdTipoPrev());
			        		
			        		personaF.setNUMEPROG(BigInteger.valueOf(counter));
			        		
			        		//personaF.setIDRUOLO(""+rowSogInt.getTTpTipoRuolo().getCodiKIdRuolo());
			        		
			        		personaF.setIDRUOLO(decodificaRuolo(rowSogInt.getTTpTipoRuolo().getCodiKIdRuolo()));
			        		
			        		personaF.setIDPERSONA(BigInteger.valueOf(persF.getId().getCodiKIdPersonaFis()));
			        		personaF.setDESCRCOGNOME(persF.getDescCognome());
			        		personaF.setDESCRNOME(persF.getDescNome());
			        		
			        		if(persF.getDataNascita() != null){
			        			personaF.setDATANASCITA(dfDate.format(persF.getDataNascita()));
			        		}
			        		
			        		if(persF.getTAnStatoByCodiFkIdStatoNasc() != null && persF.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato() > 0){
			        			TAnStato statoNasc = commonDao.getStatoById(persF.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato());
				        		personaF.setIDISTATSTATONAS(statoNasc.getDescIstatStato());
			        		}
			        		
			        		if(persF.getTAnComuniByCodiFkIdComNasc() != null && persF.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune() > 0){
			        			TAnComuni comuneNasc = commonDao.getComuneById(persF.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune());
				        		personaF.setIDISTATCOMUNENAS(comuneNasc.getDescIstatComune());
			        		}
			        		
			        		personaF.setDESCRCOMUNENAS(persF.getDescComEsteroNasc());
			        		
			        		if(persF.getFlgSesso() != null ){
			        			personaF.setFLGSESSO(persF.getFlgSesso().toString());
	        				}
			        		personaF.setDESCRCODICEFISC(persF.getDescCodiceFiscale());
			        		
			        		if(rowSogInt.getTTpProfessione() != null){
			        			personaF.setIDPROFESSIONE(""+rowSogInt.getTTpProfessione().getCodiKIdProfessione());
			        		}
			        		personaF.setDESCRCUI(persF.getDescCui());		        		
			        		
			        		if(rowSogInt.getTTpCondGiur() != null){
			        			personaF.setIDCONDIZIONEGIUR(""+rowSogInt.getTTpCondGiur().getCodiKIdCondGiur());
			        		}
			        		
			        		if(rowSogInt.getTAnStatoByCodiFkIdStatoRes() != null && rowSogInt.getTAnStatoByCodiFkIdStatoRes().getCodiKIdStato() > 0){
			        			TAnStato statoRes = commonDao.getStatoById(rowSogInt.getTAnStatoByCodiFkIdStatoRes().getCodiKIdStato());
				        		personaF.setIDISTATSTATORES(statoRes.getDescIstatStato());
			        		}
			        		
			        		if(rowSogInt.getTAnComuniByCodiFkIdComRes() != null && rowSogInt.getTAnComuniByCodiFkIdComRes().getCodiKIdComune() > 0){
			        			TAnComuni comuneRes = commonDao.getComuneById(rowSogInt.getTAnComuniByCodiFkIdComRes().getCodiKIdComune());
				        		personaF.setIDISTATCOMUNERES(comuneRes.getDescIstatComune());
			        		}
			        		
			        		personaF.setDESCRCOMUNERES(rowSogInt.getDescComEsteroRes());
			        		personaF.setDESCRINDIRIZZORES(rowSogInt.getDescIndirRes());
			        		personaF.setDESCRCIVICORES(rowSogInt.getDescCivicoRes());
			        		
			        		if(rowSogInt.getTAnStatoByCodiFkIdStatoDim() != null && rowSogInt.getTAnStatoByCodiFkIdStatoDim().getCodiKIdStato() > 0){
			        			TAnStato statoDim = commonDao.getStatoById(rowSogInt.getTAnStatoByCodiFkIdStatoDim().getCodiKIdStato());
				        		personaF.setIDISTATSTATODIM(statoDim.getDescIstatStato());
			        		}
			        		
			        		if(rowSogInt.getTAnComuniByCodiFkIdComDim() != null && rowSogInt.getTAnComuniByCodiFkIdComDim().getCodiKIdComune() > 0){
			        			TAnComuni comuneDim = commonDao.getComuneById(rowSogInt.getTAnComuniByCodiFkIdComDim().getCodiKIdComune());
				        		personaF.setIDISTATCOMUNEDIM(comuneDim.getDescIstatComune());
			        		}
			        		
			        		personaF.setDESCRCOMUNEDIM(rowSogInt.getDescComEsteroDim());
			        		personaF.setDESCRINDIRIZZODIM(rowSogInt.getDescIndirDim());
			        		personaF.setDESCRCIVICODIM(rowSogInt.getDescCivicoDim());

			        		if(rowSogInt.getTAnStatoByCodiFkIdStatoDomel() != null && rowSogInt.getTAnStatoByCodiFkIdStatoDomel().getCodiKIdStato() > 0){
			        			TAnStato statoDomel = commonDao.getStatoById(rowSogInt.getTAnStatoByCodiFkIdStatoDomel().getCodiKIdStato());
				        		personaF.setIDISTATSTATOELE(statoDomel.getDescIstatStato());
			        		}
			        		
			        		if(rowSogInt.getTAnComuniByCodiFkIdComDomel() != null && rowSogInt.getTAnComuniByCodiFkIdComDomel().getCodiKIdComune() > 0){
			        			TAnComuni comuneDomel = commonDao.getComuneById(rowSogInt.getTAnComuniByCodiFkIdComDomel().getCodiKIdComune());
				        		personaF.setIDISTATCOMUNEELE(comuneDomel.getDescIstatComune());
			        		}
			        		
			        		personaF.setDESCRCOMUNEELE(rowSogInt.getDescComEsteroDomel());
			        		personaF.setDESCRINDIRIZZOELE(rowSogInt.getDescIndirDomel());
			        		personaF.setDESCRCIVICOELE(rowSogInt.getDescCivicoDomel());
			        		
			        		if(persF.getTAnStatoByCodiFkIdStatoMorte() != null && persF.getTAnStatoByCodiFkIdStatoMorte().getCodiKIdStato() > 0){
			        			TAnStato statoDomel = commonDao.getStatoById(persF.getTAnStatoByCodiFkIdStatoMorte().getCodiKIdStato());
				        		personaF.setIDISTATSTATOMORTE(statoDomel.getDescIstatStato());
			        		}
			        		
			        		if(persF.getTAnComuniByCodiFkIdComMorte() != null && persF.getTAnComuniByCodiFkIdComMorte().getCodiKIdComune() > 0){
			        			TAnComuni comuneDomel = commonDao.getComuneById(persF.getTAnComuniByCodiFkIdComMorte().getCodiKIdComune());
				        		personaF.setIDISTATCOMUNEMORTE(comuneDomel.getDescIstatComune());
			        		}
			        		
			        		personaF.setDESCRCOMUNEMORTE(persF.getDescComEsteroMorte());
			        		
			        		if(persF.getDataDecesso() != null){
			        			personaF.setDATAMORTE(dfDate.format(persF.getDataDecesso()));
			        		}
			        		
			        		personaF.setDESCRCOGNOMEPADR(persF.getDescCogPadre());
			        		personaF.setDESCRNOMEPADR(persF.getDescNomePadre());
			        		personaF.setDESCRCOGNOMEMADR(persF.getDescCogMadre());
			        		personaF.setDESCRNOMEMADR(persF.getDescNomeMadre());
			        		
			        		//aggiungo persona fisica all'array di appoggio

			        		request.getSoggettiProposti().add(personaF);
		        		}  		
	        		}
	    		}
	        }
		}else{
			//se non ci sono persone associate aggiungo elenco vuoto per obbligatorietà
			
			 personaF = new DatiSoggettoPersonaFisicaType();
			 request.getSoggettiProposti().add(personaF);
			 misuraP = new DatiMisuraPersonaleType();
			 request.getMisuraPersonale().add(misuraP);
		}
		
		return request;
	}

	public ProposteMisurePersonaliResponse inviaProposteMisurePersonali(ProposteMisurePersonaliRequest request) {
		ProposteMisurePersonaliResponse response = poliziaServiceWS.inviaProposteMisurePersonali(request);
		if(response != null)
			return response;
		else
			return null;
	}

	//8.2
	public ProposteMisurePatrimonialiRequest createProposteMisurePatrimonialiRequest(long idProcedimento, long idAtto, int uffOwner, int uffDest) {
		ProposteMisurePatrimonialiRequest request = new ProposteMisurePatrimonialiRequest();
		//autorità ricevente (questura associata al tribunale uffowner)
		DatiAutoritaIdType autoritaType = new DatiAutoritaIdType(); 
		
		autoritaType.setIDAUTORITA(BigInteger.valueOf(uffDest));		
		request.setAutoritaRicevente(autoritaType);
		
		String num =  null;
		String anno = null;
		
		DatiAutoritaProponenteType autoritaPropType = new DatiAutoritaProponenteType(); 
		
		TAttoId attoId = new TAttoId(idAtto, uffOwner);
		TAtto attoRecord = attoDao.getAttoById(attoId);
		TIstanzaId istanzaId = new TIstanzaId(idAtto, uffOwner);
		TIstanza istanzaRecord = poliziaDao.getIstanzabyId(istanzaId);
		
		if(istanzaRecord.getNumeAnnoAtto() != null){
			num =  istanzaRecord.getNumeAnnoAtto().toString();
		}  
		if(istanzaRecord.getNumeAtto() != null){
			anno = istanzaRecord.getNumeAtto().toString();
		}
		
		TRelProcProc tRelProcProc = poliziaDao.getIdUffOwnerByProcAtto(idProcedimento, idAtto);
//		String numProposta = formattaNumeroAnno(num, anno);
		//autoritaPropType.setIDAUTORITA(BigInteger.valueOf(attoRecord.getId().getCodiKIdUffOwner()));
		autoritaPropType.setIDAUTORITA(BigInteger.valueOf(tRelProcProc.getTAnUfficio().getCodiKIdUfficio()));
		autoritaPropType.setIDPROPOSTA(BigInteger.valueOf(attoRecord.getId().getCodiKIdAtto()));
		if(attoRecord.getDataEmissione() != null){
			autoritaPropType.setDATAEMISSIONE(dfDate.format(attoRecord.getDataEmissione()));
		}
		if(attoRecord.getDataDeposito() != null){
			autoritaPropType.setDATADEPOSITO(dfDate.format(attoRecord.getDataDeposito()));
		}
//		autoritaPropType.setDATADEPOSITO(numProposta);
		request.setAutoritaProponente(autoritaPropType);
		
		DatiUfficioCompetenteType ufficioType = new DatiUfficioCompetenteType();
		
		TProcedimento procRecord = poliziaDao.getProcedimento(idProcedimento, uffOwner);
		
		String numP = null;
		String annoP = null;
		
		if(procRecord.getNumeAnnoProc() != null){
			numP = procRecord.getNumeAnnoProc().toString();
		}
		
		if(procRecord.getNumeProc() != null){
			annoP = procRecord.getNumeProc().toString();
		}
		
		String descrNumProc = formattaNumeroAnno(numP, annoP);

		ufficioType.setIDUFFICIO(BigInteger.valueOf(uffOwner));
		ufficioType.setDESCRNUMPROC(descrNumProc);
		ufficioType.setIDPROCEDIMENTO(BigInteger.valueOf(idProcedimento));
		
		request.setTribunaleCompetente(ufficioType);
		
		//RECUPERARE PERSONE FISICHE ASSOCIATE AL PROCEDIMENTO/ATTO
		List<TRelProcSogint> listaSoggettiAtto = getTRelProcSogintList(idProcedimento, uffOwner);//poliziaDao.getTRelProcSogintList(idProcedimento, uffOwner);
		
		if(listaSoggettiAtto != null){
			
	    	Iterator<TRelProcSogint> itr = listaSoggettiAtto.iterator();
	        int counterPersona = 0;
	        
	        //il contatore delle misure deve essere progressivo generale e non per soggetto
	        int contaBeni = 0;
	        long idBeneTemp = 0L;
	    	while(itr.hasNext()) {
	    		counterPersona ++;
	    		TRelProcSogint rowSogInt = itr.next();
	    		//se idruolo = 1 o 2 verificare se il soggetto è persona fisica
	    		if(rowSogInt.getTTpTipoRuolo().getCodiKIdRuolo() == 1 || rowSogInt.getTTpTipoRuolo().getCodiKIdRuolo() == 2){ 
	    
	    			String tipoSogg =  poliziaDao.getTipoSoggetto(rowSogInt.getId().getCodiKIdSoggInt(), uffOwner);
	        	
	        		if(tipoSogg.equalsIgnoreCase(Constants.SOGGETTO_FISICO)){
		
        				//setSoggettoFisicoMisurePatrimoniali(counter, request.getPersonaFisica(), request.getMisuraPatrimoniale(), rowSogInt, idProcedimento, idAtto, uffOwner);
	        		
	        			DatiSoggettoPersonaFisicaType personaF = null;
	        			
	        			DatiMisuraPatrimonialeType misuraPat = null;
	        			
	        			TPersonaFisica persF = poliziaDao.getPersonaFisica(rowSogInt.getId().getCodiKIdSoggInt(), rowSogInt.getTRelProcSogintVer().getId().getCodiKIdVer(), uffOwner);
	        			TTpTipoPrevProc tipoPrevProc = null;
	        			if(rowSogInt!=null && rowSogInt.getTTpTipoPrevProc()!=null && rowSogInt.getTTpTipoPrevProc().getCodiKIdTipoNorm()!=0)
	        				tipoPrevProc = poliziaDao.getTipoPrevenzioneProc(rowSogInt.getTTpTipoPrevProc().getCodiKIdTipoNorm());
	        			
	        			//se la persona è soggetto fisico verifico che abbia misure personali associate
	        			List<TRelAttoMisPatr> beniSoggetto = poliziaDao.listaRelAttoMisPatrSogInt(persF.getId().getCodiKIdPersonaFis(), idProcedimento, idAtto, uffOwner);
	        			
	        			//se ci sono misure legate al soggetto aggiungo il soggetto e tutte le misure collegate.
	        			if (beniSoggetto !=null && beniSoggetto.size() > 0 ){ 
	        		    	Iterator<TRelAttoMisPatr> iter = beniSoggetto.iterator();
	        		        
	        		    	while(iter.hasNext()) {
	        		    		misuraPat = new DatiMisuraPatrimonialeType();
	        		    		
	        		    		TRelAttoMisPatr rowMisura = iter.next();
	        		    		
	        		    		if(idBeneTemp != rowMisura.getId().getCodiKIdBene())
	        		    		{
	        		    			idBeneTemp = rowMisura.getId().getCodiKIdBene();
	        		    			contaBeni ++;
	        		    		}
	        		    		misuraPat.setNUMEPROGBENE(BigInteger.valueOf(contaBeni));
	        		    		
	        		    		misuraPat.setNUMEPROGPROP(BigInteger.valueOf(counterPersona));
	        		    		misuraPat.setIDBENE(""+rowMisura.getId().getCodiKIdBene());

	        		    		TRelBeneAtto rowRelBeneAtto = beneDao.getTipoInteressePerSoggettoInteressato(idProcedimento,idAtto, uffOwner, rowMisura.getId().getCodiKIdBene(), rowMisura.getId().getCodiKIdSoggTit());
	        		    		TBene rowBene = beneDao.getTBeneById(rowMisura.getId().getCodiKIdBene(), uffOwner);

	        		    		if(rowBene.getTTpCatBene() != null){
	        			    		misuraPat.setIDCATBENE(""+rowBene.getTTpCatBene().getCodiKIdCatBene());	    			
	        		    		}

	        		    		if(rowBene.getTTpSottocatBene() != null){
	        		    			misuraPat.setIDSUBCATBENE(""+rowBene.getTTpSottocatBene().getCodiKIdSottocat());
	        		    		}
	        		    		
	        		    		misuraPat.setDESCRBENE(rowBene.getDescrBene());
	        		    		
	        		    		if(rowBene.getTTpTipoBene() != null)
	        		    			misuraPat.setIDTIPOBENE(""+rowBene.getTTpTipoBene().getCodiKIdTipoBene());
	        		    		
	        		    		if(rowRelBeneAtto.getId() != null){
	        		    			misuraPat.setNUMEPROGTITOL(BigInteger.valueOf(rowRelBeneAtto.getId().getCodiKIdSoggTit()));
	        		    		}
	        		    		
	        		    		if(rowRelBeneAtto.getTTpTipoInteresse() != null){
	        		    			misuraPat.setIDTIPOINTERESSE(""+rowRelBeneAtto.getTTpTipoInteresse().getCodiKIdTipoInt());
	        		    		}
	        		    		if(rowRelBeneAtto.getNumeQuota() != null){
	        		    			misuraPat.setNUMEQUOTA(""+rowRelBeneAtto.getNumeQuota());
	        		    		}
	        		    		if(tipoPrevProc!=null && tipoPrevProc.getTTpTipoPrevenzione() != null){
	        		    			misuraPat.setIDTIPOPREV(""+tipoPrevProc.getTTpTipoPrevenzione().getCodiKIdTipoPrev());
	        		    		}
	        		    		if(rowSogInt.getTTpTipoPrevProc() != null){
	        		    			misuraPat.setIDNORMATIVARIF(BigInteger.valueOf(rowSogInt.getTTpTipoPrevProc().getCodiKIdTipoNorm()));
	        		    		}
	        		    		if(rowRelBeneAtto.getTTpTipoMisura() != null){
	        		    			misuraPat.setIDTIPOMISPATR(""+rowRelBeneAtto.getTTpTipoMisura().getCodiKIdTipoMisura());
	        		    		}
	        		    		if(rowRelBeneAtto.getTAnNormative() != null){
	        		    			misuraPat.setIDNORMMISPATR(""+rowRelBeneAtto.getTAnNormative().getCodiKIdNormativa());
	        		    		}
	        		    		
	        		    		request.getMisuraPatrimoniale().add(misuraPat);	    		
	        				}

	        		    }
	        		    	
	        	    		personaF = new DatiSoggettoPersonaFisicaType();
	        	    		
	        	    		if(rowSogInt.getTTpTipoPrevProc() != null){
	        	    			personaF.setIDNORMATIVARIF(BigInteger.valueOf(rowSogInt.getTTpTipoPrevProc().getCodiKIdTipoNorm()));
	        	    		}

	        	    		if(tipoPrevProc!=null && tipoPrevProc.getTTpTipoPrevenzione() != null){
	        	    			personaF.setIDTIPOPREV(""+tipoPrevProc.getTTpTipoPrevenzione().getCodiKIdTipoPrev());
	        	    		}
	        	    		
	        	    		personaF.setNUMEPROG(BigInteger.valueOf(counterPersona));
	        	    		
	        	    		if(rowSogInt.getTTpTipoRuolo() != null){
	        	    		personaF.setIDRUOLO(decodificaRuolo(rowSogInt.getTTpTipoRuolo().getCodiKIdRuolo()));
	        				}
	        	    		
	        	    		personaF.setIDPERSONA(BigInteger.valueOf(persF.getId().getCodiKIdPersonaFis()));
	        	    		personaF.setDESCRCOGNOME(persF.getDescCognome());
	        	    		personaF.setDESCRNOME(persF.getDescNome());
	        	    		
	        	    		if(persF.getDataNascita() != null){
	        	    			personaF.setDATANASCITA(dfDate.format(persF.getDataNascita()));
	        	    		}
	        	    		
	        	    		if(persF.getTAnStatoByCodiFkIdStatoNasc() != null && persF.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato() > 0){
	        	    			TAnStato statoNasc = commonDao.getStatoById(persF.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato());
	        	        		personaF.setIDISTATSTATONAS(statoNasc.getDescIstatStato());
	        	    		}
	        	    		
	        	    		if(persF.getTAnComuniByCodiFkIdComNasc() != null && persF.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune() > 0){
	        	    			TAnComuni comuneNasc = commonDao.getComuneById(persF.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune());
	        	        		personaF.setIDISTATCOMUNENAS(comuneNasc.getDescIstatComune());
	        	    		}
	        	    		
	        	    		personaF.setDESCRCOMUNENAS(persF.getDescComEsteroNasc());
	        	    		
	        	    		if(persF.getFlgSesso() != null){
	        	    			personaF.setFLGSESSO(persF.getFlgSesso().toString());
	        	    		}
	        	    		
	        	    		personaF.setDESCRCODICEFISC(persF.getDescCodiceFiscale());
	        	    		
	        	    		if(rowSogInt.getTTpProfessione() != null && rowSogInt.getTTpProfessione().getCodiKIdProfessione() > 0){
	        	    			personaF.setIDPROFESSIONE(""+rowSogInt.getTTpProfessione().getCodiKIdProfessione());
	        	    		}
	        	    		
	        	    		personaF.setDESCRCUI(persF.getDescCui());
	        	    		
	        	    		if(rowSogInt.getTTpCondGiur() != null && rowSogInt.getTTpCondGiur().getCodiKIdCondGiur() > 0) {
	        	    				personaF.setIDCONDIZIONEGIUR(""+rowSogInt.getTTpCondGiur().getCodiKIdCondGiur());
	        	    		}
	        	    		
	        	    		if(rowSogInt.getTAnStatoByCodiFkIdStatoRes() != null && rowSogInt.getTAnStatoByCodiFkIdStatoRes().getCodiKIdStato() > 0){
	        	    			TAnStato statoRes = commonDao.getStatoById(rowSogInt.getTAnStatoByCodiFkIdStatoRes().getCodiKIdStato());
	        	        		personaF.setIDISTATSTATORES(statoRes.getDescIstatStato());
	        	    		}
	        	    		
	        	    		if(rowSogInt.getTAnComuniByCodiFkIdComRes() != null && rowSogInt.getTAnComuniByCodiFkIdComRes().getCodiKIdComune() > 0){
	        	    			TAnComuni comuneRes = commonDao.getComuneById(rowSogInt.getTAnComuniByCodiFkIdComRes().getCodiKIdComune());
	        	        		personaF.setIDISTATCOMUNERES(comuneRes.getDescIstatComune());
	        	    		}
	        	    		
	        	    		personaF.setDESCRCOMUNERES(rowSogInt.getDescComEsteroRes());
	        	    		personaF.setDESCRINDIRIZZORES(rowSogInt.getDescIndirRes());
	        	    		personaF.setDESCRCIVICORES(rowSogInt.getDescCivicoRes());
	        	    		
	        	    		if(rowSogInt.getTAnStatoByCodiFkIdStatoDim() != null && rowSogInt.getTAnStatoByCodiFkIdStatoDim().getCodiKIdStato() > 0){
	        	    			TAnStato statoDim = commonDao.getStatoById(rowSogInt.getTAnStatoByCodiFkIdStatoDim().getCodiKIdStato());
	        	        		personaF.setIDISTATSTATODIM(statoDim.getDescIstatStato());
	        	    		}
	        	    		
	        	    		if(rowSogInt.getTAnComuniByCodiFkIdComDim() != null && rowSogInt.getTAnComuniByCodiFkIdComDim().getCodiKIdComune() > 0){
	        	    			TAnComuni comuneDim = commonDao.getComuneById(rowSogInt.getTAnComuniByCodiFkIdComDim().getCodiKIdComune());
	        	        		personaF.setIDISTATCOMUNEDIM(comuneDim.getDescIstatComune());
	        	    		}
	        	    		
	        	    		personaF.setDESCRCOMUNEDIM(rowSogInt.getDescComEsteroDim());
	        	    		personaF.setDESCRINDIRIZZODIM(rowSogInt.getDescIndirDim());
	        	    		personaF.setDESCRCIVICODIM(rowSogInt.getDescCivicoDim());

	        	    		if(rowSogInt.getTAnStatoByCodiFkIdStatoDomel() != null && rowSogInt.getTAnStatoByCodiFkIdStatoDomel().getCodiKIdStato() > 0){
	        	    			TAnStato statoDomel = commonDao.getStatoById(rowSogInt.getTAnStatoByCodiFkIdStatoDomel().getCodiKIdStato());
	        	        		personaF.setIDISTATSTATOELE(statoDomel.getDescIstatStato());
	        	    		}
	        	    		
	        	    		if(rowSogInt.getTAnComuniByCodiFkIdComDomel() != null && rowSogInt.getTAnComuniByCodiFkIdComDomel().getCodiKIdComune() > 0){
	        	    			TAnComuni comuneDomel = commonDao.getComuneById(rowSogInt.getTAnComuniByCodiFkIdComDomel().getCodiKIdComune());
	        	        		personaF.setIDISTATCOMUNEELE(comuneDomel.getDescIstatComune());
	        	    		}
	        	    		
	        	    		personaF.setDESCRCOMUNEELE(rowSogInt.getDescComEsteroDomel());
	        	    		personaF.setDESCRINDIRIZZOELE(rowSogInt.getDescIndirDomel());
	        	    		personaF.setDESCRCIVICOELE(rowSogInt.getDescCivicoDomel());
	        	    		
	        	    		if(persF.getTAnStatoByCodiFkIdStatoMorte() != null && persF.getTAnStatoByCodiFkIdStatoMorte().getCodiKIdStato() > 0){
	        	    			TAnStato statoDomel = commonDao.getStatoById(persF.getTAnStatoByCodiFkIdStatoMorte().getCodiKIdStato());
	        	        		personaF.setIDISTATSTATOMORTE(statoDomel.getDescIstatStato());
	        	    		}
	        	    		
	        	    		if(persF.getTAnComuniByCodiFkIdComMorte() != null && persF.getTAnComuniByCodiFkIdComMorte().getCodiKIdComune() > 0){
	        	    			TAnComuni comuneDomel = commonDao.getComuneById(persF.getTAnComuniByCodiFkIdComMorte().getCodiKIdComune());
	        	        		personaF.setIDISTATCOMUNEMORTE(comuneDomel.getDescIstatComune());
	        	    		}
	        	    		
	        	    		personaF.setDESCRCOMUNEMORTE(persF.getDescComEsteroMorte());
	        	    		
	        	    		if(persF.getDataDecesso() != null){
	        	    			personaF.setDATAMORTE(dfDate.format(persF.getDataDecesso()));
	        	    		}
	        	    		
	        	    		personaF.setDESCRCOGNOMEPADR(persF.getDescCogPadre());
	        	    		personaF.setDESCRNOMEPADR(persF.getDescNomePadre());
	        	    		personaF.setDESCRCOGNOMEMADR(persF.getDescCogMadre());
	        	    		personaF.setDESCRNOMEMADR(persF.getDescNomeMadre());
	        	    		
	        	    		//aggiungo persona fisica alla request

	        	    		request.getPersonaFisica().add(personaF);
	        			
	        			
	        			
	        		}else if(tipoSogg.equalsIgnoreCase(Constants.SOGGETTO_GIURIDICO)) {
	        			
	        			DatiSoggettoPersonaGiuridicaType personaG = null;
	        			
	        			DatiMisuraPatrimonialeType misuraPat = null;
	        			
	        			TPersonaGiur persG = poliziaDao.getPersonaGiuridica(rowSogInt.getId().getCodiKIdSoggInt(), rowSogInt.getTRelProcSogintVer().getId().getCodiKIdVer(), uffOwner);
	        			
	        			TTpTipoPrevProc tipoPrevProc = poliziaDao.getTipoPrevenzioneProc(rowSogInt.getTTpTipoPrevProc().getCodiKIdTipoNorm());
	        			
	        			//se la persona è soggetto giuridico verifico che abbia misure patrimoniali associate
	        			List<TRelAttoMisPatr> beniSoggetto = beneDao.listaRelAttoMisPatr(persG.getId().getCodiKIdPersGiur(), idProcedimento, uffOwner);
	        			
	        			//se ci sono misure legate al soggetto aggiungo il soggetto e tutte le misure collegate.
	        			if (beniSoggetto !=null && beniSoggetto.size() > 0 ){ 
	        		    	Iterator<TRelAttoMisPatr> iter = beniSoggetto.iterator();
//	        		        int contaMisure = 0;
	        		        
	        		    	while(iter.hasNext()) {
//	        		    		contaMisure ++;
	        		    		
	        		    		misuraPat = new DatiMisuraPatrimonialeType();
	        		    		
	        		    		TRelAttoMisPatr rowMisura = iter.next();
	        		    		
	        		    		if(idBeneTemp != rowMisura.getId().getCodiKIdBene())
	        		    		{
	        		    			idBeneTemp = rowMisura.getId().getCodiKIdBene();
	        		    			contaBeni ++;
	        		    		}
	        		    		misuraPat.setNUMEPROGBENE(BigInteger.valueOf(contaBeni));
	        		    		
	        		    		misuraPat.setNUMEPROGPROP(BigInteger.valueOf(counterPersona));
	        		    		misuraPat.setIDBENE(""+rowMisura.getId().getCodiKIdBene());
	        		
	        		    		TRelBeneAtto rowRelBeneAtto = beneDao.getTipoInteressePerSoggettoInteressato(idProcedimento,idAtto, uffOwner, rowMisura.getId().getCodiKIdBene(), rowMisura.getId().getCodiKIdSoggTit());
	        		    		TBene rowBene = beneDao.getTBeneById(rowMisura.getId().getCodiKIdBene(), uffOwner);
	        		    		
	        		    		if(rowBene.getTTpCatBene() != null){
	        		    			misuraPat.setIDCATBENE(""+rowBene.getTTpCatBene().getCodiKIdCatBene());
	        		    		}
	        		    		
	        		    		if(rowBene.getTTpSottocatBene() != null){
	        		    			misuraPat.setIDSUBCATBENE(""+rowBene.getTTpSottocatBene().getCodiKIdSottocat());
	        		    		}
	        		    		
	        		    		misuraPat.setDESCRBENE(rowBene.getDescrBene());
	        		    		
	        		    		if(rowBene.getTTpTipoBene() != null)
	        		    			misuraPat.setIDTIPOBENE(""+rowBene.getTTpTipoBene().getCodiKIdTipoBene());
	        		    		
	        		    		if(rowRelBeneAtto.getId() != null){
	        		    			misuraPat.setNUMEPROGTITOL(BigInteger.valueOf(rowRelBeneAtto.getId().getCodiKIdSoggTit()));
	        		    		}
	        		    		
	        		    		if(rowRelBeneAtto.getTTpTipoInteresse() != null){
	        		    			misuraPat.setIDTIPOINTERESSE(""+rowRelBeneAtto.getTTpTipoInteresse().getCodiKIdTipoInt());
	        		    		}
	        		    		
	        		    		misuraPat.setNUMEQUOTA(""+rowRelBeneAtto.getNumeQuota());
	        		    		
	        		    		if(tipoPrevProc.getTTpTipoPrevenzione() != null){
	        		    			misuraPat.setIDTIPOPREV(""+tipoPrevProc.getTTpTipoPrevenzione().getCodiKIdTipoPrev());
	        		    		}
	        		    		
	        		    		if(rowSogInt.getTTpTipoPrevProc() != null){
	        			    		misuraPat.setIDNORMATIVARIF(BigInteger.valueOf(rowSogInt.getTTpTipoPrevProc().getCodiKIdTipoNorm()));	    			
	        		    		}

	        		    		if(rowRelBeneAtto.getTTpTipoMisura() != null){
	        		    			misuraPat.setIDTIPOMISPATR(""+rowRelBeneAtto.getTTpTipoMisura().getCodiKIdTipoMisura());
	        		    		}
	        		    		
	        		    		if(rowRelBeneAtto.getTAnNormative() != null){
	        		    			misuraPat.setIDNORMMISPATR(""+rowRelBeneAtto.getTAnNormative().getCodiKIdNormativa());
	        		    		}

	        		    		request.getMisuraPatrimoniale().add(misuraPat);
	        		    	}

	        		    }
	        		    	
	        				personaG = new DatiSoggettoPersonaGiuridicaType();
	        		
	        				personaG.setNUMEPROG(BigInteger.valueOf(counterPersona));
	        				personaG.setIDRUOLO(decodificaRuolo(rowSogInt.getTTpTipoRuolo().getCodiKIdRuolo()));
	        				personaG.setIDPERSONAG(BigInteger.valueOf(persG.getId().getCodiKIdPersGiur()));
	        				
	        				if(tipoPrevProc.getTTpTipoPrevenzione() != null){
	        				personaG.setIDTIPOPREV(""+tipoPrevProc.getTTpTipoPrevenzione().getCodiKIdTipoPrev());
	        				}
	        				if(rowSogInt.getTTpTipoPrevProc() != null){
	        					personaG.setIDNORMATIVARIF(BigInteger.valueOf(rowSogInt.getTTpTipoPrevProc().getCodiKIdTipoNorm()));
	        				}
	        				if(persG.getTTpTipoSocieta() != null){
	        				personaG.setIDTIPOSOCIETA(BigInteger.valueOf(persG.getTTpTipoSocieta().getCodiKIdTipoSoc()));
	        				}
	        				personaG.setDESCRSOCIALE(persG.getDescRagioneSoc());
	        				personaG.setDESCRNUMCCIAA(persG.getDescNumeCciaa());
	        				personaG.setDESCRPARTITAIVA(persG.getDescPartitaIva());			
	        				
	        				if(persG.getTAnStato() != null && persG.getTAnStato().getCodiKIdStato() > 0){
	        					TAnStato statoNasc = commonDao.getStatoById(persG.getTAnStato().getCodiKIdStato());
	        					personaG.setIDISTATSTATO(statoNasc.getDescIstatStato());
	        				}
	        				
	        				if(persG.getTAnComuni() != null && persG.getTAnComuni().getCodiKIdComune() > 0){
	        					TAnComuni comuneNasc = commonDao.getComuneById(persG.getTAnComuni().getCodiKIdComune());
	        					personaG.setIDISTATCOMUNE(comuneNasc.getDescIstatComune());
	        				}
	        				
	        				personaG.setDESCRCOMUNE(persG.getDescComEsteroSede());
	        				personaG.setDESCRINDIRIZZO(persG.getDescIndirizzoSede());
	        				personaG.setDESCRCIVICO(persG.getDescCivico());
	        				personaG.setDESCRRAPPRSOC(persG.getDescRapprSoc());			
	        				personaG.setDESCRAMMINISTR(persG.getDescAmmin());
	        				
	        				if(persG.getImpoCapSoc() != null ){
	        					personaG.setIMPOCAPITALESOC(""+persG.getImpoCapSoc());
	        				}
	        				
	        				if(persG.getTTpSettoreAtt() != null && persG.getTTpSettoreAtt().getCodiKIdSettoreAtt() > 0){
	        					personaG.setIDSETTOREATT(""+persG.getTTpSettoreAtt().getCodiKIdSettoreAtt());
	        				}
	        				
	        				personaG.setDESCRNOTE(""+persG.getDescNote());
	        				
	        				request.getPersonaGiuridica().add(personaG);
	        			
	        		}
	    		}
	    	}
		}
		return request;
	}

	public ProposteMisurePatrimonialiResponse inviaProposteMisurePatrimoniali(ProposteMisurePatrimonialiRequest request) {
		ProposteMisurePatrimonialiResponse response = poliziaServiceWS.inviaProposteMisurePatrimoniali(request);
		if(response != null)
			return response;
		else
			return null;
	}

	//8.3
	public IndaginiSoggettiPropostiTrCaRequest createIndaginiSoggettiPropostiTrCaRequest(long idProcedimento, long idAtto, int uffOwner, int uffDest) {

		IndaginiSoggettiPropostiTrCaRequest request = new IndaginiSoggettiPropostiTrCaRequest();

		String num = null;
		String anno = null;
		
		DatiAutoritaIdType autoritaType = new DatiAutoritaIdType(); 
		autoritaType.setIDAUTORITA(BigInteger.valueOf(uffDest));

		request.setAutoritaDelegata(autoritaType);
		
		TProcedimento procRecord = poliziaDao.getProcedimento(idProcedimento, uffOwner);

		if(procRecord.getNumeAnnoProc() != null){
			num = procRecord.getNumeAnnoProc().toString();
		}
		
		if(procRecord.getNumeProc() != null){
			anno = procRecord.getNumeProc().toString();	
		}
		
		String descrNumProc =  formattaNumeroAnno(num, anno);

		DatiUfficioGiudiziarioType ufficioType = new DatiUfficioGiudiziarioType();
		ufficioType.setIDATTO(BigInteger.valueOf(idAtto));
		ufficioType.setIDUFFICIO(BigInteger.valueOf(uffOwner)); 
		ufficioType.setIDPROCEDIMENTO(BigInteger.valueOf(idProcedimento));
		ufficioType.setDESCRNUMPROC(descrNumProc);

		request.setUfficioDelegante(ufficioType);
		
		List<TRelProcSogint> listaSoggettiAtto = getTRelProcSogintList(idProcedimento, uffOwner);//poliziaDao.getTRelProcSogintList(idProcedimento, uffOwner);
		
		DatiPersonaGiuridicaType personaG = null;
		DatiPersonaFisicaType personaF = null;
		
		if(listaSoggettiAtto != null){
			
	    	Iterator<TRelProcSogint> itr = listaSoggettiAtto.iterator();
	        int counter = 0;
	    	while(itr.hasNext()) {
	    		counter ++;
	    		TRelProcSogint rowSogInt = (TRelProcSogint)itr.next();
	        	String tipoSogg =  poliziaDao.getTipoSoggetto(rowSogInt.getId().getCodiKIdSoggInt(), uffOwner);
	        	
	        	if(tipoSogg.equalsIgnoreCase(Constants.SOGGETTO_FISICO)){

	        		personaF = new DatiPersonaFisicaType();
	        		personaF.setNUMEPROG(BigInteger.valueOf(counter));
	        		
	        		//richiamo il metodo generalizzato per settare personafisicaType al netto del contatore
	        		setDatiPersonaFisicaType(personaF, rowSogInt, uffOwner);
	        		
	        		//aggiungo persona fisica all'array
	        		request.getPersonaFisicaIndagata().add(personaF);
	        	}
	        	
	        	if(tipoSogg.equalsIgnoreCase(Constants.SOGGETTO_GIURIDICO)){

	        		personaG = new DatiPersonaGiuridicaType();
	        		personaG.setNUMEPROG(BigInteger.valueOf(counter));
	        		
	        		setDatiPersonaGiuridicaType(personaG, rowSogInt, uffOwner);
	        		
	        		//aggiungo persona fisica all'array
	        		request.getPersonaGiuridicaIndagata().add(personaG);
	        	}
	        }
			
		}else{
			//se non ci sono soggetti aggiungo liste vuote per obbligatorietà
			 personaG = new DatiPersonaGiuridicaType();
     		 request.getPersonaGiuridicaIndagata().add(personaG);
			 personaF = new DatiPersonaFisicaType();
     		 request.getPersonaFisicaIndagata().add(personaF);
		}
		
		return request;
	}

	public IndaginiSoggettiPropostiTrCaResponse inviaIndaginiSoggettiPropostiTrCa(IndaginiSoggettiPropostiTrCaRequest request) {
		
		IndaginiSoggettiPropostiTrCaResponse response = poliziaServiceWS.inviaIndaginiSoggettiPropostiTrCa(request);
		
		if(response != null)
			return response;
		else
			return null;
	}
	
	//8.4
	public ParereRevocaModificaMisurePersonaliRequest createParereRevocaModificaMisurePersonaliRequest(long idProcedimento, long idAtto, int uffOwner, int uffDest) {
		ParereRevocaModificaMisurePersonaliRequest request = new ParereRevocaModificaMisurePersonaliRequest();
		
		String num = null;
		String anno = null;
		
		DatiAutoritaIdType autoritaType = new DatiAutoritaIdType();
		autoritaType.setIDAUTORITA(BigInteger.valueOf(uffDest));
		request.setAutoritaParere(autoritaType);
		
		TProcedimento procRecord = poliziaDao.getProcedimento(idProcedimento, uffOwner);

		if(procRecord.getNumeAnnoProc() != null){
			num = procRecord.getNumeAnnoProc().toString();
		}
		
		if(procRecord.getNumeProc() != null){
			anno = procRecord.getNumeProc().toString();	
		}
		
		String descrNumProc =  formattaNumeroAnno(num, anno);
		
		DatiUfficioGiudiziarioType ufficioType = new DatiUfficioGiudiziarioType();
		ufficioType.setIDATTO(BigInteger.valueOf(idAtto));
		ufficioType.setIDUFFICIO(BigInteger.valueOf(uffOwner)); 
		ufficioType.setIDPROCEDIMENTO(BigInteger.valueOf(idProcedimento));
		ufficioType.setDESCRNUMPROC(descrNumProc);

		request.setUfficioParere(ufficioType);
		
		TAttoId attoId = new TAttoId(idAtto, uffOwner);
		TAtto attoRecord = attoDao.getAttoById(attoId);
		
		DatiParereType parereType = new DatiParereType();
		if(attoRecord.getDescNote()!=null && attoRecord.getDescNote().length()>0)
			parereType.setDESCRRICHPARERE(attoRecord.getDescNote());
		else
			parereType.setDESCRRICHPARERE("");
		parereType.setDESCRNOTE("");
		request.setParere(parereType);
		
		DatiPersonaFisicaType personaF = null;
		
		List<TRelProcSogint> listaSoggettiAtto = getTRelProcSogintList(idProcedimento, uffOwner);//poliziaDao.getTRelProcSogintList(idProcedimento, uffOwner);
		
		if(listaSoggettiAtto != null){
			
	    	Iterator<TRelProcSogint> itr = listaSoggettiAtto.iterator();
	        int counter = 0;
	    	while(itr.hasNext()) {
	    		counter ++;
	    		TRelProcSogint rowSogInt = itr.next();
	        	String tipoSogg =  poliziaDao.getTipoSoggetto(rowSogInt.getId().getCodiKIdSoggInt(), uffOwner);
	        	
	        	if(tipoSogg.equalsIgnoreCase(Constants.SOGGETTO_FISICO)){

	        		personaF = new DatiPersonaFisicaType();
	        		personaF.setNUMEPROG(BigInteger.valueOf(counter));
	        		
	        		//richiamo il metodo generalizzato per settare personafisicaType al netto del contatore
	        		setDatiPersonaFisicaType(personaF, rowSogInt, uffOwner);
	        		
	        		//aggiungo persona fisica all'array
	        		request.getSoggettoSottoposto().add(personaF);
	        	}
	        }
		}
		
		return request;
	}
	
	public ParereRevocaModificaMisurePersonaliResponse inviaParereRevocaModificaMisurePersonali(ParereRevocaModificaMisurePersonaliRequest request) {
		ParereRevocaModificaMisurePersonaliResponse response = poliziaServiceWS.inviaParereRevocaModificaMisurePersonali(request);
		if(response != null)
			return response;
		else
			return null;
	}

	//8.5
	public VariazioneAnagraficheSoggettiInteressatiRequest createVariazioneAnagraficheSoggettiInteressatiRequest(long idProcedimento, long idSoggetto, int idVerOld, int idVerNew, int uffOwner, int uffDest) {

		VariazioneAnagraficheSoggettiInteressatiRequest request = new VariazioneAnagraficheSoggettiInteressatiRequest(); 
		
		String num = null;
		String anno = null;
		
		DatiAutoritaIdType autoritaType = new DatiAutoritaIdType(); 
		autoritaType.setIDAUTORITA(BigInteger.valueOf(uffDest));		
		request.setAutoritaRicevente(autoritaType);
		
		DatiUfficioCompetenteType ufficioType = new DatiUfficioCompetenteType();
		
		TProcedimento procRecord = poliziaDao.getProcedimento(idProcedimento, uffOwner);
		if(procRecord.getNumeAnnoProc() != null){
			num = procRecord.getNumeAnnoProc().toString(); 
		}
		
		if(procRecord.getNumeProc() != null){
			anno = procRecord.getNumeProc().toString(); 
		}
		String descrNumProc = formattaNumeroAnno(num, anno);

		ufficioType.setIDUFFICIO(BigInteger.valueOf(uffOwner));
		ufficioType.setDESCRNUMPROC(descrNumProc);
		ufficioType.setIDPROCEDIMENTO(BigInteger.valueOf(idProcedimento));
		request.setUfficioDetermina(ufficioType);
		
		VariazioneAnagraficiType varAnagType = new VariazioneAnagraficiType();
		
		TPersonaFisica persFOld = poliziaDao.getPersonaFisica(idSoggetto, idVerOld, uffOwner);
		TPersonaFisica persFNew = poliziaDao.getPersonaFisica(idSoggetto, idVerNew, uffOwner);
		
		varAnagType.setIDPERSONAF(BigInteger.valueOf(idSoggetto));

		//soggetto old
		varAnagType.setDESCRCOGNOMEOLD(persFOld.getDescCognome());
		varAnagType.setDESCRNOMEOLD(persFOld.getDescNome());
		
		if(persFOld.getDataNascita() != null){
			varAnagType.setDATANASCITAOLD(dfDate.format(persFOld.getDataNascita()));
		}
		
		if(persFOld.getTAnStatoByCodiFkIdStatoNasc() != null && persFOld.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato() > 0){
			TAnStato statoNasc = commonDao.getStatoById(persFOld.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato());
			varAnagType.setIDISTATSTATONASOLD(statoNasc.getDescIstatStato());
		}
		
		if(persFOld.getTAnComuniByCodiFkIdComNasc() != null && persFOld.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune() > 0){
			TAnComuni comuneNasc = commonDao.getComuneById(persFOld.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune());
			varAnagType.setIDISTATCOMUNENASOLD(comuneNasc.getDescIstatComune());
		}
		
		//soggetto new
		varAnagType.setDESCRCOMUNENASNEW(persFNew.getDescComEsteroNasc());
		varAnagType.setDESCRCOGNOMENEW(persFNew.getDescCognome());
		varAnagType.setDESCRNOMENEW(persFNew.getDescNome());
		
		if(persFNew.getDataNascita() != null){
			varAnagType.setDATANASCITANEW(dfDate.format(persFNew.getDataNascita()));
		}
		
		if(persFNew.getTAnStatoByCodiFkIdStatoNasc() != null && persFNew.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato() > 0){
			TAnStato statoNasc = commonDao.getStatoById(persFNew.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato());
			varAnagType.setIDISTATSTATONASNEW(statoNasc.getDescIstatStato());
		}
		
		if(persFNew.getTAnComuniByCodiFkIdComNasc() != null && persFNew.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune() > 0){
			TAnComuni comuneNasc = commonDao.getComuneById(persFNew.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune());
			varAnagType.setIDISTATCOMUNENASNEW(comuneNasc.getDescIstatComune());
		}
		
		varAnagType.setDESCRCOMUNENASNEW(persFNew.getDescComEsteroNasc());
		request.setSoggettoVariazione(varAnagType);
		return request;
	}
	
	public VariazioneAnagraficheSoggettiInteressatiResponse inviaVariazioneAnagraficheSoggettiInteressati(VariazioneAnagraficheSoggettiInteressatiRequest request) {
		VariazioneAnagraficheSoggettiInteressatiResponse response = poliziaServiceWS.inviaVariazioneAnagraficheSoggettiInteressati(request);
		if(response != null)
			return response;
		else
			return null;
	}

	//8.6 - 1
	public SequestroRequest createSequestroRequest(long idProcedimento,	long idAtto, Integer uffOwner, Integer uffDestinatario) {
		SequestroRequest request = new SequestroRequest();
		
		//autorita
		DatiAutoritaIdType autoritaType = new DatiAutoritaIdType(); 
		autoritaType.setIDAUTORITA(BigInteger.valueOf(uffDestinatario));		
		request.setAutoritaRicevente(autoritaType);
		
		//ufficio competente
		DatiUfficioCompetenteType ufficioType = new DatiUfficioCompetenteType();
		ufficioType.setIDUFFICIO(BigInteger.valueOf(uffOwner));
		TProcedimento procRecord = poliziaDao.getProcedimento(idProcedimento, uffOwner);
		String numeProc = "";
		String annoProc = "";
		if(procRecord.getNumeAnnoProc() != null){
			numeProc = procRecord.getNumeAnnoProc().toString(); 
		}
		if(procRecord.getNumeProc() != null){
			annoProc = procRecord.getNumeProc().toString(); 
		}
		String descrNumProc = formattaNumeroAnno(numeProc, annoProc);
		ufficioType.setDESCRNUMPROC(descrNumProc);
		ufficioType.setIDPROCEDIMENTO(BigInteger.valueOf(idProcedimento));
		request.setUfficioEmittente(ufficioType);
		
		//decreto
		DatiDecretoType decretoType = new DatiDecretoType();
		String numD = null;
		String annoD = null;
		TAttoId attoId = new TAttoId();
	    attoId.setCodiKIdAtto(idAtto);
	    attoId.setCodiKIdUffOwner(uffOwner);
		TAtto attoRecord = attoDao.getAttoById(attoId);
		TDecreto decrRecord = attoDao.getTDecreto(idAtto, uffOwner);
		decretoType.setIDDECRETO(BigInteger.valueOf(idAtto));
		if(decrRecord != null && decrRecord.getNumeAnnoAtto() != null){
			numD = decrRecord.getNumeAnnoAtto().toString(); 
		}
		if(decrRecord != null && decrRecord.getNumeAtto() != null){
			annoD = decrRecord.getNumeAtto().toString(); 
		}
		String descrNumDecr = formattaNumeroAnno(numD, annoD);
		if(attoRecord.getDataEmissione() != null){
			decretoType.setDATAEMISDECRETO(dfDate.format(attoRecord.getDataEmissione()));
		}
		if(attoRecord.getDataDeposito() != null){
			decretoType.setDATADEPDECRETO(dfDate.format(attoRecord.getDataDeposito()));
		}
		decretoType.setDESCRNUMDECRETO(descrNumDecr);
		request.setDecreto(decretoType);
		
		//beni
		List<DatiBeneType> beniTypeList = new ArrayList<DatiBeneType>();
		List<Bene> beniList = anbscService.getBeni(idProcedimento, idAtto);
		
			//soggetti
			List<SoggettiCoinvoltiType> soggettiCoinvoltiTypeList = new ArrayList<SoggettiCoinvoltiType>();
			List<Soggetto> soggettiList = anbscService.getSoggetti(uffOwner, idProcedimento, idAtto, beniList, null);
			SoggettiCoinvoltiType soggCoinv = null;
			DatiAnagraficiPersonaFisicaType personaF = null;
			DatiAnagraficiPersonaGiuridicaType personaG = null;
			if(soggettiList != null)
			{
				Iterator<Soggetto> itr = soggettiList.iterator();
				int counter = 0;
		    	while(itr.hasNext()) {
		    		counter ++;
		    		Soggetto rowSog = itr.next();
		    		soggCoinv = new  SoggettiCoinvoltiType();
		    		soggCoinv.setNUMEPROG(BigInteger.valueOf(counter));
		    		soggCoinv.setIDSOGGETTO(rowSog.getIdentificativoSoggetto());
		    		soggCoinv.setIDTIPOSOGGETTO(rowSog.getTipoPersona());
		    		if(rowSog.getTipoPersona().equalsIgnoreCase(Constants.SOGGETTO_FISICO))
		    		{
		    			personaF = new DatiAnagraficiPersonaFisicaType();
		    			List<TRelProcSogint> rowSogInt = anbscDao.getTRelProcSogintList(idProcedimento,rowSog.getIdentificativoSoggetto().longValue(),uffOwner);
	        			setDatiAnagraficiPersonaFisicaType(personaF, rowSogInt.get(0), uffOwner);
	        			List<DatiAnagraficiPersonaFisicaType> datiAnagraficiPersonaFisica = new ArrayList<DatiAnagraficiPersonaFisicaType>();
	        			datiAnagraficiPersonaFisica.add(personaF);
	        			soggCoinv.setDatiAnagraficiPersonaFisica(datiAnagraficiPersonaFisica);
	        			if(rowSogInt.get(0).getTTpTipoPrevProc() != null)
	        				soggCoinv.setIDNORMATIVA(BigInteger.valueOf(rowSogInt.get(0).getTTpTipoPrevProc().getCodiKIdTipoNorm()));
	        		}
		    		else if(rowSog.getTipoPersona().equalsIgnoreCase(Constants.SOGGETTO_GIURIDICO))
		    		{
		    			personaG = new DatiAnagraficiPersonaGiuridicaType();
		    			List<TRelProcSogint> rowSogInt = anbscDao.getTRelProcSogintList(idProcedimento,rowSog.getIdentificativoSoggetto().longValue(),uffOwner);
	        			setDatiAnagraficiPersonaGiuridicaType(personaG, rowSogInt.get(0), uffOwner);
	        			List<DatiAnagraficiPersonaGiuridicaType> datiAnagraficiPersonaGiuridica = new ArrayList<DatiAnagraficiPersonaGiuridicaType>();
	        			datiAnagraficiPersonaGiuridica.add(personaG);
	        			soggCoinv.setDatiAnagraficiPersonaGiuridica(datiAnagraficiPersonaGiuridica);
	        			if(rowSogInt.get(0).getTTpTipoPrevProc() != null)
	        				soggCoinv.setIDNORMATIVA(BigInteger.valueOf(rowSogInt.get(0).getTTpTipoPrevProc().getCodiKIdTipoNorm()));
	        		}
		    		soggettiCoinvoltiTypeList.add(soggCoinv);
		    	}
			}
			request.setSoggettiCoinvolti(soggettiCoinvoltiTypeList);
			//Fine soggetti
			
		DatiBeneType beneType = null;
		List<DatiMisuraPatrimonialeType> misuraPatrimonialeTypeList = new ArrayList<DatiMisuraPatrimonialeType>();
		for(int i=0; i<beniList.size();i++)
		{
			Bene bene = beniList.get(i);
			beneType = new DatiBeneType();
			beneType.setNUMEPROGBENE(BigInteger.valueOf(i+1));
			beneType.setIDBENE(bene.getIdentificativoBene().toString());
			beneType.setIDTIPOBENE(bene.getTipologia().toString());
			beneType.setIDCATBENE(bene.getCategoria().toString());
			if(bene.getSottocategoria() != null)
				beneType.setIDSUBCATBENE(bene.getSottocategoria().toString());
			TBene tBene = anbscDao.getBene(bene.getIdentificativoBene().longValue());
			if(tBene!= null && tBene.getDescrBene()!=null)
				beneType.setDESCRBENE(tBene.getDescrBene());
			beniTypeList.add(beneType);
			//Misure
			List<TRelAttoMisPatr> misureList = anbscDao.getSoggettoTitolare(idProcedimento, idAtto, bene.getIdentificativoBene().longValue() ,uffOwner);
//			List<DatiMisuraPatrimonialeType> misuraPatrimonialeTypeList = new ArrayList<DatiMisuraPatrimonialeType>();
			if (misureList !=null && misureList.size() > 0 ){ 
		    	Iterator<TRelAttoMisPatr> iter = misureList.iterator();
		        int contaMisure = 0;
		    	while(iter.hasNext()) 
		    	{
		    		contaMisure ++;
		    		DatiMisuraPatrimonialeType misuraPat = new DatiMisuraPatrimonialeType();
		    		TRelAttoMisPatr rowMisura = iter.next();
		    		
//		    		misuraPat.setNUMEPROGBENE(BigInteger.valueOf(contaMisure));
		    		misuraPat.setNUMEPROGBENE(BigInteger.valueOf(i+1));
		    		
		    		misuraPat.setIDBENE(""+rowMisura.getId().getCodiKIdBene());
		    		if(bene.getCategoria() != null){
		    			misuraPat.setIDCATBENE(""+bene.getCategoria());
		    		}
		    		if(bene.getSottocategoria() != null){
		    			misuraPat.setIDSUBCATBENE(""+bene.getSottocategoria());
		    		}
		    		if(bene.getTipologia() != null)
		    			misuraPat.setIDTIPOBENE(bene.getTipologia().toString());
		    		
		    		misuraPat.setDESCRBENE(beneType.getDESCRBENE());
		    		TRelBeneAtto rowRelBeneAtto = beneDao.getTipoInteressePerSoggettoInteressato(idProcedimento,idAtto, uffOwner, rowMisura.getId().getCodiKIdBene(), rowMisura.getId().getCodiKIdSoggTit());
		    		if(rowRelBeneAtto.getTTpTipoInteresse() != null){
		    			misuraPat.setIDTIPOINTERESSE(""+rowRelBeneAtto.getTTpTipoInteresse().getCodiKIdTipoInt());
		    		}
		    		misuraPat.setNUMEQUOTA(""+rowRelBeneAtto.getNumeQuota());
		    		long soggettoProposto = rowMisura.getId().getCodiKIdSoggInt();
		    		long soggettoTitolare = rowMisura.getId().getCodiKIdSoggTit();
		    		for(int j=0; j<soggettiCoinvoltiTypeList.size(); j++)
		    		{
		    			if(soggettiCoinvoltiTypeList.get(j).getIDSOGGETTO().longValue() == soggettoProposto)
		    				misuraPat.setNUMEPROGPROP(soggettiCoinvoltiTypeList.get(j).getNUMEPROG());
		    			if(soggettiCoinvoltiTypeList.get(j).getIDSOGGETTO().longValue() == soggettoTitolare)
		    				misuraPat.setNUMEPROGTITOL(soggettiCoinvoltiTypeList.get(j).getNUMEPROG());
		    			TRelProcSogint soggettoAtto = poliziaDao.getTRelProcSogint(idProcedimento, soggettoTitolare, uffOwner);
		    			if(soggettoAtto.getTTpTipoPrevProc() != null)
		    			{
			    			TTpTipoPrevProc tipoPrevProc = poliziaDao.getTipoPrevenzioneProc(soggettoAtto.getTTpTipoPrevProc().getCodiKIdTipoNorm());
				    		if(tipoPrevProc.getTTpTipoPrevenzione() != null){
				    			misuraPat.setIDTIPOPREV(""+tipoPrevProc.getTTpTipoPrevenzione().getCodiKIdTipoPrev());
				    		}
		    			}
		    		}
		    		if(rowRelBeneAtto.getTTpTipoMisura() != null){
		    			misuraPat.setIDTIPOMISPATR(""+rowRelBeneAtto.getTTpTipoMisura().getCodiKIdTipoMisura());
		    		}
		    		if(rowRelBeneAtto.getTAnNormative() != null){
		    			misuraPat.setIDNORMMISPATR(""+rowRelBeneAtto.getTAnNormative().getCodiKIdNormativa());
		    		}
		    		misuraPatrimonialeTypeList.add(misuraPat);
		    	}
			}
//			request.setMisuraPatrimoniale(misuraPatrimonialeTypeList);
			//End Misure
		}
		request.setBene(beniTypeList);
		request.setMisuraPatrimoniale(misuraPatrimonialeTypeList);

		return request;
	}
	
	public SequestroResponse inviaSequestro(SequestroRequest request) {
		SequestroResponse response = poliziaServiceWS.inviaSequestro(request);
		if(response != null)
			return response;
		else
			return null;
	}

	//8.6 - 2
	public RevocaSequestroRequest createRevocaSequestroRequest(long idProcedimento,	long idAtto, Integer uffOwner, Integer uffDestinatario) {
		RevocaSequestroRequest request = new RevocaSequestroRequest();
		
		//autorita
		DatiAutoritaIdType autoritaType = new DatiAutoritaIdType(); 
		autoritaType.setIDAUTORITA(BigInteger.valueOf(uffDestinatario));		
		request.setAutoritaRicevente(autoritaType);
		
		//ufficio competente
		DatiUfficioCompetenteType ufficioType = new DatiUfficioCompetenteType();
		ufficioType.setIDUFFICIO(BigInteger.valueOf(uffOwner));
		TProcedimento procRecord = poliziaDao.getProcedimento(idProcedimento, uffOwner);
		String numeProc = "";
		String annoProc = "";
		if(procRecord.getNumeAnnoProc() != null){
			numeProc = procRecord.getNumeAnnoProc().toString(); 
		}
		if(procRecord.getNumeProc() != null){
			annoProc = procRecord.getNumeProc().toString(); 
		}
		String descrNumProc = formattaNumeroAnno(numeProc, annoProc);
		ufficioType.setDESCRNUMPROC(descrNumProc);
		ufficioType.setIDPROCEDIMENTO(BigInteger.valueOf(idProcedimento));
		request.setUfficioEmittente(ufficioType);
		
		//decreto
		DatiDecretoType decretoType = new DatiDecretoType();
		String numD = null;
		String annoD = null;
		TAttoId attoId = new TAttoId();
	    attoId.setCodiKIdAtto(idAtto);
	    attoId.setCodiKIdUffOwner(uffOwner);
		TAtto attoRecord = attoDao.getAttoById(attoId);
		TDecreto decrRecord = attoDao.getTDecreto(idAtto, uffOwner);
		decretoType.setIDDECRETO(BigInteger.valueOf(idAtto));
		if(decrRecord != null && decrRecord.getNumeAnnoAtto() != null){
			numD = decrRecord.getNumeAnnoAtto().toString(); 
		}
		if(decrRecord != null && decrRecord.getNumeAtto() != null){
			annoD = decrRecord.getNumeAtto().toString(); 
		}
		String descrNumDecr = formattaNumeroAnno(numD, annoD);
		if(attoRecord.getDataEmissione() != null){
			decretoType.setDATAEMISDECRETO(dfDate.format(attoRecord.getDataEmissione()));
		}
		if(attoRecord.getDataDeposito() != null){
			decretoType.setDATADEPDECRETO(dfDate.format(attoRecord.getDataDeposito()));
		}
		decretoType.setDESCRNUMDECRETO(descrNumDecr);
		request.setDecreto(decretoType);
		
		//beni
		List<DatiBeneType> beniTypeList = new ArrayList<DatiBeneType>();
		List<Bene> beniList = anbscService.getBeni(idProcedimento, idAtto);
		
		//soggetti
		List<SoggettiCoinvoltiType> soggettiCoinvoltiTypeList = new ArrayList<SoggettiCoinvoltiType>();
		List<Soggetto> soggettiList = anbscService.getSoggetti(uffOwner, idProcedimento, idAtto, beniList, null);
		SoggettiCoinvoltiType soggCoinv = null;
		DatiAnagraficiPersonaFisicaType personaF = null;
		DatiAnagraficiPersonaGiuridicaType personaG = null;
		if(soggettiList != null)
		{
			Iterator<Soggetto> itr = soggettiList.iterator();
			int counter = 0;
	    	while(itr.hasNext()) {
	    		counter ++;
	    		Soggetto rowSog = itr.next();
	    		soggCoinv = new  SoggettiCoinvoltiType();
	    		soggCoinv.setNUMEPROG(BigInteger.valueOf(counter));
	    		soggCoinv.setIDSOGGETTO(rowSog.getIdentificativoSoggetto());
	    		soggCoinv.setIDTIPOSOGGETTO(rowSog.getTipoPersona());
	    		if(rowSog.getTipoPersona().equalsIgnoreCase(Constants.SOGGETTO_FISICO))
	    		{
	    			personaF = new DatiAnagraficiPersonaFisicaType();
	    			List<TRelProcSogint> rowSogInt = anbscDao.getTRelProcSogintList(idProcedimento,rowSog.getIdentificativoSoggetto().longValue(),uffOwner);
        			setDatiAnagraficiPersonaFisicaType(personaF, rowSogInt.get(0), uffOwner);
        			List<DatiAnagraficiPersonaFisicaType> datiAnagraficiPersonaFisica = new ArrayList<DatiAnagraficiPersonaFisicaType>();
        			datiAnagraficiPersonaFisica.add(personaF);
        			soggCoinv.setDatiAnagraficiPersonaFisica(datiAnagraficiPersonaFisica);
        			if(rowSogInt.get(0).getTTpTipoPrevProc() != null)
        				soggCoinv.setIDNORMATIVA(BigInteger.valueOf(rowSogInt.get(0).getTTpTipoPrevProc().getCodiKIdTipoNorm()));
        		}
	    		else if(rowSog.getTipoPersona().equalsIgnoreCase(Constants.SOGGETTO_GIURIDICO))
	    		{
	    			personaG = new DatiAnagraficiPersonaGiuridicaType();
	    			List<TRelProcSogint> rowSogInt = anbscDao.getTRelProcSogintList(idProcedimento,rowSog.getIdentificativoSoggetto().longValue(),uffOwner);
        			setDatiAnagraficiPersonaGiuridicaType(personaG, rowSogInt.get(0), uffOwner);
        			List<DatiAnagraficiPersonaGiuridicaType> datiAnagraficiPersonaGiuridica = new ArrayList<DatiAnagraficiPersonaGiuridicaType>();
        			datiAnagraficiPersonaGiuridica.add(personaG);
        			soggCoinv.setDatiAnagraficiPersonaGiuridica(datiAnagraficiPersonaGiuridica);
        			if(rowSogInt.get(0).getTTpTipoPrevProc() != null)
        				soggCoinv.setIDNORMATIVA(BigInteger.valueOf(rowSogInt.get(0).getTTpTipoPrevProc().getCodiKIdTipoNorm()));
        		}
	    		soggettiCoinvoltiTypeList.add(soggCoinv);
	    	}
		}
		request.setSoggettiCoinvolti(soggettiCoinvoltiTypeList);
		//Fine soggetti
			
		DatiBeneType beneType = null;
		List<DatiMisuraPatrimonialeType> misuraPatrimonialeTypeList = new ArrayList<DatiMisuraPatrimonialeType>();
		for(int i=0; i<beniList.size();i++)
		{
			Bene bene = beniList.get(i);
			beneType = new DatiBeneType();
			beneType.setNUMEPROGBENE(BigInteger.valueOf(i+1));
			beneType.setIDBENE(bene.getIdentificativoBene().toString());
			beneType.setIDTIPOBENE(bene.getTipologia().toString());
			beneType.setIDCATBENE(bene.getCategoria().toString());
			if(bene.getSottocategoria() != null)
				beneType.setIDSUBCATBENE(bene.getSottocategoria().toString());
			TBene tBene = anbscDao.getBene(bene.getIdentificativoBene().longValue());
			if(tBene!= null && tBene.getDescrBene()!=null)
				beneType.setDESCRBENE(tBene.getDescrBene());
			beniTypeList.add(beneType);
			//Misure
			List<TRelAttoMisPatr> misureList = anbscDao.getSoggettoTitolare(idProcedimento, idAtto, bene.getIdentificativoBene().longValue() ,uffOwner);
			
			if (misureList !=null && misureList.size() > 0 ){
		    	Iterator<TRelAttoMisPatr> iter = misureList.iterator();
		        int contaMisure = 0;
		    	while(iter.hasNext()) 
		    	{
		    		contaMisure ++;
		    		DatiMisuraPatrimonialeType misuraPat = new DatiMisuraPatrimonialeType();
		    		TRelAttoMisPatr rowMisura = iter.next();
		    		
//		    		misuraPat.setNUMEPROGBENE(BigInteger.valueOf(contaMisure));
		    		misuraPat.setNUMEPROGBENE(BigInteger.valueOf(i+1));
		    		
		    		misuraPat.setIDBENE(""+rowMisura.getId().getCodiKIdBene());
		    		if(bene.getCategoria() != null){
		    			misuraPat.setIDCATBENE(""+bene.getCategoria());
		    		}
		    		if(bene.getSottocategoria() != null){
		    			misuraPat.setIDSUBCATBENE(""+bene.getSottocategoria());
		    		}
		    		if(bene.getTipologia() != null)
		    			misuraPat.setIDTIPOBENE(bene.getTipologia().toString());
		    		
		    		misuraPat.setDESCRBENE(beneType.getDESCRBENE());
		    		TRelBeneAtto rowRelBeneAtto = beneDao.getTipoInteressePerSoggettoInteressato(idProcedimento,idAtto, uffOwner, rowMisura.getId().getCodiKIdBene(), rowMisura.getId().getCodiKIdSoggTit());
		    		if(rowRelBeneAtto.getTTpTipoInteresse() != null){
		    			misuraPat.setIDTIPOINTERESSE(""+rowRelBeneAtto.getTTpTipoInteresse().getCodiKIdTipoInt());
		    		}
		    		misuraPat.setNUMEQUOTA(""+rowRelBeneAtto.getNumeQuota());
		    		long soggettoProposto = rowMisura.getId().getCodiKIdSoggInt();
		    		long soggettoTitolare = rowMisura.getId().getCodiKIdSoggTit();
		    		for(int j=0; j<soggettiCoinvoltiTypeList.size(); j++)
		    		{
		    			if(soggettiCoinvoltiTypeList.get(j).getIDSOGGETTO().longValue() == soggettoProposto)
		    				misuraPat.setNUMEPROGPROP(soggettiCoinvoltiTypeList.get(j).getNUMEPROG());
		    			if(soggettiCoinvoltiTypeList.get(j).getIDSOGGETTO().longValue() == soggettoTitolare)
		    				misuraPat.setNUMEPROGTITOL(soggettiCoinvoltiTypeList.get(j).getNUMEPROG());
		    			TRelProcSogint soggettoAtto = poliziaDao.getTRelProcSogint(idProcedimento, soggettoTitolare, uffOwner);
		    			if(soggettoAtto.getTTpTipoPrevProc() != null)
		    			{
			    			TTpTipoPrevProc tipoPrevProc = poliziaDao.getTipoPrevenzioneProc(soggettoAtto.getTTpTipoPrevProc().getCodiKIdTipoNorm());
				    		if(tipoPrevProc.getTTpTipoPrevenzione() != null){
				    			misuraPat.setIDTIPOPREV(""+tipoPrevProc.getTTpTipoPrevenzione().getCodiKIdTipoPrev());
				    		}
		    			}
		    		}
		    		if(rowRelBeneAtto.getTTpTipoMisura() != null){
		    			misuraPat.setIDTIPOMISPATR(""+rowRelBeneAtto.getTTpTipoMisura().getCodiKIdTipoMisura());
		    		}
		    		if(rowRelBeneAtto.getTAnNormative() != null){
		    			misuraPat.setIDNORMMISPATR(""+rowRelBeneAtto.getTAnNormative().getCodiKIdNormativa());
		    		}
		    		misuraPatrimonialeTypeList.add(misuraPat);
		    	}
			}
			
			//End Misure
		}
		request.setBene(beniTypeList);
		request.setMisuraPatrimoniale(misuraPatrimonialeTypeList);
		return request;
	}
	
	public RevocaSequestroResponse inviaRevocaSequestro(RevocaSequestroRequest request) {
		RevocaSequestroResponse response = poliziaServiceWS.inviaRevocaSequestro(request);
		if(response != null)
			return response;
		else
			return null;
	}

	//8.7
	public DecretiSospensioneRestituzioneBeniRequest createDecretiSospensioneRestituzioneBeniRequest(long idProcedimento, long idAtto, int uffOwner, int uffDest) {
		DecretiSospensioneRestituzioneBeniRequest request = new DecretiSospensioneRestituzioneBeniRequest();
		
		String num = null;
		String anno = null;
		
		DatiAutoritaIdType autoritaType = new DatiAutoritaIdType(); 
		autoritaType.setIDAUTORITA(BigInteger.valueOf(uffDest));		
		request.setAutoritaRicevente(autoritaType);
		
		DatiUfficioCompetenteType ufficioType = new DatiUfficioCompetenteType();
		
		TProcedimento procRecord = poliziaDao.getProcedimento(idProcedimento, uffOwner);
		if(procRecord.getNumeAnnoProc() != null){
			num = procRecord.getNumeAnnoProc().toString(); 
		}
		
		if(procRecord.getNumeProc() != null){
			anno = procRecord.getNumeProc().toString(); 
		}
		
		String descrNumProc = formattaNumeroAnno(num, anno);

		ufficioType.setIDUFFICIO(BigInteger.valueOf(uffOwner));
		ufficioType.setDESCRNUMPROC(descrNumProc);
		ufficioType.setIDPROCEDIMENTO(BigInteger.valueOf(idProcedimento));
		request.setUfficioEmittente(ufficioType);
		
		DatiDecretoType decretoType = new DatiDecretoType();
		TDecreto decrRecord = poliziaDao.getDecreto(idAtto, uffOwner);
		TAttoId attoId = new TAttoId(idAtto, uffOwner);
		TAtto attoRecord = attoDao.getAttoById(attoId);
		
	    String numD = null;
	    String annoD = null;
	    
		decretoType.setIDDECRETO(BigInteger.valueOf(idAtto));
		
		if(decrRecord != null && decrRecord.getNumeAnnoAtto() != null){
			numD = decrRecord.getNumeAnnoAtto().toString(); 
		}
		
		if(decrRecord != null && decrRecord.getNumeAtto() != null){
			annoD = decrRecord.getNumeAtto().toString(); 
		}
		
		String descrNumDecr = formattaNumeroAnno(numD, annoD);
		
		decretoType.setDESCRNUMDECRETO(descrNumDecr);
		
	    if(attoRecord.getDataDeposito() != null){
			decretoType.setDATADEPDECRETO(dfDate.format(attoRecord.getDataDeposito()));
		}
		
		if(attoRecord.getDataEmissione() != null){
			decretoType.setDATAEMISDECRETO(dfDate.format(attoRecord.getDataEmissione()));
		}
	
		request.setDecreto(decretoType);
		
		List<Bene> beniList = anbscService.getBeni(idProcedimento, idAtto);
		
		DatiBeneType beneType = null;
		
		for(int i=0; i<beniList.size();i++)	{
			Bene bene = beniList.get(i);
			beneType = new DatiBeneType();
			beneType.setNUMEPROGBENE(BigInteger.valueOf(i+1));
			beneType.setIDBENE(bene.getIdentificativoBene().toString());
			beneType.setIDTIPOBENE(bene.getTipologia().toString());
			beneType.setIDCATBENE(bene.getCategoria().toString());
			if(bene.getSottocategoria() != null)
				beneType.setIDSUBCATBENE(bene.getSottocategoria().toString());
			TBene tBene = anbscDao.getBene(bene.getIdentificativoBene().longValue());
			if(tBene!= null && tBene.getDescrBene()!=null)
				beneType.setDESCRBENE(tBene.getDescrBene());
			request.getBene().add(beneType);
		}
		
		//RECUPERARE PERSONE FISICHE ASSOCIATE AL PROCEDIMENTO/ATTO
		List<TRelProcSogint> listaSoggettiAtto = getTRelProcSogintList(idProcedimento, uffOwner);//poliziaDao.getTRelProcSogintList(idProcedimento, uffOwner);
		
		SoggettiCoinvoltiType soggCoinv = null;
		
		DatiAnagraficiPersonaFisicaType personaF = null;
		
		DatiAnagraficiPersonaGiuridicaType personaG = null;
		
		if(listaSoggettiAtto != null){
			
	    	Iterator<TRelProcSogint> itr = listaSoggettiAtto.iterator();
	        int counter = 0;
	    	while(itr.hasNext()) {
	    		counter ++;
	    		TRelProcSogint rowSogInt = itr.next();

	    		if(rowSogInt.getTTpTipoRuolo().getCodiKIdRuolo() == 1 || rowSogInt.getTTpTipoRuolo().getCodiKIdRuolo() == 2){ 
	    			
	    			soggCoinv = new  SoggettiCoinvoltiType();
	    			soggCoinv.setNUMEPROG(BigInteger.valueOf(counter));
	    			
	    			String tipoSogg =  poliziaDao.getTipoSoggetto(rowSogInt.getId().getCodiKIdSoggInt(), uffOwner);

	    			soggCoinv.setIDSOGGETTO(BigInteger.valueOf(rowSogInt.getId().getCodiKIdSoggInt()));
	    			soggCoinv.setIDTIPOSOGGETTO(tipoSogg);
	    			
	        		if(tipoSogg.equalsIgnoreCase(Constants.SOGGETTO_FISICO)){
	        			
	        			personaF = new DatiAnagraficiPersonaFisicaType();
	        			setDatiAnagraficiPersonaFisicaType(personaF, rowSogInt, uffOwner);
	        			setDatiMisuraPatrimonialePersonaType(request.getMisuraPatrimoniale(), counter, rowSogInt.getId().getCodiKIdSoggInt(),idProcedimento, idAtto, rowSogInt, uffOwner);
	        			//correzione 16/10
	        			soggCoinv.getDatiAnagraficiPersonaFisica().add(personaF);
	        			request.getSoggettiCoinvolti().add(soggCoinv);
	        			
	        		}else if(tipoSogg.equalsIgnoreCase(Constants.SOGGETTO_GIURIDICO)) {
	        			
	        			personaG = new DatiAnagraficiPersonaGiuridicaType();
	        			setDatiAnagraficiPersonaGiuridicaType(personaG, rowSogInt, uffOwner);
	        			setDatiMisuraPatrimonialePersonaType(request.getMisuraPatrimoniale(), counter, rowSogInt.getId().getCodiKIdSoggInt(),idProcedimento, idAtto, rowSogInt, uffOwner);
	        			//correzione 16/10
	        			soggCoinv.getDatiAnagraficiPersonaGiuridica().add(personaG);
	        			request.getSoggettiCoinvolti().add(soggCoinv);
	        		}
	    		}
	    	}
		}
		
		return request;
	}
	
	public DecretiSospensioneRestituzioneBeniResponse inviaDecretiSospensioneRestituzioneBeni(DecretiSospensioneRestituzioneBeniRequest request) {
		DecretiSospensioneRestituzioneBeniResponse response = poliziaServiceWS.inviaDecretiSospensioneRestituzioneBeni(request);
		if(response != null)
			return response;
		else
			return null;
	}

	//8.8 - 1
	public DecretiProvvisoriApplicazioneRequest createDecretiProvvisoriApplicazioneRequest(long idProcedimento, long idAtto, int uffOwner, int uffDest) {

		DecretiProvvisoriApplicazioneRequest request = new DecretiProvvisoriApplicazioneRequest();
		String num = null;
		String anno = null;
		
		DatiAutoritaIdType autoritaType = new DatiAutoritaIdType(); 
		autoritaType.setIDAUTORITA(BigInteger.valueOf(uffDest));		
		request.setAutoritaRicevente(autoritaType);
		
		DatiUfficioCompetenteType ufficioType = new DatiUfficioCompetenteType();
		
		TProcedimento procRecord = poliziaDao.getProcedimento(idProcedimento, uffOwner);
		if(procRecord.getNumeAnnoProc() != null){
			num = procRecord.getNumeAnnoProc().toString(); 
		}
		
		if(procRecord.getNumeProc() != null){
			anno = procRecord.getNumeProc().toString(); 
		}
		
		String descrNumProc = formattaNumeroAnno(num, anno);

		ufficioType.setIDUFFICIO(BigInteger.valueOf(uffOwner));
		ufficioType.setDESCRNUMPROC(descrNumProc);
		ufficioType.setIDPROCEDIMENTO(BigInteger.valueOf(idProcedimento));
		request.setUfficioEmittente(ufficioType);
		
		DatiDecretoType decretoType = new DatiDecretoType();
		TDecreto decrRecord = poliziaDao.getDecreto(idAtto, uffOwner);
		TAttoId attoId = new TAttoId(idAtto, uffOwner);
		TAtto attoRecord = attoDao.getAttoById(attoId);
		
	    String numD = null;
	    String annoD = null;
	    
		decretoType.setIDDECRETO(BigInteger.valueOf(idAtto));
		
		if(decrRecord != null && decrRecord.getNumeAnnoAtto() != null){
			numD = decrRecord.getNumeAnnoAtto().toString(); 
		}
		
		if(decrRecord != null && decrRecord.getNumeAtto() != null){
			annoD = decrRecord.getNumeAtto().toString(); 
		}
		
		String descrNumDecr = formattaNumeroAnno(numD, annoD);
		
		decretoType.setDESCRNUMDECRETO(descrNumDecr);
		
	    if(attoRecord.getDataDeposito() != null){
			decretoType.setDATADEPDECRETO(dfDate.format(attoRecord.getDataDeposito()));
		}
		
		if(attoRecord.getDataEmissione() != null){
			decretoType.setDATAEMISDECRETO(dfDate.format(attoRecord.getDataEmissione()));
		}
	
		request.setDecreto(decretoType);
		
		//RECUPERARE PERSONE FISICHE ASSOCIATE AL PROCEDIMENTO/ATTO
		List<TRelProcSogint> listaSoggettiAtto = getTRelProcSogintList(idProcedimento, uffOwner);//poliziaDao.getTRelProcSogintList(idProcedimento, uffOwner);
		
		SoggettiCoinvoltiPersonaFisicaType soggCoinv = null;
		
		DatiAnagraficiPersonaFisicaType personaF = null;
		
		if(listaSoggettiAtto != null){
			
	    	Iterator<TRelProcSogint> itr = listaSoggettiAtto.iterator();
	        int counter = 0;
	    	while(itr.hasNext()) {
	    		counter ++;
	    		TRelProcSogint rowSogInt = itr.next();
	    		if(rowSogInt.getTTpTipoRuolo().getCodiKIdRuolo() == 1 || rowSogInt.getTTpTipoRuolo().getCodiKIdRuolo() == 2){ 
	    			
	    			soggCoinv = new  SoggettiCoinvoltiPersonaFisicaType();
	    			soggCoinv.setNUMEPROG(BigInteger.valueOf(counter));
	    			
	    			String tipoSogg =  poliziaDao.getTipoSoggetto(rowSogInt.getId().getCodiKIdSoggInt(), uffOwner);

	    			soggCoinv.setIDSOGGETTO(BigInteger.valueOf(rowSogInt.getId().getCodiKIdSoggInt()));
	    			soggCoinv.setIDTIPOSOGGETTO(tipoSogg);
	    			
	        		if(tipoSogg.equalsIgnoreCase(Constants.SOGGETTO_FISICO)){
	        			
	        			personaF = new DatiAnagraficiPersonaFisicaType();
	        			setDatiAnagraficiPersonaFisicaType(personaF, rowSogInt, uffOwner);
	        			setDatiMisuraPersonalePersonaType(request.getMisuraPersonale(), counter, rowSogInt.getId().getCodiKIdSoggInt(),idProcedimento, idAtto, rowSogInt, uffOwner);
	        			//correzione 16/10
	        			soggCoinv.getDatiAnagraficiPersonaFisica().add(personaF);
	        			request.getSoggettiCoinvoltiPersonaFisica().add(soggCoinv); 
	        		}
	    		}
	    	}
		}
	
		return request;
	}
	
	public DecretiProvvisoriApplicazioneResponse inviaDecretiProvvisoriApplicazione(DecretiProvvisoriApplicazioneRequest request) {
		
		DecretiProvvisoriApplicazioneResponse response = poliziaServiceWS.inviaDecretiProvvisoriApplicazione(request);
		if(response != null)
			return response;
		else
			return null;
	}

	//8.8 - 2
	public DecretiProvvisoriRevocaRequest createDecretiProvvisoriRevocaRequest(long idProcedimento, long idAtto, int uffOwner, int uffDest) {

		DecretiProvvisoriRevocaRequest request = new DecretiProvvisoriRevocaRequest();

		String num = null;
		String anno = null;
		
		DatiAutoritaIdType autoritaType = new DatiAutoritaIdType(); 
		autoritaType.setIDAUTORITA(BigInteger.valueOf(uffDest));		
		request.setAutoritaRicevente(autoritaType);
		
		DatiUfficioCompetenteType ufficioType = new DatiUfficioCompetenteType();
		
		TProcedimento procRecord = poliziaDao.getProcedimento(idProcedimento, uffOwner);
		if(procRecord.getNumeAnnoProc() != null){
			num = procRecord.getNumeAnnoProc().toString(); 
		}
		
		if(procRecord.getNumeProc() != null){
			anno = procRecord.getNumeProc().toString(); 
		}
		
		String descrNumProc = formattaNumeroAnno(num, anno);

		ufficioType.setIDUFFICIO(BigInteger.valueOf(uffOwner));
		ufficioType.setDESCRNUMPROC(descrNumProc);
		ufficioType.setIDPROCEDIMENTO(BigInteger.valueOf(idProcedimento));
		request.setUfficioEmittente(ufficioType);
		
		DatiDecretoType decretoType = new DatiDecretoType();
		TDecreto decrRecord = poliziaDao.getDecreto(idAtto, uffOwner);
		TAttoId attoId = new TAttoId(idAtto, uffOwner);
		TAtto attoRecord = attoDao.getAttoById(attoId);
		
	    String numD = null;
	    String annoD = null;
	    
		decretoType.setIDDECRETO(BigInteger.valueOf(idAtto));
		
		if(decrRecord != null && decrRecord.getNumeAnnoAtto() != null){
			numD = decrRecord.getNumeAnnoAtto().toString(); 
		}
		
		if(decrRecord != null && decrRecord.getNumeAtto() != null){
			annoD = decrRecord.getNumeAtto().toString(); 
		}
		
		String descrNumDecr = formattaNumeroAnno(numD, annoD);
		
		decretoType.setDESCRNUMDECRETO(descrNumDecr);
		
	    if(attoRecord.getDataDeposito() != null){
			decretoType.setDATADEPDECRETO(dfDate.format(attoRecord.getDataDeposito()));
		}
		
		if(attoRecord.getDataEmissione() != null){
			decretoType.setDATAEMISDECRETO(dfDate.format(attoRecord.getDataEmissione()));
		}
	
		request.setDecreto(decretoType);
		
		//RECUPERARE PERSONE FISICHE ASSOCIATE AL PROCEDIMENTO/ATTO
		List<TRelProcSogint> listaSoggettiAtto = getTRelProcSogintList(idProcedimento, uffOwner);//poliziaDao.getTRelProcSogintList(idProcedimento, uffOwner);
		
		SoggettiCoinvoltiPersonaFisicaType soggCoinv = null;
		
		DatiAnagraficiPersonaFisicaType personaF = null;
		
		if(listaSoggettiAtto != null){
			
	    	Iterator<TRelProcSogint> itr = listaSoggettiAtto.iterator();
	        int counter = 0;
	    	while(itr.hasNext()) {
	    		counter ++;
	    		TRelProcSogint rowSogInt = itr.next();
	    		
	    		if(rowSogInt.getTTpTipoRuolo().getCodiKIdRuolo() == 1 || rowSogInt.getTTpTipoRuolo().getCodiKIdRuolo() == 2){ 
	    			
	    			soggCoinv = new  SoggettiCoinvoltiPersonaFisicaType();
	    			soggCoinv.setNUMEPROG(BigInteger.valueOf(counter));
	    			
	    			String tipoSogg =  poliziaDao.getTipoSoggetto(rowSogInt.getId().getCodiKIdSoggInt(),uffOwner);

	    			soggCoinv.setIDSOGGETTO(BigInteger.valueOf(rowSogInt.getId().getCodiKIdSoggInt()));
	    			soggCoinv.setIDTIPOSOGGETTO(tipoSogg);
	    			
	        		if(tipoSogg.equalsIgnoreCase(Constants.SOGGETTO_FISICO)){
	        			
	        			personaF = new DatiAnagraficiPersonaFisicaType();
	        			setDatiAnagraficiPersonaFisicaType(personaF, rowSogInt, uffOwner);
	        			setDatiMisuraPersonalePersonaType(request.getMisuraPersonale(), counter, rowSogInt.getId().getCodiKIdSoggInt(),idProcedimento, idAtto, rowSogInt, uffOwner);
	        			//correzione 16/10
	        			soggCoinv.getDatiAnagraficiPersonaFisica().add(personaF);
	        			request.getSoggettiCoinvoltiPersonaFisica().add(soggCoinv); 
	        		}
	    		}
	    	}
		}
		
		return request;
	}
	
	public DecretiProvvisoriRevocaResponse inviaDecretiProvvisoriRevoca(DecretiProvvisoriRevocaRequest request) {

		DecretiProvvisoriRevocaResponse response = poliziaServiceWS.inviaDecretiProvvisoriRevoca(request);
		if(response != null)
			return response;
		else
			return null;
		
	}

	//8.9
	public DecretiDecisoriIGradoPersonaliRequest createDecretiDecisoriIGradoPersonaliRequest(long idProcedimento, long idAtto, int uffOwner, int uffDest) {
	    
		DecretiDecisoriIGradoPersonaliRequest request = new DecretiDecisoriIGradoPersonaliRequest(); 
		
		DatiAutoritaIdType autoritaType = new DatiAutoritaIdType(); 
		autoritaType.setIDAUTORITA(BigInteger.valueOf(uffDest));
		request.setAutoritaRicevente(autoritaType);
		
	    DatiUfficioCompetenteType ufficioType = new DatiUfficioCompetenteType();
	    ufficioType.setIDUFFICIO(BigInteger.valueOf(uffOwner));
	    ufficioType.setIDPROCEDIMENTO(BigInteger.valueOf(idProcedimento));
	    String numProc = getNumeProc(idProcedimento,uffOwner);
	    ufficioType.setDESCRNUMPROC(numProc);
	    
	    request.setUfficioEmittente(ufficioType);
	    
	    DatiDecretoType decretoType = new DatiDecretoType();
	    String numD = null;
	    String annoD = null;
	    
		TDecreto decrRecord = attoDao.getTDecreto(idAtto, uffOwner);
		decretoType.setIDDECRETO(BigInteger.valueOf(idAtto));
		
		TAttoId attoId = new TAttoId(idAtto, uffOwner);
		TAtto attoRecord = attoDao.getAttoById(attoId);
		
		if(decrRecord != null && decrRecord.getNumeAnnoAtto() != null){
			numD = decrRecord.getNumeAnnoAtto().toString(); 
		}
		
		if(decrRecord != null && decrRecord.getNumeAtto() != null){
			annoD = decrRecord.getNumeAtto().toString(); 
		}
		
		String descrNumDecr = formattaNumeroAnno(numD, annoD);
		
		decretoType.setDESCRNUMDECRETO(descrNumDecr);
		
		
	    if(attoRecord.getDataDeposito() != null){
			decretoType.setDATADEPDECRETO(dfDate.format(attoRecord.getDataDeposito()));
		}
		
		if(attoRecord.getDataEmissione() != null){
			decretoType.setDATAEMISDECRETO(dfDate.format(attoRecord.getDataEmissione()));
		}
	    request.setDecreto(decretoType);
	    
	    SoggettiCoinvoltiPersonaFisicaType soggettiCoinvoltiPersonaFisicaType = null ;
	    
	    DatiMisuraPersonalePrimoGradoType misuraPersonaleType = null;
	    
	    
		//RECUPERARE PERSONE FISICHE ASSOCIATE AL PROCEDIMENTO/ATTO
		List<TRelProcSogint> listaSoggettiAtto = getTRelProcSogintList(idProcedimento, uffOwner);//poliziaDao.getTRelProcSogintList(idProcedimento, uffOwner);
		
		DatiAnagraficiPersonaFisicaType personaF = null;
		
		if(listaSoggettiAtto != null){
			
	    	Iterator<TRelProcSogint> itr = listaSoggettiAtto.iterator();
	        int counter = 0;
	    	while(itr.hasNext()) {
	    		counter ++;
	    		TRelProcSogint rowSogInt = itr.next();
	    		soggettiCoinvoltiPersonaFisicaType = new SoggettiCoinvoltiPersonaFisicaType();
	    		if(rowSogInt.getTTpTipoRuolo().getCodiKIdRuolo() == 1){
	    			String tipoSogg =  poliziaDao.getTipoSoggetto(rowSogInt.getId().getCodiKIdSoggInt(),uffOwner);
	        	
	        		if(tipoSogg.equalsIgnoreCase(Constants.SOGGETTO_FISICO)){
	        			
		        		TPersonaFisica persF = poliziaDao.getPersonaFisica(rowSogInt.getId().getCodiKIdSoggInt(), rowSogInt.getTRelProcSogintVer().getId().getCodiKIdVer(), uffOwner);
		        		
		        		List<TMisuraPers> misureSoggetto = poliziaDao.listaMisurePersonaliByIdSoggetto(persF.getId().getCodiKIdPersonaFis(), idProcedimento, idAtto, uffOwner);
		        		
		        		if (misureSoggetto !=null && misureSoggetto.size() > 0 ){ 
		        	    	Iterator<TMisuraPers> iter = misureSoggetto.iterator();
		        	        int contaMisure = 0;
		        	        
		        	    	while(iter.hasNext()) {
		        	    		contaMisure ++;
		        	    		
		        	    		misuraPersonaleType = new DatiMisuraPersonalePrimoGradoType();
		        	    		
		        	    		TMisuraPers rowMisura = iter.next();
		        	    		
		        	    		misuraPersonaleType.setNUMEPROGRMISPERS(BigInteger.valueOf(contaMisure));
		        	    		misuraPersonaleType.setNUMEPROGPROP(BigInteger.valueOf(counter));
		        	    		
		        	    		//devo vedere quale misura personale è relativa al procedimento-atto-uffowner per aggiungere i dati corretti
		        				Set<TRelAttoMisPers> rowRelaz = rowMisura.getTRelAttoMisPerses();
		        				Iterator<TRelAttoMisPers> itRelAtto = rowRelaz.iterator();
		        				
		        				while(itRelAtto.hasNext()) {
		        					TRelAttoMisPers rowRelAtto = itRelAtto.next();
	        						
			        	    		if (rowRelAtto.getTAnNormative() != null){
			        	    			misuraPersonaleType.setIDNORMMISPERS(""+rowRelAtto.getTAnNormative().getCodiKIdNormativa());
			        	    		}
		        					if(rowRelAtto.getTTpEsitoByCodiFkIdEsito1() != null){
		        						misuraPersonaleType.setIDESITO(""+rowRelAtto.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito());
		        					}
	        						
		        					if (rowRelAtto.getId().getCodiKIdProc() == idProcedimento && 
		        						rowRelAtto.getId().getCodiKIdAtto() == idAtto && 
		        						rowRelAtto.getId().getCodiKIdUffOwner() == uffOwner && 
		        						rowRelAtto.getId().getCodiKIdMisuraPers() == rowMisura.getId().getCodiKIdMisura()
		        						) 
		        					{
		        						
		        						misuraPersonaleType.setIDTIPOMISPERS(""+rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura());
		        						
		        						//correzione 19/10/2015
		        						if (rowRelAtto.getTAnNormative() != null){
		        							misuraPersonaleType.setIDNORMMISPERS(""+rowRelAtto.getTAnNormative().getCodiKIdNormativa());
		        						}
		        					
		        						if(rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 1 || rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 2 || rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 3){
		        							misuraPersonaleType.setDESCRMISPERS(getMisPersAnniMesiGiorni(rowRelAtto.getNumeDurataAnni(), rowRelAtto.getNumeDurataMesi(), rowRelAtto.getNumeDurataGiorni(), rowRelAtto.getDescLoc()));
		        							
		        						}else if (rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 25){
		        							
		        							if(rowRelAtto.getTCauzione() != null && rowRelAtto.getTCauzione().getId() != null && rowRelAtto.getTCauzione().getId().getCodiKIdCauzione() >0 ){
		        							TCauzioneId idCauzione = new TCauzioneId();
		        							idCauzione.setCodiKIdCauzione(rowRelAtto.getTCauzione().getId().getCodiKIdCauzione());
		        							idCauzione.setCodiKIdUffOwner(uffOwner);
		        							TCauzione cauzione = poliziaDao.getCauzionebyId(idCauzione);
		        							if(cauzione != null && cauzione.getImpoCauzione() != null){
		        							  misuraPersonaleType.setDESCRMISPERS(cauzione.getImpoCauzione().toString());
		        							 }
		        							}
		        						}
		        					request.getMisuraPersonale().add(misuraPersonaleType);
		        					}
			        				//fine while aggiungere dati persona!!!!
//				        			soggettiCoinvoltiPersonaFisicaType = new SoggettiCoinvoltiPersonaFisicaType();
				        			soggettiCoinvoltiPersonaFisicaType.setNUMEPROG(BigInteger.valueOf(counter));
					        		soggettiCoinvoltiPersonaFisicaType.setIDSOGGETTO(BigInteger.valueOf(persF.getId().getCodiKIdPersonaFis()));
					        		soggettiCoinvoltiPersonaFisicaType.setIDTIPOSOGGETTO(tipoSogg);
					        		
					        		if(rowSogInt.getTTpTipoPrevProc() != null){
					        			soggettiCoinvoltiPersonaFisicaType.setIDNORMATIVA(BigInteger.valueOf(rowSogInt.getTTpTipoPrevProc().getCodiKIdTipoNorm()));
					        		}
					        		
			        				personaF = new DatiAnagraficiPersonaFisicaType();
					        		
					        		personaF.setDESCRCOGNOME(persF.getDescCognome());
					        		personaF.setDESCRNOME(persF.getDescNome());
					        		
					        		if(persF.getDataNascita() != null){
					        			personaF.setDATANASCITA(dfDate.format(persF.getDataNascita()));
					        		}
					        		
					        		if(persF.getTAnStatoByCodiFkIdStatoNasc() != null && persF.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato() > 0){
					        			TAnStato statoNasc = commonDao.getStatoById(persF.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato());
						        		personaF.setIDISTATSTATONAS(statoNasc.getDescIstatStato());
					        		}
					        		
					        		if(persF.getTAnComuniByCodiFkIdComNasc() != null && persF.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune() > 0){
					        			TAnComuni comuneNasc = commonDao.getComuneById(persF.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune());
						        		personaF.setIDISTATCOMUNENAS(comuneNasc.getDescIstatComune());
					        		}
					        		
					        		personaF.setDESCRCOMUNENAS(persF.getDescComEsteroNasc());
		        				}
		        			}
		        		}
		        		//aggiungo persona fisica all'array di appoggio
						soggettiCoinvoltiPersonaFisicaType.getDatiAnagraficiPersonaFisica().add(personaF);
						request.getSoggettiCoinvoltiPersonaFisica().add(soggettiCoinvoltiPersonaFisicaType);
	        		}
	    		}
	    	}
		}else{
			//setto valori di default
			misuraPersonaleType = new DatiMisuraPersonalePrimoGradoType();
			soggettiCoinvoltiPersonaFisicaType = new SoggettiCoinvoltiPersonaFisicaType();
			personaF = new DatiAnagraficiPersonaFisicaType();
			soggettiCoinvoltiPersonaFisicaType.getDatiAnagraficiPersonaFisica().add(personaF);
			request.getMisuraPersonale().add(misuraPersonaleType);
			request.getSoggettiCoinvoltiPersonaFisica().add(soggettiCoinvoltiPersonaFisicaType);
		}
		
		return request;
	}
	
	public DecretiDecisoriIGradoPersonaliResponse inviaDecretiDecisoriIGradoPersonali(DecretiDecisoriIGradoPersonaliRequest request) {
		
		DecretiDecisoriIGradoPersonaliResponse response = poliziaServiceWS.inviaDecretiDecisoriIGradoPersonali(request);
		if(response != null)
			return response;
		else
			return null;
	}
	
	//8.10
	public DecretiDecisoriIGradoPatrimonialiRequest createDecretiDecisoriIGradoPatrimonialiRequest(long idProcedimento, long idAtto, int uffOwner, int uffDest) {
	
		DecretiDecisoriIGradoPatrimonialiRequest request = new DecretiDecisoriIGradoPatrimonialiRequest();
		DatiAutoritaIdType autoritaType = new DatiAutoritaIdType(); 
		autoritaType.setIDAUTORITA(BigInteger.valueOf(uffDest));
		request.setAutoritaRicevente(autoritaType);
		
	    DatiUfficioCompetenteType ufficioType = new DatiUfficioCompetenteType();
	    ufficioType.setIDUFFICIO(BigInteger.valueOf(uffOwner));
	    ufficioType.setIDPROCEDIMENTO(BigInteger.valueOf(idProcedimento));
	    String numProc = getNumeProc(idProcedimento,uffOwner);
	    ufficioType.setDESCRNUMPROC(numProc);
	    
	    request.setUfficioEmittente(ufficioType);
	    
	    DatiDecretoType decretoType = new DatiDecretoType();
	    String numD = null;
	    String annoD = null;
	    
		TDecreto decrRecord = attoDao.getTDecreto(idAtto, uffOwner);
		decretoType.setIDDECRETO(BigInteger.valueOf(idAtto));
		
		TAttoId attoId = new TAttoId(idAtto, uffOwner);
		TAtto attoRecord = attoDao.getAttoById(attoId);
		
		if(decrRecord != null && decrRecord.getNumeAnnoAtto() != null){
			numD = decrRecord.getNumeAnnoAtto().toString(); 
		}
		
		if(decrRecord != null && decrRecord.getNumeAtto() != null){
			annoD = decrRecord.getNumeAtto().toString(); 
		}
		
		String descrNumDecr = formattaNumeroAnno(numD, annoD);
		
		decretoType.setDESCRNUMDECRETO(descrNumDecr);
		
		
	    if(attoRecord.getDataDeposito() != null){
			decretoType.setDATADEPDECRETO(dfDate.format(attoRecord.getDataDeposito()));
		}
		
		if(attoRecord.getDataEmissione() != null){
			decretoType.setDATAEMISDECRETO(dfDate.format(attoRecord.getDataEmissione()));
		}
	    request.setDecreto(decretoType);
	    
		List<Bene> beniList = anbscService.getBeni(idProcedimento, idAtto);
		
		DatiBeneType beneType = null;
		
		for(int i=0; i<beniList.size();i++)	{
			Bene bene = beniList.get(i);
			beneType = new DatiBeneType();
			beneType.setNUMEPROGBENE(BigInteger.valueOf(i+1));
			beneType.setIDBENE(bene.getIdentificativoBene().toString());
			beneType.setIDTIPOBENE(bene.getTipologia().toString());
			beneType.setIDCATBENE(bene.getCategoria().toString());
			if(bene.getSottocategoria() != null)
				beneType.setIDSUBCATBENE(bene.getSottocategoria().toString());
			TBene tBene = anbscDao.getBene(bene.getIdentificativoBene().longValue());
			if(tBene!= null && tBene.getDescrBene()!=null)
				beneType.setDESCRBENE(tBene.getDescrBene());
			request.getBene().add(beneType);
		}
	    
		//RECUPERARE PERSONE FISICHE/Giuridiche ASSOCIATE AL PROCEDIMENTO/ATTO
		List<TRelProcSogint> listaSoggettiAtto = getTRelProcSogintList(idProcedimento, uffOwner);//poliziaDao.getTRelProcSogintList(idProcedimento, uffOwner);
		
		SoggettiCoinvoltiType soggettiCoinvoltiType = null ;
		
		DatiAnagraficiPersonaFisicaType personaF = null;
		
		DatiAnagraficiPersonaGiuridicaType personaG = null;
		
		if(listaSoggettiAtto != null){
			
//			soggettiCoinvoltiType = new SoggettiCoinvoltiType();
			
	    	Iterator<TRelProcSogint> itr = listaSoggettiAtto.iterator();
	        int counter = 0;
	    	while(itr.hasNext()) {
	    		counter ++;
	    		TRelProcSogint rowSogInt = itr.next();
	    		soggettiCoinvoltiType = new SoggettiCoinvoltiType();
	    		if(rowSogInt.getTTpTipoRuolo().getCodiKIdRuolo() == 1 || rowSogInt.getTTpTipoRuolo().getCodiKIdRuolo() == 2)
	    		{ 
	    
	    			String tipoSogg =  poliziaDao.getTipoSoggetto(rowSogInt.getId().getCodiKIdSoggInt(), uffOwner);
	    			
	    			soggettiCoinvoltiType.setNUMEPROG(BigInteger.valueOf(counter));
	    			soggettiCoinvoltiType.setIDSOGGETTO(BigInteger.valueOf(rowSogInt.getId().getCodiKIdSoggInt()));
	    			soggettiCoinvoltiType.setIDTIPOSOGGETTO(tipoSogg);
	    			
	        		if(tipoSogg.equalsIgnoreCase(Constants.SOGGETTO_FISICO)){
	        			
	        			personaF = new DatiAnagraficiPersonaFisicaType();
	        			setDatiAnagraficiPersonaFisicaType(personaF, rowSogInt, uffOwner);
	        			soggettiCoinvoltiType.getDatiAnagraficiPersonaFisica().add(personaF);
	        			//correzione del 19/10/2015
	        			request.getSoggettiCoinvolti().add(soggettiCoinvoltiType);
	        			
	        		}else if(tipoSogg.equalsIgnoreCase(Constants.SOGGETTO_GIURIDICO)) {
	        			
	        			personaG = new DatiAnagraficiPersonaGiuridicaType();
	        			setDatiAnagraficiPersonaGiuridicaType(personaG, rowSogInt, uffOwner);
	        			//correzione del 19/10/2015
	        			soggettiCoinvoltiType.getDatiAnagraficiPersonaGiuridica().add(personaG);
	        			request.getSoggettiCoinvolti().add(soggettiCoinvoltiType);
	        		}
	    		}
	    	}//chiusura ciclo soggetti interessati
	    	
	    		//set dati misura patrimoniale
	    		List<SoggettiCoinvoltiType> soggettiDecretoList = request.getSoggettiCoinvolti();
	    		Iterator<SoggettiCoinvoltiType> itrSoggettiDecreto = soggettiDecretoList.iterator();
	    		while(itrSoggettiDecreto.hasNext()) 
	    		{
	    			SoggettiCoinvoltiType rowSogCoinv = itrSoggettiDecreto.next();
				   DatiMisuraPatrimonialePrimoGradoType misuraPat = null;
					//se la persona è soggetto giuridico verifico che abbia misure patrimoniali associate
//					List<TRelAttoMisPatr> beniSoggetto = beneDao.listaRelAttoMisPatr(rowSogCoinv.getIDSOGGETTO().longValue(), idProcedimento, uffOwner);
					List<TRelAttoMisPatr> beniSoggetto = poliziaDao.listaRelAttoMisPatrSogInt(rowSogCoinv.getIDSOGGETTO().longValue(), idProcedimento, idAtto, uffOwner);
					//se ci sono misure legate al soggetto aggiungo il soggetto e tutte le misure collegate.
					if (beniSoggetto !=null && beniSoggetto.size() > 0 )
					{ 
				    	Iterator<TRelAttoMisPatr> iter = beniSoggetto.iterator();
	//			        int contaMisure = 0;
				        
				    	while(iter.hasNext()) 
				    	{
	//			    		contaMisure ++;
				    		
				    		misuraPat = new DatiMisuraPatrimonialePrimoGradoType();
				    		
				    		TRelAttoMisPatr rowMisura = iter.next();
				    		
				    		//ciclo per gestire il NUMEPROGBENE in base alla lista beni già creata
				    		List<DatiBeneType> listaBeni = request.getBene();
				    		Iterator<DatiBeneType> iterBeni = listaBeni.iterator();
				    		while(iterBeni.hasNext()){
				    			DatiBeneType bene = iterBeni.next();
				    			if(bene.getIDBENE().equals(""+rowMisura.getId().getCodiKIdBene()))
				    				misuraPat.setNUMEPROGBENE(bene.getNUMEPROGBENE());
				    		}
	// 			    		misuraPat.setNUMEPROGBENE(BigInteger.valueOf(contaMisure));
				    		
//				    		misuraPat.setNUMEPROGPROP(BigInteger.valueOf(counter));
				    		misuraPat.setNUMEPROGPROP(rowSogCoinv.getNUMEPROG());
				
				    		TRelBeneAtto rowRelBeneAtto = beneDao.getTipoInteressePerSoggettoInteressato(idProcedimento, idAtto, uffOwner, rowMisura.getId().getCodiKIdBene(), rowMisura.getId().getCodiKIdSoggTit());
				    		
				    		if(rowRelBeneAtto.getId() != null){
				    			misuraPat.setNUMEPROGTITOL(BigInteger.valueOf(rowRelBeneAtto.getId().getCodiKIdSoggTit()));
				    		}
				    		
				    		if(rowRelBeneAtto.getTTpTipoInteresse() != null){
				    			misuraPat.setIDTIPOINTERESSE(""+rowRelBeneAtto.getTTpTipoInteresse().getCodiKIdTipoInt());
				    		}
				    		
				    		misuraPat.setNUMEQUOTA(""+rowRelBeneAtto.getNumeQuota());
				    		
				    		if(rowRelBeneAtto.getTAnNormative() != null){
				    			misuraPat.setIDNORMMISPATR(""+rowRelBeneAtto.getTAnNormative().getCodiKIdNormativa());
				    		}
				    		
				    		if(rowRelBeneAtto.getTTpEsitoByCodiFkIdEsito1() != null){
				    			misuraPat.setIDESITO(""+rowRelBeneAtto.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito());
				    		}
				    		
				    		request.getMisuraPatrimoniale().add(misuraPat);
				    	}//chiusura ciclo TRelAttoMisPatr
					}
				}
			}

		return request;
	}
	
	public DecretiDecisoriIGradoPatrimonialiResponse inviaDecretiDecisoriIGradoPatrimoniali(DecretiDecisoriIGradoPatrimonialiRequest request) {
	
		DecretiDecisoriIGradoPatrimonialiResponse response = poliziaServiceWS.inviaDecretiDecisoriIGradoPatrimoniali(request);
		if(response != null)
			return response;
		else
			return null;
	}	

	//8.11
	public DecretiDecisoriIIGradoPersonaliRequest createDecretiDecisoriIIGradoPersonaliRequest(long idProcedimento, long idAtto, int uffOwner, int uffDest) {
		DecretiDecisoriIIGradoPersonaliRequest request = new DecretiDecisoriIIGradoPersonaliRequest();  
		
		DatiAutoritaIdType autoritaType = new DatiAutoritaIdType(); 
		autoritaType.setIDAUTORITA(BigInteger.valueOf(uffDest));
		request.setAutoritaRicevente(autoritaType);
		
	    DatiUfficioCompetenteType ufficioType = new DatiUfficioCompetenteType();
	    ufficioType.setIDUFFICIO(BigInteger.valueOf(uffOwner));
	    ufficioType.setIDPROCEDIMENTO(BigInteger.valueOf(idProcedimento));
	    String numProc = getNumeProc(idProcedimento,uffOwner);
	    ufficioType.setDESCRNUMPROC(numProc);
	    
	    request.setUfficioEmittente(ufficioType);
	    
	    DatiDecretoType decretoType = new DatiDecretoType();
	    String numD = null;
	    String annoD = null;
	    
		TDecreto decrRecord = attoDao.getTDecreto(idAtto, uffOwner);
		decretoType.setIDDECRETO(BigInteger.valueOf(idAtto));
		
		TAttoId attoId = new TAttoId(idAtto, uffOwner);
		TAtto attoRecord = attoDao.getAttoById(attoId);
		
		if(decrRecord != null && decrRecord.getNumeAnnoAtto() != null){
			numD = decrRecord.getNumeAnnoAtto().toString(); 
		}
		
		if(decrRecord != null && decrRecord.getNumeAtto() != null){
			annoD = decrRecord.getNumeAtto().toString(); 
		}
		
		String descrNumDecr = formattaNumeroAnno(numD, annoD);
		
		decretoType.setDESCRNUMDECRETO(descrNumDecr);
		
		
	    if(attoRecord.getDataDeposito() != null){
			decretoType.setDATADEPDECRETO(dfDate.format(attoRecord.getDataDeposito()));
		}
		
		if(attoRecord.getDataEmissione() != null){
			decretoType.setDATAEMISDECRETO(dfDate.format(attoRecord.getDataEmissione()));
		}
	    request.setDecreto(decretoType);
	    
	    SoggettiCoinvoltiPersonaFisicaType soggettiCoinvoltiPersonaFisicaType = null ;
	    
	    DatiMisuraPersonaleSecondoGradoType misuraPersonaleType = null;
	    
		//RECUPERARE PERSONE FISICHE ASSOCIATE AL PROCEDIMENTO/ATTO
		List<TRelProcSogint> listaSoggettiAtto = getTRelProcSogintList(idProcedimento, uffOwner);//poliziaDao.getTRelProcSogintList(idProcedimento, uffOwner);
		
		DatiAnagraficiPersonaFisicaType personaF = null;
		
		if(listaSoggettiAtto != null){
			
	    	Iterator<TRelProcSogint> itr = listaSoggettiAtto.iterator();
	        int counter = 0;
	    	while(itr.hasNext()) {
	    		counter ++;
	    		TRelProcSogint rowSogInt = itr.next();
	    		//se idruolo = 1 verificare se il soggetto è persona fisica
	    		if(rowSogInt.getTTpTipoRuolo().getCodiKIdRuolo() == 1){ 
	    
	    			String tipoSogg =  poliziaDao.getTipoSoggetto(rowSogInt.getId().getCodiKIdSoggInt(), uffOwner);
	    			soggettiCoinvoltiPersonaFisicaType = new SoggettiCoinvoltiPersonaFisicaType();
	        		if(tipoSogg.equalsIgnoreCase(Constants.SOGGETTO_FISICO)){

		        		TPersonaFisica persF = poliziaDao.getPersonaFisica(rowSogInt.getId().getCodiKIdSoggInt(), rowSogInt.getTRelProcSogintVer().getId().getCodiKIdVer(), uffOwner);
		        		
		        		//se la persona è soggetto fisico verifico che abbia misure personali associate
		        		List<TMisuraPers> misureSoggetto = poliziaDao.listaMisurePersonaliByIdSoggetto(persF.getId().getCodiKIdPersonaFis(), idProcedimento, idAtto, uffOwner);
		        		
		        		//se ci sono misure legate al soggetto aggiungo il soggetto e tutte le misure collegate.
		        		if (misureSoggetto !=null && misureSoggetto.size() > 0 ){ 
		        	    	Iterator<TMisuraPers> iter = misureSoggetto.iterator();
		        	        int contaMisure = 0;
		        	        
		        	    	while(iter.hasNext()) {
		        	    		contaMisure ++;
		        	    		
		        	    		misuraPersonaleType = new DatiMisuraPersonaleSecondoGradoType();
		        	    		
		        	    		TMisuraPers rowMisura = iter.next();
		        	    		
		        	    		misuraPersonaleType.setNUMEPROGRMISPERS(BigInteger.valueOf(contaMisure));
		        	    		misuraPersonaleType.setNUMEPROGPROP(BigInteger.valueOf(counter));
		        	    		
		        	    		//devo vedere quale misura personale è relativa al procedimento-atto-uffowner per aggiungere i dati corretti
		        				Set<TRelAttoMisPers> rowRelaz = rowMisura.getTRelAttoMisPerses();
		        				Iterator<TRelAttoMisPers> itRelAtto = rowRelaz.iterator();
		        				
		        				while(itRelAtto.hasNext()) {
		        					TRelAttoMisPers rowRelAtto = itRelAtto.next();
	        						
			        	    		if (rowRelAtto.getTAnNormative() != null){
			        	    			misuraPersonaleType.setIDNORMMISPERS(""+rowRelAtto.getTAnNormative().getCodiKIdNormativa());
			        	    		}
		        					if(rowRelAtto.getTTpEsitoByCodiFkIdEsito1() != null){
		        						misuraPersonaleType.setIDESITO1(""+rowRelAtto.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito());
		        					}
		        					if(rowRelAtto.getTTpEsitoByCodiFkIdEsito2() != null){
		        						misuraPersonaleType.setIDESITO2(""+rowRelAtto.getTTpEsitoByCodiFkIdEsito2().getCodiKIdEsito());
		        					}
	        						
		        					if (rowRelAtto.getId().getCodiKIdProc() == idProcedimento && 
		        						rowRelAtto.getId().getCodiKIdAtto() == idAtto && 
		        						rowRelAtto.getId().getCodiKIdUffOwner() == uffOwner && 
		        						rowRelAtto.getId().getCodiKIdMisuraPers() == rowMisura.getId().getCodiKIdMisura()
		        						) 
		        					{
		        						
		        						misuraPersonaleType.setIDTIPOMISPERS(""+rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura());
		        						
		        						//correzione 19/10/2015
		        						if (rowRelAtto.getTAnNormative() != null){
		        							misuraPersonaleType.setIDNORMMISPERS(""+rowRelAtto.getTAnNormative().getCodiKIdNormativa());
		        						}
		        					
		        						if(rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 1 || rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 2 || rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 3){
		        							misuraPersonaleType.setDESCRMISPERS(getMisPersAnniMesiGiorni(rowRelAtto.getNumeDurataAnni(), rowRelAtto.getNumeDurataMesi(), rowRelAtto.getNumeDurataGiorni(), rowRelAtto.getDescLoc()));
		        							
		        						}else if (rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 25){
		        							
		        							if(rowRelAtto.getTCauzione() != null && rowRelAtto.getTCauzione().getId() != null && rowRelAtto.getTCauzione().getId().getCodiKIdCauzione() >0 ){
		        							TCauzioneId idCauzione = new TCauzioneId();
		        							idCauzione.setCodiKIdCauzione(rowRelAtto.getTCauzione().getId().getCodiKIdCauzione());
		        							idCauzione.setCodiKIdUffOwner(uffOwner);
		        							TCauzione cauzione = poliziaDao.getCauzionebyId(idCauzione);
		        							misuraPersonaleType.setDESCRMISPERS(cauzione.getImpoCauzione().toString());
		        							}
		        						}
		        					request.getMisuraPersonale().add(misuraPersonaleType);
		        					}
		        				//fine while aggiungere dati persona!!!!
//			        			soggettiCoinvoltiPersonaFisicaType = new SoggettiCoinvoltiPersonaFisicaType();
			        			soggettiCoinvoltiPersonaFisicaType.setNUMEPROG(BigInteger.valueOf(counter));
				        		soggettiCoinvoltiPersonaFisicaType.setIDSOGGETTO(BigInteger.valueOf(persF.getId().getCodiKIdPersonaFis()));
				        		soggettiCoinvoltiPersonaFisicaType.setIDTIPOSOGGETTO(tipoSogg);
				        		
				        		if(rowSogInt.getTTpTipoPrevProc() != null){
				        			soggettiCoinvoltiPersonaFisicaType.setIDNORMATIVA(BigInteger.valueOf(rowSogInt.getTTpTipoPrevProc().getCodiKIdTipoNorm()));
				        		}
				        		
		        				personaF = new DatiAnagraficiPersonaFisicaType();
				        		
				        		personaF.setDESCRCOGNOME(persF.getDescCognome());
				        		personaF.setDESCRNOME(persF.getDescNome());
				        		
				        		if(persF.getDataNascita() != null){
				        			personaF.setDATANASCITA(dfDate.format(persF.getDataNascita()));
				        		}
				        		
				        		if(persF.getTAnStatoByCodiFkIdStatoNasc() != null && persF.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato() > 0){
				        			TAnStato statoNasc = commonDao.getStatoById(persF.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato());
					        		personaF.setIDISTATSTATONAS(statoNasc.getDescIstatStato());
				        		}
				        		
				        		if(persF.getTAnComuniByCodiFkIdComNasc() != null && persF.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune() > 0){
				        			TAnComuni comuneNasc = commonDao.getComuneById(persF.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune());
					        		personaF.setIDISTATCOMUNENAS(comuneNasc.getDescIstatComune());
				        		}
				        		
				        			personaF.setDESCRCOMUNENAS(persF.getDescComEsteroNasc());
		        				}
		        			}
		        		}
	        		}
	    		}
	    		//aggiungo persona fisica all'array di appoggio
				soggettiCoinvoltiPersonaFisicaType.getDatiAnagraficiPersonaFisica().add(personaF);
				request.getSoggettiCoinvoltiPersonaFisica().add(soggettiCoinvoltiPersonaFisicaType);
	    	}
		}else{
			//setto valori di default
			misuraPersonaleType = new DatiMisuraPersonaleSecondoGradoType();
			soggettiCoinvoltiPersonaFisicaType = new SoggettiCoinvoltiPersonaFisicaType();
			personaF = new DatiAnagraficiPersonaFisicaType();
			soggettiCoinvoltiPersonaFisicaType.getDatiAnagraficiPersonaFisica().add(personaF);
			request.getMisuraPersonale().add(misuraPersonaleType);
			request.getSoggettiCoinvoltiPersonaFisica().add(soggettiCoinvoltiPersonaFisicaType);
		}
		
		return request;
	}
	
	public DecretiDecisoriIIGradoPersonaliResponse inviaDecretiDecisoriIIGradoPersonali(DecretiDecisoriIIGradoPersonaliRequest request) {
		DecretiDecisoriIIGradoPersonaliResponse response = poliziaServiceWS.inviaDecretiDecisoriIIGradoPersonali(request);
		if(response != null)
			return response;
		else
			return null;
	}
	
	//8.12
	public DecretiDecisoriIIGradoPatrimonialiRequest createDecretiDecisoriIIGradoPatrimonialiRequest(long idProcedimento, long idAtto, int uffOwner, int uffDest) {
		
		DecretiDecisoriIIGradoPatrimonialiRequest request = new DecretiDecisoriIIGradoPatrimonialiRequest();
		DatiAutoritaIdType autoritaType = new DatiAutoritaIdType(); 
		autoritaType.setIDAUTORITA(BigInteger.valueOf(uffDest));
		request.setAutoritaRicevente(autoritaType);
		
	    DatiUfficioCompetenteType ufficioType = new DatiUfficioCompetenteType();
	    ufficioType.setIDUFFICIO(BigInteger.valueOf(uffOwner));
	    ufficioType.setIDPROCEDIMENTO(BigInteger.valueOf(idProcedimento));
	    String numProc = getNumeProc(idProcedimento,uffOwner);
	    ufficioType.setDESCRNUMPROC(numProc);
	    
	    request.setUfficioEmittente(ufficioType);
	    
	    DatiDecretoType decretoType = new DatiDecretoType();
	    String numD = null;
	    String annoD = null;
	    
		TDecreto decrRecord = attoDao.getTDecreto(idAtto, uffOwner);
		decretoType.setIDDECRETO(BigInteger.valueOf(idAtto));
		
		if(decrRecord != null && decrRecord.getNumeAnnoAtto() != null){
			numD = decrRecord.getNumeAnnoAtto().toString(); 
		}
		
		if(decrRecord != null && decrRecord.getNumeAtto() != null){
			annoD = decrRecord.getNumeAtto().toString(); 
		}
		
		String descrNumDecr = formattaNumeroAnno(numD, annoD);
		
		TAttoId id = new TAttoId(idAtto, (int) uffOwner);
		TAtto atto = attoDao.getAttoById(id);
		
		decretoType.setDESCRNUMDECRETO(descrNumDecr);
		if(atto!=null && atto.getDataEmissione()!=null)
			decretoType.setDATAEMISDECRETO(dfDate.format(atto.getDataEmissione()));
		if(atto!=null && atto.getDataDeposito()!=null)
			decretoType.setDATADEPDECRETO(dfDate.format(atto.getDataDeposito()));
		
	    request.setDecreto(decretoType);
		
		List<Bene> beniList = anbscService.getBeni(idProcedimento, idAtto);
		
		DatiBeneType beneType = null;
		
		for(int i=0; i<beniList.size();i++)	{
			Bene bene = beniList.get(i);
			beneType = new DatiBeneType();
			beneType.setNUMEPROGBENE(BigInteger.valueOf(i+1));
			beneType.setIDBENE(bene.getIdentificativoBene().toString());
			beneType.setIDTIPOBENE(bene.getTipologia().toString());
			beneType.setIDCATBENE(bene.getCategoria().toString());
			if(bene.getSottocategoria() != null)
				beneType.setIDSUBCATBENE(bene.getSottocategoria().toString());
			TBene tBene = anbscDao.getBene(bene.getIdentificativoBene().longValue());
			if(tBene!= null && tBene.getDescrBene()!=null)
				beneType.setDESCRBENE(tBene.getDescrBene());
			request.getBene().add(beneType);
		}
		
		//RECUPERARE PERSONE FISICHE/Giuridiche ASSOCIATE AL PROCEDIMENTO/ATTO
		List<TRelProcSogint> listaSoggettiAtto = getTRelProcSogintList(idProcedimento, uffOwner);//poliziaDao.getTRelProcSogintList(idProcedimento, uffOwner);
		
		SoggettiCoinvoltiType soggettiCoinvoltiType = null ;
		
		DatiAnagraficiPersonaFisicaType personaF = null;
		
		DatiAnagraficiPersonaGiuridicaType personaG = null;
		
		if(listaSoggettiAtto != null){
			
			Iterator<TRelProcSogint> itr = listaSoggettiAtto.iterator();
	        int counter = 0;
	    	while(itr.hasNext()) {
	    		counter ++;
	    		TRelProcSogint rowSogInt = itr.next();
	    		soggettiCoinvoltiType = new SoggettiCoinvoltiType();
	    		if(rowSogInt.getTTpTipoRuolo().getCodiKIdRuolo() == 1 || rowSogInt.getTTpTipoRuolo().getCodiKIdRuolo() == 2)
	    		{ 
	    
	    			String tipoSogg =  poliziaDao.getTipoSoggetto(rowSogInt.getId().getCodiKIdSoggInt(), uffOwner);
	    			
	    			soggettiCoinvoltiType.setNUMEPROG(BigInteger.valueOf(counter));
	    			soggettiCoinvoltiType.setIDSOGGETTO(BigInteger.valueOf(rowSogInt.getId().getCodiKIdSoggInt()));
	    			soggettiCoinvoltiType.setIDTIPOSOGGETTO(tipoSogg);
	    			
	        		if(tipoSogg.equalsIgnoreCase(Constants.SOGGETTO_FISICO)){
	        			
	        			personaF = new DatiAnagraficiPersonaFisicaType();
	        			setDatiAnagraficiPersonaFisicaType(personaF, rowSogInt, uffOwner);
	        			soggettiCoinvoltiType.getDatiAnagraficiPersonaFisica().add(personaF);
	        			//correzione del 19/10/2015
	        			request.getSoggettiCoinvolti().add(soggettiCoinvoltiType);
	        		}else if(tipoSogg.equalsIgnoreCase(Constants.SOGGETTO_GIURIDICO)) {
	        			
	        			personaG = new DatiAnagraficiPersonaGiuridicaType();
	        			setDatiAnagraficiPersonaGiuridicaType(personaG, rowSogInt, uffOwner);
	        			//correzione del 19/10/2015
	        			soggettiCoinvoltiType.getDatiAnagraficiPersonaGiuridica().add(personaG);
	        			request.getSoggettiCoinvolti().add(soggettiCoinvoltiType);
	        		}
	    		}
	    	}
	        		
    			//set dati misura patrimoniale
	    	List<SoggettiCoinvoltiType> soggettiDecretoList = request.getSoggettiCoinvolti();
    		Iterator<SoggettiCoinvoltiType> itrSoggettiDecreto = soggettiDecretoList.iterator();
    		while(itrSoggettiDecreto.hasNext()) 
    		{
    			SoggettiCoinvoltiType rowSogCoinv = itrSoggettiDecreto.next();
			    DatiMisuraPatrimonialeSecondoGradoType misuraPat = null;
				//se la persona è soggetto giuridico verifico che abbia misure patrimoniali associate
//    				List<TRelAttoMisPatr> beniSoggetto = beneDao.listaRelAttoMisPatr(rowSogInt.getId().getCodiKIdSoggInt(), idProcedimento, uffOwner);
				List<TRelAttoMisPatr> misPatrList = poliziaDao.listaRelAttoMisPatrSogInt(rowSogCoinv.getIDSOGGETTO().longValue(), idProcedimento, idAtto, uffOwner);
				//se ci sono misure legate al soggetto aggiungo il soggetto e tutte le misure collegate.
				if (misPatrList !=null && misPatrList.size() > 0 ){ 
			    	Iterator<TRelAttoMisPatr> iter = misPatrList.iterator();
			        
			    	while(iter.hasNext()) {
			    		
			    		misuraPat = new DatiMisuraPatrimonialeSecondoGradoType();
			    		
			    		TRelAttoMisPatr rowMisura = iter.next();
			    		
			    		//ciclo per gestire il NUMEPROGBENE in base alla lista beni già creata
			    		List<DatiBeneType> listaBeni = request.getBene();
			    		Iterator<DatiBeneType> iterBeni = listaBeni.iterator();
			    		while(iterBeni.hasNext()){
			    			DatiBeneType bene = iterBeni.next();
			    			if(bene.getIDBENE().equals(""+rowMisura.getId().getCodiKIdBene()))
			    				misuraPat.setNUMEPROGBENE(bene.getNUMEPROGBENE());
			    		}
			    		
			    		
			    		misuraPat.setNUMEPROGPROP(BigInteger.valueOf(counter));
			
			    		TRelBeneAtto rowRelBeneAtto = beneDao.getTipoInteressePerSoggettoInteressato(idProcedimento, idAtto, uffOwner, rowMisura.getId().getCodiKIdBene(), rowMisura.getId().getCodiKIdSoggTit());
			    		
			    		if(rowRelBeneAtto.getId() != null){
			    			misuraPat.setNUMEPROGTITOL(BigInteger.valueOf(rowRelBeneAtto.getId().getCodiKIdSoggTit()));
			    		}
			    		
			    		if(rowRelBeneAtto.getTTpTipoInteresse() != null){
			    			misuraPat.setIDTIPOINTERESSE(""+rowRelBeneAtto.getTTpTipoInteresse().getCodiKIdTipoInt());
			    		}
			    		
			    		misuraPat.setNUMEQUOTA(""+rowRelBeneAtto.getNumeQuota());
			    		
			    		if(rowRelBeneAtto.getTAnNormative() != null){
			    			misuraPat.setIDNORMMISPATR(""+rowRelBeneAtto.getTAnNormative().getCodiKIdNormativa());
			    		}
			    		
			    		if(rowRelBeneAtto.getTTpEsitoByCodiFkIdEsito1() != null){
			    			misuraPat.setIDESITO1(""+rowRelBeneAtto.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito());
			    		}
			    		if(rowRelBeneAtto.getTTpEsitoByCodiFkIdEsito2() != null) {
			    			misuraPat.setIDESITO2(""+rowRelBeneAtto.getTTpEsitoByCodiFkIdEsito2().getCodiKIdEsito());
			    		}
			    		request.getMisuraPatrimoniale().add(misuraPat);
					}
        		}
	    	}
		}
		
		return request;
	}
	
	public DecretiDecisoriIIGradoPatrimonialiResponse inviaDecretiDecisoriIIGradoPatrimoniali(DecretiDecisoriIIGradoPatrimonialiRequest request) {
		DecretiDecisoriIIGradoPatrimonialiResponse response = poliziaServiceWS.inviaDecretiDecisoriIIGradoPatrimoniali(request);
		if(response != null)
			return response;
		else
			return null;
	}	
	
	//8.13
	public SentenzaCassazionePersonaliRequest createSentenzaCassazionePersonaliRequest(long idProcedimento, long idAtto, int uffOwner, int uffDest) {

		SentenzaCassazionePersonaliRequest request = new SentenzaCassazionePersonaliRequest();   
		
		DatiAutoritaIdType autoritaType = new DatiAutoritaIdType(); 
		autoritaType.setIDAUTORITA(BigInteger.valueOf(uffDest));
		request.setAutoritaRicevente(autoritaType);
		
	    DatiUfficioCompetenteType ufficioType = new DatiUfficioCompetenteType();
	    ufficioType.setIDUFFICIO(BigInteger.valueOf(uffOwner));
	    ufficioType.setIDPROCEDIMENTO(BigInteger.valueOf(idProcedimento));
	    String numProc = getNumeProc(idProcedimento,uffOwner);
	    ufficioType.setDESCRNUMPROC(numProc);
	    
	    request.setUfficioEmittente(ufficioType);
	    
	    DatiSentenzaType sentenzaType = new DatiSentenzaType();
	    String numS = null;
	    String annoS = null;
	    
	    TAttoId attoId = new TAttoId();
	    attoId.setCodiKIdAtto(idAtto);
	    attoId.setCodiKIdUffOwner(uffOwner);
		TAtto sentenzaRecord = attoDao.getAttoById(attoId);
		TIstanzaId istanzaId = new TIstanzaId(idAtto, uffOwner);
		TIstanza istanzaRecord = poliziaDao.getIstanzabyId(istanzaId);
		
		if(istanzaRecord.getNumeAnnoAtto() != null){
			numS = istanzaRecord.getNumeAnnoAtto().toString();
		}
		
		if(istanzaRecord.getNumeAtto() != null){
			annoS = istanzaRecord.getNumeAtto().toString();
		}
		
		String numSentenza = formattaNumeroAnno(numS, annoS);
		
		if(sentenzaRecord.getDataEmissione() != null){
			sentenzaType.setDATAEMISSENTENZA(dfDate.format(sentenzaRecord.getDataEmissione()));
		}
		
		if(sentenzaRecord.getDataDeposito() != null){
			sentenzaType.setDATADEPSENTENZA(dfDate.format(sentenzaRecord.getDataDeposito()));
		}

		sentenzaType.setIDSENTENZA(""+idAtto);		
		sentenzaType.setDESCRNUMSENTENZA(numSentenza);
	    request.setSentenza(sentenzaType);
	    
	    SoggettiCoinvoltiPersonaFisicaType soggettiCoinvoltiPersonaFisicaType = null ;
	    
	    DatiMisuraPersonaleCassazioneType misuraPersonaleType = null;
	    
	    
		//RECUPERARE PERSONE FISICHE ASSOCIATE AL PROCEDIMENTO/ATTO
		List<TRelProcSogint> listaSoggettiAtto = getTRelProcSogintList(idProcedimento, uffOwner);//poliziaDao.getTRelProcSogintList(idProcedimento, uffOwner);
		
		DatiAnagraficiPersonaFisicaType personaF = null;
		
		if(listaSoggettiAtto != null){
			
	    	Iterator<TRelProcSogint> itr = listaSoggettiAtto.iterator();
	        int counter = 0;
	    	while(itr.hasNext()) {
	    		counter ++;
	    		TRelProcSogint rowSogInt = itr.next();
	    		//se idruolo = 1 verificare se il soggetto è persona fisica
	    		if(rowSogInt.getTTpTipoRuolo().getCodiKIdRuolo() == 1){ 
	    
	    			String tipoSogg =  poliziaDao.getTipoSoggetto(rowSogInt.getId().getCodiKIdSoggInt(), uffOwner);
	        	
	        		if(tipoSogg.equalsIgnoreCase(Constants.SOGGETTO_FISICO)){

		        		TPersonaFisica persF = poliziaDao.getPersonaFisica(rowSogInt.getId().getCodiKIdSoggInt(), rowSogInt.getTRelProcSogintVer().getId().getCodiKIdVer(), uffOwner);
		        		
		        		//se la persona è soggetto fisico verifico che abbia misure personali associate
		        		List<TMisuraPers> misureSoggetto = poliziaDao.listaMisurePersonaliByIdSoggetto(persF.getId().getCodiKIdPersonaFis(), idProcedimento, idAtto, uffOwner);
		        		
		        		//se ci sono misure legate al soggetto aggiungo il soggetto e tutte le misure collegate.
		        		if (misureSoggetto !=null && misureSoggetto.size() > 0 ){ 
		        	    	Iterator<TMisuraPers> iter = misureSoggetto.iterator();
		        	        int contaMisure = 0;
		        	        
		        	    	while(iter.hasNext()) {
		        	    		contaMisure ++;
		        	    		
		        	    		misuraPersonaleType = new DatiMisuraPersonaleCassazioneType();
		        	    		
		        	    		TMisuraPers rowMisura = iter.next();
		        	    		
		        	    		misuraPersonaleType.setNUMEPROGRMISPERS(BigInteger.valueOf(contaMisure));
		        	    		misuraPersonaleType.setNUMEPROGPROP(BigInteger.valueOf(counter));
		        	    		
		        	    		//devo vedere quale misura personale è relativa al procedimento-atto-uffowner per aggiungere i dati corretti
		        				Set<TRelAttoMisPers> rowRelaz = rowMisura.getTRelAttoMisPerses();
		        				Iterator<TRelAttoMisPers> itRelAtto = rowRelaz.iterator();
		        				
		        				while(itRelAtto.hasNext()) {
		        					TRelAttoMisPers rowRelAtto = itRelAtto.next();
	        						
			        	    		if (rowRelAtto.getTAnNormative() != null){
			        	    			misuraPersonaleType.setIDNORMMISPERS(""+rowRelAtto.getTAnNormative().getCodiKIdNormativa());
			        	    		}
		        					if(rowRelAtto.getTTpEsitoByCodiFkIdEsito1() != null){
		        						misuraPersonaleType.setIDESITO1(""+rowRelAtto.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito());
		        					}
		        					if(rowRelAtto.getTTpEsitoByCodiFkIdEsito2() != null){
		        						misuraPersonaleType.setIDESITO2(""+rowRelAtto.getTTpEsitoByCodiFkIdEsito2().getCodiKIdEsito());
		        					}
		        					if(rowRelAtto.getTTpEsitoByCodiFkIdEsito3() != null){
		        						misuraPersonaleType.setIDESITO3(""+rowRelAtto.getTTpEsitoByCodiFkIdEsito3().getCodiKIdEsito());
		        					}
	        						
		        					if (rowRelAtto.getId().getCodiKIdProc() == idProcedimento && 
		        						rowRelAtto.getId().getCodiKIdAtto() == idAtto && 
		        						rowRelAtto.getId().getCodiKIdUffOwner() == uffOwner && 
		        						rowRelAtto.getId().getCodiKIdMisuraPers() == rowMisura.getId().getCodiKIdMisura()) {
		        						
		        						misuraPersonaleType.setIDTIPOMISPERS(""+rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura());
		        						
		        						if(rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 1 || rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 2 || rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 3){
		        							misuraPersonaleType.setDESCRMISPERS(getMisPersAnniMesiGiorni(rowRelAtto.getNumeDurataAnni(), rowRelAtto.getNumeDurataMesi(), rowRelAtto.getNumeDurataGiorni(), rowRelAtto.getDescLoc()));
		        							
		        						}else if (rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 25){
		        							
		        							if(rowRelAtto.getTCauzione() != null && rowRelAtto.getTCauzione().getId() != null && rowRelAtto.getTCauzione().getId().getCodiKIdCauzione() >0 ){
		        							TCauzioneId idCauzione = new TCauzioneId();
		        							idCauzione.setCodiKIdCauzione(rowRelAtto.getTCauzione().getId().getCodiKIdCauzione());
		        							idCauzione.setCodiKIdUffOwner(uffOwner);
		        							TCauzione cauzione = poliziaDao.getCauzionebyId(idCauzione);
		        							misuraPersonaleType.setDESCRMISPERS(cauzione.getImpoCauzione().toString());
		        							}
		        						}
		        					request.getMisuraPersonale().add(misuraPersonaleType);
		        					}
		        				//fine while aggiungere dati persona!!!!
			        			soggettiCoinvoltiPersonaFisicaType = new SoggettiCoinvoltiPersonaFisicaType();
			        			soggettiCoinvoltiPersonaFisicaType.setNUMEPROG(BigInteger.valueOf(counter));
				        		soggettiCoinvoltiPersonaFisicaType.setIDSOGGETTO(BigInteger.valueOf(persF.getId().getCodiKIdPersonaFis()));
				        		soggettiCoinvoltiPersonaFisicaType.setIDTIPOSOGGETTO(tipoSogg);
				        		
				        		if(rowSogInt.getTTpTipoPrevProc() != null){
				        			soggettiCoinvoltiPersonaFisicaType.setIDNORMATIVA(BigInteger.valueOf(rowSogInt.getTTpTipoPrevProc().getCodiKIdTipoNorm()));
				        		}
				        		
		        				personaF = new DatiAnagraficiPersonaFisicaType();
				        		
				        		personaF.setDESCRCOGNOME(persF.getDescCognome());
				        		personaF.setDESCRNOME(persF.getDescNome());
				        		
				        		if(persF.getDataNascita() != null){
				        			personaF.setDATANASCITA(dfDate.format(persF.getDataNascita()));
				        		}
				        		
				        		if(persF.getTAnStatoByCodiFkIdStatoNasc() != null && persF.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato() > 0){
				        			TAnStato statoNasc = commonDao.getStatoById(persF.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato());
					        		personaF.setIDISTATSTATONAS(statoNasc.getDescIstatStato());
				        		}
				        		
				        		if(persF.getTAnComuniByCodiFkIdComNasc() != null && persF.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune() > 0){
				        			TAnComuni comuneNasc = commonDao.getComuneById(persF.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune());
					        		personaF.setIDISTATCOMUNENAS(comuneNasc.getDescIstatComune());
				        		}
				        		
				        		personaF.setDESCRCOMUNENAS(persF.getDescComEsteroNasc());
		        				}

			    				//aggiungo persona fisica all'array di appoggio
		        				soggettiCoinvoltiPersonaFisicaType.getDatiAnagraficiPersonaFisica().add(personaF);
		        				request.getSoggettiCoinvoltiPersonaFisica().add(soggettiCoinvoltiPersonaFisicaType);
		        			}
		        		}
	        		}
	    		}
	    	}
		}else{
			//setto valori di default
			misuraPersonaleType = new DatiMisuraPersonaleCassazioneType();
			soggettiCoinvoltiPersonaFisicaType = new SoggettiCoinvoltiPersonaFisicaType();
			personaF = new DatiAnagraficiPersonaFisicaType();
			soggettiCoinvoltiPersonaFisicaType.getDatiAnagraficiPersonaFisica().add(personaF);
			request.getMisuraPersonale().add(misuraPersonaleType);
			request.getSoggettiCoinvoltiPersonaFisica().add(soggettiCoinvoltiPersonaFisicaType);
		}
		
		return request;
	}
	
	public SentenzaCassazionePersonaliResponse inviaSentenzaCassazionePersonali(SentenzaCassazionePersonaliRequest request) {
		SentenzaCassazionePersonaliResponse response = poliziaServiceWS.inviaSentenzaCassazionePersonali(request);
		if(response != null)
			return response;
		else
			return null;
	}

	//8.13 - 8.14
	public void elaboraSentenze(){
		List queryResults = poliziaDao.getListaSentenze();
		List<TAtto> sentenze = null;
		if ((queryResults != null) && !queryResults.isEmpty()) {
			sentenze = new ArrayList<TAtto>();
			Iterator iterator = queryResults.iterator();
			while (iterator.hasNext()) {
				Map<String, Object> row = (Map<String, Object>) iterator.next();
				TAtto msg = (TAtto) row.get("att");
				sentenze.add(msg);
			}
		}
		
		if(sentenze!=null && sentenze.size()>0)//ci sono sentenze da inviare
		{
			Iterator<TAtto> iter = sentenze.iterator();
			while(iter.hasNext() ) {
				TAtto atto = (TAtto)iter.next();
				long idProcedimento = anbscDao.getIdProcedimento(atto.getId().getCodiKIdAtto(), atto.getId().getCodiKIdUffOwner());
				String descUtente = atto.getDescUtente();
				TProcedimento procedimento = procedimentoDao.getProcedimentoById(idProcedimento);
				List<Integer> uffDestinatariList = getUffDestinatario(procedimento.getTAnProcedente().getCodiKIdProced(), atto.getId().getCodiKIdUffOwner(), idProcedimento, ""+procedimento.getTTpTipoProc().getCodiKIdTipoProc());
					for(int i=0; i<uffDestinatariList.size(); i++)
					{
						if(procedimento.getTTpTipoProc().getCodiKIdTipoProc() == Integer.parseInt(Constants.TIPOPROC_PERSONALE))
						{
							SentenzaCassazionePersonaliRequest sentenzaCassazionePersonaliRequest = createSentenzaCassazionePersonaliRequest(idProcedimento, atto.getId().getCodiKIdAtto(), atto.getId().getCodiKIdUffOwner(), uffDestinatariList.get(i));
							long idMsg = insertPoliziaMsgDatabase(Constants.POLIZIA_SENTENZA_CASSAZIONE_PERS, idProcedimento, atto.getId().getCodiKIdAtto(), sentenzaCassazionePersonaliRequest, Constants.POLIZIA_STATO_SERVIZIO_INSERITO, uffDestinatariList.get(i), atto.getId().getCodiKIdUffOwner(), descUtente);
						}
						if(procedimento.getTTpTipoProc().getCodiKIdTipoProc() == Integer.parseInt(Constants.TIPOPROC_PATRIMONIALE))
						{
							SentenzaCassazionePatrimonialiRequest sentenzaCassazionePatrimonialiRequest = createSentenzaCassazionePatrimonialiRequest(idProcedimento, atto.getId().getCodiKIdAtto(), atto.getId().getCodiKIdUffOwner(), uffDestinatariList.get(i));
							long idMsg = insertPoliziaMsgDatabase(Constants.POLIZIA_SENTENZA_CASSAZIONE_PATR, idProcedimento, atto.getId().getCodiKIdAtto(), sentenzaCassazionePatrimonialiRequest, Constants.POLIZIA_STATO_SERVIZIO_INSERITO, uffDestinatariList.get(i), atto.getId().getCodiKIdUffOwner(), descUtente);
						}
						if(procedimento.getTTpTipoProc().getCodiKIdTipoProc() == Integer.parseInt(Constants.TIPOPROC_PERSONALE_E_PATRIMONIALE))
						{
							SentenzaCassazionePersonaliRequest sentenzaCassazionePersonaliRequest = createSentenzaCassazionePersonaliRequest(idProcedimento, atto.getId().getCodiKIdAtto(), atto.getId().getCodiKIdUffOwner(), uffDestinatariList.get(i));
							insertPoliziaMsgDatabase(Constants.POLIZIA_SENTENZA_CASSAZIONE_PERS, idProcedimento, atto.getId().getCodiKIdAtto(), sentenzaCassazionePersonaliRequest, Constants.POLIZIA_STATO_SERVIZIO_INSERITO, uffDestinatariList.get(i), atto.getId().getCodiKIdUffOwner(), descUtente);
							SentenzaCassazionePatrimonialiRequest sentenzaCassazionePatrimonialiRequest = createSentenzaCassazionePatrimonialiRequest(idProcedimento, atto.getId().getCodiKIdAtto(), atto.getId().getCodiKIdUffOwner(), uffDestinatariList.get(i));
							insertPoliziaMsgDatabase(Constants.POLIZIA_SENTENZA_CASSAZIONE_PATR, idProcedimento, atto.getId().getCodiKIdAtto(), sentenzaCassazionePatrimonialiRequest, Constants.POLIZIA_STATO_SERVIZIO_INSERITO, uffDestinatariList.get(i), atto.getId().getCodiKIdUffOwner(), descUtente);
						}
					}
					poliziaDao.aggiornaFlgMps(atto.getId().getCodiKIdAtto(),atto.getId().getCodiKIdUffOwner(),'S');
				}
		}	
	}
	
	public SentenzaCassazionePatrimonialiRequest createSentenzaCassazionePatrimonialiRequest(long idProcedimento, long idAtto, int uffOwner, int uffDest) {
		
		SentenzaCassazionePatrimonialiRequest request = new SentenzaCassazionePatrimonialiRequest();
		DatiAutoritaIdType autoritaType = new DatiAutoritaIdType(); 
		autoritaType.setIDAUTORITA(BigInteger.valueOf(uffDest));
		request.setAutoritaRicevente(autoritaType);
		
	    DatiUfficioCompetenteType ufficioType = new DatiUfficioCompetenteType();
	    ufficioType.setIDUFFICIO(BigInteger.valueOf(uffOwner));
	    ufficioType.setIDPROCEDIMENTO(BigInteger.valueOf(idProcedimento));
	    String numProc = getNumeProc(idProcedimento,uffOwner);
	    ufficioType.setDESCRNUMPROC(numProc);
	    
	    request.setUfficioEmittente(ufficioType);
	    
	    DatiSentenzaType sentenzaType = new DatiSentenzaType();
	    String numS = null;
	    String annoS = null;
	    
	    TAttoId attoId = new TAttoId();
	    attoId.setCodiKIdAtto(idAtto);
	    attoId.setCodiKIdUffOwner(uffOwner);
		TAtto sentenzaRecord = attoDao.getAttoById(attoId);
		TIstanzaId istanzaId = new TIstanzaId(idAtto, uffOwner);
		TIstanza istanzaRecord = poliziaDao.getIstanzabyId(istanzaId);
		
		if(istanzaRecord.getNumeAnnoAtto() != null){
			numS = istanzaRecord.getNumeAnnoAtto().toString();
		}
		
		if(istanzaRecord.getNumeAtto() != null){
			annoS = istanzaRecord.getNumeAtto().toString();
		}
		
		String numSentenza = formattaNumeroAnno(numS, annoS);
		
		if(sentenzaRecord.getDataEmissione() != null){
			sentenzaType.setDATAEMISSENTENZA(dfDate.format(sentenzaRecord.getDataEmissione()));
		}
		
		if(sentenzaRecord.getDataDeposito() != null){
			sentenzaType.setDATADEPSENTENZA(dfDate.format(sentenzaRecord.getDataDeposito()));
		}

		sentenzaType.setIDSENTENZA(""+idAtto);		
		sentenzaType.setDESCRNUMSENTENZA(numSentenza);
	    request.setSentenza(sentenzaType);
	    
		List<Bene> beniList = anbscService.getBeni(idProcedimento, idAtto);
		
		DatiBeneType beneType = null;
		
		for(int i=0; i<beniList.size();i++)	{
			Bene bene = beniList.get(i);
			beneType = new DatiBeneType();
			beneType.setNUMEPROGBENE(BigInteger.valueOf(i+1));
			beneType.setIDBENE(bene.getIdentificativoBene().toString());
			beneType.setIDTIPOBENE(bene.getTipologia().toString());
			beneType.setIDCATBENE(bene.getCategoria().toString());
			if(bene.getSottocategoria() != null)
				beneType.setIDSUBCATBENE(bene.getSottocategoria().toString());
			TBene tBene = anbscDao.getBene(bene.getIdentificativoBene().longValue());
			if(tBene!= null && tBene.getDescrBene()!=null)
				beneType.setDESCRBENE(tBene.getDescrBene());
			request.getBene().add(beneType);
		}
		
		//RECUPERARE PERSONE FISICHE/Giuridiche ASSOCIATE AL PROCEDIMENTO/ATTO
		List<TRelProcSogint> listaSoggettiAtto = getTRelProcSogintList(idProcedimento, uffOwner);//poliziaDao.getTRelProcSogintList(idProcedimento, uffOwner);
		
		SoggettiCoinvoltiType soggettiCoinvoltiType = null ;
		
		DatiAnagraficiPersonaFisicaType personaF = null;
		
		DatiAnagraficiPersonaGiuridicaType personaG = null;
		
		if(listaSoggettiAtto != null){
			
			soggettiCoinvoltiType = new SoggettiCoinvoltiType();
			
	    	Iterator<TRelProcSogint> itr = listaSoggettiAtto.iterator();
	        int counter = 0;
	    	while(itr.hasNext()) {
	    		counter ++;
	    		TRelProcSogint rowSogInt = itr.next();

	    		if(rowSogInt.getTTpTipoRuolo().getCodiKIdRuolo() == 1 || rowSogInt.getTTpTipoRuolo().getCodiKIdRuolo() == 2){ 
	    
	    			String tipoSogg =  poliziaDao.getTipoSoggetto(rowSogInt.getId().getCodiKIdSoggInt(), uffOwner);
	    			
	    			soggettiCoinvoltiType.setNUMEPROG(BigInteger.valueOf(counter));
	    			soggettiCoinvoltiType.setIDSOGGETTO(BigInteger.valueOf(rowSogInt.getId().getCodiKIdSoggInt()));
	    			soggettiCoinvoltiType.setIDTIPOSOGGETTO(tipoSogg);
	    			
	        		if(tipoSogg.equalsIgnoreCase(Constants.SOGGETTO_FISICO)){
	        			
	        			personaF = new DatiAnagraficiPersonaFisicaType();
	        			setDatiAnagraficiPersonaFisicaType(personaF, rowSogInt, uffOwner);
	        			soggettiCoinvoltiType.getDatiAnagraficiPersonaFisica().add(personaF);

	        			//correzione del 19/10/2015
	        			request.getSoggettiCoinvolti().add(soggettiCoinvoltiType);
	        			
	        			//set dati misura patrimoniale
	        			DatiMisuraPatrimonialeCassazioneType misuraPat = null;
	        				//se la persona è soggetto giuridico verifico che abbia misure patrimoniali associate
	        				List<TRelAttoMisPatr> beniSoggetto = beneDao.listaRelAttoMisPatr(rowSogInt.getId().getCodiKIdSoggInt(), idProcedimento, uffOwner);
	        				
	        				//se ci sono misure legate al soggetto aggiungo il soggetto e tutte le misure collegate.
	        				if (beniSoggetto !=null && beniSoggetto.size() > 0 ){ 
	        			    	Iterator<TRelAttoMisPatr> iter = beniSoggetto.iterator();
	        			        int contaMisure = 0;
	        			        
	        			    	while(iter.hasNext()) {
	        			    		contaMisure ++;
	        			    		
	        			    		misuraPat = new DatiMisuraPatrimonialeCassazioneType();
	        			    		
	        			    		TRelAttoMisPatr rowMisura = iter.next();
	        			    		
	        			    		misuraPat.setNUMEPROGBENE(BigInteger.valueOf(contaMisure));
	        			    		misuraPat.setNUMEPROGPROP(BigInteger.valueOf(counter));
	        			
	        			    		TRelBeneAtto rowRelBeneAtto = beneDao.getTipoInteressePerSoggettoInteressato(idProcedimento, idAtto, uffOwner, rowMisura.getId().getCodiKIdBene(), rowMisura.getId().getCodiKIdSoggTit());
	        			    		
	        			    		if(rowRelBeneAtto.getId() != null){
	        			    			misuraPat.setNUMEPROGTITOL(BigInteger.valueOf(rowRelBeneAtto.getId().getCodiKIdSoggTit()));
	        			    		}
	        			    		
	        			    		if(rowRelBeneAtto.getTTpTipoInteresse() != null){
	        			    			misuraPat.setIDTIPOINTERESSE(""+rowRelBeneAtto.getTTpTipoInteresse().getCodiKIdTipoInt());
	        			    		}
	        			    		
	        			    		misuraPat.setNUMEQUOTA(""+rowRelBeneAtto.getNumeQuota());
	        			    		
	        			    		if(rowRelBeneAtto.getTAnNormative() != null){
	        			    			misuraPat.setIDNORMMISPATR(""+rowRelBeneAtto.getTAnNormative().getCodiKIdNormativa());
	        			    		}

	        			    		if(rowRelBeneAtto.getTTpEsitoByCodiFkIdEsito1() != null){
	        			    			misuraPat.setIDESITO1(""+rowRelBeneAtto.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito());
	        			    		}
	        			    		
	        			    		if(rowRelBeneAtto.getTTpEsitoByCodiFkIdEsito2() != null) {
	        			    			misuraPat.setIDESITO2(""+rowRelBeneAtto.getTTpEsitoByCodiFkIdEsito2().getCodiKIdEsito());
	        			    		}
	        			    		request.getMisuraPatrimoniale().add(misuraPat);
	        					}
	        				}
	        			
	        		}else if(tipoSogg.equalsIgnoreCase(Constants.SOGGETTO_GIURIDICO)) {
	        			
	        			personaG = new DatiAnagraficiPersonaGiuridicaType();
	        			setDatiAnagraficiPersonaGiuridicaType(personaG, rowSogInt, uffOwner);
	        			//correzione del 19/10/2015
	        			soggettiCoinvoltiType.getDatiAnagraficiPersonaGiuridica().add(personaG);
	        			request.getSoggettiCoinvolti().add(soggettiCoinvoltiType);
	        			//set dati misura patrimoniale
	        			DatiMisuraPatrimonialeCassazioneType misuraPat = null;
	        				//se la persona è soggetto giuridico verifico che abbia misure patrimoniali associate
	        				List<TRelAttoMisPatr> beniSoggetto = beneDao.listaRelAttoMisPatr(rowSogInt.getId().getCodiKIdSoggInt(), idProcedimento, uffOwner);
	        				
	        				//se ci sono misure legate al soggetto aggiungo il soggetto e tutte le misure collegate.
	        				if (beniSoggetto !=null && beniSoggetto.size() > 0 ){ 
	        			    	Iterator<TRelAttoMisPatr> iter = beniSoggetto.iterator();
	        			        int contaMisure = 0;
	        			        
	        			    	while(iter.hasNext()) {
	        			    		contaMisure ++;
	        			    		
	        			    		misuraPat = new DatiMisuraPatrimonialeCassazioneType();
	        			    		
	        			    		TRelAttoMisPatr rowMisura = iter.next();
	        			    		
	        			    		misuraPat.setNUMEPROGBENE(BigInteger.valueOf(contaMisure));
	        			    		misuraPat.setNUMEPROGPROP(BigInteger.valueOf(counter));
	        			
	        			    		TRelBeneAtto rowRelBeneAtto = beneDao.getTipoInteressePerSoggettoInteressato(idProcedimento, idAtto, uffOwner, rowMisura.getId().getCodiKIdBene(), rowMisura.getId().getCodiKIdSoggTit());
	        			    		
	        			    		if(rowRelBeneAtto.getId() != null){
	        			    			misuraPat.setNUMEPROGTITOL(BigInteger.valueOf(rowRelBeneAtto.getId().getCodiKIdSoggTit()));
	        			    		}
	        			    		
	        			    		if(rowRelBeneAtto.getTTpTipoInteresse() != null){
	        			    			misuraPat.setIDTIPOINTERESSE(""+rowRelBeneAtto.getTTpTipoInteresse().getCodiKIdTipoInt());
	        			    		}
	        			    		
	        			    		misuraPat.setNUMEQUOTA(""+rowRelBeneAtto.getNumeQuota());
	        			    		
	        			    		if(rowRelBeneAtto.getTAnNormative() != null){
	        			    			misuraPat.setIDNORMMISPATR(""+rowRelBeneAtto.getTAnNormative().getCodiKIdNormativa());
	        			    		}
	        			    		
	        			    		if(rowRelBeneAtto.getTTpEsitoByCodiFkIdEsito1() != null){
	        			    			misuraPat.setIDESITO1(""+rowRelBeneAtto.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito());
	        			    		}
	        			    		if(rowRelBeneAtto.getTTpEsitoByCodiFkIdEsito2() != null) {
	        			    			misuraPat.setIDESITO2(""+rowRelBeneAtto.getTTpEsitoByCodiFkIdEsito2().getCodiKIdEsito());
	        			    		}
	        			    		if(rowRelBeneAtto.getTTpEsitoByCodiFkIdEsito3() != null) {
	        			    			misuraPat.setIDESITO3(""+rowRelBeneAtto.getTTpEsitoByCodiFkIdEsito3().getCodiKIdEsito());
	        			    		}
	        			    		
	        			    		request.getMisuraPatrimoniale().add(misuraPat);
	        					}
	        				}
	        		}
	    		}
	    	}
		}
		return request;
	}
	
	public SentenzaCassazionePatrimonialiResponse inviaSentenzaCassazionePatrimoniali(SentenzaCassazionePatrimonialiRequest request) {
		SentenzaCassazionePatrimonialiResponse response = poliziaServiceWS.inviaSentenzaCassazionePatrimoniali(request);
		if(response != null)
			return response;
		else
			return null;
	}
	
	//8.15
	public DefinitivitaPersonaliRequest createDefinitivitaPersonaliRequest(long idProcedimento, long idAtto, int uffOwner, int uffDest,	HashMap<Long, Date> misureDateDefinitive) {
		DefinitivitaPersonaliRequest request = new DefinitivitaPersonaliRequest(); 

		DatiAutoritaIdType autoritaType = new DatiAutoritaIdType(); 
		autoritaType.setIDAUTORITA(BigInteger.valueOf(uffDest));
		request.setAutoritaRicevente(autoritaType);
		
	    DatiUfficioCompetenteType ufficioType = new DatiUfficioCompetenteType();
	    ufficioType.setIDUFFICIO(BigInteger.valueOf(uffOwner));
	    ufficioType.setIDPROCEDIMENTO(BigInteger.valueOf(idProcedimento));
	    String numProc = getNumeProc(idProcedimento,uffOwner);
	    ufficioType.setDESCRNUMPROC(numProc);
	    
	    request.setUfficioEmittente(ufficioType);
	    
	    DatiDecretoDefinitivitaType decretoType = new DatiDecretoDefinitivitaType();
	    String numD = null;
	    String annoD = null;
	    
		TDecreto decrRecord = attoDao.getTDecreto(idAtto, uffOwner);
		decretoType.setIDDECRETO(BigInteger.valueOf(idAtto));
		
		if(decrRecord != null && decrRecord.getNumeAnnoAtto() != null){
			numD = decrRecord.getNumeAnnoAtto().toString(); 
		}
		
		if(decrRecord != null && decrRecord.getNumeAtto() != null){
			annoD = decrRecord.getNumeAtto().toString(); 
		}
		
		String descrNumDecr = formattaNumeroAnno(numD, annoD);
		
		decretoType.setDESCRNUMDECRETO(descrNumDecr);
	    request.setDecreto(decretoType);
	    
	    SoggettiCoinvoltiPersonaFisicaType soggettiCoinvoltiPersonaFisicaType = null ;
	    
	    DatiMisuraPersonaleDefinitivitaType misuraPersonaleType = null;
	    
		//RECUPERARE PERSONE FISICHE ASSOCIATE AL PROCEDIMENTO/ATTO
		List<TRelProcSogint> listaSoggettiAtto = getTRelProcSogintList(idProcedimento, uffOwner);//poliziaDao.getTRelProcSogintList(idProcedimento, uffOwner);
		
		DatiAnagraficiPersonaFisicaType personaF = null;
		
		if(listaSoggettiAtto != null){
			
	    	Iterator<TRelProcSogint> itr = listaSoggettiAtto.iterator();
	        int counter = 0;
	    	while(itr.hasNext()) {
	    		counter ++;
	    		TRelProcSogint rowSogInt = itr.next();
	    		//se idruolo = 1 verificare se il soggetto è persona fisica
	    		if(rowSogInt.getTTpTipoRuolo().getCodiKIdRuolo() == 1){
	    			soggettiCoinvoltiPersonaFisicaType = new SoggettiCoinvoltiPersonaFisicaType();
	    
	    			String tipoSogg =  poliziaDao.getTipoSoggetto(rowSogInt.getId().getCodiKIdSoggInt(),uffOwner);
	        	
	        		if(tipoSogg.equalsIgnoreCase(Constants.SOGGETTO_FISICO)){

		        		TPersonaFisica persF = poliziaDao.getPersonaFisica(rowSogInt.getId().getCodiKIdSoggInt(), rowSogInt.getTRelProcSogintVer().getId().getCodiKIdVer(), uffOwner);
		        		
		        		//se la persona è soggetto fisico verifico che abbia misure personali associate
		        		List<TMisuraPers> misureSoggetto = poliziaDao.listaMisurePersonaliByIdSoggetto(persF.getId().getCodiKIdPersonaFis(), idProcedimento, idAtto, uffOwner);
		        		
		        		//se ci sono misure legate al soggetto aggiungo il soggetto e tutte le misure collegate.
		        		if (misureSoggetto !=null && misureSoggetto.size() > 0 ){ 
		        			
		        	    	Iterator<TMisuraPers> iter = misureSoggetto.iterator();
		        	        int contaMisure = 0;
		        	        
		        	    	while(iter.hasNext()) {
		        	    		contaMisure ++;
		        	    		
		        	    		misuraPersonaleType = new DatiMisuraPersonaleDefinitivitaType();
		        	    		
		        	    		TMisuraPers rowMisura = iter.next();
		        	    		
		        	    		misuraPersonaleType.setNUMEPROGRMISPERS(BigInteger.valueOf(contaMisure));
		        	    		misuraPersonaleType.setNUMEPROGPROP(BigInteger.valueOf(counter));
		        	    		
		        	    		//devo vedere quale misura personale è relativa al procedimento-atto-uffowner per aggiungere i dati corretti
		        				Set<TRelAttoMisPers> rowRelaz = rowMisura.getTRelAttoMisPerses();
		        				Iterator<TRelAttoMisPers> itRelAtto = rowRelaz.iterator();
		        				
		        				while(itRelAtto.hasNext()) {
		        					TRelAttoMisPers rowRelAtto = itRelAtto.next();
	        						// se la misura non è tra quelle passate nell'hashmap di input non va aggiunta la misura
		        					if(misureDateDefinitive.containsKey(new Long(rowRelAtto.getId().getCodiKIdMisuraPers()))){
		        					//setto la data definitività passata nell'hashmap relativa all'id misura
		        					misuraPersonaleType.setDATADEFINITIVITA(dfDate.format(misureDateDefinitive.get(new Long(rowRelAtto.getId().getCodiKIdMisuraPers()))));
		        						
			        	    		if (rowRelAtto.getTAnNormative() != null){
			        	    			misuraPersonaleType.setIDNORMMISPERS(""+rowRelAtto.getTAnNormative().getCodiKIdNormativa());
			        	    		}

			        	    		if(rowRelAtto.getTTpEsitoByCodiFkIdEsito1() != null){
		        						misuraPersonaleType.setIDESITO1(""+rowRelAtto.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito());
		        					}
			        	    		
			        	    		if(rowRelAtto.getTTpEsitoByCodiFkIdEsito2() != null){
		        						misuraPersonaleType.setIDESITO2(""+rowRelAtto.getTTpEsitoByCodiFkIdEsito2().getCodiKIdEsito());
		        					}
			        	    		
			        	    		if(rowRelAtto.getTTpEsitoByCodiFkIdEsito3() != null){
		        						misuraPersonaleType.setIDESITO3(""+rowRelAtto.getTTpEsitoByCodiFkIdEsito3().getCodiKIdEsito());
		        					}
	        						
		        					if (rowRelAtto.getId().getCodiKIdProc() == idProcedimento && 
		        						rowRelAtto.getId().getCodiKIdAtto() == idAtto && 
		        						rowRelAtto.getId().getCodiKIdUffOwner() == uffOwner && 
		        						rowRelAtto.getId().getCodiKIdMisuraPers() == rowMisura.getId().getCodiKIdMisura()
		        						) {
		        						
		        						misuraPersonaleType.setIDTIPOMISPERS(""+rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura());
		        					
		        						//correzione 19/10/2015
		        						if (rowRelAtto.getTAnNormative() != null){
		        							misuraPersonaleType.setIDNORMMISPERS(""+rowRelAtto.getTAnNormative().getCodiKIdNormativa());
		        						}
		        						
		        						if(rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 1 || rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 2 || rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 3){
		        							misuraPersonaleType.setDESCRMISPERS(getMisPersAnniMesiGiorni(rowRelAtto.getNumeDurataAnni(), rowRelAtto.getNumeDurataMesi(), rowRelAtto.getNumeDurataGiorni(), rowRelAtto.getDescLoc()));
		        							
		        						}else if (rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 25){
		        							
		        							if(rowRelAtto.getTCauzione() != null && rowRelAtto.getTCauzione().getId() != null && rowRelAtto.getTCauzione().getId().getCodiKIdCauzione() >0 ){
		        							TCauzioneId idCauzione = new TCauzioneId();
		        							idCauzione.setCodiKIdCauzione(rowRelAtto.getTCauzione().getId().getCodiKIdCauzione());
		        							idCauzione.setCodiKIdUffOwner(uffOwner);
		        							TCauzione cauzione = poliziaDao.getCauzionebyId(idCauzione);
		        							if (cauzione!=null && cauzione.getImpoCauzione()!=null) {
		        								misuraPersonaleType.setDESCRMISPERS(cauzione.getImpoCauzione().toString());
		        							}
		        							
		        							}
		        						}
		        					request.getMisuraPersonale().add(misuraPersonaleType);
		        				}
		        			}
		        				//fine while aggiungere dati persona!!!!
//			        			soggettiCoinvoltiPersonaFisicaType = new SoggettiCoinvoltiPersonaFisicaType();
			        			soggettiCoinvoltiPersonaFisicaType.setNUMEPROG(BigInteger.valueOf(counter));
				        		soggettiCoinvoltiPersonaFisicaType.setIDSOGGETTO(BigInteger.valueOf(persF.getId().getCodiKIdPersonaFis()));
				        		soggettiCoinvoltiPersonaFisicaType.setIDTIPOSOGGETTO(tipoSogg);
				        		if(rowSogInt.getTTpTipoPrevProc() != null){
				        			soggettiCoinvoltiPersonaFisicaType.setIDNORMATIVA(BigInteger.valueOf(rowSogInt.getTTpTipoPrevProc().getCodiKIdTipoNorm()));
				        		}
		        				personaF = new DatiAnagraficiPersonaFisicaType();
				        		
				        		personaF.setDESCRCOGNOME(persF.getDescCognome());
				        		personaF.setDESCRNOME(persF.getDescNome());
				        		
				        		if(persF.getDataNascita() != null){
				        			personaF.setDATANASCITA(dfDate.format(persF.getDataNascita()));
				        		}
				        		
				        		if(persF.getTAnStatoByCodiFkIdStatoNasc() != null && persF.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato() > 0){
				        			TAnStato statoNasc = commonDao.getStatoById(persF.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato());
					        		personaF.setIDISTATSTATONAS(statoNasc.getDescIstatStato());
				        		}
				        		
				        		if(persF.getTAnComuniByCodiFkIdComNasc() != null && persF.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune() > 0){
				        			TAnComuni comuneNasc = commonDao.getComuneById(persF.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune());
					        		personaF.setIDISTATCOMUNENAS(comuneNasc.getDescIstatComune());
				        		}
				        		
				        			personaF.setDESCRCOMUNENAS(persF.getDescComEsteroNasc());
		        				}
		        		}
		        	}
	        	}
	    		}
	    		//aggiungo persona fisica all'array di appoggio
				soggettiCoinvoltiPersonaFisicaType.getDatiAnagraficiPersonaFisica().add(personaF);
				request.getSoggettiCoinvoltiPersonaFisica().add(soggettiCoinvoltiPersonaFisicaType);
	    	}
		}else{
			//setto valori di default
			misuraPersonaleType = new DatiMisuraPersonaleDefinitivitaType();
			soggettiCoinvoltiPersonaFisicaType = new SoggettiCoinvoltiPersonaFisicaType();
			personaF = new DatiAnagraficiPersonaFisicaType();
			soggettiCoinvoltiPersonaFisicaType.getDatiAnagraficiPersonaFisica().add(personaF);
			request.getMisuraPersonale().add(misuraPersonaleType);
			request.getSoggettiCoinvoltiPersonaFisica().add(soggettiCoinvoltiPersonaFisicaType);
		}
		
		return request;
	}
	
	public DefinitivitaPersonaliResponse inviaDefinitivitaPersonali(DefinitivitaPersonaliRequest request) {
		DefinitivitaPersonaliResponse response = poliziaServiceWS.inviaDefinitivitaPersonali(request);
		if(response != null)
			return response;
		else
			return null;
	}

	//8.16
	public DefinitivitaPatrimonialiRequest createDefinitivitaPatrimonialiRequest(long idProcedimento, long idAtto, int uffOwner, int uffDest, HashMap <Long, Date> misureDateDefinitive) {
		
		DefinitivitaPatrimonialiRequest request = new DefinitivitaPatrimonialiRequest();
		
		DatiAutoritaIdType autoritaType = new DatiAutoritaIdType(); 
		autoritaType.setIDAUTORITA(BigInteger.valueOf(uffDest));
		request.setAutoritaRicevente(autoritaType);
		
	    DatiUfficioCompetenteType ufficioType = new DatiUfficioCompetenteType();
	    ufficioType.setIDUFFICIO(BigInteger.valueOf(uffOwner));
	    ufficioType.setIDPROCEDIMENTO(BigInteger.valueOf(idProcedimento));
	    String numProc = getNumeProc(idProcedimento,uffOwner);
	    ufficioType.setDESCRNUMPROC(numProc);
	    
	    request.setUfficioEmittente(ufficioType);
	    
	    DatiDecretoDefinitivitaType decretoType = new DatiDecretoDefinitivitaType();
	    String numD = null;
	    String annoD = null;
	    
		TDecreto decrRecord = attoDao.getTDecreto(idAtto, uffOwner);
		decretoType.setIDDECRETO(BigInteger.valueOf(idAtto));
		
		if(decrRecord != null && decrRecord.getNumeAnnoAtto() != null){
			numD = decrRecord.getNumeAnnoAtto().toString(); 
		}
		
		if(decrRecord != null && decrRecord.getNumeAtto() != null){
			annoD = decrRecord.getNumeAtto().toString(); 
		}
		
		String descrNumDecr = formattaNumeroAnno(numD, annoD);
		
		decretoType.setDESCRNUMDECRETO(descrNumDecr);
	    request.setDecreto(decretoType);
		
		List<Bene> beniList = anbscService.getBeni(idProcedimento, idAtto);
		
		DatiBeneType beneType = null;
		
		for(int i=0; i<beniList.size();i++)	{
			Bene bene = beniList.get(i);
			beneType = new DatiBeneType();
			beneType.setNUMEPROGBENE(BigInteger.valueOf(i+1));
			beneType.setIDBENE(bene.getIdentificativoBene().toString());
			beneType.setIDTIPOBENE(bene.getTipologia().toString());
			beneType.setIDCATBENE(bene.getCategoria().toString());
			if(bene.getSottocategoria() != null)
				beneType.setIDSUBCATBENE(bene.getSottocategoria().toString());
			TBene tBene = anbscDao.getBene(bene.getIdentificativoBene().longValue());
			if(tBene!= null && tBene.getDescrBene()!=null)
				beneType.setDESCRBENE(tBene.getDescrBene());
			request.getBene().add(beneType);
		}
	    
		//RECUPERARE PERSONE Fisiche/Giuridiche ASSOCIATE AL PROCEDIMENTO/ATTO
		List<TRelProcSogint> listaSoggettiAtto = getTRelProcSogintList(idProcedimento, uffOwner);//poliziaDao.getTRelProcSogintList(idProcedimento, uffOwner);
		
		SoggettiCoinvoltiType soggettiCoinvoltiType = null ;
		
		DatiAnagraficiPersonaFisicaType personaF = null;
		
		DatiAnagraficiPersonaGiuridicaType personaG = null;
		
		if(listaSoggettiAtto != null){
			
			Iterator<TRelProcSogint> itr = listaSoggettiAtto.iterator();
	        int counterSoggetto = 0;
	    	while(itr.hasNext()) 
	    	{
	    		TRelProcSogint rowSogInt = itr.next();

	    		if(rowSogInt.getTTpTipoRuolo().getCodiKIdRuolo() == 1 || rowSogInt.getTTpTipoRuolo().getCodiKIdRuolo() == 2)
	    		{ 
	    			String tipoSogg =  poliziaDao.getTipoSoggetto(rowSogInt.getId().getCodiKIdSoggInt(),uffOwner);
	    			counterSoggetto ++;
	    			soggettiCoinvoltiType = new SoggettiCoinvoltiType();
	    			soggettiCoinvoltiType.setNUMEPROG(BigInteger.valueOf(counterSoggetto));
	    			soggettiCoinvoltiType.setIDSOGGETTO(BigInteger.valueOf(rowSogInt.getId().getCodiKIdSoggInt()));
	    			soggettiCoinvoltiType.setIDTIPOSOGGETTO(tipoSogg);
	    			
	    			if(tipoSogg.equalsIgnoreCase(Constants.SOGGETTO_FISICO)){
	        			personaF = new DatiAnagraficiPersonaFisicaType();
	        			setDatiAnagraficiPersonaFisicaType(personaF, rowSogInt, uffOwner);
	        			soggettiCoinvoltiType.getDatiAnagraficiPersonaFisica().add(personaF);
	        			request.getSoggettiCoinvolti().add(soggettiCoinvoltiType);
	        		}else if(tipoSogg.equalsIgnoreCase(Constants.SOGGETTO_GIURIDICO)) {
	        			personaG = new DatiAnagraficiPersonaGiuridicaType();
	        			setDatiAnagraficiPersonaGiuridicaType(personaG, rowSogInt, uffOwner);
	        			soggettiCoinvoltiType.getDatiAnagraficiPersonaGiuridica().add(personaG);
	        			request.getSoggettiCoinvolti().add(soggettiCoinvoltiType);
	        		}
	    			
	    			/************************** MISURE ******************************/
	    			//set dati misura patrimoniale
        			DatiMisuraPatrimonialeDefinitivitaType misuraPat = null;
//        			List<TRelAttoMisPatr> beniSoggetto = beneDao.listaRelAttoMisPatr(rowSogInt.getId().getCodiKIdSoggInt(), idProcedimento, uffOwner);
        			List<TRelAttoMisPatr> beniSoggetto = poliziaDao.listaRelAttoMisPatrSogInt(rowSogInt.getId().getCodiKIdSoggInt(), idProcedimento, idAtto, uffOwner);
        				
    				//se ci sono misure legate al soggetto le aggiungo.
    				if (beniSoggetto !=null && beniSoggetto.size() > 0 )
    				{ 
    			    	Iterator<TRelAttoMisPatr> iter = beniSoggetto.iterator();
//    			        int contaMisure = 0;
    			        
    			    	while(iter.hasNext()) 
    			    	{
//    			    		contaMisure ++;
    			    		
    			    		misuraPat = new DatiMisuraPatrimonialeDefinitivitaType();
    			    		
    			    		TRelAttoMisPatr rowMisura = iter.next();

    			    		//ciclo per gestire il NUMEPROGBENE in base alla lista beni già creata
				    		List<DatiBeneType> listaBeni = request.getBene();
				    		Iterator<DatiBeneType> iterBeni = listaBeni.iterator();
				    		while(iterBeni.hasNext()){
				    			DatiBeneType bene = iterBeni.next();
				    			if(bene.getIDBENE().equals(""+rowMisura.getId().getCodiKIdBene()))
				    				misuraPat.setNUMEPROGBENE(bene.getNUMEPROGBENE());
				    		}
//    			    		misuraPat.setNUMEPROGBENE(BigInteger.valueOf(contaMisure));
    			    		misuraPat.setNUMEPROGPROP(BigInteger.valueOf(counterSoggetto));
    			
    			    		TRelBeneAtto rowRelBeneAtto = beneDao.getTipoInteressePerSoggettoInteressato(idProcedimento, idAtto, uffOwner, rowMisura.getId().getCodiKIdBene(), rowMisura.getId().getCodiKIdSoggTit());

        					if(misureDateDefinitive.containsKey(new Long(rowRelBeneAtto.getId().getCodiKIdBene())))
        					{
	        					//setto la data definitività passata nell'hashmap relativa all'id misura/bene
	        						misuraPat.setDATADEFINITIVITA(dfDate.format(misureDateDefinitive.get(new Long(rowRelBeneAtto.getId().getCodiKIdBene()))));
        			    		
        			    		if(rowRelBeneAtto.getId() != null){
        			    			misuraPat.setNUMEPROGTITOL(BigInteger.valueOf(rowRelBeneAtto.getId().getCodiKIdSoggTit()));
        			    		}
        			    		
        			    		if(rowRelBeneAtto.getTTpTipoInteresse() != null){
        			    			misuraPat.setIDTIPOINTERESSE(""+rowRelBeneAtto.getTTpTipoInteresse().getCodiKIdTipoInt());
        			    		}
        			    		
        			    		misuraPat.setNUMEQUOTA(""+rowRelBeneAtto.getNumeQuota());
        			    		
        			    		if(rowRelBeneAtto.getTAnNormative() != null){
        			    			misuraPat.setIDNORMMISPATR(""+rowRelBeneAtto.getTAnNormative().getCodiKIdNormativa());
        			    		}

        			    		if(rowRelBeneAtto.getTTpEsitoByCodiFkIdEsito1() != null){
        			    			misuraPat.setIDESITO1(""+rowRelBeneAtto.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito());
        			    		}
        			    		
        			    		if(rowRelBeneAtto.getTTpEsitoByCodiFkIdEsito2() != null) {
        			    			misuraPat.setIDESITO2(""+rowRelBeneAtto.getTTpEsitoByCodiFkIdEsito2().getCodiKIdEsito());
        			    		}
        			    		request.getMisuraPatrimoniale().add(misuraPat);
        					}
    			    	}//end while TRelAttoMisPatr
    			    }//end if beniSoggetto
    				/******************************** END MISURE **********************************/
	    		}
	    	}
		}
		return request;
	}
	
	public DefinitivitaPatrimonialiResponse inviaDefinitivitaPatrimoniali(DefinitivitaPatrimonialiRequest request) {
		DefinitivitaPatrimonialiResponse response = poliziaServiceWS.inviaDefinitivitaPatrimoniali(request);
		if(response != null)
			return response;
		else
			return null;
	}
	
	//8.17
	public RichiestaAggravamentoMisuraPersonaleRequest createRichiestaAggravamentoMisuraPersonale(long idProcedimento, long idAtto, int uffOwner, int uffDest, long idProcedimentoOld, int ufficioProcOld) {
		RichiestaAggravamentoMisuraPersonaleRequest request = new RichiestaAggravamentoMisuraPersonaleRequest();
		String num = null;
		String anno = null;
		
		DatiAutoritaIdType autoritaType = new DatiAutoritaIdType(); 
		autoritaType.setIDAUTORITA(BigInteger.valueOf(uffDest));		

		request.setAutoritaRicevente(autoritaType);

		DatiAutoritaRichiedenteType autRicType = new DatiAutoritaRichiedenteType();
		autRicType.setIDAUTORITA(BigInteger.valueOf(uffOwner));
		autRicType.setIDRICHIESTA(BigInteger.valueOf(idAtto));
				
		TAttoId attoId = new TAttoId(idAtto, uffOwner);
		TAtto attoRecord = attoDao.getAttoById(attoId);
		TIstanzaId istanzaId = new TIstanzaId(idAtto, uffOwner);
		TIstanza istanzaRecord = poliziaDao.getIstanzabyId(istanzaId);
		
		if(istanzaRecord.getNumeAnnoAtto() != null){
			num = istanzaRecord.getNumeAnnoAtto().toString();
		}
		
		if(istanzaRecord.getNumeAtto() != null){
			anno = istanzaRecord.getNumeAtto().toString();
		}
		
		String numProposta = formattaNumeroAnno(num, anno);
		
		if(attoRecord.getDataEmissione() != null){
			autRicType.setDATAEMISSIONE(dfDate.format(attoRecord.getDataEmissione()));
		}
		
		if(attoRecord.getDataDeposito() != null){
			autRicType.setDATADEPOSITO(dfDate.format(attoRecord.getDataDeposito()));
		}
		
		autRicType.setDESCRNUMRICHIESTA(numProposta);
		
		request.setAutoritaRichiedente(autRicType);
		
		DatiUfficioDecidenteType uffDecidenteType = new DatiUfficioDecidenteType(); 
		uffDecidenteType.setIDUFFICIO(BigInteger.valueOf(ufficioProcOld));
		
		TProcedimento procRecordOld = poliziaDao.getProcedimento(idProcedimentoOld, ufficioProcOld);
		String numPOld = null;
		String annoPOld = null;
		
		if(procRecordOld!=null){
			if(procRecordOld.getNumeAnnoProc() != null){
				numPOld = procRecordOld.getNumeAnnoProc().toString(); 
			}
			
			if(procRecordOld.getNumeProc() != null){
				annoPOld = procRecordOld.getNumeProc().toString(); 
			}
		}
		
		String descrNumProcOld = formattaNumeroAnno(numPOld, annoPOld);
		
		uffDecidenteType.setDESCRNUMPROC(descrNumProcOld);
		request.setUfficioDecidente(uffDecidenteType);

		DatiUfficioCompetenteType uffCompType = new DatiUfficioCompetenteType();
		uffCompType.setIDUFFICIO(BigInteger.valueOf(uffOwner));
		uffCompType.setIDPROCEDIMENTO(BigInteger.valueOf(idProcedimento));
		
		String numP = null;
		String annoP = null;
		
		TProcedimento procRecord = poliziaDao.getProcedimento(idProcedimento, uffOwner);
		
		if(procRecord.getNumeAnnoProc() != null){
			numP = procRecord.getNumeAnnoProc().toString(); 
		}
		
		if(procRecord.getNumeProc() != null){
			annoP = procRecord.getNumeProc().toString(); 
		}
		
		String descrNumProc = formattaNumeroAnno(numP, annoP);
		
		uffCompType.setDESCRNUMPROC(descrNumProc);
		request.setUfficioCompetente(uffCompType);
		
		//RECUPERARE PERSONE FISICHE ASSOCIATE AL PROCEDIMENTO/ATTO
		List<TRelProcSogint> listaSoggettiAtto = getTRelProcSogintList(idProcedimento, uffOwner);//poliziaDao.getTRelProcSogintList(idProcedimento, uffOwner);
		
		if(listaSoggettiAtto != null){
			
	    	Iterator<TRelProcSogint> itr = listaSoggettiAtto.iterator();
	        int counter = 0;
	    	while(itr.hasNext()) {
	    		counter ++;
	    		TRelProcSogint rowSogInt = itr.next();
	    		//se idruolo = 1 verificare se il soggetto è persona fisica
	    		if(rowSogInt.getTTpTipoRuolo().getCodiKIdRuolo() == 1){ 
	    
	    			String tipoSogg =  poliziaDao.getTipoSoggetto(rowSogInt.getId().getCodiKIdSoggInt(),uffOwner);
	        	
	        		if(tipoSogg.equalsIgnoreCase(Constants.SOGGETTO_FISICO)){

	        			setSoggettoFisicoMisurePersonali(counter, request.getPersonaFisica(), request.getMisuraPersonale(), rowSogInt, idProcedimento, idAtto, uffOwner );	        			
	        		}
	    		}
	    	}
		}
		
		return request;
	}
	
	public RichiestaAggravamentoMisuraPersonaleResponse inviaRichiestaAggravamentoMisuraPersonale(RichiestaAggravamentoMisuraPersonaleRequest request) {
		RichiestaAggravamentoMisuraPersonaleResponse response = poliziaServiceWS.inviaRichiestaAggravamentoMisuraPersonale(request);
		
		if(response != null)
			return response;
		else
			return null;
	}
	
	// 8.18
	public DecisorioAggravamentoMisuraPersonaleRequest createDecisorioAggravamentoMisuraPersonaleRequest(long idProcedimento, long idAtto, int uffOwner, int uffDest) {
		DecisorioAggravamentoMisuraPersonaleRequest request = new DecisorioAggravamentoMisuraPersonaleRequest();

		DatiAutoritaIdType autoritaType = new DatiAutoritaIdType(); 
		autoritaType.setIDAUTORITA(BigInteger.valueOf(uffDest));		

		request.setAutoritaRicevente(autoritaType);
				
		DatiUfficioCompetenteType uffCompType = new DatiUfficioCompetenteType();
		uffCompType.setIDUFFICIO(BigInteger.valueOf(uffOwner));
		uffCompType.setIDPROCEDIMENTO(BigInteger.valueOf(idProcedimento));
		
		String numP = null;
		String annoP = null;
		
		TProcedimento procRecord = poliziaDao.getProcedimento(idProcedimento, uffOwner);
		
		if(procRecord.getNumeAnnoProc() != null){
			numP = procRecord.getNumeAnnoProc().toString(); 
		}
		
		if(procRecord.getNumeProc() != null){
			annoP = procRecord.getNumeProc().toString(); 
		}
		
		String descrNumProc = formattaNumeroAnno(numP, annoP);
		
		uffCompType.setDESCRNUMPROC(descrNumProc);
		request.setUfficioCompetente(uffCompType);
		
		DatiDecretoType decretoType = new DatiDecretoType();
	    String numD = null;
	    String annoD = null;
	    
	    TAttoId attoId = new TAttoId();
	    attoId.setCodiKIdAtto(idAtto);
	    attoId.setCodiKIdUffOwner(uffOwner);
		
	    TAtto attoRecord = attoDao.getAttoById(attoId);
			    
		TDecreto decrRecord = attoDao.getTDecreto(idAtto, uffOwner);
		decretoType.setIDDECRETO(BigInteger.valueOf(idAtto));
		
		if(decrRecord != null && decrRecord.getNumeAnnoAtto() != null){
			numD = decrRecord.getNumeAnnoAtto().toString(); 
		}
		
		if(decrRecord != null && decrRecord.getNumeAtto() != null){
			annoD = decrRecord.getNumeAtto().toString(); 
		}
		
		String descrNumDecr = formattaNumeroAnno(numD, annoD);
		
		if(attoRecord.getDataEmissione() != null){
			decretoType.setDATAEMISDECRETO(dfDate.format(attoRecord.getDataEmissione()));
		}
		
		if(attoRecord.getDataDeposito() != null){
			decretoType.setDATADEPDECRETO(dfDate.format(attoRecord.getDataDeposito()));
		}
		
		decretoType.setDESCRNUMDECRETO(descrNumDecr);
	    request.setDecreto(decretoType);

		//RECUPERARE PERSONE FISICHE ASSOCIATE AL PROCEDIMENTO/ATTO
		List<TRelProcSogint> listaSoggettiAtto = getTRelProcSogintList(idProcedimento, uffOwner);//poliziaDao.getTRelProcSogintList(idProcedimento, uffOwner);
		
		SoggettiCoinvoltiPersonaFisicaType soggettiCoinvoltiPersonaFisicaType = null;
		
		DatiAnagraficiPersonaFisicaType personaF = null;
		
		DatiMisuraPersonalePrimoGradoType misuraPersonaleType = null;
		
		if(listaSoggettiAtto != null){
			
	    	Iterator<TRelProcSogint> itr = listaSoggettiAtto.iterator();
	        int counter = 0;
	    	while(itr.hasNext()) {
	    		counter ++;
	    		TRelProcSogint rowSogInt = itr.next();
	    		//se idruolo = 1 verificare se il soggetto è persona fisica
	    		if(rowSogInt.getTTpTipoRuolo().getCodiKIdRuolo() == 1){ 
	    
	    			String tipoSogg =  poliziaDao.getTipoSoggetto(rowSogInt.getId().getCodiKIdSoggInt(),uffOwner);
	        	
	        		if(tipoSogg.equalsIgnoreCase(Constants.SOGGETTO_FISICO)){
	        			
		        		TPersonaFisica persF = poliziaDao.getPersonaFisica(rowSogInt.getId().getCodiKIdSoggInt(), rowSogInt.getTRelProcSogintVer().getId().getCodiKIdVer(), uffOwner);
		        		
		        		//se la persona è soggetto fisico verifico che abbia misure personali associate
		        		List<TMisuraPers> misureSoggetto = poliziaDao.listaMisurePersonaliByIdSoggetto(persF.getId().getCodiKIdPersonaFis(), idProcedimento, idAtto, uffOwner);
		        		
		        		//se ci sono misure legate al soggetto aggiungo il soggetto e tutte le misure collegate.
		        		if (misureSoggetto !=null && misureSoggetto.size() > 0 ){ 
		        	    	Iterator<TMisuraPers> iter = misureSoggetto.iterator();
		        	        int contaMisure = 0;
		        	        
		        	    	while(iter.hasNext()) {
		        	    		contaMisure ++;
		        	    		
		        	    		misuraPersonaleType = new DatiMisuraPersonalePrimoGradoType();
		        	    		
		        	    		TMisuraPers rowMisura = iter.next();
		        	    		
		        	    		misuraPersonaleType.setNUMEPROGRMISPERS(BigInteger.valueOf(contaMisure));
		        	    		misuraPersonaleType.setNUMEPROGPROP(BigInteger.valueOf(counter));
		        	    		
		        	    		//devo vedere quale misura personale è relativa al procedimento-atto-uffowner per aggiungere i dati corretti
		        				Set<TRelAttoMisPers> rowRelaz = rowMisura.getTRelAttoMisPerses();
		        				Iterator<TRelAttoMisPers> itRelAtto = rowRelaz.iterator();
		        				
		        				while(itRelAtto.hasNext()) 
		        				{
		        					TRelAttoMisPers rowRelAtto = itRelAtto.next();
	        						
		        					if (rowRelAtto.getId().getCodiKIdProc() == idProcedimento && 
		        						rowRelAtto.getId().getCodiKIdAtto() == idAtto && 
		        						rowRelAtto.getId().getCodiKIdUffOwner() == uffOwner && 
		        						rowRelAtto.getId().getCodiKIdMisuraPers() == rowMisura.getId().getCodiKIdMisura()
		        						) 
		        					{
		        					
				        	    		if (rowRelAtto.getTAnNormative() != null){
				        	    			misuraPersonaleType.setIDNORMMISPERS(""+rowRelAtto.getTAnNormative().getCodiKIdNormativa());
				        	    		}
				        	    		TTpTipoUfficio tipoUfficio = commonDao.getTipoUfficioByIdUfficio(uffOwner);
				        	    		if(tipoUfficio.getCodiKIdTipoUff()==19 && rowRelAtto.getTTpEsitoByCodiFkIdEsito1() != null)
				        	    		{
			        						misuraPersonaleType.setIDESITO(""+rowRelAtto.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito());
			        					}
				        	    		if(tipoUfficio.getCodiKIdTipoUff() == 6)
				        	    		{
				        	    			 if(rowRelAtto.getTTpEsitoByCodiFkIdEsito3() != null)
				        	    				 misuraPersonaleType.setIDESITO(""+rowRelAtto.getTTpEsitoByCodiFkIdEsito3().getCodiKIdEsito());
				        	    			 else
				        	    			 {
				        	    				 if(rowRelAtto.getTTpEsitoByCodiFkIdEsito2() != null)
					        	    				 misuraPersonaleType.setIDESITO(""+rowRelAtto.getTTpEsitoByCodiFkIdEsito2().getCodiKIdEsito());
				        	    			 }
			        					}
			        					
		        						misuraPersonaleType.setIDTIPOMISPERS(""+rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura());
		        						
		        						if(rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 1 || rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 2 || rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 3){
		        							misuraPersonaleType.setDESCRMISPERS(getMisPersAnniMesiGiorni(rowRelAtto.getNumeDurataAnni(), rowRelAtto.getNumeDurataMesi(), rowRelAtto.getNumeDurataGiorni(), rowRelAtto.getDescLoc()));
		        							
		        						}else if (rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 25){
		        							
		        							if(rowRelAtto.getTCauzione() != null && rowRelAtto.getTCauzione().getId() != null && rowRelAtto.getTCauzione().getId().getCodiKIdCauzione() >0 ){
		        							TCauzioneId idCauzione = new TCauzioneId();
		        							idCauzione.setCodiKIdCauzione(rowRelAtto.getTCauzione().getId().getCodiKIdCauzione());
		        							idCauzione.setCodiKIdUffOwner(uffOwner);
		        							TCauzione cauzione = poliziaDao.getCauzionebyId(idCauzione);
		        							misuraPersonaleType.setDESCRMISPERS(cauzione.getImpoCauzione().toString());
		        							}
		        						}
		        						request.getMisuraPersonale().add(misuraPersonaleType);
		        					}
		        				}
		        	    	}	
		        				//fine while aggiungere dati persona!!!!
			        			soggettiCoinvoltiPersonaFisicaType = new SoggettiCoinvoltiPersonaFisicaType();
			        			soggettiCoinvoltiPersonaFisicaType.setNUMEPROG(BigInteger.valueOf(counter));
				        		soggettiCoinvoltiPersonaFisicaType.setIDSOGGETTO(BigInteger.valueOf(persF.getId().getCodiKIdPersonaFis()));
				        		soggettiCoinvoltiPersonaFisicaType.setIDTIPOSOGGETTO(tipoSogg);
				        		
				        		if(rowSogInt.getTTpTipoPrevProc() != null){
				        			soggettiCoinvoltiPersonaFisicaType.setIDNORMATIVA(BigInteger.valueOf(rowSogInt.getTTpTipoPrevProc().getCodiKIdTipoNorm()));
				        		}
				        		
		        				personaF = new DatiAnagraficiPersonaFisicaType();
				        		
				        		personaF.setDESCRCOGNOME(persF.getDescCognome());
				        		personaF.setDESCRNOME(persF.getDescNome());
				        		
				        		if(persF.getDataNascita() != null){
				        			personaF.setDATANASCITA(dfDate.format(persF.getDataNascita()));
				        		}
				        		
				        		if(persF.getTAnStatoByCodiFkIdStatoNasc() != null && persF.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato() > 0){
				        			TAnStato statoNasc = commonDao.getStatoById(persF.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato());
					        		personaF.setIDISTATSTATONAS(statoNasc.getDescIstatStato());
				        		}
				        		
				        		if(persF.getTAnComuniByCodiFkIdComNasc() != null && persF.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune() > 0){
				        			TAnComuni comuneNasc = commonDao.getComuneById(persF.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune());
					        		personaF.setIDISTATCOMUNENAS(comuneNasc.getDescIstatComune());
				        		}
				        		
				        		personaF.setDESCRCOMUNENAS(persF.getDescComEsteroNasc());

			    				//aggiungo persona fisica all'array di appoggio
		        				soggettiCoinvoltiPersonaFisicaType.getDatiAnagraficiPersonaFisica().add(personaF);
		        				request.getSoggettiCoinvoltiPersonaFisica().add(soggettiCoinvoltiPersonaFisicaType);
        					}
		        		}
	        		}
	    		}
	    	}

		return request;
	}
	
	public DecisorioAggravamentoMisuraPersonaleResponse inviaDecisorioAggravamentoMisuraPersonale(DecisorioAggravamentoMisuraPersonaleRequest request) {
		DecisorioAggravamentoMisuraPersonaleResponse response = poliziaServiceWS.inviaDecisorioAggravamentoMisuraPersonale(request);
		
		if(response != null)
			return response;
		else
			return null;
	}

	// 8.19
	public DefinitivitaAggravamentoMisuraPersonaleRequest createDefinitivitaAggravamentoMisuraPersonaleRequest(long idProcedimento, long idAtto, int uffOwner, int uffDest,	HashMap<Long, Date> misureDateDefinitive) {
		DefinitivitaAggravamentoMisuraPersonaleRequest request = new DefinitivitaAggravamentoMisuraPersonaleRequest();
		DatiAutoritaIdType autoritaType = new DatiAutoritaIdType(); 
		autoritaType.setIDAUTORITA(BigInteger.valueOf(uffDest));
		request.setAutoritaRicevente(autoritaType);
		
	    DatiUfficioCompetenteType ufficioType = new DatiUfficioCompetenteType();
	    ufficioType.setIDUFFICIO(BigInteger.valueOf(uffOwner));
	    ufficioType.setIDPROCEDIMENTO(BigInteger.valueOf(idProcedimento));
	    String numProc = getNumeProc(idProcedimento,uffOwner);
	    ufficioType.setDESCRNUMPROC(numProc);
	    
	    request.setUfficioCompetente(ufficioType);
	    
	    DatiDecretoDefinitivitaType decretoType = new DatiDecretoDefinitivitaType();
	    String numD = null;
	    String annoD = null;
	    
		TDecreto decrRecord = attoDao.getTDecreto(idAtto, uffOwner);
		decretoType.setIDDECRETO(BigInteger.valueOf(idAtto));
		
		if(decrRecord != null && decrRecord.getNumeAnnoAtto() != null){
			numD = decrRecord.getNumeAnnoAtto().toString(); 
		}
		
		if(decrRecord != null && decrRecord.getNumeAtto() != null){
			annoD = decrRecord.getNumeAtto().toString(); 
		}
		
		String descrNumDecr = formattaNumeroAnno(numD, annoD);
		
		decretoType.setDESCRNUMDECRETO(descrNumDecr);
	    request.setDecreto(decretoType);
	    
	    SoggettiCoinvoltiPersonaFisicaType soggettiCoinvoltiPersonaFisicaType = null ;
	    
	    DatiMisuraPersonaleDefinitivitaType misuraPersonaleType = null;
	    
	    
		//RECUPERARE PERSONE FISICHE ASSOCIATE AL PROCEDIMENTO/ATTO
		List<TRelProcSogint> listaSoggettiAtto = getTRelProcSogintList(idProcedimento, uffOwner);//poliziaDao.getTRelProcSogintList(idProcedimento, uffOwner);
		
		DatiAnagraficiPersonaFisicaType personaF = null;
		
		if(listaSoggettiAtto != null){
			
	    	Iterator<TRelProcSogint> itr = listaSoggettiAtto.iterator();
	        int counter = 0;
	        long idSoggTmp = 0L;
	    	while(itr.hasNext()) 
	    	{
	    		counter ++;
	    		TRelProcSogint rowSogInt = itr.next();
	    		//se idruolo = 1 verificare se il soggetto è persona fisica
	    		if(rowSogInt.getTTpTipoRuolo().getCodiKIdRuolo() == 1)
	    		{ 
	    
	    			String tipoSogg =  poliziaDao.getTipoSoggetto(rowSogInt.getId().getCodiKIdSoggInt(),uffOwner);
	        	
	        		if(tipoSogg.equalsIgnoreCase(Constants.SOGGETTO_FISICO))
	        		{
	        			
	        			TPersonaFisica persF = poliziaDao.getPersonaFisica(rowSogInt.getId().getCodiKIdSoggInt(), rowSogInt.getTRelProcSogintVer().getId().getCodiKIdVer(), uffOwner);
		        		
		        		//se la persona è soggetto fisico verifico che abbia misure personali associate
		        		List<TMisuraPers> misureSoggetto = poliziaDao.listaMisurePersonaliByIdSoggetto(persF.getId().getCodiKIdPersonaFis(), idProcedimento, idAtto, uffOwner);
		        		
		        		//se ci sono misure legate al soggetto aggiungo il soggetto e tutte le misure collegate.
		        		if (misureSoggetto !=null && misureSoggetto.size() > 0 )
		        		{
		        			
		        	    	Iterator<TMisuraPers> iter = misureSoggetto.iterator();
		        	        int contaMisure = 0;
		        	        
		        	    	while(iter.hasNext()) 
		        	    	{
		        	    		misuraPersonaleType = new DatiMisuraPersonaleDefinitivitaType();
		        	    		
		        	    		TMisuraPers rowMisura = iter.next();
		        	    		
		        	    		//devo vedere quale misura personale è relativa al procedimento-atto-uffowner per aggiungere i dati corretti
		        				Set<TRelAttoMisPers> rowRelaz = rowMisura.getTRelAttoMisPerses();
		        				Iterator<TRelAttoMisPers> itRelAtto = rowRelaz.iterator();
		        				
		        				while(itRelAtto.hasNext()) 
		        				{
		        					TRelAttoMisPers rowRelAtto = itRelAtto.next();
	        						// se la misura non è tra quelle passate nell'hashmap di input non va aggiunta la misura
		        					if(misureDateDefinitive.containsKey(new Long(rowRelAtto.getId().getCodiKIdMisuraPers())))
		        					{
			        					//setto la data definitività passata nell'hashmap relativa all'id misura
			        					misuraPersonaleType.setDATADEFINITIVITA(dfDate.format(misureDateDefinitive.get(new Long(rowRelAtto.getId().getCodiKIdMisuraPers()))));
			        						
				        	    		if (rowRelAtto.getTAnNormative() != null){
				        	    			misuraPersonaleType.setIDNORMMISPERS(""+rowRelAtto.getTAnNormative().getCodiKIdNormativa());
				        	    		}
	
				        	    		if(rowRelAtto.getTTpEsitoByCodiFkIdEsito1() != null){
			        						misuraPersonaleType.setIDESITO1(""+rowRelAtto.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito());
			        					}
				        	    		
				        	    		if(rowRelAtto.getTTpEsitoByCodiFkIdEsito2() != null){
			        						misuraPersonaleType.setIDESITO2(""+rowRelAtto.getTTpEsitoByCodiFkIdEsito2().getCodiKIdEsito());
			        					}
				        	    		
				        	    		if(rowRelAtto.getTTpEsitoByCodiFkIdEsito3() != null){
			        						misuraPersonaleType.setIDESITO3(""+rowRelAtto.getTTpEsitoByCodiFkIdEsito3().getCodiKIdEsito());
			        					}
		        						
			        					if (rowRelAtto.getId().getCodiKIdProc() == idProcedimento && 
			        						rowRelAtto.getId().getCodiKIdAtto() == idAtto && 
			        						rowRelAtto.getId().getCodiKIdUffOwner() == uffOwner && 
			        						rowRelAtto.getId().getCodiKIdMisuraPers() == rowMisura.getId().getCodiKIdMisura()
			        						)
			        					{
			        						
			        						misuraPersonaleType.setIDTIPOMISPERS(""+rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura());
			        						
			        						//correzione 19/10/2015
			        						if (rowRelAtto.getTAnNormative() != null){
			        							misuraPersonaleType.setIDNORMMISPERS(""+rowRelAtto.getTAnNormative().getCodiKIdNormativa());
			        						}
			        					
			        						if(rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 1 || rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 2 || rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 3){
			        							misuraPersonaleType.setDESCRMISPERS(getMisPersAnniMesiGiorni(rowRelAtto.getNumeDurataAnni(), rowRelAtto.getNumeDurataMesi(), rowRelAtto.getNumeDurataGiorni(), rowRelAtto.getDescLoc()));
			        							
			        						}else if (rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 25){
			        							
			        							if(rowRelAtto.getTCauzione() != null && rowRelAtto.getTCauzione().getId() != null && rowRelAtto.getTCauzione().getId().getCodiKIdCauzione() >0 ){
			        							TCauzioneId idCauzione = new TCauzioneId();
			        							idCauzione.setCodiKIdCauzione(rowRelAtto.getTCauzione().getId().getCodiKIdCauzione());
			        							idCauzione.setCodiKIdUffOwner(uffOwner);
			        							TCauzione cauzione = poliziaDao.getCauzionebyId(idCauzione);
			        							misuraPersonaleType.setDESCRMISPERS(cauzione.getImpoCauzione().toString());
			        							}
			        						}
			        						contaMisure ++;
			        						misuraPersonaleType.setNUMEPROGRMISPERS(BigInteger.valueOf(contaMisure));
					        	    		misuraPersonaleType.setNUMEPROGPROP(BigInteger.valueOf(counter));
					        	    		
			        					request.getMisuraPersonale().add(misuraPersonaleType);
			        					}
		        					
		        					}
		        				}//fine while aggiungere dati persona!!!!
		        				
		        				if(idSoggTmp != rowSogInt.getId().getCodiKIdSoggInt())
		        				{
		        					idSoggTmp = rowSogInt.getId().getCodiKIdSoggInt();
				        			soggettiCoinvoltiPersonaFisicaType = new SoggettiCoinvoltiPersonaFisicaType();
				        			soggettiCoinvoltiPersonaFisicaType.setNUMEPROG(BigInteger.valueOf(counter));
					        		soggettiCoinvoltiPersonaFisicaType.setIDSOGGETTO(BigInteger.valueOf(persF.getId().getCodiKIdPersonaFis()));
					        		soggettiCoinvoltiPersonaFisicaType.setIDTIPOSOGGETTO(tipoSogg);
					        		if(rowSogInt.getTTpTipoPrevProc() != null){
					        			soggettiCoinvoltiPersonaFisicaType.setIDNORMATIVA(BigInteger.valueOf(rowSogInt.getTTpTipoPrevProc().getCodiKIdTipoNorm()));
					        		}
			        				personaF = new DatiAnagraficiPersonaFisicaType();
					        		
					        		personaF.setDESCRCOGNOME(persF.getDescCognome());
					        		personaF.setDESCRNOME(persF.getDescNome());
					        		
					        		if(persF.getDataNascita() != null){
					        			personaF.setDATANASCITA(dfDate.format(persF.getDataNascita()));
					        		}
					        		
					        		if(persF.getTAnStatoByCodiFkIdStatoNasc() != null && persF.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato() > 0){
					        			TAnStato statoNasc = commonDao.getStatoById(persF.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato());
						        		personaF.setIDISTATSTATONAS(statoNasc.getDescIstatStato());
					        		}
					        		
					        		if(persF.getTAnComuniByCodiFkIdComNasc() != null && persF.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune() > 0){
					        			TAnComuni comuneNasc = commonDao.getComuneById(persF.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune());
						        		personaF.setIDISTATCOMUNENAS(comuneNasc.getDescIstatComune());
					        		}
					        		
					        		personaF.setDESCRCOMUNENAS(persF.getDescComEsteroNasc());
					        		
			        				//aggiungo persona fisica all'array di appoggio
			        				soggettiCoinvoltiPersonaFisicaType.getDatiAnagraficiPersonaFisica().add(personaF);
			        				request.getSoggettiCoinvoltiPersonaFisica().add(soggettiCoinvoltiPersonaFisicaType);
		        				}
		        	    	}
	        		}
	    		} //fine if soggetto fisico
	    	}//fine if tipo ruolo
	    	}//fine while TRelProcSogint
		}else{//se listaSoggettiAtto = null
			//setto valori di default
			misuraPersonaleType = new DatiMisuraPersonaleDefinitivitaType();
			soggettiCoinvoltiPersonaFisicaType = new SoggettiCoinvoltiPersonaFisicaType();
			personaF = new DatiAnagraficiPersonaFisicaType();
			soggettiCoinvoltiPersonaFisicaType.getDatiAnagraficiPersonaFisica().add(personaF);
			request.getMisuraPersonale().add(misuraPersonaleType);
			request.getSoggettiCoinvoltiPersonaFisica().add(soggettiCoinvoltiPersonaFisicaType);
		}
		
		return request;
	}
	
	public DefinitivitaAggravamentoMisuraPersonaleResponse inviaDefinitivitaAggravamentoMisuraPersonale(DefinitivitaAggravamentoMisuraPersonaleRequest request) {
		DefinitivitaAggravamentoMisuraPersonaleResponse response = poliziaServiceWS.inviaDefinitivitaAggravamentoMisuraPersonale(request);
		
		if(response != null)
			return response;
		else
			return null;
	}
	
	//8.20
	public RichiestaModificaRevocaMisuraPersonaleRequest createRichiestaModificaRevocaMisuraPersonaleRequest(long idProcedimento, long idAtto, int uffOwner, int uffDest, long idSoggetto, long idMisura, long idProcedimentoOld, int ufficioProcOld) {
		RichiestaModificaRevocaMisuraPersonaleRequest request = new RichiestaModificaRevocaMisuraPersonaleRequest();

		DatiAutoritaIdType autType = new DatiAutoritaIdType();
		autType.setIDAUTORITA(BigInteger.valueOf(uffDest));
		request.setAutoritaRicevente(autType);
		
		DatiUfficioCompetenteType ufficioType = new DatiUfficioCompetenteType();
	    ufficioType.setIDUFFICIO(BigInteger.valueOf(uffOwner));
	    ufficioType.setIDPROCEDIMENTO(BigInteger.valueOf(idProcedimento));
	    String numProc = getNumeProc(idProcedimento,uffOwner);
	    ufficioType.setDESCRNUMPROC(numProc);

	    request.setUfficioCompetente(ufficioType);
	    
	    DatiUfficioDecidenteType uffDecidenteType = new DatiUfficioDecidenteType();
	    uffDecidenteType.setIDUFFICIO(BigInteger.valueOf(ufficioProcOld));
	    uffDecidenteType.setIDPROCEDIMENTO(BigInteger.valueOf(idProcedimentoOld));
	    
		TProcedimento procRecordOld = poliziaDao.getProcedimento(idProcedimentoOld, ufficioProcOld);
		String numPOld = null;
		String annoPOld = null;
		
		if(procRecordOld!=null){
			if(procRecordOld.getNumeAnnoProc() != null){
				numPOld = procRecordOld.getNumeAnnoProc().toString(); 
			}
			
			if(procRecordOld.getNumeProc() != null){
				annoPOld = procRecordOld.getNumeProc().toString(); 
			}
		}
		
		String descrNumProcOld = formattaNumeroAnno(numPOld, annoPOld);
		
		uffDecidenteType.setDESCRNUMPROC(descrNumProcOld);
		request.setUfficioDecidente(uffDecidenteType);
	    
		TIstanzaId istanzaId = new TIstanzaId(idAtto, uffOwner);
		TIstanza istanzaRecord = poliziaDao.getIstanzabyId(istanzaId);
		
	    DatiSoggettoRichiestaType soggRichType = new DatiSoggettoRichiestaType();
	    soggRichType.setIDSOGGETTO(BigInteger.valueOf(idSoggetto));
	    soggRichType.setDESCRCOGNOME(istanzaRecord.getDescCognProp());
	    soggRichType.setDESCRNOME(istanzaRecord.getDescNomeProp());
	    

	    
	    DatiRichiestaModificaRevocaMisuraType richiestaType = new DatiRichiestaModificaRevocaMisuraType();
	    	
		//RECUPERARE PERSONE FISICHE ASSOCIATE AL PROCEDIMENTO/ATTO
		List<TRelProcSogint> listaSoggettiAtto = getTRelProcSogintList(idProcedimento, uffOwner);//poliziaDao.getTRelProcSogintList(idProcedimento, uffOwner);
		
		if(listaSoggettiAtto != null){
			
	    	Iterator<TRelProcSogint> itr = listaSoggettiAtto.iterator();
	    	while(itr.hasNext()) {

	    		TRelProcSogint rowSogInt = itr.next();
	    		//se il soggetto è quello passato in input ......
	    		
	    		if(rowSogInt.getId().getCodiKIdSoggInt() == idSoggetto){ 
	    
	    			String tipoSogg =  poliziaDao.getTipoSoggetto(rowSogInt.getId().getCodiKIdSoggInt(),uffOwner);
	        	
	        		if(tipoSogg.equalsIgnoreCase(Constants.SOGGETTO_FISICO)){

	        			setSoggettoMisuraPersonale(request.getPersonaFisica(), richiestaType, rowSogInt, idProcedimento, idAtto, uffOwner, idMisura );	        			
	        		}
	    		}
	    	}
		}
	    
		return request;
	}
	
	public RichiestaModificaRevocaMisuraPersonaleResponse inviaRichiestaModificaRevocaMisuraPersonale(RichiestaModificaRevocaMisuraPersonaleRequest request) {
		RichiestaModificaRevocaMisuraPersonaleResponse response = poliziaServiceWS.inviaRichiestaModificaRevocaMisuraPersonale(request);
		
		if(response != null)
			return response;
		else
			return null;
	}
	
	//8.21
	public DecisorioRevocaModificaMisuraPersonaleRequest createDecisorioRevocaModificaMisuraPersonaleRequest(long idProcedimento, long idAtto, int uffOwner, int uffDest) {
		DecisorioRevocaModificaMisuraPersonaleRequest request = new DecisorioRevocaModificaMisuraPersonaleRequest();
		DatiAutoritaIdType autType = new DatiAutoritaIdType();
		autType.setIDAUTORITA(BigInteger.valueOf(uffDest));
		request.setAutoritaRicevente(autType);
		
		DatiUfficioCompetenteType ufficioType = new DatiUfficioCompetenteType();
	    ufficioType.setIDUFFICIO(BigInteger.valueOf(uffOwner));
	    ufficioType.setIDPROCEDIMENTO(BigInteger.valueOf(idProcedimento));
	    String numProc = getNumeProc(idProcedimento,uffOwner);
	    ufficioType.setDESCRNUMPROC(numProc);
	    
	    request.setUfficioCompetente(ufficioType);
	     
		DatiDecretoType decretoType = new DatiDecretoType();
	    String numD = null;
	    String annoD = null;
	    
	    TAttoId attoId = new TAttoId();
	    attoId.setCodiKIdAtto(idAtto);
	    attoId.setCodiKIdUffOwner(uffOwner);
		
	    TAtto attoRecord = attoDao.getAttoById(attoId);
			    
		TDecreto decrRecord = attoDao.getTDecreto(idAtto, uffOwner);
		decretoType.setIDDECRETO(BigInteger.valueOf(idAtto));
		
		if(decrRecord != null && decrRecord.getNumeAnnoAtto() != null){
			numD = decrRecord.getNumeAnnoAtto().toString(); 
		}
		
		if(decrRecord != null && decrRecord.getNumeAtto() != null){
			annoD = decrRecord.getNumeAtto().toString(); 
		}
		
		String descrNumDecr = formattaNumeroAnno(numD, annoD);
		
		if(attoRecord.getDataEmissione() != null){
			decretoType.setDATAEMISDECRETO(dfDate.format(attoRecord.getDataEmissione()));
		}
		
		if(attoRecord.getDataDeposito() != null){
			decretoType.setDATADEPDECRETO(dfDate.format(attoRecord.getDataDeposito()));
		}
		
		decretoType.setDESCRNUMDECRETO(descrNumDecr);
	    request.setDecreto(decretoType);
	    
		//RECUPERARE PERSONE FISICHE ASSOCIATE AL PROCEDIMENTO/ATTO
		List<TRelProcSogint> listaSoggettiAtto = getTRelProcSogintList(idProcedimento, uffOwner);//poliziaDao.getTRelProcSogintList(idProcedimento, uffOwner);
		
		SoggettiCoinvoltiPersonaFisicaType soggCoinv = null;
		
		DatiAnagraficiPersonaFisicaType personaF = null;
		
		if(listaSoggettiAtto != null){
			
	    	Iterator<TRelProcSogint> itr = listaSoggettiAtto.iterator();
	        int counter = 0;
	    	while(itr.hasNext()) {

	    		TRelProcSogint rowSogInt = itr.next();
	    		//se idruolo = 1 o 2 verificare se il soggetto è persona fisica
	    		if(rowSogInt.getTTpTipoRuolo().getCodiKIdRuolo() == 1){ 
	    			
	    			String tipoSogg =  poliziaDao.getTipoSoggetto(rowSogInt.getId().getCodiKIdSoggInt(),uffOwner);

	        		if(tipoSogg.equalsIgnoreCase(Constants.SOGGETTO_FISICO)){
	    	    		counter ++;	        			
		    			soggCoinv = new  SoggettiCoinvoltiPersonaFisicaType();
		    			soggCoinv.setNUMEPROG(BigInteger.valueOf(counter));
		    			soggCoinv.setIDSOGGETTO(BigInteger.valueOf(rowSogInt.getId().getCodiKIdSoggInt()));
		    			soggCoinv.setIDTIPOSOGGETTO(tipoSogg);
		    			if(rowSogInt!=null && rowSogInt.getTTpTipoPrevProc()!=null && rowSogInt.getTTpTipoPrevProc().getCodiKIdTipoNorm()!=0)
		    				soggCoinv.setIDNORMATIVA(BigInteger.valueOf(rowSogInt.getTTpTipoPrevProc().getCodiKIdTipoNorm()));
		    			personaF = new DatiAnagraficiPersonaFisicaType();
	        			setDatiAnagraficiPersonaFisicaType(personaF, rowSogInt, uffOwner);
	        			soggCoinv.getDatiAnagraficiPersonaFisica().add(personaF);
	        			setDatiMisuraPersonalePersonaType(request.getMisuraPersonale(), counter, rowSogInt.getId().getCodiKIdSoggInt(),idProcedimento, idAtto, rowSogInt, uffOwner);
	        			request.getSoggettiCoinvoltiPersonaFisica().add(soggCoinv);
	        		}
	    		}
	    	}
		}
	    
		return request;
	}
	
	public DecisorioRevocaModificaMisuraPersonaleResponse inviaDecisorioRevocaModificaMisuraPersonale(DecisorioRevocaModificaMisuraPersonaleRequest request) {
		DecisorioRevocaModificaMisuraPersonaleResponse response = poliziaServiceWS.inviaDecisorioRevocaModificaMisuraPersonale(request);
		
		if(response != null)
			return response;
		else
			return null;
	}
	
	//8.22
	public DefinitivitaRevocaModificaMisuraPersonaleRequest createDefinitivitaRevocaModificaMisuraPersonaleRequest(long idProcedimento, long idAtto, int uffOwner, int uffDest,	HashMap<Long, Date> misureDateDefinitive) {
		DefinitivitaRevocaModificaMisuraPersonaleRequest request = new DefinitivitaRevocaModificaMisuraPersonaleRequest();
		
		DatiAutoritaIdType autType = new DatiAutoritaIdType();
		autType.setIDAUTORITA(BigInteger.valueOf(uffDest));
		request.setAutoritaRicevente(autType);
		
		DatiUfficioCompetenteType ufficioType = new DatiUfficioCompetenteType();
	    ufficioType.setIDUFFICIO(BigInteger.valueOf(uffOwner));
	    ufficioType.setIDPROCEDIMENTO(BigInteger.valueOf(idProcedimento));
	    String numProc = getNumeProc(idProcedimento,uffOwner);
	    ufficioType.setDESCRNUMPROC(numProc);
	    
	    request.setUfficioCompetente(ufficioType);
	    
	    DatiDecretoDefinitivitaType decretoType = new DatiDecretoDefinitivitaType();
	    String numD = null;
	    String annoD = null;
	    
		TDecreto decrRecord = attoDao.getTDecreto(idAtto, uffOwner);
		decretoType.setIDDECRETO(BigInteger.valueOf(idAtto));
		
		if(decrRecord != null && decrRecord.getNumeAnnoAtto() != null){
			numD = decrRecord.getNumeAnnoAtto().toString(); 
		}
		
		if(decrRecord != null && decrRecord.getNumeAtto() != null){
			annoD = decrRecord.getNumeAtto().toString(); 
		}
		
		String descrNumDecr = formattaNumeroAnno(numD, annoD);
		
		decretoType.setDESCRNUMDECRETO(descrNumDecr);
	    request.setDecreto(decretoType);
		
	    SoggettiCoinvoltiPersonaFisicaType soggettiCoinvoltiPersonaFisicaType = null ;
	    
	    DatiMisuraPersonaleDefinitivitaType misuraPersonaleType = null;
	    
		//RECUPERARE PERSONE ASSOCIATE AL PROCEDIMENTO/ATTO
		List<TRelProcSogint> listaSoggettiAtto = getTRelProcSogintList(idProcedimento, uffOwner);//poliziaDao.getTRelProcSogintList(idProcedimento, uffOwner);
		
		DatiAnagraficiPersonaFisicaType personaF = null;
		
		if(listaSoggettiAtto != null)
		{
			Iterator<TRelProcSogint> itr = listaSoggettiAtto.iterator();
	        
	    	//inizializzo contatore ad 1 perchè occorre utilizzarlo all'interno delle misure prima di incrementarlo all'aggiunta della persona
	    	int counter = 1;
	    	while(itr.hasNext()) //ciclo dei soggetti
	    	{
	    		TRelProcSogint rowSogInt = itr.next();
	    		//se idruolo = 1 verificare se il soggetto è persona fisica
	    		if(rowSogInt.getTTpTipoRuolo().getCodiKIdRuolo() == 1)
	    		{
	    			String tipoSogg =  poliziaDao.getTipoSoggetto(rowSogInt.getId().getCodiKIdSoggInt(),uffOwner);
	        	
	        		if(tipoSogg.equalsIgnoreCase(Constants.SOGGETTO_FISICO))
	        		{
	        			TPersonaFisica persF = poliziaDao.getPersonaFisica(rowSogInt.getId().getCodiKIdSoggInt(), rowSogInt.getTRelProcSogintVer().getId().getCodiKIdVer(), uffOwner);
		        		
		        		//se la persona è soggetto fisico verifico che abbia misure personali associate
		        		List<TMisuraPers> misureSoggetto = poliziaDao.listaMisurePersonaliByIdSoggetto(persF.getId().getCodiKIdPersonaFis(), idProcedimento, idAtto, uffOwner);
		        		
		        		//se ci sono misure legate al soggetto 
		        		if (misureSoggetto !=null && misureSoggetto.size() > 0 )
		        		{
		        			Iterator<TMisuraPers> iter = misureSoggetto.iterator();
		        	        int contaMisure = 0;
		        	        
		        	    	while(iter.hasNext())//ciclo sulle misure TMisuraPers
		        	    	{
		        	    		TMisuraPers rowMisura = iter.next();

		        	    		//devo vedere quale misura personale è relativa al procedimento-atto-uffowner per aggiungere i dati corretti
		        				Set<TRelAttoMisPers> rowRelaz = rowMisura.getTRelAttoMisPerses();
		        				Iterator<TRelAttoMisPers> itRelAtto = rowRelaz.iterator();
		        				
		        				while(itRelAtto.hasNext())
		        				{
		        					TRelAttoMisPers rowRelAtto = itRelAtto.next();
	        						// se la misura non è tra quelle passate nell'hashmap di input non va aggiunta la misura
		        					if(misureDateDefinitive.containsKey(new Long(rowRelAtto.getId().getCodiKIdMisuraPers()))
		        						&& (rowRelAtto.getId().getCodiKIdProc()==idProcedimento && rowRelAtto.getId().getCodiKIdAtto()==idAtto))
		        					{
		        				    	contaMisure ++;
					        	    		
					        	    	misuraPersonaleType = new DatiMisuraPersonaleDefinitivitaType();
					        	    	misuraPersonaleType.setNUMEPROGRMISPERS(BigInteger.valueOf(contaMisure));
					        	    	misuraPersonaleType.setNUMEPROGPROP(BigInteger.valueOf(counter));
			        					//setto la data definitività passata nell'hashmap relativa all'id misura
			        					misuraPersonaleType.setDATADEFINITIVITA(dfDate.format(misureDateDefinitive.get(new Long(rowRelAtto.getId().getCodiKIdMisuraPers()))));
	
				        	    		if (rowRelAtto.getTAnNormative() != null){
				        	    			misuraPersonaleType.setIDNORMMISPERS(""+rowRelAtto.getTAnNormative().getCodiKIdNormativa());
				        	    		}
	
				        	    		if(rowRelAtto.getTTpEsitoByCodiFkIdEsito1() != null){
			        						misuraPersonaleType.setIDESITO1(""+rowRelAtto.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito());
			        					}
				        	    		
				        	    		if(rowRelAtto.getTTpEsitoByCodiFkIdEsito2() != null){
			        						misuraPersonaleType.setIDESITO2(""+rowRelAtto.getTTpEsitoByCodiFkIdEsito2().getCodiKIdEsito());
			        					}
				        	    		
				        	    		if(rowRelAtto.getTTpEsitoByCodiFkIdEsito3() != null){
			        						misuraPersonaleType.setIDESITO3(""+rowRelAtto.getTTpEsitoByCodiFkIdEsito3().getCodiKIdEsito());
			        					}
		        						
			        					if (rowRelAtto.getId().getCodiKIdProc() == idProcedimento && 
			        						rowRelAtto.getId().getCodiKIdAtto() == idAtto && 
			        						rowRelAtto.getId().getCodiKIdUffOwner() == uffOwner && 
			        						rowRelAtto.getId().getCodiKIdMisuraPers() == rowMisura.getId().getCodiKIdMisura()) 
			        					{
			        						
			        						misuraPersonaleType.setIDTIPOMISPERS(""+rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura());
			        					
			        						//correzione 19/10/2015
			        						if (rowRelAtto.getTAnNormative() != null){
			        							misuraPersonaleType.setIDNORMMISPERS(""+rowRelAtto.getTAnNormative().getCodiKIdNormativa());
			        						}
			        						
			        						if(rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 1 || rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 2 || rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 3){
			        							misuraPersonaleType.setDESCRMISPERS(getMisPersAnniMesiGiorni(rowRelAtto.getNumeDurataAnni(), rowRelAtto.getNumeDurataMesi(), rowRelAtto.getNumeDurataGiorni(), rowRelAtto.getDescLoc()));
			        							
			        						}else if (rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 25)
			        						{
			        							
			        							if(rowRelAtto.getTCauzione() != null && rowRelAtto.getTCauzione().getId() != null && rowRelAtto.getTCauzione().getId().getCodiKIdCauzione() >0 )
			        							{
				        							TCauzioneId idCauzione = new TCauzioneId();
				        							idCauzione.setCodiKIdCauzione(rowRelAtto.getTCauzione().getId().getCodiKIdCauzione());
				        							idCauzione.setCodiKIdUffOwner(uffOwner);
				        							TCauzione cauzione = poliziaDao.getCauzionebyId(idCauzione);
				        							if(cauzione!=null && cauzione.getImpoCauzione()!=null)
				        								misuraPersonaleType.setDESCRMISPERS(cauzione.getImpoCauzione().toString());
			        							}
			        						}
			        					}
			        					request.getMisuraPersonale().add(misuraPersonaleType);
	
			        					if(contaMisure == 1){
					        				//se è stata aggiunta una misura aggiungere dati persona!!!!
						        			soggettiCoinvoltiPersonaFisicaType = new SoggettiCoinvoltiPersonaFisicaType();
						        			soggettiCoinvoltiPersonaFisicaType.setNUMEPROG(BigInteger.valueOf(counter));
							        		soggettiCoinvoltiPersonaFisicaType.setIDSOGGETTO(BigInteger.valueOf(persF.getId().getCodiKIdPersonaFis()));
							        		soggettiCoinvoltiPersonaFisicaType.setIDTIPOSOGGETTO(tipoSogg);
							        		if(rowSogInt.getTTpTipoPrevProc() != null){
							        			soggettiCoinvoltiPersonaFisicaType.setIDNORMATIVA(BigInteger.valueOf(rowSogInt.getTTpTipoPrevProc().getCodiKIdTipoNorm()));
							        		}
					        				personaF = new DatiAnagraficiPersonaFisicaType();
							        		
							        		personaF.setDESCRCOGNOME(persF.getDescCognome());
							        		personaF.setDESCRNOME(persF.getDescNome());
							        		
							        		if(persF.getDataNascita() != null){
							        			personaF.setDATANASCITA(dfDate.format(persF.getDataNascita()));
							        		}
							        		
							        		if(persF.getTAnStatoByCodiFkIdStatoNasc() != null && persF.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato() > 0){
							        			TAnStato statoNasc = commonDao.getStatoById(persF.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato());
								        		personaF.setIDISTATSTATONAS(statoNasc.getDescIstatStato());
							        		}
							        		
							        		if(persF.getTAnComuniByCodiFkIdComNasc() != null && persF.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune() > 0){
							        			TAnComuni comuneNasc = commonDao.getComuneById(persF.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune());
								        		personaF.setIDISTATCOMUNENAS(comuneNasc.getDescIstatComune());
							        		}
						        		
							        		personaF.setDESCRCOMUNENAS(persF.getDescComEsteroNasc());
		
						    				//aggiungo persona fisica all'array di appoggio
					        				soggettiCoinvoltiPersonaFisicaType.getDatiAnagraficiPersonaFisica().add(personaF);
					        				request.getSoggettiCoinvoltiPersonaFisica().add(soggettiCoinvoltiPersonaFisicaType);
		
					        				//incremento contatore dei soggetti
				        					counter ++;
			        					}
		        				  }
		        				}//end while TRelAttoMisPers
	        	    		}//end while TMisuraPers
		        		}//end if misureSoggetto
		       		}//end if soggetto fisico
	        	}//end if(rowSogInt.getTTpTipoRuolo().getCodiKIdRuolo()
	    	}//end while TRelProcSogint
		}//end if ci sono soggetti
		
		return request;
	}
	
	public DefinitivitaRevocaModificaMisuraPersonaleResponse inviaDefinitivitaRevocaModificaMisuraPersonale(DefinitivitaRevocaModificaMisuraPersonaleRequest request) {
		DefinitivitaRevocaModificaMisuraPersonaleResponse response = poliziaServiceWS.inviaDefinitivitaRevocaModificaMisuraPersonale(request);
		
		if(response != null)
			return response;
		else
			return null;
	}
	
	//8.23
	public DefinitivitaCorrezioneErroreDatiSoggettoRequest createDefinitivitaCorrezioneErroreDatiSoggettoRequest(long idProcedimento, long idSoggetto, int idVerOld, int idVerNew,	int uffOwner, int uffDest, long idAtto) {

			DefinitivitaCorrezioneErroreDatiSoggettoRequest request = new DefinitivitaCorrezioneErroreDatiSoggettoRequest();
			String num = null;
			String anno = null;
			
			DatiAutoritaIdType autoritaType = new DatiAutoritaIdType(); 
			autoritaType.setIDAUTORITA(BigInteger.valueOf(uffDest));		
			request.setAutoritaRicevente(autoritaType);
			
			DatiUfficioCompetenteType ufficioType = new DatiUfficioCompetenteType();
			
			TProcedimento procRecord = poliziaDao.getProcedimento(idProcedimento, uffOwner);
			if(procRecord.getNumeAnnoProc() != null){
				num = procRecord.getNumeAnnoProc().toString(); 
			}
			
			if(procRecord.getNumeProc() != null){
				anno = procRecord.getNumeProc().toString(); 
			}
			String descrNumProc = formattaNumeroAnno(num, anno);

			ufficioType.setIDUFFICIO(BigInteger.valueOf(uffOwner));
			ufficioType.setDESCRNUMPROC(descrNumProc);
			ufficioType.setIDPROCEDIMENTO(BigInteger.valueOf(idProcedimento));
			request.setUfficioCompetente(ufficioType);
			
			DatiDecretoDefinitivitaType decretoType = new DatiDecretoDefinitivitaType();
			
			String numD = null;
			String annoD = null;
			
			TDecreto decrRecord = attoDao.getTDecreto(idAtto, uffOwner);

			decretoType.setIDDECRETO(BigInteger.valueOf(idAtto));
			
			if(decrRecord.getNumeAnnoAtto() != null){
				numD = decrRecord.getNumeAnnoAtto().toString(); 
			}
			
			if(decrRecord.getNumeAtto() != null){
				annoD = decrRecord.getNumeAtto().toString(); 
			}
			
			String descrNumDecr = formattaNumeroAnno(numD, annoD);
			
			decretoType.setDESCRNUMDECRETO(descrNumDecr);
			
			request.setDecreto(decretoType);
			/*
			List<TRelProcSogint> listaSoggettiAtto = getTRelProcSogintList(idProcedimento, uffOwner);//poliziaDao.getTRelProcSogintList(idProcedimento, uffOwner);
			
			SoggettiCoinvoltiPersonaFisicaType soggCoinvType = new SoggettiCoinvoltiPersonaFisicaType();
			
			DatiAnagraficiPersonaFisicaType datiAnagType = new DatiAnagraficiPersonaFisicaType();
			
			if(listaSoggettiAtto != null){
				
		    	Iterator<TRelProcSogint> itr = listaSoggettiAtto.iterator();
		        int counter = 0;
		    	while(itr.hasNext()) {
		    		counter ++;
		    		TRelProcSogint rowSogInt = itr.next();
		        	String tipoSogg =  poliziaDao.getTipoSoggetto(rowSogInt.getId().getCodiKIdSoggInt(),uffOwner);
		        	
		        	if(tipoSogg.equalsIgnoreCase(Constants.SOGGETTO_FISICO)){
		        		
		        		TPersonaFisica persF = poliziaDao.getPersonaFisica(rowSogInt.getId().getCodiKIdSoggInt(), rowSogInt.getTRelProcSogintVer().getId().getCodiKIdVer(), uffOwner);
		        		
		        		soggCoinvType.setNUMEPROG(BigInteger.valueOf(counter));
		        		soggCoinvType.setIDSOGGETTO(BigInteger.valueOf(rowSogInt.getId().getCodiKIdSoggInt()));
		        		soggCoinvType.setIDTIPOSOGGETTO(tipoSogg);
		        		
		        		if(rowSogInt.getTTpTipoPrevProc() != null){
		        			soggCoinvType.setIDNORMATIVA(BigInteger.valueOf(rowSogInt.getTTpTipoPrevProc().getCodiKIdTipoNorm()));
		        		}
		        		
		        		datiAnagType.setDESCRCOGNOME(persF.getDescCognome());
		        		datiAnagType.setDESCRNOME(persF.getDescNome());
		        		
		        		if(persF.getDataNascita() != null){
		        		datiAnagType.setDATANASCITA(dfDate.format(persF.getDataNascita()));
		        		}
		        		
		        		if(persF.getTAnStatoByCodiFkIdStatoNasc() != null && persF.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato() > 0){
		        			TAnStato statoNasc = commonDao.getStatoById(persF.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato());
		        			datiAnagType.setIDISTATSTATONAS(statoNasc.getDescIstatStato());
		        		}
		        		
		        		if(persF.getTAnComuniByCodiFkIdComNasc() != null && persF.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune() > 0){
		        			TAnComuni comuneNasc = commonDao.getComuneById(persF.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune());
		        			datiAnagType.setIDISTATCOMUNENAS(comuneNasc.getDescIstatComune());
		        		}
		        		
		        		datiAnagType.setDESCRCOMUNENAS(persF.getDescComEsteroNasc());
		        		
		        		soggCoinvType.getDatiAnagraficiPersonaFisica().add(datiAnagType);
		        		
		        		request.getSoggettiCoinvoltiPersonaFisica().add(soggCoinvType);
		        		
		        	}
		    	}
			}*/
			
			
			//recupero del soggetto da T_REL_PROC_INTESTATARI perché si tratta di un cameralino
			List<IntestatarioDto> listaIntestatari = procedimentoDaoCam.getIntestatari(uffOwner, idProcedimento);
			if(listaIntestatari!=null && !listaIntestatari.isEmpty()) {
				int counter = 0;
				for (IntestatarioDto intestatarioDto : listaIntestatari) {
					if(intestatarioDto.getCODI_FK_ID_TIPO_SOGG()=='F') {
						counter++;
						SoggettiCoinvoltiPersonaFisicaType soggCoinvType = new SoggettiCoinvoltiPersonaFisicaType();
						soggCoinvType.setNUMEPROG(BigInteger.valueOf(counter));
//						soggCoinvType.setIDSOGGETTO(BigInteger.valueOf(intestatarioDto.getCODI_K_ID_INTESTATARIO().longValue()));
						soggCoinvType.setIDTIPOSOGGETTO(intestatarioDto.getCODI_FK_ID_TIPO_SOGG().toString());
						soggCoinvType.setIDNORMATIVA(null);
						
						TPersonaFisicaId idPF = new TPersonaFisicaId();
						idPF.setCodiKIdUffOwner(uffOwner);
						idPF.setCodiKIdPersonaFis(idSoggetto);
						idPF.setCodiKIdVer(Short.parseShort(""+intestatarioDto.getCODI_FK_ID_VER()));
						DettaglioPersonaFisicaDto personaFisicaDto = soggettoDao.getDettaglioPersonaFisicaVer(idPF);
						DatiAnagraficiPersonaFisicaType datiAnagType = null;
						if(personaFisicaDto!=null)
							soggCoinvType.setIDSOGGETTO(BigInteger.valueOf(personaFisicaDto.getCODI_K_ID_PERSONA_FIS().longValue()));
						
						datiAnagType = new DatiAnagraficiPersonaFisicaType();
						datiAnagType.setDESCRNOME(intestatarioDto.getDESC_NOME());
						datiAnagType.setDESCRCOGNOME(intestatarioDto.getDESC_COGNOME());
						
						if(intestatarioDto.getDATA_NASCITA() != null)
							datiAnagType.setDATANASCITA(dfDate.format(intestatarioDto.getDATA_NASCITA()));
						else
							datiAnagType.setDATANASCITA(null);
						
						if(intestatarioDto.getCODI_FK_ID_STATO_NASC()!= null && intestatarioDto.getCODI_FK_ID_STATO_NASC().longValue() > 0)
							datiAnagType.setIDISTATSTATONAS(intestatarioDto.getDESC_STATO());
						else
							datiAnagType.setIDISTATSTATONAS(null);
						
						if(intestatarioDto.getCODI_FK_ID_COM_NASC() != null && intestatarioDto.getCODI_FK_ID_COM_NASC().longValue() > 0)
		        			datiAnagType.setIDISTATCOMUNENAS(intestatarioDto.getDESC_COMUNE());
						else
							datiAnagType.setIDISTATCOMUNENAS(null);
						
						if(intestatarioDto.getDESC_COM_ESTERO_NASC()!=null)
							datiAnagType.setDESCRCOMUNENAS(intestatarioDto.getDESC_COM_ESTERO_NASC());
						else
							datiAnagType.setDESCRCOMUNENAS(null);
						
						if(datiAnagType!=null)
							soggCoinvType.getDatiAnagraficiPersonaFisica().add(datiAnagType);
						request.getSoggettiCoinvoltiPersonaFisica().add(soggCoinvType);
						
					}
				}
			}
			
	    	return request;
	}
	
	public DefinitivitaCorrezioneErroreDatiSoggettoResponse inviaDefinitivitaCorrezioneErroreDatiSoggetto(DefinitivitaCorrezioneErroreDatiSoggettoRequest request) {
		DefinitivitaCorrezioneErroreDatiSoggettoResponse response = poliziaServiceWS.inviaDefinitivitaCorrezioneErroreDatiSoggetto(request);
		if(response != null)
			return response;
		else
			return null;
	}
		
	private void setSoggettoFisicoMisurePersonali(int counterPersona, List<DatiSoggettoPersonaFisicaType> personaFisica, List<DatiMisuraPersonaleType> misuraPersonale, TRelProcSogint rowSogInt, long idProcedimento, long idAtto, int uffOwner ) {

		DatiSoggettoPersonaFisicaType personaF = null;
		
		DatiMisuraPersonaleType misuraP = null;
		
		TPersonaFisica persF = poliziaDao.getPersonaFisica(rowSogInt.getId().getCodiKIdSoggInt(), rowSogInt.getTRelProcSogintVer().getId().getCodiKIdVer(), uffOwner);
		
		TTpTipoPrevProc tipoPrevProc = poliziaDao.getTipoPrevenzioneProc(rowSogInt.getTTpTipoPrevProc().getCodiKIdTipoNorm());
		
		//se la persona è soggetto fisico verifico che abbia misure personali associate
		List<TMisuraPers> misureSoggetto = poliziaDao.listaMisurePersonaliByIdSoggetto(persF.getId().getCodiKIdPersonaFis(), idProcedimento, idAtto, uffOwner);
		
		//se ci sono misure legate al soggetto aggiungo il soggetto e tutte le misure collegate.
		if (misureSoggetto !=null && misureSoggetto.size() > 0 ){ 
	    	Iterator<TMisuraPers> iter = misureSoggetto.iterator();
	        int contaMisure = 0;
	        
	    	while(iter.hasNext()) {
	    		contaMisure ++;
	    		
	    		misuraP = new DatiMisuraPersonaleType();
	    		
	    		TMisuraPers rowMisura = iter.next();
	    		
	    		misuraP.setNUMEPROGRMISPERS(BigInteger.valueOf(contaMisure));
	    		misuraP.setNUMEPROGPROP(BigInteger.valueOf(counterPersona));
	    		misuraP.setIDTIPOPREV(""+tipoPrevProc.getTTpTipoPrevenzione().getCodiKIdTipoPrev());
	    		if(rowSogInt.getTTpTipoPrevProc() != null)
	    			misuraP.setIDNORMATIVARIF(BigInteger.valueOf(rowSogInt.getTTpTipoPrevProc().getCodiKIdTipoNorm()));

	    		//devo vedere quale misura personale è relativa al procedimento-atto-uffowner per aggiungere i dati corretti
				Set<TRelAttoMisPers> rowRelaz = rowMisura.getTRelAttoMisPerses();
				Iterator<TRelAttoMisPers> itRelAtto = rowRelaz.iterator();
				
				while(itRelAtto.hasNext()) {
					TRelAttoMisPers rowRelAtto = itRelAtto.next();
					if (rowRelAtto.getId().getCodiKIdProc() == idProcedimento && 
						rowRelAtto.getId().getCodiKIdAtto() == idAtto && 
						rowRelAtto.getId().getCodiKIdUffOwner() == uffOwner && 
						rowRelAtto.getId().getCodiKIdMisuraPers() == rowMisura.getId().getCodiKIdMisura()
						) {
						
						misuraP.setIDTIPOMISPERS(""+rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura());
						
						//correzione 19/10/2015
						if (rowRelAtto.getTAnNormative() != null){
							misuraP.setIDNORMMISPERS(""+rowRelAtto.getTAnNormative().getCodiKIdNormativa());
						}

						if (rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 1
								|| rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 2
								|| rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 3) {
							misuraP.setDESCRMISPERS(getMisPersAnniMesiGiorni(rowRelAtto.getNumeDurataAnni(),
									rowRelAtto.getNumeDurataMesi(), rowRelAtto.getNumeDurataGiorni(), rowRelAtto.getDescLoc()));
						} else if (rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 25){
							if (rowRelAtto.getTCauzione() != null && rowRelAtto.getTCauzione().getId() != null
									&& rowRelAtto.getTCauzione().getId().getCodiKIdCauzione() >0 ) {
							TCauzioneId idCauzione = new TCauzioneId();
							idCauzione.setCodiKIdCauzione(rowRelAtto.getTCauzione().getId().getCodiKIdCauzione());
							idCauzione.setCodiKIdUffOwner(uffOwner);
							TCauzione cauzione = poliziaDao.getCauzionebyId(idCauzione);
							// 20200507 [SG]: gestione nullpointer
							misuraP.setDESCRMISPERS(cauzione.getImpoCauzione() != null ? cauzione.getImpoCauzione().toString() : "");
							}
						}
					}
					misuraPersonale.add(misuraP);
				}
	    	}

    		personaF = new DatiSoggettoPersonaFisicaType();
    		
    		if (rowSogInt.getTTpTipoPrevProc() != null) {
    			personaF.setIDNORMATIVARIF(BigInteger.valueOf(rowSogInt.getTTpTipoPrevProc().getCodiKIdTipoNorm()));
    		}
    		
    		if (tipoPrevProc.getTTpTipoPrevenzione() != null ) {
    			personaF.setIDTIPOPREV(""+tipoPrevProc.getTTpTipoPrevenzione().getCodiKIdTipoPrev());
    		}
    		
    		personaF.setNUMEPROG(BigInteger.valueOf(counterPersona));
    		
    		if (rowSogInt.getTTpTipoRuolo() != null){
    			personaF.setIDRUOLO(decodificaRuolo(rowSogInt.getTTpTipoRuolo().getCodiKIdRuolo()));
    		}
    		
    		personaF.setIDPERSONA(BigInteger.valueOf(persF.getId().getCodiKIdPersonaFis()));
    		personaF.setDESCRCOGNOME(persF.getDescCognome());
    		personaF.setDESCRNOME(persF.getDescNome());
    		
    		if(persF.getDataNascita() != null){
    			personaF.setDATANASCITA(dfDate.format(persF.getDataNascita()));
    		}
    		
    		if(persF.getTAnStatoByCodiFkIdStatoNasc() != null && persF.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato() > 0){
    			TAnStato statoNasc = commonDao.getStatoById(persF.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato());
        		personaF.setIDISTATSTATONAS(statoNasc.getDescIstatStato());
    		}
    		
    		if(persF.getTAnComuniByCodiFkIdComNasc() != null && persF.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune() > 0){
    			TAnComuni comuneNasc = commonDao.getComuneById(persF.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune());
        		personaF.setIDISTATCOMUNENAS(comuneNasc.getDescIstatComune());
    		}
    		
    		personaF.setDESCRCOMUNENAS(persF.getDescComEsteroNasc());
    		personaF.setFLGSESSO(persF.getFlgSesso().toString());
    		personaF.setDESCRCODICEFISC(persF.getDescCodiceFiscale());
    		
    		if(rowSogInt.getTTpProfessione() != null){
    			personaF.setIDPROFESSIONE(""+rowSogInt.getTTpProfessione().getCodiKIdProfessione());
    		}
    		personaF.setDESCRCUI(persF.getDescCui());		        		
    		
    		if(rowSogInt.getTTpCondGiur() != null){
    			personaF.setIDCONDIZIONEGIUR(""+rowSogInt.getTTpCondGiur().getCodiKIdCondGiur());
    		}

    		if(rowSogInt.getTAnStatoByCodiFkIdStatoRes() != null && rowSogInt.getTAnStatoByCodiFkIdStatoRes().getCodiKIdStato() > 0){
    			TAnStato statoRes = commonDao.getStatoById(rowSogInt.getTAnStatoByCodiFkIdStatoRes().getCodiKIdStato());
        		personaF.setIDISTATSTATORES(statoRes.getDescIstatStato());
    		}
    		
    		if(rowSogInt.getTAnComuniByCodiFkIdComRes() != null && rowSogInt.getTAnComuniByCodiFkIdComRes().getCodiKIdComune() > 0){
    			TAnComuni comuneRes = commonDao.getComuneById(rowSogInt.getTAnComuniByCodiFkIdComRes().getCodiKIdComune());
        		personaF.setIDISTATCOMUNERES(comuneRes.getDescIstatComune());
    		}
    		
    		personaF.setDESCRCOMUNERES(rowSogInt.getDescComEsteroRes());
    		personaF.setDESCRINDIRIZZORES(rowSogInt.getDescIndirRes());
    		personaF.setDESCRCIVICORES(rowSogInt.getDescCivicoRes());
    		
    		if(rowSogInt.getTAnStatoByCodiFkIdStatoDim() != null && rowSogInt.getTAnStatoByCodiFkIdStatoDim().getCodiKIdStato() > 0){
    			TAnStato statoDim = commonDao.getStatoById(rowSogInt.getTAnStatoByCodiFkIdStatoDim().getCodiKIdStato());
        		personaF.setIDISTATSTATODIM(statoDim.getDescIstatStato());
    		}
    		
    		if(rowSogInt.getTAnComuniByCodiFkIdComDim() != null && rowSogInt.getTAnComuniByCodiFkIdComDim().getCodiKIdComune() > 0){
    			TAnComuni comuneDim = commonDao.getComuneById(rowSogInt.getTAnComuniByCodiFkIdComDim().getCodiKIdComune());
        		personaF.setIDISTATCOMUNEDIM(comuneDim.getDescIstatComune());
    		}
    		
    		personaF.setDESCRCOMUNEDIM(rowSogInt.getDescComEsteroDim());
    		personaF.setDESCRINDIRIZZODIM(rowSogInt.getDescIndirDim());
    		personaF.setDESCRCIVICODIM(rowSogInt.getDescCivicoDim());

    		if(rowSogInt.getTAnStatoByCodiFkIdStatoDomel() != null && rowSogInt.getTAnStatoByCodiFkIdStatoDomel().getCodiKIdStato() > 0){
    			TAnStato statoDomel = commonDao.getStatoById(rowSogInt.getTAnStatoByCodiFkIdStatoDomel().getCodiKIdStato());
        		personaF.setIDISTATSTATOELE(statoDomel.getDescIstatStato());
    		}
    		
    		if(rowSogInt.getTAnComuniByCodiFkIdComDomel() != null && rowSogInt.getTAnComuniByCodiFkIdComDomel().getCodiKIdComune() > 0){
    			TAnComuni comuneDomel = commonDao.getComuneById(rowSogInt.getTAnComuniByCodiFkIdComDomel().getCodiKIdComune());
        		personaF.setIDISTATCOMUNEELE(comuneDomel.getDescIstatComune());
    		}
    		
    		personaF.setDESCRCOMUNEELE(rowSogInt.getDescComEsteroDomel());
    		personaF.setDESCRINDIRIZZOELE(rowSogInt.getDescIndirDomel());
    		personaF.setDESCRCIVICOELE(rowSogInt.getDescCivicoDomel());
    		
    		if(persF.getTAnStatoByCodiFkIdStatoMorte() != null && persF.getTAnStatoByCodiFkIdStatoMorte().getCodiKIdStato() > 0){
    			TAnStato statoDomel = commonDao.getStatoById(persF.getTAnStatoByCodiFkIdStatoMorte().getCodiKIdStato());
        		personaF.setIDISTATSTATOMORTE(statoDomel.getDescIstatStato());
    		}
    		
    		if(persF.getTAnComuniByCodiFkIdComMorte() != null && persF.getTAnComuniByCodiFkIdComMorte().getCodiKIdComune() > 0){
    			TAnComuni comuneDomel = commonDao.getComuneById(persF.getTAnComuniByCodiFkIdComMorte().getCodiKIdComune());
        		personaF.setIDISTATCOMUNEMORTE(comuneDomel.getDescIstatComune());
    		}
    		
    		personaF.setDESCRCOMUNEMORTE(persF.getDescComEsteroMorte());
    		
    		if(persF.getDataDecesso() != null) {
        		personaF.setDATAMORTE(dfDate.format(persF.getDataDecesso()));    			
    		}
    		
    		personaF.setDESCRCOGNOMEPADR(persF.getDescCogPadre());
    		personaF.setDESCRNOMEPADR(persF.getDescNomePadre());
    		personaF.setDESCRCOGNOMEMADR(persF.getDescCogMadre());
    		personaF.setDESCRNOMEMADR(persF.getDescNomeMadre());
    		
    		//aggiungo persona fisica all'array di appoggio

    		personaFisica.add(personaF);
		}
	}
	
	private String formattaNumeroAnno(String num, String anno){
		StringBuilder numAnno = new StringBuilder();
		String slash = "/";
		
		if (num != null){
			numAnno.append(num);
			numAnno.append(slash);
		}
		
		if(anno != null){
			numAnno.append(anno);	
		}
		
		return numAnno.toString();
		
	}
	
	private void setDatiPersonaFisicaType(DatiPersonaFisicaType personaF, TRelProcSogint rowSogInt, int uffOwner ) {
		
		TPersonaFisica persF = poliziaDao.getPersonaFisica(rowSogInt.getId().getCodiKIdSoggInt(), rowSogInt.getTRelProcSogintVer().getId().getCodiKIdVer(), uffOwner);
		
		personaF.setIDPERSONAF(BigInteger.valueOf(persF.getId().getCodiKIdPersonaFis()));
		personaF.setDESCRCOGNOME(persF.getDescCognome());
		personaF.setDESCRNOME(persF.getDescNome());
	
		if(persF.getDataNascita() != null){
		personaF.setDATANASCITA(dfDate.format(persF.getDataNascita()));
		}
		
		if(persF.getTAnStatoByCodiFkIdStatoNasc() != null && persF.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato() > 0){
			TAnStato statoNasc = commonDao.getStatoById(persF.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato());
    		personaF.setIDISTATSTATONAS(statoNasc.getDescIstatStato());
		}
		
		if(persF.getTAnComuniByCodiFkIdComNasc() != null && persF.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune() > 0){
			TAnComuni comuneNasc = commonDao.getComuneById(persF.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune());
    		personaF.setIDISTATCOMUNENAS(comuneNasc.getDescIstatComune());
		}
		
		personaF.setDESCRCOMUNENAS(persF.getDescComEsteroNasc());
		
		if(persF.getFlgSesso() != null){
			personaF.setFLGSESSO(persF.getFlgSesso().toString());
		}
		
		if(rowSogInt.getTAnStatoByCodiFkIdStatoRes() != null && rowSogInt.getTAnStatoByCodiFkIdStatoRes().getCodiKIdStato() > 0){
			TAnStato statoRes = commonDao.getStatoById(rowSogInt.getTAnStatoByCodiFkIdStatoRes().getCodiKIdStato());
    		personaF.setIDISTATSTATORES(statoRes.getDescIstatStato());
		}
		
		if(rowSogInt.getTAnComuniByCodiFkIdComRes() != null && rowSogInt.getTAnComuniByCodiFkIdComRes().getCodiKIdComune() > 0){
			TAnComuni comuneRes = commonDao.getComuneById(rowSogInt.getTAnComuniByCodiFkIdComRes().getCodiKIdComune());
    		personaF.setIDISTATCOMUNERES(comuneRes.getDescIstatComune());
		}
		
		personaF.setDESCRCOMUNERES(rowSogInt.getDescComEsteroRes());
		personaF.setDESCRINDIRIZZORES(rowSogInt.getDescIndirRes());
		personaF.setDESCRCIVICORES(rowSogInt.getDescCivicoRes());
		
	}
	
	private void setDatiPersonaGiuridicaType(DatiPersonaGiuridicaType personaG, TRelProcSogint rowSogInt, int uffOwner ) {
		
		TPersonaGiur persG = poliziaDao.getPersonaGiuridica(rowSogInt.getId().getCodiKIdSoggInt(), rowSogInt.getTRelProcSogintVer().getId().getCodiKIdVer(), uffOwner);
		
		personaG.setIDPERSONAG(BigInteger.valueOf(persG.getId().getCodiKIdPersGiur()));
		personaG.setIDTIPOSOCIETA(BigInteger.valueOf(persG.getTTpTipoSocieta().getCodiKIdTipoSoc()));
		personaG.setDESCRSOCIALE(persG.getDescRagioneSoc());
		
		if(persG.getTAnStato() != null && persG.getTAnStato().getCodiKIdStato() > 0){
			TAnStato statoAtt = commonDao.getStatoById(persG.getTAnStato().getCodiKIdStato());
    		personaG.setIDISTATSTATO(statoAtt.getDescIstatStato());
		}
		
		if(persG.getTAnComuni() != null && persG.getTAnComuni().getCodiKIdComune() > 0){
			TAnComuni comuneAtt = commonDao.getComuneById(persG.getTAnComuni().getCodiKIdComune());
    		personaG.setIDISTATCOMUNE(comuneAtt.getDescIstatComune());
		}
		
		personaG.setDESCRCOMUNE(persG.getDescComEsteroSede());
		personaG.setDESCRNOTE(persG.getDescNote());
		
	}

	private String getNumeProc(long idProcedimento, Integer uffOwner){
		
		TProcedimento procRecord = poliziaDao.getProcedimento(idProcedimento, uffOwner);
		String num = null;
		String anno = null;
		
		if(procRecord.getNumeAnnoProc() != null){
			num = procRecord.getNumeAnnoProc().toString();
		}
		
		if(procRecord.getNumeProc() != null){
			anno = procRecord.getNumeProc().toString(); 
		}
		
		String descrNumProc = formattaNumeroAnno(num, anno);
		
		return descrNumProc;
	}
	
	private String decodificaRuolo(int idRuolo){

		StringBuilder descrRuolo = null; 
		
		if(idRuolo == 1){
			descrRuolo = new StringBuilder("D");
			
		}else if(idRuolo == 2){
			descrRuolo = new StringBuilder("T");
		
		}else{
			descrRuolo = new StringBuilder("");
		}
		
		return descrRuolo.toString(); 
	}
	
	private String getMisPersAnniMesiGiorni(Byte numAnni, Byte numMesi, Byte numGiorni, String DescrPresso){
	
		StringBuilder descrMisPers = new StringBuilder();
		
		if (numAnni != null){
			descrMisPers.append(" Anni ");
			descrMisPers.append(numAnni);
		}
		
		if (numMesi != null){
			descrMisPers.append(" Mesi ");
			descrMisPers.append(numMesi);
		}
		
		if (numGiorni != null){
			descrMisPers.append(" Giorni ");
			descrMisPers.append(numGiorni);
		}
		
		if (DescrPresso != null && !DescrPresso.isEmpty()){
			descrMisPers.append(" Presso ");
			descrMisPers.append(DescrPresso);
		}
		
		return descrMisPers.toString();
	}
	
	private void setDatiAnagraficiPersonaFisicaType(DatiAnagraficiPersonaFisicaType personaF, TRelProcSogint rowSogInt, int uffOwner ) {
		
		TPersonaFisica persF = poliziaDao.getPersonaFisica(rowSogInt.getId().getCodiKIdSoggInt(), rowSogInt.getTRelProcSogintVer().getId().getCodiKIdVer(), uffOwner);
		
		personaF.setDESCRCOGNOME(persF.getDescCognome());
		personaF.setDESCRNOME(persF.getDescNome());
		
		if(persF.getDataNascita() != null){
		personaF.setDATANASCITA(dfDate.format(persF.getDataNascita()));
		}
		
		if(persF.getTAnStatoByCodiFkIdStatoNasc() != null && persF.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato() > 0){
			TAnStato statoNasc = commonDao.getStatoById(persF.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato());
    		personaF.setIDISTATSTATONAS(statoNasc.getDescIstatStato());
		}
		else
			personaF.setIDISTATSTATONAS("");
		
		if(persF.getTAnComuniByCodiFkIdComNasc() != null && persF.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune() > 0){
			TAnComuni comuneNasc = commonDao.getComuneById(persF.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune());
    		personaF.setIDISTATCOMUNENAS(comuneNasc.getDescIstatComune());
		}
		else
			personaF.setIDISTATCOMUNENAS("");
		
		personaF.setDESCRCOMUNENAS(persF.getDescComEsteroNasc());		
	}

	private void setDatiAnagraficiPersonaGiuridicaType(DatiAnagraficiPersonaGiuridicaType personaG, TRelProcSogint rowSogInt, int uffOwner ) {
		
		TPersonaGiur persG = poliziaDao.getPersonaGiuridica(rowSogInt.getId().getCodiKIdSoggInt(), rowSogInt.getTRelProcSogintVer().getId().getCodiKIdVer(), uffOwner);
		
		personaG.setIDTIPOSOCIETA(BigInteger.valueOf(persG.getTTpTipoSocieta().getCodiKIdTipoSoc()));
		personaG.setDESCRSOCIALE(persG.getDescRagioneSoc());
		personaG.setDESCRPARTITAIVA(persG.getDescPartitaIva());
		
		if(persG.getTAnStato() != null && persG.getTAnStato().getCodiKIdStato() > 0){
			TAnStato statoAtt = commonDao.getStatoById(persG.getTAnStato().getCodiKIdStato());
    		personaG.setIDISTATSTATO(statoAtt.getDescIstatStato());
		}
		
		if(persG.getTAnComuni() != null && persG.getTAnComuni().getCodiKIdComune() > 0){
			TAnComuni comuneAtt = commonDao.getComuneById(persG.getTAnComuni().getCodiKIdComune());
    		personaG.setIDISTATCOMUNE(comuneAtt.getDescIstatComune());
		}
		
		personaG.setDESCRCOMUNE(persG.getDescComEsteroSede());
		personaG.setDESCRNOTE(persG.getDescNote());
	}
	
	private void setDatiMisuraPatrimonialePersonaType(List<DatiMisuraPatrimonialePrimoGradoType> misuraPatrimoniale, int counterPersona, long idPersonaG, long idProcedimento, long idAtto, TRelProcSogint rowSogInt, int uffOwner ) {
		
	   DatiMisuraPatrimonialePrimoGradoType misuraPat = null;
		//se la persona è soggetto giuridico verifico che abbia misure patrimoniali associate
//		List<TRelAttoMisPatr> beniSoggetto = beneDao.listaRelAttoMisPatr(idPersonaG, idProcedimento, idAtto, uffOwner);
		List<TRelAttoMisPatr> beniSoggetto = poliziaDao.listaRelAttoMisPatrSogInt(idPersonaG, idProcedimento, idAtto, uffOwner);
		//se ci sono misure legate al soggetto aggiungo il soggetto e tutte le misure collegate.
		if (beniSoggetto !=null && beniSoggetto.size() > 0 ){ 
	    	Iterator<TRelAttoMisPatr> iter = beniSoggetto.iterator();
	        int contaMisure = 0;
	        long idBeneTmp = 0L;
	    	while(iter.hasNext()) {
	    		misuraPat = new DatiMisuraPatrimonialePrimoGradoType();
	    		
	    		TRelAttoMisPatr rowMisura = iter.next();
	    		
	    		misuraPat.setNUMEPROGPROP(BigInteger.valueOf(counterPersona));
	
	    		TRelBeneAtto rowRelBeneAtto = beneDao.getTipoInteressePerSoggettoInteressato(idProcedimento, idAtto, uffOwner, rowMisura.getId().getCodiKIdBene(), rowMisura.getId().getCodiKIdSoggTit());
	    		
	    		if(rowRelBeneAtto.getId() != null && (rowRelBeneAtto.getId().getCodiKIdBene() != idBeneTmp))
	    		{
	    			idBeneTmp = rowRelBeneAtto.getId().getCodiKIdBene();
	    			contaMisure ++;
	    		}
	    		misuraPat.setNUMEPROGBENE(BigInteger.valueOf(contaMisure));
	    		
	    		if(rowRelBeneAtto.getId() != null){
	    			misuraPat.setNUMEPROGTITOL(BigInteger.valueOf(rowRelBeneAtto.getId().getCodiKIdSoggTit()));
	    		}
	    		
	    		if(rowRelBeneAtto.getTTpTipoInteresse() != null){
	    			misuraPat.setIDTIPOINTERESSE(""+rowRelBeneAtto.getTTpTipoInteresse().getCodiKIdTipoInt());
	    		}
	    		
	    		misuraPat.setNUMEQUOTA(""+rowRelBeneAtto.getNumeQuota());
	    		
	    		if(rowRelBeneAtto.getTAnNormative() != null){
	    			misuraPat.setIDNORMMISPATR(""+rowRelBeneAtto.getTAnNormative().getCodiKIdNormativa());
	    		}
	    		
	    		if(rowRelBeneAtto.getTTpEsitoByCodiFkIdEsito2() != null)
	    			misuraPat.setIDESITO(""+rowRelBeneAtto.getTTpEsitoByCodiFkIdEsito2().getCodiKIdEsito());
	    		
	    		misuraPatrimoniale.add(misuraPat);
			}
		}
	}

	private void setDatiMisuraPersonalePersonaType(List<DatiMisuraPersonalePrimoGradoType> misuraPersonale, int counterPersona, long idPersonaF, long idProcedimento, long idAtto, TRelProcSogint rowSogInt, int uffOwner ) {
			
	   DatiMisuraPersonalePrimoGradoType misuraPersonaleType = null;

   		List<TMisuraPers> misureSoggetto = poliziaDao.listaMisurePersonaliByIdSoggetto(idPersonaF, idProcedimento, idAtto, uffOwner);
		
   		//se ci sono misure legate al soggetto aggiungo il soggetto e tutte le misure collegate.
   		if (misureSoggetto !=null && misureSoggetto.size() > 0 ){ 
   	    	Iterator<TMisuraPers> iter = misureSoggetto.iterator();
   	        int contaMisure = 0;
   	        
   	    	while(iter.hasNext()) {
   	    		contaMisure ++;
   	    		
   	    		misuraPersonaleType = new DatiMisuraPersonalePrimoGradoType();
   	    		
   	    		TMisuraPers rowMisura = iter.next();
   	    		
   	    		misuraPersonaleType.setNUMEPROGRMISPERS(BigInteger.valueOf(contaMisure));
   	    		misuraPersonaleType.setNUMEPROGPROP(BigInteger.valueOf(counterPersona));
   	    		
   	    		//devo vedere quale misura personale è relativa al procedimento-atto-uffowner per aggiungere i dati corretti
   				Set<TRelAttoMisPers> rowRelaz = rowMisura.getTRelAttoMisPerses();
   				Iterator<TRelAttoMisPers> itRelAtto = rowRelaz.iterator();
   				
   				while(itRelAtto.hasNext()) {
   					TRelAttoMisPers rowRelAtto = itRelAtto.next();
						
       	    		if (rowRelAtto.getTAnNormative() != null){
       	    			misuraPersonaleType.setIDNORMMISPERS(""+rowRelAtto.getTAnNormative().getCodiKIdNormativa());
       	    		}
       	    		TTpTipoUfficio tipoUfficio = commonDao.getTipoUfficioByIdUfficio(uffOwner);
    	    		if(tipoUfficio.getCodiKIdTipoUff()==19 && rowRelAtto.getTTpEsitoByCodiFkIdEsito1() != null)
    	    		{
						misuraPersonaleType.setIDESITO(""+rowRelAtto.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito());
					}
    	    		if(tipoUfficio.getCodiKIdTipoUff() == 6)
    	    		{
    	    			 if(rowRelAtto.getTTpEsitoByCodiFkIdEsito3() != null)
    	    				 misuraPersonaleType.setIDESITO(""+rowRelAtto.getTTpEsitoByCodiFkIdEsito3().getCodiKIdEsito());
    	    			 else
    	    			 {
    	    				 if(rowRelAtto.getTTpEsitoByCodiFkIdEsito2() != null)
        	    				 misuraPersonaleType.setIDESITO(""+rowRelAtto.getTTpEsitoByCodiFkIdEsito2().getCodiKIdEsito());
    	    			 }
					}
//   					if(rowRelAtto.getTTpEsitoByCodiFkIdEsito1() != null){
//   						misuraPersonaleType.setIDESITO(""+rowRelAtto.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito());
//   					}
       	    		
						
   					if (rowRelAtto.getId().getCodiKIdProc() == idProcedimento && 
   						rowRelAtto.getId().getCodiKIdAtto() == idAtto && 
   						rowRelAtto.getId().getCodiKIdUffOwner() == uffOwner && 
   						rowRelAtto.getId().getCodiKIdMisuraPers() == rowMisura.getId().getCodiKIdMisura()
   						) {
   						
   						misuraPersonaleType.setIDTIPOMISPERS(""+rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura());
   					
   						if(rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 1 || rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 2 || rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 3){
							misuraPersonaleType.setDESCRMISPERS(getMisPersAnniMesiGiorni(rowRelAtto.getNumeDurataAnni(), rowRelAtto.getNumeDurataMesi(), rowRelAtto.getNumeDurataGiorni(), rowRelAtto.getDescLoc()));
							
						}else if (rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 25){
							
							if(rowRelAtto.getTCauzione() != null && rowRelAtto.getTCauzione().getId() != null && rowRelAtto.getTCauzione().getId().getCodiKIdCauzione() >0 ){
							TCauzioneId idCauzione = new TCauzioneId();
							idCauzione.setCodiKIdCauzione(rowRelAtto.getTCauzione().getId().getCodiKIdCauzione());
							idCauzione.setCodiKIdUffOwner(uffOwner);
							TCauzione cauzione = poliziaDao.getCauzionebyId(idCauzione);
							if(cauzione!=null && cauzione.getImpoCauzione()!=null)
								misuraPersonaleType.setDESCRMISPERS(cauzione.getImpoCauzione().toString());
							}
						}
   						
   					misuraPersonale.add(misuraPersonaleType);
   				}
   				}
   	    	}
   		}
	}
	
	//vale per il servizio 8.20
	private void setSoggettoMisuraPersonale( List<DatiSoggettoPersonaFisicaType> personaFisica, DatiRichiestaModificaRevocaMisuraType richiestaType, TRelProcSogint rowSogInt, long idProcedimento, long idAtto, int uffOwner, long idMisura ) {

		DatiSoggettoPersonaFisicaType personaF = null;
		
		TPersonaFisica persF = poliziaDao.getPersonaFisica(rowSogInt.getId().getCodiKIdSoggInt(), rowSogInt.getTRelProcSogintVer().getId().getCodiKIdVer(), uffOwner);
		
		TTpTipoPrevProc tipoPrevProc = poliziaDao.getTipoPrevenzioneProc(rowSogInt.getTTpTipoPrevProc().getCodiKIdTipoNorm());
		
		//se la persona è soggetto fisico verifico che abbia misure personali associate
		List<TMisuraPers> misureSoggetto = poliziaDao.listaMisurePersonaliByIdSoggetto(persF.getId().getCodiKIdPersonaFis(), idProcedimento, idAtto, uffOwner);
		
		//se ci sono misure legate al soggetto aggiungo il soggetto e tutte le misure collegate.
		if (misureSoggetto !=null && misureSoggetto.size() > 0 ){ 
	    	Iterator<TMisuraPers> iter = misureSoggetto.iterator();
	        
	    	while(iter.hasNext()) {
	    		
	    		TMisuraPers rowMisura = iter.next();
	    		
	    		//se la misura è quella passata in input 
	    		if(rowMisura.getId().getCodiKIdMisura() == idMisura){
	    			
	    		    richiestaType.setNUMEPROGRMISPERS(BigInteger.valueOf(1));
	    		    richiestaType.setNUMEPROGPROP(BigInteger.valueOf(1));

	    		//devo vedere quale misura personale è relativa al procedimento-atto-uffowner per aggiungere i dati corretti
				Set<TRelAttoMisPers> rowRelaz = rowMisura.getTRelAttoMisPerses();
				Iterator<TRelAttoMisPers> itRelAtto = rowRelaz.iterator();
				
				while(itRelAtto.hasNext()) {
					TRelAttoMisPers rowRelAtto = itRelAtto.next();
					if (rowRelAtto.getId().getCodiKIdProc() == idProcedimento && 
						rowRelAtto.getId().getCodiKIdAtto() == idAtto && 
						rowRelAtto.getId().getCodiKIdUffOwner() == uffOwner && 
						rowRelAtto.getId().getCodiKIdMisuraPers() == rowMisura.getId().getCodiKIdMisura()
						) {
						
						richiestaType.setIDTIPOMISPERS(""+rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura());
						
						if(rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 1 || rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 2){
							richiestaType.setDESCRMISPERS(getMisPersAnniMesiGiorni(rowRelAtto.getNumeDurataAnni(), rowRelAtto.getNumeDurataMesi(), rowRelAtto.getNumeDurataGiorni(), rowRelAtto.getDescLoc()));
							
						}else if (rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 25 || rowRelAtto.getTTpTipoMisura().getCodiKIdTipoMisura() == 26){
							if(rowRelAtto.getTCauzione().getId() != null && rowRelAtto.getTCauzione().getId().getCodiKIdCauzione() >0 ){
							TCauzioneId idCauzione = new TCauzioneId();
							idCauzione.setCodiKIdCauzione(rowRelAtto.getTCauzione().getId().getCodiKIdCauzione());
							idCauzione.setCodiKIdUffOwner(uffOwner);
							TCauzione cauzione = poliziaDao.getCauzionebyId(idCauzione);
							richiestaType.setDESCRMISPERS(cauzione.getImpoCauzione().toString());
							}
						}
					}
				}
	    		}
	    	}
	    	
    		personaF = new DatiSoggettoPersonaFisicaType();
    		
    		if (rowSogInt.getTTpTipoPrevProc() != null) {
    			personaF.setIDNORMATIVARIF(BigInteger.valueOf(rowSogInt.getTTpTipoPrevProc().getCodiKIdTipoNorm()));
    		}
    		
    		if (tipoPrevProc.getTTpTipoPrevenzione() != null ) {
    			personaF.setIDTIPOPREV(""+tipoPrevProc.getTTpTipoPrevenzione().getCodiKIdTipoPrev());
    		}
    		
    		personaF.setNUMEPROG(BigInteger.valueOf(1));
    		
    		if (rowSogInt.getTTpTipoRuolo() != null){
    			personaF.setIDRUOLO(decodificaRuolo(rowSogInt.getTTpTipoRuolo().getCodiKIdRuolo()));
    		}
    		
    		personaF.setIDPERSONA(BigInteger.valueOf(persF.getId().getCodiKIdPersonaFis()));
    		personaF.setDESCRCOGNOME(persF.getDescCognome());
    		personaF.setDESCRNOME(persF.getDescNome());
    		
    		if(persF.getDataNascita() != null){
    			personaF.setDATANASCITA(dfDate.format(persF.getDataNascita()));
    		}
    		
    		if(persF.getTAnStatoByCodiFkIdStatoNasc() != null && persF.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato() > 0){
    			TAnStato statoNasc = commonDao.getStatoById(persF.getTAnStatoByCodiFkIdStatoNasc().getCodiKIdStato());
        		personaF.setIDISTATSTATONAS(statoNasc.getDescIstatStato());
    		}
    		
    		if(persF.getTAnComuniByCodiFkIdComNasc() != null && persF.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune() > 0){
    			TAnComuni comuneNasc = commonDao.getComuneById(persF.getTAnComuniByCodiFkIdComNasc().getCodiKIdComune());
        		personaF.setIDISTATCOMUNENAS(comuneNasc.getDescIstatComune());
    		}
    		
    		personaF.setDESCRCOMUNENAS(persF.getDescComEsteroNasc());
    		personaF.setFLGSESSO(persF.getFlgSesso().toString());
    		personaF.setDESCRCODICEFISC(persF.getDescCodiceFiscale());
    		
    		if(rowSogInt.getTTpProfessione() != null){
    			personaF.setIDPROFESSIONE(""+rowSogInt.getTTpProfessione().getCodiKIdProfessione());
    		}
    		personaF.setDESCRCUI(persF.getDescCui());		        		
    		
    		if(rowSogInt.getTTpCondGiur() != null){
    			personaF.setIDCONDIZIONEGIUR(""+rowSogInt.getTTpCondGiur().getCodiKIdCondGiur());
    		}

    		if(rowSogInt.getTAnStatoByCodiFkIdStatoRes() != null && rowSogInt.getTAnStatoByCodiFkIdStatoRes().getCodiKIdStato() > 0){
    			TAnStato statoRes = commonDao.getStatoById(rowSogInt.getTAnStatoByCodiFkIdStatoRes().getCodiKIdStato());
        		personaF.setIDISTATSTATORES(statoRes.getDescIstatStato());
    		}
    		
    		if(rowSogInt.getTAnComuniByCodiFkIdComRes() != null && rowSogInt.getTAnComuniByCodiFkIdComRes().getCodiKIdComune() > 0){
    			TAnComuni comuneRes = commonDao.getComuneById(rowSogInt.getTAnComuniByCodiFkIdComRes().getCodiKIdComune());
        		personaF.setIDISTATCOMUNERES(comuneRes.getDescIstatComune());
    		}
    		
    		personaF.setDESCRCOMUNERES(rowSogInt.getDescComEsteroRes());
    		personaF.setDESCRINDIRIZZORES(rowSogInt.getDescIndirRes());
    		personaF.setDESCRCIVICORES(rowSogInt.getDescCivicoRes());
    		
    		if(rowSogInt.getTAnStatoByCodiFkIdStatoDim() != null && rowSogInt.getTAnStatoByCodiFkIdStatoDim().getCodiKIdStato() > 0){
    			TAnStato statoDim = commonDao.getStatoById(rowSogInt.getTAnStatoByCodiFkIdStatoDim().getCodiKIdStato());
        		personaF.setIDISTATSTATODIM(statoDim.getDescIstatStato());
    		}
    		
    		if(rowSogInt.getTAnComuniByCodiFkIdComDim() != null && rowSogInt.getTAnComuniByCodiFkIdComDim().getCodiKIdComune() > 0){
    			TAnComuni comuneDim = commonDao.getComuneById(rowSogInt.getTAnComuniByCodiFkIdComDim().getCodiKIdComune());
        		personaF.setIDISTATCOMUNEDIM(comuneDim.getDescIstatComune());
    		}
    		
    		personaF.setDESCRCOMUNEDIM(rowSogInt.getDescComEsteroDim());
    		personaF.setDESCRINDIRIZZODIM(rowSogInt.getDescIndirDim());
    		personaF.setDESCRCIVICODIM(rowSogInt.getDescCivicoDim());

    		if(rowSogInt.getTAnStatoByCodiFkIdStatoDomel() != null && rowSogInt.getTAnStatoByCodiFkIdStatoDomel().getCodiKIdStato() > 0){
    			TAnStato statoDomel = commonDao.getStatoById(rowSogInt.getTAnStatoByCodiFkIdStatoDomel().getCodiKIdStato());
        		personaF.setIDISTATSTATOELE(statoDomel.getDescIstatStato());
    		}
    		
    		if(rowSogInt.getTAnComuniByCodiFkIdComDomel() != null && rowSogInt.getTAnComuniByCodiFkIdComDomel().getCodiKIdComune() > 0){
    			TAnComuni comuneDomel = commonDao.getComuneById(rowSogInt.getTAnComuniByCodiFkIdComDomel().getCodiKIdComune());
        		personaF.setIDISTATCOMUNEELE(comuneDomel.getDescIstatComune());
    		}
    		
    		personaF.setDESCRCOMUNEELE(rowSogInt.getDescComEsteroDomel());
    		personaF.setDESCRINDIRIZZOELE(rowSogInt.getDescIndirDomel());
    		personaF.setDESCRCIVICOELE(rowSogInt.getDescCivicoDomel());
    		
    		if(persF.getTAnStatoByCodiFkIdStatoMorte() != null && persF.getTAnStatoByCodiFkIdStatoMorte().getCodiKIdStato() > 0){
    			TAnStato statoDomel = commonDao.getStatoById(persF.getTAnStatoByCodiFkIdStatoMorte().getCodiKIdStato());
        		personaF.setIDISTATSTATOMORTE(statoDomel.getDescIstatStato());
    		}
    		
    		if(persF.getTAnComuniByCodiFkIdComMorte() != null && persF.getTAnComuniByCodiFkIdComMorte().getCodiKIdComune() > 0){
    			TAnComuni comuneDomel = commonDao.getComuneById(persF.getTAnComuniByCodiFkIdComMorte().getCodiKIdComune());
        		personaF.setIDISTATCOMUNEMORTE(comuneDomel.getDescIstatComune());
    		}
    		
    		personaF.setDESCRCOMUNEMORTE(persF.getDescComEsteroMorte());
    		
    		if(persF.getDataDecesso() != null) {
        		personaF.setDATAMORTE(dfDate.format(persF.getDataDecesso()));    			
    		}
    		
    		personaF.setDESCRCOGNOMEPADR(persF.getDescCogPadre());
    		personaF.setDESCRNOMEPADR(persF.getDescNomePadre());
    		personaF.setDESCRCOGNOMEMADR(persF.getDescCogMadre());
    		personaF.setDESCRNOMEMADR(persF.getDescNomeMadre());
    		
    		//aggiungo persona fisica all'array di appoggio

    		personaFisica.add(personaF);
		}
	}

	public Long getIdUfficioDest(long idUffOwner) {
		return poliziaDao.getIdUfficioDest(idUffOwner);
	}

	public Long getIdUfficioDestByIdProcedente(int idProcedente) {
		return poliziaDao.getIdUfficioDestByIdProcedente(idProcedente);
	}
	
	private List<TRelProcSogint> getTRelProcSogintList(long idProcedimento, int uffOwner){
		List queryResults = poliziaDao.getTRelProcSogintList(idProcedimento, uffOwner);
		List<TRelProcSogint> list = null;
		if ((queryResults == null) || queryResults.isEmpty()) {
			return null;
		}
		else
			list = new ArrayList<TRelProcSogint>();
		Iterator iterator = queryResults.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			TRelProcSogint msg = (TRelProcSogint) row.get("psi");
			list.add(msg);
		}
		return list;	
	}

	public String registraFlussiIngressoPolizia(long idServizio, long idUfficio, long idProcedimento, long idAutorita, java.sql.Date dataEmissione, String descrNote, String descrProcedura){
		return poliziaDao.registraFlussiIngressoPolizia(idServizio, idUfficio, idProcedimento, idAutorita, dataEmissione, descrNote, descrProcedura);
	}

    public String registraFlussiIngressoPoliziaAgg(long idServizio, long idUfficio, long idAutorita, String descrNumAccert, java.sql.Date dataEmissione, String descrNote, String descrProcedura){
    	return poliziaDao.registraFlussiIngressoPoliziaAgg(idServizio, idUfficio, idAutorita, descrNumAccert, dataEmissione, descrNote, descrProcedura);
    }

    public PoliziaMsgErrorRequest createPoliziaMsgErrorRequest(long idMsg){
    	PoliziaMsgErrorRequest request = null;
    	TPoliziaMsg msgDatabase = poliziaDao.getTPoliziaMsgById(idMsg);
    	if(msgDatabase != null)
    	{
	    	request = new PoliziaMsgErrorRequest();
	    	DatiMsgErroreType msgErrore = new DatiMsgErroreType();
	    	msgErrore.setIdMessaggio(BigInteger.valueOf(idMsg));
	    	msgErrore.setDescrizioneErrore(msgDatabase.getDescErrore());
	    	request.setDettaglioMessaggio(msgErrore);
    	}
    	return request;
    }
    
	public PoliziaMsgErrorResponse inviaMessaggiDiErrore(PoliziaMsgErrorRequest request) {
		PoliziaMsgErrorResponse response = poliziaServiceWS.inviaMessaggiDiErrore(request);
		if(response != null)
			return response;
		else
			return null;
	}
	
    
    public TTpPoliziaServizi getTtpPoliziaServiziById(final long codiKIdServizi){
    	return poliziaDao.getTtpPoliziaServiziById(codiKIdServizi);
    }
    
	public List<Integer> getUffDestinatario(int procedente, int uffOwner, long idProcedimento, String tipoProcedimento) {
		List<BigDecimal> result = poliziaDao.getUffDestinatario(procedente, uffOwner, idProcedimento, tipoProcedimento);
		List<Integer> listDef = new ArrayList<Integer>();
		if(result!=null){
			for(Iterator<BigDecimal> itr = result.iterator(); itr.hasNext();){
				listDef.add(new Integer(itr.next().intValue()));
			}
		}
		return listDef;
	}
	
	public int getIdProcedente(int idUfficio){
		return poliziaDao.getIdProcedente(idUfficio);
	}
	
	public void inserisciDocumentoAllegatoAtto(AttoForm attoForm, long idProc, int idUfficio){
		TAtto tatto = popolaAttoEDocumento(attoForm);
		tatto.setCodiIdProcEmiss(idProc);
		tatto.getId().setCodiKIdUffOwner(idUfficio);
		poliziaDao.inserisciDocumentoAllegatoAtto(tatto);
	}
	
	private TAtto popolaAttoEDocumento(AttoForm attoForm){
		TAtto atto = new TAtto();
		mapperBean.map(attoForm, atto);
		Utility.popolaDocumento(attoForm, atto);
		return atto;
	}
	
	public TTpCatAtto getTtpCatAttoById(final long codiKIdCatAtto){
    	return poliziaDao.getTtpCatAttoById(codiKIdCatAtto);
    }
	/*public static void main (String args[]){
		
		long idProcedimento = new Long ("1420000000802").longValue();
		long idSoggetto = new Long(0).longValue();
		int idVerOld = Integer.valueOf("1").intValue();
		int idVerNew = Integer.valueOf("1").intValue();;
		int uffOwner = Integer.valueOf("1420").intValue();;
		int uffDest = Integer.valueOf("2060").intValue();;
		long idAtto = new Long("1420000003806").longValue();
		
		DefinitivitaCorrezioneErroreDatiSoggettoRequest request = new PoliziaServiceImpl().createDefinitivitaCorrezioneErroreDatiSoggettoRequest(idProcedimento, idSoggetto, idVerOld, idVerNew, uffOwner, uffDest, idAtto);
	}*/
}
