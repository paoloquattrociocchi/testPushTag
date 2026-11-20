
package it.giustizia.smp.xml.amministratori;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipoProvvedimentoType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="tipoProvvedimentoType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Notifica"/>
 *     &lt;enumeration value="Relata di notifica"/>
 *     &lt;enumeration value="Decreto"/>
 *     &lt;enumeration value="Impugnazione"/>
 *     &lt;enumeration value="Istanza"/>
 *     &lt;enumeration value="Ricorso"/>
 *     &lt;enumeration value="Indagine"/>
 *     &lt;enumeration value="Certificato"/>
 *     &lt;enumeration value="Proposta"/>
 *     &lt;enumeration value="Esterno"/>
 *     &lt;enumeration value="Informativa"/>
 *     &lt;enumeration value="Fonte"/>
 *     &lt;enumeration value="Giudiziario generico"/>
 *     &lt;enumeration value="Atto generico"/>
 *     &lt;enumeration value="Archiviazione"/>
 *     &lt;enumeration value="Verbale"/>
 *     &lt;enumeration value="Atto esecutivo"/>
 *     &lt;enumeration value="Decreto II grado"/>
 *     &lt;enumeration value="Impugnazione II grado"/>
 *     &lt;enumeration value="Sentenza di Cassazione"/>
 *     &lt;enumeration value="Sentenza di Cassazione da CDA"/>
 *     &lt;enumeration value="Atti in archivio"/>
 *     &lt;enumeration value="Ordinanza di trasformazione"/>
 *     &lt;enumeration value="Versionamento soggetto"/>
 *     &lt;enumeration value="Istanza"/>
 *     &lt;enumeration value="Accertamento"/>
 *     &lt;enumeration value="Provvedimento"/>
 *     &lt;enumeration value="Ricevuta"/>
 *     &lt;enumeration value="Relazione dell'Amministratore"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "tipoProvvedimentoType")
@XmlEnum
public enum TipoProvvedimentoType {

    @XmlEnumValue("Notifica")
    NOTIFICA("Notifica"),
    @XmlEnumValue("Relata di notifica")
    RELATA_DI_NOTIFICA("Relata di notifica"),
    @XmlEnumValue("Decreto")
    DECRETO("Decreto"),
    @XmlEnumValue("Impugnazione")
    IMPUGNAZIONE("Impugnazione"),
    @XmlEnumValue("Istanza")
    ISTANZA("Istanza"),
    @XmlEnumValue("Ricorso")
    RICORSO("Ricorso"),
    @XmlEnumValue("Indagine")
    INDAGINE("Indagine"),
    @XmlEnumValue("Certificato")
    CERTIFICATO("Certificato"),
    @XmlEnumValue("Proposta")
    PROPOSTA("Proposta"),
    @XmlEnumValue("Esterno")
    ESTERNO("Esterno"),
    @XmlEnumValue("Informativa")
    INFORMATIVA("Informativa"),
    @XmlEnumValue("Fonte")
    FONTE("Fonte"),
    @XmlEnumValue("Giudiziario generico")
    GIUDIZIARIO_GENERICO("Giudiziario generico"),
    @XmlEnumValue("Atto generico")
    ATTO_GENERICO("Atto generico"),
    @XmlEnumValue("Archiviazione")
    ARCHIVIAZIONE("Archiviazione"),
    @XmlEnumValue("Verbale")
    VERBALE("Verbale"),
    @XmlEnumValue("Atto esecutivo")
    ATTO_ESECUTIVO("Atto esecutivo"),
    @XmlEnumValue("Decreto II grado")
    DECRETO_II_GRADO("Decreto II grado"),
    @XmlEnumValue("Impugnazione II grado")
    IMPUGNAZIONE_II_GRADO("Impugnazione II grado"),
    @XmlEnumValue("Sentenza di Cassazione")
    SENTENZA_DI_CASSAZIONE("Sentenza di Cassazione"),
    @XmlEnumValue("Sentenza di Cassazione da CDA")
    SENTENZA_DI_CASSAZIONE_DA_CDA("Sentenza di Cassazione da CDA"),
    @XmlEnumValue("Atti in archivio")
    ATTI_IN_ARCHIVIO("Atti in archivio"),
    @XmlEnumValue("Ordinanza di trasformazione")
    ORDINANZA_DI_TRASFORMAZIONE("Ordinanza di trasformazione"),
    @XmlEnumValue("Versionamento soggetto")
    VERSIONAMENTO_SOGGETTO("Versionamento soggetto"),
    @XmlEnumValue("Accertamento")
    ACCERTAMENTO("Accertamento"),
    @XmlEnumValue("Provvedimento")
    PROVVEDIMENTO("Provvedimento"),
    @XmlEnumValue("Ricevuta")
    RICEVUTA("Ricevuta"),
    @XmlEnumValue("Relazione dell'Amministratore")
    RELAZIONE_DELL_AMMINISTRATORE("Relazione dell'Amministratore");
    private final String value;

    TipoProvvedimentoType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoProvvedimentoType fromValue(String v) {
        for (TipoProvvedimentoType c: TipoProvvedimentoType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
