//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2015.03.10 alle 02:34:02 PM CET 
//


package it.giustizia.smp.webservice.client.glmp.servizitrasmissione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per IdFascicolo complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="IdFascicolo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="anno" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="idRepository" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idSgr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numero" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="registro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="subProcedimento" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ufficio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdFascicolo", namespace = "http://www.giustizia.it/gestorelocale/tipi", propOrder = {
    "anno",
    "idRepository",
    "idSgr",
    "numero",
    "registro",
    "subProcedimento",
    "ufficio"
})
public class IdFascicolo {

    protected int anno;
    protected String idRepository;
    protected String idSgr;
    protected int numero;
    protected String registro;
    protected int subProcedimento;
    protected String ufficio;

    /**
     * Recupera il valore della proprietà anno.
     * 
     */
    public int getAnno() {
        return anno;
    }

    /**
     * Imposta il valore della proprietà anno.
     * 
     */
    public void setAnno(int value) {
        this.anno = value;
    }

    /**
     * Recupera il valore della proprietà idRepository.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdRepository() {
        return idRepository;
    }

    /**
     * Imposta il valore della proprietà idRepository.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdRepository(String value) {
        this.idRepository = value;
    }

    /**
     * Recupera il valore della proprietà idSgr.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdSgr() {
        return idSgr;
    }

    /**
     * Imposta il valore della proprietà idSgr.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdSgr(String value) {
        this.idSgr = value;
    }

    /**
     * Recupera il valore della proprietà numero.
     * 
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Imposta il valore della proprietà numero.
     * 
     */
    public void setNumero(int value) {
        this.numero = value;
    }

    /**
     * Recupera il valore della proprietà registro.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegistro() {
        return registro;
    }

    /**
     * Imposta il valore della proprietà registro.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegistro(String value) {
        this.registro = value;
    }

    /**
     * Recupera il valore della proprietà subProcedimento.
     * 
     */
    public int getSubProcedimento() {
        return subProcedimento;
    }

    /**
     * Imposta il valore della proprietà subProcedimento.
     * 
     */
    public void setSubProcedimento(int value) {
        this.subProcedimento = value;
    }

    /**
     * Recupera il valore della proprietà ufficio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUfficio() {
        return ufficio;
    }

    /**
     * Imposta il valore della proprietà ufficio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUfficio(String value) {
        this.ufficio = value;
    }

}
