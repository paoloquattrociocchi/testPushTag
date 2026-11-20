/**
 * 
 */
package it.giustizia.smp.utility;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;

/**
 * @author g.gabriele
 * 
 */
public class BeanUtilsBeanCustom {
	private static final BeanUtilsBean BEAN_UTILS_BEAN = BeanUtilsBean.getInstance();
	
	/**
	 * 
	 * @param bean
	 * @return
	 */
	public static Map<String, ?> describeObj(Object bean) {
		if (bean == null) {
			return (new java.util.HashMap<String, String>());
		}

		Map<String, String> description = new HashMap<String, String>();
		if (bean instanceof DynaBean) {
			DynaProperty[] descriptors = ((DynaBean) bean).getDynaClass().getDynaProperties();
			for (int i = 0; i < descriptors.length; i++) {
				String name = descriptors[i].getName();
				try {
					description.put(name, BEAN_UTILS_BEAN.getProperty(bean, name));
				} catch (Exception e) {

				}
			}
		} else {
			PropertyDescriptor[] descriptors = BEAN_UTILS_BEAN.getPropertyUtils().getPropertyDescriptors(bean);
			for (int i = 0; i < descriptors.length; i++) {
				String name = descriptors[i].getName();
				try {
					description.put(name, BEAN_UTILS_BEAN.getProperty(bean, name));
				} catch (Exception e) {

				}
			}
		}
		return description;
	}
}
