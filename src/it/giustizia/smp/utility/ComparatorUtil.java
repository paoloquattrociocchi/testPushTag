/**
 * 
 */
package it.giustizia.smp.utility;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 * @author g.gabriele
 * 
 */
public class ComparatorUtil {
	/**
	 * Restituisce true se id è uguale ad almeno uno degli stati passati, false altrimenti
	 * @param id
	 * @param stati
	 * @return true se id è uguale ad almeno uno degli stati passati, false altrimenti
	 */
	public static boolean containsValue(long id, String... stati) {
		return containsValue(String.valueOf(id), stati);
	}
	
	public static boolean containsValue(String id, long... stati) {
		return containsValue(NumberUtils.toLong(id, 0), stati);
	}
	/**
	 * Restituisce true se id è uguale ad almeno uno degli stati passati, false altrimenti
	 * @param id
	 * @param stati
	 * @return true se id è uguale ad almeno uno degli stati passati, false altrimenti
	 */
	public static boolean containsValue(String id, String... stati) {
		if (id == null) {
			return false;
		}
		
		for(String valore:stati){
			if(StringUtils.equals(id, valore)){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Restituisce true se id è uguale ad almeno uno degli stati passati, false altrimenti
	 * @param id
	 * @param stati
	 * @return true se id è uguale ad almeno uno degli stati passati, false altrimenti
	 */
	public static boolean containsValue(long id, long... stati) {
		
		for(long valore:stati){
			if(id == valore){
				return true;
			}
		}
		
		return false;
	}
	/**
	 * Restituisce true se il valore della variabile id è contenuta nel range minRange e maxRange (estremi compresi),false altrimenti
	 * @param id
	 * @param minRange
	 * @param maxRange
	 * @return true se il valore della variabile id è contenuta nel range minRange e maxRange (estremi compresi),false altrimenti
	 */
	public static boolean isInRange(long id,long minRange,long maxRange){
		return id>=minRange && id<=maxRange;
	}
	
	/**
	 * Restituisce true se il valore della variabile id è contenuta nel range minRange e maxRange (estremi compresi),false altrimenti
	 * @param id
	 * @param minRange
	 * @param maxRange
	 * @return true se il valore della variabile id è contenuta nel range minRange e maxRange (estremi compresi),false altrimenti
	 */
	public static boolean isInRange(String id,long minRange,long maxRange){
		return isInRange(NumberUtils.toLong(id, -1000), minRange, maxRange);
	}
	
}
