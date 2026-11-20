package it.giustizia.smp.webservice.utility;

import java.io.ByteArrayInputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public enum XmlUnmarshaller implements IXmlUnmarshaller {
	INSTANCE;

	private static JAXBContext	CONTEXT;

	public <T> T unmarshall(final String xmlString, final Class<T> classe) {

		final ByteArrayInputStream bis = new ByteArrayInputStream(xmlString.getBytes());
		try {
			CONTEXT = JAXBContext.newInstance(classe);
			final Unmarshaller unmarshaller = CONTEXT.createUnmarshaller();
			final Object risultato = unmarshaller.unmarshal(bis);
			return classe.cast(risultato);
		} catch (final JAXBException eccezione) {
			eccezione.printStackTrace();
		}
		return null;
	}
}
