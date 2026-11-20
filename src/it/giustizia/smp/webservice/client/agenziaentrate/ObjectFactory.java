
package it.giustizia.smp.webservice.client.agenziaentrate;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.giustizia.smp.webservice.client.agenziaentrate package. 
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

    private final static QName _DatiPersonaFisica_QNAME = new QName("http://anagrafica.verifica.anagrafica", "datiPersonaFisica");
    private final static QName _InterrogazioneCodiceFiscalePersonaFisica_QNAME = new QName("http://anagrafica.verifica.anagrafica", "interrogazioneCodiceFiscalePersonaFisica");
    private final static QName _DatiSoggettoDiverso_QNAME = new QName("http://anagrafica.verifica.anagrafica", "datiSoggettoDiverso");
    private final static QName _InterrogazioneDatiAnagraficiSoggettiDiversi_QNAME = new QName("http://anagrafica.verifica.anagrafica", "interrogazioneDatiAnagraficiSoggettiDiversi");
    private final static QName _InterrogazioneDatiAnagraficiPersonaFisica_QNAME = new QName("http://anagrafica.verifica.anagrafica", "interrogazioneDatiAnagraficiPersonaFisica");
    private final static QName _InterrogazioneCodiceFiscaleSoggettiDiversi_QNAME = new QName("http://anagrafica.verifica.anagrafica", "interrogazioneCodiceFiscaleSoggettiDiversi");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.giustizia.smp.webservice.client.agenziaentrate
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MessaggioType }
     * 
     */
    public MessaggioType createMessaggioType() {
        return new MessaggioType();
    }

    /**
     * Create an instance of {@link SoggettoDiversoType }
     * 
     */
    public SoggettoDiversoType createSoggettoDiversoType() {
        return new SoggettoDiversoType();
    }

    /**
     * Create an instance of {@link InterrogazioneDatiAnagraficiPersonaFisicaType }
     * 
     */
    public InterrogazioneDatiAnagraficiPersonaFisicaType createInterrogazioneDatiAnagraficiPersonaFisicaType() {
        return new InterrogazioneDatiAnagraficiPersonaFisicaType();
    }

    /**
     * Create an instance of {@link DatiSoggettoDiversoType }
     * 
     */
    public DatiSoggettoDiversoType createDatiSoggettoDiversoType() {
        return new DatiSoggettoDiversoType();
    }

    /**
     * Create an instance of {@link DatiPersonaFisicaType }
     * 
     */
    public DatiPersonaFisicaType createDatiPersonaFisicaType() {
        return new DatiPersonaFisicaType();
    }

    /**
     * Create an instance of {@link DatiDiNascitaType }
     * 
     */
    public DatiDiNascitaType createDatiDiNascitaType() {
        return new DatiDiNascitaType();
    }

    /**
     * Create an instance of {@link PersonaFisicaType }
     * 
     */
    public PersonaFisicaType createPersonaFisicaType() {
        return new PersonaFisicaType();
    }

    /**
     * Create an instance of {@link InterrogazioneCodiceFiscaleType }
     * 
     */
    public InterrogazioneCodiceFiscaleType createInterrogazioneCodiceFiscaleType() {
        return new InterrogazioneCodiceFiscaleType();
    }

    /**
     * Create an instance of {@link InterrogazioneDatiAnagraficiSoggettiDiversiType }
     * 
     */
    public InterrogazioneDatiAnagraficiSoggettiDiversiType createInterrogazioneDatiAnagraficiSoggettiDiversiType() {
        return new InterrogazioneDatiAnagraficiSoggettiDiversiType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DatiPersonaFisicaType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://anagrafica.verifica.anagrafica", name = "datiPersonaFisica")
    public JAXBElement<DatiPersonaFisicaType> createDatiPersonaFisica(DatiPersonaFisicaType value) {
        return new JAXBElement<DatiPersonaFisicaType>(_DatiPersonaFisica_QNAME, DatiPersonaFisicaType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InterrogazioneCodiceFiscaleType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://anagrafica.verifica.anagrafica", name = "interrogazioneCodiceFiscalePersonaFisica")
    public JAXBElement<InterrogazioneCodiceFiscaleType> createInterrogazioneCodiceFiscalePersonaFisica(InterrogazioneCodiceFiscaleType value) {
        return new JAXBElement<InterrogazioneCodiceFiscaleType>(_InterrogazioneCodiceFiscalePersonaFisica_QNAME, InterrogazioneCodiceFiscaleType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DatiSoggettoDiversoType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://anagrafica.verifica.anagrafica", name = "datiSoggettoDiverso")
    public JAXBElement<DatiSoggettoDiversoType> createDatiSoggettoDiverso(DatiSoggettoDiversoType value) {
        return new JAXBElement<DatiSoggettoDiversoType>(_DatiSoggettoDiverso_QNAME, DatiSoggettoDiversoType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InterrogazioneDatiAnagraficiSoggettiDiversiType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://anagrafica.verifica.anagrafica", name = "interrogazioneDatiAnagraficiSoggettiDiversi")
    public JAXBElement<InterrogazioneDatiAnagraficiSoggettiDiversiType> createInterrogazioneDatiAnagraficiSoggettiDiversi(InterrogazioneDatiAnagraficiSoggettiDiversiType value) {
        return new JAXBElement<InterrogazioneDatiAnagraficiSoggettiDiversiType>(_InterrogazioneDatiAnagraficiSoggettiDiversi_QNAME, InterrogazioneDatiAnagraficiSoggettiDiversiType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InterrogazioneDatiAnagraficiPersonaFisicaType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://anagrafica.verifica.anagrafica", name = "interrogazioneDatiAnagraficiPersonaFisica")
    public JAXBElement<InterrogazioneDatiAnagraficiPersonaFisicaType> createInterrogazioneDatiAnagraficiPersonaFisica(InterrogazioneDatiAnagraficiPersonaFisicaType value) {
        return new JAXBElement<InterrogazioneDatiAnagraficiPersonaFisicaType>(_InterrogazioneDatiAnagraficiPersonaFisica_QNAME, InterrogazioneDatiAnagraficiPersonaFisicaType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InterrogazioneCodiceFiscaleType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://anagrafica.verifica.anagrafica", name = "interrogazioneCodiceFiscaleSoggettiDiversi")
    public JAXBElement<InterrogazioneCodiceFiscaleType> createInterrogazioneCodiceFiscaleSoggettiDiversi(InterrogazioneCodiceFiscaleType value) {
        return new JAXBElement<InterrogazioneCodiceFiscaleType>(_InterrogazioneCodiceFiscaleSoggettiDiversi_QNAME, InterrogazioneCodiceFiscaleType.class, null, value);
    }

}
