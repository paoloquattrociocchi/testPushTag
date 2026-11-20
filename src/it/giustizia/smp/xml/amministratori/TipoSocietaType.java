
package it.giustizia.smp.xml.amministratori;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipoSocietaType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="tipoSocietaType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="IMPRESA INDIVIDUALE"/>
 *     &lt;enumeration value="SOCIETA` IN ACCOMANDITA PER AZIONI"/>
 *     &lt;enumeration value="SOCIETA` IN ACCOMANDITA SEMPLICE"/>
 *     &lt;enumeration value="SOCIETA` IN NOME COLLETTIVO"/>
 *     &lt;enumeration value="SOCIETA` SEMPLICE"/>
 *     &lt;enumeration value="SOCIETA` PER AZIONI"/>
 *     &lt;enumeration value="SOCIETA` A RESPONSABILITA` LIMITATA"/>
 *     &lt;enumeration value="SOCIETA` COOPERATIVA"/>
 *     &lt;enumeration value="SOCIETA` DI FATTO"/>
 *     &lt;enumeration value="ALTRO"/>
 *     &lt;enumeration value="SOCIETA` COOPERATIVA A RESP. LIMITATA"/>
 *     &lt;enumeration value="ASSOCIAZIONE"/>
 *     &lt;enumeration value="CONSORZIO"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "tipoSocietaType")
@XmlEnum
public enum TipoSocietaType {

    @XmlEnumValue("IMPRESA INDIVIDUALE")
    IMPRESA_INDIVIDUALE("IMPRESA INDIVIDUALE"),
    @XmlEnumValue("SOCIETA` IN ACCOMANDITA PER AZIONI")
    SOCIETA_IN_ACCOMANDITA_PER_AZIONI("SOCIETA` IN ACCOMANDITA PER AZIONI"),
    @XmlEnumValue("SOCIETA` IN ACCOMANDITA SEMPLICE")
    SOCIETA_IN_ACCOMANDITA_SEMPLICE("SOCIETA` IN ACCOMANDITA SEMPLICE"),
    @XmlEnumValue("SOCIETA` IN NOME COLLETTIVO")
    SOCIETA_IN_NOME_COLLETTIVO("SOCIETA` IN NOME COLLETTIVO"),
    @XmlEnumValue("SOCIETA` SEMPLICE")
    SOCIETA_SEMPLICE("SOCIETA` SEMPLICE"),
    @XmlEnumValue("SOCIETA` PER AZIONI")
    SOCIETA_PER_AZIONI("SOCIETA` PER AZIONI"),
    @XmlEnumValue("SOCIETA` A RESPONSABILITA` LIMITATA")
    SOCIETA_A_RESPONSABILITA_LIMITATA("SOCIETA` A RESPONSABILITA` LIMITATA"),
    @XmlEnumValue("SOCIETA` COOPERATIVA")
    SOCIETA_COOPERATIVA("SOCIETA` COOPERATIVA"),
    @XmlEnumValue("SOCIETA` DI FATTO")
    SOCIETA_DI_FATTO("SOCIETA` DI FATTO"),
    ALTRO("ALTRO"),
    @XmlEnumValue("SOCIETA` COOPERATIVA A RESP. LIMITATA")
    SOCIETA_COOPERATIVA_A_RESP_LIMITATA("SOCIETA` COOPERATIVA A RESP. LIMITATA"),
    ASSOCIAZIONE("ASSOCIAZIONE"),
    CONSORZIO("CONSORZIO");
    private final String value;

    TipoSocietaType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoSocietaType fromValue(String v) {
        for (TipoSocietaType c: TipoSocietaType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
