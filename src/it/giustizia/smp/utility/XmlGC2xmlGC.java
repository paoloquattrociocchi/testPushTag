package it.giustizia.smp.utility;

import javax.xml.datatype.XMLGregorianCalendar;

import org.dozer.DozerConverter;

public class XmlGC2xmlGC extends
	DozerConverter<XMLGregorianCalendar, XMLGregorianCalendar> {
	public XmlGC2xmlGC() {
	super(XMLGregorianCalendar.class, XMLGregorianCalendar.class);
	}
	@Override
	public XMLGregorianCalendar convertFrom(XMLGregorianCalendar src,
	XMLGregorianCalendar dest) {
	return src;
	}
	@Override
	public XMLGregorianCalendar convertTo(XMLGregorianCalendar src,
	XMLGregorianCalendar dest) {
	return dest;
	}
}