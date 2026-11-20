//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2015.03.10 alle 02:34:02 PM CET 
//


package it.giustizia.smp.webservice.client.glmp.servizitrasmissione;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.giustizia.smp.webservice.client.glmp.servizitrasmissione package. 
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

    private final static QName _Reinvia_QNAME = new QName("http://www.giustizia.it/gestorelocale/invioAtti", "reinvia");
    private final static QName _InviaComunicazione_QNAME = new QName("http://www.giustizia.it/gestorelocale/invioAtti", "inviaComunicazione");
    private final static QName _ReinviaResponse_QNAME = new QName("http://www.giustizia.it/gestorelocale/invioAtti", "reinviaResponse");
    private final static QName _ErroreServizioException_QNAME = new QName("http://www.giustizia.it/gestorelocale/invioAtti", "ErroreServizioException");
    private final static QName _QueryStatoTrasmissione_QNAME = new QName("http://www.giustizia.it/gestorelocale/invioAtti", "queryStatoTrasmissione");
    private final static QName _InviaComunicazioneResponse_QNAME = new QName("http://www.giustizia.it/gestorelocale/invioAtti", "inviaComunicazioneResponse");
    private final static QName _ParametroInvalidoException_QNAME = new QName("http://www.giustizia.it/gestorelocale/invioAtti", "ParametroInvalidoException");
    private final static QName _GeneraRapportoTrasmissione_QNAME = new QName("http://www.giustizia.it/gestorelocale/invioAtti", "generaRapportoTrasmissione");
    private final static QName _RecuperaRapportoTrasmissioneResponse_QNAME = new QName("http://www.giustizia.it/gestorelocale/invioAtti", "recuperaRapportoTrasmissioneResponse");
    private final static QName _QueryStatoTrasmissioneResponse_QNAME = new QName("http://www.giustizia.it/gestorelocale/invioAtti", "queryStatoTrasmissioneResponse");
    private final static QName _GestioneManuale_QNAME = new QName("http://www.giustizia.it/gestorelocale/invioAtti", "gestioneManuale");
    private final static QName _DownloadRicevuta_QNAME = new QName("http://www.giustizia.it/gestorelocale/invioAtti", "downloadRicevuta");
    private final static QName _DownloadRicevutaResponse_QNAME = new QName("http://www.giustizia.it/gestorelocale/invioAtti", "downloadRicevutaResponse");
    private final static QName _GeneraRapportoTrasmissioneResponse_QNAME = new QName("http://www.giustizia.it/gestorelocale/invioAtti", "generaRapportoTrasmissioneResponse");
    private final static QName _GestioneManualeResponse_QNAME = new QName("http://www.giustizia.it/gestorelocale/invioAtti", "gestioneManualeResponse");
    private final static QName _RecuperaRapportoTrasmissione_QNAME = new QName("http://www.giustizia.it/gestorelocale/invioAtti", "recuperaRapportoTrasmissione");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.giustizia.smp.webservice.client.glmp.servizitrasmissione
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ParametroInvalidoException }
     * 
     */
    public ParametroInvalidoException createParametroInvalidoException() {
        return new ParametroInvalidoException();
    }

    /**
     * Create an instance of {@link GeneraRapportoTrasmissione }
     * 
     */
    public GeneraRapportoTrasmissione createGeneraRapportoTrasmissione() {
        return new GeneraRapportoTrasmissione();
    }

    /**
     * Create an instance of {@link InviaComunicazioneResponse }
     * 
     */
    public InviaComunicazioneResponse createInviaComunicazioneResponse() {
        return new InviaComunicazioneResponse();
    }

    /**
     * Create an instance of {@link Reinvia }
     * 
     */
    public Reinvia createReinvia() {
        return new Reinvia();
    }

    /**
     * Create an instance of {@link InviaComunicazione }
     * 
     */
    public InviaComunicazione createInviaComunicazione() {
        return new InviaComunicazione();
    }

    /**
     * Create an instance of {@link ReinviaResponse }
     * 
     */
    public ReinviaResponse createReinviaResponse() {
        return new ReinviaResponse();
    }

    /**
     * Create an instance of {@link ErroreServizioException }
     * 
     */
    public ErroreServizioException createErroreServizioException() {
        return new ErroreServizioException();
    }

    /**
     * Create an instance of {@link QueryStatoTrasmissione }
     * 
     */
    public QueryStatoTrasmissione createQueryStatoTrasmissione() {
        return new QueryStatoTrasmissione();
    }

    /**
     * Create an instance of {@link RecuperaRapportoTrasmissione }
     * 
     */
    public RecuperaRapportoTrasmissione createRecuperaRapportoTrasmissione() {
        return new RecuperaRapportoTrasmissione();
    }

    /**
     * Create an instance of {@link GestioneManualeResponse }
     * 
     */
    public GestioneManualeResponse createGestioneManualeResponse() {
        return new GestioneManualeResponse();
    }

    /**
     * Create an instance of {@link DownloadRicevuta }
     * 
     */
    public DownloadRicevuta createDownloadRicevuta() {
        return new DownloadRicevuta();
    }

    /**
     * Create an instance of {@link DownloadRicevutaResponse }
     * 
     */
    public DownloadRicevutaResponse createDownloadRicevutaResponse() {
        return new DownloadRicevutaResponse();
    }

    /**
     * Create an instance of {@link GeneraRapportoTrasmissioneResponse }
     * 
     */
    public GeneraRapportoTrasmissioneResponse createGeneraRapportoTrasmissioneResponse() {
        return new GeneraRapportoTrasmissioneResponse();
    }

    /**
     * Create an instance of {@link RecuperaRapportoTrasmissioneResponse }
     * 
     */
    public RecuperaRapportoTrasmissioneResponse createRecuperaRapportoTrasmissioneResponse() {
        return new RecuperaRapportoTrasmissioneResponse();
    }

    /**
     * Create an instance of {@link GestioneManuale }
     * 
     */
    public GestioneManuale createGestioneManuale() {
        return new GestioneManuale();
    }

    /**
     * Create an instance of {@link QueryStatoTrasmissioneResponse }
     * 
     */
    public QueryStatoTrasmissioneResponse createQueryStatoTrasmissioneResponse() {
        return new QueryStatoTrasmissioneResponse();
    }

    /**
     * Create an instance of {@link EventoTrasmissione }
     * 
     */
    public EventoTrasmissione createEventoTrasmissione() {
        return new EventoTrasmissione();
    }

    /**
     * Create an instance of {@link StatoTrasmissione }
     * 
     */
    public StatoTrasmissione createStatoTrasmissione() {
        return new StatoTrasmissione();
    }

    /**
     * Create an instance of {@link RisultatoComunicazione }
     * 
     */
    public RisultatoComunicazione createRisultatoComunicazione() {
        return new RisultatoComunicazione();
    }

    /**
     * Create an instance of {@link InfoTrasmissione }
     * 
     */
    public InfoTrasmissione createInfoTrasmissione() {
        return new InfoTrasmissione();
    }

    /**
     * Create an instance of {@link Attributo }
     * 
     */
    public Attributo createAttributo() {
        return new Attributo();
    }

    /**
     * Create an instance of {@link InfoDestinatario }
     * 
     */
    public InfoDestinatario createInfoDestinatario() {
        return new InfoDestinatario();
    }

    /**
     * Create an instance of {@link Notifica }
     * 
     */
    public Notifica createNotifica() {
        return new Notifica();
    }

    /**
     * Create an instance of {@link InfoEccezione }
     * 
     */
    public InfoEccezione createInfoEccezione() {
        return new InfoEccezione();
    }

    /**
     * Create an instance of {@link IdFascicolo }
     * 
     */
    public IdFascicolo createIdFascicolo() {
        return new IdFascicolo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Reinvia }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.giustizia.it/gestorelocale/invioAtti", name = "reinvia")
    public JAXBElement<Reinvia> createReinvia(Reinvia value) {
        return new JAXBElement<Reinvia>(_Reinvia_QNAME, Reinvia.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InviaComunicazione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.giustizia.it/gestorelocale/invioAtti", name = "inviaComunicazione")
    public JAXBElement<InviaComunicazione> createInviaComunicazione(InviaComunicazione value) {
        return new JAXBElement<InviaComunicazione>(_InviaComunicazione_QNAME, InviaComunicazione.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReinviaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.giustizia.it/gestorelocale/invioAtti", name = "reinviaResponse")
    public JAXBElement<ReinviaResponse> createReinviaResponse(ReinviaResponse value) {
        return new JAXBElement<ReinviaResponse>(_ReinviaResponse_QNAME, ReinviaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ErroreServizioException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.giustizia.it/gestorelocale/invioAtti", name = "ErroreServizioException")
    public JAXBElement<ErroreServizioException> createErroreServizioException(ErroreServizioException value) {
        return new JAXBElement<ErroreServizioException>(_ErroreServizioException_QNAME, ErroreServizioException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryStatoTrasmissione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.giustizia.it/gestorelocale/invioAtti", name = "queryStatoTrasmissione")
    public JAXBElement<QueryStatoTrasmissione> createQueryStatoTrasmissione(QueryStatoTrasmissione value) {
        return new JAXBElement<QueryStatoTrasmissione>(_QueryStatoTrasmissione_QNAME, QueryStatoTrasmissione.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InviaComunicazioneResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.giustizia.it/gestorelocale/invioAtti", name = "inviaComunicazioneResponse")
    public JAXBElement<InviaComunicazioneResponse> createInviaComunicazioneResponse(InviaComunicazioneResponse value) {
        return new JAXBElement<InviaComunicazioneResponse>(_InviaComunicazioneResponse_QNAME, InviaComunicazioneResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ParametroInvalidoException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.giustizia.it/gestorelocale/invioAtti", name = "ParametroInvalidoException")
    public JAXBElement<ParametroInvalidoException> createParametroInvalidoException(ParametroInvalidoException value) {
        return new JAXBElement<ParametroInvalidoException>(_ParametroInvalidoException_QNAME, ParametroInvalidoException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GeneraRapportoTrasmissione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.giustizia.it/gestorelocale/invioAtti", name = "generaRapportoTrasmissione")
    public JAXBElement<GeneraRapportoTrasmissione> createGeneraRapportoTrasmissione(GeneraRapportoTrasmissione value) {
        return new JAXBElement<GeneraRapportoTrasmissione>(_GeneraRapportoTrasmissione_QNAME, GeneraRapportoTrasmissione.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RecuperaRapportoTrasmissioneResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.giustizia.it/gestorelocale/invioAtti", name = "recuperaRapportoTrasmissioneResponse")
    public JAXBElement<RecuperaRapportoTrasmissioneResponse> createRecuperaRapportoTrasmissioneResponse(RecuperaRapportoTrasmissioneResponse value) {
        return new JAXBElement<RecuperaRapportoTrasmissioneResponse>(_RecuperaRapportoTrasmissioneResponse_QNAME, RecuperaRapportoTrasmissioneResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryStatoTrasmissioneResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.giustizia.it/gestorelocale/invioAtti", name = "queryStatoTrasmissioneResponse")
    public JAXBElement<QueryStatoTrasmissioneResponse> createQueryStatoTrasmissioneResponse(QueryStatoTrasmissioneResponse value) {
        return new JAXBElement<QueryStatoTrasmissioneResponse>(_QueryStatoTrasmissioneResponse_QNAME, QueryStatoTrasmissioneResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GestioneManuale }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.giustizia.it/gestorelocale/invioAtti", name = "gestioneManuale")
    public JAXBElement<GestioneManuale> createGestioneManuale(GestioneManuale value) {
        return new JAXBElement<GestioneManuale>(_GestioneManuale_QNAME, GestioneManuale.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DownloadRicevuta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.giustizia.it/gestorelocale/invioAtti", name = "downloadRicevuta")
    public JAXBElement<DownloadRicevuta> createDownloadRicevuta(DownloadRicevuta value) {
        return new JAXBElement<DownloadRicevuta>(_DownloadRicevuta_QNAME, DownloadRicevuta.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DownloadRicevutaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.giustizia.it/gestorelocale/invioAtti", name = "downloadRicevutaResponse")
    public JAXBElement<DownloadRicevutaResponse> createDownloadRicevutaResponse(DownloadRicevutaResponse value) {
        return new JAXBElement<DownloadRicevutaResponse>(_DownloadRicevutaResponse_QNAME, DownloadRicevutaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GeneraRapportoTrasmissioneResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.giustizia.it/gestorelocale/invioAtti", name = "generaRapportoTrasmissioneResponse")
    public JAXBElement<GeneraRapportoTrasmissioneResponse> createGeneraRapportoTrasmissioneResponse(GeneraRapportoTrasmissioneResponse value) {
        return new JAXBElement<GeneraRapportoTrasmissioneResponse>(_GeneraRapportoTrasmissioneResponse_QNAME, GeneraRapportoTrasmissioneResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GestioneManualeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.giustizia.it/gestorelocale/invioAtti", name = "gestioneManualeResponse")
    public JAXBElement<GestioneManualeResponse> createGestioneManualeResponse(GestioneManualeResponse value) {
        return new JAXBElement<GestioneManualeResponse>(_GestioneManualeResponse_QNAME, GestioneManualeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RecuperaRapportoTrasmissione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.giustizia.it/gestorelocale/invioAtti", name = "recuperaRapportoTrasmissione")
    public JAXBElement<RecuperaRapportoTrasmissione> createRecuperaRapportoTrasmissione(RecuperaRapportoTrasmissione value) {
        return new JAXBElement<RecuperaRapportoTrasmissione>(_RecuperaRapportoTrasmissione_QNAME, RecuperaRapportoTrasmissione.class, null, value);
    }

}
