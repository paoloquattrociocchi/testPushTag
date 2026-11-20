package it.giustizia.smp.webservice.utility;

public interface IXmlUnmarshaller {

	<T> T unmarshall(final String xmlString, final Class<T> classe);
}
