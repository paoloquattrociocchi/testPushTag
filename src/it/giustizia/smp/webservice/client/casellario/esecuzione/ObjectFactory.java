
package it.giustizia.smp.webservice.client.casellario.esecuzione;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.giustizia.smp.webservice.client.casellario.esecuzione package. 
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

    private final static QName _ESTRATTOPROVVEDIMENTO_QNAME = new QName("http://it/mig/sippi/service/inviaProvvedimentoEsecuzione/type", "ESTRATTO_PROVVEDIMENTO");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.giustizia.smp.webservice.client.casellario.esecuzione
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ArrayMisurePrevenzione }
     * 
     */
    public ArrayMisurePrevenzione createArrayMisurePrevenzione() {
        return new ArrayMisurePrevenzione();
    }

    /**
     * Create an instance of {@link DATIOPERAZIONE }
     * 
     */
    public DATIOPERAZIONE createDATIOPERAZIONE() {
        return new DATIOPERAZIONE();
    }

    /**
     * Create an instance of {@link TRASFERIMENTO }
     * 
     */
    public TRASFERIMENTO createTRASFERIMENTO() {
        return new TRASFERIMENTO();
    }

    /**
     * Create an instance of {@link PROVVEDIMENTOESECUZIONEMP }
     * 
     */
    public PROVVEDIMENTOESECUZIONEMP createPROVVEDIMENTOESECUZIONEMP() {
        return new PROVVEDIMENTOESECUZIONEMP();
    }

    /**
     * Create an instance of {@link DATIANAGRAFICAPRINCIPALE }
     * 
     */
    public DATIANAGRAFICAPRINCIPALE createDATIANAGRAFICAPRINCIPALE() {
        return new DATIANAGRAFICAPRINCIPALE();
    }

    /**
     * Create an instance of {@link CHIAVI }
     * 
     */
    public CHIAVI createCHIAVI() {
        return new CHIAVI();
    }

    /**
     * Create an instance of {@link ESITO }
     * 
     */
    public ESITO createESITO() {
        return new ESITO();
    }

    /**
     * Create an instance of {@link OMONIMO }
     * 
     */
    public OMONIMO createOMONIMO() {
        return new OMONIMO();
    }

    /**
     * Create an instance of {@link ArrayEsito }
     * 
     */
    public ArrayEsito createArrayEsito() {
        return new ArrayEsito();
    }

    /**
     * Create an instance of {@link DATIRISPOSTATRASFERIMENTO }
     * 
     */
    public DATIRISPOSTATRASFERIMENTO createDATIRISPOSTATRASFERIMENTO() {
        return new DATIRISPOSTATRASFERIMENTO();
    }

    /**
     * Create an instance of {@link MISURADIPREVENZIONE }
     * 
     */
    public MISURADIPREVENZIONE createMISURADIPREVENZIONE() {
        return new MISURADIPREVENZIONE();
    }

    /**
     * Create an instance of {@link DATIANAGRAFICI }
     * 
     */
    public DATIANAGRAFICI createDATIANAGRAFICI() {
        return new DATIANAGRAFICI();
    }

    /**
     * Create an instance of {@link DATIUFFICIO }
     * 
     */
    public DATIUFFICIO createDATIUFFICIO() {
        return new DATIUFFICIO();
    }

    /**
     * Create an instance of {@link ArrayOmonimi }
     * 
     */
    public ArrayOmonimi createArrayOmonimi() {
        return new ArrayOmonimi();
    }

    /**
     * Create an instance of {@link DURATA }
     * 
     */
    public DURATA createDURATA() {
        return new DURATA();
    }

    /**
     * Create an instance of {@link DATIPROVVEDIMENTO }
     * 
     */
    public DATIPROVVEDIMENTO createDATIPROVVEDIMENTO() {
        return new DATIPROVVEDIMENTO();
    }

    /**
     * Create an instance of {@link DATA }
     * 
     */
    public DATA createDATA() {
        return new DATA();
    }

    /**
     * Create an instance of {@link DATIANAGRAFICAART130 }
     * 
     */
    public DATIANAGRAFICAART130 createDATIANAGRAFICAART130() {
        return new DATIANAGRAFICAART130();
    }

    /**
     * Create an instance of {@link DATICHIAMATATRASFERIMENTO }
     * 
     */
    public DATICHIAMATATRASFERIMENTO createDATICHIAMATATRASFERIMENTO() {
        return new DATICHIAMATATRASFERIMENTO();
    }

    /**
     * Create an instance of {@link PROCEDIMENTO }
     * 
     */
    public PROCEDIMENTO createPROCEDIMENTO() {
        return new PROCEDIMENTO();
    }

    /**
     * Create an instance of {@link DATIUTENTE }
     * 
     */
    public DATIUTENTE createDATIUTENTE() {
        return new DATIUTENTE();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it/mig/sippi/service/inviaProvvedimentoEsecuzione/type", name = "ESTRATTO_PROVVEDIMENTO")
    public JAXBElement<byte[]> createESTRATTOPROVVEDIMENTO(byte[] value) {
        return new JAXBElement<byte[]>(_ESTRATTOPROVVEDIMENTO_QNAME, byte[].class, null, ((byte[]) value));
    }

}
