
package it.giustizia.smp.webservice.server.polizia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiMisuraPersonalePFType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiMisuraPersonalePFType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NUME_PROG_MIS_PERS" type="{http://webservice.smp.giustizia.it/basetypes}IDNumProgType"/>
 *         &lt;element name="NUME_PROG_PROP" type="{http://webservice.smp.giustizia.it/basetypes}IDNumProgType"/>
 *         &lt;element name="ID_TIPO_PREV">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;length value="1"/>
 *               &lt;enumeration value="1"/>
 *               &lt;enumeration value="2"/>
 *               &lt;enumeration value="3"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ID_NORMATIVA_RIF" type="{http://webservice.smp.giustizia.it/basetypes}IDTipologicaGenericoType"/>
 *         &lt;element name="ID_TIPO_MIS_PERS" type="{http://webservice.smp.giustizia.it/basetypes}IDTipologicaGenericoType"/>
 *         &lt;element name="ID_NORM_MIS_PERS" type="{http://webservice.smp.giustizia.it/basetypes}IDTipologicaGenericoType"/>
 *         &lt;element name="DESCR_MIS_PERS" type="{http://webservice.smp.giustizia.it/basetypes}Desc200Type"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiMisuraPersonalePFType", propOrder = {
    "numeprogmispers",
    "numeprogprop",
    "idtipoprev",
    "idnormativarif",
    "idtipomispers",
    "idnormmispers",
    "descrmispers"
})
public class DatiMisuraPersonalePFType {

    @XmlElement(name = "NUME_PROG_MIS_PERS", required = true)
    protected String NUME_PROG_MIS_PERS;
    @XmlElement(name = "NUME_PROG_PROP", required = true)
    protected String NUME_PROG_PROP;
    @XmlElement(name = "ID_TIPO_PREV", required = true)
    protected String ID_TIPO_PREV;
    @XmlElement(name = "ID_NORMATIVA_RIF", required = true)
    protected String ID_NORMATIVA_RIF;
    @XmlElement(name = "ID_TIPO_MIS_PERS", required = true)
    protected String ID_TIPO_MIS_PERS;
    @XmlElement(name = "ID_NORM_MIS_PERS", required = true, nillable = true)
    protected String ID_NORM_MIS_PERS;
    @XmlElement(name = "DESCR_MIS_PERS", required = true, nillable = true)
    protected String DESCR_MIS_PERS;
    
	public String getNUME_PROG_MIS_PERS() {
		return NUME_PROG_MIS_PERS;
	}
	public void setNUME_PROG_MIS_PERS(String nUME_PROG_MIS_PERS) {
		NUME_PROG_MIS_PERS = nUME_PROG_MIS_PERS;
	}
	public String getNUME_PROG_PROP() {
		return NUME_PROG_PROP;
	}
	public void setNUME_PROG_PROP(String nUME_PROG_PROP) {
		NUME_PROG_PROP = nUME_PROG_PROP;
	}
	public String getID_TIPO_PREV() {
		return ID_TIPO_PREV;
	}
	public void setID_TIPO_PREV(String iD_TIPO_PREV) {
		ID_TIPO_PREV = iD_TIPO_PREV;
	}
	public String getID_NORMATIVA_RIF() {
		return ID_NORMATIVA_RIF;
	}
	public void setID_NORMATIVA_RIF(String iD_NORMATIVA_RIF) {
		ID_NORMATIVA_RIF = iD_NORMATIVA_RIF;
	}
	public String getID_TIPO_MIS_PERS() {
		return ID_TIPO_MIS_PERS;
	}
	public void setID_TIPO_MIS_PERS(String iD_TIPO_MIS_PERS) {
		ID_TIPO_MIS_PERS = iD_TIPO_MIS_PERS;
	}
	public String getID_NORM_MIS_PERS() {
		return ID_NORM_MIS_PERS;
	}
	public void setID_NORM_MIS_PERS(String iD_NORM_MIS_PERS) {
		ID_NORM_MIS_PERS = iD_NORM_MIS_PERS;
	}
	public String getDESCR_MIS_PERS() {
		return DESCR_MIS_PERS;
	}
	public void setDESCR_MIS_PERS(String dESCR_MIS_PERS) {
		DESCR_MIS_PERS = dESCR_MIS_PERS;
	}


}
