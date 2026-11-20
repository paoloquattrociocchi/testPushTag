
package it.giustizia.smp.webservice.server.polizia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IdSoggettoSottopostoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IdSoggettoSottopostoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ID_SOGGETTO" type="{http://webservice.smp.giustizia.it/basetypes}IDSoggettoType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdSoggettoSottopostoType", propOrder = {
    "idsoggetto"
})
public class IdSoggettoSottopostoType {

    @XmlElement(name = "ID_SOGGETTO", required = true)
    protected String ID_SOGGETTO;

	public String getID_SOGGETTO() {
		return ID_SOGGETTO;
	}

	public void setID_SOGGETTO(String iD_SOGGETTO) {
		ID_SOGGETTO = iD_SOGGETTO;
	}

}
