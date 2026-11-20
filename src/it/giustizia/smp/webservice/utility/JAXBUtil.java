package it.giustizia.smp.webservice.utility;



import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import it.giustizia.smp.webservice.client.anbsc.SequestroRequest;

/**
 * 
 * @author g.gabriele
 * 
 */
public class JAXBUtil {	
		
	public static <T> T unmarshall(Class<T> klasse, File file) throws JAXBException, IOException, SQLException {
		InputStream is = FileUtils.openInputStream(file);
		try {
			return unmarshall(klasse, is);
		} catch (JAXBException e) {
			throw e;
		} finally {
			IOUtils.closeQuietly(is);
		}
	}

	public static <T> T unmarshall(Class<T> klasse, InputStream is) throws JAXBException, IOException, SQLException {

		JAXBContext jc = JAXBContext.newInstance(klasse.getPackage().getName());
		Unmarshaller um = jc.createUnmarshaller();
		JAXBElement<?> root = um.unmarshal(new StreamSource(is),klasse);
		
		return klasse.cast(root.getValue());
	}
	
	public static <T> T unmarshallPartial(Class<T> klasse, String partialXml) throws JAXBException, IOException {
		Source  source = new StreamSource(new ByteArrayInputStream(partialXml.getBytes()));
		JAXBContext jc = JAXBContext.newInstance(klasse.getPackage().getName());
		Unmarshaller um = jc.createUnmarshaller();
		Object object = um.unmarshal(source, klasse);
		if(object instanceof JAXBElement){
			object = ((JAXBElement<?>)object).getValue();
		}
		return klasse.cast(object);
		
	}

	public static byte[] marshal(Object object) throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(object.getClass());
		Marshaller marshaller = jc.createMarshaller();
		marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		marshaller.marshal(object, byteOut);

		return byteOut.toByteArray();
	}

	public static String marshalAsString(JAXBElement<?> jaxbWrapped) throws JAXBException {
		JAXBContext jaxbContext;
        StringWriter writer = new StringWriter();
        jaxbContext = JAXBContext.newInstance(jaxbWrapped.getDeclaredType());
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(jaxbWrapped , writer);
        return  writer.toString();               
	}
	
	// P.Q. 14/01/2021 Segnalazioni collaudo Mev 2020/22 Inizio
	public static byte[] validationAndMarshal(Object object) throws JAXBException, SAXException {
		JAXBContext jc = JAXBContext.newInstance(object.getClass());
		Marshaller marshaller = jc.createMarshaller();
		marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		
		// usato il metodo con il nome classe perche' andava in conflitto con la libreria dentro lib/endorsed jboss
		//SchemaFactory sf = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema", "com.sun.org.apache.xerces.internal.jaxp.validation.XMLSchemaFactory", ClassLoader.getSystemClassLoader());		
		SchemaFactory sf = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");		
		URL xsdUrl = JAXBUtil.class.getClassLoader().getResource("resources/SchemaScambioDati.xsd");
		
		Schema schema = sf.newSchema(xsdUrl);
		
		marshaller.setSchema(schema);
		marshaller.marshal(object, byteOut);
		return byteOut.toByteArray();
	}	
	// P.Q. 14/01/2021 Segnalazioni collaudo Mev 2020/22 Fine
	
	// F.C. 19/11/2021 validazione FUG
		public static byte[] validationAndMarshalFug(Object object) throws Exception {
			JAXBContext jc;
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			
				jc = JAXBContext.newInstance(object.getClass());
			
				Marshaller marshaller = jc.createMarshaller();
				marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
				
				
				
				SchemaFactory sf = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");		
				URL xsdUrl = JAXBUtil.class.getClassLoader().getResource("resources/fug/FUGtypes.xsd");
				// F.C. 18/10/2023 Ticket#20231011013 — SITMP - Invio FUG (RITM0705401)
				//inserito handler per validazione e scrittura dettaglio log
				FugValidationEventHandler fugH = new FugValidationEventHandler(); 
				Schema schema = sf.newSchema(xsdUrl);
				 marshaller.setEventHandler(fugH);
				
				marshaller.setSchema(schema);
				marshaller.marshal(object, byteOut);
				
				if (fugH.ret) {
					throw new Exception("Errore Validazione!");
				}
				return byteOut.toByteArray();
			
		}	
		// F.C. 19/11/2021 validazione FUG Fine
	
	public static byte[] printXML(Object in) {
		Logger logger = Logger.getRootLogger();
		byte[] out = null;
		String xml = null;
		if (in != null) {
		  	out = getXML(in);
		  	xml = new String(out);
		}
		logger.debug(xml);
		System.out.println(xml);
		return out;
	}

	public static byte[] getXML(Object object) {
		byte[] out = null;
		try {
			out = marshal(object);
		} catch (Exception e) {
			out = new String("Impossibile recuperare oggetto: " + e.getMessage()).getBytes();
		}
		return out;
	}
	
	public static Object convertBlobToObject(Blob blob, Class T){
		try {
			InputStream in = blob.getBinaryStream();
			JAXBContext context = JAXBContext.newInstance(T);
			Unmarshaller m = context.createUnmarshaller();
			return (Object)m.unmarshal(in);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
}
