package it.giustizia.smp.service.msg;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

//import it.giustizia.smp.controller.procedimento.AttoController.EsitoFromResponse;
import it.giustizia.smp.dao.AnbscDao;
import it.giustizia.smp.dao.CommonDao;
import it.giustizia.smp.model.TAtto;
import it.giustizia.smp.model.TDefPatr;
import it.giustizia.smp.model.TTpTipoUfficio;
import it.giustizia.smp.service.AnbscService;
import it.giustizia.smp.utility.AnbscEnum;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.webservice.client.anbsc.ConfiscaDefinitivaRequest;
import it.giustizia.smp.webservice.client.anbsc.ConfiscaDefinitivaResponse;
import it.giustizia.smp.webservice.client.anbsc.ConfiscaPrimoGradoRequest;
import it.giustizia.smp.webservice.client.anbsc.ConfiscaPrimoGradoResponse;
import it.giustizia.smp.webservice.client.anbsc.ConfiscaSecondoGradoRequest;
import it.giustizia.smp.webservice.client.anbsc.ConfiscaSecondoGradoResponse;
import it.giustizia.smp.webservice.client.anbsc.Esito;
import it.giustizia.smp.webservice.client.anbsc.RevocaConfiscaRequest;
import it.giustizia.smp.webservice.client.anbsc.RevocaConfiscaResponse;
import it.giustizia.smp.webservice.client.anbsc.RevocaSequestroRequest;
import it.giustizia.smp.webservice.client.anbsc.RevocaSequestroResponse;
import it.giustizia.smp.webservice.client.anbsc.RiunioneRequest;
import it.giustizia.smp.webservice.client.anbsc.RiunioneResponse;
import it.giustizia.smp.webservice.client.anbsc.SequestroRequest;
import it.giustizia.smp.webservice.client.anbsc.SequestroResponse;
import it.giustizia.smp.webservice.client.anbsc.StralcioRequest;
import it.giustizia.smp.webservice.client.anbsc.StralcioResponse;
import it.giustizia.smp.webservice.utility.JAXBUtil;

@Service
public class AnbscMsgService  {

	private static char ERRORE_VALIDAZIONE_XML = 'E';
	
	@Autowired
	private AnbscService anbscService;
	
	@Autowired
	private AnbscDao anbscDao;
	
	@Autowired
	private CommonDao commonDao;
	
	
	public AnbscDao getAnbscDao() {
		return anbscDao;
	}
	public void setAnbscDao(AnbscDao anbscDao) {
		this.anbscDao = anbscDao;
	}
	
	/**
	 * Invio Sequestro
	 * URL: value = { "/procedimento/atto/attoRegistrato.htm" }, method = RequestMethod.POST, params = { "Esecuzione" }
	 * CONDIZIONE: if(categoriaAtto.equals(Constants.DECRETO_SEQUESTRO_ORDINARIO) || categoriaAtto.equals(Constants.DECRETO_SEQUESTRO_ORDINARIO_II_GRADO))
	 * @param idProcedimento
	 * @param idAtto
	 * @param idUfficioUtente
	 * @param codUtente
	 * @return
	 */
	
	/* Mev 2020/22 Inizio
	public boolean invioSequestro(final long idProcedimento,final  long idAtto,final  Integer idUfficioUtente,final  String codUtente)
	{
		boolean result = true;
		SequestroRequest sequestroRequest = anbscService.createSequestroRequest(idProcedimento, idAtto, idUfficioUtente, Constants.DECRETO_SEQUESTRO_ORDINARIO);
		long idMsg = anbscService.insertMsgDatabase(sequestroRequest, idProcedimento, idAtto, AnbscEnum.ANBSC_SEQUESTRO.getId(), codUtente);
		if(idMsg > 0)
		{  
			SequestroResponse responseAnbsc = anbscService.inviaSequestro(sequestroRequest);
			if(responseAnbsc != null)
				anbscService.updateRispostaMsgDatabase(idMsg, responseAnbsc.getEsito(), responseAnbsc);
			else
				result = false;
		}
		return result;
	}
	*/

