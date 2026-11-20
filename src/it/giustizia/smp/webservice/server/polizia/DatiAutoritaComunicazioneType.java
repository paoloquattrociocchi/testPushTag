
package it.giustizia.smp.webservice.server.polizia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiAutoritaComunicazioneType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiAutoritaComunicazioneType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ID_AUTORITA" type="{http://webservice.smp.giustizia.it/basetypes}IDAutoritaType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiAutoritaComunicazioneType", propOrder = {
    "idautorita"
})
public class DatiAutoritaComunicazioneType {

    @XmlElement(name = "ID_AUTORITA", required = true)
    protected String ID_AUTORITA;

	public String getID_AUTORITA() {
		return ID_AUTORITA;
	}

	public void setID_AUTORITA(String iD_AUTORITA) {
		ID_AUTORITA = iD_AUTORITA;
	}

    

}
