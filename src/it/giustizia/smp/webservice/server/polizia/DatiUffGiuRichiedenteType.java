
package it.giustizia.smp.webservice.server.polizia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiUffGiuRichiedenteType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiUffGiuRichiedenteType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ID_UFFICIO" type="{http://webservice.smp.giustizia.it/basetypes}IDUfficioType"/>
 *         &lt;element name="ID_PROCEDIMENTO" type="{http://webservice.smp.giustizia.it/basetypes}IDProcedimentoType"/>
 *         &lt;element name="ID_ATTO" type="{http://webservice.smp.giustizia.it/basetypes}IDAttoType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiUffGiuRichiedenteType", propOrder = {
    "idufficio",
    "idprocedimento",
    "idatto"
})
public class DatiUffGiuRichiedenteType {

    @XmlElement(name = "ID_UFFICIO", required = true)
    protected String ID_UFFICIO;
    @XmlElement(name = "ID_PROCEDIMENTO", required = true)
    protected String ID_PROCEDIMENTO;
    @XmlElement(name = "ID_ATTO", required = true)
    protected String ID_ATTO;
    
	public String getID_UFFICIO() {
		return ID_UFFICIO;
	}
	public void setID_UFFICIO(String iD_UFFICIO) {
		ID_UFFICIO = iD_UFFICIO;
	}
	public String getID_PROCEDIMENTO() {
		return ID_PROCEDIMENTO;
	}
	public void setID_PROCEDIMENTO(String iD_PROCEDIMENTO) {
		ID_PROCEDIMENTO = iD_PROCEDIMENTO;
	}
	public String getID_ATTO() {
		return ID_ATTO;
	}
	public void setID_ATTO(String iD_ATTO) {
		ID_ATTO = iD_ATTO;
	}

    

}
