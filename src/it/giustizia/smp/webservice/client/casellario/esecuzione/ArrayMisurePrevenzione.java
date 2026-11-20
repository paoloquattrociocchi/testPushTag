
package it.giustizia.smp.webservice.client.casellario.esecuzione;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://it/mig/sippi/service/inviaProvvedimentoEsecuzione/type}MISURA_DI_PREVENZIONE" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "misuradiprevenzione"
})
@XmlRootElement(name = "ArrayMisurePrevenzione")
public class ArrayMisurePrevenzione {

    @XmlElement(name = "MISURA_DI_PREVENZIONE")
    protected List<MISURADIPREVENZIONE> misuradiprevenzione;

    /**
     * Gets the value of the misuradiprevenzione property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the misuradiprevenzione property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMISURADIPREVENZIONE().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MISURADIPREVENZIONE }
     * 
     * 
     */
    public List<MISURADIPREVENZIONE> getMISURADIPREVENZIONE() {
        if (misuradiprevenzione == null) {
            misuradiprevenzione = new ArrayList<MISURADIPREVENZIONE>();
        }
        return this.misuradiprevenzione;
    }

}
