package it.giustizia.smp.webservice.utility;

import java.io.IOException;

public interface IByteArrayConverter {

	byte[] toByteArray(Object oggetto) throws IOException;

	Object toObject(byte[] byteArray) throws IOException, ClassNotFoundException;

	String toString(byte[] byteArray);
}