	public SequestroResponse invioSequestro(final long idProcedimento,final  long idAtto,final  Integer idUfficioUtente,final  String codUtente)
	{
		SequestroResponse responseAnbsc = null;
		SequestroRequest sequestroRequest = anbscService.createSequestroRequest(idProcedimento, idAtto, idUfficioUtente, Constants.DECRETO_SEQUESTRO_ORDINARIO);
		long idMsg = anbscService.insertMsgDatabase(sequestroRequest, idProcedimento, idAtto, AnbscEnum.ANBSC_SEQUESTRO.getId(), codUtente);
		if(idMsg > 0)
		{  
			// Controllo se XML che sto inviando e' corretto 
			byte[] out = null;
			try {
				out = JAXBUtil.validationAndMarshal(sequestroRequest);
			} catch (Exception e) {
				anbscService.updateRispostaMsgDatabase(idMsg, ERRORE_VALIDAZIONE_XML, e.getCause().getLocalizedMessage());	
				return responseAnbsc; 
			}						
			
			responseAnbsc = anbscService.inviaSequestro(sequestroRequest);
			if(responseAnbsc != null)
				anbscService.updateRispostaMsgDatabase(idMsg, responseAnbsc.getEsito(), responseAnbsc);			
		}
		return responseAnbsc;
	}
	// Mev 2020/22 Fine	
	
	/**
	 * URL: value = "/procedimento/atto/decretoConvalidaSequestro.htm", method = RequestMethod.POST, params = { "Invia" }
	 * 
	 * @param idProcedimento
	 * @param idAttoFi
	 * @param idUfficioUtente
	 * @param codUtente
	 * @param tipoSequestro
	 * @return
	 */
	/* Mev 2020/22 Inizio
	public boolean invioDecretoConvalidaSequestro(final long idProcedimento,final long idAttoFi,final Integer idUfficioUtente,final String codUtente,final String tipoSequestro)
	{
		boolean result = true;
		long idAttoOr = anbscService.recuperaAttoOrig(idAttoFi, idUfficioUtente); 
		
		if(idAttoOr != 0)
		{
			Long idTipoAnbscMsg = null;
			if(tipoSequestro.equals(Constants.DECRETO_CONVALIDA_SEQUESTRO_URGENTE))
				idTipoAnbscMsg = AnbscEnum.ANBSC_SEQUESTRO_URGENTE.getId();
			else if(tipoSequestro.equals(Constants.DECRETO_CONVALIDA_SEQUESTRO_ANTICIPATO))
				idTipoAnbscMsg = AnbscEnum.ANBSC_SEQUESTRO_ANTICIPATO.getId();
			SequestroRequest sequestroRequest = anbscService.createDecretoConvalidaRequest(idProcedimento, idAttoOr, idAttoFi, idUfficioUtente, tipoSequestro);
			long idMsg = anbscService.insertMsgDatabase(sequestroRequest, idProcedimento, idAttoFi, idTipoAnbscMsg, codUtente);
			if(idMsg > 0)
			{  
				SequestroResponse responseAnbsc = anbscService.inviaSequestro(sequestroRequest);
				if(responseAnbsc != null)
					anbscService.updateRispostaMsgDatabase(idMsg, responseAnbsc.getEsito(), responseAnbsc);
				else
					result = false;
			}
			return result;
		}
		else
			return false;
	}
	*/

	public SequestroResponse invioDecretoConvalidaSequestro(final long idProcedimento,final long idAttoFi,final Integer idUfficioUtente,final String codUtente,final String tipoSequestro)
	{
		SequestroResponse responseAnbsc = null;
		long idAttoOr = anbscService.recuperaAttoOrig(idAttoFi, idUfficioUtente); 
		
		if(idAttoOr != 0)
		{
			Long idTipoAnbscMsg = null;
			if(tipoSequestro.equals(Constants.DECRETO_CONVALIDA_SEQUESTRO_URGENTE))
				idTipoAnbscMsg = AnbscEnum.ANBSC_SEQUESTRO_URGENTE.getId();
			else if(tipoSequestro.equals(Constants.DECRETO_CONVALIDA_SEQUESTRO_ANTICIPATO))
				idTipoAnbscMsg = AnbscEnum.ANBSC_SEQUESTRO_ANTICIPATO.getId();
			SequestroRequest sequestroRequest = anbscService.createDecretoConvalidaRequest(idProcedimento, idAttoOr, idAttoFi, idUfficioUtente, tipoSequestro);
			long idMsg = anbscService.insertMsgDatabase(sequestroRequest, idProcedimento, idAttoFi, idTipoAnbscMsg, codUtente);
			if(idMsg > 0)
			{  				
				// Controllo se XML che sto inviando e' corretto 
				byte[] out = null;
				try {
					out = JAXBUtil.validationAndMarshal(sequestroRequest);
				} catch (Exception e) {
					anbscService.updateRispostaMsgDatabase(idMsg, ERRORE_VALIDAZIONE_XML, e.getCause().getLocalizedMessage());	
					return responseAnbsc; 
				}
				
				responseAnbsc = anbscService.inviaSequestro(sequestroRequest);
				if(responseAnbsc != null)
					anbscService.updateRispostaMsgDatabase(idMsg, responseAnbsc.getEsito(), responseAnbsc);
			}
			
			return responseAnbsc;

		}
		else
			return responseAnbsc;
	}
	// Mev 2020/22 Fine	

