
package it.giustizia.smp.webservice.server.polizia;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PropostaMisuraPatrPersType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PropostaMisuraPatrPersType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://webservice.smp.giustizia.it/server/polizia/type}DatiAutoritaProponente"/>
 *         &lt;element ref="{http://webservice.smp.giustizia.it/server/polizia/type}DatiTribunaleCompetente"/>
 *         &lt;element ref="{http://webservice.smp.giustizia.it/server/polizia/type}DatiSoggettoDirettoTerzoFisico" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://webservice.smp.giustizia.it/server/polizia/type}DatiSoggettoDirettoTerzoGiuridico" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://webservice.smp.giustizia.it/server/polizia/type}DatiMisuraPersonalePF" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://webservice.smp.giustizia.it/server/polizia/type}DatiMisuraPatrimoniale" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PropostaMisuraPatrPersType", propOrder = {
    "datiAutoritaProponente",
    "datiTribunaleCompetente",
    "datiSoggettoDirettoTerzoFisico",
    "datiSoggettoDirettoTerzoGiuridico",
    "datiMisuraPersonalePF",
    "datiMisuraPatrimoniale"
})
public class PropostaMisuraPatrPersType {

    @XmlElement(name = "DatiAutoritaProponente", required = true)
    protected DatiAutoritaProponenteType datiAutoritaProponente;
    @XmlElement(name = "DatiTribunaleCompetente", required = true)
    protected DatiTribunaleCompetenteType datiTribunaleCompetente;
    @XmlElement(name = "DatiSoggettoDirettoTerzoFisico", required = true)
    protected List<DatiSoggettoFisicoType> datiSoggettoDirettoTerzoFisico;
    @XmlElement(name = "DatiSoggettoDirettoTerzoGiuridico", required = true)
    protected List<DatiSoggettoGiuridicoType> datiSoggettoDirettoTerzoGiuridico;
    @XmlElement(name = "DatiMisuraPersonalePF")
    protected List<DatiMisuraPersonalePFType> datiMisuraPersonalePF;
    @XmlElement(name = "DatiMisuraPatrimoniale")
    protected List<DatiMisuraPatrimonialeType> datiMisuraPatrimoniale;

    /**
     * 
     * 						Dati dell autorità proponente
     * 					
     * 
     * @return
     *     possible object is
     *     {@link DatiAutoritaProponenteType }
     *     
     */
    public DatiAutoritaProponenteType getDatiAutoritaProponente() {
        return datiAutoritaProponente;
    }

    /**
     * Sets the value of the datiAutoritaProponente property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiAutoritaProponenteType }
     *     
     */
    public void setDatiAutoritaProponente(DatiAutoritaProponenteType value) {
        this.datiAutoritaProponente = value;
    }

    /**
     * 
     * 						Dati del tribunale competente a decidere sulla
     * 						proposta
     * 					
     * 
     * @return
     *     possible object is
     *     {@link DatiTribunaleCompetenteType }
     *     
     */
    public DatiTribunaleCompetenteType getDatiTribunaleCompetente() {
        return datiTribunaleCompetente;
    }

    /**
     * Sets the value of the datiTribunaleCompetente property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiTribunaleCompetenteType }
     *     
     */
    public void setDatiTribunaleCompetente(DatiTribunaleCompetenteType value) {
        this.datiTribunaleCompetente = value;
    }

    /**
     * 
     * 						Dati identificativi del soggetto diretto/terso
     * 						(persona fisica)
     * 					Gets the value of the datiSoggettoDirettoTerzoFisico property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the datiSoggettoDirettoTerzoFisico property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDatiSoggettoDirettoTerzoFisico().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DatiSoggettoFisicoType }
     * 
     * 
     */
    public List<DatiSoggettoFisicoType> getDatiSoggettoDirettoTerzoFisico() {
        if (datiSoggettoDirettoTerzoFisico == null) {
            datiSoggettoDirettoTerzoFisico = new ArrayList<DatiSoggettoFisicoType>();
        }
        return this.datiSoggettoDirettoTerzoFisico;
    }

    /**
     * 
     * 						Dati identificativi del soggetto diretto/terso
     * 						(persona giuridica)
     * 					Gets the value of the datiSoggettoDirettoTerzoGiuridico property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the datiSoggettoDirettoTerzoGiuridico property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDatiSoggettoDirettoTerzoGiuridico().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DatiSoggettoGiuridicoType }
     * 
     * 
     */
    public List<DatiSoggettoGiuridicoType> getDatiSoggettoDirettoTerzoGiuridico() {
        if (datiSoggettoDirettoTerzoGiuridico == null) {
            datiSoggettoDirettoTerzoGiuridico = new ArrayList<DatiSoggettoGiuridicoType>();
        }
        return this.datiSoggettoDirettoTerzoGiuridico;
    }

    /**
     * 
     * 						Dati della misura personale (per proposto
     * 						persona fisica)
     * 					Gets the value of the datiMisuraPersonalePF property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the datiMisuraPersonalePF property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDatiMisuraPersonalePF().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DatiMisuraPersonalePFType }
     * 
     * 
     */
    public List<DatiMisuraPersonalePFType> getDatiMisuraPersonalePF() {
        if (datiMisuraPersonalePF == null) {
            datiMisuraPersonalePF = new ArrayList<DatiMisuraPersonalePFType>();
        }
        return this.datiMisuraPersonalePF;
    }

    /**
     * 
     * 						Dati della misura patrimoniale
     * 					Gets the value of the datiMisuraPatrimoniale property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the datiMisuraPatrimoniale property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDatiMisuraPatrimoniale().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DatiMisuraPatrimonialeType }
     * 
     * 
     */
    public List<DatiMisuraPatrimonialeType> getDatiMisuraPatrimoniale() {
        if (datiMisuraPatrimoniale == null) {
            datiMisuraPatrimoniale = new ArrayList<DatiMisuraPatrimonialeType>();
        }
        return this.datiMisuraPatrimoniale;
    }

}
