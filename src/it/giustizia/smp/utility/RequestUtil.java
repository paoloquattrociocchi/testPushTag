/**
 * 
 */
package it.giustizia.smp.utility;

import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.ProfiloForm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.web.util.UrlPathHelper;

/**
 * Classe di utilità per la gestione della request e il recupero di oggetti da essa
 * @author g.gabriele
 *
 */
public class RequestUtil {
	/**
	 * Restituisce il path della request mappato come path nei controller
	 * @param request
	 * @return il path della request mappato come path nei controller
	 */
	public static String getPathRequest(HttpServletRequest request){
		UrlPathHelper urlPathHelper = new UrlPathHelper();
		String pathForRequest = urlPathHelper.getLookupPathForRequest(request);
		return pathForRequest;
	}
	
	/**
	 * Converte tutti i parametri in query string
	 * @param params
	 * @return tutti i parametri in query string
	 */
	public static String buildQueryString(Map<String, String> params){
		
		if(params == null){
			return "";
		}

		Set<String> keySet = params.keySet();
		
		List<String> listOfParams = new ArrayList<String>(keySet.size());
		
		for (String key : keySet) {
		
			String value = params.get(key);

			if (StringUtils.isNotEmpty(value)) {
				listOfParams.add(key + "=" + params.get(key));
			}
		}
		
		return StringUtils.join(listOfParams, "&amp;");
	}
	
	
	/**
	 * Recupera i parametri dalla request e restituisce un map di string string
	 * 
	 * @return i parametri dalla request e restituisce un map di string string
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> recuperaParametri(HttpServletRequest request) {
		Map<String, String> params = new HashMap<String, String>();

		Map<String, String[]> parametri = request.getParameterMap();

		for (String key : parametri.keySet()) {
			String attrValue = ConvertUtils.convert(request.getParameterValues(key));
			if (attrValue != null) {
				params.put(key, attrValue);
			}
		}

		return params;
	}
	/**
	 * Restituisce il profilo associato alla request
	 * @param request
	 * @return il profilo associato alla request
	 */
	public static ProfiloForm recuperaProfilo(HttpServletRequest request){
		HttpSession session = request.getSession();
		return (ProfiloForm) session.getAttribute(Constants.PROFILO);
	}
	
	/**
	 * Restituisce il tipo procedimento dalla request se esiste altrimenti restituisce 0
	 * @param request
	 * @return  il tipo procedimento dalla request se esiste altrimenti restituisce 0
	 */
	public static long recuperaIdTipoProcedimento(HttpServletRequest request){
		HttpSession session = request.getSession();
		String tipoProcString = (String) session.getAttribute(Constants.ID_TIPO_PROCEDIMENTO);
		return NumberUtils.toLong(tipoProcString, 0);		
	}
	
	/**
	 * Restituisce il procedimento dalla request null se non esiste
	 * @param request
	 * @return il procedimento dalla request null se non esiste
	 */
	public static ProcedimentoForm recuperaProcedimento(HttpServletRequest request){
		HttpSession session = request.getSession();
		ProcedimentoForm procedimentoForm = (ProcedimentoForm) session.getAttribute(Constants.ID_PROCEDIMENTO);
		return procedimentoForm;
	}
	
	/**
	 * Restituisce l'id del procedimento dalla request 0 se non esiste
	 * @param request
	 * @return l'id del procedimento dalla request 0 se non esiste
	 */
	public static long recuperaIdProcedimento(HttpServletRequest request){
		ProcedimentoForm procedimentoForm = recuperaProcedimento(request);
		if(procedimentoForm == null){
			return 0;
		}
		return procedimentoForm.getIdProcedimento();
	}

	
	/**
	 * Restituisce lo stato del procedimento dalla request 0 se non esiste
	 * @param request
	 * @return lo stato del procedimento dalla request 0 se non esiste
	 */
	public static int recuperaIdStatoProcedimento(HttpServletRequest request){
		ProcedimentoForm procedimentoForm = recuperaProcedimento(request);
		if(procedimentoForm == null || procedimentoForm.getStatoProcedimento() == null){
			return 0;
		}
		
		return NumberUtils.toInt(procedimentoForm.getStatoProcedimento().getId(), 0);
	}
	
	/**
	 * Restituisce l'atto dalla request null se non esiste
	 * @param request
	 * @return l'atto dalla request null se non esiste
	 */
	public static AttoForm recuperaAtto(HttpServletRequest request){
		HttpSession session = request.getSession();
		AttoForm attoForm = (AttoForm) session.getAttribute(Constants.ATTO);
		return attoForm;
	}
	
