package it.giustizia.smp.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class DateUtils {

	public static final String FORMAT_STANDARD = "dd/MM/yyyy";
	
	public static XMLGregorianCalendar convert(Date in) {
		XMLGregorianCalendar out = null;
		if (in != null) {
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			gregorianCalendar.setTime(in);
			try {
				DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
		    out = datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
			} catch (DatatypeConfigurationException e) {
				System.out.println("Errore in DateUtils.convert");
				e.printStackTrace();
			}
		}
		return out;
	}

	public static Date convert(XMLGregorianCalendar in) {
		Date out = null;
		if (in != null) {
			out = in.toGregorianCalendar().getTime();
		}
		return out;
	}

	public static boolean isMaggiorenne(Date dataNascita) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.YEAR, -18);
		return !dataNascita.after(c.getTime());
	}

	/**
	 * Riscritto il metodo della classe Date e fatto nullsafe
	 * @param data1
	 * @param data2
	 * @return
	 */
	public static boolean isBefore(Date data1, Date data2) {
		if (data1 == null || data2 == null) {
			return false;
		} else {
			return data1.before(data2);
		}
	}
	
	public static String formatDate(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	public static Date parseDate(String date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
	
}
