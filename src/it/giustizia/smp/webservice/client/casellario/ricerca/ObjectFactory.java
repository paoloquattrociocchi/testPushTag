//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.10.14 alle 10:17:21 AM CEST 
//


package it.giustizia.smp.webservice.client.casellario.ricerca;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.giustizia.smp.webservice.client.casellario.ricerca package. 
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

    private final static QName _ArrayOmonimi_QNAME = new QName("http://it/mig/sippi/service/richiestaCertificato/type", "ArrayOmonimi");
    private final static QName _CERTIFICATOPENALE_QNAME = new QName("http://it/mig/sippi/service/richiestaCertificato/type", "CERTIFICATO_PENALE");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.giustizia.smp.webservice.client.casellario.ricerca
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DATIRISPOSTACERTIFICATO }
     * 
     */
    public DATIRISPOSTACERTIFICATO createDATIRISPOSTACERTIFICATO() {
        return new DATIRISPOSTACERTIFICATO();
    }

    /**
     * Create an instance of {@link SERVIZIOCERTIFICATIVO }
     * 
     */
    public SERVIZIOCERTIFICATIVO createSERVIZIOCERTIFICATIVO() {
        return new SERVIZIOCERTIFICATIVO();
    }

    /**
     * Create an instance of {@link DATIRICHIESTACERTIFICATO }
     * 
     */
    public DATIRICHIESTACERTIFICATO createDATIRICHIESTACERTIFICATO() {
        return new DATIRICHIESTACERTIFICATO();
    }

    /**
     * Create an instance of {@link DATIUTENTE }
     * 
     */
    public DATIUTENTE createDATIUTENTE() {
        return new DATIUTENTE();
    }

    /**
     * Create an instance of {@link DATIUFFICIO }
     * 
     */
    public DATIUFFICIO createDATIUFFICIO() {
        return new DATIUFFICIO();
    }

    /**
     * Create an instance of {@link ANAGRAFICA }
     * 
     */
    public ANAGRAFICA createANAGRAFICA() {
        return new ANAGRAFICA();
    }

    /**
     * Create an instance of {@link DATIANAGRAFICI }
     * 
     */
    public DATIANAGRAFICI createDATIANAGRAFICI() {
        return new DATIANAGRAFICI();
    }

    /**
     * Create an instance of {@link DATA }
     * 
     */
    public DATA createDATA() {
        return new DATA();
    }

    /**
     * Create an instance of {@link DATICERTIFICATO }
     * 
     */
    public DATICERTIFICATO createDATICERTIFICATO() {
        return new DATICERTIFICATO();
    }

    /**
     * Create an instance of {@link ArrayOmonimi }
     * 
     */
    public ArrayOmonimi createArrayOmonimi() {
        return new ArrayOmonimi();
    }

    /**
     * Create an instance of {@link DATIRISPOSTACERTIFICATO.ArrayEsito }
     * 
     */
    public DATIRISPOSTACERTIFICATO.ArrayEsito createDATIRISPOSTACERTIFICATOArrayEsito() {
        return new DATIRISPOSTACERTIFICATO.ArrayEsito();
    }

    /**
     * Create an instance of {@link OMONIMO }
     * 
     */
    public OMONIMO createOMONIMO() {
        return new OMONIMO();
    }

    /**
     * Create an instance of {@link DATIAUTENTICAZIONE }
     * 
     */
    public DATIAUTENTICAZIONE createDATIAUTENTICAZIONE() {
        return new DATIAUTENTICAZIONE();
    }

    /**
     * Create an instance of {@link ESITO }
     * 
     */
    public ESITO createESITO() {
        return new ESITO();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOmonimi }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it/mig/sippi/service/richiestaCertificato/type", name = "ArrayOmonimi")
    public JAXBElement<ArrayOmonimi> createArrayOmonimi(ArrayOmonimi value) {
        return new JAXBElement<ArrayOmonimi>(_ArrayOmonimi_QNAME, ArrayOmonimi.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it/mig/sippi/service/richiestaCertificato/type", name = "CERTIFICATO_PENALE")
    public JAXBElement<byte[]> createCERTIFICATOPENALE(byte[] value) {
        return new JAXBElement<byte[]>(_CERTIFICATOPENALE_QNAME, byte[].class, null, ((byte[]) value));
    }

}
