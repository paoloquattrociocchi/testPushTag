
package it.giustizia.smp.xml.amministratori;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for categoriaBeneType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="categoriaBeneType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="UNITA` IMMOBILIARE PER USO DI ABITAZIONE E ASSIMILABILE"/>
 *     &lt;enumeration value="UNITA` IMMOBILIARE PER ALLOGGIO E USI COLLETTIVI"/>
 *     &lt;enumeration value="UNITA` IMMOBILIARE A DESTINAZIONE COMMERCIALE E INDUSTRIALE"/>
 *     &lt;enumeration value="ALTRA UNITA` IMMOBILIARE"/>
 *     &lt;enumeration value="TERRENO"/>
 *     &lt;enumeration value="DENARO"/>
 *     &lt;enumeration value="COLLEZIONE"/>
 *     &lt;enumeration value="ANIMALI"/>
 *     &lt;enumeration value="ALTRI OGGETTI"/>
 *     &lt;enumeration value="VEICOLO"/>
 *     &lt;enumeration value="BENE IMMATERIALE"/>
 *     &lt;enumeration value="TITOLO CAMBIARIO"/>
 *     &lt;enumeration value="TITOLO OBBLIGAZIONARIO O DI PRESTITO"/>
 *     &lt;enumeration value="TITOLO DI PARTECIPAZIONE"/>
 *     &lt;enumeration value="TITOLO RAPPRESENTATIVO DI MERCI"/>
 *     &lt;enumeration value="ALTRI BENI FINANZIARI"/>
 *     &lt;enumeration value="IMPRESA INDIVIDUALE"/>
 *     &lt;enumeration value="SOCIETA` A RESPONSABILITA` LIMITATA"/>
 *     &lt;enumeration value="SOCIETA` COOPERATIVA"/>
 *     &lt;enumeration value="SOCIETA` DI FATTO"/>
 *     &lt;enumeration value="SOCIETA` IN ACCOMANDITA PER AZIONI"/>
 *     &lt;enumeration value="SOCIETA` IN ACCOMANDITA SEMPLICE"/>
 *     &lt;enumeration value="SOCIETA` IN NOME COLLETTIVO"/>
 *     &lt;enumeration value="SOCIETA` PER AZIONI"/>
 *     &lt;enumeration value="SOCIETA` SEMPLICE"/>
 *     &lt;enumeration value="ALTRO"/>
 *     &lt;enumeration value="SOCIETA` COOPERATIVA A RESPONSABILITA` LIMITATA"/>
 *     &lt;enumeration value="ASSOCIAZIONE"/>
 *     &lt;enumeration value="CONSORZIO"/>
 *     &lt;enumeration value="SOCIETA` NON DEFINITA"/>
 *     &lt;enumeration value="ALTRE FORME TECNICHE DI RAPPORTI FINANZIARI"/>
 *     &lt;enumeration value="POLIZZE ASSICURATIVE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "categoriaBeneType")
@XmlEnum
public enum CategoriaBeneType {

