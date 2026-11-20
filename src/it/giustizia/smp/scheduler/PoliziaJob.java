package it.giustizia.smp.scheduler;

import it.giustizia.smp.model.TPoliziaMsg;
import it.giustizia.smp.multidb.UserRoutingDataSource;
import it.giustizia.smp.service.PoliziaService;
import it.giustizia.smp.utility.Constants;
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
import it.giustizia.smp.webservice.client.polizia.VariazioneAnagraficheSoggettiInteressatiRequest;
import it.giustizia.smp.webservice.client.polizia.VariazioneAnagraficheSoggettiInteressatiResponse;
import it.giustizia.smp.webservice.utility.JAXBUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import it.giustizia.smp.webservice.client.documentale.stub.Configuration;


public class PoliziaJob{
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private PoliziaService poliziaService;
	@Autowired
	private UserRoutingDataSource	myDataSource;
	
	    
    public void scheduledMethod()
    {
	 	System.out.println("Inizio Polizia Job...");
	 	invioSMP();
	 	invioSMP_GIU();
	 	System.out.println("Fine Polizia Job");
    }
	 
	private void invioSMP(){
		myDataSource.selectConnection("SMP");
		invioDati();
	}
	
	private void invioSMP_GIU(){
		myDataSource.selectConnection("SMP_GIU");
		poliziaService.elaboraSentenze(); //8.13 - 8.14
		invioDati();
	}
	