	/**
	 * URL: value = "/procedimento/atto/decisorioIGrado.htm", method = RequestMethod.POST, params = { "Salva" }
	 * METODO: public String aggiornaDecisorio(@Valid AttoForm attoForm, BindingResult result, HttpServletRequest request, ModelMap modelMap, String tipo)
	 * CONDIZIONE: if (tipo != null && tipo.equals("ConfermaInvia")) {
			if (proced.getTipoProcedimento().getId().equals(Constants.TIPOPROC_PERSONALE_E_PATRIMONIALE) || 
					proced.getTipoProcedimento().getId().equals(Constants.TIPOPROC_PATRIMONIALE))
			{
				if(proced.getTipoFascicolo().getId().equals("1") && funzione.equalsIgnoreCase("I_GRADO"))
	 * @param idProcedimento
	 * @param idAtto
	 * @param idUfficioUtente
	 * @param codUtente
	 * @return
	 */
	/* Mev 2020/22 Inizio	
	public boolean invioConfiscaIGrado(final long idProcedimento,final long idAtto,final Integer idUfficioUtente,final String codUtente)
	{
		boolean result = true;
		ConfiscaPrimoGradoRequest confiscaPrimoGradoRequest = anbscService.createConfiscaPrimoGradoRequest(idProcedimento, idAtto, idUfficioUtente);
		long idMsg = anbscService.insertMsgDatabase(confiscaPrimoGradoRequest, idProcedimento, idAtto, AnbscEnum.ANBSC_CONFISCA_I_GRADO.getId(), codUtente);
		if(idMsg > 0)
		{
			ConfiscaPrimoGradoResponse responseAnbsc = anbscService.inviaConfiscaPrimoGrado(confiscaPrimoGradoRequest);
			if(responseAnbsc != null)
				anbscService.updateRispostaMsgDatabase(idMsg, responseAnbsc.getEsito(), responseAnbsc);
			else
				result = false;	
		}
		return result;
	}
	*/
	public ConfiscaPrimoGradoResponse invioConfiscaIGrado(final long idProcedimento,final long idAtto,final Integer idUfficioUtente,final String codUtente)
	{
		ConfiscaPrimoGradoResponse responseAnbsc = null;
		boolean result = true;
		ConfiscaPrimoGradoRequest confiscaPrimoGradoRequest = anbscService.createConfiscaPrimoGradoRequest(idProcedimento, idAtto, idUfficioUtente);
		long idMsg = anbscService.insertMsgDatabase(confiscaPrimoGradoRequest, idProcedimento, idAtto, AnbscEnum.ANBSC_CONFISCA_I_GRADO.getId(), codUtente);
		if(idMsg > 0)
		{			
			// Controllo se XML che sto inviando e' corretto 
			byte[] out = null;
			try {
				out = JAXBUtil.validationAndMarshal(confiscaPrimoGradoRequest);
			} catch (Exception e) {
				anbscService.updateRispostaMsgDatabase(idMsg, ERRORE_VALIDAZIONE_XML, e.getCause().getLocalizedMessage());	
				return responseAnbsc; 
			}
			
			responseAnbsc = anbscService.inviaConfiscaPrimoGrado(confiscaPrimoGradoRequest);
			if(responseAnbsc != null)
				anbscService.updateRispostaMsgDatabase(idMsg, responseAnbsc.getEsito(), responseAnbsc);
		}
		return responseAnbsc;
	}
	
	
	/* Mev 2020/22 Fine

	/**
	 * URL: value = "/procedimento/atto/decisorioIGrado.htm", method = RequestMethod.POST, params = { "Salva" }
	 * METODO: public String aggiornaDecisorio(@Valid AttoForm attoForm, BindingResult result, HttpServletRequest request, ModelMap modelMap, String tipo)
	 * CONDIZIONE: if (tipo != null && tipo.equals("ConfermaInvia")) {
			if (proced.getTipoProcedimento().getId().equals(Constants.TIPOPROC_PERSONALE_E_PATRIMONIALE) || 
					proced.getTipoProcedimento().getId().equals(Constants.TIPOPROC_PATRIMONIALE))
			{
				if(proced.getTipoFascicolo().getId().equals("1") && funzione.equalsIgnoreCase("II_GRADO"))
	 * @param idProcedimento
	 * @param idAtto
	 * @param idUfficioUtente
	 * @param codUtente
	 * @return
	 */
	/* Mev 2020/22
	public boolean invioConfiscaIIGrado(final long idProcedimento,final long idAtto,final Integer idUfficioUtente,final String codUtente)
	{
		boolean result = true;
		ConfiscaSecondoGradoRequest confiscaSecondoGradoRequest = anbscService.createConfiscaSecondoGradoRequest(idProcedimento, idAtto, idUfficioUtente);
		long idMsg = anbscService.insertMsgDatabase(confiscaSecondoGradoRequest, idProcedimento, idAtto, AnbscEnum.ANBSC_CONFISCA_II_GRADO.getId(), codUtente);
		if(idMsg > 0)
		{
			ConfiscaSecondoGradoResponse responseAnbsc = anbscService.inviaConfiscaSecondoGrado(confiscaSecondoGradoRequest);
			if(responseAnbsc != null)
				anbscService.updateRispostaMsgDatabase(idMsg, responseAnbsc.getEsito(), responseAnbsc);
			else
				result = false;	
		}
		return result;
	}
	Mev 2020/22 */
	
