package it.giustizia.smp.service.msg;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.model.TDefPatr;
import it.giustizia.smp.service.PoliziaService;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.webservice.client.polizia.DecretiDecisoriIGradoPatrimonialiRequest;
import it.giustizia.smp.webservice.client.polizia.DecretiDecisoriIIGradoPatrimonialiRequest;
import it.giustizia.smp.webservice.client.polizia.DecretiProvvisoriApplicazioneRequest;
import it.giustizia.smp.webservice.client.polizia.DecretiProvvisoriRevocaRequest;
import it.giustizia.smp.webservice.client.polizia.DefinitivitaPatrimonialiRequest;

@Service
public class PoliziaMsgService {

	@Autowired
	private PoliziaService poliziaService;
	
	/**************************************** INVIO DATI POLIZIA *******************************************/
	public boolean invioSequestroPolizia(long idProcedimento, long idAtto, Integer idUfficioUtente, String codUtente, String uffDestinatario)
	{
		boolean result = true;
		it.giustizia.smp.webservice.client.polizia.SequestroRequest sequestroRequest = poliziaService.createSequestroRequest(idProcedimento, idAtto, idUfficioUtente, new Integer(uffDestinatario));
		/*long idMsg = */poliziaService.insertPoliziaMsgDatabase(Constants.POLIZIA_SEQUESTRO, idProcedimento, idAtto, sequestroRequest, Constants.POLIZIA_STATO_SERVIZIO_INSERITO, new Integer(uffDestinatario), idUfficioUtente, codUtente);
		return result;
	}
	
	public boolean invioRevocaSequestroPolizia(long idProcedimento, long idAtto, Integer idUfficioUtente, String codUtente, String uffDestinatario)
	{
		boolean result = true;
		it.giustizia.smp.webservice.client.polizia.RevocaSequestroRequest revocaSequestroRequest = poliziaService.createRevocaSequestroRequest(idProcedimento, idAtto, idUfficioUtente, new Integer(uffDestinatario));
		/*long idMsg = */poliziaService.insertPoliziaMsgDatabase(Constants.POLIZIA_REVOCA_SEQUESTRO, idProcedimento, idAtto, revocaSequestroRequest, Constants.POLIZIA_STATO_SERVIZIO_INSERITO, new Integer(uffDestinatario), idUfficioUtente, codUtente);
		return result;
	}
	
	public boolean invioDecretoDecisorioIGradoPatrimonialiPolizia(long idProcedimento, long idAtto, Integer idUfficioUtente, String codUtente, String uffDestinatario)
	{
		boolean result = true;
		DecretiDecisoriIGradoPatrimonialiRequest decisorioIGradoPatrimonialiRequest = poliziaService.createDecretiDecisoriIGradoPatrimonialiRequest(idProcedimento, idAtto, idUfficioUtente, new Integer(uffDestinatario));
		/*long idMsg = */poliziaService.insertPoliziaMsgDatabase(Constants.POLIZIA_DECRETI_DECISORI_I_GRADO_PATR, idProcedimento, idAtto, decisorioIGradoPatrimonialiRequest, Constants.POLIZIA_STATO_SERVIZIO_INSERITO, new Integer(uffDestinatario), idUfficioUtente, codUtente);
		return result;
	}
	
	public boolean invioDecretoDecisorioIIGradoPatrimonialiPolizia(long idProcedimento, long idAtto, Integer idUfficioUtente, String codUtente, String uffDestinatario)
	{
		boolean result = true;
		DecretiDecisoriIIGradoPatrimonialiRequest decisorioIIGradoPatrimonialiRequest = poliziaService.createDecretiDecisoriIIGradoPatrimonialiRequest(idProcedimento, idAtto, idUfficioUtente, new Integer(uffDestinatario));
		/*long idMsg = */poliziaService.insertPoliziaMsgDatabase(Constants.POLIZIA_DECRETI_DECISORI_II_GRADO_PATR, idProcedimento, idAtto, decisorioIIGradoPatrimonialiRequest, Constants.POLIZIA_STATO_SERVIZIO_INSERITO, new Integer(uffDestinatario), idUfficioUtente, codUtente);
		return result;
	}
	
