
package it.giustizia.smp.webservice.server.polizia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiComunicazioneType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiComunicazioneType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DESCR_NUM_COMUNIC" type="{http://webservice.smp.giustizia.it/basetypes}DescNumeroComunicazione"/>
 *         &lt;element name="DATA_EMISSIONE" type="{http://webservice.smp.giustizia.it/basetypes}Data"/>
 *         &lt;element name="ID_TIPO_COMUNIC" type="{http://webservice.smp.giustizia.it/basetypes}IDTipologicaGenericoType"/>
 *         &lt;element name="DATA_EVENTO" type="{http://webservice.smp.giustizia.it/basetypes}Data"/>
 *         &lt;element name="DESCR_NOTE" type="{http://webservice.smp.giustizia.it/basetypes}Desc200Type"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiComunicazioneType", propOrder = {
    "descrnumcomunic",
    "dataemissione",
    "idtipocomunic",
    "dataevento",
    "descrnote"
})
public class DatiComunicazioneType {

    @XmlElement(name = "DESCR_NUM_COMUNIC", required = true)
    protected String DESCR_NUM_COMUNIC;
    @XmlElement(name = "DATA_EMISSIONE", required = true)
    protected String DATA_EMISSIONE;
    @XmlElement(name = "ID_TIPO_COMUNIC", required = true)
    protected String ID_TIPO_COMUNIC;
    @XmlElement(name = "DATA_EVENTO", required = true)
    protected String DATA_EVENTO;
    @XmlElement(name = "DESCR_NOTE", required = true, nillable = true)
    protected String DESCR_NOTE;
    
	public String getDESCR_NUM_COMUNIC() {
		return DESCR_NUM_COMUNIC;
	}
	public void setDESCR_NUM_COMUNIC(String dESCR_NUM_COMUNIC) {
		DESCR_NUM_COMUNIC = dESCR_NUM_COMUNIC;
	}
	public String getDATA_EMISSIONE() {
		return DATA_EMISSIONE;
	}
	public void setDATA_EMISSIONE(String dATA_EMISSIONE) {
		DATA_EMISSIONE = dATA_EMISSIONE;
	}
	public String getID_TIPO_COMUNIC() {
		return ID_TIPO_COMUNIC;
	}
	public void setID_TIPO_COMUNIC(String iD_TIPO_COMUNIC) {
		ID_TIPO_COMUNIC = iD_TIPO_COMUNIC;
	}
	public String getDATA_EVENTO() {
		return DATA_EVENTO;
	}
	public void setDATA_EVENTO(String dATA_EVENTO) {
		DATA_EVENTO = dATA_EVENTO;
	}
	public String getDESCR_NOTE() {
		return DESCR_NOTE;
	}
	public void setDESCR_NOTE(String dESCR_NOTE) {
		DESCR_NOTE = dESCR_NOTE;
	}

}
