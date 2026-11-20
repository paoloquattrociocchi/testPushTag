
package it.giustizia.smp.xml.amministratori;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipoBeneType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="tipoBeneType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="IMMOBILE"/>
 *     &lt;enumeration value="MOBILE"/>
 *     &lt;enumeration value="MOBILE REGISTRATO"/>
 *     &lt;enumeration value="FINANZIARIO"/>
 *     &lt;enumeration value="AZIENDA"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "tipoBeneType")
@XmlEnum
public enum TipoBeneType {

    IMMOBILE("IMMOBILE"),
    MOBILE("MOBILE"),
    @XmlEnumValue("MOBILE REGISTRATO")
    MOBILE_REGISTRATO("MOBILE REGISTRATO"),
    FINANZIARIO("FINANZIARIO"),
    AZIENDA("AZIENDA");
    private final String value;

    TipoBeneType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoBeneType fromValue(String v) {
        for (TipoBeneType c: TipoBeneType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