	//INVIO A MPS - 8.16
	public void inviaDefinitivitaPatrimonialiPolizia(ProcedimentoForm procedimentoForm, long idAtto, Integer idUfficioUtente, List<TDefPatr> misPatrPresenti, String descUtente){
		long idProcedimento = procedimentoForm.getIdProcedimento().longValue();
		List<Integer> destinatariList = poliziaService.getUffDestinatario(new Integer(procedimentoForm.getProcedente().getId()), idUfficioUtente, procedimentoForm.getIdProcedimento(), Constants.TIPOPROC_PATRIMONIALE);
//    	String descUtente = recuperaIdCodiceUtente();
    		if(misPatrPresenti!=null && !misPatrPresenti.isEmpty()){
    			HashMap<Long, Date> misureDateDefinitive = new HashMap<Long, Date>();
    			for(Iterator<TDefPatr> itr = misPatrPresenti.iterator(); itr.hasNext();){
    				TDefPatr item = itr.next();
    				misureDateDefinitive.put(item.getId().getCodiKIdBene(), item.getDataDefinitivita());
    			}
    			if(procedimentoForm.getTipoProcedimento().getId().equals(Constants.TIPOPROC_PATRIMONIALE) || procedimentoForm.getTipoProcedimento().getId().equals(Constants.TIPOPROC_PERSONALE_E_PATRIMONIALE) ){
    				for(int i=0; i<destinatariList.size(); i++)
    				{
	    				DefinitivitaPatrimonialiRequest definitivitaPatrimoniali = poliziaService.createDefinitivitaPatrimonialiRequest(idProcedimento, idAtto, idUfficioUtente.intValue(), destinatariList.get(i), misureDateDefinitive);
	    				/*long idMsg = */poliziaService.insertPoliziaMsgDatabase(Constants.POLIZIA_DEFINITIVITA_PATR, idProcedimento, idAtto, definitivitaPatrimoniali, Constants.POLIZIA_STATO_SERVIZIO_INSERITO, idUfficioUtente.longValue(), destinatariList.get(i), descUtente) ;
    				}
    			}
    		}
		}
		
		//INVIO A MPS - 8.8
	public void inviaDecretiProvvApplicazioneRevoca(ProcedimentoForm procedimento, Long idAtto, int tipoDecreto, Integer idUffOwn, String descUtente){
			long idProcedimento = procedimento.getIdProcedimento().longValue();
//			Integer idUffOwn = recuperaIdUfficioUtente();
//			String descrProcedente = procedimento.getProcedente()!=null ? procedimento.getProcedente().getDescrizione().toUpperCase().trim() : "";
//			String descUtente = recuperaIdCodiceUtente();
			
//			Long idUfficioDest = new Long(0);
//	    	if(StringUtils.contains(descrProcedente, "QUESTORE")){
//	    		String idProcedente = procedimento.getProcedente().getId();
//	    		idUfficioDest = poliziaDao.getIdUfficioDestByIdProcedente(new Integer(idProcedente).intValue());
//	    	} else if(StringUtils.contains(descrProcedente, "PROCURATORE")) {
//	    		idUfficioDest = idUffOwn.longValue();
//	    	} else{
//	    		idUfficioDest = poliziaDao.getIdUfficioDest(idUffOwn.longValue());
//	    	}

//	    	if(idUfficioDest!=null && idUfficioDest.longValue()!=0){
			if(tipoDecreto == Constants.POLIZIA_DECRETI_PROVVISORI_APPLICAZIONE){
				List<Integer> uffDestinatariList = poliziaService.getUffDestinatario(Integer.valueOf(procedimento.getProcedente().getId()), idUffOwn, procedimento.getIdProcedimento(), Constants.TIPOPROC_PERSONALE);
				for(int i=0; i<uffDestinatariList.size(); i++)
				{
					DecretiProvvisoriApplicazioneRequest decretoApplicazione = poliziaService.createDecretiProvvisoriApplicazioneRequest(idProcedimento, idAtto.longValue(), idUffOwn.intValue(), uffDestinatariList.get(i));
					/*long idMsg = */poliziaService.insertPoliziaMsgDatabase(Constants.POLIZIA_DECRETI_PROVVISORI_APPLICAZIONE, idProcedimento, idAtto.longValue(), decretoApplicazione, Constants.POLIZIA_STATO_SERVIZIO_INSERITO, idUffOwn.longValue(), uffDestinatariList.get(i), descUtente) ;
				}
			}
			if(tipoDecreto == Constants.POLIZIA_DECRETI_PROVVISORI_REVOCA){
				List<Integer> uffDestinatariList = poliziaService.getUffDestinatario(Integer.valueOf(procedimento.getProcedente().getId()), idUffOwn, procedimento.getIdProcedimento(), Constants.TIPOPROC_PERSONALE);
				for(int i=0; i<uffDestinatariList.size(); i++)
				{
					DecretiProvvisoriRevocaRequest decretoRevoca = poliziaService.createDecretiProvvisoriRevocaRequest(idProcedimento, idAtto.longValue(), idUffOwn.intValue(), uffDestinatariList.get(i));
					/*long idMsg = */poliziaService.insertPoliziaMsgDatabase(Constants.POLIZIA_DECRETI_PROVVISORI_REVOCA, idProcedimento, idAtto.longValue(), decretoRevoca, Constants.POLIZIA_STATO_SERVIZIO_INSERITO, idUffOwn.longValue(), uffDestinatariList.get(i), descUtente) ;
				}
			}
//	    	}
		}
	
	/************************************** FINE INVIO DATI POLIZIA *****************************************/
}
