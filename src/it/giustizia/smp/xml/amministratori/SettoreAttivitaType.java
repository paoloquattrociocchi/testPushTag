
package it.giustizia.smp.xml.amministratori;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for settoreAttivitaType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="settoreAttivitaType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="AGRICOLTURA, CACCIA E SILVICOLTURA"/>
 *     &lt;enumeration value="PESCA, PISCICOLTURA E SERVIZI CONNESSI"/>
 *     &lt;enumeration value="ESTRAZIONE DI MINERALI"/>
 *     &lt;enumeration value="ATTIVITA` MANIFATTURIERE"/>
 *     &lt;enumeration value="PRODUZIONE E DISTRIBUZIONE DI ENERGIA ELETTRICA, GAS E ACQUA"/>
 *     &lt;enumeration value="COSTRUZIONI"/>
 *     &lt;enumeration value="COMMERCIO INGROSSO-DETTAGLIO, RIPARAZIONE VEICOLI, BENI PERSONALI, CASA"/>
 *     &lt;enumeration value="ALBERGHI E RISTORANTI"/>
 *     &lt;enumeration value="TRASPORTI, MAGAZZINAGGIO E COMUNICAZIONI"/>
 *     &lt;enumeration value="ATTIVITA` FINANZIARIE"/>
 *     &lt;enumeration value="ATTIVITA` IMMOBILIARI, NOLEGGIO, INFORMATICA, RICERCA, SERVIZI ALLE IMPRESE"/>
 *     &lt;enumeration value="AMMINISTRAZIONE PUBBLICA"/>
 *     &lt;enumeration value="ISTRUZIONE"/>
 *     &lt;enumeration value="SANITA` E ASSISTENZA SOCIALE"/>
 *     &lt;enumeration value="ALTRI SERVIZI PUBBLICI, SOCIALI E PERSONALI"/>
 *     &lt;enumeration value="ATTIVITA` SVOLTE DA FAMIGLIE E CONVIVENZE"/>
 *     &lt;enumeration value="ORGANIZZAZIONI ED ORGANISMI EXTRATERRITORIALI"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "settoreAttivitaType")
@XmlEnum
public enum SettoreAttivitaType {

    @XmlEnumValue("AGRICOLTURA, CACCIA E SILVICOLTURA")
    AGRICOLTURA_CACCIA_E_SILVICOLTURA("AGRICOLTURA, CACCIA E SILVICOLTURA"),
    @XmlEnumValue("PESCA, PISCICOLTURA E SERVIZI CONNESSI")
    PESCA_PISCICOLTURA_E_SERVIZI_CONNESSI("PESCA, PISCICOLTURA E SERVIZI CONNESSI"),
    @XmlEnumValue("ESTRAZIONE DI MINERALI")
    ESTRAZIONE_DI_MINERALI("ESTRAZIONE DI MINERALI"),
    @XmlEnumValue("ATTIVITA` MANIFATTURIERE")
    ATTIVITA_MANIFATTURIERE("ATTIVITA` MANIFATTURIERE"),
    @XmlEnumValue("PRODUZIONE E DISTRIBUZIONE DI ENERGIA ELETTRICA, GAS E ACQUA")
    PRODUZIONE_E_DISTRIBUZIONE_DI_ENERGIA_ELETTRICA_GAS_E_ACQUA("PRODUZIONE E DISTRIBUZIONE DI ENERGIA ELETTRICA, GAS E ACQUA"),
    COSTRUZIONI("COSTRUZIONI"),
    @XmlEnumValue("COMMERCIO INGROSSO-DETTAGLIO, RIPARAZIONE VEICOLI, BENI PERSONALI, CASA")
    COMMERCIO_INGROSSO_DETTAGLIO_RIPARAZIONE_VEICOLI_BENI_PERSONALI_CASA("COMMERCIO INGROSSO-DETTAGLIO, RIPARAZIONE VEICOLI, BENI PERSONALI, CASA"),
    @XmlEnumValue("ALBERGHI E RISTORANTI")
    ALBERGHI_E_RISTORANTI("ALBERGHI E RISTORANTI"),
    @XmlEnumValue("TRASPORTI, MAGAZZINAGGIO E COMUNICAZIONI")
    TRASPORTI_MAGAZZINAGGIO_E_COMUNICAZIONI("TRASPORTI, MAGAZZINAGGIO E COMUNICAZIONI"),
    @XmlEnumValue("ATTIVITA` FINANZIARIE")
    ATTIVITA_FINANZIARIE("ATTIVITA` FINANZIARIE"),
    @XmlEnumValue("ATTIVITA` IMMOBILIARI, NOLEGGIO, INFORMATICA, RICERCA, SERVIZI ALLE IMPRESE")
    ATTIVITA_IMMOBILIARI_NOLEGGIO_INFORMATICA_RICERCA_SERVIZI_ALLE_IMPRESE("ATTIVITA` IMMOBILIARI, NOLEGGIO, INFORMATICA, RICERCA, SERVIZI ALLE IMPRESE"),
    @XmlEnumValue("AMMINISTRAZIONE PUBBLICA")
    AMMINISTRAZIONE_PUBBLICA("AMMINISTRAZIONE PUBBLICA"),
    ISTRUZIONE("ISTRUZIONE"),
    @XmlEnumValue("SANITA` E ASSISTENZA SOCIALE")
    SANITA_E_ASSISTENZA_SOCIALE("SANITA` E ASSISTENZA SOCIALE"),
    @XmlEnumValue("ALTRI SERVIZI PUBBLICI, SOCIALI E PERSONALI")
    ALTRI_SERVIZI_PUBBLICI_SOCIALI_E_PERSONALI("ALTRI SERVIZI PUBBLICI, SOCIALI E PERSONALI"),
    @XmlEnumValue("ATTIVITA` SVOLTE DA FAMIGLIE E CONVIVENZE")
    ATTIVITA_SVOLTE_DA_FAMIGLIE_E_CONVIVENZE("ATTIVITA` SVOLTE DA FAMIGLIE E CONVIVENZE"),
    @XmlEnumValue("ORGANIZZAZIONI ED ORGANISMI EXTRATERRITORIALI")
    ORGANIZZAZIONI_ED_ORGANISMI_EXTRATERRITORIALI("ORGANIZZAZIONI ED ORGANISMI EXTRATERRITORIALI");
    private final String value;

    SettoreAttivitaType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SettoreAttivitaType fromValue(String v) {
        for (SettoreAttivitaType c: SettoreAttivitaType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
