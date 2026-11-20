
package it.giustizia.gestorelocale.accettazione.esiti;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for queryEventiDeposito complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="queryEventiDeposito">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idRepository" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "queryEventiDeposito", propOrder = {
    "idRepository"
})
public class QueryEventiDeposito {

    protected String idRepository;

    /**
     * Gets the value of the idRepository property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdRepository() {
        return idRepository;
    }

    /**
     * Sets the value of the idRepository property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdRepository(String value) {
        this.idRepository = value;
    }

}
