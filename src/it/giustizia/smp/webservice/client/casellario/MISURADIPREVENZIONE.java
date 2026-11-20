//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.09.17 alle 11:32:38 AM CEST 
//


package it.giustizia.smp.webservice.client.casellario;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PROG_MIS_PREVENZIONE">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               &lt;totalDigits value="15"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="CODI_TIPO_MP">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="3"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="DURATA_MISURA" type="{http://it/mig/sippi/service/inviaProvvedimento/type}DURATA" minOccurs="0"/>
 *         &lt;element name="IMPORTO" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "progmisprevenzione",
    "coditipomp",
    "duratamisura",
    "importo"
})
@XmlRootElement(name = "MISURA_DI_PREVENZIONE")
public class MISURADIPREVENZIONE {

    @XmlElement(name = "PROG_MIS_PREVENZIONE", required = true)
    protected BigInteger progmisprevenzione;
    @XmlElement(name = "CODI_TIPO_MP", required = true)
    protected String coditipomp;
    @XmlElement(name = "DURATA_MISURA")
    protected DURATA duratamisura;
    @XmlElement(name = "IMPORTO")
    protected BigDecimal importo;

    /**
     * Recupera il valore della proprietà progmisprevenzione.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPROGMISPREVENZIONE() {
        return progmisprevenzione;
    }

    /**
     * Imposta il valore della proprietà progmisprevenzione.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPROGMISPREVENZIONE(BigInteger value) {
        this.progmisprevenzione = value;
    }

    /**
     * Recupera il valore della proprietà coditipomp.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODITIPOMP() {
        return coditipomp;
    }

    /**
     * Imposta il valore della proprietà coditipomp.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODITIPOMP(String value) {
        this.coditipomp = value;
    }

    /**
     * Recupera il valore della proprietà duratamisura.
     * 
     * @return
     *     possible object is
     *     {@link DURATA }
     *     
     */
    public DURATA getDURATAMISURA() {
        return duratamisura;
    }

    /**
     * Imposta il valore della proprietà duratamisura.
     * 
     * @param value
     *     allowed object is
     *     {@link DURATA }
     *     
     */
    public void setDURATAMISURA(DURATA value) {
        this.duratamisura = value;
    }

    /**
     * Recupera il valore della proprietà importo.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIMPORTO() {
        return importo;
    }

    /**
     * Imposta il valore della proprietà importo.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIMPORTO(BigDecimal value) {
        this.importo = value;
    }

}