	private void invioDati(){
		if(Configuration.isAbilitaPOLIZIA() == false)
			return;
		List<TPoliziaMsg> elementList = poliziaService.getDaInviare();
		if(elementList != null)
		{
			Iterator<TPoliziaMsg> iter = elementList.iterator();
			while(iter.hasNext() ) {
				TPoliziaMsg element = (TPoliziaMsg)iter.next();
				try{
					if(element.getTTpPoliziaServizi().getCodiKIdServizi() == Constants.POLIZIA_SEQUESTRO)
					{
						SequestroRequest request = JAXBUtil.unmarshall(SequestroRequest.class, element.getXmlDati().getAsciiStream());
						SequestroResponse response = poliziaService.inviaSequestro(request);
						if(response != null)
							poliziaService.updateRispostaMsgDatabase(element.getCodiKIdMsg(), response.getEsito());
					}
					else if(element.getTTpPoliziaServizi().getCodiKIdServizi() == Constants.POLIZIA_REVOCA_SEQUESTRO)
					{
						RevocaSequestroRequest request = JAXBUtil.unmarshall(RevocaSequestroRequest.class, element.getXmlDati().getAsciiStream());
						RevocaSequestroResponse response = poliziaService.inviaRevocaSequestro(request);
						if(response != null)
							poliziaService.updateRispostaMsgDatabase(element.getCodiKIdMsg(), response.getEsito());
					}
					else if(element.getTTpPoliziaServizi().getCodiKIdServizi() == Constants.POLIZIA_PROPOSTE_MIS_PERS)
					{
						ProposteMisurePersonaliRequest request = JAXBUtil.unmarshall(ProposteMisurePersonaliRequest.class, element.getXmlDati().getAsciiStream());
						ProposteMisurePersonaliResponse response = poliziaService.inviaProposteMisurePersonali(request);
						if(response != null)
							poliziaService.updateRispostaMsgDatabase(element.getCodiKIdMsg(), response.getEsito());
					}
					else if(element.getTTpPoliziaServizi().getCodiKIdServizi() == Constants.POLIZIA_PROPOSTE_MIS_PATR)
					{
						ProposteMisurePatrimonialiRequest request = JAXBUtil.unmarshall(ProposteMisurePatrimonialiRequest.class, element.getXmlDati().getAsciiStream());
						ProposteMisurePatrimonialiResponse response = poliziaService.inviaProposteMisurePatrimoniali(request);
						if(response != null)
							poliziaService.updateRispostaMsgDatabase(element.getCodiKIdMsg(), response.getEsito());
					}
					else if(element.getTTpPoliziaServizi().getCodiKIdServizi() == Constants.POLIZIA_PROPOSTE_INDAGINI_SOGG)
					{
						IndaginiSoggettiPropostiTrCaRequest request = JAXBUtil.unmarshall(IndaginiSoggettiPropostiTrCaRequest.class, element.getXmlDati().getAsciiStream());
						IndaginiSoggettiPropostiTrCaResponse response = poliziaService.inviaIndaginiSoggettiPropostiTrCa(request);
						if(response != null)
							poliziaService.updateRispostaMsgDatabase(element.getCodiKIdMsg(), response.getEsito());
					}
					else if(element.getTTpPoliziaServizi().getCodiKIdServizi() == Constants.POLIZIA_DECRETI_SOSPENSIONE_REST_BENI)
					{
						DecretiSospensioneRestituzioneBeniRequest request = JAXBUtil.unmarshall(DecretiSospensioneRestituzioneBeniRequest.class, element.getXmlDati().getAsciiStream());
						DecretiSospensioneRestituzioneBeniResponse response = poliziaService.inviaDecretiSospensioneRestituzioneBeni(request);
						if(response != null)
							poliziaService.updateRispostaMsgDatabase(element.getCodiKIdMsg(), response.getEsito());
					}
					else if(element.getTTpPoliziaServizi().getCodiKIdServizi() == Constants.POLIZIA_DECRETI_PROVVISORI_APPLICAZIONE)
					{
						DecretiProvvisoriApplicazioneRequest request = JAXBUtil.unmarshall(DecretiProvvisoriApplicazioneRequest.class, element.getXmlDati().getAsciiStream());
						DecretiProvvisoriApplicazioneResponse response = poliziaService.inviaDecretiProvvisoriApplicazione(request);
						if(response != null)
							poliziaService.updateRispostaMsgDatabase(element.getCodiKIdMsg(), response.getEsito());
					}
					else if(element.getTTpPoliziaServizi().getCodiKIdServizi() == Constants.POLIZIA_DECRETI_PROVVISORI_REVOCA)
					{
						DecretiProvvisoriRevocaRequest request = JAXBUtil.unmarshall(DecretiProvvisoriRevocaRequest.class, element.getXmlDati().getAsciiStream());
						DecretiProvvisoriRevocaResponse response = poliziaService.inviaDecretiProvvisoriRevoca(request);
						if(response != null)
							poliziaService.updateRispostaMsgDatabase(element.getCodiKIdMsg(), response.getEsito());
					}
					else if(element.getTTpPoliziaServizi().getCodiKIdServizi() == Constants.POLIZIA_DECRETI_DECISORI_I_GRADO_PERS)
					{
						DecretiDecisoriIGradoPersonaliRequest request = JAXBUtil.unmarshall(DecretiDecisoriIGradoPersonaliRequest.class, element.getXmlDati().getAsciiStream());
						DecretiDecisoriIGradoPersonaliResponse response = poliziaService.inviaDecretiDecisoriIGradoPersonali(request);
						if(response != null)
							poliziaService.updateRispostaMsgDatabase(element.getCodiKIdMsg(), response.getEsito());
					}
					else if(element.getTTpPoliziaServizi().getCodiKIdServizi() == Constants.POLIZIA_DECRETI_DECISORI_I_GRADO_PATR)
					{
						DecretiDecisoriIGradoPatrimonialiRequest request = JAXBUtil.unmarshall(DecretiDecisoriIGradoPatrimonialiRequest.class, element.getXmlDati().getAsciiStream());
						DecretiDecisoriIGradoPatrimonialiResponse response = poliziaService.inviaDecretiDecisoriIGradoPatrimoniali(request);
						if(response != null)
							poliziaService.updateRispostaMsgDatabase(element.getCodiKIdMsg(), response.getEsito());
					}
					else if(element.getTTpPoliziaServizi().getCodiKIdServizi() == Constants.POLIZIA_DECRETI_DECISORI_II_GRADO_PERS)
					{
						DecretiDecisoriIIGradoPersonaliRequest request = JAXBUtil.unmarshall(DecretiDecisoriIIGradoPersonaliRequest.class, element.getXmlDati().getAsciiStream());
						DecretiDecisoriIIGradoPersonaliResponse response = poliziaService.inviaDecretiDecisoriIIGradoPersonali(request);
						if(response != null)
							poliziaService.updateRispostaMsgDatabase(element.getCodiKIdMsg(), response.getEsito());
					}
					else if(element.getTTpPoliziaServizi().getCodiKIdServizi() == Constants.POLIZIA_DECRETI_DECISORI_II_GRADO_PATR)
					{
						DecretiDecisoriIIGradoPatrimonialiRequest request = JAXBUtil.unmarshall(DecretiDecisoriIIGradoPatrimonialiRequest.class, element.getXmlDati().getAsciiStream());
						DecretiDecisoriIIGradoPatrimonialiResponse response = poliziaService.inviaDecretiDecisoriIIGradoPatrimoniali(request);
						if(response != null)
							poliziaService.updateRispostaMsgDatabase(element.getCodiKIdMsg(), response.getEsito());
					}
					else if(element.getTTpPoliziaServizi().getCodiKIdServizi() == Constants.POLIZIA_SENTENZA_CASSAZIONE_PERS)
					{
						SentenzaCassazionePersonaliRequest request = JAXBUtil.unmarshall(SentenzaCassazionePersonaliRequest.class, element.getXmlDati().getAsciiStream());
						SentenzaCassazionePersonaliResponse response = poliziaService.inviaSentenzaCassazionePersonali(request);
						if(response != null)
							poliziaService.updateRispostaMsgDatabase(element.getCodiKIdMsg(), response.getEsito());
					}
					else if(element.getTTpPoliziaServizi().getCodiKIdServizi() == Constants.POLIZIA_SENTENZA_CASSAZIONE_PATR)
					{
						SentenzaCassazionePatrimonialiRequest request = JAXBUtil.unmarshall(SentenzaCassazionePatrimonialiRequest.class, element.getXmlDati().getAsciiStream());
						SentenzaCassazionePatrimonialiResponse response = poliziaService.inviaSentenzaCassazionePatrimoniali(request);
						if(response != null)
							poliziaService.updateRispostaMsgDatabase(element.getCodiKIdMsg(), response.getEsito());
					}
					else if(element.getTTpPoliziaServizi().getCodiKIdServizi() == Constants.POLIZIA_DEFINITIVITA_PERS)
					{
						DefinitivitaPersonaliRequest request = JAXBUtil.unmarshall(DefinitivitaPersonaliRequest.class, element.getXmlDati().getAsciiStream());
						DefinitivitaPersonaliResponse response = poliziaService.inviaDefinitivitaPersonali(request);
						if(response != null)
							poliziaService.updateRispostaMsgDatabase(element.getCodiKIdMsg(), response.getEsito());
					}
					else if(element.getTTpPoliziaServizi().getCodiKIdServizi() == Constants.POLIZIA_DEFINITIVITA_PATR)
					{
						DefinitivitaPatrimonialiRequest request = JAXBUtil.unmarshall(DefinitivitaPatrimonialiRequest.class, element.getXmlDati().getAsciiStream());
						DefinitivitaPatrimonialiResponse response = poliziaService.inviaDefinitivitaPatrimoniali(request);
						if(response != null)
							poliziaService.updateRispostaMsgDatabase(element.getCodiKIdMsg(), response.getEsito());
					}
					else if(element.getTTpPoliziaServizi().getCodiKIdServizi() == Constants.POLIZIA_INDAGINI_SOGGETTI_PROPOSTI_TR_CA)
					{
						IndaginiSoggettiPropostiRequest request = JAXBUtil.unmarshall(IndaginiSoggettiPropostiRequest.class, element.getXmlDati().getAsciiStream());
						IndaginiSoggettiPropostiResponse response = poliziaService.inviaIndaginiSoggettiProposti(request);
						if(response != null)
							poliziaService.updateRispostaMsgDatabase(element.getCodiKIdMsg(), response.getEsito());
					}
					else if(element.getTTpPoliziaServizi().getCodiKIdServizi() == Constants.POLIZIA_PARERE_REVOCA_MODIFICA_MISURE_PERSONALI)
					{
						ParereRevocaModificaMisurePersonaliRequest request = JAXBUtil.unmarshall(ParereRevocaModificaMisurePersonaliRequest.class, element.getXmlDati().getAsciiStream());
						ParereRevocaModificaMisurePersonaliResponse response = poliziaService.inviaParereRevocaModificaMisurePersonali(request);
						if(response != null)
							poliziaService.updateRispostaMsgDatabase(element.getCodiKIdMsg(), response.getEsito());
					}
					else if(element.getTTpPoliziaServizi().getCodiKIdServizi() == Constants.POLIZIA_VARIAZIONE_ANAGRAFICA_SOGGETTI_INTERESSATI)
					{
						VariazioneAnagraficheSoggettiInteressatiRequest request = JAXBUtil.unmarshall(VariazioneAnagraficheSoggettiInteressatiRequest.class, element.getXmlDati().getAsciiStream());
						VariazioneAnagraficheSoggettiInteressatiResponse response = poliziaService.inviaVariazioneAnagraficheSoggettiInteressati(request);
						if(response != null)
							poliziaService.updateRispostaMsgDatabase(element.getCodiKIdMsg(), response.getEsito());
					}
					else if(element.getTTpPoliziaServizi().getCodiKIdServizi() == Constants.POLIZIA_RICHIESTA_AGGRAVAMENTO_MISURE_PERSONALI)
					{
						RichiestaAggravamentoMisuraPersonaleRequest request = JAXBUtil.unmarshall(RichiestaAggravamentoMisuraPersonaleRequest.class, element.getXmlDati().getAsciiStream());
						RichiestaAggravamentoMisuraPersonaleResponse response = poliziaService.inviaRichiestaAggravamentoMisuraPersonale(request);
						if(response != null)
							poliziaService.updateRispostaMsgDatabase(element.getCodiKIdMsg(), response.getEsito());
					}
					else if(element.getTTpPoliziaServizi().getCodiKIdServizi() == Constants.POLIZIA_DECISORIO_AGGRAVAMENTO_MISURE_PERSONALI)
					{
						DecisorioAggravamentoMisuraPersonaleRequest request = JAXBUtil.unmarshall(DecisorioAggravamentoMisuraPersonaleRequest.class, element.getXmlDati().getAsciiStream());
						DecisorioAggravamentoMisuraPersonaleResponse response = poliziaService.inviaDecisorioAggravamentoMisuraPersonale(request);
						if(response != null)
							poliziaService.updateRispostaMsgDatabase(element.getCodiKIdMsg(), response.getEsito());
					}
					else if(element.getTTpPoliziaServizi().getCodiKIdServizi() == Constants.POLIZIA_DEFINITIVITA_AGGRAVAMENTO_MISURE_PERSONALI)
					{
						DefinitivitaAggravamentoMisuraPersonaleRequest request = JAXBUtil.unmarshall(DefinitivitaAggravamentoMisuraPersonaleRequest.class, element.getXmlDati().getAsciiStream());
						DefinitivitaAggravamentoMisuraPersonaleResponse response = poliziaService.inviaDefinitivitaAggravamentoMisuraPersonale(request);
						if(response != null)
							poliziaService.updateRispostaMsgDatabase(element.getCodiKIdMsg(), response.getEsito());
					}
					else if(element.getTTpPoliziaServizi().getCodiKIdServizi() == Constants.POLIZIA_RICHIESTA_MODIFICA_REVOCA_PATRIMONIALE_PERSONALE)
					{
						RichiestaModificaRevocaMisuraPersonaleRequest request = JAXBUtil.unmarshall(RichiestaModificaRevocaMisuraPersonaleRequest.class, element.getXmlDati().getAsciiStream());
						RichiestaModificaRevocaMisuraPersonaleResponse response = poliziaService.inviaRichiestaModificaRevocaMisuraPersonale(request);
						if(response != null)
							poliziaService.updateRispostaMsgDatabase(element.getCodiKIdMsg(), response.getEsito());
					}
					else if(element.getTTpPoliziaServizi().getCodiKIdServizi() == Constants.POLIZIA_DECISORIO_MODIFICA_REVOCA_PATRIMONIALE_PERSONALE)
					{
						DecisorioRevocaModificaMisuraPersonaleRequest request = JAXBUtil.unmarshall(DecisorioRevocaModificaMisuraPersonaleRequest.class, element.getXmlDati().getAsciiStream());
						DecisorioRevocaModificaMisuraPersonaleResponse response = poliziaService.inviaDecisorioRevocaModificaMisuraPersonale(request);
						if(response != null)
							poliziaService.updateRispostaMsgDatabase(element.getCodiKIdMsg(), response.getEsito());
					}
					else if(element.getTTpPoliziaServizi().getCodiKIdServizi() == Constants.POLIZIA_DEFINITIVITA_MODIFICA_REVOCA_PATRIMONIALE_PERSONALE)
					{
						DefinitivitaRevocaModificaMisuraPersonaleRequest request = JAXBUtil.unmarshall(DefinitivitaRevocaModificaMisuraPersonaleRequest.class, element.getXmlDati().getAsciiStream());
						DefinitivitaRevocaModificaMisuraPersonaleResponse response = poliziaService.inviaDefinitivitaRevocaModificaMisuraPersonale(request);
						if(response != null)
							poliziaService.updateRispostaMsgDatabase(element.getCodiKIdMsg(), response.getEsito());
					}
					else if(element.getTTpPoliziaServizi().getCodiKIdServizi() == Constants.POLIZIA_DEFINITIVITA_VARIAZIONE_ANAGRAFICA_SOGGETTI_INTERESSATI)
					{
						DefinitivitaCorrezioneErroreDatiSoggettoRequest request = JAXBUtil.unmarshall(DefinitivitaCorrezioneErroreDatiSoggettoRequest.class, element.getXmlDati().getAsciiStream());
						DefinitivitaCorrezioneErroreDatiSoggettoResponse response = poliziaService.inviaDefinitivitaCorrezioneErroreDatiSoggetto(request);
						if(response != null)
							poliziaService.updateRispostaMsgDatabase(element.getCodiKIdMsg(), response.getEsito());
					}
					else if(element.getNumeStato() == 6)
					{
						PoliziaMsgErrorRequest request = poliziaService.createPoliziaMsgErrorRequest(element.getCodiKIdMsg());
						PoliziaMsgErrorResponse response = poliziaService.inviaMessaggiDiErrore(request);
						if(response != null)
							poliziaService.updateRispostaMsgDatabase(element.getCodiKIdMsg(), response.getEsito());
					}
					
				}
				catch(JAXBException ex)
				{logger.error("Errore in PoliziaJob.invioDati: " + ex);}
				catch(IOException ex)
				{logger.error("Errore in PoliziaJob.invioDati: " + ex);}
				catch(SQLException ex)
				{logger.error("Errore in PoliziaJob.invioDati: " + ex);}

			}
		}
	}
	 
	
	
}
