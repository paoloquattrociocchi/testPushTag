package it.giustizia.smp.service.webservices;

import it.giustizia.smp.dao.AttoDao;
import it.giustizia.smp.dao.CommonDao;
import it.giustizia.smp.dao.ProcedimentoDao;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.model.TAnComuni;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.webservice.client.agenziaentrate.AgenziaEntrateServiceClient;
import it.giustizia.smp.webservice.client.agenziaentrate.DatiPersonaFisicaType;
import it.giustizia.smp.webservice.client.agenziaentrate.DatiSoggettoDiversoType;
import it.giustizia.smp.webservice.client.agenziaentrate.EsitoControlloCFePIWrapper;
import it.giustizia.smp.webservice.client.agenziaentrate.InterrogazioneDatiAnagraficiPersonaFisicaType;
import it.giustizia.smp.webservice.client.agenziaentrate.InterrogazioneDatiAnagraficiSoggettiDiversiType;
import it.giustizia.smp.webservice.client.agenziaentrate.SessoType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/***
 * Classe per l'implementazione del controllo del CF tramite la chimata al WS
 * @author m.prisco
 *
 */
public class AgenziaEntrateService  {

	@Autowired
	private AgenziaEntrateServiceClient agenziaEntrateServiceClient;

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private AttoDao attoDao;

	@Autowired
	private ProcedimentoDao procedimentoDao;

	private Logger logger = Logger.getLogger(getClass());
	
	/***
	 * Implementazione del controllo del codice fiscale tramite il servizio dell'agenzia delle entrate
	 * @param soggettoForm
	 * @return Esito Controllo
	 */
	public EsitoControlloCFePIWrapper controllaDatiAnagraficiPF(SoggettoForm soggettoForm) {
		InterrogazioneDatiAnagraficiPersonaFisicaType datiAnagraficiPF = new InterrogazioneDatiAnagraficiPersonaFisicaType();
		datiAnagraficiPF.setCognome(soggettoForm.getCognome());
		datiAnagraficiPF.setNome(soggettoForm.getNome());
		if (soggettoForm.getDataNascita()!=null)
			datiAnagraficiPF.setDataDiNascita(Utility.toXMLGregorianCalendar(soggettoForm.getDataNascita()));
		
		if (soggettoForm.getComuneNascita()!=null && soggettoForm.getComuneNascita().getId()!=null) {
			TAnComuni comune =  commonDao.getComuneById(Long.parseLong(soggettoForm.getComuneNascita().getId()));
			datiAnagraficiPF.setComuneDiNascita(comune.getDescComune());
			if (soggettoForm.getSiglaProvinciaNascita()!=null) {
				datiAnagraficiPF.setProvinciaDiNascita(soggettoForm.getSiglaProvinciaNascita());
			}
		} else  if (soggettoForm.getComuneNascita()!=null && soggettoForm.getComuneNascita().getDescrizione()!=null) {
			datiAnagraficiPF.setComuneDiNascita(soggettoForm.getComuneNascita().getDescrizione());
			
		}
		/*
		if (soggettoForm.getProvinciaNascita()!=null && soggettoForm.getProvinciaNascita() != null ) {
			datiAnagrafici.setProvinciaDiNascita(soggettoForm.getProvinciaNascita().getId());
		}*/
		
		if (soggettoForm.getSesso()!=null && !soggettoForm.getSesso().equals("-1")   ) {
			datiAnagraficiPF.setSesso((soggettoForm.getSesso().equals("M")?SessoType.M:SessoType.F));
		}
		return controllaDatiAnagrafici(datiAnagraficiPF);
	}
	
	
	/***
	 * Implementazione del controllo della partita iva tramite il servizio dell'agenzia delle entrate
	 * @param soggettoForm
	 * @return Esito Controllo
	 */
	public EsitoControlloCFePIWrapper controllaDatiAnagraficiPI(SoggettoForm soggettoForm) {
		InterrogazioneDatiAnagraficiSoggettiDiversiType datiAnagraficiPG = new InterrogazioneDatiAnagraficiSoggettiDiversiType();
		datiAnagraficiPG.setDescrizioneDenominazione(soggettoForm.getRagioneSociale());
		
		/*
		if (soggettoForm.getDataNascita()!=null)
			datiAnagraficiPG.setDataDiNascita(Utility.toXMLGregorianCalendar(soggettoForm.getDataNascita()));
		
		if (soggettoForm.getComuneNascita()!=null && soggettoForm.getComuneNascita().getId()!=null) {
			TAnComuni comune =  commonDao.getComuneById(Long.parseLong(soggettoForm.getComuneNascita().getId()));
			datiAnagraficiPG.setComuneDiNascita(comune.getDescComune());
			if (soggettoForm.getSiglaProvinciaNascita()!=null) {
				datiAnagraficiPG.setProvinciaDiNascita(soggettoForm.getSiglaProvinciaNascita());
			}
		} else  if (soggettoForm.getComuneNascita()!=null && soggettoForm.getComuneNascita().getDescrizione()!=null) {
			datiAnagraficiPG.setComuneDiNascita(soggettoForm.getComuneNascita().getDescrizione());
			
		}
		/*
		if (soggettoForm.getProvinciaNascita()!=null && soggettoForm.getProvinciaNascita() != null ) {
			datiAnagrafici.setProvinciaDiNascita(soggettoForm.getProvinciaNascita().getId());
		}
		
		if (soggettoForm.getSesso()!=null && !soggettoForm.getSesso().equals("-1")   ) {
			datiAnagraficiPG.setSesso((soggettoForm.getSesso().equals("M")?SessoType.M:SessoType.F));
		}*/
		return controllaDatiAnagrafici(datiAnagraficiPG);
	}
	
	/***
	 * Implementazione del controllo del codice fiscale tramite il servizio dell'agenzia delle entrate
	 * @param soggettoForm
	 * @return Esito Controllo
	 */
	public EsitoControlloCFePIWrapper controllaDatiAnagrafici(InterrogazioneDatiAnagraficiSoggettiDiversiType datiAnagrafici) {
		DatiSoggettoDiversoType response = null;
		try {
			response = agenziaEntrateServiceClient.interrogazioneDatiAnagraficiSoggettiDiversi(datiAnagrafici);
		} catch (Exception e) {
			logger.error("Errore durante la chiamata al WS Agenzia Entrate", e);
		}
		return new EsitoControlloCFePIWrapper(response);
	}
	
	
	
	/***
	 * Implementazione del controllo del codice fiscale tramite il servizio dell'agenzia delle entrate
	 * @param soggettoForm
	 * @return Esito Controllo
	 */
	public EsitoControlloCFePIWrapper controllaDatiAnagrafici(InterrogazioneDatiAnagraficiPersonaFisicaType datiAnagrafici) {
		DatiPersonaFisicaType response = null;
		try {
			response = agenziaEntrateServiceClient.interrogazioneDatiAnagraficiPersonaFisica(datiAnagrafici);
		} catch (Exception e) {
			logger.error("Errore durante la chiamata al WS Agenzia Entrate", e);
		}
		return new EsitoControlloCFePIWrapper(response);
	}
	

}