	public ConfiscaSecondoGradoResponse invioConfiscaIIGrado(final long idProcedimento,final long idAtto,final Integer idUfficioUtente,final String codUtente)
	{
		ConfiscaSecondoGradoResponse responseAnbsc = null;
		ConfiscaSecondoGradoRequest confiscaSecondoGradoRequest = anbscService.createConfiscaSecondoGradoRequest(idProcedimento, idAtto, idUfficioUtente);
		long idMsg = anbscService.insertMsgDatabase(confiscaSecondoGradoRequest, idProcedimento, idAtto, AnbscEnum.ANBSC_CONFISCA_II_GRADO.getId(), codUtente);
		if(idMsg > 0)
		{			
			// Controllo se XML che sto inviando e' corretto 
			byte[] out = null;
			try {
				out = JAXBUtil.validationAndMarshal(confiscaSecondoGradoRequest);
			} catch (Exception e) {
				anbscService.updateRispostaMsgDatabase(idMsg, ERRORE_VALIDAZIONE_XML, e.getCause().getLocalizedMessage());	
				return responseAnbsc; 
			}
			
			responseAnbsc = anbscService.inviaConfiscaSecondoGrado(confiscaSecondoGradoRequest);
			if(responseAnbsc != null)
				anbscService.updateRispostaMsgDatabase(idMsg, responseAnbsc.getEsito(), responseAnbsc);	
		}
		return responseAnbsc;
	}	
	
