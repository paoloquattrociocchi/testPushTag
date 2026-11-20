/**
 * 
 */
package it.giustizia.smp.utility;

import static it.giustizia.smp.utility.Constants.SottoCategoriaBeni.*;

/**
 * @author g.gabriele
 *
 */
public class SottoCategoriaUtils {

	/**
	 * Restituisce true se id della sotto categoria fa parte degli id delle sottocategorie non definite, false altrimenti
	 * @param idSottoCategoria
	 * @return true se id della sotto categoria fa parte degli id delle sottocategorie non definite, false altrimenti
	 */
	public static boolean isSottoCategoriaNonDefinita(long idSottoCategoria){
		return ComparatorUtil.isInRange(idSottoCategoria, TIPI_NON_DEFINITI_MIN_RANGE, TIPI_NON_DEFINITI_MAX_RANGE);
	}
	
	/**
	 * Restituisce true se id della sotto categoria fa parte degli id delle sottocategorie non definite, false altrimenti
	 * @param idSottoCategoria
	 * @return true se id della sotto categoria fa parte degli id delle sottocategorie non definite, false altrimenti
	 */
	public static boolean isSottoCategoriaNonDefinita(String idSottoCategoria){
		return ComparatorUtil.isInRange(idSottoCategoria, TIPI_NON_DEFINITI_MIN_RANGE, TIPI_NON_DEFINITI_MAX_RANGE);
	}

	public static boolean isPolizza(String sottocategoria) {
		return ComparatorUtil.containsValue(sottocategoria, Constants.SottoCategoriaBeni.POLIZZA_ADESIONE_FONDI_PENSIONE_S, Constants.SottoCategoriaBeni.POLIZZA_ALTRA_POLIZZA_S,
				Constants.SottoCategoriaBeni.POLIZZA_CASO_MORTE_A_VITA_INTERA_S, Constants.SottoCategoriaBeni.POLIZZA_CASO_VITA_CON_CONTROASSICURAZIONE_S, Constants.SottoCategoriaBeni.POLIZZA_CASO_VITA_S,
				Constants.SottoCategoriaBeni.POLIZZA_CONTRATTO_CAPITALIZZAZIONE_S, Constants.SottoCategoriaBeni.POLIZZA_TEMPORANEA_CASO_MORTE_S, Constants.SottoCategoriaBeni.POLIZZA_VITA_MISTA_S);
	}
}
