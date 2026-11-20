
package it.giustizia.smp.webservice.server.polizia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiUffGiuSottoposizioneType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiUffGiuSottoposizioneType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ID_UFFICIO" type="{http://webservice.smp.giustizia.it/basetypes}IDUfficioType"/>
 *         &lt;element name="ID_PROCEDIMENTO" type="{http://webservice.smp.giustizia.it/basetypes}IDProcedimentoType"/>
 *         &lt;element name="DESCR_NUM_PROC" type="{http://webservice.smp.giustizia.it/basetypes}DescNumFascicolo"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiUffGiuSottoposizioneType", propOrder = {
    "idufficio",
    "idprocedimento",
    "descrnumproc"
})
public class DatiUffGiuSottoposizioneType {

    @XmlElement(name = "ID_UFFICIO", required = true)
    protected String ID_UFFICIO;
    @XmlElement(name = "ID_PROCEDIMENTO", required = true)
    protected String ID_PROCEDIMENTO;
    @XmlElement(name = "DESCR_NUM_PROC", required = true, nillable = true)
    protected String DESCR_NUM_PROC;
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
	public String getDESCR_NUM_PROC() {
		return DESCR_NUM_PROC;
	}
	public void setDESCR_NUM_PROC(String dESCR_NUM_PROC) {
		DESCR_NUM_PROC = dESCR_NUM_PROC;
	}

   

}
