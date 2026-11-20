
package it.giustizia.gestorelocale.accettazione.servizi;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.giustizia.gestorelocale.accettazione.servizi package. 
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

    private final static QName _AccettaDeposito_QNAME = new QName("http://www.giustizia.it/gestorelocale/accettazione/servizi", "accettaDeposito");
    private final static QName _AssegnaDeposito_QNAME = new QName("http://www.giustizia.it/gestorelocale/accettazione/servizi", "assegnaDeposito");
    private final static QName _DownloadContenutoDepositoResponse_QNAME = new QName("http://www.giustizia.it/gestorelocale/accettazione/servizi", "downloadContenutoDepositoResponse");
    private final static QName _AccettazioneException_QNAME = new QName("http://www.giustizia.it/gestorelocale/accettazione/servizi", "AccettazioneException");
    private final static QName _ScriviEventoDepositoResponse_QNAME = new QName("http://www.giustizia.it/gestorelocale/accettazione/servizi", "scriviEventoDepositoResponse");
    private final static QName _DownloadContenutoDeposito_QNAME = new QName("http://www.giustizia.it/gestorelocale/accettazione/servizi", "downloadContenutoDeposito");
    private final static QName _AccettaDepositoResponse_QNAME = new QName("http://www.giustizia.it/gestorelocale/accettazione/servizi", "accettaDepositoResponse");
    private final static QName _ScriviEventoDeposito_QNAME = new QName("http://www.giustizia.it/gestorelocale/accettazione/servizi", "scriviEventoDeposito");
    private final static QName _RifiutaDeposito_QNAME = new QName("http://www.giustizia.it/gestorelocale/accettazione/servizi", "rifiutaDeposito");
    private final static QName _RifiutaDepositoResponse_QNAME = new QName("http://www.giustizia.it/gestorelocale/accettazione/servizi", "rifiutaDepositoResponse");
    private final static QName _AssegnaDepositoResponse_QNAME = new QName("http://www.giustizia.it/gestorelocale/accettazione/servizi", "assegnaDepositoResponse");
    private final static QName _ReinviaEsiti_QNAME = new QName("http://www.giustizia.it/gestorelocale/accettazione/servizi", "reinviaEsiti");
    private final static QName _ReinviaEsitiResponse_QNAME = new QName("http://www.giustizia.it/gestorelocale/accettazione/servizi", "reinviaEsitiResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.giustizia.gestorelocale.accettazione.servizi
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AccettazioneException }
     * 
     */
    public AccettazioneException createAccettazioneException() {
        return new AccettazioneException();
    }

    /**
     * Create an instance of {@link RifiutaDepositoResponse }
     * 
     */
    public RifiutaDepositoResponse createRifiutaDepositoResponse() {
        return new RifiutaDepositoResponse();
    }

    /**
     * Create an instance of {@link AccettaDeposito }
     * 
     */
    public AccettaDeposito createAccettaDeposito() {
        return new AccettaDeposito();
    }

    /**
     * Create an instance of {@link ScriviEventoDeposito }
     * 
     */
    public ScriviEventoDeposito createScriviEventoDeposito() {
        return new ScriviEventoDeposito();
    }

    /**
     * Create an instance of {@link AssegnaDeposito }
     * 
     */
    public AssegnaDeposito createAssegnaDeposito() {
        return new AssegnaDeposito();
    }

    /**
     * Create an instance of {@link ReinviaEsiti }
     * 
     */
    public ReinviaEsiti createReinviaEsiti() {
        return new ReinviaEsiti();
    }

    /**
     * Create an instance of {@link AccettaDepositoResponse }
     * 
     */
    public AccettaDepositoResponse createAccettaDepositoResponse() {
        return new AccettaDepositoResponse();
    }

    /**
     * Create an instance of {@link ScriviEventoDepositoResponse }
     * 
     */
    public ScriviEventoDepositoResponse createScriviEventoDepositoResponse() {
        return new ScriviEventoDepositoResponse();
    }

    /**
     * Create an instance of {@link RifiutaDeposito }
     * 
     */
    public RifiutaDeposito createRifiutaDeposito() {
        return new RifiutaDeposito();
    }

    /**
     * Create an instance of {@link AssegnaDepositoResponse }
     * 
     */
    public AssegnaDepositoResponse createAssegnaDepositoResponse() {
        return new AssegnaDepositoResponse();
    }

    /**
     * Create an instance of {@link DownloadContenutoDeposito }
     * 
     */
    public DownloadContenutoDeposito createDownloadContenutoDeposito() {
        return new DownloadContenutoDeposito();
    }

    /**
     * Create an instance of {@link DownloadContenutoDepositoResponse }
     * 
     */
    public DownloadContenutoDepositoResponse createDownloadContenutoDepositoResponse() {
        return new DownloadContenutoDepositoResponse();
    }

    /**
     * Create an instance of {@link ReinviaEsitiResponse }
     * 
     */
    public ReinviaEsitiResponse createReinviaEsitiResponse() {
        return new ReinviaEsitiResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccettaDeposito }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.giustizia.it/gestorelocale/accettazione/servizi", name = "accettaDeposito")
    public JAXBElement<AccettaDeposito> createAccettaDeposito(AccettaDeposito value) {
        return new JAXBElement<AccettaDeposito>(_AccettaDeposito_QNAME, AccettaDeposito.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AssegnaDeposito }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.giustizia.it/gestorelocale/accettazione/servizi", name = "assegnaDeposito")
    public JAXBElement<AssegnaDeposito> createAssegnaDeposito(AssegnaDeposito value) {
        return new JAXBElement<AssegnaDeposito>(_AssegnaDeposito_QNAME, AssegnaDeposito.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DownloadContenutoDepositoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.giustizia.it/gestorelocale/accettazione/servizi", name = "downloadContenutoDepositoResponse")
    public JAXBElement<DownloadContenutoDepositoResponse> createDownloadContenutoDepositoResponse(DownloadContenutoDepositoResponse value) {
        return new JAXBElement<DownloadContenutoDepositoResponse>(_DownloadContenutoDepositoResponse_QNAME, DownloadContenutoDepositoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccettazioneException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.giustizia.it/gestorelocale/accettazione/servizi", name = "AccettazioneException")
    public JAXBElement<AccettazioneException> createAccettazioneException(AccettazioneException value) {
        return new JAXBElement<AccettazioneException>(_AccettazioneException_QNAME, AccettazioneException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ScriviEventoDepositoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.giustizia.it/gestorelocale/accettazione/servizi", name = "scriviEventoDepositoResponse")
    public JAXBElement<ScriviEventoDepositoResponse> createScriviEventoDepositoResponse(ScriviEventoDepositoResponse value) {
        return new JAXBElement<ScriviEventoDepositoResponse>(_ScriviEventoDepositoResponse_QNAME, ScriviEventoDepositoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DownloadContenutoDeposito }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.giustizia.it/gestorelocale/accettazione/servizi", name = "downloadContenutoDeposito")
    public JAXBElement<DownloadContenutoDeposito> createDownloadContenutoDeposito(DownloadContenutoDeposito value) {
        return new JAXBElement<DownloadContenutoDeposito>(_DownloadContenutoDeposito_QNAME, DownloadContenutoDeposito.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccettaDepositoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.giustizia.it/gestorelocale/accettazione/servizi", name = "accettaDepositoResponse")
    public JAXBElement<AccettaDepositoResponse> createAccettaDepositoResponse(AccettaDepositoResponse value) {
        return new JAXBElement<AccettaDepositoResponse>(_AccettaDepositoResponse_QNAME, AccettaDepositoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ScriviEventoDeposito }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.giustizia.it/gestorelocale/accettazione/servizi", name = "scriviEventoDeposito")
    public JAXBElement<ScriviEventoDeposito> createScriviEventoDeposito(ScriviEventoDeposito value) {
        return new JAXBElement<ScriviEventoDeposito>(_ScriviEventoDeposito_QNAME, ScriviEventoDeposito.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RifiutaDeposito }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.giustizia.it/gestorelocale/accettazione/servizi", name = "rifiutaDeposito")
    public JAXBElement<RifiutaDeposito> createRifiutaDeposito(RifiutaDeposito value) {
        return new JAXBElement<RifiutaDeposito>(_RifiutaDeposito_QNAME, RifiutaDeposito.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RifiutaDepositoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.giustizia.it/gestorelocale/accettazione/servizi", name = "rifiutaDepositoResponse")
    public JAXBElement<RifiutaDepositoResponse> createRifiutaDepositoResponse(RifiutaDepositoResponse value) {
        return new JAXBElement<RifiutaDepositoResponse>(_RifiutaDepositoResponse_QNAME, RifiutaDepositoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AssegnaDepositoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.giustizia.it/gestorelocale/accettazione/servizi", name = "assegnaDepositoResponse")
    public JAXBElement<AssegnaDepositoResponse> createAssegnaDepositoResponse(AssegnaDepositoResponse value) {
        return new JAXBElement<AssegnaDepositoResponse>(_AssegnaDepositoResponse_QNAME, AssegnaDepositoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReinviaEsiti }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.giustizia.it/gestorelocale/accettazione/servizi", name = "reinviaEsiti")
    public JAXBElement<ReinviaEsiti> createReinviaEsiti(ReinviaEsiti value) {
        return new JAXBElement<ReinviaEsiti>(_ReinviaEsiti_QNAME, ReinviaEsiti.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReinviaEsitiResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.giustizia.it/gestorelocale/accettazione/servizi", name = "reinviaEsitiResponse")
    public JAXBElement<ReinviaEsitiResponse> createReinviaEsitiResponse(ReinviaEsitiResponse value) {
        return new JAXBElement<ReinviaEsitiResponse>(_ReinviaEsitiResponse_QNAME, ReinviaEsitiResponse.class, null, value);
    }

}
