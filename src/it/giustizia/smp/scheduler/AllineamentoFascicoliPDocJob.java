package it.giustizia.smp.scheduler;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import it.giustizia.smp.dao.AllineaFascPdocDao;
import it.giustizia.smp.model.TAllineaFascPdoc;
import it.giustizia.smp.multidb.UserRoutingDataSource;
import it.giustizia.smp.service.AttoService;
import it.giustizia.smp.service.ProcedimentoService;
import it.giustizia.smp.service.webservices.IDocumentaleService;
import it.giustizia.smp.webservice.client.allineamentoFascicoliPDoc.model.general.recuperoFascicoloResponse.Risposta;

/**
 *
 * 20200709 [SG]: nuovo job per allineamento fascicoli PDoc andati in errore col
 * documentaleJob
 *
 * @author Gioggi
 *
 */
public class AllineamentoFascicoliPDocJob {

	@Autowired
	private UserRoutingDataSource myDataSource;
	@Autowired
	private AttoService attoService;
	@Autowired
	private IDocumentaleService documentaleService;
	@Autowired
	private ProcedimentoService procedimentoService;
	@Autowired
	private AllineaFascPdocDao allineaFascPdocDao;

	private Logger logger = Logger.getLogger(getClass());

	public void scheduledMethod() {

	    System.out.println("1) Inizio AllineamentoFascicoliPDoc Job ->SMP<-");
    	elaboraJob("SMP");
	    System.out.println("3) Fine AllineamentoFascicoliPDocJob Job ->SMP<-");
		
	    System.out.println("1) Inizio AllineamentoFascicoliPDocJob Job ->SMP_GIU<-");
		elaboraJob("SMP_GIU");
		myDataSource.setSchemaDocumentale("");
		System.out.println("3) Fine AllineamentoFascicoliPDoc Job ->SMP_GIU<-");
	}

	private void elaboraJob(String schema) {

		myDataSource.selectConnection(schema);
		if (testConnessione()) {
			recuperaFascicolo();
		} else {
			System.out.println(
					"2) ERRORE CONNESSIONE !! Verificare Servizio PDoc se raggiungibile!!");
		}
	}

	private boolean testConnessione() {

		int idUffOwner = attoService.getUffOwnerTestDocumentale();
		if (logger.isDebugEnabled()) {
			logger.debug("    1.1) TEST CONNESSIONE - Ufficio pilota per test connessione: "
					+ Integer.toString(idUffOwner) + ";");
		}
		return documentaleService.esitoTestConnessione(idUffOwner);
	}

	private void recuperaFascicolo() {

		List<TAllineaFascPdoc> listFascicoli = allineaFascPdocDao.getDaInviare();

		System.out.println("2) - Recupero Elenco Fascicoli");
		if (listFascicoli != null && !listFascicoli.isEmpty()) {
			System.out.println("	2.1) Numero Fascicoli recuperati: " + listFascicoli.size());
			Iterator<TAllineaFascPdoc> it = listFascicoli.iterator();
			while (it.hasNext()) {
				TAllineaFascPdoc tAllineaFascPdoc = it.next();
				System.out.println("	2.1) Elaborazione Fascicolo: " + tAllineaFascPdoc.getCodiFkIdProc() + " / "
						+ tAllineaFascPdoc.getDescFascPdoc());
				// recupero fascicolo
				// GC 14/07/2020: corretto il metodo passando direttamente l'owner per evitare
				// l'ulteriore chiamata al db
				Risposta risposta = procedimentoService.recuperaFascicoloPDoc(
						(int) (tAllineaFascPdoc.getCodiFkIdUffOwner()), tAllineaFascPdoc.getDescFascPdoc());
				if (risposta != null && risposta.getMessaggio() != null && risposta.getMessaggio().isEsito()) {
					// GC 14/07/2020: aggiornamento log della chiamata
					String msg = "Fascicolo " + tAllineaFascPdoc.getDescFascPdoc() + " già presente in PDOC";
					logger.info("	2.2) " + msg);
					// aggiorna tabella T_ALLINEA_FASC_PDOC: flag a S e descMSG = response
					allineaFascPdocDao.updateTAllineaFascPdoc(tAllineaFascPdoc.getcodiKIdAllineaFascPdoc(), "S", msg);
				} else {
					logger.debug("Fascicolo NON TROVATO: inserisco e creo fascicolo!");
					boolean esitoInsertFascicoloPdoc = procedimentoService.inserisciECreaFascicoloPDoc(tAllineaFascPdoc.getCodiFkIdProc(),
							tAllineaFascPdoc.getDescFascPdoc());
					if (esitoInsertFascicoloPdoc)
						// aggiorna tabella T_ALLINEA_FASC_PDOC: flag a S e descMSG
						allineaFascPdocDao.updateTAllineaFascPdoc(tAllineaFascPdoc.getcodiKIdAllineaFascPdoc(), "S", 
								"	2.3) Fascicolo: allineato correttamente");
					else
						// aggiorna tabella T_ALLINEA_FASC_PDOC: flag a N e descMSG
						allineaFascPdocDao.updateTAllineaFascPdoc(tAllineaFascPdoc.getcodiKIdAllineaFascPdoc(), "N",
								"	2.3) ERRORE nell'allineamento: verificare il log Sitmp per maggiori dettagli.");
				}
			}
		} else {
			System.out.println("  2.2)" + " Non ci sono Fascicoli da allineare!");
		}
	}

}