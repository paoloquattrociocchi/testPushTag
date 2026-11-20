//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2020.07.13 alle 02:50:11 PM CEST 
//


package it.giustizia.smp.webservice.client.allineamentoFascicoliPDoc.model.general.recuperoFascicoloRequest;

import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.giustizia.smp.prova package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Password_QNAME = new QName("", "password");
    private final static QName _Idutente_QNAME = new QName("", "idutente");
    private final static QName _Idufficio_QNAME = new QName("", "idufficio");
    private final static QName _Codicefascicolo_QNAME = new QName("", "codicefascicolo");
    private final static QName _Codiceapplicazione_QNAME = new QName("", "codiceapplicazione");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.giustizia.smp.prova
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Autenticazione }
     * 
     */
    public Autenticazione createAutenticazione() {
        return new Autenticazione();
    }

    /**
     * Create an instance of {@link Firma }
     * 
     */
    public Firma createFirma() {
        return new Firma();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "password")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createPassword(String value) {
        return new JAXBElement<String>(_Password_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "idutente")
    public JAXBElement<BigInteger> createIdutente(BigInteger value) {
        return new JAXBElement<BigInteger>(_Idutente_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "idufficio")
    public JAXBElement<BigInteger> createIdufficio(BigInteger value) {
        return new JAXBElement<BigInteger>(_Idufficio_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "codicefascicolo")
    public JAXBElement<String> createCodicefascicolo(String value) {
        return new JAXBElement<String>(_Codicefascicolo_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "codiceapplicazione")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createCodiceapplicazione(String value) {
        return new JAXBElement<String>(_Codiceapplicazione_QNAME, String.class, null, value);
    }

}
