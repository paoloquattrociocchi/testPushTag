
package it.giustizia.gestorelocale.accettazione.servizi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for reinviaEsiti complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="reinviaEsiti">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idBusta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "reinviaEsiti", propOrder = {
    "idBusta"
})
public class ReinviaEsiti {

    protected String idBusta;

    /**
     * Gets the value of the idBusta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdBusta() {
        return idBusta;
    }

    /**
     * Sets the value of the idBusta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdBusta(String value) {
        this.idBusta = value;
    }

}
