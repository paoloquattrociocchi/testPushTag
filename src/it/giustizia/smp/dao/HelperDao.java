package it.giustizia.smp.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.object.GenericStoredProcedure;
import org.springframework.jdbc.object.StoredProcedure;

import it.giustizia.smp.utility.Utility;

public class HelperDao {

	public final static String DB_SMP = "java:/smp";   // ATTENZIONE ___ PER RILASCIO WAR RIPRISTINARE SMP!!!!!
	public final static String DB_SMP_GIU = "java:/smp_giu";

	public final static String PROC_PG_AMMINISTRAZIONE_aggiornamento_anag_uffici = "PG_AMMINISTRAZIONE.AGGIORNAMENTO_ANAG_UFFICI";
	public final static String PG_ATTO_getnumeroatto = "PG_ATTO.GETNUMEROATTO";

	public static Connection getConnection(String jndiDB) {
		Connection connection = null;
		try {
			InitialContext context = new InitialContext();
			DataSource dataSource = (DataSource) context.lookup(jndiDB);
			connection = dataSource.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public static DataSource getDataSource(String jndiDB) {
		DataSource dataSource = null;
		try {
			InitialContext context = new InitialContext();
			dataSource = (DataSource) context.lookup(jndiDB);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return dataSource;
	}

	public static Map<String, Object> eseguiProcedura(String schema, String procedura, SqlParameter[] paramType, Object paramInput, boolean isFunction) {
		StoredProcedure procedure = new GenericStoredProcedure();
		procedure.setDataSource(getDataSource(schema));

		procedure.setSql(procedura);
		procedure.setFunction(isFunction);

		procedure.setParameters(paramType);
		procedure.compile();

		Map<String, Object> result = procedure.execute(paramInput);
		return result;
	}

	public static Map<String, Object> eseguiProcedura(String schema, String procedura, SqlParameter[] paramType, Object paramInput) {
		return eseguiProcedura(schema, procedura, paramType, paramInput, false);
	}

	public static Map<String, Object> eseguiProcedura(String schema, String procedura, SqlParameter[] paramType, Map<String, Object> paramInput, boolean isFunction) {
		StoredProcedure procedure = new GenericStoredProcedure();
		procedure.setDataSource(getDataSource(schema));

		procedure.setSql(procedura);
		procedure.setFunction(isFunction);

		procedure.setParameters(paramType);
		procedure.compile();

		Map<String, Object> result = procedure.execute(paramInput);
		return result;
	}

	public static Map<String, Object> eseguiProcedura(String schema, String procedura, SqlParameter[] paramType, Map<String, Object> paramInput) {
		return eseguiProcedura(schema, procedura, paramType, paramInput, false);
	}

	public static Map<String, Object> callProcedure(String schema, String procedura, Object paramInput) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource(schema));
		String[] proc = procedura.split("\\.");
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName(proc[0]).withProcedureName(proc[1]);
		Map<String, Object> result = simpleJdbcCall.execute(paramInput);
		return result;
	}

	public static Map<String, Object> callProcedure(String schema, String procedura, Map<String, Object> paramInput) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource(schema));
		String[] proc = procedura.split("\\.");
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName(proc[0]).withProcedureName(proc[1]);
		Map<String, Object> result = simpleJdbcCall.execute(paramInput);
		return result;
	}

	public static Map<String, Object> callProcedure(String schema, String procedura, Object paramInput, String cursor, Class type) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource(schema));
		String[] proc = procedura.split("\\.");
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName(proc[0]).withProcedureName(proc[1]);
		simpleJdbcCall.returningResultSet(cursor, BeanPropertyRowMapper.newInstance(type));
		Map<String, Object> result = simpleJdbcCall.execute(paramInput);
		return result;
	}

	public static Map<String, Object> callProcedure(String schema, String procedura, Map<String, Object> paramInput, String cursor, Class type) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource(schema));
		String[] proc = procedura.split("\\.");
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName(proc[0]).withProcedureName(proc[1]);
		simpleJdbcCall.returningResultSet(cursor, BeanPropertyRowMapper.newInstance(type));
		Map<String, Object> result = simpleJdbcCall.execute(paramInput);
		return result;
	}

	public static String parseString(String inp) {
		String ret = null;
		if (inp != null) {
			String orx = inp.toString();
			String ory = orx.replace("'", "''");
			ret = "'" + ory + "'";
		}
		return ret;
	}

	public static String parseDate(Date inp) {
		String ret = null;
		if (inp != null) {
			ret = "to_date('" + Utility.convertDateToString(inp) + "','dd/mm/yyyy')";
		}
		return ret;
	}

	public static String parseCharacter(Character inp) {
		String ret = null;
		if (inp != null) {
			ret = "'" + inp.toString() + "'";
		}
		return ret;
	}

}
