package it.giustizia.smp.webservice.utility;

import org.apache.xml.security.exceptions.Base64DecodingException;
import org.apache.xml.security.utils.Base64;

public enum Base64Encoder implements IBase64Encoder {
	INSTANCE;

	private Base64Encoder() {

		org.apache.xml.security.Init.init();
	}

	public final String decode(final byte[] array) throws Base64DecodingException {

		return decode(ByteArrayConverter.INSTANCE.toString(array));
	}

	public final String decode(final String input) throws Base64DecodingException {

		final byte[] inputAsByteArray = Base64.decode(input);
		return ByteArrayConverter.INSTANCE.toString(inputAsByteArray);
	}

	public String encode(final byte[] array) {

		return Base64.encode(array);
	}

	public final String encode(final String input) {

		return Base64.encode(input.getBytes());
	}
}
