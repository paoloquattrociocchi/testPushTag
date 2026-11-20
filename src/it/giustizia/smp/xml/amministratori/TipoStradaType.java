
package it.giustizia.smp.xml.amministratori;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipoStradaType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="tipoStradaType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Notifica"/>
 *     &lt;enumeration value="ALZAIA"/>
 *     &lt;enumeration value="ALZATA"/>
 *     &lt;enumeration value="ANDRONA"/>
 *     &lt;enumeration value="ARCHIVOLTO"/>
 *     &lt;enumeration value="ARCO"/>
 *     &lt;enumeration value="ASCENSORE"/>
 *     &lt;enumeration value="ATRIO"/>
 *     &lt;enumeration value="BACINO"/>
 *     &lt;enumeration value="BAGLIO"/>
 *     &lt;enumeration value="BANCHINA"/>
 *     &lt;enumeration value="BASTIONI"/>
 *     &lt;enumeration value="BORGATA"/>
 *     &lt;enumeration value="BORGHETTO"/>
 *     &lt;enumeration value="BORGO"/>
 *     &lt;enumeration value="CALATA"/>
 *     &lt;enumeration value="CALLE"/>
 *     &lt;enumeration value="CALLESELLA"/>
 *     &lt;enumeration value="CAMPASSO"/>
 *     &lt;enumeration value="CAMPAZZO"/>
 *     &lt;enumeration value="CAMPIELLO"/>
 *     &lt;enumeration value="CAMPO"/>
 *     &lt;enumeration value="CASALE"/>
 *     &lt;enumeration value="CASE"/>
 *     &lt;enumeration value="CAVA"/>
 *     &lt;enumeration value="CAVALCAVIA"/>
 *     &lt;enumeration value="CHIASSO"/>
 *     &lt;enumeration value="CIRCONVALLAZIONE"/>
 *     &lt;enumeration value="CLIVIO"/>
 *     &lt;enumeration value="CONTRADA"/>
 *     &lt;enumeration value="CORSETTO"/>
 *     &lt;enumeration value="CORSO"/>
 *     &lt;enumeration value="CORTE"/>
 *     &lt;enumeration value="CORTICELLA"/>
 *     &lt;enumeration value="CORTILE"/>
 *     &lt;enumeration value="CUPA"/>
 *     &lt;enumeration value="DIRAMAZIONE"/>
 *     &lt;enumeration value="DISCESA"/>
 *     &lt;enumeration value="FONDACO"/>
 *     &lt;enumeration value="FONDAMENTA"/>
 *     &lt;enumeration value="FONDO"/>
 *     &lt;enumeration value="FORO"/>
 *     &lt;enumeration value="FRAZIONE"/>
 *     &lt;enumeration value="GALLERIA"/>
 *     &lt;enumeration value="GIARDINO"/>
 *     &lt;enumeration value="GRADINI"/>
 *     &lt;enumeration value="GRADONI"/>
 *     &lt;enumeration value="LARGO"/>
 *     &lt;enumeration value="LISTA"/>
 *     &lt;enumeration value="LOCALITA`"/>
 *     &lt;enumeration value="LOGGE"/>
 *     &lt;enumeration value="LUNGADIGE"/>
 *     &lt;enumeration value="LUNGARGINE"/>
 *     &lt;enumeration value="LUNGARNO"/>
 *     &lt;enumeration value="LUNGODORA"/>
 *     &lt;enumeration value="LUNGOMARE"/>
 *     &lt;enumeration value="LUNGOPO"/>
 *     &lt;enumeration value="LUNGOSTURA"/>
 *     &lt;enumeration value="LUNGOTEVERE"/>
 *     &lt;enumeration value="MASSERIA"/>
 *     &lt;enumeration value="MERCATO"/>
 *     &lt;enumeration value="MOLO"/>
 *     &lt;enumeration value="MULATTIERA"/>
 *     &lt;enumeration value="MURA"/>
 *     &lt;enumeration value="PARCO"/>
 *     &lt;enumeration value="PASSAGGIO"/>
 *     &lt;enumeration value="PASSEGGIATA"/>
 *     &lt;enumeration value="PASSETTO"/>
 *     &lt;enumeration value="PASSO"/>
 *     &lt;enumeration value="PIAZZA"/>
 *     &lt;enumeration value="PIAZZALE"/>
 *     &lt;enumeration value="PIAZZETTA"/>
 *     &lt;enumeration value="PISCINA"/>
 *     &lt;enumeration value="PONTE"/>
 *     &lt;enumeration value="PONTILE"/>
 *     &lt;enumeration value="PORTA"/>
 *     &lt;enumeration value="PORTICO"/>
 *     &lt;enumeration value="PORTO"/>
 *     &lt;enumeration value="PROLUNGAMENTO"/>
 *     &lt;enumeration value="QUARTIERE"/>
 *     &lt;enumeration value="RACCORDO"/>
 *     &lt;enumeration value="RAMI"/>
 *     &lt;enumeration value="RAMO"/>
 *     &lt;enumeration value="RAMPA"/>
 *     &lt;enumeration value="RAMPE"/>
 *     &lt;enumeration value="RECINTO"/>
 *     &lt;enumeration value="RIONE"/>
 *     &lt;enumeration value="RIPA"/>
 *     &lt;enumeration value="RIVA"/>
 *     &lt;enumeration value="RIVIERA"/>
 *     &lt;enumeration value="ROTONDA"/>
 *     &lt;enumeration value="RUA"/>
 *     &lt;enumeration value="SALITA"/>
 *     &lt;enumeration value="SALIZZATA"/>
 *     &lt;enumeration value="SCALA"/>
 *     &lt;enumeration value="SCALE"/>
 *     &lt;enumeration value="SCALEA"/>
 *     &lt;enumeration value="SCALETTA"/>
 *     &lt;enumeration value="SCALETTE"/>
 *     &lt;enumeration value="SCALI"/>
 *     &lt;enumeration value="SCALINATA"/>
 *     &lt;enumeration value="SCALONE"/>
 *     &lt;enumeration value="SDRUCCIOLO"/>
 *     &lt;enumeration value="SENTIERO"/>
 *     &lt;enumeration value="SESTRIERI"/>
 *     &lt;enumeration value="SITO"/>
 *     &lt;enumeration value="SL"/>
 *     &lt;enumeration value="SOBBORGO"/>
 *     &lt;enumeration value="SOTTOPORTICO"/>
 *     &lt;enumeration value="SOTTOVIA"/>
 *     &lt;enumeration value="SPALTO"/>
 *     &lt;enumeration value="SPIANATA"/>
 *     &lt;enumeration value="STRADA"/>
 *     &lt;enumeration value="STRADALE"/>
 *     &lt;enumeration value="STRADONE"/>
 *     &lt;enumeration value="SUPPORTICO"/>
 *     &lt;enumeration value="SVINCOLO"/>
 *     &lt;enumeration value="TRATTO"/>
 *     &lt;enumeration value="TRAVERSA"/>
 *     &lt;enumeration value="TRAVERSAVIA"/>
 *     &lt;enumeration value="TRESANDA"/>
 *     &lt;enumeration value="TRONCO"/>
 *     &lt;enumeration value="VALLONE"/>
 *     &lt;enumeration value="VARCO"/>
 *     &lt;enumeration value="VIA"/>
 *     &lt;enumeration value="VIADOTTO"/>
 *     &lt;enumeration value="VIALE"/>
 *     &lt;enumeration value="VIALETTO"/>
 *     &lt;enumeration value="VICO"/>
 *     &lt;enumeration value="VICOLETTO"/>
 *     &lt;enumeration value="VICOLO"/>
 *     &lt;enumeration value="VILLAGGIO"/>
 *     &lt;enumeration value="VIOTTOLO"/>
 *     &lt;enumeration value="VIUZZO"/>
 *     &lt;enumeration value="VO"/>
 *     &lt;enumeration value="VOLTA"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "tipoStradaType")
