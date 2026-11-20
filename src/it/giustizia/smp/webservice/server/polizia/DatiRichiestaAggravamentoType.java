
package it.giustizia.smp.webservice.server.polizia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiRichiestaAggravamentoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiRichiestaAggravamentoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DESCR_NUM_PROPOSTA" type="{http://webservice.smp.giustizia.it/basetypes}DescNumeroProposta"/>
 *         &lt;element name="DATA_EMISSIONE" type="{http://webservice.smp.giustizia.it/basetypes}Data"/>
 *         &lt;element name="DESCR_PROPOSTA" type="{http://webservice.smp.giustizia.it/basetypes}DescPropostaType"/>
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
@XmlType(name = "DatiRichiestaAggravamentoType", propOrder = {
    "descrnumproposta",
    "dataemissione",
    "descrproposta",
    "descrnote"
})
public class DatiRichiestaAggravamentoType {

    @XmlElement(name = "DESCR_NUM_PROPOSTA", required = true)
    protected String DESCR_NUM_PROPOSTA;
    @XmlElement(name = "DATA_EMISSIONE", required = true)
    protected String DATA_EMISSIONE;
    @XmlElement(name = "DESCR_PROPOSTA", required = true)
    protected String DESCR_PROPOSTA;
    @XmlElement(name = "DESCR_NOTE", required = true, nillable = true)
    protected String DESCR_NOTE;
    
	public String getDESCR_NUM_PROPOSTA() {
		return DESCR_NUM_PROPOSTA;
	}
	public void setDESCR_NUM_PROPOSTA(String dESCR_NUM_PROPOSTA) {
		DESCR_NUM_PROPOSTA = dESCR_NUM_PROPOSTA;
	}
	public String getDATA_EMISSIONE() {
		return DATA_EMISSIONE;
	}
	public void setDATA_EMISSIONE(String dATA_EMISSIONE) {
		DATA_EMISSIONE = dATA_EMISSIONE;
	}
	public String getDESCR_PROPOSTA() {
		return DESCR_PROPOSTA;
	}
	public void setDESCR_PROPOSTA(String dESCR_PROPOSTA) {
		DESCR_PROPOSTA = dESCR_PROPOSTA;
	}
	public String getDESCR_NOTE() {
		return DESCR_NOTE;
	}
	public void setDESCR_NOTE(String dESCR_NOTE) {
		DESCR_NOTE = dESCR_NOTE;
	}

   
}
