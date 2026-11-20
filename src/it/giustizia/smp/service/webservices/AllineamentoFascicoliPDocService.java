package it.giustizia.smp.service.webservices;

import java.rmi.ConnectException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.giustizia.smp.dao.CommonDao;
import it.giustizia.smp.model.TAnUtenzePdoc;
import it.giustizia.smp.webservice.client.allineamentoFascicoliPDoc.IAllineamentoFascicoliPDocClient;
import it.giustizia.smp.webservice.client.allineamentoFascicoliPDoc.model.general.recuperoFascicoloResponse.Risposta;

@Service
public class AllineamentoFascicoliPDocService implements IAllineamentoFascicoliPDocService {

	@Autowired
	private IAllineamentoFascicoliPDocClient allineamentoFascicoliPDocClient;
	@Autowired
	private CommonDao commonDao;

	private Logger logger = Logger.getLogger(getClass());

	public Risposta recuperaFascicolo(String codiceFascicolo, int idUffOwner) {

		Risposta response = null;
		TAnUtenzePdoc utenzaPdoc = commonDao.getUtenzaPdoc(idUffOwner);
		try {
			response = allineamentoFascicoliPDocClient.recuperaFascicolo(codiceFascicolo, utenzaPdoc);
		} catch (ConnectException e) {
			e.printStackTrace();
		}
		if (logger.isDebugEnabled()) {
			logger.debug("   0.2 ) AllineamentoFascicoliPDocService ==> codiceFascicolo: " + codiceFascicolo
					+ " Recuperato fascicolo in PDoc: "
					+ ((response != null && response.getFascicolo() != null)
							? response.getFascicolo().getCodicefascicolo()
							: "NESSUNO!"));
		}
		return response;
	}

}