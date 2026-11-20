
package it.giustizia.smp.xml.amministratori;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipoFinanziarioType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="tipoFinanziarioType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Finanziario"/>
 *     &lt;enumeration value="DenaroContante"/>
 *     &lt;enumeration value="Denaro"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "tipoFinanziarioType")
@XmlEnum
public enum TipoFinanziarioType {

    @XmlEnumValue("Finanziario")
    FINANZIARIO("Finanziario"),
    @XmlEnumValue("DenaroContante")
    DENARO_CONTANTE("DenaroContante"),
    @XmlEnumValue("Denaro")
    DENARO("Denaro");
    private final String value;

    TipoFinanziarioType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoFinanziarioType fromValue(String v) {
        for (TipoFinanziarioType c: TipoFinanziarioType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
