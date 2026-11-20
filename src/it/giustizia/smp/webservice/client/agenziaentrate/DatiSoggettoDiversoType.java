
package it.giustizia.smp.webservice.client.agenziaentrate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for datiSoggettoDiversoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="datiSoggettoDiversoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="soggettoDiverso" type="{http://anagrafica.verifica.anagrafica}soggettoDiversoType" minOccurs="0"/>
 *         &lt;element name="messaggio" type="{http://anagrafica.verifica.anagrafica}messaggioType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "datiSoggettoDiversoType", propOrder = {
    "soggettoDiverso",
    "messaggio"
})
public class DatiSoggettoDiversoType {

    protected SoggettoDiversoType soggettoDiverso;
    protected MessaggioType messaggio;

    /**
     * Gets the value of the soggettoDiverso property.
     * 
     * @return
     *     possible object is
     *     {@link SoggettoDiversoType }
     *     
     */
    public SoggettoDiversoType getSoggettoDiverso() {
        return soggettoDiverso;
    }

    /**
     * Sets the value of the soggettoDiverso property.
     * 
     * @param value
     *     allowed object is
     *     {@link SoggettoDiversoType }
     *     
     */
    public void setSoggettoDiverso(SoggettoDiversoType value) {
        this.soggettoDiverso = value;
    }

    /**
     * Gets the value of the messaggio property.
     * 
     * @return
     *     possible object is
     *     {@link MessaggioType }
     *     
     */
    public MessaggioType getMessaggio() {
        return messaggio;
    }

    /**
     * Sets the value of the messaggio property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessaggioType }
     *     
     */
    public void setMessaggio(MessaggioType value) {
        this.messaggio = value;
    }

}
