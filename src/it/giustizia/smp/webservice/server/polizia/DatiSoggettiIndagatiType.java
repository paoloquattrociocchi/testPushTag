
package it.giustizia.smp.webservice.server.polizia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiSoggettiIndagatiType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiSoggettiIndagatiType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NUME_PROG" type="{http://webservice.smp.giustizia.it/basetypes}IDNumProgType"/>
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
@XmlType(name = "DatiSoggettiIndagatiType", propOrder = {
    "numeprog",
    "idsoggetto"
})
public class DatiSoggettiIndagatiType {

    @XmlElement(name = "NUME_PROG", required = true)
    protected String NUME_PROG;
    @XmlElement(name = "ID_SOGGETTO", required = true)
    protected String ID_SOGGETTO;
    
	public String getNUME_PROG() {
		return NUME_PROG;
	}
	public void setNUME_PROG(String nUME_PROG) {
		NUME_PROG = nUME_PROG;
	}
	public String getID_SOGGETTO() {
		return ID_SOGGETTO;
	}
	public void setID_SOGGETTO(String iD_SOGGETTO) {
		ID_SOGGETTO = iD_SOGGETTO;
	}
}
