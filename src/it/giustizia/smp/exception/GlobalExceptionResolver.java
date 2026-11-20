package it.giustizia.smp.exception;

import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

@Controller
public class GlobalExceptionResolver extends SimpleMappingExceptionResolver {

	private Logger				logger	= Logger.getLogger(GlobalExceptionResolver.class);
	private Collection<String>	ignoredExceptions;

	public void setIgnoreExceptions(final Collection<String> ignoredExceptions) {

		this.ignoredExceptions = ignoredExceptions;
	}

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception eccezione) {

		if (ignoredExceptions != null && !ignoredExceptions.isEmpty()) {
			for (String ignoredException : ignoredExceptions) {
				try {
					Class<?> classeIgnorata = Class.forName(ignoredException.trim());
					if (classeIgnorata.isInstance(eccezione)) {
						return null;
					}
				} catch (ClassNotFoundException ignored) {
					logger.warn("La stringa [" + ignoredException + "] non rappresenta un nome di classe valido, non sarà quindi associato ad alcuna eccezione lanciata. La causa più probabile è un errore di digitazione nel nome della classe o un errore di configurazione.");
				}
			}
		}
		logger.debug("ECCEZIONE!", eccezione);
		
		if(eccezione.getCause() != null && eccezione.getCause() instanceof SQLException) {
			return super.doResolveException(request, response, handler, new FilteredSQLException(eccezione.getCause()));
		} else return super.doResolveException(request, response, handler, eccezione);
	}
}