	/**
	 * Usato in  StralcioController
	 * @param idProcedimentoOrigine
	 * @param idProcedimentoDestinazione
	 * @param idUfficioUtente
	 * @param codUtente
	 * @return
	 */
	/* Mev 2020/22
	public boolean invioStralcio(final long idProcedimentoOrigine,final long idProcedimentoDestinazione,final Integer idUfficioUtente,final String codUtente)
	{
		boolean result = true;
		if(anbscService.verificaInvioStralcioRiunione(idProcedimentoOrigine) == true)
		{
			StralcioRequest stralcioRequest = anbscService.createStralcioRequest(idProcedimentoOrigine, idProcedimentoDestinazione, idUfficioUtente);
			//verificare quale idatto inserire - per ora nella tabella dei log viene inserito solo 0L
			long idMsg = anbscService.insertMsgDatabase(stralcioRequest, idProcedimentoOrigine, 0L, AnbscEnum.ANBSC_STRALCIO.getId(), codUtente);
	
			if(idMsg > 0)
			{
				StralcioResponse responseAnbsc = anbscService.inviaStralcio(stralcioRequest);
				if(responseAnbsc != null)
					anbscService.updateRispostaMsgDatabase(idMsg, responseAnbsc.getEsito(), responseAnbsc);
				else
					result = false;
			}
		}
		else
			return false;
		return result;
	}
	Mev 2020/22 */
	
	//Mev 2020/22 inizio
	public StralcioResponse invioStralcio(final long idProcedimentoOrigine,final long idProcedimentoDestinazione,final Integer idUfficioUtente,final String codUtente)
	{
		StralcioResponse responseAnbsc = null;
		boolean result = true;
		if(anbscService.verificaInvioStralcioRiunione(idProcedimentoOrigine) == true)
		{
			StralcioRequest stralcioRequest = anbscService.createStralcioRequest(idProcedimentoOrigine, idProcedimentoDestinazione, idUfficioUtente);
			//verificare quale idatto inserire - per ora nella tabella dei log viene inserito solo 0L
			long idMsg = anbscService.insertMsgDatabase(stralcioRequest, idProcedimentoOrigine, 0L, AnbscEnum.ANBSC_STRALCIO.getId(), codUtente);
	
			if(idMsg > 0)
			{				
				// Controllo se XML che sto inviando e' corretto 
				byte[] out = null;
				try {
					out = JAXBUtil.validationAndMarshal(stralcioRequest);
				} catch (Exception e) {
					anbscService.updateRispostaMsgDatabase(idMsg, ERRORE_VALIDAZIONE_XML, e.getCause().getLocalizedMessage());	
					return responseAnbsc; 
				}
				
				responseAnbsc = anbscService.inviaStralcio(stralcioRequest);
				if(responseAnbsc != null)
					anbscService.updateRispostaMsgDatabase(idMsg, responseAnbsc.getEsito(), responseAnbsc);
			}
		}
		return responseAnbsc;		
	}
	//Mev 2020/22 fine
	
	
	/**
	 * usato in RiunioneController
	 * @param idProcedimentoRiunito
	 * @param idProcedimentoRiunente
	 * @param idUfficioUtente
	 * @param codUtente
	 * @return
	 */
	/* Mev 2020/22	
	public boolean invioRiunione(final long idProcedimentoRiunito,final long idProcedimentoRiunente,final Integer idUfficioUtente,final String codUtente)
	{
		boolean result = true;
		if(anbscService.verificaInvioStralcioRiunione(idProcedimentoRiunito) == true)
		{
			RiunioneRequest riunioneRequest = anbscService.createRiunioneRequest(idProcedimentoRiunito, idProcedimentoRiunente, idUfficioUtente);
			//verificare quale idAtto inserire
			long idMsg = anbscService.insertMsgDatabase(riunioneRequest, idProcedimentoRiunito, 0L, AnbscEnum.ANBSC_RIUNIONE.getId(), codUtente);
	
			if(idMsg > 0)
			{
				RiunioneResponse responseAnbsc = anbscService.inviaRiunione(riunioneRequest);
				if(responseAnbsc != null)
					anbscService.updateRispostaMsgDatabase(idMsg, responseAnbsc.getEsito(), responseAnbsc);
				else
					result = false;
			}
		}
		else
			return false;
		return result;
	}
	Mev 2020/22 */
	
