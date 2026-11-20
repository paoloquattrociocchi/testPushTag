//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2020.07.13 alle 02:48:55 PM CEST 
//


package it.giustizia.smp.webservice.client.allineamentoFascicoliPDoc.model.general.recuperoFascicoloResponse;

import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
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

    private final static QName _Messagebody_QNAME = new QName("", "messagebody");
    private final static QName _Esito_QNAME = new QName("", "esito");
    private final static QName _Fascicolo_QNAME = new QName("", "fascicolo");
    private final static QName _Codicemessaggio_QNAME = new QName("", "codicemessaggio");
    private final static QName _Ufficioabilitato_QNAME = new QName("", "ufficioabilitato");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.giustizia.smp.prova
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Security }
     * 
     */
    public Security createSecurity() {
        return new Security();
    }

    /**
     * Create an instance of {@link Messaggio }
     * 
     */
    public Messaggio createMessaggio() {
        return new Messaggio();
    }

    /**
     * Create an instance of {@link Risposta }
     * 
     */
    public Risposta createRisposta() {
        return new Risposta();
    }

    /**
     * Create an instance of {@link Fascicolo }
     * 
     */
    public Fascicolo createFascicolo() {
        return new Fascicolo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "messagebody")
    public JAXBElement<String> createMessagebody(String value) {
        return new JAXBElement<String>(_Messagebody_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "esito")
    public JAXBElement<Boolean> createEsito(Boolean value) {
        return new JAXBElement<Boolean>(_Esito_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Fascicolo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "fascicolo")
    public JAXBElement<Fascicolo> createFascicolo(Fascicolo value) {
        return new JAXBElement<Fascicolo>(_Fascicolo_QNAME, Fascicolo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "codicemessaggio")
    public JAXBElement<BigInteger> createCodicemessaggio(BigInteger value) {
        return new JAXBElement<BigInteger>(_Codicemessaggio_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ufficioabilitato")
    public JAXBElement<BigInteger> createUfficioabilitato(BigInteger value) {
        return new JAXBElement<BigInteger>(_Ufficioabilitato_QNAME, BigInteger.class, null, value);
    }

}
