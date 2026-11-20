
package it.giustizia.gestorelocale.accettazione.servizi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import it.giustizia.gestorelocale.accettazione.EventoDeposito;


/**
 * <p>Java class for scriviEventoDeposito complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="scriviEventoDeposito">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="evento" type="{http://www.giustizia.it/gestorelocale/accettazione}eventoDeposito" minOccurs="0"/>
 *         &lt;element name="ultimoEvento" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "scriviEventoDeposito", propOrder = {
    "evento",
    "ultimoEvento"
})
public class ScriviEventoDeposito {

    protected EventoDeposito evento;
    protected boolean ultimoEvento;

    /**
     * Gets the value of the evento property.
     * 
     * @return
     *     possible object is
     *     {@link EventoDeposito }
     *     
     */
    public EventoDeposito getEvento() {
        return evento;
    }

    /**
     * Sets the value of the evento property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventoDeposito }
     *     
     */
    public void setEvento(EventoDeposito value) {
        this.evento = value;
    }

    /**
     * Gets the value of the ultimoEvento property.
     * 
     */
    public boolean isUltimoEvento() {
        return ultimoEvento;
    }

    /**
     * Sets the value of the ultimoEvento property.
     * 
     */
    public void setUltimoEvento(boolean value) {
        this.ultimoEvento = value;
    }

}
