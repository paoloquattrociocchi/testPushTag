
package it.giustizia.smp.webservice.client.reginde;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per elencoPaginatoSoggetti complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="elencoPaginatoSoggetti">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="da" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="count" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "elencoPaginatoSoggetti", propOrder = {
    "da",
    "count"
})
public class ElencoPaginatoSoggetti {

    protected int da;
    protected int count;

    /**
     * Recupera il valore della proprietà da.
     * 
     */
    public int getDa() {
        return da;
    }

    /**
     * Imposta il valore della proprietà da.
     * 
     */
    public void setDa(int value) {
        this.da = value;
    }

    /**
     * Recupera il valore della proprietà count.
     * 
     */
    public int getCount() {
        return count;
    }

    /**
     * Imposta il valore della proprietà count.
     * 
     */
    public void setCount(int value) {
        this.count = value;
    }

}