@XmlEnum
public enum TipoStradaType {

    @XmlEnumValue("Notifica")
    NOTIFICA("Notifica"),
    ALZAIA("ALZAIA"),
    ALZATA("ALZATA"),
    ANDRONA("ANDRONA"),
    ARCHIVOLTO("ARCHIVOLTO"),
    ARCO("ARCO"),
    ASCENSORE("ASCENSORE"),
    ATRIO("ATRIO"),
    BACINO("BACINO"),
    BAGLIO("BAGLIO"),
    BANCHINA("BANCHINA"),
    BASTIONI("BASTIONI"),
    BORGATA("BORGATA"),
    BORGHETTO("BORGHETTO"),
    BORGO("BORGO"),
    CALATA("CALATA"),
    CALLE("CALLE"),
    CALLESELLA("CALLESELLA"),
    CAMPASSO("CAMPASSO"),
    CAMPAZZO("CAMPAZZO"),
    CAMPIELLO("CAMPIELLO"),
    CAMPO("CAMPO"),
    CASALE("CASALE"),
    CASE("CASE"),
    CAVA("CAVA"),
    CAVALCAVIA("CAVALCAVIA"),
    CHIASSO("CHIASSO"),
    CIRCONVALLAZIONE("CIRCONVALLAZIONE"),
    CLIVIO("CLIVIO"),
    CONTRADA("CONTRADA"),
    CORSETTO("CORSETTO"),
    CORSO("CORSO"),
    CORTE("CORTE"),
    CORTICELLA("CORTICELLA"),
    CORTILE("CORTILE"),
    CUPA("CUPA"),
    DIRAMAZIONE("DIRAMAZIONE"),
    DISCESA("DISCESA"),
    FONDACO("FONDACO"),
    FONDAMENTA("FONDAMENTA"),
    FONDO("FONDO"),
    FORO("FORO"),
    FRAZIONE("FRAZIONE"),
    GALLERIA("GALLERIA"),
    GIARDINO("GIARDINO"),
    GRADINI("GRADINI"),
    GRADONI("GRADONI"),
    LARGO("LARGO"),
    LISTA("LISTA"),
    @XmlEnumValue("LOCALITA`")
    LOCALITA("LOCALITA`"),
    LOGGE("LOGGE"),
    LUNGADIGE("LUNGADIGE"),
    LUNGARGINE("LUNGARGINE"),
    LUNGARNO("LUNGARNO"),
    LUNGODORA("LUNGODORA"),
    LUNGOMARE("LUNGOMARE"),
    LUNGOPO("LUNGOPO"),
    LUNGOSTURA("LUNGOSTURA"),
    LUNGOTEVERE("LUNGOTEVERE"),
    MASSERIA("MASSERIA"),
    MERCATO("MERCATO"),
    MOLO("MOLO"),
    MULATTIERA("MULATTIERA"),
    MURA("MURA"),
    PARCO("PARCO"),
    PASSAGGIO("PASSAGGIO"),
    PASSEGGIATA("PASSEGGIATA"),
    PASSETTO("PASSETTO"),
    PASSO("PASSO"),
    PIAZZA("PIAZZA"),
    PIAZZALE("PIAZZALE"),
    PIAZZETTA("PIAZZETTA"),
    PISCINA("PISCINA"),
    PONTE("PONTE"),
    PONTILE("PONTILE"),
    PORTA("PORTA"),
    PORTICO("PORTICO"),
    PORTO("PORTO"),
    PROLUNGAMENTO("PROLUNGAMENTO"),
    QUARTIERE("QUARTIERE"),
    RACCORDO("RACCORDO"),
    RAMI("RAMI"),
    RAMO("RAMO"),
    RAMPA("RAMPA"),
    RAMPE("RAMPE"),
    RECINTO("RECINTO"),
    RIONE("RIONE"),
    RIPA("RIPA"),
    RIVA("RIVA"),
    RIVIERA("RIVIERA"),
    ROTONDA("ROTONDA"),
    RUA("RUA"),
    SALITA("SALITA"),
    SALIZZATA("SALIZZATA"),
    SCALA("SCALA"),
    SCALE("SCALE"),
    SCALEA("SCALEA"),
    SCALETTA("SCALETTA"),
    SCALETTE("SCALETTE"),
    SCALI("SCALI"),
    SCALINATA("SCALINATA"),
    SCALONE("SCALONE"),
    SDRUCCIOLO("SDRUCCIOLO"),
    SENTIERO("SENTIERO"),
    SESTRIERI("SESTRIERI"),
    SITO("SITO"),
    SL("SL"),
    SOBBORGO("SOBBORGO"),
    SOTTOPORTICO("SOTTOPORTICO"),
    SOTTOVIA("SOTTOVIA"),
    SPALTO("SPALTO"),
    SPIANATA("SPIANATA"),
    STRADA("STRADA"),
    STRADALE("STRADALE"),
    STRADONE("STRADONE"),
    SUPPORTICO("SUPPORTICO"),
    SVINCOLO("SVINCOLO"),
    TRATTO("TRATTO"),
    TRAVERSA("TRAVERSA"),
    TRAVERSAVIA("TRAVERSAVIA"),
    TRESANDA("TRESANDA"),
    TRONCO("TRONCO"),
    VALLONE("VALLONE"),
    VARCO("VARCO"),
    VIA("VIA"),
    VIADOTTO("VIADOTTO"),
    VIALE("VIALE"),
    VIALETTO("VIALETTO"),
    VICO("VICO"),
    VICOLETTO("VICOLETTO"),
    VICOLO("VICOLO"),
    VILLAGGIO("VILLAGGIO"),
    VIOTTOLO("VIOTTOLO"),
    VIUZZO("VIUZZO"),
    VO("VO"),
    VOLTA("VOLTA");
    private final String value;

    TipoStradaType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoStradaType fromValue(String v) {
        for (TipoStradaType c: TipoStradaType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
