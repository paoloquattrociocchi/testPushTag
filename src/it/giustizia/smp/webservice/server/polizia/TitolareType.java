
package it.giustizia.smp.webservice.server.polizia;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TitolareType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TitolareType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NUME_PROG_TITOL" type="{http://webservice.smp.giustizia.it/basetypes}IDNumProgType"/>
 *         &lt;element name="ID_TIPO_INTERESSE" type="{http://webservice.smp.giustizia.it/basetypes}IDTipoInteresseType"/>
 *         &lt;element name="NUME_QUOTA" type="{http://webservice.smp.giustizia.it/basetypes}QuotaType"/>
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
 *         &lt;element name="ID_TIPO_MIS_PATR" type="{http://webservice.smp.giustizia.it/basetypes}IDTipologicaGenericoType"/>
 *         &lt;element name="ID_NORM_MIS_PATR" type="{http://webservice.smp.giustizia.it/basetypes}IDTipologicaGenericoType"/>
 *         &lt;element name="NUME_PROG_PROP" type="{http://webservice.smp.giustizia.it/basetypes}IDNumProgType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TitolareType", propOrder = {
    "numeprogtitol",
    "idtipointeresse",
    "numequota",
    "idtipoprev",
    "idnormativarif",
    "idtipomispatr",
    "idnormmispatr",
    "numeprogprop"
})
public class TitolareType {

    @XmlElement(name = "NUME_PROG_TITOL", required = true)
    protected String NUME_PROG_TITOL;
    @XmlElement(name = "ID_TIPO_INTERESSE", required = true)
    protected String ID_TIPO_INTERESSE;
    @XmlElement(name = "NUME_QUOTA", required = true, nillable = true)
    protected String NUME_QUOTA;
    @XmlElement(name = "ID_TIPO_PREV", required = true)
    protected String ID_TIPO_PREV;
    @XmlElement(name = "ID_NORMATIVA_RIF", required = true)
    protected String ID_NORMATIVA_RIF;
    @XmlElement(name = "ID_TIPO_MIS_PATR", required = true)
    protected String ID_TIPO_MIS_PATR;
    @XmlElement(name = "ID_NORM_MIS_PATR", required = true)
    protected String ID_NORM_MIS_PATR;
    @XmlElement(name = "NUME_PROG_PROP", required = true)
    protected List<String> NUME_PROG_PROP;
    
	public String getID_TIPO_INTERESSE() {
		return ID_TIPO_INTERESSE;
	}
	public void setID_TIPO_INTERESSE(String iD_TIPO_INTERESSE) {
		ID_TIPO_INTERESSE = iD_TIPO_INTERESSE;
	}
	public String getNUME_QUOTA() {
		return NUME_QUOTA;
	}
	public void setNUME_QUOTA(String nUME_QUOTA) {
		NUME_QUOTA = nUME_QUOTA;
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
	public String getID_TIPO_MIS_PATR() {
		return ID_TIPO_MIS_PATR;
	}
	public void setID_TIPO_MIS_PATR(String iD_TIPO_MIS_PATR) {
		ID_TIPO_MIS_PATR = iD_TIPO_MIS_PATR;
	}
	public String getID_NORM_MIS_PATR() {
		return ID_NORM_MIS_PATR;
	}
	public void setID_NORM_MIS_PATR(String iD_NORM_MIS_PATR) {
		ID_NORM_MIS_PATR = iD_NORM_MIS_PATR;
	}
	public List<String> getNUME_PROG_PROP() {
		return NUME_PROG_PROP;
	}
	public void setNUME_PROG_PROP(List<String> nUME_PROG_PROP) {
		NUME_PROG_PROP = nUME_PROG_PROP;
	}
	public String getNUME_PROG_TITOL() {
		return NUME_PROG_TITOL;
	}
	public void setNUME_PROG_TITOL(String nUME_PROG_TITOL) {
		NUME_PROG_TITOL = nUME_PROG_TITOL;
	}

}