	/**
	 * Restituisce l'id del tipo atto dalla request 0 se non esiste
	 * @param request
	 * @return l'id del tipo atto dalla request 0 se non esiste
	 */
	public static int recuperaIdTipoAtto(HttpServletRequest request){
		AttoForm attoForm = recuperaAtto(request);
		if(attoForm == null || attoForm.getTipoAtto() == null){
			return 0;
		}
		return NumberUtils.toInt(attoForm.getTipoAtto().getId(), 0);
	}
	
	/**
	 * Restituisce l'id della categoria dell'atto dalla request 0 se non esiste
	 * @param request
	 * @return l'id della categoria dell'atto dalla request 0 se non esiste
	 */
	public static int recuperaIdCategoriaAtto(HttpServletRequest request){
		AttoForm attoForm = recuperaAtto(request);
		if(attoForm == null || attoForm.getCategoriaAtto() == null){
			return 0;
		}
		return NumberUtils.toInt(attoForm.getCategoriaAtto().getId(), 0);
	}
	
	/**
	 * Restituisce dalla request l'id dell'ufficio dell'utente null se non esiste
	 * @param request
	 * @return dalla request l'id dell'ufficio dell'utente null se non esiste
	 */
	public static Integer recuperaIdUfficioUtente(HttpServletRequest request){
		HttpSession session = request.getSession();
		if(session.getAttribute(Constants.ID_UFFICIO_UTENTE) == null){
			return null;
		}
		return (Integer) session.getAttribute(Constants.ID_UFFICIO_UTENTE);
	}
	/**
	 * Restituisce dalla request l'id del tipo ufficio, null se non esiste
	 * @param request
	 * @return dalla request l'id del tipo ufficio, null se non esiste
	 */
	public static Integer recuperaIdTipoUfficio(HttpServletRequest request){
		HttpSession session = request.getSession();
		if(session.getAttribute(Constants.ID_TIPO_UFFICIO) == null){
			return null;
		}
		return (Integer) session.getAttribute(Constants.ID_TIPO_UFFICIO);
	}
	
	/**
	 * Restituisce l'id codice utente dalla request 0 se non esiste
	 * @param request
	 * @return l'id codice utente dalla request 0 se non esiste
	 */
	public static String recuperaIdCodiceUtente(HttpServletRequest request){
		HttpSession session = request.getSession();
		if(session.getAttribute(Constants.ID_CODICE_UTENTE)==null){
			return "";
		}
		
		return (String)session.getAttribute(Constants.ID_CODICE_UTENTE);
	}
	
	/**
	 * Restituisce lo stato del procedimento dalla request 0 se non esiste
	 * @param request
	 * @return lo stato del procedimento dalla request 0 se non esiste
	 */
	public static int recuperaIdStatoAtto(HttpServletRequest request){
		AttoForm attoForm = recuperaAtto(request);
		if(attoForm == null || attoForm.getStatoAtto() == null){
			return 0;
		}
		
		return NumberUtils.toInt(attoForm.getStatoAtto().getId(), 0);
	}
	
	/**
	 * Restituisce l' id dell'atto dalla request 0 se non esiste
	 * @param request
	 * @return il codice atto dalla request 0 se non esiste
	 */
	public static long recuperaIdAtto(HttpServletRequest request){
		AttoForm attoForm = recuperaAtto(request);
		if(attoForm == null || attoForm.getCodAtto() > 0){
			return 0; 
		}
		
		return attoForm.getCodAtto();
	}
	
	
	/**
	 * Restituisce l' id del fascicolo dalla request 0 se non esiste
	 * @param request
	 */
	public static int recuperaIdFascicolo(HttpServletRequest request) {
		Object tipoFascicolo = request.getSession().getAttribute(Constants.TIPO_FASCICOLO);
		int idTipoFascicolo =0;
		if(tipoFascicolo!=null){
			idTipoFascicolo = Integer.parseInt(tipoFascicolo.toString());
		}
		return idTipoFascicolo;
	}

	/***
	 * Restituisce l'id del procedimento dalla sessione
	 * @param session
	 * @return
	 */
	public static long recuperaIdProcedimento(HttpSession session) {
		ProcedimentoForm procedimentoForm = (ProcedimentoForm) session.getAttribute(Constants.ID_PROCEDIMENTO);
		return procedimentoForm.getIdProcedimento();
	}
	
	/**
	 * Restituisce dalla request l'id dell'ufficio dell'utente null se non esiste
	 * @return dalla request l'id dell'ufficio dell'utente null se non esiste
	 */
	public static Integer recuperaIdUfficioUtente(HttpSession session){
		if(session.getAttribute(Constants.ID_UFFICIO_UTENTE) == null){
			return null;
		}
		return (Integer) session.getAttribute(Constants.ID_UFFICIO_UTENTE);
	}
	
}