	//Mev 2020/22 inizio
	public RiunioneResponse invioRiunione(final long idProcedimentoRiunito,final long idProcedimentoRiunente,final Integer idUfficioUtente,final String codUtente)
	{
		RiunioneResponse responseAnbsc = null;
		boolean result = true;
		if(anbscService.verificaInvioStralcioRiunione(idProcedimentoRiunito) == true)
		{
			RiunioneRequest riunioneRequest = anbscService.createRiunioneRequest(idProcedimentoRiunito, idProcedimentoRiunente, idUfficioUtente);
			//verificare quale idAtto inserire
			long idMsg = anbscService.insertMsgDatabase(riunioneRequest, idProcedimentoRiunito, 0L, AnbscEnum.ANBSC_RIUNIONE.getId(), codUtente);
	
			if(idMsg > 0)
			{				
				// Controllo se XML che sto inviando e' corretto 
				byte[] out = null;
				try {
					out = JAXBUtil.validationAndMarshal(riunioneRequest);
				} catch (Exception e) {
					anbscService.updateRispostaMsgDatabase(idMsg, ERRORE_VALIDAZIONE_XML, e.getCause().getLocalizedMessage());	
					return responseAnbsc; 
				}
				
				responseAnbsc = anbscService.inviaRiunione(riunioneRequest);
				if(responseAnbsc != null)
					anbscService.updateRispostaMsgDatabase(idMsg, responseAnbsc.getEsito(), responseAnbsc);
			}
		}
		return responseAnbsc;
	}
	//Mev 2020/22 fine	
	
	/**
	 * TODO: Sincrono
	 * @param idProcedimento
	 * @param idAtto
	 * @param idUfficioUtente
	 * @param codUtente
	 * @return
	 */
	/* Mev 2020/22		
	public boolean invioRevocaSequestro(final long idProcedimento,final long idAtto,final Integer idUfficioUtente,final String codUtente, final Long idTipoAnbscMsg)
	{
		boolean result = true;
		RevocaSequestroRequest revocaSequestroRequest = anbscService.createRevocaSequestroRequest(idProcedimento, idAtto, idUfficioUtente);
		long idMsg = anbscService.insertMsgDatabase(revocaSequestroRequest, idProcedimento, idAtto, idTipoAnbscMsg, codUtente);
		if(idMsg > 0)
		{
			RevocaSequestroResponse responseAnbsc = anbscService.inviaRevocaSequestro(revocaSequestroRequest);
			if(responseAnbsc != null)
				anbscService.updateRispostaMsgDatabase(idMsg, responseAnbsc.getEsito(), responseAnbsc);
			else
				result = false;		
		}
		return result;
	}
	Mev 2020/22 */
	//Mev 2020/22 Inizio
	public RevocaSequestroResponse invioRevocaSequestro(final long idProcedimento,final long idAtto,final Integer idUfficioUtente,final String codUtente, final Long idTipoAnbscMsg)
	{
		RevocaSequestroResponse responseAnbsc = null;
		boolean result = true;
		RevocaSequestroRequest revocaSequestroRequest = anbscService.createRevocaSequestroRequest(idProcedimento, idAtto, idUfficioUtente);
		long idMsg = anbscService.insertMsgDatabase(revocaSequestroRequest, idProcedimento, idAtto, idTipoAnbscMsg, codUtente);
		if(idMsg > 0)
		{			
			// Controllo se XML che sto inviando e' corretto 
			byte[] out = null;
			try {
				out = JAXBUtil.validationAndMarshal(revocaSequestroRequest);
			} catch (Exception e) {
				anbscService.updateRispostaMsgDatabase(idMsg, ERRORE_VALIDAZIONE_XML, e.getCause().getLocalizedMessage());	
				return responseAnbsc; 
			}
			
			responseAnbsc = anbscService.inviaRevocaSequestro(revocaSequestroRequest);
			if(responseAnbsc != null)
				anbscService.updateRispostaMsgDatabase(idMsg, responseAnbsc.getEsito(), responseAnbsc);				
		}
		return responseAnbsc;
	}
	//Mev 2020/22 fine
	
