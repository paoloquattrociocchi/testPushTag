//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.09.17 alle 11:32:38 AM CEST 
//


package it.giustizia.smp.webservice.client.casellario;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per OPERAZIONE.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="OPERAZIONE">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;maxLength value="10"/>
 *     &lt;enumeration value="INSERT"/>
 *     &lt;enumeration value="UPDATE"/>
 *     &lt;enumeration value="DELETE"/>
 *     &lt;enumeration value="READ"/>
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
    READ("READ"),
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
