package it.giustizia.smp.webservice.utility;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marker annotation to exclude fields from dynamically generated toString() method
 * 
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ToStringIgnore {
	// Marker annotation to exclude fields from dynamically generated toString() method
}
