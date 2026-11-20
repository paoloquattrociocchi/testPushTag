package it.giustizia.smp.dto.flussi;

import it.giustizia.smp.dto.pg_procedimento.SogIntDettaglioProcedimentoDto;
import it.giustizia.smp.utility.Utility;

import java.math.BigDecimal;


public class T_SOGG_INT_PROCDto extends SogIntDettaglioProcedimentoDto {
	
    public void setDATA_ISCRIZIONE(String dATA_ISCRIZIONE) {
    	//System.out.println("---dentro t_sogg_intDto > "  + dATA_ISCRIZIONE);
        super.setDATA_ISCRIZIONE(Utility.stringToDate(dATA_ISCRIZIONE, "dd/MM/yyyy HH:mm:ss"));
    }
    
    public void setDATA_NASCITA(String dATA_NASCITA) {
    
//        super.setDATA_NASCITA(Utility.stringToDate(dATA_NASCITA, "dd/MM/yyyy"));
        super.setDATA_NASCITA(dATA_NASCITA);
       // System.out.println("---fine t_sogg_int > " + dATA_NASCITA);
    }

    public void setID_COM_NASC(String iD_COM_NASC) {

        super.setID_COM_NASC(new BigDecimal(iD_COM_NASC));
    }

    public void setID_COM_SEDE(String iD_COM_SEDE) {

        super.setID_COM_SEDE(new BigDecimal(iD_COM_SEDE));
    }

    public void setID_PERS(String iD_PERS) {

        super.setID_PERS(new BigDecimal(iD_PERS));
    }

    public void setID_STATO_NASC(String iD_STATO_NASC) {

        super.setID_STATO_NASC(new BigDecimal(iD_STATO_NASC));
    }

    public void setID_STATO_SEDE(String iD_STATO_SEDE) {

        super.setID_STATO_SEDE(new BigDecimal(iD_STATO_SEDE));
    }

    public void setID_VER(String iD_VER) {

        super.setID_VER(new BigDecimal(iD_VER));
    }

    public void setPARTITA_IVA(String pARTITA_IVA) {

        super.setPARTITA_IVA(pARTITA_IVA);
    }

    public void setTIPO_PERS(String tIPO_PERS) {

        super.setTIPO_PERS(new Character(tIPO_PERS.charAt(0)));
    }
    
	public void setCODI_FK_ID_TIPO_SOC(String cODI_FK_ID_TIPO_SOC) {
		super.setCODI_FK_ID_TIPO_SOC(new BigDecimal(cODI_FK_ID_TIPO_SOC));
	}
}
