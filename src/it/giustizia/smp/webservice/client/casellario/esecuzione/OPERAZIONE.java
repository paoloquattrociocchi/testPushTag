
package it.giustizia.smp.webservice.client.casellario.esecuzione;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OPERAZIONE.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="OPERAZIONE">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;maxLength value="10"/>
 *     &lt;enumeration value="INSERT"/>
 *     &lt;enumeration value="UPDATE"/>
 *     &lt;enumeration value="DELETE"/>
 *     &lt;enumeration value="SKIP-INSERT-S"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "OPERAZIONE")
@XmlEnum
public enum OPERAZIONE {

    INSERT("INSERT"),
    UPDATE("UPDATE"),
    DELETE("DELETE"),
    @XmlEnumValue("SKIP-INSERT-S")
    SKIP_INSERT_S("SKIP-INSERT-S");
    private final String value;

    OPERAZIONE(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static OPERAZIONE fromValue(String v) {
        for (OPERAZIONE c: OPERAZIONE.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
