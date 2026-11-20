package it.giustizia.smp.dto.pg_procedimento;

import it.giustizia.smp.model.TPersonaFisica;
import it.giustizia.smp.model.TPersonaGiur;
import it.giustizia.smp.model.TRelProcSogint;
import it.giustizia.smp.model.TSoggetto;

import java.math.BigDecimal;

public class AssociaPersonaDto {

    private BigDecimal           IDPERS;
    private BigDecimal           IDPROC;
    private BigDecimal           IDSOGGINT;
    private BigDecimal           IDUFFOWN;
    private BigDecimal           IDVER;
    private TPersonaFisica       persFis;
    private TPersonaGiur         personaGiur;
    private TRelProcSogint       relProcSoggInt;
    private TSoggetto 			 soggInt;

    public BigDecimal getIDPERS() {

        return IDPERS;
    }

    public BigDecimal getIDPROC() {

        return IDPROC;
    }

    public BigDecimal getIDSOGGINT() {

        return IDSOGGINT;
    }

    public BigDecimal getIDUFFOWN() {

        return IDUFFOWN;
    }

    public BigDecimal getIDVER() {

        return IDVER;
    }

    public TPersonaFisica getPersFis() {

        return persFis;
    }

    public TPersonaGiur getPersonaGiur() {

        return personaGiur;
    }

    public TRelProcSogint getRelProcSoggInt() {

        return relProcSoggInt;
    }

    public TSoggetto getSoggInt() {

        return soggInt;
    }

    public void setIDPERS(BigDecimal iDPERS) {

        IDPERS = iDPERS;
    }

    public void setIDPROC(BigDecimal iDPROC) {

        IDPROC = iDPROC;
    }

    public void setIDSOGGINT(BigDecimal iDSOGGINT) {

        IDSOGGINT = iDSOGGINT;
    }

    public void setIDUFFOWN(BigDecimal iDUFFOWN) {

        IDUFFOWN = iDUFFOWN;
    }

    public void setIDVER(BigDecimal iDVER) {

        IDVER = iDVER;
    }

    public void setPersFis(TPersonaFisica persFis) {

        this.persFis = persFis;
    }

    public void setPersonaGiur(TPersonaGiur personaGiur) {

        this.personaGiur = personaGiur;
    }

    public void setRelProcSoggInt(TRelProcSogint relProcSoggInt) {

        this.relProcSoggInt = relProcSoggInt;
    }

    public void setSoggInt(TSoggetto soggInt) {

        this.soggInt = soggInt;
    }
}
