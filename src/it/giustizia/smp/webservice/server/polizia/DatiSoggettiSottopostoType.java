
package it.giustizia.smp.webservice.server.polizia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiSoggettiSottopostoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiSoggettiSottopostoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NUME_PROG" type="{http://webservice.smp.giustizia.it/basetypes}IDNumProgType"/>
 *         &lt;element name="ID_SOGGETTO" type="{http://webservice.smp.giustizia.it/basetypes}IDSoggettoType"/>
 *         &lt;element name="DESCR_COGNOME" type="{http://webservice.smp.giustizia.it/basetypes}CognomeType"/>
 *         &lt;element name="DESCR_NOME" type="{http://webservice.smp.giustizia.it/basetypes}NomeType"/>
 *         &lt;element name="DATA_NASCITA" type="{http://webservice.smp.giustizia.it/basetypes}Data"/>
 *         &lt;element name="ID_ISTAT_STATO_NAS" type="{http://webservice.smp.giustizia.it/basetypes}CodStatoIstatType"/>
 *         &lt;element name="ID_ISTAT_COMUNE_NAS" type="{http://webservice.smp.giustizia.it/basetypes}CodComuneIstatType"/>
 *         &lt;element name="DESCR_COMUNE_NAS" type="{http://webservice.smp.giustizia.it/basetypes}ComuneType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiSoggettiSottopostoType", propOrder = {
    "numeprog",
    "idsoggetto",
    "descrcognome",
    "descrnome",
    "datanascita",
    "idistatstatonas",
    "idistatcomunenas",
    "descrcomunenas"
})
public class DatiSoggettiSottopostoType {

    @XmlElement(name = "NUME_PROG", required = true)
    protected String NUME_PROG;
    @XmlElement(name = "ID_SOGGETTO", required = true)
    protected String ID_SOGGETTO;
    @XmlElement(name = "DESCR_COGNOME", required = true, nillable = true)
    protected String DESCR_COGNOME;
    @XmlElement(name = "DESCR_NOME", required = true, nillable = true)
    protected String DESCR_NOME;
    @XmlElement(name = "DATA_NASCITA", required = true, nillable = true)
    protected String DATA_NASCITA;
    @XmlElement(name = "ID_ISTAT_STATO_NAS", required = true, nillable = true)
    protected String ID_ISTAT_STATO_NAS;
    @XmlElement(name = "ID_ISTAT_COMUNE_NAS", required = true, nillable = true)
    protected String ID_ISTAT_COMUNE_NAS;
    @XmlElement(name = "DESCR_COMUNE_NAS", required = true, nillable = true)
    protected String DESCR_COMUNE_NAS;
    
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
	public String getDESCR_COGNOME() {
		return DESCR_COGNOME;
	}
	public void setDESCR_COGNOME(String dESCR_COGNOME) {
		DESCR_COGNOME = dESCR_COGNOME;
	}
	public String getDESCR_NOME() {
		return DESCR_NOME;
	}
	public void setDESCR_NOME(String dESCR_NOME) {
		DESCR_NOME = dESCR_NOME;
	}
	public String getDATA_NASCITA() {
		return DATA_NASCITA;
	}
	public void setDATA_NASCITA(String dATA_NASCITA) {
		DATA_NASCITA = dATA_NASCITA;
	}
	public String getID_ISTAT_STATO_NAS() {
		return ID_ISTAT_STATO_NAS;
	}
	public void setID_ISTAT_STATO_NAS(String iD_ISTAT_STATO_NAS) {
		ID_ISTAT_STATO_NAS = iD_ISTAT_STATO_NAS;
	}
	public String getID_ISTAT_COMUNE_NAS() {
		return ID_ISTAT_COMUNE_NAS;
	}
	public void setID_ISTAT_COMUNE_NAS(String iD_ISTAT_COMUNE_NAS) {
		ID_ISTAT_COMUNE_NAS = iD_ISTAT_COMUNE_NAS;
	}
	public String getDESCR_COMUNE_NAS() {
		return DESCR_COMUNE_NAS;
	}
	public void setDESCR_COMUNE_NAS(String dESCR_COMUNE_NAS) {
		DESCR_COMUNE_NAS = dESCR_COMUNE_NAS;
	}

}
