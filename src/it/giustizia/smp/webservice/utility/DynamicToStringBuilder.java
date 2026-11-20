package it.giustizia.smp.webservice.utility;


import java.lang.reflect.Field;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class DynamicToStringBuilder extends ReflectionToStringBuilder {

	private final static ToStringStyle	DEFAULT_STYLE	= ToStringStyle.SHORT_PREFIX_STYLE;

	public DynamicToStringBuilder(final Object object) {

		super(object, DEFAULT_STYLE);
	}

	private DynamicToStringBuilder(final Object object, final ToStringStyle style) {

		super(object, style);
	}

	@Override
	protected boolean accept(final Field field) {

		return super.accept(field) && !isAnnotatedToBeIgnored(field);
	}

	private final static boolean isAnnotatedToBeIgnored(final Field field) {

		return field.isAnnotationPresent(ToStringIgnore.class);
	}
}
