package it.giustizia.smp.utility;

import it.giustizia.smp.model.Error;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

public class ExceptionResolver extends SimpleMappingExceptionResolver {

    private Logger logger = Logger.getLogger(ExceptionResolver.class);

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        logger.error("", ex);
        Error error = new Error();
        error.setMessage(ex.getMessage());
        return new ModelAndView("defaultError", "error", error);
    }
}
