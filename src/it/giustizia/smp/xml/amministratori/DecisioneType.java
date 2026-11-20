
package it.giustizia.smp.xml.amministratori;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for decisioneType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="decisioneType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Perdita efficacia sequestro"/>
 *     &lt;enumeration value="Sequestro non convalidato"/>
 *     &lt;enumeration value="Sospensione del passaporto o documenti equipollenti"/>
 *     &lt;enumeration value="Sorveglianza speciale della P.S."/>
 *     &lt;enumeration value="Sorveglianza speciale della P.S. con obbligo di soggiorno"/>
 *     &lt;enumeration value="Sorveglianza speciale della P.S. con divieto di soggiorno"/>
 *     &lt;enumeration value="Sorveglianza speciale della P.S."/>
 *     &lt;enumeration value="Sorveglianza speciale della P.S. con obbligo di soggiorno"/>
 *     &lt;enumeration value="Sorveglianza speciale della P.S. con divieto di soggiorno"/>
 *     &lt;enumeration value="Cauzione (art. 31 D.Lgs. 159/2011)"/>
 *     &lt;enumeration value="Art. 67 D.Lgs. 159/2011"/>
 *     &lt;enumeration value="Prescrizioni previste dall'art. 8 comma 3 D.Lgs. 159/2011"/>
 *     &lt;enumeration value="Prescrizioni previste dall'art. 8 comma 4 D.Lgs. 159/2011"/>
 *     &lt;enumeration value="Ulteriori prescriz. previste dall'art.8 c. 5 D.Lgs. 159/2011"/>
 *     &lt;enumeration value="Prescrizioni previste dall'art. 8 comma 6 D.Lgs. 159/2011"/>
 *     &lt;enumeration value="Confisca previo sequestro"/>
 *     &lt;enumeration value="Sequestro"/>
 *     &lt;enumeration value="Sequestro urgente"/>
 *     &lt;enumeration value="Sospensione temporanea dall'amministrazione dei beni"/>
 *     &lt;enumeration value="Confisca"/>
 *     &lt;enumeration value="Confisca previo sequestro urgente"/>
 *     &lt;enumeration value="Confisca previo sequestro anticipato"/>
 *     &lt;enumeration value="Sequestro anticipato"/>
 *     &lt;enumeration value="Revoca sequestro"/>
 *     &lt;enumeration value="Opposizione ai divieti del questore (art.3 c.6 D.Lgs. 159/2011)"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "decisioneType")
@XmlEnum
public enum DecisioneType {

    @XmlEnumValue("Perdita efficacia sequestro")
    PERDITA_EFFICACIA_SEQUESTRO("Perdita efficacia sequestro"),
    @XmlEnumValue("Sequestro non convalidato")
    SEQUESTRO_NON_CONVALIDATO("Sequestro non convalidato"),
    @XmlEnumValue("Sospensione del passaporto o documenti equipollenti")
    SOSPENSIONE_DEL_PASSAPORTO_O_DOCUMENTI_EQUIPOLLENTI("Sospensione del passaporto o documenti equipollenti"),
    @XmlEnumValue("Sorveglianza speciale della P.S.")
    SORVEGLIANZA_SPECIALE_DELLA_P_S("Sorveglianza speciale della P.S."),
    @XmlEnumValue("Sorveglianza speciale della P.S. con obbligo di soggiorno")
    SORVEGLIANZA_SPECIALE_DELLA_P_S_CON_OBBLIGO_DI_SOGGIORNO("Sorveglianza speciale della P.S. con obbligo di soggiorno"),
    @XmlEnumValue("Sorveglianza speciale della P.S. con divieto di soggiorno")
    SORVEGLIANZA_SPECIALE_DELLA_P_S_CON_DIVIETO_DI_SOGGIORNO("Sorveglianza speciale della P.S. con divieto di soggiorno"),
    @XmlEnumValue("Cauzione (art. 31 D.Lgs. 159/2011)")
    CAUZIONE_ART_31_D_LGS_159_2011("Cauzione (art. 31 D.Lgs. 159/2011)"),
    @XmlEnumValue("Art. 67 D.Lgs. 159/2011")
    ART_67_D_LGS_159_2011("Art. 67 D.Lgs. 159/2011"),
    @XmlEnumValue("Prescrizioni previste dall'art. 8 comma 3 D.Lgs. 159/2011")
    PRESCRIZIONI_PREVISTE_DALL_ART_8_COMMA_3_D_LGS_159_2011("Prescrizioni previste dall'art. 8 comma 3 D.Lgs. 159/2011"),
    @XmlEnumValue("Prescrizioni previste dall'art. 8 comma 4 D.Lgs. 159/2011")
    PRESCRIZIONI_PREVISTE_DALL_ART_8_COMMA_4_D_LGS_159_2011("Prescrizioni previste dall'art. 8 comma 4 D.Lgs. 159/2011"),
    @XmlEnumValue("Ulteriori prescriz. previste dall'art.8 c. 5 D.Lgs. 159/2011")
    ULTERIORI_PRESCRIZ_PREVISTE_DALL_ART_8_C_5_D_LGS_159_2011("Ulteriori prescriz. previste dall'art.8 c. 5 D.Lgs. 159/2011"),
    @XmlEnumValue("Prescrizioni previste dall'art. 8 comma 6 D.Lgs. 159/2011")
    PRESCRIZIONI_PREVISTE_DALL_ART_8_COMMA_6_D_LGS_159_2011("Prescrizioni previste dall'art. 8 comma 6 D.Lgs. 159/2011"),
    @XmlEnumValue("Confisca previo sequestro")
    CONFISCA_PREVIO_SEQUESTRO("Confisca previo sequestro"),
    @XmlEnumValue("Sequestro")
    SEQUESTRO("Sequestro"),
    @XmlEnumValue("Sequestro urgente")
    SEQUESTRO_URGENTE("Sequestro urgente"),
    @XmlEnumValue("Sospensione temporanea dall'amministrazione dei beni")
    SOSPENSIONE_TEMPORANEA_DALL_AMMINISTRAZIONE_DEI_BENI("Sospensione temporanea dall'amministrazione dei beni"),
    @XmlEnumValue("Confisca")
    CONFISCA("Confisca"),
    @XmlEnumValue("Confisca previo sequestro urgente")
    CONFISCA_PREVIO_SEQUESTRO_URGENTE("Confisca previo sequestro urgente"),
    @XmlEnumValue("Confisca previo sequestro anticipato")
    CONFISCA_PREVIO_SEQUESTRO_ANTICIPATO("Confisca previo sequestro anticipato"),
    @XmlEnumValue("Sequestro anticipato")
    SEQUESTRO_ANTICIPATO("Sequestro anticipato"),
    @XmlEnumValue("Revoca sequestro")
    REVOCA_SEQUESTRO("Revoca sequestro"),
    @XmlEnumValue("Opposizione ai divieti del questore (art.3 c.6 D.Lgs. 159/2011)")
    OPPOSIZIONE_AI_DIVIETI_DEL_QUESTORE_ART_3_C_6_D_LGS_159_2011("Opposizione ai divieti del questore (art.3 c.6 D.Lgs. 159/2011)");
    private final String value;

    DecisioneType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DecisioneType fromValue(String v) {
        for (DecisioneType c: DecisioneType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
