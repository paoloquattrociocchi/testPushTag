package it.giustizia.smp.scheduler;

import it.giustizia.smp.dao.CommonDao;
import it.giustizia.smp.dto.ChiaveRicorsoCassazioneDto;
import it.giustizia.smp.multidb.UserRoutingDataSource;
import it.giustizia.smp.service.webservices.RicorsoCassazioneService;

import java.util.List;

import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

/***
 * Job per l'invio posticipato dei ricorsi in cassazione
 * 
 * @author alamviva
 * 
 */
public class InvioCassazioneJob {

	@Autowired
	private UserRoutingDataSource myDataSource;
	@Autowired
	private CommonDao commonDao;
	@Autowired
	private RicorsoCassazioneService ricorsoCassazioneService;
	
	private static boolean hasInstaceInRun = false;
	private Logger logger = Logger.getLogger(getClass());
	
//	@Scheduled(cron = "0 0/5 8-20 * * ?")// scheduler ogni giorno dalle 08 alle 20 ogni 5 minuti
//	 @Scheduled(cron="0 0/45 5-6 * * ?")//scheduler ogni giorno dalle 1 alle 2 ogni 45 minuti
	public void scheduledMethod() {
		elaboraJob("SMP_GIU");
		myDataSource.setSchemaDocumentale("");
	}

	private void elaboraJob(String schema) {
		myDataSource.selectConnection(schema);
		try {
			if (!hasInstaceInRun) {
				hasInstaceInRun = true;
				System.out.println("Inizio Job - Invio ricorso in cassazione.");
				inviaInCassazione();
				System.out.println("Fine Job - Invio ricorso in cassazione.");
			}
		} catch (Exception e) {
			logger.error("Errore durante l'esecuzione del job InvioCassazioneJob: " + e.getMessage());
		}
		hasInstaceInRun = false;
	}

	private void inviaInCassazione() {

		// Estraggo la lista dei ricorso che non sono ancora stati inviati
		List<ChiaveRicorsoCassazioneDto> listaRicorsiDaInviare = commonDao.getListaRicorsiDaInviare();
		for (ChiaveRicorsoCassazioneDto chiaveRicorsoCassazioneDto : listaRicorsiDaInviare) {
			// Il servizio per inviare nuovamente il ricorso
			try {
				ricorsoCassazioneService.inserisciRicorso(
						chiaveRicorsoCassazioneDto.getCODI_K_ID_UFF_OWNER().intValue(), 
						chiaveRicorsoCassazioneDto.getCODI_K_ID_ATTO().longValue(), 
						chiaveRicorsoCassazioneDto.getCODI_K_ID_PROC().longValue()
						
				);
			} catch (Exception e) {
				logger.error("Errore durante l'esecuzione del job InvioCassazioneJob: " + e.getMessage());
			}
			
		}

	
	}

}
