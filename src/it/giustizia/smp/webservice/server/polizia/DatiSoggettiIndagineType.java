
package it.giustizia.smp.webservice.server.polizia;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiSoggettiIndagineType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiSoggettiIndagineType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence maxOccurs="unbounded">
 *         &lt;element name="personaFisica" type="{http://webservice.smp.giustizia.it/server/polizia/type}DatiSoggettoFisicoType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="personaGiuridica" type="{http://webservice.smp.giustizia.it/server/polizia/type}DatiSoggettoGiuridicoType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiSoggettiIndagineType", propOrder = {
    "personaFisicaAndPersonaGiuridica"
})
public class DatiSoggettiIndagineType {

    @XmlElements({
        @XmlElement(name = "personaGiuridica", type = DatiSoggettoGiuridicoType.class, nillable = true),
        @XmlElement(name = "personaFisica", type = DatiSoggettoFisicoType.class, nillable = true)
    })
    protected List<Object> personaFisicaAndPersonaGiuridica;

    /**
     * Gets the value of the personaFisicaAndPersonaGiuridica property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the personaFisicaAndPersonaGiuridica property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPersonaFisicaAndPersonaGiuridica().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DatiSoggettoGiuridicoType }
     * {@link DatiSoggettoFisicoType }
     * 
     * 
     */
    public List<Object> getPersonaFisicaAndPersonaGiuridica() {
        if (personaFisicaAndPersonaGiuridica == null) {
            personaFisicaAndPersonaGiuridica = new ArrayList<Object>();
        }
        return this.personaFisicaAndPersonaGiuridica;
    }

}
