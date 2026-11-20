package it.giustizia.smp.scheduler;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import it.giustizia.smp.dao.NotificheDao;
import it.giustizia.smp.dao.ProcedimentoDao;
import it.giustizia.smp.model.TNotifDest;
import it.giustizia.smp.multidb.UserRoutingDataSource;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.webservice.client.glmp.servizitrasmissione.RecuperaRapportoTrasmissioneResponse;
import it.giustizia.smp.webservice.client.glmp.servizitrasmissione.ServiziTrasmissioneServiceClient;

 /***
 * Job per la ricezione dei rapporti di Trasmissione dal sistema GL 
 * 
 * @author alamviva
 * 
 */
public class NotificheRicezioneRappTrasmJob {

	@Autowired
	private UserRoutingDataSource myDataSource;
	@Autowired
	private NotificheDao notificheDao;
	@Autowired
	private ProcedimentoDao procedimentoDao;
	@Autowired
	ServiziTrasmissioneServiceClient serviziTrasmissioneServiceClient;
	
	private Logger logger = Logger.getLogger(getClass());
	private static boolean hasInstaceInRun = false;

	
	public void scheduledMethod() {
		if (!hasInstaceInRun) {
			hasInstaceInRun = true;
			elaboraJob("SMP_GIU");
			elaboraJob("SMP");
			myDataSource.setSchemaDocumentale("");
		}
		hasInstaceInRun = false;
	}

	private void elaboraJob(String schema) {
		try {
			logger.info("Inizio Job - Ricezione Rapporti Trasmissione GL su schema: " +schema);
			myDataSource.selectConnection(schema);
			ricezioneRappTrasm();
			logger.info("Fine Job - Ricezione Rapporti Trasmissione GL su schema: " +schema);
		} catch (Exception e) {
			logger.error("Fine Job - Ricezione Rapporti Trasmissione GL con Errore:" + e.getMessage());
			
		}
	}

	/***
	 * Implementazione della ricezione dei rapporti di trasmissione ricevute
	 */
	private void ricezioneRappTrasm() throws Exception {
		// Estraggo la lista delle notifiche per le quali la trasmissione è conclusa ed il rapporto non è arrivato
		List<TNotifDest> attesaRappTrasm = notificheDao.getNotifDestAttesaRappTrasm();
		for (TNotifDest tNotifDest : attesaRappTrasm) {
			String idTrasmissione = tNotifDest.getDescCodiTrasmissione();
			logger.info("Lettura della id pddoc del rapporto dal servizio GL: " + idTrasmissione);
			RecuperaRapportoTrasmissioneResponse response = null;
			try {
				response = serviziTrasmissioneServiceClient.recuperaRapportoTrasmissione(idTrasmissione);
				if(response != null && response.getReturn() != null)
				{
					String idPdocTrasmissione= response.getReturn();
					//String idPdocTrasmissione= "123454321";
					
					// Creare il nuovo atto e del record relativo alla ricevuta e 
					// modifico TNotifDest impostato lo stato a finale se necessario
					int idUfficioOwn = tNotifDest.getTNotifRichieste().getTNotifiche().getCodiFkIdUffOwner();
					long idAttoNotificato = tNotifDest.getTNotifRichieste().getTNotifiche().getCodiFkIdAtto();
					long idProcedimento = procedimentoDao.getIdByAttoEUfficio(idAttoNotificato, idUfficioOwn);
	
					// Inserisco la ricevuta e controllo e imposto lo stato della trasmissione conclusa se è una ricevuta di tipo finale.
					notificheDao.inserisciAttoRappTrasm(	idUfficioOwn,
														Constants.CAT_ATTO_RAPP_TRASMISSIONE, 
														"Job Ricezione RappTrasm", 
														idProcedimento,
														tNotifDest.getCodiKIdNotifDest(),
														idPdocTrasmissione);
				}
				else
					logger.error("Errore durante l'invocazione con idTrasmissione: " + idTrasmissione + " il servizio ha restituito null");
				
				
			} catch (Exception ex){
				logger.error("Errore durante l'invocazione -> idTrasmissione " + idTrasmissione);
				logger.error(ex);
			}

			
		}
	}
}
/*
// Per ogni evento disponibile negli eventi di trasmissione controllo 
// se l'evento è agganciato all'arrivo di una ricevuta 
if (response!=null &&  response.getReturn().getStoricoEventi()!=null) {
	for (EventoTrasmissione eventoTrasmissione : response.getReturn().getStoricoEventi()) {
		String  idRicevuta  = eventoTrasmissione.getIdRicevuta();
		if (!"".equals(eventoTrasmissione.getIdRicevuta()) && idRicevuta!=null) {
			TTpStatiTrasm tpStatiTrasm =  notificheDao.getTTpStatiTrasmById(eventoTrasmissione.getCodiceStato());
			String statoTrasmissione = eventoTrasmissione.getCodiceStato();
			
			//Verifico se evento già gestito 
			if (!notificheDao.isStatoTrasmissioneGiaGestito(statoTrasmissione,tNotifDest.getCodiKIdNotifDest()) ) {
				logger.info("Invocazione servizio -> idTrasmissione " + idTrasmissione);
				DownloadRicevutaResponse ricevutaResponse =  serviziTrasmissioneServiceClient.downloadRicevuta(idRicevuta);
				System.out.println("CONTENT TYPE RICEVUTA: " +  ricevutaResponse.getReturn().getContentType() );
				byte[] file = IOUtils.toByteArray(ricevutaResponse.getReturn().getInputStream());
				
				// Determino il tipo della ricevuta dalla stato:
				if (tpStatiTrasm.getCodiFkIdCatAtto()==null) {
					logger.error("Impossibile inserire la ricevuto per la trasmissione " + idTrasmissione + " perchè la categoria atto associate è null." );
				} else {
					int codiKIdCatAttoRicevuta = tpStatiTrasm.getCodiFkIdCatAtto();
					
					// Modificato perchè ritorna octect stream
					//String tipoDoc = ricevutaResponse.getReturn().getContentType();
					String tipoDoc = "message/rfc822";
					
					// Creare il nuovo atto e del record relativo alla ricevuta e 
					// modifico TNotifDest impostato lo stato a finale se necessario
					int idUfficioOwn = tNotifDest.getTNotifRichieste().getTNotifiche().getCodiFkIdUffOwner();
					long idAttoNotificato = tNotifDest.getTNotifRichieste().getTNotifiche().getCodiFkIdAtto();
					long idProcedimento = procedimentoDao.getIdByAttoEUfficio(idAttoNotificato, idUfficioOwn);

					// Inserisco la ricevuta e controllo e imposto lo stato della trasmissione conclusa se è una ricevuta di tipo finale.
					notificheDao.inserisciAttoRicevuta(	idUfficioOwn,
														codiKIdCatAttoRicevuta, 
														"Job Ricezione Ricevute", 
														idProcedimento,
														tNotifDest.getCodiKIdNotifDest(),
														statoTrasmissione, 
														file,
														tipoDoc);
				}
			}
		}
	}*/
