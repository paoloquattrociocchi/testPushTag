
package it.giustizia.smp.webservice.server.polizia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiTribunaleCompetenteType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiTribunaleCompetenteType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ID_UFFICIO" type="{http://webservice.smp.giustizia.it/basetypes}IDUfficioType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiTribunaleCompetenteType", propOrder = {
    "idufficio"
})
public class DatiTribunaleCompetenteType {

    @XmlElement(name = "ID_UFFICIO", required = true)
    protected String ID_UFFICIO;

	public String getID_UFFICIO() {
		return ID_UFFICIO;
	}

	public void setID_UFFICIO(String iD_UFFICIO) {
		ID_UFFICIO = iD_UFFICIO;
	}

    

}
