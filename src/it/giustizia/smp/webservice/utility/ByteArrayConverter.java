package it.giustizia.smp.webservice.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;

public enum ByteArrayConverter implements IByteArrayConverter {
	INSTANCE;

	private static final String	CHARSET_DECODIFICA	= "ISO-8859-1";

	public final byte[] toByteArray(final Object oggetto) throws IOException {

		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		final ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(oggetto);
		oos.flush();
		oos.close();
		bos.close();
		final byte[] byteArray = bos.toByteArray();
		return byteArray;
	}

	public final Object toObject(final byte[] byteArray) throws IOException, ClassNotFoundException {

		Object oggetto = null;
		final ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
		final ObjectInputStream ois = new ObjectInputStream(bis);
		oggetto = ois.readObject();
		return oggetto;
	}

	public final String toString(final byte[] byteArray) {

		final String risultato = new String(byteArray, Charset.forName(CHARSET_DECODIFICA));
		return risultato;
	}
}
