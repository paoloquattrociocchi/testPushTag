package it.giustizia.smp.utility;

/**
 * Un tipico e comodo utilizzo è quello di fare 
 * l' import static di questa interfaccia nella classe in cui serve:
 * <pre>
 * import static it.giustizia.smp.utility.StaticQueries.*
 * </pre>
 * 
 * @author Francesco.Raccuglia
 *
 */
public interface StaticQueries {

	String QUERY_SEQUESTRO_REGISTRATO = "SELECT TATTO.CODI_K_ID_ATTO FROM T_ATTO TATTO INNER JOIN T_REL_ATTO_STATO_ATTO TRASA " +
			"ON TRASA.CODI_K_ID_ATTO = TATTO.CODI_K_ID_ATTO AND TRASA.CODI_K_ID_UFF_OWNER = TATTO.CODI_K_ID_UFF_OWNER " +
			"INNER JOIN T_REL_BENE_ATTO TRBA ON TRBA.CODI_K_ID_ATTO = TATTO.CODI_K_ID_ATTO " +
			"AND TRBA.CODI_K_ID_UFF_OWNER = TATTO.CODI_K_ID_UFF_OWNER WHERE TRASA.CODI_K_ID_STATO_ATTO = 3 " +
			"AND TRBA.CODI_K_ID_BENE = :idBene AND TRBA.CODI_K_ID_PROC = :idProc AND TATTO.CODI_FK_ID_CAT_ATTO " +
			"IN (300, 301,302) AND TRBA.CODI_K_ID_UFF_OWNER = :idUffOwner";
	
}
