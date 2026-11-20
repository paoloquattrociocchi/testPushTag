/**
 * 
 */
package it.giustizia.smp.utility.goback;

import it.giustizia.smp.utility.BeanUtilsBeanCustom;
import it.giustizia.smp.utility.RequestUtil;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author g.gabriele
 * 
 */
public class Breadcrumbs {

	private Logger logger = Logger.getLogger(getClass());

	private Map<String, String> parameters;
	private String method;
	private String action;
	private String params;
	

	public Breadcrumbs(String method, String action, String params) {
		super();
		this.parameters = new HashMap<String, String>();
		this.method = method;
		this.action = action;
		this.params = params;
	}

	/**
	 * Aggiunge un parametero alla request
	 * 
	 * @param key
	 * @param value
	 */
	public void addParameter(String key, String value) {
		if (parameters == null) {
			parameters = new HashMap<String, String>();
		}
		parameters.put(key, value);
	}
	/**
	 * Aggiunge ai parametri una mappa di parametri
	 * @param parameters
	 */
	public void addParameter(Map<String, String> parameters) {
		if (parameters == null) {
			parameters = new HashMap<String, String>();
		}
		this.parameters.putAll(parameters);
	}
	/**
	 * Aggiunge l'oggetto (tutti i campi dell'oggetto) come parametri
	 * @param obj
	 */
	public void addParameter(Object obj) {
		try {
			Map<String, ?> paramsMap = BeanUtilsBeanCustom.describeObj(obj);
			for (String key : paramsMap.keySet()) {
				if (StringUtils.equalsIgnoreCase("class", key)) {
					continue;
				}
				String value = ConvertUtils.convert(paramsMap.get(key));
				if (StringUtils.isNotEmpty(value)) {
					parameters.put(key, value);
				}
			}
		} catch (Exception e) {
			logger.error("Errore addParameter", e);
		}
	}
	
	public Breadcrumbs removeParameter(String key){
		this.parameters.remove(key);
		return this;
	}
	/**
	 * Restituisce tutti i parametri come queryString
	 * @return tutti i parametri come queryString
	 */
	public String getUrlEncoded() {
		return RequestUtil.buildQueryString(parameters);
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + ((params == null) ? 0 : params.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Breadcrumbs))
			return false;
		Breadcrumbs other = (Breadcrumbs) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
			return false;
		if (params == null) {
			if (other.params != null)
				return false;
		} else if (!params.equals(other.params))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Breadcrumbs [");
		if (method != null) {
			builder.append("method=");
			builder.append(method);
			builder.append(", ");
		}
		if (action != null) {
			builder.append("action=");
			builder.append(action);
			builder.append(", ");
		}
		if (params != null) {
			builder.append("params=");
			builder.append(params);
		}
		builder.append("]");
		return builder.toString();
	}

	
	

}