    @XmlEnumValue("UNITA` IMMOBILIARE PER USO DI ABITAZIONE E ASSIMILABILE")
    UNITA_IMMOBILIARE_PER_USO_DI_ABITAZIONE_E_ASSIMILABILE("UNITA` IMMOBILIARE PER USO DI ABITAZIONE E ASSIMILABILE"),
    @XmlEnumValue("UNITA` IMMOBILIARE PER ALLOGGIO E USI COLLETTIVI")
    UNITA_IMMOBILIARE_PER_ALLOGGIO_E_USI_COLLETTIVI("UNITA` IMMOBILIARE PER ALLOGGIO E USI COLLETTIVI"),
    @XmlEnumValue("UNITA` IMMOBILIARE A DESTINAZIONE COMMERCIALE E INDUSTRIALE")
    UNITA_IMMOBILIARE_A_DESTINAZIONE_COMMERCIALE_E_INDUSTRIALE("UNITA` IMMOBILIARE A DESTINAZIONE COMMERCIALE E INDUSTRIALE"),
    @XmlEnumValue("ALTRA UNITA` IMMOBILIARE")
    ALTRA_UNITA_IMMOBILIARE("ALTRA UNITA` IMMOBILIARE"),
    TERRENO("TERRENO"),
    DENARO("DENARO"),
    COLLEZIONE("COLLEZIONE"),
    ANIMALI("ANIMALI"),
    @XmlEnumValue("ALTRI OGGETTI")
    ALTRI_OGGETTI("ALTRI OGGETTI"),
    VEICOLO("VEICOLO"),
    @XmlEnumValue("BENE IMMATERIALE")
    BENE_IMMATERIALE("BENE IMMATERIALE"),
    @XmlEnumValue("TITOLO CAMBIARIO")
    TITOLO_CAMBIARIO("TITOLO CAMBIARIO"),
    @XmlEnumValue("TITOLO OBBLIGAZIONARIO O DI PRESTITO")
    TITOLO_OBBLIGAZIONARIO_O_DI_PRESTITO("TITOLO OBBLIGAZIONARIO O DI PRESTITO"),
    @XmlEnumValue("TITOLO DI PARTECIPAZIONE")
    TITOLO_DI_PARTECIPAZIONE("TITOLO DI PARTECIPAZIONE"),
    @XmlEnumValue("TITOLO RAPPRESENTATIVO DI MERCI")
    TITOLO_RAPPRESENTATIVO_DI_MERCI("TITOLO RAPPRESENTATIVO DI MERCI"),
    @XmlEnumValue("ALTRI BENI FINANZIARI")
    ALTRI_BENI_FINANZIARI("ALTRI BENI FINANZIARI"),
    @XmlEnumValue("IMPRESA INDIVIDUALE")
    IMPRESA_INDIVIDUALE("IMPRESA INDIVIDUALE"),
    @XmlEnumValue("SOCIETA` A RESPONSABILITA` LIMITATA")
    SOCIETA_A_RESPONSABILITA_LIMITATA("SOCIETA` A RESPONSABILITA` LIMITATA"),
    @XmlEnumValue("SOCIETA` COOPERATIVA")
    SOCIETA_COOPERATIVA("SOCIETA` COOPERATIVA"),
    @XmlEnumValue("SOCIETA` DI FATTO")
    SOCIETA_DI_FATTO("SOCIETA` DI FATTO"),
    @XmlEnumValue("SOCIETA` IN ACCOMANDITA PER AZIONI")
    SOCIETA_IN_ACCOMANDITA_PER_AZIONI("SOCIETA` IN ACCOMANDITA PER AZIONI"),
    @XmlEnumValue("SOCIETA` IN ACCOMANDITA SEMPLICE")
    SOCIETA_IN_ACCOMANDITA_SEMPLICE("SOCIETA` IN ACCOMANDITA SEMPLICE"),
    @XmlEnumValue("SOCIETA` IN NOME COLLETTIVO")
    SOCIETA_IN_NOME_COLLETTIVO("SOCIETA` IN NOME COLLETTIVO"),
    @XmlEnumValue("SOCIETA` PER AZIONI")
    SOCIETA_PER_AZIONI("SOCIETA` PER AZIONI"),
    @XmlEnumValue("SOCIETA` SEMPLICE")
    SOCIETA_SEMPLICE("SOCIETA` SEMPLICE"),
    ALTRO("ALTRO"),
    @XmlEnumValue("SOCIETA` COOPERATIVA A RESPONSABILITA` LIMITATA")
    SOCIETA_COOPERATIVA_A_RESPONSABILITA_LIMITATA("SOCIETA` COOPERATIVA A RESPONSABILITA` LIMITATA"),
    ASSOCIAZIONE("ASSOCIAZIONE"),
    CONSORZIO("CONSORZIO"),
    @XmlEnumValue("SOCIETA` NON DEFINITA")
    SOCIETA_NON_DEFINITA("SOCIETA` NON DEFINITA"),
    @XmlEnumValue("ALTRE FORME TECNICHE DI RAPPORTI FINANZIARI")
    ALTRE_FORME_TECNICHE_DI_RAPPORTI_FINANZIARI("ALTRE FORME TECNICHE DI RAPPORTI FINANZIARI"),
    @XmlEnumValue("POLIZZE ASSICURATIVE")
    POLIZZE_ASSICURATIVE("POLIZZE ASSICURATIVE");
    private final String value;

    CategoriaBeneType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CategoriaBeneType fromValue(String v) {
        for (CategoriaBeneType c: CategoriaBeneType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
