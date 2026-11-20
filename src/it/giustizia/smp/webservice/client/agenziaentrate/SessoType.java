
package it.giustizia.smp.webservice.client.agenziaentrate;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for sessoType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="sessoType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="M"/>
 *     &lt;enumeration value="F"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "sessoType", namespace = "http://types.verifica.anagrafica")
@XmlEnum
public enum SessoType {

    M,
    F;

    public String value() {
        return name();
    }

    public static SessoType fromValue(String v) {
        return valueOf(v);
    }

}
