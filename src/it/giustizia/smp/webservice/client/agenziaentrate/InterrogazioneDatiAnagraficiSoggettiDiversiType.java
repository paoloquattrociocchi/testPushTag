
package it.giustizia.smp.webservice.client.agenziaentrate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for interrogazioneDatiAnagraficiSoggettiDiversiType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="interrogazioneDatiAnagraficiSoggettiDiversiType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="descrizioneDenominazione" type="{http://types.verifica.anagrafica}descrizioneDenominazioneType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "interrogazioneDatiAnagraficiSoggettiDiversiType", propOrder = {
    "descrizioneDenominazione"
})
public class InterrogazioneDatiAnagraficiSoggettiDiversiType {

    @XmlElement(required = true)
    protected String descrizioneDenominazione;

    /**
     * Gets the value of the descrizioneDenominazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneDenominazione() {
        return descrizioneDenominazione;
    }

    /**
     * Sets the value of the descrizioneDenominazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneDenominazione(String value) {
        this.descrizioneDenominazione = value;
    }

}
