
package it.giustizia.smp.webservice.server.polizia;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiMisuraPatrimonialeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiMisuraPatrimonialeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NUME_PROG_BENE" type="{http://webservice.smp.giustizia.it/basetypes}IDNumProgType"/>
 *         &lt;element name="ID_BENE" type="{http://webservice.smp.giustizia.it/basetypes}IDBeneType"/>
 *         &lt;element name="ID_TIPO_BENE" type="{http://webservice.smp.giustizia.it/basetypes}IDTipoBenType"/>
 *         &lt;element name="ID_CAT_BENE" type="{http://webservice.smp.giustizia.it/basetypes}IDTipologicaGenericoType"/>
 *         &lt;element name="ID_SUBCAT_BENE" type="{http://webservice.smp.giustizia.it/basetypes}IDTipologicaGenericoType"/>
 *         &lt;element name="SPECIFICA_BENE" type="{http://webservice.smp.giustizia.it/server/polizia/type}DatiSpecificiBeneType"/>
 *         &lt;element name="TITOLARE" type="{http://webservice.smp.giustizia.it/server/polizia/type}TitolareType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiMisuraPatrimonialeType", propOrder = {
    "numeprogbene",
    "idbene",
    "idtipobene",
    "idcatbene",
    "idsubcatbene",
    "specificabene",
    "titolare"
})
public class DatiMisuraPatrimonialeType {

    @XmlElement(name = "NUME_PROG_BENE", required = true)
    protected String NUME_PROG_BENE;
    @XmlElement(name = "ID_BENE", required = true, nillable = true)
    protected String ID_BENE;
    @XmlElement(name = "ID_TIPO_BENE", required = true)
    protected String ID_TIPO_BENE;
    @XmlElement(name = "ID_CAT_BENE", required = true)
    protected String ID_CAT_BENE;
    @XmlElement(name = "ID_SUBCAT_BENE", required = true)
    protected String ID_SUBCAT_BENE;
    @XmlElement(name = "SPECIFICA_BENE", required = true, nillable = true)
    protected DatiSpecificiBeneType SPECIFICA_BENE;
    @XmlElement(name = "TITOLARE", required = true)
    protected List<TitolareType> TITOLARE;
    
	public String getNUME_PROG_BENE() {
		return NUME_PROG_BENE;
	}
	public void setNUME_PROG_BENE(String nUME_PROG_BENE) {
		NUME_PROG_BENE = nUME_PROG_BENE;
	}
	public String getID_BENE() {
		return ID_BENE;
	}
	public void setID_BENE(String iD_BENE) {
		ID_BENE = iD_BENE;
	}
	public String getID_TIPO_BENE() {
		return ID_TIPO_BENE;
	}
	public void setID_TIPO_BENE(String iD_TIPO_BENE) {
		ID_TIPO_BENE = iD_TIPO_BENE;
	}
	public String getID_CAT_BENE() {
		return ID_CAT_BENE;
	}
	public void setID_CAT_BENE(String iD_CAT_BENE) {
		ID_CAT_BENE = iD_CAT_BENE;
	}
	public DatiSpecificiBeneType getSPECIFICA_BENE() {
		return SPECIFICA_BENE;
	}
	public void setSPECIFICA_BENE(DatiSpecificiBeneType sPECIFICA_BENE) {
		SPECIFICA_BENE = sPECIFICA_BENE;
	}
	public List<TitolareType> getTITOLARE() {
		return TITOLARE;
	}
	public void setTITOLARE(List<TitolareType> tITOLARE) {
		TITOLARE = tITOLARE;
	}
	public String getID_SUBCAT_BENE() {
		return ID_SUBCAT_BENE;
	}
	public void setID_SUBCAT_BENE(String iD_SUBCAT_BENE) {
		ID_SUBCAT_BENE = iD_SUBCAT_BENE;
	}

}