	/**
	 * TODO: Sincrono
	 * @param idProcedimento
	 * @param idAtto
	 * @param idUfficioUtente
	 * @param codUtente
	 * @return
	 */
	/* Mev 2020/22
	public boolean invioConfiscaDefinitiva(final long idProcedimento, final long idAtto, final Integer idUfficioUtente, final String codUtente, final Long idTipoAnbscMsg)
	{
		boolean result = true;
		ConfiscaDefinitivaRequest confiscaDefinitivaRequest = anbscService.createConfiscaDefinitivaRequest(idProcedimento, idAtto, idUfficioUtente, 4);
		long idMsg = anbscService.insertMsgDatabase(confiscaDefinitivaRequest, idProcedimento, idAtto, idTipoAnbscMsg, codUtente);
		if(idMsg > 0)
		{
			if(idTipoAnbscMsg.equals(AnbscEnum.ANBSC_CONFISCA_DEFINITIVA_CASSAZIONE.getId())) {
				anbscDao.updateSentenzaInviata(idAtto, idUfficioUtente);
			}
			ConfiscaDefinitivaResponse responseAnbsc = anbscService.inviaConfiscaDefinitiva(confiscaDefinitivaRequest);
			if(responseAnbsc != null)
				anbscService.updateRispostaMsgDatabase(idMsg, responseAnbsc.getEsito(), responseAnbsc);
			else
				result = false;	
		}
		return result;
	}
	Mev 2020/22 */
	
	//Mev 2020/22 Inizio
	public ConfiscaDefinitivaResponse invioConfiscaDefinitiva(final long idProcedimento, final long idAtto, final Integer idUfficioUtente, final String codUtente, final Long idTipoAnbscMsg, List<TDefPatr> listaMisPatrDaInviareANBSC, boolean sincrono)
	{
		ConfiscaDefinitivaResponse responseAnbsc = null;
		ConfiscaDefinitivaRequest confiscaDefinitivaRequest = anbscService.createConfiscaDefinitivaRequest(idProcedimento, idAtto, idUfficioUtente, 4, listaMisPatrDaInviareANBSC);
		long idMsg = anbscService.insertMsgDatabase(confiscaDefinitivaRequest, idProcedimento, idAtto, idTipoAnbscMsg, codUtente);
		if(idMsg > 0)
		{
			if(idTipoAnbscMsg.equals(AnbscEnum.ANBSC_CONFISCA_DEFINITIVA_CASSAZIONE.getId())) {
				anbscDao.updateSentenzaInviata(idAtto, idUfficioUtente);
			}
			
			if(sincrono) {
				
				// Controllo se XML che sto inviando e' corretto 
				byte[] out = null;
				try {
					out = JAXBUtil.validationAndMarshal(confiscaDefinitivaRequest);
				} catch (Exception e) {
					anbscService.updateRispostaMsgDatabase(idMsg, ERRORE_VALIDAZIONE_XML, e.getCause().getLocalizedMessage());	
					return responseAnbsc; 
				}
				
				responseAnbsc = anbscService.inviaConfiscaDefinitiva(confiscaDefinitivaRequest);
				if(responseAnbsc != null)
					anbscService.updateRispostaMsgDatabase(idMsg, responseAnbsc.getEsito(), responseAnbsc);				
			}
		}
		
		return responseAnbsc;
	}	
	//Mev 2020/22 fine
	
	/**
	 * Metodo per l'invio asincrono, schedulato in AnbscJob,
	 * delle sentenze recuperate dalla tabella Tatto con "flgAnbsc"="N"
	 */
	public void invioSentenze(){
		List<TAtto> sentenze = anbscDao.listSentenze();
		if(sentenze!=null && sentenze.size()>0)//ci sono sentenze da inviare
		{
			Iterator<TAtto> iter = sentenze.iterator();
			while(iter.hasNext() ) {
				TAtto atto = (TAtto)iter.next();
				// P.Q. MEV 2021/10 Inizio 
				// Solamente gli atti di CDA inviano il flusso verso ANBSC
				TTpTipoUfficio tipoUfficio = commonDao.getTipoUfficioByIdUfficio(atto.getTAnUfficio().getCodiKIdUfficio());
				if (tipoUfficio.getCodiKIdTipoUff() == Constants.TIPO_UFFICIO_CORTE_D_APPELLO) {
					String codUtente = atto.getDescUtente();
					long idProcedimento = anbscDao.getIdProcedimento(atto.getId().getCodiKIdAtto(), atto.getId().getCodiKIdUffOwner());
					if(idProcedimento != 0)
					{
						invioConfiscaDefinitiva(idProcedimento, atto.getId().getCodiKIdAtto(), atto.getId().getCodiKIdUffOwner(), codUtente, AnbscEnum.ANBSC_CONFISCA_DEFINITIVA_CASSAZIONE.getId(), null, true);
					}					
				}
				// P.Q. MEV 2021/10 Fine 
			}
		}
	}
	
