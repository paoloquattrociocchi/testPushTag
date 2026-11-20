
package it.giustizia.smp.xml.amministratori;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipoCatastoType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="tipoCatastoType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="T"/>
 *     &lt;enumeration value="U"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "tipoCatastoType")
@XmlEnum
public enum TipoCatastoType {

    T,
    U;

    public String value() {
        return name();
    }

    public static TipoCatastoType fromValue(String v) {
        return valueOf(v);
    }

}
