
package it.giustizia.smp.xml.amministratori;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for categoriaCatastaleType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="categoriaCatastaleType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="A1"/>
 *     &lt;enumeration value="A2"/>
 *     &lt;enumeration value="A3"/>
 *     &lt;enumeration value="A4"/>
 *     &lt;enumeration value="A5"/>
 *     &lt;enumeration value="A6"/>
 *     &lt;enumeration value="A7"/>
 *     &lt;enumeration value="A8"/>
 *     &lt;enumeration value="A9"/>
 *     &lt;enumeration value="A10"/>
 *     &lt;enumeration value="A11"/>
 *     &lt;enumeration value="B1"/>
 *     &lt;enumeration value="B2"/>
 *     &lt;enumeration value="B3"/>
 *     &lt;enumeration value="B4"/>
 *     &lt;enumeration value="B5"/>
 *     &lt;enumeration value="B6"/>
 *     &lt;enumeration value="B7"/>
 *     &lt;enumeration value="B8"/>
 *     &lt;enumeration value="C1"/>
 *     &lt;enumeration value="C2"/>
 *     &lt;enumeration value="C3"/>
 *     &lt;enumeration value="C4"/>
 *     &lt;enumeration value="C5"/>
 *     &lt;enumeration value="C6"/>
 *     &lt;enumeration value="C7"/>
 *     &lt;enumeration value="D1"/>
 *     &lt;enumeration value="D2"/>
 *     &lt;enumeration value="D3"/>
 *     &lt;enumeration value="D4"/>
 *     &lt;enumeration value="D5"/>
 *     &lt;enumeration value="D6"/>
 *     &lt;enumeration value="D7"/>
 *     &lt;enumeration value="D8"/>
 *     &lt;enumeration value="D9"/>
 *     &lt;enumeration value="E1"/>
 *     &lt;enumeration value="E2"/>
 *     &lt;enumeration value="E3"/>
 *     &lt;enumeration value="E4"/>
 *     &lt;enumeration value="E5"/>
 *     &lt;enumeration value="E6"/>
 *     &lt;enumeration value="E7"/>
 *     &lt;enumeration value="E8"/>
 *     &lt;enumeration value="E9"/>
 *     &lt;enumeration value="AP"/>
 *     &lt;enumeration value="A"/>
 *     &lt;enumeration value="F"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "categoriaCatastaleType")
@XmlEnum
public enum CategoriaCatastaleType {

    @XmlEnumValue("A1")
    A_1("A1"),
    @XmlEnumValue("A2")
    A_2("A2"),
    @XmlEnumValue("A3")
    A_3("A3"),
    @XmlEnumValue("A4")
    A_4("A4"),
    @XmlEnumValue("A5")
    A_5("A5"),
    @XmlEnumValue("A6")
    A_6("A6"),
    @XmlEnumValue("A7")
    A_7("A7"),
    @XmlEnumValue("A8")
    A_8("A8"),
    @XmlEnumValue("A9")
    A_9("A9"),
    @XmlEnumValue("A10")
    A_10("A10"),
    @XmlEnumValue("A11")
    A_11("A11"),
    @XmlEnumValue("B1")
    B_1("B1"),
    @XmlEnumValue("B2")
    B_2("B2"),
    @XmlEnumValue("B3")
    B_3("B3"),
    @XmlEnumValue("B4")
    B_4("B4"),
    @XmlEnumValue("B5")
    B_5("B5"),
    @XmlEnumValue("B6")
    B_6("B6"),
    @XmlEnumValue("B7")
    B_7("B7"),
    @XmlEnumValue("B8")
    B_8("B8"),
    @XmlEnumValue("C1")
    C_1("C1"),
    @XmlEnumValue("C2")
    C_2("C2"),
    @XmlEnumValue("C3")
    C_3("C3"),
    @XmlEnumValue("C4")
    C_4("C4"),
    @XmlEnumValue("C5")
    C_5("C5"),
    @XmlEnumValue("C6")
    C_6("C6"),
    @XmlEnumValue("C7")
    C_7("C7"),
    @XmlEnumValue("D1")
    D_1("D1"),
    @XmlEnumValue("D2")
    D_2("D2"),
    @XmlEnumValue("D3")
    D_3("D3"),
    @XmlEnumValue("D4")
    D_4("D4"),
    @XmlEnumValue("D5")
    D_5("D5"),
    @XmlEnumValue("D6")
    D_6("D6"),
    @XmlEnumValue("D7")
    D_7("D7"),
    @XmlEnumValue("D8")
    D_8("D8"),
    @XmlEnumValue("D9")
    D_9("D9"),
    @XmlEnumValue("E1")
    E_1("E1"),
    @XmlEnumValue("E2")
    E_2("E2"),
    @XmlEnumValue("E3")
    E_3("E3"),
    @XmlEnumValue("E4")
    E_4("E4"),
    @XmlEnumValue("E5")
    E_5("E5"),
    @XmlEnumValue("E6")
    E_6("E6"),
    @XmlEnumValue("E7")
    E_7("E7"),
    @XmlEnumValue("E8")
    E_8("E8"),
    @XmlEnumValue("E9")
    E_9("E9"),
    AP("AP"),
    A("A"),
    F("F");
    private final String value;

    CategoriaCatastaleType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CategoriaCatastaleType fromValue(String v) {
        for (CategoriaCatastaleType c: CategoriaCatastaleType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