	/**
	 * URL: value = "/flussi/associaFlussoProposta.htm", method = RequestMethod.POST, params = { "Registra" }
	 * METODO: public String associaFlussoProposta(@Valid FlussiForm flussiForm,
			BindingResult result, ModelMap modelMap, HttpServletRequest request)
	 * CONDIZIONE: if(flussiForm.getInclude().equals(Constants.SENTENZA_REVOCAZIONE_CONFISCA_MSG))
	 * 			   SENTENZA_REVOCAZIONE_CONFISCA_MSG=1519, che in realta sarebbe un decreto e non una sentenza		
	 * @param idProcedimento
	 * @param idAtto
	 * @param idUfficio
	 */
	/* Mev 2020/22 Inizio
	public void inviaRevocaConfisca(long idProcedimento, long idAtto, Integer idUfficio) {
		RevocaConfiscaRequest request = anbscService.createRevocaConfiscaRequest(idProcedimento, idAtto, idUfficio);
		long idMsg = anbscService.insertMsgDatabase(request, idProcedimento, idAtto, AnbscEnum.ANBSC_REVOCA_CONFISCA.getId(), "999");
		if(idMsg > 0)
		{  
			RevocaConfiscaResponse responseAnbsc = anbscService.inviaRevocaConfisca(request);
			if(responseAnbsc != null)
			{
				anbscService.updateRispostaMsgDatabase(idMsg, responseAnbsc.getEsito(), responseAnbsc);
			}
		}
	}
	Mev 2020/22 Fine */
	/* Mev 2020/22 Inizio */
	public RevocaConfiscaResponse inviaRevocaConfisca(long idProcedimento, long idAtto, Integer idUfficio) {
		RevocaConfiscaResponse responseAnbsc = null;
		RevocaConfiscaRequest request = anbscService.createRevocaConfiscaRequest(idProcedimento, idAtto, idUfficio);
		long idMsg = anbscService.insertMsgDatabase(request, idProcedimento, idAtto, AnbscEnum.ANBSC_REVOCA_CONFISCA.getId(), "999");
		if(idMsg > 0)
		{  
			// Controllo se XML che sto inviando e' corretto 
			byte[] out = null;
			try {
				out = JAXBUtil.validationAndMarshal(request);
			} catch (Exception e) {
				anbscService.updateRispostaMsgDatabase(idMsg, ERRORE_VALIDAZIONE_XML, e.getCause().getLocalizedMessage());	
				return responseAnbsc; 
			}			
			
			responseAnbsc = anbscService.inviaRevocaConfisca(request);
			if(responseAnbsc != null)
			{
				anbscService.updateRispostaMsgDatabase(idMsg, responseAnbsc.getEsito(), responseAnbsc);
			}
		}
		
		return responseAnbsc;
	}
	/* Mev 2020/22 Fine */
	
	// Mev 2020 22 Inizio
	
	public EsitoFromResponse getEsitoFromResponse(Esito esito) {
		String descRisposta = "";
		EsitoFromResponse esitoFromResponse = new EsitoFromResponse('0', "");
			
		if(esito.isEsito()) {
			esitoFromResponse.setEsito('T');
		} else {
			esitoFromResponse.setEsito('F');
		}
									
		if(esito.getErrordescription() != null)
		{
			descRisposta += esito.getErrordescription();
			if(esito.getErrordetails() != null)
			{
				List<String> errorDescription = esito.getErrordetails().getItem();
				Iterator<String> iter = errorDescription.iterator();
				while (iter.hasNext()) {
					descRisposta += "|" + iter.next();
				}								
			}
			
			descRisposta +=")";
			esitoFromResponse.setMessaggio(descRisposta);
			
		}		
		
		
		return esitoFromResponse;
	}
	
	public class EsitoFromResponse {
		char esito;
		String messaggio;
		
		public EsitoFromResponse(char esito, String messaggio) {
			super();
			this.esito = esito;
			this.messaggio = messaggio;
		}
		
		public char getEsito() {
			return esito;
		}
		public void setEsito(char esito) {
			this.esito = esito;
		}
		public String getMessaggio() {
			return messaggio;
		}
		public void setMessaggio(String messaggio) {
			this.messaggio = messaggio;
		}
						
	}
	// Mev 2020 22 Fine	
	
	
}
