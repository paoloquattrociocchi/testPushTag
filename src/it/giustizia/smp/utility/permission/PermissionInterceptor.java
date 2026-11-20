/**
 * 
 */
package it.giustizia.smp.utility.permission;

import it.giustizia.smp.model.TAnPagina;
import it.giustizia.smp.service.UtilService;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.RequestUtil;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author g.gabriele
 * 
 */
public class PermissionInterceptor extends HandlerInterceptorAdapter {
	/**
	 * The logger
	 */
	private Logger logger = Logger.getLogger(getClass());

	private static final String separatorChar = ";";
	
	private List<String> excludeUrl = Arrays.asList("/", "/login.htm","/index.html");

	@Autowired
	private UtilService service;

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

		try {
			String pathForRequest = RequestUtil.getPathRequest(request);
			
			if(excludeUrl.contains(pathForRequest)){
				return;
			}

			if (!FilenameUtils.wildcardMatch(pathForRequest, "*.htm") || modelAndView == null) {
				return;
			}

			Map<String, String> paramaters = RequestUtil.recuperaParametri(request);

			if (StringUtils.equalsIgnoreCase("POST", request.getMethod()) && paramaters.isEmpty()) {
				return;
			}

			List<String> disabilitare = recuperaElementiDaDisabilitare(request, paramaters);

			modelAndView.addObject("mappingUrl", RequestUtil.getPathRequest(request));
			modelAndView.addObject(Constants.NAME_DA_DISABILITARE, disabilitare);

			if (logger.isDebugEnabled()) {
				logger.debug(String.format("postHandle %s", pathForRequest));
				logger.debug(String.format("Campi da disabilitare %s", disabilitare));
				// logger.debug(String.format("modelAndView %s", modelAndView));
			}

		} catch (Exception e) {
			logger.error("Errore PermissionInterceptor", e);
		}
		super.postHandle(request, response, handler, modelAndView);
	}

	private List<String> recuperaElementiDaDisabilitare(HttpServletRequest request, Map<String, String> paramaters) {
		List<String> result = new LinkedList<String>();

		List<TAnPagina> pagine = service.findPagine(request);

		if (pagine == null || pagine.isEmpty()) {
			return result;
		}

		for (TAnPagina pagina : pagine) {
			if (StringUtils.isNotEmpty(pagina.getDescParamUri()) && paramaters.containsKey(pagina.getDescParamUri())) {
				List<String> ris = splitNames(pagina.getDescNames());
				if(ris!=null){
					result.addAll(ris);
				}
				break;
			}

			if (StringUtils.isEmpty(pagina.getDescParamUri()) && paramaters.isEmpty()) {
				List<String> ris = splitNames(pagina.getDescNames());
				if(ris!=null){
					result.addAll(ris);
				}
				break;
			}

			if (StringUtils.isEmpty(pagina.getDescParamUri())) {
				List<String> ris = splitNames(pagina.getDescNames());
				if(ris!=null){
					result.addAll(ris);
				}
				break;
			}
		}

		return result;
	}

	/**
	 * Divide i names in una lista di name
	 * 
	 * @param names
	 * @return i names in una lista di name
	 */
	private List<String> splitNames(String names) {
		String[] array = StringUtils.split(names, separatorChar);
		if(array!=null){
			return Arrays.asList(array);
		}else{
			return null;
		}
	}

}
