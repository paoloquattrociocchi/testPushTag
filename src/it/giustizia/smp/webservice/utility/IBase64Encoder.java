package it.giustizia.smp.webservice.utility;

import org.apache.xml.security.exceptions.Base64DecodingException;

public interface IBase64Encoder {

	String decode(final byte[] array) throws Base64DecodingException;

	String decode(final String input) throws Base64DecodingException;

	String encode(final byte[] array);

	String encode(final String input);
}
