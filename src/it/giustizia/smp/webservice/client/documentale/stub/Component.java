package it.giustizia.smp.webservice.client.documentale.stub;

import it.giustizia.smp.webservice.utility.DynamicToStringBuilder;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public abstract class Component implements Serializable {

	private static final long	serialVersionUID	= 1L;

	@Override
	public String toString() {

		final ReflectionToStringBuilder builder = new DynamicToStringBuilder(this);
		return builder.toString();
	}
}