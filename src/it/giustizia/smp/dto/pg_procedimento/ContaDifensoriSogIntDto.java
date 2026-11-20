package it.giustizia.smp.dto.pg_procedimento;

import java.math.BigDecimal;

public class ContaDifensoriSogIntDto {

    private BigDecimal IDSOGGINT;
    private BigDecimal NUMDIF;

    public BigDecimal getIDSOGGINT() {

        return IDSOGGINT;
    }

    public BigDecimal getNUMDIF() {

        return NUMDIF;
    }

    public void setIDSOGGINT(BigDecimal iDSOGGINT) {

        IDSOGGINT = iDSOGGINT;
    }

    public void setNUMDIF(BigDecimal nUMDIF) {

        NUMDIF = nUMDIF;
    }
}
