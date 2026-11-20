
package it.giustizia.smp.xml.amministratori;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipoValutaType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="tipoValutaType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CAD"/>
 *     &lt;enumeration value="CHF"/>
 *     &lt;enumeration value="CNY"/>
 *     &lt;enumeration value="GBP"/>
 *     &lt;enumeration value="JPY"/>
 *     &lt;enumeration value="RUB"/>
 *     &lt;enumeration value="USD"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "tipoValutaType")
@XmlEnum
public enum TipoValutaType {

    CAD,
    CHF,
    CNY,
    GBP,
    JPY,
    RUB,
    USD;

    public String value() {
        return name();
    }

    public static TipoValutaType fromValue(String v) {
        return valueOf(v);
    }

}
