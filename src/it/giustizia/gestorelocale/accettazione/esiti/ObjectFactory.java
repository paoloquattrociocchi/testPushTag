
package it.giustizia.gestorelocale.accettazione.esiti;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import it.giustizia.gestorelocale.accettazione.servizi.AccettazioneException;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.giustizia.gestorelocale.accettazione.esiti package. 
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

    private final static QName _CercaRiepiloghi_QNAME = new QName("http://www.giustizia.it/gestorelocale/accettazione/esiti", "cercaRiepiloghi");
    private final static QName _QueryRiepologhi_QNAME = new QName("http://www.giustizia.it/gestorelocale/accettazione/esiti", "queryRiepologhi");
    private final static QName _QueryRiepologhiResponse_QNAME = new QName("http://www.giustizia.it/gestorelocale/accettazione/esiti", "queryRiepologhiResponse");
    private final static QName _CercaRiepiloghiResponse_QNAME = new QName("http://www.giustizia.it/gestorelocale/accettazione/esiti", "cercaRiepiloghiResponse");
    private final static QName _QueryEventiDeposito_QNAME = new QName("http://www.giustizia.it/gestorelocale/accettazione/esiti", "queryEventiDeposito");
    private final static QName _AccettazioneException_QNAME = new QName("http://www.giustizia.it/gestorelocale/accettazione/esiti", "AccettazioneException");
    private final static QName _QueryEventiDepositoResponse_QNAME = new QName("http://www.giustizia.it/gestorelocale/accettazione/esiti", "queryEventiDepositoResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.giustizia.gestorelocale.accettazione.esiti
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link QueryEventiDepositoResponse }
     * 
     */
    public QueryEventiDepositoResponse createQueryEventiDepositoResponse() {
        return new QueryEventiDepositoResponse();
    }

    /**
     * Create an instance of {@link CercaRiepiloghi }
     * 
     */
    public CercaRiepiloghi createCercaRiepiloghi() {
        return new CercaRiepiloghi();
    }

    /**
     * Create an instance of {@link QueryRiepologhi }
     * 
     */
    public QueryRiepologhi createQueryRiepologhi() {
        return new QueryRiepologhi();
    }

    /**
     * Create an instance of {@link RiepilogoEsiti }
     * 
     */
    public RiepilogoEsiti createRiepilogoEsiti() {
        return new RiepilogoEsiti();
    }

    /**
     * Create an instance of {@link QueryRiepologhiResponse }
     * 
     */
    public QueryRiepologhiResponse createQueryRiepologhiResponse() {
        return new QueryRiepologhiResponse();
    }

    /**
     * Create an instance of {@link QueryEventiDeposito }
     * 
     */
    public QueryEventiDeposito createQueryEventiDeposito() {
        return new QueryEventiDeposito();
    }

    /**
     * Create an instance of {@link CercaRiepiloghiResponse }
     * 
     */
    public CercaRiepiloghiResponse createCercaRiepiloghiResponse() {
        return new CercaRiepiloghiResponse();
    }

    /**
     * Create an instance of {@link RiepilogoContenuto }
     * 
     */
    public RiepilogoContenuto createRiepilogoContenuto() {
        return new RiepilogoContenuto();
    }

    /**
     * Create an instance of {@link ExtraInfo }
     * 
     */
    public ExtraInfo createExtraInfo() {
        return new ExtraInfo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CercaRiepiloghi }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.giustizia.it/gestorelocale/accettazione/esiti", name = "cercaRiepiloghi")
    public JAXBElement<CercaRiepiloghi> createCercaRiepiloghi(CercaRiepiloghi value) {
        return new JAXBElement<CercaRiepiloghi>(_CercaRiepiloghi_QNAME, CercaRiepiloghi.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryRiepologhi }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.giustizia.it/gestorelocale/accettazione/esiti", name = "queryRiepologhi")
    public JAXBElement<QueryRiepologhi> createQueryRiepologhi(QueryRiepologhi value) {
        return new JAXBElement<QueryRiepologhi>(_QueryRiepologhi_QNAME, QueryRiepologhi.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryRiepologhiResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.giustizia.it/gestorelocale/accettazione/esiti", name = "queryRiepologhiResponse")
    public JAXBElement<QueryRiepologhiResponse> createQueryRiepologhiResponse(QueryRiepologhiResponse value) {
        return new JAXBElement<QueryRiepologhiResponse>(_QueryRiepologhiResponse_QNAME, QueryRiepologhiResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CercaRiepiloghiResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.giustizia.it/gestorelocale/accettazione/esiti", name = "cercaRiepiloghiResponse")
    public JAXBElement<CercaRiepiloghiResponse> createCercaRiepiloghiResponse(CercaRiepiloghiResponse value) {
        return new JAXBElement<CercaRiepiloghiResponse>(_CercaRiepiloghiResponse_QNAME, CercaRiepiloghiResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryEventiDeposito }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.giustizia.it/gestorelocale/accettazione/esiti", name = "queryEventiDeposito")
    public JAXBElement<QueryEventiDeposito> createQueryEventiDeposito(QueryEventiDeposito value) {
        return new JAXBElement<QueryEventiDeposito>(_QueryEventiDeposito_QNAME, QueryEventiDeposito.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccettazioneException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.giustizia.it/gestorelocale/accettazione/esiti", name = "AccettazioneException")
    public JAXBElement<AccettazioneException> createAccettazioneException(AccettazioneException value) {
        return new JAXBElement<AccettazioneException>(_AccettazioneException_QNAME, AccettazioneException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryEventiDepositoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.giustizia.it/gestorelocale/accettazione/esiti", name = "queryEventiDepositoResponse")
    public JAXBElement<QueryEventiDepositoResponse> createQueryEventiDepositoResponse(QueryEventiDepositoResponse value) {
        return new JAXBElement<QueryEventiDepositoResponse>(_QueryEventiDepositoResponse_QNAME, QueryEventiDepositoResponse.class, null, value);
    }

}
