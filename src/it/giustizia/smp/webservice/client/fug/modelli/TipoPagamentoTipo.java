package it.giustizia.smp.webservice.client.fug.modelli;

/**
 * Valori ammessi:<br>
 * <ul>
 * <li>CRO = BONIFICO</li>
 * <li>POYP = BONIFICO POSTALE</li>
 * <li>TRN = BONIFICO SEPA</li>
 * <li>VCY = BOLLETTINO VCY</li>
 * <li>VCYL = BOLLETTINO VCYL</li>
 * <li>VEY = DISTINTA DI VERSAMENTO VEY</li>
 * <li>LVE = DISTINTA DI VERSAMENTO LVE</li>
 * <li>PGR = POSTAGIRO</li>
 * </ul>
 * @param tipoPagamento valore
 */
public enum TipoPagamentoTipo {

	CRO("CRO", "BONIFICO"),
	POYP("POYP", "BONIFICO POSTALE"),
	TRN("TRN", "BONIFICO SEPA"),
	VCY("VCY", "BOLLETTINO VCY"),
	VCYL("VCYL", "BOLLETTINO VCYL"),
	VEY("VEY", "DISTINTA DI VERSAMENTO VEY"),
	LVE("LVE", "DISTINTA DI VERSAMENTO LVE"),
	PGR("PGR", "POSTAGIRO");
  private final String value;
  private final String desc;

  TipoPagamentoTipo(String v, String desc) {
      this.value = v;
      this.desc = desc;
  }

  public String value() {
      return this.value;
  }

  public String desc() {
    return this.desc;
}

  public static TipoPagamentoTipo fromValue(String v) {
      for (TipoPagamentoTipo c: TipoPagamentoTipo.values()) {
          if (c.value.equals(v)) {
              return c;
          }
      }
      throw new IllegalArgumentException(v);
  }
}